package com.out.accu.link.data.converter;

import android.util.Log;

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

public class ReportPeriodConverter {

    public static byte[] request(String id, int reportPeriod) {
        byte[] data = new byte[10];
        ByteUtil.arrayCopy(id.getBytes(), 0, data, 0, 6);
        ByteUtil.arrayCopy(ByteUtil.intToByte(reportPeriod), 0, data, 6, 4);
        return data;
    }

    public static Device response(Device device, Response response) {
        device.id = ByteUtil.getString(response.data, 0, 6);
        device.reportPeriod = ByteUtil.getInt(response.data, 6);
        Log.d("response", "device id ->" + ByteUtil.getId(device.id));
        Log.d("response", "reportPeriod ->" + device.reportPeriod);
        return device;
    }
}
