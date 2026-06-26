package build.jenesis.crawler;

import module java.base;

class ModuleMaven {
    void main() throws Exception {        
        var root = Path.of("data/modules");
        try (var stream = Files.walk(root)) {
            stream
                .filter(path -> path.getNameCount() > 0)
                .filter(path -> path.getFileName().toString().equals("modules.tsv"))
                .map(path -> computeUniqueModuleGroupArtifactLine(root, path))
                .filter(line -> !line.isEmpty())
                .sorted()
                .forEach(IO::println);
        }        
    }

    static String computeUniqueModuleGroupArtifactLine(Path root, Path path) {
        var joiner = new StringJoiner(".");
        for (var element : root.relativize(path).getParent()) joiner.add(element.toString());
        var moduleName = joiner.toString();
        try {
            var lines = Files.readAllLines(path);
            var items = lines.getFirst().split("\t");
            var mavenGroup = items[1];
            var mavenGroupAlias = computeMavenGroupAlias(mavenGroup);
            var mavenArtifact = items[2];
            return moduleName.startsWith(mavenGroup) || moduleName.startsWith(mavenGroupAlias)
                ? moduleName + '=' + mavenGroup + ':' + mavenArtifact
                : "";
        } catch (IOException exception) {
            throw new UncheckedIOException(exception);
        }
    }

    static String computeMavenGroupAlias(String group) {
        return switch (group) {
            case "com.fasterxml.jackson.core" -> "com.fasterxml.jackson";
            case "com.github.almasb" -> "com.almasb";
            case "io.github.openfeign" -> "feign";
            case "javax.json" -> "java.json";
            case "net.colesico.framework" -> "colesico.framework";
            case "org.jetbrains.kotlin" -> "kotlin";
            case "org.jfxtras" -> "jfxtras";
            case "org.openjfx" -> "javafx";
            case "org.ow2.asm" -> "org.objectweb.asm";
            case "org.projectlombok" -> "lombok";
            case "org.swimos" -> "swim";
            default -> group.replace("-", "");
        };
    }    
}
