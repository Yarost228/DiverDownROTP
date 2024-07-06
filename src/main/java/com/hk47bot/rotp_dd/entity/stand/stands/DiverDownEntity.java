package com.hk47bot.rotp_dd.entity.stand.stands;

import javax.annotation.Nullable;

import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.github.standobyte.jojo.init.ModStatusEffects;

import com.hk47bot.rotp_dd.init.InitStands;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

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
        return getPlacedTrapsCount() < 7;
    }

    // code to go through walls

    @Override
    protected boolean shouldHaveNoPhysics() {
        return true;
    }

    // code to get into entities
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
        return targetInside != null;
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
        if (this.getCurrentTaskAction() == InitStands.DIVER_DOWN_GLIDE.get() && this.getUser().horizontalCollision){

        }
        super.updatePosition();
    }
    public boolean diverIsGliding() {
        return this.getCurrentTaskAction() == InitStands.DIVER_DOWN_GLIDE.get();

    }
    public boolean diverIsProtecting() {
        return this.getCurrentTaskAction() == InitStands.DIVER_DOWN_PROTECTION.get();

    }

    @Override
    public boolean isFollowingUser() {
        return super.isFollowingUser() && targetInside == null && !diverIsGliding();
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
}
