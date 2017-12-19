package com.out.accu.link.data.converter;

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
 * @version 2017/12/14
 */

public class DeviceIdConverter {

    public static void convert(Device device, ResponseCmd response) {
        byte[] idBytes = new byte[6];
        ByteUtil.arrayCopy(response.data, 0, idBytes, 0, 6);
        device.id = ByteUtil.getIdString(idBytes);
        device.idBytes = idBytes;
    }
}
