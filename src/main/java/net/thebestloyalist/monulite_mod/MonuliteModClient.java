package net.thebestloyalist.monulite_mod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.thebestloyalist.monulite_mod.block.ModBlocks;
import net.thebestloyalist.monulite_mod.client.KeyBinds;
import net.thebestloyalist.monulite_mod.item.ModItems;
import net.thebestloyalist.monulite_mod.item.custom.FlingItem;
import net.thebestloyalist.monulite_mod.item.custom.TrelgnackItem;
import net.thebestloyalist.monulite_mod.network.FlingPacket;
import net.thebestloyalist.monulite_mod.screen.ModMenuTypes;
import net.thebestloyalist.monulite_mod.screen.custom.AcroteCoinMolderScreen;
import net.thebestloyalist.monulite_mod.screen.custom.MagicInfuserScreen;
import net.thebestloyalist.monulite_mod.screen.custom.MonuliteCoinMolderScreen;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = MonuliteMod.MOD_ID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = MonuliteMod.MOD_ID, value = Dist.CLIENT)
public class MonuliteModClient {
    public MonuliteModClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        MonuliteMod.LOGGER.info("HELLO FROM CLIENT SETUP");
        MonuliteMod.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());

    }

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.MONULITE_COIN_MOLDER_MENU.get(), MonuliteCoinMolderScreen::new);
        event.register(ModMenuTypes.ACROTE_COIN_MOLDER_MENU.get(), AcroteCoinMolderScreen::new);
        event.register(ModMenuTypes.MAGIC_INFUSER_MENU.get(), MagicInfuserScreen::new);
    }
}
