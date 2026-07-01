# Module ownership drifters

Generated 2026-07-01. A module *drifts* when more than one groupId publishes the name and its `owners.tsv` does not yet name every publisher (no `owners.tsv`, or one that leaves some publishing groupId neither `allowed` nor `rejected`). Resolving a drift means deciding each groupId via `SetOwners` (which writes `allowed`/`rejected`); a fully-named module drops off this list.

| Category | Unresolved | Resolved via owners.tsv |
|---|---:|---:|
| explicit-rules | 33 | 669 |
| republisher | 1 | 15 |
| migration | 3 | 786 |
| fork | 98 | 353 |
| shaded | 12 | 1257 |
| tld-dropped | 1 | 70 |
| two-segments | 67 | 0 |
| unclassified | 264 | 1 |
| **total** | **479** | **3151** |

The table covers all **3630** multi-owner modules (of **36804** modules scanned).

Timeline axis spans 2017-01 .. 2026-07 (today). Per group: decision `A`=allowed `R`=rejected `?`=undecided, `*`=current owner, then the publication range, latest version, and a `=` activity bar across the axis.

## explicit-rules (33)

Hand-curated overrides: a module matching an explicit rule is assigned to a fixed owner groupId regardless of the heuristic.

- The module name equals, or falls under, a hand-curated prefix in the explicit-owner map.
- Allow every publisher whose groupId falls under the mapped owner prefix; reject all other publishers.

| count | current owner | new owner(s) |
|---:|---|---|
| 1 | `org.jetbrains.kotlinx` | `org.jetbrains.kotlinx, org.jetbrains.dokka, org.jetbrains.intellij.deps.kotlinx` |

```
com.google.gson  [explicit rule: owned by `com.google.code.gson`; 533 other group(s) rejected]
  A * com.google.code.gson                 2019-10..2026-04 2.14.0               |.....===============|
  R   com.aliyun                           2021-09..2026-06 1.16.1               |.........===========|
  R   com.google.cloud.bigdataoss          2023-10..2026-06 3.1.18               |..............======|
  ?   io.github.rahul1818                  2026-06..2026-06 1.0.4                |...................=|
  ?   com.conaxgames                       2026-06..2026-06 1.5.0                |...................=|
  R   org.openjproxy                       2025-09..2026-06 0.5.0-beta           |.................===|
    + 528 more: org.sonarsource.sonarlint.ls, org.sonarsource.sonarlint.core, org.operaton.bpm, org.sonarsource.java, io.quarkus, com.linkedin.iceberg, tools.vitruv, io.jooby, network.lightsail, com.uber.nullaway, org.apache.flink, win.dailypickle.report, (+516 more)
kotlin.stdlib  [explicit rule: owned by `org.jetbrains.kotlin`; 222 other group(s) rejected]
  A * org.jetbrains.kotlin                 2019-01..2023-08 1.9.10               |....==========......|
  R   com.easemob.im                       2024-04..2026-06 1.0.20               |...............=====|
  R   com.aliyun.odps                      2026-03..2026-06 0.57.3-public        |..................==|
  R   fi.evident.apina                     2023-08..2026-06 0.29.0               |.............=======|
  R   love.forte.plugin.suspend-transform  2024-09..2026-06 2.4.0-0.14.0         |...............=====|
  R   de.darkatra.injector                 2025-03..2026-06 1.0.28               |................====|
    + 217 more: io.last9, net.master-studios, org.jetbrains.lets-plot, org.jetbrains.kotlinx, com.volcengine, com.alibaba.ververica, org.octopusden.octopus.jira, com.airbnb.viaduct, com.tidbcloud, org.virtuslab, dev.robocode.tankroyale, dev.hsbrysk, (+205 more)
kotlin.stdlib.jdk8  [explicit rule: owned by `org.jetbrains.kotlin`; 242 other group(s) rejected]
  A * org.jetbrains.kotlin                 2019-01..2023-08 1.9.10               |....==========......|
  R   com.google.genai                     2026-04..2026-06 1.60.0               |...................=|
  R   com.squareup                         2022-11..2026-06 0.19.0               |............========|
  R   com.aliyun.odps                      2026-03..2026-06 3.10.8               |..................==|
  ?   org.apache.flink                     2026-06..2026-06 2.3.0                |...................=|
  R   com.commercetools.rmf                2021-10..2026-06 1.0.0-20260608125910 |..........==========|
    + 237 more: com.aliyun, org.apache.hudi, com.spectralogic.ds3, me.bechberger, com.newrelic.agent.android, org.octopusden.octopus.jira, com.sportradar.unifiedodds.sdk, org.apache.dolphinscheduler, com.airbnb.viaduct, com.airbnb.viaduct.javaapi, net.corda, ai.realitydefender, (+225 more)
com.google.common  [explicit rule: owned by `com.google.guava`; 71 other group(s) rejected]
  A * com.google.guava                     2017-07..2026-04 33.6.0-jre           |.===================|
  R   io.acryl                             2026-04..2026-06 1.6.0.6rc2           |...................=|
  R   io.orkes.conductor                   2026-05..2026-06 5.1.0                |...................=|
  R   org.conductoross                     2026-05..2026-06 5.1.0                |...................=|
  R   io.digiexpress                       2025-10..2026-06 6.0.27               |..................==|
  R   org.talend.sdk.component.sample.feature 2025-08..2026-06 1.93.0               |.................===|
    + 66 more: lv.ailab.morphology, org.foundationdb, org.opendaylight.aaa, com.hubspot, io.javelit, com.alibaba.ververica, org.apache.hbase, io.github.davidwhitlock.joy.original, org.jboss.shrinkwrap.resolver, org.apache.jackrabbit, net.sourceforge.plantuml, de.m3y.prometheus.exporter.fsimage, (+54 more)
kotlin.stdlib.jdk7  [explicit rule: owned by `org.jetbrains.kotlin`; 21 other group(s) rejected]
  A * org.jetbrains.kotlin                 2019-01..2023-08 1.9.10               |....==========......|
  R   com.kroegerama.openapi-kgen          2023-12..2026-06 0.18.2               |..............======|
  ?   org.octopusden.octopus.jira          2026-06..2026-06 2.0.3                |...................=|
  R   io.pyroscope                         2025-04..2026-06 2.6.0                |................====|
  ?   me.bechberger                        2026-06..2026-06 0.0.7                |...................=|
  R   io.github.team-sneakymouse           2026-05..2026-05 4.0-Beta-13          |...................=|
    + 16 more: com.seanshubin.code.structure, me.xcue, org.partiql, io.github.wadoon.key, org.btmonier, com.slothiesmooth, com.slothiesmooth.links-detektor, hu.bme.mit.theta, com.github.shynixn.mccoroutine, dev.nelmin.spigot, com.facebook, com.cjcrafter, (+4 more)
kotlinx.serialization.core  [explicit rule: owned by `org.jetbrains`; 28 other group(s) rejected]
  A * org.jetbrains.kotlinx                2021-09..2026-04 1.11.0               |.........===========|
  ?   lol.simeon                           2026-06..2026-06 1.1.2                |...................=|
  R   love.forte.plugin.suspend-transform  2025-04..2026-06 2.4.0-0.14.0         |.................===|
  R   dev.sebastiano.spectre               2026-05..2026-06 0.2.1                |...................=|
  R   dev.robocode.tankroyale              2026-01..2026-05 1.0.2                |..................==|
  R   io.github.wangbax                    2026-04..2026-04 5.5.1-okio-fork-2    |...................=|
    + 23 more: com.squareup.wire, org.ldemetrios, io.github.lumamontes, dev.zacsweers.metro, io.typst, io.availe, dev.oglass, io.github.oewntk, io.github.lexa-diky, com.toasttab.expediter, io.johnsonlee.exec, io.specmatic, (+11 more)
org.objectweb.asm  [explicit rule: owned by `org.ow2.asm`; 163 other group(s) rejected]
  A * org.ow2.asm                          2017-07..2026-05 9.10.1               |.===================|
  R   com.my-oli                           2025-05..2026-06 1.1.3                |.................===|
  R   org.teavm                            2023-03..2026-06 0.15.0               |............========|
  R   io.github.mitsumi-solutions-develop  2025-06..2026-06 1.0.0                |.................===|
  ?   io.spicelabs                         2026-06..2026-06 0.16.0               |...................=|
  R   org.noear                            2019-03..2026-06 1.12.0               |....================|
    + 158 more: net.corda, org.virtuslab.scala-cli, org.tiatesting, com.microsoft.azure.kusto, com.pinterest.psc, com.datadoghq, org.apache.iotdb, com.github.jnr, io.github.nieuwmijnleven.jadex, be.ugent.idlab.knows, org.fitnesse, io.github.vdaburon, (+146 more)
com.sun.tools.xjc  [explicit rule: owned by `org.glassfish.jaxb`; 4 other group(s) rejected]
  A * org.glassfish.jaxb                   2018-07..2019-01 2.3.2                |...==...............|
  ?   gov.nasa.pds                         2026-06..2026-06 3.2.1                |...................=|
  R   com.sun.xml.bind                     2018-07..2026-05 4.0.9                |...=================|
  R   cn.lzgabel.jaxb.xml.bind             2022-03..2022-03 4.0.0                |..........=.........|
  R   com.github.shynixn                   2019-02..2019-02 1.0                  |....=...............|
spring.context.indexer  [explicit rule: owned by `org.springframework`; 2 other group(s) rejected]
  A * org.springframework                  2017-09..2026-06 7.0.8                |.===================|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |...................=|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
spring.context.support  [explicit rule: owned by `org.springframework`; 4 other group(s) rejected]
  A * org.springframework                  2017-09..2026-06 7.0.8                |.===================|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |...................=|
  R   org.gov4j.thirdparty.org.springframework 2024-12..2025-05 5.3.39-gov4j-2       |................==..|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
  R   com.succsoft                         2024-12..2024-12 5.3.42               |................=...|
spring.aop  [explicit rule: owned by `org.springframework`; 3 other group(s) rejected]
  A * org.springframework                  2017-09..2026-06 7.0.8                |.===================|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |...................=|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
  R   com.succsoft                         2024-12..2024-12 5.3.42               |................=...|
spring.aspects  [explicit rule: owned by `org.springframework`; 2 other group(s) rejected]
  A * org.springframework                  2017-09..2026-06 7.0.8                |.===================|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |...................=|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
spring.beans  [explicit rule: owned by `org.springframework`; 5 other group(s) rejected]
  A * org.springframework                  2017-09..2026-06 7.0.8                |.===================|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |...................=|
  R   org.gov4j.thirdparty.org.springframework 2024-12..2025-05 5.3.39-gov4j-2       |................==..|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
  R   com.succsoft                         2024-12..2024-12 5.3.42               |................=...|
  R   com.liferay                          2023-01..2024-08 5.3.39.LIFERAY-PATCHED-1 |............====....|
spring.context  [explicit rule: owned by `org.springframework`; 5 other group(s) rejected]
  A * org.springframework                  2017-09..2026-06 7.0.8                |.===================|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |...................=|
  R   org.gov4j.thirdparty.org.springframework 2024-12..2025-05 5.3.39-gov4j-2       |................==..|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
  R   com.succsoft                         2024-12..2024-12 5.3.42               |................=...|
  R   com.liferay                          2024-12..2024-12 5.3.39.LIFERAY-PATCHED-1 |................=...|
spring.core  [explicit rule: owned by `org.springframework`; 4 other group(s) rejected]
  A * org.springframework                  2017-09..2026-06 7.0.8                |.===================|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |...................=|
  R   org.gov4j.thirdparty.org.springframework 2024-12..2025-05 5.3.39-gov4j-2       |................==..|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
  R   com.succsoft                         2024-12..2024-12 5.3.42               |................=...|
spring.expression  [explicit rule: owned by `org.springframework`; 4 other group(s) rejected]
  A * org.springframework                  2017-09..2026-06 7.0.8                |.===================|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |...................=|
  R   org.gov4j.thirdparty.org.springframework 2024-12..2025-05 5.3.39-gov4j-2       |................==..|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
  R   com.succsoft                         2024-12..2024-12 5.3.42               |................=...|
spring.instrument  [explicit rule: owned by `org.springframework`; 2 other group(s) rejected]
  A * org.springframework                  2017-09..2026-06 7.0.8                |.===================|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |...................=|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
spring.jdbc  [explicit rule: owned by `org.springframework`; 3 other group(s) rejected]
  A * org.springframework                  2017-09..2026-06 7.0.8                |.===================|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |...................=|
  R   org.gov4j.thirdparty.org.springframework 2024-12..2025-05 5.3.39-gov4j-2       |................==..|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
spring.jms  [explicit rule: owned by `org.springframework`; 3 other group(s) rejected]
  A * org.springframework                  2017-09..2026-06 7.0.8                |.===================|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |...................=|
  R   org.gov4j.thirdparty.org.springframework 2024-12..2025-05 5.3.39-gov4j-2       |................==..|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
spring.messaging  [explicit rule: owned by `org.springframework`; 2 other group(s) rejected]
  A * org.springframework                  2017-09..2026-06 7.0.8                |.===================|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |...................=|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
spring.orm  [explicit rule: owned by `org.springframework`; 3 other group(s) rejected]
  A * org.springframework                  2017-09..2026-06 7.0.8                |.===================|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |...................=|
  R   com.liferay                          2022-03..2025-05 5.3.39.LIFERAY-PATCHED-1.JAKARTA-LIFERAY-PATCHED-1 |..........========..|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
spring.oxm  [explicit rule: owned by `org.springframework`; 3 other group(s) rejected]
  A * org.springframework                  2017-09..2026-06 7.0.8                |.===================|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |...................=|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
  R   com.succsoft                         2024-12..2024-12 5.3.42               |................=...|
spring.r2dbc  [explicit rule: owned by `org.springframework`; 2 other group(s) rejected]
  A * org.springframework                  2020-10..2026-06 7.0.8                |........============|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |...................=|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
spring.test  [explicit rule: owned by `org.springframework`; 5 other group(s) rejected]
  A * org.springframework                  2017-09..2026-06 7.0.8                |.===================|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |...................=|
  R   org.gov4j.thirdparty.org.springframework 2024-12..2025-05 5.3.39-gov4j-2       |................==..|
  R   com.liferay                          2025-05..2025-05 5.3.39.JAKARTA-LIFERAY-PATCHED-1 |.................=..|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
  R   com.succsoft                         2024-12..2024-12 5.3.42               |................=...|
spring.tx  [explicit rule: owned by `org.springframework`; 5 other group(s) rejected]
  A * org.springframework                  2017-09..2026-06 7.0.8                |.===================|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |...................=|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
  R   com.succsoft                         2024-12..2024-12 5.3.42               |................=...|
  R   com.liferay                          2024-01..2024-08 5.3.39.LIFERAY-PATCHED-1 |..............==....|
  R   com.labun                            2020-01..2020-01 5.2.2.RELEASE.patched |......=.............|
spring.web  [explicit rule: owned by `org.springframework`; 7 other group(s) rejected]
  A * org.springframework                  2017-09..2026-06 7.0.8                |.===================|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |...................=|
  R   org.gov4j.thirdparty.org.springframework 2024-12..2025-05 5.3.39-gov4j-2       |................==..|
  R   com.liferay                          2025-05..2025-05 5.3.39.JAKARTA-LIFERAY-PATCHED-1 |.................=..|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
  R   com.succsoft                         2024-12..2024-12 5.3.42               |................=...|
    + 2 more: io.github.redteamobile, io.github.tfedyanin.springframework
spring.webflux  [explicit rule: owned by `org.springframework`; 3 other group(s) rejected]
  A * org.springframework                  2017-09..2026-06 7.0.8                |.===================|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |...................=|
  R   org.gov4j.thirdparty.org.springframework 2024-12..2025-05 5.3.39-gov4j-2       |................==..|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
spring.webmvc  [explicit rule: owned by `org.springframework`; 4 other group(s) rejected]
  A * org.springframework                  2017-09..2026-06 7.0.8                |.===================|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |...................=|
  R   org.gov4j.thirdparty.org.springframework 2024-12..2025-05 5.3.39-gov4j-2       |................==..|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
  R   com.succsoft                         2024-12..2024-12 5.3.42               |................=...|
spring.websocket  [explicit rule: owned by `org.springframework`; 3 other group(s) rejected]
  A * org.springframework                  2017-09..2026-06 7.0.8                |.===================|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |...................=|
  R   org.gov4j.thirdparty.org.springframework 2024-12..2025-05 5.3.39-gov4j-2       |................==..|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
spring.jcl  [explicit rule: owned by `org.springframework`; 2 other group(s) rejected]
  A * org.springframework                  2017-09..2026-06 6.2.19               |.===================|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |...................=|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
scala.library  [explicit rule: owned by `org.scala-lang`; 2 other group(s) rejected]
  A * org.scala-lang                       2018-03..2026-06 3.9.0-RC1            |..==================|
  ?   ch.epfl.lara                         2026-06..2026-06 3.10.0-RC1-bin-20260608-cf86bba-NIGHTLY |...................=|
  R   com.github.xuwei-k                   2021-01..2021-01 2.13.3-bin-1ca7d14   |........=...........|
kotlin.reflect  [explicit rule: owned by `org.jetbrains.kotlin`; 64 other group(s) rejected]
  A * org.jetbrains.kotlin                 2019-01..2023-08 1.9.10               |....==========......|
  ?   io.github.rodrigotimoteo             2026-06..2026-06 0.1.0                |...................=|
  R   com.airbnb.viaduct                   2026-01..2026-04 0.29.0               |..................==|
  R   org.apache.pinot                     2025-09..2026-04 1.5.0                |.................===|
  R   io.github.abdullahkhan118            2026-03..2026-03 1.0.4                |..................=.|
  R   io.github.tobi-laa                   2026-03..2026-03 1.0.0                |..................=.|
    + 59 more: io.github.kshulzh.kefir, io.github.xilinjia.krdb, io.github.snow1026, com.browserstack, com.simprints.realm.kotlin, org.pkl-lang, com.statsig, com.infomaniak.realm.kotlin, com.solapi, io.github.honkling.commando, io.github.tabilzad.inspektor, hu.bme.mit.theta, (+47 more)
kotlinx.coroutines.core  [explicit rule: owned by `org.jetbrains`; 21 other group(s) rejected]
  A * org.jetbrains.kotlinx                2023-03..2026-05 1.11.0               |............========|
  ?   org.openprojectx.java.dns            2026-06..2026-06 0.1.1                |...................=|
  R   com.airbnb.viaduct                   2026-05..2026-05 1.1.0                |...................=|
  R   ca.acendas                           2025-11..2026-05 1.9.1                |..................==|
  R   com.krillforge                       2026-04..2026-04 0.0.2                |...................=|
  R   org.openprojectx.hadoop.win          2026-04..2026-04 0.1.4-3.1.1.7.1.9.14-2 |...................=|
    + 18 more: org.jetbrains.dokka, com.eygraber, org.jetbrains.intellij.deps.kotlinx, io.github.danbeldev, io.johnsonlee.kx, io.github.zimoyin, io.johnsonlee.exec, io.github.saumya-bhatt, io.realm.kotlin, com.rickbusarow.doks, io.sirix, com.squareup.wire, (+6 more)
```

