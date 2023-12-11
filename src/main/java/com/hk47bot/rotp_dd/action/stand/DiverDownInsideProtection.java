// тут короче не работает считывание урона

package com.hk47bot.rotp_dd.action.stand;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.hk47bot.rotp_dd.RotpDiverDownAddon;
import com.hk47bot.rotp_dd.entity.stand.stands.DiverDownEntity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = RotpDiverDownAddon.MOD_ID)
public class DiverDownInsideProtection extends StandEntityAction {
    private static float damage;
    private static DamageSource source;
    private static LivingDamageEvent eventus;
    public static LivingEntity target;
    public LivingEntity user;
    public DiverDownInsideProtection(StandEntityAction.Builder builder) {
        super(builder.holdType());
    }

        @Override
    protected ActionConditionResult checkStandConditions(StandEntity stand, IStandPower power, ActionTarget target) {
        if (stand instanceof DiverDownEntity){
            DiverDownEntity diver = (DiverDownEntity)stand;
            if (diver.isInside())
            return ActionConditionResult.POSITIVE;
        }
        return conditionMessage("dd_notinside");
    }

    @Override
    public void standTickPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (!world.isClientSide()){
            if (standEntity instanceof DiverDownEntity) {
                DiverDownEntity diver = (DiverDownEntity)standEntity;
                LivingEntity effectTarget = diver.getTargetInside();
                target = effectTarget;
                if (hurtConditions()){
                    source.bypassArmor();
                    user.hurt(source, damage/2);
                }
                if (effectTarget != null) {
                    effectTarget.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 5, 2));
                }
            }
        }
    }
        
    
    @SubscribeEvent
    public static void justBeforeDamageApplies(LivingDamageEvent event) {
        eventus = event;
        source = event.getSource();
        damage = event.getAmount();
    }

    private boolean hurtConditions(){
        if (user != null && source != null && !user.isInvulnerableTo(source) && !user.isInvulnerable() && eventus.getEntityLiving() == target){
            return true;
        }
        else {
                return false;
        }
    }
}
