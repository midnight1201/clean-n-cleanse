package net.midnight.cncleanse.data.recipe.create;

import com.simibubi.create.AllItems;
import com.simibubi.create.api.data.recipe.MixingRecipeGen;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import net.midnight.cncleanse.CnCleanse;
import net.midnight.cncleanse.content.sponge.item.SpongeItemColor;
import net.midnight.cncleanse.register.CnCleanseFluids;
import net.midnight.cncleanse.register.CnCleanseItems;
import net.midnight.cncleanse.register.CnCleanseTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.material.Fluids;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public class CnCleanseMixingRecipeGen extends MixingRecipeGen {

    private static final int VISCOSE_BATCH = 500;

    public CnCleanseMixingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CnCleanse.ID);
    }

    GeneratedRecipe
            ALKALI_CELLULOSE = create("steeping", b -> b
                    .require(AllItems.PULP.get())
                    .require(CnCleanseFluids.CAUSTIC_SODA.get(), 250)
                    .output(CnCleanseItems.ALKALI_CELLULOSE.get())),

            SODIUM_SULFATE = create("sodium_sulfate", b -> b
                    .require(CnCleanseTags.Items.dust("sulfur"))
                    .require(CnCleanseTags.Items.SALTS)
                    .require(CnCleanseTags.Items.SALTS)
                    .require(Fluids.WATER, 100)
                    .output(CnCleanseItems.SODIUM_SULFATE.get())
                    .requiresHeat(HeatCondition.HEATED)),

            XANTHATE = create("xanthation", b -> b
                    .require(CnCleanseItems.ALKALI_CELLULOSE.get())
                    .require(CnCleanseFluids.CARBON_DISULFIDE.get(), 100)
                    .output(CnCleanseItems.XANTHATE.get())),

            CAUSTIC_SODA = create("causticization", b -> b
                    .require(CnCleanseItems.SODA_ASH.get())
                    .require(CnCleanseFluids.SLAKED_LIME.get(), 250)
                    .output(CnCleanseFluids.CAUSTIC_SODA.get(), 500)
                    .output(CnCleanseItems.LIME_MUD.get())
                    .requiresHeat(HeatCondition.HEATED)),

            CARBON_DISULFIDE = create("carbon_disulfide", b -> b
                    .require(ItemTags.COALS)
                    .require(CnCleanseTags.Items.dust("sulfur"))
                    .require(CnCleanseTags.Items.dust("sulfur"))
                    .output(CnCleanseFluids.CARBON_DISULFIDE.get(), 250)
                    .requiresHeat(HeatCondition.SUPERHEATED)),

            SLAKED_LIME = create("slaked_lime", b -> b
                    .require(CnCleanseItems.QUICKLIME.get())
                    .require(Fluids.WATER, 250)
                    .output(CnCleanseFluids.SLAKED_LIME.get(), 250)),

            VISCOSE = create("viscose", b -> b
                    .require(CnCleanseItems.XANTHATE.get())
                    .require(CnCleanseFluids.CAUSTIC_SODA.get(), 250)
                    .output(CnCleanseFluids.VISCOSE.get(), 500)),

            LIME_SULFUR = create("lime_sulfur", b -> b
                    .require(CnCleanseTags.Items.dust("sulfur"))
                    .require(CnCleanseTags.Items.dust("sulfur"))
                    .require(CnCleanseFluids.SLAKED_LIME.get(), 250)
                    .require(Fluids.WATER, 750)
                    .output(CnCleanseFluids.LIME_SULFUR.get(), 1000)
                    .requiresHeat(HeatCondition.HEATED)),

            SALTED_VISCOSE_WHITE = saltedViscose(SpongeItemColor.WHITE),
            SALTED_VISCOSE_RED = saltedViscose(SpongeItemColor.RED),
            SALTED_VISCOSE_YELLOW = saltedViscose(SpongeItemColor.YELLOW),
            SALTED_VISCOSE_LIME = saltedViscose(SpongeItemColor.LIME),
            SALTED_VISCOSE_LIGHT_BLUE = saltedViscose(SpongeItemColor.LIGHT_BLUE);

    private GeneratedRecipe saltedViscose(SpongeItemColor color) {
        String id = color.name().toLowerCase() + "_salted_viscose";
        return create(id, b -> b
                .require(CnCleanseFluids.VISCOSE.get(), 500)
                .require(color.dye().getTag())
                .require(CnCleanseItems.SODIUM_SULFATE.get())
                .output(color.saltedViscose().get(), 500));
    }
}
