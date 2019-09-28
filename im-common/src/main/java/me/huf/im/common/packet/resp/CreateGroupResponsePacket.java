package me.huf.im.common.packet.resp;

import lombok.Data;
import me.huf.im.common.packet.Packet;

import java.util.List;

@Data
public class CreateGroupResponsePacket extends Packet {
    private Boolean success;
    private String info;
    private String id;
    private List<String> nameList;
}
