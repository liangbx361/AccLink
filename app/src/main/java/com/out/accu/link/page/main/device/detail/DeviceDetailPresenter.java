package com.out.accu.link.page.main.device.detail;

import android.widget.Toast;

import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.out.accu.link.R;
import com.out.accu.link.data.BusAction;
import com.out.accu.link.data.DataManager;
import com.out.accu.link.data.mode.Device;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <P>Company: 17173</p>
 *
 * @author
 * @version 2017-10-13
 */
class DeviceDetailPresenter implements DeviceDetailContract.Presenter {

    private DeviceDetailContract.View mView;
    private Device mDevice;
    private String mDeviceId;
    private Device mSetDevice = new Device();

    DeviceDetailPresenter(DeviceDetailContract.View view) {
        mView = view;
    }

    /**
     * 开始执行业务逻辑，如加载缓存、请求网络数据
     */
    @Override
    public void start() {
        mDevice = DataManager.getInstance().getModeData().getDevice(mDeviceId);
        mView.showData(mDevice);
    }

    /**
     * 重新加载
     */
    @Override
    public void retryLoad() {

    }

    @Override
    public void initArgument() {
        mDeviceId = mView.getActivity().getIntent().getStringExtra("deviceId");
    }

    @Override
    public Device getDevice() {
        return DataManager.getInstance().getModeData().getDevice(mDeviceId);
    }

