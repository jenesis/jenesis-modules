package build.jenesis.modules;

import module java.base;

public final class FileBatchSource implements BatchSource {

    private final Worklist.Reader reader;
    private final int batchSize;
    private boolean exhausted;
    private long lastPosition;

    public FileBatchSource(Worklist worklist, long startPosition, int batchSize) throws IOException {
        if (batchSize < 1) {
            throw new IllegalArgumentException("batchSize must be >= 1, got " + batchSize);
        }
        this.reader = worklist.open(startPosition);
        this.batchSize = batchSize;
        this.lastPosition = startPosition;
    }

    @Override
    public Batch next() throws IOException {
        if (exhausted) {
            return new Batch(List.of(), lastPosition, true);
        }
        List<Coordinate> coordinates = new ArrayList<>(batchSize);
        for (int i = 0; i < batchSize; i++) {
            String line = reader.nextLine();
            lastPosition = reader.position();
            if (line == null) {
                exhausted = true;
                break;
            }
            if (line.isEmpty()) {
                continue;
            }
            coordinates.add(Worklist.parse(line));
        }
        return new Batch(coordinates, lastPosition, exhausted);
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}
