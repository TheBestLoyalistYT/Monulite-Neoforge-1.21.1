package net.thebestloyalist.monulite_mod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.thebestloyalist.monulite_mod.MonuliteMod;
import net.thebestloyalist.monulite_mod.item.ModItems;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                              CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, MonuliteMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ItemTags.SWORDS)
                .add(ModItems.TRELGNACK.get())
                .add(ModItems.MONULITE_SWORD.get());
        tag(ItemTags.PICKAXES)
                .add(ModItems.MONULITE_PICKAXE.get());
        tag(ItemTags.SHOVELS)
                .add(ModItems.MONULITE_SHOVEL.get());
        tag(ItemTags.AXES)
                .add(ModItems.MONULITE_AXE.get());
        tag(ItemTags.HOES)
                .add(ModItems.MONULITE_HOE.get());

        this.tag(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.MONULITE_HELMET.get())
                .add(ModItems.MONULITE_CHESTPLATE.get())
                .add(ModItems.MONULITE_LEGGINGS.get())
                .add(ModItems.MONULITE_BOOTS.get());

        this.tag(ItemTags.MINING_ENCHANTABLE)
                .add(ModItems.MONULITE_PICKAXE.get())
                .add(ModItems.MONULITE_SHOVEL.get())
                .add(ModItems.MONULITE_AXE.get())
                .add(ModItems.MONULITE_HOE.get());

        this.tag(ItemTags.SWORD_ENCHANTABLE)
                .add(ModItems.TRELGNACK.get())
                .add(ModItems.MONULITE_SWORD.get());

        this.tag(ItemTags.SHARP_WEAPON_ENCHANTABLE)
                .add(ModItems.MONULITE_AXE.get());

        this.tag(ItemTags.HEAD_ARMOR_ENCHANTABLE)
                .add(ModItems.MONULITE_HELMET.get());

        this.tag(ItemTags.CHEST_ARMOR_ENCHANTABLE)
                .add(ModItems.MONULITE_CHESTPLATE.get());

        this.tag(ItemTags.LEG_ARMOR_ENCHANTABLE)
                .add(ModItems.MONULITE_LEGGINGS.get());

        this.tag(ItemTags.FOOT_ARMOR_ENCHANTABLE)
                .add(ModItems.CLOUD_BOOTS.get())
                .add(ModItems.MONULITE_BOOTS.get());

        this.tag(ItemTags.EQUIPPABLE_ENCHANTABLE)
                .add(ModItems.MONULITE_HELMET.get())
                .add(ModItems.MONULITE_CHESTPLATE.get())
                .add(ModItems.MONULITE_LEGGINGS.get())
                .add(ModItems.MONULITE_BOOTS.get())
                .add(ModItems.CLOUD_BOOTS.get());

        this.tag(ItemTags.DURABILITY_ENCHANTABLE)
                .add(ModItems.MONULITE_HELMET.get())
                .add(ModItems.MONULITE_CHESTPLATE.get())
                .add(ModItems.MONULITE_LEGGINGS.get())
                .add(ModItems.MONULITE_BOOTS.get())
                .add(ModItems.CLOUD_BOOTS.get());
    }
}