package build.jenesis.crawler.test;

import module java.base;
import build.jenesis.crawler.fetch.ByteSource;
import build.jenesis.crawler.fetch.Scanner;
import build.jenesis.crawler.model.ModuleType;
import build.jenesis.crawler.model.ScannedModule;
import module org.junit.jupiter.api;


import static org.assertj.core.api.Assertions.assertThat;

public class ScannerTest {

    private final Scanner scanner = new Scanner();

    @Test
    public void extracts_explicit_module_name_from_module_info() throws IOException {
        byte[] jar = Jars.modularJar("com.example.lib");

        Optional<ScannedModule> scanned = scanner.scan(ByteSource.ofBytes(jar));

        assertThat(scanned).contains(new ScannedModule("com.example.lib", ModuleType.NAMED));
    }

    @Test
    public void extracts_automatic_module_name_from_manifest() throws IOException {
        byte[] jar = Jars.automaticJar("com.example.auto");

        Optional<ScannedModule> scanned = scanner.scan(ByteSource.ofBytes(jar));

        assertThat(scanned).contains(new ScannedModule("com.example.auto", ModuleType.AUTOMATIC));
    }

    @Test
    public void returns_empty_when_neither_module_info_nor_automatic_name_present() throws IOException {
        byte[] jar = Jars.plainJar();

        Optional<ScannedModule> scanned = scanner.scan(ByteSource.ofBytes(jar));

        assertThat(scanned).isEmpty();
    }

    @Test
    public void prefers_module_info_over_automatic_module_name_when_both_exist() throws IOException {
        byte[] jar = Jars.modularJar("explicit.module", Map.of("Automatic-Module-Name", "ignored.module"));

        Optional<ScannedModule> scanned = scanner.scan(ByteSource.ofBytes(jar));

        assertThat(scanned).contains(new ScannedModule("explicit.module", ModuleType.NAMED));
    }

    @Test
    public void trims_whitespace_in_automatic_module_name() throws IOException {
        byte[] jar = Jars.automaticJar("trimmed.name");

        Optional<ScannedModule> scanned = scanner.scan(ByteSource.ofBytes(jar));

        assertThat(scanned).map(ScannedModule::name).contains("trimmed.name");
    }

    @Test
    public void picks_up_module_info_from_versioned_path_when_root_is_absent() throws IOException {
        byte[] jar = Jars.multiReleaseModularJar("modular.mrjar", 9);

        Optional<ScannedModule> scanned = scanner.scan(ByteSource.ofBytes(jar));

        assertThat(scanned).contains(new ScannedModule("modular.mrjar", ModuleType.NAMED));
    }

    @Test
    public void uses_highest_versioned_module_info_when_multiple_present() throws IOException {
        byte[] jar = Jars.multiReleaseModularJar("modular.mrjar.high", 9, 11, 17);

        Optional<ScannedModule> scanned = scanner.scan(ByteSource.ofBytes(jar));

        assertThat(scanned).contains(new ScannedModule("modular.mrjar.high", ModuleType.NAMED));
    }

    @Test
    public void prefers_root_module_info_over_versioned() throws IOException {
        byte[] jar = Jars.rootAndVersionedModularJar("root.module", "versioned.module", 11);

        Optional<ScannedModule> scanned = scanner.scan(ByteSource.ofBytes(jar));

        assertThat(scanned).contains(new ScannedModule("root.module", ModuleType.NAMED));
    }

    @Test
    public void skips_invalid_automatic_module_name() throws IOException {
        byte[] jar = Jars.automaticJar("http-messages-signing-servlet");

        Optional<ScannedModule> scanned = scanner.scan(ByteSource.ofBytes(jar));

        assertThat(scanned).isEmpty();
    }

    @Test
    public void handles_tail_size_smaller_than_full_archive() throws IOException {
        byte[] jar = Jars.modularJar("compact.module");
        Scanner narrowScanner = new Scanner(256);

        Optional<ScannedModule> scanned = narrowScanner.scan(ByteSource.ofBytes(jar));

        assertThat(scanned).contains(new ScannedModule("compact.module", ModuleType.NAMED));
    }

    @Test
    public void reads_self_extracting_jar_with_launcher_prefix() throws IOException {
        byte[] jar = Jars.selfExtractingModularJar("fat.jar.module", 4096);

        Optional<ScannedModule> scanned = scanner.scan(ByteSource.ofBytes(jar));

        assertThat(scanned).contains(new ScannedModule("fat.jar.module", ModuleType.NAMED));
    }

    @Test
    public void retries_with_larger_tail_when_initial_buffer_misses_eocd() throws IOException {
        byte[] jar = Jars.jarWithLongArchiveComment("buried.eocd.module", 3000);
        Scanner narrowScanner = new Scanner(1024);

        Optional<ScannedModule> scanned = narrowScanner.scan(ByteSource.ofBytes(jar));

        assertThat(scanned).contains(new ScannedModule("buried.eocd.module", ModuleType.NAMED));
    }
}
