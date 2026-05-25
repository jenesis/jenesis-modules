package build.jenesis.crawler.index;

import module java.base;

public record IndexProperties(String chainId, long timestamp, int lastIncremental, int firstRetainedIncremental) {

    public static final String KEY_CHAIN_ID = "nexus.index.chain-id";
    public static final String KEY_TIMESTAMP = "nexus.index.timestamp";
    public static final String KEY_LAST_INCREMENTAL = "nexus.index.last-incremental";
    public static final String KEY_INCREMENTAL_PREFIX = "nexus.index.incremental-";

    public static final DateTimeFormatter TIMESTAMP_FORMAT = new DateTimeFormatterBuilder()
            .appendPattern("yyyyMMddHHmmss")
            .optionalStart().appendLiteral('.').appendValue(ChronoField.MILLI_OF_SECOND, 3).optionalEnd()
            .optionalStart().appendLiteral(' ').appendOffset("+HHMM", "+0000").optionalEnd()
            .toFormatter();

    public static IndexProperties read(InputStream input) throws IOException {
        Properties properties = new Properties();
        properties.load(input);
        String chainId = properties.getProperty(KEY_CHAIN_ID);
        long timestamp = parseTimestamp(properties.getProperty(KEY_TIMESTAMP));
        int lastIncremental = parseInt(properties.getProperty(KEY_LAST_INCREMENTAL), -1);
        // The .properties file lists every retained chunk as nexus.index.incremental-<offset>=<N>,
        // typically 30 entries. We need the smallest N so that after a FULL we know which chunks
        // still need to be applied incrementally - the FULL is generated periodically and lags
        // behind the latest incremental, so chunks newer than the FULL's snapshot point are NOT
        // in the FULL even though the .properties file says "last-incremental=<latest>".
        int firstRetained = lastIncremental;
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            String key = entry.getKey().toString();
            if (!key.startsWith(KEY_INCREMENTAL_PREFIX)) {
                continue;
            }
            int value = parseInt(entry.getValue().toString(), Integer.MAX_VALUE);
            if (value < firstRetained) {
                firstRetained = value;
            }
        }
        return new IndexProperties(chainId, timestamp, lastIncremental, firstRetained);
    }

    public boolean hasIncrementals() {
        return lastIncremental >= 0;
    }

    private static long parseTimestamp(String raw) {
        if (raw == null || raw.isBlank()) {
            return 0L;
        }
        try {
            return OffsetDateTime.parse(raw.trim(), TIMESTAMP_FORMAT).toInstant().toEpochMilli();
        } catch (DateTimeParseException _) {
            try {
                return LocalDateTime.parse(raw.trim(), TIMESTAMP_FORMAT)
                        .toInstant(ZoneOffset.UTC).toEpochMilli();
            } catch (DateTimeParseException stillBad) {
                return 0L;
            }
        }
    }

    private static int parseInt(String value, int fallback) {
        if (value == null || value.isBlank()) {
            return fallback;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException invalid) {
            return fallback;
        }
    }
}
