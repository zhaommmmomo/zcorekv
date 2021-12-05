package com.zmm.zcorekv.api;

/**
 * 暴露给用户的API
 * @author zmm
 * @date 2021/11/28 19:45
 */
public interface DB {
    boolean set(byte[] key, byte[] value);
    byte[] get(byte[] key);
    boolean del(byte[] key);
    DBIterator newIterator();
    Stats info();
    void close();
}
