package com.out.accu.link.data.converter;

import com.out.accu.link.data.mode.Login;
import com.out.accu.link.data.mode.Response;
import com.out.accu.link.data.util.ByteUtil;
import com.out.accu.link.data.util.PasswordUtil;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/10/14
 */

public class LoginConverter {

    public static byte[] request(String username, String password) {
        byte[] userByte = username.getBytes();
        byte[] pwdByte = PasswordUtil.encrypt(password.getBytes());
        byte[] data = new byte[52];
        ByteUtil.arrayCopy(userByte, 0, data, 0, 20);
        ByteUtil.arrayCopy(pwdByte, 0, data, 20, 32);
        return data;
    }

    public static Login response(Response response) {
        Login login = new Login();
        login.isSuccess = response.data[0] != 0;
        return login;
    }
}
