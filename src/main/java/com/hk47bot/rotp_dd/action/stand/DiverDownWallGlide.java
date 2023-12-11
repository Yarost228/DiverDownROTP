package com.hk47bot.rotp_dd.action.stand;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;


public class DiverDownWallGlide extends StandEntityAction {
    

    public DiverDownWallGlide(StandEntityAction.Builder builder) {
        super(builder.holdType());
    }
    @Override
    protected ActionConditionResult checkSpecificConditions(LivingEntity user, IStandPower power, ActionTarget target) {
        if (user.horizontalCollision){
            return ActionConditionResult.POSITIVE;
        }
        return ActionConditionResult.NEGATIVE;
    }
    @Override
    protected void holdTick(World world, LivingEntity user, IStandPower userPower, int ticksHeld, ActionTarget target, boolean requirementsFulfilled) {
        if (world.isClientSide() && user.horizontalCollision) {
                Vector3d movement = user.getLookAngle();
                user.setDeltaMovement(movement.x/3.5, 0, movement.z/3.5);
        }
    }
}
