package net.midnight.cncleanse.content.sponge.block;  // or keep in .block

import com.tterrag.registrate.util.entry.BlockEntry;
import net.midnight.cncleanse.register.CnCleanseBlocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;

public enum SpongeBlockColor {
    WHITE, RED, LIME, LIGHT_BLUE;

    public MapColor mapColor() {
        return switch (this) {
            case WHITE -> MapColor.SNOW;
            case RED -> MapColor.COLOR_RED;
            case LIME -> MapColor.COLOR_LIGHT_GREEN;
            case LIGHT_BLUE -> MapColor.COLOR_LIGHT_BLUE;
        };
    }

    public BlockEntry<DrySpongeBlock> dry() {
        return switch (this) {
            case WHITE -> CnCleanseBlocks.WHITE_SPONGE_BLOCK;
            case RED -> CnCleanseBlocks.RED_SPONGE_BLOCK;
            case LIME -> CnCleanseBlocks.LIME_SPONGE_BLOCK;
            case LIGHT_BLUE -> CnCleanseBlocks.LIGHT_BLUE_SPONGE_BLOCK;
        };
    }

    public BlockEntry<WetSpongeBlock> wet() {
        return switch (this) {
            case WHITE -> CnCleanseBlocks.WET_WHITE_SPONGE_BLOCK;
            case RED -> CnCleanseBlocks.WET_RED_SPONGE_BLOCK;
            case LIME -> CnCleanseBlocks.WET_LIME_SPONGE_BLOCK;
            case LIGHT_BLUE -> CnCleanseBlocks.WET_LIGHT_BLUE_SPONGE_BLOCK;
        };
    }

    public String blockId(boolean wet) {
        String color = name().toLowerCase();
        return wet ? "wet_" + color + "_sponge_block" : color + "_sponge_block";
    }

    public BlockState dryState() {
        return dry().get().defaultBlockState();
    }

    public BlockState wetState() {
        return wet().get().defaultBlockState();
    }
}