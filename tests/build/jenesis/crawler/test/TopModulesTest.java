package build.jenesis.crawler.test;

import module java.base;
import module jdk.httpserver;
import build.jenesis.crawler.TopModules;
import module org.junit.jupiter.api;


import static org.assertj.core.api.Assertions.assertThat;

public class TopModulesTest {

    @TempDir
    Path dataDir;

    @TempDir
    Path topDir;

    @Test
    public void listing_counts_files_and_sums_their_sizes() {
        TopModules.Listing listing = TopModules.parseListing(listingOf(
                "lib-1.0.jar", 1_000,
                "lib-1.0.jar.asc", 800,
                "lib-1.0.pom", 200));

        assertThat(listing.files()).isEqualTo(3);
        assertThat(listing.bytes()).isEqualTo(2_000L);
    }

    @Test
    public void listing_ignores_the_parent_link_and_nested_directories() {
        String html = """
                <pre id="contents"><a href="../">../</a>
                <a href="nested/">nested/</a>                    2026-01-02 03:04         -
                <a href="lib-1.0.jar">lib-1.0.jar</a>            2026-01-02 03:04       512
                </pre>
                """;

        TopModules.Listing listing = TopModules.parseListing(html);

        assertThat(listing.files()).as("only the file row counts").isEqualTo(1);
        assertThat(listing.bytes()).isEqualTo(512L);
    }

    @Test
    public void listing_reads_a_row_whose_file_name_the_repository_abbreviated() {
        // Maven Central truncates a long name with an ellipsis, so the size column is the only
        // field a row can be measured by.
        String html = """
                <pre id="contents"><a href="../">../</a>
                <a href="a-very-long-artifact-name-1.0-cyclonedx.json">a-very-long-artifact-na...</a>  2026-01-02 03:04   4096
                </pre>
                """;

        TopModules.Listing listing = TopModules.parseListing(html);

        assertThat(listing.files()).isEqualTo(1);
        assertThat(listing.bytes()).isEqualTo(4_096L);
    }

    @Test
    public void report_has_no_publishing_columns_when_no_repository_is_configured() throws IOException {
        writeModule("com.example", "lib", List.of("1.0", "1.1"));
        Path topFile = writeTopFile("com.example:lib");

        TopModules.main(new String[] {topFile.toString()});

        String report = Files.readString(topFile.resolveSibling("2026.md"), StandardCharsets.UTF_8);
        assertThat(report).doesNotContain("Files per release");
        assertThat(report).doesNotContain("Over Central limit");
    }

    @Test
    public void report_measures_a_group_against_the_central_limits() throws IOException {
        writeModule("com.example", "lib", List.of("1.0", "1.1"));
        Path topFile = writeTopFile("com.example:lib");

        String report = renderAgainst(topFile, Map.of(
                "com/example/lib/1.0/", listingOf("lib-1.0.jar", 3_000_000, "lib-1.0.pom", 1_000_000),
                "com/example/lib/1.1/", listingOf("lib-1.1.jar", 5_000_000, "lib-1.1.pom", 1_000_000)));

        assertThat(report).contains(
                "| Group artifacts | Files per release | MB per release | Releases per month | Over Central limit |");
        // One artifact, two releases of two files each weighing 4 MB and 6 MB: 2,0 files and 5,0 MB
        // a release at 2/12 releases a month, which breaches nothing.
        assertThat(report).contains("| 1 | 2,0 | 5,0 | 0,2 | - |");
        assertThat(report).contains("| Artifacts whose group is over any limit | 0 (0,0%)");
    }

    @Test
    public void a_group_publishing_above_a_limit_is_flagged_and_counted() throws IOException {
        // Twelve releases a year is one a month, under the release limit, but each weighs 100 MB,
        // so the group publishes 100 MB a month against a 78 MB threshold.
        Map<String, String> listings = new HashMap<>();
        List<String> versions = versions(12);
        for (String version : versions) {
            listings.put("com/example/lib/" + version + "/",
                    listingOf("lib-" + version + ".jar", 100_000_000, "lib-" + version + ".pom", 1_000));
        }
        writeModule("com.example", "lib", versions);
        Path topFile = writeTopFile("com.example:lib");

        String report = renderAgainst(topFile, listings);

        assertThat(report).contains("🔺 size");
        assertThat(report).contains("| Artifacts whose group is over the size limit | 1 (100,0%)");
        assertThat(report).contains("| Artifacts whose group is over the file limit | 0 (0,0%)");
        assertThat(report).contains("| Groups over the size limit | 1 (100,0%)");
    }

    @Test
    public void a_group_is_measured_over_all_its_artifacts_not_only_the_listed_one() throws IOException {
        // The listed artifact publishes 60 MB a month, under the threshold on its own. Its groupId
        // holds a second artifact that never reached the list and publishes another 60 MB, and the
        // group is what Maven Central caps, so the listed row is flagged on the pair's 120 MB.
        Map<String, String> listings = new HashMap<>();
        List<String> versions = versions(12);
        for (String artifactId : List.of("listed", "unlisted")) {
            for (String version : versions) {
                listings.put("com/example/" + artifactId + "/" + version + "/",
                        listingOf(artifactId + "-" + version + ".jar", 60_000_000));
            }
        }
        writeModule("com.example", "listed", versions);
        writeScanned("com.example", "unlisted", versions);
        Path topFile = writeTopFile("com.example:listed");

        String report = renderAgainst(topFile, listings);

        assertThat(report).as("both artifacts of the group are summarised").contains("| 2 |");
        assertThat(report).contains("🔺 size");
        assertThat(report).contains("| Groups over the size limit | 1 (100,0%)");
    }

