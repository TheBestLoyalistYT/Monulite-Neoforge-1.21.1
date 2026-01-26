package net.thebestloyalist.monulite_mod;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.thebestloyalist.monulite_mod.block.ModBlocks;
import net.thebestloyalist.monulite_mod.item.ModItems;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.fml.common.Mod;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(MonuliteMod.MOD_ID)
public class MonuliteMod {

    public static final String MOD_ID = "monulite_mod";
    public static final Logger LOGGER = LogUtils.getLogger();

    public MonuliteMod() {
        // NeoForge mod event bus
        IEventBus modEventBus =
                ModLoadingContext.get().getActiveContainer().getEventBus();

        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);

        LOGGER.info("Monulite Mod initialized");
    }
}
