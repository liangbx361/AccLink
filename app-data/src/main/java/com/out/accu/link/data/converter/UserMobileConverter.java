package com.out.accu.link.data.converter;

import com.out.accu.link.data.mode.ResponseCmd;
import com.out.accu.link.data.mode.User;
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

public class UserMobileConverter {

    public static byte[] request(String mobile) {
        byte[] data = new byte[20];
        ByteUtil.arrayCopy(mobile.getBytes(), 0, data, 0, 20);
        return data;
    }

    public static User response(User user, ResponseCmd response) {
        user.mobile = ByteUtil.getString(response.data, 0, 20).trim();
        return user;
    }
}
