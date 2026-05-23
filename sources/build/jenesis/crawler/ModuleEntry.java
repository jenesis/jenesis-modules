package build.jenesis.crawler;

import module java.base;

public record ModuleEntry(Version version, ModuleType type, String groupId, String artifactId) {

    public static final Comparator<ModuleEntry> NEWEST_FIRST = Comparator
            .comparing(ModuleEntry::version, Comparator.reverseOrder())
            .thenComparing(ModuleEntry::groupId)
            .thenComparing(ModuleEntry::artifactId);

    public ModuleEntry {
        Objects.requireNonNull(version, "version");
        Objects.requireNonNull(type, "type");
        Objects.requireNonNull(groupId, "groupId");
        Objects.requireNonNull(artifactId, "artifactId");
    }

    public String format() {
        return version.raw() + '\t' + type.label() + '\t' + groupId + '\t' + artifactId;
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
        if (line.indexOf('\t', thirdTab + 1) >= 0) {
            throw new IllegalArgumentException("Unexpected extra tab in line: " + line);
        }
        String rawVersion = line.substring(0, firstTab);
        String typeLabel = line.substring(firstTab + 1, secondTab);
        String groupId = line.substring(secondTab + 1, thirdTab);
        String artifactId = line.substring(thirdTab + 1);
        return new ModuleEntry(new Version(rawVersion), ModuleType.fromLabel(typeLabel), groupId, artifactId);
    }
}
