package me.huf.im.client.console.command.impl;

import io.netty.channel.Channel;
import me.huf.im.client.console.Console;
import me.huf.im.client.console.command.ConsoleCommand;
import me.huf.im.common.packet.req.CreateGroupRequestPacket;

import java.util.Arrays;

public class CreateGroupConsoleCommand implements ConsoleCommand {
    @Override
    public String command() {
        return "createGroup";
    }

    @Override
    public void exec(Channel channel) {
        Console.print("Please enter a group name:");
        String groupName = Console.nextInputLine();
        Console.print("Please enter some userId separated by commas:");
        String ids = Console.nextInputLine();
        String[] userIds = ids.split(", ?");

        CreateGroupRequestPacket createGroupRequestPacket = new CreateGroupRequestPacket();
        createGroupRequestPacket.setUserIds(Arrays.asList(userIds));
        createGroupRequestPacket.setGroupName(groupName);

        channel.writeAndFlush(createGroupRequestPacket);
    }
}
