package com.out.accu.link.data.util;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/10/14
 */

public class PasswordUtil {

    public static byte[] encrypt(byte[] data) {
        byte length = (byte) data.length;
        for(int i=0; i<data.length; i++) {
            byte cell = data[i];
            byte result = (byte) (cell+length+i);
            byte temp = (byte) (result - 0x7E);
            if(temp <= 0) {
                data[i] = result;
            } else {
                data[i] = (byte) (temp + 0x20);
            }
        }
        return data;
    }

    public static byte[] decrypt(byte[] data) {
        return data;
    }
}
