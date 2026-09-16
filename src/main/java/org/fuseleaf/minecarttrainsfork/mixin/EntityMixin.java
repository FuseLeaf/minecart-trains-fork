package org.fuseleaf.minecarttrainsfork.mixin;

import org.fuseleaf.minecarttrainsfork.chaining.Chainable;
import org.fuseleaf.minecarttrainsfork.chaining.Connection;
import org.fuseleaf.minecarttrainsfork.network.NetworkManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import net.minecraft.world.level.Level;

@Mixin(Entity.class)
public class EntityMixin {

    @Inject(method = "startSeenByPlayer", at = @At("TAIL"))
    private void injectStartSeenByPlayer(ServerPlayer player, CallbackInfo ci) {
        Entity self = (Entity)(Object)this;

        if (self instanceof AbstractMinecart) {
            NetworkManager.sendRelationshipPayload(self.getUUID(), ((Chainable) self).getParentUUID(), player.level());
        }
    }

    @Inject(method = "remove", at = @At("TAIL"))
    private void injectRemove(Entity.RemovalReason reason, CallbackInfo ci) {
        Entity self = (Entity)(Object)this;

        if (self instanceof AbstractMinecart) {
            Chainable icu = (Chainable)(Object)this;
            Level world = ((Entity)(Object)this).level();

            if (!world.isClientSide()) {
                ServerLevel serverWorld = (ServerLevel)world;
                Connection.unlink(icu, serverWorld);
            }
        }
    }
}
