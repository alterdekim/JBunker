package com.alterdekim.javabot.util;

public class PseudoRandom {
    private final double seed;

    private double i = 0;

    public PseudoRandom( double seed ) {
        assert seed > 0 && seed < 1;
        this.seed = seed;
    }

    public PseudoRandom() {
        this.seed = 1;
    }

    public double next() {
        double f = ((Math.cos(3d * i) * Math.cos(3d * i) * Math.sin(3d * i)) * 1.3333d * Math.cos(i) * Math.cos(i)) + 0.5d;
        i += seed;
        i = i % 6.2d;
        return f;
    }

    public int nextInt(int bound) {
        return (int) Math.floor(next() * ((double) bound));
    }
}
