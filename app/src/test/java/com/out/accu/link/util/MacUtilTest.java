package com.out.accu.link.util;

import com.out.accu.link.data.util.MacUtil;

import org.junit.Assert;
import org.junit.Test;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/12/22
 */
public class MacUtilTest {
    @Test
    public void formatMac() throws Exception {
        String mac = "02:00:00:00:00:00";
        byte[] bytes = new byte[]{0x02, 0x00, 0x00, 0x00, 0x00, 0x00};
        Assert.assertArrayEquals(bytes, MacUtil.formatMac(mac));
    }

}