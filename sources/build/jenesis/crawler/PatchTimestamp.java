package build.jenesis.crawler;

import module java.base;
import build.jenesis.crawler.fetch.Fetcher;
import build.jenesis.crawler.model.ModuleEntry;
import build.jenesis.crawler.publish.GitPublisher;
import build.jenesis.crawler.store.ModuleStore;

/**
 * One-shot backfill: walks every {@code versions[-classifier].tsv} under {@code data/modules/},
 * HEADs each row's artifact for its canonical {@code Last-Modified}, and rewrites the row's
 * publication timestamp when the on-disk value differs. After every module's version files have
 * been patched, the module's resolved views ({@code artifacts.tsv} and {@code modules.tsv}) are
 * regenerated so the implicit-owner heuristic re-resolves against the corrected timestamps.
 *
 * <p>Brings the existing audit log to the state it would have if the crawler had always sourced
 * {@code publishedAt} from the storage layer's {@code Last-Modified} rather than the Nexus
 * index's per-record timestamp (the index occasionally re-stamps records during republishing
 * events; see "Sourcing {@code publishedAt}" in the README).
 *
 * <p>Usage:
 * <pre>
 *   java sources/build/jenesis/crawler/PatchTimestamp.java &lt;artifact-base-uri&gt;
 * </pre>
 *
 * <p>The HEAD target defaults to {@code <artifact-base-uri>}; override with
 * {@code -Djenesis.crawler.canonical.timestamp.uri=...} to point HEADs at a different repository.
 * To pick up canonical timestamps for pre-2019 GCS bulk-imports, set this property to
 * {@code https://repo.maven.apache.org/maven2/} when the primary base is the GCS mirror.
 *
 * <p>The tool is idempotent. Failed HEADs (404, timeout, network error, missing header) leave
 * the row's existing timestamp untouched; a re-run will re-HEAD them.
 */
public final class PatchTimestamp {

    public static final String PROP_DATA = Crawl.PROP_DATA;
    public static final String PROP_CONCURRENCY = Crawl.PROP_CONCURRENCY;
    public static final String PROP_CANONICAL_TIMESTAMP_URI = Crawl.PROP_CANONICAL_TIMESTAMP_URI;
    public static final String PROP_GIT_PUBLISH = Crawl.PROP_GIT_PUBLISH;
    public static final String PROP_GIT_WORK_DIR = Crawl.PROP_GIT_WORK_DIR;
    public static final String PROP_GIT_PUSH_EVERY = Crawl.PROP_GIT_PUSH_EVERY;
    public static final String PROP_COMMIT_EVERY = "jenesis.patch.commit.every";

    private static final int DEFAULT_CONCURRENCY = 64;
    private static final int DEFAULT_COMMIT_EVERY = 100;
    /**
     * Per-module resume marker. An empty file placed inside a module directory once that
     * module's patch has been committed. A re-run skips any module whose directory already
     * contains this file: zero HEADs, zero regeneration. Committed alongside the catalogue
     * updates so a workflow restart picks up exactly where the killed run left off. Swept
     * away on clean completion of the run so the repo doesn't permanently carry one of
     * these per module. Size: 0 bytes per marker; bounded total since git deduplicates the
     * empty blob across every tree entry.
     */
    private static final String MARKER_FILE = ".timestampUpdate";

    private PatchTimestamp() {
    }

