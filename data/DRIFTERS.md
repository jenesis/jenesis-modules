# Module ownership drifters

Generated 2026-06-12. A module *drifts* when more than one groupId publishes the name and its `owners.tsv` does not yet name every publisher (no `owners.tsv`, or one that leaves some publishing groupId neither `allowed` nor `rejected`). Resolving a drift means deciding each groupId via `SetOwners` (which writes `allowed`/`rejected`); a fully-named module drops off this list.

| Category | Unresolved | Resolved via owners.tsv |
|---|---:|---:|
| explicit-rules | 0 | 369 |
| republisher | 0 | 156 |
| migration | 0 | 790 |
| fork | 0 | 394 |
| shaded | 0 | 1272 |
| tld-dropped | 0 | 71 |
| two-segments | 85 | 0 |
| unclassified | 435 | 0 |
| **total** | **520** | **3052** |

The table covers all **3572** multi-owner modules (of **36334** modules scanned).

Timeline axis spans 2017-01 .. 2026-06 (today). Per group: decision `A`=allowed `R`=rejected `?`=undecided, `*`=current owner, then the publication range, latest version, and a `=` activity bar across the axis.

## explicit-rules (0)

Hand-curated overrides: a module matching an explicit rule is assigned to a fixed owner groupId regardless of the heuristic.

- The module name equals, or falls under, a hand-curated prefix in the explicit-owner map.
- Allow every publisher whose groupId falls under the mapped owner prefix; reject all other publishers.

## republisher (0)

Earliest owner is foreign to the module name while a natural-namespace owner is also present (shaded / repackaged jars).

- The earliest (current) owner's groupId is foreign to the module name: the name does not fall under it.
- A natural-namespace owner - a publisher whose groupId the module name does fall under - is also present.
- The foreign earliest owner is still globally active (a dormant one would be a relocation, see migration).
- Allow the natural owner; reject the foreign republisher.

## migration (0)

The publishing groupId handed off over time (a rename or a relocation), so both coordinates are kept.

- Rename: a more-recent successor is the same project as the owner (a shared groupId prefix, or two shared leading segments).
- Relocation: the owner stopped at or before a credible successor took over (or the owner went globally dormant), and that successor itself owns the module namespace.
- Allow both old and new so history stays resolvable and `latest` is current.

## fork (0)

A cross-org coordinate publishes the same name while the original owner is still active.

- A more-recent cross-org coordinate (a successor) publishes the same name while the original is still active.
- The earliest publisher is itself a credible owner: it owns the module namespace, or is the closest groupId to it.
- Keep the original owner; reject the fork.

## shaded (0)

The natural-namespace owner is the earliest and most-recent publisher; every other group merely shades or bundles the name. Resolution is unchanged; this just records the decision so the module drops off the report.

- The owner is also the most-recent publisher (there is no later successor).
- The owner is the closest groupId to the module name: it shares the longest leading-segment prefix (hyphens ignored), even if the name is not strictly under it.
- Allow the natural owner; reject every group that merely shades the name.

## tld-dropped (0)

The dominant owner's groupId with its top-level domain dropped is the module-name prefix.

- The owner's groupId with its first segment (the top-level domain) removed is a prefix of the module name.
- Allow that owner; reject the rest.

## two-segments (85)

The dominant owner's groupId with its first two segments dropped is the module-name prefix.

- The owner's groupId with its first two segments removed is a prefix of the module name.
- Allow that owner; reject the rest.

| count | current owner | new owner(s) |
|---:|---|---|
| 1 | `com.telenav.cactus` | `com.telenav.cactus, com.telenav.lexakai` |
| 1 | `org.jetbrains.kotlin` | `org.jetbrains.kotlin, org.jetbrains, org.jetbrains.kotlinx, org.jetbrains.xodus, org.jetbrains.bsp, org.jetbrains.kotlinx.dataframe, org.jetbrains.lets-plot` |
| 1 | `org.jetbrains.kotlin` | `org.jetbrains.kotlin, org.jetbrains.dokka, org.jetbrains.kotlinx, org.jetbrains.lets-plot, org.jetbrains.kotlinx.dataframe, org.jetbrains.intellij.plugins, org.jetbrains` |
| 1 | `org.jetbrains.kotlin` | `org.jetbrains.kotlin, org.jetbrains.lets-plot` |
| 1 | `org.jetbrains.kotlin` | `org.jetbrains.kotlin, org.jetbrains.lets-plot, org.jetbrains.kotlinx, org.jetbrains.kotlinx.dataframe` |

