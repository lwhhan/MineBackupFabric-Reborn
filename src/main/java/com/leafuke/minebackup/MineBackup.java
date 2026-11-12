package com.leafuke.minebackup;

import com.leafuke.minebackup.command.Command;
import com.leafuke.minebackup.api.v2.MineBackupApi;
import com.leafuke.minebackup.api.v2.CurrentWorldAutomationMode;
import com.leafuke.minebackup.client.MineBackupClient;
import com.leafuke.minebackup.knotlink.KnotLinkClient;
import com.leafuke.minebackup.runtime.MineBackupRuntime;
import com.leafuke.minebackup.runtime.AutoBackupUpdateResult;
import com.leafuke.minebackup.runtime.AutomationUpdateResult;
import com.leafuke.minebackup.runtime.RestoreControlResult;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public final class MineBackup implements ModInitializer {
    public static final String MOD_ID = "minebackup";
    public static final String PLUGIN_GUIDE_URL = "https://modrinth.com/plugin/minebackupplugin";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static final MineBackupRuntime RUNTIME = new MineBackupRuntime();

    @Override
    public void onInitialize() {
        RUNTIME.registerEvents();

        // Register commands via Fabric's command registration callback.
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
                Command.register(dispatcher));

        // Initialize client on client dist
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            MineBackupClient.initialize();
        }

        LOGGER.info("MineBackup {} initialized with KnotLink v2.", ModInfo.version());
    }

    public static KnotLinkClient knotLink() {
        return RUNTIME.knotLink();
    }

    public static MineBackupApi api() {
        return RUNTIME;
    }

    public static RestoreControlResult confirmPendingRestore() {
        return RUNTIME.confirmPendingRestore();
    }

    public static RestoreControlResult cancelPendingRestore() {
        return RUNTIME.cancelPendingRestore();
    }

    public static AutoBackupUpdateResult startAutomaticBackup(Duration interval) {
        return RUNTIME.startAutomaticBackup(interval);
    }

    public static AutoBackupUpdateResult stopAutomaticBackup() {
        return RUNTIME.stopAutomaticBackup();
    }

    public static AutomationUpdateResult startCurrentWorldAutomation(
            Duration interval,
            CurrentWorldAutomationMode mode) {
        return RUNTIME.startCurrentWorldAutomation(interval, mode);
    }

    public static AutomationUpdateResult stopCurrentWorldAutomation() {
        return RUNTIME.stopCurrentWorldAutomation();
    }

    public static void completeClientRestore(boolean success, String reason) {
        RUNTIME.completeClientRestore(success, reason);
    }

    public static void shutdownClient() {
        RUNTIME.close();
    }

    public static MutableComponent pluginLinkMessage() {
        return Component.translatable("minebackup.message.plugin_link_prefix")
                .append(Component.literal(PLUGIN_GUIDE_URL).withStyle(style -> style
                        .withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,
                                PLUGIN_GUIDE_URL))
                        .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                Component.translatable("minebackup.message.plugin_link_hover")))
                        .withUnderlined(true)));
    }
}
