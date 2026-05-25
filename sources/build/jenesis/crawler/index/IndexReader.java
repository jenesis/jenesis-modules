package build.jenesis.crawler.index;

import module java.base;

public final class IndexReader implements Closeable {

    public static final int FORMAT_VERSION = 1;

    // Sanity caps on values read from the index stream. Real Nexus records have a handful of
    // fields and field values of at most a few hundred KB (the compressed signature field is
    // the largest). Anything well beyond that is almost certainly garbage from a corrupted or
    // truncated stream; without these caps a single bad record could trigger a multi-GB
    // allocation and OOM the JVM before the parse error surfaces as an exception.
    public static final int MAX_FIELD_COUNT = 1024;
    public static final int MAX_FIELD_LENGTH = 16 * 1024 * 1024;

    private static final int FLAG_COMPRESSED = 0x08;

    private final DataInputStream input;
    private final int version;
    private final long timestamp;

    public IndexReader(InputStream input) throws IOException {
        this.input = new DataInputStream(input);
        this.version = this.input.readByte() & 0xFF;
        this.timestamp = this.input.readLong();
    }

    public int version() {
        return version;
    }

    public long timestamp() {
        return timestamp;
    }

    public Map<String, String> nextRecord() throws IOException {
        int fieldCount;
        try {
            fieldCount = input.readInt();
        } catch (EOFException _) {
            return null;
        }
        if (fieldCount < 0 || fieldCount > MAX_FIELD_COUNT) {
            throw new IOException("Implausible field count " + fieldCount
                    + " (cap is " + MAX_FIELD_COUNT + "); stream likely corrupted");
        }
        Map<String, String> record = new HashMap<>(Math.max(8, fieldCount * 2));
        for (int field = 0; field < fieldCount; field++) {
            byte flag = input.readByte();
            String name = input.readUTF();
            int length = input.readInt();
            if (length < 0 || length > MAX_FIELD_LENGTH) {
                throw new IOException("Implausible field length " + length
                        + " for field '" + name + "' (cap is " + MAX_FIELD_LENGTH + "); stream likely corrupted");
            }
            byte[] data = input.readNBytes(length);
            if (data.length < length) {
                throw new EOFException("Truncated field value: expected " + length + " bytes, got " + data.length);
            }
            String value = (flag & FLAG_COMPRESSED) != 0
                    ? decompress(data)
                    : new String(data, StandardCharsets.UTF_8);
            record.put(name, value);
        }
        return record;
    }

    @Override
    public void close() throws IOException {
        input.close();
    }

    private static String decompress(byte[] bytes) throws IOException {
        try (GZIPInputStream input = new GZIPInputStream(new ByteArrayInputStream(bytes))) {
            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
