package build.jenesis.crawler;

import module java.base;
import build.jenesis.crawler.fetch.ByteSource;
import build.jenesis.crawler.fetch.Fetcher;
import build.jenesis.crawler.fetch.Scanner;
import build.jenesis.crawler.model.ModuleEntry;
import build.jenesis.crawler.model.ModuleType;
import build.jenesis.crawler.model.ScannedModule;
import build.jenesis.crawler.store.ModuleStore;

/**
 * One-shot migration tool: walks every {@code versions.tsv} and
 * {@code versions-<classifier>.tsv} under {@code data/modules/} and adds the
 * missing trailing module-info-version column to legacy rows that predate the
 * column.
 *
 * <ul>
 *   <li>{@code AUTOMATIC} rows get an empty trailing column without fetching:
 *       automatic modules have no module-info, so the value would be empty
 *       anyway.</li>
 *   <li>{@code NAMED} rows are patched by re-fetching the JAR (from whichever
 *       artifact base URI was supplied), running the same {@link Scanner} the
 *       crawler uses, and writing {@code ModuleDescriptor.rawVersion()} into
 *       the trailing column (empty when module-info declared no version).</li>
 *   <li>Named rows whose fetch or scan fails are left as legacy so the next
 *       invocation can retry. No state file is needed: the absence of the
 *       trailing column is the resume marker.</li>
 * </ul>
 *
 * Rows that already carry a trailing column (whether empty or not) are skipped:
 * the tool is idempotent and restartable.
 */
public final class PatchModuleVersion {

    public static final String PROP_DATA = "jenesis.crawler.data";
    public static final String PROP_CONCURRENCY = "jenesis.crawler.concurrency";
    public static final String PROP_TAIL_SIZE = "jenesis.crawler.tail.size";

    public static final int DEFAULT_CONCURRENCY = 32;
    public static final int DEFAULT_TAIL_SIZE = Scanner.DEFAULT_TAIL_SIZE;
    private static final String DEFAULT_DATA_DIR = "data";
    private static final String JAR_EXTENSION = "jar";

    private PatchModuleVersion() {
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
            throw new IllegalArgumentException("Expected exactly 1 positional argument (<artifact-base-uri>); got " + arguments.length);
        }
        URI artifactBase = URI.create(arguments[0]);
        Path dataDir = property(PROP_DATA).map(Path::of).orElse(Path.of(DEFAULT_DATA_DIR));
        int concurrency = property(PROP_CONCURRENCY).map(Integer::parseInt).orElse(DEFAULT_CONCURRENCY);
        int tailSize = property(PROP_TAIL_SIZE).map(Integer::parseInt).orElse(DEFAULT_TAIL_SIZE);
        Path modulesRoot = dataDir.resolve("modules");

        System.out.println("[info] Configuration:");
        System.out.println("[info]   dataDir=" + dataDir.toAbsolutePath());
        System.out.println("[info]   modulesRoot=" + modulesRoot.toAbsolutePath());
        System.out.println("[info]   artifactBase=" + artifactBase);
        System.out.println("[info]   concurrency=" + concurrency);
        System.out.println("[info]   tailSize=" + tailSize);

