package net.susu609.modlib;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.susu609.modlib.resource.RL;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Example common config for SuModLib.
 */
@Mod.EventBusSubscriber(modid = SuModLib.MOD_ID,
                        bus = Mod.EventBusSubscriber.Bus.MOD)
public final class Config {

    private static final ForgeConfigSpec.Builder BUILDER =
            new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.ConfigValue<String>
            MAGIC_NUMBER_INTRODUCTION = BUILDER.comment(
                    "What you want the introduction message to be for the " +
                            "magic number")
            .define("magicNumberIntroduction", "The magic number is... ");

    private static final ForgeConfigSpec.BooleanValue LOG_DIRT_BLOCK =
            BUILDER.comment("Whether to log the dirt block on common setup")
                    .define("logDirtBlock", true);

    private static final ForgeConfigSpec.IntValue MAGIC_NUMBER =
            BUILDER.comment("A magic number")
                    .defineInRange("magicNumber", 42, 0, Integer.MAX_VALUE);

    /*
     * Build the spec only after every value has been defined.
     */
    static final ForgeConfigSpec SPEC = BUILDER.build();
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>>
            ITEM_STRINGS =
            BUILDER.comment("A list of items to log on common setup.")
                    .defineListAllowEmpty(
                            "items",
                            List.of("minecraft:iron_ingot"),
                            Config::validateItemName);

    public static boolean logDirtBlock;
    public static int magicNumber;
    public static String magicNumberIntroduction;
    public static Set<Item> items;

    private Config() {
    }

    private static boolean validateItemName(final Object obj) {
        if (!(obj instanceof String itemName)) {
            return false;
        }
        ResourceLocation itemId = RL.parse(itemName);
        return itemId != null && ForgeRegistries.ITEMS.containsKey(itemId);
    }

    @SubscribeEvent static void onLoad(final ModConfigEvent event) {
        logDirtBlock = LOG_DIRT_BLOCK.get();
        magicNumber = MAGIC_NUMBER.get();
        magicNumberIntroduction = MAGIC_NUMBER_INTRODUCTION.get();
        items = ITEM_STRINGS.get().stream().map(RL::require)
                .map(ForgeRegistries.ITEMS::getValue)
                .filter(item -> item != null)
                .collect(Collectors.toSet());
    }
}
