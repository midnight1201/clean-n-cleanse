package net.midnight.cncleanse.register;

import com.tterrag.registrate.util.entry.BlockEntry;
import net.midnight.cncleanse.CnCleanse;
import net.midnight.cncleanse.content.sponge.block.DrySpongeBlock;
import net.midnight.cncleanse.content.sponge.block.SpongeBlockColor;
import net.midnight.cncleanse.content.sponge.block.WetSpongeBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;

import static com.simibubi.create.foundation.data.TagGen.tagBlockAndItem;

public class CnCleanseBlocks {

    public static final BlockEntry<DrySpongeBlock> WHITE_SPONGE_BLOCK = dryBlock("white_sponge_block", SpongeBlockColor.WHITE);
    public static final BlockEntry<WetSpongeBlock> WET_WHITE_SPONGE_BLOCK = wetBlock("wet_white_sponge_block", SpongeBlockColor.WHITE);

    public static final BlockEntry<DrySpongeBlock> RED_SPONGE_BLOCK = dryBlock("red_sponge_block", SpongeBlockColor.RED);
    public static final BlockEntry<WetSpongeBlock> WET_RED_SPONGE_BLOCK = wetBlock("wet_red_sponge_block", SpongeBlockColor.RED);

    public static final BlockEntry<DrySpongeBlock> LIME_SPONGE_BLOCK = dryBlock("lime_sponge_block", SpongeBlockColor.LIME);
    public static final BlockEntry<WetSpongeBlock> WET_LIME_SPONGE_BLOCK = wetBlock("wet_lime_sponge_block", SpongeBlockColor.LIME);

    public static final BlockEntry<DrySpongeBlock> LIGHT_BLUE_SPONGE_BLOCK = dryBlock("light_blue_sponge_block", SpongeBlockColor.LIGHT_BLUE);
    public static final BlockEntry<WetSpongeBlock> WET_LIGHT_BLUE_SPONGE_BLOCK = wetBlock("wet_light_blue_sponge_block", SpongeBlockColor.LIGHT_BLUE);

    private static BlockEntry<DrySpongeBlock> dryBlock(String name, SpongeBlockColor color) {
        return CnCleanse.REGISTRATE
                .block(name, p -> new DrySpongeBlock(p, color))
                .initialProperties(() -> Blocks.SPONGE)
                .properties(p -> p.mapColor(color.mapColor()))
                .tag(BlockTags.MINEABLE_WITH_HOE)
                .blockstate((ctx, prov) -> prov.simpleBlock(ctx.get(),
                        prov.models().cubeAll(ctx.getName(), CnCleanse.spongeBlockTexture(false, name))))
                .transform(tagBlockAndItem(CnCleanseTags.Blocks.SPONGE_BLOCKS, CnCleanseTags.Items.SPONGE_BLOCKS))
                .build()
                .register();
    }
    private static BlockEntry<WetSpongeBlock> wetBlock(String name, SpongeBlockColor color) {
        return CnCleanse.REGISTRATE
                .block(name, p -> new WetSpongeBlock(p, color))
                .initialProperties(() -> Blocks.WET_SPONGE)
                .properties(p -> p.mapColor(color.mapColor()))
                .tag(BlockTags.MINEABLE_WITH_HOE)
                .blockstate((ctx, prov) -> prov.simpleBlock(ctx.get(),
                        prov.models().cubeAll(ctx.getName(), CnCleanse.spongeBlockTexture(true, name))))
                .transform(tagBlockAndItem(CnCleanseTags.Blocks.WET_SPONGE_BLOCKS, CnCleanseTags.Items.WET_SPONGE_BLOCKS))
                .build()
                .register();
    }
    public static void register() {
        CnCleanse.LOGGER.info("Register Blocks...");
    }
}
