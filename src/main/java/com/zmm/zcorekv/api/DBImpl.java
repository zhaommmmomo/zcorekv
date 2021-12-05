package com.zmm.zcorekv.api;

import com.zmm.zcorekv.lsm.LSM;
import com.zmm.zcorekv.lsm.LSMOptions;
import com.zmm.zcorekv.lsm.Merge;
import com.zmm.zcorekv.utils.Entry;
import com.zmm.zcorekv.valuelog.ValueLog;

import java.io.File;
import static java.util.Objects.requireNonNull;

/**
 * @author zmm
 * @date 2021/11/28 19:45
 */
public class DBImpl implements DB{

    /** 工作目录，默认会在 工作目录下的/db */
    private final File dbDir;

    /** 一些配置 */
    private final DBOptions options;
    private WriteOptions writeOptions;
    private ReadOptions readOptions;

    /** LSM树组件 */
    private LSM lsm;

    /** kv分离 */
    private ValueLog valueLog;

    /** 一些状态信息 */
    private Stats stats;

    public DBImpl(DBOptions options, File dbDir) {
        requireNonNull(options, "options is null");
        this.options = options;
        this.dbDir = dbDir != null ? dbDir :
                new File(System.getProperty("user.dir") + "\\db");
        init();
    }

    /**
     * 初始化方法，负责加载启动等操作
     */
    private void init() {
        // 初始化LSM
        lsm = new LSM(new LSMOptions().setDBDir(dbDir));

        // 初始化vlog

        // 初始化统计信息

        // 启动sst压缩合并
        Merge.start();

        // 启动value gc

        // 启动stats统计
    }

    @Override
    public boolean set(byte[] key, byte[] value) {
        return lsm.set(new Entry().setKey(key).setValue(value));
    }

    @Override
    public byte[] get(byte[] key) {
        return null;
    }

    @Override
    public boolean del(byte[] key) {
        return false;
    }

    @Override
    public DBIterator newIterator() {
        return null;
    }

    @Override
    public Stats info() {
        return null;
    }

    @Override
    public void close() {

    }
}
