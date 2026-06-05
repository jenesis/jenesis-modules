# Maven Central most downloaded artifacts vs. modules (bleeding edge)

_Bleeding edge: the 2024 top-artifact list assessed against current data, as of 2026-06-02; nothing is cropped to a year end, and the ⚠️ / 🚩 flags use rolling 12- and 36-month windows._

**By artifact**

| Category | All listed | Libraries | Maintained |
|---|---|---|---|
| Total artifacts | 1000 (100,0%) | 868 (100,0%) | 554 (100,0%) |
| Modular artifacts | 524 (52,4%) | 514 (59,2%) | 411 (74,2%) |
| Automatic modules | 336 (33,6%) | 331 (38,1%) | 260 (46,9%) |
| Named modules | 188 (18,8%) | 183 (21,1%) | 151 (27,3%) |
| Named modules with declared version | 136 (13,6%) | 131 (15,1%) | 112 (20,2%) |
| Non-modular artifacts | 476 (47,6%) | 354 (40,8%) | 143 (25,8%) |

**By groupId**

| Category | All listed | Libraries | Maintained |
|---|---|---|---|
| Total groups | 381 (100,0%) | 361 (100,0%) | 203 (100,0%) |
| Groups without modules | 162 (42,5%) | 146 (40,4%) | 54 (26,6%) |
| Partial modularized groups | 219 (57,5%) | 215 (59,6%) | 149 (73,4%) |
| Groups with full modularization | 181 (47,5%) | 185 (51,2%) | 137 (67,5%) |
| Groups with named modules only | 86 (22,6%) | 86 (23,8%) | 64 (31,5%) |
| Groups with automatic modules only | 123 (32,3%) | 121 (33,5%) | 80 (39,4%) |
| Groups with modules and version info only | 68 (17,8%) | 68 (18,8%) | 52 (25,6%) |

Counts are absolute with the share in parentheses. "All listed" covers all 1000 artifacts; "Libraries" excludes the 132 struck rows that cannot reflect module adoption (119 Maven build-tooling, 12 POM-only parents/BOMs/dependencies, 1 placeholder artifact) and is over the remaining 868. "Maintained" further drops library artifacts with no release during the last 12 months (the ⚠️ / 🚩 flagged ones), leaving 554. Everything is as of 2026-06-02. Artifact shares are of total artifacts; group shares are of total groups. "Partial modularized groups" have at least one artifact whose latest version carries a module; "full modularization" is the subset where every artifact does; the named/automatic/version rows classify groups whose modules are exclusively of that kind.

Every figure is as of 2026-06-02, and each artifact is judged by its latest version on or before that date: the module columns describe that version's module and are blank when the latest version carries none, even if an earlier version did. Its name, type (⚙️ automatic, 🏷️ named, ✳️ named with a module-info version) and version come from that latest version; the last-publication date and latest artifact version are from the latest scanned publication on or before it. A ⚠️ marks an artifact with no release during the last 12 months, a 🚩 one that looks deserted (no release in the last three years). Ages are in years (comma-decimal) measured to that date: artifact age from the artifact's first publication, module age from its first module publication. The trailing counts are distinct versions: the "released" totals cover everything up to the year end, "in year" only the report year, and the module counts only versions that carried a Java module. Three kinds of row are shown struck through and excluded from the Libraries column, as they crowd these rankings for reasons unrelated to module adoption: Maven's own build tooling (119 rows: Maven, Plexus, Sonatype/Sisu/Aether), POM-only aggregators (12 rows: parents, BOMs and dependency imports, which ship no JAR), and hand-listed placeholder artifacts (1 row).

