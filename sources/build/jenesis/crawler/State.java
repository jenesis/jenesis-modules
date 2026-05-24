package build.jenesis.crawler;

import module java.base;

public record State(long indexChunkLastApplied,
                    long indexChunkPending,
                    long indexTimestamp,
                    String indexChainId,
                    Instant sweepStartedAt) {

    public static final State EMPTY = new State(-1L, -1L, 0L, null, null);

    private static final String KEY_INDEX_CHUNK = "index.lastAppliedChunk";
    private static final String KEY_INDEX_PENDING = "index.pendingChunk";
    private static final String KEY_INDEX_TIMESTAMP = "index.timestamp";
    private static final String KEY_INDEX_CHAIN_ID = "index.chainId";
    private static final String KEY_SWEEP_STARTED = "sweep.startedAt";

    public State withIndex(long chunk, long timestamp, String chainId) {
        return new State(chunk, indexChunkPending, timestamp, chainId, sweepStartedAt);
    }

    public State withIndexChunkPending(long pending) {
        return new State(indexChunkLastApplied, pending, indexTimestamp, indexChainId, sweepStartedAt);
    }

    public State withSweepStartedAt(Instant startedAt) {
        return new State(indexChunkLastApplied, indexChunkPending, indexTimestamp, indexChainId, startedAt);
    }

    public boolean hasPendingFullScan() {
        return indexChunkPending >= 0L;
    }

    public boolean hasIndexBaseline() {
        return indexChainId != null && indexChunkLastApplied >= 0L;
    }

    public static State load(Path path) throws IOException {
        if (!Files.exists(path)) {
            return EMPTY;
        }
        Properties properties = new Properties();
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            properties.load(reader);
        }
        long chunk = parseLong(properties, KEY_INDEX_CHUNK, -1L);
        long pending = parseLong(properties, KEY_INDEX_PENDING, -1L);
        long timestamp = parseLong(properties, KEY_INDEX_TIMESTAMP, 0L);
        String chainId = trimOrNull(properties.getProperty(KEY_INDEX_CHAIN_ID));
        String startedRaw = properties.getProperty(KEY_SWEEP_STARTED);
        Instant startedAt = startedRaw == null || startedRaw.isEmpty() ? null : Instant.parse(startedRaw);
        return new State(chunk, pending, timestamp, chainId, startedAt);
    }

    public void save(Path path) throws IOException {
        Properties properties = new Properties();
        properties.setProperty(KEY_INDEX_CHUNK, Long.toString(indexChunkLastApplied));
        properties.setProperty(KEY_INDEX_PENDING, Long.toString(indexChunkPending));
        properties.setProperty(KEY_INDEX_TIMESTAMP, Long.toString(indexTimestamp));
        if (indexChainId != null) {
            properties.setProperty(KEY_INDEX_CHAIN_ID, indexChainId);
        }
        if (sweepStartedAt != null) {
            properties.setProperty(KEY_SWEEP_STARTED, sweepStartedAt.toString());
        }
        Path parent = path.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        Path temp = path.resolveSibling(path.getFileName() + ".tmp");
        try (BufferedWriter writer = Files.newBufferedWriter(temp, StandardCharsets.UTF_8)) {
            properties.store(writer, "jenesis-modules state");
        }
        try {
            Files.move(temp, path, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (AtomicMoveNotSupportedException atomicNotSupported) {
            Files.move(temp, path, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private static long parseLong(Properties properties, String key, long fallback) {
        String value = properties.getProperty(key);
        if (value == null || value.isEmpty()) {
            return fallback;
        }
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException invalid) {
            return fallback;
        }
    }

    private static String trimOrNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
