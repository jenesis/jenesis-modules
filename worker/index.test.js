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

// A module whose newest publishes are pre-releases, and whose modules view declares a
// module-info version without the qualifier its Maven version carries.
const ALPHA_ARTIFACTS = tsv([
    ["2.1.0-alpha1", "named", "org.alpha", "alpha-api"],
    ["2.1.0-alpha0", "named", "org.alpha", "alpha-api"],
    ["2.0.19", "named", "org.alpha", "alpha-api"],
]);
const ALPHA_MODULES = tsv([
    ["2.1.0", "org.alpha", "alpha-api", "2.1.0-alpha1"],
    ["2.0.19", "org.alpha", "alpha-api", "2.0.19"],
]);
// A module that has never published anything but pre-releases.
const UNRELEASED_ARTIFACTS = tsv([
    ["1.0-alpha2", "named", "org.unreleased", "unreleased"],
    ["1.0-alpha1", "named", "org.unreleased", "unreleased"],
]);

const FIXTURES = {
    "org/slf4j/artifacts.tsv": SLF4J_ARTIFACTS,
    "org/slf4j/modules.tsv": SLF4J_MODULES,
    "com/fasterxml/jackson/core/artifacts-no_aopalliance.tsv": JACKSON_NOAOP,
    "org/alpha/artifacts.tsv": ALPHA_ARTIFACTS,
    "org/alpha/modules.tsv": ALPHA_MODULES,
    "org/unreleased/artifacts.tsv": UNRELEASED_ARTIFACTS,
};

// An artifacts view of `org.sample` listing the given versions, newest first.
const sampleVersions = (...rows) =>
    ({ "org/sample/artifacts.tsv": tsv(rows.map((version) => [version, "named", "org.sample", "sample"])) });

/**
 * Drive the worker for one request. `files` maps a TSV path suffix to either a string body
 * (served as 200) or `{ status, body }` to simulate upstream errors / 404s. Falls back to
 * FIXTURES, then to a 404, so most tests only declare what differs. `headers` are request
 * headers, which is how a caller opts in to pre-releases.
 */
async function call(path, { files = {}, env = ENV, method = "GET", headers = {} } = {}) {
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
        const request = new Request("https://worker.test" + path, { method, headers });
        return await worker.fetch(request, env);
    } finally {
        globalThis.fetch = saved;
    }
}

test("root path redirects to the default home", async () => {
    const response = await call("/", { env: {} });
    assert.equal(response.status, 302);
    assert.equal(response.headers.get("Location"), "https://github.com/jenesis/jenesis-modules");
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
    assert.equal(response.headers.get("Jenesis-GroupId"), "org.slf4j");
    assert.equal(response.headers.get("Jenesis-ArtifactId"), "slf4j-api");
    assert.equal(response.headers.get("Jenesis-MavenVersion"), "2.0.10");
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
    assert.equal(response.headers.get("Jenesis-ModuleVersion"), "2.0.9");
    assert.equal(response.headers.get("Jenesis-MavenVersion"), "2.0.9");
});

test("module mode only accepts a .jar filename", async () => {
    const response = await call("/module/org.slf4j/org.slf4j.pom");
    assert.equal(response.status, 404);
});

test("module mode serves the detached signature beside the jar", async () => {
    const response = await call("/module/org.slf4j/2.0.9/org.slf4j.jar.asc");
    assert.equal(response.status, 302);
    assert.equal(
        response.headers.get("Location"),
        "https://maven.test/org/slf4j/slf4j-api/2.0.9/slf4j-api-2.0.9.jar.asc",
    );
});

test("module mode still rejects a sidecar it does not serve", async () => {
    const response = await call("/module/org.slf4j/2.0.9/org.slf4j.jar.sha256");
    assert.equal(response.status, 404);
});

test("sources mode signs the -sources jar, not the plain one", async () => {
    const response = await call("/sources/org.slf4j/2.0.9/org.slf4j.jar.asc");
    assert.equal(response.status, 302);
    assert.equal(
        response.headers.get("Location"),
        "https://maven.test/org/slf4j/slf4j-api/2.0.9/slf4j-api-2.0.9-sources.jar.asc",
    );
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
    assert.equal(response.headers.get("Jenesis-BestEffort"), "true");
});

test("without a version the newest pre-release is skipped", async () => {
    const response = await call("/artifact/org.alpha/org.alpha.jar");
    assert.equal(response.status, 302);
    assert.equal(
        response.headers.get("Location"),
        "https://maven.test/org/alpha/alpha-api/2.0.19/alpha-api-2.0.19.jar",
    );
    assert.equal(response.headers.get("Jenesis-Prerelease"), null);
});

test("module mode skips a row whose Maven version is a pre-release, clean module version notwithstanding", async () => {
    const response = await call("/module/org.alpha/org.alpha.jar");
    assert.equal(response.status, 302);
    assert.equal(response.headers.get("Jenesis-ModuleVersion"), "2.0.19");
    assert.equal(response.headers.get("Jenesis-MavenVersion"), "2.0.19");
});

test("a pre-release named explicitly is served and flagged", async () => {
    const response = await call("/artifact/org.alpha/2.1.0-alpha1/org.alpha.jar");
    assert.equal(response.status, 302);
    assert.equal(
        response.headers.get("Location"),
        "https://maven.test/org/alpha/alpha-api/2.1.0-alpha1/alpha-api-2.1.0-alpha1.jar",
    );
    assert.equal(response.headers.get("Jenesis-Prerelease"), "true");
});

test("a module with nothing but pre-releases is a 404 that names the header", async () => {
    const response = await call("/artifact/org.unreleased/org.unreleased.jar");
    assert.equal(response.status, 404);
    assert.match(await response.text(), /Jenesis-Prerelease: true/);
    assert.equal(response.headers.get("Vary"), "Jenesis-Prerelease");
});

