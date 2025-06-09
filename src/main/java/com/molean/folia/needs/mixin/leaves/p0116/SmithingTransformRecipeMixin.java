package com.molean.folia.needs.mixin.leaves.p0116;

import com.molean.folia.needs.meta.REIResultHolder;
import net.minecraft.world.item.crafting.SmithingTransformRecipe;
import net.minecraft.world.item.crafting.TransmuteResult;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SmithingTransformRecipe.class)
public class SmithingTransformRecipeMixin implements REIResultHolder {
    @Shadow @Final private TransmuteResult result;

    // Leaves start - REI
    public SlotDisplay getResult() {
        return this.result.display();
    }
    // Leaves end - REI
}
