package com.hk47bot.rotp_dd.init;

import com.hk47bot.rotp_dd.RotpDiverDownAddon;

import com.hk47bot.rotp_dd.entity.projectile.BoneShardEntity;
import com.hk47bot.rotp_dd.entity.trap.KineticTrapEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, RotpDiverDownAddon.MOD_ID);

    public static final RegistryObject<EntityType<KineticTrapEntity>> KINETIC_TRAP = ENTITIES.register("kinetic_trap",
            () -> EntityType.Builder.<KineticTrapEntity>of(KineticTrapEntity::new, EntityClassification.MISC).sized(0.3F, 0.3F).setUpdateInterval(20).fireImmune()
                    .build(new ResourceLocation(RotpDiverDownAddon.MOD_ID, "kinetic_trap").toString()));
    public static final RegistryObject<EntityType<BoneShardEntity>> BONE_SHARD = ENTITIES.register("bone_shard",
            () -> EntityType.Builder.<BoneShardEntity>of(BoneShardEntity::new, EntityClassification.MISC).sized(0.2F, 0.15F).fireImmune().clientTrackingRange(4).updateInterval(20)
                    .build(new ResourceLocation(RotpDiverDownAddon.MOD_ID, "bone_shard").toString()));
};
