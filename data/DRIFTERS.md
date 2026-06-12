# Module ownership drifters

Generated 2026-06-12. A module *drifts* when more than one groupId publishes the name and its `owners.tsv` does not yet name every publisher (no `owners.tsv`, or one that leaves some publishing groupId neither `allowed` nor `blocked`). Resolving a drift means deciding each groupId via `SetOwners` (which writes `allowed`/`blocked`); a fully-named module drops off this list.

| Category | Unresolved | Resolved via owners.tsv |
|---|---:|---:|
| explicit-rules | 0 | 125 |
| republisher | 0 | 57 |
| migration | 0 | 1586 |
| fork | 0 | 255 |
| shaded | 47 | 1222 |
| tld-dropped | 0 | 15 |
| two-segments | 156 | 0 |
| unclassified | 109 | 0 |
| **total** | **312** | **3260** |
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

## shaded (47)

The natural-namespace owner (the module name falls under its groupId) is the earliest and most-recent publisher; every other group merely shades or bundles the name under its own coordinate. Proposal: allow the natural owner, block the rest. Resolution is unchanged; this just records the decision so the module drops off the report.

| count | current owner -> proposed allowed |
|---:|---|
| 24 | `org.eclipse.platform -> org.eclipse.platform` |
| 8 | `com.google.inject.extensions -> com.google.inject.extensions` |
| 3 | `org.graalvm.sdk -> org.graalvm.sdk` |
| 2 | `biz.aQute.bnd -> biz.aQute.bnd` |
| 2 | `com.sun.xml.bind.external -> com.sun.xml.bind.external` |
| 1 | `com.amihaiemil.web -> com.amihaiemil.web` |
| 1 | `com.sun.xml.ws -> com.sun.xml.ws` |
| 1 | `io.soabase.record-builder -> io.soabase.record-builder` |
| 1 | `org.apache.logging.log4j -> org.apache.logging.log4j` |
| 1 | `org.hibernate.common -> org.hibernate.common` |
| 1 | `org.neo4j.app -> org.neo4j.app` |
| 1 | `org.neo4j.test -> org.neo4j.test` |
| 1 | `uk.co.real-logic -> uk.co.real-logic` |

