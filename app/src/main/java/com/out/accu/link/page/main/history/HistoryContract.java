package com.out.accu.link.page.main.history;

import com.cyou17173.android.arch.base.mvp.SmartPresenter;
import com.cyou17173.android.arch.base.mvp.SmartView;
import com.out.accu.link.data.mode.DeviceHistory;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2017</p>
 * <P>Company: 17173</p>
 *
 * @author
 * @version 2017-10-15
 */
public interface HistoryContract {

    interface View extends SmartView {

        void showHistory(DeviceHistory history);
    }

    interface Presenter extends SmartPresenter {

        void search(String deviceId, long start, long end);
    }
}