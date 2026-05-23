package build.jenesis.crawler;

import module java.base;

/**
 * Row of {@code data/scanned/<groupId-path>/scanned.tsv}.
 *
 * Four tab-separated columns: {@code artifactId}, {@code version}, {@code classifier-or-empty},
 * {@code errorMessage-or-empty}. The fourth column carries the recorded failure text (sanitised
 * so it stays on one line) when scanning the coordinate threw a permanent error; an empty fourth
 * column means the scan succeeded.
 */
public record ScannedEntry(String artifactId, String version, String classifier, String errorMessage) {

    public static final Comparator<ScannedEntry> COMPARATOR = Comparator
            .comparing(ScannedEntry::artifactId)
            .thenComparing(ScannedEntry::version)
            .thenComparing(entry -> entry.classifier() == null ? "" : entry.classifier());

    public ScannedEntry {
        Objects.requireNonNull(artifactId, "artifactId");
        Objects.requireNonNull(version, "version");
        Objects.requireNonNull(errorMessage, "errorMessage");
        if (artifactId.isEmpty()) {
            throw new IllegalArgumentException("artifactId must not be empty");
        }
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

    public static ScannedEntry ok(String artifactId, String version, String classifier) {
        return new ScannedEntry(artifactId, version, classifier, "");
    }

    public static ScannedEntry failed(String artifactId, String version, String classifier, String errorMessage) {
        return new ScannedEntry(artifactId, version, classifier, sanitize(errorMessage));
    }

    public boolean isFailed() {
        return !errorMessage.isEmpty();
    }

    public String format() {
        return artifactId + '\t' + version + '\t' + (classifier == null ? "" : classifier) + '\t' + errorMessage;
    }

    public static ScannedEntry parse(String line) {
        String[] parts = line.split("\t", -1);
        if (parts.length != 4) {
            throw new IllegalArgumentException("Expected 4 tab-separated fields in scanned entry: " + line);
        }
        return new ScannedEntry(
                parts[0],
                parts[1],
                parts[2].isEmpty() ? null : parts[2],
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
