package net.midnight.cncleanse.data.recipe.create;

import com.simibubi.create.api.data.recipe.FillingRecipeGen;
import net.midnight.cncleanse.CnCleanse;
import net.midnight.cncleanse.register.CnCleanseBlocks;
import net.midnight.cncleanse.register.CnCleanseItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluids;
import net.midnight.cncleanse.register.CnCleanseFluids;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WeatheringCopper;
import com.simibubi.create.foundation.block.CopperRegistries;
import java.util.HashSet;
import java.util.Set;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public class CnCleanseFillingRecipeGen extends FillingRecipeGen {

    private final Set<Block> oxidationSources = new HashSet<>();

    private static final int FLUID_PER_ITEM = 250;
    private static final int FLUID_PER_BLOCK = 1000;

    public CnCleanseFillingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CnCleanse.ID);
        registerAllOxidationRecipes();
    }

    GeneratedRecipe
            WET_WHITE_SPONGE = fillItem("white",
                    CnCleanseItems.WHITE_SPONGE.get(),
                    CnCleanseItems.WET_WHITE_SPONGE.get()),
            WET_RED_SPONGE = fillItem("red",
                    CnCleanseItems.RED_SPONGE.get(),
                    CnCleanseItems.WET_RED_SPONGE.get()),
            WET_YELLOW_SPONGE = fillItem("yellow",
                    CnCleanseItems.YELLOW_SPONGE.get(),
                    CnCleanseItems.WET_YELLOW_SPONGE.get()),
            WET_LIME_SPONGE = fillItem("lime",
                    CnCleanseItems.LIME_SPONGE.get(),
                    CnCleanseItems.WET_LIME_SPONGE.get()),
            WET_LIGHT_BLUE_SPONGE = fillItem("light_blue",
                    CnCleanseItems.LIGHT_BLUE_SPONGE.get(),
                    CnCleanseItems.WET_LIGHT_BLUE_SPONGE.get()),

            WET_WHITE_SPONGE_BLOCK = fillBlock("white",
                    CnCleanseBlocks.WHITE_SPONGE_BLOCK.get(),
                    CnCleanseBlocks.WET_WHITE_SPONGE_BLOCK.get()),
            WET_RED_SPONGE_BLOCK = fillBlock("red",
                    CnCleanseBlocks.RED_SPONGE_BLOCK.get(),
                    CnCleanseBlocks.WET_RED_SPONGE_BLOCK.get()),
            WET_YELLOW_SPONGE_BLOCK = fillBlock("yellow",
                    Blocks.SPONGE,
                    Blocks.WET_SPONGE),
            WET_LIME_SPONGE_BLOCK = fillBlock("lime",
                    CnCleanseBlocks.LIME_SPONGE_BLOCK.get(),
                    CnCleanseBlocks.WET_LIME_SPONGE_BLOCK.get()),
            WET_LIGHT_BLUE_SPONGE_BLOCK = fillBlock("light_blue",
                    CnCleanseBlocks.LIGHT_BLUE_SPONGE_BLOCK.get(),
                    CnCleanseBlocks.WET_LIGHT_BLUE_SPONGE_BLOCK.get()),

            SLAKED_LIME_BOTTLE = fillBottle("slaked_lime",
                    CnCleanseFluids.SLAKED_LIME.get(),
                    CnCleanseItems.SLAKED_LIME_BOTTLE),
            CAUSTIC_SODA_BOTTLE = fillBottle("caustic_soda",
                    CnCleanseFluids.CAUSTIC_SODA.get(),
                    CnCleanseItems.CAUSTIC_SODA_BOTTLE),
            CARBON_DISULFIDE_BOTTLE = fillBottle("carbon_disulfide",
                    CnCleanseFluids.CARBON_DISULFIDE.get(),
                    CnCleanseItems.CARBON_DISULFIDE_BOTTLE),
            LIME_SULFUR_BOTTLE = fillBottle("lime_sulfur",
                    CnCleanseFluids.LIME_SULFUR.get(),
                    CnCleanseItems.LIME_SULFUR_BOTTLE),

            STERILIZE_MYCELIUM = sterilizeMycelium()
                    ;

    private GeneratedRecipe fillItem(String color, ItemLike dry, ItemLike wet) {
        return create("fill_" + color + "_sponge", b -> b
                .require(Fluids.WATER, FLUID_PER_ITEM)
                .require(dry)
                .output(wet));
    }
    private GeneratedRecipe fillBlock(String color, ItemLike dry, ItemLike wet) {
        return create("fill_" + color + "_sponge_block", b -> b
                .require(Fluids.WATER, FLUID_PER_BLOCK)
                .require(dry)
                .output(wet));
    }
    private GeneratedRecipe fillBottle(String fluidName, FlowingFluid fluid, ItemLike bottle) {
        return create("fill_" + fluidName + "_bottle", b -> b
                .require(fluid, FLUID_PER_ITEM)
                .require(Items.GLASS_BOTTLE)
                .output(bottle));
    }
    private GeneratedRecipe sterilizeMycelium() {
        return create("sterilize_mycelium", b -> b
                .require(CnCleanseFluids.LIME_SULFUR.get(), FLUID_PER_ITEM)
                .require(Blocks.MYCELIUM)
                .output(Blocks.DIRT));
    }

    private void oxidizeCopper(Block from, Block to) {
        String name = BuiltInRegistries.BLOCK.getKey(from).getPath();
        create("oxidize_" + name, b -> b
                .require(CnCleanseFluids.LIME_SULFUR.get(), FLUID_PER_ITEM)
                .require(from)
                .output(to));
    }
    @SuppressWarnings("UnstableApiUsage")
    private void registerAllOxidationRecipes() {
        BuiltInRegistries.BLOCK.forEach(this::tryOxidizeFromDatamap);
        CopperRegistries.getWeatheringView().forEach((from, to) -> {
            Block fromBlock = from.value();
            if (oxidationSources.add(fromBlock)) {
                oxidizeCopper(fromBlock, to.value());
            }
        });
    }
    private void tryOxidizeFromDatamap(Block block) {
        if (!(block instanceof WeatheringCopper)) {
            return;
        }
        WeatheringCopper.getNext(block).ifPresent(next -> {
            if (oxidationSources.add(block)) {
                oxidizeCopper(block, next);
            }
        });
    }
}