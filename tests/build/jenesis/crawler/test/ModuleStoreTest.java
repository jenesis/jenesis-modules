package build.jenesis.crawler.test;

import module java.base;
import build.jenesis.crawler.model.Coordinate;
import build.jenesis.crawler.model.ModuleEntry;
import build.jenesis.crawler.model.ModuleType;
import build.jenesis.crawler.model.Version;
import build.jenesis.crawler.store.ModuleStore;
import module org.junit.jupiter.api;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ModuleStoreTest {

    @TempDir
    Path root;

    @Test
    public void freshly_recorded_row_carries_module_info_version_column() throws IOException {
        ModuleStore store = new ModuleStore(root);
        store.record("com.example.lib", ModuleType.NAMED, "4.2.0", ts("com.example", "lib", "1.0", null, 1_700_000_000_000L));
        store.record("com.example.lib", ModuleType.NAMED, null, ts("com.example", "lib", "1.1", null, 1_700_000_001_000L));
        store.flush();

        Path file = root.resolve("com").resolve("example").resolve("lib").resolve("versions.tsv");
        List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
        // Both rows are in the 6-column format. A null moduleVersion becomes an empty trailing
        // column ("scanned, module-info had no version") while a non-empty one is written
        // verbatim.
        assertThat(lines).hasSize(2);
        assertThat(lines.get(0)).endsWith("\t4.2.0");
        assertThat(lines.get(1)).endsWith("\t");
        assertThat(lines.get(1).chars().filter(c -> c == '\t').count()).isEqualTo(5L);
    }

    @Test
    public void records_append_chronologically_to_versions_tsv() throws IOException {
        ModuleStore store = new ModuleStore(root);
        store.record("com.example.lib", ModuleType.NAMED, null, ts("com.example", "lib", "2.0", null, 1_700_000_000_000L));
        store.record("com.example.lib", ModuleType.NAMED, null, ts("com.example", "lib", "1.0", null, 1_690_000_000_000L));
        store.record("com.example.lib", ModuleType.AUTOMATIC, null, ts("com.example", "lib", "0.9", null, 1_680_000_000_000L));
        store.flush();

        Path file = root.resolve("com").resolve("example").resolve("lib").resolve("versions.tsv");
        List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
        // chronological: oldest publishedAt first
        assertThat(lines).hasSize(3);
        assertThat(lines.get(0)).startsWith("0.9\tautomatic\tcom.example\tlib\t");
        assertThat(lines.get(1)).startsWith("1.0\tnamed\tcom.example\tlib\t");
        assertThat(lines.get(2)).startsWith("2.0\tnamed\tcom.example\tlib\t");
    }

    @Test
    public void prefix_module_and_child_module_coexist_without_path_conflict() throws IOException {
        ModuleStore store = new ModuleStore(root);
        store.record("jakarta.servlet", ModuleType.NAMED, null, ts("jakarta.servlet", "jakarta.servlet-api", "5.0", null, 1L));
        store.record("jakarta.servlet.api", ModuleType.NAMED, null, ts("jakarta.servlet", "jakarta.servlet-api", "6.0", null, 1L));
        store.flush();

        Path prefix = root.resolve("jakarta").resolve("servlet").resolve("versions.tsv");
        Path child = root.resolve("jakarta").resolve("servlet").resolve("api").resolve("versions.tsv");

        assertThat(prefix).exists();
        assertThat(child).exists();
        assertThat(Files.readAllLines(prefix)).hasSize(1);
        assertThat(Files.readAllLines(child)).hasSize(1);
    }

    @Test
    public void appends_to_existing_versions_tsv_without_dropping_entries() throws IOException {
        ModuleStore first = new ModuleStore(root);
        first.record("alpha.module", ModuleType.NAMED, null, ts("a", "alpha", "1.0", null, 1_600_000_000_000L));
        first.flush();

        ModuleStore second = new ModuleStore(root);
        second.record("alpha.module", ModuleType.NAMED, null, ts("a", "alpha", "2.0", null, 1_700_000_000_000L));
        second.flush();

        List<String> lines = Files.readAllLines(root.resolve("alpha").resolve("module").resolve("versions.tsv"));
        assertThat(lines).hasSize(2);
        assertThat(lines.get(0)).startsWith("1.0\tnamed\ta\talpha\t");
        assertThat(lines.get(1)).startsWith("2.0\tnamed\ta\talpha\t");
    }

    @Test
    public void classifier_is_appended_to_leaf_file_name() throws IOException {
        ModuleStore store = new ModuleStore(root);
        store.record("widget.core", ModuleType.NAMED, null, ts("org.widget", "core", "1.0", "jakarta", 1L));
        store.flush();

        Path file = root.resolve("widget").resolve("core").resolve("versions-jakarta.tsv");
        assertThat(file).exists();
        assertThat(Files.readAllLines(file)).hasSize(1);
    }

    @Test
    public void skips_record_with_sentinel_timestamp() throws IOException {
        ModuleStore store = new ModuleStore(root);
        boolean recorded = store.record("untimed.module", ModuleType.NAMED, null,
                new Coordinate("com.example", "untimed", "1.0", null, "jar", 0L, 0L));

        assertThat(recorded).isFalse();
        store.flush();
        assertThat(root.resolve("untimed").resolve("module").resolve("versions.tsv"))
                .doesNotExist();
    }

    @Test
    public void record_does_not_apply_owners_filter() throws IOException {
        Path dir = Files.createDirectories(root.resolve("guarded").resolve("module"));
        Files.writeString(dir.resolve("owners.tsv"), "trusted.group\n");

        ModuleStore store = new ModuleStore(root);
        assertThat(store.record("guarded.module", ModuleType.NAMED, null, ts("trusted.group", "x", "1.0", null, 1L))).isTrue();
        assertThat(store.record("guarded.module", ModuleType.NAMED, null, ts("hostile.group", "x", "1.0", null, 2L))).isTrue();
        store.flush();

        // versions.tsv is the audit log - both rows are present.
        assertThat(Files.readAllLines(dir.resolve("versions.tsv"))).hasSize(2);
    }

    @Test
    public void regenerate_writes_artifacts_tsv_using_implicit_owner_when_no_owners_file() throws IOException {
        ModuleStore store = new ModuleStore(root);
        store.record("contested.module", ModuleType.NAMED, null, ts("canonical.org", "lib", "1.0", null, 1_700_000_000_000L));
        store.record("contested.module", ModuleType.NAMED, null, ts("canonical.org", "lib", "3.0", null, 1_710_000_000_000L));
        store.record("contested.module", ModuleType.NAMED, null, ts("hostile.org", "imposter", "4.0", null, 1_720_000_000_000L));
        store.flush();

        store.regenerate("contested.module");

        Path current = root.resolve("contested").resolve("module").resolve("artifacts.tsv");
        List<String> lines = Files.readAllLines(current, StandardCharsets.UTF_8);
        // artifacts.tsv has only the implicit owner's rows (canonical.org), sorted version desc, 4 columns (no timestamp).
        assertThat(lines).containsExactly(
                "3.0\tnamed\tcanonical.org\tlib",
                "1.0\tnamed\tcanonical.org\tlib");
    }

    @Test
    public void regenerate_writes_artifacts_tsv_constrained_by_owners_file() throws IOException {
        ModuleStore store = new ModuleStore(root);
        store.record("guarded.module", ModuleType.NAMED, null, ts("a.org", "lib", "1.0", null, 1_700_000_000_000L));
        store.record("guarded.module", ModuleType.NAMED, null, ts("b.org", "lib", "2.0", null, 1_710_000_000_000L));
        store.record("guarded.module", ModuleType.NAMED, null, ts("c.org", "lib", "3.0", null, 1_720_000_000_000L));
        store.flush();

        Path moduleDir = root.resolve("guarded").resolve("module");
        Files.writeString(moduleDir.resolve("owners.tsv"), "a.org\nb.org\tlib\n");

        store.regenerate("guarded.module");

        List<String> lines = Files.readAllLines(moduleDir.resolve("artifacts.tsv"));
        // c.org is filtered out by owners.tsv; a.org and b.org both pass; sorted version desc.
        assertThat(lines).containsExactly(
                "2.0\tnamed\tb.org\tlib",
                "1.0\tnamed\ta.org\tlib");
    }

    @Test
    public void regenerate_picks_oldest_publication_when_multiple_owners_share_a_version() throws IOException {
        ModuleStore store = new ModuleStore(root);
        // both publishers ship version 1.0; canonical was first
        store.record("shared.module", ModuleType.NAMED, null, ts("canonical.org", "lib", "1.0", null, 1_700_000_000_000L));
        store.record("shared.module", ModuleType.NAMED, null, ts("vendor.org", "lib", "1.0", null, 1_720_000_000_000L));
        store.flush();

        Path moduleDir = root.resolve("shared").resolve("module");
        Files.writeString(moduleDir.resolve("owners.tsv"), "canonical.org\nvendor.org\n");

        store.regenerate("shared.module");

        // For the shared version 1.0, the row with the oldest publishedAt wins.
        assertThat(Files.readAllLines(moduleDir.resolve("artifacts.tsv")))
                .containsExactly("1.0\tnamed\tcanonical.org\tlib");
    }

    @Test
    public void regenerate_deletes_artifacts_tsv_when_policy_filters_out_everything() throws IOException {
        ModuleStore store = new ModuleStore(root);
        store.record("blocked.module", ModuleType.NAMED, null, ts("a.org", "lib", "1.0", null, 1L));
        store.flush();

        Path moduleDir = root.resolve("blocked").resolve("module");
        Files.writeString(moduleDir.resolve("owners.tsv"), ""); // empty allowlist - reject everything

        store.regenerate("blocked.module");

        assertThat(moduleDir.resolve("artifacts.tsv")).doesNotExist();
        // versions.tsv stays as the audit log
        assertThat(moduleDir.resolve("versions.tsv")).exists();
    }

    @Test
    public void regenerate_handles_classifier_variants_independently() throws IOException {
        ModuleStore store = new ModuleStore(root);
        store.record("classy.module", ModuleType.NAMED, null, ts("canon.org", "lib", "1.0", null, 1L));
        store.record("classy.module", ModuleType.NAMED, null, ts("canon.org", "lib", "1.0", "jakarta", 1L));
        store.flush();

        store.regenerate("classy.module");

        Path dir = root.resolve("classy").resolve("module");
        assertThat(Files.readAllLines(dir.resolve("artifacts.tsv")))
                .containsExactly("1.0\tnamed\tcanon.org\tlib");
        assertThat(Files.readAllLines(dir.resolve("artifacts-jakarta.tsv")))
                .containsExactly("1.0\tnamed\tcanon.org\tlib");
    }

    @Test
    public void regenerate_writes_modules_tsv_keyed_by_module_info_version() throws IOException {
        ModuleStore store = new ModuleStore(root);
        // Two Maven versions declare module-info version "1.0" - first publish wins for modules.tsv.
        store.record("repeat.module", ModuleType.NAMED, "1.0", ts("canon.org", "lib", "1.0", null, 1_700_000_000_000L));
        store.record("repeat.module", ModuleType.NAMED, "1.0", ts("canon.org", "lib", "1.0.0.1", null, 1_710_000_000_000L));
        // Distinct module-info version - gets its own row.
        store.record("repeat.module", ModuleType.NAMED, "2.0", ts("canon.org", "lib", "2.0", null, 1_720_000_000_000L));
        // Empty module-info version - falls back to the Maven version.
        store.record("repeat.module", ModuleType.NAMED, "", ts("canon.org", "lib", "3.0", null, 1_730_000_000_000L));
        store.flush();

        store.regenerate("repeat.module");

        Path modules = root.resolve("repeat").resolve("module").resolve("modules.tsv");
        // Sorted by moduleVersion desc, 4 cols: moduleVersion, groupId, artifactId, mavenVersion.
        // module-version 1.0 maps to the OLDER Maven coordinate (1.0), not 1.0.0.1.
        assertThat(Files.readAllLines(modules)).containsExactly(
                "3.0\tcanon.org\tlib\t3.0",
                "2.0\tcanon.org\tlib\t2.0",
                "1.0\tcanon.org\tlib\t1.0");
    }

    @Test
    public void regenerate_omits_modules_tsv_when_owner_publishes_only_automatic() throws IOException {
        ModuleStore store = new ModuleStore(root);
        store.record("automatic.only", ModuleType.AUTOMATIC, "", ts("canon.org", "lib", "1.0", null, 1_700_000_000_000L));
        store.record("automatic.only", ModuleType.AUTOMATIC, "", ts("canon.org", "lib", "2.0", null, 1_710_000_000_000L));
        store.flush();

        store.regenerate("automatic.only");

        Path dir = root.resolve("automatic").resolve("only");
        // artifacts.tsv still gets written - automatic rows belong there.
        assertThat(dir.resolve("artifacts.tsv")).exists();
        // modules.tsv is intentionally absent - automatic modules have no module-info version.
        assertThat(dir.resolve("modules.tsv")).doesNotExist();
    }

    @Test
    public void regenerate_deletes_stale_legacy_current_tsv() throws IOException {
        Path moduleDir = Files.createDirectories(root.resolve("legacy").resolve("module"));
        // Hand-write a pre-rename current.tsv so we can verify it gets swept.
        Files.writeString(moduleDir.resolve("current.tsv"), "0.9\tnamed\tg\ta\n",
                StandardCharsets.UTF_8);
        Files.writeString(moduleDir.resolve("current-jakarta.tsv"), "0.9\tnamed\tg\ta\n",
                StandardCharsets.UTF_8);

        ModuleStore store = new ModuleStore(root);
        store.record("legacy.module", ModuleType.NAMED, null, ts("g", "a", "1.0", null, 1_700_000_000_000L));
        store.flush();
        store.regenerate("legacy.module");

        assertThat(moduleDir.resolve("current.tsv")).doesNotExist();
        assertThat(moduleDir.resolve("current-jakarta.tsv")).doesNotExist();
        assertThat(moduleDir.resolve("artifacts.tsv")).exists();
    }

    @Test
    public void read_returns_in_chronological_order() throws IOException {
        ModuleStore store = new ModuleStore(root);
        store.record("rt.module", ModuleType.NAMED, null, ts("g", "a", "1.0", null, 1_700_000_000_000L));
        store.record("rt.module", ModuleType.AUTOMATIC, null, ts("g2", "a2", "0.5", null, 1_600_000_000_000L));
        store.flush();

        NavigableSet<ModuleEntry> entries = new ModuleStore(root).read("rt.module", null);

        assertThat(entries).extracting(ModuleEntry::mavenVersion).extracting(Version::raw)
                .containsExactly("0.5", "1.0");
        assertThat(entries.first().type()).isEqualTo(ModuleType.AUTOMATIC);
    }

    @Test
    public void rejects_module_name_with_dash() {
        assertThatThrownBy(() -> new ModuleStore.StoreKey("not-allowed", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid module name");
    }

    @Test
    public void rejects_module_name_with_empty_segment() {
        assertThatThrownBy(() -> new ModuleStore.StoreKey("foo..bar", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid module name");
    }

    @Test
    public void rejects_module_name_starting_with_digit() {
        assertThatThrownBy(() -> new ModuleStore.StoreKey("9foo", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid module name");
    }

    @Test
    public void rejects_module_name_that_is_a_reserved_word() {
        assertThatThrownBy(() -> new ModuleStore.StoreKey("class", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid module name");
    }

    private static Coordinate ts(String groupId, String artifactId, String version, String classifier, long publishedAt) {
        return new Coordinate(groupId, artifactId, version, classifier, "jar", 0L, publishedAt);
    }
}
