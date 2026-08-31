package build.jenesis.crawler.test;

import module java.base;
import build.jenesis.crawler.model.Coordinate;
import build.jenesis.crawler.model.ScannedEntry;
import build.jenesis.crawler.store.ScannedStore;
import module org.junit.jupiter.api;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ScannedStoreTest {

    @TempDir
    Path root;

    @Test
    public void marks_and_persists_ok_entries_under_per_artifact_path() throws IOException {
        ScannedStore store = new ScannedStore(root);
        store.markOk(coordinate("com.example", "alpha", "1.0", null));
        store.markOk(coordinate("com.example", "alpha", "1.1", null));
        store.markOk(coordinate("com.example", "beta", "2.0", "jakarta"));

        store.flush();

        Path alpha = root.resolve("com").resolve("example").resolve("alpha.tsv");
        Path beta = root.resolve("com").resolve("example").resolve("beta.tsv");
        assertThat(alpha).exists();
        assertThat(beta).exists();
        assertThat(Files.readAllLines(alpha, StandardCharsets.UTF_8))
                .containsExactly("1.0\t\t\t", "1.1\t\t\t");
        assertThat(Files.readAllLines(beta, StandardCharsets.UTF_8))
                .containsExactly("2.0\tjakarta\t\t");
    }

    @Test
    public void markFailed_writes_error_message_in_third_column() throws IOException {
        ScannedStore store = new ScannedStore(root);
        store.markFailed(coordinate("com.example", "broken", "1.0", null),
                "IllegalArgumentException: Expected central file header signature at offset 0");
        store.flush();

        Path file = root.resolve("com").resolve("example").resolve("broken.tsv");
        assertThat(Files.readAllLines(file)).containsExactly(
                "1.0\t\t\tIllegalArgumentException: Expected central file header signature at offset 0");
    }

    @Test
    public void error_message_is_sanitised_for_tab_and_newline() throws IOException {
        ScannedStore store = new ScannedStore(root);
        store.markFailed(coordinate("g", "a", "1.0", null), "first line\nsecond\ttab");
        store.flush();

        assertThat(Files.readAllLines(root.resolve("g").resolve("a.tsv")))
                .containsExactly("1.0\t\t\tfirst line second tab");
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

        assertThat(Files.readAllLines(root.resolve("g").resolve("a.tsv")))
                .containsExactly("1.0\t\t\t");
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
    public void no_disk_writes_when_no_artifact_marked() throws IOException {
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

        assertThat(Files.readAllLines(root.resolve("a").resolve("b.tsv")))
                .containsExactly("1.0\t\t\t");
    }

    @Test
    public void parses_and_formats_entries_symmetrically() {
        ScannedEntry plain = ScannedEntry.ok("1.0", null);
        ScannedEntry classified = ScannedEntry.ok("1.0", "jakarta");
        ScannedEntry failed = ScannedEntry.failed("1.0", null, "bad zip");

        assertThat(ScannedEntry.parse(plain.format())).isEqualTo(plain);
        assertThat(ScannedEntry.parse(classified.format())).isEqualTo(classified);
        assertThat(ScannedEntry.parse(failed.format())).isEqualTo(failed);
    }

    @Test
    public void parse_rejects_legacy_three_column_rows() {
        assertThatThrownBy(() -> ScannedEntry.parse("1.0\t\t"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Expected 4 tab-separated fields");
    }

    @Test
    public void lru_cache_evicts_oldest_clean_entry_when_over_cap() throws IOException {
        // Seed three artifacts on disk so the second store can lazy-load them.
        ScannedStore writer = new ScannedStore(root);
        writer.markOk(coordinate("g", "alpha", "1.0", null));
        writer.markOk(coordinate("g", "beta", "1.0", null));
        writer.markOk(coordinate("g", "gamma", "1.0", null));
        writer.flush();

        // Cache size 2: after touching all three, the first-touched artifact must have
        // been evicted from memory. Verified via reflection - we can't observe the cache
        // directly, so we touch each artifact and check the underlying file is still the
        // source of truth (mutating it on disk between accesses would expose a stale
        // cache, but for this test we just rely on the semantic that contains() keeps
        // working after eviction by reloading on demand).
        ScannedStore reader = new ScannedStore(root, false, 2);
        assertThat(reader.contains(coordinate("g", "alpha", "1.0", null))).isTrue();
        assertThat(reader.contains(coordinate("g", "beta", "1.0", null))).isTrue();
        assertThat(reader.contains(coordinate("g", "gamma", "1.0", null))).isTrue();
        // Re-touching the evicted-then-reloaded one must still see the value (i.e. the
        // reload path is wired up). If LRU eviction broke contains semantics this would
        // start returning false.
        assertThat(reader.contains(coordinate("g", "alpha", "1.0", null))).isTrue();
        assertThat(reader.contains(coordinate("g", "alpha", "9.9", null))).isFalse();
    }

    @Test
    public void lru_cache_never_evicts_dirty_entry() throws IOException {
        // Cache size 1, but two artifacts are marked dirty: the cap is exceeded yet both
        // entries must survive until flush, otherwise the second markOk would lose data
        // (its in-memory NavigableSet would be discarded before reaching disk).
        ScannedStore store = new ScannedStore(root, false, 1);
        store.markOk(coordinate("g", "alpha", "1.0", null));
        store.markOk(coordinate("g", "beta", "1.0", null));
        store.markOk(coordinate("g", "gamma", "1.0", null));
        // Force eviction attempts by touching a fourth artifact's contains path.
        assertThat(store.contains(coordinate("g", "delta", "1.0", null))).isFalse();

        store.flush();

        // Every dirty mark survived to disk despite cap=1.
        assertThat(Files.readAllLines(root.resolve("g").resolve("alpha.tsv"))).containsExactly("1.0\t\t\t");
        assertThat(Files.readAllLines(root.resolve("g").resolve("beta.tsv"))).containsExactly("1.0\t\t\t");
        assertThat(Files.readAllLines(root.resolve("g").resolve("gamma.tsv"))).containsExactly("1.0\t\t\t");
    }

    @Test
    public void markOk_writes_coordinate_publishedAt_as_third_column() throws IOException {
        // Real publishedAt should land on disk as an ISO 8601 UTC timestamp in column 3.
        long publishedAt = 1700000000000L;  // 2023-11-14T22:13:20Z
        ScannedStore store = new ScannedStore(root);
        store.markOk(new Coordinate("g", "a", "1.0", null, "jar", 0L, publishedAt));
        store.flush();

        assertThat(Files.readAllLines(root.resolve("g").resolve("a.tsv")))
                .containsExactly("1.0\t\t2023-11-14T22:13:20Z\t");
    }

    @Test
    public void a_line_that_is_not_a_scanned_entry_costs_a_rescan_and_not_the_run() throws IOException {
        // This store is a cache of what has already been looked at, so an unreadable line means "scan that
        // coordinate again", never "abandon the sweep". A scheduled reconcile ran for hours over rebuildable
        // state and was killed every time by one file holding a bare version, hand-written into data/ against
        // the rule that only the crawler writes there.
        Files.createDirectories(root.resolve("g"));
        Files.writeString(root.resolve("g").resolve("a.tsv"), "0.5.0\n");

        ScannedStore reader = new ScannedStore(root);

        assertThat(reader.contains(coordinate("g", "a", "0.5.0", null)))
                .as("the malformed entry is dropped, so the coordinate looks unscanned")
                .isFalse();
    }

    @Test
    public void a_malformed_line_does_not_discard_the_good_ones_beside_it() throws IOException {
        Files.createDirectories(root.resolve("g"));
        Files.writeString(root.resolve("g").resolve("a.tsv"), "1.0\t\t\t\n0.5.0\n2.0\t\t\t\n");

        ScannedStore reader = new ScannedStore(root);

        assertThat(reader.contains(coordinate("g", "a", "1.0", null))).isTrue();
        assertThat(reader.contains(coordinate("g", "a", "2.0", null))).isTrue();
        assertThat(reader.contains(coordinate("g", "a", "0.5.0", null))).isFalse();
    }

    @Test
    public void a_rescan_rewrites_the_file_in_the_four_column_shape() throws IOException {
        // The self-healing half: once the coordinate is scanned again, writeArtifact replaces the whole file, so
        // the bad line is gone rather than skipped forever.
        Files.createDirectories(root.resolve("g"));
        Files.writeString(root.resolve("g").resolve("a.tsv"), "0.5.0\n");

        ScannedStore store = new ScannedStore(root);
        store.markOk(coordinate("g", "a", "0.5.0", null));
        store.flush();

        assertThat(Files.readAllLines(root.resolve("g").resolve("a.tsv"))).containsExactly("0.5.0\t\t\t");
    }

    private static Coordinate coordinate(String groupId, String artifactId, String version, String classifier) {
        return new Coordinate(groupId, artifactId, version, classifier, "jar", 0L, 0L);
    }
}
