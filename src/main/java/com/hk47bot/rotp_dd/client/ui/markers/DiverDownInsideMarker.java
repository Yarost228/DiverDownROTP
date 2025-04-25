package com.hk47bot.rotp_dd.client.ui.markers;

import com.github.standobyte.jojo.client.ui.marker.MarkerRenderer;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.hk47bot.rotp_dd.RotpDiverDownAddon;
import com.hk47bot.rotp_dd.entity.stand.stands.DiverDownEntity;
import com.hk47bot.rotp_dd.init.AddonStands;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;

import java.util.List;


public class DiverDownInsideMarker extends MarkerRenderer {

    public DiverDownInsideMarker(Minecraft mc) {
        super(AddonStands.DIVER_DOWN.getStandType().getColor(), new ResourceLocation(RotpDiverDownAddon.MOD_ID, "textures/action/diver_down_entity_phasing.png"), mc);
    }
    @Override
    protected boolean shouldRender() {
        return true;
    }
    @Override
    protected void updatePositions(List<MarkerInstance> list, float partialTick) {
        IStandPower.getStandPowerOptional(mc.player).ifPresent(stand -> {
            if (stand.getStandManifestation() instanceof DiverDownEntity) {
                if (((DiverDownEntity) stand.getStandManifestation()).isInside()){
                    Vector3d targetPos = ((DiverDownEntity) stand.getStandManifestation()).getPosition(partialTick).add(0, ((DiverDownEntity) stand.getStandManifestation()).getBbHeight() * 1.1, 0);
                    list.add(new Marker(targetPos, false));
                }
            }
        });
    }
    protected static class Marker extends MarkerRenderer.MarkerInstance {

        public Marker(Vector3d pos, boolean outlined) {
            super(pos, outlined);
        }

    }
}

