package net.midnight.cncleanse.data.recipe.create;

import com.simibubi.create.api.data.recipe.WashingRecipeGen;
import net.midnight.cncleanse.CnCleanse;
import net.midnight.cncleanse.content.sponge.item.SpongeItemColor;
import net.midnight.cncleanse.register.CnCleanseItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public class CnCleanseWashingRecipeGen extends WashingRecipeGen {

    public CnCleanseWashingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CnCleanse.ID);
    }

    GeneratedRecipe
            WHITE = washSponge(SpongeItemColor.WHITE),
            RED = washSponge(SpongeItemColor.RED),
            YELLOW = washSponge(SpongeItemColor.YELLOW),
            LIME = washSponge(SpongeItemColor.LIME),
            LIGHT_BLUE = washSponge(SpongeItemColor.LIGHT_BLUE),

            KELP_ASH = create("kelp_ash", b -> b
                    .require(CnCleanseItems.KELP_ASH.get())
                    .output(0.5f, CnCleanseItems.SALT.get())
                    .output(0.25f, CnCleanseItems.SODA_ASH.get()));

    private GeneratedRecipe washSponge(SpongeItemColor color) {
        String name = color.name().toLowerCase();
        return create("wash_" + name + "_sponge", b -> b
                .require(color.dryBlock())
                .output(color.wetBlock()));
    }
}