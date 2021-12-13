package com.zmm.zcorekv;

import com.zmm.zcorekv.api.DB;
import com.zmm.zcorekv.api.DBImpl;
import com.zmm.zcorekv.api.DBIterator;
import com.zmm.zcorekv.api.DBOptions;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;

/**
 * @author zmm
 * @date 2021/12/7 14:38
 */
public class DBTest {

    @Test
    public void dbTest() {
        DB db = null;
        try {
            db = new DBImpl(DBOptions.builder().build(), null);
            assert db.set("hello".getBytes(), "corekv".getBytes());
            assert Arrays.equals("corekv".getBytes(), db.get("hello".getBytes()));
            DBIterator iterator = db.newIterator();

            // for (iterator.rewind(); iterator.valid();) {
            //     System.out.println(new String(iterator.next()));
            // }
            //iterator.close();
            assert db.del("hello".getBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }
}
