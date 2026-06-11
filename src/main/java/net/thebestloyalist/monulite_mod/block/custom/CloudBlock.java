package net.thebestloyalist.monulite_mod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class CloudBlock extends Block {

    private int delayedTick = 0;

    public CloudBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        level.scheduleTick(pos, this, 1);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random){
        level.scheduleTick(pos, this, 1);
        delayedTick++;
        if(delayedTick >= 190){
            level.removeBlock(pos, false);

            delayedTick = 0;
        }
    }
}
