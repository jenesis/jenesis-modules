package build.jenesis.crawler;

import module java.base;
import build.jenesis.crawler.model.ModuleEntry;
import build.jenesis.crawler.model.ModuleType;
import build.jenesis.crawler.store.ModuleStore;
import build.jenesis.crawler.store.ModuleStore.OwnersPolicy;

/**
 * Walks {@code data/modules/} and writes {@code data/DRIFTERS.md}: a report of module names whose
 * ownership has not been fully decided. A module "drifts" when more than one groupId publishes the
 * name and its {@code owners.tsv} does not yet name every one of them (no {@code owners.tsv} at all,
 * or one that leaves some publishing groupId neither {@code allowed} nor {@code blocked}).
 *
 * <p>Each drift is classified, as of today, into one of three shapes:
 * <ul>
 *   <li>{@code republisher} - the current (earliest-publishing) owner is foreign to the module
 *       name while a natural-namespace owner is also present (a shaded / repackaged jar that
 *       bundles someone else's module under its own coordinate).</li>
 *   <li>{@code migration} - the publishing groupId handed off over time: the old coordinate went
 *       dormant and a newer one (same project rename, or a cross-org relocation) took over.</li>
 *   <li>{@code fork} - a second, cross-org coordinate publishes the same name while the original
 *       owner is still active: the original stays canonical, the other is a fork.</li>
 * </ul>
 * Anything that fits none of these lands in {@code unclassified}.
 *
 * <p>With {@code -Djenesis.crawler.drift.emit=<category>} the tool also writes a {@code SetOwners}
 * properties file proposing the inferred owner(s) for every module in that category; applying it
 * (SetOwners auto-blocks the other publishers) clears those modules from the next report.
 */
public final class DriftReport {

    public static final String PROP_DATA = "jenesis.crawler.data";
    public static final String PROP_TODAY = "jenesis.crawler.drift.today";
    public static final String PROP_EMIT = "jenesis.crawler.drift.emit";
    public static final String PROP_EMIT_FILE = "jenesis.crawler.drift.emit.file";
    public static final String PROP_DETAIL_LIMIT = "jenesis.crawler.drift.detail.limit";
    private static final int DEFAULT_DETAIL_LIMIT = 200;
    private static final String DEFAULT_DATA_DIR = "data";
    private static final String REPORT_FILE = "DRIFTERS.md";
    private static final int TIMELINE_WIDTH = 20;
    private static final int ACTIVE_MONTHS = 18;
    private static final int MAX_GROUP_ROWS = 6;
    private static final int MAX_NAMES_IN_SUMMARY = 12;

    private static final DateTimeFormatter YM = DateTimeFormatter.ofPattern("yyyy-MM").withZone(ZoneOffset.UTC);

    private DriftReport() {
    }

    enum Category {
        REPUBLISHER("republisher", "Earliest owner is foreign to the module name while a natural-namespace owner is also present (shaded / repackaged jars). Proposal: allow the natural owner, block the republisher."),
        MIGRATION("migration", "The publishing groupId handed off over time: the old coordinate went dormant, a newer one took over (a rename or a relocation). Proposal: allow both old and new so history stays resolvable and `latest` is current."),
        FORK("fork", "A cross-org coordinate publishes the same name while the original owner is still active. Proposal: keep the original owner, block the fork."),
        UNCLASSIFIED("unclassified", "Multiple publishers that fit none of the shapes above (concurrent collisions, ambiguous handoffs). Proposal: keep the current owner, but review by hand.");

        final String id;
        final String blurb;

        Category(String id, String blurb) {
            this.id = id;
            this.blurb = blurb;
        }

        static Category byId(String id) {
            for (Category category : values()) {
                if (category.id.equals(id)) {
                    return category;
                }
            }
            throw new IllegalArgumentException("Unknown category '" + id + "'. Expected one of: republisher, migration, fork, unclassified");
        }
    }

    /** Mutable per-groupId accumulator over a module's audit log. */
    static final class Group {
        final String groupId;
        long first = Long.MAX_VALUE;
        long last = Long.MIN_VALUE;
        long latestAt = Long.MIN_VALUE;
        String latestVersion = "";
        int count;

        Group(String groupId) {
            this.groupId = groupId;
        }

        void add(ModuleEntry entry) {
            long at = entry.publishedAt();
            first = Math.min(first, at);
            last = Math.max(last, at);
            count++;
            if (at >= latestAt) {
                latestAt = at;
                latestVersion = entry.mavenVersion().raw();
            }
        }
    }

