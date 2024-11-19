package com.hk47bot.rotp_dd.client.ui.markers;

import com.github.standobyte.jojo.client.ui.marker.MarkerRenderer;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.hk47bot.rotp_dd.RotpDiverDownAddon;
import com.hk47bot.rotp_dd.action.stand.DiverDownKineticTrap;
import com.hk47bot.rotp_dd.entity.stand.stands.DiverDownEntity;
import com.hk47bot.rotp_dd.entity.trap.KineticTrapEntity;
import com.hk47bot.rotp_dd.init.AddonStands;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;

import java.util.List;

public class DiverDownTrapMarker extends MarkerRenderer {
    public DiverDownTrapMarker(Minecraft mc) {
        super(AddonStands.DIVER_DOWN.getStandType().getColor(), new ResourceLocation(RotpDiverDownAddon.MOD_ID, "textures/action/diver_down_kinetic_trap.png"), mc);
    }
    @Override
    protected boolean shouldRender() {
        return true;
    }
    @Override
    protected void updatePositions(List<MarkerRenderer.MarkerInstance> list, float partialTick) {
        PlayerEntity owner = mc.player;
        owner.level.getLoadedEntitiesOfClass(KineticTrapEntity.class, owner.getBoundingBox().inflate(16), trap -> trap.getOwner() == owner).forEach(trap -> {
            list.add(new MarkerInstance(trap.position(), trap.isActive));
        });
    }
    protected static class Marker extends MarkerRenderer.MarkerInstance {

        public Marker(Vector3d pos, boolean outlined) {
            super(pos, outlined);
        }

    }
}