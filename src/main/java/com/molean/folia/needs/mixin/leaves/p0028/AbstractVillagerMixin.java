package com.molean.folia.needs.mixin.leaves.p0028;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractVillager.class)
public abstract class AbstractVillagerMixin extends AgeableMob {

    @Shadow @Final private SimpleContainer inventory;

    protected AbstractVillagerMixin(EntityType<? extends AgeableMob> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    public void on(EntityType entityType, Level level, CallbackInfo ci) {
        // Leaves start - pca
        if (!this.level().isClientSide()) {
            this.inventory.addListener(inventory -> {
                if (org.leavesmc.leaves.LeavesConfig.protocol.pca.enable) {
                    org.leavesmc.leaves.protocol.PcaSyncProtocol.syncEntityToClient(this);
                }
            });
        }
        // Leaves end - pca
    }
}
