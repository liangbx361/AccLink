package com.out.accu.link.data.remote;

import com.out.accu.link.data.DataService;
import com.out.accu.link.data.config.Platform;
import com.out.accu.link.data.converter.AliasNameConverter;
import com.out.accu.link.data.converter.ChannelRangeConverter;
import com.out.accu.link.data.converter.DefenseEnableConverter;
import com.out.accu.link.data.converter.DeviceListConverter;
import com.out.accu.link.data.converter.LocationConverter;
import com.out.accu.link.data.converter.LoginConverter;
import com.out.accu.link.data.converter.LowAlarmConverter;
import com.out.accu.link.data.converter.LowAlarmEnableConverter;
import com.out.accu.link.data.converter.LowLowAlarmConverter;
import com.out.accu.link.data.converter.LowLowAlarmEnableConverter;
import com.out.accu.link.data.converter.LteStatusConverter;
import com.out.accu.link.data.converter.PhoneNumberConverter;
import com.out.accu.link.data.converter.ReportPeriodConverter;
import com.out.accu.link.data.converter.TemConverter;
import com.out.accu.link.data.converter.TxConverter;
import com.out.accu.link.data.converter.ValueRangeConverter;
import com.out.accu.link.data.mode.Device;
import com.out.accu.link.data.mode.DeviceHistory;
import com.out.accu.link.data.mode.Login;
import com.out.accu.link.data.udp.UdpHandler;
import com.out.accu.link.data.util.ByteUtil;
import com.out.accu.link.data.util.PacketUtil;

import java.util.List;

