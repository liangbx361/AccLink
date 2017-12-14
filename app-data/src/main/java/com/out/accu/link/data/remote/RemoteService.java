package com.out.accu.link.data.remote;

import com.cyou17173.android.arch.base.app.Smart;
import com.out.accu.link.data.DataService;
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
import com.out.accu.link.data.mode.LoginInfo;
import com.out.accu.link.data.udp.UdpHandler;
import com.out.accu.link.data.util.PacketUtil;

import java.net.InetAddress;
import java.net.UnknownHostException;

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

    public RemoteService() {

    }

    public InetAddress getInet(String host) {
        try {
            return InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void login(LoginInfo loginInfo) {
        if(mUdpHandler != null) {
            mUdpHandler.stopReceive();
        }
        mUdpHandler = new UdpHandler(getInet(loginInfo.server), loginInfo.port);
        mUdpHandler.startReceive();

        Observable.just(LoginConverter.request(loginInfo.user, loginInfo.password))
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
        byte[] deviceId = device.idBytes;
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
    public void getDeviceHistory(byte[] deviceId, long beginTime, long endTime) {

    }

    @Override
    public void setChannel(byte[] deviceId, int value1, int value2) {
        Observable.just(ChannelRangeConverter.request(deviceId, value1, value2))
                .subscribeOn(Schedulers.io())
                .map(bytes -> PacketUtil.getPacket(PacketUtil.CMD_SET_CHANNEL_RANGE, PacketUtil.TYPE_REQUEST, bytes))
                .map(bytes -> mUdpHandler.send(bytes))
                .subscribe();
    }

    @Override
    public void setValue(byte[] deviceId, int value) {
        Observable.just(ValueRangeConverter.request(deviceId, value))
                .subscribeOn(Schedulers.io())
                .map(bytes -> PacketUtil.getPacket(PacketUtil.CMD_SET_VALUE_RANGE, PacketUtil.TYPE_REQUEST, bytes))
                .map(bytes -> mUdpHandler.send(bytes))
                .subscribe();
    }

    @Override
    public void setReportPeriod(byte[] deviceId, int value) {
        Observable.just(ReportPeriodConverter.request(deviceId, value))
                .subscribeOn(Schedulers.io())
                .map(bytes -> PacketUtil.getPacket(PacketUtil.CMD_SET_REPORT_PERIOD, PacketUtil.TYPE_REQUEST, bytes))
                .map(bytes -> mUdpHandler.send(bytes))
                .subscribe();
    }

    @Override
    public void setLowAlarmEnable(byte[] deviceId, boolean enable) {
        Observable.just(LowAlarmEnableConverter.request(deviceId, enable))
                .subscribeOn(Schedulers.io())
                .map(bytes -> PacketUtil.getPacket(PacketUtil.CMD_SET_LOW_ALARM_ENABLE, PacketUtil.TYPE_REQUEST, bytes))
                .map(bytes -> mUdpHandler.send(bytes))
                .subscribe();
    }

    @Override
    public void setLowAlarmLimitValue(byte[] deviceId, int value, String[] phones, String sms) {
        Observable.just(LowAlarmConverter.request(deviceId, value, phones, sms))
                .subscribeOn(Schedulers.io())
                .map(bytes -> PacketUtil.getPacket(PacketUtil.CMD_SET_LOW_ALARM_PARAMS, PacketUtil.TYPE_REQUEST, bytes))
                .map(bytes -> mUdpHandler.send(bytes))
                .subscribe();
    }

    @Override
    public void setLowLowAlarmEnable(byte[] deviceId, boolean enable) {
        Observable.just(LowLowAlarmEnableConverter.request(deviceId, enable))
                .subscribeOn(Schedulers.io())
                .map(bytes -> PacketUtil.getPacket(PacketUtil.CMD_SET_LOW_LOW_ALARM_ENABLE, PacketUtil.TYPE_REQUEST, bytes))
                .map(bytes -> mUdpHandler.send(bytes))
                .subscribe();
    }

    @Override
    public void setLowLowAlarmLimitValue(byte[] deviceId, int value, String[] phones, String sms) {
        Observable.just(LowLowAlarmConverter.request(deviceId, value, phones, sms))
                .subscribeOn(Schedulers.io())
                .map(bytes -> PacketUtil.getPacket(PacketUtil.CMD_SET_LOW_LOW_ALARM_PARAMS, PacketUtil.TYPE_REQUEST, bytes))
                .map(bytes -> mUdpHandler.send(bytes))
                .subscribe();
    }

    @Override
    public void setGps(byte[] deviceId, double lat, double lot) {
        Observable.just(LocationConverter.request(deviceId, lat, lot))
                .subscribeOn(Schedulers.io())
                .map(bytes -> PacketUtil.getPacket(PacketUtil.CMD_SET_LOCATION, PacketUtil.TYPE_REQUEST, bytes))
                .map(bytes -> mUdpHandler.send(bytes))
                .subscribe();
    }

    @Override
    public void setDefenseEnable(byte[] deviceId, boolean enable) {
        Observable.just(DefenseEnableConverter.request(deviceId, enable))
                .subscribeOn(Schedulers.io())
                .map(bytes -> PacketUtil.getPacket(PacketUtil.CMD_SET_DEFENSE_ENABLE, PacketUtil.TYPE_REQUEST, bytes))
                .map(bytes -> mUdpHandler.send(bytes))
                .subscribe();
    }

    @Override
    public void setAliasName(byte[] deviceId, String name) {
        Observable.just(AliasNameConverter.request(deviceId, name))
                .subscribeOn(Schedulers.io())
                .map(bytes -> PacketUtil.getPacket(PacketUtil.CMD_SET_ALIAS_NAME, PacketUtil.TYPE_REQUEST, bytes))
                .map(bytes -> mUdpHandler.send(bytes))
                .subscribe();
    }

    @Override
    public void getHistory(byte[] deviceId, long startTime, long endTime) {
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

    @Override
    public void saveLoginInfo(LoginInfo loginInfo) {
        Smart.getApp().getConfig().getAppConfig().saveObject("loginInfo", loginInfo);
    }

    @Override
    public LoginInfo getLoginInfo() {
        return Smart.getApp().getConfig().getAppConfig().readObject("loginInfo", LoginInfo.class);
    }

}
