package build.jenesis.crawler.test;

import module java.base;
import module org.junit.jupiter.api;

import build.jenesis.crawler.SetOwners;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SetOwnersTest {

    @TempDir
    Path root;

    @BeforeEach
    void setUp() {
        System.setProperty(SetOwners.PROP_DATA, root.resolve("data").toString());
    }

    @AfterEach
    void tearDown() {
        System.clearProperty(SetOwners.PROP_DATA);
    }

    @Test
    public void writes_owners_file_and_filters_versions_in_place() throws IOException {
        Path moduleDir = Files.createDirectories(root.resolve("data").resolve("modules")
                .resolve("com").resolve("example").resolve("lib"));
        Files.writeString(moduleDir.resolve("versions.tsv"), """
                2.0\tnamed\tcom.example\tlib\t2024-01-15T10:00:00Z
                1.0\tnamed\tcom.example\tlib\t2023-01-15T10:00:00Z
                0.5\tnamed\thostile.group\timposter\t2025-06-01T12:00:00Z
                """);
        Path props = root.resolve("policy.properties");
        Files.writeString(props, "com.example.lib=com.example:lib\n");

        SetOwners.main(new String[]{props.toString()});

        assertThat(Files.readAllLines(moduleDir.resolve("owners.tsv")))
                .containsExactly("com.example\tlib");
        assertThat(Files.readAllLines(moduleDir.resolve("versions.tsv"))).containsExactly(
                "2.0\tnamed\tcom.example\tlib\t2024-01-15T10:00:00Z",
                "1.0\tnamed\tcom.example\tlib\t2023-01-15T10:00:00Z");
    }

    @Test
    public void group_only_entry_allows_any_artifact_in_group() throws IOException {
        Path moduleDir = Files.createDirectories(root.resolve("data").resolve("modules")
                .resolve("trusted").resolve("module"));
        Files.writeString(moduleDir.resolve("versions.tsv"), """
                1.0\tnamed\ttrusted.org\tcore\t2024-01-01T00:00:00Z
                1.0\tnamed\ttrusted.org\textras\t2024-01-01T00:00:00Z
                1.0\tnamed\tother.org\twhatever\t2024-01-01T00:00:00Z
                """);
        Path props = root.resolve("policy.properties");
        Files.writeString(props, "trusted.module=trusted.org\n");

        SetOwners.main(new String[]{props.toString()});

        assertThat(Files.readAllLines(moduleDir.resolve("owners.tsv")))
                .containsExactly("trusted.org");
        assertThat(Files.readAllLines(moduleDir.resolve("versions.tsv"))).containsExactly(
                "1.0\tnamed\ttrusted.org\tcore\t2024-01-01T00:00:00Z",
                "1.0\tnamed\ttrusted.org\textras\t2024-01-01T00:00:00Z");
    }

    @Test
    public void empty_value_clears_owners_and_drops_versions() throws IOException {
        Path moduleDir = Files.createDirectories(root.resolve("data").resolve("modules")
                .resolve("com").resolve("example").resolve("gone"));
        Files.writeString(moduleDir.resolve("versions.tsv"),
                "1.0\tnamed\tcom.example\tgone\t2024-01-01T00:00:00Z\n");
        Path props = root.resolve("policy.properties");
        Files.writeString(props, "com.example.gone=\n");

        SetOwners.main(new String[]{props.toString()});

        assertThat(moduleDir.resolve("owners.tsv")).exists();
        assertThat(Files.size(moduleDir.resolve("owners.tsv"))).isZero();
        assertThat(moduleDir.resolve("versions.tsv")).doesNotExist();
    }

    @Test
    public void merges_owners_across_multiple_files() throws IOException {
        Path moduleDir = Files.createDirectories(root.resolve("data").resolve("modules")
                .resolve("multi").resolve("module"));
        Files.writeString(moduleDir.resolve("versions.tsv"), """
                1.0\tnamed\ta.example\tcore\t2024-01-01T00:00:00Z
                1.0\tnamed\tb.example\tcore\t2024-01-01T00:00:00Z
                1.0\tnamed\tc.example\tcore\t2024-01-01T00:00:00Z
                """);
        Path first = root.resolve("first.properties");
        Path second = root.resolve("second.properties");
        Files.writeString(first, "multi.module=a.example\n");
        Files.writeString(second, "multi.module=b.example:core\n");

        SetOwners.main(new String[]{first.toString(), second.toString()});

        assertThat(Files.readAllLines(moduleDir.resolve("owners.tsv")))
                .containsExactly("a.example", "b.example\tcore");
        assertThat(Files.readAllLines(moduleDir.resolve("versions.tsv"))).containsExactly(
                "1.0\tnamed\ta.example\tcore\t2024-01-01T00:00:00Z",
                "1.0\tnamed\tb.example\tcore\t2024-01-01T00:00:00Z");
    }

    @Test
    public void filters_versions_with_classifier() throws IOException {
        Path moduleDir = Files.createDirectories(root.resolve("data").resolve("modules")
                .resolve("classy").resolve("module"));
        Files.writeString(moduleDir.resolve("versions.tsv"),
                "1.0\tnamed\tkeeper\tcore\t2024-01-01T00:00:00Z\n");
        Files.writeString(moduleDir.resolve("versions-jakarta.tsv"), """
                1.0\tnamed\tkeeper\tcore\t2024-01-01T00:00:00Z
                1.0\tnamed\tdropper\tcore\t2024-01-01T00:00:00Z
                """);
        Path props = root.resolve("policy.properties");
        Files.writeString(props, "classy.module=keeper\n");

        SetOwners.main(new String[]{props.toString()});

        assertThat(Files.readAllLines(moduleDir.resolve("versions.tsv")))
                .containsExactly("1.0\tnamed\tkeeper\tcore\t2024-01-01T00:00:00Z");
        assertThat(Files.readAllLines(moduleDir.resolve("versions-jakarta.tsv")))
                .containsExactly("1.0\tnamed\tkeeper\tcore\t2024-01-01T00:00:00Z");
    }

    @Test
    public void creates_owners_file_for_module_with_no_existing_versions() throws IOException {
        Path props = root.resolve("policy.properties");
        Files.writeString(props, "future.module=plan.org:lib\n");

        SetOwners.main(new String[]{props.toString()});

        Path ownersFile = root.resolve("data").resolve("modules").resolve("future").resolve("module").resolve("owners.tsv");
        assertThat(Files.readAllLines(ownersFile)).containsExactly("plan.org\tlib");
    }

    @Test
    public void rejects_invalid_module_name() throws IOException {
        Path props = root.resolve("policy.properties");
        Files.writeString(props, "not-a-valid-module=anything\n");

        assertThatThrownBy(() -> SetOwners.main(new String[]{props.toString()}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Invalid module name");
    }

    @Test
    public void rejects_owner_entry_with_two_colons() throws IOException {
        Path props = root.resolve("policy.properties");
        Files.writeString(props, "good.module=foo:bar:baz\n");

        assertThatThrownBy(() -> SetOwners.main(new String[]{props.toString()}))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("more than one colon");
    }
}