    public static void main(String[] arguments) throws IOException {
        if (arguments.length == 0 || arguments[0].equals("--help") || arguments[0].equals("-h")) {
            printUsage();
            if (arguments.length == 0) {
                throw new IllegalArgumentException("Missing required <artifact-base-uri> positional argument");
            }
            return;
        }
        if (arguments.length > 1) {
            printUsage();
            throw new IllegalArgumentException("Expected exactly 1 positional argument (<artifact-base-uri>); got "
                    + arguments.length);
        }
        URI artifactBase = normaliseTrailingSlash(URI.create(arguments[0]));
        URI canonicalBase = property(PROP_CANONICAL_TIMESTAMP_URI)
                .map(URI::create)
                .map(PatchTimestamp::normaliseTrailingSlash)
                .orElse(artifactBase);
        Path dataDir = property(PROP_DATA).map(Path::of).orElse(Path.of("data"));
        int concurrency = property(PROP_CONCURRENCY).map(Integer::parseInt).orElse(DEFAULT_CONCURRENCY);
        if (concurrency < 1) {
            throw new IllegalArgumentException("Concurrency must be >= 1, got " + concurrency);
        }

        Path modulesRoot = dataDir.resolve("modules");
        if (!Files.isDirectory(modulesRoot)) {
            throw new IOException("No modules directory at " + modulesRoot);
        }

        boolean gitPublish = property(PROP_GIT_PUBLISH).map(value -> Crawl.parseBoolean(value, PROP_GIT_PUBLISH)).orElse(false);
        Path workingDirectory = property(PROP_GIT_WORK_DIR).map(Path::of).orElse(Path.of("."));
        int pushEvery = property(PROP_GIT_PUSH_EVERY).map(Integer::parseInt).orElse(GitPublisher.DEFAULT_PUSH_EVERY);
        int commitEvery = property(PROP_COMMIT_EVERY).map(Integer::parseInt).orElse(DEFAULT_COMMIT_EVERY);
        if (commitEvery < 1) {
            throw new IllegalArgumentException("commitEvery must be >= 1, got " + commitEvery);
        }
        GitPublisher publisher = null;
        if (gitPublish) {
            Path dataAbs = dataDir.toAbsolutePath();
            Path workingAbs = workingDirectory.toAbsolutePath();
            Path relativeData = workingAbs.relativize(dataAbs);
            publisher = new GitPublisher(workingAbs, List.of(relativeData.toString()), pushEvery);
        }

        System.out.println("[info] Configuration:");
        System.out.println("[info]   dataDir=" + dataDir.toAbsolutePath());
        System.out.println("[info]   artifactBase=" + artifactBase);
        System.out.println("[info]   canonicalTimestampBase=" + canonicalBase);
        System.out.println("[info]   concurrency=" + concurrency);
        System.out.println("[info]   gitPublish=" + gitPublish
                + (gitPublish ? " (commitEvery=" + commitEvery + " modules, pushEvery=" + pushEvery + " commits)" : ""));

        List<Path> moduleDirs = collectModuleDirs(modulesRoot);
        long alreadyPatched = moduleDirs.stream()
                .filter(dir -> Files.exists(dir.resolve(MARKER_FILE)))
                .count();
        System.out.println("[info] Found " + moduleDirs.size() + " module directory/ies to patch.");
        if (alreadyPatched > 0L) {
            System.out.println("[info] Resuming: " + alreadyPatched
                    + " module(s) carry a '" + MARKER_FILE
                    + "' marker from a previous run and will be skipped.");
        }

        long startMillis = System.currentTimeMillis();
        long totalRows = 0L;
        long updatedRows = 0L;
        long headFailures = 0L;
        long touchedFiles = 0L;
        long regeneratedModules = 0L;

        ModuleStore store = new ModuleStore(modulesRoot);
        try (Fetcher fetcher = new Fetcher();
             ExecutorService executor = Executors.newFixedThreadPool(concurrency)) {

            for (int i = 0; i < moduleDirs.size(); i++) {
                Path moduleDir = moduleDirs.get(i);
                String moduleName = moduleNameFor(modulesRoot, moduleDir);
                if (!ModuleStore.isValidModuleName(moduleName)) {
                    continue;
                }
                Path marker = moduleDir.resolve(MARKER_FILE);
                if (Files.exists(marker)) {
                    continue;
                }
                boolean dirChanged = false;
                for (Path versionsFile : listVersionFiles(moduleDir)) {
                    String classifier = classifierFor(versionsFile);
                    List<ModuleEntry> oldEntries = readEntries(versionsFile);
                    if (oldEntries.isEmpty()) {
                        continue;
                    }
                    totalRows += oldEntries.size();
                    PatchResult result = patchEntries(executor, fetcher, canonicalBase, oldEntries, classifier);
                    updatedRows += result.updated();
                    headFailures += result.failures();
                    if (result.updated() > 0L) {
                        writeEntries(versionsFile, result.entries());
                        touchedFiles++;
                        dirChanged = true;
                    }
                }
                if (dirChanged) {
                    store.regenerate(moduleName);
                    regeneratedModules++;
                }
                Files.writeString(marker, "", StandardCharsets.UTF_8);

                int completed = i + 1;
                boolean lastModule = i == moduleDirs.size() - 1;
                if ((completed % 200 == 0) || lastModule) {
                    long elapsedSec = Math.max(1L, (System.currentTimeMillis() - startMillis) / 1000L);
                    System.out.println("[patch] modules=" + completed + "/" + moduleDirs.size()
                            + " rows=" + totalRows
                            + " updated=" + updatedRows
                            + " headFailures=" + headFailures
                            + " touchedFiles=" + touchedFiles
                            + " regeneratedModules=" + regeneratedModules
                            + " elapsedSec=" + elapsedSec);
                }
                if (publisher != null && (completed % commitEvery == 0 || lastModule)) {
                    publisher.checkpoint("patch: progress modules=" + completed + "/" + moduleDirs.size()
                            + " updated=" + updatedRows
                            + " touchedFiles=" + touchedFiles);
                }
            }
        }

        long sweptMarkers = sweepMarkers(moduleDirs);
        if (sweptMarkers > 0L) {
            System.out.println("[patch] Swept " + sweptMarkers + " resume marker(s) on completion.");
            if (publisher != null) {
                publisher.checkpoint("patch: clear resume markers (" + sweptMarkers + ")");
            }
        }
        if (publisher != null) {
            publisher.flush();
        }

        long elapsedSec = Math.max(1L, (System.currentTimeMillis() - startMillis) / 1000L);
        System.out.println("[patch] Done."
                + " modules=" + moduleDirs.size()
                + " rows=" + totalRows
                + " updated=" + updatedRows
                + " headFailures=" + headFailures
                + " touchedFiles=" + touchedFiles
                + " regeneratedModules=" + regeneratedModules
                + " elapsedSec=" + elapsedSec);
    }

