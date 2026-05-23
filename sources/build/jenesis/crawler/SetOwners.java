package build.jenesis.crawler;

import module java.base;

public final class SetOwners {

    private static final String FLAG_DATA = "--data";
    private static final String DEFAULT_DATA_DIR = "data";

    private SetOwners() {
    }

    public static void main(String[] arguments) throws IOException {
        Path dataDir = Path.of(DEFAULT_DATA_DIR);
        List<Path> propertyFiles = new ArrayList<>();
        for (int i = 0; i < arguments.length; i++) {
            String argument = arguments[i];
            switch (argument) {
                case FLAG_DATA -> {
                    if (i + 1 >= arguments.length) {
                        throw new IllegalArgumentException("Missing value for " + FLAG_DATA);
                    }
                    dataDir = Path.of(arguments[++i]);
                }
                case "--help", "-h" -> {
                    printUsage();
                    return;
                }
                default -> propertyFiles.add(Path.of(argument));
            }
        }
        if (propertyFiles.isEmpty()) {
            printUsage();
            throw new IllegalArgumentException("No property files specified");
        }
        Path modulesRoot = dataDir.resolve("modules");
        Map<String, Set<String>> groupsByModule = new LinkedHashMap<>();
        Map<String, Set<String>> pairsByModule = new LinkedHashMap<>();
        for (Path file : propertyFiles) {
            mergeFrom(file, groupsByModule, pairsByModule);
        }
        int cleared = 0;
        int populated = 0;
        long rowsKept = 0L;
        long rowsDropped = 0L;
        for (String moduleName : groupsByModule.keySet()) {
            Set<String> groups = groupsByModule.getOrDefault(moduleName, Set.of());
            Set<String> pairs = pairsByModule.getOrDefault(moduleName, Set.of());
            ApplyResult result = applyOwners(modulesRoot, moduleName, groups, pairs);
            if (groups.isEmpty() && pairs.isEmpty()) {
                cleared++;
            } else {
                populated++;
            }
            rowsKept += result.kept();
            rowsDropped += result.dropped();
            System.out.println(moduleName + ": owners=" + (groups.size() + pairs.size())
                    + " versions kept=" + result.kept() + " dropped=" + result.dropped());
        }
        System.out.println("Done. modules touched=" + groupsByModule.size()
                + " populated=" + populated
                + " cleared=" + cleared
                + " rows kept=" + rowsKept
                + " rows dropped=" + rowsDropped);
    }

    private static void mergeFrom(Path file,
                                  Map<String, Set<String>> groupsByModule,
                                  Map<String, Set<String>> pairsByModule) throws IOException {
        Properties properties = new Properties();
        try (Reader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            properties.load(reader);
        }
        for (String moduleName : properties.stringPropertyNames()) {
            if (!ModuleStore.isValidModuleName(moduleName)) {
                throw new IllegalArgumentException("Invalid module name in " + file + ": " + moduleName);
            }
            Set<String> groups = groupsByModule.computeIfAbsent(moduleName, _ -> new LinkedHashSet<>());
            Set<String> pairs = pairsByModule.computeIfAbsent(moduleName, _ -> new LinkedHashSet<>());
            String value = properties.getProperty(moduleName, "").trim();
            if (value.isEmpty()) {
                continue;
            }
            for (String raw : value.split(",")) {
                String entry = raw.trim();
                if (entry.isEmpty()) {
                    continue;
                }
                int colon = entry.indexOf(':');
                if (colon < 0) {
                    groups.add(entry);
                } else {
                    String groupId = entry.substring(0, colon).trim();
                    String artifactId = entry.substring(colon + 1).trim();
                    if (groupId.isEmpty() || artifactId.isEmpty()) {
                        throw new IllegalArgumentException("Invalid owner '" + entry + "' for module " + moduleName + " in " + file);
                    }
                    if (entry.indexOf(':', colon + 1) >= 0) {
                        throw new IllegalArgumentException("Owner '" + entry + "' for module " + moduleName + " in " + file + " has more than one colon");
                    }
                    pairs.add(groupId + '\t' + artifactId);
                }
            }
        }
    }

