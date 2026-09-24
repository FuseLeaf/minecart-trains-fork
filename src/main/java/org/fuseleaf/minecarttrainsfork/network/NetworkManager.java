package org.fuseleaf.minecarttrainsfork.network;

import java.util.UUID;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

import net.neoforged.neoforge.network.PacketDistributor;

public class NetworkManager {

    public static void sendRelationshipPayload(UUID childUUID, UUID parentUUID, Level level) {
        if (!(level instanceof ServerLevel)) {
            return;
        }

        PacketDistributor.sendToAllPlayers(new RelationshipPayload(childUUID, parentUUID));
    }
}
