package build.jenesis.crawler;

import module java.base;

public record ModuleEntry(Version version, ModuleType type, String groupId, String artifactId, long publishedAt) {

    public static final Comparator<ModuleEntry> NEWEST_FIRST = Comparator
            .comparing(ModuleEntry::version, Comparator.reverseOrder())
            .thenComparing(ModuleEntry::groupId)
            .thenComparing(ModuleEntry::artifactId);

    private static final DateTimeFormatter ISO_UTC_SECONDS = DateTimeFormatter
            .ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
            .withZone(ZoneOffset.UTC);

    public ModuleEntry {
        Objects.requireNonNull(version, "version");
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(groupId, "groupId");
        Objects.requireNonNull(artifactId, "artifactId");
        if (publishedAt < 0L) {
            throw new IllegalArgumentException("publishedAt must be >= 0, got: " + publishedAt);
        }
    }

    public String publishedAtIso() {
        return ISO_UTC_SECONDS.format(Instant.ofEpochMilli(publishedAt));
    }

    public String format() {
        return version.raw() + '\t' + type.label() + '\t' + groupId + '\t' + artifactId + '\t' + publishedAtIso();
    }

    public static ModuleEntry parse(String line) {
        int firstTab = line.indexOf('\t');
        if (firstTab < 0) {
            throw new IllegalArgumentException("Missing first tab in line: " + line);
        }
        int secondTab = line.indexOf('\t', firstTab + 1);
        if (secondTab < 0) {
            throw new IllegalArgumentException("Missing second tab in line: " + line);
        }
        int thirdTab = line.indexOf('\t', secondTab + 1);
        if (thirdTab < 0) {
            throw new IllegalArgumentException("Missing third tab in line: " + line);
        }
        int fourthTab = line.indexOf('\t', thirdTab + 1);
        if (fourthTab < 0) {
            throw new IllegalArgumentException("Missing fourth tab in line: " + line);
        }
        if (line.indexOf('\t', fourthTab + 1) >= 0) {
            throw new IllegalArgumentException("Unexpected extra tab in line: " + line);
        }
        String rawVersion = line.substring(0, firstTab);
        String typeLabel = line.substring(firstTab + 1, secondTab);
        String groupId = line.substring(secondTab + 1, thirdTab);
        String artifactId = line.substring(thirdTab + 1, fourthTab);
        String rawPublishedAt = line.substring(fourthTab + 1);
        long publishedAt;
        try {
            publishedAt = Instant.from(ISO_UTC_SECONDS.parse(rawPublishedAt)).toEpochMilli();
        } catch (DateTimeParseException invalid) {
            throw new IllegalArgumentException("Invalid publishedAt in line: " + line, invalid);
        }
        return new ModuleEntry(new Version(rawVersion), ModuleType.fromLabel(typeLabel), groupId, artifactId, publishedAt);
    }
}
