package com.gmail.gorayan3838.notebetterfabric.mixin;

import com.gmail.gorayan3838.notebetterfabric.NoteBetterFabric;
import com.gmail.gorayan3838.notebetterfabric.config.SoundConfig;
import net.minecraft.block.Block;
import net.minecraft.block.NoteBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.registry.Registries;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Arrays;
import java.util.List;

@Mixin(NoteBlock.class)
public class NoteBlockMixin {

    @Redirect(method = "onSyncedBlockEvent", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/PlayerEntity;DDDLnet/minecraft/registry/entry/RegistryEntry;Lnet/minecraft/sound/SoundCategory;FFJ)V"))
    private void injected(World world, @Nullable PlayerEntity player, double x, double y, double z, RegistryEntry<SoundEvent> sound, SoundCategory category, float volume, float pitch, long seed) {
        BlockPos pos = new BlockPos((int) Math.floor(x), (int) Math.floor(y), (int) Math.floor(z));
        SoundConfig.Mapping[] mappings = NoteBetterFabric.CONFIG.getMappings();
        List<SoundConfig.Mapping> filteredMappings = Arrays.stream(mappings)
                .filter(mapping -> world.getBlockState(pos.down()).getBlock().equals(Registries.BLOCK.get(new Identifier(mapping.getBlock()))))
                .toList();
        filteredMappings.forEach(mapping -> {
            SoundConfig.Sound soundInfo = mapping.getSound();
            world.playSound(
                    null,
                    x,
                    y,
                    z,
                    SoundEvent.of(new Identifier(soundInfo.getSound())),
                    SoundCategory.RECORDS,
                    soundInfo.getVolume(),
                    soundInfo.getPitch() * pitch,
                    seed
            );
        });
        if (!filteredMappings.isEmpty()) {
            return;
        }
        world.playSound(null, x, y, z, sound, SoundCategory.RECORDS, volume, pitch, seed);
    }
}
