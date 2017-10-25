package com.out.accu.link.data.mock;

import com.cyou17173.android.arch.base.app.Smart;
import com.cyou17173.android.component.common.util.file.AssetsUtil;
import com.cyou17173.android.component.common.util.jackson.MapperUtil;
import com.out.accu.link.data.DataService;
import com.out.accu.link.data.mode.Device;
import com.out.accu.link.data.mode.DeviceHistory;
import com.out.accu.link.data.mode.Login;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/10/13
 */

public class MockService implements DataService {

    @Override
    public Observable<Login> login(String userName, String password) {
        Login login = new Login();
        login.isSuccess = true;
        return Observable.just(login);
    }

    @Override
    public Observable<List<Device>> getDevices() {
        return Observable.just("mock/device_list.json")
                .delay(getMockTime(), TimeUnit.SECONDS)
                .map(file -> AssetsUtil.getStringFromFile(Smart.getApp(), file))
                .map(data -> MapperUtil.stringMapToList(data, Device.class));
    }

    @Override
    public Observable<Device> getDevice(String id) {
        return null;
    }

    @Override
    public Observable<DeviceHistory> getDeviceHistory(String id, long beginTime, long endTime) {
        return Observable.just("mock/device_history.json")
                .delay(getMockTime(), TimeUnit.SECONDS)
                .map(file -> AssetsUtil.getStringFromFile(Smart.getApp(), file))
                .map(data -> MapperUtil.stringMapToObject(data, DeviceHistory.class));
    }

    @Override
    public Observable<Boolean> setChannel(String deviceId, int value1, int value2) {
        return Observable.just(true)
                .delay(getMockTime(), TimeUnit.SECONDS);
    }

    @Override
    public Observable<Boolean> setValue(String deviceId, int value) {
        return Observable.just(true)
                .delay(getMockTime(), TimeUnit.SECONDS);
    }

    @Override
    public Observable<Boolean> setReportPeriod(String deviceId, int value) {
        return Observable.just(true)
                .delay(getMockTime(), TimeUnit.SECONDS);
    }

    @Override
    public Observable<Boolean> setLowAlarmEnable(String deviceId, boolean enable) {
        return Observable.just(true)
                .delay(getMockTime(), TimeUnit.SECONDS);
    }

    @Override
    public Observable<Boolean> setLowAlarmLimitValue(String deviceId, int value, String[] phones, String sms) {
        return Observable.just(true)
                .delay(getMockTime(), TimeUnit.SECONDS);
    }

    @Override
    public Observable<Boolean> setLowLowAlarmEnable(String deviceId, boolean enable) {
        return Observable.just(true)
                .delay(getMockTime(), TimeUnit.SECONDS);
    }

    @Override
    public Observable<Boolean> setLowLowAlarmLimitValue(String deviceId, int value, String[] phones, String sms) {
        return Observable.just(true)
                .delay(getMockTime(), TimeUnit.SECONDS);
    }

    @Override
    public Observable<Boolean> setGps(String deviceId, double lat, double lot) {
        return Observable.just(true)
                .delay(getMockTime(), TimeUnit.SECONDS);
    }

    @Override
    public Observable<Boolean> setDefenseEnable(String deviceId, boolean enable) {
        return Observable.just(true)
                .delay(getMockTime(), TimeUnit.SECONDS);
    }

    @Override
    public Observable<Boolean> setAliasName(String deviceId, String name) {
        return Observable.just(true)
                .delay(getMockTime(), TimeUnit.SECONDS);
    }

    @Override
    public Observable<List<DeviceHistory>> getHistory(long startTime, long endTime) {
        return Observable.just("mock/device_history.json")
                .delay(getMockTime(), TimeUnit.SECONDS)
                .map(file -> AssetsUtil.getStringFromFile(Smart.getApp(), file))
                .map(data -> MapperUtil.stringMapToList(data, DeviceHistory.class));
    }

    private int getMockTime() {
        return new Random().nextInt(3);
    }
}
