package com.zmm.zcorekv.utils;

import java.util.Random;

/**
 * @author zmm
 * @date 2021/11/28 19:45
 */
public class Rand {

    private static final Random random = new Random();

    public static int rand(int num) {
        return random.nextInt(num);
    }
}
