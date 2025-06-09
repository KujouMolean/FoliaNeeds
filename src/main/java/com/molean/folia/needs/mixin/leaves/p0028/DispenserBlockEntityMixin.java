package com.molean.folia.needs.mixin.leaves.p0028;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(DispenserBlockEntity.class)
public abstract class DispenserBlockEntityMixin extends RandomizableContainerBlockEntity {

    protected DispenserBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    // Leaves start - pca
    @Override
    public void setChanged() {
        super.setChanged();
        if (org.leavesmc.leaves.LeavesConfig.protocol.pca.enable) {
            org.leavesmc.leaves.protocol.PcaSyncProtocol.syncBlockEntityToClient(this);
        }
    }
    // Leaves end - pca
}
