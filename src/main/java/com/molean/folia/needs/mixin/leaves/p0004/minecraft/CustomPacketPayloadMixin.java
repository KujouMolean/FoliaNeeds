package com.molean.folia.needs.mixin.leaves.p0004.minecraft;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.network.protocol.common.custom.CustomPacketPayload$1")
public abstract class CustomPacketPayloadMixin {

    @Shadow protected abstract StreamCodec<FriendlyByteBuf, ? extends CustomPacketPayload> findCodec(ResourceLocation par1);

    @Inject(method = "encode(Lnet/minecraft/network/FriendlyByteBuf;Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload;)V", at = @At("HEAD"), cancellable = true)
    public void on(FriendlyByteBuf buffer, CustomPacketPayload value, CallbackInfo ci) {
        // Leaves start - protocol core
        if (value instanceof org.leavesmc.leaves.protocol.core.LeavesCustomPayload payload) {
            org.leavesmc.leaves.protocol.core.LeavesProtocolManager.encode(buffer, payload);
            ci.cancel();
        }
        // Leaves end - protocol core
    }

    @Inject(method = "decode(Lnet/minecraft/network/FriendlyByteBuf;)Lnet/minecraft/network/protocol/common/custom/CustomPacketPayload;", at = @At("HEAD"), cancellable = true)
    public void on(FriendlyByteBuf buffer, CallbackInfoReturnable<CustomPacketPayload> cir) {
        ResourceLocation resourceLocation = buffer.readResourceLocation();
        // Leaves start - protocol core
        var payload = org.leavesmc.leaves.protocol.core.LeavesProtocolManager.decode(resourceLocation, buffer);
        // Leaves end - protocol core
        cir.setReturnValue(java.util.Objects.requireNonNullElseGet(payload, () -> this.findCodec(resourceLocation).decode(buffer)));
    }
}
