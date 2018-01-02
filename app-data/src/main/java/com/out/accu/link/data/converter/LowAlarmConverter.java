package com.out.accu.link.data.converter;

import com.out.accu.link.data.logger.AppLogger;
import com.out.accu.link.data.mode.Device;
import com.out.accu.link.data.mode.ResponseCmd;
import com.out.accu.link.data.util.ByteUtil;

import java.io.UnsupportedEncodingException;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/10/25
 */

public class LowAlarmConverter {

    public static byte[] request(byte[] deviceId, int value, String[] phones, String content) {
        byte[] data = new byte[298];
        ByteUtil.arrayCopy(deviceId, 0, data, 0, 6);
        ByteUtil.arrayCopy(ByteUtil.intToByte(value), 0, data, 6, 4);
        int i=0;
        if(phones != null) {
            for (String phone : phones) {
                ByteUtil.arrayCopy(phone.getBytes(), 0, data, 10 + i * 20, 20);
                i++;
            }
        }
        try {
            ByteUtil.arrayCopy(content.getBytes("GB2312"), 0, data, 170, 128);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static Device response(Device device, ResponseCmd response) {
        DeviceIdConverter.convert(device, response);
        device.lowAlarmLimitValue = ByteUtil.getInt(response.data, 6);
        String[] phones = new String[8];
        for(int i=0; i<8; i++) {
            phones[i] = ByteUtil.getString(response.data, 10+i*20, 20);
        }
        device.lowNotifyPhones = phones;
        device.lowSmsContent = ByteUtil.getString(response.data, 170, 128);

        AppLogger.get().d("response", "device reqId ->" + device.id);
        AppLogger.get().d("response", "lowAlarmLimitValue ->" + device.lowAlarmLimitValue);
        StringBuilder phoneSb = new StringBuilder();
        for(String phone : phones) {
            phoneSb.append(phone);
            phoneSb.append("-");
        }
        AppLogger.get().d("response", "phone ->" + phoneSb.toString());
        AppLogger.get().d("response", "lowSmsContent ->" + device.lowSmsContent);

        return device;
    }
}
