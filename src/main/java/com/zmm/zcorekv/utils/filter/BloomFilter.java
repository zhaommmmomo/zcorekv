package com.zmm.zcorekv.utils.filter;

import static com.zmm.zcorekv.utils.UnsafeUtils.unsafe;

/**
 * @author zmm
 * @date 2021/11/28 19:45
 */
public class BloomFilter extends AbstractFilter {

    private byte[] bits;
    private int bitsSize;
    private int hashCount;

    private long indices;
    private long ptr;

    public BloomFilter(long exceptElements, double falsePositiveRate) {
        // 计算数组大小
        bitsSize = (int) Math.ceil(-1 * exceptElements * Math.log(falsePositiveRate) / Math.pow(Math.log(2), 2));
        // 计算hash函数的数量
        hashCount = (int) Math.ceil(bitsSize / exceptElements * Math.log(2));
        if (hashCount < 1) {
            hashCount = 1;
        } else if (hashCount > 30) {
            hashCount = 30;
        }
        indices = (long) Math.ceil((double) exceptElements / 64);
        ptr = unsafe.allocateMemory(8L * indices);
        unsafe.setMemory(ptr, 8L * indices, (byte) 0);
        bits = new byte[bitsSize];
    }

    public int[] add(byte[] key) {
        int hash = hash(key);
        int nBits = key.length * (int) bitsSize;
        int[] filter = new int[nBits];
        for (byte b : key) {
            int delta = b >> 17 | b <<15;
            for (int i = 0; i < hash; i++) {
                int bitPos = b % nBits;
                filter[bitPos] = 1;
                b += delta;
            }
        }
        return filter;
    }

    public boolean mightContain() {
        return false;
    }

    public void dispose() {
        unsafe.freeMemory(ptr);
    }

    public int hash(byte[] key) {
        int seed = 0xbc9f1d34;
        int m = 0xc6a4a793;
        int h = seed ^ key.length * m;
        //for (; key.length >= 4; key = key[4:]) {

        //}
        return h;
    }
}
