package net.susu609.modlib;

import net.minecraftforge.common.ForgeConfigSpec;

/**
 * Client config of SuModLib ({@code sumodlib-client.toml}).
 */
public final class ClientConfig {

    private static final ForgeConfigSpec.Builder BUILDER =
            new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.BooleanValue
            DISABLE_VANILLA_TUTORIAL = BUILDER
            .comment("Turn the vanilla tutorial (the movement, inventory and " +
                    "crafting toasts) off whenever you join a world.")
            .define("disableVanillaTutorial", false);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    private ClientConfig() {
    }
}
