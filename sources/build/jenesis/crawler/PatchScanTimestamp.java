package build.jenesis.crawler;

import module java.base;
import build.jenesis.crawler.fetch.Fetcher;
import build.jenesis.crawler.model.ScannedEntry;
import build.jenesis.crawler.publish.GitPublisher;

/**
 * One-shot tool that walks every {@code data/scanned/.../*.tsv} file and back-fills the
 * {@code publishedAt} column on rows that predate the format extension (the legacy three-column
 * shape with no timestamp). For each legacy row, the tool issues the same HEAD-based timestamp
 * lookup the regular crawler runs on every successful scan: the mirror at
 * {@code <artifact-base-uri>} first (its {@code x-goog-meta-last-modified} header is canonical
 * for post-2019 uploads), with a fallback to the canonical timestamp source (typically
 * {@code https://repo.maven.apache.org/maven2/}) when the mirror's response is the bucket-import
 * mtime rather than the publish time.
 *
 * <p>Runs the per-row HEADs sequentially within a file but processes files in parallel across
 * {@link #PROP_CONCURRENCY} worker threads. A file is rewritten atomically (temp-file +
 * rename) only after every legacy row in it has been resolved (or the lookup gave up); a
 * crash mid-file leaves the original on disk unchanged. Git checkpoints fire once per
 * {@link #PROP_CHECKPOINT_EVERY} *patched files* (twice the crawler's per-coordinate
 * checkpoint default; one file usually carries dozens of rows).
 *
 * <p>Rows where the HEAD failed or returned no timestamp are left in the legacy three-column
 * shape, so a future run picks them up again.
 */
public final class PatchScanTimestamp {

    public static final String PROP_DATA = Crawl.PROP_DATA;
    public static final String PROP_BUDGET_MINUTES = Crawl.PROP_BUDGET_MINUTES;
    public static final String PROP_CONCURRENCY = Crawl.PROP_CONCURRENCY;
    public static final String PROP_CHECKPOINT_EVERY = Crawl.PROP_CHECKPOINT_EVERY;
    public static final String PROP_CANONICAL_TIMESTAMP_URI = Crawl.PROP_CANONICAL_TIMESTAMP_URI;
    public static final String PROP_GIT_PUBLISH = Crawl.PROP_GIT_PUBLISH;
    public static final String PROP_GIT_WORK_DIR = Crawl.PROP_GIT_WORK_DIR;
    public static final String PROP_GIT_PUSH_EVERY = Crawl.PROP_GIT_PUSH_EVERY;

    public static final int DEFAULT_CONCURRENCY = 64;
    public static final long DEFAULT_BUDGET_MINUTES = 180L;
    /** Patch tool checkpoints once per N patched files; default is double the crawler's
     *  per-coordinate checkpoint (4000 vs 2000) because each file typically carries dozens
     *  of rows. */
    public static final long DEFAULT_CHECKPOINT_EVERY_FILES = 4000L;

    private PatchScanTimestamp() {
    }

