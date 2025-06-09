package com.molean.folia.needs.mixin.leaves.p0113;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerList.class)
public class PlayerListMixin {
    @Inject(method = "sendLevelInfo", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;isRaining()Z"))
    public void on(ServerPlayer player, ServerLevel level, CallbackInfo ci) {
        org.leavesmc.leaves.protocol.XaeroMapProtocol.onSendWorldInfo(player); // Leaves - xaero map protocol
    }
}
