package build.jenesis.crawler;

import module java.base;

public final class CentralDirectory {

    public static final int EOCD_SIGNATURE = 0x06054b50;
    public static final int EOCD64_SIGNATURE = 0x06064b50;
    public static final int EOCD64_LOCATOR_SIGNATURE = 0x07064b50;
    public static final int CENTRAL_FILE_HEADER_SIGNATURE = 0x02014b50;
    public static final int LOCAL_FILE_HEADER_SIGNATURE = 0x04034b50;

    public static final int MIN_EOCD_SIZE = 22;
    public static final int LOCAL_HEADER_SIZE = 30;
    public static final int ZIP64_LOCATOR_SIZE = 20;

    public record Position(long centralDirectoryOffset, long centralDirectorySize, long entryCount, long shift) {
    }

    public record Entry(String name, int compressionMethod, long compressedSize, long uncompressedSize, long localHeaderOffset) {
    }

    private CentralDirectory() {
    }

    public static Position locate(byte[] tail, long fileSize) {
        long tailStart = fileSize - tail.length;
        int eocdOffset = findEndOfCentralDirectory(tail);
        if (eocdOffset < 0) {
            throw new IllegalArgumentException("End of central directory record not found in supplied tail buffer");
        }
        ByteBuffer buffer = ByteBuffer.wrap(tail).order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(eocdOffset + 4);
        buffer.getShort();
        buffer.getShort();
        buffer.getShort();
        int totalEntries = buffer.getShort() & 0xFFFF;
        long centralDirectorySize = buffer.getInt() & 0xFFFFFFFFL;
        long centralDirectoryOffset = buffer.getInt() & 0xFFFFFFFFL;

        boolean zip64 = totalEntries == 0xFFFF
                || centralDirectorySize == 0xFFFFFFFFL
                || centralDirectoryOffset == 0xFFFFFFFFL;
        if (!zip64) {
            long actualCdStart = (tailStart + eocdOffset) - centralDirectorySize;
            long shift = actualCdStart - centralDirectoryOffset;
            if (shift < 0L || actualCdStart < 0L) {
                shift = 0L;
            }
            return new Position(centralDirectoryOffset + shift, centralDirectorySize, totalEntries, shift);
        }
        int locatorOffset = eocdOffset - ZIP64_LOCATOR_SIZE;
        if (locatorOffset < 0) {
            throw new IllegalArgumentException("ZIP64 end of central directory locator not in supplied tail buffer");
        }
        buffer.position(locatorOffset);
        int locatorSignature = buffer.getInt();
        if (locatorSignature != EOCD64_LOCATOR_SIGNATURE) {
            throw new IllegalArgumentException("Expected ZIP64 locator signature at offset " + locatorOffset);
        }
        buffer.getInt();
        long eocd64Offset = buffer.getLong();
        long eocd64BufferOffset = eocd64Offset - tailStart;
        if (eocd64BufferOffset < 0 || eocd64BufferOffset > tail.length - 56) {
            throw new IllegalArgumentException("ZIP64 end of central directory record not in supplied tail buffer");
        }
        buffer.position((int) eocd64BufferOffset);
        int eocd64Signature = buffer.getInt();
        if (eocd64Signature != EOCD64_SIGNATURE) {
            throw new IllegalArgumentException("Expected ZIP64 end of central directory signature at offset " + eocd64BufferOffset);
        }
        buffer.getLong();
        buffer.getShort();
        buffer.getShort();
        buffer.getInt();
        buffer.getInt();
        buffer.getLong();
        long totalEntries64 = buffer.getLong();
        long centralDirectorySize64 = buffer.getLong();
        long centralDirectoryOffset64 = buffer.getLong();
        return new Position(centralDirectoryOffset64, centralDirectorySize64, totalEntries64, 0L);
    }

    public static Map<String, Entry> parse(byte[] bytes, long entryCount) {
        return parse(bytes, entryCount, 0L);
    }

