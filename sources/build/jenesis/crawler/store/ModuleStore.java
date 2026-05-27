package build.jenesis.crawler.store;

import module java.base;
import build.jenesis.crawler.model.Coordinate;
import build.jenesis.crawler.model.ArtifactsEntry;
import build.jenesis.crawler.model.ModuleEntry;
import build.jenesis.crawler.model.ModuleType;
import build.jenesis.crawler.model.ModuleVersionEntry;
import build.jenesis.crawler.model.Version;

public final class ModuleStore {

    private final Path root;
    private final Map<StoreKey, NavigableSet<ModuleEntry>> dirty;

    public ModuleStore(Path root) {
        this.root = Objects.requireNonNull(root, "root");
        this.dirty = new HashMap<>();
    }

    public static final String LEAF_FILE_BASE = "versions";
    public static final String ARTIFACTS_FILE_BASE = "artifacts";
    public static final String MODULES_FILE_BASE = "modules";
    public static final String LEAF_FILE_EXTENSION = ".tsv";
    public static final String OWNERS_FILE = "owners.tsv";

    /**
     * Selects which resolved-view files {@link #regenerate(String, Scope)} touches.
     * {@code BOTH} is the default for full re-runs; {@code ARTIFACTS} or {@code MODULES}
     * lets a caller (e.g. the {@code Regenerate} CLI) rebuild one family at a time.
     * Whichever file the scope excludes is left exactly as it was on disk.
     */
    public enum Scope {
        ARTIFACTS, MODULES, BOTH;

        public boolean writesArtifacts() {
            return this != MODULES;
        }

        public boolean writesModules() {
            return this != ARTIFACTS;
        }
    }

    public static final Comparator<ModuleEntry> CHRONOLOGICAL = Comparator
            .comparingLong(ModuleEntry::publishedAt)
            .thenComparing(ModuleEntry::groupId)
            .thenComparing(ModuleEntry::artifactId)
            .thenComparing(ModuleEntry::mavenVersion, Comparator.reverseOrder());

    public static boolean isValidModuleName(String moduleName) {
        if (moduleName == null || moduleName.isEmpty()) {
            return false;
        }
        try {
            ModuleDescriptor.newModule(moduleName);
            return true;
        } catch (IllegalArgumentException invalid) {
            return false;
        }
    }

    public record StoreKey(String moduleName, String classifier) {

        public StoreKey {
            Objects.requireNonNull(moduleName, "moduleName");
            if (!isValidModuleName(moduleName)) {
                throw new IllegalArgumentException("Invalid module name: " + moduleName);
            }
            if (classifier != null && classifier.isEmpty()) {
                throw new IllegalArgumentException("classifier must be null or non-empty");
            }
        }

        public String[] segments() {
            return moduleName.split("\\.", -1);
        }

        public String versionsFileName() {
            return classifier == null
                    ? LEAF_FILE_BASE + LEAF_FILE_EXTENSION
                    : LEAF_FILE_BASE + '-' + classifier + LEAF_FILE_EXTENSION;
        }

        public String artifactsFileName() {
            return classifier == null
                    ? ARTIFACTS_FILE_BASE + LEAF_FILE_EXTENSION
                    : ARTIFACTS_FILE_BASE + '-' + classifier + LEAF_FILE_EXTENSION;
        }

        public String modulesFileName() {
            return classifier == null
                    ? MODULES_FILE_BASE + LEAF_FILE_EXTENSION
                    : MODULES_FILE_BASE + '-' + classifier + LEAF_FILE_EXTENSION;
        }
    }

