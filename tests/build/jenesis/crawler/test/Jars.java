package build.jenesis.crawler.test;

import module java.base;

import java.lang.classfile.ClassFile;
import java.lang.classfile.attribute.ModuleAttribute;
import java.lang.classfile.attribute.ModuleRequireInfo;
import java.lang.constant.ModuleDesc;

public final class Jars {

    private Jars() {
    }

    public static byte[] modularJar(String moduleName) throws IOException {
        return modularJar(moduleName, Map.of());
    }

    public static byte[] modularJar(String moduleName, Map<String, String> manifestEntries) throws IOException {
        return modularJar(moduleName, null, manifestEntries);
    }

    public static byte[] modularJarWithVersion(String moduleName, String moduleVersion) throws IOException {
        return modularJar(moduleName, moduleVersion, Map.of());
    }

    public static byte[] modularJar(String moduleName, String moduleVersion, Map<String, String> manifestEntries) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        Manifest manifest = baseManifest(manifestEntries);
        try (JarOutputStream jar = new JarOutputStream(buffer, manifest)) {
            jar.putNextEntry(new ZipEntry("module-info.class"));
            jar.write(buildModuleInfo(moduleName, moduleVersion));
            jar.closeEntry();
            jar.putNextEntry(new ZipEntry("META-INF/dummy.txt"));
            jar.write("padding\n".getBytes(StandardCharsets.UTF_8));
            jar.closeEntry();
        }
        return buffer.toByteArray();
    }

    public static byte[] multiReleaseModularJar(String moduleName, int javaVersion) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        Manifest manifest = baseManifest(Map.of("Multi-Release", "true"));
        try (JarOutputStream jar = new JarOutputStream(buffer, manifest)) {
            jar.putNextEntry(new ZipEntry("com/example/Placeholder.txt"));
            jar.write("placeholder".getBytes(StandardCharsets.UTF_8));
            jar.closeEntry();
            jar.putNextEntry(new ZipEntry("META-INF/versions/" + javaVersion + "/module-info.class"));
            jar.write(buildModuleInfo(moduleName));
            jar.closeEntry();
        }
        return buffer.toByteArray();
    }

    public static byte[] multiReleaseModularJar(String moduleName, int... javaVersions) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        Manifest manifest = baseManifest(Map.of("Multi-Release", "true"));
        try (JarOutputStream jar = new JarOutputStream(buffer, manifest)) {
            jar.putNextEntry(new ZipEntry("com/example/Placeholder.txt"));
            jar.write("placeholder".getBytes(StandardCharsets.UTF_8));
            jar.closeEntry();
            for (int version : javaVersions) {
                jar.putNextEntry(new ZipEntry("META-INF/versions/" + version + "/module-info.class"));
                jar.write(buildModuleInfo(moduleName));
                jar.closeEntry();
            }
        }
        return buffer.toByteArray();
    }

    public static byte[] rootAndVersionedModularJar(String rootModuleName, String versionedModuleName, int javaVersion) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        Manifest manifest = baseManifest(Map.of("Multi-Release", "true"));
        try (JarOutputStream jar = new JarOutputStream(buffer, manifest)) {
            jar.putNextEntry(new ZipEntry("module-info.class"));
            jar.write(buildModuleInfo(rootModuleName));
            jar.closeEntry();
            jar.putNextEntry(new ZipEntry("META-INF/versions/" + javaVersion + "/module-info.class"));
            jar.write(buildModuleInfo(versionedModuleName));
            jar.closeEntry();
        }
        return buffer.toByteArray();
    }

    public static byte[] automaticJar(String automaticModuleName) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        Manifest manifest = baseManifest(Map.of("Automatic-Module-Name", automaticModuleName));
        try (JarOutputStream jar = new JarOutputStream(buffer, manifest)) {
            jar.putNextEntry(new ZipEntry("com/example/Placeholder.txt"));
            jar.write("placeholder content".getBytes(StandardCharsets.UTF_8));
            jar.closeEntry();
        }
        return buffer.toByteArray();
    }

    public static byte[] selfExtractingModularJar(String moduleName, int prefixSize) throws IOException {
        byte[] jar = modularJar(moduleName);
        byte[] combined = new byte[prefixSize + jar.length];
        for (int index = 0; index < prefixSize; index++) {
            combined[index] = (byte) (index & 0xFF);
        }
        System.arraycopy(jar, 0, combined, prefixSize, jar.length);
        return combined;
    }

    public static byte[] jarWithLongArchiveComment(String moduleName, int commentLength) throws IOException {
        byte[] jar = modularJar(moduleName);
        if (commentLength < 0 || commentLength > 0xFFFF) {
            throw new IllegalArgumentException("commentLength must fit in unsigned short: " + commentLength);
        }
        int eocdOffset = findEocd(jar);
        if (eocdOffset < 0) {
            throw new IllegalStateException("EOCD not found in synthesized jar");
        }
        int commentLengthOffset = eocdOffset + 20;
        if (jar[commentLengthOffset] != 0 || jar[commentLengthOffset + 1] != 0) {
            throw new IllegalStateException("Synthesized jar already has an archive comment");
        }
        byte[] extended = Arrays.copyOf(jar, jar.length + commentLength);
        extended[commentLengthOffset] = (byte) (commentLength & 0xFF);
        extended[commentLengthOffset + 1] = (byte) ((commentLength >>> 8) & 0xFF);
        for (int index = 0; index < commentLength; index++) {
            extended[jar.length + index] = (byte) ('#');
        }
        return extended;
    }

    private static int findEocd(byte[] bytes) {
        for (int candidate = bytes.length - 22; candidate >= 0; candidate--) {
            if (bytes[candidate] == 0x50
                    && bytes[candidate + 1] == 0x4b
                    && bytes[candidate + 2] == 0x05
                    && bytes[candidate + 3] == 0x06) {
                return candidate;
            }
        }
        return -1;
    }

    public static byte[] plainJar() throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        Manifest manifest = baseManifest(Map.of());
        try (JarOutputStream jar = new JarOutputStream(buffer, manifest)) {
            jar.putNextEntry(new ZipEntry("com/example/SomeClass.class"));
            jar.write(new byte[]{(byte) 0xCA, (byte) 0xFE, (byte) 0xBA, (byte) 0xBE});
            jar.closeEntry();
        }
        return buffer.toByteArray();
    }

    public static byte[] buildModuleInfo(String moduleName) {
        return buildModuleInfo(moduleName, null);
    }

    public static byte[] buildModuleInfo(String moduleName, String moduleVersion) {
        return ClassFile.of().buildModule(ModuleAttribute.of(
                ModuleDesc.of(moduleName),
                builder -> {
                    builder.requires(ModuleRequireInfo.of(ModuleDesc.of("java.base"), 0, null));
                    if (moduleVersion != null) {
                        builder.moduleVersion(moduleVersion);
                    }
                }));
    }

    private static Manifest baseManifest(Map<String, String> additional) {
        Manifest manifest = new Manifest();
        java.util.jar.Attributes attributes = manifest.getMainAttributes();
        attributes.put(java.util.jar.Attributes.Name.MANIFEST_VERSION, "1.0");
        for (Map.Entry<String, String> entry : additional.entrySet()) {
            attributes.put(new java.util.jar.Attributes.Name(entry.getKey()), entry.getValue());
        }
        return manifest;
    }
}
