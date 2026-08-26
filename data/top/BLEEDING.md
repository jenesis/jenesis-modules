# Maven Central most downloaded artifacts vs. modules (bleeding edge)

_Bleeding edge: the 2025 top-artifact list assessed against current data, as of 2026-08-13; nothing is cropped to a year end, and the ⚠️ / 🚩 flags use rolling 12- and 36-month windows._

**By artifact**

| Category | All listed | Libraries | Maintained |
|---|---|---|---|
| Total artifacts | 1000 (100,0%) | 697 (100,0%) | 488 (100,0%) |
| Modular artifacts | 454 (45,4%) | 442 (63,4%) | 367 (75,2%) |
| Automatic modules | 280 (28,0%) | 273 (39,2%) | 226 (46,3%) |
| Named modules | 174 (17,4%) | 169 (24,2%) | 141 (28,9%) |
| Named modules with declared version | 128 (12,8%) | 123 (17,6%) | 103 (21,1%) |
| Non-modular artifacts | 546 (54,6%) | 255 (36,6%) | 121 (24,8%) |
| Artifacts whose group is over the file limit | 240 (24,0%) | 192 (27,5%) | 187 (38,3%) |
| Artifacts whose group is over the size limit | 308 (30,8%) | 251 (36,0%) | 237 (48,6%) |
| Artifacts whose group is over the release limit | 71 (7,1%) | 62 (8,9%) | 60 (12,3%) |
| Artifacts whose group is over any limit | 334 (33,4%) | 271 (38,9%) | 257 (52,7%) |

**By groupId**

| Category | All listed | Libraries | Maintained |
|---|---|---|---|
| Total groups | 354 (100,0%) | 299 (100,0%) | 174 (100,0%) |
| Groups without modules | 163 (46,0%) | 113 (37,8%) | 41 (23,6%) |
| Partial modularized groups | 191 (54,0%) | 186 (62,2%) | 133 (76,4%) |
| Groups with full modularization | 105 (29,7%) | 147 (49,2%) | 116 (66,7%) |
| Groups with named modules only | 82 (23,2%) | 82 (27,4%) | 63 (36,2%) |
| Groups with automatic modules only | 100 (28,2%) | 97 (32,4%) | 66 (37,9%) |
| Groups with modules and version info only | 66 (18,6%) | 66 (22,1%) | 52 (29,9%) |
| Groups over the file limit | 30 (8,5%) | 22 (7,4%) | 21 (12,1%) |
| Groups over the size limit | 42 (11,9%) | 33 (11,0%) | 30 (17,2%) |
| Groups over the release limit | 10 (2,8%) | 8 (2,7%) | 8 (4,6%) |
| Groups over any limit | 50 (14,1%) | 39 (13,0%) | 36 (20,7%) |

Counts are absolute with the share in parentheses. "All listed" covers all 1000 artifacts; "Libraries" excludes the 303 struck rows that cannot reflect module adoption (140 Maven build-tooling, 162 POM-only parents/BOMs/dependencies, 1 placeholder artifact) and is over the remaining 697. "Maintained" further drops library artifacts with no release during the last 12 months (the ⚠️ / 🚩 flagged ones), leaving 488. Everything is as of 2026-08-13. Artifact shares are of total artifacts; group shares are of total groups. "Partial modularized groups" have at least one artifact whose latest version carries a module; "full modularization" is the subset where every artifact does; the named/automatic/version rows classify groups whose modules are exclusively of that kind.

Every figure is as of 2026-08-13, and each artifact is judged by its latest version on or before that date: the module columns describe that version's module and are blank when the latest version carries none, even if an earlier version did. Its name, type (⚙️ automatic, 🏷️ named, ✳️ named with a module-info version) and version come from that latest version; the last-publication date and latest artifact version are from the latest scanned publication on or before it. A ⚠️ marks an artifact with no release during the last 12 months, a 🚩 one that looks deserted (no release in the last three years). Ages are in years (comma-decimal) measured to that date: artifact age from the artifact's first publication, module age from its first module publication. The trailing counts are distinct versions: the "released" totals cover everything up to the year end, "in year" only the report year, and the module counts only versions that carried a Java module. Three kinds of row are shown struck through and excluded from the Libraries column, as they crowd these rankings for reasons unrelated to module adoption: Maven's own build tooling (140 rows: Maven, Plexus, Sonatype/Sisu/Aether), POM-only aggregators (162 rows: parents, BOMs and dependency imports, which ship no JAR), and hand-listed placeholder artifacts (1 row).

The last five columns measure publishing against Maven Central's free thresholds, which since 2026-06-16 are notified as soft limits and become enforceable on 2026-10-01: 1,167 files, 78 MB and 7 releases per month, the 90th percentile of all publishers. They describe the row's whole **groupId**, not its single artifact, because that is the unit Central caps, so every row sharing a groupId carries the same figures and "Group artifacts" says how many artifacts they cover. Here 221 groups published inside the window, across 10517 artifacts, which is 47,6 artifacts per group on average, against the 1000 the list itself names.

A release is a distinct version across the group's artifacts, since a multi-module project publishes one version over many artifacts in a single deployment. "Files per release" and "MB per release" divide the group's window totals by those releases, counting every file the repository serves under a version - the artifacts, the POM, and the signature and checksum sidecars beside them - because Central counts the same set. A 🔺 marks a figure whose monthly volume is above its threshold, and "Over Central limit" names them. Central averages monthly volume over a rolling three months rather than bucketing it by calendar month, so these figures divide the window's twelve months evenly; a group that published in one burst can therefore breach at Central while its yearly mean here stays under.

Read every one of these as a **best case**. Central applies a limit to an organization, and an organization may hold several namespaces: `org.springframework`, `org.springframework.boot` and `org.springframework.security` are separate rows here and may well be one account there. A group counted as under a threshold can therefore still belong to an organization over it, never the reverse. The thresholds themselves may also move during the soft-limit phase, since the Usage Center, not this table, is their source of truth.