```
org.neo4j.harness  [owned by `org.neo4j.test`; 1 other group(s) shade the name]
  ? * org.neo4j.test                       2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb.test       2020-04..2025-06 1.0.6        |.......===========..|
org.neo4j.server  [owned by `org.neo4j.app`; 1 other group(s) shade the name]
  ? * org.neo4j.app                        2018-03..2026-05 2026.05.0    |..==================|
  ?   org.graphfoundation.ongdb.app        2020-04..2025-06 1.0.6        |.......===========..|
com.sun.tools.rngdatatype  [owned by `com.sun.xml.bind.external`; 1 other group(s) shade the name]
  ? * com.sun.xml.bind.external            2018-07..2026-05 4.0.9        |...=================|
  ?   cn.lzgabel.jaxb.xml.bind.external    2022-03..2022-03 4.0.0        |..........=.........|
com.sun.tools.rngom  [owned by `com.sun.xml.bind.external`; 1 other group(s) shade the name]
  ? * com.sun.xml.bind.external            2018-07..2026-05 4.0.9        |...=================|
  ?   cn.lzgabel.jaxb.xml.bind.external    2022-03..2022-03 4.0.0        |..........=.........|
org.apache.log4j  [owned by `org.apache.logging.log4j`; 1 other group(s) shade the name]
  ? * org.apache.logging.log4j             2017-11..2026-05 2.26.0       |..==================|
  ?   org.slf4j                            2019-08..2022-02 1.7.36       |.....======.........|
uk.co.real_logic.sbe.tool  [owned by `uk.co.real-logic`; 1 other group(s) shade the name]
  ? * uk.co.real-logic                     2019-02..2026-04 1.38.1       |....================|
  ?   org.viewstreet                       2020-03..2020-03 1.16.1.760   |......=.............|
org.graalvm.collections  [owned by `org.graalvm.sdk`; 1 other group(s) shade the name]
  ? * org.graalvm.sdk                      2023-09..2026-04 25.0.3       |.............=======|
  ?   io.github.lnyo-cly                   2026-04..2026-04 2.2.0        |...................=|
org.graalvm.nativeimage  [owned by `org.graalvm.sdk`; 1 other group(s) shade the name]
  ? * org.graalvm.sdk                      2023-09..2026-04 25.0.3       |.............=======|
  ?   io.vproxy                            2023-10..2024-07 1.2.2        |..............==....|
org.graalvm.word  [owned by `org.graalvm.sdk`; 1 other group(s) shade the name]
  ? * org.graalvm.sdk                      2023-09..2026-04 25.0.3       |.............=======|
  ?   io.vproxy                            2023-10..2024-07 1.2.2        |..............==....|
biz.aQute.bndlib  [owned by `biz.aQute.bnd`; 1 other group(s) shade the name]
  ? * biz.aQute.bnd                        2018-05..2026-03 7.2.3        |...=================|
  ?   com.liferay                          2019-07..2024-01 4.2.0-20190219.175746-114-LIFERAY-CACHED.LIFERAY-PATCHED-2 |.....==========.....|
biz.aQute.resolve  [owned by `biz.aQute.bnd`; 1 other group(s) shade the name]
  ? * biz.aQute.bnd                        2018-05..2026-03 7.2.3        |...=================|
  ?   com.liferay                          2018-11..2018-11 4.1.0.LIFERAY-PATCHED-1 |....=...............|
org.eclipse.help  [owned by `org.eclipse.platform`; 1 other group(s) shade the name]
  ? * org.eclipse.platform                 2018-06..2026-03 3.11.0       |...================.|
  ?   com.innoventsolutions.birt.runtime   2018-08..2018-08 4.8.0        |...=................|
org.eclipse.swt  [owned by `org.eclipse.platform`; 1 other group(s) shade the name]
  ? * org.eclipse.platform                 2018-06..2026-03 3.133.0      |...================.|
  ?   net.dongliu                          2019-11..2019-11 4.13.1       |......=.............|
org.eclipse.core.resources  [owned by `org.eclipse.platform`; 4 other group(s) shade the name]
  ? * org.eclipse.platform                 2018-06..2026-03 3.23.200     |...================.|
  ?   ch.reportingsoft.birt                2025-04..2025-07 3.22.200     |.................=..|
  ?   org.kie.j2cl.tools.external          2024-07..2024-11 v20241110-1  |...............==...|
  ?   com.vertispan.j2cl.external          2022-03..2023-11 v20230718-1  |..........=====.....|
  ?   com.innoventsolutions.birt.runtime   2018-08..2018-08 4.8.0        |...=................|
org.eclipse.text  [owned by `org.eclipse.platform`; 2 other group(s) shade the name]
  ? * org.eclipse.platform                 2018-06..2026-03 3.14.600     |...================.|
  ?   org.kie.j2cl.tools.external          2024-07..2024-11 v20241110-1  |...............==...|
  ?   com.vertispan.j2cl.external          2022-03..2023-11 v20230718-1  |..........=====.....|
org.eclipse.update.configurator  [owned by `org.eclipse.platform`; 1 other group(s) shade the name]
  ? * org.eclipse.platform                 2018-07..2026-03 3.5.1000     |...================.|
  ?   com.innoventsolutions.birt.runtime   2018-08..2018-08 4.8.0        |...=................|
org.eclipse.core.runtime  [owned by `org.eclipse.platform`; 5 other group(s) shade the name]
  ? * org.eclipse.platform                 2018-06..2026-03 3.34.200     |...================.|
  ?   ch.reportingsoft.birt                2025-04..2025-05 3.33.0       |.................=..|
  ?   org.kie.j2cl.tools.external          2024-07..2024-11 v20241110-1  |...............==...|
  ?   org.geckoprojects.eclipse.core       2024-09..2024-09 3.14.0       |................=...|
  ?   com.vertispan.j2cl.external          2022-03..2023-11 v20230718-1  |..........=====.....|
  ?   com.innoventsolutions.birt.runtime   2018-08..2018-08 4.8.0        |...=................|
org.eclipse.equinox.app  [owned by `org.eclipse.platform`; 2 other group(s) shade the name]
  ? * org.eclipse.platform                 2018-06..2026-03 1.7.600      |...================.|
  ?   ch.reportingsoft.birt                2025-04..2025-05 1.7.300      |.................=..|
  ?   com.innoventsolutions.birt.runtime   2018-08..2018-08 4.8.0        |...=................|
io.soabase.recordbuilder.core  [owned by `io.soabase.record-builder`; 1 other group(s) shade the name]
  ? * io.soabase.record-builder            2021-06..2026-01 52           |.........==========.|
  ?   dev.ikm.jpms                         2024-01..2024-08 36-r6        |..............==....|
org.eclipse.equinox.preferences  [owned by `org.eclipse.platform`; 4 other group(s) shade the name]
  ? * org.eclipse.platform                 2018-06..2025-12 3.12.100     |...================.|
  ?   ch.reportingsoft.birt                2025-04..2025-05 3.11.300     |.................=..|
  ?   org.kie.j2cl.tools.external          2024-07..2024-11 v20241110-1  |...............==...|
  ?   com.vertispan.j2cl.external          2022-03..2023-11 v20230718-1  |..........=====.....|
  ?   com.innoventsolutions.birt.runtime   2018-08..2018-08 4.8.0        |...=................|
org.eclipse.equinox.registry  [owned by `org.eclipse.platform`; 2 other group(s) shade the name]
  ? * org.eclipse.platform                 2018-06..2025-12 3.12.600     |...================.|
  ?   ch.reportingsoft.birt                2025-04..2025-05 3.12.300     |.................=..|
  ?   com.innoventsolutions.birt.runtime   2018-08..2018-08 4.8.0        |...=................|
org.eclipse.equinox.simpleconfigurator  [owned by `org.eclipse.platform`; 2 other group(s) shade the name]
  ? * org.eclipse.platform                 2018-06..2025-12 1.5.700      |...================.|
  ?   ch.reportingsoft.birt                2025-04..2025-07 1.5.500      |.................=..|
  ?   com.innoventsolutions.birt.runtime   2018-08..2018-08 4.8.0        |...=................|
org.eclipse.core.filesystem  [owned by `org.eclipse.platform`; 2 other group(s) shade the name]
  ? * org.eclipse.platform                 2018-06..2025-12 1.11.400     |...================.|
  ?   ch.reportingsoft.birt                2025-04..2025-07 1.11.200     |.................=..|
  ?   com.innoventsolutions.birt.runtime   2018-08..2018-08 4.8.0        |...=................|
org.eclipse.core.contenttype  [owned by `org.eclipse.platform`; 4 other group(s) shade the name]
  ? * org.eclipse.platform                 2018-06..2025-12 3.9.800      |...================.|
  ?   ch.reportingsoft.birt                2025-04..2025-04 3.9.600      |.................=..|
  ?   org.kie.j2cl.tools.external          2024-07..2024-11 v20241110-1  |...............==...|
  ?   com.vertispan.j2cl.external          2022-03..2023-11 v20230718-1  |..........=====.....|
  ?   com.innoventsolutions.birt.runtime   2018-08..2018-08 4.8.0        |...=................|
org.eclipse.equinox.common  [owned by `org.eclipse.platform`; 4 other group(s) shade the name]
  ? * org.eclipse.platform                 2018-06..2025-12 3.20.300     |...================.|
  ?   ch.reportingsoft.birt                2025-04..2025-05 3.20.0       |.................=..|
  ?   org.kie.j2cl.tools.external          2024-07..2024-11 v20241110-1  |...............==...|
  ?   com.vertispan.j2cl.external          2022-03..2023-11 v20230718-1  |..........=====.....|
  ?   com.innoventsolutions.birt.runtime   2018-08..2018-08 4.8.0        |...=................|
org.eclipse.equinox.frameworkadmin  [owned by `org.eclipse.platform`; 1 other group(s) shade the name]
  ? * org.eclipse.platform                 2018-06..2025-09 2.3.500      |...===============..|
  ?   com.innoventsolutions.birt.runtime   2018-08..2018-08 4.8.0        |...=................|
org.eclipse.equinox.metatype  [owned by `org.eclipse.platform`; 1 other group(s) shade the name]
  ? * org.eclipse.platform                 2018-06..2025-09 1.6.900      |...===============..|
  ?   com.liferay                          2024-10..2024-10 1.4.400.LIFERAY-PATCHED-1 |................=...|
org.eclipse.osgi.services  [owned by `org.eclipse.platform`; 1 other group(s) shade the name]
  ? * org.eclipse.platform                 2018-06..2025-09 3.12.300     |...===============..|
  ?   com.innoventsolutions.birt.runtime   2018-08..2018-08 4.8.0        |...=................|
org.eclipse.core.expressions  [owned by `org.eclipse.platform`; 2 other group(s) shade the name]
  ? * org.eclipse.platform                 2018-06..2025-09 3.9.500      |...===============..|
  ?   ch.reportingsoft.birt                2025-04..2025-04 3.9.400      |.................=..|
  ?   com.innoventsolutions.birt.runtime   2018-08..2018-08 4.8.0        |...=................|
org.eclipse.equinox.console  [owned by `org.eclipse.platform`; 1 other group(s) shade the name]
  ? * org.eclipse.platform                 2018-07..2025-09 1.4.1100     |...===============..|
  ?   com.liferay                          2023-07..2023-07 1.4.300.LIFERAY-PATCHED-1 |.............=......|
org.eclipse.equinox.simpleconfigurator.manipulator  [owned by `org.eclipse.platform`; 1 other group(s) shade the name]
  ? * org.eclipse.platform                 2018-06..2025-09 2.3.600      |...===============..|
  ?   com.innoventsolutions.birt.runtime   2018-08..2018-08 4.8.0        |...=................|
org.eclipse.core.jobs  [owned by `org.eclipse.platform`; 5 other group(s) shade the name]
  ? * org.eclipse.platform                 2018-06..2025-09 3.15.700     |...===============..|
  ?   ch.reportingsoft.birt                2025-04..2025-05 3.15.500     |.................=..|
  ?   org.kie.j2cl.tools.external          2024-07..2024-11 v20241110-1  |...............==...|
  ?   com.vertispan.j2cl.external          2022-03..2023-11 v20230718-1  |..........=====.....|
  ?   net.officefloor.eclipse              2019-07..2019-07 3.12.0       |.....=..............|
  ?   com.innoventsolutions.birt.runtime   2018-08..2018-08 4.8.0        |...=................|
org.eclipse.equinox.security  [owned by `org.eclipse.platform`; 1 other group(s) shade the name]
  ? * org.eclipse.platform                 2018-06..2025-09 1.4.700      |...===============..|
  ?   com.innoventsolutions.birt.runtime   2018-08..2018-08 4.8.0        |...=................|
org.eclipse.osgi.util  [owned by `org.eclipse.platform`; 1 other group(s) shade the name]
  ? * org.eclipse.platform                 2018-06..2025-06 3.7.400      |...===============..|
  ?   com.innoventsolutions.birt.runtime   2018-08..2018-08 4.8.0        |...=................|
org.eclipse.equinox.frameworkadmin.equinox  [owned by `org.eclipse.platform`; 1 other group(s) shade the name]
  ? * org.eclipse.platform                 2018-06..2025-06 1.3.400      |...===============..|
  ?   com.innoventsolutions.birt.runtime   2018-08..2018-08 4.8.0        |...=................|
org.hibernate.commons.annotations  [owned by `org.hibernate.common`; 1 other group(s) shade the name]
  ? * org.hibernate.common                 2018-02..2024-10 7.0.3.Final  |..===============...|
  ?   com.guicedee.services                2019-11..2022-02 1.2.2.1-jre17 |......=====.........|
com.amihaiemil.eoyaml  [owned by `com.amihaiemil.web`; 1 other group(s) shade the name]
  ? * com.amihaiemil.web                   2020-04..2024-08 8.0.6        |......==========....|
  ?   io.github.portlek                    2020-06..2020-07 4.7.5        |.......=............|
com.google.guice.extensions.testlib  [owned by `com.google.inject.extensions`; 5 other group(s) shade the name]
  ? * com.google.inject.extensions         2018-02..2023-05 7.0.0        |..============......|
  ?   ru.vyarus.guice.jakarta              2023-04..2023-04 5.1.0-rc.2   |.............=......|
  ?   io.forestframework                   2021-06..2021-06 5.0.1.1      |.........=..........|
  ?   ca.stellardrift.guice-backport.extensions 2021-03..2021-03 5.0.1        |........=...........|
  ?   com.jwebmp.inject.extensions         2019-04..2019-08 0.68.0.1     |....==..............|
  ?   org.sonatype.sisu.inject             2018-04..2018-04 4.2.0        |..=.................|
com.google.guice.extensions.throwingproviders  [owned by `com.google.inject.extensions`; 5 other group(s) shade the name]
  ? * com.google.inject.extensions         2018-02..2023-05 7.0.0        |..============......|
  ?   ru.vyarus.guice.jakarta              2023-04..2023-04 5.1.0-rc.2   |.............=......|
  ?   io.forestframework                   2021-06..2021-06 5.0.1.1      |.........=..........|
  ?   ca.stellardrift.guice-backport.extensions 2021-03..2021-03 5.0.1        |........=...........|
  ?   com.jwebmp.inject.extensions         2019-02..2019-08 0.68.0.1     |....==..............|
  ?   org.sonatype.sisu.inject             2018-04..2018-04 4.2.0        |..=.................|
com.google.guice.extensions.spring  [owned by `com.google.inject.extensions`; 4 other group(s) shade the name]
  ? * com.google.inject.extensions         2018-02..2023-05 7.0.0        |..============......|
  ?   io.forestframework                   2021-06..2021-06 5.0.1.1      |.........=..........|
  ?   ca.stellardrift.guice-backport.extensions 2021-03..2021-03 5.0.1        |........=...........|
  ?   com.jwebmp.inject.extensions         2019-02..2019-08 0.68.0.1     |....==..............|
  ?   org.sonatype.sisu.inject             2018-04..2018-04 4.2.0        |..=.................|
com.google.guice.extensions.servlet  [owned by `com.google.inject.extensions`; 7 other group(s) shade the name]
  ? * com.google.inject.extensions         2018-02..2023-05 7.0.0        |..============......|
  ?   ru.vyarus.guice.jakarta              2023-04..2023-04 5.1.0-rc.2   |.............=......|
  ?   com.guicedee.services                2020-07..2022-02 1.2.2.1-jre17 |.......====.........|
  ?   io.forestframework                   2021-06..2021-06 5.0.1.1      |.........=..........|
  ?   ca.stellardrift.guice-backport.extensions 2021-03..2021-03 5.0.1        |........=...........|
  ?   com.guicedee.services.extensions     2019-11..2020-07 1.0.13.5-jre12 |......==............|
    + 2 more: com.jwebmp.inject.extensions, org.sonatype.sisu.inject
com.google.guice.extensions.persist  [owned by `com.google.inject.extensions`; 7 other group(s) shade the name]
  ? * com.google.inject.extensions         2018-02..2023-05 7.0.0        |..============......|
  ?   ru.vyarus.guice.jakarta              2023-04..2023-04 5.1.0-rc.2   |.............=......|
  ?   com.guicedee.services                2020-07..2022-02 1.2.2.1-jre17 |.......====.........|
  ?   io.forestframework                   2021-06..2021-06 5.0.1.1      |.........=..........|
  ?   ca.stellardrift.guice-backport.extensions 2021-03..2021-03 5.0.1        |........=...........|
  ?   com.guicedee.services.extensions     2019-11..2020-07 1.0.13.5-jre12 |......==............|
    + 2 more: com.jwebmp.inject.extensions, org.sonatype.sisu.inject
com.google.guice.extensions.daggeradapter  [owned by `com.google.inject.extensions`; 4 other group(s) shade the name]
  ? * com.google.inject.extensions         2018-02..2023-05 7.0.0        |..============......|
  ?   io.forestframework                   2021-06..2021-06 5.0.1.1      |.........=..........|
  ?   ca.stellardrift.guice-backport.extensions 2021-03..2021-03 5.0.1        |........=...........|
  ?   com.jwebmp.inject.extensions         2019-02..2019-08 0.68.0.1     |....==..............|
  ?   org.sonatype.sisu.inject             2018-04..2018-04 4.2.0        |..=.................|
com.google.guice.extensions.struts2  [owned by `com.google.inject.extensions`; 3 other group(s) shade the name]
  ? * com.google.inject.extensions         2018-02..2023-05 6.0.0        |..============......|
  ?   io.forestframework                   2021-06..2021-06 5.0.1.1      |.........=..........|
  ?   ca.stellardrift.guice-backport.extensions 2021-03..2021-03 5.0.1        |........=...........|
  ?   org.sonatype.sisu.inject             2018-04..2018-04 4.2.0        |..=.................|
org.eclipse.equinox.ds  [owned by `org.eclipse.platform`; 1 other group(s) shade the name]
  ? * org.eclipse.platform                 2018-06..2020-06 1.6.200      |...=====............|
  ?   com.innoventsolutions.birt.runtime   2018-08..2018-08 4.8.0        |...=................|
com.google.guice.extensions.multibindings  [owned by `com.google.inject.extensions`; 1 other group(s) shade the name]
  ? * com.google.inject.extensions         2018-02..2020-03 4.2.3        |..=====.............|
  ?   org.sonatype.sisu.inject             2018-04..2018-04 4.2.0        |..=.................|
com.sun.tools.ws.jaxws  [owned by `com.sun.xml.ws`; 1 other group(s) shade the name]
  ? * com.sun.xml.ws                       2019-01..2020-03 2.3.2-1      |....===.............|
  ?   org.glassfish.metro                  2019-01..2019-01 2.4.3        |....=...............|
```

