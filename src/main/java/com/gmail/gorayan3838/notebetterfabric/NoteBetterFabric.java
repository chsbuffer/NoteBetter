package com.gmail.gorayan3838.notebetterfabric;

import com.gmail.gorayan3838.notebetterfabric.config.SoundConfig;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.TranslatableText;

public class NoteBetterFabric implements ModInitializer {

    public static final String modID = "notebetterfabric";

    public static SoundConfig CONFIG;

    @Override
    public void onInitialize() {
        AutoConfig.register(SoundConfig.class, GsonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(SoundConfig.class).get();
        registerCommand();
    }

    public void registerCommand() {
        CommandRegistrationCallback.EVENT.register((dispatcher, b) -> {
            LiteralArgumentBuilder builder = CommandManager.literal("notebetter")
                    .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2))
                    .then(CommandManager.literal("reload")
                            .executes(context -> {
                                AutoConfig.getConfigHolder(SoundConfig.class).load();
                                CONFIG = AutoConfig.getConfigHolder(SoundConfig.class).get();
                                context.getSource().sendFeedback(new TranslatableText("commands.notebetterfabric.reload.success"), true);
                                return 1;
                            })
                    );
            dispatcher.register(builder);
        });
    }
}
