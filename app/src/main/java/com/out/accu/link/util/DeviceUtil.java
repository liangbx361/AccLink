package com.out.accu.link.util;

import android.text.TextUtils;

import com.out.accu.link.data.mode.Device;
import com.out.accu.link.data.util.ByteUtil;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/11/30
 */

public class DeviceUtil {

    public static String getDeviceName(Device device) {
        String id = ByteUtil.getId(device.id);
        if(TextUtils.isEmpty(device.aliasName)) {
            return id;
        } else {
            return id + " (" + device.aliasName + ")";
        }
    }
}
