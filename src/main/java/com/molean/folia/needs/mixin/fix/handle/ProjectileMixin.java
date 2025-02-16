package com.molean.folia.needs.mixin.fix.handle;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Projectile.class)
public class ProjectileMixin {
    @Redirect(method = "getOwnerRaw", at = @At(value = "INVOKE", target = "Lca/spottedleaf/moonrise/common/util/TickThread;ensureTickThread(Lnet/minecraft/world/entity/Entity;Ljava/lang/String;)V"))
    public void on(Entity entity, String reason) {
        // empty
    }
}
