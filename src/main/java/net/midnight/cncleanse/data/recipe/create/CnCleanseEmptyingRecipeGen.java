package net.midnight.cncleanse.data.recipe.create;

import com.simibubi.create.api.data.recipe.EmptyingRecipeGen;
import net.midnight.cncleanse.CnCleanse;
import net.midnight.cncleanse.register.CnCleanseFluids;
import net.midnight.cncleanse.register.CnCleanseItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluids;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public class CnCleanseEmptyingRecipeGen extends EmptyingRecipeGen {

    private static final int FLUID_PER_ITEM = 250;

    public CnCleanseEmptyingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CnCleanse.ID);
    }

    GeneratedRecipe
            WHITE = emptyItem("white",
                    CnCleanseItems.WET_WHITE_SPONGE.get(),
                    CnCleanseItems.WHITE_SPONGE.get()),
            RED = emptyItem("red",
                    CnCleanseItems.WET_RED_SPONGE.get(),
                    CnCleanseItems.RED_SPONGE.get()),
            YELLOW = emptyItem("yellow",
                    CnCleanseItems.WET_YELLOW_SPONGE.get(),
                    CnCleanseItems.YELLOW_SPONGE.get()),
            LIME = emptyItem("lime",
                    CnCleanseItems.WET_LIME_SPONGE.get(),
                    CnCleanseItems.LIME_SPONGE.get()),
            LIGHT_BLUE = emptyItem("light_blue",
                    CnCleanseItems.WET_LIGHT_BLUE_SPONGE.get(),
                    CnCleanseItems.LIGHT_BLUE_SPONGE.get()),

            SLAKED_LIME_BOTTLE = emptyBottle("slaked_lime",
                    CnCleanseFluids.SLAKED_LIME.get(),
                    CnCleanseItems.SLAKED_LIME_BOTTLE),
            CAUSTIC_SODA_BOTTLE = emptyBottle("caustic_soda",
                    CnCleanseFluids.CAUSTIC_SODA.get(),
                    CnCleanseItems.CAUSTIC_SODA_BOTTLE),
            CARBON_DISULFIDE_BOTTLE = emptyBottle("carbon_disulfide",
                    CnCleanseFluids.CARBON_DISULFIDE.get(),
                    CnCleanseItems.CARBON_DISULFIDE_BOTTLE),
            LIME_SULFUR_BOTTLE = emptyBottle("lime_sulfur",
                    CnCleanseFluids.LIME_SULFUR.get(),
                    CnCleanseItems.LIME_SULFUR_BOTTLE)
                    ;

    private GeneratedRecipe emptyItem(String color, ItemLike wet, ItemLike dry) {
        return create("empty_" + color + "_sponge", b -> b
                .require(wet)
                .output(Fluids.WATER, FLUID_PER_ITEM)
                .output(dry));
    }
    private GeneratedRecipe emptyBottle(String fluidName, FlowingFluid fluid, ItemLike bottle) {
        return create("empty_" + fluidName + "_bottle", b -> b
                .require(bottle)
                .output(fluid, FLUID_PER_ITEM)
                .output(Items.GLASS_BOTTLE));
    }
}
