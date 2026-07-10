package com.deathsound;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class DeathSoundMod implements ModInitializer {
    public static final String MOD_ID = "deathsound";

    public static final Identifier CUSTOM_DEATH_SOUND_ID = Identifier.of(MOD_ID, "player.death");
    public static final SoundEvent CUSTOM_DEATH_SOUND = SoundEvent.of(CUSTOM_DEATH_SOUND_ID);

    @Override
    public void onInitialize() {
        DeathSoundConfig.init();

        Registry.register(Registries.SOUND_EVENT, CUSTOM_DEATH_SOUND_ID, CUSTOM_DEATH_SOUND);

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("deathsound")
                .then(literal("volume")
                    .then(argument("percent", IntegerArgumentType.integer(0, 100))
                        .executes(context -> {
                            int percent = IntegerArgumentType.getInteger(context, "percent");
                            DeathSoundConfig.setVolumeMultiplier(percent / 100.0f);
                            context.getSource().sendFeedback(() ->
                                Text.literal("§aDeath sound volume set to " + percent + "%"), true);
                            return 1;
                        })
                    )
                    .executes(context -> {
                        int current = Math.round(DeathSoundConfig.getVolumeMultiplier() * 100);
                        context.getSource().sendFeedback(() ->
                            Text.literal("§eCurrent death sound volume: " + current + "%"), false);
                        return 1;
                    })
                )
            );
        });
    }
}
