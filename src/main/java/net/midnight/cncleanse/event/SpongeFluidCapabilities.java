package net.midnight.cncleanse.event;

import net.midnight.cncleanse.CnCleanse;
import net.midnight.cncleanse.content.sponge.item.DrySpongeFluidHandler;
import net.midnight.cncleanse.content.sponge.item.SpongeItemColor;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@EventBusSubscriber(modid = CnCleanse.ID)
public class SpongeFluidCapabilities {

    @SubscribeEvent
    public static void register(RegisterCapabilitiesEvent event) {
        for (SpongeItemColor color : SpongeItemColor.values()) {
            event.registerItem(
                    Capabilities.FluidHandler.ITEM,
                    (stack, ctx) -> new DrySpongeFluidHandler(stack, color),
                    color.dry().get()
            );
        }
    }
}