package build.jenesis.modules;

import module java.base;

public final class Crawler {

    public static final String INDEX_FILE = "nexus-maven-repository-index.gz";
    public static final String INDEX_PROPERTIES_FILE = "nexus-maven-repository-index.properties";
    public static final String INCREMENTAL_PREFIX = "nexus-maven-repository-index.";
    public static final String INCREMENTAL_SUFFIX = ".gz";

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
                                long checkpointEvery) {

        public static Configuration defaults() {
            return new Configuration(
                    DEFAULT_INDEX_BASE,
                    DEFAULT_ARTIFACT_BASE,
                    Path.of("data"),
                    Duration.ofMinutes(160L),
                    128,
                    Scanner.DEFAULT_TAIL_SIZE,
                    2000L
            );
        }
    }

    public record Result(long processed, long modular, long failed, boolean worklistComplete, SyncMode syncMode) {
    }

    public record ScanOutcome(Coordinate coordinate, Optional<ScannedModule> module, Throwable error) {
    }

    private final Configuration configuration;
    private final Fetcher fetcher;
    private final Scanner scanner;
    private final ModuleStore store;
    private CheckpointListener checkpointListener;

    public Crawler(Configuration configuration) {
        this(configuration, new Fetcher(), new Scanner(configuration.tailSize()), new ModuleStore(configuration.dataDir().resolve("modules")));
    }

    public Crawler(Configuration configuration, Fetcher fetcher, Scanner scanner, ModuleStore store) {
        this.configuration = Objects.requireNonNull(configuration, "configuration");
        this.fetcher = Objects.requireNonNull(fetcher, "fetcher");
        this.scanner = Objects.requireNonNull(scanner, "scanner");
        this.store = Objects.requireNonNull(store, "store");
        this.checkpointListener = CheckpointListener.NOOP;
    }

    public Crawler withCheckpointListener(CheckpointListener listener) {
        this.checkpointListener = Objects.requireNonNull(listener, "listener");
        return this;
    }

    public Result run() throws IOException {
        Path statePath = configuration.dataDir().resolve("state.properties");
        State state = State.load(statePath);
        Worklist worklist = new Worklist(configuration.dataDir().resolve("worklist.tsv"));

        SyncMode syncMode = SyncMode.SKIPPED;
        if (!worklist.exists() || state.worklistComplete()) {
            IndexProperties remote = fetchIndexProperties();
            if (state.hasIndexBaseline()
                    && Objects.equals(state.indexChainId(), remote.chainId())
                    && state.indexChunkLastApplied() >= remote.lastIncremental()) {
                System.out.println("Index already up to date (chain=" + remote.chainId()
                        + ", lastIncremental=" + remote.lastIncremental() + "). Nothing to crawl.");
                return new Result(0L, 0L, 0L, true, SyncMode.UP_TO_DATE);
            }
            boolean incremental = state.hasIndexBaseline()
                    && Objects.equals(state.indexChainId(), remote.chainId());
            if (incremental) {
                state = generateIncrementalWorklist(worklist, state, remote);
                syncMode = SyncMode.INCREMENTAL;
            } else {
                if (state.indexChainId() != null && !state.indexChainId().equals(remote.chainId())) {
                    System.out.println("Index chain rotated from " + state.indexChainId()
                            + " to " + remote.chainId() + ": performing full sync.");
                }
                state = generateFullWorklist(worklist, state, remote);
                syncMode = SyncMode.FULL;
            }
            state.save(statePath);
        }
        return process(worklist, state, statePath, syncMode);
    }

    public IndexProperties fetchIndexProperties() throws IOException {
        URI uri = configuration.indexBaseUri().resolve(INDEX_PROPERTIES_FILE);
        try (InputStream input = fetcher.get(uri)) {
            return IndexProperties.read(input);
        }
    }

    private State generateFullWorklist(Worklist worklist, State state, IndexProperties remote) throws IOException {
        URI indexUri = configuration.indexBaseUri().resolve(INDEX_FILE);
        System.out.println("Downloading full Maven Central index from " + indexUri);
        long count = writeWorklist(worklist, target -> appendIndex(target, indexUri));
        System.out.println("Full worklist generated: " + count + " coordinates");
        return state.withWorklist(count, Instant.now())
                .withIndex(remote.lastIncremental(), remote.timestamp(), remote.chainId());
    }

    private State generateIncrementalWorklist(Worklist worklist, State state, IndexProperties remote) throws IOException {
        long from = state.indexChunkLastApplied() + 1L;
        long to = remote.lastIncremental();
        System.out.println("Fetching incremental index chunks " + from + ".." + to);
        long count = writeWorklist(worklist, target -> {
            long total = 0L;
            for (long chunk = from; chunk <= to; chunk++) {
                URI chunkUri = configuration.indexBaseUri().resolve(INCREMENTAL_PREFIX + chunk + INCREMENTAL_SUFFIX);
                total += appendIndex(target, chunkUri);
            }
            return total;
        });
        System.out.println("Incremental worklist generated: " + count + " coordinates across " + (to - from + 1L) + " chunks");
        return state.withWorklist(count, Instant.now())
                .withIndex(to, remote.timestamp(), remote.chainId());
    }

    private long writeWorklist(Worklist worklist, WriteOperation operation) throws IOException {
        Path target = worklist.path();
        Path parent = target.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        Path temp = target.resolveSibling(target.getFileName() + ".tmp");
        long count;
        try (BufferedWriter writer = Files.newBufferedWriter(temp, StandardCharsets.UTF_8)) {
            count = operation.execute(writer);
        }
        Files.move(temp, target, StandardCopyOption.REPLACE_EXISTING);
        return count;
    }

    @FunctionalInterface
    private interface WriteOperation {
        long execute(BufferedWriter writer) throws IOException;
    }

    private long appendIndex(BufferedWriter writer, URI uri) throws IOException {
        long count = 0L;
        try (InputStream raw = fetcher.get(uri);
             GZIPInputStream gzipped = new GZIPInputStream(raw);
             IndexReader reader = new IndexReader(gzipped)) {
            Map<String, String> record;
            while ((record = reader.nextRecord()) != null) {
                Optional<Coordinate> coordinate = Coordinate.from(record);
                if (coordinate.isEmpty()) {
                    continue;
                }
                Coordinate candidate = coordinate.get();
                if (!isInteresting(candidate)) {
                    continue;
                }
                writer.write(Worklist.format(candidate));
                writer.newLine();
                count++;
            }
        }
        return count;
    }

    public static boolean isInteresting(Coordinate coordinate) {
        if (!"jar".equals(coordinate.extension())) {
            return false;
        }
        return coordinate.classifier() == null || !SKIPPED_CLASSIFIERS.contains(coordinate.classifier());
    }

    private Result process(Worklist worklist, State state, Path statePath, SyncMode syncMode) throws IOException {
        Instant deadline = Instant.now().plus(configuration.budget());
        long processed = 0L;
        long modular = 0L;
        long failed = 0L;
        long sinceCheckpoint = 0L;
        try (Worklist.Reader reader = worklist.open(state.worklistPosition());
             ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            while (Instant.now().isBefore(deadline)) {
                List<Coordinate> batch = nextBatch(reader);
                if (batch.isEmpty()) {
                    break;
                }
                List<Future<ScanOutcome>> futures = new ArrayList<>(batch.size());
                for (Coordinate coordinate : batch) {
                    futures.add(executor.submit(() -> scanOne(coordinate)));
                }
                for (Future<ScanOutcome> future : futures) {
                    ScanOutcome outcome = await(future);
                    if (outcome.error() != null) {
                        failed++;
                    } else if (outcome.module().isPresent()) {
                        ScannedModule module = outcome.module().get();
                        synchronized (store) {
                            store.record(module.name(), module.type(), outcome.coordinate());
                        }
                        modular++;
                    }
                }
                processed += batch.size();
                sinceCheckpoint += batch.size();
                if (sinceCheckpoint >= configuration.checkpointEvery()) {
                    state = checkpoint(state, statePath, reader, processed, modular, failed, syncMode);
                    sinceCheckpoint = 0L;
                }
            }
            state = checkpoint(state, statePath, reader, processed, modular, failed, syncMode);
        }
        return new Result(processed, modular, failed, state.worklistComplete(), syncMode);
    }

    private List<Coordinate> nextBatch(Worklist.Reader reader) throws IOException {
        List<Coordinate> batch = new ArrayList<>(configuration.concurrency());
        for (int i = 0; i < configuration.concurrency(); i++) {
            String line = reader.nextLine();
            if (line == null) {
                break;
            }
            if (line.isEmpty()) {
                continue;
            }
            batch.add(Worklist.parse(line));
        }
        return batch;
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

    private State checkpoint(State state, Path statePath, Worklist.Reader reader, long processed, long modular, long failed, SyncMode syncMode) throws IOException {
        synchronized (store) {
            store.flush();
        }
        State updated = state.withPosition(reader.position());
        updated.save(statePath);
        System.out.println("checkpoint processed=" + processed + " modular=" + modular + " failed=" + failed + " position=" + reader.position());
        checkpointListener.onCheckpoint(updated, new CheckpointListener.Statistics(processed, modular, failed, syncMode));
        return updated;
    }

    private ScanOutcome scanOne(Coordinate coordinate) {
        try {
            URI uri = configuration.artifactBaseUri().resolve(coordinate.mavenPath());
            ByteSource source = fetcher.sourceWithCachedTail(uri, configuration.tailSize());
            Optional<ScannedModule> module = scanner.scan(source);
            return new ScanOutcome(coordinate, module, null);
        } catch (Throwable error) {
            return new ScanOutcome(coordinate, Optional.empty(), error);
        }
    }
}
