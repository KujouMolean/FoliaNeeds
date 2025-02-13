package com.molean.folia.needs.mixin.fix.handle;

import net.minecraft.world.entity.Entity;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CraftEntity.class)
public class CraftEntityMixin {
    @Shadow
    protected Entity entity;

    @Inject(
            method = "getHandle", at = @At("HEAD"), cancellable = true
    )

    public void on(CallbackInfoReturnable<Entity> cir) {
        cir.setReturnValue(this.entity);
    }
}
