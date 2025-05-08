package com.wccg.well_c_code_git_backend.global.util.random;

import java.util.concurrent.ThreadLocalRandom;

public class RandomNumberGenerator {

    private RandomNumberGenerator() {

    }

    public static int generate(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }
}
