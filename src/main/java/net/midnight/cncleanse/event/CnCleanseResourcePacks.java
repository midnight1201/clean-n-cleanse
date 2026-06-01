package net.midnight.cncleanse.event;

import net.midnight.cncleanse.CnCleanse;
import net.midnight.cncleanse.data.CnCleanseLang;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddPackFindersEvent;

@EventBusSubscriber(modid = CnCleanse.ID, value = Dist.CLIENT)
public class CnCleanseResourcePacks {

    @SubscribeEvent
    public static void addPackFinders(AddPackFindersEvent event) {
        event.addPackFinders(
                CnCleanse.asResource("resourcepacks/nomenclature"),
                PackType.CLIENT_RESOURCES,
                Component.translatable(CnCleanseLang.Keys.ResourcePacks.NOMENCLATURE),
                PackSource.BUILT_IN,
                false,
                Pack.Position.TOP
        );
    }
}