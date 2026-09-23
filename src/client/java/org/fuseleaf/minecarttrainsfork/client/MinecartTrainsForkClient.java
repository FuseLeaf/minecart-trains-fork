package org.fuseleaf.minecarttrainsfork.client;

import org.fuseleaf.minecarttrainsfork.MinecartTrainsFork;
import org.fuseleaf.minecarttrainsfork.client.initializer.ClientInitializer;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(value = MinecartTrainsFork.MOD_ID, dist = Dist.CLIENT)
public class MinecartTrainsForkClient {

    public MinecartTrainsForkClient(IEventBus iEventBus) {
        ClientInitializer.init(iEventBus);
    }
}
