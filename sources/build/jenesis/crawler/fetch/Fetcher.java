package build.jenesis.crawler.fetch;

import module java.base;
import build.jenesis.crawler.model.Version;
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
        // HTTP/1.1 across the board. HTTP/2's per-stream flow-control window (16 MB
        // default in JDK HttpClient, plus connection-level windows) lets the server
        // push gigabytes of DATA frames into the body subscriber's internal byte[]
        // deque before our reader / ofByteArray drains them - which produced 4 GB
        // heap dumps dominated by 16-64 KB byte[] in both the index-stream path and
        // the consumer's jar-fetch burst. HTTP/1.1 streams a single body over TCP
        // with kernel-level back-pressure: read() blocks the socket and the JDK
        // doesn't accumulate frame buffers. We lose multiplexing across the 96
        // concurrent jar fetches, but with virtual threads the cost of 96 idle
        // sockets is negligible, and the JDK's connection pool keeps reuse cheap.
        this.client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
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

    /**
     * Returns the resource's {@code Content-Length} via a HEAD request, or empty if the request
     * fails or the server doesn't declare a length. Used to seed byte-level progress reporting on
     * long-running streams (notably the Maven Central index gzip); the one extra round-trip is
     * insignificant against a multi-minute body. Failures here must not abort the stream that
     * follows, so any exception collapses to {@link OptionalLong#empty()} - callers degrade to
     * record-count progress.
     */
    public OptionalLong probeContentLength(URI uri) {
        HttpRequest request = builder(uri)
                .method("HEAD", HttpRequest.BodyPublishers.noBody())
                .build();
        try {
            HttpResponse<Void> response = send(request, HttpResponse.BodyHandlers.discarding());
            if (response.statusCode() / 100 != 2) {
                return OptionalLong.empty();
            }
            return response.headers().firstValueAsLong("content-length");
        } catch (IOException probeFailed) {
            return OptionalLong.empty();
        }
    }

    public InputStream resumableGet(URI uri) throws IOException {
        return new ResumableInputStream(uri, this::openRangeStream);
    }

    public static InputStream resumable(URI uri, RangeStreamOpener opener) throws IOException {
        return new ResumableInputStream(uri, opener);
    }

    /**
     * Opens the resumable index-streaming GET via {@link java.net.HttpURLConnection} rather than
     * the JDK's async {@link java.net.http.HttpClient}.
     *
     * <p>Why not HttpClient? The JDK's {@code HttpClient} runs an internal
     * {@code SelectorManager} thread that reads bytes from the socket as fast as the OS delivers
     * them, into a {@code Deque<ByteBuffer>} owned by the response body subscriber. Whatever
     * backpressure the subscriber claims to apply (the body handlers we care about - {@code
     * ofByteArray} calls {@code subscription.request(Long.MAX_VALUE)}, and {@code ofInputStream}
     * does request only {@code MAX_BUFFERS_IN_QUEUE = 1} but the underlying protocol decoder
     * still buffers eagerly), the practical end-to-end limit on receive buffering is the network
     * delivery rate, not our consumption rate. For the 3 GB GZIP index stream, where the producer
     * decompresses at ~13 MB/s and the network delivers at ~100 MB/s, that mismatch accumulated
     * 4 GB of 4-16 KB {@code byte[]} in the heap dumps - producing the OOMs we kept chasing.
     *
     * <p>{@code HttpURLConnection} is synchronous: socket reads happen on the application's
     * thread when it calls {@code InputStream.read()}. If the producer is slow, the socket sits
     * unread, the kernel TCP receive window shrinks, and the server backs off at the TCP layer.
     * Total in-flight bytes are bounded by {@code SO_RCVBUF} (typically 64-256 KB) instead of
     * "however much the server can push between now and OOM."
     *
     * <p>Trade-offs we accept: no HTTP/2, no async connection pool, no multiplexing. For one
     * big-body GET they're free; for the small per-jar fetches we keep {@code HttpClient}.
     */
    private OpenedRange openRangeStream(URI uri, long offset, String expectedEtag) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout((int) CONNECT_TIMEOUT.toMillis());
        connection.setReadTimeout((int) timeout.toMillis());
        connection.setInstanceFollowRedirects(true);
        connection.setRequestProperty("User-Agent", USER_AGENT);
        if (offset > 0L) {
            connection.setRequestProperty("Range", "bytes=" + offset + "-");
            if (expectedEtag != null) {
                connection.setRequestProperty("If-Range", expectedEtag);
            }
        }
        int status;
        String etag;
        try {
            status = connection.getResponseCode();
            etag = connection.getHeaderField("ETag");
        } catch (IOException headerFailure) {
            connection.disconnect();
            throw headerFailure;
        }
        if (status == 206) {
            if (expectedEtag != null && etag != null && !expectedEtag.equals(etag)) {
                drainAndDisconnect(connection);
                throw new ResourceChangedException("Resource " + uri + " ETag changed during streaming ("
                        + expectedEtag + " -> " + etag + ")");
            }
            return new OpenedRange(closingInputStream(connection, connection.getInputStream()),
                    etag != null ? etag : expectedEtag);
        }
        if (status == 200 && offset == 0L) {
            return new OpenedRange(closingInputStream(connection, connection.getInputStream()), etag);
        }
        drainAndDisconnect(connection);
        if (status == 200 && expectedEtag != null) {
            throw new ResourceChangedException("Resource " + uri + " changed during streaming"
                    + " (server returned 200 to a Range request with If-Range)");
        }
        throw new IOException("Resumable GET of " + uri + " at position " + offset + " returned status " + status);
    }

    private static InputStream closingInputStream(HttpURLConnection connection, InputStream body) {
        return new FilterInputStream(body) {
            @Override
            public void close() throws IOException {
                try {
                    super.close();
                } finally {
                    connection.disconnect();
                }
            }
        };
    }

    private static void drainAndDisconnect(HttpURLConnection connection) {
        try (InputStream errorBody = connection.getErrorStream()) {
            if (errorBody != null) {
                errorBody.transferTo(OutputStream.nullOutputStream());
            }
        } catch (IOException _) {
            // best-effort drain; falling through to disconnect below
        }
        connection.disconnect();
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

    public byte[] range(URI uri, long offset, int length) throws IOException {
        if (length <= 0) {
            return new byte[0];
        }
        // Synchronous fetch via HttpURLConnection rather than HttpClient.ofByteArray. Same
        // rationale as openRangeStream: ofByteArray calls subscription.request(Long.MAX_VALUE),
        // which under high concurrency (96 jar fetches in a burst) accumulates per-frame
        // ByteBuffer/byte[] until each body assembles - producing the gigabyte-scale receive
        // buffer leaks we saw in the heap dumps. HttpURLConnection reads the body on this
        // thread with kernel-level back-pressure; in-flight bytes are bounded by SO_RCVBUF.
        return urlConnectionRange(uri, offset, length).bytes();
    }

    /**
     * Same as {@link #range(URI, long, int)} but also returns the response's
     * {@code Last-Modified} (epoch millis, or {@code 0L} when the header is absent or
     * unparseable). Used by the scanner's small-jar fast path so the storage layer's
     * mtime can be persisted as the authoritative publication timestamp.
     */
    public RangedBody rangeWithLastModified(URI uri, long offset, int length) throws IOException {
        if (length <= 0) {
            return new RangedBody(new byte[0], 0L, false);
        }
        return urlConnectionRange(uri, offset, length);
    }

    public Tail tail(URI uri, int suffixLength) throws IOException {
        return urlConnectionTail(uri, suffixLength);
    }

    private RangedBody urlConnectionRange(URI uri, long offset, int length) throws IOException {
        HttpURLConnection connection = openSyncConnection(uri,
                conn -> conn.setRequestProperty("Range", "bytes=" + offset + "-" + (offset + length - 1)));
        try {
            int status = connection.getResponseCode();
            if (status != 206 && status != 200) {
                drainAndDisconnect(connection);
                throw new IOException("Range " + offset + ".." + (offset + length - 1)
                        + " on " + uri + " returned status " + status);
            }
            byte[] body = readBoundedBody(connection, length, status == 200);
            LastModified stamp = preferredLastModified(connection);
            if (status == 200 && body.length != length) {
                byte[] sliced = offset == 0L
                        ? body
                        : Arrays.copyOfRange(body, (int) offset, (int) Math.min(offset + length, body.length));
                return new RangedBody(sliced, stamp.millis(), stamp.canonical());
            }
            return new RangedBody(body, stamp.millis(), stamp.canonical());
        } finally {
            connection.disconnect();
        }
    }

    private Tail urlConnectionTail(URI uri, int suffixLength) throws IOException {
        HttpURLConnection connection = openSyncConnection(uri,
                conn -> conn.setRequestProperty("Range", "bytes=-" + suffixLength));
        try {
            int status = connection.getResponseCode();
            if (status != 206 && status != 200) {
                drainAndDisconnect(connection);
                throw new IOException("Tail request on " + uri + " returned status " + status);
            }
            byte[] body = readBoundedBody(connection, suffixLength, status == 200);
            long total = parseTotalFromContentRange(connection.getHeaderField("Content-Range"));
            if (total < 0L) {
                long contentLength = connection.getContentLengthLong();
                total = contentLength >= 0L ? contentLength : body.length;
            }
            LastModified stamp = preferredLastModified(connection);
            return new Tail(body, total, stamp.millis(), stamp.canonical());
        } finally {
            connection.disconnect();
        }
    }

    /**
     * Pair of "best Last-Modified the response can provide" and a flag indicating whether
     * that value came from a header known to preserve the original publication time
     * (the GCS mirror's {@code x-goog-meta-last-modified}). When {@code canonical} is
     * {@code false}, the {@code millis} value is the plain HTTP {@code Last-Modified}: still
     * useful, but on the GCS mirror it can reflect the bucket-landing time rather than the
     * publish time (notably for pre-2019 bulk-imported artifacts).
     */
    private record LastModified(long millis, boolean canonical) {

        static final LastModified NONE = new LastModified(0L, false);
    }

    private static LastModified preferredLastModified(HttpURLConnection connection) {
        String googMeta = connection.getHeaderField("x-goog-meta-last-modified");
        if (googMeta != null) {
            long parsed = parseHttpDate(googMeta);
            if (parsed > 0L) {
                return new LastModified(parsed, true);
            }
        }
        long standard = connection.getLastModified();
        if (standard <= 0L) {
            return LastModified.NONE;
        }
        // Mark non-canonical only when the response is from a mirror known to rewrite
        // mtimes. Sonatype's GCS mirror tags every response with x-goog-* headers, so the
        // absence of any such header means we're talking to a source that preserves the
        // upstream Last-Modified (Maven Central direct, an internal mirror, or a test
        // fixture); trust it.
        boolean fromGcs = connection.getHeaderFields().keySet().stream()
                .filter(name -> name != null)
                .anyMatch(name -> name.regionMatches(true, 0, "x-goog-", 0, 7));
        return new LastModified(standard, !fromGcs);
    }

    // Sonatype's GCS bucket sometimes records x-goog-meta-last-modified with a single-digit
    // hour (e.g. "Mon, 31 Mar 2025 6:21:43 GMT") which RFC_1123_DATE_TIME rejects. Zero-pad
    // the hour before parsing.
    private static final Pattern SINGLE_DIGIT_HOUR = Pattern.compile(" (\\d):(\\d{2}):");

    private static long parseHttpDate(String value) {
        String normalised = SINGLE_DIGIT_HOUR.matcher(value).replaceFirst(" 0$1:$2:");
        try {
            return ZonedDateTime.parse(normalised, DateTimeFormatter.RFC_1123_DATE_TIME)
                    .toInstant().toEpochMilli();
        } catch (DateTimeParseException invalid) {
            return 0L;
        }
    }

    /**
     * Outcome of a {@link #headLastModifiedProbe} call. {@code lastModifiedMillis} is the
     * timestamp we could extract from the response (or {@code 0L} when nothing usable came
     * back); {@code status} is the HTTP status code ({@code 0} when the request never made
     * it that far); {@code error} is a short human-readable string when an exception was
     * raised (or {@code null} on a normal response, even a 4xx).
     */
    public record HeadProbe(long lastModifiedMillis, boolean canonical, int status, String error) {

        public boolean ok() {
            return lastModifiedMillis > 0L;
        }
    }

    /**
     * One-shot HEAD against {@code uri}. Returns the canonical publication timestamp along
     * with the HTTP status and any error string for diagnostics. Never throws: network or
     * HTTP failures are encoded in the returned {@link HeadProbe}.
     */
    public HeadProbe headLastModifiedProbe(URI uri) {
        HttpRequest request = builder(uri)
                .method("HEAD", HttpRequest.BodyPublishers.noBody())
                .build();
        try {
            HttpResponse<Void> response = send(request, HttpResponse.BodyHandlers.discarding());
            int status = response.statusCode();
            if (status / 100 != 2) {
                return new HeadProbe(0L, false, status, null);
            }
            LastModified stamp = preferredLastModifiedFromHeaders(response.headers());
            return new HeadProbe(stamp.millis(), stamp.canonical(), status,
                    stamp.millis() > 0L ? null : "no Last-Modified header");
        } catch (IOException error) {
            return new HeadProbe(0L, false, 0, error.getClass().getSimpleName() + ": " + error.getMessage());
        }
    }

    /**
     * HttpClient-side equivalent of {@link #preferredLastModified(HttpURLConnection)}.
     * Reuses the same goog-meta-vs-Last-Modified preference so the canonical-flag semantics
     * match across the two HEAD/GET paths.
     */
    private static LastModified preferredLastModifiedFromHeaders(HttpHeaders headers) {
        Optional<String> googMeta = headers.firstValue("x-goog-meta-last-modified");
        if (googMeta.isPresent()) {
            long parsed = parseHttpDate(googMeta.get());
            if (parsed > 0L) {
                return new LastModified(parsed, true);
            }
        }
        Optional<String> standard = headers.firstValue("last-modified");
        if (standard.isEmpty()) {
            return LastModified.NONE;
        }
        long parsedStandard = parseHttpDate(standard.get());
        if (parsedStandard <= 0L) {
            return LastModified.NONE;
        }
        boolean fromGcs = headers.map().keySet().stream()
                .anyMatch(name -> name.regionMatches(true, 0, "x-goog-", 0, 7));
        return new LastModified(parsedStandard, !fromGcs);
    }

    /**
     * Thin wrapper around {@link #headLastModifiedProbe} for callers that only need the
     * timestamp value. Returns {@code 0L} on any failure.
     */
    public long headLastModified(URI uri) {
        return headLastModifiedProbe(uri).lastModifiedMillis();
    }

    private HttpURLConnection openSyncConnection(URI uri, Consumer<HttpURLConnection> configurator) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout((int) CONNECT_TIMEOUT.toMillis());
        connection.setReadTimeout((int) timeout.toMillis());
        connection.setInstanceFollowRedirects(true);
        connection.setRequestProperty("User-Agent", USER_AGENT);
        configurator.accept(connection);
        return connection;
    }

    private static byte[] readBoundedBody(HttpURLConnection connection, int expected, boolean wholeBody) throws IOException {
        try (InputStream body = connection.getInputStream()) {
            if (wholeBody) {
                return body.readAllBytes();
            }
            // 206 response is exactly the bytes we asked for - read up to expected, fall back
            // to readAllBytes if the server delivers fewer than declared.
            byte[] buffer = new byte[expected];
            int read = 0;
            while (read < expected) {
                int n = body.read(buffer, read, expected - read);
                if (n < 0) break;
                read += n;
            }
            if (read == expected) {
                return buffer;
            }
            return Arrays.copyOf(buffer, read);
        }
    }

    private static long parseTotalFromContentRange(String header) {
        if (header == null) {
            return -1L;
        }
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

    public ByteSource sourceWithCachedTail(URI uri, int suffixLength) throws IOException {
        Tail tail = tail(uri, suffixLength);
        long total = tail.totalSize();
        long tailStart = total - tail.bytes().length;
        byte[] bytes = tail.bytes();
        long lastModified = tail.lastModifiedMillis();
        boolean canonical = tail.lastModifiedCanonical();
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

            @Override
            public long lastModifiedMillis() {
                return lastModified;
            }

            @Override
            public boolean lastModifiedCanonical() {
                return canonical;
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

    public record Tail(byte[] bytes, long totalSize, long lastModifiedMillis, boolean lastModifiedCanonical) {
    }

    /**
     * Body of a successful range fetch, paired with the best {@code Last-Modified} the
     * response could provide (epoch millis, or {@code 0L} when both
     * {@code x-goog-meta-last-modified} and {@code Last-Modified} are absent or unparseable).
     * {@code lastModifiedCanonical} is {@code true} when the value came from
     * {@code x-goog-meta-last-modified} (the upstream Maven Central {@code Last-Modified}
     * preserved on GCS bucket objects), {@code false} when it's the plain {@code Last-Modified}
     * (which on GCS can reflect bucket-landing time rather than publish time for pre-2019
     * bulk-imported artifacts).
     */
    public record RangedBody(byte[] bytes, long lastModifiedMillis, boolean lastModifiedCanonical) {
    }

    private HttpRequest.Builder builder(URI uri) {
        return HttpRequest.newBuilder(uri)
                .timeout(timeout)
                .header("User-Agent", USER_AGENT);
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
                    System.err.println("[fetcher] resumable read of " + uri + " failed at byte " + position
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
