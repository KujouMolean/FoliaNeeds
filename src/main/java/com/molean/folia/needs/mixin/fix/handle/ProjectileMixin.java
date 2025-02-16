package com.molean.folia.needs.mixin.fix.handle;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Projectile.class)
public class ProjectileMixin {



    // 移除检查后可能会造成并发问题，但目前没有好的办法，如果不去掉检测，部分功能无法实现。

    @Redirect(method = "getOwnerRaw", at = @At(value = "INVOKE", target = "Lca/spottedleaf/moonrise/common/util/TickThread;ensureTickThread(Lnet/minecraft/world/entity/Entity;Ljava/lang/String;)V"))
    public void on(Entity entity, String reason) {
        // empty
    }
}
