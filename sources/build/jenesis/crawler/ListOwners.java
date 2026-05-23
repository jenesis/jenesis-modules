package build.jenesis.crawler;

import module java.base;

public final class ListOwners {

    public static final String PROP_DATA = "jenesis.crawler.data";
    public static final String PROP_GROUP_ONLY = "jenesis.crawler.list.group.only";
    public static final String PROP_ONLY_MISSING_OWNERS = "jenesis.crawler.list.only.missing.owners";
    public static final String PROP_ONLY_AMBIGUOUS = "jenesis.crawler.list.only.ambiguous";
    private static final String DEFAULT_DATA_DIR = "data";

    private ListOwners() {
    }

    public static void main(String[] arguments) throws IOException {
        List<String> globs = new ArrayList<>();
        for (String argument : arguments) {
            if (argument.equals("--help") || argument.equals("-h")) {
                printUsage();
                return;
            }
            globs.add(argument);
        }
        if (globs.isEmpty()) {
            printUsage();
            throw new IllegalArgumentException("No globs specified");
        }
        String configuredDataDir = System.getProperty(PROP_DATA);
        Path dataDir = configuredDataDir == null || configuredDataDir.isBlank()
                ? Path.of(DEFAULT_DATA_DIR)
                : Path.of(configuredDataDir.trim());
        boolean groupOnly = booleanProperty(PROP_GROUP_ONLY, true);
        boolean onlyMissingOwners = booleanProperty(PROP_ONLY_MISSING_OWNERS, false);
        boolean onlyAmbiguous = booleanProperty(PROP_ONLY_AMBIGUOUS, false);
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
                Path ownersFile = moduleDir.resolve(ModuleStore.OWNERS_FILE);
                boolean hasOwners = Files.exists(ownersFile);
                if (onlyMissingOwners && hasOwners) {
                    continue;
                }
                String moduleName = dottedName(relative);
                List<String> owners = hasOwners
                        ? ownersFromOwnersFile(ownersFile)
                        : ownersFromVersions(moduleDir, groupOnly);
                if (onlyAmbiguous && owners.size() < 2) {
                    continue;
                }
                ownersByModule.put(moduleName, owners);
            }
        }
        emit(ownersByModule);
        System.err.println(ownersByModule.size() + " module(s) listed.");
    }

    private static boolean booleanProperty(String name, boolean defaultValue) {
        String value = System.getProperty(name);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return switch (value.trim().toLowerCase(Locale.ROOT)) {
            case "true", "1", "yes" -> true;
            case "false", "0", "no" -> false;
            default -> throw new IllegalArgumentException("Expected true/false for " + name + ", got: " + value);
        };
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

    private static List<String> ownersFromVersions(Path moduleDir, boolean groupOnly) throws IOException {
        SortedSet<String> owners = new TreeSet<>();
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
                        owners.add(groupOnly
                                ? parsed.groupId()
                                : parsed.groupId() + ':' + parsed.artifactId());
                    });
                }
            }
        }
        return new ArrayList<>(owners);
    }

    private static void emit(SortedMap<String, List<String>> ownersByModule) {
        for (Map.Entry<String, List<String>> entry : ownersByModule.entrySet()) {
            System.out.println(entry.getKey() + '=' + String.join(",", entry.getValue()));
        }
    }

    private static void printUsage() {
        System.out.println("Usage: java build.jenesis.crawler.ListOwners <glob> [<glob> ...]");
        System.out.println();
        System.out.println("Writes a SetOwners-compatible properties stream to stdout, listing the current");
        System.out.println("owners of every module under data/modules/ whose dotted name matches any glob.");
        System.out.println();
        System.out.println("Glob semantics mirror the module-name structure: '*' matches one segment,");
        System.out.println("'**' matches across dots. Example: 'net.bytebuddy.*' matches");
        System.out.println("'net.bytebuddy.agent' but not 'net.bytebuddy.agent.builder'.");
        System.out.println();
        System.out.println("Per module, owners are sourced from owners.tsv when it exists; otherwise from");
        System.out.println("the (groupId, artifactId) pairs found in versions[-<classifier>].tsv.");
        System.out.println();
        System.out.println("Optional system properties:");
        System.out.println("  -D" + PROP_DATA + "=<dir>");
        System.out.println("        Data directory (default 'data').");
        System.out.println("  -D" + PROP_GROUP_ONLY + "=<true|false>");
        System.out.println("        For modules without an owners.tsv, emit only groupIds (default true).");
        System.out.println("        Set to false to emit groupId:artifactId pairs derived from versions.tsv.");
        System.out.println("  -D" + PROP_ONLY_MISSING_OWNERS + "=<true|false>");
        System.out.println("        Skip modules that already have an owners.tsv (default false).");
        System.out.println("  -D" + PROP_ONLY_AMBIGUOUS + "=<true|false>");
        System.out.println("        Keep only modules whose computed owners list has more than one entry");
        System.out.println("        (default false). Counted after the group-only dedup.");
        System.out.println();
        System.out.println("Pipe stdout to a file (or to SetOwners) as needed.");
    }
}
