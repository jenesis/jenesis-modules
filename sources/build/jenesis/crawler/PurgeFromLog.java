package build.jenesis.crawler;

import module java.base;
import build.jenesis.crawler.model.ModuleEntry;
import build.jenesis.crawler.store.ModuleStore;

/**
 * One-shot cleanup: reads a PatchTimestamp workflow log on stdin, extracts every
 * {@code [patch] HEAD failure uri=... status=404} line, and removes the corresponding rows
 * from {@code versions[-classifier].tsv}. Empty files are deleted. Affected modules are
 * regenerated through {@link ModuleStore#regenerate}.
 *
 * <p>Used to surgically clean up rows for artifacts that have been withdrawn from Maven
 * Central since the original scan recorded them. The audit log loses these rows
 * intentionally - a record pointing at a 404 URL is no longer useful.
 *
 * <p>Usage:
 * <pre>
 *   cat workflow.log | java sources/build/jenesis/crawler/PurgeFromLog.java
 * </pre>
 */
public final class PurgeFromLog {

    public static final String PROP_DATA = Crawl.PROP_DATA;

    // [patch] HEAD failure uri=<URI> status=<N> [error="..."]
    private static final Pattern LOG_LINE = Pattern.compile(
            "\\[patch\\] HEAD failure uri=(\\S+) status=(\\d+)");

    private PurgeFromLog() {
    }

