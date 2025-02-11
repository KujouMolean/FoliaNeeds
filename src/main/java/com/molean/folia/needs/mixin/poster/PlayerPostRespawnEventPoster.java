package com.molean.folia.needs.mixin.poster;

import ca.spottedleaf.concurrentutil.completable.CallbackCompletable;
import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerPlayer.class)
public abstract class PlayerPostRespawnEventPoster {
    @Shadow
    public abstract CraftPlayer getBukkitEntity();

    @Redirect(method = "lambda$respawn$29", at = @At(value = "INVOKE", target = "Lca/spottedleaf/concurrentutil/completable/CallbackCompletable;complete(Ljava/lang/Object;)V"))
    public void on(CallbackCompletable instance, Object result) {
        instance.complete(result);
        new PlayerPostRespawnEvent(getBukkitEntity(), getBukkitEntity().getLocation(), false).callEvent();
    }
}
