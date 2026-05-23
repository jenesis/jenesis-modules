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

Each module's directory path mirrors the dot-separated module name. The leaf file is always `versions.tsv` (or `versions-<classifier>.tsv` for a classified variant), three tab-separated columns, sorted by Maven version descending then by group:artifact:

```
2.0.10  named      org.slf4j:slf4j-api
2.0.9   named      org.slf4j:slf4j-api
1.7.36  automatic  org.slf4j:slf4j-api
```

- Column 1: version as published.
- Column 2: `named` (the JAR contains `module-info.class`, either at the root or at the highest `META-INF/versions/<N>/module-info.class` of a multi-release JAR) or `automatic` (the JAR's manifest sets `Automatic-Module-Name`). Non-modular JARs are not recorded.
- Column 3: `groupId:artifactId`. Combined with column 1 this gives the full Maven coordinate.

The hierarchical layout means a module whose name is a prefix of another module name coexists without conflict: `org.slf4j` and `org.slf4j.api` live at `org/slf4j/versions.tsv` and `org/slf4j/api/versions.tsv` respectively. The directory `org/slf4j` holds both its own `versions.tsv` and the `api/` subtree.

Lookup math (no parsing required): `data/modules/<segments-joined-by-slash>/versions[-<classifier>].tsv`.

### `data/scanned/<dotted/path>/scanned.tsv`

For every groupId we have ever scanned an artifact under, a `scanned.tsv` file lists every `(artifactId, version, classifier)` we have looked at. Three tab-separated columns: `artifactId`, `version`, `classifier-or-empty`. Used internally by the crawler to skip coordinates on subsequent runs - Maven Central is immutable per GAV, so once a JAR has been scanned we never need to look at it again.

## Running the crawler

Requires Java 25 or newer. The crawler is launched with the JDK's multi-file source-code mode. No build step is required - the JDK compiles the sources on demand:

```
java sources/build/jenesis/crawler/Main.java [options]
```

For a quick local smoke run with a tiny budget:

```
java sources/build/jenesis/crawler/Main.java --data smoke-data --budget-minutes 3
```

On a first run the crawler streams the full Maven Central index while the scanner is already consuming coordinates from the queue, so artifact scanning starts within the first second or two. The 3-minute budget governs wall-clock time spent in the scan loop; when it expires the crawler exits cleanly, leaving everything under `data/` in a consistent state.

Common flags:

| Flag | Default | Purpose |
|---|---|---|
| `--data <dir>` | `data` | Where state, worklist, module files, and the scanned-index live. |
| `--budget-minutes <n>` | 160 | Wall-clock budget for this run. |
| `--concurrency <n>` | 96 | Maximum in-flight artifact fetches; kept under the HTTP/2 stream limit per connection. |
| `--tail-size <n>` | 65536 | Bytes range-fetched from the end of each JAR. |
| `--small-jar-threshold <n>` | 262144 | JAR size at or below which we fetch the whole file in one request, falling back to the cached-tail path on any failure. |
| `--checkpoint-every <n>` | 2000 | Coordinates between on-disk checkpoints. |
| `--index-base <uri>` | Maven Central index | Base URI of the index. |
| `--artifact-base <uri>` | GCS mirror | Base URI used to range-fetch JARs. |
| `--resume <true\|false>` | `true` | When `false`, deletes `state.properties` and any `worklist.tsv[.streaming]` before starting, so the next run begins a fresh streaming sync. `data/scanned/` and `data/modules/` are preserved, so already-scanned coordinates are still skipped. |

All flags can also be set via matching environment variables (`BUDGET_MINUTES`, `CONCURRENCY`, `DATA_DIR`, `TAIL_SIZE`, `SMALL_JAR_THRESHOLD`, `CHECKPOINT_EVERY`, `INDEX_BASE`, `ARTIFACT_BASE`, `RESUME`). Flags take precedence over environment variables.

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
5. On every checkpoint (default every 2000 coordinates): flush module entries, update the scanned-coordinate index, save `state.properties`, rewrite `STATUS.md`, and (when `GIT_PUBLISH=1`) commit + push.
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

`.github/workflows/crawl.yml` runs three times per day (every 8 hours, at minute 7), each run with a 90-minute Java budget inside a 100-minute job timeout. With `GIT_PUBLISH=1` the crawler commits and pushes after every checkpoint, so a 90-minute run typically produces dozens of small incremental commits rather than one large terminal commit. A tail step at the end of the workflow pushes anything not yet committed, with a 3-attempt rebase-retry loop.

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
