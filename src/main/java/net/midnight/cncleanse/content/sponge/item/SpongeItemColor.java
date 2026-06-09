package net.midnight.cncleanse.content.sponge.item;  // or keep in .item

import com.tterrag.registrate.util.entry.ItemEntry;
import net.midnight.cncleanse.content.sponge.block.SpongeBlockColor;
import net.midnight.cncleanse.register.CnCleanseItems;

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

    public String itemId(boolean wet) {
        String color = name().toLowerCase();
        return wet ? "wet_" + color + "_sponge" : color + "_sponge";
    }
}