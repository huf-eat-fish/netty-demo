package me.huf.im.client.console.command;

import io.netty.channel.Channel;

public interface ConsoleCommand {
    String command();

    void exec(Channel channel);
}
