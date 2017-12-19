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
 * @version 2017/10/31
 */

public class LteStatusConverter {

//    public static byte[] request(String reqId, int lteStatus, int lteRssi, int lteMode) {
//        byte[] data = new byte[9];
//        ByteUtil.arrayCopy(reqId.getBytes(), 0, data, 0, 6);
//        data[6] = (byte) lteStatus;
//        data[7] = (byte) lteRssi;
//        data[8] = (byte) lteMode;
//        return data;
//    }

    public static Device response(Device device, ResponseCmd response) {
        DeviceIdConverter.convert(device, response);
        device.lteStatus = response.data[6];
        device.lteRssi = response.data[7];
        device.lteMode = response.data[8];
        AppLogger.get().d("response", "device reqId ->" + ByteUtil.getId(device.id));
        AppLogger.get().d("response", "lteStatus ->" + device.lteStatus);
        AppLogger.get().d("response", "lteRssi ->" + device.lteRssi);
        AppLogger.get().d("response", "lteMode ->" + device.lteMode);
        return device;
    }
}
