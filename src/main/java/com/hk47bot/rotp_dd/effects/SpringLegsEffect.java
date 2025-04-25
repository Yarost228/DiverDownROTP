package com.hk47bot.rotp_dd.effects;

import com.github.standobyte.jojo.potion.UncurableEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;

public class SpringLegsEffect extends UncurableEffect {
    public SpringLegsEffect(EffectType type, int color){super(type, color);}
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.level.isClientSide() && entity.isOnGround()){
            entity.hurt(DamageSource.FALL, 2.0F);
        }
    }
}
