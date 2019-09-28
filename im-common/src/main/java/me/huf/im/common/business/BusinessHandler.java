package me.huf.im.common.business;

import io.netty.channel.ChannelHandlerContext;
import me.huf.im.common.packet.Packet;

public interface BusinessHandler {
    Class<? extends Packet> supportType();

    void handle(ChannelHandlerContext ctx, Packet packet);
}
