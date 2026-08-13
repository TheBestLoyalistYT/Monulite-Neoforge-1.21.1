package net.thebestloyalist.monulite_mod.recipe;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.thebestloyalist.monulite_mod.MonuliteMod;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, MonuliteMod.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, MonuliteMod.MOD_ID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<MagicInfuserRecipe>> MAGIC_INFUSER_SERIALIZER =
            SERIALIZERS.register("magic_infuser", MagicInfuserRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<MagicInfuserRecipe>> MAGIC_INFUSER_TYPE =
            TYPES.register("magic_infuser", () -> new RecipeType<MagicInfuserRecipe>() {
                @Override
                public String toString() {
                    return "magic_infuser";
                }
            });

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<CoinATMRecipe>> COIN_ATM_SERIALIZER =
            SERIALIZERS.register("coin_atm", CoinATMRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<CoinATMRecipe>> COIN_ATM_TYPE =
            TYPES.register("coin_atm", () -> new RecipeType<CoinATMRecipe>() {
                @Override
                public String toString() {
                    return "coin_atm";
                }
            });


    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }
}
