package net.thebestloyalist.monulite_mod.item.custom;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.thebestloyalist.monulite_mod.block.ModBlocks;
import net.thebestloyalist.monulite_mod.effect.ModEffects;
import net.thebestloyalist.monulite_mod.item.ModArmorMaterials;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Map;

public class ModArmorItem extends ArmorItem {
    private static final Map<Holder<ArmorMaterial>, List<MobEffectInstance>> MATERIAL_TO_EFFECT_MAP =
            (new ImmutableMap.Builder<Holder<ArmorMaterial>, List<MobEffectInstance>>())
                    .put(ModArmorMaterials.CLOUD_BOOT_MATERIAL,
                            List.of(new MobEffectInstance(ModEffects.FLOAT_EFFECT, 21, 0, false, false)))
                    .build();

    public ModArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {

        if (entity instanceof Player player && !level.isClientSide()) {
            if (hasPlayerCorrectArmorOn(ModArmorMaterials.CLOUD_BOOT_MATERIAL, player)) {
                evaluateArmorEffects(player);

                if (player.isCrouching()) {
                    BlockPos center = player.blockPosition().below();

                    for (int x = -1; x <= 1; x++)
                        for (int z = -1; z <= 1; z++) {

                            BlockPos pos = center.offset(x, 0, z);

                            if (level.isEmptyBlock(pos)) {

                                level.setBlockAndUpdate(pos, ModBlocks.CLOUD_BLOCK.get().defaultBlockState());
                            }
                        }
                    }
                }
        }
        if (entity instanceof Player player && level.isClientSide()) {
            if (hasPlayerCorrectArmorOn(ModArmorMaterials.CLOUD_BOOT_MATERIAL, player)) {
                evaluateArmorEffects(player);

                if (player.isCrouching()) {
                    BlockPos center = player.blockPosition().below();

                    for (int x = 0; x <= 0; x++)
                        for (int z = 0; z <= 0; z++) {

                            BlockPos pos = center.offset(x, 0, z);

                            if (level.isEmptyBlock(pos)) {

                                level.setBlockAndUpdate(pos, ModBlocks.CLOUD_BLOCK.get().defaultBlockState());
                            }
                        }
                    }
                }
        }
    }

    private void evaluateArmorEffects(Player player) {
        for(Map.Entry<Holder<ArmorMaterial>, List<MobEffectInstance>> entry : MATERIAL_TO_EFFECT_MAP.entrySet()) {
            Holder<ArmorMaterial> mapArmorMaterial = entry.getKey();
            List<MobEffectInstance> mapEffect = entry.getValue();

            if(hasPlayerCorrectArmorOn(mapArmorMaterial, player)) {
                addEffectToPlayer(player, mapEffect);
            }
        }
    }

    private void addEffectToPlayer(Player player, List<MobEffectInstance> mapEffect) {
        boolean hasPlayerEffect = mapEffect.stream().allMatch(effect -> player.hasEffect(effect.getEffect()));

        if(!hasPlayerEffect) {
            for (MobEffectInstance effect : mapEffect) {
                player.addEffect(new MobEffectInstance(effect.getEffect(),
                        effect.getDuration(), effect.getAmplifier(), effect.isAmbient(), effect.isVisible()));
            }
        }
    }

    private boolean hasPlayerCorrectArmorOn(Holder<ArmorMaterial> material, Player player) {

        if (this.type != Type.BOOTS) {
            return false;
        }

        ItemStack bootsStack = player.getInventory().getArmor(0);

        return bootsStack.getItem() == this;
    }

}