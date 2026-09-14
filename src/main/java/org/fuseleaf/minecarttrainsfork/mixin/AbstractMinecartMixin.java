package org.fuseleaf.minecarttrainsfork.mixin;

import org.fuseleaf.minecarttrainsfork.chaining.Chainable;
import org.fuseleaf.minecarttrainsfork.chaining.ChainableData;
import org.fuseleaf.minecarttrainsfork.chaining.Connection;
import org.fuseleaf.minecarttrainsfork.train.TrainBehavior;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.vehicle.minecart.AbstractMinecart;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

@Mixin(AbstractMinecart.class)
public class AbstractMinecartMixin implements Chainable {

    @Unique private @Nullable UUID parentUUID;

    @Unique private @Nullable UUID childUUID;

    @Override
    public UUID getParentUUID() {
        return parentUUID;
    }

    @Override
    public void setParentUUID(@Nullable UUID uuid) {
        this.parentUUID = uuid;
    }

    @Override
    public UUID getChildUUID() {
        return childUUID;
    }

    @Override
    public void setChildUUID(@Nullable UUID uuid) {
        this.childUUID = uuid;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void injectTick(CallbackInfo ci) {
        TrainBehavior.tick((AbstractMinecart)(Object)this);
    }

    @Inject(method = "getMaxSpeed", at = @At("RETURN"), cancellable = true)
    private void injectGetMaxSpeed(ServerLevel level, CallbackInfoReturnable<Double> cir) {
        cir.setReturnValue(TrainBehavior.setMaxSpeed((AbstractMinecart)(Object)this, cir.getReturnValue()));
    }

    @Override
    public @Nullable AbstractMinecart getChainedParent() {
        UUID parentUUID = getParentUUID();

        if (parentUUID == null) {
            return null;
        }

        return (AbstractMinecart)((AbstractMinecart)(Object)this).level().getEntity(parentUUID);
    }

    @Override
    public void setChainedParent(@Nullable AbstractMinecart newParent) {
        Connection.setChainedParent(newParent, (Chainable)(Object)this);
    }


    @Override
    public @Nullable AbstractMinecart getChainedChild() {
        UUID childUUID = getChildUUID();

        if (childUUID == null) {
            return null;
        }

        return (AbstractMinecart)((AbstractMinecart)(Object)this).level().getEntity(childUUID);
    }

    @Override
    public void setChainedChild(@Nullable AbstractMinecart newChild) {
        Connection.setChainedChild(newChild, (Chainable)(Object)this);
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    public void injectAddAdditionalSaveData(ValueOutput writeView, CallbackInfo ci) {
        ChainableData.write(writeView, (Chainable)(Object)this);
    }

    @Inject(method="readAdditionalSaveData", at = @At("TAIL"))
    public void injectReadAdditionalSaveData(ValueInput readView, CallbackInfo ci) {
        ChainableData.read(readView, (Chainable)(Object)this);
    }
}
