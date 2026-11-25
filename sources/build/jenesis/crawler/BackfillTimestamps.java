package build.jenesis.crawler;

import module java.base;
import build.jenesis.crawler.fetch.Fetcher;
import build.jenesis.crawler.model.Coordinate;
import build.jenesis.crawler.model.ScannedEntry;
import build.jenesis.crawler.store.ScannedStore;

/**
 * Standalone tool that fills in the publish timestamp of the rows in {@code data/scanned/}
 * that were recorded without one. Such rows come from coordinates that never passed through
 * the index (the metadata reconcile and, formerly, the failure retry), and they are invisible
 * to anything that buckets scan records by publish month.
 *
 * <p>Each undated, successfully scanned row costs one HEAD against the artifact base. The
 * GCS mirror answers with the upstream publish time in {@code x-goog-meta-last-modified} for
 * everything it synced itself, so that one request settles the row. Where the mirror only
 * offers its own {@code Last-Modified} (the pre-2019 bulk import, stamped with the time the
 * object landed in the bucket), the canonical timestamp source is asked instead; if that
 * yields nothing either the row is left undated rather than stamped with a landing time, and
 * the next run tries it again. Failed rows are left alone: a 404 has no timestamp to find.
 *
 * <p>No JAR is downloaded and nothing but {@code data/scanned/} is written, so the run needs
 * no resolved-view regeneration and can be stopped and restarted at any point.
 *
 * Usage:
 *   BackfillTimestamps &lt;artifact-base-uri&gt;
 */
public final class BackfillTimestamps {

    public static final String PROP_DATA = Crawl.PROP_DATA;
    public static final String PROP_BUDGET_MINUTES = Crawl.PROP_BUDGET_MINUTES;
    public static final String PROP_CONCURRENCY = Crawl.PROP_CONCURRENCY;
    public static final String PROP_CANONICAL_TIMESTAMP_URI = Crawl.PROP_CANONICAL_TIMESTAMP_URI;

    private static final int BATCH_SIZE = 5_000;
    private static final String JAR_EXTENSION = "jar";

    private BackfillTimestamps() {
    }

    /**
     * Totals of one run: {@code candidates} undated rows were looked at, {@code stamped} got a
     * timestamp, {@code notFound} no longer exist at the artifact base, and {@code unresolved}
     * exist but yielded no trustworthy timestamp. {@code complete} is false when the budget
     * ran out before the whole tree was walked.
     */
    public record Result(long candidates, long stamped, long notFound, long unresolved, boolean complete) {
    }

    public static void main(String[] arguments) throws IOException {
        if (arguments.length == 0 || arguments[0].equals("--help") || arguments[0].equals("-h")) {
            printUsage();
            if (arguments.length == 0) {
                throw new IllegalArgumentException("Missing required <artifact-base-uri> positional argument");
            }
            return;
        }
        if (arguments.length > 1) {
            printUsage();
            throw new IllegalArgumentException("Expected exactly 1 positional argument (<artifact-base-uri>); got "
                    + arguments.length);
        }
        URI artifactBase = URI.create(arguments[0]);
        Crawler.Configuration base = Crawler.Configuration.defaults(artifactBase, artifactBase);
        Path dataDir = property(PROP_DATA).map(Path::of).orElse(base.dataDir());
        Duration budget = property(PROP_BUDGET_MINUTES).map(Long::parseLong).map(Duration::ofMinutes).orElse(base.budget());
        int concurrency = property(PROP_CONCURRENCY).map(Integer::parseInt).orElse(base.concurrency());
        URI canonicalBase = property(PROP_CANONICAL_TIMESTAMP_URI).map(URI::create).orElse(artifactBase);
        System.out.println("[info] Configuration:");
        System.out.println("[info]   dataDir=" + dataDir.toAbsolutePath());
        System.out.println("[info]   budget=" + budget);
        System.out.println("[info]   concurrency=" + concurrency);
        System.out.println("[info]   artifactBase=" + artifactBase);
        System.out.println("[info]   canonicalTimestampBase=" + canonicalBase);
        Result result;
        try (Fetcher fetcher = new Fetcher()) {
            result = run(dataDir.resolve("scanned"), artifactBase, canonicalBase, fetcher, concurrency, budget);
        }
        System.out.println("[info] candidates=" + result.candidates()
                + " stamped=" + result.stamped()
                + " notFound=" + result.notFound()
                + " unresolved=" + result.unresolved()
                + " complete=" + result.complete());
    }

    public static Result run(Path scannedRoot,
                             URI artifactBase,
                             URI canonicalBase,
                             Fetcher fetcher,
                             int concurrency,
                             Duration budget) throws IOException {
        if (!Files.isDirectory(scannedRoot)) {
            return new Result(0L, 0L, 0L, 0L, true);
        }
        Instant deadline = Instant.now().plus(budget);
        ScannedStore store = new ScannedStore(scannedRoot);
        Semaphore permits = new Semaphore(concurrency);
        long[] totals = new long[4];
        List<Coordinate> batch = new ArrayList<>(BATCH_SIZE);
        boolean complete = true;
        try (Stream<Path> stream = Files.walk(scannedRoot);
             ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            Iterator<Path> iterator = stream.iterator();
            while (iterator.hasNext()) {
                Path file = iterator.next();
                if (!Files.isRegularFile(file) || !file.getFileName().toString().endsWith(ScannedStore.LEAF_FILE_EXTENSION)) {
                    continue;
                }
                collectUndated(scannedRoot, file, batch);
                if (batch.size() >= BATCH_SIZE) {
                    resolve(batch, artifactBase, canonicalBase, fetcher, executor, permits, store, totals);
                    batch.clear();
                    System.out.println("[backfill] candidates=" + totals[0] + " stamped=" + totals[1]
                            + " notFound=" + totals[2] + " unresolved=" + totals[3]);
                    if (!Instant.now().isBefore(deadline)) {
                        complete = false;
                        break;
                    }
                }
            }
            if (complete) {
                resolve(batch, artifactBase, canonicalBase, fetcher, executor, permits, store, totals);
            }
        }
        return new Result(totals[0], totals[1], totals[2], totals[3], complete);
    }

