package net.thebestloyalist.monulite_mod;

import com.google.common.collect.Lists;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.thebestloyalist.monulite_mod.worldgen.tree.ModTrunkPlacers;

import java.util.List;
import java.util.OptionalInt;
import java.util.function.BiConsumer;

public class custTreeTrunkPlacer extends TrunkPlacer {
    public static final MapCodec<custTreeTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec((p_70161_) -> trunkPlacerParts(p_70161_).apply(p_70161_, custTreeTrunkPlacer::new));

    public custTreeTrunkPlacer(int baseHeight, int heightRandA, int heightRandB) {
        super(baseHeight, heightRandA, heightRandB);
    }

    protected TrunkPlacerType<?> type() {
        return ModTrunkPlacers.LITETREE_TRUNK_PLACER.get();
    }

    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, int freeTreeHeight, BlockPos pos, TreeConfiguration config) {
        setDirtAt(level, blockSetter, random, pos.below(), config);
        List<FoliagePlacer.FoliageAttachment> list = Lists.newArrayList();
        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);

        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        int k = pos.getX();
        int l = pos.getZ();
        OptionalInt optionalint = OptionalInt.empty();

        for(int i1 = 0; i1 < freeTreeHeight; ++i1) {
            int j1 = pos.getY() + i1;

            if (this.placeLog(level, blockSetter, random, blockpos$mutableblockpos.set(k, j1, l), config)) {
                optionalint = OptionalInt.of(j1 + 1);
            }
        }

        if (optionalint.isPresent()) {
            list.add(new FoliagePlacer.FoliageAttachment(new BlockPos(k, optionalint.getAsInt(), l), 0, false));
        }

        int mainTopX = k;
        int mainTopZ = l;
        int mainTopY = optionalint.orElse(pos.getY() + freeTreeHeight);

        int extensionCount = 4 - random.nextInt(4);

        for (int extension = 0; extension < extensionCount; extension++) {

            // Pick a different direction for each extension
            Direction extensionDirection = switch (extension) {
                case 0 -> Direction.NORTH;
                case 1 -> Direction.WEST;
                case 2 -> Direction.EAST;
                default -> Direction.SOUTH;
            };

            // Move ONE block away from the main trunk
            mainTopX += extensionDirection.getStepX();
            mainTopZ += extensionDirection.getStepZ();

            // Each extension gets its own random height
            int extensionHeight = 1 + random.nextInt(3);

            OptionalInt extensionTop = OptionalInt.empty();

            for (int y = 0; y < extensionHeight; y++) {

                int extensionY = mainTopY + y;

                if (this.placeLog(
                        level,
                        blockSetter,
                        random,
                        blockpos$mutableblockpos.set(
                                mainTopX,
                                extensionY,
                                mainTopZ
                        ),
                        config
                )) {
                    extensionTop = OptionalInt.of(extensionY + 1);
                }
            }

            if (extensionTop.isPresent()) {
                list.add(new FoliagePlacer.FoliageAttachment(
                        new BlockPos(
                                mainTopX,
                                extensionTop.getAsInt(),
                                mainTopZ
                        ),
                        0,
                        false
                ));
            }
        }

        return list;
    }
}