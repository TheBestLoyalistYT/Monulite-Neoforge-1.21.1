package net.thebestloyalist.monulite_mod.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;


public class DeathsDoorPortalBlock extends Block {

    public DeathsDoorPortalBlock(Properties properties){
        super(properties);
    }

    @Override
    protected void entityInside(
            BlockState state,
            Level level,
            BlockPos pos,
            Entity entity
    ) {
        if (!level.isClientSide) {
            if (entity instanceof ServerPlayer player) {

                ServerLevel overworld = player.server.overworld();

                player.teleportTo(overworld,
                        player.getX(),
                        256,
                        player.getZ(),
                        player.getYRot(), player.getXRot());

                player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 460, 0,false, true));
            }
        }
    }
}
