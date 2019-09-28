package me.huf.im.common.utils;

import java.util.UUID;

public class IDUtils {
    public static String randomId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
