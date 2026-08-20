package net.thebestloyalist.monulite_mod.worldgen.tree;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.thebestloyalist.monulite_mod.MonuliteMod;
import net.thebestloyalist.monulite_mod.custTreeTrunkPlacer;

import java.util.function.Supplier;

public class ModTrunkPlacers {

    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACERS =
            DeferredRegister.create(BuiltInRegistries.TRUNK_PLACER_TYPE, MonuliteMod.MOD_ID);

    public static final Supplier<TrunkPlacerType<custTreeTrunkPlacer>> LITETREE_TRUNK_PLACER =
            TRUNK_PLACERS.register(
                    "litetree_trunk_placer",
                    () -> new TrunkPlacerType<>(custTreeTrunkPlacer.CODEC)
            );

    public static void register(IEventBus eventBus) {
        TRUNK_PLACERS.register(eventBus);
    }
}