package com.hk47bot.rotp_dd.client.render.renderer.projectile;

import com.github.standobyte.jojo.client.render.entity.renderer.SimpleEntityRenderer;
import com.hk47bot.rotp_dd.RotpDiverDownAddon;
import com.hk47bot.rotp_dd.client.render.model.projectile.BoneShardModel;
import com.hk47bot.rotp_dd.entity.projectile.BoneShardEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class BoneShardRenderer extends SimpleEntityRenderer<BoneShardEntity, BoneShardModel> {
    public BoneShardRenderer(EntityRendererManager rendererManager){
        super(rendererManager, new BoneShardModel (), new ResourceLocation(RotpDiverDownAddon.MOD_ID, "textures/entity/bone_shard.png"));
    }
}
