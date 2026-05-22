package build.jenesis.modules.test;

import module java.base;
import module org.junit.jupiter.api;

import build.jenesis.modules.ByteSource;
import build.jenesis.modules.ModuleType;
import build.jenesis.modules.ScannedModule;
import build.jenesis.modules.Scanner;

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
    public void handles_tail_size_smaller_than_full_archive() throws IOException {
        byte[] jar = Jars.modularJar("compact.module");
        Scanner narrowScanner = new Scanner(256);

        Optional<ScannedModule> scanned = narrowScanner.scan(ByteSource.ofBytes(jar));

        assertThat(scanned).contains(new ScannedModule("compact.module", ModuleType.NAMED));
    }
}
