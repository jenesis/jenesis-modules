package build.jenesis.crawler.test;

import module java.base;
import build.jenesis.crawler.index.IndexReader;
import build.jenesis.crawler.model.Coordinate;
import module org.junit.jupiter.api;


import static org.assertj.core.api.Assertions.assertThat;

public class IndexReaderTest {

    private static final int FLAG_STORED = 0x04;
    private static final int FLAG_COMPRESSED = 0x08;

    @Test
    public void reads_header_and_returns_null_at_eof() throws IOException {
        byte[] indexBytes = writeIndex(1747700000L, List.of());

        try (IndexReader reader = new IndexReader(new ByteArrayInputStream(indexBytes))) {
            assertThat(reader.version()).isEqualTo(1);
            assertThat(reader.timestamp()).isEqualTo(1747700000L);
            assertThat(reader.nextRecord()).isNull();
        }
    }

    @Test
    public void reads_stored_fields_as_utf8_strings() throws IOException {
        byte[] indexBytes = writeIndex(0L, List.of(
                ordered(Map.entry("u", "org.example|widget|1.0|NA|jar"),
                        Map.entry("i", "jar|1700000000000|2048|0|0|0|jar"),
                        Map.entry("1", "deadbeef"))));

        try (IndexReader reader = new IndexReader(new ByteArrayInputStream(indexBytes))) {
            Map<String, String> record = reader.nextRecord();
            assertThat(record).containsEntry("u", "org.example|widget|1.0|NA|jar");
            assertThat(record).containsEntry("i", "jar|1700000000000|2048|0|0|0|jar");
            assertThat(record).containsEntry("1", "deadbeef");
            assertThat(reader.nextRecord()).isNull();
        }
    }

    @Test
    public void decompresses_compressed_field_values() throws IOException {
        byte[] payload = "some.long.compressed.value".getBytes(StandardCharsets.UTF_8);
        byte[] gzipped = gzip(payload);

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(buffer);
        out.writeByte(1);
        out.writeLong(0L);
        out.writeInt(1);
        out.writeByte(FLAG_STORED | FLAG_COMPRESSED);
        out.writeUTF("D");
        out.writeInt(gzipped.length);
        out.write(gzipped);

        try (IndexReader reader = new IndexReader(new ByteArrayInputStream(buffer.toByteArray()))) {
            Map<String, String> record = reader.nextRecord();
            assertThat(record).containsEntry("D", "some.long.compressed.value");
        }
    }

    @Test
    public void coordinate_extraction_skips_descriptor_records() {
        Map<String, String> descriptor = Map.of("DESCRIPTOR", "NexusIndex");

        assertThat(Coordinate.from(descriptor)).isEmpty();
    }

    @Test
    public void coordinate_extraction_parses_uinfo_and_info() {
        Map<String, String> record = Map.of(
                "u", "org.example|widget|2.1|NA|jar",
                "i", "jar|1700000000000|4096|0|0|0|jar"
        );

        Optional<Coordinate> coordinate = Coordinate.from(record);

        assertThat(coordinate).isPresent();
        Coordinate value = coordinate.get();
        assertThat(value.groupId()).isEqualTo("org.example");
        assertThat(value.artifactId()).isEqualTo("widget");
        assertThat(value.version()).isEqualTo("2.1");
        assertThat(value.classifier()).isNull();
        assertThat(value.extension()).isEqualTo("jar");
        assertThat(value.size()).isEqualTo(4096L);
        assertThat(value.lastModified()).isEqualTo(1700000000000L);
    }

    @Test
    public void coordinate_extraction_keeps_classifier_when_present() {
        Map<String, String> record = Map.of(
                "u", "org.example|widget|2.1|jakarta|jar",
                "i", "jar|0|0|0|0|0|jar"
        );

        Coordinate coordinate = Coordinate.from(record).orElseThrow();

        assertThat(coordinate.classifier()).isEqualTo("jakarta");
    }

