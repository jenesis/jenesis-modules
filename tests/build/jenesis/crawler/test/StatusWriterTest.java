package build.jenesis.crawler.test;

import module java.base;
import module org.junit.jupiter.api;

import build.jenesis.crawler.CheckpointListener;
import build.jenesis.crawler.State;
import build.jenesis.crawler.StatusWriter;
import build.jenesis.crawler.SyncMode;

import static org.assertj.core.api.Assertions.assertThat;

public class StatusWriterTest {

    @TempDir
    Path tempDir;

    @Test
    public void writes_position_percentage_and_throughput() throws IOException {
        Path file = tempDir.resolve("STATUS.md");
        StatusWriter writer = new StatusWriter(file, Instant.now().minusSeconds(60L));
        State state = State.EMPTY
                .withIndex(42L, 1700000000000L, "chain-42")
                .withWorklist(1000L, Instant.parse("2026-05-22T10:00:00Z"))
                .withPosition(250L);

        writer.onCheckpoint(state, new CheckpointListener.Statistics(250L, 30L, 1L, SyncMode.FULL));

        String content = Files.readString(file, StandardCharsets.UTF_8);
        assertThat(content).contains("Sync mode: FULL");
        assertThat(content).contains("Position: 250 / 1000 (25.00%)");
        assertThat(content).contains("processed=250");
        assertThat(content).contains("modular=30");
        assertThat(content).contains("failed=1");
        assertThat(content).contains("Last applied index chunk: 42");
        assertThat(content).contains("chain-42");
        assertThat(content).containsPattern("Throughput: \\d+ coordinates/sec");
    }

    @Test
    public void rewrites_file_on_each_checkpoint() throws IOException {
        Path file = tempDir.resolve("STATUS.md");
        StatusWriter writer = new StatusWriter(file);
        State firstState = State.EMPTY.withWorklist(100L, Instant.now()).withPosition(10L);
        State secondState = State.EMPTY.withWorklist(100L, Instant.now()).withPosition(75L);

        writer.onCheckpoint(firstState, new CheckpointListener.Statistics(10L, 1L, 0L, SyncMode.FULL));
        String first = Files.readString(file);
        writer.onCheckpoint(secondState, new CheckpointListener.Statistics(75L, 9L, 2L, SyncMode.FULL));
        String second = Files.readString(file);

        assertThat(first).contains("Position: 10 / 100");
        assertThat(second).contains("Position: 75 / 100");
        assertThat(second).doesNotContain("Position: 10 / 100");
    }

    @Test
    public void formats_duration_human_readable() {
        assertThat(StatusWriter.formatDuration(Duration.ofSeconds(5L))).isEqualTo("5s");
        assertThat(StatusWriter.formatDuration(Duration.ofSeconds(125L))).isEqualTo("2m 05s");
        assertThat(StatusWriter.formatDuration(Duration.ofSeconds(3725L))).isEqualTo("1h 02m 05s");
    }
}
