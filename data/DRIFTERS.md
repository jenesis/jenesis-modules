# Module ownership drifters

Generated 2026-09-02. A module *drifts* when more than one groupId publishes the name and its `owners.tsv` does not yet name every publisher (no `owners.tsv`, or one that leaves some publishing groupId neither `allowed` nor `rejected`). Resolving a drift means deciding each groupId via `SetOwners` (which writes `allowed`/`rejected`); a fully-named module drops off this list.

| Category | Unresolved | Resolved via owners.tsv |
|---|---:|---:|
| explicit-rules | 46 | 661 |
| republisher | 9 | 13 |
| migration | 33 | 766 |
| fork | 144 | 310 |
| shaded | 180 | 1216 |
| tld-dropped | 17 | 69 |
| two-segments | 67 | 0 |
| unclassified | 344 | 6 |
| **total** | **840** | **3041** |

The table covers all **3881** multi-owner modules (of **38760** modules scanned).

Timeline axis spans 2017-01 .. 2026-09 (today). Per group: decision `A`=allowed `R`=rejected `?`=undecided, `*`=current owner, then the publication range, latest version, and a `=` activity bar across the axis.

## explicit-rules (46)

Hand-curated overrides: a module matching an explicit rule is assigned to a fixed owner groupId regardless of the heuristic.

- The module name equals, or falls under, a hand-curated prefix in the explicit-owner map.
- Allow every publisher whose groupId falls under the mapped owner prefix; reject all other publishers.

| count | current owner | new owner(s) |
|---:|---|---|
| 2 | `org.jetbrains.kotlinx` | `org.jetbrains.kotlinx, org.jetbrains.kotlin` |
| 1 | `org.jetbrains.kotlinx` | `org.jetbrains.kotlinx, org.jetbrains.dokka, org.jetbrains.intellij.deps.kotlinx` |
| 1 | `org.jetbrains.kotlinx` | `org.jetbrains.kotlinx, org.jetbrains.intellij.deps.kotlinx` |
| 1 | `ru.tinkoff.kora` | `ru.tinkoff.kora, io.koraframework` |
| 1 | `ru.tinkoff.kora.experimental` | `ru.tinkoff.kora.experimental, io.koraframework` |

```
com.google.gson  [explicit rule: owned by `com.google.code.gson`; 541 other group(s) rejected]
  A * com.google.code.gson                 2019-10..2026-04 2.14.0               |.....==============.|
  R   com.aliyun                           2021-09..2026-09 10.0.2               |.........===========|
  ?   com.conaxgames                       2026-06..2026-08 1.6.8                |...................=|
  ?   io.github.jpostman                   2026-07..2026-08 1.1.5                |...................=|
  R   dev.cjfravel                         2025-12..2026-08 0.1.9-beta           |..................==|
  R   com.linkedin.iceberg                 2025-11..2026-08 1.5.2.21             |.................===|
    + 536 more: dev.frontseat.maven.extensions, org.sonarsource.java, com.telesign.enterprise, org.apache.rocketmq, io.quarkus, ai.h2o, im.dart.boot, com.helger.smp-mate, org.operaton.bpm, org.sonarsource.dotnet, io.github.lambdatest, com.contentful.java, (+524 more)
kotlin.stdlib.jdk8  [explicit rule: owned by `org.jetbrains.kotlin`; 245 other group(s) rejected]
  A * org.jetbrains.kotlin                 2019-01..2026-08 2.4.20-RC2           |....================|
  ?   eu.rekawek.coffeegb                  2026-07..2026-08 2.0.8                |...................=|
  R   net.corda                            2024-07..2026-08 4.14.3               |...............=====|
  R   com.google.genai                     2026-04..2026-08 1.68.0               |..................==|
  R   ai.realitydefender                   2025-06..2026-08 0.1.9                |.................===|
  R   com.newrelic.agent.android           2024-09..2026-08 7.8.2                |...............=====|
    + 240 more: org.apache.inlong, com.squareup, com.airbnb.viaduct, cn.ctyun, com.aliyun.odps, com.huaweicloud, com.aliyun, org.octopusden.octopus.automation.teamcity, io.github.graphdsl, com.spectralogic.ds3, com.commercetools.rmf, me.bechberger, (+228 more)
com.google.common  [explicit rule: owned by `com.google.guava`; 76 other group(s) rejected]
  A * com.google.guava                     2017-07..2026-08 33.7.1-jre           |.===================|
  ?   guru.mocker.composition              2026-08..2026-08 0.1.0                |...................=|
  R   org.opendaylight.aaa                 2025-07..2026-08 0.21.6               |.................===|
  R   org.conductoross                     2026-05..2026-08 7.0.0-alpha.1        |..................==|
  R   net.sourceforge.plantuml             2025-06..2026-08 1.2026.7             |.................===|
  R   org.talend.sdk.component.sample.feature 2025-08..2026-08 1.95.0               |.................===|
    + 71 more: org.sonarsource.python, eu.rssw.sonar.openedge, org.apache.jackrabbit, io.digiexpress, io.acryl, io.axoniq.framework, io.restx, org.apache.sedona, com.google.cloud.flink, com.alibaba.ververica, org.foundationdb, org.apache.kyuubi, (+59 more)
kotlinx.collections.immutable  [explicit rule: owned by `org.jetbrains`; 1 other group(s) rejected]
  ? * org.jetbrains.kotlinx                2026-07..2026-08 0.5.2                |...................=|
  ?   org.jetbrains.kotlin                 2026-07..2026-08 2.4.20-RC2           |...................=|
  ?   com.salesforce.revoman               2026-08..2026-08 0.90.0               |...................=|
kotlin.stdlib  [explicit rule: owned by `org.jetbrains.kotlin`; 228 other group(s) rejected]
  A * org.jetbrains.kotlin                 2019-01..2026-08 2.4.20-RC2           |....================|
  R   com.rudderstack.sdk.java.analytics   2023-01..2026-08 3.1.5                |............========|
  R   net.corda                            2024-07..2026-08 4.14.3               |...............=====|
  ?   com.contentful.java                  2026-08..2026-08 10.6.1               |...................=|
  ?   com.antwerkz                         2026-08..2026-08 1.0.1                |...................=|
  ?   com.codenameone                      2026-08..2026-08 7.0.267              |...................=|
    + 223 more: se.liu.research.hefquin, dev.robocode.tankroyale, com.volcengine, com.airbnb.viaduct, com.woutwerkman.calltreevisualizer, com.tidbcloud, com.eygraber, de.darkatra.injector, com.alibaba.ververica, org.virtuslab, com.nhncloud.android, org.jetbrains.kotlinx, (+211 more)
com.google.common.util.concurrent.internal  [explicit rule: owned by `com.google.guava`; 3 other group(s) rejected]
  A * com.google.guava                     2023-10..2025-03 1.0.3                |.............====...|
  ?   org.sonarsource.python               2026-08..2026-08 5.31.0.36502         |...................=|
  ?   org.hyperledger.fabric               2026-07..2026-07 1.12.0               |...................=|
  R   de.bsommerfeld.pathetic              2026-01..2026-06 5.5.0                |..................==|
com.sun.xml.txw2  [explicit rule: owned by `org.glassfish.jaxb`; 9 other group(s) rejected]
  A * org.glassfish.jaxb                   2018-07..2026-05 4.0.9                |...================.|
  ?   ch.exense.step                       2026-08..2026-08 3.30.3               |...................=|
  R   org.uma.jmetal                       2025-12..2026-07 7.5                  |..................==|
  R   io.github.jeff-tian                  2026-02..2026-02 2.4.1                |..................=.|
  R   ai.starlake                          2022-04..2025-05 1.3.5                |..........=======...|
  R   com.jordansamhi                      2024-08..2024-08 1.1.8                |...............=....|
    + 4 more: org.soot-oss, com.yotpo, cn.lzgabel.jaxb, org.apache.servicemix.bundles
scala.library  [explicit rule: owned by `org.scala-lang`; 3 other group(s) rejected]
  A * org.scala-lang                       2018-03..2026-08 3.10.0-RC1           |..==================|
  ?   dev.propensive                       2026-08..2026-08 3.10.0-dev-p15       |...................=|
  ?   ch.epfl.lara                         2026-06..2026-07 3.10.0-RC1-bin-20260730-0cb7a4c-NIGHTLY |...................=|
  R   com.github.xuwei-k                   2021-01..2021-01 2.13.3-bin-1ca7d14   |........=...........|
imgui.app  [explicit rule: owned by `io.github.spair`; 1 other group(s) rejected]
  ? * io.github.spair                      2022-12..2026-07 1.92.7.1             |............========|
  ?   io.github.lionblazer                 2026-08..2026-08 1.92.5.1             |...................=|
kotlinx.coroutines.slf4j  [explicit rule: owned by `org.jetbrains`; 5 other group(s) rejected]
  A * org.jetbrains.kotlinx                2023-03..2026-05 1.11.0               |............=======.|
  ?   io.github.erenalpaslan               2026-08..2026-08 0.2.0                |...................=|
  A   org.jetbrains.intellij.deps.kotlinx  2025-09..2026-08 1.10.2-intellij-2    |.................===|
  R   jp.co.gahojin.thrifty                2025-02..2025-05 4.6.3                |................=...|
  R   io.github.vooft                      2024-09..2025-02 0.5.4                |...............==...|
  R   dev.suresh.kmp                       2024-06..2024-07 0.15.0               |...............=....|
    + 1 more: xyz.block
kotlin.stdlib.jdk7  [explicit rule: owned by `org.jetbrains.kotlin`; 23 other group(s) rejected]
  A * org.jetbrains.kotlin                 2019-01..2026-08 2.4.20-RC2           |....================|
  ?   net.maizegenetics                    2026-08..2026-08 5.2.98               |...................=|
  R   io.pyroscope                         2025-04..2026-08 2.9.1                |................====|
  ?   io.github.zawarka03                  2026-07..2026-07 0.1.1                |...................=|
  ?   me.bechberger                        2026-06..2026-06 0.0.8                |...................=|
  R   com.kroegerama.openapi-kgen          2023-12..2026-06 0.18.2               |..............======|
    + 18 more: org.octopusden.octopus.jira, io.github.team-sneakymouse, com.seanshubin.code.structure, me.xcue, org.partiql, io.github.wadoon.key, org.btmonier, com.slothiesmooth, com.slothiesmooth.links-detektor, hu.bme.mit.theta, com.github.shynixn.mccoroutine, dev.nelmin.spigot, (+6 more)
kotlin.reflect  [explicit rule: owned by `org.jetbrains.kotlin`; 67 other group(s) rejected]
  A * org.jetbrains.kotlin                 2019-01..2026-08 2.4.20-RC2           |....================|
  ?   com.utopia-rise                      2026-08..2026-08 1.0.0-dev3           |...................=|
  ?   org.octopusden.octopus.infrastructure 2026-07..2026-07 3.0.8                |...................=|
  ?   io.github.barqdb.kotlin              2026-07..2026-07 4.1.0                |...................=|
  R   org.apache.pinot                     2025-09..2026-06 1.5.1                |.................===|
  ?   io.github.rodrigotimoteo             2026-06..2026-06 0.1.0                |...................=|
    + 62 more: com.airbnb.viaduct, io.github.abdullahkhan118, io.github.tobi-laa, io.github.kshulzh.kefir, io.github.xilinjia.krdb, io.github.snow1026, com.browserstack, com.simprints.realm.kotlin, org.pkl-lang, com.statsig, com.infomaniak.realm.kotlin, com.solapi, (+50 more)
kotlinx.serialization.protobuf  [explicit rule: owned by `org.jetbrains`; 1 other group(s) rejected]
  A * org.jetbrains.kotlinx                2021-09..2026-04 1.11.0               |.........==========.|
  ?   org.jetbrains.kotlin                 2026-01..2026-08 2.4.20-RC2           |..................==|
  R   org.danbrough.kotlinx                2022-09..2023-03 1.5.0                |...........==.......|
org.objectweb.asm  [explicit rule: owned by `org.ow2.asm`; 166 other group(s) rejected]
  A * org.ow2.asm                          2017-07..2026-05 9.10.1               |.==================.|
  ?   io.spicelabs                         2026-06..2026-08 0.18.0               |...................=|
  R   com.github.jnr                       2019-10..2026-08 0.39.3               |.....===============|
  R   be.ugent.idlab.knows                 2025-09..2026-08 0.8.0                |.................===|
  ?   org.virtuslab                        2026-07..2026-08 0.1.0-M2             |...................=|
  R   org.tiatesting                       2024-04..2026-08 0.1.19               |..............======|
    + 161 more: org.virtuslab.scala-cli, com.datadoghq, edu.berkeley.cs.jqf, org.apache.iotdb, org.glassfish.main.extras, com.my-oli, org.teavm, io.github.mitsumi-solutions-develop, org.apache.geaflow, org.noear, net.corda, com.microsoft.azure.kusto, (+149 more)
kora.s3.client.aws  [explicit rule: owned by `ru.tinkoff.kora`, `io.koraframework`; 0 other group(s) rejected]
  ? * ru.tinkoff.kora.experimental         2025-01..2026-08 1.2.20               |................====|
  ?   io.koraframework                     2026-08..2026-08 2.0.0.RC1            |...................=|
kora.konvert.ksp.extension  [explicit rule: owned by `ru.tinkoff.kora`, `io.koraframework`; 0 other group(s) rejected]
  ? * ru.tinkoff.kora                      2026-07..2026-08 1.2.20               |...................=|
  ?   io.koraframework                     2026-08..2026-08 2.0.0.RC1            |...................=|
org.dataloader  [explicit rule: owned by `com.graphql-java`; 2 other group(s) rejected]
  A * com.graphql-java                     2022-06..2026-08 0.0.0-2026-08-20T22-16-50-747a7de |...........=========|
  ?   com.guicedee.modules.services        2026-07..2026-08 2.2.2                |...................=|
  R   com.liferay                          2025-05..2025-05 3.2.0.JAKARTA-LIFERAY-PATCHED-1 |................=...|
spring.aop  [explicit rule: owned by `org.springframework`; 3 other group(s) rejected]
  A * org.springframework                  2017-09..2026-08 7.1.0-M1             |.===================|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |...................=|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
  R   com.succsoft                         2024-12..2024-12 5.3.42               |................=...|
spring.aspects  [explicit rule: owned by `org.springframework`; 2 other group(s) rejected]
  A * org.springframework                  2017-09..2026-08 7.0.9                |.===================|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |...................=|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
spring.context  [explicit rule: owned by `org.springframework`; 5 other group(s) rejected]
  A * org.springframework                  2017-09..2026-08 7.1.0-M1             |.===================|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |...................=|
  R   org.gov4j.thirdparty.org.springframework 2024-12..2025-05 5.3.39-gov4j-2       |................=...|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
  R   com.succsoft                         2024-12..2024-12 5.3.42               |................=...|
  R   com.liferay                          2024-12..2024-12 5.3.39.LIFERAY-PATCHED-1 |................=...|
spring.context.indexer  [explicit rule: owned by `org.springframework`; 2 other group(s) rejected]
  A * org.springframework                  2017-09..2026-08 7.0.9                |.===================|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |...................=|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
spring.context.support  [explicit rule: owned by `org.springframework`; 4 other group(s) rejected]
  A * org.springframework                  2017-09..2026-08 7.0.9                |.===================|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |...................=|
  R   org.gov4j.thirdparty.org.springframework 2024-12..2025-05 5.3.39-gov4j-2       |................=...|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
  R   com.succsoft                         2024-12..2024-12 5.3.42               |................=...|
spring.expression  [explicit rule: owned by `org.springframework`; 4 other group(s) rejected]
  A * org.springframework                  2017-09..2026-08 7.1.0-M1             |.===================|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |...................=|
  R   org.gov4j.thirdparty.org.springframework 2024-12..2025-05 5.3.39-gov4j-2       |................=...|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
  R   com.succsoft                         2024-12..2024-12 5.3.42               |................=...|
spring.jms  [explicit rule: owned by `org.springframework`; 3 other group(s) rejected]
  A * org.springframework                  2017-09..2026-08 7.0.9                |.===================|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |...................=|
  R   org.gov4j.thirdparty.org.springframework 2024-12..2025-05 5.3.39-gov4j-2       |................=...|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
spring.orm  [explicit rule: owned by `org.springframework`; 3 other group(s) rejected]
  A * org.springframework                  2017-09..2026-08 7.0.9                |.===================|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |...................=|
  R   com.liferay                          2022-03..2025-05 5.3.39.LIFERAY-PATCHED-1.JAKARTA-LIFERAY-PATCHED-1 |..........=======...|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
spring.oxm  [explicit rule: owned by `org.springframework`; 3 other group(s) rejected]
  A * org.springframework                  2017-09..2026-08 7.0.9                |.===================|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |...................=|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
  R   com.succsoft                         2024-12..2024-12 5.3.42               |................=...|
spring.r2dbc  [explicit rule: owned by `org.springframework`; 2 other group(s) rejected]
  A * org.springframework                  2020-10..2026-08 7.1.0-M1             |.......=============|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |...................=|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
spring.webflux  [explicit rule: owned by `org.springframework`; 3 other group(s) rejected]
  A * org.springframework                  2017-09..2026-08 7.1.0-M1             |.===================|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |...................=|
  R   org.gov4j.thirdparty.org.springframework 2024-12..2025-05 5.3.39-gov4j-2       |................=...|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
spring.beans  [explicit rule: owned by `org.springframework`; 5 other group(s) rejected]
  A * org.springframework                  2017-09..2026-08 7.1.0-M1             |.===================|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |...................=|
  R   org.gov4j.thirdparty.org.springframework 2024-12..2025-05 5.3.39-gov4j-2       |................=...|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
  R   com.succsoft                         2024-12..2024-12 5.3.42               |................=...|
  R   com.liferay                          2023-01..2024-08 5.3.39.LIFERAY-PATCHED-1 |............====....|
spring.core  [explicit rule: owned by `org.springframework`; 4 other group(s) rejected]
  A * org.springframework                  2017-09..2026-08 7.0.9                |.===================|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |...................=|
  R   org.gov4j.thirdparty.org.springframework 2024-12..2025-05 5.3.39-gov4j-2       |................=...|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
  R   com.succsoft                         2024-12..2024-12 5.3.42               |................=...|
spring.instrument  [explicit rule: owned by `org.springframework`; 2 other group(s) rejected]
  A * org.springframework                  2017-09..2026-08 7.0.9                |.===================|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |...................=|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
spring.jdbc  [explicit rule: owned by `org.springframework`; 3 other group(s) rejected]
  A * org.springframework                  2017-09..2026-08 7.1.0-M1             |.===================|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |...................=|
  R   org.gov4j.thirdparty.org.springframework 2024-12..2025-05 5.3.39-gov4j-2       |................=...|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
spring.messaging  [explicit rule: owned by `org.springframework`; 2 other group(s) rejected]
  A * org.springframework                  2017-09..2026-08 7.0.9                |.===================|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |...................=|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
spring.test  [explicit rule: owned by `org.springframework`; 5 other group(s) rejected]
  A * org.springframework                  2017-09..2026-08 7.1.0-M1             |.===================|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |...................=|
  R   org.gov4j.thirdparty.org.springframework 2024-12..2025-05 5.3.39-gov4j-2       |................=...|
  R   com.liferay                          2025-05..2025-05 5.3.39.JAKARTA-LIFERAY-PATCHED-1 |................=...|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
  R   com.succsoft                         2024-12..2024-12 5.3.42               |................=...|
spring.tx  [explicit rule: owned by `org.springframework`; 5 other group(s) rejected]
  A * org.springframework                  2017-09..2026-08 7.0.9                |.===================|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |...................=|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
  R   com.succsoft                         2024-12..2024-12 5.3.42               |................=...|
  R   com.liferay                          2024-01..2024-08 5.3.39.LIFERAY-PATCHED-1 |..............==....|
  R   com.labun                            2020-01..2020-01 5.2.2.RELEASE.patched |......=.............|
spring.web  [explicit rule: owned by `org.springframework`; 7 other group(s) rejected]
  A * org.springframework                  2017-09..2026-08 7.0.9                |.===================|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |...................=|
  R   org.gov4j.thirdparty.org.springframework 2024-12..2025-05 5.3.39-gov4j-2       |................=...|
  R   com.liferay                          2025-05..2025-05 5.3.39.JAKARTA-LIFERAY-PATCHED-1 |................=...|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
  R   com.succsoft                         2024-12..2024-12 5.3.42               |................=...|
    + 2 more: io.github.redteamobile, io.github.tfedyanin.springframework
spring.webmvc  [explicit rule: owned by `org.springframework`; 4 other group(s) rejected]
  A * org.springframework                  2017-09..2026-08 7.0.9                |.===================|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |...................=|
  R   org.gov4j.thirdparty.org.springframework 2024-12..2025-05 5.3.39-gov4j-2       |................=...|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
  R   com.succsoft                         2024-12..2024-12 5.3.42               |................=...|
spring.websocket  [explicit rule: owned by `org.springframework`; 3 other group(s) rejected]
  A * org.springframework                  2017-09..2026-08 7.0.9                |.===================|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |...................=|
  R   org.gov4j.thirdparty.org.springframework 2024-12..2025-05 5.3.39-gov4j-2       |................=...|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
reactor.core  [explicit rule: owned by `io.projectreactor`; 1 other group(s) rejected]
  ? * io.projectreactor                    2019-09..2026-08 3.8.7                |.....===============|
  ?   com.guicedee.modules.services        2026-08..2026-08 2.2.2                |...................=|
org.objectweb.asm.tree  [explicit rule: owned by `org.ow2.asm`; 19 other group(s) rejected]
  A * org.ow2.asm                          2017-07..2026-05 9.10.1               |.==================.|
  R   io.joynr.tools.generator             2021-05..2026-08 1.24.0-ws2           |.........===========|
  R   com.scylladb                         2025-12..2026-08 2.0.5                |..................==|
  R   org.jetbrains.compose.hot-reload     2025-10..2026-07 1.3.0-alpha01        |.................===|
  ?   org.opennms.newts                    2026-07..2026-07 3.0.1                |...................=|
  R   org.teavm                            2023-03..2026-06 0.15.0               |............========|
    + 14 more: ch.exense.step, io.killedkenny.crossfuzz, com.lihaoyi, io.joern, io.github.llmagentbuilder, com.jordansamhi, com.liferay, com.uber.nullaway, com.houxinlin, io.github.houxinlin, com.autonomousapps, org.netbeans.external, (+2 more)
kotlinx.serialization.core  [explicit rule: owned by `org.jetbrains`; 28 other group(s) rejected]
  A * org.jetbrains.kotlinx                2021-09..2026-04 1.11.0               |.........==========.|
  R   dev.sebastiano.spectre               2026-05..2026-08 0.5.0                |..................==|
  R   dev.robocode.tankroyale              2026-01..2026-08 1.1.0                |..................==|
  ?   lol.simeon                           2026-06..2026-06 1.1.2                |...................=|
  R   love.forte.plugin.suspend-transform  2025-04..2026-06 2.4.0-0.14.0         |................====|
  R   io.github.wangbax                    2026-04..2026-04 5.5.1-okio-fork-2    |..................=.|
    + 23 more: com.squareup.wire, org.ldemetrios, io.github.lumamontes, dev.zacsweers.metro, io.typst, io.availe, dev.oglass, io.github.oewntk, io.github.lexa-diky, com.toasttab.expediter, io.johnsonlee.exec, io.specmatic, (+11 more)
org.objectweb.asm.commons  [explicit rule: owned by `org.ow2.asm`; 40 other group(s) rejected]
  A * org.ow2.asm                          2017-07..2026-05 9.10.1               |.==================.|
  R   com.appdynamics                      2024-01..2026-08 26.8.0               |..............======|
  R   io.debezium                          2026-02..2026-08 3.6.1.Final          |..................==|
  ?   com.lealceldeiro                     2026-07..2026-07 2.4.1                |...................=|
  ?   org.gephi                            2026-06..2026-06 0.11.2               |...................=|
  R   org.teavm                            2023-03..2026-06 0.15.0               |............========|
    + 35 more: io.joern, com.apollographql.apollo, org.copper-engine, cn.iservicego, org.tango-controls, com.apollographql.apollo3, de.firemage.autograder, com.yugabyte, com.newrelic.agent.android, com.gradleup, org.tango-controls.pogo, software.amazon.disco, (+23 more)
kotlinx.coroutines.core  [explicit rule: owned by `org.jetbrains`; 21 other group(s) rejected]
  A * org.jetbrains.kotlinx                2023-03..2026-05 1.11.0               |............=======.|
  R   com.airbnb.viaduct                   2026-05..2026-08 2.0.0                |..................==|
  A   org.jetbrains.intellij.deps.kotlinx  2025-09..2026-08 1.10.2-intellij-2    |.................===|
  R   com.eygraber                         2026-01..2026-08 0.1.7                |..................==|
  ?   org.openprojectx.java.dns            2026-06..2026-07 0.1.4                |..................==|
  R   ca.acendas                           2025-11..2026-05 1.9.1                |.................==.|
    + 18 more: com.krillforge, org.openprojectx.hadoop.win, org.jetbrains.dokka, io.github.danbeldev, io.johnsonlee.kx, io.github.zimoyin, io.johnsonlee.exec, io.github.saumya-bhatt, io.realm.kotlin, com.rickbusarow.doks, io.sirix, com.squareup.wire, (+6 more)
jul.to.slf4j  [explicit rule: owned by `org.slf4j`; 17 other group(s) rejected]
  A * org.slf4j                            2019-02..2026-05 2.0.18               |....===============.|
  R   net.finmath                          2025-11..2026-08 3.0.0                |.................===|
  ?   io.github.zhouzhoucoder              2026-07..2026-07 6.0                  |...................=|
  ?   xyz.hldev.libra-common               2026-06..2026-06 1.0.1                |...................=|
  R   de.codecentric                       2024-01..2026-02 3.3.0                |..............=====.|
  R   io.github.davincilll                 2025-12..2025-12 1.0.4                |..................=.|
    + 12 more: io.github.daone-dadp, io.kestra.plugin, io.github.tky0065, com.itxk.maven, io.github.srilathakarri, de.fraunhofer.iosb.ilt.faaast.registry, org.easypeelsecurity, io.github.tracedin, com.github.kaklakariada, com.tencent.cloud, org.conductoross, io.bdeploy
com.sun.tools.xjc  [explicit rule: owned by `org.glassfish.jaxb`; 4 other group(s) rejected]
  A * org.glassfish.jaxb                   2018-07..2019-01 2.3.2                |...==...............|
  ?   gov.nasa.pds                         2026-06..2026-06 3.2.1                |...................=|
  R   com.sun.xml.bind                     2018-07..2026-05 4.0.9                |...================.|
  R   cn.lzgabel.jaxb.xml.bind             2022-03..2022-03 4.0.0                |..........=.........|
  R   com.github.shynixn                   2019-02..2019-02 1.0                  |....=...............|
spring.jcl  [explicit rule: owned by `org.springframework`; 2 other group(s) rejected]
  A * org.springframework                  2017-09..2026-06 6.2.19               |.===================|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |...................=|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
```

## republisher (9)

Earliest owner is foreign to the module name while a natural-namespace owner is also present (shaded / repackaged jars).

- The earliest (current) owner's groupId is foreign to the module name: the name does not fall under it.
- A natural-namespace owner - a publisher whose groupId the module name does fall under - is also present.
- The foreign earliest owner is still globally active (a dormant one would be a relocation, see migration).
- Allow the natural owner; reject the foreign republisher.

| count | current owner | new owner(s) |
|---:|---|---|
| 1 | `cn.enaium.cafully` | `org.tinylog` |
| 1 | `com.atlassian.commonmark` | `org.commonmark` |
| 1 | `com.github.ppodgorsek.email` | `org.apache.commons` |
| 1 | `com.sun.activation` | `jakarta.activation` |
| 1 | `com.sun.mail` | `jakarta.mail` |
| 1 | `io.github.pustike` | `org.apache.commons` |
| 1 | `io.github.ynverxe` | `org.tinylog` |
| 1 | `io.sdks` | `com.univapay` |
| 1 | `org.glassfish.jaxb` | `com.sun.xml.bind` |

