package build.jenesis.crawler.model;

import module java.base;

/**
 * Row of {@code data/scanned/<groupId-path>/<artifactId>.tsv}.
 *
 * <p>Four tab-separated columns: {@code version}, {@code classifier-or-empty},
 * {@code publishedAt-iso-or-empty}, {@code errorMessage-or-empty}. The {@code artifactId} that
 * this row belongs to lives in the file name; storing it again on every row would be redundant.
 *
 * <p>{@code publishedAt} carries the artifact's publish timestamp (epoch ms) as it was known
 * to the scanner that recorded the row, formatted as ISO 8601 UTC seconds for readability.
 * It's the third column so non-modular publications can be bucketed by publish month
 * downstream, without having to cross-reference {@code versions.tsv}. An empty timestamp slot
 * parses as zero (used for rows whose publish time could not be resolved, e.g. permanent
 * failures).
 *
 * <p>The fourth column carries the recorded failure text (sanitised so it stays on one line)
 * when scanning the coordinate threw a permanent error; an empty fourth column means the scan
 * succeeded.
 */
public record ScannedEntry(String version, String classifier, long publishedAt, String errorMessage) {

    private static final DateTimeFormatter ISO_UTC_SECONDS = DateTimeFormatter
            .ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
            .withZone(ZoneOffset.UTC);

    public static final Comparator<ScannedEntry> COMPARATOR = Comparator
            .comparing(ScannedEntry::version)
            .thenComparing(entry -> entry.classifier() == null ? "" : entry.classifier());

    public ScannedEntry {
        Objects.requireNonNull(version, "version");
        Objects.requireNonNull(errorMessage, "errorMessage");
        if (version.isEmpty()) {
            throw new IllegalArgumentException("version must not be empty");
        }
        if (classifier != null && classifier.isEmpty()) {
            throw new IllegalArgumentException("classifier must be null or non-empty");
        }
        if (publishedAt < 0L) {
            throw new IllegalArgumentException("publishedAt must be >= 0, got: " + publishedAt);
        }
        if (errorMessage.indexOf('\t') >= 0 || errorMessage.indexOf('\n') >= 0 || errorMessage.indexOf('\r') >= 0) {
            throw new IllegalArgumentException("errorMessage must not contain tab or newline characters; call sanitize() first");
        }
    }

    /** Convenience: probe-shaped entry whose timestamp is unknown (zero). */
    public static ScannedEntry ok(String version, String classifier) {
        return ok(version, classifier, 0L);
    }

    public static ScannedEntry ok(String version, String classifier, long publishedAt) {
        return new ScannedEntry(version, classifier, publishedAt, "");
    }

    /** Convenience: probe-shaped entry whose timestamp is unknown (zero). */
    public static ScannedEntry failed(String version, String classifier, String errorMessage) {
        return failed(version, classifier, 0L, errorMessage);
    }

    public static ScannedEntry failed(String version, String classifier, long publishedAt, String errorMessage) {
        return new ScannedEntry(version, classifier, publishedAt, sanitize(errorMessage));
    }

    public boolean isFailed() {
        return !errorMessage.isEmpty();
    }

    public String format() {
        return version + '\t'
                + (classifier == null ? "" : classifier) + '\t'
                + (publishedAt > 0L ? ISO_UTC_SECONDS.format(Instant.ofEpochMilli(publishedAt)) : "") + '\t'
                + errorMessage;
    }

    /**
     * Parses a TSV line in the four-column shape
     * ({@code version, classifier, publishedAt, errorMessage}). An empty {@code publishedAt}
     * column yields {@code 0}.
     */
    public static ScannedEntry parse(String line) {
        String[] parts = line.split("\t", -1);
        if (parts.length != 4) {
            throw new IllegalArgumentException("Expected 4 tab-separated fields in scanned entry: " + line);
        }
        long publishedAt = parts[2].isEmpty()
                ? 0L
                : Instant.from(ISO_UTC_SECONDS.parse(parts[2])).toEpochMilli();
        return new ScannedEntry(
                parts[0],
                parts[1].isEmpty() ? null : parts[1],
                publishedAt,
                parts[3]);
    }

    /** Replace tabs and newlines so the message fits on one TSV line. */
    public static String sanitize(String message) {
        if (message == null || message.isEmpty()) {
            return "";
        }
        StringBuilder builder = new StringBuilder(message.length());
        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            builder.append(c == '\t' || c == '\n' || c == '\r' ? ' ' : c);
        }
        return builder.toString();
    }
}
