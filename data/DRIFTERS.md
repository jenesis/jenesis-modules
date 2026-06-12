# Module ownership drifters

Generated 2026-06-12. A module *drifts* when more than one groupId publishes the name and its `owners.tsv` does not yet name every publisher (no `owners.tsv`, or one that leaves some publishing groupId neither `allowed` nor `blocked`). Resolving a drift means deciding each groupId via `SetOwners` (which writes `allowed`/`blocked`); a fully-named module drops off this list.

| Category | Modules |
|---|---:|
| republisher | 0 |
| migration | 0 |
| fork | 0 |
| shaded | 1204 |
| unclassified | 460 |
| **unresolved total** | **1664** |
| multi-owner modules scanned | 3572 |
| modules scanned | 36334 |

Timeline axis spans 2017-01 .. 2026-06 (today). Per group: decision `A`=allowed `B`=blocked `?`=undecided, `*`=current owner, then the publication range, latest version, and a `=` activity bar across the axis.

## republisher (0)

Earliest owner is foreign to the module name while a natural-namespace owner is also present (shaded / repackaged jars). Proposal: allow the natural owner, block the republisher.

## migration (0)

The publishing groupId handed off over time: the old coordinate went dormant, a newer one took over (a rename or a relocation). Proposal: allow both old and new so history stays resolvable and `latest` is current.

## fork (0)

A cross-org coordinate publishes the same name while the original owner is still active. Proposal: keep the original owner, block the fork.

## shaded (1204)

The natural-namespace owner (the module name falls under its groupId) is the earliest and most-recent publisher; every other group merely shades or bundles the name under its own coordinate. Proposal: allow the natural owner, block the rest. Resolution is unchanged; this just records the decision so the module drops off the report.

| count | current owner -> proposed allowed |
|---:|---|
| 454 | `software.amazon.awssdk -> software.amazon.awssdk` |
| 98 | `io.ktor -> io.ktor` |
| 93 | `org.apache.cxf -> org.apache.cxf` |
| 64 | `org.lwjgl -> org.lwjgl,org.lwjgl.osgi` |
| 44 | `org.bytedeco -> org.bytedeco` |
| 38 | `org.neo4j -> org.neo4j` |
| 27 | `org.codehaus.groovy -> org.codehaus.groovy,org.codehaus.groovy.groovy-all.3.0.8.repo.org.codehaus.groovy` |
| 16 | `org.apache.lucene -> org.apache.lucene` |
| 15 | `com.amazonaws -> com.amazonaws` |
| 15 | `org.eclipse.jgit -> org.eclipse.jgit` |
| 11 | `io.netty -> io.netty` |
| 9 | `com.azure -> com.azure` |
| 8 | `com.fasterxml.jackson.dataformat -> com.fasterxml.jackson.dataformat` |
| 8 | `com.fasterxml.jackson.datatype -> com.fasterxml.jackson.datatype` |
| 8 | `com.twelvemonkeys.imageio -> com.twelvemonkeys.imageio` |

_Showing the 200 most recently active of 1204. For the full list, emit the SetOwners file: `-Djenesis.crawler.drift.emit=shaded`._

