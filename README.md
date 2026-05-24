# Jenesis Modules

![build](https://github.com/raphw/jenesis-modules/actions/workflows/build.yml/badge.svg)
![crawl](https://github.com/raphw/jenesis-modules/actions/workflows/crawl.yml/badge.svg)

A modular Java program that crawls Maven Central and records the Java module name produced by every modularised artifact. For each module, every published version is recorded with the Maven coordinates that produced it. The intended lookup pattern is: given a module name (and optional classifier) plus a version, find the Maven coordinates that publish it.

## Output layout

```
data/
├── STATUS.md                     # live progress snapshot, rewritten every checkpoint
├── state.properties              # crawler resume point (index chain, last applied chunk, sweep start)
├── modules/                      # per-module version history (what consumers care about)
│   ├── com/fasterxml/jackson/core/versions.tsv
│   ├── com/fasterxml/jackson/core/versions-no_aopalliance.tsv
│   ├── org/slf4j/versions.tsv
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

Five tab-separated columns:

```
1.7.36  automatic  org.slf4j  slf4j-api  2021-11-15T14:02:00Z
2.0.9   named      org.slf4j  slf4j-api  2023-08-21T09:15:00Z
2.0.10  named      org.slf4j  slf4j-api  2023-08-22T11:42:00Z
```

- Column 1: version as published.
- Column 2: `named` (the JAR contains `module-info.class`, either at the root or at the highest `META-INF/versions/<N>/module-info.class` of a multi-release JAR) or `automatic` (the JAR's manifest sets `Automatic-Module-Name`). Non-modular JARs are not recorded.
- Column 3: `groupId`.
- Column 4: `artifactId`. Combined with columns 1 and 3 this gives the full Maven coordinate.
- Column 5: publication timestamp on Maven Central, UTC ISO-8601 with seconds precision (`yyyy-MM-dd'T'HH:mm:ss'Z'`). Sourced from the index's authoritative per-artifact timestamp; fixed-width so lexicographic and chronological sort agree. Rows are only recorded when the index carries a real timestamp - coordinates whose timestamp is missing or zero are dropped at write time and never appear here, so consumers do not have to filter sentinels.

Use this file for auditing - "who has ever claimed this name?" - not for resolution. For resolution, read the sibling `current.tsv` (next section).

The hierarchical layout means a module whose name is a prefix of another module name coexists without conflict: `org.slf4j` and `org.slf4j.api` live at `org/slf4j/versions.tsv` and `org/slf4j/api/versions.tsv` respectively. The directory `org/slf4j` holds both its own `versions.tsv` and the `api/` subtree.

Lookup math (no parsing required): `data/modules/<segments-joined-by-slash>/versions[-<classifier>].tsv`.

### `data/modules/<dotted/path>/current[-<classifier>].tsv` (resolved view)

`current.tsv` is the **resolved view** of the module: the rows the crawler currently considers authoritative, after applying `owners.tsv` (if present) or the implicit-owner rule (if absent). Consumers wanting "give me module M at version V" read this file - no filtering, no sorting required.

Four tab-separated columns (no timestamp - the resolution has already been made):

```
2.0.10  named      org.slf4j  slf4j-api
2.0.9   named      org.slf4j  slf4j-api
1.7.36  automatic  org.slf4j  slf4j-api
```

- Columns mirror `versions.tsv`'s columns 1-4. Column 5 (timestamp) is omitted; it's no longer needed once the resolution has been made.
- Rows are sorted by version descending. There is at most one row per version - if the policy permits multiple `(groupId, artifactId)` for the same module name and version, the one with the oldest `publishedAt` wins (lexicographic `groupId` ascending breaks same-second ties).
- The file may be absent when the policy filters out every claim (e.g. an empty `owners.tsv`) or when stage 2 has not yet run for this module after a fresh crawl. Either way: no `current.tsv` means no resolved owner.

`current.tsv` is regenerated from `versions.tsv` whenever the policy changes (via `SetOwners`) or new audit-log rows arrive (by the crawler's stage 2, see "How the crawl works").

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
- Drop an **`owners.tsv`** allowlist next to the module's `versions.tsv` (see below) and the crawler narrows `current.tsv` to rows whose `(groupId, artifactId)` is on the list. `versions.tsv` itself stays untouched as the full audit log of every claim that has ever been seen.

The index distinguishes two layers: `versions.tsv` is a *catalog of declarations* — every party that has ever published the module name on Maven Central, kept forever for audit. `current.tsv` is the *resolved view* — the rows the crawler considers authoritative right now, generated from `versions.tsv` by the resolution algorithm below. Consumers read `current.tsv` for resolution; they read `versions.tsv` to audit collisions.

#### Resolution algorithm

The crawler implements the rule below. Its output is `data/modules/<path>/current.tsv` (4 columns: `version`, `type`, `groupId`, `artifactId`, no timestamp). Consumers do not have to re-derive anything — they read `current.tsv` directly.

For each module:

1. **If `owners.tsv` exists** next to the module's `versions.tsv`, treat it as the authoritative allowlist:
   - From `versions.tsv` (and each `versions-<classifier>.tsv`), keep only rows whose `(groupId, artifactId)` is permitted (group-only line → any artifact in that group; pair line → that exact pair).
   - Group the surviving rows by `version`. For each version, the row with the **oldest** `publishedAt` timestamp wins (lexicographic `groupId` ascending breaks any same-second tie). That row is the canonical resolution for that version.

2. **If no `owners.tsv` exists**, apply *first come, first served*:
   - Find the **implicit owner**: the `groupId` of the row in `versions.tsv` with the smallest `publishedAt` (lexicographic `groupId` ascending breaks ties).
   - Filter `versions.tsv` to rows whose `groupId` equals the implicit owner. The result is `current.tsv`.

In both branches, `current.tsv` is sorted version descending and contains no `publishedAt` column - it has no need to: the resolution has already been made. Resolving "I need module `M` at version `V`" reduces to "read `current.tsv` and find the row whose `version` column equals `V`." When the file is missing or empty, the module has no resolved owner (either nothing has been crawled yet, or the policy filters out every claim).

The rule in one sentence: *the first party to publish a module name on Maven Central owns it until an operator says otherwise.*

Why this is the right default:

- **Defends against module injection.** An attacker who registers a popular module name after the fact has, by construction, a later `publishedAt` than the canonical publisher and is excluded from `current.tsv` automatically.
- **Filters shaded redeclarations for free.** When `software.amazon.awssdk:third-party-jackson-core` ships a vendored copy of Jackson, its `publishedAt` is later than `com.fasterxml.jackson.core:jackson-core`'s. The shaded row never makes it into `current.tsv`; you do not have to maintain an exclusion list by hand.
- **Composable with explicit policy.** Where the heuristic is wrong — legitimate ownership transfers (`org.jboss.netty` → `io.netty`), umbrella distributions that should coexist, internal forks — drop an `owners.tsv` next to the module and the explicit branch above takes over. The historical `versions.tsv` data stays intact, so loosening or reverting the policy later is a pure regeneration operation; no re-crawl needed.

Caveats to internalise:

- **Legitimate group renames.** If the original `groupId` is abandoned and a successor takes over, "first come first served" keeps awarding the abandoned `groupId`. Track and curate `owners.tsv` for known transfers.
- **Race-to-first on new names.** For module names that were *first* registered by a low-reputation publisher, the rule will hand ownership to them. The first-owner heuristic flips the *default*; it does not absolve a consumer of vetting genuinely new names.
- **Same-second ties.** Maven Central can publish many artifacts in a single staging batch with identical timestamps. The lexicographic `groupId` tiebreaker is arbitrary but stable; consumers must use exactly this rule (or pin via `owners.tsv`) to remain reproducible across machines.

#### `data/modules/<dotted/path>/owners.tsv` (optional allowlist)

If a module's directory contains an `owners.tsv` file, the resolver uses it as the authoritative allowlist when generating `current.tsv` for that module. `versions.tsv` itself is **not** filtered - it remains the full audit log of every claim that has ever been seen, including ones the policy now excludes. With no `owners.tsv` present, the implicit-owner rule (oldest publisher wins) is used instead. Either way, `current.tsv` is the resolved view.

Line format - one entry per line, tab-separated:

- `<groupId>` (no tab) — any artifactId under this groupId is allowed. Useful when you trust the entire publishing organisation.
- `<groupId>\t<artifactId>` — only this exact coordinate is allowed. Useful when you want to explicitly admit a single repackaged or vendored artifact alongside the canonical one.

Lines starting with `#` and blank lines are ignored.

Example - guard `com.fasterxml.jackson.core` so `current.tsv` only resolves to the canonical artifact plus the AWS third-party repackaging:

```
# canonical
com.fasterxml.jackson.core	jackson-core

# AWS bundles their own; we want to admit that into resolution too
software.amazon.awssdk	third-party-jackson-core
```

Changing the policy is non-destructive: edit `owners.tsv` and either let the next crawl regenerate the affected modules' `current.tsv` files, or run `SetOwners` (below) for an immediate refresh. The `versions.tsv` audit log is never touched, so loosening or reverting the policy later is a pure regeneration.

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

The companion `build.jenesis.crawler.ListOwners` entry point emits a SetOwners-compatible properties file listing the *current* owners (from `owners.tsv` when present, otherwise derived from `versions[-<classifier>].tsv`) for every module whose dotted name matches one of the supplied globs.

```
java sources/build/jenesis/crawler/ListOwners.java <glob> [<glob> ...]
```

Glob semantics mirror module-name structure: `*` matches one segment, `**` matches across dots. `net.bytebuddy.*` matches `net.bytebuddy.agent` but not `net.bytebuddy.agent.builder`; use `net.bytebuddy.**` for the latter. Output is always written to stdout - redirect or pipe as needed.

Optional system properties:

| Property | Default | Effect |
|---|---|---|
| `jenesis.crawler.data` | `data` | Data directory to read from. |
| `jenesis.crawler.list.group.only` | `true` | When deriving owners from `versions.tsv` (no `owners.tsv`), emit groupIds only. Set to `false` to emit `groupId:artifactId` pairs. |
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

When owners are read from `owners.tsv`, lines are emitted verbatim (the file's tab is rendered as `:`); the `group.only` flag only affects values derived from `versions.tsv`.

### `data/scanned/<dotted/path>/<artifactId>.tsv`

For every artifact we have ever fetched, an `<artifactId>.tsv` file under the artifact's group path lists every `(version, classifier)` the crawler has looked at. Three tab-separated columns: `version`, `classifier-or-empty`, `errorMessage-or-empty`. The artifactId is in the file name and not repeated on every row. Used internally to skip coordinates on subsequent runs - Maven Central is immutable per GAV, so once a JAR has been scanned we never need to look at it again.

This layout is per-artifact rather than per-group so that loading "the set of already-scanned coordinates for the artifact I'm about to scan" reads at most a few thousand rows (one artifact's release history) instead of "everything in this Maven group ever," which on prolific groups like `software.amazon.awssdk` (~470 K coordinates) used to pull 175 MB into the heap on every `contains` lookup. Per-artifact files keep each lookup at a few hundred KB.

The third column distinguishes two kinds of completion:

- **Empty** — the scan succeeded (the artifact either yielded a module declaration that landed in `versions.tsv`, or carried no module name at all and is intentionally not in the modules index).
- **Non-empty** — the scan failed *permanently* (the JAR is malformed, or the artifact returned HTTP 404/410). The text is the recorded error message, sanitised so it stays on a single TSV line. Future runs skip these coordinates by default, exactly as they skip successful ones; the artifact is not refetched. To retry every recorded permanent failure on the next run, set `-Djenesis.crawler.reprocess.failed=true` (default `false`). Transient failures (network timeouts, HTTP 5xx, etc.) are *not* recorded here - they remain unmarked, so the next run retries them as a matter of course.

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
| `jenesis.crawler.resume` | `true` | When `false`, deletes `state.properties` before starting, so the next run begins a fresh streaming sync of the full index. `data/scanned/` and `data/modules/` are preserved, so already-scanned coordinates are still skipped. |
| `jenesis.crawler.reprocess.failed` | `false` | When `true`, coordinates whose previous scan ended in a permanent failure (malformed JAR, HTTP 404/410) are treated as un-scanned and re-fetched on this run. Useful after a scanner bug fix; leave at `false` for normal operation so chronically broken artifacts are not refetched on every run. |
| `jenesis.crawler.allow.rebaseline` | `false` | When the crawler falls behind Central's incremental retention window (~30 entries) the next incremental fetch 404s. By default the crawler fails fast and points at this property. Set `true` to reset the index baseline and re-FULL on the next iteration; `data/` is preserved, and `ScannedStore` short-circuits every already-scanned coordinate, so the recovery sweep is fast. |
| `jenesis.crawler.git.publish` | `false` | When `true`, commit + push checkpoints inline. |
| `jenesis.crawler.git.work.dir` | `.` | Working tree for the publishing commits. |
| `jenesis.crawler.git.push.every` | `1` | Push every N checkpoints. |

## How the crawl works

1. Fetch `nexus-maven-repository-index.properties` from Maven Central to learn the current chain id and last incremental chunk number.
2. Decide sync mode:
   - **Full**: first run, or the chain id has rotated. Stream the full Lucene index.
   - **Incremental**: chain id unchanged and there are new chunks. Stream only the new incremental chunks.
   - **Up to date**: nothing new published. Exit immediately.
3. The producer reads the index and emits filtered coordinates onto a bounded queue. Two filters run at the producer:
   - **Extension**: only `jar` artifacts, dropping `sources`, `javadoc`, `tests`, etc. classifiers.
   - **Already scanned**: the in-memory `ScannedStore` (loaded from `data/scanned/`) rejects coordinates we've seen before, so those JARs are never fetched again.

   The queue is purely in-memory. The producer blocks on `queue.put` whenever the queue is full, so it can never outrun the scanner. Nothing about the producer's state is written to disk - the only durable record of "which coordinates have been processed" is `data/scanned/`.
4. The scanner consumes from the queue concurrently. For each coordinate it either fetches the whole small JAR in one ranged GET, or fetches the central-directory tail and then ranges the specific entry it needs. Detection order:
   1. `module-info.class` at the JAR root → `named`.
   2. Highest-version `META-INF/versions/<N>/module-info.class` (multi-release JARs) → `named`.
   3. `META-INF/MANIFEST.MF` with `Automatic-Module-Name` → `automatic`.
   4. Otherwise no record is written.
5. On every checkpoint (default every 2000 coordinates): flush module entries into the `versions.tsv` audit log, update the scanned-coordinate index, save `state.properties`, rewrite `STATUS.md`, and (when `-Djenesis.crawler.git.publish=true`) commit + push. **After the first FULL pass has completed and a baseline exists** (`state.indexChunkLastApplied >= 0`), each module whose `versions.tsv` was touched in this sweep is also recorded in `data/dirty-modules.tsv` so stage 2 below knows what to regenerate. During the first FULL pass itself the dirty list is suppressed — a first sweep touches ~every module in Maven Central, so writing a per-module marker would balloon the file for no benefit (stage 2 handles the first pass wholesale, below).
6. When the producer reaches end-of-stream and the queue has fully drained, the chunk is considered complete and the index chain watermark advances. On budget-truncated runs the watermark does *not* advance; the next run re-streams the same chunk from scratch and the scanned-coordinate filter discards everything already processed, so only the unscanned tail does real work.

**Stage 2 (resolve).** Only after the chunk's queue has fully drained in a run does the crawler regenerate `current.tsv`. Deferring this avoids publishing a freshly-seen `(groupId, artifactId)` as the implicit owner of a module name when an *older* publisher of the same name might still be queued in the producer's in-flight set (a real concern on first-pass full syncs). Two flows:

- **First pass** (no baseline yet, so dirty tracking was suppressed during stage 1): the crawler walks the modules tree and calls `regenerate(moduleName)` for every directory that doesn't already have a `current*.tsv` file. The file's existence is the per-module progress marker — a crash mid-walk leaves baseline unset, the next run re-enters the first-pass branch, the sweep re-runs quickly (`ScannedStore` short-circuits every already-scanned coordinate), and the walk resumes from the directories still missing a `current.tsv`. After the walk finishes the baseline is saved.
- **Subsequent passes** (baseline established, sweeps small): regenerate `current.tsv` for each module in `data/dirty-modules.tsv`. Each `regenerate(moduleName)` reads `versions.tsv` (and any `versions-<classifier>.tsv`) plus the optional `owners.tsv`, writes the resolved `current.tsv` atomically, then drops the module from `data/dirty-modules.tsv`. A crash mid-drain simply leaves remaining entries in the dirty list; the next run drains them before doing anything else.

A crash mid-stage-1 leaves stage 2 pending, but the next run picks the same chunk (the chain watermark hasn't advanced) and re-streams it before running stage 2 - so `current.tsv` is never built from partial data.

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

### Crash safety

The crawler is designed so that a hard kill (SIGTERM, SIGKILL, OOM, machine reboot) at any moment leaves on-disk state such that the next run resumes correctly and never loses a recorded artifact. The properties that make this work:

1. **Every file the crawler owns is written via temp-file + atomic rename.** `versions[-<classifier>].tsv`, `current[-<classifier>].tsv`, `owners.tsv`, `scanned.tsv`, `state.properties`, `dirty-modules.tsv`, and `STATUS.md` all go through `write-to-<file>.tmp` followed by `Files.move(..., ATOMIC_MOVE, REPLACE_EXISTING)` (with a non-atomic fallback only when the filesystem refuses the atomic flag). So readers always see either the previous fully-written version or the next fully-written version, never a torn write.
2. **`versions.tsv` is flushed before `scanned.tsv` at every checkpoint.** This is the load-bearing invariant: every coordinate marked as "scanned" on disk has, by construction, already had its module declaration committed to the audit log. After any crash, re-scanning a coordinate is a no-op (the audit-log entry is idempotent via the in-memory `TreeSet`), but losing a coordinate is impossible.
3. **`state.properties` is saved last in a checkpoint**, after both the audit log and the scanned index. If we crash after saving the audit log/scanned marks but before saving state, the next run re-attempts the same index chunk - it will reprocess the same coordinates and end up with the same data, because the producer filter skips anything in `scanned/`.
4. **`dirty-modules.tsv` (only maintained after the first baseline) is persisted at the moment a row is added.** It can therefore be *ahead* of `versions.tsv` (one record-then-flush window). That is intentional: if we crash with a dirty name whose audit-log entry isn't yet on disk, the next run re-scans the coordinate, the entry lands in `versions.tsv` on the next flush, and stage 2 regenerates correctly. The dirty list is only drained after a chunk's queue has been fully exhausted in the current run, so stage 2 never builds `current.tsv` from a partial audit log. During the very first FULL pass the dirty list is suppressed entirely — first-pass stage 2 uses a tree walk (existence of `current*.tsv` is the resumption marker) instead of a dirty list, so the same "no partial publish" guarantee holds via a different mechanism.
5. **Stage 2 (`regenerate(moduleName)`) is idempotent.** It reads `versions.tsv` and `owners.tsv` and atomically rewrites `current.tsv`. In the post-baseline (dirty-list) flow, the dirty entry is removed only *after* the new `current.tsv` is committed: a crash either leaves the old `current.tsv` + dirty entry (next run redoes, same result) or the new `current.tsv` + dirty entry (next run redoes with the same input, same output, then removes the entry). In the first-pass (tree-walk) flow, idempotence falls out of the skip-if-exists check: a directory that already has a `current.tsv` is left alone.
6. **The producer holds nothing durable.** Its in-flight set lives only in the bounded queue plus per-scanner state. A crash anywhere mid-stream simply loses both, and the next run re-streams the same index chunk; coordinates that had already been scanned and marked are skipped by the `scanned/` filter, coordinates that had been in flight at crash time are re-emitted and re-scanned (idempotent against the audit log).
7. **The `--resume false` switch is the only way to deliberately drop in-flight state.** It clears `state.properties` and `dirty-modules.tsv` in one shot so the next run starts from a clean baseline. `data/scanned/` and `data/modules/` are preserved - already-scanned coordinates remain skipped, the audit log remains intact.

What the design does **not** protect against: a power loss that loses OS-buffered writes (the crawler does not call `fsync`, relying on atomic rename for process-crash semantics). And: an interruption during stage 2 followed by an edit to `versions.tsv` from outside the crawler would, of course, produce a different `current.tsv` on the next regenerate - by design.

### Bounding heap usage on long runs

The two on-heap structures that grow during a sweep are `ModuleStore.dirty` (per-module buffer waiting to be appended to `versions.tsv`) and `ScannedStore.entries` (read-through cache of `<artifactId>.tsv` files keyed by `(groupId, artifactId)`). Both are naturally bounded by the per-artifact layout:

- `ModuleStore.dirty` is cleared at every consumer checkpoint (every `jenesis.crawler.checkpoint.every` records, default 2000) inside `store.flush()`. Retention is `O(checkpoint window)`, not `O(run lifetime)`.
- `ScannedStore.entries` grows monotonically as touched artifacts are loaded. Each artifact's set is small — the largest single artifact in current data is `software.amazon.awssdk:bundle-sdk` with ~2000 versions = ~30 KB on disk, ~750 KB resident. Even an all-Maven-Central sweep that touches every artifact stays comfortably under a few hundred MB, so there is no eviction logic — the cache just stays loaded.

Earlier revisions of this code used a per-group cache (one `NavigableSet` per Maven group, all artifacts in that group inside it). On `software.amazon.awssdk` — 470 K coordinates across 445 artifacts — a single `contains` call pulled ~175 MB into the heap, and the producer's repeated touches of that group drove load/evict/reload cycles that OOMed the JVM regardless of heap size. The per-artifact split fixed both the per-call allocation size and the churn; no eviction was needed once the per-call cost dropped.

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

`release.yml` triggers directly on push to `main` (not via a chain off `build.yml`) and runs whenever the commit message starts with `[release]` or `[release X.Y.Z]`. This makes release commits independent of `paths-ignore` - even an empty `[release]` commit fires the release flow. With no explicit version inside the brackets, the workflow auto-bumps the minor digit from the latest `v*` tag (`0.0.1` if no tag exists yet). The release job's "Build and stage artifacts" step invokes Jenesis with strict pinning, sources, and documentation enabled, then JReleaser publishes to Maven Central and tags `v<version>`.

## Adapting in a fork without editing YAML

The workflow reads optional GitHub repository variables (Settings → Secrets and variables → Actions → Variables) so a fork can be retargeted without touching the workflow file:

| Variable | Effect when set |
|---|---|
| `INDEX_BASE` | Point the index download at an internal mirror. |
| `ARTIFACT_BASE` | Point JAR range-fetches at a different mirror or proxy. |
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
sources/                 production code (one module: build.jenesis.crawler)
tests/                   tests (JUnit Jupiter + AssertJ)
build/jenesis            symlink into the Jenesis submodule (the launcher)
.jenesis/                Jenesis submodule (sources + runtime cache under cache/)
.github/workflows/       build (push/PR) and crawl (scheduled) workflows
data/                    output (created by the crawler)
```
