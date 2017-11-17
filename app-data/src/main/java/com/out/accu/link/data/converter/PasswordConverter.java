package com.out.accu.link.data.converter;

import com.out.accu.link.data.util.ByteUtil;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/11/2
 */

public class PasswordConverter {

    public static byte[] request(String oldPwd, String newPwd) {
        byte[] data = new byte[64];
        ByteUtil.arrayCopy(oldPwd.getBytes(), 0, data, 0, 32);
        ByteUtil.arrayCopy(newPwd.getBytes(), 0, data, 32, 32);
        return data;
    }
}