## republisher (1)

Earliest owner is foreign to the module name while a natural-namespace owner is also present (shaded / repackaged jars).

- The earliest (current) owner's groupId is foreign to the module name: the name does not fall under it.
- A natural-namespace owner - a publisher whose groupId the module name does fall under - is also present.
- The foreign earliest owner is still globally active (a dormant one would be a relocation, see migration).
- Allow the natural owner; reject the foreign republisher.

| count | current owner | new owner(s) |
|---:|---|---|
| 1 | `org.glassfish.jaxb` | `com.sun.xml.bind` |

```
com.sun.xml.bind  [republished by `org.glassfish.jaxb` (still active); belongs to `com.sun.xml.bind`]
  R * org.glassfish.jaxb                   2018-07..2019-01 2.3.2                |...==...............|
  ?   com.checkmarx                        2026-06..2026-06 2026.2.32            |...................=|
  A   com.sun.xml.bind                     2018-07..2026-05 4.0.9                |...=================|
  R   com.exasol                           2024-10..2025-10 5.4.3                |................===.|
  R   int.esa.ccsds.mo                     2025-08..2025-08 12.3                 |.................=..|
  R   gov.nasa.pds                         2021-10..2025-04 2.11.0               |.........=========..|
    + 31 more: com.intuit.quickbooks-online, com.google.tsunami, com.helger.schematron, io.github.azagniotov, com.liferay, org.opengis.cite, org.duracloud, edu.iris.dmc, br.com.swconsultoria, one.gfw, org.xtce, com.github.cafapi, (+19 more)
```

## migration (3)

The publishing groupId handed off over time (a rename or a relocation), so both coordinates are kept.

- Rename: a more-recent successor is the same project as the owner (a shared groupId prefix, or two shared leading segments).
- Relocation: the owner stopped at or before a credible successor took over (or the owner went globally dormant), and that successor itself owns the module namespace.
- Allow both old and new so history stays resolvable and `latest` is current.

| count | current owner | new owner(s) |
|---:|---|---|
| 1 | `com.ibm.zertjsse` | `com.ibm.zertjsse, com.ibm.semeru-zjavasecurity` |
| 1 | `io.github.pdvrieze.xmlutil` | `io.github.pdvrieze.xmlutil, io.github.pdvrieze` |
| 1 | `org.eclipse.tycho` | `org.eclipse.tycho, org.eclipse.platform` |

```
io.github.pdvrieze.testutil  [renamed `io.github.pdvrieze.xmlutil` -> `io.github.pdvrieze` (latest 1.0.0-rc3)]
  ? * io.github.pdvrieze.xmlutil           2025-07..2026-01 1.0.0-rc2            |.................==.|
  ?   io.github.pdvrieze                   2026-06..2026-06 1.0.0-rc3            |...................=|
org.eclipse.osgi  [renamed `org.eclipse.tycho` -> `org.eclipse.platform` (latest 3.24.200)]
  ? * org.eclipse.tycho                    2018-05..2018-05 3.13.0.v20180226-1711 |...=................|
  ?   org.eclipse.platform                 2018-06..2026-06 3.24.200             |...=================|
  ?   io.joynr.tools.generator             2025-01..2026-06 1.26.6               |................====|
  ?   org.alfasoftware                     2021-05..2026-04 2.7.0                |.........===========|
  ?   net.kieker-monitoring                2026-04..2026-04 2.0.3                |...................=|
  ?   org.bonitasoft.bpm                   2023-10..2026-04 9.0.9                |..............======|
    + 22 more: de.funfried.libraries, io.github.alien-tools, org.tango-controls, org.tango-controls.pogo, ch.reportingsoft.birt, com.liferay, net.revelc.code.formatter, org.kie.j2cl.tools.external, io.github.dogla, com.vertispan.j2cl.external, fr.inria.gforge.spirals, org.geneweaver, (+10 more)
zertjsse  [renamed `com.ibm.zertjsse` -> `com.ibm.semeru-zjavasecurity` (latest 11.0.31.0)]
  ? * com.ibm.zertjsse                     2026-05..2026-05 11.0.31.0            |...................=|
  ?   com.ibm.semeru-zjavasecurity         2026-05..2026-05 11.0.31.0            |...................=|
```

## fork (98)

A cross-org coordinate publishes the same name while the original owner is still active.

- A more-recent cross-org coordinate (a successor) publishes the same name while the original is still active.
- The earliest publisher is itself a credible owner: it owns the module namespace, or is the closest groupId to it.
- Keep the original owner; reject the fork.

