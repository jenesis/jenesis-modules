# Jenesis Modules

![build](https://github.com/raphw/jenesis-modules/actions/workflows/build.yml/badge.svg)
![crawl](https://github.com/raphw/jenesis-modules/actions/workflows/crawl.yml/badge.svg)

A modular Java program that crawls Maven Central and records the Java module name produced by every modularised artifact. For each module, every published version is recorded with the Maven coordinates that produced it.

The intended lookup pattern is: given a module name (and optional classifier) and a version, find the Maven coordinates that produce it. Files are sorted newest version first, so the freshest mapping is the first line of the file.

## Output layout

```
data/
├── STATUS.md                     # live progress snapshot, rewritten every checkpoint
├── state.properties              # crawler resume point (index chain, position, etc.)
├── worklist.tsv                  # current sweep's pending coordinates
└── modules/
    ├── c/com.fasterxml.jackson.core
    ├── c/com.fasterxml.jackson.core-no_aopalliance
    ├── o/org.slf4j.api
    └── ...
```

Each file under `modules/<first-letter>/<module-name>[-<classifier>]` is tab-separated, three columns, sorted by Maven version descending then by group:artifact:

```
2.0.10  named      org.slf4j:slf4j-api
2.0.9   named      org.slf4j:slf4j-api
1.7.36  automatic  org.slf4j:slf4j-api
```

- Column 1: version as published.
- Column 2: `named` (the JAR contains `module-info.class`) or `automatic` (the JAR's manifest has `Automatic-Module-Name`). Non-modular JARs are not recorded.
- Column 3: `groupId:artifactId`. Combined with column 1 this gives the full Maven coordinate.

To look up "what is the latest known coordinate for module `org.slf4j.api`":

```
head -n 1 data/modules/o/org.slf4j.api | cut -f1,3
```

For a classified variant, append `-<classifier>` to the file name. The caller supplies the classifier at lookup time, so no parsing of the file name is required.

## How the crawl works

1. Fetch `nexus-maven-repository-index.properties` from Maven Central to learn the current chain id and last incremental chunk number.
2. Decide sync mode:
   - **Full**: first run, or the chain id has rotated. Download the full Lucene index and write every modular-eligible coordinate to the worklist.
   - **Incremental**: chain id unchanged and there are new chunks. Fetch only the new incremental chunks and write their coordinates.
   - **Up to date**: nothing new published. Exit immediately.
3. Process the worklist: for each coordinate, range-fetch the JAR's tail (default last 64 KB), parse the ZIP central directory, read `module-info.class` or `META-INF/MANIFEST.MF` from a second range request, and record the result. Concurrent virtual threads keep the network busy.
4. Checkpoint every 2000 coordinates: flush in-memory module entries to their files on disk, write `state.properties`, rewrite `STATUS.md`, and (optionally) commit + push to git.

A run stops when its wall-clock budget expires or the worklist is exhausted. The next run resumes from the recorded byte position in the worklist.

## Running locally

The project is built with [Jenesis](https://github.com/raphw/jenesis), which is vendored as a git submodule under `.jenesis` and surfaced via the symlink `build/jenesis`. A `pom.xml` is also included so the project loads cleanly in IDEs and so a Maven build works as a fallback.

### With Jenesis (preferred)

After cloning, initialise the submodule once:

```
git clone --recurse-submodules https://github.com/raphw/jenesis-modules.git
cd jenesis-modules
```

Or, on an existing clone:

```
git submodule update --init
```

Then build and test in a single step:

```
java build/jenesis/Project.java
```

Jenesis discovers `sources/`, `tests/`, the two `module-info.java` files, and the dependency pins in `pom.xml` and `tests/module-info.java` automatically. The first build downloads dependencies into `.jenesis/cache/`; subsequent builds reuse content-hashed step outputs and skip unchanged work. Build outputs land under `target/`.

To run the modular JAR produced by the build:

```
java -p target/maven/compose/module/module-/produce/assemble/java/artifacts/build.jenesis.modules.jar \
     -m build.jenesis.modules/build.jenesis.modules.Main
```

### With Maven (fallback)

```
mvn -DskipTests package
java -p target/build.jenesis.modules-0-SNAPSHOT.jar \
     -m build.jenesis.modules/build.jenesis.modules.Main
```

Common flags:

| Flag | Default | Purpose |
|---|---|---|
| `--data <dir>` | `data` | Where state, worklist, and module files live. |
| `--budget-minutes <n>` | 160 | Wall-clock budget for this run. The crawler self-exits when it expires. |
| `--concurrency <n>` | 128 | Maximum in-flight artifact fetches. |
| `--tail-size <n>` | 65536 | Bytes to range-fetch from the end of each JAR. |
| `--checkpoint-every <n>` | 2000 | Coordinates between on-disk checkpoints. |
| `--index-base <uri>` | `https://repo.maven.apache.org/maven2/.index/` | Base URI of the Maven Central index. |
| `--artifact-base <uri>` | `https://maven-central.storage-download.googleapis.com/maven2/` | Base URI used to range-fetch JARs. The GCS mirror is recommended. |

Environment overrides:

| Variable | Default | Purpose |
|---|---|---|
| `BUDGET_MINUTES` | 160 | Wall-clock budget for this run. |
| `CONCURRENCY` | 128 | Maximum in-flight artifact fetches. |
| `DATA_DIR` | `data` | Output directory. |
| `INDEX_BASE` | `https://repo.maven.apache.org/maven2/.index/` | Base URI of the Maven Central index. |
| `ARTIFACT_BASE` | `https://maven-central.storage-download.googleapis.com/maven2/` | Base URI used to range-fetch JARs. |
| `TAIL_SIZE` | 65536 | Bytes range-fetched from the end of each JAR. |
| `CHECKPOINT_EVERY` | 2000 | Coordinates between on-disk checkpoints. |

All of them can also be set via the matching CLI flag, which takes precedence over the environment variable.

Run a small sweep to validate the toolchain:

```
java -p target/build.jenesis.modules-0-SNAPSHOT.jar \
     -m build.jenesis.modules/build.jenesis.modules.Main \
     --data data --budget-minutes 5
```

The first run downloads the full index (a few hundred MB) and generates the worklist before any artifact is scanned, so a 5 minute first run will mostly be spent on index generation.

## Resuming after interruption

Three different forms of progress are persisted so the next run picks up where the previous one left off:

- `state.properties` records the byte position inside the worklist file, the chain id and last applied chunk of the Maven Central index, and the sweep start time.
- The per-module TSV files are append-and-resort. Re-recording the same coordinate is a no-op.
- The worklist file itself is regenerated only when the current sweep is fully consumed or the index chain has rotated. Otherwise it is reused.

A run that crashes or is killed loses at most the work since the last checkpoint (`--checkpoint-every` coordinates, ~30-60 seconds at full throughput).

## Continuous integration

Two workflows live under `.github/workflows/`:

- **`build.yml`**: runs on every push and pull request, plus manual dispatch. Checks out submodules, sets up JDK 25, restores the Jenesis dependency cache, and runs `java build/jenesis/Project.java` (a full Jenesis build including tests). `paths-ignore` filters out commits that only touch `data/**` or `*.md`, so the crawl bot's data-only commits do not trigger it.
- **`crawl.yml`**: runs three times per day (every 8 hours, at minute 7), each run with a 90 minute Java budget inside a 100 minute job timeout. It builds the JAR with `mvn -DskipTests package` (no tests on scheduled runs), runs the crawler with `GIT_PUBLISH=1` (so the crawler commits and pushes after each checkpoint), and a final cleanup step pushes anything not yet committed.

In short: code pushes go through `build` (full tests), scheduled crawls go through `crawl` (no tests). They never trigger each other.

Manual run with custom inputs is available via the Actions tab (`workflow_dispatch`):

- `budget_minutes` (default 90)
- `concurrency` (default 128)
- `push_every` (default 1; raise this to batch pushes if push throughput becomes a bottleneck)

Required workflow permission: `contents: write` (already set in the workflow file).

The very first sweep typically needs 4-8 runs over a couple of days. After that, each scheduled run only consumes the few thousand new GAVs published since the last sync and completes in minutes.

## Monitoring

- **`data/STATUS.md`**: rewritten at every checkpoint. Position, percentage, throughput, ETA, sync mode, index chain id. Visible in the GitHub web UI without clicking into any tabs.
- **Commit log**: each checkpoint produces a commit whose message contains `position=<n>/<total> processed=<n> modular=<n>`. `git log --since="3 hours ago" --pretty=format:'%ar %s' data/` gives a trajectory.
- **Actions step summary**: each completed run renders a "Crawl run summary" table on its Actions page.
- **Badge**: the README badge above (`crawl`) reflects the most recent scheduled run's outcome.

## Configuration override matrix

The crawler reads configuration in this order, last writer winning:

1. Built-in defaults.
2. Environment variables (`BUDGET_MINUTES`, `CONCURRENCY`, `DATA_DIR`).
3. Command-line flags (`--budget-minutes`, etc.).

The git publisher is opt-in via `GIT_PUBLISH=1`. It also reads `GIT_WORK_DIR` (defaults to the current directory) and `GIT_PUSH_EVERY` (defaults to 1; commits always happen at every checkpoint, this only throttles `git push`).

## Limitations to be aware of

- **First sweep size**: the initial worklist is ~8 million coordinates. Even at the best GCS throughput this takes hours of crawl time, split across however many scheduled runs it takes.
- **Index chain rotation**: if Maven Central republishes its index from scratch (rare, but happens) the chain id changes and a full sweep is triggered automatically. Existing per-module files are preserved and merged.
- **Deleted artifacts**: incremental chunks can contain deletion markers. They are ignored. A coordinate that was modular and is later deleted from Central remains in our module files.
- **Version sorting**: Maven's `ComparableVersion` algorithm is implemented in full for numeric components, qualifier ordering, `ga`/`final`/`release`/`cr` aliases, and trailing-zero normalisation. Single-letter qualifier expansion (`1.0a` meaning `1.0-alpha`) is not currently handled, which can mis-order a small number of very old artifacts.

## Project layout

```
sources/                 production code (one module: build.jenesis.modules)
tests/                   tests (JUnit Jupiter + AssertJ)
build/jenesis            symlink into the Jenesis submodule (the launcher)
.jenesis/                Jenesis submodule (sources + runtime cache under cache/)
.github/workflows/       continuous crawl workflow
pom.xml                  dependency pins, IDE integration, Maven fallback build
data/                    output (created by the crawler)
```

## Adapting in a fork without editing YAML

The workflow reads optional GitHub repository variables (Settings → Secrets and variables → Actions → Variables) so a fork can be retargeted without touching the workflow file:

| Variable | Effect when set |
|---|---|
| `INDEX_BASE` | Point the index download at an internal mirror. |
| `ARTIFACT_BASE` | Point JAR range-fetches at a different mirror or proxy. |
| `BUDGET_MINUTES` | Override the per-run wall-clock budget. |
| `CONCURRENCY` | Override the in-flight fetch count. |
| `TAIL_SIZE` | Override how many bytes are pulled from each JAR tail. |
| `CHECKPOINT_EVERY` | Override coordinates between checkpoints. |
| `GIT_PUSH_EVERY` | Throttle pushes (commits stay per-checkpoint). |

When a variable is unset the crawler's built-in default is used. `workflow_dispatch` inputs still take precedence over repository variables, which in turn take precedence over the built-in defaults.

The only edit really needed after forking is the `raphw/jenesis-modules` placeholder in the badge URL at the top of this file. If your fork still wants to point at the original Central + GCS endpoints, leave all variables unset and nothing else changes.
