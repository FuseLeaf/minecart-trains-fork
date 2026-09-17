package org.fuseleaf.minecarttrainsfork.network;

import java.util.UUID;

import org.fuseleaf.minecarttrainsfork.MinecartTrainsFork;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record RelationshipPayload(@Nullable UUID childUUID, @Nullable UUID parentUUID) implements CustomPacketPayload {

    public static @NonNull final Type<RelationshipPayload> TYPE = new Type<>(
        Identifier.fromNamespaceAndPath(MinecartTrainsFork.MOD_ID, "relationship")
    );

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static final @NonNull StreamCodec<FriendlyByteBuf, RelationshipPayload> CODEC = StreamCodec.of(

        // Write
        (buf, payload) -> {
            UUID child = payload.childUUID();
            UUID parent = payload.parentUUID();

            buf.writeUUID(child != null ? child : new UUID(0L, 0L));
            buf.writeUUID(parent != null ? parent : new UUID(0L, 0L));
        },

        // Read
        buf -> {
            UUID child = buf.readUUID();
            UUID parent = buf.readUUID();

            if (child.getMostSignificantBits() == 0L && child.getLeastSignificantBits() == 0L) {
                child = null;
            }

            if (parent.getMostSignificantBits() == 0L && parent.getLeastSignificantBits() == 0L) {
                parent = null;
            }

            return new RelationshipPayload(child, parent);
        }
    );
}
