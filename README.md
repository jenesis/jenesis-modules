# Jenesis Modules

![build](https://github.com/raphw/jenesis-modules/actions/workflows/build.yml/badge.svg)
![crawl](https://github.com/raphw/jenesis-modules/actions/workflows/crawl.yml/badge.svg)

A modular Java program that crawls Maven Central and records the Java module name produced by every modularised artifact. For each module, every published version is recorded with the Maven coordinates that produced it. The intended lookup pattern is: given a module name (and optional classifier) plus a version, find the Maven coordinates that publish it.

## Output layout

```
data/
├── STATUS.md                     # live progress snapshot, rewritten every checkpoint
├── state.properties              # crawler resume point (index chain, position, etc.)
├── worklist.tsv                  # current sweep's pending coordinates (if any)
├── modules/                      # per-module version history (what consumers care about)
│   ├── com/fasterxml/jackson/core/versions.tsv
│   ├── com/fasterxml/jackson/core/versions-no_aopalliance.tsv
│   ├── org/slf4j/versions.tsv
│   ├── org/slf4j/api/versions.tsv
│   └── ...
└── scanned/                      # per-group "we have already looked at these JARs" index
    ├── com/fasterxml/jackson/scanned.tsv
    ├── org/slf4j/scanned.tsv
    └── ...
```

### `data/modules/<dotted/path>/versions[-<classifier>].tsv`

Each module's directory path mirrors the dot-separated module name. The leaf file is always `versions.tsv` (or `versions-<classifier>.tsv` for a classified variant), five tab-separated columns, sorted by Maven version descending then by groupId, then artifactId:

```
2.0.10  named      org.slf4j  slf4j-api  2023-08-22T11:42:00Z
2.0.9   named      org.slf4j  slf4j-api  2023-08-21T09:15:00Z
1.7.36  automatic  org.slf4j  slf4j-api  2021-11-15T14:02:00Z
```

- Column 1: version as published.
- Column 2: `named` (the JAR contains `module-info.class`, either at the root or at the highest `META-INF/versions/<N>/module-info.class` of a multi-release JAR) or `automatic` (the JAR's manifest sets `Automatic-Module-Name`). Non-modular JARs are not recorded.
- Column 3: `groupId`.
- Column 4: `artifactId`. Combined with columns 1 and 3 this gives the full Maven coordinate.
- Column 5: publication timestamp on Maven Central, UTC ISO-8601 with seconds precision (`yyyy-MM-dd'T'HH:mm:ss'Z'`). Sourced from the index's authoritative per-artifact timestamp; fixed-width so lexicographic and chronological sort agree. `1970-01-01T00:00:00Z` is used as a sentinel when the index did not carry a timestamp.

The hierarchical layout means a module whose name is a prefix of another module name coexists without conflict: `org.slf4j` and `org.slf4j.api` live at `org/slf4j/versions.tsv` and `org/slf4j/api/versions.tsv` respectively. The directory `org/slf4j` holds both its own `versions.tsv` and the `api/` subtree.

Lookup math (no parsing required): `data/modules/<segments-joined-by-slash>/versions[-<classifier>].tsv`.

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
- Drop an **`owners.tsv`** allowlist next to the module's `versions.tsv` (see below) and the crawler will refuse to record any row whose `(groupId, artifactId)` is not on it. This is the operator-level escape hatch for known collision-prone modules.

The index is a *catalog of declarations*, not a *trust statement*. Use it to discover and audit, not to dispatch.

#### `data/modules/<dotted/path>/owners.tsv` (optional allowlist)

If a module's directory contains an `owners.tsv` file, the crawler treats it as an allowlist for that module name and silently discards any incoming `(groupId, artifactId)` that does not match. With no `owners.tsv` present, the module is unrestricted (the default behavior). Coordinates that get filtered out still go into `data/scanned/` so they are not re-fetched on subsequent runs.

Line format - one entry per line, tab-separated:

- `<groupId>` (no tab) — any artifactId under this groupId is allowed. Useful when you trust the entire publishing organisation.
- `<groupId>\t<artifactId>` — only this exact coordinate is allowed. Useful when you want to explicitly admit a single repackaged or vendored artifact alongside the canonical one.

Lines starting with `#` and blank lines are ignored.

Example - guard `com.fasterxml.jackson.core` so only the canonical artifact plus the AWS third-party repackaging are recorded:

```
# canonical
com.fasterxml.jackson.core	jackson-core

# AWS bundles their own; we want to track it but not anyone else's
software.amazon.awssdk	third-party-jackson-core
```

The file is read once per module the first time the crawler is asked to record into it, and cached in memory for the rest of the run. To change the policy, edit the file and start the next run - existing rows in `versions.tsv` are not retroactively pruned, only future records are filtered. To rewrite existing rows against a new policy in one shot, use the `SetOwners` entry point described below.

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

### `data/scanned/<dotted/path>/scanned.tsv`

For every groupId we have ever scanned an artifact under, a `scanned.tsv` file lists every `(artifactId, version, classifier)` we have looked at. Three tab-separated columns: `artifactId`, `version`, `classifier-or-empty`. Used internally by the crawler to skip coordinates on subsequent runs - Maven Central is immutable per GAV, so once a JAR has been scanned we never need to look at it again.

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
| `jenesis.crawler.data` | `data` | Where state, worklist, module files, and the scanned-index live. |
| `jenesis.crawler.budget` | `160` | Wall-clock budget for this run, in minutes. |
| `jenesis.crawler.concurrency` | `96` | Maximum in-flight artifact fetches; kept under the HTTP/2 stream limit per connection. |
| `jenesis.crawler.tail.size` | `65536` | Bytes range-fetched from the end of each JAR. |
| `jenesis.crawler.small.jar.threshold` | `262144` | JAR size at or below which we fetch the whole file in one request, falling back to the cached-tail path on any failure. |
| `jenesis.crawler.checkpoint.every` | `2000` | Coordinates between on-disk checkpoints. |
| `jenesis.crawler.resume` | `true` | When `false`, deletes `state.properties` and any `worklist.tsv[.streaming]` before starting, so the next run begins a fresh streaming sync. `data/scanned/` and `data/modules/` are preserved, so already-scanned coordinates are still skipped. |
| `jenesis.crawler.git.publish` | `false` | When `true`, commit + push checkpoints inline. |
| `jenesis.crawler.git.work.dir` | `.` | Working tree for the publishing commits. |
| `jenesis.crawler.git.push.every` | `1` | Push every N checkpoints. |

## How the crawl works

1. Fetch `nexus-maven-repository-index.properties` from Maven Central to learn the current chain id and last incremental chunk number.
2. Decide sync mode:
   - **Full**: first run, or the chain id has rotated. Stream the full Lucene index.
   - **Incremental**: chain id unchanged and there are new chunks. Stream only the new incremental chunks.
   - **Up to date**: nothing new published. Exit immediately.
3. The producer reads the index and emits filtered coordinates onto a bounded queue while writing them to `worklist.tsv.streaming` on disk. Two filters run at the producer:
   - **Extension**: only `jar` artifacts, dropping `sources`, `javadoc`, `tests`, etc. classifiers.
   - **Already scanned**: the in-memory `ScannedStore` (loaded from `data/scanned/`) rejects coordinates we've seen before, so those JARs are never fetched again.
4. The scanner consumes from the queue concurrently. For each coordinate it either fetches the whole small JAR in one ranged GET, or fetches the central-directory tail and then ranges the specific entry it needs. Detection order:
   1. `module-info.class` at the JAR root → `named`.
   2. Highest-version `META-INF/versions/<N>/module-info.class` (multi-release JARs) → `named`.
   3. `META-INF/MANIFEST.MF` with `Automatic-Module-Name` → `automatic`.
   4. Otherwise no record is written.
5. On every checkpoint (default every 2000 coordinates): flush module entries, update the scanned-coordinate index, save `state.properties`, rewrite `STATUS.md`, and (when `-Djenesis.crawler.git.publish=true`) commit + push.
6. On clean sync completion `worklist.tsv.streaming` is renamed to `worklist.tsv` and the index chain watermark advances. On budget-truncated sync the streaming file is discarded and the next run re-syncs - module entries already recorded and the scanned-coordinate index are preserved.

A run stops when its wall-clock budget expires or the worklist is exhausted. The next run picks up by either resuming the existing `worklist.tsv` (sync had completed), or re-syncing the index but skipping every coordinate already in `data/scanned/`.

## Building and testing with Jenesis

The crawler does not need to be built to run, but tests are run via [Jenesis](https://github.com/raphw/jenesis), which is vendored as a git submodule under `.jenesis` and surfaced via the symlink `build/jenesis`.

```
git submodule update --init
java build/jenesis/Project.java                 # build + run tests
java build/jenesis/Project.java stage           # build + stage a clean modular jar under target/stage/
```

The staged jar lives at `target/stage/output/build/jenesis/build.jenesis.crawler/0-SNAPSHOT/build.jenesis.crawler-0-SNAPSHOT.jar`, a normal Maven-shaped layout. The CI workflow `.github/workflows/build.yml` invokes Jenesis on every push.

## Continuous crawling via GitHub Actions

`.github/workflows/crawl.yml` runs three times per day (every 8 hours, at minute 7), each run with a 90-minute Java budget inside a 100-minute job timeout. The workflow owns the default URIs for Maven Central and the GCS artifact mirror (the crawler itself has no hardcoded defaults); they can be overridden per-run via `workflow_dispatch` inputs or per-fork via repo variables (see below). With `-Djenesis.crawler.git.publish=true` (the workflow sets this) the crawler commits and pushes after every checkpoint, so a 90-minute run typically produces dozens of small incremental commits rather than one large terminal commit. A tail step at the end of the workflow pushes anything not yet committed, with a 3-attempt rebase-retry loop.

Scheduled and manual triggers coexist:
- A guard job runs first and, on **scheduled** triggers only, checks whether another crawl is already in flight. If so, the scheduled run exits without doing any work, so a long manual crawl (e.g. 10 hours) is never followed by a freshly-queued 90-minute scheduled run when it ends.
- Manual dispatches (`workflow_dispatch`) always proceed. They share a `crawl` concurrency group so a double-click on "Run workflow" queues rather than overlaps.
- The manual dispatch form exposes a `resume` choice (default `true`). Set to `false` to discard `state.properties` and any in-flight worklist before starting; `data/scanned/` and `data/modules/` are preserved so already-scanned coordinates remain skipped.

`build.yml` runs on every push and pull request, builds with Jenesis, and runs the full test suite. `paths-ignore` filters out commits that only touch `data/**` or `*.md`, so the crawl bot's data-only commits do not trigger CI.

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

The manual `workflow_dispatch` form also exposes per-run overrides for the most commonly tweaked settings (`budget_minutes`, `concurrency`, `push_every`, `resume`, `index_base`, `artifact_base`). Precedence: dispatch input → repository variable → built-in default. Use the `index_base` / `artifact_base` inputs for one-off test runs against a mirror without changing repo variables.

## Monitoring

- **`data/STATUS.md`**: rewritten at every checkpoint. Position, percentage, throughput, ETA, sync mode, index chain id. Visible in the GitHub web UI without clicking into any tabs.
- **Commit log**: each checkpoint produces a commit whose message contains `position=<n>/<total> processed=<n> modular=<n>`. `git log --since="3 hours ago" --pretty=format:'%ar %s' data/` gives a trajectory.
- **Actions step summary**: each completed run renders a "Crawl run summary" table on its Actions page, including a per-category failure breakdown (exception class + HTTP status code when present, plus a sample message).
- **Badges**: the README badges at the top reflect the most recent build and crawl outcomes.

## Limitations to be aware of

- **First sweep size**: the initial worklist is ~8 million coordinates. Even at full GCS throughput this takes hours of crawl time, split across however many scheduled runs it takes. The scanned-coordinate index makes successive runs additive rather than repetitive.
- **Index chain rotation**: if Maven Central republishes its index from scratch (rare, but happens) the chain id changes and a full sweep is triggered automatically. Existing module files and the scanned-coordinate index are preserved across the rotation, so already-scanned coordinates are still skipped.
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