        if (!Files.isDirectory(modulesRoot)) {
            System.out.println("[info] No modules root at " + modulesRoot + "; nothing to patch.");
            return;
        }
        try (Fetcher fetcher = new Fetcher()) {
            Stats stats = patch(modulesRoot, fetcher, new Scanner(tailSize), artifactBase, concurrency, tailSize);
            System.out.println("[info] Done. files=" + stats.filesScanned
                    + " filesPatched=" + stats.filesPatched
                    + " rows[total]=" + stats.rowsTotal
                    + " rows[legacy]=" + stats.rowsLegacy
                    + " rows[patched]=" + stats.rowsPatched
                    + " rows[automatic]=" + stats.rowsAutomatic
                    + " rows[failed]=" + stats.rowsFailed);
        }
    }

    /**
     * Walks every versions file under {@code modulesRoot} and patches legacy rows. Returns aggregated
     * statistics. Visible for tests so they can drive the patch loop against a fake registry without
     * spinning up the {@code main} argument parser.
     */
    public static Stats patch(Path modulesRoot,
                              Fetcher fetcher,
                              Scanner scanner,
                              URI artifactBase,
                              int concurrency,
                              int tailSize) throws IOException {
        if (concurrency <= 0) {
            throw new IllegalArgumentException("concurrency must be > 0; got " + concurrency);
        }
        Stats stats = new Stats();
        Semaphore inflight = new Semaphore(concurrency);
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
             Stream<Path> stream = Files.walk(modulesRoot)) {
            for (Path file : (Iterable<Path>) stream::iterator) {
                if (!Files.isRegularFile(file)) {
                    continue;
                }
                Optional<VersionsFile> versionsFile = VersionsFile.from(modulesRoot, file);
                if (versionsFile.isEmpty()) {
                    continue;
                }
                patchFile(versionsFile.get(), fetcher, scanner, artifactBase, executor, inflight, tailSize, stats);
            }
        }
        return stats;
    }

    private static void patchFile(VersionsFile file,
                                  Fetcher fetcher,
                                  Scanner scanner,
                                  URI artifactBase,
                                  ExecutorService executor,
                                  Semaphore inflight,
                                  int tailSize,
                                  Stats stats) throws IOException {
        List<ModuleEntry> entries = readEntries(file.path());
        stats.filesScanned++;
        stats.rowsTotal += entries.size();

        ModuleEntry[] patched = new ModuleEntry[entries.size()];
        List<Integer> namedIndices = new ArrayList<>();
        List<Future<Optional<String>>> namedFutures = new ArrayList<>();

        for (int i = 0; i < entries.size(); i++) {
            ModuleEntry entry = entries.get(i);
            if (entry.moduleVersion() != null) {
                continue;
            }
            stats.rowsLegacy++;
            if (entry.type() == ModuleType.AUTOMATIC) {
                patched[i] = withModuleVersion(entry, "");
                stats.rowsAutomatic++;
                continue;
            }
            URI uri = artifactBase.resolve(mavenPath(file, entry));
            namedIndices.add(i);
            namedFutures.add(submitScan(executor, inflight, fetcher, scanner, uri, tailSize));
        }

        for (int j = 0; j < namedFutures.size(); j++) {
            int rowIndex = namedIndices.get(j);
            ModuleEntry entry = entries.get(rowIndex);
            try {
                Optional<String> result = namedFutures.get(j).get();
                if (result.isEmpty()) {
                    // The row was previously recorded as NAMED, so a successful re-fetch should
                    // produce a ScannedModule. An empty result here means the JAR no longer scans
                    // as a named module (deleted, replaced with a non-modular jar, etc.). Leave
                    // the row as legacy so a future run can retry once the source recovers.
                    stats.rowsFailed++;
                    System.out.println("[warn] " + describe(file, entry) + ": no named module found on re-scan; leaving as legacy");
                    continue;
                }
                patched[rowIndex] = withModuleVersion(entry, result.get());
                stats.rowsPatched++;
            } catch (InterruptedException interrupted) {
                Thread.currentThread().interrupt();
                throw new IOException("Interrupted while patching " + file.path(), interrupted);
            } catch (ExecutionException failed) {
                stats.rowsFailed++;
                System.out.println("[warn] " + describe(file, entry) + ": " + rootCause(failed));
            }
        }

        boolean anyChange = Arrays.stream(patched).anyMatch(Objects::nonNull);
        if (!anyChange) {
            return;
        }
        List<ModuleEntry> output = new ArrayList<>(entries.size());
        for (int i = 0; i < entries.size(); i++) {
            output.add(patched[i] != null ? patched[i] : entries.get(i));
        }
        writeEntries(file.path(), output);
        stats.filesPatched++;
        System.out.println("[info] " + file.path() + ": rows=" + entries.size() + " patched=" + countNonNull(patched));
    }

    private static Future<Optional<String>> submitScan(ExecutorService executor,
                                                       Semaphore inflight,
                                                       Fetcher fetcher,
                                                       Scanner scanner,
                                                       URI uri,
                                                       int tailSize) {
        return executor.submit(() -> {
            inflight.acquire();
            try {
                ByteSource source = fetcher.sourceWithCachedTail(uri, tailSize);
                Optional<ScannedModule> module = scanner.scan(source);
                // Empty trailing column for module-info-without-version is encoded as "" so the
                // distinction from legacy (null moduleVersion) is preserved on disk.
                return module.map(scanned -> scanned.moduleVersion() == null ? "" : scanned.moduleVersion());
            } finally {
                inflight.release();
            }
        });
    }

    private static ModuleEntry withModuleVersion(ModuleEntry entry, String moduleVersion) {
        return new ModuleEntry(entry.mavenVersion(), entry.type(), entry.groupId(), entry.artifactId(), entry.publishedAt(), moduleVersion);
    }

    private static String mavenPath(VersionsFile file, ModuleEntry entry) {
        StringBuilder builder = new StringBuilder();
        builder.append(entry.groupId().replace('.', '/')).append('/');
        builder.append(entry.artifactId()).append('/');
        builder.append(entry.mavenVersion().raw()).append('/');
        builder.append(entry.artifactId()).append('-').append(entry.mavenVersion().raw());
        if (file.classifier() != null) {
            builder.append('-').append(file.classifier());
        }
        builder.append('.').append(JAR_EXTENSION);
        return builder.toString();
    }

    private static String describe(VersionsFile file, ModuleEntry entry) {
        StringBuilder builder = new StringBuilder();
        builder.append(entry.groupId()).append(':').append(entry.artifactId()).append(':').append(entry.mavenVersion().raw());
        if (file.classifier() != null) {
            builder.append(':').append(file.classifier());
        }
        return builder.toString();
    }

    private static Throwable rootCause(ExecutionException failed) {
        Throwable cause = failed.getCause();
        return cause != null ? cause : failed;
    }

    private static int countNonNull(ModuleEntry[] array) {
        int count = 0;
        for (ModuleEntry entry : array) {
            if (entry != null) {
                count++;
            }
        }
        return count;
    }

    private static List<ModuleEntry> readEntries(Path file) throws IOException {
        try (Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8)) {
            return lines.filter(line -> !line.isEmpty()).map(ModuleEntry::parse).toList();
        }
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

    private static Optional<String> property(String name) {
        String value = System.getProperty(name);
        return value == null || value.isBlank() ? Optional.empty() : Optional.of(value.trim());
    }

    private static void printUsage() {
        System.out.println("Usage: java build.jenesis.crawler.PatchModuleVersion <artifact-base-uri>");
        System.out.println();
        System.out.println("Walks every versions.tsv under <dataDir>/modules/ and adds the missing");
        System.out.println("trailing module-info-version column to legacy rows that predate it.");
        System.out.println("Automatic-module rows are patched in place (empty trailing column).");
        System.out.println("Named-module rows are patched by re-fetching the JAR from <artifact-base-uri>");
        System.out.println("and re-reading ModuleDescriptor.rawVersion(). Failed fetches leave the row");
        System.out.println("as legacy; rerunning the tool retries them.");
        System.out.println();
        System.out.println("System properties:");
        System.out.println("  -D" + PROP_DATA + "=<dir>          Data directory (default 'data')");
        System.out.println("  -D" + PROP_CONCURRENCY + "=<n>     Concurrent fetches (default " + DEFAULT_CONCURRENCY + ")");
        System.out.println("  -D" + PROP_TAIL_SIZE + "=<bytes>   Tail-fetch size per JAR (default " + DEFAULT_TAIL_SIZE + ")");
    }

    /** Aggregated counters reported at the end of a run. */
    public static final class Stats {
        public long filesScanned;
        public long filesPatched;
        public long rowsTotal;
        public long rowsLegacy;
        public long rowsPatched;
        public long rowsAutomatic;
        public long rowsFailed;
    }

    /** A single {@code versions[-<classifier>].tsv} file together with the module name it serves. */
    private record VersionsFile(Path path, String moduleName, String classifier) {

        static Optional<VersionsFile> from(Path modulesRoot, Path file) {
            String name = file.getFileName().toString();
            if (!name.endsWith(ModuleStore.LEAF_FILE_EXTENSION)) {
                return Optional.empty();
            }
            String stem = name.substring(0, name.length() - ModuleStore.LEAF_FILE_EXTENSION.length());
            String classifier;
            if (stem.equals(ModuleStore.LEAF_FILE_BASE)) {
                classifier = null;
            } else if (stem.startsWith(ModuleStore.LEAF_FILE_BASE + '-')) {
                classifier = stem.substring(ModuleStore.LEAF_FILE_BASE.length() + 1);
            } else {
                return Optional.empty();
            }
            Path moduleDir = file.getParent();
            if (moduleDir == null) {
                return Optional.empty();
            }
            Path relative = modulesRoot.relativize(moduleDir);
            StringBuilder builder = new StringBuilder();
            for (Path part : relative) {
                if (builder.length() > 0) {
                    builder.append('.');
                }
                builder.append(part.toString());
            }
            String moduleName = builder.toString();
            if (!ModuleStore.isValidModuleName(moduleName)) {
                return Optional.empty();
            }
            return Optional.of(new VersionsFile(file, moduleName, classifier));
        }
    }
}
