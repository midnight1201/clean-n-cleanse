package net.midnight.cncleanse.data;

import net.midnight.cncleanse.CnCleanse;
import net.midnight.cncleanse.register.CnCleanseTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider.TagAppender;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public final class CnCleanseCompatTagGen {

    private static final String[] SULFUR_FAMILY = {
            "sulfur",
            "polished_sulfur",
            "sulfur_bricks",
            "chiseled_sulfur",
            "sulfur_stairs",
            "sulfur_wall",
            "polished_sulfur_stairs",
            "polished_sulfur_wall",
            "sulfur_brick_stairs",
            "sulfur_brick_wall",
    };

    private CnCleanseCompatTagGen() {}

    private static <T> void addSulfurVariants(TagAppender<T> appender) {
        for (String id : SULFUR_FAMILY) {
            appender.addOptional(ResourceLocation.fromNamespaceAndPath("minecraft", id));
        }
    }

    public static final class Blocks extends BlockTagsProvider {

        public Blocks(PackOutput output,
                      CompletableFuture<HolderLookup.Provider> lookup,
                      @Nullable ExistingFileHelper existingFileHelper) {
            super(output, lookup, CnCleanse.ID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            addSulfurVariants(tag(CnCleanseTags.Blocks.VB_SULFUR_BLOCKS));
        }
    }

    public static final class Items extends ItemTagsProvider {

        public Items(PackOutput output,
                     CompletableFuture<HolderLookup.Provider> lookup,
                     CompletableFuture<TagLookup<Block>> blockTags,
                     @Nullable ExistingFileHelper existingFileHelper) {
            super(output, lookup, blockTags, CnCleanse.ID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            addSulfurVariants(tag(CnCleanseTags.Items.VB_SULFUR_BLOCKS));
        }
    }
}