    public static void main(String[] arguments) throws Exception {
        if (arguments.length == 0 || arguments[0].equals("--help") || arguments[0].equals("-h")) {
            printUsage();
            if (arguments.length == 0) {
                throw new IllegalArgumentException("Missing required <artifact-base-uri> positional argument");
            }
            return;
        }

        URI artifactBase = URI.create(ensureTrailingSlash(arguments[0]));
        URI canonicalSource = property(PROP_CANONICAL_TIMESTAMP_URI)
                .map(s -> URI.create(ensureTrailingSlash(s)))
                .orElse(artifactBase);

        Path dataDir = property(PROP_DATA).map(Path::of).orElse(Path.of("data"));
        Path scannedRoot = dataDir.resolve("scanned");
        if (!Files.isDirectory(scannedRoot)) {
            System.err.println("[patch] no scanned/ directory at " + scannedRoot);
            return;
        }
        int concurrency = property(PROP_CONCURRENCY).map(Integer::parseInt).orElse(DEFAULT_CONCURRENCY);
        long checkpointEvery = property(PROP_CHECKPOINT_EVERY).map(Long::parseLong).orElse(DEFAULT_CHECKPOINT_EVERY_FILES);
        Duration budget = property(PROP_BUDGET_MINUTES)
                .map(Long::parseLong).map(Duration::ofMinutes)
                .orElse(Duration.ofMinutes(DEFAULT_BUDGET_MINUTES));

        Optional<GitPublisher> publisher = configureGitPublisher(dataDir);

        System.out.println("[patch] Configuration:");
        System.out.println("[patch]   dataDir=" + dataDir);
        System.out.println("[patch]   artifactBase=" + artifactBase);
        System.out.println("[patch]   canonicalSource=" + canonicalSource);
        System.out.println("[patch]   concurrency=" + concurrency);
        System.out.println("[patch]   checkpointEvery=" + checkpointEvery + " patched file(s)");
        System.out.println("[patch]   budget=" + budget);

        List<Path> candidates = enumerateScannedFiles(scannedRoot);
        System.out.println("[patch] enumerated " + candidates.size() + " scanned.tsv file(s); filtering legacy ones...");
        // Pre-filter to files that actually have legacy rows. Saves worker thread overhead
        // on files that have already been migrated by an earlier markOk in a recent crawl.
        BlockingQueue<Patch> work = new LinkedBlockingQueue<>();
        long legacyFiles = 0L;
        long legacyRows = 0L;
        for (Path file : candidates) {
            LegacyCount count = countLegacyRows(file);
            if (count.legacy > 0L) {
                String groupId = groupIdFor(scannedRoot, file);
                String artifactId = artifactIdFor(file);
                work.offer(new Patch(file, groupId, artifactId));
                legacyFiles++;
                legacyRows += count.legacy;
            }
        }
        System.out.println("[patch] " + legacyFiles + " file(s) carry "
                + legacyRows + " legacy row(s) (out of " + candidates.size() + " scanned.tsv file(s) total).");

        if (legacyFiles == 0L) {
            System.out.println("[patch] Nothing to do.");
            publisher.ifPresent(GitPublisher::flush);
            return;
        }

        Fetcher fetcher = new Fetcher();
        Instant deadline = Instant.now().plus(budget);
        AtomicLong filesProcessed = new AtomicLong();
        AtomicLong filesPatched = new AtomicLong();
        AtomicLong rowsPatched = new AtomicLong();
        AtomicLong headFailures = new AtomicLong();
        AtomicLong sinceLastCheckpoint = new AtomicLong();
        AtomicBoolean stop = new AtomicBoolean(false);

        ExecutorService executor = Executors.newFixedThreadPool(concurrency, runnable -> {
            Thread thread = new Thread(runnable, "patch-scan-ts");
            thread.setDaemon(false);
            return thread;
        });
        try {
            CountDownLatch done = new CountDownLatch(concurrency);
            for (int i = 0; i < concurrency; i++) {
                executor.submit(() -> {
                    try {
                        while (!stop.get()) {
                            if (Instant.now().isAfter(deadline)) {
                                stop.set(true);
                                System.err.println("[patch] Budget exhausted; signalling stop.");
                                return;
                            }
                            Patch patch = work.poll();
                            if (patch == null) {
                                return;
                            }
                            FileResult result = processFile(patch, fetcher, artifactBase, canonicalSource);
                            filesProcessed.incrementAndGet();
                            if (result.rowsPatched > 0L) {
                                filesPatched.incrementAndGet();
                                rowsPatched.addAndGet(result.rowsPatched);
                            }
                            headFailures.addAndGet(result.headFailures);
                            long since = sinceLastCheckpoint.incrementAndGet();
                            if (since >= checkpointEvery) {
                                maybeCheckpoint(publisher, sinceLastCheckpoint, filesPatched, rowsPatched, headFailures);
                            }
                        }
                    } finally {
                        done.countDown();
                    }
                });
            }
            // Status thread: log every 30 seconds while workers run.
            Thread status = new Thread(() -> {
                while (!stop.get() && done.getCount() > 0L) {
                    try {
                        Thread.sleep(30_000L);
                    } catch (InterruptedException interrupted) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                    System.out.println("[patch] progress filesProcessed=" + filesProcessed.get()
                            + " filesPatched=" + filesPatched.get()
                            + " rowsPatched=" + rowsPatched.get()
                            + " headFailures=" + headFailures.get()
                            + " inQueue=" + work.size());
                }
            }, "patch-scan-ts-status");
            status.setDaemon(true);
            status.start();
            done.await();
        } finally {
            executor.shutdown();
            executor.awaitTermination(60L, TimeUnit.SECONDS);
        }

        // Final checkpoint + push.
        if (sinceLastCheckpoint.get() > 0L) {
            maybeCheckpoint(publisher, sinceLastCheckpoint, filesPatched, rowsPatched, headFailures);
        }
        publisher.ifPresent(GitPublisher::flush);

        System.out.println("[patch] Done. filesProcessed=" + filesProcessed.get()
                + " filesPatched=" + filesPatched.get()
                + " rowsPatched=" + rowsPatched.get()
                + " headFailures=" + headFailures.get());
    }

