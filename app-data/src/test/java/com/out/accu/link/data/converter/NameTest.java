package com.out.accu.link.data.converter;

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


}
