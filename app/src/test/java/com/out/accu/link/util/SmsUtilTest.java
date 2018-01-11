package com.out.accu.link.util;

import com.out.accu.link.data.mode.Device;

import org.junit.Assert;
import org.junit.Test;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2018/1/11
 */

public class SmsUtilTest {

    @Test
    public void getSmsTest() throws Exception {
        Device device = new Device();
        device.id = "123456";
        device.sampleValue = 10;
        device.lowSmsContent = "%M Current is: %V0";
        Assert.assertEquals("123456 Current is: 10cm", SmsUtil.getSms(device));

        device.lowSmsContent = null;
        SmsUtil.getSms(device);
    }
}
