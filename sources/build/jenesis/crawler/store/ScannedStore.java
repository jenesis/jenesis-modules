package build.jenesis.crawler.store;

import module java.base;
import build.jenesis.crawler.model.Coordinate;
import build.jenesis.crawler.model.ScannedEntry;

/**
 * Tracks every coordinate the crawler has ever scanned (whether successfully or with a
 * permanent failure) so the producer's filter can skip it on subsequent runs.
 *
 * Files are laid out one per artifact: {@code data/scanned/<groupId-path>/<artifactId>.tsv}.
 *
 * <p><b>Cache shape.</b> The producer streams ~90 M Maven Central index records per crawl
 * and calls {@link #contains} for every coordinate that passes {@code isInteresting}. The
 * cache below is a bounded LRU keyed by {@code (groupId, artifactId)}. The Maven Central
 * index has locality: a release batch publishes many artifacts of the same group together,
 * and adjacent index chunks publish adjacent versions of the same artifact, so a cache of
 * a few thousand slots covers the producer's working set without thrashing. Without
 * eviction (the earlier shape), the map grew to ~600 K entries holding ~16 M
 * {@link ScannedEntry}s and ~2 GB of skip-list overhead, which combined with String content
 * pushed the long-tail of a full sweep over the 4 GB heap cap on GitHub Actions. Eviction
 * is gated on the {@link #dirty} set: an entry with unflushed marks is never evicted, so
 * the in-memory state always survives until {@link #flush} writes it to disk.
 */
public final class ScannedStore {

    public static final String LEAF_FILE_EXTENSION = ".tsv";
    public static final String PROP_CACHE_SIZE = "jenesis.crawler.scanned.cache.size";
    /**
     * Default cache capacity: tuned to comfortably cover the largest plausible release-batch
     * width plus enough headroom that scanner-thread interleaving doesn't immediately evict
     * an artifact the producer is still iterating. At ~30 entries average size, the cache
     * costs a few MB resident, far below the per-run heap budget.
     */
    public static final int DEFAULT_CACHE_SIZE = 4096;

    public record CacheKey(String groupId, String artifactId) {
        public CacheKey {
            Objects.requireNonNull(groupId, "groupId");
            Objects.requireNonNull(artifactId, "artifactId");
            if (artifactId.isEmpty()) {
                throw new IllegalArgumentException("artifactId must not be empty");
            }
        }
    }

    private record CacheEntry(NavigableSet<ScannedEntry> set, AtomicLong lastAccess) {
        static CacheEntry create(NavigableSet<ScannedEntry> set, long tick) {
            return new CacheEntry(set, new AtomicLong(tick));
        }
    }

    private final Path root;
    private final boolean reprocessFailed;
    private final int cacheSize;
    /**
     * Soft cap: when {@link #entries} grows beyond this watermark, {@link #evict} runs and
     * brings it back down to {@link #cacheSize}. Picking the trigger a bit above the target
     * amortises the eviction sort cost across many puts.
     */
    private final int evictionTrigger;
    private final ConcurrentMap<CacheKey, CacheEntry> entries;
    private final Set<CacheKey> dirty;
    private final AtomicLong tick;
    private final Object evictionLock = new Object();

    public ScannedStore(Path root) {
        this(root, false);
    }

    public ScannedStore(Path root, boolean reprocessFailed) {
        this(root, reprocessFailed, parseCacheSize());
    }

    public ScannedStore(Path root, boolean reprocessFailed, int cacheSize) {
        if (cacheSize < 1) {
            throw new IllegalArgumentException("cacheSize must be >= 1, got " + cacheSize);
        }
        this.root = Objects.requireNonNull(root, "root");
        this.reprocessFailed = reprocessFailed;
        this.cacheSize = cacheSize;
        this.evictionTrigger = cacheSize + Math.max(64, cacheSize / 4);
        this.entries = new ConcurrentHashMap<>();
        this.dirty = ConcurrentHashMap.newKeySet();
        this.tick = new AtomicLong();
    }

    private static int parseCacheSize() {
        String raw = System.getProperty(PROP_CACHE_SIZE);
        if (raw == null || raw.isBlank()) {
            return DEFAULT_CACHE_SIZE;
        }
        try {
            int parsed = Integer.parseInt(raw.trim());
            if (parsed < 1) {
                throw new NumberFormatException();
            }
            return parsed;
        } catch (NumberFormatException invalid) {
            throw new IllegalArgumentException(
                    "Expected a positive integer for " + PROP_CACHE_SIZE + ", got: " + raw);
        }
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
        CacheEntry entry = entries.computeIfAbsent(key,
                k -> CacheEntry.create(load(k), tick.incrementAndGet()));
        entry.lastAccess().set(tick.incrementAndGet());
        if (entries.size() > evictionTrigger) {
            evict();
        }
        return entry.set();
    }

    /**
     * Trim the cache back to {@link #cacheSize} by removing the least-recently-accessed
     * clean entries. Dirty entries (those with unflushed {@code markOk}/{@code markFailed})
     * are skipped so {@link #flush} always finds their in-memory state. The lock serialises
     * evictions so concurrent triggers don't all sort + scan simultaneously; threads that
     * lose the race fall through to the early-out on the next iteration.
     */
    private void evict() {
        synchronized (evictionLock) {
            if (entries.size() <= cacheSize) {
                return;
            }
            List<Map.Entry<CacheKey, CacheEntry>> snapshot = new ArrayList<>(entries.entrySet());
            snapshot.sort(Comparator.comparingLong(e -> e.getValue().lastAccess().get()));
            for (Map.Entry<CacheKey, CacheEntry> e : snapshot) {
                if (entries.size() <= cacheSize) {
                    return;
                }
                if (dirty.contains(e.getKey())) {
                    continue;
                }
                entries.remove(e.getKey(), e.getValue());
            }
        }
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
        CacheEntry cacheEntry = entries.get(key);
        if (cacheEntry == null) {
            return;
        }
        NavigableSet<ScannedEntry> artifactEntries = cacheEntry.set();
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
