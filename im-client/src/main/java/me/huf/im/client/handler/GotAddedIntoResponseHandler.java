package me.huf.im.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import me.huf.im.client.console.Console;
import me.huf.im.common.packet.resp.GotAddedIntoResponsePacket;

public class GotAddedIntoResponseHandler extends SimpleChannelInboundHandler<GotAddedIntoResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GotAddedIntoResponsePacket msg) throws Exception {
        Console.print("You were invited to join the group[" + msg.getGroupId() + "]. The following users in the group: " + msg.getNameList());
    }
}
