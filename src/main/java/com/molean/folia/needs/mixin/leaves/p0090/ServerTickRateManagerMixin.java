package com.molean.folia.needs.mixin.leaves.p0090;

import com.molean.folia.needs.meta.RemainingSprintTicksHolder;
import net.minecraft.server.ServerTickRateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ServerTickRateManager.class)
public abstract class ServerTickRateManagerMixin implements RemainingSprintTicksHolder {
    @Shadow private long remainingSprintTicks;

    // Leaves start - servux
    public long getRemainingSprintTicks() {
        return remainingSprintTicks;
    }
    // Leaves end - servux
}