```
info.picocli  [fork: keep `info.picocli`, `ai.tegmentum.webassembly4j` still publishes the name]
  A * info.picocli                         2017-10..2025-04 4.7.7                |.=================..|
  R   ai.tegmentum.webassembly4j           2026-03..2026-06 1.4.0                |..................==|
  ?   io.github.cyber655                   2026-06..2026-06 1.0.0                |...................=|
  ?   io.github.a-collet                   2026-06..2026-06 1.1.0                |...................=|
  R   org.primefaces                       2021-09..2026-06 14.0.17-LTS          |.........===========|
  R   com.github.nbbrd.sasquatch           2024-09..2026-06 1.1.2                |...............=====|
    + 148 more: io.github.horadomu, io.spicelabs, com.instaclustr, org.keycloak, org.substrate4j, com.muquit.libgpw, org.rundeck.rd, org.apache.tika, dev.kgidwani, site.asm0dey, de.splatgames.aether.datafixers, com.eventoframework, (+136 more)
io.netty.internal.tcnative  [fork: keep `io.netty`, `com.google.api-ads` still publishes the name]
  A * io.netty                             2021-10..2026-06 2.0.79.Final         |.........===========|
  R   com.google.api-ads                   2025-08..2026-06 43.2.0               |.................===|
  R   org.finos.legend.engine              2026-01..2026-06 4.129.15             |..................==|
  R   app.cash.backfila                    2025-04..2026-06 2026.06.19.161401-6c57b66 |.................===|
  R   com.spotify.confidence               2026-01..2026-06 0.15.2               |..................==|
  R   org.apache.ozone                     2025-12..2026-06 2.1.1                |..................==|
    + 72 more: com.tsurugidb.iceaxe, com.tsurugidb.tsubakuro, org.apache.flink, com.datastax.oss, com.microsoft.azure.kusto, com.opendatadsl, io.grpc, com.liquibase.ext, com.instaclustr, com.azure.cosmos.spark, com.azure.cosmos.kafka, com.google.api, (+60 more)
com.fasterxml.jackson.annotation  [fork: keep `com.fasterxml.jackson.core`, `com.chartiq.finsemble` still publishes the name]
  A * com.fasterxml.jackson.core           2017-09..2026-05 2.22                 |.===================|
  R   com.chartiq.finsemble                2025-06..2026-06 10.2.4-BETA-2        |.................===|
  R   io.debezium                          2022-04..2026-06 3.6.0.CR1            |..........==========|
  R   io.dekorate                          2025-09..2026-06 4.1.8                |.................===|
  R   com.yahoo.vespa                      2022-06..2026-06 8.710.53             |...........=========|
  R   io.github.techelliiptica             2026-02..2026-06 3.0.4                |..................==|
    + 463 more: io.valkyrja, fr.inria.gforge.spoon, com.adobe.cq, io.orqueio.bpm, org.melaya, com.scylladb, com.heroku, org.mock-server, ru.loolzaaa.tgbot4j, io.github.spiceforgeio, com.databricks.labs, org.wso2.am.analytics.publisher, (+451 more)
com.fasterxml.jackson.core  [fork: keep `com.fasterxml.jackson.core`, `com.algolia` still publishes the name]
  A * com.fasterxml.jackson.core           2017-09..2026-05 2.21.4               |.===================|
  R   com.algolia                          2023-08..2026-06 4.41.1               |.............=======|
  R   org.kill-bill.billing                2022-10..2026-06 1.4.1                |...........=========|
  R   io.orqueio.spin                      2025-10..2026-06 1.0.10               |..................==|
  R   org.operaton.spin                    2024-10..2026-06 1.1.4                |................====|
  R   org.finos.fluxnova.spin              2025-11..2026-06 2.0.3                |..................==|
    + 402 more: io.openlineage, org.talend.sdk.component, io.delta, org.openidentityplatform.openam, com.facebook.presto, com.alibaba.hologres, com.vibeiq.contrail-java-sdk, com.gitee.feizns, com.linkedin.iceberg, org.apache.flink, ai.particledb, com.grafana, (+390 more)
org.apache.commons.lang3  [fork: keep `org.apache.commons`, `de.grobmeier.json` still publishes the name]
  A * org.apache.commons                   2017-06..2021-02 3.12.0               |.========...........|
  ?   de.grobmeier.json                    2026-06..2026-06 0.1.8                |...................=|
  R   ai.platon.gora                       2026-06..2026-06 1.0.1-slim           |...................=|
  R   io.openlineage                       2024-08..2026-06 1.50.0               |...............=====|
  R   org.sonarsource.java                 2024-11..2026-06 8.33.0.44481         |................====|
  R   io.streamnative                      2025-05..2026-06 4.2.1.4              |.................===|
    + 102 more: io.github.luo-zhan, com.ubs-hainer, com.equinor.neqsim, com.datastax.oss, io.swagger.parser.v3, org.apache.tomee, com.ascentstream.pulsar, com.liquibase.ext, org.apache.pulsar, io.github.roger3lee, no.entur, io.github.davidwhitlock.joy, (+90 more)
com.fasterxml.jackson.dataformat.yaml  [fork: keep `com.fasterxml.jackson.dataformat`, `io.fabrikt` still publishes the name]
  A * com.fasterxml.jackson.dataformat     2017-10..2026-06 2.22.0               |.===================|
  R   io.fabrikt                           2026-03..2026-06 27.4.1               |..................==|
  R   org.apache.flink                     2022-04..2026-06 0.3.0                |...........=========|
  R   dev.skyramp                          2025-02..2026-06 1.3.27               |................====|
  R   org.apiaddicts.apitools.dosonarapi   2025-01..2026-06 1.4.1                |................====|
  R   io.telicent.jena                     2025-06..2026-06 3.0.4                |.................===|
    + 60 more: io.github.gw-kit, org.apache.dolphinscheduler, com.helpchoice, com.sagframe, net.corda, org.testcontainers, com.cjbooms, io.github.pavan2504, io.github.rohitect, org.apache.plc4x, org.wildfly.prospero, com.free-now.sauron.plugins, (+48 more)
org.tukaani.xz  [fork: keep `org.tukaani`, `io.github.peterdowdy` still publishes the name]
  A * org.tukaani                          2018-01..2026-03 1.12                 |..=================.|
  ?   io.github.peterdowdy                 2026-06..2026-06 0.0.0-main-74fc6cf   |...................=|
  R   org.sonarsource.javascript           2023-09..2026-06 13.0.0.42526         |.............=======|
  ?   com.timecho.timechodb                2026-06..2026-06 2.0.9.4              |...................=|
  R   io.anserini                          2022-01..2026-06 2.2.0                |..........==========|
  R   org.apache.syncope.fit               2025-11..2026-05 4.0.6                |..................==|
    + 23 more: de.m3y.parquet, com.timecho.iotdb, com.sonatype.clm, net.neoforged.installertools, org.apache.iotdb, org.incenp, io.archivesunleashed, org.apache.parquet, io.github.seabow, org.apache.inlong, io.kestra.plugin, com.github.samtools, (+11 more)
org.jspecify  [fork: keep `org.jspecify`, `io.trino.tpcds` still publishes the name]
  A * org.jspecify                         2021-07..2024-07 1.0.0                |.........=======....|
  ?   io.trino.tpcds                       2026-06..2026-06 1.6                  |...................=|
  R   de.pirckheimer-gymnasium             2026-04..2026-06 0.49.0               |...................=|
  R   io.github.openfeign.querydsl         2026-05..2026-06 7.4.0                |...................=|
  R   org.treblereel.gwt.xml.mapper        2025-10..2026-06 0.11                 |..................==|
  R   org.treblereel.gwt.json.mapper       2025-10..2026-06 0.8                  |..................==|
    + 43 more: org.treblereel.gwt.yaml.mapper, org.treblereel.j2cl.processors, com.google.appengine, org.kie.j2cl.tools.di, org.jboss.elemento, com.helger.kaltblut, org.kie.j2cl.tools.json.mapper, org.kie.j2cl.tools.processors, org.kie.j2cl.tools.xml.mapper, org.kie.j2cl.tools.yaml.mapper, com.power4j.fist3, io.cryostat, (+31 more)
com.fasterxml.jackson.datatype.jsr310  [fork: keep `com.fasterxml.jackson.datatype`, `io.github.cloudstub` still publishes the name]
  A * com.fasterxml.jackson.datatype       2017-10..2026-06 2.22.0               |.===================|
  ?   io.github.cloudstub                  2026-06..2026-06 0.1.0-beta.4         |...................=|
  R   cab.ml                               2026-05..2026-06 0.1.0                |...................=|
  ?   org.octopusden.octopus.reporting-service 2026-06..2026-06 2.0.2                |...................=|
  R   io.openlineage                       2022-06..2026-06 1.50.0               |...........=========|
  R   com.linkedin.iceberg                 2025-09..2026-06 1.5.2.15             |.................===|
    + 81 more: org.opencds.cqf.cql.ls, org.octopusden.octopus.automation.teamcity, org.openapitools, org.apache.hudi, org.apache.gravitino, org.byteveda.agenteval, org.codelibs.fess, org.apache.doris, io.spring.gradle, io.camunda.filestorage, org.octopusden.octopus.automation.artifactory, org.openapitools.openapidiff, (+69 more)
de.agilecoders.wicket.webjars  [fork: keep `de.agilecoders.wicket.webjars`, `io.github.arieslab` still publishes the name]
  ? * de.agilecoders.wicket.webjars        2023-10..2025-12 4.0.14               |..............=====.|
  ?   io.github.arieslab                   2026-06..2026-06 2.4.2                |...................=|
com.fasterxml.jackson.databind  [fork: keep `com.fasterxml.jackson.core`, `io.strimzi` still publishes the name]
  A * com.fasterxml.jackson.core           2017-09..2026-06 2.22.0               |.===================|
  R   io.strimzi                           2023-05..2026-06 1.1.0-RC1            |.............=======|
  R   com.flexemarkets                     2026-02..2026-06 0.0.3                |..................==|
  R   io.github.kathukyabrian              2025-02..2026-06 1.0.3                |................====|
  ?   io.github.wxganzhanfan               2026-06..2026-06 0.1.0                |...................=|
  R   org.jetbrains.intellij.plugins       2024-07..2026-06 1.407                |...............=====|
    + 445 more: io.github.tomas-samek, org.octopusden.octopus.sonar, com.linkedin.iceberg, com.icegreen, wang.minggen.flowgram, com.expediagroup.apiary, io.modelcontextprotocol.sdk, com.sitepark, org.octopusden.octopus.automation.release-management, com.azure.cosmos.spark, org.apache.spark, io.github.6000fish, (+433 more)
org.graalvm.truffle  [fork: keep `org.graalvm.truffle`, `ai.looktech` still publishes the name]
  A * org.graalvm.truffle                  2018-10..2026-04 25.0.3               |...=================|
  ?   ai.looktech                          2026-06..2026-06 2.6.1-looktech.0     |...................=|
  R   com.walmartlabs.concord.k8s          2026-05..2026-06 2.43.0               |...................=|
  R   com.walmartlabs.concord              2026-05..2026-06 2.43.0               |...................=|
  R   com.walmartlabs.concord.runtime.v1   2026-05..2026-06 2.43.0               |...................=|
  R   com.walmartlabs.concord.runtime.v2   2026-05..2026-06 2.43.0               |...................=|
    + 27 more: io.knish, com.liquibase.ext, com.arcadedb, io.hyperfoil.tools, org.opensearch.migrations.trafficcapture, sh.oso, org.mitre.synthea, com.molo17.gluesync.alpha, tools.dscode, ch.zizka.jbake, com.dbvis, io.camunda.connectors.community, (+15 more)
io.netty.handler.proxy  [fork: keep `io.netty`, `io.kestra` still publishes the name]
  A * io.netty                             2017-12..2026-06 4.1.135.Final        |..==================|
  R   io.kestra                            2025-08..2026-06 0.22.45              |.................===|
  ?   io.neonbee                           2026-06..2026-06 0.37.30              |...................=|
  R   com.facebook.presto                  2026-04..2026-06 0.298.1              |...................=|
  R   io.sirix                             2026-04..2026-06 1.0.0-alpha17        |...................=|
  R   io.micronaut.starter                 2025-06..2026-06 4.10.16              |.................===|
    + 5 more: org.apache.iceberg, org.apache.grails, io.kestra.plugin, com.frog-development.consul-populate, io.kestra.storage
org.apache.commons.pool2  [fork: keep `org.apache.commons`, `org.openjproxy` still publishes the name]
  A * org.apache.commons                   2020-07..2021-08 2.11.1               |.......===..........|
  R   org.openjproxy                       2026-03..2026-06 0.5.0-beta           |..................==|
  ?   com.liquibase.ext                    2026-06..2026-06 5.2.0                |...................=|
  R   org.apache.directory.api             2023-10..2026-05 2.1.8                |.............=======|
  R   io.github.caobahuong                 2026-05..2026-05 0.1.1                |...................=|
  R   org.apache.druid.extensions.contrib  2024-06..2026-04 37.0.0               |...............=====|
    + 5 more: com.redis, org.noear, io.github.hexsook, org.apache.storm, com.vlkan.log4j2
org.slf4j  [fork: keep `org.slf4j`, `io.github.cyber655` still publishes the name]
  A * org.slf4j                            2017-04..2026-05 2.0.18               |====================|
  ?   io.github.cyber655                   2026-06..2026-06 1.1.0                |...................=|
  R   enterprises.iwakura                  2025-09..2026-06 3.5.3                |.................===|
  R   it.rotaliano.salesforce              2026-03..2026-06 2.0.2                |..................==|
  R   org.talend.sdk.component.sample.feature 2025-06..2026-06 1.93.0               |.................===|
  R   io.continual                         2025-03..2026-06 0.3.42               |................====|
    + 314 more: dev.detekt, org.openidentityplatform.openam, io.testomat, io.github.vitorcamillo, org.expath.http.client, org.openidentityplatform.openidm, ru.loolzaaa.tgbot4j, us.springett, org.open-metadata, net.openhft, org.openidentityplatform.opendj, org.mustangproject, (+302 more)
com.github.librepdf.openpdf  [fork: keep `com.github.librepdf`, `io.github.icarius4iu` still publishes the name]
  A * com.github.librepdf                  2018-09..2026-05 3.0.5                |...=================|
  ?   io.github.icarius4iu                 2026-06..2026-06 0.1.0                |...................=|
  R   com.guicedee.modules.services        2026-04..2026-06 2.1.0                |...................=|
  R   net.ioze                             2026-01..2026-01 1.0.9                |..................=.|
  R   org.computate                        2023-11..2024-02 2.0.2                |..............=.....|
  R   io.github.darkxanter                 2023-10..2023-10 1.3.31               |..............=.....|
    + 2 more: com.github.kwart.jsign, com.guicedee.services
org.htmlunit.websocket.client  [fork: keep `org.htmlunit`, `org.wetator` still publishes the name]
  ? * org.htmlunit                         2026-05..2026-06 5.2.0                |...................=|
  ?   org.wetator                          2026-06..2026-06 5.2.0                |...................=|
org.htmlunit.cyberneko  [fork: keep `org.htmlunit`, `org.seleniumhq.selenium` still publishes the name]
  ? * org.htmlunit                         2026-05..2026-06 5.2.0                |...................=|
  ?   org.seleniumhq.selenium              2026-06..2026-06 4.45.0               |...................=|
org.kotlincrypto.hash.sha2  [fork: keep `org.kotlincrypto.hash`, `io.github.zzzyyylllty.sertraline` still publishes the name]
  ? * org.kotlincrypto.hash                2024-03..2025-09 0.8.0                |..............====..|
  ?   io.github.zzzyyylllty.sertraline     2026-06..2026-06 3.9.2                |...................=|
org.yaml.snakeyaml  [fork: keep `org.yaml`, `io.github.spah1879` still publishes the name]
  A * org.yaml                             2019-02..2026-02 2.6                  |....===============.|
  R   io.github.spah1879                   2024-10..2026-06 1.2.0                |................====|
  R   com.huaweicloud.sdk                  2024-01..2026-06 3.1.201              |..............======|
  R   org.conductoross                     2026-03..2026-06 3.31.0-rc.4          |..................==|
  R   com.nvidia                           2023-04..2026-06 26.04.6              |.............=======|
  R   org.apache.flink                     2025-03..2026-06 2.3.0                |................====|
    + 77 more: io.github.wangscu, com.liquibase, com.sparkutils, io.vertx, com.arcmutate, dev.feit, com.google.cloud, dev.domkss, org.apache.phoenix, com.scivicslab, com.scivicslab.turingworkflow.plugins, com.uchicom, (+65 more)
org.jsoup  [fork: keep `org.jsoup`, `org.finos.legend.sdlc` still publishes the name]
  A * org.jsoup                            2018-04..2026-04 1.22.2               |..==================|
  R   org.finos.legend.sdlc                2026-04..2026-06 0.225.4              |...................=|
  R   io.get-coursier                      2024-02..2026-06 2.1.25-M26           |..............======|
  R   software.amazon.jdbc                 2025-09..2026-06 4.1.0                |.................===|
  R   org.jboss.pnc.bacon                  2025-07..2026-06 3.5.0                |.................===|
  R   org.apache.flink                     2026-02..2026-06 0.3.0                |..................==|
    + 35 more: io.github.padreati, org.scala-sbt, org.kie.j2cl.tools.di.ui, io.github.searchable-io, com.qainsights, org.graylog2, com.sonatype.clm, org.testingisdocumenting.znai, com.github.tsantalis, org.apache.tika, io.yupiik.maven, org.spdx, (+23 more)
org.eclipse.jetty.security  [fork: keep `org.eclipse.jetty`, `org.sonatype.nexus.common.components` still publishes the name]
  A * org.eclipse.jetty                    2018-11..2026-05 12.0.36              |....================|
  R   org.sonatype.nexus.common.components 2026-02..2026-06 3.93.1-04            |..................==|
  ?   ch.exense.step                       2026-06..2026-06 3.30.0               |...................=|
  R   org.sonatype.nexus.jetty             2025-09..2026-01 3.87.2-01            |.................==.|
jakarta.inject  [fork: keep `jakarta.inject`, `org.openidentityplatform.openig` still publishes the name]
  A * jakarta.inject                       2020-04..2021-10 2.0.1                |.......====.........|
  ?   org.openidentityplatform.openig      2026-06..2026-06 6.1.1                |...................=|
  ?   org.kill-bill.billing                2026-06..2026-06 0.42.0               |...................=|
  R   com.google.gerrit                    2023-10..2026-06 3.13.7               |..............======|
  R   io.joynr.java.core                   2026-01..2026-06 1.24.8               |..................==|
  R   dev.getelements.elements             2025-04..2026-03 3.6.32               |.................==.|
    + 13 more: io.github.jolt-community.jolt, it.netgrid, network.sloud.hytale, com.uchicom, com.google.tsunami, io.github.avistotelecom, org.apache.opennlp, com.google.template, io.github.openfeign.querydsl, org.reploop, com.guicedee.services, io.github.jbock-java, (+1 more)
com.github.benmanes.caffeine  [fork: keep `com.github.ben-manes.caffeine`, `org.kill-bill.billing` still publishes the name]
  A * com.github.ben-manes.caffeine        2017-12..2023-08 3.1.8                |..============......|
  ?   org.kill-bill.billing                2026-06..2026-06 0.42.0               |...................=|
  R   io.zeebe.redis                       2025-05..2026-06 8.9.1                |.................===|
  R   com.google.errorprone                2022-04..2026-06 2.50.0               |..........==========|
  R   io.pebbletemplates                   2025-12..2026-05 4.1.2                |..................==|
  R   net.wirelabs                         2026-02..2026-04 1.4.5                |..................==|
    + 15 more: org.apache.tinkerpop, com.janeluo, nl.basjes.parse.useragent, org.openprovenance.prov, org.odftoolkit, io.tileverse.pmtiles, org.opengis.cite, nl.goodbytes.xmpp.xep, org.igniterealtime.whack, be.vlaanderen.informatievlaanderen.ldes.ldio, com.gitlab.cdc-java.office, io.github.sinri, (+3 more)
io.netty.codec.http  [fork: keep `io.netty`, `com.amazonaws` still publishes the name]
  A * io.netty                             2017-12..2026-06 4.1.135.Final        |..==================|
  R   com.amazonaws                        2026-02..2026-06 2026.24.1            |..................==|
  ?   org.apache.flink                     2026-06..2026-06 2.3.0                |...................=|
  R   com.xuxueli                          2025-08..2026-06 2.2.1                |.................===|
  ?   io.camunda.connector                 2026-06..2026-06 8.7.21               |...................=|
  R   dev.zio                              2025-05..2026-05 3.11.2               |.................===|
    + 4 more: org.apache.tika, org.eclipse.ditto, io.sapl, de.fraunhofer.iosb.ilt.faaast.service
io.netty.codec.http2  [fork: keep `io.netty`, `com.applitools` still publishes the name]
  A * io.netty                             2017-12..2026-06 4.1.135.Final        |..==================|
  R   com.applitools                       2026-05..2026-06 5.87.7               |...................=|
  ?   io.camunda.connector                 2026-06..2026-06 8.7.21               |...................=|
  R   org.apache.spark                     2025-10..2026-05 4.1.2                |..................==|
  R   org.apache.iceberg                   2026-05..2026-05 1.11.0               |...................=|
  R   org.eclipse.ditto                    2025-09..2026-05 3.9.0                |.................===|
    + 10 more: io.github.jdbc-armour, io.github.cstopyak, it.neckar.open, net.xdob.ratly, io.micronaut.testresources, com.exactpro.th2, io.etcd, io.kestra.storage, org.wiremock, io.github.sunny-chung
org.apache.commons.text  [fork: keep `org.apache.commons`, `com.telamin.fluxtion` still publishes the name]
  A * org.apache.commons                   2018-03..2020-07 1.9                  |..======............|
  R   com.telamin.fluxtion                 2026-05..2026-06 1.0.9                |...................=|
  ?   net.officefloor.tutorial             2026-06..2026-06 4.0.0                |...................=|
  R   org.bonitasoft.engine.data           2026-01..2026-04 11.0.0               |..................==|
  R   com.vmlens                           2026-01..2026-04 1.2.28               |..................==|
  R   ru.biosoft.diagrams                  2026-01..2026-02 1.0.3                |..................=.|
    + 17 more: io.github.venkateshamurthy, dev.jbang, io.github.davidwhitlock.joy, io.github.pro4d, org.bidib.com.github.markusbernhardt, fr.lirmm.graphik, io.github.noeltoy, io.github.mderevyankoaqa, com.salesforce.functions, org.opendaylight.aaa, org.zowe.client.java.sdk, com.jkoolcloud.tnt4j.streams, (+5 more)
org.snakeyaml.engine.v2  [fork: keep `org.snakeyaml`, `com.walmartlabs.concord.k8s` still publishes the name]
  A * org.snakeyaml                        2019-10..2025-07 2.10                 |.....=============..|
  ?   com.walmartlabs.concord.k8s          2026-06..2026-06 2.42.0               |...................=|
  R   com.datadoghq                        2025-06..2026-06 1.63.0               |.................===|
  ?   org.apache.zeppelin                  2026-05..2026-05 0.12.1               |...................=|
  R   org.frankframework                   2025-01..2026-04 10.0.1               |................====|
  R   io.acryl                             2026-03..2026-04 1.5.0.5              |..................==|
    + 25 more: io.strimzi, io.github.phompang, eu.koboo, io.dscope.camel, org.workflomics, io.github.ethanz0x0, org.sonarsource.iac, ch.framedev, com.atlan, org.pkl-lang, io.kestra.plugin, io.github.frame-dev, (+13 more)
io.github.humbleui.skija.android.arm64  [fork: keep `io.github.humbleui`, `com.behemiron.engine` still publishes the name]
  ? * io.github.humbleui                   2026-01..2026-06 0.143.17             |..................==|
  ?   com.behemiron.engine                 2026-06..2026-06 0.143.17             |...................=|
io.github.humbleui.skija.android.x64  [fork: keep `io.github.humbleui`, `com.behemiron.engine` still publishes the name]
  ? * io.github.humbleui                   2026-01..2026-06 0.143.17             |..................==|
  ?   com.behemiron.engine                 2026-06..2026-06 0.143.17             |...................=|
io.github.humbleui.skija.linux.arm64  [fork: keep `io.github.humbleui`, `com.behemiron.engine` still publishes the name]
  ? * io.github.humbleui                   2025-11..2026-06 0.143.17             |..................==|
  ?   com.behemiron.engine                 2026-06..2026-06 0.143.17             |...................=|
io.github.humbleui.skija.linux.x64  [fork: keep `io.github.humbleui`, `com.behemiron.engine` still publishes the name]
  ? * io.github.humbleui                   2022-12..2026-06 0.143.17             |............========|
  ?   com.behemiron.engine                 2026-06..2026-06 0.143.17             |...................=|
io.github.humbleui.skija.macos.arm64  [fork: keep `io.github.humbleui.skija`, `com.behemiron.engine` still publishes the name]
  A * io.github.humbleui.skija             2021-11..2021-11 0.96.0               |..........=.........|
  ?   com.behemiron.engine                 2026-06..2026-06 0.143.17             |...................=|
  A   io.github.humbleui                   2021-12..2026-06 0.143.17             |..........==========|
io.github.humbleui.skija.macos.x64  [fork: keep `io.github.humbleui.skija`, `com.behemiron.engine` still publishes the name]
  A * io.github.humbleui.skija             2021-11..2021-11 0.96.0               |..........=.........|
  ?   com.behemiron.engine                 2026-06..2026-06 0.143.17             |...................=|
  A   io.github.humbleui                   2021-12..2026-06 0.143.17             |..........==========|
io.github.humbleui.skija.shared  [fork: keep `io.github.humbleui.skija`, `com.behemiron.engine` still publishes the name]
  A * io.github.humbleui.skija             2021-11..2021-11 0.96.0               |..........=.........|
  ?   com.behemiron.engine                 2026-06..2026-06 0.143.17             |...................=|
  A   io.github.humbleui                   2021-12..2026-06 0.143.17             |..........==========|
io.github.humbleui.skija.windows.arm64  [fork: keep `io.github.humbleui`, `com.behemiron.engine` still publishes the name]
  ? * io.github.humbleui                   2026-05..2026-06 0.143.17             |...................=|
  ?   com.behemiron.engine                 2026-06..2026-06 0.143.17             |...................=|
io.github.humbleui.skija.windows.x64  [fork: keep `io.github.humbleui`, `com.behemiron.engine` still publishes the name]
  ? * io.github.humbleui                   2022-12..2026-06 0.143.17             |............========|
  ?   com.behemiron.engine                 2026-06..2026-06 0.143.17             |...................=|
io.netty.transport  [fork: keep `io.netty`, `io.github.qbsstg` still publishes the name]
  A * io.netty                             2017-12..2026-06 4.1.135.Final        |..==================|
  ?   io.github.qbsstg                     2026-06..2026-06 0.17.0               |...................=|
  R   com.arcadedb                         2025-04..2026-06 26.6.1               |.................===|
  R   io.github.lukaszsamson               2026-04..2026-04 0.1.0                |...................=|
  R   com.sportradar.unifiedodds.sdk       2026-02..2026-03 5.0.0-rc4            |..................=.|
  R   io.karatelabs                        2025-10..2025-11 1.5.2                |..................=.|
com.fasterxml.jackson.module.paramnames  [fork: keep `com.fasterxml.jackson.module`, `org.realityforge.router.fu` still publishes the name]
  A * com.fasterxml.jackson.module         2017-10..2026-06 2.22.0               |.===================|
  ?   org.realityforge.router.fu           2026-06..2026-06 0.47                 |...................=|
  ?   org.realityforge.react4j             2026-06..2026-06 0.226                |...................=|
  ?   org.realityforge.arez                2026-06..2026-06 0.249                |...................=|
  ?   org.realityforge.sting               2026-06..2026-06 0.37                 |...................=|
  ?   org.realityforge.proton              2026-06..2026-06 0.72                 |...................=|
    + 6 more: com.infobip, io.kestra, com.araksis, com.araksis.sjd, io.github.codgen, io.micronaut.example
net.bytebuddy  [fork: keep `net.bytebuddy`, `com.logitags` still publishes the name]
  A * net.bytebuddy                        2017-05..2026-06 1.18.10              |.===================|
  ?   com.logitags                         2026-06..2026-06 2.3                  |...................=|
  R   de.gematik.test                      2024-08..2026-06 4.3.1                |...............=====|
  R   com.jcabi                            2025-11..2026-05 1.9.0                |..................==|
  R   org.lucee                            2026-04..2026-04 5.6.15.15-RC         |...................=|
  R   io.github.lucientong                 2026-04..2026-04 1.0.0                |...................=|
    + 124 more: dev.jorel, io.github.rocketbunny727, io.github.smallfast, net.aivory, ai.superstream, io.github.mlanett, com.appland, io.github.jlapugot.chronoguard, io.github.quiethappiness, com.graphql-java, net.dmulloy2, org.talend.sdk.component.sample.feature, (+112 more)
com.azure.storage.common  [fork: keep `com.azure`, `org.gaul` still publishes the name]
  A * com.azure                            2019-09..2026-06 12.34.0              |.....===============|
  R   org.gaul                             2025-11..2026-06 3.2.0                |..................==|
  ?   org.tomitribe.s3proxy                2026-06..2026-06 3.0.1                |...................=|
com.github.snksoft.crc  [fork: keep `com.github.snksoft`, `org.jurr.java.omniusb` still publishes the name]
  ? * com.github.snksoft                   2022-11..2022-11 1.1.0                |............=.......|
  ?   org.jurr.java.omniusb                2026-06..2026-06 1.0.1                |...................=|
org.springdoc.openapi.ai.common  [fork: keep `org.springdoc`, `io.github.vpelikh` still publishes the name]
  ? * org.springdoc                        2026-04..2026-04 3.0.3                |...................=|
  ?   io.github.vpelikh                    2026-06..2026-06 4.0.0                |...................=|
org.springdoc.openapi.common  [fork: keep `org.springdoc`, `io.github.vpelikh` still publishes the name]
  A * org.springdoc                        2020-12..2026-04 2.8.17               |........============|
  ?   io.github.vpelikh                    2026-06..2026-06 4.0.0                |...................=|
  R   io.github.lisi9988                   2025-07..2025-12 2.8.14               |.................==.|
org.springdoc.openapi.ui  [fork: keep `org.springdoc`, `io.github.vpelikh` still publishes the name]
  A * org.springdoc                        2020-12..2026-04 2.8.17               |........============|
  ?   io.github.vpelikh                    2026-06..2026-06 4.0.0                |...................=|
  R   io.github.lisi9988                   2025-07..2025-12 2.8.14               |.................==.|
org.springdoc.openapi.webflux.ai  [fork: keep `org.springdoc`, `io.github.vpelikh` still publishes the name]
  ? * org.springdoc                        2026-04..2026-04 3.0.3                |...................=|
  ?   io.github.vpelikh                    2026-06..2026-06 4.0.0                |...................=|
org.springdoc.openapi.webflux.core  [fork: keep `org.springdoc`, `io.github.vpelikh` still publishes the name]
  A * org.springdoc                        2020-12..2026-04 2.8.17               |........============|
  ?   io.github.vpelikh                    2026-06..2026-06 4.0.0                |...................=|
  R   io.github.lisi9988                   2025-08..2025-12 2.8.14               |.................==.|
org.springdoc.openapi.webflux.scalar  [fork: keep `org.springdoc`, `io.github.vpelikh` still publishes the name]
  A * org.springdoc                        2025-09..2026-04 2.8.17               |.................===|
  ?   io.github.vpelikh                    2026-06..2026-06 4.0.0                |...................=|
  R   io.github.lisi9988                   2025-12..2025-12 2.8.14               |..................=.|
org.springdoc.openapi.webflux.ui  [fork: keep `org.springdoc`, `io.github.vpelikh` still publishes the name]
  A * org.springdoc                        2020-12..2026-04 2.8.17               |........============|
  ?   io.github.vpelikh                    2026-06..2026-06 4.0.0                |...................=|
  R   io.github.lisi9988                   2025-08..2025-12 2.8.14               |.................==.|
org.springdoc.openapi.webmvc.ai  [fork: keep `org.springdoc`, `io.github.vpelikh` still publishes the name]
  ? * org.springdoc                        2026-04..2026-04 3.0.3                |...................=|
  ?   io.github.vpelikh                    2026-06..2026-06 4.0.0                |...................=|
org.springdoc.openapi.webmvc.core  [fork: keep `org.springdoc`, `io.github.vpelikh` still publishes the name]
  A * org.springdoc                        2020-12..2026-04 2.8.17               |........============|
  ?   io.github.vpelikh                    2026-06..2026-06 4.0.0                |...................=|
  R   io.github.lisi9988                   2025-07..2025-12 2.8.14               |.................==.|
org.springdoc.openapi.webmvc.scalar  [fork: keep `org.springdoc`, `io.github.vpelikh` still publishes the name]
  A * org.springdoc                        2025-09..2026-04 2.8.17               |.................===|
  ?   io.github.vpelikh                    2026-06..2026-06 4.0.0                |...................=|
  R   io.github.lisi9988                   2025-12..2025-12 2.8.14               |..................=.|
io.netty.handler  [fork: keep `io.netty`, `org.apache.flink` still publishes the name]
  A * io.netty                             2017-12..2026-06 4.1.135.Final        |..==================|
  R   org.apache.flink                     2026-01..2026-06 2.3.0                |..................==|
  ?   org.openidentityplatform.opendj      2026-06..2026-06 5.1.1                |...................=|
  R   eu.michael-simons.neo4j              2025-07..2026-06 4.1.0                |.................===|
  ?   io.camunda.connector                 2026-06..2026-06 8.7.21               |...................=|
  R   org.apache.tika                      2025-04..2026-05 3.3.1                |.................===|
    + 5 more: org.apache.storm, io.github.ousatov-ua, io.kestra.plugin, org.lucee, com.luhuiguo.netty
tools.jackson.core  [fork: keep `tools.jackson.core`, `org.pragmatica-lite.aether` still publishes the name]
  A * tools.jackson.core                   2025-03..2026-06 3.2.0                |................====|
  ?   org.pragmatica-lite.aether           2026-06..2026-06 1.0.0-rc1            |...................=|
  R   ru.tinkoff.piapi                     2026-01..2026-06 1.49.1               |..................==|
  ?   com.phonepe.sentinel-ai              2026-06..2026-06 1.1.1-alpha8         |...................=|
  ?   fish.payara.tools                    2026-06..2026-06 1.0.0-Alpha3         |...................=|
  R   io.github.ignf                       2026-05..2026-05 2.0.0                |...................=|
    + 19 more: software.xdev.mockserver, media.barney, org.sonarsource.sonarlint.ls, io.github.tansuasici, com.limemojito.oss.standards, com.limemojito.oss.standards.development-test, io.sapl, com.io7m.montarre, io.github.shangtx, net.unit8.enkan, org.opentripplanner, io.github.anmol023, (+7 more)
org.apache.logging.log4j  [fork: keep `org.apache.logging.log4j`, `com.nqadmin.swingset.demo` still publishes the name]
  A * org.apache.logging.log4j             2017-11..2026-05 2.26.0               |..==================|
  ?   com.nqadmin.swingset.demo            2026-06..2026-06 4.0.13               |...................=|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
  R   org.apache.hudi                      2023-02..2026-06 0.14.2               |............========|
  R   com.alibaba.ververica                2020-08..2026-06 1.20-vvr-11.5.1-jdk11 |.......=============|
  R   io.github.zhouzhoucoder              2026-05..2026-06 5.0                  |...................=|
    + 324 more: org.beilstein, com.ibm.galasa, io.github.beehive-lab, io.github.uwegeercken, org.hpccsystems, org.into-cps.maestro, com.adobe.campaign.tests.bridge.service, io.camunda, com.robotaccomplice, dev.mauch, net.cg4j, nl.basjes.parse.useragent, (+312 more)
com.microsoft.onnxruntime  [fork: keep `com.microsoft.onnxruntime`, `io.github.eduramiba` still publishes the name]
  ? * com.microsoft.onnxruntime            2020-06..2026-05 1.26.0               |.......=============|
  ?   io.github.eduramiba                  2026-06..2026-06 1.26.0               |...................=|
com.fasterxml.jackson.module.jaxb  [fork: keep `com.fasterxml.jackson.module`, `com.datastax.oss` still publishes the name]
  A * com.fasterxml.jackson.module         2017-10..2026-06 2.22.0               |.===================|
  R   com.datastax.oss                     2021-06..2026-06 6.0.11               |.........===========|
  ?   com.liquibase                        2026-06..2026-06 5.2.0                |...................=|
  R   org.apache.dolphinscheduler          2025-03..2026-05 3.4.2                |................====|
  R   com.facebook.presto.spark            2026-05..2026-05 3.4.1-2              |...................=|
  R   com.rovio.ingest                     2024-10..2026-04 1.0.8_spark_3.4.1    |...............=====|
    + 91 more: org.apache.seatunnel, org.apache.pulsar, com.ascentstream.pulsar, io.github.solven-eu.cleanthat, com.oceanbase, io.github.dodogeny, com.solacecoe.connectors, com.seeq, io.streamnative.connectors, org.apache.phoenix, com.kenstott.components, io.cdap.cdap, (+79 more)
org.apache.commons.configuration2  [fork: keep `org.apache.commons`, `org.wso2.orbit.org.apache.commons` still publishes the name]
  A * org.apache.commons                   2017-10..2022-06 2.8.0                |.===========........|
  ?   org.wso2.orbit.org.apache.commons    2026-06..2026-06 2.15.1.wso2v1        |...................=|
  R   org.neo4j.procedure                  2024-04..2026-01 4.4.0.40             |...............====.|
  R   com.databricks.labs                  2025-05..2026-01 0.6.17               |.................==.|
  R   software.amazon.s3tables             2024-12..2025-08 0.1.8                |................==..|
  R   com.sonatype.central.testing.amazon  2025-06..2025-06 0.1.7                |.................=..|
    + 2 more: org.bidib.com.github.markusbernhardt, consulting.freiheitsgrade.patched
org.apache.jena.cmds  [fork: keep `org.apache.jena`, `com.vaimee` still publishes the name]
  ? * org.apache.jena                      2018-06..2026-05 6.1.0                |...=================|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.dboe.index  [fork: keep `org.apache.jena`, `com.vaimee` still publishes the name]
  ? * org.apache.jena                      2018-12..2026-05 6.1.0                |....================|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.jdbc.driver.remote  [fork: keep `org.apache.jena`, `com.vaimee` still publishes the name]
  ? * org.apache.jena                      2019-10..2023-10 4.10.0               |.....==========.....|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.shex  [fork: keep `org.apache.jena`, `com.vaimee` still publishes the name]
  ? * org.apache.jena                      2021-09..2026-05 6.1.0                |.........===========|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.arq  [fork: keep `org.apache.jena`, `com.vaimee` still publishes the name]
  ? * org.apache.jena                      2018-06..2026-05 6.1.0                |...=================|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.commonsrdf  [fork: keep `org.apache.jena`, `com.vaimee` still publishes the name]
  ? * org.apache.jena                      2020-05..2026-05 6.1.0                |.......=============|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.dboe.base  [fork: keep `org.apache.jena`, `com.vaimee` still publishes the name]
  ? * org.apache.jena                      2020-01..2026-05 6.1.0                |......==============|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.dboe.storage  [fork: keep `org.apache.jena`, `com.vaimee` still publishes the name]
  ? * org.apache.jena                      2019-10..2026-05 6.1.0                |.....===============|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.dboe.trans.data  [fork: keep `org.apache.jena`, `com.vaimee` still publishes the name]
  ? * org.apache.jena                      2018-12..2026-05 6.1.0                |....================|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.dboe.transaction  [fork: keep `org.apache.jena`, `com.vaimee` still publishes the name]
  ? * org.apache.jena                      2018-12..2026-05 6.1.0                |....================|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.ext.com.google  [fork: keep `org.apache.jena`, `com.vaimee` still publishes the name]
  ? * org.apache.jena                      2018-06..2023-04 4.8.0                |...===========......|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.fuseki.access  [fork: keep `org.apache.jena`, `com.vaimee` still publishes the name]
  ? * org.apache.jena                      2018-12..2026-05 6.1.0                |....================|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.fuseki.core  [fork: keep `org.apache.jena`, `com.vaimee` still publishes the name]
  ? * org.apache.jena                      2018-12..2026-05 6.1.0                |....================|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.jdbc.core  [fork: keep `org.apache.jena`, `com.vaimee` still publishes the name]
  ? * org.apache.jena                      2019-10..2023-10 4.10.0               |.....==========.....|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.jdbc.driver.tdb  [fork: keep `org.apache.jena`, `com.vaimee` still publishes the name]
  ? * org.apache.jena                      2019-10..2023-10 4.10.0               |.....==========.....|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.permissions  [fork: keep `org.apache.jena`, `com.vaimee` still publishes the name]
  ? * org.apache.jena                      2018-06..2025-10 5.6.0                |...================.|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.querybuilder  [fork: keep `org.apache.jena`, `com.vaimee` still publishes the name]
  ? * org.apache.jena                      2020-05..2026-05 6.1.0                |.......=============|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.rdfconnection  [fork: keep `org.apache.jena`, `com.vaimee` still publishes the name]
  ? * org.apache.jena                      2018-06..2026-05 6.1.0                |...=================|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.shacl  [fork: keep `org.apache.jena`, `com.vaimee` still publishes the name]
  ? * org.apache.jena                      2019-10..2026-05 6.1.0                |.....===============|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.tdb  [fork: keep `org.apache.jena`, `com.vaimee` still publishes the name]
  ? * org.apache.jena                      2018-06..2026-05 6.1.0                |...=================|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.tdb2  [fork: keep `org.apache.jena`, `com.vaimee` still publishes the name]
  ? * org.apache.jena                      2018-06..2026-05 6.1.0                |...=================|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.text  [fork: keep `org.apache.jena`, `com.vaimee` still publishes the name]
  ? * org.apache.jena                      2018-06..2026-05 6.1.0                |...=================|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
jakarta.json  [fork: keep `jakarta.json`, `com.vaimee` still publishes the name]
  A * jakarta.json                         2020-01..2023-10 2.1.3                |......=========.....|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
  R   org.eclipse.parsson                  2021-06..2026-05 1.1.9                |.........===========|
  R   io.github.qudtlib                    2026-02..2026-02 7.2.0                |..................=.|
  R   com.arangodb                         2025-08..2026-01 1.9.0                |.................==.|
  R   io.quarkus                           2024-10..2025-02 3.18.4               |................=...|
    + 9 more: org.openpreservation.jhove, zone.cogni.semanticz, com.exasol, io.github.changebooks, com.atomgraph.etl.csv, org.avaje.experiment, org.spdx, org.glassfish, com.mparticle
org.apache.jena.base  [fork: keep `org.apache.jena`, `com.vaimee` still publishes the name]
  ? * org.apache.jena                      2018-06..2026-05 6.1.0                |...=================|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.core  [fork: keep `org.apache.jena`, `com.vaimee` still publishes the name]
  ? * org.apache.jena                      2018-06..2026-05 6.1.0                |...=================|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.dboe.index.test  [fork: keep `org.apache.jena`, `com.vaimee` still publishes the name]
  ? * org.apache.jena                      2018-12..2026-05 6.1.0                |....================|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.fuseki.main  [fork: keep `org.apache.jena`, `com.vaimee` still publishes the name]
  ? * org.apache.jena                      2018-12..2026-05 6.1.0                |....================|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.geosparql  [fork: keep `org.apache.jena`, `com.vaimee` still publishes the name]
  ? * org.apache.jena                      2019-09..2026-05 6.1.0                |.....===============|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.iri  [fork: keep `org.apache.jena`, `com.vaimee` still publishes the name]
  ? * org.apache.jena                      2018-06..2025-10 5.6.0                |...================.|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.jdbc.driver.mem  [fork: keep `org.apache.jena`, `com.vaimee` still publishes the name]
  ? * org.apache.jena                      2019-10..2023-10 4.10.0               |.....==========.....|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
com.aayushatharva.brotli4j  [fork: keep `com.aayushatharva.brotli4j`, `net.sourceforge.plantuml` still publishes the name]
  A * com.aayushatharva.brotli4j           2022-12..2026-04 1.23.0               |............========|
  ?   net.sourceforge.plantuml             2026-06..2026-06 1.2026.6             |...................=|
  R   org.apache.orc                       2024-11..2026-01 2.1.4                |................===.|
com.github.javaparser.core  [fork: keep `com.github.javaparser`, `org.key-project.proofjava` still publishes the name]
  A * com.github.javaparser                2017-12..2026-05 3.28.2               |..==================|
  ?   org.key-project.proofjava            2026-06..2026-06 3.28.0-K13.5         |...................=|
  R   org.checkerframework                 2023-09..2026-05 3.28.1               |.............=======|
  R   org.mvel.javaparser                  2026-02..2026-02 3.25.5-mvel3-1       |..................=.|
  R   io.joern                             2022-06..2022-06 3.24.3-SL3           |...........=........|
com.github.javaparser.core.serialization  [fork: keep `com.github.javaparser`, `org.key-project.proofjava` still publishes the name]
  A * com.github.javaparser                2018-11..2026-05 3.28.2               |....================|
  ?   org.key-project.proofjava            2026-06..2026-06 3.28.0-K13.5         |...................=|
  R   org.mvel.javaparser                  2026-02..2026-02 3.25.5-mvel3-1       |..................=.|
  R   io.joern                             2022-06..2022-06 3.24.3-SL3           |...........=........|
com.github.javaparser.symbolsolver.core  [fork: keep `com.github.javaparser`, `org.key-project.proofjava` still publishes the name]
  A * com.github.javaparser                2018-01..2026-05 3.28.2               |..==================|
  ?   org.key-project.proofjava            2026-06..2026-06 3.28.0-K13.5         |...................=|
  R   org.mvel.javaparser                  2026-02..2026-02 3.25.5-mvel3-1       |..................=.|
  R   io.joern                             2022-06..2022-06 3.24.3-SL3           |...........=........|
com.fasterxml.jackson.jakarta.rs.json  [fork: keep `com.fasterxml.jackson.jakarta.rs`, `com.inteligr8.activiti` still publishes the name]
  A * com.fasterxml.jackson.jakarta.rs     2021-07..2026-06 2.22.0               |.........===========|
  ?   com.inteligr8.activiti               2026-06..2026-06 1.4.1-aps-v25.3      |...................=|
  R   ch.exense.step                       2022-10..2026-06 3.29.5               |............========|
  R   org.apache.tika                      2023-12..2026-05 3.3.1                |..............======|
  R   com.phonepe.sentinel-ai              2026-05..2026-05 1.1.2-SOLARIS-rc0    |...................=|
  R   ch.exense.step.library               2023-08..2026-05 1.0.31               |.............=======|
    + 13 more: org.eclipse.tractusx.edc, org.ow2.petals.samples.rest.edm, dev.getelements.elements, org.eclipse.edc.huawei, org.eclipse.edc.aws, org.eclipse.edc, io.nflow, com.brightsparklabs, io.trino.gateway, com.snehasishroy, com.smoketurner.dropwizard, org.kiwiproject, (+1 more)
org.eclipse.microprofile.config  [fork: keep `org.eclipse.microprofile.config`, `io.vidocq.ravel` still publishes the name]
  ? * org.eclipse.microprofile.config      2026-01..2026-04 3.1.1                |..................==|
  ?   io.vidocq.ravel                      2026-05..2026-06 0.1.1                |...................=|
org.eclipse.jetty.client  [fork: keep `org.eclipse.jetty`, `ch.exense.step` still publishes the name]
  A * org.eclipse.jetty                    2018-11..2026-05 12.0.36              |....================|
  ?   ch.exense.step                       2026-06..2026-06 3.30.0               |...................=|
  R   org.exploit                          2024-10..2026-04 1.0.9                |................====|
org.apache.commons.collections4  [fork: keep `org.apache.commons`, `com.guicedee.modules.services` still publishes the name]
  A * org.apache.commons                   2018-07..2019-07 4.4                  |...===..............|
  R   com.guicedee.modules.services        2026-04..2026-06 2.1.0                |...................=|
  ?   org.apache.directory.api             2026-05..2026-05 2.1.8                |...................=|
  R   io.github.qudtlib                    2024-12..2025-10 7.1.1                |................===.|
  R   de.jball                             2025-07..2025-07 0.9.0                |.................=..|
  R   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17        |......=====.........|
    + 1 more: com.jwebmp.jpms.commons
org.apache.logging.log4j.core  [fork: keep `org.apache.logging.log4j`, `nl.tno.org.portico` still publishes the name]
  A * org.apache.logging.log4j             2017-11..2026-05 2.26.0               |..==================|
  ?   nl.tno.org.portico                   2026-05..2026-05 2.1.3                |...................=|
  R   app.freerouting                      2026-05..2026-05 2.2.4                |...................=|
  R   org.beilstein                        2026-05..2026-05 1.1.2                |...................=|
  R   me.bechberger                        2026-03..2026-04 0.1.6                |..................==|
  R   io.openems                           2026-02..2026-04 3.3.0-openems.2      |..................==|
    + 19 more: com.kount, com.github.aquality-automation, com.ghgande, com.github.bilderherunterlader, com.gemecosystem.gemjar, org.lucee, io.github.alien-tools, com.webforj, io.github.egonw, com.liferay, de.fraunhofer.iem, net.maizegenetics, (+7 more)
org.mavai.punit.examples  [fork: keep `org.mavai`, `org.javai` still publishes the name]
  ? * org.mavai                            2026-05..2026-05 0.7.0                |...................=|
  ?   org.javai                            2026-05..2026-05 0.6.99               |...................=|
```

