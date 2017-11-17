package com.out.accu.link.data.converter;

import com.out.accu.link.data.mode.DeviceHistory;
import com.out.accu.link.data.mode.Response;
import com.out.accu.link.data.util.ByteUtil;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/11/2
 */

public class HistoryConverter {

    public static byte[] request(String deviceId, long startTime, long endTime) {
        byte[] data = new byte[22];

        Date start = new Date(startTime);
        Date end = new Date(endTime);
        

        ByteUtil.arrayCopy(deviceId.getBytes(), 0, data, 0, 6);
        ByteUtil.arrayCopy(ByteUtil.doubleToByte(startTime), 0, data, 6, 8);
        ByteUtil.arrayCopy(ByteUtil.doubleToByte(endTime), 0, data, 14, 8);
        return data;
    }

    public static List<DeviceHistory> response(Response response) {
        List<DeviceHistory> histories = new ArrayList<>();
        if(response.data.length > 0) {
            int length = (response.data.length - 6) / 12;
            for (int i=0; i<length; i++) {
                int index = 6 + i*12;
                double time = ByteUtil.getDouble(response.data, index);
                int value = ByteUtil.getInt(response.data, index+8);
                DeviceHistory history = new DeviceHistory();
                history.time = (float) time;
                history.value = value;
                histories.add(history);
            }
        }

        return histories;
    }
}
