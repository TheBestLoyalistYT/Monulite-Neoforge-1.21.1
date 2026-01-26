package net.thebestloyalist.monulite_mod.item;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.thebestloyalist.monulite_mod.MonuliteMod;

import java.util.List;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MonuliteMod.MOD_ID);

    public static final DeferredItem<Item> COIN_MOLD = ITEMS.register("coin_mold",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_MONULITE = ITEMS.register("raw_monulite",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> MONULITE_INGOT = ITEMS.register("monulite_ingot",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> MONULITE_OOZ = ITEMS.register("monulite_ooz",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> MONULITE_COIN = ITEMS.register("monulite_coin",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_ACROTE = ITEMS.register("raw_acrote",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ACROTE_OOZ = ITEMS.register("acrote_ooz",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ACROTE_COIN = ITEMS.register("acrote_coin",
            () -> new Item(new Item.Properties()));


    public static final DeferredItem<SwordItem> MONULITE_SWORD = ITEMS.register("monulite_sword",
            () -> new SwordItem(ModToolTeirs.MONULITE_SWORD, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTeirs.MONULITE_SWORD, 4, -2.4f))));

    public static final DeferredItem<PickaxeItem> MONULITE_PICKAXE = ITEMS.register("monulite_pickaxe",
            () -> new PickaxeItem(ModToolTeirs.MONULITE_PICKAXE, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTeirs.MONULITE_PICKAXE, 2, -2.8f))));

    public static final DeferredItem<ShovelItem> MONULITE_SHOVEL = ITEMS.register("monulite_shovel",
            () -> new ShovelItem(ModToolTeirs.MONULITE_SHOVEL, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTeirs.MONULITE_SHOVEL, 2, -3.0f))));

    public static final DeferredItem<AxeItem> MONULITE_AXE = ITEMS.register("monulite_axe",
            () -> new AxeItem(ModToolTeirs.MONULITE_AXE, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTeirs.MONULITE_AXE, 6, -3.0f))));

    public static final DeferredItem<HoeItem> MONULITE_HOE = ITEMS.register("monulite_hoe",
            () -> new HoeItem(ModToolTeirs.MONULITE_HOE, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTeirs.MONULITE_HOE, 1, 0.0f))));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}