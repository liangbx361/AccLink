package com.out.accu.link.data.converter;

import com.out.accu.link.data.mode.DeviceHistory;
import com.out.accu.link.data.mode.Response;
import com.out.accu.link.data.util.ByteUtil;

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

        ByteUtil.arrayCopy(deviceId.getBytes(), 0, data, 0, 6);
        ByteUtil.arrayCopy(ByteUtil.formatTime(startTime), 0, data, 6, 8);
        ByteUtil.arrayCopy(ByteUtil.formatTime(endTime), 0, data, 14, 8);
        return data;
    }

    public static DeviceHistory response(Response response) {
        DeviceHistory history = new DeviceHistory();
        history.deviceId = ByteUtil.getString(response.data, 0, 6);
        if(response.data.length > 0) {
            int length = (response.data.length - 6) / 12;
            for (int i=0; i<length; i++) {
                int index = 6 + i*12;
                int value = ByteUtil.getInt(response.data, index);
                long time = ByteUtil.formatTime(response.data, index+4);
                DeviceHistory.Item item = new DeviceHistory.Item();
                item.time = time;
                item.value = value;
                history.list.add(item);
            }
        }

        return history;
    }
}
