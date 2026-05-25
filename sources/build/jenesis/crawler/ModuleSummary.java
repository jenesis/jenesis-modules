package build.jenesis.crawler;

import module java.base;
import build.jenesis.crawler.model.CurrentEntry;
import build.jenesis.crawler.model.ModuleEntry;
import build.jenesis.crawler.model.ModuleType;
import build.jenesis.crawler.model.ScannedEntry;

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
    private static final DateTimeFormatter ISO_UTC_SECONDS = DateTimeFormatter
            .ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
            .withZone(ZoneOffset.UTC);

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
                        Transitions transitions,
                        RecentActivity recent,
                        NamingPatterns naming,
                        ProcessingErrors errors,
                        TopLists top) {
    }

    public record Totals(int modules,
                         long versionRows,
                         int distinctGroupIds,
                         Optional<Instant> latestPublishedAt) {
    }

    public record TypeBreakdown(int uniqueModules, long rows) {
    }

    public record Transitions(int autoToNamed, int namedToAuto) {
    }

    public record RecentActivity(int modules, long versions) {
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

    public record TopEntry(String key, long count) {
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
        builder.append("_Generated: ").append(ISO_UTC_SECONDS.format(stats.generatedAt())).append("_  \n");
        State state = stats.state();
        if (state.indexTimestamp() > 0L) {
            builder.append("_Index timestamp: ").append(ISO_UTC_SECONDS.format(Instant.ofEpochMilli(state.indexTimestamp()))).append("_  \n");
        }
        if (state.sweepStartedAt() != null) {
            builder.append("_Current chunk started: ").append(ISO_UTC_SECONDS.format(state.sweepStartedAt())).append("_  \n");
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
        builder.append("| Metric | Value |\n|---|---:|\n");
        builder.append("| Modules tracked | ").append(fmt(totals.modules())).append(" |\n");
        builder.append("| Total version records | ").append(fmt(totals.versionRows())).append(" |\n");
        builder.append("| Distinct groupIds publishing modules | ").append(fmt(totals.distinctGroupIds())).append(" |\n");
        builder.append("| Most recent publication | ")
                .append(totals.latestPublishedAt().map(ISO_UTC_SECONDS::format).orElse("(none)"))
                .append(" |\n\n");

        builder.append("## Type breakdown\n\n");
        builder.append("Computed over the canonical (`current.tsv`) view. Unique-module counts use the **latest** version's type, so a module that started automatic and is currently named counts as named. Rows are the total per-type entries across every `current[-<classifier>].tsv`.\n\n");
        builder.append("| Type | Unique modules | Published rows |\n|---|---:|---:|\n");
        builder.append("| Named | ").append(fmt(stats.named().uniqueModules())).append(" | ").append(fmt(stats.named().rows())).append(" |\n");
        builder.append("| Automatic | ").append(fmt(stats.automatic().uniqueModules())).append(" | ").append(fmt(stats.automatic().rows())).append(" |\n\n");

        builder.append("## Type transitions (from current.tsv history)\n\n");
        builder.append("Counted from each module's resolved view: if the latest-version row is one type and at least one older-version row is the other type, the module is counted in the appropriate direction. Cross-publisher type swings that exist in the audit log but were filtered out by owners.tsv are intentionally excluded.\n\n");
        builder.append("| Direction | Modules |\n|---|---:|\n");
        builder.append("| Automatic → Named | ").append(fmt(stats.transitions().autoToNamed())).append(" |\n");
        builder.append("| Named → Automatic | ").append(fmt(stats.transitions().namedToAuto())).append(" |\n\n");

        builder.append("## Recent activity (last 7 days)\n\n");
        builder.append("| Metric | Value |\n|---|---:|\n");
        builder.append("| Modules with a publication | ").append(fmt(stats.recent().modules())).append(" |\n");
        builder.append("| New version rows | ").append(fmt(stats.recent().versions())).append(" |\n\n");

        builder.append("## Naming patterns\n\n");
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
        builder.append("Recorded permanent failures across every scanned coordinate. URLs in messages are normalised to `<URL>` so 404s against different paths aggregate into a single row.\n\n");
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
        builder.append("| Module | Versions |\n|---|---:|\n");
        for (TopEntry entry : stats.top().modulesByVersionCount()) {
            builder.append("| `").append(entry.key()).append("` | ").append(fmt(entry.count())).append(" |\n");
        }
        builder.append('\n');

        builder.append("## Top ").append(topN).append(" groupIds by module count\n\n");
        builder.append("| groupId | Modules published |\n|---|---:|\n");
        for (TopEntry entry : stats.top().groupsByModuleCount()) {
            builder.append("| `").append(entry.key()).append("` | ").append(fmt(entry.count())).append(" |\n");
        }
        builder.append('\n');

        builder.append("## Top ").append(topN).append(" modules with most colliding groupIds\n\n");
        builder.append("| Module | Distinct groupIds |\n|---|---:|\n");
        for (TopEntry entry : stats.top().collisionsByDistinctGroups()) {
            builder.append("| `").append(entry.key()).append("` | ").append(fmt(entry.count())).append(" |\n");
        }
        builder.append('\n');

        builder.append("## Top ").append(topN).append(" modules updated in the last 7 days\n\n");
        if (stats.top().latestModuleUpdates().isEmpty()) {
            builder.append("_(none — no publications recorded within the last week)_\n\n");
        } else {
            builder.append("| Module | Last publication |\n|---|---|\n");
            for (TopLatestEntry entry : stats.top().latestModuleUpdates()) {
                builder.append("| `").append(entry.key()).append("` | ")
                        .append(ISO_UTC_SECONDS.format(entry.publishedAt())).append(" |\n");
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

        private final Set<Path> dirsWithClassifier = new HashSet<>();
        private final Set<String> distinctGroupIds = new HashSet<>();
        private final Map<String, Integer> versionsCountByModule = new HashMap<>();
        private final Map<String, Integer> distinctGroupsCountByModule = new HashMap<>();
        private final Map<String, Set<String>> modulesByGroup = new HashMap<>();
        private final Map<String, Long> versionsByGroup = new HashMap<>();
        private final Map<String, Long> latestPublishedByModule = new HashMap<>();
        private long processingErrorTotal;
        private final Map<String, Long> errorMessageCounts = new HashMap<>();

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
            versionsCountByModule.put(moduleKey, versions.size());

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
            Totals totals = new Totals(
                    totalModules,
                    totalVersionRows,
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
                    topByValue(versionsCountByModule, topN),
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
            return new Stats(generatedAt, state, totals, named, automatic, transitions, recent, naming, errors, top);
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
     * Returns the number of leading dot-separated segments that {@code moduleName} and
     * {@code groupId} share. Examples:
     *   {@code a.b.c}, {@code a.b.d}   →  2
     *   {@code org.slf4j}, {@code org.slf4j.api}  →  2
     *   {@code com.example}, {@code org.something}  →  0
     */
    /**
     * Normalises a recorded scanner error message so similar failures aggregate cleanly.
     * Replaces full URLs with {@code <URL>}; everything else is kept verbatim so distinct
     * error classes stay distinct. Without this, every HTTP 404 against a different URL
     * would land in its own bucket and crowd out the more interesting patterns.
     */
    static String normalizeErrorMessage(String message) {
        return message.replaceAll("https?://\\S+", "<URL>");
    }

    /** Escapes the pipe character so embedded `|` doesn't break a markdown table row. */
    private static String escapePipes(String value) {
        return value.indexOf('|') < 0 ? value : value.replace("|", "\\|");
    }

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
}
