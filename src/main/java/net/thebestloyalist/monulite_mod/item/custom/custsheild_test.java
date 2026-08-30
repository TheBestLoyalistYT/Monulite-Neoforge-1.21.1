package net.thebestloyalist.monulite_mod.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.thebestloyalist.monulite_mod.MonuliteMod;
import net.thebestloyalist.monulite_mod.item.ModItems;
import org.apache.logging.log4j.core.jmx.Server;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@EventBusSubscriber(modid = MonuliteMod.MOD_ID)
public class custsheild_test extends ShieldItem {
    public custsheild_test(Properties properties) {super(properties);}

    private static final Map<Entity, Custsheild_data> custSldt = new HashMap<>();

    private static class Custsheild_data {
        Entity attacker;

        public Custsheild_data(Entity attacker) {
            this.attacker = attacker;
        }
    }

    @SubscribeEvent
    public static void onBlock(LivingShieldBlockEvent event) {
        LivingEntity entity = event.getEntity();
        Entity attacker = event.getDamageSource().getEntity();

        if (entity.isBlocking()) {
            custSldt.put(attacker, new Custsheild_data(attacker));
        }
    }
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 100;
    }


    @SubscribeEvent
    public static void onPunch(PlayerInteractEvent.LeftClickEmpty event) {
        Player player = event.getEntity();
        Vec3 look = player.getLookAngle();
        Vec3 velocity = look.scale(1.6);
        for (Custsheild_data data : custSldt.values()) {
            Entity attacker = data.attacker;

            if (attacker != null) {
                attacker.setDeltaMovement(velocity.x * 1.3, velocity.y * 1.4, velocity.z * 1.3);
            }
        }
        custSldt.clear();
    }

    @SubscribeEvent
    public static void onLevelTick(LevelTickEvent.Post event) {
        Level level = event.getLevel();

        for (Custsheild_data data : custSldt.values()) {
            Entity attacker = data.attacker;
            if (attacker != null && level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.SCULK_CHARGE_POP,
                        attacker.getX(), attacker.getY() + 1, attacker.getZ(), 2, 0.2, 0.2, 0.2, 0.50);
                serverLevel.sendParticles(ParticleTypes.FIREWORK,
                        attacker.getX(), attacker.getY() + 1, attacker.getZ(), 1, 0.2, 0.2, 0.2, 0.15);
            }
        }
    }
}
