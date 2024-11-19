package com.hk47bot.rotp_dd.entity.projectile;

import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.entity.damaging.projectile.ModdedProjectileEntity;
import com.hk47bot.rotp_dd.init.InitEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class BoneShardEntity extends ModdedProjectileEntity {
    public BoneShardEntity(LivingEntity shooter, World world){
        super(InitEntities.BONE_SHARD.get(), shooter, world);
    }
    public BoneShardEntity(EntityType<? extends BoneShardEntity> type, World world){
        super(type, world);
    }
    @Override
    public int ticksLifespan() {
        return 360;
    }
    @Override
    protected float getBaseDamage() {
        return 5;
    }
    @Override
    protected float getMaxHardnessBreakable() {
        return 0.0F;
    }
    @Override
    public boolean standDamage() {
        return false;
    }
    @Override
    protected boolean hasGravity() {
        return true;
    }
    @Override
    protected boolean constVelocity() {
        return false;
    }
    @Override
    protected double getGravityAcceleration() {
        return 0.05;
    }

    @Override
    protected void afterBlockHit(BlockRayTraceResult blockRayTraceResult, boolean blockDestroyed) {
        if (blockDestroyed) {
            if (!level.isClientSide()) {
                setDeltaMovement(getDeltaMovement().scale(0.9));
                checkHit();
            }
        }
        else {
            BlockPos blockPos = blockRayTraceResult.getBlockPos();
            BlockState blockState = level.getBlockState(blockPos);
            blockSound(blockPos, blockState);
        }
        this.remove();
    }

    private void blockSound(BlockPos blockPos, BlockState blockState) {
        SoundType soundType = blockState.getSoundType(level, blockPos, this);
        level.playSound(null, blockPos, soundType.getHitSound(), SoundCategory.BLOCKS, 1.0F, soundType.getPitch() * 0.5F);
    }

    @Override
    protected void breakProjectile(ActionTarget.TargetType targetType, RayTraceResult hitTarget) {
        if (targetType != ActionTarget.TargetType.BLOCK) {
            super.breakProjectile(targetType, hitTarget);
        }
    }
}
