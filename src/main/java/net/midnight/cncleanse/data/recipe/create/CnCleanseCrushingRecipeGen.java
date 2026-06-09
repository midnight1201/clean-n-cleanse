package net.midnight.cncleanse.data.recipe.create;

import com.simibubi.create.api.data.recipe.CrushingRecipeGen;
import com.simibubi.create.api.data.recipe.DatagenMod;
import com.simibubi.create.content.decoration.palettes.AllPaletteStoneTypes;
import net.midnight.cncleanse.CnCleanse;
import net.midnight.cncleanse.register.CnCleanseItems;
import net.midnight.cncleanse.register.CnCleanseTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public class CnCleanseCrushingRecipeGen extends CrushingRecipeGen {

    private static final float SULFUR_CHANCE = 0.4f;

    public CnCleanseCrushingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CnCleanse.ID);
    }

    GeneratedRecipe
            SULFUR_SPIKE = create("compat/vanillabackport/sulfur_spike", b -> b
                    .duration(100)
                    .require(CompatMods.MINECRAFT, "sulfur_spike")
                    .output(0.3f, CnCleanseItems.SULFUR.get(), 1)
                    .whenModLoaded("vanillabackport")),

            SULFUR = create("compat/vanillabackport/sulfur", b -> b
                    .duration(250)
                    .require(CompatMods.MINECRAFT, "sulfur")
                    .output(CnCleanseItems.SULFUR.get(), 1)
                    .output(0.25f, CnCleanseItems.SULFUR.get(), 1)
                    .whenModLoaded("vanillabackport")),

            SULFUR_BLOCKS = create("compat/vanillabackport/sulfur_blocks", b -> b
                    .duration(250)
                    .require(CnCleanseTags.Items.VB_SULFUR_BLOCKS)
                    .output(CnCleanseItems.SULFUR.get(), 1)
                    .output(0.25f, CnCleanseItems.SULFUR.get(), 1)
                    .whenModLoaded("vanillabackport")),

            POTENT_SULFUR = create("compat/vanillabackport/potent_sulfur", b -> b
                    .duration(500)
                    .require(CompatMods.MINECRAFT, "potent_sulfur")
                    .output(CnCleanseItems.SULFUR.get(), 9)
                    .output(0.75f, CnCleanseItems.SULFUR.get(), 2)
                    .output(0.75f, CnCleanseItems.SULFUR.get(), 1)
                    .whenModLoaded("vanillabackport")),

            SOUL_SAND = create("soul_sand", b -> b
                    .duration(150)
                    .require(Items.SOUL_SAND)
                    .output(SULFUR_CHANCE, CnCleanseItems.SULFUR.get(), 1)),

            SOUL_SOIL = create("soul_soil", b -> b
                    .duration(150)
                    .require(Items.SOUL_SOIL)
                    .output(SULFUR_CHANCE, CnCleanseItems.SULFUR.get(), 1)),

            SCORIA = create("scoria", b -> b
                    .duration(250)
                    .require(AllPaletteStoneTypes.SCORIA.getBaseBlock().get())
                    .output(SULFUR_CHANCE, CnCleanseItems.SULFUR.get(), 1)),

            SCORIA_RECYCLING = create("scoria_recycling", b -> b
                    .duration(250)
                    .require(AllPaletteStoneTypes.SCORIA.materialTag)
                    .output(SULFUR_CHANCE, CnCleanseItems.SULFUR.get(), 1)),

            SCORCHIA = create("scorchia", b -> b
                    .duration(250)
                    .require(AllPaletteStoneTypes.SCORCHIA.getBaseBlock().get())
                    .output(SULFUR_CHANCE, CnCleanseItems.SULFUR.get(), 1)),

            SCORCHIA_RECYCLING = create("scorchia_recycling", b -> b
                    .duration(250)
                    .require(AllPaletteStoneTypes.SCORCHIA.materialTag)
                    .output(SULFUR_CHANCE, CnCleanseItems.SULFUR.get(), 1)),

            LIMESTONE = create("limestone", b -> b
                    .duration(200)
                    .require(AllPaletteStoneTypes.LIMESTONE.getBaseBlock().get())
                    .output(CnCleanseItems.LIME_MUD.get(), 1)
                    .output(0.5f, CnCleanseItems.LIME_MUD.get(), 1)),

            LIMESTONE_RECYCLING = create("limestone_recycling", b -> b
                    .duration(200)
                    .require(AllPaletteStoneTypes.LIMESTONE.materialTag)
                    .output(CnCleanseItems.LIME_MUD.get(), 1)
                    .output(0.5f, CnCleanseItems.LIME_MUD.get(), 1))
                    ;

    private enum CompatMods implements DatagenMod {
        MINECRAFT("minecraft");

        private final String id;

        CompatMods(String id) {
            this.id = id;
        }

        @Override
        public String getId() {
            return id;
        }
    }
}
