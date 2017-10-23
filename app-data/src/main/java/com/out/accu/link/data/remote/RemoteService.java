package com.out.accu.link.data.remote;

import com.out.accu.link.data.DataService;
import com.out.accu.link.data.config.Platform;
import com.out.accu.link.data.converter.LoginConverter;
import com.out.accu.link.data.mode.Device;
import com.out.accu.link.data.mode.DeviceHistory;
import com.out.accu.link.data.mode.Login;
import com.out.accu.link.data.udp.UdpHandler;
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
    }

    @Override
    public Observable<List<Device>> getDevices() {
        return null;
    }

    @Override
    public Observable<Device> getDevice(String id) {
        return null;
    }

    @Override
    public Observable<DeviceHistory> getDeviceHistory(String id, long beginTime, long endTime) {
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


}
