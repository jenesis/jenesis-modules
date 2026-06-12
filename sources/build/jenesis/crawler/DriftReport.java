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
    private static final String REASSIGN_EMOJI = "🔀";
    private static final String WIDEN_EMOJI = "➕";

    private static final DateTimeFormatter YM = DateTimeFormatter.ofPattern("yyyy-MM").withZone(ZoneOffset.UTC);

    /**
     * Hand-curated module-prefix to owner-groupId overrides, checked before the heuristic. A module
     * whose name equals a key or falls under it (key + ".") is assigned to that groupId. Add rules
     * here for canonical ownerships the naming heuristics cannot infer.
     */
    private static final Map<String, List<String>> EXPLICIT_OWNERS = Map.ofEntries(
            Map.entry("spring", List.of("org.springframework")),
            Map.entry("reactor", List.of("io.projectreactor")),
            Map.entry("zipkin2", List.of("io.zipkin")),
            Map.entry("scala", List.of("org.scala-lang")),
            Map.entry("javafx", List.of("org.openjfx")),
            Map.entry("jme3", List.of("org.jmonkeyengine")),
            Map.entry("com.codahale.metrics", List.of("io.dropwizard")),
            Map.entry("feign", List.of("io.github.openfeign")),
            Map.entry("kotlinx", List.of("org.jetbrains")),
            Map.entry("kora", List.of("ru.tinkoff.kora", "io.koraframework")),
            Map.entry("io.questdb", List.of("org.questdb")),
            Map.entry("akka", List.of("com.typesafe.akka")),
            Map.entry("jamal", List.of("com.javax0.jamal")),
            Map.entry("play", List.of("com.typesafe.play", "org.playframework")),
            Map.entry("okhttp3", List.of("com.squareup.okhttp3")),
            Map.entry("jul.to.slf4j", List.of("org.slf4j")),
            Map.entry("log4j", List.of("org.slf4j")),
            Map.entry("mockwebserver3", List.of("com.squareup.okhttp3")),
            Map.entry("okio", List.of("com.squareup.okio")),
            Map.entry("imgui", List.of("io.github.spair")),
            Map.entry("com.sun.jna", List.of("net.java.dev.jna")),
            Map.entry("com.almasb.fxgl", List.of("com.github.almasb")),
            Map.entry("com.sun.codemodel", List.of("org.glassfish.jaxb")),
            Map.entry("com.sun.xml.txw2", List.of("org.glassfish.jaxb")),
            Map.entry("com.sun.xml.xsom", List.of("org.glassfish.jaxb")),
            Map.entry("com.sun.tools.txw2", List.of("org.glassfish.jaxb")),
            Map.entry("inet.ipaddr", List.of("com.github.seancfoley")),
            Map.entry("org.dataloader", List.of("com.graphql-java"))
    );

    private DriftReport() {
    }

    enum Category {
        EXPLICIT("explicit-rules", "Hand-curated overrides: a module matching an explicit rule is assigned to a fixed owner groupId regardless of the heuristic. Proposal: allow that owner, block the rest."),
        REPUBLISHER("republisher", "Earliest owner is foreign to the module name while a natural-namespace owner is also present (shaded / repackaged jars). Proposal: allow the natural owner, block the republisher."),
        MIGRATION("migration", "The publishing groupId handed off over time: the old coordinate went dormant, a newer one took over (a rename or a relocation). Proposal: allow both old and new so history stays resolvable and `latest` is current."),
        FORK("fork", "A cross-org coordinate publishes the same name while the original owner is still active. Proposal: keep the original owner, block the fork."),
        SHADED("shaded", "The natural-namespace owner (the module name falls under its groupId) is the earliest and most-recent publisher; every other group merely shades or bundles the name under its own coordinate. Proposal: allow the natural owner, block the rest. Resolution is unchanged; this just records the decision so the module drops off the report."),
        TLD_DROPPED("tld-dropped", "The dominant owner's groupId with its top-level domain (first segment) dropped is the module-name prefix. Proposal: allow that owner, block the rest."),
        TWO_SEGMENTS("two-segments", "The dominant owner's groupId with its first two segments dropped is the module-name prefix. Proposal: allow that owner, block the rest."),
        UNCLASSIFIED("unclassified", "Multiple publishers with no natural-namespace owner present (the module name matches no publisher's groupId): a genuine collision the heuristic cannot settle. Proposal: keep the current owner, but review by hand.");

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

    /**
     * A resolved module whose owner(s) differ from the implicit first-publisher owner.
     * {@code widened} = the first publisher is still an owner but others were added (e.g. a groupId
     * migration); otherwise the first publisher was replaced (a reassignment).
     */
    record Reassignment(String module, String implicitOwner, List<String> owners, boolean widened) {
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

        // Pass 2: classify every multi-owner module. Those whose owners.tsv already names every
        // publisher are "resolved" (tallied per category); the rest are unresolved drift.
        List<Drift> drifts = new ArrayList<>();
        Map<Category, Integer> resolvedByCategory = new EnumMap<>(Category.class);
        List<Reassignment> reassigned = new ArrayList<>();
        int multiGroup = 0;
        for (Map.Entry<String, Map<String, Group>> entry : byModule.entrySet()) {
            Map<String, Group> groups = entry.getValue();
            if (groups.size() < 2) {
                continue;
            }
            multiGroup++;
            OwnersPolicy owners = store.loadOwners(entry.getKey()).orElse(null);
            Drift drift = classify(entry.getKey(), groups, globalLast, activeCutoff, owners);
            if (owners != null && owners.namedGroups().containsAll(groups.keySet())) {
                resolvedByCategory.merge(drift.category(), 1, Integer::sum);
                Reassignment reassignment = reassignment(entry.getKey(), drift.owner().groupId, owners);
                if (reassignment != null) {
                    reassigned.add(reassignment);
                }
            } else {
                drifts.add(drift);
            }
        }

        String emit = System.getProperty(PROP_EMIT);
        int detailLimit = intProperty(PROP_DETAIL_LIMIT, DEFAULT_DETAIL_LIMIT);
        Path report = dataDir.resolve(REPORT_FILE);
        Files.writeString(report, render(drifts, resolvedByCategory, reassigned, byModule.size(), multiGroup, today, earliestOverall, todayMillis, detailLimit),
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

        List<String> explicit = explicitOwners(module);
        if (explicit != null) {
            // The rule's owners are groupId prefixes: allow every publisher under any of them (a
            // group and its subgroups), block the rest.
            List<String> allowedExplicit = groups.stream().map(g -> g.groupId)
                    .filter(g -> matchesAnyPrefix(g, explicit))
                    .distinct().toList();
            if (allowedExplicit.isEmpty()) {
                allowedExplicit = explicit;
            }
            long blocked = groups.stream().map(g -> g.groupId).filter(g -> !matchesAnyPrefix(g, explicit)).count();
            String owned = explicit.stream().map(o -> "`" + o + "`").collect(Collectors.joining(", "));
            return new Drift(module, groups, owner, Category.EXPLICIT, allowedExplicit,
                    "explicit rule: owned by " + owned + "; " + blocked + " other group(s) blocked", owners);
        }

        List<String> naturals = groups.stream()
                .filter(g -> g != owner && under(module, g.groupId))
                .map(g -> g.groupId)
                .toList();
        Group successor = groups.stream()
                .filter(g -> g != owner && g.last > owner.last)
                .max(Comparator.comparingLong(g -> g.last))
                .orElse(null);

        // A groupId migration hands the module off over time: the current (earliest) owner stops
        // at or before a successor takes over, or it has gone dormant. That handoff is a migration
        // even across organisations, and is what separates a migration from a republisher - a
        // foreign coordinate that keeps publishing *alongside* the still-active natural owner.
        boolean ownerForeign = !under(module, owner.groupId)
                && naturals.stream().anyMatch(natural -> !sameProject(owner.groupId, natural));
        boolean ownerDormant = globalLast.getOrDefault(owner.groupId, 0L) < activeCutoff;
        // A credible successor published the name more than once; a single late publication is a
        // one-off (often a shaded jar) and must not be mistaken for a relocation.
        boolean handoff = successor != null && successor.count >= 2
                && (owner.last <= successor.first || ownerDormant);
        Category category;
        List<String> allowed;
        String description;
        if (ownerForeign && !handoff) {
            category = Category.REPUBLISHER;
            allowed = naturals.stream().filter(natural -> !sameProject(owner.groupId, natural)).toList();
            description = "republished by `" + owner.groupId + "` (still active); belongs to " + code(allowed);
        } else if (successor != null && sameProject(owner.groupId, successor.groupId)) {
            category = Category.MIGRATION;
            allowed = Stream.concat(Stream.of(owner.groupId),
                            groups.stream().map(g -> g.groupId).filter(g -> sameProject(owner.groupId, g)))
                    .distinct().toList();
            description = "renamed `" + owner.groupId + "` -> `" + successor.groupId
                    + "` (latest " + successor.latestVersion + ")";
        } else if (handoff) {
            category = Category.MIGRATION;
            allowed = List.of(owner.groupId, successor.groupId);
            description = "relocated `" + owner.groupId + "` -> `" + successor.groupId
                    + "` (latest " + successor.latestVersion + ")";
        } else if (successor != null) {
            category = Category.FORK;
            allowed = List.of(owner.groupId);
            description = "fork: keep `" + owner.groupId + "`, `" + successor.groupId + "` still publishes the name";
        } else if (canonicalOwner(module, owner, groups)) {
            // The owner is the earliest and most-recent publisher and the closest groupId to the
            // module name (it shares the longest leading-segment prefix); every other group merely
            // shades or bundles the name. This holds even when the name is not strictly under the
            // groupId, only sharing a leading prefix with it.
            category = Category.SHADED;
            List<String> canonical = Stream.concat(Stream.of(owner.groupId),
                            groups.stream().map(g -> g.groupId).filter(g -> sameProject(owner.groupId, g)))
                    .distinct().toList();
            allowed = canonical;
            description = "owned by `" + owner.groupId + "`; " + (groups.size() - canonical.size())
                    + " other group(s) shade the name";
        } else if (groupTailPrefixes(module, owner.groupId, 1)) {
            // The dominant owner's groupId minus its top-level domain is the module-name prefix.
            category = Category.TLD_DROPPED;
            List<String> canonical = Stream.concat(Stream.of(owner.groupId),
                            groups.stream().map(g -> g.groupId).filter(g -> sameProject(owner.groupId, g)))
                    .distinct().toList();
            allowed = canonical;
            description = "owned by `" + owner.groupId + "` (groupId minus TLD is the module prefix); "
                    + (groups.size() - canonical.size()) + " other group(s) shade the name";
        } else if (groupTailPrefixes(module, owner.groupId, 2)) {
            // The dominant owner's groupId minus its first two segments is the module-name prefix.
            category = Category.TWO_SEGMENTS;
            List<String> canonical = Stream.concat(Stream.of(owner.groupId),
                            groups.stream().map(g -> g.groupId).filter(g -> sameProject(owner.groupId, g)))
                    .distinct().toList();
            allowed = canonical;
            description = "owned by `" + owner.groupId + "` (groupId minus two segments is the module prefix); "
                    + (groups.size() - canonical.size()) + " other group(s) shade the name";
        } else {
            category = Category.UNCLASSIFIED;
            allowed = List.of(owner.groupId);
            description = "no clear owner; `" + owner.groupId + "` is earliest and most recent";
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

    private static String render(List<Drift> drifts, Map<Category, Integer> resolvedByCategory,
                                 List<Reassignment> reassigned, int totalModules,
                                 int multiGroup, LocalDate today, long axisStart, long axisEnd, int detailLimit) {
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

        out.append("| Category | Unresolved | Resolved via owners.tsv |\n|---|---:|---:|\n");
        int resolvedTotal = 0;
        for (Category category : Category.values()) {
            int resolved = resolvedByCategory.getOrDefault(category, 0);
            resolvedTotal += resolved;
            out.append("| ").append(category.id).append(" | ").append(byCategory.get(category).size())
                    .append(" | ").append(resolved).append(" |\n");
        }
        out.append("| **total** | **").append(drifts.size()).append("** | **").append(resolvedTotal).append("** |\n\n");
        out.append("The table covers all **").append(multiGroup).append("** multi-owner modules (of **")
                .append(totalModules).append("** modules scanned).\n\n");

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
        appendReassigned(out, reassigned);
        return out.toString();
    }

    /**
     * Compact "current owner -> new owner(s) (count)" rollup, limited to transitions that would
     * change ownership: a proposal that merely keeps the first-publisher owner is omitted.
     */
    private static void appendAggregate(StringBuilder out, List<Drift> list) {
        Map<String, Integer> counts = new TreeMap<>();
        for (Drift drift : list) {
            if (!changesOwnership(drift)) {
                continue;
            }
            counts.merge(drift.owner().groupId + " -> " + String.join(",", drift.allowed()), 1, Integer::sum);
        }
        if (counts.isEmpty()) {
            return;
        }
        List<Map.Entry<String, Integer>> top = new ArrayList<>(counts.entrySet());
        top.sort(Comparator.<Map.Entry<String, Integer>>comparingInt(Map.Entry::getValue).reversed());
        out.append("| count | current owner -> new owner(s) |\n|---:|---|\n");
        for (Map.Entry<String, Integer> entry : top.subList(0, Math.min(top.size(), 15))) {
            out.append("| ").append(entry.getValue()).append(" | `").append(entry.getKey()).append("` |\n");
        }
        out.append('\n');
    }

    /**
     * A drift's proposal changes ownership when it does not simply keep the first-publisher owner:
     * either the implicit owner is dropped (reassignment) or other owners are added (widening).
     */
    private static boolean changesOwnership(Drift drift) {
        List<String> allowed = drift.allowed();
        return !(allowed.size() == 1 && allowed.get(0).equals(drift.owner().groupId));
    }

    /**
     * For a resolved module, the {@link Reassignment} describing how its owners.tsv owners differ
     * from the implicit first-publisher owner, or {@code null} when they do not (owners.tsv merely
     * confirms the first publisher, or rejects everything).
     */
    private static Reassignment reassignment(String module, String implicitOwner, OwnersPolicy owners) {
        Set<String> allowed = new TreeSet<>(owners.allowedGroups());
        for (String pair : owners.allowedPairs()) {
            allowed.add(pair.substring(0, pair.indexOf('\t')));
        }
        if (allowed.isEmpty() || (allowed.size() == 1 && allowed.contains(implicitOwner))) {
            return null;
        }
        boolean widened = allowed.contains(implicitOwner);
        // A widening only counts when the allowed owners span more than one project: a single
        // project published under several coordinates is one legal owner, not many.
        if (widened && !spansMultipleProjects(allowed)) {
            return null;
        }
        return new Reassignment(module, implicitOwner, new ArrayList<>(allowed), widened);
    }

    private static boolean spansMultipleProjects(Set<String> groups) {
        List<String> list = new ArrayList<>(groups);
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (!sameProject(list.get(i), list.get(j))) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void appendReassigned(StringBuilder out, List<Reassignment> reassigned) {
        if (reassigned.isEmpty()) {
            return;
        }
        long reassignCount = reassigned.stream().filter(entry -> !entry.widened()).count();
        long widenCount = reassigned.size() - reassignCount;
        out.append("## Reassigned and widened ownership\n\n");
        out.append("Modules whose resolved owner differs from the implicit first-publisher owner once ")
                .append("`owners.tsv` is applied. ").append(REASSIGN_EMOJI).append(" reassigned (").append(reassignCount)
                .append("): the first publisher was replaced by a different owner. ").append(WIDEN_EMOJI)
                .append(" widened (").append(widenCount).append("): extra legal owners were allowed alongside the ")
                .append("first publisher (e.g. a groupId migration or a co-maintained project). Modules where ")
                .append("`owners.tsv` only confirms the first publisher are not listed. Submodules that share the ")
                .append("same transition are collapsed into a single `prefix.*` row carrying the module count.\n\n");

        // Group modules that share the exact same transition (same direction, implicit owner and
        // resolved owners); collapse each group's shared leading dot-prefix into one wildcard row.
        Map<String, List<Reassignment>> groups = new LinkedHashMap<>();
        for (Reassignment entry : reassigned) {
            String key = (entry.widened() ? "1" : "0") + ' ' + entry.implicitOwner()
                    + ' ' + String.join(",", entry.owners());
            groups.computeIfAbsent(key, k -> new ArrayList<>()).add(entry);
        }
        List<ReassignRow> rows = new ArrayList<>();
        for (List<Reassignment> group : groups.values()) {
            Reassignment any = group.getFirst();
            String owners = String.join(", ", any.owners());
            String prefix = group.size() == 1 ? "" : commonDotPrefix(group.stream().map(Reassignment::module).toList());
            if (prefix.isEmpty()) {
                for (Reassignment entry : group) {
                    rows.add(new ReassignRow(entry.widened(), entry.module(), 1, entry.implicitOwner(), owners));
                }
            } else {
                rows.add(new ReassignRow(any.widened(), prefix + ".*", group.size(), any.implicitOwner(), owners));
            }
        }
        rows.sort(Comparator.comparing(ReassignRow::widened).thenComparing(ReassignRow::label));

        out.append("```\n");
        for (ReassignRow row : rows) {
            out.append(row.widened() ? WIDEN_EMOJI : REASSIGN_EMOJI).append("  ").append(row.label());
            if (row.count() > 1) {
                out.append(" (").append(row.count()).append(" modules)");
            }
            out.append("  ").append(row.implicitOwner()).append(" -> ").append(row.owners()).append('\n');
        }
        out.append("```\n\n");
    }

    private record ReassignRow(boolean widened, String label, int count, String implicitOwner, String owners) {
    }

    /** The longest shared leading dot-segment prefix of the given names, or empty when there is none. */
    private static String commonDotPrefix(List<String> names) {
        String[] first = names.getFirst().split("\\.");
        int shared = first.length;
        for (String name : names) {
            String[] parts = name.split("\\.");
            int limit = Math.min(shared, parts.length);
            int i = 0;
            while (i < limit && parts[i].equals(first[i])) {
                i++;
            }
            shared = i;
            if (shared == 0) {
                return "";
            }
        }
        return String.join(".", Arrays.copyOfRange(first, 0, shared));
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

    /** The explicit-rule owner prefixes for a module, or null: the longest matching key in EXPLICIT_OWNERS. */
    private static List<String> explicitOwners(String module) {
        List<String> owners = null;
        int best = -1;
        for (Map.Entry<String, List<String>> rule : EXPLICIT_OWNERS.entrySet()) {
            String prefix = rule.getKey();
            if ((module.equals(prefix) || module.startsWith(prefix + ".")) && prefix.length() > best) {
                best = prefix.length();
                owners = rule.getValue();
            }
        }
        return owners;
    }

    private static boolean matchesAnyPrefix(String groupId, List<String> prefixes) {
        for (String prefix : prefixes) {
            if (groupId.equals(prefix) || groupId.startsWith(prefix + ".")) {
                return true;
            }
        }
        return false;
    }

    /** Module names cannot contain '-', so strip it from groupIds before comparing the two. */
    private static String noHyphen(String value) {
        return value.indexOf('-') < 0 ? value : value.replace("-", "");
    }

    private static boolean under(String module, String groupId) {
        String m = noHyphen(module);
        String g = noHyphen(groupId);
        return m.equals(g) || m.startsWith(g + ".");
    }

    /** True when the groupId with its first {@code drop} segments removed prefixes the module name. */
    private static boolean groupTailPrefixes(String module, String groupId, int drop) {
        int from = 0;
        for (int i = 0; i < drop; i++) {
            int dot = groupId.indexOf('.', from);
            if (dot < 0) {
                return false;
            }
            from = dot + 1;
        }
        String stripped = noHyphen(groupId.substring(from));
        String m = noHyphen(module);
        return !stripped.isEmpty() && (m.equals(stripped) || m.startsWith(stripped + "."));
    }

    /**
     * The owner is the canonical owner when its groupId is the closest to the module name: either
     * the name is strictly under the groupId, or the groupId shares the longest leading-segment
     * prefix with the name. A 3+ segment shared prefix is a project-level group and ties are
     * allowed; a 2 segment shared prefix is only the org, so the owner must be strictly the closest
     * (leaving sibling-project collisions unclassified).
     */
    private static boolean canonicalOwner(String module, Group owner, List<Group> groups) {
        if (under(module, owner.groupId)) {
            return true;
        }
        int ownerAffinity = sharedLeading(module, owner.groupId);
        if (ownerAffinity < 2) {
            return false;
        }
        int otherAffinity = groups.stream().filter(group -> group != owner)
                .mapToInt(group -> sharedLeading(module, group.groupId)).max().orElse(0);
        // 3+ shared segments is a project-level prefix, where ties are fine; 2 shared segments is
        // only the org, so require the owner to be strictly the closest, leaving sibling-project
        // collisions unclassified.
        return ownerAffinity >= 3 ? ownerAffinity >= otherAffinity : ownerAffinity > otherAffinity;
    }

    private static boolean sameProject(String a, String b) {
        String na = noHyphen(a);
        String nb = noHyphen(b);
        return na.startsWith(nb + ".") || nb.startsWith(na + ".") || sharedLeading(a, b) >= 2;
    }

    private static int sharedLeading(String a, String b) {
        String[] as = noHyphen(a).split("\\.");
        String[] bs = noHyphen(b).split("\\.");
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
