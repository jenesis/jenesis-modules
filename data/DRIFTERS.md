# Module ownership drifters

Generated 2026-06-12. A module *drifts* when more than one groupId publishes the name and its `owners.tsv` does not yet name every publisher (no `owners.tsv`, or one that leaves some publishing groupId neither `allowed` nor `blocked`). Resolving a drift means deciding each groupId via `SetOwners` (which writes `allowed`/`blocked`); a fully-named module drops off this list.

| Category | Unresolved | Resolved via owners.tsv |
|---|---:|---:|
| explicit-rules | 0 | 353 |
| republisher | 0 | 146 |
| migration | 0 | 1286 |
| fork | 0 | 448 |
| shaded | 0 | 1272 |
| tld-dropped | 0 | 15 |
| two-segments | 9 | 0 |
| unclassified | 43 | 0 |
| **total** | **52** | **3520** |

The table covers all **3572** multi-owner modules (of **36334** modules scanned).

Timeline axis spans 2017-01 .. 2026-06 (today). Per group: decision `A`=allowed `B`=blocked `?`=undecided, `*`=current owner, then the publication range, latest version, and a `=` activity bar across the axis.

## explicit-rules (0)

Hand-curated overrides: a module matching an explicit rule is assigned to a fixed owner groupId regardless of the heuristic. Proposal: allow that owner, block the rest.

## republisher (0)

Earliest owner is foreign to the module name while a natural-namespace owner is also present (shaded / repackaged jars). Proposal: allow the natural owner, block the republisher.

## migration (0)

The publishing groupId handed off over time: the old coordinate went dormant, a newer one took over (a rename or a relocation). Proposal: allow both old and new so history stays resolvable and `latest` is current.

## fork (0)

A cross-org coordinate publishes the same name while the original owner is still active. Proposal: keep the original owner, block the fork.

## shaded (0)

The natural-namespace owner (the module name falls under its groupId) is the earliest and most-recent publisher; every other group merely shades or bundles the name under its own coordinate. Proposal: allow the natural owner, block the rest. Resolution is unchanged; this just records the decision so the module drops off the report.

## tld-dropped (0)

The dominant owner's groupId with its top-level domain (first segment) dropped is the module-name prefix. Proposal: allow that owner, block the rest.

## two-segments (9)

The dominant owner's groupId with its first two segments dropped is the module-name prefix. Proposal: allow that owner, block the rest.

| count | current owner -> new owner(s) |
|---:|---|
| 1 | `com.telenav.cactus -> com.telenav.cactus,com.telenav.lexakai` |
| 1 | `org.jetbrains.kotlin -> org.jetbrains.kotlin,org.jetbrains.lets-plot` |

```
jackson.datatype.pcollections  [owned by `com.fasterxml.jackson.datatype` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * com.fasterxml.jackson.datatype       2019-07..2026-06 2.22.0       |.....===============|
  ?   tools.jackson.datatype               2025-03..2026-05 3.1.3        |................====|
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

## unclassified (43)

Multiple publishers with no natural-namespace owner present (the module name matches no publisher's groupId): a genuine collision the heuristic cannot settle. Proposal: keep the current owner, but review by hand.

```
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
simpletimeapi  [no clear owner; `io.github.fontysvenlo` is earliest and most recent]
  ? * io.github.fontysvenlo                2023-08..2025-10 1.0.0        |.............======.|
  ?   io.github.fontysvenlo.alda           2023-09..2023-09 2.5          |.............=......|
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
javax.persistence  [no clear owner; `org.datanucleus` is earliest and most recent]
  ? * org.datanucleus                      2018-07..2021-08 2.2.4        |...=======..........|
  ?   org.apache.geronimo.specs            2020-03..2020-03 1.1          |......=.............|
javax.websocket.api  [no clear owner; `com.guicedee.services` is earliest and most recent]
  ? * com.guicedee.services                2019-11..2020-11 1.0.20.2-jre15 |......===...........|
  ?   org.apache.tomcat                    2020-09..2020-10 9.0.39       |.......=............|
