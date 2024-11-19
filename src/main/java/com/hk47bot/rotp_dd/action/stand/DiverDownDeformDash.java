package com.hk47bot.rotp_dd.action.stand;

import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityHeavyAttack;
import com.github.standobyte.jojo.action.stand.punch.StandEntityPunch;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.entity.stand.StandStatFormulas;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.general.MathUtil;
import com.github.standobyte.jojo.util.mc.damage.StandEntityDamageSource;
import com.github.standobyte.jojo.util.mod.JojoModUtil;

import com.hk47bot.rotp_dd.entity.stand.stands.DiverDownEntity;
import com.hk47bot.rotp_dd.init.InitStands;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;

import javax.annotation.Nullable;

public class DiverDownDeformDash extends StandEntityHeavyAttack{

    public DiverDownDeformDash(StandEntityHeavyAttack.Builder builder) {
        super(builder);
    }
    @Override
    public int getStandWindupTicks(IStandPower standPower, StandEntity standEntity) {
        return 0;
    }

    @Nullable
    @Override
    public Action<IStandPower> getVisibleAction(IStandPower power, ActionTarget target) {
        DiverDownEntity diverDown = (DiverDownEntity) power.getStandManifestation();
        if (diverDown != null) {
            if (diverDown.isInside()) {
                return InitStands.DIVER_DOWN_MOB_DISASSEMBLE.get();
            }
            return super.getVisibleAction(power, target);
        }
        return super.getVisibleAction(power, target);
    }
    
    @Override
    public int getStandActionTicks(IStandPower standPower, StandEntity standEntity) {
        return Math.max(StandStatFormulas.getHeavyAttackWindup(standEntity.getAttackSpeed(), standEntity.getLastHeavyFinisherValue()), 2);
    }
    
    @Override
    public void phaseTransition(World world, StandEntity standEntity, IStandPower standPower, 
            Phase from, Phase to, StandEntityTask task, int ticks) {
        super.phaseTransition(world, standEntity, standPower, from, to, task, ticks);
        if (to == Phase.PERFORM) {
            if (standEntity.isFollowingUser() && standEntity.getAttackSpeed() < 24) {
                LivingEntity user = standEntity.getUser();
                if (user != null) {
                    Vector3d vec = MathUtil.relativeCoordsToAbsolute(0, 0, 1.0, user.yRot);
                    standEntity.setPos(user.getX() + vec.x, 
                            standEntity.getY(), 
                            user.getZ() + vec.z);
                }
            }
            task.getAdditionalData().push(Vector3d.class, standEntity.getLookAngle().scale(10D / (double) ticks));
        }
    }

    @Override
    public void standTickPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        float completion = task.getPhaseCompletion(1.0F);
        boolean lastTick = task.getTicksLeft() <= 1;
        boolean moveForward = completion <= 0.5F;
        if (moveForward) {
            for (RayTraceResult rayTraceResult : JojoModUtil.rayTraceMultipleEntities(standEntity, 
                    standEntity.getAttributeValue(ForgeMod.REACH_DISTANCE.get()), 
                    standEntity.canTarget(), 0.25, standEntity.getPrecision())) {
                standEntity.punch(task, this, ActionTarget.fromRayTraceResult(rayTraceResult));
            }
        }
        else if (!Vector3d.ZERO.equals(standEntity.getDeltaMovement())) {
            standEntity.punch(task, this, task.getTarget());
        }
        if (!world.isClientSide() && lastTick && standEntity.isFollowingUser()) {
            standEntity.retractStand(false);
        }
        standEntity.setDeltaMovement(moveForward ? task.getAdditionalData().peek(Vector3d.class) : Vector3d.ZERO);
    }
    
    @Override
    public StandEntityPunch punchEntity(StandEntity stand, Entity target, StandEntityDamageSource dmgSource) {
        StandEntityPunch stab = super.punchEntity(stand, target, dmgSource);
        if (target.getEntity() instanceof LivingEntity){
            LivingEntity effectTarget = (LivingEntity) target.getEntity();
                    effectTarget.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 200, 1));
                    effectTarget.addEffect(new EffectInstance(Effects.WEAKNESS, 200, 1));
                    effectTarget.addEffect(new EffectInstance(Effects.DIG_SLOWDOWN, 200, 1));
        }
        stab.impactSound(null);
        if (stand.getAttackSpeed() < 24) {
            return stab
                    .damage(7);
    
        }
        else {
            return stab
                    .damage(7)
                    .impactSound(null);
        }
    }
    
    @Override
    protected boolean standKeepsTarget(ActionTarget target) {
        return false;
    }
    
    @Override
    public boolean isChainable(IStandPower standPower, StandEntity standEntity) {
        return true;
    }
    
    @Override
    protected boolean canBeQueued(IStandPower standPower, StandEntity standEntity) {
        return false;
    }
    
    
    @Override
    protected boolean standMovesByItself(IStandPower standPower, StandEntity standEntity) {
        return true;
    }
    
    @Override
    public ActionConditionResult checkStandTarget(ActionTarget target, StandEntity standEntity, IStandPower standPower) {
        return ActionConditionResult.NEGATIVE;
    }
}


