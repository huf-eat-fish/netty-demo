package me.huf.im.common.packet.resp;

import lombok.Data;
import me.huf.im.common.packet.Packet;

@Data
public class LoginResponsePacket extends Packet {
    private Boolean success;
    private String info;
    private String id;
}
