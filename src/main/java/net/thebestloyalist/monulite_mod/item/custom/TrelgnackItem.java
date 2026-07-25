package net.thebestloyalist.monulite_mod.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

import java.util.*;

public class TrelgnackItem extends SwordItem {
    private LivingEntity targer = null;
    public int ACTIVE = 0;
    public int tickCountup = 0;
    public double rando = 0.0;
    private Vec3 oldTarPos = null;
    public TrelgnackItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @SubscribeEvent
    public void onLevelTick(LevelTickEvent.Pre event) {
        if (ACTIVE == 1 && targer != null) {
            targer.sendSystemMessage(Component.literal("Ticking target! count: " + tickCountup));
            tickCountup++;
            targer.fallDistance = 0;
            if (tickCountup <= 1199) {
                targer.setDeltaMovement(0, 0, 0);
                targer.hurtMarked = true;
                if (targer instanceof Player) {
                    targer.setNoGravity(true);
                }
            }else if (tickCountup >= 1200) {
                if (targer instanceof ServerPlayer serverPlayer) {
                    serverPlayer.connection.teleport(oldTarPos.x, oldTarPos.y, oldTarPos.z, serverPlayer.getYRot(), serverPlayer.getXRot());
                    targer.setNoGravity(false);
                } else {
                    targer.teleportTo(oldTarPos.x, oldTarPos.y, oldTarPos.z);
                    targer.setNoGravity(false);
                }
                tickCountup = 0;
                ACTIVE = 0;
            }
        }
    }

    @SubscribeEvent
    public void onPlayerAttack(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker) {

            if (attacker.getMainHandItem().getItem() instanceof TrelgnackItem) {
                if (ACTIVE == 0) {
                    ACTIVE = 1;

                    targer = event.getEntity();

                    oldTarPos = targer.position();

                    Vec3 playersPos = targer.position();

                    rando = Math.random() * 2 - 1;

                    Vec3 floatingPos = new Vec3(
                            playersPos.x + rando * 3.5,
                            playersPos.y + 10,
                            playersPos.z + rando * 3.5
                    );
                    targer.teleportTo(floatingPos.x, floatingPos.y, floatingPos.z);
                    targer.hurtMarked = true;
                }
            }
        }
    }
}