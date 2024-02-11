package com.alterdekim.javabot.util;

public class Clamp {
    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}
