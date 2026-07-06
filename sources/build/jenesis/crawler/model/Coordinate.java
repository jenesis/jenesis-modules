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
     * Extensions that the Nexus indexer writes for what should be a main JAR record
     * (i.e. {@code classifier == null}). Classifier-less records share a single uinfo key per
     * GAV, so whichever classifier-less sidecar file the indexer processes last overwrites the
     * main record's extension - the underlying bug behind the byte-buddy gap that motivated
     * {@code ReconcileMetadata} (OSSRH-60950; the windup nexus-repository-indexer performs the
     * same rewrite on a downloaded index). {@code Coordinate.from(...)} rewrites the extension
     * back to {@code jar} so downstream code (Crawler.isInteresting, Scanner, the scanned-store
     * filter) treats it like any other main-JAR coordinate. Side artifacts keep their
     * classifier and are unaffected.
     *
     * <p>The masking sidecars keep evolving, so a closed list loses: a 2026-07 sweep of the
     * full index found 5.8M main records stamped {@code pom.sha512}, 11,965 stamped
     * {@code spdx.json} (Central's SBOM sidecars - how commons-fileupload2 went missing),
     * plus {@code *.asc}, {@code *.md5} and {@code pom.sigstore.json.sha512} variants. Hence
     * the split: an explicit set for whole-extension maskers, and a suffix rule for the
     * open-ended checksum/signature family.
     *
     * <p>The trade-off is unchanged: for artifacts whose real packaging is not a JAR (parent
     * POMs, zips, wars), a masked record is rewritten anyway and the scanner fetches a
     * non-existent {@code .jar}. Those 404s land as permanent failures in {@code scanned.tsv}
     * and inflate "Top error messages" in the summary, but the cost is one-time per coordinate
     * (the scanned-store filter dedupes thereafter) and the upside is that no mis-stamped main
     * JAR is silently dropped. Completeness wins.
     */
    private static final Set<String> MISCATEGORISED_JAR_EXTENSIONS = Set.of(
            "module",
            "sha256",
            "sha512",
            "sha1",
            "md5",
            "asc");

    /**
     * SBOM sidecar families whose extension keeps changing as tooling evolves — commons-lang3 3.15+ mask
     * with {@code spdx.json}, 3.13/3.14 with the older {@code spdx.rdf.xml}, and CycloneDX emits
     * {@code cyclonedx.*}. Match the family by substring rather than chase each exact spelling: no real
     * artifact packaging contains {@code spdx} or {@code cyclonedx}, so a classifier-less record carrying
     * one is always a mis-stamped main JAR.
     */
    private static final List<String> MISCATEGORISED_JAR_INFIXES = List.of("spdx", "cyclonedx", "sbom");

    private static final List<String> MISCATEGORISED_JAR_SUFFIXES = List.of(
            ".sha256",
            ".sha512",
            ".sha1",
            ".md5",
            ".asc");

    public Coordinate {
        Objects.requireNonNull(groupId, "groupId");
        Objects.requireNonNull(artifactId, "artifactId");
        Objects.requireNonNull(version, "version");
        Objects.requireNonNull(extension, "extension");
    }

    public Coordinate withLastModified(long lastModified) {
        return new Coordinate(groupId, artifactId, version, classifier, extension, size, lastModified);
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
        if (classifier == null && miscategorised(extension)) {
            extension = "jar";
        }

        return Optional.of(new Coordinate(groupId, artifactId, version, classifier, extension, size, lastModified));
    }

    private static boolean miscategorised(String extension) {
        if (MISCATEGORISED_JAR_EXTENSIONS.contains(extension)) {
            return true;
        }
        for (String infix : MISCATEGORISED_JAR_INFIXES) {
            if (extension.contains(infix)) {
                return true;
            }
        }
        for (String suffix : MISCATEGORISED_JAR_SUFFIXES) {
            if (extension.endsWith(suffix)) {
                return true;
            }
        }
        return false;
    }
}
