package com.out.accu.link.util;

import com.out.accu.link.data.mode.Device;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2018/1/11
 */

public class SmsUtil {

    public static String getSms(Device device) {
        if(device.lowSmsContent == null) {
            return "";
        }

        String smsContent = device.lowSmsContent;
        smsContent = smsContent.replaceAll("%V0", device.sampleValue/1000+"cm");
        smsContent = smsContent.replaceAll("%M", device.id);
        return smsContent;
    }
}
