package com.out.accu.link.data.converter;

import org.junit.Assert;
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
public class LoginConverterTest {

    @Test
    public void request() throws Exception {
        byte[] data = LoginConverter.request("1", "1");
        Assert.assertEquals(52, data.length);
        Assert.assertEquals(0x31, data[0]);
        Assert.assertEquals(0x32, data[20]);
    }

}