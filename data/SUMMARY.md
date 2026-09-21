# Module summary

> ### [Jenesis](https://jenesis.build) - a modern Java build tool
> _Java-native config, plugin-free, with `module-info.java` treated as a feature, not an afterthought._

_Index timestamp: 2026-09-15 13:15:15 UTC_  
_Current chunk started: 2026-09-21 10:12:14 UTC_  
_Index chain id: `1318453614498`_  
_Last applied index chunk: 938_  

## Top artifacts by year

Real-world Java projects lean on a fairly small set of widely-shared libraries, while the catalogue as a whole carries a very long tail of artifacts that almost nothing depends on. Adoption measured across that whole tail understates what most projects actually encounter. The reports below instead rank the most depended-on artifacts and show how many of them ship a module, which gives a clearer view of module adoption where it matters and how it has moved over time. The bleeding-edge report assesses the latest list against current data, uncropped.

[2019](top/2019.md) · [2020](top/2020.md) · [2021](top/2021.md) · [2022](top/2022.md) · [2023](top/2023.md) · [2024](top/2024.md) · [2025](top/2025.md) · [bleeding edge](top/BLEEDING.md)

## Totals

Catalogue-wide counts. Unless a section is explicitly labelled as "audit" or "history", every row-level number here and below describes the canonical view of the catalogue: shaded or otherwise non-authoritative claims on a module name do not contribute. "Artifacts" counts JARs (one row per groupId/artifactId/version/classifier coordinate); "modules" counts the named or automatic-module identities those JARs expose. Distinct counts deduplicate by name. "With module-info version" means the module declared a non-empty version in its `module-info`, whether or not it matches the Maven coordinate version.

| Metric | Value |
|---|---:|
| Total artifacts scanned | 18 671 959 |
| Non-module artifacts | 16 619 624 |
| Modular artifacts | 1 694 352 |
| Total automatic modules | 1 324 882 |
| Total named modules | 369 470 |
| Total named modules with module-info version | 280 396 |
| Distinct Maven artifacts | 695 271 |
| Distinct module names | 42 426 |
| Distinct automatic modules | 22 445 |
| Distinct named modules | 18 401 |
| Distinct named modules with module-info version | 13 397 |
| Distinct groupIds publishing modules | 5 483 |
| Most recent tracked publication | 2026-09-15 03:11:47 UTC |

## Resolved catalogue size

Across every `modules[-classifier].tsv` under `data/modules/`, the resolved view holds **356 465** distinct module-version rows. Each row is one (module name, classifier, `module-info` version) combination that survived owner resolution; rows whose `module-info` version contradicts the Maven version are excluded by the resolution policy.

Of those, **246** rows (55 distinct values) carry a version key that is not a valid `ModuleDescriptor.Version` - it does not begin with a digit (e.g. a leading `v`, `r`, or `master-`, or stray strings like `@version@`). Such modules still load fine on the module path: a module version is optional metadata that resolution never uses, so the JVM keeps the string as `rawVersion()` and leaves the parsed `version()` empty rather than refusing the module.

### Republished JVM modules

