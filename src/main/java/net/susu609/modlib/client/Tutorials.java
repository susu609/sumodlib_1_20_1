package net.susu609.modlib.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.tutorial.TutorialSteps;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.susu609.modlib.ClientConfig;
import net.susu609.modlib.SuModLib;

/**
 * Switch for the vanilla tutorial (the "Look around", "Move" and "Open
 * inventory" toasts). Off by default. Turn it on with the client config
 * {@code disableVanillaTutorial = true}, or with the JVM property
 * {@code sumodlib.noTutorial=true} (a ForgeGradle run:
 * {@code property 'sumodlib.noTutorial', 'true'}); the tutorial is then
 * switched off every time the player joins a world. A mod can also call
 * {@link #disable()} itself when it needs the toasts gone.
 * <p>
 * Switching the tutorial off is what the options screen does: the step is
 * stored in {@code options.txt} as {@code tutorialStep:none}, so it stays off
 * after the switch is turned off again. Set it back in {@code options.txt}.
 */
@Mod.EventBusSubscriber(modid = SuModLib.MOD_ID,
                        bus = Mod.EventBusSubscriber.Bus.FORGE,
                        value = Dist.CLIENT)
public final class Tutorials {
    public static final String PROPERTY = "sumodlib.noTutorial";

    private Tutorials() {
    }

    /** Whether the config or the JVM property asks for the tutorial off. */
    public static boolean requested() {
        return ClientConfig.DISABLE_VANILLA_TUTORIAL.get() ||
                Boolean.getBoolean(PROPERTY);
    }

    /** Stops the running tutorial and sets its step to none. */
    public static void disable() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.options.tutorialStep != TutorialSteps.NONE)
            minecraft.getTutorial().setStep(TutorialSteps.NONE);
    }

    @SubscribeEvent
    public static void onJoin(ClientPlayerNetworkEvent.LoggingIn event) {
        if (requested())
            disable();
    }
}