    record Drift(String module, List<Group> groups, Group owner, Category category,
                 List<String> allowed, String description, OwnersPolicy owners) {
    }

    public static void main(String[] arguments) throws IOException {
        for (String argument : arguments) {
            if (argument.equals("--help") || argument.equals("-h")) {
                printUsage();
                return;
            }
        }
        String configuredDataDir = System.getProperty(PROP_DATA);
        Path dataDir = configuredDataDir == null || configuredDataDir.isBlank()
                ? Path.of(DEFAULT_DATA_DIR)
                : Path.of(configuredDataDir.trim());
        Path modulesRoot = dataDir.resolve("modules");
        if (!Files.isDirectory(modulesRoot)) {
            throw new IOException("No modules directory at " + modulesRoot);
        }
        String todayProperty = System.getProperty(PROP_TODAY);
        LocalDate today = todayProperty == null || todayProperty.isBlank()
                ? LocalDate.now(ZoneOffset.UTC)
                : LocalDate.parse(todayProperty.trim());
        long todayMillis = today.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
        long activeCutoff = today.minusMonths(ACTIVE_MONTHS).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();

        ModuleStore store = new ModuleStore(modulesRoot);

        // Pass 1: per-module per-group stats, plus each group's most recent activity anywhere.
        SortedMap<String, Map<String, Group>> byModule = new TreeMap<>();
        Map<String, Long> globalLast = new HashMap<>();
        long earliestOverall = todayMillis;
        try (Stream<Path> stream = Files.walk(modulesRoot)) {
            for (Path dir : (Iterable<Path>) stream::iterator) {
                if (dir.equals(modulesRoot) || !Files.isDirectory(dir) || !hasVersionsFile(dir)) {
                    continue;
                }
                String moduleName = dottedName(modulesRoot.relativize(dir));
                if (!ModuleStore.isValidModuleName(moduleName)) {
                    continue;
                }
                List<ModuleEntry> rows = store.readAllVersions(moduleName);
                if (rows.isEmpty()) {
                    continue;
                }
                Map<String, Group> groups = new HashMap<>();
                for (ModuleEntry entry : rows) {
                    groups.computeIfAbsent(entry.groupId(), Group::new).add(entry);
                    globalLast.merge(entry.groupId(), entry.publishedAt(), Math::max);
                    earliestOverall = Math.min(earliestOverall, entry.publishedAt());
                }
                byModule.put(moduleName, groups);
            }
        }

        // Pass 2: keep the modules that drift, and classify each.
        List<Drift> drifts = new ArrayList<>();
        int multiGroup = 0;
        for (Map.Entry<String, Map<String, Group>> entry : byModule.entrySet()) {
            Map<String, Group> groups = entry.getValue();
            if (groups.size() < 2) {
                continue;
            }
            multiGroup++;
            OwnersPolicy owners = store.loadOwners(entry.getKey()).orElse(null);
            if (owners != null && owners.namedGroups().containsAll(groups.keySet())) {
                continue; // every publisher is named (allowed or blocked) - handled, not drift
            }
            drifts.add(classify(entry.getKey(), groups, globalLast, activeCutoff, owners));
        }

        String emit = System.getProperty(PROP_EMIT);
        int detailLimit = intProperty(PROP_DETAIL_LIMIT, DEFAULT_DETAIL_LIMIT);
        Path report = dataDir.resolve(REPORT_FILE);
        Files.writeString(report, render(drifts, byModule.size(), multiGroup, today, earliestOverall, todayMillis, detailLimit),
                StandardCharsets.UTF_8);
        System.err.println("[drift] " + report + " (" + drifts.size() + " unresolved of " + multiGroup + " multi-owner modules)");

        if (emit != null && !emit.isBlank()) {
            Category category = Category.byId(emit.trim().toLowerCase(Locale.ROOT));
            String emitFile = System.getProperty(PROP_EMIT_FILE);
            Path target = emitFile == null || emitFile.isBlank()
                    ? Path.of("drift-" + category.id + ".properties")
                    : Path.of(emitFile.trim());
            long written = emit(drifts, category, target);
            System.err.println("[drift] " + target + " (" + written + " " + category.id + " modules for SetOwners)");
        }
    }

