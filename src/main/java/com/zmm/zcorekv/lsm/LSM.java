package com.zmm.zcorekv.lsm;

import com.zmm.zcorekv.api.DBIterator;
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
    private MemTable memTable;

    /** 还未写入磁盘的内存表，只能读 */
    private List<MemTable> immutables = new CopyOnWriteArrayList<>();

    /** 负责管理磁盘中的文件 */
    private final LevelManager levelManager = new LevelManager();
    private LSMOptions options;

    /**  */
    private Closer closer;


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
        // 开启merge

    }

    public boolean set(Entry entry) {
        if (memTable.size() >= options.memTableSize) {
            // 如果内存表大小达到阈值，触发flush
            immutables.add(memTable);
            memTable = new SkipList();
        }
        boolean flag = memTable.set(entry);

        // 查看是否需要刷盘
        immutables.forEach(levelManager::flush);

        // 释放immutable
        for (MemTable immutable : immutables) {
            immutable.close();
        }
        return flag;
    }

    /**
     * 查询指定key。
     * 1. 先从memtable中找。找到了就直接返回
     * 2. 如果没找到就去immutables（只是不可写，但是可读）中找。
     * 3. 如果还没找到就去磁盘中找
     * @param key       key
     * @return          value
     */
    public byte[] get(byte[] key) {

        // 1.先从内存中找
        byte[] res = memTable.get(key);
        if (res != null) {
            return res;
        }

        // 2.如果没找到去immutables中找
        for (MemTable immutable : immutables) {
            res = immutable.get(key);
            if (res != null) {
                return res;
            }
        }

        // 3.如果还没找到就去磁盘中找
        return levelManager.get(key);
    }

    /**
     * 删除指定key
     * @param key       key
     * @return          true / false
     */
    public boolean del(byte[] key) {
        return memTable.del(key);
    }

    /**
     * 迭代器
     * @return          迭代器
     */
    public DBIterator iterator() {
        return memTable.iterator();
    }
}
