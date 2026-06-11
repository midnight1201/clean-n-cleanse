package net.midnight.cncleanse.register;

import com.simibubi.create.AllFluids.TintedFluidType;
import com.tterrag.registrate.builders.FluidBuilder.FluidTypeFactory;
import net.midnight.cncleanse.CnCleanse;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.fluids.FluidStack;

public final class CnCleanseFluidTypes {

    public static final ResourceLocation CREATE_MILK_STILL =
            ResourceLocation.fromNamespaceAndPath("create", "fluid/milk_still");
    public static final ResourceLocation CREATE_MILK_FLOW =
            ResourceLocation.fromNamespaceAndPath("create", "fluid/milk_flow");

    public static ResourceLocation fluidStill(String name) {
        return ResourceLocation.fromNamespaceAndPath(CnCleanse.ID, "fluid/" + name + "_still");
    }
    public static ResourceLocation fluidFlow(String name) {
        return ResourceLocation.fromNamespaceAndPath(CnCleanse.ID, "fluid/" + name + "_flow");
    }

    public static final class FluidTints {
        private FluidTints() {}

        public static final int CAUSTIC_SODA = 0xE8F4FF;
        public static final int CARBON_DISULFIDE = 0xE8D020;
        public static final int LIME_SULFUR = 0xC4A574;
    }

    public static final ResourceLocation WATER_STILL =
            ResourceLocation.withDefaultNamespace("block/water_still");
    public static final ResourceLocation WATER_FLOW =
            ResourceLocation.withDefaultNamespace("block/water_flow");

    public static FluidTypeFactory fixedTintWater(int rgb) {
        final int color = 0xFF000000 | (rgb & 0xFFFFFF);
        return (properties, still, flowing) -> new TintedFluidType(properties, still, flowing) {
            @Override
            protected int getTintColor(FluidStack stack) {
                return color;
            }

            @Override
            protected int getTintColor(FluidState state, BlockAndTintGetter getter, BlockPos pos) {
                return color;
            }
        };
    }

    private CnCleanseFluidTypes() {}
}