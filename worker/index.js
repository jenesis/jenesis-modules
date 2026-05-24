/**
 * Cloudflare Worker that resolves Java module names + versions to Maven Central
 * artifact URLs by consulting the published `data/modules/<dotted/path>/current[-<classifier>].tsv`
 * files from this repository and returning a 302 redirect.
 *
 * For `sources` and `documentation` requests the same `current.tsv` (or
 * `current-<classifier>.tsv` if the URL filename specifies a classifier) is consulted to
 * resolve the module's groupId / artifactId / version, and the companion JAR URL is
 * synthesised by appending Maven's `-sources` / `-javadoc` filename convention. The
 * crawler does not separately track sources or javadoc JARs — they're assumed to follow
 * the convention if the upstream publisher attached them to the same coordinate.
 *
 * Request URL shapes (mirroring `build.jenesis.module.JenesisModuleRepository`):
 *
 *     GET /<kind>/<moduleName>/<filename>.jar                    → latest
 *     GET /<kind>/<moduleName>/<version>/<filename>.jar          → versioned
 *
 * where `<kind>` is one of:
 *     module          main artifact (alias of `modules`, matching this repo's data path)
 *     modules         main artifact
 *     sources         Maven `-sources` companion JAR
 *     documentation   Maven `-javadoc` companion JAR
 *
 * and `<filename>` is `<moduleName>` or `<moduleName>-<classifier>`.
 *
 * The worker is indifferent to any path segments preceding the kind marker — only the
 * trailing three or four segments are inspected — so it can be deployed behind any
 * additional route prefix without configuration.
 *
 * Environment bindings (all optional):
 *   DATA_BASE         Base URL for fetching `current[-<classifier>].tsv`. Defaults to
 *                     this repo's `main` branch on raw.githubusercontent.com.
 *   ARTIFACT_BASE     Base URL of the Maven repository to redirect to. Defaults to
 *                     repo.maven.apache.org/maven2/.
 *   REDIRECT_TTL      Cache-Control max-age (seconds) on the 302 response. Defaults
 *                     to 3600 (1 hour). The Cloudflare edge cache also caches the
 *                     upstream `.tsv` fetches at the same TTL.
 */

const DEFAULT_DATA_BASE =
    "https://raw.githubusercontent.com/raphw/jenesis-modules/main/data/modules/";
const DEFAULT_ARTIFACT_BASE = "https://repo.maven.apache.org/maven2/";
const DEFAULT_REDIRECT_TTL = 3600;
const STALE_WHILE_REVALIDATE = 86400;

// Per Java Language Spec: module name segments are Java identifiers (no hyphens).
// We use this both to validate URL-supplied module names and to split a
// `<moduleName>-<classifier>` basename at the first hyphen.
const MODULE_SEGMENT = /^[A-Za-z_$][A-Za-z0-9_$]*$/;

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

    const parsed = parsePath(new URL(request.url).pathname);
    if (!parsed) {
        return textResponse(404, "Not Found\n");
    }
    const { moduleName, version, classifier, kind } = parsed;

    const dataBase = (env && env.DATA_BASE) || DEFAULT_DATA_BASE;
    const artifactBase = (env && env.ARTIFACT_BASE) || DEFAULT_ARTIFACT_BASE;
    const redirectTtl = Number((env && env.REDIRECT_TTL) || DEFAULT_REDIRECT_TTL);

    const tsvPath =
        moduleName.replaceAll(".", "/") +
        "/" +
        (classifier ? `current-${classifier}.tsv` : "current.tsv");

    const tsvResponse = await fetch(dataBase + tsvPath, {
        cf: { cacheTtl: redirectTtl, cacheEverything: true },
    });
    if (tsvResponse.status === 404) {
        return textResponse(
            404,
            `Not Found: module ${moduleName}${classifier ? `-${classifier}` : ""}\n`,
        );
    }
    if (!tsvResponse.ok) {
        return textResponse(
            502,
            `Upstream ${tsvResponse.status} fetching ${tsvPath}\n`,
        );
    }

    const tsv = await tsvResponse.text();
    const row = pickRow(tsv, version);
    if (!row) {
        return textResponse(
            404,
            `Not Found: version ${version ?? "latest"} for module ${moduleName}\n`,
        );
    }

    const jarUrl = artifactUrl(artifactBase, row, classifier, kind);
    return new Response(null, {
        status: 302,
        headers: {
            Location: jarUrl,
            "Cache-Control": `public, max-age=${redirectTtl}, stale-while-revalidate=${STALE_WHILE_REVALIDATE}`,
            "X-Jenesis-GroupId": row.groupId,
            "X-Jenesis-ArtifactId": row.artifactId,
            "X-Jenesis-Module": row.type,
        },
    });
}

