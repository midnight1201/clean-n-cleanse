package net.midnight.cncleanse.register;

import com.simibubi.create.content.fluids.VirtualFluid;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.midnight.cncleanse.CnCleanse;

import static net.midnight.cncleanse.register.CnCleanseFluidTypes.*;

public class CnCleanseFluids {

    public static final FluidEntry<VirtualFluid> CAUSTIC_SODA =
            tintedVirtualWater("caustic_soda", CnCleanseFluidTypes.FluidTints.CAUSTIC_SODA, "Lye");

    public static final FluidEntry<VirtualFluid> CARBON_DISULFIDE =
            tintedVirtualWater("carbon_disulfide", CnCleanseFluidTypes.FluidTints.CARBON_DISULFIDE, "Carbon Disulfide");

    public static final FluidEntry<VirtualFluid> SLAKED_LIME = CnCleanse.REGISTRATE
            .virtualFluid("slaked_lime", CREATE_MILK_STILL, CREATE_MILK_FLOW)
            .lang("Milk of Lime")
            .register();

    public static final FluidEntry<VirtualFluid> VISCOSE =
            texturedVirtualFluid("viscose", "Viscose");

    public static final FluidEntry<VirtualFluid> LIME_SULFUR =
            tintedVirtualWater("lime_sulfur", CnCleanseFluidTypes.FluidTints.LIME_SULFUR, "Lime Sulfur");

    public static final FluidEntry<VirtualFluid> WHITE_SALTED_VISCOSE =
            texturedVirtualFluid("white_salted_viscose", "White Salted Viscose");
    public static final FluidEntry<VirtualFluid> RED_SALTED_VISCOSE =
            texturedVirtualFluid("red_salted_viscose", "Red Salted Viscose");
    public static final FluidEntry<VirtualFluid> YELLOW_SALTED_VISCOSE =
            texturedVirtualFluid("yellow_salted_viscose", "Yellow Salted Viscose");
    public static final FluidEntry<VirtualFluid> LIME_SALTED_VISCOSE =
            texturedVirtualFluid("lime_salted_viscose", "Lime Salted Viscose");
    public static final FluidEntry<VirtualFluid> LIGHT_BLUE_SALTED_VISCOSE =
            texturedVirtualFluid("light_blue_salted_viscose", "Light Blue Salted Viscose");

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

    private static FluidEntry<VirtualFluid> texturedVirtualFluid(String name, String lang) {
        return CnCleanse.REGISTRATE
                .virtualFluid(name, fluidStill(name), fluidFlow(name))
                .lang(lang)
                .register();
    }

    public static void register() {
        CnCleanse.LOGGER.info("Register Fluids...");
    }
}