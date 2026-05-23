package build.jenesis.crawler;

import module java.base;

public final class ScannedStore {

    public static final String LEAF_FILE_NAME = "scanned.tsv";

    private final Path root;
    private final boolean reprocessFailed;
    // Cache of group->entries loaded from disk on first touch. Evicted in flush() so retained
    // memory stays bounded by what's been touched since the last checkpoint, not by the lifetime
    // of the JVM. If this read-through cache ever becomes the bottleneck (CPU for the reloads,
    // or memory pressure within a single checkpoint window because a checkpoint touches a huge
    // number of large groups), the next step is to swap it for a coordinate-keyed bloom filter
    // for contains()/markOk fast-path lookups, with an append-only on-disk log and periodic
    // compaction. That removes the requirement to hold any group's full set in memory at all.
    private final ConcurrentMap<String, NavigableSet<ScannedEntry>> entries;
    private final Set<String> dirty;
    // Reads (contains/markOk/markFailed) take the read lock; flush() takes the write lock so
    // that no live caller holds an evicted set when entries.remove(groupId) runs. Without this
    // barrier, a writer holding the old reference could lose updates that the post-eviction
    // disk file no longer contains.
    private final ReadWriteLock cacheLock;
    // Groups for which we have already emitted the large-group warning during this JVM lifetime.
    // We only want one log line per problematic group per run; subsequent loads stay quiet.
    private final Set<String> warnedLargeGroups;

    // Loading a single group's scanned.tsv past this many entries is suspicious - the current
    // design holds the whole group as a NavigableSet in memory whenever contains()/markOk()
    // runs (for the floor() lookup), so a group of this size is a single allocation that can
    // tip a constrained heap into OOM. The actual threshold is ~150 bytes per entry of
    // resident memory; at 100 K entries that's ~15 MB, which is fine on its own but combined
    // with concurrent loads and other allocations is the kind of spike that historically caused
    // OOMs. The warning is purely informational: nothing about correctness changes when it fires.
    public static final int LARGE_GROUP_WARN_THRESHOLD = 100_000;
    public static final long APPROX_BYTES_PER_ENTRY = 150L;

    public ScannedStore(Path root) {
        this(root, false);
    }

    public ScannedStore(Path root, boolean reprocessFailed) {
        this.root = Objects.requireNonNull(root, "root");
        this.reprocessFailed = reprocessFailed;
        this.entries = new ConcurrentHashMap<>();
        this.dirty = ConcurrentHashMap.newKeySet();
        this.cacheLock = new ReentrantReadWriteLock();
        this.warnedLargeGroups = ConcurrentHashMap.newKeySet();
    }

    public boolean contains(Coordinate coordinate) {
        return contains(coordinate.groupId(), coordinate.artifactId(), coordinate.version(), coordinate.classifier());
    }

    public boolean contains(String groupId, String artifactId, String version, String classifier) {
        cacheLock.readLock().lock();
        try {
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
        } finally {
            cacheLock.readLock().unlock();
        }
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
        cacheLock.readLock().lock();
        try {
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
        } finally {
            cacheLock.readLock().unlock();
        }
    }

    public int cachedGroups() {
        return entries.size();
    }

    public int cachedEntries() {
        int total = 0;
        for (NavigableSet<ScannedEntry> set : entries.values()) {
            total += set.size();
        }
        return total;
    }

    public int pendingGroups() {
        return dirty.size();
    }

    public void flush() throws IOException {
        cacheLock.writeLock().lock();
        try {
            for (String groupId : List.copyOf(dirty)) {
                writeGroup(groupId);
                dirty.remove(groupId);
                // Drop the cached set so retained memory doesn't grow without bound across a
                // long run. Next access reloads from the freshly-written file. The write lock
                // above guarantees no live reader/writer holds a reference at this point.
                entries.remove(groupId);
            }
        } finally {
            cacheLock.writeLock().unlock();
        }
    }

    /**
     * Drop cached group entries that have no pending writes. The producer side calls this
     * periodically because its {@link #contains} check loads groups into the cache much faster
     * than the consumer's flush rate can evict them; without an explicit cleanup, an all-skipped
     * pass through a large index (~770 K records) accumulates the cache to the point of OOM
     * before any checkpoint fires. Dirty groups are kept because evicting them would lose writes
     * that have not yet reached disk.
     */
    public void evictIdle() {
        cacheLock.writeLock().lock();
        try {
            for (String groupId : List.copyOf(entries.keySet())) {
                if (!dirty.contains(groupId)) {
                    entries.remove(groupId);
                }
            }
        } finally {
            cacheLock.writeLock().unlock();
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
        if (set.size() >= LARGE_GROUP_WARN_THRESHOLD && warnedLargeGroups.add(groupId)) {
            long approxMb = ((long) set.size() * APPROX_BYTES_PER_ENTRY) / (1024L * 1024L);
            System.err.println("[scanned-store] WARNING: group '" + groupId + "' already contains "
                    + set.size() + " scanned entries (estimated ~" + approxMb
                    + " MB resident when loaded). Every contains() and markOk() call on this group"
                    + " loads the full scanned.tsv into a NavigableSet (the floor() lookup that"
                    + " backs the existing-coordinate dedup requires the whole set in memory),"
                    + " so a single touch of this group allocates that much at once. Under a heap"
                    + " cap, that single allocation - combined with concurrent loads of other"
                    + " groups and the usual HTTP/parse working set - is the most plausible cause"
                    + " of an OutOfMemoryError. If you see a subsequent OOM whose heap dump is"
                    + " dominated by ScannedEntry / String / ConcurrentSkipListMap.Node, this"
                    + " group is the prime suspect. Mitigation today: raise the JVM heap (-Xmx)."
                    + " Long-term fix: replace the per-group cache with an append-only file plus"
                    + " a per-group bloom filter so contains()/markOk() no longer need the full"
                    + " set in memory. Threshold for this warning: "
                    + LARGE_GROUP_WARN_THRESHOLD + " entries.");
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
