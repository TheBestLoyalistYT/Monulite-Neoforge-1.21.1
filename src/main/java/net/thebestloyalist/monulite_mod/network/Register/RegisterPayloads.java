package net.thebestloyalist.monulite_mod.network.Register;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.SoundType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.thebestloyalist.monulite_mod.MonuliteMod;
import net.thebestloyalist.monulite_mod.item.ModItems;
import net.thebestloyalist.monulite_mod.item.custom.FlingItem;
import net.thebestloyalist.monulite_mod.network.FlingPacket;
import net.thebestloyalist.monulite_mod.network.GreyShaderPacket;

@EventBusSubscriber(modid = MonuliteMod.MOD_ID)
public class RegisterPayloads {

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrarFling = event.registrar("monulite_mod");

        registrarFling.playToServer(FlingPacket.TYPE, FlingPacket.CODEC,
                (packet, context) -> {
                    context.enqueueWork(() -> {
                        ServerPlayer player = (ServerPlayer) context.player();

                        if (player != null) {
                            FlingItem.fling(player, player.level());
                    }
                });
        });

        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playBidirectional(
                GreyShaderPacket.MyData.TYPE,
                GreyShaderPacket.MyData.STREAM_CODEC,
                GreyShaderPacket.MyData::handle
        );
    }
}
