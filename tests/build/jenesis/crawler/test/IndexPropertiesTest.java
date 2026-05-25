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
