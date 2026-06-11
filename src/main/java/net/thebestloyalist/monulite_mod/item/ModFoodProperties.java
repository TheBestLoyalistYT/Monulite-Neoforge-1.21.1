package net.thebestloyalist.monulite_mod.item;

import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.thebestloyalist.monulite_mod.effect.ModEffects;

public class ModFoodProperties {
    public static final FoodProperties MONULITE_FLIGHT_FOOD = new FoodProperties.Builder().nutrition(3).saturationModifier(0.25f).alwaysEdible()
            .effect(() -> new MobEffectInstance(ModEffects.FLIGHT_EFFECT, 600), 1.0f).build();

    public static final FoodProperties MONULITE_GOLDEN_CARROT_FOOD = new FoodProperties.Builder().nutrition(5).saturationModifier(1.00f).alwaysEdible()
            .effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 600), 1.0f).build();
}
