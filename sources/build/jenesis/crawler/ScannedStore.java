package build.jenesis.crawler;

import module java.base;

public final class ScannedStore {

    public static final String LEAF_FILE_NAME = "scanned.tsv";

    private final Path root;
    private final boolean reprocessFailed;
    private final ConcurrentMap<String, NavigableSet<ScannedEntry>> entries;
    private final Set<String> dirty;

    public ScannedStore(Path root) {
        this(root, false);
    }

    public ScannedStore(Path root, boolean reprocessFailed) {
        this.root = Objects.requireNonNull(root, "root");
        this.reprocessFailed = reprocessFailed;
        this.entries = new ConcurrentHashMap<>();
        this.dirty = ConcurrentHashMap.newKeySet();
    }

    public boolean contains(Coordinate coordinate) {
        return contains(coordinate.groupId(), coordinate.artifactId(), coordinate.version(), coordinate.classifier());
    }

    public boolean contains(String groupId, String artifactId, String version, String classifier) {
        NavigableSet<ScannedEntry> groupEntries = entriesFor(groupId);
        ScannedEntry probe = ScannedEntry.ok(artifactId, version, classifier);
        // COMPARATOR ignores errorMessage, so floor() finds any entry matching the coordinate.
        ScannedEntry found = groupEntries.floor(probe);
        if (found == null || ScannedEntry.COMPARATOR.compare(found, probe) != 0) {
            return false;
        }
        if (!found.isFailed()) {
            return true;
        }
        // Failed entries skip future fetches unless reprocessFailed=true, in which case
        // the producer treats them as un-scanned and the coordinate is queued again.
        return !reprocessFailed;
    }

    /** Records that a coordinate was scanned successfully. */
    public void markOk(Coordinate coordinate) {
        replaceEntry(coordinate, ScannedEntry.ok(coordinate.artifactId(), coordinate.version(), coordinate.classifier()));
    }

    /**
     * Records that a coordinate failed scanning in a way that is intrinsic to the artifact
     * (not a transient network problem). The error message is stored verbatim on the row
     * after tab/newline sanitisation. Future runs will skip the coordinate unless the
     * reprocess-failed flag is set.
     */
    public void markFailed(Coordinate coordinate, String errorMessage) {
        replaceEntry(coordinate, ScannedEntry.failed(coordinate.artifactId(), coordinate.version(), coordinate.classifier(), errorMessage));
    }

    private void replaceEntry(Coordinate coordinate, ScannedEntry newEntry) {
        NavigableSet<ScannedEntry> groupEntries = entriesFor(coordinate.groupId());
        boolean changed;
        synchronized (groupEntries) {
            ScannedEntry existing = groupEntries.floor(newEntry);
            if (existing != null && ScannedEntry.COMPARATOR.compare(existing, newEntry) == 0) {
                if (existing.equals(newEntry)) {
                    return;
                }
                groupEntries.remove(existing);
            }
            changed = groupEntries.add(newEntry);
        }
        if (changed) {
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
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read " + file, e);
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
