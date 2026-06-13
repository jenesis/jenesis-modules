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
        store.record("com.example.svc", ModuleType.NAMED, null, ts("com.example", "svc-api", "5.0", null, 1L));
        store.record("com.example.svc.api", ModuleType.NAMED, null, ts("com.example", "svc-api", "6.0", null, 1L));
        store.flush();

        Path prefix = root.resolve("com").resolve("example").resolve("svc").resolve("versions.tsv");
        Path child = root.resolve("com").resolve("example").resolve("svc").resolve("api").resolve("versions.tsv");

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
        Files.writeString(dir.resolve("owners.tsv"), "trusted.group\tallowed\n");

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
        Files.writeString(moduleDir.resolve("owners.tsv"), "a.org\tallowed\nb.org:lib\tallowed\n");

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
        Files.writeString(moduleDir.resolve("owners.tsv"), "canonical.org\tallowed\nvendor.org\tallowed\n");

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
    public void owners_policy_parses_allowed_and_rejected_and_blocks_resolution() throws IOException {
        ModuleStore store = new ModuleStore(root);
        store.record("decided.module", ModuleType.NAMED, null, ts("good.org", "lib", "1.0", null, 1_700_000_000_000L));
        store.record("decided.module", ModuleType.NAMED, null, ts("bad.org", "imposter", "2.0", null, 1_710_000_000_000L));
        store.flush();

        Path dir = root.resolve("decided").resolve("module");
        Files.writeString(dir.resolve("owners.tsv"), "good.org\tallowed\nbad.org\trejected\n");

        ModuleStore.OwnersPolicy policy = store.loadOwners("decided.module").orElseThrow();
        assertThat(policy.allows("good.org", "lib")).isTrue();
        assertThat(policy.allows("bad.org", "imposter")).isFalse();
        // Both groups are "named" (decided), so the module is no longer drift.
        assertThat(policy.namedGroups()).containsExactlyInAnyOrder("good.org", "bad.org");

        store.regenerate("decided.module");
        // A rejected group is excluded from resolution exactly like an unlisted one.
        assertThat(Files.readAllLines(dir.resolve("artifacts.tsv")))
                .containsExactly("1.0\tnamed\tgood.org\tlib");
    }

    @Test
    public void owners_policy_rejects_a_line_without_a_decision() throws IOException {
        ModuleStore store = new ModuleStore(root);
        store.record("strict.module", ModuleType.NAMED, null, ts("g", "a", "1.0", null, 1L));
        store.flush();
        Path dir = root.resolve("strict").resolve("module");
        Files.writeString(dir.resolve("owners.tsv"), "g\n"); // missing the allowed|rejected column

        assertThatThrownBy(() -> store.loadOwners("strict.module"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("allowed|rejected");
    }

    @Test
    public void regenerate_keeps_classifier_variant_published_by_the_owner() throws IOException {
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
    public void regenerate_drops_classifier_variant_published_only_by_a_non_owner() throws IOException {
        ModuleStore store = new ModuleStore(root);
        // canon.org owns the module - it published first, as the main jar. A shaded variant under a
        // different coordinate bundles the same module name, but ownership spans the whole space, so
        // its classifier view resolves to nothing and is removed.
        store.record("shaded.module", ModuleType.NAMED, null, ts("canon.org", "lib", "1.0", null, 1_700_000_000_000L));
        store.record("shaded.module", ModuleType.NAMED, null, ts("bundler.org", "fat", "9.0", "all", 1_710_000_000_000L));
        store.flush();

        store.regenerate("shaded.module");

        Path dir = root.resolve("shaded").resolve("module");
        assertThat(Files.readAllLines(dir.resolve("artifacts.tsv")))
                .containsExactly("1.0\tnamed\tcanon.org\tlib");
        assertThat(dir.resolve("artifacts-all.tsv")).doesNotExist();
    }

    @Test
    public void regenerate_owner_is_determined_across_classifier_publications() throws IOException {
        ModuleStore store = new ModuleStore(root);
        // real.org publishes a native classifier FIRST, then its main jar; a squatter slips a main
        // jar in between. The oldest publication anywhere in the space owns the module, so real.org
        // owns it and the squatter's main-jar row is filtered out of the main view.
        store.record("platform.module", ModuleType.NAMED, null, ts("real.org", "lib", "1.0", "linux", 1_700_000_000_000L));
        store.record("platform.module", ModuleType.NAMED, null, ts("squatter.org", "imposter", "5.0", null, 1_710_000_000_000L));
        store.record("platform.module", ModuleType.NAMED, null, ts("real.org", "lib", "1.0", null, 1_720_000_000_000L));
        store.flush();

        store.regenerate("platform.module");

        Path dir = root.resolve("platform").resolve("module");
        assertThat(Files.readAllLines(dir.resolve("artifacts.tsv")))
                .containsExactly("1.0\tnamed\treal.org\tlib");
        assertThat(Files.readAllLines(dir.resolve("artifacts-linux.tsv")))
                .containsExactly("1.0\tnamed\treal.org\tlib");
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
    public void regenerate_drops_mismatching_module_info_rows_from_modules_tsv() throws IOException {
        ModuleStore store = new ModuleStore(root);
        // Maven 1.0 publishes module-info version "1.0" - matches, kept.
        store.record("filter.module", ModuleType.NAMED, "1.0", ts("canon.org", "lib", "1.0", null, 1_700_000_000_000L));
        // Maven 1.0.0.1 publishes module-info version "1.0" - the JAR's module-info contradicts
        // the Maven coordinate, so it must not show up in modules.tsv (it's the case the new
        // policy targets: consumers shouldn't see module version "1.0" mapped to Maven 1.0.0.1).
        store.record("filter.module", ModuleType.NAMED, "1.0", ts("canon.org", "lib", "1.0.0.1", null, 1_710_000_000_000L));
        // Maven 2.0 publishes empty module-info version (the "absent" case), still kept because
        // the row carries no contradictory version - the Maven coordinate is the only reference.
        store.record("filter.module", ModuleType.NAMED, "", ts("canon.org", "lib", "2.0", null, 1_720_000_000_000L));
        // Maven 3.0 publishes a contradictory module-info version "9.9" - dropped.
        store.record("filter.module", ModuleType.NAMED, "9.9", ts("canon.org", "lib", "3.0", null, 1_730_000_000_000L));
        store.flush();

        store.regenerate("filter.module");

        Path dir = root.resolve("filter").resolve("module");
        // artifacts.tsv keeps every Maven version - the policy doesn't touch the artifact view.
        assertThat(Files.readAllLines(dir.resolve("artifacts.tsv"))).containsExactly(
                "3.0\tnamed\tcanon.org\tlib",
                "2.0\tnamed\tcanon.org\tlib",
                "1.0.0.1\tnamed\tcanon.org\tlib",
                "1.0\tnamed\tcanon.org\tlib");
        // modules.tsv keeps only matching-or-absent rows.
        assertThat(Files.readAllLines(dir.resolve("modules.tsv"))).containsExactly(
                "2.0\tcanon.org\tlib\t2.0",
                "1.0\tcanon.org\tlib\t1.0");
    }

    @Test
    public void regenerate_deletes_modules_tsv_when_every_named_row_mismatches() throws IOException {
        ModuleStore store = new ModuleStore(root);
        store.record("allmismatch.module", ModuleType.NAMED, "9.9", ts("canon.org", "lib", "1.0", null, 1_700_000_000_000L));
        store.record("allmismatch.module", ModuleType.NAMED, "9.9", ts("canon.org", "lib", "2.0", null, 1_710_000_000_000L));
        store.flush();

        store.regenerate("allmismatch.module");

        Path dir = root.resolve("allmismatch").resolve("module");
        // The Maven view still resolves.
        assertThat(dir.resolve("artifacts.tsv")).exists();
        // No defensible modules.tsv: every row would mis-represent the Maven version.
        assertThat(dir.resolve("modules.tsv")).doesNotExist();
    }

    @Test
    public void regenerate_mismatch_filter_does_not_shift_implicit_owner() throws IOException {
        ModuleStore store = new ModuleStore(root);
        // First publisher wins implicit ownership by oldest publishedAt - even though every one
        // of its rows is mismatching (and so won't survive into modules.tsv), it must still hold
        // the module: ownership decisions read the full versions.tsv, the mismatch filter only
        // gates which of the owner's rows reach the resolved view.
        store.record("owned.module", ModuleType.NAMED, "9.9", ts("first.org", "lib", "1.0", null, 1_700_000_000_000L));
        store.record("owned.module", ModuleType.NAMED, "1.0", ts("second.org", "lib", "1.0", null, 1_710_000_000_000L));
        store.flush();

        store.regenerate("owned.module");

        Path dir = root.resolve("owned").resolve("module");
        // first.org owns the module; its single mismatching row lands in artifacts.tsv.
        assertThat(Files.readAllLines(dir.resolve("artifacts.tsv"))).containsExactly(
                "1.0\tnamed\tfirst.org\tlib");
        // second.org's matching row is filtered out because it lost ownership, not because of
        // the mismatch policy. modules.tsv is absent because first.org's only row is mismatching.
        assertThat(dir.resolve("modules.tsv")).doesNotExist();
    }

    @Test
    public void regenerate_scope_artifacts_leaves_existing_modules_tsv_untouched() throws IOException {
        ModuleStore store = new ModuleStore(root);
        store.record("scope.module", ModuleType.NAMED, "1.0", ts("g", "a", "1.0", null, 1_700_000_000_000L));
        store.flush();
        Path dir = root.resolve("scope").resolve("module");
        // Seed a placeholder modules.tsv that the policy would normally overwrite. Scope.ARTIFACTS
        // must leave it alone so a caller that only wants to recompute artifacts.tsv can do so.
        Files.writeString(dir.resolve("modules.tsv"), "placeholder content\n", StandardCharsets.UTF_8);

        store.regenerate("scope.module", ModuleStore.Scope.ARTIFACTS);

        assertThat(Files.readAllLines(dir.resolve("artifacts.tsv"))).containsExactly(
                "1.0\tnamed\tg\ta");
        assertThat(Files.readString(dir.resolve("modules.tsv"), StandardCharsets.UTF_8))
                .isEqualTo("placeholder content\n");
    }

    @Test
    public void regenerate_scope_modules_leaves_existing_artifacts_tsv_untouched() throws IOException {
        ModuleStore store = new ModuleStore(root);
        store.record("scope.module", ModuleType.NAMED, "1.0", ts("g", "a", "1.0", null, 1_700_000_000_000L));
        store.flush();
        Path dir = root.resolve("scope").resolve("module");
        Files.writeString(dir.resolve("artifacts.tsv"), "placeholder content\n", StandardCharsets.UTF_8);

        store.regenerate("scope.module", ModuleStore.Scope.MODULES);

        assertThat(Files.readString(dir.resolve("artifacts.tsv"), StandardCharsets.UTF_8))
                .isEqualTo("placeholder content\n");
        assertThat(Files.readAllLines(dir.resolve("modules.tsv"))).containsExactly(
                "1.0\tg\ta\t1.0");
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

    @Test
    public void regenerate_excludes_jdk_bundled_module_from_modules_tsv() throws IOException {
        ModuleStore store = new ModuleStore(root);
        // A third party (here a JDK-reimplementation) declares the module name java.sql. The name is
        // bundled with the JVM, so it can never resolve as that module on the module path. It must
        // not feed modules.tsv, but the audit log and the coordinate-keyed Maven proxy stay intact.
        store.record("java.sql", ModuleType.NAMED, "1.0", ts("org.example.rt", "rt-java.sql", "1.0", null, 1_700_000_000_000L));
        store.flush();

        store.regenerate("java.sql");

        Path dir = root.resolve("java").resolve("sql");
        assertThat(dir.resolve("versions.tsv")).exists();
        // artifacts.tsv is a transparent Maven proxy keyed by coordinate - left untouched.
        assertThat(Files.readAllLines(dir.resolve("artifacts.tsv"))).containsExactly(
                "1.0\tnamed\torg.example.rt\trt-java.sql");
        // No modules.tsv: /module/java.sql would only ever hand back a JAR the JVM refuses to use.
        assertThat(dir.resolve("modules.tsv")).doesNotExist();
    }

    @Test
    public void regenerate_deletes_stale_modules_tsv_for_jdk_bundled_module() throws IOException {
        ModuleStore store = new ModuleStore(root);
        // jdk.jsobject is bundled with the JVM but also republished under platform classifiers; both
        // the main and the classifier-scoped resolved views must be removed.
        store.record("jdk.jsobject", ModuleType.NAMED, "26.0.1", ts("org.openjfx", "jdk-jsobject", "26.0.1", null, 1L));
        store.record("jdk.jsobject", ModuleType.NAMED, "26.0.1", ts("org.openjfx", "jdk-jsobject", "26.0.1", "linux", 1L));
        store.flush();
        Path dir = root.resolve("jdk").resolve("jsobject");
        // Seed stale resolved views as if produced by an older crawl before the platform rule existed.
        Files.writeString(dir.resolve("modules.tsv"), "26.0.1\torg.openjfx\tjdk-jsobject\t26.0.1\n");
        Files.writeString(dir.resolve("modules-linux.tsv"), "26.0.1\torg.openjfx\tjdk-jsobject\t26.0.1\n");

        store.regenerate("jdk.jsobject");

        assertThat(dir.resolve("modules.tsv")).doesNotExist();
        assertThat(dir.resolve("modules-linux.tsv")).doesNotExist();
    }

    @Test
    public void isPlatformModule_matches_jdk_modules_and_ignores_lookalikes() {
        // From the fixed ModuleStore.PLATFORM_MODULES set (the java --list-modules platform modules).
        assertThat(ModuleStore.isPlatformModule("java.base")).isTrue();
        assertThat(ModuleStore.isPlatformModule("java.xml")).isTrue();
        assertThat(ModuleStore.isPlatformModule("jdk.unsupported")).isTrue();
        assertThat(ModuleStore.isPlatformModule("jdk.net")).isTrue();
        // Jakarta EE names that share the java. prefix but are NOT bundled with a modern JVM, and
        // legacy EE modules removed by JEP 320 - all legitimately published on Maven Central.
        assertThat(ModuleStore.isPlatformModule("java.persistence")).isFalse();
        assertThat(ModuleStore.isPlatformModule("java.servlet")).isFalse();
        assertThat(ModuleStore.isPlatformModule("java.xml.bind")).isFalse();
        assertThat(ModuleStore.isPlatformModule("java.transaction")).isFalse();
        // JavaFX ships separately as OpenJFX, not as a JDK module.
        assertThat(ModuleStore.isPlatformModule("javafx.base")).isFalse();
    }

    private static Coordinate ts(String groupId, String artifactId, String version, String classifier, long publishedAt) {
        return new Coordinate(groupId, artifactId, version, classifier, "jar", 0L, publishedAt);
    }
}
