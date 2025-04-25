package com.hk47bot.rotp_dd.entity.trap;

import com.github.standobyte.jojo.JojoModConfig;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mc.damage.DamageUtil;
import com.hk47bot.rotp_dd.init.InitEntities;
import com.hk47bot.rotp_dd.init.InitSounds;
import net.minecraft.block.material.Material;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class KineticTrapEntity extends Entity {
    public boolean isActive;
    protected static final DataParameter<Optional<UUID>> OWNER_UUID = EntityDataManager.defineId(KineticTrapEntity.class, DataSerializers.OPTIONAL_UUID);
    public Vector3d targetVec;
    public int lifespan = 0;
    public boolean isAttacking;
    public float attackTime;


    public KineticTrapEntity(EntityType<?> type, World world) {
        super(type, world);
        noPhysics = true;
        setNoGravity(true);
    }

    @Nullable
    private UUID getOwnerUUID() {
        return entityData.get(OWNER_UUID).orElse(null);
    }

    private void setOwnerUUID(@Nullable UUID uuid) {
        entityData.set(OWNER_UUID, Optional.ofNullable(uuid));
    }

    public void setOwner(LivingEntity owner) {
        setOwnerUUID(owner != null ? owner.getUUID() : null);
    }

    @Nullable
    public LivingEntity getOwner() {
        try {
            UUID uuid = this.getOwnerUUID();
            if (uuid == null) return null;
            PlayerEntity owner = level.getPlayerByUUID(uuid);
            return owner;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(OWNER_UUID, Optional.empty());
    }
        @Override
        protected void readAdditionalSaveData (CompoundNBT nbt){
            UUID ownerId = nbt.hasUUID("Owner") ? nbt.getUUID("Owner") : null;
            if (ownerId != null) {
                setOwnerUUID(ownerId);
            }
        }
        @Override
        protected void addAdditionalSaveData (CompoundNBT nbt){
            if (getOwnerUUID() != null) {
                nbt.putUUID("Owner", getOwnerUUID());
            }
        }
        @Override
        public IPacket<?> getAddEntityPacket () {
            return NetworkHooks.getEntitySpawningPacket(this);
        }

    public KineticTrapEntity(World world) {
            this(InitEntities.KINETIC_TRAP.get(), world);
        }

        public boolean canDamage(LivingEntity entity) {
            if (entity instanceof TameableEntity) {
                TameableEntity pet = (TameableEntity) entity;
                if (pet.getOwner() == this.getOwner()) return false;
            }
            if (entity instanceof StandEntity) {
                StandEntity stand = (StandEntity) entity;
                if (getOwner() != null && stand == IStandPower.getStandPowerOptional(this.getOwner()).resolve().get().getStandManifestation()) return false;
            }
            return entity != this.getOwner() && (getOwner() != null && !entity.isAlliedTo(this.getOwner())) && !(entity instanceof ArmorStandEntity);
        }

        @Override
        public void tick() {
            super.tick();
            tickCount++;
            if (tickCount > 1200 && !this.isActive || this.level.getBlockState(blockPosition()).getMaterial() == Material.AIR) {
                this.remove();
            }
            if (!this.isActive){
                List<Entity> targets = this.level.getEntities(this, this.getBoundingBox().inflate(1.1D), entity -> {return entity instanceof LivingEntity && entity != level.getPlayerByUUID(getOwnerUUID()); });
                if (!targets.isEmpty()) {
                    LivingEntity target = (LivingEntity)targets.stream().findAny().get();
                    this.lookAt(EntityAnchorArgument.Type.EYES, target.getEyePosition(1));
                    targetVec = this.getLookAngle();
                    if (this.canDamage(target)) this.activate(target);
                }
            }
            else {
                this.updateAttackTick();
                lifespan ++;
                if (lifespan >= 15){
                    this.remove();
                }
            }
        }

        public void activate(LivingEntity target){
            if (!target.isInvulnerable()) {
                this.isActive = true;
                this.playSound(InitSounds.DIVER_DOWN_PUNCH_LIGHT.get(), 1, 1);
                if (!target.level.isClientSide()){
                   DamageUtil.hurtThroughInvulTicks(target, DamageUtil.enderDragonDamageHack(DamageSource.mobAttack(this.getOwner()), target), 7 * JojoModConfig.getCommonConfigInstance(false).standDamageMultiplier.get().floatValue());
                }
            }
        }
    public void updateAttackTick(){
        if (isAttacking){
            attackTime += 1.2F;
            if (attackTime >= 0){
                this.isAttacking = false;
            }
        }
        else {
            attackTime -= 1.5F;
            if (attackTime <= -8){
                this.isAttacking = true;
            }
        }
    }
}

