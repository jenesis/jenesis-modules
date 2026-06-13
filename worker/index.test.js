/**
 * Tests for the Cloudflare Worker in index.js.
 *
 * The worker is exercised end-to-end through its `fetch(request, env)` handler - the same
 * entry point Cloudflare invokes - so routing, TSV parsing, row selection, redirect-URL
 * construction and error mapping are all covered by real requests rather than by reaching
 * into private helpers. The only thing stubbed is the global `fetch` the worker uses to pull
 * `*.tsv` files from `DATA_BASE`: each test supplies the TSV bodies it needs keyed by the
 * path suffix the worker requests.
 *
 * Runs on the Node built-in test runner with no dependencies: `node --test` (Node 20+).
 * `Request`, `Response`, `URL` and `fetch` are Web-standard globals in Node 18+, matching
 * the workerd runtime closely enough for these resolution tests.
 */
import { test } from "node:test";
import assert from "node:assert/strict";

import worker from "./index.js";

const DATA_BASE = "https://data.test/";
const ARTIFACT_BASE = "https://maven.test/";
const ENV = { DATA_BASE, ARTIFACT_BASE };

const tsv = (rows) => rows.map((cols) => cols.join("\t")).join("\n") + "\n";

// org.slf4j: a plain, named module with both an artifacts and a modules view.
const SLF4J_ARTIFACTS = tsv([
    ["2.0.10", "named", "org.slf4j", "slf4j-api"],
    ["2.0.9", "named", "org.slf4j", "slf4j-api"],
    ["1.7.36", "named", "org.slf4j", "slf4j-api"],
]);
const SLF4J_MODULES = tsv([
    ["2.0.10", "org.slf4j", "slf4j-api", "2.0.10"],
    ["2.0.9", "org.slf4j", "slf4j-api", "2.0.9"],
]);
// A classifier-scoped artifacts view (jackson's no_aopalliance variant).
const JACKSON_NOAOP = tsv([
    ["2.17.0", "named", "com.fasterxml.jackson.core", "jackson-core"],
]);

const FIXTURES = {
    "org/slf4j/artifacts.tsv": SLF4J_ARTIFACTS,
    "org/slf4j/modules.tsv": SLF4J_MODULES,
    "com/fasterxml/jackson/core/artifacts-no_aopalliance.tsv": JACKSON_NOAOP,
};

/**
 * Drive the worker for one request. `files` maps a TSV path suffix to either a string body
 * (served as 200) or `{ status, body }` to simulate upstream errors / 404s. Falls back to
 * FIXTURES, then to a 404, so most tests only declare what differs.
 */
async function call(path, { files = {}, env = ENV, method = "GET" } = {}) {
    const saved = globalThis.fetch;
    globalThis.fetch = async (url) => {
        const u = String(url);
        const table = { ...FIXTURES, ...files };
        for (const [suffix, value] of Object.entries(table)) {
            if (u.endsWith(suffix)) {
                if (typeof value === "string") {
                    return new Response(value, { status: 200 });
                }
                return new Response(value.body ?? "", { status: value.status });
            }
        }
        return new Response("not found", { status: 404 });
    };
    try {
        const request = new Request("https://worker.test" + path, { method });
        return await worker.fetch(request, env);
    } finally {
        globalThis.fetch = saved;
    }
}

test("root path redirects to the default home", async () => {
    const response = await call("/", { env: {} });
    assert.equal(response.status, 302);
    assert.equal(response.headers.get("Location"), "https://github.com/raphw/jenesis-modules");
});

test("root path honours HOME_REDIRECT override", async () => {
    const response = await call("/", { env: { HOME_REDIRECT: "https://example.org/home" } });
    assert.equal(response.status, 302);
    assert.equal(response.headers.get("Location"), "https://example.org/home");
});

test("non-GET/HEAD methods are rejected with 405 and an Allow header", async () => {
    const response = await call("/artifact/org.slf4j/org.slf4j.jar", { method: "POST" });
    assert.equal(response.status, 405);
    assert.equal(response.headers.get("Allow"), "GET, HEAD");
});

test("HEAD is allowed and resolves like GET", async () => {
    const response = await call("/artifact/org.slf4j/org.slf4j.jar", { method: "HEAD" });
    assert.equal(response.status, 302);
    assert.equal(
        response.headers.get("Location"),
        "https://maven.test/org/slf4j/slf4j-api/2.0.10/slf4j-api-2.0.10.jar",
    );
});

test("a path with too few segments is a 404", async () => {
    const response = await call("/artifact/org.slf4j");
    assert.equal(response.status, 404);
});

test("artifact mode without a version redirects to the newest Maven coordinate", async () => {
    const response = await call("/artifact/org.slf4j/org.slf4j.jar");
    assert.equal(response.status, 302);
    assert.equal(
        response.headers.get("Location"),
        "https://maven.test/org/slf4j/slf4j-api/2.0.10/slf4j-api-2.0.10.jar",
    );
    assert.equal(response.headers.get("X-Jenesis-GroupId"), "org.slf4j");
    assert.equal(response.headers.get("X-Jenesis-ArtifactId"), "slf4j-api");
    assert.equal(response.headers.get("X-Jenesis-MavenVersion"), "2.0.10");
});

