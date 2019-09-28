package me.huf.im.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import me.huf.im.common.packet.req.CreateGroupRequestPacket;
import me.huf.im.common.packet.resp.CreateGroupResponsePacket;
import me.huf.im.common.packet.resp.GotAddedIntoResponsePacket;
import me.huf.im.common.utils.IDUtils;
import me.huf.im.server.utils.SessionUtils;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket msg) throws Exception {
        List<String> userIds = msg.getUserIds();
        userIds.add(SessionUtils.getSession(ctx.channel()).getUserId());
        List<Channel> channels = userIds.stream()
                .distinct()
                .map(SessionUtils::getChannel)
                .filter(Objects::nonNull)
                .filter(SessionUtils::hasLogin)
                .collect(toList());

        // 没有其他人在线
        if (channels.size() == 1) {
            CreateGroupResponsePacket createGroupResponsePacket = new CreateGroupResponsePacket();
            createGroupResponsePacket.setSuccess(false);
            createGroupResponsePacket.setInfo("there must be at least one other online user in the group");
            ctx.channel().writeAndFlush(createGroupResponsePacket);
            return;
        }

        String groupId = IDUtils.randomId();

        ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());
        channelGroup.addAll(channels);
        SessionUtils.bindChannelGroup(groupId, channelGroup);

        List<String> nameList = channels.stream()
                .map(ch -> SessionUtils.getSession(ch).getUsername())
                .collect(toList());

        CreateGroupResponsePacket createGroupResponsePacket = new CreateGroupResponsePacket();
        createGroupResponsePacket.setSuccess(true);
        createGroupResponsePacket.setInfo("create group success");
        createGroupResponsePacket.setId(groupId);
        createGroupResponsePacket.setNameList(nameList);

        ctx.channel().writeAndFlush(createGroupResponsePacket);


        GotAddedIntoResponsePacket gotAddedIntoResponsePacket = new GotAddedIntoResponsePacket();
        gotAddedIntoResponsePacket.setGroupId(groupId);
        gotAddedIntoResponsePacket.setNameList(nameList);

        channelGroup.writeAndFlush(gotAddedIntoResponsePacket);
    }
}
