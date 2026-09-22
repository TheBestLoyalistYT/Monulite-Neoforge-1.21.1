package net.thebestloyalist.monulite_mod.item.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.thebestloyalist.monulite_mod.MonuliteMod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@EventBusSubscriber(modid = MonuliteMod.MOD_ID)
public class custsheild_test extends ShieldItem {
    public custsheild_test(Properties properties) {super(properties);}

    public static final Map<UUID, Custsheild_data> custSldt = new HashMap<>();

    public static class Custsheild_data {
        public Entity attacker;
        public LivingEntity blocker;

        public Custsheild_data(Entity attacker, LivingEntity blocker) {
            this.attacker = attacker;
            this.blocker = blocker;
        }
    }

    @SubscribeEvent
    public static void onBlock(LivingShieldBlockEvent event) {
        LivingEntity entity = event.getEntity();
        Entity attacker = event.getDamageSource().getEntity();

        if (entity instanceof Player player) {
            if (entity.getItemBySlot(EquipmentSlot.OFFHAND).getItem() instanceof custsheild_test ||
                    entity.getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof custsheild_test) {
                if (entity.isBlocking()) {
                    custSldt.put(entity.getUUID(), new Custsheild_data(attacker, entity));
                }
            }
        }
    }

    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 100;
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
