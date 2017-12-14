package com.out.accu.link.data.udp;

import org.junit.Test;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/11/27
 */
public class TaskQueueTest {

    @Test
    public void test() {
        byte[] cmd = new byte[]{(byte) 0xA4, 0x08};
        StringBuilder keySb = new StringBuilder();
        keySb.append(Integer.toHexString(cmd[0]&0xff));
        keySb.append(Integer.toHexString(cmd[1]&0xff));
        System.out.println(keySb.toString());

        int value = 6;
        int sample = 25000000;

        float v1 = (value * 10000.0f)  / (sample / 1000);
        float v2 = v1 * 100;
        System.out.println(v1*100);
    }
}