package com.hk47bot.rotp_dd.action.stand;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.hk47bot.rotp_dd.entity.stand.stands.DiverDownEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;


public class DiverDownWallGlide extends StandEntityAction {
    public DiverDownWallGlide(StandEntityAction.Builder builder) {
        super(builder);
    }

    @Override
    protected ActionConditionResult checkSpecificConditions(LivingEntity user, IStandPower power, ActionTarget target) {
        DiverDownEntity diverDown = (DiverDownEntity) power.getStandManifestation();
        if (!user.horizontalCollision && diverDown != null) {
            if (!diverDown.isInside()) {
                if (user.level.isClientSide()) {
                    if (user instanceof PlayerEntity) {
                        return ActionConditionResult.NEGATIVE_CONTINUE_HOLD;
                    }
                } else if (!(user instanceof PlayerEntity)) {
                    return ActionConditionResult.NEGATIVE_CONTINUE_HOLD;
                }
            }
        }
        return ActionConditionResult.POSITIVE;
    }

    @Override
    protected void holdTick(World world, LivingEntity user, IStandPower power, int ticksHeld, ActionTarget target, boolean requirementsFulfilled) {
        if (requirementsFulfilled){
            Vector3d movement = user.getLookAngle();
            if (!user.isShiftKeyDown()){
                user.setDeltaMovement(movement.x / 3, movement.y / 3, movement.z / 3);
            }
            else {
                user.setDeltaMovement(0, 0, 0);
            }
        }
    }
}
