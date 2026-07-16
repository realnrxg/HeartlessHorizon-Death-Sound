package com.deathsound;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class DeathSoundMod implements ModInitializer {
    public static final String MOD_ID = "deathsound";

    public static final Identifier CUSTOM_DEATH_SOUND_ID = Identifier.fromNamespaceAndPath(MOD_ID, "player.death");
    public static final SoundEvent CUSTOM_DEATH_SOUND = SoundEvent.createVariableRangeEvent(CUSTOM_DEATH_SOUND_ID);

    @Override
    public void onInitialize() {
        DeathSoundConfig.init();

        Registry.register(BuiltInRegistries.SOUND_EVENT, CUSTOM_DEATH_SOUND_ID, CUSTOM_DEATH_SOUND);

        CommandRegistrationCallback.EVENT.register((dispatcher, buildContext, selection) -> {
            dispatcher.register(literal("deathsound")
                .then(literal("volume")
                    .then(argument("percent", IntegerArgumentType.integer(0, 100))
                        .executes(context -> {
                            int percent = IntegerArgumentType.getInteger(context, "percent");
                            DeathSoundConfig.setVolumeMultiplier(percent / 100.0f);
                            context.getSource().sendSuccess(() ->
                                Component.literal("§aDeath sound volume set to " + percent + "%"), true);
                            return 1;
                        })
                    )
                    .executes(context -> {
                        int current = Math.round(DeathSoundConfig.getVolumeMultiplier() * 100);
                        context.getSource().sendSuccess(() ->
                            Component.literal("§eCurrent death sound volume: " + current + "%"), false);
                        return 1;
                    })
                )
            );
        });
    }
}
