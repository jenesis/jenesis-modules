package build.jenesis.crawler;

import module java.base;
import build.jenesis.crawler.model.CurrentEntry;
import build.jenesis.crawler.model.ModuleEntry;
import build.jenesis.crawler.model.ModuleType;
import build.jenesis.crawler.model.ScannedEntry;
import build.jenesis.crawler.model.Version;

/**
 * Reads {@code data/modules/} (versions.tsv + current.tsv) and writes a markdown
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
    private static final String CURRENT_STEM = "current";
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
            new ModuleFold("com.fasterxml.jackson.*",
                    name -> name.startsWith("com.fasterxml.jackson.")));

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
                        Transitions transitions,
                        RecentActivity recent,
                        List<MonthlyPublication> monthlyPublications,
                        NamingPatterns naming,
                        ProcessingErrors errors,
                        TopLists top) {
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
     *   <li>{@code untracked}: legacy row written before the module-info-version column existed; still to be backfilled by {@code PatchModuleVersion}.</li>
     * </ul>
     */
    public record ModuleVersionCoverage(long explicit, long mismatching, long absent, long untracked) {
    }

    public record Totals(int modules,
                         long versionRows,
                         long namedVersionRows,
                         long automaticVersionRows,
                         long namedVersionRowsWithModuleVersion,
                         int distinctModulesWithModuleVersion,
                         long scannedArtifacts,
                         long nonModuleArtifacts,
                         long distinctMavenArtifacts,
                         int distinctGroupIds,
                         Optional<Instant> latestPublishedAt) {
    }

    public record TypeBreakdown(int uniqueModules, long rows) {
    }

    public record Transitions(int autoToNamed, int namedToAuto) {
    }

    public record RecentActivity(int modules, long versions) {
    }

    /**
     * One row of the per-month publication breakdown. {@code month} is the calendar month in UTC;
     * {@code named} and {@code automatic} count {@code versions.tsv} rows of that type whose
     * {@code publishedAt} falls in the month. The renderer keeps the most recent 12 calendar
     * months (including the current one) so growth trends are visible at a glance.
     */
    public record MonthlyPublication(YearMonth month, long named, long automatic) {
    }

    public record NamingPatterns(int collidingModules,
                                 SortedMap<Integer, Integer> sharedSegmentHistogram,
                                 int modulesWithClassifier,
                                 int classifierVariants) {

        public NamingPatterns {
            sharedSegmentHistogram = Collections.unmodifiableSortedMap(new TreeMap<>(sharedSegmentHistogram));
        }
    }

    public record ProcessingErrors(long total, List<TopEntry> topMessages) {
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
        Aggregator aggregator = new Aggregator(generatedAt, topN);
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
        return aggregator.toStats(state);
    }

    private static String render(Stats stats, int topN) {
        StringBuilder builder = new StringBuilder();
        builder.append("# Module summary\n\n");
        builder.append("> ### Powered by [Jenesis](https://github.com/raphw/jenesis)\n");
        builder.append("> _A modern Java build tool: Java-native config, plugin-free, with `module-info.java` treated as a feature, not an afterthought._\n\n");
        builder.append("_Generated: ").append(HUMAN_UTC_TIMESTAMP.format(stats.generatedAt())).append("_  \n");
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

        Totals totals = stats.totals();
        builder.append("## Totals\n\n");
        builder.append("Catalogue-wide counts. \"Artifacts\" counts JARs (one row per groupId/artifactId/version/classifier coordinate); \"modules\" counts the named or automatic-module identities those JARs expose. Distinct counts deduplicate by name. \"With module-info version\" means the module declared a non-empty version in its `module-info`, whether or not it matches the Maven coordinate version. The Module-info version coverage table further splits that group into explicit (semantic match) and mismatching.\n\n");
        builder.append("| Metric | Value |\n|---|---:|\n");
        builder.append("| Total artifacts scanned | ").append(fmt(totals.scannedArtifacts())).append(" |\n");
        builder.append("| Non-module artifacts | ").append(fmt(totals.nonModuleArtifacts())).append(" |\n");
        builder.append("| Modular artifacts | ").append(fmt(totals.versionRows())).append(" |\n");
        builder.append("| Total automatic modules | ").append(fmt(totals.automaticVersionRows())).append(" |\n");
        builder.append("| Total named modules | ").append(fmt(totals.namedVersionRows())).append(" |\n");
        builder.append("| Total named modules with module-info version | ").append(fmt(totals.namedVersionRowsWithModuleVersion())).append(" |\n");
        builder.append("| Distinct Maven artifacts | ").append(fmt(totals.distinctMavenArtifacts())).append(" |\n");
        builder.append("| Distinct module names | ").append(fmt(totals.modules())).append(" |\n");
        builder.append("| Distinct automatic modules | ").append(fmt(stats.automatic().uniqueModules())).append(" |\n");
        builder.append("| Distinct named modules | ").append(fmt(stats.named().uniqueModules())).append(" |\n");
        builder.append("| Distinct named modules with module-info version | ").append(fmt(totals.distinctModulesWithModuleVersion())).append(" |\n");
        builder.append("| Distinct groupIds publishing modules | ").append(fmt(totals.distinctGroupIds())).append(" |\n");
        builder.append("| Most recent tracked publication | ")
                .append(totals.latestPublishedAt().map(HUMAN_UTC_TIMESTAMP::format).orElse("(none)"))
                .append(" |\n\n");

        builder.append("## Type breakdown\n\n");
        builder.append("Computed over the canonical (`current.tsv`) view. Unique-module counts use the **latest** version's type, so a module that started automatic and is currently named counts as named. Rows are the total per-type entries across every `current[-<classifier>].tsv`.\n\n");
        builder.append("| Type | Unique modules | Published rows |\n|---|---:|---:|\n");
        builder.append("| Named | ").append(fmt(stats.named().uniqueModules())).append(" | ").append(fmt(stats.named().rows())).append(" |\n");
        builder.append("| Automatic | ").append(fmt(stats.automatic().uniqueModules())).append(" | ").append(fmt(stats.automatic().rows())).append(" |\n\n");

        ModuleVersionCoverage coverage = stats.moduleVersionCoverage();
        builder.append("## Module-info version coverage\n\n");
        builder.append("Counted over named-module rows in `versions.tsv` (automatic modules are excluded since they have no `module-info` to declare a version). \"Explicit\" means the row's `module-info` declared a version that equals the Maven coordinate version; \"mismatching\" means a non-empty `module-info` version that differs from the Maven version; \"without\" means the JAR was scanned but `module-info` declared no version; \"untracked\" means a legacy row written before the column existed and not yet backfilled by `PatchModuleVersion`.\n\n");
        builder.append("| Category | Rows |\n|---|---:|\n");
        builder.append("| With explicit module version | ").append(fmt(coverage.explicit())).append(" |\n");
        builder.append("| With mismatching module version | ").append(fmt(coverage.mismatching())).append(" |\n");
        builder.append("| Without module version | ").append(fmt(coverage.absent())).append(" |\n");
        builder.append("| Untracked | ").append(fmt(coverage.untracked())).append(" |\n\n");

        builder.append("## Type transitions (from current.tsv history)\n\n");
        builder.append("Counted from each module's resolved view: if the latest-version row is one type and at least one older-version row is the other type, the module is counted in the appropriate direction. Cross-publisher type swings that exist in the audit log but were filtered out by owners.tsv are intentionally excluded.\n\n");
        builder.append("| Direction | Modules |\n|---|---:|\n");
        builder.append("| Automatic → Named | ").append(fmt(stats.transitions().autoToNamed())).append(" |\n");
        builder.append("| Named → Automatic | ").append(fmt(stats.transitions().namedToAuto())).append(" |\n\n");

        builder.append("## Recent activity (last 7 days)\n\n");
        builder.append("Activity in the 7-day window leading up to the generation timestamp at the top of this file. \"Modules with a publication\" counts distinct module names that received at least one new version row; \"new version rows\" is the total count of those rows.\n\n");
        builder.append("| Metric | Value |\n|---|---:|\n");
        builder.append("| Modules with a publication | ").append(fmt(stats.recent().modules())).append(" |\n");
        builder.append("| New version rows | ").append(fmt(stats.recent().versions())).append(" |\n\n");

        renderMonthlyPublications(builder, stats.monthlyPublications());

        builder.append("## Naming patterns\n\n");
        builder.append("How module names relate to their publishing groupId and to classifier-bundled JARs. \"Classifier variants\" are non-main artifacts like `-jar-with-dependencies` or `-uber` that also produce a module; \"competing groupIds\" means the same module name was published under more than one groupId across history (collision, before owners.tsv resolved it). The shared-dot-segments histogram shows how closely module names follow the convention of starting with their groupId.\n\n");
        builder.append("| Pattern | Modules |\n|---|---:|\n");
        builder.append("| Has classifier variants | ").append(fmt(stats.naming().modulesWithClassifier())).append(" |\n");
        builder.append("| Total classifier variants (across all modules) | ").append(fmt(stats.naming().classifierVariants())).append(" |\n");
        builder.append("| Multiple competing groupIds in audit history | ").append(fmt(stats.naming().collidingModules())).append(" |\n");
        SortedMap<Integer, Integer> histogram = stats.naming().sharedSegmentHistogram();
        int maxShared = histogram.isEmpty() ? 0 : histogram.lastKey();
        for (int i = 0; i <= maxShared; i++) {
            int count = histogram.getOrDefault(i, 0);
            builder.append("| Shared leading dot-segments with canonical groupId: ").append(i).append(" | ").append(fmt(count)).append(" |\n");
        }
        builder.append('\n');

        ProcessingErrors errors = stats.errors();
        builder.append("## Processing errors (from `data/scanned/`)\n\n");
        builder.append("Recorded permanent failures across every scanned coordinate. Variable bits of well-known error classes (URLs, shaded package names, classfile entry indexes, HTTP status codes, line numbers, class identifiers) are replaced with placeholders like `<URL>`, `<PACKAGE>`, `<CLASS>` so messages that differ only in those bits aggregate into one row.\n\n");
        builder.append("| Metric | Value |\n|---|---:|\n");
        builder.append("| Total failed coordinates | ").append(fmt(errors.total())).append(" |\n\n");
        if (!errors.topMessages().isEmpty()) {
            builder.append("### Top ").append(topN).append(" error messages\n\n");
            builder.append("| Error message | Count |\n|---|---:|\n");
            for (TopEntry entry : errors.topMessages()) {
                builder.append("| `").append(escapePipes(entry.key())).append("` | ").append(fmt(entry.count())).append(" |\n");
            }
            builder.append('\n');
        }

        builder.append("## Top ").append(topN).append(" modules by version count\n\n");
        builder.append("Counted from the canonical `versions.tsv` only (per-classifier counts like `-jar-with-dependencies` are excluded). Module families that would otherwise occupy many adjacent slots are folded into a single `<prefix>.*` row; the cell is rendered as `[min, max]` when the absorbed modules have different counts, and a trailing `(N modules)` notes how many were absorbed.\n\n");
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
        builder.append("Module names that have been published under the most different groupIds across history. A high count indicates name reuse: forks, rebranded artifacts, or coordinate moves that left old groupIds in the audit log even after owners.tsv picked a canonical publisher.\n\n");
        builder.append("| Module | Distinct groupIds |\n|---|---:|\n");
        for (TopEntry entry : stats.top().collisionsByDistinctGroups()) {
            builder.append("| `").append(entry.key()).append("` | ").append(fmt(entry.count())).append(" |\n");
        }
        builder.append('\n');

        builder.append("## Top ").append(topN).append(" modules updated in the last 7 days\n\n");
        builder.append("Modules whose most recent publication landed in the 7-day window, sorted newest first. Use this as a recency view; the count above (`Recent activity`) gives the totals while this table names which modules they were.\n\n");
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
    private static void renderMonthlyPublications(StringBuilder builder, List<MonthlyPublication> monthly) {
        builder.append("## Monthly publications by type (last 12 months)\n\n");
        builder.append("Counted from `versions.tsv` by the row's `publishedAt` timestamp (UTC) and type. Bars are scaled to the maximum count across either type so the two columns are directly comparable.\n\n");
        long maxCount = 0L;
        for (MonthlyPublication entry : monthly) {
            maxCount = Math.max(maxCount, Math.max(entry.named(), entry.automatic()));
        }
        builder.append("| Month | Named modules | Automatic modules |\n|---|---|---|\n");
        for (MonthlyPublication entry : monthly) {
            builder.append("| ").append(MONTH_FORMAT.format(entry.month()))
                    .append(" | ").append(monthlyCell(entry.named(), maxCount))
                    .append(" | ").append(monthlyCell(entry.automatic(), maxCount))
                    .append(" |\n");
        }
        builder.append('\n');
    }

    private static String monthlyCell(long count, long maxCount) {
        if (count == 0L || maxCount <= 0L) {
            return fmt(count);
        }
        int bars = (int) Math.round((double) count * MONTHLY_BAR_WIDTH / (double) maxCount);
        if (bars == 0) {
            bars = 1;
        }
        return "`" + "▓".repeat(bars) + "`&nbsp;" + fmt(count);
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
        private long totalNamedVersionRows;
        private long totalAutomaticVersionRows;
        private int namedUniqueModules;
        private int automaticUniqueModules;
        private long namedRows;
        private long automaticRows;
        private int autoToNamed;
        private int namedToAuto;
        private int modulesPublishedLastWeek;
        private long versionsPublishedLastWeek;
        private int collidingModules;
        private int classifierVariants;
        private final SortedMap<Integer, Integer> sharedSegmentHistogram = new TreeMap<>();
        private long latestPublishedMillis;
        private long moduleVersionExplicit;
        private long moduleVersionMismatching;
        private long moduleVersionAbsent;
        private long moduleVersionUntracked;

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
        private final Map<YearMonth, long[]> monthlyTypeCounts = new HashMap<>();

        Aggregator(Instant generatedAt, int topN) {
            this.generatedAt = Objects.requireNonNull(generatedAt, "generatedAt");
            this.recentCutoffMillis = generatedAt.minus(RECENT_WINDOW).toEpochMilli();
            if (topN < 1) {
                throw new IllegalArgumentException("topN must be >= 1, got " + topN);
            }
            this.topN = topN;
        }

        void acceptDirectory(Path modulesRoot, Path dir) throws IOException {
            List<ClassifierFile> versionFiles = listTsv(dir, VERSIONS_STEM);
            if (versionFiles.isEmpty()) {
                return;
            }
            Map<String, ClassifierFile> currentByClassifier = new HashMap<>();
            for (ClassifierFile entry : listTsv(dir, CURRENT_STEM)) {
                currentByClassifier.put(nullToEmpty(entry.classifier()), entry);
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
                ClassifierFile currentEntry = currentByClassifier.get(nullToEmpty(classifier));
                acceptModule(dir, moduleName, classifier, versionsEntry.path(), currentEntry == null ? null : currentEntry.path());
            }
            if (dirHasClassifier) {
                dirsWithClassifier.add(dir);
            }
        }

        private void acceptModule(Path dir, String moduleName, String classifier, Path versionsFile, Path currentFile) throws IOException {
            totalModules++;
            String moduleKey = displayKey(moduleName, classifier);

            List<ModuleEntry> versions = readVersionsFile(versionsFile);
            totalVersionRows += versions.size();
            // The top-modules-by-version-count table is limited to the canonical versions.tsv:
            // classifier variants like -jar-with-dependencies otherwise dominate the list with
            // bundled-dependency version counts that aren't really about the module itself.
            if (classifier == null) {
                versionsCountByModule.put(moduleName, versions.size());
            }

            Set<String> groupsHere = new HashSet<>();
            boolean recent = false;
            long recentRows = 0L;
            long moduleLatestMillis = 0L;
            for (ModuleEntry entry : versions) {
                distinctGroupIds.add(entry.groupId());
                groupsHere.add(entry.groupId());
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
                }
                if (entry.type() == ModuleType.AUTOMATIC) {
                    totalAutomaticVersionRows++;
                }
                if (entry.publishedAt() > 0L && entry.type() != null) {
                    YearMonth bucket = YearMonth.from(Instant.ofEpochMilli(entry.publishedAt()).atZone(ZoneOffset.UTC));
                    long[] counts = monthlyTypeCounts.computeIfAbsent(bucket, _ -> new long[2]);
                    if (entry.type() == ModuleType.NAMED) {
                        counts[0]++;
                    } else if (entry.type() == ModuleType.AUTOMATIC) {
                        counts[1]++;
                    }
                }
                // Automatic modules have no module-info to declare a version, so they would
                // always land in the "absent" bucket and dilute the signal. Skip them so the
                // breakdown reflects only the population where the question is meaningful.
                if (entry.type() == ModuleType.NAMED) {
                    totalNamedVersionRows++;
                    String moduleVersion = entry.moduleVersion();
                    if (moduleVersion == null) {
                        moduleVersionUntracked++;
                    } else if (moduleVersion.isEmpty()) {
                        moduleVersionAbsent++;
                    } else {
                        // A non-empty module-info version: the row counts toward the
                        // "has a module-info version" tally regardless of whether it matches
                        // the Maven coordinate. Within that, the explicit/mismatching split
                        // uses semantic Version equality so trailing-zero and qualifier-alias
                        // variants ("1.0" vs "1.0.0", "1.0-ga" vs "1.0") aggregate as matches.
                        moduleKeysWithModuleVersion.add(moduleKey);
                        if (new Version(moduleVersion).equals(entry.mavenVersion())) {
                            moduleVersionExplicit++;
                        } else {
                            moduleVersionMismatching++;
                        }
                    }
                }
            }
            if (moduleLatestMillis > 0L) {
                latestPublishedByModule.put(moduleKey, moduleLatestMillis);
            }
            distinctGroupsCountByModule.put(moduleKey, groupsHere.size());
            if (groupsHere.size() > 1) {
                collidingModules++;
            }
            for (String group : groupsHere) {
                modulesByGroup.computeIfAbsent(group, _ -> new HashSet<>()).add(moduleKey);
            }
            if (recent) {
                modulesPublishedLastWeek++;
                versionsPublishedLastWeek += recentRows;
            }

            if (currentFile != null) {
                List<CurrentEntry> current = readCurrentFile(currentFile);
                if (!current.isEmpty()) {
                    CurrentEntry latest = current.get(0);
                    if (latest.type() == ModuleType.NAMED) {
                        namedUniqueModules++;
                    } else if (latest.type() == ModuleType.AUTOMATIC) {
                        automaticUniqueModules++;
                    }
                    // Transitions are detected purely from the resolved view: if the latest
                    // version is one type and any older version (further down current.tsv,
                    // which is sorted version-descending) is the other type, that's a
                    // transition. This avoids counting cross-publisher type changes that
                    // appear in versions.tsv's collision history but were owners-filtered
                    // out of the resolved view.
                    boolean hasNamed = false;
                    boolean hasAutomatic = false;
                    for (CurrentEntry entry : current) {
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
                    String groupId = latest.groupId();
                    int sharedSegments = sharedLeadingSegments(moduleName, groupId);
                    sharedSegmentHistogram.merge(sharedSegments, 1, Integer::sum);
                    for (CurrentEntry entry : current) {
                        if (entry.type() == ModuleType.NAMED) {
                            namedRows++;
                        } else if (entry.type() == ModuleType.AUTOMATIC) {
                            automaticRows++;
                        }
                    }
                }
            }
        }

        void acceptScannedFile(Path file) throws IOException {
            // Each scanned tsv file corresponds to a single (groupId, artifactId) Maven coordinate,
            // regardless of how many versions or classifiers it contains. Counting files therefore
            // gives the number of distinct Maven artifacts the crawler has touched.
            distinctMavenArtifactTotal++;
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
                        continue;
                    }
                    processingErrorTotal++;
                    String normalized = normalizeErrorMessage(entry.errorMessage());
                    errorMessageCounts.merge(normalized, 1L, Long::sum);
                }
            }
        }

        Stats toStats(State state) {
            // Non-module artifacts = JARs that scanned successfully but contained no module
            // identity (no module-info, no Automatic-Module-Name), so they didn't land in
            // any versions.tsv. Computed as (scanned - failed) - module rows. Clamped to 0 in
            // case scanned data temporarily lags versions data mid-crawl.
            long successfullyScanned = scannedArtifactTotal - processingErrorTotal;
            long nonModuleArtifacts = Math.max(0L, successfullyScanned - totalVersionRows);
            Totals totals = new Totals(
                    totalModules,
                    totalVersionRows,
                    totalNamedVersionRows,
                    totalAutomaticVersionRows,
                    moduleVersionExplicit + moduleVersionMismatching,
                    moduleKeysWithModuleVersion.size(),
                    scannedArtifactTotal,
                    nonModuleArtifacts,
                    distinctMavenArtifactTotal,
                    distinctGroupIds.size(),
                    latestPublishedMillis > 0L ? Optional.of(Instant.ofEpochMilli(latestPublishedMillis)) : Optional.empty());
            TypeBreakdown named = new TypeBreakdown(namedUniqueModules, namedRows);
            TypeBreakdown automatic = new TypeBreakdown(automaticUniqueModules, automaticRows);
            Transitions transitions = new Transitions(autoToNamed, namedToAuto);
            RecentActivity recent = new RecentActivity(modulesPublishedLastWeek, versionsPublishedLastWeek);
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
            List<TopEntry> topErrorMessages = errorMessageCounts.entrySet().stream()
                    .sorted((a, b) -> {
                        int cmp = Long.compare(b.getValue(), a.getValue());
                        return cmp != 0 ? cmp : a.getKey().compareTo(b.getKey());
                    })
                    .limit(topN)
                    .map(entry -> new TopEntry(entry.getKey(), entry.getValue()))
                    .toList();
            ProcessingErrors errors = new ProcessingErrors(processingErrorTotal, topErrorMessages);
            ModuleVersionCoverage coverage = new ModuleVersionCoverage(
                    moduleVersionExplicit,
                    moduleVersionMismatching,
                    moduleVersionAbsent,
                    moduleVersionUntracked);
            YearMonth currentMonth = YearMonth.from(generatedAt.atZone(ZoneOffset.UTC));
            List<MonthlyPublication> monthly = new ArrayList<>(12);
            for (int i = 11; i >= 0; i--) {
                YearMonth month = currentMonth.minusMonths(i);
                long[] counts = monthlyTypeCounts.getOrDefault(month, new long[2]);
                monthly.add(new MonthlyPublication(month, counts[0], counts[1]));
            }
            return new Stats(generatedAt, state, totals, named, automatic, coverage, transitions, recent, monthly, naming, errors, top);
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

    private static List<CurrentEntry> readCurrentFile(Path file) throws IOException {
        List<CurrentEntry> entries = new ArrayList<>();
        try (Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8)) {
            lines.filter(line -> !line.isEmpty()).map(CurrentEntry::parse).forEach(entries::add);
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
