package build.jenesis.crawler;

import module java.base;
import build.jenesis.crawler.model.ArtifactsEntry;
import build.jenesis.crawler.model.ModuleEntry;
import build.jenesis.crawler.model.ModuleType;
import build.jenesis.crawler.model.ScannedEntry;
import build.jenesis.crawler.model.Version;

/**
 * Reads {@code data/modules/} (versions.tsv + artifacts.tsv) and writes a markdown
 * summary describing the catalogue: counts of named vs automatic modules,
 * type-transition history, recent-publication activity, naming patterns,
 * collisions, and top-N tables. The whole file is regenerated atomically on
 * every invocation; previous content is overwritten.
 *
 * Inputs are configurable via system properties:
 *   jenesis.crawler.data        Crawler data directory (default: "data")
 *   jenesis.summary.output      Output file path (default: "<data>/SUMMARY.md")
 */
public final class ModuleSummary {

    public static final String PROP_DATA = "jenesis.crawler.data";
    public static final String PROP_OUTPUT = "jenesis.summary.output";
    public static final String PROP_TOP_N = "jenesis.summary.top.n";
    public static final String DEFAULT_OUTPUT = "SUMMARY.md";
    public static final Duration RECENT_WINDOW = Duration.ofDays(7L);
    public static final int DEFAULT_TOP_N = 25;

    private static final String VERSIONS_STEM = "versions";
    private static final String ARTIFACTS_STEM = "artifacts";
    private static final String MODULES_STEM = "modules";
    private static final String TSV_EXTENSION = ".tsv";
    private static final DateTimeFormatter HUMAN_UTC_TIMESTAMP = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss 'UTC'")
            .withZone(ZoneOffset.UTC);

    /**
     * Folds applied to the "Top N modules by version count" table so that families of closely
     * related modules don't crowd out everyone else by occupying many adjacent slots with the
     * same value. Each fold defines a display key (e.g. {@code software.amazon.awssdk.*}) and a
     * predicate over module names. Every match collapses into a single row whose count is the
     * highest in the family and (when they differ) is rendered as {@code [min, max]}.
     */
    static final List<ModuleFold> MODULE_FOLDS = List.of(
            new ModuleFold("software.amazon.awssdk.*",
                    name -> name.startsWith("software.amazon.awssdk.")),
            new ModuleFold("org.scala.lang.scala3.*",
                    name -> name.startsWith("org.scala.lang.scala3.")),
            new ModuleFold("org.scala.lang.* (excl. scala3)",
                    name -> name.startsWith("org.scala.lang.") && !name.startsWith("org.scala.lang.scala3.")),
            new ModuleFold("com.fasterxml.jackson.*",
                    name -> name.startsWith("com.fasterxml.jackson.")),
            new ModuleFold("com.google.api.services.*",
                    name -> name.startsWith("com.google.api.services.")),
            new ModuleFold("com.guicedee.*",
                    name -> name.startsWith("com.guicedee.")),
            new ModuleFold("jakarta.enterprise.*",
                    name -> name.startsWith("jakarta.enterprise.")),
            new ModuleFold("undertow.*",
                    name -> name.startsWith("undertow.")));

    public record ModuleFold(String displayKey, Predicate<String> matches) {
    }

    private ModuleSummary() {
    }

    public static void main(String[] arguments) throws IOException {
        Path dataDir = Path.of(System.getProperty(PROP_DATA, "data"));
        Path output = Path.of(System.getProperty(PROP_OUTPUT, dataDir.resolve(DEFAULT_OUTPUT).toString()));
        int topN = parseTopN(System.getProperty(PROP_TOP_N));
        Instant generatedAt = Instant.now();
        Stats stats = compute(dataDir, generatedAt, topN);
        atomicWrite(output, render(stats, topN));
        System.out.println("[summary] Wrote " + output
                + " (modules=" + stats.totals().modules()
                + ", versionRows=" + stats.totals().versionRows()
                + ", topN=" + topN + ")");
    }

    private static int parseTopN(String value) {
        if (value == null || value.isBlank()) {
            return DEFAULT_TOP_N;
        }
        int parsed;
        try {
            parsed = Integer.parseInt(value.trim());
        } catch (NumberFormatException invalid) {
            throw new IllegalArgumentException("Expected integer for " + PROP_TOP_N + ", got: " + value);
        }
        if (parsed < 1) {
            throw new IllegalArgumentException(PROP_TOP_N + " must be >= 1, got: " + parsed);
        }
        return parsed;
    }

    public record Stats(Instant generatedAt,
                        State state,
                        Totals totals,
                        TypeBreakdown named,
                        TypeBreakdown automatic,
                        ModuleVersionCoverage moduleVersionCoverage,
                        LatestModuleVersionCoverage latestModuleVersionCoverage,
                        MismatchImpact mismatchImpact,
                        MismatchPatterns mismatchPatterns,
                        Transitions transitions,
                        RecentActivity recent,
                        List<MonthlyPublication> monthlyPublications,
                        NamingPatterns naming,
                        ProcessingErrors errors,
                        TopLists top,
                        List<String> topYears) {
    }

    /**
     * Row-level breakdown of how the module-info version column in {@code versions.tsv}
     * relates to the Maven coordinate version for the same row. Counted over named-module
     * rows only; automatic modules have no module-info to declare a version, so they are
     * excluded so they don't dilute the absent bucket.
     *
     * <ul>
     *   <li>{@code explicit}: module-info declared a version and it equals the Maven coordinate version.</li>
     *   <li>{@code mismatching}: module-info declared a non-empty version that differs from the Maven coordinate version.</li>
     *   <li>{@code absent}: the JAR was scanned but module-info declared no version.</li>
     * </ul>
     */
    public record ModuleVersionCoverage(long explicit, long mismatching, long absent) {
    }

    /**
     * Same breakdown as {@link ModuleVersionCoverage} but counted once per canonical module,
     * against the highest-versioned named row in its no-classifier resolved view (the row a
     * consumer fetching the "latest" of a module would land on). Classifier variants and
     * modules with no canonical named row contribute to none of the buckets. Where
     * {@link ModuleVersionCoverage} answers "how do publishers fill the field across all
     * canonical releases", this answers "where is each canonical module today".
     */
    public record LatestModuleVersionCoverage(long explicit, long mismatching, long absent) {
    }

    /**
     * Per canonical module (no-classifier view), what happens if every row with a mismatching
     * {@code module-info} version is dropped. Useful for sizing the impact of a "reject
     * Maven-vs-module-info version mismatches" policy. Classifier-keyed rows are out of scope
     * (they would otherwise inflate the picture with fat-jar / shaded variants whose bundled
     * {@code module-info} version is expected to contradict the bundling Maven version).
     *
     * <ul>
     *   <li>{@code total}: canonical module names that have at least one named row.
     *       Equals {@code clean + partial + fullyLost}.</li>
     *   <li>{@code clean}: at least one named row exists, and none are mismatching. Untouched.</li>
     *   <li>{@code partial}: at least one mismatching row exists, but at least one
     *       non-mismatching row survives. The module loses some versions but keeps a path forward.</li>
     *   <li>{@code fullyLost}: every named row is mismatching. The module has no surviving row
     *       after the drop and disappears from the module-version lookup space entirely.</li>
     *   <li>{@code losingLatest}: the canonical latest named row is mismatching. After the drop,
     *       the module's "latest" shifts to an older version (or vanishes entirely, if also in
     *       {@code fullyLost}).</li>
     * </ul>
     */
    public record MismatchImpact(int total, int clean, int partial, int fullyLost, int losingLatest) {
    }

    /**
     * Pattern breakdown of the rows in {@link ModuleVersionCoverage#mismatching}. Each row in
     * the mismatching bucket falls into exactly one of these classes, so the fields sum to the
     * mismatching total. Categories closer to the top of the list are formatting drift (the
     * publisher's release process didn't fully strip a SNAPSHOT marker, a repackager appended a
     * coordinate suffix, etc.); {@code substantive} is the catch-all for everything that looks
     * like a genuinely different version. Counted only over rows where both versions are
     * non-empty - {@code explicit} and {@code absent} don't reach here.
     *
     * <ul>
     *   <li>{@code snapshotSuffix}: module-info version is exactly {@code <maven>-SNAPSHOT}.
     *       Typical of releases that forgot to drop the SNAPSHOT qualifier when promoting.</li>
     *   <li>{@code otherSuffixAdded}: module-info version is {@code <maven>-<suffix>} for some
     *       non-SNAPSHOT suffix (build labels, patch tags, etc.).</li>
     *   <li>{@code suffixDropped}: Maven version is {@code <module>-<suffix>}. Typical of
     *       repackagers that append an {@code -r<N>} suffix to the Maven coordinate while the
     *       module-info keeps the upstream version unchanged.</li>
     *   <li>{@code segmentAdded}: module-info version is {@code <maven>.<segment>} - the module
     *       carries an extra dot-segment past where the Maven version ends.</li>
     *   <li>{@code segmentDropped}: Maven version is {@code <module>.<segment>} - the Maven
     *       coordinate carries an extra dot-segment past the module version. Both segment
     *       categories slip past {@link build.jenesis.crawler.model.Version#equals(Object)}
     *       only when the trailing segment is non-numeric or non-trivial.</li>
     *   <li>{@code substantive}: everything else - module-info declares a meaningfully
     *       different version than the Maven coordinate.</li>
     * </ul>
     */
    public record MismatchPatterns(long snapshotSuffix,
                                   long otherSuffixAdded,
                                   long suffixDropped,
                                   long segmentAdded,
                                   long segmentDropped,
                                   long plusMetadataAdded,
                                   long plusMetadataDropped,
                                   long unresolvedPlaceholder,
                                   long differentMajor,
                                   long substantive) {

        public long total() {
            return snapshotSuffix + otherSuffixAdded + suffixDropped + segmentAdded + segmentDropped
                    + plusMetadataAdded + plusMetadataDropped + unresolvedPlaceholder
                    + differentMajor + substantive;
        }
    }

    public record Totals(int modules,
                         long versionRows,
                         long namedVersionRows,
                         long automaticVersionRows,
                         long namedVersionRowsWithModuleVersion,
                         int distinctModulesWithModuleVersion,
                         long resolvedModuleVersions,
                         long scannedArtifacts,
                         long nonModuleArtifacts,
                         long distinctMavenArtifacts,
                         int distinctGroupIds,
                         Optional<Instant> latestPublishedAt) {
    }

    public record TypeBreakdown(int distinctModules, long rows) {
    }

