package com.zmm.zcorekv.lsm;

import com.zmm.zcorekv.file.Manifest;
import com.zmm.zcorekv.file.SSTable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author zmm
 * @date 2021/11/28 19:46
 */
public class LevelManager {

    /** 文件名编号 */
    private final AtomicLong fId = new AtomicLong(0);

    /** 统一管理某一层的SST */
    private final List<LevelHandle> levelHandle = new CopyOnWriteArrayList<>();

    /** 缓存 */
    private final Cache cache = new Cache();

    /** 用来存储那些SST在那一层的信息 */
    private Manifest manifest;

    private LSMOptions options;

    /** 刷盘线程 */
    private final Flush flusher = new Flush();

    public void init(LSMOptions options) {
        this.options = options;
        // 初始化manifest

        // 初始化levelHandle

        // 初始化cache
    }

    /**
     * 在磁盘中查询指定key
     * @param key           key
     * @return              value
     */
    public byte[] get(byte[] key) {
        return cache.get(key);
    }

    /**
     * 刷盘方法
     * @param memTable      内存表
     */
    public void flush(MemTable memTable) {
        // 分配文件名
        String filename = options.dbDir + fId.getAndIncrement() + ".sst";

        // 构建SST文件
//        Table table = new Table(filename, memTable, options);
        SSTable ssTable = new SSTable(filename, memTable, options);

        // 更新levelHandle和manifest

        // 添加异步flash任务
        flusher.addTask(ssTable);
    }

}
