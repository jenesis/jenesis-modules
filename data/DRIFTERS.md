# Module ownership drifters

Generated 2026-11-19. A module *drifts* when more than one groupId publishes the name and its `owners.tsv` does not yet name every publisher (no `owners.tsv`, or one that leaves some publishing groupId neither `allowed` nor `rejected`). Resolving a drift means deciding each groupId via `SetOwners` (which writes `allowed`/`rejected`); a fully-named module drops off this list.

| Category | Unresolved | Resolved via owners.tsv |
|---|---:|---:|
| explicit-rules | 84 | 647 |
| republisher | 7 | 12 |
| migration | 62 | 757 |
| fork | 287 | 263 |
| shaded | 144 | 1216 |
| tld-dropped | 17 | 69 |
| two-segments | 68 | 0 |
| unclassified | 353 | 7 |
| **total** | **1022** | **2971** |

The table covers all **3993** multi-owner modules (of **42105** modules scanned).

Timeline axis spans 2017-01 .. 2026-11 (today). Per group: decision `A`=allowed `R`=rejected `?`=undecided, `*`=current owner, then the publication range, latest version, and a `=` activity bar across the axis.

## explicit-rules (84)

Hand-curated overrides: a module matching an explicit rule is assigned to a fixed owner groupId regardless of the heuristic.

- The module name equals, or falls under, a hand-curated prefix in the explicit-owner map.
- Allow every publisher whose groupId falls under the mapped owner prefix; reject all other publishers.

| count | current owner | new owner(s) |
|---:|---|---|
| 3 | `org.jetbrains.kotlinx` | `org.jetbrains.kotlinx, org.jetbrains.intellij.deps.kotlinx` |
| 3 | `org.jetbrains.kotlinx` | `org.jetbrains.kotlinx, org.jetbrains.kotlin` |
| 1 | `com.github.ljun20160606` | `com.squareup.okhttp3` |
| 1 | `org.jetbrains.kotlinx` | `org.jetbrains.kotlinx, org.jetbrains.dokka, org.jetbrains.intellij.deps.kotlinx, org.jetbrains.kotlin` |
| 1 | `org.jetbrains.kotlinx` | `org.jetbrains.kotlinx, org.jetbrains.kotlinx.dataframe` |
| 1 | `org.springframework.security.experimental` | `org.springframework.security.experimental, org.springframework.security` |
| 1 | `ru.tinkoff.kora` | `ru.tinkoff.kora, io.koraframework` |
| 1 | `ru.tinkoff.kora.experimental` | `ru.tinkoff.kora.experimental, io.koraframework` |

```
com.google.gson  [explicit rule: owned by `com.google.code.gson`; 549 other group(s) rejected]
  A * com.google.code.gson                 2019-10..2026-10 2.15.0               |.....===============|
  R   com.aliyun                           2021-09..2026-11 1.10.0               |.........===========|
  ?   com.conaxgames                       2026-06..2026-11 2.0.8                |..................==|
  R   com.exaroton                         2021-06..2026-11 2.6.0                |........============|
  R   dev.uebelacker.babeli                2026-05..2026-11 1.1.0                |..................==|
  R   io.quarkus                           2024-07..2026-11 4.0.0.CR1            |..............======|
    + 544 more: org.sonarsource.dotnet, org.devlive.metaforge, nl.trusttech, org.sonarsource.sonarlint.ls, org.operaton.bpm, io.github.iamsanjaymalakar, com.linkedin.iceberg, org.sonarsource.sonarlint.core, org.apache.shenyu, com.afipsdk, com.newrelic, dev.frontseat.maven.extensions, (+532 more)
kotlin.stdlib.jdk8  [explicit rule: owned by `org.jetbrains.kotlin`; 246 other group(s) rejected]
  A * org.jetbrains.kotlin                 2019-01..2026-11 2.5.0-RC             |....================|
  R   cn.ctyun                             2025-10..2026-11 1.0.3                |.................===|
  R   com.google.genai                     2026-04..2026-11 2.4.0                |..................==|
  R   ai.realitydefender                   2025-06..2026-11 0.1.10               |................====|
  R   com.aliyun                           2026-01..2026-11 3.14.2               |.................===|
  ?   eu.rekawek.coffeegb                  2026-07..2026-11 2.1.8                |..................==|
    + 241 more: com.newrelic.agent.android, com.aliyun.odps, com.squareup.okhttp3, com.airbnb.viaduct, com.cyberark, io.github.graphdsl, org.apache.seatunnel, io.github.infocusmodereal, org.apache.dolphinscheduler, io.github.prakhar998, net.corda, org.apache.inlong, (+229 more)
jul.to.slf4j  [explicit rule: owned by `org.slf4j`; 18 other group(s) rejected]
  A * org.slf4j                            2019-02..2026-09 2.0.20               |....================|
  ?   com.zonzonzon                        2026-09..2026-11 1.1.9                |...................=|
  R   net.finmath                          2025-11..2026-08 3.0.0                |.................==.|
  ?   io.github.zhouzhoucoder              2026-07..2026-07 6.0                  |..................=.|
  ?   xyz.hldev.libra-common               2026-06..2026-06 1.0.1                |..................=.|
  R   de.codecentric                       2024-01..2026-02 3.3.0                |..............====..|
    + 13 more: io.github.davincilll, io.github.daone-dadp, io.kestra.plugin, io.github.tky0065, com.itxk.maven, io.github.srilathakarri, de.fraunhofer.iosb.ilt.faaast.registry, org.easypeelsecurity, io.github.tracedin, com.github.kaklakariada, com.tencent.cloud, org.conductoross, (+1 more)
kotlinx.serialization.core  [explicit rule: owned by `org.jetbrains`; 29 other group(s) rejected]
  A * org.jetbrains.kotlinx                2021-09..2026-11 1.13.0               |.........===========|
  R   love.forte.plugin.suspend-transform  2025-04..2026-11 2.5.0-RC-0.15.0      |................====|
  ?   org.jetbrains.kotlin                 2026-10..2026-11 2.5.0-RC             |...................=|
  ?   io.github.kscripting.amiops          2026-10..2026-11 7.5.0                |...................=|
  ?   lol.simeon                           2026-06..2026-10 1.4.0                |..................==|
  R   dev.robocode.tankroyale              2026-01..2026-10 1.4.0                |.................===|
    + 25 more: io.github.oewntk, dev.sebastiano.spectre, io.github.wangbax, com.squareup.wire, org.ldemetrios, io.github.lumamontes, dev.zacsweers.metro, io.typst, io.availe, dev.oglass, io.github.lexa-diky, com.toasttab.expediter, (+13 more)
kotlin.stdlib  [explicit rule: owned by `org.jetbrains.kotlin`; 239 other group(s) rejected]
  A * org.jetbrains.kotlin                 2019-01..2026-11 2.5.0-RC             |....================|
  ?   dev.otherlode                        2026-11..2026-11 0.1.0                |...................=|
  ?   zip.trev.trevrpc                     2026-08..2026-11 1.9.0                |...................=|
  R   dev.bmcreations                      2026-04..2026-11 0.2.0                |..................==|
  ?   io.github.ghosthack                  2026-10..2026-11 0.1.1                |...................=|
  R   com.tidbcloud                        2026-05..2026-11 0.4.10               |..................==|
    + 234 more: org.jetbrains, com.nhncloud.android, de.darkatra.injector, se.liu.research.hefquin, com.airbnb.viaduct, ca.acendas, com.bynder, gg.ginco.hytest, com.google.cloud.pubsub.flink, io.github.fjossinet.rnartist, com.hiyowa, dev.robocode.tankroyale, (+222 more)
kotlinx.serialization.json  [explicit rule: owned by `org.jetbrains`; 37 other group(s) rejected]
  A * org.jetbrains.kotlinx                2021-09..2026-11 1.13.0               |.........===========|
  R   com.google.devtools.ksp              2024-07..2026-11 2.3.14               |..............======|
  R   top.e404.statimg                     2026-05..2026-11 6.0.2                |..................==|
  ?   dev.sebastiano.spectre               2026-09..2026-09 0.6.0                |...................=|
  R   ch.ubique.uniffi                     2025-07..2026-07 1.0.16               |................===.|
  R   com.emergetools                      2026-05..2026-05 4.4.1                |..................=.|
    + 33 more: com.github.skydoves, io.github.ruxbit.ksp, dev.mtctx.library, io.github.adokky, co.touchlab.skie, com.hadisatrio.libs.android, io.portone, com.pinterest.ktlint, community.flock.wirespec.plugin.cli, io.johnsonlee.kx, io.johnsonlee.exec, io.specmatic, (+21 more)
org.objectweb.asm.tree  [explicit rule: owned by `org.ow2.asm`; 22 other group(s) rejected]
  A * org.ow2.asm                          2017-07..2026-10 9.11.1               |.===================|
  R   com.scylladb                         2025-12..2026-11 2.1.1                |.................===|
  ?   dev.protsenko.jpamutator             2026-10..2026-10 0.1.0                |...................=|
  R   org.jetbrains.compose.hot-reload     2025-10..2026-10 1.3.0-rc01           |.................===|
  ?   io.github.jarrunner.teavm            2026-10..2026-10 0.16.0-jr.1          |...................=|
  R   org.teavm                            2023-03..2026-10 0.16.0               |............========|
    + 17 more: io.github.lnyo-cly, io.joynr.tools.generator, org.opennms.newts, ch.exense.step, io.killedkenny.crossfuzz, com.lihaoyi, io.joern, io.github.llmagentbuilder, com.jordansamhi, com.liferay, com.uber.nullaway, com.houxinlin, (+5 more)
org.objectweb.asm  [explicit rule: owned by `org.ow2.asm`; 170 other group(s) rejected]
  A * org.ow2.asm                          2017-07..2026-10 9.11.1               |.===================|
  R   org.virtuslab.scala-cli              2023-05..2026-11 1.19.0               |............========|
  ?   com.arinits.nullguard                2026-11..2026-11 2.0.1                |...................=|
  R   com.github.jnr                       2019-10..2026-11 0.39.9               |.....===============|
  R   org.fitnesse                         2023-10..2026-11 20261101             |.............=======|
  ?   io.spicelabs                         2026-06..2026-10 0.20.2               |..................==|
    + 165 more: se.swedenconnect.keycloak, gg.ginco.hytest, com.ibm.ta.sdk, io.github.jarrunner.teavm, org.teavm, be.ugent.idlab.knows, org.virtuslab, org.tiatesting, com.datadoghq, edu.berkeley.cs.jqf, org.apache.iotdb, org.glassfish.main.extras, (+153 more)
kotlinx.coroutines.core  [explicit rule: owned by `org.jetbrains`; 24 other group(s) rejected]
  A * org.jetbrains.kotlinx                2023-03..2026-05 1.11.0               |............=======.|
  ?   org.scip-code                        2026-11..2026-11 0.14.2               |...................=|
  ?   org.jetbrains.kotlin                 2026-10..2026-11 2.5.0-RC             |...................=|
  A   org.jetbrains.dokka                  2024-10..2026-11 2.3.0                |...............=====|
  R   com.airbnb.viaduct                   2026-05..2026-10 3.0.0                |..................==|
  ?   io.github.eilinwis                   2026-10..2026-10 1.0.0                |...................=|
    + 22 more: ca.acendas, love.forte.plugin.suspend-transform, org.jetbrains.intellij.deps.kotlinx, com.eygraber, org.openprojectx.java.dns, com.krillforge, org.openprojectx.hadoop.win, io.github.danbeldev, io.johnsonlee.kx, io.github.zimoyin, io.johnsonlee.exec, io.github.saumya-bhatt, (+10 more)
org.objectweb.asm.commons  [explicit rule: owned by `org.ow2.asm`; 43 other group(s) rejected]
  A * org.ow2.asm                          2017-07..2026-10 9.11.1               |.===================|
  ?   io.micronaut.scala                   2026-11..2026-11 1.0.0-M1             |...................=|
  R   io.debezium                          2026-02..2026-11 3.6.4.Final          |..................==|
  R   org.copper-engine                    2025-11..2026-11 8.1.1                |.................===|
  ?   org.gephi                            2026-06..2026-11 0.11.4               |..................==|
  R   com.apollographql.apollo             2024-07..2026-10 4.4.4                |..............======|
    + 38 more: io.github.vesselloader, io.github.jarrunner.teavm, org.teavm, com.appdynamics, com.lealceldeiro, io.joern, cn.iservicego, org.tango-controls, com.apollographql.apollo3, de.firemage.autograder, com.yugabyte, com.newrelic.agent.android, (+26 more)
com.google.common.util.concurrent.internal  [explicit rule: owned by `com.google.guava`; 16 other group(s) rejected]
  A * com.google.guava                     2023-10..2025-03 1.0.3                |.............====...|
  ?   io.openliberty.tools                 2026-10..2026-11 2.4.4                |...................=|
  ?   org.sonarsource.python               2026-08..2026-11 5.33.0.48743         |...................=|
  ?   com.spiraldb                         2026-11..2026-11 1.13.0-spiral.1      |...................=|
  ?   io.github.mahmoudimus                2026-11..2026-11 0.8.1                |...................=|
  ?   org.apache.shenyu                    2026-11..2026-11 2.7.2                |...................=|
    + 11 more: com.instaclustr, org.apache.pinot, com.google.errorprone, io.github.seogineer, org.hyperledger.fabric, com.datastrato, com.google.turbine, org.opendaylight.aaa, org.apache.iceberg, org.opennms.alec.wrap, de.bsommerfeld.pathetic
okhttp3  [explicit rule: owned by `com.squareup.okhttp3`; 11 other group(s) rejected]
  R * com.github.ljun20160606              2018-02..2018-02 3.10.0               |..=.................|
  ?   ai.suvir                             2026-11..2026-11 1.0.3                |...................=|
  ?   com.arcadedb                         2026-09..2026-11 26.11.1              |...................=|
  A   com.squareup.okhttp3                 2018-02..2026-10 4.12.1               |..==================|
  R   org.eclipse.csi                      2026-03..2026-10 0.8.4                |..................==|
  ?   com.huaweicloud                      2026-10..2026-10 3.26.9               |...................=|
    + 6 more: com.squareup.wire, org.testcontainers, io.github.mashanshui, com.huanli233.okhttp3-compat, io.github.sunny-chung, com.datadoghq.okhttp3
javafx.graphics  [explicit rule: owned by `org.openjfx`; 7 other group(s) rejected]
  A * org.openjfx                          2018-07..2026-10 27.0.1               |...=================|
  ?   org.jrebirth.af                      2026-11..2026-11 12.0.0               |...................=|
  R   org.glavo.hmcl.openjfx               2022-08..2026-08 25.0.4-linux-riscv64 |...........=========|
  R   io.github.makbn                      2025-10..2025-10 2.0.0                |.................=..|
  R   de.wenzlaff.twbibel                  2022-05..2024-12 0.1.1                |..........======....|
  R   de.pirckheimer-gymnasium             2024-08..2024-08 3.1.0                |...............=....|
    + 2 more: org.jfxcore, com.robotaccomplice
kotlinx.serialization.protobuf  [explicit rule: owned by `org.jetbrains`; 1 other group(s) rejected]
  A * org.jetbrains.kotlinx                2021-09..2026-11 1.13.0               |.........===========|
  ?   org.jetbrains.kotlin                 2026-01..2026-11 2.5.0-RC             |.................===|
  R   org.danbrough.kotlinx                2022-09..2023-03 1.5.0                |...........==.......|
kotlinx.serialization.cbor  [explicit rule: owned by `org.jetbrains`; 3 other group(s) rejected]
  A * org.jetbrains.kotlinx                2021-09..2026-11 1.13.0               |.........===========|
  ?   dev.sebastiano.spectre               2026-09..2026-11 0.9.0                |...................=|
  R   org.danbrough.kotlinx                2022-09..2023-03 1.5.0                |...........==.......|
  R   io.github.pluginloader               2022-01..2022-01 1.0.0                |..........=.........|
com.google.common  [explicit rule: owned by `com.google.guava`; 80 other group(s) rejected]
  A * com.google.guava                     2017-07..2026-11 33.8.0-android       |.===================|
  R   net.sourceforge.plantuml             2025-06..2026-11 1.2026.10            |................====|
  R   org.talend.sdk.component.sample.feature 2025-08..2026-11 1.2610.1             |.................===|
  ?   org.finos.legend.pure                2026-09..2026-11 5.112.1              |...................=|
  ?   io.github.yuku123                    2026-10..2026-11 1.3.3                |...................=|
  R   io.digiexpress                       2025-10..2026-11 5.3.22               |.................===|
    + 75 more: eu.rssw.sonar.openedge, io.github.bdezonia, com.google.googlejavaformat, org.apache.spark, io.github.mahmoudimus, org.conductoross, org.apache.jackrabbit, org.apache.calcite.avatica, dev.walgo, guru.mocker.composition, org.opendaylight.aaa, org.sonarsource.python, (+63 more)
kotlin.reflect  [explicit rule: owned by `org.jetbrains.kotlin`; 68 other group(s) rejected]
  A * org.jetbrains.kotlin                 2019-01..2026-11 2.5.0-RC             |....================|
  R   org.apache.pinot                     2025-09..2026-10 1.6.0                |.................===|
  ?   io.microservices.tools.canvas        2026-10..2026-10 0.15.0.RELEASE       |...................=|
  ?   com.utopia-rise                      2026-08..2026-10 1.0.0-rc3            |..................==|
  ?   org.octopusden.octopus.infrastructure 2026-07..2026-07 3.0.8                |..................=.|
  ?   io.github.barqdb.kotlin              2026-07..2026-07 4.1.0                |..................=.|
    + 63 more: io.github.rodrigotimoteo, com.airbnb.viaduct, io.github.abdullahkhan118, io.github.tobi-laa, io.github.kshulzh.kefir, io.github.xilinjia.krdb, io.github.snow1026, com.browserstack, com.simprints.realm.kotlin, org.pkl-lang, com.statsig, com.infomaniak.realm.kotlin, (+51 more)
kotlin.stdlib.jdk7  [explicit rule: owned by `org.jetbrains.kotlin`; 23 other group(s) rejected]
  A * org.jetbrains.kotlin                 2019-01..2026-11 2.5.0-RC             |....================|
  ?   org.octopusden.octopus.jira          2026-06..2026-10 2.1.3                |..................==|
  ?   me.bechberger                        2026-06..2026-10 0.0.10               |..................==|
  R   com.github.shynixn.mccoroutine       2024-08..2026-09 2.23.0               |...............=====|
  R   io.pyroscope                         2025-04..2026-09 2.9.2                |................====|
  ?   net.maizegenetics                    2026-08..2026-08 5.2.98               |..................=.|
    + 18 more: io.github.zawarka03, com.kroegerama.openapi-kgen, io.github.team-sneakymouse, com.seanshubin.code.structure, me.xcue, org.partiql, io.github.wadoon.key, org.btmonier, com.slothiesmooth, com.slothiesmooth.links-detektor, hu.bme.mit.theta, dev.nelmin.spigot, (+6 more)
kotlinx.collections.immutable  [explicit rule: owned by `org.jetbrains`; 1 other group(s) rejected]
  ? * org.jetbrains.kotlinx                2026-07..2026-08 0.5.2                |..................==|
  ?   org.jetbrains.kotlin                 2026-07..2026-11 2.5.0-RC             |..................==|
  ?   com.salesforce.revoman               2026-08..2026-08 0.90.0               |..................=.|
org.objectweb.asm.tree.analysis  [explicit rule: owned by `org.ow2.asm`; 17 other group(s) rejected]
  A * org.ow2.asm                          2018-01..2026-10 9.11.1               |..==================|
  R   org.bonitasoft.bpm                   2025-03..2026-11 10.0.5               |................====|
  ?   io.github.jarrunner.teavm            2026-10..2026-10 0.16.0-jr.1          |...................=|
  R   org.teavm                            2023-03..2026-10 0.16.0               |............========|
  ?   ch.exense.step                       2026-09..2026-09 3.30.4               |...................=|
  ?   org.apache.aries.spifly              2026-09..2026-09 1.3.8                |...................=|
    + 12 more: com.codenameone, org.openidentityplatform.openidm.tools, org.mock-server, io.github.moliholy, com.webforj, com.weedow, org.noear, fish.payara.extras, za.co.absa.hermes, com.yotpo, org.apache.felix, build.please
feign.slf4j  [explicit rule: owned by `io.github.openfeign`; 1 other group(s) rejected]
  ? * io.github.openfeign                  2023-02..2026-11 14.0-alpha1          |............========|
  ?   org.apache.james                     2026-09..2026-09 3.9.1                |...................=|
feign.gson  [explicit rule: owned by `io.github.openfeign`; 2 other group(s) rejected]
  A * io.github.openfeign                  2023-02..2026-11 14.0-alpha1          |............========|
  ?   com.licensespring                    2026-10..2026-10 2.31.0               |...................=|
  R   com.appdynamics                      2025-10..2025-10 25.08.1524           |.................=..|
scala.library  [explicit rule: owned by `org.scala-lang`; 3 other group(s) rejected]
  A * org.scala-lang                       2018-03..2026-11 3.10.1               |..==================|
  ?   dev.propensive                       2026-08..2026-10 3.9.1-dev-p20        |..................==|
  ?   ch.epfl.lara                         2026-06..2026-09 3.10.1-RC1-bin-20260903-e1f9361-NIGHTLY |..................==|
  R   com.github.xuwei-k                   2021-01..2021-01 2.13.3-bin-1ca7d14   |........=...........|
kora.konvert.ksp.extension  [explicit rule: owned by `ru.tinkoff.kora`, `io.koraframework`; 0 other group(s) rejected]
  ? * ru.tinkoff.kora                      2026-07..2026-11 1.2.25               |..................==|
  ?   io.koraframework                     2026-08..2026-10 2.0.0.RC2            |..................==|
kora.s3.client.aws  [explicit rule: owned by `ru.tinkoff.kora`, `io.koraframework`; 0 other group(s) rejected]
  ? * ru.tinkoff.kora.experimental         2025-01..2026-11 1.2.25               |................====|
  ?   io.koraframework                     2026-08..2026-10 2.0.0.RC2            |..................==|
reactor.core  [explicit rule: owned by `io.projectreactor`; 2 other group(s) rejected]
  ? * io.projectreactor                    2019-09..2026-10 3.8.8                |.....===============|
  ?   com.guicedee.modules.services        2026-08..2026-11 2.5.1                |...................=|
  ?   io.github.ltslab                     2026-10..2026-10 3.7.19-ltslab.1      |...................=|
com.sun.jna.platform  [explicit rule: owned by `net.java.dev.jna`; 2 other group(s) rejected]
  A * net.java.dev.jna                     2018-10..2026-06 5.19.1               |...================.|
  ?   com.guicedee.modules.services        2026-10..2026-11 2.5.1                |...................=|
  R   ch.reportingsoft.birt                2025-04..2025-07 5.17.0               |................=...|
org.dataloader  [explicit rule: owned by `com.graphql-java`; 2 other group(s) rejected]
  A * com.graphql-java                     2022-06..2026-11 0.0.0-2026-11-01T06-25-28-9a28f7d |..........==========|
  ?   com.guicedee.modules.services        2026-07..2026-11 2.5.1                |..................==|
  R   com.liferay                          2025-05..2025-05 3.2.0.JAKARTA-LIFERAY-PATCHED-1 |................=...|
org.objectweb.asm.util  [explicit rule: owned by `org.ow2.asm`; 21 other group(s) rejected]
  A * org.ow2.asm                          2017-07..2026-10 9.11.1               |.===================|
  ?   com.javasolver                       2026-11..2026-11 3.0.0                |...................=|
  R   io.camunda                           2025-09..2026-11 8.7.45               |.................===|
  ?   io.github.jarrunner.teavm            2026-10..2026-10 0.16.0-jr.1          |...................=|
  R   org.teavm                            2023-03..2026-10 0.16.0               |............========|
  ?   org.wso2.orbit.org.openjdk.nashorn   2026-09..2026-09 15.7.0.wso2v1        |...................=|
    + 16 more: io.jactl, com.vmlens, com.webforj, io.joynr.tools.generator, io.joern, com.rookout, io.btrace, com.codacy, org.soot-oss, org.activecomponents.jadex, com.github.luxlang, org.geneweaver, (+4 more)
com.sun.xml.txw2  [explicit rule: owned by `org.glassfish.jaxb`; 9 other group(s) rejected]
  A * org.glassfish.jaxb                   2018-07..2026-05 4.0.9                |...================.|
  R   org.uma.jmetal                       2025-12..2026-11 7.11                 |.................===|
  ?   ch.exense.step                       2026-08..2026-09 3.30.4               |..................==|
  R   io.github.jeff-tian                  2026-02..2026-02 2.4.1                |..................=.|
  R   ai.starlake                          2022-04..2025-05 1.3.5                |..........=======...|
  R   com.jordansamhi                      2024-08..2024-08 1.1.8                |...............=....|
    + 4 more: org.soot-oss, com.yotpo, cn.lzgabel.jaxb, org.apache.servicemix.bundles
spring.test  [explicit rule: owned by `org.springframework`; 6 other group(s) rejected]
  A * org.springframework                  2017-09..2026-11 7.1.0-RC2            |.===================|
  ?   org.nordix                           2026-11..2026-11 5.3.39-est-1         |...................=|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |..................=.|
  R   org.gov4j.thirdparty.org.springframework 2024-12..2025-05 5.3.39-gov4j-2       |...............==...|
  R   com.liferay                          2025-05..2025-05 5.3.39.JAKARTA-LIFERAY-PATCHED-1 |................=...|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
    + 1 more: com.succsoft
spring.web  [explicit rule: owned by `org.springframework`; 9 other group(s) rejected]
  A * org.springframework                  2017-09..2026-11 7.1.0-RC2            |.===================|
  ?   org.nordix                           2026-11..2026-11 5.3.39-est-1         |...................=|
  ?   io.github.ltslab                     2026-10..2026-10 6.2.19-ltslab.1      |...................=|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |..................=.|
  R   org.gov4j.thirdparty.org.springframework 2024-12..2025-05 5.3.39-gov4j-2       |...............==...|
  R   com.liferay                          2025-05..2025-05 5.3.39.JAKARTA-LIFERAY-PATCHED-1 |................=...|
    + 4 more: net.xdob.springframework, com.succsoft, io.github.redteamobile, io.github.tfedyanin.springframework
spring.webflux  [explicit rule: owned by `org.springframework`; 5 other group(s) rejected]
  A * org.springframework                  2017-09..2026-11 7.1.0-RC2            |.===================|
  ?   org.nordix                           2026-11..2026-11 5.3.39-est-1         |...................=|
  ?   io.github.ltslab                     2026-10..2026-10 6.2.19-ltslab.1      |...................=|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |..................=.|
  R   org.gov4j.thirdparty.org.springframework 2024-12..2025-05 5.3.39-gov4j-2       |...............==...|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
spring.webmvc  [explicit rule: owned by `org.springframework`; 6 other group(s) rejected]
  A * org.springframework                  2017-09..2026-11 7.1.0-RC2            |.===================|
  ?   org.nordix                           2026-11..2026-11 5.3.39-est-1         |...................=|
  ?   io.github.ltslab                     2026-10..2026-10 6.2.19-ltslab.1      |...................=|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |..................=.|
  R   org.gov4j.thirdparty.org.springframework 2024-12..2025-05 5.3.39-gov4j-2       |...............==...|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
    + 1 more: com.succsoft
spring.websocket  [explicit rule: owned by `org.springframework`; 4 other group(s) rejected]
  A * org.springframework                  2017-09..2026-11 7.1.0-RC2            |.===================|
  ?   org.nordix                           2026-11..2026-11 5.3.39-est-1         |...................=|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |..................=.|
  R   org.gov4j.thirdparty.org.springframework 2024-12..2025-05 5.3.39-gov4j-2       |...............==...|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
spring.aop  [explicit rule: owned by `org.springframework`; 4 other group(s) rejected]
  A * org.springframework                  2017-09..2026-11 7.1.0-RC2            |.===================|
  ?   org.nordix                           2026-11..2026-11 5.3.39-est-1         |...................=|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |..................=.|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
  R   com.succsoft                         2024-12..2024-12 5.3.42               |...............=....|
spring.beans  [explicit rule: owned by `org.springframework`; 6 other group(s) rejected]
  A * org.springframework                  2017-09..2026-11 7.1.0-RC2            |.===================|
  ?   org.nordix                           2026-11..2026-11 5.3.39-est-1         |...................=|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |..................=.|
  R   org.gov4j.thirdparty.org.springframework 2024-12..2025-05 5.3.39-gov4j-2       |...............==...|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
  R   com.succsoft                         2024-12..2024-12 5.3.42               |...............=....|
    + 1 more: com.liferay
spring.context  [explicit rule: owned by `org.springframework`; 6 other group(s) rejected]
  A * org.springframework                  2017-09..2026-11 7.1.0-RC2            |.===================|
  ?   org.nordix                           2026-11..2026-11 5.3.39-est-1         |...................=|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |..................=.|
  R   org.gov4j.thirdparty.org.springframework 2024-12..2025-05 5.3.39-gov4j-2       |...............==...|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
  R   com.succsoft                         2024-12..2024-12 5.3.42               |...............=....|
    + 1 more: com.liferay
spring.context.indexer  [explicit rule: owned by `org.springframework`; 3 other group(s) rejected]
  A * org.springframework                  2017-09..2026-11 7.1.0-RC2            |.===================|
  ?   org.nordix                           2026-11..2026-11 5.3.39-est-1         |...................=|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |..................=.|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
spring.context.support  [explicit rule: owned by `org.springframework`; 6 other group(s) rejected]
  A * org.springframework                  2017-09..2026-11 7.1.0-RC2            |.===================|
  ?   org.nordix                           2026-11..2026-11 5.3.39-est-1         |...................=|
  ?   io.github.ltslab                     2026-10..2026-10 6.2.19-ltslab.1      |...................=|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |..................=.|
  R   org.gov4j.thirdparty.org.springframework 2024-12..2025-05 5.3.39-gov4j-2       |...............==...|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
    + 1 more: com.succsoft
spring.core  [explicit rule: owned by `org.springframework`; 6 other group(s) rejected]
  A * org.springframework                  2017-09..2026-11 7.1.0-RC2            |.===================|
  ?   org.nordix                           2026-11..2026-11 5.3.39-est-1         |...................=|
  ?   io.github.ltslab                     2026-10..2026-10 6.2.19-ltslab.1      |...................=|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |..................=.|
  R   org.gov4j.thirdparty.org.springframework 2024-12..2025-05 5.3.39-gov4j-2       |...............==...|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
    + 1 more: com.succsoft
spring.expression  [explicit rule: owned by `org.springframework`; 6 other group(s) rejected]
  A * org.springframework                  2017-09..2026-11 7.1.0-RC2            |.===================|
  ?   org.nordix                           2026-11..2026-11 5.3.39-est-1         |...................=|
  ?   io.github.ltslab                     2026-10..2026-10 6.2.19-ltslab.1      |...................=|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |..................=.|
  R   org.gov4j.thirdparty.org.springframework 2024-12..2025-05 5.3.39-gov4j-2       |...............==...|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
    + 1 more: com.succsoft
spring.instrument  [explicit rule: owned by `org.springframework`; 3 other group(s) rejected]
  A * org.springframework                  2017-09..2026-11 7.1.0-RC2            |.===================|
  ?   org.nordix                           2026-11..2026-11 5.3.39-est-1         |...................=|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |..................=.|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
spring.jdbc  [explicit rule: owned by `org.springframework`; 4 other group(s) rejected]
  A * org.springframework                  2017-09..2026-11 7.1.0-RC2            |.===================|
  ?   org.nordix                           2026-11..2026-11 5.3.39-est-1         |...................=|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |..................=.|
  R   org.gov4j.thirdparty.org.springframework 2024-12..2025-05 5.3.39-gov4j-2       |...............==...|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
spring.jms  [explicit rule: owned by `org.springframework`; 4 other group(s) rejected]
  A * org.springframework                  2017-09..2026-11 7.1.0-RC2            |.===================|
  ?   org.nordix                           2026-11..2026-11 5.3.39-est-1         |...................=|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |..................=.|
  R   org.gov4j.thirdparty.org.springframework 2024-12..2025-05 5.3.39-gov4j-2       |...............==...|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
spring.messaging  [explicit rule: owned by `org.springframework`; 4 other group(s) rejected]
  A * org.springframework                  2017-09..2026-11 7.1.0-RC2            |.===================|
  ?   org.nordix                           2026-11..2026-11 5.3.39-est-1         |...................=|
  ?   io.github.ltslab                     2026-10..2026-10 6.2.19-ltslab.1      |...................=|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |..................=.|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
spring.orm  [explicit rule: owned by `org.springframework`; 4 other group(s) rejected]
  A * org.springframework                  2017-09..2026-11 7.1.0-RC2            |.===================|
  ?   org.nordix                           2026-11..2026-11 5.3.39-est-1         |...................=|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |..................=.|
  R   com.liferay                          2022-03..2025-05 5.3.39.LIFERAY-PATCHED-1.JAKARTA-LIFERAY-PATCHED-1 |..........=======...|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
spring.oxm  [explicit rule: owned by `org.springframework`; 4 other group(s) rejected]
  A * org.springframework                  2017-09..2026-11 7.1.0-RC2            |.===================|
  ?   org.nordix                           2026-11..2026-11 5.3.39-est-1         |...................=|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |..................=.|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
  R   com.succsoft                         2024-12..2024-12 5.3.42               |...............=....|
spring.r2dbc  [explicit rule: owned by `org.springframework`; 3 other group(s) rejected]
  A * org.springframework                  2020-10..2026-11 7.1.0-RC2            |.......=============|
  ?   org.nordix                           2026-11..2026-11 5.3.39-est-1         |...................=|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |..................=.|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
spring.tx  [explicit rule: owned by `org.springframework`; 6 other group(s) rejected]
  A * org.springframework                  2017-09..2026-11 7.1.0-RC2            |.===================|
  ?   org.nordix                           2026-11..2026-11 5.3.39-est-1         |...................=|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |..................=.|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
  R   com.succsoft                         2024-12..2024-12 5.3.42               |...............=....|
  R   com.liferay                          2024-01..2024-08 5.3.39.LIFERAY-PATCHED-1 |..............==....|
    + 1 more: com.labun
spring.aspects  [explicit rule: owned by `org.springframework`; 3 other group(s) rejected]
  A * org.springframework                  2017-09..2026-11 7.1.0-RC2            |.===================|
  ?   org.nordix                           2026-11..2026-11 5.3.39-est-1         |...................=|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |..................=.|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
spring.jcl  [explicit rule: owned by `org.springframework`; 3 other group(s) rejected]
  A * org.springframework                  2017-09..2026-06 6.2.19               |.==================.|
  ?   org.nordix                           2026-11..2026-11 5.3.39-est-1         |...................=|
  ?   io.github.duanluan.springframework   2026-06..2026-06 5.3.42               |..................=.|
  R   net.xdob.springframework             2025-03..2025-03 5.3.41               |................=...|
kotlinx.coroutines.slf4j  [explicit rule: owned by `org.jetbrains`; 5 other group(s) rejected]
  A * org.jetbrains.kotlinx                2023-03..2026-05 1.11.0               |............=======.|
  ?   io.github.erenalpaslan               2026-08..2026-11 0.4.0                |...................=|
  A   org.jetbrains.intellij.deps.kotlinx  2025-09..2026-09 1.11.0-intellij-1    |.................===|
  R   jp.co.gahojin.thrifty                2025-02..2025-05 4.6.3                |................=...|
  R   io.github.vooft                      2024-09..2025-02 0.5.4                |...............==...|
  R   dev.suresh.kmp                       2024-06..2024-07 0.15.0               |..............=.....|
    + 1 more: xyz.block
spring.security.web  [explicit rule: owned by `org.springframework`; 1 other group(s) rejected]
  ? * org.springframework.security         2017-11..2026-10 7.0.8                |..==================|
  ?   io.github.ltslab                     2026-10..2026-10 6.5.11-ltslab.2      |...................=|
spring.batch.core  [explicit rule: owned by `org.springframework`; 1 other group(s) rejected]
  ? * org.springframework.batch            2023-11..2026-10 6.0.6                |.............=======|
  ?   io.github.ltslab                     2026-10..2026-10 5.2.6-ltslab.2       |...................=|
spring.batch.infrastructure  [explicit rule: owned by `org.springframework`; 1 other group(s) rejected]
  ? * org.springframework.batch            2023-11..2026-10 6.0.6                |.............=======|
  ?   io.github.ltslab                     2026-10..2026-10 5.2.6-ltslab.2       |...................=|
spring.data.jpa  [explicit rule: owned by `org.springframework`; 2 other group(s) rejected]
  A * org.springframework.data             2017-10..2026-10 4.0.8                |.===================|
  ?   io.github.ltslab                     2026-10..2026-10 3.5.13-ltslab.2      |...................=|
  R   com.github.iherasymenko              2020-10..2020-11 2.3.4.2.RELEASE      |.......=............|
spring.data.rest.webmvc  [explicit rule: owned by `org.springframework`; 1 other group(s) rejected]
  ? * org.springframework.data             2017-10..2026-10 5.0.8                |.===================|
  ?   io.github.ltslab                     2026-10..2026-10 4.5.13-ltslab.2      |...................=|
spring.security.config  [explicit rule: owned by `org.springframework`; 1 other group(s) rejected]
  ? * org.springframework.security         2017-11..2026-10 7.0.8                |..==================|
  ?   io.github.ltslab                     2026-10..2026-10 6.5.11-ltslab.2      |...................=|
spring.security.core  [explicit rule: owned by `org.springframework`; 1 other group(s) rejected]
  ? * org.springframework.security         2017-11..2026-10 7.0.8                |..==================|
  ?   io.github.ltslab                     2026-10..2026-10 6.5.11-ltslab.2      |...................=|
spring.security.crypto  [explicit rule: owned by `org.springframework`; 2 other group(s) rejected]
  A * org.springframework.security         2017-11..2026-10 7.0.8                |..==================|
  ?   io.github.ltslab                     2026-10..2026-10 6.5.11-ltslab.2      |...................=|
  R   org.gov4j.thirdparty.org.springframework.security 2025-04..2025-04 5.8.16-gov4j-1       |................=...|
spring.security.ldap  [explicit rule: owned by `org.springframework`; 1 other group(s) rejected]
  ? * org.springframework.security         2017-11..2026-10 7.0.8                |..==================|
  ?   io.github.ltslab                     2026-10..2026-10 6.5.11-ltslab.2      |...................=|
spring.security.oauth2.authorization.server  [explicit rule: owned by `org.springframework`; 2 other group(s) rejected]
  A * org.springframework.security.experimental 2020-08..2021-07 0.1.2                |.......===..........|
  ?   io.github.ltslab                     2026-10..2026-10 1.5.8-ltslab.2       |...................=|
  A   org.springframework.security         2021-08..2026-10 7.0.8                |.........===========|
  R   io.springboot.security               2022-06..2022-06 0.3.0                |..........=.........|
spring.security.oauth2.core  [explicit rule: owned by `org.springframework`; 1 other group(s) rejected]
  ? * org.springframework.security         2017-11..2026-10 7.0.8                |..==================|
  ?   io.github.ltslab                     2026-10..2026-10 6.5.11-ltslab.2      |...................=|
spring.security.oauth2.jose  [explicit rule: owned by `org.springframework`; 1 other group(s) rejected]
  ? * org.springframework.security         2017-11..2026-10 7.0.8                |..==================|
  ?   io.github.ltslab                     2026-10..2026-10 6.5.11-ltslab.2      |...................=|
spring.security.oauth2.resource.server  [explicit rule: owned by `org.springframework`; 1 other group(s) rejected]
  ? * org.springframework.security         2018-09..2026-10 7.0.8                |...=================|
  ?   io.github.ltslab                     2026-10..2026-10 6.5.11-ltslab.2      |...................=|
spring.integration.core  [explicit rule: owned by `org.springframework`; 1 other group(s) rejected]
  ? * org.springframework.integration      2017-11..2026-10 7.0.7                |..==================|
  ?   io.github.ltslab                     2026-10..2026-10 6.5.10-ltslab.1      |...................=|
spring.kafka  [explicit rule: owned by `org.springframework`; 2 other group(s) rejected]
  A * org.springframework.kafka            2017-09..2026-10 4.0.8                |.===================|
  ?   io.github.ltslab                     2026-10..2026-10 3.3.16-ltslab.1      |...................=|
  R   ai.superstream                       2024-04..2024-05 2.8.4-alpha6         |..............=.....|
spring.amqp  [explicit rule: owned by `org.springframework`; 1 other group(s) rejected]
  ? * org.springframework.amqp             2017-10..2026-10 4.0.6                |.===================|
  ?   io.github.ltslab                     2026-10..2026-10 3.2.12-ltslab.1      |...................=|
spring.integration.file  [explicit rule: owned by `org.springframework`; 1 other group(s) rejected]
  ? * org.springframework.integration      2017-11..2026-10 7.0.7                |..==================|
  ?   io.github.ltslab                     2026-10..2026-10 6.5.10-ltslab.1      |...................=|
spring.integration.http  [explicit rule: owned by `org.springframework`; 1 other group(s) rejected]
  ? * org.springframework.integration      2017-11..2026-10 7.0.7                |..==================|
  ?   io.github.ltslab                     2026-10..2026-10 6.5.10-ltslab.1      |...................=|
spring.integration.ip  [explicit rule: owned by `org.springframework`; 1 other group(s) rejected]
  ? * org.springframework.integration      2017-11..2026-10 7.0.7                |..==================|
  ?   io.github.ltslab                     2026-10..2026-10 6.5.10-ltslab.1      |...................=|
spring.integration.jms  [explicit rule: owned by `org.springframework`; 1 other group(s) rejected]
  ? * org.springframework.integration      2017-11..2026-10 7.0.7                |..==================|
  ?   io.github.ltslab                     2026-10..2026-10 6.5.10-ltslab.1      |...................=|
spring.integration.syslog  [explicit rule: owned by `org.springframework`; 1 other group(s) rejected]
  ? * org.springframework.integration      2017-11..2026-10 7.0.7                |..==================|
  ?   io.github.ltslab                     2026-10..2026-10 6.5.10-ltslab.1      |...................=|
spring.integration.zip  [explicit rule: owned by `org.springframework`; 1 other group(s) rejected]
  ? * org.springframework.integration      2023-05..2026-10 7.0.7                |............========|
  ?   io.github.ltslab                     2026-10..2026-10 6.5.10-ltslab.1      |...................=|
spring.rabbit  [explicit rule: owned by `org.springframework`; 1 other group(s) rejected]
  ? * org.springframework.amqp             2017-10..2026-10 4.0.6                |.===================|
  ?   io.github.ltslab                     2026-10..2026-10 3.2.12-ltslab.1      |...................=|
spring.rabbit.junit  [explicit rule: owned by `org.springframework`; 1 other group(s) rejected]
  ? * org.springframework.amqp             2017-10..2026-10 4.0.6                |.===================|
  ?   io.github.ltslab                     2026-10..2026-10 3.2.12-ltslab.1      |...................=|
reactor.netty.core  [explicit rule: owned by `io.projectreactor`; 2 other group(s) rejected]
  A * io.projectreactor.netty              2020-10..2026-10 1.3.8                |.......=============|
  ?   io.github.ltslab                     2026-10..2026-10 1.2.18-ltslab.1      |...................=|
  R   io.github.sunny-chung                2024-06..2024-06 1.1.20-patch1        |..............=.....|
reactor.netty.http  [explicit rule: owned by `io.projectreactor`; 2 other group(s) rejected]
  A * io.projectreactor.netty              2020-10..2026-10 1.3.8                |.......=============|
  ?   io.github.ltslab                     2026-10..2026-10 1.2.18-ltslab.1      |...................=|
  R   io.github.sunny-chung                2024-06..2024-06 1.1.20-patch1        |..............=.....|
reactor.netty.http.brave  [explicit rule: owned by `io.projectreactor`; 1 other group(s) rejected]
  ? * io.projectreactor.netty              2020-10..2026-10 1.3.8                |.......=============|
  ?   io.github.ltslab                     2026-10..2026-10 1.2.18-ltslab.1      |...................=|
kotlinx.coroutines.test.artifact_disambiguating_module  [explicit rule: owned by `org.jetbrains`; 0 other group(s) rejected]
  ? * org.jetbrains.kotlinx                2026-04..2026-05 1.11.0               |..................=.|
  ?   org.jetbrains.intellij.deps.kotlinx  2026-09..2026-09 1.11.0-intellij-1    |...................=|
kotlinx.coroutines.core.artifact_disambiguating_module  [explicit rule: owned by `org.jetbrains`; 0 other group(s) rejected]
  ? * org.jetbrains.kotlinx                2026-04..2026-05 1.11.0               |..................=.|
  ?   org.jetbrains.intellij.deps.kotlinx  2026-09..2026-09 1.11.0-intellij-1    |...................=|
imgui.app  [explicit rule: owned by `io.github.spair`; 1 other group(s) rejected]
  ? * io.github.spair                      2022-12..2026-07 1.92.7.1             |...........========.|
  ?   io.github.lionblazer                 2026-08..2026-08 1.92.5.1             |...................=|
com.sun.tools.xjc  [explicit rule: owned by `org.glassfish.jaxb`; 4 other group(s) rejected]
  A * org.glassfish.jaxb                   2018-07..2019-01 2.3.2                |...==...............|
  ?   gov.nasa.pds                         2026-06..2026-06 3.2.1                |..................=.|
  R   com.sun.xml.bind                     2018-07..2026-05 4.0.9                |...================.|
  R   cn.lzgabel.jaxb.xml.bind             2022-03..2022-03 4.0.0                |..........=.........|
  R   com.github.shynixn                   2019-02..2019-02 1.0                  |....=...............|
```