/**
 * Parse a request path into `{ moduleName, version, classifier, kind }` or return `null`
 * if the path doesn't fit a supported shape. Only the trailing 3-4 segments are inspected.
 *
 * `kind` is one of:
 *   "main"    — preceded by `module/` or `modules/`
 *   "sources" — preceded by `sources/` (maps to Maven's `-sources` classifier)
 *   "javadoc" — preceded by `documentation/` (maps to Maven's `-javadoc` classifier)
 *
 * `<filename>` is `<moduleName>.jar` or `<moduleName>-<classifier>.jar`.
 */
function parsePath(pathname) {
    const parts = pathname.split("/").filter((p) => p.length > 0);
    if (parts.length < 3) {
        return null;
    }
    const filename = parts[parts.length - 1];
    if (!filename.endsWith(".jar")) {
        return null;
    }
    const basename = filename.slice(0, -".jar".length);
    if (basename.length === 0) {
        return null;
    }

    const hyphen = basename.indexOf("-");
    const fileModule = hyphen < 0 ? basename : basename.slice(0, hyphen);
    const classifier = hyphen < 0 ? null : basename.slice(hyphen + 1);

    if (!isModuleName(fileModule) || (classifier && classifier.length === 0)) {
        return null;
    }

    // Locate the start of the module section: either parts[length-2] (latest) or
    // parts[length-3] (versioned). Prefer the versioned interpretation when both could
    // match, so a path like /<prefix>/<kind>/<moduleName>/<version>/<filename>.jar with
    // `<prefix>` being any mount prefix still resolves correctly.
    const dir = parts[parts.length - 2];
    let moduleStart;
    let version;
    if (parts.length >= 4 && parts[parts.length - 3] === fileModule) {
        moduleStart = parts.length - 3;
        version = dir;
    } else if (dir === fileModule) {
        moduleStart = parts.length - 2;
        version = null;
    } else {
        return null;
    }

    // Kind marker is mandatory: segment immediately before the module section.
    if (moduleStart < 1) {
        return null;
    }
    const before = parts[moduleStart - 1];
    let kind;
    if (before === "module" || before === "modules") {
        kind = "main";
    } else if (before === "sources") {
        kind = "sources";
    } else if (before === "documentation") {
        kind = "javadoc";
    } else {
        return null;
    }

    return { moduleName: fileModule, version, classifier, kind };
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
 * Pick a row from a `current.tsv` payload.
 *
 * If `version` is `null`, returns the first row (highest version — `current.tsv` is
 * sorted version-descending). Otherwise returns the first row whose first column matches
 * `version` exactly, or `null` if not found.
 *
 * Each row has four tab-separated columns: version, type, groupId, artifactId.
 */
function pickRow(tsv, version) {
    for (const line of tsv.split("\n")) {
        if (line.length === 0) {
            continue;
        }
        const cols = line.split("\t");
        if (cols.length < 4) {
            continue;
        }
        if (version === null || cols[0] === version) {
            return {
                version: cols[0],
                type: cols[1],
                groupId: cols[2],
                artifactId: cols[3],
            };
        }
    }
    return null;
}

function artifactUrl(base, row, classifier, kind) {
    const groupPath = row.groupId.replaceAll(".", "/");
    const classifierSuffix = classifier ? `-${classifier}` : "";
    const kindSuffix =
        kind === "sources" ? "-sources" : kind === "javadoc" ? "-javadoc" : "";
    return `${base}${groupPath}/${row.artifactId}/${row.version}/${row.artifactId}-${row.version}${classifierSuffix}${kindSuffix}.jar`;
}

function textResponse(status, body) {
    return new Response(body, {
        status,
        headers: { "Content-Type": "text/plain; charset=utf-8" },
    });
}