## shaded (12)

The natural-namespace owner is the earliest and most-recent publisher; every other group merely shades or bundles the name. Resolution is unchanged; this just records the decision so the module drops off the report.

- The owner is also the most-recent publisher (there is no later successor).
- The owner is the closest groupId to the module name: it shares the longest leading-segment prefix (hyphens ignored), even if the name is not strictly under it.
- Allow the natural owner; reject every group that merely shades the name.

| count | current owner | new owner(s) |
|---:|---|---|
| 1 | `org.neo4j.bolt` | `org.neo4j.bolt, org.neo4j.connectors` |

```
ch.qos.logback.classic  [owned by `ch.qos.logback`; 94 other group(s) shade the name]
  A * ch.qos.logback                       2018-01..2026-06 1.5.36               |..==================|
  R   com.daml                             2022-10..2026-06 3.6.0-snapshot.20260624.14723.0.v4145c783 |............========|
  R   com.limemojito.oss.aws               2025-10..2026-06 8.0.14               |..................==|
  ?   org.alfasoftware                     2026-06..2026-06 3.0.0                |...................=|
  ?   com.salesforce.cantor                2026-06..2026-06 0.5.24               |...................=|
  R   org.eclipse.ecsp                     2025-02..2026-06 1.3.0                |................====|
    + 89 more: org.javastro.ivoa, ch.exense.step, io.mosip.biosdk, io.mosip.demosdk, de.fraunhofer.iosb.ilt, org.apache.sling, dk.alexandra.fresco, org.jboss.pnc.gradle-manipulator, com.deltaproto, org.commonjava.atlas, club.dawdler, org.apache.zookeeper, (+77 more)
ch.qos.logback.core  [owned by `ch.qos.logback`; 32 other group(s) shade the name]
  A * ch.qos.logback                       2018-01..2026-06 1.5.36               |..==================|
  R   com.deltaproto                       2026-04..2026-06 1.1.6                |...................=|
  R   org.ton.ton4j                        2025-11..2026-06 2.1.0                |..................==|
  ?   org.springframework.cloud            2026-06..2026-06 5.0.2                |...................=|
  R   de.gematik.test                      2025-11..2026-06 4.3.1                |..................==|
  R   com.limemojito.oss.aws               2026-03..2026-06 8.0.12               |..................==|
    + 27 more: io.smallrye.reactive, com.effacy.jui, io.spicelabs, com.expediagroup, io.camunda, org.chenile, club.dawdler, org.jetbrains.kotlinx, io.github.neodix42, com.yetanalytics, org.opendaylight.bgpcep, fun.adaptive, (+15 more)
io.vertx.core  [owned by `io.vertx`; 3 other group(s) shade the name]
  A * io.vertx                             2020-05..2026-06 5.1.3                |.......=============|
  ?   io.sirix                             2026-06..2026-06 1.0.0-beta4          |...................=|
  R   io.github.crac.io.vertx              2023-08..2024-09 4.4.6.CRAC.0         |.............===....|
  R   one.gfw                              2023-03..2023-03 4.4.0                |............=.......|
io.opentelemetry.instrumentation_annotations  [owned by `io.opentelemetry.instrumentation`; 1 other group(s) shade the name]
  ? * io.opentelemetry.instrumentation     2023-10..2026-06 2.29.0               |..............======|
  ?   io.vidocq.humboldt                   2026-06..2026-06 0.1.1                |...................=|
com.github.luben.zstd_jni  [owned by `com.github.luben`; 18 other group(s) shade the name]
  A * com.github.luben                     2018-06..2026-06 1.5.7-11             |...=================|
  ?   com.timecho.timechodb                2026-06..2026-06 2.0.9.4              |...................=|
  R   com.snowflake                        2024-12..2026-06 3.5.4                |................====|
  R   org.apache.tsfile                    2024-11..2026-05 2.3.1                |................====|
  R   ai.h2o                               2024-05..2026-05 3.46.0.11            |...............=====|
  R   com.aliyun.openservices.eas          2024-06..2026-05 2.0.30               |...............=====|
    + 13 more: com.timecho.iotdb, org.chipsalliance, org.apache.celeborn, org.apache.iotdb, io.moderne, io.spicelabs, io.nosqlbench, io.github.willena, io.github.fernandolopes, org.apache.amoro, io.kroxylicious, io.github.azagniotov, (+1 more)
io.netty.internal.tcnative.openssl.linux.x86_64  [owned by `io.netty`; 10 other group(s) shade the name]
  A * io.netty                             2022-05..2026-06 2.0.79.Final         |...........=========|
  R   com.azure.cosmos.spark               2026-02..2026-06 4.49.0               |..................==|
  ?   io.camunda.connector                 2026-06..2026-06 8.7.21               |...................=|
  R   io.smallrye                          2026-04..2026-04 0.1.4                |...................=|
  R   org.apache.iotdb                     2026-04..2026-04 2.0.8                |...................=|
  R   io.zipkin.dependencies               2026-03..2026-03 3.2.2                |..................=.|
    + 5 more: com.danielflower.apprunner, io.karatelabs, io.opentelemetry.javaagent, com.github.emc-mongoose, io.servicetalk
io.netty.tcnative.classes.openssl  [owned by `io.netty`; 4 other group(s) shade the name]
  A * io.netty                             2022-03..2026-06 2.0.79.Final         |..........==========|
  ?   io.vertx                             2026-06..2026-06 4.5.28               |...................=|
  R   org.neo4j.driver                     2024-11..2026-06 4.4.24               |................====|
  R   io.kestra.plugin                     2024-10..2025-06 0.23.0               |...............===..|
  R   eu.michael-simons.neo4j              2024-10..2025-06 2.17.4               |................==..|
com.azure.http.netty  [owned by `com.azure`; 1 other group(s) shade the name]
  ? * com.azure                            2019-11..2026-06 1.16.5               |......==============|
  ?   io.lakefs                            2026-06..2026-06 0.24.1               |...................=|
org.neo4j.bolt.connection.routed  [owned by `org.neo4j.bolt`; 0 other group(s) shade the name]
  ? * org.neo4j.bolt                       2025-03..2026-06 12.0.0               |................====|
  ?   org.neo4j.connectors                 2026-06..2026-06 6.0.0-RC01-s_2.13    |...................=|
io.opentelemetry.api  [owned by `io.opentelemetry`; 1 other group(s) shade the name]
  ? * io.opentelemetry                     2020-03..2026-06 1.63.0               |......==============|
  ?   io.vidocq.humboldt                   2026-06..2026-06 0.1.1                |...................=|
io.opentelemetry.context  [owned by `io.opentelemetry`; 1 other group(s) shade the name]
  ? * io.opentelemetry                     2020-11..2026-06 1.63.0               |........============|
  ?   io.vidocq.humboldt                   2026-06..2026-06 0.1.1                |...................=|
io.netty.transport.epoll.linux.x86_64  [owned by `io.netty`; 4 other group(s) shade the name]
  A * io.netty                             2022-05..2026-06 4.1.135.Final        |...........=========|
  ?   io.camunda.connector                 2026-06..2026-06 8.7.21               |...................=|
  R   org.apache.storm                     2025-05..2026-05 2.8.8                |.................===|
  R   com.atscale.opensource               2026-01..2026-01 1.14                 |..................=.|
  R   org.readutf.orchestrator             2025-02..2025-02 2.0.0                |................=...|
```

