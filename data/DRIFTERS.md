# Module ownership drifters

Generated 2026-06-12. A module *drifts* when more than one groupId publishes the name and its `owners.tsv` does not yet name every publisher (no `owners.tsv`, or one that leaves some publishing groupId neither `allowed` nor `blocked`). Resolving a drift means deciding each groupId via `SetOwners` (which writes `allowed`/`blocked`); a fully-named module drops off this list.

| Category | Unresolved | Resolved via owners.tsv |
|---|---:|---:|
| explicit-rules | 0 | 369 |
| republisher | 0 | 146 |
| migration | 0 | 1278 |
| fork | 0 | 448 |
| shaded | 0 | 1272 |
| tld-dropped | 0 | 15 |
| two-segments | 3 | 0 |
| unclassified | 41 | 0 |
| **total** | **44** | **3528** |

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

## two-segments (3)

The dominant owner's groupId with its first two segments dropped is the module-name prefix. Proposal: allow that owner, block the rest.

| count | current owner -> new owner(s) |
|---:|---|
| 1 | `com.telenav.cactus -> com.telenav.cactus,com.telenav.lexakai` |
| 1 | `org.jetbrains.kotlin -> org.jetbrains.kotlin,org.jetbrains.lets-plot` |

```
jackson.datatype.pcollections  [owned by `com.fasterxml.jackson.datatype` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * com.fasterxml.jackson.datatype       2019-07..2026-06 2.22.0       |.....===============|
  ?   tools.jackson.datatype               2025-03..2026-05 3.1.3        |................====|
kotlin.test.junit  [owned by `org.jetbrains.kotlin` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.jetbrains.kotlin                 2020-07..2023-08 1.9.10       |.......=======......|
  ?   org.archguard.scanner                2022-06..2022-12 2.0.0-beta.5 |...........==.......|
  ?   org.jetbrains.lets-plot              2021-04..2021-06 2.0.4        |.........=..........|
cactus.maven.xml  [owned by `com.telenav.cactus` (groupId minus two segments is the module prefix); 0 other group(s) shade the name]
  ? * com.telenav.cactus                   2022-06..2022-11 1.5.49       |...........==.......|
  ?   com.telenav.lexakai                  2022-09..2022-10 1.0.13       |...........==.......|
```

## unclassified (41)

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

Modules whose resolved owner differs from the implicit first-publisher owner once `owners.tsv` is applied. 🔀 reassigned (158): the first publisher was replaced by a different owner. ➕ widened (597): extra legal owners were allowed alongside the first publisher (e.g. a groupId migration or a co-maintained project). Modules where `owners.tsv` only confirms the first publisher are not listed. Submodules that share the same transition are collapsed into a single `prefix.*` row; the Modules column reports how many modules that row covers.

