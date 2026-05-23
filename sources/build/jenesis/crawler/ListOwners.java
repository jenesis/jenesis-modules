package build.jenesis.crawler;

import module java.base;

public final class ListOwners {

    private static final String FLAG_DATA = "--data";
    private static final String FLAG_OUTPUT = "--output";
    private static final String DEFAULT_DATA_DIR = "data";

    private ListOwners() {
    }

    public static void main(String[] arguments) throws IOException {
        Path dataDir = Path.of(DEFAULT_DATA_DIR);
        Path output = null;
        List<String> globs = new ArrayList<>();
        for (int i = 0; i < arguments.length; i++) {
            String argument = arguments[i];
            switch (argument) {
                case FLAG_DATA -> {
                    if (i + 1 >= arguments.length) {
                        throw new IllegalArgumentException("Missing value for " + FLAG_DATA);
                    }
                    dataDir = Path.of(arguments[++i]);
                }
                case FLAG_OUTPUT -> {
                    if (i + 1 >= arguments.length) {
                        throw new IllegalArgumentException("Missing value for " + FLAG_OUTPUT);
                    }
                    output = Path.of(arguments[++i]);
                }
                case "--help", "-h" -> {
                    printUsage();
                    return;
                }
                default -> globs.add(argument);
            }
        }
        if (globs.isEmpty()) {
            printUsage();
            throw new IllegalArgumentException("No globs specified");
        }
        Path modulesRoot = dataDir.resolve("modules");
        if (!Files.isDirectory(modulesRoot)) {
            throw new IOException("No modules directory at " + modulesRoot);
        }
        List<PathMatcher> matchers = compileGlobs(globs);
        SortedMap<String, List<String>> ownersByModule = new TreeMap<>();
        try (Stream<Path> stream = Files.walk(modulesRoot)) {
            List<Path> moduleDirs = stream
                    .filter(Files::isDirectory)
                    .filter(dir -> !dir.equals(modulesRoot))
                    .filter(ListOwners::hasVersionsFile)
                    .toList();
            for (Path moduleDir : moduleDirs) {
                Path relative = modulesRoot.relativize(moduleDir);
                if (matchers.stream().noneMatch(matcher -> matcher.matches(relative))) {
                    continue;
                }
                String moduleName = dottedName(relative);
                ownersByModule.put(moduleName, collectOwners(moduleDir));
            }
        }
        emit(ownersByModule, output);
        System.err.println(ownersByModule.size() + " module(s) listed.");
    }

    private static String dottedName(Path relative) {
        StringJoiner joiner = new StringJoiner(".");
        for (Path segment : relative) {
            joiner.add(segment.toString());
        }
        return joiner.toString();
    }

    private static boolean hasVersionsFile(Path dir) {
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(dir)) {
            for (Path entry : entries) {
                if (!Files.isRegularFile(entry)) {
                    continue;
                }
                if (isVersionsFile(entry.getFileName().toString())) {
                    return true;
                }
            }
        } catch (IOException io) {
            throw new UncheckedIOException("Failed to inspect " + dir, io);
        }
        return false;
    }

    private static boolean isVersionsFile(String name) {
        if (!name.endsWith(ModuleStore.LEAF_FILE_EXTENSION)) {
            return false;
        }
        String stem = name.substring(0, name.length() - ModuleStore.LEAF_FILE_EXTENSION.length());
        return stem.equals(ModuleStore.LEAF_FILE_BASE)
                || stem.startsWith(ModuleStore.LEAF_FILE_BASE + '-');
    }

    private static List<PathMatcher> compileGlobs(List<String> globs) {
        FileSystem fs = FileSystems.getDefault();
        List<PathMatcher> matchers = new ArrayList<>(globs.size());
        for (String glob : globs) {
            String pathGlob = glob.replace('.', '/');
            matchers.add(fs.getPathMatcher("glob:" + pathGlob));
        }
        return matchers;
    }

    private static List<String> collectOwners(Path moduleDir) throws IOException {
        Path ownersFile = moduleDir.resolve(ModuleStore.OWNERS_FILE);
        if (Files.exists(ownersFile)) {
            return ownersFromOwnersFile(ownersFile);
        }
        return ownersFromVersions(moduleDir);
    }

    private static List<String> ownersFromOwnersFile(Path file) throws IOException {
        SortedSet<String> entries = new TreeSet<>();
        try (Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8)) {
            lines.forEach(rawLine -> {
                String line = rawLine.stripTrailing();
                if (line.isBlank() || line.startsWith("#")) {
                    return;
                }
                entries.add(line.replace('\t', ':'));
            });
        }
        return new ArrayList<>(entries);
    }

    private static List<String> ownersFromVersions(Path moduleDir) throws IOException {
        SortedSet<String> pairs = new TreeSet<>();
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(moduleDir)) {
            for (Path entry : entries) {
                if (!Files.isRegularFile(entry)) {
                    continue;
                }
                if (!isVersionsFile(entry.getFileName().toString())) {
                    continue;
                }
                try (Stream<String> lines = Files.lines(entry, StandardCharsets.UTF_8)) {
                    lines.forEach(line -> {
                        if (line.isEmpty()) {
                            return;
                        }
                        ModuleEntry parsed = ModuleEntry.parse(line);
                        pairs.add(parsed.groupId() + ':' + parsed.artifactId());
                    });
                }
            }
        }
        return new ArrayList<>(pairs);
    }

    private static void emit(SortedMap<String, List<String>> ownersByModule, Path output) throws IOException {
        if (output == null) {
            for (Map.Entry<String, List<String>> entry : ownersByModule.entrySet()) {
                System.out.println(entry.getKey() + '=' + String.join(",", entry.getValue()));
            }
            return;
        }
        Path parent = output.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        Path temp = output.resolveSibling(output.getFileName() + ".tmp");
        try (BufferedWriter writer = Files.newBufferedWriter(temp, StandardCharsets.UTF_8)) {
            for (Map.Entry<String, List<String>> entry : ownersByModule.entrySet()) {
                writer.write(entry.getKey());
                writer.write('=');
                writer.write(String.join(",", entry.getValue()));
                writer.newLine();
            }
        }
        try {
            Files.move(temp, output, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (AtomicMoveNotSupportedException atomicNotSupported) {
            Files.move(temp, output, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private static void printUsage() {
        System.out.println("Usage: java build.jenesis.crawler.ListOwners [--data <dir>] [--output <file>] <glob> [<glob> ...]");
        System.out.println();
        System.out.println("Emits a SetOwners-compatible properties file listing the current owners");
        System.out.println("for every module under data/modules/ whose dotted name matches any of the globs.");
        System.out.println();
        System.out.println("Glob semantics mirror the module-name structure: '*' matches one segment,");
        System.out.println("'**' matches across dots. Example: 'net.bytebuddy.*' matches");
        System.out.println("'net.bytebuddy.agent' but not 'net.bytebuddy.agent.builder'.");
        System.out.println();
        System.out.println("Per module, owners are sourced from owners.tsv when it exists; otherwise from");
        System.out.println("the (groupId, artifactId) pairs found in versions[-<classifier>].tsv.");
        System.out.println();
        System.out.println("With no --output, the properties file is written to stdout.");
    }
}
