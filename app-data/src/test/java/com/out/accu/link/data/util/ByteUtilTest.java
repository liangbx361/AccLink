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

    @Test
    public void formatDouble() throws Exception {
        byte[] doubleByte = new byte[]{(byte) 0xC9, 0x76, (byte) 0xBE, (byte) 0x9F, 0x1A, (byte) 0xC7, 0x4B, 0x40};
        double value = ByteUtil.getDouble(doubleByte, 0);
        System.out.println(value);

        byte[] dValue = ByteUtil.doubleToByte(0.1234);
        System.out.println(ByteUtil.getDouble(dValue, 0));
    }
}