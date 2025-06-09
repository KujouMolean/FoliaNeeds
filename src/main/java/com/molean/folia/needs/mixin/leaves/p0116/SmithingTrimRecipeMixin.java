package com.molean.folia.needs.mixin.leaves.p0116;

import com.molean.folia.needs.meta.REIPatternHolder;
import net.minecraft.core.Holder;
import net.minecraft.world.item.crafting.SmithingTrimRecipe;
import net.minecraft.world.item.equipment.trim.TrimPattern;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(SmithingTrimRecipe.class)
public class SmithingTrimRecipeMixin implements REIPatternHolder {
    @Shadow @Final private Holder<TrimPattern> pattern;

    // Leaves start
    public Holder<TrimPattern> pattern() {
        return pattern;
    }
    // Leaves end
}