```
com.sun.xml.bind  [republished by `org.glassfish.jaxb` (still active); belongs to `com.sun.xml.bind`]
  R * org.glassfish.jaxb                   2018-07..2019-01 2.3.2                |...==...............|
  R   int.esa.ccsds.mo                     2025-08..2026-08 14.2                 |.................===|
  R   com.helger.schematron                2021-09..2026-08 10.0.1               |.........===========|
  ?   com.checkmarx                        2026-06..2026-06 2026.2.32            |...................=|
  A   com.sun.xml.bind                     2018-07..2026-05 4.0.9                |...================.|
  R   com.exasol                           2024-10..2025-10 5.4.3                |...............===..|
    + 32 more: gov.nasa.pds, com.intuit.quickbooks-online, com.google.tsunami, io.github.azagniotov, com.liferay, org.opengis.cite, org.duracloud, edu.iris.dmc, br.com.swconsultoria, one.gfw, org.xtce, com.github.cafapi, (+20 more)
jakarta.activation  [republished by `com.sun.activation` (still active); belongs to `jakarta.activation`]
  R * com.sun.activation                   2018-11..2021-02 2.0.1                |....=====...........|
  R   com.cognite.spark.datasource         2022-01..2026-08 4.0.1247             |..........==========|
  R   org.playframework                    2023-09..2026-08 3.1.0-M14            |.............=======|
  R   com.github.xeroapi                   2025-06..2026-08 18.1.0               |.................===|
  R   org.tinystruct                       2025-09..2026-08 1.1.8                |.................===|
  ?   net.anotheria                        2026-07..2026-07 4.1.0                |...................=|
    + 40 more: org.uma.jmetal, org.kill-bill.billing, com.typesafe.play, com.helger.schematron, org.takes, com.eed3si9n, jakarta.activation, org.hpccsystems, ch.exense.step, com.newrelic.labs, io.github.dimabarbul, org.opengis.cite, (+28 more)
org.apache.commons.csv  [republished by `io.github.pustike` (still active); belongs to `org.apache.commons`]
  ? * io.github.pustike                    2019-01..2019-07 1.7.0                |....==..............|
  ?   org.sonarsource.scanner.engine       2026-05..2026-08 13.9.0.4593          |..................==|
  ?   be.ugent.idlab.knows                 2025-09..2026-08 4.2.0                |.................===|
  ?   com.guicedee.modules.services        2026-04..2026-08 2.2.2                |..................==|
  ?   com.orientechnologies                2026-03..2026-07 3.2.55               |..................==|
  ?   org.apache.pinot                     2024-08..2026-06 1.5.1                |...............=====|
    + 10 more: org.testingisdocumenting.znai, io.kestra.plugin, org.apache.commons, xyz.ottr.lutra, org.jetbrains.kotlinx, com.wizzdi, io.telicent.jena.graphql, io.telicent.jena, org.apache.jena, com.guicedee.services
jakarta.mail  [republished by `com.sun.mail` (still active); belongs to `jakarta.mail`]
  R * com.sun.mail                         2018-11..2025-07 2.0.2                |....==============..|
  ?   org.tinystruct                       2026-08..2026-08 1.7.29               |...................=|
  R   io.github.noeltoy                    2024-10..2026-08 1.2                  |...............=====|
  ?   org.cibseven.community.mail          2026-08..2026-08 2.2.0                |...................=|
  ?   org.apache.manifoldcf                2026-07..2026-07 2.31                 |...................=|
  R   com.randomnoun.db                    2022-10..2025-11 1.0.2                |...........========.|
    + 7 more: org.eclipse.angus, jakarta.mail, org.camunda.bpm.extension, name.bychkov, io.gravitee.apim.rest.api.standalone, com.krux, com.guicedee.services
com.univapay.api  [republished by `io.sdks` (still active); belongs to `com.univapay`]
  ? * io.sdks                              2026-07..2026-08 1.0.0                |...................=|
  ?   com.univapay                         2026-08..2026-08 1.2.2                |...................=|
org.commonmark  [republished by `com.atlassian.commonmark` (still active); belongs to `org.commonmark`]
  R * com.atlassian.commonmark             2018-01..2021-01 0.17.0               |..=======...........|
  ?   org.apache.tika                      2026-08..2026-08 4.0.0                |...................=|
  A   org.commonmark                       2021-01..2026-08 0.30.0               |........============|
  R   com.qainsights                       2026-05..2026-05 0.0.1                |..................=.|
  R   se.alipsa.gmd                        2025-09..2025-09 3.0.1                |.................=..|
  R   org.lucee                            2024-05..2024-05 0.22.0               |...............=....|
    + 1 more: org.aya-prover
org.tinylog.api.slf4j  [republished by `io.github.ynverxe` (still active); belongs to `org.tinylog`]
  ? * io.github.ynverxe                    2025-05..2025-05 1.0.0-indev          |.................=..|
  ?   org.tinylog                          2026-08..2026-08 2.8.0-M2             |...................=|
org.tinylog.impl  [republished by `cn.enaium.cafully` (still active); belongs to `org.tinylog`]
  ? * cn.enaium.cafully                    2023-02..2023-03 1.1.1                |............=.......|
  ?   org.tinylog                          2026-08..2026-08 2.8.0-M2             |...................=|
org.apache.commons.mail  [republished by `com.github.ppodgorsek.email` (still active); belongs to `org.apache.commons`]
  ? * com.github.ppodgorsek.email          2023-06..2023-06 2.0.0                |.............=......|
  ?   io.prophecy                          2024-08..2026-07 4.1.0-onprem-9.5.0   |...............=====|
  ?   org.apache.commons                   2023-12..2023-12 1.6.0                |..............=.....|
```

## migration (33)

The publishing groupId handed off over time (a rename or a relocation), so both coordinates are kept.

- Rename: a more-recent successor is the same project as the owner (a shared groupId prefix, or two shared leading segments).
- Relocation: the owner stopped at or before a credible successor took over (or the owner went globally dormant), and that successor itself owns the module namespace.
- Allow both old and new so history stays resolvable and `latest` is current.

| count | current owner | new owner(s) |
|---:|---|---|
| 13 | `org.hibernate` | `org.hibernate, org.hibernate.orm` |
| 4 | `dev.mohterbaord` | `dev.mohterbaord, dev.mohterbaord.fp4j` |
| 3 | `io.github.humbleui.skija` | `io.github.humbleui.skija, io.github.humbleui` |
| 2 | `love.forte.simbot.logger` | `love.forte.simbot.logger, love.forte.simbot` |
| 1 | `com.aliyun.polardb2` | `com.aliyun.polardb2, com.aliyun` |
| 1 | `com.ibm.zertjsse` | `com.ibm.zertjsse, com.ibm.semeru-zjavasecurity` |
| 1 | `com.peruncs.odbjca` | `com.peruncs.odbjca, com.peruncs` |
| 1 | `dev.kaiquebt` | `dev.kaiquebt, dev.kaiquebt.anycall` |
| 1 | `io.github.beast2-dev` | `io.github.beast2-dev, io.github.compevol` |
| 1 | `io.github.zyraz-io` | `io.github.zyraz-io, io.github.ekbatan-io` |
| 1 | `org.apache.commons` | `org.apache.commons, org.apache.tomee, org.apache.openjpa, org.apache.meecrowave` |
| 1 | `org.eclipse` | `org.eclipse, org.eclipse.yasson` |
| 1 | `org.opendaylight.yangtools` | `org.opendaylight.yangtools, org.opendaylight.netconf` |
| 1 | `pub.ihub.lib` | `pub.ihub.lib, pub.ihub.integration, pub.ihub.module` |
| 1 | `software.coley` | `software.coley, software.coley.bento-fx` |

```
com.aliyun.polardb2.jdbc  [renamed `com.aliyun.polardb2` -> `com.aliyun` (latest 42.5.7.0.16.1)]
  ? * com.aliyun.polardb2                  2026-07..2026-07 42.5.7.0.15          |...................=|
  ?   com.aliyun                           2026-07..2026-08 42.5.7.0.16.1        |...................=|
org.eclipse.yasson  [renamed `org.eclipse` -> `org.eclipse.yasson` (latest 3.1.0-M1)]
  A * org.eclipse                          2017-06..2024-07 3.0.4                |.===============....|
  ?   org.eclipse.yasson                   2026-07..2026-08 3.1.0-M1             |...................=|
  R   com.charlyghislain.keycloak          2022-08..2022-08 19.0.1               |...........=........|
ihub.core  [renamed `pub.ihub.lib` -> `pub.ihub.module` (latest 0.3.1)]
  ? * pub.ihub.lib                         2021-09..2026-07 2.0.0                |.........===========|
  ?   pub.ihub.module                      2024-04..2026-08 0.3.1                |..............======|
  ?   pub.ihub.integration                 2024-03..2026-08 0.2.5                |..............======|
org.hibernate.orm.c3p0  [renamed `org.hibernate` -> `org.hibernate.orm` (latest 7.4.6.Final)]
  A * org.hibernate                        2018-01..2026-06 5.3.39.Final         |..==================|
  A   org.hibernate.orm                    2018-12..2026-08 7.4.6.Final          |....================|
  ?   io.github.martinhickson              2026-08..2026-08 6.6.7-bravura-1      |...................=|
  R   com.guicedee.modules.services        2026-04..2026-04 2.0.0-RC10           |..................=.|
  R   com.guicedee.services                2020-07..2022-02 1.2.2.1-jre17        |.......====.........|
org.hibernate.orm.graalvm  [renamed `org.hibernate` -> `org.hibernate.orm` (latest 7.4.6.Final)]
  A * org.hibernate                        2020-02..2023-02 5.6.15.Final         |......=======.......|
  A   org.hibernate.orm                    2020-04..2026-08 7.4.6.Final          |......==============|
  ?   io.github.martinhickson              2026-08..2026-08 6.6.7-bravura-1      |...................=|
org.hibernate.orm.hikaricp  [renamed `org.hibernate` -> `org.hibernate.orm` (latest 7.4.6.Final)]
  A * org.hibernate                        2018-01..2026-06 5.3.39.Final         |..==================|
  A   org.hibernate.orm                    2018-12..2026-08 7.4.6.Final          |....================|
  ?   io.github.martinhickson              2026-08..2026-08 6.6.7-bravura-1      |...................=|
org.hibernate.orm.micrometer  [renamed `org.hibernate` -> `org.hibernate.orm` (latest 7.4.6.Final)]
  A * org.hibernate                        2020-12..2023-02 5.6.15.Final         |........=====.......|
  A   org.hibernate.orm                    2021-03..2026-08 7.4.6.Final          |........============|
  ?   io.github.martinhickson              2026-08..2026-08 6.6.7-bravura-1      |...................=|
org.hibernate.orm.testing  [renamed `org.hibernate` -> `org.hibernate.orm` (latest 7.4.6.Final)]
  A * org.hibernate                        2018-01..2026-06 5.3.39.Final         |..==================|
  A   org.hibernate.orm                    2018-12..2026-08 7.4.6.Final          |....================|
  ?   io.github.martinhickson              2026-08..2026-08 6.6.7-bravura-1      |...................=|
org.hibernate.orm.agroal  [renamed `org.hibernate` -> `org.hibernate.orm` (latest 7.4.6.Final)]
  A * org.hibernate                        2018-02..2026-06 5.3.39.Final         |..==================|
  A   org.hibernate.orm                    2018-12..2026-08 7.4.6.Final          |....================|
  ?   io.github.martinhickson              2026-08..2026-08 6.6.7-bravura-1      |...................=|
org.hibernate.orm.core  [renamed `org.hibernate` -> `org.hibernate.orm` (latest 7.4.6.Final)]
  A * org.hibernate                        2018-01..2026-06 5.3.39.Final         |..==================|
  A   org.hibernate.orm                    2018-12..2026-08 7.4.6.Final          |....================|
  ?   io.github.martinhickson              2026-08..2026-08 6.6.7-bravura-1      |...................=|
  R   com.guicedee.modules.services        2026-04..2026-08 2.2.2                |..................==|
  R   org.beangle.hibernate                2020-06..2026-08 7.4.5.Final          |.......=============|
  R   io.github.vmodi001                   2026-03..2026-03 5.6.16.Final         |..................=.|
    + 2 more: com.liferay, com.guicedee.services
org.hibernate.orm.envers  [renamed `org.hibernate` -> `org.hibernate.orm` (latest 7.4.6.Final)]
  A * org.hibernate                        2018-01..2026-06 5.3.39.Final         |..==================|
  A   org.hibernate.orm                    2019-11..2026-08 7.4.6.Final          |......==============|
  ?   io.github.martinhickson              2026-08..2026-08 6.6.7-bravura-1      |...................=|
org.hibernate.orm.jcache  [renamed `org.hibernate` -> `org.hibernate.orm` (latest 7.4.6.Final)]
  A * org.hibernate                        2018-01..2026-06 5.3.39.Final         |..==================|
  A   org.hibernate.orm                    2019-11..2026-08 7.4.6.Final          |......==============|
  ?   io.github.martinhickson              2026-08..2026-08 6.6.7-bravura-1      |...................=|
  R   com.guicedee.modules.services        2026-04..2026-08 2.2.2                |..................==|
  R   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17        |.....======.........|
org.hibernate.orm.spatial  [renamed `org.hibernate` -> `org.hibernate.orm` (latest 7.4.6.Final)]
  A * org.hibernate                        2018-01..2026-06 5.3.39.Final         |..==================|
  A   org.hibernate.orm                    2021-10..2026-08 7.4.6.Final          |.........===========|
  ?   io.github.martinhickson              2026-08..2026-08 6.6.7-bravura-1      |...................=|
org.hibernate.orm.jpamodelgen  [renamed `org.hibernate` -> `org.hibernate.orm` (latest 6.6.56.Final)]
  A * org.hibernate                        2018-01..2026-06 5.3.39.Final         |..==================|
  A   org.hibernate.orm                    2018-12..2026-08 6.6.56.Final         |....================|
  ?   io.github.martinhickson              2026-08..2026-08 6.6.7-bravura-1      |...................=|
  R   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17        |.....======.........|
org.hibernate.orm.proxool  [renamed `org.hibernate` -> `org.hibernate.orm` (latest 6.6.56.Final)]
  A * org.hibernate                        2018-01..2026-06 5.3.39.Final         |..==================|
  A   org.hibernate.orm                    2018-12..2026-08 6.6.56.Final         |....================|
  ?   io.github.martinhickson              2026-08..2026-08 6.6.7-bravura-1      |...................=|
org.hibernate.orm.vibur  [renamed `org.hibernate` -> `org.hibernate.orm` (latest 6.6.56.Final)]
  A * org.hibernate                        2018-01..2026-06 5.3.39.Final         |..==================|
  A   org.hibernate.orm                    2018-12..2026-08 6.6.56.Final         |....================|
  ?   io.github.martinhickson              2026-08..2026-08 6.6.7-bravura-1      |...................=|
org.opendaylight.yangtools.yang.model.export  [renamed `org.opendaylight.yangtools` -> `org.opendaylight.netconf` (latest 11.0.1)]
  ? * org.opendaylight.yangtools           2019-04..2026-08 15.1.5               |....================|
  ?   org.opendaylight.netconf             2026-08..2026-08 11.0.1               |...................=|
simbot.logger  [renamed `love.forte.simbot.logger` -> `love.forte.simbot` (latest 5.0.0-Preview5)]
  ? * love.forte.simbot.logger             2024-01..2026-05 5.0.0-Preview2       |..............=====.|
  ?   love.forte.simbot                    2026-06..2026-08 5.0.0-Preview5       |...................=|
simbot.logger.slf4j2impl  [renamed `love.forte.simbot.logger` -> `love.forte.simbot` (latest 5.0.0-Preview5)]
  ? * love.forte.simbot.logger             2024-01..2026-05 5.0.0-Preview2       |..............=====.|
  ?   love.forte.simbot                    2026-06..2026-08 5.0.0-Preview5       |...................=|
dev.kaiquebt.anycall  [renamed `dev.kaiquebt` -> `dev.kaiquebt.anycall` (latest 0.3.0)]
  ? * dev.kaiquebt                         2026-06..2026-06 0.1.1                |...................=|
  ?   dev.kaiquebt.anycall                 2026-08..2026-08 0.3.0                |...................=|
io.ekbatan.graalvm  [renamed `io.github.zyraz-io` -> `io.github.ekbatan-io` (latest 1.0.1)]
  ? * io.github.zyraz-io                   2026-05..2026-08 1.0.0                |..................==|
  ?   io.github.ekbatan-io                 2026-08..2026-08 1.0.1                |...................=|
dev.mohterbaord.fp4j.apf  [renamed `dev.mohterbaord` -> `dev.mohterbaord.fp4j` (latest 0.6.0)]
  ? * dev.mohterbaord                      2026-05..2026-05 0.5.0                |..................=.|
  ?   dev.mohterbaord.fp4j                 2026-08..2026-08 0.6.0                |...................=|
dev.mohterbaord.fp4j.core  [renamed `dev.mohterbaord` -> `dev.mohterbaord.fp4j` (latest 0.6.0)]
  ? * dev.mohterbaord                      2026-05..2026-05 0.5.0                |..................=.|
  ?   dev.mohterbaord.fp4j                 2026-08..2026-08 0.6.0                |...................=|
dev.mohterbaord.fp4j.scope  [renamed `dev.mohterbaord` -> `dev.mohterbaord.fp4j` (latest 0.6.0)]
  ? * dev.mohterbaord                      2026-05..2026-05 0.5.0                |..................=.|
  ?   dev.mohterbaord.fp4j                 2026-08..2026-08 0.6.0                |...................=|
dev.mohterbaord.fp4j.util  [renamed `dev.mohterbaord` -> `dev.mohterbaord.fp4j` (latest 0.6.0)]
  ? * dev.mohterbaord                      2026-05..2026-05 0.5.0                |..................=.|
  ?   dev.mohterbaord.fp4j                 2026-08..2026-08 0.6.0                |...................=|
mutable.alignment  [renamed `io.github.beast2-dev` -> `io.github.compevol` (latest 0.1.1)]
  ? * io.github.beast2-dev                 2026-08..2026-08 0.1.0                |...................=|
  ?   io.github.compevol                   2026-08..2026-08 0.1.1                |...................=|
org.apache.commons.dbcp2  [renamed `org.apache.commons` -> `org.apache.tomee` (latest 10.2.0)]
  ? * org.apache.commons                   2023-08..2025-12 2.14.0               |.............======.|
  ?   org.apache.tomee                     2023-12..2026-07 10.2.0               |..............======|
  ?   org.apache.meecrowave                2025-10..2025-10 2.0.0                |.................=..|
  ?   net.ontopia                          2025-04..2025-07 5.5.2                |................==..|
  ?   org.apache.openjpa                   2024-09..2025-05 4.1.1                |...............==...|
  ?   org.ikasan                           2024-07..2024-07 1.0.0                |...............=....|
bento.fx  [renamed `software.coley` -> `software.coley.bento-fx` (latest 0.16.0)]
  ? * software.coley                       2025-04..2026-01 0.15.1               |................===.|
  ?   software.coley.bento-fx              2026-07..2026-07 0.16.0               |...................=|
io.github.humbleui.skija.macos.arm64  [renamed `io.github.humbleui.skija` -> `io.github.humbleui` (latest 0.119.6)]
  A * io.github.humbleui.skija             2021-11..2021-11 0.96.0               |.........=..........|
  A   io.github.humbleui                   2021-12..2026-06 0.119.6              |..........==========|
  ?   com.behemiron.engine                 2026-06..2026-06 0.143.17             |...................=|
io.github.humbleui.skija.macos.x64  [renamed `io.github.humbleui.skija` -> `io.github.humbleui` (latest 0.119.6)]
  A * io.github.humbleui.skija             2021-11..2021-11 0.96.0               |.........=..........|
  A   io.github.humbleui                   2021-12..2026-06 0.119.6              |..........==========|
  ?   com.behemiron.engine                 2026-06..2026-06 0.143.17             |...................=|
io.github.humbleui.skija.shared  [renamed `io.github.humbleui.skija` -> `io.github.humbleui` (latest 0.119.6)]
  A * io.github.humbleui.skija             2021-11..2021-11 0.96.0               |.........=..........|
  A   io.github.humbleui                   2021-12..2026-06 0.119.6              |..........==========|
  ?   com.behemiron.engine                 2026-06..2026-06 0.143.17             |...................=|
zertjsse  [renamed `com.ibm.zertjsse` -> `com.ibm.semeru-zjavasecurity` (latest 11.0.31.0)]
  ? * com.ibm.zertjsse                     2026-05..2026-05 11.0.31.0            |..................=.|
  ?   com.ibm.semeru-zjavasecurity         2026-05..2026-05 11.0.31.0            |..................=.|
com.peruncs.odbjca.api  [renamed `com.peruncs.odbjca` -> `com.peruncs` (latest 0.0.2)]
  ? * com.peruncs.odbjca                   2018-11..2018-11 0.0.1                |....=...............|
  ?   com.peruncs                          2018-11..2018-11 0.0.2                |....=...............|
```

## fork (144)

A cross-org coordinate publishes the same name while the original owner is still active.

- A more-recent cross-org coordinate (a successor) publishes the same name while the original is still active.
- The earliest publisher is itself a credible owner: it owns the module namespace, or is the closest groupId to it.
- Keep the original owner; reject the fork.

