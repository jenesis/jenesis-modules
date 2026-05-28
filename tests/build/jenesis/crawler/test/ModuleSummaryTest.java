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
                "0.7\tautomatic\tcom.example\tlib\t2023-10-01T00:00:00Z\t"
        ) + "\n", StandardCharsets.UTF_8);
        writeArtifactsMirror(moduleDir,
                "1.0\tnamed\tcom.example\tlib",
                "1.1\tnamed\tcom.example\tlib",
                "1.2\tnamed\tcom.example\tlib",
                "0.9\tautomatic\tcom.example\tlib",
                "0.7\tautomatic\tcom.example\tlib");

        ModuleSummary.Stats stats = ModuleSummary.compute(dataDir, Instant.parse("2024-04-01T00:00:00Z"), 25);

        ModuleVersionCoverage coverage = stats.moduleVersionCoverage();
        // Automatic rows (0.9 and 0.7) are excluded from every bucket since they have no
        // module-info to declare a version.
        assertThat(coverage.explicit()).isEqualTo(1L);     // 1.0: module-info "1.0" matches maven "1.0"
        assertThat(coverage.mismatching()).isEqualTo(1L);  // 1.1: module-info "9.9" differs from maven "1.1"
        assertThat(coverage.absent()).isEqualTo(1L);       // 1.2: named, module-info had no version
    }

    @Test
    public void module_version_coverage_is_zero_when_modules_root_missing() throws IOException {
        ModuleSummary.Stats stats = ModuleSummary.compute(dataDir, Instant.parse("2024-04-01T00:00:00Z"), 25);

        ModuleVersionCoverage coverage = stats.moduleVersionCoverage();
        assertThat(coverage.explicit()).isZero();
        assertThat(coverage.mismatching()).isZero();
        assertThat(coverage.absent()).isZero();
    }

    @Test
    public void renders_module_info_version_coverage_table_in_summary_output() throws IOException {
        Path moduleDir = Files.createDirectories(dataDir.resolve("modules").resolve("com").resolve("example").resolve("lib"));
        Files.writeString(moduleDir.resolve("versions.tsv"),
                "1.0\tnamed\tcom.example\tlib\t2024-01-01T00:00:00Z\t1.0\n"
                        + "1.1\tnamed\tcom.example\tlib\t2024-02-01T00:00:00Z\t9.9\n",
                StandardCharsets.UTF_8);
        writeArtifactsMirror(moduleDir,
                "1.0\tnamed\tcom.example\tlib",
                "1.1\tnamed\tcom.example\tlib");
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
        assertThat(content).contains("## `module-info` version field across named publications");
        assertThat(content).contains("| `module-info` version matches the Maven coordinate version | 1 |");
        assertThat(content).contains("| `module-info` version is non-empty but differs from the Maven coordinate version | 1 |");
        assertThat(content).contains("| `module-info` declared no version (Maven coordinate version is the only reference) | 0 |");
        assertThat(content).doesNotContain("Untracked");
    }

    @Test
    public void normalizes_well_known_error_variants_into_single_buckets() {
        assertThat(ModuleSummary.normalizeErrorMessage(
                "InvalidModuleDescriptorException: Package org.agrona.shadow.net.bytebuddy.build missing from ModulePackages class file attribute"))
                .isEqualTo("InvalidModuleDescriptorException: Package <PACKAGE> missing from ModulePackages class file attribute");
        assertThat(ModuleSummary.normalizeErrorMessage(
                "InvalidModuleDescriptorException: Package io.aeron.shadow.net.bytebuddy.build missing from ModulePackages class file attribute"))
                .isEqualTo("InvalidModuleDescriptorException: Package <PACKAGE> missing from ModulePackages class file attribute");
        assertThat(ModuleSummary.normalizeErrorMessage(
                "InvalidModuleDescriptorException: Unsupported major.minor version 59.65535"))
                .isEqualTo("InvalidModuleDescriptorException: Unsupported major.minor version <VERSION>");
        assertThat(ModuleSummary.normalizeErrorMessage(
                "InvalidModuleDescriptorException: AutoTuneServiceProvider: unnamed package"))
                .isEqualTo("InvalidModuleDescriptorException: <CLASS>: unnamed package");
        assertThat(ModuleSummary.normalizeErrorMessage(
                "InvalidModuleDescriptorException: ClassPathScannerFactory: is not a qualified name of a Java class in a named package"))
                .isEqualTo("InvalidModuleDescriptorException: <CLASS>: is not a qualified name of a Java class in a named package");
        // Only the entry index is normalized; kind and character are retained so distinct
        // illegal-character classes stay distinct.
        assertThat(ModuleSummary.normalizeErrorMessage(
                "InvalidModuleDescriptorException: CONSTANT_Package at entry 13 has illegal character: '.'"))
                .isEqualTo("InvalidModuleDescriptorException: CONSTANT_Package at entry <ENTRY> has illegal character: '.'");
        assertThat(ModuleSummary.normalizeErrorMessage(
                "InvalidModuleDescriptorException: CONSTANT_Class at entry 38 has illegal character: ';'"))
                .isEqualTo("InvalidModuleDescriptorException: CONSTANT_Class at entry <ENTRY> has illegal character: ';'");
        assertThat(ModuleSummary.normalizeErrorMessage(
                "IOException: invalid header field (line 9)"))
                .isEqualTo("IOException: invalid header field (line <LINE>)");
        // Status code is retained: a 404 and a 500 are different signals and should not collapse.
        assertThat(ModuleSummary.normalizeErrorMessage(
                "IOException: Tail request on https://repo1.maven.org/foo/bar.jar returned status 404"))
                .isEqualTo("IOException: Tail request on <URL> returned status 404");
        assertThat(ModuleSummary.normalizeErrorMessage(
                "IOException: Tail request on https://repo1.maven.org/foo/bar.jar returned status 500"))
                .isEqualTo("IOException: Tail request on <URL> returned status 500");
        assertThat(ModuleSummary.normalizeErrorMessage(
                "IllegalArgumentException: Illegal character in path at index 26: com/trendyol/kediatr-core/\"3.1.0\"/kediatr-core-\"3.1.0\".jar"))
                .isEqualTo("IllegalArgumentException: Illegal character in path at index <INDEX>: <PATH>");
        assertThat(ModuleSummary.normalizeErrorMessage(
                "InvalidModuleDescriptorException: this_class should be module-info"))
                .isEqualTo("InvalidModuleDescriptorException: this_class should be module-info");
    }

    @Test
    public void top_modules_excludes_classifier_variants_from_canonical_count() throws IOException {
        Path libDir = Files.createDirectories(dataDir.resolve("modules").resolve("com").resolve("example").resolve("lib"));
        Files.writeString(libDir.resolve("versions.tsv"),
                "1.0\tnamed\tcom.example\tlib\t2024-01-01T00:00:00Z\t1.0\n"
                        + "1.1\tnamed\tcom.example\tlib\t2024-02-01T00:00:00Z\t1.1\n",
                StandardCharsets.UTF_8);
        writeArtifactsMirror(libDir,
                "1.0\tnamed\tcom.example\tlib",
                "1.1\tnamed\tcom.example\tlib");
        Files.writeString(libDir.resolve("versions-jar-with-dependencies.tsv"),
                ("a\tnamed\tcom.example\tlib\t2024-01-01T00:00:00Z\t1.0\n"
                        + "b\tnamed\tcom.example\tlib\t2024-01-01T00:00:00Z\t1.0\n"
                        + "c\tnamed\tcom.example\tlib\t2024-01-01T00:00:00Z\t1.0\n"
                        + "d\tnamed\tcom.example\tlib\t2024-01-01T00:00:00Z\t1.0\n"
                        + "e\tnamed\tcom.example\tlib\t2024-01-01T00:00:00Z\t1.0\n"),
                StandardCharsets.UTF_8);
        Files.writeString(libDir.resolve("artifacts-jar-with-dependencies.tsv"),
                ("a\tnamed\tcom.example\tlib\n"
                        + "b\tnamed\tcom.example\tlib\n"
                        + "c\tnamed\tcom.example\tlib\n"
                        + "d\tnamed\tcom.example\tlib\n"
                        + "e\tnamed\tcom.example\tlib\n"),
                StandardCharsets.UTF_8);

        ModuleSummary.Stats stats = ModuleSummary.compute(dataDir, Instant.parse("2024-04-01T00:00:00Z"), 25);

        // The top list reports the canonical 2 versions, not the inflated 5 from the
        // jar-with-dependencies classifier.
        List<ModuleSummary.TopEntry> top = stats.top().modulesByVersionCount();
        assertThat(top).hasSize(1);
        assertThat(top.get(0).key()).isEqualTo("com.example.lib");
        assertThat(top.get(0).count()).isEqualTo(2L);
    }

    @Test
    public void top_modules_folds_awssdk_family_into_single_row() throws IOException {
        writeVersions("software.amazon.awssdk.annotations", 10);
        writeVersions("software.amazon.awssdk.auth", 10);
        writeVersions("software.amazon.awssdk.http", 7);
        writeVersions("com.example.unrelated", 20);

        ModuleSummary.Stats stats = ModuleSummary.compute(dataDir, Instant.parse("2024-04-01T00:00:00Z"), 25);

        List<ModuleSummary.TopEntry> top = stats.top().modulesByVersionCount();
        ModuleSummary.TopEntry awsRow = top.stream()
                .filter(e -> e.key().equals("software.amazon.awssdk.*"))
                .findFirst()
                .orElseThrow();
        assertThat(awsRow.count()).isEqualTo(10L);
        assertThat(awsRow.min()).isEqualTo(7L);
        assertThat(awsRow.members()).isEqualTo(3);
        assertThat(awsRow.isRange()).isTrue();
        assertThat(awsRow.isFold()).isTrue();
        assertThat(top).extracting(ModuleSummary.TopEntry::key)
                .doesNotContain("software.amazon.awssdk.annotations",
                        "software.amazon.awssdk.auth",
                        "software.amazon.awssdk.http");
    }

    @Test
    public void top_modules_fold_renders_single_count_when_all_equal() throws IOException {
        writeVersions("software.amazon.awssdk.a", 5);
        writeVersions("software.amazon.awssdk.b", 5);
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
        assertThat(content).contains("| `software.amazon.awssdk.* (2 modules)` | 5 |");
        assertThat(content).doesNotContain("[5, 5]");
    }

    @Test
    public void totals_include_scanned_and_non_module_artifact_counts() throws IOException {
        // Two module rows (one named, one automatic) so total version records = 2.
        Path moduleDir = Files.createDirectories(dataDir.resolve("modules").resolve("com").resolve("example").resolve("lib"));
        Files.writeString(moduleDir.resolve("versions.tsv"),
                "1.0\tnamed\tcom.example\tlib\t2024-01-01T00:00:00Z\t1.0\n"
                        + "0.9\tautomatic\tcom.example\tlib\t2023-12-01T00:00:00Z\t\n",
                StandardCharsets.UTF_8);
        writeArtifactsMirror(moduleDir,
                "1.0\tnamed\tcom.example\tlib",
                "0.9\tautomatic\tcom.example\tlib");
        // 5 scanned rows: 2 successes that produced modules (covered by versions.tsv above),
        // 2 successes that didn't yield a module, 1 failure. Expected:
        //   scannedArtifacts = 4 (5 rows minus the 1 failure), nonModuleArtifacts = 4 - 2 = 2.
        Path scannedDir = Files.createDirectories(dataDir.resolve("scanned").resolve("com.example"));
        Files.writeString(scannedDir.resolve("lib.tsv"), String.join("\n",
                "1.0\t\t\t",
                "0.9\t\t\t",
                "0.8\t\t\t",
                "0.7\tsources\t\t",
                "0.6\t\t\tIOException: boom"
        ) + "\n", StandardCharsets.UTF_8);

        ModuleSummary.Stats stats = ModuleSummary.compute(dataDir, Instant.parse("2024-04-01T00:00:00Z"), 25);

        assertThat(stats.totals().scannedArtifacts()).isEqualTo(4L);
        assertThat(stats.totals().nonModuleArtifacts()).isEqualTo(2L);
    }

    @Test
    public void totals_include_named_and_module_version_rows() throws IOException {
        Path moduleDir = Files.createDirectories(dataDir.resolve("modules").resolve("com").resolve("example").resolve("lib"));
        Files.writeString(moduleDir.resolve("versions.tsv"), String.join("\n",
                "1.0\tnamed\tcom.example\tlib\t2024-01-01T00:00:00Z\t1.0",       // explicit
                "1.1\tnamed\tcom.example\tlib\t2024-02-01T00:00:00Z\t9.9",       // mismatching
                "1.2\tnamed\tcom.example\tlib\t2024-03-01T00:00:00Z\t",          // absent
                "0.9\tautomatic\tcom.example\tlib\t2023-12-01T00:00:00Z\t",
                "0.8\tautomatic\tcom.example\tlib\t2023-11-01T00:00:00Z\t"
        ) + "\n", StandardCharsets.UTF_8);
        writeArtifactsMirror(moduleDir,
                "1.0\tnamed\tcom.example\tlib",
                "1.1\tnamed\tcom.example\tlib",
                "1.2\tnamed\tcom.example\tlib",
                "0.9\tautomatic\tcom.example\tlib",
                "0.8\tautomatic\tcom.example\tlib");

        ModuleSummary.Stats stats = ModuleSummary.compute(dataDir, Instant.parse("2024-04-01T00:00:00Z"), 25);

        assertThat(stats.totals().namedVersionRows()).isEqualTo(3L);
        assertThat(stats.totals().automaticVersionRows()).isEqualTo(2L);
        // Rows with any non-empty module-info version (explicit + mismatching).
        assertThat(stats.totals().namedVersionRowsWithModuleVersion()).isEqualTo(2L);
        assertThat(stats.totals().distinctModulesWithModuleVersion()).isEqualTo(1);
    }

    @Test
    public void monthly_publications_cover_last_twelve_months_including_empty_buckets() throws IOException {
        Path moduleDir = Files.createDirectories(dataDir.resolve("modules").resolve("com").resolve("example").resolve("lib"));
        Files.writeString(moduleDir.resolve("versions.tsv"), String.join("\n",
                // March 2026: 2 named versions + 1 automatic, all of the single module com.example.lib
                // -> distinct named names = 1, distinct automatic names = 1.
                "1.0\tnamed\tcom.example\tlib\t2026-03-05T00:00:00Z\t1.0",
                "1.1\tnamed\tcom.example\tlib\t2026-03-20T00:00:00Z\t1.1",
                "0.9\tautomatic\tcom.example\tlib\t2026-03-10T00:00:00Z\t",
                // February 2026: 1 named
                "0.8\tnamed\tcom.example\tlib\t2026-02-15T00:00:00Z\t",
                // Way out of window: should be ignored
                "0.1\tnamed\tcom.example\tlib\t2020-01-01T00:00:00Z\t"
        ) + "\n", StandardCharsets.UTF_8);
        writeArtifactsMirror(moduleDir,
                "1.0\tnamed\tcom.example\tlib",
                "1.1\tnamed\tcom.example\tlib",
                "0.9\tautomatic\tcom.example\tlib",
                "0.8\tnamed\tcom.example\tlib",
                "0.1\tnamed\tcom.example\tlib");

        ModuleSummary.Stats stats = ModuleSummary.compute(dataDir, Instant.parse("2026-05-25T00:00:00Z"), 25);

        List<ModuleSummary.MonthlyPublication> monthly = stats.monthlyPublications();
        assertThat(monthly).hasSize(12);
        assertThat(monthly.get(0).month()).isEqualTo(YearMonth.of(2025, 6));
        assertThat(monthly.get(11).month()).isEqualTo(YearMonth.of(2026, 5));
        // Find the buckets we wrote data into.
        ModuleSummary.MonthlyPublication march = monthly.stream()
                .filter(m -> m.month().equals(YearMonth.of(2026, 3)))
                .findFirst().orElseThrow();
        // Distinct module names, not publication rows: the two named versions collapse to one name.
        assertThat(march.named()).isEqualTo(1L);
        assertThat(march.automatic()).isEqualTo(1L);
        ModuleSummary.MonthlyPublication february = monthly.stream()
                .filter(m -> m.month().equals(YearMonth.of(2026, 2)))
                .findFirst().orElseThrow();
        assertThat(february.named()).isEqualTo(1L);
        assertThat(february.automatic()).isZero();
        // 2020 row outside the 12-month window is not visible here, but it counted in totals.
        assertThat(stats.totals().namedVersionRows()).isEqualTo(4L);
    }

    @Test
    public void monthly_and_recent_non_modular_counts_distinct_artifacts() throws IOException {
        // One modular artifact (com.example:lib) that publishes a named and an automatic version
        // in 2026-05 - distinct named name = 1, distinct automatic name = 1, both the same module.
        Path moduleDir = Files.createDirectories(dataDir.resolve("modules").resolve("com").resolve("example").resolve("lib"));
        Files.writeString(moduleDir.resolve("versions.tsv"), String.join("\n",
                "1.0\tnamed\tcom.example\tlib\t2026-05-20T00:00:00Z\t1.0",
                "0.9\tautomatic\tcom.example\tlib\t2026-05-21T00:00:00Z\t"
        ) + "\n", StandardCharsets.UTF_8);
        writeArtifactsMirror(moduleDir,
                "1.0\tnamed\tcom.example\tlib",
                "0.9\tautomatic\tcom.example\tlib");
        // Three distinct scanned artifacts published in 2026-05. com.example:lib is the modular
        // one (multiple successful rows + a failure that must not count); org.foo:bar and
        // org.baz:qux are non-modular (no versions.tsv). Distinct scanned = 3, distinct modular
        // = 1, so non-modular = 2.
        Path scannedLib = Files.createDirectories(dataDir.resolve("scanned").resolve("com").resolve("example"));
        Files.writeString(scannedLib.resolve("lib.tsv"), String.join("\n",
                "1.0\t\t2026-05-20T00:00:00Z\t",
                "0.9\t\t2026-05-21T00:00:00Z\t",
                "0.5\t\t2026-05-19T00:00:00Z\tIOException: boom"
        ) + "\n", StandardCharsets.UTF_8);
        Path scannedFoo = Files.createDirectories(dataDir.resolve("scanned").resolve("org").resolve("foo"));
        Files.writeString(scannedFoo.resolve("bar.tsv"), String.join("\n",
                "2.0\t\t2026-05-22T00:00:00Z\t",
                "2.1\t\t2026-05-23T00:00:00Z\t"
        ) + "\n", StandardCharsets.UTF_8);
        Path scannedBaz = Files.createDirectories(dataDir.resolve("scanned").resolve("org").resolve("baz"));
        Files.writeString(scannedBaz.resolve("qux.tsv"),
                "3.0\t\t2026-05-24T00:00:00Z\t\n", StandardCharsets.UTF_8);

        // Generated 2026-05-25: the whole 2026-05 activity is inside the 7-day recent window.
        ModuleSummary.Stats stats = ModuleSummary.compute(dataDir, Instant.parse("2026-05-25T00:00:00Z"), 25);

        ModuleSummary.MonthlyPublication may = stats.monthlyPublications().stream()
                .filter(m -> m.month().equals(YearMonth.of(2026, 5)))
                .findFirst().orElseThrow();
        assertThat(may.named()).isEqualTo(1L);
        assertThat(may.automatic()).isEqualTo(1L);
        // 3 distinct scanned artifacts minus 1 distinct modular artifact = 2 non-modular.
        assertThat(may.nonModular()).isEqualTo(2L);
        // Same distinct-artifact subtraction over the 7-day window.
        assertThat(stats.recent().nonModularArtifacts()).isEqualTo(2L);
    }

    private void writeVersions(String moduleName, int count) throws IOException {
        Path dir = dataDir.resolve("modules");
        for (String segment : moduleName.split("\\.")) {
            dir = dir.resolve(segment);
        }
        Files.createDirectories(dir);
        StringBuilder versions = new StringBuilder();
        StringBuilder current = new StringBuilder();
        for (int i = 0; i < count; i++) {
            versions.append(i).append(".0\tnamed\tcom.example\t").append(moduleName).append("\t2024-01-01T00:00:00Z\t").append(i).append(".0\n");
            current.append(i).append(".0\tnamed\tcom.example\t").append(moduleName).append('\n');
        }
        Files.writeString(dir.resolve("versions.tsv"), versions.toString(), StandardCharsets.UTF_8);
        Files.writeString(dir.resolve("artifacts.tsv"), current.toString(), StandardCharsets.UTF_8);
    }

    private static void writeArtifactsMirror(Path moduleDir, String... entries) throws IOException {
        Files.writeString(moduleDir.resolve("artifacts.tsv"), String.join("\n", entries) + "\n", StandardCharsets.UTF_8);
    }
}
