package com.zmm.zcorekv.lsm;

import com.zmm.zcorekv.file.SSTable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 负责管理每一层的SST
 * @author zmm
 * @date 021/11/28 19:34
 */
public class LevelHandle {

    private final List<SSTable> ssTables = new CopyOnWriteArrayList<>();

    public boolean add(SSTable ssTable) {
        return ssTables.add(ssTable);
    }
}