## tld-dropped (1)

The dominant owner's groupId with its top-level domain dropped is the module-name prefix.

- The owner's groupId with its first segment (the top-level domain) removed is a prefix of the module name.
- Allow that owner; reject the rest.

```
roaringbitmap  [owned by `org.roaringbitmap` (groupId minus TLD is the module prefix); 3 other group(s) shade the name]
  A * org.roaringbitmap                    2023-09..2026-04 1.6.14               |.............=======|
  ?   com.atomgraph.etl.csv                2026-06..2026-06 2.2.0                |...................=|
  R   org.apache.celeborn                  2024-06..2026-04 0.6.3                |...............=====|
  R   org.bitlap                           2023-10..2023-10 1.0.1.0              |..............=.....|
```

## two-segments (67)

The dominant owner's groupId with its first two segments dropped is the module-name prefix.

- The owner's groupId with its first two segments removed is a prefix of the module name.
- Allow that owner; reject the rest.

| count | current owner | new owner(s) |
|---:|---|---|
| 1 | `com.telenav.cactus` | `com.telenav.cactus, com.telenav.lexakai` |

```
jackson.datatype.pcollections  [owned by `com.fasterxml.jackson.datatype` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * com.fasterxml.jackson.datatype       2019-07..2026-06 2.22.0               |.....===============|
  ?   tools.jackson.datatype               2025-03..2026-06 3.2.0                |................====|
mosaic.animation  [owned by `com.jakewharton.mosaic` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * com.jakewharton.mosaic               2025-08..2025-08 0.18.0               |.................=..|
  ?   ee.schimke.composeai.mosaic          2026-05..2026-05 0.18.0-1             |...................=|
mosaic.runtime  [owned by `com.jakewharton.mosaic` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * com.jakewharton.mosaic               2025-08..2025-08 0.18.0               |.................=..|
  ?   ee.schimke.composeai.mosaic          2026-05..2026-05 0.18.0-1             |...................=|
mosaic.tty  [owned by `com.jakewharton.mosaic` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * com.jakewharton.mosaic               2025-08..2025-08 0.18.0               |.................=..|
  ?   ee.schimke.composeai.mosaic          2026-05..2026-05 0.18.0-1             |...................=|
mosaic.terminal  [owned by `com.jakewharton.mosaic` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * com.jakewharton.mosaic               2025-08..2025-08 0.18.0               |.................=..|
  ?   ee.schimke.composeai.mosaic          2026-05..2026-05 0.18.0-1             |...................=|
mosaic.testing  [owned by `com.jakewharton.mosaic` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * com.jakewharton.mosaic               2025-08..2025-08 0.18.0               |.................=..|
  ?   ee.schimke.composeai.mosaic          2026-05..2026-05 0.18.0-1             |...................=|
mosaic.tty.terminal  [owned by `com.jakewharton.mosaic` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * com.jakewharton.mosaic               2025-08..2025-08 0.18.0               |.................=..|
  ?   ee.schimke.composeai.mosaic          2026-05..2026-05 0.18.0-1             |...................=|
tucache.core  [owned by `co.tunan.tucache` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * co.tunan.tucache                     2024-02..2024-02 1.0.4.RELEASE        |..............=.....|
  ?   io.github.tri5m                      2024-12..2026-04 1.0.6                |................====|
tucache.spring.boot.autoconfigure  [owned by `co.tunan.tucache` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * co.tunan.tucache                     2024-02..2024-02 1.0.4.RELEASE        |..............=.....|
  ?   io.github.tri5m                      2024-12..2026-04 1.0.6                |................====|
tucache.spring.boot.starter  [owned by `co.tunan.tucache` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * co.tunan.tucache                     2024-02..2024-02 1.0.4.RELEASE        |..............=.....|
  ?   io.github.tri5m                      2024-12..2026-04 1.0.6                |................====|
dagger  [owned by `com.google.dagger` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * com.google.dagger                    2021-06..2026-02 2.59.2               |.........==========.|
  ?   io.github.licy5352.dagger            2022-02..2026-03 2.55-kim-rc1         |..........=========.|
  ?   me.gulya.dagger                      2025-08..2025-08 2.56.2-workaround10  |.................=..|
  ?   io.github.jbock-java                 2021-10..2022-03 2.41.2               |.........==.........|
tuweni.bytes  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.consensys.tuweni                  2025-02..2025-10 2.7.2                |................===.|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0                |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.concurrent  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.consensys.tuweni                  2025-02..2025-10 2.7.2                |................===.|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0                |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.concurrent_coroutines  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.consensys.tuweni                  2025-02..2025-10 2.7.2                |................===.|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0                |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.config  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.consensys.tuweni                  2025-02..2025-10 2.7.2                |................===.|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0                |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.crypto  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.consensys.tuweni                  2025-02..2025-10 2.7.2                |................===.|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0                |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.devp2p  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.consensys.tuweni                  2025-02..2025-10 2.7.2                |................===.|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0                |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.io  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.consensys.tuweni                  2025-02..2025-10 2.7.2                |................===.|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0                |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.junit  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.consensys.tuweni                  2025-02..2025-10 2.7.2                |................===.|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0                |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.kademlia  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.consensys.tuweni                  2025-02..2025-10 2.7.2                |................===.|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0                |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.net  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.consensys.tuweni                  2025-02..2025-10 2.7.2                |................===.|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0                |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.rlp  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.consensys.tuweni                  2025-02..2025-10 2.7.2                |................===.|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0                |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.ssz  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.consensys.tuweni                  2025-02..2025-10 2.7.2                |................===.|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0                |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.toml  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.consensys.tuweni                  2025-02..2025-10 2.7.2                |................===.|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0                |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.units  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.consensys.tuweni                  2025-02..2025-10 2.7.2                |................===.|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0                |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
glide.api  [owned by `software.amazon.glide` (groupId minus two segments is the module prefix); 2 other group(s) shade the name]
  ? * software.amazon.glide                2024-06..2024-06 0.4.3                |...............=....|
  ?   io.valkey                            2024-07..2025-10 2.1.1                |...............====.|
  ?   io.github.gumpacg                    2024-08..2024-08 0.1.0                |...............=....|
afterburner.fx  [owned by `com.dlsc.afterburner` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * com.dlsc.afterburner                 2019-10..2023-07 2.3.0                |.....=========......|
  ?   org.jabref                           2023-09..2023-09 2.0.0                |.............=......|
tuweni.wallet  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.scuttlebutt_rpc  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.scuttlebutt_handshake  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.scuttlebutt_discovery  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.scuttlebutt_client_lib  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.scuttlebutt  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.rlpx  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.pow  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.plumtree  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.peer_repository  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.metrics  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.merkle_trie  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.les  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.kv  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.jsonrpc_app  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.jsonrpc  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.hobbits_relayer  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.hobbits  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.gossip  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.genesis  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.evm_dsl  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-09..2022-11 2.3.1                |...........==.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.evm  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.ethstats  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.eth_repository  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.eth_precompiles  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-09..2022-11 2.3.1                |...........==.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.eth_faucet  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.eth_crawler  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.eth_client_ui  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.eth_client_app  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.eth_client  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.eth_blockprocessor  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-09..2022-11 2.3.1                |...........==.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.eth  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.dns_discovery  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.devp2p_proxy  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.devp2p_eth  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.app_commons  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
cactus.maven.xml  [owned by `com.telenav.cactus` (groupId minus two segments is the module prefix); 0 other group(s) shade the name]
  ? * com.telenav.cactus                   2022-06..2022-11 1.5.49               |...........==.......|
  ?   com.telenav.lexakai                  2022-09..2022-10 1.0.13               |...........=........|
telegram4j.tl.api  [owned by `io.github.telegram4j` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * io.github.telegram4j                 2022-02..2022-02 0.1.0                |..........=.........|
  ?   com.telegram4j                       2022-09..2022-09 0.1.1                |...........=........|
telegram4j.tl  [owned by `io.github.telegram4j` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * io.github.telegram4j                 2022-02..2022-02 0.1.0                |..........=.........|
  ?   com.telegram4j                       2022-09..2022-09 0.1.1                |...........=........|
stasgora.observetree  [owned by `io.github.stasgora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * io.github.stasgora                   2019-04..2019-09 1.0.3                |.....=..............|
  ?   dev.sgora                            2019-10..2019-10 1.0.3.1              |.....=..............|
```

## unclassified (264)

Multiple publishers with no natural-namespace owner present: a genuine collision the heuristic cannot settle.

- More than one publisher, and none is a credible owner: no natural-namespace owner is present and the earliest is not the closest groupId.
- Left unresolved - no owners.tsv is written - for a later hand decision.

_Showing the 200 most recently active of 264. For the full list, emit the SetOwners file: `-Djenesis.crawler.drift.emit=unclassified`._

```
org.apache.commons.io  [no clear owner; `commons-io` is earliest and most recent]
  ? * commons-io                           2017-10..2021-07 2.11.0               |.=========..........|
  ?   com.jfrog                            2026-06..2026-06 1.2.0                |...................=|
  ?   io.github.liquid-java                2026-06..2026-06 0.0.27               |...................=|
  ?   io.prophecy                          2025-11..2026-06 3.3.0-9.4.0          |..................==|
  ?   no.entur                             2024-03..2026-06 1.122.0              |..............======|
  ?   org.apache.flink                     2024-06..2026-06 2.3.0                |...............=====|
    + 94 more: io.boxlang, org.apache.distributedlog, org.apache.dolphinscheduler, org.sonarsource.python, com.github.cafdataprocessing.workers.languagedetection, org.teavm, org.apache.hudi, org.apache.tika, io.github.zgrge, org.bidib.jbidib, com.ascentstream.distributedlog, com.networknt, (+82 more)
org.apache.commons.codec  [no clear owner; `commons-codec` is earliest and most recent]
  ? * commons-codec                        2017-10..2020-08 1.15                 |.=======............|
  ?   cn.ctyun                             2025-10..2026-06 2.0.1                |..................==|
  ?   software.amazon.awssdk               2024-07..2026-06 2.46.15              |...............=====|
  ?   org.operaton.bpm.extension           2024-12..2026-06 2.2.0-M1             |................====|
  ?   com.mirakl                           2025-11..2026-06 10.9.0               |..................==|
  ?   com.ibm.cloud                        2024-08..2026-06 3.0.0                |...............=====|
    + 77 more: com.republicate.modality, org.boostscale, com.liquibase.ext.vaults, io.gitlab.cupofcode, com.alibaba.ververica, ai.platon.gora, org.apache.tika, com.suprsend, io.github.rsv-code, com.gitee.melin, org.apache.druid.extensions.contrib, org.wso2.orbit.org.opensaml, (+65 more)
