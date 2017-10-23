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
    Observable<Device> getDevice(String deviceId);

    /**
     * 获取设备历史数据
     */
    Observable<DeviceHistory> getDeviceHistory(String deviceId, long beginTime, long endTime);

    /**
     * 设置两路量程
     */
    Observable<Boolean> setChannel(String deviceId, int value1, int value2);

    /**
     * 设置值量程
     */
    Observable<Boolean> setValue(String deviceId, int value);

    /**
     * 设置上报周期
     */
    Observable<Boolean> setReportPeriod(String deviceId, int value);

    /**
     * 设置数据低报开关
     */
    Observable<Boolean> setLowAlarmEnable(String deviceId, boolean enable);

    /**
     * 设置数据低报参数
     */
    Observable<Boolean> setLowAlarmLimitValue(String deviceId, int value, String[] phones, String sms);

    /**
     * 设置数据低低报开关
     */
    Observable<Boolean> setLowLowAlarmEnable(String deviceId, boolean enable);

    /**
     * 设置数据低低报参数
     */
    Observable<Boolean> setLowLowAlarmLimitValue(String deviceId, int value, String[] phones, String sms);

    /**
     * 设置GPS坐标
     */
    Observable<Boolean> setGps(String deviceId, double lat, double lot);

    /**
     * 设置设置设备布防使能开关
     */
    Observable<Boolean> setDefenseEnable(String deviceId, boolean enable);

    /**
     * 设置模块别名
     */
    Observable<Boolean> setAliasName(String deviceId, String name);
}
