package net.midnight.cncleanse.register;

import com.simibubi.create.AllTags;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.midnight.cncleanse.CnCleanse;
import net.midnight.cncleanse.content.LimeSulfurBottle;
import net.midnight.cncleanse.content.sponge.item.DrySpongeItem;
import net.midnight.cncleanse.content.sponge.item.SpongeItemColor;
import net.midnight.cncleanse.content.sponge.item.WetSpongeItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.Tags;

import java.util.function.Function;

public class CnCleanseItems {

    private static final int BOTTLE_STACK_SIZE = 16;

    // --- sponge items ---

    public static final ItemEntry<DrySpongeItem> WHITE_SPONGE = dry(SpongeItemColor.WHITE);
    public static final ItemEntry<WetSpongeItem> WET_WHITE_SPONGE = wet(SpongeItemColor.WHITE);

    public static final ItemEntry<DrySpongeItem> RED_SPONGE = dry(SpongeItemColor.RED);
    public static final ItemEntry<WetSpongeItem> WET_RED_SPONGE = wet(SpongeItemColor.RED);

    public static final ItemEntry<DrySpongeItem> YELLOW_SPONGE = dry(SpongeItemColor.YELLOW);
    public static final ItemEntry<WetSpongeItem> WET_YELLOW_SPONGE = wet(SpongeItemColor.YELLOW);

    public static final ItemEntry<DrySpongeItem> LIME_SPONGE = dry(SpongeItemColor.LIME);
    public static final ItemEntry<WetSpongeItem> WET_LIME_SPONGE = wet(SpongeItemColor.LIME);

    public static final ItemEntry<DrySpongeItem> LIGHT_BLUE_SPONGE = dry(SpongeItemColor.LIGHT_BLUE);
    public static final ItemEntry<WetSpongeItem> WET_LIGHT_BLUE_SPONGE = wet(SpongeItemColor.LIGHT_BLUE);

    // --- powders ---

    public static final ItemEntry<Item> ALKALI_CELLULOSE = CnCleanse.REGISTRATE
            .item("alkali_cellulose", Item::new)
            .model((ctx, prov) -> prov.generated(ctx, CnCleanse.asResource("item/alkali_cellulose")))
            .register();

    public static final ItemEntry<Item> LIME_MUD = powder("lime_mud", "Lime Mud");
    public static final ItemEntry<Item> QUICKLIME = powder("quicklime", "Quicklime");
    public static final ItemEntry<Item> SODA_ASH = powder("soda_ash", "Soda Ash");

    public static final ItemEntry<Item> SALT = CnCleanse.REGISTRATE
            .item("salt", Item::new)
            .lang("Salt")
            .tag(Tags.Items.DUSTS)
            .tag(CnCleanseTags.Items.dust("salt"))
            .tag(CnCleanseTags.Items.SALTS)
            .model((ctx, prov) -> prov.generated(ctx, CnCleanse.asResource("item/salt")))
            .register();

    public static final ItemEntry<Item> SULFUR = powder("sulfur", "Powdered Sulfur");
    public static final ItemEntry<Item> SODIUM_SULFATE = powder("sodium_sulfate", "Sodium Sulfate");
    public static final ItemEntry<Item> XANTHATE = powder("xanthate", "Xanthate");
    public static final ItemEntry<Item> KELP_ASH = powder("kelp_ash", "Kelp Ash");

    // --- fluid bottles ---

    public static final ItemEntry<Item> CAUSTIC_SODA_BOTTLE = bottle("caustic_soda", "Lye");
    public static final ItemEntry<Item> CARBON_DISULFIDE_BOTTLE = bottle("carbon_disulfide", "Carbon Disulfide");
    public static final ItemEntry<Item> SLAKED_LIME_BOTTLE = bottle("slaked_lime", "Milk of Lime");
    public static final ItemEntry<Item> VISCOSE_BOTTLE = bottle("viscose", "Viscose");
    public static final ItemEntry<LimeSulfurBottle> LIME_SULFUR_BOTTLE = bottle("lime_sulfur", "Lime Sulfur", LimeSulfurBottle::new);

    // --- registration helpers ---

    private static ItemEntry<DrySpongeItem> dry(SpongeItemColor color) {
        String name = color.itemId(false);
        return CnCleanse.REGISTRATE
                .item(name, p -> new DrySpongeItem(p, color))
                .tag(CnCleanseTags.Items.SPONGE_ITEMS)
                .model((ctx, prov) -> prov.generated(ctx, CnCleanse.spongeItemTexture(false, name)))
                .register();
    }
    private static ItemEntry<WetSpongeItem> wet(SpongeItemColor color) {
        String name = color.itemId(true);
        return CnCleanse.REGISTRATE
                .item(name, p -> new WetSpongeItem(p, color))
                .tag(CnCleanseTags.Items.WET_SPONGE_ITEMS)
                .model((ctx, prov) -> prov.generated(ctx, CnCleanse.spongeItemTexture(true, name)))
                .register();
    }

    private static ItemEntry<Item> powder(String name, String lang) {
        return CnCleanse.REGISTRATE
                .item(name, Item::new)
                .lang(lang)
                .tag(Tags.Items.DUSTS)
                .tag(CnCleanseTags.Items.dust(name))
                .model((ctx, prov) -> prov.generated(ctx, CnCleanse.asResource("item/" + name)))
                .register();
    }

    private static ItemEntry<Item> bottle(String fluidName, String lang) {
        return bottle(fluidName, lang, Item::new);
    }
    private static <T extends Item> ItemEntry<T> bottle(String fluidName, String lang, Function<Item.Properties, T> factory) {
        String name = fluidName + "_bottle";
        return CnCleanse.REGISTRATE
                .item(name, factory::apply)
                .properties(p -> p.stacksTo(BOTTLE_STACK_SIZE))
                .tag(AllTags.AllItemTags.UPRIGHT_ON_BELT.tag)
                .lang(lang)
                .model((ctx, prov) -> prov.generated(ctx, CnCleanse.bottleTexture(fluidName)))
                .register();
    }

    // --- register ---

    public static void register() {
        CnCleanse.LOGGER.info("Register Items...");
    }
}
