package com.deathsound;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Files;
import java.nio.file.Path;

public class DeathSoundConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static Path configPath;
    private static float volumeMultiplier = 1.0f;

    public static void init() {
        configPath = FabricLoader.getInstance().getConfigDir().resolve("deathsound.json");
        load();
    }

    private static void load() {
        try {
            if (Files.exists(configPath)) {
                String content = Files.readString(configPath);
                JsonObject obj = GSON.fromJson(content, JsonObject.class);
                if (obj.has("volume")) {
                    volumeMultiplier = obj.get("volume").getAsFloat();
                }
            }
        } catch (Exception ignored) {
        }
        volumeMultiplier = Math.max(0, Math.min(1.0f, volumeMultiplier));
    }

    public static void save() {
        try {
            Files.createDirectories(configPath.getParent());
            JsonObject obj = new JsonObject();
            obj.addProperty("volume", volumeMultiplier);
            Files.writeString(configPath, GSON.toJson(obj));
        } catch (Exception ignored) {
        }
    }

    public static float getVolumeMultiplier() {
        return volumeMultiplier;
    }

    public static void setVolumeMultiplier(float v) {
        volumeMultiplier = Math.max(0, Math.min(1.0f, v));
        save();
    }
}
