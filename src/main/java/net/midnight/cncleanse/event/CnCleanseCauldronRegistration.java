package net.midnight.cncleanse.event;

import net.midnight.cncleanse.CnCleanse;
import net.midnight.cncleanse.content.sponge.item.SpongeItemColor;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@EventBusSubscriber(modid = CnCleanse.ID)
public final class CnCleanseCauldronRegistration {
    private CnCleanseCauldronRegistration() {}

    @SubscribeEvent
    public static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            for (SpongeItemColor color : SpongeItemColor.values()) {
                var wet = color.wet().get();
                var dry = color.dry().get();

                CauldronInteraction.EMPTY.map().put(wet, CnCleanseCauldronInteractions.deposit(color));
                CauldronInteraction.WATER.map().put(wet, CnCleanseCauldronInteractions.deposit(color));

                CauldronInteraction.WATER.map().put(dry, CnCleanseCauldronInteractions.take(color));
            }
        });
    }
}