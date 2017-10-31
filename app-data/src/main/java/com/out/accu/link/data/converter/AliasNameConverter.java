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
 * @version 2017/10/26
 */

public class AliasNameConverter {

    public byte[] request(String id, String name) {
        byte[] data = new byte[26];
        ByteUtil.arrayCopy(id.getBytes(), 0, data, 0, 6);
        ByteUtil.arrayCopy(name.getBytes(), 0, data, 6, 20);
        return data;
    }

    public static Device response(Device device, Response response) {
        device.aliasName = ByteUtil.getString(response.data, 6, 20);
        return device;
    }
}
