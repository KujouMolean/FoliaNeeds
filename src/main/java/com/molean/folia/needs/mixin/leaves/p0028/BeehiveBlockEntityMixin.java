package com.molean.folia.needs.mixin.leaves.p0028;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(BeehiveBlockEntity.class)
public class BeehiveBlockEntityMixin {
    @Inject(method = "addOccupant", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/Bee;discard(Lorg/bukkit/event/entity/EntityRemoveEvent$Cause;)V"))
    public void on(Bee bee, CallbackInfo ci) {
        // Leaves start - pca
        if (org.leavesmc.leaves.LeavesConfig.protocol.pca.enable) {
            org.leavesmc.leaves.protocol.PcaSyncProtocol.syncBlockEntityToClient((BeehiveBlockEntity) (Object) this);
        }
        // Leaves end - pca
    }

    @Inject(method = "tickOccupants", at = @At(value = "INVOKE", target = "Ljava/util/Iterator;remove()V", shift = At.Shift.AFTER))
    private static void on(Level level, BlockPos pos, BlockState state, List data, BlockPos savedFlowerPos, CallbackInfo ci) {
        // Leaves start - pca
        if (org.leavesmc.leaves.LeavesConfig.protocol.pca.enable) {
            org.leavesmc.leaves.protocol.PcaSyncProtocol.syncBlockEntityToClient(java.util.Objects.requireNonNull(level.getBlockEntity(pos)));
        }
        // Leaves end - pca
    }

    @Inject(method = "loadAdditional", at = @At("TAIL"))
    public void on(CompoundTag tag, HolderLookup.Provider registries, CallbackInfo ci) {
        // Leaves start - pca
        if (org.leavesmc.leaves.LeavesConfig.protocol.pca.enable) {
            org.leavesmc.leaves.protocol.PcaSyncProtocol.syncBlockEntityToClient((BeehiveBlockEntity) (Object) this);
        }
        // Leaves end - pca
    }
}
