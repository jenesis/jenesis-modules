# Jenesis Modules

[![release](https://img.shields.io/github/v/release/raphw/jenesis-modules?label=release)](https://github.com/raphw/jenesis-modules/releases/latest)
![build](https://github.com/raphw/jenesis-modules/actions/workflows/build.yml/badge.svg)
![crawl](https://github.com/raphw/jenesis-modules/actions/workflows/crawl.yml/badge.svg)
![last crawl](https://img.shields.io/github/last-commit/raphw/jenesis-modules?path=data%2FSTATUS.md&display_timestamp=committer&label=last%20crawl)

> ### [Jenesis](https://jenesis.build) - a modern Java build tool
> _Java-native config, plugin-free, with `module-info.java` treated as a feature, not an afterthought._

**A crawler that records the Java module name every modularised artifact on Maven Central declares**, and the
catalogue it produces. For each module name, every published version is recorded with the Maven coordinates
that produced it, so a build can answer the question that matters to `module-info.java`: given a module name
and a version, which artifact publishes it?

📖 **The user documentation lives at [jenesis.build/modules](https://jenesis.build/modules/)** - the
`repo.jenesis.build` API, the reports, and how to run a crawl of your own. What follows is for people working
*on* this repository.

> ## [Read the module summary](data/SUMMARY.md)
> Module adoption across all of Maven Central: catalogue-wide counts, named vs automatic adoption, and per-year reports on the most depended-on artifacts.

[`data/STATUS.md`](data/STATUS.md) shows live crawl progress. Maven Central's index lags published artifacts
by up to a week, so the most recent releases may not appear immediately.

## Resolving a module name

```bash
curl -L https://repo.jenesis.build/module/com.fasterxml.jackson.databind/com.fasterxml.jackson.databind.jar
```

Every request answers with a 302 to Maven Central - nothing is re-hosted. The
[API documentation](https://jenesis.build/modules/resolving/) covers the route modes, versions, classifiers
and headers.

## What is in this repository

| Path | Contents |
|------|----------|
| `sources/build/jenesis/crawler/` | The crawler: `Crawl` plus the companion programs, over `fetch/` (HTTP and jar-byte access), `index/` (the Maven Indexer format and batch sources), `store/` (on-disk stores), `model/` (domain records) and `publish/` (checkpoint sinks). |
| `tests/` | JUnit Jupiter and AssertJ, one test package. |
| `worker/` | The Cloudflare Worker (`index.js`) that serves the repository contract over `data/modules/`. |
| `data/` | The catalogue itself, written by the crawler and committed. |
| `build/jenesis/` | A link into the `.jenesis/upstream` git submodule, the pinned Jenesis build tool. |
| `.github/workflows/` | build, crawl, summary, release, reconcile-metadata, and worker. |

## Building and testing

The crawler runs from source and needs no build. Tests are run with
[Jenesis](https://github.com/raphw/jenesis), pinned as a shallow git submodule:

```bash
git submodule update --init --depth 1         # the pinned build tool, once after cloning
java build/jenesis/Project.java               # build + run tests
java build/jenesis/Project.java stage         # stage the runtime jar under target/stage/
```

## Running a crawl

`Crawl` takes an artifact base URI and an index base URI; neither has a default, so the same code drives a
production crawl and a test against a local mock:

```bash
java sources/build/jenesis/crawler/Crawl.java \
     https://maven-central.storage-download.googleapis.com/maven2/ \
     https://repo.maven.apache.org/maven2/.index/
```

A run works to a wall-clock budget (`jenesis.crawler.budget`, default 180 minutes), checkpoints as it goes,
and resumes where it stopped. `jenesis.crawler.data` relocates the output, `jenesis.crawler.concurrency`
bounds in-flight fetches and therefore peak memory, and `jenesis.crawler.resume=false` restarts the index
sweep while keeping everything already scanned. The remaining properties - tail size, small-jar threshold,
checkpoint frequency, git publishing, rebaselining, canonical timestamps - are the `PROP_*` constants at the
top of `Crawl`, which is the reference that cannot go stale.

The [companion programs](https://jenesis.build/modules/crawling/) (`RetryFailed`, `ReconcileMetadata`,
`LoadCoordinates`, `IndexProbe`, `ModuleSummary`, `Regenerate`, `DriftReport`, `ModuleMaven`) are documented
for users; each prints its own usage when run without arguments.

## The data layout

Each module has a directory under `data/modules/` whose path mirrors its dot-separated name, holding three
tab-separated files (plus `-<classifier>` variants):

| File | Role |
|------|------|
| `versions.tsv` | The **audit log**: every `(groupId, artifactId, version)` that has ever declared this name, append-only in `publishedAt` order, never pruned - so collisions and name grabs stay visible and a policy change is reversible. |
| `artifacts.tsv` | A **resolved view** keyed by the Maven version, for `/artifact/` lookups. |
| `modules.tsv` | A **resolved view** keyed by the `module-info` version, for `/module/` lookups. |
| `owners.tsv` | Optional ownership policy: which publishing groupIds are `allowed` or `rejected` for this name. |

`data/scanned/<dotted/path>/<artifactId>.tsv` records which coordinates have been scanned and how they ended,
which is what makes a crawl resumable and keeps immutable coordinates from being fetched twice. Both resolved
views are derived from `versions.tsv`, so `Regenerate` can rebuild them after a policy change without
re-fetching anything.

<sub>Timestamps come from the artifact storage layer's `Last-Modified` rather than the index, because the index
re-stamps records during republishing events and ownership is decided by who published a name first.</sub>

## Continuous crawling

- **`crawl.yml`** runs every 12 hours at minute 7, with a 90-minute budget inside a 110-minute timeout, and
  commits each checkpoint. A guard job makes a *scheduled* run stand down while another crawl is in flight, so
  a long manual crawl is never chased by a queued one; manual dispatches always proceed and share a
  concurrency group. The dispatch form exposes `budget_minutes`, `concurrency`, `push_every` and `resume`.
- **`summary.yml`** runs daily at 06:07 UTC - halfway between the two crawls - and regenerates
  `data/SUMMARY.md`, `data/top/BLEEDING.md` and `data/DRIFTERS.md`, committing only when the content changed.
- **`build.yml`** builds and tests on every push and pull request. `paths-ignore` excludes `data/**` and
  `*.md`, so the crawl bot's data commits do not trigger CI.
- **`release.yml`** fires on a push to `main` whose message starts with `[release]` (`[release X.Y.Z]` for an
  exact version, otherwise the latest tag's minor is bumped), stages with strict pinning, and publishes
  through JReleaser.
- **`reconcile-metadata.yml`** runs weekly (Mondays, 04:41 UTC) and on demand, and shares the `crawl`
  concurrency group so it queues behind a crawl rather than fighting it for the same files.
- **`worker.yml`** tests the Cloudflare Worker with Node's built-in test runner, gated on `worker/**`, since
  it is independent of the Java crawler and needs no install step.

### Retargeting a fork

The workflows read optional repository variables (Settings → Secrets and variables → Actions), so a fork
changes catalogues without editing YAML: `INDEX_BASE`, `ARTIFACT_BASE`, `CANONICAL_TIMESTAMP_BASE`,
`BUDGET_MINUTES`, `CONCURRENCY`, `TAIL_SIZE`, `SMALL_JAR_THRESHOLD`, `CHECKPOINT_EVERY`, `GIT_PUSH_EVERY`.
Unset variables keep the built-in defaults, and precedence is dispatch input → repository variable → default.

The two URIs are deliberately **not** per-run dispatch inputs: they are the fork's identity, and changing one
mid-flight would leave `data/scanned/` describing a repository the crawl is no longer reading.

## Monitoring

`data/STATUS.md` is rewritten at every checkpoint with position, throughput, ETA and sync mode. Each
checkpoint commit carries `position=<n>/<total> processed=<n> named=<n> automatic=<n>` in its message, so
`git log --since="3 hours ago" --pretty=format:'%ar %s' data/` gives a trajectory. Every completed run renders
a summary table on its Actions page, including a per-category failure breakdown.

## Operational limits

- **A first sweep is long.** The index carries ~101 million records, a few million of which are jars worth
  opening, so a fresh catalogue takes many hours spread over as many runs as it takes. Each resumed run
  re-streams the index before finding new work - roughly a quarter of a 90-minute budget.
- **The incremental window is ~30 chunks.** A crawler idle for longer asks for an incremental that no longer
  exists; the run fails fast and names `jenesis.crawler.allow.rebaseline`, which resets the baseline and
  re-sweeps. Already-scanned coordinates are skipped, so recovery mostly re-streams the index.
- **An index rotation is handled.** If Central republishes its index from scratch the chain id changes and a
  full sweep is triggered automatically; module files and the scanned record survive it.
- **Deletions are ignored.** A coordinate that was modular and is later deleted from Central stays recorded.
- **Transient failures retry themselves.** A coordinate lost to a network blip is left unmarked and retried on
  the next run; only genuinely broken artifacts are recorded as permanent, and `RetryFailed` revisits those.

## License

Apache License 2.0. Copyright Rafael Winterhalter.
