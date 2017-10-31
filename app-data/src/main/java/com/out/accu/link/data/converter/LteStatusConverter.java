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
 * @version 2017/10/31
 */

public class LteStatusConverter {

//    public static byte[] request(String id, int lteStatus, int lteRssi, int lteMode) {
//        byte[] data = new byte[9];
//        ByteUtil.arrayCopy(id.getBytes(), 0, data, 0, 6);
//        data[6] = (byte) lteStatus;
//        data[7] = (byte) lteRssi;
//        data[8] = (byte) lteMode;
//        return data;
//    }

    public static Device response(Device device, Response response) {
        device.lteStatus = response.data[6];
        device.lteRssi = response.data[7];
        device.lteMode = response.data[8];
        return device;
    }
}
