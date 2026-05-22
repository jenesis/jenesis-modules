package build.jenesis.modules;

import module java.base;

public final class Crawler implements AutoCloseable {

    public static final String INDEX_FILE = "nexus-maven-repository-index.gz";
    public static final String INDEX_PROPERTIES_FILE = "nexus-maven-repository-index.properties";
    public static final String INCREMENTAL_PREFIX = "nexus-maven-repository-index.";
    public static final String INCREMENTAL_SUFFIX = ".gz";
    public static final String STREAMING_SUFFIX = ".streaming";

    public static final Set<String> SKIPPED_CLASSIFIERS = Set.of(
            "sources", "javadoc", "tests", "test-sources", "cyclonedx"
    );

    public static final URI DEFAULT_INDEX_BASE = URI.create("https://repo.maven.apache.org/maven2/.index/");
    public static final URI DEFAULT_ARTIFACT_BASE = URI.create("https://maven-central.storage-download.googleapis.com/maven2/");

    public record Configuration(URI indexBaseUri,
                                URI artifactBaseUri,
                                Path dataDir,
                                Duration budget,
                                int concurrency,
                                int tailSize,
                                long checkpointEvery,
                                long smallJarThreshold) {

        public static final long DEFAULT_SMALL_JAR_THRESHOLD = 262144L;

        public static Configuration defaults() {
            return new Configuration(
                    DEFAULT_INDEX_BASE,
                    DEFAULT_ARTIFACT_BASE,
                    Path.of("data"),
                    Duration.ofMinutes(160L),
                    96,
                    Scanner.DEFAULT_TAIL_SIZE,
                    2000L,
                    DEFAULT_SMALL_JAR_THRESHOLD
            );
        }
    }

    public record Result(long processed, long modular, long failed, boolean worklistComplete, SyncMode syncMode, Map<String, FailureBreakdown> failureBreakdown) {
    }

    public record FailureBreakdown(long count, String sampleMessage) {
    }

    private static final Pattern STATUS_PATTERN = Pattern.compile("returned status (\\d+)");

    public record ScanOutcome(Coordinate coordinate, Optional<ScannedModule> module, Throwable error) {
    }

    private final Configuration configuration;
    private final Fetcher fetcher;
    private final Scanner scanner;
    private final ModuleStore store;
    private final ScannedStore scannedStore;
    private final boolean ownsFetcher;
    private final ConcurrentMap<String, FailureBucket> failures;
    private CheckpointListener checkpointListener;

    public Crawler(Configuration configuration) {
        this(configuration,
                new Fetcher(),
                new Scanner(configuration.tailSize()),
                new ModuleStore(configuration.dataDir().resolve("modules")),
                new ScannedStore(configuration.dataDir().resolve("scanned")),
                true);
    }

    public Crawler(Configuration configuration, Fetcher fetcher, Scanner scanner, ModuleStore store, ScannedStore scannedStore) {
        this(configuration, fetcher, scanner, store, scannedStore, false);
    }

    private Crawler(Configuration configuration, Fetcher fetcher, Scanner scanner, ModuleStore store, ScannedStore scannedStore, boolean ownsFetcher) {
        this.configuration = Objects.requireNonNull(configuration, "configuration");
        this.fetcher = Objects.requireNonNull(fetcher, "fetcher");
        this.scanner = Objects.requireNonNull(scanner, "scanner");
        this.store = Objects.requireNonNull(store, "store");
        this.scannedStore = Objects.requireNonNull(scannedStore, "scannedStore");
        this.ownsFetcher = ownsFetcher;
        this.failures = new ConcurrentHashMap<>();
        this.checkpointListener = CheckpointListener.NOOP;
    }

    private static final class FailureBucket {
        final AtomicLong count = new AtomicLong();
        volatile String sample;
    }

    private void recordFailure(Throwable error) {
        String key = categorize(error);
        FailureBucket bucket = failures.computeIfAbsent(key, ignored -> new FailureBucket());
        if (bucket.count.incrementAndGet() == 1L) {
            bucket.sample = sampleOf(error);
        }
    }

