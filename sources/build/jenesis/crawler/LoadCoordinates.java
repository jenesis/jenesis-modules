package build.jenesis.crawler;

import module java.base;
import build.jenesis.crawler.fetch.Fetcher;
import build.jenesis.crawler.index.MetadataReconcileStream;
import build.jenesis.crawler.index.StreamingBatchSource;
import build.jenesis.crawler.publish.CheckpointListener;
import build.jenesis.crawler.store.DirtyModules;

/**
 * Standalone tool that seeds one or more explicit {@code groupId:artifactId} coordinates that the
 * regular crawler never saw because their records are missing from the Maven Central Nexus index
 * entirely (which lags freshly published artifacts, sometimes for days). For each coordinate it
 * downloads {@code maven-metadata.xml}, reads the full version list, and pipes every not-yet-scanned
 * version through the same scanner pipeline the crawler uses - reusing {@link MetadataReconcileStream}
 * (in its explicit-coordinate mode), the same {@link build.jenesis.crawler.fetch.Scanner}, and the
 * same {@link build.jenesis.crawler.store.ModuleStore} flush invariant.
 *
 * <p>Unlike {@link ReconcileMetadata} and the crawler, this is a targeted manual seed, not a crawl,
 * so it deliberately keeps a minimal footprint: it installs {@link CheckpointListener#NOOP} (no
 * {@code STATUS.md}), does not commit or push, and restores the crawl-wide bookkeeping files
 * ({@code state.properties}, {@code STATUS.md}, {@code dirty-modules.tsv}) to exactly what it found,
 * so the only changes it leaves on disk are the coordinate's own {@code data/scanned/} and
 * {@code data/modules/} rows. The index chain resume point is never advanced, and re-running is
 * idempotent (already-scanned versions are skipped). Regenerate the resolved views for the loaded
 * modules afterwards ({@link Regenerate}, then {@link ModuleMaven} if the name-to-coordinate map is
 * consumed downstream).
 *
 * Usage:
 *   LoadCoordinates &lt;artifact-base-uri&gt; &lt;groupId:artifactId&gt; [&lt;groupId:artifactId&gt; ...]
 *
 * The artifact-base URI is also used to fetch {@code maven-metadata.xml}; Maven Central mirrors
 * (including the GCS bucket) serve both binary jars and per-artifact metadata XML under the same
 * prefix, so one URI is enough.
 */
public final class LoadCoordinates {

    public static final String PROP_DATA = Crawl.PROP_DATA;
    public static final String PROP_BUDGET_MINUTES = Crawl.PROP_BUDGET_MINUTES;
    public static final String PROP_CONCURRENCY = Crawl.PROP_CONCURRENCY;
    public static final String PROP_TAIL_SIZE = Crawl.PROP_TAIL_SIZE;
    public static final String PROP_CHECKPOINT_EVERY = Crawl.PROP_CHECKPOINT_EVERY;
    public static final String PROP_SMALL_JAR_THRESHOLD = Crawl.PROP_SMALL_JAR_THRESHOLD;
    public static final String PROP_CANONICAL_TIMESTAMP_URI = Crawl.PROP_CANONICAL_TIMESTAMP_URI;
    public static final String PROP_METADATA_CONCURRENCY = ReconcileMetadata.PROP_METADATA_CONCURRENCY;
    public static final String PROP_BATCH_SIZE = ReconcileMetadata.PROP_BATCH_SIZE;

    private LoadCoordinates() {
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
            throw new IllegalArgumentException(
                    "Expected <artifact-base-uri> followed by at least one <groupId:artifactId>; got "
                            + arguments.length + " argument(s)");
        }
        URI artifactBase = URI.create(arguments[0]);
        List<String> coordinates = List.of(Arrays.copyOfRange(arguments, 1, arguments.length));
        int metadataConcurrency = property(PROP_METADATA_CONCURRENCY)
                .map(Integer::parseInt)
                .orElse(MetadataReconcileStream.DEFAULT_METADATA_CONCURRENCY);
        int batchSize = property(PROP_BATCH_SIZE).map(Integer::parseInt).orElse(ReconcileMetadata.DEFAULT_BATCH_SIZE);