```
io.netty.internal.tcnative  [fork: keep `io.netty`, `app.cash.backfila` still publishes the name]
  A * io.netty                             2021-10..2026-08 2.0.83.Final         |.........===========|
  R   app.cash.backfila                    2025-04..2026-09 2026.09.01.175539-b7f0968 |................====|
  R   com.instaclustr                      2025-10..2026-08 4.1.12               |.................===|
  R   com.spotify.confidence               2026-01..2026-08 0.20.0               |..................==|
  R   org.apache.ozone                     2025-12..2026-08 2.2.1                |..................==|
  R   com.google.cloud.bigdataoss          2025-10..2026-08 4.0.5                |.................===|
    + 76 more: com.google.api, com.google.cloud, org.finos.legend.engine, com.google.api-ads, com.google.cloud.bigtable, org.metricshub, io.grpc, io.weaviate, com.liquibase.ext, com.scalekit, io.temporal, io.github.ktestify, (+64 more)
org.yaml.snakeyaml  [fork: keep `org.yaml`, `com.google.cloud` still publishes the name]
  A * org.yaml                             2019-02..2026-02 2.6                  |....===============.|
  R   com.google.cloud                     2025-07..2026-08 0.7.0                |.................===|
  R   io.vertigo                           2024-07..2026-08 4.4.1                |...............=====|
  R   com.huaweicloud.sdk                  2024-01..2026-08 3.1.214              |..............======|
  R   org.conductoross                     2026-03..2026-08 3.32.2.rc3           |..................==|
  R   com.nvidia                           2023-04..2026-08 26.06.3              |............========|
    + 81 more: com.arcmutate, org.wso2.carbon.secvault, dev.sophiawhite, com.sparkutils, org.htcom, io.vertx, io.github.spah1879, com.deftdevs, org.apache.phoenix, org.apache.flink, io.github.wangscu, com.liquibase, (+69 more)
com.fasterxml.jackson.databind  [fork: keep `com.fasterxml.jackson.core`, `com.expediagroup.apiary` still publishes the name]
  A * com.fasterxml.jackson.core           2017-09..2026-08 2.22.2               |.===================|
  R   com.expediagroup.apiary              2025-11..2026-08 8.2.5                |.................===|
  ?   io.github.alexmercedcoder            2026-08..2026-08 1.0.4                |...................=|
  ?   io.github.paohaijiao                 2026-08..2026-08 2.6.0                |...................=|
  R   com.linkedin.iceberg                 2022-06..2026-08 1.5.2.21             |...........=========|
  ?   io.fluxzero.tools                    2026-07..2026-08 1.7.2                |...................=|
    + 467 more: org.octopusden.octopus.sonar, com.debugbundle, org.octopusden.octopus.components.registry.automation, io.github.kathukyabrian, dev.omnist, se.deversity.codekarta, com.icegreen, io.teknek.deliverance, io.github.jarredhj0214, io.github.vicliu624.explicitarchitecture, io.github.gabrielbbaldez, io.strimzi, (+455 more)
info.picocli  [fork: keep `info.picocli`, `com.instaclustr` still publishes the name]
  A * info.picocli                         2017-10..2025-04 4.7.7                |.================...|
  R   com.instaclustr                      2020-01..2026-08 4.1.12               |......==============|
  R   io.spicelabs                         2025-06..2026-08 1.7.0                |.................===|
  ?   org.dcache.nfs4j                     2026-07..2026-08 0.28.5               |...................=|
  R   org.keycloak                         2024-06..2026-08 26.7.2               |...............=====|
  ?   io.github.ruixuqi                    2026-08..2026-08 2.0.41-legacy        |...................=|
    + 163 more: io.github.kg-construct, io.github.daniloagostinho, org.primefaces, ru.curs, org.jgroups, org.apache.tika, ai.tegmentum.webassembly4j, io.github.shafthq, org.rundeck.rd, io.github.arthurcollet, run.endive, org.substrate4j, (+151 more)
ch.qos.logback.core  [fork: keep `ch.qos.logback`, `com.deltaproto` still publishes the name]
  A * ch.qos.logback                       2018-01..2026-08 1.6.3                |..==================|
  R   com.deltaproto                       2026-04..2026-08 1.5.0                |..................==|
  R   com.effacy.jui                       2024-12..2026-08 0.4.0                |................====|
  ?   ch.exense.step                       2026-07..2026-08 3.30.3               |...................=|
  R   de.gematik.test                      2025-11..2026-08 4.4.2                |.................===|
  R   io.camunda                           2026-02..2026-08 0.2.8                |..................==|
    + 30 more: club.dawdler, com.yetanalytics, net.ladenthin, org.eclipse.hawkbit, io.smallrye.reactive, org.ton.ton4j, org.springframework.cloud, com.limemojito.oss.aws, io.spicelabs, com.expediagroup, org.chenile, org.jetbrains.kotlinx, (+18 more)
com.fasterxml.jackson.annotation  [fork: keep `com.fasterxml.jackson.core`, `com.chartiq.finsemble` still publishes the name]
  A * com.fasterxml.jackson.core           2017-09..2026-08 2.18.10              |.===================|
  R   com.chartiq.finsemble                2025-06..2026-08 10.4.0               |.................===|
  ?   org.modeljars                        2026-08..2026-08 0.1.19               |...................=|
  R   com.bluecirclesoft.open              2022-08..2026-08 1.11                 |...........=========|
  R   com.scylladb.oss.simulacron          2025-09..2026-08 0.14.0.1             |.................===|
  R   com.gu.play-secret-rotation          2021-12..2026-08 21.0.0               |..........==========|
    + 469 more: ch.exense.step, org.jboss.pnc.maven-manipulator, top.bella, com.kubling, software.amazon.neptune, org.wso2.am.analytics.publisher, io.github.cdy-codey, org.itburger.maxbots, org.telegram, org.zowe.client.java.sdk, com.sbbsystems.flink, com.silanis.esl, (+457 more)
io.netty.codec.http2  [fork: keep `io.netty`, `com.applitools` still publishes the name]
  A * io.netty                             2017-12..2026-08 4.1.137.Final        |..==================|
  R   com.applitools                       2026-05..2026-08 5.89.4               |..................==|
  ?   org.openjproxy                       2026-08..2026-08 1.0.0-RC1            |...................=|
  ?   io.camunda.connector                 2026-06..2026-08 8.7.23               |..................==|
  R   org.apache.spark                     2025-10..2026-07 4.1.3                |.................===|
  ?   org.apache.gravitino                 2026-06..2026-06 1.3.0                |...................=|
    + 12 more: org.apache.iceberg, org.eclipse.ditto, io.github.jdbc-armour, io.github.cstopyak, it.neckar.open, net.xdob.ratly, io.micronaut.testresources, com.exactpro.th2, io.etcd, io.kestra.storage, org.wiremock, io.github.sunny-chung
com.formdev.flatlaf  [fork: keep `com.formdev`, `eu.rekawek.coffeegb` still publishes the name]
  A * com.formdev                          2019-10..2026-07 3.7.2                |.....===============|
  ?   eu.rekawek.coffeegb                  2026-08..2026-08 2.0.8                |...................=|
  R   dev.robocode.tankroyale              2026-05..2026-08 1.1.0                |..................==|
  ?   org.graphper                         2026-08..2026-08 1.5.3                |...................=|
  R   ca.corbett                           2025-05..2026-06 3.0.0                |................====|
  R   de.florianreuth                      2026-02..2026-02 2.2.0                |..................=.|
    + 4 more: de.florianmichael, com.suckatcoding, io.github.harvardpl, com.github.sundev79.MineBootFramework
org.graalvm.truffle  [fork: keep `org.graalvm.truffle`, `ai.mindconnect` still publishes the name]
  A * org.graalvm.truffle                  2018-10..2026-08 25.3.4.1             |...=================|
  ?   ai.mindconnect                       2026-08..2026-08 0.1.0                |...................=|
  ?   org.helixflo                         2026-08..2026-08 1.1.10               |...................=|
  ?   ai.looktech                          2026-06..2026-08 2.8.0-looktech.0     |...................=|
  R   com.liquibase.ext                    2025-09..2026-08 5.2.2                |.................===|
  R   com.walmartlabs.concord.k8s          2026-05..2026-08 2.44.0               |..................==|
    + 29 more: com.walmartlabs.concord, com.walmartlabs.concord.runtime.v1, com.walmartlabs.concord.runtime.v2, com.arcadedb, io.knish, io.hyperfoil.tools, org.opensearch.migrations.trafficcapture, sh.oso, org.mitre.synthea, com.molo17.gluesync.alpha, tools.dscode, ch.zizka.jbake, (+17 more)
org.jspecify  [fork: keep `org.jspecify`, `fr.inria.gforge.spoon` still publishes the name]
  A * org.jspecify                         2021-07..2026-07 1.0.1                |.........===========|
  ?   fr.inria.gforge.spoon                2026-08..2026-08 11.5.1-beta-7        |...................=|
  R   io.github.openfeign.querydsl         2026-05..2026-08 7.6                  |..................==|
  R   com.google.appengine                 2025-04..2026-08 5.1.0                |................====|
  R   org.jboss.elemento                   2025-10..2026-08 2.5.6                |.................===|
  ?   org.occurrent                        2026-07..2026-08 0.33.0               |...................=|
    + 54 more: org.finos.fluxnova.bpm.qa, org.finos.fluxnova.bpm, org.apache.meecrowave, io.crysknife, io.crysknife.ui, org.treblereel.gwt.yaml.mapper, org.treblereel.gwt.jakarta.websocket, org.treblereel.j2cl.processors, com.arcadedb, org.dominokit, org.treblereel.gwt.jakarta.rest, com.google.googlejavaformat, (+42 more)
io.vertx.circuitbreaker  [fork: keep `io.vertx`, `io.github.the-infinite` still publishes the name]
  ? * io.vertx                             2020-05..2026-08 4.5.32               |.......=============|
  ?   io.github.the-infinite               2026-07..2026-08 1.0.41-b             |...................=|
com.fasterxml.jackson.datatype.jsr310  [fork: keep `com.fasterxml.jackson.datatype`, `com.github.twitch4j` still publishes the name]
  A * com.fasterxml.jackson.datatype       2017-10..2026-08 2.22.2               |.===================|
  R   com.github.twitch4j                  2021-02..2026-08 1.27.0               |........============|
  R   com.linkedin.iceberg                 2025-09..2026-08 1.5.2.21             |.................===|
  R   org.openapitools                     2022-09..2026-08 7.25.0               |...........=========|
  R   cab.ml                               2026-05..2026-08 0.1.1                |..................==|
  ?   io.teknek.deliverance                2026-07..2026-08 0.0.14               |...................=|
    + 83 more: org.octopusden.octopus.reporting-service, org.apache.inlong, org.apache.xtable, org.octopusden.octopus.automation.teamcity, io.openlineage, io.github.cloudstub, org.opencds.cqf.cql.ls, org.datap-rs, org.codelibs.fess, org.apache.gravitino, org.apache.hudi, org.byteveda.agenteval, (+71 more)
org.threeten.extra  [fork: keep `org.threeten`, `fr.insee.trevas` still publishes the name]
  A * org.threeten                         2018-01..2026-06 1.10.0               |..==================|
  ?   fr.insee.trevas                      2026-08..2026-08 2.6.0                |...................=|
  R   org.apache.orc                       2020-01..2026-07 1.9.9                |......==============|
  R   org.yupana                           2022-01..2026-06 0.43.1               |..........==========|
  R   org.liquibase.ext                    2023-11..2023-11 4.25.0               |.............=......|
  R   com.netease.arctic                   2022-12..2022-12 0.4.0                |............=.......|
ch.qos.logback.classic  [fork: keep `ch.qos.logback`, `com.daml` still publishes the name]
  A * ch.qos.logback                       2018-01..2026-08 1.6.3                |..==================|
  R   com.daml                             2022-10..2026-08 3.6.0-snapshot.20260828.14793.0.vfa06ab6b |...........=========|
  ?   org.eclipse.kapua                    2026-08..2026-08 1.6.15               |...................=|
  ?   net.anotheria                        2026-07..2026-07 4.1.0                |...................=|
  R   org.chenile                          2024-09..2026-07 2.1.28               |...............=====|
  ?   org.jboss.pnc.maven-manipulator      2026-07..2026-07 5.4                  |...................=|
    + 92 more: com.limemojito.oss.aws, top.yqingyu, org.alfasoftware, org.apache.sling, com.salesforce.cantor, org.eclipse.ecsp, org.javastro.ivoa, ch.exense.step, io.mosip.biosdk, io.mosip.demosdk, de.fraunhofer.iosb.ilt, dk.alexandra.fresco, (+80 more)
org.apache.poi.poi  [fork: keep `org.apache.poi`, `ai.platon.pulsar` still publishes the name]
  A * org.apache.poi                       2021-01..2025-11 5.5.1                |........==========..|
  ?   ai.platon.pulsar                     2026-08..2026-08 4.14.0-rc.2          |...................=|
  R   com.guicedee.modules.services        2026-04..2026-08 2.2.2                |..................==|
  R   io.github.itgemini                   2024-12..2026-02 2.2.5                |................===.|
  R   io.github.daichangya                 2025-12..2025-12 5.1.1                |..................=.|
  R   com.jsdiff                           2025-12..2025-12 5.1.0                |..................=.|
    + 14 more: io.gitee.tikadoc, io.github.geminit-it, io.github.mianalysis, com.liferay, com.crealytics, com.codoid.products, ch.exense.step.library, com.guicedee.services, io.github.rocketmadev, org.datakurator, org.lucee, io.github.weizhonzhen, (+2 more)
com.fasterxml.jackson.core  [fork: keep `com.fasterxml.jackson.core`, `com.linkedin.iceberg` still publishes the name]
  A * com.fasterxml.jackson.core           2017-09..2026-08 2.22.2               |.===================|
  R   com.linkedin.iceberg                 2022-10..2026-08 1.2.0.24             |...........=========|
  R   com.facebook.presto                  2025-02..2026-08 0.299                |................====|
  ?   tech.streamfusion                    2026-08..2026-08 0.1.0-rc2            |...................=|
  R   org.jboss.pnc.maven-manipulator      2025-11..2026-08 5.6                  |.................===|
  ?   com.testingbot                       2026-07..2026-08 4.9                  |...................=|
    + 409 more: org.talend.sdk.component, com.lemmabase, org.operaton.spin, com.gpudb, io.delta, org.apache.jackrabbit, com.scylladb, com.algolia, org.cadenzaflow.spin, ai.particledb, io.deephaven, org.apache.inlong, (+397 more)
org.checkerframework.checker.qual  [fork: keep `org.checkerframework`, `io.valkyrja` still publishes the name]
  A * org.checkerframework                 2018-08..2026-08 4.2.2                |...=================|
  ?   io.valkyrja                          2026-07..2026-08 26.4.25              |...................=|
  R   io.boxlang                           2026-01..2026-08 1.17.0               |..................==|
  R   com.facebook.business.sdk            2025-02..2026-08 26.0.1               |................====|
  R   com.google.cloud                     2024-06..2026-08 3.46.0               |...............=====|
  ?   com.silanis.esl                      2026-07..2026-08 11.70.0              |...................=|
    + 31 more: com.daml, io.smallrye.reactive, com.webforj, io.joynr.java.core, io.github.imonja, io.github.eisop, org.eclipse.hawkbit, org.jetbrains.kotlinx, com.jcabi, org.opencastproject, org.orbisgis.geoclimate, io.github.mscheong01, (+19 more)
org.apache.commons.compress  [fork: keep `org.apache.commons`, `io.github.trethore` still publishes the name]
  A * org.apache.commons                   2017-10..2025-07 1.28.0               |.=================..|
  R   io.github.trethore                   2026-02..2026-08 146.0.10-jcefgithub.12 |..................==|
  R   org.apache.grails                    2026-04..2026-08 7.2.3                |..................==|
  R   com.mobidevelop.robovm               2024-02..2026-08 2.3.26               |..............======|
  R   org.apache.parquet                   2024-11..2026-08 1.18.0               |................====|
  ?   com.google.cloud.flink               2026-08..2026-08 1.2.0                |...................=|
    + 37 more: com.github.broadinstitute, com.alibaba.ververica, org.eclipse.tahu, com.codenameone, ink.icoding.codex, org.apache.beam, org.apache.flink, org.apache.pinot, org.apache.druid.extensions, com.theartos, me.bechberger, com.jlpka.langidentify, (+25 more)
com.fasterxml.jackson.dataformat.yaml  [fork: keep `com.fasterxml.jackson.dataformat`, `org.apiaddicts.apitools.dosonarapi` still publishes the name]
  A * com.fasterxml.jackson.dataformat     2017-10..2026-08 2.22.2               |.===================|
  R   org.apiaddicts.apitools.dosonarapi   2025-01..2026-08 1.6.0-beta-1         |................====|
  ?   io.simpleishard                      2026-07..2026-08 0.80.0               |...................=|
  ?   ai.chalk                             2026-08..2026-08 1.3.5                |...................=|
  R   dev.skyramp                          2025-02..2026-08 1.3.41               |................====|
  R   net.corda                            2024-07..2026-08 4.14.3               |...............=====|
    + 64 more: io.fabrikt, org.apache.flink, org.wildfly.prospero, com.helpchoice, io.fabric8, io.telicent.jena, io.github.gw-kit, org.apache.dolphinscheduler, com.sagframe, org.testcontainers, com.cjbooms, io.github.pavan2504, (+52 more)
org.slf4j  [fork: keep `org.slf4j`, `io.github.arun0009` still publishes the name]
  A * org.slf4j                            2017-04..2026-05 2.0.18               |===================.|
  ?   io.github.arun0009                   2026-08..2026-08 0.6.0                |...................=|
  R   io.github.adorsys-gis                2026-03..2026-08 0.3.0                |..................==|
  R   ch.exense.step                       2024-01..2026-08 3.30.3               |..............======|
  ?   com.buralotech.oss.identifier        2026-06..2026-08 3.1.1                |...................=|
  R   com.databricks                       2026-02..2026-08 1.5.0                |..................==|
    + 326 more: com.launchdarkly, io.testomat, org.mustangproject, com.erudika, com.newrelic.agent.android, org.open-metadata, org.talend.sdk.component.sample.feature, org.apache.artemis, edu.montana.cs.notch, com.google.appengine, com.google.cloud.bigtable, cn.zpcnet.beanforge, (+314 more)
org.signal.libsignal  [fork: keep `org.signal`, `io.github.wanggenlin` still publishes the name]
  A * org.signal                           2023-09..2025-11 0.86.5               |.............=====..|
  R   io.github.wanggenlin                 2026-02..2026-08 0.86.16              |..................==|
  ?   com.securegroupchat                  2026-07..2026-07 0.96.3               |...................=|
org.springdoc.openapi.ui  [fork: keep `org.springdoc`, `io.github.vpelikh` still publishes the name]
  A * org.springdoc                        2020-12..2026-08 3.1.0                |........============|
  ?   io.github.vpelikh                    2026-06..2026-08 5.1.0                |...................=|
  ?   io.github.branislavmalo              2026-08..2026-08 3.1.0-jackson3       |...................=|
  R   io.github.lisi9988                   2025-07..2025-12 2.8.14               |.................==.|
org.springdoc.openapi.webflux.ai  [fork: keep `org.springdoc`, `io.github.vpelikh` still publishes the name]
  ? * org.springdoc                        2026-04..2026-08 3.1.0                |..................==|
  ?   io.github.vpelikh                    2026-06..2026-08 5.1.0                |...................=|
org.springdoc.openapi.webflux.core  [fork: keep `org.springdoc`, `io.github.vpelikh` still publishes the name]
  A * org.springdoc                        2020-12..2026-08 3.1.0                |........============|
  ?   io.github.vpelikh                    2026-06..2026-08 5.1.0                |...................=|
  R   io.github.lisi9988                   2025-08..2025-12 2.8.14               |.................==.|
org.springdoc.openapi.webmvc.core  [fork: keep `org.springdoc`, `io.github.vpelikh` still publishes the name]
  A * org.springdoc                        2020-12..2026-08 2.9.0                |........============|
  ?   io.github.vpelikh                    2026-06..2026-08 5.1.0                |...................=|
  R   io.github.lisi9988                   2025-07..2025-12 2.8.14               |.................==.|
org.springdoc.openapi.ai.common  [fork: keep `org.springdoc`, `io.github.vpelikh` still publishes the name]
  ? * org.springdoc                        2026-04..2026-08 3.1.0                |..................==|
  ?   io.github.vpelikh                    2026-06..2026-08 5.1.0                |...................=|
org.springdoc.openapi.common  [fork: keep `org.springdoc`, `io.github.vpelikh` still publishes the name]
  A * org.springdoc                        2020-12..2026-08 3.1.0                |........============|
  ?   io.github.vpelikh                    2026-06..2026-08 5.1.0                |...................=|
  R   io.github.lisi9988                   2025-07..2025-12 2.8.14               |.................==.|
org.springdoc.openapi.webflux.scalar  [fork: keep `org.springdoc`, `io.github.vpelikh` still publishes the name]
  A * org.springdoc                        2025-09..2026-08 2.9.0                |.................===|
  ?   io.github.vpelikh                    2026-06..2026-08 5.1.0                |...................=|
  R   io.github.lisi9988                   2025-12..2025-12 2.8.14               |..................=.|
org.springdoc.openapi.webflux.ui  [fork: keep `org.springdoc`, `io.github.vpelikh` still publishes the name]
  A * org.springdoc                        2020-12..2026-08 2.9.0                |........============|
  ?   io.github.vpelikh                    2026-06..2026-08 5.1.0                |...................=|
  R   io.github.lisi9988                   2025-08..2025-12 2.8.14               |.................==.|
org.springdoc.openapi.webmvc.ai  [fork: keep `org.springdoc`, `io.github.vpelikh` still publishes the name]
  ? * org.springdoc                        2026-04..2026-08 3.1.0                |..................==|
  ?   io.github.vpelikh                    2026-06..2026-08 5.1.0                |...................=|
org.springdoc.openapi.webmvc.scalar  [fork: keep `org.springdoc`, `io.github.vpelikh` still publishes the name]
  A * org.springdoc                        2025-09..2026-08 2.9.0                |.................===|
  ?   io.github.vpelikh                    2026-06..2026-08 5.1.0                |...................=|
  R   io.github.lisi9988                   2025-12..2025-12 2.8.14               |..................=.|
io.netty.common  [fork: keep `io.netty`, `org.opendaylight.netconf` still publishes the name]
  A * io.netty                             2017-12..2026-08 4.1.137.Final        |..==================|
  R   org.opendaylight.netconf             2026-04..2026-08 11.0.2               |..................==|
  ?   io.gravitee.singularitee             2026-08..2026-08 1.2.1                |...................=|
  R   org.frankframework                   2026-04..2026-07 10.2.0               |..................==|
  R   io.quarkiverse.roq                   2026-04..2026-04 2.1.0.CR1            |..................=.|
  R   com.scylladb                         2025-12..2026-03 1.3.11               |..................=.|
    + 5 more: org.apache.arrow, com.github.tonivade, de.codecentric, com.google.cloud, software.amazon.jdbc
io.netty.buffer  [fork: keep `io.netty`, `org.opendaylight.netconf` still publishes the name]
  A * io.netty                             2017-12..2026-08 4.1.137.Final        |..==================|
  R   org.opendaylight.netconf             2025-07..2026-08 10.0.5               |.................===|
  R   org.opendaylight.controller          2025-07..2026-08 12.0.8               |.................===|
  ?   ai.covia                             2026-07..2026-08 0.9.6                |...................=|
  R   org.opendaylight.bgpcep              2025-07..2026-08 2.0.1                |.................===|
  ?   io.github.technobasant               2026-08..2026-08 1.3.0_spark-4.2.0    |...................=|
    + 20 more: io.krabka, top.shouldbe, org.apache.polaris, org.opendaylight.jsonrpc, org.apache.kyuubi, org.apache.tika, org.apache.tinkerpop, io.github.lukaszsamson, com.urbanairship, net.neoforged.jst, org.apache.flink, org.apache.iceberg, (+8 more)
org.apache.commons.lang3  [fork: keep `org.apache.commons`, `org.sonarsource.java` still publishes the name]
  A * org.apache.commons                   2017-06..2025-11 3.20.0               |.=================..|
  R   org.sonarsource.java                 2024-11..2026-08 8.41.0.47177         |...............=====|
  R   io.streamnative                      2025-05..2026-08 4.0.12.5             |................====|
  ?   com.blazemeter.jmeter                2026-08..2026-08 3.2.0                |...................=|
  R   com.ubs-hainer                       2025-11..2026-08 3.26.33              |.................===|
  ?   com.power4j.fist3                    2026-08..2026-08 3.15.1               |...................=|
    + 105 more: com.equinor.neqsim, io.swagger.parser.v3, com.sngular, org.metricshub, com.liquibase.ext, org.apache.paimon, com.datastax.oss, org.frankframework, org.openclover, org.apache.pulsar, io.openlineage, org.apache.tomee, (+93 more)
io.netty.handler.proxy  [fork: keep `io.netty`, `com.facebook.presto` still publishes the name]
  A * io.netty                             2017-12..2026-08 4.1.137.Final        |..==================|
  R   com.facebook.presto                  2026-04..2026-08 0.299                |..................==|
  R   io.kestra                            2025-08..2026-08 1.0.57               |.................===|
  ?   io.neonbee                           2026-06..2026-08 0.37.33              |...................=|
  R   org.apache.grails                    2026-05..2026-08 8.0.0-M5             |..................==|
  ?   io.github.norby99                    2026-07..2026-07 0.20.7               |...................=|
    + 7 more: io.micronaut.starter, org.apache.gravitino, io.sirix, org.apache.iceberg, io.kestra.plugin, com.frog-development.consul-populate, io.kestra.storage
com.google.errorprone.annotations  [fork: keep `com.google.errorprone`, `com.facebook.presto` still publishes the name]
  A * com.google.errorprone                2019-12..2026-06 2.50.0               |......==============|
  R   com.facebook.presto                  2025-02..2026-08 0.299                |................====|
  R   org.checkerframework                 2024-10..2026-08 4.2.2                |...............=====|
  R   com.salesforce.multicloudj           2026-04..2026-08 0.4.4                |..................==|
  ?   com.google.googlejavaformat          2026-07..2026-07 1.36.1               |...................=|
  ?   io.github.de-tu-dresden-inf-lat      2026-07..2026-07 0.6                  |...................=|
    + 55 more: org.apache.spark, com.clickzetta, eu.rssw.sonar.openedge, org.apache.seatunnel, org.noear, au.com.integradev.samples, com.google.appengine, com.google.turbine, io.okdp, io.zipkin.zipkin2, com.google.cloud, de.enflexit.awbAssist, (+43 more)
codes.rafael.asmjdkbridge  [fork: keep `codes.rafael.asmjdkbridge`, `co.hyperprobe` still publishes the name]
  ? * codes.rafael.asmjdkbridge            2025-01..2025-10 0.0.13               |................==..|
  ?   co.hyperprobe                        2026-07..2026-08 1.2.26               |...................=|
org.jsoup  [fork: keep `org.jsoup`, `org.scala-sbt` still publishes the name]
  A * org.jsoup                            2018-04..2026-08 1.23.2               |..==================|
  R   org.scala-sbt                        2024-12..2026-08 2.0.8                |................====|
  ?   org.jetbrains.intellij.plugins       2026-07..2026-08 1.410                |...................=|
  ?   io.github.shafthq                    2026-08..2026-08 10.3.20260826        |...................=|
  R   io.yupiik.maven                      2024-11..2026-08 1.2.16               |...............=====|
  R   org.finos.legend.sdlc                2026-04..2026-08 0.232.2              |..................==|
    + 40 more: software.amazon.jdbc, org.graylog2, com.qainsights, ru.biosoft.diagrams, io.crysknife.ui, com.sonatype.clm, org.apache.flink, ai.platon.pulsar, org.apache.tika, net.serenity-bdd, org.spdx, io.get-coursier, (+28 more)
org.eclipse.jetty.security  [fork: keep `org.eclipse.jetty`, `org.sonatype.nexus.common.components` still publishes the name]
  A * org.eclipse.jetty                    2018-11..2026-08 12.1.12              |....================|
  R   org.sonatype.nexus.common.components 2026-02..2026-08 3.94.2-01            |..................==|
  ?   io.debezium                          2026-07..2026-08 3.7.0.Beta1          |...................=|
  ?   ch.exense.step                       2026-06..2026-07 3.30.1               |...................=|
  R   org.sonatype.nexus.jetty             2025-09..2026-01 3.87.2-01            |.................==.|
io.vertx.core  [fork: keep `io.vertx`, `io.etcd` still publishes the name]
  A * io.vertx                             2020-05..2026-08 4.5.32               |.......=============|
  ?   io.etcd                              2026-08..2026-08 0.8.7                |...................=|
  ?   io.sirix                             2026-06..2026-07 1.0.0-beta7          |...................=|
  R   io.github.crac.io.vertx              2023-08..2024-09 4.4.6.CRAC.0         |.............===....|
  R   one.gfw                              2023-03..2023-03 4.4.0                |............=.......|
jakarta.ws.rs  [fork: keep `jakarta.ws.rs`, `com.bluecirclesoft.open` still publishes the name]
  A * jakarta.ws.rs                        2020-02..2024-04 4.0.0                |......=========.....|
  R   com.bluecirclesoft.open              2025-09..2026-08 1.11                 |.................===|
  R   org.jboss.narayana.lra               2024-12..2026-08 2.0.0.Final          |................====|
  R   nl.mirila.cli                        2025-11..2026-08 3.15.1               |.................===|
  ?   com.exasol                           2026-06..2026-07 5.7.4                |...................=|
  R   io.github.tblsoft.solr               2025-07..2026-06 4.7                  |.................===|
    + 14 more: com.inteligr8.activiti, com.affinidi.tdk, me.chrissw-r1, com.biit-solutions, com.github.xeroapi, com.liferay, no.telenor.sdk, org.opengis.cite, com.documents4j, com.github.estuaryoss, com.jcabi, com.datadoghq, (+2 more)
com.ethlo.time  [fork: keep `com.ethlo.time`, `io.github.adorsys-gis` still publishes the name]
  ? * com.ethlo.time                       2023-06..2025-02 1.14.0               |.............====...|
  ?   io.github.adorsys-gis                2026-07..2026-08 1.3.6                |...................=|
com.fasterxml.jackson.datatype.jdk8  [fork: keep `com.fasterxml.jackson.datatype`, `org.octopusden.octopus.cvevalidationservice` still publishes the name]
  A * com.fasterxml.jackson.datatype       2017-10..2026-08 2.22.2               |.===================|
  ?   org.octopusden.octopus.cvevalidationservice 2026-08..2026-08 2.0.0                |...................=|
  R   com.networknt                        2022-08..2026-08 2.3.7                |...........=========|
  ?   org.octopusden.octopus.automation.cve 2026-06..2026-08 2.0.7                |...................=|
  R   io.github.unmeshjoshi                2025-10..2026-06 0.1.0-alpha.29       |.................===|
  R   org.ic4j                             2026-03..2026-05 0.8.2                |..................=.|
    + 32 more: org.apache.grails, io.github.tansuasici, ai.onehouse, com.atlan, io.github.demiourgoi, io.kestra.plugin, com.tencent.cloud, hu.webarticum.miniconnect, io.openlineage, de.m3y.parquet, io.edurt.datacap, org.apache.parquet, (+20 more)
com.zaxxer.hikari  [fork: keep `com.zaxxer`, `io.github.kaleert` still publishes the name]
  A * com.zaxxer                           2018-01..2026-06 7.1.0                |..==================|
  R   io.github.kaleert                    2026-01..2026-08 1.2.2                |..................==|
  R   org.apache.inlong                    2022-06..2026-08 2.4.0                |...........=========|
  R   org.kill-bill.billing                2020-09..2026-08 0.24.21              |.......=============|
  R   org.finos.legend.depot               2025-06..2026-08 2.96.0               |.................===|
  R   com.aliyun.schedulerx                2020-08..2026-08 1.14.2               |.......=============|
    + 73 more: org.quickfixj, work.noice, org.apache.kylin, org.apache.hudi, org.apache.dolphinscheduler, io.github.deathgod7, io.higson, org.apache.seatunnel, com.scalar-labs, org.apache.flink, org.testingisdocumenting.webtau, it.unibz.inf.ontop, (+61 more)
com.fasterxml.jackson.jakarta.rs.json  [fork: keep `com.fasterxml.jackson.jakarta.rs`, `ch.exense.step` still publishes the name]
  A * com.fasterxml.jackson.jakarta.rs     2021-07..2026-08 2.22.2               |.........===========|
  R   ch.exense.step                       2022-10..2026-08 3.30.3               |...........=========|
  R   ch.exense.step.library               2023-08..2026-08 1.0.32               |.............=======|
  ?   com.inteligr8.activiti               2026-06..2026-08 1.4.1-aps-v26.2      |...................=|
  R   org.apache.tika                      2023-12..2026-07 3.3.2                |..............======|
  R   com.phonepe.sentinel-ai              2026-05..2026-05 1.1.2-SOLARIS-rc0    |..................=.|
    + 13 more: org.eclipse.tractusx.edc, org.ow2.petals.samples.rest.edm, dev.getelements.elements, org.eclipse.edc.huawei, org.eclipse.edc.aws, org.eclipse.edc, io.nflow, com.brightsparklabs, io.trino.gateway, com.snehasishroy, com.smoketurner.dropwizard, org.kiwiproject, (+1 more)
org.eclipse.jetty.compression.gzip  [fork: keep `org.eclipse.jetty.compression`, `ch.exense.step` still publishes the name]
  ? * org.eclipse.jetty.compression        2024-12..2026-08 12.1.12              |................====|
  ?   ch.exense.step                       2026-08..2026-08 3.30.3               |...................=|
tools.jackson.databind  [fork: keep `tools.jackson.core`, `de.codelix.commandapi` still publishes the name]
  A * tools.jackson.core                   2025-03..2026-08 3.2.2                |................====|
  ?   de.codelix.commandapi                2026-08..2026-08 4.4.0                |...................=|
  R   com.smartystreets.api                2026-01..2026-07 7.2.0                |..................==|
  R   software.xdev.mockserver             2026-03..2026-03 2.0.4                |..................=.|
  R   io.github.koinsaari                  2025-11..2025-11 0.1.0                |.................=..|
org.lwjgl.glfw  [fork: keep `org.lwjgl`, `io.github.lionblazer` still publishes the name]
  A * org.lwjgl                            2017-09..2026-08 3.4.3                |.===================|
  ?   io.github.lionblazer                 2026-08..2026-08 1.92.5.1             |...................=|
  R   io.github.spair                      2021-06..2025-08 1.90.0               |.........=========..|
  R   com.github.tommyettinger             2022-10..2023-11 1.12.1.0             |...........====.....|
  R   org.glavo.hmcl.mmachina              2022-09..2022-09 3.3.1-mmachina.1     |...........=........|
  A   org.lwjgl.osgi                       2018-12..2021-12 3.3.0                |....=======.........|
org.tukaani.xz  [fork: keep `org.tukaani`, `net.algart` still publishes the name]
  A * org.tukaani                          2018-01..2026-03 1.12                 |..=================.|
  ?   net.algart                           2026-08..2026-08 1.5.2                |...................=|
  R   io.anserini                          2022-01..2026-08 2.3.0                |..........==========|
  R   org.sonarsource.javascript           2023-09..2026-08 11.8.1.44439         |.............=======|
  R   de.m3y.parquet                       2025-01..2026-08 1.18.0               |................====|
  ?   com.timecho.timechodb                2026-06..2026-08 2.0.11.1             |...................=|
    + 24 more: org.apache.syncope.fit, org.incenp, io.github.peterdowdy, com.timecho.iotdb, com.sonatype.clm, net.neoforged.installertools, org.apache.iotdb, io.archivesunleashed, org.apache.parquet, io.github.seabow, org.apache.inlong, io.kestra.plugin, (+12 more)
org.apache.logging.log4j.slf4j2.impl  [fork: keep `org.apache.logging.log4j`, `net.corda` still publishes the name]
  A * org.apache.logging.log4j             2023-10..2026-07 2.25.5               |.............=======|
  R   net.corda                            2024-07..2026-08 4.14.3               |...............=====|
  ?   org.beilstein                        2026-07..2026-08 1.4.1                |...................=|
  R   org.apache.tika                      2025-04..2026-07 3.3.2                |................====|
  R   io.kroxylicious                      2024-12..2026-07 0.23.0               |................====|
  R   io.github.alien-tools                2025-07..2025-07 0.2.0                |.................=..|
    + 2 more: xyz.gianlu.librespot, io.github.giis-uniovi
org.apache.jena.iri3986x  [fork: keep `org.apache.jena`, `be.ugent.idlab.knows` still publishes the name]
  ? * org.apache.jena                      2026-01..2026-08 6.2.0                |..................==|
  ?   be.ugent.idlab.knows                 2026-08..2026-08 1.5.1                |...................=|
com.nimbusds.jose.jwt  [fork: keep `com.nimbusds`, `com.vaadin` still publishes the name]
  A * com.nimbusds                         2020-08..2026-07 5.0.2                |.......=============|
  R   com.vaadin                           2025-07..2026-08 2.3.2                |.................===|
  ?   org.ligoj.plugin                     2026-07..2026-07 2.0.0                |...................=|
  R   fish.payara.security.connectors      2024-05..2026-04 2.9.0                |..............=====.|
  R   org.bonitasoft.connectors            2026-04..2026-04 1.0.0-beta.1         |..................=.|
  R   org.apache.hadoop                    2026-03..2026-03 3.5.0                |..................=.|
    + 6 more: com.waveinformatica.skysso, io.github.swiyu-admin-ch, io.okdp, org.project-kessel, com.liferay, com.thetransactioncompany
net.bytebuddy  [fork: keep `net.bytebuddy`, `de.gematik.test` still publishes the name]
  A * net.bytebuddy                        2017-05..2026-08 1.18.12              |.===================|
  R   de.gematik.test                      2024-08..2026-08 4.4.2                |...............=====|
  R   com.graphql-java                     2024-03..2026-08 24.4                 |..............======|
  R   pub.ihub.integration                 2024-04..2026-08 0.2.5                |..............======|
  ?   com.mysticalrzc                      2026-06..2026-07 0.0.3                |...................=|
  ?   io.github.praveenkpandu              2026-06..2026-06 0.1.0-alpha          |...................=|
    + 126 more: com.logitags, com.jcabi, org.lucee, io.github.lucientong, dev.jorel, io.github.rocketbunny727, io.github.smallfast, net.aivory, ai.superstream, io.github.mlanett, com.appland, io.github.jlapugot.chronoguard, (+114 more)
org.opentest4j  [fork: keep `org.opentest4j`, `org.jetbrains.kotlin` still publishes the name]
  A * org.opentest4j                       2017-07..2023-07 1.3.0                |.=============......|
  ?   org.jetbrains.kotlin                 2026-07..2026-08 2.4.20-RC2           |...................=|
  ?   com.eatthepath                       2026-08..2026-08 1.0.0                |...................=|
  R   org.tiatesting                       2024-12..2026-08 0.1.19               |................====|
  R   berlin.yuna                          2025-11..2026-06 2026.06.1562143      |.................===|
  R   org.ndviet                           2026-04..2026-04 4.42.0               |..................=.|
    + 9 more: com.adobe.cq, io.pravega, org.caseine, io.github.origin-energy, com.hurence.logisland, io.github.thxno, io.github.osvalda, net.corda, com.github.tandronicus
com.fasterxml.aalto  [fork: keep `com.fasterxml`, `org.jetbrains.kotlin` still publishes the name]
  A * com.fasterxml                        2018-04..2026-05 1.4.0                |..=================.|
  ?   org.jetbrains.kotlin                 2023-12..2026-08 2.4.20-RC2           |..............======|
  R   org.jetbrains.dokka                  2022-06..2023-03 1.8.10               |...........==.......|
com.samskivert.jmustache  [fork: keep `com.samskivert`, `dev.getelements.elements` still publishes the name]
  A * com.samskivert                       2019-07..2023-11 1.16                 |.....==========.....|
  ?   dev.getelements.elements             2026-08..2026-08 3.9.0-rc-9           |...................=|
  R   io.swagger.codegen.v3                2022-02..2023-02 3.0.41               |..........===.......|
  R   io.garam                             2020-11..2020-11 0.4                  |........=...........|
com.github.jknack.handlebars  [fork: keep `com.github.jknack`, `me.bechberger` still publishes the name]
  A * com.github.jknack                    2024-03..2026-07 4.5.4                |..............======|
  R   me.bechberger                        2026-01..2026-08 0.1.2                |..................==|
  ?   org.openidentityplatform.openidm.tools 2026-07..2026-07 7.1.2                |...................=|
  R   org.craftercms                       2026-04..2026-04 4.6.0                |..................=.|
org.openapitools.jackson.nullable  [fork: keep `org.openapitools`, `io.airlift` still publishes the name]
  A * org.openapitools                     2023-02..2026-07 0.2.11               |............========|
  R   io.airlift                           2026-04..2026-08 445                  |..................==|
  ?   io.github.giis-uniovi                2026-07..2026-07 2.2.1                |...................=|
org.jctools.core  [fork: keep `org.jctools`, `io.monix` still publishes the name]
  A * org.jctools                          2020-11..2026-08 4.0.7                |........============|
  ?   io.monix                             2026-08..2026-08 3.5.0                |...................=|
  R   net.kieker-monitoring                2024-09..2026-04 2.0.3                |...............====.|
  R   io.xdag                              2025-10..2025-12 0.1.6                |.................==.|
  R   io.github.shangor                    2025-07..2025-08 1.1.3                |.................=..|
  R   io.actor4j                           2025-06..2025-06 2.4.0-beta.3         |.................=..|
    + 1 more: io.github.jponge.jct
org.apache.commons.jexl3  [fork: keep `org.apache.commons`, `org.hotrodorm.hotrod` still publishes the name]
  ? * org.apache.commons                   2024-06..2026-06 3.7.0                |...............=====|
  ?   org.hotrodorm.hotrod                 2025-07..2026-08 5.1.25               |.................===|
org.snakeyaml.engine.v2  [fork: keep `org.snakeyaml`, `io.github.ethanz0x0` still publishes the name]
  A * org.snakeyaml                        2019-10..2025-07 2.10                 |.....=============..|
  R   io.github.ethanz0x0                  2025-08..2026-08 2.0.4                |.................===|
  R   org.frankframework                   2025-01..2026-07 9.4.4                |................====|
  ?   io.github.baokhang83.mnemo           2026-07..2026-07 0.1.4                |...................=|
  ?   io.btrace                            2026-07..2026-07 0.26.2               |...................=|
  ?   com.walmartlabs.concord.k8s          2026-06..2026-07 2.42.1               |...................=|
    + 29 more: com.datadoghq, org.wildfly.glow, org.apache.zeppelin, io.acryl, io.fabric8, io.strimzi, io.github.phompang, eu.koboo, io.dscope.camel, org.workflomics, org.sonarsource.iac, ch.framedev, (+17 more)
org.bytedeco.pytorch.platform  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  ? * org.bytedeco                         2021-08..2026-08 2.13.0-1.5.14        |.........===========|
  ?   io.github.mullerhai                  2026-07..2026-08 2.13.0-1.5.14-beta-08.7 |...................=|
org.bytedeco.pytorch  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  ? * org.bytedeco                         2021-08..2026-08 2.13.0-1.5.14        |.........===========|
  ?   io.github.mullerhai                  2026-07..2026-08 2.13.0-1.5.14-beta-08.7 |...................=|
org.bytedeco.pytorch.linux.x86_64  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  ? * org.bytedeco                         2021-08..2026-08 2.13.0-1.5.14        |.........===========|
  ?   io.github.mullerhai                  2026-08..2026-08 2.13.0-1.5.14-beta-08.7 |...................=|
org.bytedeco.pytorch.platform.gpu  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  ? * org.bytedeco                         2021-08..2026-08 2.13.0-1.5.14        |.........===========|
  ?   io.github.mullerhai                  2026-08..2026-08 2.13.0-1.5.14-beta-08.5 |...................=|
org.bytedeco.pytorch.windows.x86_64  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  ? * org.bytedeco                         2021-08..2026-08 2.13.0-1.5.14        |.........===========|
  ?   io.github.mullerhai                  2026-08..2026-08 2.13.0-1.5.14-beta-08.6 |...................=|
info.picocli.shell.jline3  [fork: keep `info.picocli`, `org.modeljars` still publishes the name]
  ? * info.picocli                         2019-01..2025-04 4.7.7                |....=============...|
  ?   org.modeljars                        2026-08..2026-08 0.1.17               |...................=|
org.bytedeco.pytorch.macosx.arm64  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  ? * org.bytedeco                         2024-01..2026-08 2.13.0-1.5.14        |..............======|
  ?   io.github.mullerhai                  2026-07..2026-08 2.13.0-1.5.14-beta-08.4 |...................=|
org.bytedeco.pytorch.macosx.x86_64  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  ? * org.bytedeco                         2021-08..2026-08 2.13.0-1.5.14        |.........===========|
  ?   io.github.mullerhai                  2026-08..2026-08 2.13.0-1.5.14-beta-08.4 |...................=|
org.bytedeco.pytorch.windows.x86_64.gpu  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  ? * org.bytedeco                         2021-08..2026-08 2.13.0-1.5.14        |.........===========|
  ?   io.github.mullerhai                  2026-08..2026-08 2.13.0-1.5.14-beta-08.3 |...................=|
tools.jackson.core  [fork: keep `tools.jackson.core`, `org.pragmatica-lite` still publishes the name]
  A * tools.jackson.core                   2025-03..2026-08 3.2.2                |................====|
  ?   org.pragmatica-lite                  2026-08..2026-08 0.7.3                |...................=|
  R   ru.tinkoff.piapi                     2026-01..2026-08 1.49.6               |..................==|
  ?   org.apache.cayenne                   2026-06..2026-08 5.0-M3               |...................=|
  R   com.erudika                          2025-11..2026-08 1.43.0               |.................===|
  ?   com.phonepe.sentinel-ai              2026-06..2026-07 1.2.0-alpha3         |...................=|
    + 23 more: org.pragmatica-lite.aether, software.xdev.mockserver, com.ibm.jsonata4java, fish.payara.tools, com.playtika.reactivefeign, io.github.ignf, media.barney, org.sonarsource.sonarlint.ls, io.github.tansuasici, com.limemojito.oss.standards, com.limemojito.oss.standards.development-test, io.sapl, (+11 more)
com.fasterxml.jackson.jaxrs.json  [fork: keep `com.fasterxml.jackson.jaxrs`, `org.lance` still publishes the name]
  A * com.fasterxml.jackson.jaxrs          2017-10..2026-08 2.22.2               |.===================|
  R   org.lance                            2025-12..2026-08 0.8.0-beta.1         |..................==|
  ?   ru.moysklad.api                      2026-08..2026-08 0.20.0               |...................=|
  R   org.devlive.connector                2025-03..2026-08 2026.0.0             |................====|
  R   com.alibaba.ververica                2022-10..2026-07 1.20-vvr-11.8.0-1-jdk11 |...........=========|
  ?   io.simpleishard                      2026-07..2026-07 0.55.0               |...................=|
    + 55 more: ai.askamerica, org.apache.pulsar, org.apache.phoenix, com.ascentstream.pulsar, org.apache.hbase.thirdparty, org.apache.pinot, org.apache.hudi, dev.henneberger, io.github.giis-uniovi, org.apache.flink, org.apache.seatunnel, org.onebusaway, (+43 more)
dev.tamboui.toolkit  [fork: keep `dev.tamboui`, `com.steeplesoft` still publishes the name]
  ? * dev.tamboui                          2026-02..2026-06 0.4.0                |..................==|
  ?   com.steeplesoft                      2026-08..2026-08 1.0.0                |...................=|
  ?   dev.jbang                            2026-07..2026-07 0.141.0              |...................=|
io.netty.handler  [fork: keep `io.netty`, `eu.michael-simons.neo4j` still publishes the name]
  A * io.netty                             2017-12..2026-08 4.1.137.Final        |..==================|
  R   eu.michael-simons.neo4j              2025-07..2026-08 4.1.3                |.................===|
  ?   io.camunda.connector                 2026-06..2026-08 8.7.23               |..................==|
  R   org.apache.storm                     2025-05..2026-07 2.8.9                |................====|
  R   org.apache.tika                      2025-04..2026-07 3.3.2                |................====|
  ?   org.openidentityplatform.opendj      2026-06..2026-07 5.1.2                |...................=|
    + 5 more: org.apache.flink, io.github.ousatov-ua, io.kestra.plugin, org.lucee, com.luhuiguo.netty
io.github.willena.sqlitejdbc  [fork: keep `io.github.willena`, `com.codenameone` still publishes the name]
  ? * io.github.willena                    2021-11..2026-07 3.53.3.0             |..........==========|
  ?   com.codenameone                      2026-08..2026-08 7.0.267              |...................=|
org.jetbrains.annotations  [fork: keep `org.jetbrains`, `com.qcloud.cos` still publishes the name]
  A * org.jetbrains                        2018-09..2026-02 26.1.0               |...================.|
  R   com.qcloud.cos                       2024-03..2026-08 1.6.1                |..............======|
  ?   io.split                             2026-06..2026-08 1.0.0-beta3          |...................=|
  R   com.kinetica                         2025-09..2026-08 7.2.3.24             |.................===|
  R   io.deephaven                         2025-09..2026-08 41.8                 |.................===|
  R   systems.manifold                     2023-08..2026-08 2026.1.12            |.............=======|
    + 77 more: me.bechberger, com.microsoft.azure.kusto, de.fraunhofer.iosb.ilt.FROST-Server, io.github.4rg0n, de.tubyoub, beer.devs, io.github.alepandocr, io.github.nbauma109, io.github.happybavarian07, org.jam4s, io.streamthoughts, org.tallison.tika, (+65 more)
io.netty.codec.http  [fork: keep `io.netty`, `com.amazonaws` still publishes the name]
  A * io.netty                             2017-12..2026-08 4.1.137.Final        |..==================|
  R   com.amazonaws                        2026-02..2026-08 2026.33.1            |..................==|
  R   dev.zio                              2025-05..2026-08 3.11.4               |.................===|
  ?   io.github.shafthq                    2026-07..2026-08 10.3.20260806        |...................=|
  ?   io.camunda.connector                 2026-06..2026-08 8.7.23               |..................==|
  R   org.apache.tika                      2026-03..2026-07 3.3.2                |..................==|
    + 6 more: com.liquibase.ext, org.apache.flink, com.xuxueli, org.eclipse.ditto, io.sapl, de.fraunhofer.iosb.ilt.faaast.service
com.github.benmanes.caffeine  [fork: keep `com.github.ben-manes.caffeine`, `net.wirelabs` still publishes the name]
  A * com.github.ben-manes.caffeine        2017-12..2026-05 3.2.4                |..=================.|
  R   net.wirelabs                         2026-02..2026-08 1.4.6                |..................==|
  R   com.janeluo                          2026-03..2026-08 1.0.15               |..................==|
  R   io.zeebe.redis                       2025-05..2026-07 8.9.2                |................====|
  ?   org.kill-bill.billing                2026-06..2026-07 0.42.2               |...................=|
  R   org.openprovenance.prov              2024-10..2026-07 2.2.4                |...............=====|
    + 16 more: nl.basjes.parse.useragent, org.apache.tinkerpop, com.google.errorprone, io.pebbletemplates, org.odftoolkit, io.tileverse.pmtiles, org.opengis.cite, nl.goodbytes.xmpp.xep, org.igniterealtime.whack, be.vlaanderen.informatievlaanderen.ldes.ldio, com.aerospike, com.gitlab.cdc-java.office, (+4 more)
org.apache.logging.log4j  [fork: keep `org.apache.logging.log4j`, `org.springframework.cloud` still publishes the name]
  A * org.apache.logging.log4j             2017-11..2026-07 2.25.5               |..==================|
  ?   org.springframework.cloud            2026-08..2026-08 5.0.3                |...................=|
  R   com.ibm.galasa                       2026-02..2026-08 2.0.1                |..................==|
  R   com.mobius-software.protocols.sip    2024-01..2026-08 10.1.0-32-java11     |..............======|
  R   com.alibaba.ververica                2020-08..2026-07 1.20-vvr-11.8.0-1-jdk11 |.......=============|
  R   io.github.beehive-lab                2025-09..2026-07 1.0.0-jdk25          |.................===|
    + 328 more: com.alibaba.dts.client, io.kroxylicious, org.lucee, com.nqadmin.swingset.demo, com.vaimee, org.apache.hudi, io.github.zhouzhoucoder, org.beilstein, io.github.uwegeercken, org.hpccsystems, org.into-cps.maestro, com.adobe.campaign.tests.bridge.service, (+316 more)
org.apache.commons.beanutils2  [fork: keep `org.apache.commons`, `org.onebusaway` still publishes the name]
  ? * org.apache.commons                   2024-12..2025-05 2.0.0-M2             |................=...|
  ?   org.onebusaway                       2025-05..2026-08 14.2.2               |................====|
  ?   com.github.bordertech.wcomponents    2025-12..2026-01 1.5.39               |..................=.|
com.fasterxml.jackson.module.paramnames  [fork: keep `com.fasterxml.jackson.module`, `com.infobip` still publishes the name]
  A * com.fasterxml.jackson.module         2017-10..2026-08 2.22.2               |.===================|
  R   com.infobip                          2026-03..2026-08 4.0.0                |..................==|
  ?   org.realityforge.proton              2026-06..2026-07 0.74                 |...................=|
  ?   org.realityforge.sting               2026-06..2026-06 0.39                 |...................=|
  ?   org.realityforge.router.fu           2026-06..2026-06 0.47                 |...................=|
  ?   org.realityforge.react4j             2026-06..2026-06 0.226                |...................=|
    + 6 more: org.realityforge.arez, io.kestra, com.araksis, com.araksis.sjd, io.github.codgen, io.micronaut.example
org.locationtech.proj4j  [fork: keep `org.locationtech.proj4j`, `io.github.emilevictor.neoproj4j` still publishes the name]
  ? * org.locationtech.proj4j              2026-06..2026-06 1.4.3                |...................=|
  ?   io.github.emilevictor.neoproj4j      2026-08..2026-08 2.3.0                |...................=|
org.locationtech.proj4j.geoapi  [fork: keep `org.locationtech.proj4j`, `io.github.emilevictor.neoproj4j` still publishes the name]
  ? * org.locationtech.proj4j              2026-05..2026-06 1.4.3                |..................==|
  ?   io.github.emilevictor.neoproj4j      2026-08..2026-08 2.3.0                |...................=|
org.locationtech.proj4j.epsg  [fork: keep `org.locationtech.proj4j`, `io.github.emilevictor.neoproj4j` still publishes the name]
  ? * org.locationtech.proj4j              2026-06..2026-06 1.4.3                |...................=|
  ?   io.github.emilevictor.neoproj4j      2026-08..2026-08 2.3.0                |...................=|
org.assertj.core  [fork: keep `org.assertj`, `io.gitlab.cupofcode` still publishes the name]
  A * org.assertj                          2018-05..2026-01 3.27.7               |...================.|
  ?   io.gitlab.cupofcode                  2026-08..2026-08 1.2.6                |...................=|
  ?   com.install4j                        2026-07..2026-07 13.0.1               |...................=|
  R   io.github.y-yabust                   2025-12..2025-12 0.1.0                |..................=.|
  R   io.github.algomaster99               2023-07..2025-01 0.14.1               |.............====...|
  R   br.com.leverinfo                     2023-05..2023-12 0.2.0                |............===.....|
    + 9 more: org.kie, com.accenture.testing.bdd, org.projectnessie, com.github.aro-tech, com.github.mizosoft.methanol, com.salesforce.dockerfile-image-update, org.robotframework, io.prestosql, io.prestosql.tempto
org.mongodb.driver.core  [fork: keep `org.mongodb`, `com.guicedee.modules.services` still publishes the name]
  ? * org.mongodb                          2018-04..2026-08 5.10.0               |..==================|
  ?   com.guicedee.modules.services        2026-08..2026-08 2.2.2                |...................=|
org.mongodb.bson  [fork: keep `org.mongodb`, `com.guicedee.modules.services` still publishes the name]
  ? * org.mongodb                          2018-04..2026-08 5.10.0               |..==================|
  ?   com.guicedee.modules.services        2026-08..2026-08 2.2.2                |...................=|
com.github.librepdf.openpdf  [fork: keep `com.github.librepdf`, `com.guicedee.modules.services` still publishes the name]
  A * com.github.librepdf                  2018-09..2026-05 3.0.5                |...================.|
  R   com.guicedee.modules.services        2026-04..2026-08 2.2.2                |..................==|
  ?   io.github.icarius4iu                 2026-06..2026-06 0.1.0                |...................=|
  R   net.ioze                             2026-01..2026-01 1.0.9                |..................=.|
  R   org.computate                        2023-11..2024-02 2.0.2                |.............==.....|
  R   io.github.darkxanter                 2023-10..2023-10 1.3.31               |.............=......|
    + 2 more: com.github.kwart.jsign, com.guicedee.services
org.apache.commons.collections4  [fork: keep `org.apache.commons`, `com.guicedee.modules.services` still publishes the name]
  A * org.apache.commons                   2018-07..2026-08 4.6.0                |...=================|
  R   com.guicedee.modules.services        2026-04..2026-08 2.2.2                |..................==|
  ?   org.apache.directory.api             2026-05..2026-05 2.1.8                |..................=.|
  R   io.github.qudtlib                    2024-12..2025-10 7.1.1                |................==..|
  R   de.jball                             2025-07..2025-07 0.9.0                |.................=..|
  R   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17        |.....======.........|
    + 1 more: com.jwebmp.jpms.commons
org.mongodb.bson.record.codec  [fork: keep `org.mongodb`, `com.guicedee.modules.services` still publishes the name]
  ? * org.mongodb                          2022-04..2026-08 5.10.0               |..........==========|
  ?   com.guicedee.modules.services        2026-08..2026-08 2.2.2                |...................=|
org.mongodb.driver.reactivestreams  [fork: keep `org.mongodb`, `com.guicedee.modules.services` still publishes the name]
  ? * org.mongodb                          2020-01..2026-08 5.10.0               |......==============|
  ?   com.guicedee.modules.services        2026-08..2026-08 2.2.2                |...................=|
org.reactivestreams  [fork: keep `org.reactivestreams`, `com.guicedee.modules.services` still publishes the name]
  A * org.reactivestreams                  2017-12..2022-05 1.0.4                |..==========........|
  ?   com.guicedee.modules.services        2026-07..2026-08 2.2.2                |...................=|
  R   dev.ikm.jpms                         2024-01..2024-08 1.0.3-r6             |..............==....|
org.bouncycastle.provider  [fork: keep `org.bouncycastle`, `io.gitee.maluole` still publishes the name]
  A * org.bouncycastle                     2018-07..2026-08 1.85.2               |...=================|
  ?   io.gitee.maluole                     2026-06..2026-08 1.3.1.RELEASE        |...................=|
  R   org.terracotta                       2022-02..2026-08 3.3.49               |..........==========|
  R   org.wso2.charon                      2019-04..2026-06 5.1.5                |....================|
  R   org.dcache                           2025-12..2026-06 3.4.3                |..................==|
  R   org.apache.pinot                     2025-02..2026-06 1.5.1                |................====|
    + 83 more: org.openeuler, org.apache.dolphinscheduler, org.exploit, de.moritzpetersen, org.apache.seatunnel, de.splatgames.aether.pack, net.maritimeconnectivity.pki, io.github.swiyu-admin-ch, org.hyperledger.fabric, io.kestra.storage, io.aiven, io.kestra.plugin, (+71 more)
io.netty.transport  [fork: keep `io.netty`, `org.reactivemongo` still publishes the name]
  A * io.netty                             2017-12..2026-08 4.1.137.Final        |..==================|
  ?   org.reactivemongo                    2026-08..2026-08 1.1.0-RC21.patch1    |...................=|
  R   com.sportradar.unifiedodds.sdk       2026-02..2026-08 5.0.0-rc5            |..................==|
  R   com.arcadedb                         2025-04..2026-08 26.8.1               |................====|
  R   io.github.lukaszsamson               2026-04..2026-07 0.2.0                |..................==|
  ?   io.github.qbsstg                     2026-06..2026-06 0.18.0               |...................=|
    + 1 more: io.karatelabs
io.netty.transport.unix.common  [fork: keep `io.netty`, `org.reactivemongo` still publishes the name]
  ? * io.netty                             2017-12..2026-08 4.1.137.Final        |..==================|
  ?   org.reactivemongo                    2026-08..2026-08 1.1.0-RC21.patch1    |...................=|
jakarta.validation  [fork: keep `jakarta.validation`, `dev.getelements.elements` still publishes the name]
  A * jakarta.validation                   2020-02..2025-10 4.0.0-M1             |......============..|
  R   dev.getelements.elements             2025-03..2026-08 3.8.16               |................====|
  ?   com.meta-analyzer                    2026-06..2026-06 1.0.0                |...................=|
  R   io.flux-capacitor                    2023-05..2024-06 0.943.0              |............====....|
  R   org.pipservices                      2024-06..2024-06 1.0.0                |...............=....|
  R   no.nav.security                      2023-04..2023-11 3.2.0                |............===.....|
    + 2 more: com.neko233, com.guicedee.services
ch.randelshofer.fastdoubleparser  [fork: keep `ch.randelshofer`, `org.apache.inlong` still publishes the name]
  A * ch.randelshofer                      2022-11..2024-11 2.0.1                |............====....|
  R   org.apache.inlong                    2023-10..2026-08 2.4.0                |.............=======|
  R   za.co.absa.spline.agent.spark        2023-06..2026-08 2.4.0-RC3            |.............=======|
  ?   org.jruby                            2026-07..2026-07 10.1.1.0             |...................=|
  R   com.clickzetta                       2024-08..2026-07 2.0.1                |...............=====|
  R   software.amazon.smithy.java          2026-05..2026-06 1.4.0                |..................==|
    + 33 more: io.github.solven-eu.pepper, org.metafacture, org.jetbrains.kotlinx.dataframe, com.cjbooms, io.github.cmu-phil, io.github.hkarthik7, io.kestra.plugin, io.trino, org.apache.arrow, com.databricks, org.sonarsource.text, org.openrewrite, (+21 more)
org.bouncycastle.pkix  [fork: keep `org.bouncycastle`, `org.apache.inlong` still publishes the name]
  A * org.bouncycastle                     2018-07..2026-07 1.85                 |...=================|
  R   org.apache.inlong                    2025-11..2026-08 2.4.0                |.................===|
  R   com.alibaba.ververica                2022-10..2026-07 1.20-vvr-11.8.0-1-jdk11 |...........=========|
  ?   com.datarobot                        2026-07..2026-07 11.2.42              |...................=|
  R   com.exasol                           2024-03..2026-06 26.2.8               |..............======|
  R   com.github.melin                     2026-04..2026-06 1.0.3                |..................==|
    + 58 more: de.tk.opensource, org.apache.pinot, com.github.toolarium, io.streamnative.connectors, org.hyperledger.fabric, org.finos.legend.engine, io.kestra.plugin, org.jetbrains, org.lucee, com.nhn.gameanvil, io.sermant, com.linecorp.armeria, (+46 more)
org.apache.arrow.flight.core  [fork: keep `org.apache.arrow`, `io.mishmash.stacks.patches` still publishes the name]
  ? * org.apache.arrow                     2024-04..2026-03 19.0.0               |..............=====.|
  ?   io.mishmash.stacks.patches           2026-03..2026-08 19.0.0-mmio.1.2      |..................==|
org.apache.arrow.format  [fork: keep `org.apache.arrow`, `io.mishmash.stacks.patches` still publishes the name]
  ? * org.apache.arrow                     2024-04..2026-03 19.0.0               |..............=====.|
  ?   io.mishmash.stacks.patches           2026-03..2026-08 19.0.0-mmio.1.2      |..................==|
org.apache.arrow.memory.core  [fork: keep `org.apache.arrow`, `io.mishmash.stacks.patches` still publishes the name]
  ? * org.apache.arrow                     2024-04..2026-03 19.0.0               |..............=====.|
  ?   io.mishmash.stacks.patches           2026-03..2026-08 19.0.0-mmio.1.2      |..................==|
org.apache.arrow.memory.unsafe  [fork: keep `org.apache.arrow`, `io.mishmash.stacks.patches` still publishes the name]
  ? * org.apache.arrow                     2024-04..2026-03 19.0.0               |..............=====.|
  ?   io.mishmash.stacks.patches           2026-03..2026-08 19.0.0-mmio.1.2      |..................==|
org.apache.arrow.vector  [fork: keep `org.apache.arrow`, `io.mishmash.stacks.patches` still publishes the name]
  A * org.apache.arrow                     2024-04..2026-03 19.0.0               |..............=====.|
  ?   io.mishmash.stacks.patches           2026-03..2026-08 19.0.0-mmio.1.2      |..................==|
  ?   io.indextables                       2026-07..2026-07 0.6.0-rc2_spark_4.1.2 |...................=|
  A   org.apache.pinot                     2025-09..2025-09 1.4.0                |.................=..|
  R   com.salesforce.datacloud             2025-05..2025-08 0.34.0               |................==..|
org.apache.bcel  [fork: keep `org.apache.bcel`, `io.spicelabs` still publishes the name]
  A * org.apache.bcel                      2017-09..2026-01 6.12.0               |.==================.|
  ?   io.spicelabs                         2026-08..2026-08 0.17.4               |...................=|
  R   com.github.spotbugs                  2024-10..2026-07 4.7.0                |...............=====|
  R   com.github.kwart.jd                  2025-10..2025-10 1.3.0-beta-1         |.................=..|
  R   org.checkerframework.annotatedlib    2018-02..2020-10 6.5.0                |..======............|
  R   org.checkerframework                 2018-02..2018-02 0.0.1                |..=.................|
org.apache.commons.pool2  [fork: keep `org.apache.commons`, `com.liquibase.ext` still publishes the name]
  A * org.apache.commons                   2020-07..2025-12 2.13.1               |.......============.|
  ?   com.liquibase.ext                    2026-06..2026-08 5.2.2                |...................=|
  R   org.openjproxy                       2026-03..2026-07 0.5.3-beta           |..................==|
  R   org.apache.directory.api             2023-10..2026-05 2.1.8                |.............======.|
  R   io.github.caobahuong                 2026-05..2026-05 0.1.1                |..................=.|
  R   org.apache.druid.extensions.contrib  2024-06..2026-04 37.0.0               |...............====.|
    + 5 more: com.redis, org.noear, io.github.hexsook, org.apache.storm, com.vlkan.log4j2
com.github.snksoft.crc  [fork: keep `com.github.snksoft`, `org.jurr.java.omniusb` still publishes the name]
  ? * com.github.snksoft                   2022-11..2022-11 1.1.0                |...........=........|
  ?   org.jurr.java.omniusb                2026-06..2026-08 1.0.3                |...................=|
com.googlecode.javaewah  [fork: keep `com.googlecode.javaewah`, `io.github.baokhang83.blastradius` still publishes the name]
  A * com.googlecode.javaewah              2023-03..2023-03 1.2.3                |............=.......|
  ?   io.github.baokhang83.blastradius     2026-07..2026-08 0.3.3                |...................=|
  R   org.meyvn                            2023-07..2026-05 1.9.4                |.............======.|
  R   org.liquibase.ext                    2024-02..2025-01 0.4.0                |..............===...|
  R   io.kestra.plugin                     2023-07..2024-08 0.17.2               |.............===....|
net.kyori.adventure.text.serializer.bungeecord  [fork: keep `net.kyori`, `studio.mevera.adventure` still publishes the name]
  A * net.kyori                            2021-09..2025-07 4.4.1                |.........=========..|
  ?   studio.mevera.adventure              2026-08..2026-08 1.0.2                |...................=|
  R   io.github.toxicity188                2026-05..2026-06 5.2.0                |..................==|
jakarta.inject  [fork: keep `jakarta.inject`, `io.github.jolt-community.jolt` still publishes the name]
  A * jakarta.inject                       2020-04..2021-10 2.0.1                |......====..........|
  R   io.github.jolt-community.jolt        2025-09..2026-08 1.3.0                |.................===|
  ?   org.kill-bill.billing                2026-06..2026-07 0.42.2               |...................=|
  R   com.google.gerrit                    2023-10..2026-07 3.13.8               |.............=======|
  ?   org.openidentityplatform.openig      2026-06..2026-06 6.1.1                |...................=|
  R   io.joynr.java.core                   2026-01..2026-06 1.24.8               |..................==|
    + 13 more: dev.getelements.elements, it.netgrid, network.sloud.hytale, com.uchicom, com.google.tsunami, io.github.avistotelecom, org.apache.opennlp, com.google.template, io.github.openfeign.querydsl, org.reploop, com.guicedee.services, io.github.jbock-java, (+1 more)
org.apache.commons.text  [fork: keep `org.apache.commons`, `de.fraunhofer.iosb.ilt.FROST-Server` still publishes the name]
  A * org.apache.commons                   2018-03..2025-12 1.15.0               |..=================.|
  ?   de.fraunhofer.iosb.ilt.FROST-Server  2026-07..2026-07 2.8.0                |...................=|
  R   org.bonitasoft.engine.data           2026-01..2026-06 11.1.0               |..................==|
  R   com.telamin.fluxtion                 2026-05..2026-06 1.0.9                |..................==|
  ?   net.officefloor.tutorial             2026-06..2026-06 4.0.0                |..................=.|
  R   com.vmlens                           2026-01..2026-04 1.2.28               |..................=.|
    + 18 more: ru.biosoft.diagrams, io.github.venkateshamurthy, dev.jbang, io.github.davidwhitlock.joy, io.github.pro4d, org.bidib.com.github.markusbernhardt, fr.lirmm.graphik, io.github.noeltoy, io.github.mderevyankoaqa, com.salesforce.functions, org.opendaylight.aaa, org.zowe.client.java.sdk, (+6 more)
fr.lirmm.integraal.rule_analysis  [fork: keep `fr.lirmm.graphik`, `fr.inria.rules` still publishes the name]
  ? * fr.lirmm.graphik                     2023-11..2025-06 2.0.7                |.............=====..|
  ?   fr.inria.rules                       2026-06..2026-07 3.2.2                |...................=|
com.github.javaparser.symbolsolver.core  [fork: keep `com.github.javaparser`, `org.key-project.proofjava` still publishes the name]
  A * com.github.javaparser                2018-01..2026-05 3.28.2               |..=================.|
  ?   org.key-project.proofjava            2026-06..2026-07 3.28.0-K13.6         |...................=|
  R   org.mvel.javaparser                  2026-02..2026-02 3.25.5-mvel3-1       |..................=.|
  R   io.joern                             2022-06..2022-06 3.24.3-SL3           |...........=........|
com.github.javaparser.core.serialization  [fork: keep `com.github.javaparser`, `org.key-project.proofjava` still publishes the name]
  A * com.github.javaparser                2018-11..2026-05 3.28.2               |....===============.|
  ?   org.key-project.proofjava            2026-06..2026-07 3.28.0-K13.6         |...................=|
  R   org.mvel.javaparser                  2026-02..2026-02 3.25.5-mvel3-1       |..................=.|
  R   io.joern                             2022-06..2022-06 3.24.3-SL3           |...........=........|
com.github.javaparser.core  [fork: keep `com.github.javaparser`, `org.key-project.proofjava` still publishes the name]
  A * com.github.javaparser                2017-12..2026-05 3.28.2               |..=================.|
  ?   org.key-project.proofjava            2026-06..2026-07 3.28.0-K13.6         |...................=|
  R   org.checkerframework                 2023-09..2026-05 3.28.1               |.............======.|
  R   org.mvel.javaparser                  2026-02..2026-02 3.25.5-mvel3-1       |..................=.|
  R   io.joern                             2022-06..2022-06 3.24.3-SL3           |...........=........|
com.googlecode.lanterna  [fork: keep `com.googlecode.lanterna`, `io.gitlab.cupofcode` still publishes the name]
  A * com.googlecode.lanterna              2020-01..2026-03 3.1.5                |......=============.|
  ?   io.gitlab.cupofcode                  2026-07..2026-07 1.2.2                |...................=|
  R   io.github.dawciobiel                 2026-03..2026-03 3.0.0                |..................=.|
  R   io.github.ReleaseStandard.CodeEditor.editor 2021-10..2021-10 23.30.0              |.........=..........|
org.apache.logging.log4j.core  [fork: keep `org.apache.logging.log4j`, `dk.dma.ais.lib` still publishes the name]
  A * org.apache.logging.log4j             2017-11..2026-07 2.25.5               |..==================|
  ?   dk.dma.ais.lib                       2026-07..2026-07 2.8.7                |...................=|
  R   org.lucee                            2022-03..2026-06 2.26.0.0             |..........==========|
  ?   nl.tno.org.portico                   2026-05..2026-05 2.1.3                |..................=.|
  R   app.freerouting                      2026-05..2026-05 2.2.4                |..................=.|
  R   org.beilstein                        2026-05..2026-05 1.1.2                |..................=.|
    + 20 more: me.bechberger, io.openems, com.kount, com.github.aquality-automation, com.ghgande, com.github.bilderherunterlader, com.gemecosystem.gemjar, io.github.alien-tools, com.webforj, io.github.egonw, com.liferay, de.fraunhofer.iem, (+8 more)
com.headius.invokebinder  [fork: keep `com.headius`, `org.sahli.asciidoc.confluence.publisher` still publishes the name]
  A * com.headius                          2017-10..2026-07 1.16                 |.===================|
  R   org.sahli.asciidoc.confluence.publisher 2026-03..2026-07 0.35.0               |..................==|
  R   org.springframework.cloud            2024-11..2026-06 4.3.4                |................====|
  R   com.lealceldeiro                     2025-02..2026-03 2.3.1                |................===.|
  R   ch.ifocusit.livingdoc                2025-05..2025-05 2.16                 |................=...|
  R   de.jcup.asp                          2021-09..2022-04 1.4.1                |.........==.........|
    + 2 more: org.asciidoctor, org.jruby
org.hsqldb  [fork: keep `org.hsqldb`, `io.synclite` still publishes the name]
  A * org.hsqldb                           2021-04..2024-11 2.7.4                |........========....|
  ?   io.synclite                          2026-07..2026-07 1.0.0                |...................=|
  R   com.github.massamany                 2025-08..2025-09 1.2.3                |.................=..|
  ?   ch.zizka.csvcruncher                 2021-11..2023-09 2.7.0                |.........=====......|
  R   org.lucee                            2023-06..2023-06 2.7.2.jdk11          |.............=......|
dev.tamboui.widgets  [fork: keep `dev.tamboui`, `com.github.jlangch` still publishes the name]
  ? * dev.tamboui                          2026-02..2026-06 0.4.0                |..................==|
  ?   com.github.jlangch                   2026-06..2026-07 1.13.12              |...................=|
org.eclipse.microprofile.config  [fork: keep `org.eclipse.microprofile.config`, `io.vidocq.ravel` still publishes the name]
  ? * org.eclipse.microprofile.config      2026-01..2026-04 3.1.1                |..................=.|
  ?   io.vidocq.ravel                      2026-05..2026-07 0.2.0                |..................==|
org.kotlincrypto.hash.sha2  [fork: keep `org.kotlincrypto.hash`, `io.github.zzzyyylllty.sertraline` still publishes the name]
  ? * org.kotlincrypto.hash                2024-03..2025-09 0.8.0                |..............====..|
  ?   io.github.zzzyyylllty.sertraline     2026-06..2026-07 3.9.6                |..................==|
com.azure.json  [fork: keep `com.azure`, `io.indextables` still publishes the name]
  A * com.azure                            2022-09..2026-01 1.5.1                |...........========.|
  ?   io.indextables                       2026-07..2026-07 0.6.0-rc2_spark_4.0.3 |...................=|
  R   com.microsoft.azure.kusto            2026-04..2026-05 7.0.8                |..................=.|
org.apache.commons.exec  [fork: keep `org.apache.commons`, `cn.net.pap.md5.jmh` still publishes the name]
  ? * org.apache.commons                   2024-01..2025-11 1.6.0                |..............====..|
  ?   cn.net.pap.md5.jmh                   2026-07..2026-07 0.0.4                |...................=|
  ?   com.github.zhkl0228                  2026-03..2026-03 2.0.2                |..................=.|
com.jtconnors.socket  [fork: keep `com.jtconnors.socket`, `io.github.jtconnors` still publishes the name]
  A * com.jtconnors.socket                 2019-02..2019-02 11.0.1               |....=...............|
  ?   io.github.jtconnors                  2026-06..2026-06 21.0.0               |...................=|
  A   com.jtconnors                        2019-02..2019-02 11.0.3               |....=...............|
de.agilecoders.wicket.webjars  [fork: keep `de.agilecoders.wicket.webjars`, `io.github.arieslab` still publishes the name]
  ? * de.agilecoders.wicket.webjars        2023-10..2025-12 4.0.14               |.............======.|
  ?   io.github.arieslab                   2026-06..2026-06 2.4.2                |...................=|
io.github.humbleui.skija.android.arm64  [fork: keep `io.github.humbleui`, `com.behemiron.engine` still publishes the name]
  ? * io.github.humbleui                   2026-01..2026-06 0.143.17             |..................==|
  ?   com.behemiron.engine                 2026-06..2026-06 0.143.17             |...................=|
io.github.humbleui.skija.android.x64  [fork: keep `io.github.humbleui`, `com.behemiron.engine` still publishes the name]
  ? * io.github.humbleui                   2026-01..2026-06 0.143.17             |..................==|
  ?   com.behemiron.engine                 2026-06..2026-06 0.143.17             |...................=|
io.github.humbleui.skija.linux.arm64  [fork: keep `io.github.humbleui`, `com.behemiron.engine` still publishes the name]
  ? * io.github.humbleui                   2025-11..2026-06 0.143.17             |.................===|
  ?   com.behemiron.engine                 2026-06..2026-06 0.143.17             |...................=|
io.github.humbleui.skija.windows.arm64  [fork: keep `io.github.humbleui`, `com.behemiron.engine` still publishes the name]
  ? * io.github.humbleui                   2026-05..2026-06 0.143.17             |..................==|
  ?   com.behemiron.engine                 2026-06..2026-06 0.143.17             |...................=|
org.apache.commons.configuration2  [fork: keep `org.apache.commons`, `org.wso2.orbit.org.apache.commons` still publishes the name]
  A * org.apache.commons                   2017-10..2026-05 2.15.1               |.==================.|
  ?   org.wso2.orbit.org.apache.commons    2026-06..2026-06 2.15.1.wso2v1        |...................=|
  R   org.neo4j.procedure                  2024-04..2026-01 4.4.0.40             |..............=====.|
  R   com.databricks.labs                  2025-05..2026-01 0.6.17               |................===.|
  R   software.amazon.s3tables             2024-12..2025-08 0.1.8                |................==..|
  R   com.sonatype.central.testing.amazon  2025-06..2025-06 0.1.7                |.................=..|
    + 2 more: org.bidib.com.github.markusbernhardt, consulting.freiheitsgrade.patched
org.apache.jena.jdbc.driver.remote  [fork: keep `org.apache.jena`, `com.vaimee` still publishes the name]
  ? * org.apache.jena                      2019-10..2023-10 4.10.0               |.....=========......|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.ext.com.google  [fork: keep `org.apache.jena`, `com.vaimee` still publishes the name]
  ? * org.apache.jena                      2018-06..2023-04 4.8.0                |...==========.......|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.jdbc.core  [fork: keep `org.apache.jena`, `com.vaimee` still publishes the name]
  ? * org.apache.jena                      2019-10..2023-10 4.10.0               |.....=========......|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.jdbc.driver.tdb  [fork: keep `org.apache.jena`, `com.vaimee` still publishes the name]
  ? * org.apache.jena                      2019-10..2023-10 4.10.0               |.....=========......|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.permissions  [fork: keep `org.apache.jena`, `com.vaimee` still publishes the name]
  ? * org.apache.jena                      2018-06..2025-10 5.6.0                |...===============..|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
jakarta.json  [fork: keep `jakarta.json`, `com.vaimee` still publishes the name]
  A * jakarta.json                         2020-01..2023-10 2.1.3                |......========......|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
  R   org.eclipse.parsson                  2021-06..2026-05 1.1.9                |.........==========.|
  R   io.github.qudtlib                    2026-02..2026-02 7.2.0                |..................=.|
  R   com.arangodb                         2025-08..2026-01 1.9.0                |.................==.|
  R   io.quarkus                           2024-10..2025-02 3.18.4               |...............==...|
    + 9 more: org.openpreservation.jhove, zone.cogni.semanticz, com.exasol, io.github.changebooks, com.atomgraph.etl.csv, org.avaje.experiment, org.spdx, org.glassfish, com.mparticle
org.apache.jena.iri  [fork: keep `org.apache.jena`, `com.vaimee` still publishes the name]
  ? * org.apache.jena                      2018-06..2025-10 5.6.0                |...===============..|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.jdbc.driver.mem  [fork: keep `org.apache.jena`, `com.vaimee` still publishes the name]
  ? * org.apache.jena                      2019-10..2023-10 4.10.0               |.....=========......|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
com.aayushatharva.brotli4j  [fork: keep `com.aayushatharva.brotli4j`, `net.sourceforge.plantuml` still publishes the name]
  A * com.aayushatharva.brotli4j           2022-12..2026-04 1.23.0               |............=======.|
  ?   net.sourceforge.plantuml             2026-06..2026-06 1.2026.6             |...................=|
  R   org.apache.orc                       2024-11..2026-01 2.1.4                |...............====.|
org.osgi.framework  [fork: keep `org.osgi`, `org.apache.karaf` still publishes the name]
  ? * org.osgi                             2020-12..2020-12 1.10.0               |........=...........|
  ?   org.apache.karaf                     2025-08..2026-04 4.4.11               |.................==.|
org.apache.commons.fileupload2.jakarta.servlet6  [fork: keep `org.apache.commons`, `io.github.dhruvrawatdev` still publishes the name]
  ? * org.apache.commons                   2023-12..2026-02 2.0.0-M5             |..............=====.|
  R   io.github.dhruvrawatdev              2026-04..2026-04 1.0.1                |..................=.|
  R   cloud.piranha.dist                   2024-07..2025-06 25.6.0               |...............===..|
  R   io.telicent.smart-caches.graph       2024-06..2024-12 0.82.14              |...............==...|
  A   org.apache.jena                      2024-03..2024-07 5.1.0                |..............==....|
  R   io.telicent                          2024-06..2024-06 1.2.1                |...............=....|
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

## shaded (180)

The natural-namespace owner is the earliest and most-recent publisher; every other group merely shades or bundles the name. Resolution is unchanged; this just records the decision so the module drops off the report.

- The owner is also the most-recent publisher (there is no later successor).
- The owner is the closest groupId to the module name: it shares the longest leading-segment prefix (hyphens ignored), even if the name is not strictly under it.
- Allow the natural owner; reject every group that merely shades the name.

| count | current owner | new owner(s) |
|---:|---|---|
| 1 | `io.github.classgraph` | `io.github.classgraph, io.github.it-gorillaz, io.github.mtrevisan, io.github.shenbinglife, io.github.eisop, io.github.azodox, io.github.kojiv, io.github.openfeign.querydsl, io.github.mingeun0507, io.github.alabijak, io.github.api-ghost-agent` |
| 1 | `io.github.pdvrieze.xmlutil` | `io.github.pdvrieze.xmlutil, io.github.pdvrieze` |
| 1 | `org.flywaydb` | `org.flywaydb, org.flywaydb.pro, org.flywaydb.enterprise` |
| 1 | `org.junit.jupiter` | `org.junit.jupiter, org.junit.platform` |
| 1 | `org.neo4j` | `org.neo4j, org.neo4j.connectors` |
| 1 | `org.neo4j.bolt` | `org.neo4j.bolt, org.neo4j.connectors` |

```
com.github.luben.zstd_jni  [owned by `com.github.luben`; 20 other group(s) shade the name]
  A * com.github.luben                     2018-06..2026-08 1.5.7-16             |...=================|
  ?   io.github.kpn-dsh                    2026-08..2026-08 0.4.1                |...................=|
  ?   com.scylladb                         2026-08..2026-08 2.0.6                |...................=|
  ?   com.timecho.timechodb                2026-06..2026-08 2.0.11.1             |...................=|
  R   org.apache.celeborn                  2025-06..2026-08 0.7.0                |.................===|
  R   com.aliyun.openservices.eas          2024-06..2026-07 2.0.33               |...............=====|
    + 15 more: org.apache.tsfile, org.apache.iotdb, com.snowflake, ai.h2o, com.timecho.iotdb, org.chipsalliance, io.moderne, io.spicelabs, io.nosqlbench, io.github.willena, io.github.fernandolopes, org.apache.amoro, (+3 more)
