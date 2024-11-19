package com.hk47bot.rotp_dd.entity.stand.stands;

import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.github.standobyte.jojo.init.ModStatusEffects;
import com.github.standobyte.jojo.util.mc.MCUtil;
import com.hk47bot.rotp_dd.init.InitStands;
import net.minecraft.block.Blocks;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class DiverDownEntity extends StandEntity {
    private static final DataParameter<Integer> PLACED_TRAPS = EntityDataManager.defineId(DiverDownEntity.class, DataSerializers.INT);
    public static final DataParameter<Integer> TARGET_INSIDE_ID = EntityDataManager.defineId(DiverDownEntity.class, DataSerializers.INT);
    private LivingEntity targetInside;

    public DiverDownEntity(StandEntityType<DiverDownEntity> type, World world) {
        super(type, world);
    }
    public int getPlacedTrapsCount() {
        return entityData.get(PLACED_TRAPS);
    }
    public boolean canPlaceTraps() {
        return getPlacedTrapsCount() < 8;
    }

    @Override
    protected boolean shouldHaveNoPhysics() {
        return true;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(TARGET_INSIDE_ID, -1);
    }

    @Override
    public void onSyncedDataUpdated(DataParameter<?> dataParameter) {
        super.onSyncedDataUpdated(dataParameter);
        if (dataParameter == TARGET_INSIDE_ID) {
            int entityId = entityData.get(TARGET_INSIDE_ID);
            Entity entity = level.getEntity(entityId);
            if (entity instanceof LivingEntity) {
                targetInside = (LivingEntity) entity;
            }
            else {
                targetInside = null;
            }
        }
    }
    
    public void setTargetInside(@Nullable Entity entity) {
        int entityId;
        if (entity != null) {
            entityId = entity.getId();
        }
        else {
            entityId = -1;
        }
        
        entityData.set(TARGET_INSIDE_ID, entityId);
    }

    public LivingEntity getTargetInside() {
        return targetInside;
    }

    public boolean isInside(){
        return entityData.get(TARGET_INSIDE_ID) != -1;
    }

    @Override
    public Vector3d collideNextPos(Vector3d pos) {
        return pos;
    }

    @Override
    public void updatePosition() {
        if (targetInside != null) {
            if (targetInside.isAlive() && !isBeingRetracted() && this.getRangeEfficiency() > 0.1) {
                Vector3d targetPos = targetInside.position();
                setPos(targetPos.x, targetPos.y, targetPos.z);
                this.lookAt(EntityAnchorArgument.Type.EYES, targetPos);

                return;
            }
            else {
                setTargetInside(null);
                retractStand(false);
            }
        }
        if (diverIsGliding() && this.getUser().horizontalCollision){
            List<BlockPos> blocksAroundUser = blocksAroundUser(this);
            blocksAroundUser.forEach(blockAroundUser -> {
                Vector3d userPos = this.getUser().position();
                BlockPos blockPos = this.getUser().blockPosition();
                Vector3d userBlockPos = new Vector3d(blockPos.getX(), blockPos.getY(), blockPos.getZ());
                Vector3d diff = new Vector3d(userPos.x() - userBlockPos.x(), userPos.y() - userBlockPos.y(), userPos.z() - userBlockPos.z());
                Vector3d diverBlockPos = Vector3d.atLowerCornerOf(blockAroundUser);
                Vector3d diverPos = diverBlockPos.add(diff);
                Vector3d diverExpPos = new Vector3d((userPos.x() + diverPos.x())/2, (userPos.y() + diverPos.y())/2, (userPos.z() + diverPos.z())/2);
                if (this.level.getBlockState(blockAroundUser).getBlock() != Blocks.AIR){
                    setPos(diverExpPos.x(), userPos.y(), diverExpPos.z());
                }
            });
        }
        else {
            super.updatePosition();
        }
    }
    public boolean diverIsGliding() {
        return this.getCurrentTaskAction() == InitStands.DIVER_DOWN_GLIDE.get();

    }

    public static List<BlockPos> blocksAroundUser(StandEntity standEntity){
        LivingEntity user = standEntity.getUser();
        BlockPos startPos = user.blockPosition();
        List<BlockPos> result = new ArrayList<>();
        result.add(startPos.north());
        result.add(startPos.south());
        result.add(startPos.east());
        result.add(startPos.west());
        result.add(startPos.above().north());
        result.add(startPos.above().south());
        result.add(startPos.above().east());
        result.add(startPos.above().west());
        return result;
    }

    @Override
    public boolean isFollowingUser() {
        return super.isFollowingUser() && targetInside == null;
    }

    @Override 
    public void tick() {
        super.tick();
        if (this.isInside()){
            this.addEffect(new EffectInstance(ModStatusEffects.FULL_INVISIBILITY.get(), 5, 2));
            this.setNoPhysics(true);
        }
    }
    public Predicate<Entity> canTarget() {
        return entity -> !entity.is(this) && !entity.is(getUser()) && entity.isAlive()
                && !(entity instanceof ProjectileEntity && this.is(((ProjectileEntity) entity).getOwner()));
    }
    @Override
    public ActionTarget aimWithThisOrUser(double reachDistance, ActionTarget currentTarget) {
        ActionTarget target;
        if (currentTarget.getType() == ActionTarget.TargetType.ENTITY && isTargetInReach(currentTarget)) {
            target = currentTarget;
        }
        else {
            RayTraceResult aim = null;
            if (!isManuallyControlled()) {
                LivingEntity user = getUser();
                if (user != null) {
                    aim = precisionRayTrace(user, reachDistance);
                }
            }
            if (aim == null || this.isInside()) {
                aim = precisionRayTrace(this, reachDistance);
            }

            target = ActionTarget.fromRayTraceResult(aim);
        }

        if (target.getEntity() != this) {
            Vector3d targetPos = target.getTargetPos(true);
            if (targetPos != null) {
                MCUtil.rotateTowards(this, targetPos, (float) getAttackSpeed() / 16F * 18F);
            }
        }

        return target;
    }
}
