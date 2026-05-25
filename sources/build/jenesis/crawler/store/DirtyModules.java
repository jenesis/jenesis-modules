package build.jenesis.crawler.store;

import module java.base;

/**
 * Tracks module names whose current.tsv needs to be regenerated. Persisted to
 * disk so crash recovery between stage 1 (record) and stage 2 (resolve) of a
 * crawl picks up where it left off.
 *
 * Append-only on add; the underlying file is rewritten on remove/clear so the
 * on-disk set stays unique and bounded.
 */
public final class DirtyModules {

    public static final String FILE_NAME = "dirty-modules.tsv";

    private final Path file;
    private final SortedSet<String> pending;

    public DirtyModules(Path dataDir) {
        this.file = dataDir.resolve(FILE_NAME);
        this.pending = new TreeSet<>();
        load();
    }

    private void load() {
        if (!Files.exists(file)) {
            return;
        }
        try (Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8)) {
            lines.map(String::strip).filter(line -> !line.isEmpty()).forEach(pending::add);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read " + file, e);
        }
    }

    public synchronized boolean add(String moduleName) throws IOException {
        if (!pending.add(moduleName)) {
            return false;
        }
        appendLine(moduleName);
        return true;
    }

    public synchronized SortedSet<String> snapshot() {
        return new TreeSet<>(pending);
    }

    public synchronized boolean isEmpty() {
        return pending.isEmpty();
    }

    public synchronized int size() {
        return pending.size();
    }

    public synchronized void remove(String moduleName) throws IOException {
        if (!pending.remove(moduleName)) {
            return;
        }
        rewrite();
    }

    public synchronized void clear() throws IOException {
        pending.clear();
        Files.deleteIfExists(file);
    }

    private void appendLine(String moduleName) throws IOException {
        ensureParent();
        try (BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            writer.write(moduleName);
            writer.newLine();
        }
    }

    private void rewrite() throws IOException {
        if (pending.isEmpty()) {
            Files.deleteIfExists(file);
            return;
        }
        ensureParent();
        Path temp = file.resolveSibling(file.getFileName() + ".tmp");
        try (BufferedWriter writer = Files.newBufferedWriter(temp, StandardCharsets.UTF_8)) {
            for (String name : pending) {
                writer.write(name);
                writer.newLine();
            }
        }
        try {
            Files.move(temp, file, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (AtomicMoveNotSupportedException atomicNotSupported) {
            Files.move(temp, file, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private void ensureParent() throws IOException {
        Path parent = file.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
    }
}
