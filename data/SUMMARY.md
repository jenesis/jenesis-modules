# Module summary

> ### Powered by [Jenesis](https://github.com/raphw/jenesis)
> _A modern Java build tool: Java-native config, plugin-free, with `module-info.java` treated as a feature, not an afterthought._

_Index timestamp: 2026-05-20 23:40:56 UTC_  
_Current chunk started: 2026-05-27 15:09:04 UTC_  
_Index chain id: `1318453614498`_  
_Last applied index chunk: 928_  

## Top artifacts by year

Real-world Java projects lean on a fairly small set of widely-shared libraries, while the catalogue as a whole carries a very long tail of artifacts that almost nothing depends on. Adoption measured across that whole tail understates what most projects actually encounter. The reports below instead rank the most depended-on artifacts and show how many of them ship a module, which gives a clearer view of module adoption where it matters and how it has moved over time. The bleeding-edge report assesses the latest list against current data, uncropped.

[2019](top/2019.md) · [2020](top/2020.md) · [2021](top/2021.md) · [2022](top/2022.md) · [2023](top/2023.md) · [2024](top/2024.md) · [bleeding edge](top/bleeding.md)

## Totals

Catalogue-wide counts. Unless a section is explicitly labelled as "audit" or "history", every row-level number here and below describes the canonical view of the catalogue: shaded or otherwise non-authoritative claims on a module name do not contribute. "Artifacts" counts JARs (one row per groupId/artifactId/version/classifier coordinate); "modules" counts the named or automatic-module identities those JARs expose. Distinct counts deduplicate by name. "With module-info version" means the module declared a non-empty version in its `module-info`, whether or not it matches the Maven coordinate version.

| Metric | Value |
|---|---:|
| Total artifacts scanned | 17 252 834 |
| Non-module artifacts | 15 357 856 |
| Modular artifacts | 1 541 349 |
| Total automatic modules | 1 219 497 |
| Total named modules | 321 852 |
| Total named modules with module-info version | 225 100 |
| Distinct Maven artifacts | 648 778 |
| Distinct module names | 39 120 |
| Distinct automatic modules | 20 884 |
| Distinct named modules | 18 236 |
| Distinct named modules with module-info version | 13 125 |
| Distinct groupIds publishing modules | 5 528 |
| Most recent tracked publication | 2026-05-20 21:16:53 UTC |

## Resolved catalogue size

Across every `modules[-classifier].tsv` under `data/modules/`, the resolved view holds **292 732** distinct module-version rows. Each row is one (module name, classifier, `module-info` version) combination that survived owner resolution; rows whose `module-info` version contradicts the Maven version are excluded by the resolution policy.

## Type breakdown

Named vs automatic counts. Distinct-module counts use the **latest** version's type, so a module that started automatic and is currently named counts as named. Row counts include every classifier variant.

| Type | Distinct modules | Published rows |
|---|---:|---:|
| Named | 18 236 | 321 852 |
| Automatic | 20 884 | 1 219 497 |

## `module-info` version field across named publications

Every table in this section is scoped to **canonical (no-classifier) named publications**. Classifier-keyed rows (mostly fat-jar / shaded variants that bundle another module under their own Maven coordinate) are excluded, because the bundled module's `module-info` version is expected to contradict the bundling Maven version, which would otherwise overwhelm the signal here.

Counts canonical **named publications** (one count per published JAR, not per distinct module) by how the JAR's `module-info` fills its optional version attribute. Automatic JARs are excluded; they carry no `module-info`. The three rows are mutually exclusive and cover every canonical named publication in the catalogue. The breakdown table below classifies the `mismatching` bucket by *why* the two versions differ.

| Publication category | Publications |
|---|---:|
| `module-info` version matches the Maven coordinate version | 219 070 |
| `module-info` version is non-empty but differs from the Maven coordinate version | 6 030 |
| `module-info` declared no version (Maven coordinate version is the only reference) | 50 669 |

Same breakdown but counted once per **canonical module**, against the latest named row in its no-classifier resolved view (the row a consumer fetching the "latest" of a module would land on). Modules whose latest row is automatic are excluded.

| Module category (by latest canonical named row) | Modules |
|---|---:|
| `module-info` version matches the Maven coordinate version | 10 667 |
| `module-info` version is non-empty but differs from the Maven coordinate version | 349 |
| `module-info` declared no version (Maven coordinate version is the only reference) | 4 105 |

