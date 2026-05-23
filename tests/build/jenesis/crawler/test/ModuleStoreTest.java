package build.jenesis.crawler.test;

import module java.base;
import module org.junit.jupiter.api;

import build.jenesis.crawler.Coordinate;
import build.jenesis.crawler.ModuleEntry;
import build.jenesis.crawler.ModuleStore;
import build.jenesis.crawler.ModuleType;
import build.jenesis.crawler.Version;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ModuleStoreTest {

    @TempDir
    Path root;

    @Test
    public void writes_entries_newest_first_under_dotted_directory_path() throws IOException {
        ModuleStore store = new ModuleStore(root);
        store.record("com.example.lib", ModuleType.NAMED, coordinate("com.example", "lib", "1.0", null));
        store.record("com.example.lib", ModuleType.NAMED, coordinate("com.example", "lib", "2.0", null));
        store.record("com.example.lib", ModuleType.AUTOMATIC, coordinate("com.example", "lib", "0.9", null));

        store.flush();

        Path file = root.resolve("com").resolve("example").resolve("lib").resolve("versions.tsv");
        assertThat(file).exists();
        List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
        assertThat(lines).containsExactly(
                "2.0\tnamed\tcom.example\tlib\t1970-01-01T00:00:00Z",
                "1.0\tnamed\tcom.example\tlib\t1970-01-01T00:00:00Z",
                "0.9\tautomatic\tcom.example\tlib\t1970-01-01T00:00:00Z");
    }

    @Test
    public void prefix_module_and_child_module_coexist_without_path_conflict() throws IOException {
        ModuleStore store = new ModuleStore(root);
        store.record("jakarta.servlet", ModuleType.NAMED, coordinate("jakarta.servlet", "jakarta.servlet-api", "5.0", null));
        store.record("jakarta.servlet.api", ModuleType.NAMED, coordinate("jakarta.servlet", "jakarta.servlet-api", "6.0", null));
        store.flush();

        Path prefix = root.resolve("jakarta").resolve("servlet").resolve("versions.tsv");
        Path child = root.resolve("jakarta").resolve("servlet").resolve("api").resolve("versions.tsv");

        assertThat(prefix).exists();
        assertThat(child).exists();
        assertThat(Files.readAllLines(prefix)).containsExactly("5.0\tnamed\tjakarta.servlet\tjakarta.servlet-api\t1970-01-01T00:00:00Z");
        assertThat(Files.readAllLines(child)).containsExactly("6.0\tnamed\tjakarta.servlet\tjakarta.servlet-api\t1970-01-01T00:00:00Z");
    }

    @Test
    public void appends_to_existing_file_without_dropping_entries() throws IOException {
        ModuleStore first = new ModuleStore(root);
        first.record("alpha.module", ModuleType.NAMED, coordinate("a", "alpha", "1.0", null));
        first.flush();

        ModuleStore second = new ModuleStore(root);
        second.record("alpha.module", ModuleType.NAMED, coordinate("a", "alpha", "2.0", null));
        second.flush();

        List<String> lines = Files.readAllLines(root.resolve("alpha").resolve("module").resolve("versions.tsv"));
        assertThat(lines).containsExactly(
                "2.0\tnamed\ta\talpha\t1970-01-01T00:00:00Z",
                "1.0\tnamed\ta\talpha\t1970-01-01T00:00:00Z");
    }

    @Test
    public void classifier_is_appended_to_leaf_file_name() throws IOException {
        ModuleStore store = new ModuleStore(root);
        store.record("widget.core", ModuleType.NAMED, coordinate("org.widget", "core", "1.0", "jakarta"));
        store.flush();

        Path file = root.resolve("widget").resolve("core").resolve("versions-jakarta.tsv");
        assertThat(file).exists();
        assertThat(Files.readAllLines(file)).containsExactly("1.0\tnamed\torg.widget\tcore\t1970-01-01T00:00:00Z");
    }

    @Test
    public void parses_back_what_it_writes() throws IOException {
        ModuleStore writer = new ModuleStore(root);
        writer.record("round.trip", ModuleType.NAMED, coordinate("g", "a", "1.0", null));
        writer.record("round.trip", ModuleType.AUTOMATIC, coordinate("g2", "a2", "0.5", null));
        writer.flush();

        NavigableSet<ModuleEntry> entries = new ModuleStore(root).read("round.trip", null);

        assertThat(entries).extracting(ModuleEntry::version).extracting(Version::raw)
                .containsExactly("1.0", "0.5");
        assertThat(entries.first().type()).isEqualTo(ModuleType.NAMED);
        assertThat(entries.last().type()).isEqualTo(ModuleType.AUTOMATIC);
    }

    @Test
    public void breaks_ties_by_group_artifact_ascending() throws IOException {
        ModuleStore store = new ModuleStore(root);
        store.record("shared.name", ModuleType.NAMED, coordinate("z.example", "lib", "1.0", null));
        store.record("shared.name", ModuleType.NAMED, coordinate("a.example", "lib", "1.0", null));
        store.flush();

        List<String> lines = Files.readAllLines(root.resolve("shared").resolve("name").resolve("versions.tsv"), StandardCharsets.UTF_8);

        assertThat(lines).containsExactly(
                "1.0\tnamed\ta.example\tlib\t1970-01-01T00:00:00Z",
                "1.0\tnamed\tz.example\tlib\t1970-01-01T00:00:00Z");
    }

    @Test
    public void records_publication_timestamp_from_coordinate() throws IOException {
        ModuleStore store = new ModuleStore(root);
        store.record("com.example.timed", ModuleType.NAMED,
                new Coordinate("com.example", "timed", "1.0", null, "jar", 0L, 1692704520000L));
        store.flush();

        Path file = root.resolve("com").resolve("example").resolve("timed").resolve("versions.tsv");
        assertThat(Files.readAllLines(file)).containsExactly("1.0\tnamed\tcom.example\ttimed\t2023-08-22T11:42:00Z");
    }

    @Test
    public void records_when_no_owners_file_exists() throws IOException {
        ModuleStore store = new ModuleStore(root);
        boolean recorded = store.record("open.module", ModuleType.NAMED, coordinate("any.group", "any-artifact", "1.0", null));

        assertThat(recorded).isTrue();
        store.flush();
        assertThat(root.resolve("open").resolve("module").resolve("versions.tsv")).exists();
    }

    @Test
    public void owners_with_group_only_line_allows_any_artifact_in_group() throws IOException {
        Path dir = Files.createDirectories(root.resolve("guarded").resolve("module"));
        Files.writeString(dir.resolve("owners.tsv"), "trusted.group\n");

        ModuleStore store = new ModuleStore(root);
        boolean allowed = store.record("guarded.module", ModuleType.NAMED, coordinate("trusted.group", "anything", "1.0", null));
        boolean rejected = store.record("guarded.module", ModuleType.NAMED, coordinate("other.group", "anything", "1.0", null));

        assertThat(allowed).isTrue();
        assertThat(rejected).isFalse();
        store.flush();
        assertThat(Files.readAllLines(dir.resolve("versions.tsv")))
                .containsExactly("1.0\tnamed\ttrusted.group\tanything\t1970-01-01T00:00:00Z");
    }

    @Test
    public void owners_with_exact_pair_line_rejects_other_artifacts_in_same_group() throws IOException {
        Path dir = Files.createDirectories(root.resolve("guarded").resolve("module"));
        Files.writeString(dir.resolve("owners.tsv"), "trusted.group\tknown-artifact\n");

        ModuleStore store = new ModuleStore(root);
        boolean allowed = store.record("guarded.module", ModuleType.NAMED, coordinate("trusted.group", "known-artifact", "1.0", null));
        boolean rejected = store.record("guarded.module", ModuleType.NAMED, coordinate("trusted.group", "other-artifact", "1.0", null));

        assertThat(allowed).isTrue();
        assertThat(rejected).isFalse();
        store.flush();
        assertThat(Files.readAllLines(dir.resolve("versions.tsv")))
                .containsExactly("1.0\tnamed\ttrusted.group\tknown-artifact\t1970-01-01T00:00:00Z");
    }

    @Test
    public void owners_ignores_comments_and_blank_lines() throws IOException {
        Path dir = Files.createDirectories(root.resolve("guarded").resolve("module"));
        Files.writeString(dir.resolve("owners.tsv"), """
                # canonical maintainers
                trusted.group

                # vendored repackagings explicitly allowed:
                vendor.group\tvendor-artifact
                """);

        ModuleStore store = new ModuleStore(root);

        assertThat(store.record("guarded.module", ModuleType.NAMED, coordinate("trusted.group", "x", "1.0", null))).isTrue();
        assertThat(store.record("guarded.module", ModuleType.NAMED, coordinate("vendor.group", "vendor-artifact", "1.0", null))).isTrue();
        assertThat(store.record("guarded.module", ModuleType.NAMED, coordinate("vendor.group", "other", "1.0", null))).isFalse();
        assertThat(store.record("guarded.module", ModuleType.NAMED, coordinate("hostile.group", "x", "1.0", null))).isFalse();
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

    private static Coordinate coordinate(String groupId, String artifactId, String version, String classifier) {
        return new Coordinate(groupId, artifactId, version, classifier, "jar", 0L, 0L);
    }
}
