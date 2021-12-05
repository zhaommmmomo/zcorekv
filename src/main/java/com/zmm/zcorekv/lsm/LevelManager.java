package com.zmm.zcorekv.lsm;

import com.zmm.zcorekv.file.Manifest;

/**
 * @author zmm
 * @date 2021/11/28 19:46
 */
public class LevelManager {

    /** 统一管理某一层的SST */
    private LevelHandle levelHandle;

    /** 对 */
    private Cache cache;

    /** 用来存储那些SST在那一层的信息 */
    private Manifest manifest;

    public void init(LSMOptions options) {
        // 初始化manifest

        // 初始化levelHandle

        // 初始化cache
    }
}
