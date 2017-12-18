package com.out.accu.link.data.mock;

import com.out.accu.link.data.DataService;
import com.out.accu.link.data.mode.Device;
import com.out.accu.link.data.mode.LoginInfo;

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
    public void login(LoginInfo loginInfo) {

    }

    @Override
    public void getDevices() {

    }

    @Override
    public void getDevice(Device device) {

    }

    @Override
    public void getDeviceHistory(byte[] deviceId, long beginTime, long endTime) {

    }

    @Override
    public void setChannel(byte[] deviceId, int value1, int value2) {

    }

    @Override
    public void setValue(byte[] deviceId, int value) {

    }

    @Override
    public void setReportPeriod(byte[] deviceId, int value) {

    }

    @Override
    public void setLowAlarmEnable(byte[] deviceId, boolean enable) {

    }

    @Override
    public void setLowAlarmLimitValue(byte[] deviceId, int value, String[] phones, String sms) {

    }

    @Override
    public void setLowLowAlarmEnable(byte[] deviceId, boolean enable) {

    }

    @Override
    public void setLowLowAlarmLimitValue(byte[] deviceId, int value, String[] phones, String sms) {

    }

    @Override
    public void setGps(byte[] deviceId, double lat, double lot) {

    }

    @Override
    public void setDefenseEnable(byte[] deviceId, boolean enable) {

    }

    @Override
    public void setAliasName(byte[] deviceId, String name) {

    }

    @Override
    public void getHistory(byte[] deviceId, long startTime, long endTime) {

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

    @Override
    public void saveLoginInfo(LoginInfo loginInfo) {

    }

    @Override
    public LoginInfo getLoginInfo() {
        return null;
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
//    public Observable<DeviceHistory> getDeviceHistory(String reqId, long beginTime, long endTime) {
//        return Observable.just("mock/device_history.json")
//                .delay(getMockTime(), TimeUnit.SECONDS)
//                .map(file -> AssetsUtil.getStringFromFile(Smart.getApp(), file))
//                .map(data -> MapperUtil.stringMapToObject(data, DeviceHistory.class));
//    }
//
//    @Override
//    public Observable<Boolean> setChannel(byte[] deviceId, int value1, int value2) {
//        return Observable.just(true)
//                .delay(getMockTime(), TimeUnit.SECONDS);
//    }
//
//    @Override
//    public Observable<Boolean> setValue(byte[] deviceId, int value) {
//        return Observable.just(true)
//                .delay(getMockTime(), TimeUnit.SECONDS);
//    }
//
//    @Override
//    public Observable<Boolean> setReportPeriod(byte[] deviceId, int value) {
//        return Observable.just(true)
//                .delay(getMockTime(), TimeUnit.SECONDS);
//    }
//
//    @Override
//    public Observable<Boolean> setLowAlarmEnable(byte[] deviceId, boolean enable) {
//        return Observable.just(true)
//                .delay(getMockTime(), TimeUnit.SECONDS);
//    }
//
//    @Override
//    public Observable<Boolean> setLowAlarmLimitValue(byte[] deviceId, int value, String[] phones, String sms) {
//        return Observable.just(true)
//                .delay(getMockTime(), TimeUnit.SECONDS);
//    }
//
//    @Override
//    public Observable<Boolean> setLowLowAlarmEnable(byte[] deviceId, boolean enable) {
//        return Observable.just(true)
//                .delay(getMockTime(), TimeUnit.SECONDS);
//    }
//
//    @Override
//    public Observable<Boolean> setLowLowAlarmLimitValue(byte[] deviceId, int value, String[] phones, String sms) {
//        return Observable.just(true)
//                .delay(getMockTime(), TimeUnit.SECONDS);
//    }
//
//    @Override
//    public Observable<Boolean> setGps(byte[] deviceId, double lat, double lot) {
//        return Observable.just(true)
//                .delay(getMockTime(), TimeUnit.SECONDS);
//    }
//
//    @Override
//    public Observable<Boolean> setDefenseEnable(byte[] deviceId, boolean enable) {
//        return Observable.just(true)
//                .delay(getMockTime(), TimeUnit.SECONDS);
//    }
//
//    @Override
//    public Observable<Boolean> setAliasName(byte[] deviceId, String name) {
//        return Observable.just(true)
//                .delay(getMockTime(), TimeUnit.SECONDS);
//    }
//
//    @Override
//    public Observable<List<DeviceHistory>> getHistory(byte[] deviceId, long startTime, long endTime) {
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
