package com.out.accu.link.data.remote;

import com.out.accu.link.data.DataService;
import com.out.accu.link.data.config.Platform;
import com.out.accu.link.data.converter.AliasNameConverter;
import com.out.accu.link.data.converter.ChannelRangeConverter;
import com.out.accu.link.data.converter.DefenseEnableConverter;
import com.out.accu.link.data.converter.HistoryConverter;
import com.out.accu.link.data.converter.LocationConverter;
import com.out.accu.link.data.converter.LoginConverter;
import com.out.accu.link.data.converter.LowAlarmConverter;
import com.out.accu.link.data.converter.LowAlarmEnableConverter;
import com.out.accu.link.data.converter.LowLowAlarmConverter;
import com.out.accu.link.data.converter.LowLowAlarmEnableConverter;
import com.out.accu.link.data.converter.PasswordConverter;
import com.out.accu.link.data.converter.ReportPeriodConverter;
import com.out.accu.link.data.converter.UsernameConverter;
import com.out.accu.link.data.converter.ValueRangeConverter;
import com.out.accu.link.data.mode.Device;
import com.out.accu.link.data.udp.UdpHandler;
import com.out.accu.link.data.util.ByteUtil;
import com.out.accu.link.data.util.PacketUtil;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/10/14
 */

public class RemoteService implements DataService {

    private UdpHandler mUdpHandler;

    public RemoteService(Platform platform, UdpHandler udpHandler) {
        mUdpHandler = udpHandler;
    }

    @Override
    public void login(String userName, String password) {
        Observable.just(LoginConverter.request(userName, password))
                .subscribeOn(Schedulers.io())
                .map(bytes -> PacketUtil.getPacket(PacketUtil.CMD_LOGIN, PacketUtil.TYPE_REQUEST, bytes))
                .map(bytes -> mUdpHandler.send(bytes))
                .subscribe();
    }

    @Override
    public void getDevices() {
        Observable.just(new byte[0])
                .subscribeOn(Schedulers.io())
                .map(bytes -> PacketUtil.getPacket(PacketUtil.CMD_GET_DEVICES, PacketUtil.TYPE_REQUEST, bytes))
                .map(bytes -> mUdpHandler.send(bytes))
                .subscribe();
    }

    @Override
    public void getDevice(Device device) {
        byte[] deviceId = ByteUtil.stringToByte(device.id, 6);
        Observable.just(device)
                .subscribeOn(Schedulers.io())
                // 两路量程
                .map(device1 -> PacketUtil.getPacket(PacketUtil.CMD_GET_CHANNEL_RANGE, PacketUtil.TYPE_REQUEST, deviceId))
                .map(bytes -> mUdpHandler.send(bytes))
                // 量程
                .map(device1 -> PacketUtil.getPacket(PacketUtil.CMD_GET_VALUE_RANGE, PacketUtil.TYPE_REQUEST, deviceId))
                .map(bytes -> mUdpHandler.send(bytes))
                //上报周期
                .map(device1 -> PacketUtil.getPacket(PacketUtil.CMD_GET_REPORT_PERIOD, PacketUtil.TYPE_REQUEST, deviceId))
                .map(bytes -> mUdpHandler.send(bytes))
                //低报参数
                .map(device1 -> PacketUtil.getPacket(PacketUtil.CMD_GET_LOW_ALARM_ENABLE, PacketUtil.TYPE_REQUEST, deviceId))
                .map(bytes -> mUdpHandler.send(bytes))
                //
                .map(device1 -> PacketUtil.getPacket(PacketUtil.CMD_GET_LOW_ALARM_PARAMS, PacketUtil.TYPE_REQUEST, deviceId))
                .map(bytes -> mUdpHandler.send(bytes))
                //
                .map(device1 -> PacketUtil.getPacket(PacketUtil.CMD_GET_LOW_LOW_ALARM_ENABLE, PacketUtil.TYPE_REQUEST, deviceId))
                .map(bytes -> mUdpHandler.send(bytes))
                //
                .map(device1 -> PacketUtil.getPacket(PacketUtil.CMD_GET_LOW_LOW_ALARM_PARAMS, PacketUtil.TYPE_REQUEST, deviceId))
                .map(bytes -> mUdpHandler.send(bytes))

                // 别名
                .map(device1 -> PacketUtil.getPacket(PacketUtil.CMD_GET_ALIAS_NAME, PacketUtil.TYPE_REQUEST, deviceId))
                .map(bytes -> mUdpHandler.send(bytes))
                //
                .map(device1 -> PacketUtil.getPacket(PacketUtil.CMD_GET_LOCATION, PacketUtil.TYPE_REQUEST, deviceId))
                .map(bytes -> mUdpHandler.send(bytes))
                //
                .map(device1 -> PacketUtil.getPacket(PacketUtil.CMD_GET_DEFENSE_ENABLE, PacketUtil.TYPE_REQUEST, deviceId))
                .map(bytes -> mUdpHandler.send(bytes))
                // CMD_GET_TEM
                .map(device1 -> PacketUtil.getPacket(PacketUtil.CMD_GET_TEM, PacketUtil.TYPE_REQUEST, deviceId))
                .map(bytes -> mUdpHandler.send(bytes))
                // CMD_GET_PHONE_NUMBER
                .map(device1 -> PacketUtil.getPacket(PacketUtil.CMD_GET_PHONE_NUMBER, PacketUtil.TYPE_REQUEST, deviceId))
                .map(bytes -> mUdpHandler.send(bytes))
                // CMD_GET_LTE_STATUS
                .map(device1 -> PacketUtil.getPacket(PacketUtil.CMD_GET_LTE_STATUS, PacketUtil.TYPE_REQUEST, deviceId))
                .map(bytes -> mUdpHandler.send(bytes))
                // CMD_GET_TX
                .map(device1 -> PacketUtil.getPacket(PacketUtil.CMD_GET_TX, PacketUtil.TYPE_REQUEST, deviceId))
                .map(bytes -> mUdpHandler.send(bytes))
                .subscribe();
    }

