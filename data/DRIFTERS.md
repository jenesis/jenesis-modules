# Module ownership drifters

Generated 2026-06-12. A module *drifts* when more than one groupId publishes the name and its `owners.tsv` does not yet name every publisher (no `owners.tsv`, or one that leaves some publishing groupId neither `allowed` nor `blocked`). Resolving a drift means deciding each groupId via `SetOwners` (which writes `allowed`/`blocked`); a fully-named module drops off this list.

| Category | Modules |
|---|---:|
| republisher | 13 |
| migration | 1251 |
| fork | 483 |
| unclassified | 1664 |
| **unresolved total** | **3411** |
| multi-owner modules scanned | 3572 |
| modules scanned | 36334 |

Timeline axis spans 2017-01 .. 2026-06 (today). Per group: decision `A`=allowed `B`=blocked `?`=undecided, `*`=current owner, then the publication range, latest version, and a `=` activity bar across the axis.

## republisher (13)

Earliest owner is foreign to the module name while a natural-namespace owner is also present (shaded / repackaged jars). Proposal: allow the natural owner, block the republisher.

| count | current owner -> proposed allowed |
|---:|---|
| 3 | `jakarta.enterprise -> jakarta.cdi` |
| 3 | `org.avaje -> io.ebean` |
| 1 | `com.sun.activation -> jakarta.activation` |
| 1 | `com.sun.mail -> jakarta.mail` |
| 1 | `io.github.evonit -> net.evonit` |
| 1 | `jakarta.enterprise.concurrent -> jakarta.concurrency` |
| 1 | `org.apache.tomcat -> jakarta.servlet.jsp` |
| 1 | `org.avaje -> io.avaje` |
| 1 | `org.glassfish.jaxb -> com.sun.xml.bind` |

```
jakarta.activation  [republished by `com.sun.activation`; belongs to `jakarta.activation`]
  ? * com.sun.activation                   2018-11..2021-02 2.0.1        |....=====...........|
  ?   com.helger.schematron                2026-05..2026-05 9.2.0        |...................=|
  ?   org.uma.jmetal                       2025-12..2026-05 7.3          |..................==|
  ?   org.tinystruct                       2025-09..2026-05 1.1.5        |..................==|
  ?   com.github.xeroapi                   2025-06..2026-05 16.0.0       |.................===|
  ?   com.typesafe.play                    2022-01..2026-05 2.2.16       |..........==========|
    + 39 more: org.playframework, org.kill-bill.billing, com.cognite.spark.datasource, org.takes, com.eed3si9n, jakarta.activation, org.hpccsystems, ch.exense.step, com.newrelic.labs, io.github.dimabarbul, org.opengis.cite, io.github.gsidhwani-nr, (+27 more)
com.sun.xml.bind  [republished by `org.glassfish.jaxb`; belongs to `com.sun.xml.bind`]
  ? * org.glassfish.jaxb                   2018-07..2019-01 2.3.2        |...==...............|
  ?   com.sun.xml.bind                     2018-07..2026-05 4.0.9        |...=================|
  ?   com.exasol                           2024-10..2025-10 5.4.3        |................===.|
  ?   int.esa.ccsds.mo                     2025-08..2025-08 12.3         |.................=..|
  ?   gov.nasa.pds                         2021-10..2025-04 2.11.0       |.........=========..|
  ?   com.intuit.quickbooks-online         2021-08..2025-01 6.5.0        |.........========...|
    + 30 more: com.google.tsunami, com.helger.schematron, io.github.azagniotov, com.liferay, org.opengis.cite, org.duracloud, edu.iris.dmc, br.com.swconsultoria, one.gfw, org.xtce, com.github.cafapi, com.alibaba.compileflow, (+18 more)
jakarta.cdi  [republished by `jakarta.enterprise`; belongs to `jakarta.cdi`]
  ? * jakarta.enterprise                   2020-08..2025-10 5.0.0-M1     |.......============.|
  ?   jakarta.cdi                          2025-11..2026-05 5.0.0.Beta1  |..................==|
  ?   com.abavilla                         2023-04..2023-12 1.8.11       |.............==.....|
jakarta.cdi.el  [republished by `jakarta.enterprise`; belongs to `jakarta.cdi`]
  ? * jakarta.enterprise                   2023-10..2025-10 5.0.0-M1     |..............=====.|
  ?   jakarta.cdi                          2025-11..2026-05 5.0.0.Beta1  |..................==|
jakarta.cdi.lang.model  [republished by `jakarta.enterprise`; belongs to `jakarta.cdi`]
  ? * jakarta.enterprise                   2021-12..2025-10 5.0.0-M1     |..........=========.|
  ?   jakarta.cdi                          2025-11..2026-05 5.0.0.Beta1  |..................==|
jakarta.servlet.jsp  [republished by `org.apache.tomcat`; belongs to `jakarta.servlet.jsp`]
  ? * org.apache.tomcat                    2020-09..2026-05 10.1.55      |.......=============|
  ?   jakarta.servlet.jsp                  2020-10..2026-05 4.1.0-M2     |........============|
  ?   com.heroku                           2024-07..2024-07 10.1.25.0    |...............=....|
  ?   com.guicedee.services                2020-11..2022-02 1.2.2.1-jre17 |........===.........|
io.ebean.querybean  [republished by `org.avaje`; belongs to `io.ebean`]
  ? * org.avaje                            2020-10..2020-10 12.5.0       |........=...........|
  ?   io.ebean                             2020-10..2026-05 17.6.0       |........============|
io.ebean.test  [republished by `org.avaje`; belongs to `io.ebean`]
  ? * org.avaje                            2020-10..2020-10 12.5.0       |........=...........|
  ?   io.ebean                             2020-10..2026-05 17.6.0       |........============|
jakarta.concurrency  [republished by `jakarta.enterprise.concurrent`; belongs to `jakarta.concurrency`]
  ? * jakarta.enterprise.concurrent        2022-07..2025-10 3.2.0-M1     |...........========.|
  ?   jakarta.concurrency                  2026-03..2026-03 3.2.0-M2     |...................=|
io.avaje.junit  [republished by `org.avaje`; belongs to `io.avaje`]
  ? * org.avaje                            2023-02..2023-10 1.3          |............===.....|
  ?   io.avaje                             2023-10..2026-02 1.8          |..............=====.|
jakarta.mail  [republished by `com.sun.mail`; belongs to `jakarta.mail`]
  ? * com.sun.mail                         2018-11..2025-07 2.0.2        |....==============..|
  ?   com.randomnoun.db                    2022-10..2025-11 1.0.2        |............=======.|
  ?   org.eclipse.angus                    2021-08..2025-09 2.0.5        |.........==========.|
  ?   jakarta.mail                         2020-03..2025-09 2.1.5        |......=============.|
  ?   io.github.noeltoy                    2024-10..2024-11 1.1          |................=...|
  ?   org.camunda.bpm.extension            2022-12..2024-07 7.19.2       |............====....|
    + 4 more: name.bychkov, io.gravitee.apim.rest.api.standalone, com.krux, com.guicedee.services
net.evonit.thumbnailator2  [republished by `io.github.evonit`; belongs to `net.evonit`]
  ? * io.github.evonit                     2025-01..2025-01 0.4.21       |................=...|
  ?   net.evonit                           2025-01..2025-01 0.4.21       |................=...|
io.ebean.externalmapping.xml  [republished by `org.avaje`; belongs to `io.ebean`]
  ? * org.avaje                            2020-10..2020-10 12.5.0       |........=...........|
  ?   io.ebean                             2020-10..2021-09 12.11.5      |........==..........|
```

## migration (1251)

The publishing groupId handed off over time: the old coordinate went dormant, a newer one took over (a rename or a relocation). Proposal: allow both old and new so history stays resolvable and `latest` is current.

| count | current owner -> proposed allowed |
|---:|---|
| 72 | `io.github.reporting-solutions.nl -> io.github.reporting-solutions.nl,org.eclipse.birt.nl` |
| 68 | `io.github.dawdler-series -> io.github.dawdler-series,club.dawdler` |
| 65 | `org.opendaylight.mdsal.binding.model.ietf -> org.opendaylight.mdsal.binding.model.ietf,org.opendaylight.ietf.model` |
| 52 | `ai.swim -> ai.swim,org.swimos` |
| 36 | `org.apache.tuweni -> org.apache.tuweni,io.tmio` |
| 26 | `com.helger -> com.helger,com.helger.photon` |
| 23 | `io.github.bluetape4k -> io.github.bluetape4k,io.github.bluetape4k.exposed` |
| 19 | `com.helger -> com.helger,com.helger.commons` |
| 19 | `org.springframework.experimental -> org.springframework.experimental,org.springframework.modulith` |
| 18 | `io.github.weblegacy -> io.github.weblegacy,io.github.ys-kalyakin` |
| 17 | `com.jwebmp.jre10 -> com.jwebmp.jre10,com.jwebmp.jre11,com.jwebmp` |
| 17 | `com.querydsl -> com.querydsl,io.github.openfeign.querydsl` |
| 16 | `com.jwebmp.jre10 -> com.jwebmp.jre10,com.jwebmp.jre11,com.jwebmp,com.jwebmp.plugins.angular` |
| 15 | `com.truthbean.debbie -> com.truthbean.debbie,com.truthbean` |
| 15 | `com.truthbean.logger -> com.truthbean.logger,com.truthbean` |

_Showing the 200 most recently active of 1251. For the full list, emit the SetOwners file: `-Djenesis.crawler.drift.emit=migration`._

