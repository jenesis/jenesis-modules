# Module ownership drifters

Generated 2026-07-15. A module *drifts* when more than one groupId publishes the name and its `owners.tsv` does not yet name every publisher (no `owners.tsv`, or one that leaves some publishing groupId neither `allowed` nor `rejected`). Resolving a drift means deciding each groupId via `SetOwners` (which writes `allowed`/`rejected`); a fully-named module drops off this list.

| Category | Unresolved | Resolved via owners.tsv |
|---|---:|---:|
| explicit-rules | 36 | 666 |
| republisher | 3 | 15 |
| migration | 10 | 772 |
| fork | 119 | 320 |
| shaded | 31 | 1271 |
| tld-dropped | 2 | 70 |
| two-segments | 67 | 0 |
| unclassified | 263 | 7 |
| **total** | **531** | **3121** |

The table covers all **3652** multi-owner modules (of **37389** modules scanned).

Timeline axis spans 2017-01 .. 2026-07 (today). Per group: decision `A`=allowed `R`=rejected `?`=undecided, `*`=current owner, then the publication range, latest version, and a `=` activity bar across the axis.

## explicit-rules (36)

Hand-curated overrides: a module matching an explicit rule is assigned to a fixed owner groupId regardless of the heuristic.

- The module name equals, or falls under, a hand-curated prefix in the explicit-owner map.
- Allow every publisher whose groupId falls under the mapped owner prefix; reject all other publishers.

| count | current owner | new owner(s) |
|---:|---|---|
| 1 | `org.jetbrains.kotlinx` | `org.jetbrains.kotlinx, org.jetbrains.dokka, org.jetbrains.intellij.deps.kotlinx` |
| 1 | `org.jetbrains.kotlinx` | `org.jetbrains.kotlinx, org.jetbrains.kotlin` |

```
com.google.gson  [explicit rule: owned by `com.google.code.gson`; 536 other group(s) rejected]
  A * com.google.code.gson                 2019-10..2026-04 2.14.0               |.....===============|
  R   com.aliyun                           2021-09..2026-07 4.5.0                |.........===========|
  ?   com.conaxgames                       2026-06..2026-07 1.5.7                |...................=|
  R   org.sonarsource.dotnet               2022-02..2026-07 10.29.0.143774       |..........==========|
  R   org.openjproxy                       2025-09..2026-07 0.5.2-beta           |.................===|
  R   io.quarkus                           2024-07..2026-07 3.37.2               |...............=====|
    + 531 more: dev.cjfravel, org.jetbrains.kotlin, org.sonarsource.java, com.snowflake, org.apache.pulsar, org.apache.orc, org.sonarsource.sonarlint.ls, org.sonarsource.sonarlint.core, network.lightsail, com.ironcorelabs, top.ceroxe.api, com.linkedin.iceberg, (+519 more)
kotlin.stdlib  [explicit rule: owned by `org.jetbrains.kotlin`; 222 other group(s) rejected]
  A * org.jetbrains.kotlin                 2019-01..2026-07 2.4.10-RC2           |....================|
  R   com.aliyun.odps                      2026-03..2026-07 0.59.0-public        |..................==|
  R   org.pgpainless                       2023-07..2026-07 1.7.8                |.............=======|
  R   io.github.cuneytcakir                2026-04..2026-07 2.0.1                |..................==|
  R   org.jetbrains.lets-plot              2025-03..2026-06 4.11.0               |................====|
  R   com.easemob.im                       2024-04..2026-06 1.0.20               |..............======|
    + 217 more: fi.evident.apina, love.forte.plugin.suspend-transform, de.darkatra.injector, io.last9, net.master-studios, org.jetbrains.kotlinx, com.volcengine, com.alibaba.ververica, org.octopusden.octopus.jira, com.airbnb.viaduct, com.tidbcloud, org.virtuslab, (+205 more)
kotlin.stdlib.jdk8  [explicit rule: owned by `org.jetbrains.kotlin`; 245 other group(s) rejected]
  A * org.jetbrains.kotlin                 2019-01..2026-07 2.4.10-RC2           |....================|
  R   com.huaweicloud                      2023-02..2026-07 3.26.6               |............========|
  R   com.aliyun                           2026-01..2026-07 3.13.4               |..................==|
  R   com.google.genai                     2026-04..2026-07 1.61.0               |..................==|
  ?   org.octopusden.octopus.automation.teamcity 2026-07..2026-07 1.0.40               |...................=|
  R   com.aliyun.odps                      2026-03..2026-07 3.10.11              |..................==|
    + 240 more: eu.rekawek.coffeegb, com.newrelic.agent.android, io.github.graphdsl, com.spectralogic.ds3, com.commercetools.rmf, me.bechberger, com.squareup, org.apache.flink, org.apache.hudi, org.octopusden.octopus.jira, com.sportradar.unifiedodds.sdk, org.apache.dolphinscheduler, (+228 more)
scala.library  [explicit rule: owned by `org.scala-lang`; 2 other group(s) rejected]
  A * org.scala-lang                       2018-03..2026-07 3.9.0-RC3            |..==================|
  ?   ch.epfl.lara                         2026-06..2026-06 3.10.0-RC1-bin-20260608-cf86bba-NIGHTLY |...................=|
  R   com.github.xuwei-k                   2021-01..2021-01 2.13.3-bin-1ca7d14   |........=...........|
com.google.common  [explicit rule: owned by `com.google.guava`; 72 other group(s) rejected]
  A * com.google.guava                     2017-07..2026-04 33.6.0-jre           |.==================.|
  R   io.acryl                             2026-04..2026-07 1.6.0.13rc1          |...................=|
  R   io.github.katsuhisamaruyama          2026-03..2026-07 3.0.37               |..................==|
  R   io.digiexpress                       2025-10..2026-07 6.0.28               |..................==|
  R   org.weakref                          2025-12..2026-06 1.29                 |..................==|
  R   org.apache.phoenix                   2025-09..2026-06 5.3.2                |.................===|
    + 67 more: io.trino.tempto, org.foundationdb, io.orkes.conductor, org.conductoross, org.talend.sdk.component.sample.feature, lv.ailab.morphology, org.opendaylight.aaa, com.hubspot, org.apache.hbase.thirdparty, io.javelit, com.alibaba.ververica, org.apache.pinot, (+55 more)
kotlin.reflect  [explicit rule: owned by `org.jetbrains.kotlin`; 66 other group(s) rejected]
  A * org.jetbrains.kotlin                 2019-01..2026-07 2.4.10-RC2           |....================|
  ?   io.github.barqdb.kotlin              2026-07..2026-07 4.0.7                |...................=|
  ?   org.octopusden.octopus.infrastructure 2026-07..2026-07 3.0.2                |...................=|
  R   org.apache.pinot                     2025-09..2026-06 1.5.1                |.................===|
  ?   io.github.rodrigotimoteo             2026-06..2026-06 0.1.0                |...................=|
  R   com.airbnb.viaduct                   2026-01..2026-04 0.29.0               |..................==|
    + 61 more: io.github.abdullahkhan118, io.github.tobi-laa, io.github.kshulzh.kefir, io.github.xilinjia.krdb, io.github.snow1026, com.browserstack, com.simprints.realm.kotlin, org.pkl-lang, com.statsig, com.infomaniak.realm.kotlin, com.solapi, io.github.honkling.commando, (+49 more)
kotlin.stdlib.jdk7  [explicit rule: owned by `org.jetbrains.kotlin`; 21 other group(s) rejected]
  A * org.jetbrains.kotlin                 2019-01..2026-07 2.4.10-RC2           |....================|
  R   io.pyroscope                         2025-04..2026-07 2.7.0                |................====|
  ?   me.bechberger                        2026-06..2026-06 0.0.8                |...................=|
  R   com.kroegerama.openapi-kgen          2023-12..2026-06 0.18.2               |..............======|
  ?   org.octopusden.octopus.jira          2026-06..2026-06 2.0.3                |...................=|
  R   io.github.team-sneakymouse           2026-05..2026-05 4.0-Beta-13          |...................=|
    + 16 more: com.seanshubin.code.structure, me.xcue, org.partiql, io.github.wadoon.key, org.btmonier, com.slothiesmooth, com.slothiesmooth.links-detektor, hu.bme.mit.theta, com.github.shynixn.mccoroutine, dev.nelmin.spigot, com.facebook, com.cjcrafter, (+4 more)
kotlinx.serialization.protobuf  [explicit rule: owned by `org.jetbrains`; 1 other group(s) rejected]
  A * org.jetbrains.kotlinx                2021-09..2026-04 1.11.0               |.........==========.|
  ?   org.jetbrains.kotlin                 2026-01..2026-07 2.4.10-RC2           |..................==|
  R   org.danbrough.kotlinx                2022-09..2023-03 1.5.0                |...........==.......|
org.objectweb.asm  [explicit rule: owned by `org.ow2.asm`; 166 other group(s) rejected]
  A * org.ow2.asm                          2017-07..2026-05 9.10.1               |.===================|
  R   be.ugent.idlab.knows                 2025-09..2026-07 0.6.8                |.................===|
  R   com.github.jnr                       2019-10..2026-07 0.39.2               |......==============|
  R   org.apache.iotdb                     2023-12..2026-07 2.0.10               |..............======|
  ?   org.virtuslab                        2026-07..2026-07 0.1.0-M1             |...................=|
  R   org.tiatesting                       2024-04..2026-06 0.1.18               |...............=====|
    + 161 more: org.virtuslab.scala-cli, org.glassfish.main.extras, io.spicelabs, com.my-oli, org.teavm, io.github.mitsumi-solutions-develop, org.apache.geaflow, org.noear, net.corda, com.microsoft.azure.kusto, com.pinterest.psc, com.datadoghq, (+149 more)
org.objectweb.asm.commons  [explicit rule: owned by `org.ow2.asm`; 39 other group(s) rejected]
  A * org.ow2.asm                          2017-07..2026-05 9.10.1               |.===================|
  R   io.debezium                          2026-02..2026-07 3.6.0.Final          |..................==|
  ?   org.gephi                            2026-06..2026-06 0.11.2               |...................=|
  R   org.teavm                            2023-03..2026-06 0.15.0               |............========|
  R   io.joern                             2025-06..2026-05 12.2_744c5dee92-202605281342 |.................===|
  R   com.appdynamics                      2024-01..2026-04 26.3.1               |..............======|
    + 34 more: com.apollographql.apollo, org.copper-engine, cn.iservicego, org.tango-controls, com.apollographql.apollo3, de.firemage.autograder, com.yugabyte, com.newrelic.agent.android, com.gradleup, org.tango-controls.pogo, software.amazon.disco, org.realityforge.shade, (+22 more)
kotlinx.coroutines.core  [explicit rule: owned by `org.jetbrains`; 21 other group(s) rejected]
  A * org.jetbrains.kotlinx                2023-03..2026-05 1.11.0               |............========|
  ?   org.openprojectx.java.dns            2026-06..2026-07 0.1.4                |...................=|
  R   com.airbnb.viaduct                   2026-05..2026-05 1.1.0                |...................=|
  R   ca.acendas                           2025-11..2026-05 1.9.1                |..................==|
  R   com.krillforge                       2026-04..2026-04 0.0.2                |...................=|
  R   org.openprojectx.hadoop.win          2026-04..2026-04 0.1.4-3.1.1.7.1.9.14-2 |..................=.|
    + 18 more: org.jetbrains.dokka, com.eygraber, org.jetbrains.intellij.deps.kotlinx, io.github.danbeldev, io.johnsonlee.kx, io.github.zimoyin, io.johnsonlee.exec, io.github.saumya-bhatt, io.realm.kotlin, com.rickbusarow.doks, io.sirix, com.squareup.wire, (+6 more)
jul.to.slf4j  [explicit rule: owned by `org.slf4j`; 16 other group(s) rejected]
  A * org.slf4j                            2019-02..2026-05 2.0.18               |....================|
  ?   xyz.hldev.libra-common               2026-06..2026-06 1.0.1                |...................=|
  R   de.codecentric                       2024-01..2026-02 3.3.0                |..............=====.|
  R   net.finmath                          2025-11..2026-02 2.5.7                |..................=.|
  R   io.github.davincilll                 2025-12..2025-12 1.0.4                |..................=.|
  R   io.github.daone-dadp                 2025-11..2025-11 3.0.2                |..................=.|
    + 11 more: io.kestra.plugin, io.github.tky0065, com.itxk.maven, io.github.srilathakarri, de.fraunhofer.iosb.ilt.faaast.registry, org.easypeelsecurity, io.github.tracedin, com.github.kaklakariada, com.tencent.cloud, org.conductoross, io.bdeploy
kotlinx.serialization.core  [explicit rule: owned by `org.jetbrains`; 28 other group(s) rejected]
  A * org.jetbrains.kotlinx                2021-09..2026-04 1.11.0               |.........==========.|
  ?   lol.simeon                           2026-06..2026-06 1.1.2                |...................=|
  R   love.forte.plugin.suspend-transform  2025-04..2026-06 2.4.0-0.14.0         |.................===|
  R   dev.sebastiano.spectre               2026-05..2026-06 0.2.1                |...................=|
  R   dev.robocode.tankroyale              2026-01..2026-05 1.0.2                |..................==|
  R   io.github.wangbax                    2026-04..2026-04 5.5.1-okio-fork-2    |...................=|
    + 23 more: com.squareup.wire, org.ldemetrios, io.github.lumamontes, dev.zacsweers.metro, io.typst, io.availe, dev.oglass, io.github.oewntk, io.github.lexa-diky, com.toasttab.expediter, io.johnsonlee.exec, io.specmatic, (+11 more)
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
```

## republisher (3)

Earliest owner is foreign to the module name while a natural-namespace owner is also present (shaded / repackaged jars).

- The earliest (current) owner's groupId is foreign to the module name: the name does not fall under it.
- A natural-namespace owner - a publisher whose groupId the module name does fall under - is also present.
- The foreign earliest owner is still globally active (a dormant one would be a relocation, see migration).
- Allow the natural owner; reject the foreign republisher.

| count | current owner | new owner(s) |
|---:|---|---|
| 1 | `com.github.ppodgorsek.email` | `org.apache.commons` |
| 1 | `io.github.pustike` | `org.apache.commons` |
| 1 | `org.glassfish.jaxb` | `com.sun.xml.bind` |

```
org.apache.commons.csv  [republished by `io.github.pustike` (still active); belongs to `org.apache.commons`]
  ? * io.github.pustike                    2019-01..2019-07 1.7.0                |....==..............|
  ?   org.sonarsource.scanner.engine       2026-05..2026-07 13.4.0.3968          |...................=|
  ?   be.ugent.idlab.knows                 2025-09..2026-07 2.0.3                |.................===|
  ?   com.orientechnologies                2026-03..2026-07 3.2.54               |..................==|
  ?   org.apache.pinot                     2024-08..2026-06 1.5.1                |...............=====|
  ?   com.guicedee.modules.services        2026-04..2026-06 2.1.0                |...................=|
    + 10 more: org.testingisdocumenting.znai, io.kestra.plugin, org.apache.commons, xyz.ottr.lutra, org.jetbrains.kotlinx, com.wizzdi, io.telicent.jena.graphql, io.telicent.jena, org.apache.jena, com.guicedee.services
com.sun.xml.bind  [republished by `org.glassfish.jaxb` (still active); belongs to `com.sun.xml.bind`]
  R * org.glassfish.jaxb                   2018-07..2019-01 2.3.2                |...==...............|
  R   int.esa.ccsds.mo                     2025-08..2026-07 14.0                 |.................===|
  ?   com.checkmarx                        2026-06..2026-06 2026.2.32            |...................=|
  A   com.sun.xml.bind                     2018-07..2026-05 4.0.9                |...=================|
  R   com.exasol                           2024-10..2025-10 5.4.3                |................===.|
  R   gov.nasa.pds                         2021-10..2025-04 2.11.0               |.........========...|
    + 32 more: com.intuit.quickbooks-online, com.google.tsunami, com.helger.schematron, io.github.azagniotov, com.liferay, org.opengis.cite, org.duracloud, edu.iris.dmc, br.com.swconsultoria, one.gfw, org.xtce, com.github.cafapi, (+20 more)
org.apache.commons.mail  [republished by `com.github.ppodgorsek.email` (still active); belongs to `org.apache.commons`]
  ? * com.github.ppodgorsek.email          2023-06..2023-06 2.0.0                |.............=......|
  ?   io.prophecy                          2024-08..2026-06 3.3.0-onprem-9.4.1   |...............=====|
  ?   org.apache.commons                   2023-12..2023-12 1.6.0                |..............=.....|
```

## migration (10)

The publishing groupId handed off over time (a rename or a relocation), so both coordinates are kept.

- Rename: a more-recent successor is the same project as the owner (a shared groupId prefix, or two shared leading segments).
- Relocation: the owner stopped at or before a credible successor took over (or the owner went globally dormant), and that successor itself owns the module namespace.
- Allow both old and new so history stays resolvable and `latest` is current.

