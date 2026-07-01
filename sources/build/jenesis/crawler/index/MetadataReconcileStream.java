package build.jenesis.crawler.index;

import module java.base;
import build.jenesis.crawler.fetch.Fetcher;
import build.jenesis.crawler.model.Coordinate;
import build.jenesis.crawler.model.ScannedEntry;

/**
 * Producer that walks {@code data/scanned/} and emits the coordinates we never saw via the
 * Maven Central Nexus index. For each {@code <groupId-path>/<artifactId>.tsv} file it:
 *
 * <ol>
 *   <li>Fetches {@code <artifactBase>/<groupId-path>/<artifactId>/maven-metadata.xml}</li>
 *   <li>Parses the {@code <versions>} list</li>
 *   <li>Reads the local scanned tsv and collects every version that already has a
 *       <em>classifier-less</em> {@code .jar} row (the main jar - the only thing the scanner
 *       ever directly emits, since {@link build.jenesis.crawler.Crawler#SKIPPED_CLASSIFIERS}
 *       drops sources/javadoc/tests)</li>
 *   <li>Diffs the two sets and pushes the missing versions onto the queue as plain
 *       {@code Coordinate(groupId, artifactId, version, null, "jar", 0, 0)} items</li>
 * </ol>
 *
 * <p>The motivation: the Nexus index is sometimes missing main-jar records for versions whose
 * sidecar artifacts (sources, javadoc, signatures) are indexed. {@code byte-buddy} for instance
 * has 315 versions on Central but only 86 main-jar records in the index, so the regular crawler
 * physically cannot see the other 229. {@code maven-metadata.xml} is the canonical version
 * list and recovers them.
 *
 * <p>Shape matches {@link IndexStream}: the producer thread feeds a {@link BlockingQueue} of
 * {@link IndexStream.QueueItem}s and signals exhaustion with {@link IndexStream.QueueItem#POISON};
 * downstream consumers wrap the queue in a {@link StreamingBatchSource}, so the rest of the
 * crawler (scanner, scanned-store filter, module-store, checkpoint listener) plugs in unchanged.
 */
public final class MetadataReconcileStream implements AutoCloseable {

    public static final int DEFAULT_QUEUE_CAPACITY = 4096;
    public static final int DEFAULT_METADATA_CONCURRENCY = 32;
    public static final long DEFAULT_LOG_EVERY = 1_000L;
    public static final Duration JOIN_TIMEOUT = Duration.ofSeconds(5L);
    public static final String METADATA_FILE = "maven-metadata.xml";

    private final BlockingQueue<IndexStream.QueueItem> queue;
    private final Fetcher fetcher;
    private final Path scannedRoot;
    private final URI artifactBase;
    private final int metadataConcurrency;
    private final List<String[]> explicitCoordinates;
    private final AtomicReference<IOException> producerError = new AtomicReference<>();
    private final AtomicLong recordsProduced = new AtomicLong();
    private final AtomicLong filesWalked = new AtomicLong();
    private final AtomicLong metadataFetched = new AtomicLong();
    private final AtomicLong metadataMissing = new AtomicLong();
    private final AtomicLong metadataFailed = new AtomicLong();
    private final AtomicLong artifactsWithGap = new AtomicLong();
    private volatile Thread producer;

    public MetadataReconcileStream(Fetcher fetcher,
                                   Path scannedRoot,
                                   URI artifactBase,
                                   int metadataConcurrency) {
        this(fetcher, scannedRoot, artifactBase, metadataConcurrency, DEFAULT_QUEUE_CAPACITY, null);
    }

    public MetadataReconcileStream(Fetcher fetcher,
                                   Path scannedRoot,
                                   URI artifactBase,
                                   int metadataConcurrency,
                                   int queueCapacity) {
        this(fetcher, scannedRoot, artifactBase, metadataConcurrency, queueCapacity, null);
    }

    /**
     * Explicit-coordinate mode: instead of walking {@code scannedRoot}, reconcile exactly the
     * supplied {@code groupId:artifactId} coordinates against their {@code maven-metadata.xml}.
     * A coordinate with no scanned tsv yet has all of its Central versions queued for scanning;
     * versions already recorded are skipped, so re-running is idempotent. This is the producer
     * behind {@link build.jenesis.crawler.LoadCoordinates}, which seeds brand-new coordinates
     * that never appeared in the Maven Central Nexus index the regular crawler streams.
     */
    public MetadataReconcileStream(Fetcher fetcher,
                                   Path scannedRoot,
                                   URI artifactBase,
                                   int metadataConcurrency,
                                   List<String> coordinates) {
        this(fetcher, scannedRoot, artifactBase, metadataConcurrency, DEFAULT_QUEUE_CAPACITY, parseCoordinates(coordinates));
    }

