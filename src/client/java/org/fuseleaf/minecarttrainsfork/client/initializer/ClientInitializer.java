package org.fuseleaf.minecarttrainsfork.client.initializer;

import java.util.UUID;

import org.fuseleaf.minecarttrainsfork.chaining.Chainable;
import org.fuseleaf.minecarttrainsfork.client.config.ClientConfigManager;
import org.fuseleaf.minecarttrainsfork.client.extension.config.ClientConfigData;
import org.fuseleaf.minecarttrainsfork.network.NetworkManager;
import org.fuseleaf.minecarttrainsfork.network.RelationshipPayload;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;

public class ClientInitializer {

    public static void init() {
        Config.init();
        Network.init();
    }

    private static class Config {

        private static void init() {
            try {
                Class.forName("me.shedaniel.autoconfig.AutoConfig");

                AutoConfig.register(ClientConfigData.class, GsonConfigSerializer::new);
                ClientConfigManager.setConfigData(
                    AutoConfig.getConfigHolder(ClientConfigData.class).getConfig()
                );
            } catch (ClassNotFoundException e) {}
        }
    }

    private static class Network {

        private static void init() {
            ClientPlayNetworking.registerGlobalReceiver(
                RelationshipPayload.TYPE,
                (payload, context) -> {
                    context.client().execute(() -> {
                        ClientLevel clientWorld = Minecraft.getInstance().level;

                        if (clientWorld != null) {
                            UUID childUUID = payload.childUUID();
                            UUID parentUUID = payload.parentUUID();

                            if (childUUID != null) {
                                Chainable childChainableUtil = (Chainable) clientWorld.getEntity(childUUID);

                                if (childChainableUtil != null) {
                                    childChainableUtil.setParentUUID(parentUUID);
                                }
                            }

                            if (parentUUID != null) {
                                Chainable parentChainableUtil = (Chainable) clientWorld.getEntity(parentUUID);

                                if (parentChainableUtil != null) {
                                    parentChainableUtil.setChildUUID(childUUID);
                                }
                            }
                        }
                    });
                }
            );
        }
    }
}
