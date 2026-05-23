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
}