    private static void collectUndated(Path scannedRoot, Path file, List<Coordinate> sink) throws IOException {
        String fileName = file.getFileName().toString();
        String artifactId = fileName.substring(0, fileName.length() - ScannedStore.LEAF_FILE_EXTENSION.length());
        StringBuilder groupId = new StringBuilder();
        for (Path segment : scannedRoot.relativize(file.getParent())) {
            if (!groupId.isEmpty()) {
                groupId.append('.');
            }
            groupId.append(segment);
        }
        if (artifactId.isEmpty() || groupId.isEmpty()) {
            return;
        }
        try (Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8)) {
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
                if (entry.isFailed() || entry.publishedAt() > 0L) {
                    continue;
                }
                sink.add(new Coordinate(groupId.toString(), artifactId, entry.version(), entry.classifier(), JAR_EXTENSION, 0L, 0L));
            }
        }
    }

    private static void resolve(List<Coordinate> batch,
                                URI artifactBase,
                                URI canonicalBase,
                                Fetcher fetcher,
                                ExecutorService executor,
                                Semaphore permits,
                                ScannedStore store,
                                long[] totals) throws IOException {
        List<Future<Long>> futures = new ArrayList<>(batch.size());
        for (Coordinate coordinate : batch) {
            futures.add(executor.submit(() -> {
                permits.acquire();
                try {
                    return publishedAt(coordinate, artifactBase, canonicalBase, fetcher);
                } finally {
                    permits.release();
                }
            }));
        }
        for (int index = 0; index < batch.size(); index++) {
            long publishedAt;
            try {
                publishedAt = futures.get(index).get();
            } catch (InterruptedException interrupted) {
                Thread.currentThread().interrupt();
                throw new IOException("Interrupted while awaiting a timestamp probe", interrupted);
            } catch (ExecutionException failed) {
                publishedAt = UNRESOLVED;
            }
            totals[0]++;
            if (publishedAt > 0L) {
                store.markOk(batch.get(index).withLastModified(publishedAt));
                totals[1]++;
            } else if (publishedAt == NOT_FOUND) {
                totals[2]++;
            } else {
                totals[3]++;
            }
        }
        store.flush();
    }

    private static final long UNRESOLVED = 0L;
    private static final long NOT_FOUND = -1L;

    private static long publishedAt(Coordinate coordinate, URI artifactBase, URI canonicalBase, Fetcher fetcher) {
        String path = coordinate.mavenPath();
        Fetcher.HeadProbe primary;
        try {
            primary = fetcher.headLastModifiedProbe(artifactBase.resolve(path));
        } catch (IllegalArgumentException unaddressable) {
            return UNRESOLVED;
        }
        boolean singleSource = canonicalBase == null || canonicalBase.equals(artifactBase);
        if (primary.ok() && (primary.lastModifiedCanonical() || singleSource)) {
            return primary.lastModifiedMillis();
        }
        if (singleSource) {
            return primary.status() == 404 ? NOT_FOUND : UNRESOLVED;
        }
        Fetcher.HeadProbe canonical = fetcher.headLastModifiedProbe(canonicalBase.resolve(path));
        if (canonical.ok()) {
            return canonical.lastModifiedMillis();
        }
        return primary.status() == 404 && canonical.status() == 404 ? NOT_FOUND : UNRESOLVED;
    }

    private static Optional<String> property(String name) {
        String value = System.getProperty(name);
        return value == null || value.isBlank() ? Optional.empty() : Optional.of(value.trim());
    }

    private static void printUsage() {
        System.out.println("Usage: BackfillTimestamps <artifact-base-uri>");
        System.out.println();
        System.out.println("Fills in the publish timestamp of every successfully scanned row in <dataDir>/scanned/");
        System.out.println("that was recorded without one, with one HEAD per row and no JAR download. A mirror's own");
        System.out.println("Last-Modified is not trusted: where the artifact base offers nothing better, the canonical");
        System.out.println("timestamp source is asked, and a row neither can date is left for the next run.");
        System.out.println();
        System.out.println("System properties:");
        System.out.println("  -D" + PROP_DATA + "=<dir>                 Data directory (default: 'data')");
        System.out.println("  -D" + PROP_BUDGET_MINUTES + "=<minutes>   Wall-clock budget (default: 180)");
        System.out.println("  -D" + PROP_CONCURRENCY + "=<n>            Concurrent probes (default: 64)");
        System.out.println("  -D" + PROP_CANONICAL_TIMESTAMP_URI + "=<uri>");
        System.out.println("                                                  HEAD target when the artifact base only has a");
        System.out.println("                                                  mirror's own Last-Modified (default: the artifact base)");
    }
}
