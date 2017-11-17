package com.out.accu.link.util;

import com.out.accu.link.data.mode.Device;

import java.util.List;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/10/18
 */

public class DeviceHelper {

    public static void saveDevices(List<Device> devices) {
//        Smart.getApp().getConfig().getRuntimeConfig().putParcelableArrayList(
//                AppConfig.DEVICE_LIST, (ArrayList<? extends Parcelable>) devices
//        );
    }

    public static List<Device> getDevices() {
        return null;
    }

    public static Device getDevice(String id) {
        List<Device> devices = getDevices();
        for(Device device : devices) {
            if(device.id.equals(id)) {
                return device;
            }
        }

        return null;
    }
}
