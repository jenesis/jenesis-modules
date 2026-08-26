package build.jenesis.crawler;

import module java.base;
import build.jenesis.crawler.fetch.Fetcher;
import build.jenesis.crawler.fetch.RobotsTxt;
import build.jenesis.crawler.model.ModuleEntry;
import build.jenesis.crawler.model.ModuleType;
import build.jenesis.crawler.model.ScannedEntry;
import build.jenesis.crawler.store.ModuleStore;

/**
 * Cross-references a {@code data/top/<year>.txt} popularity list against the crawler's module
 * and scanned catalogues, writing a sibling {@code <year>.md} table for each input file.
 *
 * <p>Each input file is named for a four-digit year and lists one Maven coordinate
 * {@code <groupId>:<artifactId>} per line, ranked by popularity. The table has one row per line:
 * <ul>
 *   <li><b>Top</b> - the 1-based rank (line number) within the input file;</li>
 *   <li><b>Artifact</b> - the {@code groupId:artifactId};</li>
 *   <li><b>Module</b> - the module carried by the artifact's latest version on or before the year
 *       end (blank if that version carries none), suffixed with a single emoji for its kind:
 *       automatic, named, or named with a declared {@code module-info} version;</li>
 *   <li><b>Last publication</b> - the most recent publish date in {@code data/scanned} on or before
 *       the year end;</li>
 *   <li><b>Artifact age</b> - years (comma-decimal) from the artifact's first publication to the
 *       year end;</li>
 *   <li><b>Module age</b> - years (comma-decimal) from the first module publication to the year end;</li>
 *   <li><b>Latest artifact version</b> - the artifact version of that most recent publication on or
 *       before the year end;</li>
 *   <li><b>Latest module version</b> - the module version of the artifact's latest version (the
 *       {@code module-info} version, falling back to the Maven version when none was declared, as
 *       automatic modules never declare one);</li>
 *   <li><b>Total released artifacts (all versions)</b> - distinct artifact versions published on or
 *       before the year end;</li>
 *   <li><b>Total released modules (all versions)</b> - distinct artifact versions that carried a
 *       module on or before the year end;</li>
 *   <li><b>Artifacts released in year</b> - distinct artifact versions published during the report
 *       year;</li>
 *   <li><b>Modules released in year</b> - distinct module-carrying versions published during the
 *       report year.</li>
 * </ul>
 *
 * <p>Setting {@link #PROP_RELEASES_URI} adds five publishing columns measured against Maven
 * Central's free thresholds: the artifacts covered, the mean file count and megabytes of a
 * release, the release rate, and the thresholds exceeded. Every one of them describes the row's
 * whole groupId rather than its single artifact, because a groupId is the closest stand-in this
 * report has for the organization Central actually caps, and a group is measured over all of its
 * artifacts rather than the listed ones alone. A group is measured once however many rows share
 * it.
 *
 * <p>Those columns are the only figures the report reads from the network. It reads the
 * repository's directory listing for the window's releases, one request each; no artifact is
 * downloaded and nothing is written to {@code data/}, because a listing already carries the size
 * of every file beside it, which is what lets the figures cover the POM, signatures and checksums
 * that Maven Central counts alongside the JARs. Leave the property unset and the report is what
 * it always was, offline and without those columns.
 *
 * <p>Module facts come from {@code data/modules/<name-as-path>/versions.tsv}, the only catalogue
 * file carrying both publish timestamps and the named/automatic type. ({@code modules.tsv} is a
 * derived, named-only view without dates.) The module name is the directory path of the
 * {@code versions.tsv}, its segments joined by {@code '.'}. A coordinate that mapped to several
 * module names over time contributes a row from each; the latest publication on or before the
 * year end decides the reported name, type and version.
 */
public final class TopModules {

    public static final String PROP_DATA = "jenesis.crawler.data";
    public static final String PROP_BLEEDING = "jenesis.crawler.top.bleeding";
    public static final String PROP_RELEASES_URI = "jenesis.crawler.top.releases.uri";
    public static final String PROP_RELEASES_CONCURRENCY = "jenesis.crawler.top.releases.concurrency";
    private static final int DEFAULT_RELEASES_CONCURRENCY = 32;
    private static final String DEFAULT_DATA_DIR = "data";
    private static final String VERSIONS_FILE = ModuleStore.LEAF_FILE_BASE + ModuleStore.LEAF_FILE_EXTENSION;

    private static final DateTimeFormatter ISO_DATE = DateTimeFormatter
            .ofPattern("yyyy-MM-dd")
            .withZone(ZoneOffset.UTC);

    /** Mean Gregorian year, so a comma-decimal age reads naturally across leap years. */
    private static final double MILLIS_PER_YEAR = 365.2425d * 86_400_000d;
    private static final String NAMED_EMOJI = "🏷️"; // label tag
    private static final String AUTOMATIC_EMOJI = "⚙️";   // gear
    private static final String VERSIONED_EMOJI = "✳️"; // named module that declares a module-info version
    private static final String DORMANT_EMOJI = "⚠️"; // released within three years but not during the report year
    private static final String STALE_EMOJI = "🚩"; // popular artifact that looks deserted: no release in three years
    private static final String OVER_LIMIT_EMOJI = "🔺"; // publishes above one of Maven Central's free thresholds

    /**
     * Maven Central's free publishing thresholds, as monthly volumes: file count, release size in
     * megabytes, and release count. They are the 90th percentile of all publishers, the point
     * Sonatype describes as where the top ten percent by volume begins, and become enforceable on
     * 2026-10-01 after a soft-limit phase that started on 2026-06-16.
     *
     * <p>Two caveats travel with every figure derived from them. Sonatype evaluates the limits per
     * organization, which may hold several namespaces, while a row here is a single artifact, so a
     * row under a threshold can still belong to an organization over it, and a group's artifacts
     * are counted together by Central but separately here. And the thresholds may move during the
     * soft-limit phase, since the Usage Center, not this table, is their source of truth.
     */
    private static final double LIMIT_FILES_PER_MONTH = 1_167d;
    private static final double LIMIT_MEGABYTES_PER_MONTH = 78d;
    private static final double LIMIT_RELEASES_PER_MONTH = 7d;

    /** Both report modes measure publishing over twelve months: a calendar year, or the rolling year. */
    private static final int WINDOW_MONTHS = 12;

    /**
     * GroupId prefixes for Maven's own build tooling: Maven itself (core, plugins, shared,
     * wagon, resolver), the Plexus container, and the Sonatype / Sisu / Aether stack behind it.
     * These top popularity rankings because every Maven build resolves them, not because they
     * say anything about module adoption, so their rows are struck through in the table.
     */
    private static final List<String> MAVEN_GROUP_PREFIXES = List.of(
            "org.apache.maven",
            "org.codehaus.plexus",
            "org.sonatype",
            "org.eclipse.sisu",
            "org.eclipse.aether");

    /**
     * ArtifactId suffixes of POM-only aggregators: parent POMs, BOMs, and dependency-management
     * imports. These ship no JAR, so they can never carry a Java module; they crowd the top lists
     * purely as dependency-management plumbing (and are far more numerous in the 2019 list than in
     * later ones, which would otherwise skew the year-over-year picture). Struck through and
     * excluded from the library figures, like Maven tooling.
     */
    private static final List<String> POM_AGGREGATOR_SUFFIXES = List.of("-parent", "-bom", "-dependencies");

