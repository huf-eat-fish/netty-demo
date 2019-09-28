package me.huf.im.common.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import me.huf.im.common.constant.MagicNumber;

public class Spliter extends LengthFieldBasedFrameDecoder {
    private final static int LENGTH_FIELD_OFFEST = 4 + 1 + 1 + 1;
    private final static int LENGTH_FIELD_LENGTH = 4;

    public Spliter() {
        super(Integer.MAX_VALUE, LENGTH_FIELD_OFFEST, LENGTH_FIELD_LENGTH);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        if (in.readableBytes() < 4) {
            return null;
        }

        if (in.getInt(in.readerIndex()) != MagicNumber.MAGIC_NUMBER) {
            ctx.channel().close();
            return null;
        }

        return super.decode(ctx, in);
    }
}