    private MetadataReconcileStream(Fetcher fetcher,
                                    Path scannedRoot,
                                    URI artifactBase,
                                    int metadataConcurrency,
                                    int queueCapacity,
                                    List<String[]> explicitCoordinates) {
        this.fetcher = Objects.requireNonNull(fetcher, "fetcher");
        this.scannedRoot = Objects.requireNonNull(scannedRoot, "scannedRoot");
        this.artifactBase = Objects.requireNonNull(artifactBase, "artifactBase");
        if (metadataConcurrency < 1) {
            throw new IllegalArgumentException("metadataConcurrency must be >= 1, got " + metadataConcurrency);
        }
        if (queueCapacity < 1) {
            throw new IllegalArgumentException("queueCapacity must be >= 1, got " + queueCapacity);
        }
        this.metadataConcurrency = metadataConcurrency;
        this.queue = new ArrayBlockingQueue<>(queueCapacity);
        this.explicitCoordinates = explicitCoordinates;
    }

    private static List<String[]> parseCoordinates(List<String> coordinates) {
        Objects.requireNonNull(coordinates, "coordinates");
        List<String[]> parsed = new ArrayList<>();
        for (String coordinate : coordinates) {
            int colon = coordinate.indexOf(':');
            if (colon < 1 || colon != coordinate.lastIndexOf(':') || colon == coordinate.length() - 1) {
                throw new IllegalArgumentException("Expected exactly one 'groupId:artifactId', got: " + coordinate);
            }
            parsed.add(new String[] {coordinate.substring(0, colon), coordinate.substring(colon + 1)});
        }
        if (parsed.isEmpty()) {
            throw new IllegalArgumentException("At least one groupId:artifactId coordinate is required");
        }
        return parsed;
    }

    public BlockingQueue<IndexStream.QueueItem> queue() {
        return queue;
    }

    public IOException error() {
        return producerError.get();
    }

    public long recordsProduced() {
        return recordsProduced.get();
    }

    public long filesWalked() {
        return filesWalked.get();
    }

    public void start() {
        Thread thread = Thread.ofVirtual().name("metadata-reconciler").unstarted(this::runProducer);
        producer = thread;
        thread.start();
    }

