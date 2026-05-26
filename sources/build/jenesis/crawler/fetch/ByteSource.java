package build.jenesis.crawler.fetch;

import module java.base;

public interface ByteSource {

    long size() throws IOException;

    byte[] read(long offset, int length) throws IOException;

    /**
     * Epoch milliseconds parsed from the underlying transport's {@code Last-Modified} header
     * (or equivalent), or {@code 0L} when no such value is available. Used by the crawler as
     * the authoritative publication timestamp because the Nexus index can re-stamp records
     * during republishing events; the artifact storage layer preserves the original mtime
     * across those events.
     */
    default long lastModifiedMillis() {
        return 0L;
    }

    /**
     * {@code true} when {@link #lastModifiedMillis()} is known to preserve the original
     * publication time (e.g. came from GCS's {@code x-goog-meta-last-modified}, or from a
     * non-mirrored Maven Central response). {@code false} when the value is a plain HTTP
     * {@code Last-Modified} from a mirror that may rewrite mtimes (e.g. GCS's bucket-landing
     * time for pre-2019 bulk-imported artifacts). The crawler uses this flag to decide
     * whether a Maven Central HEAD fallback is worth issuing.
     */
    default boolean lastModifiedCanonical() {
        return false;
    }

    static ByteSource ofBytes(byte[] bytes) {
        return ofBytes(bytes, 0L, false);
    }

    static ByteSource ofBytes(byte[] bytes, long lastModifiedMillis, boolean lastModifiedCanonical) {
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

            @Override
            public long lastModifiedMillis() {
                return lastModifiedMillis;
            }

            @Override
            public boolean lastModifiedCanonical() {
                return lastModifiedCanonical;
            }
        };
    }

}