## republisher (7)

Earliest owner is foreign to the module name while a natural-namespace owner is also present (shaded / repackaged jars).

- The earliest (current) owner's groupId is foreign to the module name: the name does not fall under it.
- A natural-namespace owner - a publisher whose groupId the module name does fall under - is also present.
- The foreign earliest owner is still globally active (a dormant one would be a relocation, see migration).
- Allow the natural owner; reject the foreign republisher.

| count | current owner | new owner(s) |
|---:|---|---|
| 1 | `com.github.ppodgorsek.email` | `org.apache.commons` |
| 1 | `com.sun.activation` | `jakarta.activation` |
| 1 | `com.sun.mail` | `jakarta.mail` |
| 1 | `io.github.pustike` | `org.apache.commons` |
| 1 | `io.github.shafthq` | `io.qameta.allure` |
| 1 | `io.sdks` | `com.univapay` |
| 1 | `org.glassfish.jaxb` | `com.sun.xml.bind` |

```
com.sun.xml.bind  [republished by `org.glassfish.jaxb` (still active); belongs to `com.sun.xml.bind`]
  R * org.glassfish.jaxb                   2018-07..2019-01 2.3.2                |...==...............|
  R   com.helger.schematron                2021-09..2026-11 10.3.3               |.........===========|
  R   gov.nasa.pds                         2021-10..2026-10 4.0.1                |.........===========|
  R   int.esa.ccsds.mo                     2025-08..2026-08 14.2                 |.................===|
  ?   com.checkmarx                        2026-06..2026-06 2026.2.32            |..................=.|
  A   com.sun.xml.bind                     2018-07..2026-05 4.0.9                |...================.|
    + 32 more: com.exasol, com.intuit.quickbooks-online, com.google.tsunami, io.github.azagniotov, com.liferay, org.opengis.cite, org.duracloud, edu.iris.dmc, br.com.swconsultoria, one.gfw, org.xtce, com.github.cafapi, (+20 more)
jakarta.activation  [republished by `com.sun.activation` (still active); belongs to `jakarta.activation`]
  R * com.sun.activation                   2018-11..2021-02 2.0.1                |....=====...........|
  R   com.github.xeroapi                   2025-06..2026-11 18.5.0               |................====|
  R   com.cognite.spark.datasource         2022-01..2026-11 4.1.1250             |..........==========|
  R   org.tinystruct                       2025-09..2026-11 1.1.12               |.................===|
  R   org.uma.jmetal                       2025-12..2026-11 7.11                 |.................===|
  R   com.newrelic.labs                    2025-06..2026-10 1.6.0                |................====|
    + 40 more: com.eed3si9n, com.typesafe.play, org.playframework, net.anotheria, org.kill-bill.billing, com.helger.schematron, org.takes, jakarta.activation, org.hpccsystems, ch.exense.step, io.github.dimabarbul, org.opengis.cite, (+28 more)
org.apache.commons.csv  [republished by `io.github.pustike` (still active); belongs to `org.apache.commons`]
  ? * io.github.pustike                    2019-01..2019-07 1.7.0                |....==..............|
  ?   org.sonarsource.scanner.engine       2026-05..2026-11 13.19.0.6869         |..................==|
  ?   com.orientechnologies                2026-03..2026-11 3.2.58               |..................==|
  ?   com.guicedee.modules.services        2026-04..2026-11 2.5.1                |..................==|
  ?   org.apache.pinot                     2024-08..2026-10 1.6.0                |...............=====|
  ?   be.ugent.idlab.knows                 2025-09..2026-09 5.0.0                |.................===|
    + 10 more: org.testingisdocumenting.znai, io.kestra.plugin, org.apache.commons, xyz.ottr.lutra, org.jetbrains.kotlinx, com.wizzdi, io.telicent.jena.graphql, io.telicent.jena, org.apache.jena, com.guicedee.services
org.apache.commons.mail  [republished by `com.github.ppodgorsek.email` (still active); belongs to `org.apache.commons`]
  ? * com.github.ppodgorsek.email          2023-06..2023-06 2.0.0                |............=.......|
  ?   com.walmartlabs.concord.plugins.basic 2026-11..2026-11 2.47.0               |...................=|
  ?   io.prophecy                          2024-08..2026-09 4.0.0-ml-9.6.0       |...............=====|
  ?   org.apache.commons                   2023-12..2023-12 1.6.0                |.............=......|
jakarta.mail  [republished by `com.sun.mail` (still active); belongs to `jakarta.mail`]
  R * com.sun.mail                         2018-11..2025-07 2.0.2                |....=============...|
  ?   com.uchicom                          2026-11..2026-11 0.2.2                |...................=|
  ?   org.tinystruct                       2026-08..2026-09 1.7.32               |..................==|
  R   io.github.noeltoy                    2024-10..2026-08 1.2                  |...............====.|
  ?   org.cibseven.community.mail          2026-08..2026-08 2.2.0                |..................=.|
  ?   org.apache.manifoldcf                2026-07..2026-07 2.31                 |..................=.|
    + 8 more: com.randomnoun.db, org.eclipse.angus, jakarta.mail, org.camunda.bpm.extension, name.bychkov, io.gravitee.apim.rest.api.standalone, com.krux, com.guicedee.services
io.qameta.allure.cucumber8jvm  [republished by `io.github.shafthq` (still active); belongs to `io.qameta.allure`]
  ? * io.github.shafthq                    2026-10..2026-11 10.4.20261102        |...................=|
  ?   io.qameta.allure                     2026-11..2026-11 3.1.1                |...................=|
com.univapay.api  [republished by `io.sdks` (still active); belongs to `com.univapay`]
  ? * io.sdks                              2026-07..2026-09 1.0.3                |..................==|
  ?   com.univapay                         2026-08..2026-09 1.2.3                |..................==|
```

## migration (62)

The publishing groupId handed off over time (a rename or a relocation), so both coordinates are kept.

- Rename: a more-recent successor is the same project as the owner (a shared groupId prefix, or two shared leading segments).
- Relocation: the owner stopped at or before a credible successor took over (or the owner went globally dormant), and that successor itself owns the module namespace.
- Allow both old and new so history stays resolvable and `latest` is current.

| count | current owner | new owner(s) |
|---:|---|---|
| 13 | `org.hibernate` | `org.hibernate, org.hibernate.orm` |
| 10 | `org.graceframework` | `org.graceframework, org.graceframework.data` |
| 5 | `io.github.octofleet` | `io.github.octofleet, io.github.wordyarc` |
| 4 | `dev.langchain4j.cdi.mcp` | `dev.langchain4j.cdi.mcp, dev.langchain4j.cdi` |
| 4 | `dev.mohterbaord` | `dev.mohterbaord, dev.mohterbaord.fp4j` |
| 3 | `io.github.humbleui.skija` | `io.github.humbleui.skija, io.github.humbleui` |
| 2 | `app.oreshkov.delva` | `app.oreshkov.delva, app.oreshkov.clikit` |
| 2 | `com.rainccup` | `com.rainccup, com.rainccup.jwkt` |
| 2 | `love.forte.simbot.logger` | `love.forte.simbot.logger, love.forte.simbot` |
| 1 | `com.aliyun.polardb2` | `com.aliyun.polardb2, com.aliyun` |
| 1 | `com.atlassian.commonmark` | `com.atlassian.commonmark, org.commonmark` |
| 1 | `com.guicedee.services` | `com.guicedee.services, com.guicedee.modules.services` |
| 1 | `com.pambrose` | `com.pambrose, com.pambrose.jev4k` |
| 1 | `com.peruncs.odbjca` | `com.peruncs.odbjca, com.peruncs` |
| 1 | `dev.kaiquebt` | `dev.kaiquebt, dev.kaiquebt.anycall` |

```
dev.kaiquebt.anycall  [renamed `dev.kaiquebt` -> `dev.kaiquebt.anycall` (latest 0.10.0)]
  ? * dev.kaiquebt                         2026-06..2026-06 0.1.1                |..................=.|
  ?   dev.kaiquebt.anycall                 2026-08..2026-11 0.10.0               |..................==|
grace.datastore.gorm.bson  [renamed `org.graceframework` -> `org.graceframework.data` (latest 2025.0.0-M2)]
  ? * org.graceframework                   2026-11..2026-11 2024.2.0             |...................=|
  ?   org.graceframework.data              2026-11..2026-11 2025.0.0-M2          |...................=|
grace.datastore.gorm.mongodb  [renamed `org.graceframework` -> `org.graceframework.data` (latest 2025.0.0-M2)]
  ? * org.graceframework                   2026-11..2026-11 2024.2.0             |...................=|
  ?   org.graceframework.data              2026-11..2026-11 2025.0.0-M2          |...................=|
grace.datastore.core  [renamed `org.graceframework` -> `org.graceframework.data` (latest 2025.0.0-M2)]
  ? * org.graceframework                   2026-11..2026-11 2024.2.0             |...................=|
  ?   org.graceframework.data              2026-11..2026-11 2025.0.0-M2          |...................=|
grace.datastore.gorm  [renamed `org.graceframework` -> `org.graceframework.data` (latest 2025.0.0-M2)]
  ? * org.graceframework                   2026-11..2026-11 2024.2.0             |...................=|
  ?   org.graceframework.data              2026-11..2026-11 2025.0.0-M2          |...................=|
grace.datastore.gorm.async  [renamed `org.graceframework` -> `org.graceframework.data` (latest 2025.0.0-M2)]
  ? * org.graceframework                   2026-11..2026-11 2024.2.0             |...................=|
  ?   org.graceframework.data              2026-11..2026-11 2025.0.0-M2          |...................=|
grace.datastore.gorm.support  [renamed `org.graceframework` -> `org.graceframework.data` (latest 2025.0.0-M2)]
  ? * org.graceframework                   2026-11..2026-11 2024.2.0             |...................=|
  ?   org.graceframework.data              2026-11..2026-11 2025.0.0-M2          |...................=|
grace.datastore.gorm.tck  [renamed `org.graceframework` -> `org.graceframework.data` (latest 2025.0.0-M2)]
  ? * org.graceframework                   2026-11..2026-11 2024.2.0             |...................=|
  ?   org.graceframework.data              2026-11..2026-11 2025.0.0-M2          |...................=|
grace.datastore.gorm.test  [renamed `org.graceframework` -> `org.graceframework.data` (latest 2025.0.0-M2)]
  ? * org.graceframework                   2026-11..2026-11 2024.2.0             |...................=|
  ?   org.graceframework.data              2026-11..2026-11 2025.0.0-M2          |...................=|
grace.datastore.gorm.validation  [renamed `org.graceframework` -> `org.graceframework.data` (latest 2025.0.0-M2)]
  ? * org.graceframework                   2026-11..2026-11 2024.2.0             |...................=|
  ?   org.graceframework.data              2026-11..2026-11 2025.0.0-M2          |...................=|
grace.datastore.web  [renamed `org.graceframework` -> `org.graceframework.data` (latest 2025.0.0-M2)]
  ? * org.graceframework                   2026-11..2026-11 2024.2.0             |...................=|
  ?   org.graceframework.data              2026-11..2026-11 2025.0.0-M2          |...................=|
org.hibernate.orm.core  [renamed `org.hibernate` -> `org.hibernate.orm` (latest 7.4.16.Final)]
  A * org.hibernate                        2018-01..2026-06 5.3.39.Final         |..=================.|
  A   org.hibernate.orm                    2018-12..2026-11 7.4.16.Final         |....================|
  R   com.guicedee.modules.services        2026-04..2026-11 2.5.1                |..................==|
  R   org.beangle.hibernate                2020-06..2026-11 7.4.14.Final         |.......=============|
  ?   io.github.martinhickson              2026-08..2026-08 6.6.7-bravura-1      |...................=|
  R   io.github.vmodi001                   2026-03..2026-03 5.6.16.Final         |..................=.|
    + 2 more: com.liferay, com.guicedee.services
org.hibernate.orm.spatial  [renamed `org.hibernate` -> `org.hibernate.orm` (latest 7.4.16.Final)]
  A * org.hibernate                        2018-01..2026-06 5.3.39.Final         |..=================.|
  A   org.hibernate.orm                    2021-10..2026-11 7.4.16.Final         |.........===========|
  ?   io.github.martinhickson              2026-08..2026-08 6.6.7-bravura-1      |...................=|
org.hibernate.orm.testing  [renamed `org.hibernate` -> `org.hibernate.orm` (latest 7.4.16.Final)]
  A * org.hibernate                        2018-01..2026-06 5.3.39.Final         |..=================.|
  A   org.hibernate.orm                    2018-12..2026-11 7.4.16.Final         |....================|
  ?   io.github.martinhickson              2026-08..2026-08 6.6.7-bravura-1      |...................=|
org.hibernate.orm.agroal  [renamed `org.hibernate` -> `org.hibernate.orm` (latest 7.4.16.Final)]
  A * org.hibernate                        2018-02..2026-06 5.3.39.Final         |..=================.|
  A   org.hibernate.orm                    2018-12..2026-11 7.4.16.Final         |....================|
  ?   io.github.martinhickson              2026-08..2026-08 6.6.7-bravura-1      |...................=|
org.hibernate.orm.c3p0  [renamed `org.hibernate` -> `org.hibernate.orm` (latest 7.4.16.Final)]
  A * org.hibernate                        2018-01..2026-06 5.3.39.Final         |..=================.|
  A   org.hibernate.orm                    2018-12..2026-11 7.4.16.Final         |....================|
  ?   io.github.martinhickson              2026-08..2026-08 6.6.7-bravura-1      |...................=|
  R   com.guicedee.modules.services        2026-04..2026-04 2.0.0-RC10           |..................=.|
  R   com.guicedee.services                2020-07..2022-02 1.2.2.1-jre17        |.......====.........|
org.hibernate.orm.envers  [renamed `org.hibernate` -> `org.hibernate.orm` (latest 7.4.16.Final)]
  A * org.hibernate                        2018-01..2026-06 5.3.39.Final         |..=================.|
  A   org.hibernate.orm                    2019-11..2026-11 7.4.16.Final         |.....===============|
  ?   io.github.martinhickson              2026-08..2026-08 6.6.7-bravura-1      |...................=|
org.hibernate.orm.graalvm  [renamed `org.hibernate` -> `org.hibernate.orm` (latest 7.4.16.Final)]
  A * org.hibernate                        2020-02..2023-02 5.6.15.Final         |......=======.......|
  A   org.hibernate.orm                    2020-04..2026-11 7.4.16.Final         |......==============|
  ?   io.github.martinhickson              2026-08..2026-08 6.6.7-bravura-1      |...................=|
org.hibernate.orm.hikaricp  [renamed `org.hibernate` -> `org.hibernate.orm` (latest 7.4.16.Final)]
  A * org.hibernate                        2018-01..2026-06 5.3.39.Final         |..=================.|
  A   org.hibernate.orm                    2018-12..2026-11 7.4.16.Final         |....================|
  ?   io.github.martinhickson              2026-08..2026-08 6.6.7-bravura-1      |...................=|
org.hibernate.orm.jcache  [renamed `org.hibernate` -> `org.hibernate.orm` (latest 7.4.16.Final)]
  A * org.hibernate                        2018-01..2026-06 5.3.39.Final         |..=================.|
  A   org.hibernate.orm                    2019-11..2026-11 7.4.16.Final         |.....===============|
  R   com.guicedee.modules.services        2026-04..2026-11 2.5.1                |..................==|
  ?   io.github.martinhickson              2026-08..2026-08 6.6.7-bravura-1      |...................=|
  R   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17        |.....======.........|
org.hibernate.orm.micrometer  [renamed `org.hibernate` -> `org.hibernate.orm` (latest 7.4.16.Final)]
  A * org.hibernate                        2020-12..2023-02 5.6.15.Final         |........=====.......|
  A   org.hibernate.orm                    2021-03..2026-11 7.4.16.Final         |........============|
  ?   io.github.martinhickson              2026-08..2026-08 6.6.7-bravura-1      |...................=|
cad.builder.cad.dsl.main  [renamed `org.taack.cad` -> `org.taack` (latest 0.7)]
  ? * org.taack.cad                        2026-04..2026-04 0.1                  |..................=.|
  ?   org.taack                            2026-10..2026-11 0.7                  |...................=|
com.pambrose.jev4k  [renamed `com.pambrose` -> `com.pambrose.jev4k` (latest 0.4.1)]
  ? * com.pambrose                         2026-09..2026-09 0.1.0                |...................=|
  ?   com.pambrose.jev4k                   2026-09..2026-11 0.4.1                |...................=|
org.apache.commons.beanutils  [renamed `com.guicedee.services` -> `com.guicedee.modules.services` (latest 2.5.1)]
  ? * com.guicedee.services                2020-06..2022-02 1.2.2.1-jre17        |.......====.........|
  ?   com.guicedee.modules.services        2026-04..2026-11 2.5.1                |..................==|
  ?   org.wildfly                          2025-06..2026-10 42.0.0.Final         |................====|
  ?   io.github.stoyank7                   2026-06..2026-06 1.0.2                |..................=.|
  ?   org.jvnet.jaxb                       2025-09..2026-06 2.0.16               |.................==.|
  ?   com.github.bld-commons               2026-01..2026-05 3.0.19               |.................==.|
    + 4 more: kg.apc, com.github.bordertech.wcomponents, commons-beanutils, org.onebusaway
org.eclipse.paho.mqttv5.client  [renamed `org.geckoprojects.messaging` -> `org.geckoprojects.paho` (latest 1.3.0)]
  A * org.geckoprojects.messaging          2024-08..2024-12 1.2.7                |...............=....|
  ?   org.geckoprojects.paho               2026-11..2026-11 1.3.0                |...................=|
  A   org.geckoprojects.libraries          2024-12..2024-12 1.2.6.202412111240   |...............=....|
app.oreshkov.clikit  [renamed `app.oreshkov.delva` -> `app.oreshkov.clikit` (latest 0.4.0)]
  ? * app.oreshkov.delva                   2026-10..2026-10 0.1.0                |...................=|
  ?   app.oreshkov.clikit                  2026-10..2026-11 0.4.0                |...................=|
app.oreshkov.clikit.testing  [renamed `app.oreshkov.delva` -> `app.oreshkov.clikit` (latest 0.4.0)]
  ? * app.oreshkov.delva                   2026-10..2026-10 0.1.0                |...................=|
  ?   app.oreshkov.clikit                  2026-10..2026-11 0.4.0                |...................=|
org.apache.commons.dbcp2  [renamed `org.apache.commons` -> `org.apache.tomee` (latest 10.3.0)]
  ? * org.apache.commons                   2023-08..2025-12 2.14.0               |.............=====..|
  ?   org.apache.tomee                     2023-12..2026-11 10.3.0               |.............=======|
  ?   org.apache.meecrowave                2025-10..2025-10 2.0.0                |.................=..|
  ?   net.ontopia                          2025-04..2025-07 5.5.2                |................=...|
  ?   org.apache.openjpa                   2024-09..2025-05 4.1.1                |...............==...|
  ?   org.ikasan                           2024-07..2024-07 1.0.0                |..............=.....|
com.rainccup.jwkt  [renamed `com.rainccup` -> `com.rainccup.jwkt` (latest 0.1.0)]
  ? * com.rainccup                         2026-11..2026-11 0.1.0                |...................=|
  ?   com.rainccup.jwkt                    2026-11..2026-11 0.1.0                |...................=|
com.rainccup.jwkt.spring  [renamed `com.rainccup` -> `com.rainccup.jwkt` (latest 0.1.0)]
  ? * com.rainccup                         2026-11..2026-11 0.1.0                |...................=|
  ?   com.rainccup.jwkt                    2026-11..2026-11 0.1.0                |...................=|
io.ekbatan.graalvm  [renamed `io.github.zyraz-io` -> `io.github.ekbatan-io` (latest 1.1.3)]
  ? * io.github.zyraz-io                   2026-05..2026-08 1.0.0                |..................=.|
  ?   io.github.ekbatan-io                 2026-08..2026-11 1.1.3                |..................==|
org.apache.wicket.metrics  [renamed `org.apache.wicket.experimental.wicket9` -> `org.apache.wicket.experimental.wicket11` (latest 11.0.0)]
  A * org.apache.wicket.experimental.wicket9 2019-04..2026-10 0.37                 |....================|
  ?   org.apache.wicket.experimental.wicket11 2026-11..2026-11 11.0.0               |...................=|
  A   org.apache.wicket.experimental.wicket10 2024-05..2026-10 10.12.0              |..............======|
org.commonmark  [relocated `com.atlassian.commonmark` -> `org.commonmark` (latest 0.32.0)]
  R * com.atlassian.commonmark             2018-01..2021-01 0.17.0               |..=======...........|
  A   org.commonmark                       2021-01..2026-11 0.32.0               |........============|
  ?   org.apache.tika                      2026-08..2026-09 4.1.0                |...................=|
  R   com.qainsights                       2026-05..2026-05 0.0.1                |..................=.|
  R   se.alipsa.gmd                        2025-09..2025-09 3.0.1                |.................=..|
  R   org.lucee                            2024-05..2024-05 0.22.0               |..............=.....|
    + 1 more: org.aya-prover
simbot.logger.slf4j2impl  [renamed `love.forte.simbot.logger` -> `love.forte.simbot` (latest 5.0.0-Alpha2)]
  ? * love.forte.simbot.logger             2024-01..2026-05 5.0.0-Preview2       |.............======.|
  ?   love.forte.simbot                    2026-06..2026-10 5.0.0-Alpha2         |..................==|
simbot.logger  [renamed `love.forte.simbot.logger` -> `love.forte.simbot` (latest 5.0.0-Alpha2)]
  ? * love.forte.simbot.logger             2024-01..2026-05 5.0.0-Preview2       |.............======.|
  ?   love.forte.simbot                    2026-06..2026-10 5.0.0-Alpha2         |..................==|
org.neo4j.driver  [renamed `org.neo4j.driver` -> `org.neo4j.connectors` (latest 6.1.2-s_2.13)]
  A * org.neo4j.driver                     2018-02..2026-09 6.3.0                |..==================|
  ?   org.neo4j.connectors                 2026-10..2026-10 6.1.2-s_2.13         |...................=|
  R   org.geneweaver                       2025-06..2025-08 1.7.11               |................==..|
io.akki.core  [renamed `io.github.octofleet` -> `io.github.wordyarc` (latest 0.3.0)]
  ? * io.github.octofleet                  2026-09..2026-09 0.1.0                |...................=|
  ?   io.github.wordyarc                   2026-09..2026-10 0.3.0                |...................=|
io.akki.coroutines  [renamed `io.github.octofleet` -> `io.github.wordyarc` (latest 0.3.0)]
  ? * io.github.octofleet                  2026-09..2026-09 0.1.0                |...................=|
  ?   io.github.wordyarc                   2026-09..2026-10 0.3.0                |...................=|
io.akki.slf4j  [renamed `io.github.octofleet` -> `io.github.wordyarc` (latest 0.3.0)]
  ? * io.github.octofleet                  2026-09..2026-09 0.1.0                |...................=|
  ?   io.github.wordyarc                   2026-09..2026-10 0.3.0                |...................=|
io.akki.test  [renamed `io.github.octofleet` -> `io.github.wordyarc` (latest 0.3.0)]
  ? * io.github.octofleet                  2026-09..2026-09 0.1.0                |...................=|
  ?   io.github.wordyarc                   2026-09..2026-10 0.3.0                |...................=|
io.akki.test.coroutines  [renamed `io.github.octofleet` -> `io.github.wordyarc` (latest 0.3.0)]
  ? * io.github.octofleet                  2026-09..2026-09 0.1.0                |...................=|
  ?   io.github.wordyarc                   2026-09..2026-10 0.3.0                |...................=|
org.hibernate.orm.jpamodelgen  [renamed `org.hibernate` -> `org.hibernate.orm` (latest 6.6.59.Final)]
  A * org.hibernate                        2018-01..2026-06 5.3.39.Final         |..=================.|
  A   org.hibernate.orm                    2018-12..2026-10 6.6.59.Final         |....================|
  ?   io.github.martinhickson              2026-08..2026-08 6.6.7-bravura-1      |...................=|
  R   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17        |.....======.........|
org.hibernate.orm.proxool  [renamed `org.hibernate` -> `org.hibernate.orm` (latest 6.6.59.Final)]
  A * org.hibernate                        2018-01..2026-06 5.3.39.Final         |..=================.|
  A   org.hibernate.orm                    2018-12..2026-10 6.6.59.Final         |....================|
  ?   io.github.martinhickson              2026-08..2026-08 6.6.7-bravura-1      |...................=|
org.hibernate.orm.vibur  [renamed `org.hibernate` -> `org.hibernate.orm` (latest 6.6.59.Final)]
  A * org.hibernate                        2018-01..2026-06 5.3.39.Final         |..=================.|
  A   org.hibernate.orm                    2018-12..2026-10 6.6.59.Final         |....================|
  ?   io.github.martinhickson              2026-08..2026-08 6.6.7-bravura-1      |...................=|
dev.langchain4j.cdi.mcp.server  [renamed `dev.langchain4j.cdi.mcp` -> `dev.langchain4j.cdi` (latest 1.5.1)]
  ? * dev.langchain4j.cdi.mcp              2026-10..2026-10 1.5.0                |...................=|
  ?   dev.langchain4j.cdi                  2026-10..2026-10 1.5.1                |...................=|
dev.langchain4j.cdi.mcp.buildcompatible  [renamed `dev.langchain4j.cdi.mcp` -> `dev.langchain4j.cdi` (latest 1.5.1)]
  ? * dev.langchain4j.cdi.mcp              2026-10..2026-10 1.5.0                |...................=|
  ?   dev.langchain4j.cdi                  2026-10..2026-10 1.5.1                |...................=|
dev.langchain4j.cdi.mcp.invoker.cdi41  [renamed `dev.langchain4j.cdi.mcp` -> `dev.langchain4j.cdi` (latest 1.5.1)]
  ? * dev.langchain4j.cdi.mcp              2026-10..2026-10 1.5.0                |...................=|
  ?   dev.langchain4j.cdi                  2026-10..2026-10 1.5.1                |...................=|
dev.langchain4j.cdi.mcp.portable  [renamed `dev.langchain4j.cdi.mcp` -> `dev.langchain4j.cdi` (latest 1.5.1)]
  ? * dev.langchain4j.cdi.mcp              2026-10..2026-10 1.5.0                |...................=|
  ?   dev.langchain4j.cdi                  2026-10..2026-10 1.5.1                |...................=|
org.eclipse.yasson  [renamed `org.eclipse` -> `org.eclipse.yasson` (latest 3.1.0-M2)]
  A * org.eclipse                          2017-06..2024-07 3.0.4                |.==============.....|
  ?   org.eclipse.yasson                   2026-07..2026-10 3.1.0-M2             |..................==|
  R   com.charlyghislain.keycloak          2022-08..2022-08 19.0.1               |...........=........|
com.querydsl.sql.json  [renamed `io.github.openfeign.querydsl` -> `io.github.weir2010` (latest 8.0)]
  ? * io.github.openfeign.querydsl         2024-08..2026-09 7.7                  |...............=====|
  ?   io.github.weir2010                   2026-10..2026-10 8.0                  |...................=|
com.aliyun.polardb2.jdbc  [renamed `com.aliyun.polardb2` -> `com.aliyun` (latest 42.5.7.0.16.2)]
  ? * com.aliyun.polardb2                  2026-07..2026-07 42.5.7.0.15          |..................=.|
  ?   com.aliyun                           2026-07..2026-10 42.5.7.0.16.2        |..................==|
io.github.humbleui.skija.macos.x64  [renamed `io.github.humbleui.skija` -> `io.github.humbleui` (latest 0.143.18)]
  A * io.github.humbleui.skija             2021-11..2021-11 0.96.0               |.........=..........|
  A   io.github.humbleui                   2021-12..2026-10 0.143.18             |.........===========|
  ?   com.behemiron.engine                 2026-06..2026-06 0.143.17             |..................=.|
io.github.humbleui.skija.macos.arm64  [renamed `io.github.humbleui.skija` -> `io.github.humbleui` (latest 0.143.18)]
  A * io.github.humbleui.skija             2021-11..2021-11 0.96.0               |.........=..........|
  A   io.github.humbleui                   2021-12..2026-10 0.143.18             |.........===========|
  ?   com.behemiron.engine                 2026-06..2026-06 0.143.17             |..................=.|
io.github.humbleui.skija.shared  [renamed `io.github.humbleui.skija` -> `io.github.humbleui` (latest 0.143.18)]
  A * io.github.humbleui.skija             2021-11..2021-11 0.96.0               |.........=..........|
  A   io.github.humbleui                   2021-12..2026-10 0.143.18             |.........===========|
  ?   com.behemiron.engine                 2026-06..2026-06 0.143.17             |..................=.|
org.opendaylight.yangtools.rfc6536.model.api  [renamed `org.opendaylight.yangtools` -> `org.opendaylight.netconf` (latest 12.0.2)]
  ? * org.opendaylight.yangtools           2019-04..2026-09 16.1.0               |....================|
  ?   org.opendaylight.netconf             2026-10..2026-10 12.0.2               |...................=|
dev.mohterbaord.fp4j.apf  [renamed `dev.mohterbaord` -> `dev.mohterbaord.fp4j` (latest 0.6.0)]
  ? * dev.mohterbaord                      2026-05..2026-05 0.5.0                |..................=.|
  ?   dev.mohterbaord.fp4j                 2026-08..2026-08 0.6.0                |..................=.|
dev.mohterbaord.fp4j.core  [renamed `dev.mohterbaord` -> `dev.mohterbaord.fp4j` (latest 0.6.0)]
  ? * dev.mohterbaord                      2026-05..2026-05 0.5.0                |..................=.|
  ?   dev.mohterbaord.fp4j                 2026-08..2026-08 0.6.0                |..................=.|
dev.mohterbaord.fp4j.scope  [renamed `dev.mohterbaord` -> `dev.mohterbaord.fp4j` (latest 0.6.0)]
  ? * dev.mohterbaord                      2026-05..2026-05 0.5.0                |..................=.|
  ?   dev.mohterbaord.fp4j                 2026-08..2026-08 0.6.0                |..................=.|
dev.mohterbaord.fp4j.util  [renamed `dev.mohterbaord` -> `dev.mohterbaord.fp4j` (latest 0.6.0)]
  ? * dev.mohterbaord                      2026-05..2026-05 0.5.0                |..................=.|
  ?   dev.mohterbaord.fp4j                 2026-08..2026-08 0.6.0                |..................=.|
mutable.alignment  [renamed `io.github.beast2-dev` -> `io.github.compevol` (latest 0.1.1)]
  ? * io.github.beast2-dev                 2026-08..2026-08 0.1.0                |..................=.|
  ?   io.github.compevol                   2026-08..2026-08 0.1.1                |..................=.|
bento.fx  [renamed `software.coley` -> `software.coley.bento-fx` (latest 0.16.0)]
  ? * software.coley                       2025-04..2026-01 0.15.1               |................==..|
  ?   software.coley.bento-fx              2026-07..2026-07 0.16.0               |..................=.|
com.peruncs.odbjca.api  [renamed `com.peruncs.odbjca` -> `com.peruncs` (latest 0.0.2)]
  ? * com.peruncs.odbjca                   2018-11..2018-11 0.0.1                |....=...............|
  ?   com.peruncs                          2018-11..2018-11 0.0.2                |....=...............|
```

