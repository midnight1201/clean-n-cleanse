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
import net.minecraft.world.level.ItemLike;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public class CnCleanseCrushingRecipeGen extends CrushingRecipeGen {

    private static final float SULFUR_CHANCE = 0.4f;

    private static final int LIMESTONE_CRUSH_DURATION = 200;
    private static final int STONE_CRUSH_DURATION = 250;

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
                    .duration(STONE_CRUSH_DURATION)
                    .require(CompatMods.MINECRAFT, "sulfur")
                    .output(CnCleanseItems.SULFUR.get(), 1)
                    .output(0.25f, CnCleanseItems.SULFUR.get(), 1)
                    .whenModLoaded("vanillabackport")),

            SULFUR_BLOCKS = create("compat/vanillabackport/sulfur_blocks", b -> b
                    .duration(STONE_CRUSH_DURATION)
                    .require(CnCleanseTags.Items.VB_SULFUR_BLOCKS)
                    .output(CnCleanseItems.SULFUR.get(), 1)
                    .output(0.25f, CnCleanseItems.SULFUR.get(), 1)
                    .whenModLoaded("vanillabackport")),

            POTENT_SULFUR = create("compat/vanillabackport/potent_sulfur", b -> b
                    .duration(STONE_CRUSH_DURATION)
                    .require(CompatMods.MINECRAFT, "potent_sulfur")
                    .output(CnCleanseItems.SULFUR.get(), 9)
                    .output(0.75f, CnCleanseItems.SULFUR.get(), 2)
                    .output(0.75f, CnCleanseItems.SULFUR.get(), 1)
                    .whenModLoaded("vanillabackport")),

            SOUL_SAND = soulCrush("soul_sand", Items.SOUL_SAND),
            SOUL_SOIL = soulCrush("soul_soil", Items.SOUL_SOIL),

            SCORIA = paletteSulfurCrush(AllPaletteStoneTypes.SCORIA, "scoria"),
            SCORIA_RECYCLING = paletteSulfurRecycling(AllPaletteStoneTypes.SCORIA, "scoria"),
            SCORCHIA = paletteSulfurCrush(AllPaletteStoneTypes.SCORCHIA, "scorchia"),
            SCORCHIA_RECYCLING = paletteSulfurRecycling(AllPaletteStoneTypes.SCORCHIA, "scorchia"),

            LIMESTONE = paletteLimeMudCrush(AllPaletteStoneTypes.LIMESTONE, "limestone"),
            LIMESTONE_RECYCLING = paletteLimeMudRecycling(AllPaletteStoneTypes.LIMESTONE, "limestone");

    private GeneratedRecipe soulCrush(String id, ItemLike input) {
        return create(id, b -> b
                .duration(150)
                .require(input)
                .output(SULFUR_CHANCE, CnCleanseItems.SULFUR.get(), 1));
    }

    private GeneratedRecipe paletteSulfurCrush(AllPaletteStoneTypes type, String name) {
        return create(name, b -> b
                .duration(STONE_CRUSH_DURATION)
                .require(type.getBaseBlock().get())
                .output(SULFUR_CHANCE, CnCleanseItems.SULFUR.get(), 1));
    }
    private GeneratedRecipe paletteSulfurRecycling(AllPaletteStoneTypes type, String name) {
        return create(name + "_recycling", b -> b
                .duration(STONE_CRUSH_DURATION)
                .require(type.materialTag)
                .output(SULFUR_CHANCE, CnCleanseItems.SULFUR.get(), 1));
    }

    private GeneratedRecipe paletteLimeMudCrush(AllPaletteStoneTypes type, String name) {
        return create(name, b -> b
                .duration(LIMESTONE_CRUSH_DURATION)
                .require(type.getBaseBlock().get())
                .output(CnCleanseItems.LIME_MUD.get(), 1)
                .output(0.5f, CnCleanseItems.LIME_MUD.get(), 1));
    }
    private GeneratedRecipe paletteLimeMudRecycling(AllPaletteStoneTypes type, String name) {
        return create(name + "_recycling", b -> b
                .duration(LIMESTONE_CRUSH_DURATION)
                .require(type.materialTag)
                .output(CnCleanseItems.LIME_MUD.get(), 1)
                .output(0.5f, CnCleanseItems.LIME_MUD.get(), 1));
    }

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
