package org.fuseleaf.minecarttrainsfork.chaining;

import java.util.UUID;

import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import org.jspecify.annotations.Nullable;

public interface Chainable {

    /* Interfaces cannot have constructors */

    UUID getParentUUID();

    void setParentUUID(@Nullable UUID uuid);

    UUID getChildUUID();

    void setChildUUID(@Nullable UUID uuid);

    // 默认实现：客户端断开时清理引用和 ID
    default @Nullable AbstractMinecart getChainedParent() {
        return null;
    }

    default void setChainedParent(@Nullable AbstractMinecart newParent) {}

    default @Nullable AbstractMinecart getChainedChild() {
        return null;
    }

    default void setChainedChild(@Nullable AbstractMinecart newChild) {}

    default AbstractMinecart getAbstractMinecartEntity() {
        return (AbstractMinecart) this;
    }

    // 建立连接：先清理旧关系，再建立新关系
    static void setChainedParentChild(Chainable parent, Chainable child) {
        unsetChainedParentChild(parent, (Chainable)parent.getChainedChild());
        unsetChainedParentChild(child, (Chainable)child.getChainedParent());
        parent.setChainedChild(child.getAbstractMinecartEntity());
        child.setChainedParent(parent.getAbstractMinecartEntity());
    }

    // 断开连接：同时清理引用和 ID
    static void unsetChainedParentChild(@Nullable Chainable parent, @Nullable Chainable child) {
        if (parent != null) {
            parent.setChainedChild(null);
        }

        if (child != null) {
            child.setChainedParent(null);
        }
    }
}
