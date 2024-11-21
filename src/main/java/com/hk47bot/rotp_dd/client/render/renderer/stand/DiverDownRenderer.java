package com.hk47bot.rotp_dd.client.render.renderer.stand;

import com.github.standobyte.jojo.client.render.entity.renderer.stand.StandEntityRenderer;
import com.hk47bot.rotp_dd.RotpDiverDownAddon;
import com.hk47bot.rotp_dd.client.render.model.stand.DiverDownModel;
import com.hk47bot.rotp_dd.entity.stand.stands.DiverDownEntity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class DiverDownRenderer extends StandEntityRenderer<DiverDownEntity, DiverDownModel> {
    
    public DiverDownRenderer(EntityRendererManager renderManager) {
        super(renderManager, new DiverDownModel(), new ResourceLocation(RotpDiverDownAddon.MOD_ID, "textures/entity/stand/diverdown.png"), 0);
    }
    public void render(DiverDownEntity entity, float yRotation, float partialTick, MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight) {
        if (!entity.isInside()){
            super.render(entity, yRotation, partialTick, matrixStack, buffer, packedLight);
        }
    }
}
