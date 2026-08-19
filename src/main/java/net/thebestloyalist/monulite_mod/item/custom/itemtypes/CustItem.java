package net.thebestloyalist.monulite_mod.item.custom.itemtypes;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

import java.util.logging.Level;

public abstract class CustItem extends Item {

    public CustItem(Properties properties) {
        super(properties);
    }
    public void onAttackerDeath(LivingDeathEvent event){
    }
    public void onTargerDeath(LivingDeathEvent event){
    }
    public void onRightClick(PlayerInteractEvent.RightClickItem event) {
    }
    public static void onLevelTick(LevelTickEvent.Post event) {
    }
    public void tick(Player player, Level level) {
    }
}