```
kotlin.stdlib  [owned by `org.jetbrains.kotlin` (groupId minus two segments is the module prefix); 215 other group(s) shade the name]
  ? * org.jetbrains.kotlin                 2019-01..2023-08 1.9.10       |....==========......|
  ?   com.alibaba.ververica                2025-11..2026-06 1.20-vvr-11.7.0-3-jdk11 |..................==|
  ?   com.airbnb.viaduct                   2026-01..2026-05 1.1.0        |..................==|
  ?   org.octopusden.octopus.jira          2026-05..2026-05 2.0.0        |...................=|
  ?   io.last9                             2026-02..2026-05 2.3.5-beta.11 |..................==|
  ?   com.tidbcloud                        2026-05..2026-05 0.4.6        |...................=|
    + 216 more: org.jetbrains.kotlinx, org.virtuslab, com.volcengine, com.easemob.im, org.jetbrains.lets-plot, dev.robocode.tankroyale, de.darkatra.injector, dev.hsbrysk, net.corda, com.aliyun.odps, ca.acendas, com.seanshubin.code.structure, (+204 more)
jackson.datatype.pcollections  [owned by `com.fasterxml.jackson.datatype` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * com.fasterxml.jackson.datatype       2019-07..2026-06 2.22.0       |.....===============|
  ?   tools.jackson.datatype               2025-03..2026-05 3.1.3        |................====|
kotlin.stdlib.jdk8  [owned by `org.jetbrains.kotlin` (groupId minus two segments is the module prefix); 233 other group(s) shade the name]
  ? * org.jetbrains.kotlin                 2019-01..2023-08 1.9.10       |....==========......|
  ?   com.airbnb.viaduct                   2026-01..2026-05 1.1.0        |..................==|
  ?   com.airbnb.viaduct.javaapi           2026-05..2026-05 1.1.0        |...................=|
  ?   com.google.genai                     2026-04..2026-05 1.56.0       |...................=|
  ?   com.newrelic.agent.android           2024-09..2026-05 7.7.5        |...............=====|
  ?   com.sportradar.unifiedodds.sdk       2025-02..2026-05 4.9.0        |................====|
    + 234 more: org.apache.hudi, com.aliyun, net.corda, com.squareup, ai.realitydefender, com.gitee.melin.huaweicloud, com.kernelflux.mobile, com.aliyun.odps, pl.wtx.wordpress, io.getstream, com.displee, top.mrxiaom.mirai, (+222 more)
kotlin.stdlib.jdk7  [owned by `org.jetbrains.kotlin` (groupId minus two segments is the module prefix); 19 other group(s) shade the name]
  ? * org.jetbrains.kotlin                 2019-01..2023-08 1.9.10       |....==========......|
  ?   io.github.team-sneakymouse           2026-05..2026-05 4.0-Beta-13  |...................=|
  ?   io.pyroscope                         2025-04..2026-03 2.5.2        |.................===|
  ?   com.seanshubin.code.structure        2026-02..2026-02 1.1.1        |..................=.|
  ?   me.xcue                              2026-01..2026-01 26.0.1       |..................=.|
  ?   com.kroegerama.openapi-kgen          2023-12..2025-09 0.18.1       |..............=====.|
    + 14 more: org.partiql, io.github.wadoon.key, org.btmonier, com.slothiesmooth, com.slothiesmooth.links-detektor, hu.bme.mit.theta, com.github.shynixn.mccoroutine, dev.nelmin.spigot, com.facebook, com.cjcrafter, tech.carcadex, io.pixeloutlaw.mythicdrops, (+2 more)
kotlin.reflect  [owned by `org.jetbrains.kotlin` (groupId minus two segments is the module prefix); 60 other group(s) shade the name]
  ? * org.jetbrains.kotlin                 2019-01..2023-08 1.9.10       |....==========......|
  ?   com.airbnb.viaduct                   2026-01..2026-04 0.29.0       |..................==|
  ?   org.apache.pinot                     2025-09..2026-04 1.5.0        |.................===|
  ?   io.github.abdullahkhan118            2026-03..2026-03 1.0.4        |...................=|
  ?   io.github.tobi-laa                   2026-03..2026-03 1.0.0        |..................=.|
  ?   io.github.kshulzh.kefir              2026-03..2026-03 0.0.1        |..................=.|
    + 58 more: io.github.xilinjia.krdb, io.github.snow1026, com.browserstack, com.simprints.realm.kotlin, org.pkl-lang, com.statsig, com.infomaniak.realm.kotlin, com.solapi, io.github.honkling.commando, io.github.tabilzad.inspektor, hu.bme.mit.theta, com.beeproduced, (+46 more)
tucache.core  [owned by `co.tunan.tucache` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * co.tunan.tucache                     2024-02..2024-02 1.0.4.RELEASE |..............=.....|
  ?   io.github.tri5m                      2024-12..2026-04 1.0.6        |................====|
tucache.spring.boot.autoconfigure  [owned by `co.tunan.tucache` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * co.tunan.tucache                     2024-02..2024-02 1.0.4.RELEASE |..............=.....|
  ?   io.github.tri5m                      2024-12..2026-04 1.0.6        |................====|
tucache.spring.boot.starter  [owned by `co.tunan.tucache` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * co.tunan.tucache                     2024-02..2024-02 1.0.4.RELEASE |..............=.....|
  ?   io.github.tri5m                      2024-12..2026-04 1.0.6        |................====|
dagger  [owned by `com.google.dagger` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * com.google.dagger                    2021-06..2026-02 2.59.2       |.........==========.|
  ?   io.github.licy5352.dagger            2022-02..2026-03 2.55-kim-rc1 |..........=========.|
  ?   me.gulya.dagger                      2025-08..2025-08 2.56.2-workaround10 |.................=..|
  ?   io.github.jbock-java                 2021-10..2022-03 2.41.2       |..........=.........|
retrofit2.converter.guava  [owned by `com.squareup.retrofit2` (groupId minus two segments is the module prefix); 5 other group(s) shade the name]
  ? * com.squareup.retrofit2               2018-03..2025-05 3.0.0        |..================..|
  ?   io.github.mindcomic.retrofit2        2025-11..2026-01 3.1.2        |..................=.|
  ?   com.huanli233.retrofit2-compat       2025-02..2025-02 2.12.0-p2    |................=...|
  ?   io.github.goooler.retrofit2          2023-12..2023-12 2.10.0       |..............=.....|
  ?   io.github.zawn.retrofit2             2021-09..2021-09 2.10.6       |.........=..........|
  ?   io.github.zawn                       2021-04..2021-04 2.10.2       |.........=..........|
retrofit2.converter.jackson  [owned by `com.squareup.retrofit2` (groupId minus two segments is the module prefix); 5 other group(s) shade the name]
  ? * com.squareup.retrofit2               2018-03..2025-05 3.0.0        |..================..|
  ?   io.github.mindcomic.retrofit2        2025-11..2026-01 3.1.2        |..................=.|
  ?   com.huanli233.retrofit2-compat       2025-02..2025-02 2.12.0-p2    |................=...|
  ?   io.github.goooler.retrofit2          2023-12..2023-12 2.10.0       |..............=.....|
  ?   io.github.zawn.retrofit2             2021-09..2021-09 2.10.6       |.........=..........|
  ?   io.github.zawn                       2021-04..2021-04 2.10.2       |.........=..........|
retrofit2  [owned by `com.squareup.retrofit2` (groupId minus two segments is the module prefix); 6 other group(s) shade the name]
  ? * com.squareup.retrofit2               2018-03..2025-05 3.0.0        |..================..|
  ?   io.github.mindcomic.retrofit2        2025-11..2026-01 3.1.2        |..................=.|
  ?   com.huanli233.retrofit2-compat       2025-02..2025-02 2.12.0-p2    |................=...|
  ?   io.github.goooler.retrofit2          2023-12..2023-12 2.10.0       |..............=.....|
  ?   io.github.jamesfchen                 2022-10..2022-10 1.0.0        |............=.......|
  ?   io.github.zawn.retrofit2             2021-09..2021-09 2.10.6       |.........=..........|
    + 1 more: io.github.zawn
retrofit2.adapter.guava  [owned by `com.squareup.retrofit2` (groupId minus two segments is the module prefix); 5 other group(s) shade the name]
  ? * com.squareup.retrofit2               2018-03..2025-05 3.0.0        |..================..|
  ?   io.github.mindcomic.retrofit2        2025-11..2026-01 3.1.2        |..................=.|
  ?   com.huanli233.retrofit2-compat       2025-02..2025-02 2.12.0-p2    |................=...|
  ?   io.github.goooler.retrofit2          2023-12..2023-12 2.10.0       |..............=.....|
  ?   io.github.zawn.retrofit2             2021-09..2021-09 2.10.6       |.........=..........|
  ?   io.github.zawn                       2021-04..2021-04 2.10.2       |.........=..........|
retrofit2.adapter.java8  [owned by `com.squareup.retrofit2` (groupId minus two segments is the module prefix); 5 other group(s) shade the name]
  ? * com.squareup.retrofit2               2018-03..2025-05 3.0.0        |..================..|
  ?   io.github.mindcomic.retrofit2        2025-11..2026-01 3.1.2        |..................=.|
  ?   com.huanli233.retrofit2-compat       2025-02..2025-02 2.12.0-p2    |................=...|
  ?   io.github.goooler.retrofit2          2023-12..2023-12 2.10.0       |..............=.....|
  ?   io.github.zawn.retrofit2             2021-09..2021-09 2.10.6       |.........=..........|
  ?   io.github.zawn                       2021-04..2021-04 2.10.2       |.........=..........|
retrofit2.adapter.rxjava  [owned by `com.squareup.retrofit2` (groupId minus two segments is the module prefix); 5 other group(s) shade the name]
  ? * com.squareup.retrofit2               2018-03..2025-05 3.0.0        |..================..|
  ?   io.github.mindcomic.retrofit2        2025-11..2026-01 3.1.2        |..................=.|
  ?   com.huanli233.retrofit2-compat       2025-02..2025-02 2.12.0-p2    |................=...|
  ?   io.github.goooler.retrofit2          2023-12..2023-12 2.10.0       |..............=.....|
  ?   io.github.zawn.retrofit2             2021-09..2021-09 2.10.6       |.........=..........|
  ?   io.github.zawn                       2021-04..2021-04 2.10.2       |.........=..........|
retrofit2.adapter.rxjava2  [owned by `com.squareup.retrofit2` (groupId minus two segments is the module prefix); 5 other group(s) shade the name]
  ? * com.squareup.retrofit2               2018-03..2025-05 3.0.0        |..================..|
  ?   io.github.mindcomic.retrofit2        2025-11..2026-01 3.1.2        |..................=.|
  ?   com.huanli233.retrofit2-compat       2025-02..2025-02 2.12.0-p2    |................=...|
  ?   io.github.goooler.retrofit2          2023-12..2023-12 2.10.0       |..............=.....|
  ?   io.github.zawn.retrofit2             2021-09..2021-09 2.10.6       |.........=..........|
  ?   io.github.zawn                       2021-04..2021-04 2.10.2       |.........=..........|
retrofit2.adapter.rxjava3  [owned by `com.squareup.retrofit2` (groupId minus two segments is the module prefix); 5 other group(s) shade the name]
  ? * com.squareup.retrofit2               2020-05..2025-05 3.0.0        |.......===========..|
  ?   io.github.mindcomic.retrofit2        2025-11..2026-01 3.1.2        |..................=.|
  ?   com.huanli233.retrofit2-compat       2025-02..2025-02 2.12.0-p2    |................=...|
  ?   io.github.goooler.retrofit2          2023-12..2023-12 2.10.0       |..............=.....|
  ?   io.github.zawn.retrofit2             2021-09..2021-09 2.10.6       |.........=..........|
  ?   io.github.zawn                       2021-04..2021-04 2.10.2       |.........=..........|
retrofit2.adapter.scala  [owned by `com.squareup.retrofit2` (groupId minus two segments is the module prefix); 5 other group(s) shade the name]
  ? * com.squareup.retrofit2               2018-03..2025-05 3.0.0        |..================..|
  ?   io.github.mindcomic.retrofit2        2025-11..2026-01 3.1.2        |..................=.|
  ?   com.huanli233.retrofit2-compat       2025-02..2025-02 2.12.0-p2    |................=...|
  ?   io.github.goooler.retrofit2          2023-12..2023-12 2.10.0       |..............=.....|
  ?   io.github.zawn.retrofit2             2021-09..2021-09 2.10.6       |.........=..........|
  ?   io.github.zawn                       2021-04..2021-04 2.10.2       |.........=..........|
retrofit2.converter.gson  [owned by `com.squareup.retrofit2` (groupId minus two segments is the module prefix); 5 other group(s) shade the name]
  ? * com.squareup.retrofit2               2018-03..2025-05 3.0.0        |..================..|
  ?   io.github.mindcomic.retrofit2        2025-11..2026-01 3.1.2        |..................=.|
  ?   com.huanli233.retrofit2-compat       2025-02..2025-02 2.12.0-p2    |................=...|
  ?   io.github.goooler.retrofit2          2023-12..2023-12 2.10.0       |..............=.....|
  ?   io.github.zawn.retrofit2             2021-09..2021-09 2.10.6       |.........=..........|
  ?   io.github.zawn                       2021-04..2021-04 2.10.2       |.........=..........|
retrofit2.converter.java8  [owned by `com.squareup.retrofit2` (groupId minus two segments is the module prefix); 5 other group(s) shade the name]
  ? * com.squareup.retrofit2               2018-03..2025-05 3.0.0        |..================..|
  ?   io.github.mindcomic.retrofit2        2025-11..2026-01 3.1.2        |..................=.|
  ?   com.huanli233.retrofit2-compat       2025-02..2025-02 2.12.0-p2    |................=...|
  ?   io.github.goooler.retrofit2          2023-12..2023-12 2.10.0       |..............=.....|
  ?   io.github.zawn.retrofit2             2021-09..2021-09 2.10.6       |.........=..........|
  ?   io.github.zawn                       2021-04..2021-04 2.10.2       |.........=..........|
retrofit2.converter.jaxb  [owned by `com.squareup.retrofit2` (groupId minus two segments is the module prefix); 5 other group(s) shade the name]
  ? * com.squareup.retrofit2               2018-03..2025-05 3.0.0        |..================..|
  ?   io.github.mindcomic.retrofit2        2025-11..2026-01 3.1.2        |..................=.|
  ?   com.huanli233.retrofit2-compat       2025-02..2025-02 2.12.0-p2    |................=...|
  ?   io.github.goooler.retrofit2          2023-12..2023-12 2.10.0       |..............=.....|
  ?   io.github.zawn.retrofit2             2021-09..2021-09 2.10.6       |.........=..........|
  ?   io.github.zawn                       2021-04..2021-04 2.10.2       |.........=..........|
retrofit2.converter.moshi  [owned by `com.squareup.retrofit2` (groupId minus two segments is the module prefix); 5 other group(s) shade the name]
  ? * com.squareup.retrofit2               2018-03..2025-05 3.0.0        |..================..|
  ?   io.github.mindcomic.retrofit2        2025-11..2026-01 3.1.2        |..................=.|
  ?   com.huanli233.retrofit2-compat       2025-02..2025-02 2.12.0-p2    |................=...|
  ?   io.github.goooler.retrofit2          2023-12..2023-12 2.10.0       |..............=.....|
  ?   io.github.zawn.retrofit2             2021-09..2021-09 2.10.6       |.........=..........|
  ?   io.github.zawn                       2021-04..2021-04 2.10.2       |.........=..........|
retrofit2.converter.protobuf  [owned by `com.squareup.retrofit2` (groupId minus two segments is the module prefix); 5 other group(s) shade the name]
  ? * com.squareup.retrofit2               2018-03..2025-05 3.0.0        |..================..|
  ?   io.github.mindcomic.retrofit2        2025-11..2026-01 3.1.2        |..................=.|
  ?   com.huanli233.retrofit2-compat       2025-02..2025-02 2.12.0-p2    |................=...|
  ?   io.github.goooler.retrofit2          2023-12..2023-12 2.10.0       |..............=.....|
  ?   io.github.zawn.retrofit2             2021-09..2021-09 2.10.6       |.........=..........|
  ?   io.github.zawn                       2021-04..2021-04 2.10.2       |.........=..........|
retrofit2.converter.scalars  [owned by `com.squareup.retrofit2` (groupId minus two segments is the module prefix); 5 other group(s) shade the name]
  ? * com.squareup.retrofit2               2018-03..2025-05 3.0.0        |..================..|
  ?   io.github.mindcomic.retrofit2        2025-11..2026-01 3.1.2        |..................=.|
  ?   com.huanli233.retrofit2-compat       2025-02..2025-02 2.12.0-p2    |................=...|
  ?   io.github.goooler.retrofit2          2023-12..2023-12 2.10.0       |..............=.....|
  ?   io.github.zawn.retrofit2             2021-09..2021-09 2.10.6       |.........=..........|
  ?   io.github.zawn                       2021-04..2021-04 2.10.2       |.........=..........|
retrofit2.converter.simplexml  [owned by `com.squareup.retrofit2` (groupId minus two segments is the module prefix); 5 other group(s) shade the name]
  ? * com.squareup.retrofit2               2018-03..2025-05 3.0.0        |..================..|
  ?   io.github.mindcomic.retrofit2        2025-11..2026-01 3.1.2        |..................=.|
  ?   com.huanli233.retrofit2-compat       2025-02..2025-02 2.12.0-p2    |................=...|
  ?   io.github.goooler.retrofit2          2023-12..2023-12 2.10.0       |..............=.....|
  ?   io.github.zawn.retrofit2             2021-09..2021-09 2.10.6       |.........=..........|
  ?   io.github.zawn                       2021-04..2021-04 2.10.2       |.........=..........|
retrofit2.converter.wire  [owned by `com.squareup.retrofit2` (groupId minus two segments is the module prefix); 5 other group(s) shade the name]
  ? * com.squareup.retrofit2               2018-03..2025-05 3.0.0        |..================..|
  ?   io.github.mindcomic.retrofit2        2025-11..2026-01 3.1.2        |..................=.|
  ?   com.huanli233.retrofit2-compat       2025-02..2025-02 2.12.0-p2    |................=...|
  ?   io.github.goooler.retrofit2          2023-12..2023-12 2.10.0       |..............=.....|
  ?   io.github.zawn.retrofit2             2021-09..2021-09 2.10.6       |.........=..........|
  ?   io.github.zawn                       2021-04..2021-04 2.10.2       |.........=..........|
retrofit2.mock  [owned by `com.squareup.retrofit2` (groupId minus two segments is the module prefix); 5 other group(s) shade the name]
  ? * com.squareup.retrofit2               2018-03..2025-05 3.0.0        |..================..|
  ?   io.github.mindcomic.retrofit2        2025-11..2026-01 3.1.2        |..................=.|
  ?   com.huanli233.retrofit2-compat       2025-02..2025-02 2.12.0-p2    |................=...|
  ?   io.github.goooler.retrofit2          2023-12..2023-12 2.10.0       |..............=.....|
  ?   io.github.zawn.retrofit2             2021-09..2021-09 2.10.6       |.........=..........|
  ?   io.github.zawn                       2021-04..2021-04 2.10.2       |.........=..........|
tuweni.bytes  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1        |..........===.......|
  ?   io.consensys.tuweni                  2025-02..2025-10 2.7.2        |................===.|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0        |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
tuweni.concurrent  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1        |..........===.......|
  ?   io.consensys.tuweni                  2025-02..2025-10 2.7.2        |................===.|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0        |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
tuweni.concurrent_coroutines  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1        |..........===.......|
  ?   io.consensys.tuweni                  2025-02..2025-10 2.7.2        |................===.|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0        |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
tuweni.config  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1        |..........===.......|
  ?   io.consensys.tuweni                  2025-02..2025-10 2.7.2        |................===.|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0        |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
tuweni.crypto  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1        |..........===.......|
  ?   io.consensys.tuweni                  2025-02..2025-10 2.7.2        |................===.|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0        |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
tuweni.devp2p  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1        |..........===.......|
  ?   io.consensys.tuweni                  2025-02..2025-10 2.7.2        |................===.|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0        |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
tuweni.io  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1        |..........===.......|
  ?   io.consensys.tuweni                  2025-02..2025-10 2.7.2        |................===.|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0        |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
tuweni.junit  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1        |..........===.......|
  ?   io.consensys.tuweni                  2025-02..2025-10 2.7.2        |................===.|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0        |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
tuweni.kademlia  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1        |..........===.......|
  ?   io.consensys.tuweni                  2025-02..2025-10 2.7.2        |................===.|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0        |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
tuweni.net  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1        |..........===.......|
  ?   io.consensys.tuweni                  2025-02..2025-10 2.7.2        |................===.|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0        |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
tuweni.rlp  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1        |..........===.......|
  ?   io.consensys.tuweni                  2025-02..2025-10 2.7.2        |................===.|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0        |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
tuweni.ssz  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1        |..........===.......|
  ?   io.consensys.tuweni                  2025-02..2025-10 2.7.2        |................===.|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0        |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
tuweni.toml  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1        |..........===.......|
  ?   io.consensys.tuweni                  2025-02..2025-10 2.7.2        |................===.|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0        |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
tuweni.units  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1        |..........===.......|
  ?   io.consensys.tuweni                  2025-02..2025-10 2.7.2        |................===.|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0        |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
glide.api  [owned by `software.amazon.glide` (groupId minus two segments is the module prefix); 2 other group(s) shade the name]
  ? * software.amazon.glide                2024-06..2024-06 0.4.3        |...............=....|
  ?   io.valkey                            2024-07..2025-10 2.1.1        |...............====.|
  ?   io.github.gumpacg                    2024-08..2024-08 0.1.0        |...............=....|
kotlin.test  [owned by `org.jetbrains.kotlin` (groupId minus two segments is the module prefix); 2 other group(s) shade the name]
  ? * org.jetbrains.kotlin                 2019-01..2023-08 1.9.10       |....==========......|
  ?   xyz.block.kotlin-formatter           2025-01..2025-08 1.3.0        |................==..|
  ?   org.archguard.scanner                2023-05..2024-10 2.2.0        |.............====...|
afterburner.fx  [owned by `com.dlsc.afterburner` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * com.dlsc.afterburner                 2019-10..2023-07 2.3.0        |.....=========......|
  ?   org.jabref                           2023-09..2023-09 2.0.0        |.............=......|
kotlin.test.junit  [owned by `org.jetbrains.kotlin` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.jetbrains.kotlin                 2020-07..2023-08 1.9.10       |.......=======......|
  ?   org.archguard.scanner                2022-06..2022-12 2.0.0-beta.5 |...........==.......|
  ?   org.jetbrains.lets-plot              2021-04..2021-06 2.0.4        |.........=..........|
tuweni.wallet  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1        |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
tuweni.scuttlebutt_rpc  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1        |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
tuweni.scuttlebutt_handshake  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1        |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
tuweni.scuttlebutt_discovery  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1        |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
tuweni.scuttlebutt_client_lib  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1        |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
tuweni.scuttlebutt  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1        |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
tuweni.rlpx  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1        |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
tuweni.pow  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1        |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
tuweni.plumtree  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1        |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
tuweni.peer_repository  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1        |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
tuweni.metrics  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1        |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
tuweni.merkle_trie  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1        |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
tuweni.les  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1        |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
tuweni.kv  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1        |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
tuweni.jsonrpc_app  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1        |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
tuweni.jsonrpc  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1        |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
tuweni.hobbits_relayer  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1        |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
tuweni.hobbits  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1        |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
tuweni.gossip  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1        |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
tuweni.genesis  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1        |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
tuweni.evm_dsl  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-09..2022-11 2.3.1        |...........==.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
tuweni.evm  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1        |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
tuweni.ethstats  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1        |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
tuweni.eth_repository  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1        |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
tuweni.eth_precompiles  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-09..2022-11 2.3.1        |...........==.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
tuweni.eth_faucet  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1        |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
tuweni.eth_crawler  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1        |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
tuweni.eth_client_ui  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1        |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
tuweni.eth_client_app  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1        |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
tuweni.eth_client  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1        |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
tuweni.eth_blockprocessor  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-09..2022-11 2.3.1        |...........==.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
tuweni.eth  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1        |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
tuweni.dns_discovery  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1        |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
tuweni.devp2p_proxy  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1        |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
tuweni.devp2p_eth  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1        |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
tuweni.app_commons  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1        |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2        |.............=......|
cactus.maven.xml  [owned by `com.telenav.cactus` (groupId minus two segments is the module prefix); 0 other group(s) shade the name]
  ? * com.telenav.cactus                   2022-06..2022-11 1.5.49       |...........==.......|
  ?   com.telenav.lexakai                  2022-09..2022-10 1.0.13       |...........==.......|
telegram4j.tl.api  [owned by `io.github.telegram4j` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * io.github.telegram4j                 2022-02..2022-02 0.1.0        |..........=.........|
  ?   com.telegram4j                       2022-09..2022-09 0.1.1        |...........=........|
telegram4j.tl  [owned by `io.github.telegram4j` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * io.github.telegram4j                 2022-02..2022-02 0.1.0        |..........=.........|
  ?   com.telegram4j                       2022-09..2022-09 0.1.1        |...........=........|
stasgora.observetree  [owned by `io.github.stasgora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * io.github.stasgora                   2019-04..2019-09 1.0.3        |.....=..............|
  ?   dev.sgora                            2019-10..2019-10 1.0.3.1      |.....=..............|
