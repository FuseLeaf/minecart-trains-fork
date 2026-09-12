package org.fuseleaf.minecarttrainsfork.initializer;

import org.fuseleaf.minecarttrainsfork.MinecartTrainsFork;
import org.fuseleaf.minecarttrainsfork.chaining.ChainableComponents;
import org.fuseleaf.minecarttrainsfork.chaining.Chaining;
import org.fuseleaf.minecarttrainsfork.config.ConfigManager;
import org.fuseleaf.minecarttrainsfork.extension.config.ConfigData;
import org.fuseleaf.minecarttrainsfork.network.NetworkManager;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

public class Initializer {

    public static void init() {
        Config.init();
        Components.init();
        Events.init();
        Network.init();

        // For Development
        // ParticleEnumGenerator.generateEnum();
    }

    private static class Config {

        private static void init() {
            try {
                Class.forName("me.shedaniel.autoconfig.AutoConfig");

                AutoConfig.register(ConfigData.class, GsonConfigSerializer::new);
                ConfigManager.setConfigData(AutoConfig.getConfigHolder(ConfigData.class).getConfig());
            } catch (ClassNotFoundException e) {}
        }
    }

    private static class Components {

        @SuppressWarnings("null")
        private static void init() {
            Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, Identifier.fromNamespaceAndPath(MinecartTrainsFork.MOD_ID, "parent_id"), ChainableComponents.PARENT_ID);
        }
    }

    private static class Events {

        private static void init() {
            UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
                return Chaining.handle(entity, player, hand, world, ChainableComponents.PARENT_ID);
            });
        }
    }

    private static class Network {

        private static void init() {
            PayloadTypeRegistry.clientboundPlay().register(NetworkManager.RelationshipPayload.TYPE, NetworkManager.RelationshipPayload.CODEC);
        }
    }
}
