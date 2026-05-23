package build.jenesis.crawler.test;

import module java.base;
import module org.junit.jupiter.api;

import build.jenesis.crawler.Coordinate;
import build.jenesis.crawler.ScannedEntry;
import build.jenesis.crawler.ScannedStore;

import static org.assertj.core.api.Assertions.assertThat;

public class ScannedStoreTest {

    @TempDir
    Path root;

    @Test
    public void marks_and_persists_ok_entries_under_group_path() throws IOException {
        ScannedStore store = new ScannedStore(root);
        store.markOk(coordinate("com.example", "alpha", "1.0", null));
        store.markOk(coordinate("com.example", "alpha", "1.1", null));
        store.markOk(coordinate("com.example", "beta", "2.0", "jakarta"));

        store.flush();

        Path file = root.resolve("com").resolve("example").resolve("scanned.tsv");
        assertThat(file).exists();
        List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
        // Empty 4th column = ok.
        assertThat(lines).containsExactly(
                "alpha\t1.0\t\t",
                "alpha\t1.1\t\t",
                "beta\t2.0\tjakarta\t");
    }

    @Test
    public void markFailed_writes_error_message_in_fourth_column() throws IOException {
        ScannedStore store = new ScannedStore(root);
        store.markFailed(coordinate("com.example", "broken", "1.0", null),
                "IllegalArgumentException: Expected central file header signature at offset 0");
        store.flush();

        Path file = root.resolve("com").resolve("example").resolve("scanned.tsv");
        assertThat(Files.readAllLines(file)).containsExactly(
                "broken\t1.0\t\tIllegalArgumentException: Expected central file header signature at offset 0");
    }

    @Test
    public void error_message_is_sanitised_for_tab_and_newline() throws IOException {
        ScannedStore store = new ScannedStore(root);
        store.markFailed(coordinate("g", "a", "1.0", null), "first line\nsecond\ttab");
        store.flush();

        assertThat(Files.readAllLines(root.resolve("g").resolve("scanned.tsv")))
                .containsExactly("a\t1.0\t\tfirst line second tab");
    }

    @Test
    public void contains_returns_true_for_ok_entries() throws IOException {
        ScannedStore writer = new ScannedStore(root);
        writer.markOk(coordinate("org.widget", "core", "5.2", null));
        writer.flush();

        ScannedStore reader = new ScannedStore(root);
        assertThat(reader.contains(coordinate("org.widget", "core", "5.2", null))).isTrue();
        assertThat(reader.contains(coordinate("org.widget", "core", "5.3", null))).isFalse();
        assertThat(reader.contains(coordinate("org.widget", "other", "5.2", null))).isFalse();
    }

    @Test
    public void contains_returns_true_for_failed_entries_by_default() throws IOException {
        ScannedStore writer = new ScannedStore(root);
        writer.markFailed(coordinate("g", "broken", "1.0", null), "bad zip");
        writer.flush();

        ScannedStore reader = new ScannedStore(root);
        assertThat(reader.contains(coordinate("g", "broken", "1.0", null))).isTrue();
    }

    @Test
    public void reprocessFailed_treats_failed_entries_as_unseen() throws IOException {
        ScannedStore writer = new ScannedStore(root);
        writer.markOk(coordinate("g", "good", "1.0", null));
        writer.markFailed(coordinate("g", "broken", "1.0", null), "bad zip");
        writer.flush();

        ScannedStore reader = new ScannedStore(root, true);
        assertThat(reader.contains(coordinate("g", "good", "1.0", null))).isTrue();
        assertThat(reader.contains(coordinate("g", "broken", "1.0", null))).isFalse();
    }

    @Test
    public void markOk_after_markFailed_clears_the_failure() throws IOException {
        ScannedStore store = new ScannedStore(root);
        store.markFailed(coordinate("g", "a", "1.0", null), "transient ride");
        store.markOk(coordinate("g", "a", "1.0", null));
        store.flush();

        List<String> lines = Files.readAllLines(root.resolve("g").resolve("scanned.tsv"));
        assertThat(lines).containsExactly("a\t1.0\t\t");
        // and the reprocess view also sees it as scanned now
        assertThat(new ScannedStore(root, true).contains(coordinate("g", "a", "1.0", null))).isTrue();
    }

    @Test
    public void distinguishes_by_classifier() throws IOException {
        ScannedStore store = new ScannedStore(root);
        store.markOk(coordinate("g", "a", "1.0", null));
        store.markOk(coordinate("g", "a", "1.0", "jakarta"));

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
        store.markOk(coordinate("a", "b", "1.0", null));
        store.markOk(coordinate("a", "b", "1.0", null));
        store.flush();

        assertThat(Files.readAllLines(root.resolve("a").resolve("scanned.tsv")))
                .containsExactly("b\t1.0\t\t");
    }

    @Test
    public void parses_and_formats_entries_symmetrically() {
        ScannedEntry plain = ScannedEntry.ok("artifact", "1.0", null);
        ScannedEntry classified = ScannedEntry.ok("artifact", "1.0", "jakarta");
        ScannedEntry failed = ScannedEntry.failed("artifact", "1.0", null, "bad zip");

        assertThat(ScannedEntry.parse(plain.format())).isEqualTo(plain);
        assertThat(ScannedEntry.parse(classified.format())).isEqualTo(classified);
        assertThat(ScannedEntry.parse(failed.format())).isEqualTo(failed);
    }

    private static Coordinate coordinate(String groupId, String artifactId, String version, String classifier) {
        return new Coordinate(groupId, artifactId, version, classifier, "jar", 0L, 0L);
    }
}
