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

    public static boolean isEnabledLinkLine() {
        return isConfigAvailable() ? config.enabledLinkLine : true;
    }

    public static boolean isEnabledHeadParticle() {
        return isConfigAvailable() ? config.enabledHeadParticle : true;
    }

    public static boolean isEnabledLinkParticle() {
        return isConfigAvailable() ? config.enabledLinkParticle : false;
    }

    public static boolean isEnabledNotice() {
        return isConfigAvailable() ? config.enabledNotice : true;
    }

    /* */

    public static double getLineWidth() {
        return config.lineWidth * 0.01;
    }

    public static boolean isAlwaysRenderHeadParticle() {
        return config.alwaysRenderHeadParticle;
    }

    public static @NonNull SimpleParticleType getHeadParticleType() {
        return config.headParticleType.getType();
    }

    public static int getHeadParticleNumber() {
        return config.headParticleNumber;
    }

    public static double getHeadParticleHeight() {
        return config.headParticleHeight * 0.1;
    }

    public static int getHeadParticleCycle() {
        return config.headParticleCycle;
    }

    public static @NonNull SimpleParticleType getLinkParticleType() {
        return config.linkParticleType.getType();
    }

    public static double getLinkParticleHeight() {
        return config.linkParticleHeight * 0.1;
    }

    public static int getLinkParticleCycle() {
        return config.linkParticleCycle;
    }
}