    private record PatchResult(List<ModuleEntry> entries, long updated, long failures) {
    }

    private static PatchResult patchEntries(ExecutorService executor,
                                            Fetcher fetcher,
                                            URI canonicalBase,
                                            List<ModuleEntry> oldEntries,
                                            String classifier) {
        List<Future<ModuleEntry>> futures = new ArrayList<>(oldEntries.size());
        long[] counters = new long[2];
        for (ModuleEntry entry : oldEntries) {
            URI uri = canonicalBase.resolve(mavenJarPath(entry, classifier));
            futures.add(executor.submit(() -> probe(fetcher, uri, entry, counters)));
        }
        List<ModuleEntry> updated = new ArrayList<>(oldEntries.size());
        for (Future<ModuleEntry> future : futures) {
            try {
                updated.add(future.get());
            } catch (InterruptedException interrupted) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Interrupted while awaiting HEAD result", interrupted);
            } catch (ExecutionException e) {
                throw new RuntimeException("HEAD task threw", e.getCause());
            }
        }
        return new PatchResult(updated, counters[0], counters[1]);
    }

    private static ModuleEntry probe(Fetcher fetcher, URI uri, ModuleEntry entry, long[] counters) {
        Fetcher.HeadProbe head;
        try {
            head = fetcher.headLastModifiedProbe(uri);
        } catch (Throwable error) {
            logHeadFailure(uri, 0, error.getClass().getSimpleName() + ": " + error.getMessage());
            synchronized (counters) {
                counters[1]++;
            }
            return entry;
        }
        if (!head.ok()) {
            logHeadFailure(uri, head.status(), head.error());
            synchronized (counters) {
                counters[1]++;
            }
            return entry;
        }
        long millis = head.lastModifiedMillis();
        if (millis == entry.publishedAt()) {
            return entry;
        }
        synchronized (counters) {
            counters[0]++;
        }
        return new ModuleEntry(entry.mavenVersion(), entry.type(), entry.groupId(), entry.artifactId(),
                millis, entry.moduleVersion());
    }

    private static void logHeadFailure(URI uri, int status, String error) {
        StringBuilder line = new StringBuilder("[patch] HEAD failure uri=").append(uri);
        if (status > 0) {
            line.append(" status=").append(status);
        }
        if (error != null && !error.isEmpty()) {
            line.append(" error=\"").append(error).append('"');
        }
        System.err.println(line);
    }

    private static String mavenJarPath(ModuleEntry entry, String classifier) {
        StringBuilder builder = new StringBuilder();
        builder.append(entry.groupId().replace('.', '/')).append('/');
        builder.append(entry.artifactId()).append('/');
        builder.append(entry.mavenVersion().raw()).append('/');
        builder.append(entry.artifactId()).append('-').append(entry.mavenVersion().raw());
        if (classifier != null) {
            builder.append('-').append(classifier);
        }
        builder.append(".jar");
        return builder.toString();
    }

    private static List<ModuleEntry> readEntries(Path file) throws IOException {
        List<ModuleEntry> entries = new ArrayList<>();
        try (Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8)) {
            lines.filter(line -> !line.isEmpty()).map(ModuleEntry::parse).forEach(entries::add);
        }
        return entries;
    }

    private static void writeEntries(Path file, List<ModuleEntry> entries) throws IOException {
        Path temp = file.resolveSibling(file.getFileName() + ".tmp");
        try (BufferedWriter writer = Files.newBufferedWriter(temp, StandardCharsets.UTF_8)) {
            for (ModuleEntry entry : entries) {
                writer.write(entry.format());
                writer.newLine();
            }
        }
        try {
            Files.move(temp, file, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (AtomicMoveNotSupportedException atomicNotSupported) {
            Files.move(temp, file, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private static List<Path> collectModuleDirs(Path modulesRoot) throws IOException {
        List<Path> dirs = new ArrayList<>();
        try (Stream<Path> stream = Files.walk(modulesRoot)) {
            for (Path dir : (Iterable<Path>) stream::iterator) {
                if (dir.equals(modulesRoot) || !Files.isDirectory(dir)) {
                    continue;
                }
                if (!listVersionFiles(dir).isEmpty()) {
                    dirs.add(dir);
                }
            }
        }
        Collections.sort(dirs);
        return dirs;
    }

    private static List<Path> listVersionFiles(Path moduleDir) throws IOException {
        List<Path> files = new ArrayList<>();
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(moduleDir)) {
            for (Path entry : entries) {
                if (!Files.isRegularFile(entry)) {
                    continue;
                }
                String name = entry.getFileName().toString();
                if (!name.endsWith(ModuleStore.LEAF_FILE_EXTENSION)) {
                    continue;
                }
                String stem = name.substring(0, name.length() - ModuleStore.LEAF_FILE_EXTENSION.length());
                if (stem.equals(ModuleStore.LEAF_FILE_BASE)
                        || stem.startsWith(ModuleStore.LEAF_FILE_BASE + '-')) {
                    files.add(entry);
                }
            }
        }
        Collections.sort(files);
        return files;
    }

    private static String classifierFor(Path versionsFile) {
        String name = versionsFile.getFileName().toString();
        String stem = name.substring(0, name.length() - ModuleStore.LEAF_FILE_EXTENSION.length());
        if (stem.equals(ModuleStore.LEAF_FILE_BASE)) {
            return null;
        }
        return stem.substring(ModuleStore.LEAF_FILE_BASE.length() + 1);
    }

    private static long sweepMarkers(List<Path> moduleDirs) throws IOException {
        long swept = 0L;
        for (Path moduleDir : moduleDirs) {
            Path marker = moduleDir.resolve(MARKER_FILE);
            if (Files.deleteIfExists(marker)) {
                swept++;
            }
        }
        return swept;
    }

    private static String moduleNameFor(Path modulesRoot, Path moduleDir) {
        Path relative = modulesRoot.relativize(moduleDir);
        StringJoiner joiner = new StringJoiner(".");
        for (Path segment : relative) {
            joiner.add(segment.toString());
        }
        return joiner.toString();
    }

    private static URI normaliseTrailingSlash(URI uri) {
        String text = uri.toString();
        return text.endsWith("/") ? uri : URI.create(text + "/");
    }

    private static Optional<String> property(String name) {
        String value = System.getProperty(name);
        return value == null || value.isBlank() ? Optional.empty() : Optional.of(value.trim());
    }

    private static void printUsage() {
        System.out.println("Usage: java sources/build/jenesis/crawler/PatchTimestamp.java <artifact-base-uri>");
        System.out.println();
        System.out.println("Walks data/modules/, HEADs each row's artifact for its canonical Last-Modified,");
        System.out.println("rewrites the row when the on-disk publication timestamp differs, then regenerates");
        System.out.println("the resolved views (artifacts.tsv, modules.tsv) for every touched module.");
        System.out.println();
        System.out.println("Brings the audit log to the state it would have if the crawler had always sourced");
        System.out.println("publishedAt from the storage-layer Last-Modified rather than the Nexus index's");
        System.out.println("per-record timestamp.");
        System.out.println();
        System.out.println("Optional system properties:");
        System.out.println("  -D" + PROP_DATA + "=<dir>                          Data directory (default 'data')");
        System.out.println("  -D" + PROP_CONCURRENCY + "=<n>                   Concurrent HEAD fetches (default 64)");
        System.out.println("  -D" + PROP_CANONICAL_TIMESTAMP_URI + "=<uri>");
        System.out.println("                                                     Repository to HEAD for the canonical");
        System.out.println("                                                     Last-Modified. Defaults to the primary");
        System.out.println("                                                     <artifact-base-uri>. Set this to");
        System.out.println("                                                     https://repo.maven.apache.org/maven2/");
        System.out.println("                                                     when the primary is the GCS mirror to");
        System.out.println("                                                     correctly handle pre-2019 imports.");
    }
}
