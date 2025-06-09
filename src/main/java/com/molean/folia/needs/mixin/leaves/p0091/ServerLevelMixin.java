package com.molean.folia.needs.mixin.leaves.p0091;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLevel.class)
public class ServerLevelMixin {
    @Inject(method = "setDefaultSpawnPos", at = @At("TAIL"))
    public void on(BlockPos pos, float angle, CallbackInfo ci) {
        org.leavesmc.leaves.protocol.servux.ServuxHudDataProtocol.refreshSpawnMetadata = true; // Leaves - servux
    }
}
