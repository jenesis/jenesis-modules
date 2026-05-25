package build.jenesis.crawler.index;

import module java.base;
import build.jenesis.crawler.model.Coordinate;
import build.jenesis.crawler.model.ScannedEntry;
import build.jenesis.crawler.store.ScannedStore;

/**
 * A {@link BatchSource} that emits coordinates with a recorded permanent failure in
 * {@code data/scanned/}, optionally filtered by regex match against the failure message.
 *
 * Coordinates are pre-collected at construction time so the caller can report how many
 * candidates were found before processing starts. The on-disk file format is the same one
 * {@link ScannedStore} writes: one {@link ScannedEntry} per tab-separated line, with the
 * groupId encoded in the directory path and the artifactId in the filename stem.
 *
 * Since {@code scanned/} doesn't store {@code size} or {@code lastModified}, both default
 * to 0 on the re-emitted {@link Coordinate} (which makes {@code scanOne} skip the small-jar
 * fast path and go straight to the cached-tail strategy - the right behaviour when we
 * don't actually know the JAR size).
 */
public final class FailedScannedBatchSource implements BatchSource {

    private static final String LEAF_FILE_EXTENSION = ".tsv";
    private static final String JAR_EXTENSION = "jar";

    private final List<Coordinate> coordinates;
    private final int batchSize;
    private int index;

    public FailedScannedBatchSource(List<Coordinate> coordinates, int batchSize) {
        if (batchSize < 1) {
            throw new IllegalArgumentException("batchSize must be >= 1, got " + batchSize);
        }
        this.coordinates = List.copyOf(coordinates);
        this.batchSize = batchSize;
    }

    /**
     * Walks {@code scannedRoot}, parses every per-artifact TSV, keeps every failed entry
     * whose error message matches at least one of {@code patterns} (or all failed entries
     * when {@code patterns} is empty). The returned source is independent of the on-disk
     * tree: subsequent writes to {@code scanned/} don't affect what it emits.
     */
    public static FailedScannedBatchSource from(Path scannedRoot, List<Pattern> patterns, int batchSize) throws IOException {
        return new FailedScannedBatchSource(collect(scannedRoot, patterns), batchSize);
    }

    public int total() {
        return coordinates.size();
    }

    @Override
    public Batch next() {
        if (index >= coordinates.size()) {
            return new Batch(List.of(), index, true);
        }
        int end = Math.min(index + batchSize, coordinates.size());
        List<Coordinate> batch = new ArrayList<>(coordinates.subList(index, end));
        index = end;
        boolean exhausted = index >= coordinates.size();
        return new Batch(batch, index, exhausted);
    }

    @Override
    public void close() {
    }

    private static List<Coordinate> collect(Path scannedRoot, List<Pattern> patterns) throws IOException {
        List<Coordinate> result = new ArrayList<>();
        if (!Files.isDirectory(scannedRoot)) {
            return result;
        }
        try (Stream<Path> stream = Files.walk(scannedRoot)) {
            Iterator<Path> iterator = stream.iterator();
            while (iterator.hasNext()) {
                Path file = iterator.next();
                if (!Files.isRegularFile(file)) {
                    continue;
                }
                String fileName = file.getFileName().toString();
                if (!fileName.endsWith(LEAF_FILE_EXTENSION)) {
                    continue;
                }
                String artifactId = fileName.substring(0, fileName.length() - LEAF_FILE_EXTENSION.length());
                if (artifactId.isEmpty()) {
                    continue;
                }
                String groupId = pathToGroupId(scannedRoot, file.getParent());
                if (groupId.isEmpty()) {
                    continue;
                }
                appendMatchingFailures(file, groupId, artifactId, patterns, result);
            }
        }
        return result;
    }

    private static void appendMatchingFailures(Path file,
                                               String groupId,
                                               String artifactId,
                                               List<Pattern> patterns,
                                               List<Coordinate> sink) throws IOException {
        try (Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8)) {
            Iterator<String> iterator = lines.iterator();
            while (iterator.hasNext()) {
                String line = iterator.next();
                if (line.isEmpty()) {
                    continue;
                }
                ScannedEntry entry;
                try {
                    entry = ScannedEntry.parse(line);
                } catch (IllegalArgumentException malformed) {
                    continue;
                }
                if (!entry.isFailed()) {
                    continue;
                }
                if (!matchesAny(entry.errorMessage(), patterns)) {
                    continue;
                }
                sink.add(new Coordinate(groupId, artifactId, entry.version(), entry.classifier(), JAR_EXTENSION, 0L, 0L));
            }
        }
    }

    private static boolean matchesAny(String message, List<Pattern> patterns) {
        if (patterns.isEmpty()) {
            return true;
        }
        for (Pattern pattern : patterns) {
            if (pattern.matcher(message).find()) {
                return true;
            }
        }
        return false;
    }

    private static String pathToGroupId(Path root, Path dir) {
        if (dir == null) {
            return "";
        }
        Path relative = root.relativize(dir);
        StringBuilder builder = new StringBuilder();
        for (Path segment : relative) {
            if (!builder.isEmpty()) {
                builder.append('.');
            }
            builder.append(segment.toString());
        }
        return builder.toString();
    }
}