```

## Reassigned and widened ownership

Modules whose resolved owner differs from the implicit first-publisher owner once `owners.tsv` is applied. 🔀 reassigned (150): the first publisher was replaced by a different owner. ➕ widened (605): extra legal owners were allowed alongside the first publisher (e.g. a groupId migration or a co-maintained project). Modules where `owners.tsv` only confirms the first publisher are not listed.

```
🔀  com.sun.xml.bind  org.glassfish.jaxb -> com.sun.xml.bind
🔀  io.whitfin.siphash  com.io7m.repackage.io.whitfin -> io.whitfin
🔀  jakarta.activation  com.sun.activation -> jakarta.activation
🔀  jakarta.concurrency  jakarta.enterprise.concurrent -> jakarta.concurrency
🔀  jakarta.mail  com.sun.mail -> jakarta.mail
🔀  jakarta.servlet.jsp  org.apache.tomcat -> jakarta.servlet.jsp
🔀  net.evonit.thumbnailator2  io.github.evonit -> net.evonit
🔀  org.commonmark.ext.front.matter  com.atlassian.commonmark -> org.commonmark
🔀  org.eclipse.birt  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.axis.overlay  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.build  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.chart.cshelp  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.chart.device.extension  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.chart.device.pdf  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.chart.device.svg  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.chart.device.swt  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.chart.doc.isv  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.chart.engine  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.chart.engine.extension  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.chart.examples  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.chart.examples.core  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.chart.integration.wtp.ui  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.chart.reportitem  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.chart.reportitem.ui  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.chart.tests  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.chart.ui  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.chart.ui.extension  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.chart.viewer  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.core.script.function  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.core.testhelper  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.core.tests  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.core.ui  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.cshelp  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.data.aggregation  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.data.oda.mongodb  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.data.oda.mongodb.ui  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.data.oda.pojo  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.data.oda.pojo.tests  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.data.oda.pojo.ui  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.data.tests  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.doc  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.doc.isv  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.example  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.integration.wtp.ui  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.jetty.overlay  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.me.prettyprint.hector  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.data.adapter  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.data.bidi.utils  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.data.bidi.utils.ui  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.data.oda.excel  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.data.oda.excel.ui  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.data.oda.hive  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.data.oda.hive.ui  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.data.oda.jdbc  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.data.oda.jdbc.dbprofile  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.data.oda.jdbc.dbprofile.sampledb  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.data.oda.jdbc.dbprofile.sampledb.ui  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.data.oda.jdbc.dbprofile.ui  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.data.oda.jdbc.tests  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.data.oda.jdbc.ui  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.data.oda.sampledb  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.data.oda.sampledb.ui  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.data.oda.xml  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.data.oda.xml.ui  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.debug.core  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.debug.ui  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.designer.core  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.designer.samplereports  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.designer.tests  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.designer.ui  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.designer.ui.cubebuilder  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.designer.ui.data  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.designer.ui.editor.script  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.designer.ui.editor.xml.wtp  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.designer.ui.editors  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.designer.ui.editors.schematic  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.designer.ui.ide  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.designer.ui.lib  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.designer.ui.lib.explorer  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.designer.ui.preview  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.designer.ui.preview.static_html  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.designer.ui.preview.test  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.designer.ui.preview.web  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.designer.ui.rcp  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.designer.ui.samples.ide  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.designer.ui.samples.rcp  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.designer.ui.samplesview  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.designer.ui.views  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.engine  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.engine.dataextraction  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.engine.dataextraction.csv  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.engine.dataextraction.csv.tests  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.engine.emitter.config  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.engine.emitter.config.docx  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.engine.emitter.config.excel  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.engine.emitter.config.html  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.engine.emitter.config.odp  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.engine.emitter.config.ods  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.engine.emitter.config.odt  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.engine.emitter.config.pdf  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.engine.emitter.config.postscript  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.engine.emitter.config.ppt  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.engine.emitter.config.pptx  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.engine.emitter.config.wpml  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.engine.emitter.docx  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.engine.emitter.html  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.engine.emitter.html.tests  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.engine.emitter.odp  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.engine.emitter.ods  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.engine.emitter.odt  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.engine.emitter.pdf  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.engine.emitter.pdf.tests  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.engine.emitter.postscript  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.engine.emitter.postscript.tests  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.engine.emitter.ppt  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.engine.emitter.pptx  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.engine.emitter.pptx.tests  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.engine.emitter.prototype.excel  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.engine.emitter.prototype.excel.tests  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.engine.emitter.wpml  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.engine.fonts  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.engine.odf  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.engine.ooxml  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.engine.script.javascript  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.engine.testhelper  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.engine.tests  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.item.crosstab.core  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.item.crosstab.core.tests  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.item.crosstab.ui  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.model  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.model.adapter.oda  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.model.adapter.oda.tests  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.model.testhelper  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.model.testresources  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.model.tests  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.modelextension.tests  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.tests.chart  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.tests.engine  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.tests.engine.emitter.html  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.tests.model  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.viewer  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.report.viewer.tests  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.resources  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.tests.core  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.birt.tests.data  io.github.reporting-solutions -> org.eclipse.birt
🔀  org.eclipse.emf.ecore.change  com.innoventsolutions.birt.runtime -> org.eclipse.emf
🔀  spring.security.maven.plugin  io.spring.gradle -> org.springframework
🔀  spring.security.project.plugin  io.spring.security.gradle -> org.springframework
🔀  spring.security.release.tools.core  io.spring.gradle -> org.springframework
🔀  zipkin2.reporter.kafka  org.apache.zipkin.reporter2 -> io.zipkin.reporter2
➕  Mal4J  com.kttdevelopment -> com.kttdevelopment, dev.katsute
➕  aerogel  io.github.derklaro -> dev.derklaro.aerogel, io.github.derklaro
➕  aerogel.auto  io.github.derklaro -> dev.derklaro.aerogel, io.github.derklaro
➕  aopalliance  com.jwebmp.jre11 -> com.guicedee.modules.services, com.jwebmp.jre11
➕  be.webtechie.piheaders  be.webtechie -> be.webtechie, com.pi4j
➕  builderCore  com.github.srujankujmar -> com.github.srujankujmar, io.hyscale
➕  builderservices  com.github.srujankujmar -> com.github.srujankujmar, io.hyscale
➕  cache.annotations.ri.common  com.jwebmp.thirdparty.jcache -> com.guicedee.modules.services, com.jwebmp.thirdparty.jcache
➕  cache.annotations.ri.guice  com.jwebmp.thirdparty.jcache -> com.guicedee.modules.services, com.jwebmp.thirdparty.jcache
➕  cache.api  com.jwebmp.thirdparty.jcache -> com.guicedee.modules.services, com.jwebmp.thirdparty.jcache
➕  ch.randelshofer.fastdoubleparser  ch.randelshofer -> ch.randelshofer, software.amazon.smithy.java
➕  co.paralleluniverse.quasar.core  co.paralleluniverse -> co.paralleluniverse, com.nhn.gameanvil
➕  com.azure.json  com.azure -> com.azure, com.microsoft.azure.kusto
➕  com.carrotsearch.hppc  com.carrotsearch -> com.carrotsearch, org.teavm
➕  com.computinglaboratory.resourcewatcher  com.computinglaboratory -> com.computinglaboratory, io.github.kamilszewc
➕  com.datastax.oss.protocol  com.datastax.oss -> com.datastax.oss, com.scylladb
➕  com.dlsc.formsfx  com.dlsc.formsfx -> com.dlsc.formsfx, com.github.stefanofornari
➕  com.formdev.flatlaf  com.formdev -> com.formdev, dev.robocode.tankroyale
➕  com.github.benmanes.caffeine  com.github.ben-manes.caffeine -> com.github.ben-manes.caffeine, io.pebbletemplates
➕  com.github.benmanes.caffeine.guava  com.github.ben-manes.caffeine -> com.github.ben-manes.caffeine, io.github.ck-jesse
➕  com.github.jinahya.database.metadata.bind  com.github.jinahya -> com.github.jinahya, io.github.jinahya
➕  com.github.jinahya.kftc.financial.institution.info  com.github.jinahya -> com.github.jinahya, io.github.jinahya
➕  com.github.kokorin.jaffree  com.github.kokorin.jaffree -> com.github.kokorin.jaffree, io.v47.jaffree
➕  com.github.maeda6uiui.mechtatel.core  com.github.dabasan -> com.github.dabasan, io.github.maeda6uiui
➕  com.github.maeda6uiui.mechtatel.logging  com.github.dabasan -> com.github.dabasan, io.github.maeda6uiui
➕  com.github.maeda6uiui.mechtatel.natives  com.github.dabasan -> com.github.dabasan, io.github.maeda6uiui
➕  com.github.maeda6uiui.mechtatel.natives.linux  com.github.dabasan -> com.github.dabasan, io.github.maeda6uiui
➕  com.github.maeda6uiui.mechtatel.natives.windows  com.github.dabasan -> com.github.dabasan, io.github.maeda6uiui
➕  com.google.auto.service  com.github.sidneibjunior -> com.github.sidneibjunior, dev.ikm.jpms
➕  com.google.common.util.concurrent.internal  com.google.guava -> com.google.guava, de.bsommerfeld.pathetic
➕  com.google.guice  com.google.inject -> com.google.inject, com.guicedee.modules.services
➕  com.google.guice.extensions.assistedinject  com.google.inject.extensions -> com.google.inject.extensions, com.guicedee.modules.services
➕  com.google.guice.extensions.grapher  com.google.inject.extensions -> com.google.inject.extensions, com.guicedee.modules.services
➕  com.google.guice.extensions.jmx  com.google.inject.extensions -> com.google.inject.extensions, com.guicedee.modules.services
➕  com.google.guice.extensions.jndi  com.google.inject.extensions -> com.google.inject.extensions, com.guicedee.modules.services
➕  com.googlecode.javaewah  com.googlecode.javaewah -> com.googlecode.javaewah, org.meyvn
➕  com.headius.invokebinder  com.headius -> com.headius, org.sahli.asciidoc.confluence.publisher
➕  com.install4j.runtime  com.iamsoft -> com.iamsoft, com.install4j
➕  com.jcraft.jsch  com.github.mwiede -> com.github.mwiede, com.pesitwizard.connector
➕  com.jfoenix  com.jfoenix -> com.jfoenix, org.rationalityfrontline.workaround
➕  com.jwebmp.guicedpersistence.wildfly  com.jwebmp.jre10 -> com.guicedee.persistence, com.jwebmp.jre10
➕  com.kohlschutter.junixsocket.core  com.kohlschutter.junixsocket -> com.kohlschutter.junixsocket, com.sbbsystems.flink
➕  com.lmax.disruptor  com.lmax -> com.lmax, software.xdev.mockserver
➕  com.paypal.sdk  com.paypal.sdk -> com.paypal.sdk, io.sdks
➕  com.querydsl.apt  com.querydsl -> com.querydsl, io.github.openfeign.querydsl
➕  com.querydsl.codegen  com.querydsl -> com.querydsl, io.github.openfeign.querydsl
➕  com.querydsl.codegen.utils  com.querydsl -> com.querydsl, io.github.openfeign.querydsl
➕  com.querydsl.collections  com.querydsl -> com.querydsl, io.github.openfeign.querydsl
➕  com.querydsl.core  com.querydsl -> com.querydsl, io.github.openfeign.querydsl
➕  com.querydsl.core.group.guava  com.querydsl -> com.querydsl, io.github.openfeign.querydsl
➕  com.querydsl.hibernate.search  com.querydsl -> com.querydsl, io.github.openfeign.querydsl
➕  com.querydsl.jpa  com.querydsl -> com.querydsl, io.github.openfeign.querydsl
➕  com.querydsl.jpa.codegen  com.querydsl -> com.querydsl, io.github.openfeign.querydsl
➕  com.querydsl.lucene3  com.querydsl -> com.querydsl, io.github.openfeign.querydsl
➕  com.querydsl.lucene4  com.querydsl -> com.querydsl, io.github.openfeign.querydsl
➕  com.querydsl.lucene5  com.querydsl -> com.querydsl, io.github.openfeign.querydsl
➕  com.querydsl.mongodb  com.querydsl -> com.querydsl, io.github.openfeign.querydsl
➕  com.querydsl.spatial  com.querydsl -> com.querydsl, io.github.openfeign.querydsl
➕  com.querydsl.sql  com.querydsl -> com.querydsl, io.github.openfeign.querydsl
➕  com.querydsl.sql.codegen  com.querydsl -> com.querydsl, io.github.openfeign.querydsl
➕  com.querydsl.sql.spatial  com.querydsl -> com.querydsl, io.github.openfeign.querydsl
➕  com.spotify.futures  com.spotify -> com.spotify, org.hyperledger.fabric-sdk-java
➕  com.sshtools.common.logger  com.sshtools -> com.sshtools, solutions.a2.oracle
➕  com.sshtools.synergy.client  com.sshtools -> com.sshtools, solutions.a2.oracle
➕  com.sun.istack.runtime  com.sun.istack -> com.intuit.quickbooks-online, com.sun.istack
➕  com.sun.xml.fastinfoset  com.sun.xml.fastinfoset -> com.sun.xml.fastinfoset, net.corda
➕  commons  com.github.srujankujmar -> com.github.srujankujmar, io.github.rassafel
➕  controller.service  com.github.srujankujmar -> com.github.srujankujmar, io.hyscale
➕  core  pro.shuangxi.framework.openfx -> org.apereo.cas, pro.shuangxi.framework.openfx
➕  dawdler.boot.classloader  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.boot.classloader.core  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.boot.server  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.boot.web  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.boot.web.undertow  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.boot.web.undertow.jsp  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.cache.caffeine  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.cache.core  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.cache.jedis  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.circuit.breaker  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.client  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.client.plug.config.center.consul  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.client.plug.dao  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.client.plug.db  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.client.plug.discovery.center.consul  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.client.plug.discovery.center.zookeeper  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.client.plug.es  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.client.plug.jedis  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.client.plug.load  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.client.plug.local.service  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.client.plug.mybatis  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.client.plug.rabbitmq  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.client.plug.remote.service  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.client.plug.schedule  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.client.plug.session  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.client.plug.validator  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.client.plug.velocity  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.client.plug.web  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.config.center.consul.core  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.config.center.core  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.core  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.dao.core  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.db.core  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.discovery.center.consul.core  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.discovery.center.core  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.discovery.center.server.plug  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.discovery.center.zookeeper.core  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.distributed.transaction.api  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.distributed.transaction.compensator  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.distributed.transaction.core  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.es.core  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.jakarta.fileupload  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.jedis.core  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.load.bean  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.local.service.core  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.mybatis.core  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.pinpoint.plug  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.rabbitmq.core  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.remote.service.core  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.schedule.core  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.serialization  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.server  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.server.logback  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.server.plug.config.center.consul  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.server.plug.dao  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.server.plug.db  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.server.plug.discovery.center.consul  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.server.plug.discovery.center.zookeeper  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.server.plug.es  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.server.plug.jedis  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.server.plug.load  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.server.plug.local.service  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.server.plug.mybatis  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.server.plug.rabbitmq  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.server.plug.remote.service  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.server.plug.schedule  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.service.core  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  dawdler.util  io.github.dawdler-series -> club.dawdler, io.github.dawdler-series
➕  deeplearning4j.core  org.deeplearning4j -> es.upm.etsisi, org.deeplearning4j
➕  deployer.services  com.github.srujankujmar -> com.github.srujankujmar, io.hyscale
➕  deployerModel  com.github.srujankujmar -> com.github.srujankujmar, io.hyscale
➕  dev.failsafe.core  dev.failsafe -> dev.failsafe, org.apache.iceberg
➕  dockerfilegencore  com.github.srujankujmar -> com.github.srujankujmar, io.hyscale
➕  dockerfilegenservices  com.github.srujankujmar -> com.github.srujankujmar, io.hyscale
➕  eu.toop.connector.mem.def  eu.toop -> com.helger.dcng, eu.toop
➕  glide.api  software.amazon.glide -> io.valkey, software.amazon.glide
➕  graphql.java.tools  com.graphql-java-kickstart -> com.graphql-java-kickstart, io.github.graphql-java-kickstart
➕  hla.rti1516e  io.github.tno-mst -> io.github.tno-mst, nl.tno
➕  info.movito.themoviedbapi  com.github.holgerbrandl -> com.github.holgerbrandl, uk.co.conoregan
➕  info.picocli  info.picocli -> info.picocli, io.spicelabs
➕  io.avaje.junit  org.avaje -> io.avaje, org.avaje
➕  io.ebean.externalmapping.xml  org.avaje -> io.ebean, org.avaje
➕  io.ebean.querybean  org.avaje -> io.ebean, org.avaje
➕  io.ebean.test  org.avaje -> io.ebean, org.avaje
➕  io.github.bucket4j.caffeine  com.github.vladimir-bukhtoyarov -> com.bucket4j, com.github.vladimir-bukhtoyarov
➕  io.github.bucket4j.coherence  com.github.vladimir-bukhtoyarov -> com.bucket4j, com.github.vladimir-bukhtoyarov
➕  io.github.bucket4j.core  com.github.vladimir-bukhtoyarov -> com.bucket4j, com.github.vladimir-bukhtoyarov
➕  io.github.bucket4j.hazelcast  com.github.vladimir-bukhtoyarov -> com.bucket4j, com.github.vladimir-bukhtoyarov
➕  io.github.bucket4j.hazelcast3  com.github.vladimir-bukhtoyarov -> com.bucket4j, com.github.vladimir-bukhtoyarov
➕  io.github.bucket4j.ignite  com.github.vladimir-bukhtoyarov -> com.bucket4j, com.github.vladimir-bukhtoyarov
➕  io.github.bucket4j.infinispan  com.github.vladimir-bukhtoyarov -> com.bucket4j, com.github.vladimir-bukhtoyarov
➕  io.github.bucket4j.jcache  com.github.vladimir-bukhtoyarov -> com.bucket4j, com.github.vladimir-bukhtoyarov
➕  io.github.bucket4j.mysql  com.github.vladimir-bukhtoyarov -> com.bucket4j, com.github.vladimir-bukhtoyarov
➕  io.github.bucket4j.postgresql  com.github.vladimir-bukhtoyarov -> com.bucket4j, com.github.vladimir-bukhtoyarov
➕  io.github.bucket4j.redis  com.github.vladimir-bukhtoyarov -> com.bucket4j, com.github.vladimir-bukhtoyarov
➕  io.github.classgraph  io.github.classgraph -> io.github.classgraph, org.finos.legend.engine
➕  io.grpc  io.helidon.grpc -> io.grpc, io.helidon.grpc
➕  io.smallrye.common.constraint  io.smallrye.common -> io.github.danielgp-eu, io.smallrye.common
➕  io.swagger.v3.core  io.swagger.core.v3 -> io.github.vpelikh, io.swagger.core.v3
➕  io.swagger.v3.jaxrs2  io.swagger.core.v3 -> io.github.vpelikh, io.swagger.core.v3
➕  io.swagger.v3.jaxrs2.integration  io.swagger.core.v3 -> io.github.vpelikh, io.swagger.core.v3
➕  io.swagger.v3.jaxrs2.integration.servlet  io.swagger.core.v3 -> io.github.vpelikh, io.swagger.core.v3
➕  io.swagger.v3.oas.annotations  io.swagger.core.v3 -> io.github.vpelikh, io.swagger.core.v3
➕  io.swagger.v3.oas.integration  io.swagger.core.v3 -> io.github.vpelikh, io.swagger.core.v3
➕  io.swagger.v3.oas.models  io.swagger.core.v3 -> io.github.vpelikh, io.swagger.core.v3
➕  it.auties.whatsapp4j  io.github.angleto -> com.github.auties00, io.github.angleto
➕  it.unimi.dsi.fastutil  it.unimi.dsi -> io.github.lionblazer, it.unimi.dsi
➕  jakarta.annotation  jakarta.annotation -> io.quarkus, jakarta.annotation
➕  jakarta.cdi  jakarta.enterprise -> jakarta.cdi, jakarta.enterprise
➕  jakarta.cdi.el  jakarta.enterprise -> jakarta.cdi, jakarta.enterprise
➕  jakarta.cdi.lang.model  jakarta.enterprise -> jakarta.cdi, jakarta.enterprise
➕  jakarta.ejb  com.guicedee.services -> com.guicedee.services, com.manorrock.flounder
➕  jakarta.el  jakarta.el -> jakarta.el, org.open-metadata
➕  jakarta.el.api  jakarta.el -> jakarta.el, org.apache.tomcat
➕  jakarta.faces  com.guicedee.services -> com.guicedee.services, jakarta.faces
➕  jakarta.inject  jakarta.inject -> com.google.gerrit, jakarta.inject
➕  jakarta.json  jakarta.json -> jakarta.json, org.eclipse.parsson
➕  jakarta.jws  jakarta.jws -> com.guicedee.services, jakarta.jws
➕  jakarta.messaging  jakarta.jms -> jakarta.jms, org.apache.storm
➕  jakarta.resource  jakarta.resource -> jakarta.resource, org.jboss.ironjacamar
➕  jakarta.security.auth.message  jakarta.authentication -> jakarta.authentication, org.apache.tomcat
➕  jakarta.websocket.api  jakarta.websocket -> com.guicedee.services, jakarta.websocket
➕  jakarta.ws.rs  jakarta.ws.rs -> jakarta.ws.rs, org.jboss.narayana.lra
➕  java.annotation  javax.annotation -> javax.annotation, org.apache.tomcat
➕  java.base  com.rover12421.android.hide -> com.kohlschutter.jacline, com.rover12421.android.hide
➕  java.inject  io.github.pustike -> com.erudika, io.github.pustike
➕  java.json  javax.json -> javax.json, org.choco-solver
➕  java.json.bind  javax.json.bind -> javax.json.bind, org.open-metadata
➕  java.mail  com.sun.mail -> com.sun.mail, net.iotgw
➕  java.measure  javax.measure -> javax.measure, org.locationtech.geomesa
➕  java.money  javax.money -> dev.zabricraft, javax.money
➕  java.persistence  javax.persistence -> com.guicedee.services, javax.persistence
➕  java.rmi  fr.inria.gforge.spoon -> fr.inria.gforge.spoon, org.wso2.carbon.automation
➕  java.security.auth.message  jakarta.security.auth.message -> jakarta.security.auth.message, org.apache.tomcat
➕  java.security.jacc  jakarta.security.jacc -> jakarta.authorization, jakarta.security.jacc
➕  java.servlet  jakarta.servlet -> jakarta.servlet, org.apache.tomcat
➕  java.transaction  org.jboss.spec.javax.transaction -> com.guicedee.services, org.jboss.spec.javax.transaction
➕  java.validation  javax.validation -> com.guicedee.services, javax.validation
➕  java.ws.rs  javax.ws.rs -> javax.ws.rs, org.jboss.pnc.build-agent
➕  java.xml.bind  javax.xml.bind -> com.yahoo.vespa, javax.xml.bind
➕  javax.inject  com.jwebmp -> com.jwebmp, dev.ikm.jpms
➕  javax.jms  com.jwebmp.thirdparty.jms -> com.guicedee.services, com.jwebmp.thirdparty.jms
➕  javax.jws  javax.jws -> com.guicedee.services, javax.jws
➕  javax.servlet.api  com.guicedee.services -> com.guicedee.services, org.apache.tomcat
➕  javax.servlet.jsp.api  com.guicedee.services -> com.guicedee.services, org.apache.tomcat
➕  jbase  net.sf.xtext-jbase -> io.github.lorenzobettini.jbase, net.sf.xtext-jbase
➕  jbase.lib  net.sf.xtext-jbase -> io.github.lorenzobettini.jbase, net.sf.xtext-jbase
➕  jbase.mwe2  net.sf.xtext-jbase -> io.github.lorenzobettini.jbase, net.sf.xtext-jbase
➕  jcohy.ali.oss  com.jcohy.gradle -> com.jcohy.gradle, io.github.jcohy
➕  jcohy.java.checkstyle  com.jcohy.gradle -> com.jcohy.gradle, io.github.jcohy
➕  jcohy.java.convention  com.jcohy.gradle -> com.jcohy.gradle, io.github.jcohy
➕  jssc  net.jockx -> io.github.java-native, net.jockx
➕  kiwi  com.gitlab.tixtix320 -> com.github.tix320, com.gitlab.tixtix320
➕  kora.annotation.processor.common  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.annotation.processors  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.aop.annotation.processor  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.aop.symbol.processor  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.application.graph  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.cache.annotation.processor  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.cache.caffeine  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.cache.common  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.cache.redis  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.cache.symbol.processor  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.camunda.engine.bpmn  ru.tinkoff.kora.experimental -> io.koraframework.experimental, ru.tinkoff.kora.experimental
➕  kora.camunda.rest.undertow  ru.tinkoff.kora.experimental -> io.koraframework.experimental, ru.tinkoff.kora.experimental
➕  kora.camunda.zeebe.worker  ru.tinkoff.kora.experimental -> io.koraframework.experimental, ru.tinkoff.kora.experimental
➕  kora.camunda.zeebe.worker.annotation.processor  ru.tinkoff.kora.experimental -> io.koraframework.experimental, ru.tinkoff.kora.experimental
➕  kora.camunda.zeebe.worker.symbol.processor  ru.tinkoff.kora.experimental -> io.koraframework.experimental, ru.tinkoff.kora.experimental
➕  kora.common  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.config.annotation.processor  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.config.common  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.config.hocon  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.config.symbol.processor  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.config.yaml  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.database.annotation.processor  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.database.cassandra  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.database.common  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.database.flyway  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.database.jdbc  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.database.liquibase  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.database.symbol.processor  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.declarative.logging.annotation.processor  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.declarative.logging.symbol.processor  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.grpc.client  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.grpc.client.annotation.processor  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.grpc.client.symbol.processor  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.grpc.server  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.http.client.annotation.processor  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.http.client.common  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.http.client.jdk  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.http.client.ok  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.http.client.symbol.processor  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.http.common  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.http.server.annotation.processor  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.http.server.common  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.http.server.symbol.processor  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.http.server.undertow  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.jackson.module  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.jms  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.json.annotation.processor  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.json.common  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.json.symbol.processor  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.kafka  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.kafka.annotation.processor  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.kafka.symbol.processor  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.kora.app.annotation.processor  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.kora.app.symbol.processor  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.logging.common  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.logging.logback  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.mapstruct.java.extension  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.mapstruct.ksp.extension  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.micrometer.module  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.netty.common  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.openapi.generator  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.openapi.management  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.opentelemetry.common  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.opentelemetry.tracing  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.opentelemetry.tracing.exporter.grpc  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.opentelemetry.tracing.exporter.http  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.resilent.kora  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.resilient.annotation.processor  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.resilient.symbol.processor  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.s3.client  ru.tinkoff.kora.experimental -> io.koraframework.experimental, ru.tinkoff.kora.experimental
➕  kora.s3.client.annotation.processor  ru.tinkoff.kora.experimental -> io.koraframework.experimental, ru.tinkoff.kora.experimental
➕  kora.s3.client.symbol.processor  ru.tinkoff.kora.experimental -> io.koraframework.experimental, ru.tinkoff.kora.experimental
➕  kora.scheduling.annotation.processor  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.scheduling.common  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.scheduling.jdk  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.scheduling.ksp  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.scheduling.quartz  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.soap.client  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.soap.client.annotation.processor  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.soap.client.symbol.processor  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.symbol.processor.common  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.symbol.processors  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.telemetry.common  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.test.junit5  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.validation.annotation.processor  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.validation.common  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.validation.module  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kora.validation.symbol.processor  ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora
➕  kotlin.reflect  org.jetbrains.kotlin -> com.airbnb.viaduct, org.jetbrains.kotlin
➕  kotlin.stdlib  org.jetbrains.kotlin -> com.alibaba.ververica, org.jetbrains.kotlin
➕  kotlin.stdlib.jdk8  org.jetbrains.kotlin -> com.airbnb.viaduct, org.jetbrains.kotlin
➕  kotlin.test  org.jetbrains.kotlin -> org.jetbrains.kotlin, xyz.block.kotlin-formatter
➕  library  build.buf.prototype -> build.buf.prototype, com.connectrpc
➕  liqp  nl.big-o -> io.github.luoxuansz, nl.big-o
➕  manifestGenerator  com.github.srujankujmar -> com.github.srujankujmar, io.hyscale
➕  me.linusdev.data  io.github.lni-dev -> de.linusdev, io.github.lni-dev
➕  me.tongfei.progressbar  me.tongfei -> io.github.o2alexanderfedin.javafv, me.tongfei
➕  mslinks  com.github.vatbub -> com.github.vatbub, org.jabref
➕  net.jbock  com.github.h908714124 -> com.github.h908714124, io.github.jbock-java
➕  net.jbock.compiler  com.github.h908714124 -> com.github.h908714124, io.github.jbock-java
➕  net.kyori.ansi  net.kyori -> net.flectone.pulse, net.kyori
➕  net.kyori.examination.api  net.kyori -> io.github.zerog228, net.kyori
➕  net.kyori.examination.string  net.kyori -> eu.koboo, net.kyori
➕  net.postgis.jdbc  net.postgis -> com.enterprisedb, net.postgis
➕  net.sf.jsqlparser  com.github.jsqlparser -> com.github.jsqlparser, com.manticore-projects.jsqlformatter
➕  net.sf.uadetector.core  com.jwebmp.jre11 -> com.guicedee.modules.services, com.jwebmp.jre11
➕  net.sf.uadetector.resources  com.jwebmp.jre11 -> com.guicedee.modules.services, com.jwebmp.jre11
➕  net.sourceforge.argparse4j  net.sourceforge.argparse4j -> net.sourceforge.argparse4j, org.opendaylight.netconf
➕  netty.socketio.core  io.github.neatguycoding -> com.socketio4j, io.github.neatguycoding
➕  netty.socketio.spring  io.github.neatguycoding -> com.socketio4j, io.github.neatguycoding
➕  ogc.tools.gml.jts  io.github.soc -> io.github.soc, org.ogc-schemas
➕  okhttp  build.buf -> build.buf, com.connectrpc
➕  okhttp3  com.github.ljun20160606 -> com.github.ljun20160606, com.squareup.wire
➕  okhttp3.androidsupport  com.github.ljun20160606 -> com.github.ljun20160606, com.squareup.okhttp3
➕  okhttp3.apache  com.github.ljun20160606 -> com.github.ljun20160606, com.squareup.okhttp3
➕  okhttp3.curl  com.github.ljun20160606 -> com.github.ljun20160606, com.squareup.okhttp3
➕  okhttp3.logging  com.github.ljun20160606 -> com.github.ljun20160606, com.ibm.cloud
➕  okhttp3.mockwebserver  com.github.ljun20160606 -> com.github.ljun20160606, com.squareup.okhttp3
➕  okhttp3.testing  com.github.ljun20160606 -> com.github.ljun20160606, com.squareup.okhttp3
➕  okhttp3.urlconnection  com.github.ljun20160606 -> com.github.ljun20160606, com.squareup.okhttp3
➕  org.apache.commons.beanutils  com.guicedee.services -> com.guicedee.services, org.wildfly
➕  org.apache.commons.cli  org.apache.shiro.tools -> org.apache.shiro.tools, org.teavm
➕  org.apache.commons.codec  commons-codec -> com.alibaba.ververica, commons-codec
➕  org.apache.commons.collections4  org.apache.commons -> com.guicedee.modules.services, org.apache.commons
➕  org.apache.commons.compress  org.apache.commons -> com.alibaba.ververica, org.apache.commons
➕  org.apache.commons.configuration2  org.apache.commons -> org.apache.commons, org.neo4j.procedure
➕  org.apache.commons.csv  io.github.pustike -> io.github.pustike, org.sonarsource.scanner.engine
➕  org.apache.commons.fileupload  com.jwebmp.jre11 -> com.guicedee.modules.services, com.jwebmp.jre11
➕  org.apache.commons.fileupload2.jakarta  org.apache.jena -> com.svenruppert, org.apache.jena
➕  org.apache.commons.fileupload2.jakarta.servlet6  org.apache.jena -> io.github.dhruvrawatdev, org.apache.jena
➕  org.apache.commons.imaging  org.apache.commons -> org.apache.commons, org.dromara
➕  org.apache.commons.io  commons-io -> commons-io, no.entur
➕  org.apache.commons.mail  com.github.ppodgorsek.email -> com.github.ppodgorsek.email, io.prophecy
➕  org.apache.commons.net  commons-net -> commons-net, int.esa.ccsds.mo
➕  org.apache.commons.pool2  org.apache.commons -> org.apache.commons, org.openjproxy
➕  org.apache.commons.text  org.apache.commons -> com.telamin.fluxtion, org.apache.commons
➕  org.apache.derby.engine  org.apache.derby -> org.apache.derby, org.bridgedb
➕  org.apache.derby.tools  org.apache.derby -> org.apache.derby, org.bridgedb
➕  org.apache.directory.scim.client  org.apache.directory.scimple -> io.acryl, org.apache.directory.scimple
➕  org.apache.directory.scim.compliance  org.apache.directory.scimple -> io.acryl, org.apache.directory.scimple
➕  org.apache.directory.scim.core  org.apache.directory.scimple -> io.acryl, org.apache.directory.scimple
➕  org.apache.directory.scim.protocol  org.apache.directory.scimple -> io.acryl, org.apache.directory.scimple
➕  org.apache.directory.scim.server  org.apache.directory.scimple -> io.acryl, org.apache.directory.scimple
➕  org.apache.directory.scim.spec  org.apache.directory.scimple -> io.acryl, org.apache.directory.scimple
➕  org.apache.directory.scim.test  org.apache.directory.scimple -> io.acryl, org.apache.directory.scimple
➕  org.apache.directory.scim.tools  org.apache.directory.scimple -> io.acryl, org.apache.directory.scimple
➕  org.apache.felix.framework  org.apache.felix -> com.yahoo.vespa, org.apache.felix
➕  org.apache.logging.log4j.core  org.apache.logging.log4j -> app.freerouting, org.apache.logging.log4j
➕  org.apache.lucene.suggest  org.apache.lucene -> org.apache.lucene, org.opensearch.migrations.snapshots
➕  org.apache.poi.poi  org.apache.poi -> com.guicedee.modules.services, org.apache.poi
➕  org.apache.xmlbeans  com.guicedee.services -> com.guicedee.services, io.github.cdnk
➕  org.apiguardian.api  org.apiguardian -> org.apache.tinkerpop, org.apiguardian
➕  org.beryx.awt.color  org.beryx -> org.apache.sedona, org.beryx
➕  org.bouncycastle.lts.prov  org.bouncycastle -> org.bouncycastle, org.brylex
➕  org.bukkit  com.uroria.curepur -> com.mineplex.studio.server, com.uroria.curepur
➕  org.bytedeco.bullet  org.bytedeco -> org.bytedeco, us.ihmc
➕  org.bytedeco.bullet.linux.x86_64  org.bytedeco -> org.bytedeco, us.ihmc
➕  org.bytedeco.bullet.windows.x86_64  org.bytedeco -> org.bytedeco, us.ihmc
➕  org.bytedeco.librealsense2  org.bytedeco -> org.bytedeco, us.ihmc
➕  org.bytedeco.librealsense2.linux.arm64  org.bytedeco -> org.bytedeco, us.ihmc
➕  org.bytedeco.librealsense2.linux.x86_64  org.bytedeco -> org.bytedeco, us.ihmc
➕  org.bytedeco.librealsense2.windows.x86_64  org.bytedeco -> org.bytedeco, us.ihmc
➕  org.codehaus.plexus.interpolation  org.codehaus.plexus -> me.chrissw-r1, org.codehaus.plexus
➕  org.commonmark  com.atlassian.commonmark -> com.atlassian.commonmark, com.qainsights
➕  org.commonmark.ext.autolink  com.atlassian.commonmark -> com.atlassian.commonmark, org.commonmark
➕  org.commonmark.ext.gfm.strikethrough  com.atlassian.commonmark -> com.atlassian.commonmark, org.commonmark
➕  org.commonmark.ext.gfm.tables  com.atlassian.commonmark -> com.atlassian.commonmark, io.github.dfengwei
➕  org.commonmark.ext.heading.anchor  com.atlassian.commonmark -> com.atlassian.commonmark, org.commonmark
➕  org.commonmark.ext.image.attributes  com.atlassian.commonmark -> com.atlassian.commonmark, org.commonmark
➕  org.commonmark.ext.ins  com.atlassian.commonmark -> com.atlassian.commonmark, org.commonmark
➕  org.commonmark.ext.task.list.items  com.atlassian.commonmark -> com.atlassian.commonmark, org.commonmark
➕  org.commonmark.testutil  com.atlassian.commonmark -> com.atlassian.commonmark, org.commonmark
➕  org.eclipse.emf.common  com.innoventsolutions.birt.runtime -> com.innoventsolutions.birt.runtime, org.eclipse.emf
➕  org.eclipse.emf.ecore  com.innoventsolutions.birt.runtime -> com.innoventsolutions.birt.runtime, org.eclipse.emf
➕  org.eclipse.emf.ecore.xmi  com.innoventsolutions.birt.runtime -> com.innoventsolutions.birt.runtime, org.eclipse.emf
➕  org.eclipse.jetty.alpn.conscrypt.server  org.eclipse.jetty -> com.adobe.campaign.tests.bridge.service, org.eclipse.jetty
➕  org.eclipse.oomph.console  com.github.a-langer -> com.github.a-langer, io.klib.tools
➕  org.eclipse.oomph.console.core  com.github.a-langer -> com.github.a-langer, io.klib.tools
➕  org.eclipse.oomph.console.test  com.github.a-langer -> com.github.a-langer, io.klib.tools
➕  org.eclipse.osgi  org.eclipse.tycho -> io.joynr.tools.generator, org.eclipse.tycho
➕  org.eclipse.swt.win32.win32.x86_64  org.eclipse.platform -> io.github.sundenjaeger, org.eclipse.platform
➕  org.freedesktop.dbus  com.github.hypfvieh -> com.github.hypfvieh, org.endlesssource.mediainterface
➕  org.freedesktop.gstreamer  org.freedesktop.gstreamer -> org.freedesktop.gstreamer, org.java-gi
➕  org.freedesktop.harfbuzz  io.github.jwharm.javagi -> io.github.jwharm.javagi, org.java-gi
➕  org.glassfish.jakarta.json  org.glassfish -> org.glassfish, org.nanopub
➕  org.glassfish.java.json  org.glassfish -> com.mobius-software.plugins, org.glassfish
➕  org.gnome.adw  io.github.jwharm.javagi -> io.github.jwharm.javagi, org.java-gi
➕  org.gnome.gdkpixbuf  io.github.jwharm.javagi -> io.github.jwharm.javagi, org.java-gi
➕  org.gnome.glib  io.github.jwharm.javagi -> io.github.jwharm.javagi, org.java-gi
➕  org.gnome.gtk  io.github.jwharm.javagi -> io.github.jwharm.javagi, org.java-gi
➕  org.gnome.gtksourceview  io.github.jwharm.javagi -> io.github.jwharm.javagi, org.java-gi
➕  org.gnome.pango  io.github.jwharm.javagi -> io.github.jwharm.javagi, org.java-gi
➕  org.gnome.soup  io.github.jwharm.javagi -> io.github.jwharm.javagi, org.java-gi
➕  org.hibernate.validator  org.hibernate.validator -> com.guicedee.modules.services, org.hibernate.validator
➕  org.hsqldb  org.hsqldb -> com.github.massamany, org.hsqldb
➕  org.incode.platform.lib.servletapi  org.incode.module.slack -> org.incode.module.slack, org.isisaddons.module.servletapi
➕  org.jboss.jandex  org.jboss -> io.smallrye, org.jboss
➕  org.jboss.jandex.typeannotationtest  org.jboss.jandex -> io.smallrye, org.jboss.jandex
➕  org.jgrapht.core  org.jgrapht -> org.choco-solver, org.jgrapht
➕  org.jheaps  org.jheaps -> dev.ikm.jpms, org.jheaps
➕  org.jnetpcap  de.gematik -> com.slytechs.jnet.jnetpcap, de.gematik
➕  org.json  org.json -> com.github.karsaig, org.json
➕  org.jsoup  org.jsoup -> org.finos.legend.sdlc, org.jsoup
➕  org.jspecify  org.jspecify -> io.cryostat, org.jspecify
➕  org.leadpony.justify  org.leadpony.justify -> com.ibm.ta.sdk, org.leadpony.justify
➕  org.lz4.java  org.lz4 -> at.yawk.lz4, org.lz4
➕  org.mapstruct  org.mapstruct -> com.guicedee.modules.services, org.mapstruct
➕  org.mnode.ical4j.serializer  org.mnode.ical4j -> org.ical4j, org.mnode.ical4j
➕  org.neo4j.collection.primitive  org.neo4j -> org.graphfoundation.ongdb, org.neo4j
➕  org.neo4j.cypher.internal.compatibility.v3_4.runtime  org.neo4j -> org.graphfoundation.ongdb, org.neo4j
➕  org.neo4j.cypher.internal.compatibility.v3_4.runtime.slotted  org.neo4j -> org.graphfoundation.ongdb, org.neo4j
➕  org.neo4j.cypher.internal.compiler.v3_4  org.neo4j -> org.graphfoundation.ongdb, org.neo4j
➕  org.neo4j.cypher.internal.frontend.v3_4  org.neo4j -> org.graphfoundation.ongdb, org.neo4j
➕  org.neo4j.cypher.internal.ir.v3_4  org.neo4j -> org.graphfoundation.ongdb, org.neo4j
➕  org.neo4j.cypher.internal.planner.v3_4.spi  org.neo4j -> org.graphfoundation.ongdb, org.neo4j
➕  org.neo4j.cypher.internal.runtime.compiled.expressions  org.neo4j -> org.graphfoundation.ongdb, org.neo4j
➕  org.neo4j.cypher.internal.util.v3_4  org.neo4j -> org.graphfoundation.ongdb, org.neo4j
➕  org.neo4j.cypher.internal.v3_4.expressions  org.neo4j -> org.graphfoundation.ongdb, org.neo4j
➕  org.neo4j.cypher.internal.v3_4.logical.plans  org.neo4j -> org.graphfoundation.ongdb, org.neo4j
➕  org.neo4j.graphdb.resourse  org.neo4j -> org.graphfoundation.ongdb, org.neo4j
➕  org.neo4j.internal.cypher.acceptance.spec.suite  org.neo4j -> org.graphfoundation.ongdb, org.neo4j
➕  org.neo4j.internal.cypher.compatibility.spec.suite  org.neo4j -> org.graphfoundation.ongdb, org.neo4j
➕  org.neo4j.internal.cypher.spec.suite.tools  org.neo4j -> org.graphfoundation.ongdb, org.neo4j
➕  org.neo4j.internal.enterprise.cypher.acceptance.spec.suite  org.neo4j -> org.graphfoundation.ongdb, org.neo4j
➕  org.neo4j.internal.enterprise.cypher.compatibility.spec.suite  org.neo4j -> org.graphfoundation.ongdb, org.neo4j
➕  org.neo4j.stresstests  org.neo4j -> org.graphfoundation.ongdb, org.neo4j
➕  org.newsclub.net.unix  com.kohlschutter.junixsocket -> com.kohlschutter.junixsocket, com.sbbsystems.flink
➕  org.objectweb.asm.tree  org.ow2.asm -> io.killedkenny.crossfuzz, org.ow2.asm
➕  org.openapitools.jackson.nullable  org.openapitools -> io.airlift, org.openapitools
➕  org.opengis.geoapi  org.opengis -> org.apache.tika, org.opengis
➕  org.openqa.selenium.remote  org.seleniumhq.selenium -> dev.aherscu.qa, org.seleniumhq.selenium
➕  org.opensaml.security  org.opensaml -> org.elasticsearch.plugin, org.opensaml
➕  org.opentest4j  org.opentest4j -> berlin.yuna, org.opentest4j
➕  org.pcap4j.core  org.pcap4j -> io.vproxy, org.pcap4j
➕  org.pcap4j.packetfactory.propertiesbased  org.pcap4j -> io.vproxy, org.pcap4j
➕  org.pcap4j.packetfactory.statik  org.pcap4j -> io.vproxy, org.pcap4j
➕  org.pcollections  net.pincette -> net.pincette, org.pcollections
➕  org.primefaces.extensions  com.guicedee.services -> com.guicedee.services, org.primefaces.extensions
➕  org.reactivestreams  org.reactivestreams -> dev.ikm.jpms, org.reactivestreams
➕  org.roaringbitmap  com.clickhouse -> com.clickhouse, dev.ikm.jpms
➕  org.signal.libsignal  org.signal -> io.github.wanggenlin, org.signal
➕  org.teavm.metaprogramming.api  org.teavm -> com.fermyon, org.teavm
➕  org.testfx.monocle  org.testfx -> one.jpro.platform.jpms, org.testfx
➕  org.tinylog.api  org.tinylog -> de.bsommerfeld.pathetic, org.tinylog
➕  org.wildfly.common  org.wildfly.common -> io.quarkus, org.wildfly.common
➕  org.zeromq.jeromq  org.zeromq -> org.jetbrains.kotlinx, org.zeromq
➕  play.ws.standalone  com.typesafe.play -> com.typesafe.play, org.playframework
➕  play.ws.standalone.ahc  com.typesafe.play -> com.typesafe.play, org.playframework
➕  play.ws.standalone.json  com.typesafe.play -> com.typesafe.play, org.playframework
➕  play.ws.standalone.xml  com.typesafe.play -> com.typesafe.play, org.playframework
➕  pluginframework  com.github.srujankujmar -> com.github.srujankujmar, io.hyscale
➕  r2dbc.postgresql  io.r2dbc -> com.yugabyte, io.r2dbc
➕  retrofit2  com.squareup.retrofit2 -> com.squareup.retrofit2, io.github.mindcomic.retrofit2
➕  retrofit2.adapter.guava  com.squareup.retrofit2 -> com.squareup.retrofit2, io.github.mindcomic.retrofit2
➕  retrofit2.adapter.java8  com.squareup.retrofit2 -> com.squareup.retrofit2, io.github.mindcomic.retrofit2
➕  retrofit2.adapter.rxjava  com.squareup.retrofit2 -> com.squareup.retrofit2, io.github.mindcomic.retrofit2
➕  retrofit2.adapter.rxjava2  com.squareup.retrofit2 -> com.squareup.retrofit2, io.github.mindcomic.retrofit2
➕  retrofit2.adapter.rxjava3  com.squareup.retrofit2 -> com.squareup.retrofit2, io.github.mindcomic.retrofit2
➕  retrofit2.adapter.scala  com.squareup.retrofit2 -> com.squareup.retrofit2, io.github.mindcomic.retrofit2
➕  retrofit2.converter.gson  com.squareup.retrofit2 -> com.squareup.retrofit2, io.github.mindcomic.retrofit2
➕  retrofit2.converter.guava  com.squareup.retrofit2 -> com.squareup.retrofit2, io.github.mindcomic.retrofit2
➕  retrofit2.converter.jackson  com.squareup.retrofit2 -> com.squareup.retrofit2, io.github.mindcomic.retrofit2
➕  retrofit2.converter.java8  com.squareup.retrofit2 -> com.squareup.retrofit2, io.github.mindcomic.retrofit2
➕  retrofit2.converter.jaxb  com.squareup.retrofit2 -> com.squareup.retrofit2, io.github.mindcomic.retrofit2
➕  retrofit2.converter.moshi  com.squareup.retrofit2 -> com.squareup.retrofit2, io.github.mindcomic.retrofit2
➕  retrofit2.converter.protobuf  com.squareup.retrofit2 -> com.squareup.retrofit2, io.github.mindcomic.retrofit2
➕  retrofit2.converter.scalars  com.squareup.retrofit2 -> com.squareup.retrofit2, io.github.mindcomic.retrofit2
➕  retrofit2.converter.simplexml  com.squareup.retrofit2 -> com.squareup.retrofit2, io.github.mindcomic.retrofit2
➕  retrofit2.converter.wire  com.squareup.retrofit2 -> com.squareup.retrofit2, io.github.mindcomic.retrofit2
➕  retrofit2.mock  com.squareup.retrofit2 -> com.squareup.retrofit2, io.github.mindcomic.retrofit2
➕  schema.validator  com.github.srujankujmar -> com.github.srujankujmar, io.hyscale
➕  service_spec_commons  com.github.srujankujmar -> com.github.srujankujmar, io.hyscale
➕  sonder  com.gitlab.tixtix320 -> com.github.tix320, com.gitlab.tixtix320
➕  stasgora.observetree  io.github.stasgora -> dev.sgora, io.github.stasgora
➕  swim.api  ai.swim -> ai.swim, org.swimos
➕  swim.args  ai.swim -> ai.swim, org.swimos
➕  swim.auth  ai.swim -> ai.swim, org.swimos
➕  swim.client  ai.swim -> ai.swim, org.swimos
➕  swim.codec  ai.swim -> ai.swim, org.swimos
➕  swim.collections  ai.swim -> ai.swim, org.swimos
➕  swim.concurrent  ai.swim -> ai.swim, org.swimos
➕  swim.dataflow  ai.swim -> ai.swim, org.swimos
➕  swim.decipher  ai.swim -> ai.swim, org.swimos
➕  swim.deflate  ai.swim -> ai.swim, org.swimos
➕  swim.dynamic  ai.swim -> ai.swim, org.swimos
➕  swim.dynamic.api  ai.swim -> ai.swim, org.swimos
➕  swim.dynamic.java  ai.swim -> ai.swim, org.swimos
➕  swim.dynamic.observable  ai.swim -> ai.swim, org.swimos
➕  swim.dynamic.structure  ai.swim -> ai.swim, org.swimos
➕  swim.http  ai.swim -> ai.swim, org.swimos
➕  swim.io  ai.swim -> ai.swim, org.swimos
➕  swim.io.http  ai.swim -> ai.swim, org.swimos
➕  swim.io.mqtt  ai.swim -> ai.swim, org.swimos
➕  swim.io.warp  ai.swim -> ai.swim, org.swimos
➕  swim.io.ws  ai.swim -> ai.swim, org.swimos
➕  swim.java  ai.swim -> ai.swim, org.swimos
➕  swim.js  ai.swim -> ai.swim, org.swimos
➕  swim.json  ai.swim -> ai.swim, org.swimos
➕  swim.kernel  ai.swim -> ai.swim, org.swimos
➕  swim.linker  ai.swim -> ai.swim, org.swimos
➕  swim.loader  ai.swim -> ai.swim, org.swimos
➕  swim.math  ai.swim -> ai.swim, org.swimos
➕  swim.mqtt  ai.swim -> ai.swim, org.swimos
➕  swim.observable  ai.swim -> ai.swim, org.swimos
➕  swim.protobuf  ai.swim -> ai.swim, org.swimos
➕  swim.recon  ai.swim -> ai.swim, org.swimos
➕  swim.remote  ai.swim -> ai.swim, org.swimos
➕  swim.runtime  ai.swim -> ai.swim, org.swimos
➕  swim.security  ai.swim -> ai.swim, org.swimos
➕  swim.server  ai.swim -> ai.swim, org.swimos
➕  swim.service  ai.swim -> ai.swim, org.swimos
➕  swim.service.web  ai.swim -> ai.swim, org.swimos
➕  swim.spatial  ai.swim -> ai.swim, org.swimos
➕  swim.store  ai.swim -> ai.swim, org.swimos
➕  swim.store.mem  ai.swim -> ai.swim, org.swimos
➕  swim.streamlet  ai.swim -> ai.swim, org.swimos
➕  swim.structure  ai.swim -> ai.swim, org.swimos
➕  swim.uri  ai.swim -> ai.swim, org.swimos
➕  swim.util  ai.swim -> ai.swim, org.swimos
➕  swim.vm  ai.swim -> ai.swim, org.swimos
➕  swim.vm.js  ai.swim -> ai.swim, org.swimos
➕  swim.warp  ai.swim -> ai.swim, org.swimos
➕  swim.web  ai.swim -> ai.swim, org.swimos
➕  swim.ws  ai.swim -> ai.swim, org.swimos
➕  swim.xml  ai.swim -> ai.swim, org.swimos
➕  tech.uom.lib.common  tech.uom.lib -> io.pcp.agentparfait, tech.uom.lib
➕  tm.bitronix.btm  com.jwebmp.jre11 -> com.guicedee.services, com.jwebmp.jre11
➕  tornadofx  it.unibo.alchemist -> com.googlecode.blaisemath.tornado, it.unibo.alchemist
➕  troubleshooting.integration  com.github.srujankujmar -> com.github.srujankujmar, io.hyscale
➕  tucache.core  co.tunan.tucache -> co.tunan.tucache, io.github.tri5m
➕  tucache.spring.boot.autoconfigure  co.tunan.tucache -> co.tunan.tucache, io.github.tri5m
➕  tucache.spring.boot.starter  co.tunan.tucache -> co.tunan.tucache, io.github.tri5m
➕  tuweni.app_commons  org.apache.tuweni -> io.tmio, org.apache.tuweni
➕  tuweni.bytes  org.apache.tuweni -> io.consensys.tuweni, org.apache.tuweni
➕  tuweni.concurrent  org.apache.tuweni -> io.consensys.tuweni, org.apache.tuweni
➕  tuweni.concurrent_coroutines  org.apache.tuweni -> io.consensys.tuweni, org.apache.tuweni
➕  tuweni.config  org.apache.tuweni -> io.consensys.tuweni, org.apache.tuweni
➕  tuweni.crypto  org.apache.tuweni -> io.consensys.tuweni, org.apache.tuweni
➕  tuweni.devp2p  org.apache.tuweni -> io.consensys.tuweni, org.apache.tuweni
➕  tuweni.devp2p_eth  org.apache.tuweni -> io.tmio, org.apache.tuweni
➕  tuweni.devp2p_proxy  org.apache.tuweni -> io.tmio, org.apache.tuweni
➕  tuweni.dns_discovery  org.apache.tuweni -> io.tmio, org.apache.tuweni
➕  tuweni.eth  org.apache.tuweni -> io.tmio, org.apache.tuweni
➕  tuweni.eth_blockprocessor  org.apache.tuweni -> io.tmio, org.apache.tuweni
➕  tuweni.eth_client  org.apache.tuweni -> io.tmio, org.apache.tuweni
➕  tuweni.eth_client_app  org.apache.tuweni -> io.tmio, org.apache.tuweni
➕  tuweni.eth_client_ui  org.apache.tuweni -> io.tmio, org.apache.tuweni
➕  tuweni.eth_crawler  org.apache.tuweni -> io.tmio, org.apache.tuweni
➕  tuweni.eth_faucet  org.apache.tuweni -> io.tmio, org.apache.tuweni
➕  tuweni.eth_precompiles  org.apache.tuweni -> io.tmio, org.apache.tuweni
➕  tuweni.eth_repository  org.apache.tuweni -> io.tmio, org.apache.tuweni
➕  tuweni.ethstats  org.apache.tuweni -> io.tmio, org.apache.tuweni
➕  tuweni.evm  org.apache.tuweni -> io.tmio, org.apache.tuweni
➕  tuweni.evm_dsl  org.apache.tuweni -> io.tmio, org.apache.tuweni
➕  tuweni.genesis  org.apache.tuweni -> io.tmio, org.apache.tuweni
➕  tuweni.gossip  org.apache.tuweni -> io.tmio, org.apache.tuweni
➕  tuweni.hobbits  org.apache.tuweni -> io.tmio, org.apache.tuweni
➕  tuweni.hobbits_relayer  org.apache.tuweni -> io.tmio, org.apache.tuweni
➕  tuweni.io  org.apache.tuweni -> io.consensys.tuweni, org.apache.tuweni
➕  tuweni.jsonrpc  org.apache.tuweni -> io.tmio, org.apache.tuweni
➕  tuweni.jsonrpc_app  org.apache.tuweni -> io.tmio, org.apache.tuweni
➕  tuweni.junit  org.apache.tuweni -> io.consensys.tuweni, org.apache.tuweni
➕  tuweni.kademlia  org.apache.tuweni -> io.consensys.tuweni, org.apache.tuweni
➕  tuweni.kv  org.apache.tuweni -> io.tmio, org.apache.tuweni
➕  tuweni.les  org.apache.tuweni -> io.tmio, org.apache.tuweni
➕  tuweni.merkle_trie  org.apache.tuweni -> io.tmio, org.apache.tuweni
➕  tuweni.metrics  org.apache.tuweni -> io.tmio, org.apache.tuweni
➕  tuweni.net  org.apache.tuweni -> io.consensys.tuweni, org.apache.tuweni
➕  tuweni.peer_repository  org.apache.tuweni -> io.tmio, org.apache.tuweni
➕  tuweni.plumtree  org.apache.tuweni -> io.tmio, org.apache.tuweni
➕  tuweni.pow  org.apache.tuweni -> io.tmio, org.apache.tuweni
➕  tuweni.rlp  org.apache.tuweni -> io.consensys.tuweni, org.apache.tuweni
➕  tuweni.rlpx  org.apache.tuweni -> io.tmio, org.apache.tuweni
➕  tuweni.scuttlebutt  org.apache.tuweni -> io.tmio, org.apache.tuweni
➕  tuweni.scuttlebutt_client_lib  org.apache.tuweni -> io.tmio, org.apache.tuweni
➕  tuweni.scuttlebutt_discovery  org.apache.tuweni -> io.tmio, org.apache.tuweni
➕  tuweni.scuttlebutt_handshake  org.apache.tuweni -> io.tmio, org.apache.tuweni
➕  tuweni.scuttlebutt_rpc  org.apache.tuweni -> io.tmio, org.apache.tuweni
➕  tuweni.ssz  org.apache.tuweni -> io.consensys.tuweni, org.apache.tuweni
➕  tuweni.toml  org.apache.tuweni -> io.consensys.tuweni, org.apache.tuweni
➕  tuweni.units  org.apache.tuweni -> io.consensys.tuweni, org.apache.tuweni
➕  tuweni.wallet  org.apache.tuweni -> io.tmio, org.apache.tuweni
➕  vault.java.driver  com.bettercloud -> com.bettercloud, io.github.jopenlibs
```

