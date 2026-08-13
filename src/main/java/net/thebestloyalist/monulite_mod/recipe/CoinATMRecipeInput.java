package net.thebestloyalist.monulite_mod.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record CoinATMRecipeInput(ItemStack input1) implements RecipeInput {

    @Override
    public ItemStack getItem(int index) {
        return switch (index) {
            case 0 -> input1;
            default -> ItemStack.EMPTY;
        };
    }

    @Override
    public int size() {
        return 1;
    }
}