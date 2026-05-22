package build.jenesis.modules;

import module java.base;

public final class Main {

    private static final String FLAG_DATA = "--data";
    private static final String FLAG_BUDGET = "--budget-minutes";
    private static final String FLAG_CONCURRENCY = "--concurrency";
    private static final String FLAG_INDEX_BASE = "--index-base";
    private static final String FLAG_ARTIFACT_BASE = "--artifact-base";
    private static final String FLAG_TAIL_SIZE = "--tail-size";
    private static final String FLAG_CHECKPOINT_EVERY = "--checkpoint-every";

    private Main() {
    }

    public static void main(String[] arguments) throws IOException {
        Crawler.Configuration configuration = parse(arguments);
        System.out.println("Configuration:");
        System.out.println("  dataDir=" + configuration.dataDir().toAbsolutePath());
        System.out.println("  budget=" + configuration.budget());
        System.out.println("  concurrency=" + configuration.concurrency());
        System.out.println("  indexBase=" + configuration.indexBaseUri());
        System.out.println("  artifactBase=" + configuration.artifactBaseUri());

        Crawler crawler = new Crawler(configuration);
        configureListener(crawler, configuration);
        Crawler.Result result = crawler.run();
        System.out.println("syncMode=" + result.syncMode()
                + " processed=" + result.processed()
                + " modular=" + result.modular()
                + " failed=" + result.failed()
                + " worklistComplete=" + result.worklistComplete());
        if (!result.worklistComplete()) {
            System.out.println("Worklist still has remaining entries: resume with another run.");
        }
        writeStepSummary(result);
    }

    private static Crawler.Configuration parse(String[] arguments) {
        Crawler.Configuration base = Crawler.Configuration.defaults();
        URI indexBase = base.indexBaseUri();
        URI artifactBase = base.artifactBaseUri();
        Path dataDir = base.dataDir();
        Duration budget = base.budget();
        int concurrency = base.concurrency();
        int tailSize = base.tailSize();
        long checkpointEvery = base.checkpointEvery();

        String envBudget = System.getenv("BUDGET_MINUTES");
        if (envBudget != null && !envBudget.isBlank()) {
            budget = Duration.ofMinutes(Long.parseLong(envBudget.trim()));
        }
        String envConcurrency = System.getenv("CONCURRENCY");
        if (envConcurrency != null && !envConcurrency.isBlank()) {
            concurrency = Integer.parseInt(envConcurrency.trim());
        }
        String envDataDir = System.getenv("DATA_DIR");
        if (envDataDir != null && !envDataDir.isBlank()) {
            dataDir = Path.of(envDataDir.trim());
        }

        for (int index = 0; index < arguments.length; index++) {
            String argument = arguments[index];
            String value = index + 1 < arguments.length ? arguments[index + 1] : null;
            switch (argument) {
                case FLAG_DATA -> {
                    dataDir = Path.of(require(value, FLAG_DATA));
                    index++;
                }
                case FLAG_BUDGET -> {
                    budget = Duration.ofMinutes(Long.parseLong(require(value, FLAG_BUDGET)));
                    index++;
                }
                case FLAG_CONCURRENCY -> {
                    concurrency = Integer.parseInt(require(value, FLAG_CONCURRENCY));
                    index++;
                }
                case FLAG_INDEX_BASE -> {
                    indexBase = URI.create(require(value, FLAG_INDEX_BASE));
                    index++;
                }
                case FLAG_ARTIFACT_BASE -> {
                    artifactBase = URI.create(require(value, FLAG_ARTIFACT_BASE));
                    index++;
                }
                case FLAG_TAIL_SIZE -> {
                    tailSize = Integer.parseInt(require(value, FLAG_TAIL_SIZE));
                    index++;
                }
                case FLAG_CHECKPOINT_EVERY -> {
                    checkpointEvery = Long.parseLong(require(value, FLAG_CHECKPOINT_EVERY));
                    index++;
                }
                case "--help", "-h" -> {
                    printUsage();
                    System.exit(0);
                }
                default -> throw new IllegalArgumentException("Unknown argument: " + argument);
            }
        }
        return new Crawler.Configuration(indexBase, artifactBase, dataDir, budget, concurrency, tailSize, checkpointEvery);
    }

    private static String require(String value, String flag) {
        if (value == null) {
            throw new IllegalArgumentException("Missing value for " + flag);
        }
        return value;
    }

    private static void configureListener(Crawler crawler, Crawler.Configuration configuration) {
        CheckpointListener listener = new StatusWriter(configuration.dataDir().resolve("STATUS.md"));

        String publish = System.getenv("GIT_PUBLISH");
        if (publish != null && "1".equals(publish.trim())) {
            String workingDirectoryRaw = System.getenv("GIT_WORK_DIR");
            Path workingDirectory = workingDirectoryRaw == null || workingDirectoryRaw.isBlank()
                    ? Path.of(".")
                    : Path.of(workingDirectoryRaw.trim());
            String pushEveryRaw = System.getenv("GIT_PUSH_EVERY");
            int pushEvery = pushEveryRaw == null || pushEveryRaw.isBlank()
                    ? GitPublisher.DEFAULT_PUSH_EVERY
                    : Integer.parseInt(pushEveryRaw.trim());
            Path dataDir = configuration.dataDir().toAbsolutePath();
            Path workingAbsolute = workingDirectory.toAbsolutePath();
            Path relative = workingAbsolute.relativize(dataDir);
            listener = listener.andThen(new GitPublisher(workingAbsolute, List.of(relative.toString()), pushEvery));
            System.out.println("Publishing checkpoints via git in " + workingAbsolute + " (pushEvery=" + pushEvery + ")");
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
        summary.append("| Modular artifacts recorded | ").append(result.modular()).append(" |\n");
        summary.append("| Failed fetches | ").append(result.failed()).append(" |\n");
        summary.append("| Worklist complete | ").append(result.worklistComplete() ? "yes" : "no, resume next run").append(" |\n");
        try {
            Files.writeString(Path.of(path), summary.toString(), StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException io) {
            System.err.println("Failed to write step summary: " + io.getMessage());
        }
    }

    private static void printUsage() {
        System.out.println("Usage: java build.jenesis.modules.Main [options]");
        System.out.println();
        System.out.println("Options:");
        System.out.println("  --data <dir>             Data directory (state, worklist, modules)");
        System.out.println("  --budget-minutes <n>     Wall-clock time budget in minutes");
        System.out.println("  --concurrency <n>        Number of in-flight artifact fetches");
        System.out.println("  --index-base <uri>       Base URI for the Maven Central index");
        System.out.println("  --artifact-base <uri>    Base URI for artifact downloads");
        System.out.println("  --tail-size <n>          Bytes to fetch from each JAR tail");
        System.out.println("  --checkpoint-every <n>   Coordinates between state checkpoints");
        System.out.println();
        System.out.println("Environment overrides: BUDGET_MINUTES, CONCURRENCY, DATA_DIR");
        System.out.println("Incremental publishing: GIT_PUBLISH=1 to enable; GIT_WORK_DIR (default '.');");
        System.out.println("  GIT_PUSH_EVERY=<n> to push every n checkpoints (default 1).");
    }
}
