package org.fuseleaf.minecarttrainsfork.chaining;

import java.util.UUID;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;

import org.jetbrains.annotations.Nullable;

public class Link {

    private Link() {}

    public static void setChainedParent(@Nullable AbstractMinecart newParent, Chainable icu) {
        if(newParent != null) {
            @Nullable UUID parentUUID = newParent.getUUID();
            icu.setParentUUID(parentUUID);
        } else {
            @Nullable UUID parentUUID = null;
            icu.setParentUUID(parentUUID);
        }
    }

    public static void setChainedChild(@Nullable AbstractMinecart newChild, Chainable icu) {
        if(newChild != null) {
            @Nullable UUID childUUID = newChild.getUUID();
            icu.setChildUUID(childUUID);
        } else {
            @Nullable UUID childUUID = null;
            icu.setChildUUID(childUUID);
        }
    }
}
