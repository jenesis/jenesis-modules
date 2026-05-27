package build.jenesis.crawler.test;

import module java.base;
import build.jenesis.crawler.Crawler;
import build.jenesis.crawler.State;
import build.jenesis.crawler.SyncMode;
import module org.junit.jupiter.api;


import static org.assertj.core.api.Assertions.assertThat;

public class CrawlerChainIntegrationTest {

    private static final String CHAIN_ID = "test-chain-2026";
    private static final long T0 = 1_700_000_000_000L;
    private static final long T1 = 1_700_000_010_000L;
    private static final long T2 = 1_700_000_020_000L;

    @TempDir
    Path tempDir;

    @Test
    public void crawler_processes_full_then_two_incrementals_with_per_chunk_chain_advance() throws Exception {
        Path dataDir = tempDir.resolve("data");
        Files.createDirectories(dataDir);

        try (FakeMavenCentral central = new FakeMavenCentral()) {
            // Round 1 — only full index is published. lastIncremental = 0.
            central.setIndexProperties(CHAIN_ID, 0, T0);
            central.publishFullIndex(T0, List.of(
                    indexed("com.example", "named-mod", "1.0", Jars.modularJar("com.example.named.mod")),
                    indexed("com.example", "auto-mod", "1.0", Jars.automaticJar("com.example.auto.mod")),
                    indexed("com.example", "plain-pkg", "1.0", Jars.plainJar()),
                    indexed("com.example", "broken-pkg", "1.0", garbageBytes())
            ));

            runCrawler(central, dataDir);

            State afterFull = State.load(dataDir.resolve("state.properties"));
            assertThat(afterFull.indexChainId()).isEqualTo(CHAIN_ID);
            assertThat(afterFull.indexChunkLastApplied()).isEqualTo(0L);
            assertThat(afterFull.hasPendingFullScan()).isFalse();

            // Modules from full sync are recorded and artifacts.tsv is materialised
            // (drainDirty is invoked at chunk boundary).
            assertModulePresent(dataDir, "com.example.named.mod", "1.0");
            assertModulePresent(dataDir, "com.example.auto.mod", "1.0");
            assertModuleAbsent(dataDir, "com.example.plain.pkg");
            assertScannedFailure(dataDir, "com.example", "broken-pkg", "1.0");

            // Round 2 — server publishes incremental 1.
            central.setIndexProperties(CHAIN_ID, 1, T1);
            central.publishIncremental(1, T1, List.of(
                    indexed("com.example", "named-mod", "2.0", Jars.modularJar("com.example.named.mod")),
                    indexed("com.example", "added-auto", "1.0", Jars.automaticJar("com.example.added.auto"))
            ));

            runCrawler(central, dataDir);

            State afterInc1 = State.load(dataDir.resolve("state.properties"));
            assertThat(afterInc1.indexChunkLastApplied()).isEqualTo(1L);
            assertModulePresent(dataDir, "com.example.named.mod", "1.0", "2.0");
            assertModulePresent(dataDir, "com.example.added.auto", "1.0");

            // Round 3 — server publishes incremental 2.
            central.setIndexProperties(CHAIN_ID, 2, T2);
            central.publishIncremental(2, T2, List.of(
                    indexed("com.example", "named-mod", "3.0", Jars.modularJar("com.example.named.mod")),
                    indexed("com.example", "added-named", "1.0", Jars.modularJar("com.example.added.named"))
            ));

            runCrawler(central, dataDir);

            State afterInc2 = State.load(dataDir.resolve("state.properties"));
            assertThat(afterInc2.indexChunkLastApplied()).isEqualTo(2L);
            assertModulePresent(dataDir, "com.example.named.mod", "1.0", "2.0", "3.0");
            assertModulePresent(dataDir, "com.example.added.named", "1.0");

            // Round 4 — nothing new on the server. Crawler should detect caught-up and do no work.
            runCrawler(central, dataDir);

            State afterNoop = State.load(dataDir.resolve("state.properties"));
            assertThat(afterNoop.indexChunkLastApplied()).isEqualTo(2L);
            assertThat(afterNoop.hasPendingFullScan()).isFalse();
        }
    }

    private static void runCrawler(FakeMavenCentral central, Path dataDir) throws IOException {
        Crawler.Configuration configuration = new Crawler.Configuration(
                central.indexBaseUri(),
                central.artifactBaseUri(),
                /* canonicalTimestampBaseUri */ null,
                dataDir,
                Duration.ofSeconds(60L),
                4,
                65536,
                100L,
                262144L,
                true,
                false,
                false
        );
        try (Crawler crawler = new Crawler(configuration)) {
            Crawler.Result result = crawler.run();
            assertThat(result.syncMode()).isIn(SyncMode.FULL, SyncMode.INCREMENTAL, SyncMode.UP_TO_DATE, SyncMode.SKIPPED);
        }
    }

    private static FakeMavenCentral.IndexedJar indexed(String group, String artifact, String version, byte[] jarBytes) {
        return new FakeMavenCentral.IndexedJar(group, artifact, version, jarBytes);
    }

    private static byte[] garbageBytes() {
        byte[] bytes = new byte[256];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (i & 0xFF);
        }
        return bytes;
    }

    private static void assertModulePresent(Path dataDir, String moduleName, String... expectedVersions) throws IOException {
        Path moduleDir = dataDir.resolve("modules").resolve(moduleDirRelative(moduleName));
        assertThat(moduleDir).as("module dir for " + moduleName).exists();
        Path versions = moduleDir.resolve("versions.tsv");
        assertThat(versions).as("versions.tsv for " + moduleName).exists();
        Set<String> observed = Files.readAllLines(versions, StandardCharsets.UTF_8).stream()
                .filter(line -> !line.isEmpty())
                .map(line -> {
                    int firstTab = line.indexOf('\t');
                    return firstTab < 0 ? line : line.substring(0, firstTab);
                })
                .collect(java.util.stream.Collectors.toSet());
        assertThat(observed).as("versions.tsv for " + moduleName).contains(expectedVersions);
    }

    private static void assertModuleAbsent(Path dataDir, String moduleName) {
        Path moduleDir = dataDir.resolve("modules").resolve(moduleDirRelative(moduleName));
        assertThat(moduleDir).as("module dir for " + moduleName + " should not exist").doesNotExist();
    }

    private static void assertScannedFailure(Path dataDir, String groupId, String artifactId, String version) throws IOException {
        Path scannedFile = dataDir.resolve("scanned");
        for (String segment : groupId.split("\\.", -1)) {
            scannedFile = scannedFile.resolve(segment);
        }
        scannedFile = scannedFile.resolve(artifactId + ".tsv");
        assertThat(scannedFile).as(artifactId + ".tsv for " + groupId).exists();
        String body = Files.readString(scannedFile, StandardCharsets.UTF_8);
        boolean found = body.lines().anyMatch(line -> {
            String[] parts = line.split("\t", -1);
            // Accept both the historical three-column shape and the current four-column
            // shape (version, classifier, publishedAt, errorMessage). The error column is
            // always the trailing one.
            return (parts.length == 3 || parts.length == 4)
                    && parts[0].equals(version)
                    && !parts[parts.length - 1].isEmpty();
        });
        assertThat(found).as(artifactId + ".tsv contains a non-empty error column for version "
                + version + " (body=" + body + ")").isTrue();
    }

    private static Path moduleDirRelative(String moduleName) {
        return Path.of(moduleName.replace('.', '/'));
    }
}
