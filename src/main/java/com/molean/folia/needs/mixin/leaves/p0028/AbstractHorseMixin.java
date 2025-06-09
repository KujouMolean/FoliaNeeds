package com.molean.folia.needs.mixin.leaves.p0028;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractHorse.class)
public class AbstractHorseMixin {
    @Shadow public SimpleContainer inventory;

    @Inject(method = "createInventory", at = @At(value = "TAIL"))
    public void on(CallbackInfo ci) {
        // Leaves start - pca
        this.inventory.addListener(inv -> {
            if (org.leavesmc.leaves.LeavesConfig.protocol.pca.enable) {
                org.leavesmc.leaves.protocol.PcaSyncProtocol.syncEntityToClient((AbstractHorse) (Object) (this));
            }
        });
        // Leaves end - pca
    }
}
