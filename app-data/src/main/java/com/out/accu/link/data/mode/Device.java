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

    // 频道1量程
    int channel1Range;

    // 频道2量程
    int channel2Range;

    // 值量程
    int valueRange;

    // 数据上报周期 4
    int reportPeriod;

    // 数据底报使能开关 1
    boolean lowAlarmEnable;

    // 数据底报参数 4
    int lowAlarmLimitValue;

    // 8组短信通知号码 20*8
    String[] lowNotifyPhones;

    // 短信内容
    String smsFormat;

    // 数据底底报使能开关
    boolean lowLowAlarmEnable;

    // 数据底底报参数
    int lowLowAlarmLimitValue;

    // 8组短信通知号码 20*8
    String[] lowLowNotifyPhones;

    String lowLowSmsFormat;

    // 模块别名
    String aliasName;

    // 经度
    double lat;

    // 纬度
    double lon;

    // 设备布防状态
    boolean defenseEnable;

    // 内部的温度值
    int temperature;

    // 内部的湿度值
    int humidity;

    // 号码
    String phoneNumber;

    // LTE通讯模块状态
    int lteStatus;

    // 天线信号强度值
    int lteRssi;

    // 工作模式
    int lteMode;

    // 当前月总发送数据字节数
    int currTx;
}
