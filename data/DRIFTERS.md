# Module ownership drifters

Generated 2026-06-12. A module *drifts* when more than one groupId publishes the name and its `owners.tsv` does not yet name every publisher (no `owners.tsv`, or one that leaves some publishing groupId neither `allowed` nor `blocked`). Resolving a drift means deciding each groupId via `SetOwners` (which writes `allowed`/`blocked`); a fully-named module drops off this list.

| Category | Modules |
|---|---:|
| republisher | 0 |
| migration | 0 |
| fork | 0 |
| shaded | 0 |
| unclassified | 460 |
| **unresolved total** | **460** |
| multi-owner modules scanned | 3572 |
| modules scanned | 36334 |

Timeline axis spans 2017-01 .. 2026-06 (today). Per group: decision `A`=allowed `B`=blocked `?`=undecided, `*`=current owner, then the publication range, latest version, and a `=` activity bar across the axis.

## republisher (0)

Earliest owner is foreign to the module name while a natural-namespace owner is also present (shaded / repackaged jars). Proposal: allow the natural owner, block the republisher.

## migration (0)

The publishing groupId handed off over time: the old coordinate went dormant, a newer one took over (a rename or a relocation). Proposal: allow both old and new so history stays resolvable and `latest` is current.

## fork (0)

A cross-org coordinate publishes the same name while the original owner is still active. Proposal: keep the original owner, block the fork.

## shaded (0)

The natural-namespace owner (the module name falls under its groupId) is the earliest and most-recent publisher; every other group merely shades or bundles the name under its own coordinate. Proposal: allow the natural owner, block the rest. Resolution is unchanged; this just records the decision so the module drops off the report.

## unclassified (460)

