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
        Worklist worklist = new Worklist(tempDir.resolve("worklist.tsv"));

        long written = worklist.write(coordinates.stream());

        assertThat(written).isEqualTo(2L);
        try (Worklist.Reader reader = worklist.open(0L)) {
            assertThat(Worklist.parse(reader.nextLine())).isEqualTo(coordinates.get(0));
            assertThat(Worklist.parse(reader.nextLine())).isEqualTo(coordinates.get(1));
            assertThat(reader.nextLine()).isNull();
        }
    }

    @Test
    public void resumes_from_byte_position_recorded_after_partial_read() throws IOException {
        Worklist worklist = new Worklist(tempDir.resolve("worklist.tsv"));
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
}
