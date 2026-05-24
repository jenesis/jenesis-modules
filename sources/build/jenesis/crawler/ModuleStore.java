package build.jenesis.crawler;

import module java.base;

public final class ModuleStore {

    private final Path root;
    private final Map<StoreKey, NavigableSet<ModuleEntry>> dirty;

    public ModuleStore(Path root) {
        this.root = Objects.requireNonNull(root, "root");
        this.dirty = new HashMap<>();
    }

    public static final String LEAF_FILE_BASE = "versions";
    public static final String CURRENT_FILE_BASE = "current";
    public static final String LEAF_FILE_EXTENSION = ".tsv";
    public static final String OWNERS_FILE = "owners.tsv";

    public static final Comparator<ModuleEntry> CHRONOLOGICAL = Comparator
            .comparingLong(ModuleEntry::publishedAt)
            .thenComparing(ModuleEntry::groupId)
            .thenComparing(ModuleEntry::artifactId)
            .thenComparing(ModuleEntry::version, Comparator.reverseOrder());

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

        public String currentFileName() {
            return classifier == null
                    ? CURRENT_FILE_BASE + LEAF_FILE_EXTENSION
                    : CURRENT_FILE_BASE + '-' + classifier + LEAF_FILE_EXTENSION;
        }
    }

    /**
     * Appends a module declaration to the in-memory buffer for the next flush.
     * Returns true when the entry will be persisted; false only for sentinel
     * (missing) publish timestamps, which are dropped. The owners.tsv allowlist
     * is NOT consulted here - it only governs current.tsv generation. Coordinates
     * that fail policy still land in versions.tsv as part of the audit log.
     */
    public boolean record(String moduleName, ModuleType type, Coordinate coordinate) {
        if (coordinate.lastModified() <= 0L) {
            return false;
        }
        StoreKey key = new StoreKey(moduleName, coordinate.classifier());
        NavigableSet<ModuleEntry> entries = dirty.computeIfAbsent(key, this::loadOrEmpty);
        entries.add(new ModuleEntry(new Version(coordinate.version()), type, coordinate.groupId(), coordinate.artifactId(), coordinate.lastModified()));
        return true;
    }

    public int pendingFiles() {
        return dirty.size();
    }

    public Set<String> pendingModuleNames() {
        Set<String> names = new LinkedHashSet<>();
        for (StoreKey key : dirty.keySet()) {
            names.add(key.moduleName());
        }
        return names;
    }

    /** Flushes the in-memory buffer to versions.tsv files. Does NOT touch current.tsv. */
    public void flush() throws IOException {
        for (Map.Entry<StoreKey, NavigableSet<ModuleEntry>> entry : dirty.entrySet()) {
            writeVersions(entry.getKey(), entry.getValue());
        }
        dirty.clear();
    }

    public Path pathFor(StoreKey key) {
        return moduleDir(key.moduleName()).resolve(key.versionsFileName());
    }

    public Path currentPathFor(StoreKey key) {
        return moduleDir(key.moduleName()).resolve(key.currentFileName());
    }

    public Path ownersPathFor(String moduleName) {
        return moduleDir(moduleName).resolve(OWNERS_FILE);
    }

    public NavigableSet<ModuleEntry> read(String moduleName, String classifier) throws IOException {
        return loadOrEmpty(new StoreKey(moduleName, classifier));
    }

    /**
     * Walks the modules tree and regenerates current[-classifier].tsv for every
     * versions[-classifier].tsv file that doesn't already have a matching current
     * file. The unit of progress is the (module, classifier) pair: each missing
     * current file is independently regenerated, and a crash during the walk
     * leaves the partially-finished module in a fully-recoverable state - the
     * next invocation skips classifier files that finished and resumes from
     * exactly the ones still missing. No in-memory module list or separate
     * progress flag is needed. Returns the number of files actually written
     * (excluding skipped ones and those that resolved to an empty owner set).
     */
    public long regenerateMissing() throws IOException {
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
                Optional<Owners> owners = null;
                for (ClassifierFile classifierFile : versionFiles) {
                    Path currentFile = dir.resolve(currentFileName(classifierFile.classifier()));
                    if (Files.exists(currentFile)) {
                        continue;
                    }
                    if (owners == null) {
                        owners = loadOwners(moduleName);
                    }
                    List<ModuleEntry> versions = readVersionsFile(classifierFile.path());
                    List<CurrentEntry> resolved = resolve(versions, owners);
                    if (resolved.isEmpty()) {
                        // Empty result is the correct steady state (no current file). Don't
                        // create one just to satisfy the marker - the absent-current-on-empty
                        // case re-resolves to the same empty result next run, so it stays
                        // self-consistent without on-disk state.
                        continue;
                    }
                    writeCurrent(currentFile, resolved);
                    count++;
                }
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
     * Rebuilds current[-classifier].tsv files for the given module from its
     * versions.tsv contents intersected with owners.tsv (when present) or the
     * implicit-owner rule (when absent). Existing current.tsv files for the
     * module that no longer have content are deleted.
     */
    public void regenerate(String moduleName) throws IOException {
        if (!isValidModuleName(moduleName)) {
            throw new IllegalArgumentException("Invalid module name: " + moduleName);
        }
        Path dir = moduleDir(moduleName);
        if (!Files.isDirectory(dir)) {
            return;
        }
        Optional<Owners> owners = loadOwners(moduleName);
        for (ClassifierFile classifierFile : listVersionFiles(dir)) {
            List<ModuleEntry> versions = readVersionsFile(classifierFile.path());
            List<CurrentEntry> resolved = resolve(versions, owners);
            Path currentFile = dir.resolve(currentFileName(classifierFile.classifier()));
            if (resolved.isEmpty()) {
                Files.deleteIfExists(currentFile);
            } else {
                writeCurrent(currentFile, resolved);
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

    private static String currentFileName(String classifier) {
        return classifier == null
                ? CURRENT_FILE_BASE + LEAF_FILE_EXTENSION
                : CURRENT_FILE_BASE + '-' + classifier + LEAF_FILE_EXTENSION;
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
     * Apply the resolution rule: filter by owners (or implicit-owner rule), then
     * for each (version) pick the row with the oldest publishedAt - that becomes
     * the canonical current.tsv row.
     */
    private static List<CurrentEntry> resolve(List<ModuleEntry> versions, Optional<Owners> owners) {
        if (versions.isEmpty()) {
            return List.of();
        }
        List<ModuleEntry> allowed;
        if (owners.isPresent()) {
            Owners policy = owners.get();
            allowed = versions.stream()
                    .filter(entry -> policy.allows(entry.groupId(), entry.artifactId()))
                    .toList();
        } else {
            String implicitOwner = versions.stream()
                    .min(Comparator.comparingLong(ModuleEntry::publishedAt).thenComparing(ModuleEntry::groupId))
                    .map(ModuleEntry::groupId)
                    .orElseThrow();
            allowed = versions.stream()
                    .filter(entry -> entry.groupId().equals(implicitOwner))
                    .toList();
        }
        if (allowed.isEmpty()) {
            return List.of();
        }
        Map<String, ModuleEntry> bestByVersion = new LinkedHashMap<>();
        Comparator<ModuleEntry> pickOrder = Comparator
                .comparingLong(ModuleEntry::publishedAt)
                .thenComparing(ModuleEntry::groupId)
                .thenComparing(ModuleEntry::artifactId);
        for (ModuleEntry entry : allowed) {
            bestByVersion.merge(entry.version().raw(), entry,
                    (existing, candidate) -> pickOrder.compare(candidate, existing) < 0 ? candidate : existing);
        }
        List<CurrentEntry> result = bestByVersion.values().stream()
                .map(CurrentEntry::of)
                .sorted(CurrentEntry.NEWEST_FIRST)
                .collect(Collectors.toCollection(ArrayList::new));
        return result;
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

    private static void writeCurrent(Path file, List<CurrentEntry> entries) throws IOException {
        ensureParent(file);
        Path temp = file.resolveSibling(file.getFileName() + ".tmp");
        try (BufferedWriter writer = Files.newBufferedWriter(temp, StandardCharsets.UTF_8)) {
            for (CurrentEntry entry : entries) {
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
