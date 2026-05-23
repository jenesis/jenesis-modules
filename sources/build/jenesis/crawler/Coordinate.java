package build.jenesis.crawler;

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

    public Coordinate {
        Objects.requireNonNull(groupId, "groupId");
        Objects.requireNonNull(artifactId, "artifactId");
        Objects.requireNonNull(version, "version");
        Objects.requireNonNull(extension, "extension");
    }

    public String groupArtifact() {
        return groupId + ":" + artifactId;
    }

    public String coordinate() {
        StringBuilder builder = new StringBuilder();
        builder.append(groupId).append(':').append(artifactId).append(':').append(version);
        if (classifier != null) {
            builder.append(':').append(classifier);
        }
        return builder.toString();
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
                } catch (NumberFormatException ignored) {
                }
            }
            if (iparts.length > 2) {
                try {
                    size = Long.parseLong(iparts[2]);
                } catch (NumberFormatException ignored) {
                }
            }
            if (iparts.length > 6 && !NOT_AVAILABLE.equals(iparts[6])) {
                extensionFromInfo = iparts[6];
            }
        }

        String extension = extensionFromUinfo != null
                ? extensionFromUinfo
                : extensionFromInfo != null ? extensionFromInfo : packaging != null ? packaging : "jar";

        return Optional.of(new Coordinate(groupId, artifactId, version, classifier, extension, size, lastModified));
    }
}
