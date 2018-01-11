package com.out.accu.link.util;

import android.content.res.Resources;
import android.text.TextUtils;

import com.cyou17173.android.arch.base.app.Smart;
import com.out.accu.link.R;
import com.out.accu.link.data.mode.Device;

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
        String id = device.id;
        if(TextUtils.isEmpty(device.aliasName)) {
            return id;
        } else {
            return id + " (" + device.aliasName + ")";
        }
    }

    public static String getDeviceDesc(Device device) {

        if(device.sampleValue == -1) {
            return null;
        }

        Resources resources = Smart.getApp().getResources();
        String level = resources.getString(R.string.sample_value, device.sampleValue/1000);
        if(device.samplePercent != null) {
            level += " [" + device.samplePercent + "%]";
        }

        return level;
    }
}
