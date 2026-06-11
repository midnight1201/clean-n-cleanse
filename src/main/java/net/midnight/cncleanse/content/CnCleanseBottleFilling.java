package net.midnight.cncleanse.content;

import com.simibubi.create.AllFluids;
import net.midnight.cncleanse.register.CnCleanseFluids;
import net.midnight.cncleanse.register.CnCleanseItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;

import java.util.Optional;

public final class CnCleanseBottleFilling {

    public static final int AMOUNT = 250;

    private record Entry(Fluid still, Item bottle) {}

    private static final Entry[] ENTRIES = {
            new Entry(CnCleanseFluids.CAUSTIC_SODA.get(), CnCleanseItems.CAUSTIC_SODA_BOTTLE.get()),
            new Entry(CnCleanseFluids.CARBON_DISULFIDE.get(), CnCleanseItems.CARBON_DISULFIDE_BOTTLE.get()),
            new Entry(CnCleanseFluids.SLAKED_LIME.get(), CnCleanseItems.SLAKED_LIME_BOTTLE.get()),
            new Entry(CnCleanseFluids.VISCOSE.get(), CnCleanseItems.VISCOSE_BOTTLE.get()),
            new Entry(CnCleanseFluids.LIME_SULFUR.get(), CnCleanseItems.LIME_SULFUR_BOTTLE.get()),
            new Entry(AllFluids.HONEY.get(), Items.HONEY_BOTTLE),
    };

    private CnCleanseBottleFilling() {}

    private static Fluid asStill(Fluid fluid) {
        if (fluid instanceof FlowingFluid flowing) {
            return flowing.getSource();
        }
        return fluid;
    }

    public static boolean isBottleFluid(Fluid fluid) {
        Fluid still = asStill(fluid);
        for (Entry e : ENTRIES) {
            if (e.still.isSame(still)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isBottleFluid(FluidStack stack) {
        return !stack.isEmpty() && isBottleFluid(stack.getFluid());
    }

    public static int requiredAmount(FluidStack stack) {
        return isBottleFluid(stack) ? AMOUNT : -1;
    }

    public static Optional<ItemStack> createFilledBottle(FluidStack fluid) {
        Fluid still = asStill(fluid.getFluid());
        for (Entry e : ENTRIES) {
            if (e.still.isSame(still)) {
                return Optional.of(new ItemStack(e.bottle));
            }
        }
        return Optional.empty();
    }
}