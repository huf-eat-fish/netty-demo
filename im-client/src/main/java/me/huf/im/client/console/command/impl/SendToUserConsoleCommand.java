package me.huf.im.client.console.command.impl;

import io.netty.channel.Channel;
import me.huf.im.client.console.Console;
import me.huf.im.client.console.command.ConsoleCommand;
import me.huf.im.common.packet.req.ToUserMessageRequestPacket;

public class SendToUserConsoleCommand implements ConsoleCommand {
    @Override
    public String command() {
        return "sendToUser";
    }

    @Override
    public void exec(Channel channel) {
        Console.print("Please enter a userId to send message:");
        String toUserId = Console.nextInputLine();
        Console.print("Please enter a message:");
        String line = Console.nextInputLine();

        ToUserMessageRequestPacket packet = new ToUserMessageRequestPacket();
        packet.setToUserId(toUserId);
        packet.setMessage(line);
        channel.writeAndFlush(packet);
    }
}
