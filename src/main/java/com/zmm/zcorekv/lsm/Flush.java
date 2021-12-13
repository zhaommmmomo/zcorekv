package com.zmm.zcorekv.lsm;

import com.zmm.zcorekv.file.SSTable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * @author zmm
 * @date 2021/11/28 19:46
 */
public class Flush implements Runnable{

    /** flush task queue */
    private final List<SSTable> queue = new CopyOnWriteArrayList<>();

    @Override
    public void run() {
        try {
            while (queue.size() != 0) {
                queue.forEach(ssTable -> {
                    // 执行刷盘逻辑

                });
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addTask(SSTable ssTable) {
        queue.add(ssTable);
    }
}