org.dnsjava  [no clear owner; `dnsjava` is earliest and most recent]
  ? * dnsjava                              2019-05..2026-05 3.6.5                |.....===============|
  ?   ai.platon.gora                       2026-06..2026-06 1.0.2-slim           |...................=|
  ?   io.jikkou                            2026-05..2026-06 1.0.1                |...................=|
  ?   org.apache.hbase                     2025-02..2026-06 2.6.6-hadoop3        |................====|
  ?   org.apache.beam                      2025-01..2026-05 2.74.0               |................====|
  ?   de.m3y.hadoop.hdfs.hfsa              2025-02..2026-05 1.4.0                |................====|
    + 17 more: org.apache.phoenix, com.hazelcast.jet, com.helger.peppol.mcp, org.apache.atlas, org.apache.paimon, com.clickzetta, com.alibaba.polardbx, org.apache.pinot, io.github.littleproxy, com.foilen, org.apache.kudu, dev.redcoke, (+5 more)
java.xml.bind  [no clear owner; `javax.xml.bind` is earliest and most recent]
  ? * javax.xml.bind                       2017-07..2018-09 2.3.1                |.===................|
  ?   com.yahoo.vespa                      2020-05..2026-06 8.710.53             |.......=============|
  ?   org.apache.flink                     2020-02..2026-06 2.3.0                |......==============|
  ?   org.verapdf.apps                     2019-06..2026-06 1.30.2               |.....===============|
  ?   io.mosip.mock.sdk                    2024-11..2026-06 1.3.1-rc.1           |................====|
  ?   org.apache.tika                      2018-09..2026-05 3.3.1                |...=================|
    + 184 more: de.fraunhofer.iosb.ilt, org.metricshub, org.kendar.protocol, io.mosip.esignet, org.wso2.msf4j.perftest.echo, org.wso2.msf4j, org.wso2.msf4j.samples, org.wso2.msf4j.sample, org.apache.paimon, org.apache.fluss, de.fraunhofer.iosb.ilt.FROST-Server, org.apache.pinot, (+172 more)
bus.starter  [no clear owner; `org.miaixz` is earliest and most recent]
  ? * org.miaixz                           2025-05..2026-06 8.8.0                |.................===|
  ?   io.github.rassafel                   2025-07..2025-07 0.0.1                |.................=..|
org.apache.commons.mail  [no clear owner; `com.github.ppodgorsek.email` is earliest and most recent]
  ? * com.github.ppodgorsek.email          2023-06..2023-06 2.0.0                |.............=......|
  ?   io.prophecy                          2024-08..2026-06 3.5.0-ml-9.4.0       |...............=====|
org.apache.commons.logging  [no clear owner; `org.slf4j` is earliest and most recent]
  ? * org.slf4j                            2017-04..2026-05 2.0.18               |====================|
  ?   org.operaton.bpm.extension           2026-02..2026-06 2.2.0-M1             |..................==|
  ?   org.open-metadata                    2025-11..2026-06 1.12.11              |..................==|
  ?   commons-logging                      2023-11..2026-06 1.4.0                |..............======|
  ?   org.apache.tika                      2022-09..2026-05 3.3.1                |...........=========|
  ?   net.ontopia                          2026-05..2026-05 5.7.0                |...................=|
    + 30 more: org.jboss.pnc.build-agent, com.facebook.presto.hive, com.nordstrom.ui-tools, org.beangle.sas, io.github.linagora.linid.im, io.brunoborges, org.apache.orc, io.pivotal.cfenv, org.operaton.bpm, de.redsix, org.jboss.logging, com.uchicom, (+18 more)
org.apache.commons.beanutils  [no clear owner; `com.guicedee.services` is earliest and most recent]
  ? * com.guicedee.services                2020-06..2022-02 1.2.2.1-jre17        |.......====.........|
  ?   org.wildfly                          2025-06..2026-06 40.0.1.Final         |.................===|
  ?   org.jvnet.jaxb                       2025-09..2026-06 2.0.16               |.................===|
  ?   com.guicedee.modules.services        2026-04..2026-06 2.1.0                |...................=|
  ?   com.github.bld-commons               2026-01..2026-05 3.0.19               |..................==|
  ?   kg.apc                               2025-06..2026-03 1.12                 |.................==.|
    + 2 more: com.github.bordertech.wcomponents, org.onebusaway
java.ws.rs  [no clear owner; `javax.ws.rs` is earliest and most recent]
  ? * javax.ws.rs                          2017-06..2018-08 2.1.1                |.===................|
  ?   org.apache.opennlp                   2020-07..2026-06 1.9.5                |.......=============|
  ?   org.jboss.pnc.build-agent            2021-07..2026-05 1.2.3                |.........===========|
  ?   org.apache.hadoop                    2026-03..2026-03 3.5.0                |..................=.|
  ?   net.oneandone.ioc-unit               2021-09..2025-11 2.0.51               |.........==========.|
  ?   com.scylladb                         2025-06..2025-09 1.2.6                |.................=..|
    + 54 more: org.opencb.opencga, io.streamnative, io.github.willena, com.liferay, com.inteligr8.activiti, cn.langpy, io.github.fernandolopes, com.epam.reportportal, com.github.openstack4j.core, org.moskito, com.mailintegrate, dev.parodos, (+42 more)
VirtualizedFX  [no clear owner; `io.github.palexdev` is earliest and most recent]
  ? * io.github.palexdev                   2022-03..2026-06 25.2.1               |..........==========|
  ?   org.glavo.materialfx                 2022-04..2022-04 11.2.6               |..........=.........|
jakarta.security.auth.message  [no clear owner; `jakarta.authentication` is earliest and most recent]
  ? * jakarta.authentication               2020-11..2024-05 3.1.0                |........========....|
  ?   org.apache.tomcat                    2020-11..2026-06 11.0.23              |........============|
org.apache.commons.csv  [no clear owner; `io.github.pustike` is earliest and most recent]
  ? * io.github.pustike                    2019-01..2019-07 1.7.0                |....==..............|
  ?   org.sonarsource.scanner.engine       2026-05..2026-06 13.0.0.3653          |...................=|
  ?   com.guicedee.modules.services        2026-04..2026-06 2.1.0                |...................=|
  ?   com.orientechnologies                2026-03..2026-06 3.2.53               |..................==|
  ?   org.apache.pinot                     2024-08..2026-04 1.5.0                |...............=====|
  ?   org.testingisdocumenting.znai        2026-01..2026-03 1.86                 |..................=.|
    + 9 more: be.ugent.idlab.knows, io.kestra.plugin, xyz.ottr.lutra, org.jetbrains.kotlinx, com.wizzdi, io.telicent.jena.graphql, io.telicent.jena, org.apache.jena, com.guicedee.services
org.apache.commons.beanutils2  [no clear owner; `org.onebusaway` is earliest and most recent]
  ? * org.onebusaway                       2025-05..2026-06 14.0.0               |.................===|
  ?   com.github.bordertech.wcomponents    2025-12..2026-01 1.5.39               |..................=.|
org.bukkit  [no clear owner; `com.uroria.curepur` is earliest and most recent]
  ? * com.uroria.curepur                   2024-07..2024-07 1.21-R0.1            |...............=....|
  ?   com.620cloud.server                  2026-06..2026-06 1.21.11-R0.1-362     |...................=|
  ?   com.mineplex.studio.server           2024-10..2026-04 26.1.2-357           |................====|
  ?   com.uroria.latest                    2024-07..2024-07 1.21-R0.1-2d776710d6 |...............=....|
  ?   com.uroria                           2024-07..2024-07 1.21-R0.1            |...............=....|
library  [no clear owner; `build.buf.prototype` is earliest and most recent]
  ? * build.buf.prototype                  2023-01..2023-01 v0.0.0-test0120      |............=.......|
  ?   com.connectrpc                       2023-09..2026-06 0.9.0                |.............=======|
  ?   build.buf                            2023-01..2023-09 0.1.10               |............==......|
okhttp  [no clear owner; `build.buf` is earliest and most recent]
  ? * build.buf                            2023-02..2023-09 0.1.10               |............==......|
  ?   com.connectrpc                       2023-09..2026-06 0.9.0                |.............=======|
com.ctc.wstx  [no clear owner; `com.fasterxml.woodstox` is earliest and most recent]
  ? * com.fasterxml.woodstox               2018-03..2026-06 7.2.1                |..==================|
  ?   org.uma.jmetal                       2025-12..2026-06 7.4                  |..................==|
  ?   org.bidib.jbidib                     2021-12..2026-05 2.0.44               |..........==========|
  ?   gov.nih.ncats                        2022-01..2026-03 1.0.26               |..........=========.|
  ?   org.hpccsystems                      2022-02..2026-03 9.12.94-1            |..........=========.|
  ?   com.backpackcloud                    2025-03..2026-01 2.1.0                |................===.|
    + 17 more: com.liferay.portal, de.fraunhofer.iosb.ilt.FROST-Server, com.ibm.jsonata4java, se.signatureservice.support, com.liferay, net.pincette, org.opengis.cite, org.immregistries, com.testdroid, org.sonarsource.slang, com.checkmarx, com.github.spoonlabs, (+5 more)
org.apache.commons.dbcp2  [no clear owner; `org.apache.tomee` is earliest and most recent]
  ? * org.apache.tomee                     2023-12..2026-06 11.0.0-M1            |..............======|
  ?   org.apache.meecrowave                2025-10..2025-10 2.0.0                |..................=.|
  ?   net.ontopia                          2025-04..2025-07 5.5.2                |.................=..|
  ?   org.apache.openjpa                   2024-09..2025-05 4.1.1                |...............===..|
org.scala.lang.scala3.compiler  [no clear owner; `org.scala-lang` is earliest and most recent]
  ? * org.scala-lang                       2021-06..2026-06 3.3.8                |.........===========|
  ?   com.michaelpollmeier                 2022-10..2022-11 3.2.2-RC1-bin-20221101-d84007c-NIGHTLY+1-extensible-repl |............=.......|
uk.co.spudsoft.birt.emitters.excel  [no clear owner; `io.github.reporting-solutions` is earliest and most recent]
  ? * io.github.reporting-solutions        2019-05..2026-02 4.23.0               |.....==============.|
  ?   org.eclipse.birt                     2022-05..2026-06 4.24.0               |...........=========|
org.apache.commons.validator  [no clear owner; `org.chronos-eaas` is earliest and most recent]
  ? * org.chronos-eaas                     2024-07..2025-01 2.5.1                |...............==...|
  ?   org.apiaddicts.apitools.dosonarapi   2026-05..2026-06 2.0.1                |...................=|
flying.saucer.pdf  [no clear owner; `org.xhtmlrenderer` is earliest and most recent]
  ? * org.xhtmlrenderer                    2024-09..2026-06 10.3.0               |...............=====|
  ?   io.github.openpdfsaucer              2025-03..2025-05 2.0.9                |................==..|
org.newsclub.net.unix  [no clear owner; `com.kohlschutter.junixsocket` is earliest and most recent]
  ? * com.kohlschutter.junixsocket         2018-12..2024-09 2.10.1               |....============....|
  ?   org.jam4s                            2025-10..2026-06 0.7.2-M1             |..................==|
  ?   com.sbbsystems.flink                 2026-01..2026-05 3.4.3                |..................==|
  ?   net.corda                            2025-09..2026-05 4.14.2               |.................===|
  ?   io.nosqlbench                        2020-02..2020-03 3.12.47              |......=.............|
  ?   io.engineblock                       2020-01..2020-01 2.12.65              |......=.............|
org.jfree.chart  [no clear owner; `de.enflexit` is earliest and most recent]
  ? * de.enflexit                          2025-02..2025-02 1.5.6                |................=...|
  ?   io.github.jiaweim                    2026-05..2026-06 2.7.0                |...................=|
com.kingbase8.jdbc  [no clear owner; `org.jeecgframework` is earliest and most recent]
  ? * org.jeecgframework                   2024-06..2024-06 9.0.0                |...............=....|
  ?   cn.com.kingbase                      2025-04..2026-06 9.0.2.jre7           |.................===|
  ?   io.github.iscasdmo                   2026-05..2026-05 8.6.0                |...................=|
jdk.internal.vm.compiler  [no clear owner; `org.graalvm.compiler` is earliest and most recent]
  ? * org.graalvm.compiler                 2018-10..2026-04 23.0.12              |...=================|
  ?   io.vertx                             2022-11..2026-06 4.5.28               |............========|
  ?   org.linuxforhealth.fhir              2022-08..2022-12 5.1.1                |...........==.......|
localhost3000  [no clear owner; `io.github.zahran444` is earliest and most recent]
  ? * io.github.zahran444                  2026-04..2026-05 4.0.0                |...................=|
  ?   io.sdks                              2026-06..2026-06 0.0.4                |...................=|
cache.annotations.ri.common  [no clear owner; `com.jwebmp.thirdparty.jcache` is earliest and most recent]
  ? * com.jwebmp.thirdparty.jcache         2019-05..2019-08 0.68.0.1             |.....=..............|
  ?   com.guicedee.modules.services        2026-04..2026-06 2.1.0                |...................=|
  ?   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17        |......=====.........|
cache.api  [no clear owner; `com.jwebmp.thirdparty.jcache` is earliest and most recent]
  ? * com.jwebmp.thirdparty.jcache         2019-05..2019-08 0.68.0.1             |.....=..............|
  ?   com.guicedee.modules.services        2026-04..2026-06 2.1.0                |...................=|
  ?   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17        |......=====.........|
cache.annotations.ri.guice  [no clear owner; `com.jwebmp.thirdparty.jcache` is earliest and most recent]
  ? * com.jwebmp.thirdparty.jcache         2019-05..2019-08 0.68.0.1             |.....=..............|
  ?   com.guicedee.modules.services        2026-04..2026-06 2.1.0                |...................=|
  ?   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17        |......=====.........|
net.sf.uadetector.core  [no clear owner; `com.jwebmp.jre11` is earliest and most recent]
  ? * com.jwebmp.jre11                     2018-11..2018-12 0.63.0.19            |....=...............|
  ?   com.guicedee.modules.services        2026-04..2026-06 2.1.0                |...................=|
  ?   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17        |......=====.........|
  ?   com.jwebmp.thirdparty                2019-04..2019-08 0.68.0.1             |....==..............|
  ?   com.jwebmp                           2019-01..2019-04 0.66.0.1             |....=...............|
net.sf.uadetector.resources  [no clear owner; `com.jwebmp.jre11` is earliest and most recent]
  ? * com.jwebmp.jre11                     2018-11..2018-12 0.63.0.19            |....=...............|
  ?   com.guicedee.modules.services        2026-04..2026-06 2.1.0                |...................=|
  ?   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17        |......=====.........|
  ?   com.jwebmp.thirdparty                2019-04..2019-08 0.68.0.1             |....==..............|
  ?   com.jwebmp                           2019-01..2019-04 0.66.0.1             |....=...............|
org.apache.commons.fileupload  [no clear owner; `com.jwebmp.jre11` is earliest and most recent]
  ? * com.jwebmp.jre11                     2018-12..2018-12 0.63.0.19            |....=...............|
  ?   com.guicedee.modules.services        2026-04..2026-06 2.1.0                |...................=|
  ?   org.wiremock                         2025-06..2026-04 4.0.0-beta.32        |.................===|
  ?   org.openidentityplatform.openam.agents 2025-11..2026-03 5.0.3                |..................=.|
  ?   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17        |......=====.........|
  ?   com.jwebmp.jpms.commons              2019-04..2019-08 0.68.0.1             |....==..............|
    + 1 more: com.jwebmp
aopalliance  [no clear owner; `com.jwebmp.jre11` is earliest and most recent]
  ? * com.jwebmp.jre11                     2018-12..2018-12 0.63.0.19            |....=...............|
  ?   com.guicedee.modules.services        2026-04..2026-06 2.1.0                |...................=|
  ?   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17        |......=====.........|
  ?   com.jwebmp.thirdparty                2019-04..2019-08 0.68.0.1             |....==..............|
  ?   com.jwebmp                           2019-01..2019-04 0.66.0.1             |....=...............|
com.oracle.truffle.tools.profiler  [no clear owner; `org.graalvm.tools` is earliest and most recent]
  ? * org.graalvm.tools                    2018-10..2026-04 25.0.3               |...=================|
  ?   com.orientechnologies                2025-12..2026-06 3.2.53               |..................==|
info.movito.themoviedbapi  [no clear owner; `com.github.holgerbrandl` is earliest and most recent]
  ? * com.github.holgerbrandl              2023-05..2023-05 1.15                 |.............=......|
  ?   uk.co.conoregan                      2023-11..2026-05 2.6.1                |..............======|
vault.java.driver  [no clear owner; `com.bettercloud` is earliest and most recent]
  ? * com.bettercloud                      2019-06..2019-12 5.1.0                |.....==.............|
  ?   io.github.jopenlibs                  2022-10..2026-05 6.2.2                |............========|
  ?   io.axual.utilities.config.providers  2020-06..2024-11 1.2.0                |.......==========...|
  ?   edu.utexas.tacc.tapis                2021-10..2021-10 5.1.2                |..........=.........|
org.apache.commons.cli  [no clear owner; `org.apache.shiro.tools` is earliest and most recent]
  ? * org.apache.shiro.tools               2023-10..2023-10 1.13.0               |..............=.....|
  ?   org.teavm                            2024-04..2026-05 0.14.1               |...............=====|
  ?   io.github.vdaburon                   2024-01..2026-05 5.1                  |..............======|
  ?   org.apktool                          2023-12..2026-04 3.0.2                |..............======|
  ?   com.ericsson.bss.cassandra.ecaudit   2024-08..2026-03 3.1.5                |...............====.|
  ?   org.imixs.bpmn                       2025-05..2026-03 1.2.9                |.................==.|
    + 23 more: io.github.gvergine, com.amazonaws, io.github.706412584, com.legsem.legstar, dev.walgo, org.apache.phoenix.thirdparty, org.apache.meecrowave, org.apache.james, net.thisptr, us.poliscore, com.github.oboehm, org.apache.hbase, (+11 more)
java.json  [no clear owner; `javax.json` is earliest and most recent]
  ? * javax.json                           2017-01..2018-11 1.1.4                |=====...............|
  ?   org.choco-solver                     2019-07..2026-05 6.0.1                |.....===============|
  ?   com.amihaiemil.web                   2021-02..2024-08 8.0.6                |........========....|
  ?   com.scalar-labs                      2019-02..2024-03 2.2.0                |....===========.....|
  ?   org.apache.sling                     2022-10..2023-11 1.1.8                |............===.....|
  ?   com.atomgraph.etl.json               2022-11..2023-08 1.0.7                |............==......|
    + 11 more: org.openpreservation.jhove, com.artipie, com.onespan.integration, org.odftoolkit, org.finra.herd, com.bitplan.wikifrontend, net.pincette, org.glassfish, com.phenixrts.edgeauth, jakarta.json, de.julielab