test("the opt-in header serves the newest pre-release of an otherwise unresolvable module", async () => {
    const response = await call("/artifact/org.unreleased/org.unreleased.jar", {
        headers: { "Jenesis-Prerelease": "true" },
    });
    assert.equal(response.status, 302);
    assert.equal(
        response.headers.get("Location"),
        "https://maven.test/org/unreleased/unreleased/1.0-alpha2/unreleased-1.0-alpha2.jar",
    );
    assert.equal(response.headers.get("Jenesis-Prerelease"), "true");
});

test("the opt-in header restores the plain newest row", async () => {
    const response = await call("/artifact/org.alpha/org.alpha.jar", {
        headers: { "Jenesis-Prerelease": "TRUE " },
    });
    assert.equal(response.status, 302);
    assert.equal(
        response.headers.get("Location"),
        "https://maven.test/org/alpha/alpha-api/2.1.0-alpha1/alpha-api-2.1.0-alpha1.jar",
    );
    assert.equal(response.headers.get("Jenesis-Prerelease"), "true");
});

test("the redirect varies on both steering headers", async () => {
    const response = await call("/artifact/org.slf4j/org.slf4j.jar");
    assert.equal(response.headers.get("Vary"), "Jenesis-Prerelease, Jenesis-Mirror");
});

test("the redirect targets the mirror when no repository is bound", async () => {
    const response = await call("/artifact/org.slf4j/org.slf4j.jar", { env: { DATA_BASE } });
    assert.equal(
        response.headers.get("Location"),
        "https://maven-central.storage-download.googleapis.com/maven2/org/slf4j/slf4j-api/2.0.10/slf4j-api-2.0.10.jar",
    );
    assert.equal(response.headers.get("Jenesis-Mirror"), null);
});

test("a false mirror header redirects to Maven Central and says so", async () => {
    const response = await call("/artifact/org.slf4j/org.slf4j.jar", {
        env: { DATA_BASE },
        headers: { "Jenesis-Mirror": "false" },
    });
    assert.equal(
        response.headers.get("Location"),
        "https://repo.maven.apache.org/maven2/org/slf4j/slf4j-api/2.0.10/slf4j-api-2.0.10.jar",
    );
    assert.equal(response.headers.get("Jenesis-Mirror"), "false");
});

test("a false mirror header honours a bound CENTRAL_BASE", async () => {
    const response = await call("/artifact/org.slf4j/org.slf4j.jar", {
        env: { DATA_BASE, ARTIFACT_BASE, CENTRAL_BASE: "https://central.test/" },
        headers: { "Jenesis-Mirror": "false" },
    });
    assert.equal(
        response.headers.get("Location"),
        "https://central.test/org/slf4j/slf4j-api/2.0.10/slf4j-api-2.0.10.jar",
    );
});

test("a deployment naming one repository never redirects outside it", async () => {
    for (const value of ["false", "true", ""]) {
        const response = await call("/artifact/org.slf4j/org.slf4j.jar", {
            headers: { "Jenesis-Mirror": value },
        });
        assert.equal(
            response.headers.get("Location"),
            "https://maven.test/org/slf4j/slf4j-api/2.0.10/slf4j-api-2.0.10.jar",
            value,
        );
    }
});

test("any mirror header value other than false keeps the mirror", async () => {
    for (const value of ["true", "no", "0", ""]) {
        const response = await call("/artifact/org.slf4j/org.slf4j.jar", {
            env: { DATA_BASE },
            headers: { "Jenesis-Mirror": value },
        });
        assert.equal(
            response.headers.get("Location"),
            "https://maven-central.storage-download.googleapis.com/maven2/org/slf4j/slf4j-api/2.0.10/slf4j-api-2.0.10.jar",
            value,
        );
        assert.equal(response.headers.get("Jenesis-Mirror"), null, value);
    }
});

test("any header value other than true leaves pre-releases skipped", async () => {
    for (const value of ["false", "yes", "1", ""]) {
        const response = await call("/artifact/org.alpha/org.alpha.jar", {
            headers: { "Jenesis-Prerelease": value },
        });
        assert.equal(response.headers.get("Jenesis-MavenVersion"), "2.0.19", value);
    }
});

test("every qualifier that ranks below the release is treated as a pre-release", async () => {
    for (const version of [
        "2.1.0-alpha1", "1.1-beta-2", "1.1-M3", "1.1-rc1", "1.1-CR1", "1.1-SNAPSHOT",
        "1.1-a1", "1.1-b2", "1.1.0.RC2", "1.1-ea", "1.1-preview3", "1.1-dev", "1.1-nightly",
        "1.1-milestone-1", "1.1-pre", "1.1-canary", "1.1-next", "1.1-adhoc", "1.1-test",
    ]) {
        const response = await call("/artifact/org.sample/org.sample.jar", {
            files: sampleVersions(version, "1.0"),
        });
        assert.equal(response.headers.get("Jenesis-MavenVersion"), "1.0", version);
    }
});

test("a qualifier that ranks at or above the release is not a pre-release", async () => {
    for (const version of [
        "2.0.19", "1.1.0.Final", "1.1.0.RELEASE", "1.1-ga", "1.1-sp1", "1.1-jre",
        "1.1-android", "1.1.0+build7", "1.1-incubating", "1.1-b", "1.1.1",
    ]) {
        const response = await call("/artifact/org.sample/org.sample.jar", {
            files: sampleVersions(version, "0.9"),
        });
        assert.equal(response.headers.get("Jenesis-MavenVersion"), version, version);
        assert.equal(response.headers.get("Jenesis-Prerelease"), null, version);
    }
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
