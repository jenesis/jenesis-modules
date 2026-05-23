package build.jenesis.crawler;

import module java.base;

public record ScannedEntry(String artifactId, String version, String classifier) {

    public static final Comparator<ScannedEntry> COMPARATOR = Comparator
            .comparing(ScannedEntry::artifactId)
            .thenComparing(ScannedEntry::version)
            .thenComparing(entry -> entry.classifier() == null ? "" : entry.classifier());

    public ScannedEntry {
        Objects.requireNonNull(artifactId, "artifactId");
        Objects.requireNonNull(version, "version");
        if (artifactId.isEmpty()) {
            throw new IllegalArgumentException("artifactId must not be empty");
        }
        if (version.isEmpty()) {
            throw new IllegalArgumentException("version must not be empty");
        }
        if (classifier != null && classifier.isEmpty()) {
            throw new IllegalArgumentException("classifier must be null or non-empty");
        }
    }

    public String format() {
        return artifactId + '\t' + version + '\t' + (classifier == null ? "" : classifier);
    }

    public static ScannedEntry parse(String line) {
        String[] parts = line.split("\t", -1);
        if (parts.length != 3) {
            throw new IllegalArgumentException("Expected 3 tab-separated fields in scanned entry: " + line);
        }
        return new ScannedEntry(parts[0], parts[1], parts[2].isEmpty() ? null : parts[2]);
    }
}
