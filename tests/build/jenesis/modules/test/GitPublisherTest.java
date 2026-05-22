package build.jenesis.modules.test;

import module java.base;
import module org.junit.jupiter.api;

import build.jenesis.modules.CheckpointListener;
import build.jenesis.modules.GitPublisher;
import build.jenesis.modules.State;
import build.jenesis.modules.SyncMode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class GitPublisherTest {

    @TempDir
    Path repo;

    @BeforeEach
    void initRepo() throws IOException, InterruptedException {
        assumeTrue(isGitAvailable(), "git binary not on PATH");
        run("git", "init", "-q", "-b", "main");
        run("git", "config", "user.email", "test@example.com");
        run("git", "config", "user.name", "Test");
        run("git", "config", "commit.gpgsign", "false");
        Files.writeString(repo.resolve("README.md"), "seed\n", StandardCharsets.UTF_8);
        run("git", "add", "README.md");
        run("git", "commit", "-q", "-m", "seed");
        Files.createDirectory(repo.resolve("data"));
    }

    @Test
    public void commits_changes_under_managed_paths_at_each_checkpoint() throws IOException, InterruptedException {
        GitPublisher publisher = new GitPublisher(repo, List.of("data"), Integer.MAX_VALUE);

        Files.writeString(repo.resolve("data").resolve("first"), "alpha\n", StandardCharsets.UTF_8);
        publisher.onCheckpoint(stateAt(10L), stats(50L, 5L, 0L));

        Files.writeString(repo.resolve("data").resolve("second"), "beta\n", StandardCharsets.UTF_8);
        publisher.onCheckpoint(stateAt(20L), stats(100L, 10L, 1L));

        String log = capture("git", "log", "--oneline");
        long commitCount = log.lines().count();
        assertThat(commitCount).isEqualTo(3L);
        assertThat(log).contains("position=10");
        assertThat(log).contains("position=20");
        assertThat(log).contains("failed=1");
    }

    @Test
    public void skips_commit_when_nothing_changed_under_managed_paths() throws IOException, InterruptedException {
        GitPublisher publisher = new GitPublisher(repo, List.of("data"), Integer.MAX_VALUE);

        publisher.onCheckpoint(stateAt(0L), stats(0L, 0L, 0L));
        Files.writeString(repo.resolve("unrelated.txt"), "ignored\n", StandardCharsets.UTF_8);
        publisher.onCheckpoint(stateAt(0L), stats(0L, 0L, 0L));

        long commitCount = capture("git", "log", "--oneline").lines().count();
        assertThat(commitCount).isEqualTo(1L);
    }

    private State stateAt(long position) {
        return State.EMPTY.withWorklist(100L, Instant.parse("2026-05-22T10:00:00Z")).withPosition(position);
    }

    private CheckpointListener.Statistics stats(long processed, long modular, long failed) {
        return new CheckpointListener.Statistics(processed, modular, failed, SyncMode.FULL);
    }

    private static boolean isGitAvailable() {
        try {
            Process process = new ProcessBuilder("git", "--version")
                    .redirectErrorStream(true)
                    .start();
            return process.waitFor() == 0;
        } catch (IOException | InterruptedException unavailable) {
            if (unavailable instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            return false;
        }
    }

    private void run(String... command) throws IOException, InterruptedException {
        Process process = new ProcessBuilder(command).directory(repo.toFile()).redirectErrorStream(true).start();
        process.getInputStream().readAllBytes();
        if (process.waitFor() != 0) {
            throw new IOException("command failed: " + String.join(" ", command));
        }
    }

    private String capture(String... command) throws IOException, InterruptedException {
        Process process = new ProcessBuilder(command).directory(repo.toFile()).redirectErrorStream(true).start();
        String output = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        process.waitFor();
        return output;
    }
}
