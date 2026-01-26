package net.thebestloyalist.monulite_mod.screen;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.thebestloyalist.monulite_mod.MonuliteMod;
import net.thebestloyalist.monulite_mod.screen.custom.AcroteCoinMolderMenu;
import net.thebestloyalist.monulite_mod.screen.custom.MonuliteCoinMolderMenu;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, MonuliteMod.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<MonuliteCoinMolderMenu>> MONULITE_COIN_MOLDER_MENU =
            registerMenuType("monulite_coin_molder_menu", MonuliteCoinMolderMenu::new);

    public static final DeferredHolder<MenuType<?>, MenuType<AcroteCoinMolderMenu>> ACROTE_COIN_MOLDER_MENU =
            registerMenuType("acrote_coin_molder_menu", AcroteCoinMolderMenu::new);

    private static <T extends AbstractContainerMenu>DeferredHolder<MenuType<?>, MenuType<T>> registerMenuType(String name,
                                                                                                              IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IMenuTypeExtension.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}