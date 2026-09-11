package org.fuseleaf.minecarttrainsfork;

import org.fuseleaf.minecarttrainsfork.initializer.Initializer;
import net.fabricmc.api.ModInitializer;

public class MinecartTrainsFork implements ModInitializer {

    public static final String MOD_ID = "minecart-trains-fork";

    @Override
    public void onInitialize() {
        Initializer.init();
    }
}
