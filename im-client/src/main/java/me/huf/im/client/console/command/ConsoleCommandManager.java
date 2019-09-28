package me.huf.im.client.console.command;

import io.netty.channel.Channel;
import me.huf.im.client.console.Console;
import me.huf.im.client.console.command.impl.CreateGroupConsoleCommand;
import me.huf.im.client.console.command.impl.LeaveGroupConsoleCommand;
import me.huf.im.client.console.command.impl.LoginConsoleCommand;
import me.huf.im.client.console.command.impl.SendToUserConsoleCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

public class ConsoleCommandManager implements ConsoleCommand {
    public final Map<String, ConsoleCommand> COMMAND_MAP;

    {
        List<ConsoleCommand> commands = new ArrayList<>();
        commands.add(new LoginConsoleCommand());
        commands.add(new SendToUserConsoleCommand());
        commands.add(new CreateGroupConsoleCommand());
        commands.add(new LeaveGroupConsoleCommand());

        COMMAND_MAP = commands.stream().collect(toMap(ConsoleCommand::command, Function.identity()));
    }

    @Override
    public String command() {
        return null;
    }

    @Override
    public void exec(Channel channel) {
        String c = Console.nextInputLine();
        ConsoleCommand command = COMMAND_MAP.get(c);
        if (command == null) {
            Console.print("command not exist");
            return;
        }

        command.exec(channel);
    }
}
