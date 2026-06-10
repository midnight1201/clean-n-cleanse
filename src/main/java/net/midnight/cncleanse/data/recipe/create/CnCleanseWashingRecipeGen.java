package net.midnight.cncleanse.data.recipe.create;

import com.simibubi.create.api.data.recipe.WashingRecipeGen;
import net.midnight.cncleanse.CnCleanse;
import net.midnight.cncleanse.register.CnCleanseBlocks;
import net.midnight.cncleanse.register.CnCleanseItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public class CnCleanseWashingRecipeGen extends WashingRecipeGen {

    public CnCleanseWashingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CnCleanse.ID);
    }

    GeneratedRecipe
            WHITE = washBlock("white",
                    CnCleanseBlocks.WHITE_SPONGE_BLOCK.get(),
                    CnCleanseBlocks.WET_WHITE_SPONGE_BLOCK.get()),
            RED = washBlock("red",
                    CnCleanseBlocks.RED_SPONGE_BLOCK.get(),
                    CnCleanseBlocks.WET_RED_SPONGE_BLOCK.get()),
            YELLOW = washBlock("yellow",
                    Blocks.SPONGE,
                    Blocks.WET_SPONGE),
            LIME = washBlock("lime",
                    CnCleanseBlocks.LIME_SPONGE_BLOCK.get(),
                    CnCleanseBlocks.WET_LIME_SPONGE_BLOCK.get()),
            LIGHT_BLUE = washBlock("light_blue",
                    CnCleanseBlocks.LIGHT_BLUE_SPONGE_BLOCK.get(),
                    CnCleanseBlocks.WET_LIGHT_BLUE_SPONGE_BLOCK.get()),

            KELP_ASH = create("kelp_ash", b -> b
                    .require(CnCleanseItems.KELP_ASH.get())
                    .output(0.5f, CnCleanseItems.SALT.get())
                    .output(0.25f, CnCleanseItems.SODA_ASH.get()))
                    ;

    private GeneratedRecipe washBlock(String color, ItemLike dry, ItemLike wet) {
        return create("wash_" + color + "_sponge", b -> b
                .require(dry)
                .output(wet));
    }
}