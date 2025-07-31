package com.molean.folia.needs.mixin.leaves.p0004.minecraft;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.authlib.GameProfile;
import com.molean.folia.needs.meta.GameProfileHolder;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.server.network.ServerCommonPacketListenerImpl;
import net.minecraft.server.network.ServerConfigurationPacketListenerImpl;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.leavesmc.leaves.protocol.core.LeavesProtocolManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerCommonPacketListenerImpl.class)
public class C2_ServerCommonPacketListenerImplMixin implements GameProfileHolder {
    public GameProfile profile; // Leaves - protocol core

    @Inject(method = "<init>", at = @At("TAIL"))
    private void on(MinecraftServer server, Connection connection, CommonListenerCookie cookie, CallbackInfo ci) {
        this.profile = cookie.gameProfile(); // Leaves - protocol core
    }

    @Inject(method = "handleCustomPayload", at = @At("HEAD"), cancellable = true)
    public void on(ServerboundCustomPayloadPacket packet, CallbackInfo ci) {
        // Leaves start - protocol
        if (packet.payload() instanceof org.leavesmc.leaves.protocol.core.LeavesCustomPayload leavesPayload) {
            org.leavesmc.leaves.protocol.core.LeavesProtocolManager.handlePayload(org.leavesmc.leaves.protocol.core.ProtocolUtils.createSelector((ServerCommonPacketListenerImpl)(Object)this), leavesPayload);
            ci.cancel();
        }
        if (packet.payload() instanceof net.minecraft.network.protocol.common.custom.DiscardedPayload(net.minecraft.resources.ResourceLocation id, byte[] data)) {
            if (org.leavesmc.leaves.protocol.core.LeavesProtocolManager.handleBytebuf(org.leavesmc.leaves.protocol.core.ProtocolUtils.createSelector((ServerCommonPacketListenerImpl)(Object)this), id, io.netty.buffer.Unpooled.wrappedBuffer(data))) {
                ci.cancel();
            }
        }
        // Leaves end - protocol
    }

    @Inject(method = "readChannelIdentifier", at = @At(value = "INVOKE", target = "Lio/papermc/paper/connection/PluginMessageBridgeImpl;addChannel(Ljava/lang/String;)Z"))
    public void on(byte[] data, int from, int _to, boolean register, CallbackInfo ci, @Local(name = "channel") String channel) {
        org.leavesmc.leaves.protocol.core.LeavesProtocolManager.handleMinecraftRegister(channel, org.leavesmc.leaves.protocol.core.ProtocolUtils.createSelector((ServerCommonPacketListenerImpl)(Object)this)); // Leaves - protocol
    }

    @Override
    public void setGameProfile(GameProfile profile) {
        this.profile = profile;
    }

    @Override
    public GameProfile getGameProfile() {
        return profile;
    }
}
