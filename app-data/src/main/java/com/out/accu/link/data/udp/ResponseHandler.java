package com.out.accu.link.data.udp;

import android.util.Log;

import com.cyou17173.android.arch.base.bus.SmartBus;
import com.cyou17173.android.arch.base.page.SmartTransformer;
import com.out.accu.link.data.BusAction;
import com.out.accu.link.data.DataManager;
import com.out.accu.link.data.converter.AliasNameConverter;
import com.out.accu.link.data.converter.ChannelRangeConverter;
import com.out.accu.link.data.converter.DefenseEnableConverter;
import com.out.accu.link.data.converter.DeviceListConverter;
import com.out.accu.link.data.converter.HistoryConverter;
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
import com.out.accu.link.data.converter.UploadOnlineConverter;
import com.out.accu.link.data.converter.UploadValueConverter;
import com.out.accu.link.data.converter.UserMobileConverter;
import com.out.accu.link.data.converter.UsernameConverter;
import com.out.accu.link.data.converter.ValueRangeConverter;
import com.out.accu.link.data.mode.Device;
import com.out.accu.link.data.mode.DeviceHistory;
import com.out.accu.link.data.mode.Login;
import com.out.accu.link.data.mode.ModeData;
import com.out.accu.link.data.mode.Response;
import com.out.accu.link.data.mode.User;
import com.out.accu.link.data.util.ByteUtil;
import com.out.accu.link.data.util.PacketUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * <p>Title: <／p>
 * <p>Description: <／p>
 * <p>Copyright: Copyright (c) 2017<／p>
 * <p>Company: <／p>
 *
 * @author liangbx
 * @version 2017/11/14
 */

public class ResponseHandler {

    private ModeData mModeData;

    public ResponseHandler(ModeData modeData) {
        mModeData = modeData;
        init();
    }