**15** module names that ship inside the JDK itself (`java.*` / `jdk.*` platform modules such as `java.sql`, `jdk.unsupported`, the `java --list-modules` set) have been republished on Maven Central under some coordinate, across **800** publication rows. They are excluded from the resolved module-version space entirely: the JVM always provides these modules, so no Maven artifact can be resolved as one (the platform's own copy is found first on the module path and shadows anything supplied externally). Such names stay in the `versions.tsv` audit log and remain fetchable as plain coordinates via `artifacts.tsv`, but get no `modules.tsv`. Legacy Java EE modules removed from the JDK (JEP 320: `java.xml.bind`, `java.transaction`, ...) are not counted here - they are absent from a modern JVM and resolve normally - and JavaFX (`javafx.*`) is not a JDK module either.

## Type breakdown

Named vs automatic counts. Distinct-module counts use the **latest** version's type, so a module that started automatic and is currently named counts as named. Row counts include every classifier variant.

| Type | Distinct modules | Published rows |
|---|---:|---:|
| Named | 18 401 | 369 469 |
| Automatic | 22 445 | 1 324 882 |

## Classifiers

A classifier-keyed JAR (e.g. `-jakarta`, `-jdk8`, `-jar-with-dependencies`) that exposes a module is tracked as a separate `<moduleName>-<classifier>` identity with its own `versions-<classifier>.tsv` / `artifacts-<classifier>.tsv` / `modules-<classifier>.tsv`. Ownership of a module name spans its whole classifier space (the first groupId to publish the name owns every classifier under it), and the resolver serves a variant at `/module/<name>-<classifier>/...`.

| Metric | Value |
|---|---:|
| Distinct classifiers | 214 |
| Modules with at least one classifier variant | 2 076 |
| Classifier variant files (`versions-<classifier>.tsv`) | 4 177 |
| Classifier-keyed resolved module-version rows | 23 376 |

Top classifiers by resolved module-version rows:

| Classifier | Resolved rows |
|---|---:|
| `linux` | 1 339 |
| `mac` | 1 339 |
| `win` | 1 338 |
| `jdk9` | 1 191 |
| `mac-aarch64` | 812 |
| `linux-x86_64` | 787 |
| `natives-windows` | 648 |
| `windows-x86_64` | 631 |
| `natives-macos` | 622 |
| `natives-linux` | 617 |
| `android` | 608 |
| `ios` | 607 |
| `jar-with-dependencies` | 604 |
| `desktop` | 589 |
| `linux-aarch64` | 559 |

## `module-info` version field across named publications

Every table in this section is scoped to **canonical (no-classifier) named publications**. Classifier-keyed rows (mostly fat-jar / shaded variants that bundle another module under their own Maven coordinate) are excluded, because the bundled module's `module-info` version is expected to contradict the bundling Maven version, which would otherwise overwhelm the signal here.

Counts canonical **named publications** (one count per published JAR, not per distinct module) by how the JAR's `module-info` fills its optional version attribute. Automatic JARs are excluded; they carry no `module-info`. The three rows are mutually exclusive and cover every canonical named publication in the catalogue. The breakdown table below classifies the `mismatching` bucket by *why* the two versions differ.

| Publication category | Publications |
|---|---:|
| `module-info` version matches the Maven coordinate version | 274 738 |
| `module-info` version is non-empty but differs from the Maven coordinate version | 5 658 |
| `module-info` declared no version (Maven coordinate version is the only reference) | 58 328 |

Same breakdown but counted once per **canonical module**, against the latest named row in its no-classifier resolved view (the row a consumer fetching the "latest" of a module would land on). Modules whose latest row is automatic are excluded.

| Module category (by latest canonical named row) | Modules |
|---|---:|
| `module-info` version matches the Maven coordinate version | 12 024 |
| `module-info` version is non-empty but differs from the Maven coordinate version | 209 |
| `module-info` declared no version (Maven coordinate version is the only reference) | 4 411 |

Each row describes what the **version-mismatch filter** (drop every named row whose `module-info` version semantically contradicts its Maven coordinate version) leaves behind in the module's `modules.tsv`, counted once per **canonical module** (no-classifier view). Modules with no canonical named row are out of scope. The first row is the in-scope total; rows two through four are mutually exclusive and sum to it; the fifth row overlaps with rows three and four (it's the subset whose head-of-`modules.tsv` is the one the filter removes).

| Module version filtering impact | Module names |
|---|---:|
| Canonical modules with at least one named row (in scope) | 16 790 |
| Filter keeps every named row: `modules.tsv` is unchanged | 16 171 |
| Filter drops some named rows but at least one survives: `modules.tsv` shrinks | 509 |
| Filter drops every named row: `modules.tsv` is removed entirely | 110 |
| Filter drops the module's current top row: "latest" shifts to an older Maven version (or vanishes if fully lost) | 209 |

