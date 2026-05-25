package build.jenesis.crawler.model;

import module java.base;

/**
 * A row of versions.tsv. {@code mavenVersion} is the version from the Maven coordinate that
 * the row belongs to; {@code moduleVersion} is the raw, unparsed version string from the JAR's
 * {@code module-info}. An empty {@code moduleVersion} means the JAR was scanned and its
 * {@code module-info} carried no version attribute; a non-empty value is the literal string.
 */
public record ModuleEntry(Version mavenVersion, ModuleType type, String groupId, String artifactId, long publishedAt, String moduleVersion) {

    public static final Comparator<ModuleEntry> NEWEST_FIRST = Comparator
            .comparing(ModuleEntry::mavenVersion, Comparator.reverseOrder())
            .thenComparing(ModuleEntry::groupId)
            .thenComparing(ModuleEntry::artifactId);

    private static final DateTimeFormatter ISO_UTC_SECONDS = DateTimeFormatter
            .ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
            .withZone(ZoneOffset.UTC);

    public ModuleEntry {
        Objects.requireNonNull(mavenVersion, "mavenVersion");
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(groupId, "groupId");
        Objects.requireNonNull(artifactId, "artifactId");
        Objects.requireNonNull(moduleVersion, "moduleVersion");
        if (publishedAt < 0L) {
            throw new IllegalArgumentException("publishedAt must be >= 0, got: " + publishedAt);
        }
    }

    public String publishedAtIso() {
        return ISO_UTC_SECONDS.format(Instant.ofEpochMilli(publishedAt));
    }

    public String format() {
        return mavenVersion.raw() + '\t' + type.label() + '\t' + groupId + '\t' + artifactId
                + '\t' + publishedAtIso() + '\t' + moduleVersion;
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
        int fifthTab = line.indexOf('\t', fourthTab + 1);
        if (fifthTab < 0) {
            throw new IllegalArgumentException("Missing fifth tab in line: " + line);
        }
        if (line.indexOf('\t', fifthTab + 1) >= 0) {
            throw new IllegalArgumentException("Unexpected extra tab in line: " + line);
        }
        String rawMavenVersion = line.substring(0, firstTab);
        String typeLabel = line.substring(firstTab + 1, secondTab);
        String groupId = line.substring(secondTab + 1, thirdTab);
        String artifactId = line.substring(thirdTab + 1, fourthTab);
        String rawPublishedAt = line.substring(fourthTab + 1, fifthTab);
        String moduleVersion = line.substring(fifthTab + 1);
        long publishedAt;
        try {
            publishedAt = Instant.from(ISO_UTC_SECONDS.parse(rawPublishedAt)).toEpochMilli();
        } catch (DateTimeParseException invalid) {
            throw new IllegalArgumentException("Invalid publishedAt in line: " + line, invalid);
        }
        return new ModuleEntry(new Version(rawMavenVersion), ModuleType.fromLabel(typeLabel), groupId, artifactId, publishedAt, moduleVersion);
    }
}