io.netty.internal.tcnative.openssl.linux.x86_64  [owned by `io.netty`; 11 other group(s) shade the name]
  A * io.netty                             2022-05..2026-08 2.0.83.Final         |..........==========|
  ?   com.solacecoe.connectors             2026-08..2026-08 4.0.0                |...................=|
  R   io.smallrye                          2026-04..2026-08 0.2.3                |..................==|
  ?   io.camunda.connector                 2026-06..2026-08 8.7.23               |..................==|
  R   com.azure.cosmos.spark               2026-02..2026-07 4.49.2               |..................==|
  R   org.apache.iotdb                     2026-04..2026-07 2.0.10               |..................==|
    + 6 more: io.zipkin.dependencies, com.danielflower.apprunner, io.karatelabs, io.opentelemetry.javaagent, com.github.emc-mongoose, io.servicetalk
io.netty.tcnative.classes.openssl  [owned by `io.netty`; 5 other group(s) shade the name]
  A * io.netty                             2022-03..2026-08 2.0.83.Final         |..........==========|
  R   org.neo4j.driver                     2024-11..2026-08 4.4.27               |................====|
  ?   io.vertx                             2026-06..2026-08 4.5.32               |...................=|
  ?   io.fabric8                           2026-06..2026-06 7.8.0                |...................=|
  R   io.kestra.plugin                     2024-10..2025-06 0.23.0               |...............===..|
  R   eu.michael-simons.neo4j              2024-10..2025-06 2.17.4               |...............===..|
