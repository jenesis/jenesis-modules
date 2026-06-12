# Module ownership drifters

Generated 2026-06-12. A module *drifts* when more than one groupId publishes the name and its `owners.tsv` does not yet name every publisher (no `owners.tsv`, or one that leaves some publishing groupId neither `allowed` nor `blocked`). Resolving a drift means deciding each groupId via `SetOwners` (which writes `allowed`/`blocked`); a fully-named module drops off this list.

| Category | Unresolved | Resolved via owners.tsv |
|---|---:|---:|
| explicit-rules | 0 | 322 |
| republisher | 0 | 57 |
| migration | 0 | 1578 |
| fork | 0 | 253 |
| shaded | 0 | 1272 |
| tld-dropped | 0 | 15 |
| two-segments | 15 | 0 |
| unclassified | 60 | 0 |
| **total** | **75** | **3497** |
| multi-owner modules scanned | 3572 | |
| modules scanned | 36334 | |

Timeline axis spans 2017-01 .. 2026-06 (today). Per group: decision `A`=allowed `B`=blocked `?`=undecided, `*`=current owner, then the publication range, latest version, and a `=` activity bar across the axis.

## explicit-rules (0)

Hand-curated overrides: a module matching an explicit rule is assigned to a fixed owner groupId regardless of the heuristic (e.g. spring.boot.* owned by org.springframework.boot). Proposal: allow that owner, block the rest.

## republisher (0)

Earliest owner is foreign to the module name while a natural-namespace owner is also present (shaded / repackaged jars). Proposal: allow the natural owner, block the republisher.

## migration (0)

The publishing groupId handed off over time: the old coordinate went dormant, a newer one took over (a rename or a relocation). Proposal: allow both old and new so history stays resolvable and `latest` is current.

## fork (0)

A cross-org coordinate publishes the same name while the original owner is still active. Proposal: keep the original owner, block the fork.

## shaded (0)

The natural-namespace owner (the module name falls under its groupId) is the earliest and most-recent publisher; every other group merely shades or bundles the name under its own coordinate. Proposal: allow the natural owner, block the rest. Resolution is unchanged; this just records the decision so the module drops off the report.

## tld-dropped (0)

The dominant owner's groupId with its top-level domain (first segment) dropped is the module-name prefix (e.g. module ktorm.* owned by org.ktorm). Proposal: allow that owner, block the rest.

## two-segments (15)

The dominant owner's groupId with its first two segments dropped is the module-name prefix (e.g. module kotlinx.* owned by org.jetbrains.kotlinx). Proposal: allow that owner, block the rest.

| count | current owner -> proposed allowed |
|---:|---|
| 6 | `com.squareup.okhttp3 -> com.squareup.okhttp3` |
| 4 | `com.typesafe.play -> com.typesafe.play` |
| 2 | `com.squareup.okio -> com.squareup.okio` |
| 1 | `com.fasterxml.jackson.datatype -> com.fasterxml.jackson.datatype` |
| 1 | `com.telenav.cactus -> com.telenav.cactus,com.telenav.lexakai` |
| 1 | `org.jetbrains.kotlin -> org.jetbrains.kotlin,org.jetbrains.lets-plot` |

