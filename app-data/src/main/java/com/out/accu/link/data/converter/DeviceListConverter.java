package com.out.accu.link.data.converter;

import com.out.accu.link.data.mode.Device;
import com.out.accu.link.data.mode.Response;
import com.out.accu.link.data.util.ByteUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/10/25
 */

public class DeviceListConverter {

//    public static byte[] request() {
//        byte[] userByte = username.getBytes();
//        byte[] pwdByte = PasswordUtil.encrypt(password.getBytes());
//        byte[] data = new byte[52];
//        ByteUtil.arrayCopy(userByte, 0, data, 0, 20);
//        ByteUtil.arrayCopy(pwdByte, 0, data, 20, 32);
//        return data;
//    }

    public static List<Device> response(Response response) {
        List<Device> devices = new ArrayList<>();

        int length = response.data.length / 7;
        for(int i=0; i<length; i++) {
            Device device = new Device();
            device.id = ByteUtil.getString(response.data, i*7, 6);
            device.isOnline = response.data[i * 7 + 6] == 1;
            devices.add(device);
        }

        return devices;
    }

    
}
