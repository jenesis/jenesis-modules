# Module summary

> ### Powered by [Jenesis](https://github.com/raphw/jenesis)
> _A modern Java build tool: Java-native config, plugin-free, with `module-info.java` treated as a feature, not an afterthought._

_Index timestamp: 2026-06-02 09:59:33 UTC_  
_Current chunk started: 2026-06-02 13:39:00 UTC_  
_Index chain id: `1318453614498`_  
_Last applied index chunk: 929_  

## Top artifacts by year

Real-world Java projects lean on a fairly small set of widely-shared libraries, while the catalogue as a whole carries a very long tail of artifacts that almost nothing depends on. Adoption measured across that whole tail understates what most projects actually encounter. The reports below instead rank the most depended-on artifacts and show how many of them ship a module, which gives a clearer view of module adoption where it matters and how it has moved over time. The bleeding-edge report assesses the latest list against current data, uncropped.

[2019](top/2019.md) · [2020](top/2020.md) · [2021](top/2021.md) · [2022](top/2022.md) · [2023](top/2023.md) · [2024](top/2024.md) · [bleeding edge](top/BLEEDING.md)

## Totals

Catalogue-wide counts. Unless a section is explicitly labelled as "audit" or "history", every row-level number here and below describes the canonical view of the catalogue: shaded or otherwise non-authoritative claims on a module name do not contribute. "Artifacts" counts JARs (one row per groupId/artifactId/version/classifier coordinate); "modules" counts the named or automatic-module identities those JARs expose. Distinct counts deduplicate by name. "With module-info version" means the module declared a non-empty version in its `module-info`, whether or not it matches the Maven coordinate version.

| Metric | Value |
|---|---:|
| Total artifacts scanned | 17 368 273 |
| Non-module artifacts | 15 459 649 |
| Modular artifacts | 1 582 519 |
| Total automatic modules | 1 242 223 |
| Total named modules | 340 296 |
| Total named modules with module-info version | 256 696 |
| Distinct Maven artifacts | 653 652 |
| Distinct module names | 39 449 |
| Distinct automatic modules | 20 968 |
| Distinct named modules | 17 100 |
| Distinct named modules with module-info version | 12 548 |
| Distinct groupIds publishing modules | 5 161 |
| Most recent tracked publication | 2026-06-10 23:52:31 UTC |

## Resolved catalogue size

Across every `modules[-classifier].tsv` under `data/modules/`, the resolved view holds **325 853** distinct module-version rows. Each row is one (module name, classifier, `module-info` version) combination that survived owner resolution; rows whose `module-info` version contradicts the Maven version are excluded by the resolution policy.

## Type breakdown

Named vs automatic counts. Distinct-module counts use the **latest** version's type, so a module that started automatic and is currently named counts as named. Row counts include every classifier variant.

| Type | Distinct modules | Published rows |
|---|---:|---:|
| Named | 17 100 | 340 296 |
| Automatic | 20 968 | 1 242 223 |

## `module-info` version field across named publications

Every table in this section is scoped to **canonical (no-classifier) named publications**. Classifier-keyed rows (mostly fat-jar / shaded variants that bundle another module under their own Maven coordinate) are excluded, because the bundled module's `module-info` version is expected to contradict the bundling Maven version, which would otherwise overwhelm the signal here.

Counts canonical **named publications** (one count per published JAR, not per distinct module) by how the JAR's `module-info` fills its optional version attribute. Automatic JARs are excluded; they carry no `module-info`. The three rows are mutually exclusive and cover every canonical named publication in the catalogue. The breakdown table below classifies the `mismatching` bucket by *why* the two versions differ.

| Publication category | Publications |
|---|---:|
| `module-info` version matches the Maven coordinate version | 250 057 |
| `module-info` version is non-empty but differs from the Maven coordinate version | 6 639 |
| `module-info` declared no version (Maven coordinate version is the only reference) | 52 643 |

Same breakdown but counted once per **canonical module**, against the latest named row in its no-classifier resolved view (the row a consumer fetching the "latest" of a module would land on). Modules whose latest row is automatic are excluded.

