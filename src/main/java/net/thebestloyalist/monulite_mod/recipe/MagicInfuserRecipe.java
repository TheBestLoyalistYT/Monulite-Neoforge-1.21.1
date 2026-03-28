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

public record MagicInfuserRecipe(Ingredient inputItem, ItemStack output) implements Recipe<MagicInfuserRecipeInput> {
    // inputItem & output ==> Read From JSON File!
    // GrowthChamberRecipeInput --> INVENTORY of the Block Entity

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(inputItem);
        return list;
    }

    @Override
    public boolean matches(MagicInfuserRecipeInput magicInfuserRecipeInput, Level level) {
        if (level.isClientSide()) {
            return false;
        }

        return inputItem.test(magicInfuserRecipeInput.getItem(0)) &&
                inputItem.test(magicInfuserRecipeInput.getItem(1));
    }

    @Override
    public ItemStack assemble(MagicInfuserRecipeInput magicInfuserRecipeInput, HolderLookup.Provider provider) {
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
        return ModRecipes.MAGIC_INFUSER_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.MAGIC_INFUSER_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<MagicInfuserRecipe> {
        public static final MapCodec<MagicInfuserRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(MagicInfuserRecipe::inputItem),
                ItemStack.CODEC.fieldOf("result").forGetter(MagicInfuserRecipe::output)
        ).apply(inst, MagicInfuserRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, MagicInfuserRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, MagicInfuserRecipe::inputItem,
                        ItemStack.STREAM_CODEC, MagicInfuserRecipe::output,
                        MagicInfuserRecipe::new);

        @Override
        public MapCodec<MagicInfuserRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, MagicInfuserRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
