package com.molean.folia.needs.mixin.poster;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerRespawnEvent.class)
public class PlayerRespawnEventMixin {
    @Inject(method = "isBedSpawn", at = @At("HEAD"))
    public void on1(CallbackInfoReturnable<Boolean> cir) {
        throw new UnsupportedOperationException();
    }
    @Inject(method = "isAnchorSpawn", at = @At("HEAD"))
    public void on2(CallbackInfoReturnable<Boolean> cir) {
        throw new UnsupportedOperationException();
    }
}

