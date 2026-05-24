package build.jenesis.crawler;

import module java.base;

public final class Crawler implements AutoCloseable {

    public static final String INDEX_FILE = "nexus-maven-repository-index.gz";
    public static final String INDEX_PROPERTIES_FILE = "nexus-maven-repository-index.properties";
    public static final String INCREMENTAL_PREFIX = "nexus-maven-repository-index.";
    public static final String INCREMENTAL_SUFFIX = ".gz";

    public static final Set<String> SKIPPED_CLASSIFIERS = Set.of(
            "sources", "javadoc", "tests", "test-sources", "cyclonedx"
    );

    public record Configuration(URI indexBaseUri,
                                URI artifactBaseUri,
                                Path dataDir,
                                Duration budget,
                                int concurrency,
                                int tailSize,
                                long checkpointEvery,
                                long smallJarThreshold,
                                boolean resume,
                                boolean reprocessFailed,
                                boolean allowRebaseline) {

        public static final long DEFAULT_SMALL_JAR_THRESHOLD = 262144L;
        public static final Duration DEFAULT_BUDGET = Duration.ofMinutes(180L);
        public static final int DEFAULT_CONCURRENCY = 64;
        public static final long DEFAULT_CHECKPOINT_EVERY = 2000L;
        public static final Path DEFAULT_DATA_DIR = Path.of("data");
        public static final boolean DEFAULT_RESUME = true;
        public static final boolean DEFAULT_REPROCESS_FAILED = false;
        public static final boolean DEFAULT_ALLOW_REBASELINE = false;

        public static Configuration defaults(URI artifactBaseUri, URI indexBaseUri) {
            return new Configuration(
                    Objects.requireNonNull(indexBaseUri, "indexBaseUri"),
                    Objects.requireNonNull(artifactBaseUri, "artifactBaseUri"),
                    DEFAULT_DATA_DIR,
                    DEFAULT_BUDGET,
                    DEFAULT_CONCURRENCY,
                    Scanner.DEFAULT_TAIL_SIZE,
                    DEFAULT_CHECKPOINT_EVERY,
                    DEFAULT_SMALL_JAR_THRESHOLD,
                    DEFAULT_RESUME,
                    DEFAULT_REPROCESS_FAILED,
                    DEFAULT_ALLOW_REBASELINE
            );
        }
    }

    public record Result(long processed, long named, long automatic, long failed, boolean chunkComplete, SyncMode syncMode, Map<String, FailureBreakdown> failureBreakdown) {
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
    private final DirtyModules dirtyModules;
    private final boolean ownsFetcher;
    private final ConcurrentMap<String, FailureBucket> failures;
    private CheckpointListener checkpointListener;

    public Crawler(Configuration configuration) {
        this(configuration,
                new Fetcher(),
                new Scanner(configuration.tailSize()),
                new ModuleStore(configuration.dataDir().resolve("modules")),
                new ScannedStore(configuration.dataDir().resolve("scanned"), configuration.reprocessFailed()),
                new DirtyModules(configuration.dataDir()),
                true);
    }

    public Crawler(Configuration configuration, Fetcher fetcher, Scanner scanner, ModuleStore store, ScannedStore scannedStore) {
        this(configuration, fetcher, scanner, store, scannedStore, new DirtyModules(configuration.dataDir()), false);
    }

    private Crawler(Configuration configuration, Fetcher fetcher, Scanner scanner, ModuleStore store, ScannedStore scannedStore, DirtyModules dirtyModules, boolean ownsFetcher) {
        this.configuration = Objects.requireNonNull(configuration, "configuration");
        this.fetcher = Objects.requireNonNull(fetcher, "fetcher");
        this.scanner = Objects.requireNonNull(scanner, "scanner");
        this.store = Objects.requireNonNull(store, "store");
        this.scannedStore = Objects.requireNonNull(scannedStore, "scannedStore");
        this.dirtyModules = Objects.requireNonNull(dirtyModules, "dirtyModules");
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
        FailureBucket bucket = failures.computeIfAbsent(key, _ -> new FailureBucket());
        if (bucket.count.incrementAndGet() == 1L) {
            bucket.sample = sampleOf(error);
        }
    }