Multiple publishers with no natural-namespace owner present (the module name matches no publisher's groupId): a genuine collision the heuristic cannot settle. Proposal: keep the current owner, but review by hand.

| count | current owner -> proposed allowed |
|---:|---|
| 79 | `ru.tinkoff.kora -> ru.tinkoff.kora` |
| 69 | `org.springframework.boot -> org.springframework.boot` |
| 32 | `com.javax0.jamal -> com.javax0.jamal` |
| 24 | `org.eclipse.platform -> org.eclipse.platform` |
| 22 | `org.springframework -> org.springframework` |
| 18 | `org.jetbrains.kotlinx -> org.jetbrains.kotlinx` |
| 17 | `org.jmonkeyengine -> org.jmonkeyengine` |
| 14 | `org.openjfx -> org.openjfx` |
| 12 | `com.typesafe.akka -> com.typesafe.akka` |
| 11 | `eu.europa.ec.joinup.sd-dss -> eu.europa.ec.joinup.sd-dss` |
| 9 | `com.squareup.okhttp3 -> com.squareup.okhttp3` |
| 9 | `org.ktorm -> org.ktorm` |
| 8 | `com.google.inject.extensions -> com.google.inject.extensions` |
| 8 | `io.zipkin.reporter2 -> io.zipkin.reporter2` |
| 7 | `ru.tinkoff.kora.experimental -> ru.tinkoff.kora.experimental` |

_Showing the 200 most recently active of 460. For the full list, emit the SetOwners file: `-Djenesis.crawler.drift.emit=unclassified`._

```
com.fasterxml.jackson.kotlin  [no natural-namespace owner; `com.fasterxml.jackson.module` is earliest and most recent]
  ? * com.fasterxml.jackson.module         2021-01..2026-06 2.22.0       |........============|
  ?   io.inji.certify                      2026-03..2026-03 0.6.0        |...................=|
  ?   io.github.aaiezza                    2024-08..2024-08 1.0.3        |...............=....|
  ?   com.atlan                            2023-11..2024-05 1.11.5       |..............==....|
  ?   com.expediagroup.openworld.sdk       2023-02..2023-09 2.0.0        |............==......|
  ?   it.unibo.tuprolog.argumentation      2021-12..2022-06 0.6.7        |..........==........|
    + 1 more: com.googlecode.blaisemath
jackson.datatype.pcollections  [no natural-namespace owner; `com.fasterxml.jackson.datatype` is earliest and most recent]
  ? * com.fasterxml.jackson.datatype       2019-07..2026-06 2.22.0       |.....===============|
  ?   tools.jackson.datatype               2025-03..2026-05 3.1.3        |................====|
com.fasterxml.jackson.databind  [no natural-namespace owner; `com.fasterxml.jackson.core` is earliest and most recent]
  ? * com.fasterxml.jackson.core           2017-09..2026-06 2.22.0       |.===================|
  ?   com.airbnb.viaduct                   2026-01..2026-05 1.1.0        |..................==|
  ?   io.github.dhruvrawatdev              2026-05..2026-05 2.2.0        |...................=|
  ?   com.sparkutils                       2025-04..2026-05 0.1.0-RC2    |.................===|
  ?   org.jboss.galleon                    2024-08..2026-05 7.0.8.Final  |...............=====|
  ?   com.perimeterx                       2020-03..2026-05 6.17.0       |......==============|
    + 439 more: io.github.randomcodespace.sonarpredict, com.streamlake, net.thisptr, org.apache.hudi, io.hyperfoil.tools, com.amazonaws, org.apache.seatunnel, org.jetbrains.intellij.plugins, org.apache.tika, org.octopusden.octopus.automation.release-management, se.liu.research.hefquin, io.github.1030907690, (+427 more)
com.fasterxml.jackson.annotation  [no natural-namespace owner; `com.fasterxml.jackson.core` is earliest and most recent]
  ? * com.fasterxml.jackson.core           2017-09..2026-05 2.22         |.===================|
  ?   io.debezium                          2022-04..2026-05 3.6.0.Beta1  |...........=========|
  ?   com.wingify.sdk                      2026-05..2026-05 1.50.0       |...................=|
  ?   com.vwo.sdk                          2020-06..2026-05 1.50.0       |.......=============|
  ?   org.chenile                          2025-04..2026-05 2.1.22       |.................===|
  ?   nl.multicode.elevenproof             2026-05..2026-05 1.1.0        |...................=|
    + 459 more: com.yahoo.vespa, com.intuit.quickbooks-online, com.chartiq.finsemble, com.messagebird, com.ascentstream.pulsar, ch.admin.swiyu, ai.chronon, io.github.spiceforgeio, io.github.astonbitecode, org.zowe.client.java.sdk, io.github.jiajunbernoulli, ai.tock, (+447 more)
com.graphqljava  [no natural-namespace owner; `com.graphql-java` is earliest and most recent]
  ? * com.graphql-java                     2020-11..2026-05 0.0.0-2026-05-29T07-49-37-79b227e |........============|
  ?   com.liferay                          2025-05..2025-05 19.11.JAKARTA-LIFERAY-PATCHED-1 |.................=..|
  ?   io.github.my-workforce               2022-07..2023-07 19.6         |...........===......|
javafx.base  [no natural-namespace owner; `org.openjfx` is earliest and most recent]
  ? * org.openjfx                          2018-07..2026-05 27-ea+18     |...=================|
  ?   org.glavo.hmcl.openjfx               2022-08..2024-01 14.0.2.1-freebsd |...........====.....|
  ?   com.googlecode.blaisemath            2022-08..2023-02 0.5.4        |...........==.......|
  ?   org.jfxcore                          2020-12..2022-07 18-ea+1      |........====........|
  ?   it.unibo.tuprolog.argumentation      2021-01..2021-10 0.5.1        |........===.........|
  ?   it.unibo.tuprolog                    2020-10..2021-05 0.17.4       |........==..........|
    + 3 more: com.github.nkb03, com.vwo.sdk, xyz.gianlu.librespot
javafx.controls  [no natural-namespace owner; `org.openjfx` is earliest and most recent]
  ? * org.openjfx                          2018-07..2026-05 27-ea+18     |...=================|
  ?   pro.verron.office-stamper            2026-03..2026-03 3.2          |..................=.|
  ?   com.sta-soft                         2025-09..2025-09 1.1          |.................=..|
  ?   org.glavo.hmcl.openjfx               2022-08..2024-01 14.0.2.1-freebsd |...........====.....|
  ?   org.jfxcore                          2020-12..2022-07 18-ea+1      |........====........|
  ?   org.jetbrains.lets-plot              2021-10..2021-10 2.2.0-rc2    |..........=.........|
    + 1 more: io.github.martinheywang
javafx.fxml  [no natural-namespace owner; `org.openjfx` is earliest and most recent]
  ? * org.openjfx                          2018-07..2026-05 27-ea+18     |...=================|
  ?   de.fraunhofer.iosb.ilt               2022-03..2025-05 0.37         |..........========..|
  ?   org.glavo.hmcl.openjfx               2022-08..2023-08 17.0.8-loongarch64 |...........===......|
  ?   org.jfxcore                          2020-12..2022-07 18-ea+1      |........====........|
  ?   de.ipk-gatersleben                   2020-07..2020-07 3.0.2        |.......=............|
javafx.graphics  [no natural-namespace owner; `org.openjfx` is earliest and most recent]
  ? * org.openjfx                          2018-07..2026-05 27-ea+18     |...=================|
  ?   io.github.makbn                      2025-10..2025-10 2.0.0        |..................=.|
  ?   de.wenzlaff.twbibel                  2022-05..2024-12 0.1.1        |...........======...|
  ?   de.pirckheimer-gymnasium             2024-08..2024-08 3.1.0        |...............=....|
  ?   org.glavo.hmcl.openjfx               2022-08..2024-01 14.0.2.1-freebsd |...........====.....|
  ?   org.jfxcore                          2020-12..2022-07 18-ea+1      |........====........|
    + 1 more: com.robotaccomplice
javafx.graphicsEmpty  [no natural-namespace owner; `org.openjfx` is earliest and most recent]
  ? * org.openjfx                          2018-08..2026-05 27-ea+18     |...=================|
  ?   org.glavo.hmcl.openjfx               2022-08..2023-08 17.0.8-loongarch64 |...........===......|
  ?   org.jfxcore                          2020-12..2022-07 18-ea+1      |........====........|
javafx.media  [no natural-namespace owner; `org.openjfx` is earliest and most recent]
  ? * org.openjfx                          2018-07..2026-05 27-ea+18     |...=================|
  ?   net.kurobako                         2026-03..2026-03 0.8.0        |...................=|
  ?   org.glavo.hmcl.openjfx               2022-08..2023-08 17.0.8-loongarch64 |...........===......|
  ?   org.jfxcore                          2020-12..2022-07 18-ea+1      |........====........|
javafx.swing  [no natural-namespace owner; `org.openjfx` is earliest and most recent]
  ? * org.openjfx                          2018-07..2026-05 27-ea+18     |...=================|
  ?   org.glavo.hmcl.openjfx               2022-08..2023-08 17.0.8-loongarch64 |...........===......|
  ?   org.jfxcore                          2022-06..2022-07 18-ea+1      |...........=........|
  ?   de.ipk-gatersleben                   2021-05..2021-05 3.0.3        |.........=..........|
javafx.web  [no natural-namespace owner; `org.openjfx` is earliest and most recent]
  ? * org.openjfx                          2018-07..2026-05 27-ea+18     |...=================|
  ?   org.glavo.hmcl.openjfx               2022-08..2023-08 17.0.8-loongarch64 |...........===......|
  ?   org.jfxcore                          2020-12..2022-07 18-ea+1      |........====........|
  ?   de.ipk-gatersleben                   2019-04..2021-05 3.0.4        |....======..........|
javafx.webEmpty  [no natural-namespace owner; `org.openjfx` is earliest and most recent]
  ? * org.openjfx                          2018-08..2026-05 27-ea+18     |...=================|
  ?   org.glavo.hmcl.openjfx               2022-08..2023-08 17.0.8-loongarch64 |...........===......|
  ?   org.jfxcore                          2020-12..2022-07 18-ea+1      |........====........|
javafx.baseEmpty  [no natural-namespace owner; `org.openjfx` is earliest and most recent]
  ? * org.openjfx                          2018-08..2026-05 27-ea+18     |...=================|
  ?   org.glavo.hmcl.openjfx               2022-08..2023-08 17.0.8-loongarch64 |...........===......|
  ?   org.jfxcore                          2022-06..2022-07 18-ea+1      |...........=........|
javafx.controlsEmpty  [no natural-namespace owner; `org.openjfx` is earliest and most recent]
  ? * org.openjfx                          2018-08..2026-05 27-ea+18     |...=================|
  ?   org.glavo.hmcl.openjfx               2022-08..2023-08 17.0.8-loongarch64 |...........===......|
  ?   org.jfxcore                          2022-06..2022-07 18-ea+1      |...........=........|
javafx.fxmlEmpty  [no natural-namespace owner; `org.openjfx` is earliest and most recent]
  ? * org.openjfx                          2018-08..2026-05 27-ea+18     |...=================|
  ?   org.glavo.hmcl.openjfx               2022-08..2023-08 17.0.8-loongarch64 |...........===......|
  ?   org.jfxcore                          2022-06..2022-07 18-ea+1      |...........=........|
javafx.mediaEmpty  [no natural-namespace owner; `org.openjfx` is earliest and most recent]
  ? * org.openjfx                          2018-08..2026-05 27-ea+18     |...=================|
  ?   org.glavo.hmcl.openjfx               2022-08..2023-08 17.0.8-loongarch64 |...........===......|
  ?   org.jfxcore                          2020-12..2022-07 18-ea+1      |........====........|
javafx.swingEmpty  [no natural-namespace owner; `org.openjfx` is earliest and most recent]
  ? * org.openjfx                          2018-08..2026-05 27-ea+18     |...=================|
  ?   org.glavo.hmcl.openjfx               2022-08..2023-08 17.0.8-loongarch64 |...........===......|
  ?   org.jfxcore                          2022-06..2022-07 18-ea+1      |...........=........|
org.neo4j.harness  [no natural-namespace owner; `org.neo4j.test` is earliest and most recent]
  ? * org.neo4j.test                       2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb.test       2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.server  [no natural-namespace owner; `org.neo4j.app` is earliest and most recent]
  ? * org.neo4j.app                        2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb.app        2020-04..2025-06 1.0.6        |.......===========..|
lettuce.core  [no natural-namespace owner; `io.lettuce` is earliest and most recent]
  ? * io.lettuce                           2019-09..2026-05 7.6.0.RELEASE |.....===============|
  ?   org.mandas                           2021-04..2021-09 6.1.5.RELEASE |.........=..........|
com.sun.tools.rngdatatype  [no natural-namespace owner; `com.sun.xml.bind.external` is earliest and most recent]
  ? * com.sun.xml.bind.external            2018-07..2026-05 4.0.9        |...=================|
  ?   cn.lzgabel.jaxb.xml.bind.external    2022-03..2022-03 4.0.0        |..........=.........|
com.sun.tools.rngom  [no natural-namespace owner; `com.sun.xml.bind.external` is earliest and most recent]
  ? * com.sun.xml.bind.external            2018-07..2026-05 4.0.9        |...=================|
  ?   cn.lzgabel.jaxb.xml.bind.external    2022-03..2022-03 4.0.0        |..........=.........|
com.sun.codemodel  [no natural-namespace owner; `org.glassfish.jaxb` is earliest and most recent]
  ? * org.glassfish.jaxb                   2018-07..2026-05 4.0.9        |...=================|
  ?   cn.lzgabel.jaxb                      2022-03..2022-03 4.0.0        |..........=.........|
com.sun.codemodel.ac  [no natural-namespace owner; `org.glassfish.jaxb` is earliest and most recent]
  ? * org.glassfish.jaxb                   2019-10..2026-05 4.0.9        |.....===============|
  ?   cn.lzgabel.jaxb                      2022-03..2022-03 4.0.0        |..........=.........|
com.sun.xml.txw2  [no natural-namespace owner; `org.glassfish.jaxb` is earliest and most recent]
  ? * org.glassfish.jaxb                   2018-07..2026-05 4.0.9        |...=================|
  ?   org.uma.jmetal                       2025-12..2026-05 7.3          |..................==|
  ?   io.github.jeff-tian                  2026-02..2026-02 2.4.1        |..................=.|
  ?   ai.starlake                          2022-04..2025-05 1.3.5        |...........=======..|
  ?   com.jordansamhi                      2024-08..2024-08 1.1.8        |...............=....|
  ?   org.soot-oss                         2024-04..2024-04 4.5.0        |...............=....|
    + 3 more: com.yotpo, cn.lzgabel.jaxb, org.apache.servicemix.bundles
com.sun.xml.xsom  [no natural-namespace owner; `org.glassfish.jaxb` is earliest and most recent]
  ? * org.glassfish.jaxb                   2018-07..2026-05 4.0.9        |...=================|
  ?   cn.lzgabel.jaxb                      2022-03..2022-03 4.0.0        |..........=.........|
  ?   no.entur                             2020-06..2022-01 1.47         |.......====.........|
io.questdb.client  [no natural-namespace owner; `org.questdb` is earliest and most recent]
  ? * org.questdb                          2026-02..2026-05 1.3.2        |..................==|
  ?   io.github.sklarsa                    2026-05..2026-05 0.0.1        |...................=|
jme3.jbullet  [no natural-namespace owner; `org.jmonkeyengine` is earliest and most recent]
  ? * org.jmonkeyengine                    2021-03..2026-05 3.10.0-alpha5 |........============|
  ?   org.ngengine                         2025-06..2025-10 0.2.0        |.................==.|
jme3.testdata  [no natural-namespace owner; `org.jmonkeyengine` is earliest and most recent]
  ? * org.jmonkeyengine                    2021-03..2026-05 3.10.0-alpha5 |........============|
  ?   org.ngengine                         2025-06..2025-10 0.2.0        |.................==.|
jme3.android  [no natural-namespace owner; `org.jmonkeyengine` is earliest and most recent]
  ? * org.jmonkeyengine                    2021-03..2026-05 3.10.0-alpha5 |........============|
  ?   org.ngengine                         2025-06..2025-10 0.2.0        |.................==.|
jme3.desktop  [no natural-namespace owner; `org.jmonkeyengine` is earliest and most recent]
  ? * org.jmonkeyengine                    2021-03..2026-05 3.10.0-alpha5 |........============|
  ?   org.ngengine                         2025-06..2025-10 0.2.0        |.................==.|
jme3.examples  [no natural-namespace owner; `org.jmonkeyengine` is earliest and most recent]
  ? * org.jmonkeyengine                    2021-03..2026-05 3.10.0-alpha5 |........============|
  ?   org.ngengine                         2025-06..2025-10 0.2.0        |.................==.|
jme3.jogg  [no natural-namespace owner; `org.jmonkeyengine` is earliest and most recent]
  ? * org.jmonkeyengine                    2021-03..2026-05 3.10.0-alpha5 |........============|
  ?   org.ngengine                         2025-06..2025-10 0.2.0        |.................==.|
jme3.lwjgl3  [no natural-namespace owner; `org.jmonkeyengine` is earliest and most recent]
  ? * org.jmonkeyengine                    2021-03..2026-05 3.10.0-alpha5 |........============|
  ?   org.ngengine                         2025-06..2025-10 0.2.0        |.................==.|
jme3.plugins.json  [no natural-namespace owner; `org.jmonkeyengine` is earliest and most recent]
  ? * org.jmonkeyengine                    2024-02..2026-05 3.10.0-alpha5 |..............======|
  ?   org.ngengine                         2025-06..2025-10 0.2.0        |.................==.|
jme3.plugins.json.gson  [no natural-namespace owner; `org.jmonkeyengine` is earliest and most recent]
  ? * org.jmonkeyengine                    2024-02..2026-05 3.10.0-alpha5 |..............======|
  ?   org.ngengine                         2025-06..2025-10 0.2.0        |.................==.|
jme3.screenshot.tests  [no natural-namespace owner; `org.jmonkeyengine` is earliest and most recent]
  ? * org.jmonkeyengine                    2025-01..2026-05 3.10.0-alpha5 |................====|
  ?   org.ngengine                         2025-06..2025-10 0.2.0        |.................==.|
jme3.terrain  [no natural-namespace owner; `org.jmonkeyengine` is earliest and most recent]
  ? * org.jmonkeyengine                    2021-03..2026-05 3.10.0-alpha5 |........============|
  ?   org.ngengine                         2025-06..2025-10 0.2.0        |.................==.|
jme3.awt.dialogs  [no natural-namespace owner; `org.jmonkeyengine` is earliest and most recent]
  ? * org.jmonkeyengine                    2023-01..2026-05 3.10.0-alpha5 |............========|
  ?   org.ngengine                         2025-06..2025-10 0.2.0        |.................==.|
jme3.core  [no natural-namespace owner; `org.jmonkeyengine` is earliest and most recent]
  ? * org.jmonkeyengine                    2021-03..2026-05 3.10.0-alpha5 |........============|
  ?   org.ngengine                         2025-06..2025-10 0.2.0        |.................==.|
jme3.effects  [no natural-namespace owner; `org.jmonkeyengine` is earliest and most recent]
  ? * org.jmonkeyengine                    2021-03..2026-05 3.10.0-alpha5 |........============|
  ?   org.ngengine                         2025-06..2025-10 0.2.0        |.................==.|
jme3.ios  [no natural-namespace owner; `org.jmonkeyengine` is earliest and most recent]
  ? * org.jmonkeyengine                    2021-03..2026-05 3.10.0-alpha5 |........============|
  ?   org.ngengine                         2025-06..2025-10 0.2.0        |.................==.|
jme3.networking  [no natural-namespace owner; `org.jmonkeyengine` is earliest and most recent]
  ? * org.jmonkeyengine                    2021-03..2026-05 3.10.0-alpha5 |........============|
  ?   org.ngengine                         2025-06..2025-10 0.2.0        |.................==.|
jme3.plugins  [no natural-namespace owner; `org.jmonkeyengine` is earliest and most recent]
  ? * org.jmonkeyengine                    2021-03..2026-05 3.10.0-alpha5 |........============|
  ?   org.ngengine                         2025-06..2025-10 0.2.0        |.................==.|
com.codahale.metrics.health  [no natural-namespace owner; `io.dropwizard.metrics5` is earliest and most recent]
  ? * io.dropwizard.metrics5               2018-02..2026-05 5.0.7        |..==================|
  ?   io.dropwizard.metrics                2018-03..2026-05 4.2.39       |..==================|
com.codahale.metrics  [no natural-namespace owner; `io.dropwizard.metrics5` is earliest and most recent]
  ? * io.dropwizard.metrics5               2018-02..2026-05 5.0.7        |..==================|
  ?   io.dropwizard.metrics                2018-03..2026-05 4.2.39       |..==================|
  ?   com.guicedee.modules.services        2026-04..2026-05 2.0.2        |...................=|
com.codahale.metrics.jetty9  [no natural-namespace owner; `io.dropwizard.metrics` is earliest and most recent]
  ? * io.dropwizard.metrics                2018-03..2026-05 4.2.39       |..==================|
  ?   io.dropwizard.metrics5               2020-12..2020-12 5.0.0-rc4    |........=...........|
com.codahale.metrics.logback  [no natural-namespace owner; `io.dropwizard.metrics` is earliest and most recent]
  ? * io.dropwizard.metrics                2018-12..2026-05 4.2.39       |....================|
  ?   io.dropwizard.metrics5               2019-08..2020-12 5.0.0-rc4    |.....====...........|
VirtualizedFX  [no natural-namespace owner; `io.github.palexdev` is earliest and most recent]
  ? * io.github.palexdev                   2022-03..2026-05 25.1.16      |..........==========|
  ?   org.glavo.materialfx                 2022-04..2022-04 11.2.6       |...........=........|
bus.starter  [no natural-namespace owner; `org.miaixz` is earliest and most recent]
  ? * org.miaixz                           2025-05..2026-05 8.6.11       |.................===|
  ?   io.github.rassafel                   2025-07..2025-07 0.0.1        |.................=..|
org.dnsjava  [no natural-namespace owner; `dnsjava` is earliest and most recent]
  ? * dnsjava                              2019-05..2026-05 3.6.5        |.....===============|
  ?   de.m3y.hadoop.hdfs.hfsa              2025-02..2026-05 1.4.0        |................====|
  ?   org.apache.phoenix                   2025-09..2026-05 5.3.1        |.................===|
  ?   com.hazelcast.jet                    2025-10..2026-05 5.7.0        |..................==|
  ?   com.helger.peppol.mcp                2026-05..2026-05 0.5.1        |...................=|
  ?   io.jikkou                            2026-05..2026-05 1.0.0        |...................=|
    + 16 more: org.apache.atlas, org.apache.beam, org.apache.paimon, com.clickzetta, com.alibaba.polardbx, org.apache.pinot, io.github.littleproxy, org.apache.hbase, com.foilen, org.apache.kudu, dev.redcoke, org.apache.avro, (+4 more)
org.chocosolver.solver  [no natural-namespace owner; `org.choco-solver` is earliest and most recent]
  ? * org.choco-solver                     2020-07..2026-05 6.0.1        |.......=============|
  ?   io.gitlab.chaver                     2022-09..2023-08 1.0.2        |...........===......|
  ?   es.us.isa                            2023-04..2023-04 1.0.0        |.............=......|
flying.saucer.pdf  [no natural-namespace owner; `org.xhtmlrenderer` is earliest and most recent]
  ? * org.xhtmlrenderer                    2024-09..2026-05 10.2.2       |...............=====|
  ?   io.github.openpdfsaucer              2025-03..2025-05 2.0.9        |................==..|
ktorm.support.sqlserver  [no natural-namespace owner; `org.ktorm` is earliest and most recent]
  ? * org.ktorm                            2023-01..2026-05 4.2.0        |............========|
  ?   live.sidian                          2025-12..2025-12 5.0.0        |..................=.|
ktorm.core  [no natural-namespace owner; `org.ktorm` is earliest and most recent]
  ? * org.ktorm                            2023-01..2026-05 4.2.0        |............========|
  ?   live.sidian                          2025-12..2025-12 5.0.0        |..................=.|
ktorm.global  [no natural-namespace owner; `org.ktorm` is earliest and most recent]
  ? * org.ktorm                            2023-01..2026-05 4.2.0        |............========|
  ?   live.sidian                          2025-12..2025-12 5.0.0        |..................=.|
ktorm.jackson  [no natural-namespace owner; `org.ktorm` is earliest and most recent]
  ? * org.ktorm                            2023-01..2026-05 4.2.0        |............========|
  ?   live.sidian                          2025-12..2025-12 5.0.0        |..................=.|
ktorm.ksp.annotations  [no natural-namespace owner; `org.ktorm` is earliest and most recent]
  ? * org.ktorm                            2024-05..2026-05 4.2.0        |...............=====|
  ?   live.sidian                          2025-12..2025-12 5.0.0        |..................=.|
ktorm.support.mysql  [no natural-namespace owner; `org.ktorm` is earliest and most recent]
  ? * org.ktorm                            2023-01..2026-05 4.2.0        |............========|
  ?   live.sidian                          2025-12..2025-12 5.0.0        |..................=.|
ktorm.support.oracle  [no natural-namespace owner; `org.ktorm` is earliest and most recent]
  ? * org.ktorm                            2023-01..2026-05 4.2.0        |............========|
  ?   live.sidian                          2025-12..2025-12 5.0.0        |..................=.|
ktorm.support.postgresql  [no natural-namespace owner; `org.ktorm` is earliest and most recent]
  ? * org.ktorm                            2023-01..2026-05 4.2.0        |............========|
  ?   live.sidian                          2025-12..2025-12 5.0.0        |..................=.|
ktorm.support.sqlite  [no natural-namespace owner; `org.ktorm` is earliest and most recent]
  ? * org.ktorm                            2023-01..2026-05 4.2.0        |............========|
  ?   live.sidian                          2025-12..2025-12 5.0.0        |..................=.|
ihub.core  [no natural-namespace owner; `pub.ihub.lib` is earliest and most recent]
  ? * pub.ihub.lib                         2021-09..2026-05 1.7.7        |.........===========|
  ?   pub.ihub.integration                 2024-03..2025-05 0.1.12       |...............===..|
  ?   pub.ihub.module                      2024-04..2025-05 0.2.2        |...............===..|
liquibase.core  [no natural-namespace owner; `org.liquibase` is earliest and most recent]
  ? * org.liquibase                        2023-03..2026-05 5.0.3        |............========|
  ?   io.github.shshdxk                    2025-07..2025-12 4.34.0       |.................==.|
  ?   dev.ocpd.liquibase                   2023-05..2023-05 4.22.0-b14   |.............=......|
org.apache.cxf.xkms.x509.ldap  [no natural-namespace owner; `org.apache.cxf.services.xkms` is earliest and most recent]
  ? * org.apache.cxf.services.xkms         2018-06..2026-05 3.6.11       |...=================|
  ?   io.github.rzo1.org.apache.cxf.services.xkms 2024-07..2025-11 4.2.0-tomee-m0-071068f |...............====.|
org.apache.cxf.xkms.client  [no natural-namespace owner; `org.apache.cxf.services.xkms` is earliest and most recent]
  ? * org.apache.cxf.services.xkms         2018-06..2026-05 3.6.11       |...=================|
  ?   io.github.rzo1.org.apache.cxf.services.xkms 2024-07..2025-11 4.2.0-tomee-m0-071068f |...............====.|
org.apache.cxf.xkms.service  [no natural-namespace owner; `org.apache.cxf.services.xkms` is earliest and most recent]
  ? * org.apache.cxf.services.xkms         2018-06..2026-05 3.6.11       |...=================|
  ?   io.github.rzo1.org.apache.cxf.services.xkms 2024-07..2025-11 4.2.0-tomee-m0-071068f |...............====.|
org.apache.cxf.xkms.x509.handlers  [no natural-namespace owner; `org.apache.cxf.services.xkms` is earliest and most recent]
  ? * org.apache.cxf.services.xkms         2018-06..2026-05 3.6.11       |...=================|
  ?   io.github.rzo1.org.apache.cxf.services.xkms 2024-07..2025-11 4.2.0-tomee-m0-071068f |...............====.|
org.apache.cxf.xkms.common  [no natural-namespace owner; `org.apache.cxf.services.xkms` is earliest and most recent]
  ? * org.apache.cxf.services.xkms         2018-06..2026-05 3.6.11       |...=================|
  ?   io.github.rzo1.org.apache.cxf.services.xkms 2024-07..2025-11 4.2.0-tomee-m0-071068f |...............====.|
org.apache.cxf.ws.discovery.service  [no natural-namespace owner; `org.apache.cxf.services.ws-discovery` is earliest and most recent]
  ? * org.apache.cxf.services.ws-discovery 2018-06..2026-05 3.6.11       |...=================|
  ?   io.github.rzo1.org.apache.cxf.services.ws-discovery 2024-07..2025-11 4.2.0-tomee-m0-071068f |...............====.|
org.apache.cxf.ws.discovery  [no natural-namespace owner; `org.apache.cxf.services.ws-discovery` is earliest and most recent]
  ? * org.apache.cxf.services.ws-discovery 2018-06..2026-05 3.6.11       |...=================|
  ?   io.github.rzo1.org.apache.cxf.services.ws-discovery 2024-07..2025-11 4.2.0-tomee-m0-071068f |...............====.|
org.apache.cxf.wsn.osgi  [no natural-namespace owner; `org.apache.cxf.services.wsn` is earliest and most recent]
  ? * org.apache.cxf.services.wsn          2018-06..2026-05 3.6.11       |...=================|
  ?   io.github.rzo1.org.apache.cxf.services.wsn 2024-07..2025-11 4.2.0-tomee-m0-071068f |...............====.|
org.apache.cxf.wsn.core  [no natural-namespace owner; `org.apache.cxf.services.wsn` is earliest and most recent]
  ? * org.apache.cxf.services.wsn          2018-06..2026-05 3.6.11       |...=================|
  ?   io.github.rzo1.org.apache.cxf.services.wsn 2024-07..2025-11 4.2.0-tomee-m0-071068f |...............====.|
org.apache.cxf.wsn  [no natural-namespace owner; `org.apache.cxf.services.wsn` is earliest and most recent]
  ? * org.apache.cxf.services.wsn          2018-06..2026-05 3.6.11       |...=================|
  ?   io.github.rzo1.org.apache.cxf.services.wsn 2024-07..2025-11 4.2.0-tomee-m0-071068f |...............====.|
org.apache.cxf.systests.sts.osgi  [no natural-namespace owner; `org.apache.cxf.services.sts.systests` is earliest and most recent]
  ? * org.apache.cxf.services.sts.systests 2018-06..2026-05 3.6.11       |...=================|
  ?   io.github.rzo1.org.apache.cxf.services.sts.systests 2025-11..2025-11 4.2.0-tomee-m0-071068f |..................=.|
org.apache.cxf.systests.sts.advanced  [no natural-namespace owner; `org.apache.cxf.services.sts.systests` is earliest and most recent]
  ? * org.apache.cxf.services.sts.systests 2018-06..2026-05 3.6.11       |...=================|
  ?   io.github.rzo1.org.apache.cxf.services.sts.systests 2025-11..2025-11 4.2.0-tomee-m0-071068f |..................=.|
org.apache.cxf.systests.sts.basic  [no natural-namespace owner; `org.apache.cxf.services.sts.systests` is earliest and most recent]
  ? * org.apache.cxf.services.sts.systests 2018-06..2026-05 3.6.11       |...=================|
  ?   io.github.rzo1.org.apache.cxf.services.sts.systests 2025-11..2025-11 4.2.0-tomee-m0-071068f |..................=.|
org.apache.cxf.sts.core  [no natural-namespace owner; `org.apache.cxf.services.sts` is earliest and most recent]
  ? * org.apache.cxf.services.sts          2018-06..2026-05 3.6.11       |...=================|
  ?   io.github.rzo1.org.apache.cxf.services.sts 2024-07..2025-11 4.2.0-tomee-m0-071068f |...............====.|
play.ws.standalone  [no natural-namespace owner; `com.typesafe.play` is earliest and most recent]
  ? * com.typesafe.play                    2022-01..2026-05 2.2.16       |..........==========|
  ?   org.playframework                    2023-09..2026-05 3.0.12       |.............=======|
play.ws.standalone.ahc  [no natural-namespace owner; `com.typesafe.play` is earliest and most recent]
  ? * com.typesafe.play                    2022-01..2026-05 2.2.16       |..........==========|
  ?   org.playframework                    2023-09..2026-05 3.0.12       |.............=======|
play.ws.standalone.json  [no natural-namespace owner; `com.typesafe.play` is earliest and most recent]
  ? * com.typesafe.play                    2022-01..2026-05 2.2.16       |..........==========|
  ?   org.playframework                    2023-09..2026-05 3.0.12       |.............=======|
play.ws.standalone.xml  [no natural-namespace owner; `com.typesafe.play` is earliest and most recent]
  ? * com.typesafe.play                    2022-01..2026-05 2.2.16       |..........==========|
  ?   org.playframework                    2023-09..2026-05 3.0.12       |.............=======|
jul.to.slf4j  [no natural-namespace owner; `org.slf4j` is earliest and most recent]
  ? * org.slf4j                            2019-02..2026-05 2.0.18       |....================|
  ?   de.codecentric                       2024-01..2026-02 3.3.0        |..............=====.|
  ?   net.finmath                          2025-11..2026-02 2.5.7        |..................=.|
  ?   io.github.davincilll                 2025-12..2025-12 1.0.4        |..................=.|
  ?   io.github.daone-dadp                 2025-11..2025-11 3.0.2        |..................=.|
  ?   io.kestra.plugin                     2024-10..2025-10 0.24.2       |................===.|
    + 10 more: io.github.tky0065, com.itxk.maven, io.github.srilathakarri, de.fraunhofer.iosb.ilt.faaast.registry, org.easypeelsecurity, io.github.tracedin, com.github.kaklakariada, com.tencent.cloud, org.conductoross, io.bdeploy
log4j  [no natural-namespace owner; `org.slf4j` is earliest and most recent]
  ? * org.slf4j                            2017-04..2026-05 2.0.18       |====================|
  ?   com.fluxninja.aperture               2023-01..2024-01 2.30.0       |............===.....|
org.apache.commons.beanutils2  [no natural-namespace owner; `org.onebusaway` is earliest and most recent]
  ? * org.onebusaway                       2025-05..2026-05 12.0.1       |.................===|
  ?   com.github.bordertech.wcomponents    2025-12..2026-01 1.5.39       |..................=.|
kotlinx.coroutines.jdk9  [no natural-namespace owner; `org.jetbrains.kotlinx` is earliest and most recent]
  ? * org.jetbrains.kotlinx                2023-03..2026-05 1.11.0       |............========|
  ?   org.jetbrains.intellij.deps.kotlinx  2025-09..2025-11 1.10.2-intellij-1 |..................=.|
kotlinx.coroutines.android  [no natural-namespace owner; `org.jetbrains.kotlinx` is earliest and most recent]
  ? * org.jetbrains.kotlinx                2023-03..2026-05 1.11.0       |............========|
  ?   org.jetbrains.intellij.deps.kotlinx  2025-09..2025-11 1.10.2-intellij-1 |..................=.|
kotlinx.coroutines.rx2  [no natural-namespace owner; `org.jetbrains.kotlinx` is earliest and most recent]
  ? * org.jetbrains.kotlinx                2023-03..2026-05 1.11.0       |............========|
  ?   org.jetbrains.intellij.deps.kotlinx  2025-09..2025-11 1.10.2-intellij-1 |..................=.|
kotlinx.coroutines.reactive  [no natural-namespace owner; `org.jetbrains.kotlinx` is earliest and most recent]
  ? * org.jetbrains.kotlinx                2023-03..2026-05 1.11.0       |............========|
  ?   org.jetbrains.intellij.deps.kotlinx  2025-09..2025-11 1.10.2-intellij-1 |..................=.|
kotlinx.coroutines.reactor  [no natural-namespace owner; `org.jetbrains.kotlinx` is earliest and most recent]
  ? * org.jetbrains.kotlinx                2023-03..2026-05 1.11.0       |............========|
  ?   org.jetbrains.intellij.deps.kotlinx  2025-09..2025-11 1.10.2-intellij-1 |..................=.|
kotlinx.coroutines.debug  [no natural-namespace owner; `org.jetbrains.kotlinx` is earliest and most recent]
  ? * org.jetbrains.kotlinx                2023-12..2026-05 1.11.0       |..............======|
  ?   org.jetbrains.intellij.deps.kotlinx  2025-09..2025-11 1.10.2-intellij-1 |..................=.|
kotlinx.coroutines.guava  [no natural-namespace owner; `org.jetbrains.kotlinx` is earliest and most recent]
  ? * org.jetbrains.kotlinx                2023-03..2026-05 1.11.0       |............========|
  ?   org.jetbrains.intellij.deps.kotlinx  2025-09..2025-11 1.10.2-intellij-1 |..................=.|
kotlinx.coroutines.javafx  [no natural-namespace owner; `org.jetbrains.kotlinx` is earliest and most recent]
  ? * org.jetbrains.kotlinx                2023-03..2026-05 1.11.0       |............========|
  ?   org.jetbrains.intellij.deps.kotlinx  2025-09..2025-11 1.10.2-intellij-1 |..................=.|
kotlinx.coroutines.rx3  [no natural-namespace owner; `org.jetbrains.kotlinx` is earliest and most recent]
  ? * org.jetbrains.kotlinx                2023-03..2026-05 1.11.0       |............========|
  ?   org.jetbrains.intellij.deps.kotlinx  2025-09..2025-11 1.10.2-intellij-1 |..................=.|
kotlinx.coroutines.slf4j  [no natural-namespace owner; `org.jetbrains.kotlinx` is earliest and most recent]
  ? * org.jetbrains.kotlinx                2023-03..2026-05 1.11.0       |............========|
  ?   org.jetbrains.intellij.deps.kotlinx  2025-09..2025-11 1.10.2-intellij-1 |..................=.|
  ?   jp.co.gahojin.thrifty                2025-02..2025-05 4.6.3        |................==..|
  ?   io.github.vooft                      2024-09..2025-02 0.5.4        |................=...|
  ?   dev.suresh.kmp                       2024-06..2024-07 0.15.0       |...............=....|
  ?   xyz.block                            2024-03..2024-03 0.13.0       |..............==....|
kotlinx.coroutines.swing  [no natural-namespace owner; `org.jetbrains.kotlinx` is earliest and most recent]
  ? * org.jetbrains.kotlinx                2023-03..2026-05 1.11.0       |............========|
  ?   org.jetbrains.intellij.deps.kotlinx  2025-09..2025-11 1.10.2-intellij-1 |..................=.|
  ?   org.deltacv.EOCV-Sim                 2024-11..2024-11 3.8.4        |................=...|
kotlinx.coroutines.jdk8  [no natural-namespace owner; `org.jetbrains.kotlinx` is earliest and most recent]
  ? * org.jetbrains.kotlinx                2023-03..2026-05 1.11.0       |............========|
  ?   org.jetbrains.intellij.deps.kotlinx  2025-09..2025-11 1.10.2-intellij-1 |..................=.|
  ?   dev.zabricraft                       2025-01..2025-01 0.3.2        |................=...|
  ?   io.github.danbeldev                  2024-08..2024-11 0.1.0        |...............==...|
  ?   xyz.block                            2024-02..2024-02 0.8.0-beta   |..............=.....|
  ?   com.blr19c.falowp                    2023-12..2024-01 1.0.1-beta-5 |..............=.....|
    + 2 more: org.cs124.jeed, io.github.flaxoos
kotlinx.datetime  [no natural-namespace owner; `org.jetbrains.kotlinx` is earliest and most recent]
  ? * org.jetbrains.kotlinx                2022-01..2026-05 0.8.0        |..........==========|
  ?   me.haroldmartin                      2024-08..2024-09 0.3.2        |...............=....|
  ?   me.nathanfallet.zabricraft           2023-11..2023-12 0.2.4        |..............=.....|
  ?   org.danbrough.kotlinx                2022-10..2022-11 0.4.0d       |............=.......|
jcef  [no natural-namespace owner; `me.friwi` is earliest and most recent]
  ? * me.friwi                             2021-12..2026-05 jcef-d3de827+cef-146.0.10+g8219561+chromium-146.0.7680.179 |..........==========|
  ?   io.github.trethore                   2026-02..2026-04 jcef-65f9d7b+cef-146.0.10+g8219561+chromium-146.0.7680.179 |..................==|
org.apache.log4j  [no natural-namespace owner; `org.apache.logging.log4j` is earliest and most recent]
  ? * org.apache.logging.log4j             2017-11..2026-05 2.26.0       |..==================|
  ?   org.slf4j                            2019-08..2022-02 1.7.36       |.....======.........|
jam.common  [no natural-namespace owner; `sk.annotation.library.jam` is earliest and most recent]
  ? * sk.annotation.library.jam            2022-01..2026-05 0.9.21       |..........==========|
  ?   sk.annotation.projects.signito       2022-12..2022-12 0.9.53       |............=.......|
java.servlet.jsp  [no natural-namespace owner; `org.apache.tomcat` is earliest and most recent]
  ? * org.apache.tomcat                    2020-11..2026-05 9.0.118      |........============|
  ?   com.heroku                           2024-05..2026-04 9.0.117.0    |...............=====|
java.el  [no natural-namespace owner; `org.apache.tomcat` is earliest and most recent]
  ? * org.apache.tomcat                    2020-11..2026-05 9.0.118      |........============|
  ?   com.heroku                           2024-10..2024-10 9.0.96.0     |................=...|
org.scala.lang.scala3.compiler  [no natural-namespace owner; `org.scala-lang` is earliest and most recent]
  ? * org.scala-lang                       2021-06..2026-04 3.3.8-RC1    |.........===========|
  ?   com.michaelpollmeier                 2022-10..2022-11 3.2.2-RC1-bin-20221101-d84007c-NIGHTLY+1-extensible-repl |............=.......|
uk.co.real_logic.sbe.tool  [no natural-namespace owner; `uk.co.real-logic` is earliest and most recent]
  ? * uk.co.real-logic                     2019-02..2026-04 1.38.1       |....================|
  ?   org.viewstreet                       2020-03..2020-03 1.16.1.760   |......=.............|
imgui.binding  [no natural-namespace owner; `io.github.spair` is earliest and most recent]
  ? * io.github.spair                      2022-01..2026-04 1.92.0       |..........==========|
  ?   io.github.lionblazer                 2026-04..2026-04 1.92.5       |...................=|
imgui.lwjgl3  [no natural-namespace owner; `io.github.spair` is earliest and most recent]
  ? * io.github.spair                      2022-01..2026-04 1.92.0       |..........==========|
  ?   io.github.lionblazer                 2026-04..2026-04 1.92.5       |...................=|
imgui.natives.linux  [no natural-namespace owner; `io.github.spair` is earliest and most recent]
  ? * io.github.spair                      2022-03..2026-04 1.92.0       |..........==========|
  ?   io.github.lionblazer                 2026-04..2026-04 1.92.5       |...................=|
imgui.natives.macos  [no natural-namespace owner; `io.github.spair` is earliest and most recent]
  ? * io.github.spair                      2022-03..2026-04 1.92.0       |..........==========|
  ?   io.github.lionblazer                 2026-04..2026-04 1.92.5       |...................=|
imgui.natives.windows  [no natural-namespace owner; `io.github.spair` is earliest and most recent]
  ? * io.github.spair                      2022-03..2026-04 1.92.0       |..........==========|
  ?   io.github.lionblazer                 2026-04..2026-04 1.92.5       |...................=|
org.apache.commons.dbcp2  [no natural-namespace owner; `org.apache.tomee` is earliest and most recent]
  ? * org.apache.tomee                     2023-12..2026-04 10.1.5       |..............======|
  ?   org.apache.meecrowave                2025-10..2025-10 2.0.0        |..................=.|
  ?   net.ontopia                          2025-04..2025-07 5.5.2        |.................=..|
  ?   org.apache.openjpa                   2024-09..2025-05 4.1.1        |...............===..|
spring.boot.actuator.autoconfigure  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.antlib  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.autoconfigure  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.configuration.processor  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.loader.tools  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.batch  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.data.cassandra.reactive  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.data.couchbase.reactive  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.data.mongodb.reactive  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.data.neo4j  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.data.r2dbc  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2020-05..2026-04 3.5.14       |.......=============|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.data.redis.reactive  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.freemarker  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.hateoas  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.integration  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.jetty  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.jooq  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.json  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.log4j2  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.mail  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.oauth2.client  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-10..2026-04 3.5.14       |....================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.oauth2.resource.server  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-10..2026-04 3.5.14       |....================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.web.services  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.webflux  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.actuator  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.buildpack.platform  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2020-05..2026-04 3.5.14       |.......=============|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.cli  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.configuration.metadata  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.devtools  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.loader  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.properties.migrator  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.actuator  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.amqp  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.aop  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.artemis  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.cache  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.data.couchbase  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.data.elasticsearch  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.data.jdbc  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-10..2026-04 3.5.14       |....================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.data.jpa  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.data.redis  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.graphql  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2022-05..2026-04 3.5.14       |...........=========|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.groovy.templates  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.jdbc  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.jersey  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.mustache  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.quartz  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.reactor.netty  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.security  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.test  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.tomcat  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.undertow  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.validation  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.web  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.websocket  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.test  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.test.autoconfigure  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.autoconfigure.processor  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.maven.plugin  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.activemq  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.data.cassandra  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.data.ldap  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.data.mongodb  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.data.rest  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.logging  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.rsocket  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2019-10..2026-04 3.5.14       |......==============|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.thymeleaf  [no natural-namespace owner; `org.springframework.boot` is earliest and most recent]
  ? * org.springframework.boot             2018-03..2026-04 3.5.14       |..==================|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
kora.cache.caffeine  [no natural-namespace owner; `ru.tinkoff.kora` is earliest and most recent]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.camunda.engine.bpmn  [no natural-namespace owner; `ru.tinkoff.kora.experimental` is earliest and most recent]
  ? * ru.tinkoff.kora.experimental         2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework.experimental        2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.camunda.zeebe.worker.symbol.processor  [no natural-namespace owner; `ru.tinkoff.kora.experimental` is earliest and most recent]
  ? * ru.tinkoff.kora.experimental         2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework.experimental        2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.config.symbol.processor  [no natural-namespace owner; `ru.tinkoff.kora` is earliest and most recent]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.database.liquibase  [no natural-namespace owner; `ru.tinkoff.kora` is earliest and most recent]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.grpc.client  [no natural-namespace owner; `ru.tinkoff.kora` is earliest and most recent]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.jms  [no natural-namespace owner; `ru.tinkoff.kora` is earliest and most recent]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.kafka.annotation.processor  [no natural-namespace owner; `ru.tinkoff.kora` is earliest and most recent]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.kora.app.symbol.processor  [no natural-namespace owner; `ru.tinkoff.kora` is earliest and most recent]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.scheduling.ksp  [no natural-namespace owner; `ru.tinkoff.kora` is earliest and most recent]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.cache.redis  [no natural-namespace owner; `ru.tinkoff.kora` is earliest and most recent]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.cache.symbol.processor  [no natural-namespace owner; `ru.tinkoff.kora` is earliest and most recent]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.camunda.rest.undertow  [no natural-namespace owner; `ru.tinkoff.kora.experimental` is earliest and most recent]
  ? * ru.tinkoff.kora.experimental         2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework.experimental        2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.declarative.logging.annotation.processor  [no natural-namespace owner; `ru.tinkoff.kora` is earliest and most recent]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.grpc.client.annotation.processor  [no natural-namespace owner; `ru.tinkoff.kora` is earliest and most recent]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.grpc.client.symbol.processor  [no natural-namespace owner; `ru.tinkoff.kora` is earliest and most recent]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.http.client.ok  [no natural-namespace owner; `ru.tinkoff.kora` is earliest and most recent]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.http.client.symbol.processor  [no natural-namespace owner; `ru.tinkoff.kora` is earliest and most recent]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
```