com.azure.http.netty  [owned by `com.azure`; 1 other group(s) shade the name]
  ? * com.azure                            2019-11..2026-08 1.16.7               |......==============|
  ?   io.lakefs                            2026-06..2026-06 0.24.1               |...................=|
org.seleniumhq.selenium.api  [owned by `org.seleniumhq.selenium`; 3 other group(s) shade the name]
  A * org.seleniumhq.selenium              2019-09..2026-08 4.48.0               |.....===============|
  ?   com.github.aquality-automation       2026-08..2026-08 7.5.0                |...................=|
  R   com.github.saikrishna321             2023-12..2024-01 14.0.4               |..............=.....|
  R   ru.sbtqa.tag.pagefactory             2022-09..2023-08 21.0.1               |...........===......|
org.seleniumhq.selenium.devtools_v151  [owned by `org.seleniumhq.selenium`; 1 other group(s) shade the name]
  ? * org.seleniumhq.selenium              2026-08..2026-08 4.48.0               |...................=|
  ?   com.github.aquality-automation       2026-08..2026-08 4.17.0               |...................=|
org.seleniumhq.selenium.grid  [owned by `org.seleniumhq.selenium`; 1 other group(s) shade the name]
  ? * org.seleniumhq.selenium              2019-09..2026-08 4.48.0               |.....===============|
  ?   com.infotel.seleniumRobot            2022-09..2022-09 5.0.4                |...........=........|
org.jline.terminal.jni  [owned by `org.jline`; 1 other group(s) shade the name]
  ? * org.jline                            2023-10..2026-08 4.4.0                |.............=======|
  ?   fish.payara.extras                   2026-08..2026-08 7.2026.8             |...................=|
org.xerial.sqlitejdbc  [owned by `org.xerial`; 25 other group(s) shade the name]
  A * org.xerial                           2021-06..2026-08 3.53.4.0             |.........===========|
  ?   tech.molecules                       2026-08..2026-08 0.3.6                |...................=|
  ?   dev.mintychochip                     2026-08..2026-08 26.8.12.3            |...................=|
  R   com.codenameone                      2024-08..2026-07 7.0.262              |...............=====|
  R   org.apache.tika                      2023-01..2026-07 3.3.2                |............========|
  ?   int.esa.nmf.sdk                      2026-07..2026-07 5.0                  |...................=|
    + 20 more: at.hugob.plugin.library, io.github.zhyt1985, com.clapbxt, dev.aga.sqlite, io.toxicity.sqlite-mc, io.github.frame-dev, org.apache.sedona, nz.co.gregs, app.cash.sqldelight, io.github.pacocarlesimo, me.gulya.sqldelight, solutions.a2.oracle.iceberg, (+8 more)
org.flywaydb.core  [owned by `org.flywaydb`; 4 other group(s) shade the name]
  A * org.flywaydb                         2017-12..2026-08 13.4.0               |..==================|
  ?   io.github.wu9007                     2026-08..2026-08 9.9.9                |...................=|
  R   io.github.coolbeevip                 2023-03..2026-01 9.15.2.5             |............=======.|
  R   io.gitee.gbase8s                     2025-01..2025-01 6.5.7                |................=...|
  A   org.flywaydb.enterprise              2020-04..2022-07 9.0.0                |......======........|
  R   io.github.linceln                    2021-07..2021-08 5.0.8                |.........=..........|
    + 1 more: org.flywaydb.pro
org.neo4j.cypherdsl.core  [owned by `org.neo4j`; 0 other group(s) shade the name]
  ? * org.neo4j                            2020-07..2026-08 2025.3.1             |.......=============|
  ?   org.neo4j.connectors                 2026-06..2026-07 6.0.0-s_2.13         |...................=|
com.clickhouse.jdbc  [owned by `com.clickhouse`; 4 other group(s) shade the name]
  A * com.clickhouse                       2021-12..2026-08 0.9.9                |..........==========|
  ?   io.github.tridog                     2026-07..2026-07 0.7.3-2              |...................=|
  R   org.apache.seatunnel                 2023-10..2024-10 1.0.2                |.............===....|
  R   io.kestra.plugin                     2022-04..2023-03 0.6.1                |..........===.......|
  R   ru.yandex.clickhouse                 2021-12..2021-12 0.3.2                |..........=.........|
io.github.pdvrieze.testutil  [owned by `io.github.pdvrieze.xmlutil`; 0 other group(s) shade the name]
  ? * io.github.pdvrieze.xmlutil           2025-07..2026-08 1.0.2.1              |.................===|
  ?   io.github.pdvrieze                   2026-06..2026-06 1.0.0-rc3            |...................=|
io.github.classgraph  [owned by `io.github.classgraph`; 72 other group(s) shade the name]
  A * io.github.classgraph                 2018-08..2026-08 4.8.194              |...=================|
  R   org.finos.legend.engine              2025-10..2026-08 4.140.2              |.................===|
  R   org.dominokit                        2024-01..2026-07 1.0.3                |..............======|
  R   com.datarobot                        2022-02..2026-07 11.2.42              |..........==========|
  ?   dev.sixpack                          2026-07..2026-07 0.6.2                |...................=|
  R   tech.neander                         2024-05..2026-06 0.0.4                |..............=====.|
    + 77 more: org.finos.legend.sdlc, org.paramixel, org.javastro.vodsl, software.amazon.glue, dev.getelements.elements.crossfire, com.netgrif, org.plumelib, com.google.tsunami, org.finos.legend.depot, io.github.api-ghost-agent, org.geneweaver, cn.ashersu.omni.model, (+65 more)
com.graphqljava  [owned by `com.graphql-java`; 3 other group(s) shade the name]
  A * com.graphql-java                     2020-11..2026-08 26.1                 |........============|
  ?   com.guicedee.modules.services        2026-07..2026-08 2.2.2                |...................=|
  R   com.liferay                          2025-05..2025-05 19.11.JAKARTA-LIFERAY-PATCHED-1 |................=...|
  R   io.github.my-workforce               2022-07..2023-07 19.6                 |...........===......|
io.opentelemetry.instrumentation_annotations  [owned by `io.opentelemetry.instrumentation`; 1 other group(s) shade the name]
  ? * io.opentelemetry.instrumentation     2023-10..2026-08 2.31.1               |.............=======|
  ?   io.vidocq.humboldt                   2026-06..2026-07 0.2.0                |...................=|
org.hibernate.orm.ant  [owned by `org.hibernate.orm`; 1 other group(s) shade the name]
  ? * org.hibernate.orm                    2021-10..2026-08 7.4.6.Final          |.........===========|
  ?   io.github.martinhickson              2026-08..2026-08 6.6.7-bravura-1      |...................=|
org.hibernate.orm.community.dialects  [owned by `org.hibernate.orm`; 1 other group(s) shade the name]
  ? * org.hibernate.orm                    2022-05..2026-08 7.4.6.Final          |...........=========|
  ?   io.github.martinhickson              2026-08..2026-08 6.6.7-bravura-1      |...................=|
org.hibernate.orm.jfr  [owned by `org.hibernate.orm`; 1 other group(s) shade the name]
  ? * org.hibernate.orm                    2023-11..2026-08 7.4.6.Final          |..............======|
  ?   io.github.martinhickson              2026-08..2026-08 6.6.7-bravura-1      |...................=|
org.hibernate.orm.vector  [owned by `org.hibernate.orm`; 1 other group(s) shade the name]
  ? * org.hibernate.orm                    2023-11..2026-08 7.4.6.Final          |..............======|
  ?   io.github.martinhickson              2026-08..2026-08 6.6.7-bravura-1      |...................=|
org.hibernate.orm.ucp  [owned by `org.hibernate.orm`; 1 other group(s) shade the name]
  ? * org.hibernate.orm                    2024-03..2026-08 6.6.56.Final         |..............======|
  ?   io.github.martinhickson              2026-08..2026-08 6.6.7-bravura-1      |...................=|
org.hibernate.orm.enhance.maven.plugin  [owned by `org.hibernate.orm.tooling`; 1 other group(s) shade the name]
  ? * org.hibernate.orm.tooling            2022-05..2026-08 6.6.56.Final         |...........=========|
  ?   io.github.martinhickson              2026-08..2026-08 6.6.7-bravura-1      |...................=|
com.networknt.schema  [owned by `com.networknt`; 11 other group(s) shade the name]
  A * com.networknt                        2023-04..2026-08 2.0.7                |............========|
  ?   io.krabka                            2026-08..2026-08 1.1.0                |...................=|
  R   dev.dokimos                          2026-05..2026-05 0.15.0               |..................=.|
  R   io.github.jdbcx                      2026-01..2026-05 1.1.1                |..................=.|
  R   com.intuit.isl                       2026-02..2026-04 1.2.0                |..................=.|
  R   org.wiremock                         2023-07..2026-01 1.0.0-beta.5         |.............======.|
    + 6 more: org.sonarsource.text, org.wiremock.extensions, com.github.nagyesta.abort-mission.reports, org.wiremock.integrations, xyz.block, com.github.tomakehurst
org.mockbukkit.mockbukkit  [owned by `org.mockbukkit.mockbukkit`; 2 other group(s) shade the name]
  A * org.mockbukkit.mockbukkit            2024-11..2026-08 4.116.3              |...............=====|
  ?   com.620cloud.server                  2026-08..2026-08 26.2-48              |...................=|
  R   com.mineplex.studio                  2024-11..2026-02 1.21.11-R0.1-43      |...............====.|
com.azure.storage.common  [owned by `com.azure`; 2 other group(s) shade the name]
  A * com.azure                            2019-09..2026-08 12.34.1              |.....===============|
  R   org.gaul                             2025-11..2026-07 3.3.0                |.................===|
  ?   org.tomitribe.s3proxy                2026-06..2026-06 3.0.1                |...................=|
net.bytebuddy.agent  [owned by `net.bytebuddy`; 20 other group(s) shade the name]
  A * net.bytebuddy                        2017-05..2026-08 1.18.12              |.===================|
  R   com.google.gerrit                    2020-02..2026-07 3.13.8               |......==============|
  ?   co.hyperprobe                        2026-07..2026-07 1.2.19               |...................=|
  R   co.elastic.apm                       2019-02..2026-06 1.56.0               |....================|
  R   com.macstab.chaos.jvm                2026-04..2026-04 1.0.0                |..................=.|
  R   cn.easii                             2026-04..2026-04 1.0.6                |..................=.|
    + 15 more: me.bechberger, cn.langpy, com.leanxcale, com.zto.fire, software.amazon.disco, com.nerdvision, com.idea-aedi, org.openstreetmap.atlas, com.netsensia.rivalchess, com.github.liuzhengyang, com.securenative.java, com.amazonaws, (+3 more)
com.fasterxml.jackson.datatype.joda  [owned by `com.fasterxml.jackson.datatype`; 4 other group(s) shade the name]
  A * com.fasterxml.jackson.datatype       2017-10..2026-08 2.22.2               |.===================|
  R   io.kestra.plugin                     2024-06..2024-08 0.18.1               |...............=....|
  R   org.apache.beam                      2022-05..2023-05 2.48.0               |...........===......|
  ?   io.siddhi                            2022-11..2023-02 5.1.28               |...........==.......|
  R   com.seeq                             2021-12..2022-08 55.4.9-v202208021422 |..........==........|
com.fasterxml.jackson.module.jaxb  [owned by `com.fasterxml.jackson.module`; 97 other group(s) shade the name]
  A * com.fasterxml.jackson.module         2017-10..2026-08 2.22.2               |.===================|
  ?   com.liquibase                        2026-06..2026-08 5.2.2                |...................=|
  R   com.solacecoe.connectors             2024-07..2026-08 3.2.0                |...............=====|
  R   com.oceanbase                        2024-12..2026-07 1.5                  |................====|
  R   com.seeq                             2022-07..2026-07 65.2.3-v202607141628 |...........=========|
  R   io.github.solven-eu.cleanthat        2025-07..2026-06 2.26                 |.................===|
    + 92 more: org.apache.gravitino, com.datastax.oss, io.cdap.cdap, org.apache.dolphinscheduler, com.facebook.presto.spark, com.rovio.ingest, org.apache.seatunnel, org.apache.pulsar, com.ascentstream.pulsar, io.github.dodogeny, io.streamnative.connectors, org.apache.phoenix, (+80 more)
com.microsoft.onnxruntime  [owned by `com.microsoft.onnxruntime`; 1 other group(s) shade the name]
  ? * com.microsoft.onnxruntime            2020-06..2026-08 1.29.0               |.......=============|
  ?   io.github.eduramiba                  2026-06..2026-06 1.26.0               |...................=|