## fork (287)

A cross-org coordinate publishes the same name while the original owner is still active.

- A more-recent cross-org coordinate (a successor) publishes the same name while the original is still active.
- The earliest publisher is itself a credible owner: it owns the module namespace, or is the closest groupId to it.
- Keep the original owner; reject the fork.

_Showing the 200 most recently active of 287. For the full list, emit the SetOwners file: `-Djenesis.crawler.drift.emit=fork`._

```
org.mockbukkit.mockbukkit  [fork: keep `org.mockbukkit.mockbukkit`, `com.620cloud.server` still publishes the name]
  A * org.mockbukkit.mockbukkit            2024-11..2026-11 4.120.0              |...............=====|
  ?   com.620cloud.server                  2026-08..2026-11 26.3-52              |..................==|
  R   com.mineplex.studio                  2024-11..2026-02 1.21.11-R0.1-43      |...............====.|
ch.qos.logback.classic  [fork: keep `ch.qos.logback`, `com.daml` still publishes the name]
  A * ch.qos.logback                       2018-01..2026-11 1.6.7                |..==================|
  R   com.daml                             2022-10..2026-11 3.8.0-snapshot.20261116.14928.0.vaf67d256 |...........=========|
  R   com.deltaproto                       2026-03..2026-11 1.8.5                |..................==|
  ?   org.openldes.ldio                    2026-11..2026-11 3.2.2                |...................=|
  ?   org.jboss.pnc.npm-manipulator        2026-10..2026-10 1.3.5                |...................=|
  R   de.gematik.test                      2025-04..2026-10 4.5.0                |................====|
    + 96 more: org.chenile, org.springframework.cloud, org.apache.zookeeper, io.smallrye.reactive, io.camunda, org.alfasoftware, ch.exense.step, org.commonjava.atlas, org.apache.iotdb, com.salesforce.cantor, org.eclipse.kapua, net.anotheria, (+84 more)
org.jetbrains.annotations  [fork: keep `org.jetbrains`, `com.kinetica` still publishes the name]
  A * org.jetbrains                        2018-09..2026-02 26.1.0               |...================.|
  R   com.kinetica                         2025-09..2026-11 7.2.3.32             |.................===|
  R   com.microsoft.azure.kusto            2025-10..2026-11 7.1.5                |.................===|
  R   com.qcloud.cos                       2024-03..2026-10 1.6.1-xima.2         |..............======|
  R   systems.manifold                     2023-08..2026-10 2026.1.16            |.............=======|
  R   io.deephaven                         2025-09..2026-09 41.10                |.................===|
    + 78 more: net.finmath, io.split, me.bechberger, de.fraunhofer.iosb.ilt.FROST-Server, io.github.4rg0n, de.tubyoub, beer.devs, io.github.alepandocr, io.github.nbauma109, io.github.happybavarian07, org.jam4s, io.streamthoughts, (+66 more)
com.fasterxml.jackson.annotation  [fork: keep `com.fasterxml.jackson.core`, `com.kinetica` still publishes the name]
  A * com.fasterxml.jackson.core           2017-09..2026-11 2.23                 |.===================|
  R   com.kinetica                         2024-08..2026-11 7.2.3.32             |...............=====|
  R   com.yahoo.vespa                      2022-06..2026-11 8.788.13             |..........==========|
  ?   org.modeljars                        2026-08..2026-11 0.1.80               |...................=|
  R   io.github.techelliiptica             2026-02..2026-11 4.0.25               |.................===|
  R   io.antmedia.plugin                   2024-04..2026-11 3.2.0                |..............======|
    + 476 more: io.github.damienmiheev, org.mongodb.kafka, com.chartiq.finsemble, io.github.cdy-codey, org.jfrog.buildinfo, com.heroku, io.debezium, software.xdev.mockserver, io.synclite, io.github.vishalmysore, org.openldes.ldio, com.gu.play-secret-rotation, (+464 more)
codes.rafael.asmjdkbridge  [fork: keep `codes.rafael.asmjdkbridge`, `co.hyperprobe` still publishes the name]
  ? * codes.rafael.asmjdkbridge            2025-01..2026-10 0.0.14               |...............=====|
  ?   co.hyperprobe                        2026-07..2026-11 1.2.32-8             |..................==|
io.netty.codec.http2  [fork: keep `io.netty`, `com.applitools` still publishes the name]
  A * io.netty                             2017-12..2026-11 4.1.140.Final        |..==================|
  R   com.applitools                       2026-05..2026-11 5.93.0               |..................==|
  ?   org.openjproxy                       2026-08..2026-11 1.0.7                |..................==|
  R   io.etcd                              2025-11..2026-11 0.8.8                |.................===|
  ?   com.spiraldb                         2026-11..2026-11 1.13.0-spiral.1      |...................=|
  ?   io.camunda.connector                 2026-06..2026-11 8.6.36               |..................==|
    + 15 more: org.opendaylight.gnmi, org.apache.spark, com.datastrato, org.apache.iceberg, org.apache.gravitino, org.eclipse.ditto, io.github.jdbc-armour, io.github.cstopyak, it.neckar.open, net.xdob.ratly, io.micronaut.testresources, com.exactpro.th2, (+3 more)
io.opentelemetry.javaagent  [fork: keep `io.opentelemetry.javaagent`, `com.vaadin` still publishes the name]
  A * io.opentelemetry.javaagent           2023-10..2026-10 2.33.1               |.............=======|
  R   com.vaadin                           2024-12..2026-11 4.1.3                |...............=====|
  ?   ai.tasktrooper                       2026-11..2026-11 0.1.0                |...................=|
  R   dev.agenttel                         2026-03..2026-10 0.4.0-alpha          |..................==|
org.eclipse.jetty.http2.hpack  [fork: keep `org.eclipse.jetty.http2`, `com.yahoo.vespa` still publishes the name]
  ? * org.eclipse.jetty.http2              2018-11..2026-11 12.0.41              |...=================|
  ?   com.yahoo.vespa                      2026-10..2026-11 8.788.13             |...................=|
info.picocli  [fork: keep `info.picocli`, `com.github.toolarium` still publishes the name]
  A * info.picocli                         2017-10..2025-04 4.7.7                |.================...|
  R   com.github.toolarium                 2022-12..2026-11 0.9.0                |...........=========|
  ?   com.cedzlabs.java.ctx                2026-11..2026-11 1.3.8                |...................=|
  ?   com.cedzlabs                         2026-11..2026-11 1.3.4                |...................=|
  R   org.primefaces                       2021-09..2026-11 15.0.20              |.........===========|
  ?   com.julien-dubois.bootui             2026-09..2026-11 2.4.0                |...................=|
    + 171 more: io.github.a-n-o-d-e-r, com.helger, org.keycloak, com.bioinceptionlabs, io.github.pgdro, gg.jte, run.endive, io.xpipe, org.apache.tika, io.spicelabs, com.instaclustr, io.github.ruixuqi, (+159 more)
com.fasterxml.jackson.databind  [fork: keep `com.fasterxml.jackson.core`, `com.debugbundle` still publishes the name]
  A * com.fasterxml.jackson.core           2017-09..2026-11 2.23.0               |.===================|
  ?   com.debugbundle                      2026-07..2026-11 3.1.4                |..................==|
  ?   io.youtrackdb                        2026-10..2026-11 0.5.0-beta-2026-11-14 |...................=|
  ?   com.zonzonzon                        2026-10..2026-11 1.2.5                |...................=|
  ?   org.finos.legend.engine              2026-10..2026-11 5.3.0                |...................=|
  R   org.thepalaceproject.webpub          2026-05..2026-11 1.4.0                |..................==|
    + 487 more: org.devlive.metaforge, io.teknek.reckoning, org.apache.seatunnel, io.github.javatechgroup, io.github.1030907690, dev.zarr, com.linkedin.iceberg, com.amazonaws, io.strimzi, io.github.srijithunni7182, io.github.yuku123, org.jboss.galleon, (+475 more)
net.bytebuddy  [fork: keep `net.bytebuddy`, `org.lucee` still publishes the name]
  A * net.bytebuddy                        2017-05..2026-11 1.18.17              |.===================|
  R   org.lucee                            2026-04..2026-11 5.6.15.22-RC         |..................==|
  R   com.graphql-java                     2024-03..2026-11 24.5                 |..............======|
  R   de.gematik.test                      2024-08..2026-10 4.5.0                |...............=====|
  ?   io.github.chaoking320                2026-10..2026-10 0.0.7                |...................=|
  ?   cn.itcraft                           2026-10..2026-10 1.1.0                |...................=|
    + 129 more: io.github.martinhickson, io.github.lucientong, pub.ihub.integration, com.mysticalrzc, io.github.praveenkpandu, com.logitags, com.jcabi, dev.jorel, io.github.rocketbunny727, io.github.smallfast, net.aivory, ai.superstream, (+117 more)
com.fasterxml.jackson.module.jaxb  [fork: keep `com.fasterxml.jackson.module`, `com.rovio.ingest` still publishes the name]
  A * com.fasterxml.jackson.module         2017-10..2026-11 2.23.0               |.===================|
  R   com.rovio.ingest                     2024-10..2026-11 1.1.0_spark_3.5.8    |...............=====|
  R   com.seeq                             2022-07..2026-11 65.2.6-v202611052228 |...........=========|
  ?   com.liquibase                        2026-06..2026-11 6.0.1                |..................==|
  ?   org.apache.gravitino                 2026-06..2026-09 1.3.1                |..................==|
  R   org.apache.seatunnel                 2022-09..2026-09 3.0.0                |...........=========|
    + 92 more: org.apache.dolphinscheduler, com.solacecoe.connectors, com.oceanbase, io.github.solven-eu.cleanthat, com.datastax.oss, io.cdap.cdap, com.facebook.presto.spark, org.apache.pulsar, com.ascentstream.pulsar, io.github.dodogeny, io.streamnative.connectors, org.apache.phoenix, (+80 more)
org.json  [fork: keep `org.json`, `org.test-charm` still publishes the name]
  A * org.json                             2018-08..2026-08 20260814             |...================.|
  R   org.test-charm                       2026-04..2026-11 1.0.0-alpha.31       |..................==|
  ?   org.wso2.orbit.com.hazelcast         2026-11..2026-11 5.7.0.wso2v2         |...................=|
  R   com.guicedee.modules.services        2026-04..2026-11 2.5.1                |..................==|
  R   io.swagger.codegen.v3                2025-09..2026-11 3.0.85               |.................===|
  ?   org.openidentityplatform.openam      2026-10..2026-10 16.1.4               |...................=|
    + 34 more: io.github.belmomusta, com.theoryinpractise, com.github.karsaig, org.labkey.api, io.github.dasilvafg, io.firebolt, io.github.bareboneslib, org.primefaces, io.github.lemonjuice95, fr.milekat, io.github.funcbox-i3, io.github.xtemplus, (+22 more)
io.netty.internal.tcnative  [fork: keep `io.netty`, `org.finos.legend.engine` still publishes the name]
  A * io.netty                             2021-10..2026-11 2.0.85.Final         |.........===========|
  R   org.finos.legend.engine              2026-01..2026-11 5.3.1                |.................===|
  ?   io.pinecone                          2026-11..2026-11 6.3.1                |...................=|
  ?   io.github.fahara02                   2026-10..2026-11 0.6.17               |...................=|
  ?   com.salesforce.multicloudj           2026-11..2026-11 0.4.16               |...................=|
  ?   com.google.cloud.spark.spanner       2026-09..2026-11 1.5.1                |...................=|
    + 91 more: com.google.cloud.bigdataoss, com.google.api-ads, com.google.appengine, com.spotify.confidence, com.microsoft.azure.kusto, net.hironico, com.google.api, com.spiraldb, com.google.cloud, io.camunda.connector, com.arcadedb, com.liquibase.ext, (+79 more)
org.apache.commons.lang3  [fork: keep `org.apache.commons`, `org.polypheny` still publishes the name]
  A * org.apache.commons                   2017-06..2026-11 3.22.0               |.===================|
  R   org.polypheny                        2024-05..2026-11 3.1                  |..............======|
  R   com.sngular                          2024-11..2026-11 8.4.1                |...............=====|
  R   org.apache.seatunnel                 2025-10..2026-11 3.18.0-3.0.1         |.................===|
  ?   com.aliyun.odps                      2026-11..2026-11 1.0.0-1.16           |...................=|
  ?   io.github.luo-zhan                   2026-06..2026-11 2.4.0                |..................==|
    + 110 more: io.streamnative, com.jcabi, org.apache.tomee, com.equinor.neqsim, com.liquibase.ext, io.vertx, com.ubs-hainer, io.openlineage, de.knightsoft-net, edu.kit.kastel.sdq, org.frankframework, com.power4j.fist3, (+98 more)
org.slf4j  [fork: keep `org.slf4j`, `com.ionutbanu` still publishes the name]
  A * org.slf4j                            2017-04..2026-09 2.0.20               |====================|
  R   com.ionutbanu                        2026-05..2026-11 0.3.0                |..................==|
  ?   io.github.davidarthurcole.daetekt    2026-11..2026-11 2.0.0-dae.1          |...................=|
  ?   io.modelcontextprotocol.sdk          2026-11..2026-11 2.2.0                |...................=|
  R   io.continual                         2025-03..2026-11 0.3.46               |................====|
  R   com.jcabi                            2020-11..2026-11 0.37.3               |.......=============|
    + 339 more: org.talend.sdk.component.sample.feature, com.google.appengine, org.wildfly.core, org.eclipse.jetty.ee9, org.eclipse.jetty.ee8, org.eclipse.jetty.ee10, ml.combust.mleap, org.apache.storm, io.github.yuku123, com.launchdarkly, com.liquibase, com.github.joutvhu, (+327 more)
org.graalvm.js  [fork: keep `org.graalvm.js`, `io.youtrackdb` still publishes the name]
  A * org.graalvm.js                       2018-10..2026-11 25.5.5.0.3           |...=================|
  ?   io.youtrackdb                        2026-10..2026-11 0.5.0-beta-2026-11-14 |...................=|
  R   org.hl7.fhir.publisher               2025-02..2026-11 3.0.4                |................====|
  R   de.caluga                            2026-01..2026-11 6.3.16               |.................===|
  R   io.vertx                             2026-05..2026-11 5.2.3                |..................==|
  R   ca.ibodrov.concord                   2023-10..2026-10 2.0.6                |.............=======|
    + 15 more: lat.sofis.biobpm, com.walmartlabs.concord.runtime.v2, com.walmartlabs.concord, com.walmartlabs.concord.k8s, com.walmartlabs.concord.runtime.v1, org.cibseven.bpm, dev.ybrig.ck8s.cli, org.noear, io.hyperfoil.tools, com.soartech, io.github.neofreko, org.graphwalker, (+3 more)
org.jspecify  [fork: keep `org.jspecify`, `com.zonzonzon` still publishes the name]
  A * org.jspecify                         2021-07..2026-07 1.0.1                |.........==========.|
  ?   com.zonzonzon                        2026-10..2026-11 1.1.12               |...................=|
  ?   com.snowflake                        2026-11..2026-11 4.3.1                |...................=|
  R   org.jboss.elemento                   2025-10..2026-11 2.5.14               |.................===|
  R   org.treblereel.gwt.yaml.mapper       2025-10..2026-11 0.11                 |.................===|
  R   org.treblereel.gwt.xml.mapper        2025-10..2026-11 0.12                 |.................===|
    + 69 more: eu.rekawek.coffeegb, org.treblereel.j2cl.processors, io.camunda, com.atomgraph.etl.csv, fr.inria.gforge.spoon, org.apache.storm, io.github.iamsanjaymalakar, org.springframework.cloud, dev.zacsweers.metro, net.flectone, org.finos.fluxnova.bpm.qa, org.finos.fluxnova.bpm, (+57 more)
tools.jackson.core  [fork: keep `tools.jackson.core`, `com.zonzonzon` still publishes the name]
  A * tools.jackson.core                   2025-03..2026-11 3.3.0                |................====|
  ?   com.zonzonzon                        2026-09..2026-11 1.1.9                |...................=|
  ?   org.apache.cayenne                   2026-06..2026-10 5.0-RC1              |..................==|
  ?   fish.payara.tools                    2026-06..2026-10 1.0.0-Alpha5         |..................==|
  R   com.erudika                          2025-11..2026-10 1.43.2               |.................===|
  ?   io.github.dfa1.typesafe-java         2026-09..2026-09 0.6.0                |...................=|
    + 25 more: com.phonepe.sentinel-ai, org.opentripplanner, org.meeuw, org.pragmatica-lite.aether, org.pragmatica-lite, ru.tinkoff.piapi, software.xdev.mockserver, com.ibm.jsonata4java, com.playtika.reactivefeign, io.github.ignf, media.barney, org.sonarsource.sonarlint.ls, (+13 more)
com.fasterxml.jackson.dataformat.yaml  [fork: keep `com.fasterxml.jackson.dataformat`, `dev.skyramp` still publishes the name]
  A * com.fasterxml.jackson.dataformat     2017-10..2026-11 2.23.0               |.===================|
  R   dev.skyramp                          2025-02..2026-11 1.3.56               |................====|
  ?   io.github.hasanyelaldi               2026-11..2026-11 0.2.0                |...................=|
  ?   ai.chalk                             2026-08..2026-11 1.3.10               |...................=|
  ?   org.geneontology.obographs           2026-11..2026-11 0.4.0                |...................=|
  R   io.openlineage                       2022-01..2026-10 1.55.0               |..........==========|
    + 69 more: io.simpleishard, org.wildfly.prospero, org.incenp, io.fabrikt, org.apache.plc4x.extras, org.apiaddicts.apitools.dosonarapi, org.apache.dolphinscheduler, io.fabric8, dev.itara, net.corda, org.apache.flink, com.helpchoice, (+57 more)
org.apache.logging.log4j.core  [fork: keep `org.apache.logging.log4j`, `io.github.jseqa` still publishes the name]
  A * org.apache.logging.log4j             2017-11..2026-07 2.25.5               |..=================.|
  ?   io.github.jseqa                      2026-11..2026-11 0.1.0                |...................=|
  R   io.openems                           2026-02..2026-10 3.4.0-openems.2      |..................==|
  R   app.freerouting                      2026-05..2026-10 2.5.0                |..................==|
  R   com.ghgande                          2025-12..2026-09 3.4.0                |.................===|
  ?   dk.dma.ais.lib                       2026-07..2026-07 2.8.7                |..................=.|
    + 21 more: org.lucee, nl.tno.org.portico, org.beilstein, me.bechberger, com.kount, com.github.aquality-automation, com.github.bilderherunterlader, com.gemecosystem.gemjar, io.github.alien-tools, com.webforj, io.github.egonw, com.liferay, (+9 more)
org.codehaus.janino  [fork: keep `org.codehaus.janino`, `io.github.janino-lts` still publishes the name]
  ? * org.codehaus.janino                  2023-07..2024-02 3.1.12               |............===.....|
  ?   io.github.janino-lts                 2026-10..2026-11 3.1.21               |...................=|
  ?   io.github.digitalgust                2026-10..2026-11 4.0.1                |...................=|
org.codehaus.commons.compiler  [fork: keep `org.codehaus.janino`, `io.github.janino-lts` still publishes the name]
  ? * org.codehaus.janino                  2023-07..2024-02 3.1.12               |............===.....|
  ?   io.github.janino-lts                 2026-10..2026-11 3.1.21               |...................=|
  ?   io.github.digitalgust                2026-10..2026-11 4.0.1                |...................=|
org.codehaus.commons.compiler.jdk  [fork: keep `org.codehaus.janino`, `io.github.janino-lts` still publishes the name]
  ? * org.codehaus.janino                  2023-07..2024-02 3.1.12               |............===.....|
  ?   io.github.janino-lts                 2026-10..2026-11 3.1.21               |...................=|
  ?   io.github.digitalgust                2026-10..2026-11 4.0.1                |...................=|
nl.basjes.parse.useragent  [fork: keep `nl.basjes.parse.useragent`, `io.github.deepcitizen` still publishes the name]
  ? * nl.basjes.parse.useragent            2021-10..2026-10 8.3.0                |.........===========|
  ?   io.github.deepcitizen                2026-11..2026-11 8.5.0                |...................=|
io.github.classgraph  [fork: keep `io.github.classgraph`, `org.javastro.vodsl` still publishes the name]
  A * io.github.classgraph                 2018-08..2026-11 6.0.2                |...=================|
  R   org.javastro.vodsl                   2022-02..2026-11 0.4.11               |..........==========|
  R   org.finos.legend.engine              2025-10..2026-11 4.152.3              |.................===|
  ?   eu.europa.ted.eforms                 2026-09..2026-09 0.13.0               |...................=|
  R   software.amazon.glue                 2021-06..2026-09 2.0.0                |.........===========|
  R   org.dominokit                        2024-01..2026-07 1.0.3                |.............======.|
    + 78 more: com.datarobot, dev.sixpack, tech.neander, org.finos.legend.sdlc, org.paramixel, dev.getelements.elements.crossfire, com.netgrif, org.plumelib, com.google.tsunami, org.finos.legend.depot, io.github.api-ghost-agent, org.geneweaver, (+66 more)
org.bouncycastle.util  [fork: keep `org.bouncycastle`, `net.maritimeconnectivity.pki` still publishes the name]
  A * org.bouncycastle                     2021-06..2026-11 1.87                 |........============|
  R   net.maritimeconnectivity.pki         2024-12..2026-11 1.4.5                |...............=====|
  ?   org.apache.pinot                     2026-10..2026-10 1.6.0                |...................=|
  R   org.hyperledger.fabric               2026-05..2026-05 1.11.0               |..................=.|
  R   com.liferay                          2021-10..2023-04 6.1.1                |.........====.......|
  R   com.luhuiguo.bouncycastle            2022-01..2022-02 1.70.GM              |..........=.........|
com.samskivert.jmustache  [fork: keep `com.samskivert`, `dev.getelements.elements` still publishes the name]
  A * com.samskivert                       2019-07..2023-11 1.16                 |.....=========......|
  ?   dev.getelements.elements             2026-08..2026-11 3.8.17-rc-3          |..................==|
  R   io.swagger.codegen.v3                2022-02..2023-02 3.0.41               |..........===.......|
  R   io.garam                             2020-11..2020-11 0.4                  |.......=............|
com.github.benmanes.caffeine  [fork: keep `com.github.ben-manes.caffeine`, `io.pebbletemplates` still publishes the name]
  A * com.github.ben-manes.caffeine        2017-12..2026-10 3.3.1                |..==================|
  R   io.pebbletemplates                   2025-12..2026-11 4.1.3                |.................===|
  R   org.openprovenance.prov              2024-10..2026-11 2.5.0                |...............=====|
  ?   org.openldes.ldio                    2026-11..2026-11 3.2.2                |...................=|
  R   nl.basjes.parse.useragent            2022-01..2026-10 8.3.0                |..........==========|
  R   net.wirelabs                         2026-02..2026-08 1.4.6                |..................==|
    + 17 more: com.janeluo, io.zeebe.redis, org.kill-bill.billing, org.apache.tinkerpop, com.google.errorprone, org.odftoolkit, io.tileverse.pmtiles, org.opengis.cite, nl.goodbytes.xmpp.xep, org.igniterealtime.whack, be.vlaanderen.informatievlaanderen.ldes.ldio, com.aerospike, (+5 more)
io.netty.handler.proxy  [fork: keep `io.netty`, `io.neonbee` still publishes the name]
  A * io.netty                             2017-12..2026-11 4.1.140.Final        |..==================|
  ?   io.neonbee                           2026-06..2026-11 0.37.39              |..................==|
  R   io.kestra                            2025-08..2026-11 1.3.47               |.................===|
  ?   com.spiraldb                         2026-11..2026-11 1.13.0-spiral.1      |...................=|
  R   com.facebook.presto                  2026-04..2026-11 0.300                |..................==|
  ?   com.datastrato                       2026-09..2026-10 1.13.0               |...................=|
    + 9 more: org.apache.grails, org.apache.iceberg, org.apache.gravitino, io.micronaut.starter, io.github.norby99, io.sirix, io.kestra.plugin, com.frog-development.consul-populate, io.kestra.storage
org.yaml.snakeyaml  [fork: keep `org.yaml`, `com.huaweicloud.sdk` still publishes the name]
  A * org.yaml                             2019-02..2026-08 2.7                  |....================|
  R   com.huaweicloud.sdk                  2024-01..2026-11 3.1.224              |..............======|
  ?   io.github.psm8                       2026-09..2026-11 0.5.0                |...................=|
  R   com.nvidia                           2023-04..2026-11 26.10.2              |............========|
  R   com.arcmutate                        2024-01..2026-11 1.7.3                |.............=======|
  R   org.conductoross                     2026-03..2026-11 3.34.0-rc2           |..................==|
    + 87 more: com.aerospike, io.vertx, com.vunetsystems.tools.android, com.google.errorprone, com.sparkutils, io.github.thedistortedwajdan.jarbase, org.htcom, dev.feit, io.swagger, com.deftdevs, io.github.spah1879, io.github.autor3search, (+75 more)
com.azure.http.netty  [fork: keep `com.azure`, `io.lakefs` still publishes the name]
  ? * com.azure                            2019-11..2026-10 1.17.0               |.....===============|
  ?   io.lakefs                            2026-06..2026-11 0.26.0               |..................==|
com.fasterxml.jackson.core  [fork: keep `com.fasterxml.jackson.core`, `org.wso2.am.microgw` still publishes the name]
  A * com.fasterxml.jackson.core           2017-09..2026-11 2.23.0               |.===================|
  R   org.wso2.am.microgw                  2022-08..2026-11 3.2.11               |...........=========|
  ?   com.ratepay.sdk                      2026-10..2026-11 1.17.0               |...................=|
  R   com.alibaba.hologres                 2025-10..2026-11 2.8.1                |.................===|
  R   io.deephaven                         2026-01..2026-11 42.9                 |.................===|
  R   com.clickhouse.spark                 2026-01..2026-11 0.11.0               |.................===|
    + 416 more: org.pipelineframework, io.krabka, org.talend.sdk.component, software.amazon.msk, com.algolia, org.apache.seatunnel, org.operaton.spin, io.github.martinhickson, com.scylladb, org.apache.parquet, com.facebook.presto, com.liquibase.ext, (+404 more)
org.openapitools.jackson.nullable  [fork: keep `org.openapitools`, `io.airlift` still publishes the name]
  A * org.openapitools                     2023-02..2026-10 0.2.13               |............========|
  R   io.airlift                           2026-04..2026-11 459                  |..................==|
  ?   io.github.giis-uniovi                2026-07..2026-10 2.2.2                |..................==|
org.tukaani.xz  [fork: keep `org.tukaani`, `app.keve.jsubleqvm` still publishes the name]
  A * org.tukaani                          2018-01..2026-03 1.12                 |..=================.|
  ?   app.keve.jsubleqvm                   2026-11..2026-11 0.1.0                |...................=|
  R   org.sonarsource.javascript           2023-09..2026-11 14.4.0.46458         |.............=======|
  R   org.apache.syncope.fit               2025-11..2026-10 4.0.9                |.................===|
  ?   com.timecho.timechodb                2026-06..2026-10 2.0.12.1             |..................==|
  R   org.apache.iotdb                     2023-09..2026-10 2.0.11               |.............=======|
    + 26 more: org.incenp, de.m3y.parquet, io.github.treebolic, net.algart, io.anserini, io.github.peterdowdy, com.timecho.iotdb, com.sonatype.clm, net.neoforged.installertools, io.archivesunleashed, org.apache.parquet, io.github.seabow, (+14 more)
com.nimbusds.jose.jwt  [fork: keep `com.nimbusds`, `ai.starlake.qod` still publishes the name]
  A * com.nimbusds                         2020-08..2026-10 5.1                  |.......=============|
  ?   ai.starlake.qod                      2026-11..2026-11 19.0.0               |...................=|
  R   com.vaadin                           2025-07..2026-10 2.13.9               |................====|
  ?   org.ligoj.plugin                     2026-07..2026-07 2.0.0                |..................=.|
  R   fish.payara.security.connectors      2024-05..2026-04 2.9.0                |..............=====.|
  R   org.bonitasoft.connectors            2026-04..2026-04 1.0.0-beta.1         |..................=.|
    + 7 more: org.apache.hadoop, com.waveinformatica.skysso, io.github.swiyu-admin-ch, io.okdp, org.project-kessel, com.liferay, com.thetransactioncompany
org.jsoup  [fork: keep `org.jsoup`, `io.github.shafthq` still publishes the name]
  A * org.jsoup                            2018-04..2026-11 1.24.1               |..==================|
  ?   io.github.shafthq                    2026-08..2026-11 10.4.20261112        |...................=|
  R   io.github.algomaster99               2024-03..2026-11 0.17.1               |..............======|
  R   io.yupiik.maven                      2024-11..2026-11 1.3.1                |...............=====|
  R   com.github.tsantalis                 2025-06..2026-11 3.2.0                |................====|
  R   io.github.cmu-phil                   2025-10..2026-11 8.0.0                |.................===|
    + 50 more: com.sonatype.clm, org.jetbrains.dokka, org.scala-sbt, org.graylog2, software.amazon.jdbc, org.jboss.pnc.ant-manipulator, org.finos.legend.sdlc, org.jboss.pnc.bacon, net.serenity-bdd, com.testzombie, io.github.srijithunni7182, one.nextera.openat.javelle, (+38 more)
org.apache.commons.compress  [fork: keep `org.apache.commons`, `com.mobidevelop.robovm` still publishes the name]
  A * org.apache.commons                   2017-10..2025-07 1.28.0               |.================...|
  R   com.mobidevelop.robovm               2024-02..2026-11 3.0.1                |..............======|
  R   org.apache.parquet                   2024-11..2026-11 1.18.2               |...............=====|
  R   org.apache.pinot                     2024-08..2026-10 1.6.0                |...............=====|
  ?   io.spicelabs                         2026-10..2026-10 1.9.12               |...................=|
  R   org.onebusaway                       2026-01..2026-10 2.7.2                |.................===|
    + 40 more: com.alibaba.ververica, org.apache.beam, org.apache.druid.extensions, org.apache.grails, com.alibaba.hologres, com.io7m.montarre, io.github.trethore, com.google.cloud.flink, com.github.broadinstitute, org.eclipse.tahu, com.codenameone, ink.icoding.codex, (+28 more)
org.apache.maven.resolver  [fork: keep `org.apache.maven.resolver`, `com.jcabi` still publishes the name]
  ? * org.apache.maven.resolver            2017-06..2026-11 2.0.25               |.===================|
  ?   com.jcabi                            2026-11..2026-11 0.17.2               |...................=|
org.apache.commons.exec  [fork: keep `org.apache.commons`, `cn.net.pap.md5.jmh` still publishes the name]
  ? * org.apache.commons                   2024-01..2025-11 1.6.0                |.............=====..|
  ?   cn.net.pap.md5.jmh                   2026-07..2026-11 0.0.5                |..................==|
  ?   com.github.zhkl0228                  2026-03..2026-03 2.0.2                |..................=.|
jakarta.inject  [fork: keep `jakarta.inject`, `com.google.gerrit` still publishes the name]
  A * jakarta.inject                       2020-04..2021-10 2.0.1                |......====..........|
  R   com.google.gerrit                    2023-10..2026-11 3.13.11              |.............=======|
  ?   org.openidentityplatform.openig      2026-06..2026-10 6.2.0                |..................==|
  R   io.joynr.java.core                   2026-01..2026-09 1.24.10              |.................===|
  R   io.github.jolt-community.jolt        2025-09..2026-08 1.3.0                |.................==.|
  ?   org.kill-bill.billing                2026-06..2026-07 0.42.2               |..................=.|
    + 13 more: dev.getelements.elements, it.netgrid, network.sloud.hytale, com.uchicom, com.google.tsunami, io.github.avistotelecom, org.apache.opennlp, com.google.template, io.github.openfeign.querydsl, org.reploop, com.guicedee.services, io.github.jbock-java, (+1 more)
net.bytebuddy.agent  [fork: keep `net.bytebuddy`, `com.google.gerrit` still publishes the name]
  A * net.bytebuddy                        2017-05..2026-11 1.18.17              |.===================|
  R   com.google.gerrit                    2020-02..2026-11 3.13.11              |......==============|
  R   co.elastic.apm                       2019-02..2026-10 1.57.1               |....================|
  ?   co.hyperprobe                        2026-07..2026-07 1.2.19               |..................=.|
  R   com.macstab.chaos.jvm                2026-04..2026-04 1.0.0                |..................=.|
  R   cn.easii                             2026-04..2026-04 1.0.6                |..................=.|
    + 15 more: me.bechberger, cn.langpy, com.leanxcale, com.zto.fire, software.amazon.disco, com.nerdvision, com.idea-aedi, org.openstreetmap.atlas, com.netsensia.rivalchess, com.github.liuzhengyang, com.securenative.java, com.amazonaws, (+3 more)
org.apache.commons.jexl3  [fork: keep `org.apache.commons`, `org.hotrodorm.hotrod` still publishes the name]
  ? * org.apache.commons                   2024-06..2026-10 3.7.1                |..............======|
  ?   org.hotrodorm.hotrod                 2025-07..2026-11 5.1.28               |................====|
io.netty.buffer  [fork: keep `io.netty`, `com.buralotech.oss.identifier` still publishes the name]
  A * io.netty                             2017-12..2026-11 4.1.140.Final        |..==================|
  ?   com.buralotech.oss.identifier        2026-10..2026-11 3.2.1                |...................=|
  ?   cn.itsub                             2026-11..2026-11 2.0.3                |...................=|
  ?   io.krabka                            2026-08..2026-11 1.4.3                |..................==|
  R   org.apache.polaris                   2025-07..2026-11 1.9.0                |................====|
  ?   com.spiraldb                         2026-11..2026-11 1.13.0-spiral.1      |...................=|
    + 27 more: com.github.marks-yag, com.urbanairship, net.neoforged.jst, org.opendaylight.jsonrpc, org.apache.tika, com.datastrato, org.opendaylight.controller, org.apache.iceberg, com.exness, top.shouldbe, org.opendaylight.bgpcep, org.opendaylight.netconf, (+15 more)
com.fasterxml.jackson.dataformat.smile  [fork: keep `com.fasterxml.jackson.dataformat`, `io.deephaven` still publishes the name]
  A * com.fasterxml.jackson.dataformat     2017-10..2026-11 2.23.0               |.===================|
  ?   io.deephaven                         2026-10..2026-11 42.9                 |...................=|
  R   org.opensearch.migrations.snapshots  2026-04..2026-11 0.3.4.1              |..................==|
  ?   org.apache.pinot                     2026-10..2026-10 1.6.0                |...................=|
  R   com.netflix.spectator                2021-10..2025-08 1.8.17               |.........=========..|
  R   io.acryl                             2024-01..2025-06 1.1.0.3rc1           |..............===...|
    + 4 more: org.apache.hudi, org.pipservices, com.hazelcast.jet, io.trino
io.netty.handler  [fork: keep `io.netty`, `eu.michael-simons.neo4j` still publishes the name]
  A * io.netty                             2017-12..2026-11 4.1.140.Final        |..==================|
  R   eu.michael-simons.neo4j              2025-07..2026-11 4.2.2                |................====|
  ?   io.github.ktestify                   2026-11..2026-11 1.2.8                |...................=|
  R   org.apache.storm                     2025-05..2026-11 3.2.0                |................====|
  ?   io.camunda.connector                 2026-06..2026-11 8.7.32               |..................==|
  ?   eu.exeris                            2026-10..2026-10 0.13.1               |...................=|
    + 9 more: org.openidentityplatform.opendj, io.vitess, org.apache.tika, org.apache.fluss, org.apache.flink, io.github.ousatov-ua, io.kestra.plugin, org.lucee, com.luhuiguo.netty
org.apache.commons.text  [fork: keep `org.apache.commons`, `io.github.liquid-java` still publishes the name]
  A * org.apache.commons                   2018-03..2025-12 1.15.0               |..================..|
  ?   io.github.liquid-java                2026-09..2026-11 0.0.38               |...................=|
  ?   com.nanolaba                         2026-11..2026-11 1.4                  |...................=|
  ?   de.fraunhofer.iosb.ilt.FROST-Server  2026-07..2026-07 2.8.0                |..................=.|
  R   org.bonitasoft.engine.data           2026-01..2026-06 11.1.0               |.................==.|
  R   com.telamin.fluxtion                 2026-05..2026-06 1.0.9                |..................=.|
    + 20 more: net.officefloor.tutorial, com.vmlens, ru.biosoft.diagrams, io.github.venkateshamurthy, dev.jbang, io.github.davidwhitlock.joy, io.github.pro4d, org.bidib.com.github.markusbernhardt, fr.lirmm.graphik, io.github.noeltoy, io.github.mderevyankoaqa, com.salesforce.functions, (+8 more)
org.apache.logging.log4j.slf4j2.impl  [fork: keep `org.apache.logging.log4j`, `org.beilstein` still publishes the name]
  A * org.apache.logging.log4j             2023-10..2026-07 2.25.5               |.............======.|
  ?   org.beilstein                        2026-07..2026-11 1.4.4                |..................==|
  R   org.apache.tika                      2025-04..2026-10 3.3.3                |................====|
  R   net.corda                            2024-07..2026-08 4.14.3               |..............======|
  R   io.kroxylicious                      2024-12..2026-07 0.23.0               |...............====.|
  R   io.github.alien-tools                2025-07..2025-07 0.2.0                |................=...|
    + 2 more: xyz.gianlu.librespot, io.github.giis-uniovi
com.google.j2objc.annotations  [fork: keep `com.google.j2objc`, `de.arbeitsagentur.opdt` still publishes the name]
  A * com.google.j2objc                    2024-03..2025-08 3.1                  |..............====..|
  R   de.arbeitsagentur.opdt               2025-06..2026-11 6.2.2                |................====|
  ?   org.devlive.metaforge                2026-11..2026-11 2026.0.0             |...................=|
  ?   org.fcrepo.migration                 2026-10..2026-10 7.0.0                |...................=|
  ?   org.fcrepo                           2026-10..2026-10 7.0.0                |...................=|
  R   org.apache.cassandra                 2025-02..2026-09 4.19.4               |................====|
    + 18 more: dev.zacsweers.metro, com.scylladb, com.intergral.deep, io.dingodb, io.github.erroraway, com.apivolve, nl.markv, io.edurt.datacap, com.google.cloud, org.liquibase.ext, com.yugabyte, com.google.api, (+6 more)
io.vertx.core  [fork: keep `io.vertx`, `io.quarkus` still publishes the name]
  A * io.vertx                             2020-05..2026-11 4.5.36               |......==============|
  ?   io.quarkus                           2026-11..2026-11 4.0.0.CR1            |...................=|
  ?   org.frankframework                   2026-10..2026-10 10.3.0               |...................=|
  ?   io.dekorate                          2026-09..2026-09 5.0.0                |...................=|
  ?   io.fabric8                           2026-09..2026-09 8.0.0                |...................=|
  ?   io.etcd                              2026-08..2026-08 0.8.7                |...................=|
    + 3 more: io.sirix, io.github.crac.io.vertx, one.gfw
com.networknt.schema  [fork: keep `com.networknt`, `io.krabka` still publishes the name]
  A * com.networknt                        2023-04..2026-11 3.0.9                |............========|
  ?   io.krabka                            2026-08..2026-11 1.4.3                |..................==|
  ?   org.apache.flink                     2026-10..2026-10 0.4.0                |...................=|
  ?   xyz.tcheeric                         2026-08..2026-10 2.4.2                |...................=|
  R   dev.dokimos                          2026-05..2026-05 0.15.0               |..................=.|
  R   io.github.jdbcx                      2026-01..2026-05 1.1.1                |.................==.|
    + 8 more: com.intuit.isl, org.wiremock, org.sonarsource.text, org.wiremock.extensions, com.github.nagyesta.abort-mission.reports, org.wiremock.integrations, xyz.block, com.github.tomakehurst
com.fasterxml.aalto  [fork: keep `com.fasterxml`, `org.jetbrains.kotlin` still publishes the name]
  A * com.fasterxml                        2018-04..2026-05 1.4.0                |..=================.|
  ?   org.jetbrains.kotlin                 2023-12..2026-11 2.5.0-RC             |.............=======|
  R   org.jetbrains.dokka                  2022-06..2023-03 1.8.10               |..........===.......|
org.opentest4j  [fork: keep `org.opentest4j`, `org.jetbrains.kotlin` still publishes the name]
  A * org.opentest4j                       2017-07..2023-07 1.3.0                |.============.......|
  ?   org.jetbrains.kotlin                 2026-07..2026-11 2.5.0-RC             |..................==|
  ?   com.eatthepath                       2026-08..2026-08 1.0.0                |...................=|
  R   org.tiatesting                       2024-12..2026-08 0.1.19               |...............====.|
  R   berlin.yuna                          2025-11..2026-06 2026.06.1562143      |.................==.|
  R   org.ndviet                           2026-04..2026-04 4.42.0               |..................=.|
    + 9 more: com.adobe.cq, io.pravega, org.caseine, io.github.origin-energy, com.hurence.logisland, io.github.thxno, io.github.osvalda, net.corda, com.github.tandronicus
org.checkerframework.checker.qual  [fork: keep `org.checkerframework`, `org.jetbrains.kotlin` still publishes the name]
  A * org.checkerframework                 2018-08..2026-11 4.4.0                |...=================|
  ?   org.jetbrains.kotlin                 2026-09..2026-11 2.5.0-RC             |...................=|
  ?   io.valkyrja                          2026-07..2026-11 26.5.7               |..................==|
  ?   com.dslplatform                      2026-11..2026-11 2.2.0                |...................=|
  R   com.google.cloud                     2024-06..2026-11 3.52.0               |..............======|
  R   com.daml                             2024-09..2026-11 2.10.7-snapshot.20261106.13211.0.v90c49ea2 |...............=====|
    + 34 more: com.facebook.business.sdk, com.silanis.esl, org.docshare, io.vitess, io.boxlang, io.joynr.java.core, io.smallrye.reactive, com.webforj, io.github.imonja, io.github.eisop, org.eclipse.hawkbit, org.jetbrains.kotlinx, (+22 more)
com.zaxxer.hikari  [fork: keep `com.zaxxer`, `io.github.kaleert` still publishes the name]
  A * com.zaxxer                           2018-01..2026-06 7.1.0                |..=================.|
  R   io.github.kaleert                    2026-01..2026-11 1.2.4                |.................===|
  R   org.apache.seatunnel                 2022-12..2026-11 4.0.3-3.0.1          |...........=========|
  R   org.finos.legend.depot               2025-06..2026-11 2.101.0              |................====|
  R   solutions.a2.oracle                  2023-05..2026-10 2.15.7               |............========|
  R   org.apache.hudi                      2023-02..2026-10 1.1.2                |............========|
    + 74 more: org.apache.kylin, org.apache.flink, io.github.yuku123, org.kill-bill.billing, org.apache.dolphinscheduler, org.apache.inlong, com.aliyun.schedulerx, org.quickfixj, work.noice, io.github.deathgod7, io.higson, com.scalar-labs, (+62 more)
jakarta.annotation  [fork: keep `jakarta.annotation`, `org.eclipse.hawkbit` still publishes the name]
  A * jakarta.annotation                   2020-01..2024-02 3.0.0                |......=========.....|
  ?   org.eclipse.hawkbit                  2026-11..2026-11 1.2.0                |...................=|
  R   org.apache.tomcat                    2020-09..2026-11 10.1.62              |.......=============|
  R   com.heroku                           2024-10..2026-11 10.1.61.0            |...............=====|
  R   io.quarkus                           2024-08..2026-11 3.33.4.2             |...............=====|
  R   io.github.n1ckl0sk0rtge              2025-06..2026-10 1.4.0                |................====|
    + 20 more: se.swedenconnect.keycloak, com.datadoghq, org.tinystruct, org.apache.james, org.apache.iotdb, com.intuit.quickbooks-online, com.segment.analytics.java, org.eclipse.ecsp, com.jcabi, com.affinidi.tdk, net.welen.jmole, de.fraunhofer.sit.sse.flowdroid, (+8 more)
jakarta.el  [fork: keep `jakarta.el`, `org.apache.tomcat` still publishes the name]
  A * jakarta.el                           2020-08..2026-05 6.1.0-M2             |.......============.|
  R   org.apache.tomcat                    2020-11..2026-11 10.1.62              |.......=============|
  ?   dev.thihup.joel                      2026-11..2026-11 6.1.0-M2             |...................=|
  R   org.open-metadata                    2026-05..2026-10 2.1.0-rc1            |..................==|
  R   com.heroku                           2024-05..2026-07 10.1.56.0            |..............=====.|
  R   org.jboss.spec.jakarta.el            2020-12..2025-11 6.0.2.Final          |.......===========..|
    + 2 more: com.guicedee.services, org.tomitribe.jamira
org.slf4j.simple  [fork: keep `org.slf4j`, `com.atomgraph.etl.json` still publishes the name]
  A * org.slf4j                            2017-04..2026-09 2.0.20               |====================|
  ?   com.atomgraph.etl.json               2026-11..2026-11 1.0.9                |...................=|
  ?   de.uniks                             2026-10..2026-11 4.8.1405             |...................=|
  R   io.github.huaweicloud                2023-08..2026-10 1.2.0                |.............=======|
  R   eu.maveniverse.maven.plugins         2024-05..2026-02 0.15.4               |..............=====.|
  R   io.btrace                            2025-12..2026-02 0.14.0               |.................==.|
    + 25 more: dev.neovoxel.jarflow, io.github.mario-bezerra, com.vajrapulse, no.elixir, com.mineplex.studio.sdk, se.bjurr.gitchangelog, io.github.eschizoid, cz.o2.proxima, dev.lukebemish, io.github.lizhi404, io.opentelemetry.contrib, io.github.qsy7.java.examples, (+13 more)
com.google.errorprone.annotations  [fork: keep `com.google.errorprone`, `io.github.dfa1.vortex` still publishes the name]
  A * com.google.errorprone                2019-12..2026-11 2.52.0               |......==============|
  ?   io.github.dfa1.vortex                2026-10..2026-11 0.20.0               |...................=|
  R   org.apache.seatunnel                 2024-10..2026-11 1.38.0-3.0.1         |...............=====|
  R   com.facebook.presto                  2025-02..2026-11 0.300                |................====|
  R   org.checkerframework                 2024-10..2026-11 4.4.0                |...............=====|
  ?   com.jasonwjones.hyperion.vess        2026-10..2026-10 1.0.0                |...................=|
    + 61 more: edu.uky.cs.nil.tt, com.salesforce.multicloudj, org.apache.spark, io.github.iztou, edu.uky.cs.nil, com.palantir.hadoop-crypto2, au.com.integradev.samples, io.github.ai-dos-tr, com.google.googlejavaformat, io.github.de-tu-dresden-inf-lat, com.clickzetta, eu.rssw.sonar.openedge, (+49 more)
io.matthewnelson.kmp.tor.resource.lib.tor  [fork: keep `io.matthewnelson.kmp-tor`, `io.github.rodvar.kmp-tor` still publishes the name]
  ? * io.matthewnelson.kmp-tor             2024-11..2026-02 409.5.0              |...............====.|
  ?   io.github.rodvar.kmp-tor             2026-09..2026-11 409.16.0             |...................=|
io.matthewnelson.kmp.tor.resource.noexec.tor  [fork: keep `io.matthewnelson.kmp-tor`, `io.github.rodvar.kmp-tor` still publishes the name]
  ? * io.matthewnelson.kmp-tor             2024-11..2026-02 409.5.0              |...............====.|
  ?   io.github.rodvar.kmp-tor             2026-09..2026-11 409.16.0             |...................=|
io.matthewnelson.kmp.tor.resource.exec.tor  [fork: keep `io.matthewnelson.kmp-tor`, `io.github.rodvar.kmp-tor` still publishes the name]
  ? * io.matthewnelson.kmp-tor             2024-11..2026-02 409.5.0              |...............====.|
  ?   io.github.rodvar.kmp-tor             2026-09..2026-11 409.16.0             |...................=|
io.matthewnelson.kmp.tor.resource.geoip  [fork: keep `io.matthewnelson.kmp-tor`, `io.github.rodvar.kmp-tor` still publishes the name]
  ? * io.matthewnelson.kmp-tor             2024-11..2026-02 409.5.0              |...............====.|
  ?   io.github.rodvar.kmp-tor             2026-09..2026-11 409.16.0             |...................=|
com.fasterxml.jackson.datatype.jsr310  [fork: keep `com.fasterxml.jackson.datatype`, `io.teknek.reckoning` still publishes the name]
  A * com.fasterxml.jackson.datatype       2017-10..2026-11 2.23.0               |.===================|
  ?   io.teknek.reckoning                  2026-11..2026-11 0.0.17               |...................=|
  R   com.linkedin.iceberg                 2025-09..2026-11 1.5.2.24             |.................===|
  R   org.opencds.cqf.cql.ls               2026-03..2026-11 5.0.0                |..................==|
  ?   io.github.mattiasgreen               2026-11..2026-11 1.13.0               |...................=|
  R   org.openapitools                     2022-09..2026-11 7.27.0               |...........=========|
    + 87 more: org.camunda.connector, io.camunda.filestorage, io.openlineage, org.apache.hudi, org.apache.knox, io.github.yuku123, org.apache.gravitino, org.octopusden.octopus.reporting-service, io.teknek.deliverance, org.apache.doris, com.github.twitch4j, cab.ml, (+75 more)
com.fasterxml.jackson.dataformat.cbor  [fork: keep `com.fasterxml.jackson.dataformat`, `software.amazon.glue` still publishes the name]
  A * com.fasterxml.jackson.dataformat     2017-10..2026-11 2.23.0               |.===================|
  ?   software.amazon.glue                 2026-10..2026-11 2.0.2                |...................=|
  R   net.snowflake                        2023-06..2026-10 4.4.6                |............========|
  R   org.apache.dolphinscheduler          2025-03..2026-09 3.4.3                |................====|
  R   org.eclipse.ditto                    2022-03..2026-08 1.2.3                |..........=========.|
  R   com.amazonaws                        2020-09..2025-12 1.12.797             |.......===========..|
    + 18 more: org.apache.jackrabbit, software.amazon.neptune, org.elasticsearch.plugin, com.hazelcast.jet, org.duracloud, org.alluxio, org.apache.beam, com.liferay.portal, io.kestra.plugin, org.apache.flink, edu.internet2.middleware.grouper, org.apache.pinot, (+6 more)
com.fasterxml.jackson.jakarta.rs.json  [fork: keep `com.fasterxml.jackson.jakarta.rs`, `org.finos.legend.sdlc` still publishes the name]
  A * com.fasterxml.jackson.jakarta.rs     2021-07..2026-11 2.23.0               |.........===========|
  ?   org.finos.legend.sdlc                2026-10..2026-11 1.0.1                |...................=|
  ?   io.smartdatalake                     2026-10..2026-10 8.2.0                |...................=|
  R   org.apache.tika                      2023-12..2026-10 3.3.3                |.............=======|
  R   ch.exense.step                       2022-10..2026-09 3.30.4               |...........=========|
  R   ch.exense.step.library               2023-08..2026-08 1.0.32               |.............======.|
    + 15 more: com.inteligr8.activiti, com.phonepe.sentinel-ai, org.eclipse.tractusx.edc, org.ow2.petals.samples.rest.edm, dev.getelements.elements, org.eclipse.edc.huawei, org.eclipse.edc.aws, org.eclipse.edc, io.nflow, com.brightsparklabs, io.trino.gateway, com.snehasishroy, (+3 more)
com.ethlo.time  [fork: keep `com.ethlo.time`, `io.github.adorsys-gis` still publishes the name]
  ? * com.ethlo.time                       2023-06..2026-10 1.17.0               |............========|
  ?   io.github.adorsys-gis                2026-07..2026-11 1.4.4                |..................==|
org.snakeyaml.engine.v2  [fork: keep `org.snakeyaml`, `io.github.baokhang83.mnemo` still publishes the name]
  A * org.snakeyaml                        2019-10..2025-07 2.10                 |.....============...|
  ?   io.github.baokhang83.mnemo           2026-07..2026-11 0.2.1                |..................==|
  R   dev.dejvokep                         2024-07..2026-10 1.3.8                |..............======|
  R   org.frankframework                   2025-01..2026-10 9.4.5                |...............=====|
  ?   io.btrace                            2026-07..2026-10 0.29.0               |..................==|
  R   io.github.ethanz0x0                  2025-08..2026-08 2.0.4                |.................===|
    + 29 more: com.walmartlabs.concord.k8s, com.datadoghq, org.wildfly.glow, org.apache.zeppelin, io.acryl, io.fabric8, io.strimzi, io.github.phompang, eu.koboo, io.dscope.camel, org.workflomics, org.sonarsource.iac, (+17 more)
io.github.classgraph.vfs  [fork: keep `io.github.classgraph`, `de.redsix` still publishes the name]
  ? * io.github.classgraph                 2026-10..2026-11 6.0.2                |...................=|
  ?   de.redsix                            2026-11..2026-11 1.5.0                |...................=|
org.apache.commons.configuration2  [fork: keep `org.apache.commons`, `io.github.zqxxjs` still publishes the name]
  A * org.apache.commons                   2017-10..2026-05 2.15.1               |.==================.|
  ?   io.github.zqxxjs                     2026-11..2026-11 1.0.0                |...................=|
  ?   org.wso2.orbit.org.apache.commons    2026-06..2026-06 2.15.1.wso2v1        |..................=.|
  R   org.neo4j.procedure                  2024-04..2026-01 4.4.0.40             |..............====..|
  R   com.databricks.labs                  2025-05..2026-01 0.6.17               |................==..|
  R   software.amazon.s3tables             2024-12..2025-08 0.1.8                |...............===..|
    + 3 more: com.sonatype.central.testing.amazon, org.bidib.com.github.markusbernhardt, consulting.freiheitsgrade.patched
com.fasterxml.jackson.kotlin  [fork: keep `com.fasterxml.jackson.module`, `io.fabrikt` still publishes the name]
  A * com.fasterxml.jackson.module         2021-01..2026-11 2.23.0               |........============|
  ?   io.fabrikt                           2026-10..2026-11 29.0.0               |...................=|
  R   io.inji.certify                      2026-03..2026-07 1.0.0-alpha.1        |..................=.|
  R   io.github.aaiezza                    2024-08..2024-08 1.0.3                |...............=....|
  R   com.atlan                            2023-11..2024-05 1.11.5               |.............==.....|
  R   com.expediagroup.openworld.sdk       2023-02..2023-09 2.0.0                |............==......|
    + 2 more: it.unibo.tuprolog.argumentation, com.googlecode.blaisemath
org.htmlunit.cyberneko  [fork: keep `org.htmlunit`, `com.nordstrom.ui-tools` still publishes the name]
  ? * org.htmlunit                         2026-05..2026-11 5.7.0                |..................==|
  ?   com.nordstrom.ui-tools               2026-07..2026-11 4.52.0               |..................==|
  ?   org.seleniumhq.selenium              2026-06..2026-06 4.45.0               |..................=.|
com.formdev.flatlaf  [fork: keep `com.formdev`, `net.thevpc.nuts.installers` still publishes the name]
  A * com.formdev                          2019-10..2026-11 3.8                  |.....===============|
  ?   net.thevpc.nuts.installers           2026-11..2026-11 1.1.0                |...................=|
  ?   eu.rekawek.coffeegb                  2026-08..2026-11 2.1.8                |..................==|
  R   com.suckatcoding                     2025-11..2026-10 1.23.1               |.................===|
  R   dev.robocode.tankroyale              2026-05..2026-10 1.4.0                |..................==|
  ?   org.graphper                         2026-08..2026-09 1.5.5                |..................==|
    + 5 more: ca.corbett, de.florianreuth, de.florianmichael, io.github.harvardpl, com.github.sundev79.MineBootFramework
com.github.librepdf.openpdf  [fork: keep `com.github.librepdf`, `com.guicedee.modules.services` still publishes the name]
  A * com.github.librepdf                  2018-09..2026-05 3.0.5                |...================.|
  R   com.guicedee.modules.services        2026-04..2026-11 2.5.1                |..................==|
  ?   io.github.icarius4iu                 2026-06..2026-06 0.1.0                |..................=.|
  R   net.ioze                             2026-01..2026-01 1.0.9                |.................=..|
  R   org.computate                        2023-11..2024-02 2.0.2                |.............==.....|
  R   io.github.darkxanter                 2023-10..2023-10 1.3.31               |.............=......|
    + 2 more: com.github.kwart.jsign, com.guicedee.services
org.mongodb.driver.core  [fork: keep `org.mongodb`, `com.guicedee.modules.services` still publishes the name]
  ? * org.mongodb                          2018-04..2026-10 5.14.0               |..==================|
  ?   com.guicedee.modules.services        2026-08..2026-11 2.5.1                |...................=|
org.mongodb.driver.reactivestreams  [fork: keep `org.mongodb`, `com.guicedee.modules.services` still publishes the name]
  ? * org.mongodb                          2020-01..2026-10 5.14.0               |......==============|
  ?   com.guicedee.modules.services        2026-08..2026-11 2.5.1                |...................=|
org.reactivestreams  [fork: keep `org.reactivestreams`, `com.guicedee.modules.services` still publishes the name]
  A * org.reactivestreams                  2017-12..2022-05 1.0.4                |..=========.........|
  ?   com.guicedee.modules.services        2026-07..2026-11 2.5.1                |..................==|
  R   dev.ikm.jpms                         2024-01..2024-08 1.0.3-r6             |..............==....|
com.google.guice  [fork: keep `com.google.inject`, `com.guicedee.modules.services` still publishes the name]
  A * com.google.inject                    2018-02..2023-05 7.0.0                |..===========.......|
  R   com.guicedee.modules.services        2026-04..2026-11 2.5.1                |..................==|
  ?   com.carrotsearch.thirdparty.google.inject 2026-09..2026-09 7.0.1                |...................=|
  ?   io.airlift                           2026-08..2026-08 10                   |...................=|
  R   ru.vyarus.guice.jakarta              2023-04..2023-04 5.1.0-rc.2           |............=.......|
  R   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17        |.....======.........|
    + 4 more: io.forestframework, ca.stellardrift.guice-backport, com.jwebmp.inject, org.sonatype.sisu
com.google.guice.extensions.assistedinject  [fork: keep `com.google.inject.extensions`, `com.guicedee.modules.services` still publishes the name]
  A * com.google.inject.extensions         2018-02..2023-05 7.0.0                |..===========.......|
  R   com.guicedee.modules.services        2026-04..2026-11 2.5.1                |..................==|
  ?   io.airlift                           2026-08..2026-08 10                   |...................=|
  R   ru.vyarus.guice.jakarta              2023-04..2023-04 5.1.0-rc.2           |............=.......|
  R   com.guicedee.services                2020-06..2022-02 1.2.2.1-jre17        |.......====.........|
  R   io.forestframework                   2021-06..2021-06 5.0.1.1              |........=...........|
    + 4 more: ca.stellardrift.guice-backport.extensions, com.guicedee.services.extensions, com.jwebmp.inject.extensions, org.sonatype.sisu.inject
com.google.guice.extensions.grapher  [fork: keep `com.google.inject.extensions`, `com.guicedee.modules.services` still publishes the name]
  A * com.google.inject.extensions         2018-02..2023-05 7.0.0                |..===========.......|
  R   com.guicedee.modules.services        2026-04..2026-11 2.5.1                |..................==|
  ?   io.airlift                           2026-08..2026-08 10                   |...................=|
  R   ru.vyarus.guice.jakarta              2023-04..2023-04 5.1.0-rc.2           |............=.......|
  R   com.guicedee.services                2020-07..2022-02 1.2.2.1-jre17        |.......====.........|
  R   io.forestframework                   2021-06..2021-06 5.0.1.1              |........=...........|
    + 4 more: ca.stellardrift.guice-backport.extensions, com.guicedee.services.extensions, com.jwebmp.inject.extensions, org.sonatype.sisu.inject
com.google.guice.extensions.jmx  [fork: keep `com.google.inject.extensions`, `com.guicedee.modules.services` still publishes the name]
  A * com.google.inject.extensions         2018-02..2023-05 7.0.0                |..===========.......|
  R   com.guicedee.modules.services        2026-04..2026-11 2.5.1                |..................==|
  ?   io.airlift                           2026-08..2026-08 10                   |...................=|
  R   ru.vyarus.guice.jakarta              2023-04..2023-04 5.1.0-rc.2           |............=.......|
  R   com.guicedee.services                2020-07..2022-02 1.2.2.1-jre17        |.......====.........|
  R   io.forestframework                   2021-06..2021-06 5.0.1.1              |........=...........|
    + 4 more: ca.stellardrift.guice-backport.extensions, com.guicedee.services.extensions, com.jwebmp.inject.extensions, org.sonatype.sisu.inject
org.apache.commons.collections4  [fork: keep `org.apache.commons`, `com.guicedee.modules.services` still publishes the name]
  A * org.apache.commons                   2018-07..2026-08 4.6.0                |...================.|
  R   com.guicedee.modules.services        2026-04..2026-11 2.5.1                |..................==|
  ?   org.apache.directory.api             2026-05..2026-09 2.1.9                |..................==|
  R   io.github.qudtlib                    2024-12..2025-10 7.1.1                |...............===..|
  R   de.jball                             2025-07..2025-07 0.9.0                |................=...|
  R   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17        |.....======.........|
    + 1 more: com.jwebmp.jpms.commons
org.apache.poi.poi  [fork: keep `org.apache.poi`, `com.guicedee.modules.services` still publishes the name]
  A * org.apache.poi                       2021-01..2026-10 6.0.0                |........============|
  R   com.guicedee.modules.services        2026-04..2026-11 2.5.1                |..................==|
  ?   ai.platon.pulsar                     2026-08..2026-10 4.14.3               |..................==|
  ?   io.github.mahendranchinnaiah         2026-10..2026-10 1.0.0                |...................=|
  R   io.github.itgemini                   2024-12..2026-02 2.2.5                |...............===..|
  R   io.github.daichangya                 2025-12..2025-12 5.1.1                |.................=..|
    + 15 more: com.jsdiff, io.gitee.tikadoc, io.github.geminit-it, io.github.mianalysis, com.liferay, com.crealytics, com.codoid.products, ch.exense.step.library, com.guicedee.services, io.github.rocketmadev, org.datakurator, org.lucee, (+3 more)
org.mongodb.bson  [fork: keep `org.mongodb`, `com.guicedee.modules.services` still publishes the name]
  ? * org.mongodb                          2018-04..2026-10 5.14.0               |..==================|
  ?   com.guicedee.modules.services        2026-08..2026-11 2.5.1                |...................=|
org.mongodb.bson.record.codec  [fork: keep `org.mongodb`, `com.guicedee.modules.services` still publishes the name]
  ? * org.mongodb                          2022-04..2026-10 5.14.0               |..........==========|
  ?   com.guicedee.modules.services        2026-08..2026-11 2.5.1                |...................=|
io.netty.transport.epoll.linux.x86_64  [fork: keep `io.netty`, `org.apache.storm` still publishes the name]
  A * io.netty                             2022-05..2026-11 4.1.140.Final        |..........==========|
  R   org.apache.storm                     2025-05..2026-11 3.2.0                |................====|
  ?   io.camunda.connector                 2026-06..2026-08 8.7.23               |..................=.|
  R   com.atscale.opensource               2026-01..2026-01 1.14                 |.................=..|
  R   org.readutf.orchestrator             2025-02..2025-02 2.0.0                |................=...|
dev.dirs  [fork: keep `dev.dirs`, `io.get-coursier.util` still publishes the name]
  ? * dev.dirs                             2020-06..2021-04 26                   |.......==...........|
  ?   io.get-coursier.util                 2026-11..2026-11 0.2.0                |...................=|
org.apache.xmlgraphics.fop.core  [fork: keep `org.apache.xmlgraphics`, `org.docx4j` still publishes the name]
  ? * org.apache.xmlgraphics               2024-10..2026-10 2.12                 |...............=====|
  ?   org.docx4j                           2026-09..2026-11 2.12-docx4j.1        |...................=|
org.apache.xmlgraphics.fop  [fork: keep `org.apache.xmlgraphics`, `org.docx4j` still publishes the name]
  ? * org.apache.xmlgraphics               2024-10..2026-10 2.12                 |...............=====|
  ?   org.docx4j                           2026-09..2026-11 2.12-docx4j.1        |...................=|
org.apache.xmlgraphics.fop.events  [fork: keep `org.apache.xmlgraphics`, `org.docx4j` still publishes the name]
  ? * org.apache.xmlgraphics               2024-10..2026-10 2.12                 |...............=====|
  ?   org.docx4j                           2026-09..2026-11 2.12-docx4j.1        |...................=|
org.apache.xmlgraphics.fop.util  [fork: keep `org.apache.xmlgraphics`, `org.docx4j` still publishes the name]
  ? * org.apache.xmlgraphics               2024-10..2026-10 2.12                 |...............=====|
  ?   org.docx4j                           2026-09..2026-11 2.12-docx4j.1        |...................=|
org.apache.sis.referencing  [fork: keep `org.apache.sis.core`, `org.openldes.ldio` still publishes the name]
  A * org.apache.sis.core                  2019-09..2026-10 1.7                  |.....===============|
  ?   org.openldes.ldio                    2026-11..2026-11 3.2.2                |...................=|
  R   be.vlaanderen.informatievlaanderen.ldes.ldio 2024-12..2024-12 2.12.0               |...............=....|
org.jboss.logging  [fork: keep `org.jboss.logging`, `org.openldes.ldio` still publishes the name]
  A * org.jboss.logging                    2018-02..2026-03 3.6.3.Final          |..=================.|
  ?   org.openldes.ldio                    2026-11..2026-11 3.2.2                |...................=|
  R   io.quarkus                           2024-09..2026-10 4.0.0.Beta2          |...............=====|
  R   org.jboss.ironjacamar                2026-05..2026-08 1.5.28.Final         |..................==|
  R   io.appform.ranger                    2025-11..2026-08 2.0.0-RC6            |.................===|
  R   org.tinystruct                       2026-05..2026-08 1.0.9                |..................=.|
    + 5 more: be.vlaanderen.informatievlaanderen.ldes.ldio, io.mvnpm, ch.nerdin, com.abavilla, com.guicedee.services
com.azure.storage.blob  [fork: keep `com.azure`, `org.openldes.ldio` still publishes the name]
  A * com.azure                            2020-06..2026-11 12.38.0-beta.1       |.......=============|
  ?   org.openldes.ldio                    2026-11..2026-11 3.2.2                |...................=|
  R   org.apache.jackrabbit                2026-04..2026-07 2.4.0                |..................=.|
  R   be.vlaanderen.informatievlaanderen.ldes.ldio 2024-12..2024-12 2.12.0               |...............=....|
org.apache.datasketches.memory  [fork: keep `org.apache.datasketches`, `com.spiraldb` still publishes the name]
  A * org.apache.datasketches              2021-09..2024-10 3.0.2                |.........=======....|
  ?   com.spiraldb                         2026-11..2026-11 1.13.0-spiral.1      |...................=|
  A   org.apache.doris                     2024-10..2026-10 4.1.0                |...............=====|
  A   org.apache.paimon                    2024-09..2026-10 2.1.0                |...............=====|
  ?   com.datastrato                       2026-09..2026-10 1.13.0               |...................=|
  A   org.apache.flink                     2025-01..2026-09 3.7.0-2.2            |...............=====|
    + 11 more: org.apache.iceberg, org.apache.seatunnel, org.apache.pinot, com.facebook.presto, org.apache.fluss, io.github.pl-buiquang, com.alibaba.fluss, uk.gov.gchq.gaffer, io.kestra.plugin, org.locationtech.geomesa, com.gelerion.spark.sketches
com.googlecode.javaewah  [fork: keep `com.googlecode.javaewah`, `io.github.baokhang83.blastradius` still publishes the name]
  A * com.googlecode.javaewah              2023-03..2023-03 1.2.3                |............=.......|
  ?   io.github.baokhang83.blastradius     2026-07..2026-11 0.4.0                |..................==|
  R   org.meyvn                            2023-07..2026-05 1.9.4                |............=======.|
  R   org.liquibase.ext                    2024-02..2025-01 0.4.0                |..............===...|
  R   io.kestra.plugin                     2023-07..2024-08 0.17.2               |............====....|
io.netty.transport  [fork: keep `io.netty`, `io.camunda.connector` still publishes the name]
  A * io.netty                             2017-12..2026-11 4.1.140.Final        |..==================|
  ?   io.camunda.connector                 2026-08..2026-11 8.7.32               |...................=|
  R   com.arcadedb                         2025-04..2026-11 26.11.1              |................====|
  R   com.sportradar.unifiedodds.sdk       2026-02..2026-10 5.0.0                |..................==|
  ?   org.reactivemongo                    2026-08..2026-10 1.1.0-RC22           |..................==|
  R   io.github.lukaszsamson               2026-04..2026-07 0.2.0                |..................=.|
    + 2 more: io.github.qbsstg, io.karatelabs
io.netty.internal.tcnative.openssl.linux.x86_64  [fork: keep `io.netty`, `io.camunda.connector` still publishes the name]
  A * io.netty                             2022-05..2026-11 2.0.85.Final         |..........==========|
  ?   io.camunda.connector                 2026-06..2026-11 8.6.36               |..................==|
  R   io.smallrye                          2026-04..2026-11 0.2.3+tcnative-2.0.85.Final |..................==|
  R   com.azure.cosmos.spark               2026-02..2026-09 4.50.0               |..................==|
  R   org.apache.iotdb                     2026-04..2026-09 2.0.11               |..................==|
  ?   com.solacecoe.connectors             2026-08..2026-08 4.0.0                |..................=.|
    + 6 more: io.zipkin.dependencies, com.danielflower.apprunner, io.karatelabs, io.opentelemetry.javaagent, com.github.emc-mongoose, io.servicetalk
io.netty.codec.http  [fork: keep `io.netty`, `io.camunda.connector` still publishes the name]
  A * io.netty                             2017-12..2026-11 4.1.140.Final        |..==================|
  ?   io.camunda.connector                 2026-06..2026-11 8.6.36               |..................==|
  R   com.amazonaws                        2026-02..2026-10 2026.43.1            |.................===|
  R   org.apache.tika                      2026-03..2026-10 3.3.3                |..................==|
  R   dev.zio                              2025-05..2026-09 3.11.6               |................====|
  ?   io.github.shafthq                    2026-07..2026-08 10.3.20260806        |..................=.|
    + 6 more: com.liquibase.ext, org.apache.flink, com.xuxueli, org.eclipse.ditto, io.sapl, de.fraunhofer.iosb.ilt.faaast.service
org.apache.logging.log4j  [fork: keep `org.apache.logging.log4j`, `com.mobius-software.protocols.sip` still publishes the name]
  A * org.apache.logging.log4j             2017-11..2026-07 2.25.5               |..=================.|
  R   com.mobius-software.protocols.sip    2024-01..2026-11 10.1.0-35            |..............======|
  R   dev.mauch                            2024-12..2026-10 3.4.4_0.34.1-prerelease37 |...............=====|
  R   nl.basjes.parse.useragent            2020-06..2026-10 8.3.0                |.......=============|
  R   com.ibm.galasa                       2026-02..2026-10 2.2.0                |..................==|
  ?   org.springframework.cloud            2026-08..2026-10 5.0.4                |...................=|
    + 330 more: io.github.egonw, org.wordinator, com.alibaba.ververica, io.github.yuku123, org.apache.seatunnel, io.github.liangqinxin, dk.dma.ais.lib, io.github.beehive-lab, com.alibaba.dts.client, io.kroxylicious, org.lucee, com.nqadmin.swingset.demo, (+318 more)
com.h2database  [fork: keep `com.h2database`, `io.vidocq.runtime.extensions.module.repackaged` still publishes the name]
  A * com.h2database                       2019-02..2026-09 2.5.252              |....================|
  ?   io.vidocq.runtime.extensions.module.repackaged 2026-08..2026-11 0.4.0-RC2            |...................=|
  R   ai.platon.pulsar                     2026-01..2026-01 1.4.197              |.................=..|
  R   net.xdob.h2db                        2022-06..2023-02 2.1.1                |..........===.......|
  R   org.lucee                            2023-01..2023-01 2.1.214.0001L        |............=.......|
io.opentelemetry.instrumentation_annotations  [fork: keep `io.opentelemetry.instrumentation`, `io.vidocq.humboldt` still publishes the name]
  ? * io.opentelemetry.instrumentation     2023-10..2026-10 2.33.1               |.............=======|
  ?   io.vidocq.humboldt                   2026-06..2026-11 0.4.0-RC2            |..................==|
org.eclipse.microprofile.config  [fork: keep `org.eclipse.microprofile.config`, `io.vidocq.ravel` still publishes the name]
  ? * org.eclipse.microprofile.config      2026-01..2026-10 3.1.3                |.................===|
  ?   io.vidocq.ravel                      2026-05..2026-11 0.4.0-RC2            |..................==|
ch.randelshofer.fastdoubleparser  [fork: keep `ch.randelshofer`, `org.jruby` still publishes the name]
  A * ch.randelshofer                      2022-11..2024-11 2.0.1                |...........=====....|
  ?   org.jruby                            2026-07..2026-11 10.1.3.0             |..................==|
  R   za.co.absa.spline.agent.spark        2023-06..2026-08 2.4.0                |............========|
  R   org.apache.inlong                    2023-10..2026-08 2.4.0                |.............======.|
  R   com.clickzetta                       2024-08..2026-07 2.0.1                |...............====.|
  R   software.amazon.smithy.java          2026-05..2026-06 1.4.0                |..................=.|
    + 33 more: io.github.solven-eu.pepper, org.metafacture, org.jetbrains.kotlinx.dataframe, com.cjbooms, io.github.cmu-phil, io.github.hkarthik7, io.kestra.plugin, io.trino, org.apache.arrow, com.databricks, org.sonarsource.text, org.openrewrite, (+21 more)
jakarta.ws.rs  [fork: keep `jakarta.ws.rs`, `nl.mirila.cli` still publishes the name]
  A * jakarta.ws.rs                        2020-02..2026-10 5.0.0-M1             |......==============|
  R   nl.mirila.cli                        2025-11..2026-11 3.20.2               |.................===|
  R   io.github.tblsoft.solr               2025-07..2026-10 4.8                  |................====|
  R   org.jboss.narayana.lra               2024-12..2026-10 2.0.1.Final          |...............=====|
  ?   com.exasol                           2026-06..2026-09 5.7.5                |..................==|
  R   com.bluecirclesoft.open              2025-09..2026-08 1.11                 |.................===|
    + 14 more: com.inteligr8.activiti, com.affinidi.tdk, me.chrissw-r1, com.biit-solutions, com.github.xeroapi, com.liferay, no.telenor.sdk, org.opengis.cite, com.documents4j, com.github.estuaryoss, com.jcabi, com.datadoghq, (+2 more)
fr.lirmm.integraal.rule_analysis  [fork: keep `fr.lirmm.graphik`, `fr.inria.rules` still publishes the name]
  ? * fr.lirmm.graphik                     2023-11..2025-06 2.0.7                |.............====...|
  ?   fr.inria.rules                       2026-06..2026-11 3.2.7                |..................==|
cafe.cryptography.curve25519_elisabeth  [fork: keep `cafe.cryptography`, `com.weavechain` still publishes the name]
  ? * cafe.cryptography                    2019-05..2019-05 0.1.0                |....=...............|
  ?   com.weavechain                       2023-06..2026-11 0.1.7                |............========|
org.graalvm.truffle  [fork: keep `org.graalvm.truffle`, `ai.ravenroot` still publishes the name]
  A * org.graalvm.truffle                  2018-10..2026-11 25.5.5.0.3           |...=================|
  ?   ai.ravenroot                         2026-09..2026-11 0.9.0-alpha.1        |...................=|
  ?   cn.itrondi.plugin                    2026-10..2026-10 23.1.6-ap2           |...................=|
  R   com.arcadedb                         2025-12..2026-10 26.10.1              |.................===|
  R   io.camunda.connectors.community      2024-11..2026-09 2.3.0                |...............=====|
  ?   ai.mindconnect                       2026-08..2026-09 0.8.4                |...................=|
    + 31 more: ai.looktech, org.helixflo, com.liquibase.ext, com.walmartlabs.concord.k8s, com.walmartlabs.concord, com.walmartlabs.concord.runtime.v1, com.walmartlabs.concord.runtime.v2, io.knish, io.hyperfoil.tools, org.opensearch.migrations.trafficcapture, sh.oso, org.mitre.synthea, (+19 more)
org.jctools.core  [fork: keep `org.jctools`, `net.kieker-monitoring` still publishes the name]
  A * org.jctools                          2020-11..2026-08 4.0.7                |.......============.|
  R   net.kieker-monitoring                2024-09..2026-11 2.0.4                |...............=====|
  ?   io.monix                             2026-08..2026-10 3.5.2                |...................=|
  R   io.xdag                              2025-10..2025-12 0.1.6                |.................=..|
  R   io.github.shangor                    2025-07..2025-08 1.1.3                |................==..|
  R   io.actor4j                           2025-06..2025-06 2.4.0-beta.3         |................=...|
    + 1 more: io.github.jponge.jct
org.apache.commons.pool2  [fork: keep `org.apache.commons`, `com.liquibase.ext` still publishes the name]
  A * org.apache.commons                   2020-07..2025-12 2.13.1               |.......===========..|
  ?   com.liquibase.ext                    2026-06..2026-11 6.0.1                |..................==|
  R   org.apache.directory.api             2023-10..2026-09 2.1.9                |.............=======|
  R   org.apache.druid.extensions.contrib  2024-06..2026-09 38.0.0               |..............======|
  R   org.openjproxy                       2026-03..2026-07 0.5.3-beta           |..................=.|
  R   io.github.caobahuong                 2026-05..2026-05 0.1.1                |..................=.|
    + 5 more: com.redis, org.noear, io.github.hexsook, org.apache.storm, com.vlkan.log4j2
org.springdoc.openapi.ui  [fork: keep `org.springdoc`, `io.github.branislavmalo` still publishes the name]
  A * org.springdoc                        2020-12..2026-10 2.10.0               |.......=============|
  ?   io.github.branislavmalo              2026-08..2026-11 3.2.0-jackson3       |..................==|
  ?   io.github.vpelikh                    2026-06..2026-08 5.1.0                |..................==|
  R   io.github.lisi9988                   2025-07..2025-12 2.8.14               |................==..|
io.netty.tcnative.classes.openssl  [fork: keep `io.netty`, `io.vertx` still publishes the name]
  A * io.netty                             2022-03..2026-11 2.0.85.Final         |..........==========|
  ?   io.vertx                             2026-06..2026-11 4.5.36               |..................==|
  ?   io.fabric8                           2026-06..2026-09 7.9.0                |..................==|
  R   org.neo4j.driver                     2024-11..2026-08 4.4.27               |...............====.|
  R   io.kestra.plugin                     2024-10..2025-06 0.23.0               |...............==...|
  R   eu.michael-simons.neo4j              2024-10..2025-06 2.17.4               |...............==...|
org.lwjgl  [fork: keep `org.lwjgl`, `org.eclipse.rdf4j` still publishes the name]
  A * org.lwjgl                            2017-09..2026-08 3.4.3                |.===================|
  ?   org.eclipse.rdf4j                    2026-11..2026-11 6.2.0-M2             |...................=|
  R   io.github.lego-eden                  2025-10..2026-10 0.2.5                |.................===|
  R   com.io7m.jcoronado                   2024-12..2026-09 2.1.0                |...............=====|
  R   org.collebol                         2025-01..2025-11 0.2.8                |................==..|
  R   io.github.ranchordo                  2022-02..2022-02 1.0.2                |..........=.........|
    + 1 more: org.lwjgl.osgi
org.snakeyaml.engine  [fork: keep `org.snakeyaml`, `org.yamlstar` still publishes the name]
  ? * org.snakeyaml                        2025-11..2026-10 3.2                  |.................===|
  ?   org.yamlstar                         2026-10..2026-11 3.2.1                |...................=|
app.photofox.vipsffm  [fork: keep `app.photofox.vips-ffm`, `com.lopcode.vips-ffm` still publishes the name]
  ? * app.photofox.vips-ffm                2024-09..2026-11 1.10.0               |...............=====|
  ?   com.lopcode.vips-ffm                 2026-11..2026-11 1.11.1               |...................=|
com.thoughtworks.qdox  [fork: keep `com.thoughtworks.qdox`, `com.github.shalousun` still publishes the name]
  A * com.thoughtworks.qdox                2017-08..2024-11 2.2.0                |.===============....|
  ?   com.github.shalousun                 2026-10..2026-10 2.2.0                |...................=|
  R   com.ly.smart-doc                     2024-01..2024-01 2.0.3.4              |.............=......|
org.codehaus.plexus.interpolation  [fork: keep `org.codehaus.plexus`, `org.pipelineframework` still publishes the name]
  A * org.codehaus.plexus                  2025-11..2026-08 1.30.0               |.................===|
  ?   org.pipelineframework                2026-10..2026-10 26.10.1              |...................=|
  R   me.chrissw-r1                        2025-11..2025-12 1.2.5                |.................=..|
org.xerial.sqlitejdbc  [fork: keep `org.xerial`, `tech.molecules` still publishes the name]
  A * org.xerial                           2021-06..2026-10 3.54.1.0             |.........===========|
  ?   tech.molecules                       2026-08..2026-10 0.4.0                |...................=|
  R   org.apache.tika                      2023-01..2026-10 3.3.3                |............========|
  ?   int.esa.nmf                          2026-09..2026-09 5.1                  |...................=|
  ?   int.esa.nmf.sdk                      2026-07..2026-09 5.1                  |..................==|
  R   io.github.treebolic                  2024-07..2026-09 4.2-1                |..............======|
    + 21 more: dev.mintychochip, com.codenameone, at.hugob.plugin.library, io.github.zhyt1985, com.clapbxt, dev.aga.sqlite, io.toxicity.sqlite-mc, io.github.frame-dev, org.apache.sedona, nz.co.gregs, app.cash.sqldelight, io.github.pacocarlesimo, (+9 more)
io.smallrye.common.annotation  [fork: keep `io.smallrye.common`, `org.tinystruct` still publishes the name]
  A * io.smallrye.common                   2022-05..2026-09 2.21.1               |..........==========|
  ?   org.tinystruct                       2026-10..2026-10 1.1.2                |...................=|
  R   io.quarkus                           2024-11..2025-07 3.25.0               |...............==...|
  R   dev.ikm.jpms                         2024-01..2024-08 1.8.0-r5             |..............==....|
  R   ch.nerdin                            2023-03..2023-03 1.0.0-alpha2         |............=.......|
  R   com.abavilla                         2022-10..2022-10 1.0.147              |...........=........|
org.update4j  [fork: keep `org.update4j`, `de.rpgframework` still publishes the name]
  A * org.update4j                         2018-06..2022-02 1.5.9                |...========.........|
  ?   de.rpgframework                      2026-10..2026-10 2.0.0                |...................=|
  R   io.github.vadimpikha                 2022-08..2022-08 1.5.12               |...........=........|
com.github.victools.jsonschema.generator  [fork: keep `com.github.victools`, `com.scivicslab` still publishes the name]
  A * com.github.victools                  2023-03..2026-02 5.0.0                |............=======.|
  ?   com.scivicslab                       2026-09..2026-10 4.6.0                |...................=|
  R   org.drools                           2023-05..2026-03 10.2.0               |............=======.|
com.sun.istack.runtime  [fork: keep `com.sun.istack`, `berlin.yuna` still publishes the name]
  A * com.sun.istack                       2018-08..2023-04 4.2.0                |...==========.......|
  ?   berlin.yuna                          2026-09..2026-10 2026.10.19           |...................=|
  R   com.intuit.quickbooks-online         2025-10..2026-07 6.5.5                |.................==.|
  R   com.github.target365                 2026-04..2026-05 1.9.1                |..................=.|
  R   pro.verron.office-stamper            2026-04..2026-04 3.3                  |..................=.|
  R   io.github.hongjinqiu                 2026-02..2026-02 1.0.6                |..................=.|
    + 7 more: io.github.adrianseguraortiz, com.github.bld-commons, org.uma.jmetal, com.ibm.cics, org.uma, org.atlanmod.neoemf, net.haesleinhuepf
info.picocli.codegen  [fork: keep `info.picocli`, `io.xpipe` still publishes the name]
  ? * info.picocli                         2018-10..2025-04 4.7.7                |...==============...|
  ?   io.xpipe                             2026-10..2026-10 4.7.7-jdk21          |...................=|
com.hazelcast.core  [fork: keep `com.hazelcast`, `io.github.sharding-db` still publishes the name]
  ? * com.hazelcast                        2018-08..2026-05 5.7.0                |...================.|
  ?   io.github.sharding-db                2026-10..2026-10 5.5.0-hf.1           |...................=|
com.headius.invokebinder  [fork: keep `com.headius`, `org.sahli.asciidoc.confluence.publisher` still publishes the name]
  A * com.headius                          2017-10..2026-07 1.16                 |.==================.|
  R   org.sahli.asciidoc.confluence.publisher 2026-03..2026-10 0.36.0               |..................==|
  R   org.springframework.cloud            2024-11..2026-06 4.3.4                |...............====.|
  R   com.lealceldeiro                     2025-02..2026-03 2.3.1                |................===.|
  R   ch.ifocusit.livingdoc                2025-05..2025-05 2.16                 |................=...|
  R   de.jcup.asp                          2021-09..2022-04 1.4.1                |.........==.........|
    + 2 more: org.asciidoctor, org.jruby
com.querydsl.core  [fork: keep `com.querydsl`, `io.github.weir2010` still publishes the name]
  A * com.querydsl                         2021-06..2024-01 5.1.0                |........=======.....|
  ?   io.github.weir2010                   2026-10..2026-10 8.0                  |...................=|
  R   io.github.openfeign.querydsl         2023-10..2026-09 7.7                  |.............=======|
com.querydsl.jpa  [fork: keep `com.querydsl`, `io.github.weir2010` still publishes the name]
  A * com.querydsl                         2021-06..2024-01 5.1.0                |........=======.....|
  ?   io.github.weir2010                   2026-10..2026-10 8.0                  |...................=|
  R   io.github.openfeign.querydsl         2023-10..2026-09 7.7                  |.............=======|
com.querydsl.mongodb  [fork: keep `com.querydsl`, `io.github.weir2010` still publishes the name]
  A * com.querydsl                         2021-06..2024-01 5.1.0                |........=======.....|
  ?   io.github.weir2010                   2026-10..2026-10 8.0                  |...................=|
  R   io.github.openfeign.querydsl         2023-10..2026-09 7.7                  |.............=======|
com.querydsl.spatial  [fork: keep `com.querydsl`, `io.github.weir2010` still publishes the name]
  A * com.querydsl                         2021-06..2024-01 5.1.0                |........=======.....|
  ?   io.github.weir2010                   2026-10..2026-10 8.0                  |...................=|
  R   io.github.openfeign.querydsl         2023-10..2026-09 7.7                  |.............=======|
com.querydsl.sql  [fork: keep `com.querydsl`, `io.github.weir2010` still publishes the name]
  A * com.querydsl                         2021-06..2024-01 5.1.0                |........=======.....|
  ?   io.github.weir2010                   2026-10..2026-10 8.0                  |...................=|
  R   io.github.openfeign.querydsl         2023-10..2026-09 7.7                  |.............=======|
com.querydsl.sql.codegen  [fork: keep `com.querydsl`, `io.github.weir2010` still publishes the name]
  A * com.querydsl                         2021-06..2024-01 5.1.0                |........=======.....|
  ?   io.github.weir2010                   2026-10..2026-10 8.0                  |...................=|
  R   io.github.openfeign.querydsl         2023-10..2026-09 7.7                  |.............=======|
com.querydsl.sql.spatial  [fork: keep `com.querydsl`, `io.github.weir2010` still publishes the name]
  A * com.querydsl                         2021-06..2024-01 5.1.0                |........=======.....|
  ?   io.github.weir2010                   2026-10..2026-10 8.0                  |...................=|
  R   io.github.openfeign.querydsl         2023-10..2026-09 7.7                  |.............=======|
com.querydsl.apt  [fork: keep `com.querydsl`, `io.github.weir2010` still publishes the name]
  A * com.querydsl                         2021-06..2024-01 5.1.0                |........=======.....|
  ?   io.github.weir2010                   2026-10..2026-10 8.0                  |...................=|
  R   io.github.openfeign.querydsl         2023-10..2026-09 7.7                  |.............=======|
  R   io.github.mingeun0507                2023-12..2023-12 5.0.1                |.............=......|
com.querydsl.codegen  [fork: keep `com.querydsl`, `io.github.weir2010` still publishes the name]
  A * com.querydsl                         2021-06..2024-01 5.1.0                |........=======.....|
  ?   io.github.weir2010                   2026-10..2026-10 8.0                  |...................=|
  R   io.github.openfeign.querydsl         2023-10..2026-09 7.7                  |.............=======|
com.querydsl.codegen.utils  [fork: keep `com.querydsl`, `io.github.weir2010` still publishes the name]
  A * com.querydsl                         2021-06..2024-01 5.1.0                |........=======.....|
  ?   io.github.weir2010                   2026-10..2026-10 8.0                  |...................=|
  R   io.github.openfeign.querydsl         2023-10..2026-09 7.7                  |.............=======|
com.querydsl.collections  [fork: keep `com.querydsl`, `io.github.weir2010` still publishes the name]
  A * com.querydsl                         2021-06..2024-01 5.1.0                |........=======.....|
  ?   io.github.weir2010                   2026-10..2026-10 8.0                  |...................=|
  R   io.github.openfeign.querydsl         2023-10..2026-09 7.7                  |.............=======|
com.querydsl.core.group.guava  [fork: keep `com.querydsl`, `io.github.weir2010` still publishes the name]
  A * com.querydsl                         2021-06..2024-01 5.1.0                |........=======.....|
  ?   io.github.weir2010                   2026-10..2026-10 8.0                  |...................=|
  R   io.github.openfeign.querydsl         2023-10..2026-09 7.7                  |.............=======|
com.querydsl.jpa.codegen  [fork: keep `com.querydsl`, `io.github.weir2010` still publishes the name]
  A * com.querydsl                         2021-06..2024-01 5.1.0                |........=======.....|
  ?   io.github.weir2010                   2026-10..2026-10 8.0                  |...................=|
  R   io.github.openfeign.querydsl         2023-10..2026-09 7.7                  |.............=======|
org.apache.commons.beanutils2  [fork: keep `org.apache.commons`, `org.onebusaway` still publishes the name]
  ? * org.apache.commons                   2024-12..2025-05 2.0.0-M2             |...............==...|
  ?   org.onebusaway                       2025-05..2026-10 15.0.0               |................====|
  ?   com.github.bordertech.wcomponents    2025-12..2026-01 1.5.39               |.................=..|
org.signal.libsignal  [fork: keep `org.signal`, `io.github.wanggenlin` still publishes the name]
  A * org.signal                           2023-09..2025-11 0.86.5               |.............=====..|
  R   io.github.wanggenlin                 2026-02..2026-10 0.87.7               |..................==|
  ?   com.securegroupchat                  2026-07..2026-07 0.96.3               |..................=.|
org.bytedeco.javacpp.ios.arm64  [fork: keep `org.bytedeco`, `org.apache.tika` still publishes the name]
  A * org.bytedeco                         2020-04..2026-08 1.5.14               |......=============.|
  R   org.apache.tika                      2025-08..2026-10 3.3.3                |.................===|
  ?   io.github.mullerhai                  2026-07..2026-08 1.5.14-GA-1.0        |..................==|
org.teavm.jso  [fork: keep `org.teavm`, `io.github.jarrunner.teavm` still publishes the name]
  A * org.teavm                            2022-04..2026-10 0.16.0               |..........==========|
  ?   io.github.jarrunner.teavm            2026-10..2026-10 0.16.0-jr.1          |...................=|
  R   com.fermyon                          2022-09..2023-02 0.2.8                |...........==.......|
org.teavm.jso.apis  [fork: keep `org.teavm`, `io.github.jarrunner.teavm` still publishes the name]
  A * org.teavm                            2022-04..2026-10 0.16.0               |..........==========|
  ?   io.github.jarrunner.teavm            2026-10..2026-10 0.16.0-jr.1          |...................=|
  R   com.fermyon                          2022-09..2023-02 0.2.8                |...........==.......|
org.apache.arrow.flight.core  [fork: keep `org.apache.arrow`, `io.mishmash.stacks.patches` still publishes the name]
  ? * org.apache.arrow                     2024-04..2026-03 19.0.0               |..............=====.|
  ?   io.mishmash.stacks.patches           2026-03..2026-10 19.0.0-mmio.1.3      |..................==|
org.apache.arrow.format  [fork: keep `org.apache.arrow`, `io.mishmash.stacks.patches` still publishes the name]
  ? * org.apache.arrow                     2024-04..2026-03 19.0.0               |..............=====.|
  ?   io.mishmash.stacks.patches           2026-03..2026-10 19.0.0-mmio.1.3      |..................==|
org.apache.arrow.memory.core  [fork: keep `org.apache.arrow`, `io.mishmash.stacks.patches` still publishes the name]
  ? * org.apache.arrow                     2024-04..2026-03 19.0.0               |..............=====.|
  ?   io.mishmash.stacks.patches           2026-03..2026-10 19.0.0-mmio.1.3      |..................==|
org.apache.arrow.memory.unsafe  [fork: keep `org.apache.arrow`, `io.mishmash.stacks.patches` still publishes the name]
  ? * org.apache.arrow                     2024-04..2026-03 19.0.0               |..............=====.|
  ?   io.mishmash.stacks.patches           2026-03..2026-10 19.0.0-mmio.1.3      |..................==|
org.apache.arrow.vector  [fork: keep `org.apache.arrow`, `io.mishmash.stacks.patches` still publishes the name]
  A * org.apache.arrow                     2024-04..2026-03 19.0.0               |..............=====.|
  ?   io.mishmash.stacks.patches           2026-03..2026-10 19.0.0-mmio.1.3      |..................==|
  ?   io.indextables                       2026-07..2026-07 0.6.0-rc2_spark_4.1.2 |..................=.|
  A   org.apache.pinot                     2025-09..2025-09 1.4.0                |.................=..|
  R   com.salesforce.datacloud             2025-05..2025-08 0.34.0               |................==..|
net.bis5.mattermost4j.core  [fork: keep `net.bis5.mattermost4j`, `io.github.ug23` still publishes the name]
  ? * net.bis5.mattermost4j                2020-02..2023-01 1.0.0-beta.1         |......=======.......|
  ?   io.github.ug23                       2026-10..2026-10 0.25.1               |...................=|
net.bis5.mattermost4j.models  [fork: keep `net.bis5.mattermost4j`, `io.github.ug23` still publishes the name]
  ? * net.bis5.mattermost4j                2020-02..2023-01 1.0.0-beta.1         |......=======.......|
  ?   io.github.ug23                       2026-10..2026-10 0.25.1               |...................=|
org.seleniumhq.selenium.devtools_v151  [fork: keep `org.seleniumhq.selenium`, `com.github.aquality-automation` still publishes the name]
  ? * org.seleniumhq.selenium              2026-08..2026-09 4.49.0               |..................==|
  ?   com.github.aquality-automation       2026-08..2026-09 4.26.0               |...................=|
org.bouncycastle.lts.prov  [fork: keep `org.bouncycastle`, `org.apache.seatunnel` still publishes the name]
  A * org.bouncycastle                     2023-06..2026-09 2.73.13              |............========|
  ?   org.apache.seatunnel                 2026-09..2026-09 3.0.0                |...................=|
  R   org.brylex                           2026-05..2026-05 0.2                  |..................=.|
  R   io.kestra.plugin                     2025-06..2025-08 0.24.0               |................==..|
org.osgi.framework  [fork: keep `org.osgi`, `org.apache.karaf` still publishes the name]
  ? * org.osgi                             2020-12..2020-12 1.10.0               |.......=............|
  ?   org.apache.karaf                     2025-08..2026-09 4.4.12               |................====|
com.microsoft.credentialstorage  [fork: keep `com.microsoft`, `io.alapierre` still publishes the name]
  ? * com.microsoft                        2022-05..2024-09 1.0.3                |..........======....|
  ?   io.alapierre                         2026-09..2026-09 1.0.4                |...................=|
org.locationtech.jts  [fork: keep `org.locationtech.jts`, `org.datasyslab` still publishes the name]
  ? * org.locationtech.jts                 2018-06..2024-08 1.20.0               |...=============....|
  ?   org.datasyslab                       2026-09..2026-09 1.21.0-datasyslab-1  |...................=|
com.dlsc.atlantafx.themes  [fork: keep `com.dlsc.atlantafx`, `io.xpipe` still publishes the name]
  ? * com.dlsc.atlantafx                   2026-05..2026-05 1.9.0                |..................=.|
  ?   io.xpipe                             2026-09..2026-09 1.9.1                |...................=|
org.hsqldb  [fork: keep `org.hsqldb`, `io.synclite` still publishes the name]
  A * org.hsqldb                           2021-04..2024-11 2.7.4                |........========....|
  ?   io.synclite                          2026-07..2026-09 1.1.0                |..................==|
  R   com.github.massamany                 2025-08..2025-09 1.2.3                |.................=..|
  ?   ch.zizka.csvcruncher                 2021-11..2023-09 2.7.0                |.........=====......|
  R   org.lucee                            2023-06..2023-06 2.7.2.jdk11          |............=.......|
org.bytedeco.pytorch  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  ? * org.bytedeco                         2021-08..2026-08 2.13.0-1.5.14        |.........==========.|
  ?   io.github.mullerhai                  2026-07..2026-09 2.13.0-1.5.14-GA-1.10 |..................==|
org.bytedeco.cuda.redist.cublas  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................==.|
  ?   io.github.mullerhai                  2026-08..2026-09 13.3-9.25-1.5.14-GA-1.0 |..................==|
org.bytedeco.cuda.redist.cudnn  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................==.|
  ?   io.github.mullerhai                  2026-08..2026-09 13.3-9.25-1.5.14-GA-1.0 |..................==|
org.bytedeco.cuda.redist.cusparse  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................==.|
  ?   io.github.mullerhai                  2026-08..2026-09 13.3-9.25-1.5.14-GA-1.0 |..................==|
org.bytedeco.cuda.redist.nvcomp  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................==.|
  ?   io.github.mullerhai                  2026-07..2026-09 13.3-9.25-1.5.14-GA-1.0 |..................==|
org.bytedeco.cuda.redist.cusolver  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................==.|
  ?   io.github.mullerhai                  2026-08..2026-09 13.3-9.25-1.5.14-GA-1.0 |..................==|
org.bytedeco.cuda.redist.npp  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................==.|
  ?   io.github.mullerhai                  2026-07..2026-09 13.3-9.25-1.5.14-GA-1.0 |..................==|
org.bytedeco.cuda.redist.nccl  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................==.|
  ?   io.github.mullerhai                  2026-07..2026-09 13.3-9.25-1.5.14-GA-1.0 |..................==|
org.bytedeco.pytorch.linux.x86_64  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  ? * org.bytedeco                         2021-08..2026-08 2.13.0-1.5.14        |.........==========.|
  ?   io.github.mullerhai                  2026-08..2026-09 2.13.0-1.5.14-GA-1.10 |..................==|
org.bytedeco.cuda.redist.nccl.linux.x86_64  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................==.|
  ?   io.github.mullerhai                  2026-08..2026-09 13.3-9.25-1.5.14-GA-1.0 |..................==|
org.bytedeco.cuda.redist.nccl.windows.x86_64  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................==.|
  ?   io.github.mullerhai                  2026-07..2026-09 13.3-9.25-1.5.14-GA-1.0 |..................==|
org.bytedeco.cuda.redist.nvcomp.linux.x86_64  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................==.|
  ?   io.github.mullerhai                  2026-07..2026-09 13.3-9.25-1.5.14-GA-1.0 |..................==|
org.bytedeco.cuda.redist.nvcomp.windows.x86_64  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................==.|
  ?   io.github.mullerhai                  2026-07..2026-09 13.3-9.25-1.5.14-GA-1.0 |..................==|
org.bytedeco.cpython.platform  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  A * org.bytedeco                         2019-04..2026-08 3.14.7-1.5.14        |....===============.|
  ?   io.github.mullerhai                  2026-07..2026-09 3.14.7-1.5.14-GA-1.0 |..................==|
  R   cn.wangshuyi                         2023-10..2023-10 3.10.2-1.5.7         |.............=......|
org.bytedeco.cuda.platform.redist  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  ? * org.bytedeco                         2020-09..2026-08 13.3-9.25-1.5.14     |.......============.|
  ?   io.github.mullerhai                  2026-07..2026-09 13.3-9.25-1.5.14-GA-1.0 |..................==|
org.bytedeco.cuda.redist  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................==.|
  ?   io.github.mullerhai                  2026-07..2026-09 13.3-9.25-1.5.14-GA-1.0 |..................==|
org.bytedeco.cuda  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  A * org.bytedeco                         2019-04..2026-08 13.3-9.25-1.5.14     |....===============.|
  ?   io.github.mullerhai                  2026-07..2026-09 13.3-9.25-1.5.14-GA-1.0 |..................==|
  R   us.ihmc                              2025-01..2025-01 12.6-9.5-1.5.11-ihmc-2 |...............=....|
org.bytedeco.cuda.platform  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  ? * org.bytedeco                         2019-04..2026-08 13.3-9.25-1.5.14     |....===============.|
  ?   io.github.mullerhai                  2026-07..2026-09 13.3-9.25-1.5.14-GA-1.0 |..................==|
org.bytedeco.ffmpeg.platform  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  A * org.bytedeco                         2019-04..2026-08 8.1.2-1.5.14         |....===============.|
  ?   io.github.mullerhai                  2026-07..2026-09 8.1.2-1.5.14-GA-1.0  |..................==|
  R   us.ihmc                              2023-06..2023-06 6.0-1.5.9            |............=.......|
org.bytedeco.ffmpeg  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  A * org.bytedeco                         2019-04..2026-08 8.1.2-1.5.14         |....===============.|
  ?   io.github.mullerhai                  2026-07..2026-09 8.1.2-1.5.14-GA-1.0  |..................==|
  R   us.ihmc                              2023-06..2025-01 7.1-1.5.11-ihmc-2    |............====....|
  R   com.github.javacpp-nogpl             2020-09..2020-09 4.3.1-1.5.4          |.......=............|
org.bytedeco.cpython  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  A * org.bytedeco                         2019-04..2026-08 3.14.7-1.5.14        |....===============.|
  ?   io.github.mullerhai                  2026-07..2026-09 3.14.7-1.5.14-GA-1.0 |..................==|
  R   us.ihmc                              2025-02..2025-02 3.11.3-1.5.11-ihmc-2 |................=...|
  R   cn.wangshuyi                         2023-10..2023-10 3.10.13-1.5.7.2      |.............=......|
org.bytedeco.javacpp.platform  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  A * org.bytedeco                         2020-04..2026-08 1.5.14               |......=============.|
  ?   io.github.mullerhai                  2026-07..2026-09 1.5.14-GA-1.0        |..................==|
  R   org.apache.tika                      2024-07..2024-10 3.0.0                |..............==....|
  R   us.ihmc                              2023-06..2023-06 1.5.9                |............=.......|
org.bytedeco.openblas.platform  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  A * org.bytedeco                         2019-04..2026-08 0.3.34-1.5.14        |....===============.|
  ?   io.github.mullerhai                  2026-07..2026-09 0.3.34-1.5.14-GA-1.0 |..................==|
  R   us.ihmc                              2023-06..2023-06 0.3.23-1.5.9         |............=.......|
  R   de.uni-mannheim.informatik.dws.melt  2022-06..2022-06 3.3                  |..........=.........|
org.bytedeco.numpy  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  ? * org.bytedeco                         2019-04..2026-08 2.5.1-1.5.14         |....===============.|
  ?   io.github.mullerhai                  2026-07..2026-09 2.5.1-1.5.14-GA-1.0  |..................==|
org.bytedeco.numpy.platform  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  ? * org.bytedeco                         2019-04..2026-08 2.5.1-1.5.14         |....===============.|
  ?   io.github.mullerhai                  2026-07..2026-09 2.5.1-1.5.14-GA-1.0  |..................==|
org.bytedeco.javacpp  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  A * org.bytedeco                         2019-04..2026-08 1.5.14               |....===============.|
  ?   io.github.mullerhai                  2026-07..2026-09 1.5.14-GA-1.0        |..................==|
  R   us.ihmc                              2023-06..2025-01 1.5.11-ihmc-2        |............====....|
  R   ai.polus.utils                       2024-06..2024-06 2.0.7                |..............=.....|
  R   com.github.vinhkhuc                  2023-05..2023-05 0.5                  |............=.......|
  R   com.alibaba.alink                    2021-10..2022-10 0.2.0-0.6            |.........===........|
    + 5 more: cn.langpy, org.flinkextended, org.deeplearning4j, org.nd4j, io.github.carschno
org.bytedeco.openblas  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  A * org.bytedeco                         2019-04..2026-08 0.3.34-1.5.14        |....===============.|
  ?   io.github.mullerhai                  2026-07..2026-09 0.3.34-1.5.14-GA-1.0 |..................==|
  R   us.ihmc                              2023-06..2025-02 0.3.23-1.5.11-ihmc-2 |............=====...|
org.bytedeco.tensorrt  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  ? * org.bytedeco                         2019-04..2026-08 11.2-1.5.14          |....===============.|
  ?   io.github.mullerhai                  2026-08..2026-09 11.2-1.5.14-GA-1.0   |..................==|
org.bytedeco.opencv.platform  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  A * org.bytedeco                         2019-04..2026-08 4.14.0-1.5.14        |....===============.|
  ?   io.github.mullerhai                  2026-07..2026-09 4.14.0-1.5.14-GA-1.0 |..................==|
  R   io.github.extractpdf4j               2025-12..2026-07 2.2.0                |.................==.|
  R   io.github.mehulimukherjee            2025-08..2025-09 0.1.1                |.................=..|
  R   us.ihmc                              2023-06..2023-06 4.7.0-1.5.9          |............=.......|
org.bytedeco.opencv  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  A * org.bytedeco                         2019-04..2026-08 4.14.0-1.5.14        |....===============.|
  ?   io.github.mullerhai                  2026-07..2026-09 4.14.0-1.5.14-GA-1.0 |..................==|
  R   us.ihmc                              2023-06..2025-02 4.7.0-1.5.11-ihmc-2  |............=====...|
org.bytedeco.tritonserver  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  ? * org.bytedeco                         2022-02..2026-08 2.71.0-1.5.14        |..........=========.|
  ?   io.github.mullerhai                  2026-08..2026-09 2.71.0-1.5.14-GA-1.0 |..................==|
org.bytedeco.cuda.redist.npp.linux.x86_64  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................==.|
  ?   io.github.mullerhai                  2026-07..2026-09 13.3-9.25-1.5.14-GA-1.0 |..................==|
org.bytedeco.cuda.redist.npp.windows.x86_64  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................==.|
  ?   io.github.mullerhai                  2026-07..2026-09 13.3-9.25-1.5.14-GA-1.0 |..................==|
org.bytedeco.cuda.redist.cusparse.linux.x86_64  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................==.|
  ?   io.github.mullerhai                  2026-08..2026-09 13.3-9.25-1.5.14-GA-1.0 |..................==|
org.bytedeco.cuda.redist.cusparse.windows.x86_64  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................==.|
  ?   io.github.mullerhai                  2026-08..2026-09 13.3-9.25-1.5.14-GA-1.0 |..................==|
org.bytedeco.cuda.redist.cusolver.linux.x86_64  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................==.|
  ?   io.github.mullerhai                  2026-08..2026-09 13.3-9.25-1.5.14-GA-1.0 |..................==|
org.bytedeco.cuda.redist.cusolver.windows.x86_64  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................==.|
  ?   io.github.mullerhai                  2026-08..2026-09 13.3-9.25-1.5.14-GA-1.0 |..................==|
org.bytedeco.cuda.redist.cudnn.linux.x86_64  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................==.|
  ?   io.github.mullerhai                  2026-08..2026-08 13.3-9.25-1.5.14-GA-1.0 |..................==|
org.bytedeco.cuda.redist.cudnn.windows.x86_64  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................==.|
  ?   io.github.mullerhai                  2026-08..2026-08 13.3-9.25-1.5.14-GA-1.0 |..................==|
org.bytedeco.cuda.redist.cublas.linux.x86_64  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................==.|
  ?   io.github.mullerhai                  2026-08..2026-08 13.3-9.25-1.5.14-GA-1.0 |..................==|
org.bytedeco.cuda.redist.cublas.windows.x86_64  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................==.|
  ?   io.github.mullerhai                  2026-08..2026-08 13.3-9.25-1.5.14-GA-1.0 |..................==|
org.bytedeco.cuda.redist.linux.arm64  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................==.|
  ?   io.github.mullerhai                  2026-07..2026-08 13.3-9.25-1.5.14-GA-1.0 |..................==|
org.bytedeco.cuda.redist.linux.x86_64  [fork: keep `org.bytedeco`, `io.github.mullerhai` still publishes the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................==.|
  ?   io.github.mullerhai                  2026-07..2026-08 13.3-9.25-1.5.14-GA-1.0 |..................==|
```

