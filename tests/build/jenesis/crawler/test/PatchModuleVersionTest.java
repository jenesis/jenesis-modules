package build.jenesis.crawler.test;

import module java.base;
import build.jenesis.crawler.PatchModuleVersion;
import build.jenesis.crawler.fetch.Fetcher;
import build.jenesis.crawler.fetch.Scanner;
import build.jenesis.crawler.test.FakeMavenCentral.IndexedJar;
import module org.junit.jupiter.api;


import static org.assertj.core.api.Assertions.assertThat;

public class PatchModuleVersionTest {

    @TempDir
    Path dataDir;

    private Path modulesRoot;
    private FakeMavenCentral central;
    private Fetcher fetcher;
    private Scanner scanner;

    @BeforeEach
    public void setup() throws IOException {
        modulesRoot = Files.createDirectories(dataDir.resolve("modules"));
        central = new FakeMavenCentral();
        fetcher = new Fetcher();
        scanner = new Scanner();
    }

    @AfterEach
    public void teardown() {
        fetcher.close();
        central.close();
    }

    @Test
    public void patches_named_legacy_row_with_raw_module_info_version() throws IOException {
        publishJar("com.example", "lib", "1.0", Jars.modularJarWithVersion("com.example.lib", "1.0.0-FROM-MODULE-INFO"));
        Path versionsFile = writeVersionsFile("com.example.lib",
                "1.0\tnamed\tcom.example\tlib\t2024-01-01T00:00:00Z");

        PatchModuleVersion.Stats stats = PatchModuleVersion.patch(modulesRoot, fetcher, scanner, central.artifactBaseUri(), 4, Scanner.DEFAULT_TAIL_SIZE);

        assertThat(stats.rowsPatched).isEqualTo(1);
        assertThat(stats.rowsFailed).isZero();
        assertThat(Files.readAllLines(versionsFile, StandardCharsets.UTF_8))
                .containsExactly("1.0\tnamed\tcom.example\tlib\t2024-01-01T00:00:00Z\t1.0.0-FROM-MODULE-INFO");
    }

    @Test
    public void patches_named_legacy_row_with_empty_trailing_when_module_info_has_no_version() throws IOException {
        publishJar("com.example", "lib", "1.0", Jars.modularJar("com.example.lib"));
        Path versionsFile = writeVersionsFile("com.example.lib",
                "1.0\tnamed\tcom.example\tlib\t2024-01-01T00:00:00Z");

        PatchModuleVersion.Stats stats = PatchModuleVersion.patch(modulesRoot, fetcher, scanner, central.artifactBaseUri(), 4, Scanner.DEFAULT_TAIL_SIZE);

        assertThat(stats.rowsPatched).isEqualTo(1);
        assertThat(Files.readAllLines(versionsFile, StandardCharsets.UTF_8))
                .containsExactly("1.0\tnamed\tcom.example\tlib\t2024-01-01T00:00:00Z\t");
    }

    @Test
    public void patches_automatic_legacy_row_in_place_without_fetching() throws IOException {
        // No JAR published. The tool must not need to fetch automatic rows.
        Path versionsFile = writeVersionsFile("com.example.auto",
                "2.0\tautomatic\tcom.example\tauto\t2024-01-01T00:00:00Z");

        PatchModuleVersion.Stats stats = PatchModuleVersion.patch(modulesRoot, fetcher, scanner, central.artifactBaseUri(), 4, Scanner.DEFAULT_TAIL_SIZE);

        assertThat(stats.rowsAutomatic).isEqualTo(1);
        assertThat(stats.rowsFailed).isZero();
        assertThat(Files.readAllLines(versionsFile, StandardCharsets.UTF_8))
                .containsExactly("2.0\tautomatic\tcom.example\tauto\t2024-01-01T00:00:00Z\t");
    }

    @Test
    public void leaves_already_extracted_row_untouched() throws IOException {
        Path versionsFile = writeVersionsFile("com.example.lib",
                "1.0\tnamed\tcom.example\tlib\t2024-01-01T00:00:00Z\t1.0.0",
                "1.1\tnamed\tcom.example\tlib\t2024-02-01T00:00:00Z\t");

        PatchModuleVersion.Stats stats = PatchModuleVersion.patch(modulesRoot, fetcher, scanner, central.artifactBaseUri(), 4, Scanner.DEFAULT_TAIL_SIZE);

        assertThat(stats.rowsLegacy).isZero();
        assertThat(stats.filesPatched).isZero();
        assertThat(Files.readAllLines(versionsFile, StandardCharsets.UTF_8))
                .containsExactly(
                        "1.0\tnamed\tcom.example\tlib\t2024-01-01T00:00:00Z\t1.0.0",
                        "1.1\tnamed\tcom.example\tlib\t2024-02-01T00:00:00Z\t");
    }

