package org.leavesmc.leaves.protocol.bladeren;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.papermc.paper.threadedregions.ThreadedRegionizer;
import io.papermc.paper.threadedregions.TickData;
import io.papermc.paper.threadedregions.TickRegionScheduler;
import io.papermc.paper.threadedregions.TickRegions;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.leavesmc.leaves.LeavesConfig;
import org.leavesmc.leaves.protocol.core.LeavesProtocol;
import org.leavesmc.leaves.protocol.core.ProtocolHandler;
import org.leavesmc.leaves.protocol.core.ProtocolUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

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


    private static final Cache<Long, Pair<Double, Double>> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(5, TimeUnit.SECONDS)
            .build();

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
                long id = currentRegion.id;
                Pair<Double, Double> data = Pair.of(0d, 0d);
                try {
                    data = cache.get(id, () -> {
                        TickData.TickReportData reportData = currentRegion.getData().getRegionSchedulingHandle().getTickReport5s(System.nanoTime());
                        if (reportData == null) {
                            return Pair.of(0d, 0d);
                        }
                        TickData.SegmentedAverage segmentedAverage = reportData.tpsData();
                        if (segmentedAverage == null) {
                            return Pair.of(0d, 0d);
                        }
                        final double tps = segmentedAverage.segmentAll().average();
                        final double mspt = reportData.timePerTickData().segmentAll().average() / 1.0E6;
                        return Pair.of(tps, mspt);

                    });
                } catch (ExecutionException ignored) {
                }
                Pair<Double, Double> finalData = data;
                ProtocolUtils.sendBytebufPacket(serverPlayer, MSPT_SYNC, buf -> {
                    buf.writeDouble(finalData.value());
                    buf.writeDouble(finalData.key());
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
