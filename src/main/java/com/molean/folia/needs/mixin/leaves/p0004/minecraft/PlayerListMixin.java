package com.molean.folia.needs.mixin.leaves.p0004.minecraft;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.authlib.GameProfile;
import com.molean.folia.needs.meta.ConnectionHolder;
import net.kyori.adventure.text.Component;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.server.network.ServerLoginPacketListenerImpl;
import net.minecraft.server.players.PlayerList;
import org.bukkit.Location;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(PlayerList.class)
public class PlayerListMixin {
    @Inject(method = "placeNewPlayer", at = @At(value = "INVOKE", target = "Lorg/bukkit/event/player/PlayerJoinEvent;joinMessage()Lnet/kyori/adventure/text/Component;"))
    public void on(Connection connection, ServerPlayer player, CommonListenerCookie cookie, Optional<CompoundTag> optional, String string, Location selectedSpawn, CallbackInfo ci) {
        org.leavesmc.leaves.protocol.core.LeavesProtocolManager.handlePlayerJoin(player); // Leaves - protocol
    }

    @Inject(method = "remove(Lnet/minecraft/server/level/ServerPlayer;Lnet/kyori/adventure/text/Component;)Lnet/kyori/adventure/text/Component;", at = @At("HEAD"))
    public void on(ServerPlayer player, Component leaveMessage, CallbackInfoReturnable<Component> cir) {
        org.leavesmc.leaves.protocol.core.LeavesProtocolManager.handlePlayerLeave(player); // Leaves - protocol
    }

    @Inject(method = "canPlayerLogin", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;getBukkitEntity()Lorg/bukkit/craftbukkit/entity/CraftPlayer;"))
    public void on(ServerLoginPacketListenerImpl loginlistener, GameProfile gameProfile, CallbackInfoReturnable<ServerPlayer> cir, @Local ServerPlayer entity) {
        ConnectionHolder connectionHolder = (ConnectionHolder) entity;
        connectionHolder.setInternalConnection(loginlistener.connection); // Leaves - protocol core
    }

    @Inject(method = "reloadRecipes", at = @At("TAIL"))
    public void on(CallbackInfo ci) {
        org.leavesmc.leaves.protocol.core.LeavesProtocolManager.handleDataPackReload(); // Leaves - protocol core
    }
}
