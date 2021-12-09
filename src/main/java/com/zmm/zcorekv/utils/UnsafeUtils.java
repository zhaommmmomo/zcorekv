package com.zmm.zcorekv.utils;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author zmm
 * @date 2021/12/6 13:40
 */
public class UnsafeUtils {
    public static final Unsafe unsafe;
    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        //HashFunction hashFunction = Hashing.murmur3_32_fixed();
        //hashFunction.hashBytes(new byte[]{}).asInt();
        //hashFunction.newHasher().putBytes();
        int a = -1;
        float b = -0.3f;
        long c = -2l;
        double d = -2.1;
        System.out.println(Math.ceil(a));
        System.out.println(Math.ceil(b));
        System.out.println(Math.ceil(c));
        System.out.println(Math.ceil(d));
    }
}