    /**
     * Hand-listed coordinates excluded as ranking noise: known placeholder or fake artifacts that
     * crowd the lists without being a real, modularisable library. {@code com.google.guava:listenablefuture}
     * is Guava's empty {@code 9999.0-empty-to-avoid-conflict-with-guava} stub, published only to
     * resolve a dependency-graph conflict. Struck through and excluded from the library figures.
     */
    private static final Set<String> IGNORED_COORDINATES = Set.of("com.google.guava:listenablefuture");

    private static final List<String> HEADERS = List.of(
            "Top",
            "Artifact",
            "Module",
            "Last publication",
            "Artifact age",
            "Module age",
            "Latest artifact version",
            "Latest module version",
            "Total released artifacts (all versions)",
            "Total released modules (all versions)",
            "Artifacts released in year",
            "Modules released in year");

    /**
     * The publishing columns, appended only when {@link #PROP_RELEASES_URI} supplied a repository
     * to measure against. Every one of them describes the row's whole groupId rather than its
     * single artifact, because that is the unit Maven Central caps, so rows sharing a groupId
     * carry identical values. "Group artifacts" says how many artifacts those values cover.
     */
    private static final List<String> RELEASE_HEADERS = List.of(
            "Group artifacts",
            "Files per release",
            "MB per release",
            "Releases per month",
            "Over Central limit");

    private TopModules() {
    }

    private record Artifact(String groupId, String artifactId) {
        @Override
        public String toString() {
            return groupId + ':' + artifactId;
        }
    }

    private record Hit(String moduleName, ModuleEntry entry) {
    }

    private record ScanStats(long firstPublished, long lastPublished, String latestVersion,
                             int totalVersions, Set<String> versionsInYear) {
        static final ScanStats NONE = new ScanStats(0L, 0L, "", 0, Set.of());
    }

    /** The file count and total size of one release directory, read from its listing. */
    public record Listing(int files, long bytes) {
    }

    /**
     * What one groupId published during the window, which is the unit Maven Central caps: the
     * artifacts it covers, its releases, and the files and bytes they weigh in total.
     *
     * <p>A release is a distinct version across the group's artifacts, because a multi-module
     * project publishes one version over many artifacts in a single deployment. The monthly
     * volumes divide the window's totals by its twelve months, which matches how Sonatype
     * evaluates the limits: a rolling average of monthly volume, not a calendar-month bucket.
     */
    private record GroupPublishing(boolean present, int artifacts, int releases, long files, long bytes) {

        static final GroupPublishing NONE = new GroupPublishing(false, 0, 0, 0L, 0L);

        double filesPerRelease() {
            return releases == 0 ? 0d : (double) files / releases;
        }

        double megabytesPerRelease() {
            return releases == 0 ? 0d : bytes / 1_000_000d / releases;
        }

        double releasesPerMonth() {
            return (double) releases / WINDOW_MONTHS;
        }

        boolean overFiles() {
            return present && (double) files / WINDOW_MONTHS > LIMIT_FILES_PER_MONTH;
        }

        boolean overSize() {
            return present && bytes / 1_000_000d / WINDOW_MONTHS > LIMIT_MEGABYTES_PER_MONTH;
        }

        boolean overReleases() {
            return present && releasesPerMonth() > LIMIT_RELEASES_PER_MONTH;
        }

        boolean overAny() {
            return overFiles() || overSize() || overReleases();
        }
    }

    /** A rendered detail row plus the classification the summary table aggregates over. */
    private record Row(String[] cells, String[] releaseCells, GroupPublishing publishing,
                       boolean modular, ModuleType type, boolean declaresModuleVersion,
                       boolean mavenRelated, boolean pomAggregator, boolean ignored, boolean maintained, String groupId) {

        /** Excluded from the library figures: structurally cannot reflect module adoption. */
        boolean excluded() {
            return mavenRelated || pomAggregator || ignored;
        }
    }

    public static void main(String[] arguments) throws IOException {
        List<Path> topFiles = new ArrayList<>();
        for (String argument : arguments) {
            if (argument.equals("--help") || argument.equals("-h")) {
                printUsage();
                return;
            }
            topFiles.add(Path.of(argument));
        }
        if (topFiles.isEmpty()) {
            printUsage();
            throw new IllegalArgumentException("No input files specified");
        }

        String configuredDataDir = System.getProperty(PROP_DATA);
        Path dataDir = configuredDataDir == null || configuredDataDir.isBlank()
                ? Path.of(DEFAULT_DATA_DIR)
                : Path.of(configuredDataDir.trim());
        Path modulesRoot = dataDir.resolve("modules");
        Path scannedRoot = dataDir.resolve("scanned");
        if (!Files.isDirectory(modulesRoot)) {
            throw new IOException("No modules directory at " + modulesRoot);
        }
        if (!Files.isDirectory(scannedRoot)) {
            throw new IOException("No scanned directory at " + scannedRoot);
        }

        Map<Path, Integer> years = new LinkedHashMap<>();
        Map<Path, List<Artifact>> targetsByFile = new LinkedHashMap<>();
        Set<Artifact> allTargets = new HashSet<>();
        for (Path topFile : topFiles) {
            if (!Files.isRegularFile(topFile)) {
                throw new IOException("No such file: " + topFile);
            }
            String stem = stem(topFile);
            if (stem.length() != 4 || !stem.chars().allMatch(Character::isDigit)) {
                throw new IllegalArgumentException("Input file name must be a four-digit year: " + topFile.getFileName());
            }
            years.put(topFile, Integer.parseInt(stem));
            List<Artifact> targets = readTargets(topFile);
            targetsByFile.put(topFile, targets);
            allTargets.addAll(targets);
        }

        Map<Artifact, List<Hit>> index = indexModules(modulesRoot, allTargets);
        URI releasesUri = property(PROP_RELEASES_URI)
                .map(value -> URI.create(value.endsWith("/") ? value : value + "/"))
                .orElse(null);
        int releasesConcurrency = property(PROP_RELEASES_CONCURRENCY)
                .map(Integer::parseInt)
                .orElse(DEFAULT_RELEASES_CONCURRENCY);
        if (releasesConcurrency < 1) {
            throw new IllegalArgumentException("Concurrency must be >= 1, got: " + releasesConcurrency);
        }

        if (booleanProperty(PROP_BLEEDING)) {
            // Bleeding edge: take the most recent list we have, but don't crop the data to that
            // year - assess those artifacts against the current state (cutoff = the index
            // timestamp), so the table reflects their latest versions and recent activity rather
            // than a frozen year end.
            Path latestFile = topFiles.stream().max(Comparator.comparingInt(years::get)).orElseThrow();
            int listYear = years.get(latestFile);
            // Anchor every window to the crawler's index timestamp (the date of the last index),
            // not the wall clock, so the report depends only on the data it was built from and
            // re-renders byte-for-byte until the index moves. Fall back to now only when there is
            // no recorded state (e.g. tests with no state.properties).
            State state = State.load(dataDir.resolve("state.properties"));
            Instant now = state.indexTimestamp() > 0L
                    ? Instant.ofEpochMilli(state.indexTimestamp())
                    : Instant.now();
            int windowYear = now.atZone(ZoneOffset.UTC).getYear();
            // Rolling windows, not calendar boundaries: the index year may be only weeks old, which
            // would wrongly mark almost everything stale. "Maintained" = released in the last 12
            // months; deserted = no release in the last 36 months, both relative to the anchor.
            long yearStart = now.atZone(ZoneOffset.UTC).minusMonths(12).toInstant().toEpochMilli();
            long threeYearStart = now.atZone(ZoneOffset.UTC).minusMonths(36).toInstant().toEpochMilli();
            long cutoff = now.toEpochMilli();
            String asOf = ISO_DATE.format(now);
            List<Row> rows = buildRows(targetsByFile.get(latestFile), index, yearStart, threeYearStart, cutoff, scannedRoot, releasesUri, releasesConcurrency);
            Path output = latestFile.resolveSibling("BLEEDING.md");
            Files.writeString(output, render(windowYear, "bleeding edge", asOf, true, listYear, rows), StandardCharsets.UTF_8);
            long withModule = rows.stream().filter(Row::modular).count();
            System.err.println("[top-modules] " + output + " (bleeding edge from " + listYear + " list, "
                    + rows.size() + " artifacts, " + withModule + " modular as of " + asOf + ")");
            return;
        }

        for (Path topFile : topFiles) {
            int year = years.get(topFile);
            long yearStart = LocalDate.of(year, 1, 1).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
            long threeYearStart = LocalDate.of(year - 2, 1, 1).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
            long cutoff = LocalDate.of(year + 1, 1, 1).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
            List<Row> rows = buildRows(targetsByFile.get(topFile), index, yearStart, threeYearStart, cutoff, scannedRoot, releasesUri, releasesConcurrency);
            Path output = topFile.resolveSibling(stem(topFile) + ".md");
            Files.writeString(output, render(year, Integer.toString(year), year + "-12-31", false, year, rows), StandardCharsets.UTF_8);
            long withModule = rows.stream().filter(Row::modular).count();
            System.err.println("[top-modules] " + output + " (" + rows.size() + " artifacts, "
                    + withModule + " with a module by " + year + ")");
        }
    }

