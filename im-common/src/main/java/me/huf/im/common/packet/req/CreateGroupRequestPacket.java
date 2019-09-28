package me.huf.im.common.packet.req;

import lombok.Data;
import me.huf.im.common.packet.Packet;

import java.util.List;

@Data
public class CreateGroupRequestPacket extends Packet {
    private List<String> userIds;
    private String groupName;
}
