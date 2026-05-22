# Jenesis Modules

![crawl](https://github.com/OWNER/REPO/actions/workflows/crawl.yml/badge.svg)

A modular Java program that crawls Maven Central and records the Java module name produced by every modularised artifact. Results are written under `data/modules/<first-letter>/<module-name>[-<classifier>]` as tab-separated lines sorted newest version first, so a lookup is `head -n 1`.

## Status

`data/STATUS.md` is rewritten by the crawler at every checkpoint and reflects current position, throughput, and ETA. The Actions tab on GitHub shows each scheduled run's summary.

## Running locally

```
mvn -DskipTests package
java -p target/build.jenesis.modules-0-SNAPSHOT.jar \
     -m build.jenesis.modules/build.jenesis.modules.Main \
     --data data --budget-minutes 30
```

Replace `OWNER/REPO` in the badge above with this repository's slug once it is pushed to GitHub.