    /**
     * Distinguishes failures that are intrinsic to the artifact (and thus permanent under
     * the same input) from transient failures (network, server, timeout) that may succeed
     * on a later run.
     *
     * Permanent: any {@link IllegalArgumentException}, {@link InvalidModuleDescriptorException},
     * or {@link java.util.zip.ZipException} in the cause chain (the scanner raises these for
     * malformed ZIPs and invalid module-info contents); HTTP 404/410 (the artifact is not /
     * no longer present at the URL we computed); or any cause whose message contains one of
     * {@link #PERMANENT_MESSAGE_FRAGMENTS} (intrinsic JAR-parsing errors that the JDK surfaces
     * as plain {@link IOException}). Everything else - including HTTP 5xx, timeouts, and
     * generic IOException without a known intrinsic-message fragment - is treated as transient.
     */
    private static boolean isNotFound(Throwable error) {
        String message = error == null ? null : error.getMessage();
        if (message == null) {
            return false;
        }
        java.util.regex.Matcher matcher = STATUS_PATTERN.matcher(message);
        if (matcher.find()) {
            int status = Integer.parseInt(matcher.group(1));
            return status == 404 || status == 410;
        }
        return false;
    }

    /**
     * Message fragments that, when found anywhere in a failure's cause chain, classify the
     * failure as intrinsic to the artifact (permanent). Used when the JDK surfaces an
     * intrinsic JAR-parsing problem as a plain {@link IOException} instead of a more specific
     * exception type. Grown from observation: add new fragments here as future runs surface
     * recurring intrinsic-failure messages that aren't already covered by the type checks.
     */
    private static final List<String> PERMANENT_MESSAGE_FRAGMENTS = List.of(
            // java.util.jar.Manifest.parse() chokes on malformed MANIFEST.MF content
            "invalid header field"
    );

    private static boolean isPermanentFailure(Throwable error) {
        Throwable current = error;
        Set<Throwable> seen = new HashSet<>();
        while (current != null && seen.add(current)) {
            if (current instanceof IllegalArgumentException
                    || current instanceof InvalidModuleDescriptorException
                    || current instanceof java.util.zip.ZipException) {
                return true;
            }
            String currentMessage = current.getMessage();
            if (currentMessage != null) {
                for (String fragment : PERMANENT_MESSAGE_FRAGMENTS) {
                    if (currentMessage.contains(fragment)) {
                        return true;
                    }
                }
            }
            current = current.getCause();
        }
        String message = error.getMessage();
        if (message != null) {
            java.util.regex.Matcher matcher = STATUS_PATTERN.matcher(message);
            if (matcher.find()) {
                int status = Integer.parseInt(matcher.group(1));
                return status == 404 || status == 410;
            }
        }
        return false;
    }

    private static String failureMessage(Throwable error) {
        StringBuilder builder = new StringBuilder();
        appendThrowableLine(builder, error);
        return ScannedEntry.sanitize(builder.toString());
    }

    private static void logFailure(Coordinate coordinate, Throwable error) {
        StringBuilder builder = new StringBuilder("[scan] failed: ")
                .append(coordinate.mavenPath())
                .append(" :: ");
        appendThrowableLine(builder, error);
        System.err.println(builder);
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
        verifyRobotsTxt();
        Path statePath = configuration.dataDir().resolve("state.properties");
        if (!configuration.resume()) {
            discardInflight(statePath);
        }
        State state = State.load(statePath);

        // During the first full sweep, per-module dirty tracking is suppressed
        // (a full pass touches ~every module in Maven Central, so dirty-modules.tsv
        // would balloon for no benefit). At first-pass completion we walk the
        // modules tree and regenerate current.tsv for any directory that doesn't
        // already have one - the file's existence is the progress marker, so a
        // crashed regeneration resumes naturally on the next run.
        boolean firstPass = !state.hasIndexBaseline();
        if (!firstPass) {
            drainDirtyIfPending();
        }

        Instant deadline = Instant.now().plus(configuration.budget());
        Aggregator aggregator = new Aggregator();

        while (Instant.now().isBefore(deadline)) {
            IndexProperties remote = fetchIndexProperties();
            if (state.indexChainId() != null && !Objects.equals(state.indexChainId(), remote.chainId())) {
                System.out.println("[info] Index chain rotated from " + state.indexChainId()
                        + " to " + remote.chainId() + ": resetting baseline and performing full sync.");
                state = state.withIndex(-1L, 0L, null).withIndexChunkPending(-1L);
                state.save(statePath);
            }

            ChunkPlan plan = decideNextChunk(state, remote);
            if (plan == null) {
                System.out.println("[info] Index already up to date (chain=" + remote.chainId()
                        + ", lastIncremental=" + remote.lastIncremental() + ").");
                break;
            }

            if (plan.mode() == SyncMode.FULL) {
                long pending = state.hasPendingFullScan()
                        ? Math.max(state.indexChunkPending(), remote.lastIncremental())
                        : remote.lastIncremental();
                state = state.withIndexChunkPending(pending);
                state.save(statePath);
            }

            boolean chunkFirstPass = !state.hasIndexBaseline();
            Result chunkResult = runStreamingChunk(state, statePath, plan.uri(), remote, plan.mode());
            aggregator.add(chunkResult);
            if (!chunkResult.chunkComplete()) {
                // If runStreamingChunk reset the baseline (incremental 404 with
                // allow-rebaseline=true), the next iteration's decideNextChunk
                // will pick FULL; loop instead of bailing out.
                State reloaded = State.load(statePath);
                if (!chunkFirstPass && !reloaded.hasIndexBaseline()) {
                    state = reloaded;
                    continue;
                }
                System.out.println("[info] Chunk not yet complete; deferring current.tsv regeneration ("
                        + dirtyModules.size() + " module(s) pending).");
                return aggregator.finish(false);
            }

            if (chunkFirstPass) {
                regenerateMissingForFirstPass();
            }

            long chunkApplied = (plan.mode() == SyncMode.FULL)
                    ? State.load(statePath).indexChunkPending()
                    : plan.incrementalNumber();
            state = State.load(statePath)
                    .withIndex(chunkApplied, remote.timestamp(), remote.chainId());
            if (plan.mode() == SyncMode.FULL) {
                state = state.withIndexChunkPending(-1L);
            }
            state.save(statePath);
            drainDirty();
            System.out.println("[info] Chain advanced: chainId=" + remote.chainId()
                    + ", lastApplied=" + chunkApplied
                    + " (mode=" + plan.mode() + ")");
        }

        return aggregator.finish(true);
    }

