package com.molean.folia.needs.meta;

public interface ConnectionHolder {
    public void setInternalConnection(net.minecraft.network.Connection connection);

    public net.minecraft.network.Connection getInternalConnection();

}
