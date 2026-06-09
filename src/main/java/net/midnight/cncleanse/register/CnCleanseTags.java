package net.midnight.cncleanse.register;

import net.midnight.cncleanse.CnCleanse;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class CnCleanseTags {
    public static final class Blocks {
        public static final TagKey<Block> SPONGE_BLOCKS = BlockTags.create(CnCleanse.asResource("sponge_blocks"));
        public static final TagKey<Block> WET_SPONGE_BLOCKS = BlockTags.create(CnCleanse.asResource("wet_sponge_blocks"));

        public static final TagKey<Block> VB_SULFUR_BLOCKS =
                BlockTags.create(CnCleanse.asResource("compat/sulfur_blocks"));
        private Blocks() {}
    }
    public static final class Items {
        public static final TagKey<Item> SPONGE_BLOCKS = ItemTags.create(CnCleanse.asResource("sponge_blocks"));
        public static final TagKey<Item> WET_SPONGE_BLOCKS = ItemTags.create(CnCleanse.asResource("wet_sponge_blocks"));
        public static final TagKey<Item> SPONGE_ITEMS = ItemTags.create(CnCleanse.asResource("sponge_items"));
        public static final TagKey<Item> WET_SPONGE_ITEMS = ItemTags.create(CnCleanse.asResource("wet_sponge_items"));

        public static final TagKey<Item> VB_SULFUR_BLOCKS =
                ItemTags.create(CnCleanse.asResource("compat/sulfur_blocks"));

        public static TagKey<Item> dust(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "dusts/" + name));
        }
        private Items() {}
    }
    private CnCleanseTags() {}
}
