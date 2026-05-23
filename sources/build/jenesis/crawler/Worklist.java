package build.jenesis.crawler;

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
                writer.write('\n');
                count++;
            }
        }
        try {
            Files.move(temp, path, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (AtomicMoveNotSupportedException atomicNotSupported) {
            Files.move(temp, path, StandardCopyOption.REPLACE_EXISTING);
        }
        return count;
    }

    public Reader open(long startRecord) throws IOException {
        BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
        long skipped = 0L;
        while (skipped < startRecord) {
            String line = reader.readLine();
            if (line == null) {
                break;
            }
            skipped++;
        }
        return new Reader(reader, skipped);
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

        private final BufferedReader delegate;
        private long position;

        private Reader(BufferedReader delegate, long initialPosition) {
            this.delegate = delegate;
            this.position = initialPosition;
        }

        public String nextLine() throws IOException {
            String line = delegate.readLine();
            if (line == null) {
                return null;
            }
            position++;
            return line;
        }

        public long position() {
            return position;
        }

        @Override
        public void close() throws IOException {
            delegate.close();
        }
    }
}
