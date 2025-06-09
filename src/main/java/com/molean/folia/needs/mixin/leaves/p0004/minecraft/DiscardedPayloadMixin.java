package com.molean.folia.needs.mixin.leaves.p0004.minecraft;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.DiscardedPayload;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DiscardedPayload.class)
public class DiscardedPayloadMixin {
    @Inject(method = "lambda$codec$0", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/FriendlyByteBuf;writeBytes([B)Lnet/minecraft/network/FriendlyByteBuf;"), cancellable = true)
    private static void on(DiscardedPayload value, FriendlyByteBuf output, CallbackInfo ci) {
        if (value.data() == null) {
            ci.cancel();
        }
    }
}
