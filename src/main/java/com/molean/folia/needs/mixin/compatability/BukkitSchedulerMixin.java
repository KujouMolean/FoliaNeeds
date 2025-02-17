package com.molean.folia.needs.mixin.compatability;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.scheduler.CraftScheduler;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CraftScheduler.class)
public class BukkitSchedulerMixin {
    @Inject(method = "cancelTasks", at = @At(value = "HEAD"), cancellable = true)
    public void on(Plugin plugin, CallbackInfo ci) {
        Bukkit.getGlobalRegionScheduler().cancelTasks(plugin);
        Bukkit.getAsyncScheduler().cancelTasks(plugin);
        ci.cancel();
    }
}
