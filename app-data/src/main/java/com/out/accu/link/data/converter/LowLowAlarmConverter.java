package com.out.accu.link.data.converter;

import com.out.accu.link.data.mode.Device;
import com.out.accu.link.data.mode.Response;
import com.out.accu.link.data.util.ByteUtil;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/10/25
 */

public class LowLowAlarmConverter {

    public static byte[] request(String id, int value, String[] phones, String content) {
        byte[] data = new byte[298];
        ByteUtil.arrayCopy(id.getBytes(), 0, data, 0, 6);
        ByteUtil.arrayCopy(ByteUtil.intToByte(value), 0, data, 6, 4);
        int i=0;
        for(String phone : phones) {
            ByteUtil.arrayCopy(phone.getBytes(), 0, data, 10+i*20, 20);
        }
        ByteUtil.arrayCopy(content.getBytes(), 0, data, 170, 128);
        return data;
    }

    public static Device response(Device device, Response response) {
        device.id = ByteUtil.getString(response.data, 0, 6);
        device.lowLowAlarmLimitValue = ByteUtil.getInt(response.data, 6);
        String[] phones = new String[8];
        for(int i=0; i<8; i++) {
            phones[i] = ByteUtil.getString(response.data, 10+i*20, 20);
        }
        device.lowLowNotifyPhones = phones;
        String sms = ByteUtil.getString(response.data, 170, 128);
        device.lowLowSmsContent = sms;
        return device;
    }
}
