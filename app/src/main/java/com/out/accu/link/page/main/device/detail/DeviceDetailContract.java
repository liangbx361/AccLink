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
    }

    interface Presenter extends SmartStatePresenter {

    }
}