## shaded (144)

The natural-namespace owner is the earliest and most-recent publisher; every other group merely shades or bundles the name. Resolution is unchanged; this just records the decision so the module drops off the report.

- The owner is also the most-recent publisher (there is no later successor).
- The owner is the closest groupId to the module name: it shares the longest leading-segment prefix (hyphens ignored), even if the name is not strictly under it.
- Allow the natural owner; reject every group that merely shades the name.

| count | current owner | new owner(s) |
|---:|---|---|
| 1 | `io.github.pdvrieze.xmlutil` | `io.github.pdvrieze.xmlutil, io.github.pdvrieze` |
| 1 | `org.apache.fory` | `org.apache.fory, org.apache.pinot` |
| 1 | `org.flywaydb` | `org.flywaydb, org.flywaydb.pro, org.flywaydb.enterprise` |
| 1 | `org.junit.jupiter` | `org.junit.jupiter, org.junit.platform` |
| 1 | `org.neo4j` | `org.neo4j, org.neo4j.connectors` |
| 1 | `org.neo4j.bolt` | `org.neo4j.bolt, org.neo4j.connectors` |
| 1 | `org.opendaylight.mdsal` | `org.opendaylight.mdsal, org.opendaylight.netconf` |
| 1 | `org.opendaylight.yangtools` | `org.opendaylight.yangtools, org.opendaylight.netconf` |