    @Override
    public void getDeviceHistory(String deviceId, long beginTime, long endTime) {

    }

    @Override
    public void setChannel(String deviceId, int value1, int value2) {
        Observable.just(ChannelRangeConverter.request(deviceId, value1, value2))
                .subscribeOn(Schedulers.io())
                .map(bytes -> PacketUtil.getPacket(PacketUtil.CMD_SET_CHANNEL_RANGE, PacketUtil.TYPE_REQUEST, bytes))
                .map(bytes -> mUdpHandler.send(bytes))
                .subscribe();
    }

    @Override
    public void setValue(String deviceId, int value) {
        Observable.just(ValueRangeConverter.request(deviceId, value))
                .subscribeOn(Schedulers.io())
                .map(bytes -> PacketUtil.getPacket(PacketUtil.CMD_SET_VALUE_RANGE, PacketUtil.TYPE_REQUEST, bytes))
                .map(bytes -> mUdpHandler.send(bytes))
                .subscribe();
    }

    @Override
    public void setReportPeriod(String deviceId, int value) {
        Observable.just(ReportPeriodConverter.request(deviceId, value))
                .subscribeOn(Schedulers.io())
                .map(bytes -> PacketUtil.getPacket(PacketUtil.CMD_SET_REPORT_PERIOD, PacketUtil.TYPE_REQUEST, bytes))
                .map(bytes -> mUdpHandler.send(bytes))
                .subscribe();
    }

    @Override
    public void setLowAlarmEnable(String deviceId, boolean enable) {
        Observable.just(LowAlarmEnableConverter.request(deviceId, enable))
                .subscribeOn(Schedulers.io())
                .map(bytes -> PacketUtil.getPacket(PacketUtil.CMD_SET_LOW_ALARM_ENABLE, PacketUtil.TYPE_REQUEST, bytes))
                .map(bytes -> mUdpHandler.send(bytes))
                .subscribe();
    }

    @Override
    public void setLowAlarmLimitValue(String deviceId, int value, String[] phones, String sms) {
        Observable.just(LowAlarmConverter.request(deviceId, value, phones, sms))
                .subscribeOn(Schedulers.io())
                .map(bytes -> PacketUtil.getPacket(PacketUtil.CMD_SET_LOW_ALARM_PARAMS, PacketUtil.TYPE_REQUEST, bytes))
                .map(bytes -> mUdpHandler.send(bytes))
                .subscribe();
    }

    @Override
    public void setLowLowAlarmEnable(String deviceId, boolean enable) {
        Observable.just(LowLowAlarmEnableConverter.request(deviceId, enable))
                .subscribeOn(Schedulers.io())
                .map(bytes -> PacketUtil.getPacket(PacketUtil.CMD_SET_LOW_LOW_ALARM_ENABLE, PacketUtil.TYPE_REQUEST, bytes))
                .map(bytes -> mUdpHandler.send(bytes))
                .subscribe();
    }

