/**
 * Cloudflare Worker that resolves Java module names + versions to Maven Central
 * artifact URLs by consulting this repository's published `data/modules/<dotted/path>/`
 * directories and returning a 302 redirect.
 *
 * Four resolution modes:
 *
 *  - `/artifact/<moduleName>/[<version>/]<filename>`
 *    Transparent Maven proxy keyed by module name. Resolves the canonical Maven coordinate
 *    via `artifacts[-<classifier>].tsv` and translates the request filename to the
 *    corresponding Maven filename verbatim. Anything after `<moduleName>` (or after
 *    `<moduleName>-<classifier>`) passes through unchanged. Examples:
 *      `<moduleName>.jar`              → `<artifactId>-<version>.jar`
 *      `<moduleName>.pom`              → `<artifactId>-<version>.pom`
 *      `<moduleName>.jar.asc`          → `<artifactId>-<version>.jar.asc`
 *      `<moduleName>.pom.sha256`       → `<artifactId>-<version>.pom.sha256`
 *      `<moduleName>.module`           → `<artifactId>-<version>.module`
 *      `<moduleName>-<c>.jar`          → `<artifactId>-<version>-<c>.jar`
 *      `<moduleName>-<c>.pom.sha512`   → `<artifactId>-<version>-<c>.pom.sha512`
 *    The version segment is the **Maven coordinate version**. The classifier `<c>` flips
 *    the lookup from `artifacts.tsv` to `artifacts-<c>.tsv` and becomes a standard Maven
 *    classifier on the resulting filename.
 *
 *  - `/module/<moduleName>/[<version>/]<filename>.jar`
 *    Resolves through `modules[-<classifier>].tsv`. The version segment is the
 *    **module-info version** (the publisher's declared module version, falling back to the
 *    Maven version when no module-info version was declared). Each module-version maps to
 *    exactly one Maven coordinate - the oldest Maven publish wins, so the mapping is
 *    stable even though Maven doesn't enforce unique module versions. Filename is
 *    `<moduleName>.jar` or `<moduleName>-<classifier>.jar`; only `.jar`, optionally with a
 *    trailing `.asc` for the detached OpenPGP signature or `.sigstore.json` for the Sigstore
 *    bundle, is supported on this route.
 *
 *  - `/sources/<moduleName>/[<moduleVersion>/]<filename>.jar`
 *    `/documentation/<moduleName>/[<moduleVersion>/]<filename>.jar`
 *    Companion sources / javadoc JARs. The version segment is the **module-info version**;
 *    these routes resolve through `modules.tsv` and synthesise the `-sources` / `-javadoc`
 *    URL from the row's Maven coordinate. As on `/module/`, a trailing `.asc` or
 *    `.sigstore.json` fetches the signature instead of the jar.
 *
 * In every mode the version segment is optional - leaving it out picks the newest row that
 * is not a pre-release: the TSVs are sorted descending, so that is the first row whose
 * version carries no `alpha`, `beta`, `milestone`, `rc`, `snapshot` or comparable
 * qualifier. A request opts out of that filter with `Jenesis-Prerelease: true`, which
 * restores the plain newest row; without the header a module whose every recorded version
 * is a pre-release resolves to nothing and answers 404. A pre-release asked for by name is
 * served either way - naming a version is already an unambiguous request for it. Whenever
 * the version served is a pre-release, the response says so with the same header.
 *
 * An explicit version that is not in the TSV is resolved on a best-effort basis against the
 * newest coordinate (the first row's groupId / artifactId), so versions published after the
 * last crawl still redirect; the request only 404s if Maven Central itself has no such
 * artifact. A request refuses that guess with `Jenesis-BestEffort: false`, which answers
 * 404 for a version the crawl has not recorded rather than redirecting optimistically.
 * Whenever the redirect served is such a guess, the response says so with the same header.
 *
 * The redirect targets Google's Maven Central mirror, which carries the same artifacts and
 * is not rate limited the way Central is. A request names another Maven repository with
 * `Jenesis-Repository: <url>`, whose URL then replaces the mirror as the base of the
 * redirect; `Jenesis-Repository: https://repo.maven.apache.org/maven2/` asks for Central
 * itself. Because the mirror trails Central by a couple of hours, a version published since
 * its last sync is a case for the header: a best-effort redirect for a version newer than
 * the crawl is the most likely to want it. A redirect built from the header is marked
 * `private`, so no shared cache hands one client's repository to another.
 *
 * The worker is indifferent to any path segments preceding the mode marker - only the
 * trailing three or four segments are inspected - so it can be deployed behind any
 * additional route prefix without configuration.
 *
 * Environment bindings (all optional):
 *   DATA_BASE         Base URL for fetching `artifacts[-<classifier>].tsv` and
 *                     `modules[-<classifier>].tsv`. Defaults to this repo's `main`
 *                     branch on raw.githubusercontent.com.
 *   ARTIFACT_BASE     Base URL of the Maven repository to redirect to when a request
 *                     does not name one. Defaults to Google's Maven Central mirror.
 *   HOME_REDIRECT     URL the root path (`/`) redirects to. Defaults to this repo's
 *                     GitHub page.
 *   REDIRECT_TTL      Cache-Control max-age (seconds) on the 302 response. Defaults
 *                     to 3600 (1 hour). The Cloudflare edge cache also caches the
 *                     upstream `.tsv` fetches at the same TTL.
 */

