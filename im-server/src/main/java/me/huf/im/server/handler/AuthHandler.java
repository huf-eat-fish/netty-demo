package me.huf.im.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import me.huf.im.server.utils.SessionUtils;

@Slf4j
public class AuthHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!SessionUtils.hasLogin(ctx.channel())) {
            ctx.channel().close();
            return;
        }
        ctx.channel().pipeline().remove(this);
        super.channelRead(ctx, msg);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        if (SessionUtils.hasLogin(ctx.channel())) {
            log.info("Connection login of {} is verified, remove AuthHandler", SessionUtils.getSession(ctx.channel()).getUsername());
        } else {
            log.info("No login verification, forced to close the connection!");
        }
    }
}
