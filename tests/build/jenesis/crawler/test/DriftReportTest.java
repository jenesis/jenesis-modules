package build.jenesis.crawler.test;

import module java.base;
import build.jenesis.crawler.DriftReport;
import build.jenesis.crawler.model.Coordinate;
import build.jenesis.crawler.model.ModuleType;
import build.jenesis.crawler.store.ModuleStore;
import module org.junit.jupiter.api;


import static org.assertj.core.api.Assertions.assertThat;

public class DriftReportTest {

    @TempDir
    Path data;

    @Test
    public void reports_a_two_owner_module_without_owners_and_emits_a_setowners_file() throws IOException {
        ModuleStore store = new ModuleStore(data.resolve("modules"));
        // A republisher case: a foreign group claims the name and keeps publishing it (first and
        // most recent), coexisting with the natural-namespace owner - not a handoff.
        store.record("com.example.lib", ModuleType.NAMED, null, coord("org.other", "fat", "9.0", 1_600_000_000_000L));
        store.record("com.example.lib", ModuleType.NAMED, null, coord("com.example", "lib", "1.0", 1_700_000_000_000L));
        store.record("com.example.lib", ModuleType.NAMED, null, coord("org.other", "fat", "9.1", 1_770_000_000_000L));
        store.flush();

        Path emit = data.resolve("emit.properties");
        run(Map.of(
                DriftReport.PROP_DATA, data.toString(),
                DriftReport.PROP_TODAY, "2026-06-12",
                DriftReport.PROP_EMIT, "republisher",
                DriftReport.PROP_EMIT_FILE, emit.toString()));

        String report = Files.readString(data.resolve("DRIFTERS.md"));
        assertThat(report).contains("# Module ownership drifters");
        assertThat(report).contains("com.example.lib");
        assertThat(report).contains("## republisher (1)");

        // The emit file proposes the natural owner for SetOwners to apply.
        assertThat(Files.readAllLines(emit)).contains("com.example.lib=com.example");
    }

    @Test
    public void a_module_whose_owners_tsv_names_every_publisher_is_not_drift() throws IOException {
        ModuleStore store = new ModuleStore(data.resolve("modules"));
        store.record("com.example.lib", ModuleType.NAMED, null, coord("com.example", "lib", "1.0", 1_700_000_000_000L));
        store.record("com.example.lib", ModuleType.NAMED, null, coord("org.other", "fat", "9.0", 1_710_000_000_000L));
        store.flush();
        // Name every publisher: com.example allowed, org.other rejected -> handled, not drift.
        Path dir = data.resolve("modules").resolve("com").resolve("example").resolve("lib");
        Files.writeString(dir.resolve("owners.tsv"), "com.example\tallowed\norg.other\trejected\n");

        run(Map.of(DriftReport.PROP_DATA, data.toString(), DriftReport.PROP_TODAY, "2026-06-12"));

        // No unresolved drift, but the handled module is counted in the resolved column.
        assertThat(Files.readString(data.resolve("DRIFTERS.md")))
                .contains("| **total** | **0** | **1** |")
                .doesNotContain("## republisher (1)");
    }

    @Test
    public void natural_owner_that_is_dominant_is_classified_shaded() throws IOException {
        ModuleStore store = new ModuleStore(data.resolve("modules"));
        // The natural owner (the module name is under its groupId) is both the earliest and the
        // most-recent publisher; a fat jar shaded it once in between.
        store.record("com.example.logging.classic", ModuleType.NAMED, null, coord("com.example.logging", "logging-classic", "1.0.0", 1_500_000_000_000L));
        store.record("com.example.logging.classic", ModuleType.NAMED, null, coord("org.bundler", "fat", "9.0", 1_600_000_000_000L));
        store.record("com.example.logging.classic", ModuleType.NAMED, null, coord("com.example.logging", "logging-classic", "1.5.0", 1_770_000_000_000L));
        store.flush();

        run(Map.of(DriftReport.PROP_DATA, data.toString(), DriftReport.PROP_TODAY, "2026-06-12"));

        String report = Files.readString(data.resolve("DRIFTERS.md"));
        assertThat(report).contains("## shaded (1)");
        assertThat(report).contains("com.example.logging.classic");
        assertThat(report).contains("owned by `com.example.logging`");
    }