const DEFAULT_DATA_BASE =
    "https://raw.githubusercontent.com/jenesis/jenesis-modules/main/data/modules/";
const DEFAULT_ARTIFACT_BASE = "https://maven-central.storage-download.googleapis.com/maven2/";
const DEFAULT_HOME_REDIRECT = "https://github.com/jenesis/jenesis-modules";
const DEFAULT_REDIRECT_TTL = 3600;
const STALE_WHILE_REVALIDATE = 86400;

// Per Java Language Spec: module name segments are Java identifiers (no hyphens).
// We use this both to validate URL-supplied module names and to split a
// `<moduleName>-<classifier>` basename at the first hyphen.
const MODULE_SEGMENT = /^[A-Za-z_$][A-Za-z0-9_$]*$/;

// Signature sidecars: the detached OpenPGP signature and the Sigstore bundle. Routes that
// constrain the request extension accept their extension with one of these appended, so a
// signature can be fetched over the same route as the artifact it signs. The redirect target
// gets the same suffix, and Maven Central serves the file the publisher uploaded beside the jar.
const SIGNATURE_EXTENSIONS = [".asc", ".sigstore.json"];

// Opt-in request header: `true` widens an unversioned request to pre-releases. On a
// response the same header states that the version served is one, so the two directions
// read alike: "pre-releases: yes".
const PRERELEASE_HEADER = "Jenesis-Prerelease";

// Request header naming the Maven repository to redirect to, as an absolute http(s) URL,
// in place of ARTIFACT_BASE. The `Location` already shows it, so it is not echoed back.
const REPOSITORY_HEADER = "Jenesis-Repository";

// Opt-out request header: `false` refuses a redirect built for a version the crawl has not
// recorded. On a response the same header states that the redirect served is such a guess,
// so the two directions read alike: "guesses: yes".
const BEST_EFFORT_HEADER = "Jenesis-BestEffort";

// Every header changes which redirect a request earns, so a shared cache has to key on them.
const VARY_HEADERS = `${PRERELEASE_HEADER}, ${REPOSITORY_HEADER}, ${BEST_EFFORT_HEADER}`;

// Maven's qualifier order, mirroring the Jenesis build tool's version negotiator: a
// qualifier that ranks below the empty one (the release itself) marks a pre-release.
// An unknown qualifier ranks above the release, so it is not a pre-release - "1.0-jre"
// is a release, "1.0-rc1" is not.
const QUALIFIERS = ["alpha", "beta", "milestone", "rc", "snapshot", "", "sp"];
const RELEASE_INDEX = QUALIFIERS.indexOf("");
const QUALIFIER_ALIASES = { ga: "", final: "", release: "", cr: "rc" };

