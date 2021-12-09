package com.zmm.zcorekv.utils;

/**
 * @author zmm
 * @date 2021/11/28 19:44
 */
public class Entry {

    public byte[] key;
    public byte[] value;
    public long expiresAt;
    public long version;
    public int offset;
    /** 头部的长度 */
    public int hLen;
    public long valThreshold;

    public Entry() {}

    public Entry(byte[] key, byte[] value) {
        this.key = key;
        this.value = value;
    }

    public Entry(byte[] key, byte[] value, long expiresAt,
                 long version, int offset, int hLen, long valThreshold) {
        this.key = key;
        this.value = value;
        this.expiresAt = expiresAt;
        this.version = version;
        this.offset = offset;
        this.hLen = hLen;
        this.valThreshold = valThreshold;
    }

    public long size() {
        return key.length + value.length;
    }

    public Entry setKey(byte[] key) {
        this.key = key;
        return this;
    }

    public Entry setValue(byte[] value) {
        this.value = value;
        return this;
    }
}
