package net.midnight.cncleanse.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.recipe.IRecipeManager;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.midnight.cncleanse.CnCleanse;
import net.midnight.cncleanse.register.CnCleanseItems;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class CnCleanseJeiPlugin implements IModPlugin {

    private static final RecipeType<RecipeHolder<Recipe<?>>> CREATE_SPOUT =
            RecipeType.createRecipeHolderType(
                    ResourceLocation.fromNamespaceAndPath("create", "spout_filling"));

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return CnCleanse.asResource("jei");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addIngredientInfo(
                new ItemStack(CnCleanseItems.LIME_SULFUR_BOTTLE.get()),
                VanillaTypes.ITEM_STACK,
                Component.translatable("item.cncleanse.lime_sulfur_bottle.tooltip.summary"),
                Component.translatable("item.cncleanse.lime_sulfur_bottle.tooltip.behaviour1")
        );
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime runtime) {
        IRecipeManager recipes = runtime.getRecipeManager();

        var spoutType = RecipeType.createRecipeHolderType(
                ResourceLocation.fromNamespaceAndPath("create", "spout_filling"));

        var toHide = recipes.createRecipeLookup(spoutType)
                .get()
                .filter(holder -> {
                    ResourceLocation id = holder.id();
                    return "create".equals(id.getNamespace())
                            && id.getPath().startsWith("fill_cncleanse_")
                            && id.getPath().contains("sponge")
                            && id.getPath().endsWith("_water");
                })
                .toList();

        if (!toHide.isEmpty()) {
            recipes.hideRecipes(spoutType, toHide);
        }
    }
}