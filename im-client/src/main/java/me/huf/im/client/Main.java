package me.huf.im.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import me.huf.im.client.console.ConsoleThread;
import me.huf.im.client.handler.*;
import me.huf.im.common.handler.PacketDecoder;
import me.huf.im.common.handler.PacketEncoder;
import me.huf.im.common.handler.Spliter;
import me.huf.im.common.utils.NumberUtils;

import java.util.concurrent.TimeUnit;

@Slf4j
public class Main {
    private final static int MAX_RETRY = 5;

    public static void main(String[] args) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new Spliter())
                                .addLast(new PacketDecoder())
                                .addLast(new LoginResponseHandler())
                                .addLast(new MessageResponseHandler())
                                .addLast(new CreateGroupResponseHandler())
                                .addLast(new LeaveGroupResponseHandler())


                                .addLast(new GotAddedIntoResponseHandler())
                                .addLast(new PacketEncoder());
                    }
                });

        connect(bootstrap, "localhost", 8080, MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                log.info("connect success");

                Channel channel = ((ChannelFuture) future).channel();
                ConsoleThread ct = new ConsoleThread(channel);
                ct.start();

                return;
            }

            if (retry == 0) {
                log.error("connect failed, retry times has been used up, give up reconnect");
                return;
            }

            int order = MAX_RETRY - retry + 1;
            long delay = 1 << order;
            log.error("connect failed, {} retry after {} seconds", NumberUtils.ordinal(order), delay);

            bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
        });
    }
}
