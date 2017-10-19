package com.out.accu.link.data;

import com.out.accu.link.data.mode.Device;
import com.out.accu.link.data.mode.DeviceHistory;
import com.out.accu.link.data.mode.Login;

import java.util.List;

import io.reactivex.Observable;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/9/29
 */

public interface DataService {

    /**
     * 登录
     * @param userName 用户名
     * @param password 密码
     */
    Observable<Login> login(String userName, String password);

    /**
     * 设备列表
     */
    Observable<List<Device>> getDevices();

    /**
     * 获取设备
     */
    Observable<Device> getDevice(String id);

    /**
     * 获取设备历史数据
     */
    Observable<DeviceHistory> getDeviceHistory(String id, long beginTime, long endTime);
}
