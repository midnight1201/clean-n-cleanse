package net.midnight.cncleanse.data.recipe.create;

import com.simibubi.create.api.data.recipe.CuttingRecipeGen;
import net.midnight.cncleanse.CnCleanse;
import net.midnight.cncleanse.register.CnCleanseBlocks;
import net.midnight.cncleanse.register.CnCleanseItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public class CnCleanseCuttingRecipeGen extends CuttingRecipeGen {

    private static final int DURATION = 20;
    private static final int ITEMS = 9;

    public CnCleanseCuttingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CnCleanse.ID);
    }

    GeneratedRecipe
            WHITE = cutBlock("white",
                    CnCleanseBlocks.WHITE_SPONGE_BLOCK.get(),
                    CnCleanseItems.WHITE_SPONGE.get()),
            RED = cutBlock("red",
                    CnCleanseBlocks.RED_SPONGE_BLOCK.get(),
                    CnCleanseItems.RED_SPONGE.get()),
            YELLOW = cutBlock("yellow",
                    Blocks.SPONGE,
                    CnCleanseItems.YELLOW_SPONGE.get()),
            LIME = cutBlock("lime",
                    CnCleanseBlocks.LIME_SPONGE_BLOCK.get(),
                    CnCleanseItems.LIME_SPONGE.get()),
            LIGHT_BLUE = cutBlock("light_blue",
                    CnCleanseBlocks.LIGHT_BLUE_SPONGE_BLOCK.get(),
                    CnCleanseItems.LIGHT_BLUE_SPONGE.get()),

            WET_WHITE = cutBlock("wet_white",
                    CnCleanseBlocks.WET_WHITE_SPONGE_BLOCK.get(),
                    CnCleanseItems.WET_WHITE_SPONGE.get()),
            WET_RED = cutBlock("wet_red",
                    CnCleanseBlocks.WET_RED_SPONGE_BLOCK.get(),
                    CnCleanseItems.WET_RED_SPONGE.get()),
            WET_YELLOW = cutBlock("wet_yellow",
                    Blocks.WET_SPONGE,
                    CnCleanseItems.WET_YELLOW_SPONGE.get()),
            WET_LIME = cutBlock("wet_lime",
                    CnCleanseBlocks.WET_LIME_SPONGE_BLOCK.get(),
                    CnCleanseItems.WET_LIME_SPONGE.get()),
            WET_LIGHT_BLUE = cutBlock("wet_light_blue",
                    CnCleanseBlocks.WET_LIGHT_BLUE_SPONGE_BLOCK.get(),
                    CnCleanseItems.WET_LIGHT_BLUE_SPONGE.get())
                    ;

    private GeneratedRecipe cutBlock(String name, ItemLike block, ItemLike items) {
        return create("cut_" + name + "_sponge", b -> b
                .duration(DURATION)
                .require(block)
                .output(items, ITEMS));
    }
}