| count | current owner | new owner(s) |
|---:|---|---|
| 3 | `io.github.humbleui.skija` | `io.github.humbleui.skija, io.github.humbleui` |
| 2 | `love.forte.simbot.logger` | `love.forte.simbot.logger, love.forte.simbot` |
| 1 | `com.ibm.zertjsse` | `com.ibm.zertjsse, com.ibm.semeru-zjavasecurity` |
| 1 | `com.peruncs.odbjca` | `com.peruncs.odbjca, com.peruncs` |
| 1 | `org.apache.commons` | `org.apache.commons, org.apache.tomee, org.apache.openjpa, org.apache.meecrowave` |
| 1 | `org.eclipse.tycho` | `org.eclipse.tycho, org.eclipse.platform` |
| 1 | `org.neo4j` | `org.neo4j, org.neo4j.connectors` |

```
io.github.humbleui.skija.macos.arm64  [renamed `io.github.humbleui.skija` -> `io.github.humbleui` (latest 0.119.6)]
  A * io.github.humbleui.skija             2021-11..2021-11 0.96.0               |..........=.........|
  A   io.github.humbleui                   2021-12..2026-06 0.119.6              |..........==========|
  ?   com.behemiron.engine                 2026-06..2026-06 0.143.17             |...................=|
io.github.humbleui.skija.macos.x64  [renamed `io.github.humbleui.skija` -> `io.github.humbleui` (latest 0.119.6)]
  A * io.github.humbleui.skija             2021-11..2021-11 0.96.0               |..........=.........|
  A   io.github.humbleui                   2021-12..2026-06 0.119.6              |..........==========|
  ?   com.behemiron.engine                 2026-06..2026-06 0.143.17             |...................=|
io.github.humbleui.skija.shared  [renamed `io.github.humbleui.skija` -> `io.github.humbleui` (latest 0.119.6)]
  A * io.github.humbleui.skija             2021-11..2021-11 0.96.0               |..........=.........|
  A   io.github.humbleui                   2021-12..2026-06 0.119.6              |..........==========|
  ?   com.behemiron.engine                 2026-06..2026-06 0.143.17             |...................=|
simbot.logger  [renamed `love.forte.simbot.logger` -> `love.forte.simbot` (latest 5.0.0-Preview3)]
  ? * love.forte.simbot.logger             2024-01..2026-05 5.0.0-Preview2       |..............======|
  ?   love.forte.simbot                    2026-06..2026-06 5.0.0-Preview3       |...................=|
simbot.logger.slf4j2impl  [renamed `love.forte.simbot.logger` -> `love.forte.simbot` (latest 5.0.0-Preview3)]
  ? * love.forte.simbot.logger             2024-01..2026-05 5.0.0-Preview2       |..............======|
  ?   love.forte.simbot                    2026-06..2026-06 5.0.0-Preview3       |...................=|
org.neo4j.cypherdsl.core  [renamed `org.neo4j` -> `org.neo4j.connectors` (latest 6.0.0-RC02-s_2.13)]
  ? * org.neo4j                            2020-07..2026-06 2025.3.0             |.......=============|
  ?   org.neo4j.connectors                 2026-06..2026-06 6.0.0-RC02-s_2.13    |...................=|
org.apache.commons.dbcp2  [renamed `org.apache.commons` -> `org.apache.tomee` (latest 11.0.0-M1)]
  ? * org.apache.commons                   2023-08..2025-12 2.14.0               |.............======.|
  ?   org.apache.tomee                     2023-12..2026-06 11.0.0-M1            |..............======|
  ?   org.apache.meecrowave                2025-10..2025-10 2.0.0                |.................=..|
  ?   net.ontopia                          2025-04..2025-07 5.5.2                |.................=..|
  ?   org.apache.openjpa                   2024-09..2025-05 4.1.1                |...............===..|
  ?   org.ikasan                           2024-07..2024-07 1.0.0                |...............=....|
org.eclipse.osgi  [renamed `org.eclipse.tycho` -> `org.eclipse.platform` (latest 3.24.200)]
  ? * org.eclipse.tycho                    2018-05..2018-05 3.13.0.v20180226-1711 |...=................|
  ?   org.eclipse.platform                 2018-06..2026-06 3.24.200             |...=================|
  ?   io.joynr.tools.generator             2025-01..2026-06 1.26.6               |................====|
  ?   org.alfasoftware                     2021-05..2026-04 2.7.0                |.........===========|
  ?   net.kieker-monitoring                2026-04..2026-04 2.0.3                |...................=|
  ?   org.bonitasoft.bpm                   2023-10..2026-04 9.0.9                |.............=======|
    + 22 more: de.funfried.libraries, io.github.alien-tools, org.tango-controls, org.tango-controls.pogo, ch.reportingsoft.birt, com.liferay, net.revelc.code.formatter, org.kie.j2cl.tools.external, io.github.dogla, com.vertispan.j2cl.external, fr.inria.gforge.spirals, org.geneweaver, (+10 more)
zertjsse  [renamed `com.ibm.zertjsse` -> `com.ibm.semeru-zjavasecurity` (latest 11.0.31.0)]
  ? * com.ibm.zertjsse                     2026-05..2026-05 11.0.31.0            |...................=|
  ?   com.ibm.semeru-zjavasecurity         2026-05..2026-05 11.0.31.0            |...................=|
com.peruncs.odbjca.api  [renamed `com.peruncs.odbjca` -> `com.peruncs` (latest 0.0.2)]
  ? * com.peruncs.odbjca                   2018-11..2018-11 0.0.1                |....=...............|
  ?   com.peruncs                          2018-11..2018-11 0.0.2                |....=...............|
```

## fork (119)

A cross-org coordinate publishes the same name while the original owner is still active.

- A more-recent cross-org coordinate (a successor) publishes the same name while the original is still active.
- The earliest publisher is itself a credible owner: it owns the module namespace, or is the closest groupId to it.
- Keep the original owner; reject the fork.

