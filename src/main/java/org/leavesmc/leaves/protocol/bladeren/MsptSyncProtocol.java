package org.leavesmc.leaves.protocol.bladeren;

import io.papermc.paper.threadedregions.*;
import io.papermc.paper.threadedregions.commands.CommandUtil;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.leavesmc.leaves.LeavesConfig;
import org.leavesmc.leaves.protocol.core.LeavesProtocol;
import org.leavesmc.leaves.protocol.core.ProtocolHandler;
import org.leavesmc.leaves.protocol.core.ProtocolUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;

@LeavesProtocol.Register(namespace = "bladeren")
public class MsptSyncProtocol implements LeavesProtocol {

    public static final String PROTOCOL_ID = "bladeren";
    private static final ResourceLocation MSPT_SYNC = id("mspt_sync");
    private static final List<ServerPlayer> players = new ArrayList<>();

    @Contract("_ -> new")
    public static ResourceLocation id(String path) {
        return ResourceLocation.tryBuild(PROTOCOL_ID, path);
    }

    @ProtocolHandler.Init
    public static void init() {
        BladerenProtocol.registerFeature("mspt_sync", (player, compoundTag) -> {
            if (compoundTag.getStringOr("Value", "").equals("true")) {
                onPlayerSubmit(player);
            } else {
                onPlayerLoggedOut(player);
            }
        });
    }

    @ProtocolHandler.PlayerLeave
    public static void onPlayerLoggedOut(@NotNull ServerPlayer player) {
        players.remove(player);
    }

    @ProtocolHandler.Ticker
    public static void tick() {
        if (players.isEmpty()) {
            return;
        }
        players.forEach(serverPlayer -> {
            serverPlayer.getBukkitEntity().getScheduler().run(Bukkit.getPluginManager().getPlugins()[0], scheduledTask -> {
                ThreadedRegionizer.ThreadedRegion<TickRegions.TickRegionData, TickRegions.TickRegionSectionData> currentRegion = TickRegionScheduler.getCurrentRegion();
                if (currentRegion == null) {
                    return;
                }
                TickData.TickReportData reportData = currentRegion.getData().getRegionSchedulingHandle().getTickReport5s(System.nanoTime());
                if (reportData == null) {
                    return;
                }
                TickData.SegmentedAverage segmentedAverage = reportData.tpsData();
                if (segmentedAverage == null) {
                    return;
                }
                final double tps = segmentedAverage.segmentAll().average();
                final double mspt = reportData.timePerTickData().segmentAll().average() / 1.0E6;
                ProtocolUtils.sendBytebufPacket(serverPlayer, MSPT_SYNC, buf -> {
                    buf.writeDouble(mspt);
                    buf.writeDouble(tps);
                });
            }, null);

        });


    }

    public static void onPlayerSubmit(@NotNull ServerPlayer player) {
        players.add(player);
    }

    @Override
    public int tickerInterval(String tickerID) {
        return LeavesConfig.protocol.bladeren.msptSyncTickInterval;
    }

    @Override
    public boolean isActive() {
        return LeavesConfig.protocol.bladeren.msptSyncProtocol;
    }
}
