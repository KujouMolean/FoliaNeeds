package com.molean.folia.needs.mixin.fix.handle;

import ca.spottedleaf.moonrise.common.util.TickThread;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

@Mixin(TickThread.class)
public class TickThreadMixin {
    @Inject(method = "ensureTickThread*", at = @At("HEAD"), cancellable = true)
    private static void on(Entity entity, String reason, CallbackInfo ci) {
        if (Arrays.stream(Thread.currentThread().getStackTrace()).anyMatch(stackTraceElement -> stackTraceElement.getMethodName().equals("getHandle"))) {
            ci.cancel();
        }
    }
}
