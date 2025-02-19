package com.molean.folia.needs.mixin.fix.piston;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Entity.class)
public class EntityMixin {
    @Redirect(method = "checkInsideBlocks", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"))
    public BlockState on(Level instance, BlockPos chunk) {
        if (!ca.spottedleaf.moonrise.common.util.TickThread.isTickThreadFor(instance, chunk)) {
            return Blocks.AIR.defaultBlockState();
        }
        return instance.getBlockState(chunk);
    }
}
