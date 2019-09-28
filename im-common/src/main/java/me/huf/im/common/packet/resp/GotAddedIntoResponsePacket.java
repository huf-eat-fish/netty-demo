package me.huf.im.common.packet.resp;

import lombok.Data;
import me.huf.im.common.packet.Packet;

import java.util.List;

@Data
public class GotAddedIntoResponsePacket extends Packet {
    private String groupId;
    private List<String> nameList;
}
