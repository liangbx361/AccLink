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

public class UsernameConverter {

    public static byte[] request(String username) {
        byte[] data = new byte[20];
        ByteUtil.arrayCopy(username.getBytes(), 0, data, 0, 20);
        return data;
    }

    public static User response(User user, ResponseCmd response) {
        user.username = ByteUtil.getString(response.data, 0, 20).trim();
        return user;
    }
}