## tld-dropped (0)

The dominant owner's groupId with its top-level domain (first segment) dropped is the module-name prefix (e.g. module ktorm.* owned by org.ktorm). Proposal: allow that owner, block the rest.

## two-segments (156)

The dominant owner's groupId with its first two segments dropped is the module-name prefix (e.g. module kotlinx.* owned by org.jetbrains.kotlinx). Proposal: allow that owner, block the rest.

| count | current owner -> proposed allowed |
|---:|---|
| 79 | `ru.tinkoff.kora -> ru.tinkoff.kora` |
| 32 | `com.javax0.jamal -> com.javax0.jamal,com.javax0.jamal.jamal-snippet.2.7.0.com.javax0.jamal` |
| 12 | `com.typesafe.akka -> com.typesafe.akka` |
| 12 | `org.jetbrains.kotlinx -> org.jetbrains.kotlinx,org.jetbrains.intellij.deps.kotlinx` |
| 6 | `com.squareup.okhttp3 -> com.squareup.okhttp3` |
| 6 | `org.jetbrains.kotlinx -> org.jetbrains.kotlinx` |
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
kotlinx.coroutines.jdk9  [owned by `org.jetbrains.kotlinx` (groupId minus two segments is the module prefix); 0 other group(s) shade the name]
  ? * org.jetbrains.kotlinx                2023-03..2026-05 1.11.0       |............========|
  ?   org.jetbrains.intellij.deps.kotlinx  2025-09..2025-11 1.10.2-intellij-1 |..................=.|
kotlinx.coroutines.android  [owned by `org.jetbrains.kotlinx` (groupId minus two segments is the module prefix); 0 other group(s) shade the name]
  ? * org.jetbrains.kotlinx                2023-03..2026-05 1.11.0       |............========|
  ?   org.jetbrains.intellij.deps.kotlinx  2025-09..2025-11 1.10.2-intellij-1 |..................=.|
kotlinx.coroutines.rx2  [owned by `org.jetbrains.kotlinx` (groupId minus two segments is the module prefix); 0 other group(s) shade the name]
  ? * org.jetbrains.kotlinx                2023-03..2026-05 1.11.0       |............========|
  ?   org.jetbrains.intellij.deps.kotlinx  2025-09..2025-11 1.10.2-intellij-1 |..................=.|
kotlinx.coroutines.reactive  [owned by `org.jetbrains.kotlinx` (groupId minus two segments is the module prefix); 0 other group(s) shade the name]
  ? * org.jetbrains.kotlinx                2023-03..2026-05 1.11.0       |............========|
  ?   org.jetbrains.intellij.deps.kotlinx  2025-09..2025-11 1.10.2-intellij-1 |..................=.|
kotlinx.coroutines.reactor  [owned by `org.jetbrains.kotlinx` (groupId minus two segments is the module prefix); 0 other group(s) shade the name]
  ? * org.jetbrains.kotlinx                2023-03..2026-05 1.11.0       |............========|
  ?   org.jetbrains.intellij.deps.kotlinx  2025-09..2025-11 1.10.2-intellij-1 |..................=.|
kotlinx.coroutines.debug  [owned by `org.jetbrains.kotlinx` (groupId minus two segments is the module prefix); 0 other group(s) shade the name]
  ? * org.jetbrains.kotlinx                2023-12..2026-05 1.11.0       |..............======|
  ?   org.jetbrains.intellij.deps.kotlinx  2025-09..2025-11 1.10.2-intellij-1 |..................=.|
