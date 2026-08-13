package net.thebestloyalist.monulite_mod.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public record CoinATMRecipe(Ingredient inputItem, int inputCount, ItemStack output) implements Recipe<CoinATMRecipeInput> {
    // inputItem & output ==> Read From JSON File!
    // GrowthChamberRecipeInput --> INVENTORY of the Block Entity

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(inputItem);
        return list;
    }

    @Override
    public boolean matches(CoinATMRecipeInput coinATMRecipeInput, Level level) {
        if (level.isClientSide()) {
            return false;
        }

        return inputItem.test(coinATMRecipeInput.getItem(0)) &&
                coinATMRecipeInput.getItem(0).getCount() >= inputCount;
    }

    @Override
    public ItemStack assemble(CoinATMRecipeInput coinATMRecipeInput, HolderLookup.Provider provider) {
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
        return ModRecipes.COIN_ATM_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.COIN_ATM_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<CoinATMRecipe> {
        public static final MapCodec<CoinATMRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(CoinATMRecipe::inputItem),
                com.mojang.serialization.Codec.INT.fieldOf("count").forGetter(CoinATMRecipe::inputCount),
                ItemStack.CODEC.fieldOf("result").forGetter(CoinATMRecipe::output)
        ).apply(inst, CoinATMRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, CoinATMRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, CoinATMRecipe::inputItem,
                        ByteBufCodecs.VAR_INT.cast(), CoinATMRecipe::inputCount,
                        ItemStack.STREAM_CODEC, CoinATMRecipe::output,
                        CoinATMRecipe::new);

        @Override
        public MapCodec<CoinATMRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, CoinATMRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