```

## unclassified (435)

Multiple publishers with no natural-namespace owner present: a genuine collision the heuristic cannot settle.

- More than one publisher, and none is a credible owner: no natural-namespace owner is present and the earliest is not the closest groupId.
- Left unresolved - no owners.tsv is written - for a later hand decision.

_Showing the 200 most recently active of 435. For the full list, emit the SetOwners file: `-Djenesis.crawler.drift.emit=unclassified`._

```
com.google.gson  [no clear owner; `com.google.code.gson` is earliest and most recent]
  ? * com.google.code.gson                 2019-10..2026-04 2.14.0       |.....===============|
  ?   com.aliyun                           2021-09..2026-06 9.3.2        |.........===========|
  ?   com.alibaba.ververica                2025-06..2026-06 1.20-vvr-11.7.0-3-jdk11 |.................===|
  ?   org.openjproxy                       2025-09..2026-05 0.4.21-beta  |.................===|
  ?   dev.uebelacker.babeli                2026-05..2026-05 1.0.1        |...................=|
  ?   org.sonarsource.java                 2024-11..2026-05 8.30.0.43910 |................====|
    + 522 more: org.sonarsource.dotnet, org.sonarsource.sonarlint.ls, com.github.transbankdevelopers, io.lakefs, org.sonarsource.sonarlint.core, org.jetbrains.kotlin, com.ascentstream.pulsar, io.lionweb, ai.chronon, cn.signit.sdk, io.arenadata.hive, dev.blitical, (+510 more)
com.google.common  [no clear owner; `com.google.guava` is earliest and most recent]
  ? * com.google.guava                     2017-07..2026-04 33.6.0-jre   |.===================|
  ?   com.alibaba.ververica                2026-01..2026-06 1.20-vvr-11.7.0-3-jdk11 |..................==|
  ?   io.orkes.conductor                   2026-05..2026-05 5.0.3        |...................=|
  ?   io.digiexpress                       2025-10..2026-05 6.0.21       |..................==|
  ?   org.foundationdb                     2026-01..2026-05 4.12.7.0     |..................==|
  ?   net.sourceforge.plantuml             2025-06..2026-05 1.2026.5     |.................===|
    + 65 more: io.acryl, io.javelit, de.m3y.prometheus.exporter.fsimage, org.apache.spark, org.apache.iceberg, org.apache.phoenix, org.conductoross, io.yki.sapply, org.apache.calcite.avatica, org.jboss.shrinkwrap.resolver, org.talend.sdk.component.sample.feature, org.apache.sedona, (+53 more)
org.apache.commons.codec  [no clear owner; `commons-codec` is earliest and most recent]
  ? * commons-codec                        2017-10..2020-08 1.15         |.=======............|
  ?   com.alibaba.ververica                2021-06..2026-06 1.20-vvr-11.7.0-3-jdk11 |.........===========|
  ?   software.amazon.awssdk               2024-07..2026-05 2.44.14      |...............=====|
  ?   ai.platon.gora                       2026-05..2026-05 1.0.0        |...................=|
  ?   org.operaton.bpm.extension           2024-12..2026-05 2.1.0        |................====|
  ?   org.apache.tika                      2024-03..2026-05 3.3.1        |...............=====|
    + 75 more: io.gitlab.cupofcode, com.suprsend, com.mirakl, io.github.rsv-code, com.gitee.melin, org.apache.druid.extensions.contrib, org.wso2.orbit.org.opensaml, org.ops4j.pax.url, io.github.ptc-alm, org.apache.jackrabbit, com.qcloud, com.republicate.modality, (+63 more)
