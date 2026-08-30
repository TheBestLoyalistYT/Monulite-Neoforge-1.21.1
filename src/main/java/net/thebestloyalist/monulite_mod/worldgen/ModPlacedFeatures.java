package net.thebestloyalist.monulite_mod.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.thebestloyalist.monulite_mod.MonuliteMod;
import net.thebestloyalist.monulite_mod.block.ModBlocks;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> MONULITE_ORE_PLACED_KEY = registerKey("monulite_ore_placed");
    public static final ResourceKey<PlacedFeature> MONULITEDIM_ORE_PLACED_KEY = registerKey("monulitedim_ore_placed");
    public static final ResourceKey<PlacedFeature> ACROTE_ORE_PLACED_KEY = registerKey("acrote_ore_placed");

    public static final ResourceKey<PlacedFeature> LITETREE_PLACED_KEY = registerKey("litetree_placed");
    public static final ResourceKey<PlacedFeature> LITETREE_GRHZ_PLACED_KEY = registerKey("litetree_grhz_placed");

    public static final ResourceKey<PlacedFeature> CRYSL_LQD_PLACED_KEY = registerKey("crystal_liquid_placed_key");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, MONULITE_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.MONULITE_ORE_KEY),
                ModOrePlacement.commonOrePlacement(4, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(10))));

        register(context, MONULITEDIM_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.MONULITEDIM_ORE_KEY),
                ModOrePlacement.commonOrePlacement(8, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(90))));


        register(context, ACROTE_ORE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.ACROTE_ORE_KEY),
                ModOrePlacement.commonOrePlacement(7, HeightRangePlacement.uniform(VerticalAnchor.absolute(1), VerticalAnchor.absolute(87))));

        register(context, LITETREE_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.LITEWOOD_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(8, 0.25f, 2),
                        ModBlocks.LITEWOOD_SAPLING.get()));

        register(context, LITETREE_GRHZ_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.LITEWOOD_GRHZ_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(1, 0.25f, 2),
                        ModBlocks.LITEWOOD_SAPLING.get()));

        register(context, CRYSL_LQD_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.CRYST_LQD_KEY),
                List.of(CountPlacement.of(30),
                        InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(
                                VerticalAnchor.absolute(55),
                                VerticalAnchor.absolute(170)
                        )));
    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(MonuliteMod.MOD_ID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}