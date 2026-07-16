package com.deathsound.mixin;

import com.deathsound.DeathSoundConfig;
import com.deathsound.DeathSoundMod;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class DeathVolumeMixin {
    private static final ThreadLocal<Boolean> replaying = ThreadLocal.withInitial(() -> false);

    @Shadow
    public void playSound(SoundEvent sound, float volume, float pitch) {}

    @Inject(method = "playSound(Lnet/minecraft/sounds/SoundEvent;FF)V", at = @At("HEAD"), cancellable = true)
    private void onPlaySound(SoundEvent sound, float volume, float pitch, CallbackInfo ci) {
        if (sound == DeathSoundMod.CUSTOM_DEATH_SOUND && !replaying.get()) {
            replaying.set(true);
            ci.cancel();
            this.playSound(sound, volume * DeathSoundConfig.getVolumeMultiplier(), pitch);
            replaying.set(false);
        }
    }
}
