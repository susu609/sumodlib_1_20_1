package net.susu609.modlib;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(SuModLib.MOD_ID) public final class SuModLib {

    public static final String MOD_ID = "sumodlib";

    public SuModLib(FMLJavaModLoadingContext context) {
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
}
