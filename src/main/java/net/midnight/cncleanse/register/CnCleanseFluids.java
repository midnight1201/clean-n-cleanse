package net.midnight.cncleanse.register;

import com.simibubi.create.content.fluids.VirtualFluid;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.midnight.cncleanse.CnCleanse;

import static net.midnight.cncleanse.register.CnCleanseFluidTypes.*;

public class CnCleanseFluids {

    public static final FluidEntry<VirtualFluid> SLAKED_LIME = CnCleanse.REGISTRATE
            .virtualFluid("slaked_lime", CREATE_MILK_STILL, CREATE_MILK_FLOW)
            .lang("Milk of Lime")
            .register();

    public static final FluidEntry<VirtualFluid> CAUSTIC_SODA =
            tintedVirtualWater("caustic_soda", CnCleanseFluidTypes.FluidTints.CAUSTIC_SODA, "Lye");

    public static final FluidEntry<VirtualFluid> CARBON_DISULFIDE =
            tintedVirtualWater("carbon_disulfide", CnCleanseFluidTypes.FluidTints.CARBON_DISULFIDE, "Carbon Disulfide");

    public static final FluidEntry<VirtualFluid> LIME_SULFUR =
            tintedVirtualWater("lime_sulfur", CnCleanseFluidTypes.FluidTints.LIME_SULFUR, "Lime Sulfur");

    private static FluidEntry<VirtualFluid> tintedVirtualWater(String name, int rgb, String lang) {
        return CnCleanse.REGISTRATE
                .virtualFluid(
                        name,
                        WATER_STILL,
                        WATER_FLOW,
                        fixedTintWater(rgb),
                        VirtualFluid::createSource,
                        VirtualFluid::createFlowing)
                .lang(lang)
                .register();
    }

    public static void register() {
        CnCleanse.LOGGER.info("Register Fluids...");
    }
}