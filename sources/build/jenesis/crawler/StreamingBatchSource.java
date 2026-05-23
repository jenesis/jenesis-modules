package build.jenesis.crawler;

import module java.base;

public final class StreamingBatchSource implements BatchSource {

    public static final Duration DEFAULT_POLL_TIMEOUT = Duration.ofSeconds(1L);

    private final BlockingQueue<WorklistStream.QueueItem> queue;
    private final int batchSize;
    private final Duration pollTimeout;
    private boolean exhausted;
    private long lastSequence;

    public StreamingBatchSource(WorklistStream stream, int batchSize) {
        this(stream.queue(), batchSize, DEFAULT_POLL_TIMEOUT);
    }

    public StreamingBatchSource(BlockingQueue<WorklistStream.QueueItem> queue, int batchSize, Duration pollTimeout) {
        if (batchSize < 1) {
            throw new IllegalArgumentException("batchSize must be >= 1, got " + batchSize);
        }
        this.queue = Objects.requireNonNull(queue, "queue");
        this.batchSize = batchSize;
        this.pollTimeout = Objects.requireNonNull(pollTimeout, "pollTimeout");
    }

    @Override
    public Batch next() throws InterruptedException {
        if (exhausted) {
            return new Batch(List.of(), lastSequence, true);
        }
        WorklistStream.QueueItem first = queue.poll(pollTimeout.toMillis(), TimeUnit.MILLISECONDS);
        if (first == null) {
            return new Batch(List.of(), lastSequence, false);
        }
        if (first.isPoison()) {
            exhausted = true;
            return new Batch(List.of(), lastSequence, true);
        }

        List<Coordinate> coordinates = new ArrayList<>(batchSize);
        coordinates.add(first.coordinate());
        lastSequence = first.sequence();

        List<WorklistStream.QueueItem> drained = new ArrayList<>(batchSize - 1);
        queue.drainTo(drained, batchSize - 1);
        for (WorklistStream.QueueItem item : drained) {
            if (item.isPoison()) {
                exhausted = true;
                break;
            }
            coordinates.add(item.coordinate());
            lastSequence = item.sequence();
        }
        return new Batch(coordinates, lastSequence, exhausted);
    }

    @Override
    public void close() {
    }
}
