package build.jenesis.crawler.test;

import module java.base;
import module org.junit.jupiter.api;

import build.jenesis.crawler.ListOwners;

import static org.assertj.core.api.Assertions.assertThat;

public class ListOwnersTest {

    @TempDir
    Path root;

    private Path dataDir;
    private Path modulesRoot;

    @BeforeEach
    void setUp() throws IOException {
        dataDir = root.resolve("data");
        modulesRoot = Files.createDirectories(dataDir.resolve("modules"));
    }

    @Test
    public void derives_owners_from_versions_when_no_owners_file() throws IOException {
        writeVersions("com.example.lib", null, """
                1.0\tnamed\tcom.example\tlib\t2024-01-01T00:00:00Z
                0.9\tnamed\thostile.group\timposter\t2024-06-01T00:00:00Z
                """);

        Path output = root.resolve("out.properties");
        ListOwners.main(new String[]{"--data", dataDir.toString(), "--output", output.toString(), "com.example.*"});

        assertThat(Files.readAllLines(output))
                .containsExactly("com.example.lib=com.example:lib,hostile.group:imposter");
    }

    @Test
    public void prefers_owners_file_when_present_using_colon_separator() throws IOException {
        Path moduleDir = writeVersions("com.example.lib", null, """
                1.0\tnamed\tcom.example\tlib\t2024-01-01T00:00:00Z
                1.0\tnamed\thostile.group\timposter\t2024-06-01T00:00:00Z
                """);
        Files.writeString(moduleDir.resolve("owners.tsv"), """
                com.example\tlib
                trusted.group
                """);

        Path output = root.resolve("out.properties");
        ListOwners.main(new String[]{"--data", dataDir.toString(), "--output", output.toString(), "com.example.*"});

        assertThat(Files.readAllLines(output))
                .containsExactly("com.example.lib=com.example:lib,trusted.group");
    }

    @Test
    public void single_star_does_not_cross_dots_but_double_star_does() throws IOException {
        writeVersions("net.bytebuddy.agent", null, "1.0\tnamed\tnet.bytebuddy\tbyte-buddy-agent\t2024-01-01T00:00:00Z\n");
        writeVersions("net.bytebuddy.agent.builder", null, "1.0\tnamed\tnet.bytebuddy\tbyte-buddy-agent\t2024-01-01T00:00:00Z\n");

        Path shallow = root.resolve("shallow.properties");
        ListOwners.main(new String[]{"--data", dataDir.toString(), "--output", shallow.toString(), "net.bytebuddy.*"});
        assertThat(Files.readAllLines(shallow))
                .containsExactly("net.bytebuddy.agent=net.bytebuddy:byte-buddy-agent");

        Path deep = root.resolve("deep.properties");
        ListOwners.main(new String[]{"--data", dataDir.toString(), "--output", deep.toString(), "net.bytebuddy.**"});
        assertThat(Files.readAllLines(deep)).containsExactly(
                "net.bytebuddy.agent=net.bytebuddy:byte-buddy-agent",
                "net.bytebuddy.agent.builder=net.bytebuddy:byte-buddy-agent");
    }

    @Test
    public void merges_owners_across_classifier_variants() throws IOException {
        writeVersions("classy.module", null, "1.0\tnamed\torg.canonical\tlib\t2024-01-01T00:00:00Z\n");
        writeVersions("classy.module", "jakarta", "1.0\tnamed\torg.canonical\tlib-jakarta\t2024-01-01T00:00:00Z\n");

        Path output = root.resolve("out.properties");
        ListOwners.main(new String[]{"--data", dataDir.toString(), "--output", output.toString(), "classy.*"});

        assertThat(Files.readAllLines(output))
                .containsExactly("classy.module=org.canonical:lib,org.canonical:lib-jakarta");
    }

    @Test
    public void empty_owners_file_emits_empty_value() throws IOException {
        Path moduleDir = writeVersions("com.example.cleared", null, "1.0\tnamed\tcom.example\tcleared\t2024-01-01T00:00:00Z\n");
        Files.writeString(moduleDir.resolve("owners.tsv"), "");

        Path output = root.resolve("out.properties");
        ListOwners.main(new String[]{"--data", dataDir.toString(), "--output", output.toString(), "com.example.*"});

        assertThat(Files.readAllLines(output))
                .containsExactly("com.example.cleared=");
    }

    @Test
    public void output_is_alphabetised_by_module_name() throws IOException {
        writeVersions("z.alpha", null, "1.0\tnamed\tz.example\tlib\t2024-01-01T00:00:00Z\n");
        writeVersions("a.alpha", null, "1.0\tnamed\ta.example\tlib\t2024-01-01T00:00:00Z\n");
        writeVersions("m.alpha", null, "1.0\tnamed\tm.example\tlib\t2024-01-01T00:00:00Z\n");

        Path output = root.resolve("out.properties");
        ListOwners.main(new String[]{"--data", dataDir.toString(), "--output", output.toString(), "*.alpha"});

        assertThat(Files.readAllLines(output)).containsExactly(
                "a.alpha=a.example:lib",
                "m.alpha=m.example:lib",
                "z.alpha=z.example:lib");
    }

    @Test
    public void skips_modules_outside_glob() throws IOException {
        writeVersions("included.lib", null, "1.0\tnamed\ti.example\tlib\t2024-01-01T00:00:00Z\n");
        writeVersions("excluded.lib", null, "1.0\tnamed\te.example\tlib\t2024-01-01T00:00:00Z\n");

        Path output = root.resolve("out.properties");
        ListOwners.main(new String[]{"--data", dataDir.toString(), "--output", output.toString(), "included.*"});

        assertThat(Files.readAllLines(output))
                .containsExactly("included.lib=i.example:lib");
    }

    private Path writeVersions(String moduleName, String classifier, String content) throws IOException {
        Path dir = modulesRoot;
        for (String segment : moduleName.split("\\.", -1)) {
            dir = dir.resolve(segment);
        }
        Files.createDirectories(dir);
        String file = classifier == null
                ? "versions.tsv"
                : "versions-" + classifier + ".tsv";
        Files.writeString(dir.resolve(file), content);
        return dir;
    }
}
