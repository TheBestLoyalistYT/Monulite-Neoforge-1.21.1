package net.thebestloyalist.monulite_mod.worldgen.tree;

import net.minecraft.world.level.block.grower.TreeGrower;
import net.thebestloyalist.monulite_mod.MonuliteMod;
import net.thebestloyalist.monulite_mod.worldgen.ModConfiguredFeatures;

import java.util.Optional;

public class ModTreeGrowers {
    public static final TreeGrower LITEWOOD = new TreeGrower(MonuliteMod.MOD_ID + ":litewood",
            Optional.empty(), Optional.of(ModConfiguredFeatures.LITEWOOD_KEY), Optional.empty());

}
