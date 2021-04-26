package com.gmail.gorayan3838.notebetterfabric.mixin.client;

import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundSystem;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SoundSystem.class)
public class SoundSystemMixin {

    @Redirect(method = "play(Lnet/minecraft/client/sound/SoundInstance;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sound/SoundSystem;getAdjustedPitch(Lnet/minecraft/client/sound/SoundInstance;)F"))
    private float injected(SoundSystem soundSystem, SoundInstance soundInstance) {
        return MathHelper.clamp(soundInstance.getPitch(), 0.0000000000000000001F, Float.MAX_VALUE);
    }
}