```
com.graphqljava  [owned by `com.graphql-java`; 3 other group(s) shade the name]
  A * com.graphql-java                     2020-11..2026-11 0.0.0-2026-11-17T04-39-14-a2fd5fe |.......=============|
  ?   com.guicedee.modules.services        2026-07..2026-11 2.5.1                |..................==|
  R   com.liferay                          2025-05..2025-05 19.11.JAKARTA-LIFERAY-PATCHED-1 |................=...|
  R   io.github.my-workforce               2022-07..2023-07 19.6                 |...........===......|
org.junit.jupiter.engine  [owned by `org.junit.jupiter`; 10 other group(s) shade the name]
  A * org.junit.jupiter                    2017-07..2026-11 6.2.0-M1             |.===================|
  ?   io.github.rpost                      2026-07..2026-07 6.0.0-M2             |..................=.|
  R   org.apache.tika                      2024-07..2026-06 4.0.0-beta-1         |..............=====.|
  R   com.janeluo                          2026-04..2026-04 1.0.0                |..................=.|
  R   org.eclipse.pass                     2023-06..2024-01 1.3.0                |............===.....|
  R   com.quantego                         2023-11..2023-11 0.6.5                |.............=......|
    + 6 more: com.salesforce.kafka.test, com.trendyol, org.caseine, org.eclipse.rdf4j, com.github.jnr, org.junit.platform
org.junit.platform.launcher  [owned by `org.junit.platform`; 5 other group(s) shade the name]
  A * org.junit.platform                   2017-07..2026-11 6.2.0-M1             |.===================|
  ?   org.apache.tika                      2026-10..2026-10 4.2.0                |...................=|
  R   be.vlaanderen.informatievlaanderen.ldes.ldio 2024-12..2024-12 2.12.0               |...............=....|
  R   com.yascribe                         2023-10..2023-10 1.0.1                |.............=......|
  R   de.gematik                           2021-03..2021-07 0.9.0                |........==..........|
  R   org.epics                            2020-06..2020-06 1.3.2                |.......=............|
org.junit.jupiter.params  [owned by `org.junit.jupiter`; 2 other group(s) shade the name]
  ? * org.junit.jupiter                    2017-07..2026-11 6.2.0-M1             |.===================|
  ?   org.uma.jmetal                       2026-09..2026-11 2.6                  |...................=|
  ?   io.github.lightdataframe             2026-09..2026-10 1.1.0                |...................=|
org.hibernate.orm.community.dialects  [owned by `org.hibernate.orm`; 1 other group(s) shade the name]
  ? * org.hibernate.orm                    2022-05..2026-11 7.4.16.Final         |..........==========|
  ?   io.github.martinhickson              2026-08..2026-08 6.6.7-bravura-1      |...................=|
org.hibernate.orm.vector  [owned by `org.hibernate.orm`; 1 other group(s) shade the name]
  ? * org.hibernate.orm                    2023-11..2026-11 7.4.16.Final         |.............=======|
  ?   io.github.martinhickson              2026-08..2026-08 6.6.7-bravura-1      |...................=|
org.hibernate.orm.ant  [owned by `org.hibernate.orm`; 1 other group(s) shade the name]
  ? * org.hibernate.orm                    2021-10..2026-11 7.4.16.Final         |.........===========|
  ?   io.github.martinhickson              2026-08..2026-08 6.6.7-bravura-1      |...................=|
org.hibernate.orm.jfr  [owned by `org.hibernate.orm`; 1 other group(s) shade the name]
  ? * org.hibernate.orm                    2023-11..2026-11 7.4.16.Final         |.............=======|
  ?   io.github.martinhickson              2026-08..2026-08 6.6.7-bravura-1      |...................=|
ch.qos.logback.core  [owned by `ch.qos.logback`; 35 other group(s) shade the name]
  A * ch.qos.logback                       2018-01..2026-11 1.6.7                |..==================|
  R   org.ton.ton4j                        2025-11..2026-10 2.2.0                |.................===|
  R   com.effacy.jui                       2024-12..2026-10 0.4.4                |...............=====|
  R   com.deltaproto                       2026-04..2026-09 1.7.0                |..................==|
  ?   ch.exense.step                       2026-07..2026-08 3.30.3               |..................==|
  R   de.gematik.test                      2025-11..2026-08 4.4.2                |.................===|
    + 30 more: io.camunda, club.dawdler, com.yetanalytics, net.ladenthin, org.eclipse.hawkbit, io.smallrye.reactive, org.springframework.cloud, com.limemojito.oss.aws, io.spicelabs, com.expediagroup, org.chenile, org.jetbrains.kotlinx, (+18 more)
com.github.luben.zstd_jni  [owned by `com.github.luben`; 23 other group(s) shade the name]
  A * com.github.luben                     2018-06..2026-11 1.5.7-28             |...=================|
  ?   cc.xiangshan                         2026-11..2026-11 2.2.0                |...................=|
  ?   ru.playa.keycloak                    2026-11..2026-11 26.8.2.1             |...................=|
  R   org.apache.amoro                     2025-08..2026-11 0.9.0-incubating     |.................===|
  R   com.snowflake                        2024-12..2026-10 3.5.6                |...............=====|
  R   org.apache.iotdb                     2024-11..2026-10 2.0.11               |...............=====|
    + 18 more: com.scylladb, org.apache.tsfile, com.lucasluqui, io.github.kpn-dsh, com.timecho.timechodb, ai.h2o, org.apache.celeborn, com.aliyun.openservices.eas, com.timecho.iotdb, org.chipsalliance, io.moderne, io.spicelabs, (+6 more)
com.clickhouse.jdbc  [owned by `com.clickhouse`; 4 other group(s) shade the name]
  A * com.clickhouse                       2021-12..2026-11 0.11.2               |.........===========|
  ?   io.github.tridog                     2026-07..2026-07 0.7.3-2              |..................=.|
  R   org.apache.seatunnel                 2023-10..2024-10 1.0.2                |.............===....|
  R   io.kestra.plugin                     2022-04..2023-03 0.6.1                |..........===.......|
  R   ru.yandex.clickhouse                 2021-12..2021-12 0.3.2                |..........=.........|
com.microsoft.onnxruntime  [owned by `com.microsoft.onnxruntime`; 1 other group(s) shade the name]
  ? * com.microsoft.onnxruntime            2020-06..2026-11 1.32.0               |.......=============|
  ?   io.github.eduramiba                  2026-06..2026-06 1.26.0               |..................=.|
org.hibernate.orm.ucp  [owned by `org.hibernate.orm`; 1 other group(s) shade the name]
  ? * org.hibernate.orm                    2024-03..2026-11 8.0.0.CR1            |..............======|
  ?   io.github.martinhickson              2026-08..2026-08 6.6.7-bravura-1      |...................=|
org.flywaydb.core  [owned by `org.flywaydb`; 4 other group(s) shade the name]
  A * org.flywaydb                         2017-12..2026-11 13.12.2              |..==================|
  ?   io.github.wu9007                     2026-08..2026-08 9.9.9                |...................=|
  R   io.github.coolbeevip                 2023-03..2026-01 9.15.2.5             |............======..|
  R   io.gitee.gbase8s                     2025-01..2025-01 6.5.7                |...............=....|
  A   org.flywaydb.enterprise              2020-04..2022-07 9.0.0                |......======........|
  R   io.github.linceln                    2021-07..2021-08 5.0.8                |.........=..........|
    + 1 more: org.flywaydb.pro
org.jline.terminal.jni  [owned by `org.jline`; 1 other group(s) shade the name]
  ? * org.jline                            2023-10..2026-11 4.7.0                |.............=======|
  ?   fish.payara.extras                   2026-08..2026-11 7.2026.11            |...................=|
org.bouncycastle.provider  [owned by `org.bouncycastle`; 89 other group(s) shade the name]
  A * org.bouncycastle                     2018-07..2026-11 1.87                 |...=================|
  R   org.terracotta                       2022-02..2026-11 3.3.50               |..........==========|
  R   io.takamaka.crypto                   2022-11..2026-11 1.0.1                |...........=========|
  R   org.openeuler                        2021-11..2026-11 2.0.5                |.........===========|
  ?   io.gitee.maluole                     2026-06..2026-10 1.4.0.RELEASE        |..................==|
  R   org.apache.pinot                     2025-02..2026-10 1.6.0                |................====|
    + 84 more: org.apache.seatunnel, org.apache.knox, net.maritimeconnectivity.pki, org.apache.dolphinscheduler, org.wso2.charon, org.dcache, org.exploit, de.moritzpetersen, de.splatgames.aether.pack, io.github.swiyu-admin-ch, org.hyperledger.fabric, io.kestra.storage, (+72 more)
org.bouncycastle.pkix  [owned by `org.bouncycastle`; 63 other group(s) shade the name]
  A * org.bouncycastle                     2018-07..2026-11 1.87                 |...=================|
  R   org.jetbrains                        2021-03..2026-10 0.1.45               |........============|
  R   de.trustable.cmp.client              2020-07..2026-10 3.2.4                |.......=============|
  R   com.exasol                           2024-03..2026-10 26.2.10              |..............======|
  R   net.maritimeconnectivity.pki         2020-10..2026-09 1.4.4                |.......=============|
  R   com.github.toolarium                 2023-12..2026-09 1.2.9                |.............=======|
    + 58 more: org.apache.inlong, com.alibaba.ververica, com.datarobot, com.github.melin, de.tk.opensource, org.apache.pinot, io.streamnative.connectors, org.hyperledger.fabric, org.finos.legend.engine, io.kestra.plugin, org.lucee, com.nhn.gameanvil, (+46 more)
com.gizmodata.quack.jdbc  [owned by `com.gizmodata`; 1 other group(s) shade the name]
  ? * com.gizmodata                        2026-05..2026-11 1.0.1                |..................==|
  ?   dev.brikk.duckdb                     2026-07..2026-11 0.14.0               |..................==|
com.qoder.sdk  [owned by `com.qoder.sdk`; 1 other group(s) shade the name]
  ? * com.qoder.sdk                        2026-10..2026-11 0.2.4                |...................=|
  ?   cn.qoder.sdk                         2026-10..2026-11 0.2.4                |...................=|
org.apache.tomcat.embed.core  [owned by `org.apache.tomcat.embed`; 11 other group(s) shade the name]
  A * org.apache.tomcat.embed              2019-09..2026-11 10.1.62              |.....===============|
  ?   org.tinystruct                       2026-10..2026-11 1.1.12               |...................=|
  R   com.heroku                           2020-09..2026-10 10.1.60.0            |.......=============|
  R   io.github.haiphamcoder               2025-09..2026-03 1.0.8                |.................==.|
  R   io.github.huangchengsir              2024-11..2026-02 1.0.5                |...............====.|
  R   io.github.typecoh                    2025-07..2025-07 1.0.2                |................=...|
    + 6 more: com.talenteca, io.github.crac.org.apache.tomcat.embed, com.codedx, de.gematik.test, su.boleyn.oj, org.ops4j.pax.tipi
org.eclipse.jetty.http  [owned by `org.eclipse.jetty`; 2 other group(s) shade the name]
  ? * org.eclipse.jetty                    2018-11..2026-11 12.0.41              |...=================|
  ?   io.debezium                          2026-11..2026-11 3.8.0.Alpha1         |...................=|
  ?   com.olexyn.min.http.server           2026-10..2026-10 0.3.0                |...................=|
org.eclipse.jetty.security  [owned by `org.eclipse.jetty`; 4 other group(s) shade the name]
  A * org.eclipse.jetty                    2018-11..2026-11 12.0.41              |...=================|
  ?   io.debezium                          2026-07..2026-11 3.7.1.Final          |..................==|
  R   org.sonatype.nexus.common.components 2026-02..2026-09 3.95.4-01            |.................===|
  ?   ch.exense.step                       2026-06..2026-07 3.30.1               |..................=.|
  R   org.sonatype.nexus.jetty             2025-09..2026-01 3.87.2-01            |.................=..|
org.eclipse.jetty.xml  [owned by `org.eclipse.jetty`; 1 other group(s) shade the name]
  ? * org.eclipse.jetty                    2018-11..2026-11 12.0.41              |...=================|
  ?   org.apache.tika                      2026-10..2026-10 3.3.3                |...................=|
org.eclipse.jetty.client  [owned by `org.eclipse.jetty`; 2 other group(s) shade the name]
  A * org.eclipse.jetty                    2018-11..2026-11 12.0.41              |...=================|
  ?   ch.exense.step                       2026-06..2026-07 3.30.1               |..................=.|
  R   org.exploit                          2024-10..2026-04 1.0.9                |...............====.|
org.eclipse.jetty.http2.common  [owned by `org.eclipse.jetty.http2`; 1 other group(s) shade the name]
  ? * org.eclipse.jetty.http2              2018-11..2026-11 12.0.41              |...=================|
  ?   com.yahoo.vespa                      2026-10..2026-10 8.763.13             |...................=|
org.eclipse.jetty.compression.gzip  [owned by `org.eclipse.jetty.compression`; 1 other group(s) shade the name]
  ? * org.eclipse.jetty.compression        2024-12..2026-11 12.1.15              |...............=====|
  ?   ch.exense.step                       2026-08..2026-08 3.30.3               |..................==|
org.apache.fory.core  [owned by `org.apache.fory`; 0 other group(s) shade the name]
  ? * org.apache.fory                      2025-06..2026-11 1.7.8                |................====|
  ?   org.apache.pinot                     2026-10..2026-10 1.6.0                |...................=|
org.neo4j.cypherdsl.core  [owned by `org.neo4j`; 0 other group(s) shade the name]
  ? * org.neo4j                            2020-07..2026-11 2025.3.6             |.......=============|
  ?   org.neo4j.connectors                 2026-06..2026-07 6.0.0-s_2.13         |..................=.|
dev.tamboui.toolkit  [owned by `dev.tamboui`; 2 other group(s) shade the name]
  ? * dev.tamboui                          2026-02..2026-11 0.7.0                |..................==|
  ?   dev.jbang                            2026-07..2026-10 0.143.0              |..................==|
  ?   com.steeplesoft                      2026-08..2026-08 1.0.0                |...................=|
dev.tamboui.widgets  [owned by `dev.tamboui`; 1 other group(s) shade the name]
  ? * dev.tamboui                          2026-02..2026-11 0.7.0                |..................==|
  ?   com.github.jlangch                   2026-06..2026-10 1.13.14              |..................==|
tools.jackson.dataformat.yaml  [owned by `tools.jackson.dataformat`; 2 other group(s) shade the name]
  A * tools.jackson.dataformat             2025-03..2026-11 3.3.0                |................====|
  ?   com.jordansamhi                      2026-10..2026-10 2.0.0                |...................=|
  R   de.fraunhofer.sit.sse.flowdroid      2026-02..2026-02 2.15.1               |..................=.|
tools.jackson.databind  [owned by `tools.jackson.core`; 4 other group(s) shade the name]
  A * tools.jackson.core                   2025-03..2026-11 3.3.0                |................====|
  ?   de.codelix.commandapi                2026-08..2026-08 4.4.0                |...................=|
  R   com.smartystreets.api                2026-01..2026-07 7.2.0                |.................==.|
  R   software.xdev.mockserver             2026-03..2026-03 2.0.4                |..................=.|
  R   io.github.koinsaari                  2025-11..2025-11 0.1.0                |.................=..|
com.fasterxml.jackson.jaxrs.json  [owned by `com.fasterxml.jackson.jaxrs`; 60 other group(s) shade the name]
  A * com.fasterxml.jackson.jaxrs          2017-10..2026-11 2.23.0               |.===================|
  R   org.lance                            2025-12..2026-10 0.9.0-beta.2         |.................===|
  R   org.apache.pinot                     2024-08..2026-10 1.6.0                |...............=====|
  R   com.ascentstream.pulsar              2026-05..2026-10 4.0.14.0             |..................==|
  R   org.onebusaway                       2024-11..2026-10 2.7.2                |...............=====|
  R   com.alibaba.ververica                2022-10..2026-10 1.20-vvr-11.9.0-jdk11 |...........=========|
    + 55 more: org.apache.pulsar, org.apache.flink, org.apache.hudi, org.apache.seatunnel, ru.moysklad.api, org.devlive.connector, io.simpleishard, ai.askamerica, org.apache.phoenix, org.apache.hbase.thirdparty, dev.henneberger, io.github.giis-uniovi, (+43 more)
com.fasterxml.jackson.datatype.joda  [owned by `com.fasterxml.jackson.datatype`; 4 other group(s) shade the name]
  A * com.fasterxml.jackson.datatype       2017-10..2026-11 2.23.0               |.===================|
  R   io.kestra.plugin                     2024-06..2024-08 0.18.1               |..............==....|
  R   org.apache.beam                      2022-05..2023-05 2.48.0               |..........===.......|
  ?   io.siddhi                            2022-11..2023-02 5.1.28               |...........==.......|
  R   com.seeq                             2021-12..2022-08 55.4.9-v202208021422 |.........===........|
com.fasterxml.jackson.datatype.jdk8  [owned by `com.fasterxml.jackson.datatype`; 40 other group(s) shade the name]
  A * com.fasterxml.jackson.datatype       2017-10..2026-11 2.23.0               |.===================|
  R   com.networknt                        2022-08..2026-11 2.5.0                |...........=========|
  ?   org.sonarsource.sonar-lits-plugin    2026-11..2026-11 0.13.0.16223         |...................=|
  ?   io.github.yuku123                    2026-10..2026-10 1.0.50               |...................=|
  ?   sh.oso                               2026-10..2026-10 0.1.6                |...................=|
  R   io.github.unmeshjoshi                2025-10..2026-10 0.1.0-beta.4         |.................===|
    + 35 more: org.octopusden.octopus.cvevalidationservice, org.octopusden.octopus.automation.cve, org.ic4j, org.apache.grails, io.github.tansuasici, ai.onehouse, com.atlan, io.github.demiourgoi, io.kestra.plugin, com.tencent.cloud, hu.webarticum.miniconnect, io.openlineage, (+23 more)
com.fasterxml.jackson.module.paramnames  [owned by `com.fasterxml.jackson.module`; 11 other group(s) shade the name]
  A * com.fasterxml.jackson.module         2017-10..2026-11 2.23.0               |.===================|
  R   com.infobip                          2026-03..2026-08 4.0.0                |..................==|
  ?   org.realityforge.proton              2026-06..2026-07 0.74                 |..................=.|
  ?   org.realityforge.sting               2026-06..2026-06 0.39                 |..................=.|
  ?   org.realityforge.router.fu           2026-06..2026-06 0.47                 |..................=.|
  ?   org.realityforge.react4j             2026-06..2026-06 0.226                |..................=.|
    + 6 more: org.realityforge.arez, io.kestra, com.araksis, com.araksis.sjd, io.github.codgen, io.micronaut.example
com.fasterxml.jackson.dataformat.toml  [owned by `com.fasterxml.jackson.dataformat`; 7 other group(s) shade the name]
  A * com.fasterxml.jackson.dataformat     2021-04..2026-11 2.23.0               |........============|
  ?   io.github.leasegate                  2026-10..2026-11 0.3                  |...................=|
  R   org.monarchinitiative.vitt           2026-01..2026-01 0.1.0                |.................=..|
  R   com.github.jobservice                2024-02..2024-02 7.0.0-334            |..............=.....|
  R   com.github.cafapi.util.flywayinstaller 2024-02..2024-02 2.0.0-184            |..............=.....|
  R   com.cefoler.configuration            2022-04..2022-05 1.2.4                |..........=.........|
    + 2 more: dev.madetobuild.typedconfig, com.github.nanodeath
org.apache.bcel  [owned by `org.apache.bcel`; 5 other group(s) shade the name]
  A * org.apache.bcel                      2017-09..2026-11 6.14.0               |.===================|
  ?   io.spicelabs                         2026-08..2026-08 0.17.4               |..................=.|
  R   com.github.spotbugs                  2024-10..2026-07 4.7.0                |...............====.|
  R   com.github.kwart.jd                  2025-10..2025-10 1.3.0-beta-1         |.................=..|
  R   org.checkerframework.annotatedlib    2018-02..2020-10 6.5.0                |..======............|
  R   org.checkerframework                 2018-02..2018-02 0.0.1                |..=.................|
io.opentelemetry.api  [owned by `io.opentelemetry`; 1 other group(s) shade the name]
  ? * io.opentelemetry                     2020-03..2026-11 1.68.0               |......==============|
  ?   io.vidocq.humboldt                   2026-06..2026-11 0.4.0-RC2            |..................==|
io.opentelemetry.context  [owned by `io.opentelemetry`; 1 other group(s) shade the name]
  ? * io.opentelemetry                     2020-11..2026-11 1.68.0               |.......=============|
  ?   io.vidocq.humboldt                   2026-06..2026-11 0.4.0-RC2            |..................==|
jakarta.json  [owned by `jakarta.json`; 14 other group(s) shade the name]
  A * jakarta.json                         2020-01..2026-11 2.2.0-M1             |......==============|
  R   com.arangodb                         2025-08..2026-09 1.10.0               |.................===|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |..................=.|
  R   org.eclipse.parsson                  2021-06..2026-05 1.1.9                |........===========.|
  R   io.github.qudtlib                    2026-02..2026-02 7.2.0                |..................=.|
  R   io.quarkus                           2024-10..2025-02 3.18.4               |...............==...|
    + 9 more: org.openpreservation.jhove, zone.cogni.semanticz, com.exasol, io.github.changebooks, com.atomgraph.etl.csv, org.avaje.experiment, org.spdx, org.glassfish, com.mparticle
com.azure.storage.common  [owned by `com.azure`; 2 other group(s) shade the name]
  A * com.azure                            2019-09..2026-11 12.37.0-beta.1       |.....===============|
  R   org.gaul                             2025-11..2026-07 3.3.0                |.................==.|
  ?   org.tomitribe.s3proxy                2026-06..2026-06 3.0.1                |..................=.|
org.eclipse.jgit.pgm  [owned by `org.eclipse.jgit`; 1 other group(s) shade the name]
  ? * org.eclipse.jgit                     2017-12..2026-11 7.8.1.202611041320-r |..==================|
  ?   org.openl.jgit                       2022-01..2023-12 6.8.0.202311291450-openl |..........====......|
org.apache.maven.resolver.spi  [owned by `org.apache.maven.resolver`; 1 other group(s) shade the name]
  ? * org.apache.maven.resolver            2017-06..2026-11 2.0.25               |.===================|
  ?   org.jboss.pnc.maven-manipulator      2026-10..2026-10 5.10                 |...................=|
com.carrotsearch.hppc  [owned by `com.carrotsearch`; 2 other group(s) shade the name]
  A * com.carrotsearch                     2020-12..2026-11 0.13.0               |........============|
  ?   io.github.jarrunner.teavm            2026-10..2026-10 0.16.0-jr.1          |...................=|
  R   org.teavm                            2024-12..2026-10 0.16.0               |...............=====|
de.agilecoders.wicket.webjars  [owned by `de.agilecoders.wicket.webjars`; 1 other group(s) shade the name]
  ? * de.agilecoders.wicket.webjars        2023-10..2026-11 5.0.0                |.............=======|
  ?   io.github.arieslab                   2026-06..2026-06 2.4.2                |..................=.|
org.graalvm.shadowed.xz  [owned by `org.graalvm.shadowed`; 1 other group(s) shade the name]
  ? * org.graalvm.shadowed                 2024-03..2026-11 25.5.5.0.3           |..............======|
  ?   io.github.sfali23                    2026-10..2026-10 0.6.0                |...................=|
io.vertx.web.common  [owned by `io.vertx`; 1 other group(s) shade the name]
  ? * io.vertx                             2020-05..2026-11 4.5.36               |......==============|
  ?   io.quarkus                           2026-09..2026-09 4.0.0.Beta1          |...................=|
io.vertx.auth.common  [owned by `io.vertx`; 1 other group(s) shade the name]
  ? * io.vertx                             2020-05..2026-11 4.5.36               |......==============|
  ?   ai.tock                              2026-07..2026-11 26.9.1               |..................==|
io.vertx.circuitbreaker  [owned by `io.vertx`; 1 other group(s) shade the name]
  ? * io.vertx                             2020-05..2026-11 4.5.36               |......==============|
  ?   io.github.the-infinite               2026-07..2026-09 1.0.53               |..................==|
io.vertx.core.logging  [owned by `io.vertx`; 1 other group(s) shade the name]
  ? * io.vertx                             2024-11..2026-11 5.2.3                |...............=====|
  ?   io.quarkus                           2026-10..2026-10 4.0.0.Beta2          |...................=|
io.vertx.codegen.api  [owned by `io.vertx`; 1 other group(s) shade the name]
  ? * io.vertx                             2024-11..2026-11 5.2.3                |...............=====|
  ?   io.smallrye.reactive                 2026-09..2026-09 4.0.2                |...................=|
io.netty.transport.unix.common  [owned by `io.netty`; 1 other group(s) shade the name]
  ? * io.netty                             2017-12..2026-11 4.1.140.Final        |..==================|
  ?   org.reactivemongo                    2026-08..2026-10 1.1.0-RC22           |..................==|
io.netty.common  [owned by `io.netty`; 10 other group(s) shade the name]
  A * io.netty                             2017-12..2026-11 4.1.140.Final        |..==================|
  R   org.frankframework                   2026-04..2026-10 10.2.1               |..................==|
  R   org.opendaylight.netconf             2026-04..2026-10 12.0.2               |..................==|
  ?   io.gravitee.singularitee             2026-08..2026-09 1.6.0                |..................==|
  R   io.quarkiverse.roq                   2026-04..2026-04 2.1.0.CR1            |..................=.|
  R   com.scylladb                         2025-12..2026-03 1.3.11               |.................==.|
    + 5 more: org.apache.arrow, com.github.tonivade, de.codecentric, com.google.cloud, software.amazon.jdbc
io.netty.codec.unused  [owned by `io.netty`; 6 other group(s) shade the name]
  A * io.netty                             2024-10..2026-11 4.2.20.Final         |...............=====|
  ?   com.github.marks-yag                 2026-09..2026-11 0.31.0               |...................=|
  ?   io.github.oewntk                     2026-09..2026-09 3.0.2                |...................=|
  R   io.acryl                             2026-05..2026-07 1.6.0.16rc7          |..................=.|
  R   com.arcadedb                         2025-07..2026-02 26.2.2               |................===.|
  R   org.apache.ignite                    2025-10..2025-10 3.1.0                |.................=..|
    + 1 more: de.bwaldvogel
org.htmlunit  [owned by `org.htmlunit`; 1 other group(s) shade the name]
  ? * org.htmlunit                         2026-05..2026-11 5.7.0                |..................==|
  ?   com.nordstrom.ui-tools               2026-07..2026-07 4.46.0               |..................=.|
org.htmlunit.corejs  [owned by `org.htmlunit`; 1 other group(s) shade the name]
  ? * org.htmlunit                         2026-05..2026-11 5.7.0                |..................==|
  ?   com.nordstrom.ui-tools               2026-10..2026-10 4.51.0               |...................=|
org.htmlunit.websocket.client  [owned by `org.htmlunit`; 1 other group(s) shade the name]
  ? * org.htmlunit                         2026-05..2026-11 5.7.0                |..................==|
  ?   org.wetator                          2026-06..2026-06 5.2.0                |..................=.|
org.htmlunit.cssparser  [owned by `org.htmlunit`; 2 other group(s) shade the name]
  ? * org.htmlunit                         2026-05..2026-11 5.7.0                |..................==|
  ?   com.nordstrom.ui-tools               2026-09..2026-09 4.48.0               |...................=|
  ?   org.seleniumhq.selenium              2026-07..2026-07 4.46.0               |..................=.|
com.apollographql.apollo.tooling  [owned by `com.apollographql.apollo`; 1 other group(s) shade the name]
  ? * com.apollographql.apollo             2024-07..2026-10 4.4.4                |..............======|
  ?   no.beint.apollo                      2026-09..2026-09 5.1.0-beint.2        |...................=|
com.apollographql.apollo.runtime  [owned by `com.apollographql.apollo`; 1 other group(s) shade the name]
  ? * com.apollographql.apollo             2024-07..2026-10 4.4.4                |..............======|
  ?   no.beint.apollo                      2026-09..2026-09 5.1.0-beint.2        |...................=|
com.apollographql.apollo.cache.normalized.api  [owned by `com.apollographql.apollo`; 1 other group(s) shade the name]
  ? * com.apollographql.apollo             2024-07..2026-10 4.4.4                |..............======|
  ?   no.beint.apollo                      2026-09..2026-09 5.1.0-beint.2        |...................=|
com.apollographql.apollo.mpp  [owned by `com.apollographql.apollo`; 1 other group(s) shade the name]
  ? * com.apollographql.apollo             2024-07..2026-10 4.4.4                |..............======|
  ?   no.beint.apollo                      2026-09..2026-09 5.1.0-beint.2        |...................=|
com.apollographql.apollo.gradle  [owned by `com.apollographql.apollo`; 1 other group(s) shade the name]
  ? * com.apollographql.apollo             2024-07..2026-10 4.4.4                |..............======|
  ?   no.beint.apollo                      2026-09..2026-09 5.1.0-beint.2        |...................=|
com.apollographql.apollo.ast  [owned by `com.apollographql.apollo`; 1 other group(s) shade the name]
  ? * com.apollographql.apollo             2024-07..2026-10 4.4.4                |..............======|
  ?   no.beint.apollo                      2026-09..2026-09 5.1.0-beint.2        |...................=|
com.apollographql.apollo.compiler  [owned by `com.apollographql.apollo`; 1 other group(s) shade the name]
  ? * com.apollographql.apollo             2024-07..2026-10 4.4.4                |..............======|
  ?   no.beint.apollo                      2026-09..2026-09 5.1.0-beint.2        |...................=|
com.apollographql.apollo.api  [owned by `com.apollographql.apollo`; 1 other group(s) shade the name]
  ? * com.apollographql.apollo             2024-07..2026-10 4.4.4                |..............======|
  ?   no.beint.apollo                      2026-09..2026-09 5.1.0-beint.2        |...................=|
com.apollographql.apollo.annotations  [owned by `com.apollographql.apollo`; 1 other group(s) shade the name]
  ? * com.apollographql.apollo             2024-07..2026-10 4.4.4                |..............======|
  ?   no.beint.apollo                      2026-09..2026-09 5.1.0-beint.2        |...................=|
org.seleniumhq.selenium.api  [owned by `org.seleniumhq.selenium`; 3 other group(s) shade the name]
  A * org.seleniumhq.selenium              2019-09..2026-10 4.52.0               |.....===============|
  ?   com.github.aquality-automation       2026-08..2026-08 7.5.0                |...................=|
  R   com.github.saikrishna321             2023-12..2024-01 14.0.4               |.............=......|
  R   ru.sbtqa.tag.pagefactory             2022-09..2023-08 21.0.1               |...........===......|
org.seleniumhq.selenium.devtools_latest  [owned by `org.seleniumhq.selenium`; 1 other group(s) shade the name]
  ? * org.seleniumhq.selenium              2026-06..2026-10 4.52.0               |..................==|
  ?   com.github.aquality-automation       2026-10..2026-10 5.0.1                |...................=|
org.seleniumhq.selenium.grid  [owned by `org.seleniumhq.selenium`; 1 other group(s) shade the name]
  ? * org.seleniumhq.selenium              2019-09..2026-10 4.52.0               |.....===============|
  ?   com.infotel.seleniumRobot            2022-09..2022-09 5.0.4                |...........=........|
com.github.jknack.handlebars  [owned by `com.github.jknack`; 3 other group(s) shade the name]
  A * com.github.jknack                    2024-03..2026-10 4.5.8                |..............======|
  R   org.craftercms                       2026-04..2026-10 4.7.10               |..................==|
  R   me.bechberger                        2026-01..2026-08 0.1.2                |.................===|
  ?   org.openidentityplatform.openidm.tools 2026-07..2026-07 7.1.2                |..................=.|
org.mozilla.rhino  [owned by `org.mozilla`; 5 other group(s) shade the name]
  A * org.mozilla                          2021-11..2026-10 1.9.2                |.........===========|
  R   org.mock-server                      2026-05..2026-10 9.2.0                |..................==|
  ?   io.github.jarrunner.teavm            2026-10..2026-10 0.16.0-jr.1          |...................=|
  ?   org.teavm                            2026-10..2026-10 0.16.0               |...................=|
  R   org.bidib.com.github.markusbernhardt 2026-02..2026-02 2.0.0                |.................=..|
  R   org.meeuw                            2025-12..2025-12 1.0                  |.................=..|
org.springdoc.openapi.common  [owned by `org.springdoc`; 2 other group(s) shade the name]
  A * org.springdoc                        2020-12..2026-10 2.10.0               |.......=============|
  ?   io.github.vpelikh                    2026-06..2026-08 5.1.0                |..................==|
  R   io.github.lisi9988                   2025-07..2025-12 2.8.14               |................==..|
org.springdoc.openapi.webflux.core  [owned by `org.springdoc`; 2 other group(s) shade the name]
  A * org.springdoc                        2020-12..2026-10 2.10.0               |.......=============|
  ?   io.github.vpelikh                    2026-06..2026-08 5.1.0                |..................==|
  R   io.github.lisi9988                   2025-08..2025-12 2.8.14               |.................=..|
org.springdoc.openapi.webflux.scalar  [owned by `org.springdoc`; 2 other group(s) shade the name]
  A * org.springdoc                        2025-09..2026-10 2.10.0               |.................===|
  ?   io.github.vpelikh                    2026-06..2026-08 5.1.0                |..................==|
  R   io.github.lisi9988                   2025-12..2025-12 2.8.14               |.................=..|
org.springdoc.openapi.webflux.ui  [owned by `org.springdoc`; 2 other group(s) shade the name]
  A * org.springdoc                        2020-12..2026-10 2.10.0               |.......=============|
  ?   io.github.vpelikh                    2026-06..2026-08 5.1.0                |..................==|
  R   io.github.lisi9988                   2025-08..2025-12 2.8.14               |.................=..|
org.springdoc.openapi.webmvc.core  [owned by `org.springdoc`; 2 other group(s) shade the name]
  A * org.springdoc                        2020-12..2026-10 2.10.0               |.......=============|
  ?   io.github.vpelikh                    2026-06..2026-08 5.1.0                |..................==|
  R   io.github.lisi9988                   2025-07..2025-12 2.8.14               |................==..|
org.springdoc.openapi.webmvc.scalar  [owned by `org.springdoc`; 2 other group(s) shade the name]
  A * org.springdoc                        2025-09..2026-10 2.10.0               |.................===|
  ?   io.github.vpelikh                    2026-06..2026-08 5.1.0                |..................==|
  R   io.github.lisi9988                   2025-12..2025-12 2.8.14               |.................=..|
org.springdoc.openapi.ai.common  [owned by `org.springdoc`; 1 other group(s) shade the name]
  ? * org.springdoc                        2026-04..2026-10 3.2.0                |..................==|
  ?   io.github.vpelikh                    2026-06..2026-08 5.1.0                |..................==|
org.springdoc.openapi.webflux.ai  [owned by `org.springdoc`; 1 other group(s) shade the name]
  ? * org.springdoc                        2026-04..2026-10 3.2.0                |..................==|
  ?   io.github.vpelikh                    2026-06..2026-08 5.1.0                |..................==|
org.springdoc.openapi.webmvc.ai  [owned by `org.springdoc`; 1 other group(s) shade the name]
  ? * org.springdoc                        2026-04..2026-10 3.2.0                |..................==|
  ?   io.github.vpelikh                    2026-06..2026-08 5.1.0                |..................==|
com.aayushatharva.brotli4j  [owned by `com.aayushatharva.brotli4j`; 2 other group(s) shade the name]
  A * com.aayushatharva.brotli4j           2022-12..2026-10 1.24.0               |...........=========|
  ?   net.sourceforge.plantuml             2026-06..2026-06 1.2026.6             |..................=.|
  R   org.apache.orc                       2024-11..2026-01 2.1.4                |...............===..|
org.hibernate.orm.enhance.maven.plugin  [owned by `org.hibernate.orm.tooling`; 1 other group(s) shade the name]
  ? * org.hibernate.orm.tooling            2022-05..2026-10 6.6.59.Final         |..........==========|
  ?   io.github.martinhickson              2026-08..2026-08 6.6.7-bravura-1      |...................=|
org.jgroups  [owned by `org.jgroups`; 1 other group(s) shade the name]
  ? * org.jgroups                          2022-12..2026-10 5.5.8.Final          |...........=========|
  ?   io.github.martinhickson              2026-09..2026-09 5.3.18-bravura-3     |...................=|
org.threeten.extra  [owned by `org.threeten`; 5 other group(s) shade the name]
  A * org.threeten                         2018-01..2026-10 1.11.0               |..==================|
  R   org.yupana                           2022-01..2026-10 0.43.2               |..........==========|
  ?   fr.insee.trevas                      2026-08..2026-09 2.7.2                |...................=|
  R   org.apache.orc                       2020-01..2026-07 1.9.9                |......=============.|
  R   org.liquibase.ext                    2023-11..2023-11 4.25.0               |.............=......|
  R   com.netease.arctic                   2022-12..2022-12 0.4.0                |...........=........|
org.neo4j.bolt.connection.routed  [owned by `org.neo4j.bolt`; 0 other group(s) shade the name]
  ? * org.neo4j.bolt                       2025-03..2026-10 12.2.2               |................====|
  ?   org.neo4j.connectors                 2026-06..2026-06 6.0.0-RC01-s_2.13    |..................=.|
io.github.humbleui.skija.android.arm64  [owned by `io.github.humbleui`; 1 other group(s) shade the name]
  ? * io.github.humbleui                   2026-01..2026-10 0.143.18             |.................===|
  ?   com.behemiron.engine                 2026-06..2026-06 0.143.17             |..................=.|
io.github.humbleui.skija.android.x64  [owned by `io.github.humbleui`; 1 other group(s) shade the name]
  ? * io.github.humbleui                   2026-01..2026-10 0.143.18             |.................===|
  ?   com.behemiron.engine                 2026-06..2026-06 0.143.17             |..................=.|
io.github.humbleui.skija.linux.arm64  [owned by `io.github.humbleui`; 1 other group(s) shade the name]
  ? * io.github.humbleui                   2025-11..2026-10 0.143.18             |.................===|
  ?   com.behemiron.engine                 2026-06..2026-06 0.143.17             |..................=.|
io.github.humbleui.skija.linux.x64  [owned by `io.github.humbleui`; 1 other group(s) shade the name]
  ? * io.github.humbleui                   2022-12..2026-10 0.143.18             |...........=========|
  ?   com.behemiron.engine                 2026-06..2026-06 0.143.17             |..................=.|
io.github.humbleui.skija.windows.arm64  [owned by `io.github.humbleui`; 1 other group(s) shade the name]
  ? * io.github.humbleui                   2026-05..2026-10 0.143.18             |..................==|
  ?   com.behemiron.engine                 2026-06..2026-06 0.143.17             |..................=.|
io.github.humbleui.skija.windows.x64  [owned by `io.github.humbleui`; 1 other group(s) shade the name]
  ? * io.github.humbleui                   2022-12..2026-10 0.143.18             |...........=========|
  ?   com.behemiron.engine                 2026-06..2026-06 0.143.17             |..................=.|
org.tinylog.api.slf4j  [owned by `org.tinylog`; 1 other group(s) shade the name]
  ? * org.tinylog                          2019-07..2026-10 2.8.1                |.....===============|
  ?   io.github.ynverxe                    2025-05..2025-05 1.0.0-indev          |................=...|
org.tinylog.impl  [owned by `org.tinylog`; 1 other group(s) shade the name]
  ? * org.tinylog                          2019-07..2026-10 2.8.1                |.....===============|
  ?   cn.enaium.cafully                    2023-02..2023-03 1.1.1                |............=.......|
org.opendaylight.mdsal.dom.api  [owned by `org.opendaylight.mdsal`; 0 other group(s) shade the name]
  ? * org.opendaylight.mdsal               2019-07..2026-10 17.0.2               |.....===============|
  ?   org.opendaylight.netconf             2026-09..2026-09 12.0.1               |...................=|
com.apollographql.apollo.gradle.tasks  [owned by `com.apollographql.apollo`; 1 other group(s) shade the name]
  ? * com.apollographql.apollo             2025-06..2026-09 5.2.0                |................====|
  ?   no.beint.apollo                      2026-09..2026-09 5.1.0-beint.2        |...................=|
org.opendaylight.yangtools.yang.model.export  [owned by `org.opendaylight.yangtools`; 0 other group(s) shade the name]
  ? * org.opendaylight.yangtools           2019-04..2026-09 16.1.0               |....================|
  ?   org.opendaylight.netconf             2026-08..2026-08 11.0.1               |...................=|
io.vavr.test  [owned by `io.vavr`; 1 other group(s) shade the name]
  ? * io.vavr                              2017-11..2026-09 1.0.0                |..==================|
  ?   com.guizmaii                         2026-09..2026-09 1.0.1                |...................=|
org.mavai.punit.examples  [owned by `org.mavai`; 1 other group(s) shade the name]
  ? * org.mavai                            2026-05..2026-09 0.8.0                |..................==|
  ?   org.javai                            2026-05..2026-05 0.6.99               |..................=.|
io.github.pdvrieze.testutil  [owned by `io.github.pdvrieze.xmlutil`; 0 other group(s) shade the name]
  ? * io.github.pdvrieze.xmlutil           2025-07..2026-08 1.0.2.1              |................====|
  ?   io.github.pdvrieze                   2026-06..2026-06 1.0.0-rc3            |..................=.|
org.bytedeco.pytorch.linux.x86_64.gpu  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2021-08..2026-08 2.13.0-1.5.14        |.........==========.|
  ?   io.github.mullerhai                  2026-08..2026-08 2.13.0-1.5.14-beta-08.01 |..................=.|
org.bytedeco.tensorrt.linux.arm64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2021-07..2026-08 11.2-1.5.14          |.........==========.|
  ?   io.github.mullerhai                  2026-08..2026-08 11.1-1.5.14-beta-08  |..................=.|
org.bytedeco.tensorrt.linux.x86_64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2019-04..2026-08 11.2-1.5.14          |....===============.|
  ?   io.github.mullerhai                  2026-08..2026-08 11.1-1.5.14-beta-08  |..................=.|
org.bytedeco.tensorrt.platform  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2019-04..2026-08 11.2-1.5.14          |....===============.|
  ?   io.github.mullerhai                  2026-08..2026-08 11.1-1.5.14-beta-08  |..................=.|
org.bytedeco.tensorrt.windows.x86_64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2020-04..2026-08 11.2-1.5.14          |......=============.|
  ?   io.github.mullerhai                  2026-08..2026-08 11.1-1.5.14-beta-08  |..................=.|
org.bytedeco.tritonserver.linux.x86_64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2022-02..2026-08 2.71.0-1.5.14        |..........=========.|
  ?   io.github.mullerhai                  2026-08..2026-08 2.70.0-1.5.14-beta-08 |..................=.|
org.bytedeco.tritonserver.platform  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2022-02..2026-08 2.71.0-1.5.14        |..........=========.|
  ?   io.github.mullerhai                  2026-08..2026-08 2.70.0-1.5.14-beta-08 |..................=.|
org.bytedeco.cuda.redist.cudnn.linux.arm64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................==.|
  ?   io.github.mullerhai                  2026-08..2026-08 13.3-9.24-1.5.14-beta-08 |..................=.|
org.bytedeco.cuda.platform.redist.cudnn  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................==.|
  ?   io.github.mullerhai                  2026-07..2026-08 13.3-9.24-1.5.14-beta-08 |..................=.|
org.bytedeco.cuda.platform.redist.cusolver  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................==.|
  ?   io.github.mullerhai                  2026-07..2026-08 13.3-9.24-1.5.14-beta-08 |..................=.|
org.bytedeco.cuda.redist.cusolver.linux.arm64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................==.|
  ?   io.github.mullerhai                  2026-08..2026-08 13.3-9.24-1.5.14-beta-08 |..................=.|
org.bytedeco.cuda.platform.redist.cublas  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................==.|
  ?   io.github.mullerhai                  2026-07..2026-08 13.3-9.24-1.5.14-beta-08 |..................=.|
org.bytedeco.cuda.redist.cublas.linux.arm64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................==.|
  ?   io.github.mullerhai                  2026-08..2026-08 13.3-9.24-1.5.14-beta-08 |..................=.|
org.bytedeco.cuda.platform.redist.npp  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................==.|
  ?   io.github.mullerhai                  2026-07..2026-08 13.3-9.24-1.5.14-beta-08 |..................=.|
org.bytedeco.cuda.redist.npp.linux.arm64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................==.|
  ?   io.github.mullerhai                  2026-08..2026-08 13.3-9.24-1.5.14-beta-08 |..................=.|
org.bytedeco.cuda.platform.redist.nccl  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................==.|
  ?   io.github.mullerhai                  2026-07..2026-08 13.3-9.24-1.5.14-beta-08 |..................=.|
org.bytedeco.cuda.redist.nccl.linux.arm64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................==.|
  ?   io.github.mullerhai                  2026-07..2026-08 13.3-9.24-1.5.14-beta-08 |..................=.|
org.bytedeco.cuda.platform.redist.cusparse  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................==.|
  ?   io.github.mullerhai                  2026-07..2026-08 13.3-9.24-1.5.14-beta-08 |..................=.|
org.bytedeco.cuda.redist.cusparse.linux.arm64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................==.|
  ?   io.github.mullerhai                  2026-08..2026-08 13.3-9.24-1.5.14-beta-08 |..................=.|
org.bytedeco.cuda.platform.redist.nvcomp  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................==.|
  ?   io.github.mullerhai                  2026-07..2026-08 13.3-9.24-1.5.14-beta-08 |..................=.|
org.bytedeco.cuda.redist.nvcomp.linux.arm64  [owned by `org.bytedeco`; 1 other group(s) shade the name]
  ? * org.bytedeco                         2025-10..2026-08 13.3-9.25-1.5.14     |.................==.|
  ?   io.github.mullerhai                  2026-07..2026-08 13.3-9.24-1.5.14-beta-08 |..................=.|
org.apache.jena.base  [owned by `org.apache.jena`; 1 other group(s) shade the name]
  ? * org.apache.jena                      2018-06..2026-08 6.2.0                |...================.|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |..................=.|
org.apache.jena.text  [owned by `org.apache.jena`; 1 other group(s) shade the name]
  ? * org.apache.jena                      2018-06..2026-08 6.2.0                |...================.|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |..................=.|
org.apache.jena.arq  [owned by `org.apache.jena`; 1 other group(s) shade the name]
  ? * org.apache.jena                      2018-06..2026-08 6.2.0                |...================.|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |..................=.|
org.apache.jena.commonsrdf  [owned by `org.apache.jena`; 1 other group(s) shade the name]
  ? * org.apache.jena                      2020-05..2026-08 6.2.0                |......=============.|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |..................=.|
org.apache.jena.core  [owned by `org.apache.jena`; 1 other group(s) shade the name]
  ? * org.apache.jena                      2018-06..2026-08 6.2.0                |...================.|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |..................=.|
org.apache.jena.dboe.trans.data  [owned by `org.apache.jena`; 1 other group(s) shade the name]
  ? * org.apache.jena                      2018-12..2026-08 6.2.0                |....===============.|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |..................=.|
org.apache.jena.fuseki.access  [owned by `org.apache.jena`; 1 other group(s) shade the name]
  ? * org.apache.jena                      2018-12..2026-08 6.2.0                |....===============.|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |..................=.|
org.apache.jena.fuseki.core  [owned by `org.apache.jena`; 1 other group(s) shade the name]
  ? * org.apache.jena                      2018-12..2026-08 6.2.0                |....===============.|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |..................=.|
org.apache.jena.shex  [owned by `org.apache.jena`; 1 other group(s) shade the name]
  ? * org.apache.jena                      2021-09..2026-08 6.2.0                |.........==========.|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |..................=.|
org.apache.jena.tdb2  [owned by `org.apache.jena`; 1 other group(s) shade the name]
  ? * org.apache.jena                      2018-06..2026-08 6.2.0                |...================.|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |..................=.|
org.apache.jena.rdfconnection  [owned by `org.apache.jena`; 1 other group(s) shade the name]
  ? * org.apache.jena                      2018-06..2026-08 6.2.0                |...================.|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |..................=.|
org.apache.jena.cmds  [owned by `org.apache.jena`; 1 other group(s) shade the name]
  ? * org.apache.jena                      2018-06..2026-08 6.2.0                |...================.|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |..................=.|
org.apache.jena.dboe.index  [owned by `org.apache.jena`; 1 other group(s) shade the name]
  ? * org.apache.jena                      2018-12..2026-08 6.2.0                |....===============.|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |..................=.|
org.apache.jena.dboe.index.test  [owned by `org.apache.jena`; 1 other group(s) shade the name]
  ? * org.apache.jena                      2018-12..2026-08 6.2.0                |....===============.|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |..................=.|
org.apache.jena.dboe.transaction  [owned by `org.apache.jena`; 1 other group(s) shade the name]
  ? * org.apache.jena                      2018-12..2026-08 6.2.0                |....===============.|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |..................=.|
org.apache.jena.shacl  [owned by `org.apache.jena`; 1 other group(s) shade the name]
  ? * org.apache.jena                      2019-10..2026-08 6.2.0                |.....==============.|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |..................=.|
org.apache.jena.tdb  [owned by `org.apache.jena`; 1 other group(s) shade the name]
  ? * org.apache.jena                      2018-06..2026-08 6.2.0                |...================.|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |..................=.|
org.apache.jena.dboe.base  [owned by `org.apache.jena`; 1 other group(s) shade the name]
  ? * org.apache.jena                      2020-01..2026-08 6.2.0                |......=============.|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |..................=.|
org.apache.jena.dboe.storage  [owned by `org.apache.jena`; 1 other group(s) shade the name]
  ? * org.apache.jena                      2019-10..2026-08 6.2.0                |.....==============.|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |..................=.|
org.apache.jena.fuseki.main  [owned by `org.apache.jena`; 1 other group(s) shade the name]
  ? * org.apache.jena                      2018-12..2026-08 6.2.0                |....===============.|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |..................=.|
org.apache.jena.geosparql  [owned by `org.apache.jena`; 1 other group(s) shade the name]
  ? * org.apache.jena                      2019-09..2026-08 6.2.0                |.....==============.|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |..................=.|
org.apache.jena.querybuilder  [owned by `org.apache.jena`; 1 other group(s) shade the name]
  ? * org.apache.jena                      2020-05..2026-08 6.2.0                |......=============.|
  ?   com.vaimee                           2026-06..2026-06 4.3.2                |..................=.|
```

