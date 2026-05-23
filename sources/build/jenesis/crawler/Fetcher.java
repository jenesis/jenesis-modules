package build.jenesis.crawler;

import module java.base;
import module java.net.http;

public final class Fetcher implements AutoCloseable {

    public static final Duration DEFAULT_TIMEOUT = Duration.ofMinutes(2);
    public static final Duration CONNECT_TIMEOUT = Duration.ofSeconds(15);
    public static final int DEFAULT_RETRIES = 3;
    public static final String USER_AGENT_PRODUCT = "JenesisModulesCrawler";
    public static final String USER_AGENT_VERSION = "1.0";
    public static final String USER_AGENT = USER_AGENT_PRODUCT + "/" + USER_AGENT_VERSION
            + " (+https://github.com/raphw/jenesis-modules)";
    public static final long MAX_RETRY_AFTER_MILLIS = Duration.ofMinutes(5).toMillis();

    private final HttpClient client;
    private final Duration timeout;
    private final int retries;

    public Fetcher() {
        this(DEFAULT_TIMEOUT, DEFAULT_RETRIES);
    }

    public Fetcher(Duration timeout, int retries) {
        this.timeout = Objects.requireNonNull(timeout, "timeout");
        this.retries = retries;
        this.client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(CONNECT_TIMEOUT)
                .build();
    }

    public InputStream get(URI uri) throws IOException {
        HttpRequest request = builder(uri).GET().build();
        HttpResponse<InputStream> response = send(request, HttpResponse.BodyHandlers.ofInputStream());
        if (response.statusCode() / 100 != 2) {
            response.body().close();
            throw new IOException("GET " + uri + " returned status " + response.statusCode());
        }
        return response.body();
    }

    public boolean probeRangeSupport(URI uri) {
        HttpRequest request = builder(uri)
                .GET()
                .header("Range", "bytes=0-0")
                .build();
        try {
            HttpResponse<Void> response = send(request, HttpResponse.BodyHandlers.discarding());
            return response.statusCode() == 206;
        } catch (IOException probeFailed) {
            return false;
        }
    }

    public InputStream resumableGet(URI uri) throws IOException {
        return new ResumableInputStream(uri, this::openRangeStream);
    }

    public static InputStream resumable(URI uri, RangeStreamOpener opener) throws IOException {
        return new ResumableInputStream(uri, opener);
    }

    private OpenedRange openRangeStream(URI uri, long offset, String expectedEtag) throws IOException {
        // Force HTTP/1.1 for the body-streaming GET. The JDK HttpClient's HTTP/2
        // receive pipeline buffers DATA frames per-stream up to the flow-control
        // window (default 16 MB per stream, larger at connection level), and that
        // buffering ignores how slowly the application drains the InputStream.
        // For a 3 GB GZIP body read at ~4 MB/s by a single producer thread, the
        // result was ~4 GB of live 16-64 KB byte[] receive buffers (confirmed via
        // heap dump - 241 K live arrays totalling 4.03 GB) before OOM. HTTP/1.1's
        // body is a single TCP stream with kernel-level back-pressure, so read()
        // blocks the socket and the JDK doesn't accumulate frame buffers.
        // The jar-fetch range requests (which use the client's HTTP/2 default)
        // are small and finish quickly, so they don't have the same problem.
        HttpRequest.Builder reqBuilder = builder(uri)
                .GET()
                .version(HttpClient.Version.HTTP_1_1);
        if (offset > 0L) {
            reqBuilder.header("Range", "bytes=" + offset + "-");
            if (expectedEtag != null) {
                reqBuilder.header("If-Range", expectedEtag);
            }
        }
        HttpResponse<InputStream> response = send(reqBuilder.build(), HttpResponse.BodyHandlers.ofInputStream());
        int status = response.statusCode();
        String etag = response.headers().firstValue("etag").orElse(null);
        if (status == 206) {
            if (expectedEtag != null && etag != null && !expectedEtag.equals(etag)) {
                response.body().close();
                throw new ResourceChangedException("Resource " + uri + " ETag changed during streaming ("
                        + expectedEtag + " -> " + etag + ")");
            }
            return new OpenedRange(response.body(), etag != null ? etag : expectedEtag);
        }
        if (status == 200 && offset == 0L) {
            return new OpenedRange(response.body(), etag);
        }
        response.body().close();
        if (status == 200 && expectedEtag != null) {
            throw new ResourceChangedException("Resource " + uri + " changed during streaming"
                    + " (server returned 200 to a Range request with If-Range)");
        }
        throw new IOException("Resumable GET of " + uri + " at position " + offset + " returned status " + status);
    }

    @FunctionalInterface
    public interface RangeStreamOpener {
        OpenedRange open(URI uri, long offset, String expectedEtag) throws IOException;
    }