    private static List<Row> buildRows(List<Artifact> targets, Map<Artifact, List<Hit>> index,
                                       long yearStart, long threeYearStart, long cutoff, Path scannedRoot,
                                       URI releasesUri, int releasesConcurrency) throws IOException {
        // Read every coordinate's scan history once, so the release listings can be fetched as a
        // single batch before any row is rendered rather than one artifact at a time.
        Map<Artifact, ScanStats> stats = new LinkedHashMap<>();
        for (Artifact target : targets) {
            stats.put(target, scanStats(scannedRoot, target, yearStart, cutoff));
        }
        Map<String, GroupPublishing> publishing = releasesUri == null
                ? Map.of()
                : groupPublishing(releasesUri, targets, scannedRoot, yearStart, cutoff, releasesConcurrency);
        List<Row> rows = new ArrayList<>(targets.size());
        for (int rank = 0; rank < targets.size(); rank++) {
            Artifact target = targets.get(rank);
            rows.add(buildRow(rank + 1, target, index.getOrDefault(target, List.of()),
                    yearStart, threeYearStart, cutoff, stats.get(target),
                    publishing.getOrDefault(target.groupId(), GroupPublishing.NONE)));
        }
        return rows;
    }

    private static boolean booleanProperty(String name) {
        String value = System.getProperty(name);
        if (value == null || value.isBlank()) {
            return false;
        }
        return switch (value.trim().toLowerCase(Locale.ROOT)) {
            case "true", "1", "yes" -> true;
            case "false", "0", "no" -> false;
            default -> throw new IllegalArgumentException("Expected true/false for " + name + ", got: " + value);
        };
    }

    private static List<Artifact> readTargets(Path topFile) throws IOException {
        List<Artifact> targets = new ArrayList<>();
        Set<Artifact> seen = new HashSet<>();
        for (String raw : Files.readAllLines(topFile, StandardCharsets.UTF_8)) {
            String line = raw.strip();
            int colon = line.indexOf(':');
            if (colon <= 0 || colon == line.length() - 1) {
                continue;
            }
            Artifact artifact = new Artifact(line.substring(0, colon).strip(), line.substring(colon + 1).strip());
            if (artifact.groupId().isEmpty() || artifact.artifactId().isEmpty()) {
                continue;
            }
            if (seen.add(artifact)) {
                targets.add(artifact);
            }
        }
        return targets;
    }

