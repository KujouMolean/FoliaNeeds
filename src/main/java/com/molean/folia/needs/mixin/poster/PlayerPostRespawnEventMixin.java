package com.molean.folia.needs.mixin.poster;

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerPostRespawnEvent.class)
public class PlayerPostRespawnEventMixin {
    @Inject(method = "isBedSpawn", at = @At("HEAD"))
    public void on(CallbackInfoReturnable<Boolean> cir) {
        throw new UnsupportedOperationException();
    }
}
