package com.hk47bot.rotp_dd.util;


import com.github.standobyte.jojo.util.mc.damage.DamageUtil;
import com.github.standobyte.jojo.util.mc.damage.StandLinkDamageSource;
import com.hk47bot.rotp_dd.RotpDiverDownAddon;
import com.hk47bot.rotp_dd.entity.stand.stands.DiverDownEntity;
import com.hk47bot.rotp_dd.init.InitStands;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
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
}
