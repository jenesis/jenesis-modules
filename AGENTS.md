# AGENTS.md

The Jenesis Module Index: the crawler that reads the module name every artifact on Maven Central declares,
the data it produces under `data/`, and the small HTTP service (`worker/`) that resolves a module name to the
artifact behind it at repo.jenesis.build. `README.md` covers the layout, the crawl, the workflows and
releasing. The user documentation is [jenesis.build/modules](https://jenesis.build/modules/)
([raphw/jenesis-documentation](https://github.com/raphw/jenesis-documentation)).

## Build & test

- **JDK 25 or newer.** The build tool is the `.jenesis/upstream` git submodule (`build/jenesis` links into
  it): `git submodule update --init --depth 1` once, then `java build/jenesis/Project.java` builds and runs
  the tests. The crawler programs themselves run from source (`java sources/build/jenesis/crawler/Crawl.java …`).
- The worker is tested with Node's built-in runner: `node --test` from `worker/`. It has no dependencies.
- CI builds under strict pinning; after changing a dependency, run `java build/jenesis/Project.java pin` and
  commit the rewritten pins.

## How the code is written

- Java 25 with `import module java.base;`; records for the domain model (`model/`), small `main` programs
  for each tool (`Crawl`, `Regenerate`, `SetOwners`, …) that read their settings from `jenesis.crawler.*`
  system properties, whose `PROP_*` constants at the top of `Crawl` are the reference. A new setting is a new
  constant, a default in `Crawler.Configuration`, and a line in `Crawl`'s usage text - keep the usage text's
  defaults equal to the real ones.
- `data/` is written by the crawler and committed by the scheduled workflows. Never edit it by hand; change
  the program that produces it and rerun. The bot's commits land on `main` continuously, so rebase before
  you push.
- The resolved views (`artifacts.tsv`, `modules.tsv`) are derived from the audit log (`versions.tsv`) and the
  ownership files (`owners.tsv`) by `Regenerate`; a change to how ownership or filtering is decided is rolled
  out by regenerating, never by rewriting history.
- The URL shapes the worker serves are the public contract (documented on jenesis.build); a change to them is
  a change to every client, including the Jenesis build tool, and is made deliberately with the
  documentation updated in the same pass.

## Tests

- `tests/` is the `@jenesis.test` module, on JUnit Jupiter with AssertJ; a test method name states the
  behaviour it proves. The crawler is driven against local fixtures, never against Maven Central.
- `worker/index.test.js` covers every route shape and status the service answers; a new route or header
  gets a case there.

## Releasing and the build tool

A release is a manual run of the release workflow from the Actions tab, so any commit is releasable: its
optional `sha` input names the commit (default: the head it runs on) and its optional `tag` input names the tag
(`vX.Y.Z`; default: the next minor of the latest tag). It stages under strict pinning and publishes through
JReleaser. The build tool pin is moved by checking out the new
commit in `.jenesis/upstream`, building, and committing the submodule pointer.
