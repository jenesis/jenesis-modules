package build.jenesis.crawler;

import module java.base;
import build.jenesis.crawler.fetch.Scanner;
import build.jenesis.crawler.index.FailedScannedBatchSource;
import build.jenesis.crawler.publish.CheckpointListener;
import build.jenesis.crawler.publish.GitPublisher;
import build.jenesis.crawler.publish.StatusWriter;
import build.jenesis.crawler.store.ModuleStore;

/**
 * Standalone tool that re-scans the coordinates currently recorded as permanent failures
 * in {@code data/scanned/}, optionally filtered by regex match against the recorded error
 * message. Reuses the regular crawler scanner pipeline (same {@link Scanner}, same
 * {@link ModuleStore} flush invariant, same checkpointing, same git publisher), but
 * bypasses the index streamer - no {@code nexus-maven-repository-index.gz} fetch, no
 * 24-minute producer warmup. Index chain state in {@code state.properties} is not modified.
 *
 * Usage:
 *   RetryFailed &lt;artifact-base-uri&gt; &lt;error-regex&gt; [&lt;error-regex&gt; ...]
 *
 * Each regex is matched against the failure message with {@link Matcher#find()} (so
 * substring matches work without anchoring). A coordinate is queued for re-scan if its
 * message matches at least one of the supplied regexes. Pass {@code .*} to retry every
 * failure.
 *
 * The crawler's {@code reprocessFailed} configuration flag is set to {@code true} for the
 * scope of this tool's run so that an existing failure record doesn't block the re-scan
 * via the consumer-side {@code scannedStore.contains} check.
 */
public final class RetryFailed {

    public static final String PROP_DATA = Crawl.PROP_DATA;
    public static final String PROP_BUDGET_MINUTES = Crawl.PROP_BUDGET_MINUTES;
    public static final String PROP_CONCURRENCY = Crawl.PROP_CONCURRENCY;
    public static final String PROP_TAIL_SIZE = Crawl.PROP_TAIL_SIZE;
    public static final String PROP_CHECKPOINT_EVERY = Crawl.PROP_CHECKPOINT_EVERY;
    public static final String PROP_SMALL_JAR_THRESHOLD = Crawl.PROP_SMALL_JAR_THRESHOLD;
    public static final String PROP_GIT_PUBLISH = Crawl.PROP_GIT_PUBLISH;
    public static final String PROP_GIT_WORK_DIR = Crawl.PROP_GIT_WORK_DIR;
    public static final String PROP_GIT_PUSH_EVERY = Crawl.PROP_GIT_PUSH_EVERY;

    private RetryFailed() {
    }

    public static void main(String[] arguments) throws IOException {
        if (arguments.length == 0 || arguments[0].equals("--help") || arguments[0].equals("-h")) {
            printUsage();
            if (arguments.length == 0) {
                throw new IllegalArgumentException("Missing required <artifact-base-uri> positional argument");
            }
            return;
        }
        if (arguments.length < 2) {
            printUsage();
            throw new IllegalArgumentException("Missing required <error-regex> argument(s)");
        }
        URI artifactBase = URI.create(arguments[0]);
        List<Pattern> patterns = new ArrayList<>(arguments.length - 1);
        for (int i = 1; i < arguments.length; i++) {
            patterns.add(Pattern.compile(arguments[i]));
        }

        Crawler.Configuration configuration = buildConfiguration(artifactBase, patterns);
        System.out.println("[info] Configuration:");
        System.out.println("[info]   dataDir=" + configuration.dataDir().toAbsolutePath());
        System.out.println("[info]   budget=" + configuration.budget());
        System.out.println("[info]   concurrency=" + configuration.concurrency());
        System.out.println("[info]   artifactBase=" + configuration.artifactBaseUri());
        System.out.println("[info]   regexes=" + patterns);

        Path scannedRoot = configuration.dataDir().resolve("scanned");
        FailedScannedBatchSource source = FailedScannedBatchSource.from(scannedRoot, patterns, configuration.concurrency());
        System.out.println("[info] Found " + source.total() + " failed coordinate(s) matching the supplied regex(es)");
        if (source.total() == 0) {
            System.out.println("[info] Nothing to re-scan; exiting.");
            return;
        }

        Crawler.Result result;
        try (Crawler crawler = new Crawler(configuration)) {
            configureListener(crawler, configuration);
            result = crawler.scan(source);
        }
        System.out.println("[info] processed=" + result.processed()
                + " named=" + result.named()
                + " automatic=" + result.automatic()
                + " failed=" + result.failed()
                + " chunkComplete=" + result.chunkComplete());
        printFailureBreakdown(result);
    }

