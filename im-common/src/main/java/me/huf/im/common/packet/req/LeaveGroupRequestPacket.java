package me.huf.im.common.packet.req;

import lombok.Data;
import me.huf.im.common.packet.Packet;

@Data
public class LeaveGroupRequestPacket extends Packet {
    private String groupId;
}
