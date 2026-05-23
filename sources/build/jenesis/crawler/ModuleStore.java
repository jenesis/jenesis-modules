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
    public static final String LEAF_FILE_EXTENSION = ".tsv";

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

    public void record(String moduleName, ModuleType type, Coordinate coordinate) {
        StoreKey key = new StoreKey(moduleName, coordinate.classifier());
        NavigableSet<ModuleEntry> entries = dirty.computeIfAbsent(key, this::loadOrEmpty);
        entries.add(new ModuleEntry(new Version(coordinate.version()), type, coordinate.groupArtifact()));
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

    public NavigableSet<ModuleEntry> read(String moduleName, String classifier) throws IOException {
        return loadOrEmpty(new StoreKey(moduleName, classifier));
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
}
