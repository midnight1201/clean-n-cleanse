package net.midnight.cncleanse;

import com.tterrag.registrate.util.entry.ItemEntry;
import net.midnight.cncleanse.sponge.SpongeItem;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class AllItems {

    public static final ItemEntry<Item> WET_WHITE_SPONGE = wet("wet_white_sponge");
    public static final ItemEntry<Item> WET_RED_SPONGE = wet("wet_red_sponge");
    public static final ItemEntry<Item> WET_YELLOW_SPONGE = wet("wet_yellow_sponge");
    public static final ItemEntry<Item> WET_LIME_SPONGE = wet("wet_lime_sponge");
    public static final ItemEntry<Item> WET_LIGHT_BLUE_SPONGE = wet("wet_light_blue_sponge");

    public static final ItemEntry<SpongeItem> WHITE_SPONGE = dry("white_sponge", WET_WHITE_SPONGE);
    public static final ItemEntry<SpongeItem> RED_SPONGE = dry("red_sponge", WET_RED_SPONGE);
    public static final ItemEntry<SpongeItem> YELLOW_SPONGE = dry("yellow_sponge", WET_YELLOW_SPONGE);
    public static final ItemEntry<SpongeItem> LIME_SPONGE = dry("lime_sponge", WET_LIME_SPONGE);
    public static final ItemEntry<SpongeItem> LIGHT_BLUE_SPONGE = dry("light_blue_sponge", WET_LIGHT_BLUE_SPONGE);

    private static ItemEntry<SpongeItem> dry(String name, Supplier<Item> wetItem) {
        return CnCleanse.REGISTRATE
                .item(name, p -> new SpongeItem(p, wetItem))
                .tag(AllTags.Items.SPONGE_ITEMS)
                .register();
    }
    private static ItemEntry<Item> wet(String name) {
        return CnCleanse.REGISTRATE
                .item(name, Item::new)
                .tag(AllTags.Items.WET_SPONGE_ITEMS)
                .register();
    }

    public static void register() {
        CnCleanse.LOGGER.info("Register Items...");
    }
}