    public record Transitions(int autoToNamed, int namedToAuto) {
    }

    public record RecentActivity(int modules,
                                 int namedModules,
                                 int automaticModules,
                                 long versions,
                                 long namedVersions,
                                 long automaticVersions,
                                 long nonModularArtifacts) {
    }

    /**
     * One row of the per-month publication breakdown, counting DISTINCT entities (not publication
     * rows) so the three columns stay within one order of magnitude and share a bar scale.
     * {@code month} is the calendar month in UTC. {@code named} and {@code automatic} count
     * distinct canonical (owner-resolved) module <em>names</em> that published a version of that
     * type in the month. {@code nonModular} counts distinct {@code (groupId, artifactId)} that
     * published a non-modular coordinate, derived as (distinct scanned artifacts in the month)
     * minus (distinct modular artifacts in the month) - the artifact-level analogue of the
     * Totals "Non-module artifacts" subtraction. The renderer keeps the most recent 12 calendar
     * months (including the current one) so growth trends are visible at a glance.
     */
    public record MonthlyPublication(YearMonth month, long named, long automatic, long nonModular) {
    }

    public record NamingPatterns(int collidingModules,
                                 SortedMap<Integer, Integer> sharedSegmentHistogram,
                                 int modulesWithClassifier,
                                 int classifierVariants) {

        public NamingPatterns {
            sharedSegmentHistogram = Collections.unmodifiableSortedMap(new TreeMap<>(sharedSegmentHistogram));
        }
    }

    /**
     * The normalized error message for the dominant "incorrectly indexed" failure class:
     * coordinates the upstream Nexus index mis-stamps as having a main JAR when only a POM was
     * ever published (BOMs, parent POMs). The crawler probes once, the fetch 404s, and the row is
     * recorded permanently. These are an upstream-data artifact, not a problem with the artifact
     * itself, so the summary breaks them out from genuine artifact errors.
     */
    public static final String INCORRECTLY_INDEXED_ERROR = "IOException: Tail request on <URL> returned status 404";

    public record ProcessingErrors(long total, long incorrectlyIndexed, List<TopEntry> topMessages) {

        /** Failures that reflect a real problem with the artifact (total minus mis-stamped 404s). */
        public long genuineErrors() {
            return Math.max(0L, total - incorrectlyIndexed);
        }
    }

    public record TopLists(List<TopEntry> modulesByVersionCount,
                           List<TopEntry> groupsByModuleCount,
                           List<TopEntry> collisionsByDistinctGroups,
                           List<TopLatestEntry> latestModuleUpdates,
                           List<TopAvgEntry> groupsByAverageVersions) {
    }

    /**
     * A row in a top-N table. {@code count} is the value used for ranking and is what gets
     * rendered for a single-module row. When a row represents a {@link ModuleFold} (e.g.
     * {@code software.amazon.awssdk.*}), {@code min} carries the lowest count among the folded
     * modules and {@code count} carries the highest; the renderer emits {@code [min, count]}
     * to convey the spread, plus a trailing {@code (N modules)} hint built from {@code members}.
     * For non-folded rows {@code min == count} and {@code members == 0} (the sentinel meaning
     * "this is a single module, not a fold"); only {@code count} is rendered.
     */
    public record TopEntry(String key, long count, long min, int members) {
        public TopEntry(String key, long count) {
            this(key, count, count, 0);
        }

        public boolean isRange() {
            return min != count;
        }

        public boolean isFold() {
            return members > 0;
        }
    }

    public record TopLatestEntry(String key, Instant publishedAt) {
    }

    public record TopAvgEntry(String key, int modules, long totalVersions, double average) {
    }

    public static Stats compute(Path dataDir, Instant generatedAt, int topN) throws IOException {
        Path statePath = dataDir.resolve("state.properties");
        Path modulesRoot = dataDir.resolve("modules");
        Path scannedRoot = dataDir.resolve("scanned");
        State state = State.load(statePath);
        // The recent-activity window is anchored to the freshest tracked publication, not the
        // wall clock: Maven Central's index lags real time by up to a week, so "last 7 days from
        // now" is frequently empty even when we're fully caught up. A cheap pre-pass finds the
        // max publishedAt before the main walk (the recent counters need the cutoff up front).
        long latestPublishedMillis = findLatestPublishedMillis(modulesRoot);
        // Anchor the 12-month axis to the crawler's index timestamp so the summary depends only on
        // the crawler state it was built from, not on when it was rendered. Fall back to the passed
        // instant only when there is no recorded state (e.g. tests with no state.properties).
        Instant monthlyAnchor = state.indexTimestamp() > 0L
                ? Instant.ofEpochMilli(state.indexTimestamp())
                : generatedAt;
        List<String> topYears = findTopYears(dataDir.resolve("top"));
        Aggregator aggregator = new Aggregator(generatedAt, monthlyAnchor, topN, latestPublishedMillis);
        if (Files.isDirectory(modulesRoot)) {
            try (Stream<Path> stream = Files.walk(modulesRoot)) {
                Iterator<Path> iterator = stream.iterator();
                while (iterator.hasNext()) {
                    Path dir = iterator.next();
                    if (!Files.isDirectory(dir) || dir.equals(modulesRoot)) {
                        continue;
                    }
                    aggregator.acceptDirectory(modulesRoot, dir);
                }
            }
        }
        if (Files.isDirectory(scannedRoot)) {
            try (Stream<Path> stream = Files.walk(scannedRoot)) {
                Iterator<Path> iterator = stream.iterator();
                while (iterator.hasNext()) {
                    Path file = iterator.next();
                    if (!Files.isRegularFile(file) || !file.getFileName().toString().endsWith(TSV_EXTENSION)) {
                        continue;
                    }
                    aggregator.acceptScannedFile(file);
                }
            }
        }
        return aggregator.toStats(state, topYears);
    }

