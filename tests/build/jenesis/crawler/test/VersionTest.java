package build.jenesis.crawler.test;

import module java.base;
import build.jenesis.crawler.model.Version;
import module org.junit.jupiter.api;


import static org.assertj.core.api.Assertions.assertThat;

public class VersionTest {

    @Test
    public void compares_numeric_components() {
        assertThat(new Version("1.2.10")).isGreaterThan(new Version("1.2.9"));
        assertThat(new Version("2.0")).isGreaterThan(new Version("1.99"));
    }

    @Test
    public void treats_trailing_zero_as_equal() {
        assertThat(new Version("1.0")).isEqualByComparingTo(new Version("1"));
        assertThat(new Version("1.0.0")).isEqualByComparingTo(new Version("1"));
    }

    @Test
    public void treats_ga_final_release_as_release_equivalents() {
        assertThat(new Version("1.0-ga")).isEqualByComparingTo(new Version("1.0"));
        assertThat(new Version("1.0-final")).isEqualByComparingTo(new Version("1.0"));
        assertThat(new Version("1.0-release")).isEqualByComparingTo(new Version("1.0"));
    }

    @Test
    public void orders_qualifiers_below_release() {
        assertThat(new Version("1.0-SNAPSHOT")).isLessThan(new Version("1.0"));
        assertThat(new Version("1.0-RC1")).isLessThan(new Version("1.0"));
        assertThat(new Version("1.0-alpha")).isLessThan(new Version("1.0-beta"));
        assertThat(new Version("1.0-beta")).isLessThan(new Version("1.0-rc"));
        assertThat(new Version("1.0-rc")).isLessThan(new Version("1.0-snapshot"));
    }

    @Test
    public void treats_cr_alias_for_rc() {
        assertThat(new Version("1.0-cr1")).isEqualByComparingTo(new Version("1.0-rc1"));
    }

    @Test
    public void expands_single_letter_milestone_alpha_beta_shorthand() {
        // Maven shorthand: a/b/m followed by a digit means alpha/beta/milestone.
        assertThat(new Version("6.1.0-M1")).isLessThan(new Version("6.1.0"));
        assertThat(new Version("1.0-a1")).isLessThan(new Version("1.0"));
        assertThat(new Version("1.0-b1")).isLessThan(new Version("1.0"));
        assertThat(new Version("1.0-M1")).isEqualByComparingTo(new Version("1.0-milestone1"));
        assertThat(new Version("6.1.0-M1")).isLessThan(new Version("6.1.0-RC1"));
        assertThat(new Version("1.0-M2")).isGreaterThan(new Version("1.0-M1"));
    }

    @Test
    public void leaves_unknown_qualifiers_above_release() {
        // A lone letter (no trailing digit) is not the shorthand and stays unknown.
        assertThat(new Version("1.0-m")).isGreaterThan(new Version("1.0"));
    }

    @Test
    public void compares_qualifier_subversions_numerically() {
        assertThat(new Version("1.0-RC2")).isGreaterThan(new Version("1.0-RC1"));
        assertThat(new Version("1.0-RC10")).isGreaterThan(new Version("1.0-RC2"));
    }

    @Test
    public void treats_case_insensitively() {
        assertThat(new Version("1.0-SNAPSHOT")).isEqualByComparingTo(new Version("1.0-snapshot"));
    }

    @Test
    public void sorts_descending_with_reverse_order() {
        List<Version> versions = new ArrayList<>(List.of(
                new Version("1.2.0"),
                new Version("2.0.0"),
                new Version("1.0.0"),
                new Version("2.0.0-SNAPSHOT"),
                new Version("1.5.3")));

        versions.sort(Comparator.reverseOrder());

        assertThat(versions).extracting(Version::raw).containsExactly(
                "2.0.0",
                "2.0.0-SNAPSHOT",
                "1.5.3",
                "1.2.0",
                "1.0.0");
    }

    @Test
    public void release_equivalents_have_consistent_hash() {
        assertThat(new Version("1.0-ga").hashCode()).isEqualTo(new Version("1.0").hashCode());
        assertThat(new Version("1.0.0").hashCode()).isEqualTo(new Version("1").hashCode());
    }
}
