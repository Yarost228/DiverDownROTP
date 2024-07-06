package com.hk47bot.rotp_dd.action.stand;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.hk47bot.rotp_dd.entity.stand.stands.DiverDownEntity;

import net.minecraft.world.World;

public class DiverDownReturnFromMob extends StandEntityAction {
    public DiverDownReturnFromMob(StandEntityAction.Builder builder) {
        super(builder);
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
    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (standEntity instanceof DiverDownEntity){
            DiverDownEntity diver = (DiverDownEntity) standEntity;
            diver.setTargetInside(null);
        }
    }
}
