package build.jenesis.crawler.model;

import module java.base;

/**
 * Row of {@code data/scanned/<groupId-path>/<artifactId>.tsv}.
 *
 * Three tab-separated columns: {@code version}, {@code classifier-or-empty},
 * {@code errorMessage-or-empty}. The {@code artifactId} that this row belongs to lives in the
 * file name; storing it again on every row would be redundant. The third column carries the
 * recorded failure text (sanitised so it stays on one line) when scanning the coordinate threw
 * a permanent error; an empty third column means the scan succeeded.
 */
public record ScannedEntry(String version, String classifier, String errorMessage) {

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
        if (errorMessage.indexOf('\t') >= 0 || errorMessage.indexOf('\n') >= 0 || errorMessage.indexOf('\r') >= 0) {
            throw new IllegalArgumentException("errorMessage must not contain tab or newline characters; call sanitize() first");
        }
    }

    public static ScannedEntry ok(String version, String classifier) {
        return new ScannedEntry(version, classifier, "");
    }

    public static ScannedEntry failed(String version, String classifier, String errorMessage) {
        return new ScannedEntry(version, classifier, sanitize(errorMessage));
    }

    public boolean isFailed() {
        return !errorMessage.isEmpty();
    }

    public String format() {
        return version + '\t' + (classifier == null ? "" : classifier) + '\t' + errorMessage;
    }

    public static ScannedEntry parse(String line) {
        String[] parts = line.split("\t", -1);
        if (parts.length != 3) {
            throw new IllegalArgumentException("Expected 3 tab-separated fields in scanned entry: " + line);
        }
        return new ScannedEntry(
                parts[0],
                parts[1].isEmpty() ? null : parts[1],
                parts[2]);
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
