# Module summary

> ### Powered by [Jenesis](https://github.com/raphw/jenesis)
> _A modern Java build tool: Java-native config, plugin-free, with `module-info.java` treated as a feature, not an afterthought._

_Index timestamp: 2026-06-26 03:30:46 UTC_  
_Current chunk started: 2026-07-06 14:22:38 UTC_  
_Index chain id: `1318453614498`_  
_Last applied index chunk: 931_  

## Top artifacts by year

Real-world Java projects lean on a fairly small set of widely-shared libraries, while the catalogue as a whole carries a very long tail of artifacts that almost nothing depends on. Adoption measured across that whole tail understates what most projects actually encounter. The reports below instead rank the most depended-on artifacts and show how many of them ship a module, which gives a clearer view of module adoption where it matters and how it has moved over time. The bleeding-edge report assesses the latest list against current data, uncropped.

[2019](top/2019.md) · [2020](top/2020.md) · [2021](top/2021.md) · [2022](top/2022.md) · [2023](top/2023.md) · [2024](top/2024.md) · [2025](top/2025.md) · [bleeding edge](top/BLEEDING.md)

## Totals

Catalogue-wide counts. Unless a section is explicitly labelled as "audit" or "history", every row-level number here and below describes the canonical view of the catalogue: shaded or otherwise non-authoritative claims on a module name do not contribute. "Artifacts" counts JARs (one row per groupId/artifactId/version/classifier coordinate); "modules" counts the named or automatic-module identities those JARs expose. Distinct counts deduplicate by name. "With module-info version" means the module declared a non-empty version in its `module-info`, whether or not it matches the Maven coordinate version.

| Metric | Value |
|---|---:|
| Total artifacts scanned | 17 671 858 |
| Non-module artifacts | 15 731 369 |
| Modular artifacts | 1 602 884 |
| Total automatic modules | 1 260 147 |
| Total named modules | 342 737 |
| Total named modules with module-info version | 260 116 |
| Distinct Maven artifacts | 663 848 |
| Distinct module names | 40 118 |
| Distinct automatic modules | 21 219 |
| Distinct named modules | 17 444 |
| Distinct named modules with module-info version | 12 729 |
| Distinct groupIds publishing modules | 5 134 |
| Most recent tracked publication | 2026-06-25 19:15:56 UTC |

## Resolved catalogue size

Across every `modules[-classifier].tsv` under `data/modules/`, the resolved view holds **330 455** distinct module-version rows. Each row is one (module name, classifier, `module-info` version) combination that survived owner resolution; rows whose `module-info` version contradicts the Maven version are excluded by the resolution policy.

Of those, **243** rows (52 distinct values) carry a version key that is not a valid `ModuleDescriptor.Version` - it does not begin with a digit (e.g. a leading `v`, `r`, or `master-`, or stray strings like `@version@`). Such modules still load fine on the module path: a module version is optional metadata that resolution never uses, so the JVM keeps the string as `rawVersion()` and leaves the parsed `version()` empty rather than refusing the module.

### Republished JVM modules

