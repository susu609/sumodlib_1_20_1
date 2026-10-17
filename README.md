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

## Void worlds

`net.susu609.modlib.world.VoidWorlds` and a few data files give empty worlds
that load in a moment instead of generating vanilla terrain.

**World type "Void".** On the Create World screen, *World Type: Void*; on a
dedicated server, `level-type=sumodlib:void` in `server.properties`. The
overworld is empty (biome The Void, no structures) except a 5x5 stone
platform at (0, 64, 0) where players spawn. When such a world is created,
the game rules are set to fixed noon, no weather and no natural mob
spawning. The Nether and the End are vanilla.

**Void dimension for a mod.** Add `data/<modid>/dimension/<name>.json`:

```json
{
  "type": "sumodlib:void",
  "generator": {
    "type": "minecraft:flat",
    "settings": {
      "biome": "minecraft:the_void",
      "layers": [{ "block": "minecraft:air", "height": 1 }],
      "lakes": false,
      "features": false,
      "structure_overrides": []
    }
  }
}
```

`sumodlib:void` is a dimension type with fixed noon, no beds, Y 0..255. In
code: `VoidWorlds.level(server, VoidWorlds.dimension("<modid>", "<name>"))`
(falls back to the overworld if the dimension is missing),
`VoidWorlds.isVoid(level)`, `VoidWorlds.ensurePlatform(level, pos, radius,
block)`.

**Straight into a test world.** With the JVM property `sumodlib.devWorld`
the client skips the title screen and opens that world, creating it as a
Void world (creative, peaceful, cheats on) the first time:

```groovy
minecraft.runs.client { property 'sumodlib.devWorld', 'Dev Void' }
```

Quitting to the title screen stays there; the shortcut fires once per
launch. Delete the world folder in `run-client1/saves/` to start fresh.

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

## Maven Central

The build can also produce a signed bundle for Maven Central
(`./gradlew centralBundle`, coordinates `io.github.susu609:sumodlib:<version>`).
It has not been released there yet; the steps and what is still needed
(a license choice, a PGP key) are in [PUBLISHING.md](PUBLISHING.md).