```
jackson.datatype.pcollections  [owned by `com.fasterxml.jackson.datatype` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * com.fasterxml.jackson.datatype       2019-07..2026-06 2.22.0       |.....===============|
  ?   tools.jackson.datatype               2025-03..2026-05 3.1.3        |................====|
play.ws.standalone  [owned by `com.typesafe.play` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * com.typesafe.play                    2022-01..2026-05 2.2.16       |..........==========|
  ?   org.playframework                    2023-09..2026-05 3.0.12       |.............=======|
play.ws.standalone.ahc  [owned by `com.typesafe.play` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * com.typesafe.play                    2022-01..2026-05 2.2.16       |..........==========|
  ?   org.playframework                    2023-09..2026-05 3.0.12       |.............=======|
play.ws.standalone.json  [owned by `com.typesafe.play` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * com.typesafe.play                    2022-01..2026-05 2.2.16       |..........==========|
  ?   org.playframework                    2023-09..2026-05 3.0.12       |.............=======|
play.ws.standalone.xml  [owned by `com.typesafe.play` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * com.typesafe.play                    2022-01..2026-05 2.2.16       |..........==========|
  ?   org.playframework                    2023-09..2026-05 3.0.12       |.............=======|
okio  [owned by `com.squareup.okio` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * com.squareup.okio                    2018-02..2026-03 3.17.0       |..=================.|
  ?   com.datadoghq.okio                   2023-09..2023-09 1.17.6       |.............=......|
  ?   org.danbrough.okio                   2022-11..2022-11 3.3.0-alpha01 |............=.......|
  ?   io.github.fabianterhorst             2021-08..2021-08 3.0.0        |.........=..........|
okio.fakefilesystem  [owned by `com.squareup.okio` (groupId minus two segments is the module prefix); 2 other group(s) shade the name]
  ? * com.squareup.okio                    2021-01..2026-03 3.17.0       |........===========.|
  ?   org.danbrough.okio                   2022-11..2022-11 3.3.0-alpha01 |............=.......|
  ?   io.github.fabianterhorst             2021-08..2021-08 3.0.0        |.........=..........|
okhttp3.dnsoverhttps  [owned by `com.squareup.okhttp3` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * com.squareup.okhttp3                 2018-07..2025-11 5.2.3        |...================.|
  ?   com.huanli233.okhttp3-compat         2025-02..2025-02 5.0.0-p2     |................=...|
okhttp3.sse  [owned by `com.squareup.okhttp3` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * com.squareup.okhttp3                 2018-07..2025-11 5.2.3        |...================.|
  ?   com.huanli233.okhttp3-compat         2025-02..2025-02 5.0.0-p2     |................=...|
okhttp3.brotli  [owned by `com.squareup.okhttp3` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * com.squareup.okhttp3                 2019-08..2025-11 5.2.3        |.....==============.|
  ?   com.huanli233.okhttp3-compat         2025-02..2025-02 5.0.0-p2     |................=...|
okhttp3.coroutines  [owned by `com.squareup.okhttp3` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * com.squareup.okhttp3                 2022-03..2025-11 5.2.3        |..........=========.|
  ?   com.huanli233.okhttp3-compat         2025-02..2025-02 5.0.0-p2     |................=...|
okhttp3.java.net.cookiejar  [owned by `com.squareup.okhttp3` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * com.squareup.okhttp3                 2023-12..2025-11 5.2.3        |..............=====.|
  ?   com.huanli233.okhttp3-compat         2025-02..2025-02 5.0.0-p2     |................=...|
okhttp3.tls  [owned by `com.squareup.okhttp3` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * com.squareup.okhttp3                 2018-07..2025-11 5.2.3        |...================.|
  ?   com.huanli233.okhttp3-compat         2025-02..2025-02 5.0.0-p2     |................=...|
kotlin.test.junit  [owned by `org.jetbrains.kotlin` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.jetbrains.kotlin                 2020-07..2023-08 1.9.10       |.......=======......|
  ?   org.archguard.scanner                2022-06..2022-12 2.0.0-beta.5 |...........==.......|
  ?   org.jetbrains.lets-plot              2021-04..2021-06 2.0.4        |.........=..........|
cactus.maven.xml  [owned by `com.telenav.cactus` (groupId minus two segments is the module prefix); 0 other group(s) shade the name]
  ? * com.telenav.cactus                   2022-06..2022-11 1.5.49       |...........==.......|
  ?   com.telenav.lexakai                  2022-09..2022-10 1.0.13       |...........==.......|
```

## unclassified (60)

