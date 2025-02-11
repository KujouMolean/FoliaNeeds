package com.molean.folia.needs.mixin.poster;

import ca.spottedleaf.concurrentutil.completable.CallbackCompletable;
import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ServerPlayer.class)
public abstract class PlayerPostRespawnEventPoster {
    @Shadow public abstract CraftPlayer getBukkitEntity();

    @Inject(method = "lambda$respawn$29", at = @At("TAIL"))
    public void on(ServerLevel respawnWorld, BlockPos respawnPos, float respawnAngle, boolean isRespawnForced, boolean alive, CallbackCompletable spawnPosComplete, boolean[] usedRespawnAnchor, List chunks, CallbackInfo ci) {
        new PlayerPostRespawnEvent(getBukkitEntity(), getBukkitEntity().getLocation(), false).callEvent();
    }
}
