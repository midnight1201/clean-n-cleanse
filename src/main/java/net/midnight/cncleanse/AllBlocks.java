package net.midnight.cncleanse;

import com.tterrag.registrate.util.entry.BlockEntry;
import net.midnight.cncleanse.sponge.DrySpongeBlock;
import net.midnight.cncleanse.sponge.SpongeColor;
import net.midnight.cncleanse.sponge.WetSpongeBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;

import static com.simibubi.create.foundation.data.TagGen.tagBlockAndItem;

public class AllBlocks {

    public static final BlockEntry<DrySpongeBlock> WHITE_SPONGE_BLOCK =
            dryBlock("white_sponge_block", SpongeColor.WHITE);
    public static final BlockEntry<WetSpongeBlock> WET_WHITE_SPONGE_BLOCK =
            wetBlock("wet_white_sponge_block", SpongeColor.WHITE);
    public static final BlockEntry<DrySpongeBlock> RED_SPONGE_BLOCK =
            dryBlock("red_sponge_block", SpongeColor.RED);
    public static final BlockEntry<WetSpongeBlock> WET_RED_SPONGE_BLOCK =
            wetBlock("wet_red_sponge_block", SpongeColor.RED);
    public static final BlockEntry<DrySpongeBlock> LIME_SPONGE_BLOCK =
            dryBlock("lime_sponge_block", SpongeColor.LIME);
    public static final BlockEntry<WetSpongeBlock> WET_LIME_SPONGE_BLOCK =
            wetBlock("wet_lime_sponge_block", SpongeColor.LIME);
    public static final BlockEntry<DrySpongeBlock> LIGHT_BLUE_SPONGE_BLOCK =
            dryBlock("light_blue_sponge_block", SpongeColor.LIGHT_BLUE);
    public static final BlockEntry<WetSpongeBlock> WET_LIGHT_BLUE_SPONGE_BLOCK =
            wetBlock("wet_light_blue_sponge_block", SpongeColor.LIGHT_BLUE);

    private static BlockEntry<DrySpongeBlock> dryBlock(String name, SpongeColor color) {
        return CnCleanse.REGISTRATE
                .block(name, p -> new DrySpongeBlock(p, color))
                .initialProperties(() -> Blocks.SPONGE)
                .tag(BlockTags.MINEABLE_WITH_HOE)
                .transform(tagBlockAndItem(AllTags.Blocks.SPONGE_BLOCKS, AllTags.Items.SPONGE_BLOCKS))
                .build()
                .register();
    }
    private static BlockEntry<WetSpongeBlock> wetBlock(String name, SpongeColor color) {
        return CnCleanse.REGISTRATE
                .block(name, p -> new WetSpongeBlock(p, color))
                .initialProperties(() -> Blocks.WET_SPONGE)
                .tag(BlockTags.MINEABLE_WITH_HOE)
                .transform(tagBlockAndItem(AllTags.Blocks.WET_SPONGE_BLOCKS, AllTags.Items.WET_SPONGE_BLOCKS))
                .build()
                .register();
    }
    public static void register() {
        CnCleanse.LOGGER.info("Register Blocks...");
    }
}
