package net.thebestloyalist.monulite_mod.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.UUID;

public record Cust_Sld_Packet() implements CustomPacketPayload {
    public static final Type<Cust_Sld_Packet> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath("monulite_mod", "cust_sld_pckt")
    );
    public static final StreamCodec<ByteBuf, Cust_Sld_Packet> CODEC =
            StreamCodec.unit(new Cust_Sld_Packet());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
