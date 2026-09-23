package org.fuseleaf.minecarttrainsfork;

import org.fuseleaf.minecarttrainsfork.initializer.Initializer;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(MinecartTrainsFork.MOD_ID)
public class MinecartTrainsFork {

    public static final String MOD_ID = "minecart_trains_fork";

    public MinecartTrainsFork(IEventBus iEventBus) {
        Initializer.init(iEventBus);
    }
}
