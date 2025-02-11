package com.molean.folia.needs.mixin.poster;

import io.papermc.paper.entity.TeleportFlag;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Mixin(CraftEntity.class)
public abstract class TeleportAsyncCallEventPoster {


    @Shadow public abstract Location getLocation();

    @ModifyVariable(method = "teleportAsync", at = @At(value = "HEAD"), argsOnly = true, index = 1)
    public Location modifyVariable(Location value) {
        return value.clone();
    }


    @Inject(method = "teleportAsync", at = @At(value = "HEAD"))
    public void callEvent(Location location, PlayerTeleportEvent.TeleportCause cause, TeleportFlag[] teleportFlags, CallbackInfoReturnable<CompletableFuture<Boolean>> cir) {
        EntityTeleportEvent entityTeleportEvent = new EntityTeleportEvent((Entity) (Object) this, getLocation(), location.clone());
        Bukkit.getPluginManager().callEvent(entityTeleportEvent);
        if (Objects.equals(entityTeleportEvent.getTo(), location)) {
            location.setWorld(entityTeleportEvent.getTo().getWorld());
            location.setX(entityTeleportEvent.getTo().getX());
            location.setY(entityTeleportEvent.getTo().getY());
            location.setZ(entityTeleportEvent.getTo().getZ());
            location.setYaw(entityTeleportEvent.getTo().getYaw());
            location.setPitch(entityTeleportEvent.getTo().getPitch());
        }
        if (entityTeleportEvent.isCancelled()) {
            cir.cancel();
        }
    }
}
