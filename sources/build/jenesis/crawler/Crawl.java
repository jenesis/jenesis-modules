package build.jenesis.crawler;

import module java.base;
import build.jenesis.crawler.publish.CheckpointListener;
import build.jenesis.crawler.publish.GitPublisher;
import build.jenesis.crawler.publish.StatusWriter;

public final class Crawl {

    public static final String PROP_DATA = "jenesis.crawler.data";
    public static final String PROP_BUDGET_MINUTES = "jenesis.crawler.budget";
    public static final String PROP_CONCURRENCY = "jenesis.crawler.concurrency";
    public static final String PROP_TAIL_SIZE = "jenesis.crawler.tail.size";
    public static final String PROP_CHECKPOINT_EVERY = "jenesis.crawler.checkpoint.every";
    public static final String PROP_SMALL_JAR_THRESHOLD = "jenesis.crawler.small.jar.threshold";
    public static final String PROP_RESUME = "jenesis.crawler.resume";
    public static final String PROP_REPROCESS_FAILED = "jenesis.crawler.reprocess.failed";
    public static final String PROP_ALLOW_REBASELINE = "jenesis.crawler.allow.rebaseline";
    public static final String PROP_CANONICAL_TIMESTAMP_URI = "jenesis.crawler.canonical.timestamp.uri";
    public static final String PROP_GIT_PUBLISH = "jenesis.crawler.git.publish";
    public static final String PROP_GIT_WORK_DIR = "jenesis.crawler.git.work.dir";
    public static final String PROP_GIT_PUSH_EVERY = "jenesis.crawler.git.push.every";

    private Crawl() {
    }

    public static void main(String[] arguments) throws IOException {
        if (arguments.length == 0 || arguments[0].equals("--help") || arguments[0].equals("-h")) {
            printUsage();
            if (arguments.length == 0) {
                throw new IllegalArgumentException("Missing required <artifact-base-uri> positional argument");
            }
            return;
        }
        if (arguments.length > 2) {
            throw new IllegalArgumentException("Expected at most 2 positional arguments (artifact-base [index-base]); got " + arguments.length);
        }
        URI artifactBase = URI.create(arguments[0]);
        URI indexBase = arguments.length == 2 ? URI.create(arguments[1]) : artifactBase;

        Crawler.Configuration configuration = buildConfiguration(artifactBase, indexBase);
        System.out.println("[info] Configuration:");
        System.out.println("[info]   dataDir=" + configuration.dataDir().toAbsolutePath());
        System.out.println("[info]   budget=" + configuration.budget());
        System.out.println("[info]   concurrency=" + configuration.concurrency());
        System.out.println("[info]   indexBase=" + configuration.indexBaseUri());
        System.out.println("[info]   artifactBase=" + configuration.artifactBaseUri());
        System.out.println("[info]   resume=" + configuration.resume());

        Crawler.Result result;
        try (Crawler crawler = new Crawler(configuration)) {
            configureListener(crawler, configuration);
            result = crawler.run();
        }
        System.out.println("[info] syncMode=" + result.syncMode()
                + " processed=" + result.processed()
                + " named=" + result.named()
                + " automatic=" + result.automatic()
                + " failed=" + result.failed()
                + " chunkComplete=" + result.chunkComplete());
        if (!result.chunkComplete()) {
            System.out.println("[info] Current index chunk did not finish within budget; resume with another run.");
        }
        writeStepSummary(result);
    }

    private static Crawler.Configuration buildConfiguration(URI artifactBase, URI indexBase) {
        Crawler.Configuration base = Crawler.Configuration.defaults(artifactBase, indexBase);
        Path dataDir = property(PROP_DATA).map(Path::of).orElse(base.dataDir());
        Duration budget = property(PROP_BUDGET_MINUTES).map(Long::parseLong).map(Duration::ofMinutes).orElse(base.budget());
        int concurrency = property(PROP_CONCURRENCY).map(Integer::parseInt).orElse(base.concurrency());
        int tailSize = property(PROP_TAIL_SIZE).map(Integer::parseInt).orElse(base.tailSize());
        long checkpointEvery = property(PROP_CHECKPOINT_EVERY).map(Long::parseLong).orElse(base.checkpointEvery());
        long smallJarThreshold = property(PROP_SMALL_JAR_THRESHOLD).map(Long::parseLong).orElse(base.smallJarThreshold());
        boolean resume = property(PROP_RESUME).map(value -> parseBoolean(value, PROP_RESUME)).orElse(base.resume());
        boolean reprocessFailed = property(PROP_REPROCESS_FAILED).map(value -> parseBoolean(value, PROP_REPROCESS_FAILED)).orElse(base.reprocessFailed());
        boolean allowRebaseline = property(PROP_ALLOW_REBASELINE).map(value -> parseBoolean(value, PROP_ALLOW_REBASELINE)).orElse(base.allowRebaseline());
        URI canonicalTimestampBase = property(PROP_CANONICAL_TIMESTAMP_URI)
                .map(URI::create)
                .orElse(base.canonicalTimestampBaseUri());
        return new Crawler.Configuration(indexBase, artifactBase, canonicalTimestampBase, dataDir, budget, concurrency, tailSize, checkpointEvery, smallJarThreshold, resume, reprocessFailed, allowRebaseline);
    }