```
com.fasterxml.jackson.databind  [fork: keep `com.fasterxml.jackson.core`, `com.adobe.granite` still publishes the name]
  A * com.fasterxml.jackson.core           2017-09..2026-07 2.18.9               |.===================|
  R   com.adobe.granite                    2024-01..2026-07 1.22.4               |..............======|
  ?   io.github.prabhusiddarth             2026-07..2026-07 1.0.3                |...................=|
  R   io.github.serpro69                   2022-10..2026-07 1.16.2               |............========|
  R   org.octopusden.octopus.sonar         2026-04..2026-07 2.0.12               |...................=|
  ?   io.github.gabrielbbaldez             2026-07..2026-07 0.4.0                |...................=|
    + 448 more: io.github.tomas-samek, org.octopusden.octopus.components.registry.automation, com.oceanbase, com.expediagroup.apiary, org.jetbrains.intellij.plugins, org.octopusden.octopus.automation.release-management, com.linkedin.iceberg, com.perimeterx, io.strimzi, com.flexemarkets, io.github.kathukyabrian, io.github.wxganzhanfan, (+436 more)
ch.qos.logback.classic  [fork: keep `ch.qos.logback`, `com.limemojito.oss.aws` still publishes the name]
  A * ch.qos.logback                       2018-01..2026-07 1.5.38               |..==================|
  R   com.limemojito.oss.aws               2025-10..2026-07 8.0.15.37            |.................===|
  R   com.daml                             2022-10..2026-07 3.6.0-snapshot.20260709.14749.0.v2489973e |...........=========|
  R   top.yqingyu                          2023-06..2026-06 2.0.0                |.............=======|
  ?   org.alfasoftware                     2026-06..2026-06 3.0.0                |...................=|
  R   org.apache.sling                     2025-05..2026-06 6.2.0                |.................===|
    + 89 more: com.salesforce.cantor, org.eclipse.ecsp, org.javastro.ivoa, ch.exense.step, io.mosip.biosdk, io.mosip.demosdk, de.fraunhofer.iosb.ilt, dk.alexandra.fresco, org.jboss.pnc.gradle-manipulator, com.deltaproto, org.commonjava.atlas, club.dawdler, (+77 more)
org.yaml.snakeyaml  [fork: keep `org.yaml`, `com.sparkutils` still publishes the name]
  A * org.yaml                             2019-02..2026-02 2.6                  |....===============.|
  R   com.sparkutils                       2024-12..2026-07 0.2.0-RC9-preview    |................====|
  R   org.conductoross                     2026-03..2026-07 3.32.0-rc.5          |..................==|
  R   com.huaweicloud.sdk                  2024-01..2026-07 3.1.205              |..............======|
  R   io.vertx                             2023-11..2026-07 4.5.29               |..............======|
  R   io.vertigo                           2024-07..2026-07 4.4.0                |...............=====|
    + 78 more: com.arcmutate, org.apache.phoenix, io.github.spah1879, com.nvidia, org.apache.flink, io.github.wangscu, com.liquibase, dev.feit, com.google.cloud, dev.domkss, com.scivicslab, com.scivicslab.turingworkflow.plugins, (+66 more)
com.google.errorprone.annotations  [fork: keep `com.google.errorprone`, `eu.rssw.sonar.openedge` still publishes the name]
  A * com.google.errorprone                2019-12..2026-06 2.50.0               |......==============|
  R   eu.rssw.sonar.openedge               2024-11..2026-07 3.9.0                |................====|
  R   com.salesforce.multicloudj           2026-04..2026-07 0.4.1                |...................=|
  R   org.checkerframework                 2024-10..2026-07 4.2.1                |...............=====|
  R   org.apache.seatunnel                 2024-10..2026-06 1.38.0-3.0.0         |................====|
  R   com.facebook.presto                  2025-02..2026-06 0.298.1              |................====|
    + 53 more: org.noear, au.com.integradev.samples, org.apache.spark, com.google.appengine, com.google.turbine, io.okdp, com.clickzetta, io.zipkin.zipkin2, com.google.cloud, de.enflexit.awbAssist, com.palantir.hadoop-crypto2, org.foundationdb, (+41 more)
dev.tamboui.widgets  [fork: keep `dev.tamboui`, `com.github.jlangch` still publishes the name]
  ? * dev.tamboui                          2026-02..2026-06 0.4.0                |..................==|
  ?   com.github.jlangch                   2026-06..2026-07 1.13.12              |...................=|
com.fasterxml.jackson.datatype.jsr310  [fork: keep `com.fasterxml.jackson.datatype`, `io.github.cloudstub` still publishes the name]
  A * com.fasterxml.jackson.datatype       2017-10..2026-07 2.18.9               |.===================|
  ?   io.github.cloudstub                  2026-06..2026-07 0.1.0-beta.8         |...................=|
  R   org.opencds.cqf.cql.ls               2026-03..2026-07 4.10.0               |..................==|
  R   io.openlineage                       2022-06..2026-07 1.51.0               |...........=========|
  ?   org.datap-rs                         2026-07..2026-07 0.1.0                |...................=|
  ?   io.teknek.deliverance                2026-07..2026-07 0.0.10               |...................=|
    + 83 more: com.linkedin.iceberg, org.codelibs.fess, org.apache.gravitino, cab.ml, org.octopusden.octopus.reporting-service, org.octopusden.octopus.automation.teamcity, org.openapitools, org.apache.hudi, org.byteveda.agenteval, org.apache.doris, io.spring.gradle, io.camunda.filestorage, (+71 more)
org.apache.commons.lang3  [fork: keep `org.apache.commons`, `io.streamnative` still publishes the name]
  A * org.apache.commons                   2017-06..2025-11 3.20.0               |.==================.|
  R   io.streamnative                      2025-05..2026-07 4.0.10.8             |.................===|
  R   com.equinor.neqsim                   2025-11..2026-07 3.16.0-Java8         |..................==|
  ?   com.liquibase.ext                    2026-06..2026-07 5.2.1                |...................=|
  R   org.sonarsource.java                 2024-11..2026-07 8.35.0.45121         |................====|
  R   org.apache.pulsar                    2025-05..2026-07 4.2.3                |.................===|
    + 102 more: io.openlineage, com.ubs-hainer, org.apache.gravitino, de.grobmeier.json, io.swagger.parser.v3, ai.platon.gora, io.github.luo-zhan, com.datastax.oss, org.apache.tomee, com.ascentstream.pulsar, io.github.roger3lee, no.entur, (+90 more)
dev.tamboui.toolkit  [fork: keep `dev.tamboui`, `dev.jbang` still publishes the name]
  ? * dev.tamboui                          2026-02..2026-06 0.4.0                |..................==|
  ?   dev.jbang                            2026-07..2026-07 0.140.1              |...................=|
org.jspecify  [fork: keep `org.jspecify`, `de.pirckheimer-gymnasium` still publishes the name]
  A * org.jspecify                         2021-07..2024-07 1.0.0                |.........=======....|
  R   de.pirckheimer-gymnasium             2026-04..2026-07 0.50.0               |..................==|
  R   com.google.appengine                 2025-04..2026-07 5.0.5-beta.1         |................====|
  ?   com.power4j.fist3                    2026-06..2026-07 3.15.0               |...................=|
  R   org.jboss.elemento                   2025-10..2026-07 2.5.4                |..................==|
  ?   org.treblereel.gwt.jakarta.rest      2026-07..2026-07 0.1                  |...................=|
    + 46 more: com.helger.kaltblut, org.treblereel.gwt.json.mapper, io.sapl, org.treblereel.gwt.yaml.mapper, io.github.jinahya, io.trino.tpcds, io.github.openfeign.querydsl, org.treblereel.gwt.xml.mapper, org.treblereel.j2cl.processors, org.kie.j2cl.tools.di, org.kie.j2cl.tools.json.mapper, org.kie.j2cl.tools.processors, (+34 more)
com.fasterxml.jackson.annotation  [fork: keep `com.fasterxml.jackson.core`, `io.github.giis-uniovi` still publishes the name]
  A * com.fasterxml.jackson.core           2017-09..2026-07 2.18.9               |.===================|
  R   io.github.giis-uniovi                2024-08..2026-07 2.2.1                |...............=====|
  R   com.yahoo.vespa                      2022-06..2026-07 8.722.24             |...........=========|
  R   com.chartiq.finsemble                2025-06..2026-07 9.10.27              |.................===|
  ?   io.github.tibyaan-org                2026-07..2026-07 0.1.0                |...................=|
  R   com.kinetica                         2024-08..2026-07 7.2.3.20             |...............=====|
    + 466 more: com.ibm.jsonata4java, ch.exense.step, org.apache.pulsar, org.kill-bill.billing, com.yetanalytics, fr.inria.gforge.spoon, org.mock-server, com.silanis.esl, org.zowe.client.java.sdk, org.apache.iotdb, io.github.cdy-codey, io.orqueio.bpm, (+454 more)
org.openapitools.jackson.nullable  [fork: keep `org.openapitools`, `io.github.giis-uniovi` still publishes the name]
  A * org.openapitools                     2023-02..2026-03 0.2.10               |............=======.|
  ?   io.github.giis-uniovi                2026-07..2026-07 2.2.1                |...................=|
  R   io.airlift                           2026-04..2026-07 438                  |..................==|
io.netty.internal.tcnative  [fork: keep `io.netty`, `org.finos.legend.engine` still publishes the name]
  A * io.netty                             2021-10..2026-06 2.0.80.Final         |.........===========|
  R   org.finos.legend.engine              2026-01..2026-07 4.132.7              |..................==|
  R   com.instaclustr                      2025-10..2026-07 4.1.10               |.................===|
  ?   com.datarobot                        2026-07..2026-07 11.2.42              |...................=|
  R   io.grpc                              2025-03..2026-07 1.81.1               |................====|
  R   com.arcadedb                         2025-10..2026-07 26.7.2               |.................===|
    + 75 more: com.spotify.confidence, com.liquibase.ext, com.seaweedfs, org.apache.beam, io.github.ktestify, com.google.api, com.google.cloud, io.qpointz.mill, com.azure.cosmos.spark, io.temporal, io.weaviate, com.google.cloud.opentelemetry, (+63 more)
com.fasterxml.jackson.core  [fork: keep `com.fasterxml.jackson.core`, `org.apache.seatunnel` still publishes the name]
  A * com.fasterxml.jackson.core           2017-09..2026-07 2.18.9               |.===================|
  R   org.apache.seatunnel                 2022-09..2026-07 2.15.4-3.0.0         |...........=========|
  R   io.deephaven                         2026-01..2026-07 42.1                 |..................==|
  R   org.evomaster                        2024-04..2026-07 6.1.0                |..............======|
  R   io.delta                             2024-05..2026-07 4.3.1                |...............=====|
  R   com.alibaba.hologres                 2025-10..2026-07 2.7.7                |.................===|
    + 405 more: com.gpudb, com.algolia, org.kill-bill.billing, io.openlineage, com.skraba, de.fraunhofer.iosb.ilt.FROST-Server, io.orqueio.spin, lat.sofis.biobpm.spin, com.linkedin.iceberg, org.operaton.spin, org.finos.fluxnova.spin, org.talend.sdk.component, (+393 more)
org.apache.commons.compress  [fork: keep `org.apache.commons`, `io.github.trethore` still publishes the name]
  A * org.apache.commons                   2017-10..2025-07 1.28.0               |.=================..|
  R   io.github.trethore                   2026-02..2026-07 146.0.10.3           |..................==|
  R   org.apache.beam                      2024-06..2026-07 2.75.0               |...............=====|
  R   org.eclipse.tahu                     2024-06..2026-07 1.0.20               |...............=====|
  R   org.apache.grails                    2026-04..2026-06 7.1.3                |...................=|
  R   com.mobidevelop.robovm               2024-02..2026-06 2.3.25               |..............======|
    + 34 more: org.apache.flink, com.alibaba.ververica, org.apache.pinot, org.apache.parquet, org.apache.druid.extensions, com.theartos, me.bechberger, com.jlpka.langidentify, io.acryl, com.jlpka, io.codechicken, org.onebusaway, (+22 more)
info.picocli  [fork: keep `info.picocli`, `com.instaclustr` still publishes the name]
  A * info.picocli                         2017-10..2025-04 4.7.7                |.=================..|
  R   com.instaclustr                      2020-01..2026-07 4.1.10               |......==============|
  R   org.keycloak                         2024-06..2026-07 26.7.0               |...............=====|
  R   org.primefaces                       2021-09..2026-07 16.0.0-RC1           |.........===========|
  ?   run.endive                           2026-06..2026-07 1.0.1                |...................=|
  R   ai.tegmentum.webassembly4j           2026-03..2026-07 2.1.0                |..................==|
    + 157 more: org.substrate4j, org.hjug.refactorfirst.report, org.rundeck.rd, org.faktorips.runtimejpa, dev.lievit, io.github.arthurcollet, io.spicelabs, io.github.cyber655, io.github.a-collet, com.github.nbbrd.sasquatch, io.github.horadomu, ws.idle, (+145 more)
io.github.classgraph  [fork: keep `io.github.classgraph`, `com.datarobot` still publishes the name]
  A * io.github.classgraph                 2018-08..2025-10 4.8.184              |...===============..|
  R   com.datarobot                        2022-02..2026-07 11.2.42              |..........==========|
  R   org.finos.legend.engine              2025-10..2026-07 4.132.7              |..................==|
  ?   dev.sixpack                          2026-07..2026-07 0.6.2                |...................=|
  R   tech.neander                         2024-05..2026-06 0.0.4                |...............=====|
  R   org.finos.legend.sdlc                2021-08..2026-05 0.222.3              |.........===========|
    + 77 more: org.paramixel, org.javastro.vodsl, software.amazon.glue, dev.getelements.elements.crossfire, com.netgrif, org.plumelib, com.google.tsunami, org.finos.legend.depot, io.github.api-ghost-agent, org.geneweaver, cn.ashersu.omni.model, org.kie.j2cl.tools.di.ui, (+65 more)
com.fasterxml.jackson.dataformat.yaml  [fork: keep `com.fasterxml.jackson.dataformat`, `io.simpleishard` still publishes the name]
  A * com.fasterxml.jackson.dataformat     2017-10..2026-07 2.18.9               |.===================|
  ?   io.simpleishard                      2026-07..2026-07 0.30.0               |...................=|
  R   dev.skyramp                          2025-02..2026-07 1.3.34               |................====|
  R   org.apiaddicts.apitools.dosonarapi   2025-01..2026-07 1.5.0-beta-3         |................====|
  R   com.helpchoice                       2026-04..2026-06 1.2.0                |...................=|
  R   io.fabrikt                           2026-03..2026-06 27.4.2               |..................==|
    + 63 more: io.fabric8, org.apache.flink, io.telicent.jena, io.github.gw-kit, org.apache.dolphinscheduler, com.sagframe, net.corda, org.testcontainers, com.cjbooms, io.github.pavan2504, io.github.rohitect, org.apache.plc4x, (+51 more)
com.fasterxml.jackson.jaxrs.json  [fork: keep `com.fasterxml.jackson.jaxrs`, `io.simpleishard` still publishes the name]
  A * com.fasterxml.jackson.jaxrs          2017-10..2026-07 2.18.9               |.===================|
  ?   io.simpleishard                      2026-07..2026-07 0.30.0               |...................=|
  R   ai.askamerica                        2026-05..2026-07 0.31.0               |...................=|
  R   org.lance                            2025-12..2026-07 0.6.1                |..................==|
  R   org.apache.pulsar                    2024-09..2026-07 4.2.3                |...............=====|
  R   org.apache.phoenix                   2026-05..2026-06 5.3.2                |...................=|
    + 54 more: com.ascentstream.pulsar, org.apache.hbase.thirdparty, com.alibaba.ververica, org.apache.pinot, org.apache.hudi, dev.henneberger, io.github.giis-uniovi, org.apache.flink, org.devlive.connector, org.apache.seatunnel, org.onebusaway, tech.leovan.hive, (+42 more)
org.slf4j  [fork: keep `org.slf4j`, `net.unit8.enkan` still publishes the name]
  A * org.slf4j                            2017-04..2026-05 2.0.18               |====================|
  R   net.unit8.enkan                      2026-03..2026-07 0.15.1               |..................==|
  R   ml.combust.mleap                     2023-11..2026-07 0.25.1               |..............======|
  R   io.testomat                          2025-10..2026-07 0.16.0               |.................===|
  ?   io.github.gokcesahinnn               2026-07..2026-07 1.0.0                |...................=|
  R   com.arcadedb                         2022-11..2026-07 26.7.2               |............========|
    + 317 more: org.apache.iotdb, org.craftercms.mariaDB4j, com.launchdarkly, org.eclipse.jetty.ee8, org.eclipse.jetty.ee10, org.eclipse.jetty.ee9, io.euhedral-execution, ch.exense.step, com.google.appengine, org.open-metadata, net.openhft, org.opensearch, (+305 more)
org.apache.arrow.memory.core  [fork: keep `org.apache.arrow`, `io.mishmash.stacks.patches` still publishes the name]
  ? * org.apache.arrow                     2024-04..2026-03 19.0.0               |..............=====.|
  ?   io.mishmash.stacks.patches           2026-03..2026-07 19.0.0-mmio.1.1      |..................==|
org.apache.arrow.flight.core  [fork: keep `org.apache.arrow`, `io.mishmash.stacks.patches` still publishes the name]
  ? * org.apache.arrow                     2024-04..2026-03 19.0.0               |..............=====.|
  ?   io.mishmash.stacks.patches           2026-03..2026-07 19.0.0-mmio.1.1      |..................==|
org.apache.arrow.memory.unsafe  [fork: keep `org.apache.arrow`, `io.mishmash.stacks.patches` still publishes the name]
  ? * org.apache.arrow                     2024-04..2026-03 19.0.0               |..............=====.|
  ?   io.mishmash.stacks.patches           2026-03..2026-07 19.0.0-mmio.1.1      |..................==|
org.apache.arrow.format  [fork: keep `org.apache.arrow`, `io.mishmash.stacks.patches` still publishes the name]
  ? * org.apache.arrow                     2024-04..2026-03 19.0.0               |..............=====.|
  ?   io.mishmash.stacks.patches           2026-03..2026-07 19.0.0-mmio.1.1      |..................==|
org.apache.arrow.vector  [fork: keep `org.apache.arrow`, `io.mishmash.stacks.patches` still publishes the name]
  A * org.apache.arrow                     2024-04..2026-03 19.0.0               |..............=====.|
  ?   io.mishmash.stacks.patches           2026-03..2026-07 19.0.0-mmio.1.1      |..................==|
  ?   io.indextables                       2026-07..2026-07 0.6.0-rc2_spark_4.1.2 |...................=|
  A   org.apache.pinot                     2025-09..2025-09 1.4.0                |.................=..|
  R   com.salesforce.datacloud             2025-05..2025-08 0.34.0               |.................=..|
org.jetbrains.annotations  [fork: keep `org.jetbrains`, `io.deephaven` still publishes the name]
  A * org.jetbrains                        2018-09..2026-02 26.1.0               |...================.|
  R   io.deephaven                         2025-09..2026-07 42.1                 |.................===|
  R   com.kinetica                         2025-09..2026-07 7.2.3.20             |.................===|
  R   systems.manifold                     2023-08..2026-07 2026.1.7             |.............=======|
  ?   io.split                             2026-06..2026-06 1.0.0-beta1          |...................=|
  R   beer.devs                            2025-02..2026-06 1.4.20               |................====|
    + 75 more: com.microsoft.azure.kusto, com.qcloud.cos, io.github.alepandocr, io.github.nbauma109, io.github.happybavarian07, org.jam4s, io.streamthoughts, org.tallison.tika, me.bechberger, me.bechberger.jfr, org.f14a, tech.guilhermekaua.spigot-boot, (+63 more)
tools.jackson.core  [fork: keep `tools.jackson.core`, `com.phonepe.sentinel-ai` still publishes the name]
  A * tools.jackson.core                   2025-03..2026-07 3.1.5                |................====|
  ?   com.phonepe.sentinel-ai              2026-06..2026-07 1.2.0-alpha2         |...................=|
  ?   com.ibm.jsonata4java                 2026-07..2026-07 2.6.4                |...................=|
  R   ru.tinkoff.piapi                     2026-01..2026-07 1.49.2               |..................==|
  ?   fish.payara.tools                    2026-06..2026-06 1.0.0-Alpha4         |...................=|
  ?   com.playtika.reactivefeign           2026-06..2026-06 5.0.0                |...................=|
    + 22 more: software.xdev.mockserver, org.pragmatica-lite.aether, org.apache.cayenne, io.github.ignf, media.barney, org.sonarsource.sonarlint.ls, io.github.tansuasici, com.limemojito.oss.standards, com.limemojito.oss.standards.development-test, io.sapl, com.io7m.montarre, io.github.shangtx, (+10 more)
io.netty.tcnative.classes.openssl  [fork: keep `io.netty`, `io.vertx` still publishes the name]
  A * io.netty                             2022-03..2026-06 2.0.80.Final         |..........==========|
  ?   io.vertx                             2026-06..2026-07 4.5.29               |...................=|
  ?   io.fabric8                           2026-06..2026-06 7.8.0                |...................=|
  R   org.neo4j.driver                     2024-11..2026-06 4.4.26               |................====|
  R   io.kestra.plugin                     2024-10..2025-06 0.23.0               |...............===..|
  R   eu.michael-simons.neo4j              2024-10..2025-06 2.17.4               |................==..|
com.clickhouse.jdbc  [fork: keep `com.clickhouse`, `io.github.tridog` still publishes the name]
  A * com.clickhouse                       2021-12..2026-07 0.10.0-rc1           |..........==========|
  ?   io.github.tridog                     2026-07..2026-07 0.7.3-2              |...................=|
  R   org.apache.seatunnel                 2023-10..2024-10 1.0.2                |.............====...|
  R   io.kestra.plugin                     2022-04..2023-03 0.6.1                |..........===.......|
  R   ru.yandex.clickhouse                 2021-12..2021-12 0.3.2                |..........=.........|
com.nimbusds.jose.jwt  [fork: keep `com.nimbusds`, `com.vaadin` still publishes the name]
  A * com.nimbusds                         2020-08..2026-05 10.9.1               |.......=============|
  R   com.vaadin                           2025-07..2026-07 2.13.6               |.................===|
  ?   org.ligoj.plugin                     2026-07..2026-07 2.0.0                |...................=|
  R   fish.payara.security.connectors      2024-05..2026-04 2.9.0                |...............=====|
  R   org.bonitasoft.connectors            2026-04..2026-04 1.0.0-beta.1         |..................=.|
  R   org.apache.hadoop                    2026-03..2026-03 3.5.0                |..................=.|
    + 6 more: com.waveinformatica.skysso, io.github.swiyu-admin-ch, io.okdp, org.project-kessel, com.liferay, com.thetransactioncompany
com.zaxxer.hikari  [fork: keep `com.zaxxer`, `org.kill-bill.billing` still publishes the name]
  A * com.zaxxer                           2018-01..2026-06 7.1.0                |..==================|
  R   org.kill-bill.billing                2020-09..2026-07 0.24.19              |.......=============|
  R   org.finos.legend.depot               2025-06..2026-07 2.94.0               |.................===|
  R   io.github.kaleert                    2026-01..2026-06 1.2.0                |..................==|
  R   org.apache.hudi                      2023-02..2026-06 0.14.2               |............========|
  R   org.apache.dolphinscheduler          2025-03..2026-05 3.4.2                |................====|
    + 73 more: io.github.deathgod7, io.higson, org.apache.seatunnel, org.quickfixj, com.scalar-labs, org.apache.flink, com.aliyun.schedulerx, org.testingisdocumenting.webtau, it.unibz.inf.ontop, org.finos.legend.shared, cn.qaiu, work.noice, (+61 more)
org.graalvm.truffle  [fork: keep `org.graalvm.truffle`, `com.arcadedb` still publishes the name]
  A * org.graalvm.truffle                  2018-10..2026-06 25.1.3               |...=================|
  R   com.arcadedb                         2025-12..2026-07 26.7.2               |..................==|
  R   com.liquibase.ext                    2025-09..2026-07 5.2.1                |.................===|
  ?   ai.looktech                          2026-06..2026-07 2.6.1-looktech.2     |...................=|
  R   com.walmartlabs.concord.runtime.v1   2026-05..2026-07 2.42.1               |...................=|
  R   com.walmartlabs.concord              2026-05..2026-07 2.42.1               |...................=|
    + 27 more: com.walmartlabs.concord.runtime.v2, io.knish, com.walmartlabs.concord.k8s, io.hyperfoil.tools, org.opensearch.migrations.trafficcapture, sh.oso, org.mitre.synthea, com.molo17.gluesync.alpha, tools.dscode, ch.zizka.jbake, com.dbvis, io.camunda.connectors.community, (+15 more)
io.netty.transport  [fork: keep `io.netty`, `com.arcadedb` still publishes the name]
  A * io.netty                             2017-12..2026-07 4.1.136.Final        |..==================|
  R   com.arcadedb                         2025-04..2026-07 26.7.2               |.................===|
  ?   io.github.qbsstg                     2026-06..2026-06 0.17.0               |...................=|
  R   io.github.lukaszsamson               2026-04..2026-04 0.1.0                |..................=.|
  R   com.sportradar.unifiedodds.sdk       2026-02..2026-03 5.0.0-rc4            |..................=.|
  R   io.karatelabs                        2025-10..2025-11 1.5.2                |..................=.|
org.jsoup  [fork: keep `org.jsoup`, `com.sonatype.clm` still publishes the name]
  A * org.jsoup                            2018-04..2026-04 1.22.2               |..==================|
  R   com.sonatype.clm                     2026-04..2026-07 3.0.10-01            |...................=|
  R   org.graylog2                         2025-07..2026-07 7.0.10               |.................===|
  R   org.finos.legend.sdlc                2026-04..2026-07 0.226.3              |...................=|
  R   org.spdx                             2025-10..2026-06 2.0.7                |..................==|
  R   org.scala-sbt                        2024-12..2026-06 2.0.1                |................====|
    + 35 more: io.get-coursier, software.amazon.jdbc, org.jboss.pnc.bacon, org.apache.flink, io.github.padreati, org.kie.j2cl.tools.di.ui, io.github.searchable-io, com.qainsights, org.testingisdocumenting.znai, com.github.tsantalis, org.apache.tika, io.yupiik.maven, (+23 more)
com.fasterxml.jackson.module.jaxb  [fork: keep `com.fasterxml.jackson.module`, `com.liquibase` still publishes the name]
  A * com.fasterxml.jackson.module         2017-10..2026-07 2.18.9               |.===================|
  ?   com.liquibase                        2026-06..2026-07 5.2.1                |...................=|
  R   com.solacecoe.connectors             2024-07..2026-07 3.1.7                |...............=====|
  R   io.github.solven-eu.cleanthat        2025-07..2026-06 2.26                 |.................===|
  ?   org.apache.gravitino                 2026-06..2026-06 1.3.0                |...................=|
  R   com.datastax.oss                     2021-06..2026-06 6.0.11               |.........===========|
    + 92 more: io.cdap.cdap, org.apache.dolphinscheduler, com.facebook.presto.spark, com.rovio.ingest, org.apache.seatunnel, org.apache.pulsar, com.ascentstream.pulsar, com.oceanbase, io.github.dodogeny, com.seeq, io.streamnative.connectors, org.apache.phoenix, (+80 more)
org.apache.commons.pool2  [fork: keep `org.apache.commons`, `com.liquibase.ext` still publishes the name]
  A * org.apache.commons                   2020-07..2025-12 2.13.1               |.......============.|
  ?   com.liquibase.ext                    2026-06..2026-07 5.2.1                |...................=|
  R   org.openjproxy                       2026-03..2026-07 0.5.2-beta           |..................==|
  R   org.apache.directory.api             2023-10..2026-05 2.1.8                |.............=======|
  R   io.github.caobahuong                 2026-05..2026-05 0.1.1                |...................=|
  R   org.apache.druid.extensions.contrib  2024-06..2026-04 37.0.0               |...............=====|
    + 5 more: com.redis, org.noear, io.github.hexsook, org.apache.storm, com.vlkan.log4j2
io.netty.codec.http  [fork: keep `io.netty`, `com.liquibase.ext` still publishes the name]
  A * io.netty                             2017-12..2026-07 4.1.136.Final        |..==================|
  ?   com.liquibase.ext                    2026-07..2026-07 5.2.1                |...................=|
  R   dev.zio                              2025-05..2026-06 3.11.3               |.................===|
  ?   io.camunda.connector                 2026-06..2026-06 8.7.22               |...................=|
  R   com.amazonaws                        2026-02..2026-06 2026.24.1            |..................==|
  ?   org.apache.flink                     2026-06..2026-06 2.3.0                |...................=|
    + 5 more: com.xuxueli, org.apache.tika, org.eclipse.ditto, io.sapl, de.fraunhofer.iosb.ilt.faaast.service
org.checkerframework.checker.qual  [fork: keep `org.checkerframework`, `io.boxlang` still publishes the name]
  A * org.checkerframework                 2018-08..2026-07 4.2.1                |...=================|
  R   io.boxlang                           2026-01..2026-07 1.15.0               |..................==|
  R   com.google.cloud                     2024-06..2026-07 3.44.0               |...............=====|
  ?   com.silanis.esl                      2026-07..2026-07 11.69.0              |...................=|
  R   com.webforj                          2026-04..2026-06 26.01                |...................=|
  R   com.facebook.business.sdk            2025-02..2026-06 25.0.3               |................====|
    + 29 more: io.joynr.java.core, io.github.imonja, com.daml, io.github.eisop, org.eclipse.hawkbit, org.jetbrains.kotlinx, com.jcabi, org.opencastproject, org.orbisgis.geoclimate, io.github.mscheong01, io.vitess, org.apache.pekko, (+17 more)
org.signal.libsignal  [fork: keep `org.signal`, `com.securegroupchat` still publishes the name]
  A * org.signal                           2023-09..2025-11 0.86.5               |.............======.|
  ?   com.securegroupchat                  2026-07..2026-07 0.96.3               |...................=|
  R   io.github.wanggenlin                 2026-02..2026-06 0.81.2               |..................==|
com.github.luben.zstd_jni  [fork: keep `com.github.luben`, `com.aliyun.openservices.eas` still publishes the name]
  A * com.github.luben                     2018-06..2026-06 1.5.7-11             |...=================|
  R   com.aliyun.openservices.eas          2024-06..2026-07 2.0.32               |...............=====|
  R   org.apache.iotdb                     2024-11..2026-07 2.0.10               |................====|
  ?   com.timecho.timechodb                2026-06..2026-06 2.0.10.1             |...................=|
  R   com.snowflake                        2024-12..2026-06 3.5.4                |................====|
  R   org.apache.tsfile                    2024-11..2026-05 2.3.1                |................====|
    + 13 more: ai.h2o, com.timecho.iotdb, org.chipsalliance, org.apache.celeborn, io.moderne, io.spicelabs, io.nosqlbench, io.github.willena, io.github.fernandolopes, org.apache.amoro, io.kroxylicious, io.github.azagniotov, (+1 more)
fr.lirmm.integraal.rule_analysis  [fork: keep `fr.lirmm.graphik`, `fr.inria.rules` still publishes the name]
  ? * fr.lirmm.graphik                     2023-11..2025-06 2.0.7                |..............====..|
  ?   fr.inria.rules                       2026-06..2026-07 3.2.1                |...................=|
jakarta.validation  [fork: keep `jakarta.validation`, `dev.getelements.elements` still publishes the name]
  A * jakarta.validation                   2020-02..2025-10 4.0.0-M1             |......=============.|
  R   dev.getelements.elements             2025-03..2026-07 3.8.8                |................====|
  ?   com.meta-analyzer                    2026-06..2026-06 1.0.0                |...................=|
  R   io.flux-capacitor                    2023-05..2024-06 0.943.0              |.............===....|
  R   org.pipservices                      2024-06..2024-06 1.0.0                |...............=....|
  R   no.nav.security                      2023-04..2023-11 3.2.0                |............===.....|
    + 2 more: com.neko233, com.guicedee.services
com.fasterxml.aalto  [fork: keep `com.fasterxml`, `org.jetbrains.kotlin` still publishes the name]
  A * com.fasterxml                        2018-04..2026-05 1.4.0                |..==================|
  ?   org.jetbrains.kotlin                 2023-12..2026-07 2.4.10-RC2           |..............======|
  R   org.jetbrains.dokka                  2022-06..2023-03 1.8.10               |...........==.......|
org.tukaani.xz  [fork: keep `org.tukaani`, `org.sonarsource.javascript` still publishes the name]
  A * org.tukaani                          2018-01..2026-03 1.12                 |..=================.|
  R   org.sonarsource.javascript           2023-09..2026-07 13.2.0.43154         |.............=======|
  R   org.incenp                           2024-07..2026-06 0.2.2                |...............=====|
  ?   com.timecho.timechodb                2026-06..2026-06 2.0.10.1             |...................=|
  ?   io.github.peterdowdy                 2026-06..2026-06 0.0.0-main-ad87994   |...................=|
  R   io.anserini                          2022-01..2026-06 2.2.0                |..........==========|
    + 23 more: org.apache.syncope.fit, de.m3y.parquet, com.timecho.iotdb, com.sonatype.clm, net.neoforged.installertools, org.apache.iotdb, io.archivesunleashed, org.apache.parquet, io.github.seabow, org.apache.inlong, io.kestra.plugin, com.github.samtools, (+11 more)
org.apache.commons.beanutils2  [fork: keep `org.apache.commons`, `org.onebusaway` still publishes the name]
  ? * org.apache.commons                   2024-12..2025-05 2.0.0-M2             |................==..|
  ?   org.onebusaway                       2025-05..2026-07 14.2.0               |.................===|
  ?   com.github.bordertech.wcomponents    2025-12..2026-01 1.5.39               |..................=.|
com.github.benmanes.caffeine  [fork: keep `com.github.ben-manes.caffeine`, `com.janeluo` still publishes the name]
  A * com.github.ben-manes.caffeine        2017-12..2026-05 3.2.4                |..==================|
  R   com.janeluo                          2026-03..2026-07 1.0.4                |..................==|
  ?   org.kill-bill.billing                2026-06..2026-06 0.42.0               |...................=|
  R   io.zeebe.redis                       2025-05..2026-06 8.9.1                |.................===|
  R   com.google.errorprone                2022-04..2026-06 2.50.0               |..........==========|
  R   io.pebbletemplates                   2025-12..2026-05 4.1.2                |..................==|
    + 16 more: net.wirelabs, org.apache.tinkerpop, nl.basjes.parse.useragent, org.openprovenance.prov, org.odftoolkit, io.tileverse.pmtiles, org.opengis.cite, nl.goodbytes.xmpp.xep, org.igniterealtime.whack, be.vlaanderen.informatievlaanderen.ldes.ldio, com.aerospike, com.gitlab.cdc-java.office, (+4 more)
jakarta.ws.rs  [fork: keep `jakarta.ws.rs`, `com.exasol` still publishes the name]
  A * jakarta.ws.rs                        2020-02..2024-04 4.0.0                |......=========.....|
  ?   com.exasol                           2026-06..2026-07 5.7.3                |...................=|
  R   io.github.tblsoft.solr               2025-07..2026-06 4.7                  |.................===|
  R   nl.mirila.cli                        2025-11..2026-06 3.15.0               |..................==|
  R   org.jboss.narayana.lra               2024-12..2026-05 1.2.0.Final          |................====|
  R   com.inteligr8.activiti               2024-10..2026-03 1.3.0-aps-v26.1      |...............====.|
    + 14 more: com.affinidi.tdk, com.bluecirclesoft.open, me.chrissw-r1, com.biit-solutions, com.github.xeroapi, com.liferay, no.telenor.sdk, org.opengis.cite, com.documents4j, com.github.estuaryoss, com.jcabi, com.datadoghq, (+2 more)
org.springdoc.openapi.ai.common  [fork: keep `org.springdoc`, `io.github.vpelikh` still publishes the name]
  ? * org.springdoc                        2026-04..2026-04 3.0.3                |..................=.|
  ?   io.github.vpelikh                    2026-06..2026-07 5.0.0                |...................=|
org.springdoc.openapi.common  [fork: keep `org.springdoc`, `io.github.vpelikh` still publishes the name]
  A * org.springdoc                        2020-12..2026-04 2.8.17               |........===========.|
  ?   io.github.vpelikh                    2026-06..2026-07 5.0.0                |...................=|
  R   io.github.lisi9988                   2025-07..2025-12 2.8.14               |.................==.|
org.springdoc.openapi.webflux.core  [fork: keep `org.springdoc`, `io.github.vpelikh` still publishes the name]
  A * org.springdoc                        2020-12..2026-04 2.8.17               |........===========.|
  ?   io.github.vpelikh                    2026-06..2026-07 5.0.0                |...................=|
  R   io.github.lisi9988                   2025-08..2025-12 2.8.14               |.................==.|
org.springdoc.openapi.webflux.scalar  [fork: keep `org.springdoc`, `io.github.vpelikh` still publishes the name]
  A * org.springdoc                        2025-09..2026-04 2.8.17               |.................==.|
  ?   io.github.vpelikh                    2026-06..2026-07 5.0.0                |...................=|
  R   io.github.lisi9988                   2025-12..2025-12 2.8.14               |..................=.|
org.springdoc.openapi.webflux.ui  [fork: keep `org.springdoc`, `io.github.vpelikh` still publishes the name]
  A * org.springdoc                        2020-12..2026-04 2.8.17               |........===========.|
  ?   io.github.vpelikh                    2026-06..2026-07 5.0.0                |...................=|
  R   io.github.lisi9988                   2025-08..2025-12 2.8.14               |.................==.|
org.springdoc.openapi.webmvc.ai  [fork: keep `org.springdoc`, `io.github.vpelikh` still publishes the name]
  ? * org.springdoc                        2026-04..2026-04 3.0.3                |..................=.|
  ?   io.github.vpelikh                    2026-06..2026-07 5.0.0                |...................=|
org.springdoc.openapi.webmvc.core  [fork: keep `org.springdoc`, `io.github.vpelikh` still publishes the name]
  A * org.springdoc                        2020-12..2026-04 2.8.17               |........===========.|
  ?   io.github.vpelikh                    2026-06..2026-07 5.0.0                |...................=|
  R   io.github.lisi9988                   2025-07..2025-12 2.8.14               |.................==.|
org.springdoc.openapi.webmvc.scalar  [fork: keep `org.springdoc`, `io.github.vpelikh` still publishes the name]
  A * org.springdoc                        2025-09..2026-04 2.8.17               |.................==.|
  ?   io.github.vpelikh                    2026-06..2026-07 5.0.0                |...................=|
  R   io.github.lisi9988                   2025-12..2025-12 2.8.14               |..................=.|
org.springdoc.openapi.ui  [fork: keep `org.springdoc`, `io.github.vpelikh` still publishes the name]
  A * org.springdoc                        2020-12..2026-04 2.8.17               |........===========.|
  ?   io.github.vpelikh                    2026-06..2026-07 5.0.0                |...................=|
  R   io.github.lisi9988                   2025-07..2025-12 2.8.14               |.................==.|
org.springdoc.openapi.webflux.ai  [fork: keep `org.springdoc`, `io.github.vpelikh` still publishes the name]
  ? * org.springdoc                        2026-04..2026-04 3.0.3                |..................=.|
  ?   io.github.vpelikh                    2026-06..2026-07 5.0.0                |...................=|
io.opentelemetry.instrumentation_annotations  [fork: keep `io.opentelemetry.instrumentation`, `io.vidocq.humboldt` still publishes the name]
  ? * io.opentelemetry.instrumentation     2023-10..2026-06 2.29.0               |.............=======|
  ?   io.vidocq.humboldt                   2026-06..2026-07 0.2.0                |...................=|
org.eclipse.microprofile.config  [fork: keep `org.eclipse.microprofile.config`, `io.vidocq.ravel` still publishes the name]
  ? * org.eclipse.microprofile.config      2026-01..2026-04 3.1.1                |..................==|
  ?   io.vidocq.ravel                      2026-05..2026-07 0.2.0                |...................=|
com.azure.storage.common  [fork: keep `com.azure`, `org.gaul` still publishes the name]
  A * com.azure                            2019-09..2026-06 12.34.0              |.....===============|
  R   org.gaul                             2025-11..2026-07 3.3.0                |..................==|
  ?   org.tomitribe.s3proxy                2026-06..2026-06 3.0.1                |...................=|
org.kotlincrypto.hash.sha2  [fork: keep `org.kotlincrypto.hash`, `io.github.zzzyyylllty.sertraline` still publishes the name]
  ? * org.kotlincrypto.hash                2024-03..2025-09 0.8.0                |..............====..|
  ?   io.github.zzzyyylllty.sertraline     2026-06..2026-07 3.9.6                |...................=|
io.netty.internal.tcnative.openssl.linux.x86_64  [fork: keep `io.netty`, `org.apache.iotdb` still publishes the name]
  A * io.netty                             2022-05..2026-06 2.0.80.Final         |...........=========|
  R   org.apache.iotdb                     2026-04..2026-07 2.0.10               |..................==|
  R   com.azure.cosmos.spark               2026-02..2026-07 4.49.1               |..................==|
  ?   io.camunda.connector                 2026-06..2026-06 8.7.22               |...................=|
  R   io.smallrye                          2026-04..2026-04 0.1.4                |...................=|
  R   io.zipkin.dependencies               2026-03..2026-03 3.2.2                |..................=.|
    + 5 more: com.danielflower.apprunner, io.karatelabs, io.opentelemetry.javaagent, com.github.emc-mongoose, io.servicetalk
com.azure.json  [fork: keep `com.azure`, `io.indextables` still publishes the name]
  A * com.azure                            2022-09..2026-01 1.5.1                |...........========.|
  ?   io.indextables                       2026-07..2026-07 0.6.0-rc2_spark_4.0.3 |...................=|
  R   com.microsoft.azure.kusto            2026-04..2026-05 7.0.8                |...................=|
net.bytebuddy.agent  [fork: keep `net.bytebuddy`, `co.hyperprobe` still publishes the name]
  A * net.bytebuddy                        2017-05..2026-07 1.18.11              |.===================|
  ?   co.hyperprobe                        2026-07..2026-07 1.2.19               |...................=|
  R   com.google.gerrit                    2020-02..2026-06 3.13.7               |......==============|
  R   co.elastic.apm                       2019-02..2026-06 1.56.0               |....================|
  R   com.macstab.chaos.jvm                2026-04..2026-04 1.0.0                |...................=|
  R   cn.easii                             2026-04..2026-04 1.0.6                |..................=.|
    + 15 more: me.bechberger, cn.langpy, com.leanxcale, com.zto.fire, software.amazon.disco, com.nerdvision, com.idea-aedi, org.openstreetmap.atlas, com.netsensia.rivalchess, com.github.liuzhengyang, com.securenative.java, com.amazonaws, (+3 more)
org.snakeyaml.engine.v2  [fork: keep `org.snakeyaml`, `io.btrace` still publishes the name]
  A * org.snakeyaml                        2019-10..2025-07 2.10                 |.....=============..|
  ?   io.btrace                            2026-07..2026-07 0.26.2               |...................=|
  ?   com.walmartlabs.concord.k8s          2026-06..2026-07 2.42.1               |...................=|
  R   com.datadoghq                        2025-06..2026-06 1.63.2               |.................===|
  ?   org.wildfly.glow                     2024-03..2026-06 2.2.0.Final          |..............======|
  ?   org.apache.zeppelin                  2026-05..2026-05 0.12.1               |...................=|
    + 28 more: org.frankframework, io.acryl, io.fabric8, io.strimzi, io.github.phompang, eu.koboo, io.dscope.camel, org.workflomics, io.github.ethanz0x0, org.sonarsource.iac, ch.framedev, com.atlan, (+16 more)
org.apache.commons.exec  [fork: keep `org.apache.commons`, `cn.net.pap.md5.jmh` still publishes the name]
  ? * org.apache.commons                   2024-01..2025-11 1.6.0                |..............=====.|
  ?   cn.net.pap.md5.jmh                   2026-07..2026-07 0.0.4                |...................=|
  ?   com.github.zhkl0228                  2026-03..2026-03 2.0.2                |..................=.|
org.apache.commons.text  [fork: keep `org.apache.commons`, `org.bonitasoft.engine.data` still publishes the name]
  A * org.apache.commons                   2018-03..2025-12 1.15.0               |..=================.|
  R   org.bonitasoft.engine.data           2026-01..2026-06 11.1.0               |..................==|
  R   com.telamin.fluxtion                 2026-05..2026-06 1.0.9                |...................=|
  ?   net.officefloor.tutorial             2026-06..2026-06 4.0.0                |...................=|
  R   com.vmlens                           2026-01..2026-04 1.2.28               |..................=.|
  R   ru.biosoft.diagrams                  2026-01..2026-02 1.0.3                |..................=.|
    + 17 more: io.github.venkateshamurthy, dev.jbang, io.github.davidwhitlock.joy, io.github.pro4d, org.bidib.com.github.markusbernhardt, fr.lirmm.graphik, io.github.noeltoy, io.github.mderevyankoaqa, com.salesforce.functions, org.opendaylight.aaa, org.zowe.client.java.sdk, com.jkoolcloud.tnt4j.streams, (+5 more)
com.jtconnors.socket  [fork: keep `com.jtconnors.socket`, `io.github.jtconnors` still publishes the name]
  A * com.jtconnors.socket                 2019-02..2019-02 11.0.1               |....=...............|
  ?   io.github.jtconnors                  2026-06..2026-06 21.0.0               |...................=|
  A   com.jtconnors                        2019-02..2019-02 11.0.3               |....=...............|
de.agilecoders.wicket.webjars  [fork: keep `de.agilecoders.wicket.webjars`, `io.github.arieslab` still publishes the name]
  ? * de.agilecoders.wicket.webjars        2023-10..2025-12 4.0.14               |..............=====.|
  ?   io.github.arieslab                   2026-06..2026-06 2.4.2                |...................=|
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
jakarta.inject  [fork: keep `jakarta.inject`, `org.openidentityplatform.openig` still publishes the name]
  A * jakarta.inject                       2020-04..2021-10 2.0.1                |.......===..........|
  ?   org.openidentityplatform.openig      2026-06..2026-06 6.1.1                |...................=|
  ?   org.kill-bill.billing                2026-06..2026-06 0.42.0               |...................=|
  R   com.google.gerrit                    2023-10..2026-06 3.13.7               |..............======|
  R   io.joynr.java.core                   2026-01..2026-06 1.24.8               |..................==|
  R   dev.getelements.elements             2025-04..2026-03 3.6.32               |.................==.|
    + 13 more: io.github.jolt-community.jolt, it.netgrid, network.sloud.hytale, com.uchicom, com.google.tsunami, io.github.avistotelecom, org.apache.opennlp, com.google.template, io.github.openfeign.querydsl, org.reploop, com.guicedee.services, io.github.jbock-java, (+1 more)
io.github.humbleui.skija.android.arm64  [fork: keep `io.github.humbleui`, `com.behemiron.engine` still publishes the name]
  ? * io.github.humbleui                   2026-01..2026-06 0.143.17             |..................==|
  ?   com.behemiron.engine                 2026-06..2026-06 0.143.17             |...................=|
io.github.humbleui.skija.android.x64  [fork: keep `io.github.humbleui`, `com.behemiron.engine` still publishes the name]
  ? * io.github.humbleui                   2026-01..2026-06 0.143.17             |..................==|
  ?   com.behemiron.engine                 2026-06..2026-06 0.143.17             |...................=|
io.github.humbleui.skija.linux.arm64  [fork: keep `io.github.humbleui`, `com.behemiron.engine` still publishes the name]
  ? * io.github.humbleui                   2025-11..2026-06 0.143.17             |..................==|
  ?   com.behemiron.engine                 2026-06..2026-06 0.143.17             |...................=|
io.github.humbleui.skija.windows.arm64  [fork: keep `io.github.humbleui`, `com.behemiron.engine` still publishes the name]
  ? * io.github.humbleui                   2026-05..2026-06 0.143.17             |...................=|
  ?   com.behemiron.engine                 2026-06..2026-06 0.143.17             |...................=|
com.github.snksoft.crc  [fork: keep `com.github.snksoft`, `org.jurr.java.omniusb` still publishes the name]
  ? * com.github.snksoft                   2022-11..2022-11 1.1.0                |............=.......|
  ?   org.jurr.java.omniusb                2026-06..2026-06 1.0.1                |...................=|
org.apache.commons.configuration2  [fork: keep `org.apache.commons`, `org.wso2.orbit.org.apache.commons` still publishes the name]
  A * org.apache.commons                   2017-10..2026-05 2.15.1               |.===================|
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
  ? * org.apache.jena                      2018-06..2025-10 5.6.0                |...===============..|
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
  R   io.quarkus                           2024-10..2025-02 3.18.4               |...............==...|
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
  ? * org.apache.jena                      2018-06..2025-10 5.6.0                |...===============..|
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
org.apache.commons.collections4  [fork: keep `org.apache.commons`, `com.guicedee.modules.services` still publishes the name]
  A * org.apache.commons                   2018-07..2025-04 4.5.0                |...===============..|
  R   com.guicedee.modules.services        2026-04..2026-06 2.1.0                |...................=|
  ?   org.apache.directory.api             2026-05..2026-05 2.1.8                |...................=|
  R   io.github.qudtlib                    2024-12..2025-10 7.1.1                |................==..|
  R   de.jball                             2025-07..2025-07 0.9.0                |.................=..|
  R   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17        |......=====.........|
    + 1 more: com.jwebmp.jpms.commons
org.osgi.framework  [fork: keep `org.osgi`, `org.apache.karaf` still publishes the name]
  ? * org.osgi                             2020-12..2020-12 1.10.0               |........=...........|
  ?   org.apache.karaf                     2025-08..2026-04 4.4.11               |.................===|
org.apache.commons.fileupload2.jakarta.servlet6  [fork: keep `org.apache.commons`, `io.github.dhruvrawatdev` still publishes the name]
  ? * org.apache.commons                   2023-12..2026-02 2.0.0-M5             |..............=====.|
  R   io.github.dhruvrawatdev              2026-04..2026-04 1.0.1                |...................=|
  R   cloud.piranha.dist                   2024-07..2025-06 25.6.0               |...............===..|
  R   io.telicent.smart-caches.graph       2024-06..2024-12 0.82.14              |...............==...|
  A   org.apache.jena                      2024-03..2024-07 5.1.0                |..............==....|
  R   io.telicent                          2024-06..2024-06 1.2.1                |...............=....|
org.hsqldb  [fork: keep `org.hsqldb`, `com.github.massamany` still publishes the name]
  A * org.hsqldb                           2021-04..2024-11 2.7.4                |........=========...|
  R   com.github.massamany                 2025-08..2025-09 1.2.3                |.................=..|
  ?   ch.zizka.csvcruncher                 2021-11..2023-09 2.7.0                |..........====......|
  R   org.lucee                            2023-06..2023-06 2.7.2.jdk11          |.............=......|
org.apache.commons.vfs2  [fork: keep `org.apache.commons`, `io.kestra.plugin` still publishes the name]
  ? * org.apache.commons                   2025-02..2025-02 2.10.0               |................=...|
  ?   io.kestra.plugin                     2025-04..2025-08 0.24.0               |................==..|
org.apache.commons.fileupload2.jakarta  [fork: keep `org.apache.commons`, `com.svenruppert` still publishes the name]
  ? * org.apache.commons                   2023-07..2023-07 2.0.0-M1             |.............=......|
  R   com.svenruppert                      2025-01..2025-01 03.00.01             |................=...|
  A   org.apache.jena                      2024-02..2024-02 5.0.0-rc1            |..............=.....|
cafe.cryptography.curve25519_elisabeth  [fork: keep `cafe.cryptography`, `com.weavechain` still publishes the name]
  ? * cafe.cryptography                    2019-05..2019-05 0.1.0                |.....=..............|
  ?   com.weavechain                       2023-06..2023-08 0.1.5                |.............=......|
```

