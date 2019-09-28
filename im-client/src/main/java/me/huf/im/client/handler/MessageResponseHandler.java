package me.huf.im.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import me.huf.im.client.console.Console;
import me.huf.im.common.packet.resp.ToUserMessageResponsePacket;

@Slf4j
public class MessageResponseHandler extends SimpleChannelInboundHandler<ToUserMessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ToUserMessageResponsePacket msg) throws Exception {
        log.info("{}:{} -> {}", msg.getFromUserId(), msg.getFromUserName(), msg.getMessage());
        Console.print(msg.getFromUserId() + ": " + msg.getFromUserName() + " -> " + msg.getMessage());
    }
}
