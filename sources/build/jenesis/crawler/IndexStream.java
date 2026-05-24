package build.jenesis.crawler;

import module java.base;

public final class IndexStream implements AutoCloseable {

    public static final int DEFAULT_QUEUE_CAPACITY = 4096;
    public static final int DEFAULT_STREAM_ATTEMPTS = 4;
    public static final long DEFAULT_TICK_EVERY = 10_000L;
    public static final long DEFAULT_LOG_EVERY = 50_000L;
    public static final Duration DEFAULT_INITIAL_BACKOFF = Duration.ofSeconds(1L);
    public static final Duration JOIN_TIMEOUT = Duration.ofSeconds(5L);

    public record QueueItem(Coordinate coordinate, long sequence) {

        public static final QueueItem POISON = new QueueItem(null, -1L);

        public boolean isPoison() {
            return this == POISON;
        }
    }

    private final BlockingQueue<QueueItem> queue;
    private final Fetcher fetcher;
    private final Predicate<Coordinate> filter;
    private final LongConsumer onProgressTick;
    private final AtomicReference<IOException> producerError;
    private final AtomicLong recordsProduced;
    private final AtomicBoolean completed;
    private volatile Thread producer;

    public IndexStream(Fetcher fetcher, Predicate<Coordinate> filter) {
        this(DEFAULT_QUEUE_CAPACITY, fetcher, filter, _ -> {});
    }

    public IndexStream(Fetcher fetcher, Predicate<Coordinate> filter, LongConsumer onProgressTick) {
        this(DEFAULT_QUEUE_CAPACITY, fetcher, filter, onProgressTick);
    }

    public IndexStream(int queueCapacity, Fetcher fetcher, Predicate<Coordinate> filter) {
        this(queueCapacity, fetcher, filter, _ -> {});
    }

    public IndexStream(int queueCapacity, Fetcher fetcher, Predicate<Coordinate> filter, LongConsumer onProgressTick) {
        this.queue = new ArrayBlockingQueue<>(queueCapacity);
        this.fetcher = Objects.requireNonNull(fetcher, "fetcher");
        this.filter = Objects.requireNonNull(filter, "filter");
        this.onProgressTick = Objects.requireNonNull(onProgressTick, "onProgressTick");
        this.producerError = new AtomicReference<>();
        this.recordsProduced = new AtomicLong(0L);
        this.completed = new AtomicBoolean(false);
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

    public void start(List<URI> indexUris) {
        producer = Thread.ofVirtual()
                .name("index-stream-producer")
                .start(() -> producerLoop(List.copyOf(indexUris)));
    }

    private void producerLoop(List<URI> indexUris) {
        try {
            for (URI uri : indexUris) {
                streamIndex(uri);
            }
            completed.set(true);
        } catch (IOException e) {
            producerError.set(e);
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

    private void streamIndex(URI uri) throws IOException, InterruptedException {
        boolean rangeSupported = fetcher.probeRangeSupport(uri);
        System.out.println("[discovery] Index source " + uri + " HTTP Range support: "
                + (rangeSupported ? "yes (using resumable stream)" : "no (will redownload on failure)"));
        if (rangeSupported) {
            try (InputStream raw = fetcher.resumableGet(uri);
                 GZIPInputStream gzipped = new GZIPInputStream(raw);
                 IndexReader reader = new IndexReader(gzipped)) {
                streamRecords(reader, 0L);
            }
            return;
        }
        IOException lastError = null;
        long backoffMillis = DEFAULT_INITIAL_BACKOFF.toMillis();
        int consecutiveFailures = 0;
        while (consecutiveFailures < DEFAULT_STREAM_ATTEMPTS) {
            long skipTarget = recordsProduced.get();
            long beforeAttempt = recordsProduced.get();
            try {
                streamIndexOnce(uri, skipTarget);
                return;
            } catch (IOException e) {
                lastError = e;
                long progressed = recordsProduced.get() - beforeAttempt;
                if (progressed > 0L) {
                    System.err.println("[discovery] stream failed after " + progressed + " records emitted: "
                            + e.getClass().getSimpleName() + ": " + e.getMessage()
                            + ". Resetting backoff and retrying in " + DEFAULT_INITIAL_BACKOFF.toMillis() + " ms.");
                    backoffMillis = DEFAULT_INITIAL_BACKOFF.toMillis();
                    consecutiveFailures = 0;
                } else {
                    consecutiveFailures++;
                    if (consecutiveFailures >= DEFAULT_STREAM_ATTEMPTS) {
                        break;
                    }
                    System.err.println("[discovery] stream failed without progress (attempt " + consecutiveFailures
                            + "/" + DEFAULT_STREAM_ATTEMPTS + ") for " + uri + " ("
                            + e.getClass().getSimpleName() + ": " + e.getMessage()
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

    private void streamIndexOnce(URI uri, long skipTarget) throws IOException, InterruptedException {
        try (InputStream raw = fetcher.get(uri);
             GZIPInputStream gzipped = new GZIPInputStream(raw);
             IndexReader reader = new IndexReader(gzipped)) {
            streamRecords(reader, skipTarget);
        }
    }

    private void streamRecords(IndexReader reader, long skipTarget) throws IOException, InterruptedException {
        long passed = 0L;
        long recordsSeen = 0L;
        long unparseable = 0L;
        long filtered = 0L;
        long behind = 0L;
        long startNanos = System.nanoTime();
        Map<String, String> record;
        while ((record = reader.nextRecord()) != null) {
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException("Producer interrupted");
            }
            if (++recordsSeen % DEFAULT_TICK_EVERY == 0L) {
                onProgressTick.accept(recordsSeen);
            }
            if (recordsSeen % DEFAULT_LOG_EVERY == 0L) {
                long elapsedSeconds = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - startNanos);
                long produced = recordsProduced.get();
                long rate = elapsedSeconds > 0L ? recordsSeen / elapsedSeconds : recordsSeen;
                System.out.println("[discovery] seen=" + recordsSeen + " queued=" + produced
                        + " inQueue=" + queue.size()
                        + " unparseable=" + unparseable + " filtered=" + filtered + " behind=" + behind
                        + " rate=" + rate + "/s elapsed=" + elapsedSeconds + "s");
            }
            Optional<Coordinate> coordinate = Coordinate.from(record);
            if (coordinate.isEmpty()) {
                unparseable++;
                continue;
            }
            Coordinate candidate = coordinate.get();
            if (!filter.test(candidate)) {
                filtered++;
                continue;
            }
            if (passed < skipTarget) {
                behind++;
                passed++;
                continue;
            }
            long sequence = recordsProduced.incrementAndGet();
            queue.put(new QueueItem(candidate, sequence));
            passed++;
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