    private void runProducer() {
        try {
            walk();
        } catch (IOException producerFailed) {
            producerError.set(producerFailed);
        } catch (InterruptedException interrupted) {
            Thread.currentThread().interrupt();
        } finally {
            try {
                queue.put(IndexStream.QueueItem.POISON);
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void walk() throws IOException, InterruptedException {
        if (explicitCoordinates != null) {
            walkExplicit();
            return;
        }
        if (!Files.isDirectory(scannedRoot)) {
            System.out.println("[reconcile] No scanned root at " + scannedRoot + "; nothing to reconcile.");
            return;
        }
        Semaphore inflight = new Semaphore(metadataConcurrency);
        long startNanos = System.nanoTime();
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
             Stream<Path> stream = Files.walk(scannedRoot)) {
            for (Path file : (Iterable<Path>) stream::iterator) {
                if (Thread.currentThread().isInterrupted()) {
                    throw new InterruptedException("Reconciler interrupted");
                }
                if (!Files.isRegularFile(file)) {
                    continue;
                }
                String fileName = file.getFileName().toString();
                if (!fileName.endsWith(".tsv")) {
                    continue;
                }
                String artifactId = fileName.substring(0, fileName.length() - ".tsv".length());
                if (artifactId.isEmpty()) {
                    continue;
                }
                String groupId = pathToGroupId(scannedRoot, file.getParent());
                if (groupId.isEmpty()) {
                    continue;
                }
                inflight.acquire();
                Path scannedFile = file;
                executor.submit(() -> {
                    try {
                        processArtifact(groupId, artifactId, scannedFile);
                    } finally {
                        inflight.release();
                        long walked = filesWalked.incrementAndGet();
                        if (walked % DEFAULT_LOG_EVERY == 0L) {
                            logProgress(walked, startNanos);
                        }
                    }
                });
            }
            // Drain in-flight metadata fetches before we drop POISON, otherwise we could
            // race and signal exhaustion while some artifacts are still queueing missing
            // coordinates.
            inflight.acquire(metadataConcurrency);
            inflight.release(metadataConcurrency);
        }
        logProgress(filesWalked.get(), startNanos);
    }

    private void walkExplicit() {
        long startNanos = System.nanoTime();
        for (String[] coordinate : explicitCoordinates) {
            if (Thread.currentThread().isInterrupted()) {
                Thread.currentThread().interrupt();
                return;
            }
            Path scannedFile = scannedRoot.resolve(coordinate[0].replace('.', '/')).resolve(coordinate[1] + ".tsv");
            processArtifact(coordinate[0], coordinate[1], scannedFile);
            logProgress(filesWalked.incrementAndGet(), startNanos);
        }
    }

    private void processArtifact(String groupId, String artifactId, Path scannedFile) {
        try {
            Set<String> alreadyScanned = readScannedMainVersions(scannedFile);
            List<String> centralVersions = fetchMetadataVersions(groupId, artifactId);
            if (centralVersions == null) {
                metadataMissing.incrementAndGet();
                return;
            }
            metadataFetched.incrementAndGet();
            List<String> missing = new ArrayList<>();
            for (String version : centralVersions) {
                if (!alreadyScanned.contains(version)) {
                    missing.add(version);
                }
            }
            if (missing.isEmpty()) {
                return;
            }
            artifactsWithGap.incrementAndGet();
            for (String version : missing) {
                Coordinate coordinate = new Coordinate(groupId, artifactId, version, null, "jar", 0L, 0L);
                long sequence = recordsProduced.incrementAndGet();
                queue.put(new IndexStream.QueueItem(coordinate, sequence));
            }
        } catch (InterruptedException interrupted) {
            Thread.currentThread().interrupt();
        } catch (IOException artifactFailed) {
            metadataFailed.incrementAndGet();
            System.err.println("[reconcile] " + groupId + ":" + artifactId + " failed: "
                    + artifactFailed.getClass().getSimpleName() + ": " + artifactFailed.getMessage());
        }
    }

    /**
     * Loads the set of versions for which we already have a classifier-less main-jar row in
     * the per-artifact scanned tsv. Classifier-only rows don't count: their existence implies
     * the version is present somewhere, but if the main jar wasn't indexed (the byte-buddy
     * symptom that motivated this tool) we still need to fetch and scan it.
     */
    private static Set<String> readScannedMainVersions(Path scannedFile) throws IOException {
        Set<String> result = new HashSet<>();
        if (!Files.exists(scannedFile)) {
            return result;
        }
        try (Stream<String> lines = Files.lines(scannedFile, StandardCharsets.UTF_8)) {
            Iterator<String> iterator = lines.iterator();
            while (iterator.hasNext()) {
                String line = iterator.next();
                if (line.isEmpty()) {
                    continue;
                }
                ScannedEntry entry;
                try {
                    entry = ScannedEntry.parse(line);
                } catch (IllegalArgumentException malformed) {
                    continue;
                }
                if (entry.classifier() == null) {
                    result.add(entry.version());
                }
            }
        }
        return result;
    }

    /**
     * Fetches and parses {@code maven-metadata.xml} for {@code groupId:artifactId}. Returns
     * the list of versions verbatim from {@code <versioning><versions><version>...</version>}.
     * Returns {@code null} when the metadata file is absent (404 or equivalent) - the
     * artifact may have been deleted upstream, which is not a failure but is also not a gap
     * we can fill.
     */
    private List<String> fetchMetadataVersions(String groupId, String artifactId) throws IOException {
        URI uri = artifactBase.resolve(groupId.replace('.', '/') + "/" + artifactId + "/" + METADATA_FILE);
        byte[] bytes;
        try (InputStream stream = fetcher.get(uri)) {
            bytes = stream.readAllBytes();
        } catch (IOException maybeMissing) {
            if (maybeMissing.getMessage() != null && maybeMissing.getMessage().contains("status 404")) {
                return null;
            }
            throw maybeMissing;
        }
        return parseMavenMetadataVersions(new String(bytes, StandardCharsets.UTF_8));
    }

    /**
     * Minimal hand-rolled parser for the version list. {@code maven-metadata.xml} has a tiny,
     * stable shape (Maven 1.x and 2.x both emit {@code <versioning><versions><version>X</version>
     * </versions></versioning>}) so the regex is robust enough; pulling in a real XML parser
     * would add a stax/dom dependency for one tag we already know.
     */
    static List<String> parseMavenMetadataVersions(String xml) {
        List<String> versions = new ArrayList<>();
        Matcher matcher = VERSION_PATTERN.matcher(xml);
        while (matcher.find()) {
            String version = matcher.group(1).trim();
            if (!version.isEmpty()) {
                versions.add(version);
            }
        }
        return versions;
    }

    private static final Pattern VERSION_PATTERN = Pattern.compile("<version>([^<]+)</version>");

    private void logProgress(long walked, long startNanos) {
        long elapsedSeconds = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - startNanos);
        long rate = elapsedSeconds > 0L ? walked / elapsedSeconds : walked;
        System.out.println("[reconcile] walked=" + walked
                + " withMetadata=" + metadataFetched.get()
                + " metadataMissing=" + metadataMissing.get()
                + " metadataFailed=" + metadataFailed.get()
                + " artifactsWithGap=" + artifactsWithGap.get()
                + " queued=" + recordsProduced.get()
                + " inQueue=" + queue.size()
                + " rate=" + rate + "/s"
                + " elapsed=" + elapsedSeconds + "s");
    }

    private static String pathToGroupId(Path root, Path dir) {
        if (dir == null) {
            return "";
        }
        Path relative = root.relativize(dir);
        StringBuilder builder = new StringBuilder();
        for (Path segment : relative) {
            if (!builder.isEmpty()) {
                builder.append('.');
            }
            builder.append(segment.toString());
        }
        return builder.toString();
    }

    @Override
    public void close() {
        Thread current = producer;
        if (current == null) {
            return;
        }
        current.interrupt();
        long deadline = System.nanoTime() + JOIN_TIMEOUT.toNanos();
        while (current.isAlive() && System.nanoTime() < deadline) {
            try {
                queue.poll(50L, TimeUnit.MILLISECONDS);
            } catch (InterruptedException interrupted) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        try {
            current.join(JOIN_TIMEOUT.toMillis());
        } catch (InterruptedException interrupted) {
            Thread.currentThread().interrupt();
        }
    }
}
