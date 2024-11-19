package com.hk47bot.rotp_dd.client.render.model.trap;// Made with Blockbench 4.11.1
// Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.hk47bot.rotp_dd.entity.trap.KineticTrapEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class DiverDownArmModel<T extends KineticTrapEntity> extends EntityModel<T> {
	private final ModelRenderer arm;
	private final ModelRenderer arm_move;


	public DiverDownArmModel() {
		texWidth = 32;
		texHeight = 32;

		arm = new ModelRenderer(this);
		arm.setPos(0, 2, 0);

		arm_move = new ModelRenderer(this);
		arm.addChild(arm_move);
		arm_move.texOffs(0, 0).addBox(-2.0F, -2.0F, -4.0F, 4.0F, 4.0F, 4.0F, 0.1F, false);
		arm_move.texOffs(0, 8).addBox(-2.0F, -2.0F, -12.0F, 4.0F, 4.0F, 12.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(KineticTrapEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		if (entity.isActive){
			arm.visible = true;
			arm.xRot =  entity.getViewXRot(1) * ((float)Math.PI / 180F) - 135F;
			arm.yRot = - entity.getViewYRot(1) * ((float)Math.PI / 180F);
			arm_move.setPos(0, 0, entity.attackTime);
		}
		else {
			arm.visible = false;
		}
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		arm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}