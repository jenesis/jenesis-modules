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

        // No unresolved drift, but the handled module is counted in the resolved column.
        assertThat(Files.readString(data.resolve("DRIFTERS.md")))
                .contains("| **total** | **0** | **1** |")
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

    @Test
    public void closest_groupid_by_prefix_is_shaded_even_when_not_strictly_under() throws IOException {
        ModuleStore store = new ModuleStore(data.resolve("modules"));
        // module com.fasterxml.jackson.kotlin is owned by groupId com.fasterxml.jackson.module:
        // they share com.fasterxml.jackson (3 segments) but the name is not under the groupId.
        store.record("com.fasterxml.jackson.kotlin", ModuleType.NAMED, null, coord("com.fasterxml.jackson.module", "jackson-module-kotlin", "2.15", 1_500_000_000_000L));
        store.record("com.fasterxml.jackson.kotlin", ModuleType.NAMED, null, coord("com.shaded.app", "fat", "1.0", 1_600_000_000_000L));
        store.record("com.fasterxml.jackson.kotlin", ModuleType.NAMED, null, coord("com.fasterxml.jackson.module", "jackson-module-kotlin", "2.18", 1_770_000_000_000L));
        store.flush();

        run(Map.of(DriftReport.PROP_DATA, data.toString(), DriftReport.PROP_TODAY, "2026-06-12"));

        String report = Files.readString(data.resolve("DRIFTERS.md"));
        assertThat(report).contains("## shaded (1)");
        assertThat(report).contains("owned by `com.fasterxml.jackson.module`");
    }

    @Test
    public void groupid_minus_tld_prefix_is_classified_tld_dropped() throws IOException {
        ModuleStore store = new ModuleStore(data.resolve("modules"));
        // module ktorm.core owned by org.ktorm: drop the org top-level domain -> ktorm.
        store.record("ktorm.core", ModuleType.NAMED, null, coord("org.ktorm", "ktorm-core", "3.0", 1_500_000_000_000L));
        store.record("ktorm.core", ModuleType.NAMED, null, coord("com.shaded.app", "fat", "1.0", 1_600_000_000_000L));
        store.record("ktorm.core", ModuleType.NAMED, null, coord("org.ktorm", "ktorm-core", "4.0", 1_770_000_000_000L));
        store.flush();

        run(Map.of(DriftReport.PROP_DATA, data.toString(), DriftReport.PROP_TODAY, "2026-06-12"));

        String report = Files.readString(data.resolve("DRIFTERS.md"));
        assertThat(report).contains("## tld-dropped (1)");
        assertThat(report).contains("ktorm.core");
        assertThat(report).contains("owned by `org.ktorm`");
    }

    @Test
    public void groupid_minus_two_segments_prefix_is_classified_two_segments() throws IOException {
        ModuleStore store = new ModuleStore(data.resolve("modules"));
        // module kotlinx.coroutines.core owned by org.jetbrains.kotlinx: drop org.jetbrains -> kotlinx.
        store.record("kotlinx.coroutines.core", ModuleType.NAMED, null, coord("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "1.7", 1_500_000_000_000L));
        store.record("kotlinx.coroutines.core", ModuleType.NAMED, null, coord("com.shaded.app", "fat", "1.0", 1_600_000_000_000L));
        store.record("kotlinx.coroutines.core", ModuleType.NAMED, null, coord("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "1.9", 1_770_000_000_000L));
        store.flush();

        run(Map.of(DriftReport.PROP_DATA, data.toString(), DriftReport.PROP_TODAY, "2026-06-12"));

        String report = Files.readString(data.resolve("DRIFTERS.md"));
        assertThat(report).contains("## two-segments (1)");
        assertThat(report).contains("owned by `org.jetbrains.kotlinx`");
    }

    @Test
    public void org_level_owner_is_shaded_when_strictly_closest() throws IOException {
        ModuleStore store = new ModuleStore(data.resolve("modules"));
        // org.eclipse.platform shares only org.eclipse (2 segments) with org.eclipse.help, but it is
        // the strictly-closest dominant publisher; a foreign jar shades the name.
        store.record("org.eclipse.help", ModuleType.NAMED, null, coord("org.eclipse.platform", "org.eclipse.help", "3.0", 1_500_000_000_000L));
        store.record("org.eclipse.help", ModuleType.NAMED, null, coord("com.innoventsolutions.birt", "fat", "1.0", 1_600_000_000_000L));
        store.record("org.eclipse.help", ModuleType.NAMED, null, coord("org.eclipse.platform", "org.eclipse.help", "3.1", 1_770_000_000_000L));
        store.flush();

        run(Map.of(DriftReport.PROP_DATA, data.toString(), DriftReport.PROP_TODAY, "2026-06-12"));

        String report = Files.readString(data.resolve("DRIFTERS.md"));
        assertThat(report).contains("## shaded (1)");
        assertThat(report).contains("owned by `org.eclipse.platform`");
    }

    @Test
    public void hyphen_in_groupid_is_ignored_when_matching_the_module_name() throws IOException {
        ModuleStore store = new ModuleStore(data.resolve("modules"));
        // groupId com.graphql-java owns module com.graphqljava: the '-' (illegal in module names) is
        // stripped, so the names match and it is recognised as the canonical owner.
        store.record("com.graphqljava", ModuleType.NAMED, null, coord("com.graphql-java", "graphql-java", "21.0", 1_500_000_000_000L));
        store.record("com.graphqljava", ModuleType.NAMED, null, coord("com.shaded.app", "fat", "1.0", 1_600_000_000_000L));
        store.record("com.graphqljava", ModuleType.NAMED, null, coord("com.graphql-java", "graphql-java", "22.0", 1_770_000_000_000L));
        store.flush();

        run(Map.of(DriftReport.PROP_DATA, data.toString(), DriftReport.PROP_TODAY, "2026-06-12"));

        String report = Files.readString(data.resolve("DRIFTERS.md"));
        assertThat(report).contains("## shaded (1)");
        assertThat(report).contains("owned by `com.graphql-java`");
    }

    @Test
    public void explicit_rule_owner_prefix_allows_subgroups() throws IOException {
        ModuleStore store = new ModuleStore(data.resolve("modules"));
        // reactor.* -> io.projectreactor (prefix): both io.projectreactor and io.projectreactor.netty
        // are allowed, the foreign shader is blocked.
        store.record("reactor.netty.core", ModuleType.NAMED, null, coord("io.projectreactor.netty", "reactor-netty-core", "1.1", 1_500_000_000_000L));
        store.record("reactor.netty.core", ModuleType.NAMED, null, coord("com.shaded.app", "fat", "1.0", 1_600_000_000_000L));
        store.flush();

        run(Map.of(DriftReport.PROP_DATA, data.toString(), DriftReport.PROP_TODAY, "2026-06-12"));

        String report = Files.readString(data.resolve("DRIFTERS.md"));
        assertThat(report).contains("## explicit-rules (1)");
        assertThat(report).contains("owned by `io.projectreactor`");
    }

    @Test
    public void explicit_rule_overrides_the_heuristic() throws IOException {
        ModuleStore store = new ModuleStore(data.resolve("modules"));
        // spring.boot.* is owned by org.springframework.boot by explicit rule, even though a shaded
        // jar published it first (which would otherwise make org.springframework.boot a non-owner).
        store.record("spring.boot.autoconfigure", ModuleType.NAMED, null, coord("com.shaded.app", "fat", "1.0", 1_400_000_000_000L));
        store.record("spring.boot.autoconfigure", ModuleType.NAMED, null, coord("org.springframework.boot", "spring-boot-autoconfigure", "3.2", 1_700_000_000_000L));
        store.flush();

        run(Map.of(DriftReport.PROP_DATA, data.toString(), DriftReport.PROP_TODAY, "2026-06-12"));

        String report = Files.readString(data.resolve("DRIFTERS.md"));
        assertThat(report).contains("## explicit-rules (1)");
        assertThat(report).contains("spring.boot.autoconfigure");
        assertThat(report).contains("owned by `org.springframework.boot`");
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
