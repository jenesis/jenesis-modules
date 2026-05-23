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
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        Manifest manifest = baseManifest(manifestEntries);
        try (JarOutputStream jar = new JarOutputStream(buffer, manifest)) {
            jar.putNextEntry(new ZipEntry("module-info.class"));
            jar.write(buildModuleInfo(moduleName));
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
        return ClassFile.of().buildModule(ModuleAttribute.of(
                ModuleDesc.of(moduleName),
                builder -> builder.requires(ModuleRequireInfo.of(ModuleDesc.of("java.base"), 0, null))));
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
