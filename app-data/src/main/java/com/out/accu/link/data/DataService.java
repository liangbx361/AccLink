package com.out.accu.link.data;

import com.out.accu.link.data.mode.Device;
import com.out.accu.link.data.mode.LoginInfo;

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
     */
    void login(LoginInfo loginInfo);

    /**
     * 设备列表
     */
    void getDevices();

    /**
     * 获取设备
     */
    void getDevice(Device device);

    /**
     * 获取设备历史数据
     */
    void getDeviceHistory(byte[] deviceId, long beginTime, long endTime);

    /**
     * 设置两路量程
     */
    void setChannel(byte[] deviceId, int value1, int value2);

    /**
     * 设置值量程
     */
    void setValue(byte[] deviceId, int value);

    /**
     * 设置上报周期
     */
    void setReportPeriod(byte[] deviceId, int value);

    /**
     * 设置数据低报开关
     */
    void setLowAlarmEnable(byte[] deviceId, boolean enable);

    /**
     * 设置数据低报参数
     */
    void setLowAlarmLimitValue(byte[] deviceId, int value, String[] phones, String sms);

    /**
     * 设置数据低低报开关
     */
    void setLowLowAlarmEnable(byte[] deviceId, boolean enable);

    /**
     * 设置数据低低报参数
     */
    void setLowLowAlarmLimitValue(byte[] deviceId, int value, String[] phones, String sms);

    /**
     * 设置GPS坐标
     */
    void setGps(byte[] deviceId, double lat, double lot);

    /**
     * 设置设置设备布防使能开关
     */
    void setDefenseEnable(byte[] deviceId, boolean enable);

    /**
     * 设置模块别名
     */
    void setAliasName(byte[] deviceId, String name);

    /**
     * 历史数据查询
     */
    void getHistory(byte[] deviceId, long startTime, long endTime);

    /**
     * 获取用户信息
     */
    void getUser();

    /**
     * 设置用户名
     */
    void setUsername(String username);

    /**
     * 设置手机号
     */
    void setMobile(String mobile);

    /**
     * 设置密码
     */
    void setPassword(String oldPwd, String newPwd);

    /**
     * 登出
     */
    void logout();

    /**
     * 保存登录信息
     */
    void saveLoginInfo(LoginInfo loginInfo);

    /**
     * 获取登录信息
     */
    LoginInfo getLoginInfo();
}