netty.socketio.core  [no clear owner; `io.github.neatguycoding` is earliest and most recent]
  ? * io.github.neatguycoding              2025-10..2025-11 3.0.1                |..................=.|
  ?   com.socketio4j                       2025-11..2026-05 4.0.1                |..................==|
netty.socketio.spring  [no clear owner; `io.github.neatguycoding` is earliest and most recent]
  ? * io.github.neatguycoding              2025-10..2025-11 3.0.1                |..................=.|
  ?   com.socketio4j                       2025-11..2026-05 4.0.1                |..................==|
io.github.bucket4j.caffeine  [no clear owner; `com.github.vladimir-bukhtoyarov` is earliest and most recent]
  ? * com.github.vladimir-bukhtoyarov      2022-03..2024-04 8.0.1                |..........======....|
  ?   com.bucket4j                         2022-07..2026-05 8.19.0               |...........=========|
io.github.bucket4j.coherence  [no clear owner; `com.github.vladimir-bukhtoyarov` is earliest and most recent]
  ? * com.github.vladimir-bukhtoyarov      2019-11..2024-04 8.0.1                |......==========....|
  ?   com.bucket4j                         2022-07..2026-05 8.19.0               |...........=========|
io.github.bucket4j.core  [no clear owner; `com.github.vladimir-bukhtoyarov` is earliest and most recent]
  ? * com.github.vladimir-bukhtoyarov      2019-11..2024-04 8.0.1                |......==========....|
  ?   com.bucket4j                         2022-07..2026-05 8.19.0               |...........=========|
io.github.bucket4j.hazelcast  [no clear owner; `com.github.vladimir-bukhtoyarov` is earliest and most recent]
  ? * com.github.vladimir-bukhtoyarov      2019-11..2024-04 8.0.1                |......==========....|
  ?   com.bucket4j                         2022-07..2026-05 8.19.0               |...........=========|
io.github.bucket4j.ignite  [no clear owner; `com.github.vladimir-bukhtoyarov` is earliest and most recent]
  ? * com.github.vladimir-bukhtoyarov      2019-11..2024-04 8.0.1                |......==========....|
  ?   com.bucket4j                         2022-07..2026-05 8.19.0               |...........=========|
io.github.bucket4j.infinispan  [no clear owner; `com.github.vladimir-bukhtoyarov` is earliest and most recent]
  ? * com.github.vladimir-bukhtoyarov      2019-11..2024-04 8.0.1                |......==========....|
  ?   com.bucket4j                         2022-07..2026-05 8.19.0               |...........=========|
io.github.bucket4j.jcache  [no clear owner; `com.github.vladimir-bukhtoyarov` is earliest and most recent]
  ? * com.github.vladimir-bukhtoyarov      2019-11..2024-04 8.0.1                |......==========....|
  ?   com.bucket4j                         2022-07..2026-05 8.19.0               |...........=========|
io.github.bucket4j.mysql  [no clear owner; `com.github.vladimir-bukhtoyarov` is earliest and most recent]
  ? * com.github.vladimir-bukhtoyarov      2022-03..2024-04 8.0.1                |..........======....|
  ?   com.bucket4j                         2022-07..2026-05 8.19.0               |...........=========|
io.github.bucket4j.postgresql  [no clear owner; `com.github.vladimir-bukhtoyarov` is earliest and most recent]
  ? * com.github.vladimir-bukhtoyarov      2022-03..2024-04 8.0.1                |..........======....|
  ?   com.bucket4j                         2022-07..2026-05 8.19.0               |...........=========|
lombok  [no clear owner; `org.projectlombok` is earliest and most recent]
  ? * org.projectlombok                    2018-05..2026-04 1.18.46              |...=================|
  ?   io.mosip.esignet.plugin.sunbirdrc    2025-02..2026-05 1.4.0                |................====|
  ?   org.eclipse.hawkbit                  2026-03..2026-04 1.0.3                |..................==|
  ?   io.inji.certify.sunbirdrc            2026-03..2026-03 0.6.0                |..................=.|
  ?   dev.alllexey                         2025-10..2026-03 1.5.0                |..................=.|
  ?   net.polyv                            2020-09..2026-03 2.2.8                |.......============.|
    + 97 more: com.scanoss, com.huaweicloud.dws, net.wirelabs, cn.fyupeng, io.github.alllexey123, io.mosip.esignet.sunbirdrc, io.mosip.certify.sunbirdrc, io.github.opentelekomcloud, io.github.version-pulse, org.qubership.automation, io.github.devlibx.easy, org.sentrysoftware, (+85 more)
ihub.core  [no clear owner; `pub.ihub.lib` is earliest and most recent]
  ? * pub.ihub.lib                         2021-09..2026-05 1.7.7                |.........===========|
  ?   pub.ihub.integration                 2024-03..2025-05 0.1.12               |..............====..|
  ?   pub.ihub.module                      2024-04..2025-05 0.2.2                |..............====..|
liqp  [no clear owner; `nl.big-o` is earliest and most recent]
  ? * nl.big-o                             2018-06..2025-04 0.9.2.3              |...==============...|
  ?   io.github.luoxuansz                  2025-11..2026-05 1.1.3                |..................==|
  ?   com.kohlschutter                     2023-12..2023-12 0.8.5.4              |..............=.....|
jakarta.messaging  [no clear owner; `jakarta.jms` is earliest and most recent]
  ? * jakarta.jms                          2022-03..2022-03 3.1.0                |..........=.........|
  ?   org.apache.storm                     2025-05..2026-05 2.8.8                |.................===|
  ?   be.vlaanderen.informatievlaanderen.ldes.ldio 2024-12..2024-12 2.12.0               |................=...|
java.json.bind  [no clear owner; `javax.json.bind` is earliest and most recent]
  ? * javax.json.bind                      2017-04..2017-06 1.0                  |==..................|
  ?   org.open-metadata                    2023-08..2026-05 1.13.0-rc1           |.............=======|
  ?   org.jboss.pnc.build-agent            2024-06..2026-03 1.1.9                |...............====.|
  ?   be.valuya.cestzam                    2021-09..2023-01 2023.1.1             |.........====.......|
  ?   com.manywho.sdk                      2020-02..2020-05 2.0.1                |......==............|
  ?   jakarta.json.bind                    2019-01..2019-08 1.0.2                |....==..............|
    + 5 more: io.zeleo.application, org.keycloak, com.github.robozonky.distribution, com.github.robozonky, net.redpipe
jcef  [no clear owner; `me.friwi` is earliest and most recent]
  ? * me.friwi                             2021-12..2026-05 jcef-d3de827+cef-146.0.10+g8219561+chromium-146.0.7680.179 |..........==========|
  ?   io.github.trethore                   2026-02..2026-04 jcef-65f9d7b+cef-146.0.10+g8219561+chromium-146.0.7680.179 |..................==|
jam.common  [no clear owner; `sk.annotation.library.jam` is earliest and most recent]
  ? * sk.annotation.library.jam            2022-01..2026-05 0.9.21               |..........==========|
  ?   sk.annotation.projects.signito       2022-12..2022-12 0.9.53               |............=.......|
java.security.auth.message  [no clear owner; `jakarta.security.auth.message` is earliest and most recent]
  ? * jakarta.security.auth.message        2018-12..2020-02 2.0.0-RC1            |....===.............|
  ?   org.apache.tomcat                    2020-11..2026-05 9.0.118              |........============|
  ?   org.jboss.spec.javax.security.auth.message 2019-08..2019-09 2.0.1.Final          |.....=..............|
java.servlet  [no clear owner; `jakarta.servlet` is earliest and most recent]
  ? * jakarta.servlet                      2019-08..2020-07 5.0.0-M2             |.....===............|
  ?   org.apache.tomcat                    2020-11..2026-05 9.0.118              |........============|
  ?   org.apache.felix                     2022-02..2022-10 2.1.0                |..........===.......|
  ?   com.guicedee.services                2020-05..2022-02 1.2.2.1-jre17        |.......====.........|
  ?   org.jboss.spec.javax.servlet         2019-08..2019-09 2.0.0.Final          |.....=..............|
java.servlet.jsp  [no clear owner; `org.apache.tomcat` is earliest and most recent]
  ? * org.apache.tomcat                    2020-11..2026-05 9.0.118              |........============|
  ?   com.heroku                           2024-05..2026-04 9.0.117.0            |...............=====|
java.el  [no clear owner; `org.apache.tomcat` is earliest and most recent]
  ? * org.apache.tomcat                    2020-11..2026-05 9.0.118              |........============|
  ?   com.heroku                           2024-10..2024-10 9.0.96.0             |................=...|
java.annotation  [no clear owner; `javax.annotation` is earliest and most recent]
  ? * javax.annotation                     2017-09..2018-02 1.3.2                |.==.................|
  ?   org.apache.tomcat                    2020-09..2026-05 9.0.118              |.......=============|
  ?   com.heroku                           2024-10..2026-04 9.0.117.0            |................====|
  ?   one.gfw                              2023-03..2023-03 1.3.5                |............=.......|
  ?   org.rationalityfrontline.workaround  2021-02..2021-02 1.3.2-3.0.2          |........=...........|
  ?   com.guicedee.services                2019-11..2020-11 1.1.0.1-jre15        |......===...........|
    + 3 more: no.ssb.jpms, org.jboss.spec.javax.annotation, jakarta.annotation
json.path  [no clear owner; `com.jayway.jsonpath` is earliest and most recent]
  ? * com.jayway.jsonpath                  2024-01..2026-02 3.0.0                |..............=====.|
  ?   org.gov4j.thirdparty.com.jayway.jsonpath 2024-12..2026-04 3.0.0-gov4j-1        |................====|
  ?   com.github.sonus21                   2025-04..2025-04 2.10.0               |.................=..|
mslinks  [no clear owner; `com.github.vatbub` is earliest and most recent]
  ? * com.github.vatbub                    2020-09..2021-07 1.0.6.2              |.......===..........|
  ?   org.jabref                           2026-02..2026-04 1.2                  |..................==|
com.oracle.truffle.regex  [no clear owner; `org.graalvm.regex` is earliest and most recent]
  ? * org.graalvm.regex                    2018-10..2026-04 25.0.3               |...=================|
  ?   org.noear                            2024-09..2025-07 1.9.6                |...............===..|
  ?   com.syncloop.middleware              2025-01..2025-01 1.7.1                |................=...|
graphql.java.tools  [no clear owner; `com.graphql-java-kickstart` is earliest and most recent]
  ? * com.graphql-java-kickstart           2023-08..2025-04 14.0.1               |.............=====..|
  ?   io.github.graphql-java-kickstart     2026-03..2026-04 14.0.2               |..................==|
com.jn.langx.java8  [no clear owner; `io.github.bes2008.solution.langx` is earliest and most recent]
  ? * io.github.bes2008.solution.langx     2024-01..2026-04 5.4.6.1              |..............======|
  ?   io.github.qhsword.langx              2025-11..2025-11 5.5.10               |..................=.|
com.jn.langx.security.gm.jca.bouncycastle  [no clear owner; `io.github.bes2008.solution.langx.security` is earliest and most recent]
  ? * io.github.bes2008.solution.langx.security 2024-01..2026-04 5.4.6.1              |..............======|
  ?   io.github.qhsword.langx.security     2025-11..2025-12 5.8.0                |..................=.|
org.java_websocket  [no clear owner; `org.java-websocket` is earliest and most recent]
  ? * org.java-websocket                   2023-07..2024-12 1.6.0                |.............====...|
  ?   io.github.cb-jarunmadhesh            2026-04..2026-04 1.0.0                |...................=|
  ?   io.github.ashwithpoojary98           2026-01..2026-01 1.0.1                |..................=.|
  ?   dev.lolyay                           2025-07..2025-10 5.8.0                |.................==.|
  ?   io.kestra.plugin                     2023-09..2025-08 0.24.0               |.............=====..|
  ?   org.jetbrains.kotlinx                2025-07..2025-07 0.14.1-506           |.................=..|
    + 3 more: io.github.gubaojian, com.taosdata.jdbc, com.enixyu
tech.fortis.sandbox.api  [no clear owner; `io.github.zahran444` is earliest and most recent]
  ? * io.github.zahran444                  2026-04..2026-04 1.0.0                |...................=|
  ?   io.sdks                              2026-04..2026-04 1.0.5                |...................=|
com.mypayquicker.api  [no clear owner; `io.sdks` is earliest and most recent]
  ? * io.sdks                              2026-02..2026-02 1.0.3                |..................=.|
  ?   io.github.zahran444                  2026-04..2026-04 1.0.0                |...................=|
java.xml.ws  [no clear owner; `javax.xml.ws` is earliest and most recent]
  ? * javax.xml.ws                         2017-06..2018-09 2.3.1                |.===................|
  ?   org.apache.manifoldcf                2026-04..2026-04 2.30                 |...................=|
  ?   mx.com.sw.services                   2020-07..2024-05 1.0.19.4             |.......=========....|
  ?   org.apache.servicemix.specs          2018-11..2020-03 2.3_3                |....===.............|
  ?   com.github.pinterest                 2018-11..2020-01 0.3.0-rc.2           |....===.............|
  ?   jakarta.xml.ws                       2018-12..2020-01 2.3.3                |....===.............|
net.sf.jsqlparser  [no clear owner; `com.github.jsqlparser` is earliest and most recent]
  ? * com.github.jsqlparser                2024-03..2025-05 5.3                  |..............====..|
  ?   com.manticore-projects.jsqlformatter 2025-12..2026-04 5.3.218              |..................==|
  ?   se.alipsa                            2025-12..2025-12 1.2.0                |..................=.|
  ?   ai.starlake                          2024-09..2024-10 1.3.0                |...............==...|
com.drew.metadata  [no clear owner; `com.github.dalet-oss` is earliest and most recent]
  ? * com.github.dalet-oss                 2024-03..2024-03 0.0.6                |..............=.....|
  ?   com.drewnoakes                       2026-04..2026-04 2.20.0               |...................=|
commons  [no clear owner; `com.github.srujankujmar` is earliest and most recent]
  ? * com.github.srujankujmar              2020-12..2020-12 0.9.8.1              |........=...........|
  ?   io.github.rassafel                   2025-07..2026-04 0.0.4                |.................===|
  ?   io.hyscale                           2020-12..2021-08 1.0.0                |........==..........|
com.example.www  [no clear owner; `io.github.zahran444` is earliest and most recent]
  ? * io.github.zahran444                  2025-06..2026-03 3.0.7                |.................==.|
  ?   io.sdks                              2026-03..2026-03 0.0.1                |..................=.|
ij  [no clear owner; `net.imagej` is earliest and most recent]
  ? * net.imagej                           2019-03..2025-02 1.54p                |....=============...|
  ?   org.tango-controls.atk               2022-06..2026-03 9.4.20               |...........========.|
  ?   org.tango-controls                   2022-10..2025-10 7.46                 |...........=======..|
druid.spring.boot4.starter  [no clear owner; `com.shr25` is earliest and most recent]
  ? * com.shr25                            2026-02..2026-02 1.2.27               |..................=.|
  ?   com.alibaba                          2026-03..2026-03 1.2.28               |..................=.|
org.freedesktop.dbus  [no clear owner; `com.github.hypfvieh` is earliest and most recent]
  ? * com.github.hypfvieh                  2021-03..2025-12 5.2.0                |........===========.|
  ?   org.endlesssource.mediainterface     2026-02..2026-03 0.2.3                |..................=.|
tornadofx  [no clear owner; `it.unibo.alchemist` is earliest and most recent]
  ? * it.unibo.alchemist                   2020-11..2020-12 9.3.0-dev218+bb50ca6a3 |........=...........|
  ?   com.googlecode.blaisemath.tornado    2023-09..2026-03 2.2.2                |.............======.|
core  [no clear owner; `pro.shuangxi.framework.openfx` is earliest and most recent]
  ? * pro.shuangxi.framework.openfx        2025-05..2025-05 1.0.0                |.................=..|
  ?   org.apereo.cas                       2025-07..2026-03 7.2.7.1              |.................==.|
dss_pki_factory_jaxb  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2023-11..2026-03 6.4                  |..............=====.|
  ?   org.digidoc4j.dss                    2024-02..2025-11 6.2.d4j.1            |..............=====.|
jpms_dss_asic_common  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_crl_parser_stream  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_diagnostic_data  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_document  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_evidence_record_asn1  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2024-07..2026-03 6.4                  |...............====.|
  ?   org.digidoc4j.dss                    2025-11..2025-11 6.2.d4j.1            |..................=.|
jpms_dss_jacoco_coverage  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2023-02..2026-03 6.4                  |............=======.|
  ?   org.digidoc4j.dss                    2024-02..2025-11 6.2.d4j.1            |..............=====.|
jpms_dss_jades  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2021-10..2025-11 6.2.d4j.1            |..........=========.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_jaxb_parsers  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_model  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_policy  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_service  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_specs_jws  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2021-10..2025-11 6.2.d4j.1            |..........=========.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_specs_xades  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_specs_xmldsig  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_specs_xmlers  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2023-11..2026-03 6.4                  |..............=====.|
  ?   org.digidoc4j.dss                    2024-02..2025-11 6.2.d4j.1            |..............=====.|
jpms_dss_test  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_ws_certificate_validation_dto  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_ws_validation_soap  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_asic_xades  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_common_converter  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_cookbook  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_crl_parser  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_detailed_report  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_pki_factory  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2023-11..2026-03 6.4                  |..............=====.|
  ?   org.digidoc4j.dss                    2024-02..2025-11 6.2.d4j.1            |..............=====.|
jpms_dss_specs_validation_report  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_utils_google_guava  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_ws_certificate_validation_rest  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_ws_server_signing_common  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_ws_server_signing_soap_client  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_ws_signature_dto  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_ws_signature_remote  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_ws_timestamp_dto  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2020-11..2025-11 6.2.d4j.1            |........===========.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_ws_timestamp_remote_rest_client  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2020-11..2025-11 6.2.d4j.1            |........===========.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_ws_timestamp_remote_soap_client  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2020-11..2025-11 6.2.d4j.1            |........===========.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_ws_validation_dto  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_ws_validation_rest  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_xades  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_alert  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2020-11..2025-11 6.2.d4j.1            |........===========.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_asic_cades  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_cades  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_common_remote_dto  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_enumerations  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_evidence_record_common  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2023-11..2026-03 6.4                  |..............=====.|
  ?   org.digidoc4j.dss                    2024-02..2025-11 6.2.d4j.1            |..............=====.|
