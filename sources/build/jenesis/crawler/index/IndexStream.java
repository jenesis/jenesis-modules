package build.jenesis.crawler.index;

import module java.base;
import build.jenesis.crawler.fetch.Fetcher;
import build.jenesis.crawler.model.Coordinate;

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
    private volatile Thread producer;

    // Raw (pre-filter) record position of the most recent emission on the current index
    // stream. Producer-thread only; reset per index URI. Retries resume by this position -
    // see streamRecords for why a count of filter-passing records must not be used.
    private long lastEmittedRecordSeen;

    public IndexStream(Fetcher fetcher, Predicate<Coordinate> filter, LongConsumer onProgressTick) {
        this.queue = new ArrayBlockingQueue<>(DEFAULT_QUEUE_CAPACITY);
        this.fetcher = Objects.requireNonNull(fetcher, "fetcher");
        this.filter = Objects.requireNonNull(filter, "filter");
        this.onProgressTick = Objects.requireNonNull(onProgressTick, "onProgressTick");
        this.producerError = new AtomicReference<>();
        this.recordsProduced = new AtomicLong(0L);
    }

    public BlockingQueue<QueueItem> queue() {
        return queue;
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
        lastEmittedRecordSeen = 0L;
        boolean rangeSupported = fetcher.probeRangeSupport(uri);
        OptionalLong totalBytes = fetcher.probeContentLength(uri);
        System.out.println("[discovery] Index source " + uri + " HTTP Range support: "
                + (rangeSupported ? "yes (using resumable stream)" : "no (will redownload on failure)")
                + (totalBytes.isPresent() ? "; size " + totalBytes.getAsLong() + " bytes" : ""));
        if (rangeSupported) {
            try (InputStream raw = fetcher.resumableGet(uri);
                 CountingInputStream counted = new CountingInputStream(raw);
                 GZIPInputStream gzipped = new GZIPInputStream(counted);
                 IndexReader reader = new IndexReader(gzipped)) {
                streamRecords(reader, 0L, counted, totalBytes);
            }
            return;
        }
        IOException lastError = null;
        long backoffMillis = DEFAULT_INITIAL_BACKOFF.toMillis();
        int consecutiveFailures = 0;
        while (consecutiveFailures < DEFAULT_STREAM_ATTEMPTS) {
            long resumeAfter = lastEmittedRecordSeen;
            long beforeAttempt = recordsProduced.get();
            try {
                streamIndexOnce(uri, resumeAfter);
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

    private void streamIndexOnce(URI uri, long resumeAfter) throws IOException, InterruptedException {
        OptionalLong totalBytes = fetcher.probeContentLength(uri);
        try (InputStream raw = fetcher.get(uri);
             CountingInputStream counted = new CountingInputStream(raw);
             GZIPInputStream gzipped = new GZIPInputStream(counted);
             IndexReader reader = new IndexReader(gzipped)) {
            streamRecords(reader, resumeAfter, counted, totalBytes);
        }
    }

    private void streamRecords(IndexReader reader,
                               long resumeAfter,
                               CountingInputStream counted,
                               OptionalLong totalBytes) throws IOException, InterruptedException {
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
                System.out.println("[discovery] seen=" + recordsSeen + " emitted=" + produced
                        + " inQueue=" + queue.size()
                        + " unparseable=" + unparseable + " filtered=" + filtered + " behind=" + behind
                        + " rate=" + rate + "/s elapsed=" + elapsedSeconds + "s"
                        + byteProgress(counted.bytesRead(), totalBytes));
            }
            // Resume by raw stream position, never by a count of filter-passing records:
            // the filter includes the scanned store, which the consumer grows while this
            // producer streams, so replaying a passed-count against the mutated filter
            // slides the skip window past records that were never emitted and drops them.
            if (recordsSeen <= resumeAfter) {
                behind++;
                continue;
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
            long sequence = recordsProduced.incrementAndGet();
            queue.put(new QueueItem(candidate, sequence));
            lastEmittedRecordSeen = recordsSeen;
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

    /**
     * Bytes-of-the-compressed-index progress suffix for the discovery log line. The percentage is
     * computed against the gzip {@code Content-Length}, so it tracks I/O progress rather than
     * record-count progress. Incremental chunks come without a length or with a very small length,
     * so this is gated: only shown when the server declared a length and it's at least one MiB,
     * keeping the line clean when the index is a few kilobytes of incremental delta.
     */
    private static String byteProgress(long bytesRead, OptionalLong totalBytes) {
        if (totalBytes.isEmpty()) {
            return "";
        }
        long total = totalBytes.getAsLong();
        if (total < 1L << 20) {
            return "";
        }
        double percent = bytesRead * 100.0 / (double) total;
        return " bytesRead=" + bytesRead + " bytesTotal=" + total
                + " percentage=" + String.format(Locale.ROOT, "%.1f", percent) + "%";
    }

    /**
     * Counts bytes pulled from the wrapped stream. Used to derive a compressed-bytes percentage
     * for the discovery log; sits above the GZIP layer so the count matches {@code Content-Length}.
     * Reads are intentionally not synchronised: the producer thread is the only reader.
     */
    static final class CountingInputStream extends FilterInputStream {

        private long bytesRead;

        CountingInputStream(InputStream in) {
            super(in);
        }

        long bytesRead() {
            return bytesRead;
        }

        @Override
        public int read() throws IOException {
            int next = in.read();
            if (next >= 0) {
                bytesRead++;
            }
            return next;
        }

        @Override
        public int read(byte[] buffer, int offset, int length) throws IOException {
            int read = in.read(buffer, offset, length);
            if (read > 0) {
                bytesRead += read;
            }
            return read;
        }

        @Override
        public long skip(long n) throws IOException {
            long skipped = in.skip(n);
            if (skipped > 0L) {
                bytesRead += skipped;
            }
            return skipped;
        }
    }
}