org.objectweb.asm.tree.analysis  [no clear owner; `org.ow2.asm` is earliest and most recent]
  ? * org.ow2.asm                          2018-01..2026-05 9.10.1       |..==================|
  ?   com.codenameone                      2025-12..2026-05 7.0.247      |..................==|
  ?   org.mock-server                      2026-05..2026-05 6.1.0        |...................=|
  ?   org.teavm                            2023-03..2026-05 0.14.1       |............========|
  ?   org.bonitasoft.bpm                   2025-03..2026-04 9.0.9        |.................===|
  ?   io.github.moliholy                   2026-04..2026-04 0.3.0        |...................=|
    + 9 more: com.webforj, com.weedow, org.openidentityplatform.openidm.tools, org.noear, fish.payara.extras, za.co.absa.hermes, com.yotpo, org.apache.felix, build.please
org.objectweb.asm  [no clear owner; `org.ow2.asm` is earliest and most recent]
  ? * org.ow2.asm                          2017-07..2026-05 9.10.1       |.===================|
  ?   com.my-oli                           2025-05..2026-05 1.1.1        |.................===|
  ?   net.corda                            2020-07..2026-05 4.11.8       |.......=============|
  ?   org.teavm                            2023-03..2026-05 0.14.1       |............========|
  ?   org.virtuslab.scala-cli              2023-05..2026-05 1.14.0       |.............=======|
  ?   org.tiatesting                       2024-04..2026-05 0.1.17       |...............=====|
    + 157 more: com.microsoft.azure.kusto, com.pinterest.psc, com.datadoghq, org.apache.iotdb, com.github.jnr, io.github.nieuwmijnleven.jadex, be.ugent.idlab.knows, org.fitnesse, io.github.vdaburon, org.soot-oss, org.noear, org.apache.hugegraph, (+145 more)
vault.java.driver  [no clear owner; `com.bettercloud` is earliest and most recent]
  ? * com.bettercloud                      2019-06..2019-12 5.1.0        |.....==.............|
  ?   io.github.jopenlibs                  2022-10..2026-05 6.2.2        |............========|
  ?   io.axual.utilities.config.providers  2020-06..2024-11 1.2.0        |.......==========...|
  ?   edu.utexas.tacc.tapis                2021-10..2021-10 5.1.2        |..........=.........|
org.objectweb.asm.tree  [no clear owner; `org.ow2.asm` is earliest and most recent]
  ? * org.ow2.asm                          2017-07..2026-05 9.10.1       |.===================|
  ?   io.killedkenny.crossfuzz             2026-05..2026-05 0.0.3        |...................=|
  ?   org.teavm                            2023-03..2026-05 0.14.1       |............========|
  ?   org.jetbrains.compose.hot-reload     2025-10..2026-05 1.2.0-alpha01 |..................==|
  ?   io.joynr.tools.generator             2021-05..2026-04 1.24.7       |.........===========|
  ?   ch.exense.step                       2025-11..2026-03 3.29.4       |..................==|
    + 13 more: com.scylladb, com.lihaoyi, io.joern, io.github.llmagentbuilder, com.jordansamhi, com.liferay, com.uber.nullaway, com.houxinlin, io.github.houxinlin, com.autonomousapps, org.netbeans.external, com.guujiang, (+1 more)
org.objectweb.asm.commons  [no clear owner; `org.ow2.asm` is earliest and most recent]
  ? * org.ow2.asm                          2017-07..2026-05 9.10.1       |.===================|
  ?   io.debezium                          2026-02..2026-05 3.6.0.Beta1  |..................==|
  ?   io.joern                             2025-06..2026-05 12.2_744c5dee92-202605281342 |.................===|
  ?   org.teavm                            2023-03..2026-05 0.14.1       |............========|
  ?   com.appdynamics                      2024-01..2026-04 26.3.1       |..............======|
  ?   com.apollographql.apollo             2024-07..2026-04 4.4.3        |...............=====|
    + 33 more: org.copper-engine, cn.iservicego, org.tango-controls, com.apollographql.apollo3, de.firemage.autograder, com.yugabyte, com.newrelic.agent.android, com.gradleup, org.tango-controls.pogo, software.amazon.disco, org.realityforge.shade, org.javastro.vodsl, (+21 more)
org.apache.commons.io  [no clear owner; `commons-io` is earliest and most recent]
  ? * commons-io                           2017-10..2021-07 2.11.0       |.=========..........|
  ?   no.entur                             2024-03..2026-05 1.115.0      |...............=====|
  ?   org.sonarsource.python               2024-10..2026-05 5.23.0.33560 |................====|
  ?   com.github.cafdataprocessing.workers.languagedetection 2024-11..2026-05 7.1.0-1677   |................====|
  ?   org.teavm                            2024-04..2026-05 0.14.1       |...............=====|
  ?   org.apache.hudi                      2025-02..2026-05 1.2.0        |................====|
    + 92 more: org.apache.tika, io.github.zgrge, io.prophecy, org.bidib.jbidib, org.apache.flink, com.ascentstream.distributedlog, com.networknt, io.boxlang, com.io7m.calino, org.apache.jackrabbit, eu.europa.ted.eforms, io.openliberty.tools, (+80 more)
java.xml.bind  [no clear owner; `javax.xml.bind` is earliest and most recent]
  ? * javax.xml.bind                       2017-07..2018-09 2.3.1        |.===................|
  ?   com.yahoo.vespa                      2020-05..2026-05 8.696.20     |.......=============|
  ?   org.apache.tika                      2018-09..2026-05 3.3.1        |...=================|
  ?   de.fraunhofer.iosb.ilt               2021-02..2026-05 0.15         |........============|
  ?   org.metricshub                       2025-05..2026-05 3.9.04       |.................===|
  ?   org.kendar.protocol                  2026-02..2026-05 4.3.9        |..................==|
    + 184 more: io.mosip.esignet, org.wso2.msf4j.perftest.echo, org.wso2.msf4j, org.wso2.msf4j.samples, org.wso2.msf4j.sample, org.apache.flink, org.apache.paimon, org.apache.fluss, de.fraunhofer.iosb.ilt.FROST-Server, org.verapdf.apps, org.apache.pinot, com.aliyun.openservices.aiservice, (+172 more)
com.sun.tools.jxc  [no clear owner; `org.glassfish.jaxb` is earliest and most recent]
  ? * org.glassfish.jaxb                   2018-07..2019-01 2.3.2        |...==...............|
  ?   com.sun.xml.bind                     2018-07..2026-05 4.0.9        |...=================|
  ?   cn.lzgabel.jaxb.xml.bind             2022-03..2022-03 4.0.0        |..........=.........|
com.sun.tools.xjc  [no clear owner; `org.glassfish.jaxb` is earliest and most recent]
  ? * org.glassfish.jaxb                   2018-07..2019-01 2.3.2        |...==...............|
  ?   com.sun.xml.bind                     2018-07..2026-05 4.0.9        |...=================|
  ?   cn.lzgabel.jaxb.xml.bind             2022-03..2022-03 4.0.0        |..........=.........|
  ?   com.github.shynixn                   2019-02..2019-02 1.0          |....=...............|
org.apache.commons.logging  [no clear owner; `org.slf4j` is earliest and most recent]
  ? * org.slf4j                            2017-04..2026-05 2.0.18       |====================|
  ?   org.open-metadata                    2025-11..2026-05 1.12.9       |..................==|
  ?   org.operaton.bpm.extension           2026-02..2026-05 2.1.0        |..................==|
  ?   org.apache.tika                      2022-09..2026-05 3.3.1        |............========|
  ?   net.ontopia                          2026-05..2026-05 5.7.0        |...................=|
  ?   org.jboss.pnc.build-agent            2026-05..2026-05 1.2.2        |...................=|
    + 30 more: com.facebook.presto.hive, com.nordstrom.ui-tools, org.beangle.sas, io.github.linagora.linid.im, io.brunoborges, commons-logging, org.apache.orc, io.pivotal.cfenv, org.operaton.bpm, de.redsix, org.jboss.logging, com.uchicom, (+18 more)
org.apache.commons.validator  [no clear owner; `org.chronos-eaas` is earliest and most recent]
  ? * org.chronos-eaas                     2024-07..2025-01 2.5.1        |...............==...|
  ?   org.apiaddicts.apitools.dosonarapi   2026-05..2026-05 2.0.0        |...................=|
org.hiero.base.utility  [no clear owner; `com.swirlds` is earliest and most recent]
  ? * com.swirlds                          2025-04..2025-06 0.62.11      |.................=..|
  ?   com.hedera.hashgraph                 2025-05..2026-05 0.75.0-rc.3  |.................===|
org.hiero.consensus.event.creator.impl  [no clear owner; `com.swirlds` is earliest and most recent]
  ? * com.swirlds                          2025-04..2025-06 0.62.11      |.................=..|
  ?   com.hedera.hashgraph                 2025-05..2026-05 0.75.0-rc.3  |.................===|
org.hiero.base.concurrent  [no clear owner; `com.swirlds` is earliest and most recent]
  ? * com.swirlds                          2025-04..2025-06 0.62.11      |.................=..|
  ?   com.hedera.hashgraph                 2025-05..2026-05 0.75.0-rc.3  |.................===|
org.hiero.base.crypto  [no clear owner; `com.swirlds` is earliest and most recent]
  ? * com.swirlds                          2025-04..2025-06 0.62.11      |.................=..|
  ?   com.hedera.hashgraph                 2025-05..2026-05 0.75.0-rc.3  |.................===|
org.hiero.consensus.event.creator  [no clear owner; `com.swirlds` is earliest and most recent]
  ? * com.swirlds                          2025-04..2025-06 0.62.11      |.................=..|
  ?   com.hedera.hashgraph                 2025-05..2026-05 0.75.0-rc.3  |.................===|
org.hiero.consensus.gossip  [no clear owner; `com.swirlds` is earliest and most recent]
  ? * com.swirlds                          2024-12..2025-06 0.62.11      |................==..|
  ?   com.hedera.hashgraph                 2025-05..2026-05 0.75.0-rc.3  |.................===|
org.hiero.consensus.gossip.impl  [no clear owner; `com.swirlds` is earliest and most recent]
  ? * com.swirlds                          2024-12..2025-06 0.62.11      |................==..|
  ?   com.hedera.hashgraph                 2025-05..2026-05 0.75.0-rc.3  |.................===|
org.hiero.consensus.utility  [no clear owner; `com.swirlds` is earliest and most recent]
  ? * com.swirlds                          2025-04..2025-06 0.62.11      |.................=..|
  ?   com.hedera.hashgraph                 2025-05..2026-05 0.75.0-rc.3  |.................===|
org.hiero.consensus.model  [no clear owner; `com.swirlds` is earliest and most recent]
  ? * com.swirlds                          2025-04..2025-06 0.62.11      |.................=..|
  ?   com.hedera.hashgraph                 2025-05..2026-05 0.75.0-rc.3  |.................===|
