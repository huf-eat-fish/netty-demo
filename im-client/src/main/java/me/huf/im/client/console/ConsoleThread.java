package me.huf.im.client.console;

import io.netty.channel.Channel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.huf.im.client.console.command.ConsoleCommandManager;
import me.huf.im.client.utils.LoginUtils;

@AllArgsConstructor
@Slf4j
public class ConsoleThread extends Thread {

    private Channel channel;

    @Override
    public void run() {
        ConsoleCommandManager commandManager = new ConsoleCommandManager();
        while (!Thread.interrupted()) {
            if (!LoginUtils.hasLogin(channel)) {
                if (LoginUtils.isLogging(channel)) {
                    continue;
                }
                Console.print("You have not logged in yet");
                commandManager.COMMAND_MAP.get("login").exec(channel);
            } else {
                Console.print("please input command:");
                commandManager.exec(channel);
            }
        }
        log.info("user interrupted");
        channel.close();
    }
}
