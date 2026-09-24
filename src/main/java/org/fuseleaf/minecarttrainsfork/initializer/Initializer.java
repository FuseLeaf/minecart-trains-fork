package org.fuseleaf.minecarttrainsfork.initializer;

import org.fuseleaf.minecarttrainsfork.chaining.ChainableComponents;
import org.fuseleaf.minecarttrainsfork.chaining.Chaining;
import org.fuseleaf.minecarttrainsfork.config.ConfigManager;
import org.fuseleaf.minecarttrainsfork.extension.config.ConfigData;
import org.fuseleaf.minecarttrainsfork.network.RelationshipPayload;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class Initializer {

    public static void init(IEventBus iEventBus) {
        Config.init();
        Components.init(iEventBus);
        Events.init();
        Network.init(iEventBus);

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

        private static void init(IEventBus iEventBus) {
            ChainableComponents.REGISTRAR.register(iEventBus);

        }
    }

    private static class Events {

        private static void init() {
            NeoForge.EVENT_BUS.addListener((PlayerInteractEvent.EntityInteract event) -> {
                Player player = event.getEntity();
                Entity entity = event.getTarget();
                InteractionHand hand = event.getHand();
                Level level = player.level();

                InteractionResult result = Chaining.handle(
                    entity,
                    player,
                    hand,
                    level,
                    ChainableComponents.PARENT_ID.get()
                );

                if (result != InteractionResult.PASS) {
                    event.setCancellationResult(result);
                    event.setCanceled(true);
                }
            });
        }
    }

    private static class Network {

        private static void init(IEventBus iEventBus) {
            iEventBus.addListener((RegisterPayloadHandlersEvent event) -> {
                PayloadRegistrar registrar = event.registrar("1");
                registrar.playToClient(RelationshipPayload.TYPE, RelationshipPayload.CODEC);
            });
        }
    }
}
