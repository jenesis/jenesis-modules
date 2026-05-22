package build.jenesis.modules;

import module java.base;
import module java.net.http;

public final class Fetcher implements AutoCloseable {

    public static final Duration DEFAULT_TIMEOUT = Duration.ofMinutes(2);
    public static final Duration CONNECT_TIMEOUT = Duration.ofSeconds(15);
    public static final int DEFAULT_RETRIES = 3;

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
        HttpRequest request = HttpRequest.newBuilder(uri).GET().timeout(timeout).build();
        HttpResponse<InputStream> response = send(request, HttpResponse.BodyHandlers.ofInputStream());
        if (response.statusCode() / 100 != 2) {
            response.body().close();
            throw new IOException("GET " + uri + " returned status " + response.statusCode());
        }
        return response.body();
    }

    public long size(URI uri) throws IOException {
        HttpRequest request = HttpRequest.newBuilder(uri)
                .method("HEAD", HttpRequest.BodyPublishers.noBody())
                .timeout(timeout)
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
        HttpRequest request = HttpRequest.newBuilder(uri)
                .GET()
                .header("Range", "bytes=" + offset + "-" + (offset + length - 1))
                .timeout(timeout)
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
        HttpRequest request = HttpRequest.newBuilder(uri)
                .GET()
                .header("Range", "bytes=-" + suffixLength)
                .timeout(timeout)
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

    public record Tail(byte[] bytes, long totalSize) {
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
                return client.send(request, handler);
            } catch (HttpTimeoutException timeout) {
                lastError = timeout;
            } catch (IOException io) {
                lastError = io;
            } catch (InterruptedException interrupted) {
                Thread.currentThread().interrupt();
                throw new IOException("Interrupted while sending " + request.uri(), interrupted);
            }
            if (attempt < retries) {
                long delayMillis = 250L * (1L << attempt);
                try {
                    Thread.sleep(delayMillis);
                } catch (InterruptedException interrupted) {
                    Thread.currentThread().interrupt();
                    throw new IOException("Interrupted while waiting to retry " + request.uri(), interrupted);
                }
            }
        }
        throw new IOException("Exhausted " + retries + " retries for " + request.uri(), lastError);
    }

    @Override
    public void close() {
        client.close();
    }
}
