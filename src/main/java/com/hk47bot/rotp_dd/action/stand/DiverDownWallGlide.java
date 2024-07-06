package com.hk47bot.rotp_dd.action.stand;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.client.sound.ClientTickingSoundsHelper;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;

import com.hk47bot.rotp_dd.init.InitSounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;


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
    public TargetRequirement getTargetRequirement() {
        return TargetRequirement.BLOCK;
    }

    @Override
    protected void holdTick(World world, LivingEntity user, IStandPower userPower, int ticksHeld, ActionTarget target, boolean requirementsFulfilled) {
        StandEntity standEntity = (StandEntity) userPower.getStandManifestation();
        int tickCount = 0;
        tickCount ++;
        if (world.isClientSide() && user.horizontalCollision) {
            Vector3d movement = user.getLookAngle();
            user.setDeltaMovement(movement.x / 3, movement.y/3, movement.z / 3);
            if (tickCount == 2){
                standEntity.playSound(InitSounds.DIVER_DOWN_SUMMON.get(), 1.0F, 1.0F, ClientUtil.getClientPlayer());
                tickCount = 0;
            }


        }
    }
    @Override
    public void stoppedHolding(World world, LivingEntity user, IStandPower power, int ticksHeld, boolean willFire) {
        StandEntity standEntity = (StandEntity) power.getStandManifestation();
        if (world.isClientSide()) {
            standEntity.playSound(InitSounds.DIVER_DOWN_UNSUMMON.get(), 1.0F, 1.0F, ClientUtil.getClientPlayer());
        }
    }
}
