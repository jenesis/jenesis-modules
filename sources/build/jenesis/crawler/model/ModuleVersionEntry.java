package build.jenesis.crawler.model;

import module java.base;

/**
 * A row of {@code modules.tsv}: a resolution from a Java module-info version (the publisher's
 * declared module version, falling back to the Maven coordinate version when the publisher
 * didn't declare one) to the canonical Maven coordinate that first published that module
 * version.
 *
 * <p>Four tab-separated columns: {@code moduleVersion}, {@code groupId}, {@code artifactId},
 * {@code mavenVersion}. The {@code type} column from {@link ArtifactsEntry} is intentionally
 * absent: {@code modules.tsv} only covers named modules (automatic modules have no
 * {@code module-info} and therefore no declared module version - they're filtered out at
 * resolution time), so the column would always be the same value.
 *
 * <p>The shape exists so the worker can answer "give me module {@code <name>} at module
 * version {@code <X>}" with a 302 to the actual Maven artifact - the {@code mavenVersion}
 * column carries the artifact-side coordinate version needed to build the JAR URL.
 */
public record ModuleVersionEntry(Version moduleVersion, String groupId, String artifactId, Version mavenVersion) {

    public static final Comparator<ModuleVersionEntry> NEWEST_FIRST = Comparator
            .comparing(ModuleVersionEntry::moduleVersion, Comparator.reverseOrder())
            .thenComparing(ModuleVersionEntry::groupId)
            .thenComparing(ModuleVersionEntry::artifactId);

    public ModuleVersionEntry {
        Objects.requireNonNull(moduleVersion, "moduleVersion");
        Objects.requireNonNull(groupId, "groupId");
        Objects.requireNonNull(artifactId, "artifactId");
        Objects.requireNonNull(mavenVersion, "mavenVersion");
    }

    /**
     * Builds an entry from a {@link ModuleEntry}, using the row's declared module-info version
     * when present and falling back to the Maven coordinate version when the {@code module-info}
     * carried no version attribute. The fallback keeps the {@code modules.tsv} key space dense
     * even for modules whose publishers never set {@code ModuleDescriptor.version()}.
     */
    public static ModuleVersionEntry of(ModuleEntry entry) {
        String raw = entry.moduleVersion().isEmpty() ? entry.mavenVersion().raw() : entry.moduleVersion();
        return new ModuleVersionEntry(new Version(raw), entry.groupId(), entry.artifactId(), entry.mavenVersion());
    }

    public String format() {
        return moduleVersion.raw() + '\t' + groupId + '\t' + artifactId + '\t' + mavenVersion.raw();
    }

    public static ModuleVersionEntry parse(String line) {
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
        String moduleVersion = line.substring(0, firstTab);
        String groupId = line.substring(firstTab + 1, secondTab);
        String artifactId = line.substring(secondTab + 1, thirdTab);
        String mavenVersion = line.substring(thirdTab + 1);
        return new ModuleVersionEntry(new Version(moduleVersion), groupId, artifactId, new Version(mavenVersion));
    }
}
