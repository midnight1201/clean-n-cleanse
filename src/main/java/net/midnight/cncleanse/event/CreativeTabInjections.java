package net.midnight.cncleanse.event;

import net.midnight.cncleanse.register.CnCleanseBlocks;
import net.midnight.cncleanse.register.CnCleanseCreativeTabs;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

public final class CreativeTabInjections {

    private CreativeTabInjections() {}

    public static void onBuildCreativeTabContents(BuildCreativeModeTabContentsEvent event) {
        if (!event.getTabKey().equals(CnCleanseCreativeTabs.MAIN_TAB.getKey())) {
            return;
        }
        ItemStack anchor = new ItemStack(CnCleanseBlocks.WET_RED_SPONGE_BLOCK.get());
        event.insertAfter(
                anchor,
                new ItemStack(Items.SPONGE),
                CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
        );
        event.insertAfter(
                new ItemStack(Items.SPONGE),
                new ItemStack(Items.WET_SPONGE),
                CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
        );
    }
}
