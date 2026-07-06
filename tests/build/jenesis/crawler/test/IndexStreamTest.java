package build.jenesis.crawler.test;

import module java.base;
import module jdk.httpserver;
import build.jenesis.crawler.fetch.Fetcher;
import build.jenesis.crawler.index.IndexStream;
import module org.junit.jupiter.api;

import static org.assertj.core.api.Assertions.assertThat;

public class IndexStreamTest {

    @Test
    public void a_retry_resumes_by_stream_position_and_loses_no_records() throws Exception {
        // A six-record index, sync-flushed after record three so the corruption boundary is
        // deterministic: the first attempt decodes exactly three records and then fails with
        // a deflate error mid-stream.
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        int mark;
        try (GZIPOutputStream gzip = new GZIPOutputStream(bytes, true)) {
            DataOutputStream data = new DataOutputStream(gzip);
            data.writeByte(1);
            data.writeLong(0L);
            for (int index = 1; index <= 3; index++) {
                writeRecord(data, index);
            }
            data.flush();
            mark = bytes.size();
            for (int index = 4; index <= 6; index++) {
                writeRecord(data, index);
            }
        }
        byte[] full = bytes.toByteArray();
        byte[] corrupted = full.clone();
        for (int offset = 0; offset < 8 && mark + offset < corrupted.length; offset++) {
            corrupted[mark + offset] = (byte) 0x55;
        }

        // The server answers a Range probe with a plain 200 (range unsupported, forcing the
        // retry path under test), corrupts the first full download, and serves cleanly after.
        AtomicInteger downloads = new AtomicInteger();
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
        server.createContext("/index.gz", exchange -> {
            try (exchange) {
                if ("HEAD".equals(exchange.getRequestMethod())
                        || exchange.getRequestHeaders().containsKey("Range")) {
                    exchange.sendResponseHeaders(200, -1);
                    return;
                }
                byte[] body = downloads.incrementAndGet() == 1 ? corrupted : full;
                exchange.sendResponseHeaders(200, body.length);
                exchange.getResponseBody().write(body);
            }
        });
        server.start();
        try {
            // The filter marks-and-passes like the crawler's scanned-store filter: a record
            // passes once and is filtered on any replay. Before the stream-position resume,
            // that mutation made the retry skip records that were never emitted.
            Set<String> scanned = ConcurrentHashMap.newKeySet();
            List<String> emitted = new ArrayList<>();
            try (Fetcher fetcher = new Fetcher(Duration.ofSeconds(10), 1);
                 IndexStream stream = new IndexStream(fetcher, candidate -> scanned.add(candidate.artifactId()), _ -> {})) {
                stream.start(List.of(URI.create("http://localhost:" + server.getAddress().getPort() + "/index.gz")));
                for (;;) {
                    IndexStream.QueueItem item = stream.queue().poll(30L, TimeUnit.SECONDS);
                    assertThat(item).as("queue item within timeout").isNotNull();
                    if (item.isPoison()) {
                        break;
                    }
                    emitted.add(item.coordinate().artifactId());
                }
                assertThat(stream.error()).isNull();
            }
            assertThat(downloads.get()).isEqualTo(2);
            assertThat(emitted).containsExactly("a1", "a2", "a3", "a4", "a5", "a6");
        } finally {
            server.stop(0);
        }
    }

    private static void writeRecord(DataOutputStream data, int index) throws IOException {
        data.writeInt(2);
        data.writeByte(0);
        data.writeUTF("u");
        byte[] uinfo = ("org.example|a" + index + "|1.0|NA|jar").getBytes(StandardCharsets.UTF_8);
        data.writeInt(uinfo.length);
        data.write(uinfo);
        data.writeByte(0);
        data.writeUTF("i");
        byte[] info = "jar|0|0|0|0|0|jar".getBytes(StandardCharsets.UTF_8);
        data.writeInt(info.length);
        data.write(info);
    }
}
