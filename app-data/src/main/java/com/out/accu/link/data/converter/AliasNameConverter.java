package com.out.accu.link.data.converter;

import com.out.accu.link.data.logger.AppLogger;
import com.out.accu.link.data.mode.Device;
import com.out.accu.link.data.mode.Response;
import com.out.accu.link.data.util.ByteUtil;

import java.io.UnsupportedEncodingException;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/10/26
 */

public class AliasNameConverter {

    public static byte[] request(byte[] deviceId, String name) {
        try {
            byte[] data = new byte[56];
            ByteUtil.arrayCopy(deviceId, 0, data, 0, 6);
            ByteUtil.arrayCopy(name.getBytes("GB2312"), 0, data, 6, 50);
            return data;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return new byte[26];

    }

    public static Device response(Device device, Response response) {
        DeviceIdConverter.convert(device, response);
        device.aliasName = ByteUtil.getString(response.data, 6, 50).trim();
        AppLogger.get().d("response", "aliasName ->" + device.aliasName);
        return device;
    }
}
