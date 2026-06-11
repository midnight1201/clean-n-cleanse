package net.midnight.cncleanse.data.recipe.create;

import com.simibubi.create.api.data.recipe.FillingRecipeGen;
import net.midnight.cncleanse.CnCleanse;
import net.midnight.cncleanse.content.CnCleanseBottleFilling;
import net.midnight.cncleanse.content.sponge.item.SpongeItemColor;
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

    private static final int BOTTLE_AMOUNT = CnCleanseBottleFilling.AMOUNT;
    private static final int SPONGE_WATER_PER_ITEM = 250;
    private static final int SPONGE_WATER_PER_BLOCK = 1000;

    public CnCleanseFillingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CnCleanse.ID);
        registerAllOxidationRecipes();
    }

    GeneratedRecipe
            WET_WHITE_SPONGE = fillSpongeItem(SpongeItemColor.WHITE),
            WET_RED_SPONGE = fillSpongeItem(SpongeItemColor.RED),
            WET_YELLOW_SPONGE = fillSpongeItem(SpongeItemColor.YELLOW),
            WET_LIME_SPONGE = fillSpongeItem(SpongeItemColor.LIME),
            WET_LIGHT_BLUE_SPONGE = fillSpongeItem(SpongeItemColor.LIGHT_BLUE),

            WET_WHITE_SPONGE_BLOCK = fillSpongeBlock(SpongeItemColor.WHITE),
            WET_RED_SPONGE_BLOCK = fillSpongeBlock(SpongeItemColor.RED),
            WET_YELLOW_SPONGE_BLOCK = fillSpongeBlock(SpongeItemColor.YELLOW),
            WET_LIME_SPONGE_BLOCK = fillSpongeBlock(SpongeItemColor.LIME),
            WET_LIGHT_BLUE_SPONGE_BLOCK = fillSpongeBlock(SpongeItemColor.LIGHT_BLUE),

            CAUSTIC_SODA_BOTTLE = fillBottle("caustic_soda",
                    CnCleanseFluids.CAUSTIC_SODA.get(),
                    CnCleanseItems.CAUSTIC_SODA_BOTTLE),
            CARBON_DISULFIDE_BOTTLE = fillBottle("carbon_disulfide",
                    CnCleanseFluids.CARBON_DISULFIDE.get(),
                    CnCleanseItems.CARBON_DISULFIDE_BOTTLE),
            SLAKED_LIME_BOTTLE = fillBottle("slaked_lime",
                    CnCleanseFluids.SLAKED_LIME.get(),
                    CnCleanseItems.SLAKED_LIME_BOTTLE),
            VISCOSE_BOTTLE = fillBottle("viscose",
                    CnCleanseFluids.VISCOSE.get(),
                    CnCleanseItems.VISCOSE_BOTTLE),
            LIME_SULFUR_BOTTLE = fillBottle("lime_sulfur",
                    CnCleanseFluids.LIME_SULFUR.get(),
                    CnCleanseItems.LIME_SULFUR_BOTTLE),

            STERILIZE_MYCELIUM = sterilizeMycelium();

    // --- sponge helpers ---

    private GeneratedRecipe fillSpongeItem(SpongeItemColor color) {
        String name = color.name().toLowerCase();
        return create("fill_" + name + "_sponge", b -> b
                .require(Fluids.WATER, SPONGE_WATER_PER_ITEM)
                .require(color.dry().get())
                .output(color.wet().get()));
    }
    private GeneratedRecipe fillSpongeBlock(SpongeItemColor color) {
        String name = color.name().toLowerCase();
        return create("fill_" + name + "_sponge_block", b -> b
                .require(Fluids.WATER, SPONGE_WATER_PER_BLOCK)
                .require(color.dryBlock())
                .output(color.wetBlock()));
    }

    // --- bottle helpers ---

    private GeneratedRecipe fillBottle(String fluidName, FlowingFluid fluid, ItemLike bottle) {
        return create("fill_" + fluidName + "_bottle", b -> b
                .require(fluid, BOTTLE_AMOUNT)
                .require(Items.GLASS_BOTTLE)
                .output(bottle));
    }

    // --- world helpers ---

    private GeneratedRecipe sterilizeMycelium() {
        return create("sterilize_mycelium", b -> b
                .require(CnCleanseFluids.LIME_SULFUR.get(), BOTTLE_AMOUNT)
                .require(Blocks.MYCELIUM)
                .output(Blocks.DIRT));
    }

    // --- copper oxidation ---

    private void oxidizeCopper(Block from, Block to) {
        String name = BuiltInRegistries.BLOCK.getKey(from).getPath();
        create("oxidize_" + name, b -> b
                .require(CnCleanseFluids.LIME_SULFUR.get(), BOTTLE_AMOUNT)
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