jpms_dss_evidence_record_xml  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2023-11..2026-03 6.4                  |..............=====.|
  ?   org.digidoc4j.dss                    2024-02..2025-11 6.2.d4j.1            |..............=====.|
jpms_dss_specs_asic_manifest  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2021-10..2025-11 6.2.d4j.1            |..........=========.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_tsl_validation  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_ws_certificate_validation_common  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_ws_certificate_validation_rest_client  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_ws_certificate_validation_soap_client  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_ws_server_signing_dto  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_ws_server_signing_rest  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_ws_server_signing_rest_client  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_ws_server_signing_soap  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_ws_signature_rest_client  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_ws_signature_soap  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_ws_validation_rest_client  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_xml  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2023-11..2026-03 6.4                  |..............=====.|
  ?   org.digidoc4j.dss                    2024-02..2025-11 6.2.d4j.1            |..............=====.|
jpms_dss_xml_common  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2023-11..2026-03 6.4                  |..............=====.|
  ?   org.digidoc4j.dss                    2024-02..2025-11 6.2.d4j.1            |..............=====.|
jpms_dss_crl_parser_x509crl  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_i18n  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2020-11..2025-11 6.2.d4j.1            |........===========.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_jaxb_common  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2022-05..2025-11 6.2.d4j.1            |...........========.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_pades  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_pades_openpdf  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_pades_pdfbox  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_pdfa  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2023-02..2026-03 6.4                  |............=======.|
  ?   org.digidoc4j.dss                    2024-02..2025-11 6.2.d4j.1            |..............=====.|
jpms_dss_simple_certificate_report  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_simple_report  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_specs_jades  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2021-10..2025-11 6.2.d4j.1            |..........=========.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_specs_saml_assertion  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2020-11..2025-11 6.2.d4j.1            |........===========.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_specs_trusted_list  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_spi  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_token  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_utils  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_utils_apache_commons  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_validation  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2024-07..2026-03 6.4                  |...............====.|
  ?   org.digidoc4j.dss                    2025-11..2025-11 6.2.d4j.1            |..................=.|
jpms_dss_ws_certificate_validation_soap  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_ws_signature_rest  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_ws_signature_soap_client  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_ws_timestamp_remote  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2020-11..2025-11 6.2.d4j.1            |........===========.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_ws_timestamp_remote_rest  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2020-11..2025-11 6.2.d4j.1            |........===========.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_ws_timestamp_remote_soap  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2020-11..2025-11 6.2.d4j.1            |........===========.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_ws_validation_common  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
jpms_dss_ws_validation_soap_client  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-03 6.4                  |............=======.|
com.utool  [no clear owner; `io.gitee.shallwecode` is earliest and most recent]
  ? * io.gitee.shallwecode                 2025-05..2026-03 1.5.0                |.................==.|
  ?   io.github.shallwecodex               2025-05..2025-05 1.0.2                |.................=..|
org.freedesktop.harfbuzz  [no clear owner; `io.github.jwharm.javagi` is earliest and most recent]
  ? * io.github.jwharm.javagi              2023-09..2025-05 0.12.2               |.............=====..|
  ?   org.java-gi                          2025-11..2026-02 0.14.1               |..................=.|
com.jcraft.jsch  [no clear owner; `com.github.mwiede` is earliest and most recent]
  ? * com.github.mwiede                    2021-08..2025-06 2.27.2               |.........=========..|
  ?   com.pesitwizard.connector            2026-02..2026-02 1.2.1                |..................=.|
  ?   com.opendatadsl                      2025-08..2026-01 1.1.29               |.................==.|
  ?   com.testingbot                       2025-08..2025-08 4.3                  |.................=..|
  ?   io.kestra.plugin                     2024-04..2025-03 0.20.1               |...............==...|
  ?   com.jcabi                            2022-09..2022-09 1.9.0                |...........=........|
jcifs  [no clear owner; `org.codelibs` is earliest and most recent]
  ? * org.codelibs                         2022-04..2026-02 3.0.2                |...........========.|
  ?   io.gitee.pickled_vegetables          2023-05..2023-05 2.2.0                |.............=......|
netty.socketio  [no clear owner; `com.corundumstudio.socketio` is earliest and most recent]
  ? * com.corundumstudio.socketio          2024-01..2026-02 2.0.14               |..............=====.|
  ?   codes.oss.socketio                   2025-04..2025-04 2.0.14               |.................=..|
  ?   io.github.opensabe-tech              2024-08..2024-08 2.0.12               |...............=....|
com.google.api.client  [no clear owner; `com.google.http-client` is earliest and most recent]
  ? * com.google.http-client               2018-10..2026-01 2.1.0                |...================.|
  ?   com.google.cloud.bigtable            2020-07..2020-11 1.17.0               |.......==...........|
jetty.servlet.api  [no clear owner; `org.eclipse.jetty.toolchain` is earliest and most recent]
  ? * org.eclipse.jetty.toolchain          2019-02..2026-01 4.0.9                |....===============.|
  ?   ch.reportingsoft.birt                2025-04..2025-04 4.0.6                |.................=..|
  ?   io.prometheus.cloudwatch             2024-08..2024-08 0.16.0               |...............=....|
  ?   org.cip4.tools.jdfutility            2022-01..2022-01 1.7.1                |..........=.........|
java.xml.soap  [no clear owner; `javax.xml.soap` is earliest and most recent]
  ? * javax.xml.soap                       2017-06..2017-06 1.4.0                |.=..................|
  ?   mx.com.sw                            2026-01..2026-01 0.0.19.1             |..................=.|
  ?   org.hpccsystems                      2022-02..2025-10 9.8.126-1            |..........=========.|
  ?   org.apache.servicemix.specs          2019-12..2020-03 1.4_2                |......=.............|
  ?   jakarta.xml.soap                     2018-12..2020-01 1.4.2                |....===.............|
  ?   org.jboss.spec.javax.xml.soap        2019-09..2020-01 1.0.2.Final          |.....==.............|
com.sun.activation.registries  [no clear owner; `org.eclipse.angus` is earliest and most recent]
  ? * org.eclipse.angus                    2021-08..2022-12 1.1.0                |.........====.......|
  ?   fish.payara.extras                   2022-07..2025-11 6.2025.11            |...........========.|
simpletimeapi  [no clear owner; `io.github.fontysvenlo` is earliest and most recent]
  ? * io.github.fontysvenlo                2023-08..2025-10 1.0.0                |.............======.|
  ?   io.github.fontysvenlo.alda           2023-09..2023-09 2.5                  |.............=......|
aerogel  [no clear owner; `io.github.derklaro` is earliest and most recent]
  ? * io.github.derklaro                   2021-10..2021-10 1.4.0                |.........=..........|
  ?   dev.derklaro.aerogel                 2025-03..2025-10 3.1.0                |................==..|
aerogel.auto  [no clear owner; `io.github.derklaro` is earliest and most recent]
  ? * io.github.derklaro                   2021-10..2021-10 1.4.0                |.........=..........|
  ?   dev.derklaro.aerogel                 2025-03..2025-10 3.1.0                |................==..|
hla.rti1516e  [no clear owner; `io.github.tno-mst` is earliest and most recent]
  ? * io.github.tno-mst                    2025-07..2025-07 0.0.1                |.................=..|
  ?   nl.tno                               2025-07..2025-10 1.0.0                |.................=..|
me.linusdev.data  [no clear owner; `io.github.lni-dev` is earliest and most recent]
  ? * io.github.lni-dev                    2022-03..2023-04 2.0.20               |..........====......|
  ?   de.linusdev                          2023-04..2025-09 2.3.1                |.............=====..|
com.aspose.words  [no clear owner; `com.luhuiguo` is earliest and most recent]
  ? * com.luhuiguo                         2022-04..2023-01 23.1                 |...........==.......|
  ?   cn.miniants                          2025-09..2025-09 21.4.0               |.................=..|
  ?   com.tengits                          2024-06..2024-06 1.5                  |...............=....|
  ?   cn.wisewe                            2022-06..2022-08 1.1.0-SHANSHOT       |...........=........|
org.apache.commons.net  [no clear owner; `commons-net` is earliest and most recent]
  ? * commons-net                          2020-08..2021-02 3.8.0                |.......==...........|
  ?   int.esa.ccsds.mo                     2025-05..2025-08 12.3                 |.................=..|
  ?   com.nordstrom.ui-tools               2024-08..2024-08 4.23.0               |...............=....|
  ?   io.kestra.plugin                     2024-02..2024-03 0.15.1               |..............=.....|
  ?   org.apache.pinot                     2024-03..2024-03 1.1.0                |..............=.....|
  ?   com.jkoolcloud.tnt4j.streams         2023-11..2023-11 2.0.0                |..............=.....|
com.luciad.imageio.webp  [no clear owner; `org.sejda.imageio` is earliest and most recent]
  ? * org.sejda.imageio                    2020-05..2020-05 0.1.6                |.......=............|
  ?   net.scenariopla.imageio              2025-08..2025-08 0.1.8                |.................=..|
  ?   com.gitee.jmash                      2024-06..2024-06 0.2.2                |...............=....|
  ?   io.github.darkxanter                 2022-10..2023-11 0.3.3                |............===.....|
  ?   org.lucee                            2022-08..2022-08 0.1.6                |...........=........|
  ?   com.github.gotson                    2021-02..2021-08 0.2.2                |........==..........|
org.eclipse.datatools.connectivity.oda.consumer  [no clear owner; `ch.reportingsoft.birt` is earliest and most recent]
  ? * ch.reportingsoft.birt                2025-04..2025-04 3.5.0                |.................=..|
  ?   org.eclipse.birt.dependencies        2025-06..2025-06 3.5.0                |.................=..|
org.eclipse.datatools.connectivity.oda.design  [no clear owner; `ch.reportingsoft.birt` is earliest and most recent]
  ? * ch.reportingsoft.birt                2025-04..2025-04 3.6.0                |.................=..|
  ?   org.eclipse.birt.dependencies        2025-06..2025-06 3.6.0                |.................=..|
org.eclipse.datatools.connectivity.oda.profile  [no clear owner; `ch.reportingsoft.birt` is earliest and most recent]
  ? * ch.reportingsoft.birt                2025-04..2025-04 3.5.0                |.................=..|
  ?   org.eclipse.birt.dependencies        2025-06..2025-06 3.5.0                |.................=..|
org.eclipse.datatools.modelbase.sql.query  [no clear owner; `ch.reportingsoft.birt` is earliest and most recent]
  ? * ch.reportingsoft.birt                2025-04..2025-05 1.4.0.2024           |.................=..|
  ?   org.eclipse.birt.dependencies        2025-06..2025-06 1.4.0                |.................=..|
org.eclipse.datatools.connectivity  [no clear owner; `ch.reportingsoft.birt` is earliest and most recent]
  ? * ch.reportingsoft.birt                2025-04..2025-04 1.15.0               |.................=..|
  ?   org.eclipse.birt.dependencies        2025-06..2025-06 1.15.0               |.................=..|
org.eclipse.datatools.connectivity.apache.derby  [no clear owner; `ch.reportingsoft.birt` is earliest and most recent]
  ? * ch.reportingsoft.birt                2025-04..2025-05 1.3.0.2024           |.................=..|
  ?   org.eclipse.birt.dependencies        2025-06..2025-06 1.3.0                |.................=..|
org.eclipse.datatools.connectivity.oda  [no clear owner; `ch.reportingsoft.birt` is earliest and most recent]
  ? * ch.reportingsoft.birt                2025-04..2025-04 3.7.0                |.................=..|
  ?   org.eclipse.birt.dependencies        2025-06..2025-06 3.7.0                |.................=..|
org.eclipse.datatools.connectivity.oda.flatfile  [no clear owner; `ch.reportingsoft.birt` is earliest and most recent]
  ? * ch.reportingsoft.birt                2025-04..2025-04 3.4.0                |.................=..|
  ?   org.eclipse.birt.dependencies        2025-06..2025-06 3.4.0                |.................=..|
org.eclipse.datatools.modelbase.dbdefinition  [no clear owner; `ch.reportingsoft.birt` is earliest and most recent]
  ? * ch.reportingsoft.birt                2025-04..2025-05 1.3.0.2024           |.................=..|
  ?   org.eclipse.birt.dependencies        2025-06..2025-06 1.3.0                |.................=..|
org.eclipse.datatools.connectivity.sqm.core  [no clear owner; `ch.reportingsoft.birt` is earliest and most recent]
  ? * ch.reportingsoft.birt                2025-04..2025-05 1.6.0.2024           |.................=..|
  ?   org.eclipse.birt.dependencies        2025-06..2025-06 1.6.0                |.................=..|
org.eclipse.datatools.enablement.oda.xml  [no clear owner; `ch.reportingsoft.birt` is earliest and most recent]
  ? * ch.reportingsoft.birt                2025-04..2025-04 1.6.0                |.................=..|
  ?   org.eclipse.birt.dependencies        2025-06..2025-06 1.6.0                |.................=..|
org.eclipse.datatools.modelbase.derby  [no clear owner; `ch.reportingsoft.birt` is earliest and most recent]
  ? * ch.reportingsoft.birt                2025-04..2025-05 1.3.0.2024           |.................=..|
  ?   org.eclipse.birt.dependencies        2025-06..2025-06 1.3.0                |.................=..|
org.eclipse.datatools.modelbase.sql  [no clear owner; `ch.reportingsoft.birt` is earliest and most recent]
  ? * ch.reportingsoft.birt                2025-04..2025-05 1.3.0.2024           |.................=..|
  ?   org.eclipse.birt.dependencies        2025-06..2025-06 1.3.0                |.................=..|
jssc  [no clear owner; `net.jockx` is earliest and most recent]
  ? * net.jockx                            2021-08..2021-08 2.9.3                |.........=..........|
  ?   io.github.java-native                2021-08..2025-06 2.10.2               |.........=========..|
  ?   com.zsmartsystems.zigbee             2022-10..2024-12 1.4.16.1             |............=====...|
common  [no clear owner; `io.github.matwoess` is earliest and most recent]
  ? * io.github.matwoess                   2024-12..2025-01 0.11.3               |................=...|
  ?   pro.shuangxi.framework.openfx        2025-05..2025-05 1.0.0                |.................=..|
persistence.api  [no clear owner; `io.ebean` is earliest and most recent]
  ? * io.ebean                             2019-03..2025-04 3.1                  |....==============..|
  ?   one.gfw                              2023-03..2023-03 3.0                  |............=.......|
jpms_dss_validation_policy  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-04 6.0.1.d4j.1          |......===========...|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2025-03 6.0.1                |............=====...|
com.amazon.corretto.crypto.provider  [no clear owner; `software.amazon.cryptools` is earliest and most recent]
  ? * software.amazon.cryptools            2019-07..2025-03 2.5.0                |.....============...|
  ?   io.github.luneo7                     2022-09..2022-09 1.6.1                |...........=........|
  ?   org.jfrog.buildinfo                  2022-01..2022-01 2.33.2               |..........=.........|
```

## Reassigned and widened ownership

Modules whose resolved owner differs from the implicit first-publisher owner once `owners.tsv` is applied. 🔀 reassigned (241): the first publisher was replaced by a different owner. ➕ widened (193): extra legal owners were allowed alongside the first publisher (e.g. a groupId migration or a co-maintained project). Modules where `owners.tsv` only confirms the first publisher are not listed. Submodules that share the same transition are collapsed into a single `prefix.*` row; the count in braces after the name is how many modules that row covers. The Rejected owner(s) column names the publishers excluded for the name (empty for a pure widening).

| Module | Implicit owner | Owner(s) | Rejected owner(s) |
|---|---|---|---|
| `com.google.auto.service` 🔀 | `com.github.sidneibjunior` | `com.google.auto.service` | `com.github.sidneibjunior, dev.ikm.jpms` |
| `com.install4j.runtime` ➕ | `com.iamsoft` | `com.iamsoft, com.install4j` | (none) |
| `dawdler.*` (68) ➕ | `io.github.dawdler-series` | `club.dawdler, io.github.dawdler-series` | (none) |
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
| `org.eclipse.birt.*` (72) 🔀 | `io.github.reporting-solutions.nl` | `org.eclipse.birt.nl` | `io.github.reporting-solutions.nl` |
| `org.eclipse.emf.*` (3) ➕ | `com.innoventsolutions.birt.runtime` | `com.innoventsolutions.birt.runtime, org.eclipse.emf` | `ch.reportingsoft.birt` |
| `org.eclipse.emf.ecore.change` 🔀 | `com.innoventsolutions.birt.runtime` | `org.eclipse.emf` | `com.innoventsolutions.birt.runtime, ch.reportingsoft.birt` |
| `org.gnome.*` (7) ➕ | `io.github.jwharm.javagi` | `io.github.jwharm.javagi, org.java-gi` | (none) |
| `org.hiero.*` (9) ➕ | `com.swirlds` | `com.hedera.hashgraph, com.swirlds` | (none) |
| `org.hiero.block.protobuf.sources` 🔀 | `org.hiero.block` | `com.hedera.hashgraph, com.swirlds` | `org.hiero.block, org.hiero.block-node` |
| `org.pcollections` 🔀 | `net.pincette` | `org.pcollections` | `net.pincette` |
| `org.primefaces.extensions` ➕ | `com.guicedee.services` | `com.guicedee.services, org.primefaces.extensions` | (none) |
| `play.ws.standalone.*` (4) ➕ | `com.typesafe.play` | `com.typesafe.play, org.playframework` | (none) |
| `retrofit2.converter.jaxb3` 🔀 | `io.github.goooler.retrofit2` | `com.squareup.retrofit2` | `io.github.goooler.retrofit2, com.huanli233.retrofit2-compat, io.github.mindcomic.retrofit2` |
| `spring.security.*` (2) 🔀 | `io.spring.gradle` | `org.springframework` | `io.spring.gradle, io.spring.security.gradle` |
| `spring.security.project.plugin` 🔀 | `io.spring.security.gradle` | `org.springframework` | `io.spring.security.gradle, io.spring.gradle` |
| `zipkin2.reporter.kafka` 🔀 | `org.apache.zipkin.reporter2` | `io.zipkin.reporter2` | `org.apache.zipkin.reporter2` |

