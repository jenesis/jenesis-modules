package build.jenesis.modules.test;

import module java.base;
import module org.junit.jupiter.api;

import build.jenesis.modules.Coordinate;
import build.jenesis.modules.ScannedEntry;
import build.jenesis.modules.ScannedStore;

import static org.assertj.core.api.Assertions.assertThat;

public class ScannedStoreTest {

    @TempDir
    Path root;

    @Test
    public void marks_and_persists_entries_under_group_path() throws IOException {
        ScannedStore store = new ScannedStore(root);
        store.mark(coordinate("com.example", "alpha", "1.0", null));
        store.mark(coordinate("com.example", "alpha", "1.1", null));
        store.mark(coordinate("com.example", "beta", "2.0", "jakarta"));

        store.flush();

        Path file = root.resolve("com").resolve("example").resolve("scanned.tsv");
        assertThat(file).exists();
        List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
        assertThat(lines).containsExactly(
                "alpha\t1.0\t",
                "alpha\t1.1\t",
                "beta\t2.0\tjakarta");
    }

    @Test
    public void contains_recognises_previously_marked_entries() throws IOException {
        ScannedStore writer = new ScannedStore(root);
        writer.mark(coordinate("org.widget", "core", "5.2", null));
        writer.flush();

        ScannedStore reader = new ScannedStore(root);
        assertThat(reader.contains(coordinate("org.widget", "core", "5.2", null))).isTrue();
        assertThat(reader.contains(coordinate("org.widget", "core", "5.3", null))).isFalse();
        assertThat(reader.contains(coordinate("org.widget", "other", "5.2", null))).isFalse();
    }

    @Test
    public void distinguishes_by_classifier() throws IOException {
        ScannedStore store = new ScannedStore(root);
        store.mark(coordinate("g", "a", "1.0", null));
        store.mark(coordinate("g", "a", "1.0", "jakarta"));

        assertThat(store.contains(coordinate("g", "a", "1.0", null))).isTrue();
        assertThat(store.contains(coordinate("g", "a", "1.0", "jakarta"))).isTrue();
        assertThat(store.contains(coordinate("g", "a", "1.0", "android"))).isFalse();
    }

    @Test
    public void no_disk_writes_when_no_groups_marked() throws IOException {
        ScannedStore store = new ScannedStore(root);
        store.flush();
        assertThat(root.toFile().list()).isEmpty();
    }

    @Test
    public void marking_same_entry_twice_does_not_duplicate() throws IOException {
        ScannedStore store = new ScannedStore(root);
        store.mark(coordinate("a", "b", "1.0", null));
        store.mark(coordinate("a", "b", "1.0", null));
        store.flush();

        List<String> lines = Files.readAllLines(root.resolve("a").resolve("scanned.tsv"), StandardCharsets.UTF_8);
        assertThat(lines).containsExactly("b\t1.0\t");
    }

    @Test
    public void parses_and_formats_entries_symmetrically() {
        ScannedEntry plain = new ScannedEntry("artifact", "1.0", null);
        ScannedEntry classified = new ScannedEntry("artifact", "1.0", "jakarta");

        assertThat(ScannedEntry.parse(plain.format())).isEqualTo(plain);
        assertThat(ScannedEntry.parse(classified.format())).isEqualTo(classified);
    }

    private static Coordinate coordinate(String groupId, String artifactId, String version, String classifier) {
        return new Coordinate(groupId, artifactId, version, classifier, "jar", 0L, 0L);
    }
}