## shaded (31)

The natural-namespace owner is the earliest and most-recent publisher; every other group merely shades or bundles the name. Resolution is unchanged; this just records the decision so the module drops off the report.

- The owner is also the most-recent publisher (there is no later successor).
- The owner is the closest groupId to the module name: it shares the longest leading-segment prefix (hyphens ignored), even if the name is not strictly under it.
- Allow the natural owner; reject every group that merely shades the name.

| count | current owner | new owner(s) |
|---:|---|---|
| 1 | `io.github.pdvrieze.xmlutil` | `io.github.pdvrieze.xmlutil, io.github.pdvrieze` |
| 1 | `org.apache.logging.log4j` | `org.apache.logging.log4j, org.apache.meecrowave, org.apache.storm, org.apache.hadoop, org.apache.pulsar, org.apache.hbase.operator.tools, org.apache.pinot, org.apache.flink, org.apache.jena, org.apache.tika, org.apache.calcite.avatica, org.apache.seatunnel, org.apache.servicemix.bundles, org.apache.bahir, org.apache.inlong, org.apache.hudi, org.apache.knox, org.apache.kylin, org.apache.doris, org.apache.hive` |
| 1 | `org.apache.logging.log4j` | `org.apache.logging.log4j, org.apache.tika` |
| 1 | `org.neo4j.bolt` | `org.neo4j.bolt, org.neo4j.connectors` |

