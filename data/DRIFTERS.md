# Module ownership drifters

Generated 2026-06-12. A module *drifts* when more than one groupId publishes the name and its `owners.tsv` does not yet name every publisher (no `owners.tsv`, or one that leaves some publishing groupId neither `allowed` nor `blocked`). Resolving a drift means deciding each groupId via `SetOwners` (which writes `allowed`/`blocked`); a fully-named module drops off this list.

| Category | Modules |
|---|---:|
| republisher | 0 |
| migration | 0 |
| fork | 0 |
| unclassified | 1664 |
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

## unclassified (1664)

Multiple publishers that fit none of the shapes above (concurrent collisions, ambiguous handoffs). Proposal: keep the current owner, but review by hand.

| count | current owner -> proposed allowed |
|---:|---|
| 455 | `software.amazon.awssdk -> software.amazon.awssdk` |
| 98 | `io.ktor -> io.ktor` |
| 93 | `org.apache.cxf -> org.apache.cxf` |
| 79 | `ru.tinkoff.kora -> ru.tinkoff.kora` |
| 69 | `org.springframework.boot -> org.springframework.boot` |
| 65 | `org.lwjgl -> org.lwjgl` |
| 44 | `org.bytedeco -> org.bytedeco` |
| 38 | `org.neo4j -> org.neo4j` |
| 32 | `com.javax0.jamal -> com.javax0.jamal` |
| 27 | `org.codehaus.groovy -> org.codehaus.groovy` |
| 24 | `org.eclipse.platform -> org.eclipse.platform` |
| 22 | `org.springframework -> org.springframework` |
| 18 | `org.jetbrains.kotlinx -> org.jetbrains.kotlinx` |
| 17 | `org.jmonkeyengine -> org.jmonkeyengine` |
| 16 | `org.apache.lucene -> org.apache.lucene` |

_Showing the 200 most recently active of 1664. For the full list, emit the SetOwners file: `-Djenesis.crawler.drift.emit=unclassified`._