org.bytedeco.pytorch.linux.x86_64.gpu  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2021-08..2026-08 2.13.0-1.5.14        |.........===========|
  ?   io.github.mullerhai                  2026-08..2026-08 2.13.0-1.5.14-beta-08.01 |...................=|
io.opentelemetry.api  [owned by `io.opentelemetry`; 1 other group(s) shade the name]
  ? * io.opentelemetry                     2020-03..2026-08 1.65.0               |......==============|
  ?   io.vidocq.humboldt                   2026-06..2026-07 0.2.0                |...................=|
io.opentelemetry.context  [owned by `io.opentelemetry`; 1 other group(s) shade the name]
  ? * io.opentelemetry                     2020-11..2026-08 1.65.0               |.......=============|
  ?   io.vidocq.humboldt                   2026-06..2026-07 0.2.0                |...................=|
io.vertx.auth.common  [owned by `io.vertx`; 1 other group(s) shade the name]
  ? * io.vertx                             2020-05..2026-08 4.5.32               |.......=============|
  ?   ai.tock                              2026-07..2026-07 26.3.3               |...................=|
org.htmlunit  [owned by `org.htmlunit`; 1 other group(s) shade the name]
  ? * org.htmlunit                         2026-05..2026-08 5.4.0                |..................==|
  ?   com.nordstrom.ui-tools               2026-07..2026-07 4.46.0               |...................=|
org.bytedeco.opencv  [owned by `org.bytedeco`; 2 other group(s) shade the name]
  A * org.bytedeco                         2019-04..2026-08 4.14.0-1.5.14        |....================|
  ?   io.github.mullerhai                  2026-07..2026-08 4.13.0-1.5.14-beta-08 |...................=|
  R   us.ihmc                              2023-06..2025-02 4.7.0-1.5.11-ihmc-2  |.............====...|
org.bytedeco.opencv.android.arm64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2019-04..2026-08 4.14.0-1.5.14        |....================|
  ?   io.github.mullerhai                  2026-07..2026-08 4.13.0-1.5.14-beta-08 |...................=|
org.bytedeco.opencv.android.x86_64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2019-04..2026-08 4.14.0-1.5.14        |....================|
  ?   io.github.mullerhai                  2026-07..2026-08 4.13.0-1.5.14-beta-08 |...................=|
org.bytedeco.opencv.ios.arm64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2019-04..2026-08 4.14.0-1.5.14        |....================|
  ?   io.github.mullerhai                  2026-07..2026-08 4.13.0-1.5.14-beta-08 |...................=|
org.bytedeco.opencv.linux.arm64  [owned by `org.bytedeco`; 2 other group(s) shade the name]
  A * org.bytedeco                         2019-07..2026-08 4.14.0-1.5.14        |.....===============|
  ?   io.github.mullerhai                  2026-07..2026-08 4.13.0-1.5.14-beta-08 |...................=|
  R   us.ihmc                              2025-01..2025-02 4.7.0-1.5.11-ihmc-2  |................=...|
org.bytedeco.opencv.macosx.arm64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2022-02..2026-08 4.14.0-1.5.14        |..........==========|
  ?   io.github.mullerhai                  2026-07..2026-08 4.13.0-1.5.14-beta-08 |...................=|
org.bytedeco.opencv.windows.x86_64  [owned by `org.bytedeco`; 2 other group(s) shade the name]
  A * org.bytedeco                         2019-04..2026-08 4.14.0-1.5.14        |....================|
  ?   io.github.mullerhai                  2026-07..2026-08 4.13.0-1.5.14-beta-08 |...................=|
  R   us.ihmc                              2023-06..2025-02 4.7.0-1.5.11-ihmc-2  |.............====...|
org.bytedeco.opencv.ios.x86_64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2019-04..2026-08 4.14.0-1.5.14        |....================|
  ?   io.github.mullerhai                  2026-07..2026-08 4.13.0-1.5.14-beta-08 |...................=|
org.bytedeco.opencv.linux.x86_64  [owned by `org.bytedeco`; 2 other group(s) shade the name]
  A * org.bytedeco                         2019-04..2026-08 4.14.0-1.5.14        |....================|
  ?   io.github.mullerhai                  2026-07..2026-08 4.13.0-1.5.14-beta-08 |...................=|
  R   us.ihmc                              2023-06..2025-02 4.7.0-1.5.11-ihmc-2  |.............====...|
org.bytedeco.opencv.macosx.x86_64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2019-04..2026-08 4.14.0-1.5.14        |....================|
  ?   io.github.mullerhai                  2026-07..2026-08 4.13.0-1.5.14-beta-08 |...................=|
org.bytedeco.opencv.platform  [owned by `org.bytedeco`; 4 other group(s) shade the name]
  A * org.bytedeco                         2019-04..2026-08 4.14.0-1.5.14        |....================|
  ?   io.github.mullerhai                  2026-07..2026-08 4.13.0-1.5.14-beta-08 |...................=|
  R   io.github.extractpdf4j               2025-12..2026-07 2.2.0                |..................==|
  R   io.github.mehulimukherjee            2025-08..2025-09 0.1.1                |.................=..|
  R   us.ihmc                              2023-06..2023-06 4.7.0-1.5.9          |.............=......|
org.htmlunit.cyberneko  [owned by `org.htmlunit`; 2 other group(s) shade the name]
  ? * org.htmlunit                         2026-05..2026-08 5.4.0                |..................==|
  ?   com.nordstrom.ui-tools               2026-07..2026-07 4.45.0               |...................=|
  ?   org.seleniumhq.selenium              2026-06..2026-06 4.45.0               |...................=|
org.bytedeco.tensorrt  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2019-04..2026-08 11.2-1.5.14          |....================|
  ?   io.github.mullerhai                  2026-08..2026-08 11.1-1.5.14-beta-08  |...................=|
org.bytedeco.tensorrt.linux.arm64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2021-07..2026-08 11.2-1.5.14          |.........===========|
  ?   io.github.mullerhai                  2026-08..2026-08 11.1-1.5.14-beta-08  |...................=|
org.bytedeco.tensorrt.linux.x86_64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2019-04..2026-08 11.2-1.5.14          |....================|
  ?   io.github.mullerhai                  2026-08..2026-08 11.1-1.5.14-beta-08  |...................=|
org.bytedeco.tensorrt.platform  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2019-04..2026-08 11.2-1.5.14          |....================|
  ?   io.github.mullerhai                  2026-08..2026-08 11.1-1.5.14-beta-08  |...................=|
org.bytedeco.tensorrt.windows.x86_64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2020-04..2026-08 11.2-1.5.14          |......==============|
  ?   io.github.mullerhai                  2026-08..2026-08 11.1-1.5.14-beta-08  |...................=|
org.htmlunit.websocket.client  [owned by `org.htmlunit`; 1 other group(s) shade the name]
  ? * org.htmlunit                         2026-05..2026-08 5.4.0                |..................==|
  ?   org.wetator                          2026-06..2026-06 5.2.0                |...................=|
org.bytedeco.tritonserver  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2022-02..2026-08 2.71.0-1.5.14        |..........==========|
  ?   io.github.mullerhai                  2026-08..2026-08 2.70.0-1.5.14-beta-08 |...................=|
org.bytedeco.tritonserver.linux.x86_64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2022-02..2026-08 2.71.0-1.5.14        |..........==========|
  ?   io.github.mullerhai                  2026-08..2026-08 2.70.0-1.5.14-beta-08 |...................=|
org.bytedeco.tritonserver.platform  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2022-02..2026-08 2.71.0-1.5.14        |..........==========|
  ?   io.github.mullerhai                  2026-08..2026-08 2.70.0-1.5.14-beta-08 |...................=|
org.junit.jupiter.engine  [owned by `org.junit.jupiter`; 10 other group(s) shade the name]
  A * org.junit.jupiter                    2017-07..2026-08 6.1.3                |.===================|
  ?   io.github.rpost                      2026-07..2026-07 6.0.0-M2             |...................=|
  R   org.apache.tika                      2024-07..2026-06 4.0.0-beta-1         |...............=====|
  R   com.janeluo                          2026-04..2026-04 1.0.0                |..................=.|
  R   org.eclipse.pass                     2023-06..2024-01 1.3.0                |.............==.....|
  R   com.quantego                         2023-11..2023-11 0.6.5                |.............=......|
    + 6 more: com.salesforce.kafka.test, com.trendyol, org.caseine, org.eclipse.rdf4j, com.github.jnr, org.junit.platform
org.htmlunit.cssparser  [owned by `org.htmlunit`; 1 other group(s) shade the name]
  ? * org.htmlunit                         2026-05..2026-08 5.4.0                |..................==|
  ?   org.seleniumhq.selenium              2026-07..2026-07 4.46.0               |...................=|
org.bytedeco.numpy  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2019-04..2026-08 2.5.1-1.5.14         |....================|
  ?   io.github.mullerhai                  2026-07..2026-08 2.5.0-1.5.14-beta-08 |...................=|
org.bytedeco.numpy.linux.arm64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2019-11..2026-08 2.5.1-1.5.14         |.....===============|
  ?   io.github.mullerhai                  2026-07..2026-08 2.5.0-1.5.14-beta-08 |...................=|
org.bytedeco.numpy.linux.x86_64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2019-04..2026-08 2.5.1-1.5.14         |....================|
  ?   io.github.mullerhai                  2026-07..2026-08 2.5.0-1.5.14-beta-08 |...................=|
org.bytedeco.numpy.macosx.arm64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2024-11..2026-08 2.5.1-1.5.14         |...............=====|
  ?   io.github.mullerhai                  2026-07..2026-08 2.5.0-1.5.14-beta-08 |...................=|
org.bytedeco.numpy.macosx.x86_64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2019-04..2026-08 2.5.1-1.5.14         |....================|
  ?   io.github.mullerhai                  2026-07..2026-08 2.5.0-1.5.14-beta-08 |...................=|
org.bytedeco.numpy.platform  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2019-04..2026-08 2.5.1-1.5.14         |....================|
  ?   io.github.mullerhai                  2026-07..2026-08 2.5.0-1.5.14-beta-08 |...................=|
org.bytedeco.numpy.windows.x86_64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2019-04..2026-08 2.5.1-1.5.14         |....================|
  ?   io.github.mullerhai                  2026-07..2026-08 2.5.0-1.5.14-beta-08 |...................=|
org.bytedeco.ffmpeg  [owned by `org.bytedeco`; 3 other group(s) shade the name]
  A * org.bytedeco                         2019-04..2026-08 8.1.2-1.5.14         |....================|
  ?   io.github.mullerhai                  2026-07..2026-08 8.1.1-1.5.14-beta-08 |...................=|
  R   us.ihmc                              2023-06..2025-01 7.1-1.5.11-ihmc-2    |.............====...|
  R   com.github.javacpp-nogpl             2020-09..2020-09 4.3.1-1.5.4          |.......=............|
org.bytedeco.ffmpeg.android.arm64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2019-04..2026-08 8.1.2-1.5.14         |....================|
  ?   io.github.mullerhai                  2026-07..2026-08 8.1.1-1.5.14-beta-08 |...................=|
org.bytedeco.ffmpeg.linux.x86_64  [owned by `org.bytedeco`; 3 other group(s) shade the name]
  A * org.bytedeco                         2019-04..2026-08 8.1.2-1.5.14         |....================|
  ?   io.github.mullerhai                  2026-07..2026-08 8.1.1-1.5.14-beta-08 |...................=|
  R   us.ihmc                              2023-06..2025-01 7.1-1.5.11-ihmc-2    |.............====...|
  R   com.github.javacpp-nogpl             2020-09..2020-09 4.3.1-1.5.4          |.......=............|
org.bytedeco.ffmpeg.android.x86_64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2019-04..2026-08 8.1.2-1.5.14         |....================|
  ?   io.github.mullerhai                  2026-07..2026-08 8.1.1-1.5.14-beta-08 |...................=|
org.bytedeco.ffmpeg.linux.arm64  [owned by `org.bytedeco`; 2 other group(s) shade the name]
  A * org.bytedeco                         2019-07..2026-08 8.1.2-1.5.14         |.....===============|
  ?   io.github.mullerhai                  2026-07..2026-08 8.1.1-1.5.14-beta-08 |...................=|
  R   us.ihmc                              2025-01..2025-01 7.1-1.5.11-ihmc-2    |................=...|
org.bytedeco.ffmpeg.macosx.arm64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2022-02..2026-08 8.1.2-1.5.14         |..........==========|
  ?   io.github.mullerhai                  2026-07..2026-08 8.1.1-1.5.14-beta-08 |...................=|
org.bytedeco.ffmpeg.macosx.x86_64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2019-04..2026-08 8.1.2-1.5.14         |....================|
  ?   io.github.mullerhai                  2026-07..2026-08 8.1.1-1.5.14-beta-08 |...................=|
org.bytedeco.ffmpeg.platform  [owned by `org.bytedeco`; 2 other group(s) shade the name]
  A * org.bytedeco                         2019-04..2026-08 8.1.2-1.5.14         |....================|
  ?   io.github.mullerhai                  2026-07..2026-08 8.1.1-1.5.14-beta-08 |...................=|
  R   us.ihmc                              2023-06..2023-06 6.0-1.5.9            |.............=......|
org.bytedeco.ffmpeg.windows.x86_64  [owned by `org.bytedeco`; 2 other group(s) shade the name]
  A * org.bytedeco                         2019-04..2026-08 8.1.2-1.5.14         |....================|
  ?   io.github.mullerhai                  2026-07..2026-08 8.1.1-1.5.14-beta-08 |...................=|
  R   us.ihmc                              2023-06..2025-01 7.1-1.5.11-ihmc-2    |.............====...|
org.bytedeco.openblas  [owned by `org.bytedeco`; 2 other group(s) shade the name]
  A * org.bytedeco                         2019-04..2026-08 0.3.34-1.5.14        |....================|
  ?   io.github.mullerhai                  2026-07..2026-08 0.3.33-1.5.14-beta-08 |...................=|
  R   us.ihmc                              2023-06..2025-02 0.3.23-1.5.11-ihmc-2 |.............====...|
org.bytedeco.openblas.android.arm64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2019-10..2026-08 0.3.34-1.5.14        |.....===============|
  ?   io.github.mullerhai                  2026-07..2026-08 0.3.33-1.5.14-beta-08 |...................=|
org.bytedeco.openblas.android.x86_64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2019-10..2026-08 0.3.34-1.5.14        |.....===============|
  ?   io.github.mullerhai                  2026-07..2026-08 0.3.33-1.5.14-beta-08 |...................=|
org.bytedeco.openblas.ios.arm64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2019-10..2026-08 0.3.34-1.5.14        |.....===============|
  ?   io.github.mullerhai                  2026-07..2026-08 0.3.33-1.5.14-beta-08 |...................=|
org.bytedeco.openblas.ios.x86_64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2019-10..2026-08 0.3.34-1.5.14        |.....===============|
  ?   io.github.mullerhai                  2026-07..2026-08 0.3.33-1.5.14-beta-08 |...................=|
org.bytedeco.openblas.linux.arm64  [owned by `org.bytedeco`; 2 other group(s) shade the name]
  A * org.bytedeco                         2019-10..2026-08 0.3.34-1.5.14        |.....===============|
  ?   io.github.mullerhai                  2026-07..2026-08 0.3.33-1.5.14-beta-08 |...................=|
  R   us.ihmc                              2025-01..2025-02 0.3.23-1.5.11-ihmc-2 |................=...|
org.bytedeco.openblas.linux.x86_64  [owned by `org.bytedeco`; 2 other group(s) shade the name]
  A * org.bytedeco                         2019-04..2026-08 0.3.34-1.5.14        |....================|
  ?   io.github.mullerhai                  2026-07..2026-08 0.3.33-1.5.14-beta-08 |...................=|
  R   us.ihmc                              2023-06..2025-02 0.3.23-1.5.11-ihmc-2 |.............====...|
org.bytedeco.openblas.macosx.arm64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2022-02..2026-08 0.3.34-1.5.14        |..........==========|
  ?   io.github.mullerhai                  2026-07..2026-08 0.3.33-1.5.14-beta-08 |...................=|
org.bytedeco.openblas.macosx.x86_64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2019-04..2026-08 0.3.34-1.5.14        |....================|
  ?   io.github.mullerhai                  2026-07..2026-08 0.3.33-1.5.14-beta-08 |...................=|
org.bytedeco.openblas.platform  [owned by `org.bytedeco`; 3 other group(s) shade the name]
  A * org.bytedeco                         2019-04..2026-08 0.3.34-1.5.14        |....================|
  ?   io.github.mullerhai                  2026-07..2026-08 0.3.33-1.5.14-beta-08 |...................=|
  R   us.ihmc                              2023-06..2023-06 0.3.23-1.5.9         |.............=......|
  R   de.uni-mannheim.informatik.dws.melt  2022-06..2022-06 3.3                  |...........=........|
org.bytedeco.openblas.windows.x86_64  [owned by `org.bytedeco`; 2 other group(s) shade the name]
  A * org.bytedeco                         2019-04..2026-08 0.3.34-1.5.14        |....================|
  ?   io.github.mullerhai                  2026-07..2026-08 0.3.33-1.5.14-beta-08 |...................=|
  R   us.ihmc                              2023-06..2025-02 0.3.23-1.5.11-ihmc-2 |.............====...|
org.bytedeco.cuda.redist.cudnn.linux.arm64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................===|
  ?   io.github.mullerhai                  2026-08..2026-08 13.3-9.24-1.5.14-beta-08 |...................=|
org.bytedeco.cuda.redist.cudnn.linux.x86_64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................===|
  ?   io.github.mullerhai                  2026-08..2026-08 13.3-9.24-1.5.14-beta-08 |...................=|
org.bytedeco.cuda.redist.cudnn.windows.x86_64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................===|
  ?   io.github.mullerhai                  2026-08..2026-08 13.3-9.24-1.5.14-beta-08 |...................=|
org.bytedeco.cuda.platform.redist.cudnn  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................===|
  ?   io.github.mullerhai                  2026-07..2026-08 13.3-9.24-1.5.14-beta-08 |...................=|
org.bytedeco.cuda.redist.cudnn  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................===|
  ?   io.github.mullerhai                  2026-08..2026-08 13.3-9.24-1.5.14-beta-08 |...................=|
org.bytedeco.cuda.platform.redist.cusolver  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................===|
  ?   io.github.mullerhai                  2026-07..2026-08 13.3-9.24-1.5.14-beta-08 |...................=|
org.bytedeco.cuda.redist.cusolver  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................===|
  ?   io.github.mullerhai                  2026-08..2026-08 13.3-9.24-1.5.14-beta-08 |...................=|
org.bytedeco.cuda.redist.cusolver.linux.arm64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................===|
  ?   io.github.mullerhai                  2026-08..2026-08 13.3-9.24-1.5.14-beta-08 |...................=|
org.bytedeco.cuda.redist.cusolver.linux.x86_64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................===|
  ?   io.github.mullerhai                  2026-08..2026-08 13.3-9.24-1.5.14-beta-08 |...................=|
org.bytedeco.cuda.redist.cusolver.windows.x86_64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................===|
  ?   io.github.mullerhai                  2026-08..2026-08 13.3-9.24-1.5.14-beta-08 |...................=|
org.bytedeco.cuda.platform.redist.cublas  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................===|
  ?   io.github.mullerhai                  2026-07..2026-08 13.3-9.24-1.5.14-beta-08 |...................=|
org.bytedeco.cuda.redist.cublas  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................===|
  ?   io.github.mullerhai                  2026-08..2026-08 13.3-9.24-1.5.14-beta-08 |...................=|
org.bytedeco.cuda.redist.cublas.linux.arm64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................===|
  ?   io.github.mullerhai                  2026-08..2026-08 13.3-9.24-1.5.14-beta-08 |...................=|
org.bytedeco.cuda.redist.cublas.linux.x86_64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................===|
  ?   io.github.mullerhai                  2026-08..2026-08 13.3-9.24-1.5.14-beta-08 |...................=|
org.bytedeco.cuda.redist.cublas.windows.x86_64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................===|
  ?   io.github.mullerhai                  2026-08..2026-08 13.3-9.24-1.5.14-beta-08 |...................=|
org.bytedeco.cuda.platform.redist.npp  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................===|
  ?   io.github.mullerhai                  2026-07..2026-08 13.3-9.24-1.5.14-beta-08 |...................=|
org.bytedeco.cuda.redist.npp  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................===|
  ?   io.github.mullerhai                  2026-07..2026-08 13.3-9.24-1.5.14-beta-08 |...................=|
org.bytedeco.cuda.redist.npp.linux.arm64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................===|
  ?   io.github.mullerhai                  2026-08..2026-08 13.3-9.24-1.5.14-beta-08 |...................=|
org.bytedeco.cuda.redist.npp.linux.x86_64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................===|
  ?   io.github.mullerhai                  2026-07..2026-08 13.3-9.24-1.5.14-beta-08 |...................=|
org.bytedeco.cuda.redist.npp.windows.x86_64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................===|
  ?   io.github.mullerhai                  2026-07..2026-08 13.3-9.24-1.5.14-beta-08 |...................=|
org.bytedeco.cuda.platform.redist.nccl  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................===|
  ?   io.github.mullerhai                  2026-07..2026-08 13.3-9.24-1.5.14-beta-08 |...................=|
org.bytedeco.cuda.redist.nccl  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................===|
  ?   io.github.mullerhai                  2026-07..2026-08 13.3-9.24-1.5.14-beta-08 |...................=|
org.bytedeco.cuda.redist.nccl.linux.arm64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................===|
  ?   io.github.mullerhai                  2026-07..2026-08 13.3-9.24-1.5.14-beta-08 |...................=|
org.bytedeco.cuda.redist.nccl.linux.x86_64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................===|
  ?   io.github.mullerhai                  2026-08..2026-08 13.3-9.24-1.5.14-beta-08 |...................=|
org.bytedeco.cuda.redist.nccl.windows.x86_64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................===|
  ?   io.github.mullerhai                  2026-07..2026-08 13.3-9.24-1.5.14-beta-08 |...................=|
org.bytedeco.cuda.platform.redist.cusparse  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................===|
  ?   io.github.mullerhai                  2026-07..2026-08 13.3-9.24-1.5.14-beta-08 |...................=|
org.bytedeco.cuda.redist.cusparse  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................===|
  ?   io.github.mullerhai                  2026-08..2026-08 13.3-9.24-1.5.14-beta-08 |...................=|
org.bytedeco.cuda.redist.cusparse.linux.arm64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................===|
  ?   io.github.mullerhai                  2026-08..2026-08 13.3-9.24-1.5.14-beta-08 |...................=|
org.bytedeco.cuda.redist.cusparse.linux.x86_64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................===|
  ?   io.github.mullerhai                  2026-08..2026-08 13.3-9.24-1.5.14-beta-08 |...................=|
org.bytedeco.cuda.redist.cusparse.windows.x86_64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................===|
  ?   io.github.mullerhai                  2026-08..2026-08 13.3-9.24-1.5.14-beta-08 |...................=|
org.bytedeco.cuda.platform.redist.nvcomp  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................===|
  ?   io.github.mullerhai                  2026-07..2026-08 13.3-9.24-1.5.14-beta-08 |...................=|
org.bytedeco.cuda.redist.nvcomp  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................===|
  ?   io.github.mullerhai                  2026-07..2026-08 13.3-9.24-1.5.14-beta-08 |...................=|
org.bytedeco.cuda.redist.nvcomp.linux.arm64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................===|
  ?   io.github.mullerhai                  2026-07..2026-08 13.3-9.24-1.5.14-beta-08 |...................=|
org.bytedeco.cuda.redist.nvcomp.linux.x86_64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................===|
  ?   io.github.mullerhai                  2026-07..2026-08 13.3-9.24-1.5.14-beta-08 |...................=|
org.bytedeco.cuda.redist.nvcomp.windows.x86_64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................===|
  ?   io.github.mullerhai                  2026-07..2026-08 13.3-9.24-1.5.14-beta-08 |...................=|
org.bytedeco.cuda.platform.redist  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2020-09..2026-08 13.3-9.25-1.5.14     |.......=============|
  ?   io.github.mullerhai                  2026-07..2026-08 13.3-9.24-1.5.14-beta-08 |...................=|
org.bytedeco.cuda.redist  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................===|
  ?   io.github.mullerhai                  2026-07..2026-08 13.3-9.24-1.5.14-beta-08 |...................=|
org.bytedeco.cuda.redist.linux.arm64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................===|
  ?   io.github.mullerhai                  2026-07..2026-08 13.3-9.24-1.5.14-beta-08 |...................=|
org.bytedeco.cuda.redist.linux.x86_64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................===|
  ?   io.github.mullerhai                  2026-07..2026-08 13.3-9.24-1.5.14-beta-08 |...................=|
org.bytedeco.cuda.redist.windows.x86_64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................===|
  ?   io.github.mullerhai                  2026-07..2026-08 13.3-9.24-1.5.14-beta-08 |...................=|
org.bytedeco.cuda  [owned by `org.bytedeco`; 2 other group(s) shade the name]
  A * org.bytedeco                         2019-04..2026-08 13.3-9.25-1.5.14     |....================|
  ?   io.github.mullerhai                  2026-07..2026-08 13.3-9.24-1.5.14-beta-08 |...................=|
  R   us.ihmc                              2025-01..2025-01 12.6-9.5-1.5.11-ihmc-2 |................=...|
org.bytedeco.cuda.linux.x86_64  [owned by `org.bytedeco`; 2 other group(s) shade the name]
  A * org.bytedeco                         2019-04..2026-08 13.3-9.25-1.5.14     |....================|
  ?   io.github.mullerhai                  2026-07..2026-08 13.3-9.24-1.5.14-beta-08 |...................=|
  R   us.ihmc                              2025-01..2025-01 12.6-9.5-1.5.11-ihmc-2 |................=...|
org.bytedeco.cuda.platform  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2019-04..2026-08 13.3-9.25-1.5.14     |....================|
  ?   io.github.mullerhai                  2026-07..2026-08 13.3-9.24-1.5.14-beta-08 |...................=|
org.bytedeco.cuda.linux.arm64  [owned by `org.bytedeco`; 2 other group(s) shade the name]
  A * org.bytedeco                         2020-04..2026-08 13.3-9.25-1.5.14     |......==============|
  ?   io.github.mullerhai                  2026-07..2026-08 13.3-9.24-1.5.14-beta-08 |...................=|
  R   us.ihmc                              2025-01..2025-01 12.6-9.5-1.5.11-ihmc-2 |................=...|
org.bytedeco.cuda.windows.x86_64  [owned by `org.bytedeco`; 2 other group(s) shade the name]
  A * org.bytedeco                         2019-04..2026-08 13.3-9.25-1.5.14     |....================|
  ?   io.github.mullerhai                  2026-07..2026-08 13.3-9.24-1.5.14-beta-08 |...................=|
  R   us.ihmc                              2025-01..2025-01 12.6-9.5-1.5.11-ihmc-2 |................=...|
org.bytedeco.cpython  [owned by `org.bytedeco`; 3 other group(s) shade the name]
  A * org.bytedeco                         2019-04..2026-08 3.14.7-1.5.14        |....================|
  ?   io.github.mullerhai                  2026-07..2026-08 3.14.6-1.5.14-beta-08 |...................=|
  R   us.ihmc                              2025-02..2025-02 3.11.3-1.5.11-ihmc-2 |................=...|
  R   cn.wangshuyi                         2023-10..2023-10 3.10.13-1.5.7.2      |.............=......|
org.bytedeco.cpython.linux.arm64  [owned by `org.bytedeco`; 2 other group(s) shade the name]
  A * org.bytedeco                         2019-11..2026-08 3.14.7-1.5.14        |.....===============|
  ?   io.github.mullerhai                  2026-07..2026-08 3.14.6-1.5.14-beta-08 |...................=|
  R   us.ihmc                              2025-02..2025-02 3.11.3-1.5.11-ihmc-2 |................=...|
org.bytedeco.cpython.linux.x86_64  [owned by `org.bytedeco`; 3 other group(s) shade the name]
  A * org.bytedeco                         2019-04..2026-08 3.14.7-1.5.14        |....================|
  ?   io.github.mullerhai                  2026-07..2026-08 3.14.6-1.5.14-beta-08 |...................=|
  R   us.ihmc                              2025-02..2025-02 3.11.3-1.5.11-ihmc-2 |................=...|
  R   cn.wangshuyi                         2023-10..2023-10 3.10.13-1.5.7.2      |.............=......|
org.bytedeco.cpython.macosx.arm64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2024-11..2026-08 3.14.7-1.5.14        |...............=====|
  ?   io.github.mullerhai                  2026-07..2026-08 3.14.6-1.5.14-beta-08 |...................=|
org.bytedeco.cpython.macosx.x86_64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2019-04..2026-08 3.14.7-1.5.14        |....================|
  ?   io.github.mullerhai                  2026-07..2026-08 3.14.6-1.5.14-beta-08 |...................=|
org.bytedeco.cpython.platform  [owned by `org.bytedeco`; 2 other group(s) shade the name]
  A * org.bytedeco                         2019-04..2026-08 3.14.7-1.5.14        |....================|
  ?   io.github.mullerhai                  2026-07..2026-08 3.14.6-1.5.14-beta-08 |...................=|
  R   cn.wangshuyi                         2023-10..2023-10 3.10.2-1.5.7         |.............=......|
org.bytedeco.cpython.windows.x86_64  [owned by `org.bytedeco`; 2 other group(s) shade the name]
  A * org.bytedeco                         2019-04..2026-08 3.14.7-1.5.14        |....================|
  ?   io.github.mullerhai                  2026-07..2026-08 3.14.6-1.5.14-beta-08 |...................=|
  R   us.ihmc                              2025-02..2025-02 3.11.3-1.5.11-ihmc-2 |................=...|