    @Override
    public void setDevice(Device device) {
        mDevice = device;
    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag(BusAction.UPDATE_DEVICE_DATA)
            }
    )
    public void updateDevice(String deviceId) {
        if(mDeviceId.equals(deviceId)) {
            Device device = DataManager.getInstance().getModeData().getDevice(mDeviceId);
            mView.showData(device);
        }
    }

    @Override
    public void setChannelRange(int channel1, int channel2) {
        mSetDevice.channel1Range = channel1;
        mSetDevice.channel2Range = channel2;
        mView.showLoadingDialog();
        DataManager.getInstance().getDataService().setChannel(mDeviceId, channel1, channel2);
    }

    @Override
    public void setValueRange(int value) {
        mSetDevice.valueRange = value;
        mView.showLoadingDialog();
        DataManager.getInstance().getDataService().setValue(mDeviceId, value);
    }

    @Override
    public void setReportPeriod(int period) {
        mSetDevice.reportPeriod = period;
        mView.showLoadingDialog();
        DataManager.getInstance().getDataService().setReportPeriod(mDeviceId, period);
    }

    @Override
    public void setLowAlarmEnable(boolean enable) {
        mSetDevice.lowAlarmEnable = enable ? 1 : 0;
        mView.showLoadingDialog();
        DataManager.getInstance().getDataService().setLowAlarmEnable(mDeviceId, enable);
    }

    @Override
    public void setLowAlarmLimitValue(int value, String[] phones, String sms) {
        mSetDevice.lowAlarmLimitValue = value;
        mSetDevice.lowNotifyPhones = phones;
        mSetDevice.lowSmsContent = sms;
        mView.showLoadingDialog();
        DataManager.getInstance().getDataService().setLowAlarmLimitValue(mDeviceId, value, phones, sms);
    }

    @Override
    public void setLowLowAlarmEnable(boolean enable) {
        mSetDevice.lowLowAlarmEnable = enable ? 1 : 0;
        mView.showLoadingDialog();
        DataManager.getInstance().getDataService().setLowLowAlarmEnable(mDeviceId, enable);
    }

    @Override
    public void setLowLowAlarmLimitValue(int value, String[] phones, String sms) {
        mSetDevice.lowLowAlarmLimitValue = value;
        mSetDevice.lowLowNotifyPhones = phones;
        mSetDevice.lowLowSmsContent = sms;
        mView.showLoadingDialog();
        DataManager.getInstance().getDataService().setLowLowAlarmLimitValue(mDeviceId, value, phones, sms);
    }

    @Override
    public void setGps(double lat, double lot) {
        mView.showLoadingDialog();
        DataManager.getInstance().getDataService().setGps(mDeviceId, lat, lot);
    }

    @Override
    public void setDefenseEnable(boolean enable) {
        mSetDevice.defenseEnable = enable ? 1:0;
        mView.showLoadingDialog();
        DataManager.getInstance().getDataService().setDefenseEnable(mDeviceId, enable);
    }

    @Override
    public void setAliasName(String name) {
        mSetDevice.aliasName = name;
        mView.showLoadingDialog();
        DataManager.getInstance().getDataService().setAliasName(mDeviceId, name);
    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag(BusAction.RESP_SET_ALIAS_NAME),
            }
    )
    public void respSetAliasName(String deviceId) {
        mView.hideLoadingDialog();
        if(mDeviceId.equals(deviceId)) {
            Device device = DataManager.getInstance().getModeData().getDevice(mDeviceId);
            device.aliasName = mSetDevice.aliasName;
            mView.showData(device);
            Toast.makeText(mView.getActivity(), R.string.modify_success, Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag(BusAction.RESP_SET_CHANNEL),
            }
    )
    public void respSetChannel(String deviceId) {
        mView.hideLoadingDialog();
        if(mDeviceId.equals(deviceId)) {
            Device device = DataManager.getInstance().getModeData().getDevice(mDeviceId);
            device.channel1Range = mSetDevice.channel1Range;
            device.channel2Range = mSetDevice.channel2Range;
            mView.showData(device);
            Toast.makeText(mView.getActivity(), R.string.modify_success, Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag(BusAction.RESP_SET_VALUE),
            }
    )
    public void respSetValue(String deviceId) {
        mView.hideLoadingDialog();
        if(mDeviceId.equals(deviceId)) {
            Device device = DataManager.getInstance().getModeData().getDevice(mDeviceId);
            device.valueRange = mSetDevice.valueRange;
            mView.showData(device);
            Toast.makeText(mView.getActivity(), R.string.modify_success, Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag(BusAction.RESP_SET_DEFENSE_ENABLE),
            }
    )
    public void respSetDefenseEnable(String deviceId) {
        mView.hideLoadingDialog();
        if(mDeviceId.equals(deviceId)) {
            Device device = DataManager.getInstance().getModeData().getDevice(mDeviceId);
            device.defenseEnable = mSetDevice.defenseEnable;
            mView.showData(device);
            Toast.makeText(mView.getActivity(), R.string.modify_success, Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag(BusAction.RESP_SET_LOCATION),
            }
    )
    public void respSetLocation(String deviceId) {
        mView.hideLoadingDialog();
        if(mDeviceId.equals(deviceId)) {
            Device device = DataManager.getInstance().getModeData().getDevice(mDeviceId);
            device.lat = mSetDevice.lat;
            device.lng = mSetDevice.lng;
            mView.showData(device);
            Toast.makeText(mView.getActivity(), R.string.modify_success, Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag(BusAction.RESP_SET_LOW_ALARM_ENABLE),
            }
    )
    public void respSetLowAlarmEnable(String deviceId) {
        mView.hideLoadingDialog();
        if(mDeviceId.equals(deviceId)) {
            Device device = DataManager.getInstance().getModeData().getDevice(mDeviceId);
            device.lowAlarmEnable = mSetDevice.lowAlarmEnable;
            mView.showData(device);
            Toast.makeText(mView.getActivity(), R.string.modify_success, Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag(BusAction.RESP_SET_LOW_ALARM_PARAMS),
            }
    )
    public void respSetLowAlarmParams(String deviceId) {
        mView.hideLoadingDialog();
        if(mDeviceId.equals(deviceId)) {
            Device device = DataManager.getInstance().getModeData().getDevice(mDeviceId);
            device.lowAlarmLimitValue = mSetDevice.lowAlarmLimitValue;
            device.lowNotifyPhones = mSetDevice.lowNotifyPhones;
            device.lowSmsContent = mSetDevice.lowSmsContent;
            mView.showData(device);
            Toast.makeText(mView.getActivity(), R.string.modify_success, Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag(BusAction.RESP_SET_LOW_LOW_ALARM_ENABLE),
            }
    )
    public void respSetLowLowAlarmEnable(String deviceId) {
        mView.hideLoadingDialog();
        if(mDeviceId.equals(deviceId)) {
            Device device = DataManager.getInstance().getModeData().getDevice(mDeviceId);
            device.lowLowAlarmEnable = mSetDevice.lowLowAlarmEnable;
            mView.showData(device);
            Toast.makeText(mView.getActivity(), R.string.modify_success, Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag(BusAction.RESP_SET_LOW_LOW_ALARM_PARAMS),
            }
    )
    public void respSetLowLowAlarmParams(String deviceId) {
        mView.hideLoadingDialog();
        if(mDeviceId.equals(deviceId)) {
            Device device = DataManager.getInstance().getModeData().getDevice(mDeviceId);
            device.lowLowAlarmLimitValue = mSetDevice.lowLowAlarmLimitValue;
            device.lowLowNotifyPhones = mSetDevice.lowLowNotifyPhones;
            device.lowLowSmsContent = mSetDevice.lowLowSmsContent;
            mView.showData(device);
            Toast.makeText(mView.getActivity(), R.string.modify_success, Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag(BusAction.RESP_SET_REPORT_PERIOD)
            }
    )
    public void respSetReportPeriod(String deviceId) {
        mView.hideLoadingDialog();
        if(mDeviceId.equals(deviceId)) {
            Device device = DataManager.getInstance().getModeData().getDevice(mDeviceId);
            device.reportPeriod = mSetDevice.reportPeriod;
            mView.showData(device);
            Toast.makeText(mView.getActivity(), R.string.modify_success, Toast.LENGTH_SHORT).show();
        }
    }

}
