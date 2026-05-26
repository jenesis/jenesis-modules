package build.jenesis.crawler;

import module java.base;
import build.jenesis.crawler.fetch.Fetcher;
import build.jenesis.crawler.fetch.Scanner;
import build.jenesis.crawler.index.MetadataReconcileStream;
import build.jenesis.crawler.index.StreamingBatchSource;
import build.jenesis.crawler.publish.CheckpointListener;
import build.jenesis.crawler.publish.GitPublisher;
import build.jenesis.crawler.publish.StatusWriter;

/**
 * Standalone tool that fills in versions the regular crawler couldn't see because their main-jar
 * records are missing from the Maven Central Nexus index. It walks {@code data/scanned/},
 * downloads {@code maven-metadata.xml} for every {@code (groupId, artifactId)} tuple, diffs the
 * version list against the local scanned tsv, and pipes the missing versions through the same
 * scanner pipeline the crawler uses - same {@link Scanner}, same {@link build.jenesis.crawler.store.ModuleStore}
 * flush invariant, same checkpointing, same {@link GitPublisher}. Index chain state in
 * {@code state.properties} is not touched.
 *
 * Usage:
 *   ReconcileMetadata &lt;artifact-base-uri&gt;
 *
 * The artifact-base URI is also used to fetch {@code maven-metadata.xml}; Maven Central mirrors
 * (including the GCS bucket) serve both binary jars and per-artifact metadata XML under the
 * same prefix, so one URI is enough.
 */
public final class ReconcileMetadata {

    public static final String PROP_DATA = Crawl.PROP_DATA;
    public static final String PROP_BUDGET_MINUTES = Crawl.PROP_BUDGET_MINUTES;
    public static final String PROP_CONCURRENCY = Crawl.PROP_CONCURRENCY;
    public static final String PROP_TAIL_SIZE = Crawl.PROP_TAIL_SIZE;
    public static final String PROP_CHECKPOINT_EVERY = Crawl.PROP_CHECKPOINT_EVERY;
    public static final String PROP_SMALL_JAR_THRESHOLD = Crawl.PROP_SMALL_JAR_THRESHOLD;
    public static final String PROP_GIT_PUBLISH = Crawl.PROP_GIT_PUBLISH;
    public static final String PROP_GIT_WORK_DIR = Crawl.PROP_GIT_WORK_DIR;
    public static final String PROP_GIT_PUSH_EVERY = Crawl.PROP_GIT_PUSH_EVERY;
    public static final String PROP_CANONICAL_TIMESTAMP_URI = Crawl.PROP_CANONICAL_TIMESTAMP_URI;
    public static final String PROP_METADATA_CONCURRENCY = "jenesis.reconcile.metadata.concurrency";
    public static final String PROP_BATCH_SIZE = "jenesis.reconcile.batch.size";

    public static final int DEFAULT_BATCH_SIZE = 256;

    private ReconcileMetadata() {
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
        int metadataConcurrency = property(PROP_METADATA_CONCURRENCY)
                .map(Integer::parseInt)
                .orElse(MetadataReconcileStream.DEFAULT_METADATA_CONCURRENCY);
        int batchSize = property(PROP_BATCH_SIZE).map(Integer::parseInt).orElse(DEFAULT_BATCH_SIZE);

        Crawler.Configuration configuration = buildConfiguration(artifactBase);
        System.out.println("[info] Configuration:");
        System.out.println("[info]   dataDir=" + configuration.dataDir().toAbsolutePath());
        System.out.println("[info]   budget=" + configuration.budget());
        System.out.println("[info]   scannerConcurrency=" + configuration.concurrency());
        System.out.println("[info]   metadataConcurrency=" + metadataConcurrency);
        System.out.println("[info]   batchSize=" + batchSize);
        System.out.println("[info]   artifactBase=" + configuration.artifactBaseUri());

        Path scannedRoot = configuration.dataDir().resolve("scanned");
        if (!Files.isDirectory(scannedRoot)) {
            System.out.println("[info] No scanned root at " + scannedRoot + "; nothing to reconcile.");
            return;
        }

        try (Fetcher metadataFetcher = new Fetcher();
             MetadataReconcileStream producer = new MetadataReconcileStream(
                     metadataFetcher, scannedRoot, artifactBase, metadataConcurrency);
             Crawler crawler = new Crawler(configuration)) {
            configureListener(crawler, configuration);
            producer.start();
            StreamingBatchSource source = new StreamingBatchSource(producer.queue(), batchSize,
                    StreamingBatchSource.DEFAULT_POLL_TIMEOUT);
            Crawler.Result result = crawler.scan(source);
            IOException producerError = producer.error();
            if (producerError != null) {
                System.err.println("[warn] Metadata producer failed mid-stream: "
                        + producerError.getClass().getSimpleName() + ": " + producerError.getMessage()
                        + " (scanner results above are still valid for the records that did reach the queue)");
            }
            System.out.println("[info] reconciled scanned=" + producer.filesWalked()
                    + " queued=" + producer.recordsProduced()
                    + " processed=" + result.processed()
                    + " named=" + result.named()
                    + " automatic=" + result.automatic()
                    + " failed=" + result.failed());
        }
    }

