package net.thebestloyalist.monulite_mod.enchantment.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraft.world.phys.Vec3;

public record DoubleJumpEnchantmentEffect() implements EnchantmentEntityEffect {
    public static final MapCodec<DoubleJumpEnchantmentEffect> CODEC = MapCodec.unit(DoubleJumpEnchantmentEffect::new);

    @Override
    public void apply(ServerLevel serverLevel, int enchantmentLvl, EnchantedItemInUse enchantedItemInUse, Entity entity, Vec3 vec3) {

    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
