package build.jenesis.crawler.test;

import module java.base;
import module jdk.httpserver;

public final class FakeMavenCentral implements AutoCloseable {

    public record IndexedJar(String groupId, String artifactId, String version, byte[] jarBytes) {
        public String mavenPath() {
            return groupId.replace('.', '/') + "/" + artifactId + "/" + version
                    + "/" + artifactId + "-" + version + ".jar";
        }
    }

    private final HttpServer server;
    private final Map<String, byte[]> artifactBodies = new ConcurrentHashMap<>();
    private final Map<String, byte[]> indexBodies = new ConcurrentHashMap<>();
    private volatile byte[] propertiesBody = new byte[0];

    public FakeMavenCentral() throws IOException {
        this.server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext("/robots.txt", exchange -> {
            try {
                exchange.sendResponseHeaders(404, -1);
            } finally {
                exchange.close();
            }
        });
        server.createContext("/maven2/", this::handleMaven);
        server.start();
    }

    public int port() {
        return server.getAddress().getPort();
    }

    public URI indexBaseUri() {
        return URI.create("http://127.0.0.1:" + port() + "/maven2/.index/");
    }

    public URI artifactBaseUri() {
        return URI.create("http://127.0.0.1:" + port() + "/maven2/");
    }

    /**
     * Replace the {@code .properties} file the server returns. {@code chainId} and {@code lastIncremental}
     * are written verbatim; callers control when to advance them.
     */
    public void setIndexProperties(String chainId, int lastIncremental, long timestampEpochMillis) {
        Instant when = Instant.ofEpochMilli(timestampEpochMillis);
        String stamp = DateTimeFormatter.ofPattern("yyyyMMddHHmmss.SSS Z")
                .withZone(ZoneOffset.UTC)
                .format(when);
        String text = "nexus.index.chain-id=" + chainId + "\n"
                + "nexus.index.last-incremental=" + lastIncremental + "\n"
                + "nexus.index.timestamp=" + stamp + "\n";
        propertiesBody = text.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * Publish (or replace) the full index at {@code nexus-maven-repository-index.gz}. The jars are
     * also exposed via their Maven coordinates so that the crawler can fetch them.
     */
    public void publishFullIndex(long timestampEpochMillis, List<IndexedJar> jars) throws IOException {
        publishJars(jars);
        indexBodies.put("nexus-maven-repository-index.gz", buildIndex(timestampEpochMillis, jars));
    }

    /**
     * Publish (or replace) an incremental at {@code nexus-maven-repository-index.<n>.gz}.
     */
    public void publishIncremental(int incrementalNumber, long timestampEpochMillis, List<IndexedJar> jars) throws IOException {
        publishJars(jars);
        indexBodies.put("nexus-maven-repository-index." + incrementalNumber + ".gz",
                buildIndex(timestampEpochMillis, jars));
    }

    private void publishJars(List<IndexedJar> jars) {
        for (IndexedJar jar : jars) {
            artifactBodies.put(jar.mavenPath(), jar.jarBytes());
        }
    }

    private void handleMaven(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        try {
            if (path.equals("/maven2/.index/nexus-maven-repository-index.properties")) {
                serveBytes(exchange, propertiesBody);
                return;
            }
            if (path.startsWith("/maven2/.index/")) {
                String name = path.substring("/maven2/.index/".length());
                byte[] body = indexBodies.get(name);
                if (body == null) {
                    exchange.sendResponseHeaders(404, -1);
                    return;
                }
                serveBytes(exchange, body);
                return;
            }
            if (path.startsWith("/maven2/")) {
                String mavenPath = path.substring("/maven2/".length());
                byte[] body = artifactBodies.get(mavenPath);
                if (body == null) {
                    exchange.sendResponseHeaders(404, -1);
                    return;
                }
                serveBytes(exchange, body);
                return;
            }
            exchange.sendResponseHeaders(404, -1);
        } finally {
            exchange.close();
        }
    }

    private void serveBytes(HttpExchange exchange, byte[] body) throws IOException {
        exchange.getResponseHeaders().add("ETag", "\"etag-" + Integer.toHexString(Arrays.hashCode(body)) + "\"");
        exchange.getResponseHeaders().add("Accept-Ranges", "bytes");
        String rangeHeader = exchange.getRequestHeaders().getFirst("Range");
        if (rangeHeader != null && rangeHeader.startsWith("bytes=")) {
            String spec = rangeHeader.substring("bytes=".length()).trim();
            long start;
            long end;
            if (spec.startsWith("-")) {
                long suffix = Long.parseLong(spec.substring(1));
                start = Math.max(0L, body.length - suffix);
                end = body.length - 1L;
            } else {
                int dash = spec.indexOf('-');
                start = Long.parseLong(spec.substring(0, dash));
                String endText = spec.substring(dash + 1).trim();
                end = endText.isEmpty() ? body.length - 1L : Math.min(body.length - 1L, Long.parseLong(endText));
            }
            if (start > end || start >= body.length) {
                exchange.sendResponseHeaders(416, -1);
                return;
            }
            long length = end - start + 1L;
            exchange.getResponseHeaders().add("Content-Range", "bytes " + start + "-" + end + "/" + body.length);
            exchange.sendResponseHeaders(206, length);
            try (OutputStream out = exchange.getResponseBody()) {
                out.write(body, (int) start, (int) length);
            }
            return;
        }
        exchange.sendResponseHeaders(200, body.length);
        try (OutputStream out = exchange.getResponseBody()) {
            out.write(body);
        }
    }

    private static byte[] buildIndex(long timestampEpochMillis, List<IndexedJar> jars) throws IOException {
        ByteArrayOutputStream gz = new ByteArrayOutputStream();
        try (GZIPOutputStream gzip = new GZIPOutputStream(gz);
             DataOutputStream out = new DataOutputStream(gzip)) {
            out.writeByte(1);
            out.writeLong(timestampEpochMillis);
            for (IndexedJar jar : jars) {
                writeRecord(out, jar);
            }
        }
        return gz.toByteArray();
    }

    private static void writeRecord(DataOutputStream out, IndexedJar jar) throws IOException {
        long lastModified = System.currentTimeMillis();
        long size = jar.jarBytes().length;
        String uinfo = jar.groupId() + "|" + jar.artifactId() + "|" + jar.version() + "|NA|jar";
        String info = "jar|" + lastModified + "|" + size + "|NA|NA|NA|jar";
        out.writeInt(2);
        writeField(out, "u", uinfo);
        writeField(out, "i", info);
    }

    private static void writeField(DataOutputStream out, String name, String value) throws IOException {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        out.writeByte(0);
        out.writeUTF(name);
        out.writeInt(bytes.length);
        out.write(bytes);
    }

    @Override
    public void close() {
        server.stop(0);
    }
}
