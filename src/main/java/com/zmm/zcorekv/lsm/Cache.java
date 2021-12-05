package com.zmm.zcorekv.lsm;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 对磁盘文件的一些索引
 * @author zmm
 * @date 2021/11/28 19:34
 */
public class Cache {

    /** 索引 */
    private Map<byte[], Integer> index = new ConcurrentHashMap<>();

    /** 块数据 */
    private Map<byte[], Integer> block = new ConcurrentHashMap<>();
}