org.apache.commons.csv  [no clear owner; `io.github.pustike` is earliest and most recent]
  ? * io.github.pustike                    2019-01..2019-07 1.7.0        |....==..............|
  ?   org.sonarsource.scanner.engine       2026-05..2026-05 12.36.0.3399 |...................=|
  ?   com.orientechnologies                2026-03..2026-05 3.2.52       |...................=|
  ?   com.guicedee.modules.services        2026-04..2026-05 2.0.2        |...................=|
  ?   org.apache.pinot                     2024-08..2026-04 1.5.0        |...............=====|
  ?   org.testingisdocumenting.znai        2026-01..2026-03 1.86         |..................=.|
    + 9 more: be.ugent.idlab.knows, io.kestra.plugin, xyz.ottr.lutra, org.jetbrains.kotlinx, com.wizzdi, io.telicent.jena.graphql, io.telicent.jena, org.apache.jena, com.guicedee.services
VirtualizedFX  [no clear owner; `io.github.palexdev` is earliest and most recent]
  ? * io.github.palexdev                   2022-03..2026-05 25.1.16      |..........==========|
  ?   org.glavo.materialfx                 2022-04..2022-04 11.2.6       |...........=........|
org.apache.commons.cli  [no clear owner; `org.apache.shiro.tools` is earliest and most recent]
  ? * org.apache.shiro.tools               2023-10..2023-10 1.13.0       |..............=.....|
  ?   org.teavm                            2024-04..2026-05 0.14.1       |...............=====|
  ?   io.github.vdaburon                   2024-01..2026-05 5.1          |..............======|
  ?   org.apktool                          2023-12..2026-04 3.0.2        |..............======|
  ?   com.ericsson.bss.cassandra.ecaudit   2024-08..2026-03 3.1.5        |...............=====|
  ?   org.imixs.bpmn                       2025-05..2026-03 1.2.9        |.................===|
    + 23 more: io.github.gvergine, com.amazonaws, io.github.706412584, com.legsem.legstar, dev.walgo, org.apache.phoenix.thirdparty, org.apache.meecrowave, org.apache.james, net.thisptr, us.poliscore, com.github.oboehm, org.apache.hbase, (+11 more)
org.objectweb.asm.util  [no clear owner; `org.ow2.asm` is earliest and most recent]
  ? * org.ow2.asm                          2017-07..2026-05 9.10.1       |.===================|
  ?   org.teavm                            2023-03..2026-05 0.14.1       |............========|
  ?   io.jactl                             2023-04..2026-05 2.8.0        |.............=======|
  ?   io.camunda                           2025-09..2026-05 8.9.5        |.................===|
  ?   com.vmlens                           2025-05..2026-04 1.2.28       |.................===|
  ?   com.webforj                          2025-02..2025-05 25.01        |................==..|
    + 13 more: io.joynr.tools.generator, io.joern, com.rookout, io.btrace, com.codacy, org.soot-oss, org.activecomponents.jadex, com.github.luxlang, org.geneweaver, com.github.pxav.kelp, net.amygdalum, org.python, (+1 more)
com.ctc.wstx  [no clear owner; `com.fasterxml.woodstox` is earliest and most recent]
  ? * com.fasterxml.woodstox               2018-03..2026-05 7.2.0        |..==================|
  ?   org.uma.jmetal                       2025-12..2026-05 7.3          |..................==|
  ?   org.bidib.jbidib                     2021-12..2026-05 2.0.44       |..........==========|
  ?   gov.nih.ncats                        2022-01..2026-03 1.0.26       |..........==========|
  ?   org.hpccsystems                      2022-02..2026-03 9.12.94-1    |..........==========|
  ?   com.backpackcloud                    2025-03..2026-01 2.1.0        |.................==.|
    + 17 more: com.liferay.portal, de.fraunhofer.iosb.ilt.FROST-Server, com.ibm.jsonata4java, se.signatureservice.support, com.liferay, net.pincette, org.opengis.cite, org.immregistries, com.testdroid, org.sonarsource.slang, com.checkmarx, com.github.spoonlabs, (+5 more)
bus.starter  [no clear owner; `org.miaixz` is earliest and most recent]
  ? * org.miaixz                           2025-05..2026-05 8.6.11       |.................===|
  ?   io.github.rassafel                   2025-07..2025-07 0.0.1        |.................=..|
org.dnsjava  [no clear owner; `dnsjava` is earliest and most recent]
  ? * dnsjava                              2019-05..2026-05 3.6.5        |.....===============|
  ?   de.m3y.hadoop.hdfs.hfsa              2025-02..2026-05 1.4.0        |................====|
  ?   org.apache.phoenix                   2025-09..2026-05 5.3.1        |.................===|
  ?   com.hazelcast.jet                    2025-10..2026-05 5.7.0        |..................==|
  ?   com.helger.peppol.mcp                2026-05..2026-05 0.5.1        |...................=|
  ?   io.jikkou                            2026-05..2026-05 1.0.0        |...................=|
    + 16 more: org.apache.atlas, org.apache.beam, org.apache.paimon, com.clickzetta, com.alibaba.polardbx, org.apache.pinot, io.github.littleproxy, org.apache.hbase, com.foilen, org.apache.kudu, dev.redcoke, org.apache.avro, (+4 more)
org.jfree.chart  [no clear owner; `de.enflexit` is earliest and most recent]
  ? * de.enflexit                          2025-02..2025-02 1.5.6        |................=...|
  ?   io.github.jiaweim                    2026-05..2026-05 2.0.0        |...................=|
java.json  [no clear owner; `javax.json` is earliest and most recent]
  ? * javax.json                           2017-01..2018-11 1.1.4        |=====...............|
  ?   org.choco-solver                     2019-07..2026-05 6.0.1        |.....===============|
  ?   com.amihaiemil.web                   2021-02..2024-08 8.0.6        |........========....|
  ?   com.scalar-labs                      2019-02..2024-03 2.2.0        |....===========.....|
  ?   org.apache.sling                     2022-10..2023-11 1.1.8        |............===.....|
  ?   com.atomgraph.etl.json               2022-11..2023-08 1.0.7        |............==......|
    + 11 more: org.openpreservation.jhove, com.artipie, com.onespan.integration, org.odftoolkit, org.finra.herd, com.bitplan.wikifrontend, net.pincette, org.glassfish, com.phenixrts.edgeauth, jakarta.json, de.julielab
org.apache.commons.beanutils  [no clear owner; `com.guicedee.services` is earliest and most recent]
  ? * com.guicedee.services                2020-06..2022-02 1.2.2.1-jre17 |.......====.........|
  ?   org.wildfly                          2025-06..2026-05 40.0.0.Final |.................===|
  ?   org.jvnet.jaxb                       2025-09..2026-05 4.0.14       |.................===|
  ?   com.guicedee.modules.services        2026-04..2026-05 2.0.2        |...................=|
  ?   com.github.bld-commons               2026-01..2026-05 3.0.19       |..................==|
  ?   kg.apc                               2025-06..2026-03 1.12         |.................===|
    + 2 more: com.github.bordertech.wcomponents, org.onebusaway
java.ws.rs  [no clear owner; `javax.ws.rs` is earliest and most recent]
  ? * javax.ws.rs                          2017-06..2018-08 2.1.1        |.===................|
  ?   org.jboss.pnc.build-agent            2021-07..2026-05 1.2.3        |.........===========|
  ?   org.apache.hadoop                    2026-03..2026-03 3.5.0        |...................=|
  ?   net.oneandone.ioc-unit               2021-09..2025-11 2.0.51       |.........==========.|
  ?   com.scylladb                         2025-06..2025-09 1.2.6        |.................=..|
  ?   org.opencb.opencga                   2024-03..2025-08 3.6.0        |..............====..|
    + 54 more: io.streamnative, io.github.willena, com.liferay, com.inteligr8.activiti, cn.langpy, io.github.fernandolopes, com.epam.reportportal, com.github.openstack4j.core, org.apache.opennlp, org.moskito, com.mailintegrate, dev.parodos, (+42 more)
library  [no clear owner; `build.buf.prototype` is earliest and most recent]
  ? * build.buf.prototype                  2023-01..2023-01 v0.0.0-test0120 |............=.......|
  ?   com.connectrpc                       2023-09..2026-05 0.8.2        |.............=======|
  ?   build.buf                            2023-01..2023-09 0.1.10       |............==......|
okhttp  [no clear owner; `build.buf` is earliest and most recent]
  ? * build.buf                            2023-02..2023-09 0.1.10       |............==......|
  ?   com.connectrpc                       2023-09..2026-05 0.8.2        |.............=======|
flying.saucer.pdf  [no clear owner; `org.xhtmlrenderer` is earliest and most recent]
  ? * org.xhtmlrenderer                    2024-09..2026-05 10.2.2       |...............=====|
  ?   io.github.openpdfsaucer              2025-03..2025-05 2.0.9        |................==..|
netty.socketio.core  [no clear owner; `io.github.neatguycoding` is earliest and most recent]
  ? * io.github.neatguycoding              2025-10..2025-11 3.0.1        |..................=.|
  ?   com.socketio4j                       2025-11..2026-05 4.0.1        |..................==|
netty.socketio.spring  [no clear owner; `io.github.neatguycoding` is earliest and most recent]
  ? * io.github.neatguycoding              2025-10..2025-11 3.0.1        |..................=.|
  ?   com.socketio4j                       2025-11..2026-05 4.0.1        |..................==|
io.github.bucket4j.caffeine  [no clear owner; `com.github.vladimir-bukhtoyarov` is earliest and most recent]
  ? * com.github.vladimir-bukhtoyarov      2022-03..2024-04 8.0.1        |..........======....|
  ?   com.bucket4j                         2022-07..2026-05 8.19.0       |...........=========|
io.github.bucket4j.coherence  [no clear owner; `com.github.vladimir-bukhtoyarov` is earliest and most recent]
  ? * com.github.vladimir-bukhtoyarov      2019-11..2024-04 8.0.1        |......==========....|
  ?   com.bucket4j                         2022-07..2026-05 8.19.0       |...........=========|
io.github.bucket4j.core  [no clear owner; `com.github.vladimir-bukhtoyarov` is earliest and most recent]
  ? * com.github.vladimir-bukhtoyarov      2019-11..2024-04 8.0.1        |......==========....|
  ?   com.bucket4j                         2022-07..2026-05 8.19.0       |...........=========|
io.github.bucket4j.hazelcast  [no clear owner; `com.github.vladimir-bukhtoyarov` is earliest and most recent]
  ? * com.github.vladimir-bukhtoyarov      2019-11..2024-04 8.0.1        |......==========....|
  ?   com.bucket4j                         2022-07..2026-05 8.19.0       |...........=========|
io.github.bucket4j.ignite  [no clear owner; `com.github.vladimir-bukhtoyarov` is earliest and most recent]
  ? * com.github.vladimir-bukhtoyarov      2019-11..2024-04 8.0.1        |......==========....|
  ?   com.bucket4j                         2022-07..2026-05 8.19.0       |...........=========|
io.github.bucket4j.infinispan  [no clear owner; `com.github.vladimir-bukhtoyarov` is earliest and most recent]
  ? * com.github.vladimir-bukhtoyarov      2019-11..2024-04 8.0.1        |......==========....|
  ?   com.bucket4j                         2022-07..2026-05 8.19.0       |...........=========|
