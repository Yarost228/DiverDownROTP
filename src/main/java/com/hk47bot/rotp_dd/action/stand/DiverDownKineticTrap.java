package com.hk47bot.rotp_dd.action.stand;

import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.IStandPhasedAction;
import com.github.standobyte.jojo.action.stand.StandEntityHeavyAttack;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.hk47bot.rotp_dd.entity.stand.stands.DiverDownEntity;
import com.hk47bot.rotp_dd.entity.trap.KineticTrapEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class DiverDownKineticTrap extends StandEntityHeavyAttack implements IStandPhasedAction {

    public DiverDownKineticTrap(StandEntityHeavyAttack.Builder builder){
        super(builder.standOffsetFront());
    }

    @Override
    public TargetRequirement getTargetRequirement() {
        return TargetRequirement.BLOCK;
    }

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        DiverDownEntity diverDown = (DiverDownEntity) standEntity;
        ActionTarget target = diverDown.aimWithThisOrUser(5, task.getTarget());
        if (target.getBlockPos() != null){
            KineticTrapEntity kineticTrap = new KineticTrapEntity(world);
            if (world.getBlockState(target.getBlockPos()).isFaceSturdy(world, target.getBlockPos(), Direction.UP)){
                kineticTrap.moveTo(Vector3d.atCenterOf(target.getBlockPos()));
            }
            else {
                kineticTrap.moveTo(Vector3d.atBottomCenterOf(target.getBlockPos()).add(0, -0.5, 0));
            }
            kineticTrap.setOwner(standEntity.getUser());
            world.addFreshEntity(kineticTrap);
        }
    }
}