    private record ChunkPlan(SyncMode mode, URI uri, long incrementalNumber) {
    }

    private ChunkPlan decideNextChunk(State state, IndexProperties remote) {
        if (state.hasPendingFullScan()) {
            return new ChunkPlan(SyncMode.FULL,
                    configuration.indexBaseUri().resolve(INDEX_FILE),
                    -1L);
        }
        if (!state.hasIndexBaseline() || state.indexChainId() == null) {
            return new ChunkPlan(SyncMode.FULL,
                    configuration.indexBaseUri().resolve(INDEX_FILE),
                    -1L);
        }
        long next = state.indexChunkLastApplied() + 1L;
        if (next > remote.lastIncremental()) {
            return null;
        }
        return new ChunkPlan(SyncMode.INCREMENTAL,
                configuration.indexBaseUri().resolve(INCREMENTAL_PREFIX + next + INCREMENTAL_SUFFIX),
                next);
    }

    private final class Aggregator {

        private long processed;
        private long named;
        private long automatic;
        private long failed;
        private SyncMode lastMode = SyncMode.UP_TO_DATE;

        void add(Result r) {
            processed += r.processed();
            named += r.named();
            automatic += r.automatic();
            failed += r.failed();
            if (r.syncMode() != SyncMode.UP_TO_DATE) {
                lastMode = r.syncMode();
            }
        }

        Result finish(boolean chunkComplete) {
            return new Result(processed, named, automatic, failed, chunkComplete, lastMode, snapshotFailures());
        }
    }

    private void drainDirtyIfPending() throws IOException {
        if (dirtyModules.isEmpty()) {
            return;
        }
        System.out.println("[info] Resuming current.tsv regeneration from previous run ("
                + dirtyModules.size() + " module(s) pending).");
        drainDirty();
    }

    private void drainDirty() throws IOException {
        if (dirtyModules.isEmpty()) {
            return;
        }
        int total = dirtyModules.size();
        System.out.println("[info] Regenerating current.tsv for " + total + " module(s)...");
        long progress = 0L;
        for (String moduleName : dirtyModules.snapshot()) {
            store.regenerate(moduleName);
            dirtyModules.remove(moduleName);
            progress++;
        }
        System.out.println("[info] current.tsv regeneration complete: " + progress + " module(s).");
    }

