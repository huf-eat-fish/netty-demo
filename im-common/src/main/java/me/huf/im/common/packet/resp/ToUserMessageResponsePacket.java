package me.huf.im.common.packet.resp;

import lombok.Data;
import me.huf.im.common.packet.Packet;

@Data
public class ToUserMessageResponsePacket extends Packet {
    private String fromUserId;
    private String fromUserName;
    private String message;
}
