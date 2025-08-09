// Made with Blockbench 4.11.2
// Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
// Paste this class into your mod and generate all required imports
package com.hk47bot.rotp_dd.client.render.model.projectile;

import com.hk47bot.rotp_dd.entity.projectile.BoneShardEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class BoneShardModel extends EntityModel<BoneShardEntity> {
	private final ModelRenderer bone;

	public BoneShardModel() {
		texWidth = 16;
		texHeight = 16;

		bone = new ModelRenderer(this);
		bone.setPos(0, -1, 0);
		bone.texOffs(0, 4).addBox(0.0F, -1.0F, -2.0F, 0.0F, 2.0F, 4.0F, 0.0F, false);
		bone.texOffs(0, 0).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 0.0F, 4.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(BoneShardEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float yRotation, float xRotation){
		bone.yRot = yRotation * ((float)Math.PI / 180F);
		bone.xRot = xRotation * ((float)Math.PI / 180F);
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		bone.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}