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
        // most recent), coexisting with the natural-namespace owner com.example - not a handoff.
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
        // Name every publisher: com.example allowed, org.other blocked -> handled, not drift.
        Path dir = data.resolve("modules").resolve("com").resolve("example").resolve("lib");
        Files.writeString(dir.resolve("owners.tsv"), "com.example\tallowed\norg.other\tblocked\n");

        run(Map.of(DriftReport.PROP_DATA, data.toString(), DriftReport.PROP_TODAY, "2026-06-12"));

        assertThat(Files.readString(data.resolve("DRIFTERS.md")))
                .contains("| **unresolved total** | **0** |")
                .doesNotContain("com.example.lib");
    }

    @Test
    public void natural_owner_that_is_dominant_is_classified_shaded() throws IOException {
        ModuleStore store = new ModuleStore(data.resolve("modules"));
        // ch.qos.logback owns ch.qos.logback.classic and is both the earliest and most-recent
        // publisher; a fat jar shaded it once in between.
        store.record("ch.qos.logback.classic", ModuleType.NAMED, null, coord("ch.qos.logback", "logback-classic", "1.0.0", 1_500_000_000_000L));
        store.record("ch.qos.logback.classic", ModuleType.NAMED, null, coord("com.bundler", "fat", "9.0", 1_600_000_000_000L));
        store.record("ch.qos.logback.classic", ModuleType.NAMED, null, coord("ch.qos.logback", "logback-classic", "1.5.34", 1_770_000_000_000L));
        store.flush();

        run(Map.of(DriftReport.PROP_DATA, data.toString(), DriftReport.PROP_TODAY, "2026-06-12"));

        String report = Files.readString(data.resolve("DRIFTERS.md"));
        assertThat(report).contains("## shaded (1)");
        assertThat(report).contains("ch.qos.logback.classic");
        assertThat(report).contains("owned by `ch.qos.logback`");
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