    @Test
    public void rows_sharing_a_group_carry_the_same_figures() throws IOException {
        Map<String, String> listings = new HashMap<>();
        List<String> versions = versions(12);
        for (String artifactId : List.of("one", "two")) {
            writeModule("com.example", artifactId, versions);
            for (String version : versions) {
                listings.put("com/example/" + artifactId + "/" + version + "/",
                        listingOf(artifactId + "-" + version + ".jar", 60_000_000));
            }
        }
        Path topFile = writeTopFile("com.example:one", "com.example:two");

        String report = renderAgainst(topFile, listings);

        List<String> published = report.lines()
                .filter(line -> line.startsWith("| 1 | com.example:") || line.startsWith("| 2 | com.example:"))
                .map(line -> line.substring(line.indexOf("| 2 |", 1)))
                .toList();
        assertThat(published).hasSize(2);
        assertThat(published.get(0)).as("one groupId, one set of figures").isEqualTo(published.get(1));
        assertThat(report).contains("| Groups over the size limit | 1 (100,0%)");
        assertThat(report).contains("| Artifacts whose group is over the size limit | 2 (100,0%)");
    }

    private static List<String> versions(int count) {
        List<String> versions = new ArrayList<>(count);
        for (int index = 0; index < count; index++) {
            versions.add("1." + index);
        }
        return versions;
    }

    private String renderAgainst(Path topFile, Map<String, String> listings) throws IOException {
        try (Listings server = new Listings(listings)) {
            System.setProperty("jenesis.crawler.top.releases.uri", server.baseUri());
            try {
                TopModules.main(new String[] {topFile.toString()});
            } finally {
                System.clearProperty("jenesis.crawler.top.releases.uri");
            }
        }
        return Files.readString(topFile.resolveSibling("2026.md"), StandardCharsets.UTF_8);
    }

    /** A module and a scan history for one coordinate, every version published inside the report year. */
    private void writeModule(String groupId, String artifactId, List<String> versions) throws IOException {
        Path moduleDir = dataDir.resolve("modules");
        for (String segment : groupId.split("\\.")) {
            moduleDir = moduleDir.resolve(segment);
        }
        moduleDir = Files.createDirectories(moduleDir.resolve(artifactId));
        StringBuilder lines = new StringBuilder();
        for (int index = 0; index < versions.size(); index++) {
            lines.append(versions.get(index)).append('\t').append("named").append('\t')
                    .append(groupId).append('\t').append(artifactId).append('\t')
                    .append(publishedAt(index)).append('\t').append(versions.get(index)).append('\n');
        }
        Files.writeString(moduleDir.resolve("versions.tsv"), lines.toString(), StandardCharsets.UTF_8);
        writeScanned(groupId, artifactId, versions);
    }

    /** A scan history alone, for a coordinate that carries no module and may not be listed. */
    private void writeScanned(String groupId, String artifactId, List<String> versions) throws IOException {
        Path scannedDir = dataDir.resolve("scanned");
        for (String segment : groupId.split("\\.")) {
            scannedDir = scannedDir.resolve(segment);
        }
        Files.createDirectories(scannedDir);
        StringBuilder lines = new StringBuilder();
        for (int index = 0; index < versions.size(); index++) {
            lines.append(versions.get(index)).append('\t').append('\t')
                    .append(publishedAt(index)).append('\t').append('\n');
        }
        Files.writeString(scannedDir.resolve(artifactId + ".tsv"), lines.toString(), StandardCharsets.UTF_8);
    }

    private static String publishedAt(int index) {
        return String.format("2026-%02d-01T00:00:00Z", (index % 12) + 1);
    }

    private Path writeTopFile(String... coordinates) throws IOException {
        Path topFile = topDir.resolve("2026.txt");
        Files.writeString(topFile, String.join("\n", coordinates) + "\n", StandardCharsets.UTF_8);
        System.setProperty("jenesis.crawler.data", dataDir.toString());
        return topFile;
    }

    @AfterEach
    public void clearDataProperty() {
        System.clearProperty("jenesis.crawler.data");
    }

    /** One directory listing in the shape Maven Central serves, name then date then size. */
    private static String listingOf(Object... namesAndSizes) {
        StringBuilder builder = new StringBuilder("<pre id=\"contents\"><a href=\"../\">../</a>\n");
        for (int index = 0; index < namesAndSizes.length; index += 2) {
            String name = (String) namesAndSizes[index];
            builder.append("<a href=\"").append(name).append("\">").append(name).append("</a>")
                    .append("      2026-01-02 03:04      ").append(namesAndSizes[index + 1]).append("      \n");
        }
        return builder.append("</pre>\n").toString();
    }

    /** Serves the supplied directory listings, and 404s anything else. */
    private static final class Listings implements AutoCloseable {

        private final HttpServer server;

        private Listings(Map<String, String> listings) throws IOException {
            server = HttpServer.create(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0), 0);
            server.createContext("/", exchange -> {
                String path = exchange.getRequestURI().getPath().substring(1);
                String body = listings.get(path);
                byte[] bytes = body == null
                        ? new byte[0]
                        : body.getBytes(StandardCharsets.UTF_8);
                exchange.sendResponseHeaders(body == null ? 404 : 200, bytes.length == 0 ? -1 : bytes.length);
                try (OutputStream output = exchange.getResponseBody()) {
                    output.write(bytes);
                }
            });
            server.start();
        }

        private String baseUri() {
            return "http://" + server.getAddress().getHostString() + ":" + server.getAddress().getPort() + "/";
        }

        @Override
        public void close() {
            server.stop(0);
        }
    }
}