```
org.apache.commons.compress  [relocated `org.apache.commons` (dormant) -> `com.alibaba.ververica` (latest 1.20-vvr-11.7.0-3-jdk11)]
  ? * org.apache.commons                   2017-10..2021-07 1.21         |.=========..........|
  ?   com.alibaba.ververica                2025-08..2026-06 1.20-vvr-11.7.0-3-jdk11 |.................===|
  ?   org.apache.parquet                   2024-11..2026-05 1.17.1       |................====|
  ?   org.apache.grails                    2026-04..2026-04 7.1.1        |...................=|
  ?   org.apache.druid.extensions          2024-02..2026-04 37.0.0       |..............======|
  ?   org.apache.beam                      2024-06..2026-04 2.73.0       |...............=====|
    + 33 more: com.theartos, org.apache.flink, io.github.trethore, org.apache.pinot, me.bechberger, com.jlpka.langidentify, io.acryl, org.eclipse.tahu, com.jlpka, io.codechicken, org.onebusaway, com.mobidevelop.robovm, (+21 more)
org.apache.commons.codec  [relocated `commons-codec` (dormant) -> `com.alibaba.ververica` (latest 1.20-vvr-11.7.0-3-jdk11)]
  ? * commons-codec                        2017-10..2020-08 1.15         |.=======............|
  ?   com.alibaba.ververica                2021-06..2026-06 1.20-vvr-11.7.0-3-jdk11 |.........===========|
  ?   software.amazon.awssdk               2024-07..2026-05 2.44.14      |...............=====|
  ?   ai.platon.gora                       2026-05..2026-05 1.0.0        |...................=|
  ?   org.operaton.bpm.extension           2024-12..2026-05 2.1.0        |................====|
  ?   org.apache.tika                      2024-03..2026-05 3.3.1        |...............=====|
    + 75 more: io.gitlab.cupofcode, com.suprsend, com.mirakl, io.github.rsv-code, com.gitee.melin, org.apache.druid.extensions.contrib, org.wso2.orbit.org.opensaml, org.ops4j.pax.url, io.github.ptc-alm, org.apache.jackrabbit, com.qcloud, com.republicate.modality, (+63 more)
org.apache.commons.lang3  [relocated `org.apache.commons` (dormant) -> `ai.platon.gora` (latest 1.0.0-slim)]
  ? * org.apache.commons                   2017-06..2021-02 3.12.0       |.========...........|
  ?   ai.platon.gora                       2026-06..2026-06 1.0.0-slim   |...................=|
  ?   io.streamnative                      2025-05..2026-05 4.2.0.9      |.................===|
  ?   no.entur                             2025-02..2026-05 1.26.0       |................====|
  ?   org.sonarsource.java                 2024-11..2026-05 8.30.0.43910 |................====|
  ?   com.ascentstream.pulsar              2025-09..2026-05 4.0.11.0     |..................==|
    + 98 more: io.swagger.parser.v3, org.apache.seatunnel, org.metricshub, com.ubs-hainer, edu.kit.kastel.sdq, com.system32dev.systemplaceholders, io.openlineage, com.equinor.neqsim, org.jboss.pnc.gradle-manipulator, org.apache.gravitino, org.finos.legend.sdlc, com.sqream, (+86 more)
com.dlsc.formsfx  [relocated `com.dlsc.formsfx` (dormant) -> `com.github.stefanofornari` (latest 26.0.3)]
  ? * com.dlsc.formsfx                     2018-09..2023-01 11.6.0       |...==========.......|
  ?   com.github.stefanofornari            2026-04..2026-05 26.0.3       |...................=|
org.opentest4j  [relocated `org.opentest4j` (dormant) -> `berlin.yuna` (latest 2026.05.1492204)]
  ? * org.opentest4j                       2017-07..2023-07 1.3.0        |.=============......|
  ?   berlin.yuna                          2025-11..2026-05 2026.05.1492204 |..................==|
  ?   org.tiatesting                       2024-12..2026-05 0.1.17       |................====|
  ?   org.ndviet                           2026-04..2026-04 4.42.0       |...................=|
  ?   com.adobe.cq                         2023-02..2024-06 1.3.0        |............====....|
  ?   io.pravega                           2022-04..2023-09 0.13.0       |...........===......|
    + 7 more: org.caseine, io.github.origin-energy, com.hurence.logisland, io.github.thxno, io.github.osvalda, net.corda, com.github.tandronicus
vault.java.driver  [relocated `com.bettercloud` (dormant) -> `io.github.jopenlibs` (latest 6.2.2)]
  ? * com.bettercloud                      2019-06..2019-12 5.1.0        |.....==.............|
  ?   io.github.jopenlibs                  2022-10..2026-05 6.2.2        |............========|
  ?   io.axual.utilities.config.providers  2020-06..2024-11 1.2.0        |.......==========...|
  ?   edu.utexas.tacc.tapis                2021-10..2021-10 5.1.2        |..........=.........|
com.github.benmanes.caffeine  [relocated `com.github.ben-manes.caffeine` (dormant) -> `io.pebbletemplates` (latest 4.1.2)]
  ? * com.github.ben-manes.caffeine        2017-12..2023-08 3.1.8        |..============......|
  ?   io.pebbletemplates                   2025-12..2026-05 4.1.2        |..................==|
  ?   net.wirelabs                         2026-02..2026-04 1.4.5        |..................==|
  ?   com.google.errorprone                2022-04..2026-04 2.49.0       |...........=========|
  ?   org.apache.tinkerpop                 2026-04..2026-04 4.0.0-beta.2 |...................=|
  ?   com.janeluo                          2026-03..2026-03 1.0.1        |...................=|
    + 14 more: nl.basjes.parse.useragent, org.openprovenance.prov, io.zeebe.redis, org.odftoolkit, io.tileverse.pmtiles, org.opengis.cite, nl.goodbytes.xmpp.xep, org.igniterealtime.whack, be.vlaanderen.informatievlaanderen.ldes.ldio, com.gitlab.cdc-java.office, io.github.sinri, io.github.noobdogcloud, (+2 more)
org.opensaml.security  [relocated `org.opensaml` (dormant) -> `org.elasticsearch.plugin` (latest 9.4.2)]
  ? * org.opensaml                         2019-08..2021-02 4.0.0        |.....====...........|
  ?   org.elasticsearch.plugin             2024-02..2026-05 9.4.2        |..............======|
com.sun.xml.fastinfoset  [relocated `com.sun.xml.fastinfoset` (dormant) -> `net.corda` (latest 4.11.8)]
  ? * com.sun.xml.fastinfoset              2018-08..2023-10 2.1.1        |...============.....|
  ?   net.corda                            2019-11..2026-05 4.11.8       |......==============|
  ?   com.github.siom79.japicmp            2025-11..2026-05 0.26.1       |..................==|
  ?   org.mapsforge                        2020-01..2025-04 0.25.0       |......============..|
  ?   com.scality                          2022-09..2022-11 1.4.7        |...........==.......|
  ?   com.expedia.www                      2019-08..2021-03 0.1.18       |.....====...........|
    + 2 more: com.phenixrts.edgeauth, org.realityforge.keycloak.converger
org.apache.commons.io  [relocated `commons-io` (dormant) -> `no.entur` (latest 1.115.0)]
  ? * commons-io                           2017-10..2021-07 2.11.0       |.=========..........|
  ?   no.entur                             2024-03..2026-05 1.115.0      |...............=====|
  ?   org.sonarsource.python               2024-10..2026-05 5.23.0.33560 |................====|
  ?   com.github.cafdataprocessing.workers.languagedetection 2024-11..2026-05 7.1.0-1677   |................====|
  ?   org.teavm                            2024-04..2026-05 0.14.1       |...............=====|
  ?   org.apache.hudi                      2025-02..2026-05 1.2.0        |................====|
    + 92 more: org.apache.tika, io.github.zgrge, io.prophecy, org.bidib.jbidib, org.apache.flink, com.ascentstream.distributedlog, com.networknt, io.boxlang, com.io7m.calino, org.apache.jackrabbit, eu.europa.ted.eforms, io.openliberty.tools, (+80 more)
org.hiero.block.protobuf.sources  [renamed `org.hiero.block` -> `org.hiero.block-node` (latest 0.35.0-rc1)]
  ? * org.hiero.block                      2025-06..2025-12 0.24.2       |.................==.|
  ?   org.hiero.block-node                 2025-12..2026-05 0.35.0-rc1   |..................==|
org.apache.commons.pool2  [relocated `org.apache.commons` (dormant) -> `org.openjproxy` (latest 0.4.21-beta)]
  ? * org.apache.commons                   2020-07..2021-08 2.11.1       |.......===..........|
  ?   org.openjproxy                       2026-03..2026-05 0.4.21-beta  |..................==|
  ?   io.github.caobahuong                 2026-05..2026-05 0.1.1        |...................=|
  ?   org.apache.druid.extensions.contrib  2024-06..2026-04 37.0.0       |...............=====|
  ?   com.redis                            2025-02..2025-09 0.9.1        |................===.|
  ?   org.noear                            2024-09..2025-05 1.9.3        |................==..|
    + 4 more: io.github.hexsook, org.apache.storm, org.apache.directory.api, com.vlkan.log4j2
io.helidon.security.bundle  [renamed `io.helidon.security` -> `io.helidon.bundles` (latest 4.5.0-M1)]
  ? * io.helidon.security                  2018-09..2019-08 0.10.6       |...===..............|
  ?   io.helidon.bundles                   2019-01..2026-05 4.5.0-M1     |....================|
io.helidon.messaging.connectors.kafka  [renamed `io.helidon.messaging.connectors.kafka` -> `io.helidon.messaging.kafka` (latest 4.5.0-M1)]
  ? * io.helidon.messaging.connectors.kafka 2020-05..2020-05 2.0.0-M3     |.......=............|
  ?   io.helidon.messaging.kafka           2020-05..2026-05 4.5.0-M1     |.......=============|
io.helidon.security.abac.policy  [renamed `io.helidon.security` -> `io.helidon.security.abac` (latest 4.5.0-M1)]
  ? * io.helidon.security                  2018-09..2019-08 0.10.6       |...===..............|
  ?   io.helidon.security.abac             2019-01..2026-05 4.5.0-M1     |....================|
io.helidon.security.abac.role  [renamed `io.helidon.security` -> `io.helidon.security.abac` (latest 4.5.0-M1)]
  ? * io.helidon.security                  2018-09..2019-08 0.10.6       |...===..............|
  ?   io.helidon.security.abac             2019-01..2026-05 4.5.0-M1     |....================|
io.helidon.metrics  [renamed `io.helidon.microprofile.metrics` -> `io.helidon.metrics` (latest 4.5.0-M1)]
  ? * io.helidon.microprofile.metrics      2018-09..2019-08 0.10.6       |...===..............|
  ?   io.helidon.metrics                   2019-01..2026-05 4.5.0-M1     |....================|
io.helidon.config.metadata  [renamed `io.helidon.config` -> `io.helidon.config.metadata` (latest 4.5.0-M1)]
  ? * io.helidon.config                    2021-10..2026-04 3.2.17       |..........==========|
  ?   io.helidon.config.metadata           2026-05..2026-05 4.5.0-M1     |...................=|
io.helidon.security.abac.policy.el  [renamed `io.helidon.security` -> `io.helidon.security.abac` (latest 4.5.0-M1)]
  ? * io.helidon.security                  2018-09..2019-08 0.10.6       |...===..............|
  ?   io.helidon.security.abac             2019-01..2026-05 4.5.0-M1     |....================|
io.helidon.security.abac.time  [renamed `io.helidon.security` -> `io.helidon.security.abac` (latest 4.5.0-M1)]
  ? * io.helidon.security                  2018-09..2019-08 0.10.6       |...===..............|
  ?   io.helidon.security.abac             2019-01..2026-05 4.5.0-M1     |....================|
io.helidon.security.abac.scope  [renamed `io.helidon.security` -> `io.helidon.security.abac` (latest 4.5.0-M1)]
  ? * io.helidon.security                  2018-09..2019-08 0.10.6       |...===..............|
  ?   io.helidon.security.abac             2019-01..2026-05 4.5.0-M1     |....================|
java.xml.bind  [relocated `javax.xml.bind` (dormant) -> `com.yahoo.vespa` (latest 8.696.20)]
  ? * javax.xml.bind                       2017-07..2018-09 2.3.1        |.===................|
  ?   com.yahoo.vespa                      2020-05..2026-05 8.696.20     |.......=============|
  ?   org.apache.tika                      2018-09..2026-05 3.3.1        |...=================|
  ?   de.fraunhofer.iosb.ilt               2021-02..2026-05 0.15         |........============|
  ?   org.metricshub                       2025-05..2026-05 3.9.04       |.................===|
  ?   org.kendar.protocol                  2026-02..2026-05 4.3.9        |..................==|
    + 184 more: io.mosip.esignet, org.wso2.msf4j.perftest.echo, org.wso2.msf4j, org.wso2.msf4j.samples, org.wso2.msf4j.sample, org.apache.flink, org.apache.paimon, org.apache.fluss, de.fraunhofer.iosb.ilt.FROST-Server, org.verapdf.apps, org.apache.pinot, com.aliyun.openservices.aiservice, (+172 more)
com.sun.istack.runtime  [relocated `com.sun.istack` (dormant) -> `com.intuit.quickbooks-online` (latest 6.5.4)]
  ? * com.sun.istack                       2018-08..2023-04 4.2.0        |...===========......|
  ?   com.intuit.quickbooks-online         2025-10..2026-05 6.5.4        |..................==|
  ?   com.github.target365                 2026-04..2026-05 1.9.1        |...................=|
  ?   pro.verron.office-stamper            2026-04..2026-04 3.3          |...................=|
  ?   io.github.hongjinqiu                 2026-02..2026-02 1.0.6        |..................=.|
  ?   io.github.adrianseguraortiz          2026-02..2026-02 1.0.0        |..................=.|
    + 6 more: com.github.bld-commons, org.uma.jmetal, com.ibm.cics, org.uma, org.atlanmod.neoemf, net.haesleinhuepf
okhttp3.logging  [relocated `com.github.ljun20160606` (dormant) -> `com.ibm.cloud` (latest 6.0.2)]
  ? * com.github.ljun20160606              2018-02..2018-02 3.10.0       |..=.................|
  ?   com.ibm.cloud                        2026-02..2026-05 6.0.2        |..................==|
  ?   com.squareup.okhttp3                 2018-02..2025-11 5.2.3        |..=================.|
  ?   com.huanli233.okhttp3-compat         2025-02..2025-02 5.0.0-p2     |................=...|
io.avaje.metrics  [renamed `io.avaje.metrics` -> `io.avaje` (latest 9.12)]
  ? * io.avaje.metrics                     2020-08..2022-08 9.0.1        |.......=====........|
  ?   io.avaje                             2023-04..2026-05 9.12         |.............=======|
io.avaje.metrics.ebean  [renamed `io.avaje.metrics` -> `io.avaje` (latest 9.12)]
  ? * io.avaje.metrics                     2022-08..2022-08 9.0.1        |...........=........|
  ?   io.avaje                             2023-04..2026-05 9.12         |.............=======|
io.avaje.metrics.graphite  [renamed `io.avaje.metrics` -> `io.avaje` (latest 9.12)]
  ? * io.avaje.metrics                     2022-07..2022-08 9.0.1        |...........=........|
  ?   io.avaje                             2023-04..2026-05 9.12         |.............=======|
software.amazon.smithy.java.codegen.core  [renamed `software.amazon.smithy.java.codegen` -> `software.amazon.smithy.java` (latest 1.3.0)]
  ? * software.amazon.smithy.java.codegen  2025-02..2025-02 0.0.1        |................=...|
  ?   software.amazon.smithy.java          2025-04..2026-05 1.3.0        |.................===|
org.jspecify  [relocated `org.jspecify` (dormant) -> `io.cryostat` (latest 0.7.0)]
  ? * org.jspecify                         2021-07..2024-07 1.0.0        |.........=======....|
  ?   io.cryostat                          2025-03..2026-05 0.7.0        |.................===|
  ?   io.github.openfeign.querydsl         2026-05..2026-05 7.2          |...................=|
  ?   com.dua3.cabe                        2024-10..2026-05 4.3.0-rc     |................====|
  ?   io.github.qhsword.nacosplugins       2026-03..2026-05 2.5.1.2      |...................=|
  ?   com.google.appengine                 2025-04..2026-05 5.0.3-beta   |.................===|
    + 41 more: org.nuiton, dev.zacsweers.metro, com.helger.kaltblut, com.helger, de.pirckheimer-gymnasium, org.wiremock.extensions, dev.jbang, org.jboss.elemento, com.couchbase.client, org.eximeebpms.bpm, org.eximeebpms.bpm.qa, com.google.googlejavaformat, (+29 more)
org.hibernate.search.backend.elasticsearch  [renamed `org.hibernate` -> `org.hibernate.search` (latest 8.4.0.Final)]
  ? * org.hibernate                        2018-05..2023-01 5.11.12.Final |...==========.......|
  ?   org.hibernate.search                 2018-11..2026-05 8.4.0.Final  |....================|
org.hibernate.search.backend.elasticsearch.aws  [renamed `org.hibernate` -> `org.hibernate.search` (latest 8.4.0.Final)]
  ? * org.hibernate                        2018-05..2023-01 5.11.12.Final |...==========.......|
  ?   org.hibernate.search                 2019-04..2026-05 8.4.0.Final  |....================|
org.hibernate.search.engine  [renamed `org.hibernate` -> `org.hibernate.search` (latest 8.4.0.Final)]
  ? * org.hibernate                        2018-05..2023-01 5.11.12.Final |...==========.......|
  ?   org.hibernate.search                 2018-11..2026-05 8.4.0.Final  |....================|
org.glassfish.jakarta.json  [relocated `org.glassfish` (dormant) -> `org.nanopub` (latest 1.90.0)]
  ? * org.glassfish                        2020-08..2021-03 2.0.1        |.......==...........|
  ?   org.nanopub                          2025-05..2026-05 1.90.0       |.................===|
  ?   com.exasol                           2022-07..2026-05 9.0.0        |...........=========|
bluetape4k.aws.kotlin  [renamed `io.github.bluetape4k` -> `io.github.bluetape4k.aws` (latest 0.3.0)]
  ? * io.github.bluetape4k                 2026-03..2026-04 1.7.0        |...................=|
  ?   io.github.bluetape4k.aws             2026-05..2026-05 0.3.0        |...................=|
org.hibernate.orm.graalvm  [renamed `org.hibernate` -> `org.hibernate.orm` (latest 7.4.0.Final)]
  ? * org.hibernate                        2020-02..2023-02 5.6.15.Final |......=======.......|
  ?   org.hibernate.orm                    2020-04..2026-05 7.4.0.Final  |.......=============|
org.hibernate.orm.agroal  [renamed `org.hibernate` -> `org.hibernate.orm` (latest 7.4.0.Final)]
  ? * org.hibernate                        2018-02..2026-01 5.3.38.Final |..=================.|
  ?   org.hibernate.orm                    2018-12..2026-05 7.4.0.Final  |....================|
org.hibernate.orm.c3p0  [renamed `org.hibernate` -> `org.hibernate.orm` (latest 7.4.0.Final)]
  ? * org.hibernate                        2018-01..2026-01 5.3.38.Final |..=================.|
  ?   org.hibernate.orm                    2018-12..2026-05 7.4.0.Final  |....================|
  ?   com.guicedee.modules.services        2026-04..2026-04 2.0.0-RC10   |...................=|
  ?   com.guicedee.services                2020-07..2022-02 1.2.2.1-jre17 |.......====.........|
org.hibernate.orm.core  [renamed `org.hibernate` -> `org.hibernate.orm` (latest 7.4.0.Final)]
  ? * org.hibernate                        2018-01..2026-01 5.3.38.Final |..=================.|
  ?   org.hibernate.orm                    2018-12..2026-05 7.4.0.Final  |....================|
  ?   com.guicedee.modules.services        2026-04..2026-05 2.0.2        |...................=|
  ?   io.github.vmodi001                   2026-03..2026-03 5.6.16.Final |...................=|
  ?   org.beangle.hibernate                2020-06..2026-03 7.2.6.Final  |.......============.|
  ?   com.liferay                          2023-02..2025-05 5.6.7.LIFERAY-PATCHED-2.JAKARTA-LIFERAY-PATCHED-1 |............======..|
    + 1 more: com.guicedee.services
org.hibernate.orm.envers  [renamed `org.hibernate` -> `org.hibernate.orm` (latest 7.4.0.Final)]
  ? * org.hibernate                        2018-01..2026-01 5.3.38.Final |..=================.|
  ?   org.hibernate.orm                    2019-11..2026-05 7.4.0.Final  |......==============|
org.hibernate.orm.hikaricp  [renamed `org.hibernate` -> `org.hibernate.orm` (latest 7.4.0.Final)]
  ? * org.hibernate                        2018-01..2026-01 5.3.38.Final |..=================.|
  ?   org.hibernate.orm                    2018-12..2026-05 7.4.0.Final  |....================|
org.hibernate.orm.jcache  [renamed `org.hibernate` -> `org.hibernate.orm` (latest 7.4.0.Final)]
  ? * org.hibernate                        2018-01..2026-01 5.3.38.Final |..=================.|
  ?   org.hibernate.orm                    2019-11..2026-05 7.4.0.Final  |......==============|
  ?   com.guicedee.modules.services        2026-04..2026-05 2.0.2        |...................=|
  ?   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17 |......=====.........|
org.hibernate.orm.micrometer  [renamed `org.hibernate` -> `org.hibernate.orm` (latest 7.4.0.Final)]
  ? * org.hibernate                        2020-12..2023-02 5.6.15.Final |........=====.......|
  ?   org.hibernate.orm                    2021-03..2026-05 7.4.0.Final  |........============|
org.hibernate.orm.spatial  [renamed `org.hibernate` -> `org.hibernate.orm` (latest 7.4.0.Final)]
  ? * org.hibernate                        2018-01..2026-01 5.3.38.Final |..=================.|
  ?   org.hibernate.orm                    2021-10..2026-05 7.4.0.Final  |..........==========|
org.hibernate.orm.testing  [renamed `org.hibernate` -> `org.hibernate.orm` (latest 7.4.0.Final)]
  ? * org.hibernate                        2018-01..2026-01 5.3.38.Final |..=================.|
  ?   org.hibernate.orm                    2018-12..2026-05 7.4.0.Final  |....================|
com.querydsl.sql.codegen  [relocated `com.querydsl` (dormant) -> `io.github.openfeign.querydsl` (latest 7.2)]
  ? * com.querydsl                         2021-06..2024-01 5.1.0        |.........======.....|
  ?   io.github.openfeign.querydsl         2023-10..2026-05 7.2          |..............======|
com.querydsl.apt  [relocated `com.querydsl` (dormant) -> `io.github.openfeign.querydsl` (latest 7.2)]
  ? * com.querydsl                         2021-06..2024-01 5.1.0        |.........======.....|
  ?   io.github.openfeign.querydsl         2023-10..2026-05 7.2          |..............======|
  ?   io.github.mingeun0507                2023-12..2023-12 5.0.1        |..............=.....|
com.querydsl.codegen  [relocated `com.querydsl` (dormant) -> `io.github.openfeign.querydsl` (latest 7.2)]
  ? * com.querydsl                         2021-06..2024-01 5.1.0        |.........======.....|
  ?   io.github.openfeign.querydsl         2023-10..2026-05 7.2          |..............======|
com.querydsl.codegen.utils  [relocated `com.querydsl` (dormant) -> `io.github.openfeign.querydsl` (latest 7.2)]
  ? * com.querydsl                         2021-06..2024-01 5.1.0        |.........======.....|
  ?   io.github.openfeign.querydsl         2023-10..2026-05 7.2          |..............======|
com.querydsl.collections  [relocated `com.querydsl` (dormant) -> `io.github.openfeign.querydsl` (latest 7.2)]
  ? * com.querydsl                         2021-06..2024-01 5.1.0        |.........======.....|
  ?   io.github.openfeign.querydsl         2023-10..2026-05 7.2          |..............======|
com.querydsl.core  [relocated `com.querydsl` (dormant) -> `io.github.openfeign.querydsl` (latest 7.2)]
  ? * com.querydsl                         2021-06..2024-01 5.1.0        |.........======.....|
  ?   io.github.openfeign.querydsl         2023-10..2026-05 7.2          |..............======|
com.querydsl.core.group.guava  [relocated `com.querydsl` (dormant) -> `io.github.openfeign.querydsl` (latest 7.2)]
  ? * com.querydsl                         2021-06..2024-01 5.1.0        |.........======.....|
  ?   io.github.openfeign.querydsl         2023-10..2026-05 7.2          |..............======|
com.querydsl.jpa  [relocated `com.querydsl` (dormant) -> `io.github.openfeign.querydsl` (latest 7.2)]
  ? * com.querydsl                         2021-06..2024-01 5.1.0        |.........======.....|
  ?   io.github.openfeign.querydsl         2023-10..2026-05 7.2          |..............======|
com.querydsl.jpa.codegen  [relocated `com.querydsl` (dormant) -> `io.github.openfeign.querydsl` (latest 7.2)]
  ? * com.querydsl                         2021-06..2024-01 5.1.0        |.........======.....|
  ?   io.github.openfeign.querydsl         2023-10..2026-05 7.2          |..............======|
com.querydsl.mongodb  [relocated `com.querydsl` (dormant) -> `io.github.openfeign.querydsl` (latest 7.2)]
  ? * com.querydsl                         2021-06..2024-01 5.1.0        |.........======.....|
  ?   io.github.openfeign.querydsl         2023-10..2026-05 7.2          |..............======|
com.querydsl.spatial  [relocated `com.querydsl` (dormant) -> `io.github.openfeign.querydsl` (latest 7.2)]
  ? * com.querydsl                         2021-06..2024-01 5.1.0        |.........======.....|
  ?   io.github.openfeign.querydsl         2023-10..2026-05 7.2          |..............======|
com.querydsl.sql  [relocated `com.querydsl` (dormant) -> `io.github.openfeign.querydsl` (latest 7.2)]
  ? * com.querydsl                         2021-06..2024-01 5.1.0        |.........======.....|
  ?   io.github.openfeign.querydsl         2023-10..2026-05 7.2          |..............======|
com.querydsl.sql.spatial  [relocated `com.querydsl` (dormant) -> `io.github.openfeign.querydsl` (latest 7.2)]
  ? * com.querydsl                         2021-06..2024-01 5.1.0        |.........======.....|
  ?   io.github.openfeign.querydsl         2023-10..2026-05 7.2          |..............======|
jakarta.json  [relocated `jakarta.json` (dormant) -> `org.eclipse.parsson` (latest 1.1.9)]
  ? * jakarta.json                         2020-01..2023-10 2.1.3        |......=========.....|
  ?   org.eclipse.parsson                  2021-06..2026-05 1.1.9        |.........===========|
  ?   io.github.qudtlib                    2026-02..2026-02 7.2.0        |..................=.|
  ?   com.arangodb                         2025-08..2026-01 1.9.0        |.................==.|
  ?   io.quarkus                           2024-10..2025-02 3.18.4       |................=...|
  ?   org.openpreservation.jhove           2024-06..2025-02 1.32.1       |...............==...|
    + 8 more: zone.cogni.semanticz, com.exasol, io.github.changebooks, com.atomgraph.etl.csv, org.avaje.experiment, org.spdx, org.glassfish, com.mparticle
bluetape4k.exposed.jdbc.caffeine  [renamed `io.github.bluetape4k` -> `io.github.bluetape4k.exposed` (latest 1.9.2)]
  ? * io.github.bluetape4k                 2026-04..2026-04 1.7.0        |...................=|
  ?   io.github.bluetape4k.exposed         2026-05..2026-05 1.9.2        |...................=|
bluetape4k.exposed.mysql8  [renamed `io.github.bluetape4k` -> `io.github.bluetape4k.exposed` (latest 1.9.2)]
  ? * io.github.bluetape4k                 2026-03..2026-04 1.7.0        |...................=|
  ?   io.github.bluetape4k.exposed         2026-05..2026-05 1.9.2        |...................=|
bluetape4k.exposed.r2dbc  [renamed `io.github.bluetape4k` -> `io.github.bluetape4k.exposed` (latest 1.9.2)]
  ? * io.github.bluetape4k                 2026-02..2026-04 1.7.0        |..................==|
  ?   io.github.bluetape4k.exposed         2026-05..2026-05 1.9.2        |...................=|
bluetape4k.exposed.r2dbc.lettuce  [renamed `io.github.bluetape4k` -> `io.github.bluetape4k.exposed` (latest 1.9.2)]
  ? * io.github.bluetape4k                 2026-03..2026-04 1.7.0        |...................=|
  ?   io.github.bluetape4k.exposed         2026-05..2026-05 1.9.2        |...................=|
bluetape4k.exposed.cache  [renamed `io.github.bluetape4k` -> `io.github.bluetape4k.exposed` (latest 1.9.2)]
  ? * io.github.bluetape4k                 2026-04..2026-04 1.7.0        |...................=|
  ?   io.github.bluetape4k.exposed         2026-05..2026-05 1.9.2        |...................=|
bluetape4k.exposed.jackson2  [renamed `io.github.bluetape4k` -> `io.github.bluetape4k.exposed` (latest 1.9.2)]
  ? * io.github.bluetape4k                 2026-03..2026-04 1.7.0        |...................=|
  ?   io.github.bluetape4k.exposed         2026-05..2026-05 1.9.2        |...................=|
bluetape4k.exposed.jackson3  [renamed `io.github.bluetape4k` -> `io.github.bluetape4k.exposed` (latest 1.9.2)]
  ? * io.github.bluetape4k                 2026-02..2026-04 1.7.0        |..................==|
  ?   io.github.bluetape4k.exposed         2026-05..2026-05 1.9.2        |...................=|
bluetape4k.exposed.jdbc  [renamed `io.github.bluetape4k` -> `io.github.bluetape4k.exposed` (latest 1.9.2)]
  ? * io.github.bluetape4k                 2026-02..2026-04 1.7.0        |..................==|
  ?   io.github.bluetape4k.exposed         2026-05..2026-05 1.9.2        |...................=|
bluetape4k.exposed.jdbc.lettuce  [renamed `io.github.bluetape4k` -> `io.github.bluetape4k.exposed` (latest 1.9.2)]
  ? * io.github.bluetape4k                 2026-03..2026-04 1.7.0        |...................=|
  ?   io.github.bluetape4k.exposed         2026-05..2026-05 1.9.2        |...................=|
bluetape4k.exposed.jdbc.redisson  [renamed `io.github.bluetape4k` -> `io.github.bluetape4k.exposed` (latest 1.9.2)]
  ? * io.github.bluetape4k                 2026-02..2026-04 1.7.0        |..................==|
  ?   io.github.bluetape4k.exposed         2026-05..2026-05 1.9.2        |...................=|
bluetape4k.exposed.postgresql  [renamed `io.github.bluetape4k` -> `io.github.bluetape4k.exposed` (latest 1.9.2)]
  ? * io.github.bluetape4k                 2026-03..2026-04 1.7.0        |...................=|
  ?   io.github.bluetape4k.exposed         2026-05..2026-05 1.9.2        |...................=|
bluetape4k.exposed.r2dbc.caffeine  [renamed `io.github.bluetape4k` -> `io.github.bluetape4k.exposed` (latest 1.9.2)]
  ? * io.github.bluetape4k                 2026-04..2026-04 1.7.0        |...................=|
  ?   io.github.bluetape4k.exposed         2026-05..2026-05 1.9.2        |...................=|
bluetape4k.exposed.bigquery  [renamed `io.github.bluetape4k` -> `io.github.bluetape4k.exposed` (latest 1.9.2)]
  ? * io.github.bluetape4k                 2026-03..2026-04 1.7.0        |...................=|
  ?   io.github.bluetape4k.exposed         2026-05..2026-05 1.9.2        |...................=|
bluetape4k.exposed.core  [renamed `io.github.bluetape4k` -> `io.github.bluetape4k.exposed` (latest 1.9.2)]
  ? * io.github.bluetape4k                 2026-02..2026-04 1.7.0        |..................==|
  ?   io.github.bluetape4k.exposed         2026-05..2026-05 1.9.2        |...................=|
bluetape4k.exposed.dao  [renamed `io.github.bluetape4k` -> `io.github.bluetape4k.exposed` (latest 1.9.2)]
  ? * io.github.bluetape4k                 2026-02..2026-04 1.7.0        |..................==|
  ?   io.github.bluetape4k.exposed         2026-05..2026-05 1.9.2        |...................=|
bluetape4k.exposed.duckdb  [renamed `io.github.bluetape4k` -> `io.github.bluetape4k.exposed` (latest 1.9.2)]
  ? * io.github.bluetape4k                 2026-03..2026-04 1.7.0        |...................=|
  ?   io.github.bluetape4k.exposed         2026-05..2026-05 1.9.2        |...................=|
bluetape4k.exposed.fastjson2  [renamed `io.github.bluetape4k` -> `io.github.bluetape4k.exposed` (latest 1.9.2)]
  ? * io.github.bluetape4k                 2026-02..2026-04 1.7.0        |..................==|
  ?   io.github.bluetape4k.exposed         2026-05..2026-05 1.9.2        |...................=|
bluetape4k.exposed.jdbc.tests  [renamed `io.github.bluetape4k` -> `io.github.bluetape4k.exposed` (latest 1.9.2)]
  ? * io.github.bluetape4k                 2026-02..2026-04 1.7.0        |..................==|
  ?   io.github.bluetape4k.exposed         2026-05..2026-05 1.9.2        |...................=|
bluetape4k.exposed.measured  [renamed `io.github.bluetape4k` -> `io.github.bluetape4k.exposed` (latest 1.9.2)]
  ? * io.github.bluetape4k                 2026-02..2026-04 1.7.0        |..................==|
  ?   io.github.bluetape4k.exposed         2026-05..2026-05 1.9.2        |...................=|
bluetape4k.exposed.r2dbc.redisson  [renamed `io.github.bluetape4k` -> `io.github.bluetape4k.exposed` (latest 1.9.2)]
  ? * io.github.bluetape4k                 2026-02..2026-04 1.7.0        |..................==|
  ?   io.github.bluetape4k.exposed         2026-05..2026-05 1.9.2        |...................=|
bluetape4k.exposed.r2dbc.tests  [renamed `io.github.bluetape4k` -> `io.github.bluetape4k.exposed` (latest 1.9.2)]
  ? * io.github.bluetape4k                 2026-02..2026-04 1.7.0        |..................==|
  ?   io.github.bluetape4k.exposed         2026-05..2026-05 1.9.2        |...................=|
bluetape4k.exposed.tink  [renamed `io.github.bluetape4k` -> `io.github.bluetape4k.exposed` (latest 1.9.2)]
  ? * io.github.bluetape4k                 2026-03..2026-04 1.7.0        |..................==|
  ?   io.github.bluetape4k.exposed         2026-05..2026-05 1.9.2        |...................=|
bluetape4k.exposed.trino  [renamed `io.github.bluetape4k` -> `io.github.bluetape4k.exposed` (latest 1.9.2)]
  ? * io.github.bluetape4k                 2026-04..2026-04 1.7.0        |...................=|
  ?   io.github.bluetape4k.exposed         2026-05..2026-05 1.9.2        |...................=|
io.dropwizard.metrics.jetty12  [renamed `io.dropwizard.metrics` -> `io.dropwizard.metrics5` (latest 5.0.7)]
  ? * io.dropwizard.metrics                2023-09..2026-05 4.2.39       |..............======|
  ?   io.dropwizard.metrics5               2024-01..2026-05 5.0.7        |..............======|
io.dropwizard.metrics.jetty12.ee10  [renamed `io.dropwizard.metrics` -> `io.dropwizard.metrics5` (latest 5.0.7)]
  ? * io.dropwizard.metrics                2023-09..2026-05 4.2.39       |..............======|
  ?   io.dropwizard.metrics5               2024-01..2026-05 5.0.7        |..............======|
io.dropwizard.metrics.jetty12.ee11  [renamed `io.dropwizard.metrics` -> `io.dropwizard.metrics5` (latest 5.0.7)]
  ? * io.dropwizard.metrics                2025-08..2026-05 4.2.39       |.................===|
  ?   io.dropwizard.metrics5               2025-08..2026-05 5.0.7        |.................===|
org.zeromq.jeromq  [relocated `org.zeromq` (dormant) -> `org.jetbrains.kotlinx` (latest 0.19.0-948)]
  ? * org.zeromq                           2024-02..2024-02 0.6.0        |..............=.....|
  ?   org.jetbrains.kotlinx                2024-03..2026-05 0.19.0-948   |...............=====|
  ?   com.github.myzhan                    2025-02..2025-02 2.2.5        |................=...|
  ?   io.github.padreati                   2024-05..2024-07 2.0.0        |...............=....|
com.github.hazendaz.parent  [renamed `com.github.hazendaz.httpunit` -> `com.github.hazendaz` (latest 1.6.0)]
  ? * com.github.hazendaz.httpunit         2022-07..2025-12 2.5.0        |...........========.|
  ?   com.github.hazendaz                  2022-09..2026-05 1.6.0        |...........=========|
  ?   com.github.hazendaz.maven            2022-07..2025-11 4.1.1        |...........========.|
  ?   org.csveed                           2023-01..2025-09 0.8.3        |............======..|
  ?   au.com.acegi                         2025-03..2025-03 4.1.0        |................=...|
  ?   com.github.psi-probe                 2022-11..2023-05 3.1.9        |............==......|
    + 1 more: com.github.hazendaz.jmockit
org.apache.commons.text  [relocated `org.apache.commons` (dormant) -> `com.telamin.fluxtion` (latest 1.0.2)]
  ? * org.apache.commons                   2018-03..2020-07 1.9          |..======............|
  ?   com.telamin.fluxtion                 2026-05..2026-05 1.0.2        |...................=|
  ?   org.bonitasoft.engine.data           2026-01..2026-04 11.0.0       |..................==|
  ?   com.vmlens                           2026-01..2026-04 1.2.28       |..................==|
  ?   ru.biosoft.diagrams                  2026-01..2026-02 1.0.3        |..................=.|
  ?   io.github.venkateshamurthy           2025-10..2025-10 1.6          |..................=.|
    + 16 more: dev.jbang, io.github.davidwhitlock.joy, io.github.pro4d, org.bidib.com.github.markusbernhardt, fr.lirmm.graphik, io.github.noeltoy, io.github.mderevyankoaqa, com.salesforce.functions, org.opendaylight.aaa, org.zowe.client.java.sdk, com.jkoolcloud.tnt4j.streams, com.jkoolcloud.tnt4j.stream, (+4 more)
org.eclipse.xtend.core  [renamed `org.eclipse.xtend` -> `org.eclipse.xtext` (latest 2.43.0)]
  ? * org.eclipse.xtend                    2019-11..2025-05 2.39.0       |......============..|
  ?   org.eclipse.xtext                    2025-06..2026-05 2.43.0       |.................===|
org.eclipse.xtend.lib.macro  [renamed `org.eclipse.xtend` -> `org.eclipse.xtext` (latest 2.43.0)]
  ? * org.eclipse.xtend                    2018-04..2025-05 2.39.0       |...===============..|
  ?   org.eclipse.xtext                    2025-06..2026-05 2.43.0       |.................===|
org.eclipse.xtend.ide.common  [renamed `org.eclipse.xtend` -> `org.eclipse.xtext` (latest 2.43.0)]
  ? * org.eclipse.xtend                    2019-11..2025-05 2.39.0       |......============..|
  ?   org.eclipse.xtext                    2025-06..2026-05 2.43.0       |.................===|
org.eclipse.xtend.lib  [renamed `org.eclipse.xtend` -> `org.eclipse.xtext` (latest 2.43.0)]
  ? * org.eclipse.xtend                    2018-04..2025-05 2.39.0       |...===============..|
  ?   org.eclipse.xtext                    2025-06..2026-05 2.43.0       |.................===|
org.eclipse.xtend.lib.gwt  [renamed `org.eclipse.xtend` -> `org.eclipse.xtext` (latest 2.43.0)]
  ? * org.eclipse.xtend                    2018-04..2025-05 2.39.0       |...===============..|
  ?   org.eclipse.xtext                    2025-06..2026-05 2.43.0       |.................===|
io.apimatic.examples  [renamed `io.github.sufyankhanrao` -> `io.github.zahran444` (latest 1.0.23)]
  ? * io.github.sufyankhanrao              2024-09..2024-09 3.0.7        |...............=....|
  ?   io.github.zahran444                  2025-06..2026-05 1.0.23       |.................===|
  ?   io.github.apimatic                   2024-10..2026-04 3.0.12       |................====|
  ?   io.sdks                              2024-09..2025-05 1.2.6        |...............===..|
com.carrotsearch.hppc  [relocated `com.carrotsearch` (dormant) -> `org.teavm` (latest 0.14.1)]
  ? * com.carrotsearch                     2020-12..2024-06 0.10.0       |........========....|
  ?   org.teavm                            2024-12..2026-05 0.14.1       |................====|
org.hibernate.orm.jpamodelgen  [renamed `org.hibernate` -> `org.hibernate.orm` (latest 6.6.51.Final)]
  ? * org.hibernate                        2018-01..2026-01 5.3.38.Final |..=================.|
  ?   org.hibernate.orm                    2018-12..2026-05 6.6.51.Final |....================|
  ?   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17 |......=====.........|
org.hibernate.orm.proxool  [renamed `org.hibernate` -> `org.hibernate.orm` (latest 6.6.51.Final)]
  ? * org.hibernate                        2018-01..2026-01 5.3.38.Final |..=================.|
  ?   org.hibernate.orm                    2018-12..2026-05 6.6.51.Final |....================|
org.hibernate.orm.vibur  [renamed `org.hibernate` -> `org.hibernate.orm` (latest 6.6.51.Final)]
  ? * org.hibernate                        2018-01..2026-01 5.3.38.Final |..=================.|
  ?   org.hibernate.orm                    2018-12..2026-05 6.6.51.Final |....================|
bluetape4k.images  [renamed `io.github.bluetape4k` -> `io.github.bluetape4k.image` (latest 0.1.2)]
  ? * io.github.bluetape4k                 2026-02..2026-04 1.7.0        |..................==|
  ?   io.github.bluetape4k.image           2026-05..2026-05 0.1.2        |...................=|
com.helger.phase4.peppol.servlet  [renamed `com.helger` -> `com.helger.phase4` (latest 4.5.1)]
  ? * com.helger                           2020-01..2020-05 0.9.16       |......==............|
  ?   com.helger.phase4                    2020-05..2026-05 4.5.1        |.......=============|
com.helger.phase4  [renamed `com.helger` -> `com.helger.phase4` (latest 4.5.1)]
  ? * com.helger                           2019-08..2020-05 0.9.16       |.....===............|
  ?   com.helger.phase4                    2020-05..2026-05 4.5.1        |.......=============|
com.helger.phase4.cef  [renamed `com.helger` -> `com.helger.phase4` (latest 4.5.1)]
  ? * com.helger                           2020-05..2020-05 0.9.16       |.......=............|
  ?   com.helger.phase4                    2020-05..2026-05 4.5.1        |.......=============|
com.helger.phase4.peppol  [renamed `com.helger` -> `com.helger.phase4` (latest 4.5.1)]
  ? * com.helger                           2019-11..2020-05 0.9.16       |......==............|
  ?   com.helger.phase4                    2020-05..2026-05 4.5.1        |.......=============|
com.helger.phase4.profile.cef  [renamed `com.helger` -> `com.helger.phase4` (latest 4.5.1)]
  ? * com.helger                           2019-08..2020-05 0.9.16       |.....===............|
  ?   com.helger.phase4                    2020-05..2026-05 4.5.1        |.......=============|
com.helger.phase4.profile.peppol  [renamed `com.helger` -> `com.helger.phase4` (latest 4.5.1)]
  ? * com.helger                           2019-09..2020-05 0.9.16       |.....===............|
  ?   com.helger.phase4                    2020-05..2026-05 4.5.1        |.......=============|
java.json  [relocated `javax.json` (dormant) -> `org.choco-solver` (latest 6.0.1)]
  ? * javax.json                           2017-01..2018-11 1.1.4        |=====...............|
  ?   org.choco-solver                     2019-07..2026-05 6.0.1        |.....===============|
  ?   com.amihaiemil.web                   2021-02..2024-08 8.0.6        |........========....|
  ?   com.scalar-labs                      2019-02..2024-03 2.2.0        |....===========.....|
  ?   org.apache.sling                     2022-10..2023-11 1.1.8        |............===.....|
  ?   com.atomgraph.etl.json               2022-11..2023-08 1.0.7        |............==......|
    + 11 more: org.openpreservation.jhove, com.artipie, com.onespan.integration, org.odftoolkit, org.finra.herd, com.bitplan.wikifrontend, net.pincette, org.glassfish, com.phenixrts.edgeauth, jakarta.json, de.julielab
org.apache.commons.beanutils  [relocated `com.guicedee.services` (dormant) -> `org.wildfly` (latest 40.0.0.Final)]
  ? * com.guicedee.services                2020-06..2022-02 1.2.2.1-jre17 |.......====.........|
  ?   org.wildfly                          2025-06..2026-05 40.0.0.Final |.................===|
  ?   org.jvnet.jaxb                       2025-09..2026-05 4.0.14       |.................===|
  ?   com.guicedee.modules.services        2026-04..2026-05 2.0.2        |...................=|
  ?   com.github.bld-commons               2026-01..2026-05 3.0.19       |..................==|
  ?   kg.apc                               2025-06..2026-03 1.12         |.................===|
    + 2 more: com.github.bordertech.wcomponents, org.onebusaway
java.ws.rs  [relocated `javax.ws.rs` (dormant) -> `org.jboss.pnc.build-agent` (latest 1.2.3)]
  ? * javax.ws.rs                          2017-06..2018-08 2.1.1        |.===................|
  ?   org.jboss.pnc.build-agent            2021-07..2026-05 1.2.3        |.........===========|
  ?   org.apache.hadoop                    2026-03..2026-03 3.5.0        |...................=|
  ?   net.oneandone.ioc-unit               2021-09..2025-11 2.0.51       |.........==========.|
  ?   com.scylladb                         2025-06..2025-09 1.2.6        |.................=..|
  ?   org.opencb.opencga                   2024-03..2025-08 3.6.0        |..............====..|
    + 54 more: io.streamnative, io.github.willena, com.liferay, com.inteligr8.activiti, cn.langpy, io.github.fernandolopes, com.epam.reportportal, com.github.openstack4j.core, org.apache.opennlp, org.moskito, com.mailintegrate, dev.parodos, (+42 more)
library  [relocated `build.buf.prototype` (dormant) -> `com.connectrpc` (latest 0.8.2)]
  ? * build.buf.prototype                  2023-01..2023-01 v0.0.0-test0120 |............=.......|
  ?   com.connectrpc                       2023-09..2026-05 0.8.2        |.............=======|
  ?   build.buf                            2023-01..2023-09 0.1.10       |............==......|
okhttp  [relocated `build.buf` (dormant) -> `com.connectrpc` (latest 0.8.2)]
  ? * build.buf                            2023-02..2023-09 0.1.10       |............==......|
  ?   com.connectrpc                       2023-09..2026-05 0.8.2        |.............=======|
io.github.humbleui.skija.macos.arm64  [renamed `io.github.humbleui.skija` -> `io.github.humbleui` (latest 0.143.16)]
  ? * io.github.humbleui.skija             2021-11..2021-11 0.96.0       |..........=.........|
  ?   io.github.humbleui                   2021-12..2026-05 0.143.16     |..........==========|
io.github.humbleui.skija.macos.x64  [renamed `io.github.humbleui.skija` -> `io.github.humbleui` (latest 0.143.16)]
  ? * io.github.humbleui.skija             2021-11..2021-11 0.96.0       |..........=.........|
  ?   io.github.humbleui                   2021-12..2026-05 0.143.16     |..........==========|
io.github.humbleui.skija.shared  [renamed `io.github.humbleui.skija` -> `io.github.humbleui` (latest 0.143.16)]
  ? * io.github.humbleui.skija             2021-11..2021-11 0.96.0       |..........=.........|
  ?   io.github.humbleui                   2021-12..2026-05 0.143.16     |..........==========|
jakarta.annotation  [relocated `jakarta.annotation` (dormant) -> `io.quarkus` (latest 3.36.0)]
  ? * jakarta.annotation                   2020-01..2024-02 3.0.0        |......=========.....|
  ?   io.quarkus                           2024-08..2026-05 3.36.0       |...............=====|
  ?   com.jcabi                            2026-05..2026-05 1.11.1       |...................=|
  ?   com.datadoghq                        2022-08..2026-05 2.55.0       |...........=========|
  ?   org.apache.tomcat                    2020-09..2026-05 10.1.55      |.......=============|
  ?   org.eclipse.ecsp                     2025-03..2026-03 1.2.0        |................====|
    + 15 more: com.affinidi.tdk, net.welen.jmole, de.fraunhofer.sit.sse.flowdroid, io.github.n1ckl0sk0rtge, com.segment.analytics.java, com.heroku, com.intuit.quickbooks-online, io.journify, io.github.mitsumi-solutions-develop, org.grails, be.vlaanderen.informatievlaanderen.vsds, io.github.openfeign.querydsl, (+3 more)
org.wildfly.common  [relocated `org.wildfly.common` (dormant) -> `io.quarkus` (latest 3.33.2)]
  ? * org.wildfly.common                   2024-05..2024-09 2.0.1        |...............==...|
  ?   io.quarkus                           2026-02..2026-05 3.33.2       |..................==|
pro.verron.officestamper  [renamed `pro.verron` -> `pro.verron.office-stamper` (latest 3.4)]
  ? * pro.verron                           2024-05..2024-05 1.6.9        |...............=....|
  ?   pro.verron.office-stamper            2024-05..2026-05 3.4          |...............=====|
pro.verron.officestamper.asciidoc  [renamed `pro.verron.office-stamper` -> `pro.verron.asciidoc` (latest 3.4)]
  ? * pro.verron.office-stamper            2025-12..2026-04 3.3          |..................==|
  ?   pro.verron.asciidoc                  2026-05..2026-05 3.4          |...................=|
io.github.bucket4j.caffeine  [relocated `com.github.vladimir-bukhtoyarov` (dormant) -> `com.bucket4j` (latest 8.19.0)]
  ? * com.github.vladimir-bukhtoyarov      2022-03..2024-04 8.0.1        |..........======....|
  ?   com.bucket4j                         2022-07..2026-05 8.19.0       |...........=========|
io.github.bucket4j.coherence  [relocated `com.github.vladimir-bukhtoyarov` (dormant) -> `com.bucket4j` (latest 8.19.0)]
  ? * com.github.vladimir-bukhtoyarov      2019-11..2024-04 8.0.1        |......==========....|
  ?   com.bucket4j                         2022-07..2026-05 8.19.0       |...........=========|
io.github.bucket4j.core  [relocated `com.github.vladimir-bukhtoyarov` (dormant) -> `com.bucket4j` (latest 8.19.0)]
  ? * com.github.vladimir-bukhtoyarov      2019-11..2024-04 8.0.1        |......==========....|
  ?   com.bucket4j                         2022-07..2026-05 8.19.0       |...........=========|
io.github.bucket4j.hazelcast  [relocated `com.github.vladimir-bukhtoyarov` (dormant) -> `com.bucket4j` (latest 8.19.0)]
  ? * com.github.vladimir-bukhtoyarov      2019-11..2024-04 8.0.1        |......==========....|
  ?   com.bucket4j                         2022-07..2026-05 8.19.0       |...........=========|
io.github.bucket4j.ignite  [relocated `com.github.vladimir-bukhtoyarov` (dormant) -> `com.bucket4j` (latest 8.19.0)]
  ? * com.github.vladimir-bukhtoyarov      2019-11..2024-04 8.0.1        |......==========....|
  ?   com.bucket4j                         2022-07..2026-05 8.19.0       |...........=========|
io.github.bucket4j.infinispan  [relocated `com.github.vladimir-bukhtoyarov` (dormant) -> `com.bucket4j` (latest 8.19.0)]
  ? * com.github.vladimir-bukhtoyarov      2019-11..2024-04 8.0.1        |......==========....|
  ?   com.bucket4j                         2022-07..2026-05 8.19.0       |...........=========|
io.github.bucket4j.jcache  [relocated `com.github.vladimir-bukhtoyarov` (dormant) -> `com.bucket4j` (latest 8.19.0)]
  ? * com.github.vladimir-bukhtoyarov      2019-11..2024-04 8.0.1        |......==========....|
  ?   com.bucket4j                         2022-07..2026-05 8.19.0       |...........=========|
io.github.bucket4j.mysql  [relocated `com.github.vladimir-bukhtoyarov` (dormant) -> `com.bucket4j` (latest 8.19.0)]
  ? * com.github.vladimir-bukhtoyarov      2022-03..2024-04 8.0.1        |..........======....|
  ?   com.bucket4j                         2022-07..2026-05 8.19.0       |...........=========|
io.github.bucket4j.postgresql  [relocated `com.github.vladimir-bukhtoyarov` (dormant) -> `com.bucket4j` (latest 8.19.0)]
  ? * com.github.vladimir-bukhtoyarov      2022-03..2024-04 8.0.1        |..........======....|
  ?   com.bucket4j                         2022-07..2026-05 8.19.0       |...........=========|
com.kingbase8.jdbc  [relocated `org.jeecgframework` (dormant) -> `io.github.iscasdmo` (latest 8.6.0)]
  ? * org.jeecgframework                   2024-06..2024-06 9.0.0        |...............=....|
  ?   io.github.iscasdmo                   2026-05..2026-05 8.6.0        |...................=|
  ?   cn.com.kingbase                      2025-04..2026-02 9.0.1.jre6   |.................==.|
com.googlecode.javaewah  [relocated `com.googlecode.javaewah` (dormant) -> `org.meyvn` (latest 1.9.4)]
  ? * com.googlecode.javaewah              2023-03..2023-03 1.2.3        |............=.......|
  ?   org.meyvn                            2023-07..2026-05 1.9.4        |.............=======|
  ?   org.liquibase.ext                    2024-02..2025-01 0.4.0        |..............===...|
  ?   io.kestra.plugin                     2023-07..2024-08 0.17.2       |.............===....|
com.kohlschutter.junixsocket.core  [relocated `com.kohlschutter.junixsocket` (dormant) -> `com.sbbsystems.flink` (latest 3.4.3)]
  ? * com.kohlschutter.junixsocket         2018-12..2021-05 2.3.4        |....======..........|
  ?   com.sbbsystems.flink                 2026-01..2026-05 3.4.3        |..................==|
  ?   io.github.kzmlabs.flinkstatefun      2026-02..2026-02 3.4.0-KZM-2.0-RC5 |..................=.|
  ?   io.github.kzmlabs                    2026-02..2026-02 3.4.0-KZM-1.0-RC20 |..................=.|
  ?   org.apache.flink                     2020-09..2023-09 3.3.0        |.......=======......|
  ?   org.flinkextended                    2022-06..2022-06 0.5.0        |...........=........|
    + 1 more: group.insyde
org.newsclub.net.unix  [relocated `com.kohlschutter.junixsocket` (dormant) -> `com.sbbsystems.flink` (latest 3.4.3)]
  ? * com.kohlschutter.junixsocket         2018-12..2024-09 2.10.1       |....=============...|
  ?   com.sbbsystems.flink                 2026-01..2026-05 3.4.3        |..................==|
  ?   net.corda                            2025-09..2026-05 4.14.2       |.................===|
  ?   org.jam4s                            2025-10..2025-12 0.7.2-alpha0 |..................=.|
  ?   io.nosqlbench                        2020-02..2020-03 3.12.47      |......=.............|
  ?   io.engineblock                       2020-01..2020-01 2.12.65      |......=.............|
org.apache.wicket.metrics  [renamed `org.apache.wicket.experimental.wicket9` -> `org.apache.wicket.experimental.wicket10` (latest 10.9.1)]
  ? * org.apache.wicket.experimental.wicket9 2019-04..2026-05 0.35         |....================|
  ?   org.apache.wicket.experimental.wicket10 2024-05..2026-05 10.9.1       |...............=====|
okhttp3  [relocated `com.github.ljun20160606` (dormant) -> `com.squareup.wire` (latest 6.4.0)]
  ? * com.github.ljun20160606              2018-02..2018-02 3.10.0       |..=.................|
  ?   com.squareup.wire                    2026-01..2026-05 6.4.0        |..................==|
  ?   org.eclipse.csi                      2026-03..2026-05 0.7.3        |...................=|
  ?   org.testcontainers                   2025-12..2026-04 2.0.5        |..................==|
  ?   io.github.mashanshui                 2025-11..2025-11 1.0.0        |..................=.|
  ?   com.squareup.okhttp3                 2018-02..2025-11 5.2.3        |..=================.|
    + 3 more: com.huanli233.okhttp3-compat, io.github.sunny-chung, com.datadoghq.okhttp3
com.jn.langx  [renamed `io.github.bes2008.solution.langx` -> `io.github.qhsword.langx` (latest 5.8.3)]
  ? * io.github.bes2008.solution.langx     2024-03..2026-04 5.4.6.1      |..............======|
  ?   io.github.qhsword.langx              2025-11..2026-05 5.8.3        |..................==|
  ?   io.github.bes2008.solution.langx.security 2024-03..2026-04 5.4.6.1      |..............======|
com.jn.langx.base  [renamed `io.github.bes2008.solution.langx` -> `io.github.qhsword.langx` (latest 5.8.3)]
  ? * io.github.bes2008.solution.langx     2024-01..2026-04 5.4.6.1      |..............======|
  ?   io.github.qhsword.langx              2025-11..2026-05 5.8.3        |..................==|
com.jn.langx.reflect.asm  [renamed `io.github.bes2008.solution.langx` -> `io.github.qhsword.langx` (latest 5.8.3)]
  ? * io.github.bes2008.solution.langx     2024-01..2026-04 5.4.6.1      |..............======|
  ?   io.github.qhsword.langx              2025-11..2026-05 5.8.3        |..................==|
com.jn.langx.regexp.joni  [renamed `io.github.bes2008.solution.langx` -> `io.github.qhsword.langx` (latest 5.8.3)]
  ? * io.github.bes2008.solution.langx     2024-01..2026-04 5.4.6.1      |..............======|
  ?   io.github.qhsword.langx              2025-11..2026-05 5.8.3        |..................==|
com.jn.langx.el  [renamed `io.github.bes2008.solution.langx` -> `io.github.qhsword.langx` (latest 5.8.3)]
  ? * io.github.bes2008.solution.langx     2024-01..2026-04 5.4.6.1      |..............======|
  ?   io.github.qhsword.langx              2025-11..2026-05 5.8.3        |..................==|
com.jn.langx.pinyin  [renamed `io.github.bes2008.solution.langx` -> `io.github.qhsword.langx` (latest 5.8.3)]
  ? * io.github.bes2008.solution.langx     2024-01..2026-04 5.4.6.1      |..............======|
  ?   io.github.qhsword.langx              2025-11..2026-05 5.8.3        |..................==|
org.apache.datasketches.memory  [renamed `org.apache.datasketches` -> `org.apache.iceberg` (latest 1.11.0)]
  ? * org.apache.datasketches              2021-09..2024-10 3.0.2        |.........========...|
  ?   org.apache.iceberg                   2024-07..2026-05 1.11.0       |...............=====|
  ?   org.apache.paimon                    2024-09..2026-05 1.3.2        |...............=====|
  ?   org.apache.pinot                     2023-09..2026-04 1.5.0        |.............=======|
  ?   org.apache.flink                     2025-01..2026-03 3.6.0-1.20   |................====|
  ?   org.apache.doris                     2024-10..2026-03 3.1.1        |................===.|
    + 9 more: org.apache.seatunnel, com.facebook.presto, org.apache.fluss, io.github.pl-buiquang, com.alibaba.fluss, uk.gov.gchq.gaffer, io.kestra.plugin, org.locationtech.geomesa, com.gelerion.spark.sketches
com.jwebmp.core  [renamed `com.jwebmp.jre10` -> `com.jwebmp` (latest 2.0.2)]
  ? * com.jwebmp.jre10                     2018-08..2018-09 0.59.0.14    |...=................|
  ?   com.jwebmp                           2019-02..2026-05 2.0.2        |....================|
  ?   com.jwebmp.core                      2019-04..2022-02 1.2.2.1-jre17 |....=======.........|
  ?   com.jwebmp.jre11                     2018-11..2018-11 0.62.0.1     |....=...............|
com.jwebmp.plugins.bootstrap  [renamed `com.jwebmp.jre10` -> `com.jwebmp.plugins` (latest 2.0.2)]
  ? * com.jwebmp.jre10                     2018-08..2018-09 0.59.0.14    |...=................|
  ?   com.jwebmp.plugins                   2026-05..2026-05 2.0.2        |...................=|
  ?   com.jwebmp                           2019-02..2019-04 0.66.0.1     |....=...............|
  ?   com.jwebmp.jre11                     2018-11..2018-11 0.62.0.1     |....=...............|
com.jwebmp.plugins.c3  [renamed `com.jwebmp.jre11` -> `com.jwebmp.plugins` (latest 2.0.2)]
  ? * com.jwebmp.jre11                     2018-11..2018-11 0.62.0.1     |....=...............|
  ?   com.jwebmp.plugins                   2026-05..2026-05 2.0.2        |...................=|
  ?   com.jwebmp.plugins.graphing          2019-04..2022-02 1.2.2.1-jre17 |....=======.........|
  ?   com.jwebmp                           2019-02..2019-04 0.66.0.1     |....=...............|
com.jwebmp.plugins.d3  [renamed `com.jwebmp.jre11` -> `com.jwebmp.plugins` (latest 2.0.2)]
  ? * com.jwebmp.jre11                     2018-11..2018-11 0.62.0.1     |....=...............|
  ?   com.jwebmp.plugins                   2026-05..2026-05 2.0.2        |...................=|
  ?   com.jwebmp.plugins.graphing          2019-04..2022-02 1.2.2.1-jre17 |....=======.........|
  ?   com.jwebmp                           2019-02..2019-04 0.66.0.1     |....=...............|
com.jwebmp.plugins.datatable  [renamed `com.jwebmp.jre10` -> `com.jwebmp.plugins` (latest 2.0.2)]
  ? * com.jwebmp.jre10                     2018-08..2018-09 0.59.0.14    |...=................|
  ?   com.jwebmp.plugins                   2026-05..2026-05 2.0.2        |...................=|
  ?   com.jwebmp.plugins.forms             2019-04..2022-02 1.2.2.1-jre17 |....=======.........|
  ?   com.jwebmp                           2019-02..2019-04 0.66.0.1     |....=...............|
  ?   com.jwebmp.jre11                     2018-11..2018-11 0.62.0.1     |....=...............|
com.jwebmp.plugins.easingeffects  [renamed `com.jwebmp.jre10` -> `com.jwebmp.plugins` (latest 2.0.2)]
  ? * com.jwebmp.jre10                     2018-08..2018-09 0.59.0.14    |...=................|
  ?   com.jwebmp.plugins                   2026-04..2026-05 2.0.2        |...................=|
  ?   com.jwebmp.plugins.javascript        2019-04..2022-02 1.2.2.1-jre17 |....=======.........|
  ?   com.jwebmp.jre11                     2018-11..2018-11 0.62.0.1     |....=...............|
com.jwebmp.plugins.easypiechart  [renamed `com.jwebmp.jre10` -> `com.jwebmp.plugins` (latest 2.0.2)]
  ? * com.jwebmp.jre10                     2018-08..2018-09 0.59.0.14    |...=................|
  ?   com.jwebmp.plugins                   2026-05..2026-05 2.0.2        |...................=|
  ?   com.jwebmp.plugins.graphing          2019-04..2022-02 1.2.2.1-jre17 |....=======.........|
  ?   com.jwebmp                           2019-02..2019-04 0.66.0.1     |....=...............|
  ?   com.jwebmp.jre11                     2018-11..2018-11 0.62.0.1     |....=...............|
com.jwebmp.plugins.fontawesome5  [renamed `com.jwebmp.jre10` -> `com.jwebmp.plugins` (latest 2.0.2)]
  ? * com.jwebmp.jre10                     2018-08..2018-09 0.59.0.14    |...=................|
  ?   com.jwebmp.plugins                   2026-04..2026-05 2.0.2        |...................=|
  ?   com.jwebmp.plugins.iconsets          2019-04..2022-02 1.2.2.1-jre17 |....=======.........|
  ?   com.jwebmp                           2019-02..2019-04 0.66.0.1     |....=...............|
  ?   com.jwebmp.jre11                     2018-11..2018-11 0.62.0.1     |....=...............|
com.jwebmp.plugins.fullcalendar  [renamed `com.jwebmp.jre10` -> `com.jwebmp.plugins` (latest 2.0.2)]
  ? * com.jwebmp.jre10                     2018-08..2018-09 0.59.0.14    |...=................|
  ?   com.jwebmp.plugins                   2026-04..2026-05 2.0.2        |...................=|
  ?   com.jwebmp.plugins.jquery            2019-04..2022-02 1.2.2.1-jre17 |....=======.........|
  ?   com.jwebmp                           2019-02..2019-04 0.66.0.1     |....=...............|
  ?   com.jwebmp.jre11                     2018-11..2018-11 0.62.0.1     |....=...............|
com.jwebmp.plugins.globalize.cultures  [renamed `com.jwebmp.jre10` -> `com.jwebmp.plugins` (latest 2.0.2)]
  ? * com.jwebmp.jre10                     2018-08..2018-09 0.59.0.14    |...=................|
  ?   com.jwebmp.plugins                   2026-05..2026-05 2.0.2        |...................=|
  ?   com.jwebmp.plugins.javascript        2019-04..2022-02 1.2.2.1-jre17 |....=======.........|
  ?   com.jwebmp                           2019-02..2019-04 0.66.0.1     |....=...............|
  ?   com.jwebmp.jre11                     2018-11..2018-11 0.62.0.1     |....=...............|
com.jwebmp.plugins.glyphicons  [renamed `com.jwebmp.jre10` -> `com.jwebmp.plugins` (latest 2.0.2)]
  ? * com.jwebmp.jre10                     2018-08..2018-09 0.59.0.14    |...=................|
  ?   com.jwebmp.plugins                   2026-05..2026-05 2.0.2        |...................=|
  ?   com.jwebmp.plugins.iconsets          2019-04..2022-02 1.2.2.1-jre17 |....=======.........|
  ?   com.jwebmp                           2019-02..2019-04 0.66.0.1     |....=...............|
  ?   com.jwebmp.jre11                     2018-11..2018-11 0.62.0.1     |....=...............|
com.jwebmp.plugins.graphing.chartjs  [renamed `com.jwebmp.plugins.graphing` -> `com.jwebmp.plugins` (latest 2.0.2)]
  ? * com.jwebmp.plugins.graphing          2021-02..2022-02 1.2.2.1-jre17 |........===.........|
  ?   com.jwebmp.plugins                   2026-04..2026-05 2.0.2        |...................=|
com.jwebmp.plugins.jqplot  [renamed `com.jwebmp.jre10` -> `com.jwebmp.plugins` (latest 2.0.2)]
  ? * com.jwebmp.jre10                     2018-08..2018-09 0.59.0.14    |...=................|
  ?   com.jwebmp.plugins                   2026-05..2026-05 2.0.2        |...................=|
  ?   com.jwebmp.plugins.graphing          2019-04..2022-02 1.2.2.1-jre17 |....=======.........|
  ?   com.jwebmp                           2019-02..2019-04 0.66.0.1     |....=...............|
  ?   com.jwebmp.jre11                     2018-11..2018-11 0.62.0.1     |....=...............|
com.jwebmp.plugins.jqueryui  [renamed `com.jwebmp.jre10` -> `com.jwebmp.plugins` (latest 2.0.2)]
  ? * com.jwebmp.jre10                     2018-08..2018-09 0.59.0.14    |...=................|
  ?   com.jwebmp.plugins                   2026-05..2026-05 2.0.2        |...................=|
  ?   com.jwebmp.plugins.jquery            2019-04..2022-02 1.2.2.1-jre17 |....=======.........|
  ?   com.jwebmp                           2019-02..2019-04 0.66.0.1     |....=...............|
  ?   com.jwebmp.jre11                     2018-11..2018-11 0.62.0.1     |....=...............|
com.jwebmp.plugins.materialdesignicons  [renamed `com.jwebmp.jre10` -> `com.jwebmp.plugins` (latest 2.0.2)]
  ? * com.jwebmp.jre10                     2018-08..2018-09 0.59.0.14    |...=................|
  ?   com.jwebmp.plugins                   2026-05..2026-05 2.0.2        |...................=|
  ?   com.jwebmp.plugins.iconsets          2019-04..2022-02 1.2.2.1-jre17 |....=======.........|
  ?   com.jwebmp                           2019-02..2019-04 0.66.0.1     |....=...............|
  ?   com.jwebmp.jre11                     2018-11..2018-11 0.62.0.1     |....=...............|
com.jwebmp.plugins.materialicons  [renamed `com.jwebmp.plugins.iconsets` -> `com.jwebmp.plugins` (latest 2.0.2)]
  ? * com.jwebmp.plugins.iconsets          2019-04..2022-02 1.2.2.1-jre17 |....=======.........|
  ?   com.jwebmp.plugins                   2026-05..2026-05 2.0.2        |...................=|
com.jwebmp.plugins.plusastab  [renamed `com.jwebmp.jre10` -> `com.jwebmp.plugins` (latest 2.0.2)]
  ? * com.jwebmp.jre10                     2018-08..2018-09 0.59.0.14    |...=................|
  ?   com.jwebmp.plugins                   2026-05..2026-05 2.0.2        |...................=|
  ?   com.jwebmp.plugins.javascript        2019-04..2022-02 1.2.2.1-jre17 |....=======.........|
  ?   com.jwebmp                           2019-02..2019-04 0.66.0.1     |....=...............|
  ?   com.jwebmp.jre11                     2018-11..2018-11 0.62.0.1     |....=...............|
com.jwebmp.plugins.security.localstorage  [renamed `com.jwebmp.plugins.security` -> `com.jwebmp.plugins` (latest 2.0.2)]
  ? * com.jwebmp.plugins.security          2019-04..2022-02 1.2.2.1-jre17 |.....======.........|
  ?   com.jwebmp.plugins                   2026-05..2026-05 2.0.2        |...................=|
com.jwebmp.plugins.security.sessionstorage  [renamed `com.jwebmp.plugins.security` -> `com.jwebmp.plugins` (latest 2.0.2)]
  ? * com.jwebmp.plugins.security          2020-09..2022-02 1.2.2.1-jre17 |.......====.........|
  ?   com.jwebmp.plugins                   2026-05..2026-05 2.0.2        |...................=|
com.jwebmp.plugins.skycons  [renamed `com.jwebmp.jre10` -> `com.jwebmp.plugins` (latest 2.0.2)]
  ? * com.jwebmp.jre10                     2018-08..2018-09 0.59.0.14    |...=................|
  ?   com.jwebmp.plugins                   2026-05..2026-05 2.0.2        |...................=|
  ?   com.jwebmp.plugins.iconsets          2019-04..2022-02 1.2.2.1-jre17 |....=======.........|
  ?   com.jwebmp                           2019-02..2019-04 0.66.0.1     |....=...............|
  ?   com.jwebmp.jre11                     2018-11..2018-11 0.62.0.1     |....=...............|
com.jwebmp.plugins.themify.icons  [renamed `com.jwebmp.jre10` -> `com.jwebmp.plugins` (latest 2.0.2)]
  ? * com.jwebmp.jre10                     2018-08..2018-09 0.59.0.14    |...=................|
  ?   com.jwebmp.plugins                   2026-05..2026-05 2.0.2        |...................=|
  ?   com.jwebmp.plugins.iconsets          2019-04..2022-02 1.2.2.1-jre17 |....=======.........|
  ?   com.jwebmp                           2019-02..2019-04 0.66.0.1     |....=...............|
  ?   com.jwebmp.jre11                     2018-11..2018-11 0.62.0.1     |....=...............|
com.jwebmp.plugins.toastr  [renamed `com.jwebmp.jre10` -> `com.jwebmp.plugins` (latest 2.0.2)]
  ? * com.jwebmp.jre10                     2018-08..2018-09 0.59.0.14    |...=................|
  ?   com.jwebmp.plugins                   2026-05..2026-05 2.0.2        |...................=|
  ?   com.jwebmp.plugins.jquery            2019-04..2022-02 1.2.2.1-jre17 |....=======.........|
  ?   com.jwebmp                           2019-02..2019-04 0.66.0.1     |....=...............|
  ?   com.jwebmp.jre11                     2018-11..2018-11 0.62.0.1     |....=...............|
com.jwebmp.plugins.waveseffect  [renamed `com.jwebmp.jre10` -> `com.jwebmp.plugins` (latest 2.0.2)]
  ? * com.jwebmp.jre10                     2018-08..2018-09 0.59.0.14    |...=................|
  ?   com.jwebmp.plugins                   2026-05..2026-05 2.0.2        |...................=|
  ?   com.jwebmp.plugins.effects           2019-04..2022-02 1.2.2.1-jre17 |....=======.........|
  ?   com.jwebmp                           2019-02..2019-04 0.66.0.1     |....=...............|
  ?   com.jwebmp.jre11                     2018-11..2018-11 0.62.0.1     |....=...............|
com.jwebmp.plugins.waypoints  [renamed `com.jwebmp.plugins.jquery` -> `com.jwebmp.plugins` (latest 2.0.2)]
  ? * com.jwebmp.plugins.jquery            2020-12..2022-02 1.2.2.1-jre17 |........===.........|
  ?   com.jwebmp.plugins                   2026-05..2026-05 2.0.2        |...................=|
com.jwebmp.plugins.weathericons  [renamed `com.jwebmp.jre10` -> `com.jwebmp.plugins` (latest 2.0.2)]
  ? * com.jwebmp.jre10                     2018-08..2018-09 0.59.0.14    |...=................|
  ?   com.jwebmp.plugins                   2026-05..2026-05 2.0.2        |...................=|
  ?   com.jwebmp.plugins.iconsets          2019-04..2022-02 1.2.2.1-jre17 |....=======.........|
  ?   com.jwebmp                           2019-02..2019-04 0.66.0.1     |....=...............|
  ?   com.jwebmp.jre11                     2018-11..2018-11 0.62.0.1     |....=...............|
dev.failsafe.core  [relocated `dev.failsafe` (dormant) -> `org.apache.iceberg` (latest 1.10.2)]
  ? * dev.failsafe                         2023-03..2023-06 3.3.2        |............==......|
  ?   org.apache.iceberg                   2024-11..2026-05 1.10.2       |................====|
  ?   org.apache.gravitino                 2025-12..2026-05 1.2.1        |..................==|
  ?   software.amazon.s3.analyticsaccelerator 2025-08..2025-11 1.3.1        |.................==.|
  ?   com.reforge                          2025-09..2025-10 1.0.3        |..................=.|
  ?   cloud.prefab                         2023-08..2025-02 0.3.25       |.............====...|
    + 5 more: io.kestra.plugin, com.spotify.confidence, com.qcefast, com.corporate-startup, io.trino
ai.timefold.solver.quarkus.jackson.integration.test  [renamed `ai.timefold.solver` -> `ai.timefold.solver.enterprise` (latest 2.1.0)]
  ? * ai.timefold.solver                   2026-03..2026-05 2.1.0        |..................==|
  ?   ai.timefold.solver.enterprise        2026-04..2026-05 2.1.0        |...................=|
com.hazelcast.mapstore  [renamed `com.hazelcast.jet` -> `com.hazelcast` (latest 5.7.0)]
  ? * com.hazelcast.jet                    2022-08..2022-08 5.2-BETA-1   |...........=........|
  ?   com.hazelcast                        2022-10..2026-05 5.7.0        |............========|
io.opentelemetry.semconv  [renamed `io.opentelemetry` -> `io.opentelemetry.semconv` (latest 1.41.1)]
  ? * io.opentelemetry                     2021-01..2023-09 1.30.1-alpha |........======......|
  ?   io.opentelemetry.semconv             2023-09..2026-05 1.41.1       |.............=======|
jakarta.messaging  [relocated `jakarta.jms` (dormant) -> `org.apache.storm` (latest 2.8.8)]
  ? * jakarta.jms                          2022-03..2022-03 3.1.0        |..........=.........|
  ?   org.apache.storm                     2025-05..2026-05 2.8.8        |.................===|
  ?   be.vlaanderen.informatievlaanderen.ldes.ldio 2024-12..2024-12 2.12.0       |................=...|
org.apache.commons.mail  [relocated `com.github.ppodgorsek.email` (dormant) -> `io.prophecy` (latest 3.5.0-onprem-9.3.0)]
  ? * com.github.ppodgorsek.email          2023-06..2023-06 2.0.0        |.............=......|
  ?   io.prophecy                          2024-08..2026-05 3.5.0-onprem-9.3.0 |...............=====|
nextentity.core  [renamed `io.github.nextentity` -> `io.github.flow-entity` (latest 2.2.2)]
  ? * io.github.nextentity                 2024-03..2024-05 1.1.1        |...............=....|
  ?   io.github.flow-entity                2026-03..2026-05 2.2.2        |..................==|
com.kohlschutter.junixsocket.nativecommon  [relocated `com.kohlschutter.junixsocket` (dormant) -> `com.scivicslab.turingworkflow.plugins` (latest 1.5.0)]
  ? * com.kohlschutter.junixsocket         2018-12..2024-09 2.10.1       |....=============...|
  ?   com.scivicslab.turingworkflow.plugins 2026-05..2026-05 1.5.0        |...................=|
  ?   io.github.kzmlabs.flinkstatefun      2026-02..2026-04 3.4.0-KZM-3.1 |..................==|
  ?   org.kiwiproject                      2026-04..2026-04 1.12.0       |...................=|
  ?   com.scivicslab                       2026-01..2026-02 2.15.0       |..................=.|
cache.annotations.ri.guice  [relocated `com.jwebmp.thirdparty.jcache` (dormant) -> `com.guicedee.modules.services` (latest 2.0.2)]
  ? * com.jwebmp.thirdparty.jcache         2019-05..2019-08 0.68.0.1     |.....=..............|
  ?   com.guicedee.modules.services        2026-04..2026-05 2.0.2        |...................=|
  ?   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17 |......=====.........|
com.google.guice  [relocated `com.google.inject` (dormant) -> `com.guicedee.modules.services` (latest 2.0.2)]
  ? * com.google.inject                    2018-02..2023-05 7.0.0        |..============......|
  ?   com.guicedee.modules.services        2026-04..2026-05 2.0.2        |...................=|
  ?   ru.vyarus.guice.jakarta              2023-04..2023-04 5.1.0-rc.2   |.............=......|
  ?   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17 |......=====.........|
  ?   io.forestframework                   2021-06..2021-06 5.0.1.1      |.........=..........|
  ?   ca.stellardrift.guice-backport       2021-03..2021-03 5.0.1        |........=...........|
    + 2 more: com.jwebmp.inject, org.sonatype.sisu
org.apache.commons.collections4  [relocated `org.apache.commons` (dormant) -> `com.guicedee.modules.services` (latest 2.0.2)]
  ? * org.apache.commons                   2018-07..2019-07 4.4          |...===..............|
  ?   com.guicedee.modules.services        2026-04..2026-05 2.0.2        |...................=|
  ?   io.github.qudtlib                    2024-12..2025-10 7.1.1        |................===.|
  ?   de.jball                             2025-07..2025-07 0.9.0        |.................=..|
  ?   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17 |......=====.........|
  ?   com.jwebmp.jpms.commons              2019-04..2019-05 0.67.0.9     |.....=..............|
org.apache.kafka.client  [renamed `com.guicedee.services` -> `com.guicedee.modules.services` (latest 2.0.2)]
  ? * com.guicedee.services                2020-09..2022-02 1.2.2.1-jre17 |.......====.........|
  ?   com.guicedee.modules.services        2026-04..2026-05 2.0.2        |...................=|
aopalliance  [relocated `com.jwebmp.jre11` (dormant) -> `com.guicedee.modules.services` (latest 2.0.2)]
  ? * com.jwebmp.jre11                     2018-12..2018-12 0.63.0.19    |....=...............|
  ?   com.guicedee.modules.services        2026-04..2026-05 2.0.2        |...................=|
  ?   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17 |......=====.........|
  ?   com.jwebmp.thirdparty                2019-04..2019-08 0.68.0.1     |....==..............|
  ?   com.jwebmp                           2019-01..2019-04 0.66.0.1     |....=...............|
cache.api  [relocated `com.jwebmp.thirdparty.jcache` (dormant) -> `com.guicedee.modules.services` (latest 2.0.2)]
  ? * com.jwebmp.thirdparty.jcache         2019-05..2019-08 0.68.0.1     |.....=..............|
  ?   com.guicedee.modules.services        2026-04..2026-05 2.0.2        |...................=|
  ?   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17 |......=====.........|
com.google.guice.extensions.assistedinject  [relocated `com.google.inject.extensions` (dormant) -> `com.guicedee.modules.services` (latest 2.0.2)]
  ? * com.google.inject.extensions         2018-02..2023-05 7.0.0        |..============......|
  ?   com.guicedee.modules.services        2026-04..2026-05 2.0.2        |...................=|
  ?   ru.vyarus.guice.jakarta              2023-04..2023-04 5.1.0-rc.2   |.............=......|
  ?   com.guicedee.services                2020-06..2022-02 1.2.2.1-jre17 |.......====.........|
  ?   io.forestframework                   2021-06..2021-06 5.0.1.1      |.........=..........|
  ?   ca.stellardrift.guice-backport.extensions 2021-03..2021-03 5.0.1        |........=...........|
    + 3 more: com.guicedee.services.extensions, com.jwebmp.inject.extensions, org.sonatype.sisu.inject
com.google.guice.extensions.grapher  [relocated `com.google.inject.extensions` (dormant) -> `com.guicedee.modules.services` (latest 2.0.2)]
  ? * com.google.inject.extensions         2018-02..2023-05 7.0.0        |..============......|
  ?   com.guicedee.modules.services        2026-04..2026-05 2.0.2        |...................=|
  ?   ru.vyarus.guice.jakarta              2023-04..2023-04 5.1.0-rc.2   |.............=......|
  ?   com.guicedee.services                2020-07..2022-02 1.2.2.1-jre17 |.......====.........|
  ?   io.forestframework                   2021-06..2021-06 5.0.1.1      |.........=..........|
  ?   ca.stellardrift.guice-backport.extensions 2021-03..2021-03 5.0.1        |........=...........|
    + 3 more: com.guicedee.services.extensions, com.jwebmp.inject.extensions, org.sonatype.sisu.inject
com.google.guice.extensions.jndi  [relocated `com.google.inject.extensions` (dormant) -> `com.guicedee.modules.services` (latest 2.0.2)]
  ? * com.google.inject.extensions         2018-02..2023-05 7.0.0        |..============......|
  ?   com.guicedee.modules.services        2026-04..2026-05 2.0.2        |...................=|
  ?   ru.vyarus.guice.jakarta              2023-04..2023-04 5.1.0-rc.2   |.............=......|
  ?   com.guicedee.services                2020-07..2022-02 1.2.2.1-jre17 |.......====.........|
  ?   io.forestframework                   2021-06..2021-06 5.0.1.1      |.........=..........|
  ?   ca.stellardrift.guice-backport.extensions 2021-03..2021-03 5.0.1        |........=...........|
    + 3 more: com.guicedee.services.extensions, com.jwebmp.inject.extensions, org.sonatype.sisu.inject
com.hazelcast.all  [renamed `com.guicedee.services` -> `com.guicedee.modules.services` (latest 2.0.2)]
  ? * com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17 |......=====.........|
  ?   com.guicedee.modules.services        2026-04..2026-05 2.0.2        |...................=|
com.neuronrobotics.nrjavaserial  [renamed `com.guicedee.services` -> `com.guicedee.modules.services` (latest 2.0.2)]
  ? * com.guicedee.services                2020-12..2021-09 1.2.0.3-jre17-rc1 |........==..........|
  ?   com.guicedee.modules.services        2026-04..2026-05 2.0.2        |...................=|
  ?   com.guicedee                         2021-11..2022-02 1.2.2.1-jre17 |..........=.........|
jakarta.security.jacc.api  [renamed `com.guicedee.services` -> `com.guicedee.modules.services` (latest 2.0.2)]
  ? * com.guicedee.services                2020-11..2022-02 1.2.2.1-jre17 |........===.........|
  ?   com.guicedee.modules.services        2026-04..2026-05 2.0.2        |...................=|
jandex  [renamed `com.guicedee.services` -> `com.guicedee.modules.services` (latest 2.0.2)]
  ? * com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17 |......=====.........|
  ?   com.guicedee.modules.services        2026-04..2026-05 2.0.2        |...................=|
javassist  [renamed `com.guicedee.services` -> `com.guicedee.modules.services` (latest 2.0.2)]
  ? * com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17 |......=====.........|
  ?   com.guicedee.modules.services        2026-04..2026-05 2.0.2        |...................=|
net.sf.uadetector.resources  [relocated `com.jwebmp.jre11` (dormant) -> `com.guicedee.modules.services` (latest 2.0.2)]
  ? * com.jwebmp.jre11                     2018-11..2018-12 0.63.0.19    |....=...............|
  ?   com.guicedee.modules.services        2026-04..2026-05 2.0.2        |...................=|
  ?   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17 |......=====.........|
  ?   com.jwebmp.thirdparty                2019-04..2019-08 0.68.0.1     |....==..............|
  ?   com.jwebmp                           2019-01..2019-04 0.66.0.1     |....=...............|
org.apache.commons.fileupload  [relocated `com.jwebmp.jre11` (dormant) -> `com.guicedee.modules.services` (latest 2.0.2)]
  ? * com.jwebmp.jre11                     2018-12..2018-12 0.63.0.19    |....=...............|
  ?   com.guicedee.modules.services        2026-04..2026-05 2.0.2        |...................=|
  ?   org.wiremock                         2025-06..2026-04 4.0.0-beta.32 |.................===|
  ?   org.openidentityplatform.openam.agents 2025-11..2026-03 5.0.3        |..................==|
  ?   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17 |......=====.........|
  ?   com.jwebmp.jpms.commons              2019-04..2019-08 0.68.0.1     |....==..............|
    + 1 more: com.jwebmp
org.apache.commons.math3  [renamed `com.guicedee.services` -> `com.guicedee.modules.services` (latest 2.0.2)]
  ? * com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17 |......=====.........|
  ?   com.guicedee.modules.services        2026-04..2026-05 2.0.2        |...................=|
cache.annotations.ri.common  [relocated `com.jwebmp.thirdparty.jcache` (dormant) -> `com.guicedee.modules.services` (latest 2.0.2)]
  ? * com.jwebmp.thirdparty.jcache         2019-05..2019-08 0.68.0.1     |.....=..............|
  ?   com.guicedee.modules.services        2026-04..2026-05 2.0.2        |...................=|
  ?   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17 |......=====.........|
com.google.guice.extensions.jmx  [relocated `com.google.inject.extensions` (dormant) -> `com.guicedee.modules.services` (latest 2.0.2)]
  ? * com.google.inject.extensions         2018-02..2023-05 7.0.0        |..============......|
  ?   com.guicedee.modules.services        2026-04..2026-05 2.0.2        |...................=|
  ?   ru.vyarus.guice.jakarta              2023-04..2023-04 5.1.0-rc.2   |.............=......|
  ?   com.guicedee.services                2020-07..2022-02 1.2.2.1-jre17 |.......====.........|
  ?   io.forestframework                   2021-06..2021-06 5.0.1.1      |.........=..........|
  ?   ca.stellardrift.guice-backport.extensions 2021-03..2021-03 5.0.1        |........=...........|
    + 3 more: com.guicedee.services.extensions, com.jwebmp.inject.extensions, org.sonatype.sisu.inject
com.guicedee.guicedhazelcast  [renamed `com.guicedee.persistence` -> `com.guicedee` (latest 2.0.2)]
  ? * com.guicedee.persistence             2019-11..2022-02 1.2.2.1-jre17 |......=====.........|
  ?   com.guicedee                         2026-05..2026-05 2.0.2        |...................=|
com.hazelcast.hibernate  [renamed `com.guicedee.services` -> `com.guicedee.modules.services` (latest 2.0.2)]
  ? * com.guicedee.services                2020-05..2022-02 1.2.2.1-jre17 |.......====.........|
  ?   com.guicedee.modules.services        2026-04..2026-05 2.0.2        |...................=|
net.sf.uadetector.core  [relocated `com.jwebmp.jre11` (dormant) -> `com.guicedee.modules.services` (latest 2.0.2)]
  ? * com.jwebmp.jre11                     2018-11..2018-12 0.63.0.19    |....=...............|
  ?   com.guicedee.modules.services        2026-04..2026-05 2.0.2        |...................=|
  ?   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17 |......=====.........|
  ?   com.jwebmp.thirdparty                2019-04..2019-08 0.68.0.1     |....==..............|
  ?   com.jwebmp                           2019-01..2019-04 0.66.0.1     |....=...............|
org.apache.cxf  [renamed `com.guicedee.services` -> `com.guicedee.modules.services` (latest 2.0.2)]
  ? * com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17 |......=====.........|
  ?   com.guicedee.modules.services        2026-04..2026-05 2.0.2        |...................=|
JSQLFormatter.JSQLTranspiler.main  [renamed `ai.starlake.jsqltranspiler` -> `ai.starlake` (latest 1.5.15)]
  ? * ai.starlake.jsqltranspiler           2025-06..2026-03 1.8          |.................==.|
  ?   ai.starlake                          2025-08..2026-05 1.5.15       |.................===|
  ?   com.manticore-projects.jsqlformatter 2025-12..2025-12 1.4.7        |..................=.|
java.json.bind  [relocated `javax.json.bind` (dormant) -> `org.open-metadata` (latest 1.13.0-rc1)]
  ? * javax.json.bind                      2017-04..2017-06 1.0          |==..................|
  ?   org.open-metadata                    2023-08..2026-05 1.13.0-rc1   |.............=======|
  ?   org.jboss.pnc.build-agent            2024-06..2026-03 1.1.9        |...............====.|
  ?   be.valuya.cestzam                    2021-09..2023-01 2023.1.1     |.........====.......|
  ?   com.manywho.sdk                      2020-02..2020-05 2.0.1        |......==............|
  ?   jakarta.json.bind                    2019-01..2019-08 1.0.2        |....==..............|
    + 5 more: io.zeleo.application, org.keycloak, com.github.robozonky.distribution, com.github.robozonky, net.redpipe
jakarta.ws.rs  [relocated `jakarta.ws.rs` (dormant) -> `org.jboss.narayana.lra` (latest 1.2.0.Final)]
  ? * jakarta.ws.rs                        2020-02..2024-04 4.0.0        |......==========....|
  ?   org.jboss.narayana.lra               2024-12..2026-05 1.2.0.Final  |................====|
  ?   nl.mirila.cli                        2025-11..2026-05 3.14.0       |..................==|
  ?   com.inteligr8.activiti               2024-10..2026-03 1.3.0-aps-v26.1 |................====|
  ?   io.github.tblsoft.solr               2025-07..2026-03 4.5          |.................===|
  ?   com.affinidi.tdk                     2025-01..2026-03 1.6.0        |................===.|
    + 13 more: com.bluecirclesoft.open, me.chrissw-r1, com.biit-solutions, com.github.xeroapi, com.liferay, no.telenor.sdk, org.opengis.cite, com.documents4j, com.github.estuaryoss, com.jcabi, com.datadoghq, com.guicedee.services, (+1 more)
com.hexagontk.http_server_jetty  [renamed `com.hexagontk` -> `com.hexagontk.http` (latest 4.3.4)]
  ? * com.hexagontk                        2024-09..2024-10 4.0.0-A6     |................=...|
  ?   com.hexagontk.http                   2025-01..2026-05 4.3.4        |................====|
com.hexagontk.jul  [renamed `com.hexagontk` -> `com.hexagontk.extra` (latest 4.3.4)]
  ? * com.hexagontk                        2024-10..2024-10 4.0.0-A6     |................=...|
  ?   com.hexagontk.extra                  2025-03..2026-05 4.3.4        |.................===|
com.hexagontk.serialization  [renamed `com.hexagontk` -> `com.hexagontk.serialization` (latest 4.3.4)]
  ? * com.hexagontk                        2024-09..2024-10 4.0.0-A6     |................=...|
  ?   com.hexagontk.serialization          2025-01..2026-05 4.3.4        |................====|
```

