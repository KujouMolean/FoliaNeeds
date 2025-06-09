package com.molean.folia.needs.mixin.leaves.p0029;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public abstract class BlockItemMixin {
    @Shadow public abstract Block getBlock();

    @Shadow protected abstract boolean canPlace(BlockPlaceContext context, BlockState state);

    @Inject(method = "getPlacementState", at = @At(value = "HEAD"), cancellable = true)
    public void on(BlockPlaceContext context, CallbackInfoReturnable<BlockState> cir) {
        BlockState stateForPlacement = this.getBlock().getStateForPlacement(context);
        // Leaves start - alternativeBlockPlacement
        switch (org.leavesmc.leaves.LeavesConfig.protocol.alternativeBlockPlacement) {
            case CARPET -> {
                BlockState tryState = org.leavesmc.leaves.protocol.CarpetAlternativeBlockPlacement.alternativeBlockPlacement(getBlock(), context);
                if (tryState != null) {
                    stateForPlacement = tryState;
                }
            }
            case CARPET_FIX -> {
                BlockState tryState = org.leavesmc.leaves.protocol.CarpetAlternativeBlockPlacement.alternativeBlockPlacementFix(getBlock(), context);
                if (tryState != null) {
                    stateForPlacement = tryState;
                }
            }
            case LITEMATICA -> {
                if (stateForPlacement != null && this.canPlace(context, stateForPlacement)) {
                    cir.setReturnValue(org.leavesmc.leaves.protocol.LitematicaEasyPlaceProtocol.applyPlacementProtocol(stateForPlacement, context));
                }
            }
        }
        // Leaves end - alternativeBlockPlacement
        cir.setReturnValue(stateForPlacement != null && this.canPlace(context, stateForPlacement) ? stateForPlacement : null);
    }
}
