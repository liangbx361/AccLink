package com.out.accu.link.page.main.device.detail;

import android.content.DialogInterface;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.cyou17173.android.arch.base.page.SmartStateActivity;
import com.out.accu.link.R;
import com.out.accu.link.data.mode.Device;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <P>Company: 17173</p>
 *
 * @author
 * @version 2017-10-13
 */
public class DeviceDetailActivity extends SmartStateActivity<DeviceDetailContract.Presenter> implements
        DeviceDetailContract.View, View.OnClickListener {

    //    @BindView(R.id.topbar)
//    QMUITopBar mTopbar;
    @BindView(R.id.groupListView)
    QMUIGroupListView mGroupListView;

    Device mDevice;

    QMUICommonListItemView deviceNameItem;
    QMUICommonListItemView channel1Item;
    QMUICommonListItemView channel2Item;
    QMUICommonListItemView valueItem;
    QMUICommonListItemView reportPeriodItem;
    QMUICommonListItemView lowAlarmEnableItem;
    QMUICommonListItemView lowAlarmLimitValueItem;
    QMUICommonListItemView lowNotifyPhonesItem;
    QMUICommonListItemView lowSmsFormatItem;
    QMUICommonListItemView lowLowAlarmEnableItem;
    QMUICommonListItemView lowLowAlarmLimitValueItem;
    QMUICommonListItemView lowLowNotifyPhonesItem;
    QMUICommonListItemView lowLowSmsFormatItem;
    QMUICommonListItemView gpsItem;
    QMUICommonListItemView defenseEnableItem;
    QMUICommonListItemView temperatureItem;
    QMUICommonListItemView humidityItem;
    QMUICommonListItemView phoneNumberItem;
    QMUICommonListItemView lteStatusItem;
    QMUICommonListItemView lteRssiItem;
    QMUICommonListItemView lteModeItem;
    QMUICommonListItemView currTxItem;

    QMUITipDialog mLoadingDialog;
    QMUITipDialog mSuccessDialog;
    QMUITipDialog mFailDialog;

    /**
     * 设置布局ID
     */
    @Override
    public int getLayoutId() {
        return R.layout.activity_device_detail;
    }

    /**
     * 初始化控件
     */
    @Override
    public void initView() {
        ButterKnife.bind(this);

        mLoadingDialog = new QMUITipDialog.Builder(this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("修改中...")
                .create();

        mSuccessDialog = new QMUITipDialog.Builder(this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                .setTipWord("修改成功")
                .create();

        mFailDialog = new QMUITipDialog.Builder(this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                .setTipWord("修改失败")
                .create();
    }

    /**
     * 注册事件，如事件监听、广播接收等
     */
    @Override
    public void registerEvent() {

    }

    /**
     * 注销事件
     */
    @Override
    public void unregisterEvent() {

    }

    /**
     * 创建对应的Presenter, 通常需要传递两个对象（视图和数据服务）
     */
    @Override
    public DeviceDetailContract.Presenter createPresenter() {
        return new DeviceDetailPresenter(this);
    }

    private boolean checkValue(int vlaue, QMUICommonListItemView itemView) {
        if (vlaue == -1) {
            itemView.setDetailText("未获取");
            return false;
        } else {
            return true;
        }
    }

    private boolean checkValue(String value, QMUICommonListItemView itemView) {
        if (value == null) {
            itemView.setDetailText("未获取");
            return false;
        } else {
            return true;
        }
    }

    private boolean checkValue(double value, QMUICommonListItemView itemView) {
        if (value == -1) {
            itemView.setDetailText("未获取");
            return false;
        } else {
            return true;
        }
    }

    private boolean checkValue(String[] value, QMUICommonListItemView itemView) {
        if (value == null) {
            itemView.setDetailText("未获取");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void showData(Device device) {
        mGroupListView.removeAllViews();

        if (deviceNameItem == null) {
            deviceNameItem = mGroupListView.createItemView(getResString(R.string.device_name));
        }
        if (checkValue(device.aliasName, deviceNameItem)) {
            deviceNameItem.setDetailText(device.aliasName);
        }

        if (channel1Item == null) {
            channel1Item = mGroupListView.createItemView(getString(R.string.channel_1_range_desc));
        }
        if (checkValue(device.channel1Range, channel1Item)) {
            channel1Item.setDetailText(device.channel1Range + "mA");
        }

        if (channel2Item == null) {
            channel2Item = mGroupListView.createItemView(getString(R.string.channel_2_range_desc));
        }
        if (checkValue(device.channel2Range, channel2Item)) {
            channel2Item.setDetailText(device.channel2Range + "mA");
        }

        if (valueItem == null) {
            valueItem = mGroupListView.createItemView(getString(R.string.value_range_desc));
        }
        if (checkValue(device.valueRange, valueItem)) {
            valueItem.setDetailText(device.valueRange + "mA");
        }

        if (reportPeriodItem == null) {
            reportPeriodItem = mGroupListView.createItemView(getString(R.string.report_period_desc));
        }
        if (checkValue(device.reportPeriod, reportPeriodItem)) {
            if (device.reportPeriod == 0) {
                reportPeriodItem.setDetailText(getString(R.string.off));
            } else {
                reportPeriodItem.setDetailText(getResources().getString(R.string.report_period_value, device.reportPeriod));
            }
        }

        // 低报
        if (lowAlarmEnableItem == null) {
            lowAlarmEnableItem = mGroupListView.createItemView(getString(R.string.low_alarm_enable_desc));
        }
        if (checkValue(device.lowAlarmEnable, lowAlarmEnableItem)) {
            if (device.lowAlarmEnable == 1) {
                lowAlarmEnableItem.setDetailText(getString(R.string.on));
            } else {
                lowAlarmEnableItem.setDetailText(getString(R.string.off));
            }
        }

        if (lowAlarmLimitValueItem == null) {
            lowAlarmLimitValueItem = mGroupListView.createItemView(getString(R.string.low_alarm_limit_value_desc));
        }
        if (checkValue(device.lowAlarmLimitValue, lowAlarmEnableItem)) {
            lowAlarmLimitValueItem.setDetailText(device.lowAlarmLimitValue + "");
        }

        if (lowNotifyPhonesItem == null) {
            lowNotifyPhonesItem = mGroupListView.createItemView(getString(R.string.low_notify_phones_desc));
        }
        if (checkValue(device.lowNotifyPhones, lowNotifyPhonesItem)) {
            StringBuilder phoneSb = new StringBuilder();
            for (String phone : device.lowNotifyPhones) {
                phoneSb.append(phone);
                phoneSb.append("...");
                break;
            }
            lowNotifyPhonesItem.setDetailText(phoneSb);
        }

        if (lowSmsFormatItem == null) {
            lowSmsFormatItem = mGroupListView.createItemView(getResString(R.string.low_sms_format_desc));
        }
        if (checkValue(device.lowSmsContent, lowSmsFormatItem)) {
            lowSmsFormatItem.setDetailText(device.lowSmsContent);
        }

        // 低低报
        if (lowLowAlarmEnableItem == null) {
            lowLowAlarmEnableItem = mGroupListView.createItemView(getString(R.string.low_low_alarm_enable_desc));
        }
        if(checkValue(device.lowAlarmEnable, lowLowAlarmEnableItem)) {
            if (device.lowLowAlarmEnable == 1) {
                lowLowAlarmEnableItem.setDetailText(getString(R.string.on));
            } else {
                lowLowAlarmEnableItem.setDetailText(getString(R.string.off));
            }
        }

        if (lowLowAlarmLimitValueItem == null) {
            lowLowAlarmLimitValueItem = mGroupListView.createItemView(getString(R.string.low_alarm_limit_value_desc));
        }
        if(checkValue(device.lowLowAlarmLimitValue, lowLowAlarmLimitValueItem)) {
            lowLowAlarmLimitValueItem.setDetailText(device.lowLowAlarmLimitValue + "");
        }

        if (lowLowNotifyPhonesItem == null) {
            lowLowNotifyPhonesItem = mGroupListView.createItemView(getString(R.string.low_low_notify_phones_desc));
        }
        if(checkValue(device.lowLowNotifyPhones, lowLowNotifyPhonesItem)) {
            StringBuilder lowLowPhoneSb = new StringBuilder();
            for (String phone : device.lowLowNotifyPhones) {
                lowLowPhoneSb.append(phone);
                lowLowPhoneSb.append("...");
                break;
            }
            lowLowNotifyPhonesItem.setDetailText(lowLowPhoneSb);
        }

        if (lowLowSmsFormatItem == null) {
            lowLowSmsFormatItem = mGroupListView.createItemView(getResString(R.string.low_low_sms_format_desc));
        }
        if(checkValue(device.lowLowSmsContent, lowLowSmsFormatItem)) {
            lowLowSmsFormatItem.setDetailText(device.lowLowSmsContent);
        }

        //
        if (gpsItem == null) {
            gpsItem = mGroupListView.createItemView(getString(R.string.gps_desc));
        }
        if(checkValue(device.lat, gpsItem)) {
            gpsItem.setDetailText(device.lat + "," + device.lng);
        }

        if (defenseEnableItem == null) {
            defenseEnableItem = mGroupListView.createItemView(getString(R.string.defense_enable_desc));
        }
        if(checkValue(device.defenseEnable, deviceNameItem)) {
            if (device.defenseEnable == 1) {
                defenseEnableItem.setDetailText(getString(R.string.on));
            } else {
                defenseEnableItem.setDetailText(getString(R.string.off));
            }
        }

        if (temperatureItem == null) {
            temperatureItem = mGroupListView.createItemView(getString(R.string.temperature_desc));
        }
        if(checkValue(device.temperature, temperatureItem)) {
            temperatureItem.setDetailText(getResources().getString(R.string.temperature_value, device.temperature));
        }

        if (humidityItem == null) {
            humidityItem = mGroupListView.createItemView(getString(R.string.humidity_desc));
        }
        if(checkValue(device.humidity, humidityItem)) {
            humidityItem.setDetailText(getResources().getString(R.string.humidity_value, device.humidity));
        }

        if (phoneNumberItem == null) {
            phoneNumberItem = mGroupListView.createItemView(getString(R.string.phone_number_desc));
        }
        if(checkValue(device.phoneNumber, phoneNumberItem)) {
            phoneNumberItem.setDetailText(device.phoneNumber);
        }

        if (lteStatusItem == null) {
            lteStatusItem = mGroupListView.createItemView(getString(R.string.lte_status_desc));
        }
        if(checkValue(device.lteStatus, lteStatusItem)) {
            String status[] = getResources().getStringArray(R.array.lte_status_value);
            lteStatusItem.setDetailText(status[device.lteStatus]);
        }

        if (lteRssiItem == null) {
            lteRssiItem = mGroupListView.createItemView(getString(R.string.lte_rssi_desc));
        }
        if(checkValue(device.lteRssi, lteRssiItem)) {
            lteRssiItem.setDetailText(device.lteRssi + "");
        }

        if (lteModeItem == null) {
            lteModeItem = mGroupListView.createItemView(getString(R.string.lte_mode_desc));
        }
        if(checkValue(device.lteMode, lteModeItem)) {
            String modes[] = getResources().getStringArray(R.array.lte_mode_value);
            lteModeItem.setDetailText(modes[device.lteMode]);
        }

        if (currTxItem == null) {
            currTxItem = mGroupListView.createItemView(getString(R.string.curr_tx_desc));
        }
        if(checkValue(device.currTx, currTxItem)) {
            currTxItem.setDetailText(device.currTx + "byte");
        }

        QMUIGroupListView.newSection(this)
                .addItemView(deviceNameItem, this)
                .addItemView(channel1Item, this)
                .addItemView(channel2Item, this)
                .addItemView(valueItem, this)
                .addItemView(reportPeriodItem, this)
                .addItemView(lowAlarmEnableItem, this)
                .addItemView(lowAlarmLimitValueItem, this)
                .addItemView(lowNotifyPhonesItem, this)
                .addItemView(lowSmsFormatItem, this)
                .addItemView(lowLowAlarmEnableItem, this)
                .addItemView(lowLowAlarmLimitValueItem, this)
                .addItemView(lowLowNotifyPhonesItem, this)
                .addItemView(lowLowSmsFormatItem, this)
                .addItemView(gpsItem, this)
                .addItemView(defenseEnableItem, this)
                .addItemView(temperatureItem, this)
                .addItemView(humidityItem, this)
                .addItemView(phoneNumberItem, this)
                .addItemView(lteStatusItem, this)
                .addItemView(lteRssiItem, this)
                .addItemView(lteModeItem, this)
                .addItemView(currTxItem, this)
                .addTo(mGroupListView);
    }

    @Override
    public void showLoadingDialog() {
        mLoadingDialog.show();
    }

    @Override
    public void hideLoadingDialog() {
        mLoadingDialog.dismiss();
    }

    @Override
    public void showLoadSuccessDialog() {
        mSuccessDialog.show();
    }

    @Override
    public void hideLoadSuccessDialog() {
        mSuccessDialog.dismiss();
    }

    @Override
    public void showLoadFailDialog() {
        mFailDialog.show();
    }

    @Override
    public void hideLoadFailDialog() {
        mFailDialog.dismiss();
    }

    private String getResString(int id) {
        return getResources().getString(id);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(deviceNameItem)) {
            showDeviceName();
        } else if (v.equals(channel1Item)) {
            showChannel1Dialog();
        } else if (v.equals(channel2Item)) {
            showChannel21Dialog();
        } else if (v.equals(valueItem)) {
            showValue1Dialog();
        } else if (v.equals(reportPeriodItem)) {
            showReportPeriodDialog();
        } else if (v.equals(lowAlarmEnableItem)) {
            showLowAlarmEnableDialog();
        } else if (v.equals(lowAlarmLimitValueItem)) {
            showLowValueDialog();
        } else if (v.equals(lowNotifyPhonesItem)) {
            showLowPhonesDialog();
        } else if (v.equals(lowSmsFormatItem)) {
            showLowSmsDialog();
        } else if (v.equals(lowLowAlarmEnableItem)) {
            showLowLowEnableDialog();
        } else if (v.equals(lowLowAlarmLimitValueItem)) {
            showLowLowValueDialog();
        } else if (v.equals(lowLowNotifyPhonesItem)) {
            showLowLowPhonesDialog();
        } else if (v.equals(lowLowSmsFormatItem)) {
            showLowLowSmsDialog();
        } else if (v.equals(defenseEnableItem)) {
            showDefenseEnableDialog();
        }
    }

    private void showDeviceName() {
        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(getActivity());
        builder.setTitle(getString(R.string.device_name))
                .setInputType(InputType.TYPE_CLASS_TEXT)
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction("修改", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        CharSequence text = builder.getEditText().getText();
                        try {
                            getPresenter().setAliasName(text.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    }
                })
                .show();

        builder.getEditText().setText(mDevice.aliasName + "");
    }

    private void showChannel1Dialog() {
        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(getActivity());
        builder.setTitle(getString(R.string.channel_1_range_desc))
                .setInputType(InputType.TYPE_CLASS_NUMBER)
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction("修改", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        CharSequence text = builder.getEditText().getText();
                        try {
                            int value = Integer.valueOf(text.toString());
                            getPresenter().setChannelRange(value, mDevice.channel2Range);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    }
                })
                .show();

        builder.getEditText().setText(mDevice.channel1Range + "");
    }

    private void showChannel21Dialog() {
        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(getActivity());
        builder.setTitle(getString(R.string.channel_2_range_desc))
                .setInputType(InputType.TYPE_CLASS_NUMBER)
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction("修改", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        CharSequence text = builder.getEditText().getText();
                        try {
                            int value = Integer.valueOf(text.toString());
                            getPresenter().setChannelRange(mDevice.channel1Range, value);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    }
                })
                .show();

        builder.getEditText().setText(mDevice.channel2Range + "");
    }

    private void showValue1Dialog() {
        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(getActivity());
        builder.setTitle(getString(R.string.value_range_desc))
                .setInputType(InputType.TYPE_CLASS_NUMBER)
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction("修改", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        CharSequence text = builder.getEditText().getText();
                        try {
                            int value = Integer.valueOf(text.toString());
                            getPresenter().setValueRange(value);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    }
                })
                .show();

        builder.getEditText().setText(mDevice.valueRange + "");
    }

    private void showReportPeriodDialog() {
        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(getActivity());
        builder.setTitle(getString(R.string.report_period_desc))
                .setInputType(InputType.TYPE_CLASS_NUMBER)
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction("修改", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        CharSequence text = builder.getEditText().getText();
                        try {
                            int value = Integer.valueOf(text.toString());
                            getPresenter().setReportPeriod(value);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    }
                })
                .show();

        builder.getEditText().setText(mDevice.reportPeriod + "");
    }

    private void showLowAlarmEnableDialog() {
        String[] enableSwitch = getResources().getStringArray(R.array.enable_switch);
        int index = mDevice.lowAlarmEnable;
        final QMUIDialog.CheckableDialogBuilder builder = new QMUIDialog.CheckableDialogBuilder(getActivity());
        builder.setTitle(getString(R.string.low_alarm_enable_desc))
                .setCheckedIndex(index)
                .addItems(enableSwitch, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getPresenter().setLowAlarmEnable(which == 1);
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void showLowValueDialog() {
        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(getActivity());
        builder.setTitle(getString(R.string.low_alarm_limit_value_desc))
                .setInputType(InputType.TYPE_CLASS_NUMBER)
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction("修改", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        CharSequence text = builder.getEditText().getText();
                        try {
                            int value = Integer.valueOf(text.toString());
                            getPresenter().setLowAlarmLimitValue(value, mDevice.lowNotifyPhones, mDevice.lowSmsContent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    }
                })
                .show();

        builder.getEditText().setText(mDevice.lowAlarmLimitValue + "");
    }

    private void showLowPhonesDialog() {
        QMUIDialog dialog = new QMUIDialog.CustomDialogBuilder(this)
                .setLayout(R.layout.item_phones)
                .setTitle(R.string.low_notify_phones_desc)
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction("修改", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {

                        LinearLayout llPhones = dialog.findViewById(R.id.phones);
                        String[] phones = new String[8];
                        for (int i = 0; i < 8; i++) {
                            EditText etPhone = (EditText) llPhones.getChildAt(i);
                            phones[i] = etPhone.getText().toString();
                        }

                        getPresenter().setLowAlarmLimitValue(mDevice.lowAlarmLimitValue, phones,
                                mDevice.lowSmsContent);

                        dialog.dismiss();
                    }
                })
                .create();

        LinearLayout phones = dialog.findViewById(R.id.phones);
        int index = 0;
        for (String phone : mDevice.lowNotifyPhones) {
            EditText etPhone = (EditText) phones.getChildAt(index);
            etPhone.setText(phone);
            index++;
        }

        dialog.show();
    }

    private void showLowSmsDialog() {
        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(getActivity());
        builder.setTitle(getString(R.string.low_sms_format_desc))
                .setInputType(InputType.TYPE_CLASS_TEXT)
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction("修改", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        CharSequence text = builder.getEditText().getText();
                        try {
                            String value = text.toString();
                            getPresenter().setLowAlarmLimitValue(mDevice.lowAlarmLimitValue,
                                    mDevice.lowNotifyPhones, value);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    }
                })
                .show();

        builder.getEditText().setText(mDevice.lowSmsContent + "");
    }

    private void showLowLowEnableDialog() {
        String[] enableSwitch = getResources().getStringArray(R.array.enable_switch);
        int index = mDevice.lowLowAlarmEnable;
        final QMUIDialog.CheckableDialogBuilder builder = new QMUIDialog.CheckableDialogBuilder(getActivity());
        builder.setTitle(getString(R.string.low_low_alarm_enable_desc))
                .setCheckedIndex(index)
                .addItems(enableSwitch, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getPresenter().setLowLowAlarmEnable(which == 1);
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void showLowLowValueDialog() {
        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(getActivity());
        builder.setTitle(getString(R.string.low_low_alarm_limit_value_desc))
                .setInputType(InputType.TYPE_CLASS_NUMBER)
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction("修改", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        CharSequence text = builder.getEditText().getText();
                        try {
                            int value = Integer.valueOf(text.toString());
                            getPresenter().setLowLowAlarmLimitValue(value, mDevice.lowLowNotifyPhones,
                                    mDevice.lowLowSmsContent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    }
                })
                .show();

        builder.getEditText().setText(mDevice.lowLowAlarmLimitValue + "");
    }

    private void showLowLowPhonesDialog() {
        QMUIDialog dialog = new QMUIDialog.CustomDialogBuilder(this)
                .setLayout(R.layout.item_phones)
                .setTitle(R.string.low_low_notify_phones_desc)
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction("修改", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {

                        LinearLayout llPhones = dialog.findViewById(R.id.phones);
                        String[] phones = new String[8];
                        for (int i = 0; i < 8; i++) {
                            EditText etPhone = (EditText) llPhones.getChildAt(i);
                            phones[i] = etPhone.getText().toString();
                        }

                        getPresenter().setLowLowAlarmLimitValue(mDevice.lowLowAlarmLimitValue, phones,
                                mDevice.lowLowSmsContent);

                        dialog.dismiss();
                    }
                })
                .create();

        LinearLayout phones = dialog.findViewById(R.id.phones);
        int index = 0;
        for (String phone : mDevice.lowLowNotifyPhones) {
            EditText etPhone = (EditText) phones.getChildAt(index);
            etPhone.setText(phone);
            index++;
        }

        dialog.show();
    }

    private void showLowLowSmsDialog() {
        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(getActivity());
        builder.setTitle(getString(R.string.low_sms_format_desc))
                .setInputType(InputType.TYPE_CLASS_TEXT)
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction("修改", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        CharSequence text = builder.getEditText().getText();
                        try {
                            String value = text.toString();
                            getPresenter().setLowLowAlarmLimitValue(mDevice.lowLowAlarmLimitValue,
                                    mDevice.lowLowNotifyPhones, value);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    }
                })
                .show();

        builder.getEditText().setText(mDevice.lowLowSmsContent + "");
    }

    private void showDefenseEnableDialog() {
        String[] enableSwitch = getResources().getStringArray(R.array.enable_switch);
        int index = mDevice.lowAlarmEnable;
        final QMUIDialog.CheckableDialogBuilder builder = new QMUIDialog.CheckableDialogBuilder(getActivity());
        builder.setTitle(getString(R.string.defense_enable_desc))
                .setCheckedIndex(index)
                .addItems(enableSwitch, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getPresenter().setDefenseEnable(which == 1);
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void setAliasNameDialog() {

    }


}