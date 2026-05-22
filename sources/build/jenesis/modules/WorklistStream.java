package build.jenesis.modules;

import module java.base;

public final class WorklistStream implements AutoCloseable {

    public static final int DEFAULT_QUEUE_CAPACITY = 4096;
    public static final int DEFAULT_STREAM_ATTEMPTS = 4;
    public static final Duration DEFAULT_INITIAL_BACKOFF = Duration.ofSeconds(1L);
    public static final Duration JOIN_TIMEOUT = Duration.ofSeconds(5L);

    public record QueueItem(Coordinate coordinate, long sequence) {

        public static final QueueItem POISON = new QueueItem(null, -1L);

        public boolean isPoison() {
            return this == POISON;
        }
    }

    private final Path tempFile;
    private final BlockingQueue<QueueItem> queue;
    private final Fetcher fetcher;
    private final Predicate<Coordinate> filter;
    private final AtomicReference<IOException> producerError;
    private final AtomicLong recordsProduced;
    private final AtomicBoolean completed;
    private volatile Thread producer;

    public WorklistStream(Path tempFile, Fetcher fetcher, Predicate<Coordinate> filter) {
        this(tempFile, DEFAULT_QUEUE_CAPACITY, fetcher, filter);
    }

    public WorklistStream(Path tempFile, int queueCapacity, Fetcher fetcher, Predicate<Coordinate> filter) {
        this.tempFile = Objects.requireNonNull(tempFile, "tempFile");
        this.queue = new ArrayBlockingQueue<>(queueCapacity);
        this.fetcher = Objects.requireNonNull(fetcher, "fetcher");
        this.filter = Objects.requireNonNull(filter, "filter");
        this.producerError = new AtomicReference<>();
        this.recordsProduced = new AtomicLong(0L);
        this.completed = new AtomicBoolean(false);
    }

    public Path tempFile() {
        return tempFile;
    }

    public BlockingQueue<QueueItem> queue() {
        return queue;
    }

    public boolean completed() {
        return completed.get();
    }

    public IOException error() {
        return producerError.get();
    }

    public long recordsProduced() {
        return recordsProduced.get();
    }

    public void start(List<URI> indexUris) throws IOException {
        Path parent = tempFile.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        Files.deleteIfExists(tempFile);
        producer = Thread.ofVirtual()
                .name("worklist-stream-producer")
                .start(() -> producerLoop(List.copyOf(indexUris)));
    }

    private void producerLoop(List<URI> indexUris) {
        try (BufferedWriter writer = Files.newBufferedWriter(tempFile,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE)) {
            for (URI uri : indexUris) {
                streamIndex(uri, writer);
            }
            writer.flush();
            completed.set(true);
        } catch (IOException io) {
            producerError.set(io);
        } catch (InterruptedException interrupted) {
            Thread.currentThread().interrupt();
        } finally {
            for (;;) {
                try {
                    queue.put(QueueItem.POISON);
                    break;
                } catch (InterruptedException interrupted) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private void streamIndex(URI uri, BufferedWriter writer) throws IOException, InterruptedException {
        IOException lastError = null;
        long backoffMillis = DEFAULT_INITIAL_BACKOFF.toMillis();
        int consecutiveFailures = 0;
        while (consecutiveFailures < DEFAULT_STREAM_ATTEMPTS) {
            long skipTarget = recordsProduced.get();
            long beforeAttempt = recordsProduced.get();
            try {
                streamIndexOnce(uri, writer, skipTarget);
                return;
            } catch (IOException io) {
                lastError = io;
                long progressed = recordsProduced.get() - beforeAttempt;
                if (progressed > 0L) {
                    System.err.println("Producer stream failed after " + progressed + " records emitted: "
                            + io.getClass().getSimpleName() + ": " + io.getMessage()
                            + ". Resetting backoff and retrying in " + DEFAULT_INITIAL_BACKOFF.toMillis() + " ms.");
                    backoffMillis = DEFAULT_INITIAL_BACKOFF.toMillis();
                    consecutiveFailures = 0;
                } else {
                    consecutiveFailures++;
                    if (consecutiveFailures >= DEFAULT_STREAM_ATTEMPTS) {
                        break;
                    }
                    System.err.println("Producer stream failed without progress (attempt " + consecutiveFailures
                            + "/" + DEFAULT_STREAM_ATTEMPTS + ") for " + uri + " ("
                            + io.getClass().getSimpleName() + ": " + io.getMessage()
                            + "). Retrying in " + backoffMillis + " ms.");
                }
                Thread.sleep(backoffMillis);
                if (progressed == 0L) {
                    backoffMillis *= 2L;
                }
            }
        }
        throw lastError;
    }

    private void streamIndexOnce(URI uri, BufferedWriter writer, long skipTarget) throws IOException, InterruptedException {
        try (InputStream raw = fetcher.get(uri);
             GZIPInputStream gzipped = new GZIPInputStream(raw);
             IndexReader reader = new IndexReader(gzipped)) {
            long passed = 0L;
            Map<String, String> record;
            while ((record = reader.nextRecord()) != null) {
                if (Thread.currentThread().isInterrupted()) {
                    throw new InterruptedException("Producer interrupted");
                }
                Optional<Coordinate> coordinate = Coordinate.from(record);
                if (coordinate.isEmpty()) {
                    continue;
                }
                Coordinate candidate = coordinate.get();
                if (!filter.test(candidate)) {
                    continue;
                }
                if (passed < skipTarget) {
                    passed++;
                    continue;
                }
                String line = Worklist.format(candidate);
                writer.write(line);
                writer.write('\n');
                long sequence = recordsProduced.incrementAndGet();
                queue.put(new QueueItem(candidate, sequence));
                passed++;
            }
        }
    }

    @Override
    public void close() {
        Thread current = producer;
        if (current == null) {
            return;
        }
        current.interrupt();
        long deadline = System.nanoTime() + JOIN_TIMEOUT.toNanos();
        while (current.isAlive() && System.nanoTime() < deadline) {
            try {
                queue.poll(50L, TimeUnit.MILLISECONDS);
            } catch (InterruptedException interrupted) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        try {
            current.join(JOIN_TIMEOUT.toMillis());
        } catch (InterruptedException interrupted) {
            Thread.currentThread().interrupt();
        }
    }
}