import io.reactivex.Observable;

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

    public RemoteService(Platform platform) {
        mUdpHandler = new UdpHandler(platform.getInet(), platform.getPort());
//        mUdpHandler.startReceive();
    }

    @Override
    public Observable<Login> login(String userName, String password) {
        return Observable.defer(() ->
                Observable.just(LoginConverter.request(userName, password)))
                .map(bytes -> PacketUtil.getPacket(PacketUtil.CMD_LOGIN, PacketUtil.TYPE_REQUEST, bytes))
                .map(bytes -> mUdpHandler.send(bytes))
                .map(success -> PacketUtil.parserPacket(mUdpHandler.receive()))
                .map(LoginConverter::response);
                // .doOnNext(login -> mUdpHandler.send(PacketUtil.receive()));
    }

    @Override
    public Observable<List<Device>> getDevices() {
        return Observable.just(new byte[0])
                .map(bytes -> PacketUtil.getPacket(PacketUtil.CMD_GET_DEVICES, PacketUtil.TYPE_REQUEST, bytes))
                .map(bytes -> mUdpHandler.send(bytes))
                .map(success -> PacketUtil.parserPacket(mUdpHandler.receive()))
                .map(DeviceListConverter::response)
                .doOnNext(devices -> {
                    for(Device device : devices) {
                        getDevice(device).subscribe(device1 -> {

                        });
                    }
                });
    }

    @Override
    public Observable<Device> getDevice(Device device) {
        byte[] deviceId = ByteUtil.stringToByte(device.id, 6);
        return Observable.just(device)
                // 两路量程
                .map(device1 -> PacketUtil.getPacket(PacketUtil.CMD_GET_CHANNEL_RANGE, PacketUtil.TYPE_REQUEST, deviceId))
                .map(bytes -> mUdpHandler.send(bytes))
                .map(success -> PacketUtil.parserPacket(mUdpHandler.receive()))
                .map(response -> ChannelRangeConverter.response(device, response))
                // 量程
                .map(device1 -> PacketUtil.getPacket(PacketUtil.CMD_GET_VALUE_RANGE, PacketUtil.TYPE_REQUEST, deviceId))
                .map(bytes -> mUdpHandler.send(bytes))
                .map(success -> PacketUtil.parserPacket(mUdpHandler.receive()))
                .map(response -> ValueRangeConverter.response(device, response))
                //上报周期
                .map(device1 -> PacketUtil.getPacket(PacketUtil.CMD_GET_REPORT_PERIOD, PacketUtil.TYPE_REQUEST, deviceId))
                .map(bytes -> mUdpHandler.send(bytes))
                .map(success -> PacketUtil.parserPacket(mUdpHandler.receive()))
                .map(response -> ReportPeriodConverter.response(device, response))
                //低报参数
                .map(device1 -> PacketUtil.getPacket(PacketUtil.CMD_GET_LOW_ALARM_ENABLE, PacketUtil.TYPE_REQUEST, deviceId))
                .map(bytes -> mUdpHandler.send(bytes))
                .map(success -> PacketUtil.parserPacket(mUdpHandler.receive()))
                .map(response -> LowAlarmEnableConverter.response(device, response))
                //
                .map(device1 -> PacketUtil.getPacket(PacketUtil.CMD_GET_LOW_ALARM_PARAMS, PacketUtil.TYPE_REQUEST, deviceId))
                .map(bytes -> mUdpHandler.send(bytes))
                .map(success -> PacketUtil.parserPacket(mUdpHandler.receive()))
                .map(response -> LowAlarmConverter.response(device, response))
                //
                .map(device1 -> PacketUtil.getPacket(PacketUtil.CMD_GET_LOW_LOW_ALARM_ENABLE, PacketUtil.TYPE_REQUEST, deviceId))
                .map(bytes -> mUdpHandler.send(bytes))
                .map(success -> PacketUtil.parserPacket(mUdpHandler.receive()))
                .map(response -> LowLowAlarmEnableConverter.response(device, response))
                //
                .map(device1 -> PacketUtil.getPacket(PacketUtil.CMD_GET_LOW_LOW_ALARM_PARAMS, PacketUtil.TYPE_REQUEST, deviceId))
                .map(bytes -> mUdpHandler.send(bytes))
                .map(success -> PacketUtil.parserPacket(mUdpHandler.receive()))
                .map(response -> LowLowAlarmConverter.response(device, response))
                //
                .map(device1 -> PacketUtil.getPacket(PacketUtil.CMD_GET_LOW_ALARM_PARAMS, PacketUtil.TYPE_REQUEST, deviceId))
                .map(bytes -> mUdpHandler.send(bytes))
                .map(success -> PacketUtil.parserPacket(mUdpHandler.receive()))
                .map(response -> LowAlarmConverter.response(device, response))
                // 别名
                .map(device1 -> PacketUtil.getPacket(PacketUtil.CMD_GET_ALIAS_NAME, PacketUtil.TYPE_REQUEST, deviceId))
                .map(bytes -> mUdpHandler.send(bytes))
                .map(success -> PacketUtil.parserPacket(mUdpHandler.receive()))
                .map(response -> AliasNameConverter.response(device, response))
                //
                .map(device1 -> PacketUtil.getPacket(PacketUtil.CMD_GET_LOCATION, PacketUtil.TYPE_REQUEST, deviceId))
                .map(bytes -> mUdpHandler.send(bytes))
                .map(success -> PacketUtil.parserPacket(mUdpHandler.receive()))
                .map(response -> LocationConverter.response(device, response))
                //
                .map(device1 -> PacketUtil.getPacket(PacketUtil.CMD_GET_DEFENSE_ENABLE, PacketUtil.TYPE_REQUEST, deviceId))
                .map(bytes -> mUdpHandler.send(bytes))
                .map(success -> PacketUtil.parserPacket(mUdpHandler.receive()))
                .map(response -> DefenseEnableConverter.response(device, response))
                // CMD_GET_TEM
                .map(device1 -> PacketUtil.getPacket(PacketUtil.CMD_GET_TEM, PacketUtil.TYPE_REQUEST, deviceId))
                .map(bytes -> mUdpHandler.send(bytes))
                .map(success -> PacketUtil.parserPacket(mUdpHandler.receive()))
                .map(response -> TemConverter.response(device, response))
                // CMD_GET_PHONE_NUMBER
                .map(device1 -> PacketUtil.getPacket(PacketUtil.CMD_GET_PHONE_NUMBER, PacketUtil.TYPE_REQUEST, deviceId))
                .map(bytes -> mUdpHandler.send(bytes))
                .map(success -> PacketUtil.parserPacket(mUdpHandler.receive()))
                .map(response -> PhoneNumberConverter.response(device, response))
                // CMD_GET_LTE_STATUS
                .map(device1 -> PacketUtil.getPacket(PacketUtil.CMD_GET_LTE_STATUS, PacketUtil.TYPE_REQUEST, deviceId))
                .map(bytes -> mUdpHandler.send(bytes))
                .map(success -> PacketUtil.parserPacket(mUdpHandler.receive()))
                .map(response -> LteStatusConverter.response(device, response))
                // CMD_GET_TX
                .map(device1 -> PacketUtil.getPacket(PacketUtil.CMD_GET_TX, PacketUtil.TYPE_REQUEST, deviceId))
                .map(bytes -> mUdpHandler.send(bytes))
                .map(success -> PacketUtil.parserPacket(mUdpHandler.receive()))
                .map(response -> TxConverter.response(device, response));

    }

    @Override
    public Observable<DeviceHistory> getDeviceHistory(String deviceId, long beginTime, long endTime) {
        return null;
    }

    @Override
    public Observable<Boolean> setChannel(String deviceId, int value1, int value2) {

        return null;
    }

    @Override
    public Observable<Boolean> setValue(String deviceId, int value) {
        return null;
    }

    @Override
    public Observable<Boolean> setReportPeriod(String deviceId, int value) {
        return null;
    }

    @Override
    public Observable<Boolean> setLowAlarmEnable(String deviceId, boolean enable) {
        return null;
    }

    @Override
    public Observable<Boolean> setLowAlarmLimitValue(String deviceId, int value, String[] phones, String sms) {
        return null;
    }

    @Override
    public Observable<Boolean> setLowLowAlarmEnable(String deviceId, boolean enable) {
        return null;
    }

    @Override
    public Observable<Boolean> setLowLowAlarmLimitValue(String deviceId, int value, String[] phones, String sms) {
        return null;
    }

    @Override
    public Observable<Boolean> setGps(String deviceId, double lat, double lot) {
        return null;
    }

    @Override
    public Observable<Boolean> setDefenseEnable(String deviceId, boolean enable) {
        return null;
    }

    @Override
    public Observable<Boolean> setAliasName(String deviceId, String name) {
        return null;
    }

    @Override
    public Observable<List<DeviceHistory>> getHistory(long startTime, long endTime) {
        return null;
    }

}
