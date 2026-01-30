package net.thebestloyalist.monulite_mod.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.thebestloyalist.monulite_mod.MonuliteMod;
import net.thebestloyalist.monulite_mod.block.ModBlocks;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MonuliteMod.MOD_ID);

    public static final Supplier<CreativeModeTab> MONULITE_ITEMS_TAB = CREATIVE_MODE_TAB.register("monulite_items_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.RAW_MONULITE.get()))
                    .title(Component.translatable("creativetab.monulite_mod.monulite_items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.RAW_MONULITE);
                        output.accept(ModItems.RAW_ACROTE);
                        output.accept(ModItems.MONULITE_INGOT);
                        output.accept(ModItems.MONULITE_OOZ);
                        output.accept(ModItems.ACROTE_OOZ);
                        output.accept(ModItems.COIN_MOLD);
                        output.accept(ModItems.MONULITE_COIN);
                        output.accept(ModItems.ACROTE_COIN);
                        output.accept(ModBlocks.MONULITE_COIN_MOLDER);
                        output.accept(ModBlocks.ACROTE_COIN_MOLDER);
                        output.accept(ModItems.MONULITE_SWORD);
                        output.accept(ModItems.MONULITE_PICKAXE);
                        output.accept(ModItems.MONULITE_SHOVEL);
                        output.accept(ModItems.MONULITE_AXE);
                        output.accept(ModItems.MONULITE_HOE);
                        output.accept(ModItems.MONULITE_HELMET);
                        output.accept(ModItems.MONULITE_CHESTPLATE);
                        output.accept(ModItems.MONULITE_LEGGINGS);
                        output.accept(ModItems.MONULITE_BOOTS);

                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}