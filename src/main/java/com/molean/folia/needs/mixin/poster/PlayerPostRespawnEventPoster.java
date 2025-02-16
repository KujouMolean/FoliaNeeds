package com.molean.folia.needs.mixin.poster;

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.function.Consumer;

@Mixin(ServerPlayer.class)
public abstract class PlayerPostRespawnEventPoster {
    @Shadow
    public abstract CraftPlayer getBukkitEntity();

    //做一个简单的call，满足插件的基本功能实现。
    @ModifyVariable(index = 1, method = "respawn(Ljava/util/function/Consumer;Lorg/bukkit/event/player/PlayerRespawnEvent$RespawnReason;)V", at = @At("HEAD"), argsOnly = true)
    public Consumer<ServerPlayer> on(Consumer<ServerPlayer> value) {
        new PlayerPostRespawnEvent(getBukkitEntity(), getBukkitEntity().getLocation(), false).callEvent();
        return value;
    }
}
