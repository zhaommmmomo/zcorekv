package com.zmm.zcorekv.lsm;

import com.zmm.zcorekv.utils.Const;

import java.io.File;
import static java.util.Objects.requireNonNull;

/**
 * @author zmm
 * @date 2021/11/28 19:34
 */
public class LSMOptions {

    /** 存储目录 */
    public File dbDir;
    /** 内存表大小 */
    public long memTableSize = 1024;
    /** sst最大值 */
    public long sstableMaxSize = 1 << 30;
    /** 块大小 */
    public int blockSize = 4 * 1024;
    /** 布隆过滤器错误概率 */
    public float bloomFalsePositive;

    /** SkipList最大层数 */
    public int maxLevel = Const.DEFAULT_SKIP_LIST_LEVEL;

    public LSMOptions() {

    }

    public LSMOptions setDBDir(File dbDir) {
        requireNonNull(dbDir, "dbDir is null");
        this.dbDir = dbDir;
        return this;
    }

    public LSMOptions setMemTableSize(long memTableSize) {
        if (memTableSize > 1024  && memTableSize < 20 * 1024)
            this.memTableSize = memTableSize;
        return this;
    }

    public LSMOptions sstableMaxSize(long sstableMaxSize) {

        return this;
    }
}
