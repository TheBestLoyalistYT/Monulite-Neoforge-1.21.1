package net.thebestloyalist.monulite_mod.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.thebestloyalist.monulite_mod.effect.ModEffects;

public class ModFoodProperties {
    public static final FoodProperties MONULITE_FLIGHT_FOOD = new FoodProperties.Builder().nutrition(3).saturationModifier(0.25f).alwaysEdible()
            .effect(() -> new MobEffectInstance(ModEffects.FLIGHT_EFFECT, 600), 1.0f).build();
}
