package org.fuseleaf.minecarttrainsfork.mixin;

import org.fuseleaf.minecarttrainsfork.chaining.ChainableComponents;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Inject(method = "split", at = @At("RETURN"))
    private void injectSplit(int amount, CallbackInfoReturnable<ItemStack> cir) {
        ItemStack result = cir.getReturnValue();

        if (result.is(Items.IRON_CHAIN)) {
            result.remove(ChainableComponents.PARENT_ID);
        }
    }

    @Inject(method = "copyWithCount", at = @At("RETURN"))
    private void injectCopyWithCount(int count, CallbackInfoReturnable<ItemStack> cir) {
        ItemStack result = cir.getReturnValue();

        if (result.is(Items.IRON_CHAIN)) {
            result.remove(ChainableComponents.PARENT_ID);
        }
    }
}
