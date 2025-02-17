package com.molean.folia.needs.mixin.fix.level;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLevel.EntityCallbacks.class)
public class ServerLevelMixinEntityCallbacksMixin {
    @Inject(method = "onTickingEnd(Lnet/minecraft/world/entity/Entity;)V", at = @At("HEAD"), cancellable = true)
    public void on(Entity entity, CallbackInfo ci) {
        if (!MinecraftServer.getServer().isRunning()) {
            ci.cancel();
        }
    }
}