    public void init() {
        TaskQueue.getInstance().createTaskIfNotExit(PacketUtil.CMD_LOGIN)
                .subscribe(response -> {
                    Login login = LoginConverter.response(response);
                    Observable.just(login)
                            .compose(SmartTransformer.applySchedulers())
                            .subscribe(login1 -> {
                                SmartBus.get().post(BusAction.RESP_LOGIN, login);
                            });
                });

        TaskQueue.getInstance().createTaskIfNotExit(PacketUtil.CMD_GET_DEVICES)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Log.d("response", "CMD_GET_DEVICES");
                    List<Device> devices = DeviceListConverter.response(response);
                    if (mModeData.getDevices().size() == 0) {
                        mModeData.addAll(devices);
                    } else {
                        Log.d("response", "devies add");
                        // 排除相同的设备
                        boolean isFind;
                        List<Device> newDevices = new ArrayList<>();
                        for (Device device : devices) {
                            isFind = false;
                            for (Device device1 : mModeData.getDevices()) {
                                if (device.id.equals(device1.id)) {
                                    device1.isOnline = device.isOnline;
                                    isFind = true;
                                    break;
                                }
                            }
                            if (!isFind) {
                                newDevices.add(device);
                            }
                        }
                        mModeData.addAll(newDevices);
                    }

                    SmartBus.get().post(BusAction.RESP_DEVICES, mModeData);
                });

        TaskQueue.getInstance().createTaskIfNotExit(PacketUtil.CMD_GET_CHANNEL_RANGE)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Device device = new Device();
                    ChannelRangeConverter.response(device, response);
                    try {
                        Device device1 = mModeData.getDevice(device.id);
                        device1.channel1Range = device.channel1Range;
                        device1.channel2Range = device.channel2Range;
                    } catch (NullPointerException e) {
                        // 无法找到对应Device 需要创建一个
//                        mModeData.addDevice(device);
                    }

                    SmartBus.get().post(BusAction.UPDATE_DEVICE_DATA, device.id);
                });

        TaskQueue.getInstance().createTaskIfNotExit(PacketUtil.CMD_GET_VALUE_RANGE)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Device device = new Device();
                    ValueRangeConverter.response(device, response);
                    try {
                        Device device1 = mModeData.getDevice(device.id);
                        device1.valueRange = device.valueRange;
                    } catch (NullPointerException e) {
                        // 无法找到对应Device 需要创建一个
//                        mModeData.addDevice(device);
                    }

                    SmartBus.get().post(BusAction.UPDATE_DEVICE_DATA, device.id);
                });

        TaskQueue.getInstance().createTaskIfNotExit(PacketUtil.CMD_GET_ALIAS_NAME)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Device device = new Device();
                    AliasNameConverter.response(device, response);
                    try {
                        Device device1 = mModeData.getDevice(device.id);
                        device1.aliasName = device.aliasName;
                    } catch (NullPointerException e) {
                        // 无法找到对应Device 需要创建一个
//                        mModeData.addDevice(device);
                    }

                    SmartBus.get().post(BusAction.UPDATE_DEVICE_DATA, device.id);
                    SmartBus.get().post(BusAction.UPDATE_ALIAS_NAME, device.id);
                });

        TaskQueue.getInstance().createTaskIfNotExit(PacketUtil.CMD_GET_LOW_ALARM_ENABLE)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Device device = new Device();
                    LowAlarmEnableConverter.response(device, response);
                    try {
                        Device device1 = mModeData.getDevice(device.id);
                        device1.lowAlarmEnable = device.lowAlarmEnable;
                    } catch (NullPointerException e) {
                        // 无法找到对应Device 需要创建一个
//                        mModeData.addDevice(device);
                    }

                    SmartBus.get().post(BusAction.UPDATE_DEVICE_DATA, device.id);
                });

        TaskQueue.getInstance().createTaskIfNotExit(PacketUtil.CMD_GET_LOW_ALARM_PARAMS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Device device = new Device();
                    LowAlarmConverter.response(device, response);
                    try {
                        Device device1 = mModeData.getDevice(device.id);
                        device1.lowAlarmLimitValue = device.lowAlarmLimitValue;
                        device1.lowNotifyPhones = device.lowNotifyPhones;
                        device1.lowSmsContent = device.lowSmsContent;
                    } catch (NullPointerException e) {
                        // 无法找到对应Device 需要创建一个
//                        mModeData.addDevice(device);
                    }

                    SmartBus.get().post(BusAction.UPDATE_DEVICE_DATA, device.id);
                });

        TaskQueue.getInstance().createTaskIfNotExit(PacketUtil.CMD_GET_LOW_LOW_ALARM_ENABLE)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Device device = new Device();
                    LowLowAlarmEnableConverter.response(device, response);
                    try {
                        Device device1 = mModeData.getDevice(device.id);
                        device1.lowLowAlarmEnable = device.lowLowAlarmEnable;
                    } catch (NullPointerException e) {
                        // 无法找到对应Device 需要创建一个
//                        mModeData.addDevice(device);
                    }

                    SmartBus.get().post(BusAction.UPDATE_DEVICE_DATA, device.id);
                });

        TaskQueue.getInstance().createTaskIfNotExit(PacketUtil.CMD_GET_LOW_LOW_ALARM_PARAMS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Device device = new Device();
                    LowLowAlarmConverter.response(device, response);
                    try {
                        Device device1 = mModeData.getDevice(device.id);
                        device1.lowLowAlarmLimitValue = device.lowLowAlarmLimitValue;
                        device1.lowLowNotifyPhones = device.lowLowNotifyPhones;
                        device1.lowLowSmsContent = device.lowLowSmsContent;
                    } catch (NullPointerException e) {
                        // 无法找到对应Device 需要创建一个
//                        mModeData.addDevice(device);
                    }

                    SmartBus.get().post(BusAction.UPDATE_DEVICE_DATA, device.id);
                });

        TaskQueue.getInstance().createTaskIfNotExit(PacketUtil.CMD_GET_DEFENSE_ENABLE)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Device device = new Device();
                    DefenseEnableConverter.response(device, response);
                    try {
                        Device device1 = mModeData.getDevice(device.id);
                        device1.defenseEnable = device.defenseEnable;
                    } catch (NullPointerException e) {
                        // 无法找到对应Device 需要创建一个
//                        mModeData.addDevice(device);
                    }

                    SmartBus.get().post(BusAction.UPDATE_DEVICE_DATA, device.id);
                });

        TaskQueue.getInstance().createTaskIfNotExit(PacketUtil.CMD_GET_LOCATION)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Device device = new Device();
                    LocationConverter.response(device, response);
                    try {
                        Device device1 = mModeData.getDevice(device.id);
                        device1.lat = device.lat;
                        device1.lng = device.lng;
                    } catch (NullPointerException e) {
                        // 无法找到对应Device 需要创建一个
//                        mModeData.addDevice(device);
                    }

                    SmartBus.get().post(BusAction.UPDATE_DEVICE_DATA, device.id);
                });

        TaskQueue.getInstance().createTaskIfNotExit(PacketUtil.CMD_GET_LTE_STATUS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Device device = new Device();
                    LteStatusConverter.response(device, response);
                    try {
                        Device device1 = mModeData.getDevice(device.id);
                        device1.lteMode = device.lteMode;
                        device1.lteRssi = device.lteRssi;
                        device1.lteStatus = device.lteRssi;
                    } catch (NullPointerException e) {
                        // 无法找到对应Device 需要创建一个
//                        mModeData.addDevice(device);
                    }

                    SmartBus.get().post(BusAction.UPDATE_DEVICE_DATA, device.id);
                });

        TaskQueue.getInstance().createTaskIfNotExit(PacketUtil.CMD_GET_PHONE_NUMBER)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Device device = new Device();
                    PhoneNumberConverter.response(device, response);
                    try {
                        Device device1 = mModeData.getDevice(device.id);
                        device1.phoneNumber = device.phoneNumber;
                    } catch (NullPointerException e) {
                        // 无法找到对应Device 需要创建一个
//                        mModeData.addDevice(device);
                    }

                    SmartBus.get().post(BusAction.UPDATE_DEVICE_DATA, device.id);
                });

        TaskQueue.getInstance().createTaskIfNotExit(PacketUtil.CMD_GET_REPORT_PERIOD)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Device device = new Device();
                    ReportPeriodConverter.response(device, response);
                    try {
                        Device device1 = mModeData.getDevice(device.id);
                        device1.reportPeriod = device.reportPeriod;
                    } catch (NullPointerException e) {
                        // 无法找到对应Device 需要创建一个
//                        mModeData.addDevice(device);
                    }

                    SmartBus.get().post(BusAction.UPDATE_DEVICE_DATA, device.id);
                });

        TaskQueue.getInstance().createTaskIfNotExit(PacketUtil.CMD_GET_TEM)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Device device = new Device();
                    TemConverter.response(device, response);
                    try {
                        Device device1 = mModeData.getDevice(device.id);
                        device1.temperature = device.temperature;
                        device1.humidity = device.humidity;
                    } catch (NullPointerException e) {
                        // 无法找到对应Device 需要创建一个
//                        mModeData.addDevice(device);
                    }

                    SmartBus.get().post(BusAction.UPDATE_DEVICE_DATA, device.id);
                });

        TaskQueue.getInstance().createTaskIfNotExit(PacketUtil.CMD_GET_TX)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Device device = new Device();
                    TxConverter.response(device, response);
                    try {
                        Device device1 = mModeData.getDevice(device.id);
                        device1.currTx = device.currTx;
                    } catch (NullPointerException e) {
                        // 无法找到对应Device 需要创建一个
//                        mModeData.addDevice(device);
                    }

                    SmartBus.get().post(BusAction.UPDATE_DEVICE_DATA, device.id);
                });

        TaskQueue.getInstance().createTaskIfNotExit(PacketUtil.CMD_GET_USER_NAME)
                .subscribe(response -> {
                    User user = new User();
                    UsernameConverter.response(user, response);
                    if (mModeData.user == null) {
                        mModeData.user = user;
                    } else {
                        mModeData.user.username = user.username;
                    }
                });

        TaskQueue.getInstance().createTaskIfNotExit(PacketUtil.CMD_SET_NAME)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    SmartBus.get().post(BusAction.RESP_SET_USERNAME_SUCCESS, "");
                });

        TaskQueue.getInstance().createTaskIfNotExit(PacketUtil.CMD_SET_PHONE)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    SmartBus.get().post(BusAction.RESP_SET_USER_MOBILE_SUCCESS, "");
                });

        TaskQueue.getInstance().createTaskIfNotExit(PacketUtil.CMD_SET_PASSWORD)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    SmartBus.get().post(BusAction.RESP_SET_PASSWORD_SUCCESS, "");
                });

        TaskQueue.getInstance().createTaskIfNotExit(PacketUtil.CMD_GET_USER_PHONE)
                .subscribe(response -> {
                    User user = new User();
                    UserMobileConverter.response(user, response);
                    if (mModeData.user == null) {
                        mModeData.user = user;
                    } else {
                        mModeData.user.mobile = user.mobile;
                    }
                });

        TaskQueue.getInstance().createTaskIfNotExit(PacketUtil.CMD_DEVICE_VALUE_UPLOAD)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Device device = new Device();
                    UploadValueConverter.response(device, response);
                    try {
                        Device device1 = mModeData.getDevice(device.id);
                        device1.channel1Value = device.channel1Value;
                        device1.channel2Value = device.channel2Value;
                        device1.sampleValue = device.sampleValue;
                        SmartBus.get().post(BusAction.UPDATE_DEVICE_VALUE, device.id);
                    } catch (NullPointerException e) {
                        // 无法找到对应Device 需要创建一个
//                        mModeData.addDevice(device);
                    }

                });

        TaskQueue.getInstance().createTaskIfNotExit(PacketUtil.CMD_DEVICE_ONLINE_UPLOAD)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Device device = new Device();
                    UploadOnlineConverter.response(device, response);
                    try {
                        Device device1 = mModeData.getDevice(device.id);
                        device1.isOnline = device.isOnline;
                    } catch (NullPointerException e) {
                        // 无法找到对应Device 需要创建一个
//                        mModeData.addDevice(device);
                    }

                    SmartBus.get().post(BusAction.ONLINE_DEVICE, device);

                    // 在线后刷新所有数据
                    DataManager.getInstance().getDataService().getDevice(device);
                });

        TaskQueue.getInstance().createTaskIfNotExit(PacketUtil.CMD_SET_CHANNEL_RANGE)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.isSuccess()) {
                        SmartBus.get().post(BusAction.RESP_SET_CHANNEL, getDeviceId(response));
                    }
                });

        TaskQueue.getInstance().createTaskIfNotExit(PacketUtil.CMD_SET_VALUE_RANGE)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.isSuccess()) {
                        SmartBus.get().post(BusAction.RESP_SET_VALUE, getDeviceId(response));
                    }
                });

        TaskQueue.getInstance().createTaskIfNotExit(PacketUtil.CMD_SET_DEFENSE_ENABLE)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.isSuccess()) {
                        SmartBus.get().post(BusAction.RESP_SET_DEFENSE_ENABLE, getDeviceId(response));
                    }
                });

        TaskQueue.getInstance().createTaskIfNotExit(PacketUtil.CMD_SET_LOCATION)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.isSuccess()) {
                        SmartBus.get().post(BusAction.RESP_SET_LOCATION, getDeviceId(response));
                    }
                });

        TaskQueue.getInstance().createTaskIfNotExit(PacketUtil.CMD_SET_LOW_LOW_ALARM_PARAMS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.isSuccess()) {
                        SmartBus.get().post(BusAction.RESP_SET_LOW_LOW_ALARM_PARAMS, getDeviceId(response));
                    }
                });

        TaskQueue.getInstance().createTaskIfNotExit(PacketUtil.CMD_SET_LOW_LOW_ALARM_ENABLE)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.isSuccess()) {
                        SmartBus.get().post(BusAction.RESP_SET_LOW_LOW_ALARM_ENABLE, getDeviceId(response));
                    }
                });

        TaskQueue.getInstance().createTaskIfNotExit(PacketUtil.CMD_SET_LOW_ALARM_PARAMS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.isSuccess()) {
                        SmartBus.get().post(BusAction.RESP_SET_LOW_LOW_ALARM_PARAMS, getDeviceId(response));
                    }
                });

        TaskQueue.getInstance().createTaskIfNotExit(PacketUtil.CMD_SET_LOW_ALARM_ENABLE)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.isSuccess()) {
                        SmartBus.get().post(BusAction.RESP_SET_LOW_LOW_ALARM_ENABLE, getDeviceId(response));
                    }
                });

        TaskQueue.getInstance().createTaskIfNotExit(PacketUtil.CMD_SET_REPORT_PERIOD)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.isSuccess()) {
                        SmartBus.get().post(BusAction.RESP_SET_REPORT_PERIOD, getDeviceId(response));
                    }
                });

        TaskQueue.getInstance().createTaskIfNotExit(PacketUtil.CMD_SET_ALIAS_NAME)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    SmartBus.get().post(BusAction.RESP_SET_ALIAS_NAME, getDeviceId(response));
                });

        TaskQueue.getInstance().createTaskIfNotExit(PacketUtil.CMD_GET_HISTORY)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    DeviceHistory history = HistoryConverter.response(response);
                    SmartBus.get().post(BusAction.RESP_HISTORY, history);
                }, throwable -> {
                    SmartBus.get().post(BusAction.RESP_HISTORY_ERROR, throwable);
                });
    }

    private String getDeviceId(Response response) {
        return ByteUtil.getString(response.data, 0, 6);
    }
}