    @Override
    public void setLowLowAlarmLimitValue(String deviceId, int value, String[] phones, String sms) {
        Observable.just(LowLowAlarmConverter.request(deviceId, value, phones, sms))
                .subscribeOn(Schedulers.io())
                .map(bytes -> PacketUtil.getPacket(PacketUtil.CMD_SET_LOW_LOW_ALARM_PARAMS, PacketUtil.TYPE_REQUEST, bytes))
                .map(bytes -> mUdpHandler.send(bytes))
                .subscribe();
    }

    @Override
    public void setGps(String deviceId, double lat, double lot) {
        Observable.just(LocationConverter.request(deviceId, lat, lot))
                .subscribeOn(Schedulers.io())
                .map(bytes -> PacketUtil.getPacket(PacketUtil.CMD_SET_LOCATION, PacketUtil.TYPE_REQUEST, bytes))
                .map(bytes -> mUdpHandler.send(bytes))
                .subscribe();
    }

    @Override
    public void setDefenseEnable(String deviceId, boolean enable) {
        Observable.just(DefenseEnableConverter.request(deviceId, enable))
                .subscribeOn(Schedulers.io())
                .map(bytes -> PacketUtil.getPacket(PacketUtil.CMD_SET_DEFENSE_ENABLE, PacketUtil.TYPE_REQUEST, bytes))
                .map(bytes -> mUdpHandler.send(bytes))
                .subscribe();
    }

    @Override
    public void setAliasName(String deviceId, String name) {
        Observable.just(AliasNameConverter.request(deviceId, name))
                .subscribeOn(Schedulers.io())
                .map(bytes -> PacketUtil.getPacket(PacketUtil.CMD_SET_ALIAS_NAME, PacketUtil.TYPE_REQUEST, bytes))
                .map(bytes -> mUdpHandler.send(bytes))
                .subscribe();
    }

    @Override
    public void getHistory(String deviceId, long startTime, long endTime) {
        Observable.just(HistoryConverter.request(deviceId, startTime, endTime))
                .subscribeOn(Schedulers.io())
                .map(bytes -> PacketUtil.getPacket(PacketUtil.CMD_GET_HISTORY, PacketUtil.TYPE_REQUEST, bytes))
                .map(bytes -> mUdpHandler.send(bytes))
                .subscribe();
    }

    @Override
    public void getUser() {
        Observable.just("")
                .subscribeOn(Schedulers.io())
                // 用户名
                .map(device1 -> PacketUtil.getPacket(PacketUtil.CMD_GET_USER_NAME, PacketUtil.TYPE_REQUEST, new byte[0]))
                .map(bytes -> mUdpHandler.send(bytes))
                // 手机号
                .map(device1 -> PacketUtil.getPacket(PacketUtil.CMD_GET_USER_PHONE, PacketUtil.TYPE_REQUEST, new byte[0]))
                .map(bytes -> mUdpHandler.send(bytes))
                .subscribe();
    }

    @Override
    public void setUsername(String username) {
        Observable.just(username)
                .subscribeOn(Schedulers.io())
                .map(username1 -> PacketUtil.getPacket(PacketUtil.CMD_SET_NAME, PacketUtil.TYPE_REQUEST, UsernameConverter.request(username1)))
                .map(bytes -> mUdpHandler.send(bytes))
                .subscribe();
    }

    @Override
    public void setMobile(String mobile) {
        Observable.just(mobile)
                .subscribeOn(Schedulers.io())
                .map(mobile1 -> PacketUtil.getPacket(PacketUtil.CMD_SET_PHONE, PacketUtil.TYPE_REQUEST, UsernameConverter.request(mobile1)))
                .map(bytes -> mUdpHandler.send(bytes))
                .subscribe();
    }

    @Override
    public void setPassword(String oldPwd, String newPwd) {
        Observable.just(PasswordConverter.request(oldPwd, newPwd))
                .subscribeOn(Schedulers.io())
                .map(bytes -> PacketUtil.getPacket(PacketUtil.CMD_SET_PASSWORD, PacketUtil.TYPE_REQUEST, bytes))
                .map(bytes -> mUdpHandler.send(bytes))
                .subscribe();
    }

    @Override
    public void logout() {
        Observable.just(new byte[0])
                .subscribeOn(Schedulers.io())
                .map(bytes -> PacketUtil.getPacket(PacketUtil.CMD_LOGOUT, PacketUtil.TYPE_REQUEST, bytes))
                .map(bytes -> mUdpHandler.send(bytes))
                .subscribe();
    }

}
