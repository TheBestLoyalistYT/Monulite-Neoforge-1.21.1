package net.thebestloyalist.monulite_mod.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.thebestloyalist.monulite_mod.client.Clientbs;

public class GreyShaderPacket {

    public record MyData(boolean shadah) implements CustomPacketPayload {

        public static final Type<MyData> TYPE =
                new Type<>(ResourceLocation.fromNamespaceAndPath(
                        "monulite_mod",
                        "my_shadah_data"
                ));

        public static final StreamCodec<ByteBuf, MyData> STREAM_CODEC =
                StreamCodec.composite(
                        ByteBufCodecs.BOOL,
                        MyData::shadah,
                        MyData::new
                );

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }

        public static void handle(MyData packet, IPayloadContext context) {
            context.enqueueWork(() -> {
                Clientbs.shadah = packet.shadah();
            });
        }
    }
}
