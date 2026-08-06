package net.thebestloyalist.monulite_mod.event;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.thebestloyalist.monulite_mod.MonuliteMod;
import net.thebestloyalist.monulite_mod.client.Clientbs;
import net.thebestloyalist.monulite_mod.client.KeyBinds;
import net.thebestloyalist.monulite_mod.event.item_event_logic.ZWorldoLog;
import net.thebestloyalist.monulite_mod.item.ModItems;
import net.thebestloyalist.monulite_mod.network.FlingPacket;
import net.thebestloyalist.monulite_mod.network.GreyShaderPacket;
import java.util.ArrayList;
import static net.thebestloyalist.monulite_mod.event.item_event_logic.ZWorldoLog.wldoData;

@EventBusSubscriber(modid = MonuliteMod.MOD_ID)
public class ModEvents {

    private static boolean shadah = false;

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
            System.out.println("gotrighttick");
            if (level.isClientSide) return;

            if (wldoData.isEmpty()) {
                System.out.println("got empt");
                ZWorldoLog.active(player);
                PacketDistributor.sendToAllPlayers(new GreyShaderPacket.MyData(true));
            } else {
                player.displayClientMessage(
                        Component.literal(ChatFormatting.DARK_RED + "TIME IS ALREADY PAUSED!"), true);
            }

            if (!wldoData.isEmpty()) {
                for (ZWorldoLog.WorldoData data : new ArrayList<>(wldoData.values())) {
                    if (data.tickTime >= 1000) {
                        if (player.getUUID() != data.pauser.getUUID()) {
                            ZWorldoLog.active(player);
                            PacketDistributor.sendToAllPlayers(new GreyShaderPacket.MyData(true));
                        }
                    } else {
                        player.displayClientMessage(Component.literal(
                                ChatFormatting.DARK_PURPLE + "your clock doesnt respond, frozen within time..."), true);
                    }
                    if (player.getUUID() == data.pauser.getUUID()) {
                        if (player.isCrouching()) {
                            if (data.tickTime <= 994) {
                                data.tickTime = 995;
                                System.out.println("Removed Time Pause");
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLevelTick(LevelTickEvent.Post event) {
        Level level = event.getLevel();

        for (ZWorldoLog.WorldoData data : new ArrayList<>(wldoData.values())) {

            if (data != null) {
                data.tickTime++;

                if (data.pauser.level().isClientSide) return;


                if (data.tickTime == 8) {
                    System.out.println("did stop");
                    data.pauser.getServer().getCommands().performPrefixedCommand(
                            data.pauser.getServer().createCommandSourceStack().withPermission(4).withSuppressedOutput(),
                            "playsound monulite_mod:time_stop master @a ~ ~ ~ 1 1 1");
                }

                if (data.tickTime == 10) {
                    data.pauser.getServer().getCommands().performPrefixedCommand(
                            data.pauser.getServer().createCommandSourceStack().withSuppressedOutput(),
                            "tick freeze");
                    System.out.println("got frez");
                    data.pauser.displayClientMessage(Component.literal(
                            ChatFormatting.DARK_PURPLE + "TIME HAS STOOD STILL... besides for *you*"), true);
                    Clientbs.shadah = true;
                }

                if (data.tickTime <= 999) {
                    for (ServerPlayer player : data.pauser.getServer().getPlayerList().getPlayers()) {
                        if (data.tickTime <= 860) {
                            player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 50, 0, false, false));
                        }
                        if (player != data.pauser) {
                            data.pauser.fallDistance = 0;
                            Vec3 pos = player.position();
                            player.setDeltaMovement(0,0,0);
                            player.setNoGravity(true);
                            player.hurtMarked = true;
                            System.out.println("got playah");
                            if (data.tickTime == 750) {
                                data.pauser.displayClientMessage(Component.literal(
                                        ChatFormatting.GOLD + "TIME IS ALMOST BACK"), true);
                            }
                        }
                    }
                }

                if (data.tickTime == 1000) {
                    for (ServerPlayer player : data.pauser.getServer().getPlayerList().getPlayers()) {
                        data.pauser.getServer().getCommands().performPrefixedCommand(
                                data.pauser.getServer().createCommandSourceStack().withSuppressedOutput(),
                                "tick unfreeze");
                        data.pauser.displayClientMessage(Component.literal(
                                ChatFormatting.RED + "TIME STARTS MOVING AGAIN"), true);
                        PacketDistributor.sendToAllPlayers(new GreyShaderPacket.MyData(false));
                        Clientbs.shadah = false;

                        if (player != data.pauser) {
                            player.setNoGravity(false);
                        }
                    }
                }

                if (data.tickTime == 1010) {
                    System.out.println("did resume");
                    data.pauser.getServer().getCommands().performPrefixedCommand(
                            data.pauser.getServer().createCommandSourceStack().withPermission(4).withSuppressedOutput(),
                            "playsound monulite_mod:time_resume master @a ~ ~ ~ 1 1 1");
                }

                if (data.tickTime == 3000) {
                    data.pauser.displayClientMessage(Component.literal(
                            ChatFormatting.YELLOW + "The Clock Is Half Way Rewinded"), true);
                    if (data.pauser.fallDistance == 0) {
                        data.pauser.fallDistance = 3;
                    }
                }

                if (data.tickTime >= 5000) {
                    wldoData.remove(data.pauser.getUUID());
                    data.pauser.displayClientMessage(Component.literal(
                            ChatFormatting.GREEN + "The Clock Is Rewinded"), true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onAttackerDeath(LivingDeathEvent event) {
        if  (event.getEntity() instanceof Player player) {
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

                shadah = false;
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


