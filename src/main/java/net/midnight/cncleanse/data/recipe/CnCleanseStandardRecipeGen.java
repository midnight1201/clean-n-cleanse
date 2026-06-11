package net.midnight.cncleanse.data.recipe;

import net.midnight.cncleanse.CnCleanse;
import net.midnight.cncleanse.content.sponge.item.SpongeItemColor;
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
        for (SpongeItemColor color : SpongeItemColor.values()) {
            String name = color.name().toLowerCase() + "_sponge_block";
            itemsToBlock(out, color.dryBlock(), color.dry().get(), name, DRY_BLOCK_FROM_ITEMS);

            String wetName = "wet_" + name;
            itemsToBlock(out, color.wetBlock(), color.wet().get(), wetName, WET_BLOCK_FROM_ITEMS);

            if (color != SpongeItemColor.YELLOW) {
                smeltWetToDry(out, color.dryBlock(), color.wetBlock(), name);
            }
        }

        smelt(out, CnCleanseItems.LIME_MUD.get(), CnCleanseItems.QUICKLIME.get(), "lime_mud_to_quicklime");
        smelt(out, Items.DRIED_KELP, CnCleanseItems.KELP_ASH.get(), "dried_kelp_to_kelp_ash");
    }

    private void itemsToBlock(RecipeOutput out, ItemLike block, ItemLike item, String name, String group) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, block)
                .pattern("CC")
                .pattern("CC")
                .define('C', item)
                .group(group)
                .unlockedBy("has_" + name, has(item))
                .save(out, CnCleanse.asResource("crafting/sponge/" + name + "_from_items"));
    }

    private void smeltWetToDry(RecipeOutput out, ItemLike dry, ItemLike wet, String name) {
        smelt(out, wet, dry, "sponge/" + name + "_from_wet");
    }

    private void smelt(RecipeOutput out, ItemLike input, ItemLike result, String name) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(input), RecipeCategory.MISC, result, 0.15f, 200)
                .unlockedBy("has_" + name, has(input))
                .save(out, CnCleanse.asResource("smelting/" + name));
    }


}