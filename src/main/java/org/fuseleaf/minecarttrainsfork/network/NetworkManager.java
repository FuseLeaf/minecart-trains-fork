package org.fuseleaf.minecarttrainsfork.network;

import java.util.UUID;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

public class NetworkManager {

    public static void sendRelationshipPayload(UUID childUUID, UUID parentUUID, Level level) {
        if (level == null || !(level instanceof ServerLevel serverLevel)) {
            return;
        }

        for (ServerPlayer p : serverLevel.getServer().getPlayerList().getPlayers()) {
            if (p != null) {
                ServerPlayNetworking.send(p, new RelationshipPayload(childUUID, parentUUID));
            }
        }
    }
}
