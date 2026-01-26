package net.thebestloyalist.monulite_mod.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public record MonuliteCoinMolderRecipe(Ingredient inputItem, ItemStack output) implements Recipe<MonuliteCoinMolderRecipeInput> {
    // inputItem & output ==> Read From JSON File!
    // GrowthChamberRecipeInput --> INVENTORY of the Block Entity

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(inputItem);
        return list;
    }

    @Override
    public boolean matches(MonuliteCoinMolderRecipeInput monuliteCoinMolderRecipeInput, Level level) {
        if (level.isClientSide()) {
            return false;
        }

        return inputItem.test(monuliteCoinMolderRecipeInput.getItem(0));
    }

    @Override
    public ItemStack assemble(MonuliteCoinMolderRecipeInput monuliteCoinMolderRecipeInput, HolderLookup.Provider provider) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.GROWTH_CHAMBER_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.GROWTH_CHAMBER_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<MonuliteCoinMolderRecipe> {
        public static final MapCodec<MonuliteCoinMolderRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(MonuliteCoinMolderRecipe::inputItem),
                ItemStack.CODEC.fieldOf("result").forGetter(MonuliteCoinMolderRecipe::output)
        ).apply(inst, MonuliteCoinMolderRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, MonuliteCoinMolderRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, MonuliteCoinMolderRecipe::inputItem,
                        ItemStack.STREAM_CODEC, MonuliteCoinMolderRecipe::output,
                        MonuliteCoinMolderRecipe::new);

        @Override
        public MapCodec<MonuliteCoinMolderRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, MonuliteCoinMolderRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}