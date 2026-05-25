package build.jenesis.crawler.model;

import module java.base;

/**
 * Result of scanning a JAR for module-info metadata. The {@code moduleVersion}
 * carries the raw, unparsed version string from {@code ModuleDescriptor.rawVersion()}
 * for named modules whose module-info declared a version; it is {@code null} for
 * named modules whose module-info declared no version and for automatic modules
 * (which have no module-info at all).
 */
public record ScannedModule(String name, ModuleType type, String moduleVersion) {

    public ScannedModule {
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(type, "type");
    }

    public ScannedModule(String name, ModuleType type) {
        this(name, type, null);
    }
}
