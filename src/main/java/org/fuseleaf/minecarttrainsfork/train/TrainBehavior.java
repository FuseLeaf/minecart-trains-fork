package org.fuseleaf.minecarttrainsfork.train;

import org.fuseleaf.minecarttrainsfork.chaining.Chainable;
import org.fuseleaf.minecarttrainsfork.config.ConfigManager;
import org.fuseleaf.minecarttrainsfork.network.NetworkManager;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

public class TrainBehavior {

    public static void tick(AbstractMinecart entity) {
        if (!entity.level().isClientSide()) {

            Chainable entityIChainable = (Chainable)entity;

            if (entityIChainable.getChainedParent() != null) {
                double distance = entityIChainable.getChainedParent().distanceTo(entity) - 1;

                if (distance <= 4) {
                    Vec3 directionToParent = entityIChainable.getChainedParent().position().subtract(entity.position()).normalize();

                    double cartSpacing = ConfigManager.getCartSpacing();

                    if (distance > cartSpacing) {
                        Vec3 parentVelocity = entityIChainable.getChainedParent().getDeltaMovement();

                        if (parentVelocity.length() < 0.1) {
                            entity.setDeltaMovement(directionToParent.scale(0.05));
                        } else {
                            entity.setDeltaMovement(directionToParent.scale(parentVelocity.length()));
                            entity.setDeltaMovement(entity.getDeltaMovement().scale(distance));
                        }
                    } else if (distance < cartSpacing - 0.2) {
                        entity.setDeltaMovement(directionToParent.scale(-0.05));
                    } else {
                        entity.setDeltaMovement(Vec3.ZERO);
                    }
                } else {
                    AbstractMinecart parentCart = entityIChainable.getChainedParent();

                    if (ConfigManager.isEnabledBrakingAfterTrainSeparation()) {
                        for (AbstractMinecart cart = entity; ((Chainable)cart).getChainedParent() != null; cart = (AbstractMinecart)((Chainable)cart).getChainedParent()) {
                            AbstractMinecart parent = ((Chainable)cart).getChainedParent();
                            parent.setDeltaMovement(Vec3.ZERO);
                            cart.setDeltaMovement(Vec3.ZERO);
                        }
                    }

                    Chainable.unsetChainedParentChild((Chainable)parentCart, entityIChainable);
                    entity.spawnAtLocation((ServerLevel) entity.level(), new ItemStack(Items.IRON_CHAIN));

                    NetworkManager.sendRelationshipPayload(entity.getUUID(), null, entity.level());
                    NetworkManager.sendRelationshipPayload(null, parentCart.getUUID(), entity.level());

                    return;
                }

                if (entityIChainable.getChainedParent().isRemoved()) {
                    AbstractMinecart parentCart = entityIChainable.getChainedParent();

                    Chainable.unsetChainedParentChild((Chainable)parentCart, entityIChainable);

                    NetworkManager.sendRelationshipPayload(entity.getUUID(), null, entity.level());
                    NetworkManager.sendRelationshipPayload(null, parentCart.getUUID(), entity.level());
                }
            }

            if (entityIChainable.getChainedChild() != null && entityIChainable.getChainedChild().isRemoved()) {
                AbstractMinecart childCart = entityIChainable.getChainedChild();

                Chainable.unsetChainedParentChild(entityIChainable, (Chainable)childCart);

                NetworkManager.sendRelationshipPayload(childCart.getUUID(), null, entity.level());
                NetworkManager.sendRelationshipPayload(null, childCart.getUUID(), entity.level());
            }

            for (Entity otherEntity : entity.level().getEntities(entity, entity.getBoundingBox().inflate(0.1))) {

                if (
                    otherEntity instanceof AbstractMinecart otherCart
                    && entityIChainable.getChainedParent() != null
                    && entityIChainable.getChainedChild() != null
                    && entityIChainable.getChainedChild() instanceof AbstractMinecart childCart
                    && !otherCart.equals(childCart)
                ) {
                    otherCart.setDeltaMovement(entity.getDeltaMovement());
                }
            }
        }
    }
}
