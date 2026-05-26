/**
 * Cloudflare Worker that resolves Java module names + versions to Maven Central
 * artifact URLs by consulting this repository's published `data/modules/<dotted/path>/`
 * directories and returning a 302 redirect.
 *
 * Two resolution modes:
 *
 *  - `/artifact/<moduleName>/[<version>/]<filename>.jar`
 *    Resolves through `artifacts[-<classifier>].tsv`. The version segment is the **Maven
 *    coordinate version**. This is the historical lookup: many Maven versions of the same
 *    module name may exist (one row per Maven version), so the version space is dense.
 *
 *  - `/module/<moduleName>/[<version>/]<filename>.jar`
 *    Resolves through `modules[-<classifier>].tsv`. The version segment is the
 *    **module-info version** (the publisher's declared module version, falling back to the
 *    Maven version when no module-info version was declared). Each module-version maps to
 *    exactly one Maven coordinate - the oldest Maven publish wins, so the mapping is
 *    stable even though Maven doesn't enforce unique module versions.
 *
 *  - `/sources/<moduleName>/[<moduleVersion>/]<filename>.jar`
 *    `/documentation/<moduleName>/[<moduleVersion>/]<filename>.jar`
 *    Companion sources / javadoc JARs. The version segment is the **module-info version**;
 *    these routes resolve through `modules.tsv` and synthesise the `-sources` / `-javadoc`
 *    URL from the row's Maven coordinate. (Maven Central doesn't separately index sources
 *    or javadoc artifacts; we trust the publisher attached them to the same coordinate.)
 *
 * In all four cases the version segment is optional - leaving it out picks the first row
 * in the TSV (highest version, since both files are sorted descending). `<filename>` is
 * `<moduleName>.jar` or `<moduleName>-<classifier>.jar`; a classifier flips the lookup
 * from `<base>.tsv` to `<base>-<classifier>.tsv` (where `<base>` is `artifacts` or
 * `modules`).
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

// Resolution modes. The "tsv" property is the TSV file base name; "filenameSuffix" is the
// Maven-side filename decoration ("-sources" / "-javadoc") added before ".jar" when
// building the redirect target.
const MODES = {
    artifact: { tsv: "artifacts", filenameSuffix: "" },
    module: { tsv: "modules", filenameSuffix: "" },
    sources: { tsv: "modules", filenameSuffix: "-sources" },
    documentation: { tsv: "modules", filenameSuffix: "-javadoc" },
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

    const parsed = parsePath(new URL(request.url).pathname);
    if (!parsed) {
        return textResponse(404, "Not Found\n");
    }
    const { moduleName, version, classifier, mode } = parsed;
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

    const jarUrl = artifactUrl(artifactBase, row, classifier, config.filenameSuffix);
    return new Response(null, {
        status: 302,
        headers: {
            Location: jarUrl,
            "Cache-Control": `public, max-age=${redirectTtl}, stale-while-revalidate=${STALE_WHILE_REVALIDATE}`,
            "X-Jenesis-GroupId": row.groupId,
            "X-Jenesis-ArtifactId": row.artifactId,
            "X-Jenesis-MavenVersion": row.mavenVersion,
            ...(row.moduleVersion ? { "X-Jenesis-ModuleVersion": row.moduleVersion } : {}),
        },
    });
}

/**
 * Parse a request path into `{ moduleName, version, classifier, mode }` or return `null`
 * if the path doesn't fit a supported shape. Only the trailing 3-4 segments are inspected.
 *
 * `mode` is one of `artifact`, `module`, `sources`, `documentation` - see {@link MODES}.
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
    // match, so a path like /<prefix>/<mode>/<moduleName>/<version>/<filename>.jar with
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

    // Mode marker is mandatory: segment immediately before the module section.
    if (moduleStart < 1) {
        return null;
    }
    const before = parts[moduleStart - 1];
    if (!Object.hasOwn(MODES, before)) {
        return null;
    }

    return { moduleName: fileModule, version, classifier, mode: before };
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
 * Returns a normalised `{ groupId, artifactId, mavenVersion, moduleVersion? }` row, or
 * `null` when no row matches. If `version` is `null`, returns the first row (highest
 * version - both files are sorted descending). Otherwise returns the first row whose
 * first column matches `version` exactly.
 */
function pickRow(tsv, version, mode) {
    for (const line of tsv.split("\n")) {
        if (line.length === 0) {
            continue;
        }
        const cols = line.split("\t");
        if (cols.length < 4) {
            continue;
        }
        if (version !== null && cols[0] !== version) {
            continue;
        }
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
    return null;
}

function artifactUrl(base, row, classifier, filenameSuffix) {
    const groupPath = row.groupId.replaceAll(".", "/");
    const classifierSuffix = classifier ? `-${classifier}` : "";
    return `${base}${groupPath}/${row.artifactId}/${row.mavenVersion}/${row.artifactId}-${row.mavenVersion}${classifierSuffix}${filenameSuffix}.jar`;
}

function textResponse(status, body) {
    return new Response(body, {
        status,
        headers: { "Content-Type": "text/plain; charset=utf-8" },
    });
}
