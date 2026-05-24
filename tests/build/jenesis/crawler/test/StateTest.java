package build.jenesis.crawler.test;

import module java.base;
import module org.junit.jupiter.api;

import build.jenesis.crawler.State;

import static org.assertj.core.api.Assertions.assertThat;

public class StateTest {

    @TempDir
    Path tempDir;

    @Test
    public void empty_when_file_does_not_exist() throws IOException {
        State loaded = State.load(tempDir.resolve("missing.properties"));

        assertThat(loaded).isEqualTo(State.EMPTY);
        assertThat(loaded.hasIndexBaseline()).isFalse();
    }

    @Test
    public void round_trips_chain_id_and_index_chunk() throws IOException {
        Path path = tempDir.resolve("state.properties");
        State original = State.EMPTY
                .withIndex(42L, 1700000000000L, "chain-uuid")
                .withSweepStartedAt(Instant.parse("2026-05-22T10:00:00Z"));

        original.save(path);
        State loaded = State.load(path);

        assertThat(loaded).isEqualTo(original);
        assertThat(loaded.hasIndexBaseline()).isTrue();
        assertThat(loaded.indexChainId()).isEqualTo("chain-uuid");
        assertThat(loaded.sweepStartedAt()).isEqualTo(Instant.parse("2026-05-22T10:00:00Z"));
    }

    @Test
    public void pending_full_scan_detected_only_when_set() {
        assertThat(State.EMPTY.hasPendingFullScan()).isFalse();
        State pending = State.EMPTY.withIndexChunkPending(7L);
        assertThat(pending.hasPendingFullScan()).isTrue();
    }
}
