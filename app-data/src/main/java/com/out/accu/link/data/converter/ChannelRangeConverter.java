package com.out.accu.link.data.converter;

import com.out.accu.link.data.logger.AppLogger;
import com.out.accu.link.data.mode.Device;
import com.out.accu.link.data.mode.ResponseCmd;
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

public class ChannelRangeConverter {

    public static byte[] request(byte[] deviceId, int value1, int value2) {
        byte[] data = new byte[14];
        ByteUtil.arrayCopy(deviceId, 0, data, 0, 6);
        ByteUtil.arrayCopy(ByteUtil.intToByte(value1), 0, data, 6, 4);
        ByteUtil.arrayCopy(ByteUtil.intToByte(value2), 0, data, 10, 4);
        return data;
    }

    public static Device response(Device device, ResponseCmd response) {
        DeviceIdConverter.convert(device, response);
        device.channel1Range = ByteUtil.getInt(response.data, 6);
        device.channel2Range = ByteUtil.getInt(response.data, 10);
        AppLogger.get().d("response", "device reqId ->" + ByteUtil.getId(device.id));
        AppLogger.get().d("response", "channel1Range ->" + device.channel1Range);
        AppLogger.get().d("response", "channel2Range ->" + device.channel2Range);
        return device;
    }
}
