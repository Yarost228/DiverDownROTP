package com.hk47bot.rotp_dd.client.render.renderer.stand;

import com.github.standobyte.jojo.client.render.entity.model.stand.StandEntityModel;
import com.github.standobyte.jojo.client.render.entity.model.stand.StandModelRegistry;
import com.github.standobyte.jojo.client.render.entity.renderer.stand.StandEntityRenderer;
import com.hk47bot.rotp_dd.RotpDiverDownAddon;
import com.hk47bot.rotp_dd.client.render.model.stand.DiverDownNewModel;
import com.hk47bot.rotp_dd.entity.stand.stands.DiverDownEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class DiverDownNewRenderer extends StandEntityRenderer<DiverDownEntity, StandEntityModel<DiverDownEntity>> {

    public DiverDownNewRenderer(EntityRendererManager renderManager) {
        super(renderManager,
                StandModelRegistry.registerModel(new ResourceLocation(RotpDiverDownAddon.MOD_ID, "diver_down"), DiverDownNewModel::new),
                new ResourceLocation(RotpDiverDownAddon.MOD_ID, "textures/entity/stand/diver_down.png"), 0);
    }
    public void render(DiverDownEntity entity, float yRotation, float partialTick, MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight) {
        super.render(entity, yRotation, partialTick, matrixStack, buffer, packedLight);
    }
}