**15** module names that ship inside the JDK itself (`java.*` / `jdk.*` platform modules such as `java.sql`, `jdk.unsupported`, the `java --list-modules` set) have been republished on Maven Central under some coordinate, across **709** publication rows. They are excluded from the resolved module-version space entirely: the JVM always provides these modules, so no Maven artifact can be resolved as one (the platform's own copy is found first on the module path and shadows anything supplied externally). Such names stay in the `versions.tsv` audit log and remain fetchable as plain coordinates via `artifacts.tsv`, but get no `modules.tsv`. Legacy Java EE modules removed from the JDK (JEP 320: `java.xml.bind`, `java.transaction`, ...) are not counted here - they are absent from a modern JVM and resolve normally - and JavaFX (`javafx.*`) is not a JDK module either.

## Type breakdown

Named vs automatic counts. Distinct-module counts use the **latest** version's type, so a module that started automatic and is currently named counts as named. Row counts include every classifier variant.

| Type | Distinct modules | Published rows |
|---|---:|---:|
| Named | 17 444 | 342 737 |
| Automatic | 21 219 | 1 260 147 |

## Classifiers

A classifier-keyed JAR (e.g. `-jakarta`, `-jdk8`, `-jar-with-dependencies`) that exposes a module is tracked as a separate `<moduleName>-<classifier>` identity with its own `versions-<classifier>.tsv` / `artifacts-<classifier>.tsv` / `modules-<classifier>.tsv`. Ownership of a module name spans its whole classifier space (the first groupId to publish the name owns every classifier under it), and the resolver serves a variant at `/module/<name>-<classifier>/...`.

| Metric | Value |
|---|---:|
| Distinct classifiers | 212 |
| Modules with at least one classifier variant | 2 024 |
| Classifier variant files (`versions-<classifier>.tsv`) | 4 037 |
| Classifier-keyed resolved module-version rows | 21 829 |

Top classifiers by resolved module-version rows:

| Classifier | Resolved rows |
|---|---:|
| `linux` | 1 298 |
| `mac` | 1 298 |
| `win` | 1 297 |
| `jdk9` | 1 191 |
| `mac-aarch64` | 771 |
| `linux-x86_64` | 730 |
| `android` | 608 |
| `ios` | 607 |
| `jar-with-dependencies` | 595 |
| `desktop` | 589 |
| `windows-x86_64` | 584 |
| `natives-windows` | 574 |
| `natives-macos` | 548 |
| `natives-linux` | 543 |
| `macosx-x86_64` | 526 |

## `module-info` version field across named publications

Every table in this section is scoped to **canonical (no-classifier) named publications**. Classifier-keyed rows (mostly fat-jar / shaded variants that bundle another module under their own Maven coordinate) are excluded, because the bundled module's `module-info` version is expected to contradict the bundling Maven version, which would otherwise overwhelm the signal here.

Counts canonical **named publications** (one count per published JAR, not per distinct module) by how the JAR's `module-info` fills its optional version attribute. Automatic JARs are excluded; they carry no `module-info`. The three rows are mutually exclusive and cover every canonical named publication in the catalogue. The breakdown table below classifies the `mismatching` bucket by *why* the two versions differ.

| Publication category | Publications |
|---|---:|
| `module-info` version matches the Maven coordinate version | 254 555 |
| `module-info` version is non-empty but differs from the Maven coordinate version | 5 561 |
| `module-info` declared no version (Maven coordinate version is the only reference) | 54 051 |

Same breakdown but counted once per **canonical module**, against the latest named row in its no-classifier resolved view (the row a consumer fetching the "latest" of a module would land on). Modules whose latest row is automatic are excluded.

| Module category (by latest canonical named row) | Modules |
|---|---:|
| `module-info` version matches the Maven coordinate version | 11 298 |
| `module-info` version is non-empty but differs from the Maven coordinate version | 290 |
| `module-info` declared no version (Maven coordinate version is the only reference) | 4 129 |

Each row describes what the **version-mismatch filter** (drop every named row whose `module-info` version semantically contradicts its Maven coordinate version) leaves behind in the module's `modules.tsv`, counted once per **canonical module** (no-classifier view). Modules with no canonical named row are out of scope. The first row is the in-scope total; rows two through four are mutually exclusive and sum to it; the fifth row overlaps with rows three and four (it's the subset whose head-of-`modules.tsv` is the one the filter removes).

| Module version filtering impact | Module names |
|---|---:|
| Canonical modules with at least one named row (in scope) | 15 852 |
| Filter keeps every named row: `modules.tsv` is unchanged | 15 243 |
| Filter drops some named rows but at least one survives: `modules.tsv` shrinks | 497 |
| Filter drops every named row: `modules.tsv` is removed entirely | 112 |
| Filter drops the module's current top row: "latest" shifts to an older Maven version (or vanishes if fully lost) | 290 |

## Mismatching module-info version patterns

