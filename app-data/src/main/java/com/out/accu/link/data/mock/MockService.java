package com.out.accu.link.data.mock;

import com.out.accu.link.data.DataService;
import com.out.accu.link.data.mode.Device;

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
    public void login(String userName, String password) {

    }

    @Override
    public void getDevices() {

    }

    @Override
    public void getDevice(Device device) {

    }

    @Override
    public void getDeviceHistory(String deviceId, long beginTime, long endTime) {

    }

    @Override
    public void setChannel(String deviceId, int value1, int value2) {

    }

    @Override
    public void setValue(String deviceId, int value) {

    }

    @Override
    public void setReportPeriod(String deviceId, int value) {

    }

    @Override
    public void setLowAlarmEnable(String deviceId, boolean enable) {

    }

    @Override
    public void setLowAlarmLimitValue(String deviceId, int value, String[] phones, String sms) {

    }

    @Override
    public void setLowLowAlarmEnable(String deviceId, boolean enable) {

    }

    @Override
    public void setLowLowAlarmLimitValue(String deviceId, int value, String[] phones, String sms) {

    }

    @Override
    public void setGps(String deviceId, double lat, double lot) {

    }

    @Override
    public void setDefenseEnable(String deviceId, boolean enable) {

    }

    @Override
    public void setAliasName(String deviceId, String name) {

    }

    @Override
    public void getHistory(String deviceId, long startTime, long endTime) {

    }

    @Override
    public void getUser() {

    }

    @Override
    public void setUsername(String username) {

    }

    @Override
    public void setMobile(String mobile) {

    }

    @Override
    public void setPassword(String oldPwd, String newPwd) {

    }

    @Override
    public void logout() {

    }

//    @Override
//    public Observable<Login> login(String userName, String password) {
//        Login login = new Login();
//        login.isSuccess = true;
//        return Observable.just(login);
//    }
//
//    @Override
//    public Observable<List<Device>> getDevices() {
//        return Observable.just("mock/device_list.json")
//                .delay(getMockTime(), TimeUnit.SECONDS)
//                .map(file -> AssetsUtil.getStringFromFile(Smart.getApp(), file))
//                .map(data -> MapperUtil.stringMapToList(data, Device.class));
//    }
//
//    @Override
//    public Observable<Device> getDevice(Device device) {
//        return null;
//    }
//
//    @Override
//    public Observable<DeviceHistory> getDeviceHistory(String id, long beginTime, long endTime) {
//        return Observable.just("mock/device_history.json")
//                .delay(getMockTime(), TimeUnit.SECONDS)
//                .map(file -> AssetsUtil.getStringFromFile(Smart.getApp(), file))
//                .map(data -> MapperUtil.stringMapToObject(data, DeviceHistory.class));
//    }
//
//    @Override
//    public Observable<Boolean> setChannel(String deviceId, int value1, int value2) {
//        return Observable.just(true)
//                .delay(getMockTime(), TimeUnit.SECONDS);
//    }
//
//    @Override
//    public Observable<Boolean> setValue(String deviceId, int value) {
//        return Observable.just(true)
//                .delay(getMockTime(), TimeUnit.SECONDS);
//    }
//
//    @Override
//    public Observable<Boolean> setReportPeriod(String deviceId, int value) {
//        return Observable.just(true)
//                .delay(getMockTime(), TimeUnit.SECONDS);
//    }
//
//    @Override
//    public Observable<Boolean> setLowAlarmEnable(String deviceId, boolean enable) {
//        return Observable.just(true)
//                .delay(getMockTime(), TimeUnit.SECONDS);
//    }
//
//    @Override
//    public Observable<Boolean> setLowAlarmLimitValue(String deviceId, int value, String[] phones, String sms) {
//        return Observable.just(true)
//                .delay(getMockTime(), TimeUnit.SECONDS);
//    }
//
//    @Override
//    public Observable<Boolean> setLowLowAlarmEnable(String deviceId, boolean enable) {
//        return Observable.just(true)
//                .delay(getMockTime(), TimeUnit.SECONDS);
//    }
//
//    @Override
//    public Observable<Boolean> setLowLowAlarmLimitValue(String deviceId, int value, String[] phones, String sms) {
//        return Observable.just(true)
//                .delay(getMockTime(), TimeUnit.SECONDS);
//    }
//
//    @Override
//    public Observable<Boolean> setGps(String deviceId, double lat, double lot) {
//        return Observable.just(true)
//                .delay(getMockTime(), TimeUnit.SECONDS);
//    }
//
//    @Override
//    public Observable<Boolean> setDefenseEnable(String deviceId, boolean enable) {
//        return Observable.just(true)
//                .delay(getMockTime(), TimeUnit.SECONDS);
//    }
//
//    @Override
//    public Observable<Boolean> setAliasName(String deviceId, String name) {
//        return Observable.just(true)
//                .delay(getMockTime(), TimeUnit.SECONDS);
//    }
//
//    @Override
//    public Observable<List<DeviceHistory>> getHistory(String deviceId, long startTime, long endTime) {
//        return null;
//    }
//
//    @Override
//    public Observable<User> getUser() {
//        return null;
//    }
//
//    @Override
//    public Observable<Boolean> setUsername(String username) {
//        return null;
//    }
//
//    @Override
//    public Observable<Boolean> setMobile(String mobile) {
//        return null;
//    }
//
//    @Override
//    public Observable<Boolean> setPassword(String oldPwd, String newPwd) {
//        return null;
//    }
//
//    @Override
//    public Observable<Boolean> logout() {
//        return null;
//    }
//
//    private int getMockTime() {
//        return new Random().nextInt(3);
//    }
}