org.bytedeco.javacpp  [owned by `org.bytedeco`; 10 other group(s) shade the name]
  A * org.bytedeco                         2019-04..2026-08 1.5.14               |....================|
  ?   io.github.mullerhai                  2026-07..2026-08 1.5.14-beta-08       |...................=|
  R   us.ihmc                              2023-06..2025-01 1.5.11-ihmc-2        |.............====...|
  R   ai.polus.utils                       2024-06..2024-06 2.0.7                |...............=....|
  R   com.github.vinhkhuc                  2023-05..2023-05 0.5                  |.............=......|
  R   com.alibaba.alink                    2021-10..2022-10 0.2.0-0.6            |.........===........|
    + 5 more: cn.langpy, org.flinkextended, org.deeplearning4j, org.nd4j, io.github.carschno
org.bytedeco.javacpp.android.arm64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2020-04..2026-08 1.5.14               |......==============|
  ?   io.github.mullerhai                  2026-07..2026-08 1.5.14-beta-08       |...................=|
org.bytedeco.javacpp.android.x86_64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2020-04..2026-08 1.5.14               |......==============|
  ?   io.github.mullerhai                  2026-07..2026-08 1.5.14-beta-08       |...................=|
org.bytedeco.javacpp.ios.arm64  [owned by `org.bytedeco`; 2 other group(s) shade the name]
  A * org.bytedeco                         2020-04..2026-08 1.5.14               |......==============|
  ?   io.github.mullerhai                  2026-07..2026-08 1.5.14-beta-08       |...................=|
  R   org.apache.tika                      2025-08..2026-07 3.3.2                |.................===|
org.bytedeco.javacpp.ios.x86_64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2020-04..2026-08 1.5.14               |......==============|
  ?   io.github.mullerhai                  2026-07..2026-08 1.5.14-beta-08       |...................=|
org.bytedeco.javacpp.linux.arm64  [owned by `org.bytedeco`; 2 other group(s) shade the name]
  A * org.bytedeco                         2020-04..2026-08 1.5.14               |......==============|
  ?   io.github.mullerhai                  2026-07..2026-08 1.5.14-beta-08       |...................=|
  R   us.ihmc                              2025-01..2025-01 1.5.11-ihmc-2        |................=...|
org.bytedeco.javacpp.linux.ppc64le  [owned by `org.bytedeco`; 2 other group(s) shade the name]
  A * org.bytedeco                         2020-04..2026-08 1.5.14               |......==============|
  ?   io.github.mullerhai                  2026-07..2026-08 1.5.14-beta-08       |...................=|
  R   org.apache.tika                      2025-01..2025-06 3.2.1                |................==..|
org.bytedeco.javacpp.linux.riscv64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-06..2026-08 1.5.14               |.................===|
  ?   io.github.mullerhai                  2026-07..2026-08 1.5.14-beta-08       |...................=|
org.bytedeco.javacpp.linux.x86_64  [owned by `org.bytedeco`; 2 other group(s) shade the name]
  A * org.bytedeco                         2020-04..2026-08 1.5.14               |......==============|
  ?   io.github.mullerhai                  2026-07..2026-08 1.5.14-beta-08       |...................=|
  R   us.ihmc                              2023-06..2025-01 1.5.11-ihmc-2        |.............====...|
org.bytedeco.javacpp.macosx.arm64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2021-03..2026-08 1.5.14               |........============|
  ?   io.github.mullerhai                  2026-07..2026-08 1.5.14-beta-08       |...................=|
org.bytedeco.javacpp.macosx.x86_64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2020-04..2026-08 1.5.14               |......==============|
  ?   io.github.mullerhai                  2026-07..2026-08 1.5.14-beta-08       |...................=|
org.bytedeco.javacpp.platform  [owned by `org.bytedeco`; 3 other group(s) shade the name]
  A * org.bytedeco                         2020-04..2026-08 1.5.14               |......==============|
  ?   io.github.mullerhai                  2026-07..2026-08 1.5.14-beta-08       |...................=|
  R   org.apache.tika                      2024-07..2024-10 3.0.0                |...............=....|
  R   us.ihmc                              2023-06..2023-06 1.5.9                |.............=......|
org.bytedeco.javacpp.windows.arm64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2026-02..2026-08 1.5.14               |..................==|
  ?   io.github.mullerhai                  2026-07..2026-08 1.5.14-beta-08       |...................=|
org.bytedeco.javacpp.windows.x86_64  [owned by `org.bytedeco`; 2 other group(s) shade the name]
  A * org.bytedeco                         2020-04..2026-08 1.5.14               |......==============|
  ?   io.github.mullerhai                  2026-07..2026-08 1.5.14-beta-08       |...................=|
  R   us.ihmc                              2023-06..2025-01 1.5.11-ihmc-2        |.............====...|
io.netty.transport.epoll.linux.x86_64  [owned by `io.netty`; 4 other group(s) shade the name]
  A * io.netty                             2022-05..2026-08 4.1.137.Final        |..........==========|
  ?   io.camunda.connector                 2026-06..2026-08 8.7.23               |..................==|
  R   org.apache.storm                     2025-05..2026-07 2.8.9                |................====|
  R   com.atscale.opensource               2026-01..2026-01 1.14                 |..................=.|
  R   org.readutf.orchestrator             2025-02..2025-02 2.0.0                |................=...|
org.eclipse.jetty.client  [owned by `org.eclipse.jetty`; 2 other group(s) shade the name]
  A * org.eclipse.jetty                    2018-11..2026-08 12.1.12              |....================|
  ?   ch.exense.step                       2026-06..2026-07 3.30.1               |...................=|
  R   org.exploit                          2024-10..2026-04 1.0.9                |...............====.|
org.apache.jena.base  [owned by `org.apache.jena`; 1 other group(s) shade the name]
  ? * org.apache.jena                      2018-06..2026-08 6.2.0                |...=================|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.text  [owned by `org.apache.jena`; 1 other group(s) shade the name]
  ? * org.apache.jena                      2018-06..2026-08 6.2.0                |...=================|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.arq  [owned by `org.apache.jena`; 1 other group(s) shade the name]
  ? * org.apache.jena                      2018-06..2026-08 6.2.0                |...=================|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.commonsrdf  [owned by `org.apache.jena`; 1 other group(s) shade the name]
  ? * org.apache.jena                      2020-05..2026-08 6.2.0                |.......=============|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.core  [owned by `org.apache.jena`; 1 other group(s) shade the name]
  ? * org.apache.jena                      2018-06..2026-08 6.2.0                |...=================|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.dboe.trans.data  [owned by `org.apache.jena`; 1 other group(s) shade the name]
  ? * org.apache.jena                      2018-12..2026-08 6.2.0                |....================|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.fuseki.access  [owned by `org.apache.jena`; 1 other group(s) shade the name]
  ? * org.apache.jena                      2018-12..2026-08 6.2.0                |....================|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.fuseki.core  [owned by `org.apache.jena`; 1 other group(s) shade the name]
  ? * org.apache.jena                      2018-12..2026-08 6.2.0                |....================|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.shex  [owned by `org.apache.jena`; 1 other group(s) shade the name]
  ? * org.apache.jena                      2021-09..2026-08 6.2.0                |.........===========|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.tdb2  [owned by `org.apache.jena`; 1 other group(s) shade the name]
  ? * org.apache.jena                      2018-06..2026-08 6.2.0                |...=================|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.rdfconnection  [owned by `org.apache.jena`; 1 other group(s) shade the name]
  ? * org.apache.jena                      2018-06..2026-08 6.2.0                |...=================|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.cmds  [owned by `org.apache.jena`; 1 other group(s) shade the name]
  ? * org.apache.jena                      2018-06..2026-08 6.2.0                |...=================|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.dboe.index  [owned by `org.apache.jena`; 1 other group(s) shade the name]
  ? * org.apache.jena                      2018-12..2026-08 6.2.0                |....================|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.dboe.index.test  [owned by `org.apache.jena`; 1 other group(s) shade the name]
  ? * org.apache.jena                      2018-12..2026-08 6.2.0                |....================|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.dboe.transaction  [owned by `org.apache.jena`; 1 other group(s) shade the name]
  ? * org.apache.jena                      2018-12..2026-08 6.2.0                |....================|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.shacl  [owned by `org.apache.jena`; 1 other group(s) shade the name]
  ? * org.apache.jena                      2019-10..2026-08 6.2.0                |.....===============|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.tdb  [owned by `org.apache.jena`; 1 other group(s) shade the name]
  ? * org.apache.jena                      2018-06..2026-08 6.2.0                |...=================|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.dboe.base  [owned by `org.apache.jena`; 1 other group(s) shade the name]
  ? * org.apache.jena                      2020-01..2026-08 6.2.0                |......==============|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.dboe.storage  [owned by `org.apache.jena`; 1 other group(s) shade the name]
  ? * org.apache.jena                      2019-10..2026-08 6.2.0                |.....===============|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.fuseki.main  [owned by `org.apache.jena`; 1 other group(s) shade the name]
  ? * org.apache.jena                      2018-12..2026-08 6.2.0                |....================|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.geosparql  [owned by `org.apache.jena`; 1 other group(s) shade the name]
  ? * org.apache.jena                      2019-09..2026-08 6.2.0                |.....===============|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
org.apache.jena.querybuilder  [owned by `org.apache.jena`; 1 other group(s) shade the name]
  ? * org.apache.jena                      2020-05..2026-08 6.2.0                |.......=============|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |...................=|
com.gizmodata.quack.jdbc  [owned by `com.gizmodata`; 1 other group(s) shade the name]
  ? * com.gizmodata                        2026-05..2026-07 0.2.0-alpha.6        |..................==|
  ?   dev.brikk.duckdb                     2026-07..2026-07 0.6.0                |...................=|
org.eclipse.jgit.pgm  [owned by `org.eclipse.jgit`; 1 other group(s) shade the name]
  ? * org.eclipse.jgit                     2017-12..2026-07 7.7.1.202607240634-r |..==================|
  ?   org.openl.jgit                       2022-01..2023-12 6.8.0.202311291450-openl |..........=====.....|
org.mavai.punit.examples  [owned by `org.mavai`; 1 other group(s) shade the name]
  ? * org.mavai                            2026-05..2026-07 0.7.1                |..................==|
  ?   org.javai                            2026-05..2026-05 0.6.99               |..................=.|
io.github.humbleui.skija.linux.x64  [owned by `io.github.humbleui`; 1 other group(s) shade the name]
  ? * io.github.humbleui                   2022-12..2026-06 0.119.6              |............========|
  ?   com.behemiron.engine                 2026-06..2026-06 0.143.17             |...................=|
io.github.humbleui.skija.windows.x64  [owned by `io.github.humbleui`; 1 other group(s) shade the name]
  ? * io.github.humbleui                   2022-12..2026-06 0.119.6              |............========|
  ?   com.behemiron.engine                 2026-06..2026-06 0.143.17             |...................=|
org.neo4j.bolt.connection.routed  [owned by `org.neo4j.bolt`; 0 other group(s) shade the name]
  ? * org.neo4j.bolt                       2025-03..2026-06 12.0.0               |................====|
  ?   org.neo4j.connectors                 2026-06..2026-06 6.0.0-RC01-s_2.13    |...................=|
```

## tld-dropped (17)

The dominant owner's groupId with its top-level domain dropped is the module-name prefix.

- The owner's groupId with its first segment (the top-level domain) removed is a prefix of the module name.
- Allow that owner; reject the rest.

```
koog.spring.ai.v2.starter.model.embedding.jvm  [owned by `ai.koog` (groupId minus TLD is the module prefix); 1 other group(s) shade the name]
  ? * ai.koog                              2026-07..2026-08 1.2.0-beta           |...................=|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-beta-kroog.3   |...................=|
koog.agents.jvm  [owned by `ai.koog` (groupId minus TLD is the module prefix); 1 other group(s) shade the name]
  ? * ai.koog                              2025-05..2026-08 1.2.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-kroog.3        |...................=|
koog.spring.ai.v2.starter.model.chat.jvm  [owned by `ai.koog` (groupId minus TLD is the module prefix); 1 other group(s) shade the name]
  ? * ai.koog                              2026-07..2026-08 1.2.0-beta           |...................=|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-beta-kroog.3   |...................=|
koog.spring.ai.starter.vector.store.jvm  [owned by `ai.koog` (groupId minus TLD is the module prefix); 1 other group(s) shade the name]
  ? * ai.koog                              2026-04..2026-08 1.2.0-beta           |..................==|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-beta-kroog.3   |...................=|
koog.ktor.jvm  [owned by `ai.koog` (groupId minus TLD is the module prefix); 1 other group(s) shade the name]
  ? * ai.koog                              2025-08..2026-08 1.2.0-beta           |.................===|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-beta-kroog.3   |...................=|
koog.spring.ai.starter.chat.memory.jvm  [owned by `ai.koog` (groupId minus TLD is the module prefix); 1 other group(s) shade the name]
  ? * ai.koog                              2026-04..2026-08 1.2.0-beta           |..................==|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-beta-kroog.3   |...................=|
koog.spring.ai.starter.model.embedding.jvm  [owned by `ai.koog` (groupId minus TLD is the module prefix); 1 other group(s) shade the name]
  ? * ai.koog                              2026-03..2026-08 1.2.0-beta           |..................==|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-beta-kroog.3   |...................=|
koog.spring.ai.v2.common.jvm  [owned by `ai.koog` (groupId minus TLD is the module prefix); 1 other group(s) shade the name]
  ? * ai.koog                              2026-07..2026-08 1.2.0-beta           |...................=|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-beta-kroog.3   |...................=|
koog.spring.boot.starter.jvm  [owned by `ai.koog` (groupId minus TLD is the module prefix); 1 other group(s) shade the name]
  ? * ai.koog                              2025-07..2026-08 1.2.0-beta           |.................===|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-beta-kroog.3   |...................=|
koog.spring.ai.starter.model.chat.jvm  [owned by `ai.koog` (groupId minus TLD is the module prefix); 1 other group(s) shade the name]
  ? * ai.koog                              2026-03..2026-08 1.2.0-beta           |..................==|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-beta-kroog.3   |...................=|
koog.spring.ai.v2.starter.vector.store.jvm  [owned by `ai.koog` (groupId minus TLD is the module prefix); 1 other group(s) shade the name]
  ? * ai.koog                              2026-07..2026-08 1.2.0-beta           |...................=|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-beta-kroog.3   |...................=|
koog.spring.ai.v2.starter.chat.memory.jvm  [owned by `ai.koog` (groupId minus TLD is the module prefix); 1 other group(s) shade the name]
  ? * ai.koog                              2026-07..2026-08 1.2.0-beta           |...................=|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-beta-kroog.3   |...................=|
koog.agents.additions.jvm  [owned by `ai.koog` (groupId minus TLD is the module prefix); 1 other group(s) shade the name]
  ? * ai.koog                              2026-05..2026-08 1.2.0-beta           |..................==|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-beta-kroog.3   |...................=|
koog.spring.ai.common.jvm  [owned by `ai.koog` (groupId minus TLD is the module prefix); 1 other group(s) shade the name]
  ? * ai.koog                              2026-04..2026-08 1.2.0-beta           |..................==|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-beta-kroog.3   |...................=|
roaringbitmap  [owned by `org.roaringbitmap` (groupId minus TLD is the module prefix); 3 other group(s) shade the name]
  A * org.roaringbitmap                    2023-09..2026-08 1.6.20               |.............=======|
  R   org.apache.celeborn                  2024-06..2026-08 0.7.0                |...............=====|
  ?   com.atomgraph.etl.csv                2026-06..2026-07 2.2.1                |...................=|
  R   org.bitlap                           2023-10..2023-10 1.0.1.0              |.............=......|
r2dbc.postgresql  [owned by `io.r2dbc` (groupId minus TLD is the module prefix); 3 other group(s) shade the name]
  A * io.r2dbc                             2019-11..2022-09 0.8.13.RELEASE       |......======........|
  ?   com.aliyun                           2026-08..2026-08 1.2.1                |...................=|
  R   org.postgresql                       2021-02..2026-06 1.1.2.RELEASE        |........============|
  R   com.yugabyte                         2023-12..2026-04 1.1.0-yb-2           |..............=====.|
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
  ? * com.fasterxml.jackson.datatype       2019-07..2026-08 2.22.2               |.....===============|
  ?   tools.jackson.datatype               2025-03..2026-08 3.2.2                |................====|
tuweni.bytes  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.consensys.tuweni                  2025-02..2026-08 2.8.0                |................====|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0                |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.concurrent  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.consensys.tuweni                  2025-02..2026-08 2.8.0                |................====|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0                |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.concurrent_coroutines  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.consensys.tuweni                  2025-02..2026-08 2.8.0                |................====|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0                |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.config  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.consensys.tuweni                  2025-02..2026-08 2.8.0                |................====|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0                |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.crypto  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.consensys.tuweni                  2025-02..2026-08 2.8.0                |................====|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0                |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.devp2p  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.consensys.tuweni                  2025-02..2026-08 2.8.0                |................====|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0                |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.io  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.consensys.tuweni                  2025-02..2026-08 2.8.0                |................====|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0                |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.junit  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.consensys.tuweni                  2025-02..2026-08 2.8.0                |................====|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0                |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.kademlia  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.consensys.tuweni                  2025-02..2026-08 2.8.0                |................====|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0                |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.net  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.consensys.tuweni                  2025-02..2026-08 2.8.0                |................====|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0                |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.rlp  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.consensys.tuweni                  2025-02..2026-08 2.8.0                |................====|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0                |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.ssz  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.consensys.tuweni                  2025-02..2026-08 2.8.0                |................====|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0                |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.toml  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.consensys.tuweni                  2025-02..2026-08 2.8.0                |................====|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0                |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
tuweni.units  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........===.......|
  ?   io.consensys.tuweni                  2025-02..2026-08 2.8.0                |................====|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0                |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |.............=......|