// Qualifiers Maven's ordering ranks above the release because it doesn't know them, but
// which publishers use to mark a pre-release. Matched against the leading letters of a
// qualifier, so "preview2" counts as well as "preview".
const PRERELEASE_QUALIFIERS = new Set([
    "ea", "pre", "prerelease", "preview", "dev", "nightly", "canary", "next", "test", "adhoc",
]);

// What `pickRow` answers when a module has rows but every one of them is a pre-release and
// the request did not opt in - distinct from `null`, which means the view is empty.
const PRERELEASE_ONLY = Symbol("prerelease-only");

// Resolution modes. `tsv` is the TSV file base name. `filenameSuffix` is the Maven-side
// filename decoration ("-sources" / "-javadoc") spliced before the extension when building
// the redirect target. `extension` constrains the request filename: `null` means the
// request extension passes through verbatim to the Maven URL (transparent proxy);
// otherwise the request must end in `.<extension>` and that's also what the Maven URL gets.
const MODES = {
    artifact: { tsv: "artifacts", filenameSuffix: "", extension: null },
    module: { tsv: "modules", filenameSuffix: "", extension: "jar" },
    sources: { tsv: "modules", filenameSuffix: "-sources", extension: "jar" },
    documentation: { tsv: "modules", filenameSuffix: "-javadoc", extension: "jar" },
};

export default {
    async fetch(request, env) {
        try {
            return await handleRequest(request, env);
        } catch (error) {
            console.error("worker error", error);
            return textResponse(500, "Internal Server Error\n");
        }
    },
};

async function handleRequest(request, env) {
    if (request.method !== "GET" && request.method !== "HEAD") {
        return new Response("Method Not Allowed\n", {
            status: 405,
            headers: {
                Allow: "GET, HEAD",
                "Content-Type": "text/plain; charset=utf-8",
            },
        });
    }

    const pathname = new URL(request.url).pathname;
    if (pathname === "/" || pathname === "") {
        const home = (env && env.HOME_REDIRECT) || DEFAULT_HOME_REDIRECT;
        return new Response(null, {
            status: 302,
            headers: {
                Location: home,
                "Cache-Control": "public, max-age=3600",
            },
        });
    }

    const parsed = parsePath(pathname);
    if (!parsed) {
        return textResponse(404, "Not Found\n");
    }
    const prereleases = (request.headers.get(PRERELEASE_HEADER) || "").trim().toLowerCase() === "true";
    const guesses = (request.headers.get(BEST_EFFORT_HEADER) || "").trim().toLowerCase() !== "false";
    const requestedRepository = (request.headers.get(REPOSITORY_HEADER) || "").trim();
    const repository = requestedRepository ? repositoryBase(requestedRepository) : null;
    if (requestedRepository && !repository) {
        return textResponse(
            400,
            `Bad Request: ${REPOSITORY_HEADER} must be an absolute http or https URL`
                + ` without credentials, query or fragment\n`,
        );
    }
    const { moduleName, version, classifier, extension, mode } = parsed;
    const config = MODES[mode];

    const dataBase = (env && env.DATA_BASE) || DEFAULT_DATA_BASE;
    const artifactBase = (env && env.ARTIFACT_BASE) || DEFAULT_ARTIFACT_BASE;
    const redirectTtl = Number((env && env.REDIRECT_TTL) || DEFAULT_REDIRECT_TTL);

    const tsvName = classifier
        ? `${config.tsv}-${classifier}.tsv`
        : `${config.tsv}.tsv`;
    const tsvPath = moduleName.replaceAll(".", "/") + "/" + tsvName;

    const tsvResponse = await fetch(dataBase + tsvPath, {
        cf: { cacheTtl: redirectTtl, cacheEverything: true },
    });
    if (tsvResponse.status === 404) {
        return textResponse(
            404,
            `Not Found: module ${moduleName}${classifier ? `-${classifier}` : ""} (${config.tsv}.tsv)\n`,
        );
    }
    if (!tsvResponse.ok) {
        return textResponse(
            502,
            `Upstream ${tsvResponse.status} fetching ${tsvPath}\n`,
        );
    }

    const tsv = await tsvResponse.text();
    const row = pickRow(tsv, version, mode, prereleases);
    if (row === PRERELEASE_ONLY) {
        return textResponse(
            404,
            `Not Found: no released version of module ${moduleName}${classifier ? `-${classifier}` : ""}`
                + ` (${config.tsv}.tsv)`
                + ` - every recorded version is a pre-release;`
                + ` send ${PRERELEASE_HEADER}: true to accept one\n`,
            { Vary: PRERELEASE_HEADER },
        );
    }
    if (!row) {
        return textResponse(
            404,
            `Not Found: version ${version ?? "latest"} for module ${moduleName} (${config.tsv}.tsv)\n`,
        );
    }
    if (row.bestEffort && !guesses) {
        return textResponse(
            404,
            `Not Found: version ${version} for module ${moduleName} (${config.tsv}.tsv)`
                + ` - the crawl has not recorded it, and ${BEST_EFFORT_HEADER}: false`
                + ` refuses the guess that it exists under the newest coordinate\n`,
            { Vary: BEST_EFFORT_HEADER },
        );
    }

    const target = artifactUrl(repository ?? artifactBase, row, classifier, extension, config.filenameSuffix);
    return new Response(null, {
        status: 302,
        headers: {
            Location: target,
            "Cache-Control": `${repository ? "private" : "public"}, max-age=${redirectTtl}, stale-while-revalidate=${STALE_WHILE_REVALIDATE}`,
            Vary: VARY_HEADERS,
            "Jenesis-GroupId": row.groupId,
            "Jenesis-ArtifactId": row.artifactId,
            "Jenesis-MavenVersion": row.mavenVersion,
            ...(row.moduleVersion ? { "Jenesis-ModuleVersion": row.moduleVersion } : {}),
            ...(row.bestEffort ? { [BEST_EFFORT_HEADER]: "true" } : {}),
            ...(isRelease(row) ? {} : { [PRERELEASE_HEADER]: "true" }),
        },
    });
}

