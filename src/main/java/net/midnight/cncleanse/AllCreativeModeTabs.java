package net.midnight.cncleanse;

import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class AllCreativeModeTabs {

    public static final RegistryEntry<CreativeModeTab, CreativeModeTab> MAIN_TAB =
            CnCleanse.REGISTRATE.defaultCreativeTab("main_tab", builder ->
                    builder
                            .title(Component.literal("Clean n' Cleanse"))
                            .icon(() -> new ItemStack(AllItems.YELLOW_SPONGE.get()))
                            .build()
            ).register();

    public static void register() {
        CnCleanse.REGISTRATE.setCreativeTab(MAIN_TAB);
        CnCleanse.LOGGER.info("Register Creative Tab...");
    }
}