    private static String categorize(Throwable error) {
        String topName = error.getClass().getSimpleName();
        String topMessage = error.getMessage();
        if (topMessage != null) {
            java.util.regex.Matcher matcher = STATUS_PATTERN.matcher(topMessage);
            if (matcher.find()) {
                return topName + " status=" + matcher.group(1);
            }
        }
        Throwable root = rootCause(error);
        if (root == error) {
            return topName;
        }
        String rootName = root.getClass().getSimpleName();
        String rootMessage = root.getMessage();
        if (rootMessage != null) {
            java.util.regex.Matcher matcher = STATUS_PATTERN.matcher(rootMessage);
            if (matcher.find()) {
                return rootName + " status=" + matcher.group(1);
            }
        }
        return topName + " <- " + rootName;
    }

    private static String sampleOf(Throwable error) {
        StringBuilder builder = new StringBuilder();
        appendThrowableLine(builder, error);
        Throwable cause = error.getCause();
        Set<Throwable> seen = new HashSet<>();
        seen.add(error);
        while (cause != null && seen.add(cause)) {
            builder.append(" | cause=");
            appendThrowableLine(builder, cause);
            cause = cause.getCause();
        }
        String text = builder.toString();
        return text.length() > 360 ? text.substring(0, 360) + "..." : text;
    }

    private static void appendThrowableLine(StringBuilder builder, Throwable error) {
        builder.append(error.getClass().getSimpleName());
        String message = error.getMessage();
        if (message != null && !message.isEmpty()) {
            builder.append(": ").append(message);
        }
    }

    private static Throwable rootCause(Throwable error) {
        Throwable current = error;
        Set<Throwable> seen = new HashSet<>();
        seen.add(current);
        while (current.getCause() != null && seen.add(current.getCause())) {
            current = current.getCause();
        }
        return current;
    }

