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

public class ByteUtil {

    public static void arrayCopy(byte[] src,  int srcPos,
                                   byte[] dest, int destPos,
                                   int maxLength) {
        if(src.length < maxLength) {
            System.arraycopy(src, srcPos, dest, destPos, src.length);
        } else {
            System.arraycopy(src, srcPos, dest, destPos, maxLength);
        }
    }
}
