package me.huf.im.common.packet.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.huf.im.common.packet.Packet;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class LeaveGroupResponsePacket extends Packet {
    private Boolean success;
    private String info;

    public static LeaveGroupResponsePacket failed(String info) {
        return new LeaveGroupResponsePacket(false, info);
    }

    public static LeaveGroupResponsePacket succeed(String info) {
        return new LeaveGroupResponsePacket(true, info);

    }
}