    private static Drift classify(String module, Map<String, Group> groupMap, Map<String, Long> globalLast,
                                  long activeCutoff, OwnersPolicy owners) {
        List<Group> groups = new ArrayList<>(groupMap.values());
        groups.sort(Comparator.comparingLong((Group g) -> g.first).thenComparing(g -> g.groupId));
        Group owner = groups.getFirst(); // earliest first publication = the implicit resolution owner

        List<String> naturals = groups.stream()
                .filter(g -> g != owner && under(module, g.groupId))
                .map(g -> g.groupId)
                .toList();
        Group successor = groups.stream()
                .filter(g -> g != owner && g.last > owner.last)
                .max(Comparator.comparingLong(g -> g.last))
                .orElse(null);

        // Republisher only when the owner is cross-org to the natural owner; a same-project natural
        // (e.g. com.jwebmp.jre10 vs com.jwebmp) is a rename, handled by the migration branch.
        boolean ownerForeign = !under(module, owner.groupId)
                && naturals.stream().anyMatch(natural -> !sameProject(owner.groupId, natural));
        Category category;
        List<String> allowed;
        String description;
        if (ownerForeign) {
            category = Category.REPUBLISHER;
            allowed = naturals.stream().filter(natural -> !sameProject(owner.groupId, natural)).toList();
            description = "republished by `" + owner.groupId + "`; belongs to " + code(allowed);
        } else if (successor != null && sameProject(owner.groupId, successor.groupId)) {
            category = Category.MIGRATION;
            allowed = Stream.concat(Stream.of(owner.groupId),
                            groups.stream().map(g -> g.groupId).filter(g -> sameProject(owner.groupId, g)))
                    .distinct().toList();
            description = "renamed `" + owner.groupId + "` -> `" + successor.groupId
                    + "` (latest " + successor.latestVersion + ")";
        } else if (successor != null && globalLast.getOrDefault(owner.groupId, 0L) < activeCutoff) {
            category = Category.MIGRATION;
            allowed = List.of(owner.groupId, successor.groupId);
            description = "relocated `" + owner.groupId + "` (dormant) -> `" + successor.groupId
                    + "` (latest " + successor.latestVersion + ")";
        } else if (successor != null) {
            category = Category.FORK;
            allowed = List.of(owner.groupId);
            description = "fork: keep `" + owner.groupId + "`, `" + successor.groupId + "` still publishes the name";
        } else {
            category = Category.UNCLASSIFIED;
            allowed = List.of(owner.groupId);
            description = "multiple owners; `" + owner.groupId + "` is earliest and most recent";
        }
        return new Drift(module, groups, owner, category, allowed, description, owners);
    }

    private static int intProperty(String name, int defaultValue) {
        String value = System.getProperty(name);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return Integer.parseInt(value.trim());
    }

