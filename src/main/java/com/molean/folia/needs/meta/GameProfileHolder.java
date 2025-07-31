package com.molean.folia.needs.meta;

import com.mojang.authlib.GameProfile;

public interface GameProfileHolder {
    public void setGameProfile(GameProfile profile);

    public GameProfile getGameProfile();

}
