package net.midnight.cncleanse.data;

import com.tterrag.registrate.providers.ProviderType;
import net.midnight.cncleanse.CnCleanse;
import net.midnight.cncleanse.register.CnCleanseTags;
import net.minecraft.world.level.block.Blocks;

public class CnCleanseRegistrateTags {

    public static void register() {
        CnCleanse.REGISTRATE.addDataGenerator(ProviderType.BLOCK_TAGS, prov -> {
            prov.addTag(CnCleanseTags.Blocks.SPONGE_BLOCKS)
                    .add(Blocks.SPONGE);
            prov.addTag(CnCleanseTags.Blocks.WET_SPONGE_BLOCKS)
                    .add(Blocks.WET_SPONGE);
        });

        CnCleanse.REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, prov -> {
            prov.addTag(CnCleanseTags.Items.SPONGE_BLOCKS)
                    .add(Blocks.SPONGE.asItem());
            prov.addTag(CnCleanseTags.Items.WET_SPONGE_BLOCKS)
                    .add(Blocks.WET_SPONGE.asItem());
        });
    }
}