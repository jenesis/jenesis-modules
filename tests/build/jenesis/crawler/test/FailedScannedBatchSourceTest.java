package build.jenesis.crawler.test;

import module java.base;
import build.jenesis.crawler.index.BatchSource;
import build.jenesis.crawler.index.FailedScannedBatchSource;
import build.jenesis.crawler.model.Coordinate;
import module org.junit.jupiter.api;


import static org.assertj.core.api.Assertions.assertThat;

public class FailedScannedBatchSourceTest {

    @TempDir
    Path root;

    @Test
    public void default_filter_skips_404_failures() throws IOException {
        Path dir = Files.createDirectories(root.resolve("scanned").resolve("com").resolve("example"));
        Files.writeString(dir.resolve("lib.tsv"), String.join("\n",
                "1.0\t\t\tIOException: Tail request on https://example.org/foo.jar returned status 404",
                "2.0\t\t\tIOException: invalid header field (line 9)",
                "3.0\t\t\tInvalidModuleDescriptorException: this_class should be module-info"
        ) + "\n", StandardCharsets.UTF_8);

        FailedScannedBatchSource source = FailedScannedBatchSource.from(root.resolve("scanned"), List.of(), 100);

        assertThat(source.total()).isEqualTo(2);
        BatchSource.Batch batch = source.next();
        assertThat(batch.coordinates()).extracting(Coordinate::version).containsExactlyInAnyOrder("2.0", "3.0");
    }

    @Test
    public void explicit_pattern_bypasses_404_skip() throws IOException {
        Path dir = Files.createDirectories(root.resolve("scanned").resolve("com").resolve("example"));
        Files.writeString(dir.resolve("lib.tsv"),
                "1.0\t\t\tIOException: Tail request on https://example.org/foo.jar returned status 404\n"
                        + "2.0\t\t\tIOException: invalid header field (line 9)\n",
                StandardCharsets.UTF_8);
        // Setting an explicit pattern reverts to "literal match" semantics, including 404s if
        // they match. A pattern that matches both rows captures both.
        Pattern matchEverything = Pattern.compile("IOException");

        FailedScannedBatchSource source = FailedScannedBatchSource.from(
                root.resolve("scanned"), List.of(matchEverything), 100);

        assertThat(source.total()).isEqualTo(2);
    }

    @Test
    public void explicit_404_pattern_includes_404s() throws IOException {
        Path dir = Files.createDirectories(root.resolve("scanned").resolve("com").resolve("example"));
        Files.writeString(dir.resolve("lib.tsv"),
                "1.0\t\t\tIOException: Tail request on https://example.org/foo.jar returned status 404\n"
                        + "2.0\t\t\tIOException: invalid header field (line 9)\n",
                StandardCharsets.UTF_8);
        Pattern only404 = Pattern.compile("returned status 404");

        FailedScannedBatchSource source = FailedScannedBatchSource.from(
                root.resolve("scanned"), List.of(only404), 100);

        assertThat(source.total()).isEqualTo(1);
        assertThat(source.next().coordinates().get(0).version()).isEqualTo("1.0");
    }
}