```
org.bouncycastle.pkix  [owned by `org.bouncycastle`; 63 other group(s) shade the name]
  A * org.bouncycastle                     2018-07..2026-07 1.85                 |...=================|
  ?   com.datarobot                        2026-07..2026-07 11.2.42              |...................=|
  R   com.exasol                           2024-03..2026-06 26.2.8               |..............======|
  R   com.github.melin                     2026-04..2026-06 1.0.3                |...................=|
  R   de.tk.opensource                     2020-10..2026-06 1.2.3                |.......=============|
  R   com.alibaba.ververica                2022-10..2026-06 1.20-vvr-11.5.1-jdk11 |...........=========|
    + 58 more: org.apache.pinot, com.github.toolarium, io.streamnative.connectors, org.hyperledger.fabric, org.finos.legend.engine, org.apache.inlong, io.kestra.plugin, org.jetbrains, org.lucee, com.nhn.gameanvil, io.sermant, com.linecorp.armeria, (+46 more)
org.bouncycastle.provider  [owned by `org.bouncycastle`; 88 other group(s) shade the name]
  A * org.bouncycastle                     2018-07..2026-07 1.85                 |...=================|
  R   org.wso2.charon                      2019-04..2026-06 5.1.5                |....================|
  ?   io.gitee.maluole                     2026-06..2026-06 1.2.7.RELEASE        |...................=|
  R   org.dcache                           2025-12..2026-06 3.4.3                |..................==|
  R   org.apache.pinot                     2025-02..2026-06 1.5.1                |................====|
  R   org.openeuler                        2021-11..2026-06 2.0.4                |..........==========|
    + 83 more: org.apache.dolphinscheduler, org.terracotta, org.exploit, de.moritzpetersen, org.apache.seatunnel, de.splatgames.aether.pack, net.maritimeconnectivity.pki, io.github.swiyu-admin-ch, org.hyperledger.fabric, io.kestra.storage, io.aiven, io.kestra.plugin, (+71 more)
org.seleniumhq.selenium.grid  [owned by `org.seleniumhq.selenium`; 1 other group(s) shade the name]
  ? * org.seleniumhq.selenium              2019-09..2026-07 4.46.0               |.....===============|
  ?   com.infotel.seleniumRobot            2022-09..2022-09 5.0.4                |...........=........|
io.opentelemetry.context  [owned by `io.opentelemetry`; 1 other group(s) shade the name]
  ? * io.opentelemetry                     2020-11..2026-07 1.64.0               |........============|
  ?   io.vidocq.humboldt                   2026-06..2026-07 0.2.0                |...................=|
io.opentelemetry.api  [owned by `io.opentelemetry`; 1 other group(s) shade the name]
  ? * io.opentelemetry                     2020-03..2026-07 1.64.0               |......==============|
  ?   io.vidocq.humboldt                   2026-06..2026-07 0.2.0                |...................=|
com.headius.invokebinder  [owned by `com.headius`; 7 other group(s) shade the name]
  A * com.headius                          2017-10..2026-07 1.16                 |.===================|
  R   org.sahli.asciidoc.confluence.publisher 2026-03..2026-06 0.34.0               |..................==|
  R   org.springframework.cloud            2024-11..2026-06 4.3.4                |................====|
  R   com.lealceldeiro                     2025-02..2026-03 2.3.1                |................===.|
  R   ch.ifocusit.livingdoc                2025-05..2025-05 2.16                 |.................=..|
  R   de.jcup.asp                          2021-09..2022-04 1.4.1                |.........===........|
    + 2 more: org.asciidoctor, org.jruby
io.vertx.core  [owned by `io.vertx`; 3 other group(s) shade the name]
  A * io.vertx                             2020-05..2026-07 4.5.29               |.......=============|
  ?   io.sirix                             2026-06..2026-07 1.0.0-beta5          |...................=|
  R   io.github.crac.io.vertx              2023-08..2024-09 4.4.6.CRAC.0         |.............===....|
  R   one.gfw                              2023-03..2023-03 4.4.0                |............=.......|
io.vertx.auth.common  [owned by `io.vertx`; 1 other group(s) shade the name]
  ? * io.vertx                             2020-05..2026-07 4.5.29               |.......=============|
  ?   ai.tock                              2026-07..2026-07 26.3.3               |...................=|
ch.qos.logback.core  [owned by `ch.qos.logback`; 34 other group(s) shade the name]
  A * ch.qos.logback                       2018-01..2026-07 1.5.38               |..==================|
  R   com.yetanalytics                     2025-05..2026-07 0.0.5                |.................===|
  ?   ch.exense.step                       2026-07..2026-07 3.30.1               |...................=|
  ?   org.eclipse.hawkbit                  2026-06..2026-07 1.1.0                |...................=|
  R   io.camunda                           2026-02..2026-06 0.2.5                |..................==|
  R   com.deltaproto                       2026-04..2026-06 1.1.7                |...................=|
    + 29 more: de.gematik.test, io.smallrye.reactive, org.ton.ton4j, org.springframework.cloud, com.limemojito.oss.aws, com.effacy.jui, io.spicelabs, com.expediagroup, org.chenile, club.dawdler, org.jetbrains.kotlinx, io.github.neodix42, (+17 more)
io.github.pdvrieze.testutil  [owned by `io.github.pdvrieze.xmlutil`; 0 other group(s) shade the name]
  ? * io.github.pdvrieze.xmlutil           2025-07..2026-07 1.0.1                |.................===|
  ?   io.github.pdvrieze                   2026-06..2026-06 1.0.0-rc3            |...................=|
io.netty.handler  [owned by `io.netty`; 10 other group(s) shade the name]
  A * io.netty                             2017-12..2026-07 4.1.136.Final        |..==================|
  R   eu.michael-simons.neo4j              2025-07..2026-06 4.1.2                |.................===|
  ?   io.camunda.connector                 2026-06..2026-06 8.7.22               |...................=|
  R   org.apache.flink                     2026-01..2026-06 2.3.0                |..................==|
  ?   org.openidentityplatform.opendj      2026-06..2026-06 5.1.1                |...................=|
  R   org.apache.tika                      2025-04..2026-05 3.3.1                |.................===|
    + 5 more: org.apache.storm, io.github.ousatov-ua, io.kestra.plugin, org.lucee, com.luhuiguo.netty
io.netty.handler.proxy  [owned by `io.netty`; 11 other group(s) shade the name]
  A * io.netty                             2017-12..2026-07 4.1.136.Final        |..==================|
  R   io.micronaut.starter                 2025-06..2026-07 4.10.17              |.................===|
  R   io.kestra                            2025-08..2026-07 1.0.50               |.................===|
  R   org.apache.grails                    2026-05..2026-07 8.0.0-M2             |...................=|
  ?   org.apache.gravitino                 2026-06..2026-06 1.3.0                |...................=|
  ?   io.neonbee                           2026-06..2026-06 0.37.30              |...................=|
    + 6 more: com.facebook.presto, io.sirix, org.apache.iceberg, io.kestra.plugin, com.frog-development.consul-populate, io.kestra.storage
io.netty.codec.http2  [owned by `io.netty`; 16 other group(s) shade the name]
  A * io.netty                             2017-12..2026-07 4.1.136.Final        |..==================|
  R   com.applitools                       2026-05..2026-07 5.87.9               |...................=|
  ?   io.camunda.connector                 2026-06..2026-06 8.7.22               |...................=|
  ?   org.apache.gravitino                 2026-06..2026-06 1.3.0                |...................=|
  R   org.apache.spark                     2025-10..2026-05 4.1.2                |..................==|
  R   org.apache.iceberg                   2026-05..2026-05 1.11.0               |...................=|
    + 11 more: org.eclipse.ditto, io.github.jdbc-armour, io.github.cstopyak, it.neckar.open, net.xdob.ratly, io.micronaut.testresources, com.exactpro.th2, io.etcd, io.kestra.storage, org.wiremock, io.github.sunny-chung
io.netty.transport.epoll.linux.x86_64  [owned by `io.netty`; 4 other group(s) shade the name]
  A * io.netty                             2022-05..2026-07 4.1.136.Final        |...........=========|
  ?   io.camunda.connector                 2026-06..2026-06 8.7.22               |...................=|
  R   org.apache.storm                     2025-05..2026-05 2.8.8                |.................===|
  R   com.atscale.opensource               2026-01..2026-01 1.14                 |..................=.|
  R   org.readutf.orchestrator             2025-02..2025-02 2.0.0                |................=...|
org.eclipse.jetty.security  [owned by `org.eclipse.jetty`; 3 other group(s) shade the name]
  A * org.eclipse.jetty                    2018-11..2026-07 12.0.37              |....================|
  ?   ch.exense.step                       2026-06..2026-07 3.30.1               |...................=|
  R   org.sonatype.nexus.common.components 2026-02..2026-07 3.90.4-01            |..................==|
  R   org.sonatype.nexus.jetty             2025-09..2026-01 3.87.2-01            |.................==.|
com.fasterxml.jackson.jakarta.rs.json  [owned by `com.fasterxml.jackson.jakarta.rs`; 18 other group(s) shade the name]
  A * com.fasterxml.jackson.jakarta.rs     2021-07..2026-07 2.18.9               |.........===========|
  R   ch.exense.step                       2022-10..2026-07 3.30.1               |...........=========|
  ?   com.inteligr8.activiti               2026-06..2026-06 1.4.1-aps-v25.3      |...................=|
  R   org.apache.tika                      2023-12..2026-05 3.3.1                |..............======|
  R   com.phonepe.sentinel-ai              2026-05..2026-05 1.1.2-SOLARIS-rc0    |...................=|
  R   ch.exense.step.library               2023-08..2026-05 1.0.31               |.............=======|
    + 13 more: org.eclipse.tractusx.edc, org.ow2.petals.samples.rest.edm, dev.getelements.elements, org.eclipse.edc.huawei, org.eclipse.edc.aws, org.eclipse.edc, io.nflow, com.brightsparklabs, io.trino.gateway, com.snehasishroy, com.smoketurner.dropwizard, org.kiwiproject, (+1 more)
com.fasterxml.jackson.datatype.joda  [owned by `com.fasterxml.jackson.datatype`; 4 other group(s) shade the name]
  A * com.fasterxml.jackson.datatype       2017-10..2026-07 2.18.9               |.===================|
  R   io.kestra.plugin                     2024-06..2024-08 0.18.1               |...............=....|
  R   org.apache.beam                      2022-05..2023-05 2.48.0               |...........===......|
  ?   io.siddhi                            2022-11..2023-02 5.1.28               |............=.......|
  R   com.seeq                             2021-12..2022-08 55.4.9-v202208021422 |..........==........|
com.fasterxml.jackson.datatype.jdk8  [owned by `com.fasterxml.jackson.datatype`; 36 other group(s) shade the name]
  A * com.fasterxml.jackson.datatype       2017-10..2026-07 2.18.9               |.===================|
  ?   org.octopusden.octopus.automation.cve 2026-06..2026-06 2.0.2                |...................=|
  R   io.github.unmeshjoshi                2025-10..2026-06 0.1.0-alpha.29       |.................===|
  R   org.ic4j                             2026-03..2026-05 0.8.2                |..................==|
  R   com.networknt                        2022-08..2026-05 2.3.4                |...........=========|
  R   org.apache.grails                    2025-06..2026-04 7.1.0                |.................==.|
    + 31 more: io.github.tansuasici, ai.onehouse, com.atlan, io.github.demiourgoi, io.kestra.plugin, com.tencent.cloud, hu.webarticum.miniconnect, io.openlineage, de.m3y.parquet, io.edurt.datacap, org.apache.parquet, io.opentelemetry.javaagent, (+19 more)
com.fasterxml.jackson.module.paramnames  [owned by `com.fasterxml.jackson.module`; 11 other group(s) shade the name]
  A * com.fasterxml.jackson.module         2017-10..2026-07 2.18.9               |.===================|
  ?   org.realityforge.proton              2026-06..2026-07 0.74                 |...................=|
  ?   org.realityforge.sting               2026-06..2026-06 0.39                 |...................=|
  ?   org.realityforge.router.fu           2026-06..2026-06 0.47                 |...................=|
  ?   org.realityforge.react4j             2026-06..2026-06 0.226                |...................=|
  ?   org.realityforge.arez                2026-06..2026-06 0.249                |...................=|
    + 6 more: com.infobip, io.kestra, com.araksis, com.araksis.sjd, io.github.codgen, io.micronaut.example
org.eclipse.jetty.client  [owned by `org.eclipse.jetty`; 2 other group(s) shade the name]
  A * org.eclipse.jetty                    2018-11..2026-07 12.0.37              |....================|
  ?   ch.exense.step                       2026-06..2026-07 3.30.1               |...................=|
  R   org.exploit                          2024-10..2026-04 1.0.9                |................===.|
org.apache.logging.log4j  [owned by `org.apache.logging.log4j`; 313 other group(s) shade the name]
  A * org.apache.logging.log4j             2017-11..2026-07 2.25.5               |..==================|
  ?   io.kroxylicious                      2026-07..2026-07 0.22.0               |...................=|
  R   org.lucee                            2021-12..2026-06 2.26.0.0             |..........==========|
  R   com.ibm.galasa                       2026-02..2026-06 1.1.9                |..................==|
  ?   com.nqadmin.swingset.demo            2026-06..2026-06 4.0.13               |...................=|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
    + 327 more: org.apache.hudi, com.alibaba.ververica, io.github.zhouzhoucoder, org.beilstein, io.github.beehive-lab, io.github.uwegeercken, org.hpccsystems, org.into-cps.maestro, com.adobe.campaign.tests.bridge.service, io.camunda, com.robotaccomplice, dev.mauch, (+315 more)
org.apache.logging.log4j.core  [owned by `org.apache.logging.log4j`; 23 other group(s) shade the name]
  A * org.apache.logging.log4j             2017-11..2026-07 2.25.5               |..==================|
  R   org.lucee                            2022-03..2026-06 2.26.0.0             |..........==========|
  ?   nl.tno.org.portico                   2026-05..2026-05 2.1.3                |...................=|
  R   app.freerouting                      2026-05..2026-05 2.2.4                |...................=|
  R   org.beilstein                        2026-05..2026-05 1.1.2                |...................=|
  R   me.bechberger                        2026-03..2026-04 0.1.6                |..................==|
    + 19 more: io.openems, com.kount, com.github.aquality-automation, com.ghgande, com.github.bilderherunterlader, com.gemecosystem.gemjar, io.github.alien-tools, com.webforj, io.github.egonw, com.liferay, de.fraunhofer.iem, net.maizegenetics, (+7 more)
net.bytebuddy  [owned by `net.bytebuddy`; 131 other group(s) shade the name]
  A * net.bytebuddy                        2017-05..2026-07 1.18.11              |.===================|
  ?   com.mysticalrzc                      2026-06..2026-07 0.0.3                |...................=|
  ?   io.github.praveenkpandu              2026-06..2026-06 0.1.0-alpha          |...................=|
  R   de.gematik.test                      2024-08..2026-06 4.3.2                |...............=====|
  ?   com.logitags                         2026-06..2026-06 2.3                  |...................=|
  R   com.jcabi                            2025-11..2026-05 1.9.0                |..................==|
    + 126 more: org.lucee, io.github.lucientong, dev.jorel, io.github.rocketbunny727, io.github.smallfast, net.aivory, ai.superstream, io.github.mlanett, com.appland, io.github.jlapugot.chronoguard, io.github.quiethappiness, com.graphql-java, (+114 more)
org.mavai.punit.examples  [owned by `org.mavai`; 1 other group(s) shade the name]
  ? * org.mavai                            2026-05..2026-07 0.7.1                |...................=|
  ?   org.javai                            2026-05..2026-05 0.6.99               |...................=|
com.microsoft.onnxruntime  [owned by `com.microsoft.onnxruntime`; 1 other group(s) shade the name]
  ? * com.microsoft.onnxruntime            2020-06..2026-06 1.27.0               |.......=============|
  ?   io.github.eduramiba                  2026-06..2026-06 1.26.0               |...................=|
io.github.humbleui.skija.linux.x64  [owned by `io.github.humbleui`; 1 other group(s) shade the name]
  ? * io.github.humbleui                   2022-12..2026-06 0.119.6              |............========|
  ?   com.behemiron.engine                 2026-06..2026-06 0.143.17             |...................=|
io.github.humbleui.skija.windows.x64  [owned by `io.github.humbleui`; 1 other group(s) shade the name]
  ? * io.github.humbleui                   2022-12..2026-06 0.119.6              |............========|
  ?   com.behemiron.engine                 2026-06..2026-06 0.143.17             |...................=|
org.apache.commons.jexl3  [owned by `org.apache.commons`; 1 other group(s) shade the name]
  ? * org.apache.commons                   2024-06..2026-06 3.7.0                |...............=====|
  ?   org.hotrodorm.hotrod                 2025-07..2026-05 5.1.23               |.................===|
org.eclipse.jgit.pgm  [owned by `org.eclipse.jgit`; 1 other group(s) shade the name]
  ? * org.eclipse.jgit                     2017-12..2026-06 7.7.0.202606012155-r |..==================|
  ?   org.openl.jgit                       2022-01..2023-12 6.8.0.202311291450-openl |..........=====.....|
com.azure.http.netty  [owned by `com.azure`; 1 other group(s) shade the name]
  ? * com.azure                            2019-11..2026-06 1.16.5               |......==============|
  ?   io.lakefs                            2026-06..2026-06 0.24.1               |...................=|
org.neo4j.bolt.connection.routed  [owned by `org.neo4j.bolt`; 0 other group(s) shade the name]
  ? * org.neo4j.bolt                       2025-03..2026-06 12.0.0               |................====|
  ?   org.neo4j.connectors                 2026-06..2026-06 6.0.0-RC01-s_2.13    |...................=|
```