    /**
     * Appends a module declaration to the in-memory buffer for the next flush.
     * Returns true when the entry will be persisted; false only for sentinel
     * (missing) publish timestamps, which are dropped. The owners.tsv allowlist
     * is NOT consulted here - it only governs resolved-view generation. Coordinates
     * that fail policy still land in versions.tsv as part of the audit log.
     *
     * <p>The {@code moduleVersion} parameter carries the raw version string from
     * the JAR's module-info ({@code ModuleDescriptor.rawVersion()}), or
     * {@code null} when module-info declared no version (or no module-info exists
     * at all, as with automatic modules). The caller has scanned the artifact,
     * so the resulting row is always written in the post-feature format: a
     * {@code null} {@code moduleVersion} produces a row whose trailing column
     * exists but is empty.
     */
    public boolean record(String moduleName, ModuleType type, String moduleVersion, Coordinate coordinate) {
        if (coordinate.lastModified() <= 0L) {
            return false;
        }
        StoreKey key = new StoreKey(moduleName, coordinate.classifier());
        NavigableSet<ModuleEntry> entries = dirty.computeIfAbsent(key, this::loadOrEmpty);
        String moduleVersionField = moduleVersion == null ? "" : moduleVersion;
        entries.add(new ModuleEntry(new Version(coordinate.version()), type, coordinate.groupId(), coordinate.artifactId(), coordinate.lastModified(), moduleVersionField));
        return true;
    }

    public int pendingFiles() {
        return dirty.size();
    }

    /** Flushes the in-memory buffer to versions.tsv files. Does NOT touch the resolved views. */
    public void flush() throws IOException {
        for (Map.Entry<StoreKey, NavigableSet<ModuleEntry>> entry : dirty.entrySet()) {
            writeVersions(entry.getKey(), entry.getValue());
        }
        dirty.clear();
    }

    public Path pathFor(StoreKey key) {
        return moduleDir(key.moduleName()).resolve(key.versionsFileName());
    }

    public Path ownersPathFor(String moduleName) {
        return moduleDir(moduleName).resolve(OWNERS_FILE);
    }

    public NavigableSet<ModuleEntry> read(String moduleName, String classifier) throws IOException {
        return loadOrEmpty(new StoreKey(moduleName, classifier));
    }

    /**
     * Walks the modules tree and regenerates {@code artifacts[-classifier].tsv} and
     * {@code modules[-classifier].tsv} for every module that has a {@code versions.tsv}.
     * Called at the end of a first-pass FULL crawl so existing modules whose audit log
     * gained rows during the pass have their resolved views refreshed, not just the
     * brand-new modules whose {@code artifacts.tsv} is missing. The unit of progress is
     * the module: each {@code regenerate} writes atomically (temp-file + rename) and is
     * idempotent, so a crash mid-walk just causes the next run to re-walk and the same
     * outputs to be re-produced. Returns the number of {@link #regenerate(String)}
     * invocations.
     */
    public long regenerateAll() throws IOException {
        if (!Files.isDirectory(root)) {
            return 0L;
        }
        long count = 0L;
        try (Stream<Path> stream = Files.walk(root)) {
            for (Path dir : (Iterable<Path>) stream::iterator) {
                if (dir.equals(root) || !Files.isDirectory(dir)) {
                    continue;
                }
                List<ClassifierFile> versionFiles = listVersionFiles(dir);
                if (versionFiles.isEmpty()) {
                    continue;
                }
                String moduleName = pathToModuleName(dir);
                if (!isValidModuleName(moduleName)) {
                    continue;
                }
                regenerate(moduleName);
                count++;
            }
        }
        return count;
    }

    private String pathToModuleName(Path dir) {
        Path relative = root.relativize(dir);
        StringBuilder builder = new StringBuilder();
        for (Path part : relative) {
            if (builder.length() > 0) {
                builder.append('.');
            }
            builder.append(part.toString());
        }
        return builder.toString();
    }

