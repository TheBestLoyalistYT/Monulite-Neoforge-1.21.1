package net.thebestloyalist.monulite_mod.block.entity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.thebestloyalist.monulite_mod.MonuliteMod;
import net.thebestloyalist.monulite_mod.block.ModBlocks;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, MonuliteMod.MOD_ID);


    public static final Supplier<BlockEntityType<MonuliteCoinMolderEntity>> GROWTH_CHAMBER_BE =
            BLOCK_ENTITIES.register("growth_chamber_be", () -> BlockEntityType.Builder.of(
                    MonuliteCoinMolderEntity::new, ModBlocks.MONULITE_COIN_MOLDER.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}