## tld-dropped (2)

The dominant owner's groupId with its top-level domain dropped is the module-name prefix.

- The owner's groupId with its first segment (the top-level domain) removed is a prefix of the module name.
- Allow that owner; reject the rest.

```
roaringbitmap  [owned by `org.roaringbitmap` (groupId minus TLD is the module prefix); 3 other group(s) shade the name]
  A * org.roaringbitmap                    2023-09..2026-07 1.6.15               |.............=======|
  ?   com.atomgraph.etl.csv                2026-06..2026-07 2.2.1                |...................=|
  R   org.apache.celeborn                  2024-06..2026-04 0.6.3                |...............=====|
  R   org.bitlap                           2023-10..2023-10 1.0.1.0              |..............=.....|
osgi.core  [owned by `org.osgi` (groupId minus TLD is the module prefix); 2 other group(s) shade the name]
  ? * org.osgi                             2020-12..2020-12 8.0.0                |........=...........|
  A   org.apache.karaf                     2022-04..2025-01 4.4.7                |..........=======...|
  A   org.apache.felix.atomos              2021-02..2021-02 8.0.0                |........=...........|
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
  ? * com.fasterxml.jackson.datatype       2019-07..2026-07 2.18.9               |.....===============|
  ?   tools.jackson.datatype               2025-03..2026-07 3.1.5                |................====|
dagger  [owned by `com.google.dagger` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * com.google.dagger                    2021-06..2026-07 2.60.1               |.........===========|
  ?   io.github.licy5352.dagger            2022-02..2026-03 2.55-kim-rc1         |..........=========.|
  ?   me.gulya.dagger                      2025-08..2025-08 2.56.2-workaround10  |.................=..|
  ?   io.github.jbock-java                 2021-10..2022-03 2.41.2               |.........==.........|
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
  ?   io.github.tri5m                      2024-12..2026-04 1.0.6                |................===.|
tucache.spring.boot.autoconfigure  [owned by `co.tunan.tucache` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * co.tunan.tucache                     2024-02..2024-02 1.0.4.RELEASE        |..............=.....|
  ?   io.github.tri5m                      2024-12..2026-04 1.0.6                |................===.|
tucache.spring.boot.starter  [owned by `co.tunan.tucache` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * co.tunan.tucache                     2024-02..2024-02 1.0.4.RELEASE        |..............=.....|
  ?   io.github.tri5m                      2024-12..2026-04 1.0.6                |................===.|
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
  ?   io.valkey                            2024-07..2025-10 2.1.1                |...............===..|
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
  ? * io.github.stasgora                   2019-04..2019-09 1.0.3                |....==..............|
  ?   dev.sgora                            2019-10..2019-10 1.0.3.1              |.....=..............|
```

## unclassified (263)

Multiple publishers with no natural-namespace owner present: a genuine collision the heuristic cannot settle.

- More than one publisher, and none is a credible owner: no natural-namespace owner is present and the earliest is not the closest groupId.
- Left unresolved - no owners.tsv is written - for a later hand decision.

_Showing the 200 most recently active of 263. For the full list, emit the SetOwners file: `-Djenesis.crawler.drift.emit=unclassified`._

