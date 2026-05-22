/**
 * Jenesis Modules
 *
 * Crawls Maven Central and records the Java module name produced by every
 * modularised artifact, in a form that supports lookup by module name then version.
 *
 * @jenesis.release 25
 * @jenesis.main build.jenesis.modules.Main
 */
module build.jenesis.modules {

    requires java.net.http;

    exports build.jenesis.modules;
}
