package net.midnight.cncleanse.content.sponge.item;

import com.simibubi.create.content.fluids.VirtualFluid;
import com.tterrag.registrate.util.entry.FluidEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.midnight.cncleanse.content.sponge.block.SpongeBlockColor;
import net.midnight.cncleanse.register.CnCleanseFluids;
import net.midnight.cncleanse.register.CnCleanseItems;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

import java.util.Optional;

public enum SpongeItemColor {
    WHITE, RED, YELLOW, LIME, LIGHT_BLUE;

    public Optional<SpongeBlockColor> toBlockColor() {
        return switch (this) {
            case WHITE -> Optional.of(SpongeBlockColor.WHITE);
            case RED -> Optional.of(SpongeBlockColor.RED);
            case LIME -> Optional.of(SpongeBlockColor.LIME);
            case LIGHT_BLUE -> Optional.of(SpongeBlockColor.LIGHT_BLUE);
            case YELLOW -> Optional.empty();
        };
    }

    public ItemEntry<DrySpongeItem> dry() {
        return switch (this) {
            case WHITE -> CnCleanseItems.WHITE_SPONGE;
            case RED -> CnCleanseItems.RED_SPONGE;
            case YELLOW -> CnCleanseItems.YELLOW_SPONGE;
            case LIME -> CnCleanseItems.LIME_SPONGE;
            case LIGHT_BLUE -> CnCleanseItems.LIGHT_BLUE_SPONGE;
        };
    }
    public ItemEntry<WetSpongeItem> wet() {
        return switch (this) {
            case WHITE -> CnCleanseItems.WET_WHITE_SPONGE;
            case RED -> CnCleanseItems.WET_RED_SPONGE;
            case YELLOW -> CnCleanseItems.WET_YELLOW_SPONGE;
            case LIME -> CnCleanseItems.WET_LIME_SPONGE;
            case LIGHT_BLUE -> CnCleanseItems.WET_LIGHT_BLUE_SPONGE;
        };
    }

    public DyeColor dye() {
        return switch (this) {
            case WHITE -> DyeColor.WHITE;
            case RED -> DyeColor.RED;
            case YELLOW -> DyeColor.YELLOW;
            case LIME -> DyeColor.LIME;
            case LIGHT_BLUE -> DyeColor.LIGHT_BLUE;
        };
    }
    public FluidEntry<VirtualFluid> saltedViscose() {
        return switch (this) {
            case WHITE -> CnCleanseFluids.WHITE_SALTED_VISCOSE;
            case RED -> CnCleanseFluids.RED_SALTED_VISCOSE;
            case YELLOW -> CnCleanseFluids.YELLOW_SALTED_VISCOSE;
            case LIME -> CnCleanseFluids.LIME_SALTED_VISCOSE;
            case LIGHT_BLUE -> CnCleanseFluids.LIGHT_BLUE_SALTED_VISCOSE;
        };
    }

    public ItemLike dryBlock() {
        return toBlockColor()
                .<ItemLike>map(c -> c.dry().get())
                .orElse(Blocks.SPONGE);
    }
    public ItemLike wetBlock() {
        return toBlockColor()
                .<ItemLike>map(c -> c.wet().get())
                .orElse(Blocks.WET_SPONGE);
    }

    public String itemId(boolean wet) {
        String color = name().toLowerCase();
        return wet ? "wet_" + color + "_sponge" : color + "_sponge";
    }
}