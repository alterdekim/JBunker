package com.alterdekim.javabot.components;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.apache.commons.math3.random.MersenneTwister;

@Component
@Slf4j
public class RandomComponent {

    private MersenneTwister random;

    public RandomComponent() {
        this.random = new MersenneTwister();
    }

    public int nextInt(int bound) {
        return random.nextInt(bound);
    }

    public double nextDouble() {
        return random.nextDouble();
    }
}