/**
 * Parse a request path into `{ moduleName, version, classifier, extension, mode }` or
 * return `null` if the path doesn't fit a supported shape. Only the trailing 3-4 segments
 * are inspected.
 *
 * `mode` is one of `artifact`, `module`, `sources`, `documentation` - see {@link MODES}.
 * For `artifact`, `extension` is whatever follows the module-name (or
 * `-<classifier>`) suffix in the filename - any string the Maven layer might serve. For the
 * other modes the extension is constrained to `jar`.
 *
 * The module-name path segment must match the filename prefix; the rest of the filename
 * begins with `.` (no classifier) or `-` (classifier). After the classifier a `.` is
 * mandatory and the rest is the extension.
 */
function parsePath(pathname) {
    const parts = pathname.split("/").filter((p) => p.length > 0);
    if (parts.length < 3) {
        return null;
    }
    const filename = parts[parts.length - 1];

    // Locate the module-name path segment: prefer the versioned shape
    // (.../mode/module/version/filename) and fall back to the unversioned shape
    // (.../mode/module/filename). The path segment must be both a valid module name and a
    // prefix of the filename followed by `.` or `-`.
    let moduleStart = -1;
    let version = null;
    let moduleName = null;
    let suffix = null;
    if (parts.length >= 4) {
        const candidate = parts[parts.length - 3];
        const tail = matchModuleSegment(candidate, filename);
        if (tail !== null) {
            moduleStart = parts.length - 3;
            version = parts[parts.length - 2];
            moduleName = candidate;
            suffix = tail;
        }
    }
    if (moduleName === null) {
        const candidate = parts[parts.length - 2];
        const tail = matchModuleSegment(candidate, filename);
        if (tail === null) {
            return null;
        }
        moduleStart = parts.length - 2;
        version = null;
        moduleName = candidate;
        suffix = tail;
    }

    // Mode marker is mandatory: segment immediately before the module section.
    if (moduleStart < 1) {
        return null;
    }
    const mode = parts[moduleStart - 1];
    if (!Object.hasOwn(MODES, mode)) {
        return null;
    }

    // Split `suffix` (starts with `.` or `-`) into optional classifier + extension.
    let classifier;
    let extension;
    if (suffix.startsWith(".")) {
        classifier = null;
        extension = suffix.slice(1);
    } else {
        const dot = suffix.indexOf(".");
        if (dot < 0) {
            return null;
        }
        classifier = suffix.slice(1, dot);
        extension = suffix.slice(dot + 1);
        if (classifier.length === 0) {
            return null;
        }
    }
    if (extension.length === 0) {
        return null;
    }

    const required = MODES[mode].extension;
    if (
        required !== null &&
        extension !== required &&
        !SIGNATURE_EXTENSIONS.some((signature) => extension === required + signature)
    ) {
        return null;
    }

    return { moduleName, version, classifier, extension, mode };
}

