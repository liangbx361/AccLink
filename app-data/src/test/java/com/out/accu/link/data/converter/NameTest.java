package com.out.accu.link.data.converter;

import com.out.accu.link.data.util.ByteUtil;

import org.junit.Test;

import java.io.UnsupportedEncodingException;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/11/16
 */

public class NameTest {

    @Test
    public void name() {
        byte[] names = new byte[]{(byte) 0xd5, (byte) 0xc5, (byte) 0xc8, (byte) 0xfd};
        String name = new String(names, java.nio.charset.Charset.forName("GB2312"));
        System.out.println(name);
    }

    @Test
    public void gb2312Name() {
        String name = new String("张三");

        try {
            String gbName = new String(name.getBytes("GB2312"), "GB2312");
            System.out.println(gbName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void time() {
        byte[] timeStart = new byte[]{0x00, 0x00, 0x00, 0x00, 0x40, 0x06, (byte) 0xe5, 0x40};
        byte[] timeEnd = new byte[]{0x00, 0x00, 0x00, 0x00, 0x60, 0x06, (byte) 0xe5, 0x40};
        long timeSL = ByteUtil.getLong(timeStart, 0);
        long timeEL = ByteUtil.getLong(timeEnd, 0);

        byte[] tmp = ByteUtil.longToByte(timeEL);
        System.out.println(timeSL + "-" + timeEL);


    }

}
