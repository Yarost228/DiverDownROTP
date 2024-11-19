package com.hk47bot.rotp_dd.util;


import com.github.standobyte.jojo.util.mc.damage.DamageUtil;
import com.github.standobyte.jojo.util.mc.damage.StandLinkDamageSource;
import com.hk47bot.rotp_dd.RotpDiverDownAddon;
import com.hk47bot.rotp_dd.entity.projectile.BoneShardEntity;
import com.hk47bot.rotp_dd.entity.stand.stands.DiverDownEntity;
import com.hk47bot.rotp_dd.init.InitEffects;
import com.hk47bot.rotp_dd.init.InitStands;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.world.Explosion;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = RotpDiverDownAddon.MOD_ID)
public class GameplayEvents {
    @SubscribeEvent
    public static void justBeforeDamageApplies(LivingDamageEvent event) {
        LivingEntity damageTarget = event.getEntityLiving();
        List<DiverDownEntity> standsInside = damageTarget.level.getEntitiesOfClass(DiverDownEntity.class, damageTarget.getBoundingBox());
        List<DiverDownEntity> standsProtecting = standsInside.stream()
                .filter(stand -> stand.getCurrentTaskAction() == InitStands.DIVER_DOWN_PROTECTION.get())
                .collect(Collectors.toList());
        if (!standsProtecting.isEmpty()) {
            DamageSource damageSource = event.getSource();
            float damageAmount = event.getAmount();
            for (DiverDownEntity diverDown : standsProtecting) {
                if (diverDown.getTargetInside() == damageTarget) {
                    LivingEntity ddUser = diverDown.getUser();
                    if (ddUser != null) {
                        DamageUtil.hurtThroughInvulTicks(ddUser, new StandLinkDamageSource(ddUser, damageSource), damageAmount / 2);
                    }
                }
            }
            event.setAmount(damageAmount / 2);
        }
    }
    @SubscribeEvent
    public static void onEntityTick(LivingEvent.LivingUpdateEvent event){
        if (event.getEntityLiving().hasEffect(InitEffects.SPRING_LEGS.get()) && event.getEntityLiving().isOnGround() && event.getEntityLiving().isAlive()){
            event.getEntityLiving().fallDistance += 2 * event.getEntityLiving().getEffect(InitEffects.SPRING_LEGS.get()).getAmplifier();
            int amplifier = (event.getEntityLiving().getEffect(InitEffects.SPRING_LEGS.get()).getAmplifier() == 0) ? 1 : event.getEntityLiving().getEffect(InitEffects.SPRING_LEGS.get()).getAmplifier() + 1;
            event.getEntityLiving().setDeltaMovement(0, amplifier/2F, 0);
        }
    }
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onEntityHurt(LivingHurtEvent event){
        LivingEntity livingEntity = event.getEntityLiving();
        if (!livingEntity.level.isClientSide()){
            if (livingEntity.hasEffect(InitEffects.BONE_SHARDS_BOMB.get())){
                Random random = new Random();
                event.setAmount(event.getAmount() * 2);
                int shardCount = 4 + random.nextInt(12);
                for (int i = 0; i < shardCount; i++) {
                    float f = ((i + 1) / (float) shardCount/2) * 360F;
                    BoneShardEntity shard = new BoneShardEntity(livingEntity, livingEntity.level);
                    shard.shootFromRotation(event.getSource().getDirectEntity(), livingEntity.getViewYRot(2) - random.nextInt(40), f, 0.0F, 0.45F + random.nextFloat() * 0.2F, 1.0F);
                    livingEntity.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 200, 1, false, false, true));
                    livingEntity.level.addFreshEntity(shard);
                }
                livingEntity.removeEffect(InitEffects.BONE_SHARDS_BOMB.get());
            }
        }
    }
}
