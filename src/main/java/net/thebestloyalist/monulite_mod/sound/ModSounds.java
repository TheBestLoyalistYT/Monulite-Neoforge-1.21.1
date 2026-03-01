package net.thebestloyalist.monulite_mod.sound;


import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.util.DeferredSoundType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.thebestloyalist.monulite_mod.MonuliteMod;

import java.util.function.Supplier;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, MonuliteMod.MOD_ID);

    public static final Supplier<SoundEvent> MONULITE_COIN_MOLDER_BREAK = registerSoundEvent("monulite_coin_molder_break");
    public static final Supplier<SoundEvent> MONULITE_COIN_MOLDER_STEP = registerSoundEvent("monulite_coin_molder_step");
    public static final Supplier<SoundEvent> MONULITE_COIN_MOLDER_PLACE = registerSoundEvent("monulite_coin_molder_place");
    public static final Supplier<SoundEvent> MONULITE_COIN_MOLDER_HIT = registerSoundEvent("monulite_coin_molder_hit");
    public static final Supplier<SoundEvent> MONULITE_COIN_MOLDER_FALL = registerSoundEvent("monulite_coin_molder_fall");

    public static final DeferredSoundType MONULITE_COIN_MOLDER_SOUND_GROUP = new DeferredSoundType(1f, 1f,
            ModSounds.MONULITE_COIN_MOLDER_BREAK, ModSounds.MONULITE_COIN_MOLDER_STEP, ModSounds.MONULITE_COIN_MOLDER_PLACE,
            ModSounds.MONULITE_COIN_MOLDER_HIT, ModSounds.MONULITE_COIN_MOLDER_FALL);

    private static Supplier<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(MonuliteMod.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}