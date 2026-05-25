package build.jenesis.crawler.model;

import module java.base;

public record Coordinate(String groupId,
                         String artifactId,
                         String version,
                         String classifier,
                         String extension,
                         long size,
                         long lastModified) {

    private static final String NOT_AVAILABLE = "NA";
    private static final String UINFO_FIELD = "u";
    private static final String INFO_FIELD = "i";

    /**
     * Extensions that the Nexus indexer occasionally writes for what should be a main JAR record
     * (i.e. {@code classifier == null}) - rewriting them back to {@code jar} at parse time lets
     * downstream code (Crawler.isInteresting, Scanner, the scanned-store filter) treat them like
     * normal main-JAR coordinates.
     *
     * <p>Only Gradle's {@code .module} extension is here. The Maven Indexer is documented not to
     * index {@code .module} files, so any such record is necessarily a mis-categorised main JAR.
     * The {@code pom.sha*} and {@code pom.asc.sha*} variants used to be in this set too (the same
     * rewrite the windup nexus-repository-indexer project applies), but those extensions exist
     * legitimately as sidecar records for pom-only artifacts (BOMs, parent POMs) - rewriting them
     * would then make the crawler fetch a non-existent {@code .jar} and bloat scanned.tsv with
     * 404 failures. The byte-buddy-style "main-jar record mis-stamped as pom.sha512" case is left
     * to {@link build.jenesis.crawler.ReconcileMetadata}, which uses {@code maven-metadata.xml}
     * as the authoritative version list and doesn't have the ambiguity. References: OSSRH-60950
     * and the windup nexus-repository-indexer project.
     */
    private static final Set<String> MISCATEGORISED_JAR_EXTENSIONS = Set.of("module");

    public Coordinate {
        Objects.requireNonNull(groupId, "groupId");
        Objects.requireNonNull(artifactId, "artifactId");
        Objects.requireNonNull(version, "version");
        Objects.requireNonNull(extension, "extension");
    }

    public String mavenPath() {
        StringBuilder builder = new StringBuilder();
        builder.append(groupId.replace('.', '/')).append('/');
        builder.append(artifactId).append('/');
        builder.append(version).append('/');
        builder.append(artifactId).append('-').append(version);
        if (classifier != null) {
            builder.append('-').append(classifier);
        }
        builder.append('.').append(extension);
        return builder.toString();
    }

    public static Optional<Coordinate> from(Map<String, String> record) {
        String uinfo = record.get(UINFO_FIELD);
        if (uinfo == null) {
            return Optional.empty();
        }
        String[] uparts = uinfo.split("\\|", -1);
        if (uparts.length < 3) {
            return Optional.empty();
        }
        String groupId = uparts[0];
        String artifactId = uparts[1];
        String version = uparts[2];
        String classifier = uparts.length > 3 && !NOT_AVAILABLE.equals(uparts[3]) ? uparts[3] : null;
        String extensionFromUinfo = uparts.length > 4 && !NOT_AVAILABLE.equals(uparts[4]) ? uparts[4] : null;

        String packaging = null;
        long lastModified = 0L;
        long size = 0L;
        String extensionFromInfo = null;

        String info = record.get(INFO_FIELD);
        if (info != null) {
            String[] iparts = info.split("\\|", -1);
            if (iparts.length > 0 && !NOT_AVAILABLE.equals(iparts[0])) {
                packaging = iparts[0];
            }
            if (iparts.length > 1) {
                try {
                    lastModified = Long.parseLong(iparts[1]);
                } catch (NumberFormatException _) {
                }
            }
            if (iparts.length > 2) {
                try {
                    size = Long.parseLong(iparts[2]);
                } catch (NumberFormatException _) {
                }
            }
            if (iparts.length > 6 && !NOT_AVAILABLE.equals(iparts[6])) {
                extensionFromInfo = iparts[6];
            }
        }

        String extension = extensionFromUinfo != null
                ? extensionFromUinfo
                : extensionFromInfo != null ? extensionFromInfo : packaging != null ? packaging : "jar";
        if (classifier == null && MISCATEGORISED_JAR_EXTENSIONS.contains(extension)) {
            extension = "jar";
        }

        return Optional.of(new Coordinate(groupId, artifactId, version, classifier, extension, size, lastModified));
    }
}
