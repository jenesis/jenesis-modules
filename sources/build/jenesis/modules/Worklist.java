package build.jenesis.modules;

import module java.base;

public final class Worklist {

    private static final char FIELD_SEPARATOR = '\t';

    private final Path path;

    public Worklist(Path path) {
        this.path = Objects.requireNonNull(path, "path");
    }

    public Path path() {
        return path;
    }

    public boolean exists() {
        return Files.exists(path);
    }

    public long size() throws IOException {
        return Files.size(path);
    }

    public long write(Stream<Coordinate> coordinates) throws IOException {
        Path parent = path.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        Path temp = path.resolveSibling(path.getFileName() + ".tmp");
        long count = 0L;
        try (BufferedWriter writer = Files.newBufferedWriter(temp, StandardCharsets.UTF_8);
             Stream<Coordinate> stream = coordinates) {
            Iterator<Coordinate> iterator = stream.iterator();
            while (iterator.hasNext()) {
                writer.write(format(iterator.next()));
                writer.newLine();
                count++;
            }
        }
        Files.move(temp, path, StandardCopyOption.REPLACE_EXISTING);
        return count;
    }

    public Reader open(long startPosition) throws IOException {
        FileChannel channel = FileChannel.open(path, StandardOpenOption.READ);
        channel.position(startPosition);
        return new Reader(channel, startPosition);
    }

    public static String format(Coordinate coordinate) {
        StringBuilder builder = new StringBuilder();
        builder.append(coordinate.groupId()).append(FIELD_SEPARATOR);
        builder.append(coordinate.artifactId()).append(FIELD_SEPARATOR);
        builder.append(coordinate.version()).append(FIELD_SEPARATOR);
        builder.append(coordinate.classifier() == null ? "" : coordinate.classifier()).append(FIELD_SEPARATOR);
        builder.append(coordinate.extension()).append(FIELD_SEPARATOR);
        builder.append(coordinate.size()).append(FIELD_SEPARATOR);
        builder.append(coordinate.lastModified());
        return builder.toString();
    }

    public static Coordinate parse(String line) {
        String[] parts = line.split("\t", -1);
        if (parts.length != 7) {
            throw new IllegalArgumentException("Expected 7 tab-separated fields in worklist line: " + line);
        }
        String groupId = parts[0];
        String artifactId = parts[1];
        String version = parts[2];
        String classifier = parts[3].isEmpty() ? null : parts[3];
        String extension = parts[4];
        long size = Long.parseLong(parts[5]);
        long lastModified = Long.parseLong(parts[6]);
        return new Coordinate(groupId, artifactId, version, classifier, extension, size, lastModified);
    }

    public static final class Reader implements Closeable {

        private final FileChannel channel;
        private final ByteBuffer buffer;
        private long position;

        private Reader(FileChannel channel, long position) {
            this.channel = channel;
            this.buffer = ByteBuffer.allocate(64 * 1024);
            this.buffer.limit(0);
            this.position = position;
        }

        public String nextLine() throws IOException {
            ByteArrayOutputStream line = new ByteArrayOutputStream();
            while (true) {
                if (!buffer.hasRemaining()) {
                    buffer.clear();
                    int read = channel.read(buffer);
                    if (read < 0) {
                        return line.size() == 0 ? null : line.toString(StandardCharsets.UTF_8);
                    }
                    buffer.flip();
                }
                byte octet = buffer.get();
                position++;
                if (octet == '\n') {
                    return line.toString(StandardCharsets.UTF_8);
                }
                if (octet != '\r') {
                    line.write(octet);
                }
            }
        }

        public long position() {
            return position;
        }

        @Override
        public void close() throws IOException {
            channel.close();
        }
    }
}
