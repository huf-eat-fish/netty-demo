package me.huf.im.common.packet.req;

import lombok.Data;
import me.huf.im.common.packet.Packet;

@Data
public class ToUserMessageRequestPacket extends Packet {
    private String toUserId;
    private String message;
}
