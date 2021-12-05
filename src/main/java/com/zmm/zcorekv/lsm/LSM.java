package com.zmm.zcorekv.lsm;

import com.zmm.zcorekv.utils.Closer;
import com.zmm.zcorekv.utils.Entry;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author zmm
 * @date 2021/11/28 19:34
 */
public class LSM {

    /** 新的内存表，可读可写 */
    private final MemTable memTable;

    /** 还未写入磁盘的内存表，只能读 */
    private final List<MemTable> immutables = new CopyOnWriteArrayList<>();

    /** 负责管理磁盘中的文件 */
    private final LevelManager levelManager = new LevelManager();
    private LSMOptions options;

    /**  */
    private Closer closer;
    private int maxMemFID;

    public LSM(LSMOptions options) {
        // 这里可以根据options传入的内存表参数来指定内存表结构
        this.memTable = new SkipList();
        this.options = options;
        init();
    }

    /**
     * 初始化LSM及其组件
     */
    private void init() {
        // 初始化MemTable组件
        memTable.init(options);
        // 初始化LevelManager
        levelManager.init(options);
    }

    public boolean set(Entry entry) {
        return memTable.set(entry);
    }

    public byte[] get(byte[] key) {
        return memTable.get(key);
    }

    public boolean del(byte[] key) {
        return memTable.del(key);
    }
}