    private record ApplyResult(long kept, long dropped) {
    }

    private static ApplyResult applyOwners(Path modulesRoot,
                                           String moduleName,
                                           Set<String> groups,
                                           Set<String> pairs) throws IOException {
        Path moduleDir = modulesRoot;
        for (String segment : moduleName.split("\\.", -1)) {
            moduleDir = moduleDir.resolve(segment);
        }
        Files.createDirectories(moduleDir);
        writeOwners(moduleDir.resolve(ModuleStore.OWNERS_FILE), groups, pairs);
        long kept = 0L;
        long dropped = 0L;
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(moduleDir)) {
            for (Path entry : entries) {
                if (!Files.isRegularFile(entry)) {
                    continue;
                }
                String name = entry.getFileName().toString();
                if (!isVersionsFile(name)) {
                    continue;
                }
                FilterResult result = filterVersions(entry, groups, pairs);
                kept += result.kept();
                dropped += result.dropped();
            }
        }
        return new ApplyResult(kept, dropped);
    }

    private static boolean isVersionsFile(String name) {
        if (!name.endsWith(ModuleStore.LEAF_FILE_EXTENSION)) {
            return false;
        }
        String stem = name.substring(0, name.length() - ModuleStore.LEAF_FILE_EXTENSION.length());
        return stem.equals(ModuleStore.LEAF_FILE_BASE)
                || stem.startsWith(ModuleStore.LEAF_FILE_BASE + '-');
    }

    private record FilterResult(long kept, long dropped) {
    }

    private static FilterResult filterVersions(Path file, Set<String> groups, Set<String> pairs) throws IOException {
        List<String> kept = new ArrayList<>();
        long dropped = 0L;
        try (Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8)) {
            for (String line : (Iterable<String>) lines::iterator) {
                if (line.isEmpty()) {
                    continue;
                }
                ModuleEntry parsed = ModuleEntry.parse(line);
                if (groups.contains(parsed.groupId()) || pairs.contains(parsed.groupId() + '\t' + parsed.artifactId())) {
                    kept.add(line);
                } else {
                    dropped++;
                }
            }
        }
        if (kept.isEmpty()) {
            Files.deleteIfExists(file);
        } else {
            writeAtomic(file, kept);
        }
        return new FilterResult(kept.size(), dropped);
    }

    private static void writeOwners(Path file, Set<String> groups, Set<String> pairs) throws IOException {
        List<String> lines = new ArrayList<>(groups.size() + pairs.size());
        groups.stream().sorted().forEach(lines::add);
        pairs.stream().sorted().forEach(lines::add);
        writeAtomic(file, lines);
    }

    private static void writeAtomic(Path file, List<String> lines) throws IOException {
        Path parent = file.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        Path temp = file.resolveSibling(file.getFileName() + ".tmp");
        try (BufferedWriter writer = Files.newBufferedWriter(temp, StandardCharsets.UTF_8)) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
        try {
            Files.move(temp, file, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (AtomicMoveNotSupportedException atomicNotSupported) {
            Files.move(temp, file, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private static void printUsage() {
        System.out.println("Usage: java build.jenesis.crawler.SetOwners [--data <dir>] <file.properties> [<file.properties> ...]");
        System.out.println();
        System.out.println("Each properties file maps a module name to a comma-separated list of owners.");
        System.out.println("An owner is either '<groupId>' (any artifact in that group) or '<groupId>:<artifactId>'.");
        System.out.println("An empty value clears the module's owners (writes an empty owners.tsv and drops all versions).");
        System.out.println();
        System.out.println("Example:");
        System.out.println("  com.fasterxml.jackson.core=com.fasterxml.jackson.core:jackson-core,software.amazon.awssdk:third-party-jackson-core");
        System.out.println("  org.junit.jupiter=org.junit.jupiter");
        System.out.println("  com.example.removed=");
        System.out.println();
        System.out.println("Multiple files: entries for the same module name are merged (union of owners).");
    }
}
