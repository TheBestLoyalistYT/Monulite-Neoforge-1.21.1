package net.thebestloyalist.monulite_mod.event;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.thebestloyalist.monulite_mod.MonuliteMod;
import net.thebestloyalist.monulite_mod.block.ModBlocks;
import net.thebestloyalist.monulite_mod.client.Clientbs;
import net.thebestloyalist.monulite_mod.client.KeyBinds;
import net.thebestloyalist.monulite_mod.event.item_event_logic.ZWorldoLog;
import net.thebestloyalist.monulite_mod.item.ModItems;
import net.thebestloyalist.monulite_mod.item.custom.TickClock;
import net.thebestloyalist.monulite_mod.network.FlingPacket;
import net.thebestloyalist.monulite_mod.network.GreyShaderPacket;
import java.util.ArrayList;
import java.util.Set;

import static net.thebestloyalist.monulite_mod.event.item_event_logic.ZWorldoLog.wldoData;

@EventBusSubscriber(modid = MonuliteMod.MOD_ID)
public class ModEvents {

    private static boolean shaderLoaded = false;

    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        Level level = player.level();
        long time = level.getGameTime();
        if (player.getMainHandItem().is(ModItems.CUST_CLOCK)) {
            player.displayClientMessage(Component.literal(
                    ChatFormatting.WHITE + "Whats the time? Why its: " + time), false);
        }

        if (player.getMainHandItem().is(ModItems.TICK_CLOCK)) {
            TickClock.onRightClick(player, level);
        }
    }

    @SubscribeEvent
    public static void onLevelTick(LevelTickEvent.Post event) {
        TickClock.onLevelTick(event);
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.getInventory().hasAnyOf(Set.of(ModItems.CONDENSED_MONULITE.get(), ModBlocks.MONULITE_CLUSTER_BLOCK.asItem()))) {
            for (int i = 1; i <= 7; i++) {
                BlockPos blockPos = player.blockPosition().below(i);

                if (i >= 6 && player.level().getBlockState(blockPos).isAir()) {
                    player.setDeltaMovement(player.getDeltaMovement().with(Direction.Axis.Y, -0.01));
                    player.hurtMarked = true;
                } else if (i == 5 && player.level().getBlockState(blockPos).isAir()) {
                    player.setDeltaMovement(player.getDeltaMovement().with(Direction.Axis.Y, 0.02));
                    player.hurtMarked = true;
                } else if (!player.level().getBlockState(blockPos).isAir()) {
                    player.setDeltaMovement(player.getDeltaMovement().with(Direction.Axis.Y, 0.1));
                    player.hurtMarked = true;

                }
            }
        }
    }

    @SubscribeEvent
    public static void onAttackerDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (wldoData.containsKey(player.getUUID())) {
                player.getServer().getCommands().performPrefixedCommand(
                        player.getServer().createCommandSourceStack().withSuppressedOutput(),
                        "tick unfreeze");
                player.displayClientMessage(Component.literal(
                        ChatFormatting.RED + "TIME STARTS MOVING AGAIN"), true);
                PacketDistributor.sendToAllPlayers(new GreyShaderPacket.MyData(false));

                for (ZWorldoLog.WorldoData data : new ArrayList<>(wldoData.values())) {
                    data.tickTime = 990;
                }
            }
        }
    }

    @SubscribeEvent
    public static void onServerClose(LevelEvent.Unload event) {
        for (ZWorldoLog.WorldoData data : new ArrayList<>(wldoData.values())) {

            data.tickTime = 999;
        }
        wldoData.clear();
    }

    @SubscribeEvent
    public static void onClient(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        if (KeyBinds.MON_KEY_1.isDown()) {
            if (mc.player.isHolding(ModItems.GRAPPLE.get())) {
                PacketDistributor.sendToServer(new FlingPacket());
            }
        }

        //this saturation shader was very much STOLEN from R4t's Squid Ink mod: Defile. all files called "greyscale" or "translucent_no_light_direction"
        //I tried to get something basic working, it didn't work. and I do NOT understand half the shit in that .fsh file :sob:
        // so, credit! I did tweak a few numbers tho!
        if (Clientbs.shadah) {
            System.out.println("LKDFNCOSKDLVNLKSNVLKJDSFN");
            mc.gameRenderer.loadEffect(
                    ResourceLocation.fromNamespaceAndPath(
                            "monulite_mod",
                            "shaders/post/greyscale.json"
                    )
            );
        } else {
            mc.gameRenderer.shutdownEffect();
        }
    }
}


