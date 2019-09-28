package me.huf.im.common.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import me.huf.im.common.packet.Packet;
import me.huf.im.common.packet.PacketCodec;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        Packet packet = PacketCodec.INSTANCE.decode(in);
        out.add(packet);
    }
}