io.github.bucket4j.jcache  [no clear owner; `com.github.vladimir-bukhtoyarov` is earliest and most recent]
  ? * com.github.vladimir-bukhtoyarov      2019-11..2024-04 8.0.1        |......==========....|
  ?   com.bucket4j                         2022-07..2026-05 8.19.0       |...........=========|
io.github.bucket4j.mysql  [no clear owner; `com.github.vladimir-bukhtoyarov` is earliest and most recent]
  ? * com.github.vladimir-bukhtoyarov      2022-03..2024-04 8.0.1        |..........======....|
  ?   com.bucket4j                         2022-07..2026-05 8.19.0       |...........=========|
io.github.bucket4j.postgresql  [no clear owner; `com.github.vladimir-bukhtoyarov` is earliest and most recent]
  ? * com.github.vladimir-bukhtoyarov      2022-03..2024-04 8.0.1        |..........======....|
  ?   com.bucket4j                         2022-07..2026-05 8.19.0       |...........=========|
lombok  [no clear owner; `org.projectlombok` is earliest and most recent]
  ? * org.projectlombok                    2018-05..2026-04 1.18.46      |...=================|
  ?   io.mosip.esignet.plugin.sunbirdrc    2025-02..2026-05 1.4.0        |................====|
  ?   org.eclipse.hawkbit                  2026-03..2026-04 1.0.3        |...................=|
  ?   io.inji.certify.sunbirdrc            2026-03..2026-03 0.6.0        |...................=|
  ?   dev.alllexey                         2025-10..2026-03 1.5.0        |..................==|
  ?   net.polyv                            2020-09..2026-03 2.2.8        |.......============.|
    + 97 more: com.scanoss, com.huaweicloud.dws, net.wirelabs, cn.fyupeng, io.github.alllexey123, io.mosip.esignet.sunbirdrc, io.mosip.certify.sunbirdrc, io.github.opentelekomcloud, io.github.version-pulse, org.qubership.automation, io.github.devlibx.easy, org.sentrysoftware, (+85 more)
com.kingbase8.jdbc  [no clear owner; `org.jeecgframework` is earliest and most recent]
  ? * org.jeecgframework                   2024-06..2024-06 9.0.0        |...............=....|
  ?   io.github.iscasdmo                   2026-05..2026-05 8.6.0        |...................=|
  ?   cn.com.kingbase                      2025-04..2026-02 9.0.1.jre6   |.................==.|
org.newsclub.net.unix  [no clear owner; `com.kohlschutter.junixsocket` is earliest and most recent]
  ? * com.kohlschutter.junixsocket         2018-12..2024-09 2.10.1       |....=============...|
  ?   com.sbbsystems.flink                 2026-01..2026-05 3.4.3        |..................==|
  ?   net.corda                            2025-09..2026-05 4.14.2       |.................===|
  ?   org.jam4s                            2025-10..2025-12 0.7.2-alpha0 |..................=.|
  ?   io.nosqlbench                        2020-02..2020-03 3.12.47      |......=.............|
  ?   io.engineblock                       2020-01..2020-01 2.12.65      |......=.............|
ihub.core  [no clear owner; `pub.ihub.lib` is earliest and most recent]
  ? * pub.ihub.lib                         2021-09..2026-05 1.7.7        |.........===========|
  ?   pub.ihub.integration                 2024-03..2025-05 0.1.12       |...............===..|
  ?   pub.ihub.module                      2024-04..2025-05 0.2.2        |...............===..|
liqp  [no clear owner; `nl.big-o` is earliest and most recent]
  ? * nl.big-o                             2018-06..2025-04 0.9.2.3      |...===============..|
  ?   io.github.luoxuansz                  2025-11..2026-05 1.1.3        |..................==|
  ?   com.kohlschutter                     2023-12..2023-12 0.8.5.4      |..............=.....|
com.oracle.truffle.tools.profiler  [no clear owner; `org.graalvm.tools` is earliest and most recent]
  ? * org.graalvm.tools                    2018-10..2026-04 25.0.3       |...=================|
  ?   com.orientechnologies                2025-12..2026-05 3.2.52       |..................==|
org.eclipse.osgi  [no clear owner; `org.eclipse.tycho` is earliest and most recent]
  ? * org.eclipse.tycho                    2018-05..2018-05 3.13.0.v20180226-1711 |...=................|
  ?   io.joynr.tools.generator             2025-01..2026-05 1.26.5       |................====|
  ?   org.alfasoftware                     2021-05..2026-04 2.7.0        |.........===========|
  ?   net.kieker-monitoring                2026-04..2026-04 2.0.3        |...................=|
  ?   org.bonitasoft.bpm                   2023-10..2026-04 9.0.9        |..............======|
  ?   de.funfried.libraries                2022-05..2026-03 1.0.13       |...........=========|
    + 22 more: org.eclipse.platform, io.github.alien-tools, org.tango-controls, org.tango-controls.pogo, ch.reportingsoft.birt, com.liferay, net.revelc.code.formatter, org.kie.j2cl.tools.external, io.github.dogla, com.vertispan.j2cl.external, fr.inria.gforge.spirals, org.geneweaver, (+10 more)
jakarta.messaging  [no clear owner; `jakarta.jms` is earliest and most recent]
  ? * jakarta.jms                          2022-03..2022-03 3.1.0        |..........=.........|
  ?   org.apache.storm                     2025-05..2026-05 2.8.8        |.................===|
  ?   be.vlaanderen.informatievlaanderen.ldes.ldio 2024-12..2024-12 2.12.0       |................=...|
org.apache.commons.beanutils2  [no clear owner; `org.onebusaway` is earliest and most recent]
  ? * org.onebusaway                       2025-05..2026-05 12.0.1       |.................===|
  ?   com.github.bordertech.wcomponents    2025-12..2026-01 1.5.39       |..................=.|
org.apache.commons.mail  [no clear owner; `com.github.ppodgorsek.email` is earliest and most recent]
  ? * com.github.ppodgorsek.email          2023-06..2023-06 2.0.0        |.............=......|
  ?   io.prophecy                          2024-08..2026-05 3.5.0-onprem-9.3.0 |...............=====|
cache.annotations.ri.guice  [no clear owner; `com.jwebmp.thirdparty.jcache` is earliest and most recent]
  ? * com.jwebmp.thirdparty.jcache         2019-05..2019-08 0.68.0.1     |.....=..............|
  ?   com.guicedee.modules.services        2026-04..2026-05 2.0.2        |...................=|
  ?   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17 |......=====.........|
aopalliance  [no clear owner; `com.jwebmp.jre11` is earliest and most recent]
  ? * com.jwebmp.jre11                     2018-12..2018-12 0.63.0.19    |....=...............|
  ?   com.guicedee.modules.services        2026-04..2026-05 2.0.2        |...................=|
  ?   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17 |......=====.........|
  ?   com.jwebmp.thirdparty                2019-04..2019-08 0.68.0.1     |....==..............|
  ?   com.jwebmp                           2019-01..2019-04 0.66.0.1     |....=...............|
cache.api  [no clear owner; `com.jwebmp.thirdparty.jcache` is earliest and most recent]
  ? * com.jwebmp.thirdparty.jcache         2019-05..2019-08 0.68.0.1     |.....=..............|
  ?   com.guicedee.modules.services        2026-04..2026-05 2.0.2        |...................=|
  ?   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17 |......=====.........|
net.sf.uadetector.resources  [no clear owner; `com.jwebmp.jre11` is earliest and most recent]
  ? * com.jwebmp.jre11                     2018-11..2018-12 0.63.0.19    |....=...............|
  ?   com.guicedee.modules.services        2026-04..2026-05 2.0.2        |...................=|
  ?   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17 |......=====.........|
  ?   com.jwebmp.thirdparty                2019-04..2019-08 0.68.0.1     |....==..............|
  ?   com.jwebmp                           2019-01..2019-04 0.66.0.1     |....=...............|
org.apache.commons.fileupload  [no clear owner; `com.jwebmp.jre11` is earliest and most recent]
  ? * com.jwebmp.jre11                     2018-12..2018-12 0.63.0.19    |....=...............|
  ?   com.guicedee.modules.services        2026-04..2026-05 2.0.2        |...................=|
  ?   org.wiremock                         2025-06..2026-04 4.0.0-beta.32 |.................===|
  ?   org.openidentityplatform.openam.agents 2025-11..2026-03 5.0.3        |..................==|
  ?   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17 |......=====.........|
  ?   com.jwebmp.jpms.commons              2019-04..2019-08 0.68.0.1     |....==..............|
    + 1 more: com.jwebmp
cache.annotations.ri.common  [no clear owner; `com.jwebmp.thirdparty.jcache` is earliest and most recent]
  ? * com.jwebmp.thirdparty.jcache         2019-05..2019-08 0.68.0.1     |.....=..............|
  ?   com.guicedee.modules.services        2026-04..2026-05 2.0.2        |...................=|
  ?   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17 |......=====.........|
net.sf.uadetector.core  [no clear owner; `com.jwebmp.jre11` is earliest and most recent]
  ? * com.jwebmp.jre11                     2018-11..2018-12 0.63.0.19    |....=...............|
  ?   com.guicedee.modules.services        2026-04..2026-05 2.0.2        |...................=|
  ?   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17 |......=====.........|
  ?   com.jwebmp.thirdparty                2019-04..2019-08 0.68.0.1     |....==..............|
  ?   com.jwebmp                           2019-01..2019-04 0.66.0.1     |....=...............|
java.json.bind  [no clear owner; `javax.json.bind` is earliest and most recent]
  ? * javax.json.bind                      2017-04..2017-06 1.0          |==..................|
  ?   org.open-metadata                    2023-08..2026-05 1.13.0-rc1   |.............=======|
  ?   org.jboss.pnc.build-agent            2024-06..2026-03 1.1.9        |...............====.|
  ?   be.valuya.cestzam                    2021-09..2023-01 2023.1.1     |.........====.......|
  ?   com.manywho.sdk                      2020-02..2020-05 2.0.1        |......==............|
  ?   jakarta.json.bind                    2019-01..2019-08 1.0.2        |....==..............|
    + 5 more: io.zeleo.application, org.keycloak, com.github.robozonky.distribution, com.github.robozonky, net.redpipe
jakarta.security.auth.message  [no clear owner; `jakarta.authentication` is earliest and most recent]
  ? * jakarta.authentication               2020-11..2024-05 3.1.0        |........========....|
  ?   org.apache.tomcat                    2020-11..2026-05 10.1.55      |........============|
jdk.internal.vm.compiler  [no clear owner; `org.graalvm.compiler` is earliest and most recent]
  ? * org.graalvm.compiler                 2018-10..2026-04 23.0.12      |...=================|
  ?   io.vertx                             2022-11..2026-05 4.5.27       |............========|
  ?   org.linuxforhealth.fhir              2022-08..2022-12 5.1.1        |...........==.......|
jcef  [no clear owner; `me.friwi` is earliest and most recent]
  ? * me.friwi                             2021-12..2026-05 jcef-d3de827+cef-146.0.10+g8219561+chromium-146.0.7680.179 |..........==========|
  ?   io.github.trethore                   2026-02..2026-04 jcef-65f9d7b+cef-146.0.10+g8219561+chromium-146.0.7680.179 |..................==|
