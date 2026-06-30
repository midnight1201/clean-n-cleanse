package net.midnight.cncleanse.mixin;

import net.midnight.cncleanse.content.CnCleanseBottleFilling;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;

@Mixin(value = com.simibubi.create.content.fluids.transfer.GenericItemFilling.class, remap = false)
public class GenericItemFilling {

    @ModifyReturnValue(
            method = "canFillGlassBottleInternally",
            at = @At("RETURN")
    )
    private static boolean cncleanse$canFillGlassBottle(boolean original, FluidStack availableFluid) {
        return original || CnCleanseBottleFilling.isBottleFluid(availableFluid.getFluid());
    }

    @Inject(
            method = "fillItem",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void cncleanse$fillCustomBottle(
            Level world,
            int requiredAmount,
            ItemStack stack,
            FluidStack availableFluid,
            CallbackInfoReturnable<ItemStack> cir) {
        if (stack.getItem() != Items.GLASS_BOTTLE) {
            return;
        }
        if (!CnCleanseBottleFilling.isBottleFluid(availableFluid.getFluid())) {
            return;
        }

        FluidStack toFill = availableFluid.copy();
        toFill.setAmount(requiredAmount);

        ItemStack filled = CnCleanseBottleFilling.createFilledBottle(toFill).orElse(ItemStack.EMPTY);
        if (filled.isEmpty()) {
            return;
        }

        stack.shrink(1);
        cir.setReturnValue(filled);
    }
}