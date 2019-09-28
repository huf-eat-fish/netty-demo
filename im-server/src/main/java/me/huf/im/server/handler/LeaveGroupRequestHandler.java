package me.huf.im.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import me.huf.im.common.packet.req.LeaveGroupRequestPacket;
import me.huf.im.common.packet.resp.LeaveGroupResponsePacket;
import me.huf.im.server.utils.SessionUtils;

public class LeaveGroupRequestHandler extends SimpleChannelInboundHandler<LeaveGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LeaveGroupRequestPacket msg) throws Exception {
        String groupId = msg.getGroupId();
        ChannelGroup group = SessionUtils.getChannelGroup(groupId);

        // group doesn't exist
        if (group == null) {
            ctx.channel().writeAndFlush(LeaveGroupResponsePacket.failed("Chat group does not exist"));
            return;
        }

        if (!group.contains(ctx.channel())) {
            ctx.channel().writeAndFlush(LeaveGroupResponsePacket.failed("You are not in the chat group"));
            return;
        }

        group.remove(ctx.channel());
        ctx.channel().writeAndFlush(LeaveGroupResponsePacket.succeed("You successfully quit the group"));
    }
}
