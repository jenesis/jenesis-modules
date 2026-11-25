package build.jenesis.crawler.test;

import module java.base;
import module jdk.httpserver;
import build.jenesis.crawler.BackfillTimestamps;
import build.jenesis.crawler.fetch.Fetcher;
import module org.junit.jupiter.api;

import static org.assertj.core.api.Assertions.assertThat;

public class BackfillTimestampsTest {

    private static final String PUBLISHED = "Tue, 17 Nov 2026 18:55:48 GMT";
    private static final String PUBLISHED_ISO = "2026-11-17T18:55:48Z";
    private static final String LANDED = "Sun, 14 Jul 2019 15:33:26 GMT";
    private static final String JAR = "/maven2/com/example/lib/1.0/lib-1.0.jar";

    @TempDir
    Path root;

    private final List<HttpServer> servers = new ArrayList<>();

    @AfterEach
    void stopServers() {
        servers.forEach(server -> server.stop(0));
    }

    @Test
    public void undated_row_takes_the_publish_time_the_mirror_preserved() throws IOException {
        Path file = scanned("1.0\t\t\t");
        URI mirror = serve(Map.of(JAR, Map.of("Last-Modified", LANDED, "x-goog-meta-last-modified", PUBLISHED)));
        URI canonical = serve(Map.of());

        BackfillTimestamps.Result result = run(mirror, canonical);

        assertThat(Files.readString(file)).isEqualTo("1.0\t\t" + PUBLISHED_ISO + "\t\n");
        assertThat(result).isEqualTo(new BackfillTimestamps.Result(1L, 1L, 0L, 0L, true));
    }

    @Test
    public void mirror_landing_time_is_replaced_by_the_canonical_publish_time() throws IOException {
        Path file = scanned("1.0\t\t\t");
        URI mirror = serve(Map.of(JAR, Map.of("Last-Modified", LANDED, "x-goog-generation", "1")));
        URI canonical = serve(Map.of(JAR, Map.of("Last-Modified", PUBLISHED)));

        run(mirror, canonical);

        assertThat(Files.readString(file)).isEqualTo("1.0\t\t" + PUBLISHED_ISO + "\t\n");
    }

    @Test
    public void row_stays_undated_when_only_a_mirror_landing_time_is_on_offer() throws IOException {
        Path file = scanned("1.0\t\t\t");
        URI mirror = serve(Map.of(JAR, Map.of("Last-Modified", LANDED, "x-goog-generation", "1")));
        URI canonical = serve(Map.of());

        BackfillTimestamps.Result result = run(mirror, canonical);

        assertThat(Files.readString(file)).isEqualTo("1.0\t\t\t\n");
        assertThat(result).isEqualTo(new BackfillTimestamps.Result(1L, 0L, 0L, 1L, true));
    }

    @Test
    public void single_source_last_modified_is_trusted() throws IOException {
        Path file = scanned("1.0\t\t\t");
        URI central = serve(Map.of(JAR, Map.of("Last-Modified", PUBLISHED)));

        run(central, central);

        assertThat(Files.readString(file)).isEqualTo("1.0\t\t" + PUBLISHED_ISO + "\t\n");
    }

    @Test
    public void dated_rows_and_failures_are_left_as_they_are() throws IOException {
        String rows = "0.8\t\t\tIOException: Tail request on https://example.org/lib-0.8.jar returned status 404\n"
                + "0.9\t\t2020-01-02T03:04:05Z\t\n";
        Path file = scanned(rows + "1.0\t\t\t");
        AtomicInteger requests = new AtomicInteger();
        URI mirror = serve(Map.of(JAR, Map.of("x-goog-meta-last-modified", PUBLISHED)), requests);

        run(mirror, mirror);

        assertThat(Files.readString(file)).isEqualTo(rows + "1.0\t\t" + PUBLISHED_ISO + "\t\n");
        assertThat(requests).hasValue(1);
    }

    @Test
    public void vanished_artifact_is_counted_and_left_undated() throws IOException {
        Path file = scanned("1.0\t\t\t");
        URI mirror = serve(Map.of());

        BackfillTimestamps.Result result = run(mirror, mirror);

        assertThat(Files.readString(file)).isEqualTo("1.0\t\t\t\n");
        assertThat(result).isEqualTo(new BackfillTimestamps.Result(1L, 0L, 1L, 0L, true));
    }

    private BackfillTimestamps.Result run(URI artifactBase, URI canonicalBase) throws IOException {
        try (Fetcher fetcher = new Fetcher(Duration.ofSeconds(10L), 0)) {
            return BackfillTimestamps.run(root.resolve("scanned"), artifactBase, canonicalBase, fetcher, 4, Duration.ofMinutes(1L));
        }
    }

    private Path scanned(String rows) throws IOException {
        Path dir = Files.createDirectories(root.resolve("scanned").resolve("com").resolve("example"));
        return Files.writeString(dir.resolve("lib.tsv"), rows + "\n", StandardCharsets.UTF_8);
    }

    private URI serve(Map<String, Map<String, String>> headersByPath) throws IOException {
        return serve(headersByPath, new AtomicInteger());
    }

    private URI serve(Map<String, Map<String, String>> headersByPath, AtomicInteger requests) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext("/", exchange -> {
            try {
                requests.incrementAndGet();
                Map<String, String> headers = headersByPath.get(exchange.getRequestURI().getPath());
                if (headers == null) {
                    exchange.sendResponseHeaders(404, -1);
                    return;
                }
                headers.forEach(exchange.getResponseHeaders()::add);
                exchange.sendResponseHeaders(200, -1);
            } finally {
                exchange.close();
            }
        });
        server.start();
        servers.add(server);
        return URI.create("http://127.0.0.1:" + server.getAddress().getPort() + "/maven2/");
    }
}
