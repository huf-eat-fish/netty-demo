package me.huf.im.common.packet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import me.huf.im.common.constant.Command;

import static me.huf.im.common.constant.MagicNumber.MAGIC_NUMBER;
import static me.huf.im.common.constant.MagicNumber.VERSION;

@Data
public abstract class Packet {
    @JsonIgnore
    private transient byte version = VERSION;
    @JsonIgnore
    private transient final int magicNumber = MAGIC_NUMBER;
    @JsonIgnore
    byte getCommand() {
        return Command.CLASS_COMMAND_MAP.get(getClass());
    }
}
