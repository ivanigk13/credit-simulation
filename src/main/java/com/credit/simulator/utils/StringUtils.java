package com.credit.simulator.utils;

public class StringUtils {
    private StringUtils() {}

    public static boolean isBlank(String string) {
        return string == null || string.isEmpty();
    }
}