/**
 * Returns the filename suffix (starting with `.` or `-`) when `segment` is a valid module
 * name and `filename` is `<segment>.<...>` or `<segment>-<...>`. Returns `null` otherwise.
 */
function matchModuleSegment(segment, filename) {
    if (!isModuleName(segment)) {
        return null;
    }
    if (!filename.startsWith(segment)) {
        return null;
    }
    const tail = filename.slice(segment.length);
    if (tail.length === 0) {
        return null;
    }
    const first = tail.charAt(0);
    if (first !== "." && first !== "-") {
        return null;
    }
    return tail;
}

function isModuleName(text) {
    if (text.length === 0) {
        return false;
    }
    for (const segment of text.split(".")) {
        if (!MODULE_SEGMENT.test(segment)) {
            return false;
        }
    }
    return true;
}

/**
 * Pick a row from a TSV payload. `mode` tells the parser which shape to expect:
 *
 *   artifact mode  → artifacts.tsv: 4 cols  `version, type, groupId, artifactId`
 *   module/sources/documentation modes → modules.tsv: 4 cols
 *                                       `moduleVersion, groupId, artifactId, mavenVersion`
 *
 * Returns a normalised `{ groupId, artifactId, mavenVersion, moduleVersion?, bestEffort? }`
 * row, or `null` when nothing can be served. If `version` is `null`, returns the first row
 * that is not a pre-release (highest release version - both files are sorted descending),
 * or, when `prereleases` is set, simply the first row. A module with rows but no release to
 * offer answers {@link PRERELEASE_ONLY} rather than a row, which the caller turns into a
 * 404 naming the header that would have widened the search. Otherwise returns the first row
 * whose first column matches `version` exactly; a pre-release asked for by name is served
 * like any other version.
 *
 * If an explicit `version` is not in the file but the module has at least one row, the
 * version is assumed to exist on Maven Central under the **newest** coordinate (the first
 * row's groupId / artifactId): a best-effort row carrying the requested version verbatim is
 * returned (flagged `bestEffort`). This lets versions published after the last crawl resolve
 * optimistically; if Maven Central has no such artifact the redirect simply 404s downstream.
 */
function pickRow(tsv, version, mode, prereleases) {
    let newest = null;
    for (const line of tsv.split("\n")) {
        if (line.length === 0) {
            continue;
        }
        const cols = line.split("\t");
        if (cols.length < 4) {
            continue;
        }
        const row = normaliseRow(cols, mode);
        if (newest === null) {
            newest = row;
        }
        if (version === null ? prereleases || isRelease(row) : cols[0] === version) {
            return row;
        }
    }
    if (version === null) {
        return newest === null ? null : PRERELEASE_ONLY;
    }
    if (newest !== null) {
        return {
            groupId: newest.groupId,
            artifactId: newest.artifactId,
            mavenVersion: version,
            moduleVersion: mode === "artifact" ? null : version,
            bestEffort: true,
        };
    }
    return null;
}

function normaliseRow(cols, mode) {
    if (mode === "artifact") {
        return {
            groupId: cols[2],
            artifactId: cols[3],
            mavenVersion: cols[0],
            moduleVersion: null,
        };
    }
    return {
        groupId: cols[1],
        artifactId: cols[2],
        mavenVersion: cols[3],
        moduleVersion: cols[0],
    };
}

