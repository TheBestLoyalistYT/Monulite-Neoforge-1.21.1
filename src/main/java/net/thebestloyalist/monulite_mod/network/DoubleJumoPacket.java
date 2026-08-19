package net.thebestloyalist.monulite_mod.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record DoubleJumoPacket() implements CustomPacketPayload {
    public static final Type<DoubleJumoPacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath("monulite_mod", "doublejumo")
    );
    public static final StreamCodec<ByteBuf, DoubleJumoPacket> CODEC =
            StreamCodec.unit(new DoubleJumoPacket());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
