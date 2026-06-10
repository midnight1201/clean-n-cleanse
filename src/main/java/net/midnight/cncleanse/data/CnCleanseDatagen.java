package net.midnight.cncleanse.data;

import net.midnight.cncleanse.CnCleanse;
import net.midnight.cncleanse.data.recipe.CnCleanseStandardRecipeGen;
import net.midnight.cncleanse.data.recipe.create.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = CnCleanse.ID)
public class CnCleanseDatagen {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        if (!event.getMods().contains(CnCleanse.ID)) {
            return;
        }
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookup = event.getLookupProvider();
        if (event.includeServer()) {
            generator.addProvider(true, new CnCleanseStandardRecipeGen(output, lookup));
            generator.addProvider(true, new CnCleanseWashingRecipeGen(output, lookup));
            generator.addProvider(true, new CnCleanseFillingRecipeGen(output, lookup));
            generator.addProvider(true, new CnCleanseEmptyingRecipeGen(output, lookup));
            generator.addProvider(true, new CnCleanseCuttingRecipeGen(output, lookup));
            generator.addProvider(true, new CnCleanseCrushingRecipeGen(output, lookup));
            generator.addProvider(true, new CnCleanseMixingRecipeGen(output, lookup));
        }
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        CnCleanseCompatTagGen.Blocks blockTags =
                new CnCleanseCompatTagGen.Blocks(output, lookup, existingFileHelper);
            generator.addProvider(true, blockTags);
            generator.addProvider(true, new CnCleanseCompatTagGen.Items(
                output, lookup, blockTags.contentsGetter(), existingFileHelper));
    }
}