    public record OpenedRange(InputStream stream, String etag) {
    }

    public static final class ResourceChangedException extends IOException {

        public ResourceChangedException(String message) {
            super(message);
        }
    }

    public long size(URI uri) throws IOException {
        HttpRequest request = builder(uri)
                .method("HEAD", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<Void> response = send(request, HttpResponse.BodyHandlers.discarding());
        if (response.statusCode() / 100 != 2) {
            throw new IOException("HEAD " + uri + " returned status " + response.statusCode());
        }
        OptionalLong length = response.headers().firstValueAsLong("content-length");
        if (length.isEmpty()) {
            throw new IOException("Missing Content-Length for " + uri);
        }
        return length.getAsLong();
    }

    public byte[] range(URI uri, long offset, int length) throws IOException {
        if (length <= 0) {
            return new byte[0];
        }
        HttpRequest request = builder(uri)
                .GET()
                .header("Range", "bytes=" + offset + "-" + (offset + length - 1))
                .build();
        HttpResponse<byte[]> response = send(request, HttpResponse.BodyHandlers.ofByteArray());
        int status = response.statusCode();
        if (status != 206 && status != 200) {
            throw new IOException("Range " + offset + ".." + (offset + length - 1) + " on " + uri + " returned status " + status);
        }
        byte[] body = response.body();
        if (status == 200 && body.length != length) {
            if (offset == 0L) {
                return body;
            }
            return Arrays.copyOfRange(body, (int) offset, (int) Math.min(offset + length, body.length));
        }
        return body;
    }

    public ByteSource source(URI uri) throws IOException {
        long total = size(uri);
        return new ByteSource() {

            @Override
            public long size() {
                return total;
            }

            @Override
            public byte[] read(long offset, int length) throws IOException {
                return range(uri, offset, length);
            }
        };
    }

    public Tail tail(URI uri, int suffixLength) throws IOException {
        HttpRequest request = builder(uri)
                .GET()
                .header("Range", "bytes=-" + suffixLength)
                .build();
        HttpResponse<byte[]> response = send(request, HttpResponse.BodyHandlers.ofByteArray());
        int status = response.statusCode();
        if (status != 206 && status != 200) {
            throw new IOException("Tail request on " + uri + " returned status " + status);
        }
        byte[] body = response.body();
        long total = response.headers()
                .firstValue("content-range")
                .map(Fetcher::parseTotalFromContentRange)
                .orElseGet(() -> response.headers().firstValueAsLong("content-length").orElse(body.length));
        return new Tail(body, total);
    }

    public ByteSource sourceWithCachedTail(URI uri, int suffixLength) throws IOException {
        Tail tail = tail(uri, suffixLength);
        long total = tail.totalSize();
        long tailStart = total - tail.bytes().length;
        byte[] bytes = tail.bytes();
        return new ByteSource() {

            @Override
            public long size() {
                return total;
            }

            @Override
            public byte[] read(long offset, int length) throws IOException {
                if (offset >= tailStart && offset + length <= total) {
                    int positionInTail = (int) (offset - tailStart);
                    return Arrays.copyOfRange(bytes, positionInTail, positionInTail + length);
                }
                return range(uri, offset, length);
            }
        };
    }

    public Optional<String> getOptional(URI uri) throws IOException {
        HttpRequest request = builder(uri).GET().build();
        HttpResponse<String> response = send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        int status = response.statusCode();
        if (status == 404 || status == 410) {
            return Optional.empty();
        }
        if (status / 100 != 2) {
            throw new IOException("GET " + uri + " returned status " + status);
        }
        return Optional.of(response.body());
    }

    public record Tail(byte[] bytes, long totalSize) {
    }

    private HttpRequest.Builder builder(URI uri) {
        return HttpRequest.newBuilder(uri)
                .timeout(timeout)
                .header("User-Agent", USER_AGENT);
    }

    private static long parseTotalFromContentRange(String header) {
        int slash = header.lastIndexOf('/');
        if (slash < 0 || slash == header.length() - 1) {
            return -1L;
        }
        String suffix = header.substring(slash + 1).trim();
        if (suffix.equals("*")) {
            return -1L;
        }
        try {
            return Long.parseLong(suffix);
        } catch (NumberFormatException invalid) {
            return -1L;
        }
    }

    private <T> HttpResponse<T> send(HttpRequest request, HttpResponse.BodyHandler<T> handler) throws IOException {
        IOException lastError = null;
        for (int attempt = 0; attempt <= retries; attempt++) {
            try {
                HttpResponse<T> response = client.send(request, handler);
                int status = response.statusCode();
                if ((status == 429 || status == 503) && attempt < retries) {
                    closeBody(response);
                    long delayMillis = parseRetryAfter(response).orElse(backoffMillis(attempt));
                    sleep(Math.min(delayMillis, MAX_RETRY_AFTER_MILLIS), request.uri());
                    continue;
                }
                return response;
            } catch (HttpTimeoutException timeout) {
                lastError = timeout;
            } catch (IOException e) {
                lastError = e;
            } catch (InterruptedException interrupted) {
                Thread.currentThread().interrupt();
                throw new IOException("Interrupted while sending " + request.uri(), interrupted);
            }
            if (attempt < retries) {
                sleep(backoffMillis(attempt), request.uri());
            }
        }
        throw new IOException("Exhausted " + retries + " retries for " + request.uri(), lastError);
    }

    private static long backoffMillis(int attempt) {
        return 250L * (1L << attempt);
    }

    private static void sleep(long millis, URI uri) throws IOException {
        if (millis <= 0L) {
            return;
        }
        try {
            Thread.sleep(millis);
        } catch (InterruptedException interrupted) {
            Thread.currentThread().interrupt();
            throw new IOException("Interrupted while waiting to retry " + uri, interrupted);
        }
    }

    private static OptionalLong parseRetryAfter(HttpResponse<?> response) {
        Optional<String> value = response.headers().firstValue("retry-after");
        if (value.isEmpty()) {
            return OptionalLong.empty();
        }
        String text = value.get().trim();
        try {
            long seconds = Long.parseLong(text);
            return seconds >= 0L ? OptionalLong.of(TimeUnit.SECONDS.toMillis(seconds)) : OptionalLong.empty();
        } catch (NumberFormatException notSeconds) {
            // Fall through to HTTP-date parsing.
        }
        try {
            Instant when = ZonedDateTime.parse(text, DateTimeFormatter.RFC_1123_DATE_TIME).toInstant();
            long millis = when.toEpochMilli() - System.currentTimeMillis();
            return OptionalLong.of(Math.max(0L, millis));
        } catch (DateTimeParseException invalid) {
            return OptionalLong.empty();
        }
    }

    private static void closeBody(HttpResponse<?> response) {
        Object body = response.body();
        if (body instanceof AutoCloseable closeable) {
            try {
                closeable.close();
            } catch (Exception _) {
            }
        }
    }

    @Override
    public void close() {
        client.close();
    }

    private static final class ResumableInputStream extends InputStream {

        private static final int MAX_RECONNECTS = 5;
        private static final Duration INITIAL_BACKOFF = Duration.ofMillis(100L);

        private final URI uri;
        private final RangeStreamOpener opener;
        private InputStream current;
        private String etag;
        private long position;
        private int reconnects;

        ResumableInputStream(URI uri, RangeStreamOpener opener) throws IOException {
            this.uri = uri;
            this.opener = opener;
            this.position = 0L;
            this.reconnects = 0;
            OpenedRange opened = opener.open(uri, 0L, null);
            this.current = opened.stream();
            this.etag = opened.etag();
        }

        @Override
        public int read() throws IOException {
            byte[] one = new byte[1];
            int read = read(one, 0, 1);
            return read < 0 ? -1 : one[0] & 0xFF;
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            for (;;) {
                try {
                    int read = current.read(b, off, len);
                    if (read > 0) {
                        position += read;
                        reconnects = 0;
                    }
                    return read;
                } catch (ResourceChangedException fatal) {
                    throw fatal;
                } catch (IOException failure) {
                    if (++reconnects > MAX_RECONNECTS) {
                        throw failure;
                    }
                    System.err.println("Resumable read of " + uri + " failed at byte " + position
                            + " (attempt " + reconnects + "/" + MAX_RECONNECTS
                            + "): " + failure.getClass().getSimpleName() + ": " + failure.getMessage()
                            + ". Re-issuing Range request.");
                    closeQuietly(current);
                    sleepBackoff();
                    OpenedRange opened = opener.open(uri, position, etag);
                    current = opened.stream();
                    if (opened.etag() != null) {
                        etag = opened.etag();
                    }
                }
            }
        }

        private void sleepBackoff() throws IOException {
            long shift = Math.min(reconnects - 1, 4);
            long millis = INITIAL_BACKOFF.toMillis() << shift;
            try {
                Thread.sleep(millis);
            } catch (InterruptedException interrupted) {
                Thread.currentThread().interrupt();
                throw new IOException("Interrupted during resumable read backoff", interrupted);
            }
        }

        @Override
        public void close() throws IOException {
            closeQuietly(current);
        }

        private static void closeQuietly(InputStream stream) {
            if (stream == null) {
                return;
            }
            try {
                stream.close();
            } catch (IOException _) {
            }
        }
    }
}
