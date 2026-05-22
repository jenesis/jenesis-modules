package build.jenesis.modules;

import module java.base;

public final class Scanner {

    public static final String MODULE_INFO_NAME = "module-info.class";
    public static final String MANIFEST_NAME = "META-INF/MANIFEST.MF";
    public static final String AUTOMATIC_MODULE_ATTRIBUTE = "Automatic-Module-Name";
    public static final String VERSIONED_PREFIX = "META-INF/versions/";
    public static final String VERSIONED_SUFFIX = "/module-info.class";

    public static final int DEFAULT_TAIL_SIZE = 65536;
    public static final int LOCAL_HEADER_SLACK = 4096;

    private final int tailSize;

    public Scanner() {
        this(DEFAULT_TAIL_SIZE);
    }

    public Scanner(int tailSize) {
        if (tailSize < CentralDirectory.MIN_EOCD_SIZE) {
            throw new IllegalArgumentException("Tail size must accommodate the end-of-central-directory record: " + tailSize);
        }
        this.tailSize = tailSize;
    }

    public Optional<ScannedModule> scan(ByteSource source) throws IOException {
        long size = source.size();
        int actualTailSize = (int) Math.min(size, tailSize);
        byte[] tail = source.read(size - actualTailSize, actualTailSize);

        CentralDirectory.Position position = CentralDirectory.locate(tail, size);
        byte[] centralDirectoryBytes = fetchCentralDirectory(source, tail, position, size, actualTailSize);
        Map<String, CentralDirectory.Entry> entries = CentralDirectory.parse(centralDirectoryBytes, position.entryCount());

        CentralDirectory.Entry moduleInfoEntry = entries.get(MODULE_INFO_NAME);
        if (moduleInfoEntry == null) {
            moduleInfoEntry = highestVersionedModuleInfo(entries);
        }
        if (moduleInfoEntry != null) {
            byte[] bytes = readEntry(source, moduleInfoEntry);
            String name = ModuleDescriptor.read(new ByteArrayInputStream(bytes)).name();
            return Optional.of(new ScannedModule(name, ModuleType.NAMED));
        }
        CentralDirectory.Entry manifestEntry = entries.get(MANIFEST_NAME);
        if (manifestEntry != null) {
            byte[] bytes = readEntry(source, manifestEntry);
            Manifest manifest = new Manifest(new ByteArrayInputStream(bytes));
            String automatic = manifest.getMainAttributes().getValue(AUTOMATIC_MODULE_ATTRIBUTE);
            if (automatic != null && !automatic.isBlank()) {
                return Optional.of(new ScannedModule(automatic.trim(), ModuleType.AUTOMATIC));
            }
        }
        return Optional.empty();
    }

    private static CentralDirectory.Entry highestVersionedModuleInfo(Map<String, CentralDirectory.Entry> entries) {
        CentralDirectory.Entry best = null;
        int bestVersion = -1;
        for (Map.Entry<String, CentralDirectory.Entry> entry : entries.entrySet()) {
            String name = entry.getKey();
            if (!name.startsWith(VERSIONED_PREFIX) || !name.endsWith(VERSIONED_SUFFIX)) {
                continue;
            }
            int versionStart = VERSIONED_PREFIX.length();
            int versionEnd = name.length() - VERSIONED_SUFFIX.length();
            if (versionEnd <= versionStart) {
                continue;
            }
            String versionText = name.substring(versionStart, versionEnd);
            if (versionText.indexOf('/') >= 0) {
                continue;
            }
            int version;
            try {
                version = Integer.parseInt(versionText);
            } catch (NumberFormatException invalid) {
                continue;
            }
            if (version > bestVersion) {
                best = entry.getValue();
                bestVersion = version;
            }
        }
        return best;
    }

    private static byte[] fetchCentralDirectory(ByteSource source,
                                                byte[] tail,
                                                CentralDirectory.Position position,
                                                long size,
                                                int actualTailSize) throws IOException {
        long tailStart = size - actualTailSize;
        long centralDirectoryEnd = position.centralDirectoryOffset() + position.centralDirectorySize();
        if (position.centralDirectoryOffset() >= tailStart && centralDirectoryEnd <= size) {
            int offsetInTail = (int) (position.centralDirectoryOffset() - tailStart);
            return Arrays.copyOfRange(tail, offsetInTail, offsetInTail + (int) position.centralDirectorySize());
        }
        return source.read(position.centralDirectoryOffset(), (int) position.centralDirectorySize());
    }

    private static byte[] readEntry(ByteSource source, CentralDirectory.Entry entry) throws IOException {
        long sourceSize = source.size();
        long fetchEnd = Math.min(entry.localHeaderOffset() + CentralDirectory.LOCAL_HEADER_SIZE + LOCAL_HEADER_SLACK + entry.compressedSize(), sourceSize);
        int fetchLength = (int) (fetchEnd - entry.localHeaderOffset());
        byte[] block = source.read(entry.localHeaderOffset(), fetchLength);

        int dataOffset = CentralDirectory.localHeaderDataOffset(block, 0);
        long compressedSize = entry.compressedSize();
        if (dataOffset + compressedSize > block.length) {
            int neededLength = dataOffset + (int) compressedSize;
            block = source.read(entry.localHeaderOffset(), neededLength);
        }
        byte[] data = new byte[(int) compressedSize];
        System.arraycopy(block, dataOffset, data, 0, (int) compressedSize);

        return decompress(entry, data);
    }

    private static byte[] decompress(CentralDirectory.Entry entry, byte[] data) throws IOException {
        return switch (entry.compressionMethod()) {
            case 0 -> data;
            case 8 -> inflate(data, (int) entry.uncompressedSize());
            default -> throw new IOException("Unsupported ZIP compression method " + entry.compressionMethod() + " for entry " + entry.name());
        };
    }

    private static byte[] inflate(byte[] data, int uncompressedSize) throws IOException {
        Inflater inflater = new Inflater(true);
        try {
            inflater.setInput(data);
            byte[] output = new byte[uncompressedSize];
            int filled = 0;
            while (filled < uncompressedSize) {
                int read = inflater.inflate(output, filled, uncompressedSize - filled);
                if (read == 0) {
                    if (inflater.finished() || inflater.needsDictionary()) {
                        break;
                    }
                    if (inflater.needsInput()) {
                        throw new EOFException("Truncated deflate stream");
                    }
                }
                filled += read;
            }
            if (filled != uncompressedSize) {
                throw new IOException("Deflate stream produced " + filled + " bytes; expected " + uncompressedSize);
            }
            return output;
        } catch (DataFormatException e) {
            throw new IOException("Malformed deflate stream", e);
        } finally {
            inflater.end();
        }
    }
}