## fork (483)

A cross-org coordinate publishes the same name while the original owner is still active. Proposal: keep the original owner, block the fork.

| count | current owner -> proposed allowed |
|---:|---|
| 73 | `org.digidoc4j.dss -> org.digidoc4j.dss` |
| 40 | `org.neo4j -> org.neo4j` |
| 27 | `com.swirlds -> com.swirlds` |
| 18 | `com.squareup.retrofit2 -> com.squareup.retrofit2` |
| 17 | `org.bytedeco -> org.bytedeco` |
| 15 | `com.github.dabasan -> com.github.dabasan` |
| 13 | `ch.reportingsoft.birt -> ch.reportingsoft.birt` |
| 10 | `com.dylibso.chicory -> com.dylibso.chicory` |
| 9 | `io.netty -> io.netty` |
| 8 | `io.github.jwharm.javagi -> io.github.jwharm.javagi` |
| 7 | `com.datastax.oss -> com.datastax.oss` |
| 7 | `com.github.almasb -> com.github.almasb` |
| 7 | `io.swagger.core.v3 -> io.swagger.core.v3` |
| 6 | `net.kyori -> net.kyori` |
| 6 | `org.ow2.asm -> org.ow2.asm` |

_Showing the 200 most recently active of 483. For the full list, emit the SetOwners file: `-Djenesis.crawler.drift.emit=fork`._