    @Test
    public void leaves_named_legacy_row_as_legacy_when_fetch_fails() throws IOException {
        // No JAR published for "missing.module" - the fetch will 404 and the row stays legacy.
        Path versionsFile = writeVersionsFile("missing.module",
                "1.0\tnamed\tcom.example\tmissing\t2024-01-01T00:00:00Z");

        PatchModuleVersion.Stats stats = PatchModuleVersion.patch(modulesRoot, fetcher, scanner, central.artifactBaseUri(), 4, Scanner.DEFAULT_TAIL_SIZE);

        assertThat(stats.rowsFailed).isEqualTo(1);
        assertThat(stats.rowsPatched).isZero();
        assertThat(stats.filesPatched).isZero();
        assertThat(Files.readAllLines(versionsFile, StandardCharsets.UTF_8))
                .containsExactly("1.0\tnamed\tcom.example\tmissing\t2024-01-01T00:00:00Z");
    }

    @Test
    public void patches_mixed_file_in_one_pass() throws IOException {
        publishJar("com.example", "lib", "1.0", Jars.modularJarWithVersion("com.example.lib", "from-1.0"));
        publishJar("com.example", "lib", "2.0", Jars.modularJar("com.example.lib"));
        Path versionsFile = writeVersionsFile("com.example.lib",
                "0.9\tautomatic\tcom.example\tlib\t2024-01-01T00:00:00Z",
                "1.0\tnamed\tcom.example\tlib\t2024-02-01T00:00:00Z",
                "2.0\tnamed\tcom.example\tlib\t2024-03-01T00:00:00Z",
                "3.0\tnamed\tcom.example\tlib\t2024-04-01T00:00:00Z\talready-extracted");

        PatchModuleVersion.Stats stats = PatchModuleVersion.patch(modulesRoot, fetcher, scanner, central.artifactBaseUri(), 4, Scanner.DEFAULT_TAIL_SIZE);

        assertThat(stats.rowsTotal).isEqualTo(4);
        assertThat(stats.rowsLegacy).isEqualTo(3);
        assertThat(stats.rowsAutomatic).isEqualTo(1);
        assertThat(stats.rowsPatched).isEqualTo(2);
        assertThat(stats.rowsFailed).isZero();
        assertThat(stats.filesPatched).isEqualTo(1);
        assertThat(Files.readAllLines(versionsFile, StandardCharsets.UTF_8))
                .containsExactly(
                        "0.9\tautomatic\tcom.example\tlib\t2024-01-01T00:00:00Z\t",
                        "1.0\tnamed\tcom.example\tlib\t2024-02-01T00:00:00Z\tfrom-1.0",
                        "2.0\tnamed\tcom.example\tlib\t2024-03-01T00:00:00Z\t",
                        "3.0\tnamed\tcom.example\tlib\t2024-04-01T00:00:00Z\talready-extracted");
    }

    @Test
    public void ignores_non_versions_files_and_directories() throws IOException {
        publishJar("com.example", "lib", "1.0", Jars.modularJarWithVersion("com.example.lib", "v"));
        Path moduleDir = Files.createDirectories(modulesRoot.resolve("com").resolve("example").resolve("lib"));
        Path versions = moduleDir.resolve("versions.tsv");
        Files.writeString(versions, "1.0\tnamed\tcom.example\tlib\t2024-01-01T00:00:00Z\n", StandardCharsets.UTF_8);
        // Sibling files that should be skipped:
        Files.writeString(moduleDir.resolve("current.tsv"), "ignored\n", StandardCharsets.UTF_8);
        Files.writeString(moduleDir.resolve("owners.tsv"), "ignored\n", StandardCharsets.UTF_8);
        Files.writeString(moduleDir.resolve("README.md"), "ignored\n", StandardCharsets.UTF_8);

        PatchModuleVersion.Stats stats = PatchModuleVersion.patch(modulesRoot, fetcher, scanner, central.artifactBaseUri(), 4, Scanner.DEFAULT_TAIL_SIZE);

        assertThat(stats.filesScanned).isEqualTo(1);
        assertThat(stats.rowsPatched).isEqualTo(1);
        assertThat(Files.readString(moduleDir.resolve("current.tsv"))).isEqualTo("ignored\n");
        assertThat(Files.readString(moduleDir.resolve("owners.tsv"))).isEqualTo("ignored\n");
    }

    private void publishJar(String groupId, String artifactId, String version, byte[] jar) throws IOException {
        // publishFullIndex stores the JAR bytes in the artifact map under its Maven path. The
        // accompanying index is irrelevant here; the patch tool only fetches via artifact base URI.
        central.publishFullIndex(0L, List.of(new IndexedJar(groupId, artifactId, version, jar)));
    }

    private Path writeVersionsFile(String moduleName, String... rows) throws IOException {
        Path dir = modulesRoot;
        for (String segment : moduleName.split("\\.")) {
            dir = dir.resolve(segment);
        }
        Files.createDirectories(dir);
        Path file = dir.resolve("versions.tsv");
        StringBuilder builder = new StringBuilder();
        for (String row : rows) {
            builder.append(row).append('\n');
        }
        Files.writeString(file, builder.toString(), StandardCharsets.UTF_8);
        return file;
    }
}
