package org.fuseleaf.minecarttrainsfork.chaining;

import java.util.UUID;

import org.fuseleaf.minecarttrainsfork.MinecartTrainsFork;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class ChainableComponents {

    private ChainableComponents() {}

    public static final DeferredRegister.DataComponents REGISTRAR =
        DeferredRegister.createDataComponents(
            Registries.DATA_COMPONENT_TYPE,
            MinecartTrainsFork.MOD_ID
        );

        @SuppressWarnings("null")
        public static final DeferredHolder<DataComponentType<?>, DataComponentType<UUID>> PARENT_ID = REGISTRAR.registerComponentType(
            "parent_id",
            builder -> builder
                .persistent(
                    RecordCodecBuilder.create(uuidInstance -> uuidInstance.group(
                            Codec.LONG.fieldOf("most_sig_bits").forGetter(UUID::getMostSignificantBits),
                            Codec.LONG.fieldOf("least_sig_bits").forGetter(UUID::getLeastSignificantBits)
                        ).apply(uuidInstance, UUID::new)
                    )
                )
                .networkSynchronized(
                    StreamCodec.composite(
                        ByteBufCodecs.VAR_LONG,
                        UUID::getMostSignificantBits,
                        ByteBufCodecs.VAR_LONG,
                        UUID::getLeastSignificantBits,
                        UUID::new
                    )
                )
    );
}
