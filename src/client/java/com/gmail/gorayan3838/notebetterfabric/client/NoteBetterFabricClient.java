package com.gmail.gorayan3838.notebetterfabric.client;

import com.gmail.gorayan3838.notebetterfabric.config.SoundConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class NoteBetterFabricClient implements ClientModInitializer {

    public static final String modID = "notebetterfabric";

    public static SoundConfig CONFIG;

    @Override
    public void onInitializeClient() {
        AutoConfig.register(SoundConfig.class, GsonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(SoundConfig.class).get();
        registerCommand();
    }

    public void registerCommand() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            var builder = ClientCommandManager.literal("notebetter")
                    .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2))
                    .then(ClientCommandManager.literal("reload")
                            .executes(context -> {
                                AutoConfig.getConfigHolder(SoundConfig.class).load();
                                CONFIG = AutoConfig.getConfigHolder(SoundConfig.class).get();
                                context.getSource().sendFeedback(Text.translatable("commands.notebetterfabric.reload.success"));
                                return 1;
                            })
                    );
            dispatcher.register(builder);
        });
    }
}