    private static String render(List<Drift> drifts, int totalModules, int multiGroup, LocalDate today,
                                 long axisStart, long axisEnd, int detailLimit) {
        Map<Category, List<Drift>> byCategory = new EnumMap<>(Category.class);
        for (Category category : Category.values()) {
            byCategory.put(category, new ArrayList<>());
        }
        for (Drift drift : drifts) {
            byCategory.get(drift.category()).add(drift);
        }

        StringBuilder out = new StringBuilder();
        out.append("# Module ownership drifters\n\n");
        out.append("Generated ").append(today).append(". A module *drifts* when more than one groupId publishes the ")
                .append("name and its `owners.tsv` does not yet name every publisher (no `owners.tsv`, or one that leaves ")
                .append("some publishing groupId neither `allowed` nor `blocked`). Resolving a drift means deciding each ")
                .append("groupId via `SetOwners` (which writes `allowed`/`blocked`); a fully-named module drops off this list.\n\n");

        out.append("| Category | Modules |\n|---|---:|\n");
        for (Category category : Category.values()) {
            out.append("| ").append(category.id).append(" | ").append(byCategory.get(category).size()).append(" |\n");
        }
        out.append("| **unresolved total** | **").append(drifts.size()).append("** |\n");
        out.append("| multi-owner modules scanned | ").append(multiGroup).append(" |\n");
        out.append("| modules scanned | ").append(totalModules).append(" |\n\n");

        out.append("Timeline axis spans ").append(YM.format(Instant.ofEpochMilli(axisStart)))
                .append(" .. ").append(YM.format(Instant.ofEpochMilli(axisEnd))).append(" (today). ")
                .append("Per group: decision `A`=allowed `B`=blocked `?`=undecided, `*`=current owner, then the ")
                .append("publication range, latest version, and a `=` activity bar across the axis.\n\n");

        for (Category category : Category.values()) {
            List<Drift> list = byCategory.get(category);
            out.append("## ").append(category.id).append(" (").append(list.size()).append(")\n\n");
            out.append(category.blurb).append("\n\n");
            if (list.isEmpty()) {
                continue;
            }
            appendAggregate(out, list);
            // Cap the per-module timeline detail (the aggregate above and the per-category emit file
            // carry the complete set); show the most recently active drifts first.
            List<Drift> detail = new ArrayList<>(list);
            detail.sort(Comparator.comparingLong((Drift drift) ->
                            drift.groups().stream().mapToLong(group -> group.last).max().orElse(0L))
                    .reversed().thenComparing(Drift::module));
            int limit = Math.min(detail.size(), detailLimit);
            if (detail.size() > limit) {
                out.append("_Showing the ").append(limit).append(" most recently active of ")
                        .append(detail.size()).append(". For the full list, emit the SetOwners file: ")
                        .append("`-Djenesis.crawler.drift.emit=").append(category.id).append("`._\n\n");
            }
            out.append("```\n");
            for (Drift drift : detail.subList(0, limit)) {
                out.append(drift.module()).append("  [").append(drift.description()).append("]\n");
                // Owner first, then the remaining publishers by most-recent activity. Cap the rows
                // so a name shaded by hundreds of fat jars stays a handful of lines; the rest are
                // still named on a single summary line.
                List<Group> ordered = new ArrayList<>();
                ordered.add(drift.owner());
                drift.groups().stream()
                        .filter(group -> group != drift.owner())
                        .sorted(Comparator.comparingLong((Group group) -> group.last).reversed()
                                .thenComparing(group -> group.groupId))
                        .forEach(ordered::add);
                int shown = Math.min(ordered.size(), MAX_GROUP_ROWS);
                for (int i = 0; i < shown; i++) {
                    Group group = ordered.get(i);
                    out.append("  ")
                            .append(decision(drift.owners(), group.groupId))
                            .append(group == drift.owner() ? " * " : "   ")
                            .append(pad(group.groupId, 36)).append(' ')
                            .append(YM.format(Instant.ofEpochMilli(group.first))).append("..")
                            .append(YM.format(Instant.ofEpochMilli(group.last))).append(' ')
                            .append(pad(group.latestVersion, 12)).append(' ')
                            .append('|').append(timeline(group.first, group.last, axisStart, axisEnd)).append("|\n");
                }
                if (ordered.size() > shown) {
                    List<String> rest = ordered.subList(shown, ordered.size()).stream()
                            .map(group -> group.groupId).toList();
                    out.append("    + ").append(rest.size()).append(" more: ").append(summarize(rest)).append('\n');
                }
            }
            out.append("```\n\n");
        }
        return out.toString();
    }

    /** Compact "current owner -> proposed allowed (count)" rollup so systematic cases stand out. */
    private static void appendAggregate(StringBuilder out, List<Drift> list) {
        Map<String, Integer> counts = new TreeMap<>();
        for (Drift drift : list) {
            counts.merge(drift.owner().groupId + " -> " + String.join(",", drift.allowed()), 1, Integer::sum);
        }
        List<Map.Entry<String, Integer>> top = new ArrayList<>(counts.entrySet());
        top.sort(Comparator.<Map.Entry<String, Integer>>comparingInt(Map.Entry::getValue).reversed());
        out.append("| count | current owner -> proposed allowed |\n|---:|---|\n");
        for (Map.Entry<String, Integer> entry : top.subList(0, Math.min(top.size(), 15))) {
            out.append("| ").append(entry.getValue()).append(" | `").append(entry.getKey()).append("` |\n");
        }
        out.append('\n');
    }