        Crawler.Configuration configuration = buildConfiguration(artifactBase);
        System.out.println("[info] Configuration:");
        System.out.println("[info]   dataDir=" + configuration.dataDir().toAbsolutePath());
        System.out.println("[info]   budget=" + configuration.budget());
        System.out.println("[info]   scannerConcurrency=" + configuration.concurrency());
        System.out.println("[info]   metadataConcurrency=" + metadataConcurrency);
        System.out.println("[info]   batchSize=" + batchSize);
        System.out.println("[info]   artifactBase=" + configuration.artifactBaseUri());
        System.out.println("[info]   coordinates=" + coordinates);

        Path dataDir = configuration.dataDir();
        Path scannedRoot = dataDir.resolve("scanned");
        // Snapshot the crawl-wide bookkeeping files: crawler.scan() stamps state.properties with a
        // fresh sweep start and the ModuleStore drops dirty-modules.tsv, but a targeted seed must
        // leave those exactly as it found them - only the coordinate's data rows are ours to change.
        List<Preserved> preserved = List.of(
                Preserved.capture(dataDir.resolve("state.properties")),
                Preserved.capture(dataDir.resolve("STATUS.md")),
                Preserved.capture(dataDir.resolve(DirtyModules.FILE_NAME)));

        try (Fetcher metadataFetcher = new Fetcher();
             MetadataReconcileStream producer = new MetadataReconcileStream(
                     metadataFetcher, scannedRoot, artifactBase, metadataConcurrency, coordinates);
             Crawler crawler = new Crawler(configuration)) {
            crawler.withCheckpointListener(CheckpointListener.NOOP);
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
            System.out.println("[info] loaded coordinates=" + producer.filesWalked()
                    + " queued=" + producer.recordsProduced()
                    + " processed=" + result.processed()
                    + " named=" + result.named()
                    + " automatic=" + result.automatic()
                    + " failed=" + result.failed());
        } finally {
            for (Preserved snapshot : preserved) {
                snapshot.restore();
            }
        }
        System.out.println("[info] Loaded audit rows only; regenerate resolved views with "
                + "Regenerate (and ModuleMaven for the name-to-coordinate map) before use.");
    }

    private record Preserved(Path path, byte[] original) {

        static Preserved capture(Path path) throws IOException {
            return new Preserved(path, Files.exists(path) ? Files.readAllBytes(path) : null);
        }

        void restore() {
            try {
                if (original == null) {
                    Files.deleteIfExists(path);
                } else {
                    Files.write(path, original);
                }
            } catch (IOException failed) {
                System.err.println("[warn] Could not restore " + path + ": "
                        + failed.getClass().getSimpleName() + ": " + failed.getMessage());
            }
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

    private static Optional<String> property(String name) {
        String value = System.getProperty(name);
        return value == null || value.isBlank() ? Optional.empty() : Optional.of(value.trim());
    }

    private static void printUsage() {
        System.out.println("Usage: LoadCoordinates <artifact-base-uri> <groupId:artifactId> [<groupId:artifactId> ...]");
        System.out.println();
        System.out.println("Downloads maven-metadata.xml for each supplied coordinate and scans every version");
        System.out.println("that is not already recorded, writing the same data/scanned/ and data/modules/");
        System.out.println("entries the crawler produces. Use it to seed brand-new coordinates whose records");
        System.out.println("are still missing from the Maven Central Nexus index the crawler streams.");
        System.out.println();
        System.out.println("Leaves state.properties, STATUS.md and dirty-modules.tsv untouched; does not commit.");
        System.out.println("Regenerate resolved views afterwards with Regenerate (and ModuleMaven if consumed).");
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
    }
}
