package com.out.accu.link.page.main.device.detail;

import com.cyou17173.android.arch.base.mvp.SmartStatePresenter;
import com.cyou17173.android.arch.base.mvp.SmartStateView;
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
public interface DeviceDetailContract {

    interface View extends SmartStateView {
        void showData(Device device);
        void showLoadingDialog();
        void hideLoadingDialog();
        void showLoadSuccessDialog();
        void hideLoadSuccessDialog();
        void showLoadFailDialog();
        void hideLoadFailDialog();
    }

    interface Presenter extends SmartStatePresenter {
        void setDevice(Device device);
        void setChannelRange(int channel1, int channel2);
        void setValueRange(int value);
        void setReportPeriod(int period);
        void setLowAlarmEnable(boolean enable);
        void setLowAlarmLimitValue(int value, String[] phones, String sms);
        void setLowLowAlarmEnable(boolean enable);
        void setLowLowAlarmLimitValue(int value, String[] phones, String sms);
        void setGps(double lat, double lot);
        void setDefenseEnable(boolean enable);
        void setAliasName(String name);
    }
}