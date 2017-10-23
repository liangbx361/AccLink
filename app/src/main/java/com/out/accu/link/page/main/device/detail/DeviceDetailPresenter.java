package com.out.accu.link.page.main.device.detail;

import android.widget.Toast;

import com.cyou17173.android.arch.base.page.SmartTransformer;
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

    DeviceDetailPresenter(DeviceDetailContract.View view) {
        mView = view;
    }

    /**
     * 开始执行业务逻辑，如加载缓存、请求网络数据
     */
    @Override
    public void start() {

    }

    /**
     * 重新加载
     */
    @Override
    public void retryLoad() {

    }

    @Override
    public void setChannelRange(int channel1, int channel2) {
        mView.showLoadingDialog();
        DataManager.getInstance().getDataService()
                .setChannel(mDevice.id, channel1, channel2)
                .compose(SmartTransformer.applySchedulers())
                .subscribe(aBoolean -> {
                    mView.hideLoadingDialog();
                    mDevice.channel1Range = channel1;
                    mDevice.channel2Range = channel2;
                    mView.showData(mDevice);
                    Toast.makeText(mView.getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
                }, throwable -> {
                    mView.hideLoadingDialog();
                    Toast.makeText(mView.getActivity(), "修改失败", Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void setValueRange(int value) {
        mView.showLoadingDialog();
        DataManager.getInstance().getDataService()
                .setValue(mDevice.id, value)
                .compose(SmartTransformer.applySchedulers())
                .subscribe(aBoolean -> {
                    mView.hideLoadingDialog();
                    mDevice.valueRange = value;
                    mView.showData(mDevice);
                    Toast.makeText(mView.getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
                }, throwable -> {
                    mView.hideLoadingDialog();
                    Toast.makeText(mView.getActivity(), "修改失败", Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void setReportPeriod(int period) {
        mView.showLoadingDialog();
        DataManager.getInstance().getDataService()
                .setReportPeriod(mDevice.id, period)
                .compose(SmartTransformer.applySchedulers())
                .subscribe(aBoolean -> {
                    mView.hideLoadingDialog();
                    mDevice.reportPeriod = period;
                    mView.showData(mDevice);
                    Toast.makeText(mView.getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
                }, throwable -> {
                    mView.hideLoadingDialog();
                    Toast.makeText(mView.getActivity(), "修改失败", Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void setLowAlarmEnable(boolean enable) {
        mView.showLoadingDialog();
        DataManager.getInstance().getDataService()
                .setLowAlarmEnable(mDevice.id, enable)
                .compose(SmartTransformer.applySchedulers())
                .subscribe(aBoolean -> {
                    mView.hideLoadingDialog();
                    mDevice.lowAlarmEnable = enable;
                    mView.showData(mDevice);
                    Toast.makeText(mView.getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
                }, throwable -> {
                    mView.hideLoadingDialog();
                    Toast.makeText(mView.getActivity(), "修改失败", Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void setLowAlarmLimitValue(int value, String[] phones, String sms) {
        mView.showLoadingDialog();
        DataManager.getInstance().getDataService()
                .setLowAlarmLimitValue(mDevice.id, value, phones, sms)
                .compose(SmartTransformer.applySchedulers())
                .subscribe(aBoolean -> {
                    mView.hideLoadingDialog();
                    mDevice.lowAlarmLimitValue = value;
                    mDevice.lowNotifyPhones = phones;
                    mDevice.lowSmsFormat = sms;
                    mView.showData(mDevice);
                    Toast.makeText(mView.getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
                }, throwable -> {
                    mView.hideLoadingDialog();
                    Toast.makeText(mView.getActivity(), "修改失败", Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void setLowLowAlarmEnable(boolean enable) {
        mView.showLoadingDialog();
        DataManager.getInstance().getDataService()
                .setLowLowAlarmEnable(mDevice.id, enable)
                .compose(SmartTransformer.applySchedulers())
                .subscribe(aBoolean -> {
                    mView.hideLoadingDialog();
                    mDevice.lowLowAlarmEnable = enable;
                    mView.showData(mDevice);
                    Toast.makeText(mView.getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
                }, throwable -> {
                    mView.hideLoadingDialog();
                    Toast.makeText(mView.getActivity(), "修改失败", Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void setLowLowAlarmLimitValue(int value, String[] phones, String sms) {
        mView.showLoadingDialog();
        DataManager.getInstance().getDataService()
                .setLowLowAlarmLimitValue(mDevice.id, value, phones, sms)
                .compose(SmartTransformer.applySchedulers())
                .subscribe(aBoolean -> {
                    mView.hideLoadingDialog();
                    mDevice.lowLowAlarmLimitValue = value;
                    mDevice.lowLowNotifyPhones = phones;
                    mDevice.lowLowSmsFormat = sms;
                    mView.showData(mDevice);
                    Toast.makeText(mView.getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
                }, throwable -> {
                    mView.hideLoadingDialog();
                    Toast.makeText(mView.getActivity(), "修改失败", Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void setGps(double lat, double lot) {
        mView.showLoadingDialog();
        DataManager.getInstance().getDataService()
                .setGps(mDevice.id, lat, lot)
                .compose(SmartTransformer.applySchedulers())
                .subscribe(aBoolean -> {
                    mView.hideLoadingDialog();
                    mDevice.lat = lat;
                    mDevice.lon = lot;
                    mView.showData(mDevice);
                    Toast.makeText(mView.getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
                }, throwable -> {
                    mView.hideLoadingDialog();
                    Toast.makeText(mView.getActivity(), "修改失败", Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void setDefenseEnable(boolean enable) {
        mView.showLoadingDialog();
        DataManager.getInstance().getDataService()
                .setDefenseEnable(mDevice.id, enable)
                .compose(SmartTransformer.applySchedulers())
                .subscribe(aBoolean -> {
                    mView.hideLoadingDialog();
                    mDevice.defenseEnable = enable;
                    mView.showData(mDevice);
                    Toast.makeText(mView.getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
                }, throwable -> {
                    mView.hideLoadingDialog();
                    Toast.makeText(mView.getActivity(), "修改失败", Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void setAliasName(String name) {
        mView.showLoadingDialog();
        DataManager.getInstance().getDataService()
                .setAliasName(mDevice.id, name)
                .compose(SmartTransformer.applySchedulers())
                .subscribe(aBoolean -> {
                    mView.hideLoadingDialog();
                    mDevice.aliasName = name;
                    mView.showData(mDevice);
                    Toast.makeText(mView.getActivity(), "修改成功", Toast.LENGTH_SHORT).show();
                }, throwable -> {
                    mView.hideLoadingDialog();
                    Toast.makeText(mView.getActivity(), "修改失败", Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public void setDevice(Device device) {
        mDevice = device;
    }
}
