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
import com.out.accu.link.data.udp.TaskRecode;
import com.out.accu.link.data.udp.UdpHandler;
import com.out.accu.link.data.util.PacketUtil;

import java.net.InetAddress;
import java.net.UnknownHostException;

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

    private void send(byte[] cmd) {
        send(cmd, new byte[0]);
    }

    private void send(byte[] cmd, byte[] data) {
        short id = TaskRecode.getInstance().getId();
        byte[] reqData = PacketUtil.getPacket(cmd, id, PacketUtil.TYPE_REQUEST, data);
        TaskRecode.getInstance().addRecode(cmd, id, reqData);
    }

    @Override
    public void login(LoginInfo loginInfo) {
        if (mUdpHandler != null) {
            mUdpHandler.stop();
        }
        mUdpHandler = new UdpHandler(getInet(loginInfo.server), loginInfo.port);
        mUdpHandler.startSend();
        mUdpHandler.startReceive();

        send(PacketUtil.CMD_LOGIN, LoginConverter.request(loginInfo.user, loginInfo.password));
    }

    @Override
    public void getDevices() {
        send(PacketUtil.CMD_GET_DEVICES);
    }

    @Override
    public void getDevice(Device device) {
        byte[] deviceId = device.idBytes;
        send(PacketUtil.CMD_GET_CHANNEL_RANGE, deviceId);
        send(PacketUtil.CMD_GET_VALUE_RANGE, deviceId);
        send(PacketUtil.CMD_GET_REPORT_PERIOD, deviceId);
        send(PacketUtil.CMD_GET_LOW_ALARM_ENABLE, deviceId);
        send(PacketUtil.CMD_GET_LOW_ALARM_PARAMS, deviceId);
        send(PacketUtil.CMD_GET_LOW_LOW_ALARM_ENABLE, deviceId);
        send(PacketUtil.CMD_GET_LOW_LOW_ALARM_PARAMS, deviceId);
        send(PacketUtil.CMD_GET_ALIAS_NAME, deviceId);
        send(PacketUtil.CMD_GET_LOCATION, deviceId);
        send(PacketUtil.CMD_GET_DEFENSE_ENABLE, deviceId);
        send(PacketUtil.CMD_GET_TEM, deviceId);
        send(PacketUtil.CMD_GET_PHONE_NUMBER, deviceId);
        send(PacketUtil.CMD_GET_LTE_STATUS, deviceId);
        send(PacketUtil.CMD_GET_TX, deviceId);
    }

    @Override
    public void getDeviceHistory(byte[] deviceId, long beginTime, long endTime) {
        send(PacketUtil.CMD_GET_HISTORY, HistoryConverter.request(deviceId, beginTime, endTime));
    }

    @Override
    public void setChannel(byte[] deviceId, int value1, int value2) {
        send(PacketUtil.CMD_SET_CHANNEL_RANGE, ChannelRangeConverter.request(deviceId, value1, value2));
    }

    @Override
    public void setValue(byte[] deviceId, int value) {
        send(PacketUtil.CMD_SET_VALUE_RANGE, ValueRangeConverter.request(deviceId, value));
    }

    @Override
    public void setReportPeriod(byte[] deviceId, int value) {
        send(PacketUtil.CMD_SET_REPORT_PERIOD, ReportPeriodConverter.request(deviceId, value));
    }

    @Override
    public void setLowAlarmEnable(byte[] deviceId, boolean enable) {
        send(PacketUtil.CMD_SET_LOW_ALARM_ENABLE, LowAlarmEnableConverter.request(deviceId, enable));
    }

    @Override
    public void setLowAlarmLimitValue(byte[] deviceId, int value, String[] phones, String sms) {
        send(PacketUtil.CMD_SET_LOW_ALARM_PARAMS, LowAlarmConverter.request(deviceId, value, phones, sms));
    }

    @Override
    public void setLowLowAlarmEnable(byte[] deviceId, boolean enable) {
        send(PacketUtil.CMD_SET_LOW_LOW_ALARM_ENABLE, LowLowAlarmEnableConverter.request(deviceId, enable));
    }

    @Override
    public void setLowLowAlarmLimitValue(byte[] deviceId, int value, String[] phones, String sms) {
        send(PacketUtil.CMD_SET_LOW_LOW_ALARM_PARAMS, LowLowAlarmConverter.request(deviceId, value, phones, sms));
    }

    @Override
    public void setGps(byte[] deviceId, double lat, double lot) {
        send(PacketUtil.CMD_SET_LOCATION, LocationConverter.request(deviceId, lat, lot));
    }

    @Override
    public void setDefenseEnable(byte[] deviceId, boolean enable) {
        send(PacketUtil.CMD_SET_DEFENSE_ENABLE, DefenseEnableConverter.request(deviceId, enable));
    }

    @Override
    public void setAliasName(byte[] deviceId, String name) {
        send(PacketUtil.CMD_SET_ALIAS_NAME, AliasNameConverter.request(deviceId, name));
    }

    @Override
    public void getHistory(byte[] deviceId, long startTime, long endTime) {
        send(PacketUtil.CMD_GET_HISTORY, HistoryConverter.request(deviceId, startTime, endTime));
    }

    @Override
    public void getUser() {
        send(PacketUtil.CMD_GET_USER_NAME);
        send(PacketUtil.CMD_GET_USER_PHONE);
    }

    @Override
    public void setUsername(String username) {
        send(PacketUtil.CMD_SET_NAME, UsernameConverter.request(username));
    }

    @Override
    public void setMobile(String mobile) {
        send(PacketUtil.CMD_SET_PHONE, UsernameConverter.request(mobile));
    }

    @Override
    public void setPassword(String oldPwd, String newPwd) {
        send(PacketUtil.CMD_SET_PASSWORD, PasswordConverter.request(oldPwd, newPwd));
    }

    @Override
    public void logout() {
        send(PacketUtil.CMD_LOGOUT);
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
