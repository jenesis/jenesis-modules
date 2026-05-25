package build.jenesis.crawler.fetch;

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

}
