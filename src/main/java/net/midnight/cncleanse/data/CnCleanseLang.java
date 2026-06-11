package net.midnight.cncleanse.data;

import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateLangProvider;
import net.midnight.cncleanse.CnCleanse;
import net.midnight.cncleanse.register.CnCleanseTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

public final class CnCleanseLang {

    private CnCleanseLang() {}

    public static final class Keys {
        private Keys() {}

        public static final class Tags {
            private Tags() {}

            public static final String SPONGE_BLOCKS_BLOCK = tagKey(CnCleanseTags.Blocks.SPONGE_BLOCKS, "block");
            public static final String WET_SPONGE_BLOCKS_BLOCK = tagKey(CnCleanseTags.Blocks.WET_SPONGE_BLOCKS, "block");
            public static final String SULFUR_BLOCKS_BLOCK = tagKey(CnCleanseTags.Items.VB_SULFUR_BLOCKS, "block");

            public static final String SPONGE_BLOCKS_ITEM = tagKey(CnCleanseTags.Items.SPONGE_BLOCKS, "item");
            public static final String WET_SPONGE_BLOCKS_ITEM = tagKey(CnCleanseTags.Items.WET_SPONGE_BLOCKS, "item");
            public static final String SULFUR_BLOCKS_ITEM = tagKey(CnCleanseTags.Items.VB_SULFUR_BLOCKS, "item");
            public static final String SPONGE_ITEMS = tagKey(CnCleanseTags.Items.SPONGE_ITEMS, "item");
            public static final String WET_SPONGE_ITEMS = tagKey(CnCleanseTags.Items.WET_SPONGE_ITEMS, "item");
        }

        public static final class Tooltips {
            private Tooltips() {}
            public static final String LIME_SULFUR_BOTTLE = "item.cncleanse.lime_sulfur_bottle.tooltip";
        }

        public static final class Vanilla {
            private Vanilla() {}

            public static final String SPONGE_BLOCK = "block.minecraft.sponge";
            public static final String WET_SPONGE_BLOCK = "block.minecraft.wet_sponge";
        }
    }

    private static final class Default {
        private Default() {}

        static final String SPONGE_BLOCKS = "Sponge Blocks";
        static final String WET_SPONGE_BLOCKS = "Wet Sponge Blocks";
        static final String SULFUR_BLOCKS = "Sulfur Blocks";

        static final String SPONGE_ITEMS = "Sponge Items";
        static final String WET_SPONGE_ITEMS = "Wet Sponge Items";

        static final String LIME_SULFUR_SUMMARY = "Fungicide and patina agent.";
        static final String LIME_SULFUR_C1 = "When used on Mycelium";
        static final String LIME_SULFUR_B1 = "_Sterilizes_ fungal spores.";
        static final String LIME_SULFUR_C2 = "When used on unwaxed Copper";
        static final String LIME_SULFUR_B2 = "Increases the _oxidation_ state.";

        static final String YELLOW_SPONGE_BLOCK = "Yellow Sponge Block";
        static final String WET_YELLOW_SPONGE_BLOCK = "Wet Yellow Sponge Block";
    }

    public static void register() {
        CnCleanse.REGISTRATE.addDataGenerator(ProviderType.LANG, prov -> {
            prov.add(Keys.Tags.SPONGE_BLOCKS_BLOCK, Default.SPONGE_BLOCKS);
            prov.add(Keys.Tags.WET_SPONGE_BLOCKS_BLOCK, Default.WET_SPONGE_BLOCKS);
            prov.add(Keys.Tags.SULFUR_BLOCKS_BLOCK, Default.SULFUR_BLOCKS);

            prov.add(Keys.Tags.SPONGE_BLOCKS_ITEM, Default.SPONGE_BLOCKS);
            prov.add(Keys.Tags.WET_SPONGE_BLOCKS_ITEM, Default.WET_SPONGE_BLOCKS);
            prov.add(Keys.Tags.SULFUR_BLOCKS_ITEM, Default.SULFUR_BLOCKS);

            prov.add(Keys.Tags.SPONGE_ITEMS, Default.SPONGE_ITEMS);
            prov.add(Keys.Tags.WET_SPONGE_ITEMS, Default.WET_SPONGE_ITEMS);

            String base = Keys.Tooltips.LIME_SULFUR_BOTTLE;
            addTooltipSummary(prov, base, Default.LIME_SULFUR_SUMMARY);
            addTooltipLine(prov, base, 1, Default.LIME_SULFUR_C1, Default.LIME_SULFUR_B1);
            addTooltipLine(prov, base, 2, Default.LIME_SULFUR_C2, Default.LIME_SULFUR_B2);

            prov.add(Keys.Vanilla.SPONGE_BLOCK, Default.YELLOW_SPONGE_BLOCK);
            prov.add(Keys.Vanilla.WET_SPONGE_BLOCK, Default.WET_YELLOW_SPONGE_BLOCK);
        });
    }

    public static String tagKey(TagKey<?> tag, String registryType) {
        ResourceLocation id = tag.location();
        return "tag." + registryType + "." + id.getNamespace() + "." + id.getPath();
    }

    private static void addTooltipSummary(RegistrateLangProvider prov, String base, String summary) {
        prov.add(base + ".summary", summary);
    }
    private static void addTooltipLine(
            RegistrateLangProvider prov,
            String base,
            int index,
            String condition,
            String behaviour) {
        prov.add(base + ".condition" + index, condition);
        prov.add(base + ".behaviour" + index, behaviour);
    }
}