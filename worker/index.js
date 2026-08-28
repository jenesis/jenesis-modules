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
 *    `<moduleName>.jar` or `<moduleName>-<classifier>.jar`; only `.jar` is supported on
 *    this route.
 *
 *  - `/sources/<moduleName>/[<moduleVersion>/]<filename>.jar`
 *    `/documentation/<moduleName>/[<moduleVersion>/]<filename>.jar`
 *    Companion sources / javadoc JARs. The version segment is the **module-info version**;
 *    these routes resolve through `modules.tsv` and synthesise the `-sources` / `-javadoc`
 *    URL from the row's Maven coordinate.
 *
 * In every mode the version segment is optional - leaving it out picks the first row in
 * the TSV (highest version, since both files are sorted descending). An explicit version
 * that is not in the TSV is resolved on a best-effort basis against the newest coordinate
 * (the first row's groupId / artifactId), so versions published after the last crawl still
 * redirect; the request only 404s if Maven Central itself has no such artifact.
 *
 * The worker is indifferent to any path segments preceding the mode marker - only the
 * trailing three or four segments are inspected - so it can be deployed behind any
 * additional route prefix without configuration.
 *
 * Environment bindings (all optional):
 *   DATA_BASE         Base URL for fetching `artifacts[-<classifier>].tsv` and
 *                     `modules[-<classifier>].tsv`. Defaults to this repo's `main`
 *                     branch on raw.githubusercontent.com.
 *   ARTIFACT_BASE     Base URL of the Maven repository to redirect to. Defaults to
 *                     repo.maven.apache.org/maven2/.
 *   HOME_REDIRECT     URL the root path (`/`) redirects to. Defaults to this repo's
 *                     GitHub page.
 *   REDIRECT_TTL      Cache-Control max-age (seconds) on the 302 response. Defaults
 *                     to 3600 (1 hour). The Cloudflare edge cache also caches the
 *                     upstream `.tsv` fetches at the same TTL.
 */

const DEFAULT_DATA_BASE =
    "https://raw.githubusercontent.com/jenesis/jenesis-modules/main/data/modules/";
const DEFAULT_ARTIFACT_BASE = "https://repo.maven.apache.org/maven2/";
const DEFAULT_HOME_REDIRECT = "https://github.com/jenesis/jenesis-modules";
const DEFAULT_REDIRECT_TTL = 3600;
const STALE_WHILE_REVALIDATE = 86400;

// Per Java Language Spec: module name segments are Java identifiers (no hyphens).
// We use this both to validate URL-supplied module names and to split a
// `<moduleName>-<classifier>` basename at the first hyphen.
const MODULE_SEGMENT = /^[A-Za-z_$][A-Za-z0-9_$]*$/;

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
    const row = pickRow(tsv, version, mode);
    if (!row) {
        return textResponse(
            404,
            `Not Found: version ${version ?? "latest"} for module ${moduleName} (${config.tsv}.tsv)\n`,
        );
    }

    const target = artifactUrl(artifactBase, row, classifier, extension, config.filenameSuffix);
    return new Response(null, {
        status: 302,
        headers: {
            Location: target,
            "Cache-Control": `public, max-age=${redirectTtl}, stale-while-revalidate=${STALE_WHILE_REVALIDATE}`,
            "X-Jenesis-GroupId": row.groupId,
            "X-Jenesis-ArtifactId": row.artifactId,
            "X-Jenesis-MavenVersion": row.mavenVersion,
            ...(row.moduleVersion ? { "X-Jenesis-ModuleVersion": row.moduleVersion } : {}),
            ...(row.bestEffort ? { "X-Jenesis-BestEffort": "true" } : {}),
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
    if (required !== null && extension !== required) {
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
 * (highest version - both files are sorted descending). Otherwise returns the first row
 * whose first column matches `version` exactly.
 *
 * If an explicit `version` is not in the file but the module has at least one row, the
 * version is assumed to exist on Maven Central under the **newest** coordinate (the first
 * row's groupId / artifactId): a best-effort row carrying the requested version verbatim is
 * returned (flagged `bestEffort`). This lets versions published after the last crawl resolve
 * optimistically; if Maven Central has no such artifact the redirect simply 404s downstream.
 */
function pickRow(tsv, version, mode) {
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
        if (version === null || cols[0] === version) {
            return row;
        }
    }
    if (version !== null && newest !== null) {
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

function artifactUrl(base, row, classifier, extension, filenameSuffix) {
    const groupPath = row.groupId.replaceAll(".", "/");
    const classifierSuffix = classifier ? `-${classifier}` : "";
    return `${base}${groupPath}/${row.artifactId}/${row.mavenVersion}/${row.artifactId}-${row.mavenVersion}${classifierSuffix}${filenameSuffix}.${extension}`;
}

function textResponse(status, body) {
    return new Response(body, {
        status,
        headers: { "Content-Type": "text/plain; charset=utf-8" },
    });
}
