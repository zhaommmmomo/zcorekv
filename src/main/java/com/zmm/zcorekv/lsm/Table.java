package com.zmm.zcorekv.lsm;

/**
 * @author zmm
 * @date 2021/11/28 19:45
 */
public class Table {

    private final String filename;

    private final MemTable memTable;

    private final LSMOptions options;

    public Table(String filename, MemTable memTable, LSMOptions options) {
        this.filename = filename;
        this.memTable = memTable;
        this.options = options;
        init();
    }

    private void init() {

    }

}