Multiple publishers with no natural-namespace owner present (the module name matches no publisher's groupId): a genuine collision the heuristic cannot settle. Proposal: keep the current owner, but review by hand.

| count | current owner -> proposed allowed |
|---:|---|
| 11 | `eu.europa.ec.joinup.sd-dss -> eu.europa.ec.joinup.sd-dss` |
| 5 | `io.github.spair -> io.github.spair` |
| 5 | `org.glassfish.jaxb -> org.glassfish.jaxb` |
| 3 | `com.squareup.okhttp3 -> com.squareup.okhttp3` |
| 2 | `com.github.vladimir-bukhtoyarov -> com.github.vladimir-bukhtoyarov` |
| 2 | `io.github.palexdev -> io.github.palexdev` |
| 2 | `org.apache.tomcat -> org.apache.tomcat` |
| 2 | `org.slf4j -> org.slf4j` |
| 1 | `com.corundumstudio.socketio -> com.corundumstudio.socketio` |
| 1 | `com.github.almasb -> com.github.almasb` |
| 1 | `com.github.seancfoley -> com.github.seancfoley` |
| 1 | `com.github.seeseemelk -> com.github.seeseemelk` |
| 1 | `com.google.http-client -> com.google.http-client` |
| 1 | `com.graphql-java -> com.graphql-java` |
| 1 | `com.guicedee.services -> com.guicedee.services` |

```
com.sun.codemodel  [no clear owner; `org.glassfish.jaxb` is earliest and most recent]
  ? * org.glassfish.jaxb                   2018-07..2026-05 4.0.9        |...=================|
  ?   cn.lzgabel.jaxb                      2022-03..2022-03 4.0.0        |..........=.........|
com.sun.codemodel.ac  [no clear owner; `org.glassfish.jaxb` is earliest and most recent]
  ? * org.glassfish.jaxb                   2019-10..2026-05 4.0.9        |.....===============|
  ?   cn.lzgabel.jaxb                      2022-03..2022-03 4.0.0        |..........=.........|
com.sun.xml.txw2  [no clear owner; `org.glassfish.jaxb` is earliest and most recent]
  ? * org.glassfish.jaxb                   2018-07..2026-05 4.0.9        |...=================|
  ?   org.uma.jmetal                       2025-12..2026-05 7.3          |..................==|
  ?   io.github.jeff-tian                  2026-02..2026-02 2.4.1        |..................=.|
  ?   ai.starlake                          2022-04..2025-05 1.3.5        |...........=======..|
  ?   com.jordansamhi                      2024-08..2024-08 1.1.8        |...............=....|
  ?   org.soot-oss                         2024-04..2024-04 4.5.0        |...............=....|
    + 3 more: com.yotpo, cn.lzgabel.jaxb, org.apache.servicemix.bundles
com.sun.xml.xsom  [no clear owner; `org.glassfish.jaxb` is earliest and most recent]
  ? * org.glassfish.jaxb                   2018-07..2026-05 4.0.9        |...=================|
  ?   cn.lzgabel.jaxb                      2022-03..2022-03 4.0.0        |..........=.........|
  ?   no.entur                             2020-06..2022-01 1.47         |.......====.........|
VirtualizedFX  [no clear owner; `io.github.palexdev` is earliest and most recent]
  ? * io.github.palexdev                   2022-03..2026-05 25.1.16      |..........==========|
  ?   org.glavo.materialfx                 2022-04..2022-04 11.2.6       |...........=........|
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
flying.saucer.pdf  [no clear owner; `org.xhtmlrenderer` is earliest and most recent]
  ? * org.xhtmlrenderer                    2024-09..2026-05 10.2.2       |...............=====|
  ?   io.github.openpdfsaucer              2025-03..2025-05 2.0.9        |................==..|
ihub.core  [no clear owner; `pub.ihub.lib` is earliest and most recent]
  ? * pub.ihub.lib                         2021-09..2026-05 1.7.7        |.........===========|
  ?   pub.ihub.integration                 2024-03..2025-05 0.1.12       |...............===..|
  ?   pub.ihub.module                      2024-04..2025-05 0.2.2        |...............===..|
jul.to.slf4j  [no clear owner; `org.slf4j` is earliest and most recent]
  ? * org.slf4j                            2019-02..2026-05 2.0.18       |....================|
  ?   de.codecentric                       2024-01..2026-02 3.3.0        |..............=====.|
  ?   net.finmath                          2025-11..2026-02 2.5.7        |..................=.|
  ?   io.github.davincilll                 2025-12..2025-12 1.0.4        |..................=.|
  ?   io.github.daone-dadp                 2025-11..2025-11 3.0.2        |..................=.|
  ?   io.kestra.plugin                     2024-10..2025-10 0.24.2       |................===.|
    + 10 more: io.github.tky0065, com.itxk.maven, io.github.srilathakarri, de.fraunhofer.iosb.ilt.faaast.registry, org.easypeelsecurity, io.github.tracedin, com.github.kaklakariada, com.tencent.cloud, org.conductoross, io.bdeploy
log4j  [no clear owner; `org.slf4j` is earliest and most recent]
  ? * org.slf4j                            2017-04..2026-05 2.0.18       |====================|
  ?   com.fluxninja.aperture               2023-01..2024-01 2.30.0       |............===.....|
org.apache.commons.beanutils2  [no clear owner; `org.onebusaway` is earliest and most recent]
  ? * org.onebusaway                       2025-05..2026-05 12.0.1       |.................===|
  ?   com.github.bordertech.wcomponents    2025-12..2026-01 1.5.39       |..................=.|
jcef  [no clear owner; `me.friwi` is earliest and most recent]
  ? * me.friwi                             2021-12..2026-05 jcef-d3de827+cef-146.0.10+g8219561+chromium-146.0.7680.179 |..........==========|
  ?   io.github.trethore                   2026-02..2026-04 jcef-65f9d7b+cef-146.0.10+g8219561+chromium-146.0.7680.179 |..................==|
jam.common  [no clear owner; `sk.annotation.library.jam` is earliest and most recent]
  ? * sk.annotation.library.jam            2022-01..2026-05 0.9.21       |..........==========|
  ?   sk.annotation.projects.signito       2022-12..2022-12 0.9.53       |............=.......|
java.servlet.jsp  [no clear owner; `org.apache.tomcat` is earliest and most recent]
  ? * org.apache.tomcat                    2020-11..2026-05 9.0.118      |........============|
  ?   com.heroku                           2024-05..2026-04 9.0.117.0    |...............=====|
java.el  [no clear owner; `org.apache.tomcat` is earliest and most recent]
  ? * org.apache.tomcat                    2020-11..2026-05 9.0.118      |........============|
  ?   com.heroku                           2024-10..2024-10 9.0.96.0     |................=...|
org.scala.lang.scala3.compiler  [no clear owner; `org.scala-lang` is earliest and most recent]
  ? * org.scala-lang                       2021-06..2026-04 3.3.8-RC1    |.........===========|
  ?   com.michaelpollmeier                 2022-10..2022-11 3.2.2-RC1-bin-20221101-d84007c-NIGHTLY+1-extensible-repl |............=.......|
imgui.binding  [no clear owner; `io.github.spair` is earliest and most recent]
  ? * io.github.spair                      2022-01..2026-04 1.92.0       |..........==========|
  ?   io.github.lionblazer                 2026-04..2026-04 1.92.5       |...................=|
imgui.lwjgl3  [no clear owner; `io.github.spair` is earliest and most recent]
  ? * io.github.spair                      2022-01..2026-04 1.92.0       |..........==========|
  ?   io.github.lionblazer                 2026-04..2026-04 1.92.5       |...................=|
imgui.natives.linux  [no clear owner; `io.github.spair` is earliest and most recent]
  ? * io.github.spair                      2022-03..2026-04 1.92.0       |..........==========|
  ?   io.github.lionblazer                 2026-04..2026-04 1.92.5       |...................=|
imgui.natives.macos  [no clear owner; `io.github.spair` is earliest and most recent]
  ? * io.github.spair                      2022-03..2026-04 1.92.0       |..........==========|
  ?   io.github.lionblazer                 2026-04..2026-04 1.92.5       |...................=|
imgui.natives.windows  [no clear owner; `io.github.spair` is earliest and most recent]
  ? * io.github.spair                      2022-03..2026-04 1.92.0       |..........==========|
  ?   io.github.lionblazer                 2026-04..2026-04 1.92.5       |...................=|
org.apache.commons.dbcp2  [no clear owner; `org.apache.tomee` is earliest and most recent]
  ? * org.apache.tomee                     2023-12..2026-04 10.1.5       |..............======|
  ?   org.apache.meecrowave                2025-10..2025-10 2.0.0        |..................=.|
  ?   net.ontopia                          2025-04..2025-07 5.5.2        |.................=..|
  ?   org.apache.openjpa                   2024-09..2025-05 4.1.1        |...............===..|
com.oracle.truffle.regex  [no clear owner; `org.graalvm.regex` is earliest and most recent]
  ? * org.graalvm.regex                    2018-10..2026-04 25.0.3       |...=================|
  ?   org.noear                            2024-09..2025-07 1.9.6        |................==..|
  ?   com.syncloop.middleware              2025-01..2025-01 1.7.1        |................=...|
com.jn.langx.java8  [no clear owner; `io.github.bes2008.solution.langx` is earliest and most recent]
  ? * io.github.bes2008.solution.langx     2024-01..2026-04 5.4.6.1      |..............======|
  ?   io.github.qhsword.langx              2025-11..2025-11 5.5.10       |..................=.|
com.jn.langx.security.gm.jca.bouncycastle  [no clear owner; `io.github.bes2008.solution.langx.security` is earliest and most recent]
  ? * io.github.bes2008.solution.langx.security 2024-01..2026-04 5.4.6.1      |..............======|
  ?   io.github.qhsword.langx.security     2025-11..2025-12 5.8.0        |..................=.|
org.dataloader  [no clear owner; `com.graphql-java` is earliest and most recent]
  ? * com.graphql-java                     2022-06..2026-03 0.0.0-2026-03-21T01-57-47-e5e5bf5 |...........=========|
  ?   com.liferay                          2025-05..2025-05 3.2.0.JAKARTA-LIFERAY-PATCHED-1 |.................=..|
inet.ipaddr  [no clear owner; `com.github.seancfoley` is earliest and most recent]
  ? * com.github.seancfoley                2018-11..2026-03 5.6.2        |....================|
  ?   me.confuser.banmanager.BanManagerLibs 2022-03..2026-01 7.10.0       |..........=========.|
  ?   com.dmetasoul                        2023-09..2024-07 0.28-2.6.1   |.............===....|
  ?   org.neo4j.procedure                  2022-05..2023-09 4.4.0.22     |...........===......|
  ?   org.geneweaver                       2023-03..2023-03 0.0.11       |............=.......|
  ?   org.odpi.egeria                      2023-01..2023-01 3.15         |............=.......|
    + 2 more: de.ipk-gatersleben, ch.cern
dss_pki_factory_jaxb  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2023-11..2026-03 6.4          |..............=====.|
  ?   org.digidoc4j.dss                    2024-02..2025-11 6.2.d4j.1    |..............=====.|
jpms_dss_evidence_record_asn1  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2024-07..2026-03 6.4          |...............====.|
  ?   org.digidoc4j.dss                    2025-11..2025-11 6.2.d4j.1    |..................=.|
jpms_dss_jacoco_coverage  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2023-02..2026-03 6.4          |............=======.|
  ?   org.digidoc4j.dss                    2024-02..2025-11 6.2.d4j.1    |..............=====.|
jpms_dss_specs_xmlers  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2023-11..2026-03 6.4          |..............=====.|
  ?   org.digidoc4j.dss                    2024-02..2025-11 6.2.d4j.1    |..............=====.|
jpms_dss_pki_factory  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2023-11..2026-03 6.4          |..............=====.|
  ?   org.digidoc4j.dss                    2024-02..2025-11 6.2.d4j.1    |..............=====.|
jpms_dss_evidence_record_common  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2023-11..2026-03 6.4          |..............=====.|
  ?   org.digidoc4j.dss                    2024-02..2025-11 6.2.d4j.1    |..............=====.|
jpms_dss_evidence_record_xml  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2023-11..2026-03 6.4          |..............=====.|
  ?   org.digidoc4j.dss                    2024-02..2025-11 6.2.d4j.1    |..............=====.|
jpms_dss_xml  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2023-11..2026-03 6.4          |..............=====.|
  ?   org.digidoc4j.dss                    2024-02..2025-11 6.2.d4j.1    |..............=====.|
jpms_dss_xml_common  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2023-11..2026-03 6.4          |..............=====.|
  ?   org.digidoc4j.dss                    2024-02..2025-11 6.2.d4j.1    |..............=====.|
jpms_dss_pdfa  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2023-02..2026-03 6.4          |............=======.|
  ?   org.digidoc4j.dss                    2024-02..2025-11 6.2.d4j.1    |..............=====.|
jpms_dss_validation  [no clear owner; `eu.europa.ec.joinup.sd-dss` is earliest and most recent]
  ? * eu.europa.ec.joinup.sd-dss           2024-07..2026-03 6.4          |...............====.|
  ?   org.digidoc4j.dss                    2025-11..2025-11 6.2.d4j.1    |..................=.|
com.utool  [no clear owner; `io.gitee.shallwecode` is earliest and most recent]
  ? * io.gitee.shallwecode                 2025-05..2026-03 1.5.0        |.................==.|
  ?   io.github.shallwecodex               2025-05..2025-05 1.0.2        |.................=..|
jcifs  [no clear owner; `org.codelibs` is earliest and most recent]
  ? * org.codelibs                         2022-04..2026-02 3.0.2        |...........========.|
  ?   io.gitee.pickled_vegetables          2023-05..2023-05 2.2.0        |.............=......|
com.almasb.fxgl.gameplay  [no clear owner; `com.github.almasb` is earliest and most recent]
  ? * com.github.almasb                    2021-04..2026-02 25.0.1       |.........==========.|
  ?   io.github.chengenzhao                2026-01..2026-01 26.4         |..................=.|
netty.socketio  [no clear owner; `com.corundumstudio.socketio` is earliest and most recent]
  ? * com.corundumstudio.socketio          2024-01..2026-02 2.0.14       |..............=====.|
  ?   codes.oss.socketio                   2025-04..2025-04 2.0.14       |.................=..|
  ?   io.github.opensabe-tech              2024-08..2024-08 2.0.12       |...............=....|
com.google.api.client  [no clear owner; `com.google.http-client` is earliest and most recent]
  ? * com.google.http-client               2018-10..2026-01 2.1.0        |...================.|
  ?   com.google.cloud.bigtable            2020-07..2020-11 1.17.0       |.......==...........|
jetty.servlet.api  [no clear owner; `org.eclipse.jetty.toolchain` is earliest and most recent]
  ? * org.eclipse.jetty.toolchain          2019-02..2026-01 4.0.9        |....===============.|
  ?   ch.reportingsoft.birt                2025-04..2025-04 4.0.6        |.................=..|
  ?   io.prometheus.cloudwatch             2024-08..2024-08 0.16.0       |...............=....|
  ?   org.cip4.tools.jdfutility            2022-01..2022-01 1.7.1        |..........=.........|
mockwebserver3  [no clear owner; `com.squareup.okhttp3` is earliest and most recent]
  ? * com.squareup.okhttp3                 2021-01..2025-11 5.2.3        |........===========.|
  ?   com.huanli233.okhttp3-compat         2025-02..2025-02 5.0.0-p2     |................=...|
mockwebserver3.junit4  [no clear owner; `com.squareup.okhttp3` is earliest and most recent]
  ? * com.squareup.okhttp3                 2021-01..2025-11 5.2.3        |........===========.|
  ?   com.huanli233.okhttp3-compat         2025-02..2025-02 5.0.0-p2     |................=...|
mockwebserver3.junit5  [no clear owner; `com.squareup.okhttp3` is earliest and most recent]
  ? * com.squareup.okhttp3                 2021-01..2025-11 5.2.3        |........===========.|
  ?   com.huanli233.okhttp3-compat         2025-02..2025-02 5.0.0-p2     |................=...|
simpletimeapi  [no clear owner; `io.github.fontysvenlo` is earliest and most recent]
  ? * io.github.fontysvenlo                2023-08..2025-10 1.0.0        |.............======.|
  ?   io.github.fontysvenlo.alda           2023-09..2023-09 2.5          |.............=......|
com.sun.jna.platform  [no clear owner; `net.java.dev.jna` is earliest and most recent]
  ? * net.java.dev.jna                     2018-10..2025-09 5.18.1       |...================.|
  ?   ch.reportingsoft.birt                2025-04..2025-07 5.17.0       |.................=..|
persistence.api  [no clear owner; `io.ebean` is earliest and most recent]
  ? * io.ebean                             2019-03..2025-04 3.1          |....==============..|
  ?   one.gfw                              2023-03..2023-03 3.0          |............=.......|
jpms_dss_validation_policy  [no clear owner; `org.digidoc4j.dss` is earliest and most recent]
  ? * org.digidoc4j.dss                    2019-12..2025-04 6.0.1.d4j.1  |......============..|
  ?   eu.europa.ec.joinup.sd-dss           2022-10..2025-03 6.0.1        |............=====...|
com.amazon.corretto.crypto.provider  [no clear owner; `software.amazon.cryptools` is earliest and most recent]
  ? * software.amazon.cryptools            2019-07..2025-03 2.5.0        |.....============...|
  ?   io.github.luneo7                     2022-09..2022-09 1.6.1        |...........=........|
  ?   org.jfrog.buildinfo                  2022-01..2022-01 2.33.2       |..........=.........|
MaterialFX  [no clear owner; `io.github.palexdev` is earliest and most recent]
  ? * io.github.palexdev                   2021-06..2025-02 21.18.0-alpha |.........========...|
  ?   org.glavo.materialfx                 2022-04..2022-04 11.13.4      |...........=........|
be.seeseemelk.mockbukkit  [no clear owner; `com.github.seeseemelk` is earliest and most recent]
  ? * com.github.seeseemelk                2022-06..2024-10 3.133.2      |...........======...|
  ?   com.mineplex.studio                  2024-10..2024-10 1.21.1-R0.1-SNAPSHOT-26 |................=...|
io.github.bucket4j.parent  [no clear owner; `com.github.vladimir-bukhtoyarov` is earliest and most recent]
  ? * com.github.vladimir-bukhtoyarov      2021-04..2024-04 8.0.1        |.........=======....|
  ?   com.bucket4j                         2022-07..2023-10 8.6.0        |...........====.....|
io.github.bucket4j.infinispan8  [no clear owner; `com.github.vladimir-bukhtoyarov` is earliest and most recent]
  ? * com.github.vladimir-bukhtoyarov      2019-11..2024-04 8.0.1        |......==========....|
  ?   com.bucket4j                         2022-07..2024-03 8.10.1       |...........====.....|
com.sun.tools.txw2  [no clear owner; `org.glassfish.jaxb` is earliest and most recent]
  ? * org.glassfish.jaxb                   2019-10..2023-06 4.0.3        |......========......|
  ?   cn.lzgabel.jaxb                      2022-03..2022-03 4.0.0        |..........=.........|
javax.persistence  [no clear owner; `org.datanucleus` is earliest and most recent]
  ? * org.datanucleus                      2018-07..2021-08 2.2.4        |...=======..........|
  ?   org.apache.geronimo.specs            2020-03..2020-03 1.1          |......=.............|
javax.websocket.api  [no clear owner; `com.guicedee.services` is earliest and most recent]
  ? * com.guicedee.services                2019-11..2020-11 1.0.20.2-jre15 |......===...........|
  ?   org.apache.tomcat                    2020-09..2020-10 9.0.39       |.......=............|
```