## Mismatching module-info version patterns

Breaks down the publications whose `module-info` version differs from the Maven coordinate version (the middle row of the previous table) by *why* they differ. The first several rows are formatting drift (publisher forgot to drop a `-SNAPSHOT`, a repackager's coordinate suffix, build-metadata `+` labels, extra dot-segments); `Unresolved placeholder` is a build-time `${...}` substitution that leaked through; `Different major segment` is a strong proxy for shaded/bundled artifacts whose `module-info` comes from a different versioning lineage; `Substantively different` is the remainder where the versions share a first segment but otherwise differ. Percentages are share of the differing-version bucket.

| Pattern | Rows | Share |
|---|---:|---:|
| Module = Maven + `-SNAPSHOT` (release that forgot to drop SNAPSHOT) | 3 202 | 56.6% |
| Module = Maven + `-<other suffix>` (build label, patch tag) | 12 | 0.2% |
| Maven = Module + `-<suffix>` (repackager appended a coordinate suffix) | 175 | 3.1% |
| Module = Maven + `.<segment>` (extra dot-segment in module-info) | 4 | 0.1% |
| Maven = Module + `.<segment>` (extra dot-segment in coordinate) | 23 | 0.4% |
| Module = Maven + `+<metadata>` (build metadata in module-info) | 0 | 0.0% |
| Maven = Module + `+<metadata>` (build metadata in coordinate) | 0 | 0.0% |
| Unresolved `${...}` placeholder in either version | 19 | 0.3% |
| Different major segment (likely shaded/bundled artifact) | 489 | 8.6% |
| Substantively different (same major, different version) | 1 734 | 30.6% |

## Type transitions

Modules that have switched between named and automatic over their history. A module counts toward a direction when its latest version's type differs from at least one earlier version's type.

| Direction | Modules |
|---|---:|
| Automatic → Named | 1 617 |
| Named → Automatic | 147 |

## Recent activity (last 7 days)

Activity in the 7-day window ending at the **most recent tracked publication** (shown in Totals), not at this file's generation time. Maven Central's index typically lags real time by up to a week, so a now-relative window is usually empty even when the crawl is fully caught up; anchoring to the freshest publication keeps the window meaningful. "Modules with a publication" counts distinct module names that received at least one new version; "new version rows" is the total count of those publications. Per-row counts split by the publication's own type; per-module counts attribute each module to whichever type it has at its latest version, so a module that switched named↔automatic shows up under its current type. The `Named`/`Automatic` columns are canonical (owner-resolved); the trailing `Non-modular artifacts` row counts distinct `(groupId, artifactId)` that published a coordinate with no module identity (distinct scanned artifacts minus distinct modular artifacts in the window), so it stands apart from the modular rows rather than summing into them.

| Metric | Total | Named | Automatic |
|---|---:|---:|---:|
| Modules with a publication | 123 | 110 | 13 |
| New version rows | 143 | 130 | 13 |
| Non-modular artifacts | 0 | - | - |

## Monthly publications by type (last 12 months)

Per-month counts of **distinct entities** that published in the month. `Named`/`Automatic` count distinct canonical (owner-resolved) module names by type; `Non-modular artifacts` counts distinct `(groupId, artifactId)` that published a coordinate carrying no module identity (distinct scanned artifacts minus distinct modular artifacts in the month). All three share one bar scale. Non-modular artifacts outnumber modules roughly 10:1, so the columns use different shades to stay legible at a glance: `█` named, `▓` automatic, `░` non-modular. The `(x%)` after each count is that type's share of the month's total (named + automatic + non-modular), so a row's three percentages sum to ~100%.

| Month | Named modules | Automatic modules | Non-modular artifacts |
|---|---|---|---|
| 2025-10 | `█`&nbsp;2 841 (4.1%) | `▓▓`&nbsp;5 378 (7.9%) | `░░░░░░░░░░░░░░░░░░░░`&nbsp;60 282 (88.0%) |
| 2025-11 | `█`&nbsp;2 438 (3.7%) | `▓▓`&nbsp;5 378 (8.2%) | `░░░░░░░░░░░░░░░░░░░`&nbsp;57 580 (88.0%) |
| 2025-12 | `█`&nbsp;2 639 (4.0%) | `▓▓`&nbsp;5 636 (8.6%) | `░░░░░░░░░░░░░░░░░░░`&nbsp;57 609 (87.4%) |
| 2026-01 | `█`&nbsp;2 871 (4.1%) | `▓▓`&nbsp;5 483 (7.8%) | `░░░░░░░░░░░░░░░░░░░░`&nbsp;61 774 (88.1%) |
| 2026-02 | `█`&nbsp;2 623 (3.8%) | `▓▓`&nbsp;5 516 (7.9%) | `░░░░░░░░░░░░░░░░░░░░`&nbsp;61 430 (88.3%) |
| 2026-03 | `█`&nbsp;3 104 (3.9%) | `▓▓`&nbsp;6 696 (8.4%) | `░░░░░░░░░░░░░░░░░░░░░░░`&nbsp;70 130 (87.7%) |
| 2026-04 | `█`&nbsp;3 387 (4.4%) | `▓▓`&nbsp;5 836 (7.6%) | `░░░░░░░░░░░░░░░░░░░░░░`&nbsp;67 391 (88.0%) |
| 2026-05 | `█`&nbsp;3 441 (4.4%) | `▓▓`&nbsp;5 625 (7.2%) | `░░░░░░░░░░░░░░░░░░░░░░░`&nbsp;69 006 (88.4%) |
| 2026-06 | `█`&nbsp;3 472 (4.2%) | `▓▓`&nbsp;6 111 (7.4%) | `░░░░░░░░░░░░░░░░░░░░░░░░`&nbsp;72 969 (88.4%) |
| 2026-07 | `█`&nbsp;3 538 (7.1%) | `▓▓`&nbsp;5 573 (11.2%) | `░░░░░░░░░░░░░`&nbsp;40 606 (81.7%) |
| 2026-08 | `█`&nbsp;4 173 (5.4%) | `▓▓`&nbsp;6 687 (8.6%) | `░░░░░░░░░░░░░░░░░░░░░░`&nbsp;66 736 (86.0%) |
| 2026-09 | `█`&nbsp;2 217 (14.5%) | `▓`&nbsp;3 783 (24.7%) | `░░░`&nbsp;9 285 (60.7%) |

## Naming patterns

How module names relate to their publishing groupId. "Competing groupIds" counts modules whose name has been published under more than one groupId across history (i.e. collisions). Classifier variants are summarised in the Classifiers section above.

| Pattern | Modules |
|---|---:|
| Multiple competing groupIds in audit history | 4 301 |

### Leading dot-segments shared with the owning groupId

For each canonical (no-classifier) module that resolved to an owner (implicit or explicit), counts how many leading dot-segments its module name shares with the owner's groupId. A high share is the textbook JPMS pattern (e.g. module `com.example.foo` published by groupId `com.example.foo`); zero indicates a module name that diverges entirely from its publisher's groupId. Classifier variants are out of scope because they share the canonical's groupId by construction. Empty buckets render as `-`.

| Shared leading dot-segments | Canonical modules |
|---:|---:|
| 0 | 8 878 |
| 1 | 1 140 |
| 2 | 12 247 |
| 3 | 13 095 |
| 4 | 2 462 |
| 5 | 345 |
| 6 | 18 |
| 7 | - |
| 8 | 2 |

## Processing errors

Recorded permanent failures across every scanned coordinate. Variable bits of well-known error classes (URLs, shaded package names, classfile entry indexes, HTTP status codes, line numbers, class identifiers) are replaced with placeholders like `<URL>`, `<PACKAGE>`, `<CLASS>` so messages that differ only in those bits aggregate into one row.

`Incorrectly indexed` is the dominant failure class: coordinates the upstream Nexus index mis-stamps as having a main JAR when only a POM was ever published (BOMs, parent POMs). The crawler probes once, the fetch 404s, and the row is recorded permanently; these are an upstream-data artifact, not a problem with the JAR, and are excluded from the `Total artifacts scanned` and `Modular artifacts` totals above. `Genuine artifact errors` is the remainder - malformed JARs, unparseable `module-info`, and the like - and is broken out in the top-N table below.

| Metric | Value |
|---|---:|
| Total failed coordinates | 3 887 365 |
| Incorrectly indexed (mis-stamped 404s) | 3 884 853 |
| Genuine artifact errors | 2 512 |

### Top 25 genuine error messages

Excludes the mis-stamped-404 class broken out above, so the genuine artifact errors are visible rather than buried beneath it.

| Error message | Count |
|---|---:|
| `IllegalArgumentException: End of central directory record not found in supplied tail buffer` | 594 |
| `InvalidModuleDescriptorException: Package <PACKAGE> missing from ModulePackages class file attribute` | 518 |
| `IllegalArgumentException: Illegal character in path at index <INDEX>: <PATH>` | 330 |
| `InvalidModuleDescriptorException: Unsupported major.minor version <VERSION>` | 304 |
| `InvalidModuleDescriptorException: this_class should be module-info` | 246 |
| `InvalidModuleDescriptorException: <CLASS>: unnamed package` | 156 |
| `InvalidModuleDescriptorException: CONSTANT_Package at entry <ENTRY> has illegal character: '.'` | 83 |
| `IllegalArgumentException: Expected central file header signature at offset <OFFSET>` | 76 |
| `InvalidModuleDescriptorException: <CLASS>: Invalid service type name: '<NAME>' is not a Java identifier` | 45 |
| `InvalidModuleDescriptorException: Index into constant pool out of range` | 34 |
| `IllegalArgumentException: Expected ZIP64 end of central directory signature at offset <OFFSET>` | 24 |
| `InvalidModuleDescriptorException: <CLASS>: is not a qualified name of a Java class in a named package` | 23 |
| `InvalidModuleDescriptorException: Dependence on self` | 20 |
| `IOException: invalid header field (line <LINE>)` | 17 |
| `InvalidModuleDescriptorException: The requires entry for java.base has ACC_SYNTHETIC set` | 15 |
| `InvalidModuleDescriptorException: Dependence upon <MODULE> already declared` | 9 |
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
| `software.amazon.awssdk.* (511 modules)` | [1, 1 833] |
| `org.scala.lang.scala3.* (12 modules)` | [9, 1 244] |
| `org.scala.lang.* (excl. scala3) (4 modules)` | [354, 1 214] |
| `com.graphqljava` | 1 184 |
| `net.minestom.server` | 752 |
| `com.google.api.services.* (349 modules)` | [1, 580] |
| `io.fluxzero.common` | 515 |
| `io.fluxzero.proxy` | 515 |
| `io.fluxzero.testserver` | 515 |
| `io.fluxzero.sdk` | 512 |
| `com.azure.sdk.template` | 475 |
| `com.hazelcast.all` | 456 |
| `javassist` | 456 |
| `org.apache.commons.math3` | 456 |
| `org.apache.cxf` | 456 |
| `org.apache.poi.ooxml` | 456 |
| `io.undertow.parser.generator` | 446 |
| `undertow.* (3 modules)` | [415, 446] |
| `com.guicedee.* (69 modules)` | [1, 445] |
| `jandex` | 442 |
| `dom4j` | 437 |
| `jakarta.enterprise.* (2 modules)` | 437 |
| `jaxen` | 433 |
| `primefaces` | 415 |
| `com.tigerbeetle` | 409 |

## Top 25 groupIds by module count

GroupIds that publish the most distinct module names, sorted by module count. Each `(groupId, moduleName)` pair counts once regardless of how many versions or classifier variants exist.

| groupId | Modules published |
|---|---:|
| `org.bytedeco` | 678 |
| `software.amazon.awssdk` | 514 |
| `org.lwjgl` | 505 |
| `org.apereo.cas` | 491 |
| `io.opentelemetry.javaagent.instrumentation` | 355 |
| `org.springframework.boot` | 351 |
| `com.google.apis` | 349 |
| `io.github.shuigedeng` | 348 |
| `org.kie` | 280 |
| `com.azure.resourcemanager` | 271 |
| `org.eclipse.platform` | 257 |
| `org.neo4j` | 221 |
| `io.micronaut.oraclecloud` | 213 |
| `com.gitee.zodiacstack` | 211 |
| `com.blazebit` | 209 |
| `io.github.bluetape4k` | 197 |
| `com.aoapps` | 189 |
| `io.netty` | 186 |
| `org.drools` | 185 |
| `com.helger` | 182 |
| `org.kie.kogito` | 182 |
| `org.ogc-schemas` | 175 |
| `net.solarnetwork.node` | 171 |
| `com.azure` | 159 |
| `io.micronaut` | 159 |

## Top 25 modules with most colliding groupIds

Module names that have been published under the most different groupIds across history. A high count indicates name reuse: forks, rebranded artifacts, or coordinate moves whose historical declarations remain on record even after the canonical publisher is set.

| Module | Distinct groupIds |
|---|---:|
| `com.google.gson` | 310 |
| `com.fasterxml.jackson.databind` | 270 |
| `com.fasterxml.jackson.core` | 238 |
| `com.fasterxml.jackson.annotation [-jar-with-dependencies]` | 229 |
| `com.fasterxml.jackson.annotation` | 188 |
| `org.slf4j` | 188 |
| `org.apache.logging.log4j` | 183 |
| `kotlin.stdlib` | 149 |
| `kotlin.stdlib.jdk8` | 137 |
| `com.google.gson [-jar-with-dependencies]` | 130 |
| `com.fasterxml.jackson.databind [-jar-with-dependencies]` | 110 |
| `org.apache.logging.log4j [-jar-with-dependencies]` | 95 |
| `java.xml.bind [-jar-with-dependencies]` | 89 |
| `java.xml.bind` | 88 |
| `net.bytebuddy` | 87 |
| `org.objectweb.asm [-jar-with-dependencies]` | 80 |
| `kotlin.stdlib.jdk8 [-all]` | 78 |
| `info.picocli` | 77 |
| `com.fasterxml.jackson.core [-jar-with-dependencies]` | 75 |
| `org.apache.commons.lang3` | 75 |
| `com.google.gson [-all]` | 72 |
| `com.fasterxml.jackson.module.jaxb` | 67 |
| `org.yaml.snakeyaml` | 67 |
| `org.apache.commons.codec` | 66 |
| `org.apache.commons.io` | 66 |

## Top 25 modules updated in the last 7 days

Modules whose most recent publication landed in the 7-day window ending at the most recent tracked publication (same window as `Recent activity`, anchored to the freshest publication rather than now since the index lags up to a week), sorted newest first. Use this as a recency view; the count above (`Recent activity`) gives the totals while this table names which modules they were.

| Module | Last publication |
|---|---|
| `build.spin` | 2026-09-15 03:11:47 UTC |
| `build.spin.application` | 2026-09-15 03:11:47 UTC |
| `build.spin.common` | 2026-09-15 03:11:47 UTC |
| `build.spin.engine` | 2026-09-15 03:11:47 UTC |
| `build.spin.module.java` | 2026-09-15 03:11:47 UTC |
| `build.spin.module.modulesystem` | 2026-09-15 03:11:47 UTC |
| `build.spin.testing` | 2026-09-15 03:11:47 UTC |
| `build.spin.module.checkstyle` | 2026-09-15 03:11:46 UTC |
| `build.spin.module.clean` | 2026-09-15 03:11:46 UTC |
| `build.spin.module.configuration` | 2026-09-15 03:11:46 UTC |
| `build.spin.module.git` | 2026-09-15 03:11:46 UTC |
| `build.spin.module.gpg` | 2026-09-15 03:11:46 UTC |
| `build.spin.module.jar` | 2026-09-15 03:11:46 UTC |
| `build.spin.module.junit` | 2026-09-15 03:11:46 UTC |
| `build.spin.module.maven` | 2026-09-15 03:11:46 UTC |
| `build.spin.module.reporting` | 2026-09-15 03:11:46 UTC |
| `ai.docling.core` | 2026-09-14 16:36:17 UTC |
| `ai.docling.serve.api` | 2026-09-14 16:36:17 UTC |
| `ai.docling.serve.client` | 2026-09-14 16:36:17 UTC |
| `ai.docling.testcontainers` | 2026-09-14 16:36:17 UTC |
| `com.github.jknack.handlebars` | 2026-09-14 14:48:36 UTC |
| `com.github.jknack.handlebars.caffeine` | 2026-09-14 14:48:36 UTC |
| `com.github.jknack.handlebars.guava` | 2026-09-14 14:48:36 UTC |
| `com.github.jknack.handlebars.helper.ext` | 2026-09-14 14:48:36 UTC |
| `com.github.jknack.handlebars.jackson` | 2026-09-14 14:48:36 UTC |

## Top 25 groupIds by average versions per module

Restricted to groupIds publishing at least 3 modules so the average isn't dominated by single-module outliers.

| groupId | Modules | Total versions | Avg versions / module |
|---|---:|---:|---:|
| `software.amazon.awssdk` | 514 | 634 608 | 1234.6 |
| `org.scala-lang` | 19 | 12 690 | 667.9 |
| `com.graphql-java` | 3 | 1 351 | 450.3 |
| `io.fluxzero` | 5 | 2 060 | 412.0 |
| `com.guicedee.servlets` | 11 | 3 517 | 319.7 |
| `com.jwebmp.plugins.angular` | 17 | 5 151 | 303.0 |
| `com.jwebmp.plugins.effects` | 3 | 909 | 303.0 |
| `com.jwebmp.plugins.iconsets` | 7 | 2 121 | 303.0 |
| `com.jwebmp.plugins.javascript` | 12 | 3 632 | 302.7 |
| `com.jwebmp.plugins.bootstrap` | 8 | 2 409 | 301.1 |
| `com.guicedee.services` | 45 | 12 368 | 274.8 |
| `net.minestom` | 3 | 795 | 265.0 |
| `com.guicedee.persistence` | 9 | 2 377 | 264.1 |
| `com.jwebmp.plugins.jquery` | 17 | 4 350 | 255.9 |
| `com.jwebmp.plugins.graphing` | 5 | 1 247 | 249.4 |
| `com.jwebmp.plugins.forms` | 10 | 2 436 | 243.6 |
| `com.jwebmp.plugins.security` | 3 | 707 | 235.7 |
| `systems.manifold` | 43 | 9 250 | 215.1 |
| `com.jwebmp.core` | 3 | 594 | 198.0 |
| `net.serenity-bdd` | 27 | 5 265 | 195.0 |
| `org.springframework` | 23 | 4 332 | 188.3 |
| `org.hibernate.orm` | 26 | 4 681 | 180.0 |
| `com.netflix.spectator` | 27 | 4 770 | 176.7 |
| `org.apache.tomcat.embed` | 5 | 878 | 175.6 |
| `org.neo4j.community` | 17 | 2 919 | 171.7 |

_This file is regenerated on every `ModuleSummary` run; previous content is discarded._
