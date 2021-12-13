package com.zmm.zcorekv;

import com.google.protobuf.ByteString;
import com.zmm.zcorekv.pb.Data;
import net.smacke.jaydio.DirectRandomAccessFile;
import net.smacke.jaydio.align.ByteChannelAligner;
import net.smacke.jaydio.align.DirectIoByteChannelAligner;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @author zmm
 * @date 2021/12/9 10:42
 */
public class DirectIOTest {

    @Test
    public void directTest() throws IOException {
        int bufferSize = 1 << 23;
        byte[] buf = null;
        DirectIoByteChannelAligner channel = new DirectIoByteChannelAligner(null, null, null);
        channel.size();
        channel.flush();
    }
}
