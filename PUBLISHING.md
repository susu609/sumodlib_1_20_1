# Publishing sumodlib

`sumodlib` reaches the other mods in three ways:

| Source | Command | Coordinates |
|---|---|---|
| Maven Local | `./gradlew publishToMavenLocal` | `net.susu609.modlib:sumodlib:<version>` |
| JitPack | git tag + <https://jitpack.io/#susu609/sumodlib_1_20_1> | `com.github.susu609:sumodlib_1_20_1:<tag>` |
| Maven Central | `./gradlew centralBundle`, then upload the zip | `io.github.susu609:sumodlib:<version>` |

Everything is configured in `gradle/central-publishing.gradle`. Maven Local and
JitPack keep the project group (`mod_group_id`); only the Central bundle uses
`maven_group_id`, because Central needs a namespace you have verified. The Java
packages (`net.susu609.modlib...`) do not change.

## One-time setup for Maven Central

1. **Account and namespace.** Sign in to <https://central.sonatype.com> with
   GitHub. The namespace `io.github.susu609` is tied to that GitHub login; check
   that it shows as verified in the portal. To use your own domain instead you
   must prove ownership with a DNS TXT record, then change `maven_group_id`.
2. **License.** Central requires license information in the POM. Pick a
   license, set `pom_license_name` and `pom_license_url` in `gradle.properties`
   (they are commented out on purpose), add a `LICENSE` file and update
   `mod_license`. The current value, `All Rights Reserved`, is not a license
   Central can describe. Choosing it is your decision.
3. **PGP key.** Central requires every file to be signed.
   ```
   gpg --full-generate-key
   gpg --list-secret-keys --keyid-format long
   gpg --keyserver keyserver.ubuntu.com --send-keys <KEYID>
   ```
   Keep the secret key and its passphrase private; never commit them. On
   Windows, install Gpg4win.
4. **Signing settings**, in `~/.gradle/gradle.properties` (your user folder,
   not this repo):
   ```
   signing.gnupg.keyName=<KEYID>
   signing.gnupg.passphrase=<passphrase>
   # Windows, if gpg is not on PATH:
   # signing.gnupg.executable=C:\\Program Files (x86)\\GnuPG\\bin\\gpg.exe
   ```
   Alternatively set the environment variables
   `ORG_GRADLE_PROJECT_signingKey` (the armored output of
   `gpg --armor --export-secret-keys <KEYID>`) and
   `ORG_GRADLE_PROJECT_signingPassword`.

## Releasing a version

1. Bump `mod_version` in `gradle.properties` and commit. A version on Central
   can never be replaced or deleted, so bump it again for any fix.
2. Run `./gradlew centralBundle`. It stops immediately and lists any missing
   property. It writes `build/central-bundle/sumodlib-<version>-central-bundle.zip`
   containing the jar, POM, sources jar, Javadoc jar, a `.asc` signature for each
   and md5/sha1/sha256/sha512 checksums.
3. In the Central Portal choose to publish a component, give it a name and
   upload the zip. Pick the manually released option so you can inspect the
   validation result first, then release it. (Menu names may change; see the
   Portal's "publish by uploading a bundle" documentation.)
4. After a few minutes the files appear under
   <https://repo1.maven.org/maven2/io/github/susu609/sumodlib/>.
5. In a consuming mod set `sumodlib_source=central`.

## What has and has not been tested

`gradle/central-publishing.gradle` was run in a stand-in Gradle project (8.14,
offline, with a throwaway PGP key and a fake `reobfJar`): the bundle layout, the
four checksums per file, valid signatures, a complete POM without
dependencies, the fail-fast check for missing properties and an unchanged
`publishToMavenLocal` all behaved as described. It has **not** been run with
ForgeGradle, so the first real run may need small adjustments (for example if
Javadoc generation behaves differently against the Minecraft classpath, which
is set not to fail the build).
