package net.thebestloyalist.monulite_mod.worldgen;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SpringConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BushFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.RandomSpreadFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.thebestloyalist.monulite_mod.MonuliteMod;
import net.thebestloyalist.monulite_mod.block.ModBlocks;
import net.thebestloyalist.monulite_mod.custTreeTrunkPlacer;

import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> MONULITE_ORE_KEY = registerKey("monulite_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MONULITEDIM_ORE_KEY = registerKey("monulitedim_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ACROTE_ORE_KEY = registerKey("acrote_ore");

    public static final ResourceKey<ConfiguredFeature<?, ?>> LITEWOOD_KEY = registerKey("litewood");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LITEWOOD_GRHZ_KEY = registerKey("litewood_grhz");

    public static final ResourceKey<ConfiguredFeature<?, ?>> CRYST_LQD_KEY = registerKey("crystal_liquid_key");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        List<OreConfiguration.TargetBlockState> overworldAcroteOres = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.ACROTE_ORE.get().defaultBlockState()));

        List<OreConfiguration.TargetBlockState> overworldMonuliteOres = List.of(
                OreConfiguration.target(stoneReplaceables, ModBlocks.MONULITE_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.DEEPSLATE_MONULITE_ORE.get().defaultBlockState()));

        List<OreConfiguration.TargetBlockState> dimMonuliteOres = List.of(
                OreConfiguration.target(deepslateReplaceables, ModBlocks.DEEPSLATE_MONULITE_ORE.get().defaultBlockState()));

        register(context, MONULITEDIM_ORE_KEY, Feature.ORE, new OreConfiguration(dimMonuliteOres, 4));

        register(context, MONULITE_ORE_KEY, Feature.ORE, new OreConfiguration(overworldMonuliteOres, 3));

        register(context, ACROTE_ORE_KEY, Feature.ORE, new OreConfiguration(overworldAcroteOres, 5));

        register(context, LITEWOOD_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.LITE_LOG.get()),
                new custTreeTrunkPlacer(2, 1, 3),

                BlockStateProvider.simple(ModBlocks.LITE_LEAVES.get()),
                new RandomSpreadFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), ConstantInt.of(2), 38),

                new TwoLayersFeatureSize(1, 1, 2)).build());


        register(context, LITEWOOD_GRHZ_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.LITE_LOG.get()),
                new custTreeTrunkPlacer(2, 1, 3),

                BlockStateProvider.simple(ModBlocks.LITE_LEAVES.get()),
                new RandomSpreadFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), ConstantInt.of(2), 38),

                new TwoLayersFeatureSize(1, 1, 2)).build());

        HolderSet<Block> cryst_lqd_blocks = HolderSet.direct(
                BuiltInRegistries.BLOCK.getHolderOrThrow(Blocks.DEEPSLATE.builtInRegistryHolder().key()),
                BuiltInRegistries.BLOCK.getHolderOrThrow(Blocks.NETHERRACK.builtInRegistryHolder().key()));

        register(context, CRYST_LQD_KEY, Feature.SPRING, new SpringConfiguration(Fluids.WATER.defaultFluidState(),
                false,
                2,
                1,
                cryst_lqd_blocks
                ));

    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(MonuliteMod.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}