## tld-dropped (17)

The dominant owner's groupId with its top-level domain dropped is the module-name prefix.

- The owner's groupId with its first segment (the top-level domain) removed is a prefix of the module name.
- Allow that owner; reject the rest.

```
koog.spring.ai.starter.vector.store.jvm  [owned by `ai.koog` (groupId minus TLD is the module prefix); 2 other group(s) shade the name]
  ? * ai.koog                              2026-04..2026-09 1.3.0-beta           |..................==|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-beta-kroog.6   |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0-beta           |...................=|
koog.spring.ai.v2.starter.model.chat.jvm  [owned by `ai.koog` (groupId minus TLD is the module prefix); 2 other group(s) shade the name]
  ? * ai.koog                              2026-07..2026-09 1.3.0-beta           |..................==|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-beta-kroog.6   |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0-beta           |...................=|
koog.spring.ai.v2.starter.model.embedding.jvm  [owned by `ai.koog` (groupId minus TLD is the module prefix); 2 other group(s) shade the name]
  ? * ai.koog                              2026-07..2026-09 1.3.0-beta           |..................==|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-beta-kroog.6   |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0-beta           |...................=|
koog.spring.ai.v2.starter.vector.store.jvm  [owned by `ai.koog` (groupId minus TLD is the module prefix); 2 other group(s) shade the name]
  ? * ai.koog                              2026-07..2026-09 1.3.0-beta           |..................==|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-beta-kroog.6   |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0-beta           |...................=|
koog.spring.boot.starter.jvm  [owned by `ai.koog` (groupId minus TLD is the module prefix); 2 other group(s) shade the name]
  ? * ai.koog                              2025-07..2026-09 1.3.0-beta           |................====|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-beta-kroog.6   |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0-beta           |...................=|
koog.agents.additions.jvm  [owned by `ai.koog` (groupId minus TLD is the module prefix); 2 other group(s) shade the name]
  ? * ai.koog                              2026-05..2026-09 1.3.0-beta           |..................==|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-beta-kroog.6   |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0-beta           |...................=|
koog.agents.jvm  [owned by `ai.koog` (groupId minus TLD is the module prefix); 2 other group(s) shade the name]
  ? * ai.koog                              2025-05..2026-09 1.3.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-kroog.6        |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0                |...................=|
koog.ktor.jvm  [owned by `ai.koog` (groupId minus TLD is the module prefix); 2 other group(s) shade the name]
  ? * ai.koog                              2025-08..2026-09 1.3.0-beta           |.................===|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-beta-kroog.6   |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0-beta           |...................=|
koog.spring.ai.common.jvm  [owned by `ai.koog` (groupId minus TLD is the module prefix); 2 other group(s) shade the name]
  ? * ai.koog                              2026-04..2026-09 1.3.0-beta           |..................==|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-beta-kroog.6   |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0-beta           |...................=|
koog.spring.ai.starter.chat.memory.jvm  [owned by `ai.koog` (groupId minus TLD is the module prefix); 2 other group(s) shade the name]
  ? * ai.koog                              2026-04..2026-09 1.3.0-beta           |..................==|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-beta-kroog.6   |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0-beta           |...................=|
koog.spring.ai.starter.model.chat.jvm  [owned by `ai.koog` (groupId minus TLD is the module prefix); 2 other group(s) shade the name]
  ? * ai.koog                              2026-03..2026-09 1.3.0-beta           |..................==|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-beta-kroog.6   |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0-beta           |...................=|
koog.spring.ai.starter.model.embedding.jvm  [owned by `ai.koog` (groupId minus TLD is the module prefix); 2 other group(s) shade the name]
  ? * ai.koog                              2026-03..2026-09 1.3.0-beta           |..................==|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-beta-kroog.6   |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0-beta           |...................=|
koog.spring.ai.v2.common.jvm  [owned by `ai.koog` (groupId minus TLD is the module prefix); 2 other group(s) shade the name]
  ? * ai.koog                              2026-07..2026-09 1.3.0-beta           |..................==|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-beta-kroog.6   |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0-beta           |...................=|
koog.spring.ai.v2.starter.chat.memory.jvm  [owned by `ai.koog` (groupId minus TLD is the module prefix); 2 other group(s) shade the name]
  ? * ai.koog                              2026-07..2026-09 1.3.0-beta           |..................==|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-beta-kroog.6   |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0-beta           |...................=|
roaringbitmap  [owned by `org.roaringbitmap` (groupId minus TLD is the module prefix); 3 other group(s) shade the name]
  A * org.roaringbitmap                    2023-09..2026-11 1.6.30               |.............=======|
  R   org.apache.celeborn                  2024-06..2026-08 0.7.0                |..............=====.|
  ?   com.atomgraph.etl.csv                2026-06..2026-07 2.2.1                |..................=.|
  R   org.bitlap                           2023-10..2023-10 1.0.1.0              |.............=......|
r2dbc.postgresql  [owned by `io.r2dbc` (groupId minus TLD is the module prefix); 3 other group(s) shade the name]
  A * io.r2dbc                             2019-11..2022-09 0.8.13.RELEASE       |.....=======........|
  R   org.postgresql                       2021-02..2026-09 1.1.3.RELEASE        |........============|
  ?   com.aliyun                           2026-08..2026-08 1.2.1                |..................=.|
  R   com.yugabyte                         2023-12..2026-04 1.1.0-yb-2           |.............======.|
osgi.core  [owned by `org.osgi` (groupId minus TLD is the module prefix); 2 other group(s) shade the name]
  ? * org.osgi                             2020-12..2020-12 8.0.0                |.......=............|
  A   org.apache.karaf                     2022-04..2025-01 4.4.7                |..........======....|
  A   org.apache.felix.atomos              2021-02..2021-02 8.0.0                |........=...........|
```