    private record Patch(Path file, String groupId, String artifactId) {
    }

    private record FileResult(long rowsPatched, long headFailures) {
    }

    private record LegacyCount(long legacy, long total) {
    }

    private static List<Path> enumerateScannedFiles(Path scannedRoot) throws IOException {
        try (Stream<Path> stream = Files.walk(scannedRoot)) {
            return stream
                    .filter(Files::isRegularFile)
                    .filter(p -> p.getFileName().toString().endsWith(".tsv"))
                    .sorted()
                    .toList();
        }
    }

    private static LegacyCount countLegacyRows(Path file) throws IOException {
        long legacy = 0L;
        long total = 0L;
        try (Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8)) {
            for (String raw : (Iterable<String>) lines::iterator) {
                if (raw.isEmpty()) {
                    continue;
                }
                total++;
                // A legacy row is the three-column shape; the new shape has an extra tab.
                int tabs = 0;
                for (int i = 0; i < raw.length(); i++) {
                    if (raw.charAt(i) == '\t') {
                        tabs++;
                    }
                }
                if (tabs == 2) {
                    legacy++;
                }
            }
        }
        return new LegacyCount(legacy, total);
    }

    private static FileResult processFile(Patch patch, Fetcher fetcher, URI artifactBase, URI canonicalSource) {
        List<ScannedEntry> entries;
        try (Stream<String> lines = Files.lines(patch.file(), StandardCharsets.UTF_8)) {
            entries = lines
                    .filter(line -> !line.isEmpty())
                    .map(ScannedEntry::parse)
                    .collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException unreadable) {
            System.err.println("[patch] failed to read " + patch.file() + ": " + unreadable.getMessage());
            return new FileResult(0L, 0L);
        }
        long rowsPatched = 0L;
        long headFailures = 0L;
        boolean changed = false;
        for (int i = 0; i < entries.size(); i++) {
            ScannedEntry existing = entries.get(i);
            if (existing.publishedAt() > 0L) {
                continue;
            }
            long stamp = fetchTimestamp(fetcher, artifactBase, canonicalSource,
                    patch.groupId(), patch.artifactId(), existing.version(), existing.classifier());
            if (stamp <= 0L) {
                headFailures++;
                continue;
            }
            ScannedEntry patched = new ScannedEntry(existing.version(), existing.classifier(), stamp, existing.errorMessage());
            entries.set(i, patched);
            rowsPatched++;
            changed = true;
        }
        if (!changed) {
            return new FileResult(0L, headFailures);
        }
        try {
            writeAtomic(patch.file(), entries);
        } catch (IOException writeFailure) {
            System.err.println("[patch] failed to rewrite " + patch.file() + ": " + writeFailure.getMessage());
            return new FileResult(0L, headFailures);
        }
        return new FileResult(rowsPatched, headFailures);
    }

    private static long fetchTimestamp(Fetcher fetcher, URI artifactBase, URI canonicalSource,
                                       String groupId, String artifactId, String version, String classifier) {
        String relative = mavenJarPath(groupId, artifactId, version, classifier);
        URI primaryUri = artifactBase.resolve(relative);
        Fetcher.HeadProbe primary = fetcher.headLastModifiedProbe(primaryUri);
        if (primary.ok() && primary.canonical()) {
            return primary.lastModifiedMillis();
        }
        if (!canonicalSource.equals(artifactBase)) {
            Fetcher.HeadProbe canonical = fetcher.headLastModifiedProbe(canonicalSource.resolve(relative));
            if (canonical.ok()) {
                return canonical.lastModifiedMillis();
            }
        }
        return primary.ok() ? primary.lastModifiedMillis() : 0L;
    }

    private static String mavenJarPath(String groupId, String artifactId, String version, String classifier) {
        StringBuilder builder = new StringBuilder();
        builder.append(groupId.replace('.', '/')).append('/');
        builder.append(artifactId).append('/');
        builder.append(version).append('/');
        builder.append(artifactId).append('-').append(version);
        if (classifier != null && !classifier.isEmpty()) {
            builder.append('-').append(classifier);
        }
        builder.append(".jar");
        return builder.toString();
    }

    private static void writeAtomic(Path file, List<ScannedEntry> entries) throws IOException {
        Path temp = file.resolveSibling(file.getFileName() + ".tmp");
        try (BufferedWriter writer = Files.newBufferedWriter(temp, StandardCharsets.UTF_8)) {
            for (ScannedEntry entry : entries) {
                writer.write(entry.format());
                writer.write('\n');
            }
        }
        try {
            Files.move(temp, file, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (AtomicMoveNotSupportedException atomicNotSupported) {
            Files.move(temp, file, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private static String groupIdFor(Path scannedRoot, Path file) {
        Path relative = scannedRoot.relativize(file.getParent());
        StringJoiner joiner = new StringJoiner(".");
        for (Path segment : relative) {
            joiner.add(segment.toString());
        }
        return joiner.toString();
    }

    private static String artifactIdFor(Path file) {
        String name = file.getFileName().toString();
        return name.substring(0, name.length() - ".tsv".length());
    }

    private static Optional<GitPublisher> configureGitPublisher(Path dataDir) {
        boolean publish = property(PROP_GIT_PUBLISH).map(v -> parseBoolean(v, PROP_GIT_PUBLISH)).orElse(false);
        if (!publish) {
            return Optional.empty();
        }
        Path workingDirectory = property(PROP_GIT_WORK_DIR).map(Path::of).orElse(Path.of("."));
        int pushEvery = property(PROP_GIT_PUSH_EVERY).map(Integer::parseInt).orElse(GitPublisher.DEFAULT_PUSH_EVERY);
        Path workingAbsolute = workingDirectory.toAbsolutePath();
        Path dataAbsolute = dataDir.toAbsolutePath();
        Path relative = workingAbsolute.relativize(dataAbsolute);
        GitPublisher publisher = new GitPublisher(workingAbsolute, List.of(relative.toString()), pushEvery);
        System.out.println("[patch] Publishing checkpoints via git in " + workingAbsolute + " (pushEvery=" + pushEvery + ")");
        return Optional.of(publisher);
    }

    private static void maybeCheckpoint(Optional<GitPublisher> publisher,
                                        AtomicLong sinceLastCheckpoint,
                                        AtomicLong filesPatched,
                                        AtomicLong rowsPatched,
                                        AtomicLong headFailures) {
        if (publisher.isEmpty()) {
            sinceLastCheckpoint.set(0L);
            return;
        }
        long since = sinceLastCheckpoint.getAndSet(0L);
        if (since <= 0L) {
            return;
        }
        String message = "patch checkpoint filesPatched=" + filesPatched.get()
                + " rowsPatched=" + rowsPatched.get()
                + " headFailures=" + headFailures.get();
        publisher.get().checkpoint(message);
    }

    private static String ensureTrailingSlash(String value) {
        return value.endsWith("/") ? value : value + "/";
    }

    private static Optional<String> property(String name) {
        String value = System.getProperty(name);
        return value == null || value.isBlank() ? Optional.empty() : Optional.of(value.trim());
    }

    private static boolean parseBoolean(String value, String source) {
        return switch (value.toLowerCase(Locale.ROOT)) {
            case "true", "1", "yes" -> true;
            case "false", "0", "no" -> false;
            default -> throw new IllegalArgumentException("Expected true/false for " + source + ", got: " + value);
        };
    }

    private static void printUsage() {
        System.out.println("Usage: PatchScanTimestamp <artifact-base-uri>");
        System.out.println();
        System.out.println("Walks every data/scanned/.../*.tsv file and back-fills the publishedAt column for");
        System.out.println("legacy three-column rows. For each legacy row, the tool issues a HEAD against");
        System.out.println("<artifact-base-uri> (the mirror) first; if the mirror's Last-Modified is non-canonical");
        System.out.println("(e.g. GCS's bucket-import mtime), it falls back to the canonical source configured via");
        System.out.println("-D" + PROP_CANONICAL_TIMESTAMP_URI + ". Files with no legacy rows are skipped.");
        System.out.println();
        System.out.println("Optional system properties:");
        System.out.println("  -D" + PROP_DATA + "=<dir>           (default 'data')");
        System.out.println("  -D" + PROP_CONCURRENCY + "=<N>      (default " + DEFAULT_CONCURRENCY + ")");
        System.out.println("  -D" + PROP_BUDGET_MINUTES + "=<minutes>  (default " + DEFAULT_BUDGET_MINUTES + ")");
        System.out.println("  -D" + PROP_CHECKPOINT_EVERY + "=<N>  patched files per git checkpoint (default " + DEFAULT_CHECKPOINT_EVERY_FILES + ")");
        System.out.println("  -D" + PROP_CANONICAL_TIMESTAMP_URI + "=<uri>  fallback HEAD source");
        System.out.println("  -D" + PROP_GIT_PUBLISH + "=<true|false>  (default false)");
        System.out.println("  -D" + PROP_GIT_WORK_DIR + "=<dir>");
        System.out.println("  -D" + PROP_GIT_PUSH_EVERY + "=<N>");
    }
}
