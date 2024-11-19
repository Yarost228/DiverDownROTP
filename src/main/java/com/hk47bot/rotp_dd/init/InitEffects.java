package com.hk47bot.rotp_dd.init;

import com.hk47bot.rotp_dd.RotpDiverDownAddon;
import com.hk47bot.rotp_dd.effects.BoneShardsBombEffect;
import com.hk47bot.rotp_dd.effects.SpringLegsEffect;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitEffects {
    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, RotpDiverDownAddon.MOD_ID);

    public static final RegistryObject<Effect> SPRING_LEGS = EFFECTS.register ("spring_legs",
            () -> new SpringLegsEffect(EffectType.HARMFUL, 0x404040));
    public static final RegistryObject<Effect> BONE_SHARDS_BOMB = EFFECTS.register ("bone_shard_bomb",
            () -> new BoneShardsBombEffect(EffectType.HARMFUL, 0x404040));
}
