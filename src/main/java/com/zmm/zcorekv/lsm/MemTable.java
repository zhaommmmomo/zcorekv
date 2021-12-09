package com.zmm.zcorekv.lsm;

import com.zmm.zcorekv.api.DBIterator;
import com.zmm.zcorekv.utils.Entry;

/**
 * 内存表抽象类
 * @author zmm
 * @date 2021/11/28 19:46
 */
public abstract class MemTable {

    public void init(LSMOptions options) {
        // 初始化结构

        // 加载数据
        recovery();
    }

    public boolean set(Entry entry) {
        return false;
    }

    public byte[] get(byte[] key){
        return null;
    }

    public boolean del(byte[] key) {
        return false;
    }

    public long size() {
        return 0;
    }

    public DBIterator iterator() {
        return null;
    }

    public void recovery() {}

    public void close() {}
}
