package com.deathsound.mixin;

import com.deathsound.DeathSoundMod;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class DeathSoundMixin {
    @Inject(method = "getDeathSound", at = @At("HEAD"), cancellable = true)
    private void onGetDeathSound(CallbackInfoReturnable<SoundEvent> cir) {
        cir.setReturnValue(DeathSoundMod.CUSTOM_DEATH_SOUND);
    }
}
