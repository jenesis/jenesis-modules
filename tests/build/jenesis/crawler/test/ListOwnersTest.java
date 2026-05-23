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
    public void derives_group_ids_only_from_versions_by_default() throws IOException {
        writeVersions("com.example.lib", null, """
                1.0\tnamed\tcom.example\tlib\t2024-01-01T00:00:00Z
                0.9\tnamed\thostile.group\timposter\t2024-06-01T00:00:00Z
                """);

        ListOwners.main(new String[]{"com.example.*"});

        assertThat(capturedLines())
                .containsExactly("com.example.lib=com.example,hostile.group");
    }

    @Test
    public void includes_artifact_ids_when_group_only_disabled() throws IOException {
        System.setProperty(ListOwners.PROP_GROUP_ONLY, "false");
        writeVersions("com.example.lib", null, """
                1.0\tnamed\tcom.example\tlib\t2024-01-01T00:00:00Z
                0.9\tnamed\thostile.group\timposter\t2024-06-01T00:00:00Z
                """);

        ListOwners.main(new String[]{"com.example.*"});

        assertThat(capturedLines())
                .containsExactly("com.example.lib=com.example:lib,hostile.group:imposter");
    }

    @Test
    public void only_ambiguous_keeps_modules_with_more_than_one_owner() throws IOException {
        System.setProperty(ListOwners.PROP_ONLY_AMBIGUOUS, "true");
        // Single-owner module - filtered out under default group-only.
        writeVersions("solo.module", null, "1.0\tnamed\tsolo.example\tlib\t2024-01-01T00:00:00Z\n");
        // Two distinct groupIds - kept.
        writeVersions("contested.module", null, """
                1.0\tnamed\tcanonical.org\tcontested\t2024-01-01T00:00:00Z
                1.0\tnamed\thostile.org\tcontested\t2024-06-01T00:00:00Z
                """);

        ListOwners.main(new String[]{"**"});

        assertThat(capturedLines())
                .containsExactly("contested.module=canonical.org,hostile.org");
    }

    @Test
    public void only_ambiguous_respects_group_only_dedup() throws IOException {
        System.setProperty(ListOwners.PROP_ONLY_AMBIGUOUS, "true");
        // Two artifacts under the same group - dedupes to one entry under group-only, filtered out.
        writeVersions("monogroup.module", null, """
                1.0\tnamed\tone.org\tartifact-a\t2024-01-01T00:00:00Z
                1.0\tnamed\tone.org\tartifact-b\t2024-01-01T00:00:00Z
                """);

        ListOwners.main(new String[]{"**"});

        assertThat(capturedLines()).isEmpty();

        captured.reset();
        // Same data, but now we DO want artifact-level granularity.
        System.setProperty(ListOwners.PROP_GROUP_ONLY, "false");
        ListOwners.main(new String[]{"**"});

        assertThat(capturedLines())
                .containsExactly("monogroup.module=one.org:artifact-a,one.org:artifact-b");
    }

    @Test
    public void skips_modules_with_owners_tsv_when_only_missing_set() throws IOException {
        System.setProperty(ListOwners.PROP_ONLY_MISSING_OWNERS, "true");
        Path withOwners = writeVersions("com.example.has", null, "1.0\tnamed\tcom.example\thas\t2024-01-01T00:00:00Z\n");
        Files.writeString(withOwners.resolve("owners.tsv"), "com.example\thas\n");
        writeVersions("com.example.lacks", null, "1.0\tnamed\tcom.example\tlacks\t2024-01-01T00:00:00Z\n");

        ListOwners.main(new String[]{"com.example.*"});

        assertThat(capturedLines())
                .containsExactly("com.example.lacks=com.example");
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

        ListOwners.main(new String[]{"com.example.*"});

        assertThat(capturedLines())
                .containsExactly("com.example.lib=com.example:lib,trusted.group");
    }

    @Test
    public void single_star_does_not_cross_dots_but_double_star_does() throws IOException {
        writeVersions("net.bytebuddy.agent", null, "1.0\tnamed\tnet.bytebuddy\tbyte-buddy-agent\t2024-01-01T00:00:00Z\n");
        writeVersions("net.bytebuddy.agent.builder", null, "1.0\tnamed\tnet.bytebuddy\tbyte-buddy-agent\t2024-01-01T00:00:00Z\n");

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
        writeVersions("classy.module", null, "1.0\tnamed\torg.canonical\tlib\t2024-01-01T00:00:00Z\n");
        writeVersions("classy.module", "jakarta", "1.0\tnamed\torg.canonical\tlib-jakarta\t2024-01-01T00:00:00Z\n");

        ListOwners.main(new String[]{"classy.*"});

        assertThat(capturedLines())
                .containsExactly("classy.module=org.canonical:lib,org.canonical:lib-jakarta");
    }

    @Test
    public void empty_owners_file_emits_empty_value() throws IOException {
        Path moduleDir = writeVersions("com.example.cleared", null, "1.0\tnamed\tcom.example\tcleared\t2024-01-01T00:00:00Z\n");
        Files.writeString(moduleDir.resolve("owners.tsv"), "");

        ListOwners.main(new String[]{"com.example.*"});

        assertThat(capturedLines())
                .containsExactly("com.example.cleared=");
    }

    @Test
    public void output_is_alphabetised_by_module_name() throws IOException {
        writeVersions("z.alpha", null, "1.0\tnamed\tz.example\tlib\t2024-01-01T00:00:00Z\n");
        writeVersions("a.alpha", null, "1.0\tnamed\ta.example\tlib\t2024-01-01T00:00:00Z\n");
        writeVersions("m.alpha", null, "1.0\tnamed\tm.example\tlib\t2024-01-01T00:00:00Z\n");

        ListOwners.main(new String[]{"*.alpha"});

        assertThat(capturedLines()).containsExactly(
                "a.alpha=a.example",
                "m.alpha=m.example",
                "z.alpha=z.example");
    }

    @Test
    public void skips_modules_outside_glob() throws IOException {
        writeVersions("included.lib", null, "1.0\tnamed\ti.example\tlib\t2024-01-01T00:00:00Z\n");
        writeVersions("excluded.lib", null, "1.0\tnamed\te.example\tlib\t2024-01-01T00:00:00Z\n");

        ListOwners.main(new String[]{"included.*"});

        assertThat(capturedLines())
                .containsExactly("included.lib=i.example");
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
