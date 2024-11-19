package com.hk47bot.rotp_dd.client.render.renderer.entity;

import com.github.standobyte.jojo.client.ClientTimeStopHandler;
import com.hk47bot.rotp_dd.RotpDiverDownAddon;
import com.hk47bot.rotp_dd.client.render.model.trap.DiverDownArmModel;
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
    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(RotpDiverDownAddon.MOD_ID, "textures/entity/trap/diver_down_arm.png");
    private final DiverDownArmModel<KineticTrapEntity> model = new DiverDownArmModel<>();
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
        return Collections.max(skyLightLevels);
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
        blockPosList.add(pos.above());
        blockPosList.add(pos.above().north());
        blockPosList.add(pos.above().south());
        blockPosList.add(pos.above().east());
        blockPosList.add(pos.above().east().north());
        blockPosList.add(pos.above().east().south());
        blockPosList.add(pos.above().west());
        blockPosList.add(pos.above().west().north());
        blockPosList.add(pos.above().west().south());
        blockPosList.add(pos);
        blockPosList.add(pos.north());
        blockPosList.add(pos.south());
        blockPosList.add(pos.east());
        blockPosList.add(pos.east().north());
        blockPosList.add(pos.east().south());
        blockPosList.add(pos.west());
        blockPosList.add(pos.west().north());
        blockPosList.add(pos.west().south());
        blockPosList.add(pos.below());
        blockPosList.add(pos.below().north());
        blockPosList.add(pos.below().south());
        blockPosList.add(pos.below().east());
        blockPosList.add(pos.below().east().north());
        blockPosList.add(pos.below().east().south());
        blockPosList.add(pos.below().west());
        blockPosList.add(pos.below().west().north());
        blockPosList.add(pos.below().west().south());
        return blockPosList;
    }
}
