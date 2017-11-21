package com.out.accu.link.data.util;

import org.junit.Test;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/11/21
 */
public class ByteUtilTest {
    @Test
    public void formatTime() throws Exception {
        byte[] timeByte = new byte[]{(byte) 0xe1,0x07,0x0b,0x15,0x07,0x0d,0x34,0x00};
        long time = ByteUtil.formatTime(timeByte, 0);
        System.out.println(time);
    }

}