    @Test
    public void coordinate_extraction_rewrites_miscategorised_no_classifier_extensions_to_jar() {
        // Nexus indexer bug: a real main-jar record gets stamped with the extension of a
        // classifier-less sidecar the indexer processed last (Gradle .module, POM checksums,
        // GPG signatures, SPDX/CycloneDX SBOMs, sigstore bundles, and whatever format is invented
        // next). Rather than chase that open-ended family, the parser rewrites ANY classifier-less
        // extension that is not a real primary packaging to "jar" (the allowlist inversion): this
        // crawler catalogs modules, a non-jar is never a module, so trying it as a jar can only
        // find modules and at worst 404s once on a genuine exotic non-jar. Includes a made-up
        // "future.sbom.format" to prove new maskers need no code change.
        for (String miscategorised : new String[]{
                "module", "pom.sha256", "pom.sha512", "pom.asc.sha256", "pom.asc.sha512",
                "spdx.json", "spdx.rdf.xml", "spdx.xml", "cyclonedx.json", "cyclonedx.xml",
                "jar.asc", "zip.sha512", "tar.gz.sha512", "toml.sha512",
                "pom.md5.asc.sha512", "pom.sigstore.json.sha512", "sha512", "md5",
                "future.sbom.format", "attestation.intoto.jsonl", "vex"}) {
            Map<String, String> record = Map.of(
                    "u", "net.bytebuddy|byte-buddy|1.10.0|NA|" + miscategorised,
                    "i", miscategorised + "|0|0|0|0|0|" + miscategorised
            );
            Coordinate coordinate = Coordinate.from(record).orElseThrow();
            assertThat(coordinate.extension())
                    .as("extension=%s with no classifier should be rewritten to jar", miscategorised)
                    .isEqualTo("jar");
            assertThat(coordinate.classifier()).isNull();
        }
    }

    @Test
    public void coordinate_extraction_keeps_real_no_classifier_packagings() {
        // The inversion's other half: a genuine primary packaging on a classifier-less record is
        // kept as-is, never rewritten to jar (so pom-only BOMs, wars, zips, native artifacts do
        // not each trigger a wasted jar fetch/404).
        for (String packaging : new String[]{
                "jar", "pom", "war", "ear", "aar", "zip", "tar.gz", "nar", "so", "hpi", "esa"}) {
            Map<String, String> record = Map.of(
                    "u", "org.example|thing|1.0|NA|" + packaging,
                    "i", packaging + "|0|0|0|0|0|" + packaging
            );
            Coordinate coordinate = Coordinate.from(record).orElseThrow();
            assertThat(coordinate.extension())
                    .as("real packaging %s must be preserved", packaging)
                    .isEqualTo(packaging);
        }
    }

    @Test
    public void coordinate_extraction_keeps_classifier_records_with_unusual_extensions() {
        // Sidecar artifacts (sources/javadoc) with checksum-style extensions are NOT mis-
        // categorised - they're independent records the indexer emits in addition to the .jar
        // record for that same classifier. Crawler.SKIPPED_CLASSIFIERS filters them out
        // downstream; the parser must leave them untouched so that filter still sees them.
        Map<String, String> record = Map.of(
                "u", "net.bytebuddy|byte-buddy|1.10.0|javadoc|jar.asc.sha512",
                "i", "jar.asc.sha512|0|0|0|0|0|jar.asc.sha512"
        );
        Coordinate coordinate = Coordinate.from(record).orElseThrow();
        assertThat(coordinate.classifier()).isEqualTo("javadoc");
        assertThat(coordinate.extension()).isEqualTo("jar.asc.sha512");
    }

    @Test
    public void coordinate_extraction_builds_maven_path() {
        Coordinate coordinate = new Coordinate("org.example", "widget", "2.1", null, "jar", 0L, 0L);

        assertThat(coordinate.mavenPath()).isEqualTo("org/example/widget/2.1/widget-2.1.jar");
    }

    @Test
    public void coordinate_extraction_builds_classified_maven_path() {
        Coordinate coordinate = new Coordinate("org.example", "widget", "2.1", "jakarta", "jar", 0L, 0L);

        assertThat(coordinate.mavenPath()).isEqualTo("org/example/widget/2.1/widget-2.1-jakarta.jar");
    }

    private static byte[] writeIndex(long timestamp, List<List<Map.Entry<String, String>>> records) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(buffer);
        out.writeByte(1);
        out.writeLong(timestamp);
        for (List<Map.Entry<String, String>> record : records) {
            out.writeInt(record.size());
            for (Map.Entry<String, String> entry : record) {
                out.writeByte(FLAG_STORED);
                out.writeUTF(entry.getKey());
                byte[] valueBytes = entry.getValue().getBytes(StandardCharsets.UTF_8);
                out.writeInt(valueBytes.length);
                out.write(valueBytes);
            }
        }
        return buffer.toByteArray();
    }

    @SafeVarargs
    private static List<Map.Entry<String, String>> ordered(Map.Entry<String, String>... entries) {
        return List.of(entries);
    }

    private static byte[] gzip(byte[] data) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (GZIPOutputStream gz = new GZIPOutputStream(out)) {
            gz.write(data);
        }
        return out.toByteArray();
    }
}
