package com.molean.folia.needs.mixin.leaves.p0004.minecraft;

import com.molean.folia.needs.meta.ConnectionHolder;
import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin implements ConnectionHolder {
    private net.minecraft.network.Connection internalConnection;

    @Override
    public void setInternalConnection(Connection connection) {
        this.internalConnection = connection;
    }

    @Override
    public Connection getInternalConnection() {
        return internalConnection;
    }
}
