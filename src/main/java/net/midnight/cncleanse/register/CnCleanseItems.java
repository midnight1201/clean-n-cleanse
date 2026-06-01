package net.midnight.cncleanse.register;

import com.simibubi.create.AllTags;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.midnight.cncleanse.CnCleanse;
import net.midnight.cncleanse.content.LimeSulfurBottle;
import net.midnight.cncleanse.content.sponge.item.DrySpongeItem;
import net.midnight.cncleanse.content.sponge.item.SpongeItemColor;
import net.midnight.cncleanse.content.sponge.item.WetSpongeItem;
import net.minecraft.world.item.Item;

import java.util.function.Function;

public class CnCleanseItems {

    private static final int BOTTLE_STACK_SIZE = 16;

    public static final ItemEntry<DrySpongeItem> WHITE_SPONGE = dry("white_sponge", SpongeItemColor.WHITE);
    public static final ItemEntry<WetSpongeItem> WET_WHITE_SPONGE = wet("wet_white_sponge", SpongeItemColor.WHITE);

    public static final ItemEntry<DrySpongeItem> RED_SPONGE = dry("red_sponge", SpongeItemColor.RED);
    public static final ItemEntry<WetSpongeItem> WET_RED_SPONGE = wet("wet_red_sponge", SpongeItemColor.RED);

    public static final ItemEntry<DrySpongeItem> YELLOW_SPONGE = dry("yellow_sponge", SpongeItemColor.YELLOW);
    public static final ItemEntry<WetSpongeItem> WET_YELLOW_SPONGE = wet("wet_yellow_sponge", SpongeItemColor.YELLOW);

    public static final ItemEntry<DrySpongeItem> LIME_SPONGE = dry("lime_sponge", SpongeItemColor.LIME);
    public static final ItemEntry<WetSpongeItem> WET_LIME_SPONGE = wet("wet_lime_sponge", SpongeItemColor.LIME);

    public static final ItemEntry<DrySpongeItem> LIGHT_BLUE_SPONGE = dry("light_blue_sponge", SpongeItemColor.LIGHT_BLUE);
    public static final ItemEntry<WetSpongeItem> WET_LIGHT_BLUE_SPONGE = wet("wet_light_blue_sponge", SpongeItemColor.LIGHT_BLUE);

    public static final ItemEntry<Item> SLAKED_LIME_BOTTLE = bottle("slaked_lime", "Milk of Lime");
    public static final ItemEntry<Item> CAUSTIC_SODA_BOTTLE = bottle("caustic_soda", "Lye");
    public static final ItemEntry<Item> CARBON_DISULFIDE_BOTTLE = bottle("carbon_disulfide", "Carbon Disulfide");
    public static final ItemEntry<LimeSulfurBottle> LIME_SULFUR_BOTTLE = bottle("lime_sulfur", "Lime Sulfur", LimeSulfurBottle::new);

    private static ItemEntry<Item> bottle(String fluidName, String lang) {
        return bottle(fluidName, lang, Item::new);
    }

    private static <T extends Item> ItemEntry<T> bottle(String fluidName, String lang, Function<Item.Properties, T> factory) {
        String name = fluidName + "_bottle";
        return CnCleanse.REGISTRATE
                .item(name, p -> factory.apply(p))
                .properties(p -> p.stacksTo(BOTTLE_STACK_SIZE))
                .tag(AllTags.AllItemTags.UPRIGHT_ON_BELT.tag)
                .lang(lang)
                .model((ctx, prov) -> prov.generated(ctx, CnCleanse.bottleTexture(fluidName)))
                .register();
    }

    private static ItemEntry<DrySpongeItem> dry(String name, SpongeItemColor color) {
        return CnCleanse.REGISTRATE
                .item(name, p -> new DrySpongeItem(p, color))
                .tag(CnCleanseTags.Items.SPONGE_ITEMS)
                .model((ctx, prov) -> prov.generated(ctx, CnCleanse.spongeItemTexture(false, name)))
                .register();
    }
    private static ItemEntry<WetSpongeItem> wet(String name, SpongeItemColor color) {
        return CnCleanse.REGISTRATE
                .item(name, p -> new WetSpongeItem(p, color))
                .tag(CnCleanseTags.Items.WET_SPONGE_ITEMS)
                .model((ctx, prov) -> prov.generated(ctx, CnCleanse.spongeItemTexture(true, name)))
                .register();
    }

    public static void register() {
        CnCleanse.LOGGER.info("Register Items...");
    }
}
