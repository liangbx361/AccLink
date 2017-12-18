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
 * @version 2017/10/26
 */

public class LocationConverter {

    public static byte[] request(byte[] deviceId, double lat, double lon) {
        byte[] data = new byte[22];
        ByteUtil.arrayCopy(deviceId, 0, data, 0, 6);
        ByteUtil.arrayCopy(ByteUtil.doubleToByte(lat), 0, data, 6, 8);
        ByteUtil.arrayCopy(ByteUtil.doubleToByte(lon), 0, data, 14, 8);
        return data;
    }

    public static Device response(Device device, Response response) {
        DeviceIdConverter.convert(device, response);
        device.lat = ByteUtil.getDouble(response.data, 6);
        device.lng = ByteUtil.getDouble(response.data, 14);
        AppLogger.get().d("response", "device reqId ->" + ByteUtil.getId(device.id));
        AppLogger.get().d("response", "lat ->" + device.lat);
        AppLogger.get().d("response", "lng ->" + device.lng);
        return device;
    }
}
