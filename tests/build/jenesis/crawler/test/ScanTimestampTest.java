package build.jenesis.crawler.test;

import module java.base;
import build.jenesis.crawler.Crawler;
import build.jenesis.crawler.index.FailedScannedBatchSource;
import module org.junit.jupiter.api;

import static org.assertj.core.api.Assertions.assertThat;

public class ScanTimestampTest {

    @TempDir
    Path tempDir;

    @Test
    public void coordinate_without_a_timestamp_is_recorded_with_the_storage_layers() throws Exception {
        Path file = scanned("1.0\t\t\tIOException: invalid header field (line 9)");
        try (FakeMavenCentral central = new FakeMavenCentral()) {
            central.publishFullIndex(0L, List.of(new FakeMavenCentral.IndexedJar("com.example", "plain", "1.0", Jars.plainJar())));
            central.setArtifactLastModified("Tue, 17 Nov 2026 18:55:48 GMT");

            retry(central);
        }

        assertThat(Files.readString(file)).isEqualTo("1.0\t\t2026-11-17T18:55:48Z\t\n");
    }

    @Test
    public void coordinate_with_a_timestamp_keeps_it() throws Exception {
        Path file = scanned("1.0\t\t2020-01-02T03:04:05Z\tIOException: invalid header field (line 9)");
        try (FakeMavenCentral central = new FakeMavenCentral()) {
            central.publishFullIndex(0L, List.of(new FakeMavenCentral.IndexedJar("com.example", "plain", "1.0", Jars.plainJar())));
            central.setArtifactLastModified("Tue, 17 Nov 2026 18:55:48 GMT");

            retry(central);
        }

        assertThat(Files.readString(file)).isEqualTo("1.0\t\t2020-01-02T03:04:05Z\t\n");
    }

    private Path scanned(String row) throws IOException {
        Path dir = Files.createDirectories(tempDir.resolve("scanned").resolve("com").resolve("example"));
        return Files.writeString(dir.resolve("plain.tsv"), row + "\n", StandardCharsets.UTF_8);
    }

    private void retry(FakeMavenCentral central) throws Exception {
        Crawler.Configuration configuration = new Crawler.Configuration(
                central.indexBaseUri(),
                central.artifactBaseUri(),
                /* canonicalTimestampBaseUri */ null,
                tempDir,
                Duration.ofSeconds(60L),
                4,
                65536,
                100L,
                262144L,
                true,
                /* reprocessFailed */ true,
                false,
                false
        );
        try (Crawler crawler = new Crawler(configuration);
             FailedScannedBatchSource source = FailedScannedBatchSource.from(tempDir.resolve("scanned"), List.of(), 4)) {
            crawler.scan(source);
        }
    }
}
