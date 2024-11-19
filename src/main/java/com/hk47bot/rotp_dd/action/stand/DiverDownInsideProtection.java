package com.hk47bot.rotp_dd.action.stand;

import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.client.sound.ClientTickingSoundsHelper;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.hk47bot.rotp_dd.entity.stand.stands.DiverDownEntity;
import com.hk47bot.rotp_dd.init.InitSounds;
import com.hk47bot.rotp_dd.init.InitStands;

import net.minecraft.world.World;

import javax.annotation.Nullable;

public class DiverDownInsideProtection extends StandEntityAction {
    
    public DiverDownInsideProtection(Builder builder) {
        super(builder.holdType());
    }

    @Nullable
    @Override
    public Action<IStandPower> getVisibleAction (IStandPower power, ActionTarget target) {
        DiverDownEntity diverDown = (DiverDownEntity) power.getStandManifestation();
        if (diverDown != null) {
            if (diverDown.isInside()) {
                return super.getVisibleAction(power, target);
            }
            return InitStands.DIVER_DOWN_BLOCK.get();
        }
        return InitStands.DIVER_DOWN_BLOCK.get();
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
    public void phaseTransition(World world, StandEntity standEntity, IStandPower standPower,
                                @Nullable Phase from, @Nullable Phase to, StandEntityTask task, int nextPhaseTicks) {
        if (world.isClientSide()) {
            if (to == Phase.PERFORM) {
                ClientTickingSoundsHelper.playStandEntityCancelableActionSound(standEntity,
                        InitSounds.DIVER_DOWN_INSIDE_PROTECTION.get(), this, Phase.PERFORM, 1.0F, 1.0F, true);
            }
            else if (from == Phase.PERFORM) {
                standEntity.playSound(InitSounds.DIVER_DOWN_SUMMON.get(), 1.0F, 1.0F, ClientUtil.getClientPlayer());
            }
        }
    }
}
