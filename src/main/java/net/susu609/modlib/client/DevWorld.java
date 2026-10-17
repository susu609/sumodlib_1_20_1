package net.susu609.modlib.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.WorldDataConfiguration;
import net.minecraft.world.level.levelgen.WorldOptions;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.susu609.modlib.SuModLib;
import net.susu609.modlib.world.VoidWorlds;

/**
 * Development shortcut: when the JVM property {@code sumodlib.devWorld} names
 * a world, the client skips the title screen and opens that world, creating
 * it as a Void world (creative, normal difficulty, cheats on, quiet rules)
 * the first time. Not peaceful: peaceful removes every hostile mob, which
 * breaks mods that spawn their own. Set it on a ForgeGradle run:
 * {@code property 'sumodlib.devWorld', 'Dev Void'}. It only fires once per
 * launch, so quitting to the title screen stays there.
 */
@Mod.EventBusSubscriber(modid = SuModLib.MOD_ID,
                        bus = Mod.EventBusSubscriber.Bus.FORGE,
                        value = Dist.CLIENT)
public final class DevWorld {
    public static final String PROPERTY = "sumodlib.devWorld";
    private static boolean done;

    private DevWorld() {
    }

    @SubscribeEvent
    public static void onTitle(ScreenEvent.Init.Post event) {
        if (done || !(event.getScreen() instanceof TitleScreen title))
            return;
        done = true;
        String name = System.getProperty(PROPERTY, "").trim();
        if (name.isEmpty())
            return;
        // Open the world on the next tick, not while the title screen is
        // still being set up.
        Minecraft.getInstance().tell(() -> open(title, name));
    }

    private static void open(TitleScreen title, String name) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.getLevelSource().levelExists(name)) {
            minecraft.createWorldOpenFlows().loadLevel(title, name);
            return;
        }
        GameRules rules = new GameRules();
        VoidWorlds.applyQuietRules(rules, null);
        LevelSettings settings = new LevelSettings(name, GameType.CREATIVE,
                false, Difficulty.NORMAL, true, rules,
                WorldDataConfiguration.DEFAULT);
        WorldOptions options = new WorldOptions(WorldOptions.randomSeed(),
                false, false);
        minecraft.createWorldOpenFlows().createFreshLevel(name, settings,
                options, registries -> registries
                        .registryOrThrow(Registries.WORLD_PRESET)
                        .getHolderOrThrow(VoidWorlds.PRESET).value()
                        .createWorldDimensions());
    }
}
