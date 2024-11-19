package com.hk47bot.rotp_dd.action.stand;

import com.github.standobyte.jojo.action.stand.StandEntityHeavyAttack;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.hk47bot.rotp_dd.entity.stand.stands.DiverDownEntity;
import com.hk47bot.rotp_dd.init.InitStands;

import javax.annotation.Nullable;

public class DiverDownHeavyPunch extends StandEntityHeavyAttack {
    public DiverDownHeavyPunch(StandEntityHeavyAttack.Builder builder){
        super(builder);
    }
    @Override
    public StandEntityHeavyAttack getFinisherVariationIfPresent(IStandPower power, @Nullable StandEntity standEntity) {
        if (standEntity != null){
            DiverDownEntity diverDown = (DiverDownEntity) standEntity;
            if (diverDown.isInside() && standEntity.willHeavyPunchBeFinisher()){
                return InitStands.DIVER_DOWN_MOB_DISASSEMBLE.get();
            }
            else {
                return super.getFinisherVariationIfPresent(power, diverDown);
            }
        }
        return this;
    }
}
