package com.hk47bot.rotp_dd.action.stand;

import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityHeavyAttack;
import com.github.standobyte.jojo.action.stand.punch.StandEntityPunch;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.general.GeneralUtil;
import com.github.standobyte.jojo.util.mc.damage.StandEntityDamageSource;
import com.hk47bot.rotp_dd.RotpDiverDownAddon;
import com.hk47bot.rotp_dd.entity.stand.stands.DiverDownEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.List;
import java.util.Random;

public class DiverDownDisassembleMob extends StandEntityHeavyAttack {
    public DiverDownDisassembleMob(StandEntityHeavyAttack.Builder builder){
        super(builder);
    }
    @Override
    protected ActionConditionResult checkStandConditions(StandEntity stand, IStandPower power, ActionTarget target) {
        if (stand instanceof DiverDownEntity){
            DiverDownEntity diver = (DiverDownEntity) stand;
            if (diver.isInside()){
                return ActionConditionResult.POSITIVE;
            }
        }
        return conditionMessage("dd_notinside");
    }


    @Override
    public StandEntityPunch punchEntity(StandEntity stand, Entity entity, StandEntityDamageSource dmgSource) {
        World world = stand.level;
        DiverDownEntity diverDown = (DiverDownEntity) stand;
        LivingEntity target = diverDown.getTargetInside();
        Random random = new Random();
        if (!world.isClientSide()){
            if (!(target instanceof PlayerEntity)) {
                ResourceLocation resourcelocation = target.getLootTable();
                RotpDiverDownAddon.getLogger().info(resourcelocation);
                LootTable loottable = target.getServer().getLootTables().get(resourcelocation);
                LootContext.Builder contextBuilder = new LootContext.Builder((ServerWorld) world).withRandom(new Random())
                        .withParameter(LootParameters.DIRECT_KILLER_ENTITY, stand.getUser())
                        .withParameter(LootParameters.KILLER_ENTITY, stand.getUser())
                        .withParameter(LootParameters.LAST_DAMAGE_PLAYER, (PlayerEntity) stand.getUser())
                        .withParameter(LootParameters.THIS_ENTITY, stand.getUser())
                        .withParameter(LootParameters.ORIGIN, stand.position())
                        .withParameter(LootParameters.DAMAGE_SOURCE, DamageSource.playerAttack((PlayerEntity) stand.getUser()))
                        .withLuck(5);
                GeneralUtil.doFractionTimes(() -> {
                    LootContext ctx = contextBuilder.create(LootParameterSets.ENTITY);
                    List<ItemStack> loot = loottable.getRandomItems(ctx);
                    RotpDiverDownAddon.getLogger().info(loot);
                    for (ItemStack itemStack : loot) {
                        if (itemStack.getItem() != Items.SADDLE) {
                            target.spawnAtLocation(itemStack);
                        }
                    }
                }, 5);
            } else {
                PlayerEntity playerTarget = (PlayerEntity) target;
                ItemStack dropItem = playerTarget.inventory.items.get(random.nextInt(playerTarget.inventory.getContainerSize()));
                playerTarget.inventory.removeItem(dropItem);
                playerTarget.spawnAtLocation(dropItem);
            }
        }
        diverDown.setTargetInside(null);
        return super.punchEntity(stand, target, dmgSource)
                .addKnockback(0.5F + stand.getLastHeavyFinisherValue())
                .disableBlocking((float) stand.getProximityRatio(entity) - 0.25F);
    }
}
