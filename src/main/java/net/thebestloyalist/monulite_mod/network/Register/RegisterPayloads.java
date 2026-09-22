package net.thebestloyalist.monulite_mod.network.Register;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.thebestloyalist.monulite_mod.MonuliteMod;
import net.thebestloyalist.monulite_mod.item.ModItems;
import net.thebestloyalist.monulite_mod.item.custom.FlingItem;
import net.thebestloyalist.monulite_mod.network.*;
import net.thebestloyalist.monulite_mod.item.custom.custsheild_test;

import java.util.UUID;

import static net.thebestloyalist.monulite_mod.event.ModEvents.movement;
import static net.thebestloyalist.monulite_mod.item.custom.custsheild_test.custSldt;

@EventBusSubscriber(modid = MonuliteMod.MOD_ID)
public class RegisterPayloads {

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrarFling = event.registrar("monulite_mod");
        PayloadRegistrar registerDD = event.registrar("monulite_mod");
        PayloadRegistrar registerSld = event.registrar("monulite_mod");

        registrarFling.playToServer(FlingPacket.TYPE, FlingPacket.CODEC,
                (packet, context) -> {
                    context.enqueueWork(() -> {
                        ServerPlayer player = (ServerPlayer) context.player();

                        if (player != null) {
                            FlingItem.fling(player, player.level());
                    }
                });
        });

        registerSld.playToServer(Cust_Sld_Packet.TYPE, Cust_Sld_Packet.CODEC,
                (packet, context) -> {
                    context.enqueueWork(() -> {
                        Player player = context.player();
                        if (custSldt.containsKey(player.getUUID())) {
                            System.out.println("got to payload");

                            custsheild_test.Custsheild_data data =
                                    custSldt.get(player.getUUID());

                            movement(data.attacker, player);
                    }});
                });

        registerDD.playToServer(DoubleJumoPacket.TYPE, DoubleJumoPacket.CODEC,
                ((doubleJumoPacket, iPayloadContext) -> {
                    iPayloadContext.enqueueWork(() -> {
                        Player player = iPayloadContext.player();
                        Vec3 look = player.getLookAngle();
                        Vec3 velocity = look.scale(0.48);
                        player.setDeltaMovement(player.getDeltaMovement().x + velocity.x, velocity.y + 0.60, player.getDeltaMovement().z + velocity.z);
                        player.hurtMarked = true;
                    });
                }));

        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playBidirectional(
                GreyShaderPacket.MyData.TYPE,
                GreyShaderPacket.MyData.STREAM_CODEC,
                GreyShaderPacket.MyData::handle
        );
    }
}
