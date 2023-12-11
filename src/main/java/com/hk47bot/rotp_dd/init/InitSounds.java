package com.hk47bot.rotp_dd.init;

import java.util.function.Supplier;

import com.github.standobyte.jojo.init.ModSounds;
import com.github.standobyte.jojo.util.mc.OstSoundList;
import com.hk47bot.rotp_dd.RotpDiverDownAddon;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, RotpDiverDownAddon.MOD_ID);
    
    public static final RegistryObject<SoundEvent> DIVER_DOWN = SOUNDS.register("diver_down", 
            () -> new SoundEvent(new ResourceLocation(RotpDiverDownAddon.MOD_ID, "diver_down")));

    public static final Supplier<SoundEvent> DIVER_DOWN_SUMMON = ModSounds.STAND_SUMMON_DEFAULT;
    
    public static final Supplier<SoundEvent> DIVER_DOWN_UNSUMMON = ModSounds.STAND_UNSUMMON_DEFAULT;
    
    public static final Supplier<SoundEvent> DIVER_DOWN_PUNCH_LIGHT = ModSounds.STAND_PUNCH_LIGHT;
    
    public static final Supplier<SoundEvent> DIVER_DOWN_PUNCH_HEAVY = ModSounds.STAND_PUNCH_HEAVY;
    
    public static final Supplier<SoundEvent> DIVER_DOWN_BARRAGE = ModSounds.STAND_PUNCH_LIGHT;

    static final OstSoundList DIVER_DOWN_OST = new OstSoundList(new ResourceLocation(RotpDiverDownAddon.MOD_ID, "diver_down_ost"), SOUNDS);


}
