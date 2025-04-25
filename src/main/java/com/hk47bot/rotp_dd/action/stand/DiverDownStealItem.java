package com.hk47bot.rotp_dd.action.stand;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.merchant.villager.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MerchantOffer;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.Random;

public class DiverDownStealItem extends StandAction {
    public DiverDownStealItem(StandAction.Builder builder) {
        super(builder);
    }

    @Override
    protected ActionConditionResult checkSpecificConditions(LivingEntity user, IStandPower power, ActionTarget target) {
        if (target.getType() == ActionTarget.TargetType.BLOCK){
            if (user.level.getBlockState(target.getBlockPos()).hasTileEntity()){
                TileEntity targetedBlockEntity = user.level.getBlockEntity(target.getBlockPos());
                if (targetedBlockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).isPresent()){
                    return ActionConditionResult.POSITIVE;
                }
                return conditionMessage("dd_target_no_inv");
            }
            return ActionConditionResult.NEGATIVE;
        }
        else if (target.getType() == ActionTarget.TargetType.ENTITY){
            if (target.getEntity() instanceof PlayerEntity || target.getEntity() instanceof AbstractVillagerEntity) {
                if (target.getEntity() instanceof VillagerEntity){
                    VillagerEntity villagerEntity = (VillagerEntity) target.getEntity();
                    if (villagerEntity.getVillagerData().getProfession() == VillagerProfession.NITWIT){
                        return conditionMessage("dd_target_no_inv");
                    }
                }
                return ActionConditionResult.POSITIVE;
            }

            return conditionMessage("dd_target_no_inv");
        }

        return ActionConditionResult.NEGATIVE;
    }

    @Override
    public TargetRequirement getTargetRequirement() {
        return TargetRequirement.ANY;
    }

    @Override
    protected void perform(World world, LivingEntity user, IStandPower power, ActionTarget target) {
        if (user instanceof PlayerEntity){
            PlayerEntity player = (PlayerEntity) user;
            switch (target.getType()){
                case BLOCK:
                    BlockState targetedBlock = world.getBlockState(target.getBlockPos());
                    if (targetedBlock.hasTileEntity()){
                        TileEntity targetedBlockEntity = user.level.getBlockEntity(target.getBlockPos());
                        if (targetedBlockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).isPresent()){
                            stealItemFromCap(player, targetedBlockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).resolve().get());
                        }
                    }
                case ENTITY:
                    Entity entity = target.getEntity();
                    if (entity instanceof PlayerEntity){
                        PlayerEntity targetedPlayer = (PlayerEntity) entity;
                        stealItem(player, targetedPlayer.inventory);
                    }
                    else if (entity instanceof AbstractVillagerEntity){
                        ArrayList<ItemStack> villagerInventory = new ArrayList<>();
                        AbstractVillagerEntity villager = (AbstractVillagerEntity) entity;
                        Random random = villager.getRandom();
                        for (int i = 0; i < villager.getOffers().size(); i++){
                            MerchantOffer offer = villager.getOffers().get(i);
                            if (!offer.isOutOfStock()){
                                villagerInventory.add(offer.getResult());
                                CompoundNBT offerTag = offer.createTag();
                                offerTag.putInt("uses", offer.getUses()+1);
                                MerchantOffer newOffer = new MerchantOffer(offerTag);
                                villager.getOffers().add(villager.getOffers().indexOf(offer), newOffer);
                                villager.getOffers().remove(offer);
                            }
                        }
                        if (villager instanceof VillagerEntity){
                            VillagerEntity villagerEntity = (VillagerEntity) villager;
                            villagerEntity.setVillagerXp(Math.max(villager.getVillagerXp()-NEXT_LEVEL_XP_THRESHOLDS[villagerEntity.getVillagerData().getLevel()]/10, 0));
                            if (villager.getVillagerXp() <= getMinXpPerLevel(villagerEntity.getVillagerData().getLevel())) villagerEntity.setVillagerData(villagerEntity.getVillagerData().setLevel(villagerEntity.getVillagerData().getLevel()-1));
                        }
                        else if (villager instanceof WanderingTraderEntity){
                            if (needsToRestock(villager)){
                                villagerInventory.add(new ItemStack(Items.MILK_BUCKET));
                                villagerInventory.add(new ItemStack(Items.BLUE_CARPET));
                                villagerInventory.add(new ItemStack(Items.LEAD));
                                villagerInventory.add(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.LONG_INVISIBILITY));
                                VillagerEntity nitwit = new VillagerEntity(EntityType.VILLAGER, world);
                                nitwit.moveTo(villager.position());
                                nitwit.load(villager.getPersistentData());
                                nitwit.setVillagerData(nitwit.getVillagerData().setProfession(VillagerProfession.NITWIT));
                                villager.remove();
                                world.addFreshEntity(nitwit);
                            }
                        }
                        if (!villagerInventory.isEmpty()){
                            int index = random.nextInt(villagerInventory.size());
                            stealItemFromArrayList(player, villagerInventory, index);
                        }
                     }
            }
        }
    }

    private void stealItem(PlayerEntity player, IInventory inventory){
        World world = player.level;
        for (int i = 0; i < inventory.getContainerSize(); i++){
            if (inventory.getItem(i).getCount() > 0){
                if (player.inventory.getFreeSlot() != -1){
                    player.addItem(inventory.removeItem(i, Math.min(inventory.getItem(i).getCount(), 64)));
                }
                else {
                    world.addFreshEntity(new ItemEntity(world, player.getX(), player.getY(), player.getZ(), inventory.removeItem(i, Math.min(inventory.getItem(i).getCount(), 64))));
                }
                break;
            }
        }
    }
    private void stealItemFromCap(PlayerEntity player, IItemHandler inventory){
        World world = player.level;
        for (int i = 0; i < inventory.getSlots(); i++){
            if (inventory.getStackInSlot(i).getCount() > 0){
                if (player.inventory.getFreeSlot() != -1){
                    player.addItem(inventory.extractItem(i, Math.min(inventory.getStackInSlot(i).getCount(), 64), false));
                }
                else {
                    world.addFreshEntity(new ItemEntity(world, player.getX(), player.getY(), player.getZ(), inventory.extractItem(i, Math.min(inventory.getStackInSlot(i).getCount(), 64), false)));
                }
                break;
            }
        }
    }
    private void stealItemFromArrayList(PlayerEntity player, ArrayList<ItemStack> inventory, int randomIndex){
        World world = player.level;
        if (player.inventory.getFreeSlot() != -1) {
            player.addItem(inventory.get(randomIndex));
        } else {
            world.addFreshEntity(new ItemEntity(world, player.getX(), player.getY(), player.getZ(), inventory.get(randomIndex)));
        }
    }
    private static final int[] NEXT_LEVEL_XP_THRESHOLDS = new int[]{0, 10, 70, 150, 250};
    public static int getMinXpPerLevel(int level) {
        return VillagerData.canLevelUp(level) ? NEXT_LEVEL_XP_THRESHOLDS[level - 1] : 0;
    }
    private boolean needsToRestock(AbstractVillagerEntity villagerEntity) {
        int count = 0;
        for(MerchantOffer merchantoffer : villagerEntity.getOffers()) {
            if (merchantoffer.isOutOfStock()) {
                count++;
            }
        }
        return villagerEntity.getOffers().size() <= count;
    }
}
