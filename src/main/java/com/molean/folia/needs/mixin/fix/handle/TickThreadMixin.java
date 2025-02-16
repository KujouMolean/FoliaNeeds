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


    // 移除过度检查，在PacketLevel需要经常getHandle，仅读取数据，而不作写入，所以去掉检查基本上不影响。

    @Inject(method = "ensureTickThread*", at = @At("HEAD"), cancellable = true)
    private static void on(Entity entity, String reason, CallbackInfo ci) {
        if (reason.equals("Accessing entity state off owning region's thread")) {
            ci.cancel();
        }
    }
}
