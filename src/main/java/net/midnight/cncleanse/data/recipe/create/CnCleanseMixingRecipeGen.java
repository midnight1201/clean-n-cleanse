package net.midnight.cncleanse.data.recipe.create;

import com.simibubi.create.api.data.recipe.MixingRecipeGen;
import net.midnight.cncleanse.CnCleanse;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public class CnCleanseMixingRecipeGen extends MixingRecipeGen {
    public CnCleanseMixingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CnCleanse.ID);
    }
}
