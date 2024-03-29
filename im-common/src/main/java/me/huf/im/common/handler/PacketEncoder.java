package me.huf.im.common.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import me.huf.im.common.packet.Packet;
import me.huf.im.common.packet.PacketCodec;


public class PacketEncoder extends MessageToByteEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
        PacketCodec.INSTANCE.encode(msg, out);
    }
}