    /**
     * Handles a 404/410 on an incremental fetch: we've fallen off the index
     * retention window (Central keeps only the last ~30 incrementals). The
     * data/ tree remains valid - we just need to re-baseline against the
     * current main index. When {@code allowRebaseline} is enabled the state's
     * baseline is reset so the outer loop picks FULL on the next iteration;
     * otherwise we fail loudly and point at the property.
     */
    private void handleIncrementalNotFound(Path statePath, URI indexUri, Throwable error) throws IOException {
        if (!configuration.allowRebaseline()) {
            System.err.println("[error] Incremental " + indexUri + " returned " + error.getMessage());
            System.err.println("[error] The crawler has fallen behind the Central index retention window.");
            System.err.println("[error] Recovery requires a fresh FULL re-baseline of the index.");
            System.err.println("[error] Enable this by setting -Djenesis.crawler.allow.rebaseline=true.");
            System.err.println("[error] Existing data/ contents remain valid; scannedStore short-circuits already-scanned coordinates.");
            return;
        }
        System.err.println("[WARN] !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.err.println("[WARN] FELL OFF THE INDEX RETENTION WINDOW: " + indexUri + " -> " + error.getMessage());
        System.err.println("[WARN] RESETTING INDEX BASELINE; THE NEXT ITERATION WILL FETCH A FRESH FULL.");
        System.err.println("[WARN] data/ CONTENTS REMAIN VALID; scannedStore SKIPS ALREADY-SCANNED COORDINATES.");
        System.err.println("[WARN] !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        State reset = State.load(statePath).withIndex(-1L, 0L, null).withIndexChunkPending(-1L);
        reset.save(statePath);
    }

    /**
     * Regenerates current.tsv for every module under the store that doesn't
     * already have one. Called once at first-pass completion (when dirty
     * tracking was suppressed during the sweep), before the index baseline is
     * updated. The existence of a current*.tsv per directory acts as the
     * progress marker: a mid-regeneration crash leaves baseline unset, the
     * next run re-enters the first-pass branch, the sweep re-runs quickly
     * (scannedStore skips every already-scanned coordinate), and regeneration
     * resumes from the directories still missing a current.tsv.
     */
    private void regenerateMissingForFirstPass() throws IOException {
        System.out.println("[info] First pass complete: regenerating missing current.tsv files...");
        long count = store.regenerateMissing();
        System.out.println("[info] First-pass regeneration complete: " + count + " module(s) written.");
    }

    private void discardInflight(Path statePath) throws IOException {
        dirtyModules.clear();
        boolean removedAnything = Files.deleteIfExists(statePath);
        System.out.println(removedAnything
                ? "[info] Resume disabled: discarded existing state; starting fresh."
                : "[info] Resume disabled: no existing state to discard.");
    }

    private void verifyRobotsTxt() throws IOException {
        Set<String> seenAuthorities = new HashSet<>();
        for (URI baseUri : List.of(configuration.indexBaseUri(), configuration.artifactBaseUri())) {
            String authority = baseUri.getScheme() + "://" + baseUri.getAuthority();
            if (!seenAuthorities.add(authority)) {
                continue;
            }
            RobotsTxt.Rules rules;
            try {
                rules = RobotsTxt.fetch(fetcher, baseUri);
            } catch (IOException e) {
                System.err.println("[info] robots.txt fetch failed for " + authority + " (" + e.getMessage() + "); continuing without restrictions");
                continue;
            }
            String path = baseUri.getRawPath();
            if (path == null || path.isEmpty()) {
                path = "/";
            }
            if (!rules.allows(path)) {
                throw new IOException("robots.txt for " + authority + " disallows " + path + " for " + RobotsTxt.agentToken(Fetcher.USER_AGENT));
            }
            System.out.println("[info] robots.txt for " + authority + " permits " + path);
        }
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

    private Result runStreamingChunk(State state,
                                     Path statePath,
                                     URI indexUri,
                                     IndexProperties remote,
                                     SyncMode mode) throws IOException {
        System.out.println("[info] Streaming " + mode + " sync from " + indexUri);
        State streamingState = state.withSweepStartedAt(Instant.now());
        streamingState.save(statePath);

        Predicate<Coordinate> producerFilter = candidate -> isInteresting(candidate) && !scannedStore.contains(candidate);
        // Periodic diagnostic so a hung or slow run is visible without attaching jcmd. The
        // ticker fires on the producer thread once per IndexStream.DEFAULT_LOG_EVERY-aligned
        // window; we emit a [status] line every fifth tick.
        LongConsumer onProgressTick = recordsSeen -> {
            if (recordsSeen % 50_000L == 0L) {
                Runtime rt = Runtime.getRuntime();
                long heapUsedMB = (rt.totalMemory() - rt.freeMemory()) / (1024L * 1024L);
                long heapMaxMB = rt.maxMemory() / (1024L * 1024L);
                System.out.println("[status] heapUsedMB=" + heapUsedMB + "/" + heapMaxMB
                        + " scannedDirty=" + scannedStore.pendingArtifacts()
                        + " moduleDirty=" + store.pendingFiles()
                        + " dirtyModules=" + dirtyModules.size());
            }
        };
        try (IndexStream stream = new IndexStream(fetcher, producerFilter, onProgressTick)) {
            stream.start(List.of(indexUri));
            try (StreamingBatchSource source = new StreamingBatchSource(stream, configuration.concurrency())) {
                Result result = process(source, streamingState, statePath, mode, stream::recordsProduced);
                stream.close();
                if (stream.error() != null) {
                    Throwable error = stream.error();
                    if (mode == SyncMode.INCREMENTAL && isNotFound(error)) {
                        handleIncrementalNotFound(statePath, indexUri, error);
                    } else {
                        System.err.println("[info] Producer aborted mid-stream: "
                                + error.getClass().getSimpleName()
                                + ": " + error.getMessage()
                                + " (on-disk state through last checkpoint is consistent; next run will re-sync)");
                    }
                    return new Result(result.processed(), result.named(), result.automatic(), result.failed(),
                            false, mode, result.failureBreakdown());
                }
                return result;
            }
        } catch (InterruptedException interrupted) {
            Thread.currentThread().interrupt();
            throw new IOException("Interrupted while streaming", interrupted);
        }
    }

    private Result process(BatchSource source,
                           State state,
                           Path statePath,
                           SyncMode syncMode,
                           LongSupplier emittedSoFar) throws IOException, InterruptedException {
        Instant runStart = Instant.now();
        Instant deadline = runStart.plus(configuration.budget());
        // Captured for the lifetime of this chunk: the baseline only flips
        // after the chunk completes (back in run()), so the value is stable
        // here even though `state` is reassigned by checkpoint().
        boolean trackDirty = state.hasIndexBaseline();
        long processed = 0L;
        long named = 0L;
        long automatic = 0L;
        long nonmodular = 0L;
        long failed = 0L;
        long sinceCheckpoint = 0L;
        boolean exhausted = false;
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            while (Instant.now().isBefore(deadline) && !exhausted) {
                BatchSource.Batch batch = source.next();
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
                    Coordinate coordinate = outcome.coordinate();
                    if (outcome.error() != null) {
                        failed++;
                        logFailure(coordinate, outcome.error());
                        recordFailure(outcome.error());
                        if (isPermanentFailure(outcome.error())) {
                            scannedStore.markFailed(coordinate, failureMessage(outcome.error()));
                        }
                        continue;
                    }
                    try {
                        if (outcome.module().isPresent()) {
                            ScannedModule module = outcome.module().get();
                            boolean recorded;
                            synchronized (store) {
                                recorded = store.record(module.name(), module.type(), coordinate);
                            }
                            if (recorded) {
                                if (module.type() == ModuleType.NAMED) {
                                    named++;
                                } else {
                                    automatic++;
                                }
                                if (trackDirty) {
                                    dirtyModules.add(module.name());
                                }
                            }
                        } else {
                            nonmodular++;
                        }
                        scannedStore.markOk(coordinate);
                    } catch (RuntimeException unexpected) {
                        failed++;
                        logFailure(coordinate, unexpected);
                        recordFailure(unexpected);
                        if (isPermanentFailure(unexpected)) {
                            scannedStore.markFailed(coordinate, failureMessage(unexpected));
                        }
                    }
                }
                processed += batch.coordinates().size();
                sinceCheckpoint += batch.coordinates().size();
                if (sinceCheckpoint >= configuration.checkpointEvery()) {
                    state = checkpoint(state, statePath, processed, named, automatic, nonmodular, failed, syncMode, emittedSoFar.getAsLong(), runStart);
                    sinceCheckpoint = 0L;
                }
            }
            state = checkpoint(state, statePath, processed, named, automatic, nonmodular, failed, syncMode, emittedSoFar.getAsLong(), runStart);
        }
        return new Result(processed, named, automatic, failed, exhausted, syncMode, snapshotFailures());
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

    private State checkpoint(State state, Path statePath, long processed, long named, long automatic, long nonmodular, long failed, SyncMode syncMode, long emitted, Instant runStart) throws IOException {
        synchronized (store) {
            store.flush();
        }
        scannedStore.flush();
        state.save(statePath);
        long elapsedSeconds = Math.max(1L, Duration.between(runStart, Instant.now()).toSeconds());
        long rate = processed / elapsedSeconds;
        System.out.println("[artifacts] processed=" + processed + " named=" + named + " automatic=" + automatic
                + " nonmodular=" + nonmodular + " failed=" + failed
                + " emitted=" + emitted
                + " rate=" + rate + "/s");
        checkpointListener.onCheckpoint(state, new CheckpointListener.Statistics(processed, named, automatic, failed, syncMode));
        return state;
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
            } catch (InvalidModuleDescriptorException malformed) {
                // Malformed module-info: re-reading via the cached-tail path will fail
                // the same way, so surface it directly instead of falling through.
                return new ScanOutcome(coordinate, Optional.empty(), malformed);
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
