package build.jenesis.crawler.model;

public enum ModuleType {

    NAMED("named"),
    AUTOMATIC("automatic");

    private final String label;

    ModuleType(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }

    public static ModuleType fromLabel(String label) {
        for (ModuleType type : values()) {
            if (type.label.equals(label)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown module type label: " + label);
    }
}
