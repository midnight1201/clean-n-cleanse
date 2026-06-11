package net.midnight.cncleanse.data.recipe.create;

import com.simibubi.create.api.data.recipe.CompactingRecipeGen;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import net.midnight.cncleanse.CnCleanse;
import net.midnight.cncleanse.content.sponge.item.SpongeItemColor;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public class CnCleanseCompactingRecipeGen extends CompactingRecipeGen {

    private static final int SALTED_VISCOSE_PER_BLOCK = 500;

    public CnCleanseCompactingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CnCleanse.ID);
    }

    GeneratedRecipe
            WHITE_SPONGE_BLOCK = spongeBlock(SpongeItemColor.WHITE),
            RED_SPONGE_BLOCK = spongeBlock(SpongeItemColor.RED),
            YELLOW_SPONGE_BLOCK = spongeBlock(SpongeItemColor.YELLOW),
            LIME_SPONGE_BLOCK = spongeBlock(SpongeItemColor.LIME),
            LIGHT_BLUE_SPONGE_BLOCK = spongeBlock(SpongeItemColor.LIGHT_BLUE);

    private GeneratedRecipe spongeBlock(SpongeItemColor color) {
        String id = color.name().toLowerCase() + "_sponge_block";
        return create(id, b -> b
                .require(color.saltedViscose().get(), SALTED_VISCOSE_PER_BLOCK)
                .output(color.dryBlock(), 1)
                .requiresHeat(HeatCondition.HEATED));
    }
}