package net.thebestloyalist.monulite_mod.item.custom;

import com.mojang.datafixers.kinds.IdF;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.thebestloyalist.monulite_mod.item.ModItems;

import java.util.*;

public class TrelgnackItem extends SwordItem {
    private int DURATION = 200;
    private int COOLDWN = 400;
    public double rando = 0.0;
    public TrelgnackItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    private final Map<UUID, TrelgnackData> trelData = new HashMap<>();

    private static class TrelgnackData {
        BlockPos oldPos;
        LivingEntity targer;
        long endTime;
        long coolTime;
        int ACTIVE;

        public TrelgnackData(BlockPos oldPos, LivingEntity targer, long endTime,long coolTime , int ACTIVE) {
            this.oldPos = oldPos;
            this.targer = targer;
            this.endTime = endTime;
            this.coolTime = coolTime;
            this.ACTIVE = ACTIVE;
        }
    }

    @SubscribeEvent
    public void onLevelTick(LevelTickEvent.Pre event) {
        Level level = event.getLevel();
        if (level.isClientSide) return;

        for (Map.Entry<UUID, TrelgnackData> entry : new HashMap<>(trelData).entrySet()) {
            UUID id = entry.getKey();
            TrelgnackData data = entry.getValue();

            data.targer.setNoGravity(false);

            if (data != null) {
                data.targer.fallDistance = 0;

                if (data.ACTIVE == 0) {
                    data.endTime = level.getGameTime() - 5;
                }

                if (level.getGameTime() < data.endTime) {
                    data.targer.setDeltaMovement(0, 0, 0);
                    data.targer.hurtMarked = true;

                    //Particles! AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAH. Sorry, got scared
                    if (data.targer instanceof Player) {
                        data.targer.setNoGravity(true);
                        if (level instanceof ServerLevel serverLevel) {
                            if (data.ACTIVE == 1) {
                                Vec3 hitPos = data.targer.position();
                                serverLevel.sendParticles(ParticleTypes.SCULK_CHARGE_POP,
                                        hitPos.x, hitPos.y + 1, hitPos.z, 15, 0.2, 0.2, 0.2, 0.50);
                                serverLevel.sendParticles(ParticleTypes.FIREWORK,
                                        hitPos.x, hitPos.y + 1, hitPos.z, 1, 0.2, 0.2, 0.2, 0.15);
                            }
                        }
                    }else {
                        if (level instanceof ServerLevel serverLevel) {
                            if (data.ACTIVE == 1) {
                                Vec3 hitPos = data.targer.position();
                                serverLevel.sendParticles(ParticleTypes.SCULK_CHARGE_POP,
                                        hitPos.x, hitPos.y + 1, hitPos.z, 15, 0.2, 0.2, 0.2, 0.50);
                                serverLevel.sendParticles(ParticleTypes.FIREWORK,
                                        hitPos.x, hitPos.y + 1, hitPos.z, 1, 0.2, 0.2, 0.2, 0.15);
                            }
                        }
                    }
                }else if (data.ACTIVE == 1) {
                    System.out.println("got Act 1");
                    if (level.getGameTime() >= data.endTime) {
                        if (data.targer instanceof ServerPlayer serverPlayer) {
                            serverPlayer.connection.teleport(data.oldPos.getX(), data.oldPos.getY(), data.oldPos.getZ(), serverPlayer.getYRot(), serverPlayer.getXRot());
                            data.targer.setNoGravity(false);
                            trelData.remove(id);
                        }else {
                            data.targer.teleportTo(data.oldPos.getX(), data.oldPos.getY(), data.oldPos.getZ());
                            data.targer.setNoGravity(false);
                            trelData.remove(id);
                        }
                    }
                }else if (level.getGameTime() >= data.coolTime) {
                    System.out.println("cooltime");
                    data.targer.setNoGravity(false);
                    trelData.remove(id);
                }
            }
            if (!data.targer.isAlive()) {
                data.targer.setNoGravity(false);
                trelData.remove(id);
                return;
            }
        }
    }

    @SubscribeEvent
    public void onServerClose(LevelEvent.Unload event) {
        for (TrelgnackData trelgnackData : trelData.values()) {
            LivingEntity targer = trelgnackData.targer;

            targer.setNoGravity(false);
        }
        trelData.clear();
    }

    @SubscribeEvent
    public void onAttackerDeath(LivingDeathEvent event) {
        UUID id = event.getEntity().getUUID();
        TrelgnackData data = trelData.get(id);
        if (data != null) {
            data.targer.setNoGravity(false);
            trelData.remove(id);
        }
    }

    @SubscribeEvent
    public void onPlayerAttack(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker) {
            Level level = attacker.level();
            UUID id = attacker.getUUID();
            TrelgnackData data = trelData.get(id);

            LivingEntity targer = event.getEntity();
            BlockPos oldPos = targer.blockPosition();

            if (data == null) {
                if (event.getSource().isDirect()) {
                    if (attacker.getMainHandItem().is(ModItems.TRELGNACK)) {
                        trelData.put(attacker.getUUID(), new TrelgnackData(oldPos, targer ,level.getGameTime() + DURATION,
                                level.getGameTime() + COOLDWN,1));

                        rando = Math.random() * 2 - 1;

                        Vec3 floatingPos = new Vec3(
                                oldPos.getX() + rando * 3.5,
                                oldPos.getY() + 10,
                                oldPos.getZ() + rando * 3.5
                        );

                        targer.teleportTo(floatingPos.x, floatingPos.y, floatingPos.z);
                        targer.hurtMarked = true;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.RightClickBlock event) {
        Player player = event.getEntity();
        Level level = player.level();
        UUID id = player.getUUID();
        TrelgnackData data = trelData.get(id);

        if (data != null) {
            if (player.getMainHandItem().is(ModItems.TRELGNACK)) {
                if (player.isCrouching()) {
                    data.targer.teleportTo(data.oldPos.getX(), data.oldPos.getY(), data.oldPos.getZ());
                    data.targer.hurtMarked = true;
                    data.targer.setNoGravity(false);
                    System.out.println("right crouch");
                }else {
                    double reach = 6.0;

                    HitResult hit = player.pick(reach, 0, false);

                    if (hit.getType() == HitResult.Type.BLOCK) {
                        BlockHitResult blockHitResult = (BlockHitResult) hit;

                        BlockPos blockPos = blockHitResult.getBlockPos();

                        data.targer.teleportTo(blockPos.getX(), blockPos.getY() + 1, blockPos.getZ());
                        data.targer.setNoGravity(false);
                        data.targer.hurtMarked = true;
                        System.out.println("right");
                    }
                }
            }
            data.ACTIVE = 0;
        }
    }

    @SubscribeEvent
    public void onRightCrouchClick(PlayerInteractEvent.RightClickEmpty event) {
        Player player = event.getEntity();
        Level level = player.level();
        UUID id = player.getUUID();
        TrelgnackData data = trelData.get(id);

        if (data != null) {
            if (player.getMainHandItem().is(ModItems.TRELGNACK)) {
                if (player.isCrouching()) {
                    data.targer.teleportTo(data.oldPos.getX(), data.oldPos.getY(), data.oldPos.getZ());
                    data.targer.hurtMarked = true;
                    data.targer.setNoGravity(false);
                    System.out.println("empt crouch");
                }
            }
            data.ACTIVE = 0;
        }
    }
}