package org.fuseleaf.minecarttrainsfork.config;

import org.fuseleaf.minecarttrainsfork.extension.config.ConfigData;

public class ConfigManager {

    private static ConfigData config;

    public static void setConfigData(ConfigData configData) {
        if (configData != null) {
            config = configData;
        }
    }

    public static boolean isConfigAvailable() {
        return config != null;
    }

    /* Get Configuration */

    /* General */

    public static boolean isEnabledBrakingAfterTrainSeparation() {
        return isConfigAvailable() ? config.brakingAfterTrainSeparation : true;
    }

    public static double getCartSpacing() {
        return isConfigAvailable() ? config.cartSpacing / 10.0 : 0.5;
    }
}
