package build.jenesis.crawler;

import module java.base;
import build.jenesis.crawler.store.ModuleStore;
import build.jenesis.crawler.store.ModuleStore.Scope;

/**
 * Walks {@code data/modules/} and recomputes {@code artifacts[-classifier].tsv} and/or
 * {@code modules[-classifier].tsv} from each module's {@code versions[-classifier].tsv}.
 * The intersection of glob filters and scope decides which files are touched: every other
 * file on disk is left exactly as it was. Used after algorithm changes in
 * {@link ModuleStore#regenerate(String, Scope)} (for example, the policy that drops named
 * rows whose {@code module-info} version contradicts the Maven version), where
 * {@code modules.tsv} needs to be re-emitted across the catalogue without re-fetching JARs.
 */
public final class Regenerate {

    public static final String PROP_DATA = "jenesis.crawler.data";
    public static final String PROP_SCOPE = "jenesis.crawler.regenerate.scope";
    public static final String PROP_DRY_RUN = "jenesis.crawler.regenerate.dry.run";
    private static final String DEFAULT_DATA_DIR = "data";

    private Regenerate() {
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
        String configuredDataDir = System.getProperty(PROP_DATA);
        Path dataDir = configuredDataDir == null || configuredDataDir.isBlank()
                ? Path.of(DEFAULT_DATA_DIR)
                : Path.of(configuredDataDir.trim());
        Scope scope = parseScope(System.getProperty(PROP_SCOPE));
        boolean dryRun = booleanProperty(PROP_DRY_RUN, false);
        Path modulesRoot = dataDir.resolve("modules");
        if (!Files.isDirectory(modulesRoot)) {
            throw new IOException("No modules directory at " + modulesRoot);
        }
        List<PathMatcher> matchers = globs.isEmpty() ? List.of() : compileGlobs(globs);

        ModuleStore store = new ModuleStore(modulesRoot);
        long matched = 0L;
        long regenerated = 0L;
        try (Stream<Path> stream = Files.walk(modulesRoot)) {
            List<Path> moduleDirs = stream
                    .filter(Files::isDirectory)
                    .filter(dir -> !dir.equals(modulesRoot))
                    .filter(Regenerate::looksLikeModuleDir)
                    .sorted()
                    .toList();
            for (Path moduleDir : moduleDirs) {
                Path relative = modulesRoot.relativize(moduleDir);
                if (!matchers.isEmpty() && matchers.stream().noneMatch(matcher -> matcher.matches(relative))) {
                    continue;
                }
                matched++;
                String moduleName = dottedName(relative);
                if (!ModuleStore.isValidModuleName(moduleName)) {
                    System.err.println("[regenerate] skip invalid module name: " + moduleName);
                    continue;
                }
                if (dryRun) {
                    System.out.println(moduleName);
                    continue;
                }
                store.regenerate(moduleName, scope);
                regenerated++;
            }
        }
        if (dryRun) {
            System.err.println("[regenerate] dry-run matched=" + matched + " scope=" + scope.name().toLowerCase(Locale.ROOT));
        } else {
            System.err.println("[regenerate] matched=" + matched + " regenerated=" + regenerated
                    + " scope=" + scope.name().toLowerCase(Locale.ROOT));
        }
    }

    private static Scope parseScope(String raw) {
        if (raw == null || raw.isBlank()) {
            return Scope.BOTH;
        }
        return switch (raw.trim().toLowerCase(Locale.ROOT)) {
            case "both", "all" -> Scope.BOTH;
            case "artifacts", "artifact" -> Scope.ARTIFACTS;
            case "modules", "module" -> Scope.MODULES;
            default -> throw new IllegalArgumentException(
                    "Expected " + PROP_SCOPE + "=both|artifacts|modules, got: " + raw);
        };
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

    private static boolean looksLikeModuleDir(Path dir) {
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(dir)) {
            for (Path entry : entries) {
                if (!Files.isRegularFile(entry)) {
                    continue;
                }
                if (isVersionsFile(entry.getFileName().toString())) {
                    return true;
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to inspect " + dir, e);
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

    private static void printUsage() {
        System.out.println("Usage: java build.jenesis.crawler.Regenerate [<glob> ...]");
        System.out.println();
        System.out.println("Walks data/modules/ and rewrites artifacts.tsv and/or modules.tsv from the");
        System.out.println("versions.tsv contents of every matching module. Pass globs to narrow the walk,");
        System.out.println("or no glob to regenerate every module. Glob semantics mirror the module-name");
        System.out.println("structure: '*' matches one segment, '**' matches across dots. Example:");
        System.out.println("'net.bytebuddy.*' matches 'net.bytebuddy.agent' but not 'net.bytebuddy.agent.builder'.");
        System.out.println();
        System.out.println("Optional system properties:");
        System.out.println("  -D" + PROP_DATA + "=<dir>");
        System.out.println("        Data directory (default 'data').");
        System.out.println("  -D" + PROP_SCOPE + "=<both|artifacts|modules>");
        System.out.println("        Which resolved views to rewrite (default 'both'). 'artifacts' touches");
        System.out.println("        only artifacts[-classifier].tsv; 'modules' touches only");
        System.out.println("        modules[-classifier].tsv. The other family is left exactly as on disk.");
        System.out.println("  -D" + PROP_DRY_RUN + "=<true|false>");
        System.out.println("        When true, list the module names that would be regenerated to stdout");
        System.out.println("        and exit without writing anything (default false).");
    }
}
