package build.jenesis.crawler.test;

import module java.base;
import module org.junit.jupiter.api;

import build.jenesis.crawler.RobotsTxt;

import static org.assertj.core.api.Assertions.assertThat;

public class RobotsTxtTest {

    @Test
    public void empty_content_allows_everything() {
        RobotsTxt.Rules rules = RobotsTxt.rulesFor("", "anybot");

        assertThat(rules.allows("/maven2/")).isTrue();
        assertThat(rules.allows("/.index/")).isTrue();
    }

    @Test
    public void wildcard_disallow_blocks_matching_prefix() {
        String content = """
                User-agent: *
                Disallow: /private/
                """;

        RobotsTxt.Rules rules = RobotsTxt.rulesFor(content, "anybot");

        assertThat(rules.allows("/private/")).isFalse();
        assertThat(rules.allows("/private/index")).isFalse();
        assertThat(rules.allows("/public")).isTrue();
    }

    @Test
    public void empty_disallow_means_allow_all() {
        String content = """
                User-agent: *
                Disallow:
                """;

        RobotsTxt.Rules rules = RobotsTxt.rulesFor(content, "anybot");

        assertThat(rules.allows("/anything")).isTrue();
    }

    @Test
    public void allow_overrides_disallow_when_more_specific() {
        String content = """
                User-agent: *
                Disallow: /assets/
                Allow: /assets/public/
                """;

        RobotsTxt.Rules rules = RobotsTxt.rulesFor(content, "anybot");

        assertThat(rules.allows("/assets/private/file")).isFalse();
        assertThat(rules.allows("/assets/public/file")).isTrue();
    }

    @Test
    public void specific_agent_block_overrides_wildcard() {
        String content = """
                User-agent: *
                Disallow: /

                User-agent: jenesismodulescrawler
                Disallow:
                """;

        RobotsTxt.Rules rules = RobotsTxt.rulesFor(content, "jenesismodulescrawler");

        assertThat(rules.allows("/maven2/")).isTrue();
    }

    @Test
    public void wildcard_applies_when_no_specific_match() {
        String content = """
                User-agent: googlebot
                Disallow: /

                User-agent: *
                Disallow: /private/
                """;

        RobotsTxt.Rules rules = RobotsTxt.rulesFor(content, "anybot");

        assertThat(rules.allows("/private/")).isFalse();
        assertThat(rules.allows("/public/")).isTrue();
    }

    @Test
    public void comments_and_blank_lines_are_ignored() {
        String content = """
                # robots.txt for example
                User-agent: *

                Disallow: /tmp/  # block tmp
                """;

        RobotsTxt.Rules rules = RobotsTxt.rulesFor(content, "anybot");

        assertThat(rules.allows("/tmp/file")).isFalse();
        assertThat(rules.allows("/data/file")).isTrue();
    }

    @Test
    public void agent_token_strips_version_and_comment() {
        assertThat(RobotsTxt.agentToken("JenesisModulesCrawler/1.0 (+https://example/)")).isEqualTo("JenesisModulesCrawler");
        assertThat(RobotsTxt.agentToken("bare")).isEqualTo("bare");
    }
}
