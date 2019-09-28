package me.huf.im.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import me.huf.im.common.bean.Session;
import me.huf.im.common.packet.req.LoginRequestPacket;
import me.huf.im.common.packet.resp.LoginResponsePacket;
import me.huf.im.common.utils.IDUtils;
import me.huf.im.server.utils.SessionUtils;

@Slf4j
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
        LoginResponsePacket responsePacket;
        if (valid(msg)) {
            Session session = new Session(IDUtils.randomId(), msg.getUsername());
            responsePacket = new LoginResponsePacket().setSuccess(true).setId(session.getUserId());

            SessionUtils.bindSession(session, ctx.channel());
            log.info("{} login success from {}", msg.getUsername(), ctx.channel().remoteAddress());
        } else {
            responsePacket = new LoginResponsePacket().setSuccess(false).setInfo("wrong username or password");
            log.info("{} login failed with info -> {}", msg.getUsername(), responsePacket.getInfo());
        }

        ctx.channel().writeAndFlush(responsePacket);
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        String password = loginRequestPacket.getPassword();
        String username = loginRequestPacket.getUsername();

        return username.length() == password.length();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtils.unBindSession(ctx.channel());
        super.channelInactive(ctx);
    }
}
