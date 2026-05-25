package build.jenesis.crawler.test;

import module java.base;
import build.jenesis.crawler.index.IndexProperties;
import module org.junit.jupiter.api;


import static org.assertj.core.api.Assertions.assertThat;

public class IndexPropertiesTest {

    @Test
    public void reads_chain_id_and_last_incremental() throws IOException {
        String content = """
                #central
                #Thu May 22 03:00:00 UTC 2026
                nexus.index.id=central
                nexus.index.chain-id=abc-123-uuid
                nexus.index.timestamp=20260522030000.000 +0000
                nexus.index.last-incremental=87
                nexus.index.chunk-counter=2400
                """;

        IndexProperties properties = IndexProperties.read(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));

        assertThat(properties.chainId()).isEqualTo("abc-123-uuid");
        assertThat(properties.lastIncremental()).isEqualTo(87);
        assertThat(properties.hasIncrementals()).isTrue();
    }

    @Test
    public void parses_timestamp_with_offset() throws IOException {
        String content = """
                nexus.index.chain-id=x
                nexus.index.timestamp=20260101120530.000 +0000
                nexus.index.last-incremental=0
                """;

        IndexProperties properties = IndexProperties.read(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));

        OffsetDateTime expected = OffsetDateTime.of(2026, 1, 1, 12, 5, 30, 0, ZoneOffset.UTC);
        assertThat(properties.timestamp()).isEqualTo(expected.toInstant().toEpochMilli());
    }

    @Test
    public void treats_missing_last_incremental_as_minus_one() throws IOException {
        String content = """
                nexus.index.chain-id=fresh
                """;

        IndexProperties properties = IndexProperties.read(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));

        assertThat(properties.lastIncremental()).isEqualTo(-1);
        assertThat(properties.hasIncrementals()).isFalse();
    }

    @Test
    public void picks_oldest_retained_incremental_from_listed_chunks() throws IOException {
        // Live Maven Central .properties carries 30 entries naming the retained chunks; we need
        // the smallest one so the post-FULL watermark sits before every chunk Sonatype still
        // serves. Without this the FULL was treated as covering through last-incremental, which
        // skipped real deltas (the byte-buddy 1.18.x gap we found in chunks 913-924).
        String content = """
                nexus.index.chain-id=1318453614498
                nexus.index.timestamp=20260520234056.644 +0000
                nexus.index.last-incremental=928
                nexus.index.incremental-0=928
                nexus.index.incremental-1=927
                nexus.index.incremental-15=913
                nexus.index.incremental-29=899
                """;

        IndexProperties properties = IndexProperties.read(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));

        assertThat(properties.lastIncremental()).isEqualTo(928);
        assertThat(properties.firstRetainedIncremental()).isEqualTo(899);
    }

    @Test
    public void first_retained_falls_back_to_last_incremental_without_retention_listing() throws IOException {
        // The fake test server (FakeMavenCentral) emits only last-incremental, no per-chunk
        // listing. In that case the FULL is self-contained and firstRetained should equal
        // lastIncremental so the watermark advances to the FULL's chunk number directly.
        String content = """
                nexus.index.chain-id=x
                nexus.index.last-incremental=5
                """;

        IndexProperties properties = IndexProperties.read(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));

        assertThat(properties.firstRetainedIncremental()).isEqualTo(5);
    }

    @Test
    public void tolerates_unparseable_timestamp() throws IOException {
        String content = """
                nexus.index.chain-id=x
                nexus.index.timestamp=not a timestamp
                nexus.index.last-incremental=5
                """;

        IndexProperties properties = IndexProperties.read(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));

        assertThat(properties.timestamp()).isEqualTo(0L);
        assertThat(properties.lastIncremental()).isEqualTo(5);
    }
}
