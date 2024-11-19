package com.hk47bot.rotp_dd.action.stand;

import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.hk47bot.rotp_dd.entity.stand.stands.DiverDownEntity;
import com.hk47bot.rotp_dd.init.InitStands;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class DiverDownEntityPhasing extends StandEntityAction {
    public LivingEntity user;
    public LivingEntity targetent;
    public DiverDownEntityPhasing(StandEntityAction.Builder builder) {
        super(builder);
    }
       @Override
       protected ActionConditionResult checkStandConditions(StandEntity stand, IStandPower power, ActionTarget target) {
        Entity targetEntity = target.getEntity();
        DiverDownEntity diver = (DiverDownEntity) stand;
        if (targetEntity.is(power.getUser())) {
            return conditionMessage("dd_self");
        }
        if (targetEntity.is(diver.getTargetInside())){
            return conditionMessage("dd_already_inside");
        }
        return ActionConditionResult.POSITIVE;
    }

    @Nullable
    @Override
    public Action<IStandPower> getVisibleAction (IStandPower power, ActionTarget target) {
        DiverDownEntity diverDown = (DiverDownEntity) power.getStandManifestation();
        if (diverDown != null){
            if (!diverDown.isInside()){
                return super.getVisibleAction(power, target);
            }
            return InitStands.DIVER_DOWN_RETRACT.get();
        }
        return super.getVisibleAction(power, target);
    }

    @Override
    public TargetRequirement getTargetRequirement() {
        return TargetRequirement.ENTITY;
    }

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        user = userPower.getUser();
        ActionTarget target = task.getTarget();
        if (target.getEntity() instanceof LivingEntity){
            LivingEntity effectTarget = (LivingEntity) target.getEntity();
            targetent = effectTarget;
            if (standEntity instanceof DiverDownEntity){
            DiverDownEntity diver = (DiverDownEntity) standEntity;
            diver.setTargetInside(effectTarget);
            }
        }
    }
}
