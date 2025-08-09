package com.hk47bot.rotp_dd.action.stand;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.action.stand.StandEntityBlock;

public class DiverDownBlock extends StandEntityBlock {
    public DiverDownBlock(StandEntityAction.Builder builder){
        super(builder.standOffsetFromUser(0, 0.75));
    }
}
