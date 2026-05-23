package build.jenesis.crawler;

import module java.base;

public final class IndexReader implements Closeable {

    public static final int FORMAT_VERSION = 1;

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
        } catch (EOFException eof) {
            return null;
        }
        Map<String, String> record = new HashMap<>(Math.max(8, fieldCount * 2));
        for (int field = 0; field < fieldCount; field++) {
            byte flag = input.readByte();
            String name = input.readUTF();
            int length = input.readInt();
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
        try (GZIPInputStream gz = new GZIPInputStream(new ByteArrayInputStream(bytes))) {
            return new String(gz.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
