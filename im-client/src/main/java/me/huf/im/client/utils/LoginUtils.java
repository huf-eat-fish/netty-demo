package me.huf.im.client.utils;

import io.netty.channel.Channel;
import me.huf.im.common.constant.AttrKey;

import java.util.Objects;

public class LoginUtils {
    public static void setLogin(Channel channel) {
        channel.attr(AttrKey.LOGIN).set(Boolean.TRUE);
    }

    public static boolean hasLogin(Channel channel) {
        return Objects.equals(channel.attr(AttrKey.LOGIN).get(), Boolean.TRUE);
    }

    public static void setId(Channel channel, String id) {
        channel.attr(AttrKey.ID).set(id);
    }

    public static String getId(Channel channel) {
        return channel.attr(AttrKey.ID).get();
    }

    public static boolean isLogging(Channel channel) {
        return Boolean.TRUE.equals(channel.attr(AttrKey.LOGGING_IN).get());
    }
}
