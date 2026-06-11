package net.midnight.cncleanse.data.recipe.create;

import com.simibubi.create.api.data.recipe.EmptyingRecipeGen;
import net.midnight.cncleanse.CnCleanse;
import net.midnight.cncleanse.content.sponge.item.SpongeItemColor;
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
            WHITE = emptySponge(SpongeItemColor.WHITE),
            RED = emptySponge(SpongeItemColor.RED),
            YELLOW = emptySponge(SpongeItemColor.YELLOW),
            LIME = emptySponge(SpongeItemColor.LIME),
            LIGHT_BLUE = emptySponge(SpongeItemColor.LIGHT_BLUE),

            CAUSTIC_SODA_BOTTLE = emptyBottle("caustic_soda",
                    CnCleanseFluids.CAUSTIC_SODA.get(),
                    CnCleanseItems.CAUSTIC_SODA_BOTTLE.get()),
            CARBON_DISULFIDE_BOTTLE = emptyBottle("carbon_disulfide",
                    CnCleanseFluids.CARBON_DISULFIDE.get(),
                    CnCleanseItems.CARBON_DISULFIDE_BOTTLE.get()),
            SLAKED_LIME_BOTTLE = emptyBottle("slaked_lime",
                    CnCleanseFluids.SLAKED_LIME.get(),
                    CnCleanseItems.SLAKED_LIME_BOTTLE.get()),
            VISCOSE_BOTTLE = emptyBottle("viscose",
                    CnCleanseFluids.VISCOSE.get(),
                    CnCleanseItems.VISCOSE_BOTTLE.get()),
            LIME_SULFUR_BOTTLE = emptyBottle("lime_sulfur",
                    CnCleanseFluids.LIME_SULFUR.get(),
                    CnCleanseItems.LIME_SULFUR_BOTTLE.get());

    private GeneratedRecipe emptySponge(SpongeItemColor color) {
        String name = color.name().toLowerCase();
        return create("empty_" + name + "_sponge", b -> b
                .require(color.wet().get())
                .output(Fluids.WATER, FLUID_PER_ITEM)
                .output(color.dry().get()));
    }

    private GeneratedRecipe emptyBottle(String fluidName, FlowingFluid fluid, ItemLike bottle) {
        return create("empty_" + fluidName + "_bottle", b -> b
                .require(bottle)
                .output(fluid, FLUID_PER_ITEM)
                .output(Items.GLASS_BOTTLE));
    }
}
