package me.huf.im.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import me.huf.im.common.handler.PacketDecoder;
import me.huf.im.common.handler.PacketEncoder;
import me.huf.im.common.handler.Spliter;
import me.huf.im.server.handler.*;

@Slf4j
public class Main {
    private final static int START_PORT = 8080;

    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new Spliter())
                                .addLast(new PacketDecoder())
                                .addLast(new LoginRequestHandler())
                                .addLast(new AuthHandler())
                                .addLast(new MessageRequestHandler())
                                .addLast(new CreateGroupRequestHandler())
                                .addLast(new LeaveGroupRequestHandler())
                                .addLast(new PacketEncoder());
                    }
                });

        bind(serverBootstrap, START_PORT);
    }

    private static void bind(ServerBootstrap serverBootstrap, int port) {
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                log.info("start success at [{}]", port);
                return;
            }

            log.info("start failed at [{}], trying start at [{}]", port, port + 1);
            bind(serverBootstrap, port + 1);
        });
    }
}
