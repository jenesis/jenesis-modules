package build.jenesis.crawler;

import module java.base;

public record IndexProperties(String chainId, long timestamp, int lastIncremental) {

    public static final String KEY_CHAIN_ID = "nexus.index.chain-id";
    public static final String KEY_TIMESTAMP = "nexus.index.timestamp";
    public static final String KEY_LAST_INCREMENTAL = "nexus.index.last-incremental";

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
        return new IndexProperties(chainId, timestamp, lastIncremental);
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
        } catch (DateTimeParseException ignored) {
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