kotlinx.coroutines.guava  [owned by `org.jetbrains.kotlinx` (groupId minus two segments is the module prefix); 0 other group(s) shade the name]
  ? * org.jetbrains.kotlinx                2023-03..2026-05 1.11.0       |............========|
  ?   org.jetbrains.intellij.deps.kotlinx  2025-09..2025-11 1.10.2-intellij-1 |..................=.|
kotlinx.coroutines.javafx  [owned by `org.jetbrains.kotlinx` (groupId minus two segments is the module prefix); 0 other group(s) shade the name]
  ? * org.jetbrains.kotlinx                2023-03..2026-05 1.11.0       |............========|
  ?   org.jetbrains.intellij.deps.kotlinx  2025-09..2025-11 1.10.2-intellij-1 |..................=.|
kotlinx.coroutines.rx3  [owned by `org.jetbrains.kotlinx` (groupId minus two segments is the module prefix); 0 other group(s) shade the name]
  ? * org.jetbrains.kotlinx                2023-03..2026-05 1.11.0       |............========|
  ?   org.jetbrains.intellij.deps.kotlinx  2025-09..2025-11 1.10.2-intellij-1 |..................=.|
kotlinx.coroutines.slf4j  [owned by `org.jetbrains.kotlinx` (groupId minus two segments is the module prefix); 4 other group(s) shade the name]
  ? * org.jetbrains.kotlinx                2023-03..2026-05 1.11.0       |............========|
  ?   org.jetbrains.intellij.deps.kotlinx  2025-09..2025-11 1.10.2-intellij-1 |..................=.|
  ?   jp.co.gahojin.thrifty                2025-02..2025-05 4.6.3        |................==..|
  ?   io.github.vooft                      2024-09..2025-02 0.5.4        |................=...|
  ?   dev.suresh.kmp                       2024-06..2024-07 0.15.0       |...............=....|
  ?   xyz.block                            2024-03..2024-03 0.13.0       |..............==....|
