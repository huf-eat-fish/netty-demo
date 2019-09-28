package me.huf.im.client.console;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Console {
    public static final Scanner sc = new Scanner(System.in);

    public static void print(String msg) {
        System.out.println(getNow() + " " + msg);
    }

    public static String nextInputLine() {
        return sc.nextLine();
    }


    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static String getNow() {
        return LocalDateTime.now().format(formatter);
    }
}
