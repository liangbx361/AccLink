package com.out.accu.link.data.mode;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/10/1
 */

public class Device {

    // 设备ID
    public String id;

    // 模块别名
    public String aliasName;

    // 设备是否在线
    public boolean isOnline;

    // 频道1量程
    public int channel1Range = -1;

    // 频道2量程
    public int channel2Range = -1;

    // 值量程
    public int valueRange = -1;

    // 数据上报周期 4
    public int reportPeriod = -1;

    // 数据底报使能开关 1
    public int lowAlarmEnable = -1;

    // 数据底报参数 4
    public int lowAlarmLimitValue = -1;

    // 8组短信通知号码 20*8
    public String[] lowNotifyPhones;

    // 短信内容
    public String lowSmsContent;

    // 数据底底报使能开关
    public int lowLowAlarmEnable = -1;

    // 数据底底报参数
    public int lowLowAlarmLimitValue = -1;

    // 8组短信通知号码 20*8
    public String[] lowLowNotifyPhones;

    public String lowLowSmsContent;

    // 经度
    public double lat = -1;

    // 纬度
    public double lng = -1;

    // 设备布防状态
    public int defenseEnable = -1;

    // 内部的温度值
    public int temperature = -1;

    // 内部的湿度值
    public int humidity = -1;

    // 模块号码
    public String phoneNumber;

    // LTE通讯模块状态
    public int lteStatus = -1;

    // 天线信号强度值
    public int lteRssi = -1;

    // 工作模式
    public int lteMode = -1;

    // 当前月总发送数据字节数
    public int currTx = -1;

    public int sampleValue = -1;

    public int channel1Value = -1;

    public int channel2Value = -1;

}
