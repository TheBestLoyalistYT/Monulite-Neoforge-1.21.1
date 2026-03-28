package net.thebestloyalist.monulite_mod.entity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.thebestloyalist.monulite_mod.MonuliteMod;
import net.thebestloyalist.monulite_mod.entity.custom.HiveEntity;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, MonuliteMod.MOD_ID);

    public static final Supplier<EntityType<HiveEntity>> HIVE =
            ENTITY_TYPES.register("hive", () -> EntityType.Builder.of(HiveEntity::new, MobCategory.MONSTER)
                    .sized(0.7f, 2.0f).build("hive"));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}