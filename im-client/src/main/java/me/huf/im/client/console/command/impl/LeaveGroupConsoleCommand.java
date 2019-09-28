package me.huf.im.client.console.command.impl;

import io.netty.channel.Channel;
import me.huf.im.client.console.Console;
import me.huf.im.client.console.command.ConsoleCommand;
import me.huf.im.common.packet.req.LeaveGroupRequestPacket;

public class LeaveGroupConsoleCommand implements ConsoleCommand {
    @Override
    public String command() {
        return "leaveGroup";
    }

    @Override
    public void exec(Channel channel) {
        Console.print("Please enter group id:");
        String groupId = Console.nextInputLine();

        LeaveGroupRequestPacket createGroupRequestPacket = new LeaveGroupRequestPacket();
        createGroupRequestPacket.setGroupId(groupId);

        channel.writeAndFlush(createGroupRequestPacket);
    }
}
