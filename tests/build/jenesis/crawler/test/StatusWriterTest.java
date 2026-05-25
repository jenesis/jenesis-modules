package build.jenesis.crawler.test;

import module java.base;
import build.jenesis.crawler.publish.CheckpointListener;
import build.jenesis.crawler.publish.StatusWriter;
import build.jenesis.crawler.State;
import build.jenesis.crawler.SyncMode;
import module org.junit.jupiter.api;


import static org.assertj.core.api.Assertions.assertThat;

public class StatusWriterTest {

    @TempDir
    Path tempDir;

    @Test
    public void writes_run_stats_and_chunk_metadata() throws IOException {
        Path file = tempDir.resolve("STATUS.md");
        StatusWriter writer = new StatusWriter(file, Instant.now().minusSeconds(60L));
        State state = State.EMPTY
                .withIndex(42L, 1700000000000L, "chain-42")
                .withSweepStartedAt(Instant.parse("2026-05-22T10:00:00Z"));

        writer.onCheckpoint(state, new CheckpointListener.Statistics(250L, 20L, 10L, 1L, SyncMode.FULL));

        String content = Files.readString(file, StandardCharsets.UTF_8);
        assertThat(content).contains("Sync mode: FULL");
        assertThat(content).contains("processed=250");
        assertThat(content).contains("named=20");
        assertThat(content).contains("automatic=10");
        assertThat(content).contains("failed=1");
        assertThat(content).contains("Last applied index chunk: 42");
        assertThat(content).contains("chain-42");
        assertThat(content).contains("Current chunk started: 2026-05-22T10:00:00Z");
        assertThat(content).containsPattern("Throughput: \\d+ coordinates/sec");
    }

    @Test
    public void rewrites_file_on_each_checkpoint() throws IOException {
        Path file = tempDir.resolve("STATUS.md");
        StatusWriter writer = new StatusWriter(file);
        State firstState = State.EMPTY.withIndex(10L, 0L, "chain-A");
        State secondState = State.EMPTY.withIndex(11L, 0L, "chain-A");

        writer.onCheckpoint(firstState, new CheckpointListener.Statistics(10L, 1L, 0L, 0L, SyncMode.FULL));
        String first = Files.readString(file);
        writer.onCheckpoint(secondState, new CheckpointListener.Statistics(75L, 7L, 2L, 2L, SyncMode.FULL));
        String second = Files.readString(file);

        assertThat(first).contains("Last applied index chunk: 10");
        assertThat(first).contains("processed=10");
        assertThat(second).contains("Last applied index chunk: 11");
        assertThat(second).contains("processed=75");
        assertThat(second).doesNotContain("Last applied index chunk: 10");
    }

    @Test
    public void formats_duration_human_readable() {
        assertThat(StatusWriter.formatDuration(Duration.ofSeconds(5L))).isEqualTo("5s");
        assertThat(StatusWriter.formatDuration(Duration.ofSeconds(125L))).isEqualTo("2m 05s");
        assertThat(StatusWriter.formatDuration(Duration.ofSeconds(3725L))).isEqualTo("1h 02m 05s");
    }
}
