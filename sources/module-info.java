/**
 * Jenesis Modules Crawler
 *
 * Crawls Maven Central and records the Java module name produced by every
 * modularised artifact, in a form that supports lookup by module name then version.
 *
 * @jenesis.release 25
 * @jenesis.main build.jenesis.crawler.Crawl
 */
module build.jenesis.crawler {

    requires java.net.http;

    exports build.jenesis.crawler;
    exports build.jenesis.crawler.fetch;
    exports build.jenesis.crawler.index;
    exports build.jenesis.crawler.store;
    exports build.jenesis.crawler.model;
    exports build.jenesis.crawler.publish;
}
