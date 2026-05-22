package build.jenesis.modules;

import module java.base;

public final class ScannedStore {

    public static final String LEAF_FILE_NAME = "scanned.tsv";

    private final Path root;
    private final ConcurrentMap<String, NavigableSet<ScannedEntry>> entries;
    private final Set<String> dirty;

    public ScannedStore(Path root) {
        this.root = Objects.requireNonNull(root, "root");
        this.entries = new ConcurrentHashMap<>();
        this.dirty = ConcurrentHashMap.newKeySet();
    }

    public boolean contains(Coordinate coordinate) {
        return contains(coordinate.groupId(), coordinate.artifactId(), coordinate.version(), coordinate.classifier());
    }

    public boolean contains(String groupId, String artifactId, String version, String classifier) {
        NavigableSet<ScannedEntry> groupEntries = entriesFor(groupId);
        return groupEntries.contains(new ScannedEntry(artifactId, version, classifier));
    }

    public void mark(Coordinate coordinate) {
        NavigableSet<ScannedEntry> groupEntries = entriesFor(coordinate.groupId());
        boolean added = groupEntries.add(new ScannedEntry(coordinate.artifactId(), coordinate.version(), coordinate.classifier()));
        if (added) {
            dirty.add(coordinate.groupId());
        }
    }

    public int pendingGroups() {
        return dirty.size();
    }

    public void flush() throws IOException {
        for (String groupId : List.copyOf(dirty)) {
            writeGroup(groupId);
            dirty.remove(groupId);
        }
    }

    public Path pathFor(String groupId) {
        Path path = root;
        for (String segment : groupId.split("\\.", -1)) {
            path = path.resolve(segment);
        }
        return path.resolve(LEAF_FILE_NAME);
    }

    public NavigableSet<ScannedEntry> read(String groupId) {
        return load(groupId);
    }

    private NavigableSet<ScannedEntry> entriesFor(String groupId) {
        return entries.computeIfAbsent(groupId, this::load);
    }

    private NavigableSet<ScannedEntry> load(String groupId) {
        NavigableSet<ScannedEntry> set = new ConcurrentSkipListSet<>(ScannedEntry.COMPARATOR);
        Path file = pathFor(groupId);
        if (!Files.exists(file)) {
            return set;
        }
        try (Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8)) {
            lines.filter(line -> !line.isEmpty()).map(ScannedEntry::parse).forEach(set::add);
        } catch (IOException io) {
            throw new UncheckedIOException("Failed to read " + file, io);
        }
        return set;
    }

    private void writeGroup(String groupId) throws IOException {
        Path file = pathFor(groupId);
        Path parent = file.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        NavigableSet<ScannedEntry> groupEntries = entries.get(groupId);
        if (groupEntries == null) {
            return;
        }
        Path temp = file.resolveSibling(file.getFileName() + ".tmp");
        try (BufferedWriter writer = Files.newBufferedWriter(temp, StandardCharsets.UTF_8)) {
            for (ScannedEntry entry : groupEntries) {
                writer.write(entry.format());
                writer.write('\n');
            }
        }
        try {
            Files.move(temp, file, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (AtomicMoveNotSupportedException atomicNotSupported) {
            Files.move(temp, file, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
