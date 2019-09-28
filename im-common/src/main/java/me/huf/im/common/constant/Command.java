package me.huf.im.common.constant;

import me.huf.im.common.packet.Packet;
import me.huf.im.common.packet.req.CreateGroupRequestPacket;
import me.huf.im.common.packet.req.LeaveGroupRequestPacket;
import me.huf.im.common.packet.req.LoginRequestPacket;
import me.huf.im.common.packet.req.ToUserMessageRequestPacket;
import me.huf.im.common.packet.resp.*;

import java.util.HashMap;
import java.util.Map;

public interface Command {
    byte LOGIN_REQUEST = 1;
    byte LOGIN_RESPONSE = 2;
    byte MESSAGE_REQUEST = 3;
    byte MESSAGE_RESPONSE = 4;
    byte CREATE_GROUP_REQUEST = 5;
    byte CREATE_GROUP_RESPONSE = 6;
    byte LEAVE_GROUP_REQUEST = 7;
    byte LEAVE_GROUP_RESPONSE = 8;
    byte JOIN_GROUP_REQUEST = 9;
    byte JOIN_GROUP_RESPONSE = 10;
    byte GROUP_MEMBER_LIST_REQUEST = 11;
    byte GROUP_MEMBER_LIST_RESPONSE = 12;
    byte GROUP_MESSAGE_REQUEST = 13;
    byte GROUP_MESSAGE_RESPONSE = 14;
    byte LOGOUT_REQUEST = 15;
    byte LOGOUT_RESPONSE = 16;
    byte GOT_ADDED_INTO_RESPONSE = 17;

    // register command
    Map<Byte, Class<? extends Packet>> COMMAND_CLASS_MAP = new HashMap<Byte, Class<? extends Packet>>() {{
        put(LOGIN_REQUEST, LoginRequestPacket.class);
        put(LOGIN_RESPONSE, LoginResponsePacket.class);
        put(MESSAGE_REQUEST, ToUserMessageRequestPacket.class);
        put(MESSAGE_RESPONSE, ToUserMessageResponsePacket.class);
        put(CREATE_GROUP_REQUEST, CreateGroupRequestPacket.class);
        put(CREATE_GROUP_RESPONSE, CreateGroupResponsePacket.class);
        put(LEAVE_GROUP_REQUEST, LeaveGroupRequestPacket.class);
        put(LEAVE_GROUP_RESPONSE, LeaveGroupResponsePacket.class);


        put(GOT_ADDED_INTO_RESPONSE, GotAddedIntoResponsePacket.class);
    }};

    Map<Class<? extends Packet>, Byte> CLASS_COMMAND_MAP = new HashMap<Class<? extends Packet>, Byte>() {{
        COMMAND_CLASS_MAP.forEach((k, v) -> put(v, k));
    }};
}
