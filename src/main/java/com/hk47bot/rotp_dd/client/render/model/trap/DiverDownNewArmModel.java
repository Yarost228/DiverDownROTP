package com.hk47bot.rotp_dd.client.render.model.trap;

import com.github.standobyte.jojo.client.render.entity.model.stand.HumanoidStandModel;
import com.hk47bot.rotp_dd.entity.trap.KineticTrapEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.HandSide;

public class DiverDownNewArmModel<T extends KineticTrapEntity> extends EntityModel<T> {
    private final ModelRenderer armXRot;
    private final ModelRenderer arm = HumanoidStandModel.createBasic().getArmNoXRot(HandSide.LEFT);
    private final ModelRenderer arm_move = HumanoidStandModel.createBasic().getArm(HandSide.LEFT);
    public DiverDownNewArmModel(){
        armXRot = new ModelRenderer(this);
        armXRot.addChild(arm);
        arm_move.setPos(0.0F, 0.0F, 0.0F);
        arm.addChild(arm_move);
        arm_move.texOffs(56, 54).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.01F, false);
        arm_move.texOffs(0, 58).addBox(-2.025F, -2.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.251F, false);
        arm_move.texOffs(16, 62).addBox(-2.0F, 4.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, false);
        arm_move.texOffs(64, 20).addBox(-2.0F, 4.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.25F, false);
    }
    @Override
    public void setupAnim(KineticTrapEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        if (entity.isActive){
            arm.visible = true;
            arm.xRot = (entity.getViewXRot(1) * ((float)Math.PI / 180F) + 202.5F);
            arm.yRot = -entity.getViewYRot(1) * ((float)Math.PI / 180F);
            arm_move.setPos(0, -entity.attackTime, 0);
        }
        else {
            arm.visible = false;
        }
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        armXRot.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
