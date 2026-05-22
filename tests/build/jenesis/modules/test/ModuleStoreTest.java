package build.jenesis.modules.test;

import module java.base;
import module org.junit.jupiter.api;

import build.jenesis.modules.Coordinate;
import build.jenesis.modules.ModuleEntry;
import build.jenesis.modules.ModuleStore;
import build.jenesis.modules.ModuleType;
import build.jenesis.modules.Version;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ModuleStoreTest {

    @TempDir
    Path root;

    @Test
    public void writes_entries_newest_first_under_first_letter_shard() throws IOException {
        ModuleStore store = new ModuleStore(root);
        store.record("com.example.lib", ModuleType.NAMED, coordinate("com.example", "lib", "1.0", null));
        store.record("com.example.lib", ModuleType.NAMED, coordinate("com.example", "lib", "2.0", null));
        store.record("com.example.lib", ModuleType.AUTOMATIC, coordinate("com.example", "lib", "0.9", null));

        store.flush();

        Path file = root.resolve("c").resolve("com.example.lib");
        assertThat(file).exists();
        List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
        assertThat(lines).containsExactly(
                "2.0\tnamed\tcom.example:lib",
                "1.0\tnamed\tcom.example:lib",
                "0.9\tautomatic\tcom.example:lib");
    }

    @Test
    public void appends_to_existing_file_without_dropping_entries() throws IOException {
        ModuleStore first = new ModuleStore(root);
        first.record("alpha.module", ModuleType.NAMED, coordinate("a", "alpha", "1.0", null));
        first.flush();

        ModuleStore second = new ModuleStore(root);
        second.record("alpha.module", ModuleType.NAMED, coordinate("a", "alpha", "2.0", null));
        second.flush();

        List<String> lines = Files.readAllLines(root.resolve("a").resolve("alpha.module"), StandardCharsets.UTF_8);
        assertThat(lines).containsExactly(
                "2.0\tnamed\ta:alpha",
                "1.0\tnamed\ta:alpha");
    }

    @Test
    public void classifier_is_appended_to_file_name() throws IOException {
        ModuleStore store = new ModuleStore(root);
        store.record("widget.core", ModuleType.NAMED, coordinate("org.widget", "core", "1.0", "jakarta"));
        store.flush();

        Path file = root.resolve("w").resolve("widget.core-jakarta");
        assertThat(file).exists();
        assertThat(Files.readAllLines(file)).containsExactly("1.0\tnamed\torg.widget:core");
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

        List<String> lines = Files.readAllLines(root.resolve("s").resolve("shared.name"), StandardCharsets.UTF_8);

        assertThat(lines).containsExactly(
                "1.0\tnamed\ta.example:lib",
                "1.0\tnamed\tz.example:lib");
    }

    @Test
    public void rejects_module_name_with_dash() {
        assertThatThrownBy(() -> new ModuleStore.StoreKey("not-allowed", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("dash");
    }

    private static Coordinate coordinate(String groupId, String artifactId, String version, String classifier) {
        return new Coordinate(groupId, artifactId, version, classifier, "jar", 0L, 0L);
    }
}
