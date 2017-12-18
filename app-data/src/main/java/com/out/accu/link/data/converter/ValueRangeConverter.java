package com.out.accu.link.data.converter;

import com.out.accu.link.data.logger.AppLogger;
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

public class ValueRangeConverter {

    public static byte[] request(byte[] deviceId, int value) {
        byte[] data = new byte[10];
        ByteUtil.arrayCopy(deviceId, 0, data, 0, 6);
        ByteUtil.arrayCopy(ByteUtil.intToByte(value), 0, data, 6, 4);
        return data;
    }

    public static Device response(Device device, Response response) {
        DeviceIdConverter.convert(device, response);
        device.valueRange = ByteUtil.getInt(response.data, 6);
        AppLogger.get().d("response", "device reqId ->" + ByteUtil.getId(device.id));
        AppLogger.get().d("response", "valueRange ->" + device.valueRange);
        return device;
    }
}
