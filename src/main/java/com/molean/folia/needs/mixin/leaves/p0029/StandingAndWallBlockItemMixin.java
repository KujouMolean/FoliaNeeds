package com.molean.folia.needs.mixin.leaves.p0029;

import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(StandingAndWallBlockItem.class)
public class StandingAndWallBlockItemMixin {
    @Redirect(method = "getPlacementState", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;getStateForPlacement(Lnet/minecraft/world/item/context/BlockPlaceContext;)Lnet/minecraft/world/level/block/state/BlockState;"))
    public BlockState on(Block instance, BlockPlaceContext context) {
        return getRealStateForPlacement(instance, context);
    }

    // Leaves start - alternativeBlockPlacement
    @Nullable
    public BlockState getRealStateForPlacement(Block instance, BlockPlaceContext ctx) {
        BlockState vanillaState = instance.getStateForPlacement(ctx);
        switch (org.leavesmc.leaves.LeavesConfig.protocol.alternativeBlockPlacement) {
            case CARPET -> {
                BlockState tryState = org.leavesmc.leaves.protocol.CarpetAlternativeBlockPlacement.alternativeBlockPlacement(instance, ctx);
                if (tryState != null) {
                    return tryState;
                }
            }
            case CARPET_FIX -> {
                BlockState tryState = org.leavesmc.leaves.protocol.CarpetAlternativeBlockPlacement.alternativeBlockPlacementFix(instance, ctx);
                if (tryState != null) {
                    return tryState;
                }
            }
            case LITEMATICA -> {
                if (vanillaState != null) {
                    return org.leavesmc.leaves.protocol.LitematicaEasyPlaceProtocol.applyPlacementProtocol(vanillaState, ctx);
                }
            }
        }
        return vanillaState;
    }
    // Leaves end - alternativeBlockPlacement
}