    /**
     * Year stems of the {@code <data>/top/<year>.md} reports, sorted ascending. Empty when the
     * {@code top} directory is absent or has no four-digit {@code .md} files.
     */
    private static List<String> findTopYears(Path topDir) throws IOException {
        if (!Files.isDirectory(topDir)) {
            return List.of();
        }
        List<String> years = new ArrayList<>();
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(topDir, "*.md")) {
            for (Path entry : entries) {
                String name = entry.getFileName().toString();
                String stem = name.substring(0, name.length() - ".md".length());
                if (stem.length() == 4 && stem.chars().allMatch(Character::isDigit)) {
                    years.add(stem);
                }
            }
        }
        Collections.sort(years);
        return List.copyOf(years);
    }

    /**
     * Finds the maximum {@code publishedAt} across every {@code versions[-classifier].tsv} in the
     * modules tree. Used to anchor the recent-activity window to the freshest tracked publication.
     * Each {@code versions.tsv} is written in chronological (publishedAt-ascending) order, so the
     * last non-empty line carries that file's maximum - one parse per file rather than per row.
     * Returns 0 when there is no data.
     */
    private static long findLatestPublishedMillis(Path modulesRoot) throws IOException {
        if (!Files.isDirectory(modulesRoot)) {
            return 0L;
        }
        long max = 0L;
        try (Stream<Path> stream = Files.walk(modulesRoot)) {
            for (Path file : (Iterable<Path>) stream::iterator) {
                if (!Files.isRegularFile(file)) {
                    continue;
                }
                String name = file.getFileName().toString();
                if (!name.startsWith(VERSIONS_STEM) || !name.endsWith(TSV_EXTENSION)) {
                    continue;
                }
                List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
                for (int i = lines.size() - 1; i >= 0; i--) {
                    String line = lines.get(i);
                    if (line.isEmpty()) {
                        continue;
                    }
                    max = Math.max(max, ModuleEntry.parse(line).publishedAt());
                    break;
                }
            }
        }
        return max;
    }

    private static String render(Stats stats, int topN) {
        StringBuilder builder = new StringBuilder();
        builder.append("# Module summary\n\n");
        builder.append("> ### Powered by [Jenesis](https://github.com/raphw/jenesis)\n");
        builder.append("> _A modern Java build tool: Java-native config, plugin-free, with `module-info.java` treated as a feature, not an afterthought._\n\n");
        State state = stats.state();
        if (state.indexTimestamp() > 0L) {
            builder.append("_Index timestamp: ").append(HUMAN_UTC_TIMESTAMP.format(Instant.ofEpochMilli(state.indexTimestamp()))).append("_  \n");
        }
        if (state.sweepStartedAt() != null) {
            builder.append("_Current chunk started: ").append(HUMAN_UTC_TIMESTAMP.format(state.sweepStartedAt())).append("_  \n");
        }
        if (state.indexChainId() != null) {
            builder.append("_Index chain id: `").append(state.indexChainId()).append("`_  \n");
        }
        if (state.indexChunkLastApplied() >= 0L) {
            builder.append("_Last applied index chunk: ").append(state.indexChunkLastApplied()).append("_  \n");
        }
        builder.append('\n');

        if (!stats.topYears().isEmpty()) {
            builder.append("## Top artifacts by year\n\n");
            builder.append("Real-world Java projects lean on a fairly small set of widely-shared libraries, while the ")
                    .append("catalogue as a whole carries a very long tail of artifacts that almost nothing depends on. ")
                    .append("Adoption measured across that whole tail understates what most projects actually encounter. ")
                    .append("The per-year reports below instead rank the most depended-on artifacts and show how many of ")
                    .append("them ship a module, which gives a clearer view of module adoption where it matters and how it ")
                    .append("has moved over time.\n\n");
            StringJoiner links = new StringJoiner(" · ");
            for (String year : stats.topYears()) {
                links.add("[" + year + "](top/" + year + ".md)");
            }
            builder.append(links).append("\n\n");
        }

        Totals totals = stats.totals();
        builder.append("## Totals\n\n");
        builder.append("Catalogue-wide counts. Unless a section is explicitly labelled as \"audit\" or \"history\", every row-level number here and below describes the canonical view of the catalogue: shaded or otherwise non-authoritative claims on a module name do not contribute. \"Artifacts\" counts JARs (one row per groupId/artifactId/version/classifier coordinate); \"modules\" counts the named or automatic-module identities those JARs expose. Distinct counts deduplicate by name. \"With module-info version\" means the module declared a non-empty version in its `module-info`, whether or not it matches the Maven coordinate version.\n\n");
        builder.append("| Metric | Value |\n|---|---:|\n");
        builder.append("| Total artifacts scanned | ").append(fmt(totals.scannedArtifacts())).append(" |\n");
        builder.append("| Non-module artifacts | ").append(fmt(totals.nonModuleArtifacts())).append(" |\n");
        builder.append("| Modular artifacts | ").append(fmt(totals.versionRows())).append(" |\n");
        builder.append("| Total automatic modules | ").append(fmt(totals.automaticVersionRows())).append(" |\n");
        builder.append("| Total named modules | ").append(fmt(totals.namedVersionRows())).append(" |\n");
        builder.append("| Total named modules with module-info version | ").append(fmt(totals.namedVersionRowsWithModuleVersion())).append(" |\n");
        builder.append("| Distinct Maven artifacts | ").append(fmt(totals.distinctMavenArtifacts())).append(" |\n");
        builder.append("| Distinct module names | ").append(fmt(totals.modules())).append(" |\n");
        builder.append("| Distinct automatic modules | ").append(fmt(stats.automatic().distinctModules())).append(" |\n");
        builder.append("| Distinct named modules | ").append(fmt(stats.named().distinctModules())).append(" |\n");
        builder.append("| Distinct named modules with module-info version | ").append(fmt(totals.distinctModulesWithModuleVersion())).append(" |\n");
        builder.append("| Distinct groupIds publishing modules | ").append(fmt(totals.distinctGroupIds())).append(" |\n");
        builder.append("| Most recent tracked publication | ")
                .append(totals.latestPublishedAt().map(HUMAN_UTC_TIMESTAMP::format).orElse("(none)"))
                .append(" |\n\n");

        builder.append("## Resolved catalogue size\n\n");
        builder.append("Across every `modules[-classifier].tsv` under `data/modules/`, the resolved view holds **")
                .append(fmt(totals.resolvedModuleVersions()))
                .append("** distinct module-version rows. Each row is one (module name, classifier, `module-info` version) combination that survived owner resolution; rows whose `module-info` version contradicts the Maven version are excluded by the resolution policy.\n\n");

        builder.append("## Type breakdown\n\n");
        builder.append("Named vs automatic counts. Distinct-module counts use the **latest** version's type, so a module that started automatic and is currently named counts as named. Row counts include every classifier variant.\n\n");
        builder.append("| Type | Distinct modules | Published rows |\n|---|---:|---:|\n");
        builder.append("| Named | ").append(fmt(stats.named().distinctModules())).append(" | ").append(fmt(stats.named().rows())).append(" |\n");
        builder.append("| Automatic | ").append(fmt(stats.automatic().distinctModules())).append(" | ").append(fmt(stats.automatic().rows())).append(" |\n\n");

        ModuleVersionCoverage coverage = stats.moduleVersionCoverage();
        LatestModuleVersionCoverage latestCoverage = stats.latestModuleVersionCoverage();
        builder.append("## `module-info` version field across named publications\n\n");
        builder.append("Every table in this section is scoped to **canonical (no-classifier) named publications**. Classifier-keyed rows (mostly fat-jar / shaded variants that bundle another module under their own Maven coordinate) are excluded, because the bundled module's `module-info` version is expected to contradict the bundling Maven version, which would otherwise overwhelm the signal here.\n\n");
        builder.append("Counts canonical **named publications** (one count per published JAR, not per distinct module) by how the JAR's `module-info` fills its optional version attribute. Automatic JARs are excluded; they carry no `module-info`. The three rows are mutually exclusive and cover every canonical named publication in the catalogue. The breakdown table below classifies the `mismatching` bucket by *why* the two versions differ.\n\n");
        builder.append("| Publication category | Publications |\n|---|---:|\n");
        builder.append("| `module-info` version matches the Maven coordinate version | ").append(fmt(coverage.explicit())).append(" |\n");
        builder.append("| `module-info` version is non-empty but differs from the Maven coordinate version | ").append(fmt(coverage.mismatching())).append(" |\n");
        builder.append("| `module-info` declared no version (Maven coordinate version is the only reference) | ").append(fmt(coverage.absent())).append(" |\n\n");
        builder.append("Same breakdown but counted once per **canonical module**, against the latest named row in its no-classifier resolved view (the row a consumer fetching the \"latest\" of a module would land on). Modules whose latest row is automatic are excluded.\n\n");
        builder.append("| Module category (by latest canonical named row) | Modules |\n|---|---:|\n");
        builder.append("| `module-info` version matches the Maven coordinate version | ").append(fmt(latestCoverage.explicit())).append(" |\n");
        builder.append("| `module-info` version is non-empty but differs from the Maven coordinate version | ").append(fmt(latestCoverage.mismatching())).append(" |\n");
        builder.append("| `module-info` declared no version (Maven coordinate version is the only reference) | ").append(fmt(latestCoverage.absent())).append(" |\n\n");
        MismatchImpact impact = stats.mismatchImpact();
        builder.append("Each row describes what the **version-mismatch filter** (drop every named row whose `module-info` version semantically contradicts its Maven coordinate version) leaves behind in the module's `modules.tsv`, counted once per **canonical module** (no-classifier view). Modules with no canonical named row are out of scope. The first row is the in-scope total; rows two through four are mutually exclusive and sum to it; the fifth row overlaps with rows three and four (it's the subset whose head-of-`modules.tsv` is the one the filter removes).\n\n");
        builder.append("| Module version filtering impact | Module names |\n|---|---:|\n");
        builder.append("| Canonical modules with at least one named row (in scope) | ").append(fmt(impact.total())).append(" |\n");
        builder.append("| Filter keeps every named row: `modules.tsv` is unchanged | ").append(fmt(impact.clean())).append(" |\n");
        builder.append("| Filter drops some named rows but at least one survives: `modules.tsv` shrinks | ").append(fmt(impact.partial())).append(" |\n");
        builder.append("| Filter drops every named row: `modules.tsv` is removed entirely | ").append(fmt(impact.fullyLost())).append(" |\n");
        builder.append("| Filter drops the module's current top row: \"latest\" shifts to an older Maven version (or vanishes if fully lost) | ").append(fmt(impact.losingLatest())).append(" |\n\n");

        renderMismatchPatterns(builder, stats.mismatchPatterns());

        builder.append("## Type transitions\n\n");
        builder.append("Modules that have switched between named and automatic over their history. A module counts toward a direction when its latest version's type differs from at least one earlier version's type.\n\n");
        builder.append("| Direction | Modules |\n|---|---:|\n");
        builder.append("| Automatic → Named | ").append(fmt(stats.transitions().autoToNamed())).append(" |\n");
        builder.append("| Named → Automatic | ").append(fmt(stats.transitions().namedToAuto())).append(" |\n\n");

        builder.append("## Recent activity (last 7 days)\n\n");
        builder.append("Activity in the 7-day window ending at the **most recent tracked publication** (shown in Totals), not at this file's generation time. Maven Central's index typically lags real time by up to a week, so a now-relative window is usually empty even when the crawl is fully caught up; anchoring to the freshest publication keeps the window meaningful. \"Modules with a publication\" counts distinct module names that received at least one new version; \"new version rows\" is the total count of those publications. Per-row counts split by the publication's own type; per-module counts attribute each module to whichever type it has at its latest version, so a module that switched named↔automatic shows up under its current type. The `Named`/`Automatic` columns are canonical (owner-resolved); the trailing `Non-modular artifacts` row counts distinct `(groupId, artifactId)` that published a coordinate with no module identity (distinct scanned artifacts minus distinct modular artifacts in the window), so it stands apart from the modular rows rather than summing into them.\n\n");
        builder.append("| Metric | Total | Named | Automatic |\n|---|---:|---:|---:|\n");
        builder.append("| Modules with a publication | ")
                .append(fmt(stats.recent().modules())).append(" | ")
                .append(fmt(stats.recent().namedModules())).append(" | ")
                .append(fmt(stats.recent().automaticModules())).append(" |\n");
        builder.append("| New version rows | ")
                .append(fmt(stats.recent().versions())).append(" | ")
                .append(fmt(stats.recent().namedVersions())).append(" | ")
                .append(fmt(stats.recent().automaticVersions())).append(" |\n");
        builder.append("| Non-modular artifacts | ")
                .append(fmt(stats.recent().nonModularArtifacts())).append(" | - | - |\n\n");

        renderMonthlyPublications(builder, stats.monthlyPublications());

        builder.append("## Naming patterns\n\n");
        builder.append("How module names relate to their publishing groupId and to classifier-bundled JARs. \"Classifier variants\" are non-main artifacts like `-jar-with-dependencies` or `-uber` that also produce a module; \"competing groupIds\" counts modules whose name has been published under more than one groupId across history (i.e. collisions).\n\n");
        builder.append("| Pattern | Modules |\n|---|---:|\n");
        builder.append("| Has classifier variants | ").append(fmt(stats.naming().modulesWithClassifier())).append(" |\n");
        builder.append("| Total classifier variants (across all modules) | ").append(fmt(stats.naming().classifierVariants())).append(" |\n");
        builder.append("| Multiple competing groupIds in audit history | ").append(fmt(stats.naming().collidingModules())).append(" |\n\n");

        builder.append("### Leading dot-segments shared with the owning groupId\n\n");
        builder.append("For each canonical (no-classifier) module that resolved to an owner (implicit or explicit), counts how many leading dot-segments its module name shares with the owner's groupId. A high share is the textbook JPMS pattern (e.g. module `com.example.foo` published by groupId `com.example.foo`); zero indicates a module name that diverges entirely from its publisher's groupId. Classifier variants are out of scope because they share the canonical's groupId by construction. Empty buckets render as `-`.\n\n");
        SortedMap<Integer, Integer> histogram = stats.naming().sharedSegmentHistogram();
        int maxShared = histogram.isEmpty() ? 0 : histogram.lastKey();
        builder.append("| Shared leading dot-segments | Canonical modules |\n|---:|---:|\n");
        for (int i = 0; i <= maxShared; i++) {
            int count = histogram.getOrDefault(i, 0);
            builder.append("| ").append(i).append(" | ").append(count == 0 ? "-" : fmt(count)).append(" |\n");
        }
        builder.append('\n');

        ProcessingErrors errors = stats.errors();
        builder.append("## Processing errors\n\n");
        builder.append("Recorded permanent failures across every scanned coordinate. Variable bits of well-known error classes (URLs, shaded package names, classfile entry indexes, HTTP status codes, line numbers, class identifiers) are replaced with placeholders like `<URL>`, `<PACKAGE>`, `<CLASS>` so messages that differ only in those bits aggregate into one row.\n\n");
        builder.append("`Incorrectly indexed` is the dominant failure class: coordinates the upstream Nexus index mis-stamps as having a main JAR when only a POM was ever published (BOMs, parent POMs). The crawler probes once, the fetch 404s, and the row is recorded permanently; these are an upstream-data artifact, not a problem with the JAR, and are excluded from the `Total artifacts scanned` and `Modular artifacts` totals above. `Genuine artifact errors` is the remainder - malformed JARs, unparseable `module-info`, and the like - and is broken out in the top-N table below.\n\n");
        builder.append("| Metric | Value |\n|---|---:|\n");
        builder.append("| Total failed coordinates | ").append(fmt(errors.total())).append(" |\n");
        builder.append("| Incorrectly indexed (mis-stamped 404s) | ").append(fmt(errors.incorrectlyIndexed())).append(" |\n");
        builder.append("| Genuine artifact errors | ").append(fmt(errors.genuineErrors())).append(" |\n\n");
        if (!errors.topMessages().isEmpty()) {
            builder.append("### Top ").append(topN).append(" genuine error messages\n\n");
            builder.append("Excludes the mis-stamped-404 class broken out above, so the genuine artifact errors are visible rather than buried beneath it.\n\n");
            builder.append("| Error message | Count |\n|---|---:|\n");
            for (TopEntry entry : errors.topMessages()) {
                builder.append("| `").append(escapePipes(entry.key())).append("` | ").append(fmt(entry.count())).append(" |\n");
            }
            builder.append('\n');
        }

        builder.append("## Top ").append(topN).append(" modules by version count\n\n");
        builder.append("Modules with the longest release history. Counts come from the main (no-classifier) view, so a classifier variant like `-jar-with-dependencies` does not inflate the number. Module families that would otherwise occupy many adjacent slots are folded into a single `<prefix>.*` row; the cell is rendered as `[min, max]` when the absorbed modules have different counts, and a trailing `(N modules)` notes how many were absorbed.\n\n");
        builder.append("| Module | Versions |\n|---|---:|\n");
        for (TopEntry entry : stats.top().modulesByVersionCount()) {
            String key = entry.isFold()
                    ? entry.key() + " (" + entry.members() + " modules)"
                    : entry.key();
            String count = entry.isRange()
                    ? "[" + fmt(entry.min()) + ", " + fmt(entry.count()) + "]"
                    : fmt(entry.count());
            builder.append("| `").append(key).append("` | ").append(count).append(" |\n");
        }
        builder.append('\n');

        builder.append("## Top ").append(topN).append(" groupIds by module count\n\n");
        builder.append("GroupIds that publish the most distinct module names, sorted by module count. Each `(groupId, moduleName)` pair counts once regardless of how many versions or classifier variants exist.\n\n");
        builder.append("| groupId | Modules published |\n|---|---:|\n");
        for (TopEntry entry : stats.top().groupsByModuleCount()) {
            builder.append("| `").append(entry.key()).append("` | ").append(fmt(entry.count())).append(" |\n");
        }
        builder.append('\n');

        builder.append("## Top ").append(topN).append(" modules with most colliding groupIds\n\n");
        builder.append("Module names that have been published under the most different groupIds across history. A high count indicates name reuse: forks, rebranded artifacts, or coordinate moves whose historical declarations remain on record even after the canonical publisher is set.\n\n");
        builder.append("| Module | Distinct groupIds |\n|---|---:|\n");
        for (TopEntry entry : stats.top().collisionsByDistinctGroups()) {
            builder.append("| `").append(entry.key()).append("` | ").append(fmt(entry.count())).append(" |\n");
        }
        builder.append('\n');

        builder.append("## Top ").append(topN).append(" modules updated in the last 7 days\n\n");
        builder.append("Modules whose most recent publication landed in the 7-day window ending at the most recent tracked publication (same window as `Recent activity`, anchored to the freshest publication rather than now since the index lags up to a week), sorted newest first. Use this as a recency view; the count above (`Recent activity`) gives the totals while this table names which modules they were.\n\n");
        if (stats.top().latestModuleUpdates().isEmpty()) {
            builder.append("_(none — no publications recorded within the last week)_\n\n");
        } else {
            builder.append("| Module | Last publication |\n|---|---|\n");
            for (TopLatestEntry entry : stats.top().latestModuleUpdates()) {
                builder.append("| `").append(entry.key()).append("` | ")
                        .append(HUMAN_UTC_TIMESTAMP.format(entry.publishedAt())).append(" |\n");
            }
            builder.append('\n');
        }

        builder.append("## Top ").append(topN).append(" groupIds by average versions per module\n\n");
        builder.append("Restricted to groupIds publishing at least 3 modules so the average isn't dominated by single-module outliers.\n\n");
        if (stats.top().groupsByAverageVersions().isEmpty()) {
            builder.append("_(none — no groupId yet publishes 3 or more modules)_\n\n");
        } else {
            builder.append("| groupId | Modules | Total versions | Avg versions / module |\n|---|---:|---:|---:|\n");
            for (TopAvgEntry entry : stats.top().groupsByAverageVersions()) {
                builder.append("| `").append(entry.key()).append("` | ")
                        .append(fmt(entry.modules())).append(" | ")
                        .append(fmt(entry.totalVersions())).append(" | ")
                        .append(String.format(Locale.ROOT, "%.1f", entry.average())).append(" |\n");
            }
            builder.append('\n');
        }

        builder.append("_This file is regenerated on every `ModuleSummary` run; previous content is discarded._\n");
        return builder.toString();
    }

    private static final int MONTHLY_BAR_WIDTH = 24;
    private static final DateTimeFormatter MONTH_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM");

    /**
     * Renders the last 12 months of named/automatic publications as a markdown table with
     * inline ASCII bars. Bar lengths are scaled to the highest count across all 12 months and
     * both types so the two columns share the same visual scale and you can compare named vs
     * automatic by eye. Markdown doesn't render real graphs, so the table-with-bars approach
     * keeps the section readable in any renderer (GitHub, plain text, IDE preview).
     */
    /**
     * Renders the per-pattern split of mismatching rows with absolute counts AND share-of-total
     * percentages. The percentages help the reader see at a glance how much of the mismatching
     * bucket is benign formatting drift vs genuinely different versions - the bulk is almost
     * always {@code substantive}, but the formatting buckets are concentrated in a few prolific
     * publishers and worth surfacing separately.
     */
    private static void renderMismatchPatterns(StringBuilder builder, MismatchPatterns patterns) {
        long total = patterns.total();
        builder.append("## Mismatching module-info version patterns\n\n");
        builder.append("Breaks down the publications whose `module-info` version differs from the Maven coordinate version (the middle row of the previous table) by *why* they differ. The first several rows are formatting drift (publisher forgot to drop a `-SNAPSHOT`, a repackager's coordinate suffix, build-metadata `+` labels, extra dot-segments); `Unresolved placeholder` is a build-time `${...}` substitution that leaked through; `Different major segment` is a strong proxy for shaded/bundled artifacts whose `module-info` comes from a different versioning lineage; `Substantively different` is the remainder where the versions share a first segment but otherwise differ. Percentages are share of the differing-version bucket.\n\n");
        builder.append("| Pattern | Rows | Share |\n|---|---:|---:|\n");
        appendMismatchRow(builder, "Module = Maven + `-SNAPSHOT` (release that forgot to drop SNAPSHOT)", patterns.snapshotSuffix(), total);
        appendMismatchRow(builder, "Module = Maven + `-<other suffix>` (build label, patch tag)", patterns.otherSuffixAdded(), total);
        appendMismatchRow(builder, "Maven = Module + `-<suffix>` (repackager appended a coordinate suffix)", patterns.suffixDropped(), total);
        appendMismatchRow(builder, "Module = Maven + `.<segment>` (extra dot-segment in module-info)", patterns.segmentAdded(), total);
        appendMismatchRow(builder, "Maven = Module + `.<segment>` (extra dot-segment in coordinate)", patterns.segmentDropped(), total);
        appendMismatchRow(builder, "Module = Maven + `+<metadata>` (build metadata in module-info)", patterns.plusMetadataAdded(), total);
        appendMismatchRow(builder, "Maven = Module + `+<metadata>` (build metadata in coordinate)", patterns.plusMetadataDropped(), total);
        appendMismatchRow(builder, "Unresolved `${...}` placeholder in either version", patterns.unresolvedPlaceholder(), total);
        appendMismatchRow(builder, "Different major segment (likely shaded/bundled artifact)", patterns.differentMajor(), total);
        appendMismatchRow(builder, "Substantively different (same major, different version)", patterns.substantive(), total);
        builder.append('\n');
    }

    private static void appendMismatchRow(StringBuilder builder, String label, long count, long total) {
        builder.append("| ").append(label).append(" | ").append(fmt(count)).append(" | ");
        if (total > 0L) {
            double percent = count * 100.0 / (double) total;
            builder.append(String.format(Locale.ROOT, "%.1f", percent)).append('%');
        } else {
            builder.append('-');
        }
        builder.append(" |\n");
    }

    private static void renderMonthlyPublications(StringBuilder builder, List<MonthlyPublication> monthly) {
        builder.append("## Monthly publications by type (last 12 months)\n\n");
        builder.append("Per-month counts of **distinct entities** that published in the month. `Named`/`Automatic` count distinct canonical (owner-resolved) module names by type; `Non-modular artifacts` counts distinct `(groupId, artifactId)` that published a coordinate carrying no module identity (distinct scanned artifacts minus distinct modular artifacts in the month). All three share one bar scale. Non-modular artifacts outnumber modules roughly 10:1, so the columns use different shades to stay legible at a glance: `█` named, `▓` automatic, `░` non-modular. The `(x%)` after each count is that type's share of the month's total (named + automatic + non-modular), so a row's three percentages sum to ~100%.\n\n");
        long maxCount = 0L;
        for (MonthlyPublication entry : monthly) {
            maxCount = Math.max(maxCount, Math.max(Math.max(entry.named(), entry.automatic()), entry.nonModular()));
        }
        builder.append("| Month | Named modules | Automatic modules | Non-modular artifacts |\n|---|---|---|---|\n");
        for (MonthlyPublication entry : monthly) {
            long rowTotal = entry.named() + entry.automatic() + entry.nonModular();
            builder.append("| ").append(MONTH_FORMAT.format(entry.month()))
                    .append(" | ").append(monthlyCell(entry.named(), maxCount, '█', rowTotal))
                    .append(" | ").append(monthlyCell(entry.automatic(), maxCount, '▓', rowTotal))
                    .append(" | ").append(monthlyCell(entry.nonModular(), maxCount, '░', rowTotal))
                    .append(" |\n");
        }
        builder.append('\n');
    }

    /**
     * Renders one bar cell. {@code glyph} differentiates the columns by shade (a stand-in for
     * colour in monospace markdown): the short modular bars use solid/dark glyphs so they stay
     * legible on the shared scale, while the much longer non-modular bar uses a light shade so it
     * reads as background rather than swamping the row. The trailing {@code (x%)} is the cell's
     * share of {@code rowTotal} (the month's named + automatic + non-modular sum), so the three
     * percentages in a row add up to ~100%.
     */
    private static String monthlyCell(long count, long maxCount, char glyph, long rowTotal) {
        String share = rowTotal > 0L
                ? String.format(Locale.ROOT, " (%.1f%%)", 100.0 * (double) count / (double) rowTotal)
                : "";
        if (count == 0L || maxCount <= 0L) {
            return fmt(count) + share;
        }
        int bars = (int) Math.round((double) count * MONTHLY_BAR_WIDTH / (double) maxCount);
        if (bars == 0) {
            bars = 1;
        }
        return "`" + String.valueOf(glyph).repeat(bars) + "`&nbsp;" + fmt(count) + share;
    }

    private static void atomicWrite(Path output, String content) throws IOException {
        Path parent = output.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        Path temp = output.resolveSibling(output.getFileName() + ".tmp");
        Files.writeString(temp, content, StandardCharsets.UTF_8);
        try {
            Files.move(temp, output, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (AtomicMoveNotSupportedException atomicNotSupported) {
            Files.move(temp, output, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private static final class Aggregator {

        private final Instant generatedAt;
        private final long recentCutoffMillis;
        private final int topN;

        private int totalModules;
        private long totalVersionRows;
        // Same as totalVersionRows but unfiltered: counts every row in every versions(-classifier).tsv.
        // Only used to compute "Non-module artifacts" (= successful scans minus *physical* modular
        // JARs); the rest of the summary uses totalVersionRows, which is filtered to artifacts.tsv.
        private long totalVersionRowsAll;
        private long totalNamedVersionRows;
        private long totalAutomaticVersionRows;
        private int namedDistinctModules;
        private int automaticDistinctModules;
        private long namedRows;
        private long automaticRows;
        private int autoToNamed;
        private int namedToAuto;
        private int modulesPublishedLastWeek;
        private int namedModulesPublishedLastWeek;
        private int automaticModulesPublishedLastWeek;
        private long versionsPublishedLastWeek;
        private long namedVersionsPublishedLastWeek;
        private long automaticVersionsPublishedLastWeek;
        private int collidingModules;
        private int classifierVariants;
        private final SortedMap<Integer, Integer> sharedSegmentHistogram = new TreeMap<>();
        private long latestPublishedMillis;
        private long moduleVersionExplicit;
        private long moduleVersionMismatching;
        private long moduleVersionAbsent;
        private long latestModuleVersionExplicit;
        private long latestModuleVersionMismatching;
        private long latestModuleVersionAbsent;
        // Per distinct module name (key = dotted module name): two flags packed into a byte.
        //   bit 0 set when any named row across any classifier variant is mismatching;
        //   bit 1 set when any named row is non-mismatching (explicit or absent).
        // Used to size the "drop every mismatching row" impact aggregated to module names.
        private final Map<String, int[]> moduleNameMismatchFlags = new HashMap<>();
        private long mismatchSnapshotSuffix;
        private long mismatchOtherSuffixAdded;
        private long mismatchSuffixDropped;
        private long mismatchSegmentAdded;
        private long mismatchSegmentDropped;
        private long mismatchPlusMetadataAdded;
        private long mismatchPlusMetadataDropped;
        private long mismatchUnresolvedPlaceholder;
        private long mismatchDifferentMajor;
        private long mismatchSubstantive;

        private final Set<Path> dirsWithClassifier = new HashSet<>();
        private final Set<String> distinctGroupIds = new HashSet<>();
        private final Map<String, Integer> versionsCountByModule = new HashMap<>();
        private final Map<String, Integer> distinctGroupsCountByModule = new HashMap<>();
        private final Map<String, Set<String>> modulesByGroup = new HashMap<>();
        private final Map<String, Long> versionsByGroup = new HashMap<>();
        private final Map<String, Long> latestPublishedByModule = new HashMap<>();
        private long processingErrorTotal;
        private long scannedArtifactTotal;
        private long distinctMavenArtifactTotal;
        private final Set<String> moduleKeysWithModuleVersion = new HashSet<>();
        private final Map<String, Long> errorMessageCounts = new HashMap<>();
        // The monthly chart counts DISTINCT entities per calendar month, not publication rows:
        // distinct canonical module names (split by the row's type) for the modular columns, and
        // distinct (groupId, artifactId) for the non-modular column. Counting distinct entities
        // keeps the three columns within one order of magnitude so they share a single bar scale.
        // All four maps are bounded to the rendered 12-month window (see monthlyWindowStart) so
        // they don't accumulate sets for the entire ~20-year history.
        private final Map<YearMonth, Set<String>> monthlyNamedNames = new HashMap<>();
        private final Map<YearMonth, Set<String>> monthlyAutomaticNames = new HashMap<>();
        // Distinct scanned (groupId, artifactId) per month is just a count: each scanned.tsv file
        // is exactly one artifact, so counting files that published in a month counts distinct
        // artifacts. Modular artifacts need a set because one (groupId, artifactId) can appear
        // under several module-name directories (a JAR that renamed its module across versions).
        private final Map<YearMonth, long[]> monthlyScannedArtifactCount = new HashMap<>();
        private final Map<YearMonth, Set<String>> monthlyModularArtifacts = new HashMap<>();
        // Recent-window (7-day) equivalents.
        private long recentScannedArtifactCount;
        private final Set<String> recentModularArtifacts = new HashSet<>();
        private final YearMonth monthlyWindowStart;
        private long resolvedModuleVersions;

        Aggregator(Instant generatedAt, Instant monthlyAnchor, int topN, long latestPublishedMillis) {
            this.generatedAt = Objects.requireNonNull(generatedAt, "generatedAt");
            Objects.requireNonNull(monthlyAnchor, "monthlyAnchor");
            if (topN < 1) {
                throw new IllegalArgumentException("topN must be >= 1, got " + topN);
            }
            this.topN = topN;
            // Anchor the recent window to the freshest tracked publication (max publishedAt), not
            // wall-clock now. The upstream index lags real time by up to a week, so a now-relative
            // window is usually empty. Fall back to generatedAt when there is no data at all.
            long anchor = latestPublishedMillis > 0L ? latestPublishedMillis : generatedAt.toEpochMilli();
            this.recentCutoffMillis = anchor - RECENT_WINDOW.toMillis();
            // The 12-month axis ends at the crawler-state anchor (index timestamp), so the summary
            // is a pure function of the data it was built from rather than the render wall clock.
            this.monthlyWindowStart = YearMonth.from(monthlyAnchor.atZone(ZoneOffset.UTC)).minusMonths(11);
        }

        void acceptDirectory(Path modulesRoot, Path dir) throws IOException {
            List<ClassifierFile> versionFiles = listTsv(dir, VERSIONS_STEM);
            if (versionFiles.isEmpty()) {
                return;
            }
            Map<String, ClassifierFile> artifactsByClassifier = new HashMap<>();
            for (ClassifierFile entry : listTsv(dir, ARTIFACTS_STEM)) {
                artifactsByClassifier.put(nullToEmpty(entry.classifier()), entry);
            }
            String moduleName = pathToModuleName(modulesRoot, dir);
            if (moduleName.isEmpty()) {
                return;
            }
            boolean dirHasClassifier = false;
            for (ClassifierFile versionsEntry : versionFiles) {
                String classifier = versionsEntry.classifier();
                if (classifier != null) {
                    dirHasClassifier = true;
                    classifierVariants++;
                }
                ClassifierFile artifactsEntry = artifactsByClassifier.get(nullToEmpty(classifier));
                acceptModule(dir, moduleName, classifier, versionsEntry.path(), artifactsEntry == null ? null : artifactsEntry.path());
            }
            if (dirHasClassifier) {
                dirsWithClassifier.add(dir);
            }
            // Sum rows across every modules[-classifier].tsv in this directory for the Totals
            // "Distinct module versions in resolved catalogue" tally.
            for (ClassifierFile entry : listTsv(dir, MODULES_STEM)) {
                resolvedModuleVersions += countTsvRows(entry.path());
            }
        }

        private void acceptModule(Path dir, String moduleName, String classifier, Path versionsFile, Path artifactsFile) throws IOException {
            totalModules++;
            String moduleKey = displayKey(moduleName, classifier);

            List<ModuleEntry> versions = readVersionsFile(versionsFile);
            // Unfiltered physical-rows tally - needed so the "Non-module artifacts" metric in
            // Totals (= successful scans minus physical modular JARs) doesn't count audit rows
            // as if they had no module info. The rest of the aggregation uses resolvedVersions.
            totalVersionRowsAll += versions.size();
            // Distinct modular (groupId, artifactId) per window month and per recent window. Raw
            // (all rows, every groupId and classifier) so the scanned-minus-modular subtraction
            // that yields the non-modular count matches the catalogue-wide Totals definition.
            for (ModuleEntry entry : versions) {
                long pub = entry.publishedAt();
                if (pub <= 0L) {
                    continue;
                }
                YearMonth bucket = YearMonth.from(Instant.ofEpochMilli(pub).atZone(ZoneOffset.UTC));
                boolean inWindow = !bucket.isBefore(monthlyWindowStart);
                boolean inRecent = pub >= recentCutoffMillis;
                if (!inWindow && !inRecent) {
                    continue;
                }
                String artifactKey = entry.groupId() + '\t' + entry.artifactId();
                if (inWindow) {
                    monthlyModularArtifacts.computeIfAbsent(bucket, _ -> new HashSet<>()).add(artifactKey);
                }
                if (inRecent) {
                    recentModularArtifacts.add(artifactKey);
                }
            }

            // Load the resolved view once. The keys are used as a row-level filter for the
            // ecosystem metrics (so non-authoritative audit rows don't pollute the picture);
            // the full list also feeds the type-breakdown / transitions / shared-segment logic.
            List<ArtifactsEntry> artifacts = artifactsFile == null ? List.of() : readArtifactsFile(artifactsFile);
            Set<String> artifactsKeys = new HashSet<>(artifacts.size());
            for (ArtifactsEntry entry : artifacts) {
                artifactsKeys.add(artifactsKey(entry.version().raw(), entry.groupId(), entry.artifactId()));
            }
            List<ModuleEntry> resolvedVersions = artifactsKeys.isEmpty()
                    ? List.of()
                    : versions.stream().filter(entry -> artifactsKeys.contains(artifactsKey(entry))).toList();

            // === Audit metrics (use ALL versions, including non-authoritative ones) ===
            // Collisions and the per-module distinct-groupId count are intentionally cross-history:
            // they describe what publishers have ever claimed this module name, not what consumers
            // currently see. Filtering would defeat their purpose.
            Set<String> auditGroupsHere = new HashSet<>();
            for (ModuleEntry entry : versions) {
                auditGroupsHere.add(entry.groupId());
            }
            distinctGroupsCountByModule.put(moduleKey, auditGroupsHere.size());
            if (auditGroupsHere.size() > 1) {
                collidingModules++;
            }

            // === Ecosystem metrics (only the rows that survived ownership resolution) ===
            // Everything below operates on resolvedVersions only. Modules whose resolved view
            // is empty contribute nothing here: they're not currently authoritative for anything,
            // so their rows shouldn't show up in any "what's the catalogue look like" figure.
            if (classifier == null && !resolvedVersions.isEmpty()) {
                versionsCountByModule.put(moduleName, resolvedVersions.size());
            }
            Set<String> resolvedGroupsHere = new HashSet<>();
            boolean recent = false;
            long recentRows = 0L;
            long recentRowsNamed = 0L;
            long recentRowsAutomatic = 0L;
            long moduleLatestMillis = 0L;
            for (ModuleEntry entry : resolvedVersions) {
                totalVersionRows++;
                distinctGroupIds.add(entry.groupId());
                resolvedGroupsHere.add(entry.groupId());
                versionsByGroup.merge(entry.groupId(), 1L, Long::sum);
                if (entry.publishedAt() > latestPublishedMillis) {
                    latestPublishedMillis = entry.publishedAt();
                }
                if (entry.publishedAt() > moduleLatestMillis) {
                    moduleLatestMillis = entry.publishedAt();
                }
                if (entry.publishedAt() >= recentCutoffMillis) {
                    recent = true;
                    recentRows++;
                    if (entry.type() == ModuleType.NAMED) {
                        recentRowsNamed++;
                    } else if (entry.type() == ModuleType.AUTOMATIC) {
                        recentRowsAutomatic++;
                    }
                }
                if (entry.type() == ModuleType.AUTOMATIC) {
                    totalAutomaticVersionRows++;
                }
                if (entry.publishedAt() > 0L && entry.type() != null) {
                    YearMonth bucket = YearMonth.from(Instant.ofEpochMilli(entry.publishedAt()).atZone(ZoneOffset.UTC));
                    if (!bucket.isBefore(monthlyWindowStart)) {
                        // Distinct module names per month (deduped across versions and classifier
                        // variants, since the chart counts modules by name, not publication rows).
                        if (entry.type() == ModuleType.NAMED) {
                            monthlyNamedNames.computeIfAbsent(bucket, _ -> new HashSet<>()).add(moduleName);
                        } else if (entry.type() == ModuleType.AUTOMATIC) {
                            monthlyAutomaticNames.computeIfAbsent(bucket, _ -> new HashSet<>()).add(moduleName);
                        }
                    }
                }
                // Automatic modules have no module-info to declare a version, so they would
                // always land in the "absent" bucket and dilute the signal. Skip them so the
                // breakdown reflects only the population where the question is meaningful.
                if (entry.type() == ModuleType.NAMED) {
                    totalNamedVersionRows++;
                    String moduleVersion = entry.moduleVersion();
                    if (!moduleVersion.isEmpty()) {
                        moduleKeysWithModuleVersion.add(moduleKey);
                    }
                    // The "module-info version field across named publications" section is
                    // restricted to canonical (no-classifier) publications. Classifier-keyed
                    // rows (mostly fat-jar / shaded variants that bundle a module under their
                    // own Maven coordinate) overwhelm the signal here: the bundled module's
                    // module-info version is *expected* to contradict the bundling Maven
                    // version, so counting those would describe fat-jar mechanics rather than
                    // the canonical publication's coverage.
                    if (classifier == null) {
                        int[] flags = moduleNameMismatchFlags.computeIfAbsent(moduleName, _ -> new int[1]);
                        if (moduleVersion.isEmpty()) {
                            moduleVersionAbsent++;
                            flags[0] |= 2;  // non-mismatching survives
                        } else if (new Version(moduleVersion).equals(entry.mavenVersion())) {
                            // Semantic Version equality folds trailing-zero variants ("1.0" vs
                            // "1.0.0") and qualifier aliases ("1.0-ga" vs "1.0") into the
                            // explicit bucket.
                            moduleVersionExplicit++;
                            flags[0] |= 2;  // non-mismatching survives
                        } else {
                            moduleVersionMismatching++;
                            categoriseMismatch(moduleVersion, entry.mavenVersion().raw());
                            flags[0] |= 1;  // mismatching row present
                        }
                    }
                }
            }
            if (moduleLatestMillis > 0L) {
                latestPublishedByModule.put(moduleKey, moduleLatestMillis);
            }
            for (String group : resolvedGroupsHere) {
                modulesByGroup.computeIfAbsent(group, _ -> new HashSet<>()).add(moduleKey);
            }
            if (recent) {
                modulesPublishedLastWeek++;
                versionsPublishedLastWeek += recentRows;
                namedVersionsPublishedLastWeek += recentRowsNamed;
                automaticVersionsPublishedLastWeek += recentRowsAutomatic;
                // Per-module named/automatic accounting is decided by the module's resolved
                // latest type below; recording it here would be premature.
            }

            // === Type breakdown, transitions, shared-segment histogram (resolved-view inherent) ===
            if (!artifacts.isEmpty()) {
                ArtifactsEntry latest = artifacts.get(0);
                if (latest.type() == ModuleType.NAMED) {
                    namedDistinctModules++;
                    if (recent) {
                        namedModulesPublishedLastWeek++;
                    }
                    // Latest-version module-info coverage: classify the canonical row matching
                    // the latest artifacts.tsv entry. Restricted to canonical (no-classifier)
                    // publications for the same reason as the per-row block above. The lookup
                    // keys are (mavenVersion.raw, groupId, artifactId), which uniquely identify
                    // a single resolved row.
                    if (classifier == null) {
                        String latestVersionRaw = latest.version().raw();
                        String latestGroupId = latest.groupId();
                        String latestArtifactId = latest.artifactId();
                        for (ModuleEntry candidate : resolvedVersions) {
                            if (candidate.mavenVersion().raw().equals(latestVersionRaw)
                                    && candidate.groupId().equals(latestGroupId)
                                    && candidate.artifactId().equals(latestArtifactId)) {
                                String latestModuleVersion = candidate.moduleVersion();
                                if (latestModuleVersion.isEmpty()) {
                                    latestModuleVersionAbsent++;
                                } else if (new Version(latestModuleVersion).equals(candidate.mavenVersion())) {
                                    latestModuleVersionExplicit++;
                                } else {
                                    latestModuleVersionMismatching++;
                                    int[] mismatchFlags = moduleNameMismatchFlags.computeIfAbsent(moduleName, _ -> new int[1]);
                                    mismatchFlags[0] |= 4;  // canonical latest row is mismatching
                                }
                                break;
                            }
                        }
                    }
                } else if (latest.type() == ModuleType.AUTOMATIC) {
                    automaticDistinctModules++;
                    if (recent) {
                        automaticModulesPublishedLastWeek++;
                    }
                }
                // Transitions are detected purely from the resolved view: if the latest version
                // is one type and any older version (the rest of the version-descending list)
                // is the other type, that's a transition. This avoids counting cross-publisher
                // type swings that exist in the audit log but were filtered out by ownership.
                boolean hasNamed = false;
                boolean hasAutomatic = false;
                for (ArtifactsEntry entry : artifacts) {
                    if (entry.type() == ModuleType.NAMED) {
                        hasNamed = true;
                    } else if (entry.type() == ModuleType.AUTOMATIC) {
                        hasAutomatic = true;
                    }
                }
                if (hasNamed && hasAutomatic) {
                    if (latest.type() == ModuleType.NAMED) {
                        autoToNamed++;
                    } else if (latest.type() == ModuleType.AUTOMATIC) {
                        namedToAuto++;
                    }
                }
                // Shared-segment histogram is canonical-only: a module's classifier variants share
                // the canonical's groupId by construction, so counting each variant would just
                // multiply the same bucket without adding signal.
                if (classifier == null) {
                    String groupId = latest.groupId();
                    int sharedSegments = sharedLeadingSegments(moduleName, groupId);
                    sharedSegmentHistogram.merge(sharedSegments, 1, Integer::sum);
                }
                for (ArtifactsEntry entry : artifacts) {
                    if (entry.type() == ModuleType.NAMED) {
                        namedRows++;
                    } else if (entry.type() == ModuleType.AUTOMATIC) {
                        automaticRows++;
                    }
                }
            }
        }

        void acceptScannedFile(Path file) throws IOException {
            // Each scanned tsv file corresponds to a single (groupId, artifactId) Maven coordinate,
            // regardless of how many versions or classifiers it contains. We count the file toward
            // the distinct-artifact tally only when at least one row in it is a successful scan;
            // a coordinate whose every version is a permanent failure never actually delivered an
            // artifact and shouldn't inflate the catalogue size.
            boolean anySuccess = false;
            // This file is exactly one (groupId, artifactId); record which window months and the
            // recent window it published a successful scan in, then bump the per-month distinct-
            // artifact counts once each (deduping multiple versions in the same month).
            Set<YearMonth> scannedMonths = null;
            boolean scannedRecent = false;
            try (Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8)) {
                Iterator<String> iterator = lines.iterator();
                while (iterator.hasNext()) {
                    String line = iterator.next();
                    if (line.isEmpty()) {
                        continue;
                    }
                    ScannedEntry entry;
                    try {
                        entry = ScannedEntry.parse(line);
                    } catch (IllegalArgumentException malformed) {
                        continue;
                    }
                    scannedArtifactTotal++;
                    if (!entry.isFailed()) {
                        anySuccess = true;
                        long pub = entry.publishedAt();
                        if (pub > 0L) {
                            YearMonth bucket = YearMonth.from(Instant.ofEpochMilli(pub).atZone(ZoneOffset.UTC));
                            if (!bucket.isBefore(monthlyWindowStart)) {
                                if (scannedMonths == null) {
                                    scannedMonths = new HashSet<>();
                                }
                                scannedMonths.add(bucket);
                            }
                            if (pub >= recentCutoffMillis) {
                                scannedRecent = true;
                            }
                        }
                        continue;
                    }
                    processingErrorTotal++;
                    String normalized = normalizeErrorMessage(entry.errorMessage());
                    errorMessageCounts.merge(normalized, 1L, Long::sum);
                }
            }
            if (anySuccess) {
                distinctMavenArtifactTotal++;
            }
            if (scannedMonths != null) {
                for (YearMonth month : scannedMonths) {
                    monthlyScannedArtifactCount.computeIfAbsent(month, _ -> new long[1])[0]++;
                }
            }
            if (scannedRecent) {
                recentScannedArtifactCount++;
            }
        }

        Stats toStats(State state, List<String> topYears) {
            // Non-module artifacts = JARs that scanned successfully but contained no module
            // identity (no module-info, no Automatic-Module-Name), so they didn't land in
            // any versions.tsv. Computed as (scanned - failed) - PHYSICAL module rows; using
            // totalVersionRowsAll (not the artifacts-filtered totalVersionRows) so audit rows
            // for shaded JARs still count as "modular" - they did declare a module, just not
            // an authoritative one. Clamped to 0 in case scanned data temporarily lags
            // versions data mid-crawl.
            long successfullyScanned = scannedArtifactTotal - processingErrorTotal;
            long nonModuleArtifacts = Math.max(0L, successfullyScanned - totalVersionRowsAll);
            Totals totals = new Totals(
                    totalModules,
                    totalVersionRows,
                    totalNamedVersionRows,
                    totalAutomaticVersionRows,
                    moduleVersionExplicit + moduleVersionMismatching,
                    moduleKeysWithModuleVersion.size(),
                    resolvedModuleVersions,
                    successfullyScanned,
                    nonModuleArtifacts,
                    distinctMavenArtifactTotal,
                    distinctGroupIds.size(),
                    latestPublishedMillis > 0L ? Optional.of(Instant.ofEpochMilli(latestPublishedMillis)) : Optional.empty());
            TypeBreakdown named = new TypeBreakdown(namedDistinctModules, namedRows);
            TypeBreakdown automatic = new TypeBreakdown(automaticDistinctModules, automaticRows);
            Transitions transitions = new Transitions(autoToNamed, namedToAuto);
            long recentNonModular = Math.max(0L, recentScannedArtifactCount - recentModularArtifacts.size());
            RecentActivity recent = new RecentActivity(
                    modulesPublishedLastWeek,
                    namedModulesPublishedLastWeek,
                    automaticModulesPublishedLastWeek,
                    versionsPublishedLastWeek,
                    namedVersionsPublishedLastWeek,
                    automaticVersionsPublishedLastWeek,
                    recentNonModular);
            NamingPatterns naming = new NamingPatterns(
                    collidingModules,
                    sharedSegmentHistogram,
                    dirsWithClassifier.size(),
                    classifierVariants);
            List<TopLatestEntry> latestUpdates = latestPublishedByModule.entrySet().stream()
                    .filter(e -> e.getValue() >= recentCutoffMillis)
                    .sorted((a, b) -> {
                        int cmp = Long.compare(b.getValue(), a.getValue());
                        return cmp != 0 ? cmp : a.getKey().compareTo(b.getKey());
                    })
                    .limit(topN)
                    .map(e -> new TopLatestEntry(e.getKey(), Instant.ofEpochMilli(e.getValue())))
                    .toList();
            List<TopAvgEntry> groupAverages = modulesByGroup.entrySet().stream()
                    .map(entry -> {
                        String group = entry.getKey();
                        int modules = entry.getValue().size();
                        long total = versionsByGroup.getOrDefault(group, 0L);
                        double avg = modules == 0 ? 0d : (double) total / (double) modules;
                        return new TopAvgEntry(group, modules, total, avg);
                    })
                    // Filter to groups with at least a few modules so the average is meaningful
                    // (a one-module group of N versions would otherwise dominate trivially).
                    .filter(entry -> entry.modules() >= 3)
                    .sorted((a, b) -> {
                        int cmp = Double.compare(b.average(), a.average());
                        return cmp != 0 ? cmp : a.key().compareTo(b.key());
                    })
                    .limit(topN)
                    .toList();
            TopLists top = new TopLists(
                    topModulesByVersionCount(versionsCountByModule, topN),
                    topByValue(modulesByGroup.entrySet().stream()
                            .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().size())), topN),
                    topByValue(distinctGroupsCountByModule.entrySet().stream()
                            .filter(e -> e.getValue() > 1)
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)), topN),
                    latestUpdates,
                    groupAverages);
            long incorrectlyIndexed = errorMessageCounts.getOrDefault(INCORRECTLY_INDEXED_ERROR, 0L);
            // The mis-stamped-404 class is broken out into the summary table, so drop it from the
            // top-N message list to surface the genuine artifact errors that would otherwise be
            // buried beneath its ~2 M count.
            List<TopEntry> topErrorMessages = errorMessageCounts.entrySet().stream()
                    .filter(entry -> !entry.getKey().equals(INCORRECTLY_INDEXED_ERROR))
                    .sorted((a, b) -> {
                        int cmp = Long.compare(b.getValue(), a.getValue());
                        return cmp != 0 ? cmp : a.getKey().compareTo(b.getKey());
                    })
                    .limit(topN)
                    .map(entry -> new TopEntry(entry.getKey(), entry.getValue()))
                    .toList();
            ProcessingErrors errors = new ProcessingErrors(processingErrorTotal, incorrectlyIndexed, topErrorMessages);
            ModuleVersionCoverage coverage = new ModuleVersionCoverage(
                    moduleVersionExplicit,
                    moduleVersionMismatching,
                    moduleVersionAbsent);
            LatestModuleVersionCoverage latestCoverage = new LatestModuleVersionCoverage(
                    latestModuleVersionExplicit,
                    latestModuleVersionMismatching,
                    latestModuleVersionAbsent);
            int impactClean = 0;
            int impactPartial = 0;
            int impactFullyLost = 0;
            int impactLosingLatest = 0;
            for (int[] flagBox : moduleNameMismatchFlags.values()) {
                int flags = flagBox[0];
                boolean hasMismatch = (flags & 1) != 0;
                boolean hasNonMismatch = (flags & 2) != 0;
                boolean latestMismatch = (flags & 4) != 0;
                if (!hasMismatch) {
                    impactClean++;
                } else if (hasNonMismatch) {
                    impactPartial++;
                } else {
                    impactFullyLost++;
                }
                if (latestMismatch) {
                    impactLosingLatest++;
                }
            }
            int impactTotal = impactClean + impactPartial + impactFullyLost;
            MismatchImpact mismatchImpact = new MismatchImpact(
                    impactTotal, impactClean, impactPartial, impactFullyLost, impactLosingLatest);
            MismatchPatterns mismatchPatterns = new MismatchPatterns(
                    mismatchSnapshotSuffix,
                    mismatchOtherSuffixAdded,
                    mismatchSuffixDropped,
                    mismatchSegmentAdded,
                    mismatchSegmentDropped,
                    mismatchPlusMetadataAdded,
                    mismatchPlusMetadataDropped,
                    mismatchUnresolvedPlaceholder,
                    mismatchDifferentMajor,
                    mismatchSubstantive);
            YearMonth currentMonth = monthlyWindowStart.plusMonths(11);
            List<MonthlyPublication> monthly = new ArrayList<>(12);
            for (int i = 11; i >= 0; i--) {
                YearMonth month = currentMonth.minusMonths(i);
                long namedNames = monthlyNamedNames.getOrDefault(month, Set.of()).size();
                long automaticNames = monthlyAutomaticNames.getOrDefault(month, Set.of()).size();
                long scannedArtifacts = monthlyScannedArtifactCount.getOrDefault(month, new long[1])[0];
                long modularArtifacts = monthlyModularArtifacts.getOrDefault(month, Set.of()).size();
                long nonModular = Math.max(0L, scannedArtifacts - modularArtifacts);
                monthly.add(new MonthlyPublication(month, namedNames, automaticNames, nonModular));
            }
            return new Stats(generatedAt, state, totals, named, automatic, coverage, latestCoverage,
                    mismatchImpact, mismatchPatterns, transitions, recent, monthly, naming, errors, top, topYears);
        }

        /**
         * Classifies a single mismatching row into the pattern buckets exposed by
         * {@link MismatchPatterns}. The first matching rule wins, so e.g.
         * {@code module = maven + "-SNAPSHOT"} is counted separately from the broader
         * {@code module = maven + "-<other suffix>"} category.
         */
        private void categoriseMismatch(String moduleVersion, String mavenRaw) {
            if (moduleVersion.equals(mavenRaw + "-SNAPSHOT")) {
                mismatchSnapshotSuffix++;
            } else if (moduleVersion.startsWith(mavenRaw + "-")) {
                mismatchOtherSuffixAdded++;
            } else if (mavenRaw.startsWith(moduleVersion + "-")) {
                mismatchSuffixDropped++;
            } else if (moduleVersion.startsWith(mavenRaw + ".")) {
                mismatchSegmentAdded++;
            } else if (mavenRaw.startsWith(moduleVersion + ".")) {
                mismatchSegmentDropped++;
            } else if (moduleVersion.startsWith(mavenRaw + "+")) {
                mismatchPlusMetadataAdded++;
            } else if (mavenRaw.startsWith(moduleVersion + "+")) {
                mismatchPlusMetadataDropped++;
            } else if (moduleVersion.contains("${") || mavenRaw.contains("${")) {
                // Unresolved Maven property placeholder leaked into a published artifact - rare
                // but real (a build-time substitution failed and the version literal ${...} got
                // baked into either module-info or the coordinate).
                mismatchUnresolvedPlaceholder++;
            } else if (!firstDotSegment(moduleVersion).equals(firstDotSegment(mavenRaw))) {
                // First dot-segment differs. Most often this is a shaded/bundled artifact whose
                // module-info comes from a completely different versioning lineage than the host
                // Maven coordinate (e.g. jackson-jr-all 2.15.1 declaring module-info for the
                // bundled fastdoubleparser 0.9.0). Separating it from the same-major drift gives
                // a usable proxy for "how much of the catalogue is shaded JARs vs real drift".
                mismatchDifferentMajor++;
            } else {
                mismatchSubstantive++;
            }
        }

        private static String firstDotSegment(String version) {
            int dot = version.indexOf('.');
            return dot < 0 ? version : version.substring(0, dot);
        }

        private static String artifactsKey(ModuleEntry entry) {
            return artifactsKey(entry.mavenVersion().raw(), entry.groupId(), entry.artifactId());
        }

        private static String artifactsKey(String version, String groupId, String artifactId) {
            return version + '|' + groupId + '|' + artifactId;
        }
    }

    private record ClassifierFile(String classifier, Path path) {
    }

    private static List<ClassifierFile> listTsv(Path dir, String stem) throws IOException {
        List<ClassifierFile> result = new ArrayList<>();
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(dir)) {
            for (Path entry : entries) {
                if (!Files.isRegularFile(entry)) {
                    continue;
                }
                String name = entry.getFileName().toString();
                if (!name.endsWith(TSV_EXTENSION)) {
                    continue;
                }
                String base = name.substring(0, name.length() - TSV_EXTENSION.length());
                if (base.equals(stem)) {
                    result.add(new ClassifierFile(null, entry));
                } else if (base.startsWith(stem + '-')) {
                    result.add(new ClassifierFile(base.substring(stem.length() + 1), entry));
                }
            }
        }
        return result;
    }

    private static List<ModuleEntry> readVersionsFile(Path file) throws IOException {
        List<ModuleEntry> entries = new ArrayList<>();
        try (Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8)) {
            lines.filter(line -> !line.isEmpty()).map(ModuleEntry::parse).forEach(entries::add);
        }
        return entries;
    }

    private static long countTsvRows(Path file) throws IOException {
        try (Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8)) {
            return lines.filter(line -> !line.isEmpty()).count();
        }
    }

    private static List<ArtifactsEntry> readArtifactsFile(Path file) throws IOException {
        List<ArtifactsEntry> entries = new ArrayList<>();
        try (Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8)) {
            lines.filter(line -> !line.isEmpty()).map(ArtifactsEntry::parse).forEach(entries::add);
        }
        return entries;
    }

    private static String pathToModuleName(Path modulesRoot, Path dir) {
        Path relative = modulesRoot.relativize(dir);
        StringBuilder builder = new StringBuilder();
        for (Path segment : relative) {
            if (!builder.isEmpty()) {
                builder.append('.');
            }
            builder.append(segment.toString());
        }
        return builder.toString();
    }

    private static String displayKey(String moduleName, String classifier) {
        return classifier == null ? moduleName : moduleName + " [-" + classifier + "]";
    }

    private static String nullToEmpty(String value) {
        return value == null ? "" : value;
    }

    /**
     * Ordered list of normalisation rules applied to scanner error messages so similar failures
     * aggregate cleanly. Each rule is a regex that matches a single error class (typically anchored
     * by enough surrounding text to be unambiguous) and a replacement that fills the variable
     * bits with placeholders like {@code <PACKAGE>}, {@code <CLASS>}, etc. Without this, every
     * shaded-package or every HTTP-404 path lands in its own bucket and crowds out the more
     * interesting patterns. Rules are applied in declaration order; the URL rule runs first so
     * subsequent rules can match against {@code <URL>} rather than a real URL.
     */
    private static final List<ErrorNormalizer> ERROR_NORMALIZERS = List.of(
            new ErrorNormalizer(Pattern.compile("https?://\\S+"), "<URL>"),
            new ErrorNormalizer(
                    Pattern.compile("Package \\S+ missing from ModulePackages class file attribute"),
                    "Package <PACKAGE> missing from ModulePackages class file attribute"),
            new ErrorNormalizer(
                    Pattern.compile("Exported package \\S+ already declared"),
                    "Exported package <PACKAGE> already declared"),
            new ErrorNormalizer(
                    Pattern.compile("Unsupported major\\.minor version \\d+\\.\\d+"),
                    "Unsupported major.minor version <VERSION>"),
            new ErrorNormalizer(
                    Pattern.compile("(CONSTANT_\\w+ at entry )\\d+( has illegal character: '[^']*')"),
                    "$1<ENTRY>$2"),
            new ErrorNormalizer(
                    Pattern.compile("(CONSTANT_\\w+ expected at entry: )\\d+"),
                    "$1<ENTRY>"),
            new ErrorNormalizer(
                    Pattern.compile("invalid header field \\(line \\d+\\)"),
                    "invalid header field (line <LINE>)"),
            new ErrorNormalizer(
                    Pattern.compile("Expected central file header signature at offset \\d+"),
                    "Expected central file header signature at offset <OFFSET>"),
            new ErrorNormalizer(
                    Pattern.compile("Expected ZIP64 end of central directory signature at offset \\d+"),
                    "Expected ZIP64 end of central directory signature at offset <OFFSET>"),
            new ErrorNormalizer(
                    Pattern.compile("Illegal character in path at index \\d+: .+$"),
                    "Illegal character in path at index <INDEX>: <PATH>"),
            new ErrorNormalizer(
                    Pattern.compile("Illegal character in fragment at index \\d+: .+$"),
                    "Illegal character in fragment at index <INDEX>: <PATH>"),
            new ErrorNormalizer(
                    Pattern.compile("\\S+: Invalid service type name: '[^']*' is not a Java identifier"),
                    "<CLASS>: Invalid service type name: '<NAME>' is not a Java identifier"),
            new ErrorNormalizer(
                    Pattern.compile("\\S+: is not a qualified name of a Java class in a named package"),
                    "<CLASS>: is not a qualified name of a Java class in a named package"),
            new ErrorNormalizer(
                    Pattern.compile("\\S+: unnamed package"),
                    "<CLASS>: unnamed package"));

    private record ErrorNormalizer(Pattern pattern, String replacement) {
    }

    /**
     * Normalises a recorded scanner error message so similar failures aggregate cleanly. The
     * variable parts of well-known error classes (URLs, shaded package names, classfile entry
     * indexes, HTTP status codes, line numbers, etc.) are replaced with stable placeholders so
     * messages that differ only in those bits collapse into one bucket. Anything that doesn't
     * match a known pattern is left verbatim so genuinely distinct error classes stay distinct.
     */
    public static String normalizeErrorMessage(String message) {
        String result = message;
        for (ErrorNormalizer normalizer : ERROR_NORMALIZERS) {
            result = normalizer.pattern().matcher(result).replaceAll(normalizer.replacement());
        }
        return result;
    }

    /** Escapes the pipe character so embedded `|` doesn't break a markdown table row. */
    private static String escapePipes(String value) {
        return value.indexOf('|') < 0 ? value : value.replace("|", "\\|");
    }

    /**
     * Returns the number of leading dot-separated segments that {@code moduleName} and
     * {@code groupId} share. Examples:
     *   {@code a.b.c}, {@code a.b.d}   →  2
     *   {@code org.slf4j}, {@code org.slf4j.api}  →  2
     *   {@code com.example}, {@code org.something}  →  0
     */
    static int sharedLeadingSegments(String moduleName, String groupId) {
        String[] moduleSegments = moduleName.split("\\.", -1);
        String[] groupSegments = groupId.split("\\.", -1);
        int max = Math.min(moduleSegments.length, groupSegments.length);
        int shared = 0;
        while (shared < max && moduleSegments[shared].equals(groupSegments[shared])) {
            shared++;
        }
        return shared;
    }

    /** Formats a non-negative integer with a regular space after every three digits from the right. */
    private static String fmt(long value) {
        String digits = Long.toString(value);
        int negative = digits.startsWith("-") ? 1 : 0;
        int len = digits.length() - negative;
        if (len <= 3) {
            return digits;
        }
        StringBuilder builder = new StringBuilder(digits.length() + len / 3);
        if (negative > 0) {
            builder.append('-');
        }
        int firstGroup = len % 3;
        if (firstGroup == 0) {
            firstGroup = 3;
        }
        builder.append(digits, negative, negative + firstGroup);
        for (int i = negative + firstGroup; i < digits.length(); i += 3) {
            builder.append(' ').append(digits, i, i + 3);
        }
        return builder.toString();
    }

    private static List<TopEntry> topByValue(Map<String, Integer> source, int limit) {
        return source.entrySet().stream()
                .sorted((a, b) -> {
                    int cmp = Integer.compare(b.getValue(), a.getValue());
                    return cmp != 0 ? cmp : a.getKey().compareTo(b.getKey());
                })
                .limit(limit)
                .map(entry -> new TopEntry(entry.getKey(), entry.getValue()))
                .toList();
    }

    /**
     * Builds the top-N module list with {@link #MODULE_FOLDS} applied: every module whose name
     * matches a fold's predicate is removed and a single synthetic row per fold is inserted
     * carrying the {@code min}/{@code max} of the absorbed counts. The synthetic row is ranked
     * by {@code max}, so the fold lands roughly where its highest-count member would have. With
     * the awssdk family, this turns ~7 adjacent identical rows into one and frees those slots
     * for other modules.
     */
    static List<TopEntry> topModulesByVersionCount(Map<String, Integer> source, int limit) {
        Map<String, long[]> folded = new LinkedHashMap<>();
        Map<String, Integer> remaining = new HashMap<>(source);
        for (ModuleFold fold : MODULE_FOLDS) {
            long min = Long.MAX_VALUE;
            long max = Long.MIN_VALUE;
            int members = 0;
            Iterator<Map.Entry<String, Integer>> iterator = remaining.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Integer> entry = iterator.next();
                if (fold.matches().test(entry.getKey())) {
                    long count = entry.getValue();
                    min = Math.min(min, count);
                    max = Math.max(max, count);
                    members++;
                    iterator.remove();
                }
            }
            if (members > 0) {
                folded.put(fold.displayKey(), new long[]{min, max, members});
            }
        }
        Stream<TopEntry> single = remaining.entrySet().stream()
                .map(entry -> new TopEntry(entry.getKey(), entry.getValue()));
        Stream<TopEntry> ranges = folded.entrySet().stream()
                .map(entry -> new TopEntry(entry.getKey(), entry.getValue()[1], entry.getValue()[0], (int) entry.getValue()[2]));
        return Stream.concat(single, ranges)
                .sorted((a, b) -> {
                    int cmp = Long.compare(b.count(), a.count());
                    return cmp != 0 ? cmp : a.key().compareTo(b.key());
                })
                .limit(limit)
                .toList();
    }
}
