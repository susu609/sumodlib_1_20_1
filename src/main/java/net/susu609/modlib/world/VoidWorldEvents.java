package net.susu609.modlib.world;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.susu609.modlib.SuModLib;

/**
 * When a world of type Void is created: a 5x5 stone platform at the spawn,
 * the spawn on top of it, noon, and the quiet game rules.
 */
@Mod.EventBusSubscriber(modid = SuModLib.MOD_ID,
                        bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class VoidWorldEvents {
    private VoidWorldEvents() {
    }

    @SubscribeEvent
    public static void onCreateSpawn(LevelEvent.CreateSpawnPosition event) {
        if (!(event.getLevel() instanceof ServerLevel level) ||
                !VoidWorlds.isVoid(level))
            return;
        BlockPos spawn = VoidWorlds.ensureSpawnPlatform(level);
        event.getSettings().setSpawn(spawn, 0.0F);
        VoidWorlds.applyQuietRules(level.getGameRules(), level.getServer());
        level.setDayTime(6000L);
        event.setCanceled(true);
    }
}
