package me.huf.im.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import me.huf.im.common.bean.Session;
import me.huf.im.common.packet.req.ToUserMessageRequestPacket;
import me.huf.im.common.packet.resp.ToUserMessageResponsePacket;
import me.huf.im.server.utils.SessionUtils;

@Slf4j
public class MessageRequestHandler extends SimpleChannelInboundHandler<ToUserMessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ToUserMessageRequestPacket msg) throws Exception {
        Session session = SessionUtils.getSession(ctx.channel());
        log.info("receive msg from {} to {}: {}", session.getUserId(), msg.getToUserId(), msg.getMessage());

        String toUserId = msg.getToUserId();
        Channel toUserChannel = SessionUtils.getChannel(toUserId);

        if (toUserChannel == null) {
            ToUserMessageResponsePacket responsePacket = new ToUserMessageResponsePacket();
            responsePacket.setFromUserId(session.getUserId());
            responsePacket.setMessage("Target user is offline and failed to send");
            ctx.channel().writeAndFlush(responsePacket);
            return;
        }

        ToUserMessageResponsePacket responsePacket = new ToUserMessageResponsePacket();
        responsePacket.setFromUserId(session.getUserId());
        responsePacket.setFromUserName(session.getUsername());
        responsePacket.setMessage(msg.getMessage());

        log.info("send msg to {}: {}", toUserId, responsePacket.getMessage());
        toUserChannel.writeAndFlush(responsePacket);
    }
}
