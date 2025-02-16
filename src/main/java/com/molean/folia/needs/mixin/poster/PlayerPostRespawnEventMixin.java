package com.molean.folia.needs.mixin.poster;

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerPostRespawnEvent.class)
public class PlayerPostRespawnEventMixin {
    //无法实现的API，最好不让调用，否则可能表现不一致。
    @Inject(method = "isBedSpawn", at = @At("HEAD"))
    public void on(CallbackInfoReturnable<Boolean> cir) {
        throw new UnsupportedOperationException();
    }
}
