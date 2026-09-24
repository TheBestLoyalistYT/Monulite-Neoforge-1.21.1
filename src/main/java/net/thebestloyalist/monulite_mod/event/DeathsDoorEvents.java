package net.thebestloyalist.monulite_mod.event;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.thebestloyalist.monulite_mod.MonuliteMod;

@EventBusSubscriber(modid = MonuliteMod.MOD_ID)
public class DeathsDoorEvents {

    public static final ResourceKey<Level> DEATHS_DOOR_DIMENSION =
            ResourceKey.create(
                    Registries.DIMENSION,
                    ResourceLocation.fromNamespaceAndPath("monulite_mod", "deaths_door_dim")
            );

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide) { return; }

        if (player.level().dimension() == DEATHS_DOOR_DIMENSION) {
            ServerLevel serverLevel = (ServerLevel) player.level();

            if (serverLevel.getGameTime() % 20 == 0) {
                serverLevel.sendParticles(ParticleTypes.ASH,
                        player.getX(),
                        player.getY(),
                        player.getZ(),
                        64, 15, 15, 15, 0.01);

                serverLevel.sendParticles(ParticleTypes.END_ROD,
                        player.getX(),
                        player.getY(),
                        player.getZ(),
                        5, 18, 18, 18, 0.01);

                serverLevel.sendParticles(ParticleTypes.PORTAL,
                        player.getX(),
                        player.getY(),
                        player.getZ(),
                        15, 17, 17, 17, 0.01);
            }
        }
    }
}
