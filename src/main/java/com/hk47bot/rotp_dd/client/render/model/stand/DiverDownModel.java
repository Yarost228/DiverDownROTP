package com.hk47bot.rotp_dd.client.render.model.stand;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.client.render.entity.model.stand.HumanoidStandModel;
import com.github.standobyte.jojo.client.render.entity.pose.ModelPose;
import com.github.standobyte.jojo.client.render.entity.pose.RotationAngle;
import com.github.standobyte.jojo.client.render.entity.pose.anim.PosedActionAnimation;
import com.github.standobyte.jojo.entity.stand.StandPose;
import com.hk47bot.rotp_dd.entity.stand.stands.DiverDownEntity;


public class DiverDownModel extends HumanoidStandModel<DiverDownEntity> {


	public DiverDownModel() {

		super();
			
		addHumanoidBaseBoxes(null);

		texWidth = 128;
		texHeight = 128;

		head.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 6.0F, 8.0F, 0.0F, false);
		head.texOffs(56, 5).addBox(-4.0F, -5.0F, -2.0F, 8.0F, 5.0F, 6.0F, -0.1F, false);
		head.texOffs(53, 0).addBox(3.75F, -7.0F, -1.75F, 1.0F, 2.0F, 2.0F, 0.0F, false);
		head.texOffs(53, 4).addBox(3.75F, -9.5F, -1.25F, 1.0F, 3.0F, 1.0F, -0.1F, false);
		head.texOffs(0, 18).addBox(-4.0F, -1.5F, -3.0F, 8.0F, 1.0F, 1.0F, 0.0F, false);
		head.texOffs(44, 3).addBox(-5.0F, -1.5F, -3.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);
		head.texOffs(44, 3).addBox(4.0F, -1.5F, -3.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);
		head.texOffs(34, 11).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 2.0F, 3.0F, 0.1F, false);
		head.texOffs(35, 0).addBox(-3.5F, -3.0F, -4.0F, 3.0F, 3.0F, 1.0F, -0.01F, false);
		head.texOffs(25, 0).addBox(0.5F, -3.0F, -4.0F, 3.0F, 3.0F, 1.0F, -0.01F, false);

		torso.setPos(0.0F, -7.0F, 0.0F);
		torso.texOffs(0, 26).addBox(-4.0F, -5.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);
		torso.texOffs(24, 26).addBox(-4.0F, -5.0F, -2.45F, 8.0F, 4.0F, 1.0F, -0.2F, false);
		torso.texOffs(25, 31).addBox(-3.5F, -1.5F, -2.35F, 7.0F, 4.0F, 1.0F, -0.2F, false);
		torso.texOffs(28, 36).addBox(-2.0F, 2.0F, -2.25F, 4.0F, 4.0F, 1.0F, -0.2F, false);
		torso.texOffs(0, 43).addBox(1.0F, -5.0F, 2.0F, 3.0F, 9.0F, 3.0F, 0.0F, false);
		torso.texOffs(12, 43).addBox(-4.0F, -5.0F, 2.0F, 3.0F, 9.0F, 3.0F, 0.0F, false);
		torso.texOffs(22, 41).addBox(-4.0F, 6.0F, -2.0F, 8.0F, 1.0F, 4.0F, 0.1F, false);

		leftArm.texOffs(22, 101).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, false);
		leftArm.texOffs(22, 93).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.1F, false);

		leftArmJoint.texOffs(24, 111).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, -0.1F, false);

		leftForeArm.texOffs(22, 117).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, false);

		rightArm.texOffs(0, 93).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, 0.1F, false);
		rightArm.texOffs(0, 101).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, false);

		rightArmJoint.texOffs(2, 111).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, -0.1F, false);

		rightForeArm.texOffs(0, 117).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, false);

		leftLeg.texOffs(67, 101).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, false);

		leftLegJoint.texOffs(69, 121).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, -0.1F, false);
		leftLegJoint.texOffs(71, 96).addBox(-1.5F, -1.75F, -2.5F, 3.0F, 3.0F, 1.0F, -0.2F, false);

		leftLowerLeg.texOffs(67, 111).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, false);

		rightLeg.texOffs(45, 101).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, false);

		rightLegJoint.texOffs(47, 121).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, -0.1F, false);
		rightLegJoint.texOffs(49, 96).addBox(-1.5F, -1.75F, -2.5F, 3.0F, 3.0F, 1.0F, -0.2F, false);

		rightLowerLeg.texOffs(45, 111).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, false);
	}

	@Override
	protected RotationAngle[][] initSummonPoseRotations() {
		return new RotationAngle[][] {
				new RotationAngle[] {
						RotationAngle.fromDegrees(head, 0, -15F, 0),
						RotationAngle.fromDegrees(body, -10F, -10F, 0),
						RotationAngle.fromDegrees(upperPart, 0, 0, 0),
						RotationAngle.fromDegrees(leftArm, 10F, 0, -2.5F),
						RotationAngle.fromDegrees(leftForeArm, -15F, 0, 2.5F),
						RotationAngle.fromDegrees(rightArm, 10F, 0, 2.5F),
						RotationAngle.fromDegrees(rightForeArm, -2.5F, 0, 2.5F),
						RotationAngle.fromDegrees(leftLeg, 7.5F, 0, -5F),
						RotationAngle.fromDegrees(leftLowerLeg, 2.5F, 0, 5F),
						RotationAngle.fromDegrees(rightLeg, 5F, 0, 5F),
						RotationAngle.fromDegrees(rightLowerLeg, 2.5F, 0, -5)
				},
				new RotationAngle[] {
						RotationAngle.fromDegrees(head, 0, 0, 0),
						RotationAngle.fromDegrees(body, 5F, -20F, 0),
						RotationAngle.fromDegrees(upperPart, 0, 0, 0),
						RotationAngle.fromDegrees(leftArm, 0, 0, 0),
						RotationAngle.fromDegrees(leftForeArm, -7.5F, 0, 0),
						RotationAngle.fromDegrees(rightArm, 0, -50F, 20F),
						RotationAngle.fromDegrees(rightForeArm, -40F, 0, 0),
						RotationAngle.fromDegrees(leftLeg, -15F, -15F, 0),
						RotationAngle.fromDegrees(leftLowerLeg, 10F, 0, 0),
						RotationAngle.fromDegrees(rightLeg, -7.5F, 15F, 0),
						RotationAngle.fromDegrees(rightLowerLeg, 2.5F, 0, 0)
				}
		};
	}

	@Override
	protected void initActionPoses() {
        actionAnim.put(StandPose.RANGED_ATTACK, new PosedActionAnimation.Builder<DiverDownEntity>()
                .addPose(StandEntityAction.Phase.BUTTON_HOLD, new ModelPose<>(new RotationAngle[] {
                        new RotationAngle(body, 0.0F, -0.48F, 0.0F),
                        new RotationAngle(leftArm, 0.0F, 0.0F, 0.0F),
                        new RotationAngle(leftForeArm, 0.0F, 0.0F, 0.0F),
                        new RotationAngle(rightArm, -1.0908F, 0.0F, 1.5708F), 
                        new RotationAngle(rightForeArm, 0.0F, 0.0F, 0.0F)
                }))
                .build(idlePose));

		super.initActionPoses();
	}

	@Override
	protected ModelPose<DiverDownEntity> initIdlePose() {
		return new ModelPose<>(new RotationAngle[] {
				RotationAngle.fromDegrees(body, -5F, 30F, 0.0F),
				RotationAngle.fromDegrees(upperPart, 0.0F, 0.0F, 0.0F),
				RotationAngle.fromDegrees(torso, 0.0F, 0.0F, 0.0F),
				RotationAngle.fromDegrees(leftArm, 12.5F, -30F, -15F),
				RotationAngle.fromDegrees(leftForeArm, -12.5F, 0.0F, 0.0F),
				RotationAngle.fromDegrees(rightArm, 10F, 30F, 15F),
				RotationAngle.fromDegrees(rightForeArm, -15F, 0.0F, 0.0F),
				RotationAngle.fromDegrees(leftLeg, 20F, 0.0F, 0.0F),
				RotationAngle.fromDegrees(leftLowerLeg, 0.0F, 0.0F, 0.0F),
				RotationAngle.fromDegrees(rightLeg, 0.0F, 0.0F, 0.0F),
				RotationAngle.fromDegrees(rightLowerLeg, 5F, 0.0F, 0.0F)
		});
	}

	@Override
	protected ModelPose<DiverDownEntity> initIdlePose2Loop() {
		return new ModelPose<>(new RotationAngle[] {
				RotationAngle.fromDegrees(leftArm, 7.5F, -30F, -15F),
				RotationAngle.fromDegrees(leftForeArm, -17.5F, 0.0F, 0.0F),
				RotationAngle.fromDegrees(rightArm, 12.5F, 30F, 15F),
				RotationAngle.fromDegrees(rightForeArm, -17.5F, 0.0F, 0.0F)
		});
	}
}