| Kind | Module | Modules | Current owner -> new owner(s) |
|---|---|---:|---|
| 🔀 | `com.sun.xml.bind` | 1 | `org.glassfish.jaxb -> com.sun.xml.bind` |
| 🔀 | `io.whitfin.siphash` | 1 | `com.io7m.repackage.io.whitfin -> io.whitfin` |
| 🔀 | `jakarta.activation` | 1 | `com.sun.activation -> jakarta.activation` |
| 🔀 | `jakarta.concurrency` | 1 | `jakarta.enterprise.concurrent -> jakarta.concurrency` |
| 🔀 | `jakarta.mail` | 1 | `com.sun.mail -> jakarta.mail` |
| 🔀 | `jakarta.servlet.jsp` | 1 | `org.apache.tomcat -> jakarta.servlet.jsp` |
| 🔀 | `net.evonit.thumbnailator2` | 1 | `io.github.evonit -> net.evonit` |
| 🔀 | `okhttp3.*` | 8 | `com.github.ljun20160606 -> com.squareup.okhttp3` |
| 🔀 | `org.commonmark.ext.front.matter` | 1 | `com.atlassian.commonmark -> org.commonmark` |
| 🔀 | `org.eclipse.birt.*` | 137 | `io.github.reporting-solutions -> org.eclipse.birt` |
| 🔀 | `org.eclipse.emf.ecore.change` | 1 | `com.innoventsolutions.birt.runtime -> org.eclipse.emf` |
| 🔀 | `spring.security.*` | 2 | `io.spring.gradle -> org.springframework` |
| 🔀 | `spring.security.project.plugin` | 1 | `io.spring.security.gradle -> org.springframework` |
| 🔀 | `zipkin2.reporter.kafka` | 1 | `org.apache.zipkin.reporter2 -> io.zipkin.reporter2` |
| ➕ | `Mal4J` | 1 | `com.kttdevelopment -> com.kttdevelopment, dev.katsute` |
| ➕ | `aerogel.*` | 2 | `io.github.derklaro -> dev.derklaro.aerogel, io.github.derklaro` |
| ➕ | `aopalliance` | 1 | `com.jwebmp.jre11 -> com.guicedee.modules.services, com.jwebmp.jre11` |
| ➕ | `be.webtechie.piheaders` | 1 | `be.webtechie -> be.webtechie, com.pi4j` |
| ➕ | `builderCore` | 1 | `com.github.srujankujmar -> com.github.srujankujmar, io.hyscale` |
| ➕ | `builderservices` | 1 | `com.github.srujankujmar -> com.github.srujankujmar, io.hyscale` |
| ➕ | `cache.*` | 3 | `com.jwebmp.thirdparty.jcache -> com.guicedee.modules.services, com.jwebmp.thirdparty.jcache` |
| ➕ | `ch.randelshofer.fastdoubleparser` | 1 | `ch.randelshofer -> ch.randelshofer, software.amazon.smithy.java` |
| ➕ | `co.paralleluniverse.quasar.core` | 1 | `co.paralleluniverse -> co.paralleluniverse, com.nhn.gameanvil` |
| ➕ | `com.azure.json` | 1 | `com.azure -> com.azure, com.microsoft.azure.kusto` |
| ➕ | `com.carrotsearch.hppc` | 1 | `com.carrotsearch -> com.carrotsearch, org.teavm` |
| ➕ | `com.computinglaboratory.resourcewatcher` | 1 | `com.computinglaboratory -> com.computinglaboratory, io.github.kamilszewc` |
| ➕ | `com.datastax.oss.protocol` | 1 | `com.datastax.oss -> com.datastax.oss, com.scylladb` |
| ➕ | `com.dlsc.formsfx` | 1 | `com.dlsc.formsfx -> com.dlsc.formsfx, com.github.stefanofornari` |
| ➕ | `com.formdev.flatlaf` | 1 | `com.formdev -> com.formdev, dev.robocode.tankroyale` |
| ➕ | `com.github.benmanes.caffeine` | 1 | `com.github.ben-manes.caffeine -> com.github.ben-manes.caffeine, io.pebbletemplates` |
| ➕ | `com.github.benmanes.caffeine.guava` | 1 | `com.github.ben-manes.caffeine -> com.github.ben-manes.caffeine, io.github.ck-jesse` |
| ➕ | `com.github.jinahya.*` | 2 | `com.github.jinahya -> com.github.jinahya, io.github.jinahya` |
| ➕ | `com.github.kokorin.jaffree` | 1 | `com.github.kokorin.jaffree -> com.github.kokorin.jaffree, io.v47.jaffree` |
| ➕ | `com.github.maeda6uiui.mechtatel.*` | 5 | `com.github.dabasan -> com.github.dabasan, io.github.maeda6uiui` |
| ➕ | `com.google.auto.service` | 1 | `com.github.sidneibjunior -> com.github.sidneibjunior, dev.ikm.jpms` |
| ➕ | `com.google.common.util.concurrent.internal` | 1 | `com.google.guava -> com.google.guava, de.bsommerfeld.pathetic` |
| ➕ | `com.google.guice` | 1 | `com.google.inject -> com.google.inject, com.guicedee.modules.services` |
| ➕ | `com.google.guice.extensions.*` | 4 | `com.google.inject.extensions -> com.google.inject.extensions, com.guicedee.modules.services` |
| ➕ | `com.googlecode.javaewah` | 1 | `com.googlecode.javaewah -> com.googlecode.javaewah, org.meyvn` |
| ➕ | `com.headius.invokebinder` | 1 | `com.headius -> com.headius, org.sahli.asciidoc.confluence.publisher` |
| ➕ | `com.install4j.runtime` | 1 | `com.iamsoft -> com.iamsoft, com.install4j` |
| ➕ | `com.jcraft.jsch` | 1 | `com.github.mwiede -> com.github.mwiede, com.pesitwizard.connector` |
| ➕ | `com.jfoenix` | 1 | `com.jfoenix -> com.jfoenix, org.rationalityfrontline.workaround` |
| ➕ | `com.jwebmp.guicedpersistence.wildfly` | 1 | `com.jwebmp.jre10 -> com.guicedee.persistence, com.jwebmp.jre10` |
| ➕ | `com.kohlschutter.junixsocket.core` | 1 | `com.kohlschutter.junixsocket -> com.kohlschutter.junixsocket, com.sbbsystems.flink` |
| ➕ | `com.lmax.disruptor` | 1 | `com.lmax -> com.lmax, software.xdev.mockserver` |
| ➕ | `com.paypal.sdk` | 1 | `com.paypal.sdk -> com.paypal.sdk, io.sdks` |
| ➕ | `com.querydsl.*` | 17 | `com.querydsl -> com.querydsl, io.github.openfeign.querydsl` |
| ➕ | `com.spotify.futures` | 1 | `com.spotify -> com.spotify, org.hyperledger.fabric-sdk-java` |
| ➕ | `com.sshtools.*` | 2 | `com.sshtools -> com.sshtools, solutions.a2.oracle` |
| ➕ | `com.sun.istack.runtime` | 1 | `com.sun.istack -> com.intuit.quickbooks-online, com.sun.istack` |
| ➕ | `com.sun.xml.fastinfoset` | 1 | `com.sun.xml.fastinfoset -> com.sun.xml.fastinfoset, net.corda` |
| ➕ | `commons` | 1 | `com.github.srujankujmar -> com.github.srujankujmar, io.github.rassafel` |
| ➕ | `controller.service` | 1 | `com.github.srujankujmar -> com.github.srujankujmar, io.hyscale` |
| ➕ | `core` | 1 | `pro.shuangxi.framework.openfx -> org.apereo.cas, pro.shuangxi.framework.openfx` |
| ➕ | `dawdler.*` | 68 | `io.github.dawdler-series -> club.dawdler, io.github.dawdler-series` |
| ➕ | `deeplearning4j.core` | 1 | `org.deeplearning4j -> es.upm.etsisi, org.deeplearning4j` |
| ➕ | `deployer.services` | 1 | `com.github.srujankujmar -> com.github.srujankujmar, io.hyscale` |
| ➕ | `deployerModel` | 1 | `com.github.srujankujmar -> com.github.srujankujmar, io.hyscale` |
| ➕ | `dev.failsafe.core` | 1 | `dev.failsafe -> dev.failsafe, org.apache.iceberg` |
| ➕ | `dockerfilegencore` | 1 | `com.github.srujankujmar -> com.github.srujankujmar, io.hyscale` |
| ➕ | `dockerfilegenservices` | 1 | `com.github.srujankujmar -> com.github.srujankujmar, io.hyscale` |
| ➕ | `eu.toop.connector.mem.def` | 1 | `eu.toop -> com.helger.dcng, eu.toop` |
| ➕ | `glide.api` | 1 | `software.amazon.glide -> io.valkey, software.amazon.glide` |
| ➕ | `graphql.java.tools` | 1 | `com.graphql-java-kickstart -> com.graphql-java-kickstart, io.github.graphql-java-kickstart` |
| ➕ | `hla.rti1516e` | 1 | `io.github.tno-mst -> io.github.tno-mst, nl.tno` |
| ➕ | `info.movito.themoviedbapi` | 1 | `com.github.holgerbrandl -> com.github.holgerbrandl, uk.co.conoregan` |
| ➕ | `info.picocli` | 1 | `info.picocli -> info.picocli, io.spicelabs` |
| ➕ | `io.avaje.junit` | 1 | `org.avaje -> io.avaje, org.avaje` |
| ➕ | `io.ebean.*` | 3 | `org.avaje -> io.ebean, org.avaje` |
| ➕ | `io.github.bucket4j.*` | 11 | `com.github.vladimir-bukhtoyarov -> com.bucket4j, com.github.vladimir-bukhtoyarov` |
| ➕ | `io.github.classgraph` | 1 | `io.github.classgraph -> io.github.classgraph, org.finos.legend.engine` |
| ➕ | `io.grpc` | 1 | `io.helidon.grpc -> io.grpc, io.helidon.grpc` |
| ➕ | `io.smallrye.common.constraint` | 1 | `io.smallrye.common -> io.github.danielgp-eu, io.smallrye.common` |
| ➕ | `io.swagger.v3.*` | 7 | `io.swagger.core.v3 -> io.github.vpelikh, io.swagger.core.v3` |
| ➕ | `it.auties.whatsapp4j` | 1 | `io.github.angleto -> com.github.auties00, io.github.angleto` |
| ➕ | `it.unimi.dsi.fastutil` | 1 | `it.unimi.dsi -> io.github.lionblazer, it.unimi.dsi` |
| ➕ | `jakarta.annotation` | 1 | `jakarta.annotation -> io.quarkus, jakarta.annotation` |
| ➕ | `jakarta.cdi.*` | 3 | `jakarta.enterprise -> jakarta.cdi, jakarta.enterprise` |
| ➕ | `jakarta.ejb` | 1 | `com.guicedee.services -> com.guicedee.services, com.manorrock.flounder` |
| ➕ | `jakarta.el` | 1 | `jakarta.el -> jakarta.el, org.open-metadata` |
| ➕ | `jakarta.el.api` | 1 | `jakarta.el -> jakarta.el, org.apache.tomcat` |
| ➕ | `jakarta.faces` | 1 | `com.guicedee.services -> com.guicedee.services, jakarta.faces` |
| ➕ | `jakarta.inject` | 1 | `jakarta.inject -> com.google.gerrit, jakarta.inject` |
| ➕ | `jakarta.json` | 1 | `jakarta.json -> jakarta.json, org.eclipse.parsson` |
| ➕ | `jakarta.jws` | 1 | `jakarta.jws -> com.guicedee.services, jakarta.jws` |
| ➕ | `jakarta.messaging` | 1 | `jakarta.jms -> jakarta.jms, org.apache.storm` |
| ➕ | `jakarta.resource` | 1 | `jakarta.resource -> jakarta.resource, org.jboss.ironjacamar` |
| ➕ | `jakarta.security.auth.message` | 1 | `jakarta.authentication -> jakarta.authentication, org.apache.tomcat` |
| ➕ | `jakarta.websocket.api` | 1 | `jakarta.websocket -> com.guicedee.services, jakarta.websocket` |
| ➕ | `jakarta.ws.rs` | 1 | `jakarta.ws.rs -> jakarta.ws.rs, org.jboss.narayana.lra` |
| ➕ | `java.annotation` | 1 | `javax.annotation -> javax.annotation, org.apache.tomcat` |
| ➕ | `java.base` | 1 | `com.rover12421.android.hide -> com.kohlschutter.jacline, com.rover12421.android.hide` |
| ➕ | `java.inject` | 1 | `io.github.pustike -> com.erudika, io.github.pustike` |
| ➕ | `java.json` | 1 | `javax.json -> javax.json, org.choco-solver` |
| ➕ | `java.json.bind` | 1 | `javax.json.bind -> javax.json.bind, org.open-metadata` |
| ➕ | `java.mail` | 1 | `com.sun.mail -> com.sun.mail, net.iotgw` |
| ➕ | `java.measure` | 1 | `javax.measure -> javax.measure, org.locationtech.geomesa` |
| ➕ | `java.money` | 1 | `javax.money -> dev.zabricraft, javax.money` |
| ➕ | `java.persistence` | 1 | `javax.persistence -> com.guicedee.services, javax.persistence` |
| ➕ | `java.rmi` | 1 | `fr.inria.gforge.spoon -> fr.inria.gforge.spoon, org.wso2.carbon.automation` |
| ➕ | `java.security.auth.message` | 1 | `jakarta.security.auth.message -> jakarta.security.auth.message, org.apache.tomcat` |
| ➕ | `java.security.jacc` | 1 | `jakarta.security.jacc -> jakarta.authorization, jakarta.security.jacc` |
| ➕ | `java.servlet` | 1 | `jakarta.servlet -> jakarta.servlet, org.apache.tomcat` |
| ➕ | `java.transaction` | 1 | `org.jboss.spec.javax.transaction -> com.guicedee.services, org.jboss.spec.javax.transaction` |
| ➕ | `java.validation` | 1 | `javax.validation -> com.guicedee.services, javax.validation` |
| ➕ | `java.ws.rs` | 1 | `javax.ws.rs -> javax.ws.rs, org.jboss.pnc.build-agent` |
| ➕ | `java.xml.bind` | 1 | `javax.xml.bind -> com.yahoo.vespa, javax.xml.bind` |
| ➕ | `javax.inject` | 1 | `com.jwebmp -> com.jwebmp, dev.ikm.jpms` |
| ➕ | `javax.jms` | 1 | `com.jwebmp.thirdparty.jms -> com.guicedee.services, com.jwebmp.thirdparty.jms` |
| ➕ | `javax.jws` | 1 | `javax.jws -> com.guicedee.services, javax.jws` |
| ➕ | `javax.servlet.*` | 2 | `com.guicedee.services -> com.guicedee.services, org.apache.tomcat` |
| ➕ | `jbase.*` | 3 | `net.sf.xtext-jbase -> io.github.lorenzobettini.jbase, net.sf.xtext-jbase` |
| ➕ | `jcohy.*` | 3 | `com.jcohy.gradle -> com.jcohy.gradle, io.github.jcohy` |
| ➕ | `jssc` | 1 | `net.jockx -> io.github.java-native, net.jockx` |
| ➕ | `kiwi` | 1 | `com.gitlab.tixtix320 -> com.github.tix320, com.gitlab.tixtix320` |
| ➕ | `kora.*` | 80 | `ru.tinkoff.kora -> io.koraframework, ru.tinkoff.kora` |
| ➕ | `kora.*` | 8 | `ru.tinkoff.kora.experimental -> io.koraframework.experimental, ru.tinkoff.kora.experimental` |
| ➕ | `kotlin.*` | 2 | `org.jetbrains.kotlin -> com.airbnb.viaduct, org.jetbrains.kotlin` |
| ➕ | `kotlin.stdlib` | 1 | `org.jetbrains.kotlin -> com.alibaba.ververica, org.jetbrains.kotlin` |
| ➕ | `kotlin.test` | 1 | `org.jetbrains.kotlin -> org.jetbrains.kotlin, xyz.block.kotlin-formatter` |
| ➕ | `library` | 1 | `build.buf.prototype -> build.buf.prototype, com.connectrpc` |
| ➕ | `liqp` | 1 | `nl.big-o -> io.github.luoxuansz, nl.big-o` |
| ➕ | `manifestGenerator` | 1 | `com.github.srujankujmar -> com.github.srujankujmar, io.hyscale` |
| ➕ | `me.linusdev.data` | 1 | `io.github.lni-dev -> de.linusdev, io.github.lni-dev` |
| ➕ | `me.tongfei.progressbar` | 1 | `me.tongfei -> io.github.o2alexanderfedin.javafv, me.tongfei` |
| ➕ | `mslinks` | 1 | `com.github.vatbub -> com.github.vatbub, org.jabref` |
| ➕ | `net.jbock.*` | 2 | `com.github.h908714124 -> com.github.h908714124, io.github.jbock-java` |
| ➕ | `net.kyori.ansi` | 1 | `net.kyori -> net.flectone.pulse, net.kyori` |
| ➕ | `net.kyori.examination.api` | 1 | `net.kyori -> io.github.zerog228, net.kyori` |
| ➕ | `net.kyori.examination.string` | 1 | `net.kyori -> eu.koboo, net.kyori` |
| ➕ | `net.postgis.jdbc` | 1 | `net.postgis -> com.enterprisedb, net.postgis` |
| ➕ | `net.sf.jsqlparser` | 1 | `com.github.jsqlparser -> com.github.jsqlparser, com.manticore-projects.jsqlformatter` |
| ➕ | `net.sf.uadetector.core` | 1 | `com.jwebmp.jre11 -> com.guicedee.modules.services, com.jwebmp.jre11` |
| ➕ | `net.sf.uadetector.resources` | 1 | `com.jwebmp.jre11 -> com.guicedee.modules.services, com.jwebmp.jre11` |
| ➕ | `net.sourceforge.argparse4j` | 1 | `net.sourceforge.argparse4j -> net.sourceforge.argparse4j, org.opendaylight.netconf` |
| ➕ | `netty.socketio.*` | 2 | `io.github.neatguycoding -> com.socketio4j, io.github.neatguycoding` |
| ➕ | `ogc.tools.gml.jts` | 1 | `io.github.soc -> io.github.soc, org.ogc-schemas` |
| ➕ | `okhttp` | 1 | `build.buf -> build.buf, com.connectrpc` |
| ➕ | `org.*` | 8 | `io.github.jwharm.javagi -> io.github.jwharm.javagi, org.java-gi` |
| ➕ | `org.apache.commons.beanutils` | 1 | `com.guicedee.services -> com.guicedee.services, org.wildfly` |
| ➕ | `org.apache.commons.cli` | 1 | `org.apache.shiro.tools -> org.apache.shiro.tools, org.teavm` |
| ➕ | `org.apache.commons.codec` | 1 | `commons-codec -> com.alibaba.ververica, commons-codec` |
| ➕ | `org.apache.commons.collections4` | 1 | `org.apache.commons -> com.guicedee.modules.services, org.apache.commons` |
| ➕ | `org.apache.commons.compress` | 1 | `org.apache.commons -> com.alibaba.ververica, org.apache.commons` |
| ➕ | `org.apache.commons.configuration2` | 1 | `org.apache.commons -> org.apache.commons, org.neo4j.procedure` |
| ➕ | `org.apache.commons.csv` | 1 | `io.github.pustike -> io.github.pustike, org.sonarsource.scanner.engine` |
| ➕ | `org.apache.commons.fileupload` | 1 | `com.jwebmp.jre11 -> com.guicedee.modules.services, com.jwebmp.jre11` |
| ➕ | `org.apache.commons.fileupload2.jakarta` | 1 | `org.apache.jena -> com.svenruppert, org.apache.jena` |
| ➕ | `org.apache.commons.fileupload2.jakarta.servlet6` | 1 | `org.apache.jena -> io.github.dhruvrawatdev, org.apache.jena` |
| ➕ | `org.apache.commons.imaging` | 1 | `org.apache.commons -> org.apache.commons, org.dromara` |
| ➕ | `org.apache.commons.io` | 1 | `commons-io -> commons-io, no.entur` |
| ➕ | `org.apache.commons.mail` | 1 | `com.github.ppodgorsek.email -> com.github.ppodgorsek.email, io.prophecy` |
| ➕ | `org.apache.commons.net` | 1 | `commons-net -> commons-net, int.esa.ccsds.mo` |
| ➕ | `org.apache.commons.pool2` | 1 | `org.apache.commons -> org.apache.commons, org.openjproxy` |
| ➕ | `org.apache.commons.text` | 1 | `org.apache.commons -> com.telamin.fluxtion, org.apache.commons` |
| ➕ | `org.apache.derby.*` | 2 | `org.apache.derby -> org.apache.derby, org.bridgedb` |
| ➕ | `org.apache.directory.scim.*` | 8 | `org.apache.directory.scimple -> io.acryl, org.apache.directory.scimple` |
| ➕ | `org.apache.felix.framework` | 1 | `org.apache.felix -> com.yahoo.vespa, org.apache.felix` |
| ➕ | `org.apache.logging.log4j.core` | 1 | `org.apache.logging.log4j -> app.freerouting, org.apache.logging.log4j` |
| ➕ | `org.apache.lucene.suggest` | 1 | `org.apache.lucene -> org.apache.lucene, org.opensearch.migrations.snapshots` |
| ➕ | `org.apache.poi.poi` | 1 | `org.apache.poi -> com.guicedee.modules.services, org.apache.poi` |
| ➕ | `org.apache.xmlbeans` | 1 | `com.guicedee.services -> com.guicedee.services, io.github.cdnk` |
| ➕ | `org.apiguardian.api` | 1 | `org.apiguardian -> org.apache.tinkerpop, org.apiguardian` |
| ➕ | `org.beryx.awt.color` | 1 | `org.beryx -> org.apache.sedona, org.beryx` |
| ➕ | `org.bouncycastle.lts.prov` | 1 | `org.bouncycastle -> org.bouncycastle, org.brylex` |
| ➕ | `org.bukkit` | 1 | `com.uroria.curepur -> com.mineplex.studio.server, com.uroria.curepur` |
| ➕ | `org.bytedeco.*` | 7 | `org.bytedeco -> org.bytedeco, us.ihmc` |
| ➕ | `org.codehaus.plexus.interpolation` | 1 | `org.codehaus.plexus -> me.chrissw-r1, org.codehaus.plexus` |
| ➕ | `org.commonmark` | 1 | `com.atlassian.commonmark -> com.atlassian.commonmark, com.qainsights` |
| ➕ | `org.commonmark.*` | 7 | `com.atlassian.commonmark -> com.atlassian.commonmark, org.commonmark` |
| ➕ | `org.commonmark.ext.gfm.tables` | 1 | `com.atlassian.commonmark -> com.atlassian.commonmark, io.github.dfengwei` |
| ➕ | `org.eclipse.emf.*` | 3 | `com.innoventsolutions.birt.runtime -> com.innoventsolutions.birt.runtime, org.eclipse.emf` |
| ➕ | `org.eclipse.jetty.alpn.conscrypt.server` | 1 | `org.eclipse.jetty -> com.adobe.campaign.tests.bridge.service, org.eclipse.jetty` |
| ➕ | `org.eclipse.oomph.console.*` | 3 | `com.github.a-langer -> com.github.a-langer, io.klib.tools` |
| ➕ | `org.eclipse.osgi` | 1 | `org.eclipse.tycho -> io.joynr.tools.generator, org.eclipse.tycho` |
| ➕ | `org.eclipse.swt.win32.win32.x86_64` | 1 | `org.eclipse.platform -> io.github.sundenjaeger, org.eclipse.platform` |
| ➕ | `org.freedesktop.dbus` | 1 | `com.github.hypfvieh -> com.github.hypfvieh, org.endlesssource.mediainterface` |
| ➕ | `org.freedesktop.gstreamer` | 1 | `org.freedesktop.gstreamer -> org.freedesktop.gstreamer, org.java-gi` |
| ➕ | `org.glassfish.jakarta.json` | 1 | `org.glassfish -> org.glassfish, org.nanopub` |
| ➕ | `org.glassfish.java.json` | 1 | `org.glassfish -> com.mobius-software.plugins, org.glassfish` |
| ➕ | `org.hibernate.validator` | 1 | `org.hibernate.validator -> com.guicedee.modules.services, org.hibernate.validator` |
| ➕ | `org.hsqldb` | 1 | `org.hsqldb -> com.github.massamany, org.hsqldb` |
| ➕ | `org.incode.platform.lib.servletapi` | 1 | `org.incode.module.slack -> org.incode.module.slack, org.isisaddons.module.servletapi` |
| ➕ | `org.jboss.jandex` | 1 | `org.jboss -> io.smallrye, org.jboss` |
| ➕ | `org.jboss.jandex.typeannotationtest` | 1 | `org.jboss.jandex -> io.smallrye, org.jboss.jandex` |
| ➕ | `org.jgrapht.core` | 1 | `org.jgrapht -> org.choco-solver, org.jgrapht` |
| ➕ | `org.jheaps` | 1 | `org.jheaps -> dev.ikm.jpms, org.jheaps` |
| ➕ | `org.jnetpcap` | 1 | `de.gematik -> com.slytechs.jnet.jnetpcap, de.gematik` |
| ➕ | `org.json` | 1 | `org.json -> com.github.karsaig, org.json` |
| ➕ | `org.jsoup` | 1 | `org.jsoup -> org.finos.legend.sdlc, org.jsoup` |
| ➕ | `org.jspecify` | 1 | `org.jspecify -> io.cryostat, org.jspecify` |
| ➕ | `org.leadpony.justify` | 1 | `org.leadpony.justify -> com.ibm.ta.sdk, org.leadpony.justify` |
| ➕ | `org.lz4.java` | 1 | `org.lz4 -> at.yawk.lz4, org.lz4` |
| ➕ | `org.mapstruct` | 1 | `org.mapstruct -> com.guicedee.modules.services, org.mapstruct` |
| ➕ | `org.mnode.ical4j.serializer` | 1 | `org.mnode.ical4j -> org.ical4j, org.mnode.ical4j` |
| ➕ | `org.neo4j.*` | 18 | `org.neo4j -> org.graphfoundation.ongdb, org.neo4j` |
| ➕ | `org.newsclub.net.unix` | 1 | `com.kohlschutter.junixsocket -> com.kohlschutter.junixsocket, com.sbbsystems.flink` |
| ➕ | `org.objectweb.asm.tree` | 1 | `org.ow2.asm -> io.killedkenny.crossfuzz, org.ow2.asm` |
| ➕ | `org.openapitools.jackson.nullable` | 1 | `org.openapitools -> io.airlift, org.openapitools` |
| ➕ | `org.opengis.geoapi` | 1 | `org.opengis -> org.apache.tika, org.opengis` |
| ➕ | `org.openqa.selenium.remote` | 1 | `org.seleniumhq.selenium -> dev.aherscu.qa, org.seleniumhq.selenium` |
| ➕ | `org.opensaml.security` | 1 | `org.opensaml -> org.elasticsearch.plugin, org.opensaml` |
| ➕ | `org.opentest4j` | 1 | `org.opentest4j -> berlin.yuna, org.opentest4j` |
| ➕ | `org.pcap4j.*` | 3 | `org.pcap4j -> io.vproxy, org.pcap4j` |
| ➕ | `org.pcollections` | 1 | `net.pincette -> net.pincette, org.pcollections` |
| ➕ | `org.primefaces.extensions` | 1 | `com.guicedee.services -> com.guicedee.services, org.primefaces.extensions` |
| ➕ | `org.reactivestreams` | 1 | `org.reactivestreams -> dev.ikm.jpms, org.reactivestreams` |
| ➕ | `org.roaringbitmap` | 1 | `com.clickhouse -> com.clickhouse, dev.ikm.jpms` |
| ➕ | `org.signal.libsignal` | 1 | `org.signal -> io.github.wanggenlin, org.signal` |
| ➕ | `org.teavm.metaprogramming.api` | 1 | `org.teavm -> com.fermyon, org.teavm` |
| ➕ | `org.testfx.monocle` | 1 | `org.testfx -> one.jpro.platform.jpms, org.testfx` |
| ➕ | `org.tinylog.api` | 1 | `org.tinylog -> de.bsommerfeld.pathetic, org.tinylog` |
| ➕ | `org.wildfly.common` | 1 | `org.wildfly.common -> io.quarkus, org.wildfly.common` |
| ➕ | `org.zeromq.jeromq` | 1 | `org.zeromq -> org.jetbrains.kotlinx, org.zeromq` |
| ➕ | `play.ws.standalone.*` | 4 | `com.typesafe.play -> com.typesafe.play, org.playframework` |
| ➕ | `pluginframework` | 1 | `com.github.srujankujmar -> com.github.srujankujmar, io.hyscale` |
| ➕ | `r2dbc.postgresql` | 1 | `io.r2dbc -> com.yugabyte, io.r2dbc` |
| ➕ | `retrofit2.*` | 18 | `com.squareup.retrofit2 -> com.squareup.retrofit2, io.github.mindcomic.retrofit2` |
| ➕ | `schema.validator` | 1 | `com.github.srujankujmar -> com.github.srujankujmar, io.hyscale` |
| ➕ | `service_spec_commons` | 1 | `com.github.srujankujmar -> com.github.srujankujmar, io.hyscale` |
| ➕ | `sonder` | 1 | `com.gitlab.tixtix320 -> com.github.tix320, com.gitlab.tixtix320` |
| ➕ | `stasgora.observetree` | 1 | `io.github.stasgora -> dev.sgora, io.github.stasgora` |
| ➕ | `swim.*` | 51 | `ai.swim -> ai.swim, org.swimos` |
| ➕ | `tech.uom.lib.common` | 1 | `tech.uom.lib -> io.pcp.agentparfait, tech.uom.lib` |
| ➕ | `tm.bitronix.btm` | 1 | `com.jwebmp.jre11 -> com.guicedee.services, com.jwebmp.jre11` |
| ➕ | `tornadofx` | 1 | `it.unibo.alchemist -> com.googlecode.blaisemath.tornado, it.unibo.alchemist` |
| ➕ | `troubleshooting.integration` | 1 | `com.github.srujankujmar -> com.github.srujankujmar, io.hyscale` |
| ➕ | `tucache.*` | 3 | `co.tunan.tucache -> co.tunan.tucache, io.github.tri5m` |
| ➕ | `tuweni.*` | 36 | `org.apache.tuweni -> io.tmio, org.apache.tuweni` |
| ➕ | `tuweni.*` | 14 | `org.apache.tuweni -> io.consensys.tuweni, org.apache.tuweni` |
| ➕ | `vault.java.driver` | 1 | `com.bettercloud -> com.bettercloud, io.github.jopenlibs` |

