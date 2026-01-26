package net.thebestloyalist.monulite_mod.item;

import net.thebestloyalist.monulite_mod.util.ModTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public class ModToolTeirs {
    public static final Tier MONULITE_SWORD = new SimpleTier(ModTags.Blocks.INCORRECT_FOR_MONULITE_TOOL,
            2721, 1.2f, 3.5f, 20, () -> Ingredient.of(ModItems.MONULITE_INGOT));

    public static final Tier MONULITE_PICKAXE = new SimpleTier(ModTags.Blocks.INCORRECT_FOR_MONULITE_TOOL,
            2731, 40.0F, 3.0f, 20, () -> Ingredient.of(ModItems.MONULITE_INGOT));

    public static final Tier MONULITE_SHOVEL = new SimpleTier(ModTags.Blocks.INCORRECT_FOR_MONULITE_TOOL,
            2721, 40.0F, 3.5f, 20, () -> Ingredient.of(ModItems.MONULITE_INGOT));

    public static final Tier MONULITE_AXE = new SimpleTier(ModTags.Blocks.INCORRECT_FOR_MONULITE_TOOL,
            2731, 40.0F, 3.5f, 20, () -> Ingredient.of(ModItems.MONULITE_INGOT));

    public static final Tier MONULITE_HOE = new SimpleTier(ModTags.Blocks.INCORRECT_FOR_MONULITE_TOOL,
            2721, 1.2f, 0.1f, 20, () -> Ingredient.of(ModItems.MONULITE_INGOT));

}