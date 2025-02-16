package com.molean.folia.needs.mixin.fix.entities;

import io.papermc.paper.configuration.WorldConfiguration;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.entity.LevelEntityGetter;
import net.minecraft.world.level.storage.WritableLevelData;
import org.bukkit.World;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.spigotmc.SpigotWorldConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.Executor;
import java.util.function.Function;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin extends Level {


    protected ServerLevelMixin(WritableLevelData worlddatamutable, ResourceKey<Level> resourcekey, RegistryAccess iregistrycustom, Holder<DimensionType> holder, boolean flag, boolean flag1, long i, int j, ChunkGenerator gen, BiomeProvider biomeProvider, World.Environment env, Function<SpigotWorldConfig, WorldConfiguration> paperWorldConfigCreator, Executor executor) {
        super(worlddatamutable, resourcekey, iregistrycustom, holder, flag, flag1, i, j, gen, biomeProvider, env, paperWorldConfigCreator, executor);
    }

    //无意义的检查，EntityLookup是支持并发的。
    @Inject(method = "getEntities()Lnet/minecraft/world/level/entity/LevelEntityGetter;", at = @At("HEAD"), cancellable = true)
    public void on(CallbackInfoReturnable<LevelEntityGetter<Entity>> cir) {
        cir.setReturnValue(this.moonrise$getEntityLookup());
    }
}