## two-segments (68)

The dominant owner's groupId with its first two segments dropped is the module-name prefix.

- The owner's groupId with its first two segments removed is a prefix of the module name.
- Allow that owner; reject the rest.

| count | current owner | new owner(s) |
|---:|---|---|
| 1 | `com.ibm.zertjsse` | `com.ibm.zertjsse, com.ibm.semeru-zjavasecurity` |
| 1 | `com.telenav.cactus` | `com.telenav.cactus, com.telenav.lexakai` |

```
jackson.datatype.pcollections  [owned by `com.fasterxml.jackson.datatype` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * com.fasterxml.jackson.datatype       2019-07..2026-11 2.23.0               |.....===============|
  ?   tools.jackson.datatype               2025-03..2026-11 3.3.0                |................====|
zertjsse  [owned by `com.ibm.zertjsse` (groupId minus two segments is the module prefix); 0 other group(s) shade the name]
  ? * com.ibm.zertjsse                     2026-05..2026-09 11.0.32.0            |..................==|
  ?   com.ibm.semeru-zjavasecurity         2026-05..2026-09 11.0.32.0            |..................==|
tuweni.bytes  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........==........|
  ?   io.consensys.tuweni                  2025-02..2026-08 2.8.0                |................===.|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0                |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
tuweni.concurrent  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........==........|
  ?   io.consensys.tuweni                  2025-02..2026-08 2.8.0                |................===.|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0                |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
tuweni.concurrent_coroutines  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........==........|
  ?   io.consensys.tuweni                  2025-02..2026-08 2.8.0                |................===.|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0                |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
tuweni.config  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........==........|
  ?   io.consensys.tuweni                  2025-02..2026-08 2.8.0                |................===.|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0                |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
tuweni.crypto  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........==........|
  ?   io.consensys.tuweni                  2025-02..2026-08 2.8.0                |................===.|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0                |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
tuweni.devp2p  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........==........|
  ?   io.consensys.tuweni                  2025-02..2026-08 2.8.0                |................===.|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0                |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
tuweni.io  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........==........|
  ?   io.consensys.tuweni                  2025-02..2026-08 2.8.0                |................===.|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0                |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
tuweni.junit  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........==........|
  ?   io.consensys.tuweni                  2025-02..2026-08 2.8.0                |................===.|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0                |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
tuweni.kademlia  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........==........|
  ?   io.consensys.tuweni                  2025-02..2026-08 2.8.0                |................===.|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0                |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
tuweni.net  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........==........|
  ?   io.consensys.tuweni                  2025-02..2026-08 2.8.0                |................===.|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0                |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
tuweni.rlp  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........==........|
  ?   io.consensys.tuweni                  2025-02..2026-08 2.8.0                |................===.|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0                |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
tuweni.ssz  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........==........|
  ?   io.consensys.tuweni                  2025-02..2026-08 2.8.0                |................===.|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0                |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
tuweni.toml  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........==........|
  ?   io.consensys.tuweni                  2025-02..2026-08 2.8.0                |................===.|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0                |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
tuweni.units  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........==........|
  ?   io.consensys.tuweni                  2025-02..2026-08 2.8.0                |................===.|
  ?   io.consensys.protocols               2025-02..2025-02 2.6.0                |................=...|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
dagger  [owned by `com.google.dagger` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * com.google.dagger                    2021-06..2026-07 2.60.1               |........===========.|
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
  ?   io.github.tri5m                      2024-12..2026-04 1.0.6                |...............====.|
tucache.spring.boot.autoconfigure  [owned by `co.tunan.tucache` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * co.tunan.tucache                     2024-02..2024-02 1.0.4.RELEASE        |..............=.....|
  ?   io.github.tri5m                      2024-12..2026-04 1.0.6                |...............====.|
tucache.spring.boot.starter  [owned by `co.tunan.tucache` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * co.tunan.tucache                     2024-02..2024-02 1.0.4.RELEASE        |..............=.....|
  ?   io.github.tri5m                      2024-12..2026-04 1.0.6                |...............====.|
glide.api  [owned by `software.amazon.glide` (groupId minus two segments is the module prefix); 2 other group(s) shade the name]
  ? * software.amazon.glide                2024-06..2024-06 0.4.3                |..............=.....|
  ?   io.valkey                            2024-07..2025-10 2.1.1                |..............====..|
  ?   io.github.gumpacg                    2024-08..2024-08 0.1.0                |...............=....|
afterburner.fx  [owned by `com.dlsc.afterburner` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * com.dlsc.afterburner                 2019-10..2023-07 2.3.0                |.....=========......|
  ?   org.jabref                           2023-09..2023-09 2.0.0                |.............=......|
tuweni.wallet  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........==........|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
tuweni.scuttlebutt_rpc  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........==........|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
tuweni.scuttlebutt_handshake  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........==........|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
tuweni.scuttlebutt_discovery  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........==........|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
tuweni.scuttlebutt_client_lib  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........==........|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
tuweni.scuttlebutt  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........==........|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
tuweni.rlpx  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........==........|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
tuweni.pow  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........==........|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
tuweni.plumtree  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........==........|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
tuweni.peer_repository  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........==........|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
tuweni.metrics  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........==........|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
tuweni.merkle_trie  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........==........|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
tuweni.les  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........==........|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
tuweni.kv  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........==........|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
tuweni.jsonrpc_app  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........==........|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
tuweni.jsonrpc  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........==........|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
tuweni.hobbits_relayer  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........==........|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
tuweni.hobbits  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........==........|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
tuweni.gossip  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........==........|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
tuweni.genesis  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........==........|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
tuweni.evm_dsl  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-09..2022-11 2.3.1                |...........=........|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
tuweni.evm  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........==........|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
tuweni.ethstats  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........==........|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
tuweni.eth_repository  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........==........|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
tuweni.eth_precompiles  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-09..2022-11 2.3.1                |...........=........|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
tuweni.eth_faucet  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........==........|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
tuweni.eth_crawler  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........==........|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
tuweni.eth_client_ui  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........==........|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
tuweni.eth_client_app  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........==........|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
tuweni.eth_client  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........==........|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
tuweni.eth_blockprocessor  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-09..2022-11 2.3.1                |...........=........|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
tuweni.eth  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........==........|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
tuweni.dns_discovery  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........==........|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
tuweni.devp2p_proxy  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........==........|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
tuweni.devp2p_eth  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........==........|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
tuweni.app_commons  [owned by `org.apache.tuweni` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.apache.tuweni                    2022-03..2022-11 2.3.1                |..........==........|
  ?   io.tmio                              2023-05..2023-07 2.4.2                |............=.......|
cactus.maven.xml  [owned by `com.telenav.cactus` (groupId minus two segments is the module prefix); 0 other group(s) shade the name]
  ? * com.telenav.cactus                   2022-06..2022-11 1.5.49               |..........==........|
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

## unclassified (353)

Multiple publishers with no natural-namespace owner present: a genuine collision the heuristic cannot settle.

- More than one publisher, and none is a credible owner: no natural-namespace owner is present and the earliest is not the closest groupId.
- Left unresolved - no owners.tsv is written - for a later hand decision.

_Showing the 200 most recently active of 353. For the full list, emit the SetOwners file: `-Djenesis.crawler.drift.emit=unclassified`._

```
net.sf.jsqlparser  [no clear owner; `com.github.jsqlparser` is earliest and most recent]
  ? * com.github.jsqlparser                2024-03..2026-09 5.4                  |..............======|
  ?   ca.denisab85                         2026-11..2026-11 5.4.166              |...................=|
  ?   com.manticore-projects.jsqlformatter 2025-12..2026-11 5.4.272              |.................===|
  ?   se.alipsa                            2025-12..2025-12 1.2.0                |.................=..|
  ?   ai.starlake                          2024-09..2024-10 1.3.0                |...............=....|
org.apache.commons.codec  [no clear owner; `commons-codec` is earliest and most recent]
  ? * commons-codec                        2017-10..2026-10 1.23.0               |.===================|
  ?   com.mirakl                           2025-11..2026-11 10.23.0              |.................===|
  ?   software.amazon.awssdk               2024-07..2026-11 2.59.1               |...............=====|
  ?   com.clickhouse.spark                 2025-12..2026-11 0.11.0               |.................===|
  ?   software.amazon.glue                 2026-09..2026-11 2.0.2                |...................=|
  ?   com.liquibase.ext.vaults             2026-06..2026-11 6.0.1                |..................==|
    + 81 more: io.prophecy, org.operaton.bpm.extension, org.apache.pinot, de.knightsoft-net, io.gitlab.cupofcode, org.apache.jackrabbit, org.apache.tika, com.alibaba.ververica, edu.stanford.protege, org.cibseven.community.keycloak, org.apache.druid.extensions.contrib, cn.ctyun, (+69 more)
prompt.cache.files.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-09 1.3.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-kroog.6        |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0                |...................=|
prompt.cache.model.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-09 1.3.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-kroog.6        |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0                |...................=|
prompt.cache.redis.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-09 1.3.0-beta           |................====|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-beta-kroog.6   |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0-beta           |...................=|
prompt.executor.anthropic.client.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-09 1.3.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-kroog.6        |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0                |...................=|
prompt.executor.bedrock.client.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-07..2026-09 1.3.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-kroog.6        |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0                |...................=|
prompt.executor.cached.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-09 1.3.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-kroog.6        |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0                |...................=|
prompt.executor.clients.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-09 1.3.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-kroog.6        |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0                |...................=|
prompt.executor.dashscope.client.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-10..2026-09 1.3.0-beta           |.................===|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-beta-kroog.6   |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0-beta           |...................=|
prompt.executor.deepseek.client.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-08..2026-09 1.3.0-beta           |.................===|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-beta-kroog.6   |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0-beta           |...................=|
prompt.executor.google.client.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-09 1.3.0-beta           |................====|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-beta-kroog.6   |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0-beta           |...................=|
prompt.executor.litert.client.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2026-05..2026-09 1.3.0-beta           |..................==|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-beta-kroog.6   |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0-beta           |...................=|
prompt.executor.llms.all.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-09 1.3.0-beta           |................====|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-beta-kroog.6   |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0-beta           |...................=|
prompt.executor.mistralai.client.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-10..2026-09 1.3.0-beta           |.................===|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-beta-kroog.6   |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0-beta           |...................=|
prompt.executor.model.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-09 1.3.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-kroog.6        |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0                |...................=|
prompt.executor.ollama.client.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-09 1.3.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-kroog.6        |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0                |...................=|
prompt.executor.openai.client.base.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-09..2026-09 1.3.0                |.................===|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-kroog.6        |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0                |...................=|
prompt.executor.openai.client.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-09 1.3.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-kroog.6        |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0                |...................=|
prompt.executor.openrouter.client.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-09 1.3.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-kroog.6        |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0                |...................=|
prompt.llm.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-09 1.3.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-kroog.6        |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0                |...................=|
prompt.markdown.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-09 1.3.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-kroog.6        |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0                |...................=|
prompt.model.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-09 1.3.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-kroog.6        |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0                |...................=|
prompt.processor.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-12..2026-09 1.3.0                |.................===|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-kroog.6        |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0                |...................=|
prompt.structure.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-09 1.3.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-kroog.6        |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0                |...................=|
prompt.tokenizer.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-06..2026-09 1.3.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-kroog.6        |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0                |...................=|
prompt.xml.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-09 1.3.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-kroog.6        |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0                |...................=|
rag.base.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-07..2026-09 1.3.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-kroog.6        |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0                |...................=|
rag.vector.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2026-04..2026-09 1.3.0-beta           |..................==|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-beta-kroog.6   |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0-beta           |...................=|
serialization.core.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2026-03..2026-09 1.3.0                |..................==|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-kroog.6        |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0                |...................=|
serialization.jackson.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2026-03..2026-09 1.3.0                |..................==|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-kroog.6        |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0                |...................=|
skills.jvm  [no clear owner; `com.kreoh.kroog` is earliest and most recent]
  ? * com.kreoh.kroog                      2026-08..2026-11 1.3.0-beta-kroog.6   |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0-beta           |...................=|
  ?   ai.koog                              2026-08..2026-09 1.3.0-beta           |...................=|
utils.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-09..2026-09 1.3.0                |.................===|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-kroog.6        |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0                |...................=|
agents.test.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-09 1.3.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-kroog.6        |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0                |...................=|
agents.tools.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-09 1.3.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-kroog.6        |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0                |...................=|
agents.utils.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-09 1.3.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-kroog.6        |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0                |...................=|
embeddings.base.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-09 1.3.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-kroog.6        |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0                |...................=|
embeddings.llm.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-09 1.3.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-kroog.6        |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0                |...................=|
http.client.core.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-10..2026-09 1.3.0                |.................===|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-kroog.6        |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0                |...................=|
http.client.java.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2026-03..2026-09 1.3.0                |..................==|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-kroog.6        |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0                |...................=|
http.client.ktor.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-10..2026-09 1.3.0                |.................===|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-kroog.6        |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0                |...................=|
http.client.okhttp.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2026-03..2026-09 1.3.0                |..................==|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-kroog.6        |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0                |...................=|
http.client.spring.webclient.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2026-07..2026-09 1.3.0                |..................==|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-kroog.6        |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0                |...................=|
koog.bedrock.agentcore.runtime.jvm  [no clear owner; `com.kreoh.kroog` is earliest and most recent]
  ? * com.kreoh.kroog                      2026-08..2026-11 1.3.0-beta-kroog.6   |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0-beta           |...................=|
  ?   ai.koog                              2026-08..2026-09 1.3.0-beta           |...................=|
