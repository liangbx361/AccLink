package com.out.accu.link.data.converter;

import com.out.accu.link.data.mode.Device;
import com.out.accu.link.data.mode.Response;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/11/15
 */

public class UploadOnlineConverter {

    public static Device response(Device device, Response response) {
        DeviceIdConverter.convert(device, response);
        device.isOnline = true;
        return device;
    }
}