    public static void main(String[] arguments) throws IOException {
        Path dataDir = property(PROP_DATA).map(Path::of).orElse(Path.of("data"));
        Path modulesRoot = dataDir.resolve("modules");
        if (!Files.isDirectory(modulesRoot)) {
            throw new IOException("No modules directory at " + modulesRoot);
        }

        // Parse the log on stdin.
        Set<Target> targets = new LinkedHashSet<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher matcher = LOG_LINE.matcher(line);
                if (!matcher.find()) {
                    continue;
                }
                if (!matcher.group(2).equals("404")) {
                    continue;
                }
                Target target = parseUri(matcher.group(1));
                if (target != null) {
                    targets.add(target);
                }
            }
        }
        System.out.println("[purge] parsed " + targets.size() + " distinct 404 target(s) from stdin");
        if (targets.isEmpty()) {
            return;
        }

        // Index targets by classifier (null/non-null) → set of (groupId, artifactId, version).
        Map<String, Set<Coord>> targetsByClassifier = new HashMap<>();
        for (Target target : targets) {
            targetsByClassifier
                    .computeIfAbsent(nullToEmpty(target.classifier()), _ -> new HashSet<>())
                    .add(new Coord(target.groupId(), target.artifactId(), target.version()));
        }

        // Walk every versions[-classifier].tsv and remove matching rows. Track affected modules.
        Set<String> affectedModules = new TreeSet<>();
        long rowsRemoved = 0L;
        long filesTouched = 0L;
        long filesDeleted = 0L;
        try (Stream<Path> stream = Files.walk(modulesRoot)) {
            for (Path file : (Iterable<Path>) stream::iterator) {
                if (!Files.isRegularFile(file)) {
                    continue;
                }
                String name = file.getFileName().toString();
                if (!name.endsWith(ModuleStore.LEAF_FILE_EXTENSION)) {
                    continue;
                }
                String stem = name.substring(0, name.length() - ModuleStore.LEAF_FILE_EXTENSION.length());
                String classifier;
                if (stem.equals(ModuleStore.LEAF_FILE_BASE)) {
                    classifier = "";
                } else if (stem.startsWith(ModuleStore.LEAF_FILE_BASE + '-')) {
                    classifier = stem.substring(ModuleStore.LEAF_FILE_BASE.length() + 1);
                } else {
                    continue;
                }
                Set<Coord> match = targetsByClassifier.get(classifier);
                if (match == null) {
                    continue;
                }
                int removed = filterFile(file, match);
                if (removed == 0) {
                    continue;
                }
                rowsRemoved += removed;
                filesTouched++;
                if (isFileEmpty(file)) {
                    Files.deleteIfExists(file);
                    filesDeleted++;
                }
                String moduleName = pathToModuleName(modulesRoot, file.getParent());
                if (ModuleStore.isValidModuleName(moduleName)) {
                    affectedModules.add(moduleName);
                }
            }
        }

        System.out.println("[purge] rows removed: " + rowsRemoved
                + " across " + filesTouched + " file(s)"
                + " (" + filesDeleted + " file(s) deleted as empty)");
        System.out.println("[purge] regenerating " + affectedModules.size() + " affected module(s)...");

        ModuleStore store = new ModuleStore(modulesRoot);
        long regenerated = 0L;
        for (String moduleName : affectedModules) {
            store.regenerate(moduleName);
            regenerated++;
            if (regenerated % 100 == 0) {
                System.out.println("[purge]   regenerated " + regenerated + "/" + affectedModules.size());
            }
        }
        System.out.println("[purge] Done. removed=" + rowsRemoved + " filesTouched=" + filesTouched
                + " filesDeleted=" + filesDeleted + " modulesRegenerated=" + regenerated);
    }

    private record Target(String groupId, String artifactId, String version, String classifier) {
    }

    private record Coord(String groupId, String artifactId, String version) {
    }

    /**
     * Pulls out {@code groupId / artifactId / version / classifier?} from a Maven URL like
     * {@code https://repo.maven.apache.org/maven2/<group-path>/<artifactId>/<version>/<filename>.jar}.
     */
    private static Target parseUri(String uri) {
        int marker = uri.indexOf("/maven2/");
        if (marker < 0) {
            return null;
        }
        String tail = uri.substring(marker + "/maven2/".length());
        String[] segments = tail.split("/");
        if (segments.length < 4) {
            return null;
        }
        String filename = segments[segments.length - 1];
        if (!filename.endsWith(".jar")) {
            return null;
        }
        String version = segments[segments.length - 2];
        String artifactId = segments[segments.length - 3];
        StringJoiner groupJoiner = new StringJoiner(".");
        for (int i = 0; i < segments.length - 3; i++) {
            groupJoiner.add(segments[i]);
        }
        String groupId = groupJoiner.toString();
        String basename = filename.substring(0, filename.length() - ".jar".length());
        String prefix = artifactId + "-" + version;
        if (!basename.startsWith(prefix)) {
            return null;
        }
        String rest = basename.substring(prefix.length());
        String classifier;
        if (rest.isEmpty()) {
            classifier = null;
        } else if (rest.startsWith("-")) {
            classifier = rest.substring(1);
            if (classifier.isEmpty()) {
                return null;
            }
        } else {
            return null;
        }
        return new Target(groupId, artifactId, version, classifier);
    }

    private static int filterFile(Path file, Set<Coord> targets) throws IOException {
        List<ModuleEntry> kept = new ArrayList<>();
        int removed = 0;
        try (Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8)) {
            for (String line : (Iterable<String>) lines::iterator) {
                if (line.isEmpty()) {
                    continue;
                }
                ModuleEntry entry = ModuleEntry.parse(line);
                Coord key = new Coord(entry.groupId(), entry.artifactId(), entry.mavenVersion().raw());
                if (targets.contains(key)) {
                    removed++;
                } else {
                    kept.add(entry);
                }
            }
        }
        if (removed == 0) {
            return 0;
        }
        Path temp = file.resolveSibling(file.getFileName() + ".tmp");
        try (BufferedWriter writer = Files.newBufferedWriter(temp, StandardCharsets.UTF_8)) {
            for (ModuleEntry entry : kept) {
                writer.write(entry.format());
                writer.newLine();
            }
        }
        try {
            Files.move(temp, file, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (AtomicMoveNotSupportedException atomicNotSupported) {
            Files.move(temp, file, StandardCopyOption.REPLACE_EXISTING);
        }
        return removed;
    }

    private static boolean isFileEmpty(Path file) throws IOException {
        return Files.size(file) == 0L;
    }

    private static String pathToModuleName(Path modulesRoot, Path moduleDir) {
        Path relative = modulesRoot.relativize(moduleDir);
        StringJoiner joiner = new StringJoiner(".");
        for (Path segment : relative) {
            joiner.add(segment.toString());
        }
        return joiner.toString();
    }

    private static String nullToEmpty(String value) {
        return value == null ? "" : value;
    }

    private static Optional<String> property(String name) {
        String value = System.getProperty(name);
        return value == null || value.isBlank() ? Optional.empty() : Optional.of(value.trim());
    }
}
