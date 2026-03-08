package net.thebestloyalist.monulite_mod.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

public class FlightEffect extends MobEffect {

    public FlightEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.level().isClientSide) {
            if (entity instanceof Player player) {
                player.getAbilities().mayfly = true;
                player.onUpdateAbilities();

                MobEffectInstance effectInstance = player.getEffect(ModEffects.FLIGHT_EFFECT);
                if (effectInstance == null) return false;

                int duration = effectInstance.getDuration();

                if (duration == 1)
                {
                    player.getAbilities().mayfly = false;
                    player.onUpdateAbilities();
                }
            }
        }
        return true;
    }
}