    private static long emit(List<Drift> drifts, Category category, Path target) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add("# SetOwners proposals for drift category '" + category.id + "'.");
        lines.add("# Apply: java -Djenesis.crawler.data=\"$PWD/data\" sources/build/jenesis/crawler/SetOwners.java " + target.getFileName());
        long written = 0;
        for (Drift drift : drifts) {
            if (drift.category() == category) {
                lines.add(drift.module() + "=" + String.join(",", drift.allowed()));
                written++;
            }
        }
        Files.write(target, lines, StandardCharsets.UTF_8);
        return written;
    }

    private static char decision(OwnersPolicy owners, String groupId) {
        if (owners == null) {
            return '?';
        }
        if (owners.allowedGroups().contains(groupId) || hasPairGroup(owners.allowedPairs(), groupId)) {
            return 'A';
        }
        if (owners.blockedGroups().contains(groupId) || hasPairGroup(owners.blockedPairs(), groupId)) {
            return 'B';
        }
        return '?';
    }

    private static boolean hasPairGroup(Set<String> pairs, String groupId) {
        String prefix = groupId + '\t';
        for (String pair : pairs) {
            if (pair.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

    private static String timeline(long first, long last, long axisStart, long axisEnd) {
        char[] bar = new char[TIMELINE_WIDTH];
        Arrays.fill(bar, '.');
        int start = scale(first, axisStart, axisEnd);
        int end = scale(last, axisStart, axisEnd);
        for (int i = start; i <= end; i++) {
            bar[i] = '=';
        }
        return new String(bar);
    }

    private static int scale(long at, long axisStart, long axisEnd) {
        if (axisEnd <= axisStart) {
            return 0;
        }
        long clamped = Math.max(axisStart, Math.min(axisEnd, at));
        int index = (int) Math.round((double) (clamped - axisStart) * (TIMELINE_WIDTH - 1) / (axisEnd - axisStart));
        return Math.max(0, Math.min(TIMELINE_WIDTH - 1, index));
    }

    private static boolean under(String module, String groupId) {
        return module.equals(groupId) || module.startsWith(groupId + ".");
    }

    private static boolean sameProject(String a, String b) {
        return a.startsWith(b + ".") || b.startsWith(a + ".") || sharedLeading(a, b) >= 2;
    }

    private static int sharedLeading(String a, String b) {
        String[] as = a.split("\\.");
        String[] bs = b.split("\\.");
        int shared = 0;
        for (int i = 0; i < Math.min(as.length, bs.length); i++) {
            if (as[i].equals(bs[i])) {
                shared++;
            } else {
                break;
            }
        }
        return shared;
    }

    private static String code(List<String> groups) {
        return groups.stream().map(g -> "`" + g + "`").collect(Collectors.joining(", "));
    }

    private static String pad(String value, int width) {
        return value.length() >= width ? value : value + " ".repeat(width - value.length());
    }

    private static String summarize(List<String> names) {
        if (names.size() <= MAX_NAMES_IN_SUMMARY) {
            return String.join(", ", names);
        }
        return String.join(", ", names.subList(0, MAX_NAMES_IN_SUMMARY))
                + ", (+" + (names.size() - MAX_NAMES_IN_SUMMARY) + " more)";
    }

    private static boolean hasVersionsFile(Path dir) {
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(dir)) {
            for (Path entry : entries) {
                String name = entry.getFileName().toString();
                if (Files.isRegularFile(entry) && name.endsWith(ModuleStore.LEAF_FILE_EXTENSION)
                        && (name.equals(ModuleStore.LEAF_FILE_BASE + ModuleStore.LEAF_FILE_EXTENSION)
                        || name.startsWith(ModuleStore.LEAF_FILE_BASE + '-'))) {
                    return true;
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to inspect " + dir, e);
        }
        return false;
    }

    private static String dottedName(Path relative) {
        StringJoiner joiner = new StringJoiner(".");
        for (Path segment : relative) {
            joiner.add(segment.toString());
        }
        return joiner.toString();
    }

    private static void printUsage() {
        System.out.println("Usage: java build.jenesis.crawler.DriftReport");
        System.out.println();
        System.out.println("Writes data/DRIFTERS.md: module names published by more than one groupId whose");
        System.out.println("owners.tsv does not yet name every publisher, classified as republisher / migration /");
        System.out.println("fork / unclassified, with per-groupId publication ranges and an activity timeline.");
        System.out.println();
        System.out.println("Optional system properties:");
        System.out.println("  -D" + PROP_DATA + "=<dir>           Data directory (default 'data').");
        System.out.println("  -D" + PROP_TODAY + "=<yyyy-MM-dd>   Reference 'today' (default: now, UTC).");
        System.out.println("  -D" + PROP_EMIT + "=<category>      Also write a SetOwners properties file for one");
        System.out.println("        category (republisher|migration|fork|unclassified).");
        System.out.println("  -D" + PROP_EMIT_FILE + "=<file>     Emit target (default 'drift-<category>.properties').");
        System.out.println("  -D" + PROP_DETAIL_LIMIT + "=<n>     Per-category timeline rows (default " + DEFAULT_DETAIL_LIMIT + "); aggregates and");
        System.out.println("        the emit files always cover the full set.");
    }
}