    /**
     * Single walk over {@code data/modules} that collects, for every target coordinate, the
     * {@code versions.tsv} rows that published it (each tagged with the module name read from the
     * file's directory path). Non-target lines are skipped after a cheap field extraction, so the
     * full {@link ModuleEntry#parse} only runs for rows that matter.
     */
    private static Map<Artifact, List<Hit>> indexModules(Path modulesRoot, Set<Artifact> targets) throws IOException {
        Map<Artifact, List<Hit>> index = new HashMap<>();
        if (targets.isEmpty()) {
            return index;
        }
        try (Stream<Path> stream = Files.walk(modulesRoot)) {
            List<Path> versionFiles = stream
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().equals(VERSIONS_FILE))
                    .toList();
            for (Path file : versionFiles) {
                String moduleName = dottedName(modulesRoot.relativize(file.getParent()));
                try (Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8)) {
                    for (String line : (Iterable<String>) lines::iterator) {
                        if (line.isEmpty()) {
                            continue;
                        }
                        Artifact artifact = coordinateOf(line);
                        if (artifact == null || !targets.contains(artifact)) {
                            continue;
                        }
                        index.computeIfAbsent(artifact, _ -> new ArrayList<>())
                                .add(new Hit(moduleName, ModuleEntry.parse(line)));
                    }
                }
            }
        }
        return index;
    }

    /** Extracts the (groupId, artifactId) of a versions.tsv line without a full parse, or null. */
    private static Artifact coordinateOf(String line) {
        int first = line.indexOf('\t');
        if (first < 0) {
            return null;
        }
        int second = line.indexOf('\t', first + 1);
        if (second < 0) {
            return null;
        }
        int third = line.indexOf('\t', second + 1);
        if (third < 0) {
            return null;
        }
        int fourth = line.indexOf('\t', third + 1);
        if (fourth < 0) {
            return null;
        }
        return new Artifact(line.substring(second + 1, third), line.substring(third + 1, fourth));
    }

    private static Row buildRow(int rank, Artifact target, List<Hit> hits, long yearStart, long threeYearStart,
                                long cutoff, ScanStats scanned, GroupPublishing publishing) {
        List<Hit> qualifying = hits.stream().filter(hit -> hit.entry().publishedAt() < cutoff).toList();

        // The artifact's last version on or before the year end: from the scan log, falling back to
        // the newest module publication when there are no scanned rows. Modularity is decided by
        // whether that exact version carried a module, so an artifact that later dropped (or only
        // later added) a module is judged by its latest release, not by its whole history.
        String lastVersion = scanned.latestVersion();
        if (lastVersion.isEmpty() && !qualifying.isEmpty()) {
            lastVersion = qualifying.stream()
                    .max(Comparator.comparingLong((Hit hit) -> hit.entry().publishedAt()))
                    .map(hit -> hit.entry().mavenVersion().raw())
                    .orElse("");
        }
        String lastVersionKey = lastVersion;
        Optional<Hit> lastModule = lastVersionKey.isEmpty() ? Optional.empty() : qualifying.stream()
                .filter(hit -> hit.entry().mavenVersion().raw().equals(lastVersionKey))
                .max(Comparator.comparingLong((Hit hit) -> hit.entry().publishedAt())
                        .thenComparing(Hit::moduleName));

        String module = "";
        String moduleVersion = "";
        String moduleAge = "";
        boolean modular = lastModule.isPresent();
        ModuleType type = null;
        boolean declaresModuleVersion = false;
        if (modular) {
            Hit latest = lastModule.get();
            long first = qualifying.stream().mapToLong(hit -> hit.entry().publishedAt()).min().orElseThrow();
            type = latest.entry().type();
            declaresModuleVersion = !latest.entry().moduleVersion().isEmpty();
            String typeEmoji = type == ModuleType.AUTOMATIC ? AUTOMATIC_EMOJI
                    : declaresModuleVersion ? VERSIONED_EMOJI : NAMED_EMOJI;
            module = latest.moduleName() + ' ' + typeEmoji;
            moduleVersion = declaresModuleVersion
                    ? latest.entry().moduleVersion()
                    : latest.entry().mavenVersion().raw();
            moduleAge = ageYears(cutoff - first);
        }
        long totalModules = qualifying.stream().map(hit -> hit.entry().mavenVersion().raw()).distinct().count();
        long modulesInYear = qualifying.stream()
                .filter(hit -> hit.entry().publishedAt() >= yearStart)
                .map(hit -> hit.entry().mavenVersion().raw()).distinct().count();
        String lastPublication = scanned.lastPublished() > 0L
                ? ISO_DATE.format(Instant.ofEpochMilli(scanned.lastPublished()))
                : "";
        String artifactAge = scanned.firstPublished() > 0L ? ageYears(cutoff - scanned.firstPublished()) : "";
        long lastPublished = scanned.lastPublished();
        String activity;
        if (lastPublished <= 0L || lastPublished >= yearStart) {
            activity = "";
        } else if (lastPublished < threeYearStart) {
            activity = " " + STALE_EMOJI;
        } else {
            activity = " " + DORMANT_EMOJI;
        }
        String artifactCell = target + activity;
        String[] cells = {
                Integer.toString(rank),
                artifactCell,
                module,
                lastPublication,
                artifactAge,
                moduleAge,
                scanned.latestVersion(),
                moduleVersion,
                Integer.toString(scanned.totalVersions()),
                Long.toString(totalModules),
                Integer.toString(scanned.versionsInYear().size()),
                Long.toString(modulesInYear),
        };
        String[] releaseCells = publishing.present()
                ? new String[] {
                        Integer.toString(publishing.artifacts()),
                        decimal(publishing.filesPerRelease()) + flag(publishing.overFiles()),
                        decimal(publishing.megabytesPerRelease()) + flag(publishing.overSize()),
                        decimal(publishing.releasesPerMonth()) + flag(publishing.overReleases()),
                        overLimitCell(publishing),
                }
                : new String[] {"", "", "", "", ""};
        boolean maintained = lastPublished >= yearStart;
        boolean mavenRelated = isMavenRelated(target);
        boolean pomAggregator = isPomAggregator(target);
        boolean ignored = isIgnored(target);
        if (mavenRelated || pomAggregator || ignored) {
            for (int index = 0; index < cells.length; index++) {
                cells[index] = "~~" + (cells[index].isEmpty() ? "-" : cells[index]) + "~~";
            }
            for (int index = 0; index < releaseCells.length; index++) {
                releaseCells[index] = "~~" + (releaseCells[index].isEmpty() ? "-" : releaseCells[index]) + "~~";
            }
        }
        return new Row(cells, releaseCells, publishing, modular, type, declaresModuleVersion,
                mavenRelated, pomAggregator, ignored, maintained, target.groupId());
    }

    /** Names the thresholds a monthly volume exceeds, or "-" when it stays under all three. */
    private static String overLimitCell(GroupPublishing metrics) {
        if (!metrics.overAny()) {
            return "-";
        }
        StringJoiner joiner = new StringJoiner(", ");
        if (metrics.overFiles()) {
            joiner.add("files");
        }
        if (metrics.overSize()) {
            joiner.add("size");
        }
        if (metrics.overReleases()) {
            joiner.add("releases");
        }
        return OVER_LIMIT_EMOJI + " " + joiner;
    }

    private static String flag(boolean over) {
        return over ? " " + OVER_LIMIT_EMOJI : "";
    }

    private static String count(double value) {
        return String.format(Locale.ROOT, "%,.0f", value);
    }

    private static String decimal(double value) {
        return String.format(Locale.GERMANY, "%.1f", value);
    }

    /** True for Maven's own build tooling, whose rows are struck through as ranking noise. */
    private static boolean isMavenRelated(Artifact artifact) {
        String group = artifact.groupId();
        for (String prefix : MAVEN_GROUP_PREFIXES) {
            if (group.equals(prefix) || group.startsWith(prefix + '.')) {
                return true;
            }
        }
        return false;
    }

    /** True for POM-only aggregators (parents, BOMs, dependency imports), struck as ranking noise. */
    private static boolean isPomAggregator(Artifact artifact) {
        String artifactId = artifact.artifactId();
        for (String suffix : POM_AGGREGATOR_SUFFIXES) {
            if (artifactId.endsWith(suffix)) {
                return true;
            }
        }
        return false;
    }

    /** True for hand-listed placeholder/fake coordinates, struck as ranking noise. */
    private static boolean isIgnored(Artifact artifact) {
        return IGNORED_COORDINATES.contains(artifact.toString());
    }

    /** Whole-and-fractional years for a duration in millis, comma-decimal (e.g. "14,3"). */
    private static String ageYears(long millis) {
        return String.format(Locale.GERMANY, "%.1f", millis / MILLIS_PER_YEAR);
    }

    /**
     * Publish-history facts for a coordinate, read from its scanned file and restricted to rows
     * published before {@code cutoff} (i.e. on or before the report year end), across every such
     * row that carries a timestamp regardless of scan outcome: earliest and latest timestamp, the
     * version of the latest, the count of distinct versions released up to the cutoff, and the
     * count released during the report year (on or after {@code yearStart}). Distinct-version
     * counts collapse a version's per-classifier rows into one. Returns {@link ScanStats#NONE}
     * when the file is missing or has no qualifying row.
     */
    private static ScanStats scanStats(Path scannedRoot, Artifact artifact, long yearStart, long cutoff) throws IOException {
        Path path = scannedRoot;
        for (String segment : artifact.groupId().split("\\.", -1)) {
            path = path.resolve(segment);
        }
        path = path.resolve(artifact.artifactId() + ".tsv");
        if (!Files.isRegularFile(path)) {
            return ScanStats.NONE;
        }
        long firstPublished = 0L;
        long lastPublished = 0L;
        String latestVersion = "";
        Set<String> versions = new HashSet<>();
        Set<String> versionsInYear = new HashSet<>();
        try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
            for (String line : (Iterable<String>) lines::iterator) {
                if (line.isEmpty()) {
                    continue;
                }
                ScannedEntry entry = ScannedEntry.parse(line);
                long published = entry.publishedAt();
                if (published <= 0L || published >= cutoff) {
                    continue;
                }
                versions.add(entry.version());
                if (published >= yearStart) {
                    versionsInYear.add(entry.version());
                }
                if (firstPublished == 0L || published < firstPublished) {
                    firstPublished = published;
                }
                if (published > lastPublished) {
                    lastPublished = published;
                    latestVersion = entry.version();
                }
            }
        }
        return lastPublished > 0L
                ? new ScanStats(firstPublished, lastPublished, latestVersion, versions.size(), versionsInYear)
                : ScanStats.NONE;
    }

    private static String dottedName(Path relative) {
        StringJoiner joiner = new StringJoiner(".");
        for (Path segment : relative) {
            joiner.add(segment.toString());
        }
        return joiner.toString();
    }

    private static String stem(Path path) {
        String name = path.getFileName().toString();
        int dot = name.lastIndexOf('.');
        return dot < 0 ? name : name.substring(0, dot);
    }

    private static String render(int year, String titleLabel, String asOf, boolean bleeding, int listYear, List<Row> rows) {
        int total = rows.size();
        boolean hasReleases = rows.stream().anyMatch(row -> row.publishing().present());
        long mavenRows = rows.stream().filter(Row::mavenRelated).count();
        long pomRows = rows.stream().filter(row -> row.pomAggregator() && !row.mavenRelated()).count();
        long ignoredRows = rows.stream().filter(row -> row.ignored() && !row.mavenRelated() && !row.pomAggregator()).count();
        long excluded = rows.stream().filter(Row::excluded).count();
        int totalLibraries = total - (int) excluded;
        int totalMaintained = (int) rows.stream().filter(row -> !row.excluded() && row.maintained()).count();
        GroupStats allGroups = groupStats(rows);
        GroupStats libGroups = groupStats(rows.stream().filter(row -> !row.excluded()).toList());
        GroupStats maintainedGroups = groupStats(rows.stream().filter(row -> !row.excluded() && row.maintained()).toList());

        StringBuilder builder = new StringBuilder();
        builder.append("# Maven Central most downloaded artifacts vs. modules (").append(titleLabel).append(")\n\n");
        if (bleeding) {
            builder.append("_Bleeding edge: the ").append(listYear)
                    .append(" top-artifact list assessed against current data, as of ").append(asOf)
                    .append("; nothing is cropped to a year end, and the ").append(DORMANT_EMOJI).append(" / ")
                    .append(STALE_EMOJI).append(" flags use rolling 12- and 36-month windows._\n\n");
        }

        builder.append("**By artifact**\n\n");
        builder.append("| Category | All listed | Libraries | Maintained |\n");
        builder.append("|---|---|---|---|\n");
        appendMetric(builder, "Total artifacts", rows, _ -> true, total, totalLibraries, totalMaintained);
        appendMetric(builder, "Modular artifacts", rows, Row::modular, total, totalLibraries, totalMaintained);
        appendMetric(builder, "Automatic modules", rows, row -> row.type() == ModuleType.AUTOMATIC, total, totalLibraries, totalMaintained);
        appendMetric(builder, "Named modules", rows, row -> row.type() == ModuleType.NAMED, total, totalLibraries, totalMaintained);
        appendMetric(builder, "Named modules with declared version", rows, Row::declaresModuleVersion, total, totalLibraries, totalMaintained);
        appendMetric(builder, "Non-modular artifacts", rows, row -> !row.modular(), total, totalLibraries, totalMaintained);
        if (hasReleases) {
            appendMetric(builder, "Artifacts whose group is over the file limit", rows,
                    row -> row.publishing().overFiles(), total, totalLibraries, totalMaintained);
            appendMetric(builder, "Artifacts whose group is over the size limit", rows,
                    row -> row.publishing().overSize(), total, totalLibraries, totalMaintained);
            appendMetric(builder, "Artifacts whose group is over the release limit", rows,
                    row -> row.publishing().overReleases(), total, totalLibraries, totalMaintained);
            appendMetric(builder, "Artifacts whose group is over any limit", rows,
                    row -> row.publishing().overAny(), total, totalLibraries, totalMaintained);
        }
        builder.append('\n');

        builder.append("**By groupId**\n\n");
        builder.append("| Category | All listed | Libraries | Maintained |\n");
        builder.append("|---|---|---|---|\n");
        long allTotal = allGroups.total();
        long libTotal = libGroups.total();
        long maintTotal = maintainedGroups.total();
        appendGroupMetric(builder, "Total groups", allGroups.total(), allTotal, libGroups.total(), libTotal, maintainedGroups.total(), maintTotal);
        appendGroupMetric(builder, "Groups without modules", allGroups.without(), allTotal, libGroups.without(), libTotal, maintainedGroups.without(), maintTotal);
        appendGroupMetric(builder, "Partial modularized groups", allGroups.withModules(), allTotal, libGroups.withModules(), libTotal, maintainedGroups.withModules(), maintTotal);
        appendGroupMetric(builder, "Groups with full modularization", allGroups.fully(), allTotal, libGroups.fully(), libTotal, maintainedGroups.fully(), maintTotal);
        appendGroupMetric(builder, "Groups with named modules only", allGroups.namedOnly(), allTotal, libGroups.namedOnly(), libTotal, maintainedGroups.namedOnly(), maintTotal);
        appendGroupMetric(builder, "Groups with automatic modules only", allGroups.automaticOnly(), allTotal, libGroups.automaticOnly(), libTotal, maintainedGroups.automaticOnly(), maintTotal);
        appendGroupMetric(builder, "Groups with modules and version info only", allGroups.versionOnly(), allTotal, libGroups.versionOnly(), libTotal, maintainedGroups.versionOnly(), maintTotal);
        if (hasReleases) {
            GroupLimits allLimits = groupLimits(rows);
            GroupLimits libLimits = groupLimits(rows.stream().filter(row -> !row.excluded()).toList());
            GroupLimits maintainedLimits = groupLimits(rows.stream().filter(row -> !row.excluded() && row.maintained()).toList());
            appendGroupMetric(builder, "Groups over the file limit", allLimits.overFiles(), allTotal, libLimits.overFiles(), libTotal, maintainedLimits.overFiles(), maintTotal);
            appendGroupMetric(builder, "Groups over the size limit", allLimits.overSize(), allTotal, libLimits.overSize(), libTotal, maintainedLimits.overSize(), maintTotal);
            appendGroupMetric(builder, "Groups over the release limit", allLimits.overReleases(), allTotal, libLimits.overReleases(), libTotal, maintainedLimits.overReleases(), maintTotal);
            appendGroupMetric(builder, "Groups over any limit", allLimits.overAny(), allTotal, libLimits.overAny(), libTotal, maintainedLimits.overAny(), maintTotal);
        }
        builder.append('\n');

        builder.append("Counts are absolute with the share in parentheses. \"All listed\" covers all ")
                .append(total).append(" artifacts; \"Libraries\" excludes the ").append(excluded)
                .append(" struck rows that cannot reflect module adoption (").append(mavenRows)
                .append(" Maven build-tooling, ").append(pomRows)
                .append(" POM-only parents/BOMs/dependencies, ").append(plural(ignoredRows, "placeholder artifact"))
                .append(") and is over the remaining ")
                .append(totalLibraries).append(". \"Maintained\" further drops library artifacts with no release during ")
                .append(bleeding ? "the last 12 months" : Integer.toString(year)).append(" (the ").append(DORMANT_EMOJI).append(" / ").append(STALE_EMOJI)
                .append(" flagged ones), leaving ").append(totalMaintained).append(". Everything is as of ")
                .append(asOf).append(". Artifact shares are of ")
                .append("total artifacts; group shares are of total groups. \"Partial modularized groups\" have at ")
                .append("least one artifact whose latest version carries a module; \"full modularization\" is the ")
                .append("subset where every artifact does; the named/automatic/version rows classify groups whose ")
                .append("modules are exclusively of that kind.\n\n");

        builder.append("Every figure is as of ").append(asOf)
                .append(", and each artifact is judged by its latest version on or before that date: the ")
                .append("module columns describe that version's module and are blank when the latest version ")
                .append("carries none, even if an earlier version did. Its name, type (")
                .append(AUTOMATIC_EMOJI).append(" automatic, ").append(NAMED_EMOJI).append(" named, ")
                .append(VERSIONED_EMOJI).append(" named with a module-info version) and version come from that ")
                .append("latest version; the last-publication date and latest artifact version are from the latest ")
                .append("scanned publication on or before it. A ").append(DORMANT_EMOJI)
                .append(" marks an artifact with no release during ").append(bleeding ? "the last 12 months" : "the year")
                .append(", a ").append(STALE_EMOJI)
                .append(" one that looks deserted (no release in the last three years). ")
                .append("Ages are in years (comma-decimal) measured to that date: ")
                .append("artifact age from the artifact's first publication, module age from its first module ")
                .append("publication. The trailing counts are distinct versions: the \"released\" totals cover ")
                .append("everything up to the year end, \"in year\" only the report year, and the module counts ")
                .append("only versions that carried a Java module. Three kinds of row are shown struck through and ")
                .append("excluded from the Libraries column, as they crowd these rankings for reasons unrelated to ")
                .append("module adoption: Maven's own build tooling (").append(plural(mavenRows, "row"))
                .append(": Maven, Plexus, Sonatype/Sisu/Aether), POM-only aggregators (").append(plural(pomRows, "row"))
                .append(": parents, BOMs and dependency imports, which ship no JAR), and hand-listed placeholder ")
                .append("artifacts (").append(plural(ignoredRows, "row")).append(").\n\n");

        if (hasReleases) {
            GroupLimits limits = groupLimits(rows);
            builder.append("The last five columns measure publishing against Maven Central's free thresholds, ")
                    .append("which since 2026-06-16 are notified as soft limits and become enforceable on ")
                    .append("2026-10-01: ").append(count(LIMIT_FILES_PER_MONTH)).append(" files, ")
                    .append(count(LIMIT_MEGABYTES_PER_MONTH)).append(" MB and ")
                    .append(count(LIMIT_RELEASES_PER_MONTH)).append(" releases per month, the 90th percentile of ")
                    .append("all publishers. They describe the row's whole **groupId**, not its single artifact, ")
                    .append("because that is the unit Central caps, so every row sharing a groupId carries the ")
                    .append("same figures and \"Group artifacts\" says how many artifacts they cover. Here ")
                    .append(limits.measured()).append(" groups published inside the window, across ")
                    .append(limits.artifacts()).append(" artifacts, which is ")
                    .append(String.format(Locale.GERMANY, "%.1f", limits.measured() == 0
                            ? 0d
                            : (double) limits.artifacts() / limits.measured()))
                    .append(" artifacts per group on average, against the ").append(total)
                    .append(" the list itself names.\n\n")
                    .append("A release is a distinct version across the group's artifacts, since a multi-module ")
                    .append("project publishes one version over many artifacts in a single deployment. \"Files ")
                    .append("per release\" and \"MB per release\" divide the group's window totals by those ")
                    .append("releases, counting every file the repository serves under a version - the artifacts, ")
                    .append("the POM, and the signature and checksum sidecars beside them - because Central counts ")
                    .append("the same set. A ").append(OVER_LIMIT_EMOJI)
                    .append(" marks a figure whose monthly volume is above its threshold, and \"Over Central ")
                    .append("limit\" names them. Central averages monthly volume over a rolling three months ")
                    .append("rather than bucketing it by calendar month, so these figures divide the window's ")
                    .append("twelve months evenly; a group that published in one burst can therefore breach at ")
                    .append("Central while its yearly mean here stays under.\n\n")
                    .append("Read every one of these as a **best case**. Central applies a limit to an ")
                    .append("organization, and an organization may hold several namespaces: ")
                    .append("`org.springframework`, `org.springframework.boot` and `org.springframework.security` ")
                    .append("are separate rows here and may well be one account there. A group counted as under a ")
                    .append("threshold can therefore still belong to an organization over it, never the reverse. ")
                    .append("The thresholds themselves may also move during the soft-limit phase, since the Usage ")
                    .append("Center, not this table, is their source of truth.\n\n");
        }

        List<String> headers = new ArrayList<>(HEADERS);
        if (bleeding) {
            headers.set(headers.indexOf("Artifacts released in year"), "Artifacts released in last 12 months");
            headers.set(headers.indexOf("Modules released in year"), "Modules released in last 12 months");
        }
        if (hasReleases) {
            headers.addAll(RELEASE_HEADERS);
        }
        builder.append("| ").append(String.join(" | ", headers)).append(" |\n");
        builder.append("|").append("---|".repeat(headers.size())).append('\n');
        for (Row row : rows) {
            builder.append('|');
            for (String cell : row.cells()) {
                builder.append(' ').append(cell.replace("|", "\\|")).append(" |");
            }
            if (hasReleases) {
                for (String cell : row.releaseCells()) {
                    builder.append(' ').append(cell.replace("|", "\\|")).append(" |");
                }
            }
            builder.append('\n');
        }
        return builder.toString();
    }

    private static void appendMetric(StringBuilder builder, String label, List<Row> rows,
                                     Predicate<Row> predicate, int total, int totalLibraries, int totalMaintained) {
        long all = rows.stream().filter(predicate).count();
        long libraries = rows.stream().filter(row -> !row.excluded()).filter(predicate).count();
        long maintained = rows.stream().filter(row -> !row.excluded() && row.maintained()).filter(predicate).count();
        builder.append("| ").append(label)
                .append(" | ").append(metricCell(all, total))
                .append(" | ").append(metricCell(libraries, totalLibraries))
                .append(" | ").append(metricCell(maintained, totalMaintained))
                .append(" |\n");
    }

    /** Per-groupId classification of modularization state over a population of rows. */
    private record GroupStats(long total, long fully, long namedOnly, long automaticOnly,
                              long versionOnly, long partial, long without) {

        /** Groups with at least one artifact whose latest version carries a module of any type. */
        long withModules() {
            return fully + partial;
        }
    }

    /** How many groupIds publish above each Maven Central threshold, counting each group once. */
    private record GroupLimits(long measured, long artifacts, long overFiles, long overSize,
                               long overReleases, long overAny) {
    }

    private static GroupLimits groupLimits(List<Row> rows) {
        Map<String, GroupPublishing> byGroup = new LinkedHashMap<>();
        for (Row row : rows) {
            if (row.publishing().present()) {
                byGroup.putIfAbsent(row.groupId(), row.publishing());
            }
        }
        long artifacts = 0;
        long overFiles = 0;
        long overSize = 0;
        long overReleases = 0;
        long overAny = 0;
        for (GroupPublishing publishing : byGroup.values()) {
            artifacts += publishing.artifacts();
            if (publishing.overFiles()) {
                overFiles++;
            }
            if (publishing.overSize()) {
                overSize++;
            }
            if (publishing.overReleases()) {
                overReleases++;
            }
            if (publishing.overAny()) {
                overAny++;
            }
        }
        return new GroupLimits(byGroup.size(), artifacts, overFiles, overSize, overReleases, overAny);
    }

    private static GroupStats groupStats(List<Row> rows) {
        Map<String, List<Row>> byGroup = rows.stream().collect(Collectors.groupingBy(Row::groupId));
        long total = 0;
        long fully = 0;
        long namedOnly = 0;
        long automaticOnly = 0;
        long versionOnly = 0;
        long partial = 0;
        long without = 0;
        for (List<Row> group : byGroup.values()) {
            total++;
            List<Row> modules = group.stream().filter(Row::modular).toList();
            if (modules.isEmpty()) {
                without++;
                continue;
            }
            if (modules.size() == group.size()) {
                fully++;
            } else {
                partial++;
            }
            boolean hasNamed = modules.stream().anyMatch(row -> row.type() == ModuleType.NAMED);
            boolean hasAutomatic = modules.stream().anyMatch(row -> row.type() == ModuleType.AUTOMATIC);
            if (hasNamed && !hasAutomatic) {
                namedOnly++;
            }
            if (hasAutomatic && !hasNamed) {
                automaticOnly++;
            }
            if (modules.stream().allMatch(Row::declaresModuleVersion)) {
                versionOnly++;
            }
        }
        return new GroupStats(total, fully, namedOnly, automaticOnly, versionOnly, partial, without);
    }

    private static void appendGroupMetric(StringBuilder builder, String label,
                                          long allCount, long allTotal, long libCount, long libTotal,
                                          long maintCount, long maintTotal) {
        builder.append("| ").append(label)
                .append(" | ").append(metricCell(allCount, (int) allTotal))
                .append(" | ").append(metricCell(libCount, (int) libTotal))
                .append(" | ").append(metricCell(maintCount, (int) maintTotal))
                .append(" |\n");
    }

    private static String metricCell(long count, int denom) {
        return count + " (" + percent(count, denom) + ")";
    }

    private static String percent(long count, int total) {
        return total == 0 ? "0,0%" : String.format(Locale.GERMANY, "%.1f%%", 100.0d * count / total);
    }

    private static String plural(long count, String noun) {
        return count + " " + noun + (count == 1L ? "" : "s");
    }

    /**
     * Reads the repository's directory listing for every release the window holds, one request
     * per release, and returns them keyed by {@code groupId:artifactId:version}. This is the only
     * network the report does: it touches the listed coordinates alone, never an artifact's bytes,
     * and keeps nothing, because a listing already carries the size of every file beside it.
     *
     * <p>A release whose listing cannot be read is left out rather than counted as empty, so a
     * failed request lowers no average.
     */
    /** One release of one artifact, awaiting its listing. */
    private record Release(String groupId, String artifactId, String version) {

        String path() {
            return groupId.replace('.', '/') + '/' + artifactId + '/' + version + '/';
        }
    }

    /**
     * Measures what every groupId on the list published during the window, which is the unit Maven
     * Central caps. A group is measured once however many of its artifacts the list carries, and
     * over all of its artifacts rather than the listed ones alone: the scan log already knows every
     * coordinate the group ever published, so the group's artifacts are read from
     * {@code data/scanned/<group>/} and each of their releases in the window is fetched.
     *
     * <p>Only the files directly under a group's directory belong to it. A nested directory is a
     * different groupId, capped separately by Central unless it happens to share an organization,
     * so it is left to its own row.
     */
    private static Map<String, GroupPublishing> groupPublishing(URI base, List<Artifact> targets, Path scannedRoot,
                                                                long yearStart, long cutoff,
                                                                int concurrency) throws IOException {
        SequencedSet<String> groupIds = new LinkedHashSet<>();
        for (Artifact target : targets) {
            groupIds.add(target.groupId());
        }
        Map<String, Set<String>> artifactsByGroup = new LinkedHashMap<>();
        List<Release> pending = new ArrayList<>();
        for (String groupId : groupIds) {
            Path groupDir = scannedRoot;
            for (String segment : groupId.split("\\.", -1)) {
                groupDir = groupDir.resolve(segment);
            }
            if (!Files.isDirectory(groupDir)) {
                continue;
            }
            try (Stream<Path> files = Files.list(groupDir)) {
                for (Path file : (Iterable<Path>) files.filter(Files::isRegularFile)
                        .filter(path -> path.getFileName().toString().endsWith(".tsv"))
                        .sorted()::iterator) {
                    String name = file.getFileName().toString();
                    String artifactId = name.substring(0, name.length() - ".tsv".length());
                    Set<String> versions = versionsInWindow(file, yearStart, cutoff);
                    if (versions.isEmpty()) {
                        continue;
                    }
                    artifactsByGroup.computeIfAbsent(groupId, _ -> new LinkedHashSet<>()).add(artifactId);
                    for (String version : versions) {
                        pending.add(new Release(groupId, artifactId, version));
                    }
                }
            }
        }
        if (pending.isEmpty()) {
            return Map.of();
        }
        System.err.println("[top-modules] reading " + pending.size() + " release listing(s) across "
                + artifactsByGroup.size() + " group(s) from " + base);

        Map<String, Listing> listings = new ConcurrentHashMap<>(pending.size());
        AtomicInteger missing = new AtomicInteger();
        AtomicInteger failed = new AtomicInteger();
        AtomicInteger done = new AtomicInteger();
        try (Fetcher fetcher = new Fetcher()) {
            verifyRobotsTxt(fetcher, base);
            ExecutorService executor = Executors.newFixedThreadPool(concurrency);
            try {
                List<Future<?>> futures = new ArrayList<>(pending.size());
                for (Release release : pending) {
                    futures.add(executor.submit(() -> {
                        Listing listing = fetchListing(fetcher, base, release.path(), missing, failed);
                        if (listing != null) {
                            listings.put(release.groupId() + ':' + release.artifactId() + ':' + release.version(), listing);
                        }
                        int completed = done.incrementAndGet();
                        if (completed % 25_000 == 0) {
                            System.err.println("[top-modules] " + completed + " of " + pending.size() + " read");
                        }
                    }));
                }
                for (Future<?> future : futures) {
                    try {
                        future.get();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new IOException("Interrupted while reading release listings", e);
                    } catch (ExecutionException e) {
                        throw new IOException("Failed to read a release listing", e.getCause());
                    }
                }
            } finally {
                executor.shutdownNow();
            }
        }
        System.err.println("[top-modules] read=" + listings.size()
                + " missing=" + missing.get() + " failed=" + failed.get());

        Map<String, long[]> totals = new LinkedHashMap<>();
        Map<String, Set<String>> releasesByGroup = new LinkedHashMap<>();
        for (Release release : pending) {
            Listing listing = listings.get(release.groupId() + ':' + release.artifactId() + ':' + release.version());
            if (listing == null) {
                continue;
            }
            long[] total = totals.computeIfAbsent(release.groupId(), _ -> new long[2]);
            total[0] += listing.files();
            total[1] += listing.bytes();
            releasesByGroup.computeIfAbsent(release.groupId(), _ -> new HashSet<>()).add(release.version());
        }
        Map<String, GroupPublishing> publishing = new LinkedHashMap<>();
        for (Map.Entry<String, long[]> entry : totals.entrySet()) {
            String groupId = entry.getKey();
            publishing.put(groupId, new GroupPublishing(true,
                    artifactsByGroup.getOrDefault(groupId, Set.of()).size(),
                    releasesByGroup.getOrDefault(groupId, Set.of()).size(),
                    entry.getValue()[0],
                    entry.getValue()[1]));
        }
        return publishing;
    }

    /** The distinct versions a scanned file records as published inside the window. */
    private static Set<String> versionsInWindow(Path file, long yearStart, long cutoff) throws IOException {
        Set<String> versions = new LinkedHashSet<>();
        try (Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8)) {
            for (String line : (Iterable<String>) lines::iterator) {
                if (line.isEmpty()) {
                    continue;
                }
                ScannedEntry entry = ScannedEntry.parse(line);
                if (entry.publishedAt() >= yearStart && entry.publishedAt() < cutoff) {
                    versions.add(entry.version());
                }
            }
        }
        return versions;
    }

    private static Listing fetchListing(Fetcher fetcher, URI base, String path,
                                        AtomicInteger missing, AtomicInteger failed) {
        URI uri = base.resolve(path);
        try {
            Optional<String> body = fetcher.getOptional(uri);
            if (body.isEmpty()) {
                missing.incrementAndGet();
                return null;
            }
            Listing listing = parseListing(body.get());
            if (listing.files() == 0) {
                missing.incrementAndGet();
                return null;
            }
            return listing;
        } catch (IOException e) {
            failed.incrementAndGet();
            System.err.println("[top-modules] " + uri + ": " + e.getMessage());
            return null;
        }
    }

    /**
     * Counts the files a directory listing serves and sums their sizes. Each entry is an anchor
     * followed by a last-modified date and a size; an anchor whose target ends in a slash is the
     * parent link or a nested directory, not a file of this release. The listing abbreviates long
     * file names with an ellipsis, which is why no name is read here: the anchor's target decides
     * whether a row counts, and only the trailing size column is summed.
     */
    public static Listing parseListing(String html) {
        int files = 0;
        long bytes = 0L;
        for (String line : html.split("\n")) {
            int anchor = line.indexOf("<a href=\"");
            if (anchor < 0) {
                continue;
            }
            int targetStart = anchor + "<a href=\"".length();
            int targetEnd = line.indexOf('"', targetStart);
            if (targetEnd < 0) {
                continue;
            }
            String target = line.substring(targetStart, targetEnd);
            if (target.isEmpty() || target.endsWith("/")) {
                continue;
            }
            int close = line.indexOf("</a>", targetEnd);
            if (close < 0) {
                continue;
            }
            String[] columns = line.substring(close + "</a>".length()).strip().split("\\s+");
            if (columns.length == 0) {
                continue;
            }
            long size;
            try {
                size = Long.parseLong(columns[columns.length - 1]);
            } catch (NumberFormatException _) {
                continue;
            }
            files++;
            bytes += size;
        }
        return new Listing(files, bytes);
    }

    private static void verifyRobotsTxt(Fetcher fetcher, URI baseUri) throws IOException {
        String authority = baseUri.getAuthority();
        RobotsTxt.Rules rules;
        try {
            rules = RobotsTxt.fetch(fetcher, baseUri);
        } catch (IOException e) {
            System.err.println("[top-modules] robots.txt fetch failed for " + authority
                    + " (" + e.getMessage() + "); continuing without restrictions");
            return;
        }
        String path = baseUri.getPath();
        if (!rules.allows(path)) {
            throw new IOException("robots.txt for " + authority + " disallows " + path
                    + " for " + RobotsTxt.agentToken(Fetcher.USER_AGENT));
        }
    }

    private static Optional<String> property(String name) {
        String value = System.getProperty(name);
        return value == null || value.isBlank() ? Optional.empty() : Optional.of(value.trim());
    }

    private static void printUsage() {
        System.out.println("Usage: java build.jenesis.crawler.TopModules <data/top/YYYY.txt> [<more.txt> ...]");
        System.out.println();
        System.out.println("For each input file (named for a four-digit year, one 'groupId:artifactId' per line),");
        System.out.println("writes a sibling 'YYYY.md' table cross-referencing the listed artifacts against the");
        System.out.println("module catalogue (data/modules/**/versions.tsv) and the scan log (data/scanned/).");
        System.out.println();
        System.out.println("Columns: Top (rank), Artifact, Module (latest name + a single emoji: automatic, named,");
        System.out.println("or named-with-module-info-version), Last publication, Artifact age and Module");
        System.out.println("age (years, comma-decimal, to the year end), Latest artifact version, Latest module");
        System.out.println("version, Total released artifacts and modules (distinct versions to the year end), and");
        System.out.println("Artifacts and Modules released in year. Every figure is bound to the year end; artifacts");
        System.out.println("with no module by then keep the module columns blank.");
        System.out.println();
        System.out.println("Maven build tooling (Maven, Plexus, Sonatype/Sisu/Aether) and POM-only aggregators");
        System.out.println("(artifactIds ending -parent, -bom, -dependencies) are struck through and excluded from");
        System.out.println("the summary's Libraries column, since they can never carry a Java module.");
        System.out.println();
        System.out.println("Optional system properties:");
        System.out.println("  -D" + PROP_DATA + "=<dir>");
        System.out.println("        Data directory holding modules/ and scanned/ (default 'data').");
        System.out.println("  -D" + PROP_RELEASES_URI + "=<uri>");
        System.out.println("        Repository to measure publishing volume against (e.g.");
        System.out.println("        https://repo1.maven.org/maven2/). Set it to add the five publishing columns:");
        System.out.println("        group artifacts, files and MB per release, releases per month, and the Maven");
        System.out.println("        Central thresholds exceeded. The figures are per groupId, not per artifact,");
        System.out.println("        since that is what Central caps, and cover every artifact of the group rather");
        System.out.println("        than the listed ones - one directory-listing request per release in the");
        System.out.println("        window, which for a 1000-artifact list is a few hundred thousand requests. No");
        System.out.println("        artifact is downloaded and nothing is written to data/. Unset, the report is");
        System.out.println("        rendered offline without those columns.");
        System.out.println("  -D" + PROP_RELEASES_CONCURRENCY + "=<n>");
        System.out.println("        Concurrent listing requests (default " + DEFAULT_RELEASES_CONCURRENCY + ").");
        System.out.println("  -D" + PROP_BLEEDING + "=true");
        System.out.println("        Bleeding-edge mode: take the latest input list and assess it against current");
        System.out.println("        data (cutoff = the crawler's index timestamp, nothing cropped to a year end),");
        System.out.println("        writing a single 'BLEEDING.md' beside it. Windows (year, three-year,");
        System.out.println("        maintained) are relative to that index timestamp, so the report re-renders");
        System.out.println("        identically until the index advances.");
    }
}
