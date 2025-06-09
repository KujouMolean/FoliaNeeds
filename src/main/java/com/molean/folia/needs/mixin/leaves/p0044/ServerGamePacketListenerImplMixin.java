package com.molean.folia.needs.mixin.leaves.p0044;

import com.molean.folia.needs.meta.ExchangeTargetHolder;
import net.minecraft.network.Connection;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.leavesmc.leaves.protocol.syncmatica.exchange.ExchangeTarget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin implements ExchangeTargetHolder {
    private org.leavesmc.leaves.protocol.syncmatica.exchange.ExchangeTarget exchangeTarget;
    @Inject(method = "<init>", at = @At("TAIL"))
    public void on(MinecraftServer server, Connection connection, ServerPlayer player, CommonListenerCookie cookie, CallbackInfo ci) {
        this.exchangeTarget = new org.leavesmc.leaves.protocol.syncmatica.exchange.ExchangeTarget((ServerGamePacketListenerImpl) (Object) this); // Leaves - Syncmatica Protocol
    }

    @Override
    public void setExchangeTarget(ExchangeTarget exchangeTarget) {
        this.exchangeTarget = exchangeTarget;
    }

    @Override
    public ExchangeTarget getExchangeTarget() {
        return exchangeTarget;
    }
}