Breaks down the publications whose `module-info` version differs from the Maven coordinate version (the middle row of the previous table) by *why* they differ. The first several rows are formatting drift (publisher forgot to drop a `-SNAPSHOT`, a repackager's coordinate suffix, build-metadata `+` labels, extra dot-segments); `Unresolved placeholder` is a build-time `${...}` substitution that leaked through; `Different major segment` is a strong proxy for shaded/bundled artifacts whose `module-info` comes from a different versioning lineage; `Substantively different` is the remainder where the versions share a first segment but otherwise differ. Percentages are share of the differing-version bucket.

| Pattern | Rows | Share |
|---|---:|---:|
| Module = Maven + `-SNAPSHOT` (release that forgot to drop SNAPSHOT) | 3 199 | 57.5% |
| Module = Maven + `-<other suffix>` (build label, patch tag) | 12 | 0.2% |
| Maven = Module + `-<suffix>` (repackager appended a coordinate suffix) | 174 | 3.1% |
| Module = Maven + `.<segment>` (extra dot-segment in module-info) | 4 | 0.1% |
| Maven = Module + `.<segment>` (extra dot-segment in coordinate) | 21 | 0.4% |
| Module = Maven + `+<metadata>` (build metadata in module-info) | 0 | 0.0% |
| Maven = Module + `+<metadata>` (build metadata in coordinate) | 0 | 0.0% |
| Unresolved `${...}` placeholder in either version | 19 | 0.3% |
| Different major segment (likely shaded/bundled artifact) | 460 | 8.3% |
| Substantively different (same major, different version) | 1 672 | 30.1% |

## Type transitions

Modules that have switched between named and automatic over their history. A module counts toward a direction when its latest version's type differs from at least one earlier version's type.

| Direction | Modules |
|---|---:|
| Automatic → Named | 1 543 |
| Named → Automatic | 136 |

## Recent activity (last 7 days)

Activity in the 7-day window ending at the **most recent tracked publication** (shown in Totals), not at this file's generation time. Maven Central's index typically lags real time by up to a week, so a now-relative window is usually empty even when the crawl is fully caught up; anchoring to the freshest publication keeps the window meaningful. "Modules with a publication" counts distinct module names that received at least one new version; "new version rows" is the total count of those publications. Per-row counts split by the publication's own type; per-module counts attribute each module to whichever type it has at its latest version, so a module that switched named↔automatic shows up under its current type. The `Named`/`Automatic` columns are canonical (owner-resolved); the trailing `Non-modular artifacts` row counts distinct `(groupId, artifactId)` that published a coordinate with no module identity (distinct scanned artifacts minus distinct modular artifacts in the window), so it stands apart from the modular rows rather than summing into them.

| Metric | Total | Named | Automatic |
|---|---:|---:|---:|
| Modules with a publication | 1 951 | 650 | 1 301 |
| New version rows | 3 258 | 1 155 | 2 103 |
| Non-modular artifacts | 17 513 | - | - |

## Monthly publications by type (last 12 months)

Per-month counts of **distinct entities** that published in the month. `Named`/`Automatic` count distinct canonical (owner-resolved) module names by type; `Non-modular artifacts` counts distinct `(groupId, artifactId)` that published a coordinate carrying no module identity (distinct scanned artifacts minus distinct modular artifacts in the month). All three share one bar scale. Non-modular artifacts outnumber modules roughly 10:1, so the columns use different shades to stay legible at a glance: `█` named, `▓` automatic, `░` non-modular. The `(x%)` after each count is that type's share of the month's total (named + automatic + non-modular), so a row's three percentages sum to ~100%.

| Month | Named modules | Automatic modules | Non-modular artifacts |
|---|---|---|---|
| 2025-07 | `█`&nbsp;2 785 (4.7%) | `▓▓`&nbsp;4 773 (8.1%) | `░░░░░░░░░░░░░░░░░`&nbsp;51 114 (87.1%) |
| 2025-08 | `█`&nbsp;2 420 (4.1%) | `▓▓`&nbsp;5 444 (9.2%) | `░░░░░░░░░░░░░░░░░░`&nbsp;51 327 (86.7%) |
| 2025-09 | `█`&nbsp;2 820 (4.3%) | `▓▓`&nbsp;5 418 (8.3%) | `░░░░░░░░░░░░░░░░░░░░`&nbsp;57 059 (87.4%) |
| 2025-10 | `█`&nbsp;2 841 (4.1%) | `▓▓`&nbsp;5 378 (7.9%) | `░░░░░░░░░░░░░░░░░░░░░`&nbsp;60 282 (88.0%) |
| 2025-11 | `█`&nbsp;2 437 (3.7%) | `▓▓`&nbsp;5 378 (8.2%) | `░░░░░░░░░░░░░░░░░░░░`&nbsp;57 581 (88.0%) |
| 2025-12 | `█`&nbsp;2 639 (4.0%) | `▓▓`&nbsp;5 634 (8.6%) | `░░░░░░░░░░░░░░░░░░░░`&nbsp;57 611 (87.4%) |
| 2026-01 | `█`&nbsp;2 871 (4.1%) | `▓▓`&nbsp;5 483 (7.8%) | `░░░░░░░░░░░░░░░░░░░░░`&nbsp;61 774 (88.1%) |
| 2026-02 | `█`&nbsp;2 623 (3.8%) | `▓▓`&nbsp;5 514 (7.9%) | `░░░░░░░░░░░░░░░░░░░░░`&nbsp;61 432 (88.3%) |
| 2026-03 | `█`&nbsp;3 103 (3.9%) | `▓▓`&nbsp;6 696 (8.4%) | `░░░░░░░░░░░░░░░░░░░░░░░░`&nbsp;70 131 (87.7%) |
| 2026-04 | `█`&nbsp;3 387 (4.4%) | `▓▓`&nbsp;5 836 (7.6%) | `░░░░░░░░░░░░░░░░░░░░░░░`&nbsp;67 391 (88.0%) |
| 2026-05 | `█`&nbsp;3 440 (4.4%) | `▓▓`&nbsp;5 609 (7.2%) | `░░░░░░░░░░░░░░░░░░░░░░░░`&nbsp;68 927 (88.4%) |
| 2026-06 | `█`&nbsp;2 872 (4.2%) | `▓▓`&nbsp;5 019 (7.3%) | `░░░░░░░░░░░░░░░░░░░░░`&nbsp;60 828 (88.5%) |

## Naming patterns

How module names relate to their publishing groupId. "Competing groupIds" counts modules whose name has been published under more than one groupId across history (i.e. collisions). Classifier variants are summarised in the Classifiers section above.

| Pattern | Modules |
|---|---:|
| Multiple competing groupIds in audit history | 4 032 |

### Leading dot-segments shared with the owning groupId

For each canonical (no-classifier) module that resolved to an owner (implicit or explicit), counts how many leading dot-segments its module name shares with the owner's groupId. A high share is the textbook JPMS pattern (e.g. module `com.example.foo` published by groupId `com.example.foo`); zero indicates a module name that diverges entirely from its publisher's groupId. Classifier variants are out of scope because they share the canonical's groupId by construction. Empty buckets render as `-`.

| Shared leading dot-segments | Canonical modules |
|---:|---:|
| 0 | 8 413 |
| 1 | 1 036 |
| 2 | 11 632 |
| 3 | 12 300 |
| 4 | 2 341 |
| 5 | 332 |
| 6 | 13 |
| 7 | - |
| 8 | 2 |

## Processing errors

Recorded permanent failures across every scanned coordinate. Variable bits of well-known error classes (URLs, shaded package names, classfile entry indexes, HTTP status codes, line numbers, class identifiers) are replaced with placeholders like `<URL>`, `<PACKAGE>`, `<CLASS>` so messages that differ only in those bits aggregate into one row.

`Incorrectly indexed` is the dominant failure class: coordinates the upstream Nexus index mis-stamps as having a main JAR when only a POM was ever published (BOMs, parent POMs). The crawler probes once, the fetch 404s, and the row is recorded permanently; these are an upstream-data artifact, not a problem with the JAR, and are excluded from the `Total artifacts scanned` and `Modular artifacts` totals above. `Genuine artifact errors` is the remainder - malformed JARs, unparseable `module-info`, and the like - and is broken out in the top-N table below.

| Metric | Value |
|---|---:|
| Total failed coordinates | 2 329 127 |
| Incorrectly indexed (mis-stamped 404s) | 2 326 910 |
| Genuine artifact errors | 2 217 |

### Top 25 genuine error messages

Excludes the mis-stamped-404 class broken out above, so the genuine artifact errors are visible rather than buried beneath it.

| Error message | Count |
|---|---:|
| `IllegalArgumentException: End of central directory record not found in supplied tail buffer` | 592 |
| `InvalidModuleDescriptorException: Package <PACKAGE> missing from ModulePackages class file attribute` | 485 |
| `IllegalArgumentException: Illegal character in path at index <INDEX>: <PATH>` | 286 |
| `InvalidModuleDescriptorException: this_class should be module-info` | 246 |
| `InvalidModuleDescriptorException: <CLASS>: unnamed package` | 156 |
| `InvalidModuleDescriptorException: Unsupported major.minor version <VERSION>` | 92 |
| `InvalidModuleDescriptorException: CONSTANT_Package at entry <ENTRY> has illegal character: '.'` | 80 |
| `IllegalArgumentException: Expected central file header signature at offset <OFFSET>` | 76 |
| `InvalidModuleDescriptorException: <CLASS>: Invalid service type name: '<NAME>' is not a Java identifier` | 45 |
| `InvalidModuleDescriptorException: Index into constant pool out of range` | 34 |
| `IllegalArgumentException: Expected ZIP64 end of central directory signature at offset <OFFSET>` | 24 |
| `InvalidModuleDescriptorException: <CLASS>: is not a qualified name of a Java class in a named package` | 23 |
| `InvalidModuleDescriptorException: Dependence on self` | 20 |
| `IOException: invalid header field (line <LINE>)` | 17 |
| `InvalidModuleDescriptorException: The requires entry for java.base has ACC_SYNTHETIC set` | 15 |
| `InvalidModuleDescriptorException: Dependence upon <MODULE> already declared` | 8 |
| `InvalidModuleDescriptorException: Attribute ModuleTarget does not match its expected length` | 4 |
| `InvalidModuleDescriptorException: The requires table must have an entry for java.base` | 4 |
| `InvalidModuleDescriptorException: Exported package <PACKAGE> already declared` | 3 |
| `InvalidModuleDescriptorException: CONSTANT_Class at entry <ENTRY> has illegal character: ';'` | 2 |
| `InvalidModuleDescriptorException: CONSTANT_Module expected at entry: <ENTRY>` | 2 |
| `InvalidModuleDescriptorException: CONSTANT_Package at entry <ENTRY> has illegal character: ';'` | 2 |
| `IllegalArgumentException: Illegal character in fragment at index <INDEX>: <PATH>` | 1 |

## Top 25 modules by version count

Modules with the longest release history. Counts come from the main (no-classifier) view, so a classifier variant like `-jar-with-dependencies` does not inflate the number. Module families that would otherwise occupy many adjacent slots are folded into a single `<prefix>.*` row; the cell is rendered as `[min, max]` when the absorbed modules have different counts, and a trailing `(N modules)` notes how many were absorbed.

| Module | Versions |
|---|---:|
| `software.amazon.awssdk.* (502 modules)` | [1, 1 775] |
| `org.scala.lang.scala3.* (9 modules)` | [1, 1 235] |
| `org.scala.lang.* (excl. scala3) (4 modules)` | [354, 1 213] |
| `com.graphqljava` | 1 167 |
| `net.minestom.server` | 745 |
| `com.google.api.services.* (341 modules)` | [1, 553] |
| `com.azure.sdk.template` | 470 |
| `com.hazelcast.all` | 453 |
| `javassist` | 453 |
| `org.apache.commons.math3` | 453 |
| `org.apache.cxf` | 453 |
| `org.apache.poi.ooxml` | 453 |
| `io.undertow.parser.generator` | 446 |
| `undertow.* (3 modules)` | [415, 446] |
| `com.guicedee.* (69 modules)` | [1, 445] |
| `jandex` | 439 |
| `dom4j` | 437 |
| `jakarta.enterprise.* (2 modules)` | 437 |
| `jaxen` | 433 |
| `primefaces` | 415 |
| `com.tigerbeetle` | 408 |
| `org.apache.poi` | 403 |
| `be.seeseemelk.mockbukkit` | 394 |
| `com.entityassist` | 394 |
| `io.ebean` | 393 |

## Top 25 groupIds by module count

GroupIds that publish the most distinct module names, sorted by module count. Each `(groupId, moduleName)` pair counts once regardless of how many versions or classifier variants exist.

| groupId | Modules published |
|---|---:|
| `org.bytedeco` | 673 |
| `software.amazon.awssdk` | 505 |
| `org.lwjgl` | 492 |
| `org.apereo.cas` | 484 |
| `org.springframework.boot` | 348 |
| `io.github.shuigedeng` | 347 |
| `io.opentelemetry.javaagent.instrumentation` | 345 |
| `com.google.apis` | 341 |
| `org.kie` | 280 |
| `com.azure.resourcemanager` | 259 |
| `org.eclipse.platform` | 257 |
| `com.gitee.zodiacstack` | 211 |
| `org.neo4j` | 211 |
| `io.micronaut.oraclecloud` | 210 |
| `com.blazebit` | 209 |
| `io.github.bluetape4k` | 193 |
| `com.aoapps` | 189 |
| `io.netty` | 186 |
| `org.drools` | 185 |
| `com.helger` | 182 |
| `org.kie.kogito` | 182 |
| `org.ogc-schemas` | 175 |
| `net.solarnetwork.node` | 170 |
| `com.azure` | 157 |
| `io.micronaut` | 154 |

## Top 25 modules with most colliding groupIds

Module names that have been published under the most different groupIds across history. A high count indicates name reuse: forks, rebranded artifacts, or coordinate moves whose historical declarations remain on record even after the canonical publisher is set.

| Module | Distinct groupIds |
|---|---:|
| `com.google.gson` | 304 |
| `com.fasterxml.jackson.databind` | 257 |
| `com.fasterxml.jackson.core` | 234 |
| `com.fasterxml.jackson.annotation [-jar-with-dependencies]` | 225 |
| `com.fasterxml.jackson.annotation` | 185 |
| `org.apache.logging.log4j` | 179 |
| `org.slf4j` | 175 |
| `kotlin.stdlib` | 144 |
| `kotlin.stdlib.jdk8` | 135 |
| `com.google.gson [-jar-with-dependencies]` | 129 |
| `com.fasterxml.jackson.databind [-jar-with-dependencies]` | 110 |
| `org.apache.logging.log4j [-jar-with-dependencies]` | 93 |
| `java.xml.bind [-jar-with-dependencies]` | 89 |
| `java.xml.bind` | 87 |
| `net.bytebuddy` | 85 |
| `org.objectweb.asm [-jar-with-dependencies]` | 79 |
| `kotlin.stdlib.jdk8 [-all]` | 77 |
| `com.fasterxml.jackson.core [-jar-with-dependencies]` | 75 |
| `info.picocli` | 72 |
| `com.google.gson [-all]` | 71 |
| `org.apache.commons.lang3` | 71 |
| `com.fasterxml.jackson.module.jaxb` | 66 |
| `org.apache.commons.codec` | 64 |
| `org.apache.commons.io` | 64 |
| `org.objectweb.asm` | 64 |

## Top 25 modules updated in the last 7 days

Modules whose most recent publication landed in the 7-day window ending at the most recent tracked publication (same window as `Recent activity`, anchored to the freshest publication rather than now since the index lags up to a week), sorted newest first. Use this as a recency view; the count above (`Recent activity`) gives the totals while this table names which modules they were.

| Module | Last publication |
|---|---|
| `ch.qos.logback.classic` | 2026-06-25 19:15:56 UTC |
| `ch.qos.logback.core` | 2026-06-25 19:15:56 UTC |
| `build.jenesis.launcher` | 2026-06-25 09:48:00 UTC |
| `com.azure.resourcemanager.redhatopenshift` | 2026-06-25 06:26:32 UTC |
| `ai.tegmentum.webassembly4j.api` | 2026-06-25 03:31:50 UTC |
| `ai.tegmentum.wasmtime4j.nativeloader` | 2026-06-25 02:44:34 UTC |
| `ai.tegmentum.wasmtime4j.natives [-all-platforms]` | 2026-06-25 02:44:34 UTC |
| `ai.tegmentum.wasmtime4j.natives [-linux-aarch64]` | 2026-06-25 02:44:34 UTC |
| `ai.tegmentum.wasmtime4j.natives [-linux-x86_64]` | 2026-06-25 02:44:34 UTC |
| `ai.tegmentum.wasmtime4j.panama` | 2026-06-25 02:44:34 UTC |
| `ai.tegmentum.wasmtime4j.natives` | 2026-06-25 02:44:33 UTC |
| `ai.tegmentum.wasmtime4j.natives [-darwin-aarch64]` | 2026-06-25 02:44:33 UTC |
| `ai.tegmentum.wasmtime4j.natives [-darwin-x86_64]` | 2026-06-25 02:44:33 UTC |
| `ai.tegmentum.wasmtime4j.natives [-windows-x86_64]` | 2026-06-25 02:44:33 UTC |
| `com.github.copilot.java` | 2026-06-25 02:33:55 UTC |
| `com.hedera.pbj.grpc.helidon` | 2026-06-24 20:26:53 UTC |
| `com.hedera.pbj.runtime` | 2026-06-24 20:26:53 UTC |
| `com.hedera.pbj.grpc.client.helidon` | 2026-06-24 20:26:52 UTC |
| `com.hedera.pbj.grpc.helidon.config` | 2026-06-24 20:26:52 UTC |
| `com.amazon.ion` | 2026-06-24 19:15:52 UTC |
| `com.oicana` | 2026-06-24 18:01:02 UTC |
| `com.azure.resourcemanager.networkcloud` | 2026-06-24 17:55:42 UTC |
| `com.sqlapp.command` | 2026-06-24 12:49:20 UTC |
| `com.sqlapp.gradle.plugin` | 2026-06-24 12:49:20 UTC |
| `com.sqlapp.graphviz` | 2026-06-24 12:49:20 UTC |

## Top 25 groupIds by average versions per module

Restricted to groupIds publishing at least 3 modules so the average isn't dominated by single-module outliers.

| groupId | Modules | Total versions | Avg versions / module |
|---|---:|---:|---:|
| `software.amazon.awssdk` | 505 | 606 997 | 1202.0 |
| `org.scala-lang` | 19 | 12 632 | 664.8 |
| `com.graphql-java` | 3 | 1 333 | 444.3 |
| `com.guicedee.servlets` | 11 | 3 517 | 319.7 |
| `io.fluxzero` | 5 | 1 540 | 308.0 |
| `com.jwebmp.plugins.angular` | 17 | 5 151 | 303.0 |
| `com.jwebmp.plugins.effects` | 3 | 909 | 303.0 |
| `com.jwebmp.plugins.iconsets` | 7 | 2 121 | 303.0 |
| `com.jwebmp.plugins.javascript` | 12 | 3 632 | 302.7 |
| `com.jwebmp.plugins.bootstrap` | 8 | 2 409 | 301.1 |
| `com.guicedee.services` | 45 | 12 368 | 274.8 |
| `com.guicedee.persistence` | 9 | 2 377 | 264.1 |
| `net.minestom` | 3 | 779 | 259.7 |
| `com.jwebmp.plugins.jquery` | 17 | 4 350 | 255.9 |
| `com.jwebmp.plugins.graphing` | 5 | 1 247 | 249.4 |
| `com.jwebmp.plugins.forms` | 10 | 2 436 | 243.6 |
| `com.jwebmp.plugins.security` | 3 | 707 | 235.7 |
| `systems.manifold` | 41 | 8 970 | 218.8 |
| `com.jwebmp.core` | 3 | 594 | 198.0 |
| `net.serenity-bdd` | 27 | 5 238 | 194.0 |
| `org.springframework` | 23 | 4 288 | 186.4 |
| `org.apache.tomcat.embed` | 5 | 846 | 169.2 |
| `com.netflix.spectator` | 27 | 4 554 | 168.7 |
| `org.springframework.data` | 24 | 3 995 | 166.5 |
| `org.hibernate.orm` | 26 | 4 287 | 164.9 |

_This file is regenerated on every `ModuleSummary` run; previous content is discarded._
