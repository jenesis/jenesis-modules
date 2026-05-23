package build.jenesis.crawler;

import module java.base;

public interface ByteSource {

    long size() throws IOException;

    byte[] read(long offset, int length) throws IOException;

    static ByteSource ofBytes(byte[] bytes) {
        return new ByteSource() {

            @Override
            public long size() {
                return bytes.length;
            }

            @Override
            public byte[] read(long offset, int length) {
                if (offset < 0 || length < 0 || offset + length > bytes.length) {
                    throw new IllegalArgumentException("Range out of bounds: offset=" + offset + " length=" + length + " size=" + bytes.length);
                }
                byte[] result = new byte[length];
                System.arraycopy(bytes, (int) offset, result, 0, length);
                return result;
            }
        };
    }

    static ByteSource ofFile(Path path) {
        return new ByteSource() {

            @Override
            public long size() throws IOException {
                return Files.size(path);
            }

            @Override
            public byte[] read(long offset, int length) throws IOException {
                try (SeekableByteChannel channel = Files.newByteChannel(path, StandardOpenOption.READ)) {
                    channel.position(offset);
                    ByteBuffer buffer = ByteBuffer.allocate(length);
                    while (buffer.hasRemaining()) {
                        if (channel.read(buffer) < 0) {
                            throw new EOFException("Unexpected end of file at offset " + (offset + buffer.position()));
                        }
                    }
                    return buffer.array();
                }
            }
        };
    }
}
