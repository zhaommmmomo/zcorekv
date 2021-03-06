package com.zmm.zcorekv.api;

import com.zmm.zcorekv.lsm.LSM;
import com.zmm.zcorekv.lsm.LSMOptions;
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
    private String dbDir = System.getProperty("user.dir") + "\\db";

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

    public DBImpl(DBOptions options) {
        requireNonNull(options, "options is null");
        this.options = options;
        init();
    }

    public DBImpl(DBOptions options, String dbDir) {
        requireNonNull(options, "options is null");
        this.options = options;
        if (dbDir != null && dbDir.length() > 0) {
            this.dbDir = dbDir;
        }
        init();
    }

    /**
     * 初始化方法，负责加载启动等操作
     */
    private void init() {
        // 初始化LSM
        lsm = new LSM(new LSMOptions().setDBDir(dbDir));

        // 初始化vlog
        valueLog = new ValueLog();

        // 初始化统计信息
        stats = new Stats();
    }

    @Override
    public boolean set(byte[] key, byte[] value) {
        // 检测value是否大于某个阈值
        // 如果大于，写入ValueLog
        // 否则正常写入

        // 写WAL

        return lsm.set(new Entry().setKey(key).setValue(value));
    }

    @Override
    public byte[] get(byte[] key) {
        return lsm.get(key);
    }

    @Override
    public boolean del(byte[] key) {
        return lsm.del(key);
    }

    @Override
    public DBIterator newIterator() {
        return lsm.iterator();
    }

    @Override
    public Stats info() {
        return stats;
    }

    @Override
    public void close() {

    }
}
