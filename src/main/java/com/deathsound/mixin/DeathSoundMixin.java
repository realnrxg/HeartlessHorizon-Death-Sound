package com.deathsound.mixin;

import com.deathsound.DeathSoundMod;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class DeathSoundMixin {
    @Inject(method = "getDeathSound", at = @At("HEAD"), cancellable = true)
    private void onGetDeathSound(CallbackInfoReturnable<SoundEvent> cir) {
        cir.setReturnValue(DeathSoundMod.CUSTOM_DEATH_SOUND);
    }
}
