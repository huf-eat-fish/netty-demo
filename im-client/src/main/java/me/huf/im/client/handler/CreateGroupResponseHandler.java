package me.huf.im.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import me.huf.im.client.console.Console;
import me.huf.im.common.packet.resp.CreateGroupResponsePacket;

public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupResponsePacket msg) throws Exception {
        if (Boolean.FALSE.equals(msg.getSuccess())) {
            Console.print("Create failed, " + msg.getInfo());
        } else {
            Console.print("Create success, group id is " + msg.getId() + ". The following users in the group: " + msg.getNameList());
        }
    }
}
