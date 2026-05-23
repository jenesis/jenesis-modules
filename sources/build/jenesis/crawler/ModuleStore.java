package build.jenesis.crawler;

import module java.base;

public final class ModuleStore {

    private final Path root;
    private final Map<StoreKey, NavigableSet<ModuleEntry>> dirty;
    private final Map<String, Optional<Owners>> ownersCache;

    public ModuleStore(Path root) {
        this.root = Objects.requireNonNull(root, "root");
        this.dirty = new HashMap<>();
        this.ownersCache = new HashMap<>();
    }

    public static final String LEAF_FILE_BASE = "versions";
    public static final String LEAF_FILE_EXTENSION = ".tsv";
    public static final String OWNERS_FILE = "owners.tsv";

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

        public String fileName() {
            return classifier == null
                    ? LEAF_FILE_BASE + LEAF_FILE_EXTENSION
                    : LEAF_FILE_BASE + '-' + classifier + LEAF_FILE_EXTENSION;
        }
    }

    public boolean record(String moduleName, ModuleType type, Coordinate coordinate) {
        StoreKey key = new StoreKey(moduleName, coordinate.classifier());
        if (!isAllowedByOwners(moduleName, coordinate.groupId(), coordinate.artifactId())) {
            return false;
        }
        NavigableSet<ModuleEntry> entries = dirty.computeIfAbsent(key, this::loadOrEmpty);
        entries.add(new ModuleEntry(new Version(coordinate.version()), type, coordinate.groupId(), coordinate.artifactId(), coordinate.lastModified()));
        return true;
    }

    public int pendingFiles() {
        return dirty.size();
    }

    public void flush() throws IOException {
        for (Map.Entry<StoreKey, NavigableSet<ModuleEntry>> entry : dirty.entrySet()) {
            write(entry.getKey(), entry.getValue());
        }
        dirty.clear();
    }

    public Path pathFor(StoreKey key) {
        Path path = root;
        for (String segment : key.segments()) {
            path = path.resolve(segment);
        }
        return path.resolve(key.fileName());
    }

    public Path ownersPathFor(String moduleName) {
        Path path = root;
        for (String segment : new StoreKey(moduleName, null).segments()) {
            path = path.resolve(segment);
        }
        return path.resolve(OWNERS_FILE);
    }

    public NavigableSet<ModuleEntry> read(String moduleName, String classifier) throws IOException {
        return loadOrEmpty(new StoreKey(moduleName, classifier));
    }

    private boolean isAllowedByOwners(String moduleName, String groupId, String artifactId) {
        Optional<Owners> owners = ownersCache.computeIfAbsent(moduleName, this::loadOwners);
        return owners.isEmpty() || owners.get().allows(groupId, artifactId);
    }

    private Optional<Owners> loadOwners(String moduleName) {
        Path file = ownersPathFor(moduleName);
        if (!Files.exists(file)) {
            return Optional.empty();
        }
        Set<String> groups = new HashSet<>();
        Set<String> pairs = new HashSet<>();
        try (Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8)) {
            lines.forEach(rawLine -> {
                String line = rawLine.stripTrailing();
                if (line.isBlank() || line.startsWith("#")) {
                    return;
                }
                int tab = line.indexOf('\t');
                if (tab < 0) {
                    groups.add(line);
                } else if (line.indexOf('\t', tab + 1) >= 0) {
                    throw new IllegalArgumentException("Unexpected extra tab in " + file + ": " + line);
                } else {
                    pairs.add(line);
                }
            });
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read " + file, e);
        }
        return Optional.of(new Owners(Set.copyOf(groups), Set.copyOf(pairs)));
    }

    private NavigableSet<ModuleEntry> loadOrEmpty(StoreKey key) {
        Path file = pathFor(key);
        NavigableSet<ModuleEntry> entries = new TreeSet<>(ModuleEntry.NEWEST_FIRST);
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

    private void write(StoreKey key, NavigableSet<ModuleEntry> entries) throws IOException {
        Path file = pathFor(key);
        Path parent = file.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        Path temp = file.resolveSibling(file.getFileName() + ".tmp");
        try (BufferedWriter writer = Files.newBufferedWriter(temp, StandardCharsets.UTF_8)) {
            for (ModuleEntry entry : entries) {
                writer.write(entry.format());
                writer.newLine();
            }
        }
        try {
            Files.move(temp, file, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (AtomicMoveNotSupportedException atomicNotSupported) {
            Files.move(temp, file, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private record Owners(Set<String> groups, Set<String> pairs) {

        boolean allows(String groupId, String artifactId) {
            return groups.contains(groupId) || pairs.contains(groupId + '\t' + artifactId);
        }
    }
}
