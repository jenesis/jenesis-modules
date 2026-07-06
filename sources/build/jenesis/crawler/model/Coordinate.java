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
     * The real primary-artifact packagings a classifier-less record may legitimately carry. Everything
     * else on a classifier-less record is treated as a mis-stamped JAR and rewritten to {@code jar} by
     * {@code Coordinate.from(...)} - see {@link #miscategorised(String)}.
     *
     * <p>The Nexus indexer keys a classifier-less record by a single uinfo per GAV, so whichever
     * classifier-less sidecar the indexer processes last overwrites the main record's extension - a real
     * JAR ends up stamped {@code module} / {@code pom.sha512} / {@code spdx.json} / {@code spdx.rdf.xml} /
     * {@code cyclonedx.*} / {@code *.asc} / a sigstore bundle / the next SBOM format nobody has invented
     * yet (OSSRH-60950; the windup nexus-repository-indexer rewrites the same way on a downloaded index).
     * That masking family is open-ended, so a denylist of sidecar spellings always loses to the next one
     * and silently drops modules (commons-fileupload2, then commons-lang3 3.13-3.20). We invert it: allowlist
     * the CLOSED, stable set of genuine packagings and rewrite everything else.
     *
     * <p>Why the inversion is safe: this crawler catalogs Java <em>modules</em>, and a non-JAR artifact is
     * never a module. So assuming "unknown classifier-less extension -&gt; try it as a JAR" can only ever
     * find a module, never lose one - the worst case is a one-time {@code scanned.tsv} 404 for a genuinely
     * exotic non-JAR (which was never a module), deduped thereafter. The failure mode flips from silent
     * module loss to a benign logged miss. The allowlist below carries the high-volume real packagings so
     * their expected 404s are avoided; a rare packaging omitted here just 404s once. New sidecar/SBOM
     * formats need no code change - they fall through to the rewrite automatically.
     */
    private static final Set<String> REAL_PACKAGINGS = Set.of(
            "jar",                                                       // the common case
            "pom",                                                       // pom-only artifacts (BOMs, parents)
            "war", "ear", "rar", "par", "ejb",                          // Java EE
            "aar", "apklib",                                             // Android
            "zip", "tar.gz", "tar.bz2", "tgz",                          // distributions
            "nar", "so", "dll", "dylib", "exe",                        // native
            "swc", "nbm", "hpi", "jpi", "esa", "kar", "sar", "car", "oar", "jdocbook"); // ecosystem-specific

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

    /**
     * Whether a classifier-less record's extension is a mis-stamped JAR: anything that is not one of the
     * {@link #REAL_PACKAGINGS}. Callers apply this only when {@code classifier == null}.
     */
    private static boolean miscategorised(String extension) {
        return !REAL_PACKAGINGS.contains(extension);
    }
}