Each row describes what the **version-mismatch filter** (drop every named row whose `module-info` version semantically contradicts its Maven coordinate version) leaves behind in the module's `modules.tsv`, counted once per **canonical module** (no-classifier view). Modules with no canonical named row are out of scope. The first row is the in-scope total; rows two through four are mutually exclusive and sum to it; the fifth row overlaps with rows three and four (it's the subset whose head-of-`modules.tsv` is the one the filter removes).

| Module version filtering impact | Module names |
|---|---:|
| Canonical modules with at least one named row (in scope) | 15 249 |
| Filter keeps every named row: `modules.tsv` is unchanged | 14 595 |
| Filter drops some named rows but at least one survives: `modules.tsv` shrinks | 516 |
| Filter drops every named row: `modules.tsv` is removed entirely | 138 |
| Filter drops the module's current top row: "latest" shifts to an older Maven version (or vanishes if fully lost) | 349 |

## Mismatching module-info version patterns

Breaks down the publications whose `module-info` version differs from the Maven coordinate version (the middle row of the previous table) by *why* they differ. The first several rows are formatting drift (publisher forgot to drop a `-SNAPSHOT`, a repackager's coordinate suffix, build-metadata `+` labels, extra dot-segments); `Unresolved placeholder` is a build-time `${...}` substitution that leaked through; `Different major segment` is a strong proxy for shaded/bundled artifacts whose `module-info` comes from a different versioning lineage; `Substantively different` is the remainder where the versions share a first segment but otherwise differ. Percentages are share of the differing-version bucket.

| Pattern | Rows | Share |
|---|---:|---:|
| Module = Maven + `-SNAPSHOT` (release that forgot to drop SNAPSHOT) | 3 195 | 53.0% |
| Module = Maven + `-<other suffix>` (build label, patch tag) | 12 | 0.2% |
| Maven = Module + `-<suffix>` (repackager appended a coordinate suffix) | 174 | 2.9% |
| Module = Maven + `.<segment>` (extra dot-segment in module-info) | 4 | 0.1% |
| Maven = Module + `.<segment>` (extra dot-segment in coordinate) | 21 | 0.3% |
| Module = Maven + `+<metadata>` (build metadata in module-info) | 400 | 6.6% |
| Maven = Module + `+<metadata>` (build metadata in coordinate) | 0 | 0.0% |
| Unresolved `${...}` placeholder in either version | 19 | 0.3% |
| Different major segment (likely shaded/bundled artifact) | 536 | 8.9% |
| Substantively different (same major, different version) | 1 669 | 27.7% |

## Type transitions

Modules that have switched between named and automatic over their history. A module counts toward a direction when its latest version's type differs from at least one earlier version's type.

| Direction | Modules |
|---|---:|
| Automatic → Named | 1 481 |
| Named → Automatic | 129 |

## Recent activity (last 7 days)

Activity in the 7-day window ending at the **most recent tracked publication** (shown in Totals), not at this file's generation time. Maven Central's index typically lags real time by up to a week, so a now-relative window is usually empty even when the crawl is fully caught up; anchoring to the freshest publication keeps the window meaningful. "Modules with a publication" counts distinct module names that received at least one new version; "new version rows" is the total count of those publications. Per-row counts split by the publication's own type; per-module counts attribute each module to whichever type it has at its latest version, so a module that switched named↔automatic shows up under its current type. The `Named`/`Automatic` columns are canonical (owner-resolved); the trailing `Non-modular artifacts` row counts distinct `(groupId, artifactId)` that published a coordinate with no module identity (distinct scanned artifacts minus distinct modular artifacts in the window), so it stands apart from the modular rows rather than summing into them.

| Metric | Total | Named | Automatic |
|---|---:|---:|---:|
| Modules with a publication | 2 433 | 605 | 1 828 |
| New version rows | 3 749 | 1 004 | 2 745 |
| Non-modular artifacts | 18 965 | - | - |

## Monthly publications by type (last 12 months)

Per-month counts of **distinct entities** that published in the month. `Named`/`Automatic` count distinct canonical (owner-resolved) module names by type; `Non-modular artifacts` counts distinct `(groupId, artifactId)` that published a coordinate carrying no module identity (distinct scanned artifacts minus distinct modular artifacts in the month). All three share one bar scale. Non-modular artifacts outnumber modules roughly 10:1, so the columns use different shades to stay legible at a glance: `█` named, `▓` automatic, `░` non-modular. The `(x%)` after each count is that type's share of the month's total (named + automatic + non-modular), so a row's three percentages sum to ~100%.

| Month | Named modules | Automatic modules | Non-modular artifacts |
|---|---|---|---|
| 2025-06 | `█`&nbsp;2 719 (4.4%) | `▓▓`&nbsp;5 701 (9.2%) | `░░░░░░░░░░░░░░░░░░`&nbsp;53 513 (86.4%) |
| 2025-07 | `█`&nbsp;2 690 (4.6%) | `▓▓`&nbsp;4 697 (8.1%) | `░░░░░░░░░░░░░░░░░░`&nbsp;50 811 (87.3%) |
| 2025-08 | `█`&nbsp;2 374 (4.0%) | `▓▓`&nbsp;5 276 (9.0%) | `░░░░░░░░░░░░░░░░░░`&nbsp;50 975 (87.0%) |
| 2025-09 | `█`&nbsp;2 769 (4.3%) | `▓▓`&nbsp;5 238 (8.1%) | `░░░░░░░░░░░░░░░░░░░░`&nbsp;56 434 (87.6%) |
| 2025-10 | `█`&nbsp;2 791 (4.1%) | `▓▓`&nbsp;5 252 (7.7%) | `░░░░░░░░░░░░░░░░░░░░░`&nbsp;59 866 (88.2%) |
| 2025-11 | `█`&nbsp;2 368 (3.7%) | `▓▓`&nbsp;5 109 (7.9%) | `░░░░░░░░░░░░░░░░░░░░`&nbsp;57 165 (88.4%) |
| 2025-12 | `█`&nbsp;2 619 (4.0%) | `▓▓`&nbsp;5 448 (8.4%) | `░░░░░░░░░░░░░░░░░░░░`&nbsp;57 162 (87.6%) |
| 2026-01 | `█`&nbsp;2 855 (4.1%) | `▓▓`&nbsp;5 310 (7.6%) | `░░░░░░░░░░░░░░░░░░░░░`&nbsp;61 373 (88.3%) |
| 2026-02 | `█`&nbsp;2 628 (3.8%) | `▓▓`&nbsp;5 466 (7.9%) | `░░░░░░░░░░░░░░░░░░░░░`&nbsp;61 039 (88.3%) |
| 2026-03 | `█`&nbsp;3 029 (3.8%) | `▓▓`&nbsp;6 522 (8.3%) | `░░░░░░░░░░░░░░░░░░░░░░░░`&nbsp;69 454 (87.9%) |
| 2026-04 | `█`&nbsp;3 351 (4.4%) | `▓▓`&nbsp;5 727 (7.5%) | `░░░░░░░░░░░░░░░░░░░░░░░`&nbsp;66 892 (88.1%) |
| 2026-05 | `█`&nbsp;2 232 (4.1%) | `▓`&nbsp;3 699 (6.9%) | `░░░░░░░░░░░░░░░░░`&nbsp;48 051 (89.0%) |

## Naming patterns

How module names relate to their publishing groupId and to classifier-bundled JARs. "Classifier variants" are non-main artifacts like `-jar-with-dependencies` or `-uber` that also produce a module; "competing groupIds" counts modules whose name has been published under more than one groupId across history (i.e. collisions).

| Pattern | Modules |
|---|---:|
| Has classifier variants | 2 002 |
| Total classifier variants (across all modules) | 3 984 |
| Multiple competing groupIds in audit history | 3 939 |

### Leading dot-segments shared with the owning groupId

For each canonical (no-classifier) module that resolved to an owner (implicit or explicit), counts how many leading dot-segments its module name shares with the owner's groupId. A high share is the textbook JPMS pattern (e.g. module `com.example.foo` published by groupId `com.example.foo`); zero indicates a module name that diverges entirely from its publisher's groupId. Classifier variants are out of scope because they share the canonical's groupId by construction. Empty buckets render as `-`.

| Shared leading dot-segments | Canonical modules |
|---:|---:|
| 0 | 8 492 |
| 1 | 1 006 |
| 2 | 11 466 |
| 3 | 11 510 |
| 4 | 2 312 |
| 5 | 335 |
| 6 | 13 |
| 7 | - |
| 8 | 2 |

## Processing errors

Recorded permanent failures across every scanned coordinate. Variable bits of well-known error classes (URLs, shaded package names, classfile entry indexes, HTTP status codes, line numbers, class identifiers) are replaced with placeholders like `<URL>`, `<PACKAGE>`, `<CLASS>` so messages that differ only in those bits aggregate into one row.

`Incorrectly indexed` is the dominant failure class: coordinates the upstream Nexus index mis-stamps as having a main JAR when only a POM was ever published (BOMs, parent POMs). The crawler probes once, the fetch 404s, and the row is recorded permanently; these are an upstream-data artifact, not a problem with the JAR, and are excluded from the `Total artifacts scanned` and `Modular artifacts` totals above. `Genuine artifact errors` is the remainder - malformed JARs, unparseable `module-info`, and the like - and is broken out in the top-N table below.

| Metric | Value |
|---|---:|
| Total failed coordinates | 2 141 096 |
| Incorrectly indexed (mis-stamped 404s) | 2 138 915 |
| Genuine artifact errors | 2 181 |

### Top 25 genuine error messages

Excludes the mis-stamped-404 class broken out above, so the genuine artifact errors are visible rather than buried beneath it.

| Error message | Count |
|---|---:|
| `IllegalArgumentException: End of central directory record not found in supplied tail buffer` | 590 |
| `InvalidModuleDescriptorException: Package <PACKAGE> missing from ModulePackages class file attribute` | 468 |
| `IllegalArgumentException: Illegal character in path at index <INDEX>: <PATH>` | 271 |
| `InvalidModuleDescriptorException: this_class should be module-info` | 246 |
| `InvalidModuleDescriptorException: <CLASS>: unnamed package` | 156 |
| `InvalidModuleDescriptorException: Unsupported major.minor version <VERSION>` | 92 |
| `InvalidModuleDescriptorException: CONSTANT_Package at entry <ENTRY> has illegal character: '.'` | 79 |
| `IllegalArgumentException: Expected central file header signature at offset <OFFSET>` | 76 |
| `InvalidModuleDescriptorException: <CLASS>: Invalid service type name: '<NAME>' is not a Java identifier` | 45 |
| `InvalidModuleDescriptorException: Index into constant pool out of range` | 34 |
| `IllegalArgumentException: Expected ZIP64 end of central directory signature at offset <OFFSET>` | 24 |
| `InvalidModuleDescriptorException: <CLASS>: is not a qualified name of a Java class in a named package` | 23 |
| `InvalidModuleDescriptorException: Dependence on self` | 19 |
| `IOException: invalid header field (line <LINE>)` | 17 |
| `InvalidModuleDescriptorException: The requires entry for java.base has ACC_SYNTHETIC set` | 15 |
| `InvalidModuleDescriptorException: Dependence upon java.base already declared` | 6 |
| `InvalidModuleDescriptorException: Attribute ModuleTarget does not match its expected length` | 4 |
| `InvalidModuleDescriptorException: The requires table must have an entry for java.base` | 4 |
| `InvalidModuleDescriptorException: Exported package <PACKAGE> already declared` | 3 |
| `InvalidModuleDescriptorException: CONSTANT_Class at entry <ENTRY> has illegal character: ';'` | 2 |
| `InvalidModuleDescriptorException: CONSTANT_Module expected at entry: <ENTRY>` | 2 |
| `InvalidModuleDescriptorException: CONSTANT_Package at entry <ENTRY> has illegal character: ';'` | 2 |
| `InvalidModuleDescriptorException: Dependence upon spring.core already declared` | 2 |
| `IllegalArgumentException: Illegal character in fragment at index <INDEX>: <PATH>` | 1 |

## Top 25 modules by version count

Modules with the longest release history. Counts come from the main (no-classifier) view, so a classifier variant like `-jar-with-dependencies` does not inflate the number. Module families that would otherwise occupy many adjacent slots are folded into a single `<prefix>.*` row; the cell is rendered as `[min, max]` when the absorbed modules have different counts, and a trailing `(N modules)` notes how many were absorbed.

| Module | Versions |
|---|---:|
| `software.amazon.awssdk.* (500 modules)` | [1, 1 750] |
| `org.scala.lang.scala3.* (8 modules)` | [670, 1 230] |
| `org.scala.lang.* (excl. scala3) (4 modules)` | [354, 1 211] |
| `com.graphqljava` | 1 157 |
| `net.minestom.server` | 742 |
| `com.google.api.services.* (338 modules)` | [1, 544] |
| `com.azure.sdk.template` | 461 |
| `com.hazelcast.all` | 446 |
| `io.undertow.parser.generator` | 446 |
| `javassist` | 446 |
| `org.apache.commons.math3` | 446 |
| `org.apache.cxf` | 446 |
| `org.apache.poi.ooxml` | 446 |
| `org.apache.xmlbeans` | 446 |
| `undertow.* (3 modules)` | [415, 446] |
| `com.guicedee.* (64 modules)` | [1, 445] |
| `dom4j` | 437 |
| `jakarta.enterprise.* (2 modules)` | 437 |
| `jaxen` | 433 |
| `jandex` | 431 |
| `primefaces` | 415 |
| `com.tigerbeetle` | 404 |
| `org.apache.poi` | 403 |
| `be.seeseemelk.mockbukkit` | 394 |
| `com.entityassist` | 394 |

## Top 25 groupIds by module count

GroupIds that publish the most distinct module names, sorted by module count. Each `(groupId, moduleName)` pair counts once regardless of how many versions or classifier variants exist.

| groupId | Modules published |
|---|---:|
| `org.bytedeco` | 673 |
| `software.amazon.awssdk` | 505 |
| `org.lwjgl` | 492 |
| `org.apereo.cas` | 484 |
| `io.github.shuigedeng` | 348 |
| `org.springframework.boot` | 347 |
| `com.google.apis` | 338 |
| `io.opentelemetry.javaagent.instrumentation` | 317 |
| `org.kie` | 281 |
| `org.eclipse.platform` | 256 |
| `com.azure.resourcemanager` | 255 |
| `com.gitee.zodiacstack` | 211 |
| `io.micronaut.oraclecloud` | 210 |
| `org.neo4j` | 210 |
| `com.blazebit` | 209 |
| `com.aoapps` | 189 |
| `io.github.bluetape4k` | 187 |
| `io.netty` | 186 |
| `org.drools` | 185 |
| `com.helger` | 183 |
| `org.kie.kogito` | 183 |
| `org.ogc-schemas` | 175 |
| `net.solarnetwork.node` | 169 |
| `com.azure` | 155 |
| `io.micronaut` | 154 |

## Top 25 modules with most colliding groupIds

Module names that have been published under the most different groupIds across history. A high count indicates name reuse: forks, rebranded artifacts, or coordinate moves whose historical declarations remain on record even after the canonical publisher is set.

| Module | Distinct groupIds |
|---|---:|
| `com.google.gson` | 294 |
| `com.fasterxml.jackson.databind` | 249 |
| `com.fasterxml.jackson.core` | 232 |
| `com.fasterxml.jackson.annotation [-jar-with-dependencies]` | 222 |
| `com.fasterxml.jackson.annotation` | 181 |
| `org.apache.logging.log4j` | 178 |
| `org.slf4j` | 171 |
| `kotlin.stdlib` | 141 |
| `kotlin.stdlib.jdk8` | 133 |
| `com.google.gson [-jar-with-dependencies]` | 129 |
| `com.fasterxml.jackson.databind [-jar-with-dependencies]` | 110 |
| `org.apache.logging.log4j [-jar-with-dependencies]` | 90 |
| `java.xml.bind [-jar-with-dependencies]` | 89 |
| `java.xml.bind` | 87 |
| `net.bytebuddy` | 84 |
| `org.objectweb.asm [-jar-with-dependencies]` | 79 |
| `kotlin.stdlib.jdk8 [-all]` | 76 |
| `com.fasterxml.jackson.core [-jar-with-dependencies]` | 74 |
| `com.google.gson [-all]` | 71 |
| `org.apache.commons.lang3` | 66 |
| `com.fasterxml.jackson.module.jaxb` | 65 |
| `info.picocli` | 64 |
| `org.objectweb.asm` | 63 |
| `org.apache.commons.io` | 62 |
| `org.apache.commons.codec` | 61 |

## Top 25 modules updated in the last 7 days

Modules whose most recent publication landed in the 7-day window ending at the most recent tracked publication (same window as `Recent activity`, anchored to the freshest publication rather than now since the index lags up to a week), sorted newest first. Use this as a recency view; the count above (`Recent activity`) gives the totals while this table names which modules they were.

| Module | Last publication |
|---|---|
| `agents.features.persistence.jdbc.jvm` | 2026-05-20 21:16:53 UTC |
| `agents.test.jvm` | 2026-05-20 21:16:53 UTC |
| `koog.spring.ai.starter.model.embedding.jvm` | 2026-05-20 21:16:53 UTC |
| `prompt.executor.llms.all.jvm` | 2026-05-20 21:16:53 UTC |
| `a2a.client.jvm` | 2026-05-20 21:16:52 UTC |
| `agents.features.snapshot.jvm` | 2026-05-20 21:16:52 UTC |
| `prompt.executor.dashscope.client.jvm` | 2026-05-20 21:16:52 UTC |
| `prompt.executor.ollama.client.jvm` | 2026-05-20 21:16:52 UTC |
| `prompt.model.jvm` | 2026-05-20 21:16:52 UTC |
| `http.client.ktor.jvm` | 2026-05-20 21:16:51 UTC |
| `prompt.cache.files.jvm` | 2026-05-20 21:16:51 UTC |
| `agents.mcp.jvm` | 2026-05-20 21:16:50 UTC |
| `koog.spring.ai.starter.chat.memory.jvm` | 2026-05-20 21:16:50 UTC |
| `prompt.executor.model.jvm` | 2026-05-20 21:16:50 UTC |
| `prompt.llm.jvm` | 2026-05-20 21:16:50 UTC |
| `http.client.core.jvm` | 2026-05-20 21:16:49 UTC |
| `prompt.processor.jvm` | 2026-05-20 21:16:49 UTC |
| `koog.spring.ai.starter.model.chat.jvm` | 2026-05-20 21:16:48 UTC |
| `prompt.executor.mistralai.client.jvm` | 2026-05-20 21:16:48 UTC |
| `prompt.tokenizer.jvm` | 2026-05-20 21:16:48 UTC |
| `a2a.transport.server.jsonrpc.http.jvm` | 2026-05-20 21:16:46 UTC |
| `agents.core.jvm` | 2026-05-20 21:16:45 UTC |
| `koog.spring.ai.common.jvm` | 2026-05-20 21:16:45 UTC |
| `prompt.executor.openai.client.jvm` | 2026-05-20 21:16:45 UTC |
| `agents.features.event.handler.jvm` | 2026-05-20 21:16:44 UTC |

## Top 25 groupIds by average versions per module

Restricted to groupIds publishing at least 3 modules so the average isn't dominated by single-module outliers.

| groupId | Modules | Total versions | Avg versions / module |
|---|---:|---:|---:|
| `software.amazon.awssdk` | 505 | 595 974 | 1180.1 |
| `org.scala-lang` | 18 | 12 600 | 700.0 |
| `io.prophecy` | 3 | 1 729 | 576.3 |
| `com.graphql-java` | 3 | 1 321 | 440.3 |
| `com.guicedee.servlets` | 10 | 3 473 | 347.3 |
| `com.jwebmp.plugins.bootstrap` | 6 | 1 804 | 300.7 |
| `com.guicedee.persistence` | 9 | 2 377 | 264.1 |
| `net.minestom` | 3 | 771 | 257.0 |
| `com.guicedee.services` | 51 | 12 908 | 253.1 |
| `io.fluxzero` | 5 | 1 256 | 251.2 |
| `com.jwebmp.plugins.security` | 3 | 707 | 235.7 |
| `systems.manifold` | 41 | 8 970 | 218.8 |
| `com.lihaoyi` | 4 | 819 | 204.8 |
| `net.serenity-bdd` | 27 | 5 238 | 194.0 |
| `org.springframework` | 23 | 4 243 | 184.5 |
| `org.springframework.security` | 22 | 3 972 | 180.5 |
| `org.apache.tomcat.embed` | 5 | 842 | 168.4 |
| `org.springframework.data` | 24 | 3 944 | 164.3 |
| `org.neo4j.community` | 17 | 2 772 | 163.1 |
| `org.joinfaces` | 30 | 4 797 | 159.9 |
| `com.netflix.spectator` | 29 | 4 566 | 157.4 |
| `com.apihug` | 28 | 4 231 | 151.1 |
| `com.opengamma.strata` | 11 | 1 628 | 148.0 |
| `io.evitadb` | 24 | 3 528 | 147.0 |
| `org.talend.sdk.component` | 67 | 9 802 | 146.3 |

_This file is regenerated on every `ModuleSummary` run; previous content is discarded._
