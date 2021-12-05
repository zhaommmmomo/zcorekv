package com.zmm.zcorekv.lsm;

import java.util.*;

import com.zmm.zcorekv.utils.Entry;
import org.junit.Test;

/**
 * @author zmm
 * @date 2021/12/5 20:02
 */
public class SkipListTest {

    @Test
    public void curdTest() {
        SkipList skipList = new SkipList();

        Entry entry1 = new Entry("key1".getBytes(), "val1".getBytes());
        assert skipList.set(entry1);
        assert Arrays.equals("val1".getBytes(), skipList.get("key1".getBytes()));

        Entry entry2 = new Entry("key2".getBytes(), "val2".getBytes());
        assert skipList.set(entry2);
        assert Arrays.equals("val2".getBytes(), skipList.get("key2".getBytes()));

        assert skipList.get("key".getBytes()) == null;

        assert skipList.set(new Entry("key1".getBytes(), "val11".getBytes()));
        assert Arrays.equals("val11".getBytes(), skipList.get("key1".getBytes()));
    }

    @Test
    public void benchmarkCRUDTest() {
        SkipList skipList = new SkipList();
        String key = "";
        String val = "";
        long time = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            key = "key" + i;
            val = "val" + i;
            //skipList.set(new Entry(key.getBytes(), val.getBytes()));
            //skipList.get(key.getBytes());
            assert skipList.set(new Entry(key.getBytes(), val.getBytes()));
            assert Arrays.equals(val.getBytes(), skipList.get(key.getBytes()));
        }
        System.out.println("time: " + (System.currentTimeMillis() - time));
    }

    @Test
    public void concurrentTest() {
        int n = 1000;
        SkipList skipList = new SkipList();

    }
}