dagger  [owned by `com.google.dagger` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * com.google.dagger                    2021-06..2026-07 2.60.1               |.........===========|
  ?   io.github.licy5352.dagger            2022-02..2026-03 2.55-kim-rc1         |..........=========.|
  ?   me.gulya.dagger                      2025-08..2025-08 2.56.2-workaround10  |.................=..|
  ?   io.github.jbock-java                 2021-10..2022-03 2.41.2               |.........==.........|
mosaic.animation  [owned by `com.jakewharton.mosaic` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * com.jakewharton.mosaic               2025-08..2025-08 0.18.0               |.................=..|
  ?   ee.schimke.composeai.mosaic          2026-05..2026-05 0.18.0-1             |..................=.|
mosaic.runtime  [owned by `com.jakewharton.mosaic` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * com.jakewharton.mosaic               2025-08..2025-08 0.18.0               |.................=..|
  ?   ee.schimke.composeai.mosaic          2026-05..2026-05 0.18.0-1             |..................=.|
mosaic.tty  [owned by `com.jakewharton.mosaic` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * com.jakewharton.mosaic               2025-08..2025-08 0.18.0               |.................=..|
  ?   ee.schimke.composeai.mosaic          2026-05..2026-05 0.18.0-1             |..................=.|
mosaic.terminal  [owned by `com.jakewharton.mosaic` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * com.jakewharton.mosaic               2025-08..2025-08 0.18.0               |.................=..|
  ?   ee.schimke.composeai.mosaic          2026-05..2026-05 0.18.0-1             |..................=.|
mosaic.testing  [owned by `com.jakewharton.mosaic` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * com.jakewharton.mosaic               2025-08..2025-08 0.18.0               |.................=..|
  ?   ee.schimke.composeai.mosaic          2026-05..2026-05 0.18.0-1             |..................=.|
mosaic.tty.terminal  [owned by `com.jakewharton.mosaic` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * com.jakewharton.mosaic               2025-08..2025-08 0.18.0               |.................=..|
  ?   ee.schimke.composeai.mosaic          2026-05..2026-05 0.18.0-1             |..................=.|
tucache.core  [owned by `co.tunan.tucache` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * co.tunan.tucache                     2024-02..2024-02 1.0.4.RELEASE        |..............=.....|
  ?   io.github.tri5m                      2024-12..2026-04 1.0.6                |................===.|
tucache.spring.boot.autoconfigure  [owned by `co.tunan.tucache` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * co.tunan.tucache                     2024-02..2024-02 1.0.4.RELEASE        |..............=.....|
  ?   io.github.tri5m                      2024-12..2026-04 1.0.6                |................===.|
tucache.spring.boot.starter  [owned by `co.tunan.tucache` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * co.tunan.tucache                     2024-02..2024-02 1.0.4.RELEASE        |..............=.....|
  ?   io.github.tri5m                      2024-12..2026-04 1.0.6                |................===.|
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
  ? * com.telenav.cactus                   2022-06..2022-11 1.5.49               |...........=........|
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

## unclassified (344)

Multiple publishers with no natural-namespace owner present: a genuine collision the heuristic cannot settle.

- More than one publisher, and none is a credible owner: no natural-namespace owner is present and the earliest is not the closest groupId.
- Left unresolved - no owners.tsv is written - for a later hand decision.

_Showing the 200 most recently active of 344. For the full list, emit the SetOwners file: `-Djenesis.crawler.drift.emit=unclassified`._

```
org.newsclub.net.unix  [no clear owner; `com.kohlschutter.junixsocket` is earliest and most recent]
  ? * com.kohlschutter.junixsocket         2018-12..2026-08 2.11.1               |....================|
  ?   net.corda                            2025-09..2026-08 4.14.3               |.................===|
  ?   com.sbbsystems.flink                 2026-01..2026-08 3.5.0                |..................==|
  ?   net.blahajcloud                      2026-06..2026-06 1.0                  |...................=|
  ?   org.jam4s                            2025-10..2026-06 0.7.2-M1             |.................===|
  ?   io.nosqlbench                        2020-02..2020-03 3.12.47              |......=.............|
    + 1 more: io.engineblock
net.sf.jsqlparser  [no clear owner; `com.github.jsqlparser` is earliest and most recent]
  ? * com.github.jsqlparser                2024-03..2025-05 5.3                  |..............===...|
  ?   com.manticore-projects.jsqlformatter 2025-12..2026-08 5.3.302              |..................==|
  ?   se.alipsa                            2025-12..2025-12 1.2.0                |..................=.|
  ?   ai.starlake                          2024-09..2024-10 1.3.0                |...............=....|
org.apache.commons.codec  [no clear owner; `commons-codec` is earliest and most recent]
  ? * commons-codec                        2017-10..2026-07 1.22.1               |.===================|
  ?   io.gitlab.cupofcode                  2026-05..2026-08 1.4.7                |..................==|
  ?   software.amazon.awssdk               2024-07..2026-08 2.54.5               |...............=====|
  ?   com.mirakl                           2025-11..2026-08 10.17.0              |.................===|
  ?   cn.ctyun                             2025-10..2026-08 2.0.1                |.................===|
  ?   com.liquibase.ext.vaults             2026-06..2026-08 5.2.2                |...................=|
    + 77 more: com.alibaba.ververica, org.apache.tika, org.operaton.bpm.extension, com.ibm.cloud, com.republicate.modality, org.boostscale, org.apache.pinot, ai.platon.gora, com.suprsend, io.github.rsv-code, com.gitee.melin, org.apache.druid.extensions.contrib, (+65 more)
org.apache.commons.io  [no clear owner; `commons-io` is earliest and most recent]
  ? * commons-io                           2017-10..2026-04 2.22.0               |.==================.|
  ?   io.boxlang                           2024-09..2026-08 1.17.0               |...............=====|
  ?   io.github.dawnuu                     2026-08..2026-08 1.0.0                |...................=|
  ?   no.entur                             2024-03..2026-08 1.131.0              |..............======|
  ?   com.mifiel                           2026-08..2026-08 2.0.0                |...................=|
  ?   com.facebook.presto.hadoop           2025-09..2026-08 3.4.1-2              |.................===|
    + 96 more: io.github.liquid-java, com.networknt, com.datastax.oss, io.prophecy, org.apache.distributedlog, org.dominokit, org.apache.tika, org.sonarsource.flex, com.github.cafdataprocessing.workers.languagedetection, eu.rekawek.coffeegb, org.sonarsource.python, com.datarobot, (+84 more)
org.apache.commons.net  [no clear owner; `commons-net` is earliest and most recent]
  ? * commons-net                          2020-08..2026-03 3.13.0               |.......============.|
  ?   int.esa.ccsds.mo                     2025-05..2026-08 14.2                 |................====|
  ?   com.nordstrom.ui-tools               2024-08..2024-08 4.23.0               |...............=....|
  ?   io.kestra.plugin                     2024-02..2024-03 0.15.1               |..............=.....|
  ?   org.apache.pinot                     2024-03..2024-03 1.1.0                |..............=.....|
  ?   com.jkoolcloud.tnt4j.streams         2023-11..2023-11 2.0.0                |..............=.....|
org.dnsjava  [no clear owner; `dnsjava` is earliest and most recent]
  ? * dnsjava                              2019-05..2026-05 3.6.5                |.....==============.|
  ?   io.github.littleproxy                2024-09..2026-08 2.9.1                |...............=====|
  ?   io.simpleishard                      2026-07..2026-08 0.80.0               |...................=|
  ?   ai.askamerica                        2026-07..2026-08 0.80.0               |...................=|
  ?   org.apache.avro                      2025-09..2026-08 1.12.2               |.................===|
  ?   org.apache.paimon                    2026-04..2026-08 2.0.0                |..................==|
    + 19 more: io.jikkou, ai.platon.gora, org.apache.hbase, org.apache.beam, org.apache.phoenix, com.alibaba.polardbx, org.apache.pinot, de.m3y.hadoop.hdfs.hfsa, com.hazelcast.jet, com.helger.peppol.mcp, org.apache.atlas, com.clickzetta, (+7 more)
jpms_dss_asic_cades  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_common_converter  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_crl_parser_x509crl  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_pades  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_policy  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_tsl_validation  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_ws_certificate_validation_soap_client  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_ws_server_signing_common  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_ws_server_signing_soap  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_ws_signature_dto  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_ws_validation_rest  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_xml_common  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2023-11..2026-08 6.5                  |.............=======|
  ?   org.digidoc4j.dss                    2024-02..2025-11 6.2.d4j.1            |..............====..|
jpms_dss_alert  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2020-11..2025-11 6.2.d4j.1            |........==========..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_asic_xades  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_cades  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_jaxb_parsers  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_pades_openpdf  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_pdfa  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2023-02..2026-08 6.5                  |............========|
  ?   org.digidoc4j.dss                    2024-02..2025-11 6.2.d4j.1            |..............====..|
jpms_dss_specs_jws  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2021-10..2025-11 6.2.d4j.1            |.........=========..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_test  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_ws_certificate_validation_dto  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_ws_server_signing_rest_client  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_ws_server_signing_soap_client  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_ws_signature_soap_client  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_ws_timestamp_remote  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2020-11..2025-11 6.2.d4j.1            |........==========..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_ws_validation_dto  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
dss_pki_factory_jaxb  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2023-11..2026-08 6.5                  |.............=======|
  ?   org.digidoc4j.dss                    2024-02..2025-11 6.2.d4j.1            |..............====..|
jpms_dss_cookbook  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_crl_parser  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_diagnostic_data  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_evidence_record_common  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2023-11..2026-08 6.5                  |.............=======|
  ?   org.digidoc4j.dss                    2024-02..2025-11 6.2.d4j.1            |..............====..|
jpms_dss_jacoco_coverage  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2023-02..2026-08 6.5                  |............========|
  ?   org.digidoc4j.dss                    2024-02..2025-11 6.2.d4j.1            |..............====..|
jpms_dss_jaxb_common  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2022-05..2025-11 6.2.d4j.1            |..........========..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_simple_certificate_report  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_specs_asic_manifest  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2021-10..2025-11 6.2.d4j.1            |.........=========..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_specs_jades  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2021-10..2025-11 6.2.d4j.1            |.........=========..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_specs_xmldsig  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_specs_xmlers  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2023-11..2026-08 6.5                  |.............=======|
  ?   org.digidoc4j.dss                    2024-02..2025-11 6.2.d4j.1            |..............====..|
jpms_dss_spi  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_token  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_utils_google_guava  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_ws_certificate_validation_soap  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_ws_signature_rest_client  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_common_remote_dto  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_crl_parser_stream  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_evidence_record_asn1  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2024-07..2026-08 6.5                  |...............=====|
  ?   org.digidoc4j.dss                    2025-11..2025-11 6.2.d4j.1            |.................=..|
jpms_dss_evidence_record_xml  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2023-11..2026-08 6.5                  |.............=======|
  ?   org.digidoc4j.dss                    2024-02..2025-11 6.2.d4j.1            |..............====..|
jpms_dss_jades  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2021-10..2025-11 6.2.d4j.1            |.........=========..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_model  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_pki_factory  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2023-11..2026-08 6.5                  |.............=======|
  ?   org.digidoc4j.dss                    2024-02..2025-11 6.2.d4j.1            |..............====..|
jpms_dss_service  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_specs_trusted_list  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_specs_xades  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_utils_apache_commons  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_validation  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2024-07..2026-08 6.5                  |...............=====|
  ?   org.digidoc4j.dss                    2025-11..2025-11 6.2.d4j.1            |.................=..|
jpms_dss_ws_certificate_validation_common  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_ws_certificate_validation_rest  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_ws_certificate_validation_rest_client  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_ws_signature_rest  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_ws_signature_soap  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_ws_timestamp_dto  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2020-11..2025-11 6.2.d4j.1            |........==========..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_ws_timestamp_remote_rest_client  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2020-11..2025-11 6.2.d4j.1            |........==========..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_ws_timestamp_remote_soap  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2020-11..2025-11 6.2.d4j.1            |........==========..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_ws_timestamp_remote_soap_client  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2020-11..2025-11 6.2.d4j.1            |........==========..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_ws_validation_common  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_ws_validation_rest_client  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_ws_validation_soap  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_ws_validation_soap_client  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_xml  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2023-11..2026-08 6.5                  |.............=======|
  ?   org.digidoc4j.dss                    2024-02..2025-11 6.2.d4j.1            |..............====..|
jpms_dss_asic_common  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_detailed_report  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_document  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_enumerations  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_i18n  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2020-11..2025-11 6.2.d4j.1            |........==========..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_pades_pdfbox  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_simple_report  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_specs_saml_assertion  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2020-11..2025-11 6.2.d4j.1            |........==========..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_specs_validation_report  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_utils  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_ws_server_signing_dto  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_ws_server_signing_rest  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_ws_signature_remote  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_ws_timestamp_remote_rest  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2020-11..2025-11 6.2.d4j.1            |........==========..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
jpms_dss_xades  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-08 6.5                  |...........=========|
agents.features.tokenizer.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-06..2026-08 1.2.0                |.................===|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-kroog.3        |...................=|
agents.utils.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-08 1.2.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-kroog.3        |...................=|
prompt.executor.bedrock.client.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-07..2026-08 1.2.0                |.................===|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-kroog.3        |...................=|
a2a.transport.core.jsonrpc.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-10..2026-08 1.2.0-beta           |.................===|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-beta-kroog.3   |...................=|
prompt.executor.mistralai.client.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-10..2026-08 1.2.0-beta           |.................===|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-beta-kroog.3   |...................=|
agents.features.chat.memory.sql.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2026-03..2026-08 1.2.0                |..................==|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-kroog.3        |...................=|
agents.features.event.handler.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-08 1.2.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-kroog.3        |...................=|
agents.features.longterm.memory.aws.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2026-05..2026-08 1.2.0-beta           |..................==|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-beta-kroog.3   |...................=|
http.client.java.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2026-03..2026-08 1.2.0                |..................==|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-kroog.3        |...................=|
agents.mcp.metadata.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2026-03..2026-08 1.2.0                |..................==|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-kroog.3        |...................=|
agents.features.longterm.memory.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2026-03..2026-08 1.2.0-beta           |..................==|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-beta-kroog.3   |...................=|
prompt.executor.google.client.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-08 1.2.0-beta           |................====|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-beta-kroog.3   |...................=|
agents.features.trace.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-08 1.2.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-kroog.3        |...................=|
http.client.ktor.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-10..2026-08 1.2.0                |.................===|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-kroog.3        |...................=|
prompt.executor.llms.all.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-08 1.2.0-beta           |................====|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-beta-kroog.3   |...................=|
prompt.model.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-08 1.2.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-kroog.3        |...................=|
prompt.cache.redis.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-08 1.2.0-beta           |................====|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-beta-kroog.3   |...................=|
a2a.core.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-10..2026-08 1.2.0-beta           |.................===|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-beta-kroog.3   |...................=|
prompt.executor.openrouter.client.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-08 1.2.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-kroog.3        |...................=|
agents.mcp.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-08 1.2.0-beta           |................====|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-beta-kroog.3   |...................=|
agents.test.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-08 1.2.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-kroog.3        |...................=|
koog.bedrock.agentcore.runtime.jvm  [no clear owner; `com.kreoh.kroog` is earliest and most recent]
  ? * com.kreoh.kroog                      2026-08..2026-08 1.1.1-beta-kroog.3   |...................=|
  ?   ai.koog                              2026-08..2026-08 1.2.0-beta           |...................=|
a2a.server.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-10..2026-08 1.2.0-beta           |.................===|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-beta-kroog.3   |...................=|
prompt.llm.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-08 1.2.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-kroog.3        |...................=|
agents.features.a2a.server.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-10..2026-08 1.2.0-beta           |.................===|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-beta-kroog.3   |...................=|
agents.features.memory.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-08 1.2.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-kroog.3        |...................=|
prompt.executor.cached.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-08 1.2.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-kroog.3        |...................=|
rag.base.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-07..2026-08 1.2.0                |.................===|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-kroog.3        |...................=|
serialization.jackson.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2026-03..2026-08 1.2.0                |..................==|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-kroog.3        |...................=|
agents.features.acp.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-12..2026-08 1.2.0-beta           |..................==|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-beta-kroog.3   |...................=|
agents.features.chat.history.aws.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2026-04..2026-08 1.2.0-beta           |..................==|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-beta-kroog.3   |...................=|
agents.mcp.server.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-09..2026-08 1.2.0-beta           |.................===|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-beta-kroog.3   |...................=|
prompt.markdown.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-08 1.2.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-kroog.3        |...................=|
prompt.structure.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-08 1.2.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-kroog.3        |...................=|
agents.ext.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-08 1.2.0-beta           |................====|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-beta-kroog.3   |...................=|
utils.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-09..2026-08 1.2.0                |.................===|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-kroog.3        |...................=|
serialization.core.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2026-03..2026-08 1.2.0                |..................==|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-kroog.3        |...................=|
skills.jvm  [no clear owner; `com.kreoh.kroog` is earliest and most recent]
  ? * com.kreoh.kroog                      2026-08..2026-08 1.1.1-beta-kroog.3   |...................=|
  ?   ai.koog                              2026-08..2026-08 1.2.0-beta           |...................=|
embeddings.llm.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-08 1.2.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-kroog.3        |...................=|
http.client.spring.webclient.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2026-07..2026-08 1.2.0                |...................=|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-kroog.3        |...................=|
prompt.executor.openai.client.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-08 1.2.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-kroog.3        |...................=|
prompt.processor.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-12..2026-08 1.2.0                |..................==|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-kroog.3        |...................=|
rag.vector.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2026-04..2026-08 1.2.0-beta           |..................==|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-beta-kroog.3   |...................=|
a2a.client.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-10..2026-08 1.2.0-beta           |.................===|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-beta-kroog.3   |...................=|
agents.features.a2a.core.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-10..2026-08 1.2.0-beta           |.................===|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-beta-kroog.3   |...................=|
agents.planner.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-12..2026-08 1.2.0-beta           |..................==|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-beta-kroog.3   |...................=|
prompt.executor.clients.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-08 1.2.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-kroog.3        |...................=|
a2a.transport.client.jsonrpc.http.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-10..2026-08 1.2.0-beta           |.................===|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-beta-kroog.3   |...................=|
agents.features.snapshot.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-07..2026-08 1.2.0                |.................===|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-kroog.3        |...................=|
http.client.core.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-10..2026-08 1.2.0                |.................===|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-kroog.3        |...................=|
agents.core.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-08 1.2.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-kroog.3        |...................=|
agents.tools.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-08 1.2.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-kroog.3        |...................=|
prompt.cache.model.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-08 1.2.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-kroog.3        |...................=|
prompt.executor.dashscope.client.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-10..2026-08 1.2.0-beta           |.................===|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-beta-kroog.3   |...................=|
prompt.executor.ollama.client.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-08 1.2.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-kroog.3        |...................=|
prompt.cache.files.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-08 1.2.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-kroog.3        |...................=|
prompt.executor.deepseek.client.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-08..2026-08 1.2.0-beta           |.................===|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-beta-kroog.3   |...................=|
prompt.executor.litert.client.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2026-05..2026-08 1.2.0-beta           |..................==|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-beta-kroog.3   |...................=|
agents.features.chat.history.jdbc.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2026-03..2026-08 1.2.0                |..................==|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-kroog.3        |...................=|
http.client.okhttp.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2026-03..2026-08 1.2.0                |..................==|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-kroog.3        |...................=|
prompt.executor.anthropic.client.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-08 1.2.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-kroog.3        |...................=|
prompt.executor.model.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-08 1.2.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-kroog.3        |...................=|
prompt.executor.openai.client.base.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-09..2026-08 1.2.0                |.................===|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-kroog.3        |...................=|
prompt.tokenizer.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-06..2026-08 1.2.0                |.................===|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-kroog.3        |...................=|
prompt.xml.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-08 1.2.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-kroog.3        |...................=|
a2a.transport.server.jsonrpc.http.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-10..2026-08 1.2.0-beta           |.................===|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-beta-kroog.3   |...................=|
agents.features.opentelemetry.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-07..2026-08 1.2.0                |.................===|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-kroog.3        |...................=|
agents.features.sql.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-09..2026-08 1.2.0                |.................===|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-kroog.3        |...................=|
agents.cli.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2026-07..2026-08 1.2.0-beta           |...................=|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-beta-kroog.3   |...................=|
agents.features.a2a.client.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-10..2026-08 1.2.0-beta           |.................===|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-beta-kroog.3   |...................=|
agents.features.persistence.jdbc.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2026-03..2026-08 1.2.0                |..................==|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-kroog.3        |...................=|
embeddings.base.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-08 1.2.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-08 1.1.1-kroog.3        |...................=|
com.kingbase8.jdbc  [no clear owner; `org.jeecgframework` is earliest and most recent]
  ? * org.jeecgframework                   2024-06..2024-06 9.0.0                |...............=....|
  ?   cn.com.kingbase                      2025-04..2026-08 9.0.2                |................====|
  ?   io.github.iscasdmo                   2026-05..2026-05 8.6.0                |..................=.|
org.scala.lang.scala3.interfaces  [no clear owner; `org.scala-lang` is earliest and most recent]
  ? * org.scala-lang                       2021-06..2026-08 3.10.0-RC1           |.........===========|
  ?   dev.propensive                       2026-08..2026-08 3.10.0-dev-p15       |...................=|
org.scala.lang.scala3.library  [no clear owner; `org.scala-lang` is earliest and most recent]
  ? * org.scala-lang                       2021-06..2026-08 3.10.0-RC1           |.........===========|
  ?   dev.propensive                       2026-08..2026-08 3.10.0-dev-p15       |...................=|
org.scala.lang.scala3.presentation.compiler  [no clear owner; `org.scala-lang` is earliest and most recent]
  ? * org.scala-lang                       2023-07..2026-08 3.10.0-RC1           |.............=======|
  ?   dev.propensive                       2026-08..2026-08 3.10.0-dev-p15       |...................=|
com.ctc.wstx  [no clear owner; `com.fasterxml.woodstox` is earliest and most recent]
  ? * com.fasterxml.woodstox               2018-03..2026-08 7.2.2                |..==================|
  ?   com.twilio.sdk                       2026-08..2026-08 13.0.0               |...................=|
  ?   org.uma.jmetal                       2025-12..2026-07 7.5                  |..................==|
  ?   org.apache.bigtop.itest              2026-07..2026-07 3.6.0                |...................=|
  ?   org.bidib.jbidib                     2021-12..2026-05 2.0.44               |..........=========.|
  ?   gov.nih.ncats                        2022-01..2026-03 1.0.26               |..........=========.|
    + 19 more: org.hpccsystems, com.backpackcloud, com.liferay.portal, de.fraunhofer.iosb.ilt.FROST-Server, com.ibm.jsonata4java, se.signatureservice.support, com.liferay, net.pincette, org.opengis.cite, org.immregistries, com.testdroid, org.sonarsource.slang, (+7 more)
org.apache.commons.beanutils  [no clear owner; `com.guicedee.services` is earliest and most recent]
  ? * com.guicedee.services                2020-06..2022-02 1.2.2.1-jre17        |.......====.........|
  ?   org.wildfly                          2025-06..2026-08 41.0.1.Final         |.................===|
  ?   com.guicedee.modules.services        2026-04..2026-08 2.2.2                |..................==|
  ?   io.github.stoyank7                   2026-06..2026-06 1.0.2                |...................=|
  ?   org.jvnet.jaxb                       2025-09..2026-06 2.0.16               |.................===|
  ?   com.github.bld-commons               2026-01..2026-05 3.0.19               |..................=.|
    + 4 more: kg.apc, com.github.bordertech.wcomponents, commons-beanutils, org.onebusaway
bus.starter  [no clear owner; `org.miaixz` is earliest and most recent]
  ? * org.miaixz                           2025-05..2026-08 8.8.18               |................====|
  ?   io.github.rassafel                   2025-07..2025-07 0.0.1                |.................=..|
bus.spring  [no clear owner; `io.github.rassafel` is earliest and most recent]
  ? * io.github.rassafel                   2026-04..2026-04 0.0.4                |..................=.|
  ?   org.miaixz                           2026-08..2026-08 8.8.18               |...................=|
org.scala.lang.scala3.compiler  [no clear owner; `org.scala-lang` is earliest and most recent]
  ? * org.scala-lang                       2021-06..2026-06 3.3.8                |.........===========|
  ?   dev.propensive                       2026-08..2026-08 3.10.0-dev-p15       |...................=|
  ?   com.michaelpollmeier                 2022-10..2022-11 3.2.2-RC1-bin-20221101-d84007c-NIGHTLY+1-extensible-repl |...........=........|
org.scala.lang.scala3.sbt.bridge  [no clear owner; `org.scala-lang` is earliest and most recent]
  ? * org.scala-lang                       2021-06..2026-06 3.3.8                |.........===========|
  ?   dev.propensive                       2026-08..2026-08 3.10.0-dev-p15       |...................=|
org.scala.lang.scala3.staging  [no clear owner; `org.scala-lang` is earliest and most recent]
  ? * org.scala-lang                       2021-06..2026-06 3.3.8                |.........===========|
  ?   dev.propensive                       2026-08..2026-08 3.10.0-dev-p15       |...................=|
org.scala.lang.scala3.tasty.inspector  [no clear owner; `org.scala-lang` is earliest and most recent]
  ? * org.scala-lang                       2021-06..2026-06 3.3.8                |.........===========|
  ?   dev.propensive                       2026-08..2026-08 3.10.0-dev-p15       |...................=|
org.scala.lang.tasty.core  [no clear owner; `org.scala-lang` is earliest and most recent]
  ? * org.scala-lang                       2021-06..2026-06 3.3.8                |.........===========|
  ?   dev.propensive                       2026-08..2026-08 3.10.0-dev-p15       |...................=|
jssc  [no clear owner; `net.jockx` is earliest and most recent]
  ? * net.jockx                            2021-08..2021-08 2.9.3                |.........=..........|
  ?   io.github.java-native                2021-08..2026-08 2.10.4               |.........===========|
  ?   com.zsmartsystems.zigbee             2022-10..2024-12 1.4.16.1             |...........======...|
org.bukkit  [no clear owner; `com.uroria.curepur` is earliest and most recent]
  ? * com.uroria.curepur                   2024-07..2024-07 1.21-R0.1            |...............=....|
  ?   com.620cloud.server                  2026-06..2026-08 26.2.build.367-stable |...................=|
  ?   io.github.xucy10                     2026-08..2026-08 26.2-R0.1            |...................=|
  ?   com.mineplex.studio.server           2024-10..2026-04 26.1.2-357           |...............====.|
  ?   com.uroria.latest                    2024-07..2024-07 1.21-R0.1-2d776710d6 |...............=....|
  ?   com.uroria                           2024-07..2024-07 1.21-R0.1            |...............=....|
org.apache.commons.logging  [no clear owner; `org.slf4j` is earliest and most recent]
  ? * org.slf4j                            2017-04..2026-05 2.0.18               |===================.|
  ?   org.open-metadata                    2025-11..2026-08 2.0.0                |.................===|
  ?   com.helger.kaltblut                  2026-08..2026-08 0.9.5                |...................=|
  ?   org.beangle.sas                      2024-11..2026-08 0.13.11              |...............=====|
  ?   io.github.peterdowdy                 2026-07..2026-07 0.0.0-main-26f8f63   |...................=|
  ?   org.apache.tika                      2022-09..2026-07 3.3.2                |...........=========|
    + 36 more: net.ontopia, org.apache.orc, org.motorbrot, org.nuiton, de.redsix, org.lucee, io.github.jinahya, org.operaton.bpm.extension, commons-logging, org.jboss.pnc.build-agent, com.facebook.presto.hive, com.nordstrom.ui-tools, (+24 more)
com.oracle.truffle.tools.profiler  [no clear owner; `org.graalvm.tools` is earliest and most recent]
  ? * org.graalvm.tools                    2018-10..2026-08 25.3.4.1             |...=================|
  ?   com.orientechnologies                2025-12..2026-07 3.2.55               |..................==|
com.oracle.truffle.regex  [no clear owner; `org.graalvm.regex` is earliest and most recent]
  ? * org.graalvm.regex                    2018-10..2026-08 25.3.4.1             |...=================|
  ?   org.noear                            2024-09..2025-07 1.9.6                |...............===..|
  ?   com.syncloop.middleware              2025-01..2025-01 1.7.1                |................=...|
com.jcraft.jsch  [no clear owner; `com.github.mwiede` is earliest and most recent]
  ? * com.github.mwiede                    2021-08..2026-08 2.28.7               |.........===========|
  ?   com.pesitwizard.connector            2026-02..2026-02 1.2.1                |..................=.|
  ?   io.github.luigidemasi                2026-01..2026-01 2.27.7               |..................=.|
  ?   com.opendatadsl                      2025-08..2026-01 1.1.29               |.................==.|
  ?   com.testingbot                       2025-08..2025-08 4.3                  |.................=..|
  ?   io.kestra.plugin                     2024-04..2025-03 0.20.1               |..............===...|
    + 1 more: com.jcabi
jdk.internal.vm.compiler  [no clear owner; `org.graalvm.compiler` is earliest and most recent]
  ? * org.graalvm.compiler                 2018-10..2026-08 23.0.13.1            |...=================|
  ?   io.vertx                             2022-11..2026-08 4.5.32               |...........=========|
  ?   org.linuxforhealth.fhir              2022-08..2022-12 5.1.1                |...........==.......|
vault.java.driver  [no clear owner; `com.bettercloud` is earliest and most recent]
  ? * com.bettercloud                      2019-06..2019-12 5.1.0                |.....==.............|
  ?   io.github.jopenlibs                  2022-10..2026-08 6.2.3                |...........=========|
  ?   io.axual.utilities.config.providers  2020-06..2024-11 1.2.0                |.......=========....|
  ?   edu.utexas.tacc.tapis                2021-10..2021-10 5.1.2                |.........=..........|
cache.annotations.ri.common  [no clear owner; `com.jwebmp.thirdparty.jcache` is earliest and most recent]
  ? * com.jwebmp.thirdparty.jcache         2019-05..2019-08 0.68.0.1             |.....=..............|
  ?   com.guicedee.modules.services        2026-04..2026-08 2.2.2                |..................==|
  ?   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17        |.....======.........|
cache.annotations.ri.guice  [no clear owner; `com.jwebmp.thirdparty.jcache` is earliest and most recent]
  ? * com.jwebmp.thirdparty.jcache         2019-05..2019-08 0.68.0.1             |.....=..............|
  ?   com.guicedee.modules.services        2026-04..2026-08 2.2.2                |..................==|
  ?   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17        |.....======.........|
org.apache.commons.fileupload  [no clear owner; `com.jwebmp.jre11` is earliest and most recent]
  ? * com.jwebmp.jre11                     2018-12..2018-12 0.63.0.19            |....=...............|
  ?   com.guicedee.modules.services        2026-04..2026-08 2.2.2                |..................==|
  ?   org.wiremock                         2025-06..2026-04 4.0.0-beta.32        |.................==.|
  ?   org.openidentityplatform.openam.agents 2025-11..2026-03 5.0.3                |.................==.|
  ?   commons-fileupload                   2025-06..2025-06 1.6.0                |.................=..|
  ?   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17        |.....======.........|
    + 2 more: com.jwebmp.jpms.commons, com.jwebmp
aopalliance  [no clear owner; `com.jwebmp.jre11` is earliest and most recent]
  ? * com.jwebmp.jre11                     2018-12..2018-12 0.63.0.19            |....=...............|
  ?   com.guicedee.modules.services        2026-04..2026-08 2.2.2                |..................==|
  ?   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17        |.....======.........|
  ?   com.jwebmp.thirdparty                2019-04..2019-08 0.68.0.1             |....==..............|
  ?   com.jwebmp                           2019-01..2019-04 0.66.0.1             |....=...............|
cache.api  [no clear owner; `com.jwebmp.thirdparty.jcache` is earliest and most recent]
  ? * com.jwebmp.thirdparty.jcache         2019-05..2019-08 0.68.0.1             |.....=..............|
  ?   com.guicedee.modules.services        2026-04..2026-08 2.2.2                |..................==|
  ?   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17        |.....======.........|
net.sf.uadetector.core  [no clear owner; `com.jwebmp.jre11` is earliest and most recent]
  ? * com.jwebmp.jre11                     2018-11..2018-12 0.63.0.19            |....=...............|
  ?   com.guicedee.modules.services        2026-04..2026-08 2.2.2                |..................==|
  ?   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17        |.....======.........|
  ?   com.jwebmp.thirdparty                2019-04..2019-08 0.68.0.1             |....==..............|
  ?   com.jwebmp                           2019-01..2019-04 0.66.0.1             |....=...............|
net.sf.uadetector.resources  [no clear owner; `com.jwebmp.jre11` is earliest and most recent]
  ? * com.jwebmp.jre11                     2018-11..2018-12 0.63.0.19            |....=...............|
  ?   com.guicedee.modules.services        2026-04..2026-08 2.2.2                |..................==|
  ?   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17        |.....======.........|
  ?   com.jwebmp.thirdparty                2019-04..2019-08 0.68.0.1             |....==..............|
  ?   com.jwebmp                           2019-01..2019-04 0.66.0.1             |....=...............|
flying.saucer.pdf  [no clear owner; `org.xhtmlrenderer` is earliest and most recent]
  ? * org.xhtmlrenderer                    2024-09..2026-08 10.5.0               |...............=====|
  ?   io.github.openpdfsaucer              2025-03..2025-05 2.0.9                |................=...|
jetty.servlet.api  [no clear owner; `org.eclipse.jetty.toolchain` is earliest and most recent]
  ? * org.eclipse.jetty.toolchain          2019-02..2026-01 4.0.9                |....===============.|
  ?   io.joynr.java.core                   2026-08..2026-08 1.24.0-ws2           |...................=|
  ?   ch.reportingsoft.birt                2025-04..2025-04 4.0.6                |................=...|
  ?   io.prometheus.cloudwatch             2024-08..2024-08 0.16.0               |...............=....|
  ?   org.cip4.tools.jdfutility            2022-01..2022-01 1.7.1                |..........=.........|
VirtualizedFX  [no clear owner; `io.github.palexdev` is earliest and most recent]
  ? * io.github.palexdev                   2022-03..2026-08 25.3.6               |..........==========|
  ?   org.glavo.materialfx                 2022-04..2022-04 11.2.6               |..........=.........|
net.jbock.compiler  [no clear owner; `com.github.h908714124` is earliest and most recent]
  ? * com.github.h908714124                2018-11..2021-05 4.4.000              |....======..........|
  ?   io.github.jbock-java                 2021-06..2026-08 5.23                 |.........===========|
net.jbock  [no clear owner; `com.github.h908714124` is earliest and most recent]
  ? * com.github.h908714124                2017-10..2021-05 4.4                  |.=========..........|
  ?   io.github.jbock-java                 2021-06..2026-08 5.23                 |.........===========|
java.xml.bind  [no clear owner; `javax.xml.bind` is earliest and most recent]
  ? * javax.xml.bind                       2017-07..2018-09 2.3.1                |.===................|
  ?   org.metricshub                       2025-05..2026-08 3.9.06               |................====|
  ?   com.yahoo.vespa                      2020-05..2026-08 8.738.17             |.......=============|
  ?   io.github.cobble-project             2026-07..2026-08 0.4.0-1-flink-1.17   |...................=|
  ?   org.apache.paimon                    2023-05..2026-08 2.0.0                |.............=======|
  ?   com.aliyun.openservices.aiservice    2024-05..2026-07 1.3.0                |..............======|
    + 185 more: io.inji.certify, de.fraunhofer.iosb.ilt.FROST-Server, com.alibaba.dts.client, org.apache.tika, org.kendar.protocol, org.verapdf.apps, org.apache.flink, org.apache.pinot, io.mosip.mock.sdk, de.fraunhofer.iosb.ilt, io.mosip.esignet, org.wso2.msf4j.perftest.echo, (+173 more)
com.jn.langx.java8  [no clear owner; `io.github.bes2008.solution.langx` is earliest and most recent]
  ? * io.github.bes2008.solution.langx     2024-01..2026-08 5.4.6.4              |..............======|
  ?   io.github.qhsword.langx              2025-11..2025-11 5.5.10               |.................==.|
com.jn.langx.security.gm.jca.bouncycastle  [no clear owner; `io.github.bes2008.solution.langx.security` is earliest and most recent]
  ? * io.github.bes2008.solution.langx.security 2024-01..2026-08 5.4.6.4              |..............======|
  ?   io.github.qhsword.langx.security     2025-11..2025-12 5.8.0                |.................==.|
jakarta.security.auth.message  [no clear owner; `jakarta.authentication` is earliest and most recent]
  ? * jakarta.authentication               2020-11..2024-05 3.1.0                |.......========.....|
  ?   org.apache.tomcat                    2020-11..2026-08 10.1.59              |........============|
org.apache.commons.cli  [no clear owner; `commons-cli` is earliest and most recent]
  ? * commons-cli                          2023-10..2025-11 1.11.0               |.............=====..|
  ?   org.apache.meecrowave                2025-10..2026-08 2.1.1                |.................===|
  ?   org.imixs.bpmn                       2025-05..2026-08 1.3.0                |................====|
  ?   org.apktool                          2023-12..2026-07 3.0.3                |..............======|
  ?   org.teavm                            2024-04..2026-05 0.14.1               |..............=====.|
  ?   io.github.vdaburon                   2024-01..2026-05 5.1                  |..............=====.|
    + 25 more: com.nanolaba, com.ericsson.bss.cassandra.ecaudit, io.github.gvergine, com.amazonaws, io.github.706412584, com.legsem.legstar, dev.walgo, org.apache.phoenix.thirdparty, org.apache.james, net.thisptr, us.poliscore, com.github.oboehm, (+13 more)
```

## Reassigned and widened ownership

Modules whose resolved owner differs from the implicit first-publisher owner once `owners.tsv` is applied. 🔀 reassigned (238): the first publisher was replaced by a different owner. ➕ widened (193): extra legal owners were allowed alongside the first publisher (e.g. a groupId migration or a co-maintained project). Modules where `owners.tsv` only confirms the first publisher are not listed. Submodules that share the same transition are collapsed into a single `prefix.*` row; the count in braces after the name is how many modules that row covers. The Rejected owner(s) column names the publishers excluded for the name (empty for a pure widening).

| Module | Implicit owner | Owner(s) | Rejected owner(s) |
|---|---|---|---|
| `com.google.auto.service` 🔀 | `com.github.sidneibjunior` | `com.google.auto.service` | `com.github.sidneibjunior, dev.ikm.jpms` |
| `com.install4j.runtime` ➕ | `com.iamsoft` | `com.iamsoft, com.install4j` | (none) |
| `dawdler.*` (68) ➕ | `io.github.dawdler-series` | `club.dawdler, io.github.dawdler-series` | (none) |
| `io.avaje.junit` ➕ | `org.avaje` | `io.avaje, org.avaje` | (none) |
| `io.ebean.*` (3) ➕ | `org.avaje` | `io.ebean, org.avaje` | (none) |
| `io.grpc` 🔀 | `io.helidon.grpc` | `io.grpc` | `io.helidon.grpc, com.clickhouse, io.github.sunny-chung` |
| `io.whitfin.siphash` 🔀 | `com.io7m.repackage.io.whitfin` | `io.whitfin` | `com.io7m.repackage.io.whitfin` |
| `jakarta.cdi.*` (3) 🔀 | `jakarta.enterprise` | `jakarta.cdi` | `jakarta.enterprise, com.abavilla` |
| `jakarta.concurrency` 🔀 | `jakarta.enterprise.concurrent` | `jakarta.concurrency` | `jakarta.enterprise.concurrent` |
| `jakarta.ejb` 🔀 | `com.guicedee.services` | `jakarta.ejb` | `com.guicedee.services, com.manorrock.flounder` |
| `jakarta.faces` ➕ | `com.guicedee.services` | `com.guicedee.services, jakarta.faces` | (none) |
| `jakarta.servlet.jsp` 🔀 | `org.apache.tomcat` | `jakarta.servlet.jsp` | `org.apache.tomcat, com.guicedee.services, com.heroku` |
| `kora.*` (80) ➕ | `ru.tinkoff.kora` | `io.koraframework, ru.tinkoff.kora` | (none) |
| `kora.*` (8) ➕ | `ru.tinkoff.kora.experimental` | `io.koraframework.experimental, ru.tinkoff.kora.experimental` | (none) |
| `net.evonit.thumbnailator2` 🔀 | `io.github.evonit` | `net.evonit` | `io.github.evonit` |
| `okhttp3.*` (8) 🔀 | `com.github.ljun20160606` | `com.squareup.okhttp3` | `com.github.ljun20160606, com.datadoghq.okhttp3, com.huanli233.okhttp3-compat, com.ibm.cloud, +5 more` |
| `org.apache.xmlbeans` 🔀 | `com.guicedee.services` | `org.apache.xmlbeans` | `com.guicedee.services, com.github.rahulsom, com.sonsure, io.github.cdnk, +1 more` |
| `org.commonmark.*` (7) ➕ | `com.atlassian.commonmark` | `com.atlassian.commonmark, org.commonmark` | `org.aya-prover` |
| `org.commonmark.ext.*` (2) 🔀 | `com.atlassian.commonmark` | `org.commonmark` | `com.atlassian.commonmark, com.adobe.aem, io.github.dfengwei, org.aya-prover, +1 more` |
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

