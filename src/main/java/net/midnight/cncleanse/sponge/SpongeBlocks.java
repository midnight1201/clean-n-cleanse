package net.midnight.cncleanse.sponge;

import com.tterrag.registrate.util.entry.BlockEntry;
import net.midnight.cncleanse.AllBlocks;
import net.minecraft.world.level.block.Block;

import java.util.Map;

public final class SpongeBlocks {

    public record Pair(BlockEntry<? extends Block> dry, BlockEntry<? extends Block> wet) {}

    private static Map<SpongeColor, Pair> byColor;

    public static Pair forColor(SpongeColor color) {
        if (byColor == null) {
            byColor = Map.of(
                    SpongeColor.WHITE, new Pair(AllBlocks.WHITE_SPONGE_BLOCK, AllBlocks.WET_WHITE_SPONGE_BLOCK),
                    SpongeColor.RED, new Pair(AllBlocks.RED_SPONGE_BLOCK, AllBlocks.WET_RED_SPONGE_BLOCK),
                    SpongeColor.LIME, new Pair(AllBlocks.LIME_SPONGE_BLOCK, AllBlocks.WET_LIME_SPONGE_BLOCK),
                    SpongeColor.LIGHT_BLUE, new Pair(AllBlocks.LIGHT_BLUE_SPONGE_BLOCK, AllBlocks.WET_LIGHT_BLUE_SPONGE_BLOCK)
            );
        }
        return byColor.get(color);
    }

    private SpongeBlocks() {}
}