    /**
     * Rebuilds {@code artifacts[-classifier].tsv} and {@code modules[-classifier].tsv} files
     * for the given module from its {@code versions.tsv} contents intersected with
     * {@code owners.tsv} (when present) or the implicit-owner rule (when absent). Existing
     * output files that no longer have content are deleted.
     *
     * <p>{@code artifacts.tsv} carries the per-Maven-version resolution (one row per Maven
     * version that survives the owners filter). {@code modules.tsv} carries the per-module-version
     * resolution (one row per declared {@code module-info} version, falling back to the Maven
     * version when {@code module-info} declared none), with oldest-{@code publishedAt} winning
     * any module-version collision so the mapping is stable across runs even though Maven
     * doesn't require module versions to be unique. Only named-module rows feed {@code modules.tsv}:
     * if the resolved owner publishes only automatic modules, no {@code modules.tsv} is written
     * (and any existing one is removed).
     */
    public void regenerate(String moduleName) throws IOException {
        regenerate(moduleName, Scope.BOTH);
    }

    /**
     * Same as {@link #regenerate(String)}, but only writes the resolved views selected by
     * {@code scope}. The other family is left untouched on disk (no read, no delete, no
     * temp-file write), which lets the {@code Regenerate} CLI rebuild one side of the
     * catalogue without disturbing the other.
     */
    public void regenerate(String moduleName, Scope scope) throws IOException {
        if (!isValidModuleName(moduleName)) {
            throw new IllegalArgumentException("Invalid module name: " + moduleName);
        }
        Objects.requireNonNull(scope, "scope");
        Path dir = moduleDir(moduleName);
        if (!Files.isDirectory(dir)) {
            return;
        }
        Optional<Owners> owners = loadOwners(moduleName);
        for (ClassifierFile classifierFile : listVersionFiles(dir)) {
            List<ModuleEntry> versions = readVersionsFile(classifierFile.path());
            String classifier = classifierFile.classifier();
            if (scope.writesArtifacts()) {
                List<ArtifactsEntry> artifacts = resolve(versions, owners);
                Path artifactsFile = dir.resolve(artifactsFileName(classifier));
                if (artifacts.isEmpty()) {
                    Files.deleteIfExists(artifactsFile);
                } else {
                    writeArtifacts(artifactsFile, artifacts);
                }
            }
            if (scope.writesModules()) {
                List<ModuleVersionEntry> modules = resolveModules(versions, owners);
                Path modulesFile = dir.resolve(modulesFileName(classifier));
                if (modules.isEmpty()) {
                    Files.deleteIfExists(modulesFile);
                } else {
                    writeModules(modulesFile, modules);
                }
            }
        }
    }

    private Path moduleDir(String moduleName) {
        Path path = root;
        for (String segment : new StoreKey(moduleName, null).segments()) {
            path = path.resolve(segment);
        }
        return path;
    }

    private static String artifactsFileName(String classifier) {
        return classifier == null
                ? ARTIFACTS_FILE_BASE + LEAF_FILE_EXTENSION
                : ARTIFACTS_FILE_BASE + '-' + classifier + LEAF_FILE_EXTENSION;
    }

    private static String modulesFileName(String classifier) {
        return classifier == null
                ? MODULES_FILE_BASE + LEAF_FILE_EXTENSION
                : MODULES_FILE_BASE + '-' + classifier + LEAF_FILE_EXTENSION;
    }

    private record ClassifierFile(String classifier, Path path) {
    }

