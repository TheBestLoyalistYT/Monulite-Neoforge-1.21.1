package net.thebestloyalist.monulite_mod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.thebestloyalist.monulite_mod.MonuliteMod;
import net.thebestloyalist.monulite_mod.block.ModBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MonuliteMod.MOD_ID, existingFileHelper);
    }

    @Override
protected void addTags(HolderLookup.Provider provider) {
    tag(BlockTags.MINEABLE_WITH_PICKAXE)
            .add(ModBlocks.MONULITE_BLOCK.get())
            .add(ModBlocks.DEEPSLATE_MONULITE_ORE.get())
            .add(ModBlocks.MONULITE_ORE.get())
            .add(ModBlocks.ACROTE_ORE.get())
            .add(ModBlocks.MONULITE_COIN_MOLDER.get())
            .add(ModBlocks.ACROTE_COIN_MOLDER.get());

    tag(BlockTags.NEEDS_STONE_TOOL)
            .add(ModBlocks.ACROTE_ORE.get())
            .add(ModBlocks.ACROTE_COIN_MOLDER.get());

    tag(BlockTags.NEEDS_IRON_TOOL)
            .add(ModBlocks.MONULITE_BLOCK.get())
            .add(ModBlocks.MONULITE_COIN_MOLDER.get());


    tag(BlockTags.NEEDS_DIAMOND_TOOL)
            .add(ModBlocks.DEEPSLATE_MONULITE_ORE.get())
            .add(ModBlocks.MONULITE_ORE.get());

    }
}