```
ch.qos.logback.classic  [multiple owners; `ch.qos.logback` is earliest and most recent]
  ? * ch.qos.logback                       2018-01..2026-06 1.5.34       |..==================|
  ?   com.daml                             2022-10..2026-05 3.6.0-snapshot.20260529.14710.0.va1ec8126 |............========|
  ?   io.mosip.biosdk                      2024-12..2026-05 1.3.1        |................====|
  ?   io.mosip.demosdk                     2025-03..2026-05 1.3.1        |................====|
  ?   de.fraunhofer.iosb.ilt               2023-01..2026-05 0.38         |............========|
  ?   org.apache.sling                     2025-05..2026-05 6.1.0        |.................===|
    + 87 more: dk.alexandra.fresco, org.jboss.pnc.gradle-manipulator, com.deltaproto, org.javastro.ivoa, org.commonjava.atlas, ch.exense.step, club.dawdler, org.eclipse.ecsp, org.apache.zookeeper, com.fenxi365, de.caluga, io.camunda, (+75 more)
ch.qos.logback.core  [multiple owners; `ch.qos.logback` is earliest and most recent]
  ? * ch.qos.logback                       2018-01..2026-06 1.5.34       |..==================|
  ?   com.effacy.jui                       2024-12..2026-05 0.3.5        |................====|
  ?   io.spicelabs                         2026-03..2026-05 0.15.5       |...................=|
  ?   com.limemojito.oss.aws               2026-03..2026-05 8.0.10       |...................=|
  ?   de.gematik.test                      2025-11..2026-05 4.2.7        |..................==|
  ?   com.deltaproto                       2026-04..2026-05 1.1.4        |...................=|
    + 25 more: org.ton.ton4j, com.expediagroup, io.camunda, org.chenile, club.dawdler, org.jetbrains.kotlinx, io.github.neodix42, com.yetanalytics, org.opendaylight.bgpcep, fun.adaptive, org.docshare, io.flux-capacitor, (+13 more)
com.fasterxml.jackson.jakarta.rs.base  [multiple owners; `com.fasterxml.jackson.jakarta.rs` is earliest and most recent]
  ? * com.fasterxml.jackson.jakarta.rs     2021-07..2026-06 2.22.0       |.........===========|
  ?   io.trino.gateway                     2025-02..2026-05 19           |................====|
  ?   io.streamthoughts                    2026-02..2026-03 0.37.3       |..................==|
  ?   com.docusign                         2024-06..2026-03 2.1.0        |...............=====|
  ?   org.graylog2                         2024-05..2025-07 6.2.5        |...............===..|
  ?   io.trino                             2025-01..2025-06 476          |................==..|
com.fasterxml.jackson.jakarta.rs.json  [multiple owners; `com.fasterxml.jackson.jakarta.rs` is earliest and most recent]
  ? * com.fasterxml.jackson.jakarta.rs     2021-07..2026-06 2.22.0       |.........===========|
  ?   org.apache.tika                      2023-12..2026-05 3.3.1        |..............======|
  ?   com.phonepe.sentinel-ai              2026-05..2026-05 1.1.2-SOLARIS-rc0 |...................=|
  ?   ch.exense.step.library               2023-08..2026-05 1.0.31       |.............=======|
  ?   ch.exense.step                       2022-10..2026-03 3.29.4       |............========|
  ?   org.eclipse.tractusx.edc             2023-06..2026-03 0.10.3       |.............=======|
    + 12 more: org.ow2.petals.samples.rest.edm, dev.getelements.elements, org.eclipse.edc.huawei, org.eclipse.edc.aws, org.eclipse.edc, io.nflow, com.brightsparklabs, io.trino.gateway, com.snehasishroy, com.smoketurner.dropwizard, org.kiwiproject, org.dhatim
com.fasterxml.jackson.jakarta.rs.xml  [multiple owners; `com.fasterxml.jackson.jakarta.rs` is earliest and most recent]
  ? * com.fasterxml.jackson.jakarta.rs     2021-07..2026-06 2.22.0       |.........===========|
  ?   com.graphhopper                      2025-10..2025-10 11.0         |..................=.|
com.fasterxml.jackson.jaxrs.base  [multiple owners; `com.fasterxml.jackson.jaxrs` is earliest and most recent]
  ? * com.fasterxml.jackson.jaxrs          2019-07..2026-06 2.22.0       |.....===============|
  ?   org.graylog2                         2022-11..2024-10 5.2.12       |............=====...|
  ?   org.jboss.pnc                        2021-11..2021-11 1.0.0        |..........=.........|
  ?   org.jboss.pnc.cleaner                2021-07..2021-07 2.1.0        |.........=..........|
  ?   org.jboss.pnc.kafkastore             2021-02..2021-06 1.0.5        |........==..........|
  ?   org.projectnessie                    2021-05..2021-06 0.7.0        |.........=..........|
    + 2 more: org.kie.kogito, com.guicedee.services
com.fasterxml.jackson.jaxrs.cbor  [multiple owners; `com.fasterxml.jackson.jaxrs` is earliest and most recent]
  ? * com.fasterxml.jackson.jaxrs          2017-10..2026-06 2.22.0       |.===================|
  ?   com.palantir.atlasdb                 2022-03..2022-09 0.702.0-rc1  |..........==........|
com.fasterxml.jackson.jaxrs.xml  [multiple owners; `com.fasterxml.jackson.jaxrs` is earliest and most recent]
  ? * com.fasterxml.jackson.jaxrs          2017-10..2026-06 2.22.0       |.===================|
  ?   com.graphhopper                      2023-10..2025-01 10.2         |..............===...|
com.fasterxml.jackson.datatype.jaxrs  [multiple owners; `com.fasterxml.jackson.datatype` is earliest and most recent]
  ? * com.fasterxml.jackson.datatype       2017-10..2026-06 2.22.0       |.===================|
  ?   com.guicedee.services                2020-11..2020-11 1.1.0.2-jre14 |........=...........|
com.fasterxml.jackson.jaxrs.yaml  [multiple owners; `com.fasterxml.jackson.jaxrs` is earliest and most recent]
  ? * com.fasterxml.jackson.jaxrs          2017-10..2026-06 2.22.0       |.===================|
  ?   io.apiwiz.astrum                     2022-01..2022-01 0.1          |..........=.........|
com.fasterxml.jackson.kotlin  [multiple owners; `com.fasterxml.jackson.module` is earliest and most recent]
  ? * com.fasterxml.jackson.module         2021-01..2026-06 2.22.0       |........============|
  ?   io.inji.certify                      2026-03..2026-03 0.6.0        |...................=|
  ?   io.github.aaiezza                    2024-08..2024-08 1.0.3        |...............=....|
  ?   com.atlan                            2023-11..2024-05 1.11.5       |..............==....|
  ?   com.expediagroup.openworld.sdk       2023-02..2023-09 2.0.0        |............==......|
  ?   it.unibo.tuprolog.argumentation      2021-12..2022-06 0.6.7        |..........==........|
    + 1 more: com.googlecode.blaisemath
com.fasterxml.jackson.module.paramnames  [multiple owners; `com.fasterxml.jackson.module` is earliest and most recent]
  ? * com.fasterxml.jackson.module         2017-10..2026-06 2.22.0       |.===================|
  ?   com.infobip                          2026-03..2026-03 3.0.1        |...................=|
  ?   io.kestra                            2025-07..2025-08 0.23.12      |.................=..|
  ?   com.araksis                          2025-03..2025-03 0.0.1a       |.................=..|
  ?   com.araksis.sjd                      2025-03..2025-03 0.0.1        |.................=..|
  ?   io.github.codgen                     2024-04..2024-11 1.1.17       |...............==...|
    + 1 more: io.micronaut.example
com.fasterxml.jackson.datatype.jdk8  [multiple owners; `com.fasterxml.jackson.datatype` is earliest and most recent]
  ? * com.fasterxml.jackson.datatype       2017-10..2026-06 2.22.0       |.===================|
  ?   org.ic4j                             2026-03..2026-05 0.8.2        |...................=|
  ?   io.github.unmeshjoshi                2025-10..2026-05 0.1.0-alpha.27 |..................==|
  ?   com.networknt                        2022-08..2026-05 2.3.4        |...........=========|
  ?   org.apache.grails                    2025-06..2026-04 7.1.0        |.................===|
  ?   io.github.tansuasici                 2026-02..2026-02 0.0.2        |..................=.|
    + 30 more: ai.onehouse, com.atlan, io.github.demiourgoi, io.kestra.plugin, com.tencent.cloud, hu.webarticum.miniconnect, io.openlineage, de.m3y.parquet, io.edurt.datacap, org.apache.parquet, io.opentelemetry.javaagent, ai.superstream, (+18 more)
com.fasterxml.jackson.datatype.jsr310  [multiple owners; `com.fasterxml.jackson.datatype` is earliest and most recent]
  ? * com.fasterxml.jackson.datatype       2017-10..2026-06 2.22.0       |.===================|
  ?   org.opencds.cqf.cql.ls               2026-03..2026-05 4.7.0        |...................=|
  ?   cab.ml                               2026-05..2026-05 0.1.0-RC     |...................=|
  ?   org.octopusden.octopus.automation.teamcity 2024-08..2026-05 1.0.36       |...............=====|
  ?   org.apache.hudi                      2023-09..2026-05 1.2.0        |..............======|
  ?   com.linkedin.iceberg                 2025-09..2026-05 1.2.0.17     |.................===|
    + 79 more: io.openlineage, org.apache.gravitino, org.openapitools, org.byteveda.agenteval, org.codelibs.fess, org.apache.doris, io.spring.gradle, io.camunda.filestorage, org.octopusden.octopus.automation.artifactory, org.openapitools.openapidiff, com.expediagroup, de.jvstvshd.necrify, (+67 more)
com.fasterxml.jackson.datatype.hibernate6  [multiple owners; `com.fasterxml.jackson.datatype` is earliest and most recent]
  ? * com.fasterxml.jackson.datatype       2023-03..2026-06 2.22.0       |............========|
  ?   io.bitdive                           2025-03..2026-04 1.3.22       |.................===|
  ?   com.bowerzlabs                       2026-04..2026-04 0.1.0-beta   |...................=|
com.fasterxml.jackson.datatype.joda  [multiple owners; `com.fasterxml.jackson.datatype` is earliest and most recent]
  ? * com.fasterxml.jackson.datatype       2017-10..2026-06 2.22.0       |.===================|
  ?   io.kestra.plugin                     2024-06..2024-08 0.18.1       |...............=....|
  ?   org.apache.beam                      2022-05..2023-05 2.48.0       |...........===......|
  ?   com.seeq                             2021-12..2022-08 55.4.9-v202208021422 |..........==........|
com.fasterxml.jackson.datatype.jsonp  [multiple owners; `com.fasterxml.jackson.datatype` is earliest and most recent]
  ? * com.fasterxml.jackson.datatype       2021-03..2026-06 2.22.0       |........============|
  ?   com.arangodb                         2025-01..2026-04 7.26.0       |................====|
com.fasterxml.jackson.datatype.jsonorg  [multiple owners; `com.fasterxml.jackson.datatype` is earliest and most recent]
  ? * com.fasterxml.jackson.datatype       2017-10..2026-06 2.22.0       |.===================|
  ?   io.apicurio                          2021-12..2022-04 2.2.3.Final  |..........==........|
com.fasterxml.jackson.datatype.guava  [multiple owners; `com.fasterxml.jackson.datatype` is earliest and most recent]
  ? * com.fasterxml.jackson.datatype       2017-10..2026-06 2.22.0       |.===================|
  ?   com.palantir.atlasdb                 2023-08..2024-10 0.1172.0     |.............====...|
  ?   io.kestra.plugin                     2024-03..2024-04 0.16.1       |..............==....|
  ?   org.openapitools                     2022-04..2022-09 6.1.0        |...........=........|
  ?   io.github.marquezproject             2020-08..2021-08 0.17.0       |.......===..........|
jackson.datatype.pcollections  [multiple owners; `com.fasterxml.jackson.datatype` is earliest and most recent]
  ? * com.fasterxml.jackson.datatype       2019-07..2026-06 2.22.0       |.....===============|
  ?   tools.jackson.datatype               2025-03..2026-05 3.1.3        |................====|
com.fasterxml.jackson.module.jsonSchema  [multiple owners; `com.fasterxml.jackson.module` is earliest and most recent]
  ? * com.fasterxml.jackson.module         2019-07..2026-06 2.22.0       |.....===============|
  ?   org.openidentityplatform.openam      2022-12..2025-04 15.1.6       |............======..|
  ?   cc.unitmesh                          2023-10..2024-07 1.0.0        |..............==....|
  ?   io.pravega                           2020-09..2021-10 0.3.0        |.......====.........|
com.fasterxml.jackson.dataformat.xml  [multiple owners; `com.fasterxml.jackson.dataformat` is earliest and most recent]
  ? * com.fasterxml.jackson.dataformat     2017-10..2026-06 2.22.0       |.===================|
  ?   org.pitest                           2024-05..2026-05 1.25.3       |...............=====|
  ?   ai.onehouse                          2026-01..2026-05 0.29.0       |..................==|
  ?   com.pawtograder.org.pitest           2026-05..2026-05 2.0.0        |...................=|
  ?   com.aliyun                           2025-09..2026-04 3.3.5-2.0.26-alpha-shade |..................==|
  ?   org.jetbrains.dokka                  2020-12..2026-03 2.2.0        |........============|
    + 25 more: org.testingisdocumenting.znai, com.github.nagyesta.file-barj, io.kestra.plugin, it.unibo.alchemist, com.spectralogic.ds3, io.github.wangminan, com.marklogic, com.feylesoft, io.kestra.storage, com.atlan, org.projectnessie.nessie-integrations, com.sonatype.clm, (+13 more)
com.fasterxml.jackson.dataformat.cbor  [multiple owners; `com.fasterxml.jackson.dataformat` is earliest and most recent]
  ? * com.fasterxml.jackson.dataformat     2017-10..2026-06 2.22.0       |.===================|
  ?   net.snowflake                        2023-06..2026-04 4.4.3        |.............=======|
  ?   org.eclipse.ditto                    2022-03..2026-03 1.1.0        |..........==========|
  ?   org.apache.dolphinscheduler          2025-03..2026-03 3.4.1        |.................==.|
  ?   com.amazonaws                        2020-09..2025-12 1.12.797     |.......============.|
  ?   org.apache.jackrabbit                2023-01..2025-09 1.22.23      |............=======.|
    + 17 more: software.amazon.neptune, org.elasticsearch.plugin, com.hazelcast.jet, org.duracloud, org.alluxio, org.apache.beam, com.liferay.portal, io.kestra.plugin, org.apache.flink, edu.internet2.middleware.grouper, org.apache.pinot, software.amazon.awssdk, (+5 more)
com.fasterxml.jackson.dataformat.protobuf  [multiple owners; `com.fasterxml.jackson.dataformat` is earliest and most recent]
  ? * com.fasterxml.jackson.dataformat     2017-10..2026-06 2.22.0       |.===================|
  ?   org.projectnessie.cel                2023-11..2023-11 0.4.2        |..............=.....|
  ?   org.projectnessie                    2021-10..2022-01 0.18.0       |..........=.........|
com.fasterxml.jackson.dataformat.smile  [multiple owners; `com.fasterxml.jackson.dataformat` is earliest and most recent]
  ? * com.fasterxml.jackson.dataformat     2017-10..2026-06 2.22.0       |.===================|
  ?   org.opensearch.migrations.snapshots  2026-04..2026-05 0.3.2.1      |...................=|
  ?   com.netflix.spectator                2021-10..2025-08 1.8.17       |..........========..|
  ?   io.acryl                             2024-01..2025-06 1.1.0.3rc1   |..............====..|
  ?   org.apache.hudi                      2024-07..2024-07 1.0.0-beta2  |...............=....|
  ?   org.pipservices                      2022-06..2023-11 0.0.1        |...........====.....|
    + 2 more: com.hazelcast.jet, io.trino
com.fasterxml.jackson.dataformat.csv  [multiple owners; `com.fasterxml.jackson.dataformat` is earliest and most recent]
  ? * com.fasterxml.jackson.dataformat     2017-10..2026-06 2.22.0       |.===================|
  ?   com.opendxl                          2025-05..2025-05 2.7.0        |.................=..|
  ?   com.hazelcast.jet                    2022-06..2024-07 5.3.8        |...........=====....|
  ?   org.apache.beam                      2021-01..2024-04 2.56.0       |........========....|
  ?   com.microsoft.azure                  2024-02..2024-03 1.0.4        |..............=.....|
  ?   com.linkedin.feathr                  2022-11..2023-06 1.0.5-rc5    |............==......|
    + 2 more: com.marklogic, com.hortonworks.registries
com.fasterxml.jackson.dataformat.javaprop  [multiple owners; `com.fasterxml.jackson.dataformat` is earliest and most recent]
  ? * com.fasterxml.jackson.dataformat     2017-10..2026-06 2.22.0       |.===================|
  ?   org.apache.seatunnel                 2024-02..2026-02 2.3.13       |..............=====.|
com.fasterxml.jackson.dataformat.toml  [multiple owners; `com.fasterxml.jackson.dataformat` is earliest and most recent]
  ? * com.fasterxml.jackson.dataformat     2021-04..2026-06 2.22.0       |.........===========|
  ?   org.monarchinitiative.vitt           2026-01..2026-01 0.1.0        |..................=.|
  ?   com.github.jobservice                2024-02..2024-02 7.0.0-334    |..............=.....|
  ?   com.github.cafapi.util.flywayinstaller 2024-02..2024-02 2.0.0-184    |..............=.....|
  ?   com.cefoler.configuration            2022-04..2022-05 1.2.4        |...........=........|
  ?   dev.madetobuild.typedconfig          2022-03..2022-03 0.2          |..........=.........|
    + 1 more: com.github.nanodeath
com.fasterxml.jackson.dataformat.yaml  [multiple owners; `com.fasterxml.jackson.dataformat` is earliest and most recent]
  ? * com.fasterxml.jackson.dataformat     2017-10..2026-06 2.22.0       |.===================|
  ?   org.apiaddicts.apitools.dosonarapi   2025-01..2026-05 1.4.1-beta-3 |................====|
  ?   com.helpchoice                       2026-04..2026-05 1.1.0        |...................=|
  ?   io.telicent.jena                     2025-06..2026-05 3.0.3        |.................===|
  ?   com.sagframe                         2022-01..2026-05 5.2.10       |..........==========|
  ?   io.fabrikt                           2026-03..2026-05 27.1.0       |..................==|
    + 59 more: net.corda, dev.skyramp, org.testcontainers, com.cjbooms, org.apache.dolphinscheduler, io.github.pavan2504, io.github.rohitect, org.apache.plc4x, org.wildfly.prospero, com.free-now.sauron.plugins, io.substrait, de.fraunhofer.aisec, (+47 more)
com.fasterxml.jackson.module.afterburner  [multiple owners; `com.fasterxml.jackson.module` is earliest and most recent]
  ? * com.fasterxml.jackson.module         2017-10..2026-06 2.22.0       |.===================|
  ?   org.apache.hudi                      2024-06..2026-05 1.2.0        |...............=====|
  ?   org.kill-bill.billing.plugin.java    2021-01..2026-04 1.0.3        |........============|
  ?   io.orkes.conductor                   2024-12..2026-02 5.0.1        |................===.|
  ?   org.conductoross                     2024-12..2026-02 5.0.1        |................===.|
  ?   org.kill-bill.billing                2020-09..2022-10 0.40.13      |.......======.......|
com.fasterxml.jackson.module.jakarta.xmlbind  [multiple owners; `com.fasterxml.jackson.module` is earliest and most recent]
  ? * com.fasterxml.jackson.module         2021-07..2026-06 2.22.0       |.........===========|
  ?   com.datadoghq                        2022-08..2026-05 2.55.0       |...........=========|
  ?   com.salesforce.multicloudj           2025-07..2026-03 0.3.0        |.................===|
  ?   org.glassfish.main.jackson.module    2022-11..2026-03 8.0.1        |............========|
  ?   de.ipk-gatersleben                   2025-08..2025-08 3.0.5        |.................=..|
  ?   com.icegreen                         2023-03..2025-08 2.1.5        |............======..|
    + 12 more: com.instaclustr, ch.exense.step, video.bug, com.sap.scimono, com.docusign, it.vige.cities, com.fujitsu.launcher, io.streamthoughts, ch.exense.step.library, io.bdeploy, org.eclipse.edc, org.eclipse.dataspaceconnector
com.fasterxml.jackson.module.jaxb  [multiple owners; `com.fasterxml.jackson.module` is earliest and most recent]
  ? * com.fasterxml.jackson.module         2017-10..2026-06 2.22.0       |.===================|
  ?   com.facebook.presto.spark            2026-05..2026-05 3.4.1-2      |...................=|
  ?   com.datastax.oss                     2021-06..2026-05 6.0.10       |.........===========|
  ?   com.rovio.ingest                     2024-10..2026-04 1.0.8_spark_3.4.1 |................====|
  ?   org.apache.dolphinscheduler          2025-03..2026-03 3.4.1        |.................==.|
  ?   org.apache.seatunnel                 2022-09..2026-02 2.3.13       |...........========.|
    + 90 more: org.apache.pulsar, com.ascentstream.pulsar, io.github.solven-eu.cleanthat, com.oceanbase, io.github.dodogeny, com.solacecoe.connectors, com.seeq, io.streamnative.connectors, org.apache.phoenix, com.kenstott.components, io.cdap.cdap, com.xenoamess.necrodialysis.pulsar-flink, (+78 more)
com.fasterxml.jackson.module.paranamer  [multiple owners; `com.fasterxml.jackson.module` is earliest and most recent]
  ? * com.fasterxml.jackson.module         2017-10..2026-06 2.22.0       |.===================|
  ?   com.criteo                           2022-07..2022-07 1.1-spark3   |...........=........|
  ?   org.openpolicyagent.kafka            2021-11..2022-01 1.4.0        |..........=.........|
  ?   com.bisnode.kafka.authorization      2021-03..2021-10 1.2.0        |........===.........|
  ?   org.apache.beam                      2020-04..2021-02 2.28.0       |......===...........|
  ?   ai.whylabs                           2021-02..2021-02 0.1.2-b0     |........=...........|
com.fasterxml.jackson.module.guice  [multiple owners; `com.fasterxml.jackson.module` is earliest and most recent]
  ? * com.fasterxml.jackson.module         2017-10..2026-06 2.22.0       |.===================|
  ?   com.jwebmp.jpms.jackson.module       2019-04..2019-08 0.68.0.1     |....==..............|
  ?   com.jwebmp.jackson.module            2019-02..2019-04 0.66.0.1     |....=...............|
com.fasterxml.jackson.jr.ob  [multiple owners; `com.fasterxml.jackson.jr` is earliest and most recent]
  ? * com.fasterxml.jackson.jr             2017-10..2026-06 2.22.0       |.===================|
  ?   it.mulders                           2025-11..2026-05 0.10.0       |..................==|
  ?   io.akeyless                          2026-03..2026-04 1.1.0        |...................=|
com.fasterxml.jackson.databind  [multiple owners; `com.fasterxml.jackson.core` is earliest and most recent]
  ? * com.fasterxml.jackson.core           2017-09..2026-06 2.22.0       |.===================|
  ?   com.airbnb.viaduct                   2026-01..2026-05 1.1.0        |..................==|
  ?   io.github.dhruvrawatdev              2026-05..2026-05 2.2.0        |...................=|
  ?   com.sparkutils                       2025-04..2026-05 0.1.0-RC2    |.................===|
  ?   org.jboss.galleon                    2024-08..2026-05 7.0.8.Final  |...............=====|
  ?   com.perimeterx                       2020-03..2026-05 6.17.0       |......==============|
    + 439 more: io.github.randomcodespace.sonarpredict, com.streamlake, net.thisptr, org.apache.hudi, io.hyperfoil.tools, com.amazonaws, org.apache.seatunnel, org.jetbrains.intellij.plugins, org.apache.tika, org.octopusden.octopus.automation.release-management, se.liu.research.hefquin, io.github.1030907690, (+427 more)
com.github.javaparser.core  [multiple owners; `com.github.javaparser` is earliest and most recent]
  ? * com.github.javaparser                2017-12..2026-05 3.28.2       |..==================|
  ?   org.checkerframework                 2023-09..2026-05 3.28.1       |.............=======|
  ?   org.mvel.javaparser                  2026-02..2026-02 3.25.5-mvel3-1 |..................=.|
  ?   io.joern                             2022-06..2022-06 3.24.3-SL3   |...........=........|
com.github.javaparser.core.serialization  [multiple owners; `com.github.javaparser` is earliest and most recent]
  ? * com.github.javaparser                2018-11..2026-05 3.28.2       |....================|
  ?   org.mvel.javaparser                  2026-02..2026-02 3.25.5-mvel3-1 |..................=.|
  ?   io.joern                             2022-06..2022-06 3.24.3-SL3   |...........=........|
com.github.javaparser.symbolsolver.core  [multiple owners; `com.github.javaparser` is earliest and most recent]
  ? * com.github.javaparser                2018-01..2026-05 3.28.2       |..==================|
  ?   org.mvel.javaparser                  2026-02..2026-02 3.25.5-mvel3-1 |..................=.|
  ?   io.joern                             2022-06..2022-06 3.24.3-SL3   |...........=........|
com.nimbusds.jose.jwt  [multiple owners; `com.nimbusds` is earliest and most recent]
  ? * com.nimbusds                         2020-08..2026-05 10.9.1       |.......=============|
  ?   com.vaadin                           2025-07..2026-05 3.0.2        |.................===|
  ?   fish.payara.security.connectors      2024-05..2026-04 2.9.0        |...............=====|
  ?   org.bonitasoft.connectors            2026-04..2026-04 1.0.0-beta.1 |...................=|
  ?   org.apache.hadoop                    2026-03..2026-03 3.5.0        |...................=|
  ?   com.waveinformatica.skysso           2025-09..2025-09 1.3.0        |..................=.|
    + 5 more: io.github.swiyu-admin-ch, io.okdp, org.project-kessel, com.liferay, com.thetransactioncompany
com.fasterxml.jackson.annotation  [multiple owners; `com.fasterxml.jackson.core` is earliest and most recent]
  ? * com.fasterxml.jackson.core           2017-09..2026-05 2.22         |.===================|
  ?   io.debezium                          2022-04..2026-05 3.6.0.Beta1  |...........=========|
  ?   com.wingify.sdk                      2026-05..2026-05 1.50.0       |...................=|
  ?   com.vwo.sdk                          2020-06..2026-05 1.50.0       |.......=============|
  ?   org.chenile                          2025-04..2026-05 2.1.22       |.................===|
  ?   nl.multicode.elevenproof             2026-05..2026-05 1.1.0        |...................=|
    + 459 more: com.yahoo.vespa, com.intuit.quickbooks-online, com.chartiq.finsemble, com.messagebird, com.ascentstream.pulsar, ch.admin.swiyu, ai.chronon, io.github.spiceforgeio, io.github.astonbitecode, org.zowe.client.java.sdk, io.github.jiajunbernoulli, ai.tock, (+447 more)
com.renomad.minum  [multiple owners; `com.renomad` is earliest and most recent]
  ? * com.renomad                          2024-11..2026-05 10.0.3       |................====|
  ?   io.github.tanin47                    2025-10..2025-10 1.1.1        |..................=.|
io.pebbletemplates  [multiple owners; `io.pebbletemplates` is earliest and most recent]
  ? * io.pebbletemplates                   2019-01..2026-05 4.1.2        |....================|
  ?   io.vertx                             2021-05..2021-05 4.1.0.CR1    |.........=..........|
org.flywaydb.core  [multiple owners; `org.flywaydb` is earliest and most recent]
  ? * org.flywaydb                         2017-12..2026-05 12.7.0       |..==================|
  ?   io.github.coolbeevip                 2023-03..2026-01 9.15.2.5     |............=======.|
  ?   io.gitee.gbase8s                     2025-01..2025-01 6.5.7        |................=...|
  ?   org.flywaydb.enterprise              2020-04..2022-07 9.0.0        |.......=====........|
  ?   io.github.linceln                    2021-07..2021-08 5.0.8        |.........=..........|
  ?   org.flywaydb.pro                     2020-04..2020-09 6.5.7        |.......=............|
com.graphqljava  [multiple owners; `com.graphql-java` is earliest and most recent]
  ? * com.graphql-java                     2020-11..2026-05 0.0.0-2026-05-29T07-49-37-79b227e |........============|
  ?   com.liferay                          2025-05..2025-05 19.11.JAKARTA-LIFERAY-PATCHED-1 |.................=..|
  ?   io.github.my-workforce               2022-07..2023-07 19.6         |...........===......|
org.atmosphere  [multiple owners; `org.atmosphere` is earliest and most recent]
  ? * org.atmosphere                       2026-02..2026-05 4.0.49       |..................==|
  ?   org.atmosphere.samples               2026-03..2026-03 4.0.19       |..................==|
org.conscrypt  [multiple owners; `org.conscrypt` is earliest and most recent]
  ? * org.conscrypt                        2018-11..2026-05 2.6-alpha2   |....================|
  ?   com.infinilabs                       2026-02..2026-02 1.0.13       |..................=.|
  ?   net.bruestel                         2026-01..2026-01 2.6.0-20260113 |..................=.|
  ?   net.tongsuo                          2023-01..2023-01 1.0.0        |............=.......|
  ?   info.guardianproject.conscrypt       2021-11..2022-03 2.6.alpha1647601986.job2220801545 |..........=.........|
javafx.base  [multiple owners; `org.openjfx` is earliest and most recent]
  ? * org.openjfx                          2018-07..2026-05 27-ea+18     |...=================|
  ?   org.glavo.hmcl.openjfx               2022-08..2024-01 14.0.2.1-freebsd |...........====.....|
  ?   com.googlecode.blaisemath            2022-08..2023-02 0.5.4        |...........==.......|
  ?   org.jfxcore                          2020-12..2022-07 18-ea+1      |........====........|
  ?   it.unibo.tuprolog.argumentation      2021-01..2021-10 0.5.1        |........===.........|
  ?   it.unibo.tuprolog                    2020-10..2021-05 0.17.4       |........==..........|
    + 3 more: com.github.nkb03, com.vwo.sdk, xyz.gianlu.librespot
javafx.controls  [multiple owners; `org.openjfx` is earliest and most recent]
  ? * org.openjfx                          2018-07..2026-05 27-ea+18     |...=================|
  ?   pro.verron.office-stamper            2026-03..2026-03 3.2          |..................=.|
  ?   com.sta-soft                         2025-09..2025-09 1.1          |.................=..|
  ?   org.glavo.hmcl.openjfx               2022-08..2024-01 14.0.2.1-freebsd |...........====.....|
  ?   org.jfxcore                          2020-12..2022-07 18-ea+1      |........====........|
  ?   org.jetbrains.lets-plot              2021-10..2021-10 2.2.0-rc2    |..........=.........|
    + 1 more: io.github.martinheywang
javafx.fxml  [multiple owners; `org.openjfx` is earliest and most recent]
  ? * org.openjfx                          2018-07..2026-05 27-ea+18     |...=================|
  ?   de.fraunhofer.iosb.ilt               2022-03..2025-05 0.37         |..........========..|
  ?   org.glavo.hmcl.openjfx               2022-08..2023-08 17.0.8-loongarch64 |...........===......|
  ?   org.jfxcore                          2020-12..2022-07 18-ea+1      |........====........|
  ?   de.ipk-gatersleben                   2020-07..2020-07 3.0.2        |.......=............|
javafx.graphics  [multiple owners; `org.openjfx` is earliest and most recent]
  ? * org.openjfx                          2018-07..2026-05 27-ea+18     |...=================|
  ?   io.github.makbn                      2025-10..2025-10 2.0.0        |..................=.|
  ?   de.wenzlaff.twbibel                  2022-05..2024-12 0.1.1        |...........======...|
  ?   de.pirckheimer-gymnasium             2024-08..2024-08 3.1.0        |...............=....|
  ?   org.glavo.hmcl.openjfx               2022-08..2024-01 14.0.2.1-freebsd |...........====.....|
  ?   org.jfxcore                          2020-12..2022-07 18-ea+1      |........====........|
    + 1 more: com.robotaccomplice
javafx.graphicsEmpty  [multiple owners; `org.openjfx` is earliest and most recent]
  ? * org.openjfx                          2018-08..2026-05 27-ea+18     |...=================|
  ?   org.glavo.hmcl.openjfx               2022-08..2023-08 17.0.8-loongarch64 |...........===......|
  ?   org.jfxcore                          2020-12..2022-07 18-ea+1      |........====........|
javafx.media  [multiple owners; `org.openjfx` is earliest and most recent]
  ? * org.openjfx                          2018-07..2026-05 27-ea+18     |...=================|
  ?   net.kurobako                         2026-03..2026-03 0.8.0        |...................=|
  ?   org.glavo.hmcl.openjfx               2022-08..2023-08 17.0.8-loongarch64 |...........===......|
  ?   org.jfxcore                          2020-12..2022-07 18-ea+1      |........====........|
javafx.swing  [multiple owners; `org.openjfx` is earliest and most recent]
  ? * org.openjfx                          2018-07..2026-05 27-ea+18     |...=================|
  ?   org.glavo.hmcl.openjfx               2022-08..2023-08 17.0.8-loongarch64 |...........===......|
  ?   org.jfxcore                          2022-06..2022-07 18-ea+1      |...........=........|
  ?   de.ipk-gatersleben                   2021-05..2021-05 3.0.3        |.........=..........|
javafx.web  [multiple owners; `org.openjfx` is earliest and most recent]
  ? * org.openjfx                          2018-07..2026-05 27-ea+18     |...=================|
  ?   org.glavo.hmcl.openjfx               2022-08..2023-08 17.0.8-loongarch64 |...........===......|
  ?   org.jfxcore                          2020-12..2022-07 18-ea+1      |........====........|
  ?   de.ipk-gatersleben                   2019-04..2021-05 3.0.4        |....======..........|
javafx.webEmpty  [multiple owners; `org.openjfx` is earliest and most recent]
  ? * org.openjfx                          2018-08..2026-05 27-ea+18     |...=================|
  ?   org.glavo.hmcl.openjfx               2022-08..2023-08 17.0.8-loongarch64 |...........===......|
  ?   org.jfxcore                          2020-12..2022-07 18-ea+1      |........====........|
javafx.baseEmpty  [multiple owners; `org.openjfx` is earliest and most recent]
  ? * org.openjfx                          2018-08..2026-05 27-ea+18     |...=================|
  ?   org.glavo.hmcl.openjfx               2022-08..2023-08 17.0.8-loongarch64 |...........===......|
  ?   org.jfxcore                          2022-06..2022-07 18-ea+1      |...........=........|
javafx.controlsEmpty  [multiple owners; `org.openjfx` is earliest and most recent]
  ? * org.openjfx                          2018-08..2026-05 27-ea+18     |...=================|
  ?   org.glavo.hmcl.openjfx               2022-08..2023-08 17.0.8-loongarch64 |...........===......|
  ?   org.jfxcore                          2022-06..2022-07 18-ea+1      |...........=........|
javafx.fxmlEmpty  [multiple owners; `org.openjfx` is earliest and most recent]
  ? * org.openjfx                          2018-08..2026-05 27-ea+18     |...=================|
  ?   org.glavo.hmcl.openjfx               2022-08..2023-08 17.0.8-loongarch64 |...........===......|
  ?   org.jfxcore                          2022-06..2022-07 18-ea+1      |...........=........|
javafx.mediaEmpty  [multiple owners; `org.openjfx` is earliest and most recent]
  ? * org.openjfx                          2018-08..2026-05 27-ea+18     |...=================|
  ?   org.glavo.hmcl.openjfx               2022-08..2023-08 17.0.8-loongarch64 |...........===......|
  ?   org.jfxcore                          2020-12..2022-07 18-ea+1      |........====........|
javafx.swingEmpty  [multiple owners; `org.openjfx` is earliest and most recent]
  ? * org.openjfx                          2018-08..2026-05 27-ea+18     |...=================|
  ?   org.glavo.hmcl.openjfx               2022-08..2023-08 17.0.8-loongarch64 |...........===......|
  ?   org.jfxcore                          2022-06..2022-07 18-ea+1      |...........=........|
org.neo4j.io  [multiple owners; `org.neo4j` is earliest and most recent]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.random.values  [multiple owners; `org.neo4j` is earliest and most recent]
  ? * org.neo4j                            2018-05..2026-05 2026.05.0    |...=================|
  ?   org.graphfoundation.ongdb            2020-04..2020-12 3.6.2        |.......==...........|
org.neo4j.collection  [multiple owners; `org.neo4j` is earliest and most recent]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.common  [multiple owners; `org.neo4j` is earliest and most recent]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.consistency  [multiple owners; `org.neo4j` is earliest and most recent]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.cypher.internal.runtime.util  [multiple owners; `org.neo4j` is earliest and most recent]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.kernel  [multiple owners; `org.neo4j` is earliest and most recent]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.tooling.procedure  [multiple owners; `org.neo4j` is earliest and most recent]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.unsafe  [multiple owners; `org.neo4j` is earliest and most recent]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.bolt  [multiple owners; `org.neo4j` is earliest and most recent]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.collection.concurrent  [multiple owners; `org.neo4j` is earliest and most recent]
  ? * org.neo4j                            2018-05..2026-05 2026.05.0    |...=================|
  ?   org.graphfoundation.ongdb            2020-04..2020-12 3.6.2        |.......==...........|
org.neo4j.community  [multiple owners; `org.neo4j` is earliest and most recent]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.cypher  [multiple owners; `org.neo4j` is earliest and most recent]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.harness  [multiple owners; `org.neo4j.test` is earliest and most recent]
  ? * org.neo4j.test                       2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb.test       2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.index.lucene  [multiple owners; `org.neo4j` is earliest and most recent]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.internal.diagnostics  [multiple owners; `org.neo4j` is earliest and most recent]
  ? * org.neo4j                            2018-08..2026-05 2026.05.0    |...=================|
  ?   org.graphfoundation.ongdb            2020-04..2020-12 3.6.2        |.......==...........|
org.neo4j.server  [multiple owners; `org.neo4j.app` is earliest and most recent]
  ? * org.neo4j.app                        2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb.app        2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.values  [multiple owners; `org.neo4j` is earliest and most recent]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.commandline  [multiple owners; `org.neo4j` is earliest and most recent]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.dbms  [multiple owners; `org.neo4j` is earliest and most recent]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.graphdb  [multiple owners; `org.neo4j` is earliest and most recent]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.index  [multiple owners; `org.neo4j` is earliest and most recent]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.logging  [multiple owners; `org.neo4j` is earliest and most recent]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.shell  [multiple owners; `org.neo4j` is earliest and most recent]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2021-03..2025-06 1.0.6        |........==========..|
org.neo4j.ssl  [multiple owners; `org.neo4j` is earliest and most recent]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.codegen  [multiple owners; `org.neo4j` is earliest and most recent]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.community.bolt.it  [multiple owners; `org.neo4j.community` is earliest and most recent]
  ? * org.neo4j.community                  2019-12..2026-05 2026.05.0    |......==============|
  ?   org.neo4j                            2021-01..2026-02 4.4.48       |........===========.|
org.neo4j.csv  [multiple owners; `org.neo4j` is earliest and most recent]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.cypher.internal.runtime.interpreted  [multiple owners; `org.neo4j` is earliest and most recent]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.gis.spatial.index  [multiple owners; `org.neo4j` is earliest and most recent]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.graphdb.resource  [multiple owners; `org.neo4j` is earliest and most recent]
  ? * org.neo4j                            2018-07..2026-05 2026.05.0    |...=================|
  ?   org.graphfoundation.ongdb            2020-04..2020-12 3.6.2        |.......==...........|
org.neo4j.configuration  [multiple owners; `org.neo4j` is earliest and most recent]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.graphalgo  [multiple owners; `org.neo4j` is earliest and most recent]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.internal.kernel.api  [multiple owners; `org.neo4j` is earliest and most recent]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.server.api  [multiple owners; `org.neo4j` is earliest and most recent]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.cypher.internal  [multiple owners; `org.neo4j` is earliest and most recent]
  ? * org.neo4j                            2019-04..2026-05 2026.05.0    |....================|
  ?   org.graphfoundation.ongdb            2020-04..2020-12 3.6.2        |.......==...........|
org.neo4j.server.security  [multiple owners; `org.neo4j` is earliest and most recent]
  ? * org.neo4j                            2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb            2020-04..2025-06 1.0.6        |.......===========..|
org.jline  [multiple owners; `org.jline` is earliest and most recent]
  ? * org.jline                            2020-05..2026-05 4.1.3        |.......=============|
  ?   org.glassfish.main.osgi-platforms    2022-04..2026-05 8.0.2        |...........=========|
  ?   org.apache.karaf                     2020-10..2024-04 4.4.6        |........========....|
com.rabbitmq.client  [multiple owners; `com.rabbitmq` is earliest and most recent]
  ? * com.rabbitmq                         2017-12..2026-05 5.31.0       |..==================|
  ?   com.guicedee.modules.services        2026-04..2026-05 2.0.2        |...................=|
  ?   com.guicedee.services                2021-11..2022-02 1.2.2.1-jre17 |..........=.........|
com.azure.security.keyvault.keys  [multiple owners; `com.azure` is earliest and most recent]
  ? * com.azure                            2019-09..2026-05 4.11.0       |.....===============|
  ?   com.protocol180                      2022-06..2022-07 0.2.0        |...........=........|
lettuce.core  [multiple owners; `io.lettuce` is earliest and most recent]
  ? * io.lettuce                           2019-09..2026-05 7.6.0.RELEASE |.....===============|
  ?   org.mandas                           2021-04..2021-09 6.1.5.RELEASE |.........=..........|
redis.clients.jedis  [multiple owners; `redis.clients` is earliest and most recent]
  ? * redis.clients                        2021-03..2026-05 8.0.0-beta1  |........============|
  ?   io.github.stellhub                   2026-05..2026-05 6.0.0-stellhub-otel.1 |...................=|
  ?   today.bonfire.oss                    2024-12..2024-12 5.2.0        |................=...|
com.sun.tools.rngdatatype  [multiple owners; `com.sun.xml.bind.external` is earliest and most recent]
  ? * com.sun.xml.bind.external            2018-07..2026-05 4.0.9        |...=================|
  ?   cn.lzgabel.jaxb.xml.bind.external    2022-03..2022-03 4.0.0        |..........=.........|
com.sun.tools.rngom  [multiple owners; `com.sun.xml.bind.external` is earliest and most recent]
  ? * com.sun.xml.bind.external            2018-07..2026-05 4.0.9        |...=================|
  ?   cn.lzgabel.jaxb.xml.bind.external    2022-03..2022-03 4.0.0        |..........=.........|
com.sun.xml.bind.core  [multiple owners; `com.sun.xml.bind` is earliest and most recent]
  ? * com.sun.xml.bind                     2020-04..2026-05 4.0.9        |.......=============|
  ?   org.takes                            2024-10..2024-10 1.24.6       |................=...|
  ?   com.jcabi                            2022-09..2024-06 1.9.1        |...........=====....|
  ?   com.exasol                           2024-06..2024-06 4.3.3        |...............=....|
  ?   one.gfw                              2023-03..2023-03 4.0.2.1      |............=.......|
com.sun.xml.bind.osgi  [multiple owners; `com.sun.xml.bind` is earliest and most recent]
  ? * com.sun.xml.bind                     2019-10..2026-05 4.0.9        |......==============|
  ?   com.liferay                          2025-05..2025-05 2.3.4.JAKARTA-LIFERAY-PATCHED-1 |.................=..|
com.sun.codemodel  [multiple owners; `org.glassfish.jaxb` is earliest and most recent]
  ? * org.glassfish.jaxb                   2018-07..2026-05 4.0.9        |...=================|
  ?   cn.lzgabel.jaxb                      2022-03..2022-03 4.0.0        |..........=.........|
com.sun.codemodel.ac  [multiple owners; `org.glassfish.jaxb` is earliest and most recent]
  ? * org.glassfish.jaxb                   2019-10..2026-05 4.0.9        |.....===============|
  ?   cn.lzgabel.jaxb                      2022-03..2022-03 4.0.0        |..........=.........|
com.sun.xml.txw2  [multiple owners; `org.glassfish.jaxb` is earliest and most recent]
  ? * org.glassfish.jaxb                   2018-07..2026-05 4.0.9        |...=================|
  ?   org.uma.jmetal                       2025-12..2026-05 7.3          |..................==|
  ?   io.github.jeff-tian                  2026-02..2026-02 2.4.1        |..................=.|
  ?   ai.starlake                          2022-04..2025-05 1.3.5        |...........=======..|
  ?   com.jordansamhi                      2024-08..2024-08 1.1.8        |...............=....|
  ?   org.soot-oss                         2024-04..2024-04 4.5.0        |...............=....|
    + 3 more: com.yotpo, cn.lzgabel.jaxb, org.apache.servicemix.bundles
com.sun.xml.xsom  [multiple owners; `org.glassfish.jaxb` is earliest and most recent]
  ? * org.glassfish.jaxb                   2018-07..2026-05 4.0.9        |...=================|
  ?   cn.lzgabel.jaxb                      2022-03..2022-03 4.0.0        |..........=.........|
  ?   no.entur                             2020-06..2022-01 1.47         |.......====.........|
org.glassfish.jaxb.core  [multiple owners; `org.glassfish.jaxb` is earliest and most recent]
  ? * org.glassfish.jaxb                   2020-04..2026-05 4.0.9        |.......=============|
  ?   org.biojava                          2025-01..2026-05 7.2.5        |................====|
  ?   com.intuit.quickbooks-online         2025-10..2025-11 6.6.2        |..................=.|
  ?   com.liferay                          2025-06..2025-06 4.0.5.LIFERAY-PATCHED-1 |.................=..|
  ?   de.fraunhofer.iem                    2025-03..2025-05 4.2.2        |................==..|
org.glassfish.jaxb.runtime  [multiple owners; `org.glassfish.jaxb` is earliest and most recent]
  ? * org.glassfish.jaxb                   2019-10..2026-05 4.0.9        |......==============|
  ?   io.github.vantiv                     2026-04..2026-04 12.49.0-jdk17 |...................=|
  ?   org.apache.iotdb                     2024-06..2026-04 2.0.8        |...............=====|
  ?   org.codeforamerica.platform          2026-01..2026-01 4.0.3        |..................=.|
  ?   org.xtce                             2025-04..2025-04 1.1.7        |.................=..|
  ?   org.soot-oss                         2024-10..2024-10 4.6.0        |................=...|
    + 5 more: com.bandwidth.sdk, org.jboss.windup.web, com.intuit.quickbooks-online, org.mustangproject, org.duracloud
org.glassfish.jaxb.xjc  [multiple owners; `org.glassfish.jaxb` is earliest and most recent]
  ? * org.glassfish.jaxb                   2019-10..2026-05 4.0.9        |......==============|
  ?   org.redundent                        2023-08..2023-10 1.9.1        |.............==.....|
software.amazon.awssdk.arns  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2019-10..2026-05 2.44.14      |.....===============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.identity.spi  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2023-10..2026-05 2.44.14      |..............======|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.cloudcontrol  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2021-09..2026-05 2.44.14      |.........===========|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.cloudtraildata  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2023-01..2026-05 2.44.14      |............========|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.docdb  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2019-01..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.glacier  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.m2  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2022-06..2026-05 2.44.14      |...........=========|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.marketplacereporting  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2024-10..2026-05 2.44.14      |................====|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.mwaa  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2020-11..2026-05 2.44.14      |........============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.networkmonitor  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2023-12..2026-05 2.44.14      |..............======|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.redshiftserverless  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2022-06..2026-05 2.44.14      |...........=========|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.testutils.service  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.crtcore  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2023-03..2026-05 2.44.14      |............========|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.appconfigdata  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2021-11..2026-05 2.44.14      |..........==========|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.codebuild  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.dlm  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.iottwinmaker  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2021-12..2026-05 2.44.14      |..........==========|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.pinpoint  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.rbin  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2021-11..2026-05 2.44.14      |..........==========|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.rekognition  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.resiliencehub  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2021-11..2026-05 2.44.14      |..........==========|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.route53domains  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.sagemaker  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.supplychain  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2024-01..2026-05 2.44.14      |..............======|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.taxsettings  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2024-06..2026-05 2.44.14      |...............=====|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.applicationcostprofiler  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2021-05..2026-05 2.44.14      |.........===========|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.cloudwatchlogs  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.datasync  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.dax  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.ec2instanceconnect  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2019-06..2026-05 2.44.14      |.....===============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.evs  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2025-06..2026-05 2.44.14      |.................===|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.greengrass  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.greengrassv2  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2020-12..2026-05 2.44.14      |........============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.iam  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.iotsitewise  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2020-04..2026-05 2.44.14      |.......=============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.iotwireless  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2020-12..2026-05 2.44.14      |........============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.kendra  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2019-12..2026-05 2.44.14      |......==============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.keyspacesstreams  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2025-06..2026-05 2.44.14      |.................===|
  ?   com.sonatype.central.testing.amazon  2025-08..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.launchwizard  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2023-11..2026-05 2.44.14      |..............======|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.marketplaceentitlement  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.mediaconvert  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.memorydb  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2021-08..2026-05 2.44.14      |.........===========|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.odb  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2025-07..2026-05 2.44.14      |.................===|
  ?   com.sonatype.central.testing.amazon  2025-08..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.route53resolver  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.servicequotas  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2019-06..2026-05 2.44.14      |.....===============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.snowball  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.sso  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2019-11..2026-05 2.44.14      |......==============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.timestreamquery  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2020-09..2026-05 2.44.14      |.......=============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.translate  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.checksums.spi  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2023-10..2026-05 2.44.14      |..............======|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.http.nio.netty  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.accessanalyzer  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2019-12..2026-05 2.44.14      |......==============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.amplify  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.appflow  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2020-08..2026-05 2.44.14      |.......=============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.applicationdiscovery  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.bedrockagent  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2023-11..2026-05 2.44.14      |..............======|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.cloudsearchdomain  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.connectparticipant  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2019-11..2026-05 2.44.14      |......==============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.finspacedata  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2021-05..2026-05 2.44.14      |.........===========|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.inspector  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.iotdeviceadvisor  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2020-12..2026-05 2.44.14      |........============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.kinesisvideomedia  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.lakeformation  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2019-08..2026-05 2.44.14      |.....===============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.machinelearning  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.macie2  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2020-05..2026-05 2.44.14      |.......=============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.mediapackagev2  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2023-05..2026-05 2.44.14      |.............=======|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.mediapackagevod  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2019-05..2026-05 2.44.14      |.....===============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.migrationhuborchestrator  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2022-09..2026-05 2.44.14      |...........=========|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.networkfirewall  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2020-11..2026-05 2.44.14      |........============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.paymentcryptography  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2023-06..2026-05 2.44.14      |.............=======|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.route53recoverycluster  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2021-07..2026-05 2.44.14      |.........===========|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.rum  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2021-11..2026-05 2.44.14      |..........==========|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.s3  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.s3tables  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2024-12..2026-05 2.44.14      |................====|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.sagemakermetrics  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2022-12..2026-05 2.44.14      |............========|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.savingsplans  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2019-11..2026-05 2.44.14      |......==============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.schemas  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2019-12..2026-05 2.44.14      |......==============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.serverlessapplicationrepository  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.ssooidc  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2019-11..2026-05 2.44.14      |......==============|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.swf  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2018-11..2026-05 2.44.14      |....================|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.vpclattice  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2023-03..2026-05 2.44.14      |.............=======|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.checksums  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2023-10..2026-05 2.44.14      |..............======|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.deadline  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2024-04..2026-05 2.44.14      |...............=====|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.entityresolution  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2023-07..2026-05 2.44.14      |.............=======|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.finspace  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2021-05..2026-05 2.44.14      |.........===========|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
software.amazon.awssdk.services.invoicing  [multiple owners; `software.amazon.awssdk` is earliest and most recent]
  ? * software.amazon.awssdk               2024-12..2026-05 2.44.14      |................====|
  ?   com.sonatype.central.testing.amazon  2025-07..2025-08 12.12.14     |.................=..|
```