    private static List<ClassifierFile> listVersionFiles(Path moduleDir) throws IOException {
        List<ClassifierFile> result = new ArrayList<>();
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(moduleDir)) {
            for (Path entry : entries) {
                if (!Files.isRegularFile(entry)) {
                    continue;
                }
                String name = entry.getFileName().toString();
                if (!name.endsWith(LEAF_FILE_EXTENSION)) {
                    continue;
                }
                String stem = name.substring(0, name.length() - LEAF_FILE_EXTENSION.length());
                if (stem.equals(LEAF_FILE_BASE)) {
                    result.add(new ClassifierFile(null, entry));
                } else if (stem.startsWith(LEAF_FILE_BASE + '-')) {
                    result.add(new ClassifierFile(stem.substring(LEAF_FILE_BASE.length() + 1), entry));
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

    /**
     * Apply the resolution rule for {@code modules.tsv}: same owner filtering as
     * {@link #resolve}, then drop any named row whose {@code module-info} version is
     * non-empty AND semantically differs from the Maven coordinate version (so consumers
     * never see a module version that contradicts the Maven version that delivered it).
     * Of the survivors, only named-module rows count (automatic modules carry no
     * {@code module-info} version to key on), and for each distinct module version the
     * row with the oldest {@code publishedAt} wins. Returns an empty list when no named
     * rows survive, which the caller uses to delete any existing {@code modules.tsv}.
     *
     * <p>The mismatch filter runs <em>after</em> owner resolution (implicit or
     * {@code owners.tsv}-driven) so a module whose implicit-owner candidate publishes
     * only mismatching rows still loses its {@code modules.tsv} rather than silently
     * handing ownership to a runner-up groupId.
     */
    private static List<ModuleVersionEntry> resolveModules(List<ModuleEntry> versions, Optional<Owners> owners) {
        if (versions.isEmpty()) {
            return List.of();
        }
        List<ModuleEntry> allowed = applyOwners(versions, owners);
        if (allowed.isEmpty()) {
            return List.of();
        }
        Comparator<ModuleEntry> pickOrder = Comparator
                .comparingLong(ModuleEntry::publishedAt)
                .thenComparing(ModuleEntry::groupId)
                .thenComparing(ModuleEntry::artifactId);
        Map<String, ModuleEntry> bestByModuleVersion = new LinkedHashMap<>();
        for (ModuleEntry entry : allowed) {
            if (entry.type() != ModuleType.NAMED) {
                continue;
            }
            String moduleVersion = entry.moduleVersion();
            if (!moduleVersion.isEmpty() && !new Version(moduleVersion).equals(entry.mavenVersion())) {
                continue;
            }
            String moduleVersionKey = moduleVersion.isEmpty()
                    ? entry.mavenVersion().raw()
                    : moduleVersion;
            bestByModuleVersion.merge(moduleVersionKey, entry,
                    (existing, candidate) -> pickOrder.compare(candidate, existing) < 0 ? candidate : existing);
        }
        return bestByModuleVersion.values().stream()
                .map(ModuleVersionEntry::of)
                .sorted(ModuleVersionEntry.NEWEST_FIRST)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Apply the resolution rule: filter by owners (or implicit-owner rule), then
     * for each (Maven version) pick the row with the oldest publishedAt - that becomes
     * the canonical {@code artifacts.tsv} row.
     */
    private static List<ArtifactsEntry> resolve(List<ModuleEntry> versions, Optional<Owners> owners) {
        if (versions.isEmpty()) {
            return List.of();
        }
        List<ModuleEntry> allowed = applyOwners(versions, owners);
        if (allowed.isEmpty()) {
            return List.of();
        }
        Map<String, ModuleEntry> bestByVersion = new LinkedHashMap<>();
        Comparator<ModuleEntry> pickOrder = Comparator
                .comparingLong(ModuleEntry::publishedAt)
                .thenComparing(ModuleEntry::groupId)
                .thenComparing(ModuleEntry::artifactId);
        for (ModuleEntry entry : allowed) {
            bestByVersion.merge(entry.mavenVersion().raw(), entry,
                    (existing, candidate) -> pickOrder.compare(candidate, existing) < 0 ? candidate : existing);
        }
        List<ArtifactsEntry> result = bestByVersion.values().stream()
                .map(ArtifactsEntry::of)
                .sorted(ArtifactsEntry.NEWEST_FIRST)
                .collect(Collectors.toCollection(ArrayList::new));
        return result;
    }

    /**
     * Filter {@code versions} down to the rows allowed by the policy: when {@code owners}
     * is present, only rows whose {@code (groupId, artifactId)} the policy admits; when
     * absent, the implicit-owner rule applies - the {@code groupId} that first published
     * the module wins, and only its rows are kept.
     */
    private static List<ModuleEntry> applyOwners(List<ModuleEntry> versions, Optional<Owners> owners) {
        if (owners.isPresent()) {
            Owners policy = owners.get();
            return versions.stream()
                    .filter(entry -> policy.allows(entry.groupId(), entry.artifactId()))
                    .toList();
        }
        String implicitOwner = versions.stream()
                .min(Comparator.comparingLong(ModuleEntry::publishedAt).thenComparing(ModuleEntry::groupId))
                .map(ModuleEntry::groupId)
                .orElseThrow();
        return versions.stream()
                .filter(entry -> entry.groupId().equals(implicitOwner))
                .toList();
    }

    private Optional<Owners> loadOwners(String moduleName) throws IOException {
        Path file = ownersPathFor(moduleName);
        if (!Files.exists(file)) {
            return Optional.empty();
        }
        Set<String> groups = new HashSet<>();
        Set<String> pairs = new HashSet<>();
        try (Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8)) {
            for (String rawLine : (Iterable<String>) lines::iterator) {
                String line = rawLine.stripTrailing();
                if (line.isBlank() || line.startsWith("#")) {
                    continue;
                }
                int tab = line.indexOf('\t');
                if (tab < 0) {
                    groups.add(line);
                } else if (line.indexOf('\t', tab + 1) >= 0) {
                    throw new IllegalArgumentException("Unexpected extra tab in " + file + ": " + line);
                } else {
                    pairs.add(line);
                }
            }
        }
        return Optional.of(new Owners(Set.copyOf(groups), Set.copyOf(pairs)));
    }

    private NavigableSet<ModuleEntry> loadOrEmpty(StoreKey key) {
        Path file = pathFor(key);
        NavigableSet<ModuleEntry> entries = new TreeSet<>(CHRONOLOGICAL);
        if (!Files.exists(file)) {
            return entries;
        }
        try (Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8)) {
            lines.filter(line -> !line.isEmpty()).map(ModuleEntry::parse).forEach(entries::add);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read " + file, e);
        }
        return entries;
    }

    private void writeVersions(StoreKey key, NavigableSet<ModuleEntry> entries) throws IOException {
        Path file = pathFor(key);
        ensureParent(file);
        Path temp = file.resolveSibling(file.getFileName() + ".tmp");
        try (BufferedWriter writer = Files.newBufferedWriter(temp, StandardCharsets.UTF_8)) {
            for (ModuleEntry entry : entries) {
                writer.write(entry.format());
                writer.newLine();
            }
        }
        atomicMove(temp, file);
    }

    private static void writeArtifacts(Path file, List<ArtifactsEntry> entries) throws IOException {
        ensureParent(file);
        Path temp = file.resolveSibling(file.getFileName() + ".tmp");
        try (BufferedWriter writer = Files.newBufferedWriter(temp, StandardCharsets.UTF_8)) {
            for (ArtifactsEntry entry : entries) {
                writer.write(entry.format());
                writer.newLine();
            }
        }
        atomicMove(temp, file);
    }

    private static void writeModules(Path file, List<ModuleVersionEntry> entries) throws IOException {
        ensureParent(file);
        Path temp = file.resolveSibling(file.getFileName() + ".tmp");
        try (BufferedWriter writer = Files.newBufferedWriter(temp, StandardCharsets.UTF_8)) {
            for (ModuleVersionEntry entry : entries) {
                writer.write(entry.format());
                writer.newLine();
            }
        }
        atomicMove(temp, file);
    }

    private static void ensureParent(Path file) throws IOException {
        Path parent = file.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
    }

    private static void atomicMove(Path source, Path target) throws IOException {
        try {
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (AtomicMoveNotSupportedException atomicNotSupported) {
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private record Owners(Set<String> groups, Set<String> pairs) {

        boolean allows(String groupId, String artifactId) {
            return groups.contains(groupId) || pairs.contains(groupId + '\t' + artifactId);
        }
    }
}
