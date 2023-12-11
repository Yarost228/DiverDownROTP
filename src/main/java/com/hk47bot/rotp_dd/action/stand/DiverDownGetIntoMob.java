package com.hk47bot.rotp_dd.action.stand;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.init.ModStatusEffects;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.hk47bot.rotp_dd.entity.stand.stands.DiverDownEntity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;

public class DiverDownGetIntoMob extends StandEntityAction {
    public LivingEntity user;
    public LivingEntity targetent;
    public DiverDownGetIntoMob(StandEntityAction.Builder builder) {
        super(builder);
    }
       @Override
    public ActionConditionResult checkTarget(ActionTarget target, LivingEntity user, IStandPower power) {
        Entity targetEntity = target.getEntity();
        if (targetEntity.is(power.getUser())) {
            return conditionMessage("dd_self");
        }
        return ActionConditionResult.POSITIVE;
    }

    @Override
    public TargetRequirement getTargetRequirement() {
        return TargetRequirement.ENTITY;
    }

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (!world.isClientSide()){
            user = userPower.getUser();
            ActionTarget target = task.getTarget();
            if (target.getEntity() instanceof LivingEntity){
                LivingEntity effectTarget = (LivingEntity) target.getEntity();
                targetent = effectTarget;
                if (standEntity instanceof DiverDownEntity){
                DiverDownEntity diver = (DiverDownEntity) standEntity;
                diver.setTargetInside(effectTarget);
                diver.addEffect(new EffectInstance(ModStatusEffects.FULL_INVISIBILITY.get(), 20, 20));
                
                }
            }
        }
    }
}
