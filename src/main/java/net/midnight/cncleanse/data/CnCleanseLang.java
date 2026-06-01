package net.midnight.cncleanse.data;

import com.tterrag.registrate.providers.ProviderType;
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
            public static final String SPONGE_BLOCKS_ITEM = tagKey(CnCleanseTags.Items.SPONGE_BLOCKS, "item");
            public static final String WET_SPONGE_BLOCKS_ITEM = tagKey(CnCleanseTags.Items.WET_SPONGE_BLOCKS, "item");
            public static final String SPONGE_ITEMS = tagKey(CnCleanseTags.Items.SPONGE_ITEMS, "item");
            public static final String WET_SPONGE_ITEMS = tagKey(CnCleanseTags.Items.WET_SPONGE_ITEMS, "item");
        }

        public static final class ResourcePacks {
            private ResourcePacks() {}

            public static final String NOMENCLATURE = "resourcepack.cncleanse.nomenclature";
        }
    }

    private static final class Default {
        private Default() {}

        static final String SPONGE_BLOCKS = "Sponge Blocks";
        static final String WET_SPONGE_BLOCKS = "Wet Sponge Blocks";
        static final String SPONGE_ITEMS = "Sponge Items";
        static final String WET_SPONGE_ITEMS = "Wet Sponge Items";
        static final String NOMENCLATURE_PACK = "Clean n' Cleanse: Nomenclature";
    }

    public static void register() {
        CnCleanse.REGISTRATE.addDataGenerator(ProviderType.LANG, prov -> {
            prov.add(Keys.Tags.SPONGE_BLOCKS_BLOCK, Default.SPONGE_BLOCKS);
            prov.add(Keys.Tags.WET_SPONGE_BLOCKS_BLOCK, Default.WET_SPONGE_BLOCKS);
            prov.add(Keys.Tags.SPONGE_BLOCKS_ITEM, Default.SPONGE_BLOCKS);
            prov.add(Keys.Tags.WET_SPONGE_BLOCKS_ITEM, Default.WET_SPONGE_BLOCKS);
            prov.add(Keys.Tags.SPONGE_ITEMS, Default.SPONGE_ITEMS);
            prov.add(Keys.Tags.WET_SPONGE_ITEMS, Default.WET_SPONGE_ITEMS);

            prov.add(Keys.ResourcePacks.NOMENCLATURE, Default.NOMENCLATURE_PACK);

            prov.add("item.cncleanse.lime_sulfur_bottle.tooltip.summary",
                    "Fungicide and patina agent.");
            prov.add("item.cncleanse.lime_sulfur_bottle.tooltip.condition1",
                    "When used on Mycelium");
            prov.add("item.cncleanse.lime_sulfur_bottle.tooltip.behaviour1",
                    "Converts _Mycelium_ into _Dirt_.");
            prov.add("item.cncleanse.lime_sulfur_bottle.tooltip.condition2",
                    "When used on Copper");
            prov.add("item.cncleanse.lime_sulfur_bottle.tooltip.behaviour2",
                    "Increases the _oxidation_ of _unwaxed_ copper.");
        });
    }

    public static String tagKey(TagKey<?> tag, String registryType) {
        ResourceLocation id = tag.location();
        return "tag." + registryType + "." + id.getNamespace() + "." + id.getPath();
    }
}