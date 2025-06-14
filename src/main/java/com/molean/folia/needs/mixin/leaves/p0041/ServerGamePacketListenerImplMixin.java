package com.molean.folia.needs.mixin.leaves.p0041;

import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin {
    @Redirect(method = "handleUseItemOn", at = @At(value = "INVOKE", target = "Ljava/lang/Math;abs(D)D"))
    public double on(double a) {
        if (org.leavesmc.leaves.LeavesConfig.modify.disableDistanceCheckForUseItem) {
            return 0;
        }
        return Math.abs(a);
    }
}
