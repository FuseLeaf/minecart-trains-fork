package org.fuseleaf.minecarttrainsfork.train;

import org.fuseleaf.minecarttrainsfork.chaining.Chainable;
import org.fuseleaf.minecarttrainsfork.config.ConfigManager;
import org.fuseleaf.minecarttrainsfork.network.NetworkManager;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

public class TrainBehavior {

    public static void tick(AbstractMinecart cart) {
        if (cart == null || cart.level().isClientSide()) {
            return;
        }

        Chainable cartChainable = (Chainable)cart;
        AbstractMinecart parentCart = cartChainable.getChainedParent();
        AbstractMinecart childCart = cartChainable.getChainedChild();

        if (parentCart != null) {
            double distance = parentCart.distanceTo(cart) - 1;

            if (distance <= 4) {
                Vec3 directionToParent = parentCart.position().subtract(cart.position()).normalize();

                double cartSpacing = ConfigManager.getCartSpacing();

                if (distance > cartSpacing) {
                    Vec3 parentVelocity = parentCart.getDeltaMovement();

                    if (parentVelocity.length() < 0.1) {
                        cart.setDeltaMovement(directionToParent.scale(0.05));
                    } else {
                        cart.setDeltaMovement(directionToParent.scale(parentVelocity.length()));
                        cart.setDeltaMovement(cart.getDeltaMovement().scale(distance));
                    }
                } else if (distance < cartSpacing - 0.2) {
                    cart.setDeltaMovement(directionToParent.scale(-0.05));
                } else {
                    cart.setDeltaMovement(Vec3.ZERO);
                }
            } else {
                if (ConfigManager.isEnabledBrakingAfterTrainSeparation()) {
                    AbstractMinecart currentCart = cart;
                    Chainable currentCartChainable = (Chainable) currentCart;
                    AbstractMinecart currentParentCart = currentCartChainable.getChainedParent();

                    while (currentParentCart != null) {
                        currentParentCart.setDeltaMovement(Vec3.ZERO);
                        currentCart.setDeltaMovement(Vec3.ZERO);

                        currentCart = currentParentCart;
                        currentParentCart = ((Chainable) currentCart).getChainedParent();
                    }
                }

                Chainable.unsetChainedParentChild((Chainable)parentCart, cartChainable);
                cart.spawnAtLocation((ServerLevel) cart.level(), new ItemStack(Items.IRON_CHAIN));

                NetworkManager.sendRelationshipPayload(cart.getUUID(), null, cart.level());
                NetworkManager.sendRelationshipPayload(null, parentCart.getUUID(), cart.level());

                return;
            }

            if (parentCart.isRemoved()) {
                Chainable.unsetChainedParentChild((Chainable)parentCart, cartChainable);

                NetworkManager.sendRelationshipPayload(cart.getUUID(), null, cart.level());
                NetworkManager.sendRelationshipPayload(null, parentCart.getUUID(), cart.level());
            }
        }

        if (childCart != null && childCart.isRemoved()) {
            Chainable.unsetChainedParentChild(cartChainable, (Chainable)childCart);

            NetworkManager.sendRelationshipPayload(childCart.getUUID(), null, cart.level());
            NetworkManager.sendRelationshipPayload(null, childCart.getUUID(), cart.level());
        }
    }

    public static double setMaxSpeed(AbstractMinecart cart, double maxSpeed) {
        if (cart == null || cart.level().isClientSide()) {
            return maxSpeed;
        }

        Chainable cartChainable = (Chainable)cart;
        AbstractMinecart parentCart = cartChainable.getChainedParent();

        if (parentCart != null) {
            double distance = parentCart.distanceTo(cart) - 1;
            double cartSpacing = ConfigManager.getCartSpacing();

            if (distance > cartSpacing) {
                maxSpeed *= 1.5;
            }
        }

        return maxSpeed;
    }
}
