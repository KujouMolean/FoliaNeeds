package com.molean.folia.needs.mixin.leaves.p0027;

import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelChunk.class)
public class LevelChunkMixin {
    @Inject(method = "setLoaded", at = @At("TAIL"))
    public void on(boolean loaded, CallbackInfo ci) {
        // Leaves start - bbor
        if (loaded) {
            org.leavesmc.leaves.protocol.BBORProtocol.onChunkLoaded((LevelChunk) (Object) (this));
        }
        // Leaves end - bbor
    }
}
