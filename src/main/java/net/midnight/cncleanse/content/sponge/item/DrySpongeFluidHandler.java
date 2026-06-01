package net.midnight.cncleanse.content.sponge.item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import org.jetbrains.annotations.NotNull;

public class DrySpongeFluidHandler implements IFluidHandlerItem {

    public static final int CAPACITY = 250;

    private final ItemStack container;
    private final SpongeItemColor color;
    private int filled;

    public DrySpongeFluidHandler(ItemStack container, SpongeItemColor color) {
        this.container = container;
        this.color = color;
    }

    @Override
    public @NotNull ItemStack getContainer() {
        return filled >= CAPACITY
                ? new ItemStack(color.wet().get())
                : container;
    }

    @Override
    public int getTanks() {
        return 1;
    }

    @Override
    public @NotNull FluidStack getFluidInTank(int tank) {
        return filled > 0
                ? new FluidStack(Fluids.WATER, filled)
                : FluidStack.EMPTY;
    }

    @Override
    public int getTankCapacity(int tank) {
        return CAPACITY;
    }

    @Override
    public boolean isFluidValid(int i, FluidStack fluidStack) {
        return false;
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        if (resource.isEmpty() || !resource.is(Fluids.WATER)) {
            return 0;
        }
        if (filled >= CAPACITY) {
            return 0;
        }

        int needed = CAPACITY - filled;
        if (resource.getAmount() < needed) {
            return 0;
        }

        if (action == FluidAction.EXECUTE) {
            filled = CAPACITY;
        }
        return needed;
    }

    @Override
    public FluidStack drain(FluidStack fluidStack, FluidAction fluidAction) {
        return null;
    }

    @Override
    public @NotNull FluidStack drain(int maxDrain, FluidAction action) {
        return FluidStack.EMPTY;
    }
}