    @Test
    public void closest_groupid_by_prefix_is_shaded_even_when_not_strictly_under() throws IOException {
        ModuleStore store = new ModuleStore(data.resolve("modules"));
        // The owner groupId shares the first three segments with the module name but is not a strict
        // prefix of it (the leaf segment differs); it is still the closest, dominant publisher.
        store.record("com.example.tool.binding", ModuleType.NAMED, null, coord("com.example.tool.core", "tool-core", "2.15", 1_500_000_000_000L));
        store.record("com.example.tool.binding", ModuleType.NAMED, null, coord("org.shaded.app", "fat", "1.0", 1_600_000_000_000L));
        store.record("com.example.tool.binding", ModuleType.NAMED, null, coord("com.example.tool.core", "tool-core", "2.18", 1_770_000_000_000L));
        store.flush();

        run(Map.of(DriftReport.PROP_DATA, data.toString(), DriftReport.PROP_TODAY, "2026-06-12"));

        String report = Files.readString(data.resolve("DRIFTERS.md"));
        assertThat(report).contains("## shaded (1)");
        assertThat(report).contains("owned by `com.example.tool.core`");
    }

    @Test
    public void org_level_owner_is_shaded_when_strictly_closest() throws IOException {
        ModuleStore store = new ModuleStore(data.resolve("modules"));
        // The owner shares only the two-segment org prefix with the module name, but it is the
        // strictly-closest dominant publisher; a foreign jar shades the name.
        store.record("org.example.help", ModuleType.NAMED, null, coord("org.example.platform", "platform", "3.0", 1_500_000_000_000L));
        store.record("org.example.help", ModuleType.NAMED, null, coord("com.bundler", "fat", "1.0", 1_600_000_000_000L));
        store.record("org.example.help", ModuleType.NAMED, null, coord("org.example.platform", "platform", "3.1", 1_770_000_000_000L));
        store.flush();

        run(Map.of(DriftReport.PROP_DATA, data.toString(), DriftReport.PROP_TODAY, "2026-06-12"));

        String report = Files.readString(data.resolve("DRIFTERS.md"));
        assertThat(report).contains("## shaded (1)");
        assertThat(report).contains("owned by `org.example.platform`");
    }

    @Test
    public void hyphen_in_groupid_is_ignored_when_matching_the_module_name() throws IOException {
        ModuleStore store = new ModuleStore(data.resolve("modules"));
        // A hyphenated groupId owns the un-hyphenated module name: the '-' (illegal in module names)
        // is stripped, so the names match and it is recognised as the canonical owner.
        store.record("com.example.foobar", ModuleType.NAMED, null, coord("com.example.foo-bar", "foo-bar", "21.0", 1_500_000_000_000L));
        store.record("com.example.foobar", ModuleType.NAMED, null, coord("org.shaded.app", "fat", "1.0", 1_600_000_000_000L));
        store.record("com.example.foobar", ModuleType.NAMED, null, coord("com.example.foo-bar", "foo-bar", "22.0", 1_770_000_000_000L));
        store.flush();

        run(Map.of(DriftReport.PROP_DATA, data.toString(), DriftReport.PROP_TODAY, "2026-06-12"));

        String report = Files.readString(data.resolve("DRIFTERS.md"));
        assertThat(report).contains("## shaded (1)");
        assertThat(report).contains("owned by `com.example.foo-bar`");
    }

    @Test
    public void groupid_minus_tld_prefix_is_classified_tld_dropped() throws IOException {
        ModuleStore store = new ModuleStore(data.resolve("modules"));
        // module widget.core owned by org.widget: drop the org top-level domain -> widget.
        store.record("widget.core", ModuleType.NAMED, null, coord("org.widget", "widget-core", "3.0", 1_500_000_000_000L));
        store.record("widget.core", ModuleType.NAMED, null, coord("org.shaded.app", "fat", "1.0", 1_600_000_000_000L));
        store.record("widget.core", ModuleType.NAMED, null, coord("org.widget", "widget-core", "4.0", 1_770_000_000_000L));
        store.flush();

        run(Map.of(DriftReport.PROP_DATA, data.toString(), DriftReport.PROP_TODAY, "2026-06-12"));

        String report = Files.readString(data.resolve("DRIFTERS.md"));
        assertThat(report).contains("## tld-dropped (1)");
        assertThat(report).contains("widget.core");
        assertThat(report).contains("owned by `org.widget`");
    }

