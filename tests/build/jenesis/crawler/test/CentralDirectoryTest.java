package build.jenesis.crawler.test;

import module java.base;
import module org.junit.jupiter.api;

import build.jenesis.crawler.CentralDirectory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CentralDirectoryTest {

    @Test
    public void parses_central_directory_of_modular_jar() throws IOException {
        byte[] jar = Jars.modularJar("test.module.alpha");

        CentralDirectory.Position position = CentralDirectory.locate(jar, jar.length);
        byte[] cdBytes = Arrays.copyOfRange(jar,
                (int) position.centralDirectoryOffset(),
                (int) (position.centralDirectoryOffset() + position.centralDirectorySize()));
        Map<String, CentralDirectory.Entry> entries = CentralDirectory.parse(cdBytes, position.entryCount());

        assertThat(entries).containsKeys("module-info.class", "META-INF/MANIFEST.MF");
        CentralDirectory.Entry moduleInfo = entries.get("module-info.class");
        assertThat(moduleInfo.uncompressedSize()).isPositive();
        assertThat(moduleInfo.compressionMethod()).isIn(0, 8);
    }

    @Test
    public void locates_eocd_when_supplied_tail_starts_within_archive() throws IOException {
        byte[] jar = Jars.modularJar("a.b");
        int tailStart = jar.length - 256;
        byte[] tail = Arrays.copyOfRange(jar, tailStart, jar.length);

        CentralDirectory.Position position = CentralDirectory.locate(tail, jar.length);

        assertThat(position.centralDirectoryOffset()).isPositive();
        assertThat(position.entryCount()).isGreaterThanOrEqualTo(2L);
    }

    @Test
    public void rejects_tail_without_end_of_central_directory_marker() {
        byte[] garbage = new byte[128];
        Arrays.fill(garbage, (byte) 0x42);

        assertThatThrownBy(() -> CentralDirectory.locate(garbage, garbage.length))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("End of central directory");
    }

    @Test
    public void local_header_data_offset_reflects_name_and_extra_field_length() throws IOException {
        byte[] jar = Jars.modularJar("alpha.beta");
        CentralDirectory.Position position = CentralDirectory.locate(jar, jar.length);
        byte[] cdBytes = Arrays.copyOfRange(jar,
                (int) position.centralDirectoryOffset(),
                (int) (position.centralDirectoryOffset() + position.centralDirectorySize()));
        Map<String, CentralDirectory.Entry> entries = CentralDirectory.parse(cdBytes, position.entryCount());
        CentralDirectory.Entry moduleInfo = entries.get("module-info.class");

        int dataOffset = CentralDirectory.localHeaderDataOffset(jar, (int) moduleInfo.localHeaderOffset());

        assertThat(dataOffset).isGreaterThanOrEqualTo(CentralDirectory.LOCAL_HEADER_SIZE + "module-info.class".length());
    }
}
