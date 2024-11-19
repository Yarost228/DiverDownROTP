package com.hk47bot.rotp_dd.action.stand;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.action.stand.StandEntityHeavyAttack;
import com.github.standobyte.jojo.action.stand.punch.StandEntityPunch;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mc.damage.StandEntityDamageSource;
import com.hk47bot.rotp_dd.entity.stand.stands.DiverDownEntity;
import com.hk47bot.rotp_dd.init.InitEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;

public class DiverDownSpringLegs extends StandEntityHeavyAttack {
    public DiverDownSpringLegs(StandEntityHeavyAttack.Builder builder){
        super(builder);
    }

    @Override
    protected ActionConditionResult checkStandConditions(StandEntity stand, IStandPower power, ActionTarget target) {
        if (stand instanceof DiverDownEntity){
            DiverDownEntity diver = (DiverDownEntity)stand;
            if (diver.isInside()){
                return ActionConditionResult.POSITIVE;
            }
        }
        return conditionMessage("dd_notinside");
    }

    @Override
    public StandEntityPunch punchEntity(StandEntity stand, Entity target, StandEntityDamageSource dmgSource) {
        if (target instanceof LivingEntity){
            LivingEntity livingTarget = (LivingEntity) target;
            livingTarget.addEffect(new EffectInstance(InitEffects.SPRING_LEGS.get(), 1200, 1, false, false, true));
        }
        DiverDownEntity diverDown = (DiverDownEntity) stand;
        diverDown.setTargetInside(null);
        return super.punchEntity(stand, target, dmgSource)
                .addKnockback(0.5F + stand.getLastHeavyFinisherValue())
                .disableBlocking((float) stand.getProximityRatio(target) - 0.25F);
    }
}
