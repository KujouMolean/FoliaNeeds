package com.molean.isletopia.ignite.mixin.bugfix.conversation;

import io.papermc.paper.threadedregions.TickRegions;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Queue;
import java.util.function.BooleanSupplier;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Shadow public Queue<Runnable> processQueue;

    @Inject(method = "tickChildren", at = @At("HEAD"))
    public void on(BooleanSupplier shouldKeepTicking, TickRegions.TickRegionData region, CallbackInfo ci) {
        while (!this.processQueue.isEmpty()) { // Folia - region threading
            this.processQueue.remove().run();
        }
    }
}
