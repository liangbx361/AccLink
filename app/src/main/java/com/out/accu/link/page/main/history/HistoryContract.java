package com.out.accu.link.page.main.history;

import com.cyou17173.android.arch.base.mvp.SmartStatePresenter;
import com.cyou17173.android.arch.base.mvp.SmartStateView;
import com.out.accu.link.data.mode.DeviceHistory;

import java.util.List;

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

    interface View extends SmartStateView {

        void showHistory(List<DeviceHistory> histories);
    }

    interface Presenter extends SmartStatePresenter {

        void search(long start, long end);
    }
}