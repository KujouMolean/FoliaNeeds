package com.molean.folia.needs.mixin.leaves.p0004.minecraft;

import net.kyori.adventure.text.Component;
import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.server.players.PlayerList;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.storage.ValueInput;
import org.bukkit.Location;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(PlayerList.class)
public class C3_PlayerListMixin {
    @Inject(method = "placeNewPlayer", at = @At(value = "INVOKE", target = "Lorg/bukkit/event/player/PlayerJoinEvent;joinMessage()Lnet/kyori/adventure/text/Component;"))
    public void on(Connection connection, ServerPlayer player, CommonListenerCookie cookie, ProblemReporter.ScopedCollector scopedCollector, Optional<ValueInput> optional1, String string, Location selectedSpawn, CallbackInfo ci) {
        org.leavesmc.leaves.protocol.core.LeavesProtocolManager.handlePlayerJoin(player);
    }

    @Inject(method = "remove(Lnet/minecraft/server/level/ServerPlayer;Lnet/kyori/adventure/text/Component;)Lnet/kyori/adventure/text/Component;", at = @At("HEAD"))
    public void on(ServerPlayer player, Component leaveMessage, CallbackInfoReturnable<Component> cir) {
        org.leavesmc.leaves.protocol.core.LeavesProtocolManager.handlePlayerLeave(player); // Leaves - protocol
    }

    @Inject(method = "reloadRecipes", at = @At("TAIL"))
    public void on(CallbackInfo ci) {
        org.leavesmc.leaves.protocol.core.LeavesProtocolManager.handleDataPackReload(); // Leaves - protocol core
    }
}