/**
 * Whether a row is a release: neither the version it is keyed by nor the Maven coordinate
 * version it resolves to carries a pre-release qualifier. Both are checked because a
 * publisher can declare a module-info version that is cleaner than the Maven version it
 * was published under.
 */
function isRelease(row) {
    return !isPrerelease(row.mavenVersion)
        && (row.moduleVersion === null || !isPrerelease(row.moduleVersion));
}

/**
 * Whether a version string carries a pre-release qualifier, following the same rules as the
 * Jenesis build tool's `STABLE` version negotiator so that both agree on what a release is.
 *
 * The version is split on `.` and `-` and at every digit / non-digit boundary, exactly as
 * Maven's `ComparableVersion` does, and every non-numeric token is judged on its own: a
 * single `a`, `b` or `m` before a digit expands to `alpha`, `beta`, `milestone`, the
 * aliases `ga`, `final`, `release` and `cr` are folded, and the token is a pre-release
 * marker if it ranks below the release qualifier or its leading letters name one of the
 * qualifiers Maven's ordering doesn't know. `2.1.0-alpha1`, `1.0-M3`, `3.0.0-ea` and
 * `1.0-SNAPSHOT` are pre-releases; `2.0.19`, `1.0.0.Final` and `33.0-jre` are not.
 */
function isPrerelease(version) {
    for (const segment of version.toLowerCase().split(/[.-]/)) {
        let index = 0;
        while (index < segment.length) {
            const start = index;
            const numeric = isDigit(segment.charAt(index));
            while (index < segment.length && isDigit(segment.charAt(index)) === numeric) {
                index++;
            }
            // Numeric and non-numeric runs alternate, so a non-numeric run that doesn't
            // end the segment is the shorthand-qualifier case ("m1", "b2").
            if (!numeric && !isReleaseQualifier(segment.slice(start, index), index < segment.length)) {
                return true;
            }
        }
    }
    return false;
}

function isReleaseQualifier(token, followedByDigit) {
    const expanded = followedByDigit && token.length === 1
        ? { a: "alpha", b: "beta", m: "milestone" }[token] ?? token
        : token;
    const qualifier = Object.hasOwn(QUALIFIER_ALIASES, expanded)
        ? QUALIFIER_ALIASES[expanded]
        : expanded;
    if (PRERELEASE_QUALIFIERS.has(/^[a-z]*/.exec(qualifier)[0])) {
        return false;
    }
    const rank = QUALIFIERS.indexOf(qualifier);
    return (rank < 0 ? QUALIFIERS.length : rank) >= RELEASE_INDEX;
}

function isDigit(character) {
    return character >= "0" && character <= "9";
}

/**
 * The redirect base a `Jenesis-Repository` value names, with a trailing slash so a value
 * given as `https://host/maven2` or `https://host` joins the artifact path like a bound
 * ARTIFACT_BASE does. Answers `null` for anything that is not an absolute http(s) URL, or
 * that carries credentials, a query or a fragment, none of which survive being joined to.
 */
function repositoryBase(value) {
    let url;
    try {
        url = new URL(value);
    } catch {
        return null;
    }
    if ((url.protocol !== "https:" && url.protocol !== "http:") || url.href !== url.origin + url.pathname) {
        return null;
    }
    return url.href.endsWith("/") ? url.href : `${url.href}/`;
}

function artifactUrl(base, row, classifier, extension, filenameSuffix) {
    const groupPath = row.groupId.replaceAll(".", "/");
    const classifierSuffix = classifier ? `-${classifier}` : "";
    return `${base}${groupPath}/${row.artifactId}/${row.mavenVersion}/${row.artifactId}-${row.mavenVersion}${classifierSuffix}${filenameSuffix}.${extension}`;
}

function textResponse(status, body, headers = {}) {
    return new Response(body, {
        status,
        headers: { "Content-Type": "text/plain; charset=utf-8", ...headers },
    });
}