| Module category (by latest canonical named row) | Modules |
|---|---:|
| `module-info` version matches the Maven coordinate version | 11 021 |
| `module-info` version is non-empty but differs from the Maven coordinate version | 327 |
| `module-info` declared no version (Maven coordinate version is the only reference) | 3 964 |

Each row describes what the **version-mismatch filter** (drop every named row whose `module-info` version semantically contradicts its Maven coordinate version) leaves behind in the module's `modules.tsv`, counted once per **canonical module** (no-classifier view). Modules with no canonical named row are out of scope. The first row is the in-scope total; rows two through four are mutually exclusive and sum to it; the fifth row overlaps with rows three and four (it's the subset whose head-of-`modules.tsv` is the one the filter removes).

| Module version filtering impact | Module names |
|---|---:|
| Canonical modules with at least one named row (in scope) | 15 458 |
| Filter keeps every named row: `modules.tsv` is unchanged | 14 785 |
| Filter drops some named rows but at least one survives: `modules.tsv` shrinks | 534 |
| Filter drops every named row: `modules.tsv` is removed entirely | 139 |
| Filter drops the module's current top row: "latest" shifts to an older Maven version (or vanishes if fully lost) | 327 |

## Mismatching module-info version patterns

Breaks down the publications whose `module-info` version differs from the Maven coordinate version (the middle row of the previous table) by *why* they differ. The first several rows are formatting drift (publisher forgot to drop a `-SNAPSHOT`, a repackager's coordinate suffix, build-metadata `+` labels, extra dot-segments); `Unresolved placeholder` is a build-time `${...}` substitution that leaked through; `Different major segment` is a strong proxy for shaded/bundled artifacts whose `module-info` comes from a different versioning lineage; `Substantively different` is the remainder where the versions share a first segment but otherwise differ. Percentages are share of the differing-version bucket.

| Pattern | Rows | Share |
|---|---:|---:|
| Module = Maven + `-SNAPSHOT` (release that forgot to drop SNAPSHOT) | 3 199 | 48.2% |
| Module = Maven + `-<other suffix>` (build label, patch tag) | 12 | 0.2% |
| Maven = Module + `-<suffix>` (repackager appended a coordinate suffix) | 201 | 3.0% |
| Module = Maven + `.<segment>` (extra dot-segment in module-info) | 4 | 0.1% |
| Maven = Module + `.<segment>` (extra dot-segment in coordinate) | 21 | 0.3% |
| Module = Maven + `+<metadata>` (build metadata in module-info) | 0 | 0.0% |
| Maven = Module + `+<metadata>` (build metadata in coordinate) | 0 | 0.0% |
| Unresolved `${...}` placeholder in either version | 40 | 0.6% |
| Different major segment (likely shaded/bundled artifact) | 1 127 | 17.0% |
| Substantively different (same major, different version) | 2 035 | 30.7% |

## Type transitions

Modules that have switched between named and automatic over their history. A module counts toward a direction when its latest version's type differs from at least one earlier version's type.

| Direction | Modules |
|---|---:|
| Automatic → Named | 1 530 |
| Named → Automatic | 147 |

## Recent activity (last 7 days)

Activity in the 7-day window ending at the **most recent tracked publication** (shown in Totals), not at this file's generation time. Maven Central's index typically lags real time by up to a week, so a now-relative window is usually empty even when the crawl is fully caught up; anchoring to the freshest publication keeps the window meaningful. "Modules with a publication" counts distinct module names that received at least one new version; "new version rows" is the total count of those publications. Per-row counts split by the publication's own type; per-module counts attribute each module to whichever type it has at its latest version, so a module that switched named↔automatic shows up under its current type. The `Named`/`Automatic` columns are canonical (owner-resolved); the trailing `Non-modular artifacts` row counts distinct `(groupId, artifactId)` that published a coordinate with no module identity (distinct scanned artifacts minus distinct modular artifacts in the window), so it stands apart from the modular rows rather than summing into them.

| Metric | Total | Named | Automatic |
|---|---:|---:|---:|
| Modules with a publication | 1 | 1 | 0 |
| New version rows | 1 | 1 | 0 |
| Non-modular artifacts | 0 | - | - |

## Monthly publications by type (last 12 months)

Per-month counts of **distinct entities** that published in the month. `Named`/`Automatic` count distinct canonical (owner-resolved) module names by type; `Non-modular artifacts` counts distinct `(groupId, artifactId)` that published a coordinate carrying no module identity (distinct scanned artifacts minus distinct modular artifacts in the month). All three share one bar scale. Non-modular artifacts outnumber modules roughly 10:1, so the columns use different shades to stay legible at a glance: `█` named, `▓` automatic, `░` non-modular. The `(x%)` after each count is that type's share of the month's total (named + automatic + non-modular), so a row's three percentages sum to ~100%.

| Month | Named modules | Automatic modules | Non-modular artifacts |
|---|---|---|---|
| 2025-07 | `█`&nbsp;2 786 (4.8%) | `▓▓`&nbsp;4 768 (8.2%) | `░░░░░░░░░░░░░░░░░░`&nbsp;50 813 (87.1%) |
| 2025-08 | `█`&nbsp;2 433 (4.1%) | `▓▓`&nbsp;5 453 (9.3%) | `░░░░░░░░░░░░░░░░░░`&nbsp;50 975 (86.6%) |
| 2025-09 | `█`&nbsp;2 802 (4.3%) | `▓▓`&nbsp;5 403 (8.4%) | `░░░░░░░░░░░░░░░░░░░░`&nbsp;56 435 (87.3%) |
| 2025-10 | `█`&nbsp;2 844 (4.2%) | `▓▓`&nbsp;5 390 (7.9%) | `░░░░░░░░░░░░░░░░░░░░░`&nbsp;59 866 (87.9%) |
| 2025-11 | `█`&nbsp;2 427 (3.7%) | `▓▓`&nbsp;5 392 (8.3%) | `░░░░░░░░░░░░░░░░░░░░`&nbsp;57 166 (88.0%) |
| 2025-12 | `█`&nbsp;2 649 (4.0%) | `▓▓`&nbsp;5 626 (8.6%) | `░░░░░░░░░░░░░░░░░░░░`&nbsp;57 162 (87.4%) |
| 2026-01 | `█`&nbsp;2 891 (4.1%) | `▓▓`&nbsp;5 498 (7.9%) | `░░░░░░░░░░░░░░░░░░░░░`&nbsp;61 373 (88.0%) |
| 2026-02 | `█`&nbsp;2 638 (3.8%) | `▓▓`&nbsp;5 524 (8.0%) | `░░░░░░░░░░░░░░░░░░░░░`&nbsp;61 040 (88.2%) |
| 2026-03 | `█`&nbsp;3 113 (3.9%) | `▓▓`&nbsp;6 696 (8.4%) | `░░░░░░░░░░░░░░░░░░░░░░░░`&nbsp;69 458 (87.6%) |
| 2026-04 | `█`&nbsp;3 397 (4.5%) | `▓▓`&nbsp;5 844 (7.7%) | `░░░░░░░░░░░░░░░░░░░░░░░`&nbsp;66 955 (87.9%) |
| 2026-05 | `█`&nbsp;3 412 (4.5%) | `▓▓`&nbsp;5 346 (7.1%) | `░░░░░░░░░░░░░░░░░░░░░░░`&nbsp;66 517 (88.4%) |
| 2026-06 | `█`&nbsp;73 (3.5%) | `▓`&nbsp;21 (1.0%) | `░`&nbsp;2 015 (95.5%) |

## Naming patterns

How module names relate to their publishing groupId and to classifier-bundled JARs. "Classifier variants" are non-main artifacts like `-jar-with-dependencies` or `-uber` that also produce a module; "competing groupIds" counts modules whose name has been published under more than one groupId across history (i.e. collisions).

| Pattern | Modules |
|---|---:|
| Has classifier variants | 2 006 |
| Total classifier variants (across all modules) | 3 993 |
| Multiple competing groupIds in audit history | 3 956 |

### Leading dot-segments shared with the owning groupId

For each canonical (no-classifier) module that resolved to an owner (implicit or explicit), counts how many leading dot-segments its module name shares with the owner's groupId. A high share is the textbook JPMS pattern (e.g. module `com.example.foo` published by groupId `com.example.foo`); zero indicates a module name that diverges entirely from its publisher's groupId. Classifier variants are out of scope because they share the canonical's groupId by construction. Empty buckets render as `-`.

| Shared leading dot-segments | Canonical modules |
|---:|---:|
| 0 | 8 415 |
| 1 | 1 052 |
| 2 | 11 333 |
| 3 | 11 959 |
| 4 | 2 319 |
| 5 | 329 |
| 6 | 13 |
| 7 | - |
| 8 | 2 |

## Processing errors

Recorded permanent failures across every scanned coordinate. Variable bits of well-known error classes (URLs, shaded package names, classfile entry indexes, HTTP status codes, line numbers, class identifiers) are replaced with placeholders like `<URL>`, `<PACKAGE>`, `<CLASS>` so messages that differ only in those bits aggregate into one row.

`Incorrectly indexed` is the dominant failure class: coordinates the upstream Nexus index mis-stamps as having a main JAR when only a POM was ever published (BOMs, parent POMs). The crawler probes once, the fetch 404s, and the row is recorded permanently; these are an upstream-data artifact, not a problem with the JAR, and are excluded from the `Total artifacts scanned` and `Modular artifacts` totals above. `Genuine artifact errors` is the remainder - malformed JARs, unparseable `module-info`, and the like - and is broken out in the top-N table below.

| Metric | Value |
|---|---:|
| Total failed coordinates | 2 180 901 |
| Incorrectly indexed (mis-stamped 404s) | 2 178 714 |
| Genuine artifact errors | 2 187 |

### Top 25 genuine error messages

Excludes the mis-stamped-404 class broken out above, so the genuine artifact errors are visible rather than buried beneath it.

| Error message | Count |
|---|---:|
| `IllegalArgumentException: End of central directory record not found in supplied tail buffer` | 590 |
| `InvalidModuleDescriptorException: Package <PACKAGE> missing from ModulePackages class file attribute` | 473 |
| `IllegalArgumentException: Illegal character in path at index <INDEX>: <PATH>` | 271 |
| `InvalidModuleDescriptorException: this_class should be module-info` | 246 |
| `InvalidModuleDescriptorException: <CLASS>: unnamed package` | 156 |
| `InvalidModuleDescriptorException: Unsupported major.minor version <VERSION>` | 92 |
| `InvalidModuleDescriptorException: CONSTANT_Package at entry <ENTRY> has illegal character: '.'` | 80 |
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
| `software.amazon.awssdk.* (500 modules)` | [1, 1 757] |
| `org.scala.lang.scala3.* (8 modules)` | [671, 1 231] |
| `org.scala.lang.* (excl. scala3) (4 modules)` | [354, 1 211] |
| `com.graphqljava` | 1 167 |
| `net.minestom.server` | 742 |
| `com.google.api.services.* (339 modules)` | [1, 544] |
| `com.azure.sdk.template` | 462 |
| `com.hazelcast.all` | 452 |
| `javassist` | 452 |
| `org.apache.commons.math3` | 452 |
| `org.apache.cxf` | 452 |
| `io.undertow.parser.generator` | 446 |
| `undertow.* (3 modules)` | [415, 446] |
| `com.guicedee.* (64 modules)` | [1, 445] |
| `java.transaction` | 442 |
| `jandex` | 438 |
| `dom4j` | 437 |
| `jakarta.enterprise.* (2 modules)` | 437 |
| `tm.bitronix.btm` | 434 |
| `jaxen` | 433 |
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
| `software.amazon.awssdk` | 503 |
| `org.lwjgl` | 492 |
| `org.apereo.cas` | 485 |
| `io.github.shuigedeng` | 347 |
| `org.springframework.boot` | 347 |
| `com.google.apis` | 339 |
| `io.opentelemetry.javaagent.instrumentation` | 330 |
| `org.kie` | 280 |
| `org.eclipse.platform` | 257 |
| `com.azure.resourcemanager` | 256 |
| `com.gitee.zodiacstack` | 211 |
| `io.micronaut.oraclecloud` | 210 |
| `org.neo4j` | 210 |
| `com.blazebit` | 209 |
| `com.aoapps` | 189 |
| `io.github.bluetape4k` | 188 |
| `io.netty` | 186 |
| `org.drools` | 185 |
| `com.helger` | 182 |
| `org.kie.kogito` | 181 |
| `org.ogc-schemas` | 176 |
| `net.solarnetwork.node` | 169 |
| `com.azure` | 155 |
| `io.micronaut` | 154 |

## Top 25 modules with most colliding groupIds

Module names that have been published under the most different groupIds across history. A high count indicates name reuse: forks, rebranded artifacts, or coordinate moves whose historical declarations remain on record even after the canonical publisher is set.

| Module | Distinct groupIds |
|---|---:|
| `com.google.gson` | 295 |
| `com.fasterxml.jackson.databind` | 250 |
| `com.fasterxml.jackson.core` | 232 |
| `com.fasterxml.jackson.annotation [-jar-with-dependencies]` | 224 |
| `com.fasterxml.jackson.annotation` | 182 |
| `org.apache.logging.log4j` | 178 |
| `org.slf4j` | 172 |
| `kotlin.stdlib` | 143 |
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
| `org.apache.commons.lang3` | 67 |
| `com.fasterxml.jackson.module.jaxb` | 65 |
| `info.picocli` | 65 |
| `org.objectweb.asm` | 63 |
| `org.apache.commons.codec` | 62 |
| `org.apache.commons.io` | 62 |

## Top 25 modules updated in the last 7 days

Modules whose most recent publication landed in the 7-day window ending at the most recent tracked publication (same window as `Recent activity`, anchored to the freshest publication rather than now since the index lags up to a week), sorted newest first. Use this as a recency view; the count above (`Recent activity`) gives the totals while this table names which modules they were.

| Module | Last publication |
|---|---|
| `build.jenesis.launcher` | 2026-06-10 23:52:31 UTC |

## Top 25 groupIds by average versions per module

Restricted to groupIds publishing at least 3 modules so the average isn't dominated by single-module outliers.

| groupId | Modules | Total versions | Avg versions / module |
|---|---:|---:|---:|
| `software.amazon.awssdk` | 503 | 598 468 | 1189.8 |
| `org.scala-lang` | 18 | 12 603 | 700.2 |
| `com.graphql-java` | 3 | 1 331 | 443.7 |
| `com.yahoo.vespa` | 3 | 1 215 | 405.0 |
| `com.guicedee.servlets` | 11 | 3 517 | 319.7 |
| `com.jwebmp.plugins.angular` | 17 | 5 151 | 303.0 |
| `com.jwebmp.plugins.effects` | 3 | 909 | 303.0 |
| `com.jwebmp.plugins.iconsets` | 7 | 2 121 | 303.0 |
| `com.jwebmp.plugins.javascript` | 12 | 3 632 | 302.7 |
| `com.jwebmp.plugins.bootstrap` | 8 | 2 409 | 301.1 |
| `com.guicedee.services` | 50 | 13 860 | 277.2 |
| `io.fluxzero` | 5 | 1 356 | 271.2 |
| `com.guicedee.persistence` | 10 | 2 694 | 269.4 |
| `net.minestom` | 3 | 771 | 257.0 |
| `com.jwebmp.plugins.jquery` | 17 | 4 350 | 255.9 |
| `com.jwebmp.plugins.graphing` | 5 | 1 247 | 249.4 |
| `com.jwebmp.plugins.forms` | 10 | 2 436 | 243.6 |
| `com.jwebmp.plugins.security` | 3 | 707 | 235.7 |
| `systems.manifold` | 41 | 8 970 | 218.8 |
| `com.jwebmp.core` | 3 | 594 | 198.0 |
| `net.serenity-bdd` | 27 | 5 238 | 194.0 |
| `org.springframework` | 23 | 4 243 | 184.5 |
| `org.hibernate.orm` | 23 | 4 004 | 174.1 |
| `org.apache.tomcat.embed` | 5 | 842 | 168.4 |
| `com.netflix.spectator` | 27 | 4 500 | 166.7 |

_This file is regenerated on every `ModuleSummary` run; previous content is discarded._
