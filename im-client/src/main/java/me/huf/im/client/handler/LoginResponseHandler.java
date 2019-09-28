package me.huf.im.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import me.huf.im.client.console.Console;
import me.huf.im.client.utils.LoginUtils;
import me.huf.im.common.constant.AttrKey;
import me.huf.im.common.packet.resp.LoginResponsePacket;

@Slf4j
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception {
        if (Boolean.TRUE.equals(msg.getSuccess())) {
            log.info("login success with id {}", msg.getId());
            Console.print("login success with id " + msg.getId());
            LoginUtils.setLogin(ctx.channel());
            LoginUtils.setId(ctx.channel(), msg.getId());
        } else {
            log.info("login failed with info -> {}", msg.getInfo());
            Console.print("login failed with info -> " + msg.getInfo());
        }
        ctx.channel().attr(AttrKey.LOGGING_IN).set(false);
    }
}
