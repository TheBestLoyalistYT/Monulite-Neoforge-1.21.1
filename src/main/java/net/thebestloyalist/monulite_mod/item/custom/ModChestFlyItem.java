package net.thebestloyalist.monulite_mod.item.custom;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.thebestloyalist.monulite_mod.client.KeyBinds;
import net.thebestloyalist.monulite_mod.effect.ModEffects;
import net.thebestloyalist.monulite_mod.item.ModArmorMaterials;

import java.util.List;
import java.util.Map;

public class ModChestFlyItem extends ArmorItem {
    private static final Map<Holder<ArmorMaterial>, List<MobEffectInstance>> MATERIAL_TO_EFFECT_MAP =
            (new ImmutableMap.Builder<Holder<ArmorMaterial>, List<MobEffectInstance>>())
                    .put(ModArmorMaterials.CLOUD_BOOT_MATERIAL,
                            List.of(new MobEffectInstance(ModEffects.FLOAT_EFFECT, 21, 0, false, false)))
                    .build();

    private int tickCounter = 0;
    private static final double FLY_STRENGTH = 0.5;

    public ModChestFlyItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {

        if (entity instanceof Player player && !level.isClientSide()) {
            if (hasPlayerCorrectArmorOn(ModArmorMaterials.CLOUD_BOOT_MATERIAL, player)) {

                onFly(player, level);
                }
        }
    }

    private void onFly(Player player, Level level) {
        Vec3 look = player.getLookAngle();
        Vec3 velocity = look.scale(FLY_STRENGTH);

        tickCounter++;

        if (KeyBinds.MON_KEY_1.isDown()) {

            player.setDeltaMovement(velocity);
            player.hurtMarked = true;

            player.fallDistance = 0;

            // Sound
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.BLASTFURNACE_FIRE_CRACKLE, SoundSource.PLAYERS, 1.0f, 1.0f);

            // Damage enemies in path (handled immediately or via tick? Instant is easier)
            // Check bounding box along path
            AABB pathBox = player.getBoundingBox().expandTowards(velocity.scale(1)).inflate(2.0);

            if (level instanceof ServerLevel serverLevel) {
                for (int i = 0; i < 10; i++) {
                    double d = i / 10.0;
                    serverLevel.sendParticles(ParticleTypes.SMOKE,
                            player.getX(),
                            player.getY() + 1.3,
                            player.getZ(),
                            5, 0.1, 0.1, 0.1, 0.05);
                    if (tickCounter % 5 == 0) {
                        serverLevel.sendParticles(ParticleTypes.SMALL_FLAME,
                                player.getX(),
                                player.getY() + 1.3,
                                player.getZ(),
                                2, 0.2, 0.2, 0.2, 0.01);
                        if (tickCounter >= 20) {
                            ItemStack chestpate = player.getItemBySlot(EquipmentSlot.CHEST);
                            tickCounter = 0;

                            double random = Math.random();

                            if (!chestpate.isEmpty()) {
                                if (random >= 0.64) {
                                    chestpate.hurtAndBreak(1, player, EquipmentSlot.CHEST);
                                }
                            }
                        }
                    }
                }
            }
        }

    }


    private boolean hasPlayerCorrectArmorOn(Holder<ArmorMaterial> material, Player player) {

        if (this.type != Type.CHESTPLATE) {
            return false;
        }

        ItemStack chestStack = player.getInventory().getArmor(2);

        return chestStack.getItem() == this;
    }

}