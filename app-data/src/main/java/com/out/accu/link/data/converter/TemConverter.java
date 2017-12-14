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
 * @version 2017/10/31
 */

public class TemConverter {

    public static Device response(Device device, Response response) {
        DeviceIdConverter.convert(device, response);
        device.temperature = ByteUtil.getInt(response.data, 6);
        device.humidity = ByteUtil.getInt(response.data, 10);
        return device;
    }
}
