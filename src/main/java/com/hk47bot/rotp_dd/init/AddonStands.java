package com.hk47bot.rotp_dd.init;

import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.github.standobyte.jojo.init.power.stand.EntityStandRegistryObject.EntityStandSupplier;
import com.github.standobyte.jojo.power.impl.stand.stats.StandStats;
import com.github.standobyte.jojo.power.impl.stand.type.EntityStandType;
import com.hk47bot.rotp_dd.entity.stand.stands.DiverDownEntity;

public class AddonStands {

    public static final EntityStandSupplier<EntityStandType<StandStats>, StandEntityType<DiverDownEntity>> 
    DIVER_DOWN = new EntityStandSupplier<>(InitStands.STAND_DIVER_DOWN);
}
