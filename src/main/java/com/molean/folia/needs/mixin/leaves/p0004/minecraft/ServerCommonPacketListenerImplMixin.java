package com.molean.folia.needs.mixin.leaves.p0004.minecraft;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerCommonPacketListenerImpl;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerCommonPacketListenerImpl.class)
public class ServerCommonPacketListenerImplMixin {
    @Shadow
    @Final
    protected ServerPlayer player;

    @Inject(method = "handleCustomPayload", at = @At("HEAD"))
    public void on(ServerboundCustomPayloadPacket packet, CallbackInfo ci) {
        // Leaves start - protocol
        if (packet.payload() instanceof org.leavesmc.leaves.protocol.core.LeavesCustomPayload leavesPayload) {
            org.leavesmc.leaves.protocol.core.LeavesProtocolManager.handlePayload(player, leavesPayload);
            return;
        }
        if (packet.payload() instanceof net.minecraft.network.protocol.common.custom.DiscardedPayload(
                net.minecraft.resources.ResourceLocation id, byte[] data
        )) {
            if (org.leavesmc.leaves.protocol.core.LeavesProtocolManager.handleBytebuf(player, id, io.netty.buffer.Unpooled.wrappedBuffer(data))) {
                return;
            }
        }
        // Leaves end - protocol
    }

    @Inject(method = "readChannelIdentifier", at = @At(value = "INVOKE", target = "Lorg/bukkit/craftbukkit/entity/CraftPlayer;addChannel(Ljava/lang/String;)V"))
    public void on(byte[] data, int from, int _to, boolean register, CallbackInfo ci, @Local(name = "channel") String channel) {
        org.leavesmc.leaves.protocol.core.LeavesProtocolManager.handleMinecraftRegister(channel, player); // Leaves - protocol
    }
}
