package net.midnight.cncleanse;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import net.createmod.catnip.lang.FontHelper;
import net.midnight.cncleanse.data.CnCleanseLang;
import net.midnight.cncleanse.data.CnCleanseRegistrateTags;
import net.midnight.cncleanse.event.CreativeTabInjections;
import net.midnight.cncleanse.register.CnCleanseBlocks;
import net.midnight.cncleanse.register.CnCleanseCreativeTabs;
import net.midnight.cncleanse.register.CnCleanseFluids;
import net.midnight.cncleanse.register.CnCleanseItems;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(CnCleanse.ID)
public class CnCleanse {
    public static final String ID = "cncleanse";
    public static final Logger LOGGER = LogManager.getLogger(ID);

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(ID)
            .defaultCreativeTab((ResourceKey<CreativeModeTab>) null)
            .setTooltipModifierFactory(item ->
                    new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE)
                            .andThen(TooltipModifier.mapNull(KineticStats.create(item)))
            );

    public CnCleanse(IEventBus modBus) {
        REGISTRATE.registerEventListeners(modBus);
        CnCleanseCreativeTabs.register();
        CnCleanseBlocks.register();
        CnCleanseItems.register();
        CnCleanseFluids.register();
        CnCleanseRegistrateTags.register();
        CnCleanseLang.register();

        modBus.addListener(
                EventPriority.LOWEST,
                CreativeTabInjections::onBuildCreativeTabContents
        );
        modBus.addListener(this::onCommonSetup);
        modBus.addListener(this::onClientSetup);
    }

    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(ID, path);
    }

    public static ResourceLocation spongeBlockTexture(boolean wet, String name) {
        return asResource((wet ? "block/sponge/wet/" : "block/sponge/dry/") + name);
    }
    public static ResourceLocation spongeItemTexture(boolean wet, String name) {
        return asResource((wet ? "item/sponge/wet/" : "item/sponge/dry/") + name);
    }
    public static ResourceLocation bottleTexture(String fluidName) {
        return asResource("item/beaker/" + fluidName + "_bottle");
    }

    private void onCommonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("Common setup...");
    }

    private void onClientSetup(FMLClientSetupEvent event) {
        LOGGER.info("Client setup...");
    }
}
