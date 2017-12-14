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

public class LowAlarmEnableConverter {

    public static byte[] request(byte[] deviceId, boolean enable) {
        byte[] data = new byte[7];
        ByteUtil.arrayCopy(deviceId, 0, data, 0, 6);
        if(enable) {
            data[6] = 1;
        }
        return data;
    }

    public static Device response(Device device, Response response) {
        DeviceIdConverter.convert(device, response);
        device.lowAlarmEnable = response.data[6];
        return device;
    }
}
