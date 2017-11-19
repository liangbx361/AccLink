package com.out.accu.link.data.mode;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: 数据合集<／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/11/9
 */

public class ModeData {

    public User user;

    private List<Device> devices = new ArrayList<>();

    public synchronized Device getDevice(String id) {
        for(Device device : devices) {
            if(device.id.equals(id)) {
                return device;
            }
        }

        throw new NullPointerException();
    }

    public synchronized void addDevice(Device device) {
        devices.add(device);
    }

    public synchronized void addAll(List<Device> devices) {
        this.devices.addAll(devices);
    }

    public synchronized List<Device> getDevices() {
        return devices;
    }
}
