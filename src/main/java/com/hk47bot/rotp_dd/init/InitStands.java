package com.hk47bot.rotp_dd.init;

import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.stand.StandAction;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.action.stand.StandEntityBlock;
import com.github.standobyte.jojo.action.stand.StandEntityHeavyAttack;
import com.github.standobyte.jojo.action.stand.StandEntityLightAttack;
import com.github.standobyte.jojo.action.stand.StandEntityMeleeBarrage;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.github.standobyte.jojo.init.power.stand.EntityStandRegistryObject;
import com.github.standobyte.jojo.init.power.stand.ModStandsInit;
import com.github.standobyte.jojo.power.impl.stand.StandInstance.StandPart;
import com.github.standobyte.jojo.power.impl.stand.stats.StandStats;
import com.github.standobyte.jojo.power.impl.stand.type.EntityStandType;
import com.github.standobyte.jojo.power.impl.stand.type.StandType;
import com.hk47bot.rotp_dd.RotpDiverDownAddon;
import com.hk47bot.rotp_dd.action.stand.*;
import com.hk47bot.rotp_dd.entity.stand.stands.DiverDownEntity;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class InitStands {
    @SuppressWarnings("unchecked")
    public static final DeferredRegister<Action<?>> ACTIONS = DeferredRegister.create(
            (Class<Action<?>>) ((Class<?>) Action.class), RotpDiverDownAddon.MOD_ID);
    @SuppressWarnings("unchecked")
    public static final DeferredRegister<StandType<?>> STANDS = DeferredRegister.create(
            (Class<StandType<?>>) ((Class<?>) StandType.class), RotpDiverDownAddon.MOD_ID);
    
 // ======================================== Diver Down ========================================
    
    public static final RegistryObject<StandEntityAction> DIVER_DOWN_PUNCH = ACTIONS.register("diver_down_punch", 
            () -> new StandEntityLightAttack(new StandEntityLightAttack.Builder()
                    .punchSound(InitSounds.DIVER_DOWN_PUNCH_LIGHT)
                    .shout(InitSounds.ANASUI_DIVER_PUNCH)));
    
    public static final RegistryObject<StandEntityAction> DIVER_DOWN_BARRAGE = ACTIONS.register("diver_down_barrage", 
            () -> new StandEntityMeleeBarrage(new StandEntityMeleeBarrage.Builder()
                    .barrageHitSound(InitSounds.DIVER_DOWN_BARRAGE)));

    public static final RegistryObject<StandEntityAction> DIVER_DOWN_BLOCK = ACTIONS.register("diver_down_block",
            () -> new DiverDownBlock(new StandEntityAction.Builder()));

    public static final RegistryObject<StandEntityAction> DIVER_DOWN_GLIDE = ACTIONS.register("diver_down_glide", 
     () -> new DiverDownWallGlide(new StandEntityAction.Builder()
             .staminaCostTick(3F)
             .cooldown(40)
             .resolveLevelToUnlock(2)
             .standSound(InitSounds.DIVER_DOWN_WALL_GLIDE)));

    public static final RegistryObject<StandEntityAction> DIVER_DOWN_PROTECTION = ACTIONS.register("diver_down_protection",
            () -> new DiverDownInsideProtection(new StandEntityAction.Builder()
                    .standSound(InitSounds.DIVER_DOWN_INSIDE_PROTECTION)
                    .resolveLevelToUnlock(4)));

          public static final RegistryObject<StandEntityAction> DIVER_DOWN_ENTITY_PHASING = ACTIONS.register("diver_down_entity_phasing",
     () -> new DiverDownEntityPhasing(new StandEntityAction.Builder()
             .shout(InitSounds.ANASUI_DIVER_ENTITY_PHASING)
             .standSound(InitSounds.DIVER_DOWN_ENTITY_PHASING)
             .resolveLevelToUnlock(4)));

     public static final RegistryObject<DiverDownRetract> DIVER_DOWN_RETRACT = ACTIONS.register("diver_down_retract",
     () -> new DiverDownRetract(new DiverDownRetract.Builder()
             .shout(InitSounds.ANASUI_DIVER_RETRACT)
             .standSound(InitSounds.DIVER_DOWN_UNSUMMON)));
     
     public static final RegistryObject<StandEntityHeavyAttack> DIVER_DOWN_DEFORM_DASH = ACTIONS.register("diver_down_deform_dash", 
     () -> new DiverDownDeformDash(new StandEntityHeavyAttack.Builder()
             .shout(InitSounds.ANASUI_DIVER_DEFORM_DASH)
             .standSound(InitSounds.DIVER_DOWN_DEFORM_DASH)
             .staminaCost(200)));

    public static final RegistryObject<StandEntityHeavyAttack> DIVER_DOWN_HEAVY_PUNCH = ACTIONS.register("diver_down_heavy_punch",
            () -> new StandEntityHeavyAttack(new StandEntityHeavyAttack.Builder()
                    .shout(InitSounds.ANASUI_DIVER_HEAVY_PUNCH)
                    .punchSound(InitSounds.DIVER_DOWN_PUNCH_HEAVY)
                    .shiftVariationOf(DIVER_DOWN_PUNCH)
                    .shiftVariationOf(DIVER_DOWN_BARRAGE)
                    .partsRequired(StandPart.ARMS)
                    .setFinisherVariation(DIVER_DOWN_DEFORM_DASH)));


    
    
    public static final EntityStandRegistryObject<EntityStandType<StandStats>, StandEntityType<DiverDownEntity>> STAND_DIVER_DOWN = 
            new EntityStandRegistryObject<>("diver_down", 
                    STANDS, 
                    () -> new EntityStandType<StandStats>(
                            0x75B7B5, ModStandsInit.PART_6_NAME,

                            new StandAction[] {
                                    DIVER_DOWN_PUNCH.get(), 
                                    DIVER_DOWN_BARRAGE.get()

                                    },
                            new StandAction[] {
                                    DIVER_DOWN_PROTECTION.get(),
                                    DIVER_DOWN_GLIDE.get(),
                                    DIVER_DOWN_ENTITY_PHASING.get()
                                    },

                            StandStats.class, new StandStats.Builder()
                            .tier(5)
                            .power(14.0)
                            .speed(14.0)
                            .range(10.0, 10.0)
                            .durability(9.0)
                            .precision(13.0)
                            .build("Diver Down"), 

                            new StandType.StandTypeOptionals()
                            .addSummonShout(InitSounds.DIVER_DOWN)
                            .addOst(InitSounds.DIVER_DOWN_OST)), 

                    InitEntities.ENTITIES, 
                    () -> new StandEntityType<DiverDownEntity>(DiverDownEntity::new, 0.65F, 1.95F)
                    .summonSound(InitSounds.DIVER_DOWN_SUMMON)
                    .unsummonSound(InitSounds.DIVER_DOWN_UNSUMMON))
            .withDefaultStandAttributes();
}
