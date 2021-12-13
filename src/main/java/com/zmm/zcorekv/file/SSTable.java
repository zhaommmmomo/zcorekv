package com.zmm.zcorekv.file;

import com.zmm.zcorekv.api.DBIterator;
import com.zmm.zcorekv.lsm.LSMOptions;
import com.zmm.zcorekv.lsm.MemTable;
import com.zmm.zcorekv.pb.Block;

/**
 * 一个按key排序的文件
 *
 * @author zmm
 * @date 2021/11/28 19:51
 */
public class SSTable {

    /** filename */
    private final String filename;

    /** memory table */
    private final MemTable memTable;

    /** options */
    private final LSMOptions options;

    public SSTable(String filename, MemTable memTable, LSMOptions options) {
        this.filename = filename;
        this.memTable = memTable;
        this.options = options;
        build();
    }

    private void build() {
        //
        DBIterator iterator = memTable.iterator();
        Block.Builder builder = Block.newBuilder();
        byte[] entry;
        while ((entry = iterator.next()) != null) {

        }

    }

    public String getFilename() {
        return filename;
    }

    public MemTable getMemTable() {
        return memTable;
    }

    public LSMOptions getOptions() {
        return options;
    }
}
