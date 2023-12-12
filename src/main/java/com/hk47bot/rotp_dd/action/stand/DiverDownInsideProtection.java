package com.hk47bot.rotp_dd.action.stand;

import java.util.List;
import java.util.stream.Collectors;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mc.damage.DamageUtil;
import com.github.standobyte.jojo.util.mc.damage.StandLinkDamageSource;
import com.hk47bot.rotp_dd.RotpDiverDownAddon;
import com.hk47bot.rotp_dd.entity.stand.stands.DiverDownEntity;
import com.hk47bot.rotp_dd.init.InitStands;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = RotpDiverDownAddon.MOD_ID)
public class DiverDownInsideProtection extends StandEntityAction {
    
    public DiverDownInsideProtection(StandEntityAction.Builder builder) {
        super(builder.holdType());
    }

    @Override
    protected ActionConditionResult checkStandConditions(StandEntity stand, IStandPower power, ActionTarget target) {
        if (stand instanceof DiverDownEntity){
            DiverDownEntity diver = (DiverDownEntity)stand;
            if (diver.isInside())
            return ActionConditionResult.POSITIVE;
        }
        return conditionMessage("dd_notinside");
    }

//    @Override
//    public void standTickPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
//        if (!world.isClientSide()){
//            if (standEntity instanceof DiverDownEntity) {
//                DiverDownEntity diver = (DiverDownEntity)standEntity;
//                LivingEntity effectTarget = diver.getTargetInside();
//                target = effectTarget;
//                if (hurtConditions()){
//                    source.bypassArmor();
//                    user.hurt(source, damage/2);
//                }
//                if (effectTarget != null) {
//                    effectTarget.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 5, 2));
//                }
//            }
//        }
//    }
        
    
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
