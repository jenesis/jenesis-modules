package build.jenesis.crawler;

import module java.base;

public final class Worklist {

    private static final char FIELD_SEPARATOR = '\t';
    private static final String MANIFEST_FILE_NAME = "manifest.tsv";
    private static final String SHARD_NAME_FORMAT = "%06d.tsv";

    public static final long DEFAULT_LINES_PER_SHARD = 100_000L;

    private final Path dir;
    private final long linesPerShard;

    public Worklist(Path dir) {
        this(dir, DEFAULT_LINES_PER_SHARD);
    }

    public Worklist(Path dir, long linesPerShard) {
        this.dir = Objects.requireNonNull(dir, "dir");
        if (linesPerShard < 1L) {
            throw new IllegalArgumentException("linesPerShard must be >= 1, got " + linesPerShard);
        }
        this.linesPerShard = linesPerShard;
    }

    public Path dir() {
        return dir;
    }

    public Path manifestPath() {
        return dir.resolve(MANIFEST_FILE_NAME);
    }

    public boolean exists() {
        return Files.exists(manifestPath());
    }

    public void clear() throws IOException {
        if (!Files.exists(dir)) {
            return;
        }
        try (Stream<Path> entries = Files.list(dir)) {
            for (Path entry : (Iterable<Path>) entries::iterator) {
                String name = entry.getFileName().toString();
                if (name.endsWith(".tsv") || name.endsWith(".tmp")) {
                    Files.deleteIfExists(entry);
                }
            }
        }
    }

    public long write(Stream<Coordinate> coordinates) throws IOException {
        Files.createDirectories(dir);
        clear();
        long count;
        List<Shard> shards;
        try (ShardedWriter writer = openWriter();
             Stream<Coordinate> stream = coordinates) {
            Iterator<Coordinate> iterator = stream.iterator();
            while (iterator.hasNext()) {
                writer.writeLine(format(iterator.next()));
            }
            writer.flush();
            count = writer.recordsProduced();
            shards = writer.shards();
        }
        writeManifest(shards);
        return count;
    }

    public ShardedWriter openWriter() throws IOException {
        Files.createDirectories(dir);
        return new ShardedWriter(dir, linesPerShard);
    }

    public List<Shard> readManifest() throws IOException {
        Path manifest = manifestPath();
        List<Shard> shards = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(manifest, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }
                int tab = line.indexOf('\t');
                if (tab < 0) {
                    throw new IOException("Invalid manifest line in " + manifest + ": " + line);
                }
                String name = line.substring(0, tab);
                long records;
                try {
                    records = Long.parseLong(line.substring(tab + 1));
                } catch (NumberFormatException invalid) {
                    throw new IOException("Invalid record count in " + manifest + ": " + line, invalid);
                }
                shards.add(new Shard(name, records));
            }
        }
        return List.copyOf(shards);
    }

    public void writeManifest(List<Shard> shards) throws IOException {
        Files.createDirectories(dir);
        Path manifest = manifestPath();
        Path temp = manifest.resolveSibling(manifest.getFileName() + ".tmp");
        try (BufferedWriter writer = Files.newBufferedWriter(temp, StandardCharsets.UTF_8)) {
            for (Shard shard : shards) {
                writer.write(shard.name());
                writer.write(FIELD_SEPARATOR);
                writer.write(Long.toString(shard.records()));
                writer.write('\n');
            }
        }
        try {
            Files.move(temp, manifest, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (AtomicMoveNotSupportedException atomicNotSupported) {
            Files.move(temp, manifest, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    public Reader open(long startRecord) throws IOException {
        List<Shard> shards = readManifest();
        return new Reader(dir, shards, startRecord);
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

    public record Shard(String name, long records) {}

    public static final class ShardedWriter implements Closeable {

        private final Path dir;
        private final long linesPerShard;
        private final List<Shard> completedShards = new ArrayList<>();
        private BufferedWriter current;
        private String currentName;
        private long currentLines;
        private int nextShardIndex;
        private long totalRecords;
        private boolean closed;

        ShardedWriter(Path dir, long linesPerShard) {
            this.dir = dir;
            this.linesPerShard = linesPerShard;
        }

        public void writeLine(String line) throws IOException {
            if (closed) {
                throw new IOException("Writer closed");
            }
            if (current == null || currentLines >= linesPerShard) {
                rotate();
            }
            current.write(line);
            current.write('\n');
            currentLines++;
            totalRecords++;
        }

        public void flush() throws IOException {
            if (current != null) {
                current.flush();
            }
        }

        public long recordsProduced() {
            return totalRecords;
        }

        public List<Shard> shards() {
            List<Shard> all = new ArrayList<>(completedShards);
            if (current != null && currentLines > 0L) {
                all.add(new Shard(currentName, currentLines));
            }
            return List.copyOf(all);
        }

        private void rotate() throws IOException {
            if (current != null) {
                current.close();
                completedShards.add(new Shard(currentName, currentLines));
            }
            currentName = String.format(SHARD_NAME_FORMAT, nextShardIndex++);
            current = Files.newBufferedWriter(dir.resolve(currentName), StandardCharsets.UTF_8);
            currentLines = 0L;
        }

        @Override
        public void close() throws IOException {
            if (closed) {
                return;
            }
            closed = true;
            if (current != null) {
                current.close();
                if (currentLines > 0L) {
                    completedShards.add(new Shard(currentName, currentLines));
                }
                current = null;
            }
        }
    }

    public static final class Reader implements Closeable {

        private final Path dir;
        private final List<Shard> shards;
        private int shardIndex;
        private BufferedReader current;
        private long position;

        Reader(Path dir, List<Shard> shards, long startRecord) throws IOException {
            this.dir = dir;
            this.shards = shards;
            long cumulative = 0L;
            int idx = 0;
            while (idx < shards.size() && cumulative + shards.get(idx).records() <= startRecord) {
                cumulative += shards.get(idx).records();
                idx++;
            }
            this.shardIndex = idx;
            this.position = cumulative;
            openCurrent();
            long skipWithinShard = startRecord - cumulative;
            for (long i = 0L; i < skipWithinShard; i++) {
                if (current == null) {
                    break;
                }
                String line = current.readLine();
                if (line == null) {
                    break;
                }
                position++;
            }
        }

        public String nextLine() throws IOException {
            while (current != null) {
                String line = current.readLine();
                if (line != null) {
                    position++;
                    return line;
                }
                current.close();
                current = null;
                shardIndex++;
                openCurrent();
            }
            return null;
        }

        public long position() {
            return position;
        }

        private void openCurrent() throws IOException {
            while (shardIndex < shards.size()) {
                Shard shard = shards.get(shardIndex);
                if (shard.records() == 0L) {
                    shardIndex++;
                    continue;
                }
                current = Files.newBufferedReader(dir.resolve(shard.name()), StandardCharsets.UTF_8);
                return;
            }
            current = null;
        }

        @Override
        public void close() throws IOException {
            if (current != null) {
                current.close();
            }
        }
    }
}
