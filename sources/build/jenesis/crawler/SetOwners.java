package build.jenesis.crawler;

import module java.base;
import build.jenesis.crawler.model.ModuleEntry;
import build.jenesis.crawler.store.ModuleStore;

public final class SetOwners {

    public static final String PROP_DATA = "jenesis.crawler.data";
    private static final String DEFAULT_DATA_DIR = "data";

    private SetOwners() {
    }

    public static void main(String[] arguments) throws IOException {
        List<Path> propertyFiles = new ArrayList<>();
        for (String argument : arguments) {
            if (argument.equals("--help") || argument.equals("-h")) {
                printUsage();
                return;
            }
            propertyFiles.add(Path.of(argument));
        }
        if (propertyFiles.isEmpty()) {
            printUsage();
            throw new IllegalArgumentException("No property files specified");
        }
        String configuredDataDir = System.getProperty(PROP_DATA);
        Path dataDir = configuredDataDir == null || configuredDataDir.isBlank()
                ? Path.of(DEFAULT_DATA_DIR)
                : Path.of(configuredDataDir.trim());
        Path modulesRoot = dataDir.resolve("modules");
        Map<String, Set<String>> groupsByModule = new LinkedHashMap<>();
        Map<String, Set<String>> pairsByModule = new LinkedHashMap<>();
        for (Path file : propertyFiles) {
            mergeFrom(file, groupsByModule, pairsByModule);
        }
        ModuleStore store = new ModuleStore(modulesRoot);
        int cleared = 0;
        int populated = 0;
        for (String moduleName : groupsByModule.keySet()) {
            Set<String> groups = groupsByModule.getOrDefault(moduleName, Set.of());
            Set<String> pairs = pairsByModule.getOrDefault(moduleName, Set.of());
            applyOwners(store, moduleName, groups, pairs);
            if (groups.isEmpty() && pairs.isEmpty()) {
                cleared++;
            } else {
                populated++;
            }
            System.out.println(moduleName + ": owners=" + (groups.size() + pairs.size())
                    + " resolved views regenerated");
        }
        System.out.println("Done. modules touched=" + groupsByModule.size()
                + " populated=" + populated
                + " cleared=" + cleared);
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

    private static void applyOwners(ModuleStore store, String moduleName, Set<String> groups, Set<String> pairs) throws IOException {
        Path ownersFile = store.ownersPathFor(moduleName);
        // Auto-block: every other groupId that publishes this module name is recorded as
        // "blocked", so the resulting owners.tsv names every publisher and the module no longer
        // shows up as unresolved drift. An empty allowlist is the "clear" case (write an empty
        // owners.tsv, which rejects everything) and is left untouched.
        Set<String> blockedGroups = new TreeSet<>();
        if (!groups.isEmpty() || !pairs.isEmpty()) {
            Set<String> pairGroups = new HashSet<>();
            for (String pair : pairs) {
                pairGroups.add(pair.substring(0, pair.indexOf('\t')));
            }
            for (ModuleEntry entry : store.readAllVersions(moduleName)) {
                String groupId = entry.groupId();
                if (!groups.contains(groupId) && !pairGroups.contains(groupId)) {
                    blockedGroups.add(groupId);
                }
            }
        }
        writeOwners(ownersFile, groups, pairs, blockedGroups);
        store.regenerate(moduleName);
    }

    private static void writeOwners(Path file, Set<String> allowedGroups, Set<String> allowedPairs,
                                    Set<String> blockedGroups) throws IOException {
        Path parent = file.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
        List<String> lines = new ArrayList<>(allowedGroups.size() + allowedPairs.size() + blockedGroups.size());
        allowedGroups.stream().sorted().forEach(group -> lines.add(group + "\tallowed"));
        allowedPairs.stream().sorted().forEach(pair -> {
            int tab = pair.indexOf('\t');
            lines.add(pair.substring(0, tab) + ':' + pair.substring(tab + 1) + "\tallowed");
        });
        blockedGroups.stream().sorted().forEach(group -> lines.add(group + "\tblocked"));
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
        System.out.println("Usage: java build.jenesis.crawler.SetOwners <file.properties> [<file.properties> ...]");
        System.out.println();
        System.out.println("Each properties file maps a module name to a comma-separated list of owners.");
        System.out.println("An owner is either '<groupId>' (any artifact in that group) or '<groupId>:<artifactId>'.");
        System.out.println("An empty value clears the module's owners (writes an empty owners.tsv).");
        System.out.println();
        System.out.println("For each mentioned module the tool writes owners.tsv and regenerates the resolved");
        System.out.println("views (artifacts.tsv + modules.tsv) from the existing versions.tsv. The audit log");
        System.out.println("in versions.tsv is never mutated - re-running with a different policy is");
        System.out.println("non-destructive.");
        System.out.println();
        System.out.println("owners.tsv is written in two columns: '<owner>\\tallowed' for each listed owner,");
        System.out.println("plus '<groupId>\\tblocked' for every other groupId that publishes the module name");
        System.out.println("(auto-block), so the file names every publisher and the module is no longer");
        System.out.println("reported as unresolved drift. An empty value writes an empty owners.tsv (rejects all).");
        System.out.println();
        System.out.println("Example properties content:");
        System.out.println("  com.fasterxml.jackson.core=com.fasterxml.jackson.core:jackson-core,software.amazon.awssdk:third-party-jackson-core");
        System.out.println("  org.junit.jupiter=org.junit.jupiter");
        System.out.println("  com.example.removed=");
        System.out.println();
        System.out.println("Multiple files: entries for the same module name are merged (union of owners).");
        System.out.println();
        System.out.println("Optional system property: -D" + PROP_DATA + "=<dir> (default 'data').");
    }
}
