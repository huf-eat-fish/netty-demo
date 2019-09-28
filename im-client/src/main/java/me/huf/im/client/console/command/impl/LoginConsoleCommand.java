package me.huf.im.client.console.command.impl;

import io.netty.channel.Channel;
import me.huf.im.client.console.Console;
import me.huf.im.client.console.command.ConsoleCommand;
import me.huf.im.common.constant.AttrKey;
import me.huf.im.common.packet.req.LoginRequestPacket;


public class LoginConsoleCommand implements ConsoleCommand {
    @Override
    public String command() {
        return "login";
    }

    @Override
    public void exec(Channel channel) {
        Console.print("Please enter a username:");
        String username = Console.nextInputLine();
        Console.print("Please enter a password:");
        String password = Console.nextInputLine();

        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUsername(username);
        loginRequestPacket.setPassword(password);

        channel.attr(AttrKey.LOGGING_IN).set(true);
        channel.writeAndFlush(loginRequestPacket);
    }
}