    private Map<String, FailureBreakdown> snapshotFailures() {
        return failures.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> new FailureBreakdown(entry.getValue().count.get(), entry.getValue().sample),
                        (a, b) -> a,
                        LinkedHashMap::new));
    }

    public Crawler withCheckpointListener(CheckpointListener listener) {
        this.checkpointListener = Objects.requireNonNull(listener, "listener");
        return this;
    }

    @Override
    public void close() {
        if (ownsFetcher) {
            fetcher.close();
        }
    }

    public Result run() throws IOException {
        Path statePath = configuration.dataDir().resolve("state.properties");
        State state = State.load(statePath);
        Worklist worklist = new Worklist(configuration.dataDir().resolve("worklist.tsv"));

        if (worklist.exists() && state.worklistRecords() > 0L && !state.worklistComplete()) {
            return runFromFile(worklist, state, statePath);
        }
        if (worklist.exists() && state.worklistRecords() == 0L) {
            try {
                Files.delete(worklist.path());
            } catch (IOException ignored) {
            }
        }

        IndexProperties remote = fetchIndexProperties();
        if (state.hasIndexBaseline()
                && Objects.equals(state.indexChainId(), remote.chainId())
                && state.indexChunkLastApplied() >= remote.lastIncremental()) {
            System.out.println("Index already up to date (chain=" + remote.chainId()
                    + ", lastIncremental=" + remote.lastIncremental() + "). Nothing to crawl.");
            return new Result(0L, 0L, 0L, true, SyncMode.UP_TO_DATE, Map.of());
        }
        boolean incremental = state.hasIndexBaseline()
                && Objects.equals(state.indexChainId(), remote.chainId());
        if (!incremental && state.indexChainId() != null && !state.indexChainId().equals(remote.chainId())) {
            System.out.println("Index chain rotated from " + state.indexChainId()
                    + " to " + remote.chainId() + ": performing full sync.");
        }
        SyncMode mode = incremental ? SyncMode.INCREMENTAL : SyncMode.FULL;
        List<URI> indexUris = incremental
                ? incrementalChunkUris(state.indexChunkLastApplied() + 1L, remote.lastIncremental())
                : List.of(configuration.indexBaseUri().resolve(INDEX_FILE));

        return runStreaming(worklist, state, statePath, indexUris, remote, mode);
    }

    public IndexProperties fetchIndexProperties() throws IOException {
        URI uri = configuration.indexBaseUri().resolve(INDEX_PROPERTIES_FILE);
        try (InputStream input = fetcher.get(uri)) {
            return IndexProperties.read(input);
        }
    }

    public static boolean isInteresting(Coordinate coordinate) {
        if (!"jar".equals(coordinate.extension())) {
            return false;
        }
        return coordinate.classifier() == null || !SKIPPED_CLASSIFIERS.contains(coordinate.classifier());
    }

    private List<URI> incrementalChunkUris(long from, long to) {
        List<URI> uris = new ArrayList<>();
        for (long chunk = from; chunk <= to; chunk++) {
            uris.add(configuration.indexBaseUri().resolve(INCREMENTAL_PREFIX + chunk + INCREMENTAL_SUFFIX));
        }
        return uris;
    }

    private Result runFromFile(Worklist worklist, State state, Path statePath) throws IOException {
        System.out.println("Resuming existing worklist: position " + state.worklistPosition()
                + "/" + state.worklistRecords() + " records");
        long pinnedRecords = state.worklistRecords();
        try (FileBatchSource source = new FileBatchSource(worklist, state.worklistPosition(), configuration.concurrency())) {
            return process(source, state, statePath, SyncMode.SKIPPED, state.worklistPosition(), () -> pinnedRecords);
        } catch (InterruptedException interrupted) {
            Thread.currentThread().interrupt();
            throw new IOException("Interrupted", interrupted);
        }
    }

    private Result runStreaming(Worklist worklist,
                                State state,
                                Path statePath,
                                List<URI> indexUris,
                                IndexProperties remote,
                                SyncMode mode) throws IOException {
        System.out.println("Streaming " + mode + " sync from " + indexUris.size() + " index source(s)");
        Path tempFile = worklist.path().resolveSibling(worklist.path().getFileName() + STREAMING_SUFFIX);
        State streamingState = state.clearedWorklist().withWorklist(0L, Instant.now());
        streamingState.save(statePath);

        Predicate<Coordinate> producerFilter = candidate -> isInteresting(candidate) && !scannedStore.contains(candidate);
        try (WorklistStream stream = new WorklistStream(tempFile, fetcher, producerFilter)) {
            stream.start(indexUris);
            try (StreamingBatchSource source = new StreamingBatchSource(stream, configuration.concurrency())) {
                Result result = process(source, streamingState, statePath, mode, 0L, stream::recordsProduced);
                stream.close();
                if (stream.error() != null) {
                    throw stream.error();
                }
                if (stream.completed()) {
                    finalizeStreamedWorklist(worklist, tempFile, statePath, stream, remote);
                    return result;
                }
                Files.deleteIfExists(tempFile);
                System.out.println("Streaming sync did not finish within budget; worklist discarded, next run will re-sync.");
                return result;
            }
        } catch (InterruptedException interrupted) {
            Thread.currentThread().interrupt();
            throw new IOException("Interrupted while streaming", interrupted);
        }
    }

    private void finalizeStreamedWorklist(Worklist worklist,
                                          Path tempFile,
                                          Path statePath,
                                          WorklistStream stream,
                                          IndexProperties remote) throws IOException {
        Path target = worklist.path();
        try {
            Files.move(tempFile, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (AtomicMoveNotSupportedException atomicNotSupported) {
            Files.move(tempFile, target, StandardCopyOption.REPLACE_EXISTING);
        }
        State state = State.load(statePath);
        State updated = state
                .withWorklist(stream.recordsProduced(), state.sweepStartedAt())
                .withPosition(state.worklistPosition())
                .withIndex(remote.lastIncremental(), remote.timestamp(), remote.chainId());
        updated.save(statePath);
        System.out.println("Sync complete: " + stream.recordsProduced() + " records at " + target);
    }

    private Result process(BatchSource source,
                           State state,
                           Path statePath,
                           SyncMode syncMode,
                           long startPosition,
                           LongSupplier knownTotal) throws IOException, InterruptedException {
        Instant deadline = Instant.now().plus(configuration.budget());
        long processed = 0L;
        long modular = 0L;
        long failed = 0L;
        long sinceCheckpoint = 0L;
        long position = startPosition;
        boolean exhausted = false;
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            while (Instant.now().isBefore(deadline) && !exhausted) {
                BatchSource.Batch batch = source.next();
                position = batch.endPosition() >= 0L ? batch.endPosition() : position;
                exhausted = batch.exhausted();
                if (batch.isEmpty()) {
                    continue;
                }

                List<Future<ScanOutcome>> futures = new ArrayList<>(batch.coordinates().size());
                for (Coordinate coordinate : batch.coordinates()) {
                    if (scannedStore.contains(coordinate)) {
                        continue;
                    }
                    futures.add(executor.submit(() -> scanOne(coordinate)));
                }
                for (Future<ScanOutcome> future : futures) {
                    ScanOutcome outcome = await(future);
                    if (outcome.error() != null) {
                        failed++;
                        recordFailure(outcome.error());
                    } else {
                        scannedStore.mark(outcome.coordinate());
                        if (outcome.module().isPresent()) {
                            ScannedModule module = outcome.module().get();
                            synchronized (store) {
                                store.record(module.name(), module.type(), outcome.coordinate());
                            }
                            modular++;
                        }
                    }
                }
                processed += batch.coordinates().size();
                sinceCheckpoint += batch.coordinates().size();
                if (sinceCheckpoint >= configuration.checkpointEvery()) {
                    state = checkpoint(state, statePath, position, processed, modular, failed, syncMode, knownTotal);
                    sinceCheckpoint = 0L;
                }
            }
            state = checkpoint(state, statePath, position, processed, modular, failed, syncMode, knownTotal);
        }
        return new Result(processed, modular, failed, state.worklistComplete(), syncMode, snapshotFailures());
    }

    private static ScanOutcome await(Future<ScanOutcome> future) throws IOException {
        try {
            return future.get();
        } catch (InterruptedException interrupted) {
            Thread.currentThread().interrupt();
            throw new IOException("Interrupted while awaiting scan", interrupted);
        } catch (ExecutionException execution) {
            Throwable cause = execution.getCause();
            if (cause instanceof IOException io) {
                throw io;
            }
            if (cause instanceof RuntimeException runtime) {
                throw runtime;
            }
            throw new IOException(cause);
        }
    }

    private State checkpoint(State state, Path statePath, long position, long processed, long modular, long failed, SyncMode syncMode, LongSupplier knownTotal) throws IOException {
        synchronized (store) {
            store.flush();
        }
        scannedStore.flush();
        long total = knownTotal.getAsLong();
        State updated = state.withPosition(position);
        if (total >= 0L) {
            updated = updated.withRecords(total);
        }
        updated.save(statePath);
        System.out.println("checkpoint processed=" + processed + " modular=" + modular + " failed=" + failed
                + " position=" + position + "/" + updated.worklistRecords());
        checkpointListener.onCheckpoint(updated, new CheckpointListener.Statistics(processed, modular, failed, syncMode));
        return updated;
    }

    private ScanOutcome scanOne(Coordinate coordinate) {
        URI uri;
        try {
            uri = configuration.artifactBaseUri().resolve(coordinate.mavenPath());
        } catch (Throwable error) {
            return new ScanOutcome(coordinate, Optional.empty(), error);
        }
        long size = coordinate.size();
        if (size > 0L && size <= configuration.smallJarThreshold()) {
            try {
                byte[] bytes = fetcher.range(uri, 0L, (int) size);
                Optional<ScannedModule> module = scanner.scan(ByteSource.ofBytes(bytes));
                return new ScanOutcome(coordinate, module, null);
            } catch (IOException | IllegalArgumentException smallJarFailure) {
                // Likely a size mismatch with the index; fall back to the cached-tail path.
            }
        }
        try {
            ByteSource source = fetcher.sourceWithCachedTail(uri, configuration.tailSize());
            Optional<ScannedModule> module = scanner.scan(source);
            return new ScanOutcome(coordinate, module, null);
        } catch (Throwable error) {
            return new ScanOutcome(coordinate, Optional.empty(), error);
        }
    }
}
