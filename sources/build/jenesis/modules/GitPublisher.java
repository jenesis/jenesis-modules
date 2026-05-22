package build.jenesis.modules;

import module java.base;

public final class GitPublisher implements CheckpointListener {

    public static final int DEFAULT_PUSH_EVERY = 1;
    public static final Duration COMMAND_TIMEOUT = Duration.ofMinutes(5L);

    private final Path workingDirectory;
    private final List<String> paths;
    private final int pushEvery;
    private int commitsSincePush;

    public GitPublisher(Path workingDirectory, List<String> paths) {
        this(workingDirectory, paths, DEFAULT_PUSH_EVERY);
    }

    public GitPublisher(Path workingDirectory, List<String> paths, int pushEvery) {
        this.workingDirectory = Objects.requireNonNull(workingDirectory, "workingDirectory");
        this.paths = List.copyOf(Objects.requireNonNull(paths, "paths"));
        if (pushEvery < 1) {
            throw new IllegalArgumentException("pushEvery must be at least 1, got " + pushEvery);
        }
        this.pushEvery = pushEvery;
    }

    @Override
    public synchronized void onCheckpoint(State state, Statistics statistics) {
        try {
            stage();
            if (!hasStagedChanges()) {
                return;
            }
            String message = buildMessage(state, statistics);
            commit(message);
            commitsSincePush++;
            if (commitsSincePush >= pushEvery) {
                push();
                commitsSincePush = 0;
            }
        } catch (IOException error) {
            System.err.println("git publisher failed: " + error.getMessage()
                    + " (continuing crawl; on-disk state is still consistent)");
            commitsSincePush = 0;
        }
    }

    private void stage() throws IOException {
        List<String> command = new ArrayList<>();
        command.add("git");
        command.add("add");
        command.add("--");
        command.addAll(paths);
        run(command, true);
    }

    private boolean hasStagedChanges() throws IOException {
        return run(List.of("git", "diff", "--cached", "--quiet"), false) != 0;
    }

    private void commit(String message) throws IOException {
        run(List.of("git", "commit", "-m", message), true);
    }

    private void push() throws IOException {
        run(List.of("git", "push"), true);
    }

    private static String buildMessage(State state, Statistics statistics) {
        StringBuilder builder = new StringBuilder("crawl checkpoint");
        if (state.worklistTotal() > 0L) {
            builder.append(" position=")
                    .append(state.worklistPosition())
                    .append('/')
                    .append(state.worklistTotal());
        }
        builder.append(" processed=").append(statistics.processed());
        builder.append(" modular=").append(statistics.modular());
        if (statistics.failed() > 0L) {
            builder.append(" failed=").append(statistics.failed());
        }
        return builder.toString();
    }

    private int run(List<String> command, boolean requireSuccess) throws IOException {
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.directory(workingDirectory.toFile());
        builder.redirectErrorStream(true);
        Process process = builder.start();
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append('\n');
            }
        }
        try {
            if (!process.waitFor(COMMAND_TIMEOUT.toSeconds(), TimeUnit.SECONDS)) {
                process.destroyForcibly();
                throw new IOException("git command timed out: " + String.join(" ", command));
            }
        } catch (InterruptedException interrupted) {
            Thread.currentThread().interrupt();
            process.destroyForcibly();
            throw new IOException("Interrupted while running: " + String.join(" ", command), interrupted);
        }
        int exit = process.exitValue();
        if (requireSuccess && exit != 0) {
            throw new IOException("git command failed (exit " + exit + "): "
                    + String.join(" ", command) + "\n" + output);
        }
        return exit;
    }
}