| Top | Artifact | Module | Last publication | Artifact age | Module age | Latest artifact version | Latest module version | Total released artifacts (all versions) | Total released modules (all versions) | Artifacts released in last 12 months | Modules released in last 12 months |
|---|---|---|---|---|---|---|---|---|---|---|---|
| ~~1~~ | ~~org.codehaus.plexus:plexus-utils~~ | ~~org.codehaus.plexus.util ⚙️~~ | ~~2026-04-01~~ | ~~20,6~~ | ~~0,2~~ | ~~3.6.1~~ | ~~3.6.1~~ | ~~83~~ | ~~2~~ | ~~2~~ | ~~2~~ |
| 2 | commons-io:commons-io 🚩 | org.apache.commons.io ⚙️ | 2021-07-10 | 20,6 | 8,6 | 2.11.0 | 2.11.0 | 22 | 6 | 0 | 0 |
| 3 | org.slf4j:slf4j-api | org.slf4j ✳️ | 2026-05-12 | 19,7 | 9,2 | 2.0.18 | 2.0.18 | 107 | 48 | 1 | 1 |
| 4 | com.google.guava:guava | com.google.common ✳️ | 2026-04-14 | 16,1 | 8,6 | 33.6.0-jre | 33.6.0-jre | 154 | 92 | 4 | 4 |
| 5 | com.fasterxml.jackson.core:jackson-core |  | 2026-05-31 | 14,3 |  | 2.22.0 |  | 185 | 94 | 19 | 18 |
| 6 | com.fasterxml.jackson.core:jackson-databind | com.fasterxml.jackson.databind ✳️ | 2026-06-01 | 14,3 | 8,7 | 2.22.0 | 2.22.0 | 226 | 112 | 18 | 18 |
| 7 | com.fasterxml.jackson.core:jackson-annotations | com.fasterxml.jackson.annotation ✳️ | 2026-05-31 | 14,3 | 8,7 | 2.22 | 2.22 | 181 | 93 | 12 | 12 |
| 8 | org.ow2.asm:asm | org.objectweb.asm ✳️ | 2026-05-23 | 14,5 | 8,9 | 9.10.1 | 9.10.1 | 45 | 33 | 4 | 4 |
| 9 | org.checkerframework:checker-qual | org.checkerframework.checker.qual 🏷️ | 2026-05-01 | 12,2 | 7,8 | 4.1.0 | 4.1.0 | 153 | 98 | 14 | 14 |
| ~~10~~ | ~~org.junit:junit-bom~~ | ~~-~~ | ~~2026-05-19~~ | ~~0,0~~ | ~~-~~ | ~~6.1.0~~ | ~~-~~ | ~~1~~ | ~~0~~ | ~~1~~ | ~~0~~ |
| 11 | commons-codec:commons-codec 🚩 | org.apache.commons.codec ⚙️ | 2020-08-28 | 20,6 | 8,6 | 1.15 | 1.15 | 16 | 5 | 0 | 0 |
| 12 | org.apache.commons:commons-lang3 🚩 | org.apache.commons.lang3 ⚙️ | 2021-02-26 | 14,9 | 9,0 | 3.12.0 | 3.12.0 | 18 | 8 | 0 | 0 |
| 13 | com.google.errorprone:error_prone_annotations | com.google.errorprone.annotations ✳️ | 2026-04-07 | 11,3 | 6,5 | 2.49.0 | 2.49.0 | 92 | 59 | 11 | 11 |
| ~~14~~ | ~~org.apache.maven:maven-artifact~~ | ~~-~~ | ~~2026-05-13~~ | ~~20,1~~ | ~~-~~ | ~~3.9.16~~ | ~~-~~ | ~~101~~ | ~~0~~ | ~~9~~ | ~~0~~ |
| ~~15~~ | ~~org.apache.maven:maven-plugin-api~~ | ~~-~~ | ~~2026-05-13~~ | ~~20,1~~ | ~~-~~ | ~~3.9.16~~ | ~~-~~ | ~~101~~ | ~~0~~ | ~~9~~ | ~~0~~ |
| ~~16~~ | ~~org.apache.maven:maven-model~~ | ~~-~~ | ~~2026-05-13~~ | ~~20,1~~ | ~~-~~ | ~~3.9.16~~ | ~~-~~ | ~~102~~ | ~~0~~ | ~~9~~ | ~~0~~ |
| 17 | org.jetbrains.kotlin:kotlin-stdlib |  | 2026-05-27 | 12,9 |  | 2.4.0-RC2 |  | 291 | 65 | 36 | 0 |
| 18 | org.apache.commons:commons-compress 🚩 | org.apache.commons.compress ⚙️ | 2021-07-09 | 17,0 | 8,6 | 1.21 | 1.21 | 25 | 8 | 0 | 0 |
| ~~19~~ | ~~org.codehaus.plexus:plexus-interpolation~~ | ~~org.codehaus.plexus.interpolation ✳️~~ | ~~2025-11-07~~ | ~~18,0~~ | ~~0,6~~ | ~~1.29~~ | ~~1.29~~ | ~~31~~ | ~~1~~ | ~~1~~ | ~~1~~ |
| ~~20~~ | ~~org.apache.maven:maven-repository-metadata~~ | ~~-~~ | ~~2026-05-13~~ | ~~20,1~~ | ~~-~~ | ~~3.9.16~~ | ~~-~~ | ~~91~~ | ~~0~~ | ~~9~~ | ~~0~~ |
| 21 | org.apache.httpcomponents:httpcore 🚩 | org.apache.httpcomponents.httpcore ⚙️ | 2022-11-26 | 18,9 | 7,9 | 4.4.16 | 4.4.16 | 50 | 7 | 0 | 0 |
| 22 | org.apache.httpcomponents:httpclient 🚩 | org.apache.httpcomponents.httpclient ⚙️ | 2022-11-30 | 18,9 | 7,9 | 4.5.14 | 4.5.14 | 55 | 9 | 0 | 0 |
| 23 | com.google.code.findbugs:jsr305 🚩 |  | 2017-03-31 | 17,2 |  | 3.0.2 |  | 10 | 0 | 0 | 0 |
| ~~24~~ | ~~org.apache.maven:maven-settings~~ | ~~-~~ | ~~2026-05-13~~ | ~~20,1~~ | ~~-~~ | ~~3.9.16~~ | ~~-~~ | ~~100~~ | ~~0~~ | ~~9~~ | ~~0~~ |
| 25 | junit:junit 🚩 | junit ⚙️ | 2021-02-13 | 20,8 | 7,5 | 4.13.2 | 4.13.2 | 32 | 8 | 0 | 0 |
| ~~26~~ | ~~org.apache.maven:maven-core~~ | ~~-~~ | ~~2026-05-13~~ | ~~20,1~~ | ~~-~~ | ~~3.9.16~~ | ~~-~~ | ~~102~~ | ~~0~~ | ~~9~~ | ~~0~~ |
| 27 | commons-logging:commons-logging |  | 2026-03-04 | 20,5 |  | 1.3.6 |  | 17 | 0 | 1 | 0 |
| ~~28~~ | ~~org.codehaus.plexus:plexus-component-annotations ⚠️~~ | ~~-~~ | ~~2023-12-24~~ | ~~18,6~~ | ~~-~~ | ~~2.2.0~~ | ~~-~~ | ~~52~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| ~~29~~ | ~~org.apache.maven.shared:maven-shared-utils 🚩~~ | ~~-~~ | ~~2023-05-11~~ | ~~13,6~~ | ~~-~~ | ~~3.4.2~~ | ~~-~~ | ~~18~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| 30 | com.google.code.gson:gson | com.google.gson ✳️ | 2026-04-23 | 17,8 | 6,7 | 2.14.0 | 2.14.0 | 44 | 16 | 3 | 3 |
| 31 | org.jetbrains.kotlin:kotlin-stdlib-common ⚠️ |  | 2023-08-23 | 9,3 |  | 1.9.10 |  | 114 | 0 | 0 | 0 |
| 32 | org.jetbrains.kotlin:kotlin-stdlib-jdk7 ⚠️ | kotlin.stdlib.jdk7 🏷️ | 2023-08-23 | 8,5 | 5,9 | 1.9.10 | 1.9.10 | 96 | 65 | 0 | 0 |
| 33 | org.jetbrains.kotlin:kotlin-stdlib-jdk8 ⚠️ | kotlin.stdlib.jdk8 🏷️ | 2023-08-23 | 8,5 | 5,9 | 1.9.10 | 1.9.10 | 96 | 65 | 0 | 0 |
| 34 | org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm | kotlinx.coroutines.core 🏷️ | 2026-05-07 | 5,2 | 3,2 | 1.11.0 | 1.11.0 | 41 | 20 | 3 | 3 |
| 35 | com.google.j2objc:j2objc-annotations | com.google.j2objc.annotations ✳️ | 2025-08-14 | 11,0 | 2,2 | 3.1 | 3.1 | 7 | 2 | 1 | 1 |
| 36 | org.junit.platform:junit-platform-commons | org.junit.platform.commons ✳️ | 2026-05-19 | 9,9 | 8,9 | 6.1.0 | 6.1.0 | 110 | 106 | 22 | 22 |
| 37 | com.fasterxml.jackson.datatype:jackson-datatype-jsr310 | com.fasterxml.jackson.datatype.jsr310 ✳️ | 2026-06-01 | 13,1 | 8,6 | 2.22.0 | 2.22.0 | 166 | 92 | 18 | 18 |
| 38 | org.junit.platform:junit-platform-engine | org.junit.platform.engine ✳️ | 2026-05-19 | 9,9 | 8,9 | 6.1.0 | 6.1.0 | 110 | 106 | 22 | 22 |
| ~~39~~ | ~~org.codehaus.plexus:plexus-archiver~~ | ~~-~~ | ~~2026-01-10~~ | ~~20,6~~ | ~~-~~ | ~~4.11.0~~ | ~~-~~ | ~~88~~ | ~~0~~ | ~~5~~ | ~~0~~ |
| ~~40~~ | ~~org.codehaus.plexus:plexus-classworlds~~ | ~~org.codehaus.plexus.classworlds ⚙️~~ | ~~2026-05-19~~ | ~~19,5~~ | ~~0,1~~ | ~~2.12.0~~ | ~~2.12.0~~ | ~~36~~ | ~~2~~ | ~~3~~ | ~~2~~ |
| ~~41~~ | ~~org.apache.maven.shared:maven-common-artifact-filters ⚠️~~ | ~~-~~ | ~~2024-06-05~~ | ~~19,5~~ | ~~-~~ | ~~3.4.0~~ | ~~-~~ | ~~15~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| ~~42~~ | ~~org.apache.maven.reporting:maven-reporting-api ⚠️~~ | ~~-~~ | ~~2024-10-01~~ | ~~20,1~~ | ~~-~~ | ~~4.0.0~~ | ~~-~~ | ~~37~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| ~~43~~ | ~~org.codehaus.plexus:plexus-io~~ | ~~-~~ | ~~2025-11-07~~ | ~~19,2~~ | ~~-~~ | ~~3.6.0~~ | ~~-~~ | ~~55~~ | ~~0~~ | ~~2~~ | ~~0~~ |
| 44 | org.junit.jupiter:junit-jupiter-api | org.junit.jupiter.api ✳️ | 2026-05-19 | 9,9 | 8,9 | 6.1.0 | 6.1.0 | 110 | 106 | 22 | 22 |
| 45 | org.ow2.asm:asm-tree | org.objectweb.asm.tree ✳️ | 2026-05-23 | 14,5 | 8,9 | 9.10.1 | 9.10.1 | 45 | 33 | 4 | 4 |
| 46 | com.squareup.okhttp3:okhttp |  | 2025-11-18 | 10,4 |  | 5.2.3 |  | 106 | 65 | 10 | 0 |
| 47 | com.google.guava:failureaccess ⚠️ | com.google.common.util.concurrent.internal ✳️ | 2025-03-19 | 7,7 | 2,6 | 1.0.3 | 1.0.3 | 4 | 2 | 0 | 0 |
| 48 | org.springframework:spring-core | spring.core ⚙️ | 2026-04-17 | 20,4 | 8,7 | 6.2.18 | 6.2.18 | 335 | 193 | 27 | 27 |
| ~~49~~ | ~~org.codehaus.plexus:plexus-container-default 🚩~~ | ~~-~~ | ~~2021-12-23~~ | ~~20,7~~ | ~~-~~ | ~~2.1.1~~ | ~~-~~ | ~~77~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| 50 | org.jetbrains.kotlin:kotlin-reflect ⚠️ | kotlin.reflect 🏷️ | 2023-08-23 | 11,2 | 5,9 | 1.9.10 | 1.9.10 | 154 | 65 | 0 | 0 |
| 51 | net.java.dev.jna:jna | com.sun.jna ⚙️ | 2025-09-30 | 16,9 | 7,7 | 5.18.1 | 5.18.1 | 53 | 22 | 2 | 2 |
| 52 | io.netty:netty-common | io.netty.common ⚙️ | 2026-05-20 | 13,9 | 8,5 | 4.1.134.Final | 4.1.134.Final | 255 | 142 | 26 | 26 |
| 53 | io.netty:netty-buffer | io.netty.buffer ⚙️ | 2026-05-20 | 13,9 | 8,5 | 4.1.134.Final | 4.1.134.Final | 255 | 142 | 26 | 26 |
| 54 | commons-lang:commons-lang 🚩 |  | 2011-01-16 | 21,1 |  | 2.6 |  | 11 | 0 | 0 | 0 |
| ~~55~~ | ~~com.google.guava:listenablefuture 🚩~~ | ~~-~~ | ~~2018-09-11~~ | ~~7,7~~ | ~~-~~ | ~~9999.0-empty-to-avoid-conflict-with-guava~~ | ~~-~~ | ~~2~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| 56 | org.ow2.asm:asm-commons | org.objectweb.asm.commons ✳️ | 2026-05-23 | 14,5 | 8,9 | 9.10.1 | 9.10.1 | 45 | 33 | 4 | 4 |
| 57 | commons-collections:commons-collections 🚩 |  | 2015-11-12 | 20,6 |  | 3.2.2 |  | 17 | 0 | 0 | 0 |
| 58 | org.jetbrains.kotlin:kotlin-gradle-plugin-api |  | 2026-05-27 | 11,2 |  | 2.4.0-RC2 |  | 240 | 0 | 36 | 0 |
| 59 | org.jetbrains:annotations | org.jetbrains.annotations 🏷️ | 2026-02-18 | 12,5 | 7,7 | 26.1.0 | 26.1.0 | 24 | 20 | 2 | 2 |
| 60 | javax.inject:javax.inject 🚩 |  | 2009-10-13 | 16,6 |  | 1 |  | 1 | 0 | 0 | 0 |
| 61 | org.junit.jupiter:junit-jupiter-engine | org.junit.jupiter.engine ✳️ | 2026-05-19 | 9,9 | 8,9 | 6.1.0 | 6.1.0 | 110 | 106 | 22 | 22 |
| 62 | io.netty:netty-transport | io.netty.transport ⚙️ | 2026-05-20 | 13,9 | 8,5 | 4.1.134.Final | 4.1.134.Final | 255 | 142 | 26 | 26 |
| 63 | com.google.protobuf:protobuf-java | com.google.protobuf ⚙️ | 2026-05-20 | 17,6 | 6,7 | 4.34.2 | 4.34.2 | 215 | 147 | 23 | 23 |
| 64 | io.netty:netty-codec | io.netty.codec ⚙️ | 2026-05-20 | 13,9 | 8,5 | 4.1.134.Final | 4.1.134.Final | 255 | 142 | 26 | 26 |
| 65 | io.netty:netty-handler | io.netty.handler ⚙️ | 2026-05-20 | 13,9 | 8,5 | 4.1.134.Final | 4.1.134.Final | 255 | 142 | 26 | 26 |
| ~~66~~ | ~~org.apache.maven.doxia:doxia-sink-api~~ | ~~-~~ | ~~2026-03-17~~ | ~~20,1~~ | ~~-~~ | ~~2.1.0~~ | ~~-~~ | ~~38~~ | ~~0~~ | ~~1~~ | ~~0~~ |
| 67 | io.netty:netty-resolver | io.netty.resolver ⚙️ | 2026-05-20 | 11,3 | 8,5 | 4.1.134.Final | 4.1.134.Final | 173 | 142 | 26 | 26 |
| 68 | com.fasterxml.jackson.module:jackson-module-parameter-names | com.fasterxml.jackson.module.paramnames ✳️ | 2026-06-01 | 12,0 | 8,6 | 2.22.0 | 2.22.0 | 151 | 92 | 18 | 18 |
| 69 | org.opentest4j:opentest4j ⚠️ | org.opentest4j ✳️ | 2023-07-06 | 10,3 | 8,9 | 1.3.0 | 1.3.0 | 12 | 9 | 0 | 0 |
| 70 | org.apiguardian:apiguardian-api 🚩 | org.apiguardian.api ✳️ | 2021-06-27 | 8,7 | 8,7 | 1.1.2 | 1.1.2 | 4 | 4 | 0 | 0 |
| ~~71~~ | ~~org.apache.maven:maven-model-builder~~ | ~~-~~ | ~~2026-05-13~~ | ~~16,6~~ | ~~-~~ | ~~3.9.16~~ | ~~-~~ | ~~78~~ | ~~0~~ | ~~9~~ | ~~0~~ |
| 72 | net.bytebuddy:byte-buddy | net.bytebuddy ✳️ | 2026-03-31 | 12,1 | 9,1 | 1.18.8 | 1.18.8 | 315 | 176 | 15 | 15 |
| 73 | org.yaml:snakeyaml | org.yaml.snakeyaml ✳️ | 2026-02-26 | 16,8 | 7,3 | 2.6 | 2.6 | 37 | 17 | 2 | 2 |
| ~~74~~ | ~~org.apache.maven:maven-settings-builder~~ | ~~-~~ | ~~2026-05-13~~ | ~~15,8~~ | ~~-~~ | ~~3.9.16~~ | ~~-~~ | ~~72~~ | ~~0~~ | ~~9~~ | ~~0~~ |
| 75 | com.squareup.okio:okio |  | 2026-03-11 | 12,1 |  | 3.17.0 |  | 87 | 28 | 9 | 0 |
| 76 | org.springframework:spring-beans | spring.beans ⚙️ | 2026-04-17 | 20,4 | 8,7 | 6.2.18 | 6.2.18 | 325 | 193 | 27 | 27 |
| 77 | org.springframework:spring-jcl | spring.jcl ⚙️ | 2026-04-17 | 8,7 | 8,7 | 6.2.18 | 6.2.18 | 173 | 173 | 12 | 12 |
| ~~78~~ | ~~org.apache.maven:maven-artifact-manager 🚩~~ | ~~-~~ | ~~2010-02-12~~ | ~~20,1~~ | ~~-~~ | ~~2.0.11~~ | ~~-~~ | ~~21~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| 79 | org.hamcrest:hamcrest-core ⚠️ | org.hamcrest.core.deprecated ⚙️ | 2024-08-01 | 18,9 | 7,5 | 3.0 | 3.0 | 12 | 7 | 0 | 0 |
| 80 | io.netty:netty-codec-http | io.netty.codec.http ⚙️ | 2026-05-20 | 13,9 | 8,5 | 4.1.134.Final | 4.1.134.Final | 255 | 142 | 26 | 26 |
| 81 | commons-beanutils:commons-beanutils 🚩 |  | 2019-07-28 | 20,6 |  | 1.9.4 |  | 21 | 0 | 0 | 0 |
| 82 | com.fasterxml.jackson.datatype:jackson-datatype-jdk8 | com.fasterxml.jackson.datatype.jdk8 ✳️ | 2026-06-01 | 11,6 | 8,6 | 2.22.0 | 2.22.0 | 148 | 92 | 18 | 18 |
| 83 | org.springframework:spring-context | spring.context ⚙️ | 2026-04-17 | 20,4 | 8,7 | 6.2.18 | 6.2.18 | 336 | 193 | 27 | 27 |
| ~~84~~ | ~~org.apache.maven:maven-project 🚩~~ | ~~-~~ | ~~2010-02-12~~ | ~~20,1~~ | ~~-~~ | ~~2.0.11~~ | ~~-~~ | ~~24~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| ~~85~~ | ~~org.apache.maven:maven-profile 🚩~~ | ~~-~~ | ~~2010-02-12~~ | ~~20,1~~ | ~~-~~ | ~~2.0.11~~ | ~~-~~ | ~~20~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| 86 | org.apache.logging.log4j:log4j-api | org.apache.logging.log4j ✳️ | 2026-05-02 | 13,8 | 8,5 | 2.26.0 | 2.26.0 | 75 | 42 | 6 | 6 |
| 87 | org.springframework:spring-aop | spring.aop ⚙️ | 2026-04-17 | 20,4 | 8,7 | 6.2.18 | 6.2.18 | 335 | 193 | 27 | 27 |
| 88 | jakarta.xml.bind:jakarta.xml.bind-api | jakarta.xml.bind ✳️ | 2026-05-02 | 7,4 | 7,4 | 4.1.0-M1 | 4.1.0-M1 | 17 | 17 | 4 | 4 |
| ~~89~~ | ~~org.eclipse.aether:aether-util 🚩~~ | ~~-~~ | ~~2016-02-03~~ | ~~13,7~~ | ~~-~~ | ~~1.1.0~~ | ~~-~~ | ~~10~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| 90 | org.springframework:spring-expression | spring.expression ⚙️ | 2026-04-17 | 16,5 | 8,7 | 6.2.18 | 6.2.18 | 286 | 193 | 27 | 27 |
| 91 | com.fasterxml.jackson.dataformat:jackson-dataformat-yaml | com.fasterxml.jackson.dataformat.yaml ✳️ | 2026-06-01 | 14,2 | 8,6 | 2.22.0 | 2.22.0 | 174 | 92 | 18 | 18 |
| ~~92~~ | ~~org.apache.maven:maven-plugin-registry 🚩~~ | ~~-~~ | ~~2010-02-12~~ | ~~20,1~~ | ~~-~~ | ~~2.0.11~~ | ~~-~~ | ~~20~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| ~~93~~ | ~~org.apache.maven.plugins:maven-compiler-plugin~~ | ~~-~~ | ~~2026-01-27~~ | ~~20,2~~ | ~~-~~ | ~~4.0.0-beta-4~~ | ~~-~~ | ~~38~~ | ~~0~~ | ~~4~~ | ~~0~~ |
| 94 | commons-cli:commons-cli 🚩 |  | 2021-10-23 | 20,6 |  | 1.5.0 |  | 10 | 0 | 0 | 0 |
| 95 | org.ow2.asm:asm-analysis | org.objectweb.asm.tree.analysis ✳️ | 2026-05-23 | 14,5 | 8,9 | 9.10.1 | 9.10.1 | 45 | 33 | 4 | 4 |
| 96 | joda-time:joda-time | org.joda.time ⚙️ | 2026-04-28 | 20,8 | 8,0 | 2.14.2 | 2.14.2 | 65 | 31 | 2 | 2 |
| ~~97~~ | ~~org.apache.maven.plugins:maven-surefire-plugin~~ | ~~-~~ | ~~2026-05-25~~ | ~~20,2~~ | ~~-~~ | ~~3.5.6~~ | ~~-~~ | ~~70~~ | ~~0~~ | ~~3~~ | ~~0~~ |
| 98 | com.thoughtworks.qdox:qdox ⚠️ | com.thoughtworks.qdox ✳️ | 2024-11-29 | 19,0 | 8,8 | 2.2.0 | 2.2.0 | 28 | 10 | 0 | 0 |
| 99 | org.junit.jupiter:junit-jupiter-params | org.junit.jupiter.params ✳️ | 2026-05-19 | 9,2 | 8,9 | 6.1.0 | 6.1.0 | 107 | 106 | 22 | 22 |
| 100 | jakarta.activation:jakarta.activation-api | jakarta.activation ✳️ | 2026-04-20 | 7,5 | 7,5 | 2.2.0-M2 | 2.2.0-M2 | 16 | 16 | 3 | 3 |
| ~~101~~ | ~~org.apache.maven:maven-aether-provider 🚩~~ | ~~-~~ | ~~2015-11-10~~ | ~~15,8~~ | ~~-~~ | ~~3.3.9~~ | ~~-~~ | ~~17~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| 102 | classworlds:classworlds 🚩 |  | 2006-01-12 | 20,8 |  | 1.1 |  | 13 | 0 | 0 | 0 |
| ~~103~~ | ~~org.apache.maven.plugins:maven-resources-plugin~~ | ~~-~~ | ~~2026-03-02~~ | ~~20,1~~ | ~~-~~ | ~~3.5.0~~ | ~~-~~ | ~~23~~ | ~~0~~ | ~~2~~ | ~~0~~ |
| 104 | org.tukaani:xz | org.tukaani.xz 🏷️ | 2026-03-01 | 14,6 | 8,4 | 1.12 | 1.12 | 13 | 5 | 2 | 2 |
| ~~105~~ | ~~org.codehaus.plexus:plexus-java~~ | ~~org.codehaus.plexus.languages.java ✳️~~ | ~~2025-12-21~~ | ~~8,8~~ | ~~7,6~~ | ~~1.5.2~~ | ~~1.5.2~~ | ~~29~~ | ~~18~~ | ~~2~~ | ~~2~~ |
| ~~106~~ | ~~org.apache.maven:maven-archiver~~ | ~~-~~ | ~~2025-12-20~~ | ~~20,1~~ | ~~-~~ | ~~3.6.6~~ | ~~-~~ | ~~39~~ | ~~0~~ | ~~3~~ | ~~0~~ |
| 107 | io.netty:netty-codec-http2 | io.netty.codec.http2 ⚙️ | 2026-05-20 | 11,3 | 8,5 | 4.1.134.Final | 4.1.134.Final | 173 | 142 | 26 | 26 |
| 108 | org.springframework.boot:spring-boot | spring.boot ⚙️ | 2026-04-23 | 12,2 | 8,3 | 3.5.14 | 3.5.14 | 286 | 221 | 39 | 39 |
| 109 | io.netty:netty-transport-native-unix-common | io.netty.transport.unix.common ⚙️ | 2026-05-20 | 9,1 | 8,5 | 4.1.134.Final | 4.1.134.Final | 149 | 142 | 26 | 26 |
| ~~110~~ | ~~org.springframework:spring-framework-bom~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| 111 | org.antlr:antlr4-runtime ⚠️ | org.antlr.antlr4.runtime ⚙️ | 2024-08-03 | 13,5 | 7,5 | 4.13.2 | 4.13.2 | 31 | 15 | 0 | 0 |
| 112 | org.springframework:spring-web | spring.web ⚙️ | 2026-04-17 | 20,4 | 8,7 | 6.2.18 | 6.2.18 | 335 | 193 | 27 | 27 |
| ~~113~~ | ~~org.apache.maven:maven-plugin-descriptor 🚩~~ | ~~-~~ | ~~2010-02-12~~ | ~~20,1~~ | ~~-~~ | ~~2.0.11~~ | ~~-~~ | ~~21~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| ~~114~~ | ~~org.apache.maven:maven-error-diagnostics 🚩~~ | ~~-~~ | ~~2010-02-12~~ | ~~20,1~~ | ~~-~~ | ~~2.0.11~~ | ~~-~~ | ~~16~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| ~~115~~ | ~~org.apache.maven:maven-monitor 🚩~~ | ~~-~~ | ~~2010-02-12~~ | ~~20,1~~ | ~~-~~ | ~~2.0.11~~ | ~~-~~ | ~~22~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| ~~116~~ | ~~org.apache.maven:maven-plugin-parameter-documenter 🚩~~ | ~~-~~ | ~~2010-02-12~~ | ~~20,1~~ | ~~-~~ | ~~2.0.11~~ | ~~-~~ | ~~17~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| 117 | org.jetbrains.kotlin:kotlin-gradle-plugin-model |  | 2025-10-23 | 7,7 |  | 2.2.21 |  | 149 | 0 | 14 | 0 |
| 118 | org.junit.jupiter:junit-jupiter | org.junit.jupiter ✳️ | 2026-05-19 | 7,4 | 7,4 | 6.1.0 | 6.1.0 | 84 | 84 | 22 | 22 |
| ~~119~~ | ~~org.apache.maven.shared:maven-filtering~~ | ~~-~~ | ~~2026-03-02~~ | ~~17,8~~ | ~~-~~ | ~~3.5.0~~ | ~~-~~ | ~~18~~ | ~~0~~ | ~~1~~ | ~~0~~ |
| 120 | org.reactivestreams:reactive-streams 🚩 | org.reactivestreams ⚙️ | 2022-05-22 | 11,9 | 8,5 | 1.0.4 | 1.0.4 | 22 | 6 | 0 | 0 |
| 121 | ch.qos.logback:logback-core | ch.qos.logback.core ✳️ | 2026-06-01 | 19,8 | 8,4 | 1.5.34 | 1.5.34 | 165 | 85 | 17 | 17 |
| 122 | org.objenesis:objenesis | org.objenesis ⚙️ | 2026-01-26 | 19,0 | 7,6 | 3.5 | 3.5 | 20 | 6 | 1 | 1 |
| 123 | ch.qos.logback:logback-classic | ch.qos.logback.classic ✳️ | 2026-06-01 | 19,8 | 8,4 | 1.5.34 | 1.5.34 | 165 | 85 | 17 | 17 |
| 124 | io.netty:netty-transport-native-epoll | io.netty.transport.epoll.linux.x86_64 ⚙️ | 2026-05-20 | 12,3 | 8,5 | 4.1.134.Final | 4.1.134.Final | 217 | 142 | 26 | 26 |
| ~~125~~ | ~~org.sonatype.sisu:sisu-guice 🚩~~ | ~~com.google.guice ⚙️~~ | ~~2018-04-03~~ | ~~15,7~~ | ~~8,2~~ | ~~4.2.0~~ | ~~4.2.0~~ | ~~31~~ | ~~1~~ | ~~0~~ | ~~0~~ |
| 126 | com.squareup.okio:okio-jvm | okio ⚙️ | 2026-03-11 | 4,7 | 4,7 | 3.17.0 | 3.17.0 | 27 | 27 | 9 | 9 |
| 127 | org.springframework.boot:spring-boot-autoconfigure | spring.boot.autoconfigure ⚙️ | 2026-04-23 | 12,2 | 8,3 | 3.5.14 | 3.5.14 | 286 | 221 | 39 | 39 |
| 128 | org.junit.platform:junit-platform-launcher | org.junit.platform.launcher ✳️ | 2026-05-19 | 9,9 | 8,9 | 6.1.0 | 6.1.0 | 110 | 106 | 22 | 22 |
| ~~129~~ | ~~org.apache.maven.wagon:wagon-provider-api 🚩~~ | ~~-~~ | ~~2022-12-18~~ | ~~20,1~~ | ~~-~~ | ~~3.5.3~~ | ~~-~~ | ~~40~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| ~~130~~ | ~~org.codehaus.plexus:plexus-compiler-api~~ | ~~-~~ | ~~2026-01-25~~ | ~~20,6~~ | ~~-~~ | ~~2.16.2~~ | ~~-~~ | ~~46~~ | ~~0~~ | ~~3~~ | ~~0~~ |
| 131 | org.apache.commons:commons-text 🚩 | org.apache.commons.text ⚙️ | 2020-07-21 | 9,3 | 8,2 | 1.9 | 1.9 | 11 | 7 | 0 | 0 |
| 132 | org.springframework.boot:spring-boot-starter | spring.boot.starter ⚙️ | 2026-04-23 | 12,2 | 8,3 | 3.5.14 | 3.5.14 | 286 | 221 | 39 | 39 |
| 133 | javax.annotation:javax.annotation-api 🚩 | java.annotation ⚙️ | 2018-02-21 | 13,4 | 8,7 | 1.3.2 | 1.3.2 | 8 | 2 | 0 | 0 |
| 134 | net.java.dev.jna:jna-platform | com.sun.jna.platform ⚙️ | 2025-09-30 | 12,9 | 7,7 | 5.18.1 | 5.18.1 | 32 | 22 | 2 | 2 |
| ~~135~~ | ~~org.sonatype.plexus:plexus-build-api 🚩~~ | ~~-~~ | ~~2011-02-11~~ | ~~17,3~~ | ~~-~~ | ~~0.0.7~~ | ~~-~~ | ~~6~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| ~~136~~ | ~~org.apache.maven.plugins:maven-clean-plugin ⚠️~~ | ~~-~~ | ~~2025-05-27~~ | ~~20,1~~ | ~~-~~ | ~~3.5.0~~ | ~~-~~ | ~~22~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| 137 | net.bytebuddy:byte-buddy-agent | net.bytebuddy.agent ✳️ | 2026-03-31 | 11,7 | 9,1 | 1.18.8 | 1.18.8 | 312 | 176 | 15 | 15 |
| ~~138~~ | ~~org.codehaus.plexus:plexus-compiler-javac~~ | ~~-~~ | ~~2026-01-25~~ | ~~20,6~~ | ~~-~~ | ~~2.16.2~~ | ~~-~~ | ~~47~~ | ~~0~~ | ~~3~~ | ~~0~~ |
| 139 | commons-digester:commons-digester 🚩 |  | 2010-09-24 | 20,5 |  | 2.1 |  | 10 | 0 | 0 | 0 |
| ~~140~~ | ~~org.codehaus.plexus:plexus-compiler-manager~~ | ~~-~~ | ~~2026-01-25~~ | ~~20,6~~ | ~~-~~ | ~~2.16.2~~ | ~~-~~ | ~~46~~ | ~~0~~ | ~~3~~ | ~~0~~ |
| 141 | io.netty:netty-handler-proxy | io.netty.handler.proxy ⚙️ | 2026-05-20 | 11,3 | 8,5 | 4.1.134.Final | 4.1.134.Final | 173 | 142 | 26 | 26 |
| 142 | io.netty:netty-codec-socks | io.netty.codec.socks ⚙️ | 2026-05-20 | 13,5 | 8,5 | 4.1.134.Final | 4.1.134.Final | 248 | 142 | 26 | 26 |
| ~~143~~ | ~~org.apache.maven.surefire:surefire-booter~~ | ~~-~~ | ~~2026-05-25~~ | ~~20,1~~ | ~~-~~ | ~~3.5.6~~ | ~~-~~ | ~~67~~ | ~~0~~ | ~~3~~ | ~~0~~ |
| ~~144~~ | ~~org.apache.maven.surefire:maven-surefire-common~~ | ~~-~~ | ~~2026-05-25~~ | ~~15,8~~ | ~~-~~ | ~~3.5.6~~ | ~~-~~ | ~~56~~ | ~~0~~ | ~~3~~ | ~~0~~ |
| 145 | org.codehaus.mojo:animal-sniffer-annotations |  | 2026-01-18 | 16,6 |  | 1.27 |  | 25 | 0 | 3 | 0 |
| ~~146~~ | ~~org.apache.maven.surefire:surefire-api~~ | ~~-~~ | ~~2026-05-25~~ | ~~20,1~~ | ~~-~~ | ~~3.5.6~~ | ~~-~~ | ~~64~~ | ~~0~~ | ~~3~~ | ~~0~~ |
| 147 | io.perfmark:perfmark-api ⚠️ | io.perfmark ⚙️ | 2023-12-21 | 7,0 | 5,0 | 0.27.0 | 0.27.0 | 15 | 4 | 0 | 0 |
| 148 | org.mockito:mockito-core | org.mockito 🏷️ | 2026-03-11 | 18,1 | 8,6 | 5.23.0 | 5.23.0 | 349 | 105 | 5 | 5 |
| ~~149~~ | ~~org.sonatype.plexus:plexus-sec-dispatcher 🚩~~ | ~~-~~ | ~~2009-12-16~~ | ~~17,3~~ | ~~-~~ | ~~1.4~~ | ~~-~~ | ~~9~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| 150 | org.ow2.asm:asm-util | org.objectweb.asm.util ✳️ | 2026-05-23 | 14,5 | 8,9 | 9.10.1 | 9.10.1 | 45 | 33 | 4 | 4 |
| ~~151~~ | ~~org.eclipse.sisu:org.eclipse.sisu.inject~~ | ~~-~~ | ~~2026-02-07~~ | ~~13,7~~ | ~~-~~ | ~~1.0.0~~ | ~~-~~ | ~~25~~ | ~~0~~ | ~~1~~ | ~~0~~ |
| ~~152~~ | ~~org.eclipse.sisu:org.eclipse.sisu.plexus~~ | ~~-~~ | ~~2026-02-07~~ | ~~13,7~~ | ~~-~~ | ~~1.0.0~~ | ~~-~~ | ~~25~~ | ~~0~~ | ~~1~~ | ~~0~~ |
| 153 | org.jetbrains.kotlinx:kotlinx-coroutines-android | kotlinx.coroutines.android 🏷️ | 2026-05-07 | 8,7 | 3,2 | 1.11.0 | 1.11.0 | 105 | 20 | 3 | 3 |
| 154 | org.jetbrains.kotlin:kotlin-gradle-plugin-idea |  | 2026-05-27 | 4,1 |  | 2.4.0-RC2 |  | 111 | 0 | 36 | 0 |
| 155 | org.slf4j:jcl-over-slf4j | org.apache.commons.logging ✳️ | 2026-05-12 | 18,0 | 9,2 | 2.0.18 | 2.0.18 | 94 | 48 | 1 | 1 |
| 156 | org.apache.velocity:velocity 🚩 |  | 2010-11-29 | 19,2 |  | 1.7 |  | 10 | 0 | 0 | 0 |
| 157 | org.springframework:spring-webmvc | spring.webmvc ⚙️ | 2026-04-17 | 20,4 | 8,7 | 6.2.18 | 6.2.18 | 335 | 193 | 27 | 27 |
| ~~158~~ | ~~org.sonatype.plexus:plexus-cipher 🚩~~ | ~~-~~ | ~~2011-07-26~~ | ~~17,5~~ | ~~-~~ | ~~1.7~~ | ~~-~~ | ~~8~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| 159 | net.minidev:json-smart |  | 2025-08-02 | 15,0 |  | 2.6.0 |  | 35 | 0 | 1 | 0 |
| 160 | io.grpc:grpc-context |  | 2026-04-30 | 9,7 |  | 1.81.0 |  | 173 | 0 | 12 | 0 |
| ~~161~~ | ~~org.apache.maven.plugins:maven-jar-plugin~~ | ~~-~~ | ~~2025-11-11~~ | ~~20,1~~ | ~~-~~ | ~~3.5.0~~ | ~~-~~ | ~~26~~ | ~~0~~ | ~~1~~ | ~~0~~ |
| 162 | org.springframework.boot:spring-boot-starter-logging | spring.boot.starter.logging ⚙️ | 2026-04-23 | 12,2 | 8,3 | 3.5.14 | 3.5.14 | 286 | 221 | 39 | 39 |
| ~~163~~ | ~~org.apache.maven.shared:file-management ⚠️~~ | ~~-~~ | ~~2025-04-13~~ | ~~20,1~~ | ~~-~~ | ~~3.2.0~~ | ~~-~~ | ~~7~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| 164 | org.springframework.boot:spring-boot-starter-json | spring.boot.starter.json ⚙️ | 2026-04-23 | 8,3 | 8,3 | 3.5.14 | 3.5.14 | 221 | 221 | 39 | 39 |
| 165 | xml-apis:xml-apis 🚩 |  | 2011-08-20 | 20,5 |  | 1.4.01 |  | 7 | 0 | 0 | 0 |
| 166 | org.springframework:spring-tx | spring.tx ⚙️ | 2026-04-17 | 18,5 | 8,7 | 6.2.18 | 6.2.18 | 296 | 193 | 27 | 27 |
| ~~167~~ | ~~org.apache.maven.shared:maven-shared-incremental 🚩~~ | ~~-~~ | ~~2013-04-03~~ | ~~13,5~~ | ~~-~~ | ~~1.1~~ | ~~-~~ | ~~2~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| 168 | io.projectreactor:reactor-core | reactor.core ⚙️ | 2026-04-14 | 11,3 | 6,7 | 3.8.5 | 3.8.5 | 200 | 143 | 24 | 24 |
| 169 | net.minidev:accessors-smart |  | 2025-08-02 | 10,8 |  | 2.6.0 |  | 14 | 0 | 1 | 0 |
| 170 | org.jetbrains.kotlinx:kotlinx-coroutines-core | kotlinx.coroutines.core.artifact_disambiguating_module ⚙️ | 2026-05-07 | 8,7 | 0,1 | 1.11.0 | 1.11.0 | 105 | 3 | 3 | 3 |
| ~~171~~ | ~~org.apache.maven.shared:maven-dependency-tree ⚠️~~ | ~~-~~ | ~~2024-05-26~~ | ~~19,5~~ | ~~-~~ | ~~3.3.0~~ | ~~-~~ | ~~14~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| ~~172~~ | ~~org.apache.maven.plugins:maven-install-plugin ⚠️~~ | ~~-~~ | ~~2025-02-24~~ | ~~20,1~~ | ~~-~~ | ~~3.1.4~~ | ~~-~~ | ~~20~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| ~~173~~ | ~~org.apache.maven.doxia:doxia-core~~ | ~~-~~ | ~~2026-03-17~~ | ~~20,1~~ | ~~-~~ | ~~2.1.0~~ | ~~-~~ | ~~38~~ | ~~0~~ | ~~1~~ | ~~0~~ |
| 174 | org.glassfish.jaxb:txw2 | com.sun.xml.txw2 ✳️ | 2026-05-28 | 12,2 | 7,9 | 4.0.9 | 4.0.9 | 45 | 38 | 4 | 4 |
| 175 | org.bouncycastle:bcprov-jdk15on 🚩 | org.bouncycastle.provider 🏷️ | 2021-12-01 | 13,3 | 7,9 | 1.70 | 1.70 | 24 | 12 | 0 | 0 |
| ~~176~~ | ~~org.codehaus.plexus:plexus-velocity~~ | ~~-~~ | ~~2025-10-12~~ | ~~20,6~~ | ~~-~~ | ~~2.3.0~~ | ~~-~~ | ~~14~~ | ~~0~~ | ~~1~~ | ~~0~~ |
| ~~177~~ | ~~org.codehaus.plexus:plexus-interactivity-api~~ | ~~-~~ | ~~2025-11-07~~ | ~~20,6~~ | ~~-~~ | ~~1.5.1~~ | ~~-~~ | ~~9~~ | ~~0~~ | ~~1~~ | ~~0~~ |
| ~~178~~ | ~~org.eclipse.aether:aether-api 🚩~~ | ~~-~~ | ~~2016-02-03~~ | ~~13,7~~ | ~~-~~ | ~~1.1.0~~ | ~~-~~ | ~~10~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| 179 | org.glassfish.jaxb:jaxb-runtime | org.glassfish.jaxb.runtime ✳️ | 2026-05-28 | 12,2 | 7,9 | 4.0.9 | 4.0.9 | 45 | 38 | 4 | 4 |
| 180 | io.netty:netty-tcnative-boringssl-static | io.netty.internal.tcnative ✳️ | 2026-04-23 | 10,3 | 7,3 | 2.0.77.Final | 2.0.77.Final | 99 | 56 | 5 | 5 |
| 181 | jakarta.annotation:jakarta.annotation-api ⚠️ | jakarta.annotation ✳️ | 2024-02-15 | 7,6 | 7,6 | 3.0.0 | 3.0.0 | 10 | 10 | 0 | 0 |
| 182 | org.springframework.boot:spring-boot-starter-web | spring.boot.starter.web ⚙️ | 2026-04-23 | 12,2 | 8,3 | 3.5.14 | 3.5.14 | 286 | 221 | 39 | 39 |
| 183 | org.slf4j:jul-to-slf4j | jul.to.slf4j ✳️ | 2026-05-12 | 18,0 | 8,2 | 2.0.18 | 2.0.18 | 94 | 33 | 1 | 1 |
| 184 | com.sun.istack:istack-commons-runtime 🚩 | com.sun.istack.runtime ✳️ | 2023-04-14 | 16,0 | 7,8 | 4.2.0 | 4.2.0 | 48 | 16 | 0 | 0 |
| 185 | org.springframework:spring-test | spring.test ⚙️ | 2026-04-17 | 18,5 | 8,7 | 6.2.18 | 6.2.18 | 296 | 193 | 27 | 27 |
| 186 | javax.xml.bind:jaxb-api 🚩 | java.xml.bind 🏷️ | 2018-09-12 | 19,9 | 8,8 | 2.3.1 | 2.3.1 | 34 | 4 | 0 | 0 |
| 187 | com.fasterxml.jackson.dataformat:jackson-dataformat-cbor | com.fasterxml.jackson.dataformat.cbor ✳️ | 2026-06-01 | 12,3 | 8,6 | 2.22.0 | 2.22.0 | 157 | 92 | 18 | 18 |
| 188 | com.github.luben:zstd-jni | com.github.luben.zstd_jni ✳️ | 2026-05-20 | 10,5 | 7,9 | 1.5.7-9 | 1.5.7-9 | 148 | 103 | 6 | 6 |
| ~~189~~ | ~~org.apache.maven.doxia:doxia-site-renderer~~ | ~~-~~ | ~~2026-03-31~~ | ~~20,1~~ | ~~-~~ | ~~2.1.0~~ | ~~-~~ | ~~46~~ | ~~0~~ | ~~1~~ | ~~0~~ |
| ~~190~~ | ~~org.apache.maven.surefire:surefire-logger-api~~ | ~~-~~ | ~~2026-05-25~~ | ~~9,2~~ | ~~-~~ | ~~3.5.6~~ | ~~-~~ | ~~32~~ | ~~0~~ | ~~3~~ | ~~0~~ |
| 191 | org.xerial.snappy:snappy-java |  | 2025-07-19 | 15,2 |  | 1.1.10.8 |  | 81 | 0 | 1 | 0 |
| ~~192~~ | ~~org.apache.maven.doxia:doxia-module-xhtml 🚩~~ | ~~-~~ | ~~2023-01-09~~ | ~~18,7~~ | ~~-~~ | ~~1.12.0~~ | ~~-~~ | ~~24~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| ~~193~~ | ~~org.apache.maven.doxia:doxia-decoration-model 🚩~~ | ~~-~~ | ~~2023-03-19~~ | ~~20,1~~ | ~~-~~ | ~~2.0.0-M6~~ | ~~-~~ | ~~34~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| 194 | org.springframework.boot:spring-boot-starter-tomcat | spring.boot.starter.tomcat ⚙️ | 2026-04-23 | 12,2 | 8,3 | 3.5.14 | 3.5.14 | 286 | 221 | 39 | 39 |
| 195 | com.jayway.jsonpath:json-path | json.path ⚙️ | 2026-02-22 | 15,3 | 2,4 | 3.0.0 | 3.0.0 | 25 | 3 | 2 | 2 |
| 196 | org.jetbrains.kotlin:kotlin-gradle-plugin |  | 2026-05-27 | 12,9 |  | 2.4.0-RC2 |  | 290 | 62 | 36 | 0 |
| 197 | com.google.auto.value:auto-value-annotations |  | 2025-11-11 | 8,2 |  | 1.11.1 |  | 28 | 0 | 1 | 0 |
| 198 | io.grpc:grpc-api | io.grpc ⚙️ | 2026-04-30 | 7,0 | 2,7 | 1.81.0 | 1.81.0 | 139 | 42 | 12 | 12 |
| 199 | oro:oro 🚩 |  | 2005-11-22 | 20,5 |  | 2.0.8 |  | 3 | 0 | 0 | 0 |
| 200 | org.apache.commons:commons-collections4 🚩 | org.apache.commons.collections4 ⚙️ | 2019-07-05 | 12,5 | 7,9 | 4.4 | 4.4 | 5 | 3 | 0 | 0 |
| 201 | org.hamcrest:hamcrest ⚠️ | org.hamcrest ⚙️ | 2024-08-01 | 7,5 | 7,5 | 3.0 | 3.0 | 9 | 9 | 0 | 0 |
| 202 | com.google.protobuf:protobuf-java-util | com.google.protobuf.util ⚙️ | 2026-05-20 | 10,8 | 6,7 | 4.34.2 | 4.34.2 | 202 | 147 | 23 | 23 |
| 203 | io.grpc:grpc-stub | io.grpc.stub ⚙️ | 2026-04-30 | 11,0 | 2,7 | 1.81.0 | 1.81.0 | 189 | 42 | 12 | 12 |
| 204 | aopalliance:aopalliance 🚩 |  | 2005-08-01 | 20,8 |  | 1.0 |  | 1 | 0 | 0 | 0 |
| 205 | com.github.ben-manes.caffeine:caffeine |  | 2025-07-13 | 11,2 |  | 3.2.2 |  | 71 | 32 | 2 | 0 |
| 206 | io.grpc:grpc-core | io.grpc.internal ⚙️ | 2026-04-30 | 11,0 | 2,7 | 1.81.0 | 1.81.0 | 186 | 42 | 12 | 12 |
| 207 | io.netty:netty-transport-classes-epoll | io.netty.transport.classes.epoll ⚙️ | 2026-05-20 | 4,6 | 4,6 | 4.1.134.Final | 4.1.134.Final | 90 | 90 | 26 | 26 |
| 208 | io.grpc:grpc-protobuf-lite | io.grpc.protobuf.lite ⚙️ | 2026-04-30 | 10,1 | 2,7 | 1.81.0 | 1.81.0 | 179 | 42 | 12 | 12 |
| 209 | org.jdom:jdom2 🚩 | org.jdom2 ⚙️ | 2021-12-06 | 14,3 | 4,5 | 2.0.6.1 | 2.0.6.1 | 10 | 1 | 0 | 0 |
| ~~210~~ | ~~org.codehaus.plexus:plexus-i18n~~ | ~~-~~ | ~~2025-11-07~~ | ~~20,6~~ | ~~-~~ | ~~1.1.0~~ | ~~-~~ | ~~5~~ | ~~0~~ | ~~2~~ | ~~0~~ |
| 211 | org.springframework.boot:spring-boot-test | spring.boot.test ⚙️ | 2026-04-23 | 9,8 | 8,3 | 3.5.14 | 3.5.14 | 252 | 221 | 39 | 39 |
| 212 | org.projectlombok:lombok | lombok 🏷️ | 2026-04-21 | 15,2 | 8,0 | 1.18.46 | 1.18.46 | 59 | 25 | 4 | 4 |
| 213 | org.assertj:assertj-core | org.assertj.core ✳️ | 2026-01-24 | 13,2 | 8,1 | 3.27.7 | 3.27.7 | 82 | 44 | 4 | 4 |
| 214 | org.springframework.boot:spring-boot-starter-test | spring.boot.starter.test ⚙️ | 2026-04-23 | 12,2 | 8,3 | 3.5.14 | 3.5.14 | 286 | 221 | 39 | 39 |
| 215 | commons-validator:commons-validator 🚩 |  | 2020-08-03 | 20,6 |  | 1.7 |  | 15 | 0 | 0 | 0 |
| 216 | org.springframework.boot:spring-boot-loader-tools | spring.boot.loader.tools ⚙️ | 2026-04-23 | 12,2 | 8,3 | 3.5.14 | 3.5.14 | 286 | 221 | 39 | 39 |
| ~~217~~ | ~~org.sonatype.sisu:sisu-inject-bean 🚩~~ | ~~-~~ | ~~2015-02-20~~ | ~~15,7~~ | ~~-~~ | ~~2.6.0~~ | ~~-~~ | ~~28~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| ~~218~~ | ~~org.sonatype.sisu:sisu-inject-plexus 🚩~~ | ~~-~~ | ~~2015-02-20~~ | ~~15,7~~ | ~~-~~ | ~~2.6.0~~ | ~~-~~ | ~~28~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| 219 | io.grpc:grpc-protobuf | io.grpc.protobuf ⚙️ | 2026-04-30 | 11,0 | 2,7 | 1.81.0 | 1.81.0 | 189 | 42 | 12 | 12 |
| 220 | com.google.api.grpc:proto-google-common-protos |  | 2026-05-06 | 9,1 |  | 2.71.0 |  | 162 | 0 | 27 | 0 |
| 221 | org.javassist:javassist | org.javassist ⚙️ | 2026-04-19 | 15,9 | 3,7 | 3.31.0-GA | 3.31.0-GA | 32 | 5 | 1 | 1 |
| 222 | org.apache.logging.log4j:log4j-core | org.apache.logging.log4j.core ✳️ | 2026-05-02 | 13,8 | 8,5 | 2.26.0 | 2.26.0 | 76 | 43 | 6 | 6 |
| 223 | org.springframework.boot:spring-boot-test-autoconfigure | spring.boot.test.autoconfigure ⚙️ | 2026-04-23 | 9,8 | 8,3 | 3.5.14 | 3.5.14 | 252 | 221 | 39 | 39 |
| 224 | org.json:json | org.json ✳️ | 2026-05-22 | 18,6 | 7,8 | 20260522 | 20260522 | 32 | 17 | 2 | 2 |
| 225 | org.jetbrains.kotlin:kotlin-compiler-embeddable ⚠️ |  | 2023-08-23 | 10,8 |  | 1.9.10 |  | 145 | 0 | 0 | 0 |
| 226 | org.jetbrains.kotlin:kotlin-daemon-embeddable ⚠️ |  | 2023-08-23 | 6,8 |  | 1.9.10 |  | 71 | 0 | 0 | 0 |
| ~~227~~ | ~~org.sonatype.aether:aether-api 🚩~~ | ~~-~~ | ~~2011-11-29~~ | ~~15,9~~ | ~~-~~ | ~~1.13.1~~ | ~~-~~ | ~~18~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| ~~228~~ | ~~org.sonatype.aether:aether-util 🚩~~ | ~~-~~ | ~~2011-11-29~~ | ~~15,9~~ | ~~-~~ | ~~1.13.1~~ | ~~-~~ | ~~18~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| 229 | org.jboss.logging:jboss-logging | org.jboss.logging ✳️ | 2026-03-17 | 16,3 | 8,3 | 3.6.3.Final | 3.6.3.Final | 37 | 13 | 2 | 2 |
| 230 | org.springframework.boot:spring-boot-buildpack-platform | spring.boot.buildpack.platform ⚙️ | 2026-04-23 | 6,0 | 6,0 | 3.5.14 | 3.5.14 | 178 | 178 | 39 | 39 |
| ~~231~~ | ~~org.sonatype.aether:aether-spi 🚩~~ | ~~-~~ | ~~2011-11-29~~ | ~~15,9~~ | ~~-~~ | ~~1.13.1~~ | ~~-~~ | ~~18~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| ~~232~~ | ~~org.sonatype.aether:aether-impl 🚩~~ | ~~-~~ | ~~2011-11-29~~ | ~~15,9~~ | ~~-~~ | ~~1.13.1~~ | ~~-~~ | ~~18~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| 233 | org.springframework:spring-jdbc | spring.jdbc ⚙️ | 2026-04-17 | 20,4 | 8,7 | 6.2.18 | 6.2.18 | 325 | 193 | 27 | 27 |
| 234 | org.iq80.snappy:snappy ⚠️ |  | 2024-05-22 | 14,7 |  | 0.5 |  | 5 | 0 | 0 | 0 |
| ~~235~~ | ~~io.projectreactor:reactor-bom~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| 236 | org.jetbrains.kotlin:kotlin-scripting-common ⚠️ |  | 2023-08-23 | 8,0 |  | 1.9.10 |  | 88 | 0 | 0 | 0 |
| 237 | org.jetbrains.kotlin:kotlin-scripting-jvm ⚠️ |  | 2023-08-23 | 8,0 |  | 1.9.10 |  | 88 | 0 | 0 | 0 |
| 238 | javax.enterprise:cdi-api 🚩 |  | 2018-07-19 | 16,6 |  | 2.0.SP1 |  | 38 | 0 | 0 | 0 |
| ~~239~~ | ~~org.apache.maven:maven-toolchain 🚩~~ | ~~-~~ | ~~2010-02-12~~ | ~~18,3~~ | ~~-~~ | ~~2.0.11~~ | ~~-~~ | ~~10~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| 240 | org.jsoup:jsoup | org.jsoup ✳️ | 2026-04-20 | 16,3 | 8,1 | 1.22.2 | 1.22.2 | 54 | 24 | 4 | 4 |
| 241 | com.fasterxml.jackson.module:jackson-module-jaxb-annotations | com.fasterxml.jackson.module.jaxb ✳️ | 2026-06-01 | 14,3 | 8,6 | 2.22.0 | 2.22.0 | 183 | 92 | 18 | 18 |
| 242 | org.apache.httpcomponents:httpmime 🚩 | org.apache.httpcomponents.httpmime ⚙️ | 2022-11-30 | 18,3 | 7,9 | 4.5.14 | 4.5.14 | 53 | 9 | 0 | 0 |
| ~~243~~ | ~~org.apache.maven.plugins:maven-dependency-plugin~~ | ~~-~~ | ~~2026-05-24~~ | ~~19,4~~ | ~~-~~ | ~~3.11.0~~ | ~~-~~ | ~~35~~ | ~~0~~ | ~~3~~ | ~~0~~ |
| ~~244~~ | ~~org.springframework.security:spring-security-bom 🚩~~ | ~~-~~ | ~~2020-05-06~~ | ~~6,3~~ | ~~-~~ | ~~4.2.16.RELEASE~~ | ~~-~~ | ~~3~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| 245 | org.jetbrains.kotlin:kotlin-scripting-compiler-embeddable ⚠️ |  | 2023-08-23 | 7,8 |  | 1.9.10 |  | 86 | 0 | 0 | 0 |
| 246 | org.jetbrains.kotlin:kotlin-scripting-compiler-impl-embeddable ⚠️ |  | 2023-08-23 | 7,0 |  | 1.9.10 |  | 73 | 0 | 0 | 0 |
| 247 | org.bouncycastle:bcpkix-jdk15on 🚩 | org.bouncycastle.pkix 🏷️ | 2021-12-01 | 13,3 | 7,9 | 1.70 | 1.70 | 23 | 11 | 0 | 0 |
| 248 | org.jetbrains.kotlin:kotlin-util-io ⚠️ |  | 2023-08-23 | 6,8 |  | 1.9.10 |  | 71 | 0 | 0 | 0 |
| 249 | net.sf.jopt-simple:jopt-simple 🚩 | joptsimple ⚙️ | 2018-09-11 | 18,2 | 7,7 | 6.0-alpha-3 | 6.0-alpha-3 | 45 | 1 | 0 | 0 |
| 250 | org.jetbrains.kotlin:kotlin-daemon-client ⚠️ |  | 2023-08-23 | 9,3 |  | 1.9.10 |  | 114 | 0 | 0 | 0 |
| 251 | org.jetbrains.kotlin:kotlin-native-utils ⚠️ |  | 2023-08-23 | 7,7 |  | 1.9.10 |  | 83 | 0 | 0 | 0 |
| 252 | org.jetbrains.kotlin:kotlin-util-klib ⚠️ |  | 2023-08-23 | 6,8 |  | 1.9.10 |  | 71 | 0 | 0 | 0 |
| ~~253~~ | ~~org.apache.maven.plugins:maven-deploy-plugin ⚠️~~ | ~~-~~ | ~~2025-02-23~~ | ~~20,1~~ | ~~-~~ | ~~3.1.4~~ | ~~-~~ | ~~23~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| ~~254~~ | ~~org.apache.maven.doxia:doxia-logging-api 🚩~~ | ~~-~~ | ~~2023-01-09~~ | ~~17,2~~ | ~~-~~ | ~~1.12.0~~ | ~~-~~ | ~~17~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| 255 | org.jetbrains.kotlin:kotlin-compiler-runner ⚠️ |  | 2023-08-23 | 9,3 |  | 1.9.10 |  | 115 | 0 | 0 | 0 |
| 256 | jakarta.validation:jakarta.validation-api | jakarta.validation ✳️ | 2025-10-22 | 7,5 | 7,5 | 4.0.0-M1 | 4.0.0-M1 | 11 | 11 | 1 | 1 |
| 257 | org.postgresql:postgresql | org.postgresql.jdbc ⚙️ | 2026-04-28 | 13,2 | 6,7 | 42.7.11 | 42.7.11 | 200 | 62 | 5 | 5 |
| 258 | org.eclipse.jetty:jetty-util | org.eclipse.jetty.util ✳️ | 2026-05-01 | 17,2 | 7,6 | 12.0.35 | 12.0.35 | 433 | 173 | 30 | 30 |
| 259 | org.jetbrains.kotlin:kotlin-script-runtime ⚠️ |  | 2023-08-23 | 9,3 |  | 1.9.10 |  | 114 | 0 | 0 | 0 |
| ~~260~~ | ~~org.apache.maven.reporting:maven-reporting-impl ⚠️~~ | ~~-~~ | ~~2024-10-12~~ | ~~20,1~~ | ~~-~~ | ~~4.0.0~~ | ~~-~~ | ~~35~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| ~~261~~ | ~~org.apache.maven.surefire:surefire-extensions-api~~ | ~~-~~ | ~~2026-05-25~~ | ~~6,6~~ | ~~-~~ | ~~3.5.6~~ | ~~-~~ | ~~23~~ | ~~0~~ | ~~3~~ | ~~0~~ |
| 262 | org.apache.httpcomponents.core5:httpcore5 | org.apache.httpcomponents.core5.httpcore5 ⚙️ | 2026-03-16 | 10,4 | 5,7 | 5.5-alpha1 | 5.5-alpha1 | 54 | 37 | 7 | 7 |
| 263 | org.springframework.security:spring-security-crypto | spring.security.crypto ⚙️ | 2026-04-20 | 14,5 | 8,5 | 7.0.5 | 7.0.5 | 261 | 210 | 34 | 34 |
| 264 | org.scala-lang:scala-library |  | 2026-05-21 | 17,5 |  | 3.8.4-RC3 |  | 239 | 55 | 23 | 3 |
| 265 | com.fasterxml.woodstox:woodstox-core | com.ctc.wstx ✳️ | 2026-05-19 | 11,3 | 8,2 | 7.2.0 | 7.2.0 | 39 | 35 | 1 | 1 |
| 266 | org.eclipse.jetty:jetty-io | org.eclipse.jetty.io ✳️ | 2026-05-01 | 17,2 | 7,6 | 12.0.35 | 12.0.35 | 433 | 173 | 30 | 30 |
| ~~267~~ | ~~org.apache.maven.surefire:surefire-extensions-spi~~ | ~~-~~ | ~~2026-05-25~~ | ~~6,0~~ | ~~-~~ | ~~3.5.6~~ | ~~-~~ | ~~22~~ | ~~0~~ | ~~3~~ | ~~0~~ |
| 268 | org.jetbrains.kotlin:kotlin-klib-commonizer-api ⚠️ |  | 2023-08-23 | 5,0 |  | 1.9.10 |  | 46 | 0 | 0 | 0 |
| 269 | org.apache.kafka:kafka-clients |  | 2026-05-13 | 11,6 |  | 4.3.0 |  | 83 | 0 | 9 | 0 |
| 270 | org.eclipse.jetty:jetty-http | org.eclipse.jetty.http ✳️ | 2026-05-01 | 17,2 | 7,6 | 12.0.35 | 12.0.35 | 433 | 173 | 30 | 30 |
| 271 | org.apache.tomcat.embed:tomcat-embed-core | org.apache.tomcat.embed.core ✳️ | 2026-05-05 | 15,9 | 6,7 | 10.1.55 | 10.1.55 | 456 | 209 | 35 | 35 |
| 272 | org.springframework.boot:spring-boot-starter-aop | spring.boot.starter.aop ⚙️ | 2026-04-23 | 12,2 | 8,3 | 3.5.14 | 3.5.14 | 271 | 206 | 24 | 24 |
| ~~273~~ | ~~org.apache.maven.doxia:doxia-module-fml~~ | ~~-~~ | ~~2026-03-17~~ | ~~18,7~~ | ~~-~~ | ~~2.1.0~~ | ~~-~~ | ~~35~~ | ~~0~~ | ~~1~~ | ~~0~~ |
| 274 | org.apache.logging.log4j:log4j-to-slf4j | org.apache.logging.log4j.to.slf4j ✳️ | 2026-05-02 | 13,1 | 8,5 | 2.26.0 | 2.26.0 | 69 | 41 | 6 | 6 |
| 275 | org.apache.httpcomponents.client5:httpclient5 | org.apache.httpcomponents.client5.httpclient5 ⚙️ | 2026-04-15 | 10,4 | 5,7 | 5.6.1 | 5.6.1 | 45 | 33 | 5 | 5 |
| 276 | org.mockito:mockito-junit-jupiter | org.mockito.junit.jupiter 🏷️ | 2026-03-11 | 8,2 | 5,4 | 5.23.0 | 5.23.0 | 100 | 58 | 5 | 5 |
| 277 | org.springframework.boot:spring-boot-actuator | spring.boot.actuator ⚙️ | 2026-04-23 | 12,2 | 8,3 | 3.5.14 | 3.5.14 | 286 | 221 | 39 | 39 |
| ~~278~~ | ~~org.apache.maven.surefire:surefire-shared-utils~~ | ~~-~~ | ~~2026-05-25~~ | ~~6,6~~ | ~~-~~ | ~~3.5.6~~ | ~~-~~ | ~~23~~ | ~~6~~ | ~~3~~ | ~~0~~ |
| 279 | org.apache.httpcomponents.core5:httpcore5-h2 | org.apache.httpcomponents.core5.httpcore5.h2 ⚙️ | 2026-03-16 | 9,4 | 5,7 | 5.5-alpha1 | 5.5-alpha1 | 53 | 37 | 7 | 7 |
| 280 | org.codehaus.woodstox:stax2-api | org.codehaus.stax2 ✳️ | 2026-03-31 | 17,5 | 8,2 | 4.3.0 | 4.3.0 | 19 | 5 | 1 | 1 |
| 281 | com.google.android:annotations 🚩 |  | 2012-08-31 | 13,8 |  | 4.1.1.4 |  | 1 | 0 | 0 | 0 |
| 282 | com.fasterxml:classmate | com.fasterxml.classmate ✳️ | 2026-01-02 | 15,5 | 8,7 | 1.7.3 | 1.7.3 | 22 | 9 | 3 | 3 |
| 283 | org.springframework.boot:spring-boot-actuator-autoconfigure | spring.boot.actuator.autoconfigure ⚙️ | 2026-04-23 | 8,3 | 8,3 | 3.5.14 | 3.5.14 | 221 | 221 | 39 | 39 |
| 284 | org.jetbrains.kotlinx:kotlinx-serialization-core-jvm | kotlinx.serialization.core 🏷️ | 2026-04-09 | 5,3 | 4,7 | 1.11.0 | 1.11.0 | 33 | 28 | 4 | 4 |
| 285 | org.springframework.boot:spring-boot-starter-actuator | spring.boot.starter.actuator ⚙️ | 2026-04-23 | 12,2 | 8,3 | 3.5.14 | 3.5.14 | 286 | 221 | 39 | 39 |
| 286 | asm:asm 🚩 |  | 2011-01-12 | 20,8 |  | 3.3.1 |  | 23 | 0 | 0 | 0 |
| 287 | org.apache.tomcat.embed:tomcat-embed-el | org.apache.tomcat.embed.el ✳️ | 2026-05-05 | 12,3 | 6,7 | 10.1.55 | 10.1.55 | 422 | 211 | 37 | 37 |
| ~~288~~ | ~~org.apache.maven.shared:maven-shared-io 🚩~~ | ~~-~~ | ~~2015-12-20~~ | ~~19,5~~ | ~~-~~ | ~~3.0.0~~ | ~~-~~ | ~~3~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| 289 | com.zaxxer:HikariCP | com.zaxxer.hikari ✳️ | 2025-09-12 | 12,6 | 8,4 | 6.3.3 | 6.3.3 | 103 | 33 | 6 | 5 |
| 290 | org.hdrhistogram:HdrHistogram ⚠️ |  | 2024-05-30 | 12,5 |  | 2.2.2 |  | 27 | 1 | 0 | 0 |
| 291 | org.jetbrains.kotlin:kotlin-android-extensions ⚠️ |  | 2023-08-23 | 11,2 |  | 1.9.10 |  | 154 | 0 | 0 | 0 |
| 292 | org.apache.tomcat.embed:tomcat-embed-websocket | org.apache.tomcat.embed.websocket ✳️ | 2026-05-05 | 12,3 | 6,7 | 10.1.55 | 10.1.55 | 422 | 211 | 37 | 37 |
| 293 | org.scala-lang:scala-reflect | scala.reflect ⚙️ | 2025-12-08 | 14,0 | 8,2 | 2.12.21 | 2.12.21 | 128 | 55 | 3 | 3 |
| 294 | log4j:log4j 🚩 |  | 2007-08-30 | 20,5 |  | 1.2.15 |  | 12 | 0 | 0 | 0 |
| 295 | org.aspectj:aspectjweaver | org.aspectj.weaver ⚙️ | 2025-12-17 | 15,5 | 8,2 | 1.9.25.1 | 1.9.25.1 | 66 | 32 | 2 | 2 |
| ~~296~~ | ~~org.apache.maven.plugin-tools:maven-plugin-annotations~~ | ~~-~~ | ~~2025-10-20~~ | ~~14,0~~ | ~~-~~ | ~~3.15.2~~ | ~~-~~ | ~~29~~ | ~~0~~ | ~~2~~ | ~~0~~ |
| 297 | org.jetbrains.kotlin:kotlin-project-model ⚠️ |  | 2023-08-23 | 5,0 |  | 1.9.10 |  | 46 | 0 | 0 | 0 |
| 298 | io.netty:netty-transport-native-kqueue | io.netty.transport.kqueue.linux.x86_64 ⚙️ | 2026-05-20 | 9,1 | 8,5 | 4.1.134.Final | 4.1.134.Final | 149 | 142 | 26 | 26 |
| 299 | org.springframework.boot:spring-boot-starter-jdbc | spring.boot.starter.jdbc ⚙️ | 2026-04-23 | 12,2 | 8,3 | 3.5.14 | 3.5.14 | 286 | 221 | 39 | 39 |
| ~~300~~ | ~~org.apache.maven.shared:maven-artifact-transfer 🚩~~ | ~~org.apache.maven.shared.artifact.transfer ⚙️~~ | ~~2020-12-22~~ | ~~9,6~~ | ~~5,4~~ | ~~0.13.1~~ | ~~0.13.1~~ | ~~7~~ | ~~1~~ | ~~0~~ | ~~0~~ |
| 301 | com.fasterxml.jackson.dataformat:jackson-dataformat-xml | com.fasterxml.jackson.dataformat.xml ✳️ | 2026-06-01 | 14,3 | 8,6 | 2.22.0 | 2.22.0 | 183 | 92 | 18 | 18 |
| 302 | io.micrometer:micrometer-observation | micrometer.observation ⚙️ | 2026-04-13 | 3,6 | 3,6 | 1.17.0-RC1 | 1.17.0-RC1 | 101 | 101 | 33 | 33 |
| 303 | javax.servlet:javax.servlet-api 🚩 |  | 2018-04-20 | 14,9 |  | 4.0.1 |  | 20 | 0 | 0 | 0 |
| ~~304~~ | ~~org.apache.maven.doxia:doxia-module-xdoc~~ | ~~-~~ | ~~2026-03-17~~ | ~~18,7~~ | ~~-~~ | ~~2.1.0~~ | ~~-~~ | ~~35~~ | ~~0~~ | ~~1~~ | ~~0~~ |
| 305 | io.micrometer:micrometer-commons | micrometer.commons ⚙️ | 2026-04-13 | 3,6 | 3,6 | 1.17.0-RC1 | 1.17.0-RC1 | 101 | 101 | 33 | 33 |
| 306 | org.jetbrains.kotlin:kotlin-tooling-core ⚠️ |  | 2023-08-23 | 4,1 |  | 1.9.10 |  | 26 | 0 | 0 | 0 |
| 307 | io.micrometer:micrometer-core | micrometer.core ⚙️ | 2026-04-13 | 8,9 | 6,1 | 1.17.0-RC1 | 1.17.0-RC1 | 254 | 178 | 33 | 33 |
| ~~308~~ | ~~org.apache.maven.plugins:maven-site-plugin~~ | ~~-~~ | ~~2026-05-20~~ | ~~20,1~~ | ~~-~~ | ~~3.22.0~~ | ~~-~~ | ~~53~~ | ~~0~~ | ~~1~~ | ~~0~~ |
| 309 | javax.annotation:jsr250-api 🚩 |  | 2006-11-22 | 19,5 |  | 1.0 |  | 1 | 0 | 0 | 0 |
| 310 | org.xmlunit:xmlunit-core | org.xmlunit ⚙️ | 2025-10-24 | 10,5 | 7,8 | 2.11.0 | 2.11.0 | 31 | 17 | 3 | 3 |
| 311 | org.jetbrains.kotlinx:kotlinx-serialization-json-jvm | kotlinx.serialization.json 🏷️ | 2026-04-09 | 5,3 | 4,7 | 1.11.0 | 1.11.0 | 33 | 28 | 4 | 4 |
| 312 | com.nimbusds:nimbus-jose-jwt | com.nimbusds.jose.jwt ✳️ | 2026-05-31 | 13,4 | 5,8 | 10.9.1 | 10.9.1 | 305 | 120 | 11 | 11 |
| 313 | org.apache.commons:commons-math3 🚩 |  | 2016-03-17 | 14,2 |  | 3.6.1 |  | 10 | 0 | 0 | 0 |
| 314 | com.amazonaws:aws-java-sdk-core |  | 2025-12-29 | 11,7 |  | 1.12.797 |  | 1961 | 0 | 14 | 0 |
| 315 | org.apache.ant:ant |  | 2026-04-06 | 19,4 |  | 1.10.17 |  | 42 | 0 | 2 | 0 |
| 316 | com.google.inject:guice 🚩 | com.google.guice ⚙️ | 2023-05-12 | 17,0 | 8,3 | 7.0.0 | 7.0.0 | 24 | 13 | 0 | 0 |
| 317 | org.eclipse.jetty:jetty-server | org.eclipse.jetty.server ✳️ | 2026-05-01 | 17,2 | 7,6 | 12.0.35 | 12.0.35 | 433 | 173 | 30 | 30 |
| 318 | org.skyscreamer:jsonassert ⚠️ |  | 2024-07-28 | 14,3 |  | 2.0-rc1 |  | 15 | 0 | 0 | 0 |
| 319 | org.springframework.boot:spring-boot-starter-validation | spring.boot.starter.validation ⚙️ | 2026-04-23 | 10,5 | 8,3 | 3.5.14 | 3.5.14 | 261 | 221 | 39 | 39 |
| ~~320~~ | ~~org.eclipse.aether:aether-impl 🚩~~ | ~~-~~ | ~~2016-02-03~~ | ~~13,7~~ | ~~-~~ | ~~1.1.0~~ | ~~-~~ | ~~10~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| 321 | org.apache.ant:ant-launcher |  | 2026-04-06 | 19,4 |  | 1.10.17 |  | 41 | 0 | 2 | 0 |
| ~~322~~ | ~~org.eclipse.aether:aether-spi 🚩~~ | ~~-~~ | ~~2016-02-03~~ | ~~13,7~~ | ~~-~~ | ~~1.1.0~~ | ~~-~~ | ~~10~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| ~~323~~ | ~~org.apache.maven.doxia:doxia-module-apt~~ | ~~-~~ | ~~2026-03-17~~ | ~~18,7~~ | ~~-~~ | ~~2.1.0~~ | ~~-~~ | ~~35~~ | ~~0~~ | ~~1~~ | ~~0~~ |
| 324 | xerces:xercesImpl 🚩 |  | 2022-01-27 | 20,8 |  | 2.12.2 |  | 20 | 0 | 0 | 0 |
| 325 | org.eclipse.jetty:jetty-security | org.eclipse.jetty.security ✳️ | 2026-05-01 | 17,2 | 7,6 | 12.0.35 | 12.0.35 | 428 | 168 | 30 | 30 |
| 326 | io.grpc:grpc-netty | io.grpc.netty ⚙️ | 2026-04-30 | 11,0 | 2,7 | 1.81.0 | 1.81.0 | 189 | 42 | 12 | 12 |
| 327 | org.springframework.security:spring-security-core | spring.security.core ⚙️ | 2026-04-20 | 18,1 | 8,5 | 7.0.5 | 7.0.5 | 279 | 210 | 34 | 34 |
| ~~328~~ | ~~org.apache.maven:apache-maven 🚩~~ | ~~-~~ | ~~2010-02-12~~ | ~~18,1~~ | ~~-~~ | ~~2.0.11~~ | ~~-~~ | ~~9~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| 329 | io.netty:netty-resolver-dns | io.netty.resolver.dns ⚙️ | 2026-05-20 | 11,3 | 8,5 | 4.1.134.Final | 4.1.134.Final | 173 | 142 | 26 | 26 |
| 330 | org.springframework:spring-orm | spring.orm ⚙️ | 2026-04-17 | 20,4 | 8,7 | 6.2.18 | 6.2.18 | 319 | 193 | 27 | 27 |
| 331 | io.opentelemetry:opentelemetry-api | io.opentelemetry.api ⚙️ | 2026-05-08 | 6,5 | 6,2 | 1.62.0 | 1.62.0 | 100 | 99 | 14 | 14 |
| 332 | com.vaadin.external.google:android-json 🚩 |  | 2014-02-28 | 12,3 |  | 0.0.20131108.vaadin1 |  | 1 | 0 | 0 | 0 |
| 333 | io.netty:netty-codec-dns | io.netty.codec.dns ⚙️ | 2026-05-20 | 11,9 | 8,5 | 4.1.134.Final | 4.1.134.Final | 176 | 142 | 26 | 26 |
| 334 | com.amazonaws:jmespath-java |  | 2025-12-29 | 9,5 |  | 1.12.797 |  | 1776 | 0 | 14 | 0 |
| 335 | com.squareup.okhttp3:logging-interceptor | okhttp3.logging 🏷️ | 2025-11-18 | 10,4 | 8,3 | 5.2.3 | 5.2.3 | 106 | 86 | 10 | 10 |
| ~~336~~ | ~~org.springframework.session:spring-session-bom~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| 337 | io.opentelemetry:opentelemetry-context | io.opentelemetry.context ⚙️ | 2026-05-08 | 5,6 | 5,6 | 1.62.0 | 1.62.0 | 88 | 88 | 14 | 14 |
| 338 | org.eclipse.jetty:jetty-servlet | org.eclipse.jetty.servlet ✳️ | 2025-08-14 | 17,2 | 7,6 | 11.0.26 | 11.0.26 | 371 | 111 | 3 | 3 |
| 339 | org.jvnet.staxex:stax-ex 🚩 | org.jvnet.staxex ✳️ | 2022-02-17 | 19,5 | 8,1 | 2.1.0 | 2.1.0 | 27 | 11 | 0 | 0 |
| 340 | org.apache.commons:commons-pool2 🚩 | org.apache.commons.pool2 ⚙️ | 2021-08-14 | 12,6 | 5,8 | 2.11.1 | 2.11.1 | 19 | 5 | 0 | 0 |
| 341 | org.springframework.security:spring-security-web | spring.security.web ⚙️ | 2026-04-20 | 16,4 | 8,5 | 7.0.5 | 7.0.5 | 270 | 210 | 34 | 34 |
| 342 | org.springframework.data:spring-data-commons | spring.data.commons ⚙️ | 2026-04-17 | 13,3 | 8,7 | 4.1.0-RC1 | 4.1.0-RC1 | 285 | 216 | 33 | 33 |
| 343 | org.jetbrains.kotlin:kotlin-gradle-plugin-idea-proto ⚠️ |  | 2023-08-23 | 3,8 |  | 1.9.10 |  | 21 | 0 | 0 | 0 |
| 344 | org.springframework:spring-aspects | spring.aspects ⚙️ | 2026-04-17 | 20,4 | 8,7 | 6.2.18 | 6.2.18 | 312 | 193 | 27 | 27 |
| ~~345~~ | ~~org.apache.maven.shared:maven-dependency-analyzer~~ | ~~-~~ | ~~2026-05-12~~ | ~~19,2~~ | ~~-~~ | ~~1.17.1~~ | ~~-~~ | ~~28~~ | ~~0~~ | ~~2~~ | ~~0~~ |
| 346 | org.jetbrains.intellij.deps:trove4j 🚩 |  | 2020-04-02 | 7,5 |  | 1.0.20200330 |  | 3 | 0 | 0 | 0 |
| 347 | org.checkerframework:checker-compat-qual ⚠️ | org.checkerframework.checker.qual ⚙️ | 2023-11-02 | 11,7 | 7,8 | 2.5.6 | 2.5.6 | 51 | 2 | 0 | 0 |
| 348 | io.netty:netty-resolver-dns-native-macos | io.netty.resolver.dns.macos.linux.x86_64 ⚙️ | 2026-05-20 | 6,5 | 6,5 | 4.1.134.Final | 4.1.134.Final | 116 | 116 | 26 | 26 |
| 349 | com.beust:jcommander 🚩 |  | 2022-01-11 | 15,9 |  | 1.82 |  | 37 | 1 | 0 | 0 |
| 350 | com.fasterxml.jackson.module:jackson-module-kotlin | com.fasterxml.jackson.kotlin ✳️ | 2026-06-01 | 11,7 | 8,6 | 2.22.0 | 2.22.0 | 166 | 75 | 18 | 18 |
| 351 | mysql:mysql-connector-java 🚩 |  | 2022-07-01 | 11,1 |  | 8.0.30 |  | 89 | 0 | 0 | 0 |
| 352 | com.squareup:javapoet 🚩 | com.squareup.javapoet ⚙️ | 2020-06-18 | 11,3 | 8,3 | 1.13.0 | 1.13.0 | 18 | 6 | 0 | 0 |
| 353 | org.springframework:spring-context-support | spring.context.support ⚙️ | 2026-04-17 | 18,5 | 8,7 | 6.2.18 | 6.2.18 | 296 | 193 | 27 | 27 |
| 354 | org.springframework.security:spring-security-config | spring.security.config ⚙️ | 2026-04-20 | 16,4 | 8,5 | 7.0.5 | 7.0.5 | 270 | 210 | 34 | 34 |
| 355 | com.squareup:kotlinpoet | com.squareup.kotlinpoet ⚙️ | 2026-03-27 | 9,0 | 8,3 | 2.3.0 | 2.3.0 | 50 | 44 | 1 | 1 |
| 356 | org.bouncycastle:bcprov-jdk18on | org.bouncycastle.provider 🏷️ | 2026-05-15 | 4,2 | 4,2 | 1.81.1 | 1.81.1 | 18 | 18 | 6 | 6 |
| 357 | software.amazon.awssdk:http-client-spi | software.amazon.awssdk.http ⚙️ | 2026-05-27 | 8,9 | 7,5 | 2.44.14 | 2.44.14 | 1775 | 1757 | 234 | 234 |
| 358 | software.amazon.awssdk:annotations | software.amazon.awssdk.annotations ⚙️ | 2026-05-27 | 8,7 | 7,5 | 2.44.14 | 2.44.14 | 1773 | 1757 | 234 | 234 |
| 359 | software.amazon.awssdk:utils | software.amazon.awssdk.utils ⚙️ | 2026-05-27 | 8,9 | 7,5 | 2.44.14 | 2.44.14 | 1774 | 1756 | 234 | 234 |
| 360 | org.lz4:lz4-java 🚩 | org.lz4.java ⚙️ | 2021-06-19 | 8,8 | 7,1 | 1.8.0 | 1.8.0 | 9 | 4 | 0 | 0 |
| 361 | org.jacoco:org.jacoco.agent | org.jacoco.agent ⚙️ | 2025-10-11 | 14,9 | 8,4 | 0.8.14 | 0.8.14 | 39 | 15 | 1 | 1 |
| 362 | org.hibernate.validator:hibernate-validator | org.hibernate.validator ✳️ | 2025-11-07 | 9,3 | 8,9 | 9.1.0.Final | 9.1.0.Final | 85 | 81 | 7 | 7 |
| 363 | software.amazon.awssdk:sdk-core | software.amazon.awssdk.core ⚙️ | 2026-05-27 | 7,8 | 7,5 | 2.44.14 | 2.44.14 | 1765 | 1756 | 234 | 234 |
| 364 | io.opencensus:opencensus-api 🚩 |  | 2022-04-29 | 8,9 |  | 0.31.1 |  | 47 | 0 | 0 | 0 |
| 365 | software.amazon.awssdk:regions | software.amazon.awssdk.regions ⚙️ | 2026-05-27 | 8,0 | 7,5 | 2.44.14 | 2.44.14 | 1766 | 1756 | 234 | 234 |
| 366 | software.amazon.awssdk:profiles | software.amazon.awssdk.profiles ⚙️ | 2026-05-27 | 8,0 | 7,5 | 2.44.14 | 2.44.14 | 1766 | 1756 | 234 | 234 |
| 367 | com.google.http-client:google-http-client | com.google.api.client ⚙️ | 2026-01-23 | 14,8 | 7,6 | 2.1.0 | 2.1.0 | 89 | 62 | 7 | 7 |
| 368 | software.amazon.awssdk:auth | software.amazon.awssdk.auth ⚙️ | 2026-05-27 | 8,0 | 7,5 | 2.44.14 | 2.44.14 | 1767 | 1757 | 234 | 234 |
| 369 | com.sun.xml.fastinfoset:FastInfoset ⚠️ | com.sun.xml.fastinfoset ✳️ | 2023-10-20 | 19,9 | 7,8 | 2.1.1 | 2.1.1 | 27 | 10 | 0 | 0 |
| 370 | org.latencyutils:LatencyUtils 🚩 |  | 2015-12-15 | 12,5 |  | 2.0.3 |  | 9 | 0 | 0 | 0 |
| 371 | io.opencensus:opencensus-contrib-http-util 🚩 |  | 2022-04-29 | 8,4 |  | 0.31.1 |  | 37 | 0 | 0 | 0 |
| 372 | com.typesafe:config | typesafe.config ⚙️ | 2026-05-05 | 14,1 | 8,3 | 1.4.8 | 1.4.8 | 31 | 12 | 5 | 5 |
| 373 | javax.activation:activation 🚩 |  | 2009-10-23 | 20,1 |  | 1.1.1 |  | 3 | 0 | 0 | 0 |
| 374 | dom4j:dom4j 🚩 |  | 2005-09-19 | 20,8 |  | 20040902.021138 |  | 18 | 0 | 0 | 0 |
| 375 | org.apache.avro:avro | org.apache.avro ⚙️ | 2025-09-11 | 15,7 | 6,8 | 1.11.5 | 1.11.5 | 36 | 13 | 2 | 2 |
| 376 | org.springframework:spring-messaging | spring.messaging ⚙️ | 2026-04-17 | 12,5 | 8,7 | 6.2.18 | 6.2.18 | 254 | 193 | 27 | 27 |
| ~~377~~ | ~~org.apache.groovy:groovy-bom~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| 378 | org.hibernate.common:hibernate-commons-annotations ⚠️ | org.hibernate.commons.annotations 🏷️ | 2024-10-21 | 14,8 | 8,3 | 7.0.3.Final | 7.0.3.Final | 30 | 20 | 0 | 0 |
| 379 | org.apache.xbean:xbean-reflect | org.apache.xbean.reflect ⚙️ | 2026-01-28 | 20,3 | 8,5 | 4.30 | 4.30 | 62 | 25 | 3 | 3 |
| 380 | com.sun.xml.bind:jaxb-impl | com.sun.xml.bind ✳️ | 2026-05-28 | 20,0 | 7,9 | 4.0.9 | 4.0.9 | 181 | 37 | 4 | 4 |
| 381 | com.github.stephenc.jcip:jcip-annotations 🚩 |  | 2013-02-13 | 13,3 |  | 1.0-1 |  | 1 | 0 | 0 | 0 |
| 382 | org.springframework.boot:spring-boot-starter-data-jpa | spring.boot.starter.data.jpa ⚙️ | 2026-04-23 | 12,2 | 8,3 | 3.5.14 | 3.5.14 | 286 | 221 | 39 | 39 |
| 383 | software.amazon.awssdk:aws-core | software.amazon.awssdk.awscore ⚙️ | 2026-05-27 | 8,0 | 7,5 | 2.44.14 | 2.44.14 | 1767 | 1757 | 234 | 234 |
| 384 | software.amazon.awssdk:metrics-spi | software.amazon.awssdk.metrics ⚙️ | 2026-05-27 | 8,9 | 5,9 | 2.44.14 | 2.44.14 | 1415 | 1413 | 234 | 234 |
| 385 | javax.activation:javax.activation-api 🚩 | java.activation ⚙️ | 2017-09-06 | 8,7 | 8,7 | 1.2.0 | 1.2.0 | 1 | 1 | 0 | 0 |
| 386 | com.jcraft:jsch 🚩 |  | 2018-11-26 | 20,6 |  | 0.1.55 |  | 24 | 0 | 0 | 0 |
| 387 | software.amazon.awssdk:protocol-core | software.amazon.awssdk.protocols.core ⚙️ | 2026-05-27 | 7,5 | 7,5 | 2.44.14 | 2.44.14 | 1756 | 1756 | 234 | 234 |
| 388 | org.apache.hadoop:hadoop-aws |  | 2026-03-24 | 11,5 |  | 3.5.0 |  | 57 | 0 | 3 | 0 |
| 389 | com.google.re2j:re2j ⚠️ |  | 2025-01-09 | 11,3 |  | 1.8 |  | 9 | 0 | 0 | 0 |
| ~~390~~ | ~~org.apache.maven.plugins:maven-assembly-plugin~~ | ~~-~~ | ~~2025-11-22~~ | ~~20,1~~ | ~~-~~ | ~~3.8.0~~ | ~~-~~ | ~~36~~ | ~~0~~ | ~~1~~ | ~~0~~ |
| 391 | org.eclipse.jgit:org.eclipse.jgit | org.eclipse.jgit ⚙️ | 2026-03-13 | 14,4 | 8,4 | 7.6.0.202603022253-r | 7.6.0.202603022253-r | 170 | 91 | 6 | 6 |
| 392 | antlr:antlr 🚩 |  | 2007-01-13 | 20,8 |  | 2.7.7 |  | 7 | 0 | 0 | 0 |
| 393 | org.codehaus.groovy:groovy ⚠️ | org.codehaus.groovy ⚙️ | 2025-05-27 | 18,9 | 8,7 | 3.0.25 | 3.0.25 | 221 | 69 | 0 | 0 |
| 394 | io.dropwizard.metrics:metrics-core | com.codahale.metrics ⚙️ | 2026-05-25 | 11,7 | 8,2 | 4.2.39 | 4.2.39 | 114 | 87 | 7 | 7 |
| 395 | com.h2database:h2 | com.h2database ⚙️ | 2025-09-24 | 19,4 | 7,3 | 2.4.240 | 2.4.240 | 139 | 15 | 1 | 1 |
| 396 | com.amazonaws:aws-java-sdk-bundle | com.fasterxml.jackson.dataformat.cbor ✳️ | 2025-12-29 | 9,4 | 1,8 | 1.12.797 | 2.17.2 | 1746 | 30 | 14 | 14 |
| 397 | org.eclipse.jetty:jetty-xml | org.eclipse.jetty.xml ✳️ | 2026-05-01 | 17,2 | 7,6 | 12.0.35 | 12.0.35 | 433 | 173 | 30 | 30 |
| 398 | software.amazon.awssdk:apache-client | software.amazon.awssdk.http.apache ⚙️ | 2026-05-27 | 8,9 | 7,5 | 2.44.14 | 2.44.14 | 1774 | 1757 | 234 | 234 |
| 399 | javax.validation:validation-api 🚩 | java.validation ⚙️ | 2017-12-19 | 17,3 | 8,9 | 2.0.1.Final | 2.0.1.Final | 26 | 4 | 0 | 0 |
| 400 | software.amazon.awssdk:netty-nio-client | software.amazon.awssdk.http.nio.netty ⚙️ | 2026-05-27 | 8,9 | 7,5 | 2.44.14 | 2.44.14 | 1774 | 1757 | 234 | 234 |
| 401 | org.bitbucket.b_c:jose4j ⚠️ | org.jose4j ⚙️ | 2024-03-06 | 12,0 | 6,6 | 0.9.6 | 0.9.6 | 45 | 21 | 0 | 0 |
| 402 | org.codehaus.jackson:jackson-core-asl 🚩 |  | 2013-07-15 | 17,4 |  | 1.9.13 |  | 80 | 0 | 0 | 0 |
| 403 | jline:jline 🚩 |  | 2018-03-26 | 21,0 |  | 2.14.6 |  | 26 | 0 | 0 | 0 |
| 404 | commons-chain:commons-chain 🚩 |  | 2008-05-29 | 20,5 |  | 1.2 |  | 3 | 0 | 0 | 0 |
| 405 | software.amazon.eventstream:eventstream 🚩 | software.amazon.eventstream ⚙️ | 2019-05-02 | 7,1 | 7,1 | 1.0.1 | 1.0.1 | 2 | 2 | 0 | 0 |
| 406 | org.tomlj:tomlj ⚠️ | org.tomlj ⚙️ | 2023-12-31 | 7,1 | 3,6 | 1.1.1 | 1.1.1 | 3 | 2 | 0 | 0 |
| 407 | com.googlecode.javaewah:JavaEWAH 🚩 | com.googlecode.javaewah ✳️ | 2023-03-08 | 14,4 | 3,2 | 1.2.3 | 1.2.3 | 77 | 1 | 0 | 0 |
| 408 | com.sun.activation:jakarta.activation 🚩 | jakarta.activation ✳️ | 2021-02-12 | 7,5 | 7,5 | 2.0.1 | 2.0.1 | 7 | 7 | 0 | 0 |
| 409 | com.google.devtools.ksp:symbol-processing-api |  | 2026-05-26 | 4,8 |  | 2.3.9 |  | 157 | 0 | 29 | 0 |
| ~~410~~ | ~~org.apache.maven.shared:maven-invoker ⚠️~~ | ~~-~~ | ~~2024-05-07~~ | ~~19,5~~ | ~~-~~ | ~~3.3.0~~ | ~~-~~ | ~~15~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| 411 | com.thoughtworks.paranamer:paranamer ⚠️ |  | 2025-03-11 | 19,3 |  | 2.8.3 |  | 48 | 0 | 0 | 0 |
| 412 | com.google.auth:google-auth-library-credentials | com.google.auth ⚙️ | 2026-05-06 | 11,3 | 6,8 | 1.47.0 | 1.47.0 | 109 | 87 | 16 | 16 |
| 413 | com.fasterxml.jackson.jaxrs:jackson-jaxrs-json-provider | com.fasterxml.jackson.jaxrs.json ✳️ | 2026-06-01 | 14,2 | 8,6 | 2.22.0 | 2.22.0 | 180 | 92 | 18 | 18 |
| 414 | software.amazon.awssdk:json-utils | software.amazon.awssdk.protocols.jsoncore ⚙️ | 2026-05-27 | 4,9 | 4,9 | 2.44.14 | 2.44.14 | 1171 | 1171 | 234 | 234 |
| 415 | org.jetbrains.kotlin:kotlin-annotation-processing-gradle ⚠️ |  | 2023-08-23 | 8,6 |  | 1.9.10 |  | 99 | 0 | 0 | 0 |
| 416 | software.amazon.awssdk:third-party-jackson-core | software.amazon.awssdk.thirdparty.jackson.core ⚙️ | 2026-05-27 | 4,9 | 4,9 | 2.44.14 | 2.44.14 | 1170 | 1170 | 234 | 234 |
| 417 | jakarta.transaction:jakarta.transaction-api 🚩 | jakarta.transaction ✳️ | 2022-03-31 | 7,5 | 7,5 | 2.0.1 | 2.0.1 | 9 | 9 | 0 | 0 |
| 418 | org.apache.velocity:velocity-tools 🚩 |  | 2010-05-10 | 19,2 |  | 2.0 |  | 5 | 0 | 0 | 0 |
| 419 | com.google.collections:google-collections 🚩 |  | 2009-12-30 | 17,6 |  | 1.0 |  | 8 | 0 | 0 | 0 |
| 420 | com.fasterxml.jackson.jaxrs:jackson-jaxrs-base | com.fasterxml.jackson.jaxrs.base ✳️ | 2026-06-01 | 13,1 | 6,9 | 2.22.0 | 2.22.0 | 167 | 84 | 18 | 18 |
| 421 | org.springframework.boot:spring-boot-maven-plugin | spring.boot.maven.plugin ⚙️ | 2026-04-23 | 12,2 | 8,3 | 3.5.14 | 3.5.14 | 286 | 221 | 39 | 39 |
| 422 | org.eclipse.jetty:jetty-util-ajax | org.eclipse.jetty.util.ajax ✳️ | 2026-05-01 | 13,7 | 7,6 | 12.0.35 | 12.0.35 | 320 | 173 | 30 | 30 |
| 423 | io.netty:netty-tcnative-classes | io.netty.tcnative.classes.openssl ✳️ | 2026-04-23 | 4,5 | 4,5 | 2.0.77.Final | 2.0.77.Final | 32 | 32 | 5 | 5 |
| 424 | com.google.dagger:dagger | dagger ⚙️ | 2026-02-20 | 11,2 | 5,0 | 2.59.2 | 2.59.2 | 106 | 44 | 7 | 7 |
| ~~425~~ | ~~org.apache.maven.resolver:maven-resolver-api~~ | ~~org.apache.maven.resolver ✳️~~ | ~~2026-05-14~~ | ~~9,4~~ | ~~8,9~~ | ~~2.0.18~~ | ~~2.0.18~~ | ~~73~~ | ~~72~~ | ~~12~~ | ~~12~~ |
| 426 | com.thoughtworks.xstream:xstream ⚠️ |  | 2024-11-07 | 19,8 |  | 1.4.21 |  | 46 | 0 | 0 | 0 |
| ~~427~~ | ~~com.squareup.okhttp3:okhttp-bom~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| 428 | com.google.auth:google-auth-library-oauth2-http | com.google.auth.oauth2 ⚙️ | 2026-05-06 | 11,3 | 6,8 | 1.47.0 | 1.47.0 | 109 | 87 | 16 | 16 |
| 429 | com.amazonaws:aws-java-sdk-kms |  | 2025-12-29 | 11,6 |  | 1.12.797 |  | 1951 | 0 | 14 | 0 |
| 430 | org.jetbrains.kotlin:kotlin-gradle-plugin-annotations ⚠️ |  | 2023-08-23 | 3,3 |  | 1.9.10 |  | 10 | 0 | 0 | 0 |
| 431 | com.amazonaws:aws-java-sdk-s3 |  | 2025-12-29 | 11,6 |  | 1.12.797 |  | 1956 | 0 | 14 | 0 |
| ~~432~~ | ~~org.apache.maven.resolver:maven-resolver-util~~ | ~~org.apache.maven.resolver.util ✳️~~ | ~~2026-05-14~~ | ~~9,4~~ | ~~8,9~~ | ~~2.0.18~~ | ~~2.0.18~~ | ~~73~~ | ~~72~~ | ~~12~~ | ~~12~~ |
| 433 | org.wildfly.openssl:wildfly-openssl |  | 2026-03-13 | 9,8 |  | 2.3.0.Final |  | 40 | 0 | 2 | 0 |
| 434 | org.vafer:jdependency |  | 2026-05-24 | 15,8 |  | 2.16 |  | 27 | 6 | 4 | 3 |
| 435 | com.google.crypto.tink:tink | com.google.crypto.tink ⚙️ | 2026-03-24 | 8,7 | 3,8 | 1.21.0 | 1.21.0 | 36 | 16 | 4 | 4 |
| 436 | org.springframework.boot:spring-boot-starter-security | spring.boot.starter.security ⚙️ | 2026-04-23 | 12,2 | 8,3 | 3.5.14 | 3.5.14 | 286 | 221 | 39 | 39 |
| 437 | org.jacoco:org.jacoco.core | org.jacoco.core ⚙️ | 2025-10-11 | 14,9 | 8,4 | 0.8.14 | 0.8.14 | 39 | 15 | 1 | 1 |
| ~~438~~ | ~~org.apache.maven.plugins:maven-antrun-plugin~~ | ~~-~~ | ~~2025-10-17~~ | ~~20,1~~ | ~~-~~ | ~~3.2.0~~ | ~~-~~ | ~~14~~ | ~~0~~ | ~~1~~ | ~~0~~ |
| 439 | io.opentelemetry:opentelemetry-sdk | io.opentelemetry.sdk ⚙️ | 2026-05-08 | 6,5 | 6,2 | 1.62.0 | 1.62.0 | 100 | 99 | 14 | 14 |
| 440 | io.opentelemetry:opentelemetry-sdk-common | io.opentelemetry.sdk.common ⚙️ | 2026-05-08 | 5,7 | 5,7 | 1.62.0 | 1.62.0 | 90 | 90 | 14 | 14 |
| 441 | org.eclipse.jetty:jetty-webapp | org.eclipse.jetty.webapp ✳️ | 2025-08-14 | 17,2 | 7,6 | 11.0.26 | 11.0.26 | 371 | 111 | 3 | 3 |
| 442 | org.scala-lang:scala-compiler | scala.tools.nsc ⚙️ | 2025-12-08 | 17,5 | 8,2 | 2.12.21 | 2.12.21 | 219 | 55 | 3 | 3 |
| ~~443~~ | ~~io.opentelemetry:opentelemetry-bom~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| 444 | org.apache.commons:commons-exec 🚩 |  | 2014-11-03 | 17,2 |  | 1.3 |  | 5 | 0 | 0 | 0 |
| 445 | org.scala-lang.modules:scala-xml_2.12 ⚠️ | scala.xml ⚙️ | 2025-05-27 | 9,6 | 5,1 | 2.4.0 | 2.4.0 | 20 | 7 | 0 | 0 |
| 446 | io.opentelemetry:opentelemetry-sdk-trace | io.opentelemetry.sdk.trace ⚙️ | 2026-05-08 | 5,5 | 5,5 | 1.62.0 | 1.62.0 | 86 | 86 | 14 | 14 |
| 447 | io.netty:netty-resolver-dns-classes-macos | io.netty.resolver.dns.classes.macos ⚙️ | 2026-05-20 | 4,6 | 4,6 | 4.1.134.Final | 4.1.134.Final | 90 | 90 | 26 | 26 |
| 448 | io.swagger:swagger-annotations ⚠️ | io.swagger.annotations ⚙️ | 2025-05-15 | 11,0 | 3,2 | 1.6.16 | 1.6.16 | 45 | 7 | 0 | 0 |
| ~~449~~ | ~~org.apache.maven.surefire:common-java5~~ | ~~-~~ | ~~2026-05-25~~ | ~~13,4~~ | ~~-~~ | ~~3.5.6~~ | ~~-~~ | ~~42~~ | ~~0~~ | ~~3~~ | ~~0~~ |
| 450 | software.amazon.awssdk:aws-query-protocol | software.amazon.awssdk.protocols.query ⚙️ | 2026-05-27 | 7,5 | 7,5 | 2.44.14 | 2.44.14 | 1757 | 1757 | 234 | 234 |
| 451 | io.opentelemetry:opentelemetry-sdk-metrics | io.opentelemetry.sdk.metrics ⚙️ | 2026-05-08 | 5,7 | 5,7 | 1.62.0 | 1.62.0 | 90 | 90 | 14 | 14 |
| ~~452~~ | ~~org.apache.maven.doxia:doxia-skin-model~~ | ~~-~~ | ~~2026-03-31~~ | ~~10,3~~ | ~~-~~ | ~~2.1.0~~ | ~~-~~ | ~~30~~ | ~~0~~ | ~~1~~ | ~~0~~ |
| 453 | com.squareup:javawriter 🚩 |  | 2014-12-03 | 13,3 |  | 2.5.1 |  | 18 | 0 | 0 | 0 |
| 454 | io.swagger.core.v3:swagger-annotations | io.swagger.v3.oas.annotations ⚙️ | 2026-05-12 | 8,5 | 7,1 | 2.2.50 | 2.2.50 | 78 | 68 | 18 | 18 |
| 455 | org.awaitility:awaitility ⚠️ |  | 2025-02-21 | 10,0 |  | 4.3.0 |  | 23 | 0 | 0 | 0 |
| 456 | io.opentelemetry:opentelemetry-sdk-logs | io.opentelemetry.sdk.logs ⚙️ | 2026-05-08 | 4,6 | 4,6 | 1.62.0 | 1.62.0 | 66 | 66 | 14 | 14 |
| 457 | org.apache.zookeeper:zookeeper |  | 2026-02-11 | 16,2 |  | 3.9.5 |  | 54 | 0 | 4 | 0 |
| ~~458~~ | ~~org.jetbrains.kotlin:kotlin-gradle-plugins-bom~~ | ~~-~~ | ~~2026-05-27~~ | ~~0,0~~ | ~~-~~ | ~~2.4.0-RC2~~ | ~~-~~ | ~~1~~ | ~~0~~ | ~~1~~ | ~~0~~ |
| ~~459~~ | ~~org.apache.maven.plugins:maven-shade-plugin~~ | ~~-~~ | ~~2026-03-02~~ | ~~18,5~~ | ~~-~~ | ~~3.6.2~~ | ~~-~~ | ~~44~~ | ~~0~~ | ~~2~~ | ~~0~~ |
| 460 | io.github.classgraph:classgraph | io.github.classgraph 🏷️ | 2025-10-10 | 7,8 | 7,8 | 4.8.184 | 4.8.184 | 260 | 254 | 5 | 5 |
| ~~461~~ | ~~org.apache.maven:maven-compat~~ | ~~-~~ | ~~2026-05-13~~ | ~~17,4~~ | ~~-~~ | ~~3.9.16~~ | ~~-~~ | ~~82~~ | ~~0~~ | ~~9~~ | ~~0~~ |
| 462 | software.amazon.awssdk:endpoints-spi | software.amazon.awssdk.endpoints ⚙️ | 2026-05-27 | 3,6 | 3,6 | 2.44.14 | 2.44.14 | 875 | 875 | 234 | 234 |
| 463 | com.google.http-client:google-http-client-gson | com.google.api.client.json.gson ⚙️ | 2026-01-23 | 13,8 | 6,7 | 2.1.0 | 2.1.0 | 79 | 52 | 7 | 7 |
| 464 | org.jacoco:org.jacoco.report | org.jacoco.report ⚙️ | 2025-10-11 | 14,9 | 8,4 | 0.8.14 | 0.8.14 | 39 | 15 | 1 | 1 |
| 465 | xmlpull:xmlpull 🚩 |  | 2010-10-26 | 23,0 |  | 1.1.3.4d_b4_min |  | 4 | 0 | 0 | 0 |
| 466 | backport-util-concurrent:backport-util-concurrent 🚩 |  | 2007-11-11 | 20,8 |  | 3.1 |  | 7 | 0 | 0 | 0 |
| ~~467~~ | ~~org.apache.maven.doxia:doxia-integration-tools~~ | ~~-~~ | ~~2026-03-31~~ | ~~13,7~~ | ~~-~~ | ~~2.1.0~~ | ~~-~~ | ~~32~~ | ~~0~~ | ~~1~~ | ~~0~~ |
| 468 | org.apache.logging.log4j:log4j-slf4j-impl | org.apache.logging.log4j.slf4j.impl ✳️ | 2026-05-02 | 13,1 | 8,5 | 2.26.0 | 2.26.0 | 69 | 35 | 6 | 6 |
| 469 | org.jetbrains.kotlin:kotlin-build-common ⚠️ |  | 2023-08-23 | 10,1 |  | 1.9.10 |  | 108 | 0 | 0 | 0 |
| 470 | jakarta.inject:jakarta.inject-api 🚩 | java.inject ✳️ | 2021-10-17 | 6,8 | 6,1 | 1.0.5 | 1.0.5 | 14 | 10 | 0 | 0 |
| 471 | software.amazon.awssdk:aws-json-protocol | software.amazon.awssdk.protocols.json ⚙️ | 2026-05-27 | 7,5 | 7,5 | 2.44.14 | 2.44.14 | 1757 | 1757 | 234 | 234 |
| 472 | com.google.flatbuffers:flatbuffers-java ⚠️ |  | 2025-02-11 | 8,6 |  | 25.2.10 |  | 29 | 0 | 0 | 0 |
| 473 | org.antlr:antlr-runtime 🚩 |  | 2022-04-10 | 19,0 |  | 3.5.3 |  | 18 | 0 | 0 | 0 |
| 474 | jakarta.persistence:jakarta.persistence-api | jakarta.persistence ✳️ | 2026-05-25 | 7,6 | 7,6 | 4.0.0-M4 | 4.0.0-M4 | 18 | 18 | 4 | 4 |
| 475 | org.springframework.data:spring-data-jpa | spring.data.jpa ⚙️ | 2026-04-17 | 14,3 | 8,7 | 4.1.0-RC1 | 4.1.0-RC1 | 294 | 216 | 33 | 33 |
| 476 | io.netty:netty-transport-classes-kqueue | io.netty.transport.classes.kqueue ⚙️ | 2026-05-20 | 4,6 | 4,6 | 4.1.134.Final | 4.1.134.Final | 90 | 90 | 26 | 26 |
| 477 | org.jetbrains.kotlinx:kotlinx-coroutines-play-services |  | 2026-05-07 | 7,7 |  | 1.11.0 |  | 76 | 0 | 3 | 0 |
| 478 | org.jetbrains.kotlin:kotlin-build-tools-api ⚠️ |  | 2023-08-23 | 3,0 |  | 1.9.10 |  | 4 | 0 | 0 | 0 |
| 479 | org.glassfish.jaxb:jaxb-core | org.glassfish.jaxb.core ✳️ | 2026-05-28 | 12,2 | 6,1 | 4.0.9 | 4.0.9 | 31 | 24 | 4 | 4 |
| 480 | com.google.jimfs:jimfs | com.google.common.jimfs ⚙️ | 2025-07-11 | 12,3 | 2,9 | 1.3.1 | 1.3.1 | 9 | 2 | 1 | 1 |
| 481 | org.springframework:spring-webflux | spring.webflux ⚙️ | 2026-04-17 | 8,7 | 8,7 | 6.2.18 | 6.2.18 | 193 | 193 | 27 | 27 |
| ~~482~~ | ~~org.codehaus.plexus:plexus-xml~~ | ~~-~~ | ~~2026-01-25~~ | ~~3,0~~ | ~~-~~ | ~~4.1.1~~ | ~~-~~ | ~~10~~ | ~~0~~ | ~~1~~ | ~~0~~ |
| 483 | org.eclipse.jetty:jetty-client | org.eclipse.jetty.client ✳️ | 2026-05-01 | 17,2 | 7,6 | 12.0.35 | 12.0.35 | 433 | 173 | 30 | 30 |
| 484 | com.ibm.icu:icu4j | com.ibm.icu ⚙️ | 2026-03-17 | 20,4 | 7,6 | 78.3 | 78.3 | 67 | 23 | 3 | 3 |
| 485 | org.threeten:threetenbp | org.threeten.bp ⚙️ | 2026-05-08 | 13,4 | 7,8 | 1.7.3 | 1.7.3 | 41 | 25 | 2 | 2 |
| 486 | com.github.jnr:jffi | org.jnrproject.jffi ⚙️ | 2026-03-31 | 14,8 | 5,7 | 1.3.15 | 1.3.15 | 42 | 13 | 2 | 2 |
| 487 | com.github.docker-java:docker-java-api | com.github.dockerjava.api ⚙️ | 2026-03-18 | 6,7 | 3,5 | 3.7.1 | 3.7.1 | 38 | 18 | 5 | 5 |
| ~~488~~ | ~~org.apache.maven.surefire:surefire-junit-platform~~ | ~~-~~ | ~~2026-05-25~~ | ~~8,0~~ | ~~-~~ | ~~3.5.6~~ | ~~-~~ | ~~29~~ | ~~0~~ | ~~3~~ | ~~0~~ |
| 489 | com.github.docker-java:docker-java-transport | com.github.dockerjava.transport ⚙️ | 2026-03-18 | 6,0 | 3,5 | 3.7.1 | 3.7.1 | 30 | 18 | 5 | 5 |
| 490 | commons-net:commons-net |  | 2026-03-15 | 20,6 |  | 3.13.0 |  | 30 | 4 | 2 | 0 |
| 491 | io.opentelemetry:opentelemetry-sdk-extension-autoconfigure-spi | io.opentelemetry.sdk.autoconfigure.spi ⚙️ | 2026-05-08 | 4,7 | 4,7 | 1.62.0 | 1.62.0 | 69 | 69 | 14 | 14 |
| 492 | org.jetbrains.kotlinx:kotlinx-serialization-core | kotlinx.serialization.core.artifact_disambiguating_module ⚙️ | 2026-04-09 | 5,3 | 0,5 | 1.11.0 | 1.11.0 | 33 | 3 | 4 | 3 |
| 493 | software.amazon.ion:ion-java 🚩 | software.amazon.ion ⚙️ | 2019-09-25 | 10,1 | 7,9 | 1.5.1 | 1.5.1 | 13 | 6 | 0 | 0 |
| 494 | org.slf4j:slf4j-simple | org.slf4j.simple ✳️ | 2026-05-12 | 20,8 | 9,2 | 2.0.18 | 2.0.18 | 116 | 48 | 1 | 1 |
| 495 | org.slf4j:slf4j-jdk14 | org.slf4j.jul ✳️ | 2026-05-12 | 20,8 | 9,2 | 2.0.18 | 2.0.18 | 116 | 48 | 1 | 1 |
| 496 | org.codehaus.jackson:jackson-mapper-asl 🚩 |  | 2013-07-15 | 17,4 |  | 1.9.13 |  | 80 | 0 | 0 | 0 |
| 497 | info.picocli:picocli ⚠️ | info.picocli ✳️ | 2025-04-19 | 9,1 | 8,6 | 4.7.7 | 4.7.7 | 86 | 73 | 0 | 0 |
| 498 | io.projectreactor.netty:reactor-netty-core | reactor.netty.core ⚙️ | 2026-04-14 | 5,6 | 5,6 | 1.3.5 | 1.3.5 | 113 | 113 | 23 | 23 |
| ~~499~~ | ~~org.apache.maven.shared:maven-doxia-tools 🚩~~ | ~~-~~ | ~~2011-04-30~~ | ~~18,0~~ | ~~-~~ | ~~1.4~~ | ~~-~~ | ~~8~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| 500 | com.google.api-client:google-api-client | google.api.client ⚙️ | 2026-02-24 | 14,8 | 7,0 | 2.9.0 | 2.9.0 | 91 | 53 | 2 | 2 |
| ~~501~~ | ~~org.apache.maven.doxia:doxia-module-xhtml5~~ | ~~-~~ | ~~2026-03-17~~ | ~~7,0~~ | ~~-~~ | ~~2.1.0~~ | ~~-~~ | ~~19~~ | ~~0~~ | ~~1~~ | ~~0~~ |
| 502 | com.google.oauth-client:google-oauth-client ⚠️ | com.google.api.client.auth ⚙️ | 2025-05-16 | 14,8 | 6,7 | 1.38.2 | 1.38.2 | 58 | 25 | 0 | 0 |
| 503 | io.projectreactor.netty:reactor-netty-http | reactor.netty.http ⚙️ | 2026-04-14 | 5,6 | 5,6 | 1.3.5 | 1.3.5 | 113 | 113 | 23 | 23 |
| 504 | com.sun.activation:javax.activation 🚩 | java.activation ⚙️ | 2017-09-06 | 8,7 | 8,7 | 1.2.0 | 1.2.0 | 1 | 1 | 0 | 0 |
| 505 | com.squareup.moshi:moshi ⚠️ | com.squareup.moshi ⚙️ | 2024-12-05 | 11,0 | 8,1 | 1.15.2 | 1.15.2 | 24 | 12 | 0 | 0 |
| 506 | org.jetbrains.kotlinx:kotlinx-coroutines-jdk8 | kotlinx.coroutines.jdk8 🏷️ | 2026-05-07 | 8,7 | 3,2 | 1.11.0 | 1.11.0 | 105 | 20 | 3 | 3 |
| 507 | org.eclipse.angus:angus-activation | org.eclipse.angus.activation ✳️ | 2025-09-11 | 4,8 | 4,8 | 2.0.3 | 2.0.3 | 9 | 9 | 2 | 2 |
| 508 | com.fasterxml.jackson.datatype:jackson-datatype-guava | com.fasterxml.jackson.datatype.guava ✳️ | 2026-06-01 | 14,3 | 8,6 | 2.22.0 | 2.22.0 | 175 | 92 | 18 | 18 |
| 509 | org.conscrypt:conscrypt-openjdk-uber | org.conscrypt ⚙️ | 2026-05-28 | 9,3 | 7,6 | 2.6-alpha2 | 2.6-alpha2 | 36 | 12 | 2 | 2 |
| 510 | com.googlecode.libphonenumber:libphonenumber | com.google.i18n.phonenumbers.libphonenumber ⚙️ | 2026-05-22 | 15,2 | 1,5 | 9.0.31 | 9.0.31 | 302 | 36 | 25 | 25 |
| 511 | org.jetbrains.kotlinx:kotlinx-coroutines-test-jvm | kotlinx.coroutines.test 🏷️ | 2026-05-07 | 4,5 | 3,2 | 1.11.0 | 1.11.0 | 31 | 20 | 3 | 3 |
| ~~512~~ | ~~org.springframework.integration:spring-integration-bom~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| 513 | io.grpc:grpc-netty-shaded | io.netty.internal.tcnative ✳️ | 2026-04-30 | 8,4 | 2,7 | 1.81.0 | 2.0.75.Final | 158 | 42 | 12 | 12 |
| 514 | com.diffplug.spotless:spotless-lib |  | 2026-05-27 | 9,4 |  | 4.6.2 |  | 154 | 0 | 12 | 0 |
| 515 | com.googlecode.json-simple:json-simple 🚩 |  | 2012-03-21 | 16,8 |  | 1.1.1 |  | 2 | 0 | 0 | 0 |
| 516 | com.fasterxml.jackson.datatype:jackson-datatype-joda | com.fasterxml.jackson.datatype.joda ✳️ | 2026-06-01 | 14,2 | 8,6 | 2.22.0 | 2.22.0 | 177 | 92 | 18 | 18 |
| 517 | com.sun.jersey:jersey-core 🚩 |  | 2017-05-24 | 16,0 |  | 1.19.4 |  | 98 | 0 | 0 | 0 |
| 518 | org.fusesource.jansi:jansi | org.fusesource.jansi ✳️ | 2026-03-27 | 16,2 | 7,2 | 2.4.3 | 2.4.3 | 32 | 15 | 1 | 1 |
| 519 | org.jetbrains.kotlinx:kotlinx-serialization-json | kotlinx.serialization.json.artifact_disambiguating_module ⚙️ | 2026-04-09 | 5,3 | 0,5 | 1.11.0 | 1.11.0 | 33 | 3 | 4 | 3 |
| 520 | org.mozilla:rhino | org.mozilla.rhino 🏷️ | 2026-02-15 | 14,9 | 4,5 | 1.9.1 | 1.9.1 | 24 | 8 | 4 | 4 |
| 521 | com.github.spotbugs:spotbugs-annotations | com.github.spotbugs.annotations ⚙️ | 2025-10-18 | 9,3 | 8,7 | 4.9.8 | 4.9.8 | 72 | 67 | 5 | 5 |
| 522 | io.swagger.core.v3:swagger-models | io.swagger.v3.oas.models ⚙️ | 2026-05-12 | 8,5 | 7,1 | 2.2.50 | 2.2.50 | 78 | 68 | 18 | 18 |
| 523 | org.testng:testng | org.testng ⚙️ | 2026-01-22 | 15,9 | 7,4 | 7.12.0 | 7.12.0 | 90 | 19 | 1 | 1 |
| 524 | org.bouncycastle:bcutil-jdk18on | org.bouncycastle.util 🏷️ | 2026-05-15 | 4,2 | 4,2 | 1.81.1 | 1.81.1 | 18 | 18 | 6 | 6 |
| 525 | com.diffplug.spotless:spotless-lib-extra |  | 2026-05-27 | 9,4 |  | 4.6.2 |  | 154 | 0 | 12 | 0 |
| 526 | org.bouncycastle:bcpkix-jdk18on | org.bouncycastle.pkix 🏷️ | 2026-05-15 | 4,2 | 4,2 | 1.81.1 | 1.81.1 | 18 | 18 | 6 | 6 |
| ~~527~~ | ~~org.apache.maven:maven-builder-support~~ | ~~-~~ | ~~2026-05-13~~ | ~~11,2~~ | ~~-~~ | ~~3.9.16~~ | ~~-~~ | ~~57~~ | ~~0~~ | ~~9~~ | ~~0~~ |
| 528 | org.reflections:reflections 🚩 | org.reflections ⚙️ | 2021-10-25 | 14,8 | 4,7 | 0.10.2 | 0.10.2 | 17 | 2 | 0 | 0 |
| 529 | com.googlecode.juniversalchardet:juniversalchardet 🚩 |  | 2011-09-19 | 14,7 |  | 1.0.3 |  | 1 | 0 | 0 | 0 |
| 530 | com.squareup.retrofit2:retrofit ⚠️ | retrofit2 ⚙️ | 2025-05-15 | 10,4 | 8,2 | 3.0.0 | 3.0.0 | 26 | 18 | 0 | 0 |
| 531 | org.springframework.retry:spring-retry ⚠️ |  | 2025-05-16 | 14,9 |  | 2.0.12 |  | 34 | 0 | 0 | 0 |
| 532 | org.springframework.boot:spring-boot-starter-reactor-netty | spring.boot.starter.reactor.netty ⚙️ | 2026-04-23 | 8,3 | 8,3 | 3.5.14 | 3.5.14 | 221 | 221 | 39 | 39 |
| 533 | org.springframework.boot:spring-boot-starter-webflux | spring.boot.starter.webflux ⚙️ | 2026-04-23 | 8,3 | 8,3 | 3.5.14 | 3.5.14 | 221 | 221 | 39 | 39 |
| 534 | org.codehaus.jettison:jettison |  | 2026-03-19 | 19,6 |  | 1.5.5 |  | 25 | 0 | 1 | 0 |
| 535 | org.scala-lang.modules:scala-collection-compat_2.12 | scala.collection.compat ⚙️ | 2025-10-07 | 8,0 | 5,1 | 2.14.0 | 2.14.0 | 36 | 12 | 1 | 1 |
| 536 | org.hamcrest:hamcrest-library ⚠️ | org.hamcrest.library.deprecated ⚙️ | 2024-08-01 | 19,1 | 7,5 | 3.0 | 3.0 | 13 | 7 | 0 | 0 |
| 537 | io.prometheus:simpleclient 🚩 |  | 2022-06-15 | 11,5 |  | 0.16.0 |  | 40 | 0 | 0 | 0 |
| 538 | it.unimi.dsi:fastutil | it.unimi.dsi.fastutil ⚙️ | 2025-10-05 | 15,3 | 8,0 | 8.5.18 | 8.5.18 | 79 | 28 | 3 | 3 |
| 539 | com.fasterxml.jackson.dataformat:jackson-dataformat-csv | com.fasterxml.jackson.dataformat.csv ✳️ | 2026-06-01 | 14,4 | 8,6 | 2.22.0 | 2.22.0 | 177 | 92 | 18 | 18 |
| 540 | org.tensorflow:tensorflow-lite-metadata ⚠️ |  | 2025-01-24 | 5,8 |  | 0.5.0 |  | 20 | 0 | 0 | 0 |
| 541 | org.slf4j:slf4j-log4j12 🚩 |  | 2022-01-13 | 20,6 |  | 2.0.0-alpha6 |  | 87 | 0 | 0 | 0 |
| ~~542~~ | ~~org.springframework.amqp:spring-amqp-bom~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| 543 | org.apache.groovy:groovy | org.apache.groovy ⚙️ | 2026-05-04 | 5,6 | 5,6 | 6.0.0-alpha-1 | 6.0.0-alpha-1 | 63 | 63 | 15 | 15 |
| 544 | org.apache.ivy:ivy ⚠️ | org.apache.ivy ⚙️ | 2024-12-23 | 18,5 | 8,1 | 2.5.3 | 2.5.3 | 19 | 4 | 0 | 0 |
| 545 | software.amazon.awssdk:arns | software.amazon.awssdk.arns ⚙️ | 2026-05-27 | 6,6 | 6,6 | 2.44.14 | 2.44.14 | 1582 | 1582 | 234 | 234 |
| 546 | org.apache.poi:poi | org.apache.poi.poi 🏷️ | 2025-11-26 | 19,0 | 5,4 | 5.5.1 | 5.5.1 | 67 | 13 | 2 | 2 |
| 547 | com.google.http-client:google-http-client-apache-v2 | com.google.api.client.http.apache.v2 ⚙️ | 2026-01-23 | 7,0 | 7,0 | 2.1.0 | 2.1.0 | 56 | 56 | 7 | 7 |
| 548 | org.glassfish.jersey.core:jersey-common |  | 2026-03-18 | 14,3 |  | 2.48 |  | 154 | 6 | 11 | 6 |
| 549 | net.sf.kxml:kxml2 🚩 |  | 2009-04-21 | 20,8 |  | 2.3.0 |  | 4 | 0 | 0 | 0 |
| 550 | com.google.api:api-common | com.google.api.apicommon ⚙️ | 2026-05-06 | 9,7 | 7,1 | 2.63.0 | 2.63.0 | 127 | 113 | 27 | 27 |
| 551 | com.google.http-client:google-http-client-jackson2 | com.google.api.client.json.jackson2 ⚙️ | 2026-01-23 | 13,8 | 6,7 | 2.1.0 | 2.1.0 | 79 | 52 | 7 | 7 |
| 552 | io.prometheus:simpleclient_common 🚩 |  | 2022-06-15 | 11,5 |  | 0.16.0 |  | 40 | 0 | 0 | 0 |
| 553 | com.google.auto.service:auto-service-annotations ⚠️ | com.google.auto.service ⚙️ | 2023-06-12 | 7,2 | 6,1 | 1.1.1 | 1.1.1 | 8 | 6 | 0 | 0 |
| 554 | jakarta.ws.rs:jakarta.ws.rs-api ⚠️ | jakarta.ws.rs ✳️ | 2024-04-02 | 7,7 | 7,7 | 4.0.0 | 4.0.0 | 10 | 10 | 0 | 0 |
| 555 | org.apache.yetus:audience-annotations ⚠️ |  | 2025-02-16 | 9,8 |  | 0.15.1 |  | 16 | 0 | 0 | 0 |
| 556 | org.apache.xmlbeans:xmlbeans ⚠️ | org.apache.xmlbeans 🏷️ | 2024-12-04 | 18,9 | 5,6 | 5.3.0 | 5.3.0 | 19 | 10 | 0 | 0 |
| 557 | javax.ws.rs:javax.ws.rs-api 🚩 | java.ws.rs 🏷️ | 2018-08-24 | 14,3 | 9,0 | 2.1.1 | 2.1.1 | 32 | 3 | 0 | 0 |
| 558 | org.springframework.boot:spring-boot-configuration-processor | spring.boot.configuration.processor ⚙️ | 2026-04-23 | 11,5 | 8,3 | 3.5.14 | 3.5.14 | 270 | 221 | 39 | 39 |
| 559 | com.sun.jersey:jersey-client 🚩 |  | 2017-05-24 | 16,0 |  | 1.19.4 |  | 98 | 0 | 0 | 0 |
| 560 | com.google.auto:auto-common ⚠️ |  | 2023-06-12 | 12,0 |  | 1.2.2 |  | 19 | 0 | 0 | 0 |
| 561 | org.apache.poi:poi-ooxml | org.apache.poi.ooxml 🏷️ | 2025-11-26 | 17,5 | 5,4 | 5.5.1 | 5.5.1 | 56 | 13 | 2 | 2 |
| 562 | software.amazon.awssdk:aws-xml-protocol | software.amazon.awssdk.protocols.xml ⚙️ | 2026-05-27 | 7,5 | 6,4 | 2.44.14 | 2.44.14 | 1757 | 1530 | 234 | 234 |
| 563 | com.nimbusds:oauth2-oidc-sdk |  | 2026-05-19 | 13,1 |  | 11.37.2 |  | 442 | 0 | 21 | 0 |
| 564 | software.amazon.awssdk:s3 | software.amazon.awssdk.services.s3 ⚙️ | 2026-05-27 | 8,9 | 7,5 | 2.44.14 | 2.44.14 | 1768 | 1756 | 234 | 234 |
| 565 | com.amazonaws:aws-java-sdk-sts |  | 2025-12-29 | 11,6 |  | 1.12.797 |  | 1956 | 0 | 14 | 0 |
| 566 | org.freemarker:freemarker ⚠️ | freemarker ⚙️ | 2024-12-08 | 19,2 | 5,3 | 2.3.34 | 2.3.34 | 26 | 4 | 0 | 0 |
| 567 | org.springframework:spring-oxm | spring.oxm ⚙️ | 2026-04-17 | 16,5 | 8,7 | 6.2.18 | 6.2.18 | 286 | 193 | 27 | 27 |
| 568 | io.swagger.core.v3:swagger-core | io.swagger.v3.core ⚙️ | 2026-05-12 | 8,5 | 7,1 | 2.2.50 | 2.2.50 | 78 | 68 | 18 | 18 |
| 569 | org.antlr:ST4 🚩 |  | 2022-09-02 | 14,9 |  | 4.3.4 |  | 13 | 0 | 0 | 0 |
| 570 | io.swagger:swagger-models ⚠️ | io.swagger.models ⚙️ | 2025-05-15 | 11,0 | 3,2 | 1.6.16 | 1.6.16 | 45 | 7 | 0 | 0 |
| 571 | org.glassfish.jersey.core:jersey-client |  | 2026-03-18 | 14,3 |  | 2.48 |  | 154 | 6 | 11 | 6 |
| 572 | io.reactivex.rxjava3:rxjava | io.reactivex.rxjava3 ✳️ | 2025-09-24 | 6,9 | 6,9 | 3.1.12 | 3.1.12 | 48 | 48 | 4 | 4 |
| ~~573~~ | ~~org.apache.maven.reporting:maven-reporting-exec~~ | ~~-~~ | ~~2026-05-12~~ | ~~14,9~~ | ~~-~~ | ~~2.0.1~~ | ~~-~~ | ~~25~~ | ~~0~~ | ~~1~~ | ~~0~~ |
| 574 | com.google.api:gax |  | 2026-05-06 | 10,3 |  | 2.80.0 |  | 271 | 0 | 27 | 0 |
| 575 | org.jacoco:jacoco-maven-plugin |  | 2025-10-11 | 14,9 |  | 0.8.14 |  | 39 | 0 | 1 | 0 |
| 576 | org.codehaus.groovy:groovy-xml ⚠️ | org.codehaus.groovy.xml ⚙️ | 2025-05-27 | 14,0 | 8,3 | 3.0.25 | 3.0.25 | 151 | 65 | 0 | 0 |
| 577 | org.scala-sbt:util-interface |  | 2026-05-17 | 10,7 |  | 2.0.0-RC13 |  | 190 | 0 | 35 | 0 |
| 578 | io.opentelemetry:opentelemetry-semconv ⚠️ | io.opentelemetry.semconv ⚙️ | 2023-09-11 | 5,4 | 5,4 | 1.30.1-alpha | 1.30.1-alpha | 46 | 46 | 0 | 0 |
| 579 | org.apache.hadoop:hadoop-client-api |  | 2026-03-24 | 9,4 |  | 3.5.0 |  | 30 | 0 | 3 | 0 |
| 580 | org.apache.commons:commons-csv 🚩 |  | 2021-07-24 | 11,8 |  | 1.9.0 |  | 10 | 0 | 0 | 0 |
| 581 | io.opentelemetry:opentelemetry-sdk-extension-autoconfigure | io.opentelemetry.sdk.autoconfigure ⚙️ | 2026-05-08 | 5,4 | 5,4 | 1.62.0 | 1.62.0 | 83 | 83 | 14 | 14 |
| 582 | jakarta.servlet:jakarta.servlet-api | jakarta.servlet ✳️ | 2026-05-18 | 7,4 | 6,8 | 6.2.0-M2 | 6.2.0-M2 | 12 | 11 | 2 | 2 |
| 583 | software.amazon.awssdk:identity-spi | software.amazon.awssdk.identity.spi ⚙️ | 2026-05-27 | 2,6 | 2,6 | 2.44.14 | 2.44.14 | 636 | 636 | 234 | 234 |
| 584 | software.amazon.awssdk:http-auth-spi | software.amazon.awssdk.http.auth.spi ⚙️ | 2026-05-27 | 2,6 | 2,6 | 2.44.14 | 2.44.14 | 636 | 636 | 234 | 234 |
| ~~585~~ | ~~org.apache.maven.plugins:maven-release-plugin~~ | ~~-~~ | ~~2025-12-09~~ | ~~20,1~~ | ~~-~~ | ~~3.3.1~~ | ~~-~~ | ~~36~~ | ~~0~~ | ~~3~~ | ~~0~~ |
| 586 | software.amazon.awssdk:http-auth-aws | software.amazon.awssdk.http.auth.aws ⚙️ | 2026-05-27 | 2,6 | 2,6 | 2.44.14 | 2.44.14 | 636 | 636 | 234 | 234 |
| 587 | org.apache.hadoop:hadoop-annotations |  | 2026-03-24 | 14,3 |  | 3.5.0 |  | 83 | 0 | 3 | 0 |
| 588 | io.grpc:grpc-auth | io.grpc.auth ⚙️ | 2026-04-30 | 11,0 | 2,7 | 1.81.0 | 1.81.0 | 189 | 42 | 12 | 12 |
| 589 | software.amazon.awssdk:http-auth | software.amazon.awssdk.http.auth ⚙️ | 2026-05-27 | 2,6 | 2,6 | 2.44.14 | 2.44.14 | 636 | 636 | 234 | 234 |
| 590 | software.amazon.awssdk:checksums-spi | software.amazon.awssdk.checksums.spi ⚙️ | 2026-05-27 | 2,6 | 2,6 | 2.44.14 | 2.44.14 | 636 | 636 | 234 | 234 |
| 591 | software.amazon.awssdk:checksums | software.amazon.awssdk.checksums ⚙️ | 2026-05-27 | 2,6 | 2,6 | 2.44.14 | 2.44.14 | 636 | 636 | 234 | 234 |
| 592 | org.testcontainers:testcontainers |  | 2026-04-20 | 10,9 |  | 2.0.5 |  | 113 | 0 | 9 | 0 |
| 593 | software.amazon.awssdk:sts | software.amazon.awssdk.services.sts ⚙️ | 2026-05-27 | 8,9 | 7,5 | 2.44.14 | 2.44.14 | 1768 | 1756 | 234 | 234 |
| 594 | org.seleniumhq.selenium:selenium-api | org.seleniumhq.selenium.api 🏷️ | 2026-05-12 | 15,3 | 7,8 | 4.44.0 | 4.44.0 | 183 | 85 | 11 | 11 |
| 595 | io.grpc:grpc-services | io.grpc.services ⚙️ | 2026-04-30 | 9,9 | 2,7 | 1.81.0 | 1.81.0 | 177 | 42 | 12 | 12 |
| 596 | io.netty:netty 🚩 |  | 2016-06-29 | 14,4 |  | 3.10.6.Final |  | 71 | 0 | 0 | 0 |
| 597 | org.apache.velocity:velocity-engine-core ⚠️ |  | 2024-10-14 | 8,9 |  | 2.4.1 |  | 6 | 0 | 0 | 0 |
| 598 | org.seleniumhq.selenium:selenium-remote-driver | org.seleniumhq.selenium.remote_driver 🏷️ | 2026-05-12 | 15,3 | 7,8 | 4.44.0 | 4.44.0 | 183 | 85 | 11 | 11 |
| 599 | org.glassfish.hk2:hk2-utils | org.glassfish.hk2.utilities ⚙️ | 2026-04-28 | 14,1 | 6,0 | 4.0.1 | 4.0.1 | 235 | 17 | 3 | 3 |
| 600 | org.glassfish.hk2:hk2-locator | org.glassfish.hk2.locator ⚙️ | 2026-04-28 | 14,1 | 6,0 | 4.0.1 | 4.0.1 | 238 | 17 | 3 | 3 |
| 601 | io.opentelemetry:opentelemetry-exporter-logging | io.opentelemetry.exporter.logging ⚙️ | 2026-05-08 | 5,6 | 5,6 | 1.62.0 | 1.62.0 | 88 | 88 | 14 | 14 |
| 602 | com.github.docker-java:docker-java-transport-zerodep | com.github.dockerjava.transport.zerodep ⚙️ | 2026-03-18 | 6,0 | 3,5 | 3.7.1 | 3.7.1 | 30 | 18 | 5 | 5 |
| 603 | org.apache.hadoop:hadoop-client-runtime |  | 2026-03-24 | 9,4 |  | 3.5.0 |  | 30 | 8 | 3 | 0 |
| 604 | org.seleniumhq.selenium:selenium-support | org.seleniumhq.selenium.support 🏷️ | 2026-05-12 | 16,5 | 7,1 | 4.44.0 | 4.44.0 | 190 | 81 | 11 | 11 |
| 605 | org.apache.groovy:groovy-xml | org.apache.groovy.xml ⚙️ | 2026-05-04 | 5,6 | 5,6 | 6.0.0-alpha-1 | 6.0.0-alpha-1 | 63 | 63 | 15 | 15 |
| 606 | com.github.virtuald:curvesapi ⚠️ | com.github.virtuald.curvesapi ⚙️ | 2023-08-11 | 10,6 | 4,4 | 1.08 | 1.08 | 7 | 2 | 0 | 0 |
| 607 | org.glassfish.hk2:osgi-resource-locator ⚠️ |  | 2025-03-23 | 15,9 |  | 1.0.4 |  | 47 | 0 | 0 | 0 |
| 608 | org.apache.felix:maven-bundle-plugin |  | 2026-02-13 | 18,9 |  | 6.0.2 |  | 42 | 0 | 1 | 0 |
| 609 | org.apache.groovy:groovy-json | org.apache.groovy.json ⚙️ | 2026-05-04 | 5,6 | 5,6 | 6.0.0-alpha-1 | 6.0.0-alpha-1 | 63 | 63 | 15 | 15 |
| 610 | io.qameta.allure:allure-commandline 🚩 |  | 2021-05-10 | 5,1 |  | 2.13.10 |  | 1 | 0 | 0 | 0 |
| 611 | commons-fileupload:commons-fileupload 🚩 |  | 2018-12-24 | 20,5 |  | 1.4 |  | 13 | 0 | 0 | 0 |
| 612 | io.netty:netty-all | io.netty.all ⚙️ | 2026-05-20 | 13,3 | 8,3 | 4.1.134.Final | 4.1.134.Final | 247 | 109 | 26 | 13 |
| 613 | io.delta:delta-core_2.12 🚩 |  | 2023-05-24 | 7,1 |  | 2.4.0 |  | 24 | 0 | 0 | 0 |
| 614 | com.lmax:disruptor ⚠️ | com.lmax.disruptor 🏷️ | 2023-09-29 | 13,4 | 5,1 | 4.0.0 | 4.0.0 | 29 | 4 | 0 | 0 |
| 615 | org.webjars:swagger-ui |  | 2026-05-26 | 12,2 |  | 5.32.6 |  | 240 | 0 | 27 | 0 |
| ~~616~~ | ~~org.apache.maven.plugins:maven-war-plugin~~ | ~~-~~ | ~~2025-11-24~~ | ~~20,1~~ | ~~-~~ | ~~3.5.1~~ | ~~-~~ | ~~27~~ | ~~0~~ | ~~2~~ | ~~0~~ |
| 617 | org.glassfish.hk2.external:aopalliance-repackaged | org.aopalliance ⚙️ | 2026-04-28 | 12,5 | 6,0 | 4.0.1 | 4.0.1 | 129 | 17 | 3 | 3 |
| 618 | com.microsoft.sqlserver:mssql-jdbc | com.microsoft.sqlserver.jdbc ⚙️ | 2026-05-14 | 9,6 | 7,8 | 13.5.0.jre11-preview | 13.5.0.jre11-preview | 268 | 131 | 34 | 17 |
| 619 | org.codehaus.jackson:jackson-jaxrs 🚩 |  | 2013-07-15 | 17,2 |  | 1.9.13 |  | 79 | 0 | 0 | 0 |
| 620 | commons-httpclient:commons-httpclient 🚩 |  | 2007-08-21 | 21,1 |  | 3.1 |  | 31 | 0 | 0 | 0 |
| 621 | org.codehaus.jackson:jackson-xc 🚩 |  | 2013-07-15 | 16,9 |  | 1.9.13 |  | 71 | 0 | 0 | 0 |
| ~~622~~ | ~~org.apache.maven.plugins:maven-help-plugin ⚠️~~ | ~~-~~ | ~~2024-10-18~~ | ~~20,1~~ | ~~-~~ | ~~3.5.1~~ | ~~-~~ | ~~16~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| 623 | org.seleniumhq.selenium:selenium-firefox-driver | org.seleniumhq.selenium.firefox_driver 🏷️ | 2026-05-12 | 16,5 | 7,8 | 4.44.0 | 4.44.0 | 190 | 85 | 11 | 11 |
| 624 | org.ccil.cowan.tagsoup:tagsoup 🚩 |  | 2011-08-22 | 20,4 |  | 1.2.1 |  | 5 | 0 | 0 | 0 |
| 625 | org.apache.httpcomponents:httpcore-nio 🚩 | org.apache.httpcomponents.httpcore.nio ⚙️ | 2022-11-26 | 18,9 | 7,4 | 4.4.16 | 4.4.16 | 50 | 6 | 0 | 0 |
| 626 | org.robolectric:android-all-instrumented |  | 2025-08-22 | 5,3 |  | 15-robolectric-13954326-i7 |  | 112 | 0 | 4 | 0 |
| 627 | com.google.api.grpc:proto-google-iam-v1 |  | 2026-05-06 | 9,1 |  | 1.66.0 |  | 192 | 0 | 27 | 0 |
| 628 | org.jboss:jandex ⚠️ | org.jboss.jandex ⚙️ | 2024-05-07 | 15,8 | 6,2 | 2.4.5.Final | 2.4.5.Final | 52 | 14 | 0 | 0 |
| 629 | org.hibernate.orm:hibernate-core | org.hibernate.orm.core ⚙️ | 2026-05-26 | 7,5 | 7,5 | 7.4.0.Final | 7.4.0.Final | 244 | 244 | 122 | 122 |
| 630 | org.springframework.boot:spring-boot-devtools | spring.boot.devtools ⚙️ | 2026-04-23 | 10,5 | 8,3 | 3.5.14 | 3.5.14 | 261 | 221 | 39 | 39 |
| 631 | io.grpc:grpc-grpclb | io.grpc.grpclb ⚙️ | 2026-04-30 | 10,4 | 2,7 | 1.81.0 | 1.81.0 | 183 | 42 | 12 | 12 |
| 632 | org.sonarsource.scanner.api:sonar-scanner-api 🚩 |  | 2022-12-22 | 10,1 |  | 2.16.3.1081 |  | 14 | 0 | 0 | 0 |
| 633 | org.seleniumhq.selenium:selenium-chrome-driver | org.seleniumhq.selenium.chrome_driver 🏷️ | 2026-05-12 | 16,5 | 7,8 | 4.44.0 | 4.44.0 | 190 | 85 | 11 | 11 |
| 634 | stax:stax-api 🚩 |  | 2006-03-14 | 20,5 |  | 1.0.1 |  | 2 | 0 | 0 | 0 |
| 635 | net.logstash.logback:logstash-logback-encoder | logstash.logback.encoder ⚙️ | 2025-10-26 | 13,2 | 6,5 | 9.0 | 9.0 | 55 | 14 | 1 | 1 |
| 636 | org.glassfish.hk2:hk2-api | org.glassfish.hk2.api ⚙️ | 2026-04-28 | 15,0 | 6,0 | 4.0.1 | 4.0.1 | 270 | 17 | 3 | 3 |
| 637 | io.delta:delta-storage |  | 2026-04-10 | 4,1 |  | 4.2.0 |  | 24 | 0 | 4 | 0 |
| 638 | commons-logging:commons-logging-api 🚩 |  | 2006-05-13 | 20,5 |  | 1.1 |  | 3 | 0 | 0 | 0 |
| 639 | io.grpc:grpc-alts | io.grpc.alts ⚙️ | 2026-04-30 | 8,2 | 2,7 | 1.81.0 | 1.81.0 | 154 | 42 | 12 | 12 |
| 640 | com.vladsch.flexmark:flexmark 🚩 |  | 2023-05-23 | 9,4 |  | 0.64.8 |  | 228 | 0 | 0 | 0 |
| 641 | net.sf.saxon:Saxon-HE |  | 2026-05-29 | 14,2 |  | 13.0 |  | 92 | 0 | 4 | 0 |
| 642 | org.dom4j:dom4j | org.dom4j ⚙️ | 2025-06-30 | 10,9 | 3,4 | 2.2.0 | 2.2.0 | 11 | 3 | 2 | 2 |
| 643 | org.springframework.security:spring-security-oauth2-core | spring.security.oauth2.core ⚙️ | 2026-04-20 | 8,5 | 8,5 | 7.0.5 | 7.0.5 | 210 | 210 | 34 | 34 |
| 644 | com.google.api:gax-grpc |  | 2026-05-06 | 9,1 |  | 2.80.0 |  | 222 | 0 | 27 | 0 |
| 645 | com.nimbusds:lang-tag 🚩 |  | 2022-07-06 | 13,2 |  | 1.7 |  | 11 | 0 | 0 | 0 |
| 646 | io.opencensus:opencensus-proto 🚩 |  | 2019-03-13 | 7,7 |  | 0.2.0 |  | 3 | 0 | 0 | 0 |
| 647 | com.vladsch.flexmark:flexmark-util 🚩 |  | 2023-05-23 | 9,4 |  | 0.64.8 |  | 228 | 0 | 0 | 0 |
| 648 | org.scala-sbt:compiler-bridge_2.12 |  | 2026-05-14 | 9,4 |  | 2.0.0-M18 |  | 156 | 0 | 18 | 0 |
| 649 | io.prometheus:simpleclient_tracer_common 🚩 |  | 2022-06-15 | 5,0 |  | 0.16.0 |  | 7 | 0 | 0 | 0 |
| 650 | io.prometheus:simpleclient_tracer_otel_agent 🚩 |  | 2022-06-15 | 5,0 |  | 0.16.0 |  | 7 | 0 | 0 | 0 |
| 651 | io.prometheus:simpleclient_tracer_otel 🚩 |  | 2022-06-15 | 5,0 |  | 0.16.0 |  | 7 | 0 | 0 | 0 |
| 652 | io.mockk:mockk-dsl-jvm |  | 2026-05-29 | 8,6 |  | 1.14.11 |  | 104 | 0 | 7 | 0 |
| 653 | org.mongodb:bson | org.mongodb.bson ⚙️ | 2026-05-28 | 15,6 | 8,1 | 5.8.0 | 5.8.0 | 220 | 132 | 15 | 15 |
| 654 | org.seleniumhq.selenium:selenium-ie-driver | org.seleniumhq.selenium.ie_driver 🏷️ | 2026-05-12 | 16,5 | 7,8 | 4.44.0 | 4.44.0 | 190 | 85 | 11 | 11 |
| 655 | org.jetbrains.kotlin:kotlin-test |  | 2026-05-27 | 10,4 |  | 2.4.0-RC2 |  | 202 | 65 | 36 | 0 |
| 656 | org.codehaus.janino:janino ⚠️ | org.codehaus.janino ⚙️ | 2024-02-06 | 16,1 | 2,9 | 3.1.12 | 3.1.12 | 37 | 3 | 0 | 0 |
| 657 | org.seleniumhq.selenium:selenium-safari-driver | org.seleniumhq.selenium.safari_driver 🏷️ | 2026-05-12 | 14,1 | 7,8 | 4.44.0 | 4.44.0 | 156 | 85 | 11 | 11 |
| 658 | com.mysql:mysql-connector-j |  | 2026-04-22 | 3,6 |  | 9.7.0 |  | 15 | 0 | 4 | 0 |
| 659 | org.seleniumhq.selenium:selenium-java | org.seleniumhq.selenium.java 🏷️ | 2026-05-12 | 14,9 | 6,7 | 4.44.0 | 4.44.0 | 180 | 79 | 11 | 11 |
| 660 | com.fasterxml.jackson.dataformat:jackson-dataformat-toml | com.fasterxml.jackson.dataformat.toml ✳️ | 2026-06-01 | 5,1 | 5,1 | 2.22.0 | 2.22.0 | 63 | 63 | 18 | 18 |
| ~~661~~ | ~~org.apache.maven.plugins:maven-enforcer-plugin~~ | ~~-~~ | ~~2026-05-15~~ | ~~19,2~~ | ~~-~~ | ~~3.6.3~~ | ~~-~~ | ~~28~~ | ~~0~~ | ~~4~~ | ~~0~~ |
| 662 | org.xmlresolver:xmlresolver | org.xmlresolver.xmlresolver ⚙️ | 2026-05-04 | 11,2 | 4,3 | 6.0.23 | 6.0.23 | 90 | 48 | 5 | 5 |
| 663 | org.flywaydb:flyway-core |  | 2026-05-29 | 12,1 |  | 12.7.0 |  | 304 | 190 | 45 | 0 |
| 664 | io.opentelemetry:opentelemetry-api-events ⚠️ | io.opentelemetry.api.events ⚙️ | 2024-03-08 | 3,3 | 3,3 | 1.36.0-alpha | 1.36.0-alpha | 17 | 17 | 0 | 0 |
| 665 | com.squareup.moshi:moshi-kotlin ⚠️ | com.squareup.moshi.kotlin ⚙️ | 2024-12-05 | 9,0 | 8,1 | 1.15.2 | 1.15.2 | 17 | 12 | 0 | 0 |
| 666 | org.seleniumhq.selenium:selenium-edge-driver | org.seleniumhq.selenium.edge_driver 🏷️ | 2026-05-12 | 10,8 | 7,8 | 4.44.0 | 4.44.0 | 122 | 85 | 11 | 11 |
| 667 | org.hibernate:hibernate-core |  | 2026-05-26 | 17,7 |  | 7.4.0.Final |  | 287 | 113 | 6 | 1 |
| 668 | org.codehaus.janino:commons-compiler ⚠️ | org.codehaus.commons.compiler ⚙️ | 2024-02-06 | 14,4 | 2,9 | 3.1.12 | 3.1.12 | 32 | 3 | 0 | 0 |
| 669 | org.apache.hadoop:hadoop-yarn-api |  | 2026-03-24 | 14,3 |  | 3.5.0 |  | 83 | 0 | 3 | 0 |
| 670 | org.rnorth.duct-tape:duct-tape 🚩 |  | 2019-04-28 | 10,8 |  | 1.0.8 |  | 5 | 0 | 0 | 0 |
| 671 | com.opencsv:opencsv | com.opencsv ⚙️ | 2025-07-27 | 11,6 | 4,9 | 5.12.0 | 5.12.0 | 37 | 13 | 2 | 2 |
| 672 | io.mockk:mockk-agent-jvm |  | 2026-05-29 | 8,0 |  | 1.14.11 |  | 71 | 0 | 7 | 0 |
| ~~673~~ | ~~org.apache.maven.plugins:maven-failsafe-plugin~~ | ~~-~~ | ~~2026-05-25~~ | ~~16,4~~ | ~~-~~ | ~~3.5.6~~ | ~~-~~ | ~~57~~ | ~~0~~ | ~~3~~ | ~~0~~ |
| 674 | com.sun.jersey:jersey-json 🚩 |  | 2017-05-24 | 16,0 |  | 1.19.4 |  | 97 | 0 | 0 | 0 |
| 675 | org.apache.hadoop:hadoop-yarn-common |  | 2026-03-24 | 14,3 |  | 3.5.0 |  | 83 | 0 | 3 | 0 |
| 676 | org.junit.vintage:junit-vintage-engine | org.junit.vintage.engine ✳️ | 2026-05-19 | 9,9 | 8,9 | 6.1.0 | 6.1.0 | 110 | 106 | 22 | 22 |
| 677 | com.sun.jersey:jersey-server 🚩 |  | 2017-05-24 | 16,0 |  | 1.19.4 |  | 98 | 0 | 0 | 0 |
| 678 | commons-pool:commons-pool 🚩 |  | 2012-01-09 | 20,6 |  | 1.6 |  | 22 | 0 | 0 | 0 |
| 679 | org.apache.httpcomponents:httpasyncclient 🚩 | org.apache.httpcomponents.httpasyncclient ⚙️ | 2021-12-08 | 15,4 | 7,9 | 4.1.5 | 4.1.5 | 17 | 2 | 0 | 0 |
| 680 | javax.ws.rs:jsr311-api 🚩 |  | 2010-05-20 | 18,7 |  | 1.1-ea |  | 14 | 0 | 0 | 0 |
| 681 | org.springframework.cloud:spring-cloud-context |  | 2026-04-01 | 11,1 |  | 4.3.2 |  | 95 | 0 | 13 | 0 |
| 682 | org.glassfish.jersey.inject:jersey-hk2 |  | 2026-03-18 | 9,0 |  | 2.48 |  | 81 | 6 | 11 | 6 |
| 683 | com.google.api:gax-httpjson |  | 2026-05-06 | 8,6 |  | 2.80.0 |  | 200 | 0 | 27 | 0 |
| 684 | com.google.googlejavaformat:google-java-format | com.google.googlejavaformat ⚙️ | 2026-03-03 | 10,6 | 8,0 | 1.35.0 | 1.35.0 | 43 | 36 | 9 | 9 |
| 685 | io.micrometer:micrometer-registry-prometheus | micrometer.registry.prometheus ⚙️ | 2026-04-13 | 8,7 | 6,1 | 1.17.0-RC1 | 1.17.0-RC1 | 246 | 178 | 33 | 33 |
| 686 | com.nimbusds:content-type ⚠️ |  | 2023-11-05 | 6,3 |  | 2.3 |  | 5 | 0 | 0 | 0 |
| 687 | org.codehaus.mojo:build-helper-maven-plugin |  | 2025-06-04 | 20,3 |  | 3.6.1 |  | 22 | 0 | 1 | 0 |
| 688 | org.apache.thrift:libthrift | org.apache.thrift ⚙️ | 2026-05-08 | 15,1 | 5,3 | 0.23.0 | 0.23.0 | 25 | 13 | 2 | 2 |
| 689 | org.apache.lucene:lucene-core | org.apache.lucene.core ✳️ | 2026-02-25 | 20,0 | 4,5 | 10.4.0 | 10.4.0 | 155 | 30 | 7 | 7 |
| 690 | org.scala-lang.modules:scala-parser-combinators_2.12 ⚠️ | scala.util.parsing ⚙️ | 2024-04-18 | 9,6 | 4,7 | 2.4.0 | 2.4.0 | 17 | 5 | 0 | 0 |
| 691 | org.springframework.security:spring-security-oauth2-jose | spring.security.oauth2.jose ⚙️ | 2026-04-20 | 8,5 | 8,5 | 7.0.5 | 7.0.5 | 210 | 210 | 34 | 34 |
| 692 | io.smallrye:jandex | org.jboss.jandex ✳️ | 2025-12-02 | 3,8 | 3,8 | 3.5.3 | 3.5.3 | 38 | 38 | 6 | 6 |
| 693 | commons-configuration:commons-configuration 🚩 |  | 2013-10-24 | 20,6 |  | 1.10 |  | 22 | 0 | 0 | 0 |
| 694 | io.opentelemetry:opentelemetry-extension-incubator ⚠️ | io.opentelemetry.extension.incubator ⚙️ | 2024-03-08 | 5,2 | 5,2 | 1.36.0-alpha | 1.36.0-alpha | 45 | 45 | 0 | 0 |
| ~~695~~ | ~~org.apache.maven.resolver:maven-resolver-spi~~ | ~~org.apache.maven.resolver.spi ✳️~~ | ~~2026-05-14~~ | ~~9,4~~ | ~~8,9~~ | ~~2.0.18~~ | ~~2.0.18~~ | ~~73~~ | ~~72~~ | ~~12~~ | ~~12~~ |
| ~~696~~ | ~~org.apache.maven.resolver:maven-resolver-impl~~ | ~~org.apache.maven.resolver.impl ⚙️~~ | ~~2026-05-14~~ | ~~9,4~~ | ~~8,9~~ | ~~2.0.18~~ | ~~2.0.18~~ | ~~73~~ | ~~72~~ | ~~12~~ | ~~12~~ |
| 697 | com.vladsch.flexmark:flexmark-ext-tables 🚩 |  | 2023-05-23 | 9,4 |  | 0.64.8 |  | 228 | 0 | 0 | 0 |
| 698 | org.apache.hadoop:hadoop-common |  | 2026-03-24 | 14,5 |  | 3.5.0 |  | 84 | 0 | 3 | 0 |
| 699 | net.sf.opencsv:opencsv 🚩 |  | 2011-07-28 | 19,2 |  | 2.3 |  | 6 | 0 | 0 | 0 |
| 700 | org.mapstruct:mapstruct | org.mapstruct ⚙️ | 2026-02-01 | 13,0 | 8,9 | 1.7.0.Beta1 | 1.7.0.Beta1 | 48 | 31 | 1 | 1 |
| 701 | software.amazon.awssdk:crt-core | software.amazon.awssdk.crtcore ⚙️ | 2026-05-27 | 3,2 | 3,2 | 2.44.14 | 2.44.14 | 771 | 771 | 234 | 234 |
| 702 | org.apache.pdfbox:fontbox | org.apache.fontbox ⚙️ | 2026-03-12 | 16,3 | 6,7 | 2.0.36 | 2.0.36 | 80 | 31 | 4 | 4 |
| 703 | io.rest-assured:rest-assured-common |  | 2026-01-16 | 10,0 |  | 5.5.7 |  | 44 | 0 | 3 | 0 |
| 704 | org.springframework.cloud:spring-cloud-commons |  | 2026-04-01 | 11,2 |  | 4.3.2 |  | 96 | 0 | 13 | 0 |
| 705 | io.rest-assured:json-path |  | 2026-01-16 | 10,0 |  | 5.5.7 |  | 44 | 0 | 3 | 0 |
| 706 | org.spark-project.spark:unused 🚩 |  | 2014-10-22 | 11,6 |  | 1.0.0 |  | 1 | 0 | 0 | 0 |
| ~~707~~ | ~~org.apache.maven:maven-resolver-provider~~ | ~~-~~ | ~~2026-05-13~~ | ~~9,3~~ | ~~-~~ | ~~3.9.16~~ | ~~-~~ | ~~54~~ | ~~0~~ | ~~9~~ | ~~0~~ |
| 708 | com.zaxxer:SparseBitSet ⚠️ | com.zaxxer.sparsebitset ⚙️ | 2023-09-06 | 12,0 | 2,7 | 1.3 | 1.3 | 4 | 1 | 0 | 0 |
| 709 | org.mongodb:mongodb-driver-core | org.mongodb.driver.core ⚙️ | 2026-05-28 | 11,4 | 8,1 | 5.8.0 | 5.8.0 | 167 | 132 | 15 | 15 |
| 710 | io.rest-assured:rest-assured |  | 2026-01-16 | 10,0 |  | 5.5.7 |  | 44 | 0 | 3 | 0 |
| 711 | org.apache.zookeeper:zookeeper-jute |  | 2026-02-11 | 7,1 |  | 3.9.5 |  | 27 | 0 | 4 | 0 |
| 712 | io.rest-assured:xml-path |  | 2026-01-16 | 10,0 |  | 5.5.7 |  | 44 | 0 | 3 | 0 |
| 713 | com.github.spullara.mustache.java:compiler ⚠️ | com.github.mustachejava ⚙️ | 2024-07-07 | 14,7 | 7,4 | 0.9.14 | 0.9.14 | 45 | 7 | 0 | 0 |
| ~~714~~ | ~~org.springframework.pulsar:spring-pulsar-bom~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~-~~ | ~~0~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| 715 | com.google.cloud:google-cloud-core |  | 2026-05-06 | 9,8 |  | 2.70.0 |  | 315 | 0 | 27 | 0 |
| 716 | org.apache.pdfbox:pdfbox | org.apache.pdfbox ⚙️ | 2026-03-12 | 16,3 | 6,7 | 2.0.36 | 2.0.36 | 80 | 31 | 4 | 4 |
| 717 | org.jetbrains.kotlinx:kotlinx-coroutines-reactive | kotlinx.coroutines.reactive 🏷️ | 2026-05-07 | 8,7 | 3,2 | 1.11.0 | 1.11.0 | 105 | 20 | 3 | 3 |
| 718 | org.apache.curator:curator-client |  | 2025-07-04 | 13,1 |  | 5.9.0 |  | 42 | 0 | 1 | 0 |
| 719 | io.github.x-stream:mxparser | io.github.xstream.mxparser ⚙️ | 2025-10-12 | 5,4 | 5,4 | 1.2.3 | 1.2.3 | 4 | 4 | 1 | 1 |
| 720 | org.springframework.boot:spring-boot-starter-cache | spring.boot.starter.cache ⚙️ | 2026-04-23 | 10,5 | 8,3 | 3.5.14 | 3.5.14 | 261 | 221 | 39 | 39 |
| 721 | org.springframework.boot:spring-boot-gradle-plugin | spring.boot.gradle.plugin ⚙️ | 2026-04-23 | 12,2 | 6,0 | 3.5.14 | 3.5.14 | 286 | 178 | 39 | 39 |
| 722 | io.dropwizard.metrics:metrics-jvm | com.codahale.metrics.jvm ⚙️ | 2026-05-25 | 11,7 | 8,2 | 4.2.39 | 4.2.39 | 114 | 87 | 7 | 7 |
| 723 | io.swagger:swagger-core ⚠️ | io.swagger.core ⚙️ | 2025-05-15 | 11,0 | 3,2 | 1.6.16 | 1.6.16 | 45 | 7 | 0 | 0 |
| 724 | org.apache.curator:curator-framework |  | 2025-07-04 | 13,1 |  | 5.9.0 |  | 42 | 0 | 1 | 0 |
| 725 | com.github.java-json-tools:jackson-coreutils 🚩 |  | 2020-05-27 | 8,1 |  | 2.0 |  | 5 | 0 | 0 | 0 |
| 726 | com.amazonaws:aws-java-sdk-sqs |  | 2025-12-29 | 11,6 |  | 1.12.797 |  | 1956 | 0 | 14 | 0 |
| 727 | io.grpc:grpc-util | io.grpc.util ⚙️ | 2026-04-30 | 2,7 | 2,7 | 1.81.0 | 1.81.0 | 42 | 42 | 12 | 12 |
| 728 | com.typesafe.netty:netty-reactive-streams | com.typesafe.netty.core ⚙️ | 2026-05-13 | 10,8 | 7,3 | 2.0.18 | 2.0.18 | 30 | 17 | 4 | 4 |
| 729 | org.apache.hadoop:hadoop-auth |  | 2026-03-24 | 14,3 |  | 3.5.0 |  | 83 | 0 | 3 | 0 |
| 730 | org.springframework.security:spring-security-test | spring.security.test ⚙️ | 2026-04-20 | 11,2 | 8,5 | 7.0.5 | 7.0.5 | 242 | 210 | 34 | 34 |
| 731 | com.vladsch.flexmark:flexmark-ext-gfm-strikethrough 🚩 |  | 2023-05-23 | 9,4 |  | 0.64.8 |  | 228 | 0 | 0 | 0 |
| 732 | org.scala-sbt:compiler-interface |  | 2026-05-14 | 10,6 |  | 2.0.0-M18 |  | 175 | 0 | 18 | 0 |
| 733 | org.glassfish:javax.json 🚩 | java.json ⚙️ | 2018-11-06 | 13,7 | 9,3 | 1.1.4 | 1.1.4 | 17 | 6 | 0 | 0 |
| 734 | org.codehaus.groovy:groovy-all ⚠️ |  | 2025-05-27 | 18,9 |  | 3.0.25 |  | 221 | 4 | 0 | 0 |
| 735 | org.springframework.security:spring-security-rsa ⚠️ |  | 2024-09-06 | 11,3 |  | 1.0.13.RELEASE |  | 19 | 0 | 0 | 0 |
| 736 | com.vladsch.flexmark:flexmark-ext-wikilink 🚩 |  | 2023-05-23 | 9,4 |  | 0.64.8 |  | 228 | 0 | 0 | 0 |
| 737 | org.mortbay.jetty:jetty-util 🚩 |  | 2010-11-10 | 20,6 |  | 6.1.26 |  | 89 | 0 | 0 | 0 |
| 738 | com.github.jknack:handlebars | com.github.jknack.handlebars ✳️ | 2026-05-05 | 13,8 | 2,2 | 4.5.1 | 4.5.1 | 55 | 3 | 2 | 2 |
| 739 | com.vladsch.flexmark:flexmark-ext-superscript 🚩 |  | 2023-05-23 | 9,4 |  | 0.64.8 |  | 222 | 0 | 0 | 0 |
| 740 | com.vladsch.flexmark:flexmark-ext-ins 🚩 |  | 2023-05-23 | 9,4 |  | 0.64.8 |  | 222 | 0 | 0 | 0 |
| 741 | com.vladsch.flexmark:flexmark-ext-emoji 🚩 |  | 2023-05-23 | 9,4 |  | 0.64.8 |  | 228 | 0 | 0 | 0 |
| 742 | org.apache.curator:curator-recipes |  | 2025-07-04 | 13,1 |  | 5.9.0 |  | 42 | 0 | 1 | 0 |
| 743 | org.jetbrains.dokka:dokka-core |  | 2026-03-26 | 5,5 |  | 2.2.0 |  | 26 | 0 | 4 | 0 |
| 744 | io.spring.gradle:dependency-management-plugin ⚠️ |  | 2024-12-17 | 11,7 |  | 1.1.7 |  | 41 | 0 | 0 | 0 |
| 745 | com.vladsch.flexmark:flexmark-jira-converter 🚩 |  | 2023-05-23 | 9,4 |  | 0.64.8 |  | 227 | 0 | 0 | 0 |
| 746 | org.jline:jline | org.jline ⚙️ | 2026-05-28 | 9,7 | 6,0 | 4.1.3 | 4.1.3 | 91 | 55 | 28 | 28 |
| 747 | org.apache.lucene:lucene-queryparser | org.apache.lucene.queryparser ✳️ | 2026-02-25 | 16,7 | 4,5 | 10.4.0 | 10.4.0 | 146 | 30 | 7 | 7 |
| 748 | com.fasterxml.jackson.dataformat:jackson-dataformat-smile | com.fasterxml.jackson.dataformat.smile ✳️ | 2026-06-01 | 14,3 | 8,6 | 2.22.0 | 2.22.0 | 184 | 92 | 18 | 18 |
| 749 | com.sun.xml.bind:jaxb-core | com.sun.xml.bind.core ✳️ | 2026-05-28 | 13,3 | 6,1 | 4.0.9 | 4.0.9 | 40 | 24 | 4 | 4 |
| 750 | com.github.java-json-tools:json-schema-core 🚩 |  | 2020-05-27 | 9,0 |  | 1.2.14 |  | 7 | 0 | 0 | 0 |
| 751 | com.github.java-json-tools:json-schema-validator 🚩 |  | 2020-05-27 | 9,0 |  | 2.2.14 |  | 8 | 0 | 0 | 0 |
| 752 | org.springframework.cloud:spring-cloud-starter |  | 2026-04-01 | 11,2 |  | 4.3.2 |  | 96 | 0 | 13 | 0 |
| 753 | org.apache.lucene:lucene-queries | org.apache.lucene.queries ✳️ | 2026-02-25 | 19,0 | 4,5 | 10.4.0 | 10.4.0 | 155 | 30 | 7 | 7 |
| 754 | org.junit.platform:junit-platform-suite-api | org.junit.platform.suite.api ✳️ | 2026-05-19 | 9,2 | 8,9 | 6.1.0 | 6.1.0 | 107 | 106 | 22 | 22 |
| 755 | com.vladsch.flexmark:flexmark-ext-autolink 🚩 |  | 2023-05-23 | 9,4 |  | 0.64.8 |  | 228 | 0 | 0 | 0 |
| 756 | com.diffplug.spotless:spotless-plugin-gradle |  | 2026-05-27 | 9,4 |  | 8.6.0 |  | 165 | 0 | 12 | 0 |
| 757 | org.codehaus.groovy:groovy-json ⚠️ | org.codehaus.groovy.json ⚙️ | 2025-05-27 | 14,0 | 8,3 | 3.0.25 | 3.0.25 | 151 | 65 | 0 | 0 |
| 758 | org.eclipse.jetty.websocket:websocket-client | org.eclipse.jetty.websocket.client ⚙️ | 2025-08-14 | 13,7 | 7,6 | 9.4.58.v20250814 | 9.4.58.v20250814 | 193 | 46 | 1 | 1 |
| 759 | org.jetbrains.kotlinx:kotlinx-coroutines-test | kotlinx.coroutines.test.artifact_disambiguating_module ⚙️ | 2026-05-07 | 7,5 | 0,1 | 1.11.0 | 1.11.0 | 66 | 3 | 3 | 3 |
| 760 | org.eclipse.jetty.websocket:websocket-api | org.eclipse.jetty.websocket.api ⚙️ | 2025-08-14 | 13,5 | 7,6 | 9.4.58.v20250814 | 9.4.58.v20250814 | 190 | 46 | 1 | 1 |
| ~~761~~ | ~~org.apache.maven.doxia:doxia-module-markdown~~ | ~~-~~ | ~~2026-03-17~~ | ~~14,1~~ | ~~-~~ | ~~2.1.0~~ | ~~-~~ | ~~25~~ | ~~0~~ | ~~1~~ | ~~0~~ |
| 762 | org.antlr:antlr4 ⚠️ |  | 2024-08-03 | 13,5 |  | 4.13.2 |  | 31 | 0 | 0 | 0 |
| 763 | com.auth0:java-jwt | com.auth0.jwt 🏷️ | 2026-04-29 | 12,2 | 4,9 | 4.5.2 | 4.5.2 | 60 | 21 | 2 | 2 |
| 764 | org.eclipse.jetty.websocket:websocket-common | org.eclipse.jetty.websocket.common ⚙️ | 2025-08-14 | 13,5 | 7,6 | 9.4.58.v20250814 | 9.4.58.v20250814 | 190 | 46 | 1 | 1 |
| 765 | com.vladsch.flexmark:flexmark-ext-yaml-front-matter 🚩 |  | 2023-05-23 | 9,4 |  | 0.64.8 |  | 228 | 0 | 0 | 0 |
| 766 | com.squareup.okhttp3:okhttp-urlconnection | okhttp3.urlconnection 🏷️ | 2025-11-18 | 10,4 | 8,3 | 5.2.3 | 5.2.3 | 106 | 86 | 10 | 10 |
| 767 | com.vladsch.flexmark:flexmark-ext-gfm-tasklist 🚩 |  | 2023-05-23 | 9,4 |  | 0.64.8 |  | 228 | 0 | 0 | 0 |
| 768 | com.vladsch.flexmark:flexmark-ext-anchorlink 🚩 |  | 2023-05-23 | 9,4 |  | 0.64.8 |  | 228 | 0 | 0 | 0 |
| 769 | io.reactivex.rxjava2:rxjava 🚩 | io.reactivex.rxjava2 ⚙️ | 2021-02-13 | 9,8 | 8,7 | 2.2.21 | 2.2.21 | 57 | 36 | 0 | 0 |
| 770 | net.jcip:jcip-annotations 🚩 |  | 2008-08-14 | 17,8 |  | 1.0 |  | 1 | 0 | 0 | 0 |
| 771 | com.github.jnr:jnr-ffi | org.jnrproject.ffi ⚙️ | 2026-03-31 | 14,8 | 5,7 | 2.2.19 | 2.2.19 | 70 | 20 | 2 | 2 |
| 772 | dk.brics.automaton:automaton 🚩 |  | 2011-12-04 | 14,5 |  | 1.11-8 |  | 1 | 0 | 0 | 0 |
| 773 | com.github.jnr:jnr-constants 🚩 | org.jnrproject.constants ⚙️ | 2022-11-15 | 14,8 | 5,7 | 0.10.4 | 0.10.4 | 30 | 5 | 0 | 0 |
| 774 | io.grpc:grpc-xds | io.grpc.xds ⚙️ | 2026-04-30 | 6,2 | 2,7 | 1.81.0 | 1.81.0 | 122 | 42 | 12 | 12 |
| 775 | io.mockk:mockk-jvm |  | 2026-05-29 | 3,8 |  | 1.14.11 |  | 27 | 0 | 7 | 0 |
| 776 | com.amazonaws:aws-java-sdk-dynamodb |  | 2025-12-29 | 11,6 |  | 1.12.797 |  | 1956 | 0 | 14 | 0 |
| 777 | com.github.java-json-tools:btf 🚩 |  | 2020-01-04 | 6,4 |  | 1.3 |  | 1 | 0 | 0 | 0 |
| 778 | com.github.java-json-tools:msg-simple 🚩 |  | 2020-01-06 | 6,4 |  | 1.2 |  | 1 | 0 | 0 | 0 |
| 779 | io.swagger.core.v3:swagger-annotations-jakarta | io.swagger.v3.oas.annotations ⚙️ | 2026-05-12 | 5,3 | 5,3 | 2.2.50 | 2.2.50 | 58 | 58 | 18 | 18 |
| 780 | org.apache.lucene:lucene-sandbox | org.apache.lucene.sandbox ✳️ | 2026-02-25 | 13,9 | 4,5 | 10.4.0 | 10.4.0 | 129 | 30 | 7 | 7 |
| 781 | io.dropwizard.metrics:metrics-json | com.codahale.metrics.json ⚙️ | 2026-05-25 | 11,7 | 8,2 | 4.2.39 | 4.2.39 | 114 | 87 | 7 | 7 |
| 782 | org.jetbrains:markdown-jvm ⚠️ |  | 2024-05-24 | 5,3 |  | 0.7.3 |  | 23 | 0 | 0 | 0 |
| 783 | org.mortbay.jetty:jetty 🚩 |  | 2010-11-10 | 20,6 |  | 6.1.26 |  | 107 | 0 | 0 | 0 |
| 784 | io.mockk:mockk-agent-api-jvm |  | 2026-05-29 | 3,8 |  | 1.14.11 |  | 29 | 0 | 7 | 0 |
| 785 | com.github.jnr:jnr-posix | org.jnrproject.posix ⚙️ | 2026-03-31 | 14,8 | 5,7 | 3.1.22 | 3.1.22 | 95 | 23 | 2 | 2 |
| 786 | org.fusesource.leveldbjni:leveldbjni-all 🚩 |  | 2013-10-17 | 14,5 |  | 1.8 |  | 10 | 0 | 0 | 0 |
| 787 | com.diffplug.durian:durian-swt.os |  | 2025-08-28 | 7,8 |  | 5.2.0 |  | 24 | 0 | 2 | 0 |
| 788 | io.mockk:mockk-core-jvm |  | 2026-05-29 | 3,7 |  | 1.14.11 |  | 27 | 0 | 7 | 0 |
| 789 | xpp3:xpp3_min 🚩 |  | 2007-11-28 | 20,3 |  | 1.1.4c |  | 3 | 0 | 0 | 0 |
| 790 | com.esotericsoftware:minlog 🚩 | com.esotericsoftware.minlog ⚙️ | 2018-12-28 | 12,5 | 7,4 | 1.3.1 | 1.3.1 | 4 | 1 | 0 | 0 |
| 791 | org.nibor.autolink:autolink | org.nibor.autolink ✳️ | 2025-06-04 | 11,0 | 8,4 | 0.12.0 | 0.12.0 | 14 | 7 | 1 | 1 |
| 792 | org.apache.spark:spark-avro_2.12 |  | 2026-01-12 | 7,6 |  | 3.5.8 |  | 43 | 0 | 2 | 0 |
| 793 | org.rocksdb:rocksdbjni |  | 2026-04-09 | 11,6 |  | 10.10.1.1 |  | 157 | 0 | 12 | 0 |
| 794 | javax.servlet:servlet-api 🚩 |  | 2008-04-17 | 20,8 |  | 3.0-alpha-1 |  | 7 | 0 | 0 | 0 |
| 795 | io.micrometer:micrometer-jakarta9 | micrometer.jakarta9 ⚙️ | 2026-04-13 | 2,6 | 2,6 | 1.17.0-RC1 | 1.17.0-RC1 | 74 | 74 | 33 | 33 |
| 796 | org.glassfish.jersey.core:jersey-server |  | 2026-03-18 | 14,3 |  | 2.48 |  | 154 | 6 | 11 | 6 |
| 797 | io.github.resilience4j:resilience4j-core | io.github.resilience4j.core ⚙️ | 2026-03-14 | 9,0 | 7,0 | 2.4.0 | 2.4.0 | 32 | 21 | 1 | 1 |
| 798 | org.apache.commons:commons-configuration2 🚩 | org.apache.commons.configuration2 ⚙️ | 2022-06-30 | 10,2 | 8,6 | 2.8.0 | 2.8.0 | 10 | 7 | 0 | 0 |
| 799 | dev.failsafe:failsafe ⚠️ | dev.failsafe.core ✳️ | 2023-06-24 | 4,5 | 4,5 | 3.3.2 | 3.3.2 | 12 | 12 | 0 | 0 |
| 800 | io.swagger.core.v3:swagger-models-jakarta | io.swagger.v3.oas.models ⚙️ | 2026-05-12 | 5,3 | 5,3 | 2.2.50 | 2.2.50 | 58 | 58 | 18 | 18 |
| 801 | io.swagger.core.v3:swagger-core-jakarta | io.swagger.v3.core ⚙️ | 2026-05-12 | 5,3 | 5,3 | 2.2.50 | 2.2.50 | 58 | 58 | 18 | 18 |
| 802 | io.jsonwebtoken:jjwt-api |  | 2025-08-20 | 7,8 |  | 0.13.0 |  | 24 | 0 | 2 | 0 |
| ~~803~~ | ~~org.apache.maven.plugins:maven-source-plugin~~ | ~~-~~ | ~~2025-11-22~~ | ~~20,1~~ | ~~-~~ | ~~3.4.0~~ | ~~-~~ | ~~23~~ | ~~0~~ | ~~1~~ | ~~0~~ |
| 804 | io.github.java-diff-utils:java-diff-utils | io.github.javadiffutils ⚙️ | 2026-05-16 | 7,9 | 7,9 | 4.17 | 4.17 | 13 | 12 | 2 | 2 |
| 805 | com.google.inject.extensions:guice-servlet 🚩 | com.google.guice.extensions.servlet ⚙️ | 2023-05-12 | 16,8 | 8,3 | 7.0.0 | 7.0.0 | 22 | 13 | 0 | 0 |
| 806 | org.springframework.kafka:spring-kafka | spring.kafka ⚙️ | 2026-04-20 | 9,9 | 8,7 | 4.1.0-RC1 | 4.1.0-RC1 | 245 | 213 | 23 | 23 |
| 807 | com.github.mifmif:generex 🚩 |  | 2016-10-30 | 11,6 |  | 1.0.2 |  | 8 | 0 | 0 | 0 |
| 808 | com.google.cloud:google-cloud-core-http |  | 2026-05-06 | 9,1 |  | 2.70.0 |  | 293 | 0 | 27 | 0 |
| 809 | io.github.microutils:kotlin-logging-jvm 🚩 | io.github.microutils.kotlinlogging ⚙️ | 2023-02-01 | 5,7 | 3,6 | 3.0.5 | 3.0.5 | 26 | 5 | 0 | 0 |
| 810 | com.jcraft:jzlib 🚩 |  | 2013-10-04 | 20,1 |  | 1.1.3 |  | 5 | 0 | 0 | 0 |
| 811 | javax.servlet.jsp:jsp-api 🚩 |  | 2011-01-10 | 19,9 |  | 2.2.1-b03 |  | 15 | 0 | 0 | 0 |
| 812 | com.vladsch.flexmark:flexmark-ext-abbreviation 🚩 |  | 2023-05-23 | 9,4 |  | 0.64.8 |  | 228 | 0 | 0 | 0 |
| 813 | com.vladsch.flexmark:flexmark-ext-definition 🚩 |  | 2023-05-23 | 9,4 |  | 0.64.8 |  | 228 | 0 | 0 | 0 |
| 814 | com.vladsch.flexmark:flexmark-ext-escaped-character 🚩 |  | 2023-05-23 | 9,4 |  | 0.64.8 |  | 228 | 0 | 0 | 0 |
| 815 | io.lettuce:lettuce-core | lettuce.core ⚙️ | 2026-05-28 | 9,1 | 6,7 | 7.6.0.RELEASE | 7.6.0.RELEASE | 115 | 95 | 30 | 30 |
| 816 | com.vladsch.flexmark:flexmark-ext-typographic 🚩 |  | 2023-05-23 | 9,4 |  | 0.64.8 |  | 228 | 0 | 0 | 0 |
| 817 | org.eclipse.jetty:jetty-servlets | org.eclipse.jetty.servlets ✳️ | 2025-08-14 | 17,2 | 7,6 | 11.0.26 | 11.0.26 | 371 | 111 | 3 | 3 |
| 818 | org.springframework.security:spring-security-oauth2-resource-server | spring.security.oauth2.resource.server ⚙️ | 2026-04-20 | 7,7 | 7,7 | 7.0.5 | 7.0.5 | 190 | 190 | 34 | 34 |
| 819 | org.seleniumhq.selenium:selenium-json | org.seleniumhq.selenium.json 🏷️ | 2026-05-12 | 6,7 | 6,7 | 4.44.0 | 4.44.0 | 79 | 79 | 11 | 11 |
| 820 | sslext:sslext 🚩 |  | 2005-08-01 | 21,7 |  | 1.10-4 |  | 2 | 0 | 0 | 0 |
| 821 | org.apache.struts:struts-core 🚩 |  | 2008-12-07 | 19,8 |  | 1.3.10 |  | 4 | 0 | 0 | 0 |
| 822 | com.vladsch.flexmark:flexmark-ext-footnotes 🚩 |  | 2023-05-23 | 9,4 |  | 0.64.8 |  | 228 | 0 | 0 | 0 |
| 823 | org.mockito:mockito-inline 🚩 |  | 2023-03-09 | 9,3 |  | 5.2.0 |  | 105 | 0 | 0 | 0 |
| 824 | org.apache.struts:struts-taglib 🚩 |  | 2008-12-07 | 19,8 |  | 1.3.10 |  | 4 | 0 | 0 | 0 |
| 825 | com.vladsch.flexmark:flexmark-ext-toc 🚩 |  | 2023-05-23 | 9,4 |  | 0.64.8 |  | 228 | 0 | 0 | 0 |
| 826 | redis.clients:jedis | redis.clients.jedis ⚙️ | 2026-05-28 | 15,7 | 5,2 | 8.0.0-beta1 | 8.0.0-beta1 | 140 | 81 | 11 | 11 |
| 827 | org.apache.lucene:lucene-analyzers-common ⚠️ |  | 2024-09-24 | 13,9 |  | 8.11.4 |  | 99 | 0 | 0 | 0 |
| 828 | org.apache.struts:struts-tiles 🚩 |  | 2008-12-07 | 19,8 |  | 1.3.10 |  | 4 | 0 | 0 | 0 |
| 829 | com.vladsch.flexmark:flexmark-ext-jekyll-front-matter 🚩 |  | 2023-05-23 | 9,4 |  | 0.64.8 |  | 228 | 0 | 0 | 0 |
| 830 | com.vladsch.flexmark:flexmark-ext-aside 🚩 |  | 2023-05-23 | 9,4 |  | 0.64.8 |  | 226 | 0 | 0 | 0 |
| 831 | org.apache.logging.log4j:log4j-1.2-api | org.apache.log4j ✳️ | 2026-05-02 | 13,1 | 8,5 | 2.26.0 | 2.26.0 | 69 | 33 | 6 | 6 |
| 832 | com.vladsch.flexmark:flexmark-profile-pegdown 🚩 |  | 2023-05-23 | 9,4 |  | 0.64.8 |  | 204 | 0 | 0 | 0 |
| 833 | com.google.http-client:google-http-client-appengine | com.google.api.client.extensions.appengine ⚙️ | 2026-01-23 | 14,2 | 6,7 | 2.1.0 | 2.1.0 | 86 | 52 | 7 | 7 |
| 834 | org.brotli:dec 🚩 |  | 2017-05-07 | 9,3 |  | 0.1.2 |  | 3 | 0 | 0 | 0 |
| 835 | org.scala-lang.modules:scala-collection-compat_2.13 | scala.collection.compat ⚙️ | 2025-10-07 | 7,0 | 5,1 | 2.14.0 | 2.14.0 | 30 | 12 | 1 | 1 |
| 836 | com.vladsch.flexmark:flexmark-formatter 🚩 |  | 2020-01-23 | 9,3 |  | 0.50.50 |  | 172 | 0 | 0 | 0 |
| 837 | com.sun.jersey.contribs:jersey-guice 🚩 |  | 2017-05-24 | 16,0 |  | 1.19.4 |  | 93 | 0 | 0 | 0 |
| 838 | com.vladsch.flexmark:flexmark-youtrack-converter 🚩 |  | 2023-05-23 | 9,4 |  | 0.64.8 |  | 224 | 0 | 0 | 0 |
| 839 | com.vladsch.flexmark:flexmark-ext-xwiki-macros 🚩 |  | 2023-05-23 | 9,4 |  | 0.64.8 |  | 210 | 0 | 0 | 0 |
| 840 | io.jsonwebtoken:jjwt-impl |  | 2025-08-20 | 7,8 |  | 0.13.0 |  | 24 | 0 | 2 | 0 |
| 841 | com.vladsch.flexmark:flexmark-ext-jekyll-tag 🚩 |  | 2023-05-23 | 9,4 |  | 0.64.8 |  | 208 | 0 | 0 | 0 |
| 842 | com.vladsch.flexmark:flexmark-all 🚩 |  | 2023-05-23 | 9,3 |  | 0.64.8 |  | 188 | 0 | 0 | 0 |
| 843 | org.seleniumhq.selenium:selenium-http | org.seleniumhq.selenium.http 🏷️ | 2026-05-12 | 6,7 | 6,7 | 4.44.0 | 4.44.0 | 79 | 79 | 11 | 11 |
| 844 | biz.aQute.bnd:biz.aQute.bndlib | biz.aQute.bndlib ⚙️ | 2026-03-25 | 11,4 | 8,1 | 7.2.3 | 7.2.3 | 32 | 25 | 4 | 4 |
| 845 | org.springframework.boot:spring-boot-starter-data-redis | spring.boot.starter.data.redis ⚙️ | 2026-04-23 | 9,8 | 8,3 | 3.5.14 | 3.5.14 | 252 | 221 | 39 | 39 |
| 846 | org.apache.poi:poi-ooxml-lite | org.apache.poi.ooxml.schemas 🏷️ | 2025-11-26 | 5,4 | 5,4 | 5.5.1 | 5.5.1 | 13 | 13 | 2 | 2 |
| 847 | org.apache.logging.log4j:log4j-layout-template-json | org.apache.logging.log4j.layout.template.json ✳️ | 2026-05-02 | 5,6 | 3,0 | 2.26.0 | 2.26.0 | 30 | 20 | 6 | 6 |
| 848 | io.airlift:aircompressor |  | 2026-02-24 | 10,2 |  | 2.0.3 |  | 29 | 0 | 1 | 0 |
| 849 | com.datadoghq:dd-trace-api |  | 2026-05-04 | 8,4 |  | 1.62.0 |  | 269 | 0 | 27 | 0 |
| 850 | com.sun.mail:javax.mail 🚩 | java.mail ⚙️ | 2018-08-29 | 15,5 | 8,3 | 1.6.2 | 1.6.2 | 21 | 2 | 0 | 0 |
| ~~851~~ | ~~org.apache.maven.shared:maven-mapping 🚩~~ | ~~-~~ | ~~2015-11-15~~ | ~~12,8~~ | ~~-~~ | ~~3.0.0~~ | ~~-~~ | ~~2~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| 852 | com.fasterxml.jackson.module:jackson-module-afterburner | com.fasterxml.jackson.module.afterburner ✳️ | 2026-06-01 | 14,4 | 8,6 | 2.22.0 | 2.22.0 | 177 | 92 | 18 | 18 |
| 853 | javax.xml.stream:stax-api 🚩 |  | 2008-10-04 | 18,7 |  | 1.0 |  | 2 | 0 | 0 | 0 |
| ~~854~~ | ~~org.codehaus.plexus:plexus-digest ⚠️~~ | ~~-~~ | ~~2023-12-17~~ | ~~19,5~~ | ~~-~~ | ~~1.3~~ | ~~-~~ | ~~3~~ | ~~0~~ | ~~0~~ | ~~0~~ |
| 855 | org.springdoc:springdoc-openapi-starter-common | org.springdoc.openapi.common ⚙️ | 2026-04-11 | 4,3 | 4,3 | 2.8.17 | 2.8.17 | 46 | 46 | 15 | 15 |
| 856 | dev.equo.ide:solstice ⚠️ |  | 2025-01-14 | 3,5 |  | 1.8.1 |  | 48 | 0 | 0 | 0 |
| 857 | commons-dbcp:commons-dbcp 🚩 |  | 2010-02-07 | 20,6 |  | 1.4 |  | 13 | 0 | 0 | 0 |
| 858 | org.apache.derby:derby ⚠️ | org.apache.derby.engine 🏷️ | 2023-11-10 | 20,5 | 7,2 | 10.17.1.0 | 10.17.1.0 | 29 | 4 | 0 | 0 |
| 859 | io.opentelemetry.semconv:opentelemetry-semconv | io.opentelemetry.semconv ⚙️ | 2026-05-12 | 2,8 | 2,8 | 1.41.1 | 1.41.1 | 21 | 21 | 8 | 8 |
| 860 | io.grpc:grpc-googleapis | io.grpc.googleapis ⚙️ | 2026-04-30 | 4,2 | 2,7 | 1.81.0 | 1.81.0 | 77 | 42 | 12 | 12 |
| 861 | com.github.jnr:jnr-x86asm 🚩 |  | 2012-04-07 | 14,8 |  | 1.0.2 |  | 2 | 0 | 0 | 0 |
| 862 | org.bouncycastle:bcutil-jdk15on 🚩 | org.bouncycastle.util 🏷️ | 2021-12-01 | 5,0 | 5,0 | 1.70 | 1.70 | 2 | 2 | 0 | 0 |
| 863 | org.codehaus.mojo:exec-maven-plugin |  | 2025-12-21 | 20,2 |  | 3.6.3 |  | 27 | 0 | 4 | 0 |
| 864 | org.unbescape:unbescape 🚩 | unbescape ⚙️ | 2018-03-30 | 12,1 | 8,2 | 1.1.6.RELEASE | 1.1.6.RELEASE | 13 | 1 | 0 | 0 |
| 865 | com.squareup.retrofit2:converter-gson ⚠️ | retrofit2.converter.gson ⚙️ | 2025-05-15 | 10,4 | 8,2 | 3.0.0 | 3.0.0 | 26 | 18 | 0 | 0 |
| 866 | org.seleniumhq.selenium:selenium-chromium-driver | org.seleniumhq.selenium.chromium_driver 🏷️ | 2026-05-12 | 6,9 | 6,9 | 4.44.0 | 4.44.0 | 80 | 80 | 11 | 11 |
| 867 | com.vladsch.flexmark:flexmark-ext-attributes 🚩 |  | 2023-05-23 | 8,4 |  | 0.64.8 |  | 128 | 0 | 0 | 0 |
| 868 | com.azure:azure-core | com.azure.core ✳️ | 2026-04-29 | 6,9 | 6,7 | 1.58.0 | 1.58.0 | 89 | 83 | 7 | 7 |
| 869 | org.seleniumhq.selenium:selenium-devtools-v85 ⚠️ | org.seleniumhq.selenium.devtools_v85 🏷️ | 2025-02-20 | 5,6 | 5,6 | 4.29.0 | 4.29.0 | 59 | 59 | 0 | 0 |
| 870 | com.vladsch.flexmark:flexmark-ext-gitlab 🚩 |  | 2023-05-23 | 7,7 |  | 0.64.8 |  | 98 | 0 | 0 | 0 |
| 871 | net.snowflake:snowflake-jdbc |  | 2026-05-07 | 9,7 |  | 4.2.0 |  | 192 | 58 | 17 | 0 |
| 872 | io.swagger:swagger-parser |  | 2026-04-14 | 11,3 |  | 1.0.76 |  | 79 | 0 | 1 | 0 |
| 873 | com.vladsch.flexmark:flexmark-ext-gfm-users 🚩 |  | 2023-05-23 | 8,9 |  | 0.64.8 |  | 152 | 0 | 0 | 0 |
| 874 | com.google.zxing:core | com.google.zxing ⚙️ | 2025-11-11 | 14,4 | 7,0 | 3.5.4 | 3.5.4 | 21 | 7 | 1 | 1 |
| 875 | com.vladsch.flexmark:flexmark-ext-gfm-issues 🚩 |  | 2023-05-23 | 8,9 |  | 0.64.8 |  | 152 | 0 | 0 | 0 |
| 876 | org.glassfish.jersey.containers:jersey-container-servlet-core |  | 2026-03-18 | 14,1 |  | 2.48 |  | 145 | 0 | 4 | 0 |
| 877 | org.jacoco:org.jacoco.ant | org.jacoco.ant ⚙️ | 2025-10-11 | 14,9 | 8,4 | 0.8.14 | 0.8.14 | 39 | 15 | 1 | 1 |
| 878 | com.vladsch.flexmark:flexmark-ext-youtube-embedded 🚩 |  | 2023-05-23 | 8,5 |  | 0.64.8 |  | 136 | 0 | 0 | 0 |
| 879 | com.vladsch.flexmark:flexmark-ext-enumerated-reference 🚩 |  | 2023-05-23 | 8,4 |  | 0.64.8 |  | 128 | 0 | 0 | 0 |
| 880 | javax.transaction:jta 🚩 |  | 2007-01-05 | 19,4 |  | 1.1 |  | 1 | 0 | 0 | 0 |
| 881 | com.vladsch.flexmark:flexmark-ext-admonition 🚩 |  | 2023-05-23 | 8,2 |  | 0.64.8 |  | 118 | 0 | 0 | 0 |
| 882 | com.vladsch.flexmark:flexmark-ext-media-tags 🚩 |  | 2023-05-23 | 7,9 |  | 0.64.8 |  | 107 | 0 | 0 | 0 |
| 883 | com.github.java-json-tools:jackson-coreutils-equivalence 🚩 |  | 2020-05-27 | 6,0 |  | 1.0 |  | 1 | 0 | 0 | 0 |
| 884 | io.ktor:ktor-utils-jvm | io.ktor.utils ⚙️ | 2026-05-14 | 7,5 | 4,2 | 3.5.0 | 3.5.0 | 96 | 54 | 14 | 14 |
| 885 | com.google.devtools.ksp:symbol-processing-gradle-plugin |  | 2026-05-26 | 4,8 |  | 2.3.9 |  | 157 | 0 | 29 | 0 |
| 886 | com.github.java-json-tools:uri-template 🚩 |  | 2020-01-06 | 6,4 |  | 0.10 |  | 1 | 0 | 0 | 0 |
| 887 | org.apache.orc:orc-core |  | 2026-02-25 | 10,0 |  | 2.3.0 |  | 96 | 0 | 11 | 0 |
| 888 | org.jetbrains.kotlin:kotlin-tooling-metadata ⚠️ |  | 2023-08-23 | 5,0 |  | 1.9.10 |  | 46 | 0 | 0 | 0 |
| 889 | org.apache.htrace:htrace-core4 🚩 |  | 2016-09-15 | 10,7 |  | 4.2.0-incubating |  | 3 | 0 | 0 | 0 |
| 890 | org.apache.hive:hive-common |  | 2025-11-17 | 14,5 |  | 4.2.0 |  | 44 | 0 | 2 | 0 |
| 891 | org.apache.hive:hive-exec |  | 2025-11-17 | 14,5 |  | 4.2.0 |  | 44 | 0 | 2 | 0 |
| 892 | io.swagger.parser.v3:swagger-parser-core |  | 2026-05-26 | 8,5 |  | 2.1.43 |  | 80 | 0 | 14 | 0 |
| 893 | io.ktor:ktor-http-jvm | io.ktor.http ⚙️ | 2026-05-14 | 7,5 | 4,2 | 3.5.0 | 3.5.0 | 96 | 54 | 14 | 14 |
| 894 | io.swagger.parser.v3:swagger-parser-v3 |  | 2026-05-26 | 8,5 |  | 2.1.43 |  | 80 | 0 | 14 | 0 |
| 895 | io.ktor:ktor-io-jvm | io.ktor.io ⚙️ | 2026-05-14 | 6,5 | 4,2 | 3.5.0 | 3.5.0 | 78 | 54 | 14 | 14 |
| 896 | com.github.javaparser:javaparser-core | com.github.javaparser.core ⚙️ | 2026-05-31 | 11,4 | 8,4 | 3.28.2 | 3.28.2 | 202 | 140 | 5 | 5 |
| 897 | org.apache.hive:hive-metastore |  | 2025-11-17 | 14,5 |  | 4.2.0 |  | 44 | 0 | 2 | 0 |
| 898 | io.swagger:swagger-compat-spec-parser |  | 2026-04-14 | 11,3 |  | 1.0.76 |  | 77 | 0 | 1 | 0 |
| 899 | io.prometheus.jmx:jmx_prometheus_javaagent ⚠️ | org.yaml.snakeyaml ✳️ | 2024-05-31 | 10,7 | 3,2 | 1.0.1 | 2.2 | 27 | 5 | 0 | 0 |
| 900 | io.jsonwebtoken:jjwt-jackson |  | 2025-08-20 | 7,8 |  | 0.13.0 |  | 24 | 0 | 2 | 0 |
| 901 | org.apache.hive:hive-shims |  | 2025-11-17 | 14,5 |  | 4.2.0 |  | 44 | 0 | 2 | 0 |
| 902 | org.apache.hadoop:hadoop-yarn-server-common |  | 2026-03-24 | 14,3 |  | 3.5.0 |  | 83 | 0 | 3 | 0 |
| 903 | org.apache.hive:hive-serde |  | 2025-11-17 | 14,5 |  | 4.2.0 |  | 44 | 0 | 2 | 0 |
| 904 | io.github.resilience4j:resilience4j-retry | io.github.resilience4j.retry ⚙️ | 2026-03-14 | 9,0 | 7,0 | 2.4.0 | 2.4.0 | 32 | 21 | 1 | 1 |
| 905 | com.carrotsearch:hppc ⚠️ | com.carrotsearch.hppc ✳️ | 2024-06-04 | 15,4 | 5,5 | 0.10.0 | 0.10.0 | 26 | 4 | 0 | 0 |
| 906 | org.apache.arrow:arrow-vector | org.apache.arrow.vector ✳️ | 2026-03-12 | 9,6 | 2,1 | 19.0.0 | 19.0.0 | 53 | 8 | 1 | 1 |
| 907 | org.jetbrains.kotlinx:kotlinx-coroutines-slf4j | kotlinx.coroutines.slf4j 🏷️ | 2026-05-07 | 7,8 | 3,2 | 1.11.0 | 1.11.0 | 84 | 20 | 3 | 3 |
| 908 | com.typesafe:ssl-config-core_2.12 | ssl.config.core ⚙️ | 2025-10-24 | 9,6 | 7,7 | 0.7.1 | 0.7.1 | 23 | 19 | 5 | 5 |
| 909 | org.apache.hive.shims:hive-shims-common |  | 2025-11-17 | 12,1 |  | 4.2.0 |  | 38 | 0 | 2 | 0 |
| 910 | org.aspectj:aspectjrt | org.aspectj.runtime ⚙️ | 2025-12-17 | 15,5 | 8,2 | 1.9.25.1 | 1.9.25.1 | 65 | 32 | 2 | 2 |
| 911 | org.apache.arrow:arrow-format | org.apache.arrow.format ✳️ | 2026-03-12 | 9,6 | 2,1 | 19.0.0 | 19.0.0 | 53 | 8 | 1 | 1 |
| 912 | com.github.bumptech.glide:annotations |  | 2026-04-19 | 9,0 |  | 5.0.7 |  | 34 | 0 | 6 | 0 |
| 913 | org.apache.iceberg:iceberg-spark-runtime-3.3_2.12 ⚠️ | dev.failsafe.core ✳️ | 2025-03-13 | 3,9 | 3,9 | 1.7.2 | 3.3.2 | 22 | 22 | 0 | 0 |
| 914 | io.grpc:grpc-inprocess | io.grpc.inprocess ⚙️ | 2026-04-30 | 2,7 | 2,7 | 1.81.0 | 1.81.0 | 42 | 42 | 12 | 12 |
| 915 | org.apache.hive.shims:hive-shims-0.23 |  | 2025-11-17 | 12,1 |  | 4.2.0 |  | 38 | 0 | 2 | 0 |
| 916 | io.swagger.parser.v3:swagger-parser |  | 2026-05-26 | 8,5 |  | 2.1.43 |  | 80 | 0 | 14 | 0 |
| 917 | dnsjava:dnsjava | org.dnsjava ✳️ | 2026-05-23 | 20,8 | 7,0 | 3.6.5 | 3.6.5 | 36 | 25 | 2 | 2 |
| 918 | com.puppycrawl.tools:checkstyle | com.puppycrawl.tools.checkstyle ⚙️ | 2026-05-30 | 15,7 | 2,3 | 13.5.0 | 13.5.0 | 184 | 46 | 22 | 22 |
| 919 | org.testcontainers:database-commons |  | 2025-12-16 | 8,2 |  | 1.21.4 |  | 76 | 0 | 3 | 0 |
| 920 | com.pinterest.ktlint:ktlint-ruleset-standard |  | 2025-12-15 | 7,1 |  | 1.7.2 |  | 51 | 0 | 4 | 0 |
| 921 | org.jline:jline-terminal | org.jline.terminal ✳️ | 2026-05-28 | 9,2 | 6,0 | 4.1.3 | 4.1.3 | 81 | 55 | 28 | 28 |
| 922 | org.apache-extras.beanshell:bsh 🚩 |  | 2016-02-18 | 11,4 |  | 2.0b6 |  | 2 | 0 | 0 | 0 |
| 923 | org.jetbrains.kotlin:kotlin-android-extensions-runtime ⚠️ |  | 2023-08-23 | 8,8 |  | 1.9.10 |  | 103 | 0 | 0 | 0 |
| 924 | io.swagger.parser.v3:swagger-parser-v2-converter |  | 2026-05-26 | 8,5 |  | 2.1.43 |  | 80 | 0 | 14 | 0 |
| 925 | org.testcontainers:jdbc |  | 2025-12-16 | 10,9 |  | 1.21.4 |  | 107 | 0 | 3 | 0 |
| 926 | com.vladsch.flexmark:flexmark-html-parser 🚩 |  | 2020-01-23 | 9,4 |  | 0.50.50 |  | 176 | 0 | 0 | 0 |
| 927 | com.vladsch.flexmark:flexmark-ext-gfm-tables 🚩 |  | 2020-01-23 | 9,4 |  | 0.50.50 |  | 201 | 0 | 0 | 0 |
| 928 | com.vladsch.flexmark:flexmark-ext-macros 🚩 |  | 2023-05-23 | 7,7 |  | 0.64.8 |  | 88 | 0 | 0 | 0 |
| 929 | org.glassfish.hk2.external:jakarta.inject 🚩 |  | 2019-08-23 | 7,5 |  | 2.6.1 |  | 4 | 0 | 0 | 0 |
| 930 | com.airbnb.android:lottie |  |  |  |  |  |  | 0 | 0 | 0 | 0 |
| 931 | software.amazon.awssdk:dynamodb | software.amazon.awssdk.services.dynamodb ⚙️ | 2026-05-27 | 8,9 | 7,5 | 2.44.14 | 2.44.14 | 1776 | 1757 | 234 | 234 |
| 932 | org.slf4j:slf4j-ext |  | 2026-05-12 | 17,7 |  | 2.0.18 |  | 93 | 1 | 1 | 0 |
| 933 | com.amazonaws:aws-java-sdk-sns |  | 2025-12-29 | 11,6 |  | 1.12.797 |  | 1956 | 0 | 14 | 0 |
| 934 | org.eclipse.jetty:jetty-continuation | org.eclipse.jetty.continuation ⚙️ | 2025-08-14 | 17,2 | 7,6 | 9.4.58.v20250814 | 9.4.58.v20250814 | 306 | 46 | 1 | 1 |
| 935 | org.springdoc:springdoc-openapi-starter-webmvc-api | org.springdoc.openapi.webmvc.core ⚙️ | 2026-04-11 | 4,3 | 4,3 | 2.8.17 | 2.8.17 | 46 | 46 | 15 | 15 |
| 936 | javax.cache:cache-api 🚩 |  | 2019-05-10 | 14,8 |  | 1.1.1 |  | 15 | 0 | 0 | 0 |
| 937 | org.webjars:jquery |  | 2026-02-05 | 13,6 |  | 4.0.0 |  | 61 | 0 | 1 | 0 |
| 938 | org.apache.hive.shims:hive-shims-scheduler ⚠️ |  | 2024-05-05 | 11,3 |  | 2.3.10 |  | 28 | 0 | 0 | 0 |
| 939 | io.coil-kt:coil-base |  |  |  |  |  |  | 0 | 0 | 0 | 0 |
| 940 | com.google.apis:google-api-services-storage | com.google.api.services.storage ⚙️ | 2026-05-29 | 13,2 | 6,9 | v1-rev20260524-2.0.0 | v1-rev20260524-2.0.0 | 2109 | 116 | 10 | 10 |
| 941 | com.squareup.okhttp3:mockwebserver | okhttp3.mockwebserver 🏷️ | 2025-11-18 | 10,4 | 8,3 | 5.2.3 | 5.2.3 | 106 | 86 | 10 | 10 |
| 942 | com.github.java-json-tools:json-patch 🚩 |  | 2020-05-27 | 8,1 |  | 1.13 |  | 4 | 0 | 0 | 0 |
| 943 | de.undercouch:gradle-download-task |  | 2026-02-03 | 12,7 |  | 5.7.0 |  | 43 | 0 | 1 | 0 |
| 944 | org.datanucleus:datanucleus-core ⚠️ | org.datanucleus ⚙️ | 2025-04-26 | 17,6 | 8,0 | 6.0.11 | 6.0.11 | 191 | 35 | 0 | 0 |
| 945 | org.springframework.boot:spring-boot-starter-thymeleaf | spring.boot.starter.thymeleaf ⚙️ | 2026-04-23 | 12,2 | 8,3 | 3.5.14 | 3.5.14 | 286 | 221 | 39 | 39 |
| 946 | org.xerial:sqlite-jdbc | org.xerial.sqlitejdbc ✳️ | 2026-05-06 | 18,1 | 4,9 | 3.53.1.0 | 3.53.1.0 | 109 | 52 | 10 | 10 |
| 947 | com.squareup.okhttp:okhttp 🚩 |  | 2016-02-26 | 13,1 |  | 2.7.5 |  | 31 | 0 | 0 | 0 |
| 948 | io.github.detekt.sarif4k:sarif4k |  | 2026-04-13 | 5,2 |  | 0.7.0 |  | 7 | 0 | 1 | 0 |
| 949 | com.samskivert:jmustache ⚠️ | com.samskivert.jmustache ✳️ | 2023-11-30 | 15,6 | 6,9 | 1.16 | 1.16 | 17 | 2 | 0 | 0 |
| 950 | io.sentry:sentry |  | 2026-05-27 | 9,1 |  | 8.43.0 |  | 317 | 33 | 44 | 0 |
| 951 | io.coil-kt:coil |  |  |  |  |  |  | 0 | 0 | 0 | 0 |
| 952 | org.datanucleus:datanucleus-api-jdo ⚠️ | org.datanucleus.api.jdo ⚙️ | 2025-04-26 | 15,3 | 8,0 | 6.0.5 | 6.0.5 | 119 | 29 | 0 | 0 |
| 953 | org.apache.thrift:libfb303 🚩 |  | 2015-10-12 | 15,1 |  | 0.9.3 |  | 7 | 0 | 0 | 0 |
| 954 | com.swoval:file-tree-views ⚠️ |  | 2023-09-21 | 7,8 |  | 2.1.12 |  | 26 | 0 | 0 | 0 |
| 955 | org.datanucleus:datanucleus-rdbms | org.datanucleus.store.rdbms ⚙️ | 2026-05-06 | 17,6 | 8,0 | 6.0.11 | 6.0.11 | 194 | 36 | 1 | 1 |
| 956 | org.jvnet.mimepull:mimepull | org.jvnet.mimepull ✳️ | 2026-05-02 | 14,8 | 8,1 | 1.11.0 | 1.11.0 | 20 | 10 | 1 | 1 |
| ~~957~~ | ~~org.codehaus.plexus:plexus-resources~~ | ~~-~~ | ~~2025-08-25~~ | ~~19,5~~ | ~~-~~ | ~~1.3.1~~ | ~~-~~ | ~~9~~ | ~~0~~ | ~~1~~ | ~~0~~ |
| 958 | com.datadoghq:dd-java-agent |  | 2026-05-04 | 9,0 |  | 1.62.0 |  | 292 | 0 | 27 | 0 |
| 959 | com.googlecode.concurrent-trees:concurrent-trees 🚩 |  | 2017-07-14 | 13,9 |  | 2.6.1 |  | 10 | 0 | 0 | 0 |
| 960 | com.azure:azure-core-http-netty | com.azure.http.netty ✳️ | 2026-04-29 | 6,7 | 6,7 | 1.16.4 | 1.16.4 | 81 | 78 | 7 | 7 |
| 961 | org.glassfish.jersey.containers:jersey-container-servlet |  | 2026-03-18 | 14,3 |  | 2.48 |  | 154 | 6 | 11 | 6 |
| 962 | org.thymeleaf:thymeleaf | thymeleaf ⚙️ | 2026-04-21 | 15,2 | 7,6 | 3.1.5.RELEASE | 3.1.5.RELEASE | 83 | 17 | 2 | 2 |
| ~~963~~ | ~~org.apache.maven.plugin-tools:maven-plugin-tools-generators~~ | ~~-~~ | ~~2025-10-20~~ | ~~14,0~~ | ~~-~~ | ~~3.15.2~~ | ~~-~~ | ~~29~~ | ~~0~~ | ~~2~~ | ~~0~~ |
| 964 | com.jolbox:bonecp 🚩 |  | 2013-10-23 | 15,3 |  | 0.8.0.RELEASE |  | 9 | 0 | 0 | 0 |
| 965 | com.diffplug.durian:durian-collect 🚩 |  | 2016-06-27 | 10,0 |  | 1.2.0 |  | 3 | 0 | 0 | 0 |
| 966 | com.diffplug.durian:durian-core 🚩 |  | 2016-06-27 | 10,0 |  | 1.2.0 |  | 3 | 0 | 0 | 0 |
| 967 | io.netty:netty-codec-haproxy | io.netty.codec.haproxy ⚙️ | 2026-05-20 | 11,9 | 8,5 | 4.1.134.Final | 4.1.134.Final | 204 | 142 | 26 | 26 |
| 968 | org.springframework.data:spring-data-redis | spring.data.redis ⚙️ | 2026-04-17 | 14,5 | 8,7 | 4.1.0-RC1 | 4.1.0-RC1 | 285 | 216 | 33 | 33 |
| 969 | javax.jdo:jdo-api 🚩 |  | 2022-05-25 | 15,9 |  | 3.2.1 |  | 6 | 0 | 0 | 0 |
| 970 | com.google.auto.value:auto-value | com.google.errorprone.annotations ✳️ | 2025-11-11 | 12,4 | 3,7 | 1.11.1 | 2.44.0 | 49 | 7 | 1 | 1 |
| ~~971~~ | ~~org.apache.maven.plugins:maven-javadoc-plugin~~ | ~~-~~ | ~~2025-09-16~~ | ~~20,1~~ | ~~-~~ | ~~3.12.0~~ | ~~-~~ | ~~44~~ | ~~0~~ | ~~2~~ | ~~0~~ |
| 972 | org.mongodb:mongodb-driver-sync | org.mongodb.driver.sync.client ⚙️ | 2026-05-28 | 8,1 | 8,1 | 5.8.0 | 5.8.0 | 132 | 132 | 15 | 15 |
| 973 | org.osgi:org.osgi.core 🚩 |  | 2014-07-30 | 16,8 |  | 6.0.0 |  | 8 | 0 | 0 | 0 |
| 974 | org.slf4j:log4j-over-slf4j | log4j ✳️ | 2026-05-12 | 19,7 | 9,2 | 2.0.18 | 2.0.18 | 101 | 48 | 1 | 1 |
| 975 | org.mapstruct:mapstruct-processor | org.mapstruct.processor ⚙️ | 2026-02-01 | 13,0 | 8,9 | 1.7.0.Beta1 | 1.7.0.Beta1 | 48 | 31 | 1 | 1 |
| 976 | org.seleniumhq.selenium:selenium-manager | org.seleniumhq.selenium.manager 🏷️ | 2026-05-12 | 3,6 | 3,6 | 4.44.0 | 4.44.0 | 52 | 52 | 11 | 11 |
| 977 | org.springframework.data:spring-data-keyvalue | spring.data.keyvalue ⚙️ | 2026-04-17 | 10,8 | 8,7 | 4.1.0-RC1 | 4.1.0-RC1 | 255 | 216 | 33 | 33 |
| 978 | com.intellij:annotations 🚩 |  | 2013-02-24 | 20,0 |  | 12.0 |  | 6 | 0 | 0 | 0 |
| 979 | org.apache.hadoop:hadoop-yarn-server-web-proxy |  | 2026-03-24 | 14,3 |  | 3.5.0 |  | 83 | 0 | 3 | 0 |
| 980 | org.apache.kerby:kerby-asn1 |  | 2025-11-07 | 10,7 |  | 2.1.1 |  | 12 | 0 | 1 | 0 |
| 981 | io.vavr:vavr | io.vavr ✳️ | 2026-03-01 | 9,1 | 8,5 | 1.0.1 | 1.0.1 | 18 | 15 | 4 | 4 |
| 982 | org.apache.commons:commons-dbcp2 🚩 |  | 2021-07-31 | 12,3 |  | 2.9.0 |  | 12 | 0 | 0 | 0 |
| 983 | kr.motd.maven:os-maven-plugin 🚩 |  | 2022-11-07 | 12,1 |  | 1.7.1 |  | 17 | 0 | 0 | 0 |
| 984 | org.scala-sbt:util-logging_2.12 |  | 2026-05-02 | 9,4 |  | 1.12.11 |  | 153 | 0 | 21 | 0 |
| 985 | org.scala-sbt:util-relation_2.12 |  | 2026-05-02 | 9,4 |  | 1.12.11 |  | 153 | 0 | 21 | 0 |
| 986 | org.scala-sbt:util-control_2.12 |  | 2026-05-02 | 9,4 |  | 1.12.11 |  | 153 | 0 | 21 | 0 |
| 987 | com.google.cloud:google-cloud-storage |  | 2026-05-06 | 9,8 |  | 2.68.0 |  | 315 | 0 | 22 | 0 |
| 988 | io.mockk:mockk-dsl |  | 2026-05-29 | 8,5 |  | 1.14.11 |  | 94 | 0 | 7 | 0 |
| 989 | com.amazonaws:aws-java-sdk-cloudwatch |  | 2025-12-29 | 11,6 |  | 1.12.797 |  | 1956 | 0 | 14 | 0 |
| 990 | org.scala-sbt:io_2.12 |  | 2026-05-07 | 9,6 |  | 1.12.0 |  | 83 | 0 | 1 | 0 |
| 991 | org.attoparser:attoparser ⚠️ | attoparser ⚙️ | 2023-07-30 | 13,7 | 8,2 | 2.0.7.RELEASE | 2.0.7.RELEASE | 20 | 3 | 0 | 0 |
| 992 | org.scala-sbt:launcher-interface |  | 2026-03-19 | 11,2 |  | 1.6.1 |  | 31 | 0 | 5 | 0 |
| 993 | com.github.jnr:jnr-unixsocket | org.jnrproject.unixsocket ⚙️ | 2026-03-31 | 13,7 | 7,3 | 0.38.25 | 0.38.25 | 62 | 41 | 2 | 2 |
| 994 | com.github.jnr:jnr-enxio | org.jnrproject.enxio ⚙️ | 2026-03-31 | 13,7 | 7,3 | 0.32.20 | 0.32.20 | 50 | 32 | 2 | 2 |
| 995 | org.springdoc:springdoc-openapi-starter-webmvc-ui | org.springdoc.openapi.ui ⚙️ | 2026-04-11 | 4,3 | 4,3 | 2.8.17 | 2.8.17 | 46 | 46 | 15 | 15 |
| 996 | org.scala-sbt:zinc-classfile_2.12 |  | 2026-01-04 | 9,4 |  | 1.12.0 |  | 125 | 0 | 4 | 0 |
| 997 | org.scala-sbt:zinc_2.12 |  | 2026-01-04 | 9,4 |  | 1.12.0 |  | 125 | 0 | 4 | 0 |
| 998 | org.scala-sbt:zinc-apiinfo_2.12 |  | 2026-01-04 | 9,4 |  | 1.12.0 |  | 125 | 0 | 4 | 0 |
| 999 | org.scala-sbt:zinc-core_2.12 |  | 2026-01-04 | 9,4 |  | 1.12.0 |  | 125 | 0 | 4 | 0 |
| 1000 | org.scala-sbt:zinc-classpath_2.12 |  | 2026-01-04 | 9,4 |  | 1.12.0 |  | 125 | 0 | 4 | 0 |
