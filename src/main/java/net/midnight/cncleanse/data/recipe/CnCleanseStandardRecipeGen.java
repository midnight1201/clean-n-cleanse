package net.midnight.cncleanse.data.recipe;

import net.midnight.cncleanse.CnCleanse;
import net.midnight.cncleanse.register.CnCleanseBlocks;
import net.midnight.cncleanse.register.CnCleanseItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.concurrent.CompletableFuture;

public class CnCleanseStandardRecipeGen extends RecipeProvider {

    private static final String DRY_BLOCK_FROM_ITEMS = "cncleanse:dry_sponge_block_from_items";
    private static final String WET_BLOCK_FROM_ITEMS = "cncleanse:wet_sponge_block_from_items";

    public CnCleanseStandardRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput out) {
        itemsToBlock(out, CnCleanseBlocks.WHITE_SPONGE_BLOCK.get(), CnCleanseItems.WHITE_SPONGE.get(), "white_sponge_block", DRY_BLOCK_FROM_ITEMS);
        itemsToBlock(out, CnCleanseBlocks.RED_SPONGE_BLOCK.get(), CnCleanseItems.RED_SPONGE.get(), "red_sponge_block", DRY_BLOCK_FROM_ITEMS);
        itemsToBlock(out, Items.SPONGE, CnCleanseItems.YELLOW_SPONGE.get(), "yellow_sponge_block", DRY_BLOCK_FROM_ITEMS);
        itemsToBlock(out, CnCleanseBlocks.LIME_SPONGE_BLOCK.get(), CnCleanseItems.LIME_SPONGE.get(), "lime_sponge_block", DRY_BLOCK_FROM_ITEMS);
        itemsToBlock(out, CnCleanseBlocks.LIGHT_BLUE_SPONGE_BLOCK.get(), CnCleanseItems.LIGHT_BLUE_SPONGE.get(), "light_blue_sponge_block", DRY_BLOCK_FROM_ITEMS);

        itemsToBlock(out, CnCleanseBlocks.WET_WHITE_SPONGE_BLOCK.get(), CnCleanseItems.WET_WHITE_SPONGE.get(), "wet_white_sponge_block", WET_BLOCK_FROM_ITEMS);
        itemsToBlock(out, CnCleanseBlocks.WET_RED_SPONGE_BLOCK.get(), CnCleanseItems.WET_RED_SPONGE.get(), "wet_red_sponge_block", WET_BLOCK_FROM_ITEMS);
        itemsToBlock(out, Items.WET_SPONGE, CnCleanseItems.WET_YELLOW_SPONGE.get(), "wet_yellow_sponge_block", WET_BLOCK_FROM_ITEMS);
        itemsToBlock(out, CnCleanseBlocks.WET_LIME_SPONGE_BLOCK.get(), CnCleanseItems.WET_LIME_SPONGE.get(), "wet_lime_sponge_block", WET_BLOCK_FROM_ITEMS);
        itemsToBlock(out, CnCleanseBlocks.WET_LIGHT_BLUE_SPONGE_BLOCK.get(), CnCleanseItems.WET_LIGHT_BLUE_SPONGE.get(), "wet_light_blue_sponge_block", WET_BLOCK_FROM_ITEMS);

        smeltWetToDry(out, CnCleanseBlocks.WHITE_SPONGE_BLOCK.get(), CnCleanseBlocks.WET_WHITE_SPONGE_BLOCK.get(), "white_sponge_block");
        smeltWetToDry(out, CnCleanseBlocks.RED_SPONGE_BLOCK.get(), CnCleanseBlocks.WET_RED_SPONGE_BLOCK.get(), "red_sponge_block");
        smeltWetToDry(out, CnCleanseBlocks.LIME_SPONGE_BLOCK.get(), CnCleanseBlocks.WET_LIME_SPONGE_BLOCK.get(), "lime_sponge_block");
        smeltWetToDry(out, CnCleanseBlocks.LIGHT_BLUE_SPONGE_BLOCK.get(), CnCleanseBlocks.WET_LIGHT_BLUE_SPONGE_BLOCK.get(), "light_blue_sponge_block");

        smelt(out, CnCleanseItems.LIME_MUD.get(), CnCleanseItems.QUICKLIME.get(), "lime_mud_to_quicklime");
        smelt(out, Items.DRIED_KELP, CnCleanseItems.KELP_ASH.get(), "dried_kelp_to_kelp_ash");
    }

    private void itemsToBlock(RecipeOutput out, ItemLike block, ItemLike item, String name, String group) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, block)
                .pattern("CCC")
                .pattern("CCC")
                .pattern("CCC")
                .define('C', item)
                .group(group)
                .unlockedBy("has_" + name, has(item))
                .save(out, CnCleanse.asResource("crafting/sponge/" + name + "_from_items"));
    }

    private void smelt(RecipeOutput out, ItemLike input, ItemLike result, String name) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(input), RecipeCategory.MISC, result, 0.15f, 200)
                .unlockedBy("has_" + name, has(input))
                .save(out, CnCleanse.asResource("smelting/" + name));
    }
    private void smeltWetToDry(RecipeOutput out, ItemLike dry, ItemLike wet, String name) {
        smelt(out, wet, dry, "sponge/" + name + "_from_wet");
    }
}