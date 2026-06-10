package net.midnight.cncleanse.data.recipe.create;

import com.simibubi.create.AllItems;
import com.simibubi.create.api.data.recipe.MixingRecipeGen;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import net.midnight.cncleanse.CnCleanse;
import net.midnight.cncleanse.register.CnCleanseFluids;
import net.midnight.cncleanse.register.CnCleanseItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public class CnCleanseMixingRecipeGen extends MixingRecipeGen {
    public CnCleanseMixingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CnCleanse.ID);
    }

    GeneratedRecipe
            ALKALI_CELLULOSE = create("steeping", b -> b
                    .require(AllItems.PULP.get())
                    .require(CnCleanseFluids.CAUSTIC_SODA.get(), 250)
                    .output(CnCleanseItems.ALKALI_CELLULOSE.get())),

            XANTHATE = create("xanthation", b -> b
                    .require(CnCleanseItems.ALKALI_CELLULOSE.get())
                    .require(CnCleanseFluids.CARBON_DISULFIDE.get(), 100)
                    .output(CnCleanseItems.XANTHATE.get())),

            CARBON_DISULFIDE = create("carbon_disulfide", b -> b
                    .require(ItemTags.COALS)
                    .require(CnCleanseItems.SULFUR.get())
                    .require(CnCleanseItems.SULFUR.get())
                    .output(CnCleanseFluids.CARBON_DISULFIDE.get(), 250)
                    .requiresHeat(HeatCondition.SUPERHEATED)),

            SODIUM_SULFATE = create("sodium_sulfate", b -> b
                    .require(CnCleanseItems.SULFUR.get())
                    .require(CnCleanseItems.SALT.get())
                    .require(CnCleanseItems.SALT.get())
                    .require(Tags.Fluids.WATER, 100)
                    .output(CnCleanseItems.SODIUM_SULFATE.get())
                    .requiresHeat(HeatCondition.HEATED)),

            SLAKED_LIME = create("slaked_lime", b -> b
                    .require(CnCleanseItems.QUICKLIME.get())
                    .require(Tags.Fluids.WATER, 250)
                    .output(CnCleanseFluids.SLAKED_LIME.get(), 250)),

            CAUSTIC_SODA = create("causticization", b -> b
                    .require(CnCleanseItems.SODA_ASH.get())
                    .require(CnCleanseFluids.SLAKED_LIME.get(), 250)
                    .output(CnCleanseFluids.CAUSTIC_SODA.get(), 500)
                    .output(CnCleanseItems.LIME_MUD.get())
                    .requiresHeat(HeatCondition.HEATED)),

            LIME_SULFUR = create("lime_sulfur", b -> b
                    .require(CnCleanseItems.SULFUR.get())
                    .require(CnCleanseItems.SULFUR.get())
                    .require(CnCleanseFluids.SLAKED_LIME.get(), 250)
                    .require(Tags.Fluids.WATER, 750)
                    .output(CnCleanseFluids.LIME_SULFUR.get(), 1000)
                    .requiresHeat(HeatCondition.HEATED))
                    ;
}
