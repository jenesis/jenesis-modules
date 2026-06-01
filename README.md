# Jenesis Modules

![build](https://github.com/raphw/jenesis-modules/actions/workflows/build.yml/badge.svg)
![crawl](https://github.com/raphw/jenesis-modules/actions/workflows/crawl.yml/badge.svg)
![last crawl](https://img.shields.io/github/last-commit/raphw/jenesis-modules?path=data%2FSTATUS.md&display_timestamp=committer&label=last%20crawl)

See [`data/STATUS.md`](data/STATUS.md) for the live crawl progress. Note that the Maven Central index lags behind published artifacts by up to one week, so the most recent releases may not appear immediately.

> ### Powered by [Jenesis](https://github.com/raphw/jenesis)
> _A modern Java build tool: Java-native config, plugin-free, with `module-info.java` treated as a feature, not an afterthought._

A modular Java program that crawls Maven Central and records the Java module name produced by every modularised artifact. For each module, every published version is recorded with the Maven coordinates that produced it. The intended lookup pattern is: given a module name (and optional classifier) plus a version, find the Maven coordinates that publish it.

> ## [Read the module summary](data/SUMMARY.md)
> Module adoption across all of Maven Central: catalogue-wide counts, named vs automatic adoption, and per-year reports on the most depended-on artifacts.

## Output layout

```
data/
├── STATUS.md                     # live progress snapshot, rewritten every checkpoint
├── state.properties              # crawler resume point (index chain, last applied chunk, sweep start)
├── modules/                      # per-module version history (what consumers care about)
│   ├── com/fasterxml/jackson/core/versions.tsv     # full audit log (every claim ever seen)
│   ├── com/fasterxml/jackson/core/artifacts.tsv    # resolved view, keyed by Maven version
│   ├── com/fasterxml/jackson/core/modules.tsv      # resolved view, keyed by module-info version
│   ├── com/fasterxml/jackson/core/versions-no_aopalliance.tsv
│   ├── com/fasterxml/jackson/core/artifacts-no_aopalliance.tsv
│   ├── com/fasterxml/jackson/core/modules-no_aopalliance.tsv
│   ├── org/slf4j/versions.tsv
│   ├── org/slf4j/artifacts.tsv
│   ├── org/slf4j/modules.tsv
│   ├── org/slf4j/api/versions.tsv
│   └── ...
└── scanned/                      # per-artifact "we have already looked at these JARs" index
    ├── com/fasterxml/jackson/core/jackson-core.tsv
    ├── com/fasterxml/jackson/core/jackson-databind.tsv
    ├── org/slf4j/slf4j-api.tsv
    └── ...
```

### `data/modules/<dotted/path>/versions[-<classifier>].tsv` (audit log)

Each module's directory path mirrors the dot-separated module name. `versions.tsv` (or `versions-<classifier>.tsv` for a classified variant) is the **full audit log**: every `(groupId, artifactId, version)` that has ever declared this module name on Maven Central, append-only, in chronological order by `publishedAt`. The audit log is never pruned - even claims that have been excluded by an `owners.tsv` policy remain here, so collisions and module-injection attempts stay visible and the policy can be reverted without losing data.

Six tab-separated columns:

```
1.7.36  automatic  org.slf4j  slf4j-api  2022-02-08T13:33:50Z
2.0.9   named      org.slf4j  slf4j-api  2023-09-03T16:14:32Z  2.0.9
2.0.10  named      org.slf4j  slf4j-api  2023-12-28T20:50:38Z  2.0.10
```

- Column 1: version as published.
- Column 2: `named` (the JAR contains `module-info.class`, either at the root or at the highest `META-INF/versions/<N>/module-info.class` of a multi-release JAR) or `automatic` (the JAR's manifest sets `Automatic-Module-Name`). Non-modular JARs are not recorded.
- Column 3: `groupId`.
- Column 4: `artifactId`. Combined with columns 1 and 3 this gives the full Maven coordinate.
- Column 5: publication timestamp on Maven Central, UTC ISO-8601 with seconds precision (`yyyy-MM-dd'T'HH:mm:ss'Z'`). Sourced from the artifact storage layer's `Last-Modified`, not from the Nexus index, because the index occasionally re-stamps records during republishing events (see [Sourcing `publishedAt`](#sourcing-publishedat) below for the details). Fixed-width so lexicographic and chronological sort agree. Rows are only recorded when a real timestamp is available - coordinates whose timestamp is missing or zero are dropped at write time and never appear here, so consumers do not have to filter sentinels.
- Column 6: raw `module-info` version (the literal string declared by `ModuleDescriptor.rawVersion()`). Empty for `automatic` rows (no `module-info` exists) and for `named` rows whose `module-info` declared no version attribute - the column is present but the field is empty. A non-empty value is the verbatim string the publisher embedded. The parser rejects rows missing this column outright; the legacy 5-column format is no longer supported.

Use this file for auditing - "who has ever claimed this name?" - not for resolution. For resolution, read the sibling `artifacts.tsv` (Maven-version-keyed) or `modules.tsv` (module-info-version-keyed) described in the next two sections.

The hierarchical layout means a module whose name is a prefix of another module name coexists without conflict: `org.slf4j` and `org.slf4j.api` live at `org/slf4j/versions.tsv` and `org/slf4j/api/versions.tsv` respectively. The directory `org/slf4j` holds both its own `versions.tsv` and the `api/` subtree.

Lookup math (no parsing required): `data/modules/<segments-joined-by-slash>/versions[-<classifier>].tsv`.

### `data/modules/<dotted/path>/artifacts[-<classifier>].tsv` (resolved view, keyed by Maven version)

`artifacts.tsv` is the **resolved view keyed by the Maven coordinate version**: the rows the crawler currently considers authoritative, after applying `owners.tsv` (if present) or the implicit-owner rule (if absent). Consumers wanting "give me module M at Maven version V" read this file - no filtering, no sorting required.

Four tab-separated columns (no timestamp - the resolution has already been made):

```
2.0.10  named      org.slf4j  slf4j-api
2.0.9   named      org.slf4j  slf4j-api
1.7.36  automatic  org.slf4j  slf4j-api
```

- Columns mirror `versions.tsv`'s columns 1-4. Column 5 (timestamp) is omitted; it's no longer needed once the resolution has been made.
- Rows are sorted by version descending. There is at most one row per Maven version - if the policy permits multiple `(groupId, artifactId)` for the same module name and version, the one with the oldest `publishedAt` wins (lexicographic `groupId` ascending breaks same-second ties).
- The file may be absent when the policy filters out every claim (e.g. an empty `owners.tsv`) or when stage 2 has not yet run for this module after a fresh crawl. Either way: no `artifacts.tsv` means no resolved owner.

### `data/modules/<dotted/path>/modules[-<classifier>].tsv` (resolved view, keyed by module-info version)

`modules.tsv` is the **resolved view keyed by the publisher-declared module version** (the literal string in `module-info.class`, falling back to the Maven coordinate version when `module-info` declared none). It exists so consumers can ask "give me module M at module version X" and get the canonical Maven artifact back. The resolution policy keeps the module-version space and the Maven-version space in lockstep: any row whose `module-info` version semantically disagrees with its Maven coordinate version is filtered out (see "Resolution algorithm" below), so a `moduleVersion` lookup never lands on a JAR whose Maven coordinate is a different number.

Four tab-separated columns:

```
2.0.10  org.slf4j  slf4j-api  2.0.10
2.0.9   org.slf4j  slf4j-api  2.0.9
1.7.36  org.slf4j  slf4j-api  1.7.36
```

- Column 1: `moduleVersion`. The literal `module-info` version (`ModuleDescriptor.rawVersion()`), or the Maven coordinate version when none was declared. This is the lookup key.
- Column 2: `groupId`.
- Column 3: `artifactId`.
- Column 4: `mavenVersion`. The Maven coordinate version that first published this `moduleVersion`. The pair `(groupId, artifactId, mavenVersion)` is the actual artifact to fetch.
- Rows are sorted by `moduleVersion` descending (groupId then artifactId as deterministic tiebreakers).
- Only `named` rows participate - `automatic` modules have no `module-info` and therefore no declared module version. The `type` column from `artifacts.tsv` is omitted because every row is `named` by construction.
- Of the named rows, only those whose `module-info` either declared no version or declared one that semantically agrees with the Maven coordinate version survive. Rows where `module-info` advertises a different number are excluded from `modules.tsv`; they remain in `versions.tsv` as part of the audit log.
- **Stability guarantee.** Each `moduleVersion` resolves to exactly one Maven coordinate: the one with the *oldest* `publishedAt` among surviving rows. Once a `moduleVersion` has been mapped, future publishers that happen to declare the same `moduleVersion` are visible in the audit log but do not override the mapping. Combined with the version-equality filter above, the promise is "module M at version X always means the same JAR, and that JAR's Maven coordinate version is also X." Maven does not enforce unique module versions; this resolution gives consumers a stable view despite that.
- The file is absent when the module has no `named` rows for the resolved owner (e.g. the owner only ever shipped `automatic` JARs, `owners.tsv` filtered out every `named` claim, or every named row failed the version-equality filter). The crawler also deletes a stale `modules.tsv` when a regeneration ends with zero qualifying rows.

Both resolved views are regenerated from `versions.tsv` whenever the policy changes (via `SetOwners`) or new audit-log rows arrive (by the crawler's stage 2, see "How the crawl works").

#### Module-name collisions and module injection

A Java module name is **not** namespaced and **not** a stable identifier on Maven Central - it is just a string a JAR's `module-info.class` (or `Automatic-Module-Name` header) carries. Nothing prevents two unrelated artifacts from declaring the same module name, and in the live index that happens routinely. The most common cause is shading: a downstream artifact bundles a third-party library and forgets to relocate the bundled `module-info` along with its classes, so the parent JAR ends up exporting somebody else's module name.

Concrete example from the current crawl - the directory `data/modules/com/fasterxml/jackson/core/versions.tsv` holds **487 rows** declaring the module `com.fasterxml.jackson.core`. The canonical row is the actual Jackson JAR (`com.fasterxml.jackson.core:jackson-core`); the long tail is a mix of repackaged or shaded artifacts from unrelated groups (`software.amazon.awssdk:third-party-jackson-core`, several `software.amazon.glue:*`, `tech.powerjob:powerjob-shade-vertx`, and many more) that all advertise the same module name.

Most collisions in the wild are accidental, but the same mechanism is a **supply-chain attack surface**:

> **Module injection.** Anyone can publish a Maven artifact under their own `groupId` and have its `module-info.class` declare a popular module name (`org.slf4j`, `com.google.gson`, `org.hibernate.orm`, ...). A tool that resolves "which artifact provides module `X`?" by looking up `X` in this index and trusting the first or only row it finds can be steered toward a hostile JAR. The index faithfully records who declared what; it does **not** decide who is allowed to declare a given module name.

Consumers of `data/modules/` **must not** treat a module name as authoritative on its own. Recommended practice:

- Pin the expected `(groupId, artifactId)` for every module name your project depends on, and reject any row whose `(groupId, artifactId)` does not match.
- Where multiple rows exist for a wanted module name, do not auto-pick - either fail loudly or require explicit allowlisting.
- Treat newly appearing `(groupId, artifactId)` pairs for an already-known module name as a signal worth reviewing (it may be a legitimate fork, a typo-squat, or an attempted injection).
- Use the column-5 timestamp to identify the **first owner** of a module name: sort all rows for a given module name ascending by the publication timestamp; the earliest `(groupId, artifactId)` is the one that first declared it on Maven Central. Module names produced later by an unrelated coordinate are by definition "newcomers" and warrant scrutiny. The first-owner heuristic is not a guarantee of legitimacy (an attacker could in principle race to publish a generic-sounding name first) but it does flip the default from "trust the first row your tool happens to return" to "trust the row that has the longest unchallenged history under that name."
- Drop an **`owners.tsv`** allowlist next to the module's `versions.tsv` (see below) and the crawler narrows `artifacts.tsv` and `modules.tsv` to rows whose `(groupId, artifactId)` is on the list. `versions.tsv` itself stays untouched as the full audit log of every claim that has ever been seen.

The index distinguishes two layers: `versions.tsv` is a *catalog of declarations* (every party that has ever published the module name on Maven Central, kept forever for audit). `artifacts.tsv` and `modules.tsv` are *resolved views* (the rows the crawler considers authoritative right now, generated from `versions.tsv` by the resolution algorithm below). Consumers read the resolved views for resolution; they read `versions.tsv` to audit collisions.

#### Resolution algorithm

The crawler implements the rule below. Its outputs are `data/modules/<path>/artifacts.tsv` (Maven-version-keyed, 4 columns: `version`, `type`, `groupId`, `artifactId`) and `data/modules/<path>/modules.tsv` (module-info-version-keyed, 4 columns: `moduleVersion`, `groupId`, `artifactId`, `mavenVersion`). Consumers do not have to re-derive anything: they read whichever resolved view fits their lookup.

For each module:

1. **If `owners.tsv` exists** next to the module's `versions.tsv`, treat it as the authoritative allowlist:
   - From `versions.tsv` (and each `versions-<classifier>.tsv`), keep only rows whose `(groupId, artifactId)` is permitted (group-only line → any artifact in that group; pair line → that exact pair).
   - For `artifacts.tsv`: group the surviving rows by `version` (the Maven coordinate version). For each version, the row with the **oldest** `publishedAt` timestamp wins (lexicographic `groupId` ascending breaks any same-second tie). That row is the canonical resolution for that Maven version.
   - For `modules.tsv`: drop `automatic` rows (no `module-info` to consult), then drop every named row whose `module-info` version is non-empty AND semantically contradicts its Maven coordinate version. Semantic equality means `1.0` and `1.0-ga` are treated as equal (`Version.equals` performs the Maven version comparison, folding qualifier aliases and trailing-zero variants). The survivors are exactly the named rows where `module-info` either declared no version or declared one that agrees with its Maven coordinate. Group those by `moduleVersion` (the `module-info` version, falling back to the Maven version when none was declared) and, for each `moduleVersion`, keep the row with the **oldest** `publishedAt` (lexicographic `groupId` ascending breaks ties). Consequence: every `modules.tsv` row's `moduleVersion` agrees with its `mavenVersion` (modulo cosmetic Version aliases), so a lookup by either column lands on the same row.

2. **If no `owners.tsv` exists**, apply *first come, first served*:
   - Find the **implicit owner**: the `groupId` of the row in `versions.tsv` with the smallest `publishedAt` (lexicographic `groupId` ascending breaks ties).
   - Filter `versions.tsv` to rows whose `groupId` equals the implicit owner, then apply the per-view grouping above.

In both branches, `artifacts.tsv` is sorted version descending and `modules.tsv` is sorted `moduleVersion` descending; neither carries a `publishedAt` column (the resolution has already been made). Resolving "I need module `M` at Maven version `V`" reduces to "read `artifacts.tsv` and find the row whose first column equals `V`"; resolving "I need module `M` at module version `X`" reduces to "read `modules.tsv` and find the row whose first column equals `X`, then fetch the artifact from its `mavenVersion` column." When a file is missing or empty, the module has no resolved owner in that view (either nothing has been crawled yet, the policy filters out every claim, or, for `modules.tsv`, the resolved owner publishes only `automatic` modules).

The rule in one sentence: *the first party to publish a module name on Maven Central owns it until an operator says otherwise; the first publisher of a given module-info version owns that version forever.*

Why this is the right default:

- **Defends against module injection.** An attacker who registers a popular module name after the fact has, by construction, a later `publishedAt` than the canonical publisher and is excluded from both resolved views automatically.
- **Filters shaded redeclarations for free.** When `software.amazon.awssdk:third-party-jackson-core` ships a vendored copy of Jackson, its `publishedAt` is later than `com.fasterxml.jackson.core:jackson-core`'s. The shaded row never makes it into the resolved views; you do not have to maintain an exclusion list by hand.
- **Stable module-version mapping despite Maven not enforcing it.** Two unrelated Maven coordinates can both publish `module-info` with `version 2.0.10`. The oldest-publish-wins rule in `modules.tsv` pins the mapping to whichever coordinate landed first, so "module M at version X" keeps resolving to the same JAR across rebuilds.
- **Refuses self-contradicting rows.** A JAR published as Maven coordinate `2.0` while its `module-info` advertises `1.0` would otherwise let a consumer ask for module M at "1.0" and receive a JAR whose Maven coordinate disagrees. Such rows are filtered out of `modules.tsv` (the audit log keeps them). The filter runs *after* owner resolution, so an owner whose only rows are mismatching loses its `modules.tsv` entirely rather than silently handing the module to a runner-up groupId.
- **Composable with explicit policy.** Where the heuristic is wrong (legitimate ownership transfers like `org.jboss.netty` → `io.netty`, umbrella distributions that should coexist, internal forks), drop an `owners.tsv` next to the module and the explicit branch above takes over. The historical `versions.tsv` data stays intact, so loosening or reverting the policy later is a pure regeneration operation; no re-crawl needed.

Caveats to internalise:

- **Legitimate group renames.** If the original `groupId` is abandoned and a successor takes over, "first come first served" keeps awarding the abandoned `groupId`. Track and curate `owners.tsv` for known transfers.
- **Race-to-first on new names.** For module names that were *first* registered by a low-reputation publisher, the rule will hand ownership to them. The first-owner heuristic flips the *default*; it does not absolve a consumer of vetting genuinely new names.
- **Same-second ties.** Maven Central can publish many artifacts in a single staging batch with identical timestamps. The lexicographic `groupId` tiebreaker is arbitrary but stable; consumers must use exactly this rule (or pin via `owners.tsv`) to remain reproducible across machines.

#### `data/modules/<dotted/path>/owners.tsv` (optional allowlist)

If a module's directory contains an `owners.tsv` file, the resolver uses it as the authoritative allowlist when generating `artifacts.tsv` and `modules.tsv` for that module. `versions.tsv` itself is **not** filtered - it remains the full audit log of every claim that has ever been seen, including ones the policy now excludes. With no `owners.tsv` present, the implicit-owner rule (oldest publisher wins) is used instead. Either way, `artifacts.tsv` and `modules.tsv` are the resolved views consumers read.

Line format - one entry per line, tab-separated:

- `<groupId>` (no tab) — any artifactId under this groupId is allowed. Useful when you trust the entire publishing organisation.
- `<groupId>\t<artifactId>` — only this exact coordinate is allowed. Useful when you want to explicitly admit a single repackaged or vendored artifact alongside the canonical one.

Lines starting with `#` and blank lines are ignored.

Example - guard `com.fasterxml.jackson.core` so the resolved views only carry the canonical artifact plus the AWS third-party repackaging:

```
# canonical
com.fasterxml.jackson.core	jackson-core

# AWS bundles their own; we want to admit that into resolution too
software.amazon.awssdk	third-party-jackson-core
```

Changing the policy is non-destructive: edit `owners.tsv` and either let the next crawl regenerate the affected modules' resolved views, or run `SetOwners` (below) for an immediate refresh. The `versions.tsv` audit log is never touched, so loosening or reverting the policy later is a pure regeneration.

#### Bulk-applying an allowlist with `SetOwners`

`build.jenesis.crawler.SetOwners` is a standalone entry point that takes one or more `.properties` files and, for each listed module: writes its `owners.tsv` and rewrites the corresponding `versions.tsv` (plus every `versions-<classifier>.tsv`) to keep only rows whose `(groupId, artifactId)` matches the new allowlist. Versions files that end up empty are deleted.

```
java sources/build/jenesis/crawler/SetOwners.java <policy.properties> [<policy.properties> ...]
```

Optional system property: `-Djenesis.crawler.data=<dir>` (default `data`).

Properties syntax — one entry per line, `<module-name>=<comma-separated owners>`:

```
# canonical Jackson plus a known repackaging
com.fasterxml.jackson.core=com.fasterxml.jackson.core:jackson-core,software.amazon.awssdk:third-party-jackson-core

# trust everything from this group
org.junit.jupiter=org.junit.jupiter

# clear the module: empty owners.tsv, drop all version rows
com.example.deprecated=
```

Each owner is either `<groupId>` (any artifact in that group) or `<groupId>:<artifactId>` (exact pair). When the same module appears in more than one file, the union of owners is applied. An empty value clears the module: an empty `owners.tsv` is written and every existing version row is dropped.

#### Generating a starter properties file with `ListOwners`

The companion `build.jenesis.crawler.ListOwners` entry point emits a SetOwners-compatible properties file listing the *current* owners (from `owners.tsv` when present, otherwise derived from `artifacts[-<classifier>].tsv`) for every module whose dotted name matches one of the supplied globs.

```
java sources/build/jenesis/crawler/ListOwners.java <glob> [<glob> ...]
```

Glob semantics mirror module-name structure: `*` matches one segment, `**` matches across dots. `net.bytebuddy.*` matches `net.bytebuddy.agent` but not `net.bytebuddy.agent.builder`; use `net.bytebuddy.**` for the latter. Output is always written to stdout - redirect or pipe as needed.

Optional system properties:

| Property | Default | Effect |
|---|---|---|
| `jenesis.crawler.data` | `data` | Data directory to read from. |
| `jenesis.crawler.list.group.only` | `true` | When deriving owners from `artifacts.tsv` (no `owners.tsv`), emit groupIds only. Set to `false` to emit `groupId:artifactId` pairs. |
| `jenesis.crawler.list.only.missing.owners` | `false` | Skip modules that already have an `owners.tsv`. Useful to focus on modules that still need a policy. |
| `jenesis.crawler.list.only.ambiguous` | `false` | Keep only modules whose computed owners list has more than one entry. Counted after the `group.only` dedup, so the granularity matches the configured output. Handy for surfacing collisions. |

Typical workflow:

```
# 1. snapshot the current owners for a family (group-level by default)
java sources/build/jenesis/crawler/ListOwners.java 'net.bytebuddy.**' > policy.properties

# 2. edit policy.properties to remove unwanted entries

# 3. apply the curated policy
java sources/build/jenesis/crawler/SetOwners.java policy.properties

# Audit-only: only modules where ownership is ambiguous, with full granularity
java -Djenesis.crawler.list.group.only=false \
     -Djenesis.crawler.list.only.ambiguous=true \
     sources/build/jenesis/crawler/ListOwners.java '**'
```

When owners are read from `owners.tsv`, lines are emitted verbatim (the file's tab is rendered as `:`); the `group.only` flag only affects values derived from `artifacts.tsv`.

### `data/scanned/<dotted/path>/<artifactId>.tsv`

For every artifact we have ever fetched, an `<artifactId>.tsv` file under the artifact's group path lists every `(version, classifier)` the crawler has looked at. Three tab-separated columns: `version`, `classifier-or-empty`, `errorMessage-or-empty`. The artifactId is in the file name and not repeated on every row. Used internally to skip coordinates on subsequent runs - Maven Central is immutable per GAV, so once a JAR has been scanned we never need to look at it again.

This layout is per-artifact rather than per-group so that loading "the set of already-scanned coordinates for the artifact I'm about to scan" reads at most a few thousand rows (one artifact's release history) instead of "everything in this Maven group ever," which on prolific groups like `software.amazon.awssdk` (~470 K coordinates) used to pull 175 MB into the heap on every `contains` lookup. Per-artifact files keep each lookup at a few hundred KB.

The third column distinguishes two kinds of completion:

- **Empty** — the scan succeeded (the artifact either yielded a module declaration that landed in `versions.tsv`, or carried no module name at all and is intentionally not in the modules index).
- **Non-empty** — the scan failed *permanently* (the JAR is malformed, or the artifact returned HTTP 404/410). The text is the recorded error message, sanitised so it stays on a single TSV line. Future runs skip these coordinates by default, exactly as they skip successful ones; the artifact is not refetched. To retry every recorded permanent failure on the next run, set `-Djenesis.crawler.reprocess.failed=true` (default `false`). Transient failures (network timeouts, HTTP 5xx, etc.) are *not* recorded here - they remain unmarked, so the next run retries them as a matter of course.

## Jenesis Module Repository

The contents of `data/modules/` are published as the **Jenesis Module Repository**: an HTTP service that resolves Java module names (plus an optional version and classifier) to the underlying Maven Central JAR via a 302 redirect. The repository's contract is a small, stable set of URL shapes: anything that can `curl -L` is a client. The reference implementation lives in `worker/index.js` (a Cloudflare Worker), and the canonical deployment is at <https://repo.jenesis.build/>. The contract itself is just "read the right TSV from `data/modules/`, pick a row, redirect."

The repository is intentionally a thin wrapper over the resolved views: every redirect target is derivable from a single row of `artifacts.tsv` or `modules.tsv`. Consumers that prefer to do their own lookup can read the TSVs directly via `raw.githubusercontent.com` (or any mirror); the worker exists so a Maven-style `<repository>` URL can be plugged in without writing a resolver.

### URL shapes

Four modes, distinguished by the path segment immediately before the module name:

| Mode | URL shape | TSV consulted | Version segment is... |
|---|---|---|---|
| `artifact` | `/artifact/<moduleName>[/<mavenVersion>]/<filename>` | `artifacts[-<classifier>].tsv` | The **Maven coordinate version**. Transparent Maven proxy: the request extension passes through verbatim. |
| `module` | `/module/<moduleName>[/<moduleVersion>]/<filename>.jar` | `modules[-<classifier>].tsv` | The **module-info version** (publisher-declared, falls back to the Maven version when none was declared). |
| `sources` | `/sources/<moduleName>[/<moduleVersion>]/<filename>.jar` | `modules[-<classifier>].tsv` | The **module-info version**. The redirect target appends `-sources` to the Maven filename. |
| `documentation` | `/documentation/<moduleName>[/<moduleVersion>]/<filename>.jar` | `modules[-<classifier>].tsv` | The **module-info version**. The redirect target appends `-javadoc` to the Maven filename. |

The version segment is optional in every mode: omitting it picks the first row of the TSV, which (because both files are sorted descending) is the highest version. With the segment present, the worker returns the row whose first column equals the segment exactly (no semantic-version range matching, no normalisation).

`<filename>` is the trailing path segment. The module-name segment immediately before the filename must equal the module-name prefix inside the filename; anything that follows (after a `.` or after `-<classifier>.`) is the extension. The classifier (everything between the first hyphen and the next dot) flips the lookup to the matching classifier-scoped TSV: `<base>-<classifier>.tsv` where `<base>` is `artifacts` or `modules`, and it also becomes the standard Maven classifier on the resulting filename.

In `artifact` mode the extension is opaque: whatever the client puts after the module name (and optional `-<classifier>`) becomes the suffix of the Maven filename. So `<moduleName>.jar`, `<moduleName>.pom`, `<moduleName>.pom.sha256`, `<moduleName>.module`, `<moduleName>-<c>.jar`, `<moduleName>-<c>.pom.sha512`, and so on all translate to the corresponding Maven file by appending the same suffix to `<artifactId>-<version>[-<classifier>]`. This makes the `/artifact/` route a drop-in Maven repository URL.

The `/module/`, `/sources/`, and `/documentation/` routes only accept `.jar` filenames.

Any number of path segments before the mode marker are ignored, so the same worker can be deployed under `/`, `/mod/`, `/jenesis/v1/`, or any other mount prefix without configuration.

### Examples

```
# Latest Maven version of org.slf4j (the Maven-version view)
GET /artifact/org.slf4j/org.slf4j.jar
→ 302 https://repo.maven.apache.org/maven2/org/slf4j/slf4j-api/2.0.10/slf4j-api-2.0.10.jar

# POM of a specific Maven version, transparent passthrough
GET /artifact/org.slf4j/2.0.9/org.slf4j.pom
→ 302 https://repo.maven.apache.org/maven2/org/slf4j/slf4j-api/2.0.9/slf4j-api-2.0.9.pom

# Checksum and signature files follow the same pattern
GET /artifact/org.slf4j/2.0.9/org.slf4j.pom.sha256
→ 302 https://repo.maven.apache.org/maven2/org/slf4j/slf4j-api/2.0.9/slf4j-api-2.0.9.pom.sha256

# Gradle module metadata if the publisher provides it
GET /artifact/org.slf4j/2.0.9/org.slf4j.module
→ 302 https://repo.maven.apache.org/maven2/org/slf4j/slf4j-api/2.0.9/slf4j-api-2.0.9.module

# Resolved by module-info version (the same artifact in this case, but the lookup key is different)
GET /module/org.slf4j/2.0.9/org.slf4j.jar
→ 302 https://repo.maven.apache.org/maven2/org/slf4j/slf4j-api/2.0.9/slf4j-api-2.0.9.jar

# Sources and javadoc JARs for a module-info version
GET /sources/org.slf4j/2.0.9/org.slf4j.jar
→ 302 https://repo.maven.apache.org/maven2/org/slf4j/slf4j-api/2.0.9/slf4j-api-2.0.9-sources.jar
GET /documentation/org.slf4j/2.0.9/org.slf4j.jar
→ 302 https://repo.maven.apache.org/maven2/org/slf4j/slf4j-api/2.0.9/slf4j-api-2.0.9-javadoc.jar

# Classifier flips the lookup to the matching classifier-scoped TSV and survives the extension passthrough
GET /artifact/com.fasterxml.jackson.core/com.fasterxml.jackson.core-no_aopalliance.pom
→ reads artifacts-no_aopalliance.tsv, redirects to <artifactId>-<version>-no_aopalliance.pom
```

Successful responses are HTTP `302` with `Location` pointing at the Maven URL and `Cache-Control: public, max-age=<REDIRECT_TTL>, stale-while-revalidate=86400`. The body is empty.

### Response headers

Every redirect carries the resolved coordinate as response headers so a client can record what it actually fetched without parsing the `Location`:

| Header | Always present | Value |
|---|---|---|
| `X-Jenesis-GroupId` | yes | Maven `groupId` of the resolved row. |
| `X-Jenesis-ArtifactId` | yes | Maven `artifactId`. |
| `X-Jenesis-MavenVersion` | yes | Maven coordinate version. |
| `X-Jenesis-ModuleVersion` | only on `/module/`, `/sources/`, `/documentation/` | The publisher-declared `module-info` version (column 1 of `modules.tsv`). Omitted in `artifact` mode where the lookup key is the Maven version itself. |

### Failure modes

| Status | When |
|---|---|
| `404` | The path doesn't fit a supported shape (unknown mode marker, module-name segment doesn't match the filename prefix, filename has no extension after `<moduleName>` or `<moduleName>-<classifier>`, `.jar`-only mode received a non-`.jar` extension, version segment present but the TSV row doesn't exist, or the TSV itself is absent because the module has no resolved owner in the requested view). The response body names the missing module / version. |
| `405` | The request is not `GET` or `HEAD`. |
| `502` | The upstream TSV fetch returned a non-200 status other than 404 (e.g. the data origin is temporarily unhealthy). |

### Stability guarantee

The repository's promise is that **`(moduleName, moduleVersion)` always resolves to the same Maven artifact, and that artifact's Maven version is the same number as the `moduleVersion`**. The promise rests on two pieces of the resolution algorithm: (1) rows whose `module-info` version contradicts the delivering Maven coordinate are filtered out of `modules.tsv` entirely; (2) of the survivors, the row with the oldest `publishedAt` wins each `moduleVersion` bucket, so a future publisher who happens to declare the same `moduleVersion` is recorded in the audit log but does not displace the existing row. Consumers can pin a module-version in their build and trust that subsequent rebuilds resolve to the same JAR with a matching Maven coordinate, even though Maven itself doesn't enforce unique module versions.

The `(moduleName, mavenVersion)` lookup in `/artifact/` is similarly stable because Maven coordinates themselves are immutable on Central; the only way the resolution shifts is if an operator changes `owners.tsv` to admit a different publisher for the same `(name, mavenVersion)` pair.

### Configuration

The worker reads three optional environment variables; defaults shown:

| Variable | Default | Purpose |
|---|---|---|
| `DATA_BASE` | `https://raw.githubusercontent.com/raphw/jenesis-modules/main/data/modules/` | Base URL the worker fetches `artifacts[-<classifier>].tsv` and `modules[-<classifier>].tsv` from. Point this at a fork or a mirror to serve a different catalogue. |
| `ARTIFACT_BASE` | `https://repo.maven.apache.org/maven2/` | Base URL the 302 redirects target. Point this at a Maven mirror or proxy. |
| `REDIRECT_TTL` | `3600` (seconds) | `Cache-Control: max-age` on the 302 response. Cloudflare's edge cache also caches the upstream TSV fetches at the same TTL. |

## Running the crawler

Requires Java 25 or newer. The crawler is launched with the JDK's multi-file source-code mode. No build step is required - the JDK compiles the sources on demand:

```
java sources/build/jenesis/crawler/Crawl.java <artifact-base-uri> [<index-base-uri>]
```

Both URIs are required input - the tool itself has no built-in default. When `<index-base-uri>` is omitted, the same URI is used for both the JAR fetches and the Lucene index. For Maven Central, the typical pairing is:

```
java sources/build/jenesis/crawler/Crawl.java \
     https://maven-central.storage-download.googleapis.com/maven2/ \
     https://repo.maven.apache.org/maven2/.index/
```

The artifact URI points at a fast range-supporting mirror (the GCS bucket above), while the index URI points at the canonical Lucene index location. Keeping both as caller-supplied means the same code drives both production crawls and tests against a local mock.

For a quick local smoke run with a tiny budget:

```
java -Djenesis.crawler.data=smoke-data \
     -Djenesis.crawler.budget=3 \
     sources/build/jenesis/crawler/Crawl.java \
     https://maven-central.storage-download.googleapis.com/maven2/ \
     https://repo.maven.apache.org/maven2/.index/
```

On a first run the crawler streams the full Maven Central index while the scanner is already consuming coordinates from the queue, so artifact scanning starts within the first second or two. The 3-minute budget governs wall-clock time spent in the scan loop; when it expires the crawler exits cleanly, leaving everything under `data/` in a consistent state.

Optional system properties:

| Property | Default | Effect |
|---|---|---|
| `jenesis.crawler.data` | `data` | Where state, module files, and the scanned-index live. |
| `jenesis.crawler.budget` | `180` | Wall-clock budget for this run, in minutes. |
| `jenesis.crawler.concurrency` | `64` | Maximum in-flight artifact fetches. Each in-flight scan can pull a multi-MB jar through the synchronous `HttpURLConnection` path, so the cap on simultaneous fetches directly bounds peak receive-buffer memory. 64 was settled on after a full sweep at 32 stayed near ~450 MB of a 4 GB heap, leaving comfortable headroom. The historical 96 default put the heap close to the worst-case ceiling when uberjars clustered in a batch, so step further (e.g. 96, 128) only with continued heap monitoring. |
| `jenesis.crawler.tail.size` | `65536` | Bytes range-fetched from the end of each JAR. |
| `jenesis.crawler.small.jar.threshold` | `262144` | JAR size at or below which we fetch the whole file in one request, falling back to the cached-tail path on any failure. |
| `jenesis.crawler.checkpoint.every` | `2000` | Coordinates between on-disk checkpoints. |
| `jenesis.crawler.scanned.cache.size` | `4096` | LRU capacity for `ScannedStore`'s per-artifact set cache. The producer's `contains` and the scanner's `markOk`/`markFailed` both look up by `(groupId, artifactId)`; the cap keeps in-memory `ScannedEntry`s bounded across a full ~90 M-record index sweep. Dirty entries (unflushed marks) are exempt from eviction, so the actual size can transiently exceed the cap between checkpoints. See [Bounding heap usage on long runs](#bounding-heap-usage-on-long-runs). |
| `jenesis.crawler.resume` | `true` | When `false`, deletes `state.properties` before starting, so the next run begins a fresh streaming sync of the full index. `data/scanned/` and `data/modules/` are preserved, so already-scanned coordinates are still skipped. |
| `jenesis.crawler.reprocess.failed` | `false` | When `true`, coordinates whose previous scan ended in a permanent failure (malformed JAR, HTTP 404/410) are treated as un-scanned and re-fetched on this run. Useful after a scanner bug fix; leave at `false` for normal operation so chronically broken artifacts are not refetched on every run. |
| `jenesis.crawler.allow.rebaseline` | `false` | When the crawler falls behind Central's incremental retention window (~30 entries) the next incremental fetch 404s. By default the crawler fails fast and points at this property. Set `true` to reset the index baseline and re-FULL on the next iteration; `data/` is preserved, and `ScannedStore` short-circuits every already-scanned coordinate, so the recovery sweep is fast. |
| `jenesis.crawler.canonical.timestamp.uri` | same as `<artifact-base-uri>` | Maven-repo base used to HEAD the canonical `Last-Modified` when the primary artifact fetch comes from a mirror that rewrites mtimes. Defaults to the primary artifact source (which disables the fallback because the upgrade pass short-circuits when the two URIs match). The scheduled workflow sets this to `https://repo.maven.apache.org/maven2/` so the GCS mirror's pre-2019 bulk-import timestamps are upgraded to canonical values. See [Sourcing `publishedAt`](#sourcing-publishedat) below. |
| `jenesis.crawler.git.publish` | `false` | When `true`, commit + push checkpoints inline. |
| `jenesis.crawler.git.work.dir` | `.` | Working tree for the publishing commits. |
| `jenesis.crawler.git.push.every` | `1` | Push every N checkpoints. |

## Companion tools

Four standalone main classes complement the crawler. The first three share its scanner pipeline (skipping the index streamer); the fourth, `Regenerate`, only touches the resolved TSVs and needs no scanner at all.

### `build.jenesis.crawler.RetryFailed`

Re-scans coordinates currently recorded as a permanent failure in `data/scanned/`, optionally narrowed by a regex match against the recorded error message. Reuses the regular crawler's `Scanner`, `ModuleStore` flush invariant, checkpointing, and git-publisher pipeline; index chain state in `state.properties` is not touched, and no 24-minute producer warmup is incurred.

```
java -cp <jar> build.jenesis.crawler.RetryFailed <artifact-base-uri>
```

By default the tool **skips entries whose error message contains `returned status 404`**. The Coordinate rewrite (see "How the crawl works" below) produces an unavoidable long tail of 404s for pom-only artifacts whose `pom.sha512`-style record was speculatively treated as a main JAR; retrying those just re-fetches and re-records the same 404. Set `jenesis.retry.error.pattern` to bypass the default skip and target a specific class (the pattern wins, including a pattern that matches the 404 message itself).

Properties:

| Property | Default | Effect |
|---|---|---|
| `jenesis.retry.error.pattern` | (unset, retry every non-404 failure) | Regex matched against recorded error messages via `Matcher.find()`. Substring match, no anchoring needed. Setting this property bypasses the default 404 skip. |
| `jenesis.crawler.data` | `data` | Crawler data directory. |
| `jenesis.crawler.budget`, `concurrency`, `tail.size`, `small.jar.threshold`, `checkpoint.every`, `git.publish`, `git.work.dir`, `git.push.every` | see crawler defaults | All share the keys used by `Crawl`. |

The crawler's `reprocessFailed` flag is forced to `true` internally so an existing failure record doesn't block its own re-scan via the consumer-side `scannedStore.contains` check.

Use cases:
- After a scanner-side fix (e.g. broadening the permanent-failure classifier or adding a new tail-size fallback), retry the affected category to recover JARs that were previously marked failed:
  ```
  java -Djenesis.retry.error.pattern='supplied tail buffer' \
       -cp <jar> build.jenesis.crawler.RetryFailed \
       https://maven-central.storage-download.googleapis.com/maven2/
  ```
- One-shot retry of every non-404 failure after a long quiet period:
  ```
  java -cp <jar> build.jenesis.crawler.RetryFailed \
       https://maven-central.storage-download.googleapis.com/maven2/
  ```

### `build.jenesis.crawler.ReconcileMetadata`

Recovers versions that are missing from the Maven Central Nexus index entirely. Walks `data/scanned/`, downloads `maven-metadata.xml` for each `(groupId, artifactId)` pair, diffs the version list against locally-scanned main-jar versions, and pipes the missing versions through the regular scanner pipeline. Uses the same `Scanner` / `ModuleStore` / checkpointing / `GitPublisher` machinery as the crawler; index chain state is not touched.

```
java -cp <jar> build.jenesis.crawler.ReconcileMetadata <artifact-base-uri>
```

The crawler-with-fixes already recovers most of the upstream-indexer bugs (Gradle-`.module` mis-stamps, POM-checksum mis-stamps), but two cases still fall through:

- **Versions absent from the index entirely.** Maven Central's `nexus-maven-repository-index.gz` is regenerated periodically; brand-new releases and certain mis-published artifacts can be missing for days or longer. `maven-metadata.xml`, by contrast, is authoritative immediately.
- **Cases the rewrite skips on purpose.** The crawler only rewrites `<none>/module` to `<none>/jar`; it doesn't touch `<none>/pom.sha*` records to keep the 404 noise bounded. `ReconcileMetadata` is the path that recovers them when you want completeness.

Properties:

| Property | Default | Effect |
|---|---|---|
| `jenesis.reconcile.metadata.concurrency` | `32` | Concurrent `maven-metadata.xml` HEAD fetches. The XML files are small; raising this is cheap until the mirror starts throttling. |
| `jenesis.reconcile.batch.size` | `256` | Coordinates per scanner batch. Higher means longer checkpoint intervals. |
| `jenesis.crawler.data`, `budget`, `concurrency`, `tail.size`, `small.jar.threshold`, `checkpoint.every`, `git.publish`, `git.work.dir`, `git.push.every` | see crawler defaults | All share the keys used by `Crawl`. |

The corresponding `reconcile-metadata.yml` workflow exposes the same knobs as manual `workflow_dispatch` inputs. It shares the `crawl` concurrency group so a manual reconcile queues behind a manual crawl rather than fighting for the same `versions.tsv` files; scheduled crawls use a per-run group so they may run concurrently, with `GitPublisher`'s rebase-retry handling any collision.

### `build.jenesis.crawler.ModuleSummary`

Walks `data/modules/` (`versions.tsv` + `artifacts.tsv`) and `data/scanned/` (for failure stats) and writes a human-readable markdown summary atomically to `data/SUMMARY.md`. Regenerated on every invocation; the previous file is overwritten.

```
java -cp <jar> build.jenesis.crawler.ModuleSummary
```

**Most row-level metrics in the summary are filtered to rows that appear in `artifacts.tsv`** (the resolved view after `owners.tsv` policy), so shading-injected audit rows don't inflate the picture. The exceptions are explicitly labelled as "audit" or "history" tables (collisions, distinct-groupIds-per-module, naming-patterns histogram). The header text at the top of the Totals section spells out the convention.

The summary covers:

- **Totals**: total artifacts scanned, non-module artifacts, modular artifacts, total named modules, total automatic modules, total named modules with module-info version, distinct Maven artifacts, distinct module names, distinct named/automatic modules (latest-type from `artifacts.tsv`), distinct named modules with module-info version, distinct groupIds, most recent tracked publication.
- **Resolved catalogue size**: total rows across every `modules[-<classifier>].tsv` (the size of the module-version view after owner resolution and the version-mismatch filter).
- **Type breakdown** (named / automatic): distinct modules + total rows from each `artifacts[-<classifier>].tsv`.
- **`module-info` version field across named publications** (canonical, no-classifier rows only — classifier-keyed fat-jar / shaded rows are excluded because their bundled `module-info` is expected to disagree with the bundling Maven coordinate): three tables. (a) Canonical named publications by whether `module-info` matched / mismatched / was absent. (b) The same breakdown counted once per canonical module against its latest named row. (c) **Module version filtering impact**: how many canonical modules the version-mismatch filter leaves untouched, shrinks, removes entirely, or shifts to an older "latest".
- **Mismatching module-info version patterns**: breakdown of the mismatching bucket by *why* the versions differ — `-SNAPSHOT` left on release, repackager `-<suffix>`, segment-count drift, `+<metadata>` build labels, unresolved `${...}` placeholders, different first dot-segment (likely shaded/bundled), substantively different. Each row carries a percent share.
- **Type transitions** (automatic → named, named → automatic) computed from each module's resolved view.
- **Recent activity (last 7 days)**: modules with a publication + new version rows, each split into total / named / automatic columns.
- **Monthly publications by type (last 12 months)**: per-month named vs automatic counts with inline ASCII bars scaled to the maximum.
- **Naming patterns**: classifier-variant counts and modules with multiple competing groupIds across the audit log, plus a subsection (`Leading dot-segments shared with the owning groupId`) that distributes canonical modules across how many leading dot-segments their name shares with the owning groupId (empty buckets render as `-`).
- **Processing errors**: total failed coordinates plus the top-N most common recorded error messages. The normaliser collapses well-known variants — URLs, package names, classfile entry indexes, line numbers, file paths — into placeholders like `<URL>`, `<PACKAGE>`, `<CLASS>` so they aggregate into a single row per error class.
- **Top-N tables**: modules by version count (with module-family folds like `software.amazon.awssdk.*` / `org.scala.lang.scala3.*` / `com.fasterxml.jackson.*` collapsed into a single row when they otherwise dominate), groupIds by module count, modules with most colliding groupIds (audit), modules updated in the last 7 days, groupIds by average versions per module (filtered to groups with ≥ 3 modules).

Properties:

| Property | Default | Effect |
|---|---|---|
| `jenesis.crawler.data` | `data` | Crawler data directory. |
| `jenesis.summary.output` | `<data>/SUMMARY.md` | Output file path. |
| `jenesis.summary.top.n` | `25` | Row count for every top-N table and the error-message list. Must be ≥ 1. |

All integer counts in the markdown output are rendered with regular ASCII space as the thousands separator (e.g. `1 118 706`). Timestamps render as `yyyy-MM-dd HH:mm:ss UTC` (no `T` separator) for readability.

### `build.jenesis.crawler.Regenerate`

Walks `data/modules/` and rebuilds `artifacts[-<classifier>].tsv` and/or `modules[-<classifier>].tsv` for matching modules from their `versions[-<classifier>].tsv` contents. Intended for rolling an algorithm change (e.g. the version-mismatch filter described under "Resolution algorithm") across the existing catalogue without re-fetching any JARs. Owners (implicit via oldest `publishedAt`, or explicit via `owners.tsv`) are recomputed from `versions.tsv` on every run.

```
java -cp <jar> build.jenesis.crawler.Regenerate [<glob> ...]
```

Globs follow the same module-name structure as `ListOwners`: `*` matches one segment, `**` matches across dots. Omit globs to regenerate every module.

Properties:

| Property | Default | Effect |
|---|---|---|
| `jenesis.crawler.data` | `data` | Crawler data directory. |
| `jenesis.crawler.regenerate.scope` | `both` | `both`, `artifacts`, or `modules`. The omitted family is left exactly as it was on disk (no read, no write, no delete). |
| `jenesis.crawler.regenerate.dry.run` | `false` | When `true`, print the module names that would be regenerated to stdout and exit without writing. Counts also go to stderr. |

The unit of progress is the module: each one is regenerated atomically (temp-file + rename), so a crash leaves the catalogue in a fully recoverable state.

## How the crawl works

1. Fetch `nexus-maven-repository-index.properties` from Maven Central to learn the current chain id and last incremental chunk number.
2. Decide sync mode:
   - **Full**: first run, or the chain id has rotated. Stream the full Lucene index.
   - **Incremental**: chain id unchanged and there are new chunks. Stream only the new incremental chunks.
   - **Up to date**: nothing new published. Exit immediately.
3. The producer reads the index and emits filtered coordinates onto a bounded queue. Two filters run at the producer, plus one parse-time rewrite:
   - **Coordinate rewrite** (`Coordinate.from`): the Nexus indexer (OSSRH-60950) occasionally writes main-jar records with the extension of a sidecar file (`module`, `pom.sha256`, `pom.sha512`, `pom.asc.sha256`, `pom.asc.sha512`). When `classifier == null` and the extension matches one of these, we rewrite it to `jar` so the rest of the pipeline treats the record as the main JAR it was supposed to be. The trade-off: for legitimate pom-only artifacts (BOMs, parent POMs) the rewritten record points at a non-existent JAR and the fetch 404s. Those 404s land in `scanned.tsv` as permanent failures (so `ScannedStore` dedupes on future runs), get counted in the `[artifacts]` log under `notFound=N` separately from real `failed=N`, and don't generate per-coordinate stderr noise. The cost is bounded; the upside is that no mis-stamped main JAR is silently dropped. Once Sonatype rotates to a healthy index the rewrite triggers on zero records and the noise vanishes.
   - **Extension**: only `jar` artifacts (post-rewrite), dropping `sources`, `javadoc`, `tests`, etc. classifiers.
   - **Already scanned**: the in-memory `ScannedStore` (loaded from `data/scanned/`) rejects coordinates we've seen before, so those JARs are never fetched again.

   The queue is purely in-memory. The producer blocks on `queue.put` whenever the queue is full, so it can never outrun the scanner. Nothing about the producer's state is written to disk - the only durable record of "which coordinates have been processed" is `data/scanned/`.
4. The scanner consumes from the queue concurrently. For each coordinate it either fetches the whole small JAR in one ranged GET, or fetches the central-directory tail and then ranges the specific entry it needs. Detection order:
   1. `module-info.class` at the JAR root → `named`.
   2. Highest-version `META-INF/versions/<N>/module-info.class` (multi-release JARs) → `named`.
   3. `META-INF/MANIFEST.MF` with `Automatic-Module-Name` → `automatic`.
   4. Otherwise no record is written.
5. On every checkpoint (default every 2000 coordinates): flush module entries into the `versions.tsv` audit log, update the scanned-coordinate index, save `state.properties`, rewrite `STATUS.md`, and (when `-Djenesis.crawler.git.publish=true`) commit + push. **After the first FULL pass has completed and a baseline exists** (`state.indexChunkLastApplied >= 0`), each module whose `versions.tsv` was touched in this sweep is also recorded in `data/dirty-modules.tsv` so stage 2 below knows what to regenerate. During the first FULL pass itself the dirty list is suppressed — a first sweep touches ~every module in Maven Central, so writing a per-module marker would balloon the file for no benefit (stage 2 handles the first pass wholesale, below).
6. When the producer reaches end-of-stream and the queue has fully drained, the chunk is considered complete and the index chain watermark advances. On budget-truncated runs the watermark does *not* advance; the next run re-streams the same chunk from scratch and the scanned-coordinate filter discards everything already processed, so only the unscanned tail does real work.

   **Post-FULL watermark.** After a successful FULL pass, the watermark is *not* set to `remote.lastIncremental()` directly — that would skip the retained incremental chunks Sonatype publishes between FULL regenerations. Maven Central's `nexus-maven-repository-index.gz` lags behind the most recent incremental: chunks newer than the FULL snapshot point contain records that aren't yet in the FULL file. Instead, the watermark parks at `firstRetained - 1` (the oldest retained incremental, taken from the `.properties` file's `incremental-N` keys), so the next run sweeps every chunk Sonatype still serves. `ScannedStore` dedupes the overlap with what the FULL already covered, so the cost is mostly index streaming with little re-fetching. The fake test server publishes only `last-incremental` (no retention listing), so in that path `firstRetained == lastIncremental` and the watermark advances directly.

**Stage 2 (resolve).** Only after the chunk's queue has fully drained in a run does the crawler regenerate the resolved views (`artifacts.tsv` and `modules.tsv`). Deferring this avoids publishing a freshly-seen `(groupId, artifactId)` as the implicit owner of a module name when an *older* publisher of the same name might still be queued in the producer's in-flight set (a real concern on first-pass full syncs). Two flows:

- **First pass** (no baseline yet, so dirty tracking was suppressed during stage 1): the crawler walks the modules tree and calls `regenerate(moduleName)` for every directory that doesn't already have an `artifacts*.tsv` file. Each `regenerate` writes both `artifacts.tsv` and, when the resolved owner has any `named` rows, `modules.tsv`. The presence of `artifacts.tsv` is the per-module progress marker; a crash mid-walk leaves baseline unset, the next run re-enters the first-pass branch, the sweep re-runs quickly (`ScannedStore` short-circuits every already-scanned coordinate), and the walk resumes from the directories still missing an `artifacts.tsv`. After the walk finishes the baseline is saved.
- **Subsequent passes** (baseline established, sweeps small): regenerate both resolved views for each module in `data/dirty-modules.tsv`. Each `regenerate(moduleName)` reads `versions.tsv` (and any `versions-<classifier>.tsv`) plus the optional `owners.tsv`, writes `artifacts.tsv` (and `modules.tsv` when applicable) atomically, then drops the module from `data/dirty-modules.tsv`. A crash mid-drain simply leaves remaining entries in the dirty list; the next run drains them before doing anything else.

A crash mid-stage-1 leaves stage 2 pending, but the next run picks the same chunk (the chain watermark hasn't advanced) and re-streams it before running stage 2 - so the resolved views are never built from partial data.

A run stops when its wall-clock budget expires or the chunk's queue drains cleanly. On the next run the producer re-streams the index from scratch; the scanned-coordinate filter skips every coordinate already processed, so only the unscanned tail is enqueued for scanning. Re-reading the full main index is roughly 24 minutes of CPU-bound gzip + Lucene-record parsing (see the table below); incremental chunks are orders of magnitude smaller and stream in seconds.

### Maven Central main-index characteristics (measured)

Measured against `https://repo.maven.apache.org/maven2/.index/nexus-maven-repository-index.gz` from a workstation on 2026-05-24, with a reject-all filter (i.e. read + parse only, no emit, no scan):

| Field | Value |
|---|---|
| Compressed size on the wire | 3,020,924,008 bytes (≈ 2.81 GiB) |
| Content-Type | `application/x-gzip` |
| Raw record count (all entries, pre-filter) | ~101,072,558 |
| Wall-clock to fully read + parse | ~24 min 15 sec (≈ 1,455 s) |
| Sustained records-per-second | ~70,000 (CPU-bound on gzip + Lucene record parse) |
| Sustained compressed throughput | ~2.1 MB/s |
| Records reaching the producer's filter | a few million (only `.jar` artifacts in non-skipped classifiers) |

Practical consequences:
- During the initial FULL sweep, every resumed run pays ~24 minutes of producer warmup before any new scanning happens — about 27% of a 90-minute budget. The producer can't be made faster without changing the index format (gzip + Lucene parsing is the floor).
- Once the FULL completes (`state.indexChunkLastApplied >= 0`), subsequent runs pick INCREMENTAL chunks of typically thousands of records each, which stream in seconds. The warmup essentially disappears.
- The full re-stream cost reappears only when the index `chainId` rotates upstream, or when the crawler falls off Central's ~30-chunk incremental retention window and rebaselines.

### Scanner throughput (measured)

Producer warmup is bounded and well-known. The actual long pole of a sweep is the scanner: range-fetching each JAR's central-directory tail, parsing it, and (for modular JARs) ranging the `module-info.class` entry. Numbers observed on a `ubuntu-latest` GitHub Actions runner against the GCS download mirror, `concurrency=64`:

| Field | Value |
|---|---|
| Sustained scanner throughput | ~70 coordinates/sec |
| Per-worker throughput | ~1.1 coordinates/sec |
| Median HTTP requests per coordinate | 1 (cached tail) to 2-3 (large JAR with separate `module-info.class` range fetch) |
| Heap envelope | sawtooth between ~20 MB post-GC and ~1.2 GB pre-GC, of 4 GB available |
| Permanent-failure rate (intrinsic to the artifact: malformed ZIP, invalid `module-info`, HTTP 404 on phantom snapshots) | well under 0.1% of scanned coordinates |

Practical consequences:
- For a fresh first sweep (~1.5M unscanned coordinates) the scanner needs roughly `1.5M / 70 / 3600 ≈ 6 hours` of pure wall clock to drain, split across however many runs the 90-minute budget per scheduled run lets you fit. Producer warmup is paid once per run on top of that.
- Steady-state (post-baseline) runs only scan whatever appeared in the incremental chunks: typically thousands of JARs, finishing in minutes.
- The scanner is HTTP-latency-bound, not bandwidth-bound: most of the per-worker time is round-trip to Central, not byte-pushing. Raising `concurrency` past 64 buys more throughput up to roughly the runner's bandwidth ceiling; we found 64 a comfortable safe-default that fits inside the 4 GB heap on the standard Actions runner even when a large uberjar batch clusters.
- Stage 2 (`regenerateMissingForFirstPass()` after the very first FULL sweep completes) walks the modules tree and writes both resolved views per module (`artifacts[-<classifier>].tsv` plus `modules[-<classifier>].tsv` when any `named` row survives): at ~170K modules with ~10ms per write on persistent disk, it's typically 10-30 minutes. Incremental drainage of `dirty-modules.tsv` in subsequent runs is seconds.

### Failure classification

When `scanOne` returns an error, `Crawler.isPermanentFailure` decides whether to write a row into `data/scanned/` (preventing re-scan on every future run) or leave the coordinate unmarked (next run retries). Permanent triggers:

- Any `IllegalArgumentException` in the cause chain — the scanner raises this for ZIP central-directory corruption it can't recover from.
- Any `java.lang.module.InvalidModuleDescriptorException` in the cause chain — `module-info.class` is malformed (a common shaded-uberjar bug: the bundled module-info wasn't relocated along with its classes).
- Any `java.util.zip.ZipException` in the cause chain — the JAR itself is malformed.
- HTTP status `404` or `410` from the artifact fetch — Central's index references an artifact that isn't actually present (snapshot/alpha coordinates, unresolved `${revision}` placeholders, etc.).
- A message-substring match against any entry in `Crawler.PERMANENT_MESSAGE_FRAGMENTS`. This list is grown from observation as new recurring intrinsic-failure messages are found that the JDK surfaces as plain `IOException`. The seeded entry is `"invalid header field"` (`java.util.jar.Manifest.parse` choking on a malformed `MANIFEST.MF`).

Everything else (HTTP 5xx, timeouts, generic `IOException` without a known fragment) stays transient and is retried automatically on the next run.

**Tail-too-small recovery.** When the central directory is bigger than the default `jenesis.crawler.tail.size = 65 536 bytes` (typical for shaded uberjars with thousands of entries), the CD parser throws `IllegalArgumentException` with a message containing `"supplied tail buffer"` or `"Expected central file header signature at offset 0"`. Before recording such a coordinate as permanent, `scanOne` retries once with `EXPANDED_TAIL_RETRY_BYTES = 4 MB` so JARs with up to ~80 000 entries succeed on the second try. JARs that fail both attempts are recorded as permanent and surfaced under the same `IllegalArgumentException` classification above.

### Sourcing `publishedAt`

The `publishedAt` column in `versions.tsv` (column 5) is the load-bearing field for the implicit-owner rule: whichever publisher claimed a module name first wins. That makes the timestamp's accuracy a correctness concern, not a cosmetic one. The Nexus index would be the most direct source, but it occasionally re-stamps records during republishing events. The motivating example is Byte Buddy: every `net.bytebuddy:byte-buddy` row at `1.9.5`, `1.10.9`, `1.10.13`, etc. carries an identical index timestamp of `2021-12-15 17:52:51 UTC`, even though those artifacts were genuinely published in 2015-2020. With that timestamp, the implicit-owner heuristic awards the `net.bytebuddy` module name to `nl.jqno.equalsverifier:equalsverifier` 3.0.1 (published 2018-10-29 with `module-info` accidentally declaring `net.bytebuddy`, a classic shaded-uberjar mistake), because the EqualsVerifier row pre-dates Byte Buddy's re-stamped 2021 dates.

So the crawler ignores the index timestamp entirely and sources `publishedAt` from the artifact storage layer's HTTP `Last-Modified`, which preserves the original upload time across the index's re-stamping events. There are three sources in play:

| Source | Header | Notes |
|---|---|---|
| **`repo.maven.apache.org/maven2/`** (canonical Sonatype repo) | `Last-Modified` | Always the original publish moment. The ground truth. |
| **`maven-central.storage-download.googleapis.com/maven2/`** (GCS mirror) | `x-goog-meta-last-modified` | Sonatype-set custom metadata that mirrors `repo.maven.apache.org`'s `Last-Modified` verbatim. Empirically identical to the canonical value in **541 / 541 sampled coordinates** where both were present (zero mismatches). Set only for artifacts published after the GCS bootstrap (~2019-01-19). |
| **GCS mirror** (fallback) | `Last-Modified` | Bucket-landing time. Lags the canonical value by ~50-120 minutes for post-bootstrap artifacts; for **pre-2019 artifacts** it collapses to the bulk-import epoch (`~July 2019`), losing the original publish date entirely. |

The fetcher's strategy:

1. **Read `x-goog-meta-last-modified` first.** Where present, this is canonical by Sonatype's own contract. Single-fetch cost.
2. **Fall back to the standard `Last-Modified`** when goog-meta is absent. For non-mirror responses (Maven Central direct, internal mirrors that don't rewrite mtimes, test fixtures) this is canonical too; the fetcher detects "this response is from the GCS mirror" by checking whether any `x-goog-*` header is present and flags the timestamp non-canonical only in that case.
3. **For non-canonical timestamps** (the pre-2019 GCS bulk-import subset), the scanner issues a follow-up HEAD against the configured canonical-timestamp base (`-Djenesis.crawler.canonical.timestamp.uri`, default same as `<artifact-base-uri>`). When the primary is the GCS mirror and this property points at `repo.maven.apache.org/maven2/`, the HEAD returns the actual publish time and the row is recorded with that. The HEAD runs on the same per-coordinate executor task as the primary scan, so the extra round-trip is fanned out across the concurrent worker pool, not serialised through the consumer loop.

The fallback HEAD is opt-in by configuration: when `<artifact-base-uri>` equals `jenesis.crawler.canonical.timestamp.uri` (the default for any single-source CLI invocation) the upgrade pass short-circuits and no extra requests are issued. The scheduled `crawl.yml` workflow sets the property to `repo.maven.apache.org/maven2/` so the production crawl picks up canonical timestamps for the back catalogue.

Reach of the fallback (measured on a 800-coordinate sample across all eras): ~32 % of post-bootstrap coordinates are pre-2019 and trigger one extra HEAD; the remaining ~68 % see no extra requests. Failure modes (HEAD 404, timeout, network error) silently degrade to whatever the primary returned and ultimately to `coordinate.lastModified()` (the index value) so the row is still recorded.

### Crash safety

The crawler is designed so that a hard kill (SIGTERM, SIGKILL, OOM, machine reboot) at any moment leaves on-disk state such that the next run resumes correctly and never loses a recorded artifact. The properties that make this work:

1. **Every file the crawler owns is written via temp-file + atomic rename.** `versions[-<classifier>].tsv`, `artifacts[-<classifier>].tsv`, `modules[-<classifier>].tsv`, `owners.tsv`, `scanned.tsv`, `state.properties`, `dirty-modules.tsv`, and `STATUS.md` all go through `write-to-<file>.tmp` followed by `Files.move(..., ATOMIC_MOVE, REPLACE_EXISTING)` (with a non-atomic fallback only when the filesystem refuses the atomic flag). So readers always see either the previous fully-written version or the next fully-written version, never a torn write. `state.properties` writes additionally short-circuit when the in-memory content equals the last-saved snapshot (via `Crawler.saveStateIfChanged`), so within a chunk the file is touched only at chunk start and chunk completion (not on every checkpoint flush) even though `checkpointListener` continues to fire normally.
2. **`versions.tsv` is flushed before `scanned.tsv` at every checkpoint.** This is the load-bearing invariant: every coordinate marked as "scanned" on disk has, by construction, already had its module declaration committed to the audit log. After any crash, re-scanning a coordinate is a no-op (the audit-log entry is idempotent via the in-memory `TreeSet`), but losing a coordinate is impossible.
3. **`state.properties` is saved last in a checkpoint**, after both the audit log and the scanned index. If we crash after saving the audit log/scanned marks but before saving state, the next run re-attempts the same index chunk - it will reprocess the same coordinates and end up with the same data, because the producer filter skips anything in `scanned/`.
4. **`dirty-modules.tsv` (only maintained after the first baseline) is persisted at the moment a row is added.** It can therefore be *ahead* of `versions.tsv` (one record-then-flush window). That is intentional: if we crash with a dirty name whose audit-log entry isn't yet on disk, the next run re-scans the coordinate, the entry lands in `versions.tsv` on the next flush, and stage 2 regenerates correctly. The dirty list is only drained after a chunk's queue has been fully exhausted in the current run, so stage 2 never builds the resolved views from a partial audit log. During the very first FULL pass the dirty list is suppressed entirely (first-pass stage 2 uses a tree walk where the presence of `artifacts*.tsv` is the resumption marker) instead of a dirty list, so the same "no partial publish" guarantee holds via a different mechanism.
5. **Stage 2 (`regenerate(moduleName)`) is idempotent.** It reads `versions.tsv` and `owners.tsv` and atomically rewrites both `artifacts.tsv` and `modules.tsv` (the latter is also removed when no qualifying `named` row survives, so the absence of `modules.tsv` is itself a valid resolved state). In the post-baseline (dirty-list) flow, the dirty entry is removed only *after* the new files are committed: a crash either leaves the old files + dirty entry (next run redoes, same result) or the new files + dirty entry (next run redoes with the same input, same output, then removes the entry). In the first-pass (tree-walk) flow, idempotence falls out of the per-module skip-if-exists check: `regenerateMissing()` regenerates only modules whose `artifacts.tsv` is missing, so a crash mid-walk leaves only the in-progress module to redo; modules already completed are skipped on the next run.
6. **The producer holds nothing durable.** Its in-flight set lives only in the bounded queue plus per-scanner state. A crash anywhere mid-stream simply loses both, and the next run re-streams the same index chunk; coordinates that had already been scanned and marked are skipped by the `scanned/` filter, coordinates that had been in flight at crash time are re-emitted and re-scanned (idempotent against the audit log).
7. **The `--resume false` switch is the only way to deliberately drop in-flight state.** It clears `state.properties` and `dirty-modules.tsv` in one shot so the next run starts from a clean baseline. `data/scanned/` and `data/modules/` are preserved - already-scanned coordinates remain skipped, the audit log remains intact.

What the design does **not** protect against: a power loss that loses OS-buffered writes (the crawler does not call `fsync`, relying on atomic rename for process-crash semantics). And: an interruption during stage 2 followed by an edit to `versions.tsv` from outside the crawler would, of course, produce different resolved views on the next regenerate (by design).

### Bounding heap usage on long runs

The two on-heap structures that grow during a sweep are `ModuleStore.dirty` (per-module buffer waiting to be appended to `versions.tsv`) and `ScannedStore.entries` (cache of `<artifactId>.tsv` files keyed by `(groupId, artifactId)`):

- `ModuleStore.dirty` is cleared at every consumer checkpoint (every `jenesis.crawler.checkpoint.every` records, default 2000) inside `store.flush()`. Retention is `O(checkpoint window)`, not `O(run lifetime)`.
- `ScannedStore.entries` is a bounded LRU keyed by `(groupId, artifactId)`. Each artifact's `NavigableSet<ScannedEntry>` is loaded on first touch (during the producer's `contains` check or the scanner's `markOk`/`markFailed`) and pinned in the cache until it loses the LRU race. Cache capacity defaults to **4096** slots and is tuned via `-Djenesis.crawler.scanned.cache.size=<N>`. **Dirty entries (those with unflushed marks) are never evicted**, so the cap may be temporarily exceeded between checkpoints; the next `flush()` clears the dirty set and the surplus entries become eligible for eviction.

The cache shape is what it is because of the Maven Central index's structure: the producer streams ~90 M records per crawl, and most are coordinates that have already been scanned. The LRU lets the producer ask `contains` for every coordinate without monotonically retaining 16 M `ScannedEntry`s + their backing strings. Earlier this code had no eviction, which on a full sweep grew `ScannedStore` to ~2 GB of skip-list nodes plus ~2 GB of pinned strings, OOMing the GitHub Actions runner before the index finished. Earlier still, the `scanned/` files were laid out per-group rather than per-artifact: a single `contains` on `software.amazon.awssdk` (~470 K coordinates) pulled 175 MB into the heap, and load/evict/reload cycles thrashed the cache regardless of heap size. The current shape combines per-artifact files (small individual loads) with the LRU (bounded global retention), and relies on Maven Central index locality (release batches publish related artifacts together) to keep the cache hit rate high without much chunk-boundary reload churn.

The workflow runs with `-XX:+ExitOnOutOfMemoryError -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=$GITHUB_WORKSPACE/crawl.hprof` and a follow-up step that uploads the dump as a workflow artifact on failure, so any regression here is debuggable without needing to reproduce locally.

## Building and testing with Jenesis

The crawler does not need to be built to run, but tests are run via [Jenesis](https://github.com/raphw/jenesis), which is vendored as a git submodule under `.jenesis` and surfaced via the symlink `build/jenesis`.

```
git submodule update --init
java build/jenesis/Project.java                 # build + run tests
java build/jenesis/Project.java stage           # build + stage a clean modular jar under target/stage/
```

The staged jar lives at `target/stage/output/build/jenesis/build.jenesis.crawler/0-SNAPSHOT/build.jenesis.crawler-0-SNAPSHOT.jar`, a normal Maven-shaped layout. The CI workflow `.github/workflows/build.yml` invokes Jenesis on every push.

## Continuous crawling via GitHub Actions

`.github/workflows/crawl.yml` runs twice per day (every 12 hours, at minute 7), each run with a 90-minute Java budget inside a 110-minute job timeout. The workflow ships the canonical Maven Central index and GCS download-mirror URIs as defaults; a fork that wants a different catalogue sets the `INDEX_BASE` / `ARTIFACT_BASE` repository variables once and every subsequent run picks them up. These URIs are intentionally **not** exposed as `workflow_dispatch` inputs - changing them per-run would point the crawler at a different catalogue mid-flight, leaving `data/scanned/` inconsistent with the new source and corrupting resumes. Repo variables, by contrast, are set once at fork time and remain stable across runs, so the in-flight invariant holds. With `-Djenesis.crawler.git.publish=true` (the workflow sets this) the crawler commits every checkpoint and pushes every 10 checkpoints by default (overridable via the `push_every` dispatch input or the `GIT_PUSH_EVERY` repo variable), so a 90-minute run typically produces a few dozen pushes batching the underlying checkpoint commits. A tail step at the end of the workflow pushes anything not yet committed, with a 3-attempt rebase-retry loop.

Scheduled and manual triggers coexist:
- A guard job runs first and, on **scheduled** triggers only, checks whether another crawl is already in flight. If so, the scheduled run exits without doing any work, so a long manual crawl (e.g. 10 hours) is never followed by a freshly-queued 90-minute scheduled run when it ends.
- Manual dispatches (`workflow_dispatch`) always proceed. They share a `crawl` concurrency group so a double-click on "Run workflow" queues rather than overlaps.
- The manual dispatch form exposes a `resume` choice (default `true`). Set to `false` to discard `state.properties` before starting; `data/scanned/` and `data/modules/` are preserved so already-scanned coordinates remain skipped.

`build.yml` runs on every push and pull request, builds with Jenesis, and runs the full test suite. `paths-ignore` filters out commits that only touch `data/**` or `*.md`, so the crawl bot's data-only commits do not trigger CI. `build.yml` additionally skips its own job for commits whose message starts with `[release]` - those are routed exclusively through `release.yml`, which runs the same Jenesis build + test as part of staging.

`.github/workflows/summary.yml` runs once a day at 06:07 UTC — deliberately picked to fall halfway between the two scheduled `crawl.yml` runs at 00:07 and 12:07. It stages the runtime jar, runs `build.jenesis.crawler.ModuleSummary` against `data/`, and commits `data/SUMMARY.md` only when it differs from `HEAD` (a content-identical regeneration is a no-op). Concurrent crawls don't block it: the summary job has no concurrency group, and a 3-attempt `git pull --rebase` retry handles the rare race where a checkpoint commit lands between this job's checkout and its push. Both the `data/**` and `**/*.md` `paths-ignore` rules in `build.yml` already exclude this commit from triggering CI rebuilds, and the `summary:` commit-message prefix doesn't match the `[release]` gate, so `release.yml` stays inert as well.

`release.yml` triggers directly on push to `main` (not via a chain off `build.yml`) and runs whenever the commit message starts with `[release]` or `[release X.Y.Z]`. This makes release commits independent of `paths-ignore` - even an empty `[release]` commit fires the release flow. With no explicit version inside the brackets, the workflow auto-bumps the minor digit from the latest `v*` tag (`0.0.1` if no tag exists yet). The release job's "Build and stage artifacts" step invokes Jenesis with strict pinning, sources, and documentation enabled, then JReleaser publishes to Maven Central and tags `v<version>`.

## Adapting in a fork without editing YAML

The workflow reads optional GitHub repository variables (Settings → Secrets and variables → Actions → Variables) so a fork can be retargeted without touching the workflow file:

| Variable | Effect when set |
|---|---|
| `INDEX_BASE` | Point the index download at an internal mirror. |
| `ARTIFACT_BASE` | Point JAR range-fetches at a different mirror or proxy. |
| `CANONICAL_TIMESTAMP_BASE` | Canonical-`Last-Modified` HEAD target, used to upgrade timestamps for pre-2019 GCS-mirrored artifacts. Defaults to `repo.maven.apache.org/maven2/`. Set to the same URL as `ARTIFACT_BASE` (or any single source) to disable the upgrade pass entirely. See [Sourcing `publishedAt`](#sourcing-publishedat). |
| `BUDGET_MINUTES` | Override the per-run wall-clock budget. |
| `CONCURRENCY` | Override the in-flight fetch count. |
| `TAIL_SIZE` | Override how many bytes are pulled from each JAR tail. |
| `SMALL_JAR_THRESHOLD` | Override the small-JAR fast path threshold. |
| `CHECKPOINT_EVERY` | Override coordinates between checkpoints. |
| `GIT_PUSH_EVERY` | Throttle pushes (commits stay per-checkpoint). |

Unset variables keep the built-in defaults.

The manual `workflow_dispatch` form also exposes per-run overrides for the most commonly tweaked **per-run** settings (`budget_minutes`, `concurrency`, `push_every`, `resume`). Precedence: dispatch input → repository variable → built-in default. The artifact and index URIs are deliberately **not** in this list - they belong to the fork's identity, not to a single run, and can only be changed by setting the `ARTIFACT_BASE` / `INDEX_BASE` repository variables (which apply uniformly to every subsequent run and keep the existing `data/scanned/` index consistent).

## Monitoring

- **`data/STATUS.md`**: rewritten at every checkpoint. Position, percentage, throughput, ETA, sync mode, index chain id. Visible in the GitHub web UI without clicking into any tabs.
- **Commit log**: each checkpoint produces a commit whose message contains `position=<n>/<total> processed=<n> named=<n> automatic=<n>` (and `failed=<n>` when non-zero). `git log --since="3 hours ago" --pretty=format:'%ar %s' data/` gives a trajectory.
- **Actions step summary**: each completed run renders a "Crawl run summary" table on its Actions page, including a per-category failure breakdown (exception class + HTTP status code when present, plus a sample message).
- **Badges**: the README badges at the top reflect the most recent build and crawl outcomes.

## Limitations to be aware of

- **First sweep size**: the main index contains ~101 million records, of which a few million pass the `jar` + classifier filter and become work for the scanner. Even at full GCS throughput a fresh first pass takes many hours of crawl time, split across however many scheduled runs it takes. Each resumed run pays roughly 24 minutes of "re-stream the index, filter against `data/scanned/`" overhead before doing any new scanning work — about 27% of a 90-minute budget. See the "Maven Central main-index characteristics (measured)" table above for the underlying numbers.
- **Index chain rotation**: if Maven Central republishes its index from scratch (rare, but happens) the chain id changes and a full sweep is triggered automatically. Existing module files and the scanned-coordinate index are preserved across the rotation, so already-scanned coordinates are still skipped.
- **Incremental retention window**: Central keeps only the ~30 most recent incremental index files on the mirror. A crawler that goes longer than that without running will, on its next attempt, request an incremental that no longer exists (HTTP 404/410). The default behaviour is to fail fast with a `[error]` block naming `jenesis.crawler.allow.rebaseline`; setting that property to `true` makes the crawler reset its index baseline and re-FULL on the next iteration. `data/` is preserved either way — `ScannedStore` short-circuits every already-scanned coordinate, so the recovery sweep mostly streams the index without re-fetching JARs.
- **Deleted artifacts**: incremental chunks can contain deletion markers. They are ignored. A coordinate that was modular and is later deleted from Central remains in the module files.
- **Transient scan failures**: a coordinate that fails due to a network blip or HTTP/2 stream burst is left unmarked in `data/scanned/`, so the next run retries it. Genuinely broken artifacts (malformed ZIP, etc.) will keep failing every run.

## Project layout

```
sources/build/jenesis/crawler/        main classes (Crawl, Crawler, ListOwners, ModuleSummary,
                                      ReconcileMetadata, Regenerate, RetryFailed, SetOwners)
                                      + State, SyncMode
sources/build/jenesis/crawler/fetch/  HTTP + JAR-byte access: Fetcher, ByteSource,
                                      RobotsTxt, CentralDirectory, Scanner
sources/build/jenesis/crawler/index/  Maven Central index format + batch sources:
                                      IndexReader, IndexProperties, IndexStream,
                                      MetadataReconcileStream, BatchSource,
                                      StreamingBatchSource, FailedScannedBatchSource
sources/build/jenesis/crawler/store/  on-disk stores: ModuleStore, ScannedStore,
                                      DirtyModules
sources/build/jenesis/crawler/model/  domain records: Coordinate, Version, ModuleType,
                                      ModuleEntry, ArtifactsEntry, ModuleVersionEntry,
                                      ScannedEntry, ScannedModule
sources/build/jenesis/crawler/publish/ checkpoint sinks: CheckpointListener,
                                      StatusWriter, GitPublisher
tests/                   tests (JUnit Jupiter + AssertJ), single test package
worker/                  Cloudflare Worker (index.js) that serves the Jenesis Module
                         Repository contract over data/modules/
build/jenesis            symlink into the Jenesis submodule (the launcher)
.jenesis/                Jenesis submodule (sources + runtime cache under cache/)
.github/workflows/       build (push/PR), crawl (scheduled), summary (scheduled),
                         release (push to main with [release] prefix), and
                         reconcile-metadata (manual) workflows
data/                    output (created by the crawler)
```
