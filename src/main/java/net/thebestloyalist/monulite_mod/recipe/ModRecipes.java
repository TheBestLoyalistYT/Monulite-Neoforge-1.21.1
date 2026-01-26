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

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<MonuliteCoinMolderRecipe>> GROWTH_CHAMBER_SERIALIZER =
            SERIALIZERS.register("growth_chamber", MonuliteCoinMolderRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<MonuliteCoinMolderRecipe>> GROWTH_CHAMBER_TYPE =
            TYPES.register("growth_chamber", () -> new RecipeType<MonuliteCoinMolderRecipe>() {
                @Override
                public String toString() {
                    return "growth_chamber";
                }
            });


    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }
}