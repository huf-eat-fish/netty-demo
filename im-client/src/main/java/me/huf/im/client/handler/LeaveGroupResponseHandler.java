package me.huf.im.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import me.huf.im.client.console.Console;
import me.huf.im.common.packet.resp.LeaveGroupResponsePacket;

public class LeaveGroupResponseHandler extends SimpleChannelInboundHandler<LeaveGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LeaveGroupResponsePacket msg) throws Exception {
        if (Boolean.TRUE.equals(msg.getSuccess())) {
            Console.print(msg.getInfo());
        } else {
            Console.print("Leave failed. "+msg.getInfo());
        }
    }
}
