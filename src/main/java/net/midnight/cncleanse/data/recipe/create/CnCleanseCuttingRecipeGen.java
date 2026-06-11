package net.midnight.cncleanse.data.recipe.create;

import com.simibubi.create.api.data.recipe.CuttingRecipeGen;
import net.midnight.cncleanse.CnCleanse;
import net.midnight.cncleanse.content.sponge.item.SpongeItemColor;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.ItemLike;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public class CnCleanseCuttingRecipeGen extends CuttingRecipeGen {

    private static final int DURATION = 20;
    private static final int ITEMS = 4;

    public CnCleanseCuttingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CnCleanse.ID);
    }

    GeneratedRecipe
            WHITE = cutDry(SpongeItemColor.WHITE),
            RED = cutDry(SpongeItemColor.RED),
            YELLOW = cutDry(SpongeItemColor.YELLOW),
            LIME = cutDry(SpongeItemColor.LIME),
            LIGHT_BLUE = cutDry(SpongeItemColor.LIGHT_BLUE),

            WET_WHITE = cutWet(SpongeItemColor.WHITE),
            WET_RED = cutWet(SpongeItemColor.RED),
            WET_YELLOW = cutWet(SpongeItemColor.YELLOW),
            WET_LIME = cutWet(SpongeItemColor.LIME),
            WET_LIGHT_BLUE = cutWet(SpongeItemColor.LIGHT_BLUE);

    private GeneratedRecipe cutBlock(String name, ItemLike block, ItemLike items) {
        return create("cut_" + name + "_sponge", b -> b
                .duration(DURATION)
                .require(block)
                .output(items, ITEMS));
    }

    private GeneratedRecipe cutDry(SpongeItemColor color) {
        String name = color.name().toLowerCase();
        return cutBlock(name, color.dryBlock(), color.dry().get());
    }
    private GeneratedRecipe cutWet(SpongeItemColor color) {
        String name = "wet_" + color.name().toLowerCase();
        return cutBlock(name, color.wetBlock(), color.wet().get());
    }
}