package com.zmm.zcorekv.lsm;

import com.zmm.zcorekv.api.DBIterator;

/**
 * @author zmm
 * @date 2021/11/28 19:34
 */
public class Iterator implements DBIterator {

    @Override
    public byte[] next() {
        return new byte[0];
    }

    @Override
    public boolean valid() {
        return false;
    }

    @Override
    public void rewind() {

    }

    @Override
    public void close() {

    }
}
