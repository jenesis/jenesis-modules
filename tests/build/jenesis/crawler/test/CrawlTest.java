package build.jenesis.crawler.test;

import module java.base;
import build.jenesis.crawler.Crawl;
import module org.junit.jupiter.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CrawlTest {

    private static final String NAME = "jenesis.crawler.test.flag";

    @AfterEach
    void tearDown() {
        System.clearProperty(NAME);
    }

    @Test
    void absentFlagKeepsItsDefault() {
        assertThat(Crawl.flag(NAME, true)).isTrue();
        assertThat(Crawl.flag(NAME, false)).isFalse();
    }

    @Test
    void flagNamedWithNoValueIsTrue() {
        System.setProperty(NAME, "");
        assertThat(Crawl.flag(NAME, false)).isTrue();
    }

    @Test
    void flagReadsTrueAndFalse() {
        System.setProperty(NAME, " True ");
        assertThat(Crawl.flag(NAME, false)).isTrue();
        System.setProperty(NAME, "false");
        assertThat(Crawl.flag(NAME, true)).isFalse();
    }

    @Test
    void flagRefusesAnyOtherValue() {
        for (String value : List.of("yes", "no", "1", "0", "on")) {
            System.setProperty(NAME, value);
            assertThatThrownBy(() -> Crawl.flag(NAME, false))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(NAME)
                    .hasMessageContaining(value);
        }
    }
}
