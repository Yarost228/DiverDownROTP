package com.hk47bot.rotp_dd.entity.stand.stands;

import javax.annotation.Nullable;

import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.github.standobyte.jojo.init.ModStatusEffects;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class DiverDownEntity extends StandEntity {
    private static final DataParameter<Integer> PLACED_TRAPS = EntityDataManager.defineId(DiverDownEntity.class, DataSerializers.INT);
    private static final DataParameter<Integer> TARGET_INSIDE_ID = EntityDataManager.defineId(DiverDownEntity.class, DataSerializers.INT);
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
    private boolean isInsideViewBlockingBlock(Vector3d pos) {
        BlockPos.Mutable blockPos$mutable = new BlockPos.Mutable();
        for (int i = 0; i < 8; ++i) {
            double x = pos.x + (double)(((float)((i >> 0) % 2) - 0.5F) * getBbWidth() * 0.8F);
            double y = pos.y + getEyeHeight() + (double)(((float)((i >> 1) % 2) - 0.5F) * 0.1F);
            double z = pos.z + (double)(((float)((i >> 2) % 2) - 0.5F) * getBbWidth() * 0.8F);
            blockPos$mutable.set(x, y, z);
            BlockState blockState = level.getBlockState(blockPos$mutable);
            if (blockState.getRenderShape() != BlockRenderType.INVISIBLE && blockState.isSuffocating(level, blockPos$mutable)) {
                return true;
            }
        }
        return false;
    }

    protected void moveFromBlocks() {
        LivingEntity user = getUser();
        
        if (user != null && isManuallyControlled() && !noPhysics && isInsideViewBlockingBlock(position())) {
            Vector3d vecToUser = user.position().subtract(position());
            if (vecToUser.lengthSqr() > 1) {
                vecToUser = vecToUser.normalize();
            }
            moveWithoutCollision(vecToUser);
        }
    }

    private void moveWithoutCollision(Vector3d vec) {
        setBoundingBox(getBoundingBox().move(vec));
        setLocationFromBoundingbox();
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
        if (targetInside != null){
            return true;
        }
        return false;
    }

    @Override
    public void updatePosition() {
        if (targetInside != null) {
            if (targetInside.isAlive()) {
                Vector3d targetPos = targetInside.position();
                setPos(targetPos.x, targetPos.y, targetPos.z);
                setRot(targetInside.yRot, targetInside.xRot);
                
                return;
            }
            else {
                setTargetInside(null);
                retractStand(false);
            }
        }
        
        super.updatePosition();
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
        }
    }

}
