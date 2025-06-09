package com.molean.folia.needs.mixin.leaves.p0004.minecraft;

import io.papermc.paper.threadedregions.RegionizedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RegionizedServer.class)
public abstract class RegionizedServerMixin {
    @Shadow public long tickCount;

    @Inject(method = "globalTick(I)V", at = @At(value = "TAIL"))
    public void on(int tickCount, CallbackInfo ci) {
        org.leavesmc.leaves.protocol.core.LeavesProtocolManager.handleTick(this.tickCount); // Leaves - protocol
    }
}
