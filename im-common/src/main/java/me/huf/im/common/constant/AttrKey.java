package me.huf.im.common.constant;

import io.netty.util.AttributeKey;
import me.huf.im.common.bean.Session;

public interface AttrKey {
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
    AttributeKey<Boolean> LOGGING_IN = AttributeKey.newInstance("loggingIn");
    AttributeKey<String> ID = AttributeKey.newInstance("id");

    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");
}
