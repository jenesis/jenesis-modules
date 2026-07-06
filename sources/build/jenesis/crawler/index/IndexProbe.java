package build.jenesis.crawler.index;

import module java.base;

/**
 * Temporary diagnostic: streams a nexus-maven-repository-index.gz with the production
 * IndexReader and prints every record whose uinfo (or del entry) contains a needle.
 */
public final class IndexProbe {

    public static void main(String... args) throws Exception {
        URI uri = URI.create(args[0]);
        String[] needles = Arrays.copyOfRange(args, 1, args.length);
        long seen = 0L, matched = 0L, deletions = 0L, noUinfo = 0L;
        Map<String, Long> mainExtensions = new HashMap<>();
        try (InputStream raw = uri.toURL().openStream();
             GZIPInputStream gzipped = new GZIPInputStream(raw, 1 << 16);
             IndexReader reader = new IndexReader(gzipped)) {
            System.err.println("index version=" + reader.version() + " timestamp=" + Instant.ofEpochMilli(reader.timestamp()));
            Map<String, String> record;
            while ((record = reader.nextRecord()) != null) {
                seen++;
                String uinfo = record.get("u");
                String del = record.get("del");
                if (del != null) {
                    deletions++;
                }
                if (uinfo == null && del == null) {
                    noUinfo++;
                }
                if (uinfo != null) {
                    String[] uparts = uinfo.split("\\|", -1);
                    // Histogram the resolved extension of every classifier-less (main) record:
                    // any non-packaging extension here is a sidecar masking a main artifact.
                    if (uparts.length > 3 && "NA".equals(uparts[3])) {
                        String extension = uparts.length > 4 && !"NA".equals(uparts[4]) ? uparts[4] : null;
                        if (extension == null) {
                            String info = record.get("i");
                            if (info != null) {
                                String[] iparts = info.split("\\|", -1);
                                extension = iparts.length > 6 && !"NA".equals(iparts[6])
                                        ? iparts[6]
                                        : iparts.length > 0 && !"NA".equals(iparts[0]) ? iparts[0] : "jar";
                            } else {
                                extension = "jar";
                            }
                        }
                        mainExtensions.merge(extension, 1L, Long::sum);
                    }
                }
                String probe = uinfo != null ? uinfo : del != null ? del : "";
                for (String needle : needles) {
                    if (probe.contains(needle)) {
                        System.out.println("MATCH " + (uinfo != null ? "u=" + uinfo : "del=" + del)
                                + " i=" + record.get("i"));
                        matched++;
                        break;
                    }
                }
                if (seen % 5_000_000L == 0L) {
                    System.err.println("... " + seen + " records, " + matched + " matches");
                }
            }
        }
        System.out.println("records=" + seen + " deletions=" + deletions + " other=" + noUinfo + " matches=" + matched);
        System.out.println("main-record (classifier=NA) extension histogram, descending:");
        mainExtensions.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(40)
                .forEach(entry -> System.out.println("  " + entry.getValue() + "\t" + entry.getKey()));
    }
}