    private static Crawler.Configuration buildConfiguration(URI artifactBase, List<Pattern> patterns) {
        Crawler.Configuration base = Crawler.Configuration.defaults(artifactBase, artifactBase);
        Path dataDir = property(PROP_DATA).map(Path::of).orElse(base.dataDir());
        Duration budget = property(PROP_BUDGET_MINUTES).map(Long::parseLong).map(Duration::ofMinutes).orElse(base.budget());
        int concurrency = property(PROP_CONCURRENCY).map(Integer::parseInt).orElse(base.concurrency());
        int tailSize = property(PROP_TAIL_SIZE).map(Integer::parseInt).orElse(base.tailSize());
        long checkpointEvery = property(PROP_CHECKPOINT_EVERY).map(Long::parseLong).orElse(base.checkpointEvery());
        long smallJarThreshold = property(PROP_SMALL_JAR_THRESHOLD).map(Long::parseLong).orElse(base.smallJarThreshold());
        return new Crawler.Configuration(
                artifactBase, artifactBase, dataDir, budget, concurrency, tailSize, checkpointEvery, smallJarThreshold,
                /* resume */ true,
                /* reprocessFailed */ true,
                /* allowRebaseline */ false);
    }

    private static void configureListener(Crawler crawler, Crawler.Configuration configuration) {
        CheckpointListener listener = new StatusWriter(configuration.dataDir().resolve("STATUS.md"));
        boolean publish = property(PROP_GIT_PUBLISH).map(value -> parseBoolean(value, PROP_GIT_PUBLISH)).orElse(false);
        if (publish) {
            Path workingDirectory = property(PROP_GIT_WORK_DIR).map(Path::of).orElse(Path.of("."));
            int pushEvery = property(PROP_GIT_PUSH_EVERY).map(Integer::parseInt).orElse(GitPublisher.DEFAULT_PUSH_EVERY);
            Path dataDir = configuration.dataDir().toAbsolutePath();
            Path workingAbsolute = workingDirectory.toAbsolutePath();
            Path relative = workingAbsolute.relativize(dataDir);
            listener = listener.andThen(new GitPublisher(workingAbsolute, List.of(relative.toString()), pushEvery));
            System.out.println("[info] Publishing checkpoints via git in " + workingAbsolute + " (pushEvery=" + pushEvery + ")");
        }
        crawler.withCheckpointListener(listener);
    }

    private static Optional<String> property(String name) {
        String value = System.getProperty(name);
        return value == null || value.isBlank() ? Optional.empty() : Optional.of(value.trim());
    }

    private static boolean parseBoolean(String value, String source) {
        return switch (value.toLowerCase(Locale.ROOT)) {
            case "true", "1", "yes" -> true;
            case "false", "0", "no" -> false;
            default -> throw new IllegalArgumentException("Expected true/false for " + source + ", got: " + value);
        };
    }

    private static void printFailureBreakdown(Crawler.Result result) {
        if (result.failureBreakdown().isEmpty()) {
            return;
        }
        System.out.println("[info] Failure breakdown (" + result.failed() + " total):");
        result.failureBreakdown().entrySet().stream()
                .sorted((a, b) -> Long.compare(b.getValue().count(), a.getValue().count()))
                .forEach(entry -> {
                    System.out.println("[info]   " + entry.getKey() + ": " + entry.getValue().count());
                    System.out.println("[info]     sample: " + entry.getValue().sampleMessage());
                });
    }

    private static void printUsage() {
        System.out.println("Usage: RetryFailed <artifact-base-uri> <error-regex> [<error-regex> ...]");
        System.out.println();
        System.out.println("Re-scans every coordinate currently marked as permanently failed in <dataDir>/scanned/");
        System.out.println("whose recorded error message matches at least one of the supplied regexes.");
        System.out.println();
        System.out.println("System properties (all share the keys used by Crawl):");
        System.out.println("  -D" + PROP_DATA + "=<dir>                 Data directory (default: 'data')");
        System.out.println("  -D" + PROP_BUDGET_MINUTES + "=<minutes>   Wall-clock budget (default: 180)");
        System.out.println("  -D" + PROP_CONCURRENCY + "=<n>            Concurrent fetches (default: 64)");
        System.out.println("  -D" + PROP_TAIL_SIZE + "=<bytes>          Tail-fetch size per JAR (default: 65536)");
        System.out.println("  -D" + PROP_SMALL_JAR_THRESHOLD + "=<bytes>  Whole-fetch threshold (default: 262144)");
        System.out.println("  -D" + PROP_CHECKPOINT_EVERY + "=<n>       Coordinates between checkpoints (default: 2000)");
        System.out.println("  -D" + PROP_GIT_PUBLISH + "=<true|false>   Commit + push checkpoints (default: false)");
        System.out.println("  -D" + PROP_GIT_WORK_DIR + "=<dir>         Working directory for git operations");
        System.out.println("  -D" + PROP_GIT_PUSH_EVERY + "=<n>         Push every N checkpoints (default: 1)");
    }
}