    private static Crawler.Configuration buildConfiguration(URI artifactBase) {
        Crawler.Configuration base = Crawler.Configuration.defaults(artifactBase, artifactBase);
        Path dataDir = property(PROP_DATA).map(Path::of).orElse(base.dataDir());
        Duration budget = property(PROP_BUDGET_MINUTES).map(Long::parseLong).map(Duration::ofMinutes).orElse(base.budget());
        int concurrency = property(PROP_CONCURRENCY).map(Integer::parseInt).orElse(base.concurrency());
        int tailSize = property(PROP_TAIL_SIZE).map(Integer::parseInt).orElse(base.tailSize());
        long checkpointEvery = property(PROP_CHECKPOINT_EVERY).map(Long::parseLong).orElse(base.checkpointEvery());
        long smallJarThreshold = property(PROP_SMALL_JAR_THRESHOLD).map(Long::parseLong).orElse(base.smallJarThreshold());
        URI canonicalTimestampBase = property(PROP_CANONICAL_TIMESTAMP_URI)
                .map(URI::create)
                .orElse(base.canonicalTimestampBaseUri());
        return new Crawler.Configuration(
                artifactBase, artifactBase, canonicalTimestampBase,
                dataDir, budget, concurrency, tailSize, checkpointEvery, smallJarThreshold,
                /* resume */ true,
                /* reprocessFailed */ false,
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

    private static void printUsage() {
        System.out.println("Usage: ReconcileMetadata <artifact-base-uri>");
        System.out.println();
        System.out.println("Walks every per-artifact scanned tsv under <dataDir>/scanned/, downloads");
        System.out.println("maven-metadata.xml for the (groupId, artifactId), diffs against locally");
        System.out.println("scanned main-jar versions, and re-scans whichever versions are missing.");
        System.out.println("Recovers versions whose main-jar record is absent from the Nexus index.");
        System.out.println();
        System.out.println("System properties:");
        System.out.println("  -D" + PROP_METADATA_CONCURRENCY + "=<n>  Concurrent maven-metadata.xml fetches (default: 32)");
        System.out.println("  -D" + PROP_BATCH_SIZE + "=<n>            Coordinates per scanner batch (default: 256)");
        System.out.println("  -D" + PROP_DATA + "=<dir>                 Data directory (default: 'data')");
        System.out.println("  -D" + PROP_BUDGET_MINUTES + "=<minutes>   Wall-clock budget (default: 180)");
        System.out.println("  -D" + PROP_CONCURRENCY + "=<n>            Concurrent JAR fetches (default: 64)");
        System.out.println("  -D" + PROP_TAIL_SIZE + "=<bytes>          Tail-fetch size per JAR (default: 65536)");
        System.out.println("  -D" + PROP_SMALL_JAR_THRESHOLD + "=<bytes>  Whole-fetch threshold (default: 262144)");
        System.out.println("  -D" + PROP_CHECKPOINT_EVERY + "=<n>       Coordinates between checkpoints (default: 2000)");
        System.out.println("  -D" + PROP_GIT_PUBLISH + "=<true|false>   Commit + push checkpoints (default: false)");
        System.out.println("  -D" + PROP_GIT_WORK_DIR + "=<dir>         Working directory for git operations");
        System.out.println("  -D" + PROP_GIT_PUSH_EVERY + "=<n>         Push every N checkpoints (default: 1)");
    }
}
