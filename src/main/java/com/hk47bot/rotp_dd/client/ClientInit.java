package com.hk47bot.rotp_dd.client;

import com.github.standobyte.jojo.client.ui.marker.MarkerRenderer;
import com.hk47bot.rotp_dd.RotpDiverDownAddon;
import com.hk47bot.rotp_dd.client.render.renderer.stand.DiverDownRenderer;
import com.hk47bot.rotp_dd.client.ui.markers.DiverDownInsideMarker;
import com.hk47bot.rotp_dd.init.AddonStands;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = RotpDiverDownAddon.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientInit {
    
    @SubscribeEvent
    public static void onFMLClientSetup(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(AddonStands.DIVER_DOWN.getEntityType(), DiverDownRenderer::new);
        Minecraft mc = event.getMinecraftSupplier().get();
        MarkerRenderer.Handler.addRenderer(new DiverDownInsideMarker(mc));
    }
}