test("artifact mode resolves an explicit Maven version present in the TSV", async () => {
    const response = await call("/artifact/org.slf4j/2.0.9/org.slf4j.jar");
    assert.equal(response.status, 302);
    assert.equal(
        response.headers.get("Location"),
        "https://maven.test/org/slf4j/slf4j-api/2.0.9/slf4j-api-2.0.9.jar",
    );
});

test("artifact mode passes the request extension through verbatim (.pom)", async () => {
    const response = await call("/artifact/org.slf4j/2.0.9/org.slf4j.pom");
    assert.equal(response.status, 302);
    assert.equal(
        response.headers.get("Location"),
        "https://maven.test/org/slf4j/slf4j-api/2.0.9/slf4j-api-2.0.9.pom",
    );
});

test("artifact mode passes a multi-dot extension through (.pom.sha256)", async () => {
    const response = await call("/artifact/org.slf4j/2.0.9/org.slf4j.pom.sha256");
    assert.equal(response.status, 302);
    assert.equal(
        response.headers.get("Location"),
        "https://maven.test/org/slf4j/slf4j-api/2.0.9/slf4j-api-2.0.9.pom.sha256",
    );
});

test("a classifier flips the lookup to the classifier-scoped TSV and decorates the filename", async () => {
    const response = await call(
        "/artifact/com.fasterxml.jackson.core/com.fasterxml.jackson.core-no_aopalliance.pom",
    );
    assert.equal(response.status, 302);
    assert.equal(
        response.headers.get("Location"),
        "https://maven.test/com/fasterxml/jackson/core/jackson-core/2.17.0/jackson-core-2.17.0-no_aopalliance.pom",
    );
});

test("module mode resolves via modules.tsv (module-info version keys the row)", async () => {
    const response = await call("/module/org.slf4j/2.0.9/org.slf4j.jar");
    assert.equal(response.status, 302);
    assert.equal(
        response.headers.get("Location"),
        "https://maven.test/org/slf4j/slf4j-api/2.0.9/slf4j-api-2.0.9.jar",
    );
    assert.equal(response.headers.get("X-Jenesis-ModuleVersion"), "2.0.9");
    assert.equal(response.headers.get("X-Jenesis-MavenVersion"), "2.0.9");
});

test("module mode only accepts a .jar filename", async () => {
    const response = await call("/module/org.slf4j/org.slf4j.pom");
    assert.equal(response.status, 404);
});

test("sources mode synthesises the -sources.jar URL", async () => {
    const response = await call("/sources/org.slf4j/2.0.9/org.slf4j.jar");
    assert.equal(response.status, 302);
    assert.equal(
        response.headers.get("Location"),
        "https://maven.test/org/slf4j/slf4j-api/2.0.9/slf4j-api-2.0.9-sources.jar",
    );
});

test("documentation mode synthesises the -javadoc.jar URL", async () => {
    const response = await call("/documentation/org.slf4j/2.0.9/org.slf4j.jar");
    assert.equal(response.status, 302);
    assert.equal(
        response.headers.get("Location"),
        "https://maven.test/org/slf4j/slf4j-api/2.0.9/slf4j-api-2.0.9-javadoc.jar",
    );
});

test("an unknown explicit version resolves best-effort against the newest coordinate", async () => {
    const response = await call("/artifact/org.slf4j/9.9.9/org.slf4j.jar");
    assert.equal(response.status, 302);
    assert.equal(
        response.headers.get("Location"),
        "https://maven.test/org/slf4j/slf4j-api/9.9.9/slf4j-api-9.9.9.jar",
    );
    assert.equal(response.headers.get("X-Jenesis-BestEffort"), "true");
});

test("an unknown module (TSV 404 upstream) returns 404", async () => {
    const response = await call("/artifact/org.unknown/org.unknown.jar", {
        files: { "org/unknown/artifacts.tsv": { status: 404 } },
    });
    assert.equal(response.status, 404);
});

test("an empty TSV with no rows returns 404", async () => {
    const response = await call("/artifact/org.empty/org.empty.jar", {
        files: { "org/empty/artifacts.tsv": "" },
    });
    assert.equal(response.status, 404);
});

test("a non-404 upstream error maps to 502", async () => {
    const response = await call("/artifact/org.slf4j/org.slf4j.jar", {
        files: { "org/slf4j/artifacts.tsv": { status: 500 } },
    });
    assert.equal(response.status, 502);
});

test("path segments before the mode marker are ignored", async () => {
    const response = await call("/jenesis/v1/artifact/org.slf4j/org.slf4j.jar");
    assert.equal(response.status, 302);
    assert.equal(
        response.headers.get("Location"),
        "https://maven.test/org/slf4j/slf4j-api/2.0.10/slf4j-api-2.0.10.jar",
    );
});

test("the module-name segment must prefix the filename", async () => {
    const response = await call("/artifact/org.slf4j/com.other.jar");
    assert.equal(response.status, 404);
});

test("an invalid module name (hyphen in a segment) is a 404", async () => {
    const response = await call("/artifact/foo-bar/foo-bar.jar");
    assert.equal(response.status, 404);
});

test("an unknown mode marker is a 404", async () => {
    const response = await call("/bogus/org.slf4j/org.slf4j.jar");
    assert.equal(response.status, 404);
});