```
com.aoapps.javadoc.resources  [fork: keep `com.aoapps`, `com.aoindustries` still publishes the name]
  ? * com.aoapps                           2021-07..2026-05 6.0.0        |.........===========|
  ?   com.aoindustries                     2024-05..2026-06 1.92.2       |...............=====|
  ?   com.semanticcms                      2023-09..2026-02 2.0.1        |.............======.|
com.fasterxml.jackson.jaxrs.json  [fork: keep `com.fasterxml.jackson.jaxrs`, `ai.askamerica` still publishes the name]
  ? * com.fasterxml.jackson.jaxrs          2017-10..2026-06 2.22.0       |.===================|
  ?   ai.askamerica                        2026-05..2026-06 0.21.4       |...................=|
  ?   com.alibaba.ververica                2022-10..2026-06 1.20-vvr-11.7.0-3-jdk11 |............========|
  ?   com.ascentstream.pulsar              2026-05..2026-05 4.0.11.0     |...................=|
  ?   org.apache.hudi                      2026-05..2026-05 1.2.0        |...................=|
  ?   org.lance                            2025-12..2026-05 0.5.0-beta.1 |..................==|
    + 53 more: org.apache.phoenix, dev.henneberger, org.apache.pulsar, io.github.giis-uniovi, org.apache.pinot, org.apache.flink, org.devlive.connector, org.apache.hbase.thirdparty, org.apache.seatunnel, org.onebusaway, tech.leovan.hive, com.lancedb, (+41 more)
com.google.gson  [fork: keep `com.google.code.gson`, `com.aliyun` still publishes the name]
  ? * com.google.code.gson                 2019-10..2026-04 2.14.0       |.....===============|
  ?   com.aliyun                           2021-09..2026-06 9.3.2        |.........===========|
  ?   com.alibaba.ververica                2025-06..2026-06 1.20-vvr-11.7.0-3-jdk11 |.................===|
  ?   org.openjproxy                       2025-09..2026-05 0.4.21-beta  |.................===|
  ?   dev.uebelacker.babeli                2026-05..2026-05 1.0.1        |...................=|
  ?   org.sonarsource.java                 2024-11..2026-05 8.30.0.43910 |................====|
    + 522 more: org.sonarsource.dotnet, org.sonarsource.sonarlint.ls, com.github.transbankdevelopers, io.lakefs, org.sonarsource.sonarlint.core, org.jetbrains.kotlin, com.ascentstream.pulsar, io.lionweb, ai.chronon, cn.signit.sdk, io.arenadata.hive, dev.blitical, (+510 more)
com.fasterxml.jackson.core  [fork: keep `com.fasterxml.jackson.core`, `com.alibaba.ververica` still publishes the name]
  ? * com.fasterxml.jackson.core           2017-09..2026-05 2.21.4       |.===================|
  ?   com.alibaba.ververica                2022-11..2026-06 1.20-vvr-11.7.0-3-jdk11 |............========|
  ?   io.github.quoll.owlapi               2026-04..2026-05 0.4.0        |...................=|
  ?   com.grafana                          2024-07..2026-05 v0.0.13      |...............=====|
  ?   com.algolia                          2023-08..2026-05 4.39.0       |.............=======|
  ?   io.process4j                         2025-06..2026-05 0.23.3       |.................===|
    + 397 more: com.dbvis, org.apache.hudi, io.deephaven, org.operaton.spin, org.apache.seatunnel, org.evomaster, ai.particledb, com.gpudb, com.alibaba.hologres, org.kill-bill.billing, org.wildfly.security, com.linkedin.iceberg, (+385 more)
com.google.common  [fork: keep `com.google.guava`, `com.alibaba.ververica` still publishes the name]
  ? * com.google.guava                     2017-07..2026-04 33.6.0-jre   |.===================|
  ?   com.alibaba.ververica                2026-01..2026-06 1.20-vvr-11.7.0-3-jdk11 |..................==|
  ?   io.orkes.conductor                   2026-05..2026-05 5.0.3        |...................=|
  ?   io.digiexpress                       2025-10..2026-05 6.0.21       |..................==|
  ?   org.foundationdb                     2026-01..2026-05 4.12.7.0     |..................==|
  ?   net.sourceforge.plantuml             2025-06..2026-05 1.2026.5     |.................===|
    + 65 more: io.acryl, io.javelit, de.m3y.prometheus.exporter.fsimage, org.apache.spark, org.apache.iceberg, org.apache.phoenix, org.conductoross, io.yki.sapply, org.apache.calcite.avatica, org.jboss.shrinkwrap.resolver, org.talend.sdk.component.sample.feature, org.apache.sedona, (+53 more)
kotlin.stdlib  [fork: keep `org.jetbrains.kotlin`, `com.alibaba.ververica` still publishes the name]
  ? * org.jetbrains.kotlin                 2019-01..2023-08 1.9.10       |....==========......|
  ?   com.alibaba.ververica                2025-11..2026-06 1.20-vvr-11.7.0-3-jdk11 |..................==|
  ?   com.airbnb.viaduct                   2026-01..2026-05 1.1.0        |..................==|
  ?   org.octopusden.octopus.jira          2026-05..2026-05 2.0.0        |...................=|
  ?   io.last9                             2026-02..2026-05 2.3.5-beta.11 |..................==|
  ?   com.tidbcloud                        2026-05..2026-05 0.4.6        |...................=|
    + 216 more: org.jetbrains.kotlinx, org.virtuslab, com.volcengine, com.easemob.im, org.jetbrains.lets-plot, dev.robocode.tankroyale, de.darkatra.injector, dev.hsbrysk, net.corda, com.aliyun.odps, ca.acendas, com.seanshubin.code.structure, (+204 more)
org.slf4j  [fork: keep `org.slf4j`, `com.alibaba.ververica` still publishes the name]
  ? * org.slf4j                            2017-04..2026-05 2.0.18       |====================|
  ?   com.alibaba.ververica                2025-02..2026-06 1.20-vvr-11.7.0-3-jdk11 |................====|
  ?   com.jcabi                            2020-11..2026-05 0.37.0       |........============|
  ?   io.testomat                          2025-10..2026-05 0.13.0       |..................==|
  ?   io.github.de-tu-dresden-inf-lat      2026-01..2026-05 0.2.1        |..................==|
  ?   org.open-metadata                    2026-02..2026-05 1.12.9       |..................==|
    + 310 more: io.euhedral-execution, com.databricks, com.ionutbanu, io.github.wallawood, com.newrelic.agent.android, org.mustangproject, org.apache.tika, it.rotaliano.salesforce, org.metricshub, nl.tno, com.google.appengine, io.github.liquid-java, (+298 more)
org.apache.logging.log4j  [fork: keep `org.apache.logging.log4j`, `com.alibaba.ververica` still publishes the name]
  ? * org.apache.logging.log4j             2017-11..2026-05 2.26.0       |..==================|
  ?   com.alibaba.ververica                2020-08..2026-06 1.20-vvr-11.7.0-3-jdk11 |.......=============|
  ?   io.github.zhouzhoucoder              2026-05..2026-05 3.0          |...................=|
  ?   com.ibm.galasa                       2026-02..2026-05 1.1.8        |..................==|
  ?   org.apache.hudi                      2023-02..2026-05 0.15.1       |............========|
  ?   io.github.beehive-lab                2025-09..2026-05 0.4.0-jdk21  |..................==|
    + 321 more: io.github.uwegeercken, org.hpccsystems, org.into-cps.maestro, com.adobe.campaign.tests.bridge.service, io.camunda, com.robotaccomplice, dev.mauch, net.cg4j, nl.basjes.parse.useragent, com.limemojito.oss.aws, io.github.egonw, org.apache.seatunnel, (+309 more)
org.bouncycastle.pkix  [fork: keep `org.bouncycastle`, `com.alibaba.ververica` still publishes the name]
  ? * org.bouncycastle                     2018-07..2026-05 1.81.1       |...=================|
  ?   com.alibaba.ververica                2022-10..2026-06 1.20-vvr-11.7.0-3-jdk11 |............========|
  ?   com.github.melin                     2026-04..2026-05 1.0.1        |...................=|
  ?   com.github.toolarium                 2023-12..2026-04 1.2.8        |..............======|
  ?   org.apache.pinot                     2025-09..2026-04 1.5.0        |.................===|
  ?   io.streamnative.connectors           2021-03..2026-02 4.1.1.0      |........===========.|
    + 57 more: com.exasol, org.hyperledger.fabric, org.finos.legend.engine, org.apache.inlong, io.kestra.plugin, de.tk.opensource, org.jetbrains, org.lucee, com.nhn.gameanvil, io.sermant, com.linecorp.armeria, com.petalsdata.arrears, (+45 more)
com.dlsc.preferencesfx  [fork: keep `com.dlsc.preferencesfx`, `com.github.stefanofornari` still publishes the name]
  ? * com.dlsc.preferencesfx               2018-09..2026-04 11.19.0      |...=================|
  ?   com.github.stefanofornari            2026-04..2026-06 26.0.4       |...................=|
org.json  [fork: keep `org.json`, `com.github.karsaig` still publishes the name]
  ? * org.json                             2018-08..2026-05 20260522     |...=================|
  ?   com.github.karsaig                   2026-05..2026-05 1.3.0        |...................=|
  ?   org.test-charm                       2026-04..2026-05 1.0.0-alpha.23 |...................=|
  ?   io.swagger.codegen.v3                2025-09..2026-05 3.0.81       |..................==|
  ?   io.github.dasilvafg                  2024-02..2026-05 20251224     |..............======|
  ?   com.guicedee.modules.services        2026-04..2026-05 2.0.2        |...................=|
    + 30 more: fr.milekat, io.github.bareboneslib, io.github.funcbox-i3, io.github.xtemplus, com.xhc-bot, org.labkey.api, org.primefaces, io.github.lemonjuice95, com.aliyun.opensearch, io.firebolt, com.smartbear, io.github.projectunified, (+18 more)
io.smallrye.common.constraint  [fork: keep `io.smallrye.common`, `io.github.danielgp-eu` still publishes the name]
  ? * io.smallrye.common                   2022-05..2026-05 2.18.1       |...........=========|
  ?   io.github.danielgp-eu                2026-05..2026-05 2.5.0        |...................=|
  ?   io.quarkus                           2025-07..2026-05 3.20.6.1     |.................===|
org.yaml.snakeyaml  [fork: keep `org.yaml`, `com.sparkutils` still publishes the name]
  ? * org.yaml                             2019-02..2026-02 2.6          |....===============.|
  ?   com.sparkutils                       2024-12..2026-05 0.2.0-i129-complete |................====|
  ?   com.arcmutate                        2024-01..2026-05 0.0.5        |..............======|
  ?   com.huaweicloud.sdk                  2024-01..2026-05 3.1.198      |..............======|
  ?   com.nvidia                           2023-04..2026-05 26.04.2      |.............=======|
  ?   dev.feit                             2026-05..2026-05 0.2.1        |...................=|
    + 75 more: org.conductoross, com.google.cloud, dev.domkss, org.apache.phoenix, com.scivicslab, com.scivicslab.turingworkflow.plugins, org.apache.flink, com.uchicom, io.vertx, io.github.mrtesz, org.gov4j.thirdparty.org.yaml, io.swagger, (+63 more)
com.alibaba.fastjson2  [fork: keep `com.alibaba.fastjson2`, `io.github.ifeng113` still publishes the name]
  ? * com.alibaba.fastjson2                2022-05..2026-05 2.0.62       |...........=========|
  ?   io.github.ifeng113                   2024-12..2026-05 0.2.0        |................====|
  ?   com.jd.live                          2024-11..2026-04 1.9.0        |................====|
  ?   org.apache.seatunnel                 2025-05..2026-02 2.3.13       |.................==.|
  ?   io.dingodb                           2025-10..2026-01 4.0.0        |..................=.|
  ?   com.taobao.arthas                    2024-11..2025-11 4.1.2        |................===.|
    + 18 more: com.dingtalk.open, cn.langpy, io.github.qiaoyk666, cn.bugstack, cn.lunadeer, com.hydraql, io.github.smartboot.mqtt, io.gitee.soulgoodmans, io.github.linyimin0812, org.smartboot.mqtt, com.simboss.sdk, io.github.cbxhpy, (+6 more)
org.xerial.sqlitejdbc  [fork: keep `org.xerial`, `com.codenameone` still publishes the name]
  ? * org.xerial                           2021-06..2026-05 3.53.1.0     |.........===========|
  ?   com.codenameone                      2024-08..2026-05 7.0.247      |...............=====|
  ?   org.apache.tika                      2023-01..2026-05 3.3.1        |............========|
  ?   at.hugob.plugin.library              2023-04..2026-04 1.1.0        |.............=======|
  ?   io.github.zhyt1985                   2026-03..2026-03 1.0.0        |...................=|
  ?   com.clapbxt                          2026-03..2026-03 1.0.0        |...................=|
    + 17 more: dev.aga.sqlite, io.toxicity.sqlite-mc, io.github.frame-dev, org.apache.sedona, nz.co.gregs, app.cash.sqldelight, io.github.pacocarlesimo, me.gulya.sqldelight, solutions.a2.oracle.iceberg, io.telereso.kmp.sqldelight, io.github.treebolic, com.erudika, (+5 more)
org.objectweb.asm.tree.analysis  [fork: keep `org.ow2.asm`, `com.codenameone` still publishes the name]
  ? * org.ow2.asm                          2018-01..2026-05 9.10.1       |..==================|
  ?   com.codenameone                      2025-12..2026-05 7.0.247      |..................==|
  ?   org.mock-server                      2026-05..2026-05 6.1.0        |...................=|
  ?   org.teavm                            2023-03..2026-05 0.14.1       |............========|
  ?   org.bonitasoft.bpm                   2025-03..2026-04 9.0.9        |.................===|
  ?   io.github.moliholy                   2026-04..2026-04 0.3.0        |...................=|
    + 9 more: com.webforj, com.weedow, org.openidentityplatform.openidm.tools, org.noear, fish.payara.extras, za.co.absa.hermes, com.yotpo, org.apache.felix, build.please
io.netty.handler.proxy  [fork: keep `io.netty`, `io.sirix` still publishes the name]
  ? * io.netty                             2017-12..2026-05 4.1.134.Final |..==================|
  ?   io.sirix                             2026-04..2026-05 1.0.0-alpha8 |...................=|
  ?   io.kestra                            2025-08..2026-05 1.1.20       |.................===|
  ?   org.apache.iceberg                   2026-05..2026-05 1.11.0       |...................=|
  ?   io.micronaut.starter                 2025-06..2026-05 4.10.14      |.................===|
  ?   org.apache.grails                    2026-05..2026-05 8.0.0-M1     |...................=|
    + 4 more: com.facebook.presto, io.kestra.plugin, com.frog-development.consul-populate, io.kestra.storage
org.objectweb.asm  [fork: keep `org.ow2.asm`, `com.my-oli` still publishes the name]
  ? * org.ow2.asm                          2017-07..2026-05 9.10.1       |.===================|
  ?   com.my-oli                           2025-05..2026-05 1.1.1        |.................===|
  ?   net.corda                            2020-07..2026-05 4.11.8       |.......=============|
  ?   org.teavm                            2023-03..2026-05 0.14.1       |............========|
  ?   org.virtuslab.scala-cli              2023-05..2026-05 1.14.0       |.............=======|
  ?   org.tiatesting                       2024-04..2026-05 0.1.17       |...............=====|
    + 157 more: com.microsoft.azure.kusto, com.pinterest.psc, com.datadoghq, org.apache.iotdb, com.github.jnr, io.github.nieuwmijnleven.jadex, be.ugent.idlab.knows, org.fitnesse, io.github.vdaburon, org.soot-oss, org.noear, org.apache.hugegraph, (+145 more)
dev.kastle.webrtc  [fork: keep `dev.kastle.webrtc`, `com.saga-it.webrtc` still publishes the name]
  ? * dev.kastle.webrtc                    2025-12..2026-03 1.0.4        |..................==|
  ?   com.saga-it.webrtc                   2026-03..2026-05 1.2.0        |..................==|
org.objectweb.asm.tree  [fork: keep `org.ow2.asm`, `io.killedkenny.crossfuzz` still publishes the name]
  ? * org.ow2.asm                          2017-07..2026-05 9.10.1       |.===================|
  ?   io.killedkenny.crossfuzz             2026-05..2026-05 0.0.3        |...................=|
  ?   org.teavm                            2023-03..2026-05 0.14.1       |............========|
  ?   org.jetbrains.compose.hot-reload     2025-10..2026-05 1.2.0-alpha01 |..................==|
  ?   io.joynr.tools.generator             2021-05..2026-04 1.24.7       |.........===========|
  ?   ch.exense.step                       2025-11..2026-03 3.29.4       |..................==|
    + 13 more: com.scylladb, com.lihaoyi, io.joern, io.github.llmagentbuilder, com.jordansamhi, com.liferay, com.uber.nullaway, com.houxinlin, io.github.houxinlin, com.autonomousapps, org.netbeans.external, com.guujiang, (+1 more)
kotlin.stdlib.jdk8  [fork: keep `org.jetbrains.kotlin`, `com.airbnb.viaduct` still publishes the name]
  ? * org.jetbrains.kotlin                 2019-01..2023-08 1.9.10       |....==========......|
  ?   com.airbnb.viaduct                   2026-01..2026-05 1.1.0        |..................==|
  ?   com.airbnb.viaduct.javaapi           2026-05..2026-05 1.1.0        |...................=|
  ?   com.google.genai                     2026-04..2026-05 1.56.0       |...................=|
  ?   com.newrelic.agent.android           2024-09..2026-05 7.7.5        |...............=====|
  ?   com.sportradar.unifiedodds.sdk       2025-02..2026-05 4.9.0        |................====|
    + 234 more: org.apache.hudi, com.aliyun, net.corda, com.squareup, ai.realitydefender, com.gitee.melin.huaweicloud, com.kernelflux.mobile, com.aliyun.odps, pl.wtx.wordpress, io.getstream, com.displee, top.mrxiaom.mirai, (+222 more)
kotlinx.coroutines.core  [fork: keep `org.jetbrains.kotlinx`, `com.airbnb.viaduct` still publishes the name]
  ? * org.jetbrains.kotlinx                2023-03..2026-05 1.11.0       |............========|
  ?   com.airbnb.viaduct                   2026-05..2026-05 1.1.0        |...................=|
  ?   ca.acendas                           2025-11..2026-05 1.9.1        |..................==|
  ?   com.krillforge                       2026-04..2026-04 0.0.2        |...................=|
  ?   org.openprojectx.hadoop.win          2026-04..2026-04 0.1.4-3.1.1.7.1.9.14-2 |...................=|
  ?   org.jetbrains.dokka                  2024-10..2026-03 2.2.0        |................====|
    + 17 more: com.eygraber, org.jetbrains.intellij.deps.kotlinx, io.github.danbeldev, io.johnsonlee.kx, io.github.zimoyin, io.johnsonlee.exec, io.github.saumya-bhatt, io.realm.kotlin, com.rickbusarow.doks, io.sirix, com.squareup.wire, io.github.mderevyankoaqa, (+5 more)
kotlinx.coroutines.test  [fork: keep `org.jetbrains.kotlinx`, `com.airbnb.viaduct` still publishes the name]
  ? * org.jetbrains.kotlinx                2023-03..2026-05 1.11.0       |............========|
  ?   com.airbnb.viaduct                   2026-04..2026-05 1.1.0        |...................=|
  ?   org.jetbrains.intellij.deps.kotlinx  2025-09..2025-11 1.10.2-intellij-1 |..................=.|
org.objectweb.asm.commons  [fork: keep `org.ow2.asm`, `io.debezium` still publishes the name]
  ? * org.ow2.asm                          2017-07..2026-05 9.10.1       |.===================|
  ?   io.debezium                          2026-02..2026-05 3.6.0.Beta1  |..................==|
  ?   io.joern                             2025-06..2026-05 12.2_744c5dee92-202605281342 |.................===|
  ?   org.teavm                            2023-03..2026-05 0.14.1       |............========|
  ?   com.appdynamics                      2024-01..2026-04 26.3.1       |..............======|
  ?   com.apollographql.apollo             2024-07..2026-04 4.4.3        |...............=====|
    + 33 more: org.copper-engine, cn.iservicego, org.tango-controls, com.apollographql.apollo3, de.firemage.autograder, com.yugabyte, com.newrelic.agent.android, com.gradleup, org.tango-controls.pogo, software.amazon.disco, org.realityforge.shade, org.javastro.vodsl, (+21 more)
org.jsoup  [fork: keep `org.jsoup`, `org.finos.legend.sdlc` still publishes the name]
  ? * org.jsoup                            2018-04..2026-04 1.22.2       |..==================|
  ?   org.finos.legend.sdlc                2026-04..2026-05 0.224.3      |...................=|
  ?   com.github.tsantalis                 2025-06..2026-05 3.1.4        |.................===|
  ?   org.apache.tika                      2025-01..2026-05 3.3.1        |................====|
  ?   org.graylog2                         2025-07..2026-05 7.0.7        |.................===|
  ?   com.qainsights                       2026-05..2026-05 2.0.8        |...................=|
    + 33 more: org.scala-sbt, software.amazon.jdbc, io.get-coursier, io.yupiik.maven, org.spdx, com.sonatype.clm, org.jboss.pnc.gradle-manipulator, org.testingisdocumenting.znai, sh.fyz, io.github.haiphamcoder, net.serenity-bdd, com.googlecode.blaisemath, (+21 more)
com.azure.resourcemanager  [fork: keep `com.azure.resourcemanager`, `net.corda` still publishes the name]
  ? * com.azure.resourcemanager            2020-10..2026-05 2.62.0       |........============|
  ?   net.corda                            2026-03..2026-05 4.11.8       |..................==|
org.graalvm.truffle  [fork: keep `org.graalvm.truffle`, `io.hyperfoil.tools` still publishes the name]
  ? * org.graalvm.truffle                  2018-10..2026-04 25.0.3       |...=================|
  ?   io.hyperfoil.tools                   2020-01..2026-05 0.11.2       |......==============|
  ?   com.walmartlabs.concord.runtime.v2   2026-05..2026-05 2.41.0       |...................=|
  ?   com.walmartlabs.concord.k8s          2026-05..2026-05 2.41.0       |...................=|
  ?   com.walmartlabs.concord.runtime.v1   2026-05..2026-05 2.41.0       |...................=|
  ?   com.walmartlabs.concord              2026-05..2026-05 2.41.0       |...................=|
    + 25 more: com.arcadedb, org.opensearch.migrations.trafficcapture, com.liquibase.ext, sh.oso, org.mitre.synthea, com.molo17.gluesync.alpha, tools.dscode, ch.zizka.jbake, com.dbvis, io.camunda.connectors.community, ai.evolv, uk.co.kmathers, (+13 more)
org.apache.cxf.rs.security.jose  [fork: keep `org.apache.cxf`, `org.gov4j.thirdparty.org.apache.cxf` still publishes the name]
  ? * org.apache.cxf                       2018-06..2026-05 3.6.11       |...=================|
  ?   org.gov4j.thirdparty.org.apache.cxf  2024-12..2026-05 4.2.1-gov4j-1 |................====|
  ?   io.github.rzo1.org.apache.cxf        2024-07..2025-11 4.2.0-tomee-m0-071068f |...............====.|
  ?   com.liferay                          2025-05..2025-05 3.5.11.JAKARTA-LIFERAY-PATCHED-1 |.................=..|
org.apache.cxf.core  [fork: keep `org.apache.cxf`, `org.gov4j.thirdparty.org.apache.cxf` still publishes the name]
  ? * org.apache.cxf                       2018-06..2026-05 3.6.11       |...=================|
  ?   org.gov4j.thirdparty.org.apache.cxf  2024-12..2026-05 4.2.1-gov4j-1 |................====|
  ?   io.github.rzo1.org.apache.cxf        2024-07..2025-11 4.2.0-tomee-m0-071068f |...............====.|
  ?   com.liferay                          2025-05..2025-05 3.5.11.JAKARTA-LIFERAY-PATCHED-1 |.................=..|
org.mozilla.rhino.engine  [fork: keep `org.mozilla`, `org.bidib.com.github.markusbernhardt` still publishes the name]
  ? * org.mozilla                          2021-11..2026-02 1.9.1        |..........=========.|
  ?   org.bidib.com.github.markusbernhardt 2026-05..2026-05 2.0.1        |...................=|
io.netty.buffer  [fork: keep `io.netty`, `com.urbanairship` still publishes the name]
  ? * io.netty                             2017-12..2026-05 4.1.134.Final |..==================|
  ?   com.urbanairship                     2026-01..2026-05 11.2.2       |..................==|
  ?   net.neoforged.jst                    2025-12..2026-05 2.0.8        |..................==|
  ?   org.apache.tika                      2025-04..2026-05 3.3.1        |.................===|
  ?   org.apache.iceberg                   2025-04..2026-05 1.11.0       |.................===|
  ?   org.apache.polaris                   2025-07..2026-05 1.5.0        |.................===|
    + 15 more: org.atsign, org.opendaylight.jsonrpc, org.lance, io.github.lukaszsamson, org.opendaylight.controller, io.github.linagora.linid.im, org.opendaylight.bgpcep, org.opendaylight.netconf, org.apache.kyuubi, org.apache.arrow, org.apache.flink, org.wso2.orbit.rabbitmq, (+3 more)
io.netty.codec.http  [fork: keep `io.netty`, `com.amazonaws` still publishes the name]
  ? * io.netty                             2017-12..2026-05 4.1.134.Final |..==================|
  ?   com.amazonaws                        2026-02..2026-05 2026.21.1    |..................==|
  ?   dev.zio                              2025-05..2026-05 3.11.2       |.................===|
  ?   org.apache.tika                      2026-03..2026-05 3.3.1        |...................=|
  ?   org.eclipse.ditto                    2025-09..2026-05 3.9.0        |.................===|
  ?   io.sapl                              2026-04..2026-04 4.0.0        |...................=|
    + 2 more: com.xuxueli, de.fraunhofer.iosb.ilt.faaast.service
org.eclipse.jetty.alpn.conscrypt.server  [fork: keep `org.eclipse.jetty`, `com.adobe.campaign.tests.bridge.service` still publishes the name]
  ? * org.eclipse.jetty                    2018-11..2026-05 12.0.35      |....================|
  ?   com.adobe.campaign.tests.bridge.service 2026-05..2026-05 3.11.5       |...................=|
io.github.classgraph  [fork: keep `io.github.classgraph`, `org.finos.legend.engine` still publishes the name]
  ? * io.github.classgraph                 2018-08..2025-10 4.8.184      |...================.|
  ?   org.finos.legend.engine              2025-10..2026-05 4.129.10     |..................==|
  ?   org.finos.legend.sdlc                2021-08..2026-05 0.222.3      |.........===========|
  ?   org.paramixel                        2026-04..2026-04 1.0.0-beta   |...................=|
  ?   org.javastro.vodsl                   2022-02..2026-03 0.4.10       |..........==========|
  ?   software.amazon.glue                 2021-06..2026-01 1.1.27       |.........==========.|
    + 76 more: dev.getelements.elements.crossfire, com.netgrif, org.plumelib, com.google.tsunami, org.finos.legend.depot, io.github.api-ghost-agent, org.geneweaver, cn.ashersu.omni.model, org.kie.j2cl.tools.di.ui, org.kie.j2cl.tools.di, io.crysknife.ui, io.crysknife, (+64 more)
io.netty.internal.tcnative  [fork: keep `io.netty`, `org.finos.legend.engine` still publishes the name]
  ? * io.netty                             2021-10..2026-04 2.0.77.Final |.........===========|
  ?   org.finos.legend.engine              2026-01..2026-05 4.129.10     |..................==|
  ?   app.cash.backfila                    2025-04..2026-05 2026.05.28.162006-546becb |.................===|
  ?   com.spotify.confidence               2026-01..2026-05 0.15.0       |..................==|
  ?   io.weaviate                          2025-07..2026-05 6.2.1        |.................===|
  ?   org.apache.tika                      2024-10..2026-05 3.3.1        |................====|
    + 69 more: com.scalekit, org.metricshub, com.opendatadsl, com.google.cloud.bigtable, io.acryl, com.instaclustr, org.apache.iceberg, com.google.cloud.spark.bigtable, com.google.api-ads, org.wso2.msf4j, com.microsoft.azure.kusto, com.arcadedb, (+57 more)
org.apache.felix.framework  [fork: keep `org.apache.felix`, `com.yahoo.vespa` still publishes the name]
  ? * org.apache.felix                     2021-06..2022-05 7.0.5        |.........===........|
  ?   com.yahoo.vespa                      2024-03..2026-05 8.696.20     |..............======|
  ?   dev.galasa                           2024-09..2026-05 0.48.0       |...............=====|
  ?   org.lucee                            2023-02..2026-05 7.0.4.34-RC  |............========|
  ?   io.stargate.starter                  2024-10..2026-04 2.1.0-BETA-38 |................====|
kotlinx.serialization.json  [fork: keep `org.jetbrains.kotlinx`, `com.emergetools` still publishes the name]
  ? * org.jetbrains.kotlinx                2021-09..2026-04 1.11.0       |.........===========|
  ?   com.emergetools                      2026-05..2026-05 4.4.1        |...................=|
  ?   com.google.devtools.ksp              2024-07..2026-05 2.3.9        |...............=====|
  ?   top.e404.statimg                     2026-05..2026-05 2.1.0        |...................=|
  ?   ch.ubique.uniffi                     2025-07..2026-05 1.0.10       |.................===|
  ?   com.github.skydoves                  2025-11..2026-04 0.7.3        |..................==|
    + 32 more: io.github.ruxbit.ksp, dev.mtctx.library, io.github.adokky, co.touchlab.skie, com.hadisatrio.libs.android, io.portone, com.pinterest.ktlint, community.flock.wirespec.plugin.cli, io.johnsonlee.kx, io.johnsonlee.exec, io.specmatic, io.github.themrmilchmann.gradle.publish.curseforge, (+20 more)
com.networknt.schema  [fork: keep `com.networknt`, `dev.dokimos` still publishes the name]
  ? * com.networknt                        2023-04..2026-05 3.0.3        |.............=======|
  ?   dev.dokimos                          2026-05..2026-05 0.15.0       |...................=|
  ?   io.github.jdbcx                      2026-01..2026-05 1.1.1        |..................==|
  ?   com.intuit.isl                       2026-02..2026-04 1.2.0        |..................==|
  ?   org.wiremock                         2023-07..2026-01 1.0.0-beta.5 |.............======.|
  ?   org.sonarsource.text                 2023-10..2025-12 2.20.2.9978  |..............=====.|
    + 5 more: org.wiremock.extensions, com.github.nagyesta.abort-mission.reports, org.wiremock.integrations, xyz.block, com.github.tomakehurst
com.zaxxer.hikari  [fork: keep `com.zaxxer`, `io.github.deathgod7` still publishes the name]
  ? * com.zaxxer                           2018-01..2025-09 6.3.3        |..================..|
  ?   io.github.deathgod7                  2024-04..2026-05 1.1.2        |...............=====|
  ?   io.higson                            2024-01..2026-05 4.0.31       |..............======|
  ?   org.apache.hudi                      2023-02..2026-05 1.2.0        |............========|
  ?   org.finos.legend.depot               2025-06..2026-05 2.92.2       |.................===|
  ?   org.apache.seatunnel                 2022-12..2026-05 4.0.3-3.0.0  |............========|
    + 72 more: org.kill-bill.billing, org.quickfixj, io.github.kaleert, com.scalar-labs, org.apache.flink, com.aliyun.schedulerx, org.testingisdocumenting.webtau, org.apache.dolphinscheduler, it.unibz.inf.ontop, org.finos.legend.shared, cn.qaiu, work.noice, (+60 more)
org.fusesource.jansi  [fork: keep `org.fusesource.jansi`, `org.wildfly.core` still publishes the name]
  ? * org.fusesource.jansi                 2019-04..2026-03 2.4.3        |....================|
  ?   org.wildfly.core                     2026-01..2026-05 33.0.0.Beta1 |..................==|
  ?   com.github.jlangch                   2026-04..2026-05 1.13.5       |...................=|
  ?   io.github.lxgaming                   2024-04..2024-11 1.3.27       |...............==...|
  ?   com.dslplatform                      2024-02..2024-02 2.1.0        |..............=.....|
org.jetbrains.annotations  [fork: keep `org.jetbrains`, `com.qcloud.cos` still publishes the name]
  ? * org.jetbrains                        2018-09..2026-02 26.1.0       |...================.|
  ?   com.qcloud.cos                       2024-03..2026-05 1.5.2        |...............=====|
  ?   io.github.alepandocr                 2026-03..2026-05 1.0.35       |...................=|
  ?   io.deephaven                         2025-09..2026-05 41.7         |..................==|
  ?   com.kinetica                         2025-09..2026-05 7.2.3.19     |.................===|
  ?   beer.devs                            2025-02..2026-05 1.4.19       |................====|
    + 74 more: com.microsoft.azure.kusto, io.github.nbauma109, io.github.happybavarian07, org.jam4s, systems.manifold, io.streamthoughts, org.tallison.tika, me.bechberger, me.bechberger.jfr, org.f14a, tech.guilhermekaua.spigot-boot, de.jvstvshd.necrify, (+62 more)
jakarta.servlet  [fork: keep `jakarta.servlet`, `com.heroku` still publishes the name]
  ? * jakarta.servlet                      2020-09..2026-05 6.2.0-M2     |.......=============|
  ?   com.heroku                           2024-11..2026-05 10.1.55.0    |................====|
  ?   org.tinystruct                       2024-01..2026-05 1.1.3        |..............======|
  ?   org.apache.tomcat                    2020-09..2026-05 10.1.55      |.......=============|
  ?   com.uchicom                          2025-06..2026-05 0.8.0        |.................===|
  ?   org.apache.myfaces.core              2023-02..2026-04 4.1.3        |............========|
    + 9 more: com.google.appengine, org.apache.felix, io.gitee.dqcer, io.github.priyanhsu10, com.github.xeroapi, io.hawt, cloud.piranha.core, com.guicedee.services, cloud.piranha.servlet
org.apache.tomcat.embed.websocket  [fork: keep `org.apache.tomcat.embed`, `com.heroku` still publishes the name]
  ? * org.apache.tomcat.embed              2019-09..2026-05 10.1.55      |.....===============|
  ?   com.heroku                           2024-06..2026-05 9.0.118.0    |...............=====|
  ?   org.chenile                          2026-03..2026-05 2.1.20       |...................=|
  ?   io.anserini                          2024-04..2024-04 0.35.1       |...............=....|
  ?   org.ops4j.pax.tipi                   2020-07..2020-09 9.0.37.1     |.......=............|
org.apache.tomcat.jasper  [fork: keep `org.apache.tomcat`, `com.heroku` still publishes the name]
  ? * org.apache.tomcat                    2020-09..2026-05 10.1.55      |.......=============|
  ?   com.heroku                           2024-05..2026-05 9.0.118.0    |...............=====|
com.sun.tools.jxc  [fork: keep `org.glassfish.jaxb`, `com.sun.xml.bind` still publishes the name]
  ? * org.glassfish.jaxb                   2018-07..2019-01 2.3.2        |...==...............|
  ?   com.sun.xml.bind                     2018-07..2026-05 4.0.9        |...=================|
  ?   cn.lzgabel.jaxb.xml.bind             2022-03..2022-03 4.0.0        |..........=.........|
com.sun.tools.xjc  [fork: keep `org.glassfish.jaxb`, `com.sun.xml.bind` still publishes the name]
  ? * org.glassfish.jaxb                   2018-07..2019-01 2.3.2        |...==...............|
  ?   com.sun.xml.bind                     2018-07..2026-05 4.0.9        |...=================|
  ?   cn.lzgabel.jaxb.xml.bind             2022-03..2022-03 4.0.0        |..........=.........|
  ?   com.github.shynixn                   2019-02..2019-02 1.0          |....=...............|
jakarta.el  [fork: keep `jakarta.el`, `org.open-metadata` still publishes the name]
  ? * jakarta.el                           2020-08..2026-05 6.1.0-M2     |.......=============|
  ?   org.open-metadata                    2026-05..2026-05 1.12.9       |...................=|
  ?   org.apache.tomcat                    2020-11..2026-05 10.1.55      |........============|
  ?   org.jboss.spec.jakarta.el            2020-12..2025-11 6.0.2.Final  |........===========.|
  ?   com.heroku                           2024-05..2025-01 10.1.34.0    |...............==...|
  ?   com.guicedee.services                2020-11..2022-02 1.2.2.1-jre17 |........===.........|
    + 1 more: org.tomitribe.jamira
org.apache.commons.logging  [fork: keep `org.slf4j`, `org.open-metadata` still publishes the name]
  ? * org.slf4j                            2017-04..2026-05 2.0.18       |====================|
  ?   org.open-metadata                    2025-11..2026-05 1.12.9       |..................==|
  ?   org.operaton.bpm.extension           2026-02..2026-05 2.1.0        |..................==|
  ?   org.apache.tika                      2022-09..2026-05 3.3.1        |............========|
  ?   net.ontopia                          2026-05..2026-05 5.7.0        |...................=|
  ?   org.jboss.pnc.build-agent            2026-05..2026-05 1.2.2        |...................=|
    + 30 more: com.facebook.presto.hive, com.nordstrom.ui-tools, org.beangle.sas, io.github.linagora.linid.im, io.brunoborges, commons-logging, org.apache.orc, io.pivotal.cfenv, org.operaton.bpm, de.redsix, org.jboss.logging, com.uchicom, (+18 more)
info.picocli  [fork: keep `info.picocli`, `io.spicelabs` still publishes the name]
  ? * info.picocli                         2017-10..2025-04 4.7.7        |.=================..|
  ?   io.spicelabs                         2025-06..2026-05 1.3.1        |.................===|
  ?   com.muquit.libgpw                    2026-05..2026-05 1.0.3        |...................=|
  ?   ai.tegmentum.webassembly4j           2026-03..2026-05 1.3.0        |...................=|
  ?   org.rundeck.rd                       2022-05..2026-05 2.1.1        |...........=========|
  ?   org.apache.tika                      2023-12..2026-05 3.3.1        |..............======|
    + 144 more: org.keycloak, com.instaclustr, dev.kgidwani, site.asm0dey, de.splatgames.aether.datafixers, com.eventoframework, org.primefaces, io.github.rygel, org.apiaddicts.apitools.apigen, com.helger, com.bioinceptionlabs, io.github.cvs0, (+132 more)
org.openapitools.jackson.nullable  [fork: keep `org.openapitools`, `io.airlift` still publishes the name]
  ? * org.openapitools                     2023-02..2026-03 0.2.10       |............========|
  ?   io.airlift                           2026-04..2026-05 427          |...................=|
ch.randelshofer.fastdoubleparser  [fork: keep `ch.randelshofer`, `software.amazon.smithy.java` still publishes the name]
  ? * ch.randelshofer                      2022-11..2024-11 2.0.1        |............=====...|
  ?   software.amazon.smithy.java          2026-05..2026-05 1.3.0        |...................=|
  ?   io.github.solven-eu.pepper           2024-11..2026-04 5.7          |................====|
  ?   org.metafacture                      2025-05..2026-03 8.0.1        |.................==.|
  ?   com.clickzetta                       2024-08..2026-02 3.0.27       |...............====.|
  ?   org.jetbrains.kotlinx.dataframe      2024-10..2026-01 1.0.0-dev-9368 |................===.|
    + 32 more: com.cjbooms, org.apache.inlong, za.co.absa.spline.agent.spark, io.github.cmu-phil, io.github.hkarthik7, io.kestra.plugin, io.trino, org.apache.arrow, com.databricks, org.sonarsource.text, org.openrewrite, org.openapitools.openapidiff, (+20 more)
org.apache.commons.validator  [fork: keep `org.chronos-eaas`, `org.apiaddicts.apitools.dosonarapi` still publishes the name]
  ? * org.chronos-eaas                     2024-07..2025-01 2.5.1        |...............==...|
  ?   org.apiaddicts.apitools.dosonarapi   2026-05..2026-05 2.0.0        |...................=|
io.smallrye.mutiny  [fork: keep `io.smallrye.reactive`, `io.github.the-infinite` still publishes the name]
  ? * io.smallrye.reactive                 2020-09..2026-05 3.2.1        |.......=============|
  ?   io.github.the-infinite               2026-05..2026-05 1.0.7        |...................=|
  ?   io.quarkus                           2025-01..2026-05 3.27.4       |................====|
  ?   io.github.dreamlike-ocean            2024-01..2024-01 3.0.2        |..............=.....|
  ?   com.abavilla                         2022-10..2023-04 1.7.1        |............==......|
org.apache.logging.log4j.to.slf4j  [fork: keep `org.apache.logging.log4j`, `io.mosip.image.compressor` still publishes the name]
  ? * org.apache.logging.log4j             2023-10..2026-05 2.26.0       |..............======|
  ?   io.mosip.image.compressor            2024-12..2026-05 0.1.1        |................====|
  ?   io.mosip.kernel                      2024-10..2026-05 1.3.1        |................====|
  ?   org.taxilang                         2026-01..2026-02 1.70.6       |..................=.|
  ?   com.regtab.core                      2024-05..2026-02 0.1.13       |...............====.|
  ?   io.mosip.imagedecoder                2024-12..2025-12 0.10.0       |................===.|
    + 4 more: nl.basjes.parse.useragent, io.github.mihirjoshi884, io.github.tky0065, com.limemojito.oss.aws
com.google.j2objc.annotations  [fork: keep `com.google.j2objc`, `de.arbeitsagentur.opdt` still publishes the name]
  ? * com.google.j2objc                    2024-03..2025-08 3.1          |..............====..|
  ?   de.arbeitsagentur.opdt               2025-06..2026-05 5.7.0        |.................===|
  ?   com.scylladb                         2025-03..2026-05 4.1.3        |.................===|
  ?   dev.zacsweers.metro                  2025-12..2026-05 1.1.1        |..................==|
  ?   com.intergral.deep                   2024-09..2026-04 1.2.8        |...............=====|
  ?   io.dingodb                           2025-07..2026-01 4.0.0        |.................==.|
    + 15 more: io.github.erroraway, org.apache.cassandra, com.apivolve, nl.markv, io.edurt.datacap, com.google.cloud, org.liquibase.ext, com.yugabyte, com.google.api, com.jd.live, de.cau.cs.kieler, io.telicent, (+3 more)
org.eclipse.jetty.server  [fork: keep `org.eclipse.jetty`, `org.sonatype.nexus.common.components` still publishes the name]
  ? * org.eclipse.jetty                    2018-11..2026-05 12.0.35      |....================|
  ?   org.sonatype.nexus.common.components 2026-02..2026-05 3.92.3-01    |..................==|
  ?   com.yahoo.vespa                      2026-03..2026-05 8.683.24     |...................=|
  ?   org.sonatype.nexus.jetty             2025-09..2026-01 3.87.2-01    |.................==.|
  ?   ch.exense.step                       2024-01..2025-10 3.28.4       |..............=====.|
  ?   nl.goodbytes.xmpp.xep                2025-06..2025-06 2.0.0        |.................=..|
    + 5 more: io.github.nilwurtz, com.unblu.tools, io.nosqlbench, com.jnngl, de.acosix.alfresco.transform
org.eclipse.jetty.security  [fork: keep `org.eclipse.jetty`, `org.sonatype.nexus.common.components` still publishes the name]
  ? * org.eclipse.jetty                    2018-11..2026-05 12.0.35      |....================|
  ?   org.sonatype.nexus.common.components 2026-02..2026-05 3.92.3-01    |..................==|
  ?   org.sonatype.nexus.jetty             2025-09..2026-01 3.87.2-01    |.................==.|
com.dylibso.chicory.annotations  [fork: keep `com.dylibso.chicory`, `org.ngengine.chicory` still publishes the name]
  ? * com.dylibso.chicory                  2025-11..2026-03 1.7.5        |..................==|
  ?   org.ngengine.chicory                 2026-05..2026-05 1.7.5-nge2   |...................=|
com.dylibso.chicory.build.time.compiler  [fork: keep `com.dylibso.chicory`, `org.ngengine.chicory` still publishes the name]
  ? * com.dylibso.chicory                  2025-11..2026-03 1.7.5        |..................==|
  ?   org.ngengine.chicory                 2026-05..2026-05 1.7.5-nge2   |...................=|
com.dylibso.chicory.codegen  [fork: keep `com.dylibso.chicory`, `org.ngengine.chicory` still publishes the name]
  ? * com.dylibso.chicory                  2026-03..2026-03 1.7.5        |...................=|
  ?   org.ngengine.chicory                 2026-05..2026-05 1.7.5-nge2   |...................=|
com.dylibso.chicory.compiler  [fork: keep `com.dylibso.chicory`, `org.ngengine.chicory` still publishes the name]
  ? * com.dylibso.chicory                  2025-11..2026-03 1.7.5        |..................==|
  ?   org.ngengine.chicory                 2026-05..2026-05 1.7.5-nge2   |...................=|
com.dylibso.chicory.log  [fork: keep `com.dylibso.chicory`, `org.ngengine.chicory` still publishes the name]
  ? * com.dylibso.chicory                  2025-11..2026-03 1.7.5        |..................==|
  ?   org.ngengine.chicory                 2026-05..2026-05 1.7.5-nge2   |...................=|
com.dylibso.chicory.runtime  [fork: keep `com.dylibso.chicory`, `org.ngengine.chicory` still publishes the name]
  ? * com.dylibso.chicory                  2025-11..2026-03 1.7.5        |..................==|
  ?   org.ngengine.chicory                 2026-05..2026-05 1.7.5-nge2   |...................=|
com.dylibso.chicory.simd  [fork: keep `com.dylibso.chicory`, `org.ngengine.chicory` still publishes the name]
  ? * com.dylibso.chicory                  2025-11..2026-03 1.7.5        |..................==|
  ?   org.ngengine.chicory                 2026-05..2026-05 1.7.5-nge2   |...................=|
com.dylibso.chicory.wabt  [fork: keep `com.dylibso.chicory`, `org.ngengine.chicory` still publishes the name]
  ? * com.dylibso.chicory                  2025-11..2026-03 1.7.5        |..................==|
  ?   org.ngengine.chicory                 2026-05..2026-05 1.7.5-nge2   |...................=|
com.dylibso.chicory.wasi  [fork: keep `com.dylibso.chicory`, `org.ngengine.chicory` still publishes the name]
  ? * com.dylibso.chicory                  2025-11..2026-03 1.7.5        |..................==|
  ?   org.ngengine.chicory                 2026-05..2026-05 1.7.5-nge2   |...................=|
  ?   io.github.petomka                    2026-05..2026-05 2.0.0        |...................=|
  ?   io.github.fatihcatalkaya             2026-05..2026-05 2.1.0        |...................=|
com.dylibso.chicory.wasm  [fork: keep `com.dylibso.chicory`, `org.ngengine.chicory` still publishes the name]
  ? * com.dylibso.chicory                  2025-11..2026-03 1.7.5        |..................==|
  ?   org.ngengine.chicory                 2026-05..2026-05 1.7.5-nge2   |...................=|
com.swirlds.platform.core  [fork: keep `com.swirlds`, `com.hedera.hashgraph` still publishes the name]
  ? * com.swirlds                          2023-10..2025-06 0.62.11      |..............====..|
  ?   com.hedera.hashgraph                 2025-05..2026-05 0.75.0-rc.3  |.................===|
com.swirlds.virtualmap  [fork: keep `com.swirlds`, `com.hedera.hashgraph` still publishes the name]
  ? * com.swirlds                          2021-12..2025-06 0.62.11      |..........========..|
  ?   com.hedera.hashgraph                 2025-05..2026-05 0.75.0-rc.3  |.................===|
org.hiero.base.utility  [fork: keep `com.swirlds`, `com.hedera.hashgraph` still publishes the name]
  ? * com.swirlds                          2025-04..2025-06 0.62.11      |.................=..|
  ?   com.hedera.hashgraph                 2025-05..2026-05 0.75.0-rc.3  |.................===|
org.hiero.consensus.event.creator.impl  [fork: keep `com.swirlds`, `com.hedera.hashgraph` still publishes the name]
  ? * com.swirlds                          2025-04..2025-06 0.62.11      |.................=..|
  ?   com.hedera.hashgraph                 2025-05..2026-05 0.75.0-rc.3  |.................===|
com.swirlds.config.extensions  [fork: keep `com.swirlds`, `com.hedera.hashgraph` still publishes the name]
  ? * com.swirlds                          2023-12..2025-06 0.62.11      |..............====..|
  ?   com.hedera.hashgraph                 2025-05..2026-05 0.75.0-rc.3  |.................===|
com.swirlds.logging  [fork: keep `com.swirlds`, `com.hedera.hashgraph` still publishes the name]
  ? * com.swirlds                          2020-11..2025-06 0.62.11      |........==========..|
  ?   com.hedera.hashgraph                 2025-05..2026-05 0.75.0-rc.3  |.................===|
org.hiero.base.concurrent  [fork: keep `com.swirlds`, `com.hedera.hashgraph` still publishes the name]
  ? * com.swirlds                          2025-04..2025-06 0.62.11      |.................=..|
  ?   com.hedera.hashgraph                 2025-05..2026-05 0.75.0-rc.3  |.................===|
org.hiero.base.crypto  [fork: keep `com.swirlds`, `com.hedera.hashgraph` still publishes the name]
  ? * com.swirlds                          2025-04..2025-06 0.62.11      |.................=..|
  ?   com.hedera.hashgraph                 2025-05..2026-05 0.75.0-rc.3  |.................===|
org.hiero.consensus.event.creator  [fork: keep `com.swirlds`, `com.hedera.hashgraph` still publishes the name]
  ? * com.swirlds                          2025-04..2025-06 0.62.11      |.................=..|
  ?   com.hedera.hashgraph                 2025-05..2026-05 0.75.0-rc.3  |.................===|
com.swirlds.component.framework  [fork: keep `com.swirlds`, `com.hedera.hashgraph` still publishes the name]
  ? * com.swirlds                          2025-02..2025-06 0.62.11      |................==..|
  ?   com.hedera.hashgraph                 2025-05..2026-05 0.75.0-rc.3  |.................===|
com.swirlds.config.api  [fork: keep `com.swirlds`, `com.hedera.hashgraph` still publishes the name]
  ? * com.swirlds                          2023-10..2025-06 0.62.11      |..............====..|
  ?   com.hedera.hashgraph                 2025-05..2026-05 0.75.0-rc.3  |.................===|
com.swirlds.metrics.api  [fork: keep `com.swirlds`, `com.hedera.hashgraph` still publishes the name]
  ? * com.swirlds                          2024-02..2025-06 0.62.11      |..............====..|
  ?   com.hedera.hashgraph                 2025-05..2026-05 0.75.0-rc.3  |.................===|
com.swirlds.state.api  [fork: keep `com.swirlds`, `com.hedera.hashgraph` still publishes the name]
  ? * com.swirlds                          2024-06..2025-06 0.62.11      |...............===..|
  ?   com.hedera.hashgraph                 2025-05..2026-05 0.75.0-rc.3  |.................===|
com.swirlds.state.impl  [fork: keep `com.swirlds`, `com.hedera.hashgraph` still publishes the name]
  ? * com.swirlds                          2024-12..2025-06 0.62.11      |................==..|
  ?   com.hedera.hashgraph                 2025-05..2026-05 0.75.0-rc.3  |.................===|
org.hiero.consensus.gossip  [fork: keep `com.swirlds`, `com.hedera.hashgraph` still publishes the name]
  ? * com.swirlds                          2024-12..2025-06 0.62.11      |................==..|
  ?   com.hedera.hashgraph                 2025-05..2026-05 0.75.0-rc.3  |.................===|
org.hiero.consensus.gossip.impl  [fork: keep `com.swirlds`, `com.hedera.hashgraph` still publishes the name]
  ? * com.swirlds                          2024-12..2025-06 0.62.11      |................==..|
  ?   com.hedera.hashgraph                 2025-05..2026-05 0.75.0-rc.3  |.................===|
com.swirlds.base  [fork: keep `com.swirlds`, `com.hedera.hashgraph` still publishes the name]
  ? * com.swirlds                          2023-05..2025-06 0.62.11      |.............=====..|
  ?   com.hedera.hashgraph                 2025-05..2026-05 0.75.0-rc.3  |.................===|
com.swirlds.config.impl  [fork: keep `com.swirlds`, `com.hedera.hashgraph` still publishes the name]
  ? * com.swirlds                          2022-10..2025-06 0.62.11      |............======..|
  ?   com.hedera.hashgraph                 2025-05..2026-05 0.75.0-rc.3  |.................===|
com.swirlds.merkledb  [fork: keep `com.swirlds`, `com.hedera.hashgraph` still publishes the name]
  ? * com.swirlds                          2023-09..2025-06 0.62.11      |.............=====..|
  ?   com.hedera.hashgraph                 2025-05..2026-05 0.75.0-rc.3  |.................===|
com.swirlds.metrics.impl  [fork: keep `com.swirlds`, `com.hedera.hashgraph` still publishes the name]
  ? * com.swirlds                          2024-06..2025-06 0.62.11      |...............===..|
  ?   com.hedera.hashgraph                 2025-05..2026-05 0.75.0-rc.3  |.................===|
org.hiero.consensus.utility  [fork: keep `com.swirlds`, `com.hedera.hashgraph` still publishes the name]
  ? * com.swirlds                          2025-04..2025-06 0.62.11      |.................=..|
  ?   com.hedera.hashgraph                 2025-05..2026-05 0.75.0-rc.3  |.................===|
com.swirlds.common  [fork: keep `com.swirlds`, `com.hedera.hashgraph` still publishes the name]
  ? * com.swirlds                          2020-11..2025-06 0.62.11      |........==========..|
  ?   com.hedera.hashgraph                 2025-05..2026-05 0.75.0-rc.3  |.................===|
org.hiero.consensus.model  [fork: keep `com.swirlds`, `com.hedera.hashgraph` still publishes the name]
  ? * com.swirlds                          2025-04..2025-06 0.62.11      |.................=..|
  ?   com.hedera.hashgraph                 2025-05..2026-05 0.75.0-rc.3  |.................===|
org.apache.commons.csv  [fork: keep `io.github.pustike`, `org.sonarsource.scanner.engine` still publishes the name]
  ? * io.github.pustike                    2019-01..2019-07 1.7.0        |....==..............|
  ?   org.sonarsource.scanner.engine       2026-05..2026-05 12.36.0.3399 |...................=|
  ?   com.orientechnologies                2026-03..2026-05 3.2.52       |...................=|
  ?   com.guicedee.modules.services        2026-04..2026-05 2.0.2        |...................=|
  ?   org.apache.pinot                     2024-08..2026-04 1.5.0        |...............=====|
  ?   org.testingisdocumenting.znai        2026-01..2026-03 1.86         |..................=.|
    + 9 more: be.ugent.idlab.knows, io.kestra.plugin, xyz.ottr.lutra, org.jetbrains.kotlinx, com.wizzdi, io.telicent.jena.graphql, io.telicent.jena, org.apache.jena, com.guicedee.services
jakarta.validation  [fork: keep `jakarta.validation`, `dev.getelements.elements` still publishes the name]
  ? * jakarta.validation                   2020-02..2025-10 4.0.0-M1     |......=============.|
  ?   dev.getelements.elements             2025-03..2026-05 3.8.4        |.................===|
  ?   io.flux-capacitor                    2023-05..2024-06 0.943.0      |.............===....|
  ?   org.pipservices                      2024-06..2024-06 1.0.0        |...............=....|
  ?   no.nav.security                      2023-04..2023-11 3.2.0        |.............==.....|
  ?   com.neko233                          2023-01..2023-01 1.0.0        |............=.......|
    + 1 more: com.guicedee.services
io.netty.codec.http2  [fork: keep `io.netty`, `com.applitools` still publishes the name]
  ? * io.netty                             2017-12..2026-05 4.1.134.Final |..==================|
  ?   com.applitools                       2026-05..2026-05 5.87.2       |...................=|
  ?   org.apache.spark                     2025-10..2026-05 4.1.2        |..................==|
  ?   org.apache.iceberg                   2026-05..2026-05 1.11.0       |...................=|
  ?   org.eclipse.ditto                    2025-09..2026-05 3.9.0        |.................===|
  ?   io.github.jdbc-armour                2026-05..2026-05 0.2          |...................=|
    + 9 more: io.github.cstopyak, it.neckar.open, net.xdob.ratly, io.micronaut.testresources, com.exactpro.th2, io.etcd, io.kestra.storage, org.wiremock, io.github.sunny-chung
tools.jackson.core  [fork: keep `tools.jackson.core`, `io.github.ignf` still publishes the name]
  ? * tools.jackson.core                   2025-03..2026-05 3.1.3        |................====|
  ?   io.github.ignf                       2026-05..2026-05 2.0.0        |...................=|
  ?   software.xdev.mockserver             2026-03..2026-05 2.50.9       |...................=|
  ?   ru.tinkoff.piapi                     2026-01..2026-05 1.49         |..................==|
  ?   media.barney                         2026-05..2026-05 0.5.0        |...................=|
  ?   org.sonarsource.sonarlint.ls         2026-04..2026-04 5.2.1.78384  |...................=|
    + 16 more: io.github.tansuasici, com.limemojito.oss.standards, com.limemojito.oss.standards.development-test, io.sapl, com.io7m.montarre, io.github.shangtx, net.unit8.enkan, org.opentripplanner, io.github.anmol023, com.intrigsoft.prathya, tools.jackson.jr, fr.pilato.elasticsearch.injector, (+4 more)
org.graalvm.js  [fork: keep `org.graalvm.js`, `io.vertx` still publishes the name]
  ? * org.graalvm.js                       2018-10..2026-04 25.0.3       |...=================|
  ?   io.vertx                             2026-05..2026-05 5.1.0        |...................=|
  ?   org.hl7.fhir.publisher               2025-02..2026-05 2.2.8        |................====|
  ?   de.caluga                            2026-01..2026-05 6.2.4        |..................==|
  ?   com.walmartlabs.concord.runtime.v2   2021-09..2026-04 2.40.0       |.........===========|
  ?   com.walmartlabs.concord              2021-09..2026-04 2.40.0       |.........===========|
    + 13 more: com.walmartlabs.concord.k8s, com.walmartlabs.concord.runtime.v1, ca.ibodrov.concord, org.cibseven.bpm, dev.ybrig.ck8s.cli, org.noear, io.hyperfoil.tools, com.soartech, io.github.neofreko, org.graphwalker, io.nosqlbench, com.dbvis, (+1 more)
io.netty.codec.http3  [fork: keep `io.netty`, `com.limemojito.oss.standards` still publishes the name]
  ? * io.netty                             2025-06..2026-05 4.2.14.Final |.................===|
  ?   com.limemojito.oss.standards         2026-04..2026-05 17.0.24      |...................=|
  ?   com.limemojito.oss.standards.development-test 2026-04..2026-05 17.0.24      |...................=|
  ?   org.red5                             2025-11..2025-11 0.3.0        |..................=.|
org.htmlunit.xpath  [fork: keep `org.htmlunit`, `com.nordstrom.ui-tools` still publishes the name]
  ? * org.htmlunit                         2026-05..2026-05 5.0.1        |...................=|
  ?   com.nordstrom.ui-tools               2026-05..2026-05 4.44.0       |...................=|
org.mariadb.jdbc  [fork: keep `org.mariadb.jdbc`, `com.system32dev` still publishes the name]
  ? * org.mariadb.jdbc                     2018-02..2026-04 3.5.8        |..==================|
  ?   com.system32dev                      2025-12..2026-05 2.2.1        |..................==|
  ?   org.metricshub                       2026-01..2026-05 3.9.04       |..................==|
  ?   io.kestra.plugin                     2025-02..2025-10 0.24.2       |................===.|
  ?   org.opencastproject                  2022-06..2025-04 16.10        |...........=======..|
  ?   io.zipkin.dependencies               2023-12..2024-04 3.1.5        |..............==....|
    + 1 more: com.microsoft.azure
kotlinx.serialization.core  [fork: keep `org.jetbrains.kotlinx`, `dev.sebastiano.spectre` still publishes the name]
  ? * org.jetbrains.kotlinx                2021-09..2026-04 1.11.0       |.........===========|
  ?   dev.sebastiano.spectre               2026-05..2026-05 0.2.0        |...................=|
  ?   dev.robocode.tankroyale              2026-01..2026-05 1.0.2        |..................==|
  ?   io.github.wangbax                    2026-04..2026-04 5.5.1-okio-fork-2 |...................=|
  ?   love.forte.plugin.suspend-transform  2025-04..2026-04 2.3.20-0.13.2 |.................===|
  ?   com.squareup.wire                    2024-04..2026-03 5.5.1        |...............====.|
    + 22 more: org.ldemetrios, io.github.lumamontes, dev.zacsweers.metro, io.typst, io.availe, dev.oglass, io.github.oewntk, io.github.lexa-diky, com.toasttab.expediter, io.johnsonlee.exec, io.specmatic, io.portone, (+10 more)
io.swagger.v3.jaxrs2  [fork: keep `io.swagger.core.v3`, `io.github.vpelikh` still publishes the name]
  ? * io.swagger.core.v3                   2019-04..2026-05 2.2.50       |.....===============|
  ?   io.github.vpelikh                    2026-05..2026-05 3.0.0-M8     |...................=|
  ?   io.github.lisi9988                   2025-08..2025-12 2.2.41       |.................==.|
  ?   com.github.krraghavan                2020-08..2020-08 2.0.8.1-RELEASE |.......=............|
io.swagger.v3.jaxrs2.integration  [fork: keep `io.swagger.core.v3`, `io.github.vpelikh` still publishes the name]
  ? * io.swagger.core.v3                   2019-04..2026-05 2.2.50       |.....===============|
  ?   io.github.vpelikh                    2026-05..2026-05 3.0.0-M8     |...................=|
  ?   io.github.lisi9988                   2025-08..2025-12 2.2.41       |.................==.|
  ?   com.github.krraghavan                2020-08..2020-08 2.0.8.1-RELEASE |.......=............|
io.swagger.v3.oas.annotations  [fork: keep `io.swagger.core.v3`, `io.github.vpelikh` still publishes the name]
  ? * io.swagger.core.v3                   2019-04..2026-05 2.2.50       |.....===============|
  ?   io.github.vpelikh                    2026-05..2026-05 3.0.0-M8     |...................=|
  ?   io.github.lisi9988                   2025-08..2025-12 2.2.41       |.................==.|
  ?   com.github.krraghavan                2020-08..2020-08 2.0.8.1-RELEASE |.......=............|
io.swagger.v3.oas.models  [fork: keep `io.swagger.core.v3`, `io.github.vpelikh` still publishes the name]
  ? * io.swagger.core.v3                   2019-04..2026-05 2.2.50       |.....===============|
  ?   io.github.vpelikh                    2026-05..2026-05 3.0.0-M8     |...................=|
  ?   io.github.lisi9988                   2025-08..2025-12 2.2.41       |.................==.|
  ?   io.github.amayaframework             2024-10..2024-10 2.2.25       |................=...|
  ?   com.github.krraghavan                2020-08..2020-08 2.0.8.1-RELEASE |.......=............|
io.swagger.v3.core  [fork: keep `io.swagger.core.v3`, `io.github.vpelikh` still publishes the name]
  ? * io.swagger.core.v3                   2019-04..2026-05 2.2.50       |.....===============|
  ?   io.github.vpelikh                    2026-05..2026-05 3.0.0-M8     |...................=|
  ?   io.github.lisi9988                   2025-08..2025-12 2.2.41       |.................==.|
  ?   com.github.krraghavan                2020-08..2020-08 2.0.8.1-RELEASE |.......=............|
io.swagger.v3.jaxrs2.integration.servlet  [fork: keep `io.swagger.core.v3`, `io.github.vpelikh` still publishes the name]
  ? * io.swagger.core.v3                   2020-04..2026-05 2.2.50       |......==============|
  ?   io.github.vpelikh                    2026-05..2026-05 3.0.0-M8     |...................=|
  ?   io.github.lisi9988                   2025-08..2025-12 2.2.41       |.................==.|
io.swagger.v3.oas.integration  [fork: keep `io.swagger.core.v3`, `io.github.vpelikh` still publishes the name]
  ? * io.swagger.core.v3                   2019-04..2026-05 2.2.50       |.....===============|
  ?   io.github.vpelikh                    2026-05..2026-05 3.0.0-M8     |...................=|
  ?   io.github.lisi9988                   2025-08..2025-12 2.2.41       |.................==.|
  ?   com.github.krraghavan                2020-08..2020-08 2.0.8.1-RELEASE |.......=............|
org.apache.commons.cli  [fork: keep `org.apache.shiro.tools`, `org.teavm` still publishes the name]
  ? * org.apache.shiro.tools               2023-10..2023-10 1.13.0       |..............=.....|
  ?   org.teavm                            2024-04..2026-05 0.14.1       |...............=====|
  ?   io.github.vdaburon                   2024-01..2026-05 5.1          |..............======|
  ?   org.apktool                          2023-12..2026-04 3.0.2        |..............======|
  ?   com.ericsson.bss.cassandra.ecaudit   2024-08..2026-03 3.1.5        |...............=====|
  ?   org.imixs.bpmn                       2025-05..2026-03 1.2.9        |.................===|
    + 23 more: io.github.gvergine, com.amazonaws, io.github.706412584, com.legsem.legstar, dev.walgo, org.apache.phoenix.thirdparty, org.apache.meecrowave, org.apache.james, net.thisptr, us.poliscore, com.github.oboehm, org.apache.hbase, (+11 more)
org.objectweb.asm.util  [fork: keep `org.ow2.asm`, `org.teavm` still publishes the name]
  ? * org.ow2.asm                          2017-07..2026-05 9.10.1       |.===================|
  ?   org.teavm                            2023-03..2026-05 0.14.1       |............========|
  ?   io.jactl                             2023-04..2026-05 2.8.0        |.............=======|
  ?   io.camunda                           2025-09..2026-05 8.9.5        |.................===|
  ?   com.vmlens                           2025-05..2026-04 1.2.28       |.................===|
  ?   com.webforj                          2025-02..2025-05 25.01        |................==..|
    + 13 more: io.joynr.tools.generator, io.joern, com.rookout, io.btrace, com.codacy, org.soot-oss, org.activecomponents.jadex, com.github.luxlang, org.geneweaver, com.github.pxav.kelp, net.amygdalum, org.python, (+1 more)
com.ctc.wstx  [fork: keep `com.fasterxml.woodstox`, `org.uma.jmetal` still publishes the name]
  ? * com.fasterxml.woodstox               2018-03..2026-05 7.2.0        |..==================|
  ?   org.uma.jmetal                       2025-12..2026-05 7.3          |..................==|
  ?   org.bidib.jbidib                     2021-12..2026-05 2.0.44       |..........==========|
  ?   gov.nih.ncats                        2022-01..2026-03 1.0.26       |..........==========|
  ?   org.hpccsystems                      2022-02..2026-03 9.12.94-1    |..........==========|
  ?   com.backpackcloud                    2025-03..2026-01 2.1.0        |.................==.|
    + 17 more: com.liferay.portal, de.fraunhofer.iosb.ilt.FROST-Server, com.ibm.jsonata4java, se.signatureservice.support, com.liferay, net.pincette, org.opengis.cite, org.immregistries, com.testdroid, org.sonarsource.slang, com.checkmarx, com.github.spoonlabs, (+5 more)
org.seleniumhq.selenium.java  [fork: keep `org.seleniumhq.selenium`, `com.salesforce.utam` still publishes the name]
  ? * org.seleniumhq.selenium              2019-09..2026-05 4.44.0       |.....===============|
  ?   com.salesforce.utam                  2024-06..2026-05 4.0.6        |...............=====|
  ?   io.github.vishalmysore               2024-04..2026-01 1.1.9        |...............====.|
  ?   io.github.lambdatest                 2023-12..2026-01 1.0.3        |..............=====.|
  ?   com.testmonitor                      2025-07..2025-09 1.0.0        |.................==.|
  ?   ie.curiositysoftware                 2025-02..2025-02 2.1.0        |................=...|
    + 8 more: com.github.marketsquare, com.smartbear, com.qcefast, tech.catheu, io.github.multicatch, io.github.ndviet, uk.co.spicule, net.mamoe
com.google.errorprone.annotations  [fork: keep `com.google.errorprone`, `eu.rssw.sonar.openedge` still publishes the name]
  ? * com.google.errorprone                2019-12..2026-04 2.49.0       |......==============|
  ?   eu.rssw.sonar.openedge               2024-11..2026-05 3.8.0        |................====|
  ?   com.salesforce.multicloudj           2026-04..2026-05 0.3.5        |...................=|
  ?   com.google.appengine                 2024-03..2026-05 5.0.2        |...............=====|
  ?   org.checkerframework                 2024-10..2026-05 4.1.0        |................====|
  ?   com.google.turbine                   2024-08..2026-04 1.15.0       |...............=====|
    + 52 more: io.okdp, com.clickzetta, com.facebook.presto, io.zipkin.zipkin2, com.google.cloud, org.apache.spark, de.enflexit.awbAssist, com.palantir.hadoop-crypto2, org.noear, org.foundationdb, com.google.cloud.spark.bigtable, com.contentful.java, (+40 more)
org.apache.wicket.core  [fork: keep `org.apache.wicket`, `org.wicketstuff` still publishes the name]
  ? * org.apache.wicket                    2019-04..2026-05 10.9.1       |....================|
  ?   org.wicketstuff                      2021-09..2026-05 10.9.1       |.........===========|
org.lwjgl  [fork: keep `org.lwjgl`, `io.github.lego-eden` still publishes the name]
  ? * org.lwjgl                            2017-09..2026-02 3.4.1        |.==================.|
  ?   io.github.lego-eden                  2025-10..2026-05 0.2.3        |..................==|
  ?   com.io7m.jcoronado                   2024-12..2026-04 1.0.0-beta0005 |................====|
  ?   org.collebol                         2025-01..2025-11 0.2.8        |................===.|
  ?   io.github.ranchordo                  2022-02..2022-02 1.0.2        |..........=.........|
  ?   org.lwjgl.osgi                       2018-12..2021-12 3.3.0        |....=======.........|
org.jboss.logging  [fork: keep `org.jboss.logging`, `org.tinystruct` still publishes the name]
  ? * org.jboss.logging                    2018-02..2026-03 3.6.3.Final  |..==================|
  ?   org.tinystruct                       2026-05..2026-05 1.0.6        |...................=|
  ?   org.jboss.ironjacamar                2026-05..2026-05 1.5.26.Final |...................=|
  ?   io.appform.ranger                    2025-11..2026-01 2.0.0-RC5    |..................=.|
  ?   io.quarkus                           2024-09..2025-09 3.15.7       |...............====.|
  ?   be.vlaanderen.informatievlaanderen.ldes.ldio 2024-12..2024-12 2.12.0       |................=...|
    + 4 more: io.mvnpm, ch.nerdin, com.abavilla, com.guicedee.services
io.opentelemetry.javaagent  [fork: keep `io.opentelemetry.javaagent`, `com.vaadin` still publishes the name]
  ? * io.opentelemetry.javaagent           2023-10..2026-05 2.28.1-alpha |..............======|
  ?   com.vaadin                           2024-12..2026-05 4.0.1        |................====|
  ?   dev.agenttel                         2026-03..2026-03 0.3.0-alpha  |..................==|
com.github.luben.zstd_jni  [fork: keep `com.github.luben`, `ai.h2o` still publishes the name]
  ? * com.github.luben                     2018-06..2026-05 1.5.7-9      |...=================|
  ?   ai.h2o                               2024-05..2026-05 3.46.0.11    |...............=====|
  ?   com.aliyun.openservices.eas          2024-06..2026-05 2.0.30       |...............=====|
  ?   com.timecho.iotdb                    2025-04..2026-05 2.0.9.2      |.................===|
  ?   org.apache.tsfile                    2024-11..2026-04 2.3.0        |................====|
  ?   org.chipsalliance                    2024-02..2026-04 2.1.1        |..............======|
    + 12 more: org.apache.celeborn, org.apache.iotdb, io.moderne, io.spicelabs, io.nosqlbench, io.github.willena, com.snowflake, io.github.fernandolopes, org.apache.amoro, io.kroxylicious, io.github.azagniotov, com.guicedee.services
org.tukaani.xz  [fork: keep `org.tukaani`, `org.apache.syncope.fit` still publishes the name]
  ? * org.tukaani                          2018-01..2026-03 1.12         |..=================.|
  ?   org.apache.syncope.fit               2025-11..2026-05 4.0.6        |..................==|
  ?   de.m3y.parquet                       2025-01..2026-05 1.17.1       |................====|
  ?   org.sonarsource.javascript           2023-09..2026-05 12.5.0.41048 |..............======|
  ?   io.anserini                          2022-01..2026-05 2.1.1        |..........==========|
  ?   com.timecho.iotdb                    2024-04..2026-05 2.0.9.2      |...............=====|
    + 21 more: com.sonatype.clm, net.neoforged.installertools, org.apache.iotdb, org.incenp, io.archivesunleashed, org.apache.parquet, io.github.seabow, org.apache.inlong, io.kestra.plugin, com.github.samtools, org.finos.morphir, dev.3-3, (+9 more)
org.jfree.chart  [fork: keep `de.enflexit`, `io.github.jiaweim` still publishes the name]
  ? * de.enflexit                          2025-02..2025-02 1.5.6        |................=...|
  ?   io.github.jiaweim                    2026-05..2026-05 2.0.0        |...................=|
org.jgrapht.core  [fork: keep `org.jgrapht`, `org.choco-solver` still publishes the name]
  ? * org.jgrapht                          2018-05..2026-04 1.5.3        |...=================|
  ?   org.choco-solver                     2026-05..2026-05 6.0.1        |...................=|
  ?   fr.inria.gforge.spoon.labs           2026-02..2026-04 1.124        |..................==|
  ?   io.github.davincilll                 2025-11..2025-11 1.0.0        |..................=.|
  ?   fr.insee.trevas                      2025-09..2025-09 2.0.0        |..................=.|
  ?   de.athalis.coreasm                   2025-08..2025-08 1.7.3-locke-7 |.................=..|
    + 9 more: io.github.jodavimehran, de.redsix, io.acryl, com.squareup.sqldelight, net.mbonnin.sqldelight, fr.lirmm.fca4j, io.github.bissim, it.unibo.alchemist, com.io7m.jgrapht
io.netty.handler  [fork: keep `io.netty`, `eu.michael-simons.neo4j` still publishes the name]
  ? * io.netty                             2017-12..2026-05 4.1.134.Final |..==================|
  ?   eu.michael-simons.neo4j              2025-07..2026-05 4.0.2        |.................===|
  ?   org.apache.tika                      2025-04..2026-05 3.3.1        |.................===|
  ?   org.apache.storm                     2025-05..2026-05 2.8.8        |.................===|
  ?   io.github.ousatov-ua                 2026-04..2026-04 5.0.3        |...................=|
  ?   org.apache.flink                     2026-01..2026-01 3.8.5-21.0   |..................=.|
    + 3 more: io.kestra.plugin, org.lucee, com.luhuiguo.netty
org.bytedeco.javacpp.ios.arm64  [fork: keep `org.bytedeco`, `org.apache.tika` still publishes the name]
  ? * org.bytedeco                         2020-04..2026-02 1.5.13       |......=============.|
  ?   org.apache.tika                      2025-08..2026-05 3.3.1        |.................===|
org.junit.jupiter.engine  [fork: keep `org.junit.jupiter`, `org.apache.tika` still publishes the name]
  ? * org.junit.jupiter                    2017-07..2026-05 6.1.0        |.===================|
  ?   org.apache.tika                      2024-07..2026-05 3.3.1        |...............=====|
  ?   com.janeluo                          2026-04..2026-04 1.0.0        |...................=|
  ?   org.eclipse.pass                     2023-06..2024-01 1.3.0        |.............==.....|
  ?   com.quantego                         2023-11..2023-11 0.6.5        |..............=.....|
  ?   com.salesforce.kafka.test            2020-02..2023-02 3.2.5        |......=======.......|
    + 5 more: com.trendyol, org.caseine, org.eclipse.rdf4j, com.github.jnr, org.junit.platform
com.headius.invokebinder  [fork: keep `com.headius`, `org.sahli.asciidoc.confluence.publisher` still publishes the name]
  ? * com.headius                          2017-10..2025-10 1.15         |.==================.|
  ?   org.sahli.asciidoc.confluence.publisher 2026-03..2026-05 0.32.0       |...................=|
  ?   org.springframework.cloud            2024-11..2026-04 4.3.3        |................====|
  ?   com.lealceldeiro                     2025-02..2026-03 2.3.1        |................====|
  ?   ch.ifocusit.livingdoc                2025-05..2025-05 2.16         |.................=..|
  ?   de.jcup.asp                          2021-09..2022-04 1.4.1        |.........===........|
    + 1 more: org.asciidoctor
org.bouncycastle.lts.prov  [fork: keep `org.bouncycastle`, `org.brylex` still publishes the name]
  ? * org.bouncycastle                     2023-06..2026-05 2.73.11      |.............=======|
  ?   org.brylex                           2026-05..2026-05 0.2          |...................=|
  ?   io.kestra.plugin                     2025-06..2025-08 0.24.0       |.................=..|
org.mozilla.rhino  [fork: keep `org.mozilla`, `org.mock-server` still publishes the name]
  ? * org.mozilla                          2021-11..2026-02 1.9.1        |..........=========.|
  ?   org.mock-server                      2026-05..2026-05 6.0.0        |...................=|
  ?   org.bidib.com.github.markusbernhardt 2026-02..2026-02 2.0.0        |..................=.|
  ?   org.meeuw                            2025-12..2025-12 1.0          |..................=.|
netty.socketio.core  [fork: keep `io.github.neatguycoding`, `com.socketio4j` still publishes the name]
  ? * io.github.neatguycoding              2025-10..2025-11 3.0.1        |..................=.|
  ?   com.socketio4j                       2025-11..2026-05 4.0.1        |..................==|
netty.socketio.spring  [fork: keep `io.github.neatguycoding`, `com.socketio4j` still publishes the name]
  ? * io.github.neatguycoding              2025-10..2025-11 3.0.1        |..................=.|
  ?   com.socketio4j                       2025-11..2026-05 4.0.1        |..................==|
io.smallrye.common.net  [fork: keep `io.smallrye.common`, `io.quarkus` still publishes the name]
  ? * io.smallrye.common                   2022-05..2026-05 2.18.1       |...........=========|
  ?   io.quarkus                           2025-04..2026-05 3.36.0       |.................===|
io.smallrye.common.ref  [fork: keep `io.smallrye.common`, `io.quarkus` still publishes the name]
  ? * io.smallrye.common                   2022-05..2026-05 2.18.1       |...........=========|
  ?   io.quarkus                           2025-02..2026-05 3.27.4       |................====|
com.sun.jna  [fork: keep `net.java.dev.jna`, `com.blazemeter.jmeter` still publishes the name]
  ? * net.java.dev.jna                     2018-10..2025-09 5.18.1       |...================.|
  ?   com.blazemeter.jmeter                2026-05..2026-05 3.0.1        |...................=|
  ?   com.guicedee.modules.services        2026-04..2026-05 2.0.2        |...................=|
  ?   org.glavo.hmcl                       2023-09..2023-09 5.13.0-rc1-linux-loongarch64_ow |.............=......|
  ?   one.gfw                              2023-03..2023-03 5.13.0       |............=.......|
  ?   com.guicedee.services                2021-03..2022-02 1.2.2.1-jre17 |........===.........|
    + 1 more: org.elasticsearch
org.eclipse.jetty.util  [fork: keep `org.eclipse.jetty`, `com.google.appengine` still publishes the name]
  ? * org.eclipse.jetty                    2018-11..2026-05 12.0.35      |....================|
  ?   com.google.appengine                 2026-04..2026-05 5.0.3-beta   |...................=|
  ?   com.yahoo.vespa                      2025-09..2025-11 8.604.22     |.................==.|
net.dv8tion.jda  [fork: keep `net.dv8tion`, `net.astr4y` still publishes the name]
  ? * net.dv8tion                          2021-11..2026-04 6.4.1        |..........==========|
  ?   net.astr4y                           2026-02..2026-05 0.0.4-alpha_DEV |..................==|
lombok  [fork: keep `org.projectlombok`, `io.mosip.esignet.plugin.sunbirdrc` still publishes the name]
  ? * org.projectlombok                    2018-05..2026-04 1.18.46      |...=================|
  ?   io.mosip.esignet.plugin.sunbirdrc    2025-02..2026-05 1.4.0        |................====|
  ?   org.eclipse.hawkbit                  2026-03..2026-04 1.0.3        |...................=|
  ?   io.inji.certify.sunbirdrc            2026-03..2026-03 0.6.0        |...................=|
  ?   dev.alllexey                         2025-10..2026-03 1.5.0        |..................==|
  ?   net.polyv                            2020-09..2026-03 2.2.8        |.......============.|
    + 97 more: com.scanoss, com.huaweicloud.dws, net.wirelabs, cn.fyupeng, io.github.alllexey123, io.mosip.esignet.sunbirdrc, io.mosip.certify.sunbirdrc, io.github.opentelekomcloud, io.github.version-pulse, org.qubership.automation, io.github.devlibx.easy, org.sentrysoftware, (+85 more)
tools.jackson.databind  [fork: keep `tools.jackson.core`, `com.smartystreets.api` still publishes the name]
  ? * tools.jackson.core                   2025-03..2026-05 3.1.3        |................====|
  ?   com.smartystreets.api                2026-01..2026-05 6.2.0        |..................==|
  ?   software.xdev.mockserver             2026-03..2026-03 2.0.4        |..................==|
  ?   io.github.koinsaari                  2025-11..2025-11 0.1.0        |..................=.|
owasp.encoder  [fork: keep `org.owasp.encoder`, `com.adobe.cq` still publishes the name]
  ? * org.owasp.encoder                    2024-08..2025-11 1.4.0        |...............====.|
  ?   com.adobe.cq                         2026-05..2026-05 2.31.0       |...................=|
com.formdev.flatlaf  [fork: keep `com.formdev`, `dev.robocode.tankroyale` still publishes the name]
  ? * com.formdev                          2019-10..2026-03 3.7.1        |.....==============.|
  ?   dev.robocode.tankroyale              2026-05..2026-05 1.0.2        |...................=|
  ?   ca.corbett                           2025-05..2026-04 2.9.0        |.................===|
  ?   de.florianreuth                      2026-02..2026-02 2.2.0        |..................=.|
  ?   de.florianmichael                    2025-11..2025-12 2.1.1        |..................=.|
  ?   com.suckatcoding                     2025-11..2025-12 1.23.0       |..................=.|
    + 2 more: io.github.harvardpl, com.github.sundev79.MineBootFramework
org.apache.lucene.suggest  [fork: keep `org.apache.lucene`, `org.opensearch.migrations.snapshots` still publishes the name]
  ? * org.apache.lucene                    2021-12..2026-02 10.4.0       |..........=========.|
  ?   org.opensearch.migrations.snapshots  2026-04..2026-05 0.3.2.1      |...................=|
  ?   org.geckoprojects.libraries          2024-12..2024-12 9.12.0       |................=...|
  ?   org.geckoprojects.search             2024-09..2024-09 9.11.1       |...............=....|
liqp  [fork: keep `nl.big-o`, `io.github.luoxuansz` still publishes the name]
  ? * nl.big-o                             2018-06..2025-04 0.9.2.3      |...===============..|
  ?   io.github.luoxuansz                  2025-11..2026-05 1.1.3        |..................==|
  ?   com.kohlschutter                     2023-12..2023-12 0.8.5.4      |..............=.....|
kotlin.stdlib.jdk7  [fork: keep `org.jetbrains.kotlin`, `io.github.team-sneakymouse` still publishes the name]
  ? * org.jetbrains.kotlin                 2019-01..2023-08 1.9.10       |....==========......|
  ?   io.github.team-sneakymouse           2026-05..2026-05 4.0-Beta-13  |...................=|
  ?   io.pyroscope                         2025-04..2026-03 2.5.2        |.................===|
  ?   com.seanshubin.code.structure        2026-02..2026-02 1.1.1        |..................=.|
  ?   me.xcue                              2026-01..2026-01 26.0.1       |..................=.|
  ?   com.kroegerama.openapi-kgen          2023-12..2025-09 0.18.1       |..............=====.|
    + 14 more: org.partiql, io.github.wadoon.key, org.btmonier, com.slothiesmooth, com.slothiesmooth.links-detektor, hu.bme.mit.theta, com.github.shynixn.mccoroutine, dev.nelmin.spigot, com.facebook, com.cjcrafter, tech.carcadex, io.pixeloutlaw.mythicdrops, (+2 more)
jakarta.xml.bind  [fork: keep `jakarta.xml.bind`, `com.nomalab` still publishes the name]
  ? * jakarta.xml.bind                     2020-02..2026-05 4.1.0-M1     |......==============|
  ?   com.nomalab                          2025-04..2026-05 1.4.1        |.................===|
  ?   com.github.bld-commons               2026-02..2026-05 3.0.19       |..................==|
  ?   io.dscope.camel                      2026-05..2026-05 1.1.0        |...................=|
  ?   gov.nasa.pds                         2025-11..2026-04 3.1.0        |..................==|
  ?   org.openfolder                       2025-04..2026-04 3.2.2        |.................===|
    + 28 more: ch.exense.step, org.openpreservation.odf, org.chenile, com.bandwidth.sdk, mx.com.sw.services, pro.verron.office-stamper, com.helger.schematron, com.orientechnologies, com.intuit.quickbooks-online, net.anotheria, io.github.rzo1.org.sweble.wikitext, com.testdroid, (+16 more)
com.datastax.oss.driver.querybuilder  [fork: keep `com.datastax.oss`, `com.scylladb` still publishes the name]
  ? * com.datastax.oss                     2020-04..2023-06 4.16.0       |.......=======......|
  ?   com.scylladb                         2020-08..2026-05 4.19.0.9     |.......=============|
  ?   com.yugabyte                         2020-06..2025-03 4.15.0-yb-3  |.......==========...|
com.datastax.oss.driver.core  [fork: keep `com.datastax.oss`, `com.scylladb` still publishes the name]
  ? * com.datastax.oss                     2020-04..2023-07 4.17.0       |.......=======......|
  ?   com.scylladb                         2020-08..2026-05 4.19.0.9     |.......=============|
  ?   org.apache.cassandra                 2023-12..2025-11 4.19.2       |..............=====.|
  ?   com.yugabyte                         2020-06..2025-05 4.19.0-yb-1  |.......===========..|
com.datastax.oss.driver.mapper.processor  [fork: keep `com.datastax.oss`, `com.scylladb` still publishes the name]
  ? * com.datastax.oss                     2020-04..2023-07 4.17.0       |.......=======......|
  ?   com.scylladb                         2020-08..2026-05 4.19.0.9     |.......=============|
  ?   org.apache.cassandra                 2023-12..2025-11 4.19.2       |..............=====.|
  ?   com.yugabyte                         2020-06..2025-05 4.19.0-yb-1  |.......===========..|
com.datastax.oss.driver.mapper.runtime  [fork: keep `com.datastax.oss`, `com.scylladb` still publishes the name]
  ? * com.datastax.oss                     2020-04..2023-06 4.16.0       |.......=======......|
  ?   com.scylladb                         2020-08..2026-05 4.19.0.9     |.......=============|
  ?   com.yugabyte                         2020-06..2025-03 4.15.0-yb-3  |.......==========...|
com.datastax.oss.driver.tests.infrastructure  [fork: keep `com.datastax.oss`, `com.scylladb` still publishes the name]
  ? * com.datastax.oss                     2020-04..2023-06 4.16.0       |.......=======......|
  ?   com.scylladb                         2020-08..2026-05 4.19.0.9     |.......=============|
  ?   com.yugabyte                         2020-06..2025-03 4.15.0-yb-3  |.......==========...|
org.checkerframework.checker.qual  [fork: keep `org.checkerframework`, `io.github.imonja` still publishes the name]
  ? * org.checkerframework                 2018-08..2026-05 4.1.0        |...=================|
  ?   io.github.imonja                     2025-07..2026-05 1.4.0        |.................===|
  ?   io.joynr.java.core                   2026-01..2026-05 1.26.5       |..................==|
  ?   com.google.cloud                     2024-06..2026-05 3.42.0       |...............=====|
  ?   com.daml                             2024-09..2026-05 2.10.4-snapshot.20260507.13205.0.v9e332c6a |................====|
  ?   io.boxlang                           2026-01..2026-05 1.13.0       |..................==|
    + 27 more: com.webforj, io.github.eisop, com.facebook.business.sdk, org.eclipse.hawkbit, org.jetbrains.kotlinx, com.jcabi, org.opencastproject, org.orbisgis.geoclimate, io.github.mscheong01, io.vitess, org.apache.pekko, org.hyperledger.fabric, (+15 more)
com.oracle.truffle.tools.profiler  [fork: keep `org.graalvm.tools`, `com.orientechnologies` still publishes the name]
  ? * org.graalvm.tools                    2018-10..2026-04 25.0.3       |...=================|
  ?   com.orientechnologies                2025-12..2026-05 3.2.52       |..................==|
com.fasterxml.classmate  [fork: keep `com.fasterxml`, `com.qainsights` still publishes the name]
  ? * com.fasterxml                        2017-09..2026-01 1.7.3        |.==================.|
  ?   com.qainsights                       2026-05..2026-05 2.0.7        |...................=|
  ?   org.kill-bill.billing                2020-07..2026-05 0.41.21      |.......=============|
  ?   org.wso2.msf4j.sample                2026-05..2026-05 2.9.1        |...................=|
  ?   io.appform.ranger                    2024-11..2026-05 1.1.3-ID-V2-RC1 |................====|
  ?   org.jboss.ironjacamar                2021-12..2026-04 1.5.25.Final |..........==========|
    + 15 more: com.jcabi, org.kill-bill.billing.plugin.java, com.biit-solutions.drools.plugins, com.enos-iot, com.phonepe.drove, com.github.bld-commons, com.twitter, org.liquibase.ext, org.duracloud, cc.shacocloud, org.open-metadata, cc.shacocloud.octopus, (+3 more)
org.apache.logging.log4j.core  [fork: keep `org.apache.logging.log4j`, `app.freerouting` still publishes the name]
  ? * org.apache.logging.log4j             2017-11..2026-05 2.26.0       |..==================|
  ?   app.freerouting                      2026-05..2026-05 2.2.4        |...................=|
  ?   org.beilstein                        2026-05..2026-05 1.1.2        |...................=|
  ?   me.bechberger                        2026-03..2026-04 0.1.6        |...................=|
  ?   io.openems                           2026-02..2026-04 3.3.0-openems.2 |..................==|
  ?   com.kount                            2025-03..2026-03 9.0.5        |.................==.|
    + 18 more: com.github.aquality-automation, com.ghgande, com.github.bilderherunterlader, com.gemecosystem.gemjar, org.lucee, io.github.alien-tools, com.webforj, io.github.egonw, com.liferay, de.fraunhofer.iem, net.maizegenetics, org.meeuw, (+6 more)
org.jooq  [fork: keep `org.jooq`, `org.kill-bill.billing` still publishes the name]
  ? * org.jooq                             2017-09..2026-05 3.21.4       |.===================|
  ?   org.kill-bill.billing                2022-12..2026-05 0.41.21      |............========|
  ?   org.kill-bill.billing.plugin.java    2023-01..2025-08 1.0.2        |............======..|
  ?   co.ntbl.dropwizard                   2023-04..2023-04 100.400.3183 |.............=......|
  ?   org.kill-bill.billing.plugin.java.catalog 2023-01..2023-01 0.5.0        |............=.......|
org.codehaus.stax2  [fork: keep `org.codehaus.woodstox`, `org.apache.phoenix` still publishes the name]
  ? * org.codehaus.woodstox                2018-03..2026-03 4.3.0        |..==================|
  ?   org.apache.phoenix                   2024-04..2026-05 5.3.1        |...............=====|
  ?   org.hpccsystems                      2023-11..2026-04 10.4.0-1     |..............======|
  ?   io.indextables                       2026-01..2026-04 0.5.5_spark_3.5.3 |..................==|
  ?   org.apache.jackrabbit                2025-04..2026-02 1.92.0       |.................==.|
  ?   io.github.jeff-tian                  2026-02..2026-02 2.4.1        |..................=.|
    + 10 more: com.ibm.jsonata4java, com.graphhopper, io.github.tmgg, org.apache.tez, de.wenzlaff.linkchecker, com.onelogin, com.microsoft.azure, com.exasol, io.github.no-dumps, com.github.zuinnote
org.eclipse.angus.activation  [fork: keep `org.eclipse.angus`, `fish.payara.extras` still publishes the name]
  ? * org.eclipse.angus                    2023-01..2025-09 2.0.3        |............======..|
  ?   fish.payara.extras                   2024-11..2026-05 7.2026.5     |................====|
  ?   com.inteligr8.activiti               2025-12..2025-12 1.3.0-aps-v25.3 |..................=.|
  ?   org.apache.james                     2025-09..2025-09 3.9.0        |..................=.|
  ?   io.github.rxue                       2024-08..2024-09 1.7.4        |...............=....|
  ?   one.gfw                              2023-03..2023-03 2.0.0        |............=.......|
com.sshtools.common.logger  [fork: keep `com.sshtools`, `solutions.a2.oracle` still publishes the name]
  ? * com.sshtools                         2024-02..2025-11 3.1.4        |..............=====.|
  ?   solutions.a2.oracle                  2025-12..2026-05 2.15.2       |..................==|
org.eclipse.osgi  [fork: keep `org.eclipse.tycho`, `io.joynr.tools.generator` still publishes the name]
  ? * org.eclipse.tycho                    2018-05..2018-05 3.13.0.v20180226-1711 |...=................|
  ?   io.joynr.tools.generator             2025-01..2026-05 1.26.5       |................====|
  ?   org.alfasoftware                     2021-05..2026-04 2.7.0        |.........===========|
  ?   net.kieker-monitoring                2026-04..2026-04 2.0.3        |...................=|
  ?   org.bonitasoft.bpm                   2023-10..2026-04 9.0.9        |..............======|
  ?   de.funfried.libraries                2022-05..2026-03 1.0.13       |...........=========|
    + 22 more: org.eclipse.platform, io.github.alien-tools, org.tango-controls, org.tango-controls.pogo, ch.reportingsoft.birt, com.liferay, net.revelc.code.formatter, org.kie.j2cl.tools.external, io.github.dogla, com.vertispan.j2cl.external, fr.inria.gforge.spirals, org.geneweaver, (+10 more)
net.bytebuddy  [fork: keep `net.bytebuddy`, `de.gematik.test` still publishes the name]
  ? * net.bytebuddy                        2017-05..2026-03 1.18.8       |.===================|
  ?   de.gematik.test                      2024-08..2026-05 4.2.7        |...............=====|
  ?   com.jcabi                            2025-11..2026-05 1.9.0        |..................==|
  ?   org.lucee                            2026-04..2026-04 5.6.15.15-RC |...................=|
  ?   io.github.lucientong                 2026-04..2026-04 1.0.0        |...................=|
  ?   dev.jorel                            2024-12..2026-04 11.2.0       |................====|
    + 123 more: io.github.rocketbunny727, io.github.smallfast, net.aivory, ai.superstream, io.github.mlanett, com.appland, io.github.jlapugot.chronoguard, io.github.quiethappiness, com.graphql-java, net.dmulloy2, org.talend.sdk.component.sample.feature, io.atlasgo, (+111 more)
org.hibernate.validator  [fork: keep `org.hibernate.validator`, `com.guicedee.modules.services` still publishes the name]
  ? * org.hibernate.validator              2017-06..2025-11 9.1.0.Final  |.==================.|
  ?   com.guicedee.modules.services        2026-04..2026-05 2.0.2        |...................=|
  ?   com.liferay                          2025-05..2025-05 6.2.5.Final.JAKARTA-LIFERAY-PATCHED-1 |.................=..|
  ?   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17 |......=====.........|
com.ibm.mq.jakarta  [fork: keep `com.ibm.mq`, `com.guicedee.modules.services` still publishes the name]
  ? * com.ibm.mq                           2023-02..2026-05 9.4.5.1      |............========|
  ?   com.guicedee.modules.services        2026-04..2026-05 2.0.2        |...................=|
io.vertx.client.kafka  [fork: keep `io.vertx`, `com.guicedee.modules.services` still publishes the name]
  ? * io.vertx                             2020-05..2026-05 4.5.27       |.......=============|
  ?   com.guicedee.modules.services        2026-04..2026-05 2.0.2        |...................=|
  ?   io.arenadata.vertx                   2022-01..2024-04 4.2.3-arenadata2 |..........======....|
org.apache.poi.poi  [fork: keep `org.apache.poi`, `com.guicedee.modules.services` still publishes the name]
  ? * org.apache.poi                       2021-01..2025-11 5.5.1        |........===========.|
  ?   com.guicedee.modules.services        2026-04..2026-05 2.0.2        |...................=|
  ?   io.github.itgemini                   2024-12..2026-02 2.2.5        |................===.|
  ?   io.github.daichangya                 2025-12..2025-12 5.1.1        |..................=.|
  ?   com.jsdiff                           2025-12..2025-12 5.1.0        |..................=.|
  ?   io.gitee.tikadoc                     2024-12..2025-02 0.2.11       |................=...|
    + 13 more: io.github.geminit-it, io.github.mianalysis, com.liferay, com.crealytics, com.codoid.products, ch.exense.step.library, com.guicedee.services, io.github.rocketmadev, org.datakurator, org.lucee, io.github.weizhonzhen, xyz.ottr.lutra, (+1 more)
org.mapstruct  [fork: keep `org.mapstruct`, `com.guicedee.modules.services` still publishes the name]
  ? * org.mapstruct                        2017-07..2026-02 1.7.0.Beta1  |.==================.|
  ?   com.guicedee.modules.services        2026-04..2026-05 2.0.2        |...................=|
  ?   com.gitee.jmash                      2023-11..2023-11 1.6.0        |..............=.....|
  ?   io.github.errantfiddle               2023-09..2023-09 2            |..............=.....|
  ?   com.guicedee.services                2020-09..2022-02 1.2.2.1-jre17 |.......====.........|
com.azure.identity  [fork: keep `com.azure`, `com.guicedee.modules.services` still publishes the name]
  ? * com.azure                            2019-09..2026-04 1.18.3       |.....===============|
  ?   com.guicedee.modules.services        2026-04..2026-05 2.0.2        |...................=|
  ?   com.microsoft.azure.kusto            2023-02..2024-08 2.0.3        |............====....|
  ?   org.apache.jackrabbit                2024-04..2024-05 1.64.0       |...............=....|
  ?   com.guicedee.services                2021-03..2022-02 1.2.2.1-jre17 |........===.........|
org.postgresql.jdbc  [fork: keep `org.postgresql`, `com.guicedee.modules.services` still publishes the name]
  ? * org.postgresql                       2019-09..2026-04 42.7.11      |.....===============|
  ?   com.guicedee.modules.services        2026-04..2026-05 2.0.2        |...................=|
  ?   com.enterprisedb                     2024-05..2026-05 42.7.11.1    |...............=====|
  ?   cn.com.kingbase                      2025-03..2026-02 9.0.1.jre6   |.................==.|
  ?   com.yugabyte                         2021-10..2025-04 42.7.3-yb-4  |..........========..|
  ?   com.alibaba.hologres                 2020-12..2024-08 42.2.26.2    |........========....|
    + 2 more: com.highgo, software.aws.rds
org.glassfish.jersey.core.common  [fork: keep `org.glassfish.jersey.core`, `com.exasol` still publishes the name]
  ? * org.glassfish.jersey.core            2025-09..2026-03 5.0.0-M1     |..................=.|
  ?   com.exasol                           2025-12..2026-05 5.6.2        |..................==|
  ?   com.jcabi                            2025-11..2025-11 1.10.0       |..................=.|
org.apache.logging.log4j.slf4j2.impl  [fork: keep `org.apache.logging.log4j`, `net.corda` still publishes the name]
  ? * org.apache.logging.log4j             2023-10..2026-05 2.26.0       |..............======|
  ?   net.corda                            2024-07..2026-05 4.14.2       |...............=====|
  ?   io.kroxylicious                      2024-12..2026-03 0.19.0       |................===.|
  ?   io.github.alien-tools                2025-07..2025-07 0.2.0        |.................=..|
  ?   org.apache.tika                      2025-04..2025-05 3.2.0        |.................=..|
  ?   xyz.gianlu.librespot                 2024-12..2024-12 1.6.5        |................=...|
    + 1 more: io.github.giis-uniovi
com.azure.json  [fork: keep `com.azure`, `com.microsoft.azure.kusto` still publishes the name]
  ? * com.azure                            2022-09..2026-01 1.5.1        |...........========.|
  ?   com.microsoft.azure.kusto            2026-04..2026-05 7.0.8        |...................=|
io.reactivex.rxjava3  [fork: keep `io.reactivex.rxjava3`, `software.amazon.nio.s3` still publishes the name]
  ? * io.reactivex.rxjava3                 2019-06..2025-09 3.1.12       |.....==============.|
  ?   software.amazon.nio.s3               2023-07..2026-05 2.4.0        |.............=======|
  ?   org.openidentityplatform.opendj      2025-07..2026-04 5.1.0        |.................===|
  ?   com.jd.live                          2025-03..2026-04 1.9.0        |................====|
  ?   org.infinispan                       2022-03..2025-08 14.0.35.Final |..........========..|
  ?   com.unblu.tools                      2023-04..2024-06 3.1.3        |.............===....|
    + 4 more: com.contentful.java, com.github.tonivade, au.csiro.pathling, net.sansa-stack
jakarta.websocket.client  [fork: keep `jakarta.websocket`, `org.apache.tomcat` still publishes the name]
  ? * jakarta.websocket                    2020-10..2026-01 2.3.0-M2     |.......============.|
  ?   org.apache.tomcat                    2021-12..2026-05 10.1.55      |..........==========|
jakarta.websocket  [fork: keep `jakarta.websocket`, `org.apache.tomcat` still publishes the name]
  ? * jakarta.websocket                    2020-10..2026-01 2.3.0-M2     |.......============.|
  ?   org.apache.tomcat                    2020-11..2026-05 10.1.55      |........============|
  ?   io.flux-capacitor                    2024-09..2025-06 0.1179.0     |...............===..|
jdk.internal.vm.compiler  [fork: keep `org.graalvm.compiler`, `io.vertx` still publishes the name]
  ? * org.graalvm.compiler                 2018-10..2026-04 23.0.12      |...=================|
  ?   io.vertx                             2022-11..2026-05 4.5.27       |............========|
  ?   org.linuxforhealth.fhir              2022-08..2022-12 5.1.1        |...........==.......|
net.kyori.adventure.text.serializer.bungeecord  [fork: keep `net.kyori`, `io.github.toxicity188` still publishes the name]
  ? * net.kyori                            2021-09..2025-07 4.4.1        |.........=========..|
  ?   io.github.toxicity188                2026-05..2026-05 5.0.0        |...................=|
net.kyori.adventure.text.serializer.legacytext3  [fork: keep `net.kyori`, `io.github.toxicity188` still publishes the name]
  ? * net.kyori                            2021-09..2025-07 4.4.1        |.........=========..|
  ?   io.github.toxicity188                2026-05..2026-05 5.0.0        |...................=|
org.snakeyaml.engine.v2  [fork: keep `org.snakeyaml`, `com.datadoghq` still publishes the name]
  ? * org.snakeyaml                        2019-10..2025-07 2.10         |.....=============..|
  ?   com.datadoghq                        2025-06..2026-05 1.62.0       |.................===|
  ?   org.frankframework                   2025-01..2026-04 10.0.1       |................====|
  ?   io.acryl                             2026-03..2026-04 1.5.0.5      |..................==|
  ?   io.strimzi                           2023-10..2026-03 0.45.2       |..............=====.|
  ?   io.github.phompang                   2026-03..2026-03 0.2.1        |..................=.|
    + 23 more: eu.koboo, io.dscope.camel, org.workflomics, io.github.ethanz0x0, org.sonarsource.iac, ch.framedev, com.atlan, org.pkl-lang, io.kestra.plugin, io.github.frame-dev, io.github.milkdrinkers, io.opentelemetry.contrib, (+11 more)
io.netty.internal.tcnative.openssl.linux.x86_64  [fork: keep `io.netty`, `com.azure.cosmos.spark` still publishes the name]
  ? * io.netty                             2022-05..2026-04 2.0.77.Final |...........=========|
  ?   com.azure.cosmos.spark               2026-02..2026-05 4.48.0       |..................==|
  ?   io.smallrye                          2026-04..2026-04 0.1.4        |...................=|
  ?   org.apache.iotdb                     2026-04..2026-04 2.0.8        |...................=|
  ?   io.zipkin.dependencies               2026-03..2026-03 3.2.2        |...................=|
  ?   com.danielflower.apprunner           2024-12..2025-08 2.0.5        |................==..|
    + 4 more: io.karatelabs, io.opentelemetry.javaagent, com.github.emc-mongoose, io.servicetalk
java.servlet  [fork: keep `jakarta.servlet`, `org.apache.tomcat` still publishes the name]
  ? * jakarta.servlet                      2019-08..2020-07 5.0.0-M2     |.....===............|
  ?   org.apache.tomcat                    2020-11..2026-05 9.0.118      |........============|
  ?   org.apache.felix                     2022-02..2022-10 2.1.0        |..........===.......|
  ?   com.guicedee.services                2020-05..2022-02 1.2.2.1-jre17 |.......====.........|
  ?   org.jboss.spec.javax.servlet         2019-08..2019-09 2.0.0.Final  |.....=..............|
io.vavr  [fork: keep `io.vavr`, `org.finos.legend.sdlc` still publishes the name]
  ? * io.vavr                              2017-11..2026-03 1.0.1        |..=================.|
  ?   org.finos.legend.sdlc                2026-04..2026-04 0.223.0      |...................=|
  ?   com.github.jlangch                   2025-02..2026-04 1.12.90      |................====|
  ?   io.github.dpsoft                     2025-12..2026-01 0.1.10       |..................=.|
  ?   ch.randelshofer                      2024-10..2024-10 0.10.5       |................=...|
net.bytebuddy.agent  [fork: keep `net.bytebuddy`, `com.macstab.chaos.jvm` still publishes the name]
  ? * net.bytebuddy                        2017-05..2026-03 1.18.8       |.===================|
  ?   com.macstab.chaos.jvm                2026-04..2026-04 1.0.0        |...................=|
  ?   com.google.gerrit                    2020-02..2026-04 3.13.6       |......==============|
  ?   co.elastic.apm                       2019-02..2026-04 1.55.6       |....================|
  ?   cn.easii                             2026-04..2026-04 1.0.6        |...................=|
  ?   me.bechberger                        2025-09..2025-09 0.0.1        |.................=..|
    + 14 more: cn.langpy, com.leanxcale, com.zto.fire, software.amazon.disco, com.nerdvision, com.idea-aedi, org.openstreetmap.atlas, com.netsensia.rivalchess, com.github.liuzhengyang, com.securenative.java, com.amazonaws, com.inaos.jam, (+2 more)
org.mockito  [fork: keep `org.mockito`, `org.apache.druid` still publishes the name]
  ? * org.mockito                          2017-10..2026-03 5.23.0       |.==================.|
  ?   org.apache.druid                     2026-04..2026-04 37.0.0       |...................=|
  ?   org.apache.tinkerpop                 2025-11..2026-04 3.8.1        |..................==|
it.unimi.dsi.fastutil  [fork: keep `it.unimi.dsi`, `io.github.lionblazer` still publishes the name]
  ? * it.unimi.dsi                         2018-05..2025-10 8.5.18       |...================.|
  ?   io.github.lionblazer                 2026-02..2026-04 8.5.29       |..................==|
  ?   co.datadome                          2022-12..2022-12 8.5.11.1     |............=.......|
org.jctools.core  [fork: keep `org.jctools`, `net.kieker-monitoring` still publishes the name]
  ? * org.jctools                          2020-11..2026-02 4.0.6        |........===========.|
  ?   net.kieker-monitoring                2024-09..2026-04 2.0.3        |................====|
  ?   io.xdag                              2025-10..2025-12 0.1.6        |..................=.|
  ?   io.github.shangor                    2025-07..2025-08 1.1.3        |.................=..|
  ?   io.actor4j                           2025-06..2025-06 2.4.0-beta.3 |.................=..|
  ?   io.github.jponge.jct                 2023-11..2023-11 4.0.2-RC2    |..............=.....|
io.netty.internal.tcnative.openssl.linux.aarch_64  [fork: keep `io.netty`, `io.smallrye` still publishes the name]
  ? * io.netty                             2022-05..2026-04 2.0.77.Final |...........=========|
  ?   io.smallrye                          2026-04..2026-04 0.1.4        |...................=|
json.path  [fork: keep `com.jayway.jsonpath`, `org.gov4j.thirdparty.com.jayway.jsonpath` still publishes the name]
  ? * com.jayway.jsonpath                  2024-01..2026-02 3.0.0        |..............=====.|
  ?   org.gov4j.thirdparty.com.jayway.jsonpath 2024-12..2026-04 3.0.0-gov4j-1 |................====|
  ?   com.github.sonus21                   2025-04..2025-04 2.10.0       |.................=..|
org.apache.wss4j.dom  [fork: keep `org.apache.wss4j`, `org.gov4j.thirdparty.org.apache.wss4j` still publishes the name]
  ? * org.apache.wss4j                     2019-03..2025-10 3.0.5        |....===============.|
  ?   org.gov4j.thirdparty.org.apache.wss4j 2024-12..2026-04 4.0.1-gov4j-1 |................====|
org.apache.wss4j.common  [fork: keep `org.apache.wss4j`, `org.gov4j.thirdparty.org.apache.wss4j` still publishes the name]
  ? * org.apache.wss4j                     2019-03..2025-10 3.0.5        |....===============.|
  ?   org.gov4j.thirdparty.org.apache.wss4j 2024-12..2026-04 4.0.1-gov4j-1 |................====|
org.apache.pdfbox.io  [fork: keep `org.apache.pdfbox`, `org.wso2.orbit.org.apache.pdfbox` still publishes the name]
  ? * org.apache.pdfbox                    2022-05..2026-03 3.0.7        |...........========.|
  ?   org.wso2.orbit.org.apache.pdfbox     2024-03..2026-04 3.0.7.wso2v1 |..............======|
org.apache.commons.fileupload2.jakarta.servlet6  [fork: keep `org.apache.jena`, `io.github.dhruvrawatdev` still publishes the name]
  ? * org.apache.jena                      2024-03..2024-07 5.1.0        |..............==....|
  ?   io.github.dhruvrawatdev              2026-04..2026-04 1.0.1        |...................=|
  ?   cloud.piranha.dist                   2024-07..2025-06 25.6.0       |...............===..|
  ?   io.telicent.smart-caches.graph       2024-06..2024-12 0.82.14      |...............==...|
  ?   io.telicent                          2024-06..2024-06 1.2.1        |...............=....|
feign.core  [fork: keep `io.github.openfeign`, `org.octopusden.octopus.jira` still publishes the name]
  ? * io.github.openfeign                  2023-02..2026-04 13.12        |............========|
  ?   org.octopusden.octopus.jira          2024-02..2026-04 2.0.6        |..............======|
  ?   io.github.sunny-chung                2024-02..2024-02 13.2.1-patch-1 |..............=.....|
com.miglayout.swing  [fork: keep `com.miglayout`, `dev.robocode.tankroyale` still publishes the name]
  ? * com.miglayout                        2021-04..2026-02 11.4.3       |.........==========.|
  ?   dev.robocode.tankroyale              2026-01..2026-04 0.42.0       |..................==|
  ?   de.schipplock.apps                   2023-01..2023-01 0.0.2        |............=.......|
spring.boot.jarmode.layertools  [fork: keep `org.springframework.boot`, `net.xdob.springframework.boot` still publishes the name]
  ? * org.springframework.boot             2020-05..2024-11 3.2.12       |.......==========...|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
spring.boot.starter.jta.atomikos  [fork: keep `org.springframework.boot`, `net.xdob.springframework.boot` still publishes the name]
  ? * org.springframework.boot             2018-03..2023-11 2.7.18       |..=============.....|
  ?   net.xdob.springframework.boot        2025-03..2026-04 2.7.22       |................====|
kotlin.reflect  [fork: keep `org.jetbrains.kotlin`, `com.airbnb.viaduct` still publishes the name]
  ? * org.jetbrains.kotlin                 2019-01..2023-08 1.9.10       |....==========......|
  ?   com.airbnb.viaduct                   2026-01..2026-04 0.29.0       |..................==|
  ?   org.apache.pinot                     2025-09..2026-04 1.5.0        |.................===|
  ?   io.github.abdullahkhan118            2026-03..2026-03 1.0.4        |...................=|
  ?   io.github.tobi-laa                   2026-03..2026-03 1.0.0        |..................=.|
  ?   io.github.kshulzh.kefir              2026-03..2026-03 0.0.1        |..................=.|
    + 58 more: io.github.xilinjia.krdb, io.github.snow1026, com.browserstack, com.simprints.realm.kotlin, org.pkl-lang, com.statsig, com.infomaniak.realm.kotlin, com.solapi, io.github.honkling.commando, io.github.tabilzad.inspektor, hu.bme.mit.theta, com.beeproduced, (+46 more)
roaringbitmap  [fork: keep `org.roaringbitmap`, `org.apache.celeborn` still publishes the name]
  ? * org.roaringbitmap                    2023-09..2026-04 1.6.14       |.............=======|
  ?   org.apache.celeborn                  2024-06..2026-04 0.6.3        |...............=====|
  ?   org.bitlap                           2023-10..2023-10 1.0.1.0      |..............=.....|
graphql.java.tools  [fork: keep `com.graphql-java-kickstart`, `io.github.graphql-java-kickstart` still publishes the name]
  ? * com.graphql-java-kickstart           2023-08..2025-04 14.0.1       |.............=====..|
  ?   io.github.graphql-java-kickstart     2026-03..2026-04 14.0.2       |...................=|
com.github.rvesse.airline  [fork: keep `com.github.rvesse`, `io.stargate.starter` still publishes the name]
  ? * com.github.rvesse                    2019-04..2025-10 3.2.0        |.....==============.|
  ?   io.stargate.starter                  2020-09..2026-04 2.0.50       |.......=============|
  ?   org.apache.seatunnel                 2024-07..2026-02 2.3.13       |...............====.|
  ?   org.graylog2                         2020-05..2023-05 4.3.15       |.......=======......|
org.java_websocket  [fork: keep `org.java-websocket`, `io.github.cb-jarunmadhesh` still publishes the name]
  ? * org.java-websocket                   2023-07..2024-12 1.6.0        |.............====...|
  ?   io.github.cb-jarunmadhesh            2026-04..2026-04 1.0.0        |...................=|
  ?   io.github.ashwithpoojary98           2026-01..2026-01 1.0.1        |..................=.|
  ?   dev.lolyay                           2025-07..2025-10 5.8.0        |.................==.|
  ?   io.kestra.plugin                     2023-09..2025-08 0.24.0       |..............====..|
  ?   org.jetbrains.kotlinx                2025-07..2025-07 0.14.1-506   |.................=..|
    + 3 more: io.github.gubaojian, com.taosdata.jdbc, com.enixyu
org.signal.libsignal  [fork: keep `org.signal`, `io.github.wanggenlin` still publishes the name]
  ? * org.signal                           2023-09..2025-11 0.86.5       |.............======.|
  ?   io.github.wanggenlin                 2026-02..2026-04 0.77.1       |..................==|
net.kyori.option  [fork: keep `net.kyori`, `io.github.wrldmap` still publishes the name]
  ? * net.kyori                            2023-12..2025-02 1.1.0        |..............===...|
  ?   io.github.wrldmap                    2026-04..2026-04 0.1.1        |...................=|
  ?   net.flectone                         2026-04..2026-04 2.12.7       |...................=|
  ?   dev.aurelium                         2025-06..2026-03 1.2.2        |.................===|
  ?   com.system32dev                      2025-10..2025-11 1.1.0        |..................=.|
  ?   site.system32dev.repo                2025-08..2025-08 1.0          |.................=..|
tech.fortis.sandbox.api  [fork: keep `io.github.zahran444`, `io.sdks` still publishes the name]
  ? * io.github.zahran444                  2026-04..2026-04 1.0.0        |...................=|
  ?   io.sdks                              2026-04..2026-04 1.0.5        |...................=|
com.maxio.advancedbilling  [fork: keep `com.maxio`, `io.github.zahran444` still publishes the name]
  ? * com.maxio                            2024-09..2026-03 9.0.0        |................====|
  ?   io.github.zahran444                  2026-04..2026-04 1.0.5        |...................=|
com.mypayquicker.api  [fork: keep `io.sdks`, `io.github.zahran444` still publishes the name]
  ? * io.sdks                              2026-02..2026-02 1.0.3        |..................=.|
  ?   io.github.zahran444                  2026-04..2026-04 1.0.0        |...................=|
net.sf.jsqlparser  [fork: keep `com.github.jsqlparser`, `com.manticore-projects.jsqlformatter` still publishes the name]
  ? * com.github.jsqlparser                2024-03..2025-05 5.3          |..............====..|
  ?   com.manticore-projects.jsqlformatter 2025-12..2026-04 5.3.218      |..................==|
  ?   se.alipsa                            2025-12..2025-12 1.2.0        |..................=.|
  ?   ai.starlake                          2024-09..2024-10 1.3.0        |...............==...|
```

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

