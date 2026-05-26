package build.jenesis.crawler.model;

import module java.base;

/**
 * A row of {@code artifacts.tsv}: the canonical resolution from a Maven coordinate version
 * to its publisher, after the owners-allowlist (or implicit-owner) filter has been applied.
 *
 * <p>Four tab-separated columns: {@code version}, {@code type}, {@code groupId},
 * {@code artifactId}.
 */
public record ArtifactsEntry(Version version, ModuleType type, String groupId, String artifactId) {

    public static final Comparator<ArtifactsEntry> NEWEST_FIRST = Comparator
            .comparing(ArtifactsEntry::version, Comparator.reverseOrder())
            .thenComparing(ArtifactsEntry::groupId)
            .thenComparing(ArtifactsEntry::artifactId);

    public ArtifactsEntry {
        Objects.requireNonNull(version, "version");
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(groupId, "groupId");
        Objects.requireNonNull(artifactId, "artifactId");
    }

    public static ArtifactsEntry of(ModuleEntry entry) {
        return new ArtifactsEntry(entry.mavenVersion(), entry.type(), entry.groupId(), entry.artifactId());
    }

    public String format() {
        return version.raw() + '\t' + type.label() + '\t' + groupId + '\t' + artifactId;
    }

    public static ArtifactsEntry parse(String line) {
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
        if (line.indexOf('\t', thirdTab + 1) >= 0) {
            throw new IllegalArgumentException("Unexpected extra tab in line: " + line);
        }
        String rawVersion = line.substring(0, firstTab);
        String typeLabel = line.substring(firstTab + 1, secondTab);
        String groupId = line.substring(secondTab + 1, thirdTab);
        String artifactId = line.substring(thirdTab + 1);
        return new ArtifactsEntry(new Version(rawVersion), ModuleType.fromLabel(typeLabel), groupId, artifactId);
    }
}
