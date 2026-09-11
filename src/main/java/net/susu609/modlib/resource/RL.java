package net.susu609.modlib.resource;

import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

/**
 * Shared ResourceLocation helpers for Forge 1.20.1 projects.
 */
public final class RL {

    private RL() {
    }

    public static ResourceLocation of(String namespace, String path) {
        return require(namespace + ":" + path);
    }

    /**
     * Parses an internal id that is expected to be valid.
     */
    public static ResourceLocation require(String value) {
        ResourceLocation result = ResourceLocation.tryParse(value);
        if (result == null) {
            throw new IllegalArgumentException(
                    "Invalid ResourceLocation: " + value);
        }
        return result;
    }

    public static ResourceLocation mc(String path) {
        return require("minecraft:" + path);
    }

    /**
     * Parses external/config input. Invalid input returns null.
     */
    @Nullable public static ResourceLocation parse(String value) {
        return ResourceLocation.tryParse(value);
    }
}