```
ch.qos.logback.classic  [owned by `ch.qos.logback`; 92 other group(s) shade the name]
  ? * ch.qos.logback                       2018-01..2026-06 1.5.34       |..==================|
  ?   com.daml                             2022-10..2026-05 3.6.0-snapshot.20260529.14710.0.va1ec8126 |............========|
  ?   io.mosip.biosdk                      2024-12..2026-05 1.3.1        |................====|
  ?   io.mosip.demosdk                     2025-03..2026-05 1.3.1        |................====|
  ?   de.fraunhofer.iosb.ilt               2023-01..2026-05 0.38         |............========|
  ?   org.apache.sling                     2025-05..2026-05 6.1.0        |.................===|
    + 87 more: dk.alexandra.fresco, org.jboss.pnc.gradle-manipulator, com.deltaproto, org.javastro.ivoa, org.commonjava.atlas, ch.exense.step, club.dawdler, org.eclipse.ecsp, org.apache.zookeeper, com.fenxi365, de.caluga, io.camunda, (+75 more)
ch.qos.logback.core  [owned by `ch.qos.logback`; 30 other group(s) shade the name]
  ? * ch.qos.logback                       2018-01..2026-06 1.5.34       |..==================|
  ?   com.effacy.jui                       2024-12..2026-05 0.3.5        |................====|
  ?   io.spicelabs                         2026-03..2026-05 0.15.5       |...................=|
  ?   com.limemojito.oss.aws               2026-03..2026-05 8.0.10       |...................=|
  ?   de.gematik.test                      2025-11..2026-05 4.2.7        |..................==|
  ?   com.deltaproto                       2026-04..2026-05 1.1.4        |...................=|
    + 25 more: org.ton.ton4j, com.expediagroup, io.camunda, org.chenile, club.dawdler, org.jetbrains.kotlinx, io.github.neodix42, com.yetanalytics, org.opendaylight.bgpcep, fun.adaptive, org.docshare, io.flux-capacitor, (+13 more)
com.fasterxml.jackson.jakarta.rs.base  [owned by `com.fasterxml.jackson.jakarta.rs`; 5 other group(s) shade the name]
  ? * com.fasterxml.jackson.jakarta.rs     2021-07..2026-06 2.22.0       |.........===========|
  ?   io.trino.gateway                     2025-02..2026-05 19           |................====|
  ?   io.streamthoughts                    2026-02..2026-03 0.37.3       |..................==|
  ?   com.docusign                         2024-06..2026-03 2.1.0        |...............=====|
  ?   org.graylog2                         2024-05..2025-07 6.2.5        |...............===..|
  ?   io.trino                             2025-01..2025-06 476          |................==..|
com.fasterxml.jackson.jakarta.rs.json  [owned by `com.fasterxml.jackson.jakarta.rs`; 17 other group(s) shade the name]
  ? * com.fasterxml.jackson.jakarta.rs     2021-07..2026-06 2.22.0       |.........===========|
  ?   org.apache.tika                      2023-12..2026-05 3.3.1        |..............======|
  ?   com.phonepe.sentinel-ai              2026-05..2026-05 1.1.2-SOLARIS-rc0 |...................=|
  ?   ch.exense.step.library               2023-08..2026-05 1.0.31       |.............=======|
  ?   ch.exense.step                       2022-10..2026-03 3.29.4       |............========|
  ?   org.eclipse.tractusx.edc             2023-06..2026-03 0.10.3       |.............=======|
    + 12 more: org.ow2.petals.samples.rest.edm, dev.getelements.elements, org.eclipse.edc.huawei, org.eclipse.edc.aws, org.eclipse.edc, io.nflow, com.brightsparklabs, io.trino.gateway, com.snehasishroy, com.smoketurner.dropwizard, org.kiwiproject, org.dhatim
com.fasterxml.jackson.jakarta.rs.xml  [owned by `com.fasterxml.jackson.jakarta.rs`; 1 other group(s) shade the name]
  ? * com.fasterxml.jackson.jakarta.rs     2021-07..2026-06 2.22.0       |.........===========|
  ?   com.graphhopper                      2025-10..2025-10 11.0         |..................=.|
com.fasterxml.jackson.jaxrs.base  [owned by `com.fasterxml.jackson.jaxrs`; 7 other group(s) shade the name]
  ? * com.fasterxml.jackson.jaxrs          2019-07..2026-06 2.22.0       |.....===============|
  ?   org.graylog2                         2022-11..2024-10 5.2.12       |............=====...|
  ?   org.jboss.pnc                        2021-11..2021-11 1.0.0        |..........=.........|
  ?   org.jboss.pnc.cleaner                2021-07..2021-07 2.1.0        |.........=..........|
  ?   org.jboss.pnc.kafkastore             2021-02..2021-06 1.0.5        |........==..........|
  ?   org.projectnessie                    2021-05..2021-06 0.7.0        |.........=..........|
    + 2 more: org.kie.kogito, com.guicedee.services
com.fasterxml.jackson.jaxrs.cbor  [owned by `com.fasterxml.jackson.jaxrs`; 1 other group(s) shade the name]
  ? * com.fasterxml.jackson.jaxrs          2017-10..2026-06 2.22.0       |.===================|
  ?   com.palantir.atlasdb                 2022-03..2022-09 0.702.0-rc1  |..........==........|
com.fasterxml.jackson.jaxrs.xml  [owned by `com.fasterxml.jackson.jaxrs`; 1 other group(s) shade the name]
  ? * com.fasterxml.jackson.jaxrs          2017-10..2026-06 2.22.0       |.===================|
  ?   com.graphhopper                      2023-10..2025-01 10.2         |..............===...|
com.fasterxml.jackson.datatype.jaxrs  [owned by `com.fasterxml.jackson.datatype`; 1 other group(s) shade the name]
  ? * com.fasterxml.jackson.datatype       2017-10..2026-06 2.22.0       |.===================|
  ?   com.guicedee.services                2020-11..2020-11 1.1.0.2-jre14 |........=...........|
com.fasterxml.jackson.jaxrs.yaml  [owned by `com.fasterxml.jackson.jaxrs`; 1 other group(s) shade the name]
  ? * com.fasterxml.jackson.jaxrs          2017-10..2026-06 2.22.0       |.===================|
  ?   io.apiwiz.astrum                     2022-01..2022-01 0.1          |..........=.........|
com.fasterxml.jackson.module.paramnames  [owned by `com.fasterxml.jackson.module`; 6 other group(s) shade the name]
  ? * com.fasterxml.jackson.module         2017-10..2026-06 2.22.0       |.===================|
  ?   com.infobip                          2026-03..2026-03 3.0.1        |...................=|
  ?   io.kestra                            2025-07..2025-08 0.23.12      |.................=..|
  ?   com.araksis                          2025-03..2025-03 0.0.1a       |.................=..|
  ?   com.araksis.sjd                      2025-03..2025-03 0.0.1        |.................=..|
  ?   io.github.codgen                     2024-04..2024-11 1.1.17       |...............==...|
    + 1 more: io.micronaut.example
com.fasterxml.jackson.datatype.jdk8  [owned by `com.fasterxml.jackson.datatype`; 35 other group(s) shade the name]
  ? * com.fasterxml.jackson.datatype       2017-10..2026-06 2.22.0       |.===================|
  ?   org.ic4j                             2026-03..2026-05 0.8.2        |...................=|
  ?   io.github.unmeshjoshi                2025-10..2026-05 0.1.0-alpha.27 |..................==|
  ?   com.networknt                        2022-08..2026-05 2.3.4        |...........=========|
  ?   org.apache.grails                    2025-06..2026-04 7.1.0        |.................===|
  ?   io.github.tansuasici                 2026-02..2026-02 0.0.2        |..................=.|
    + 30 more: ai.onehouse, com.atlan, io.github.demiourgoi, io.kestra.plugin, com.tencent.cloud, hu.webarticum.miniconnect, io.openlineage, de.m3y.parquet, io.edurt.datacap, org.apache.parquet, io.opentelemetry.javaagent, ai.superstream, (+18 more)
com.fasterxml.jackson.datatype.jsr310  [owned by `com.fasterxml.jackson.datatype`; 84 other group(s) shade the name]
  ? * com.fasterxml.jackson.datatype       2017-10..2026-06 2.22.0       |.===================|
  ?   org.opencds.cqf.cql.ls               2026-03..2026-05 4.7.0        |...................=|
  ?   cab.ml                               2026-05..2026-05 0.1.0-RC     |...................=|
  ?   org.octopusden.octopus.automation.teamcity 2024-08..2026-05 1.0.36       |...............=====|
  ?   org.apache.hudi                      2023-09..2026-05 1.2.0        |..............======|
  ?   com.linkedin.iceberg                 2025-09..2026-05 1.2.0.17     |.................===|
    + 79 more: io.openlineage, org.apache.gravitino, org.openapitools, org.byteveda.agenteval, org.codelibs.fess, org.apache.doris, io.spring.gradle, io.camunda.filestorage, org.octopusden.octopus.automation.artifactory, org.openapitools.openapidiff, com.expediagroup, de.jvstvshd.necrify, (+67 more)
com.fasterxml.jackson.datatype.hibernate6  [owned by `com.fasterxml.jackson.datatype`; 2 other group(s) shade the name]
  ? * com.fasterxml.jackson.datatype       2023-03..2026-06 2.22.0       |............========|
  ?   io.bitdive                           2025-03..2026-04 1.3.22       |.................===|
  ?   com.bowerzlabs                       2026-04..2026-04 0.1.0-beta   |...................=|
com.fasterxml.jackson.datatype.joda  [owned by `com.fasterxml.jackson.datatype`; 3 other group(s) shade the name]
  ? * com.fasterxml.jackson.datatype       2017-10..2026-06 2.22.0       |.===================|
  ?   io.kestra.plugin                     2024-06..2024-08 0.18.1       |...............=....|
  ?   org.apache.beam                      2022-05..2023-05 2.48.0       |...........===......|
  ?   com.seeq                             2021-12..2022-08 55.4.9-v202208021422 |..........==........|
com.fasterxml.jackson.datatype.jsonp  [owned by `com.fasterxml.jackson.datatype`; 1 other group(s) shade the name]
  ? * com.fasterxml.jackson.datatype       2021-03..2026-06 2.22.0       |........============|
  ?   com.arangodb                         2025-01..2026-04 7.26.0       |................====|
com.fasterxml.jackson.datatype.jsonorg  [owned by `com.fasterxml.jackson.datatype`; 1 other group(s) shade the name]
  ? * com.fasterxml.jackson.datatype       2017-10..2026-06 2.22.0       |.===================|
  ?   io.apicurio                          2021-12..2022-04 2.2.3.Final  |..........==........|
com.fasterxml.jackson.datatype.guava  [owned by `com.fasterxml.jackson.datatype`; 4 other group(s) shade the name]
  ? * com.fasterxml.jackson.datatype       2017-10..2026-06 2.22.0       |.===================|
  ?   com.palantir.atlasdb                 2023-08..2024-10 0.1172.0     |.............====...|
  ?   io.kestra.plugin                     2024-03..2024-04 0.16.1       |..............==....|
  ?   org.openapitools                     2022-04..2022-09 6.1.0        |...........=........|
  ?   io.github.marquezproject             2020-08..2021-08 0.17.0       |.......===..........|
com.fasterxml.jackson.module.jsonSchema  [owned by `com.fasterxml.jackson.module`; 3 other group(s) shade the name]
  ? * com.fasterxml.jackson.module         2019-07..2026-06 2.22.0       |.....===============|
  ?   org.openidentityplatform.openam      2022-12..2025-04 15.1.6       |............======..|
  ?   cc.unitmesh                          2023-10..2024-07 1.0.0        |..............==....|
  ?   io.pravega                           2020-09..2021-10 0.3.0        |.......====.........|
com.fasterxml.jackson.dataformat.xml  [owned by `com.fasterxml.jackson.dataformat`; 30 other group(s) shade the name]
  ? * com.fasterxml.jackson.dataformat     2017-10..2026-06 2.22.0       |.===================|
  ?   org.pitest                           2024-05..2026-05 1.25.3       |...............=====|
  ?   ai.onehouse                          2026-01..2026-05 0.29.0       |..................==|
  ?   com.pawtograder.org.pitest           2026-05..2026-05 2.0.0        |...................=|
  ?   com.aliyun                           2025-09..2026-04 3.3.5-2.0.26-alpha-shade |..................==|
  ?   org.jetbrains.dokka                  2020-12..2026-03 2.2.0        |........============|
    + 25 more: org.testingisdocumenting.znai, com.github.nagyesta.file-barj, io.kestra.plugin, it.unibo.alchemist, com.spectralogic.ds3, io.github.wangminan, com.marklogic, com.feylesoft, io.kestra.storage, com.atlan, org.projectnessie.nessie-integrations, com.sonatype.clm, (+13 more)
com.fasterxml.jackson.dataformat.cbor  [owned by `com.fasterxml.jackson.dataformat`; 22 other group(s) shade the name]
  ? * com.fasterxml.jackson.dataformat     2017-10..2026-06 2.22.0       |.===================|
  ?   net.snowflake                        2023-06..2026-04 4.4.3        |.............=======|
  ?   org.eclipse.ditto                    2022-03..2026-03 1.1.0        |..........==========|
  ?   org.apache.dolphinscheduler          2025-03..2026-03 3.4.1        |.................==.|
  ?   com.amazonaws                        2020-09..2025-12 1.12.797     |.......============.|
  ?   org.apache.jackrabbit                2023-01..2025-09 1.22.23      |............=======.|
    + 17 more: software.amazon.neptune, org.elasticsearch.plugin, com.hazelcast.jet, org.duracloud, org.alluxio, org.apache.beam, com.liferay.portal, io.kestra.plugin, org.apache.flink, edu.internet2.middleware.grouper, org.apache.pinot, software.amazon.awssdk, (+5 more)
com.fasterxml.jackson.dataformat.protobuf  [owned by `com.fasterxml.jackson.dataformat`; 2 other group(s) shade the name]
  ? * com.fasterxml.jackson.dataformat     2017-10..2026-06 2.22.0       |.===================|
  ?   org.projectnessie.cel                2023-11..2023-11 0.4.2        |..............=.....|
  ?   org.projectnessie                    2021-10..2022-01 0.18.0       |..........=.........|
com.fasterxml.jackson.dataformat.smile  [owned by `com.fasterxml.jackson.dataformat`; 7 other group(s) shade the name]
  ? * com.fasterxml.jackson.dataformat     2017-10..2026-06 2.22.0       |.===================|
  ?   org.opensearch.migrations.snapshots  2026-04..2026-05 0.3.2.1      |...................=|
  ?   com.netflix.spectator                2021-10..2025-08 1.8.17       |..........========..|
  ?   io.acryl                             2024-01..2025-06 1.1.0.3rc1   |..............====..|
  ?   org.apache.hudi                      2024-07..2024-07 1.0.0-beta2  |...............=....|
  ?   org.pipservices                      2022-06..2023-11 0.0.1        |...........====.....|
    + 2 more: com.hazelcast.jet, io.trino
com.fasterxml.jackson.dataformat.csv  [owned by `com.fasterxml.jackson.dataformat`; 7 other group(s) shade the name]
  ? * com.fasterxml.jackson.dataformat     2017-10..2026-06 2.22.0       |.===================|
  ?   com.opendxl                          2025-05..2025-05 2.7.0        |.................=..|
  ?   com.hazelcast.jet                    2022-06..2024-07 5.3.8        |...........=====....|
  ?   org.apache.beam                      2021-01..2024-04 2.56.0       |........========....|
  ?   com.microsoft.azure                  2024-02..2024-03 1.0.4        |..............=.....|
  ?   com.linkedin.feathr                  2022-11..2023-06 1.0.5-rc5    |............==......|
    + 2 more: com.marklogic, com.hortonworks.registries
com.fasterxml.jackson.dataformat.javaprop  [owned by `com.fasterxml.jackson.dataformat`; 1 other group(s) shade the name]
  ? * com.fasterxml.jackson.dataformat     2017-10..2026-06 2.22.0       |.===================|
  ?   org.apache.seatunnel                 2024-02..2026-02 2.3.13       |..............=====.|
com.fasterxml.jackson.dataformat.toml  [owned by `com.fasterxml.jackson.dataformat`; 6 other group(s) shade the name]
  ? * com.fasterxml.jackson.dataformat     2021-04..2026-06 2.22.0       |.........===========|
  ?   org.monarchinitiative.vitt           2026-01..2026-01 0.1.0        |..................=.|
  ?   com.github.jobservice                2024-02..2024-02 7.0.0-334    |..............=.....|
  ?   com.github.cafapi.util.flywayinstaller 2024-02..2024-02 2.0.0-184    |..............=.....|
  ?   com.cefoler.configuration            2022-04..2022-05 1.2.4        |...........=........|
  ?   dev.madetobuild.typedconfig          2022-03..2022-03 0.2          |..........=.........|
    + 1 more: com.github.nanodeath
com.fasterxml.jackson.dataformat.yaml  [owned by `com.fasterxml.jackson.dataformat`; 64 other group(s) shade the name]
  ? * com.fasterxml.jackson.dataformat     2017-10..2026-06 2.22.0       |.===================|
  ?   org.apiaddicts.apitools.dosonarapi   2025-01..2026-05 1.4.1-beta-3 |................====|
  ?   com.helpchoice                       2026-04..2026-05 1.1.0        |...................=|
  ?   io.telicent.jena                     2025-06..2026-05 3.0.3        |.................===|
  ?   com.sagframe                         2022-01..2026-05 5.2.10       |..........==========|
  ?   io.fabrikt                           2026-03..2026-05 27.1.0       |..................==|
    + 59 more: net.corda, dev.skyramp, org.testcontainers, com.cjbooms, org.apache.dolphinscheduler, io.github.pavan2504, io.github.rohitect, org.apache.plc4x, org.wildfly.prospero, com.free-now.sauron.plugins, io.substrait, de.fraunhofer.aisec, (+47 more)
com.fasterxml.jackson.module.afterburner  [owned by `com.fasterxml.jackson.module`; 5 other group(s) shade the name]
  ? * com.fasterxml.jackson.module         2017-10..2026-06 2.22.0       |.===================|
  ?   org.apache.hudi                      2024-06..2026-05 1.2.0        |...............=====|
  ?   org.kill-bill.billing.plugin.java    2021-01..2026-04 1.0.3        |........============|
  ?   io.orkes.conductor                   2024-12..2026-02 5.0.1        |................===.|
  ?   org.conductoross                     2024-12..2026-02 5.0.1        |................===.|
  ?   org.kill-bill.billing                2020-09..2022-10 0.40.13      |.......======.......|
com.fasterxml.jackson.module.jakarta.xmlbind  [owned by `com.fasterxml.jackson.module`; 17 other group(s) shade the name]
  ? * com.fasterxml.jackson.module         2021-07..2026-06 2.22.0       |.........===========|
  ?   com.datadoghq                        2022-08..2026-05 2.55.0       |...........=========|
  ?   com.salesforce.multicloudj           2025-07..2026-03 0.3.0        |.................===|
  ?   org.glassfish.main.jackson.module    2022-11..2026-03 8.0.1        |............========|
  ?   de.ipk-gatersleben                   2025-08..2025-08 3.0.5        |.................=..|
  ?   com.icegreen                         2023-03..2025-08 2.1.5        |............======..|
    + 12 more: com.instaclustr, ch.exense.step, video.bug, com.sap.scimono, com.docusign, it.vige.cities, com.fujitsu.launcher, io.streamthoughts, ch.exense.step.library, io.bdeploy, org.eclipse.edc, org.eclipse.dataspaceconnector
com.fasterxml.jackson.module.jaxb  [owned by `com.fasterxml.jackson.module`; 95 other group(s) shade the name]
  ? * com.fasterxml.jackson.module         2017-10..2026-06 2.22.0       |.===================|
  ?   com.facebook.presto.spark            2026-05..2026-05 3.4.1-2      |...................=|
  ?   com.datastax.oss                     2021-06..2026-05 6.0.10       |.........===========|
  ?   com.rovio.ingest                     2024-10..2026-04 1.0.8_spark_3.4.1 |................====|
  ?   org.apache.dolphinscheduler          2025-03..2026-03 3.4.1        |.................==.|
  ?   org.apache.seatunnel                 2022-09..2026-02 2.3.13       |...........========.|
    + 90 more: org.apache.pulsar, com.ascentstream.pulsar, io.github.solven-eu.cleanthat, com.oceanbase, io.github.dodogeny, com.solacecoe.connectors, com.seeq, io.streamnative.connectors, org.apache.phoenix, com.kenstott.components, io.cdap.cdap, com.xenoamess.necrodialysis.pulsar-flink, (+78 more)
com.fasterxml.jackson.module.paranamer  [owned by `com.fasterxml.jackson.module`; 5 other group(s) shade the name]
  ? * com.fasterxml.jackson.module         2017-10..2026-06 2.22.0       |.===================|
  ?   com.criteo                           2022-07..2022-07 1.1-spark3   |...........=........|
  ?   org.openpolicyagent.kafka            2021-11..2022-01 1.4.0        |..........=.........|
  ?   com.bisnode.kafka.authorization      2021-03..2021-10 1.2.0        |........===.........|
  ?   org.apache.beam                      2020-04..2021-02 2.28.0       |......===...........|
  ?   ai.whylabs                           2021-02..2021-02 0.1.2-b0     |........=...........|
com.fasterxml.jackson.module.guice  [owned by `com.fasterxml.jackson.module`; 2 other group(s) shade the name]
  ? * com.fasterxml.jackson.module         2017-10..2026-06 2.22.0       |.===================|
  ?   com.jwebmp.jpms.jackson.module       2019-04..2019-08 0.68.0.1     |....==..............|
  ?   com.jwebmp.jackson.module            2019-02..2019-04 0.66.0.1     |....=...............|
com.fasterxml.jackson.jr.ob  [owned by `com.fasterxml.jackson.jr`; 2 other group(s) shade the name]
  ? * com.fasterxml.jackson.jr             2017-10..2026-06 2.22.0       |.===================|
  ?   it.mulders                           2025-11..2026-05 0.10.0       |..................==|
  ?   io.akeyless                          2026-03..2026-04 1.1.0        |...................=|
com.github.javaparser.core  [owned by `com.github.javaparser`; 3 other group(s) shade the name]
  ? * com.github.javaparser                2017-12..2026-05 3.28.2       |..==================|
  ?   org.checkerframework                 2023-09..2026-05 3.28.1       |.............=======|
  ?   org.mvel.javaparser                  2026-02..2026-02 3.25.5-mvel3-1 |..................=.|
  ?   io.joern                             2022-06..2022-06 3.24.3-SL3   |...........=........|
com.github.javaparser.core.serialization  [owned by `com.github.javaparser`; 2 other group(s) shade the name]
  ? * com.github.javaparser                2018-11..2026-05 3.28.2       |....================|
  ?   org.mvel.javaparser                  2026-02..2026-02 3.25.5-mvel3-1 |..................=.|
  ?   io.joern                             2022-06..2022-06 3.24.3-SL3   |...........=........|
com.github.javaparser.symbolsolver.core  [owned by `com.github.javaparser`; 2 other group(s) shade the name]
  ? * com.github.javaparser                2018-01..2026-05 3.28.2       |..==================|
  ?   org.mvel.javaparser                  2026-02..2026-02 3.25.5-mvel3-1 |..................=.|
  ?   io.joern                             2022-06..2022-06 3.24.3-SL3   |...........=........|
com.nimbusds.jose.jwt  [owned by `com.nimbusds`; 10 other group(s) shade the name]
  ? * com.nimbusds                         2020-08..2026-05 10.9.1       |.......=============|
  ?   com.vaadin                           2025-07..2026-05 3.0.2        |.................===|
  ?   fish.payara.security.connectors      2024-05..2026-04 2.9.0        |...............=====|
  ?   org.bonitasoft.connectors            2026-04..2026-04 1.0.0-beta.1 |...................=|
  ?   org.apache.hadoop                    2026-03..2026-03 3.5.0        |...................=|
  ?   com.waveinformatica.skysso           2025-09..2025-09 1.3.0        |..................=.|
    + 5 more: io.github.swiyu-admin-ch, io.okdp, org.project-kessel, com.liferay, com.thetransactioncompany
com.renomad.minum  [owned by `com.renomad`; 1 other group(s) shade the name]
  ? * com.renomad                          2024-11..2026-05 10.0.3       |................====|
  ?   io.github.tanin47                    2025-10..2025-10 1.1.1        |..................=.|
io.pebbletemplates  [owned by `io.pebbletemplates`; 1 other group(s) shade the name]
  ? * io.pebbletemplates                   2019-01..2026-05 4.1.2        |....================|
  ?   io.vertx                             2021-05..2021-05 4.1.0.CR1    |.........=..........|
org.flywaydb.core  [owned by `org.flywaydb`; 3 other group(s) shade the name]
  ? * org.flywaydb                         2017-12..2026-05 12.7.0       |..==================|
  ?   io.github.coolbeevip                 2023-03..2026-01 9.15.2.5     |............=======.|
  ?   io.gitee.gbase8s                     2025-01..2025-01 6.5.7        |................=...|
  ?   org.flywaydb.enterprise              2020-04..2022-07 9.0.0        |.......=====........|
  ?   io.github.linceln                    2021-07..2021-08 5.0.8        |.........=..........|
  ?   org.flywaydb.pro                     2020-04..2020-09 6.5.7        |.......=............|
org.atmosphere  [owned by `org.atmosphere`; 0 other group(s) shade the name]
  ? * org.atmosphere                       2026-02..2026-05 4.0.49       |..................==|
  ?   org.atmosphere.samples               2026-03..2026-03 4.0.19       |..................==|
org.conscrypt  [owned by `org.conscrypt`; 4 other group(s) shade the name]
  ? * org.conscrypt                        2018-11..2026-05 2.6-alpha2   |....================|
  ?   com.infinilabs                       2026-02..2026-02 1.0.13       |..................=.|
  ?   net.bruestel                         2026-01..2026-01 2.6.0-20260113 |..................=.|
  ?   net.tongsuo                          2023-01..2023-01 1.0.0        |............=.......|
  ?   info.guardianproject.conscrypt       2021-11..2022-03 2.6.alpha1647601986.job2220801545 |..........=.........|
org.neo4j.io  [owned by `org.neo4j`; 1 other group(s) shade the name]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.random.values  [owned by `org.neo4j`; 1 other group(s) shade the name]
  ? * org.neo4j                            2018-05..2026-05 2026.05.0    |...=================|
  ?   org.graphfoundation.ongdb            2020-04..2020-12 3.6.2        |.......==...........|
org.neo4j.collection  [owned by `org.neo4j`; 1 other group(s) shade the name]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.common  [owned by `org.neo4j`; 1 other group(s) shade the name]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.consistency  [owned by `org.neo4j`; 1 other group(s) shade the name]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.cypher.internal.runtime.util  [owned by `org.neo4j`; 1 other group(s) shade the name]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.kernel  [owned by `org.neo4j`; 1 other group(s) shade the name]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.tooling.procedure  [owned by `org.neo4j`; 1 other group(s) shade the name]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.unsafe  [owned by `org.neo4j`; 1 other group(s) shade the name]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.bolt  [owned by `org.neo4j`; 1 other group(s) shade the name]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.collection.concurrent  [owned by `org.neo4j`; 1 other group(s) shade the name]
  ? * org.neo4j                            2018-05..2026-05 2026.05.0    |...=================|
  ?   org.graphfoundation.ongdb            2020-04..2020-12 3.6.2        |.......==...........|
org.neo4j.community  [owned by `org.neo4j`; 1 other group(s) shade the name]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.cypher  [owned by `org.neo4j`; 1 other group(s) shade the name]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.index.lucene  [owned by `org.neo4j`; 1 other group(s) shade the name]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.internal.diagnostics  [owned by `org.neo4j`; 1 other group(s) shade the name]
  ? * org.neo4j                            2018-08..2026-05 2026.05.0    |...=================|
  ?   org.graphfoundation.ongdb            2020-04..2020-12 3.6.2        |.......==...........|
org.neo4j.values  [owned by `org.neo4j`; 1 other group(s) shade the name]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.commandline  [owned by `org.neo4j`; 1 other group(s) shade the name]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.dbms  [owned by `org.neo4j`; 1 other group(s) shade the name]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.graphdb  [owned by `org.neo4j`; 1 other group(s) shade the name]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.index  [owned by `org.neo4j`; 1 other group(s) shade the name]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.logging  [owned by `org.neo4j`; 1 other group(s) shade the name]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.shell  [owned by `org.neo4j`; 1 other group(s) shade the name]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2021-03..2025-06 1.0.6        |........==========..|
org.neo4j.ssl  [owned by `org.neo4j`; 1 other group(s) shade the name]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.codegen  [owned by `org.neo4j`; 1 other group(s) shade the name]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.community.bolt.it  [owned by `org.neo4j.community`; 0 other group(s) shade the name]
  ? * org.neo4j.community                  2019-12..2026-05 2026.05.0    |......==============|
  ?   org.neo4j                            2021-01..2026-02 4.4.48       |........===========.|
org.neo4j.csv  [owned by `org.neo4j`; 1 other group(s) shade the name]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.cypher.internal.runtime.interpreted  [owned by `org.neo4j`; 1 other group(s) shade the name]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.gis.spatial.index  [owned by `org.neo4j`; 1 other group(s) shade the name]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.graphdb.resource  [owned by `org.neo4j`; 1 other group(s) shade the name]
  ? * org.neo4j                            2018-07..2026-05 2026.05.0    |...=================|
  ?   org.graphfoundation.ongdb            2020-04..2020-12 3.6.2        |.......==...........|
org.neo4j.configuration  [owned by `org.neo4j`; 1 other group(s) shade the name]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.graphalgo  [owned by `org.neo4j`; 1 other group(s) shade the name]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.internal.kernel.api  [owned by `org.neo4j`; 1 other group(s) shade the name]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.server.api  [owned by `org.neo4j`; 1 other group(s) shade the name]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.cypher.internal  [owned by `org.neo4j`; 1 other group(s) shade the name]
  ? * org.neo4j                            2019-04..2026-05 2026.05.0    |....================|
  ?   org.graphfoundation.ongdb            2020-04..2020-12 3.6.2        |.......==...........|
org.neo4j.server.security  [owned by `org.neo4j`; 1 other group(s) shade the name]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.jline  [owned by `org.jline`; 2 other group(s) shade the name]
  ? * org.jline                            2020-05..2026-05 4.1.3        |.......=============|
  ?   org.glassfish.main.osgi-platforms    2022-04..2026-05 8.0.2        |...........=========|
  ?   org.apache.karaf                     2020-10..2024-04 4.4.6        |........========....|
com.rabbitmq.client  [owned by `com.rabbitmq`; 2 other group(s) shade the name]
  ? * com.rabbitmq                         2017-12..2026-05 5.31.0       |..==================|
  ?   com.guicedee.modules.services        2026-04..2026-05 2.0.2        |...................=|
  ?   com.guicedee.services                2021-11..2022-02 1.2.2.1-jre17 |..........=.........|
com.azure.security.keyvault.keys  [owned by `com.azure`; 1 other group(s) shade the name]
  ? * com.azure                            2019-09..2026-05 4.11.0       |.....===============|
  ?   com.protocol180                      2022-06..2022-07 0.2.0        |...........=........|
redis.clients.jedis  [owned by `redis.clients`; 2 other group(s) shade the name]
  ? * redis.clients                        2021-03..2026-05 8.0.0-beta1  |........============|
  ?   io.github.stellhub                   2026-05..2026-05 6.0.0-stellhub-otel.1 |...................=|
  ?   today.bonfire.oss                    2024-12..2024-12 5.2.0        |................=...|
com.sun.xml.bind.core  [owned by `com.sun.xml.bind`; 4 other group(s) shade the name]
  ? * com.sun.xml.bind                     2020-04..2026-05 4.0.9        |.......=============|
  ?   org.takes                            2024-10..2024-10 1.24.6       |................=...|
  ?   com.jcabi                            2022-09..2024-06 1.9.1        |...........=====....|
  ?   com.exasol                           2024-06..2024-06 4.3.3        |...............=....|
  ?   one.gfw                              2023-03..2023-03 4.0.2.1      |............=.......|
com.sun.xml.bind.osgi  [owned by `com.sun.xml.bind`; 1 other group(s) shade the name]
  ? * com.sun.xml.bind                     2019-10..2026-05 4.0.9        |......==============|
  ?   com.liferay                          2025-05..2025-05 2.3.4.JAKARTA-LIFERAY-PATCHED-1 |.................=..|
org.glassfish.jaxb.core  [owned by `org.glassfish.jaxb`; 4 other group(s) shade the name]
  ? * org.glassfish.jaxb                   2020-04..2026-05 4.0.9        |.......=============|
  ?   org.biojava                          2025-01..2026-05 7.2.5        |................====|
  ?   com.intuit.quickbooks-online         2025-10..2025-11 6.6.2        |..................=.|
  ?   com.liferay                          2025-06..2025-06 4.0.5.LIFERAY-PATCHED-1 |.................=..|
  ?   de.fraunhofer.iem                    2025-03..2025-05 4.2.2        |................==..|
org.glassfish.jaxb.runtime  [owned by `org.glassfish.jaxb`; 10 other group(s) shade the name]
  ? * org.glassfish.jaxb                   2019-10..2026-05 4.0.9        |......==============|
  ?   io.github.vantiv                     2026-04..2026-04 12.49.0-jdk17 |...................=|
  ?   org.apache.iotdb                     2024-06..2026-04 2.0.8        |...............=====|
  ?   org.codeforamerica.platform          2026-01..2026-01 4.0.3        |..................=.|
  ?   org.xtce                             2025-04..2025-04 1.1.7        |.................=..|
  ?   org.soot-oss                         2024-10..2024-10 4.6.0        |................=...|
    + 5 more: com.bandwidth.sdk, org.jboss.windup.web, com.intuit.quickbooks-online, org.mustangproject, org.duracloud
org.glassfish.jaxb.xjc  [owned by `org.glassfish.jaxb`; 1 other group(s) shade the name]
  ? * org.glassfish.jaxb                   2019-10..2026-05 4.0.9        |......==============|
  ?   org.redundent                        2023-08..2023-10 1.9.1        |.............==.....|
software.amazon.awssdk.arns  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2019-10..2026-05 2.44.14      |.....===============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.identity.spi  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2023-10..2026-05 2.44.14      |..............======|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.cloudcontrol  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2021-09..2026-05 2.44.14      |.........===========|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.cloudtraildata  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2023-01..2026-05 2.44.14      |............========|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.docdb  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2019-01..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.glacier  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.m2  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2022-06..2026-05 2.44.14      |...........=========|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.marketplacereporting  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2024-10..2026-05 2.44.14      |................====|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.mwaa  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2020-11..2026-05 2.44.14      |........============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.networkmonitor  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2023-12..2026-05 2.44.14      |..............======|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.redshiftserverless  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2022-06..2026-05 2.44.14      |...........=========|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.testutils.service  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.crtcore  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2023-03..2026-05 2.44.14      |............========|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.appconfigdata  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2021-11..2026-05 2.44.14      |..........==========|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.codebuild  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.dlm  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.iottwinmaker  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2021-12..2026-05 2.44.14      |..........==========|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.pinpoint  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.rbin  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2021-11..2026-05 2.44.14      |..........==========|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.rekognition  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.resiliencehub  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2021-11..2026-05 2.44.14      |..........==========|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.route53domains  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.sagemaker  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.supplychain  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2024-01..2026-05 2.44.14      |..............======|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.taxsettings  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2024-06..2026-05 2.44.14      |...............=====|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.applicationcostprofiler  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2021-05..2026-05 2.44.14      |.........===========|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.cloudwatchlogs  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.datasync  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.dax  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.ec2instanceconnect  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2019-06..2026-05 2.44.14      |.....===============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.evs  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2025-06..2026-05 2.44.14      |.................===|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.greengrass  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.greengrassv2  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2020-12..2026-05 2.44.14      |........============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.iam  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.iotsitewise  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2020-04..2026-05 2.44.14      |.......=============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.iotwireless  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2020-12..2026-05 2.44.14      |........============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.kendra  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2019-12..2026-05 2.44.14      |......==============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.keyspacesstreams  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2025-06..2026-05 2.44.14      |.................===|
  ?   com.sonatype.central.testing.amazon  2025-08..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.launchwizard  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2023-11..2026-05 2.44.14      |..............======|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.marketplaceentitlement  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.mediaconvert  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.memorydb  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2021-08..2026-05 2.44.14      |.........===========|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.odb  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2025-07..2026-05 2.44.14      |.................===|
  ?   com.sonatype.central.testing.amazon  2025-08..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.route53resolver  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.servicequotas  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2019-06..2026-05 2.44.14      |.....===============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.snowball  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.sso  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2019-11..2026-05 2.44.14      |......==============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.timestreamquery  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2020-09..2026-05 2.44.14      |.......=============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.translate  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.checksums.spi  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2023-10..2026-05 2.44.14      |..............======|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.http.nio.netty  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.accessanalyzer  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2019-12..2026-05 2.44.14      |......==============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.amplify  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.appflow  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2020-08..2026-05 2.44.14      |.......=============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.applicationdiscovery  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.bedrockagent  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2023-11..2026-05 2.44.14      |..............======|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.cloudsearchdomain  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.connectparticipant  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2019-11..2026-05 2.44.14      |......==============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.finspacedata  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2021-05..2026-05 2.44.14      |.........===========|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.inspector  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.iotdeviceadvisor  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2020-12..2026-05 2.44.14      |........============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.kinesisvideomedia  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.lakeformation  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2019-08..2026-05 2.44.14      |.....===============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.machinelearning  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.macie2  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2020-05..2026-05 2.44.14      |.......=============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.mediapackagev2  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2023-05..2026-05 2.44.14      |.............=======|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.mediapackagevod  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2019-05..2026-05 2.44.14      |.....===============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.migrationhuborchestrator  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2022-09..2026-05 2.44.14      |...........=========|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.networkfirewall  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2020-11..2026-05 2.44.14      |........============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.paymentcryptography  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2023-06..2026-05 2.44.14      |.............=======|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.route53recoverycluster  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2021-07..2026-05 2.44.14      |.........===========|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.rum  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2021-11..2026-05 2.44.14      |..........==========|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.s3  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.s3tables  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2024-12..2026-05 2.44.14      |................====|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.sagemakermetrics  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2022-12..2026-05 2.44.14      |............========|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.savingsplans  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2019-11..2026-05 2.44.14      |......==============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.schemas  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2019-12..2026-05 2.44.14      |......==============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.serverlessapplicationrepository  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.ssooidc  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2019-11..2026-05 2.44.14      |......==============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.swf  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.vpclattice  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2023-03..2026-05 2.44.14      |.............=======|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.checksums  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2023-10..2026-05 2.44.14      |..............======|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.deadline  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2024-04..2026-05 2.44.14      |...............=====|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.entityresolution  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2023-07..2026-05 2.44.14      |.............=======|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.finspace  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2021-05..2026-05 2.44.14      |.........===========|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.invoicing  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2024-12..2026-05 2.44.14      |................====|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.lexruntimev2  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2021-01..2026-05 2.44.14      |........============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.location  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2020-12..2026-05 2.44.14      |........============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.migrationhub  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.panorama  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2021-10..2026-05 2.44.14      |..........==========|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.partnercentralselling  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2024-11..2026-05 2.44.14      |................====|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.servicediscovery  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.transcribe  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.trustedadvisor  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2023-11..2026-05 2.44.14      |..............======|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.wafv2  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2019-11..2026-05 2.44.14      |......==============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.thirdparty.jackson.core  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2021-07..2026-05 2.44.14      |.........===========|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.endpoints  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2022-10..2026-05 2.44.14      |............========|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.enhanced.dynamodb  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2020-02..2026-05 2.44.14      |......==============|
  ?   software.amazon.lambda               2022-02..2026-03 2.10.0       |..........==========|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.protocols.cbor  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.protocols.jsoncore  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2021-07..2026-05 2.44.14      |.........===========|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.acmpca  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.amplifyuibuilder  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2021-12..2026-05 2.44.14      |..........==========|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.autoscalingplans  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.clouddirectory  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.codeconnections  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2024-03..2026-05 2.44.14      |...............=====|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.dsql  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2024-12..2026-05 2.44.14      |................====|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.ecrpublic  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2020-12..2026-05 2.44.14      |........============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.ecs  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.efs  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.elasticloadbalancing  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.fis  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2021-03..2026-05 2.44.14      |........============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.identitystore  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2020-08..2026-05 2.44.14      |.......=============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.iotsecuretunneling  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2019-11..2026-05 2.44.14      |......==============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.migrationhubrefactorspaces  [owned by `software.amazon.awssdk`; 1 other group(s) shade the name]
  ? * software.amazon.awssdk               2021-11..2026-05 2.44.14      |..........==========|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
```

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

