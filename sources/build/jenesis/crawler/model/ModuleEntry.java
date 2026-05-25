package build.jenesis.crawler.model;

import module java.base;

/**
 * A row of versions.tsv. {@code mavenVersion} is the version from the Maven
 * coordinate that the row belongs to; {@code moduleVersion} is the raw, unparsed
 * version string from the JAR's module-info.
 *
 * <p>{@code moduleVersion} is tri-state:
 * <ul>
 *   <li>{@code null}: the row was written before module-info version extraction
 *       existed (legacy 5-column row on disk; no trailing column);</li>
 *   <li>empty string: the row was scanned after the feature was added, but the
 *       module-info declared no version (6-column row whose trailing column is
 *       empty);</li>
 *   <li>non-empty string: the raw module-info version actually extracted from
 *       the JAR (6-column row whose trailing column carries the value).</li>
 * </ul>
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
        if (publishedAt < 0L) {
            throw new IllegalArgumentException("publishedAt must be >= 0, got: " + publishedAt);
        }
    }

    public ModuleEntry(Version mavenVersion, ModuleType type, String groupId, String artifactId, long publishedAt) {
        this(mavenVersion, type, groupId, artifactId, publishedAt, null);
    }

    public String publishedAtIso() {
        return ISO_UTC_SECONDS.format(Instant.ofEpochMilli(publishedAt));
    }

    public String format() {
        String prefix = mavenVersion.raw() + '\t' + type.label() + '\t' + groupId + '\t' + artifactId + '\t' + publishedAtIso();
        return moduleVersion == null ? prefix : prefix + '\t' + moduleVersion;
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
        // A fifth tab marks a row written after module-info version extraction was added.
        // The trailing column is the raw module-info version (possibly empty when module-info
        // had no version attribute). Rows without a fifth tab are pre-feature legacy rows
        // whose module-info version was never read and must stay distinguishable from rows
        // that were read and found absent.
        String rawPublishedAt;
        String moduleVersion;
        if (fifthTab < 0) {
            rawPublishedAt = line.substring(fourthTab + 1);
            moduleVersion = null;
        } else {
            if (line.indexOf('\t', fifthTab + 1) >= 0) {
                throw new IllegalArgumentException("Unexpected extra tab in line: " + line);
            }
            rawPublishedAt = line.substring(fourthTab + 1, fifthTab);
            moduleVersion = line.substring(fifthTab + 1);
        }
        String rawMavenVersion = line.substring(0, firstTab);
        String typeLabel = line.substring(firstTab + 1, secondTab);
        String groupId = line.substring(secondTab + 1, thirdTab);
        String artifactId = line.substring(thirdTab + 1, fourthTab);
        long publishedAt;
        try {
            publishedAt = Instant.from(ISO_UTC_SECONDS.parse(rawPublishedAt)).toEpochMilli();
        } catch (DateTimeParseException invalid) {
            throw new IllegalArgumentException("Invalid publishedAt in line: " + line, invalid);
        }
        return new ModuleEntry(new Version(rawMavenVersion), ModuleType.fromLabel(typeLabel), groupId, artifactId, publishedAt, moduleVersion);
    }
}
