package com.hk47bot.rotp_dd.action.stand;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.action.stand.StandEntityBlock;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;

public class DiverDownBlock extends StandEntityBlock {
    public DiverDownBlock(StandEntityAction.Builder builder){
        super(builder.noResolveUnlock());
    }
    @Override
    public boolean isUnlocked(IStandPower power){return true;}
}
