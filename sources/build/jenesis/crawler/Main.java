package build.jenesis.crawler;

import module java.base;

public final class Main {

    private static final String FLAG_DATA = "--data";
    private static final String FLAG_BUDGET = "--budget-minutes";
    private static final String FLAG_CONCURRENCY = "--concurrency";
    private static final String FLAG_INDEX_BASE = "--index-base";
    private static final String FLAG_ARTIFACT_BASE = "--artifact-base";
    private static final String FLAG_TAIL_SIZE = "--tail-size";
    private static final String FLAG_CHECKPOINT_EVERY = "--checkpoint-every";
    private static final String FLAG_SMALL_JAR_THRESHOLD = "--small-jar-threshold";
    private static final String FLAG_RESUME = "--resume";

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
        System.out.println("  resume=" + configuration.resume());

        Crawler.Result result;
        try (Crawler crawler = new Crawler(configuration)) {
            configureListener(crawler, configuration);
            result = crawler.run();
        }
        System.out.println("syncMode=" + result.syncMode()
                + " processed=" + result.processed()
                + " modular=" + result.modular()
                + " failed=" + result.failed()
                + " worklistComplete=" + result.worklistComplete());
        if (!result.worklistComplete()) {
            System.out.println("Worklist still has remaining entries: resume with another run.");
        }
        printFailureBreakdown(result);
        writeStepSummary(result);
    }

    private static void printFailureBreakdown(Crawler.Result result) {
        if (result.failureBreakdown().isEmpty()) {
            return;
        }
        System.out.println("Failure breakdown (" + result.failed() + " total):");
        result.failureBreakdown().entrySet().stream()
                .sorted((a, b) -> Long.compare(b.getValue().count(), a.getValue().count()))
                .forEach(entry -> {
                    System.out.println("  " + entry.getKey() + ": " + entry.getValue().count());
                    System.out.println("    sample: " + entry.getValue().sampleMessage());
                });
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
        long smallJarThreshold = base.smallJarThreshold();
        boolean resume = base.resume();

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
        String envIndexBase = System.getenv("INDEX_BASE");
        if (envIndexBase != null && !envIndexBase.isBlank()) {
            indexBase = URI.create(envIndexBase.trim());
        }
        String envArtifactBase = System.getenv("ARTIFACT_BASE");
        if (envArtifactBase != null && !envArtifactBase.isBlank()) {
            artifactBase = URI.create(envArtifactBase.trim());
        }
        String envTailSize = System.getenv("TAIL_SIZE");
        if (envTailSize != null && !envTailSize.isBlank()) {
            tailSize = Integer.parseInt(envTailSize.trim());
        }
        String envCheckpointEvery = System.getenv("CHECKPOINT_EVERY");
        if (envCheckpointEvery != null && !envCheckpointEvery.isBlank()) {
            checkpointEvery = Long.parseLong(envCheckpointEvery.trim());
        }
        String envSmallJarThreshold = System.getenv("SMALL_JAR_THRESHOLD");
        if (envSmallJarThreshold != null && !envSmallJarThreshold.isBlank()) {
            smallJarThreshold = Long.parseLong(envSmallJarThreshold.trim());
        }
        String envResume = System.getenv("RESUME");
        if (envResume != null && !envResume.isBlank()) {
            resume = parseBoolean(envResume.trim(), "RESUME");
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
                case FLAG_SMALL_JAR_THRESHOLD -> {
                    smallJarThreshold = Long.parseLong(require(value, FLAG_SMALL_JAR_THRESHOLD));
                    index++;
                }
                case FLAG_RESUME -> {
                    resume = parseBoolean(require(value, FLAG_RESUME), FLAG_RESUME);
                    index++;
                }
                case "--help", "-h" -> {
                    printUsage();
                    System.exit(0);
                }
                default -> throw new IllegalArgumentException("Unknown argument: " + argument);
            }
        }
        return new Crawler.Configuration(indexBase, artifactBase, dataDir, budget, concurrency, tailSize, checkpointEvery, smallJarThreshold, resume);
    }

    private static String require(String value, String flag) {
        if (value == null) {
            throw new IllegalArgumentException("Missing value for " + flag);
        }
        return value;
    }

    private static boolean parseBoolean(String value, String source) {
        return switch (value.toLowerCase(Locale.ROOT)) {
            case "true", "1", "yes" -> true;
            case "false", "0", "no" -> false;
            default -> throw new IllegalArgumentException("Expected true/false for " + source + ", got: " + value);
        };
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
        } catch (IOException io) {
            System.err.println("Failed to write step summary: " + io.getMessage());
        }
    }

    private static void printUsage() {
        System.out.println("Usage: java build.jenesis.crawler.Main [options]");
        System.out.println();
        System.out.println("Options:");
        System.out.println("  --data <dir>             Data directory (state, worklist, modules)");
        System.out.println("  --budget-minutes <n>     Wall-clock time budget in minutes");
        System.out.println("  --concurrency <n>        Number of in-flight artifact fetches");
        System.out.println("  --index-base <uri>       Base URI for the Maven Central index");
        System.out.println("  --artifact-base <uri>    Base URI for artifact downloads");
        System.out.println("  --tail-size <n>          Bytes to fetch from each JAR tail");
        System.out.println("  --checkpoint-every <n>   Coordinates between state checkpoints");
        System.out.println("  --small-jar-threshold <n> JAR size below which we fetch the whole file in one request");
        System.out.println("  --resume <true|false>    Resume in-flight worklist (default true); false discards state + worklist");
        System.out.println();
        System.out.println("Environment overrides: BUDGET_MINUTES, CONCURRENCY, DATA_DIR,");
        System.out.println("  INDEX_BASE, ARTIFACT_BASE, TAIL_SIZE, CHECKPOINT_EVERY, SMALL_JAR_THRESHOLD, RESUME.");
        System.out.println("Incremental publishing: GIT_PUBLISH=1 to enable; GIT_WORK_DIR (default '.');");
        System.out.println("  GIT_PUSH_EVERY=<n> to push every n checkpoints (default 1).");
    }
}
