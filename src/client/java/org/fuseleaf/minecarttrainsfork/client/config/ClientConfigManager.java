package org.fuseleaf.minecarttrainsfork.client.config;

import net.minecraft.core.particles.SimpleParticleType;

import org.fuseleaf.minecarttrainsfork.client.extension.config.ClientConfigData;

import org.jspecify.annotations.NonNull;

public class ClientConfigManager {

    private static ClientConfigData config;

    public static void setConfigData(ClientConfigData configData) {
        if (configData != null) {
            config = configData;
        }
    }

    public static boolean isConfigAvailable() {
        return config != null;
    }

    /* # Get Configuration */

    /* ## General */

    public static boolean shouldShowChain() {
        return isConfigAvailable() ? config.showChain : true;
    }

    public static boolean shouldShowHeadParticle() {
        return isConfigAvailable() ? config.showHeadParticle : true;
    }

    public static boolean shouldShowLinkParticle() {
        return isConfigAvailable() ? config.showLinkParticle : false;
    }

    public static boolean shouldShowNotice() {
        return isConfigAvailable() ? config.showNotice : true;
    }

    /* ## Advanced */

    public static double getChainWidth() {
        return config.chainWidth * 0.01;
    }

    public static boolean isAlwaysShowHeadParticle() {
        return config.alwaysShowHeadParticle;
    }

    public static @NonNull SimpleParticleType getHeadParticleType() {
        return config.headParticleType.getType();
    }

    public static int getHeadParticleCount() {
        return config.headParticleCount;
    }

    public static double getHeadParticleHeight() {
        return config.headParticleHeight * 0.1;
    }

    public static int getHeadParticleInterval() {
        return config.headParticleInterval;
    }

    public static @NonNull SimpleParticleType getLinkParticleType() {
        return config.linkParticleType.getType();
    }

    public static double getLinkParticleHeight() {
        return config.linkParticleHeight * 0.1;
    }

    public static int getLinkParticleInterval() {
        return config.linkParticleInterval;
    }
}
