package com.out.accu.link.data.util;

import org.junit.Test;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/10/14
 */
public class PasswordUtilTest {
    @Test
    public void encrypt() throws Exception {
        String pwd = "123456";
        byte[] pwdByte = PasswordUtil.encrypt(pwd.getBytes());
        System.out.println(new String(pwdByte));

        pwd = "666666";
        pwdByte = PasswordUtil.encrypt(pwd.getBytes());
        System.out.println(new String(pwdByte));

        pwd = "~";
        pwdByte = PasswordUtil.encrypt(pwd.getBytes());
        System.out.println(new String(pwdByte));
    }

}