    @Test
    public void groupid_minus_two_segments_prefix_is_classified_two_segments() throws IOException {
        ModuleStore store = new ModuleStore(data.resolve("modules"));
        // module widget.ui.core owned by org.example.widget: drop the first two segments -> widget.
        store.record("widget.ui.core", ModuleType.NAMED, null, coord("org.example.widget", "widget-ui-core", "1.7", 1_500_000_000_000L));
        store.record("widget.ui.core", ModuleType.NAMED, null, coord("org.shaded.app", "fat", "1.0", 1_600_000_000_000L));
        store.record("widget.ui.core", ModuleType.NAMED, null, coord("org.example.widget", "widget-ui-core", "1.9", 1_770_000_000_000L));
        store.flush();

        run(Map.of(DriftReport.PROP_DATA, data.toString(), DriftReport.PROP_TODAY, "2026-06-12"));

        String report = Files.readString(data.resolve("DRIFTERS.md"));
        assertThat(report).contains("## two-segments (1)");
        assertThat(report).contains("owned by `org.example.widget`");
    }

    @Test
    public void reassigned_ownership_is_listed_at_the_end() throws IOException {
        ModuleStore store = new ModuleStore(data.resolve("modules"));
        // The first publisher (org.other) is a foreign jar; owners.tsv reassigns the module to the
        // natural owner com.example, so it appears in the reassigned section.
        store.record("com.example.lib", ModuleType.NAMED, null, coord("org.other", "fat", "9.0", 1_600_000_000_000L));
        store.record("com.example.lib", ModuleType.NAMED, null, coord("com.example", "lib", "1.0", 1_700_000_000_000L));
        store.flush();
        Path dir = data.resolve("modules").resolve("com").resolve("example").resolve("lib");
        Files.writeString(dir.resolve("owners.tsv"), "com.example\tallowed\norg.other\trejected\n");

        run(Map.of(DriftReport.PROP_DATA, data.toString(), DriftReport.PROP_TODAY, "2026-06-12"));

        String report = Files.readString(data.resolve("DRIFTERS.md"));
        assertThat(report).contains("## Reassigned and widened ownership");
        assertThat(report).contains("| `com.example.lib` | 1 | `org.other` | `com.example` | `org.other` |");
    }

    @Test
    public void a_single_late_publication_is_a_fork_not_a_migration() throws IOException {
        ModuleStore store = new ModuleStore(data.resolve("modules"));
        // The owner stays active; a foreign jar publishes the name exactly once, most recently. That
        // one-off is not a credible successor, so it is kept by the owner (fork), not co-owned.
        store.record("com.example.lib.core", ModuleType.NAMED, null, coord("com.example.lib", "lib-core", "1.0", 1_500_000_000_000L));
        store.record("com.example.lib.core", ModuleType.NAMED, null, coord("com.example.lib", "lib-core", "2.0", 1_700_000_000_000L));
        store.record("com.example.lib.core", ModuleType.NAMED, null, coord("org.oneoff", "fat", "9.0", 1_770_000_000_000L));
        store.flush();

        run(Map.of(DriftReport.PROP_DATA, data.toString(), DriftReport.PROP_TODAY, "2026-06-12"));

        String report = Files.readString(data.resolve("DRIFTERS.md"));
        assertThat(report).contains("## fork (1)");
        assertThat(report).contains("keep `com.example.lib`");
    }

    private static void run(Map<String, String> properties) throws IOException {
        properties.forEach(System::setProperty);
        try {
            DriftReport.main(new String[0]);
        } finally {
            properties.keySet().forEach(System::clearProperty);
        }
    }

    private static Coordinate coord(String groupId, String artifactId, String version, long publishedAt) {
        return new Coordinate(groupId, artifactId, version, null, "jar", 0L, publishedAt);
    }
}
