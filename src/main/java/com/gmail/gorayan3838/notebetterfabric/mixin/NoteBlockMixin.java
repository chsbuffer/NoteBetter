package com.gmail.gorayan3838.notebetterfabric.mixin;

import com.gmail.gorayan3838.notebetterfabric.NoteBetterFabric;
import com.gmail.gorayan3838.notebetterfabric.config.SoundConfig;
import net.minecraft.block.NoteBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Mixin(NoteBlock.class)
public class NoteBlockMixin {

    @Redirect(method = "onSyncedBlockEvent", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FF)V"))
    private void injected(World world, @Nullable PlayerEntity player, BlockPos pos, SoundEvent sound, SoundCategory category, float volume, float pitch) {
        SoundConfig.Mapping[] mappings = NoteBetterFabric.CONFIG.getMappings();
        List<SoundConfig.Mapping> filteredMappings = Arrays.stream(mappings).filter(mapping -> world.getBlockState(pos.down()).getBlock().equals(Registry.BLOCK.get(new Identifier(mapping.getBlock())))).collect(Collectors.toList());
        filteredMappings.forEach(mapping -> {
            SoundConfig.Sound soundInfo = mapping.getSound();
            world.playSound(
                    null,
                    pos,
                    new SoundEvent(new Identifier(soundInfo.getSound())),
                    SoundCategory.RECORDS,
                    soundInfo.getVolume(),
                    soundInfo.getPitch() * pitch
            );
        });
        if (!filteredMappings.isEmpty()) {
            return;
        }
        world.playSound(null, pos, sound, SoundCategory.RECORDS, volume, pitch);
    }
}
