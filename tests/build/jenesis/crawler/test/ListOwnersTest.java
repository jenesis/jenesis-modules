package build.jenesis.crawler.test;

import module java.base;
import build.jenesis.crawler.ListOwners;
import module org.junit.jupiter.api;


import static org.assertj.core.api.Assertions.assertThat;

public class ListOwnersTest {

    @TempDir
    Path root;

    private Path dataDir;
    private Path modulesRoot;
    private PrintStream originalOut;
    private ByteArrayOutputStream captured;

    @BeforeEach
    void setUp() throws IOException {
        dataDir = root.resolve("data");
        modulesRoot = Files.createDirectories(dataDir.resolve("modules"));
        System.setProperty(ListOwners.PROP_DATA, dataDir.toString());
        originalOut = System.out;
        captured = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captured, true, StandardCharsets.UTF_8));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.clearProperty(ListOwners.PROP_DATA);
        System.clearProperty(ListOwners.PROP_GROUP_ONLY);
        System.clearProperty(ListOwners.PROP_ONLY_MISSING_OWNERS);
        System.clearProperty(ListOwners.PROP_ONLY_AMBIGUOUS);
    }

    private List<String> capturedLines() {
        return captured.toString(StandardCharsets.UTF_8)
                .lines()
                .toList();
    }

    @Test
    public void emits_group_ids_only_from_artifacts_tsv_by_default() throws IOException {
        // artifacts.tsv has multiple groupIds (because owners.tsv allowed several); listing collapses to groupIds.
        writeArtifacts("com.example.lib", null, """
                1.0\tnamed\tcom.example\tlib
                0.9\tnamed\thostile.group\timposter
                """);

        ListOwners.main(new String[]{"com.example.*"});

        assertThat(capturedLines())
                .containsExactly("com.example.lib=com.example,hostile.group");
    }

    @Test
    public void includes_artifact_ids_when_group_only_disabled() throws IOException {
        System.setProperty(ListOwners.PROP_GROUP_ONLY, "false");
        writeArtifacts("com.example.lib", null, """
                1.0\tnamed\tcom.example\tlib
                0.9\tnamed\thostile.group\timposter
                """);

        ListOwners.main(new String[]{"com.example.*"});

        assertThat(capturedLines())
                .containsExactly("com.example.lib=com.example:lib,hostile.group:imposter");
    }

    @Test
    public void skips_modules_with_owners_tsv_when_only_missing_set() throws IOException {
        System.setProperty(ListOwners.PROP_ONLY_MISSING_OWNERS, "true");
        Path withOwners = writeArtifacts("com.example.has", null, "1.0\tnamed\tcom.example\thas\n");
        Files.writeString(withOwners.resolve("owners.tsv"), "com.example\thas\n");
        writeArtifacts("com.example.lacks", null, "1.0\tnamed\tcom.example\tlacks\n");

        ListOwners.main(new String[]{"com.example.*"});

        assertThat(capturedLines())
                .containsExactly("com.example.lacks=com.example");
    }

    @Test
    public void only_ambiguous_keeps_modules_with_more_than_one_owner() throws IOException {
        System.setProperty(ListOwners.PROP_ONLY_AMBIGUOUS, "true");
        writeArtifacts("solo.module", null, "1.0\tnamed\tsolo.example\tlib\n");
        writeArtifacts("contested.module", null, """
                1.0\tnamed\tcanonical.org\tcontested
                1.0\tnamed\thostile.org\tcontested
                """);

        ListOwners.main(new String[]{"**"});

        assertThat(capturedLines())
                .containsExactly("contested.module=canonical.org,hostile.org");
    }

    @Test
    public void single_star_does_not_cross_dots_but_double_star_does() throws IOException {
        writeArtifacts("net.bytebuddy.agent", null, "1.0\tnamed\tnet.bytebuddy\tbyte-buddy-agent\n");
        writeArtifacts("net.bytebuddy.agent.builder", null, "1.0\tnamed\tnet.bytebuddy\tbyte-buddy-agent\n");

        ListOwners.main(new String[]{"net.bytebuddy.*"});
        assertThat(capturedLines())
                .containsExactly("net.bytebuddy.agent=net.bytebuddy");

        captured.reset();
        ListOwners.main(new String[]{"net.bytebuddy.**"});
        assertThat(capturedLines()).containsExactly(
                "net.bytebuddy.agent=net.bytebuddy",
                "net.bytebuddy.agent.builder=net.bytebuddy");
    }

    @Test
    public void merges_owners_across_classifier_variants() throws IOException {
        System.setProperty(ListOwners.PROP_GROUP_ONLY, "false");
        writeArtifacts("classy.module", null, "1.0\tnamed\torg.canonical\tlib\n");
        writeArtifacts("classy.module", "jakarta", "1.0\tnamed\torg.canonical\tlib-jakarta\n");

        ListOwners.main(new String[]{"classy.*"});

        assertThat(capturedLines())
                .containsExactly("classy.module=org.canonical:lib,org.canonical:lib-jakarta");
    }

    @Test
    public void output_is_alphabetised_by_module_name() throws IOException {
        writeArtifacts("z.alpha", null, "1.0\tnamed\tz.example\tlib\n");
        writeArtifacts("a.alpha", null, "1.0\tnamed\ta.example\tlib\n");
        writeArtifacts("m.alpha", null, "1.0\tnamed\tm.example\tlib\n");

        ListOwners.main(new String[]{"*.alpha"});

        assertThat(capturedLines()).containsExactly(
                "a.alpha=a.example",
                "m.alpha=m.example",
                "z.alpha=z.example");
    }

    @Test
    public void skips_modules_outside_glob() throws IOException {
        writeArtifacts("included.lib", null, "1.0\tnamed\ti.example\tlib\n");
        writeArtifacts("excluded.lib", null, "1.0\tnamed\te.example\tlib\n");

        ListOwners.main(new String[]{"included.*"});

        assertThat(capturedLines())
                .containsExactly("included.lib=i.example");
    }

    @Test
    public void skips_modules_with_no_artifacts_tsv() throws IOException {
        // versions.tsv exists but artifacts.tsv is missing (e.g. stage 2 hasn't run yet).
        Path dir = modulesRoot.resolve("pending").resolve("module");
        Files.createDirectories(dir);
        Files.writeString(dir.resolve("versions.tsv"), "1.0\tnamed\tx.org\tlib\t2024-01-01T00:00:00Z\n");

        ListOwners.main(new String[]{"pending.*"});

        assertThat(capturedLines()).isEmpty();
    }

    private Path writeArtifacts(String moduleName, String classifier, String content) throws IOException {
        Path dir = modulesRoot;
        for (String segment : moduleName.split("\\.", -1)) {
            dir = dir.resolve(segment);
        }
        Files.createDirectories(dir);
        String file = classifier == null
                ? "artifacts.tsv"
                : "artifacts-" + classifier + ".tsv";
        Files.writeString(dir.resolve(file), content);
        return dir;
    }
}
