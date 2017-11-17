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

public class ValueRangeConverter {

    public static byte[] request(String id, int value) {
        byte[] data = new byte[10];
        ByteUtil.arrayCopy(id.getBytes(), 0, data, 0, 6);
        ByteUtil.arrayCopy(ByteUtil.intToByte(value), 0, data, 6, 4);
        return data;
    }

    public static Device response(Device device, Response response) {
        device.valueRange = response.data[9] << 24 | response.data[8] << 16
                | response.data[7] << 8 | response.data[6];
        return device;
    }
}