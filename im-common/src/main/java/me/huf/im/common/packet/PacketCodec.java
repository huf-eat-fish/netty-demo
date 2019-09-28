package me.huf.im.common.packet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import me.huf.im.common.constant.Command;
import me.huf.im.common.constant.MagicNumber;
import me.huf.im.common.serializer.Serializer;

/**
 * Message format:
 * <pre>
 *  |name  |magic number|version|serialize algorithm|command|body length|body|
 *  |length|           4|      1|                  1|      1|          1|    |
 * </pre>
 */
public class PacketCodec {
    public final static PacketCodec INSTANCE = new PacketCodec();

    private final static Serializer DEFAULT_SERIALIZER = Serializer.JSON_SERIALIZER;
    private final static ByteBufAllocator DEFAULT_ALLOCATOR = ByteBufAllocator.DEFAULT;

    public ByteBuf encode(Packet packet) {
        return encode(packet, DEFAULT_ALLOCATOR, DEFAULT_SERIALIZER);
    }

    public ByteBuf encode(Packet packet, ByteBufAllocator allocator) {
        return encode(packet, allocator, DEFAULT_SERIALIZER);
    }

    public ByteBuf encode(Packet packet, Serializer serializer) {
        return encode(packet, DEFAULT_ALLOCATOR, serializer);
    }

    public ByteBuf encode(Packet packet, ByteBufAllocator allocator, Serializer serializer) {
        ByteBuf buffer = allocator.buffer();
        byte[] bytes = serializer.serialize(packet);

        // magic number
        buffer.writeInt(packet.getMagicNumber());
        // version
        buffer.writeByte(packet.getVersion());
        // serialize algorithm
        buffer.writeByte(serializer.getSerializerAlgorithm());
        buffer.writeByte(packet.getCommand());

        buffer.writeInt(bytes.length);
        buffer.writeBytes(bytes);

        return buffer;
    }

    public void encode(Packet packet, ByteBuf buffer) {
        encode(packet, buffer, DEFAULT_SERIALIZER);
    }

    public void encode(Packet packet, ByteBuf buffer, Serializer serializer) {
        byte[] bytes = serializer.serialize(packet);

        // magic number
        buffer.writeInt(packet.getMagicNumber());
        // version
        buffer.writeByte(packet.getVersion());
        // serialize algorithm
        buffer.writeByte(serializer.getSerializerAlgorithm());
        buffer.writeByte(packet.getCommand());

        buffer.writeInt(bytes.length);
        buffer.writeBytes(bytes);
    }

    public Packet decode(ByteBuf byteBuf) {
        // valid magic number
        int magicNumber = byteBuf.readInt();
        if (magicNumber != MagicNumber.MAGIC_NUMBER) throw new RuntimeException();
        // valid version
        byte version = byteBuf.readByte();
        if (version != MagicNumber.VERSION) throw new RuntimeException();

        byte algorithm = byteBuf.readByte();
        byte command = byteBuf.readByte();
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = Command.COMMAND_CLASS_MAP.get(command);
        Serializer serializer = Serializer.fromAlgorithm(algorithm);

        if (requestType == null || serializer == null) {
            return null;
        }

        return serializer.deserialize(requestType, bytes);
    }
}
