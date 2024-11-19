package com.hk47bot.rotp_dd;

import com.hk47bot.rotp_dd.init.InitEffects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hk47bot.rotp_dd.init.InitEntities;
import com.hk47bot.rotp_dd.init.InitSounds;
import com.hk47bot.rotp_dd.init.InitStands;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(RotpDiverDownAddon.MOD_ID)
public class RotpDiverDownAddon {
    // The value here should match an entry in the META-INF/mods.toml file
    public static final String MOD_ID = "rotp_dd";
    private static final Logger LOGGER = LogManager.getLogger();

    public RotpDiverDownAddon() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        InitEntities.ENTITIES.register(modEventBus);
        InitSounds.SOUNDS.register(modEventBus);
        InitStands.ACTIONS.register(modEventBus);
        InitStands.STANDS.register(modEventBus);
        InitEffects.EFFECTS.register(modEventBus);
    }

    public static Logger getLogger() {
        return LOGGER;
    }
}
