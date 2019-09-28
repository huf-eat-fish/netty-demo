package me.huf.im.server.utils;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import me.huf.im.common.bean.Session;
import me.huf.im.common.constant.AttrKey;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionUtils {
    private final static Map<String, Channel> ID_CHANNEL_MAP = new ConcurrentHashMap<>();
    private final static Map<String, ChannelGroup> ID_CHANNEL_GROUP_MAP = new ConcurrentHashMap<>();

    public static void bindSession(Session session, Channel channel) {
        ID_CHANNEL_MAP.put(session.getUserId(), channel);
        channel.attr(AttrKey.SESSION).set(session);
    }

    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            ID_CHANNEL_MAP.remove(getSession(channel).getUserId());
            channel.attr(AttrKey.SESSION).set(null);
        }
    }

    public static boolean hasLogin(Channel channel) {
        return channel.hasAttr(AttrKey.SESSION);
    }

    public static Session getSession(Channel channel) {
        return channel.attr(AttrKey.SESSION).get();
    }

    public static Channel getChannel(String userId) {
        return ID_CHANNEL_MAP.get(userId);
    }

    public static void bindChannelGroup(String groupId, ChannelGroup channelGroup) {
        ID_CHANNEL_GROUP_MAP.put(groupId, channelGroup);
    }

    public static ChannelGroup getChannelGroup(String groupId) {
        return ID_CHANNEL_GROUP_MAP.get(groupId);
    }
}