jam.common  [no clear owner; `sk.annotation.library.jam` is earliest and most recent]
  ? * sk.annotation.library.jam            2022-01..2026-05 0.9.21       |..........==========|
  ?   sk.annotation.projects.signito       2022-12..2022-12 0.9.53       |............=.......|
java.security.auth.message  [no clear owner; `jakarta.security.auth.message` is earliest and most recent]
  ? * jakarta.security.auth.message        2018-12..2020-02 2.0.0-RC1    |....===.............|
  ?   org.apache.tomcat                    2020-11..2026-05 9.0.118      |........============|
  ?   org.jboss.spec.javax.security.auth.message 2019-08..2019-09 2.0.1.Final  |.....=..............|
java.servlet  [no clear owner; `jakarta.servlet` is earliest and most recent]
  ? * jakarta.servlet                      2019-08..2020-07 5.0.0-M2     |.....===............|
  ?   org.apache.tomcat                    2020-11..2026-05 9.0.118      |........============|
  ?   org.apache.felix                     2022-02..2022-10 2.1.0        |..........===.......|
  ?   com.guicedee.services                2020-05..2022-02 1.2.2.1-jre17 |.......====.........|
  ?   org.jboss.spec.javax.servlet         2019-08..2019-09 2.0.0.Final  |.....=..............|
java.servlet.jsp  [no clear owner; `org.apache.tomcat` is earliest and most recent]
  ? * org.apache.tomcat                    2020-11..2026-05 9.0.118      |........============|
  ?   com.heroku                           2024-05..2026-04 9.0.117.0    |...............=====|
java.el  [no clear owner; `org.apache.tomcat` is earliest and most recent]
  ? * org.apache.tomcat                    2020-11..2026-05 9.0.118      |........============|
  ?   com.heroku                           2024-10..2024-10 9.0.96.0     |................=...|
java.annotation  [no clear owner; `javax.annotation` is earliest and most recent]
  ? * javax.annotation                     2017-09..2018-02 1.3.2        |.==.................|
  ?   org.apache.tomcat                    2020-09..2026-05 9.0.118      |.......=============|
  ?   com.heroku                           2024-10..2026-04 9.0.117.0    |................====|
  ?   one.gfw                              2023-03..2023-03 1.3.5        |............=.......|
  ?   org.rationalityfrontline.workaround  2021-02..2021-02 1.3.2-3.0.2  |........=...........|
  ?   com.guicedee.services                2019-11..2020-11 1.1.0.1-jre15 |......===...........|
    + 3 more: no.ssb.jpms, org.jboss.spec.javax.annotation, jakarta.annotation
org.scala.lang.scala3.compiler  [no clear owner; `org.scala-lang` is earliest and most recent]
  ? * org.scala-lang                       2021-06..2026-04 3.3.8-RC1    |.........===========|
  ?   com.michaelpollmeier                 2022-10..2022-11 3.2.2-RC1-bin-20221101-d84007c-NIGHTLY+1-extensible-repl |............=.......|
org.apache.commons.dbcp2  [no clear owner; `org.apache.tomee` is earliest and most recent]
  ? * org.apache.tomee                     2023-12..2026-04 10.1.5       |..............======|
  ?   org.apache.meecrowave                2025-10..2025-10 2.0.0        |..................=.|
  ?   net.ontopia                          2025-04..2025-07 5.5.2        |.................=..|
  ?   org.apache.openjpa                   2024-09..2025-05 4.1.1        |...............===..|
json.path  [no clear owner; `com.jayway.jsonpath` is earliest and most recent]
  ? * com.jayway.jsonpath                  2024-01..2026-02 3.0.0        |..............=====.|
  ?   org.gov4j.thirdparty.com.jayway.jsonpath 2024-12..2026-04 3.0.0-gov4j-1 |................====|
  ?   com.github.sonus21                   2025-04..2025-04 2.10.0       |.................=..|
mslinks  [no clear owner; `com.github.vatbub` is earliest and most recent]
  ? * com.github.vatbub                    2020-09..2021-07 1.0.6.2      |.......===..........|
  ?   org.jabref                           2026-02..2026-04 1.2          |..................==|
com.oracle.truffle.regex  [no clear owner; `org.graalvm.regex` is earliest and most recent]
  ? * org.graalvm.regex                    2018-10..2026-04 25.0.3       |...=================|
  ?   org.noear                            2024-09..2025-07 1.9.6        |................==..|
  ?   com.syncloop.middleware              2025-01..2025-01 1.7.1        |................=...|
org.bukkit  [no clear owner; `com.uroria.curepur` is earliest and most recent]
  ? * com.uroria.curepur                   2024-07..2024-07 1.21-R0.1    |...............=....|
  ?   com.mineplex.studio.server           2024-10..2026-04 26.1.2-357   |................====|
  ?   com.uroria.latest                    2024-07..2024-07 1.21-R0.1-2d776710d6 |...............=....|
  ?   com.uroria                           2024-07..2024-07 1.21-R0.1    |...............=....|
graphql.java.tools  [no clear owner; `com.graphql-java-kickstart` is earliest and most recent]
  ? * com.graphql-java-kickstart           2023-08..2025-04 14.0.1       |.............=====..|
  ?   io.github.graphql-java-kickstart     2026-03..2026-04 14.0.2       |...................=|
com.jn.langx.java8  [no clear owner; `io.github.bes2008.solution.langx` is earliest and most recent]
  ? * io.github.bes2008.solution.langx     2024-01..2026-04 5.4.6.1      |..............======|
  ?   io.github.qhsword.langx              2025-11..2025-11 5.5.10       |..................=.|
com.jn.langx.security.gm.jca.bouncycastle  [no clear owner; `io.github.bes2008.solution.langx.security` is earliest and most recent]
  ? * io.github.bes2008.solution.langx.security 2024-01..2026-04 5.4.6.1      |..............======|
  ?   io.github.qhsword.langx.security     2025-11..2025-12 5.8.0        |..................=.|
org.java_websocket  [no clear owner; `org.java-websocket` is earliest and most recent]
  ? * org.java-websocket                   2023-07..2024-12 1.6.0        |.............====...|
  ?   io.github.cb-jarunmadhesh            2026-04..2026-04 1.0.0        |...................=|
  ?   io.github.ashwithpoojary98           2026-01..2026-01 1.0.1        |..................=.|
  ?   dev.lolyay                           2025-07..2025-10 5.8.0        |.................==.|
  ?   io.kestra.plugin                     2023-09..2025-08 0.24.0       |..............====..|
  ?   org.jetbrains.kotlinx                2025-07..2025-07 0.14.1-506   |.................=..|
    + 3 more: io.github.gubaojian, com.taosdata.jdbc, com.enixyu
tech.fortis.sandbox.api  [no clear owner; `io.github.zahran444` is earliest and most recent]
  ? * io.github.zahran444                  2026-04..2026-04 1.0.0        |...................=|
  ?   io.sdks                              2026-04..2026-04 1.0.5        |...................=|
com.mypayquicker.api  [no clear owner; `io.sdks` is earliest and most recent]
  ? * io.sdks                              2026-02..2026-02 1.0.3        |..................=.|
  ?   io.github.zahran444                  2026-04..2026-04 1.0.0        |...................=|
java.xml.ws  [no clear owner; `javax.xml.ws` is earliest and most recent]
  ? * javax.xml.ws                         2017-06..2018-09 2.3.1        |.===................|
  ?   org.apache.manifoldcf                2026-04..2026-04 2.30         |...................=|
  ?   mx.com.sw.services                   2020-07..2024-05 1.0.19.4     |.......=========....|
  ?   org.apache.servicemix.specs          2018-11..2020-03 2.3_3        |....===.............|
  ?   com.github.pinterest                 2018-11..2020-01 0.3.0-rc.2   |....===.............|
  ?   jakarta.xml.ws                       2018-12..2020-01 2.3.3        |....===.............|
net.sf.jsqlparser  [no clear owner; `com.github.jsqlparser` is earliest and most recent]
  ? * com.github.jsqlparser                2024-03..2025-05 5.3          |..............====..|
  ?   com.manticore-projects.jsqlformatter 2025-12..2026-04 5.3.218      |..................==|
  ?   se.alipsa                            2025-12..2025-12 1.2.0        |..................=.|
  ?   ai.starlake                          2024-09..2024-10 1.3.0        |...............==...|
com.drew.metadata  [no clear owner; `com.github.dalet-oss` is earliest and most recent]
  ? * com.github.dalet-oss                 2024-03..2024-03 0.0.6        |..............=.....|
  ?   com.drewnoakes                       2026-04..2026-04 2.20.0       |...................=|
commons  [no clear owner; `com.github.srujankujmar` is earliest and most recent]
  ? * com.github.srujankujmar              2020-12..2020-12 0.9.8.1      |........=...........|
  ?   io.github.rassafel                   2025-07..2026-04 0.0.4        |.................===|
  ?   io.hyscale                           2020-12..2021-08 1.0.0        |........==..........|
com.example.www  [no clear owner; `io.github.zahran444` is earliest and most recent]
  ? * io.github.zahran444                  2025-06..2026-03 3.0.7        |.................===|
  ?   io.sdks                              2026-03..2026-03 0.0.1        |...................=|
ij  [no clear owner; `net.imagej` is earliest and most recent]
  ? * net.imagej                           2019-03..2025-02 1.54p        |....=============...|
  ?   org.tango-controls.atk               2022-06..2026-03 9.4.20       |...........=========|
  ?   org.tango-controls                   2022-10..2025-10 7.46         |............=======.|
dawdler.boot.classloader  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.boot.classloader.core  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.boot.server  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.boot.web  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.boot.web.undertow  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.boot.web.undertow.jsp  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.cache.core  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.circuit.breaker  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.client  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.client.plug.config.center.consul  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.client.plug.mybatis  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.client.plug.remote.service  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.client.plug.schedule  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.client.plug.validator  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.client.plug.velocity  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.config.center.consul.core  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.core  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.dao.core  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.distributed.transaction.compensator  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.es.core  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.jedis.core  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.load.bean  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.server  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.server.plug.load  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.server.plug.remote.service  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.service.core  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.cache.caffeine  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.client.plug.discovery.center.consul  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.client.plug.discovery.center.zookeeper  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.client.plug.jedis  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.client.plug.load  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.client.plug.local.service  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.client.plug.session  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.client.plug.web  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.config.center.core  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.discovery.center.consul.core  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.discovery.center.core  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.discovery.center.server.plug  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.discovery.center.zookeeper.core  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.distributed.transaction.api  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.jakarta.fileupload  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.mybatis.core  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.pinpoint.plug  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.rabbitmq.core  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.remote.service.core  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.serialization  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.server.logback  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.server.plug.config.center.consul  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.server.plug.discovery.center.zookeeper  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.server.plug.es  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.server.plug.local.service  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.server.plug.rabbitmq  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.server.plug.schedule  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.util  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.cache.jedis  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.client.plug.dao  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.client.plug.db  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.client.plug.es  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.client.plug.rabbitmq  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.db.core  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.distributed.transaction.core  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.local.service.core  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.schedule.core  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.server.plug.dao  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.server.plug.db  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.server.plug.discovery.center.consul  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.server.plug.jedis  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
dawdler.server.plug.mybatis  [no clear owner; `io.github.dawdler-series` is earliest and most recent]
  ? * io.github.dawdler-series             2024-05..2024-08 1.0.8-jdk21-RELEASES |...............=....|
  ?   club.dawdler                         2024-09..2026-03 0.1.5-jdk25-RELEASES |...............=====|
