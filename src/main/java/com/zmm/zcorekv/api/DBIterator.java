package com.zmm.zcorekv.api;

/**
 * @author zmm
 * @date 2021/11/28 19:47
 */
public interface DBIterator {
    byte[] next();
    boolean valid();
    void rewind();
    void close();
}
