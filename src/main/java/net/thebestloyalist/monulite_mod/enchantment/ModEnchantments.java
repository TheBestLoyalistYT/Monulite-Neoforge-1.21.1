package net.thebestloyalist.monulite_mod.enchantment;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentTarget;
import net.thebestloyalist.monulite_mod.MonuliteMod;
import net.thebestloyalist.monulite_mod.enchantment.custom.DoubleJumpEnchantmentEffect;

public class ModEnchantments {
    public static final ResourceKey<Enchantment> DOUBLE_JUMP = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath(MonuliteMod.MOD_ID, "double_jump"));

    public static void bootstrap(BootstrapContext<Enchantment> context) {
        var enchantments = context.lookup(Registries.ENCHANTMENT);
        var items = context.lookup(Registries.ITEM);

        register(context, DOUBLE_JUMP, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
                5,
                1,
                Enchantment.dynamicCost(5, 7),
                Enchantment.dynamicCost(25, 7),
                2,
                EquipmentSlotGroup.FEET))
                .exclusiveWith(enchantments.getOrThrow(EnchantmentTags.BOOTS_EXCLUSIVE))
                .withEffect(EnchantmentEffectComponents.TICK, new DoubleJumpEnchantmentEffect()));
    }


    private static void register(BootstrapContext<Enchantment> registry, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
        registry.register(key, builder.build(key.location()));
    }
}