uk.co.spudsoft.birt.emitters.excel  [no clear owner; `io.github.reporting-solutions` is earliest and most recent]
  ? * io.github.reporting-solutions        2019-05..2026-02 4.23.0       |.....==============.|
  ?   org.eclipse.birt                     2022-05..2026-03 4.23.0       |...........========.|
druid.spring.boot4.starter  [no clear owner; `com.shr25` is earliest and most recent]
  ? * com.shr25                            2026-02..2026-02 1.2.27       |..................=.|
  ?   com.alibaba                          2026-03..2026-03 1.2.28       |..................=.|
java.rmi  [no clear owner; `fr.inria.gforge.spoon` is earliest and most recent]
  ? * fr.inria.gforge.spoon                2020-06..2020-06 8.2.0-beta-13 |.......=............|
  ?   org.wso2.carbon.automation           2026-02..2026-03 4.5.3        |..................=.|
org.freedesktop.dbus  [no clear owner; `com.github.hypfvieh` is earliest and most recent]
  ? * com.github.hypfvieh                  2021-03..2025-12 5.2.0        |........===========.|
  ?   org.endlesssource.mediainterface     2026-02..2026-03 0.2.3        |..................=.|
tornadofx  [no clear owner; `it.unibo.alchemist` is earliest and most recent]
  ? * it.unibo.alchemist                   2020-11..2020-12 9.3.0-dev218+bb50ca6a3 |........=...........|
  ?   com.googlecode.blaisemath.tornado    2023-09..2026-03 2.2.2        |.............======.|
info.movito.themoviedbapi  [no clear owner; `com.github.holgerbrandl` is earliest and most recent]
  ? * com.github.holgerbrandl              2023-05..2023-05 1.15         |.............=......|
  ?   uk.co.conoregan                      2023-11..2026-03 2.6.0        |..............=====.|
core  [no clear owner; `pro.shuangxi.framework.openfx` is earliest and most recent]
  ? * pro.shuangxi.framework.openfx        2025-05..2025-05 1.0.0        |.................=..|
  ?   org.apereo.cas                       2025-07..2026-03 7.2.7.1      |.................==.|
dss_pki_factory_jaxb  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2023-11..2026-03 6.4          |..............=====.|
  ?   org.digidoc4j.dss                    2024-02..2025-11 6.2.d4j.1    |..............=====.|
jpms_dss_asic_common  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1    |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4          |............=======.|
jpms_dss_crl_parser_stream  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1    |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4          |............=======.|
jpms_dss_diagnostic_data  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1    |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4          |............=======.|
jpms_dss_document  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1    |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4          |............=======.|
jpms_dss_evidence_record_asn1  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2024-07..2026-03 6.4          |...............====.|
  ?   org.digidoc4j.dss                    2025-11..2025-11 6.2.d4j.1    |..................=.|
jpms_dss_jacoco_coverage  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2023-02..2026-03 6.4          |............=======.|
  ?   org.digidoc4j.dss                    2024-02..2025-11 6.2.d4j.1    |..............=====.|
jpms_dss_jades  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2021-10..2025-11 6.2.d4j.1    |..........=========.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4          |............=======.|
jpms_dss_jaxb_parsers  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1    |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4          |............=======.|
jpms_dss_model  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1    |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4          |............=======.|
jpms_dss_policy  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1    |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4          |............=======.|
jpms_dss_service  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1    |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4          |............=======.|
jpms_dss_specs_jws  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2021-10..2025-11 6.2.d4j.1    |..........=========.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4          |............=======.|
jpms_dss_specs_xades  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1    |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4          |............=======.|
jpms_dss_specs_xmldsig  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1    |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4          |............=======.|
jpms_dss_specs_xmlers  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2023-11..2026-03 6.4          |..............=====.|
  ?   org.digidoc4j.dss                    2024-02..2025-11 6.2.d4j.1    |..............=====.|
jpms_dss_test  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1    |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4          |............=======.|
jpms_dss_ws_certificate_validation_dto  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1    |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4          |............=======.|
jpms_dss_ws_validation_soap  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1    |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4          |............=======.|
jpms_dss_asic_xades  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1    |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4          |............=======.|
jpms_dss_common_converter  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1    |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4          |............=======.|
jpms_dss_cookbook  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1    |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4          |............=======.|
jpms_dss_crl_parser  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1    |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4          |............=======.|
jpms_dss_detailed_report  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1    |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4          |............=======.|
jpms_dss_pki_factory  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2023-11..2026-03 6.4          |..............=====.|
  ?   org.digidoc4j.dss                    2024-02..2025-11 6.2.d4j.1    |..............=====.|
jpms_dss_specs_validation_report  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1    |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4          |............=======.|
jpms_dss_utils_google_guava  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1    |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4          |............=======.|
jpms_dss_ws_certificate_validation_rest  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1    |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4          |............=======.|
jpms_dss_ws_server_signing_common  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1    |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4          |............=======.|
jpms_dss_ws_server_signing_soap_client  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1    |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4          |............=======.|
jpms_dss_ws_signature_dto  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1    |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4          |............=======.|
jpms_dss_ws_signature_remote  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1    |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4          |............=======.|
```

## Reassigned and widened ownership

Modules whose resolved owner differs from the implicit first-publisher owner once `owners.tsv` is applied. 🔀 reassigned (168): the first publisher was replaced by a different owner. ➕ widened (109): extra legal owners were allowed alongside the first publisher (e.g. a groupId migration or a co-maintained project). Modules where `owners.tsv` only confirms the first publisher are not listed. Submodules that share the same transition are collapsed into a single `prefix.*` row; the count in braces after the name is how many modules that row covers. The Rejected owner(s) column names the publishers excluded for the name (empty for a pure widening).

| Module | Implicit owner | Owner(s) | Rejected owner(s) |
|---|---|---|---|
| `com.google.auto.service` 🔀 | `com.github.sidneibjunior` | `com.google.auto.service` | `com.github.sidneibjunior, dev.ikm.jpms` |
| `com.install4j.runtime` ➕ | `com.iamsoft` | `com.iamsoft, com.install4j` | (none) |
| `com.sun.xml.bind` 🔀 | `org.glassfish.jaxb` | `com.sun.xml.bind` | `org.glassfish.jaxb, at.researchstudio.sat, br.com.swconsultoria, com.airbus-cyber-security.graylog, +31 more` |
| `io.avaje.junit` ➕ | `org.avaje` | `io.avaje, org.avaje` | (none) |
| `io.ebean.*` (3) ➕ | `org.avaje` | `io.ebean, org.avaje` | (none) |
| `io.grpc` 🔀 | `io.helidon.grpc` | `io.grpc` | `io.helidon.grpc, com.clickhouse, io.github.sunny-chung` |
| `io.whitfin.siphash` 🔀 | `com.io7m.repackage.io.whitfin` | `io.whitfin` | `com.io7m.repackage.io.whitfin` |
| `jakarta.activation` 🔀 | `com.sun.activation` | `jakarta.activation` | `com.sun.activation, ai.starlake, ch.exense.step, com.adobe.cq, +40 more` |
| `jakarta.cdi.*` (3) 🔀 | `jakarta.enterprise` | `jakarta.cdi` | `jakarta.enterprise, com.abavilla` |
| `jakarta.concurrency` 🔀 | `jakarta.enterprise.concurrent` | `jakarta.concurrency` | `jakarta.enterprise.concurrent` |
| `jakarta.ejb` 🔀 | `com.guicedee.services` | `jakarta.ejb` | `com.guicedee.services, com.manorrock.flounder` |
| `jakarta.faces` ➕ | `com.guicedee.services` | `com.guicedee.services, jakarta.faces` | (none) |
| `jakarta.mail` 🔀 | `com.sun.mail` | `jakarta.mail` | `com.sun.mail, com.guicedee.services, com.krux, com.randomnoun.db, +5 more` |
| `jakarta.servlet.jsp` 🔀 | `org.apache.tomcat` | `jakarta.servlet.jsp` | `org.apache.tomcat, com.guicedee.services, com.heroku` |
| `kora.*` (80) ➕ | `ru.tinkoff.kora` | `io.koraframework, ru.tinkoff.kora` | (none) |
| `kora.*` (8) ➕ | `ru.tinkoff.kora.experimental` | `io.koraframework.experimental, ru.tinkoff.kora.experimental` | (none) |
| `net.evonit.thumbnailator2` 🔀 | `io.github.evonit` | `net.evonit` | `io.github.evonit` |
| `okhttp3.*` (8) 🔀 | `com.github.ljun20160606` | `com.squareup.okhttp3` | `com.github.ljun20160606, com.datadoghq.okhttp3, com.huanli233.okhttp3-compat, com.ibm.cloud, +5 more` |
| `org.apache.xmlbeans` 🔀 | `com.guicedee.services` | `org.apache.xmlbeans` | `com.guicedee.services, com.github.rahulsom, com.sonsure, io.github.cdnk, +1 more` |
| `org.commonmark.*` (3) 🔀 | `com.atlassian.commonmark` | `org.commonmark` | `com.atlassian.commonmark, com.adobe.aem, com.qainsights, io.github.dfengwei, +4 more` |
| `org.commonmark.*` (7) ➕ | `com.atlassian.commonmark` | `com.atlassian.commonmark, org.commonmark` | `org.aya-prover` |
| `org.eclipse.birt.*` (137) 🔀 | `io.github.reporting-solutions` | `org.eclipse.birt` | `io.github.reporting-solutions` |
| `org.eclipse.emf.*` (3) ➕ | `com.innoventsolutions.birt.runtime` | `com.innoventsolutions.birt.runtime, org.eclipse.emf` | `ch.reportingsoft.birt` |
| `org.eclipse.emf.ecore.change` 🔀 | `com.innoventsolutions.birt.runtime` | `org.eclipse.emf` | `com.innoventsolutions.birt.runtime, ch.reportingsoft.birt` |
| `org.pcollections` 🔀 | `net.pincette` | `org.pcollections` | `net.pincette` |
| `org.primefaces.extensions` ➕ | `com.guicedee.services` | `com.guicedee.services, org.primefaces.extensions` | (none) |
| `play.ws.standalone.*` (4) ➕ | `com.typesafe.play` | `com.typesafe.play, org.playframework` | (none) |
| `spring.security.*` (2) 🔀 | `io.spring.gradle` | `org.springframework` | `io.spring.gradle, io.spring.security.gradle` |
| `spring.security.project.plugin` 🔀 | `io.spring.security.gradle` | `org.springframework` | `io.spring.security.gradle, io.spring.gradle` |
| `zipkin2.reporter.kafka` 🔀 | `org.apache.zipkin.reporter2` | `io.zipkin.reporter2` | `org.apache.zipkin.reporter2` |

