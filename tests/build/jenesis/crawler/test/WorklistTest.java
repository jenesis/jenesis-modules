package build.jenesis.crawler.test;

import module java.base;
import module org.junit.jupiter.api;

import build.jenesis.crawler.Coordinate;
import build.jenesis.crawler.Worklist;

import static org.assertj.core.api.Assertions.assertThat;

public class WorklistTest {

    @TempDir
    Path tempDir;

    @Test
    public void writes_and_reads_back_coordinates() throws IOException {
        List<Coordinate> coordinates = List.of(
                new Coordinate("org.example", "alpha", "1.0", null, "jar", 1024L, 1700000000000L),
                new Coordinate("org.example", "beta", "2.5", "jakarta", "jar", 2048L, 1700100000000L)
        );
        Worklist worklist = new Worklist(tempDir.resolve("worklist"));

        long written = worklist.write(coordinates.stream());

        assertThat(written).isEqualTo(2L);
        assertThat(worklist.exists()).isTrue();
        try (Worklist.Reader reader = worklist.open(0L)) {
            assertThat(Worklist.parse(reader.nextLine())).isEqualTo(coordinates.get(0));
            assertThat(Worklist.parse(reader.nextLine())).isEqualTo(coordinates.get(1));
            assertThat(reader.nextLine()).isNull();
        }
    }

    @Test
    public void resumes_from_position_recorded_after_partial_read() throws IOException {
        Worklist worklist = new Worklist(tempDir.resolve("worklist"));
        worklist.write(IntStream.rangeClosed(1, 5)
                .mapToObj(value -> new Coordinate("g", "a", value + ".0", null, "jar", 0L, 0L)));

        long checkpoint;
        try (Worklist.Reader reader = worklist.open(0L)) {
            reader.nextLine();
            reader.nextLine();
            checkpoint = reader.position();
        }
        try (Worklist.Reader resumed = worklist.open(checkpoint)) {
            assertThat(Worklist.parse(resumed.nextLine()).version()).isEqualTo("3.0");
            assertThat(Worklist.parse(resumed.nextLine()).version()).isEqualTo("4.0");
            assertThat(Worklist.parse(resumed.nextLine()).version()).isEqualTo("5.0");
            assertThat(resumed.nextLine()).isNull();
        }
    }

    @Test
    public void rotates_into_multiple_shards_at_line_limit() throws IOException {
        Worklist worklist = new Worklist(tempDir.resolve("worklist"), 3L);
        worklist.write(IntStream.rangeClosed(1, 7)
                .mapToObj(value -> new Coordinate("g", "a", value + ".0", null, "jar", 0L, 0L)));

        List<Worklist.Shard> shards = worklist.readManifest();
        assertThat(shards).hasSize(3);
        assertThat(shards.get(0).records()).isEqualTo(3L);
        assertThat(shards.get(1).records()).isEqualTo(3L);
        assertThat(shards.get(2).records()).isEqualTo(1L);
        assertThat(shards.get(0).name()).isEqualTo("000000.tsv");
        assertThat(shards.get(2).name()).isEqualTo("000002.tsv");
    }

    @Test
    public void reads_across_shard_boundaries() throws IOException {
        Worklist worklist = new Worklist(tempDir.resolve("worklist"), 2L);
        worklist.write(IntStream.rangeClosed(1, 5)
                .mapToObj(value -> new Coordinate("g", "a", value + ".0", null, "jar", 0L, 0L)));

        try (Worklist.Reader reader = worklist.open(0L)) {
            for (int expected = 1; expected <= 5; expected++) {
                assertThat(Worklist.parse(reader.nextLine()).version()).isEqualTo(expected + ".0");
                assertThat(reader.position()).isEqualTo((long) expected);
            }
            assertThat(reader.nextLine()).isNull();
        }
    }

    @Test
    public void resumes_from_position_straddling_shard_boundary() throws IOException {
        Worklist worklist = new Worklist(tempDir.resolve("worklist"), 2L);
        worklist.write(IntStream.rangeClosed(1, 5)
                .mapToObj(value -> new Coordinate("g", "a", value + ".0", null, "jar", 0L, 0L)));

        try (Worklist.Reader resumed = worklist.open(3L)) {
            assertThat(Worklist.parse(resumed.nextLine()).version()).isEqualTo("4.0");
            assertThat(Worklist.parse(resumed.nextLine()).version()).isEqualTo("5.0");
            assertThat(resumed.nextLine()).isNull();
        }
    }

    @Test
    public void exists_only_after_manifest_committed() throws IOException {
        Worklist worklist = new Worklist(tempDir.resolve("worklist"), 100L);
        assertThat(worklist.exists()).isFalse();
        try (Worklist.ShardedWriter writer = worklist.openWriter()) {
            writer.writeLine(Worklist.format(new Coordinate("g", "a", "1.0", null, "jar", 0L, 0L)));
        }
        assertThat(worklist.exists()).as("manifest not yet written").isFalse();
        worklist.writeManifest(List.of(new Worklist.Shard("000000.tsv", 1L)));
        assertThat(worklist.exists()).isTrue();
    }

    @Test
    public void clear_removes_shards_and_manifest() throws IOException {
        Worklist worklist = new Worklist(tempDir.resolve("worklist"), 2L);
        worklist.write(IntStream.rangeClosed(1, 5)
                .mapToObj(value -> new Coordinate("g", "a", value + ".0", null, "jar", 0L, 0L)));
        assertThat(worklist.exists()).isTrue();

        worklist.clear();

        assertThat(worklist.exists()).isFalse();
        try (Stream<Path> entries = Files.list(worklist.dir())) {
            assertThat(entries.count()).isEqualTo(0L);
        }
    }
}
