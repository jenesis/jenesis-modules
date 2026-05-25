package build.jenesis.crawler.test;

import module java.base;
import build.jenesis.crawler.model.ModuleEntry;
import build.jenesis.crawler.model.ModuleType;
import build.jenesis.crawler.model.Version;
import module org.junit.jupiter.api;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ModuleEntryTest {

    private static final long EPOCH_MILLIS = 1_700_000_000_000L;
    private static final String ISO = "2023-11-14T22:13:20Z";

    @Test
    public void absent_module_version_serialises_to_empty_trailing_column() {
        ModuleEntry entry = new ModuleEntry(new Version("1.0"), ModuleType.NAMED, "g", "a", EPOCH_MILLIS, "");

        assertThat(entry.format()).isEqualTo("1.0\tnamed\tg\ta\t" + ISO + "\t");
    }

    @Test
    public void present_module_version_carries_through_unchanged() {
        ModuleEntry entry = new ModuleEntry(new Version("1.0"), ModuleType.NAMED, "g", "a", EPOCH_MILLIS, "1.0.0-SNAPSHOT");

        assertThat(entry.format()).isEqualTo("1.0\tnamed\tg\ta\t" + ISO + "\t1.0.0-SNAPSHOT");
    }

    @Test
    public void rejects_legacy_five_column_row() {
        assertThatThrownBy(() -> ModuleEntry.parse("1.0\tnamed\tg\ta\t" + ISO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Missing fifth tab");
    }

    @Test
    public void parses_six_column_row_with_empty_trailing_as_absent() {
        ModuleEntry entry = ModuleEntry.parse("1.0\tnamed\tg\ta\t" + ISO + "\t");

        assertThat(entry.moduleVersion()).isEmpty();
    }

    @Test
    public void parses_six_column_row_with_value() {
        ModuleEntry entry = ModuleEntry.parse("1.0\tnamed\tg\ta\t" + ISO + "\t2.5");

        assertThat(entry.moduleVersion()).isEqualTo("2.5");
    }

    @Test
    public void round_trips_both_module_version_states() {
        for (String moduleVersion : new String[]{"", "1.0.0"}) {
            ModuleEntry source = new ModuleEntry(new Version("1.0"), ModuleType.NAMED, "g", "a", EPOCH_MILLIS, moduleVersion);
            ModuleEntry parsed = ModuleEntry.parse(source.format());
            assertThat(parsed).isEqualTo(source);
        }
    }

    @Test
    public void rejects_null_module_version() {
        assertThatThrownBy(() -> new ModuleEntry(new Version("1.0"), ModuleType.NAMED, "g", "a", EPOCH_MILLIS, null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("moduleVersion");
    }

    @Test
    public void rejects_row_with_extra_seventh_column() {
        assertThatThrownBy(() -> ModuleEntry.parse("1.0\tnamed\tg\ta\t" + ISO + "\t2.5\textra"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unexpected extra tab");
    }
}
