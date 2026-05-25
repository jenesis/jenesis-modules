package build.jenesis.crawler.test;

import module java.base;
import build.jenesis.crawler.store.DirtyModules;
import module org.junit.jupiter.api;


import static org.assertj.core.api.Assertions.assertThat;

public class DirtyModulesTest {

    @TempDir
    Path root;

    @Test
    public void persists_added_names_across_instances() throws IOException {
        DirtyModules first = new DirtyModules(root);
        first.add("com.example.lib");
        first.add("net.bytebuddy");

        DirtyModules second = new DirtyModules(root);
        assertThat(second.snapshot()).containsExactly("com.example.lib", "net.bytebuddy");
    }

    @Test
    public void add_is_idempotent() throws IOException {
        DirtyModules dirty = new DirtyModules(root);
        assertThat(dirty.add("com.example.lib")).isTrue();
        assertThat(dirty.add("com.example.lib")).isFalse();
        assertThat(dirty.size()).isEqualTo(1);
        // Reload to make sure dedup is reflected on disk too.
        DirtyModules reload = new DirtyModules(root);
        assertThat(reload.size()).isEqualTo(1);
    }

    @Test
    public void remove_drops_the_entry_and_persists() throws IOException {
        DirtyModules dirty = new DirtyModules(root);
        dirty.add("a.module");
        dirty.add("b.module");

        dirty.remove("a.module");

        assertThat(dirty.snapshot()).containsExactly("b.module");
        assertThat(new DirtyModules(root).snapshot()).containsExactly("b.module");
    }

    @Test
    public void clear_empties_the_set_and_removes_the_file() throws IOException {
        DirtyModules dirty = new DirtyModules(root);
        dirty.add("a.module");
        dirty.add("b.module");

        dirty.clear();

        assertThat(dirty.isEmpty()).isTrue();
        assertThat(root.resolve(DirtyModules.FILE_NAME)).doesNotExist();
        assertThat(new DirtyModules(root).isEmpty()).isTrue();
    }

    @Test
    public void empty_after_remove_of_last_entry_deletes_file() throws IOException {
        DirtyModules dirty = new DirtyModules(root);
        dirty.add("only.module");
        dirty.remove("only.module");

        assertThat(root.resolve(DirtyModules.FILE_NAME)).doesNotExist();
    }
}
