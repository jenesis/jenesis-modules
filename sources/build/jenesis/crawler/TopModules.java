package build.jenesis.crawler;

import module java.base;
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
 *       end (blank if that version carries none), suffixed with an emoji for its type ({@code named}
 *       or {@code automatic}) and a second emoji when the module declares a version in
 *       {@code module-info};</li>
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
 * <p>Module facts come from {@code data/modules/<name-as-path>/versions.tsv}, the only catalogue
 * file carrying both publish timestamps and the named/automatic type. ({@code modules.tsv} is a
 * derived, named-only view without dates.) The module name is the directory path of the
 * {@code versions.tsv}, its segments joined by {@code '.'}. A coordinate that mapped to several
 * module names over time contributes a row from each; the latest publication on or before the
 * year end decides the reported name, type and version.
 */
public final class TopModules {

    public static final String PROP_DATA = "jenesis.crawler.data";
    private static final String DEFAULT_DATA_DIR = "data";
    private static final String VERSIONS_FILE = ModuleStore.LEAF_FILE_BASE + ModuleStore.LEAF_FILE_EXTENSION;

    private static final DateTimeFormatter ISO_DATE = DateTimeFormatter
            .ofPattern("yyyy-MM-dd")
            .withZone(ZoneOffset.UTC);

    /** Mean Gregorian year, so a comma-decimal age reads naturally across leap years. */
    private static final double MILLIS_PER_YEAR = 365.2425d * 86_400_000d;
    private static final String NAMED_EMOJI = "🏷️"; // label tag
    private static final String AUTOMATIC_EMOJI = "⚙️";   // gear
    private static final String VERSIONED_EMOJI = "🔖"; // module-info declares a version
    private static final String STALE_EMOJI = "🚩"; // declares a module-info version but no release in three years

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
                             int totalVersions, int versionsInYear) {
        static final ScanStats NONE = new ScanStats(0L, 0L, "", 0, 0);
    }

    /** A rendered detail row plus the classification the summary table aggregates over. */
    private record Row(String[] cells, boolean modular, ModuleType type, boolean declaresModuleVersion,
                       boolean mavenRelated, boolean pomAggregator, String groupId) {

        /** Excluded from the library figures: structurally cannot reflect module adoption. */
        boolean excluded() {
            return mavenRelated || pomAggregator;
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

        for (Path topFile : topFiles) {
            int year = years.get(topFile);
            long yearStart = LocalDate.of(year, 1, 1).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
            long threeYearStart = LocalDate.of(year - 2, 1, 1).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
            long cutoff = LocalDate.of(year + 1, 1, 1).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
            List<Artifact> targets = targetsByFile.get(topFile);
            List<Row> rows = new ArrayList<>(targets.size());
            for (int rank = 0; rank < targets.size(); rank++) {
                Artifact target = targets.get(rank);
                rows.add(buildRow(rank + 1, target, index.getOrDefault(target, List.of()), yearStart, threeYearStart, cutoff, scannedRoot));
            }
            Path output = topFile.resolveSibling(stem(topFile) + ".md");
            Files.writeString(output, render(year, rows), StandardCharsets.UTF_8);
            long withModule = rows.stream().filter(Row::modular).count();
            System.err.println("[top-modules] " + output + " (" + rows.size() + " artifacts, "
                    + withModule + " with a module by " + year + ")");
        }
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

    private static Row buildRow(int rank, Artifact target, List<Hit> hits, long yearStart, long threeYearStart, long cutoff, Path scannedRoot) throws IOException {
        List<Hit> qualifying = hits.stream().filter(hit -> hit.entry().publishedAt() < cutoff).toList();
        ScanStats scanned = scanStats(scannedRoot, target, yearStart, cutoff);

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
            module = latest.moduleName() + ' ' + (type == ModuleType.NAMED ? NAMED_EMOJI : AUTOMATIC_EMOJI)
                    + (declaresModuleVersion ? ' ' + VERSIONED_EMOJI : "");
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
        boolean stale = declaresModuleVersion
                && scanned.lastPublished() > 0L
                && scanned.lastPublished() < threeYearStart;
        String artifactCell = stale ? target + " " + STALE_EMOJI : target.toString();
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
                Integer.toString(scanned.versionsInYear()),
                Long.toString(modulesInYear),
        };
        boolean mavenRelated = isMavenRelated(target);
        boolean pomAggregator = isPomAggregator(target);
        if (mavenRelated || pomAggregator) {
            for (int index = 0; index < cells.length; index++) {
                cells[index] = "~~" + (cells[index].isEmpty() ? "-" : cells[index]) + "~~";
            }
        }
        return new Row(cells, modular, type, declaresModuleVersion, mavenRelated, pomAggregator, target.groupId());
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
                ? new ScanStats(firstPublished, lastPublished, latestVersion, versions.size(), versionsInYear.size())
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

    private static String render(int year, List<Row> rows) {
        int total = rows.size();
        long mavenRows = rows.stream().filter(Row::mavenRelated).count();
        long pomRows = rows.stream().filter(row -> row.pomAggregator() && !row.mavenRelated()).count();
        long excluded = rows.stream().filter(Row::excluded).count();
        int totalLibraries = total - (int) excluded;
        GroupStats allGroups = groupStats(rows);
        GroupStats libGroups = groupStats(rows.stream().filter(row -> !row.excluded()).toList());

        StringBuilder builder = new StringBuilder();
        builder.append("# Top artifacts vs. registered modules (").append(year).append(")\n\n");

        builder.append("**By artifact**\n\n");
        builder.append("| Category | All listed | Libraries |\n");
        builder.append("|---|---|---|\n");
        appendMetric(builder, "Total artifacts", rows, _ -> true, total, totalLibraries);
        appendMetric(builder, "Modular artifacts", rows, Row::modular, total, totalLibraries);
        appendMetric(builder, "Automatic modules", rows, row -> row.type() == ModuleType.AUTOMATIC, total, totalLibraries);
        appendMetric(builder, "Named modules", rows, row -> row.type() == ModuleType.NAMED, total, totalLibraries);
        appendMetric(builder, "Named modules with declared version", rows, Row::declaresModuleVersion, total, totalLibraries);
        appendMetric(builder, "Non-modular artifacts", rows, row -> !row.modular(), total, totalLibraries);
        builder.append('\n');

        builder.append("**By groupId**\n\n");
        builder.append("| Category | All listed | Libraries |\n");
        builder.append("|---|---|---|\n");
        long allTotal = allGroups.total();
        long libTotal = libGroups.total();
        appendGroupMetric(builder, "Total groups", allGroups.total(), allTotal, libGroups.total(), libTotal);
        appendGroupMetric(builder, "Groups without modules", allGroups.without(), allTotal, libGroups.without(), libTotal);
        appendGroupMetric(builder, "Partial modularized groups", allGroups.withModules(), allTotal, libGroups.withModules(), libTotal);
        appendGroupMetric(builder, "Groups with full modularization", allGroups.fully(), allTotal, libGroups.fully(), libTotal);
        appendGroupMetric(builder, "Groups with named modules only", allGroups.namedOnly(), allTotal, libGroups.namedOnly(), libTotal);
        appendGroupMetric(builder, "Groups with automatic modules only", allGroups.automaticOnly(), allTotal, libGroups.automaticOnly(), libTotal);
        appendGroupMetric(builder, "Groups with modules and version info only", allGroups.versionOnly(), allTotal, libGroups.versionOnly(), libTotal);
        builder.append('\n');

        builder.append("Counts are absolute with the share in parentheses. \"All listed\" covers all ")
                .append(total).append(" artifacts; \"Libraries\" excludes the ").append(excluded)
                .append(" struck rows that cannot reflect module adoption (").append(mavenRows)
                .append(" Maven build-tooling, ").append(pomRows)
                .append(" POM-only parents/BOMs/dependencies) and is over the remaining ")
                .append(totalLibraries).append(", as of ").append(year).append("-12-31. Artifact shares are of ")
                .append("total artifacts; group shares are of total groups. \"Partial modularized groups\" have at ")
                .append("least one artifact whose latest version carries a module; \"full modularization\" is the ")
                .append("subset where every artifact does; the named/automatic/version rows classify groups whose ")
                .append("modules are exclusively of that kind.\n\n");

        builder.append("Every figure is as of ").append(year)
                .append("-12-31, and each artifact is judged by its latest version on or before that date: the ")
                .append("module columns describe that version's module and are blank when the latest version ")
                .append("carries none, even if an earlier version did. Its name, type (")
                .append(NAMED_EMOJI).append(" named / ").append(AUTOMATIC_EMOJI).append(" automatic, plus ")
                .append(VERSIONED_EMOJI).append(" when module-info declares a version) and version come from that ")
                .append("latest version; the last-publication date and latest artifact version are from the latest ")
                .append("scanned publication on or before it. A ").append(STALE_EMOJI)
                .append(" flags a module that declares a module-info version but has had no release in the last ")
                .append("three years. Ages are in years (comma-decimal) measured to that date: ")
                .append("artifact age from the artifact's first publication, module age from its first module ")
                .append("publication. The trailing counts are distinct versions: the \"released\" totals cover ")
                .append("everything up to the year end, \"in year\" only the report year, and the module counts ")
                .append("only versions that carried a Java module. Two kinds of row are shown struck through and ")
                .append("excluded from the Libraries column, as they top these rankings for reasons unrelated to ")
                .append("module adoption and can never carry a module: Maven's own build tooling (").append(mavenRows)
                .append(" rows: Maven, Plexus, Sonatype/Sisu/Aether) and POM-only aggregators (").append(pomRows)
                .append(" rows: parents, BOMs and dependency imports, which ship no JAR).\n\n");

        builder.append("| ").append(String.join(" | ", HEADERS)).append(" |\n");
        builder.append("|").append("---|".repeat(HEADERS.size())).append('\n');
        for (Row row : rows) {
            builder.append('|');
            for (String cell : row.cells()) {
                builder.append(' ').append(cell.replace("|", "\\|")).append(" |");
            }
            builder.append('\n');
        }
        return builder.toString();
    }

    private static void appendMetric(StringBuilder builder, String label, List<Row> rows,
                                     Predicate<Row> predicate, int total, int totalLibraries) {
        long all = rows.stream().filter(predicate).count();
        long libraries = rows.stream().filter(row -> !row.excluded()).filter(predicate).count();
        builder.append("| ").append(label)
                .append(" | ").append(metricCell(all, total))
                .append(" | ").append(metricCell(libraries, totalLibraries))
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
                                          long allCount, long allTotal, long libCount, long libTotal) {
        builder.append("| ").append(label)
                .append(" | ").append(metricCell(allCount, (int) allTotal))
                .append(" | ").append(metricCell(libCount, (int) libTotal))
                .append(" |\n");
    }

    private static String metricCell(long count, int denom) {
        return count + " (" + percent(count, denom) + ")";
    }

    private static String percent(long count, int total) {
        return total == 0 ? "0,0%" : String.format(Locale.GERMANY, "%.1f%%", 100.0d * count / total);
    }

    private static void printUsage() {
        System.out.println("Usage: java build.jenesis.crawler.TopModules <data/top/YYYY.txt> [<more.txt> ...]");
        System.out.println();
        System.out.println("For each input file (named for a four-digit year, one 'groupId:artifactId' per line),");
        System.out.println("writes a sibling 'YYYY.md' table cross-referencing the listed artifacts against the");
        System.out.println("module catalogue (data/modules/**/versions.tsv) and the scan log (data/scanned/).");
        System.out.println();
        System.out.println("Columns: Top (rank), Artifact, Module (latest name + named/automatic emoji, plus an");
        System.out.println("emoji when module-info declares a version), Last publication, Artifact age and Module");
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
    }
}
