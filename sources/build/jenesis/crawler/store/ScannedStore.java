package build.jenesis.crawler.store;

import module java.base;
import build.jenesis.crawler.model.Coordinate;
import build.jenesis.crawler.model.ScannedEntry;

/**
 * Tracks every coordinate the crawler has ever scanned (whether successfully or with a
 * permanent failure) so the producer's filter can skip it on subsequent runs.
 *
 * Files are laid out one per artifact: {@code data/scanned/<groupId-path>/<artifactId>.tsv}.
 * The earlier per-group {@code scanned.tsv} layout collapsed the whole group into a single
 * file, which on prolific groups (notably {@code software.amazon.awssdk} - 470 K coordinates
 * across 445 artifacts) meant that any single {@link #contains}/{@link #markOk} call had to
 * pull a 175 MB {@code NavigableSet} into the heap, repeatedly, every time the producer or
 * a worker thread touched the group. {@link #load} on the per-artifact layout reads a few
 * thousand rows at worst (the biggest individual artifact, {@code bundle-sdk}, has ~2000
 * versions = ~30 KB on disk, ~750 KB resident).
 */
public final class ScannedStore {

    public static final String LEAF_FILE_EXTENSION = ".tsv";

    public record CacheKey(String groupId, String artifactId) {
        public CacheKey {
            Objects.requireNonNull(groupId, "groupId");
            Objects.requireNonNull(artifactId, "artifactId");
            if (artifactId.isEmpty()) {
                throw new IllegalArgumentException("artifactId must not be empty");
            }
        }
    }

    private final Path root;
    private final boolean reprocessFailed;
    // Cache of (group, artifact) -> entries loaded from disk on first touch. The cache grows
    // monotonically over a run. With per-artifact files the natural ceiling is small:
    // ~50 bytes/entry * total touched coordinates. For a full Maven Central sweep this is at
    // most a few hundred MB - well under any sane heap. Eviction is therefore unnecessary;
    // the earlier eviction logic caused load/evict/reload churn that drove OOMs and is gone.
    private final ConcurrentMap<CacheKey, NavigableSet<ScannedEntry>> entries;
    private final Set<CacheKey> dirty;

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
        NavigableSet<ScannedEntry> artifactEntries = entriesFor(groupId, artifactId);
        ScannedEntry probe = ScannedEntry.ok(version, classifier);
        // COMPARATOR ignores errorMessage, so floor() finds any entry matching the coordinate.
        ScannedEntry found = artifactEntries.floor(probe);
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
        replaceEntry(coordinate, ScannedEntry.ok(coordinate.version(), coordinate.classifier()));
    }

    /**
     * Records that a coordinate failed scanning in a way that is intrinsic to the artifact
     * (not a transient network problem). The error message is stored verbatim on the row
     * after tab/newline sanitisation. Future runs will skip the coordinate unless the
     * reprocess-failed flag is set.
     */
    public void markFailed(Coordinate coordinate, String errorMessage) {
        replaceEntry(coordinate, ScannedEntry.failed(coordinate.version(), coordinate.classifier(), errorMessage));
    }

    private void replaceEntry(Coordinate coordinate, ScannedEntry newEntry) {
        CacheKey key = new CacheKey(coordinate.groupId(), coordinate.artifactId());
        NavigableSet<ScannedEntry> artifactEntries = entriesFor(key);
        boolean changed;
        synchronized (artifactEntries) {
            ScannedEntry existing = artifactEntries.floor(newEntry);
            if (existing != null && ScannedEntry.COMPARATOR.compare(existing, newEntry) == 0) {
                if (existing.equals(newEntry)) {
                    return;
                }
                artifactEntries.remove(existing);
            }
            changed = artifactEntries.add(newEntry);
        }
        if (changed) {
            dirty.add(key);
        }
    }

    public int pendingArtifacts() {
        return dirty.size();
    }

    public void flush() throws IOException {
        for (CacheKey key : List.copyOf(dirty)) {
            writeArtifact(key);
            dirty.remove(key);
        }
    }

    public Path pathFor(String groupId, String artifactId) {
        Path path = groupDir(groupId);
        return path.resolve(artifactId + LEAF_FILE_EXTENSION);
    }

    public NavigableSet<ScannedEntry> read(String groupId, String artifactId) {
        return load(new CacheKey(groupId, artifactId));
    }

    private Path groupDir(String groupId) {
        Path path = root;
        for (String segment : groupId.split("\\.", -1)) {
            path = path.resolve(segment);
        }
        return path;
    }

    private NavigableSet<ScannedEntry> entriesFor(String groupId, String artifactId) {
        return entriesFor(new CacheKey(groupId, artifactId));
    }

    private NavigableSet<ScannedEntry> entriesFor(CacheKey key) {
        return entries.computeIfAbsent(key, this::load);
    }

    private NavigableSet<ScannedEntry> load(CacheKey key) {
        Path file = pathFor(key.groupId(), key.artifactId());
        NavigableSet<ScannedEntry> set = new ConcurrentSkipListSet<>(ScannedEntry.COMPARATOR);
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

    private void writeArtifact(CacheKey key) throws IOException {
        Path file = pathFor(key.groupId(), key.artifactId());
        Path parent = file.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        NavigableSet<ScannedEntry> artifactEntries = entries.get(key);
        if (artifactEntries == null) {
            return;
        }
        Path temp = file.resolveSibling(file.getFileName() + ".tmp");
        try (BufferedWriter writer = Files.newBufferedWriter(temp, StandardCharsets.UTF_8)) {
            synchronized (artifactEntries) {
                for (ScannedEntry entry : artifactEntries) {
                    writer.write(entry.format());
                    writer.write('\n');
                }
            }
        }
        try {
            Files.move(temp, file, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (AtomicMoveNotSupportedException atomicNotSupported) {
            Files.move(temp, file, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