```
jcef  [no clear owner; `me.friwi` is earliest and most recent]
  ? * me.friwi                             2021-12..2026-05 jcef-d3de827+cef-146.0.10+g8219561+chromium-146.0.7680.179 |..........==========|
  ?   io.github.trethore                   2026-02..2026-07 jcef-3bb487f+cef-146.0.10+g8219561+chromium-146.0.7680.179 |..................==|
VirtualizedFX  [no clear owner; `io.github.palexdev` is earliest and most recent]
  ? * io.github.palexdev                   2022-03..2026-07 25.2.3               |..........==========|
  ?   org.glavo.materialfx                 2022-04..2022-04 11.2.6               |..........=.........|
org.apache.commons.io  [no clear owner; `commons-io` is earliest and most recent]
  ? * commons-io                           2017-10..2026-04 2.22.0               |.===================|
  ?   org.sonarsource.python               2024-10..2026-07 5.25.0.34794         |...............=====|
  ?   com.datarobot                        2024-10..2026-07 11.2.42              |................====|
  ?   io.streamnative                      2025-06..2026-07 4.17.3.4             |.................===|
  ?   eu.rekawek.coffeegb                  2025-08..2026-07 1.7.3                |.................===|
  ?   no.entur                             2024-03..2026-07 1.125.0              |..............======|
    + 94 more: io.boxlang, org.sonarsource.flex, io.prophecy, com.jfrog, io.github.liquid-java, org.apache.flink, org.apache.distributedlog, org.apache.dolphinscheduler, com.github.cafdataprocessing.workers.languagedetection, org.teavm, org.apache.hudi, org.apache.tika, (+82 more)
bus.starter  [no clear owner; `org.miaixz` is earliest and most recent]
  ? * org.miaixz                           2025-05..2026-07 8.8.3                |.................===|
  ?   io.github.rassafel                   2025-07..2025-07 0.0.1                |.................=..|
org.apache.commons.codec  [no clear owner; `commons-codec` is earliest and most recent]
  ? * commons-codec                        2017-10..2026-04 1.22.0               |.===================|
  ?   software.amazon.awssdk               2024-07..2026-07 2.47.5               |...............=====|
  ?   com.liquibase.ext.vaults             2026-06..2026-07 5.2.1                |...................=|
  ?   com.mirakl                           2025-11..2026-07 10.11.0              |..................==|
  ?   io.gitlab.cupofcode                  2026-05..2026-07 1.2.14               |...................=|
  ?   cn.ctyun                             2025-10..2026-07 2.0.0                |.................===|
    + 77 more: org.operaton.bpm.extension, com.ibm.cloud, com.republicate.modality, org.boostscale, com.alibaba.ververica, org.apache.pinot, ai.platon.gora, org.apache.tika, com.suprsend, io.github.rsv-code, com.gitee.melin, org.apache.druid.extensions.contrib, (+65 more)
com.jcraft.jsch  [no clear owner; `com.github.mwiede` is earliest and most recent]
  ? * com.github.mwiede                    2021-08..2026-07 2.28.4               |.........===========|
  ?   com.pesitwizard.connector            2026-02..2026-02 1.2.1                |..................=.|
  ?   io.github.luigidemasi                2026-01..2026-01 2.27.7               |..................=.|
  ?   com.opendatadsl                      2025-08..2026-01 1.1.29               |.................==.|
  ?   com.testingbot                       2025-08..2025-08 4.3                  |.................=..|
  ?   io.kestra.plugin                     2024-04..2025-03 0.20.1               |..............===...|
    + 1 more: com.jcabi
java.xml.bind  [no clear owner; `javax.xml.bind` is earliest and most recent]
  ? * javax.xml.bind                       2017-07..2018-09 2.3.1                |.===................|
  ?   com.yahoo.vespa                      2020-05..2026-07 8.722.24             |.......=============|
  ?   de.fraunhofer.iosb.ilt.FROST-Server  2024-08..2026-07 2.6.4                |...............=====|
  ?   org.kendar.protocol                  2026-02..2026-06 4.3.10-tpm           |..................==|
  ?   org.verapdf.apps                     2019-06..2026-06 1.30.2               |.....===============|
  ?   org.apache.flink                     2020-02..2026-06 2.3.0                |......==============|
    + 184 more: org.apache.pinot, org.apache.paimon, io.mosip.mock.sdk, org.apache.tika, de.fraunhofer.iosb.ilt, org.metricshub, io.mosip.esignet, org.wso2.msf4j.perftest.echo, org.wso2.msf4j, org.wso2.msf4j.samples, org.wso2.msf4j.sample, org.apache.fluss, (+172 more)
jdk.internal.vm.compiler  [no clear owner; `org.graalvm.compiler` is earliest and most recent]
  ? * org.graalvm.compiler                 2018-10..2026-04 23.0.12              |...=================|
  ?   io.vertx                             2022-11..2026-07 4.5.29               |............========|
  ?   org.linuxforhealth.fhir              2022-08..2022-12 5.1.1                |...........==.......|
jakarta.security.auth.message  [no clear owner; `jakarta.authentication` is earliest and most recent]
  ? * jakarta.authentication               2020-11..2024-05 3.1.0                |........========....|
  ?   org.apache.tomcat                    2020-11..2026-07 11.0.24              |........============|
org.dnsjava  [no clear owner; `dnsjava` is earliest and most recent]
  ? * dnsjava                              2019-05..2026-05 3.6.5                |.....===============|
  ?   org.apache.beam                      2025-01..2026-07 2.75.0               |................====|
  ?   ai.platon.gora                       2026-06..2026-07 1.0.9-slim           |...................=|
  ?   org.apache.phoenix                   2025-09..2026-06 5.3.2                |.................===|
  ?   io.github.littleproxy                2024-09..2026-06 2.8.0                |...............=====|
  ?   com.alibaba.polardbx                 2025-05..2026-06 2.2.15               |.................===|
    + 17 more: io.jikkou, org.apache.pinot, org.apache.hbase, org.apache.paimon, de.m3y.hadoop.hdfs.hfsa, com.hazelcast.jet, com.helger.peppol.mcp, org.apache.atlas, com.clickzetta, com.foilen, org.apache.kudu, dev.redcoke, (+5 more)
java.servlet.jsp  [no clear owner; `org.apache.tomcat` is earliest and most recent]
  ? * org.apache.tomcat                    2020-11..2026-07 9.0.120              |........============|
  ?   com.heroku                           2024-05..2026-04 9.0.117.0            |...............=====|
java.servlet  [no clear owner; `jakarta.servlet` is earliest and most recent]
  ? * jakarta.servlet                      2019-08..2020-07 5.0.0-M2             |.....===............|
  ?   org.apache.tomcat                    2020-11..2026-07 9.0.120              |........============|
  ?   org.apache.felix                     2022-02..2022-10 2.1.0                |..........===.......|
  ?   com.guicedee.services                2020-05..2022-02 1.2.2.1-jre17        |.......====.........|
  ?   org.jboss.spec.javax.servlet         2019-08..2019-09 2.0.0.Final          |.....=..............|
java.security.auth.message  [no clear owner; `jakarta.security.auth.message` is earliest and most recent]
  ? * jakarta.security.auth.message        2018-12..2020-02 2.0.0-RC1            |....===.............|
  ?   org.apache.tomcat                    2020-11..2026-07 9.0.120              |........============|
  ?   org.jboss.spec.javax.security.auth.message 2019-08..2019-09 2.0.1.Final          |.....=..............|
java.el  [no clear owner; `org.apache.tomcat` is earliest and most recent]
  ? * org.apache.tomcat                    2020-11..2026-07 9.0.120              |........============|
  ?   com.heroku                           2024-10..2024-10 9.0.96.0             |................=...|
java.annotation  [no clear owner; `javax.annotation` is earliest and most recent]
  ? * javax.annotation                     2017-09..2018-02 1.3.2                |.==.................|
  ?   org.apache.tomcat                    2020-09..2026-07 9.0.120              |.......=============|
  ?   com.heroku                           2024-10..2026-04 9.0.117.0            |................====|
  ?   one.gfw                              2023-03..2023-03 1.3.5                |............=.......|
  ?   org.rationalityfrontline.workaround  2021-02..2021-02 1.3.2-3.0.2          |........=...........|
  ?   com.guicedee.services                2019-11..2020-11 1.1.0.1-jre15        |......===...........|
    + 3 more: no.ssb.jpms, org.jboss.spec.javax.annotation, jakarta.annotation
org.apache.commons.logging  [no clear owner; `org.slf4j` is earliest and most recent]
  ? * org.slf4j                            2017-04..2026-05 2.0.18               |====================|
  ?   org.nuiton                           2026-07..2026-07 4.0-rc-1             |...................=|
  ?   org.open-metadata                    2025-11..2026-07 1.12.13              |..................==|
  ?   de.redsix                            2024-10..2026-07 1.2.9                |................====|
  ?   io.github.peterdowdy                 2026-07..2026-07 0.0.0-main-b9346fb   |...................=|
  ?   org.lucee                            2026-06..2026-06 1.4.0.0              |...................=|
    + 34 more: io.github.jinahya, org.operaton.bpm.extension, commons-logging, org.apache.tika, net.ontopia, org.jboss.pnc.build-agent, com.facebook.presto.hive, com.nordstrom.ui-tools, org.beangle.sas, io.github.linagora.linid.im, io.brunoborges, org.apache.orc, (+22 more)
org.freedesktop.dbus  [no clear owner; `com.github.hypfvieh` is earliest and most recent]
  ? * com.github.hypfvieh                  2021-03..2025-12 5.2.0                |........===========.|
  ?   org.endlesssource.mediainterface     2026-02..2026-07 3.0.0                |..................==|
lombok  [no clear owner; `org.projectlombok` is earliest and most recent]
  ? * org.projectlombok                    2018-05..2026-04 1.18.46              |...=================|
  ?   net.polyv                            2020-09..2026-07 2.2.8.1              |.......=============|
  ?   com.scanoss                          2023-06..2026-07 0.13.1               |.............=======|
  ?   io.mosip.esignet.plugin.sunbirdrc    2025-02..2026-05 1.4.0                |................====|
  ?   org.eclipse.hawkbit                  2026-03..2026-04 1.0.3                |..................=.|
  ?   io.inji.certify.sunbirdrc            2026-03..2026-03 0.6.0                |..................=.|
    + 97 more: dev.alllexey, com.huaweicloud.dws, net.wirelabs, cn.fyupeng, io.github.alllexey123, io.mosip.esignet.sunbirdrc, io.mosip.certify.sunbirdrc, io.github.opentelekomcloud, io.github.version-pulse, org.qubership.automation, io.github.devlibx.easy, org.sentrysoftware, (+85 more)
org.apache.commons.validator  [no clear owner; `commons-validator` is earliest and most recent]
  ? * commons-validator                    2023-12..2025-11 1.10.1               |..............=====.|
  ?   org.apiaddicts.apitools.dosonarapi   2026-05..2026-07 2.0.2-beta-2         |...................=|
  ?   de.knightsoft-net                    2025-07..2025-11 1.10.1-0             |.................==.|
  ?   org.chronos-eaas                     2024-07..2025-01 2.5.1                |...............==...|
com.oracle.truffle.tools.profiler  [no clear owner; `org.graalvm.tools` is earliest and most recent]
  ? * org.graalvm.tools                    2018-10..2026-06 25.1.3               |...=================|
  ?   com.orientechnologies                2025-12..2026-07 3.2.54               |..................==|
org.apache.commons.net  [no clear owner; `commons-net` is earliest and most recent]
  ? * commons-net                          2020-08..2026-03 3.13.0               |.......============.|
  ?   int.esa.ccsds.mo                     2025-05..2026-07 14.0                 |.................===|
  ?   com.nordstrom.ui-tools               2024-08..2024-08 4.23.0               |...............=....|
  ?   io.kestra.plugin                     2024-02..2024-03 0.15.1               |..............=.....|
  ?   org.apache.pinot                     2024-03..2024-03 1.1.0                |..............=.....|
  ?   com.jkoolcloud.tnt4j.streams         2023-11..2023-11 2.0.0                |..............=.....|
jpms_dss_cookbook  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_crl_parser  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_specs_saml_assertion  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2020-11..2025-11 6.2.d4j.1            |........===========.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_specs_xmlers  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2023-11..2026-07 6.5.RC1              |..............======|
  ?   org.digidoc4j.dss                    2024-02..2025-11 6.2.d4j.1            |..............=====.|
jpms_dss_utils_apache_commons  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_ws_timestamp_remote_rest_client  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2020-11..2025-11 6.2.d4j.1            |........===========.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_common_converter  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_document  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_enumerations  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_pades  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_pades_openpdf  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_pdfa  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2023-02..2026-07 6.5.RC1              |............========|
  ?   org.digidoc4j.dss                    2024-02..2025-11 6.2.d4j.1            |..............=====.|
jpms_dss_simple_certificate_report  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_specs_jws  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2021-10..2025-11 6.2.d4j.1            |.........==========.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_specs_trusted_list  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_spi  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_utils_google_guava  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_ws_certificate_validation_rest  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_ws_signature_soap  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_ws_validation_soap  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
dss_pki_factory_jaxb  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2023-11..2026-07 6.5.RC1              |..............======|
  ?   org.digidoc4j.dss                    2024-02..2025-11 6.2.d4j.1            |..............=====.|
jpms_dss_alert  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2020-11..2025-11 6.2.d4j.1            |........===========.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_asic_cades  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_common_remote_dto  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_crl_parser_stream  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_diagnostic_data  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_evidence_record_common  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2023-11..2026-07 6.5.RC1              |..............======|
  ?   org.digidoc4j.dss                    2024-02..2025-11 6.2.d4j.1            |..............=====.|
jpms_dss_evidence_record_xml  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2023-11..2026-07 6.5.RC1              |..............======|
  ?   org.digidoc4j.dss                    2024-02..2025-11 6.2.d4j.1            |..............=====.|
jpms_dss_jades  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2021-10..2025-11 6.2.d4j.1            |.........==========.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_jaxb_common  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2022-05..2025-11 6.2.d4j.1            |...........========.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_pades_pdfbox  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_pki_factory  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2023-11..2026-07 6.5.RC1              |..............======|
  ?   org.digidoc4j.dss                    2024-02..2025-11 6.2.d4j.1            |..............=====.|
jpms_dss_specs_asic_manifest  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2021-10..2025-11 6.2.d4j.1            |.........==========.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_specs_jades  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2021-10..2025-11 6.2.d4j.1            |.........==========.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_specs_xades  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_specs_xmldsig  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_ws_server_signing_common  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_ws_server_signing_dto  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_ws_server_signing_soap  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_ws_server_signing_soap_client  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_ws_signature_remote  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_ws_signature_rest  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_ws_signature_soap_client  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_ws_timestamp_dto  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2020-11..2025-11 6.2.d4j.1            |........===========.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_ws_timestamp_remote_rest  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2020-11..2025-11 6.2.d4j.1            |........===========.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_xades  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_model  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_specs_validation_report  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_test  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_tsl_validation  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_utils  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_validation  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2024-07..2026-07 6.5.RC1              |...............=====|
  ?   org.digidoc4j.dss                    2025-11..2025-11 6.2.d4j.1            |..................=.|
jpms_dss_ws_certificate_validation_rest_client  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_ws_certificate_validation_soap_client  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_ws_timestamp_remote  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2020-11..2025-11 6.2.d4j.1            |........===========.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_ws_timestamp_remote_soap_client  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2020-11..2025-11 6.2.d4j.1            |........===========.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_ws_validation_common  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_ws_validation_soap_client  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_xml_common  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2023-11..2026-07 6.5.RC1              |..............======|
  ?   org.digidoc4j.dss                    2024-02..2025-11 6.2.d4j.1            |..............=====.|
jpms_dss_asic_common  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_asic_xades  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_cades  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_crl_parser_x509crl  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_evidence_record_asn1  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2024-07..2026-07 6.5.RC1              |...............=====|
  ?   org.digidoc4j.dss                    2025-11..2025-11 6.2.d4j.1            |..................=.|
jpms_dss_i18n  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2020-11..2025-11 6.2.d4j.1            |........===========.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_jacoco_coverage  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2023-02..2026-07 6.5.RC1              |............========|
  ?   org.digidoc4j.dss                    2024-02..2025-11 6.2.d4j.1            |..............=====.|
jpms_dss_policy  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_service  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_simple_report  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_token  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_ws_certificate_validation_common  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_ws_certificate_validation_dto  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_ws_certificate_validation_soap  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_ws_server_signing_rest  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_ws_server_signing_rest_client  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_ws_signature_rest_client  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_ws_timestamp_remote_soap  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2020-11..2025-11 6.2.d4j.1            |........===========.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_ws_validation_rest  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_xml  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2023-11..2026-07 6.5.RC1              |..............======|
  ?   org.digidoc4j.dss                    2024-02..2025-11 6.2.d4j.1            |..............=====.|
jpms_dss_detailed_report  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_jaxb_parsers  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_ws_signature_dto  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_ws_validation_dto  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
jpms_dss_ws_validation_rest_client  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......=============.|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-07 6.5.RC1              |............========|
persistence.api  [no clear owner; `io.ebean` is earliest and most recent]
  ? * io.ebean                             2019-03..2026-07 3.2                  |....================|
  ?   one.gfw                              2023-03..2023-03 3.0                  |............=.......|
com.oracle.truffle.regex  [no clear owner; `org.graalvm.regex` is earliest and most recent]
  ? * org.graalvm.regex                    2018-10..2026-06 25.1.3               |...=================|
  ?   org.noear                            2024-09..2025-07 1.9.6                |...............===..|
  ?   com.syncloop.middleware              2025-01..2025-01 1.7.1                |................=...|
org.newsclub.net.unix  [no clear owner; `com.kohlschutter.junixsocket` is earliest and most recent]
  ? * com.kohlschutter.junixsocket         2018-12..2024-09 2.10.1               |....============....|
  ?   net.blahajcloud                      2026-06..2026-06 1.0                  |...................=|
  ?   org.jam4s                            2025-10..2026-06 0.7.2-M1             |.................===|
  ?   com.sbbsystems.flink                 2026-01..2026-05 3.4.3                |..................==|
  ?   net.corda                            2025-09..2026-05 4.14.2               |.................===|
  ?   io.nosqlbench                        2020-02..2020-03 3.12.47              |......=.............|
    + 1 more: io.engineblock
com.google.api.client  [no clear owner; `com.google.http-client` is earliest and most recent]
  ? * com.google.http-client               2018-10..2026-06 2.1.1                |...=================|
  ?   com.google.cloud.bigtable            2020-07..2020-11 1.17.0               |.......==...........|
org.apache.commons.beanutils  [no clear owner; `com.guicedee.services` is earliest and most recent]
  ? * com.guicedee.services                2020-06..2022-02 1.2.2.1-jre17        |.......====.........|
  ?   io.github.stoyank7                   2026-06..2026-06 1.0.2                |...................=|
  ?   org.wildfly                          2025-06..2026-06 41.0.0.Beta1         |.................===|
  ?   org.jvnet.jaxb                       2025-09..2026-06 2.0.16               |.................===|
  ?   com.guicedee.modules.services        2026-04..2026-06 2.1.0                |...................=|
  ?   com.github.bld-commons               2026-01..2026-05 3.0.19               |..................==|
    + 4 more: kg.apc, com.github.bordertech.wcomponents, commons-beanutils, org.onebusaway
org.fife.RSyntaxTextArea  [no clear owner; `com.fifesoft` is earliest and most recent]
  ? * com.fifesoft                         2022-03..2026-06 3.6.3                |..........==========|
  ?   io.github.d3af90d                    2026-06..2026-06 3.6.3-burp1          |...................=|
com.jn.langx.java8  [no clear owner; `io.github.bes2008.solution.langx` is earliest and most recent]
  ? * io.github.bes2008.solution.langx     2024-01..2026-06 5.4.6.2              |..............======|
  ?   io.github.qhsword.langx              2025-11..2025-11 5.5.10               |..................=.|
com.jn.langx.security.gm.jca.bouncycastle  [no clear owner; `io.github.bes2008.solution.langx.security` is earliest and most recent]
  ? * io.github.bes2008.solution.langx.security 2024-01..2026-06 5.4.6.2              |..............======|
  ?   io.github.qhsword.langx.security     2025-11..2025-12 5.8.0                |..................=.|
com.ctc.wstx  [no clear owner; `com.fasterxml.woodstox` is earliest and most recent]
  ? * com.fasterxml.woodstox               2018-03..2026-06 7.2.1                |..==================|
  ?   org.uma.jmetal                       2025-12..2026-06 7.4.1                |..................==|
  ?   org.bidib.jbidib                     2021-12..2026-05 2.0.44               |..........==========|
  ?   gov.nih.ncats                        2022-01..2026-03 1.0.26               |..........=========.|
  ?   org.hpccsystems                      2022-02..2026-03 9.12.94-1            |..........=========.|
  ?   com.backpackcloud                    2025-03..2026-01 2.1.0                |................===.|
    + 17 more: com.liferay.portal, de.fraunhofer.iosb.ilt.FROST-Server, com.ibm.jsonata4java, se.signatureservice.support, com.liferay, net.pincette, org.opengis.cite, org.immregistries, com.testdroid, org.sonarsource.slang, com.checkmarx, com.github.spoonlabs, (+5 more)
java.ws.rs  [no clear owner; `javax.ws.rs` is earliest and most recent]
  ? * javax.ws.rs                          2017-06..2018-08 2.1.1                |.===................|
  ?   org.apache.opennlp                   2020-07..2026-06 1.9.5                |.......=============|
  ?   org.jboss.pnc.build-agent            2021-07..2026-05 1.2.3                |.........===========|
  ?   org.apache.hadoop                    2026-03..2026-03 3.5.0                |..................=.|
  ?   net.oneandone.ioc-unit               2021-09..2025-11 2.0.51               |.........==========.|
  ?   com.scylladb                         2025-06..2025-09 1.2.6                |.................=..|
    + 54 more: org.opencb.opencga, io.streamnative, io.github.willena, com.liferay, com.inteligr8.activiti, cn.langpy, io.github.fernandolopes, com.epam.reportportal, com.github.openstack4j.core, org.moskito, com.mailintegrate, dev.parodos, (+42 more)
org.bukkit  [no clear owner; `com.uroria.curepur` is earliest and most recent]
  ? * com.uroria.curepur                   2024-07..2024-07 1.21-R0.1            |...............=....|
  ?   com.620cloud.server                  2026-06..2026-06 1.21.11-R0.1-362     |...................=|
  ?   com.mineplex.studio.server           2024-10..2026-04 26.1.2-357           |...............=====|
  ?   com.uroria.latest                    2024-07..2024-07 1.21-R0.1-2d776710d6 |...............=....|
  ?   com.uroria                           2024-07..2024-07 1.21-R0.1            |...............=....|
library  [no clear owner; `build.buf.prototype` is earliest and most recent]
  ? * build.buf.prototype                  2023-01..2023-01 v0.0.0-test0120      |............=.......|
  ?   com.connectrpc                       2023-09..2026-06 0.9.0                |.............=======|
  ?   build.buf                            2023-01..2023-09 0.1.10               |............==......|
okhttp  [no clear owner; `build.buf` is earliest and most recent]
  ? * build.buf                            2023-02..2023-09 0.1.10               |............==......|
  ?   com.connectrpc                       2023-09..2026-06 0.9.0                |.............=======|
org.scala.lang.scala3.compiler  [no clear owner; `org.scala-lang` is earliest and most recent]
  ? * org.scala-lang                       2021-06..2026-06 3.3.8                |.........===========|
  ?   com.michaelpollmeier                 2022-10..2022-11 3.2.2-RC1-bin-20221101-d84007c-NIGHTLY+1-extensible-repl |............=.......|
uk.co.spudsoft.birt.emitters.excel  [no clear owner; `io.github.reporting-solutions` is earliest and most recent]
  ? * io.github.reporting-solutions        2019-05..2026-02 4.23.0               |.....==============.|
  ?   org.eclipse.birt                     2022-05..2026-06 4.24.0               |...........=========|
flying.saucer.pdf  [no clear owner; `org.xhtmlrenderer` is earliest and most recent]
  ? * org.xhtmlrenderer                    2024-09..2026-06 10.3.0               |...............=====|
  ?   io.github.openpdfsaucer              2025-03..2025-05 2.0.9                |................==..|
org.jfree.chart  [no clear owner; `de.enflexit` is earliest and most recent]
  ? * de.enflexit                          2025-02..2025-02 1.5.6                |................=...|
  ?   io.github.jiaweim                    2026-05..2026-06 2.7.0                |...................=|
com.kingbase8.jdbc  [no clear owner; `org.jeecgframework` is earliest and most recent]
  ? * org.jeecgframework                   2024-06..2024-06 9.0.0                |...............=....|
  ?   cn.com.kingbase                      2025-04..2026-06 9.0.2.jre7           |.................===|
  ?   io.github.iscasdmo                   2026-05..2026-05 8.6.0                |...................=|
localhost3000  [no clear owner; `io.github.zahran444` is earliest and most recent]
  ? * io.github.zahran444                  2026-04..2026-05 4.0.0                |..................==|
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
  ?   org.wiremock                         2025-06..2026-04 4.0.0-beta.32        |.................==.|
  ?   org.openidentityplatform.openam.agents 2025-11..2026-03 5.0.3                |..................=.|
  ?   commons-fileupload                   2025-06..2025-06 1.6.0                |.................=..|
  ?   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17        |......=====.........|
    + 2 more: com.jwebmp.jpms.commons, com.jwebmp
aopalliance  [no clear owner; `com.jwebmp.jre11` is earliest and most recent]
  ? * com.jwebmp.jre11                     2018-12..2018-12 0.63.0.19            |....=...............|
  ?   com.guicedee.modules.services        2026-04..2026-06 2.1.0                |...................=|
  ?   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17        |......=====.........|
  ?   com.jwebmp.thirdparty                2019-04..2019-08 0.68.0.1             |....==..............|
  ?   com.jwebmp                           2019-01..2019-04 0.66.0.1             |....=...............|
info.movito.themoviedbapi  [no clear owner; `com.github.holgerbrandl` is earliest and most recent]
  ? * com.github.holgerbrandl              2023-05..2023-05 1.15                 |.............=......|
  ?   uk.co.conoregan                      2023-11..2026-05 2.6.1                |..............======|
vault.java.driver  [no clear owner; `com.bettercloud` is earliest and most recent]
  ? * com.bettercloud                      2019-06..2019-12 5.1.0                |.....==.............|
  ?   io.github.jopenlibs                  2022-10..2026-05 6.2.2                |...........=========|
  ?   io.axual.utilities.config.providers  2020-06..2024-11 1.2.0                |.......==========...|
  ?   edu.utexas.tacc.tapis                2021-10..2021-10 5.1.2                |.........==.........|
org.apache.commons.cli  [no clear owner; `commons-cli` is earliest and most recent]
  ? * commons-cli                          2023-10..2025-11 1.11.0               |..............=====.|
  ?   org.teavm                            2024-04..2026-05 0.14.1               |...............=====|
  ?   io.github.vdaburon                   2024-01..2026-05 5.1                  |..............======|
  ?   com.nanolaba                         2025-07..2026-04 1.2                  |.................===|
  ?   org.apktool                          2023-12..2026-04 3.0.2                |..............======|
  ?   com.ericsson.bss.cassandra.ecaudit   2024-08..2026-03 3.1.5                |...............====.|
    + 25 more: org.imixs.bpmn, io.github.gvergine, com.amazonaws, io.github.706412584, com.legsem.legstar, dev.walgo, org.apache.phoenix.thirdparty, org.apache.meecrowave, org.apache.james, net.thisptr, us.poliscore, com.github.oboehm, (+13 more)
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
jam.common  [no clear owner; `sk.annotation.library.jam` is earliest and most recent]
  ? * sk.annotation.library.jam            2022-01..2026-05 0.9.21               |..........==========|
  ?   sk.annotation.projects.signito       2022-12..2022-12 0.9.53               |............=.......|
org.apache.commons.dbutils  [no clear owner; `commons-dbutils` is earliest and most recent]
  ? * commons-dbutils                      2023-08..2023-09 1.8.1                |.............=......|
  ?   dev.aulait.jeg                       2024-07..2026-04 0.12                 |...............=====|
json.path  [no clear owner; `com.jayway.jsonpath` is earliest and most recent]
  ? * com.jayway.jsonpath                  2024-01..2026-02 3.0.0                |..............=====.|
  ?   org.gov4j.thirdparty.com.jayway.jsonpath 2024-12..2026-04 3.0.0-gov4j-1        |................====|
  ?   com.github.sonus21                   2025-04..2025-04 2.10.0               |.................=..|
mslinks  [no clear owner; `com.github.vatbub` is earliest and most recent]
  ? * com.github.vatbub                    2020-09..2021-07 1.0.6.2              |.......===..........|
  ?   org.jabref                           2026-02..2026-04 1.2                  |..................==|
graphql.java.tools  [no clear owner; `com.graphql-java-kickstart` is earliest and most recent]
  ? * com.graphql-java-kickstart           2023-08..2025-04 14.0.1               |.............=====..|
  ?   io.github.graphql-java-kickstart     2026-03..2026-04 14.0.2               |..................==|
org.java_websocket  [no clear owner; `org.java-websocket` is earliest and most recent]
  ? * org.java-websocket                   2023-07..2024-12 1.6.0                |.............====...|
  ?   io.github.cb-jarunmadhesh            2026-04..2026-04 1.0.0                |..................=.|
  ?   io.github.ashwithpoojary98           2026-01..2026-01 1.0.1                |..................=.|
  ?   dev.lolyay                           2025-07..2025-10 5.8.0                |.................=..|
  ?   io.kestra.plugin                     2023-09..2025-08 0.24.0               |.............=====..|
  ?   org.jetbrains.kotlinx                2025-07..2025-07 0.14.1-506           |.................=..|
    + 3 more: io.github.gubaojian, com.taosdata.jdbc, com.enixyu
tech.fortis.sandbox.api  [no clear owner; `io.github.zahran444` is earliest and most recent]
  ? * io.github.zahran444                  2026-04..2026-04 1.0.0                |..................=.|
  ?   io.sdks                              2026-04..2026-04 1.0.5                |..................=.|
com.mypayquicker.api  [no clear owner; `io.sdks` is earliest and most recent]
  ? * io.sdks                              2026-02..2026-02 1.0.3                |..................=.|
  ?   io.github.zahran444                  2026-04..2026-04 1.0.0                |..................=.|
java.xml.ws  [no clear owner; `javax.xml.ws` is earliest and most recent]
  ? * javax.xml.ws                         2017-06..2018-09 2.3.1                |.===................|
  ?   org.apache.manifoldcf                2026-04..2026-04 2.30                 |..................=.|
  ?   mx.com.sw.services                   2020-07..2024-05 1.0.19.4             |.......=========....|
  ?   org.apache.servicemix.specs          2018-11..2020-03 2.3_3                |....===.............|
  ?   com.github.pinterest                 2018-11..2020-01 0.3.0-rc.2           |....===.............|
  ?   jakarta.xml.ws                       2018-12..2020-01 2.3.3                |....===.............|
    + 1 more: com.srotya.sidewinder
net.sf.jsqlparser  [no clear owner; `com.github.jsqlparser` is earliest and most recent]
  ? * com.github.jsqlparser                2024-03..2025-05 5.3                  |..............====..|
  ?   com.manticore-projects.jsqlformatter 2025-12..2026-04 5.3.218              |..................=.|
  ?   se.alipsa                            2025-12..2025-12 1.2.0                |..................=.|
  ?   ai.starlake                          2024-09..2024-10 1.3.0                |...............==...|
com.drew.metadata  [no clear owner; `com.github.dalet-oss` is earliest and most recent]
  ? * com.github.dalet-oss                 2024-03..2024-03 0.0.6                |..............=.....|
  ?   com.drewnoakes                       2026-04..2026-04 2.20.0               |..................=.|
org.apache.commons.jxpath  [no clear owner; `commons-jxpath` is earliest and most recent]
  ? * commons-jxpath                       2025-04..2025-04 1.4.0                |................=...|
  ?   com.jd.live                          2025-08..2026-04 1.9.0                |.................==.|
commons  [no clear owner; `com.github.srujankujmar` is earliest and most recent]
  ? * com.github.srujankujmar              2020-12..2020-12 0.9.8.1              |........=...........|
  ?   io.github.rassafel                   2025-07..2026-04 0.0.4                |.................==.|
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
tornadofx  [no clear owner; `it.unibo.alchemist` is earliest and most recent]
  ? * it.unibo.alchemist                   2020-11..2020-12 9.3.0-dev218+bb50ca6a3 |........=...........|
  ?   com.googlecode.blaisemath.tornado    2023-09..2026-03 2.2.2                |.............======.|
core  [no clear owner; `pro.shuangxi.framework.openfx` is earliest and most recent]
  ? * pro.shuangxi.framework.openfx        2025-05..2025-05 1.0.0                |.................=..|
  ?   org.apereo.cas                       2025-07..2026-03 7.2.7.1              |.................==.|
com.utool  [no clear owner; `io.gitee.shallwecode` is earliest and most recent]
  ? * io.gitee.shallwecode                 2025-05..2026-03 1.5.0                |.................==.|
  ?   io.github.shallwecodex               2025-05..2025-05 1.0.2                |.................=..|
org.freedesktop.harfbuzz  [no clear owner; `io.github.jwharm.javagi` is earliest and most recent]
  ? * io.github.jwharm.javagi              2023-09..2025-05 0.12.2               |.............=====..|
  ?   org.java-gi                          2025-11..2026-02 0.14.1               |..................=.|
jcifs  [no clear owner; `org.codelibs` is earliest and most recent]
  ? * org.codelibs                         2022-04..2026-02 3.0.2                |..........=========.|
  ?   io.gitee.pickled_vegetables          2023-05..2023-05 2.2.0                |.............=......|
netty.socketio  [no clear owner; `com.corundumstudio.socketio` is earliest and most recent]
  ? * com.corundumstudio.socketio          2024-01..2026-02 2.0.14               |..............=====.|
  ?   codes.oss.socketio                   2025-04..2025-04 2.0.14               |................=...|
  ?   io.github.opensabe-tech              2024-08..2024-08 2.0.12               |...............=....|
jetty.servlet.api  [no clear owner; `org.eclipse.jetty.toolchain` is earliest and most recent]
  ? * org.eclipse.jetty.toolchain          2019-02..2026-01 4.0.9                |....===============.|
  ?   ch.reportingsoft.birt                2025-04..2025-04 4.0.6                |................=...|
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
com.luciad.imageio.webp  [no clear owner; `org.sejda.imageio` is earliest and most recent]
  ? * org.sejda.imageio                    2020-05..2020-05 0.1.6                |.......=............|
  ?   net.scenariopla.imageio              2025-08..2025-08 0.1.8                |.................=..|
  ?   com.gitee.jmash                      2024-06..2024-06 0.2.2                |...............=....|
  ?   io.github.darkxanter                 2022-10..2023-11 0.3.3                |............===.....|
  ?   org.lucee                            2022-08..2022-08 0.1.6                |...........=........|
  ?   com.github.gotson                    2021-02..2021-08 0.2.2                |........==..........|
org.eclipse.datatools.connectivity.oda.consumer  [no clear owner; `ch.reportingsoft.birt` is earliest and most recent]
  ? * ch.reportingsoft.birt                2025-04..2025-04 3.5.0                |................=...|
  ?   org.eclipse.birt.dependencies        2025-06..2025-06 3.5.0                |.................=..|
org.eclipse.datatools.connectivity.oda.design  [no clear owner; `ch.reportingsoft.birt` is earliest and most recent]
  ? * ch.reportingsoft.birt                2025-04..2025-04 3.6.0                |................=...|
  ?   org.eclipse.birt.dependencies        2025-06..2025-06 3.6.0                |.................=..|
org.eclipse.datatools.connectivity.oda.profile  [no clear owner; `ch.reportingsoft.birt` is earliest and most recent]
  ? * ch.reportingsoft.birt                2025-04..2025-04 3.5.0                |................=...|
  ?   org.eclipse.birt.dependencies        2025-06..2025-06 3.5.0                |.................=..|
org.eclipse.datatools.modelbase.sql.query  [no clear owner; `ch.reportingsoft.birt` is earliest and most recent]
  ? * ch.reportingsoft.birt                2025-04..2025-05 1.4.0.2024           |................==..|
  ?   org.eclipse.birt.dependencies        2025-06..2025-06 1.4.0                |.................=..|
org.eclipse.datatools.connectivity  [no clear owner; `ch.reportingsoft.birt` is earliest and most recent]
  ? * ch.reportingsoft.birt                2025-04..2025-04 1.15.0               |................=...|
  ?   org.eclipse.birt.dependencies        2025-06..2025-06 1.15.0               |.................=..|
org.eclipse.datatools.connectivity.apache.derby  [no clear owner; `ch.reportingsoft.birt` is earliest and most recent]
  ? * ch.reportingsoft.birt                2025-04..2025-05 1.3.0.2024           |................==..|
  ?   org.eclipse.birt.dependencies        2025-06..2025-06 1.3.0                |.................=..|
org.eclipse.datatools.connectivity.oda  [no clear owner; `ch.reportingsoft.birt` is earliest and most recent]
  ? * ch.reportingsoft.birt                2025-04..2025-04 3.7.0                |................=...|
  ?   org.eclipse.birt.dependencies        2025-06..2025-06 3.7.0                |.................=..|
org.eclipse.datatools.connectivity.oda.flatfile  [no clear owner; `ch.reportingsoft.birt` is earliest and most recent]
  ? * ch.reportingsoft.birt                2025-04..2025-04 3.4.0                |................=...|
  ?   org.eclipse.birt.dependencies        2025-06..2025-06 3.4.0                |.................=..|
org.eclipse.datatools.modelbase.dbdefinition  [no clear owner; `ch.reportingsoft.birt` is earliest and most recent]
  ? * ch.reportingsoft.birt                2025-04..2025-05 1.3.0.2024           |................==..|
  ?   org.eclipse.birt.dependencies        2025-06..2025-06 1.3.0                |.................=..|
org.eclipse.datatools.connectivity.sqm.core  [no clear owner; `ch.reportingsoft.birt` is earliest and most recent]
  ? * ch.reportingsoft.birt                2025-04..2025-05 1.6.0.2024           |................==..|
  ?   org.eclipse.birt.dependencies        2025-06..2025-06 1.6.0                |.................=..|
org.eclipse.datatools.enablement.oda.xml  [no clear owner; `ch.reportingsoft.birt` is earliest and most recent]
  ? * ch.reportingsoft.birt                2025-04..2025-04 1.6.0                |................=...|
  ?   org.eclipse.birt.dependencies        2025-06..2025-06 1.6.0                |.................=..|
org.eclipse.datatools.modelbase.derby  [no clear owner; `ch.reportingsoft.birt` is earliest and most recent]
  ? * ch.reportingsoft.birt                2025-04..2025-05 1.3.0.2024           |................==..|
  ?   org.eclipse.birt.dependencies        2025-06..2025-06 1.3.0                |.................=..|
org.eclipse.datatools.modelbase.sql  [no clear owner; `ch.reportingsoft.birt` is earliest and most recent]
  ? * ch.reportingsoft.birt                2025-04..2025-05 1.3.0.2024           |................==..|
  ?   org.eclipse.birt.dependencies        2025-06..2025-06 1.3.0                |.................=..|
jssc  [no clear owner; `net.jockx` is earliest and most recent]
  ? * net.jockx                            2021-08..2021-08 2.9.3                |.........=..........|
  ?   io.github.java-native                2021-08..2025-06 2.10.2               |.........=========..|
  ?   com.zsmartsystems.zigbee             2022-10..2024-12 1.4.16.1             |............=====...|
common  [no clear owner; `io.github.matwoess` is earliest and most recent]
  ? * io.github.matwoess                   2024-12..2025-01 0.11.3               |................=...|
  ?   pro.shuangxi.framework.openfx        2025-05..2025-05 1.0.0                |.................=..|
jpms_dss_validation_policy  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-04 6.0.1.d4j.1          |......===========...|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2025-03 6.0.1                |............=====...|
com.amazon.corretto.crypto.provider  [no clear owner; `software.amazon.cryptools` is earliest and most recent]
  ? * software.amazon.cryptools            2019-07..2025-03 2.5.0                |.....============...|
  ?   io.github.luneo7                     2022-09..2022-09 1.6.1                |...........=........|
  ?   org.jfrog.buildinfo                  2022-01..2022-01 2.33.2               |..........=.........|
MaterialFX  [no clear owner; `io.github.palexdev` is earliest and most recent]
  ? * io.github.palexdev                   2021-06..2025-02 21.18.0-alpha        |.........========...|
  ?   org.glavo.materialfx                 2022-04..2022-04 11.13.4              |..........=.........|
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

