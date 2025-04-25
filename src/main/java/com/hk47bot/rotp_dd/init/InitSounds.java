package com.hk47bot.rotp_dd.init;

import com.github.standobyte.jojo.util.mc.OstSoundList;
import com.hk47bot.rotp_dd.RotpDiverDownAddon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class InitSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, RotpDiverDownAddon.MOD_ID);

    // user sounds
    public static final RegistryObject<SoundEvent> DIVER_DOWN = SOUNDS.register("anasui_diver_down",
            () -> new SoundEvent(new ResourceLocation(RotpDiverDownAddon.MOD_ID, "anasui_diver_down")));

    public static final RegistryObject<SoundEvent> ANASUI_DIVER_PUNCH = SOUNDS.register("anasui_diver_punch_light",
            () -> new SoundEvent(new ResourceLocation(RotpDiverDownAddon.MOD_ID, "anasui_diver_punch_light")));

    public static final RegistryObject<SoundEvent> ANASUI_DIVER_HEAVY_PUNCH = SOUNDS.register("anasui_diver_punch_heavy",
            () -> new SoundEvent(new ResourceLocation(RotpDiverDownAddon.MOD_ID, "anasui_diver_punch_heavy")));

    public static final RegistryObject<SoundEvent> ANASUI_DIVER_DEFORM_DASH = SOUNDS.register("anasui_diver_deform_dash",
            () -> new SoundEvent(new ResourceLocation(RotpDiverDownAddon.MOD_ID, "anasui_diver_deform_dash")));

    public static final RegistryObject<SoundEvent> ANASUI_DIVER_ENTITY_PHASING = SOUNDS.register("anasui_diver_entity_phasing",
            () -> new SoundEvent(new ResourceLocation(RotpDiverDownAddon.MOD_ID, "anasui_diver_entity_phasing")));

    public static final RegistryObject<SoundEvent> ANASUI_DIVER_RETRACT = SOUNDS.register("anasui_diver_retract",
            () -> new SoundEvent(new ResourceLocation(RotpDiverDownAddon.MOD_ID, "anasui_diver_retract")));



    // stand sounds
    public static final Supplier<SoundEvent> DIVER_DOWN_SUMMON = SOUNDS.register("diver_down_summon",
            () -> new SoundEvent(new ResourceLocation(RotpDiverDownAddon.MOD_ID, "diver_down_summon")));
    
    public static final Supplier<SoundEvent> DIVER_DOWN_UNSUMMON = SOUNDS.register("diver_down_unsummon",
            () -> new SoundEvent(new ResourceLocation(RotpDiverDownAddon.MOD_ID, "diver_down_unsummon")));
    
    public static final Supplier<SoundEvent> DIVER_DOWN_PUNCH_LIGHT = SOUNDS.register("diver_down_punch_light",
            () -> new SoundEvent(new ResourceLocation(RotpDiverDownAddon.MOD_ID, "diver_down_punch_light")));
    
    public static final Supplier<SoundEvent> DIVER_DOWN_PUNCH_HEAVY = SOUNDS.register("diver_down_punch_heavy",
            () -> new SoundEvent(new ResourceLocation(RotpDiverDownAddon.MOD_ID, "diver_down_punch_heavy")));
    
    public static final Supplier<SoundEvent> DIVER_DOWN_BARRAGE = DIVER_DOWN_PUNCH_LIGHT;

    public static final RegistryObject<SoundEvent> DIVER_DOWN_DEFORM_DASH = SOUNDS.register("diver_down_deform_dash",
            () -> new SoundEvent(new ResourceLocation(RotpDiverDownAddon.MOD_ID, "diver_down_deform_dash")));

    public static final RegistryObject<SoundEvent> DIVER_DOWN_WALL_GLIDE = SOUNDS.register("diver_down_glide",
            () -> new SoundEvent(new ResourceLocation(RotpDiverDownAddon.MOD_ID, "diver_down_glide")));

    public static final RegistryObject<SoundEvent> DIVER_DOWN_WALL_GLIDING = SOUNDS.register("diver_down_gliding",
            () -> new SoundEvent(new ResourceLocation(RotpDiverDownAddon.MOD_ID, "diver_down_gliding")));

    public static final RegistryObject<SoundEvent> DIVER_DOWN_ENTITY_PHASING = SOUNDS.register("diver_down_entity_phasing",
            () -> new SoundEvent(new ResourceLocation(RotpDiverDownAddon.MOD_ID, "diver_down_entity_phasing")));

    public static final RegistryObject<SoundEvent> DIVER_DOWN_INSIDE_PROTECTION = SOUNDS.register("diver_down_inside_protection",
            () -> new SoundEvent(new ResourceLocation(RotpDiverDownAddon.MOD_ID, "diver_down_inside_protection")));

    public static final RegistryObject<SoundEvent> DIVER_DOWN_RETRACT = SOUNDS.register("diver_down_retract",
            () -> new SoundEvent(new ResourceLocation(RotpDiverDownAddon.MOD_ID, "diver_down_retract")));


    static final OstSoundList DIVER_DOWN_OST = new OstSoundList(new ResourceLocation(RotpDiverDownAddon.MOD_ID, "diver_down_ost"), SOUNDS);


}