kotlinx.coroutines.swing  [owned by `org.jetbrains.kotlinx` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.jetbrains.kotlinx                2023-03..2026-05 1.11.0       |............========|
  ?   org.jetbrains.intellij.deps.kotlinx  2025-09..2025-11 1.10.2-intellij-1 |..................=.|
  ?   org.deltacv.EOCV-Sim                 2024-11..2024-11 3.8.4        |................=...|
kotlinx.coroutines.jdk8  [owned by `org.jetbrains.kotlinx` (groupId minus two segments is the module prefix); 6 other group(s) shade the name]
  ? * org.jetbrains.kotlinx                2023-03..2026-05 1.11.0       |............========|
  ?   org.jetbrains.intellij.deps.kotlinx  2025-09..2025-11 1.10.2-intellij-1 |..................=.|
  ?   dev.zabricraft                       2025-01..2025-01 0.3.2        |................=...|
  ?   io.github.danbeldev                  2024-08..2024-11 0.1.0        |...............==...|
  ?   xyz.block                            2024-02..2024-02 0.8.0-beta   |..............=.....|
  ?   com.blr19c.falowp                    2023-12..2024-01 1.0.1-beta-5 |..............=.....|
    + 2 more: org.cs124.jeed, io.github.flaxoos
kotlinx.datetime  [owned by `org.jetbrains.kotlinx` (groupId minus two segments is the module prefix); 3 other group(s) shade the name]
  ? * org.jetbrains.kotlinx                2022-01..2026-05 0.8.0        |..........==========|
  ?   me.haroldmartin                      2024-08..2024-09 0.3.2        |...............=....|
  ?   me.nathanfallet.zabricraft           2023-11..2023-12 0.2.4        |..............=.....|
  ?   org.danbrough.kotlinx                2022-10..2022-11 0.4.0d       |............=.......|
kora.cache.caffeine  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.config.symbol.processor  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.database.liquibase  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.grpc.client  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.jms  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.kafka.annotation.processor  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.kora.app.symbol.processor  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.scheduling.ksp  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.cache.redis  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.cache.symbol.processor  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.declarative.logging.annotation.processor  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.grpc.client.annotation.processor  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.grpc.client.symbol.processor  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.http.client.ok  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.http.client.symbol.processor  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.jackson.module  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.json.annotation.processor  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.json.common  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.json.symbol.processor  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.scheduling.annotation.processor  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.scheduling.jdk  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.symbol.processor.common  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.annotation.processor.common  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.cache.common  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.database.common  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.http.client.common  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.http.common  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.http.server.annotation.processor  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.kora.app.annotation.processor  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.netty.common  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.openapi.management  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.opentelemetry.common  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.opentelemetry.tracing.exporter.grpc  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.soap.client  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.soap.client.symbol.processor  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.validation.common  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.validation.symbol.processor  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.annotation.processors  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.aop.annotation.processor  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.database.annotation.processor  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.http.client.annotation.processor  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.http.server.common  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.openapi.generator  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.resilient.annotation.processor  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.resilient.symbol.processor  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.soap.client.annotation.processor  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.test.junit5  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.common  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.config.annotation.processor  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.config.common  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.config.yaml  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.database.cassandra  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.database.flyway  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.grpc.server  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.http.server.symbol.processor  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.http.server.undertow  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.kafka  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.kafka.symbol.processor  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.scheduling.common  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.telemetry.common  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.aop.symbol.processor  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.application.graph  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2023-09..2026-04 1.2.14       |.............=======|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.cache.annotation.processor  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.config.hocon  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.declarative.logging.symbol.processor  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.logging.common  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.mapstruct.java.extension  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.micrometer.module  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.opentelemetry.tracing  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.opentelemetry.tracing.exporter.http  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.scheduling.quartz  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.validation.module  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.database.jdbc  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.database.symbol.processor  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.http.client.jdk  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.logging.logback  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.mapstruct.ksp.extension  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.symbol.processors  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.validation.annotation.processor  [owned by `ru.tinkoff.kora` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * ru.tinkoff.kora                      2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework                     2026-03..2026-03 2.0.0.alpha6 |...................=|
kotlinx.serialization.protobuf  [owned by `org.jetbrains.kotlinx` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.jetbrains.kotlinx                2021-09..2026-04 1.11.0       |.........===========|
  ?   org.danbrough.kotlinx                2022-09..2023-03 1.5.0        |...........==.......|
kotlinx.serialization.hocon  [owned by `org.jetbrains.kotlinx` (groupId minus two segments is the module prefix); 2 other group(s) shade the name]
  ? * org.jetbrains.kotlinx                2021-09..2026-04 1.11.0       |.........===========|
  ?   org.danbrough.kotlinx                2022-09..2023-03 1.5.0        |...........==.......|
  ?   ru.endlesscode.mimic                 2021-12..2022-04 0.8.0        |..........==........|
kotlinx.serialization.properties  [owned by `org.jetbrains.kotlinx` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.jetbrains.kotlinx                2021-09..2026-04 1.11.0       |.........===========|
  ?   org.danbrough.kotlinx                2022-09..2023-03 1.5.0        |...........==.......|
kotlinx.serialization.cbor  [owned by `org.jetbrains.kotlinx` (groupId minus two segments is the module prefix); 2 other group(s) shade the name]
  ? * org.jetbrains.kotlinx                2021-09..2026-04 1.11.0       |.........===========|
  ?   org.danbrough.kotlinx                2022-09..2023-03 1.5.0        |...........==.......|
  ?   io.github.pluginloader               2022-01..2022-01 1.0.0        |..........=.........|
kotlinx.atomicfu  [owned by `org.jetbrains.kotlinx` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.jetbrains.kotlinx                2021-11..2026-03 0.32.1       |..........==========|
  ?   org.danbrough.kotlinx                2022-08..2023-01 0.19.0a      |...........==.......|
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
jamal.asciidoc_COMPAT  [owned by `com.javax0.jamal` (groupId minus two segments is the module prefix); 0 other group(s) shade the name]
  ? * com.javax0.jamal                     2023-06..2024-12 2.8.2        |.............====...|
  ?   com.javax0.jamal.jamal-snippet.2.7.0.com.javax0.jamal 2024-06..2024-06 2.7.0        |...............=....|
jamal.word  [owned by `com.javax0.jamal` (groupId minus two segments is the module prefix); 0 other group(s) shade the name]
  ? * com.javax0.jamal                     2022-03..2024-12 2.8.2        |..........=======...|
  ?   com.javax0.jamal.jamal-snippet.2.7.0.com.javax0.jamal 2024-06..2024-06 2.7.0        |...............=....|
jamal.markdown  [owned by `com.javax0.jamal` (groupId minus two segments is the module prefix); 0 other group(s) shade the name]
  ? * com.javax0.jamal                     2021-05..2024-12 2.8.2        |.........========...|
  ?   com.javax0.jamal.jamal-snippet.2.7.0.com.javax0.jamal 2024-06..2024-06 2.7.0        |...............=....|
jamal.cmd  [owned by `com.javax0.jamal` (groupId minus two segments is the module prefix); 0 other group(s) shade the name]
  ? * com.javax0.jamal                     2020-08..2024-12 2.8.2        |.......==========...|
  ?   com.javax0.jamal.jamal-snippet.2.7.0.com.javax0.jamal 2024-06..2024-06 2.7.0        |...............=....|
jamal.doclet  [owned by `com.javax0.jamal` (groupId minus two segments is the module prefix); 0 other group(s) shade the name]
  ? * com.javax0.jamal                     2021-04..2024-12 2.8.2        |.........========...|
  ?   com.javax0.jamal.jamal-snippet.2.7.0.com.javax0.jamal 2024-06..2024-06 2.7.0        |...............=....|
jamal.testsupport  [owned by `com.javax0.jamal` (groupId minus two segments is the module prefix); 0 other group(s) shade the name]
  ? * com.javax0.jamal                     2019-01..2024-12 2.8.2        |....=============...|
  ?   com.javax0.jamal.jamal-snippet.2.7.0.com.javax0.jamal 2024-06..2024-06 2.7.0        |...............=....|
jamal.extensions  [owned by `com.javax0.jamal` (groupId minus two segments is the module prefix); 0 other group(s) shade the name]
  ? * com.javax0.jamal                     2019-03..2024-12 2.8.2        |....=============...|
  ?   com.javax0.jamal.jamal-snippet.2.7.0.com.javax0.jamal 2024-06..2024-06 2.7.0        |...............=....|
jamal.io  [owned by `com.javax0.jamal` (groupId minus two segments is the module prefix); 0 other group(s) shade the name]
  ? * com.javax0.jamal                     2021-04..2024-12 2.8.2        |.........========...|
  ?   com.javax0.jamal.jamal-snippet.2.7.0.com.javax0.jamal 2024-06..2024-06 2.7.0        |...............=....|
jamal.prog  [owned by `com.javax0.jamal` (groupId minus two segments is the module prefix); 0 other group(s) shade the name]
  ? * com.javax0.jamal                     2023-02..2024-12 2.8.2        |............=====...|
  ?   com.javax0.jamal.jamal-snippet.2.7.0.com.javax0.jamal 2024-06..2024-06 2.7.0        |...............=....|
jamal.openai  [owned by `com.javax0.jamal` (groupId minus two segments is the module prefix); 0 other group(s) shade the name]
  ? * com.javax0.jamal                     2023-03..2024-12 2.8.2        |.............====...|
  ?   com.javax0.jamal.jamal-snippet.2.7.0.com.javax0.jamal 2024-06..2024-06 2.7.0        |...............=....|
jamal.sql  [owned by `com.javax0.jamal` (groupId minus two segments is the module prefix); 0 other group(s) shade the name]
  ? * com.javax0.jamal                     2024-05..2024-12 2.8.2        |...............==...|
  ?   com.javax0.jamal.jamal-snippet.2.7.0.com.javax0.jamal 2024-06..2024-06 2.7.0        |...............=....|
jamal.tools  [owned by `com.javax0.jamal` (groupId minus two segments is the module prefix); 0 other group(s) shade the name]
  ? * com.javax0.jamal                     2018-11..2024-12 2.8.2        |....=============...|
  ?   com.javax0.jamal.jamal-snippet.2.7.0.com.javax0.jamal 2024-06..2024-06 2.7.0        |...............=....|
jamal.snippet  [owned by `com.javax0.jamal` (groupId minus two segments is the module prefix); 0 other group(s) shade the name]
  ? * com.javax0.jamal                     2021-01..2024-12 2.8.2        |........=========...|
  ?   com.javax0.jamal.jamal-snippet.2.7.0.com.javax0.jamal 2024-06..2024-06 2.7.0        |...............=....|
jamal.engine  [owned by `com.javax0.jamal` (groupId minus two segments is the module prefix); 0 other group(s) shade the name]
  ? * com.javax0.jamal                     2018-11..2024-12 2.8.2        |....=============...|
  ?   com.javax0.jamal.jamal-snippet.2.7.0.com.javax0.jamal 2024-06..2024-06 2.7.0        |...............=....|
jamal.json  [owned by `com.javax0.jamal` (groupId minus two segments is the module prefix); 0 other group(s) shade the name]
  ? * com.javax0.jamal                     2023-03..2024-12 2.8.2        |.............====...|
  ?   com.javax0.jamal.jamal-snippet.2.7.0.com.javax0.jamal 2024-06..2024-06 2.7.0        |...............=....|
jamal.scriptbasic  [owned by `com.javax0.jamal` (groupId minus two segments is the module prefix); 0 other group(s) shade the name]
  ? * com.javax0.jamal                     2021-02..2024-12 2.8.2        |........=========...|
  ?   com.javax0.jamal.jamal-snippet.2.7.0.com.javax0.jamal 2024-06..2024-06 2.7.0        |...............=....|
jamal.asciidoc  [owned by `com.javax0.jamal` (groupId minus two segments is the module prefix); 0 other group(s) shade the name]
  ? * com.javax0.jamal                     2022-03..2024-12 2.8.2        |..........=======...|
  ?   com.javax0.jamal.jamal-snippet.2.7.0.com.javax0.jamal 2024-06..2024-06 2.7.0        |...............=....|
jamal.groovy  [owned by `com.javax0.jamal` (groupId minus two segments is the module prefix); 0 other group(s) shade the name]
  ? * com.javax0.jamal                     2021-02..2024-12 2.8.2        |........=========...|
  ?   com.javax0.jamal.jamal-snippet.2.7.0.com.javax0.jamal 2024-06..2024-06 2.7.0        |...............=....|
jamal.maven  [owned by `com.javax0.jamal` (groupId minus two segments is the module prefix); 0 other group(s) shade the name]
  ? * com.javax0.jamal                     2018-11..2024-12 2.8.2        |....=============...|
  ?   com.javax0.jamal.jamal-snippet.2.7.0.com.javax0.jamal 2024-06..2024-06 2.7.0        |...............=....|
jamal.maven.input  [owned by `com.javax0.jamal` (groupId minus two segments is the module prefix); 0 other group(s) shade the name]
  ? * com.javax0.jamal                     2023-02..2024-12 2.8.2        |............=====...|
  ?   com.javax0.jamal.jamal-snippet.2.7.0.com.javax0.jamal 2024-06..2024-06 2.7.0        |...............=....|
jamal.test  [owned by `com.javax0.jamal` (groupId minus two segments is the module prefix); 0 other group(s) shade the name]
  ? * com.javax0.jamal                     2021-02..2024-12 2.8.2        |........=========...|
  ?   com.javax0.jamal.jamal-snippet.2.7.0.com.javax0.jamal 2024-06..2024-06 2.7.0        |...............=....|
jamal.yaml  [owned by `com.javax0.jamal` (groupId minus two segments is the module prefix); 0 other group(s) shade the name]
  ? * com.javax0.jamal                     2021-04..2024-12 2.8.2        |.........========...|
  ?   com.javax0.jamal.jamal-snippet.2.7.0.com.javax0.jamal 2024-06..2024-06 2.7.0        |...............=....|
jamal.java  [owned by `com.javax0.jamal` (groupId minus two segments is the module prefix); 0 other group(s) shade the name]
  ? * com.javax0.jamal                     2023-02..2024-12 2.8.2        |............=====...|
  ?   com.javax0.jamal.jamal-snippet.2.7.0.com.javax0.jamal 2024-06..2024-06 2.7.0        |...............=....|
jamal.mock  [owned by `com.javax0.jamal` (groupId minus two segments is the module prefix); 0 other group(s) shade the name]
  ? * com.javax0.jamal                     2022-10..2024-12 2.8.2        |............=====...|
  ?   com.javax0.jamal.jamal-snippet.2.7.0.com.javax0.jamal 2024-06..2024-06 2.7.0        |...............=....|
jamal.jamal  [owned by `com.javax0.jamal` (groupId minus two segments is the module prefix); 0 other group(s) shade the name]
  ? * com.javax0.jamal                     2021-04..2024-12 2.8.2        |.........========...|
  ?   com.javax0.jamal.jamal-snippet.2.7.0.com.javax0.jamal 2024-06..2024-06 2.7.0        |...............=....|
jamal.ruby  [owned by `com.javax0.jamal` (groupId minus two segments is the module prefix); 0 other group(s) shade the name]
  ? * com.javax0.jamal                     2021-02..2024-12 2.8.2        |........=========...|
  ?   com.javax0.jamal.jamal-snippet.2.7.0.com.javax0.jamal 2024-06..2024-06 2.7.0        |...............=....|
jamal.maven.load  [owned by `com.javax0.jamal` (groupId minus two segments is the module prefix); 0 other group(s) shade the name]
  ? * com.javax0.jamal                     2023-03..2024-12 2.8.2        |.............====...|
  ?   com.javax0.jamal.jamal-snippet.2.7.0.com.javax0.jamal 2024-06..2024-06 2.7.0        |...............=....|
jamal.jar.input  [owned by `com.javax0.jamal` (groupId minus two segments is the module prefix); 0 other group(s) shade the name]
  ? * com.javax0.jamal                     2023-03..2024-12 2.8.2        |.............====...|
  ?   com.javax0.jamal.jamal-snippet.2.7.0.com.javax0.jamal 2024-06..2024-06 2.7.0        |...............=....|
jamal.assertions  [owned by `com.javax0.jamal` (groupId minus two segments is the module prefix); 0 other group(s) shade the name]
  ? * com.javax0.jamal                     2021-10..2024-12 2.8.2        |..........=======...|
  ?   com.javax0.jamal.jamal-snippet.2.7.0.com.javax0.jamal 2024-06..2024-06 2.7.0        |...............=....|
jamal.debug  [owned by `com.javax0.jamal` (groupId minus two segments is the module prefix); 0 other group(s) shade the name]
  ? * com.javax0.jamal                     2021-04..2024-12 2.8.2        |.........========...|
  ?   com.javax0.jamal.jamal-snippet.2.7.0.com.javax0.jamal 2024-06..2024-06 2.7.0        |...............=....|
jamal.core  [owned by `com.javax0.jamal` (groupId minus two segments is the module prefix); 0 other group(s) shade the name]
  ? * com.javax0.jamal                     2018-11..2024-12 2.8.2        |....=============...|
  ?   com.javax0.jamal.jamal-snippet.2.7.0.com.javax0.jamal 2024-06..2024-06 2.7.0        |...............=....|
jamal.api  [owned by `com.javax0.jamal` (groupId minus two segments is the module prefix); 0 other group(s) shade the name]
  ? * com.javax0.jamal                     2018-11..2024-12 2.8.2        |....=============...|
  ?   com.javax0.jamal.jamal-snippet.2.7.0.com.javax0.jamal 2024-06..2024-06 2.7.0        |...............=....|
akka.discovery  [owned by `com.typesafe.akka` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * com.typesafe.akka                    2018-12..2024-10 2.8.8        |....=============...|
  ?   com.sandinh                          2020-07..2020-07 2.6.8-sd     |.......=............|
akka.actor.typed  [owned by `com.typesafe.akka` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * com.typesafe.akka                    2018-01..2024-10 2.8.8        |..===============...|
  ?   com.sandinh                          2020-07..2020-07 2.6.8-sd     |.......=............|
akka.coordination  [owned by `com.typesafe.akka` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * com.typesafe.akka                    2019-04..2024-10 2.8.8        |....=============...|
  ?   com.sandinh                          2020-07..2020-07 2.6.8-sd     |.......=............|
akka.pki  [owned by `com.typesafe.akka` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * com.typesafe.akka                    2020-06..2024-10 2.8.8        |.......==========...|
  ?   com.sandinh                          2020-07..2020-07 2.6.8-sd     |.......=............|
akka.protobuf.v3  [owned by `com.typesafe.akka` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * com.typesafe.akka                    2019-08..2024-10 2.8.8        |.....============...|
  ?   com.sandinh                          2020-07..2020-07 2.6.8-sd     |.......=............|
akka.actor  [owned by `com.typesafe.akka` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * com.typesafe.akka                    2018-02..2024-10 2.8.8        |..===============...|
  ?   com.sandinh                          2020-07..2020-07 2.6.8-sd     |.......=............|
akka.actor.testkit.typed  [owned by `com.typesafe.akka` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * com.typesafe.akka                    2018-05..2024-10 2.8.8        |...==============...|
  ?   com.sandinh                          2020-07..2020-07 2.6.8-sd     |.......=............|
akka.actor.testkit  [owned by `com.typesafe.akka` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * com.typesafe.akka                    2018-02..2024-10 2.8.8        |..===============...|
  ?   com.sandinh                          2020-07..2020-07 2.6.8-sd     |.......=............|
akka.slf4j  [owned by `com.typesafe.akka` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * com.typesafe.akka                    2018-02..2024-10 2.8.8        |..===============...|
  ?   com.sandinh                          2020-07..2020-07 2.6.8-sd     |.......=............|
akka.osgi  [owned by `com.typesafe.akka` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * com.typesafe.akka                    2018-02..2024-10 2.7.1        |..===============...|
  ?   com.sandinh                          2020-07..2020-07 2.6.8-sd     |.......=............|
akka.protobuf  [owned by `com.typesafe.akka` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * com.typesafe.akka                    2018-02..2024-10 2.7.1        |..===============...|
  ?   com.sandinh                          2020-07..2020-07 2.6.8-sd     |.......=............|
kotlin.test.junit  [owned by `org.jetbrains.kotlin` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * org.jetbrains.kotlin                 2020-07..2023-08 1.9.10       |.......=======......|
  ?   org.archguard.scanner                2022-06..2022-12 2.0.0-beta.5 |...........==.......|
  ?   org.jetbrains.lets-plot              2021-04..2021-06 2.0.4        |.........=..........|
akka.stream.alpakka.kafka  [owned by `com.typesafe.akka` (groupId minus two segments is the module prefix); 1 other group(s) shade the name]
  ? * com.typesafe.akka                    2018-06..2023-04 4.0.2        |...===========......|
  ?   com.codacy                           2019-03..2019-03 1.0.1        |....=...............|
cactus.maven.xml  [owned by `com.telenav.cactus` (groupId minus two segments is the module prefix); 0 other group(s) shade the name]
  ? * com.telenav.cactus                   2022-06..2022-11 1.5.49       |...........==.......|
  ?   com.telenav.lexakai                  2022-09..2022-10 1.0.13       |...........==.......|
```

## unclassified (109)

Multiple publishers with no natural-namespace owner present (the module name matches no publisher's groupId): a genuine collision the heuristic cannot settle. Proposal: keep the current owner, but review by hand.

| count | current owner -> proposed allowed |
|---:|---|
| 17 | `org.jmonkeyengine -> org.jmonkeyengine` |
| 14 | `org.openjfx -> org.openjfx` |
| 11 | `eu.europa.ec.joinup.sd-dss -> eu.europa.ec.joinup.sd-dss` |
| 7 | `ru.tinkoff.kora.experimental -> ru.tinkoff.kora.experimental` |
| 5 | `io.github.spair -> io.github.spair` |
| 5 | `org.glassfish.jaxb -> org.glassfish.jaxb` |
| 3 | `com.graphql-java -> com.graphql-java` |
| 3 | `com.squareup.okhttp3 -> com.squareup.okhttp3` |
| 3 | `io.github.openfeign -> io.github.openfeign` |
| 2 | `com.github.vladimir-bukhtoyarov -> com.github.vladimir-bukhtoyarov` |
| 2 | `io.dropwizard.metrics -> io.dropwizard.metrics` |
| 2 | `io.dropwizard.metrics5 -> io.dropwizard.metrics5` |
| 2 | `io.github.palexdev -> io.github.palexdev` |
| 2 | `org.apache.tomcat -> org.apache.tomcat` |
| 2 | `org.slf4j -> org.slf4j` |

```
com.graphqljava  [no clear owner; `com.graphql-java` is earliest and most recent]
  ? * com.graphql-java                     2020-11..2026-05 0.0.0-2026-05-29T07-49-37-79b227e |........============|
  ?   com.liferay                          2025-05..2025-05 19.11.JAKARTA-LIFERAY-PATCHED-1 |.................=..|
  ?   io.github.my-workforce               2022-07..2023-07 19.6         |...........===......|
javafx.base  [no clear owner; `org.openjfx` is earliest and most recent]
  ? * org.openjfx                          2018-07..2026-05 27-ea+18     |...=================|
  ?   org.glavo.hmcl.openjfx               2022-08..2024-01 14.0.2.1-freebsd |...........====.....|
  ?   com.googlecode.blaisemath            2022-08..2023-02 0.5.4        |...........==.......|
  ?   org.jfxcore                          2020-12..2022-07 18-ea+1      |........====........|
  ?   it.unibo.tuprolog.argumentation      2021-01..2021-10 0.5.1        |........===.........|
  ?   it.unibo.tuprolog                    2020-10..2021-05 0.17.4       |........==..........|
    + 3 more: com.github.nkb03, com.vwo.sdk, xyz.gianlu.librespot
javafx.controls  [no clear owner; `org.openjfx` is earliest and most recent]
  ? * org.openjfx                          2018-07..2026-05 27-ea+18     |...=================|
  ?   pro.verron.office-stamper            2026-03..2026-03 3.2          |..................=.|
  ?   com.sta-soft                         2025-09..2025-09 1.1          |.................=..|
  ?   org.glavo.hmcl.openjfx               2022-08..2024-01 14.0.2.1-freebsd |...........====.....|
  ?   org.jfxcore                          2020-12..2022-07 18-ea+1      |........====........|
  ?   org.jetbrains.lets-plot              2021-10..2021-10 2.2.0-rc2    |..........=.........|
    + 1 more: io.github.martinheywang
javafx.fxml  [no clear owner; `org.openjfx` is earliest and most recent]
  ? * org.openjfx                          2018-07..2026-05 27-ea+18     |...=================|
  ?   de.fraunhofer.iosb.ilt               2022-03..2025-05 0.37         |..........========..|
  ?   org.glavo.hmcl.openjfx               2022-08..2023-08 17.0.8-loongarch64 |...........===......|
  ?   org.jfxcore                          2020-12..2022-07 18-ea+1      |........====........|
  ?   de.ipk-gatersleben                   2020-07..2020-07 3.0.2        |.......=............|
javafx.graphics  [no clear owner; `org.openjfx` is earliest and most recent]
  ? * org.openjfx                          2018-07..2026-05 27-ea+18     |...=================|
  ?   io.github.makbn                      2025-10..2025-10 2.0.0        |..................=.|
  ?   de.wenzlaff.twbibel                  2022-05..2024-12 0.1.1        |...........======...|
  ?   de.pirckheimer-gymnasium             2024-08..2024-08 3.1.0        |...............=....|
  ?   org.glavo.hmcl.openjfx               2022-08..2024-01 14.0.2.1-freebsd |...........====.....|
  ?   org.jfxcore                          2020-12..2022-07 18-ea+1      |........====........|
    + 1 more: com.robotaccomplice
javafx.graphicsEmpty  [no clear owner; `org.openjfx` is earliest and most recent]
  ? * org.openjfx                          2018-08..2026-05 27-ea+18     |...=================|
  ?   org.glavo.hmcl.openjfx               2022-08..2023-08 17.0.8-loongarch64 |...........===......|
  ?   org.jfxcore                          2020-12..2022-07 18-ea+1      |........====........|
javafx.media  [no clear owner; `org.openjfx` is earliest and most recent]
  ? * org.openjfx                          2018-07..2026-05 27-ea+18     |...=================|
  ?   net.kurobako                         2026-03..2026-03 0.8.0        |...................=|
  ?   org.glavo.hmcl.openjfx               2022-08..2023-08 17.0.8-loongarch64 |...........===......|
  ?   org.jfxcore                          2020-12..2022-07 18-ea+1      |........====........|
javafx.swing  [no clear owner; `org.openjfx` is earliest and most recent]
  ? * org.openjfx                          2018-07..2026-05 27-ea+18     |...=================|
  ?   org.glavo.hmcl.openjfx               2022-08..2023-08 17.0.8-loongarch64 |...........===......|
  ?   org.jfxcore                          2022-06..2022-07 18-ea+1      |...........=........|
  ?   de.ipk-gatersleben                   2021-05..2021-05 3.0.3        |.........=..........|
javafx.web  [no clear owner; `org.openjfx` is earliest and most recent]
  ? * org.openjfx                          2018-07..2026-05 27-ea+18     |...=================|
  ?   org.glavo.hmcl.openjfx               2022-08..2023-08 17.0.8-loongarch64 |...........===......|
  ?   org.jfxcore                          2020-12..2022-07 18-ea+1      |........====........|
  ?   de.ipk-gatersleben                   2019-04..2021-05 3.0.4        |....======..........|
javafx.webEmpty  [no clear owner; `org.openjfx` is earliest and most recent]
  ? * org.openjfx                          2018-08..2026-05 27-ea+18     |...=================|
  ?   org.glavo.hmcl.openjfx               2022-08..2023-08 17.0.8-loongarch64 |...........===......|
  ?   org.jfxcore                          2020-12..2022-07 18-ea+1      |........====........|
javafx.baseEmpty  [no clear owner; `org.openjfx` is earliest and most recent]
  ? * org.openjfx                          2018-08..2026-05 27-ea+18     |...=================|
  ?   org.glavo.hmcl.openjfx               2022-08..2023-08 17.0.8-loongarch64 |...........===......|
  ?   org.jfxcore                          2022-06..2022-07 18-ea+1      |...........=........|
javafx.controlsEmpty  [no clear owner; `org.openjfx` is earliest and most recent]
  ? * org.openjfx                          2018-08..2026-05 27-ea+18     |...=================|
  ?   org.glavo.hmcl.openjfx               2022-08..2023-08 17.0.8-loongarch64 |...........===......|
  ?   org.jfxcore                          2022-06..2022-07 18-ea+1      |...........=........|
javafx.fxmlEmpty  [no clear owner; `org.openjfx` is earliest and most recent]
  ? * org.openjfx                          2018-08..2026-05 27-ea+18     |...=================|
  ?   org.glavo.hmcl.openjfx               2022-08..2023-08 17.0.8-loongarch64 |...........===......|
  ?   org.jfxcore                          2022-06..2022-07 18-ea+1      |...........=........|
javafx.mediaEmpty  [no clear owner; `org.openjfx` is earliest and most recent]
  ? * org.openjfx                          2018-08..2026-05 27-ea+18     |...=================|
  ?   org.glavo.hmcl.openjfx               2022-08..2023-08 17.0.8-loongarch64 |...........===......|
  ?   org.jfxcore                          2020-12..2022-07 18-ea+1      |........====........|
javafx.swingEmpty  [no clear owner; `org.openjfx` is earliest and most recent]
  ? * org.openjfx                          2018-08..2026-05 27-ea+18     |...=================|
  ?   org.glavo.hmcl.openjfx               2022-08..2023-08 17.0.8-loongarch64 |...........===......|
  ?   org.jfxcore                          2022-06..2022-07 18-ea+1      |...........=........|
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
io.questdb.client  [no clear owner; `org.questdb` is earliest and most recent]
  ? * org.questdb                          2026-02..2026-05 1.3.2        |..................==|
  ?   io.github.sklarsa                    2026-05..2026-05 0.0.1        |...................=|
jme3.jbullet  [no clear owner; `org.jmonkeyengine` is earliest and most recent]
  ? * org.jmonkeyengine                    2021-03..2026-05 3.10.0-alpha5 |........============|
  ?   org.ngengine                         2025-06..2025-10 0.2.0        |.................==.|
jme3.testdata  [no clear owner; `org.jmonkeyengine` is earliest and most recent]
  ? * org.jmonkeyengine                    2021-03..2026-05 3.10.0-alpha5 |........============|
  ?   org.ngengine                         2025-06..2025-10 0.2.0        |.................==.|
jme3.android  [no clear owner; `org.jmonkeyengine` is earliest and most recent]
  ? * org.jmonkeyengine                    2021-03..2026-05 3.10.0-alpha5 |........============|
  ?   org.ngengine                         2025-06..2025-10 0.2.0        |.................==.|
jme3.desktop  [no clear owner; `org.jmonkeyengine` is earliest and most recent]
  ? * org.jmonkeyengine                    2021-03..2026-05 3.10.0-alpha5 |........============|
  ?   org.ngengine                         2025-06..2025-10 0.2.0        |.................==.|
jme3.examples  [no clear owner; `org.jmonkeyengine` is earliest and most recent]
  ? * org.jmonkeyengine                    2021-03..2026-05 3.10.0-alpha5 |........============|
  ?   org.ngengine                         2025-06..2025-10 0.2.0        |.................==.|
jme3.jogg  [no clear owner; `org.jmonkeyengine` is earliest and most recent]
  ? * org.jmonkeyengine                    2021-03..2026-05 3.10.0-alpha5 |........============|
  ?   org.ngengine                         2025-06..2025-10 0.2.0        |.................==.|
jme3.lwjgl3  [no clear owner; `org.jmonkeyengine` is earliest and most recent]
  ? * org.jmonkeyengine                    2021-03..2026-05 3.10.0-alpha5 |........============|
  ?   org.ngengine                         2025-06..2025-10 0.2.0        |.................==.|
jme3.plugins.json  [no clear owner; `org.jmonkeyengine` is earliest and most recent]
  ? * org.jmonkeyengine                    2024-02..2026-05 3.10.0-alpha5 |..............======|
  ?   org.ngengine                         2025-06..2025-10 0.2.0        |.................==.|
jme3.plugins.json.gson  [no clear owner; `org.jmonkeyengine` is earliest and most recent]
  ? * org.jmonkeyengine                    2024-02..2026-05 3.10.0-alpha5 |..............======|
  ?   org.ngengine                         2025-06..2025-10 0.2.0        |.................==.|
jme3.screenshot.tests  [no clear owner; `org.jmonkeyengine` is earliest and most recent]
  ? * org.jmonkeyengine                    2025-01..2026-05 3.10.0-alpha5 |................====|
  ?   org.ngengine                         2025-06..2025-10 0.2.0        |.................==.|
jme3.terrain  [no clear owner; `org.jmonkeyengine` is earliest and most recent]
  ? * org.jmonkeyengine                    2021-03..2026-05 3.10.0-alpha5 |........============|
  ?   org.ngengine                         2025-06..2025-10 0.2.0        |.................==.|
jme3.awt.dialogs  [no clear owner; `org.jmonkeyengine` is earliest and most recent]
  ? * org.jmonkeyengine                    2023-01..2026-05 3.10.0-alpha5 |............========|
  ?   org.ngengine                         2025-06..2025-10 0.2.0        |.................==.|
jme3.core  [no clear owner; `org.jmonkeyengine` is earliest and most recent]
  ? * org.jmonkeyengine                    2021-03..2026-05 3.10.0-alpha5 |........============|
  ?   org.ngengine                         2025-06..2025-10 0.2.0        |.................==.|
jme3.effects  [no clear owner; `org.jmonkeyengine` is earliest and most recent]
  ? * org.jmonkeyengine                    2021-03..2026-05 3.10.0-alpha5 |........============|
  ?   org.ngengine                         2025-06..2025-10 0.2.0        |.................==.|
jme3.ios  [no clear owner; `org.jmonkeyengine` is earliest and most recent]
  ? * org.jmonkeyengine                    2021-03..2026-05 3.10.0-alpha5 |........============|
  ?   org.ngengine                         2025-06..2025-10 0.2.0        |.................==.|
jme3.networking  [no clear owner; `org.jmonkeyengine` is earliest and most recent]
  ? * org.jmonkeyengine                    2021-03..2026-05 3.10.0-alpha5 |........============|
  ?   org.ngengine                         2025-06..2025-10 0.2.0        |.................==.|
jme3.plugins  [no clear owner; `org.jmonkeyengine` is earliest and most recent]
  ? * org.jmonkeyengine                    2021-03..2026-05 3.10.0-alpha5 |........============|
  ?   org.ngengine                         2025-06..2025-10 0.2.0        |.................==.|
com.codahale.metrics.health  [no clear owner; `io.dropwizard.metrics5` is earliest and most recent]
  ? * io.dropwizard.metrics5               2018-02..2026-05 5.0.7        |..==================|
  ?   io.dropwizard.metrics                2018-03..2026-05 4.2.39       |..==================|
com.codahale.metrics  [no clear owner; `io.dropwizard.metrics5` is earliest and most recent]
  ? * io.dropwizard.metrics5               2018-02..2026-05 5.0.7        |..==================|
  ?   io.dropwizard.metrics                2018-03..2026-05 4.2.39       |..==================|
  ?   com.guicedee.modules.services        2026-04..2026-05 2.0.2        |...................=|
com.codahale.metrics.jetty9  [no clear owner; `io.dropwizard.metrics` is earliest and most recent]
  ? * io.dropwizard.metrics                2018-03..2026-05 4.2.39       |..==================|
  ?   io.dropwizard.metrics5               2020-12..2020-12 5.0.0-rc4    |........=...........|
com.codahale.metrics.logback  [no clear owner; `io.dropwizard.metrics` is earliest and most recent]
  ? * io.dropwizard.metrics                2018-12..2026-05 4.2.39       |....================|
  ?   io.dropwizard.metrics5               2019-08..2020-12 5.0.0-rc4    |.....====...........|
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
org.chocosolver.solver  [no clear owner; `org.choco-solver` is earliest and most recent]
  ? * org.choco-solver                     2020-07..2026-05 6.0.1        |.......=============|
  ?   io.gitlab.chaver                     2022-09..2023-08 1.0.2        |...........===......|
  ?   es.us.isa                            2023-04..2023-04 1.0.0        |.............=......|
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
kora.camunda.engine.bpmn  [no clear owner; `ru.tinkoff.kora.experimental` is earliest and most recent]
  ? * ru.tinkoff.kora.experimental         2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework.experimental        2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.camunda.zeebe.worker.symbol.processor  [no clear owner; `ru.tinkoff.kora.experimental` is earliest and most recent]
  ? * ru.tinkoff.kora.experimental         2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework.experimental        2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.camunda.rest.undertow  [no clear owner; `ru.tinkoff.kora.experimental` is earliest and most recent]
  ? * ru.tinkoff.kora.experimental         2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework.experimental        2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.s3.client.symbol.processor  [no clear owner; `ru.tinkoff.kora.experimental` is earliest and most recent]
  ? * ru.tinkoff.kora.experimental         2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework.experimental        2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.camunda.zeebe.worker  [no clear owner; `ru.tinkoff.kora.experimental` is earliest and most recent]
  ? * ru.tinkoff.kora.experimental         2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework.experimental        2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.camunda.zeebe.worker.annotation.processor  [no clear owner; `ru.tinkoff.kora.experimental` is earliest and most recent]
  ? * ru.tinkoff.kora.experimental         2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework.experimental        2026-03..2026-03 2.0.0.alpha6 |...................=|
kora.s3.client.annotation.processor  [no clear owner; `ru.tinkoff.kora.experimental` is earliest and most recent]
  ? * ru.tinkoff.kora.experimental         2025-01..2026-04 1.2.14       |................====|
  ?   io.koraframework.experimental        2026-03..2026-03 2.0.0.alpha6 |...................=|
com.oracle.truffle.regex  [no clear owner; `org.graalvm.regex` is earliest and most recent]
  ? * org.graalvm.regex                    2018-10..2026-04 25.0.3       |...=================|
  ?   org.noear                            2024-09..2025-07 1.9.6        |................==..|
  ?   com.syncloop.middleware              2025-01..2025-01 1.7.1        |................=...|
feign.gson  [no clear owner; `io.github.openfeign` is earliest and most recent]
  ? * io.github.openfeign                  2023-02..2026-04 13.12        |............========|
  ?   com.appdynamics                      2025-10..2025-10 25.08.1524   |..................=.|
feign.jackson  [no clear owner; `io.github.openfeign` is earliest and most recent]
  ? * io.github.openfeign                  2023-02..2026-04 13.12        |............========|
  ?   io.github.jhkim593                   2025-03..2025-03 1.0.0        |................=...|
feign.kotlin  [no clear owner; `io.github.openfeign` is earliest and most recent]
  ? * io.github.openfeign                  2023-02..2026-04 13.12        |............========|
  ?   io.github.sunny-chung                2024-02..2024-02 13.2.1-patch-1 |..............=.....|
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
com.graphqljava.extendedscalars  [no clear owner; `com.graphql-java` is earliest and most recent]
  ? * com.graphql-java                     2022-09..2026-02 0.0.0-2026-02-15T04-08-22-545cf7f |...........========.|
  ?   com.liferay                          2025-05..2025-05 19.1.JAKARTA-LIFERAY-PATCHED-1 |.................=..|
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

