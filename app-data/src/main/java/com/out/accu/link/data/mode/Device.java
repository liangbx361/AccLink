package com.out.accu.link.data.mode;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/10/1
 */

public class Device implements Parcelable {

    // 设备ID
    public String id;

    // 模块别名
    public String aliasName;

    // 设备是否在线
    public boolean isOnline;

    // 频道1量程
    public int channel1Range;

    // 频道2量程
    public int channel2Range;

    // 值量程
    public int valueRange;

    // 数据上报周期 4
    public int reportPeriod;

    // 数据底报使能开关 1
    public boolean lowAlarmEnable;

    // 数据底报参数 4
    public int lowAlarmLimitValue;

    // 8组短信通知号码 20*8
    public String[] lowNotifyPhones;

    // 短信内容
    public String smsFormat;

    // 数据底底报使能开关
    public boolean lowLowAlarmEnable;

    // 数据底底报参数
    public int lowLowAlarmLimitValue;

    // 8组短信通知号码 20*8
    public String[] lowLowNotifyPhones;

    public String lowLowSmsFormat;

    // 经度
    public double lat;

    // 纬度
    public double lon;

    // 设备布防状态
    public boolean defenseEnable;

    // 内部的温度值
    public int temperature;

    // 内部的湿度值
    public int humidity;

    // 号码
    public String phoneNumber;

    // LTE通讯模块状态
    public int lteStatus;

    // 天线信号强度值
    public int lteRssi;

    // 工作模式
    public int lteMode;

    // 当前月总发送数据字节数
    public int currTx;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.aliasName);
        dest.writeByte(this.isOnline ? (byte) 1 : (byte) 0);
        dest.writeInt(this.channel1Range);
        dest.writeInt(this.channel2Range);
        dest.writeInt(this.valueRange);
        dest.writeInt(this.reportPeriod);
        dest.writeByte(this.lowAlarmEnable ? (byte) 1 : (byte) 0);
        dest.writeInt(this.lowAlarmLimitValue);
        dest.writeStringArray(this.lowNotifyPhones);
        dest.writeString(this.smsFormat);
        dest.writeByte(this.lowLowAlarmEnable ? (byte) 1 : (byte) 0);
        dest.writeInt(this.lowLowAlarmLimitValue);
        dest.writeStringArray(this.lowLowNotifyPhones);
        dest.writeString(this.lowLowSmsFormat);
        dest.writeDouble(this.lat);
        dest.writeDouble(this.lon);
        dest.writeByte(this.defenseEnable ? (byte) 1 : (byte) 0);
        dest.writeInt(this.temperature);
        dest.writeInt(this.humidity);
        dest.writeString(this.phoneNumber);
        dest.writeInt(this.lteStatus);
        dest.writeInt(this.lteRssi);
        dest.writeInt(this.lteMode);
        dest.writeInt(this.currTx);
    }

    public Device() {
    }

    protected Device(Parcel in) {
        this.id = in.readString();
        this.aliasName = in.readString();
        this.isOnline = in.readByte() != 0;
        this.channel1Range = in.readInt();
        this.channel2Range = in.readInt();
        this.valueRange = in.readInt();
        this.reportPeriod = in.readInt();
        this.lowAlarmEnable = in.readByte() != 0;
        this.lowAlarmLimitValue = in.readInt();
        this.lowNotifyPhones = in.createStringArray();
        this.smsFormat = in.readString();
        this.lowLowAlarmEnable = in.readByte() != 0;
        this.lowLowAlarmLimitValue = in.readInt();
        this.lowLowNotifyPhones = in.createStringArray();
        this.lowLowSmsFormat = in.readString();
        this.lat = in.readDouble();
        this.lon = in.readDouble();
        this.defenseEnable = in.readByte() != 0;
        this.temperature = in.readInt();
        this.humidity = in.readInt();
        this.phoneNumber = in.readString();
        this.lteStatus = in.readInt();
        this.lteRssi = in.readInt();
        this.lteMode = in.readInt();
        this.currTx = in.readInt();
    }

    public static final Parcelable.Creator<Device> CREATOR = new Parcelable.Creator<Device>() {
        @Override
        public Device createFromParcel(Parcel source) {
            return new Device(source);
        }

        @Override
        public Device[] newArray(int size) {
            return new Device[size];
        }
    };
}