agents.features.event.handler.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-09 1.3.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-kroog.6        |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0                |...................=|
agents.features.longterm.memory.aws.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2026-05..2026-09 1.3.0-beta           |..................==|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-beta-kroog.6   |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0-beta           |...................=|
agents.features.longterm.memory.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2026-03..2026-09 1.3.0-beta           |..................==|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-beta-kroog.6   |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0-beta           |...................=|
agents.features.memory.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-09 1.3.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-kroog.6        |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0                |...................=|
agents.features.opentelemetry.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-07..2026-09 1.3.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-kroog.6        |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0                |...................=|
agents.features.persistence.jdbc.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2026-03..2026-09 1.3.0                |..................==|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-kroog.6        |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0                |...................=|
agents.features.snapshot.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-07..2026-09 1.3.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-kroog.6        |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0                |...................=|
agents.features.sql.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-09..2026-09 1.3.0                |.................===|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-kroog.6        |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0                |...................=|
agents.features.tokenizer.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-06..2026-09 1.3.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-kroog.6        |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0                |...................=|
agents.features.trace.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-09 1.3.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-kroog.6        |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0                |...................=|
agents.mcp.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-09 1.3.0-beta           |................====|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-beta-kroog.6   |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0-beta           |...................=|
agents.mcp.metadata.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2026-03..2026-09 1.3.0                |..................==|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-kroog.6        |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0                |...................=|
agents.mcp.server.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-09..2026-09 1.3.0-beta           |.................===|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-beta-kroog.6   |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0-beta           |...................=|
agents.planner.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-12..2026-09 1.3.0-beta           |.................===|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-beta-kroog.6   |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0-beta           |...................=|
agents.features.chat.memory.sql.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2026-03..2026-09 1.3.0                |..................==|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-kroog.6        |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0                |...................=|
agents.core.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-09 1.3.0                |................====|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-kroog.6        |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0                |...................=|
agents.features.a2a.server.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-10..2026-09 1.3.0-beta           |.................===|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-beta-kroog.6   |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0-beta           |...................=|
agents.features.acp.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-12..2026-09 1.3.0-beta           |.................===|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-beta-kroog.6   |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0-beta           |...................=|
agents.features.chat.history.aws.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2026-04..2026-09 1.3.0-beta           |..................==|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-beta-kroog.6   |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0-beta           |...................=|
agents.features.chat.history.jdbc.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2026-03..2026-09 1.3.0                |..................==|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-kroog.6        |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0                |...................=|
a2a.core.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-10..2026-09 1.3.0-beta           |.................===|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-beta-kroog.6   |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0-beta           |...................=|
a2a.server.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-10..2026-09 1.3.0-beta           |.................===|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-beta-kroog.6   |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0-beta           |...................=|
a2a.transport.client.jsonrpc.http.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-10..2026-09 1.3.0-beta           |.................===|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-beta-kroog.6   |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0-beta           |...................=|
a2a.transport.core.jsonrpc.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-10..2026-09 1.3.0-beta           |.................===|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-beta-kroog.6   |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0-beta           |...................=|
a2a.transport.server.jsonrpc.http.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-10..2026-09 1.3.0-beta           |.................===|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-beta-kroog.6   |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0-beta           |...................=|
agents.cli.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2026-07..2026-09 1.3.0-beta           |..................==|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-beta-kroog.6   |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0-beta           |...................=|
agents.ext.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-05..2026-09 1.3.0-beta           |................====|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-beta-kroog.6   |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0-beta           |...................=|
agents.features.a2a.client.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-10..2026-09 1.3.0-beta           |.................===|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-beta-kroog.6   |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0-beta           |...................=|
agents.features.a2a.core.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-10..2026-09 1.3.0-beta           |.................===|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-beta-kroog.6   |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0-beta           |...................=|
a2a.client.jvm  [no clear owner; `ai.koog` is earliest and most recent]
  ? * ai.koog                              2025-10..2026-09 1.3.0-beta           |.................===|
  ?   com.kreoh.kroog                      2026-08..2026-11 1.3.0-beta-kroog.6   |..................==|
  ?   moe.tlaster.koog                     2026-11..2026-11 1.3.0-beta           |...................=|
org.apache.commons.logging  [no clear owner; `org.slf4j` is earliest and most recent]
  ? * org.slf4j                            2017-04..2026-09 2.0.20               |====================|
  ?   com.helger.kaltblut                  2026-08..2026-11 0.9.7                |..................==|
  ?   de.redsix                            2024-10..2026-11 1.2.13               |...............=====|
  ?   com.helger.zugferd2peppol            2026-11..2026-11 1.0.0                |...................=|
  ?   com.helger.phlox                     2026-10..2026-11 0.6.1                |...................=|
  ?   com.ibm.cos                          2026-11..2026-11 2.15.2               |...................=|
    + 43 more: org.open-metadata, org.operaton.bpm.extension, io.github.mini-software, org.apache.tika, org.beangle.bas, org.beangle.sas, org.cibseven.community.keycloak, org.jolokia, io.github.peterdowdy, net.ontopia, org.apache.orc, org.motorbrot, (+31 more)
org.quartz  [no clear owner; `org.quartz-scheduler` is earliest and most recent]
  ? * org.quartz-scheduler                 2024-11..2025-12 2.5.2                |...............===..|
  ?   com.neolama                          2026-09..2026-11 1.0.0-rc5            |...................=|
org.apache.commons.io  [no clear owner; `commons-io` is earliest and most recent]
  ? * commons-io                           2017-10..2026-04 2.22.0               |.==================.|
  ?   io.youtrackdb                        2026-10..2026-11 0.5.0-beta-2026-11-14 |...................=|
  ?   io.openliberty.tools                 2025-03..2026-11 2.4.4                |................====|
  ?   org.ysb33r.gradle                    2026-09..2026-11 5.13.0               |...................=|
  ?   no.entur                             2024-03..2026-11 1.149.0              |..............======|
  ?   com.networknt                        2025-03..2026-11 2.5.0                |................====|
    + 102 more: io.boxlang, io.github.dawnuu, com.github.cafdataprocessing.workers.languagedetection, org.utplsql, org.apache.distributedlog, org.apache.hudi, io.streamnative, org.apache.jackrabbit, com.mifiel, org.apache.tika, io.elastic, io.github.yangziwen, (+90 more)
java.xml.soap  [no clear owner; `javax.xml.soap` is earliest and most recent]
  ? * javax.xml.soap                       2017-06..2017-06 1.4.0                |.=..................|
  ?   mx.com.sw                            2026-01..2026-11 0.1.0.1              |.................===|
  ?   org.hpccsystems                      2022-02..2025-10 9.8.126-1            |..........========..|
  ?   org.apache.servicemix.specs          2019-12..2020-03 1.4_2                |......=.............|
  ?   jakarta.xml.soap                     2018-12..2020-01 1.4.2                |....===.............|
  ?   org.jboss.spec.javax.xml.soap        2019-09..2020-01 1.0.2.Final          |.....==.............|
java.xml.bind  [no clear owner; `javax.xml.bind` is earliest and most recent]
  ? * javax.xml.bind                       2017-07..2018-09 2.3.1                |.===................|
  ?   org.icij.extract                     2021-08..2026-11 9.23.0               |.........===========|
  ?   org.verapdf.apps                     2019-06..2026-11 1.30.4               |.....===============|
  ?   com.alibaba.dts.client               2023-02..2026-11 1.1.21               |............========|
  ?   edu.stanford.swrl                    2026-10..2026-10 2.1.4                |...................=|
  ?   org.apache.pinot                     2024-08..2026-10 1.6.0                |...............=====|
    + 187 more: com.github.thinker0, io.github.cobble-project, org.apache.paimon, org.wso2.msf4j.perftest.echo, org.wso2.msf4j, org.wso2.msf4j.samples, org.wso2.msf4j.sample, eu.rssw.pct, org.metricshub, int.esa.ccsds.mo, org.apache.james, io.mosip.mock.sdk, (+175 more)
javaobjectivecbridge  [no clear owner; `ca.weblite` is earliest and most recent]
  ? * ca.weblite                           2022-01..2026-11 1.3.0                |..........==========|
  ?   org.glavo.hmcl.mmachina              2022-09..2022-09 1.1.0-mmachina.1     |...........=........|
org.apache.commons.cli  [no clear owner; `commons-cli` is earliest and most recent]
  ? * commons-cli                          2023-10..2025-11 1.11.0               |.............=====..|
  ?   org.imixs.bpmn                       2025-05..2026-11 1.3.1                |................====|
  ?   com.nanolaba                         2025-07..2026-11 1.3                  |................====|
  ?   io.github.706412584                  2026-01..2026-10 1.5.3                |.................===|
  ?   org.apache.james                     2025-09..2026-09 3.9.1                |.................===|
  ?   com.legsem.legstar                   2025-12..2026-09 2.1.5                |.................===|
    + 25 more: org.apache.meecrowave, org.apktool, org.teavm, io.github.vdaburon, com.ericsson.bss.cassandra.ecaudit, io.github.gvergine, com.amazonaws, dev.walgo, org.apache.phoenix.thirdparty, net.thisptr, us.poliscore, com.github.oboehm, (+13 more)
com.manticore.jsqlformatter  [no clear owner; `com.manticore-projects.jsqlformatter` is earliest and most recent]
  ? * com.manticore-projects.jsqlformatter 2025-06..2026-10 5.4.3                |................====|
  ?   ca.denisab85                         2026-11..2026-11 5.4.3.1              |...................=|
org.eclipse.osgi  [no clear owner; `org.eclipse.tycho` is earliest and most recent]
  ? * org.eclipse.tycho                    2018-05..2018-05 3.13.0.v20180226-1711 |...=................|
  ?   org.bonitasoft.bpm                   2023-10..2026-11 10.0.5               |.............=======|
  ?   net.kieker-monitoring                2026-04..2026-11 2.0.4                |..................==|
  ?   io.joynr.tools.generator             2025-01..2026-09 1.26.7               |...............=====|
  ?   org.eclipse.platform                 2018-06..2026-09 3.24.300             |...=================|
  ?   org.alfasoftware                     2021-05..2026-04 2.7.0                |........===========.|
    + 22 more: de.funfried.libraries, io.github.alien-tools, org.tango-controls, org.tango-controls.pogo, ch.reportingsoft.birt, com.liferay, net.revelc.code.formatter, org.kie.j2cl.tools.external, io.github.dogla, com.vertispan.j2cl.external, fr.inria.gforge.spirals, org.geneweaver, (+10 more)
com.ctc.wstx  [no clear owner; `com.fasterxml.woodstox` is earliest and most recent]
  ? * com.fasterxml.woodstox               2018-03..2026-09 7.3.0                |..==================|
  ?   gov.nih.ncats                        2022-01..2026-11 1.0.28               |..........==========|
  ?   com.twilio.sdk                       2026-08..2026-11 13.2.0               |...................=|
  ?   org.uma.jmetal                       2025-12..2026-11 7.11                 |.................===|
  ?   org.bidib.jbidib                     2021-12..2026-09 2.0.45               |.........===========|
  ?   org.apache.bigtop.itest              2026-07..2026-07 3.6.0                |..................=.|
    + 19 more: org.hpccsystems, com.backpackcloud, com.liferay.portal, de.fraunhofer.iosb.ilt.FROST-Server, com.ibm.jsonata4java, se.signatureservice.support, com.liferay, net.pincette, org.opengis.cite, org.immregistries, com.testdroid, org.sonarsource.slang, (+7 more)
org.dnsjava  [no clear owner; `dnsjava` is earliest and most recent]
  ? * dnsjava                              2019-05..2026-11 3.7.1                |....================|
  ?   com.testingbot                       2026-10..2026-11 5.2                  |...................=|
  ?   org.apache.kudu                      2025-06..2026-11 1.18.2               |................====|
  ?   io.simpleishard                      2026-07..2026-10 0.123.3              |..................==|
  ?   org.apache.pinot                     2025-02..2026-10 1.6.0                |................====|
  ?   ai.askamerica                        2026-07..2026-10 0.111.1              |..................==|
    + 22 more: org.apache.paimon, org.apache.beam, com.helger.peppol.mcp, org.apache.fluss, org.apache.knox, io.github.littleproxy, org.apache.hbase, org.apache.avro, io.jikkou, ai.platon.gora, org.apache.phoenix, com.alibaba.polardbx, (+10 more)
jakarta.security.auth.message  [no clear owner; `jakarta.authentication` is earliest and most recent]
  ? * jakarta.authentication               2020-11..2024-05 3.1.0                |.......========.....|
  ?   org.apache.tomcat                    2020-11..2026-11 10.1.62              |.......=============|
org.scala.lang.scala3.presentation.compiler  [no clear owner; `org.scala-lang` is earliest and most recent]
  ? * org.scala-lang                       2023-07..2026-11 3.10.1               |............========|
  ?   dev.propensive                       2026-08..2026-10 3.9.1-dev-p20        |..................==|
org.scala.lang.scala3.interfaces  [no clear owner; `org.scala-lang` is earliest and most recent]
  ? * org.scala-lang                       2021-06..2026-11 3.10.1               |.........===========|
  ?   dev.propensive                       2026-08..2026-10 3.9.1-dev-p20        |..................==|
org.scala.lang.scala3.library  [no clear owner; `org.scala-lang` is earliest and most recent]
  ? * org.scala-lang                       2021-06..2026-11 3.10.1               |.........===========|
  ?   dev.propensive                       2026-08..2026-10 3.9.1-dev-p20        |..................==|
java.security.auth.message  [no clear owner; `jakarta.security.auth.message` is earliest and most recent]
  ? * jakarta.security.auth.message        2018-12..2020-02 2.0.0-RC1            |....===.............|
  ?   org.apache.tomcat                    2020-11..2026-11 9.0.124              |.......=============|
  ?   org.jboss.spec.javax.security.auth.message 2019-08..2019-09 2.0.1.Final          |.....=..............|
java.servlet  [no clear owner; `jakarta.servlet` is earliest and most recent]
  ? * jakarta.servlet                      2019-08..2020-07 5.0.0-M2             |.....===............|
  ?   org.apache.tomcat                    2020-11..2026-11 9.0.124              |.......=============|
  ?   com.heroku                           2026-08..2026-08 9.0.120.0            |..................=.|
  ?   org.apache.felix                     2022-02..2022-10 2.1.0                |..........==........|
  ?   com.guicedee.services                2020-05..2022-02 1.2.2.1-jre17        |......=====.........|
  ?   org.jboss.spec.javax.servlet         2019-08..2019-09 2.0.0.Final          |.....=..............|
java.servlet.jsp  [no clear owner; `org.apache.tomcat` is earliest and most recent]
  ? * org.apache.tomcat                    2020-11..2026-11 9.0.124              |.......=============|
  ?   com.heroku                           2024-05..2026-08 9.0.120.0            |..............=====.|
java.el  [no clear owner; `org.apache.tomcat` is earliest and most recent]
  ? * org.apache.tomcat                    2020-11..2026-11 9.0.124              |.......=============|
  ?   com.heroku                           2024-10..2026-09 9.0.121.0            |...............=====|
java.annotation  [no clear owner; `javax.annotation` is earliest and most recent]
  ? * javax.annotation                     2017-09..2018-02 1.3.2                |.==.................|
  ?   org.apache.tomcat                    2020-09..2026-11 9.0.124              |.......=============|
  ?   com.heroku                           2024-10..2026-04 9.0.117.0            |...............====.|
  ?   one.gfw                              2023-03..2023-03 1.3.5                |............=.......|
  ?   org.rationalityfrontline.workaround  2021-02..2021-02 1.3.2-3.0.2          |........=...........|
  ?   com.guicedee.services                2019-11..2020-11 1.1.0.1-jre15        |.....===............|
    + 3 more: no.ssb.jpms, org.jboss.spec.javax.annotation, jakarta.annotation
com.oracle.truffle.tools.profiler  [no clear owner; `org.graalvm.tools` is earliest and most recent]
  ? * org.graalvm.tools                    2018-10..2026-11 25.5.5.0.3           |...=================|
  ?   com.orientechnologies                2025-12..2026-11 3.2.58               |.................===|
atlantafx.base  [no clear owner; `io.github.mkpaz` is earliest and most recent]
  ? * io.github.mkpaz                      2022-09..2026-09 3.0.0                |...........=========|
  ?   io.xpipe                             2026-07..2026-11 2.1.4                |..................==|
net.sf.uadetector.core  [no clear owner; `com.jwebmp.jre11` is earliest and most recent]
  ? * com.jwebmp.jre11                     2018-11..2018-12 0.63.0.19            |...==...............|
  ?   com.guicedee.modules.services        2026-04..2026-11 2.5.1                |..................==|
  ?   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17        |.....======.........|
  ?   com.jwebmp.thirdparty                2019-04..2019-08 0.68.0.1             |....==..............|
  ?   com.jwebmp                           2019-01..2019-04 0.66.0.1             |....=...............|
net.sf.uadetector.resources  [no clear owner; `com.jwebmp.jre11` is earliest and most recent]
  ? * com.jwebmp.jre11                     2018-11..2018-12 0.63.0.19            |...==...............|
  ?   com.guicedee.modules.services        2026-04..2026-11 2.5.1                |..................==|
  ?   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17        |.....======.........|
  ?   com.jwebmp.thirdparty                2019-04..2019-08 0.68.0.1             |....==..............|
  ?   com.jwebmp                           2019-01..2019-04 0.66.0.1             |....=...............|
aopalliance  [no clear owner; `com.jwebmp.jre11` is earliest and most recent]
  ? * com.jwebmp.jre11                     2018-12..2018-12 0.63.0.19            |....=...............|
  ?   com.guicedee.modules.services        2026-04..2026-11 2.5.1                |..................==|
  ?   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17        |.....======.........|
  ?   com.jwebmp.thirdparty                2019-04..2019-08 0.68.0.1             |....==..............|
  ?   com.jwebmp                           2019-01..2019-04 0.66.0.1             |....=...............|
cache.annotations.ri.common  [no clear owner; `com.jwebmp.thirdparty.jcache` is earliest and most recent]
  ? * com.jwebmp.thirdparty.jcache         2019-05..2019-08 0.68.0.1             |.....=..............|
  ?   com.guicedee.modules.services        2026-04..2026-11 2.5.1                |..................==|
  ?   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17        |.....======.........|
cache.annotations.ri.guice  [no clear owner; `com.jwebmp.thirdparty.jcache` is earliest and most recent]
  ? * com.jwebmp.thirdparty.jcache         2019-05..2019-08 0.68.0.1             |.....=..............|
  ?   com.guicedee.modules.services        2026-04..2026-11 2.5.1                |..................==|
  ?   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17        |.....======.........|
cache.api  [no clear owner; `com.jwebmp.thirdparty.jcache` is earliest and most recent]
  ? * com.jwebmp.thirdparty.jcache         2019-05..2019-08 0.68.0.1             |.....=..............|
  ?   com.guicedee.modules.services        2026-04..2026-11 2.5.1                |..................==|
  ?   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17        |.....======.........|
org.apache.commons.fileupload  [no clear owner; `com.jwebmp.jre11` is earliest and most recent]
  ? * com.jwebmp.jre11                     2018-12..2018-12 0.63.0.19            |....=...............|
  ?   com.guicedee.modules.services        2026-04..2026-11 2.5.1                |..................==|
  ?   org.wiremock                         2025-06..2026-04 4.0.0-beta.32        |................===.|
  ?   org.openidentityplatform.openam.agents 2025-11..2026-03 5.0.3                |.................==.|
  ?   commons-fileupload                   2025-06..2025-06 1.6.0                |................=...|
  ?   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17        |.....======.........|
    + 2 more: com.jwebmp.jpms.commons, com.jwebmp
net.jbock.compiler  [no clear owner; `com.github.h908714124` is earliest and most recent]
  ? * com.github.h908714124                2018-11..2021-05 4.4.000              |...======...........|
  ?   io.github.jbock-java                 2021-06..2026-11 6.3.1                |........============|
net.jbock  [no clear owner; `com.github.h908714124` is earliest and most recent]
  ? * com.github.h908714124                2017-10..2021-05 4.4                  |.========...........|
  ?   io.github.jbock-java                 2021-06..2026-11 6.3                  |........============|
bus.starter  [no clear owner; `org.miaixz` is earliest and most recent]
  ? * org.miaixz                           2025-05..2026-11 8.8.50               |................====|
  ?   io.github.rassafel                   2025-07..2025-07 0.0.1                |................=...|
bus.spring  [no clear owner; `io.github.rassafel` is earliest and most recent]
  ? * io.github.rassafel                   2026-04..2026-04 0.0.4                |..................=.|
  ?   org.miaixz                           2026-08..2026-11 8.8.50               |..................==|
jakarta.messaging  [no clear owner; `jakarta.jms` is earliest and most recent]
  ? * jakarta.jms                          2022-03..2022-03 3.1.0                |..........=.........|
  ?   org.openldes.ldio                    2026-11..2026-11 3.2.2                |...................=|
  ?   org.apache.storm                     2025-05..2026-07 3.0.0                |................===.|
  ?   be.vlaanderen.informatievlaanderen.ldes.ldio 2024-12..2024-12 2.12.0               |...............=....|
vault.java.driver  [no clear owner; `com.bettercloud` is earliest and most recent]
  ? * com.bettercloud                      2019-06..2019-12 5.1.0                |.....==.............|
  ?   io.github.jopenlibs                  2022-10..2026-11 7.0.0                |...........=========|
  ?   io.axual.utilities.config.providers  2020-06..2026-10 1.1.2                |.......=============|
  ?   edu.utexas.tacc.tapis                2021-10..2021-10 5.1.2                |.........=..........|
graphql.java.tools  [no clear owner; `com.graphql-java-kickstart` is earliest and most recent]
  ? * com.graphql-java-kickstart           2023-08..2025-04 14.0.1               |.............====...|
  ?   io.github.graphql-java-kickstart     2026-03..2026-11 15.0.0               |..................==|
com.oracle.truffle.truffle_nfi_panama  [no clear owner; `org.graalvm.truffle` is earliest and most recent]
  ? * org.graalvm.truffle                  2023-09..2026-11 25.5.5.0.3           |.............=======|
  ?   com.liquibase.ext                    2026-09..2026-11 6.0.1                |...................=|
VirtualizedFX  [no clear owner; `io.github.palexdev` is earliest and most recent]
  ? * io.github.palexdev                   2022-03..2026-11 25.5.0               |..........==========|
  ?   org.glavo.materialfx                 2022-04..2022-04 11.2.6               |..........=.........|
com.oracle.truffle.regex  [no clear owner; `org.graalvm.regex` is earliest and most recent]
  ? * org.graalvm.regex                    2018-10..2026-11 25.5.5.0.3           |...=================|
  ?   org.noear                            2024-09..2025-07 1.9.6                |...............==...|
  ?   com.syncloop.middleware              2025-01..2025-01 1.7.1                |...............=....|
jdk.internal.vm.compiler  [no clear owner; `org.graalvm.compiler` is earliest and most recent]
  ? * org.graalvm.compiler                 2018-10..2026-10 23.0.14.0.3          |...=================|
  ?   io.vertx                             2022-11..2026-11 4.5.36               |...........=========|
  ?   org.linuxforhealth.fhir              2022-08..2022-12 5.1.1                |...........=........|
io.github.bucket4j.core  [no clear owner; `com.github.vladimir-bukhtoyarov` is earliest and most recent]
  ? * com.github.vladimir-bukhtoyarov      2019-11..2024-04 8.0.1                |.....==========.....|
  ?   com.bucket4j                         2022-07..2026-11 8.22.0               |...........=========|
io.github.bucket4j.hazelcast  [no clear owner; `com.github.vladimir-bukhtoyarov` is earliest and most recent]
  ? * com.github.vladimir-bukhtoyarov      2019-11..2024-04 8.0.1                |.....==========.....|
  ?   com.bucket4j                         2022-07..2026-11 8.22.0               |...........=========|
io.github.bucket4j.ignite  [no clear owner; `com.github.vladimir-bukhtoyarov` is earliest and most recent]
  ? * com.github.vladimir-bukhtoyarov      2019-11..2024-04 8.0.1                |.....==========.....|
  ?   com.bucket4j                         2022-07..2026-11 8.22.0               |...........=========|
io.github.bucket4j.jcache  [no clear owner; `com.github.vladimir-bukhtoyarov` is earliest and most recent]
  ? * com.github.vladimir-bukhtoyarov      2019-11..2024-04 8.0.1                |.....==========.....|
  ?   com.bucket4j                         2022-07..2026-11 8.22.0               |...........=========|
io.github.bucket4j.mysql  [no clear owner; `com.github.vladimir-bukhtoyarov` is earliest and most recent]
  ? * com.github.vladimir-bukhtoyarov      2022-03..2024-04 8.0.1                |..........=====.....|
  ?   com.bucket4j                         2022-07..2026-11 8.22.0               |...........=========|
io.github.bucket4j.postgresql  [no clear owner; `com.github.vladimir-bukhtoyarov` is earliest and most recent]
  ? * com.github.vladimir-bukhtoyarov      2022-03..2024-04 8.0.1                |..........=====.....|
  ?   com.bucket4j                         2022-07..2026-11 8.22.0               |...........=========|
io.github.bucket4j.caffeine  [no clear owner; `com.github.vladimir-bukhtoyarov` is earliest and most recent]
  ? * com.github.vladimir-bukhtoyarov      2022-03..2024-04 8.0.1                |..........=====.....|
  ?   com.bucket4j                         2022-07..2026-11 8.22.0               |...........=========|
io.github.bucket4j.coherence  [no clear owner; `com.github.vladimir-bukhtoyarov` is earliest and most recent]
  ? * com.github.vladimir-bukhtoyarov      2019-11..2024-04 8.0.1                |.....==========.....|
  ?   com.bucket4j                         2022-07..2026-11 8.22.0               |...........=========|
common.json  [no clear owner; `cn.brk2outside` is earliest and most recent]
  ? * cn.brk2outside                       2023-09..2024-11 1.0.3.5-jdk17-release |.............===....|
  ?   io.github.piotr-janczukowicz         2026-10..2026-10 8                    |...................=|
java.ws.rs  [no clear owner; `javax.ws.rs` is earliest and most recent]
  ? * javax.ws.rs                          2017-06..2018-08 2.1.1                |.===................|
  ?   org.jboss.pnc.build-agent            2021-07..2026-10 4.0.0                |.........===========|
  ?   org.opennms.alec                     2026-09..2026-09 3.0.4                |...................=|
  ?   org.apache.opennlp                   2020-07..2026-06 1.9.5                |.......============.|
  ?   org.apache.hadoop                    2026-03..2026-03 3.5.0                |..................=.|
  ?   net.oneandone.ioc-unit               2021-09..2025-11 2.0.51               |.........=========..|
    + 55 more: com.scylladb, org.opencb.opencga, io.streamnative, io.github.willena, com.liferay, com.inteligr8.activiti, cn.langpy, io.github.fernandolopes, com.epam.reportportal, com.github.openstack4j.core, org.moskito, com.mailintegrate, (+43 more)
org.bukkit  [no clear owner; `com.uroria.curepur` is earliest and most recent]
  ? * com.uroria.curepur                   2024-07..2024-07 1.21-R0.1            |...............=....|
  ?   com.620cloud.server                  2026-06..2026-10 26.3.build.374-stable |..................==|
  ?   io.github.xucy10                     2026-08..2026-08 26.2-R0.1            |..................=.|
  ?   com.mineplex.studio.server           2024-10..2026-04 26.1.2-357           |...............====.|
  ?   com.uroria.latest                    2024-07..2024-07 1.21-R0.1-2d776710d6 |...............=....|
  ?   com.uroria                           2024-07..2024-07 1.21-R0.1            |...............=....|
ij  [no clear owner; `net.imagej` is earliest and most recent]
  ? * net.imagej                           2019-03..2025-02 1.54p                |....=============...|
  ?   org.tango-controls                   2022-10..2026-10 8.1                  |...........=========|
  ?   org.tango-controls.atk               2022-06..2026-10 10.0.5               |..........==========|
io.swagger.petstore3  [no clear owner; `io.sdks` is earliest and most recent]
  ? * io.sdks                              2026-01..2026-02 1.0.2                |.................==.|
  ?   io.github.hassansattar-301           2026-10..2026-10 1.5.2                |...................=|
lombok  [no clear owner; `org.projectlombok` is earliest and most recent]
  ? * org.projectlombok                    2018-05..2026-09 1.18.48              |...=================|
  ?   io.github.devlibx.easy               2022-12..2026-10 0.0.162              |...........=========|
  ?   io.github.valuya                     2026-07..2026-10 1.18.48.1            |..................==|
  ?   dev.alllexey                         2025-10..2026-10 1.8.2                |.................===|
  ?   net.polyv                            2020-09..2026-09 2.2.10               |.......=============|
  ?   io.inji.certify.sunbirdrc            2026-03..2026-07 1.0.0-alpha.1        |..................=.|
    + 98 more: net.dryuf, com.scanoss, io.mosip.esignet.plugin.sunbirdrc, org.eclipse.hawkbit, com.huaweicloud.dws, net.wirelabs, cn.fyupeng, io.github.alllexey123, io.mosip.esignet.sunbirdrc, io.mosip.certify.sunbirdrc, io.github.opentelekomcloud, io.github.version-pulse, (+86 more)
flying.saucer.pdf  [no clear owner; `org.xhtmlrenderer` is earliest and most recent]
  ? * org.xhtmlrenderer                    2024-09..2026-10 9.13.4               |...............=====|
  ?   io.github.openpdfsaucer              2025-03..2025-05 2.0.9                |................=...|
api  [no clear owner; `org.apereo.cas` is earliest and most recent]
  ? * org.apereo.cas                       2018-11..2026-03 7.2.7.1              |...================.|
  ?   de.fkkaiser                          2026-10..2026-10 0.11.3               |...................=|
library  [no clear owner; `build.buf.prototype` is earliest and most recent]
  ? * build.buf.prototype                  2023-01..2023-01 v0.0.0-test0120      |............=.......|
  ?   com.connectrpc                       2023-09..2026-10 0.10.0               |.............=======|
  ?   build.buf                            2023-01..2023-09 0.1.10               |............==......|
okhttp  [no clear owner; `build.buf` is earliest and most recent]
  ? * build.buf                            2023-02..2023-09 0.1.10               |............==......|
  ?   com.connectrpc                       2023-09..2026-10 0.10.0               |.............=======|
org.scala.lang.scala3.compiler  [no clear owner; `org.scala-lang` is earliest and most recent]
  ? * org.scala-lang                       2021-06..2026-10 3.3.9                |.........===========|
  ?   dev.propensive                       2026-08..2026-10 3.9.1-dev-p20        |..................==|
  ?   com.michaelpollmeier                 2022-10..2022-11 3.2.2-RC1-bin-20221101-d84007c-NIGHTLY+1-extensible-repl |...........=........|
org.scala.lang.scala3.sbt.bridge  [no clear owner; `org.scala-lang` is earliest and most recent]
  ? * org.scala-lang                       2021-06..2026-10 3.3.9                |.........===========|
  ?   dev.propensive                       2026-08..2026-10 3.9.1-dev-p20        |..................==|
org.scala.lang.scala3.staging  [no clear owner; `org.scala-lang` is earliest and most recent]
  ? * org.scala-lang                       2021-06..2026-10 3.3.9                |.........===========|
  ?   dev.propensive                       2026-08..2026-10 3.9.1-dev-p20        |..................==|
org.scala.lang.scala3.tasty.inspector  [no clear owner; `org.scala-lang` is earliest and most recent]
  ? * org.scala-lang                       2021-06..2026-10 3.3.9                |.........===========|
  ?   dev.propensive                       2026-08..2026-10 3.9.1-dev-p20        |..................==|
org.scala.lang.tasty.core  [no clear owner; `org.scala-lang` is earliest and most recent]
  ? * org.scala-lang                       2021-06..2026-10 3.3.9                |.........===========|
  ?   dev.propensive                       2026-08..2026-10 3.9.1-dev-p20        |..................==|
com.drew.metadata  [no clear owner; `com.github.dalet-oss` is earliest and most recent]
  ? * com.github.dalet-oss                 2024-03..2024-03 0.0.6                |..............=.....|
  ?   com.drewnoakes                       2026-04..2026-10 2.22.0               |..................==|
jpms_dss_specs_asic_manifest  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2021-10..2025-11 6.2.d4j.1            |.........=========..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_specs_jades  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2021-10..2025-11 6.2.d4j.1            |.........=========..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_specs_jws  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2021-10..2025-11 6.2.d4j.1            |.........=========..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_specs_saml_assertion  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2020-11..2025-11 6.2.d4j.1            |.......===========..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_specs_trusted_list  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_specs_validation_report  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_specs_xades  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_specs_xmldsig  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_specs_xmlers  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2023-11..2026-10 6.5.1                |.............=======|
  ?   org.digidoc4j.dss                    2024-02..2025-11 6.2.d4j.1            |..............====..|
jpms_dss_validation  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2024-07..2026-10 6.5.1                |..............======|
  ?   org.digidoc4j.dss                    2025-11..2025-11 6.2.d4j.1            |.................=..|
jpms_dss_xades  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_xml  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2023-11..2026-10 6.5.1                |.............=======|
  ?   org.digidoc4j.dss                    2024-02..2025-11 6.2.d4j.1            |..............====..|
jpms_dss_xml_common  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2023-11..2026-10 6.5.1                |.............=======|
  ?   org.digidoc4j.dss                    2024-02..2025-11 6.2.d4j.1            |..............====..|
dss_pki_factory_jaxb  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2023-11..2026-10 6.5.1                |.............=======|
  ?   org.digidoc4j.dss                    2024-02..2025-11 6.2.d4j.1            |..............====..|
jpms_dss_model  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_pades  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_pades_pdfbox  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_pki_factory  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2023-11..2026-10 6.5.1                |.............=======|
  ?   org.digidoc4j.dss                    2024-02..2025-11 6.2.d4j.1            |..............====..|
jpms_dss_policy  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_service  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_simple_certificate_report  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_simple_report  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_spi  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_test  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_token  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_tsl_validation  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_utils  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_utils_apache_commons  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_utils_google_guava  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_ws_server_signing_common  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_ws_server_signing_dto  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_ws_server_signing_rest  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_ws_server_signing_rest_client  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_ws_server_signing_soap  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_ws_server_signing_soap_client  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_ws_signature_dto  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_ws_signature_remote  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_ws_signature_rest  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_ws_signature_rest_client  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_ws_signature_soap  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_ws_signature_soap_client  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_ws_timestamp_dto  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2020-11..2025-11 6.2.d4j.1            |.......===========..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_ws_timestamp_remote  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2020-11..2025-11 6.2.d4j.1            |.......===========..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_ws_timestamp_remote_rest  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2020-11..2025-11 6.2.d4j.1            |.......===========..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_ws_timestamp_remote_rest_client  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2020-11..2025-11 6.2.d4j.1            |.......===========..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_ws_timestamp_remote_soap  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2020-11..2025-11 6.2.d4j.1            |.......===========..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_ws_timestamp_remote_soap_client  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2020-11..2025-11 6.2.d4j.1            |.......===========..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_ws_validation_common  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_ws_validation_dto  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_ws_validation_rest  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_ws_validation_rest_client  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_ws_validation_soap  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_ws_validation_soap_client  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_common_converter  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_common_remote_dto  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_cookbook  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_crl_parser  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_crl_parser_stream  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_crl_parser_x509crl  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_detailed_report  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_diagnostic_data  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
jpms_dss_document  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-11 6.2.d4j.1            |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2026-10 6.5.1                |...........=========|
```

## Reassigned and widened ownership

Modules whose resolved owner differs from the implicit first-publisher owner once `owners.tsv` is applied. 🔀 reassigned (237): the first publisher was replaced by a different owner. ➕ widened (193): extra legal owners were allowed alongside the first publisher (e.g. a groupId migration or a co-maintained project). Modules where `owners.tsv` only confirms the first publisher are not listed. Submodules that share the same transition are collapsed into a single `prefix.*` row; the count in braces after the name is how many modules that row covers. The Rejected owner(s) column names the publishers excluded for the name (empty for a pure widening).

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
| `okhttp3.*` (7) 🔀 | `com.github.ljun20160606` | `com.squareup.okhttp3` | `com.github.ljun20160606, com.huanli233.okhttp3-compat, com.ibm.cloud` |
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

