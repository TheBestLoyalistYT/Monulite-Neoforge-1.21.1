package net.thebestloyalist.monulite_mod.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.thebestloyalist.monulite_mod.MonuliteMod;
import net.thebestloyalist.monulite_mod.entity.ModEntities;
import net.thebestloyalist.monulite_mod.entity.client.HiveModel;
import net.thebestloyalist.monulite_mod.entity.custom.HiveEntity;

@EventBusSubscriber(modid = MonuliteMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(HiveModel.LAYER_LOCATION, HiveModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.HIVE.get(), HiveEntity.createAttributes().build());
    }
}