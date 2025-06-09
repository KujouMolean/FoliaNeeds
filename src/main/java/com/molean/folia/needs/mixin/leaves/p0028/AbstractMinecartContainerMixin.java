package com.molean.folia.needs.mixin.leaves.p0028;

import net.minecraft.world.entity.vehicle.AbstractMinecartContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractMinecartContainer.class)
public class AbstractMinecartContainerMixin {
    @Inject(method = "setChanged", at = @At("HEAD"))
    public void on(CallbackInfo ci) {
        // Leaves start - pca
        if (org.leavesmc.leaves.LeavesConfig.protocol.pca.enable) {
            org.leavesmc.leaves.protocol.PcaSyncProtocol.syncEntityToClient((AbstractMinecartContainer) (Object) this);
        }
        // Leaves end - pca
    }
}
