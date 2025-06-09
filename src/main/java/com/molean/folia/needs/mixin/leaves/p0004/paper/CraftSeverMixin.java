package com.molean.folia.needs.mixin.leaves.p0004.paper;

import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.players.PlayerList;
import org.bukkit.craftbukkit.CraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CraftServer.class)
public class CraftSeverMixin {
    @Inject(method = "<init>", at = @At("TAIL"))
    public void on(DedicatedServer console, PlayerList playerList, CallbackInfo ci) {
        org.leavesmc.leaves.protocol.core.LeavesProtocolManager.init(); // Leaves - protocol
    }

    //handle server reload is ignored, we expect that won't happen
}