    private static Optional<String> property(String name) {
        String value = System.getProperty(name);
        return value == null || value.isBlank() ? Optional.empty() : Optional.of(value.trim());
    }

    static boolean parseBoolean(String value, String source) {
        return switch (value.toLowerCase(Locale.ROOT)) {
            case "true", "1", "yes" -> true;
            case "false", "0", "no" -> false;
            default -> throw new IllegalArgumentException("Expected true/false for " + source + ", got: " + value);
        };
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

    private static void writeStepSummary(Crawler.Result result) {
        String path = System.getenv("GITHUB_STEP_SUMMARY");
        if (path == null || path.isBlank()) {
            return;
        }
        StringBuilder summary = new StringBuilder();
        summary.append("## Crawl run summary\n\n");
        summary.append("| Field | Value |\n");
        summary.append("|---|---|\n");
        summary.append("| Sync mode | `").append(result.syncMode()).append("` |\n");
        summary.append("| Coordinates processed this run | ").append(result.processed()).append(" |\n");
        summary.append("| Named modules recorded | ").append(result.named()).append(" |\n");
        summary.append("| Automatic modules recorded | ").append(result.automatic()).append(" |\n");
        summary.append("| Failed fetches | ").append(result.failed()).append(" |\n");
        summary.append("| Chunk complete | ").append(result.chunkComplete() ? "yes" : "no, resume next run").append(" |\n");
        if (!result.failureBreakdown().isEmpty()) {
            summary.append("\n### Failure breakdown\n\n");
            summary.append("| Category | Count | Sample |\n");
            summary.append("|---|---|---|\n");
            result.failureBreakdown().entrySet().stream()
                    .sorted((a, b) -> Long.compare(b.getValue().count(), a.getValue().count()))
                    .forEach(entry -> summary.append("| ").append(entry.getKey())
                            .append(" | ").append(entry.getValue().count())
                            .append(" | `").append(entry.getValue().sampleMessage().replace("|", "\\|")).append("` |\n"));
        }
        try {
            Files.writeString(Path.of(path), summary.toString(), StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("[info] Failed to write step summary: " + e.getMessage());
        }
    }

    private static void printUsage() {
        System.out.println("Usage: java build.jenesis.crawler.Crawl <artifact-base-uri> [<index-base-uri>]");
        System.out.println();
        System.out.println("The artifact-base URI is where JARs are range-fetched from. If <index-base-uri> is");
        System.out.println("omitted, the same URI is used for the Lucene index too. There are no built-in");
        System.out.println("defaults - the caller picks what to crawl.");
        System.out.println();
        System.out.println("Optional configuration is taken from system properties (-D...):");
        System.out.println("  -D" + PROP_DATA + "=<dir>                       Data directory (default 'data')");
        System.out.println("  -D" + PROP_BUDGET_MINUTES + "=<n>                     Wall-clock budget in minutes (default 180)");
        System.out.println("  -D" + PROP_CONCURRENCY + "=<n>                Concurrent artifact fetches (default 64)");
        System.out.println("  -D" + PROP_TAIL_SIZE + "=<n>                Bytes range-fetched from each JAR tail");
        System.out.println("  -D" + PROP_CHECKPOINT_EVERY + "=<n>        Coordinates between checkpoints");
        System.out.println("  -D" + PROP_SMALL_JAR_THRESHOLD + "=<n>  JAR size cap for one-shot fetch");
        System.out.println("  -D" + PROP_RESUME + "=<true|false>             Keep existing state.properties on startup (default true; false discards it)");
        System.out.println("  -D" + PROP_REPROCESS_FAILED + "=<true|false>    Re-scan coordinates whose previous scan recorded a permanent");
        System.out.println("                                                  failure (default false; recovers from scanner bugs).");
        System.out.println("  -D" + PROP_ALLOW_REBASELINE + "=<true|false>   Allow recovery when an incremental 404s because we fell off the");
        System.out.println("                                                  Central retention window: reset the baseline and re-FULL on the");
        System.out.println("                                                  next iteration (default false; without this the crawler fails fast).");
        System.out.println("  -D" + PROP_CANONICAL_TIMESTAMP_URI + "=<uri>");
        System.out.println("                                                  Maven-repo base used to HEAD the canonical Last-Modified when the");
        System.out.println("                                                  primary artifact fetch comes from a mirror that rewrites mtimes");
        System.out.println("                                                  (notably the GCS mirror for pre-2019 imports). Defaults to the");
        System.out.println("                                                  primary <artifact-base-uri>, which disables the fallback. Set to a");
        System.out.println("                                                  different repo (e.g. https://repo.maven.apache.org/maven2/) to opt in.");
        System.out.println("  -D" + PROP_GIT_PUBLISH + "=<true|false>       Commit + push checkpoints via git (default false)");
        System.out.println("  -D" + PROP_GIT_WORK_DIR + "=<dir>             Git working tree (default '.')");
        System.out.println("  -D" + PROP_GIT_PUSH_EVERY + "=<n>            Push every n checkpoints (default 1)");
    }
}
