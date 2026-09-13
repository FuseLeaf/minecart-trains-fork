package org.fuseleaf.minecarttrainsfork.client;

import org.fuseleaf.minecarttrainsfork.client.initializer.ClientInitializer;
import net.fabricmc.api.ClientModInitializer;

public class MinecartTrainsForkClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientInitializer.init();
    }
}
