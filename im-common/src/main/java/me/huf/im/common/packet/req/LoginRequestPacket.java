package me.huf.im.common.packet.req;

import lombok.Data;
import me.huf.im.common.constant.Command;
import me.huf.im.common.packet.Packet;

@Data
public class LoginRequestPacket extends Packet {
    private String username;
    private String password;

    private byte command = Command.LOGIN_REQUEST;
}
