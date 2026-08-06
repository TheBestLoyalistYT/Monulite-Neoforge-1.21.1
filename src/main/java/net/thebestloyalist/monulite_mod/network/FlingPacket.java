package net.thebestloyalist.monulite_mod.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record FlingPacket() implements CustomPacketPayload {
    public static final Type<FlingPacket> TYPE = new Type<>(
            ResourceLocation.fromNamespaceAndPath("monulite_mod", "fling")
    );
    public static final StreamCodec<ByteBuf, FlingPacket> CODEC =
            StreamCodec.unit(new FlingPacket());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
