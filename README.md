# Su Mod Lib — Minecraft Forge 1.20.1

[![](https://jitpack.io/v/susu609/sumodlib_1_20_1.svg)](https://jitpack.io/#susu609/sumodlib_1_20_1)

Shared utilities for Su's Forge mods (`net.susu609.modlib`).

- Minecraft Java 1.20.1, Forge 47.4.23, Mojang official mappings, Java 17
- Mod ID: `sumodlib`

## Using it from another mod

The library is built on demand by [JitPack](https://jitpack.io/#susu609/sumodlib_1_20_1):

```groovy
repositories {
    maven {
        url = 'https://jitpack.io'
        content { includeGroup 'com.github.susu609' }
    }
}

dependencies {
    implementation fg.deobf('com.github.susu609:sumodlib_1_20_1:1.0.0')
}
```

The version is a git tag of this repository (or `<branch>-SNAPSHOT`, for
example `master-SNAPSHOT`, to try unreleased changes).

## Releasing a version

1. Merge the changes into `master` and bump `mod_version` in
   `gradle.properties`.
2. Create a GitHub release whose tag equals that version (for example `1.0.0`).
3. Open https://jitpack.io/#susu609/sumodlib_1_20_1 and press **Get it** next
   to the tag once, so JitPack builds it. Later requests are served from cache.

JitPack runs the command in `jitpack.yml`
(`./gradlew publishToMavenLocal -x test`) with JDK 17.

## Local development

`./gradlew publishToMavenLocal` installs
`net.susu609.modlib:sumodlib:<version>` into `~/.m2`. A consuming mod can use
that copy by setting `sumodlib_source=local` in its `gradle.properties`.
