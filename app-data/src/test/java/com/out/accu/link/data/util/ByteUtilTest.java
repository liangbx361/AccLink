package com.out.accu.link.data.util;

import org.junit.Assert;
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

    @Test
    public void cmd() throws Exception {
        byte[] cmd = new byte[]{(byte) 0xa4, 0x08};
        System.out.println(cmd[0]+cmd[1]);
    }

    @Test
    public void formatInt() throws Exception {
        byte[] intByte = new byte[]{(byte) 0x20, (byte) 0xa1, 0x07, 0x00};
        int value = ByteUtil.getInt(intByte, 0);
        Assert.assertEquals(500000, value);
        Assert.assertArrayEquals(intByte, ByteUtil.intToByte(value));
    }

    @Test
    public void foratLong() throws Exception {
        byte[] longByte = new byte[]{(byte) 0x20, (byte) 0xa1, 0x07, 0x00, 0x00, 0x00, 0x00, 0x00};
        long value = ByteUtil.getLong(longByte, 0);
        Assert.assertEquals(500000, value);
        Assert.assertArrayEquals(longByte, ByteUtil.longToByte(value));
    }
}