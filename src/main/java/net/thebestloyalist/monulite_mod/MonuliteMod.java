package net.thebestloyalist.monulite_mod;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.thebestloyalist.monulite_mod.block.ModBlocks;
import net.thebestloyalist.monulite_mod.block.entity.ModBlockEntities;
import net.thebestloyalist.monulite_mod.effect.ModEffects;
import net.thebestloyalist.monulite_mod.event.ModEvents;
import net.thebestloyalist.monulite_mod.item.ModCreativeModeTabs;
import net.thebestloyalist.monulite_mod.item.ModItems;
import net.thebestloyalist.monulite_mod.item.ModToolTeirs;
import net.thebestloyalist.monulite_mod.item.custom.FlingItem;
import net.thebestloyalist.monulite_mod.item.custom.TrelgnackItem;
import net.thebestloyalist.monulite_mod.network.FlingPacket;
import net.thebestloyalist.monulite_mod.recipe.ModRecipes;
import net.thebestloyalist.monulite_mod.screen.ModMenuTypes;
import net.thebestloyalist.monulite_mod.sound.ModSounds;
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

        ModSounds.register(modEventBus);

        ModEffects.register(modEventBus);
        ModRecipes.register(modEventBus);

        ModCreativeModeTabs.register(modEventBus);

        ModBlockEntities.register(modEventBus);

        ModMenuTypes.register(modEventBus);

        LOGGER.info("Monulite Mod initialized");
    }
}
