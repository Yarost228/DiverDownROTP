package com.hk47bot.rotp_dd.client.render.renderer.entity;

import com.github.standobyte.jojo.client.ClientTimeStopHandler;
import com.hk47bot.rotp_dd.RotpDiverDownAddon;
import com.hk47bot.rotp_dd.client.render.model.trap.DiverDownNewArmModel;
import com.hk47bot.rotp_dd.entity.trap.KineticTrapEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Collections;

public class DiverDownArmRenderer extends EntityRenderer<KineticTrapEntity> {
    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(RotpDiverDownAddon.MOD_ID, "textures/entity/stand/diver_down.png");
    private final DiverDownNewArmModel<KineticTrapEntity> model = new DiverDownNewArmModel<>();
    private final Minecraft mc = Minecraft.getInstance();

    public DiverDownArmRenderer(EntityRendererManager rendererManager) {
        super(rendererManager);
    }
    @Override
    public ResourceLocation getTextureLocation(KineticTrapEntity armEntity) {
        return TEXTURE_LOCATION;
    }


    protected int getSkyLightLevel(KineticTrapEntity trap, BlockPos blockPos) {
        ArrayList<BlockPos> blocksAround = getBlocksAroundPos(blockPos);
        ArrayList<Integer> skyLightLevels = new ArrayList<>();
        for (BlockPos pos : blocksAround) {
            if (trap.level.getBlockState(pos).getBlock() == Blocks.AIR) {
                int skyLightLevel = super.getSkyLightLevel(trap, pos);
                skyLightLevels.add(skyLightLevel);
            }
        }
        return skyLightLevels.size() > 2 ?
                Collections.max(skyLightLevels) :
                super.getSkyLightLevel(trap, blockPos);
    }

    public void render(KineticTrapEntity entity, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
        IVertexBuilder ivertexbuilder = p_225623_5_.getBuffer(this.model.renderType(TEXTURE_LOCATION));
        if (!ClientTimeStopHandler.isTimeStoppedStatic() && !mc.isPaused()){
            this.model.setupAnim(entity, p_225623_2_, p_225623_3_, p_225623_2_, p_225623_3_, p_225623_6_);
        }
        this.model.renderToBuffer(p_225623_4_, ivertexbuilder, p_225623_6_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        super.render(entity, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
    }
    private static ArrayList<BlockPos> getBlocksAroundPos(BlockPos pos){
        ArrayList<BlockPos> blockPosList = new ArrayList<>();
        for (int yOffset = -1; yOffset <= 1; yOffset++) {
            for (int xOffset = -1; xOffset <= 1; xOffset++) {
                for (int zOffset = -1; zOffset <= 1; zOffset++) {
                    blockPosList.add(pos.offset(xOffset, yOffset, zOffset));
                }
            }
        }
        return blockPosList;
    }
}
