package com.zmm.zcorekv.lsm;

/**
 * 合并操作
 * @author zmm
 * @date 2021/11/28 19:34
 */
public class Merge implements Runnable{

    /** 合并线程 */
    private static final Thread mergeThread = new Thread(new Merge(), "mergeThread");

    @Override
    public void run() {

    }

    /**
     * 开启合并
     */
    public static void start() {
        //mergeThread.start();
    }

    /**
     * 关闭合并
     */
    public static void close() {

    }
}