    public static Map<String, Entry> parse(byte[] bytes, long entryCount, long localHeaderShift) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN);
        Map<String, Entry> entries = new LinkedHashMap<>(Math.max(16, (int) Math.min(entryCount * 2, Integer.MAX_VALUE)));
        for (long index = 0; index < entryCount; index++) {
            int signature = buffer.getInt();
            if (signature != CENTRAL_FILE_HEADER_SIGNATURE) {
                throw new IllegalArgumentException("Expected central file header signature at offset " + (buffer.position() - 4));
            }
            buffer.getShort();
            buffer.getShort();
            buffer.getShort();
            int compressionMethod = buffer.getShort() & 0xFFFF;
            buffer.getShort();
            buffer.getShort();
            buffer.getInt();
            long compressedSize = buffer.getInt() & 0xFFFFFFFFL;
            long uncompressedSize = buffer.getInt() & 0xFFFFFFFFL;
            int nameLength = buffer.getShort() & 0xFFFF;
            int extraLength = buffer.getShort() & 0xFFFF;
            int commentLength = buffer.getShort() & 0xFFFF;
            buffer.getShort();
            buffer.getShort();
            buffer.getInt();
            long localHeaderOffset = buffer.getInt() & 0xFFFFFFFFL;

            byte[] nameBytes = new byte[nameLength];
            buffer.get(nameBytes);
            String name = new String(nameBytes, StandardCharsets.UTF_8);

            int extraStart = buffer.position();
            boolean needsZip64 = compressedSize == 0xFFFFFFFFL
                    || uncompressedSize == 0xFFFFFFFFL
                    || localHeaderOffset == 0xFFFFFFFFL;
            if (needsZip64 && extraLength > 0) {
                int extraEnd = extraStart + extraLength;
                while (buffer.position() + 4 <= extraEnd) {
                    int tag = buffer.getShort() & 0xFFFF;
                    int size = buffer.getShort() & 0xFFFF;
                    int dataStart = buffer.position();
                    if (tag == 0x0001) {
                        if (uncompressedSize == 0xFFFFFFFFL) {
                            uncompressedSize = buffer.getLong();
                        }
                        if (compressedSize == 0xFFFFFFFFL) {
                            compressedSize = buffer.getLong();
                        }
                        if (localHeaderOffset == 0xFFFFFFFFL) {
                            localHeaderOffset = buffer.getLong();
                        }
                        break;
                    }
                    buffer.position(dataStart + size);
                }
            }
            buffer.position(extraStart + extraLength + commentLength);

            entries.put(name, new Entry(name, compressionMethod, compressedSize, uncompressedSize, localHeaderOffset + localHeaderShift));
        }
        return entries;
    }

    public static int localHeaderDataOffset(byte[] localHeaderBytes, int offset) {
        ByteBuffer buffer = ByteBuffer.wrap(localHeaderBytes, offset, localHeaderBytes.length - offset).order(ByteOrder.LITTLE_ENDIAN);
        int signature = buffer.getInt();
        if (signature != LOCAL_FILE_HEADER_SIGNATURE) {
            throw new IllegalArgumentException("Expected local file header signature at offset " + offset);
        }
        buffer.position(offset + 26);
        int nameLength = buffer.getShort() & 0xFFFF;
        int extraLength = buffer.getShort() & 0xFFFF;
        return LOCAL_HEADER_SIZE + nameLength + extraLength;
    }

    private static int findEndOfCentralDirectory(byte[] tail) {
        for (int candidate = tail.length - MIN_EOCD_SIZE; candidate >= 0; candidate--) {
            if (tail[candidate] != 0x50
                    || tail[candidate + 1] != 0x4b
                    || tail[candidate + 2] != 0x05
                    || tail[candidate + 3] != 0x06) {
                continue;
            }
            int commentLength = (tail[candidate + 20] & 0xFF) | ((tail[candidate + 21] & 0xFF) << 8);
            if (candidate + MIN_EOCD_SIZE + commentLength == tail.length) {
                return candidate;
            }
        }
        return -1;
    }
}
