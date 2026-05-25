package build.jenesis.crawler.test;

import module java.base;
import build.jenesis.crawler.ModuleSummary;
import build.jenesis.crawler.ModuleSummary.ModuleVersionCoverage;
import module org.junit.jupiter.api;


import static org.assertj.core.api.Assertions.assertThat;

public class ModuleSummaryTest {

    @TempDir
    Path dataDir;

    @Test
    public void module_version_coverage_only_counts_named_rows() throws IOException {
        Path moduleDir = Files.createDirectories(dataDir.resolve("modules").resolve("com").resolve("example").resolve("lib"));
        Files.writeString(moduleDir.resolve("versions.tsv"), String.join("\n",
                "1.0\tnamed\tcom.example\tlib\t2024-01-01T00:00:00Z\t1.0",
                "1.1\tnamed\tcom.example\tlib\t2024-02-01T00:00:00Z\t9.9",
                "1.2\tnamed\tcom.example\tlib\t2024-03-01T00:00:00Z\t",
                "0.9\tautomatic\tcom.example\tlib\t2023-12-01T00:00:00Z\t",
                "0.8\tnamed\tcom.example\tlib\t2023-11-01T00:00:00Z",
                "0.7\tautomatic\tcom.example\tlib\t2023-10-01T00:00:00Z"
        ) + "\n", StandardCharsets.UTF_8);

        ModuleSummary.Stats stats = ModuleSummary.compute(dataDir, Instant.parse("2024-04-01T00:00:00Z"), 25);

        ModuleVersionCoverage coverage = stats.moduleVersionCoverage();
        // Automatic rows (0.9 and 0.7) are excluded from every bucket since they have no
        // module-info to declare a version.
        assertThat(coverage.explicit()).isEqualTo(1L);     // 1.0: module-info "1.0" matches maven "1.0"
        assertThat(coverage.mismatching()).isEqualTo(1L);  // 1.1: module-info "9.9" differs from maven "1.1"
        assertThat(coverage.absent()).isEqualTo(1L);       // 1.2: named, module-info had no version
        assertThat(coverage.untracked()).isEqualTo(1L);    // 0.8: named, pre-feature 5-column row
    }

    @Test
    public void module_version_coverage_is_zero_when_modules_root_missing() throws IOException {
        ModuleSummary.Stats stats = ModuleSummary.compute(dataDir, Instant.parse("2024-04-01T00:00:00Z"), 25);

        ModuleVersionCoverage coverage = stats.moduleVersionCoverage();
        assertThat(coverage.explicit()).isZero();
        assertThat(coverage.mismatching()).isZero();
        assertThat(coverage.absent()).isZero();
        assertThat(coverage.untracked()).isZero();
    }

    @Test
    public void renders_module_info_version_coverage_table_in_summary_output() throws IOException {
        Path moduleDir = Files.createDirectories(dataDir.resolve("modules").resolve("com").resolve("example").resolve("lib"));
        Files.writeString(moduleDir.resolve("versions.tsv"),
                "1.0\tnamed\tcom.example\tlib\t2024-01-01T00:00:00Z\t1.0\n"
                        + "1.1\tnamed\tcom.example\tlib\t2024-02-01T00:00:00Z\t9.9\n",
                StandardCharsets.UTF_8);
        System.setProperty(ModuleSummary.PROP_DATA, dataDir.toString());
        Path output = dataDir.resolve("SUMMARY.md");
        System.setProperty(ModuleSummary.PROP_OUTPUT, output.toString());
        try {
            ModuleSummary.main(new String[0]);
        } finally {
            System.clearProperty(ModuleSummary.PROP_DATA);
            System.clearProperty(ModuleSummary.PROP_OUTPUT);
        }

        String content = Files.readString(output, StandardCharsets.UTF_8);
        assertThat(content).contains("## Module-info version coverage");
        assertThat(content).contains("| With explicit module version | 1 |");
        assertThat(content).contains("| With mismatching module version | 1 |");
        assertThat(content).contains("| Without module version | 0 |");
        assertThat(content).contains("| Untracked | 0 |");
    }
}