| Top | Artifact | Module | Last publication | Artifact age | Module age | Latest artifact version | Latest module version | Total released artifacts (all versions) | Total released modules (all versions) | Artifacts released in last 12 months | Modules released in last 12 months | Group artifacts | Files per release | MB per release | Releases per month | Over Central limit |
|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|---|
| ~~1~~ | ~~org.apache.commons:commons-parent~~ | ~~-~~ | ~~2026-06-07~~ | ~~3,9~~ | ~~-~~ | ~~102~~ | ~~-~~ | ~~49~~ | ~~0~~ | ~~17~~ | ~~0~~ | ~~36~~ | ~~52,4~~ | ~~3,2~~ | ~~3,3~~ | ~~-~~ |
| ~~2~~ | ~~org.codehaus.plexus:plexus~~ | ~~-~~ | ~~2026-08-08~~ | ~~0,0~~ | ~~-~~ | ~~26~~ | ~~-~~ | ~~1~~ | ~~0~~ | ~~1~~ | ~~0~~ | ~~21~~ | ~~45,4~~ | ~~0,8~~ | ~~2,7~~ | ~~-~~ |
| ~~3~~ | ~~com.fasterxml.jackson:jackson-bom~~ | ~~-~~ | ~~2026-07-07~~ | ~~0,2~~ | ~~-~~ | ~~2.18.9~~ | ~~-~~ | ~~6~~ | ~~0~~ | ~~6~~ | ~~0~~ | ~~3~~ | ~~11,1~~ | ~~0,0~~ | ~~0,6~~ | ~~-~~ |
| ~~4~~ | ~~com.fasterxml:oss-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~2~~ | ~~24,0~~ | ~~0,9~~ | ~~0,4~~ | ~~-~~ |
| ~~5~~ | ~~org.sonatype.oss:oss-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| ~~6~~ | ~~org.slf4j:slf4j-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~12~~ | ~~294,0~~ | ~~3,0~~ | ~~0,1~~ | ~~-~~ |
| ~~7~~ | ~~com.google.guava:guava-parent 🚩~~ | ~~-~~ | ~~2015-09-17~~ | ~~16,3~~ | ~~-~~ | ~~19.0-rc2~~ | ~~-~~ | ~~36~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~3~~ | ~~75,0~~ | ~~22,6~~ | ~~0,3~~ | ~~-~~ |
| ~~8~~ | ~~com.fasterxml.jackson:jackson-parent~~ | ~~-~~ | ~~2026-05-31~~ | ~~0,2~~ | ~~-~~ | ~~2.22~~ | ~~-~~ | ~~1~~ | ~~0~~ | ~~1~~ | ~~0~~ | ~~3~~ | ~~11,1~~ | ~~0,0~~ | ~~0,6~~ | ~~-~~ |
| ~~9~~ | ~~org.junit:junit-bom~~ | ~~-~~ | ~~2026-08-07~~ | ~~0,2~~ | ~~-~~ | ~~6.1.3~~ | ~~-~~ | ~~3~~ | ~~0~~ | ~~3~~ | ~~0~~ | ~~2~~ | ~~50,8~~ | ~~2,6~~ | ~~0,4~~ | ~~-~~ |
| ~~10~~ | ~~org.codehaus.plexus:plexus-utils~~ | ~~org.codehaus.plexus.util ⚙️~~ | ~~2026-04-01~~ | ~~20,8~~ | ~~0,4~~ | ~~3.6.1~~ | ~~3.6.1~~ | ~~83~~ | ~~2~~ | ~~2~~ | ~~2~~ | ~~21~~ | ~~45,4~~ | ~~0,8~~ | ~~2,7~~ | ~~-~~ |
| 11 | com.fasterxml.jackson:jackson-base |  | 2026-07-07 | 0,2 |  | 2.18.9 |  | 6 | 0 | 6 | 0 | 3 | 11,1 | 0,0 | 0,6 | - |
| 12 | com.google.errorprone:error_prone_parent |  | 2026-06-10 | 0,2 |  | 2.50.0 |  | 1 | 0 | 1 | 0 | 10 | 210,7 | 69,3 | 0,8 | - |
| 13 | commons-io:commons-io | org.apache.commons.io ✳️ | 2026-04-19 | 20,8 | 8,8 | 2.22.0 | 2.22.0 | 35 | 19 | 2 | 2 | 1 | 36,0 | 7,3 | 0,2 | - |
| 14 | org.eclipse.ee4j:project |  | 2026-05-19 | 0,2 |  | 2.0.4 |  | 2 | 0 | 2 | 0 | 1 | 6,0 | 0,0 | 0,2 | - |
| ~~15~~ | ~~org.apache.maven:maven~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~36~~ | ~~360,2~~ | ~~12,5~~ | ~~0,8~~ | ~~-~~ |
| ~~16~~ | ~~org.apache.maven.shared:maven-shared-components~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~4~~ | ~~29,0~~ | ~~0,6~~ | ~~0,5~~ | ~~-~~ |
| 17 | org.slf4j:slf4j-api | org.slf4j ✳️ | 2026-05-12 | 19,9 | 9,3 | 2.0.18 | 2.0.18 | 107 | 48 | 1 | 1 | 12 | 294,0 | 3,0 | 0,1 | - |
| ~~18~~ | ~~org.sonatype.forge:forge-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| 19 | com.google.guava:guava | com.google.common ✳️ | 2026-04-14 | 16,3 | 8,8 | 33.6.0-jre | 33.6.0-jre | 154 | 92 | 4 | 4 | 3 | 75,0 | 22,6 | 0,3 | - |
| ~~20~~ | ~~org.codehaus.plexus:plexus-containers~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~21~~ | ~~45,4~~ | ~~0,8~~ | ~~2,7~~ | ~~-~~ |
| ~~21~~ | ~~org.apache.maven.plugins:maven-plugins~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~35~~ | ~~44,4~~ | ~~2,7~~ | ~~3,1~~ | ~~-~~ |
| 22 | com.fasterxml.jackson.core:jackson-core | com.fasterxml.jackson.core ✳️ | 2026-07-07 | 14,5 | 8,9 | 2.18.9 | 2.18.9 | 188 | 97 | 18 | 17 | 3 | 76,0 | 10,2 | 1,8 | - |
| 23 | com.fasterxml.jackson.core:jackson-databind | com.fasterxml.jackson.databind ✳️ | 2026-07-08 | 14,5 | 8,9 | 2.18.9 | 2.18.9 | 229 | 115 | 18 | 18 | 3 | 76,0 | 10,2 | 1,8 | - |
| ~~24~~ | ~~org.codehaus.plexus:plexus-components~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~21~~ | ~~45,4~~ | ~~0,8~~ | ~~2,7~~ | ~~-~~ |
| 25 | com.fasterxml.jackson.core:jackson-annotations | com.fasterxml.jackson.annotation ✳️ | 2026-07-07 | 14,5 | 8,9 | 2.18.9 | 2.18.9 | 182 | 94 | 10 | 10 | 3 | 76,0 | 10,2 | 1,8 | - |
| 26 | org.checkerframework:checker-qual | org.checkerframework.checker.qual 🏷️ | 2026-08-06 | 12,4 | 8,0 | 4.2.2 | 4.2.2 | 156 | 101 | 15 | 15 | 11 | 402,7 | 53,6 🔺 | 1,5 | 🔺 size |
| 27 | org.ow2.asm:asm | org.objectweb.asm ✳️ | 2026-05-23 | 14,7 | 9,1 | 9.10.1 | 9.10.1 | 45 | 33 | 4 | 4 | 7 | 242,5 | 2,2 | 0,3 | - |
| 28 | org.jetbrains.kotlin:kotlin-stdlib | kotlin.stdlib 🏷️ | 2026-07-22 | 13,1 | 6,1 | 2.4.20-Beta2 | 2.4.20-Beta2 | 297 | 156 | 35 | 35 | 175 | 3924,5 🔺 | 568,3 🔺 | 3,2 | 🔺 files, size |
| 29 | org.apache:apache |  |  |  |  |  |  | 0 | 0 | 0 | 0 |  |  |  |  |  |
| 30 | commons-codec:commons-codec | org.apache.commons.codec ✳️ | 2026-04-19 | 20,8 | 8,8 | 1.22.0 | 1.22.0 | 26 | 16 | 3 | 4 | 1 | 36,0 | 3,3 | 0,3 | - |
| ~~31~~ | ~~org.sonatype.spice:spice-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| 32 | org.apache.commons:commons-lang3 | org.apache.commons.lang3 ✳️ | 2025-11-12 | 15,1 | 9,2 | 3.20.0 | 3.20.0 | 26 | 16 | 2 | 2 | 36 | 52,4 | 3,2 | 3,3 | - |
| 33 | com.google.errorprone:error_prone_annotations | com.google.errorprone.annotations ✳️ | 2026-06-10 | 11,5 | 6,7 | 2.50.0 | 2.50.0 | 93 | 60 | 9 | 9 | 10 | 210,7 | 69,3 | 0,8 | - |
| ~~34~~ | ~~net.java:jvnet-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| 35 | org.apache.commons:commons-compress ⚠️ | org.apache.commons.compress ✳️ | 2025-07-26 | 17,2 | 8,8 | 1.28.0 | 1.28.0 | 35 | 18 | 0 | 0 | 36 | 52,4 | 3,2 | 3,3 | - |
| ~~36~~ | ~~io.netty:netty-bom~~ | ~~-~~ | ~~2026-06-02~~ | ~~0,2~~ | ~~-~~ | ~~4.1.135.Final~~ | ~~-~~ | ~~4~~ | ~~0~~ | ~~4~~ | ~~0~~ | ~~66~~ | ~~1150,9 🔺~~ | ~~47,5 🔺~~ | ~~3,2~~ | ~~🔺 files, size~~ |
| ~~37~~ | ~~org.apache.maven:maven-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~36~~ | ~~360,2~~ | ~~12,5~~ | ~~0,8~~ | ~~-~~ |
| ~~38~~ | ~~org.eclipse.jetty:jetty-bom~~ | ~~-~~ | ~~2026-08-03~~ | ~~0,2~~ | ~~-~~ | ~~12.1.12~~ | ~~-~~ | ~~4~~ | ~~0~~ | ~~4~~ | ~~0~~ | ~~82~~ | ~~882,6 🔺~~ | ~~77,9 🔺~~ | ~~3,3~~ | ~~🔺 files, size~~ |
| ~~39~~ | ~~com.google.code.gson:gson-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~3~~ | ~~42,0~~ | ~~1,0~~ | ~~0,3~~ | ~~-~~ |
| ~~40~~ | ~~org.codehaus.plexus:plexus-interpolation~~ | ~~org.codehaus.plexus.interpolation ✳️~~ | ~~2025-11-07~~ | ~~18,2~~ | ~~0,8~~ | ~~1.29~~ | ~~1.29~~ | ~~31~~ | ~~1~~ | ~~1~~ | ~~1~~ | ~~21~~ | ~~45,4~~ | ~~0,8~~ | ~~2,7~~ | ~~-~~ |
| ~~41~~ | ~~io.netty:netty-parent~~ | ~~-~~ | ~~2026-06-02~~ | ~~0,2~~ | ~~-~~ | ~~4.1.135.Final~~ | ~~-~~ | ~~4~~ | ~~0~~ | ~~4~~ | ~~0~~ | ~~66~~ | ~~1150,9 🔺~~ | ~~47,5 🔺~~ | ~~3,2~~ | ~~🔺 files, size~~ |
| 42 | com.google.code.findbugs:jsr305 🚩 |  | 2017-03-31 | 17,4 |  | 3.0.2 |  | 10 | 0 | 0 | 0 |  |  |  |  |  |
| 43 | org.apache.httpcomponents:httpcore 🚩 | org.apache.httpcomponents.httpcore ⚙️ | 2022-11-26 | 19,1 | 8,1 | 4.4.16 | 4.4.16 | 50 | 7 | 0 | 0 |  |  |  |  |  |
| 44 | org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm | kotlinx.coroutines.core 🏷️ | 2026-05-07 | 5,4 | 3,4 | 1.11.0 | 1.11.0 | 53 | 20 | 3 | 3 | 768 | 744,5 🔺 | 91,4 🔺 | 57,3 🔺 | 🔺 files, size, releases |
| ~~45~~ | ~~org.jetbrains.kotlinx:kotlinx-coroutines-bom 🚩~~ | ~~-~~ | ~~2020-11-27~~ | ~~6,2~~ | ~~-~~ | ~~1.4.2-native-mt~~ | ~~-~~ | ~~13~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~768~~ | ~~744,5 🔺~~ | ~~91,4 🔺~~ | ~~57,3 🔺~~ | ~~🔺 files, size, releases~~ |
| 46 | com.google.code.gson:gson | com.google.gson ✳️ | 2026-04-23 | 18,0 | 6,9 | 2.14.0 | 2.14.0 | 44 | 16 | 3 | 3 | 3 | 42,0 | 1,0 | 0,3 | - |
| 47 | org.apache.httpcomponents:httpclient 🚩 | org.apache.httpcomponents.httpclient ⚙️ | 2022-11-30 | 19,1 | 8,1 | 4.5.14 | 4.5.14 | 55 | 9 | 0 | 0 |  |  |  |  |  |
| ~~48~~ | ~~org.apache.maven:maven-artifact~~ | ~~-~~ | ~~2026-07-30~~ | ~~20,3~~ | ~~-~~ | ~~4.0.0-rc-6~~ | ~~-~~ | ~~102~~ | ~~1~~ | ~~7~~ | ~~1~~ | ~~36~~ | ~~360,2~~ | ~~12,5~~ | ~~0,8~~ | ~~-~~ |
| 49 | org.jetbrains.kotlin:kotlin-stdlib-jdk7 | kotlin.stdlib.jdk7 🏷️ | 2026-06-25 | 8,7 | 6,1 | 2.4.10-RC | 2.4.10-RC | 184 | 156 | 32 | 35 | 175 | 3924,5 🔺 | 568,3 🔺 | 3,2 | 🔺 files, size |
| ~~50~~ | ~~org.apache.maven:maven-model~~ | ~~-~~ | ~~2026-07-30~~ | ~~20,3~~ | ~~-~~ | ~~4.0.0-rc-6~~ | ~~-~~ | ~~103~~ | ~~1~~ | ~~7~~ | ~~1~~ | ~~36~~ | ~~360,2~~ | ~~12,5~~ | ~~0,8~~ | ~~-~~ |
| ~~51~~ | ~~org.apache.maven:maven-plugin-api~~ | ~~-~~ | ~~2026-07-30~~ | ~~20,3~~ | ~~-~~ | ~~4.0.0-rc-6~~ | ~~-~~ | ~~102~~ | ~~1~~ | ~~7~~ | ~~1~~ | ~~36~~ | ~~360,2~~ | ~~12,5~~ | ~~0,8~~ | ~~-~~ |
| 52 | org.jetbrains.kotlin:kotlin-stdlib-jdk8 | kotlin.stdlib.jdk8 🏷️ | 2026-06-25 | 8,7 | 6,1 | 2.4.10-RC | 2.4.10-RC | 184 | 156 | 32 | 35 | 175 | 3924,5 🔺 | 568,3 🔺 | 3,2 | 🔺 files, size |
| ~~53~~ | ~~org.springframework.boot:spring-boot-dependencies 🚩~~ | ~~-~~ | ~~2016-02-26~~ | ~~10,7~~ | ~~-~~ | ~~1.3.3.RELEASE~~ | ~~-~~ | ~~4~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~336~~ | ~~3788,6 🔺~~ | ~~132,2 🔺~~ | ~~2,9~~ | ~~🔺 files, size~~ |
| 54 | org.apache.httpcomponents:httpcomponents-client |  |  |  |  |  |  | 0 | 0 | 0 | 0 |  |  |  |  |  |
| ~~55~~ | ~~org.apache.logging.log4j:log4j-bom~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~29~~ | ~~580,0~~ | ~~8,1~~ | ~~0,4~~ | ~~-~~ |
| 56 | com.fasterxml.jackson.module:jackson-modules-java8 |  | 2026-07-08 | 0,2 |  | 2.18.9 |  | 6 | 0 | 6 | 0 | 23 | 690,7 | 14,9 | 1,5 | - |
| 57 | org.jetbrains.kotlin:kotlin-gradle-plugin-api |  | 2026-07-22 | 11,4 |  | 2.4.20-Beta2 |  | 246 | 0 | 35 | 0 | 175 | 3924,5 🔺 | 568,3 🔺 | 3,2 | 🔺 files, size |
| 58 | commons-logging:commons-logging | org.apache.commons.logging ✳️ | 2026-06-11 | 20,7 | 2,7 | 1.4.0 | 1.4.0 | 18 | 8 | 2 | 2 | 1 | 44,0 | 1,1 | 0,2 | - |
| 59 | junit:junit 🚩 | junit ⚙️ | 2021-02-13 | 21,0 | 7,7 | 4.13.2 | 4.13.2 | 32 | 8 | 0 | 0 |  |  |  |  |  |
| ~~60~~ | ~~org.apache.maven.shared:maven-shared-utils 🚩~~ | ~~-~~ | ~~2023-05-11~~ | ~~13,8~~ | ~~-~~ | ~~3.4.2~~ | ~~-~~ | ~~18~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~4~~ | ~~29,0~~ | ~~0,6~~ | ~~0,5~~ | ~~-~~ |
| 61 | org.jetbrains.kotlin:kotlin-stdlib-common |  | 2026-06-25 | 9,5 |  | 2.4.10-RC |  | 202 | 0 | 32 | 0 | 175 | 3924,5 🔺 | 568,3 🔺 | 3,2 | 🔺 files, size |
| ~~62~~ | ~~net.bytebuddy:byte-buddy-parent~~ | ~~-~~ | ~~2026-07-02~~ | ~~0,2~~ | ~~-~~ | ~~1.18.11~~ | ~~-~~ | ~~6~~ | ~~0~~ | ~~6~~ | ~~0~~ | ~~9~~ | ~~171,6~~ | ~~69,4 🔺~~ | ~~1,7~~ | ~~🔺 size~~ |
| 63 | com.google.j2objc:j2objc-annotations | com.google.j2objc.annotations ✳️ | 2025-08-14 | 11,2 | 2,4 | 3.1 | 3.1 | 7 | 2 | 1 | 1 | 1 | 24,0 | 0,2 | 0,1 | - |
| ~~64~~ | ~~org.apache.maven:maven-repository-metadata~~ | ~~-~~ | ~~2026-07-30~~ | ~~20,3~~ | ~~-~~ | ~~4.0.0-rc-6~~ | ~~-~~ | ~~92~~ | ~~1~~ | ~~7~~ | ~~1~~ | ~~36~~ | ~~360,2~~ | ~~12,5~~ | ~~0,8~~ | ~~-~~ |
| 65 | com.fasterxml.jackson.datatype:jackson-datatype-jsr310 | com.fasterxml.jackson.datatype.jsr310 ✳️ | 2026-07-08 | 13,3 | 8,8 | 2.18.9 | 2.18.9 | 169 | 95 | 18 | 18 | 23 | 690,3 | 6,4 | 1,5 | - |
| ~~66~~ | ~~io.micrometer:micrometer-bom~~ | ~~-~~ | ~~2026-06-08~~ | ~~0,2~~ | ~~-~~ | ~~1.17.0~~ | ~~-~~ | ~~3~~ | ~~0~~ | ~~3~~ | ~~0~~ | ~~42~~ | ~~721,7 🔺~~ | ~~12,2~~ | ~~4,9~~ | ~~🔺 files~~ |
| 67 | org.junit.platform:junit-platform-commons | org.junit.platform.commons ✳️ | 2026-08-07 | 10,1 | 9,1 | 6.1.3 | 6.1.3 | 112 | 109 | 18 | 19 | 13 | 527,8 | 26,1 | 1,5 | - |
| ~~68~~ | ~~org.apache.maven:maven-settings~~ | ~~-~~ | ~~2026-07-30~~ | ~~20,3~~ | ~~-~~ | ~~4.0.0-rc-6~~ | ~~-~~ | ~~101~~ | ~~1~~ | ~~7~~ | ~~1~~ | ~~36~~ | ~~360,2~~ | ~~12,5~~ | ~~0,8~~ | ~~-~~ |
| ~~69~~ | ~~org.apache.httpcomponents:httpcomponents-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| ~~70~~ | ~~org.codehaus.plexus:plexus-component-annotations ⚠️~~ | ~~-~~ | ~~2023-12-24~~ | ~~18,8~~ | ~~-~~ | ~~2.2.0~~ | ~~-~~ | ~~52~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~21~~ | ~~45,4~~ | ~~0,8~~ | ~~2,7~~ | ~~-~~ |
| ~~71~~ | ~~org.mockito:mockito-bom~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~21~~ | ~~320,0~~ | ~~9,9~~ | ~~0,7~~ | ~~-~~ |
| 72 | org.jetbrains:annotations | org.jetbrains.annotations 🏷️ | 2026-02-18 | 12,7 | 7,9 | 26.1.0 | 26.1.0 | 24 | 20 | 2 | 2 | 20 | 205,2 | 13,5 | 1,0 | - |
| ~~73~~ | ~~org.jetbrains.kotlin:kotlin-bom~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~175~~ | ~~3924,5 🔺~~ | ~~568,3 🔺~~ | ~~3,2~~ | ~~🔺 files, size~~ |
| ~~74~~ | ~~org.apache.maven:maven-core~~ | ~~-~~ | ~~2026-07-30~~ | ~~20,3~~ | ~~-~~ | ~~4.0.0-rc-6~~ | ~~-~~ | ~~103~~ | ~~1~~ | ~~7~~ | ~~1~~ | ~~36~~ | ~~360,2~~ | ~~12,5~~ | ~~0,8~~ | ~~-~~ |
| ~~75~~ | ~~org.infinispan:infinispan-bom~~ | ~~-~~ | ~~2026-07-01~~ | ~~0,2~~ | ~~-~~ | ~~16.0.14~~ | ~~-~~ | ~~5~~ | ~~0~~ | ~~5~~ | ~~0~~ | ~~167~~ | ~~1865,3 🔺~~ | ~~173,2 🔺~~ | ~~4,3~~ | ~~🔺 files, size~~ |
| ~~76~~ | ~~org.infinispan:infinispan-build-configuration-parent~~ | ~~-~~ | ~~2026-07-01~~ | ~~0,2~~ | ~~-~~ | ~~16.0.14~~ | ~~-~~ | ~~5~~ | ~~0~~ | ~~5~~ | ~~0~~ | ~~167~~ | ~~1865,3 🔺~~ | ~~173,2 🔺~~ | ~~4,3~~ | ~~🔺 files, size~~ |
| 77 | org.junit.platform:junit-platform-engine | org.junit.platform.engine ✳️ | 2026-08-07 | 10,1 | 9,1 | 6.1.3 | 6.1.3 | 112 | 109 | 18 | 19 | 13 | 527,8 | 26,1 | 1,5 | - |
| 78 | com.google.guava:failureaccess ⚠️ | com.google.common.util.concurrent.internal ✳️ | 2025-03-19 | 7,9 | 2,8 | 1.0.3 | 1.0.3 | 4 | 2 | 0 | 0 | 3 | 75,0 | 22,6 | 0,3 | - |
| ~~79~~ | ~~org.testcontainers:testcontainers-bom~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~125~~ | ~~1494,9~~ | ~~38,4~~ | ~~0,6~~ | ~~-~~ |
| 80 | org.apache.logging.log4j:log4j |  |  |  |  |  |  | 0 | 0 | 0 | 0 | 29 | 580,0 | 8,1 | 0,4 | - |
| ~~81~~ | ~~org.springframework:spring-framework-bom~~ | ~~-~~ | ~~2026-06-08~~ | ~~12,7~~ | ~~-~~ | ~~7.0.8~~ | ~~-~~ | ~~118~~ | ~~0~~ | ~~2~~ | ~~0~~ | ~~24~~ | ~~449,0~~ | ~~109,6 🔺~~ | ~~2,0~~ | ~~🔺 size~~ |
| ~~82~~ | ~~com.google.protobuf:protobuf-bom~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~6~~ | ~~77,6~~ | ~~19,3~~ | ~~2,1~~ | ~~-~~ |
| 83 | org.apache.httpcomponents:httpcomponents-core |  |  |  |  |  |  | 0 | 0 | 0 | 0 |  |  |  |  |  |
| 84 | org.springframework:spring-core | spring.core ⚙️ | 2026-06-08 | 20,6 | 8,9 | 7.0.8 | 7.0.8 | 337 | 195 | 24 | 24 | 24 | 449,0 | 109,6 🔺 | 2,0 | 🔺 size |
| 85 | com.squareup.okhttp3:okhttp |  | 2026-06-08 | 10,6 |  | 5.4.0 |  | 107 | 65 | 8 | 0 | 18 | 828,8 | 9,3 | 0,7 | - |
| 86 | org.ow2.asm:asm-tree | org.objectweb.asm.tree ✳️ | 2026-05-23 | 14,7 | 9,1 | 9.10.1 | 9.10.1 | 45 | 33 | 4 | 4 | 7 | 242,5 | 2,2 | 0,3 | - |
| 87 | net.java.dev.jna:jna | com.sun.jna ⚙️ | 2026-06-12 | 17,0 | 7,8 | 5.19.1 | 5.19.1 | 55 | 24 | 4 | 4 | 4 | 102,0 | 46,0 | 0,3 | - |
| 88 | org.junit.jupiter:junit-jupiter-api | org.junit.jupiter.api ✳️ | 2026-08-07 | 10,1 | 9,1 | 6.1.3 | 6.1.3 | 112 | 109 | 18 | 19 | 5 | 238,9 | 12,9 | 1,5 | - |
| ~~89~~ | ~~org.glassfish.jaxb:jaxb-bom~~ | ~~-~~ | ~~2026-05-28~~ | ~~0,2~~ | ~~-~~ | ~~4.0.9~~ | ~~-~~ | ~~1~~ | ~~0~~ | ~~1~~ | ~~0~~ | ~~27~~ | ~~399,0~~ | ~~16,2~~ | ~~0,3~~ | ~~-~~ |
| 90 | com.sun.activation:all |  |  |  |  |  |  | 0 | 0 | 0 | 0 |  |  |  |  |  |
| 91 | org.jetbrains.kotlin:kotlin-reflect | kotlin.reflect 🏷️ | 2026-06-25 | 11,4 | 6,1 | 2.4.10-RC | 2.4.10-RC | 242 | 156 | 32 | 35 | 175 | 3924,5 🔺 | 568,3 🔺 | 3,2 | 🔺 files, size |
| ~~92~~ | ~~com.google.protobuf:protobuf-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~6~~ | ~~77,6~~ | ~~19,3~~ | ~~2,1~~ | ~~-~~ |
| ~~93~~ | ~~org.springframework.data:spring-data-bom~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~17~~ | ~~68,0~~ | ~~17,9 🔺~~ | ~~10,3 🔺~~ | ~~🔺 size, releases~~ |
| 94 | org.ow2.asm:asm-commons | org.objectweb.asm.commons ✳️ | 2026-05-23 | 14,7 | 9,1 | 9.10.1 | 9.10.1 | 45 | 33 | 4 | 4 | 7 | 242,5 | 2,2 | 0,3 | - |
| 95 | com.fasterxml.jackson.module:jackson-module-parameter-names | com.fasterxml.jackson.module.paramnames ✳️ | 2026-07-08 | 12,2 | 8,8 | 2.18.9 | 2.18.9 | 154 | 95 | 18 | 18 | 23 | 690,7 | 14,9 | 1,5 | - |
| 96 | com.squareup.okio:okio-jvm | okio ⚙️ | 2026-03-11 | 4,9 | 4,9 | 3.17.0 | 3.17.0 | 27 | 29 | 5 | 7 | 32 | 1535,4 | 13,1 | 0,6 | - |
| ~~97~~ | ~~org.apache.maven.shared:maven-common-artifact-filters ⚠️~~ | ~~-~~ | ~~2024-06-05~~ | ~~19,7~~ | ~~-~~ | ~~3.4.0~~ | ~~-~~ | ~~15~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~4~~ | ~~29,0~~ | ~~0,6~~ | ~~0,5~~ | ~~-~~ |
| ~~98~~ | ~~com.google.guava:listenablefuture 🚩~~ | ~~-~~ | ~~2018-09-11~~ | ~~7,9~~ | ~~-~~ | ~~9999.0-empty-to-avoid-conflict-with-guava~~ | ~~-~~ | ~~2~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~3~~ | ~~75,0~~ | ~~22,6~~ | ~~0,3~~ | ~~-~~ |
| ~~99~~ | ~~org.glassfish.jersey:jersey-bom~~ | ~~-~~ | ~~2026-06-11~~ | ~~0,2~~ | ~~-~~ | ~~3.1.12~~ | ~~-~~ | ~~1~~ | ~~0~~ | ~~1~~ | ~~0~~ | ~~2~~ | ~~24,0~~ | ~~0,1~~ | ~~0,1~~ | ~~-~~ |
| 100 | org.springframework:spring-jcl | spring.jcl ⚙️ | 2026-06-08 | 8,9 | 8,9 | 6.2.19 | 6.2.19 | 174 | 174 | 10 | 10 | 24 | 449,0 | 109,6 🔺 | 2,0 | 🔺 size |
| 101 | org.jetbrains.kotlin:kotlin-gradle-plugin-model |  | 2025-10-23 | 7,9 |  | 2.2.21 |  | 149 | 0 | 7 | 0 | 175 | 3924,5 🔺 | 568,3 🔺 | 3,2 | 🔺 files, size |
| 102 | javax.inject:javax.inject 🚩 |  | 2009-10-13 | 16,8 |  | 1 |  | 1 | 0 | 0 | 0 |  |  |  |  |  |
| 103 | org.opentest4j:opentest4j 🚩 | org.opentest4j ✳️ | 2023-07-06 | 10,5 | 9,1 | 1.3.0 | 1.3.0 | 12 | 9 | 0 | 0 |  |  |  |  |  |
| ~~104~~ | ~~org.codehaus.plexus:plexus-classworlds~~ | ~~org.codehaus.plexus.classworlds ⚙️~~ | ~~2026-05-19~~ | ~~19,7~~ | ~~0,3~~ | ~~2.12.0~~ | ~~2.12.0~~ | ~~36~~ | ~~2~~ | ~~3~~ | ~~2~~ | ~~21~~ | ~~45,4~~ | ~~0,8~~ | ~~2,7~~ | ~~-~~ |
| ~~105~~ | ~~jakarta.xml.bind:jakarta.xml.bind-api-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~1~~ | ~~36,0~~ | ~~0,9~~ | ~~0,3~~ | ~~-~~ |
| 106 | io.netty:netty-common | io.netty.common ⚙️ | 2026-06-02 | 14,1 | 8,7 | 4.1.135.Final | 4.1.135.Final | 257 | 148 | 24 | 28 | 66 | 1150,9 🔺 | 47,5 🔺 | 3,2 | 🔺 files, size |
| 107 | io.netty:netty-buffer | io.netty.buffer ⚙️ | 2026-06-02 | 14,1 | 8,7 | 4.1.135.Final | 4.1.135.Final | 257 | 148 | 24 | 28 | 66 | 1150,9 🔺 | 47,5 🔺 | 3,2 | 🔺 files, size |
| 108 | org.junit.jupiter:junit-jupiter-engine | org.junit.jupiter.engine ✳️ | 2026-08-07 | 10,1 | 9,1 | 6.1.3 | 6.1.3 | 112 | 109 | 18 | 19 | 5 | 238,9 | 12,9 | 1,5 | - |
| 109 | io.netty:netty-transport | io.netty.transport ⚙️ | 2026-06-02 | 14,1 | 8,7 | 4.1.135.Final | 4.1.135.Final | 257 | 148 | 24 | 28 | 66 | 1150,9 🔺 | 47,5 🔺 | 3,2 | 🔺 files, size |
| 110 | io.netty:netty-handler | io.netty.handler ⚙️ | 2026-06-02 | 14,1 | 8,7 | 4.1.135.Final | 4.1.135.Final | 257 | 148 | 24 | 28 | 66 | 1150,9 🔺 | 47,5 🔺 | 3,2 | 🔺 files, size |
| ~~111~~ | ~~org.eclipse.sisu:sisu-inject~~ | ~~-~~ | ~~2026-06-24~~ | ~~0,1~~ | ~~-~~ | ~~1.0.1~~ | ~~-~~ | ~~1~~ | ~~0~~ | ~~1~~ | ~~0~~ | ~~6~~ | ~~123,0~~ | ~~2,5~~ | ~~0,2~~ | ~~-~~ |
| 112 | io.netty:netty-codec | io.netty.codec ⚙️ | 2026-06-02 | 14,1 | 8,7 | 4.1.135.Final | 4.1.135.Final | 257 | 148 | 24 | 28 | 66 | 1150,9 🔺 | 47,5 🔺 | 3,2 | 🔺 files, size |
| 113 | io.netty:netty-resolver | io.netty.resolver ⚙️ | 2026-06-02 | 11,4 | 8,7 | 4.1.135.Final | 4.1.135.Final | 175 | 148 | 24 | 28 | 66 | 1150,9 🔺 | 47,5 🔺 | 3,2 | 🔺 files, size |
| 114 | org.antlr:antlr4-master |  |  |  |  |  |  | 0 | 0 | 0 | 0 |  |  |  |  |  |
| 115 | org.yaml:snakeyaml | org.yaml.snakeyaml ✳️ | 2026-02-26 | 17,0 | 7,5 | 2.6 | 2.6 | 37 | 17 | 2 | 2 | 1 | 24,0 | 1,8 | 0,2 | - |
| 116 | com.fasterxml.jackson.dataformat:jackson-dataformat-yaml | com.fasterxml.jackson.dataformat.yaml ✳️ | 2026-07-08 | 14,4 | 8,8 | 2.18.9 | 2.18.9 | 177 | 95 | 18 | 18 | 12 | 357,3 | 5,3 | 1,5 | - |
| 117 | com.google.protobuf:protobuf-java | com.google.protobuf ⚙️ | 2026-08-03 | 17,8 | 6,9 | 4.36.0-RC2 | 4.36.0-RC2 | 217 | 150 | 23 | 24 | 6 | 77,6 | 19,3 | 2,1 | - |
| 118 | org.apiguardian:apiguardian-api 🚩 | org.apiguardian.api ✳️ | 2021-06-27 | 8,9 | 8,9 | 1.1.2 | 1.1.2 | 4 | 4 | 0 | 0 |  |  |  |  |  |
| 119 | org.jetbrains.kotlin:kotlin-gradle-plugin-idea |  | 2026-07-22 | 4,3 |  | 2.4.20-Beta2 |  | 117 | 0 | 35 | 0 | 175 | 3924,5 🔺 | 568,3 🔺 | 3,2 | 🔺 files, size |
| ~~120~~ | ~~org.slf4j:slf4j-bom~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~12~~ | ~~294,0~~ | ~~3,0~~ | ~~0,1~~ | ~~-~~ |
| ~~121~~ | ~~org.codehaus.plexus:plexus-archiver~~ | ~~-~~ | ~~2026-06-08~~ | ~~20,8~~ | ~~-~~ | ~~4.12.0~~ | ~~-~~ | ~~89~~ | ~~0~~ | ~~6~~ | ~~0~~ | ~~21~~ | ~~45,4~~ | ~~0,8~~ | ~~2,7~~ | ~~-~~ |
| 122 | org.apache.logging.log4j:log4j-api | org.apache.logging.log4j ✳️ | 2026-06-29 | 14,0 | 8,7 | 2.26.1 | 2.26.1 | 76 | 44 | 5 | 6 | 29 | 580,0 | 8,1 | 0,4 | - |
| 123 | com.squareup.okio:okio |  | 2026-03-11 | 12,3 |  | 3.17.0 |  | 87 | 28 | 5 | 0 | 32 | 1535,4 | 13,1 | 0,6 | - |
| 124 | software.amazon.awssdk:aws-sdk-java-pom |  | 2026-08-07 | 0,2 |  | 2.51.3 |  | 52 | 0 | 52 | 0 | 520 | 7784,8 🔺 | 3309,0 🔺 | 27,7 🔺 | 🔺 files, size, releases |
| ~~125~~ | ~~org.objenesis:objenesis-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~4~~ | ~~96,0~~ | ~~1,2~~ | ~~0,1~~ | ~~-~~ |
| 126 | net.bytebuddy:byte-buddy | net.bytebuddy ✳️ | 2026-07-02 | 12,3 | 9,2 | 1.18.11 | 1.18.11 | 321 | 182 | 20 | 20 | 9 | 171,6 | 69,4 🔺 | 1,7 | 🔺 size |
| ~~127~~ | ~~io.dropwizard.metrics:metrics-parent~~ | ~~-~~ | ~~2026-05-25~~ | ~~0,2~~ | ~~-~~ | ~~4.2.39~~ | ~~-~~ | ~~1~~ | ~~0~~ | ~~1~~ | ~~0~~ | ~~43~~ | ~~948,0~~ | ~~6,7~~ | ~~0,5~~ | ~~-~~ |
| ~~128~~ | ~~org.apache.maven.reporting:maven-reporting-api ⚠️~~ | ~~-~~ | ~~2024-10-01~~ | ~~20,3~~ | ~~-~~ | ~~4.0.0~~ | ~~-~~ | ~~37~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~1~~ | ~~29,0~~ | ~~0,5~~ | ~~0,1~~ | ~~-~~ |
| ~~129~~ | ~~ch.qos.logback:logback-parent~~ | ~~-~~ | ~~2026-08-11~~ | ~~0,2~~ | ~~-~~ | ~~1.6.2~~ | ~~-~~ | ~~6~~ | ~~0~~ | ~~6~~ | ~~0~~ | ~~6~~ | ~~79,4~~ | ~~8,5~~ | ~~1,8~~ | ~~-~~ |
| 130 | org.springframework:spring-beans | spring.beans ⚙️ | 2026-06-08 | 20,7 | 8,9 | 7.0.8 | 7.0.8 | 330 | 195 | 24 | 24 | 24 | 449,0 | 109,6 🔺 | 2,0 | 🔺 size |
| 131 | com.thoughtworks.qdox:qdox ⚠️ | com.thoughtworks.qdox ✳️ | 2024-11-29 | 19,2 | 9,0 | 2.2.0 | 2.2.0 | 28 | 10 | 0 | 0 |  |  |  |  |  |
| 132 | io.netty:netty-codec-http | io.netty.codec.http ⚙️ | 2026-06-02 | 14,1 | 8,7 | 4.1.135.Final | 4.1.135.Final | 257 | 148 | 24 | 28 | 66 | 1150,9 🔺 | 47,5 🔺 | 3,2 | 🔺 files, size |
| 133 | jakarta.xml.bind:jakarta.xml.bind-api | jakarta.xml.bind ✳️ | 2026-05-02 | 7,6 | 7,6 | 4.1.0-M1 | 4.1.0-M1 | 17 | 17 | 4 | 4 | 1 | 36,0 | 0,9 | 0,3 | - |
| 134 | org.antlr:antlr4-runtime ⚠️ | org.antlr.antlr4.runtime ⚙️ | 2024-08-03 | 13,7 | 7,7 | 4.13.2 | 4.13.2 | 31 | 15 | 0 | 0 |  |  |  |  |  |
| ~~135~~ | ~~org.codehaus.plexus:plexus-io~~ | ~~-~~ | ~~2025-11-07~~ | ~~19,4~~ | ~~-~~ | ~~3.6.0~~ | ~~-~~ | ~~55~~ | ~~0~~ | ~~2~~ | ~~0~~ | ~~21~~ | ~~45,4~~ | ~~0,8~~ | ~~2,7~~ | ~~-~~ |
| 136 | org.springframework:spring-context | spring.context ⚙️ | 2026-06-08 | 20,6 | 8,9 | 7.0.8 | 7.0.8 | 338 | 195 | 24 | 24 | 24 | 449,0 | 109,6 🔺 | 2,0 | 🔺 size |
| 137 | org.hamcrest:hamcrest-core ⚠️ | org.hamcrest.core.deprecated ⚙️ | 2024-08-01 | 19,1 | 7,7 | 3.0 | 3.0 | 12 | 7 | 0 | 0 |  |  |  |  |  |
| 138 | com.fasterxml.jackson.datatype:jackson-datatype-jdk8 | com.fasterxml.jackson.datatype.jdk8 ✳️ | 2026-07-08 | 11,8 | 8,8 | 2.18.9 | 2.18.9 | 151 | 95 | 18 | 18 | 23 | 690,3 | 6,4 | 1,5 | - |
| 139 | org.junit.jupiter:junit-jupiter-params | org.junit.jupiter.params ✳️ | 2026-08-07 | 9,4 | 9,1 | 6.1.3 | 6.1.3 | 109 | 109 | 18 | 19 | 5 | 238,9 | 12,9 | 1,5 | - |
| 140 | jakarta.activation:jakarta.activation-api | jakarta.activation ✳️ | 2026-04-20 | 7,7 | 7,7 | 2.2.0-M2 | 2.2.0-M2 | 16 | 16 | 3 | 3 | 1 | 36,0 | 0,6 | 0,3 | - |
| 141 | org.springframework:spring-aop | spring.aop ⚙️ | 2026-06-08 | 20,6 | 8,9 | 7.0.8 | 7.0.8 | 337 | 195 | 24 | 24 | 24 | 449,0 | 109,6 🔺 | 2,0 | 🔺 size |
| ~~142~~ | ~~org.springframework.integration:spring-integration-bom~~ | ~~-~~ | ~~2026-06-24~~ | ~~12,2~~ | ~~-~~ | ~~6.5.10~~ | ~~-~~ | ~~101~~ | ~~0~~ | ~~4~~ | ~~0~~ | ~~44~~ | ~~828,3 🔺~~ | ~~112,0 🔺~~ | ~~2,3~~ | ~~🔺 files, size~~ |
| ~~143~~ | ~~org.apache.maven.plugins:maven-compiler-plugin~~ | ~~-~~ | ~~2026-01-27~~ | ~~20,4~~ | ~~-~~ | ~~4.0.0-beta-4~~ | ~~-~~ | ~~38~~ | ~~0~~ | ~~4~~ | ~~0~~ | ~~35~~ | ~~44,4~~ | ~~2,7~~ | ~~3,1~~ | ~~-~~ |
| 144 | org.springframework:spring-expression | spring.expression ⚙️ | 2026-06-08 | 16,7 | 8,9 | 7.0.8 | 7.0.8 | 288 | 195 | 24 | 24 | 24 | 449,0 | 109,6 🔺 | 2,0 | 🔺 size |
| 145 | commons-collections:commons-collections 🚩 |  | 2015-11-12 | 20,8 |  | 3.2.2 |  | 17 | 0 | 0 | 0 |  |  |  |  |  |
| 146 | org.ow2:ow2 |  |  |  |  |  |  | 0 | 0 | 0 | 0 |  |  |  |  |  |
| ~~147~~ | ~~org.eclipse.sisu:sisu-plexus~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~6~~ | ~~123,0~~ | ~~2,5~~ | ~~0,2~~ | ~~-~~ |
| 148 | io.netty:netty-transport-native-unix-common | io.netty.transport.unix.common ⚙️ | 2026-08-06 | 9,3 | 8,7 | 4.1.137.Final | 4.1.137.Final | 155 | 148 | 28 | 28 | 66 | 1150,9 🔺 | 47,5 🔺 | 3,2 | 🔺 files, size |
| ~~149~~ | ~~org.codehaus.plexus:plexus-java~~ | ~~org.codehaus.plexus.languages.java ✳️~~ | ~~2025-12-21~~ | ~~9,0~~ | ~~7,8~~ | ~~1.5.2~~ | ~~1.5.2~~ | ~~29~~ | ~~18~~ | ~~2~~ | ~~2~~ | ~~21~~ | ~~45,4~~ | ~~0,8~~ | ~~2,7~~ | ~~-~~ |
| 150 | org.junit.platform:junit-platform-launcher | org.junit.platform.launcher ✳️ | 2026-08-07 | 10,1 | 9,1 | 6.1.3 | 6.1.3 | 112 | 109 | 18 | 19 | 13 | 527,8 | 26,1 | 1,5 | - |
| 151 | com.amazonaws:aws-java-sdk-pom |  |  |  |  |  |  | 0 | 0 | 0 | 0 | 426 | 862,8 🔺 | 702,0 🔺 | 6,1 | 🔺 files, size |
| 152 | com.fasterxml.jackson.dataformat:jackson-dataformats-text |  | 2026-07-08 | 0,2 |  | 2.18.9 |  | 6 | 0 | 6 | 0 | 12 | 357,3 | 5,3 | 1,5 | - |
| ~~153~~ | ~~io.projectreactor:reactor-bom~~ | ~~-~~ | ~~2026-06-08~~ | ~~9,5~~ | ~~-~~ | ~~2025.0.6~~ | ~~-~~ | ~~38~~ | ~~0~~ | ~~2~~ | ~~0~~ | ~~5~~ | ~~127,9~~ | ~~7,9~~ | ~~2,8~~ | ~~-~~ |
| 154 | com.sun.xml.bind:jaxb-bom-ext |  |  |  |  |  |  | 0 | 0 | 0 | 0 | 8 | 140,5 | 31,9 | 0,3 | - |
| ~~155~~ | ~~org.apache.maven.plugins:maven-surefire-plugin~~ | ~~-~~ | ~~2026-06-02~~ | ~~20,4~~ | ~~-~~ | ~~3.6.0-M1~~ | ~~-~~ | ~~71~~ | ~~0~~ | ~~4~~ | ~~0~~ | ~~35~~ | ~~44,4~~ | ~~2,7~~ | ~~3,1~~ | ~~-~~ |
| ~~156~~ | ~~org.apache.maven.doxia:doxia-sink-api~~ | ~~-~~ | ~~2026-03-17~~ | ~~20,3~~ | ~~-~~ | ~~2.1.0~~ | ~~-~~ | ~~38~~ | ~~0~~ | ~~1~~ | ~~0~~ | ~~13~~ | ~~312,0~~ | ~~7,8~~ | ~~0,1~~ | ~~-~~ |
| ~~157~~ | ~~io.micrometer:micrometer-tracing-bom~~ | ~~-~~ | ~~2026-06-08~~ | ~~0,2~~ | ~~-~~ | ~~1.7.0~~ | ~~-~~ | ~~3~~ | ~~0~~ | ~~3~~ | ~~0~~ | ~~42~~ | ~~721,7 🔺~~ | ~~12,2~~ | ~~4,9~~ | ~~🔺 files~~ |
| ~~158~~ | ~~org.apache.maven.doxia:doxia~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~13~~ | ~~312,0~~ | ~~7,8~~ | ~~0,1~~ | ~~-~~ |
| 159 | org.springframework.boot:spring-boot | spring.boot ⚙️ | 2026-06-25 | 12,4 | 8,5 | 3.5.16 | 3.5.16 | 290 | 225 | 35 | 35 | 336 | 3788,6 🔺 | 132,2 🔺 | 2,9 | 🔺 files, size |
| 160 | com.google.cloud:native-image-shared-config |  |  |  |  |  |  | 0 | 0 | 0 | 0 | 402 | 249,5 🔺 | 19,3 🔺 | 67,0 🔺 | 🔺 files, size, releases |
| 161 | io.netty:netty-codec-http2 | io.netty.codec.http2 ⚙️ | 2026-06-02 | 11,4 | 8,7 | 4.1.135.Final | 4.1.135.Final | 175 | 148 | 24 | 28 | 66 | 1150,9 🔺 | 47,5 🔺 | 3,2 | 🔺 files, size |
| ~~162~~ | ~~org.springframework.boot:spring-boot-starter-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~336~~ | ~~3788,6 🔺~~ | ~~132,2 🔺~~ | ~~2,9~~ | ~~🔺 files, size~~ |
| ~~163~~ | ~~org.apache.maven:maven-model-builder~~ | ~~-~~ | ~~2026-07-30~~ | ~~16,8~~ | ~~-~~ | ~~4.0.0-rc-6~~ | ~~-~~ | ~~79~~ | ~~1~~ | ~~7~~ | ~~1~~ | ~~36~~ | ~~360,2~~ | ~~12,5~~ | ~~0,8~~ | ~~-~~ |
| 164 | org.junit.jupiter:junit-jupiter | org.junit.jupiter ✳️ | 2026-08-07 | 7,6 | 7,6 | 6.1.3 | 6.1.3 | 86 | 87 | 18 | 19 | 5 | 238,9 | 12,9 | 1,5 | - |
| 165 | net.java.dev.jna:jna-platform | com.sun.jna.platform ⚙️ | 2026-06-12 | 13,1 | 7,8 | 5.19.1 | 5.19.1 | 34 | 24 | 4 | 4 | 4 | 102,0 | 46,0 | 0,3 | - |
| 166 | joda-time:joda-time | org.joda.time ⚙️ | 2026-07-26 | 21,0 | 8,2 | 2.14.3 | 2.14.3 | 66 | 32 | 3 | 3 | 1 | 42,0 | 5,6 | 0,3 | - |
| ~~167~~ | ~~org.apache.maven.plugins:maven-resources-plugin~~ | ~~-~~ | ~~2026-03-02~~ | ~~20,3~~ | ~~-~~ | ~~3.5.0~~ | ~~-~~ | ~~23~~ | ~~0~~ | ~~2~~ | ~~0~~ | ~~35~~ | ~~44,4~~ | ~~2,7~~ | ~~3,1~~ | ~~-~~ |
| 168 | org.apache.commons:commons-text | org.apache.commons.text ✳️ | 2025-12-04 | 9,5 | 8,4 | 1.15.0 | 1.15.0 | 18 | 14 | 1 | 1 | 36 | 52,4 | 3,2 | 3,3 | - |
| 169 | commons-lang:commons-lang 🚩 |  | 2011-01-16 | 21,3 |  | 2.6 |  | 11 | 0 | 0 | 0 |  |  |  |  |  |
| 170 | org.tukaani:xz | org.tukaani.xz 🏷️ | 2026-03-01 | 14,8 | 8,6 | 1.12 | 1.12 | 13 | 5 | 2 | 2 | 1 | 40,0 | 0,6 | 0,2 | - |
| ~~171~~ | ~~org.apache.maven:maven-settings-builder~~ | ~~-~~ | ~~2026-07-30~~ | ~~16,0~~ | ~~-~~ | ~~4.0.0-rc-6~~ | ~~-~~ | ~~73~~ | ~~1~~ | ~~7~~ | ~~1~~ | ~~36~~ | ~~360,2~~ | ~~12,5~~ | ~~0,8~~ | ~~-~~ |
| ~~172~~ | ~~org.hamcrest:hamcrest-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| 173 | org.ow2.asm:asm-analysis | org.objectweb.asm.tree.analysis ✳️ | 2026-05-23 | 14,7 | 9,1 | 9.10.1 | 9.10.1 | 45 | 33 | 4 | 4 | 7 | 242,5 | 2,2 | 0,3 | - |
| 174 | org.springframework:spring-web | spring.web ⚙️ | 2026-06-08 | 20,6 | 8,9 | 7.0.8 | 7.0.8 | 337 | 195 | 24 | 24 | 24 | 449,0 | 109,6 🔺 | 2,0 | 🔺 size |
| ~~175~~ | ~~org.codehaus.plexus:plexus-container-default 🚩~~ | ~~-~~ | ~~2021-12-23~~ | ~~20,9~~ | ~~-~~ | ~~2.1.1~~ | ~~-~~ | ~~77~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~21~~ | ~~45,4~~ | ~~0,8~~ | ~~2,7~~ | ~~-~~ |
| 176 | commons-beanutils:commons-beanutils ⚠️ | org.apache.commons.beanutils ✳️ | 2025-05-25 | 20,8 | 1,6 | 1.11.0 | 1.11.0 | 25 | 3 | 0 | 0 |  |  |  |  |  |
| 177 | com.google:google |  |  |  |  |  |  | 0 | 0 | 0 | 0 |  |  |  |  |  |
| ~~178~~ | ~~io.dropwizard.metrics:metrics-bom~~ | ~~-~~ | ~~2026-05-25~~ | ~~0,2~~ | ~~-~~ | ~~4.2.39~~ | ~~-~~ | ~~1~~ | ~~0~~ | ~~1~~ | ~~0~~ | ~~43~~ | ~~948,0~~ | ~~6,7~~ | ~~0,5~~ | ~~-~~ |
| ~~179~~ | ~~io.rest-assured:rest-assured-bom~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~14~~ | ~~336,0~~ | ~~9,5~~ | ~~0,3~~ | ~~-~~ |
| ~~180~~ | ~~org.springframework.cloud:spring-cloud-dependencies-parent~~ | ~~-~~ | ~~2026-06-11~~ | ~~0,2~~ | ~~-~~ | ~~5.0.2~~ | ~~-~~ | ~~2~~ | ~~0~~ | ~~2~~ | ~~0~~ | ~~243~~ | ~~1049,0 🔺~~ | ~~161,7 🔺~~ | ~~3,3~~ | ~~🔺 files, size~~ |
| 181 | org.springframework.boot:spring-boot-autoconfigure | spring.boot.autoconfigure ⚙️ | 2026-06-25 | 12,4 | 8,5 | 3.5.16 | 3.5.16 | 290 | 225 | 35 | 35 | 336 | 3788,6 🔺 | 132,2 🔺 | 2,9 | 🔺 files, size |
| 182 | ch.qos.logback:logback-core | ch.qos.logback.core ✳️ | 2026-08-11 | 20,0 | 8,6 | 1.6.2 | 1.6.2 | 169 | 92 | 21 | 24 | 6 | 79,4 | 8,5 | 1,8 | - |
| 183 | org.objenesis:objenesis | org.objenesis ⚙️ | 2026-01-26 | 19,2 | 7,8 | 3.5 | 3.5 | 20 | 7 | 1 | 2 | 4 | 96,0 | 1,2 | 0,1 | - |
| ~~184~~ | ~~io.rsocket:rsocket-bom 🚩~~ | ~~-~~ | ~~2019-08-30~~ | ~~7,0~~ | ~~-~~ | ~~1.0.0-RC3~~ | ~~-~~ | ~~1~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| 185 | ch.qos.logback:logback-classic | ch.qos.logback.classic ✳️ | 2026-08-11 | 20,0 | 8,6 | 1.6.2 | 1.6.2 | 169 | 92 | 21 | 24 | 6 | 79,4 | 8,5 | 1,8 | - |
| ~~186~~ | ~~org.apache.maven.shared:maven-filtering~~ | ~~-~~ | ~~2026-06-30~~ | ~~18,0~~ | ~~-~~ | ~~3.5.1~~ | ~~-~~ | ~~19~~ | ~~0~~ | ~~2~~ | ~~0~~ | ~~4~~ | ~~29,0~~ | ~~0,6~~ | ~~0,5~~ | ~~-~~ |
| 187 | io.netty:netty-handler-proxy | io.netty.handler.proxy ⚙️ | 2026-06-02 | 11,4 | 8,7 | 4.1.135.Final | 4.1.135.Final | 175 | 148 | 24 | 28 | 66 | 1150,9 🔺 | 47,5 🔺 | 3,2 | 🔺 files, size |
| 188 | org.springframework.boot:spring-boot-starter | spring.boot.starter ⚙️ | 2026-06-25 | 12,4 | 8,5 | 3.5.16 | 3.5.16 | 290 | 225 | 35 | 35 | 336 | 3788,6 🔺 | 132,2 🔺 | 2,9 | 🔺 files, size |
| 189 | io.netty:netty-codec-socks | io.netty.codec.socks ⚙️ | 2026-06-02 | 13,7 | 8,7 | 4.1.135.Final | 4.1.135.Final | 250 | 148 | 24 | 28 | 66 | 1150,9 🔺 | 47,5 🔺 | 3,2 | 🔺 files, size |
| 190 | io.perfmark:perfmark-api ⚠️ | io.perfmark ⚙️ | 2023-12-21 | 7,2 | 5,2 | 0.27.0 | 0.27.0 | 15 | 4 | 0 | 0 |  |  |  |  |  |
| ~~191~~ | ~~org.codehaus.plexus:plexus-compilers~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~21~~ | ~~45,4~~ | ~~0,8~~ | ~~2,7~~ | ~~-~~ |
| 192 | io.netty:netty-transport-native-epoll | io.netty.transport.epoll.linux.x86_64 ⚙️ | 2026-08-06 | 12,5 | 8,7 | 4.1.137.Final | 4.1.137.Final | 223 | 148 | 28 | 28 | 66 | 1150,9 🔺 | 47,5 🔺 | 3,2 | 🔺 files, size |
| ~~193~~ | ~~org.sonatype.plexus:plexus-build-api 🚩~~ | ~~-~~ | ~~2011-02-11~~ | ~~17,5~~ | ~~-~~ | ~~0.0.7~~ | ~~-~~ | ~~6~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| 194 | net.bytebuddy:byte-buddy-agent | net.bytebuddy.agent ✳️ | 2026-07-02 | 11,9 | 9,2 | 1.18.11 | 1.18.11 | 318 | 182 | 20 | 20 | 9 | 171,6 | 69,4 🔺 | 1,7 | 🔺 size |
| ~~195~~ | ~~org.codehaus.plexus:plexus-compiler-api~~ | ~~-~~ | ~~2026-01-25~~ | ~~20,8~~ | ~~-~~ | ~~2.16.2~~ | ~~-~~ | ~~46~~ | ~~0~~ | ~~3~~ | ~~0~~ | ~~21~~ | ~~45,4~~ | ~~0,8~~ | ~~2,7~~ | ~~-~~ |
| ~~196~~ | ~~org.apache.maven.plugins:maven-clean-plugin ⚠️~~ | ~~-~~ | ~~2025-05-27~~ | ~~20,3~~ | ~~-~~ | ~~3.5.0~~ | ~~-~~ | ~~22~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~35~~ | ~~44,4~~ | ~~2,7~~ | ~~3,1~~ | ~~-~~ |
| ~~197~~ | ~~org.eclipse.aether:aether-util 🚩~~ | ~~-~~ | ~~2016-02-03~~ | ~~13,9~~ | ~~-~~ | ~~1.1.0~~ | ~~-~~ | ~~10~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| ~~198~~ | ~~org.apache.logging:logging-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| 199 | org.reactivestreams:reactive-streams 🚩 | org.reactivestreams ⚙️ | 2022-05-22 | 12,1 | 8,7 | 1.0.4 | 1.0.4 | 22 | 6 | 0 | 0 |  |  |  |  |  |
| 200 | org.mockito:mockito-core | org.mockito 🏷️ | 2026-03-11 | 18,3 | 8,8 | 5.23.0 | 5.23.0 | 349 | 105 | 5 | 5 | 21 | 320,0 | 9,9 | 0,7 | - |
| 201 | org.codehaus.mojo:animal-sniffer-annotations |  | 2026-01-18 | 16,7 |  | 1.27 |  | 25 | 0 | 3 | 0 | 29 | 39,6 | 1,3 | 3,5 | - |
| 202 | org.springframework.boot:spring-boot-loader-tools | spring.boot.loader.tools ⚙️ | 2026-06-25 | 12,4 | 8,5 | 3.5.16 | 3.5.16 | 290 | 225 | 35 | 35 | 336 | 3788,6 🔺 | 132,2 🔺 | 2,9 | 🔺 files, size |
| ~~203~~ | ~~org.codehaus.plexus:plexus-compiler-javac~~ | ~~-~~ | ~~2026-01-25~~ | ~~20,8~~ | ~~-~~ | ~~2.16.2~~ | ~~-~~ | ~~47~~ | ~~0~~ | ~~3~~ | ~~0~~ | ~~21~~ | ~~45,4~~ | ~~0,8~~ | ~~2,7~~ | ~~-~~ |
| ~~204~~ | ~~org.apache.maven:maven-artifact-manager 🚩~~ | ~~-~~ | ~~2010-02-12~~ | ~~20,3~~ | ~~-~~ | ~~2.0.11~~ | ~~-~~ | ~~21~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~36~~ | ~~360,2~~ | ~~12,5~~ | ~~0,8~~ | ~~-~~ |
| ~~205~~ | ~~org.codehaus.plexus:plexus-compiler-manager~~ | ~~-~~ | ~~2026-01-25~~ | ~~20,8~~ | ~~-~~ | ~~2.16.2~~ | ~~-~~ | ~~46~~ | ~~0~~ | ~~3~~ | ~~0~~ | ~~21~~ | ~~45,4~~ | ~~0,8~~ | ~~2,7~~ | ~~-~~ |
| ~~206~~ | ~~org.apache.maven.surefire:surefire-booter~~ | ~~-~~ | ~~2026-06-02~~ | ~~20,3~~ | ~~-~~ | ~~3.6.0-M1~~ | ~~-~~ | ~~68~~ | ~~0~~ | ~~4~~ | ~~0~~ | ~~20~~ | ~~418,0~~ | ~~12,9~~ | ~~0,3~~ | ~~-~~ |
| ~~207~~ | ~~org.apache.maven.surefire:maven-surefire-common~~ | ~~-~~ | ~~2026-06-02~~ | ~~16,0~~ | ~~-~~ | ~~3.6.0-M1~~ | ~~-~~ | ~~57~~ | ~~0~~ | ~~4~~ | ~~0~~ | ~~20~~ | ~~418,0~~ | ~~12,9~~ | ~~0,3~~ | ~~-~~ |
| 208 | commons-cli:commons-cli | org.apache.commons.cli ✳️ | 2025-11-08 | 20,8 | 2,8 | 1.11.0 | 1.11.0 | 16 | 6 | 1 | 1 | 1 | 36,0 | 1,3 | 0,1 | - |
| 209 | io.prometheus:parent |  |  |  |  |  |  | 0 | 0 | 0 | 0 | 30 | 586,8 | 15,9 | 0,8 | - |
| ~~210~~ | ~~org.apache.maven:maven-aether-provider 🚩~~ | ~~-~~ | ~~2015-11-10~~ | ~~16,0~~ | ~~-~~ | ~~3.3.9~~ | ~~-~~ | ~~17~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~36~~ | ~~360,2~~ | ~~12,5~~ | ~~0,8~~ | ~~-~~ |
| 211 | io.grpc:grpc-context |  | 2026-06-23 | 9,9 |  | 1.82.1 |  | 175 | 0 | 13 | 0 | 38 | 619,6 | 203,5 🔺 | 1,4 | 🔺 size |
| ~~212~~ | ~~org.codehaus.groovy:groovy-bom~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| ~~213~~ | ~~org.apache.maven:maven-archiver~~ | ~~-~~ | ~~2025-12-20~~ | ~~20,3~~ | ~~-~~ | ~~3.6.6~~ | ~~-~~ | ~~39~~ | ~~0~~ | ~~2~~ | ~~0~~ | ~~36~~ | ~~360,2~~ | ~~12,5~~ | ~~0,8~~ | ~~-~~ |
| 214 | com.github.luben:zstd-jni | com.github.luben.zstd_jni ✳️ | 2026-08-08 | 10,7 | 8,1 | 1.5.7-13 | 1.5.7-13 | 152 | 107 | 9 | 9 | 1 | 144,0 | 18,8 | 0,8 | - |
| 215 | org.jetbrains.kotlinx:kotlinx-coroutines-android | kotlinx.coroutines.android 🏷️ | 2026-05-07 | 8,9 | 3,4 | 1.11.0 | 1.11.0 | 118 | 20 | 3 | 3 | 768 | 744,5 🔺 | 91,4 🔺 | 57,3 🔺 | 🔺 files, size, releases |
| 216 | org.springframework.boot:spring-boot-buildpack-platform | spring.boot.buildpack.platform ⚙️ | 2026-06-25 | 6,2 | 6,2 | 3.5.16 | 3.5.16 | 182 | 182 | 35 | 35 | 336 | 3788,6 🔺 | 132,2 🔺 | 2,9 | 🔺 files, size |
| ~~217~~ | ~~io.zipkin.reporter2:zipkin-reporter-bom~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~11~~ | ~~264,0~~ | ~~4,2~~ | ~~0,1~~ | ~~-~~ |
| ~~218~~ | ~~org.jboss.weld:weld-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~23~~ | ~~133,1~~ | ~~7,7~~ | ~~1,3~~ | ~~-~~ |
| 219 | org.springframework:spring-webmvc | spring.webmvc ⚙️ | 2026-06-08 | 20,6 | 8,9 | 7.0.8 | 7.0.8 | 337 | 195 | 24 | 24 | 24 | 449,0 | 109,6 🔺 | 2,0 | 🔺 size |
| ~~220~~ | ~~org.apache.maven.surefire:surefire-api~~ | ~~-~~ | ~~2026-06-02~~ | ~~20,2~~ | ~~-~~ | ~~3.6.0-M1~~ | ~~-~~ | ~~65~~ | ~~0~~ | ~~4~~ | ~~0~~ | ~~20~~ | ~~418,0~~ | ~~12,9~~ | ~~0,3~~ | ~~-~~ |
| ~~221~~ | ~~org.apache.maven:maven-project 🚩~~ | ~~-~~ | ~~2010-02-12~~ | ~~20,3~~ | ~~-~~ | ~~2.0.11~~ | ~~-~~ | ~~24~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~36~~ | ~~360,2~~ | ~~12,5~~ | ~~0,8~~ | ~~-~~ |
| ~~222~~ | ~~org.springframework.batch:spring-batch-bom~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~4~~ | ~~64,0~~ | ~~8,2~~ | ~~1,2~~ | ~~-~~ |
| ~~223~~ | ~~org.eclipse.jetty.ee10:jetty-ee10-bom~~ | ~~-~~ | ~~2026-08-03~~ | ~~0,2~~ | ~~-~~ | ~~12.1.12~~ | ~~-~~ | ~~4~~ | ~~0~~ | ~~4~~ | ~~0~~ | ~~19~~ | ~~483,2~~ | ~~39,7 🔺~~ | ~~2,2~~ | ~~🔺 size~~ |
| ~~224~~ | ~~org.eclipse.sisu:org.eclipse.sisu.inject~~ | ~~org.eclipse.sisu.inject ⚙️~~ | ~~2026-06-24~~ | ~~13,9~~ | ~~0,1~~ | ~~1.0.1~~ | ~~1.0.1~~ | ~~26~~ | ~~2~~ | ~~2~~ | ~~2~~ | ~~6~~ | ~~123,0~~ | ~~2,5~~ | ~~0,2~~ | ~~-~~ |
| ~~225~~ | ~~com.sun.xml.bind.mvn:jaxb-runtime-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~1~~ | ~~20,0~~ | ~~0,0~~ | ~~0,1~~ | ~~-~~ |
| ~~226~~ | ~~org.apache.maven:maven-profile 🚩~~ | ~~-~~ | ~~2010-02-12~~ | ~~20,3~~ | ~~-~~ | ~~2.0.11~~ | ~~-~~ | ~~20~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~36~~ | ~~360,2~~ | ~~12,5~~ | ~~0,8~~ | ~~-~~ |
| 227 | org.springframework.boot:spring-boot-starter-logging | spring.boot.starter.logging ⚙️ | 2026-06-25 | 12,4 | 8,5 | 3.5.16 | 3.5.16 | 290 | 225 | 35 | 35 | 336 | 3788,6 🔺 | 132,2 🔺 | 2,9 | 🔺 files, size |
| ~~228~~ | ~~io.zipkin.brave:brave-bom~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~39~~ | ~~924,0~~ | ~~13,5~~ | ~~0,1~~ | ~~-~~ |
| ~~229~~ | ~~com.sun.xml.bind.mvn:jaxb-txw-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~1~~ | ~~20,0~~ | ~~0,0~~ | ~~0,1~~ | ~~-~~ |
| 230 | org.ow2.asm:asm-util | org.objectweb.asm.util ✳️ | 2026-05-23 | 14,7 | 9,1 | 9.10.1 | 9.10.1 | 45 | 33 | 4 | 4 | 7 | 242,5 | 2,2 | 0,3 | - |
| 231 | org.springframework:spring-tx | spring.tx ⚙️ | 2026-06-08 | 18,7 | 8,9 | 7.0.8 | 7.0.8 | 298 | 195 | 24 | 24 | 24 | 449,0 | 109,6 🔺 | 2,0 | 🔺 size |
| ~~232~~ | ~~org.apache.maven:maven-plugin-registry 🚩~~ | ~~-~~ | ~~2010-02-12~~ | ~~20,3~~ | ~~-~~ | ~~2.0.11~~ | ~~-~~ | ~~20~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~36~~ | ~~360,2~~ | ~~12,5~~ | ~~0,8~~ | ~~-~~ |
| 233 | org.jetbrains.kotlinx:kotlinx-coroutines-core | kotlinx.coroutines.core.artifact_disambiguating_module ⚙️ | 2026-05-07 | 8,9 | 0,3 | 1.11.0 | 1.11.0 | 118 | 3 | 3 | 3 | 768 | 744,5 🔺 | 91,4 🔺 | 57,3 🔺 | 🔺 files, size, releases |
| 234 | io.prometheus:simpleclient_bom |  |  |  |  |  |  | 0 | 0 | 0 | 0 | 30 | 586,8 | 15,9 | 0,8 | - |
| 235 | org.springframework.boot:spring-boot-starter-json | spring.boot.starter.json ⚙️ | 2026-06-25 | 8,5 | 8,5 | 3.5.16 | 3.5.16 | 225 | 225 | 35 | 35 | 336 | 3788,6 🔺 | 132,2 🔺 | 2,9 | 🔺 files, size |
| ~~236~~ | ~~org.eclipse.sisu:org.eclipse.sisu.plexus~~ | ~~org.eclipse.sisu.plexus ⚙️~~ | ~~2026-06-24~~ | ~~13,9~~ | ~~0,1~~ | ~~1.0.1~~ | ~~1.0.1~~ | ~~26~~ | ~~2~~ | ~~2~~ | ~~2~~ | ~~6~~ | ~~123,0~~ | ~~2,5~~ | ~~0,2~~ | ~~-~~ |
| ~~237~~ | ~~org.springframework.ws:spring-ws-bom~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~5~~ | ~~80,0~~ | ~~13,0~~ | ~~0,8~~ | ~~-~~ |
| ~~238~~ | ~~com.google.http-client:google-http-client-bom~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~11~~ | ~~220,0~~ | ~~3,2~~ | ~~0,7~~ | ~~-~~ |
| 239 | classworlds:classworlds 🚩 |  | 2006-01-12 | 21,0 |  | 1.1 |  | 13 | 0 | 0 | 0 |  |  |  |  |  |
| 240 | io.projectreactor:reactor-core | reactor.core ⚙️ | 2026-06-08 | 11,5 | 6,9 | 3.8.6 | 3.8.6 | 202 | 145 | 19 | 19 | 5 | 127,9 | 7,9 | 2,8 | - |
| 241 | jakarta.annotation:jakarta.annotation-api ⚠️ | jakarta.annotation ✳️ | 2024-02-15 | 7,8 | 7,8 | 3.0.0 | 3.0.0 | 10 | 10 | 0 | 0 |  |  |  |  |  |
| 242 | io.netty:netty-tcnative-boringssl-static | io.netty.internal.tcnative ✳️ | 2026-07-20 | 10,5 | 7,5 | 2.0.81.Final | 2.0.81.Final | 103 | 60 | 9 | 9 | 66 | 1150,9 🔺 | 47,5 🔺 | 3,2 | 🔺 files, size |
| 243 | org.glassfish.jaxb:txw2 | com.sun.xml.txw2 ✳️ | 2026-05-28 | 12,4 | 8,1 | 4.0.9 | 4.0.9 | 45 | 38 | 4 | 4 | 27 | 399,0 | 16,2 | 0,3 | - |
| ~~244~~ | ~~org.sonatype.sisu:sisu-guice 🚩~~ | ~~com.google.guice ⚙️~~ | ~~2018-04-03~~ | ~~15,9~~ | ~~8,4~~ | ~~4.2.0~~ | ~~4.2.0~~ | ~~31~~ | ~~1~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| 245 | net.minidev:json-smart ⚠️ |  | 2025-08-02 | 15,2 |  | 2.6.0 |  | 35 | 0 | 0 | 0 |  |  |  |  |  |
| 246 | org.springframework.data.build:spring-data-build |  |  |  |  |  |  | 0 | 0 | 0 | 0 |  |  |  |  |  |
| 247 | org.glassfish.jaxb:jaxb-runtime | org.glassfish.jaxb.runtime ✳️ | 2026-05-28 | 12,4 | 8,1 | 4.0.9 | 4.0.9 | 45 | 38 | 4 | 4 | 27 | 399,0 | 16,2 | 0,3 | - |
| 248 | org.slf4j:jcl-over-slf4j | org.apache.commons.logging ✳️ | 2026-05-12 | 18,2 | 9,3 | 2.0.18 | 2.0.18 | 94 | 48 | 1 | 1 | 12 | 294,0 | 3,0 | 0,1 | - |
| 249 | org.jetbrains.kotlin:kotlin-util-io |  | 2026-06-25 | 7,0 |  | 2.4.10-RC |  | 159 | 0 | 32 | 0 | 175 | 3924,5 🔺 | 568,3 🔺 | 3,2 | 🔺 files, size |
| ~~250~~ | ~~org.apache.maven.shared:maven-shared-incremental 🚩~~ | ~~-~~ | ~~2013-04-03~~ | ~~13,7~~ | ~~-~~ | ~~1.1~~ | ~~-~~ | ~~2~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~4~~ | ~~29,0~~ | ~~0,6~~ | ~~0,5~~ | ~~-~~ |
| ~~251~~ | ~~org.assertj:assertj-bom~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~6~~ | ~~35,3~~ | ~~6,4~~ | ~~0,7~~ | ~~-~~ |
| 252 | org.jetbrains.kotlin:kotlin-compiler-embeddable |  | 2026-06-25 | 11,0 |  | 2.4.10-RC |  | 233 | 0 | 32 | 0 | 175 | 3924,5 🔺 | 568,3 🔺 | 3,2 | 🔺 files, size |
| 253 | org.springframework.boot:spring-boot-starter-web | spring.boot.starter.web ⚙️ | 2026-06-25 | 12,4 | 8,5 | 3.5.16 | 3.5.16 | 290 | 225 | 35 | 35 | 336 | 3788,6 🔺 | 132,2 🔺 | 2,9 | 🔺 files, size |
| ~~254~~ | ~~org.jetbrains.kotlinx:kotlinx-serialization-bom~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~768~~ | ~~744,5 🔺~~ | ~~91,4 🔺~~ | ~~57,3 🔺~~ | ~~🔺 files, size, releases~~ |
| 255 | com.sun.istack:istack-commons-runtime 🚩 | com.sun.istack.runtime ✳️ | 2023-04-14 | 16,2 | 8,0 | 4.2.0 | 4.2.0 | 48 | 16 | 0 | 0 |  |  |  |  |  |
| 256 | org.jetbrains.kotlin:kotlin-native-utils | kotlin.native_utils ⚙️ | 2026-06-25 | 7,9 | 1,6 | 2.4.10-RC | 2.4.10-RC | 171 | 54 | 32 | 35 | 175 | 3924,5 🔺 | 568,3 🔺 | 3,2 | 🔺 files, size |
| 257 | org.hamcrest:hamcrest ⚠️ | org.hamcrest ⚙️ | 2024-08-01 | 7,7 | 7,7 | 3.0 | 3.0 | 9 | 9 | 0 | 0 |  |  |  |  |  |
| ~~258~~ | ~~org.sonatype.plexus:plexus-sec-dispatcher 🚩~~ | ~~-~~ | ~~2009-12-16~~ | ~~17,5~~ | ~~-~~ | ~~1.4~~ | ~~-~~ | ~~9~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| 259 | org.apache.commons:commons-collections4 ⚠️ | org.apache.commons.collections4 ✳️ | 2025-04-19 | 12,7 | 8,1 | 4.5.0 | 4.5.0 | 9 | 7 | 0 | 0 | 36 | 52,4 | 3,2 | 3,3 | - |
| 260 | com.github.ben-manes.caffeine:caffeine | com.github.benmanes.caffeine ✳️ | 2026-05-03 | 11,4 | 8,6 | 3.2.4 | 3.2.4 | 73 | 37 | 2 | 2 | 4 | 300,0 | 212,1 | 0,2 | - |
| 261 | org.apache.httpcomponents.core5:httpcore5 | org.apache.httpcomponents.core5.httpcore5 ⚙️ | 2026-06-21 | 10,6 | 5,9 | 5.5-beta2 | 5.5-beta2 | 57 | 40 | 10 | 10 | 5 | 72,8 | 7,7 | 0,8 | - |
| 262 | software.amazon.awssdk:core |  | 2026-08-07 | 9,1 |  | 2.51.3 |  | 61 | 0 | 52 | 0 | 520 | 7784,8 🔺 | 3309,0 🔺 | 27,7 🔺 | 🔺 files, size, releases |
| 263 | org.jetbrains.kotlin:kotlin-gradle-plugin | org.opentest4j ✳️ | 2026-07-22 | 13,1 | 5,8 | 2.4.20-Beta2 | 1.3.0 | 296 | 153 | 35 | 35 | 175 | 3924,5 🔺 | 568,3 🔺 | 3,2 | 🔺 files, size |
| 264 | org.jetbrains.kotlin:kotlin-util-klib |  | 2026-06-25 | 7,0 |  | 2.4.10-RC |  | 159 | 0 | 32 | 0 | 175 | 3924,5 🔺 | 568,3 🔺 | 3,2 | 🔺 files, size |
| ~~265~~ | ~~org.apache.maven.plugins:maven-jar-plugin~~ | ~~-~~ | ~~2025-11-11~~ | ~~20,3~~ | ~~-~~ | ~~3.5.0~~ | ~~-~~ | ~~26~~ | ~~0~~ | ~~1~~ | ~~0~~ | ~~35~~ | ~~44,4~~ | ~~2,7~~ | ~~3,1~~ | ~~-~~ |
| 266 | org.jetbrains.kotlin:kotlin-daemon-client |  | 2026-06-25 | 9,5 |  | 2.4.10-RC |  | 202 | 0 | 32 | 0 | 175 | 3924,5 🔺 | 568,3 🔺 | 3,2 | 🔺 files, size |
| 267 | org.jetbrains.kotlin:kotlin-daemon-embeddable |  | 2026-06-25 | 7,0 |  | 2.4.10-RC |  | 159 | 0 | 32 | 0 | 175 | 3924,5 🔺 | 568,3 🔺 | 3,2 | 🔺 files, size |
| ~~268~~ | ~~com.google.auto.value:auto-value-parent 🚩~~ | ~~-~~ | ~~2019-04-05~~ | ~~7,4~~ | ~~-~~ | ~~1.6.4~~ | ~~-~~ | ~~1~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~2~~ | ~~48,0~~ | ~~3,3~~ | ~~0,1~~ | ~~-~~ |
| ~~269~~ | ~~org.apache.maven.plugins:maven-install-plugin ⚠️~~ | ~~-~~ | ~~2025-02-24~~ | ~~20,3~~ | ~~-~~ | ~~3.1.4~~ | ~~-~~ | ~~20~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~35~~ | ~~44,4~~ | ~~2,7~~ | ~~3,1~~ | ~~-~~ |
| 270 | org.springframework:spring-test | spring.test ⚙️ | 2026-06-08 | 18,7 | 8,9 | 7.0.8 | 7.0.8 | 298 | 195 | 24 | 24 | 24 | 449,0 | 109,6 🔺 | 2,0 | 🔺 size |
| 271 | org.jetbrains.kotlin:kotlin-compiler-runner |  | 2026-06-25 | 9,5 |  | 2.4.10-RC |  | 203 | 0 | 32 | 0 | 175 | 3924,5 🔺 | 568,3 🔺 | 3,2 | 🔺 files, size |
| ~~272~~ | ~~com.querydsl:querydsl-bom~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| 273 | com.jayway.jsonpath:json-path | json.path ⚙️ | 2026-02-22 | 15,5 | 2,6 | 3.0.0 | 3.0.0 | 25 | 3 | 2 | 2 | 2 | 100,0 | 1,2 | 0,2 | - |
| 274 | net.minidev:accessors-smart ⚠️ |  | 2025-08-02 | 11,0 |  | 2.6.0 |  | 14 | 0 | 0 | 0 |  |  |  |  |  |
| 275 | org.jdom:jdom2 🚩 | org.jdom2 ⚙️ | 2021-12-06 | 14,5 | 4,7 | 2.0.6.1 | 2.0.6.1 | 10 | 1 | 0 | 0 |  |  |  |  |  |
| 276 | io.grpc:grpc-api | io.grpc ⚙️ | 2026-06-23 | 7,2 | 2,9 | 1.82.1 | 1.82.1 | 141 | 49 | 13 | 18 | 38 | 619,6 | 203,5 🔺 | 1,4 | 🔺 size |
| 277 | org.jetbrains.kotlinx:kotlinx-serialization-core-jvm | kotlinx.serialization.core 🏷️ | 2026-04-09 | 5,5 | 4,9 | 1.11.0 | 1.11.0 | 37 | 28 | 3 | 3 | 768 | 744,5 🔺 | 91,4 🔺 | 57,3 🔺 | 🔺 files, size, releases |
| 278 | javax.annotation:javax.annotation-api 🚩 | java.annotation ⚙️ | 2018-02-21 | 13,6 | 8,9 | 1.3.2 | 1.3.2 | 8 | 2 | 0 | 0 |  |  |  |  |  |
| 279 | org.apache.httpcomponents.client5:httpclient5 | org.apache.httpcomponents.client5.httpclient5 ⚙️ | 2026-08-06 | 10,6 | 5,9 | 5.6.4 | 5.6.4 | 47 | 36 | 7 | 8 | 5 | 83,4 | 6,5 | 0,6 | - |
| 280 | org.apache.httpcomponents.core5:httpcore5-h2 | org.apache.httpcomponents.core5.httpcore5.h2 ⚙️ | 2026-06-21 | 9,6 | 5,9 | 5.5-beta2 | 5.5-beta2 | 56 | 40 | 10 | 10 | 5 | 72,8 | 7,7 | 0,8 | - |
| 281 | org.springframework.boot:spring-boot-starter-tomcat | spring.boot.starter.tomcat ⚙️ | 2026-06-25 | 12,4 | 8,5 | 3.5.16 | 3.5.16 | 290 | 225 | 35 | 35 | 336 | 3788,6 🔺 | 132,2 🔺 | 2,9 | 🔺 files, size |
| ~~282~~ | ~~org.apache.maven.surefire:surefire-logger-api~~ | ~~-~~ | ~~2026-06-02~~ | ~~9,3~~ | ~~-~~ | ~~3.6.0-M1~~ | ~~-~~ | ~~33~~ | ~~0~~ | ~~4~~ | ~~0~~ | ~~20~~ | ~~418,0~~ | ~~12,9~~ | ~~0,3~~ | ~~-~~ |
| 283 | org.jetbrains.kotlin:kotlin-klib-commonizer-api |  | 2026-06-25 | 5,2 |  | 2.4.10-RC |  | 134 | 0 | 32 | 0 | 175 | 3924,5 🔺 | 568,3 🔺 | 3,2 | 🔺 files, size |
| ~~284~~ | ~~org.apache.maven.wagon:wagon-provider-api 🚩~~ | ~~-~~ | ~~2022-12-18~~ | ~~20,3~~ | ~~-~~ | ~~3.5.3~~ | ~~-~~ | ~~40~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| 285 | com.google.auto.value:auto-value-annotations |  | 2025-11-11 | 8,4 |  | 1.11.1 |  | 28 | 0 | 1 | 0 | 2 | 48,0 | 3,3 | 0,1 | - |
| ~~286~~ | ~~org.apache.maven.reporting:maven-reporting~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~1~~ | ~~29,0~~ | ~~0,5~~ | ~~0,1~~ | ~~-~~ |
| ~~287~~ | ~~org.springframework.restdocs:spring-restdocs-bom~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~5~~ | ~~73,1~~ | ~~14,3~~ | ~~0,6~~ | ~~-~~ |
| ~~288~~ | ~~jakarta.platform:jakarta.jakartaee-bom~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| ~~289~~ | ~~jakarta.platform:jakartaee-api-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| 290 | org.apache.logging.log4j:log4j-core | org.apache.logging.log4j.core ✳️ | 2026-06-29 | 14,0 | 8,7 | 2.26.1 | 2.26.1 | 77 | 45 | 5 | 6 | 29 | 580,0 | 8,1 | 0,4 | - |
| 291 | org.projectlombok:lombok | lombok 🏷️ | 2026-04-21 | 15,4 | 8,2 | 1.18.46 | 1.18.46 | 59 | 25 | 4 | 4 | 1 | 16,0 | 7,5 | 0,3 | - |
| 292 | org.slf4j:jul-to-slf4j | jul.to.slf4j ✳️ | 2026-05-12 | 18,2 | 8,4 | 2.0.18 | 2.0.18 | 94 | 33 | 1 | 1 | 12 | 294,0 | 3,0 | 0,1 | - |
| 293 | org.jetbrains.kotlin:kotlin-tooling-core |  | 2026-06-25 | 4,3 |  | 2.4.10-RC |  | 114 | 0 | 32 | 0 | 175 | 3924,5 🔺 | 568,3 🔺 | 3,2 | 🔺 files, size |
| 294 | org.xerial.snappy:snappy-java ⚠️ |  | 2025-07-19 | 15,4 |  | 1.1.10.8 |  | 81 | 0 | 0 | 0 |  |  |  |  |  |
| ~~295~~ | ~~org.apache.maven.doxia:doxia-modules~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~13~~ | ~~312,0~~ | ~~7,8~~ | ~~0,1~~ | ~~-~~ |
| ~~296~~ | ~~org.sonatype.plexus:plexus-cipher 🚩~~ | ~~-~~ | ~~2011-07-26~~ | ~~17,7~~ | ~~-~~ | ~~1.7~~ | ~~-~~ | ~~8~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| ~~297~~ | ~~com.google.http-client:google-http-client-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~11~~ | ~~220,0~~ | ~~3,2~~ | ~~0,7~~ | ~~-~~ |
| 298 | io.grpc:grpc-stub | io.grpc.stub ⚙️ | 2026-06-23 | 11,2 | 2,9 | 1.82.1 | 1.82.1 | 191 | 49 | 13 | 18 | 38 | 619,6 | 203,5 🔺 | 1,4 | 🔺 size |
| ~~299~~ | ~~org.apache.maven:maven-plugin-descriptor 🚩~~ | ~~-~~ | ~~2010-02-12~~ | ~~20,3~~ | ~~-~~ | ~~2.0.11~~ | ~~-~~ | ~~21~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~36~~ | ~~360,2~~ | ~~12,5~~ | ~~0,8~~ | ~~-~~ |
| ~~300~~ | ~~org.apache.maven:maven-error-diagnostics 🚩~~ | ~~-~~ | ~~2010-02-12~~ | ~~20,3~~ | ~~-~~ | ~~2.0.11~~ | ~~-~~ | ~~16~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~36~~ | ~~360,2~~ | ~~12,5~~ | ~~0,8~~ | ~~-~~ |
| ~~301~~ | ~~org.springframework.security:spring-security-bom~~ | ~~-~~ | ~~2026-06-09~~ | ~~12,7~~ | ~~-~~ | ~~7.1.0~~ | ~~-~~ | ~~122~~ | ~~0~~ | ~~3~~ | ~~0~~ | ~~26~~ | ~~319,1~~ | ~~27,4 🔺~~ | ~~3,6~~ | ~~🔺 size~~ |
| ~~302~~ | ~~org.eclipse.aether:aether-api 🚩~~ | ~~-~~ | ~~2016-02-03~~ | ~~13,9~~ | ~~-~~ | ~~1.1.0~~ | ~~-~~ | ~~10~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| 303 | io.grpc:grpc-core | io.grpc.internal ⚙️ | 2026-06-23 | 11,2 | 2,9 | 1.82.1 | 1.82.1 | 191 | 49 | 13 | 18 | 38 | 619,6 | 203,5 🔺 | 1,4 | 🔺 size |
| 304 | com.google.api.grpc:proto-google-common-protos |  | 2026-07-08 | 9,3 |  | 2.73.0 |  | 164 | 0 | 22 | 0 | 710 | 470,8 🔺 | 49,2 🔺 | 54,3 🔺 | 🔺 files, size, releases |
| ~~305~~ | ~~org.apache.maven:maven-plugin-parameter-documenter 🚩~~ | ~~-~~ | ~~2010-02-12~~ | ~~20,3~~ | ~~-~~ | ~~2.0.11~~ | ~~-~~ | ~~17~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~36~~ | ~~360,2~~ | ~~12,5~~ | ~~0,8~~ | ~~-~~ |
| ~~306~~ | ~~org.apache.maven:maven-monitor 🚩~~ | ~~-~~ | ~~2010-02-12~~ | ~~20,3~~ | ~~-~~ | ~~2.0.11~~ | ~~-~~ | ~~22~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~36~~ | ~~360,2~~ | ~~12,5~~ | ~~0,8~~ | ~~-~~ |
| ~~307~~ | ~~org.sonatype.sisu:sisu-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| ~~308~~ | ~~org.sonatype.sisu:sisu-inject~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| 309 | com.google.protobuf:protobuf-java-util | com.google.protobuf.util ⚙️ | 2026-08-03 | 11,0 | 6,9 | 4.36.0-RC2 | 4.36.0-RC2 | 204 | 150 | 23 | 24 | 6 | 77,6 | 19,3 | 2,1 | - |
| 310 | io.grpc:grpc-protobuf-lite | io.grpc.protobuf.lite ⚙️ | 2026-06-23 | 10,3 | 2,9 | 1.82.1 | 1.82.1 | 181 | 49 | 13 | 18 | 38 | 619,6 | 203,5 🔺 | 1,4 | 🔺 size |
| 311 | org.springframework.boot:spring-boot-test | spring.boot.test ⚙️ | 2026-06-25 | 10,0 | 8,5 | 3.5.16 | 3.5.16 | 256 | 225 | 35 | 35 | 336 | 3788,6 🔺 | 132,2 🔺 | 2,9 | 🔺 files, size |
| ~~312~~ | ~~com.datastax.oss:java-driver-bom~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~125~~ | ~~501,5~~ | ~~142,6 🔺~~ | ~~1,6~~ | ~~🔺 size~~ |
| 313 | javax.xml.bind:jaxb-api 🚩 | java.xml.bind 🏷️ | 2018-09-12 | 20,1 | 9,0 | 2.3.1 | 2.3.1 | 34 | 4 | 0 | 0 |  |  |  |  |  |
| 314 | org.jetbrains.kotlin:kotlin-scripting-common |  | 2026-06-25 | 8,2 |  | 2.4.10-RC |  | 176 | 0 | 32 | 0 | 175 | 3924,5 🔺 | 568,3 🔺 | 3,2 | 🔺 files, size |
| 315 | org.jetbrains.kotlin:kotlin-scripting-jvm |  | 2026-06-25 | 8,2 |  | 2.4.10-RC |  | 176 | 0 | 32 | 0 | 175 | 3924,5 🔺 | 568,3 🔺 | 3,2 | 🔺 files, size |
| ~~316~~ | ~~org.sonatype.sisu.inject:guice-bean~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| ~~317~~ | ~~org.sonatype.sisu.inject:guice-plexus~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| ~~318~~ | ~~org.codehaus.plexus:plexus-xml~~ | ~~-~~ | ~~2026-01-25~~ | ~~3,2~~ | ~~-~~ | ~~4.1.1~~ | ~~-~~ | ~~10~~ | ~~0~~ | ~~1~~ | ~~0~~ | ~~21~~ | ~~45,4~~ | ~~0,8~~ | ~~2,7~~ | ~~-~~ |
| ~~319~~ | ~~io.fabric8:kubernetes-client-bom~~ | ~~-~~ | ~~2026-06-29~~ | ~~0,1~~ | ~~-~~ | ~~7.8.0~~ | ~~-~~ | ~~1~~ | ~~0~~ | ~~1~~ | ~~0~~ | ~~137~~ | ~~1398,4 🔺~~ | ~~255,7 🔺~~ | ~~1,4~~ | ~~🔺 files, size~~ |
| 320 | commons-digester:commons-digester 🚩 |  | 2010-09-24 | 20,7 |  | 2.1 |  | 10 | 0 | 0 | 0 |  |  |  |  |  |
| 321 | io.netty:netty-transport-classes-epoll | io.netty.transport.classes.epoll ⚙️ | 2026-06-02 | 4,8 | 4,8 | 4.1.135.Final | 4.1.135.Final | 92 | 96 | 24 | 28 | 66 | 1150,9 🔺 | 47,5 🔺 | 3,2 | 🔺 files, size |
| 322 | io.grpc:grpc-protobuf | io.grpc.protobuf ⚙️ | 2026-06-23 | 11,2 | 2,9 | 1.82.1 | 1.82.1 | 191 | 49 | 13 | 18 | 38 | 619,6 | 203,5 🔺 | 1,4 | 🔺 size |
| 323 | org.assertj:assertj-core | org.assertj.core ✳️ | 2026-01-24 | 13,4 | 8,3 | 3.27.7 | 3.27.7 | 82 | 44 | 3 | 3 | 6 | 35,3 | 6,4 | 0,7 | - |
| ~~324~~ | ~~org.apache.maven.resolver:maven-resolver-util~~ | ~~org.apache.maven.resolver.util ✳️~~ | ~~2026-06-30~~ | ~~9,6~~ | ~~9,1~~ | ~~2.0.20~~ | ~~2.0.20~~ | ~~74~~ | ~~74~~ | ~~11~~ | ~~12~~ | ~~31~~ | ~~402,3~~ | ~~15,6~~ | ~~1,3~~ | ~~-~~ |
| 325 | org.springframework.boot:spring-boot-starter-test | spring.boot.starter.test ⚙️ | 2026-06-25 | 12,4 | 8,5 | 3.5.16 | 3.5.16 | 290 | 225 | 35 | 35 | 336 | 3788,6 🔺 | 132,2 🔺 | 2,9 | 🔺 files, size |
| ~~326~~ | ~~org.apache.maven.shared:maven-dependency-tree ⚠️~~ | ~~-~~ | ~~2024-05-26~~ | ~~19,7~~ | ~~-~~ | ~~3.3.0~~ | ~~-~~ | ~~14~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~4~~ | ~~29,0~~ | ~~0,6~~ | ~~0,5~~ | ~~-~~ |
| ~~327~~ | ~~org.apache.maven.resolver:maven-resolver-api~~ | ~~org.apache.maven.resolver ✳️~~ | ~~2026-06-30~~ | ~~9,6~~ | ~~9,1~~ | ~~2.0.20~~ | ~~2.0.20~~ | ~~74~~ | ~~74~~ | ~~11~~ | ~~12~~ | ~~31~~ | ~~402,3~~ | ~~15,6~~ | ~~1,3~~ | ~~-~~ |
| 328 | org.springframework.boot:spring-boot-test-autoconfigure | spring.boot.test.autoconfigure ⚙️ | 2026-06-25 | 10,0 | 8,5 | 3.5.16 | 3.5.16 | 256 | 225 | 35 | 35 | 336 | 3788,6 🔺 | 132,2 🔺 | 2,9 | 🔺 files, size |
| ~~329~~ | ~~org.apache.groovy:groovy-bom~~ | ~~-~~ | ~~2026-08-01~~ | ~~0,1~~ | ~~-~~ | ~~6.0.0-beta-1~~ | ~~-~~ | ~~3~~ | ~~0~~ | ~~3~~ | ~~0~~ | ~~43~~ | ~~2259,4 🔺~~ | ~~106,3 🔺~~ | ~~1,4~~ | ~~🔺 files, size~~ |
| ~~330~~ | ~~org.sonatype.sisu.inject:guice-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| 331 | org.jetbrains.kotlin:kotlin-scripting-compiler-embeddable |  | 2026-06-25 | 8,0 |  | 2.4.10-RC |  | 174 | 0 | 32 | 0 | 175 | 3924,5 🔺 | 568,3 🔺 | 3,2 | 🔺 files, size |
| 332 | org.jetbrains.kotlin:kotlin-scripting-compiler-impl-embeddable |  | 2026-06-25 | 7,2 |  | 2.4.10-RC |  | 161 | 0 | 32 | 0 | 175 | 3924,5 🔺 | 568,3 🔺 | 3,2 | 🔺 files, size |
| 333 | org.jetbrains.kotlinx:kotlinx-serialization-json-jvm | kotlinx.serialization.json 🏷️ | 2026-04-09 | 5,5 | 4,9 | 1.11.0 | 1.11.0 | 36 | 28 | 3 | 3 | 768 | 744,5 🔺 | 91,4 🔺 | 57,3 🔺 | 🔺 files, size, releases |
| 334 | org.apache.httpcomponents:project |  |  |  |  |  |  | 0 | 0 | 0 | 0 |  |  |  |  |  |
| 335 | org.bouncycastle:bcprov-jdk18on | org.bouncycastle.provider 🏷️ | 2026-08-07 | 4,4 | 4,4 | 1.85.2 | 1.85.2 | 19 | 20 | 6 | 7 | 60 | 156,0 | 43,1 🔺 | 2,6 | 🔺 size |
| 336 | io.micrometer:micrometer-observation | micrometer.observation ⚙️ | 2026-06-08 | 3,8 | 3,8 | 1.17.0 | 1.17.0 | 104 | 104 | 27 | 27 | 42 | 721,7 🔺 | 12,2 | 4,9 | 🔺 files |
| 337 | org.jboss.logging:jboss-logging | org.jboss.logging ✳️ | 2026-03-17 | 16,5 | 8,5 | 3.6.3.Final | 3.6.3.Final | 37 | 13 | 2 | 2 | 2 | 24,0 | 0,3 | 0,3 | - |
| ~~338~~ | ~~org.seleniumhq.selenium:selenium-bom~~ | ~~-~~ | ~~2026-06-16~~ | ~~0,2~~ | ~~-~~ | ~~4.45.0~~ | ~~-~~ | ~~1~~ | ~~0~~ | ~~1~~ | ~~0~~ | ~~35~~ | ~~416,3~~ | ~~72,9 🔺~~ | ~~1,1~~ | ~~🔺 size~~ |
| 339 | io.micrometer:micrometer-commons | micrometer.commons ⚙️ | 2026-06-08 | 3,8 | 3,8 | 1.17.0 | 1.17.0 | 104 | 104 | 27 | 27 | 42 | 721,7 🔺 | 12,2 | 4,9 | 🔺 files |
| ~~340~~ | ~~org.apache.maven.shared:file-management ⚠️~~ | ~~-~~ | ~~2025-04-13~~ | ~~20,3~~ | ~~-~~ | ~~3.2.0~~ | ~~-~~ | ~~7~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~4~~ | ~~29,0~~ | ~~0,6~~ | ~~0,5~~ | ~~-~~ |
| 341 | org.jetbrains.kotlin:kotlin-gradle-plugin-idea-proto |  | 2026-06-25 | 4,0 |  | 2.4.10-RC |  | 109 | 0 | 32 | 0 | 175 | 3924,5 🔺 | 568,3 🔺 | 3,2 | 🔺 files, size |
| 342 | jakarta.validation:jakarta.validation-api | jakarta.validation ✳️ | 2025-10-22 | 7,7 | 7,7 | 4.0.0-M1 | 4.0.0-M1 | 11 | 11 | 1 | 1 | 5 | 150,0 | 72,2 | 0,1 | - |
| 343 | org.springframework:spring-jdbc | spring.jdbc ⚙️ | 2026-06-08 | 20,6 | 8,9 | 7.0.8 | 7.0.8 | 327 | 195 | 24 | 24 | 24 | 449,0 | 109,6 🔺 | 2,0 | 🔺 size |
| ~~344~~ | ~~com.sun.xml.bind.mvn:jaxb-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~1~~ | ~~20,0~~ | ~~0,0~~ | ~~0,1~~ | ~~-~~ |
| ~~345~~ | ~~com.oracle.database.jdbc:ojdbc-bom~~ | ~~-~~ | ~~2026-08-03~~ | ~~0,2~~ | ~~-~~ | ~~21.22.0.0~~ | ~~-~~ | ~~3~~ | ~~0~~ | ~~3~~ | ~~0~~ | ~~23~~ | ~~91,3~~ | ~~19,9~~ | ~~1,0~~ | ~~-~~ |
| ~~346~~ | ~~org.codehaus.plexus:plexus-velocity~~ | ~~-~~ | ~~2025-10-12~~ | ~~20,8~~ | ~~-~~ | ~~2.3.0~~ | ~~-~~ | ~~14~~ | ~~0~~ | ~~1~~ | ~~0~~ | ~~21~~ | ~~45,4~~ | ~~0,8~~ | ~~2,7~~ | ~~-~~ |
| ~~347~~ | ~~org.apache.maven.surefire:surefire-extensions-api~~ | ~~-~~ | ~~2026-06-02~~ | ~~6,7~~ | ~~-~~ | ~~3.6.0-M1~~ | ~~-~~ | ~~24~~ | ~~0~~ | ~~4~~ | ~~0~~ | ~~20~~ | ~~418,0~~ | ~~12,9~~ | ~~0,3~~ | ~~-~~ |
| ~~348~~ | ~~org.apache.maven.doxia:doxia-core~~ | ~~-~~ | ~~2026-03-17~~ | ~~20,3~~ | ~~-~~ | ~~2.1.0~~ | ~~-~~ | ~~38~~ | ~~0~~ | ~~1~~ | ~~0~~ | ~~13~~ | ~~312,0~~ | ~~7,8~~ | ~~0,1~~ | ~~-~~ |
| 349 | software.amazon.awssdk:services |  | 2026-08-07 | 0,2 |  | 2.51.3 |  | 52 | 0 | 52 | 0 | 520 | 7784,8 🔺 | 3309,0 🔺 | 27,7 🔺 | 🔺 files, size, releases |
| 350 | org.javassist:javassist | org.javassist ⚙️ | 2026-06-21 | 16,0 | 3,9 | 3.32.0-GA | 3.32.0-GA | 33 | 6 | 2 | 2 | 1 | 24,0 | 6,9 | 0,2 | - |
| ~~351~~ | ~~org.apache.maven.surefire:surefire-extensions-spi~~ | ~~-~~ | ~~2026-06-02~~ | ~~6,2~~ | ~~-~~ | ~~3.6.0-M1~~ | ~~-~~ | ~~23~~ | ~~0~~ | ~~4~~ | ~~0~~ | ~~20~~ | ~~418,0~~ | ~~12,9~~ | ~~0,3~~ | ~~-~~ |
| 352 | org.bouncycastle:bcprov-jdk15on 🚩 | org.bouncycastle.provider 🏷️ | 2021-12-01 | 14,4 | 8,1 | 1.70 | 1.70 | 26 | 12 | 0 | 0 | 60 | 156,0 | 43,1 🔺 | 2,6 | 🔺 size |
| 353 | com.fasterxml.jackson.module:jackson-modules-base |  | 2026-07-08 | 0,2 |  | 2.18.9 |  | 6 | 0 | 6 | 0 | 23 | 690,7 | 14,9 | 1,5 | - |
| 354 | org.json:json | org.json ✳️ | 2026-05-22 | 18,8 | 8,0 | 20260522 | 20260522 | 32 | 18 | 2 | 3 | 1 | 24,0 | 0,4 | 0,2 | - |
| 355 | com.fasterxml.jackson.dataformat:jackson-dataformat-cbor | com.fasterxml.jackson.dataformat.cbor ✳️ | 2026-07-08 | 12,4 | 8,8 | 2.18.9 | 2.18.9 | 160 | 95 | 18 | 18 | 12 | 357,3 | 5,3 | 1,5 | - |
| 356 | org.jetbrains.kotlin:kotlin-gradle-plugin-annotations |  | 2026-06-25 | 3,5 |  | 2.4.10-RC |  | 98 | 0 | 32 | 0 | 175 | 3924,5 🔺 | 568,3 🔺 | 3,2 | 🔺 files, size |
| ~~357~~ | ~~org.apache.pulsar:pulsar-bom~~ | ~~-~~ | ~~2026-06-16~~ | ~~0,2~~ | ~~-~~ | ~~5.0.0-M1~~ | ~~-~~ | ~~1~~ | ~~0~~ | ~~1~~ | ~~0~~ | ~~109~~ | ~~1414,5 🔺~~ | ~~2685,7 🔺~~ | ~~1,8~~ | ~~🔺 files, size~~ |
| 358 | org.springframework.security:spring-security-crypto | spring.security.crypto ⚙️ | 2026-06-09 | 14,7 | 8,7 | 7.1.0 | 7.1.0 | 264 | 213 | 31 | 31 | 26 | 319,1 | 27,4 🔺 | 3,6 | 🔺 size |
| ~~359~~ | ~~org.apache.maven.doxia:doxia-site-renderer~~ | ~~-~~ | ~~2026-03-31~~ | ~~20,3~~ | ~~-~~ | ~~2.1.0~~ | ~~-~~ | ~~46~~ | ~~0~~ | ~~1~~ | ~~0~~ | ~~13~~ | ~~312,0~~ | ~~7,8~~ | ~~0,1~~ | ~~-~~ |
| 360 | org.eclipse.jetty:jetty-project |  | 2026-08-03 | 0,2 |  | 12.1.12 |  | 4 | 0 | 4 | 0 | 82 | 882,6 🔺 | 77,9 🔺 | 3,3 | 🔺 files, size |
| 361 | org.apache.velocity:velocity 🚩 |  | 2010-11-29 | 19,4 |  | 1.7 |  | 10 | 0 | 0 | 0 |  |  |  |  |  |
| ~~362~~ | ~~org.jboss.arquillian:arquillian-bom~~ | ~~-~~ | ~~2026-05-20~~ | ~~0,2~~ | ~~-~~ | ~~1.10.2.Final~~ | ~~-~~ | ~~1~~ | ~~0~~ | ~~1~~ | ~~0~~ | ~~3~~ | ~~36,0~~ | ~~0,0~~ | ~~0,1~~ | ~~-~~ |
| 363 | org.apache.kafka:kafka-clients |  | 2026-06-17 | 11,8 |  | 4.3.1 |  | 84 | 0 | 10 | 0 | 70 | 4230,9 🔺 | 79,4 | 0,8 | 🔺 files |
| ~~364~~ | ~~org.xmlunit:xmlunit-parent~~ | ~~-~~ | ~~2026-05-31~~ | ~~0,2~~ | ~~-~~ | ~~2.12.0~~ | ~~-~~ | ~~1~~ | ~~0~~ | ~~1~~ | ~~0~~ | ~~9~~ | ~~243,0~~ | ~~2,9~~ | ~~0,3~~ | ~~-~~ |
| ~~365~~ | ~~org.apache.maven.surefire:surefire-shared-utils~~ | ~~-~~ | ~~2026-06-02~~ | ~~6,7~~ | ~~-~~ | ~~3.6.0-M1~~ | ~~-~~ | ~~24~~ | ~~6~~ | ~~4~~ | ~~0~~ | ~~20~~ | ~~418,0~~ | ~~12,9~~ | ~~0,3~~ | ~~-~~ |
| ~~366~~ | ~~org.jetbrains.kotlin:kotlin-gradle-plugins-bom~~ | ~~-~~ | ~~2026-06-25~~ | ~~0,2~~ | ~~-~~ | ~~2.4.10-RC~~ | ~~-~~ | ~~4~~ | ~~0~~ | ~~4~~ | ~~0~~ | ~~175~~ | ~~3924,5 🔺~~ | ~~568,3 🔺~~ | ~~3,2~~ | ~~🔺 files, size~~ |
| 367 | net.sf.jopt-simple:jopt-simple 🚩 | joptsimple ⚙️ | 2018-09-11 | 18,4 | 7,9 | 6.0-alpha-3 | 6.0-alpha-3 | 45 | 1 | 0 | 0 |  |  |  |  |  |
| ~~368~~ | ~~org.sonatype.sisu:sisu-inject-bean 🚩~~ | ~~-~~ | ~~2015-02-20~~ | ~~15,9~~ | ~~-~~ | ~~2.6.0~~ | ~~-~~ | ~~28~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| ~~369~~ | ~~org.sonatype.sisu:sisu-inject-plexus 🚩~~ | ~~-~~ | ~~2015-02-20~~ | ~~15,9~~ | ~~-~~ | ~~2.6.0~~ | ~~-~~ | ~~28~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| 370 | com.fasterxml:classmate | com.fasterxml.classmate ✳️ | 2026-01-02 | 15,7 | 8,9 | 1.7.3 | 1.7.3 | 22 | 9 | 3 | 3 | 2 | 24,0 | 0,9 | 0,4 | - |
| 371 | org.aspectj:aspectjweaver | org.aspectj.weaver ⚙️ | 2025-12-17 | 15,7 | 8,4 | 1.9.25.1 | 1.9.25.1 | 66 | 32 | 2 | 2 | 5 | 72,0 | 103,1 | 0,2 | - |
| ~~372~~ | ~~org.codehaus.plexus:plexus-interactivity-api~~ | ~~-~~ | ~~2025-11-07~~ | ~~20,8~~ | ~~-~~ | ~~1.5.1~~ | ~~-~~ | ~~9~~ | ~~0~~ | ~~1~~ | ~~0~~ | ~~21~~ | ~~45,4~~ | ~~0,8~~ | ~~2,7~~ | ~~-~~ |
| ~~373~~ | ~~org.springframework.data.build:spring-data-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| 374 | org.apache.httpcomponents:httpmime 🚩 | org.apache.httpcomponents.httpmime ⚙️ | 2022-11-30 | 18,5 | 8,1 | 4.5.14 | 4.5.14 | 53 | 9 | 0 | 0 |  |  |  |  |  |
| 375 | org.springframework.boot:spring-boot-actuator | spring.boot.actuator ⚙️ | 2026-06-25 | 12,4 | 8,5 | 3.5.16 | 3.5.16 | 290 | 225 | 35 | 35 | 336 | 3788,6 🔺 | 132,2 🔺 | 2,9 | 🔺 files, size |
| 376 | org.apache.logging.log4j:log4j-to-slf4j | org.apache.logging.log4j.to.slf4j ✳️ | 2026-06-29 | 13,3 | 8,7 | 2.26.1 | 2.26.1 | 70 | 43 | 5 | 6 | 29 | 580,0 | 8,1 | 0,4 | - |
| 377 | org.apache.tomcat.embed:tomcat-embed-el | org.apache.tomcat.embed.el ✳️ | 2026-06-18 | 12,5 | 6,9 | 9.0.119 | 9.0.119 | 425 | 217 | 31 | 34 | 4 | 70,8 | 9,2 | 2,6 | - |
| 378 | io.opentelemetry:opentelemetry-api | io.opentelemetry.api ⚙️ | 2026-06-05 | 6,7 | 6,4 | 1.63.0 | 1.63.0 | 102 | 103 | 12 | 14 | 33 | 714,2 🔺 | 3,8 | 2,0 | 🔺 files |
| 379 | org.jetbrains.kotlin:kotlin-script-runtime |  | 2026-06-25 | 9,5 |  | 2.4.10-RC |  | 202 | 0 | 32 | 0 | 175 | 3924,5 🔺 | 568,3 🔺 | 3,2 | 🔺 files, size |
| 380 | org.jsoup:jsoup | org.jsoup ✳️ | 2026-07-30 | 16,5 | 8,3 | 1.23.1 | 1.23.1 | 55 | 25 | 4 | 4 | 1 | 30,0 | 3,4 | 0,3 | - |
| 381 | com.fasterxml.woodstox:woodstox-core | com.ctc.wstx ✳️ | 2026-08-04 | 11,5 | 8,4 | 7.2.2 | 7.2.2 | 41 | 37 | 3 | 3 | 1 | 24,0 | 3,6 | 0,3 | - |
| 382 | org.springframework.boot:spring-boot-actuator-autoconfigure | spring.boot.actuator.autoconfigure ⚙️ | 2026-06-25 | 8,5 | 8,5 | 3.5.16 | 3.5.16 | 225 | 225 | 35 | 35 | 336 | 3788,6 🔺 | 132,2 🔺 | 2,9 | 🔺 files, size |
| 383 | org.apache.tomcat.embed:tomcat-embed-core | org.apache.tomcat.embed.core ✳️ | 2026-06-18 | 16,1 | 6,9 | 9.0.119 | 9.0.119 | 459 | 215 | 29 | 32 | 4 | 70,8 | 9,2 | 2,6 | - |
| ~~384~~ | ~~org.sonatype.aether:aether-api 🚩~~ | ~~-~~ | ~~2011-11-29~~ | ~~16,1~~ | ~~-~~ | ~~1.13.1~~ | ~~-~~ | ~~18~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| 385 | org.springframework.boot:spring-boot-starter-actuator | spring.boot.starter.actuator ⚙️ | 2026-06-25 | 12,4 | 8,5 | 3.5.16 | 3.5.16 | 290 | 225 | 35 | 35 | 336 | 3788,6 🔺 | 132,2 🔺 | 2,9 | 🔺 files, size |
| 386 | org.jspecify:jspecify ⚠️ | org.jspecify 🏷️ | 2024-07-16 | 5,1 | 5,1 | 1.0.0 | 1.0.0 | 6 | 7 | 0 | 1 |  |  |  |  |  |
| 387 | org.mockito:mockito-junit-jupiter | org.mockito.junit.jupiter 🏷️ | 2026-03-11 | 8,4 | 5,6 | 5.23.0 | 5.23.0 | 100 | 58 | 5 | 5 | 21 | 320,0 | 9,9 | 0,7 | - |
| 388 | aopalliance:aopalliance 🚩 |  | 2005-08-01 | 21,0 |  | 1.0 |  | 1 | 0 | 0 | 0 |  |  |  |  |  |
| ~~389~~ | ~~org.jboss.shrinkwrap.resolver:shrinkwrap-resolver-bom~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~15~~ | ~~252,0~~ | ~~11,3~~ | ~~0,3~~ | ~~-~~ |
| ~~390~~ | ~~org.jboss.shrinkwrap:shrinkwrap-bom~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~4~~ | ~~72,0~~ | ~~2,8~~ | ~~0,1~~ | ~~-~~ |
| 391 | org.jetbrains.kotlin:kotlin-build-tools-api |  | 2026-06-25 | 3,2 |  | 2.4.10-RC |  | 92 | 0 | 32 | 0 | 175 | 3924,5 🔺 | 568,3 🔺 | 3,2 | 🔺 files, size |
| ~~392~~ | ~~org.apache.ant:ant-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~29~~ | ~~214,8~~ | ~~7,4~~ | ~~0,4~~ | ~~-~~ |
| 393 | io.opentelemetry:opentelemetry-context | io.opentelemetry.context ⚙️ | 2026-06-05 | 5,8 | 5,8 | 1.63.0 | 1.63.0 | 89 | 91 | 12 | 14 | 33 | 714,2 🔺 | 3,8 | 2,0 | 🔺 files |
| ~~394~~ | ~~org.apache.maven.plugins:maven-deploy-plugin ⚠️~~ | ~~-~~ | ~~2025-02-23~~ | ~~20,3~~ | ~~-~~ | ~~3.1.4~~ | ~~-~~ | ~~23~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~35~~ | ~~44,4~~ | ~~2,7~~ | ~~3,1~~ | ~~-~~ |
| ~~395~~ | ~~org.sonatype.aether:aether-util 🚩~~ | ~~-~~ | ~~2011-11-29~~ | ~~16,1~~ | ~~-~~ | ~~1.13.1~~ | ~~-~~ | ~~18~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| 396 | org.iq80.snappy:snappy ⚠️ |  | 2024-05-22 | 14,9 |  | 0.5 |  | 5 | 0 | 0 | 0 |  |  |  |  |  |
| ~~397~~ | ~~org.sonatype.aether:aether-spi 🚩~~ | ~~-~~ | ~~2011-11-29~~ | ~~16,1~~ | ~~-~~ | ~~1.13.1~~ | ~~-~~ | ~~18~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| ~~398~~ | ~~org.sonatype.aether:aether-impl 🚩~~ | ~~-~~ | ~~2011-11-29~~ | ~~16,1~~ | ~~-~~ | ~~1.13.1~~ | ~~-~~ | ~~18~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| ~~399~~ | ~~org.apache.maven.plugins:maven-dependency-plugin~~ | ~~-~~ | ~~2026-05-24~~ | ~~19,6~~ | ~~-~~ | ~~3.11.0~~ | ~~-~~ | ~~35~~ | ~~0~~ | ~~3~~ | ~~0~~ | ~~35~~ | ~~44,4~~ | ~~2,7~~ | ~~3,1~~ | ~~-~~ |
| 400 | org.codehaus.woodstox:stax2-api | org.codehaus.stax2 ✳️ | 2026-03-31 | 17,7 | 8,4 | 4.3.0 | 4.3.0 | 19 | 5 | 1 | 1 | 1 | 24,0 | 1,3 | 0,1 | - |
| 401 | xml-apis:xml-apis 🚩 |  | 2011-08-20 | 20,7 |  | 1.4.01 |  | 7 | 0 | 0 | 0 |  |  |  |  |  |
| ~~402~~ | ~~org.apache.maven.surefire:surefire-providers~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~20~~ | ~~418,0~~ | ~~12,9~~ | ~~0,3~~ | ~~-~~ |
| ~~403~~ | ~~org.jboss.shrinkwrap.descriptors:shrinkwrap-descriptors-bom~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| ~~404~~ | ~~org.springframework.session:spring-session-bom~~ | ~~-~~ | ~~2026-06-09~~ | ~~8,5~~ | ~~-~~ | ~~4.1.0~~ | ~~-~~ | ~~55~~ | ~~0~~ | ~~3~~ | ~~0~~ | ~~6~~ | ~~82,3~~ | ~~3,2~~ | ~~1,6~~ | ~~-~~ |
| 405 | org.springframework.boot:spring-boot-starter-jdbc | spring.boot.starter.jdbc ⚙️ | 2026-06-25 | 12,4 | 8,5 | 3.5.16 | 3.5.16 | 290 | 225 | 35 | 35 | 336 | 3788,6 🔺 | 132,2 🔺 | 2,9 | 🔺 files, size |
| ~~406~~ | ~~org.ow2.asm:asm-bom~~ | ~~-~~ | ~~2026-05-23~~ | ~~0,2~~ | ~~-~~ | ~~9.10.1~~ | ~~-~~ | ~~1~~ | ~~0~~ | ~~1~~ | ~~0~~ | ~~7~~ | ~~242,5~~ | ~~2,2~~ | ~~0,3~~ | ~~-~~ |
| 407 | org.jacoco:org.jacoco.build |  |  |  |  |  |  | 0 | 0 | 0 | 0 | 6 | 108,0 | 4,2 | 0,2 | - |
| 408 | org.springframework.boot:spring-boot-starter-validation | spring.boot.starter.validation ⚙️ | 2026-06-25 | 10,7 | 8,5 | 3.5.16 | 3.5.16 | 265 | 225 | 35 | 35 | 336 | 3788,6 🔺 | 132,2 🔺 | 2,9 | 🔺 files, size |
| 409 | org.apache.tomcat.embed:tomcat-embed-websocket | org.apache.tomcat.embed.websocket ✳️ | 2026-06-18 | 12,5 | 6,9 | 9.0.119 | 9.0.119 | 425 | 217 | 31 | 34 | 4 | 70,8 | 9,2 | 2,6 | - |
| ~~410~~ | ~~io.prometheus:prometheus-metrics-bom~~ | ~~-~~ | ~~2026-06-11~~ | ~~0,2~~ | ~~-~~ | ~~1.8.0~~ | ~~-~~ | ~~2~~ | ~~0~~ | ~~2~~ | ~~0~~ | ~~30~~ | ~~586,8~~ | ~~15,9~~ | ~~0,8~~ | ~~-~~ |
| ~~411~~ | ~~javax.xml.bind:jaxb-api-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| 412 | org.xmlunit:xmlunit-core | org.xmlunit ⚙️ | 2026-05-31 | 10,7 | 8,0 | 2.12.0 | 2.12.0 | 32 | 19 | 3 | 4 | 9 | 243,0 | 2,9 | 0,3 | - |
| ~~413~~ | ~~io.opentelemetry:opentelemetry-bom~~ | ~~-~~ | ~~2026-06-05~~ | ~~0,2~~ | ~~-~~ | ~~1.63.0~~ | ~~-~~ | ~~1~~ | ~~0~~ | ~~1~~ | ~~0~~ | ~~33~~ | ~~714,2 🔺~~ | ~~3,8~~ | ~~2,0~~ | ~~🔺 files~~ |
| ~~414~~ | ~~org.codehaus:codehaus-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| 415 | com.zaxxer:HikariCP | com.zaxxer.hikari ✳️ | 2026-06-14 | 12,8 | 8,6 | 7.1.0 | 7.1.0 | 104 | 34 | 3 | 3 | 2 | 24,4 | 0,7 | 0,4 | - |
| ~~416~~ | ~~org.apache.maven.shared:maven-artifact-transfer 🚩~~ | ~~org.apache.maven.shared.artifact.transfer ⚙️~~ | ~~2020-12-22~~ | ~~9,8~~ | ~~5,6~~ | ~~0.13.1~~ | ~~0.13.1~~ | ~~7~~ | ~~1~~ | ~~0~~ | ~~0~~ | ~~4~~ | ~~29,0~~ | ~~0,6~~ | ~~0,5~~ | ~~-~~ |
| ~~417~~ | ~~org.codehaus.plexus:plexus-i18n~~ | ~~-~~ | ~~2025-11-07~~ | ~~20,8~~ | ~~-~~ | ~~1.1.0~~ | ~~-~~ | ~~5~~ | ~~0~~ | ~~2~~ | ~~0~~ | ~~21~~ | ~~45,4~~ | ~~0,8~~ | ~~2,7~~ | ~~-~~ |
| 418 | com.squareup.okhttp3:logging-interceptor | okhttp3.logging 🏷️ | 2026-06-08 | 10,6 | 8,5 | 5.4.0 | 5.4.0 | 107 | 87 | 8 | 8 | 18 | 828,8 | 9,3 | 0,7 | - |
| 419 | org.postgresql:postgresql | org.postgresql.jdbc ⚙️ | 2026-06-29 | 13,4 | 6,9 | 42.7.12 | 42.7.12 | 201 | 64 | 5 | 6 | 2 | 31,2 | 4,4 | 0,8 | - |
| 420 | com.fasterxml.jackson.dataformat:jackson-dataformat-xml | com.fasterxml.jackson.dataformat.xml ✳️ | 2026-07-08 | 14,5 | 8,8 | 2.18.9 | 2.18.9 | 186 | 95 | 18 | 18 | 12 | 357,3 | 5,3 | 1,5 | - |
| 421 | org.hdrhistogram:HdrHistogram ⚠️ |  | 2024-05-30 | 12,7 |  | 2.2.2 |  | 27 | 1 | 0 | 0 |  |  |  |  |  |
| 422 | com.fasterxml.jackson.module:jackson-module-jaxb-annotations | com.fasterxml.jackson.module.jaxb ✳️ | 2026-07-08 | 14,5 | 8,8 | 2.18.9 | 2.18.9 | 186 | 95 | 18 | 18 | 23 | 690,7 | 14,9 | 1,5 | - |
| 423 | io.micrometer:micrometer-core | micrometer.core ⚙️ | 2026-06-08 | 9,1 | 6,3 | 1.17.0 | 1.17.0 | 257 | 181 | 27 | 27 | 42 | 721,7 🔺 | 12,2 | 4,9 | 🔺 files |
| ~~424~~ | ~~org.apache.maven.doxia:doxia-module-xhtml 🚩~~ | ~~-~~ | ~~2023-01-09~~ | ~~18,9~~ | ~~-~~ | ~~1.12.0~~ | ~~-~~ | ~~24~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~13~~ | ~~312,0~~ | ~~7,8~~ | ~~0,1~~ | ~~-~~ |
| 425 | com.sun.istack:istack-commons |  |  |  |  |  |  | 0 | 0 | 0 | 0 |  |  |  |  |  |
| 426 | org.scala-lang:scala-library | scala.library ⚙️ | 2026-06-08 | 17,7 | 8,4 | 3.9.0-RC1 | 3.9.0-RC1 | 241 | 59 | 25 | 7 | 35 | 289,1 🔺 | 238,7 🔺 | 4,4 | 🔺 files, size |
| ~~427~~ | ~~org.apache.maven.doxia:doxia-decoration-model 🚩~~ | ~~-~~ | ~~2023-03-19~~ | ~~20,3~~ | ~~-~~ | ~~2.0.0-M6~~ | ~~-~~ | ~~34~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~13~~ | ~~312,0~~ | ~~7,8~~ | ~~0,1~~ | ~~-~~ |
| ~~428~~ | ~~org.apache.maven:maven-toolchain 🚩~~ | ~~-~~ | ~~2010-02-12~~ | ~~18,5~~ | ~~-~~ | ~~2.0.11~~ | ~~-~~ | ~~10~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~36~~ | ~~360,2~~ | ~~12,5~~ | ~~0,8~~ | ~~-~~ |
| ~~429~~ | ~~org.apache.maven.reporting:maven-reporting-impl ⚠️~~ | ~~-~~ | ~~2024-10-12~~ | ~~20,3~~ | ~~-~~ | ~~4.0.0~~ | ~~-~~ | ~~35~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~1~~ | ~~29,0~~ | ~~0,5~~ | ~~0,1~~ | ~~-~~ |
| 430 | software.amazon.awssdk:protocols |  | 2026-08-07 | 0,2 |  | 2.51.3 |  | 52 | 0 | 52 | 0 | 520 | 7784,8 🔺 | 3309,0 🔺 | 27,7 🔺 | 🔺 files, size, releases |
| 431 | commons-validator:commons-validator | org.apache.commons.validator ✳️ | 2025-11-14 | 20,8 | 2,7 | 1.10.1 | 1.10.1 | 19 | 5 | 1 | 2 | 1 | 36,0 | 2,3 | 0,1 | - |
| 432 | org.springframework.security:spring-security-core | spring.security.core ⚙️ | 2026-06-09 | 18,3 | 8,7 | 7.1.0 | 7.1.0 | 282 | 213 | 31 | 31 | 26 | 319,1 | 27,4 🔺 | 3,6 | 🔺 size |
| 433 | org.jetbrains.intellij.deps:trove4j 🚩 |  | 2020-04-02 | 7,7 |  | 1.0.20200330 |  | 3 | 0 | 0 | 0 | 5 | 88,8 | 3,3 | 0,8 | - |
| 434 | io.prometheus:client_java |  | 2026-06-11 | 0,2 |  | 1.8.0 |  | 2 | 0 | 2 | 0 | 30 | 586,8 | 15,9 | 0,8 | - |
| 435 | org.apache.ant:ant |  | 2026-04-06 | 19,6 |  | 1.10.17 |  | 42 | 0 | 2 | 0 | 29 | 214,8 | 7,4 | 0,4 | - |
| 436 | org.skyscreamer:jsonassert ⚠️ |  | 2024-07-28 | 14,5 |  | 2.0-rc1 |  | 15 | 0 | 0 | 0 |  |  |  |  |  |
| ~~437~~ | ~~org.apache.activemq:activemq-bom~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~74~~ | ~~509,3~~ | ~~85,2 🔺~~ | ~~1,6~~ | ~~🔺 size~~ |
| 438 | oro:oro 🚩 |  | 2005-11-22 | 20,7 |  | 2.0.8 |  | 3 | 0 | 0 | 0 |  |  |  |  |  |
| 439 | org.springframework:spring-orm | spring.orm ⚙️ | 2026-06-08 | 20,6 | 8,9 | 7.0.8 | 7.0.8 | 321 | 195 | 24 | 24 | 24 | 449,0 | 109,6 🔺 | 2,0 | 🔺 size |
| 440 | org.tomlj:tomlj ⚠️ | org.tomlj ⚙️ | 2023-12-31 | 7,3 | 3,8 | 1.1.1 | 1.1.1 | 3 | 2 | 0 | 0 |  |  |  |  |  |
| 441 | org.apache.ant:ant-launcher |  | 2026-04-06 | 19,6 |  | 1.10.17 |  | 42 | 0 | 2 | 0 | 29 | 214,8 | 7,4 | 0,4 | - |
| ~~442~~ | ~~org.awaitility:awaitility-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| 443 | software.amazon.awssdk:http-clients |  | 2026-08-07 | 0,2 |  | 2.51.3 |  | 52 | 0 | 52 | 0 | 520 | 7784,8 🔺 | 3309,0 🔺 | 27,7 🔺 | 🔺 files, size, releases |
| 444 | io.grpc:grpc-netty | io.grpc.netty ⚙️ | 2026-06-23 | 11,2 | 2,9 | 1.82.1 | 1.82.1 | 191 | 49 | 13 | 18 | 38 | 619,6 | 203,5 🔺 | 1,4 | 🔺 size |
| 445 | com.google.android:annotations 🚩 |  | 2012-08-31 | 13,9 |  | 4.1.1.4 |  | 1 | 0 | 0 | 0 |  |  |  |  |  |
| 446 | javax.enterprise:cdi-api 🚩 |  | 2018-07-19 | 16,8 |  | 2.0.SP1 |  | 38 | 0 | 0 | 0 |  |  |  |  |  |
| 447 | org.apache.commons:commons-math3 🚩 |  | 2016-03-17 | 14,4 |  | 3.6.1 |  | 10 | 0 | 0 | 0 | 36 | 52,4 | 3,2 | 3,3 | - |
| 448 | com.fasterxml.jackson.dataformat:jackson-dataformats-binary |  | 2026-07-08 | 0,2 |  | 2.18.9 |  | 6 | 0 | 6 | 0 | 12 | 357,3 | 5,3 | 1,5 | - |
| ~~449~~ | ~~org.apache.maven:apache-maven 🚩~~ | ~~-~~ | ~~2010-02-12~~ | ~~18,3~~ | ~~-~~ | ~~2.0.11~~ | ~~-~~ | ~~9~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~36~~ | ~~360,2~~ | ~~12,5~~ | ~~0,8~~ | ~~-~~ |
| 450 | com.vaadin.external.google:android-json 🚩 |  | 2014-02-28 | 12,5 |  | 0.0.20131108.vaadin1 |  | 1 | 0 | 0 | 0 |  |  |  |  |  |
| 451 | org.springframework.security:spring-security-web | spring.security.web ⚙️ | 2026-06-09 | 16,6 | 8,7 | 7.1.0 | 7.1.0 | 273 | 213 | 31 | 31 | 26 | 319,1 | 27,4 🔺 | 3,6 | 🔺 size |
| ~~452~~ | ~~io.smallrye:smallrye-build-parent~~ | ~~-~~ | ~~2026-07-01~~ | ~~0,2~~ | ~~-~~ | ~~51~~ | ~~-~~ | ~~2~~ | ~~0~~ | ~~2~~ | ~~0~~ | ~~90~~ | ~~252,4~~ | ~~4,9~~ | ~~3,7~~ | ~~-~~ |
| 453 | io.netty:netty-transport-native-kqueue | io.netty.transport.kqueue.linux.x86_64 ⚙️ | 2026-08-06 | 9,3 | 8,7 | 4.1.137.Final | 4.1.137.Final | 155 | 148 | 28 | 28 | 66 | 1150,9 🔺 | 47,5 🔺 | 3,2 | 🔺 files, size |
| ~~454~~ | ~~org.apache.maven.doxia:doxia-logging-api 🚩~~ | ~~-~~ | ~~2023-01-09~~ | ~~17,4~~ | ~~-~~ | ~~1.12.0~~ | ~~-~~ | ~~17~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~13~~ | ~~312,0~~ | ~~7,8~~ | ~~0,1~~ | ~~-~~ |
| 455 | org.apache.commons:commons-pool2 | org.apache.commons.pool2 ✳️ | 2025-12-30 | 12,8 | 6,0 | 2.13.1 | 2.13.1 | 23 | 9 | 2 | 2 | 36 | 52,4 | 3,2 | 3,3 | - |
| ~~456~~ | ~~io.grpc:grpc-bom~~ | ~~-~~ | ~~2026-06-23~~ | ~~0,2~~ | ~~-~~ | ~~1.82.1~~ | ~~-~~ | ~~2~~ | ~~0~~ | ~~2~~ | ~~0~~ | ~~38~~ | ~~619,6~~ | ~~203,5 🔺~~ | ~~1,4~~ | ~~🔺 size~~ |
| ~~457~~ | ~~org.hibernate.validator:hibernate-validator-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~6~~ | ~~98,6~~ | ~~4,7~~ | ~~0,6~~ | ~~-~~ |
| 458 | org.springframework.data:spring-data-commons | spring.data.commons ⚙️ | 2026-06-24 | 13,5 | 8,9 | 3.5.13 | 3.5.13 | 289 | 220 | 31 | 31 | 17 | 68,0 | 17,9 🔺 | 10,3 🔺 | 🔺 size, releases |
| 459 | org.springframework:spring-aspects | spring.aspects ⚙️ | 2026-06-08 | 20,6 | 8,9 | 7.0.8 | 7.0.8 | 314 | 195 | 24 | 24 | 24 | 449,0 | 109,6 🔺 | 2,0 | 🔺 size |
| 460 | org.apache.hadoop:hadoop-project |  |  |  |  |  |  | 0 | 0 | 0 | 0 | 85 | 2036,0 | 336,5 🔺 | 0,3 | 🔺 size |
| 461 | org.apache.hadoop:hadoop-main |  |  |  |  |  |  | 0 | 0 | 0 | 0 | 85 | 2036,0 | 336,5 🔺 | 0,3 | 🔺 size |
| ~~462~~ | ~~org.eclipse.jgit:org.eclipse.jgit-parent~~ | ~~-~~ | ~~2026-06-10~~ | ~~0,2~~ | ~~-~~ | ~~7.7.0.202606012155-r~~ | ~~-~~ | ~~1~~ | ~~0~~ | ~~1~~ | ~~0~~ | ~~17~~ | ~~447,0~~ | ~~45,7~~ | ~~0,5~~ | ~~-~~ |
| 463 | com.fasterxml.jackson.module:jackson-module-kotlin | com.fasterxml.jackson.kotlin ✳️ | 2026-07-08 | 11,9 | 8,8 | 2.18.9 | 2.18.9 | 169 | 78 | 18 | 18 | 23 | 690,7 | 14,9 | 1,5 | - |
| 464 | org.scala-lang:scala-reflect | scala.reflect ⚙️ | 2025-12-08 | 14,2 | 8,4 | 2.12.21 | 2.12.21 | 128 | 55 | 3 | 3 | 35 | 289,1 🔺 | 238,7 🔺 | 4,4 | 🔺 files, size |
| 465 | org.springframework.boot:spring-boot-starter-aop | spring.boot.starter.aop ⚙️ | 2026-06-25 | 12,4 | 8,5 | 3.5.16 | 3.5.16 | 273 | 208 | 18 | 18 | 336 | 3788,6 🔺 | 132,2 🔺 | 2,9 | 🔺 files, size |
| 466 | org.springframework:spring-context-support | spring.context.support ⚙️ | 2026-06-08 | 18,7 | 8,9 | 7.0.8 | 7.0.8 | 298 | 195 | 24 | 24 | 24 | 449,0 | 109,6 🔺 | 2,0 | 🔺 size |
| 467 | org.springframework.security:spring-security-config | spring.security.config ⚙️ | 2026-06-09 | 16,6 | 8,7 | 7.1.0 | 7.1.0 | 273 | 213 | 31 | 31 | 26 | 319,1 | 27,4 🔺 | 3,6 | 🔺 size |
| ~~468~~ | ~~org.apache.maven.plugins:maven-assembly-plugin~~ | ~~-~~ | ~~2025-11-22~~ | ~~20,3~~ | ~~-~~ | ~~3.8.0~~ | ~~-~~ | ~~36~~ | ~~0~~ | ~~1~~ | ~~0~~ | ~~35~~ | ~~44,4~~ | ~~2,7~~ | ~~3,1~~ | ~~-~~ |
| 469 | org.hibernate.validator:hibernate-validator | org.hibernate.validator ⚙️ | 2026-06-22 | 9,5 | 9,1 | 8.0.4.Final | 8.0.4.Final | 87 | 86 | 7 | 10 | 6 | 98,6 | 4,7 | 0,6 | - |
| 470 | org.apache.activemq:artemis-project |  |  |  |  |  |  | 0 | 0 | 0 | 0 | 74 | 509,3 | 85,2 🔺 | 1,6 | 🔺 size |
| ~~471~~ | ~~org.apache.maven.plugins:maven-site-plugin~~ | ~~-~~ | ~~2026-05-20~~ | ~~20,3~~ | ~~-~~ | ~~3.22.0~~ | ~~-~~ | ~~53~~ | ~~0~~ | ~~1~~ | ~~0~~ | ~~35~~ | ~~44,4~~ | ~~2,7~~ | ~~3,1~~ | ~~-~~ |
| 472 | io.netty:netty-resolver-dns | io.netty.resolver.dns ⚙️ | 2026-06-02 | 11,4 | 8,7 | 4.1.135.Final | 4.1.135.Final | 175 | 148 | 24 | 28 | 66 | 1150,9 🔺 | 47,5 🔺 | 3,2 | 🔺 files, size |
| 473 | org.hibernate.common:hibernate-commons-annotations ⚠️ | org.hibernate.commons.annotations 🏷️ | 2024-10-21 | 15,0 | 8,5 | 7.0.3.Final | 7.0.3.Final | 30 | 20 | 0 | 0 |  |  |  |  |  |
| ~~474~~ | ~~org.apache.activemq:artemis-bom~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~74~~ | ~~509,3~~ | ~~85,2 🔺~~ | ~~1,6~~ | ~~🔺 size~~ |
| 475 | com.nimbusds:nimbus-jose-jwt | com.nimbusds.jose.jwt ✳️ | 2026-05-31 | 13,6 | 6,0 | 10.9.1 | 10.9.1 | 305 | 120 | 7 | 7 | 11 | 30,4 | 7,4 | 4,2 | - |
| 476 | io.netty:netty-codec-dns | io.netty.codec.dns ⚙️ | 2026-06-02 | 12,1 | 8,7 | 4.1.135.Final | 4.1.135.Final | 178 | 148 | 24 | 28 | 66 | 1150,9 🔺 | 47,5 🔺 | 3,2 | 🔺 files, size |
| 477 | org.eclipse.jgit:org.eclipse.jgit | org.eclipse.jgit ⚙️ | 2026-06-10 | 14,6 | 8,6 | 7.7.0.202606012155-r | 7.7.0.202606012155-r | 171 | 93 | 6 | 7 | 17 | 447,0 | 45,7 | 0,5 | - |
| ~~478~~ | ~~io.r2dbc:r2dbc-bom~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~2~~ | ~~24,0~~ | ~~3,6~~ | ~~0,4~~ | ~~-~~ |
| 479 | org.assertj:assertj-parent-pom |  |  |  |  |  |  | 0 | 0 | 0 | 0 | 6 | 35,3 | 6,4 | 0,7 | - |
| 480 | com.google.http-client:google-http-client | com.google.api.client ⚙️ | 2026-07-20 | 15,0 | 7,8 | 2.2.0 | 2.2.0 | 91 | 64 | 8 | 8 | 11 | 220,0 | 3,2 | 0,7 | - |
| ~~481~~ | ~~org.jboss.weld:weld-api-bom~~ | ~~-~~ | ~~2026-06-09~~ | ~~0,2~~ | ~~-~~ | ~~7.0.Beta2~~ | ~~-~~ | ~~2~~ | ~~0~~ | ~~2~~ | ~~0~~ | ~~23~~ | ~~133,1~~ | ~~7,7~~ | ~~1,3~~ | ~~-~~ |
| 482 | org.eclipse.jetty:jetty-util | org.eclipse.jetty.util ✳️ | 2026-08-03 | 17,4 | 7,8 | 12.1.12 | 12.1.12 | 439 | 179 | 29 | 29 | 82 | 882,6 🔺 | 77,9 🔺 | 3,3 | 🔺 files, size |
| 483 | io.netty:netty-resolver-dns-native-macos | io.netty.resolver.dns.macos.linux.x86_64 ⚙️ | 2026-08-06 | 6,7 | 6,7 | 4.1.137.Final | 4.1.137.Final | 122 | 122 | 28 | 28 | 66 | 1150,9 🔺 | 47,5 🔺 | 3,2 | 🔺 files, size |
| ~~484~~ | ~~org.apache.maven.plugin-tools:maven-plugin-annotations~~ | ~~-~~ | ~~2025-10-20~~ | ~~14,2~~ | ~~-~~ | ~~3.15.2~~ | ~~-~~ | ~~29~~ | ~~0~~ | ~~2~~ | ~~0~~ | ~~10~~ | ~~184,0~~ | ~~3,2~~ | ~~0,2~~ | ~~-~~ |
| 485 | org.bouncycastle:bcpkix-jdk15on 🚩 | org.bouncycastle.pkix 🏷️ | 2021-12-01 | 14,4 | 8,1 | 1.70 | 1.70 | 24 | 11 | 0 | 0 | 60 | 156,0 | 43,1 🔺 | 2,6 | 🔺 size |
| ~~486~~ | ~~org.jboss.weld:weld-api-parent~~ | ~~-~~ | ~~2026-06-09~~ | ~~0,2~~ | ~~-~~ | ~~7.0.Beta2~~ | ~~-~~ | ~~2~~ | ~~0~~ | ~~2~~ | ~~0~~ | ~~23~~ | ~~133,1~~ | ~~7,7~~ | ~~1,3~~ | ~~-~~ |
| ~~487~~ | ~~org.springframework.amqp:spring-amqp-bom~~ | ~~-~~ | ~~2026-06-24~~ | ~~0,2~~ | ~~-~~ | ~~3.2.12~~ | ~~-~~ | ~~4~~ | ~~0~~ | ~~4~~ | ~~0~~ | ~~8~~ | ~~120,6~~ | ~~22,1~~ | ~~1,6~~ | ~~-~~ |
| 488 | org.fusesource:fusesource-pom |  |  |  |  |  |  | 0 | 0 | 0 | 0 |  |  |  |  |  |
| ~~489~~ | ~~org.apache.cassandra:java-driver-bom~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~47~~ | ~~312,1~~ | ~~96,7 🔺~~ | ~~1,8~~ | ~~🔺 size~~ |
| ~~490~~ | ~~com.google.inject:guice-parent 🚩~~ | ~~-~~ | ~~2015-04-28~~ | ~~12,4~~ | ~~-~~ | ~~4.0~~ | ~~-~~ | ~~3~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| 491 | org.jetbrains.kotlin:kotlin-project-model ⚠️ |  | 2024-07-19 | 5,2 |  | 1.9.25 |  | 56 | 0 | 0 | 0 | 175 | 3924,5 🔺 | 568,3 🔺 | 3,2 | 🔺 files, size |
| 492 | org.jetbrains.kotlin:kotlin-android-extensions |  | 2025-08-13 | 11,4 |  | 2.2.10 |  | 209 | 0 | 1 | 0 | 175 | 3924,5 🔺 | 568,3 🔺 | 3,2 | 🔺 files, size |
| 493 | software.amazon.awssdk:third-party |  | 2026-08-07 | 0,2 |  | 2.51.3 |  | 52 | 0 | 52 | 0 | 520 | 7784,8 🔺 | 3309,0 🔺 | 27,7 🔺 | 🔺 files, size, releases |
| 494 | com.google.inject:guice 🚩 | com.google.guice ⚙️ | 2023-05-12 | 17,2 | 8,5 | 7.0.0 | 7.0.0 | 24 | 13 | 0 | 0 |  |  |  |  |  |
| 495 | org.awaitility:awaitility ⚠️ |  | 2025-02-21 | 10,2 |  | 4.3.0 |  | 23 | 0 | 0 | 0 |  |  |  |  |  |
| 496 | org.springframework.boot:spring-boot-starter-data-jpa | spring.boot.starter.data.jpa ⚙️ | 2026-06-25 | 12,4 | 8,5 | 3.5.16 | 3.5.16 | 290 | 225 | 35 | 35 | 336 | 3788,6 🔺 | 132,2 🔺 | 2,9 | 🔺 files, size |
| 497 | org.lz4:lz4-java |  | 2025-11-26 | 9,0 |  | 1.8.1 |  | 10 | 4 | 1 | 0 | 1 | 26,0 | 0,0 | 0,1 | - |
| 498 | software.amazon.awssdk:bom |  | 2026-08-07 | 0,2 |  | 2.51.3 |  | 52 | 0 | 52 | 0 | 520 | 7784,8 🔺 | 3309,0 🔺 | 27,7 🔺 | 🔺 files, size, releases |
| 499 | org.jvnet.staxex:stax-ex 🚩 | org.jvnet.staxex ✳️ | 2022-02-17 | 19,7 | 8,3 | 2.1.0 | 2.1.0 | 27 | 11 | 0 | 0 |  |  |  |  |  |
| 500 | com.diffplug.spotless:spotless-lib |  | 2026-06-29 | 9,6 |  | 4.8.0 |  | 156 | 0 | 11 | 0 | 4 | 66,7 | 1,1 | 2,8 | - |
| 501 | com.sun.mail:all |  |  |  |  |  |  | 0 | 0 | 0 | 0 |  |  |  |  |  |
| 502 | org.bouncycastle:bcutil-jdk18on | org.bouncycastle.util 🏷️ | 2026-05-15 | 4,4 | 4,4 | 1.81.1 | 1.81.1 | 18 | 19 | 5 | 6 | 60 | 156,0 | 43,1 🔺 | 2,6 | 🔺 size |
| ~~503~~ | ~~org.apache.maven.surefire:common-java5~~ | ~~-~~ | ~~2026-06-02~~ | ~~13,6~~ | ~~-~~ | ~~3.6.0-M1~~ | ~~-~~ | ~~43~~ | ~~0~~ | ~~4~~ | ~~0~~ | ~~20~~ | ~~418,0~~ | ~~12,9~~ | ~~0,3~~ | ~~-~~ |
| 504 | com.googlecode.javaewah:JavaEWAH 🚩 | com.googlecode.javaewah ✳️ | 2023-03-08 | 14,6 | 3,4 | 1.2.3 | 1.2.3 | 77 | 1 | 0 | 0 |  |  |  |  |  |
| 505 | com.h2database:h2 | com.h2database ⚙️ | 2025-09-24 | 19,6 | 7,5 | 2.4.240 | 2.4.240 | 140 | 15 | 1 | 1 | 2 | 32,0 | 10,4 | 0,1 | - |
| 506 | org.springframework:spring-messaging | spring.messaging ⚙️ | 2026-06-08 | 12,7 | 8,9 | 7.0.8 | 7.0.8 | 256 | 195 | 24 | 24 | 24 | 449,0 | 109,6 🔺 | 2,0 | 🔺 size |
| 507 | com.squareup:javapoet 🚩 | com.squareup.javapoet ⚙️ | 2020-06-18 | 11,5 | 8,5 | 1.13.0 | 1.13.0 | 18 | 6 | 0 | 0 | 9 | 83,5 | 36,4 | 1,3 | - |
| 508 | javax.annotation:jsr250-api 🚩 |  | 2006-11-22 | 19,7 |  | 1.0 |  | 1 | 0 | 0 | 0 |  |  |  |  |  |
| 509 | io.opencensus:opencensus-api 🚩 |  | 2022-04-29 | 9,1 |  | 0.31.1 |  | 47 | 0 | 0 | 0 |  |  |  |  |  |
| 510 | io.swagger.core.v3:swagger-project |  | 2026-06-22 | 0,2 |  | 2.2.52 |  | 2 | 0 | 2 | 0 | 20 | 416,1 | 12,0 | 1,4 | - |
| 511 | org.bouncycastle:bcpkix-jdk18on | org.bouncycastle.pkix 🏷️ | 2026-05-15 | 4,4 | 4,4 | 1.81.1 | 1.81.1 | 18 | 19 | 5 | 6 | 60 | 156,0 | 43,1 🔺 | 2,6 | 🔺 size |
| 512 | org.springframework.cloud:spring-cloud-build |  | 2026-06-11 | 0,2 |  | 5.0.2 |  | 2 | 0 | 2 | 0 | 243 | 1049,0 🔺 | 161,7 🔺 | 3,3 | 🔺 files, size |
| 513 | org.latencyutils:LatencyUtils 🚩 |  | 2015-12-15 | 12,7 |  | 2.0.3 |  | 9 | 0 | 0 | 0 |  |  |  |  |  |
| 514 | io.opentelemetry:opentelemetry-sdk | io.opentelemetry.sdk ⚙️ | 2026-06-05 | 6,7 | 6,4 | 1.63.0 | 1.63.0 | 101 | 102 | 12 | 14 | 33 | 714,2 🔺 | 3,8 | 2,0 | 🔺 files |
| 515 | org.eclipse.angus:angus-activation-project |  |  |  |  |  |  | 0 | 0 | 0 | 0 | 10 | 240,0 | 8,5 | 0,3 | - |
| 516 | org.jacoco:org.jacoco.agent | org.jacoco.agent ⚙️ | 2026-06-04 | 15,1 | 8,6 | 0.8.15 | 0.8.15 | 40 | 16 | 2 | 2 | 6 | 108,0 | 4,2 | 0,2 | - |
| 517 | org.eclipse.jetty:jetty-io | org.eclipse.jetty.io ✳️ | 2026-08-03 | 17,4 | 7,8 | 12.1.12 | 12.1.12 | 437 | 179 | 27 | 29 | 82 | 882,6 🔺 | 77,9 🔺 | 3,3 | 🔺 files, size |
| 518 | io.opentelemetry:opentelemetry-sdk-common | io.opentelemetry.sdk.common ⚙️ | 2026-06-05 | 5,9 | 5,9 | 1.63.0 | 1.63.0 | 91 | 93 | 12 | 14 | 33 | 714,2 🔺 | 3,8 | 2,0 | 🔺 files |
| 519 | org.eclipse.jetty:jetty-http | org.eclipse.jetty.http ✳️ | 2026-08-03 | 17,4 | 7,8 | 12.1.12 | 12.1.12 | 437 | 179 | 27 | 29 | 82 | 882,6 🔺 | 77,9 🔺 | 3,3 | 🔺 files, size |
| ~~520~~ | ~~asm:asm-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| 521 | jakarta.inject:jakarta.inject-api 🚩 | java.inject ✳️ | 2021-10-17 | 7,0 | 6,3 | 1.0.5 | 1.0.5 | 14 | 10 | 0 | 0 |  |  |  |  |  |
| 522 | org.jetbrains.kotlinx:kotlinx-serialization-core | kotlinx.serialization.core.artifact_disambiguating_module ⚙️ | 2026-04-09 | 5,9 | 0,6 | 1.11.0 | 1.11.0 | 37 | 3 | 3 | 3 | 768 | 744,5 🔺 | 91,4 🔺 | 57,3 🔺 | 🔺 files, size, releases |
| 523 | io.opentelemetry:opentelemetry-sdk-trace | io.opentelemetry.sdk.trace ⚙️ | 2026-06-05 | 5,7 | 5,7 | 1.63.0 | 1.63.0 | 87 | 89 | 12 | 14 | 33 | 714,2 🔺 | 3,8 | 2,0 | 🔺 files |
| ~~524~~ | ~~org.jboss.logging:logging-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~2~~ | ~~24,0~~ | ~~0,3~~ | ~~0,3~~ | ~~-~~ |
| 525 | jakarta.transaction:jakarta.transaction-api 🚩 | jakarta.transaction ✳️ | 2022-03-31 | 7,7 | 7,7 | 2.0.1 | 2.0.1 | 9 | 9 | 0 | 0 |  |  |  |  |  |
| 526 | io.opentelemetry:opentelemetry-sdk-metrics | io.opentelemetry.sdk.metrics ⚙️ | 2026-06-05 | 5,9 | 5,9 | 1.63.0 | 1.63.0 | 91 | 93 | 12 | 14 | 33 | 714,2 🔺 | 3,8 | 2,0 | 🔺 files |
| 527 | commons-net:commons-net | org.apache.commons.net ✳️ | 2026-03-15 | 20,8 | 6,0 | 3.13.0 | 3.13.0 | 30 | 10 | 1 | 1 | 1 | 44,0 | 4,1 | 0,1 | - |
| 528 | com.diffplug.spotless:spotless-lib-extra |  | 2026-06-29 | 9,6 |  | 4.8.0 |  | 156 | 0 | 11 | 0 | 4 | 66,7 | 1,1 | 2,8 | - |
| ~~529~~ | ~~org.apache.maven.plugins:maven-antrun-plugin~~ | ~~-~~ | ~~2025-10-17~~ | ~~20,3~~ | ~~-~~ | ~~3.2.0~~ | ~~-~~ | ~~14~~ | ~~0~~ | ~~1~~ | ~~0~~ | ~~35~~ | ~~44,4~~ | ~~2,7~~ | ~~3,1~~ | ~~-~~ |
| 530 | software.amazon.awssdk:annotations | software.amazon.awssdk.annotations ⚙️ | 2026-08-07 | 8,9 | 7,7 | 2.51.3 | 2.51.3 | 1818 | 1808 | 233 | 239 | 520 | 7784,8 🔺 | 3309,0 🔺 | 27,7 🔺 | 🔺 files, size, releases |
| 531 | software.amazon.awssdk:utils | software.amazon.awssdk.utils ⚙️ | 2026-08-07 | 9,1 | 7,7 | 2.51.3 | 2.51.3 | 1819 | 1807 | 233 | 239 | 520 | 7784,8 🔺 | 3309,0 🔺 | 27,7 🔺 | 🔺 files, size, releases |
| 532 | io.opentelemetry:opentelemetry-sdk-logs | io.opentelemetry.sdk.logs ⚙️ | 2026-06-05 | 4,8 | 4,8 | 1.63.0 | 1.63.0 | 67 | 69 | 12 | 14 | 33 | 714,2 🔺 | 3,8 | 2,0 | 🔺 files |
| 533 | software.amazon.awssdk:http-client-spi | software.amazon.awssdk.http ⚙️ | 2026-08-07 | 9,1 | 7,7 | 2.51.3 | 2.51.3 | 1820 | 1808 | 233 | 239 | 520 | 7784,8 🔺 | 3309,0 🔺 | 27,7 🔺 | 🔺 files, size, releases |
| 534 | com.google.auth:google-auth-library-credentials | com.google.auth ⚙️ | 2026-07-07 | 11,4 | 7,0 | 1.49.0 | 1.49.0 | 111 | 90 | 16 | 17 | 4 | 76,5 | 1,7 | 1,4 | - |
| 535 | software.amazon.awssdk:sdk-core | software.amazon.awssdk.core ⚙️ | 2026-08-07 | 8,0 | 7,7 | 2.51.3 | 2.51.3 | 1810 | 1807 | 233 | 239 | 520 | 7784,8 🔺 | 3309,0 🔺 | 27,7 🔺 | 🔺 files, size, releases |
| 536 | software.amazon.awssdk:regions | software.amazon.awssdk.regions ⚙️ | 2026-08-07 | 8,2 | 7,7 | 2.51.3 | 2.51.3 | 1811 | 1807 | 233 | 239 | 520 | 7784,8 🔺 | 3309,0 🔺 | 27,7 🔺 | 🔺 files, size, releases |
| 537 | software.amazon.awssdk:auth | software.amazon.awssdk.auth ⚙️ | 2026-08-07 | 8,2 | 7,7 | 2.51.3 | 2.51.3 | 1812 | 1808 | 233 | 239 | 520 | 7784,8 🔺 | 3309,0 🔺 | 27,7 🔺 | 🔺 files, size, releases |
| 538 | org.vafer:jdependency |  | 2026-05-24 | 16,0 |  | 2.16 |  | 27 | 6 | 3 | 2 | 1 | 26,0 | 0,7 | 0,3 | - |
| 539 | software.amazon.awssdk:profiles | software.amazon.awssdk.profiles ⚙️ | 2026-08-07 | 8,2 | 7,7 | 2.51.3 | 2.51.3 | 1811 | 1807 | 233 | 239 | 520 | 7784,8 🔺 | 3309,0 🔺 | 27,7 🔺 | 🔺 files, size, releases |
| 540 | com.google.devtools.ksp:symbol-processing-api |  | 2026-08-03 | 5,0 |  | 2.3.11 |  | 158 | 0 | 21 | 0 | 8 | 270,0 | 194,4 🔺 | 1,8 | 🔺 size |
| 541 | com.sun.xml.fastinfoset:FastInfoset ⚠️ | com.sun.xml.fastinfoset ✳️ | 2023-10-20 | 20,1 | 8,0 | 2.1.1 | 2.1.1 | 27 | 10 | 0 | 0 |  |  |  |  |  |
| 542 | org.bitbucket.b_c:jose4j ⚠️ | org.jose4j ⚙️ | 2024-03-06 | 12,2 | 6,8 | 0.9.6 | 0.9.6 | 45 | 21 | 0 | 0 |  |  |  |  |  |
| 543 | com.google.auth:google-auth-library-oauth2-http | com.google.auth.oauth2 ⚙️ | 2026-07-30 | 11,4 | 7,0 | 1.50.0 | 1.50.0 | 112 | 90 | 17 | 17 | 4 | 76,5 | 1,7 | 1,4 | - |
| ~~544~~ | ~~org.apache.avro:avro-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~19~~ | ~~432,0~~ | ~~83,4~~ | ~~0,2~~ | ~~-~~ |
| 545 | org.apache.avro:avro-toplevel |  |  |  |  |  |  | 0 | 0 | 0 | 0 | 19 | 432,0 | 83,4 | 0,2 | - |
| ~~546~~ | ~~org.springframework.cloud:spring-cloud-commons-dependencies~~ | ~~-~~ | ~~2026-06-11~~ | ~~0,2~~ | ~~-~~ | ~~5.0.2~~ | ~~-~~ | ~~2~~ | ~~0~~ | ~~2~~ | ~~0~~ | ~~243~~ | ~~1049,0 🔺~~ | ~~161,7 🔺~~ | ~~3,3~~ | ~~🔺 files, size~~ |
| 547 | software.amazon.awssdk:aws-core | software.amazon.awssdk.awscore ⚙️ | 2026-08-07 | 8,2 | 7,7 | 2.51.3 | 2.51.3 | 1812 | 1808 | 233 | 239 | 520 | 7784,8 🔺 | 3309,0 🔺 | 27,7 🔺 | 🔺 files, size, releases |
| 548 | software.amazon.awssdk:metrics-spi | software.amazon.awssdk.metrics ⚙️ | 2026-08-07 | 9,1 | 6,1 | 2.51.3 | 2.51.3 | 1460 | 1464 | 233 | 239 | 520 | 7784,8 🔺 | 3309,0 🔺 | 27,7 🔺 | 🔺 files, size, releases |
| 549 | software.amazon.awssdk:protocol-core | software.amazon.awssdk.protocols.core ⚙️ | 2026-08-07 | 7,7 | 7,7 | 2.51.3 | 2.51.3 | 1801 | 1807 | 233 | 239 | 520 | 7784,8 🔺 | 3309,0 🔺 | 27,7 🔺 | 🔺 files, size, releases |
| 550 | log4j:log4j 🚩 |  | 2007-08-30 | 20,7 |  | 1.2.15 |  | 12 | 0 | 0 | 0 |  |  |  |  |  |
| 551 | com.squareup:kotlinpoet | com.squareup.kotlinpoet ⚙️ | 2026-03-27 | 9,2 | 8,5 | 2.3.0 | 2.3.0 | 50 | 44 | 1 | 1 | 9 | 83,5 | 36,4 | 1,3 | - |
| ~~552~~ | ~~org.springframework.pulsar:spring-pulsar-bom~~ | ~~-~~ | ~~2026-06-09~~ | ~~0,2~~ | ~~-~~ | ~~2.0.6~~ | ~~-~~ | ~~2~~ | ~~0~~ | ~~2~~ | ~~0~~ | ~~6~~ | ~~96,8~~ | ~~9,6~~ | ~~1,7~~ | ~~-~~ |
| ~~553~~ | ~~org.codehaus.plexus:plexus-languages~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~21~~ | ~~45,4~~ | ~~0,8~~ | ~~2,7~~ | ~~-~~ |
| 554 | io.swagger:swagger-project |  |  |  |  |  |  | 0 | 0 | 0 | 0 | 8 | 63,7 | 28,6 | 1,1 | - |
| ~~555~~ | ~~org.codehaus.plexus:plexus-compiler~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~21~~ | ~~45,4~~ | ~~0,8~~ | ~~2,7~~ | ~~-~~ |
| 556 | org.springframework.boot:spring-boot-maven-plugin | spring.boot.maven.plugin ⚙️ | 2026-06-25 | 12,4 | 8,5 | 3.5.16 | 3.5.16 | 290 | 225 | 35 | 35 | 336 | 3788,6 🔺 | 132,2 🔺 | 2,9 | 🔺 files, size |
| 557 | org.springframework.boot:spring-boot-starter-security | spring.boot.starter.security ⚙️ | 2026-06-25 | 12,4 | 8,5 | 3.5.16 | 3.5.16 | 290 | 225 | 35 | 35 | 336 | 3788,6 🔺 | 132,2 🔺 | 2,9 | 🔺 files, size |
| 558 | org.jetbrains.kotlinx:kotlinx-serialization-json | kotlinx.serialization.json.artifact_disambiguating_module ⚙️ | 2026-04-09 | 5,9 | 0,6 | 1.11.0 | 1.11.0 | 36 | 3 | 3 | 3 | 768 | 744,5 🔺 | 91,4 🔺 | 57,3 🔺 | 🔺 files, size, releases |
| 559 | com.google.http-client:google-http-client-gson | com.google.api.client.json.gson ⚙️ | 2026-07-20 | 14,0 | 6,9 | 2.2.0 | 2.2.0 | 81 | 54 | 8 | 8 | 11 | 220,0 | 3,2 | 0,7 | - |
| ~~560~~ | ~~com.squareup.okio:okio-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~32~~ | ~~1535,4~~ | ~~13,1~~ | ~~0,6~~ | ~~-~~ |
| ~~561~~ | ~~org.apache.maven.shared:maven-dependency-analyzer~~ | ~~-~~ | ~~2026-05-12~~ | ~~19,4~~ | ~~-~~ | ~~1.17.1~~ | ~~-~~ | ~~28~~ | ~~0~~ | ~~2~~ | ~~0~~ | ~~4~~ | ~~29,0~~ | ~~0,6~~ | ~~0,5~~ | ~~-~~ |
| 562 | javax.validation:validation-api 🚩 | java.validation ⚙️ | 2017-12-19 | 17,5 | 9,1 | 2.0.1.Final | 2.0.1.Final | 26 | 4 | 0 | 0 |  |  |  |  |  |
| 563 | com.github.stephenc.jcip:jcip-annotations 🚩 |  | 2013-02-13 | 13,5 |  | 1.0-1 |  | 1 | 0 | 0 | 0 |  |  |  |  |  |
| 564 | com.sun.xml.fastinfoset:fastinfoset-project |  |  |  |  |  |  | 0 | 0 | 0 | 0 |  |  |  |  |  |
| ~~565~~ | ~~com.google.auth:google-auth-library-bom~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~4~~ | ~~76,5~~ | ~~1,7~~ | ~~1,4~~ | ~~-~~ |
| 566 | software.amazon.awssdk:json-utils | software.amazon.awssdk.protocols.jsoncore ⚙️ | 2026-08-07 | 5,1 | 5,1 | 2.51.3 | 2.51.3 | 1216 | 1222 | 233 | 239 | 520 | 7784,8 🔺 | 3309,0 🔺 | 27,7 🔺 | 🔺 files, size, releases |
| 567 | com.beust:jcommander 🚩 |  | 2022-01-11 | 16,1 |  | 1.82 |  | 37 | 1 | 0 | 0 |  |  |  |  |  |
| 568 | org.glassfish.jaxb:jaxb-core | org.glassfish.jaxb.core ✳️ | 2026-05-28 | 12,4 | 6,3 | 4.0.9 | 4.0.9 | 31 | 24 | 4 | 4 | 27 | 399,0 | 16,2 | 0,3 | - |
| 569 | org.eclipse.angus:angus-activation | org.eclipse.angus.activation ✳️ | 2025-09-11 | 5,0 | 5,0 | 2.0.3 | 2.0.3 | 9 | 9 | 2 | 2 | 10 | 240,0 | 8,5 | 0,3 | - |
| 570 | software.amazon.awssdk:apache-client | software.amazon.awssdk.http.apache ⚙️ | 2026-08-07 | 9,1 | 7,7 | 2.51.3 | 2.51.3 | 1819 | 1808 | 233 | 239 | 520 | 7784,8 🔺 | 3309,0 🔺 | 27,7 🔺 | 🔺 files, size, releases |
| 571 | software.amazon.awssdk:third-party-jackson-core | software.amazon.awssdk.thirdparty.jackson.core ⚙️ | 2026-08-07 | 5,1 | 5,1 | 2.51.3 | 2.51.3 | 1221 | 1221 | 239 | 239 | 520 | 7784,8 🔺 | 3309,0 🔺 | 27,7 🔺 | 🔺 files, size, releases |
| 572 | com.typesafe:config | typesafe.config ⚙️ | 2026-06-03 | 14,3 | 8,5 | 1.4.9 | 1.4.9 | 32 | 13 | 5 | 5 | 15 | 108,0 | 11,1 | 1,2 | - |
| 573 | javax.activation:javax.activation-api 🚩 | java.activation ⚙️ | 2017-09-06 | 8,9 | 8,9 | 1.2.0 | 1.2.0 | 1 | 1 | 0 | 0 |  |  |  |  |  |
| 574 | org.glassfish.jersey:project |  | 2026-06-11 | 0,2 |  | 3.1.12 |  | 1 | 0 | 1 | 0 | 2 | 24,0 | 0,1 | 0,1 | - |
| 575 | software.amazon.awssdk:netty-nio-client | software.amazon.awssdk.http.nio.netty ⚙️ | 2026-08-07 | 9,1 | 7,7 | 2.51.3 | 2.51.3 | 1819 | 1808 | 233 | 239 | 520 | 7784,8 🔺 | 3309,0 🔺 | 27,7 🔺 | 🔺 files, size, releases |
| 576 | com.google.crypto.tink:tink | com.google.crypto.tink ⚙️ | 2026-06-18 | 8,9 | 4,0 | 1.22.0 | 1.22.0 | 37 | 18 | 4 | 5 | 7 | 45,3 | 11,1 | 0,8 | - |
| 577 | io.opentelemetry:opentelemetry-sdk-extension-autoconfigure-spi | io.opentelemetry.sdk.autoconfigure.spi ⚙️ | 2026-06-05 | 4,9 | 4,9 | 1.63.0 | 1.63.0 | 70 | 72 | 12 | 14 | 33 | 714,2 🔺 | 3,8 | 2,0 | 🔺 files |
| ~~578~~ | ~~com.thoughtworks.xstream:xstream-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| 579 | io.opencensus:opencensus-contrib-http-util 🚩 |  | 2022-04-29 | 8,6 |  | 0.31.1 |  | 37 | 0 | 0 | 0 |  |  |  |  |  |
| 580 | com.fasterxml.jackson.datatype:jackson-datatype-joda | com.fasterxml.jackson.datatype.joda ✳️ | 2026-07-08 | 14,4 | 8,8 | 2.18.9 | 2.18.9 | 180 | 95 | 18 | 18 | 23 | 690,3 | 6,4 | 1,5 | - |
| 581 | org.apache.avro:avro | org.apache.avro ⚙️ | 2025-09-11 | 15,9 | 7,0 | 1.11.5 | 1.11.5 | 36 | 13 | 2 | 2 | 19 | 432,0 | 83,4 | 0,2 | - |
| 582 | com.google.dagger:dagger | dagger ⚙️ | 2026-07-06 | 11,3 | 5,2 | 2.60.1 | 2.60.1 | 108 | 46 | 8 | 8 | 14 | 224,0 | 8,7 | 0,7 | - |
| 583 | org.jetbrains.kotlin:kotlin-build-statistics |  | 2026-06-25 | 2,7 |  | 2.4.10-RC |  | 78 | 0 | 32 | 0 | 175 | 3924,5 🔺 | 568,3 🔺 | 3,2 | 🔺 files, size |
| ~~584~~ | ~~org.springframework.cloud:spring-cloud-function-dependencies~~ | ~~-~~ | ~~2026-06-11~~ | ~~0,2~~ | ~~-~~ | ~~5.0.3~~ | ~~-~~ | ~~2~~ | ~~0~~ | ~~2~~ | ~~0~~ | ~~243~~ | ~~1049,0 🔺~~ | ~~161,7 🔺~~ | ~~3,3~~ | ~~🔺 files, size~~ |
| 585 | com.fasterxml.jackson.datatype:jackson-datatype-guava | com.fasterxml.jackson.datatype.guava ✳️ | 2026-07-08 | 14,5 | 8,8 | 2.18.9 | 2.18.9 | 178 | 95 | 18 | 18 | 23 | 690,3 | 6,4 | 1,5 | - |
| 586 | asm:asm 🚩 |  | 2011-01-12 | 21,0 |  | 3.3.1 |  | 23 | 0 | 0 | 0 |  |  |  |  |  |
| 587 | org.eclipse.jetty:jetty-server | org.eclipse.jetty.server ✳️ | 2026-08-03 | 17,4 | 7,8 | 12.1.12 | 12.1.12 | 439 | 179 | 29 | 29 | 82 | 882,6 🔺 | 77,9 🔺 | 3,3 | 🔺 files, size |
| 588 | javax.activation:activation 🚩 |  | 2009-10-23 | 20,3 |  | 1.1.1 |  | 3 | 0 | 0 | 0 |  |  |  |  |  |
| 589 | org.jacoco:org.jacoco.core | org.jacoco.core ⚙️ | 2026-06-04 | 15,1 | 8,6 | 0.8.15 | 0.8.15 | 40 | 16 | 2 | 2 | 6 | 108,0 | 4,2 | 0,2 | - |
| 590 | org.codehaus.groovy:groovy ⚠️ | org.codehaus.groovy ⚙️ | 2025-05-27 | 19,1 | 8,9 | 3.0.25 | 3.0.25 | 221 | 69 | 0 | 0 |  |  |  |  |  |
| 591 | xerces:xercesImpl 🚩 |  | 2022-01-27 | 21,0 |  | 2.12.2 |  | 20 | 0 | 0 | 0 |  |  |  |  |  |
| 592 | org.checkerframework:checker-compat-qual ⚠️ | org.checkerframework.checker.qual ⚙️ | 2023-11-02 | 11,9 | 8,0 | 2.5.6 | 2.5.6 | 51 | 2 | 0 | 0 | 11 | 402,7 | 53,6 🔺 | 1,5 | 🔺 size |
| ~~593~~ | ~~org.apache.maven.shared:maven-shared-io 🚩~~ | ~~-~~ | ~~2015-12-20~~ | ~~19,7~~ | ~~-~~ | ~~3.0.0~~ | ~~-~~ | ~~3~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~4~~ | ~~29,0~~ | ~~0,6~~ | ~~0,5~~ | ~~-~~ |
| 594 | org.jetbrains.kotlinx:kotlinx-coroutines-play-services |  | 2026-05-07 | 7,9 |  | 1.11.0 |  | 89 | 0 | 3 | 0 | 768 | 744,5 🔺 | 91,4 🔺 | 57,3 🔺 | 🔺 files, size, releases |
| 595 | com.amazonaws:aws-java-sdk-core |  | 2025-12-29 | 11,9 |  | 1.12.797 |  | 1961 | 0 | 9 | 0 | 426 | 862,8 🔺 | 702,0 🔺 | 6,1 | 🔺 files, size |
| ~~596~~ | ~~io.netty:netty-tcnative-parent~~ | ~~-~~ | ~~2026-06-29~~ | ~~0,2~~ | ~~-~~ | ~~2.0.80.Final~~ | ~~-~~ | ~~3~~ | ~~0~~ | ~~3~~ | ~~0~~ | ~~66~~ | ~~1150,9 🔺~~ | ~~47,5 🔺~~ | ~~3,2~~ | ~~🔺 files, size~~ |
| 597 | org.springdoc:springdoc-openapi |  |  |  |  |  |  | 0 | 0 | 0 | 0 | 22 | 193,2 | 3,6 | 1,3 | - |
| 598 | com.squareup.okhttp3:parent |  |  |  |  |  |  | 0 | 0 | 0 | 0 | 18 | 828,8 | 9,3 | 0,7 | - |
| 599 | com.sun.xml.bind:jaxb-impl | com.sun.xml.bind ✳️ | 2026-05-28 | 20,2 | 8,1 | 4.0.9 | 4.0.9 | 181 | 37 | 4 | 4 | 8 | 140,5 | 31,9 | 0,3 | - |
| ~~600~~ | ~~org.apache.maven.doxia:doxia-skin-model~~ | ~~-~~ | ~~2026-03-31~~ | ~~10,5~~ | ~~-~~ | ~~2.1.0~~ | ~~-~~ | ~~30~~ | ~~0~~ | ~~1~~ | ~~0~~ | ~~13~~ | ~~312,0~~ | ~~7,8~~ | ~~0,1~~ | ~~-~~ |
| ~~601~~ | ~~com.github.docker-java:docker-java-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~9~~ | ~~270,0~~ | ~~10,0~~ | ~~0,3~~ | ~~-~~ |
| 602 | javax.servlet:javax.servlet-api 🚩 |  | 2018-04-20 | 15,1 |  | 4.0.1 |  | 20 | 0 | 0 | 0 |  |  |  |  |  |
| ~~603~~ | ~~org.eclipse.aether:aether-impl 🚩~~ | ~~-~~ | ~~2016-02-03~~ | ~~13,9~~ | ~~-~~ | ~~1.1.0~~ | ~~-~~ | ~~10~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| 604 | org.jetbrains.kotlin:kotlin-build-common ⚠️ |  | 2025-01-26 | 10,3 |  | 2.1.10 |  | 145 | 0 | 0 | 0 | 175 | 3924,5 🔺 | 568,3 🔺 | 3,2 | 🔺 files, size |
| 605 | software.amazon.awssdk:endpoints-spi | software.amazon.awssdk.endpoints ⚙️ | 2026-08-07 | 3,8 | 3,8 | 2.51.3 | 2.51.3 | 920 | 926 | 233 | 239 | 520 | 7784,8 🔺 | 3309,0 🔺 | 27,7 🔺 | 🔺 files, size, releases |
| 606 | io.airlift:airbase |  | 2026-07-06 | 0,2 |  | 393 |  | 18 | 0 | 18 | 0 | 48 | 575,8 🔺 | 56,2 🔺 | 14,5 🔺 | 🔺 files, size, releases |
| 607 | jakarta.persistence:jakarta.persistence-api | jakarta.persistence ✳️ | 2026-07-03 | 7,8 | 7,8 | 4.0.0-M6 | 4.0.0-M6 | 20 | 20 | 6 | 6 | 5 | 172,0 | 41,0 | 0,5 | - |
| ~~608~~ | ~~org.eclipse.aether:aether-spi 🚩~~ | ~~-~~ | ~~2016-02-03~~ | ~~13,9~~ | ~~-~~ | ~~1.1.0~~ | ~~-~~ | ~~10~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| 609 | com.google.re2j:re2j ⚠️ |  | 2025-01-09 | 11,5 |  | 1.8 |  | 9 | 0 | 0 | 0 |  |  |  |  |  |
| 610 | org.apache.geronimo.genesis:genesis-default-flava |  |  |  |  |  |  | 0 | 0 | 0 | 0 |  |  |  |  |  |
| ~~611~~ | ~~org.apache.maven.doxia:doxia-module-xdoc~~ | ~~-~~ | ~~2026-03-17~~ | ~~18,9~~ | ~~-~~ | ~~2.1.0~~ | ~~-~~ | ~~35~~ | ~~0~~ | ~~1~~ | ~~0~~ | ~~13~~ | ~~312,0~~ | ~~7,8~~ | ~~0,1~~ | ~~-~~ |
| 612 | io.dropwizard.metrics:metrics-core | com.codahale.metrics ⚙️ | 2026-05-25 | 11,9 | 8,4 | 4.2.39 | 4.2.39 | 114 | 87 | 6 | 6 | 43 | 948,0 | 6,7 | 0,5 | - |
| 613 | io.netty:netty-resolver-dns-classes-macos | io.netty.resolver.dns.classes.macos ⚙️ | 2026-06-02 | 4,8 | 4,8 | 4.1.135.Final | 4.1.135.Final | 92 | 96 | 24 | 28 | 66 | 1150,9 🔺 | 47,5 🔺 | 3,2 | 🔺 files, size |
| ~~614~~ | ~~org.springframework.cloud:spring-cloud-build-dependencies~~ | ~~-~~ | ~~2026-06-11~~ | ~~0,2~~ | ~~-~~ | ~~5.0.2~~ | ~~-~~ | ~~2~~ | ~~0~~ | ~~2~~ | ~~0~~ | ~~243~~ | ~~1049,0 🔺~~ | ~~161,7 🔺~~ | ~~3,3~~ | ~~🔺 files, size~~ |
| 615 | org.sonarsource.parent:parent |  |  |  |  |  |  | 0 | 0 | 0 | 0 |  |  |  |  |  |
| 616 | com.thoughtworks.xstream:xstream ⚠️ |  | 2024-11-07 | 20,0 |  | 1.4.21 |  | 46 | 0 | 0 | 0 |  |  |  |  |  |
| ~~617~~ | ~~org.springframework.cloud:spring-cloud-stream-dependencies~~ | ~~-~~ | ~~2026-06-11~~ | ~~0,2~~ | ~~-~~ | ~~5.0.2~~ | ~~-~~ | ~~2~~ | ~~0~~ | ~~2~~ | ~~0~~ | ~~243~~ | ~~1049,0 🔺~~ | ~~161,7 🔺~~ | ~~3,3~~ | ~~🔺 files, size~~ |
| 618 | org.apache.commons:commons-exec | org.apache.commons.exec ✳️ | 2025-11-27 | 17,4 | 2,6 | 1.6.0 | 1.6.0 | 8 | 3 | 1 | 1 | 36 | 52,4 | 3,2 | 3,3 | - |
| ~~619~~ | ~~com.google.guava:guava-bom~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~3~~ | ~~75,0~~ | ~~22,6~~ | ~~0,3~~ | ~~-~~ |
| 620 | com.jcraft:jsch 🚩 |  | 2018-11-26 | 20,8 |  | 0.1.55 |  | 24 | 0 | 0 | 0 |  |  |  |  |  |
| 621 | io.swagger.core.v3:swagger-annotations | io.swagger.v3.oas.annotations ⚙️ | 2026-06-22 | 8,7 | 7,3 | 2.2.52 | 2.2.52 | 80 | 71 | 17 | 18 | 20 | 416,1 | 12,0 | 1,4 | - |
| 622 | org.jacoco:org.jacoco.report | org.jacoco.report ⚙️ | 2026-06-04 | 15,1 | 8,6 | 0.8.15 | 0.8.15 | 40 | 16 | 2 | 2 | 6 | 108,0 | 4,2 | 0,2 | - |
| ~~623~~ | ~~org.flywaydb:flyway-parent~~ | ~~-~~ | ~~2026-08-06~~ | ~~0,2~~ | ~~-~~ | ~~13.2.0~~ | ~~-~~ | ~~7~~ | ~~0~~ | ~~7~~ | ~~0~~ | ~~49~~ | ~~905,7 🔺~~ | ~~8,9~~ | ~~3,3~~ | ~~🔺 files~~ |
| 624 | org.eclipse.jetty:jetty-security | org.eclipse.jetty.security ✳️ | 2026-08-03 | 17,4 | 7,8 | 12.1.12 | 12.1.12 | 432 | 174 | 27 | 29 | 82 | 882,6 🔺 | 77,9 🔺 | 3,3 | 🔺 files, size |
| 625 | org.springframework.data:spring-data-jpa | spring.data.jpa ⚙️ | 2026-06-24 | 14,5 | 8,9 | 3.5.13 | 3.5.13 | 298 | 220 | 31 | 31 | 17 | 68,0 | 17,9 🔺 | 10,3 🔺 | 🔺 size, releases |
| ~~626~~ | ~~com.google.oauth-client:google-oauth-client-bom~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| 627 | org.slf4j:slf4j-simple | org.slf4j.simple ✳️ | 2026-05-12 | 21,0 | 9,3 | 2.0.18 | 2.0.18 | 116 | 48 | 1 | 1 | 12 | 294,0 | 3,0 | 0,1 | - |
| ~~628~~ | ~~org.springframework.cloud:spring-cloud-dependencies~~ | ~~-~~ | ~~2026-06-11~~ | ~~0,2~~ | ~~-~~ | ~~2025.1.2~~ | ~~-~~ | ~~2~~ | ~~0~~ | ~~2~~ | ~~0~~ | ~~243~~ | ~~1049,0 🔺~~ | ~~161,7 🔺~~ | ~~3,3~~ | ~~🔺 files, size~~ |
| 629 | com.google.flatbuffers:flatbuffers-java ⚠️ |  | 2025-02-11 | 8,8 |  | 25.2.10 |  | 29 | 0 | 0 | 0 |  |  |  |  |  |
| 630 | com.amazonaws:jmespath-java |  | 2025-12-29 | 9,7 |  | 1.12.797 |  | 1776 | 0 | 9 | 0 | 426 | 862,8 🔺 | 702,0 🔺 | 6,1 | 🔺 files, size |
| ~~631~~ | ~~jakarta.annotation:ca-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| 632 | com.squareup:javawriter 🚩 |  | 2014-12-03 | 13,5 |  | 2.5.1 |  | 18 | 0 | 0 | 0 | 9 | 83,5 | 36,4 | 1,3 | - |
| ~~633~~ | ~~com.squareup.okhttp3:okhttp-bom~~ | ~~-~~ | ~~2026-06-08~~ | ~~0,2~~ | ~~-~~ | ~~5.4.0~~ | ~~-~~ | ~~1~~ | ~~0~~ | ~~1~~ | ~~0~~ | ~~18~~ | ~~828,8~~ | ~~9,3~~ | ~~0,7~~ | ~~-~~ |
| ~~634~~ | ~~org.apache.maven.plugins:maven-shade-plugin~~ | ~~-~~ | ~~2026-03-02~~ | ~~18,7~~ | ~~-~~ | ~~3.6.2~~ | ~~-~~ | ~~44~~ | ~~0~~ | ~~2~~ | ~~0~~ | ~~35~~ | ~~44,4~~ | ~~2,7~~ | ~~3,1~~ | ~~-~~ |
| ~~635~~ | ~~org.springframework.cloud:spring-cloud-netflix-dependencies~~ | ~~-~~ | ~~2026-06-11~~ | ~~0,2~~ | ~~-~~ | ~~5.0.2~~ | ~~-~~ | ~~2~~ | ~~0~~ | ~~2~~ | ~~0~~ | ~~243~~ | ~~1049,0 🔺~~ | ~~161,7 🔺~~ | ~~3,3~~ | ~~🔺 files, size~~ |
| 636 | org.springframework:spring-webflux | spring.webflux ⚙️ | 2026-06-08 | 8,9 | 8,9 | 7.0.8 | 7.0.8 | 195 | 195 | 24 | 24 | 24 | 449,0 | 109,6 🔺 | 2,0 | 🔺 size |
| 637 | software.amazon.awssdk:aws-query-protocol | software.amazon.awssdk.protocols.query ⚙️ | 2026-08-07 | 7,7 | 7,7 | 2.51.3 | 2.51.3 | 1802 | 1808 | 233 | 239 | 520 | 7784,8 🔺 | 3309,0 🔺 | 27,7 🔺 | 🔺 files, size, releases |
| ~~638~~ | ~~org.apache.maven.doxia:doxia-integration-tools~~ | ~~-~~ | ~~2026-03-31~~ | ~~13,9~~ | ~~-~~ | ~~2.1.0~~ | ~~-~~ | ~~32~~ | ~~0~~ | ~~1~~ | ~~0~~ | ~~13~~ | ~~312,0~~ | ~~7,8~~ | ~~0,1~~ | ~~-~~ |
| ~~639~~ | ~~org.apache.maven.doxia:doxia-module-fml~~ | ~~-~~ | ~~2026-03-17~~ | ~~18,9~~ | ~~-~~ | ~~2.1.0~~ | ~~-~~ | ~~35~~ | ~~0~~ | ~~1~~ | ~~0~~ | ~~13~~ | ~~312,0~~ | ~~7,8~~ | ~~0,1~~ | ~~-~~ |
| ~~640~~ | ~~org.apache.maven.surefire:surefire-junit-platform~~ | ~~-~~ | ~~2026-06-02~~ | ~~8,2~~ | ~~-~~ | ~~3.6.0-M1~~ | ~~-~~ | ~~30~~ | ~~0~~ | ~~4~~ | ~~0~~ | ~~20~~ | ~~418,0~~ | ~~12,9~~ | ~~0,3~~ | ~~-~~ |
| ~~641~~ | ~~org.springframework.cloud:spring-cloud-contract-dependencies~~ | ~~-~~ | ~~2026-06-11~~ | ~~0,2~~ | ~~-~~ | ~~5.0.3~~ | ~~-~~ | ~~2~~ | ~~0~~ | ~~2~~ | ~~0~~ | ~~243~~ | ~~1049,0 🔺~~ | ~~161,7 🔺~~ | ~~3,3~~ | ~~🔺 files, size~~ |
| ~~642~~ | ~~org.apache.maven.doxia:doxia-module-xhtml5~~ | ~~-~~ | ~~2026-03-17~~ | ~~7,2~~ | ~~-~~ | ~~2.1.0~~ | ~~-~~ | ~~19~~ | ~~0~~ | ~~1~~ | ~~0~~ | ~~13~~ | ~~312,0~~ | ~~7,8~~ | ~~0,1~~ | ~~-~~ |
| ~~643~~ | ~~org.springframework.cloud:spring-cloud-config-dependencies~~ | ~~-~~ | ~~2026-06-11~~ | ~~0,2~~ | ~~-~~ | ~~5.0.4~~ | ~~-~~ | ~~2~~ | ~~0~~ | ~~2~~ | ~~0~~ | ~~243~~ | ~~1049,0 🔺~~ | ~~161,7 🔺~~ | ~~3,3~~ | ~~🔺 files, size~~ |
| 644 | com.diffplug.durian:durian-swt.os |  | 2025-08-28 | 8,0 |  | 5.2.0 |  | 24 | 0 | 2 | 0 | 8 | 400,0 | 1,5 | 0,2 | - |
| 645 | io.swagger:swagger-annotations ⚠️ | io.swagger.annotations ⚙️ | 2025-05-15 | 11,2 | 3,4 | 1.6.16 | 1.6.16 | 45 | 7 | 0 | 0 | 8 | 63,7 | 28,6 | 1,1 | - |
| 646 | com.squareup.moshi:moshi ⚠️ | com.squareup.moshi ⚙️ | 2024-12-05 | 11,2 | 8,3 | 1.15.2 | 1.15.2 | 24 | 12 | 0 | 0 |  |  |  |  |  |
| ~~647~~ | ~~com.thoughtworks.paranamer:paranamer-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| 648 | xmlpull:xmlpull 🚩 |  | 2010-10-26 | 23,2 |  | 1.1.3.4d_b4_min |  | 4 | 0 | 0 | 0 |  |  |  |  |  |
| 649 | org.threeten:threetenbp | org.threeten.bp ⚙️ | 2026-07-26 | 13,6 | 8,0 | 1.7.4 | 1.7.4 | 42 | 26 | 3 | 3 | 2 | 34,8 | 3,3 | 0,4 | - |
| 650 | com.github.docker-java:docker-java-api | com.github.dockerjava.api ⚙️ | 2026-03-18 | 6,9 | 3,7 | 3.7.1 | 3.7.1 | 38 | 18 | 3 | 3 | 9 | 270,0 | 10,0 | 0,3 | - |
| 651 | mysql:mysql-connector-java 🚩 |  | 2022-07-01 | 11,3 |  | 8.0.30 |  | 89 | 0 | 0 | 0 |  |  |  |  |  |
| 652 | software.amazon.eventstream:eventstream 🚩 | software.amazon.eventstream ⚙️ | 2019-05-02 | 7,3 | 7,3 | 1.0.1 | 1.0.1 | 2 | 2 | 0 | 0 |  |  |  |  |  |
| ~~653~~ | ~~org.springframework.cloud:spring-cloud-gateway-dependencies~~ | ~~-~~ | ~~2026-06-11~~ | ~~0,2~~ | ~~-~~ | ~~5.0.2~~ | ~~-~~ | ~~2~~ | ~~0~~ | ~~2~~ | ~~0~~ | ~~243~~ | ~~1049,0 🔺~~ | ~~161,7 🔺~~ | ~~3,3~~ | ~~🔺 files, size~~ |
| ~~654~~ | ~~org.apache.maven.doxia:doxia-module-apt~~ | ~~-~~ | ~~2026-03-17~~ | ~~18,9~~ | ~~-~~ | ~~2.1.0~~ | ~~-~~ | ~~35~~ | ~~0~~ | ~~1~~ | ~~0~~ | ~~13~~ | ~~312,0~~ | ~~7,8~~ | ~~0,1~~ | ~~-~~ |
| ~~655~~ | ~~org.springframework.cloud:spring-cloud-openfeign-dependencies~~ | ~~-~~ | ~~2026-06-11~~ | ~~0,2~~ | ~~-~~ | ~~5.0.2~~ | ~~-~~ | ~~2~~ | ~~0~~ | ~~2~~ | ~~0~~ | ~~243~~ | ~~1049,0 🔺~~ | ~~161,7 🔺~~ | ~~3,3~~ | ~~🔺 files, size~~ |
| 656 | com.github.docker-java:docker-java-transport | com.github.dockerjava.transport ⚙️ | 2026-03-18 | 6,2 | 3,7 | 3.7.1 | 3.7.1 | 30 | 18 | 3 | 3 | 9 | 270,0 | 10,0 | 0,3 | - |
| ~~657~~ | ~~org.springframework.cloud:spring-cloud-consul-dependencies~~ | ~~-~~ | ~~2026-06-11~~ | ~~0,2~~ | ~~-~~ | ~~5.0.2~~ | ~~-~~ | ~~2~~ | ~~0~~ | ~~2~~ | ~~0~~ | ~~243~~ | ~~1049,0 🔺~~ | ~~161,7 🔺~~ | ~~3,3~~ | ~~🔺 files, size~~ |
| 658 | io.github.classgraph:classgraph | io.github.classgraph 🏷️ | 2025-10-10 | 8,0 | 8,0 | 4.8.184 | 4.8.184 | 260 | 259 | 3 | 8 | 1 | 24,0 | 10,3 | 0,3 | - |
| ~~659~~ | ~~org.springframework.cloud:spring-cloud-zookeeper-dependencies~~ | ~~-~~ | ~~2026-06-11~~ | ~~0,2~~ | ~~-~~ | ~~5.0.2~~ | ~~-~~ | ~~2~~ | ~~0~~ | ~~2~~ | ~~0~~ | ~~243~~ | ~~1049,0 🔺~~ | ~~161,7 🔺~~ | ~~3,3~~ | ~~🔺 files, size~~ |
| ~~660~~ | ~~com.google.auto:auto-parent 🚩~~ | ~~-~~ | ~~2017-11-30~~ | ~~11,3~~ | ~~-~~ | ~~5~~ | ~~-~~ | ~~3~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| ~~661~~ | ~~org.sonatype.buildsupport:buildsupport~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~1~~ | ~~24,0~~ | ~~0,1~~ | ~~0,2~~ | ~~-~~ |
| 662 | com.sun.jersey:jersey-project |  |  |  |  |  |  | 0 | 0 | 0 | 0 |  |  |  |  |  |
| 663 | com.github.spotbugs:spotbugs-annotations | com.github.spotbugs.annotations ⚙️ | 2026-06-09 | 9,5 | 8,9 | 4.10.2 | 4.10.2 | 74 | 70 | 6 | 7 | 9 | 78,6 | 8,2 | 3,4 | - |
| 664 | io.netty:netty-tcnative-classes | io.netty.tcnative.classes.openssl ✳️ | 2026-06-29 | 4,7 | 4,7 | 2.0.80.Final | 2.0.80.Final | 35 | 36 | 8 | 9 | 66 | 1150,9 🔺 | 47,5 🔺 | 3,2 | 🔺 files, size |
| ~~665~~ | ~~org.springframework.cloud:spring-cloud-task-dependencies~~ | ~~-~~ | ~~2026-06-11~~ | ~~0,2~~ | ~~-~~ | ~~5.0.2~~ | ~~-~~ | ~~2~~ | ~~0~~ | ~~2~~ | ~~0~~ | ~~243~~ | ~~1049,0 🔺~~ | ~~161,7 🔺~~ | ~~3,3~~ | ~~🔺 files, size~~ |
| 666 | org.scala-lang.modules:scala-xml_2.12 ⚠️ | scala.xml ⚙️ | 2025-05-27 | 9,8 | 5,2 | 2.4.0 | 2.4.0 | 20 | 7 | 0 | 0 | 14 | 112,0 | 8,3 | 0,3 | - |
| 667 | com.fasterxml.jackson.jaxrs:jackson-jaxrs-json-provider | com.fasterxml.jackson.jaxrs.json ✳️ | 2026-07-08 | 14,4 | 8,8 | 2.18.9 | 2.18.9 | 183 | 95 | 18 | 18 | 7 | 244,0 | 1,1 | 1,5 | - |
| 668 | com.sun.activation:jakarta.activation 🚩 | jakarta.activation ✳️ | 2021-02-12 | 7,7 | 7,7 | 2.0.1 | 2.0.1 | 7 | 7 | 0 | 0 |  |  |  |  |  |
| 669 | org.apache.groovy:groovy | org.apache.groovy ⚙️ | 2026-08-01 | 5,8 | 5,8 | 6.0.0-beta-1 | 6.0.0-beta-1 | 68 | 68 | 17 | 17 | 43 | 2259,4 🔺 | 106,3 🔺 | 1,4 | 🔺 files, size |
| ~~670~~ | ~~org.sonatype.buildsupport:public-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~1~~ | ~~24,0~~ | ~~0,1~~ | ~~0,2~~ | ~~-~~ |
| 671 | antlr:antlr 🚩 |  | 2007-01-13 | 21,0 |  | 2.7.7 |  | 7 | 0 | 0 | 0 |  |  |  |  |  |
| ~~672~~ | ~~com.googlecode.libphonenumber:libphonenumber-parent~~ | ~~-~~ | ~~2026-07-02~~ | ~~0,2~~ | ~~-~~ | ~~9.0.34~~ | ~~-~~ | ~~4~~ | ~~0~~ | ~~4~~ | ~~0~~ | ~~6~~ | ~~42,0~~ | ~~11,8~~ | ~~5,9~~ | ~~-~~ |
| 673 | org.apache.velocity:velocity-engine-core ⚠️ |  | 2024-10-14 | 9,1 |  | 2.4.1 |  | 6 | 0 | 0 | 0 |  |  |  |  |  |
| ~~674~~ | ~~org.springframework.cloud:spring-cloud-vault-dependencies~~ | ~~-~~ | ~~2026-06-11~~ | ~~0,2~~ | ~~-~~ | ~~5.0.2~~ | ~~-~~ | ~~2~~ | ~~0~~ | ~~2~~ | ~~0~~ | ~~243~~ | ~~1049,0 🔺~~ | ~~161,7 🔺~~ | ~~3,3~~ | ~~🔺 files, size~~ |
| 675 | dom4j:dom4j 🚩 |  | 2005-09-19 | 21,0 |  | 20040902.021138 |  | 18 | 0 | 0 | 0 |  |  |  |  |  |
| ~~676~~ | ~~com.google.auth:google-auth-library-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~4~~ | ~~76,5~~ | ~~1,7~~ | ~~1,4~~ | ~~-~~ |
| 677 | com.fasterxml.jackson.jaxrs:jackson-jaxrs-base | com.fasterxml.jackson.jaxrs.base ✳️ | 2026-07-08 | 13,3 | 7,1 | 2.18.9 | 2.18.9 | 170 | 87 | 18 | 18 | 7 | 244,0 | 1,1 | 1,5 | - |
| 678 | org.jetbrains.kotlinx:kotlinx-coroutines-test-jvm | kotlinx.coroutines.test 🏷️ | 2026-05-07 | 4,7 | 3,4 | 1.11.0 | 1.11.0 | 31 | 20 | 3 | 3 | 768 | 744,5 🔺 | 91,4 🔺 | 57,3 🔺 | 🔺 files, size, releases |
| 679 | io.netty:netty-transport-classes-kqueue | io.netty.transport.classes.kqueue ⚙️ | 2026-06-02 | 4,8 | 4,8 | 4.1.135.Final | 4.1.135.Final | 92 | 96 | 24 | 28 | 66 | 1150,9 🔺 | 47,5 🔺 | 3,2 | 🔺 files, size |
| ~~680~~ | ~~org.springframework.cloud:spring-cloud-bus-dependencies~~ | ~~-~~ | ~~2026-06-11~~ | ~~0,2~~ | ~~-~~ | ~~5.0.2~~ | ~~-~~ | ~~2~~ | ~~0~~ | ~~2~~ | ~~0~~ | ~~243~~ | ~~1049,0 🔺~~ | ~~161,7 🔺~~ | ~~3,3~~ | ~~🔺 files, size~~ |
| ~~681~~ | ~~org.springframework.cloud:spring-cloud-kubernetes-dependencies~~ | ~~-~~ | ~~2026-06-11~~ | ~~0,2~~ | ~~-~~ | ~~5.0.2~~ | ~~-~~ | ~~2~~ | ~~0~~ | ~~2~~ | ~~0~~ | ~~243~~ | ~~1049,0 🔺~~ | ~~161,7 🔺~~ | ~~3,3~~ | ~~🔺 files, size~~ |
| 682 | org.antlr:antlr-master |  |  |  |  |  |  | 0 | 0 | 0 | 0 |  |  |  |  |  |
| 683 | org.eclipse.jetty:jetty-servlet | org.eclipse.jetty.servlet ✳️ | 2025-08-14 | 17,4 | 7,8 | 11.0.26 | 11.0.26 | 371 | 111 | 3 | 3 | 82 | 882,6 🔺 | 77,9 🔺 | 3,3 | 🔺 files, size |
| 684 | org.scala-lang:scala-compiler | scala.tools.nsc ⚙️ | 2025-12-08 | 17,7 | 8,4 | 2.12.21 | 2.12.21 | 219 | 55 | 3 | 3 | 35 | 289,1 🔺 | 238,7 🔺 | 4,4 | 🔺 files, size |
| 685 | jline:jline 🚩 |  | 2018-03-26 | 21,2 |  | 2.14.6 |  | 26 | 0 | 0 | 0 |  |  |  |  |  |
| ~~686~~ | ~~io.smallrye:jandex-parent~~ | ~~-~~ | ~~2026-06-08~~ | ~~0,2~~ | ~~-~~ | ~~3.6.0~~ | ~~-~~ | ~~1~~ | ~~0~~ | ~~1~~ | ~~0~~ | ~~90~~ | ~~252,4~~ | ~~4,9~~ | ~~3,7~~ | ~~-~~ |
| 687 | com.google.jimfs:jimfs ⚠️ | com.google.common.jimfs ⚙️ | 2025-07-11 | 12,5 | 3,1 | 1.3.1 | 1.3.1 | 9 | 2 | 0 | 0 |  |  |  |  |  |
| 688 | org.jetbrains.kotlin:kotlin-annotation-processing-gradle |  | 2026-06-25 | 8,7 |  | 2.4.10-RC |  | 187 | 0 | 32 | 0 | 175 | 3924,5 🔺 | 568,3 🔺 | 3,2 | 🔺 files, size |
| ~~689~~ | ~~org.springframework.cloud:spring-cloud-circuitbreaker-dependencies~~ | ~~-~~ | ~~2026-06-11~~ | ~~0,2~~ | ~~-~~ | ~~5.0.2~~ | ~~-~~ | ~~2~~ | ~~0~~ | ~~2~~ | ~~0~~ | ~~243~~ | ~~1049,0 🔺~~ | ~~161,7 🔺~~ | ~~3,3~~ | ~~🔺 files, size~~ |
| 690 | com.thoughtworks.paranamer:paranamer ⚠️ |  | 2025-03-11 | 19,5 |  | 2.8.3 |  | 48 | 0 | 0 | 0 |  |  |  |  |  |
| 691 | dev.equo.ide:solstice |  | 2026-06-02 | 3,7 |  | 1.8.2 |  | 49 | 0 | 1 | 0 | 1 | 50,0 | 1,0 | 0,1 | - |
| ~~692~~ | ~~com.google.api-client:google-api-client-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~9~~ | ~~184,0~~ | ~~2,2~~ | ~~0,2~~ | ~~-~~ |
| 693 | com.sun.activation:javax.activation 🚩 | java.activation ⚙️ | 2017-09-06 | 8,9 | 8,9 | 1.2.0 | 1.2.0 | 1 | 1 | 0 | 0 |  |  |  |  |  |
| 694 | com.sun.xml.bind.mvn:jaxb-bundles |  |  |  |  |  |  | 0 | 0 | 0 | 0 | 1 | 20,0 | 0,0 | 0,1 | - |
| 695 | software.amazon.awssdk:identity-spi | software.amazon.awssdk.identity.spi ⚙️ | 2026-08-07 | 2,8 | 2,8 | 2.51.3 | 2.51.3 | 681 | 687 | 233 | 239 | 520 | 7784,8 🔺 | 3309,0 🔺 | 27,7 🔺 | 🔺 files, size, releases |
| 696 | software.amazon.awssdk:http-auth-aws | software.amazon.awssdk.http.auth.aws ⚙️ | 2026-08-07 | 2,8 | 2,8 | 2.51.3 | 2.51.3 | 681 | 687 | 233 | 239 | 520 | 7784,8 🔺 | 3309,0 🔺 | 27,7 🔺 | 🔺 files, size, releases |
| 697 | software.amazon.awssdk:http-auth-spi | software.amazon.awssdk.http.auth.spi ⚙️ | 2026-08-07 | 2,8 | 2,8 | 2.51.3 | 2.51.3 | 681 | 687 | 233 | 239 | 520 | 7784,8 🔺 | 3309,0 🔺 | 27,7 🔺 | 🔺 files, size, releases |
| 698 | org.tensorflow:tensorflow-lite-metadata ⚠️ |  | 2025-01-24 | 6,0 |  | 0.5.0 |  | 20 | 0 | 0 | 0 | 4 | 90,0 | 985,7 🔺 | 0,1 | 🔺 size |
| 699 | software.amazon.awssdk:checksums | software.amazon.awssdk.checksums ⚙️ | 2026-08-07 | 2,8 | 2,8 | 2.51.3 | 2.51.3 | 681 | 687 | 233 | 239 | 520 | 7784,8 🔺 | 3309,0 🔺 | 27,7 🔺 | 🔺 files, size, releases |
| 700 | software.amazon.awssdk:aws-json-protocol | software.amazon.awssdk.protocols.json ⚙️ | 2026-08-07 | 7,7 | 7,7 | 2.51.3 | 2.51.3 | 1802 | 1808 | 233 | 239 | 520 | 7784,8 🔺 | 3309,0 🔺 | 27,7 🔺 | 🔺 files, size, releases |
| 701 | software.amazon.awssdk:checksums-spi | software.amazon.awssdk.checksums.spi ⚙️ | 2026-08-07 | 2,8 | 2,8 | 2.51.3 | 2.51.3 | 681 | 687 | 233 | 239 | 520 | 7784,8 🔺 | 3309,0 🔺 | 27,7 🔺 | 🔺 files, size, releases |
| 702 | org.springframework.data:spring-data-releasetrain |  |  |  |  |  |  | 0 | 0 | 0 | 0 | 17 | 68,0 | 17,9 🔺 | 10,3 🔺 | 🔺 size, releases |
| 703 | software.amazon.awssdk:http-auth | software.amazon.awssdk.http.auth ⚙️ | 2026-08-07 | 2,8 | 2,8 | 2.51.3 | 2.51.3 | 681 | 687 | 233 | 239 | 520 | 7784,8 🔺 | 3309,0 🔺 | 27,7 🔺 | 🔺 files, size, releases |
| ~~704~~ | ~~com.google.api:gax-bom 🚩~~ | ~~-~~ | ~~2022-12-09~~ | ~~7,8~~ | ~~-~~ | ~~2.19.6~~ | ~~-~~ | ~~88~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~5~~ | ~~40,9~~ | ~~21,2 🔺~~ | ~~4,8~~ | ~~🔺 size~~ |
| 705 | io.grpc:grpc-util | io.grpc.util ⚙️ | 2026-06-23 | 2,9 | 2,9 | 1.82.1 | 1.82.1 | 44 | 49 | 13 | 18 | 38 | 619,6 | 203,5 🔺 | 1,4 | 🔺 size |
| 706 | backport-util-concurrent:backport-util-concurrent 🚩 |  | 2007-11-11 | 21,0 |  | 3.1 |  | 7 | 0 | 0 | 0 |  |  |  |  |  |
| 707 | io.projectreactor.netty:reactor-netty-core | reactor.netty.core ⚙️ | 2026-06-08 | 5,8 | 5,8 | 1.3.6 | 1.3.6 | 115 | 115 | 18 | 18 | 5 | 180,0 | 6,2 | 1,7 | - |
| ~~708~~ | ~~org.jboss:jboss-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| ~~709~~ | ~~org.apache.maven:maven-compat~~ | ~~-~~ | ~~2026-07-30~~ | ~~17,6~~ | ~~-~~ | ~~4.0.0-rc-6~~ | ~~-~~ | ~~83~~ | ~~1~~ | ~~7~~ | ~~1~~ | ~~36~~ | ~~360,2~~ | ~~12,5~~ | ~~0,8~~ | ~~-~~ |
| ~~710~~ | ~~net.minidev:minidev-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| 711 | com.fasterxml.jackson.jaxrs:jackson-jaxrs-providers |  | 2026-07-08 | 0,2 |  | 2.18.9 |  | 6 | 0 | 6 | 0 | 7 | 244,0 | 1,1 | 1,5 | - |
| 712 | org.hibernate.orm:hibernate-core | org.hibernate.orm.core ⚙️ | 2026-07-05 | 7,7 | 7,7 | 7.4.4.Final | 7.4.4.Final | 268 | 276 | 121 | 129 | 24 | 425,0 🔺 | 91,9 🔺 | 10,1 🔺 | 🔺 files, size, releases |
| 713 | info.picocli:picocli ⚠️ | info.picocli ✳️ | 2025-04-19 | 9,3 | 8,8 | 4.7.7 | 4.7.7 | 86 | 73 | 0 | 0 |  |  |  |  |  |
| 714 | org.apache.xbean:xbean |  |  |  |  |  |  | 0 | 0 | 0 | 0 | 12 | 228,0 | 5,1 | 0,3 | - |
| 715 | org.jetbrains.kotlinx:kotlinx-coroutines-jdk8 | kotlinx.coroutines.jdk8 🏷️ | 2026-05-07 | 8,9 | 3,4 | 1.11.0 | 1.11.0 | 118 | 20 | 3 | 3 | 768 | 744,5 🔺 | 91,4 🔺 | 57,3 🔺 | 🔺 files, size, releases |
| 716 | com.googlecode.libphonenumber:libphonenumber | com.google.i18n.phonenumbers.libphonenumber ⚙️ | 2026-07-31 | 15,4 | 1,7 | 9.0.36 | 9.0.36 | 307 | 41 | 25 | 25 | 6 | 42,0 | 11,8 | 5,9 | - |
| 717 | org.eclipse.jetty:jetty-xml | org.eclipse.jetty.xml ✳️ | 2026-08-03 | 17,4 | 7,8 | 12.1.12 | 12.1.12 | 437 | 179 | 27 | 29 | 82 | 882,6 🔺 | 77,9 🔺 | 3,3 | 🔺 files, size |
| 718 | org.apache.zookeeper:zookeeper |  | 2026-02-11 | 16,4 |  | 3.9.5 |  | 54 | 0 | 4 | 0 | 8 | 220,0 | 28,2 | 0,3 | - |
| 719 | org.antlr:antlr-runtime 🚩 |  | 2022-04-10 | 19,2 |  | 3.5.3 |  | 18 | 0 | 0 | 0 |  |  |  |  |  |
| 720 | io.projectreactor.netty:reactor-netty-http | reactor.netty.http ⚙️ | 2026-06-08 | 5,8 | 5,8 | 1.3.6 | 1.3.6 | 115 | 115 | 18 | 18 | 5 | 180,0 | 6,2 | 1,7 | - |
| ~~721~~ | ~~com.google.jimfs:jimfs-parent 🚩~~ | ~~-~~ | ~~2014-06-16~~ | ~~12,5~~ | ~~-~~ | ~~1.0~~ | ~~-~~ | ~~4~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| 722 | org.apache.poi:poi | org.apache.poi.poi 🏷️ | 2025-11-26 | 19,2 | 5,6 | 5.5.1 | 5.5.1 | 67 | 13 | 2 | 2 | 7 | 260,0 | 70,2 | 0,2 | - |
| ~~723~~ | ~~org.apache.maven.wagon:wagon 🚩~~ | ~~-~~ | ~~2006-05-07~~ | ~~20,3~~ | ~~-~~ | ~~1.0-alpha-2~~ | ~~-~~ | ~~2~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| 724 | com.ibm.icu:icu4j | com.ibm.icu ⚙️ | 2026-03-17 | 20,6 | 7,8 | 78.3 | 78.3 | 67 | 23 | 3 | 3 | 2 | 32,0 | 25,7 | 0,3 | - |
| ~~725~~ | ~~org.eclipse.jetty:jetty-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~82~~ | ~~882,6 🔺~~ | ~~77,9 🔺~~ | ~~3,3~~ | ~~🔺 files, size~~ |
| ~~726~~ | ~~com.google.oauth-client:google-oauth-client-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| 727 | org.apache.logging.log4j:log4j-layout-template-json | org.apache.logging.log4j.layout.template.json ✳️ | 2026-06-29 | 5,8 | 3,2 | 2.26.1 | 2.26.1 | 31 | 22 | 5 | 6 | 29 | 580,0 | 8,1 | 0,4 | - |
| 728 | org.springframework.boot:spring-boot-starter-reactor-netty | spring.boot.starter.reactor.netty ⚙️ | 2026-06-25 | 8,5 | 8,5 | 3.5.16 | 3.5.16 | 225 | 225 | 35 | 35 | 336 | 3788,6 🔺 | 132,2 🔺 | 2,9 | 🔺 files, size |
| ~~729~~ | ~~com.amazonaws:aws-java-sdk-bom~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~426~~ | ~~862,8 🔺~~ | ~~702,0 🔺~~ | ~~6,1~~ | ~~🔺 files, size~~ |
| 730 | org.springframework.boot:spring-boot-starter-webflux | spring.boot.starter.webflux ⚙️ | 2026-06-25 | 8,5 | 8,5 | 3.5.16 | 3.5.16 | 225 | 225 | 35 | 35 | 336 | 3788,6 🔺 | 132,2 🔺 | 2,9 | 🔺 files, size |
| 731 | com.squareup.retrofit2:retrofit ⚠️ | retrofit2 ⚙️ | 2025-05-15 | 10,6 | 8,4 | 3.0.0 | 3.0.0 | 26 | 18 | 0 | 0 |  |  |  |  |  |
| ~~732~~ | ~~io.github.openfeign:feign-bom~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~46~~ | ~~676,0~~ | ~~6,7~~ | ~~1,0~~ | ~~-~~ |
| 733 | com.google.http-client:google-http-client-apache-v2 | com.google.api.client.http.apache.v2 ⚙️ | 2026-07-20 | 7,2 | 7,2 | 2.2.0 | 2.2.0 | 58 | 58 | 8 | 8 | 11 | 220,0 | 3,2 | 0,7 | - |
| 734 | org.apache.xmlbeans:xmlbeans ⚠️ | org.apache.xmlbeans 🏷️ | 2024-12-04 | 19,1 | 5,8 | 5.3.0 | 5.3.0 | 20 | 11 | 0 | 0 |  |  |  |  |  |
| ~~735~~ | ~~org.glassfish.hk2:hk2-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~54~~ | ~~1986,0~~ | ~~19,8~~ | ~~0,3~~ | ~~-~~ |
| 736 | commons-chain:commons-chain 🚩 |  | 2008-05-29 | 20,7 |  | 1.2 |  | 3 | 0 | 0 | 0 |  |  |  |  |  |
| 737 | org.apache.xbean:xbean-reflect | org.apache.xbean.reflect ⚙️ | 2026-08-02 | 20,5 | 8,7 | 5.0.0 | 5.0.0 | 63 | 27 | 4 | 5 | 12 | 228,0 | 5,1 | 0,3 | - |
| 738 | org.springframework.retry:spring-retry | spring.retry ⚙️ | 2026-06-08 | 15,1 | 0,2 | 2.0.13 | 2.0.13 | 35 | 1 | 1 | 1 | 1 | 16,0 | 1,0 | 0,1 | - |
| ~~739~~ | ~~org.apache.maven:maven-builder-support~~ | ~~-~~ | ~~2026-07-30~~ | ~~11,4~~ | ~~-~~ | ~~4.0.0-rc-6~~ | ~~-~~ | ~~58~~ | ~~1~~ | ~~7~~ | ~~1~~ | ~~36~~ | ~~360,2~~ | ~~12,5~~ | ~~0,8~~ | ~~-~~ |
| 740 | com.squareup.okhttp3:okhttp-urlconnection | okhttp3.urlconnection 🏷️ | 2026-06-08 | 10,6 | 8,5 | 5.4.0 | 5.4.0 | 107 | 87 | 8 | 8 | 18 | 828,8 | 9,3 | 0,7 | - |
| 741 | jakarta.servlet:jakarta.servlet-api | jakarta.servlet ✳️ | 2026-05-18 | 7,6 | 7,0 | 6.2.0-M2 | 6.2.0-M2 | 12 | 11 | 2 | 2 | 1 | 30,0 | 1,7 | 0,2 | - |
| 742 | org.apache.geronimo.genesis:genesis-java5-flava |  |  |  |  |  |  | 0 | 0 | 0 | 0 |  |  |  |  |  |
| 743 | io.micrometer:micrometer-jakarta9 | micrometer.jakarta9 ⚙️ | 2026-06-08 | 2,7 | 2,7 | 1.17.0 | 1.17.0 | 77 | 77 | 27 | 27 | 42 | 721,7 🔺 | 12,2 | 4,9 | 🔺 files |
| 744 | org.apache.poi:poi-ooxml | org.apache.poi.ooxml 🏷️ | 2025-11-26 | 17,7 | 5,6 | 5.5.1 | 5.5.1 | 56 | 13 | 2 | 2 | 7 | 260,0 | 70,2 | 0,2 | - |
| ~~745~~ | ~~org.springframework.data:spring-data-jpa-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~17~~ | ~~68,0~~ | ~~17,9 🔺~~ | ~~10,3 🔺~~ | ~~🔺 size, releases~~ |
| 746 | org.slf4j:slf4j-jdk14 | org.slf4j.jul ✳️ | 2026-05-12 | 21,0 | 9,3 | 2.0.18 | 2.0.18 | 116 | 48 | 1 | 1 | 12 | 294,0 | 3,0 | 0,1 | - |
| 747 | io.smallrye:jandex | org.jboss.jandex ✳️ | 2026-06-08 | 4,0 | 4,0 | 3.6.0 | 3.6.0 | 39 | 39 | 5 | 5 | 90 | 252,4 | 4,9 | 3,7 | - |
| 748 | org.jacoco:jacoco-maven-plugin |  | 2026-06-04 | 15,1 |  | 0.8.15 |  | 40 | 0 | 2 | 0 | 6 | 108,0 | 4,2 | 0,2 | - |
| 749 | jakarta.ws.rs:jakarta.ws.rs-api ⚠️ | jakarta.ws.rs ✳️ | 2024-04-02 | 7,8 | 7,8 | 4.0.0 | 4.0.0 | 10 | 10 | 0 | 0 |  |  |  |  |  |
| ~~750~~ | ~~com.google.api-client:google-api-client-bom~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~9~~ | ~~184,0~~ | ~~2,2~~ | ~~0,2~~ | ~~-~~ |
| 751 | org.apache.velocity:velocity-tools 🚩 |  | 2010-05-10 | 19,4 |  | 2.0 |  | 5 | 0 | 0 | 0 |  |  |  |  |  |
| 752 | com.googlecode.juniversalchardet:juniversalchardet 🚩 |  | 2011-09-19 | 14,9 |  | 1.0.3 |  | 1 | 0 | 0 | 0 |  |  |  |  |  |
| 753 | com.fasterxml.jackson.datatype:jackson-datatypes-collections |  | 2026-07-08 | 0,2 |  | 2.18.9 |  | 6 | 0 | 6 | 0 | 23 | 690,3 | 6,4 | 1,5 | - |
| ~~754~~ | ~~io.cucumber:cucumber-parent~~ | ~~-~~ | ~~2026-05-18~~ | ~~0,2~~ | ~~-~~ | ~~5.4.1~~ | ~~-~~ | ~~1~~ | ~~0~~ | ~~1~~ | ~~0~~ | ~~49~~ | ~~89,2 🔺~~ | ~~3,6~~ | ~~19,2 🔺~~ | ~~🔺 files, releases~~ |
| 755 | io.opentelemetry.semconv:opentelemetry-semconv | io.opentelemetry.semconv ⚙️ | 2026-06-16 | 2,9 | 2,9 | 1.42.0 | 1.42.0 | 22 | 23 | 8 | 9 | 2 | 50,0 | 0,7 | 1,3 | - |
| 756 | net.sf.kxml:kxml2 🚩 |  | 2009-04-21 | 21,0 |  | 2.3.0 |  | 4 | 0 | 0 | 0 |  |  |  |  |  |
| 757 | org.xmlresolver:xmlresolver | org.xmlresolver.xmlresolver ⚙️ | 2026-05-04 | 11,4 | 4,5 | 6.0.23 | 6.0.23 | 90 | 48 | 3 | 3 | 1 | 60,0 | 1,6 | 0,3 | - |
| 758 | org.fusesource.jansi:jansi | org.fusesource.jansi ✳️ | 2026-03-27 | 16,4 | 7,4 | 2.4.3 | 2.4.3 | 32 | 15 | 1 | 1 | 1 | 24,0 | 1,1 | 0,1 | - |
| 759 | io.grpc:grpc-netty-shaded | io.netty.internal.tcnative ✳️ | 2026-06-23 | 8,6 | 2,9 | 1.82.1 | 2.0.75.Final | 160 | 49 | 13 | 18 | 38 | 619,6 | 203,5 🔺 | 1,4 | 🔺 size |
| 760 | org.freemarker:freemarker ⚠️ | freemarker ⚙️ | 2024-12-08 | 19,4 | 5,5 | 2.3.34 | 2.3.34 | 26 | 4 | 0 | 0 |  |  |  |  |  |
| 761 | org.mozilla:rhino | org.mozilla.rhino 🏷️ | 2026-02-15 | 15,1 | 4,7 | 1.9.1 | 1.9.1 | 25 | 9 | 5 | 5 | 7 | 117,4 | 8,0 | 0,4 | - |
| 762 | org.apache.logging.log4j:log4j-slf4j-impl | org.apache.logging.log4j.slf4j.impl ✳️ | 2026-06-29 | 13,3 | 8,7 | 2.26.1 | 2.26.1 | 70 | 37 | 5 | 6 | 29 | 580,0 | 8,1 | 0,4 | - |
| 763 | org.codehaus.jackson:jackson-core-asl 🚩 |  | 2013-07-15 | 17,6 |  | 1.9.13 |  | 82 | 0 | 0 | 0 |  |  |  |  |  |
| 764 | com.amazonaws:aws-java-sdk-kms |  | 2025-12-29 | 11,7 |  | 1.12.797 |  | 1951 | 0 | 9 | 0 | 426 | 862,8 🔺 | 702,0 🔺 | 6,1 | 🔺 files, size |
| ~~765~~ | ~~org.apache.maven.plugins:maven-release-plugin~~ | ~~-~~ | ~~2025-12-09~~ | ~~20,3~~ | ~~-~~ | ~~3.3.1~~ | ~~-~~ | ~~36~~ | ~~0~~ | ~~3~~ | ~~0~~ | ~~35~~ | ~~44,4~~ | ~~2,7~~ | ~~3,1~~ | ~~-~~ |
| 766 | org.testng:testng | org.testng ⚙️ | 2026-01-22 | 16,1 | 7,6 | 7.12.0 | 7.12.0 | 90 | 19 | 1 | 1 | 2 | 37,0 | 1,5 | 0,2 | - |
| 767 | org.apache.groovy:groovy-json | org.apache.groovy.json ⚙️ | 2026-08-01 | 5,8 | 5,8 | 6.0.0-beta-1 | 6.0.0-beta-1 | 68 | 68 | 17 | 17 | 43 | 2259,4 🔺 | 106,3 🔺 | 1,4 | 🔺 files, size |
| 768 | io.swagger.core.v3:swagger-models | io.swagger.v3.oas.models ⚙️ | 2026-06-22 | 8,7 | 7,3 | 2.2.52 | 2.2.52 | 80 | 71 | 17 | 18 | 20 | 416,1 | 12,0 | 1,4 | - |
| 769 | org.reflections:reflections 🚩 | org.reflections ⚙️ | 2021-10-25 | 15,0 | 4,8 | 0.10.2 | 0.10.2 | 17 | 2 | 0 | 0 |  |  |  |  |  |
| 770 | org.springframework:spring-oxm | spring.oxm ⚙️ | 2026-06-08 | 16,7 | 8,9 | 7.0.8 | 7.0.8 | 288 | 195 | 24 | 24 | 24 | 449,0 | 109,6 🔺 | 2,0 | 🔺 size |
| 771 | org.springframework.boot:spring-boot-configuration-processor | spring.boot.configuration.processor ⚙️ | 2026-06-25 | 11,7 | 8,5 | 3.5.16 | 3.5.16 | 274 | 225 | 35 | 35 | 336 | 3788,6 🔺 | 132,2 🔺 | 2,9 | 🔺 files, size |
| 772 | org.apache.groovy:groovy-xml | org.apache.groovy.xml ⚙️ | 2026-08-01 | 5,8 | 5,8 | 6.0.0-beta-1 | 6.0.0-beta-1 | 68 | 68 | 17 | 17 | 43 | 2259,4 🔺 | 106,3 🔺 | 1,4 | 🔺 files, size |
| 773 | io.opentelemetry:opentelemetry-sdk-extension-autoconfigure | io.opentelemetry.sdk.autoconfigure ⚙️ | 2026-06-05 | 5,6 | 5,6 | 1.63.0 | 1.63.0 | 84 | 86 | 12 | 14 | 33 | 714,2 🔺 | 3,8 | 2,0 | 🔺 files |
| ~~774~~ | ~~org.jline:jline-parent~~ | ~~-~~ | ~~2026-06-30~~ | ~~0,2~~ | ~~-~~ | ~~4.3.1~~ | ~~-~~ | ~~10~~ | ~~0~~ | ~~10~~ | ~~0~~ | ~~24~~ | ~~525,3 🔺~~ | ~~20,5~~ | ~~2,9~~ | ~~🔺 files~~ |
| 775 | com.google.auto.service:auto-service-aggregator |  |  |  |  |  |  | 0 | 0 | 0 | 0 |  |  |  |  |  |
| 776 | org.jetbrains.kotlin:kotlin-build-tools-impl |  | 2026-06-25 | 3,2 |  | 2.4.10-RC |  | 92 | 0 | 32 | 0 | 175 | 3924,5 🔺 | 568,3 🔺 | 3,2 | 🔺 files, size |
| 777 | org.glassfish.jersey.core:jersey-common |  | 2026-06-11 | 14,5 |  | 3.1.12 |  | 155 | 6 | 9 | 6 | 3 | 76,0 | 9,9 | 0,8 | - |
| 778 | org.testcontainers:testcontainers |  | 2026-04-20 | 11,1 |  | 2.0.5 |  | 113 | 0 | 7 | 0 | 125 | 1494,9 | 38,4 | 0,6 | - |
| 779 | org.eclipse.jetty:jetty-webapp | org.eclipse.jetty.webapp ✳️ | 2025-08-14 | 17,4 | 7,8 | 11.0.26 | 11.0.26 | 371 | 111 | 3 | 3 | 82 | 882,6 🔺 | 77,9 🔺 | 3,3 | 🔺 files, size |
| 780 | org.eclipse.jetty:jetty-client | org.eclipse.jetty.client ✳️ | 2026-08-03 | 17,4 | 7,8 | 12.1.12 | 12.1.12 | 439 | 179 | 29 | 29 | 82 | 882,6 🔺 | 77,9 🔺 | 3,3 | 🔺 files, size |
| ~~781~~ | ~~org.springframework.cloud:spring-cloud-commons-parent~~ | ~~-~~ | ~~2026-06-11~~ | ~~0,2~~ | ~~-~~ | ~~5.0.2~~ | ~~-~~ | ~~2~~ | ~~0~~ | ~~2~~ | ~~0~~ | ~~243~~ | ~~1049,0 🔺~~ | ~~161,7 🔺~~ | ~~3,3~~ | ~~🔺 files, size~~ |
| 782 | com.amazonaws:aws-java-sdk-s3 |  | 2025-12-29 | 11,8 |  | 1.12.797 |  | 1956 | 0 | 9 | 0 | 426 | 862,8 🔺 | 702,0 🔺 | 6,1 | 🔺 files, size |
| 783 | com.squareup.moshi:moshi-kotlin ⚠️ | com.squareup.moshi.kotlin ⚙️ | 2024-12-05 | 9,2 | 8,3 | 1.15.2 | 1.15.2 | 17 | 12 | 0 | 0 |  |  |  |  |  |
| 784 | software.amazon.awssdk:arns | software.amazon.awssdk.arns ⚙️ | 2026-08-07 | 6,8 | 6,8 | 2.51.3 | 2.51.3 | 1627 | 1633 | 233 | 239 | 520 | 7784,8 🔺 | 3309,0 🔺 | 27,7 🔺 | 🔺 files, size, releases |
| 785 | org.apache.ivy:ivy ⚠️ | org.apache.ivy ⚙️ | 2024-12-23 | 18,7 | 8,3 | 2.5.3 | 2.5.3 | 20 | 6 | 0 | 1 |  |  |  |  |  |
| 786 | com.github.virtuald:curvesapi 🚩 | com.github.virtuald.curvesapi ⚙️ | 2023-08-11 | 10,8 | 4,6 | 1.08 | 1.08 | 7 | 2 | 0 | 0 |  |  |  |  |  |
| 787 | com.google.api:api-common | com.google.api.apicommon ⚙️ | 2026-07-08 | 9,9 | 7,3 | 2.65.0 | 2.65.0 | 129 | 116 | 22 | 23 | 5 | 40,9 | 21,2 🔺 | 4,8 | 🔺 size |
| 788 | com.google.http-client:google-http-client-jackson2 | com.google.api.client.json.jackson2 ⚙️ | 2026-07-20 | 14,0 | 6,9 | 2.2.0 | 2.2.0 | 81 | 54 | 8 | 8 | 11 | 220,0 | 3,2 | 0,7 | - |
| 789 | org.webjars:swagger-ui |  | 2026-06-23 | 12,4 |  | 5.32.7 |  | 242 | 0 | 21 | 0 | 30 | 16,3 | 6,6 | 6,3 | - |
| 790 | com.nimbusds:oauth2-oidc-sdk |  | 2026-07-21 | 13,3 |  | 11.38.2 |  | 445 | 0 | 21 | 0 | 11 | 30,4 | 7,4 | 4,2 | - |
| 791 | com.google.api-client:google-api-client | google.api.client ⚙️ | 2026-02-24 | 15,0 | 7,2 | 2.9.0 | 2.9.0 | 91 | 53 | 2 | 2 | 9 | 184,0 | 2,2 | 0,2 | - |
| ~~792~~ | ~~org.apache.maven.plugins:maven-help-plugin~~ | ~~-~~ | ~~2026-06-30~~ | ~~20,3~~ | ~~-~~ | ~~3.5.2~~ | ~~-~~ | ~~17~~ | ~~0~~ | ~~1~~ | ~~0~~ | ~~35~~ | ~~44,4~~ | ~~2,7~~ | ~~3,1~~ | ~~-~~ |
| 793 | com.sun.jersey:jersey-core 🚩 |  | 2017-05-24 | 16,2 |  | 1.19.4 |  | 98 | 0 | 0 | 0 |  |  |  |  |  |
| 794 | com.google.oauth-client:google-oauth-client ⚠️ | com.google.api.client.auth ⚙️ | 2025-05-16 | 15,0 | 6,9 | 1.38.2 | 1.38.2 | 58 | 25 | 0 | 0 |  |  |  |  |  |
| 795 | org.jetbrains.kotlin:fus-statistics-gradle-plugin |  | 2026-07-22 | 1,9 |  | 2.4.20-Beta2 |  | 62 | 0 | 35 | 0 | 175 | 3924,5 🔺 | 568,3 🔺 | 3,2 | 🔺 files, size |
| 796 | com.github.jnr:jffi | org.jnrproject.jffi ⚙️ | 2026-06-26 | 15,0 | 5,9 | 1.4.0 | 1.4.0 | 43 | 14 | 3 | 3 | 9 | 25,3 | 2,9 | 1,9 | - |
| 797 | com.googlecode.json-simple:json-simple 🚩 |  | 2012-03-21 | 17,0 |  | 1.1.1 |  | 2 | 0 | 0 | 0 |  |  |  |  |  |
| 798 | com.fasterxml.jackson.dataformat:jackson-dataformat-csv | com.fasterxml.jackson.dataformat.csv ✳️ | 2026-07-08 | 14,6 | 8,8 | 2.18.9 | 2.18.9 | 180 | 95 | 18 | 18 | 12 | 357,3 | 5,3 | 1,5 | - |
| 799 | com.github.docker-java:docker-java-transport-zerodep | com.github.dockerjava.transport.zerodep ⚙️ | 2026-03-18 | 6,2 | 3,7 | 3.7.1 | 3.7.1 | 30 | 18 | 3 | 3 | 9 | 270,0 | 10,0 | 0,3 | - |
| 800 | io.swagger.core.v3:swagger-core | io.swagger.v3.core ⚙️ | 2026-06-22 | 8,7 | 7,3 | 2.2.52 | 2.2.52 | 80 | 71 | 17 | 18 | 20 | 416,1 | 12,0 | 1,4 | - |
| 801 | org.apache.hadoop:hadoop-client-api |  | 2026-03-24 | 9,6 |  | 3.5.0 |  | 30 | 0 | 3 | 0 | 85 | 2036,0 | 336,5 🔺 | 0,3 | 🔺 size |
| 802 | io.reactivex.rxjava3:rxjava | io.reactivex.rxjava3 ✳️ | 2025-09-24 | 7,1 | 7,1 | 3.1.12 | 3.1.12 | 48 | 48 | 2 | 2 | 1 | 50,0 | 5,5 | 0,2 | - |
| 803 | org.glassfish.jersey.core:jersey-client |  | 2026-06-11 | 14,5 |  | 3.1.12 |  | 155 | 6 | 9 | 6 | 3 | 76,0 | 9,9 | 0,8 | - |
| 804 | io.swagger:swagger-models ⚠️ | io.swagger.models ⚙️ | 2025-05-15 | 11,2 | 3,4 | 1.6.16 | 1.6.16 | 45 | 7 | 0 | 0 | 8 | 63,7 | 28,6 | 1,1 | - |
| 805 | com.google.auto.service:auto-service-annotations 🚩 | com.google.auto.service ⚙️ | 2023-06-12 | 7,4 | 6,2 | 1.1.1 | 1.1.1 | 8 | 6 | 0 | 0 |  |  |  |  |  |
| 806 | org.springframework.security:spring-security-oauth2-core | spring.security.oauth2.core ⚙️ | 2026-06-09 | 8,7 | 8,7 | 7.1.0 | 7.1.0 | 213 | 213 | 31 | 31 | 26 | 319,1 | 27,4 🔺 | 3,6 | 🔺 size |
| 807 | org.glassfish.hk2:external |  |  |  |  |  |  | 0 | 0 | 0 | 0 | 54 | 1986,0 | 19,8 | 0,3 | - |
| ~~808~~ | ~~io.rest-assured:rest-assured-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~14~~ | ~~336,0~~ | ~~9,5~~ | ~~0,3~~ | ~~-~~ |
| ~~809~~ | ~~org.apache.maven.plugins:maven-enforcer-plugin~~ | ~~-~~ | ~~2026-05-15~~ | ~~19,3~~ | ~~-~~ | ~~3.6.3~~ | ~~-~~ | ~~28~~ | ~~0~~ | ~~2~~ | ~~0~~ | ~~35~~ | ~~44,4~~ | ~~2,7~~ | ~~3,1~~ | ~~-~~ |
| 810 | io.airlift:aircompressor |  | 2026-02-24 | 10,4 |  | 2.0.3 |  | 29 | 0 | 1 | 0 | 48 | 575,8 🔺 | 56,2 🔺 | 14,5 🔺 | 🔺 files, size, releases |
| 811 | org.codehaus.jackson:jackson-mapper-asl 🚩 |  | 2013-07-15 | 17,6 |  | 1.9.13 |  | 82 | 0 | 0 | 0 |  |  |  |  |  |
| 812 | io.swagger.core.v3:swagger-annotations-jakarta | io.swagger.v3.oas.annotations ⚙️ | 2026-06-22 | 5,5 | 5,5 | 2.2.52 | 2.2.52 | 60 | 61 | 17 | 18 | 20 | 416,1 | 12,0 | 1,4 | - |
| 813 | com.fasterxml.jackson.dataformat:jackson-dataformat-toml | com.fasterxml.jackson.dataformat.toml ✳️ | 2026-07-08 | 5,3 | 5,3 | 2.18.9 | 2.18.9 | 66 | 66 | 18 | 18 | 12 | 357,3 | 5,3 | 1,5 | - |
| ~~814~~ | ~~com.google.cloud:google-cloud-bom~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~402~~ | ~~249,5 🔺~~ | ~~19,3 🔺~~ | ~~67,0 🔺~~ | ~~🔺 files, size, releases~~ |
| ~~815~~ | ~~org.apache.pdfbox:pdfbox-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~12~~ | ~~125,0~~ | ~~45,5~~ | ~~0,4~~ | ~~-~~ |
| 816 | com.google.api:gax |  | 2026-07-30 | 10,5 |  | 2.83.0 |  | 274 | 0 | 23 | 0 | 5 | 40,9 | 21,2 🔺 | 4,8 | 🔺 size |
| 817 | org.apache.hadoop:hadoop-client-runtime |  | 2026-03-24 | 9,6 |  | 3.5.0 |  | 30 | 8 | 3 | 0 | 85 | 2036,0 | 336,5 🔺 | 0,3 | 🔺 size |
| 818 | com.google.collections:google-collections 🚩 |  | 2009-12-30 | 17,8 |  | 1.0 |  | 8 | 0 | 0 | 0 |  |  |  |  |  |
| 819 | org.glassfish:json |  |  |  |  |  |  | 0 | 0 | 0 | 0 | 2 | 34,9 | 13,4 | 1,3 | - |
| 820 | io.opentelemetry:opentelemetry-exporter-logging | io.opentelemetry.exporter.logging ⚙️ | 2026-06-05 | 5,8 | 5,8 | 1.63.0 | 1.63.0 | 89 | 91 | 12 | 14 | 33 | 714,2 🔺 | 3,8 | 2,0 | 🔺 files |
| ~~821~~ | ~~org.apache.maven.shared:maven-invoker ⚠️~~ | ~~-~~ | ~~2024-05-07~~ | ~~19,7~~ | ~~-~~ | ~~3.3.0~~ | ~~-~~ | ~~15~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~4~~ | ~~29,0~~ | ~~0,6~~ | ~~0,5~~ | ~~-~~ |
| 822 | org.jetbrains.kotlin:kotlin-test | kotlin.test 🏷️ | 2026-07-22 | 10,6 | 6,1 | 2.4.20-Beta2 | 2.4.20-Beta2 | 220 | 156 | 35 | 35 | 175 | 3924,5 🔺 | 568,3 🔺 | 3,2 | 🔺 files, size |
| 823 | org.eclipse.jetty:jetty-util-ajax | org.eclipse.jetty.util.ajax ✳️ | 2026-08-03 | 13,9 | 7,8 | 12.1.12 | 12.1.12 | 325 | 179 | 28 | 29 | 82 | 882,6 🔺 | 77,9 🔺 | 3,3 | 🔺 files, size |
| 824 | com.mysql:mysql-connector-j |  | 2026-04-22 | 3,8 |  | 9.7.0 |  | 15 | 0 | 3 | 0 | 1 | 17,3 | 5,9 | 0,3 | - |
| ~~825~~ | ~~org.mapstruct:mapstruct-parent~~ | ~~-~~ | ~~2026-06-27~~ | ~~0,1~~ | ~~-~~ | ~~1.7.0.Beta2~~ | ~~-~~ | ~~1~~ | ~~0~~ | ~~1~~ | ~~0~~ | ~~4~~ | ~~63,0~~ | ~~6,0~~ | ~~0,2~~ | ~~-~~ |
| 826 | software.amazon.awssdk:aws-xml-protocol | software.amazon.awssdk.protocols.xml ⚙️ | 2026-08-07 | 7,7 | 6,6 | 2.51.3 | 2.51.3 | 1802 | 1581 | 233 | 239 | 520 | 7784,8 🔺 | 3309,0 🔺 | 27,7 🔺 | 🔺 files, size, releases |
| 827 | software.amazon.awssdk:s3 | software.amazon.awssdk.services.s3 ⚙️ | 2026-08-07 | 9,1 | 7,7 | 2.51.3 | 2.51.3 | 1813 | 1807 | 233 | 239 | 520 | 7784,8 🔺 | 3309,0 🔺 | 27,7 🔺 | 🔺 files, size, releases |
| 828 | io.grpc:grpc-inprocess | io.grpc.inprocess ⚙️ | 2026-06-23 | 2,9 | 2,9 | 1.82.1 | 1.82.1 | 44 | 49 | 13 | 18 | 38 | 619,6 | 203,5 🔺 | 1,4 | 🔺 size |
| 829 | com.zaxxer:SparseBitSet ⚠️ | com.zaxxer.sparsebitset ⚙️ | 2023-09-06 | 12,2 | 2,9 | 1.3 | 1.3 | 4 | 1 | 0 | 0 | 2 | 24,4 | 0,7 | 0,4 | - |
| 830 | org.springframework.security:spring-security-oauth2-jose | spring.security.oauth2.jose ⚙️ | 2026-06-09 | 8,7 | 8,7 | 7.1.0 | 7.1.0 | 213 | 213 | 31 | 31 | 26 | 319,1 | 27,4 🔺 | 3,6 | 🔺 size |
| 831 | org.hamcrest:hamcrest-library ⚠️ | org.hamcrest.library.deprecated ⚙️ | 2024-08-01 | 19,3 | 7,7 | 3.0 | 3.0 | 13 | 7 | 0 | 0 |  |  |  |  |  |
| 832 | net.sf.saxon:Saxon-HE |  | 2026-07-09 | 14,4 |  | 12.10 |  | 93 | 0 | 3 | 0 | 1 | 80,0 | 24,5 | 0,3 | - |
| 833 | io.swagger.core.v3:swagger-models-jakarta | io.swagger.v3.oas.models ⚙️ | 2026-06-22 | 5,5 | 5,5 | 2.2.52 | 2.2.52 | 60 | 61 | 17 | 18 | 20 | 416,1 | 12,0 | 1,4 | - |
| 834 | org.apache.commons:commons-csv ⚠️ | org.apache.commons.csv ✳️ | 2025-07-27 | 12,0 | 3,5 | 1.14.1 | 1.14.1 | 16 | 6 | 0 | 0 | 36 | 52,4 | 3,2 | 3,3 | - |
| 835 | io.swagger.core.v3:swagger-core-jakarta | io.swagger.v3.core ⚙️ | 2026-06-22 | 5,5 | 5,5 | 2.2.52 | 2.2.52 | 60 | 61 | 17 | 18 | 20 | 416,1 | 12,0 | 1,4 | - |
| 836 | org.eclipse.platform:org.eclipse.osgi | org.eclipse.osgi ✳️ | 2026-06-05 | 9,6 | 8,2 | 3.24.200 | 3.24.200 | 38 | 33 | 4 | 4 | 219 | 28,1 | 2,1 | 24,9 🔺 | 🔺 releases |
| ~~837~~ | ~~io.smallrye:smallrye-parent~~ | ~~-~~ | ~~2026-07-01~~ | ~~0,2~~ | ~~-~~ | ~~51~~ | ~~-~~ | ~~2~~ | ~~0~~ | ~~2~~ | ~~0~~ | ~~90~~ | ~~252,4~~ | ~~4,9~~ | ~~3,7~~ | ~~-~~ |
| 838 | software.amazon.awssdk:retries-spi | software.amazon.awssdk.retries.api ⚙️ | 2026-08-07 | 2,2 | 2,2 | 2.51.3 | 2.51.3 | 513 | 519 | 233 | 239 | 520 | 7784,8 🔺 | 3309,0 🔺 | 27,7 🔺 | 🔺 files, size, releases |
| 839 | io.opentelemetry:opentelemetry-api-incubator | io.opentelemetry.api.incubator ⚙️ | 2026-06-05 | 2,4 | 2,4 | 1.63.0-alpha | 1.63.0-alpha | 31 | 33 | 12 | 14 | 33 | 714,2 🔺 | 3,8 | 2,0 | 🔺 files |
| 840 | software.amazon.awssdk:retries | software.amazon.awssdk.retries ⚙️ | 2026-08-07 | 2,2 | 2,2 | 2.51.3 | 2.51.3 | 513 | 519 | 233 | 239 | 520 | 7784,8 🔺 | 3309,0 🔺 | 27,7 🔺 | 🔺 files, size, releases |
| 841 | org.scala-lang.modules:scala-collection-compat_2.12 | scala.collection.compat ⚙️ | 2025-10-07 | 8,2 | 5,2 | 2.14.0 | 2.14.0 | 36 | 12 | 1 | 1 | 14 | 112,0 | 8,3 | 0,3 | - |
| 842 | org.rnorth.duct-tape:duct-tape 🚩 |  | 2019-04-28 | 11,0 |  | 1.0.8 |  | 5 | 0 | 0 | 0 |  |  |  |  |  |
| 843 | com.microsoft.sqlserver:mssql-jdbc | com.microsoft.sqlserver.jdbc ⚙️ | 2026-05-14 | 9,8 | 8,0 | 13.5.0.jre11-preview | 13.5.0.jre11-preview | 269 | 132 | 28 | 15 | 1 | 32,0 | 3,5 | 2,3 | - |
| ~~844~~ | ~~org.codehaus.janino:janino-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| 845 | io.grpc:grpc-services | io.grpc.services ⚙️ | 2026-06-23 | 10,1 | 2,9 | 1.82.1 | 1.82.1 | 179 | 49 | 13 | 18 | 38 | 619,6 | 203,5 🔺 | 1,4 | 🔺 size |
| 846 | io.jsonwebtoken:jjwt-root |  |  |  |  |  |  | 0 | 0 | 0 | 0 | 6 | 96,0 | 3,5 | 0,2 | - |
| 847 | org.codehaus.mojo:build-helper-maven-plugin ⚠️ |  | 2025-06-04 | 20,5 |  | 3.6.1 |  | 22 | 0 | 0 | 0 | 29 | 39,6 | 1,3 | 3,5 | - |
| 848 | org.codehaus.jettison:jettison |  | 2026-06-17 | 19,8 |  | 1.5.6 |  | 26 | 0 | 2 | 0 | 1 | 24,0 | 0,5 | 0,2 | - |
| ~~849~~ | ~~org.apache.maven.plugins:maven-failsafe-plugin~~ | ~~-~~ | ~~2026-06-02~~ | ~~16,6~~ | ~~-~~ | ~~3.6.0-M1~~ | ~~-~~ | ~~58~~ | ~~0~~ | ~~4~~ | ~~0~~ | ~~35~~ | ~~44,4~~ | ~~2,7~~ | ~~3,1~~ | ~~-~~ |
| 850 | it.unimi.dsi:fastutil | it.unimi.dsi.fastutil ⚙️ | 2025-10-05 | 15,5 | 8,2 | 8.5.18 | 8.5.18 | 79 | 29 | 2 | 3 | 2 | 32,0 | 103,6 | 0,2 | - |
| 851 | com.opencsv:opencsv ⚠️ | com.opencsv ⚙️ | 2025-07-27 | 11,8 | 5,1 | 5.12.0 | 5.12.0 | 37 | 13 | 0 | 0 |  |  |  |  |  |
| 852 | com.google.api.grpc:proto-google-iam-v1 |  | 2026-07-08 | 9,3 |  | 1.68.0 |  | 194 | 0 | 22 | 0 | 710 | 470,8 🔺 | 49,2 🔺 | 54,3 🔺 | 🔺 files, size, releases |
| 853 | org.apache.yetus:audience-annotations ⚠️ |  | 2025-02-16 | 10,0 |  | 0.15.1 |  | 16 | 0 | 0 | 0 |  |  |  |  |  |
| 854 | com.sun.jersey:jersey-client 🚩 |  | 2017-05-24 | 16,2 |  | 1.19.4 |  | 98 | 0 | 0 | 0 |  |  |  |  |  |
| 855 | com.diffplug.durian:durian-collect 🚩 |  | 2016-06-27 | 10,2 |  | 1.2.0 |  | 3 | 0 | 0 | 0 | 8 | 400,0 | 1,5 | 0,2 | - |
| 856 | com.github.jknack:handlebars.java |  | 2026-06-30 | 0,2 |  | 4.5.3 |  | 2 | 0 | 2 | 0 | 9 | 196,0 | 2,2 | 0,3 | - |
| 857 | com.diffplug.durian:durian-core 🚩 |  | 2016-06-27 | 10,2 |  | 1.2.0 |  | 3 | 0 | 0 | 0 | 8 | 400,0 | 1,5 | 0,2 | - |
| 858 | io.swagger.core.v3:swagger-project-jakarta |  | 2026-06-22 | 0,2 |  | 2.2.52 |  | 2 | 0 | 2 | 0 | 20 | 416,1 | 12,0 | 1,4 | - |
| 859 | org.glassfish.jersey.inject:project |  | 2026-06-11 | 0,2 |  | 3.1.12 |  | 1 | 0 | 1 | 0 | 3 | 51,3 | 0,9 | 0,8 | - |
| 860 | org.glassfish.hk2:osgi-resource-locator ⚠️ |  | 2025-03-23 | 16,1 |  | 1.0.4 |  | 47 | 0 | 0 | 0 | 54 | 1986,0 | 19,8 | 0,3 | - |
| 861 | org.slf4j:slf4j-log4j12 🚩 |  | 2022-01-13 | 20,8 |  | 2.0.0-alpha6 |  | 87 | 0 | 0 | 0 | 12 | 294,0 | 3,0 | 0,1 | - |
| 862 | org.antlr:ST4 🚩 |  | 2022-09-02 | 15,1 |  | 4.3.4 |  | 13 | 0 | 0 | 0 |  |  |  |  |  |
| 863 | org.springdoc:springdoc-openapi-starter-common | org.springdoc.openapi.common ⚙️ | 2026-04-11 | 4,5 | 4,5 | 2.8.17 | 2.8.17 | 46 | 48 | 14 | 16 | 22 | 193,2 | 3,6 | 1,3 | - |
| 864 | com.googlecode.concurrent-trees:concurrent-trees 🚩 |  | 2017-07-14 | 14,1 |  | 2.6.1 |  | 10 | 0 | 0 | 0 |  |  |  |  |  |
| ~~865~~ | ~~org.apache.maven.resolver:maven-resolver-spi~~ | ~~org.apache.maven.resolver.spi ✳️~~ | ~~2026-06-30~~ | ~~9,6~~ | ~~9,1~~ | ~~2.0.20~~ | ~~2.0.20~~ | ~~74~~ | ~~74~~ | ~~11~~ | ~~12~~ | ~~31~~ | ~~402,3~~ | ~~15,6~~ | ~~1,3~~ | ~~-~~ |
| 866 | org.springframework.boot:spring-boot-devtools | spring.boot.devtools ⚙️ | 2026-06-25 | 10,7 | 8,5 | 3.5.16 | 3.5.16 | 265 | 225 | 35 | 35 | 336 | 3788,6 🔺 | 132,2 🔺 | 2,9 | 🔺 files, size |
| 867 | org.jetbrains.kotlin:kotlin-util-klib-metadata |  | 2026-06-25 | 6,7 |  | 2.4.10-RC |  | 158 | 0 | 32 | 0 | 175 | 3924,5 🔺 | 568,3 🔺 | 3,2 | 🔺 files, size |
| ~~868~~ | ~~org.apache.maven.resolver:maven-resolver-impl~~ | ~~org.apache.maven.resolver.impl ⚙️~~ | ~~2026-06-30~~ | ~~9,6~~ | ~~9,1~~ | ~~2.0.20~~ | ~~2.0.20~~ | ~~74~~ | ~~74~~ | ~~11~~ | ~~12~~ | ~~31~~ | ~~402,3~~ | ~~15,6~~ | ~~1,3~~ | ~~-~~ |
| 869 | commons-fileupload:commons-fileupload ⚠️ | org.apache.commons.fileupload ✳️ | 2025-06-05 | 20,7 | 1,2 | 1.6.0 | 1.6.0 | 15 | 1 | 0 | 0 |  |  |  |  |  |
| 870 | com.google.api:gax-grpc |  | 2026-07-30 | 9,3 |  | 2.83.0 |  | 225 | 0 | 23 | 0 | 5 | 40,9 | 21,2 🔺 | 4,8 | 🔺 size |
| 871 | org.conscrypt:conscrypt-openjdk-uber | org.conscrypt ⚙️ | 2026-06-29 | 9,5 | 7,8 | 2.6-alpha5 | 2.6-alpha5 | 39 | 17 | 5 | 7 | 3 | 84,9 | 18,5 | 0,6 | - |
| 872 | com.google.devtools.ksp:symbol-processing-gradle-plugin |  | 2026-08-03 | 5,0 |  | 2.3.11 |  | 158 | 0 | 21 | 0 | 8 | 270,0 | 194,4 🔺 | 1,8 | 🔺 size |
| 873 | software.amazon.awssdk:http-auth-aws-eventstream | software.amazon.awssdk.http.auth.aws.eventstream ⚙️ | 2026-08-07 | 2,8 | 2,8 | 2.51.3 | 2.51.3 | 681 | 687 | 233 | 239 | 520 | 7784,8 🔺 | 3309,0 🔺 | 27,7 🔺 | 🔺 files, size, releases |
| 874 | io.grpc:grpc-auth | io.grpc.auth ⚙️ | 2026-06-23 | 11,2 | 2,9 | 1.82.1 | 1.82.1 | 191 | 49 | 13 | 18 | 38 | 619,6 | 203,5 🔺 | 1,4 | 🔺 size |
| 875 | com.google.api:gax-httpjson |  | 2026-07-30 | 8,8 |  | 2.83.0 |  | 203 | 0 | 23 | 0 | 5 | 40,9 | 21,2 🔺 | 4,8 | 🔺 size |
| 876 | io.mockk:mockk-dsl-jvm |  | 2026-05-29 | 8,8 |  | 1.14.11 |  | 104 | 0 | 4 | 0 | 17 | 430,5 | 1,9 | 0,3 | - |
| 877 | org.junit.vintage:junit-vintage-engine | org.junit.vintage.engine ✳️ | 2026-08-07 | 10,1 | 9,1 | 6.1.3 | 6.1.3 | 112 | 109 | 18 | 19 | 1 | 47,8 | 2,6 | 1,5 | - |
| 878 | javax.ws.rs:javax.ws.rs-api 🚩 | java.ws.rs 🏷️ | 2018-08-24 | 14,5 | 9,2 | 2.1.1 | 2.1.1 | 32 | 3 | 0 | 0 |  |  |  |  |  |
| ~~879~~ | ~~org.sonarsource.scanner.api:sonar-scanner-api-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| ~~880~~ | ~~org.apache.maven:maven-resolver-provider~~ | ~~-~~ | ~~2026-07-30~~ | ~~9,5~~ | ~~-~~ | ~~4.0.0-rc-6~~ | ~~-~~ | ~~55~~ | ~~1~~ | ~~7~~ | ~~1~~ | ~~36~~ | ~~360,2~~ | ~~12,5~~ | ~~0,8~~ | ~~-~~ |
| ~~881~~ | ~~com.google.cloud:libraries-bom~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~402~~ | ~~249,5 🔺~~ | ~~19,3 🔺~~ | ~~67,0 🔺~~ | ~~🔺 files, size, releases~~ |
| 882 | org.glassfish.hk2:hk2-api | org.glassfish.hk2.api ⚙️ | 2026-04-28 | 15,2 | 6,2 | 4.0.1 | 4.0.1 | 270 | 17 | 3 | 3 | 54 | 1986,0 | 19,8 | 0,3 | - |
| 883 | org.scala-sbt:util-interface |  | 2026-08-07 | 10,9 |  | 2.0.6 |  | 201 | 0 | 40 | 0 | 111 | 641,8 🔺 | 61,1 🔺 | 5,5 | 🔺 files, size |
| 884 | org.glassfish.hk2:hk2-locator | org.glassfish.hk2.locator ⚙️ | 2026-04-28 | 14,3 | 6,2 | 4.0.1 | 4.0.1 | 238 | 17 | 3 | 3 | 54 | 1986,0 | 19,8 | 0,3 | - |
| 885 | io.mockk:mockk-agent-jvm |  | 2026-05-29 | 8,2 |  | 1.14.11 |  | 71 | 0 | 4 | 0 | 17 | 430,5 | 1,9 | 0,3 | - |
| 886 | com.diffplug.durian:durian-io 🚩 |  | 2016-06-27 | 10,2 |  | 1.2.0 |  | 3 | 0 | 0 | 0 | 8 | 400,0 | 1,5 | 0,2 | - |
| 887 | org.springframework.security:spring-security-test | spring.security.test ⚙️ | 2026-06-09 | 11,4 | 8,7 | 7.1.0 | 7.1.0 | 245 | 213 | 31 | 31 | 26 | 319,1 | 27,4 🔺 | 3,6 | 🔺 size |
| 888 | org.flywaydb:flyway-core |  | 2026-08-06 | 12,3 |  | 13.2.0 |  | 309 | 190 | 40 | 0 | 49 | 905,7 🔺 | 8,9 | 3,3 | 🔺 files |
| 889 | org.glassfish.hk2:hk2-utils | org.glassfish.hk2.utilities ⚙️ | 2026-04-28 | 14,3 | 6,2 | 4.0.1 | 4.0.1 | 235 | 17 | 3 | 3 | 54 | 1986,0 | 19,8 | 0,3 | - |
| 890 | com.github.jknack:handlebars | com.github.jknack.handlebars ✳️ | 2026-06-30 | 14,0 | 2,4 | 4.5.3 | 4.5.3 | 57 | 6 | 3 | 4 | 9 | 196,0 | 2,2 | 0,3 | - |
| ~~891~~ | ~~io.quarkus:quarkus-build-parent~~ | ~~-~~ | ~~2026-07-01~~ | ~~0,2~~ | ~~-~~ | ~~3.37.1~~ | ~~-~~ | ~~13~~ | ~~0~~ | ~~13~~ | ~~0~~ | ~~1138~~ | ~~8428,1 🔺~~ | ~~191,2 🔺~~ | ~~14,4 🔺~~ | ~~🔺 files, size, releases~~ |
| 892 | com.sun.jersey.contribs:jersey-contribs |  |  |  |  |  |  | 0 | 0 | 0 | 0 |  |  |  |  |  |
| ~~893~~ | ~~io.quarkus:quarkus-parent~~ | ~~-~~ | ~~2026-07-01~~ | ~~0,2~~ | ~~-~~ | ~~3.37.1~~ | ~~-~~ | ~~13~~ | ~~0~~ | ~~13~~ | ~~0~~ | ~~1138~~ | ~~8428,1 🔺~~ | ~~191,2 🔺~~ | ~~14,4 🔺~~ | ~~🔺 files, size, releases~~ |
| 894 | software.amazon.awssdk:sts | software.amazon.awssdk.services.sts ⚙️ | 2026-08-07 | 9,1 | 7,7 | 2.51.3 | 2.51.3 | 1813 | 1807 | 233 | 239 | 520 | 7784,8 🔺 | 3309,0 🔺 | 27,7 🔺 | 🔺 files, size, releases |
| 895 | io.grpc:grpc-grpclb | io.grpc.grpclb ⚙️ | 2026-06-23 | 10,6 | 2,9 | 1.82.1 | 1.82.1 | 185 | 49 | 13 | 18 | 38 | 619,6 | 203,5 🔺 | 1,4 | 🔺 size |
| 896 | org.seleniumhq.selenium:selenium-api | org.seleniumhq.selenium.api 🏷️ | 2026-06-16 | 15,5 | 8,0 | 4.45.0 | 4.45.0 | 184 | 87 | 10 | 11 | 35 | 416,3 | 72,9 🔺 | 1,1 | 🔺 size |
| 897 | com.nimbusds:lang-tag 🚩 |  | 2022-07-06 | 13,4 |  | 1.7 |  | 11 | 0 | 0 | 0 | 11 | 30,4 | 7,4 | 4,2 | - |
| 898 | org.springframework.boot:spring-boot-starter-cache | spring.boot.starter.cache ⚙️ | 2026-06-25 | 10,7 | 8,5 | 3.5.16 | 3.5.16 | 265 | 225 | 35 | 35 | 336 | 3788,6 🔺 | 132,2 🔺 | 2,9 | 🔺 files, size |
| 899 | com.github.java-json-tools:jackson-coreutils 🚩 |  | 2020-05-27 | 8,3 |  | 2.0 |  | 5 | 0 | 0 | 0 |  |  |  |  |  |
| 900 | org.dom4j:dom4j ⚠️ | org.dom4j ⚙️ | 2025-06-30 | 11,1 | 3,6 | 2.2.0 | 2.2.0 | 11 | 3 | 0 | 0 |  |  |  |  |  |
| 901 | org.sonarsource.scanner.api:sonar-scanner-api 🚩 |  | 2022-12-22 | 10,3 |  | 2.16.3.1081 |  | 14 | 0 | 0 | 0 |  |  |  |  |  |
| 902 | com.google.auto:auto-common 🚩 |  | 2023-06-12 | 12,2 |  | 1.2.2 |  | 19 | 0 | 0 | 0 |  |  |  |  |  |
| 903 | com.lmax:disruptor ⚠️ | com.lmax.disruptor 🏷️ | 2023-09-29 | 13,6 | 5,3 | 4.0.0 | 4.0.0 | 29 | 4 | 0 | 0 | 3 | 52,5 | 2,4 | 0,3 | - |
| 904 | org.codehaus.groovy:groovy-xml ⚠️ | org.codehaus.groovy.xml ⚙️ | 2025-05-27 | 14,2 | 8,5 | 3.0.25 | 3.0.25 | 151 | 65 | 0 | 0 |  |  |  |  |  |
| 905 | io.micrometer:micrometer-registry-prometheus | micrometer.registry.prometheus ⚙️ | 2026-06-08 | 8,9 | 6,3 | 1.17.0 | 1.17.0 | 249 | 181 | 27 | 27 | 42 | 721,7 🔺 | 12,2 | 4,9 | 🔺 files |
| 906 | org.seleniumhq.selenium:selenium-remote-driver | org.seleniumhq.selenium.remote_driver 🏷️ | 2026-06-16 | 15,5 | 8,0 | 4.45.0 | 4.45.0 | 184 | 87 | 10 | 11 | 35 | 416,3 | 72,9 🔺 | 1,1 | 🔺 size |
| 907 | io.delta:delta-storage |  | 2026-06-18 | 4,3 |  | 4.3.0 |  | 26 | 0 | 5 | 0 | 28 | 196,8 | 61,3 🔺 | 2,2 | 🔺 size |
| 908 | io.grpc:grpc-alts | io.grpc.alts ⚙️ | 2026-06-23 | 8,4 | 2,9 | 1.82.1 | 1.82.1 | 156 | 49 | 13 | 18 | 38 | 619,6 | 203,5 🔺 | 1,4 | 🔺 size |
| 909 | org.glassfish.jersey.inject:jersey-hk2 |  | 2026-06-11 | 9,2 |  | 3.1.12 |  | 82 | 6 | 9 | 6 | 3 | 51,3 | 0,9 | 0,8 | - |
| 910 | org.springdoc:springdoc-openapi-starter-webmvc-api | org.springdoc.openapi.webmvc.core ⚙️ | 2026-04-11 | 4,5 | 4,5 | 2.8.17 | 2.8.17 | 46 | 48 | 14 | 16 | 22 | 193,2 | 3,6 | 1,3 | - |
| 911 | software.amazon.awssdk:crt-core | software.amazon.awssdk.crtcore ⚙️ | 2026-08-07 | 3,4 | 3,4 | 2.51.3 | 2.51.3 | 816 | 822 | 233 | 239 | 520 | 7784,8 🔺 | 3309,0 🔺 | 27,7 🔺 | 🔺 files, size, releases |
| 912 | org.seleniumhq.selenium:selenium-support | org.seleniumhq.selenium.support 🏷️ | 2026-06-16 | 16,7 | 7,3 | 4.45.0 | 4.45.0 | 191 | 83 | 10 | 11 | 35 | 416,3 | 72,9 🔺 | 1,1 | 🔺 size |
| 913 | com.nimbusds:content-type ⚠️ |  | 2023-11-05 | 6,5 |  | 2.3 |  | 5 | 0 | 0 | 0 | 11 | 30,4 | 7,4 | 4,2 | - |
| 914 | io.prometheus:simpleclient 🚩 |  | 2022-06-15 | 11,7 |  | 0.16.0 |  | 40 | 0 | 0 | 0 | 30 | 586,8 | 15,9 | 0,8 | - |
| 915 | io.swagger:swagger-core ⚠️ | io.swagger.core ⚙️ | 2025-05-15 | 11,2 | 3,4 | 1.6.16 | 1.6.16 | 45 | 7 | 0 | 0 | 8 | 63,7 | 28,6 | 1,1 | - |
| 916 | com.google.devtools.ksp:symbol-processing-common-deps |  | 2026-08-03 | 2,8 |  | 2.3.11 |  | 100 | 0 | 21 | 0 | 8 | 270,0 | 194,4 🔺 | 1,8 | 🔺 size |
| 917 | software.amazon.ion:ion-java 🚩 | software.amazon.ion ⚙️ | 2019-09-25 | 10,3 | 8,1 | 1.5.1 | 1.5.1 | 13 | 6 | 0 | 0 |  |  |  |  |  |
| 918 | org.ccil.cowan.tagsoup:tagsoup 🚩 |  | 2011-08-22 | 20,6 |  | 1.2.1 |  | 5 | 0 | 0 | 0 |  |  |  |  |  |
| 919 | io.netty:netty-all | io.netty.all ⚙️ | 2026-06-02 | 13,5 | 8,5 | 4.1.135.Final | 4.1.135.Final | 249 | 112 | 24 | 14 | 66 | 1150,9 🔺 | 47,5 🔺 | 3,2 | 🔺 files, size |
| 920 | com.google.googlejavaformat:google-java-format | com.google.googlejavaformat ⚙️ | 2026-07-30 | 10,8 | 8,2 | 1.36.1 | 1.36.1 | 45 | 38 | 10 | 10 | 1 | 30,0 | 4,6 | 0,8 | - |
| 921 | com.diffplug.spotless:spotless-plugin-gradle |  | 2026-06-29 | 9,6 |  | 8.8.0 |  | 167 | 0 | 11 | 0 | 4 | 66,7 | 1,1 | 2,8 | - |
| ~~922~~ | ~~org.apache.maven.plugins:maven-source-plugin~~ | ~~-~~ | ~~2025-11-22~~ | ~~20,3~~ | ~~-~~ | ~~3.4.0~~ | ~~-~~ | ~~23~~ | ~~0~~ | ~~1~~ | ~~0~~ | ~~35~~ | ~~44,4~~ | ~~2,7~~ | ~~3,1~~ | ~~-~~ |
| 923 | org.glassfish.jersey.media:project |  | 2026-06-11 | 0,2 |  | 3.1.12 |  | 1 | 0 | 1 | 0 | 13 | 283,3 | 4,0 | 0,8 | - |
| 924 | io.jsonwebtoken:jjwt-api |  | 2025-08-20 | 8,0 |  | 0.13.0 |  | 24 | 0 | 2 | 0 | 6 | 96,0 | 3,5 | 0,2 | - |
| 925 | org.codehaus.janino:janino ⚠️ | org.codehaus.janino ⚙️ | 2024-02-06 | 16,3 | 3,1 | 3.1.12 | 3.1.12 | 37 | 3 | 0 | 0 |  |  |  |  |  |
| 926 | net.logstash.logback:logstash-logback-encoder | logstash.logback.encoder ⚙️ | 2025-10-26 | 13,4 | 6,7 | 9.0 | 9.0 | 55 | 14 | 1 | 1 | 1 | 24,0 | 2,6 | 0,1 | - |
| 927 | org.apache.hadoop:hadoop-annotations |  | 2026-03-24 | 14,5 |  | 3.5.0 |  | 83 | 0 | 3 | 0 | 85 | 2036,0 | 336,5 🔺 | 0,3 | 🔺 size |
| ~~928~~ | ~~org.apache.maven.wagon:wagon-providers~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ |
| 929 | com.google.cloud:google-cloud-core |  | 2026-07-07 | 9,9 |  | 2.72.0 |  | 317 | 0 | 22 | 0 | 402 | 249,5 🔺 | 19,3 🔺 | 67,0 🔺 | 🔺 files, size, releases |
| 930 | org.codehaus.janino:commons-compiler ⚠️ | org.codehaus.commons.compiler ⚙️ | 2024-02-06 | 14,6 | 3,1 | 3.1.12 | 3.1.12 | 35 | 3 | 0 | 0 |  |  |  |  |  |
| ~~931~~ | ~~org.mongodb:mongodb-driver-bom~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~18~~ | ~~148,8~~ | ~~56,7 🔺~~ | ~~2,4~~ | ~~🔺 size~~ |
| 932 | com.github.java-json-tools:json-schema-core 🚩 |  | 2020-05-27 | 9,2 |  | 1.2.14 |  | 7 | 0 | 0 | 0 |  |  |  |  |  |
| 933 | com.github.java-json-tools:json-schema-validator 🚩 |  | 2020-05-27 | 9,2 |  | 2.2.14 |  | 8 | 0 | 0 | 0 |  |  |  |  |  |
| 934 | io.qameta.allure:allure-commandline |  | 2026-07-01 | 5,3 |  | 2.44.0 |  | 49 | 0 | 13 | 0 | 50 | 579,7 🔺 | 31,0 | 2,3 | 🔺 files |
| 935 | io.mockk:mockk-jvm |  | 2026-05-29 | 4,0 |  | 1.14.11 |  | 27 | 0 | 4 | 0 | 17 | 430,5 | 1,9 | 0,3 | - |
| 936 | org.apache.cxf:cxf |  |  |  |  |  |  | 0 | 0 | 0 | 0 | 100 | 1907,6 🔺 | 96,8 🔺 | 1,5 | 🔺 files, size |
| 937 | org.springframework.cloud:spring-cloud-context |  | 2026-06-11 | 11,3 |  | 5.0.2 |  | 97 | 0 | 12 | 0 | 243 | 1049,0 🔺 | 161,7 🔺 | 3,3 | 🔺 files, size |
| 938 | org.glassfish.hk2.external:aopalliance-repackaged | org.aopalliance ⚙️ | 2026-04-28 | 12,7 | 6,2 | 4.0.1 | 4.0.1 | 129 | 17 | 3 | 3 | 1 | 36,0 | 0,3 | 0,3 | - |
| 939 | org.apache.lucene:lucene-core | org.apache.lucene.core ✳️ | 2026-06-25 | 20,2 | 4,7 | 10.5.0 | 10.5.0 | 156 | 31 | 6 | 6 | 33 | 1313,3 | 73,9 | 0,5 | - |
| ~~940~~ | ~~com.google.googlejavaformat:google-java-format-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~1~~ | ~~30,0~~ | ~~4,6~~ | ~~0,8~~ | ~~-~~ |
| 941 | org.seleniumhq.selenium:selenium-firefox-driver | org.seleniumhq.selenium.firefox_driver 🏷️ | 2026-06-16 | 16,7 | 8,0 | 4.45.0 | 4.45.0 | 191 | 87 | 10 | 11 | 35 | 416,3 | 72,9 🔺 | 1,1 | 🔺 size |
| ~~942~~ | ~~com.google.api:gax-parent~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~5~~ | ~~40,9~~ | ~~21,2 🔺~~ | ~~4,8~~ | ~~🔺 size~~ |
| 943 | io.github.x-stream:mxparser | io.github.xstream.mxparser ⚙️ | 2025-10-12 | 5,6 | 5,6 | 1.2.3 | 1.2.3 | 4 | 4 | 1 | 1 | 1 | 24,0 | 0,1 | 0,1 | - |
| 944 | org.apache.pdfbox:fontbox | org.apache.fontbox ⚙️ | 2026-03-12 | 16,9 | 6,9 | 2.0.36 | 2.0.36 | 81 | 33 | 4 | 6 | 12 | 125,0 | 45,5 | 0,4 | - |
| 945 | com.github.java-json-tools:btf 🚩 |  | 2020-01-04 | 6,6 |  | 1.3 |  | 1 | 0 | 0 | 0 |  |  |  |  |  |
| 946 | com.github.java-json-tools:msg-simple 🚩 |  | 2020-01-06 | 6,6 |  | 1.2 |  | 1 | 0 | 0 | 0 |  |  |  |  |  |
| 947 | org.mapstruct:mapstruct | org.mapstruct ⚙️ | 2026-06-27 | 13,2 | 9,1 | 1.7.0.Beta2 | 1.7.0.Beta2 | 49 | 32 | 2 | 2 | 4 | 63,0 | 6,0 | 0,2 | - |
| 948 | io.mockk:mockk-agent-api-jvm |  | 2026-05-29 | 4,0 |  | 1.14.11 |  | 29 | 0 | 4 | 0 | 17 | 430,5 | 1,9 | 0,3 | - |
| 949 | io.mockk:mockk-core-jvm |  | 2026-05-29 | 3,9 |  | 1.14.11 |  | 27 | 0 | 4 | 0 | 17 | 430,5 | 1,9 | 0,3 | - |
| 950 | javax.ws.rs:jsr311-api 🚩 |  | 2010-05-20 | 18,9 |  | 1.1-ea |  | 14 | 0 | 0 | 0 |  |  |  |  |  |
| 951 | io.ktor:ktor-utils-jvm | io.ktor.utils ⚙️ | 2026-06-25 | 7,7 | 4,4 | 3.5.1 | 3.5.1 | 97 | 56 | 11 | 12 | 1849 | 63257,3 🔺 | 957,4 🔺 | 1,0 | 🔺 files, size |
| 952 | org.scala-lang.modules:scala-parser-combinators_2.12 ⚠️ | scala.util.parsing ⚙️ | 2024-04-18 | 9,8 | 4,9 | 2.4.0 | 2.4.0 | 17 | 5 | 0 | 0 | 14 | 112,0 | 8,3 | 0,3 | - |
| 953 | io.ktor:ktor-http-jvm | io.ktor.http ⚙️ | 2026-06-25 | 7,7 | 4,4 | 3.5.1 | 3.5.1 | 97 | 56 | 11 | 12 | 1849 | 63257,3 🔺 | 957,4 🔺 | 1,0 | 🔺 files, size |
| ~~954~~ | ~~org.apache.maven.doxia:doxia-sitetools~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~13~~ | ~~312,0~~ | ~~7,8~~ | ~~0,1~~ | ~~-~~ |
| 955 | io.prometheus:simpleclient_common 🚩 |  | 2022-06-15 | 11,7 |  | 0.16.0 |  | 40 | 0 | 0 | 0 | 30 | 586,8 | 15,9 | 0,8 | - |
| 956 | org.springframework.security:spring-security-oauth2-resource-server | spring.security.oauth2.resource.server ⚙️ | 2026-06-09 | 7,9 | 7,9 | 7.1.0 | 7.1.0 | 193 | 193 | 31 | 31 | 26 | 319,1 | 27,4 🔺 | 3,6 | 🔺 size |
| 957 | org.jetbrains.kotlinx:kotlinx-coroutines-slf4j | kotlinx.coroutines.slf4j 🏷️ | 2026-05-07 | 8,0 | 3,4 | 1.11.0 | 1.11.0 | 97 | 20 | 3 | 3 | 768 | 744,5 🔺 | 91,4 🔺 | 57,3 🔺 | 🔺 files, size, releases |
| 958 | org.seleniumhq.selenium:selenium-chrome-driver | org.seleniumhq.selenium.chrome_driver 🏷️ | 2026-06-16 | 16,7 | 8,0 | 4.45.0 | 4.45.0 | 191 | 87 | 10 | 11 | 35 | 416,3 | 72,9 🔺 | 1,1 | 🔺 size |
| 959 | org.apache.httpcomponents:httpcore-nio 🚩 | org.apache.httpcomponents.httpcore.nio ⚙️ | 2022-11-26 | 19,1 | 7,6 | 4.4.16 | 4.4.16 | 50 | 6 | 0 | 0 |  |  |  |  |  |
| 960 | org.apache.poi:poi-ooxml-lite | org.apache.poi.ooxml.schemas 🏷️ | 2025-11-26 | 5,6 | 5,6 | 5.5.1 | 5.5.1 | 13 | 13 | 2 | 2 | 7 | 260,0 | 70,2 | 0,2 | - |
| 961 | com.squareup:kotlinpoet-jvm | com.squareup.kotlinpoet ⚙️ | 2026-03-27 | 2,7 | 2,7 | 2.3.0 | 2.3.0 | 12 | 12 | 1 | 1 | 9 | 83,5 | 36,4 | 1,3 | - |
| 962 | com.esotericsoftware:minlog 🚩 | com.esotericsoftware.minlog ⚙️ | 2018-12-28 | 12,7 | 7,6 | 1.3.1 | 1.3.1 | 4 | 1 | 0 | 0 |  |  |  |  |  |
| 963 | commons-httpclient:commons-httpclient 🚩 |  | 2007-08-21 | 21,3 |  | 3.1 |  | 31 | 0 | 0 | 0 |  |  |  |  |  |
| 964 | io.swagger.parser.v3:swagger-parser-project |  | 2026-06-23 | 0,2 |  | 2.1.45 |  | 3 | 0 | 3 | 0 | 7 | 145,3 | 8,0 | 1,2 | - |
| ~~965~~ | ~~org.jooq:jooq-parent~~ | ~~-~~ | ~~2026-08-04~~ | ~~0,2~~ | ~~-~~ | ~~3.21.7~~ | ~~-~~ | ~~9~~ | ~~0~~ | ~~9~~ | ~~0~~ | ~~27~~ | ~~464,3 🔺~~ | ~~29,6 🔺~~ | ~~3,3~~ | ~~🔺 files, size~~ |
| 966 | net.minidev:parent |  |  |  |  |  |  | 0 | 0 | 0 | 0 |  |  |  |  |  |
| 967 | org.robolectric:android-all-instrumented |  | 2025-08-22 | 5,5 |  | 15-robolectric-13954326-i7 |  | 112 | 0 | 2 | 0 | 20 | 284,4 | 111,5 🔺 | 0,8 | 🔺 size |
| 968 | org.springdoc:springdoc-openapi-starter-webmvc-ui | org.springdoc.openapi.ui ⚙️ | 2026-04-11 | 4,5 | 4,5 | 2.8.17 | 2.8.17 | 46 | 48 | 14 | 16 | 22 | 193,2 | 3,6 | 1,3 | - |
| 969 | io.ktor:ktor-io-jvm | io.ktor.io ⚙️ | 2026-06-25 | 6,7 | 4,4 | 3.5.1 | 3.5.1 | 79 | 56 | 11 | 12 | 1849 | 63257,3 🔺 | 957,4 🔺 | 1,0 | 🔺 files, size |
| 970 | dk.brics.automaton:automaton 🚩 |  | 2011-12-04 | 14,7 |  | 1.11-8 |  | 1 | 0 | 0 | 0 |  |  |  |  |  |
| 971 | io.jsonwebtoken:jjwt-impl |  | 2025-08-20 | 8,0 |  | 0.13.0 |  | 24 | 0 | 2 | 0 | 6 | 96,0 | 3,5 | 0,2 | - |
| 972 | org.mongodb:bson | org.mongodb.bson ⚙️ | 2026-05-28 | 15,8 | 8,3 | 5.8.0 | 5.8.0 | 220 | 135 | 13 | 16 | 18 | 148,8 | 56,7 🔺 | 2,4 | 🔺 size |
| 973 | org.springframework.kafka:spring-kafka | spring.kafka ⚙️ | 2026-06-09 | 10,1 | 8,9 | 4.0.6 | 4.0.6 | 248 | 216 | 22 | 22 | 3 | 41,1 | 8,9 | 1,8 | - |
| 974 | org.jetbrains.kotlinx:kotlinx-coroutines-test | kotlinx.coroutines.test.artifact_disambiguating_module ⚙️ | 2026-05-07 | 7,7 | 0,3 | 1.11.0 | 1.11.0 | 79 | 3 | 3 | 3 | 768 | 744,5 🔺 | 91,4 🔺 | 57,3 🔺 | 🔺 files, size, releases |
| 975 | com.samskivert:jmustache ⚠️ | com.samskivert.jmustache ✳️ | 2023-11-30 | 15,8 | 7,1 | 1.16 | 1.16 | 17 | 2 | 0 | 0 | 2 | 24,0 | 6,4 | 0,8 | - |
| 976 | io.rest-assured:rest-assured-common |  | 2026-01-16 | 10,2 |  | 5.5.7 |  | 44 | 0 | 3 | 0 | 14 | 336,0 | 9,5 | 0,3 | - |
| 977 | org.apache.pdfbox:pdfbox | org.apache.pdfbox ⚙️ | 2026-03-12 | 16,9 | 6,9 | 2.0.36 | 2.0.36 | 81 | 33 | 4 | 6 | 12 | 125,0 | 45,5 | 0,4 | - |
| 978 | io.rest-assured:json-path |  | 2026-01-16 | 10,2 |  | 5.5.7 |  | 44 | 0 | 3 | 0 | 14 | 336,0 | 9,5 | 0,3 | - |
| 979 | commons-pool:commons-pool 🚩 |  | 2012-01-09 | 20,8 |  | 1.6 |  | 22 | 0 | 0 | 0 |  |  |  |  |  |
| 980 | io.github.resilience4j:resilience4j-core | io.github.resilience4j.core ⚙️ | 2026-03-14 | 9,2 | 7,2 | 2.4.0 | 2.4.0 | 32 | 21 | 1 | 1 | 28 | 1400,0 | 8,3 | 0,1 | - |
| 981 | org.scala-sbt:compiler-bridge_2.12 |  | 2026-08-06 | 9,6 |  | 1.12.1 |  | 160 | 0 | 21 | 0 | 111 | 641,8 🔺 | 61,1 🔺 | 5,5 | 🔺 files, size |
| 982 | org.codehaus.mojo:exec-maven-plugin |  | 2025-12-21 | 20,4 |  | 3.6.3 |  | 27 | 0 | 4 | 0 | 29 | 39,6 | 1,3 | 3,5 | - |
| 983 | org.jetbrains.kotlinx:kotlinx-coroutines-reactive | kotlinx.coroutines.reactive 🏷️ | 2026-05-07 | 8,9 | 3,4 | 1.11.0 | 1.11.0 | 118 | 20 | 3 | 3 | 768 | 744,5 🔺 | 91,4 🔺 | 57,3 🔺 | 🔺 files, size, releases |
| 984 | stax:stax-api 🚩 |  | 2006-03-14 | 20,7 |  | 1.0.1 |  | 2 | 0 | 0 | 0 |  |  |  |  |  |
| 985 | io.rest-assured:rest-assured |  | 2026-01-16 | 10,2 |  | 5.5.7 |  | 44 | 0 | 3 | 0 | 14 | 336,0 | 9,5 | 0,3 | - |
| 986 | io.rest-assured:xml-path |  | 2026-01-16 | 10,2 |  | 5.5.7 |  | 44 | 0 | 3 | 0 | 14 | 336,0 | 9,5 | 0,3 | - |
| 987 | org.seleniumhq.selenium:selenium-ie-driver | org.seleniumhq.selenium.ie_driver 🏷️ | 2026-06-16 | 16,7 | 8,0 | 4.45.0 | 4.45.0 | 191 | 87 | 10 | 11 | 35 | 416,3 | 72,9 🔺 | 1,1 | 🔺 size |
| 988 | org.seleniumhq.selenium:selenium-safari-driver | org.seleniumhq.selenium.safari_driver 🏷️ | 2026-06-16 | 14,3 | 8,0 | 4.45.0 | 4.45.0 | 157 | 87 | 10 | 11 | 35 | 416,3 | 72,9 🔺 | 1,1 | 🔺 size |
| 989 | org.springframework.cloud:spring-cloud-commons |  | 2026-06-11 | 11,4 |  | 5.0.2 |  | 98 | 0 | 12 | 0 | 243 | 1049,0 🔺 | 161,7 🔺 | 3,3 | 🔺 files, size |
| ~~990~~ | ~~org.apache.maven.plugins:maven-war-plugin~~ | ~~-~~ | ~~2025-11-24~~ | ~~20,3~~ | ~~-~~ | ~~3.5.1~~ | ~~-~~ | ~~27~~ | ~~0~~ | ~~2~~ | ~~0~~ | ~~35~~ | ~~44,4~~ | ~~2,7~~ | ~~3,1~~ | ~~-~~ |
| 991 | org.apache.hadoop:hadoop-yarn |  |  |  |  |  |  | 0 | 0 | 0 | 0 | 85 | 2036,0 | 336,5 🔺 | 0,3 | 🔺 size |
| 992 | org.seleniumhq.selenium:selenium-java | org.seleniumhq.selenium.java 🏷️ | 2026-06-16 | 15,1 | 6,9 | 4.45.0 | 4.45.0 | 181 | 81 | 10 | 11 | 35 | 416,3 | 72,9 🔺 | 1,1 | 🔺 size |
| 993 | org.glassfish.jersey.containers:project |  | 2026-06-11 | 0,2 |  | 3.1.12 |  | 1 | 0 | 1 | 0 | 13 | 227,3 | 2,5 | 0,8 | - |
| 994 | org.apache.felix:maven-bundle-plugin |  | 2026-02-13 | 19,1 |  | 6.0.2 |  | 42 | 0 | 1 | 0 | 8 | 32,2 | 7,5 | 2,1 | - |
| 995 | io.swagger:swagger-parser-project |  |  |  |  |  |  | 0 | 0 | 0 | 0 | 8 | 63,7 | 28,6 | 1,1 | - |
| 996 | org.hibernate:hibernate-core |  | 2026-07-05 | 17,9 |  | 7.4.4.Final |  | 312 | 114 | 31 | 2 | 31 | 114,9 | 2,2 | 2,7 | - |
| 997 | org.springframework.boot:spring-boot-starter-data-redis | spring.boot.starter.data.redis ⚙️ | 2026-06-25 | 10,0 | 8,5 | 3.5.16 | 3.5.16 | 256 | 225 | 35 | 35 | 336 | 3788,6 🔺 | 132,2 🔺 | 2,9 | 🔺 files, size |
| 998 | org.seleniumhq.selenium:selenium-edge-driver | org.seleniumhq.selenium.edge_driver 🏷️ | 2026-06-16 | 11,0 | 8,0 | 4.45.0 | 4.45.0 | 123 | 87 | 10 | 11 | 35 | 416,3 | 72,9 🔺 | 1,1 | 🔺 size |
| 999 | com.github.mifmif:generex 🚩 |  | 2016-10-30 | 11,8 |  | 1.0.2 |  | 8 | 0 | 0 | 0 |  |  |  |  |  |
| 1000 | io.jsonwebtoken:jjwt-jackson |  | 2025-08-20 | 8,0 |  | 0.13.0 |  | 24 | 0 | 2 | 0 | 6 | 96,0 | 3,5 | 0,2 | - |
