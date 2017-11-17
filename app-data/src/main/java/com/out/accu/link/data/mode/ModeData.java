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

    public List<Device> devices = new ArrayList<>();

    public Device getDevice(String id) {
        for(Device device : devices) {
            if(device.id.equals(id)) {
                return device;
            }
        }

        throw new NullPointerException();
    }
}
