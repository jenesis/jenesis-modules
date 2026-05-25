package build.jenesis.crawler.test;

import module java.base;
import build.jenesis.crawler.fetch.Fetcher;
import module jdk.httpserver;
import module org.junit.jupiter.api;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FetcherTest {

    @Test
    public void probe_returns_true_when_server_replies_206_to_a_one_byte_range() throws Exception {
        byte[] payload = deterministicPayload(1024);
        HttpServer server = startRangeAwareServer(payload);
        try (Fetcher fetcher = new Fetcher(Duration.ofSeconds(5L), 0)) {
            assertThat(fetcher.probeRangeSupport(uriOf(server))).isTrue();
        } finally {
            server.stop(0);
        }
    }

    @Test
    public void probe_returns_false_when_server_ignores_the_range_header() throws Exception {
        byte[] payload = deterministicPayload(64);
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext("/data", exchange -> {
            try {
                exchange.sendResponseHeaders(200, payload.length);
                try (OutputStream out = exchange.getResponseBody()) {
                    out.write(payload);
                }
            } catch (IOException _) {
            } finally {
                exchange.close();
            }
        });
        server.start();
        try (Fetcher fetcher = new Fetcher(Duration.ofSeconds(5L), 0)) {
            assertThat(fetcher.probeRangeSupport(uriOf(server))).isFalse();
        } finally {
            server.stop(0);
        }
    }

    @Test
    public void resumable_get_recovers_from_mid_stream_failure_via_range() throws Exception {
        byte[] payload = deterministicPayload(8192);
        AtomicInteger remainingFailures = new AtomicInteger(1);
        Fetcher.RangeStreamOpener opener = (uri, offset, expectedEtag) -> {
            if (offset == 0L && remainingFailures.getAndDecrement() > 0) {
                return new Fetcher.OpenedRange(new FailingInputStream(payload, 0, payload.length / 2), "etag-1");
            }
            return new Fetcher.OpenedRange(
                    new ByteArrayInputStream(payload, (int) offset, payload.length - (int) offset),
                    "etag-1");
        };
        try (InputStream resumable = Fetcher.resumable(URI.create("test://payload"), opener)) {
            byte[] read = resumable.readAllBytes();
            assertThat(read).containsExactly(payload);
        }
    }

    @Test
    public void resumable_get_stops_after_max_reconnects_when_no_progress_is_made() throws Exception {
        byte[] payload = deterministicPayload(4096);
        AtomicInteger openCalls = new AtomicInteger(0);
        Fetcher.RangeStreamOpener opener = (uri, offset, expectedEtag) -> {
            openCalls.incrementAndGet();
            return new Fetcher.OpenedRange(new FailingInputStream(payload, (int) offset, 0), "etag-1");
        };
        try (InputStream resumable = Fetcher.resumable(URI.create("test://payload"), opener)) {
            assertThatThrownBy(() -> resumable.readAllBytes())
                    .isInstanceOf(IOException.class)
                    .hasMessageContaining("simulated mid-stream failure");
        }
        assertThat(openCalls.get()).isLessThanOrEqualTo(10);
    }

    @Test
    public void resumable_get_aborts_with_resource_changed_when_etag_diverges_on_reconnect() throws Exception {
        byte[] payload = deterministicPayload(8192);
        AtomicInteger openCalls = new AtomicInteger(0);
        Fetcher.RangeStreamOpener opener = (uri, offset, expectedEtag) -> {
            int call = openCalls.incrementAndGet();
            if (call == 1) {
                return new Fetcher.OpenedRange(new FailingInputStream(payload, 0, payload.length / 2), "etag-original");
            }
            assertThat(expectedEtag).isEqualTo("etag-original");
            throw new Fetcher.ResourceChangedException("simulated regeneration; etag now etag-new");
        };
        try (InputStream resumable = Fetcher.resumable(URI.create("test://payload"), opener)) {
            assertThatThrownBy(() -> resumable.readAllBytes())
                    .isInstanceOf(Fetcher.ResourceChangedException.class)
                    .hasMessageContaining("simulated regeneration");
        }
        assertThat(openCalls.get()).isEqualTo(2);
    }

    private static URI uriOf(HttpServer server) {
        return URI.create("http://127.0.0.1:" + server.getAddress().getPort() + "/data");
    }

    private static HttpServer startRangeAwareServer(byte[] payload) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext("/data", exchange -> {
            try {
                String rangeHeader = exchange.getRequestHeaders().getFirst("Range");
                if (rangeHeader != null && rangeHeader.startsWith("bytes=")) {
                    serveRange(exchange, payload, rangeHeader);
                } else {
                    exchange.sendResponseHeaders(200, payload.length);
                    try (OutputStream out = exchange.getResponseBody()) {
                        out.write(payload);
                    }
                }
            } catch (IOException _) {
            } finally {
                exchange.close();
            }
        });
        server.start();
        return server;
    }

    private static void serveRange(HttpExchange exchange, byte[] payload, String rangeHeader) throws IOException {
        String spec = rangeHeader.substring("bytes=".length());
        int dash = spec.indexOf('-');
        long start = dash > 0 ? Long.parseLong(spec.substring(0, dash)) : 0L;
        String endText = dash >= 0 ? spec.substring(dash + 1).trim() : "";
        long end = endText.isEmpty() ? payload.length - 1 : Math.min(payload.length - 1, Long.parseLong(endText));
        long length = end - start + 1;
        exchange.getResponseHeaders().add("Content-Range", "bytes " + start + "-" + end + "/" + payload.length);
        exchange.sendResponseHeaders(206, length);
        try (OutputStream out = exchange.getResponseBody()) {
            out.write(payload, (int) start, (int) length);
        }
    }

    private static byte[] deterministicPayload(int size) {
        byte[] bytes = new byte[size];
        for (int index = 0; index < size; index++) {
            bytes[index] = (byte) ((index * 31 + 7) & 0xFF);
        }
        return bytes;
    }

    private static final class FailingInputStream extends InputStream {

        private final byte[] data;
        private final int start;
        private final int failAfter;
        private int consumed;

        FailingInputStream(byte[] data, int start, int failAfter) {
            this.data = data;
            this.start = start;
            this.failAfter = failAfter;
            this.consumed = 0;
        }

        @Override
        public int read() throws IOException {
            byte[] one = new byte[1];
            int read = read(one, 0, 1);
            return read < 0 ? -1 : one[0] & 0xFF;
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            if (consumed >= failAfter) {
                throw new IOException("simulated mid-stream failure after " + consumed + " bytes");
            }
            int remaining = failAfter - consumed;
            int take = Math.min(len, remaining);
            System.arraycopy(data, start + consumed, b, off, take);
            consumed += take;
            return take;
        }
    }
}
